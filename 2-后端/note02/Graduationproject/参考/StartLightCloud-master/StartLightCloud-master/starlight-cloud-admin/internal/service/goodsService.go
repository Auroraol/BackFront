/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 13:25
 */

package service

import (
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
	"sync"
	"system-admin/starlight-cloud-admin/internal/dao"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/request"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
	"time"
)

// 互斥锁
var mutex sync.Mutex

type GoodsService struct{}

func GetGoodsService() (goodsService GoodsService) {
	return goodsService
}

// GetAllGoodsInfo 获取全部商品数据
func (gs *GoodsService) GetAllGoodsInfo(c *gin.Context) (message response.MessageResponse) {

	var goodsDao dao.GoodsDao
	var goodsList []response.GoodsResponse
	row := goodsDao.QueryAllGoodsInfo(&goodsList)

	goodsListJSON, err := json.Marshal(goodsList)
	if err != nil {
		return response.MessageResponse{}
	}

	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoFailed,
			Msg:  "获取商品信息失败",
			Data: "null",
		}
		return
	}

	message = response.MessageResponse{
		Code: util.GetGoodsInfoOK,
		Msg:  "获取商品信息成功",
		Data: string(goodsListJSON),
	}

	return
}

// BuyGoodsBySCoin 购买商品(星币支付)
func (gs *GoodsService) BuyGoodsBySCoin(c *gin.Context) (message response.MessageResponse) {
	goodsRequest := request.GoodsRequest{}
	err := c.ShouldBindQuery(&goodsRequest)
	if err != nil {
		fmt.Println(err)
	}

	//加锁
	mutex.Lock()

	//查询是否有货
	//查询商品的具体信息
	var goodsDao dao.GoodsDao
	goods := &common.Goods{}
	goodsRowsAffected := goodsDao.QueryOneGoodsInfo(goods, goodsRequest.GoodsId)

	if goodsRowsAffected == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//没货就return
	if goods.StorageCount <= 0 {
		message = response.MessageResponse{
			Code: util.StorageCountNotEnough,
			Msg:  "存货不足无法购买",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//查询用户信息
	var userDao dao.UserDao
	user := &common.User{
		UserId: goodsRequest.UserId,
	}

	row := userDao.QueryInfoByUserId(user)
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//创建订单
	order := &common.Order{}
	//订单id号
	order.OrderId = util.NanoIdGenerate()
	//商品id
	order.GoodsId = goodsRequest.GoodsId
	//用户id
	order.UserId = goodsRequest.UserId
	//订单商品名
	order.Name = goods.GoodsName
	//订单结束时间(5分钟后)
	order.EndTime = time.Now().Add(5 * time.Minute)
	//商品单价
	order.Price = goods.Price
	//数量
	order.Count = 1 //todo(一般数量为1，架构设计，暂不处理)
	//货币类型
	order.MoneyType = goods.MoneyType
	//金额
	order.Payment = goods.Price * float64(order.Count)
	//订单状态(0:未支付)
	order.PayStatus = 0

	//如果订单货币类型为SCoin（星币）
	//则继续执行以下步骤
	if order.MoneyType != "SCoin" {
		message = response.MessageResponse{
			//todo
			Code: util.GetGoodsInfoUnusual,
			Msg:  "暂时不支持CNY",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//查询用户是否能够支付得起商品
	realityMoney := user.CanMoney - order.Payment
	if realityMoney < 0 {
		message = response.MessageResponse{
			Code: util.PaymentWithSCoinMoneyNotEnoughFailed,
			Msg:  "购买失败，星币余额不足",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//将订单插入数据库
	orderDao := dao.OrderDao{}
	orderResult := orderDao.Insert(order)
	if orderResult == 0 {
		message = response.MessageResponse{
			Code: util.CreateOrderFailed,
			Msg:  "创建订单失败",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//把订单加入redis
	var orderService OrderService
	orderService.AddOrderToRedis(order)

	////账面结算
	//先冻结金额再执行后续操作
	//结算实际冻结金额
	frozenMoney := order.Payment
	row = userDao.UpdateWithAutoIncrement(user, "frozen_money", fmt.Sprintf("%.2f", frozenMoney))
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//查询变化后的用户信息
	row = userDao.QueryInfoByUserId(user)
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//结算总余额
	user.Money = user.Money - frozenMoney

	//结算冻结金额
	user.FrozenMoney = user.FrozenMoney - frozenMoney

	//结算可用金额
	user.CanMoney = user.Money - user.FrozenMoney

	//扣款
	row, tx, err := userDao.UpdateMoney(user, user.Money, user.CanMoney, user.FrozenMoney)
	if row == 0 {
		fmt.Println(err)
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return

	}
	tx.Commit()

	//更改订单信息为1(已支付)
	order.PayStatus = 1
	orderResult = orderDao.Update(order, "pay_status", "1")
	if orderResult == 0 {
		message = response.MessageResponse{
			Code: util.UpdateOrderFailed,
			Msg:  "更改订单失败",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//创建发送物品模型
	//计算实际物品数量值
	itemValue := goods.ItemValue * float64(order.Count)
	sendGoodsInfo := &common.SendGoodsInfo{
		UserId:    user.UserId,
		GoodsType: goods.GoodsType,
		ItemValue: itemValue,
	}

	//根据商品类型去调用实现对应的业务
	//将发送物品信息模型发送到派遣模型自动处理
	dispatchService := &DispatchService{}
	dispatchService.SendGoodsToUser(sendGoodsInfo)

	//减少存货数量
	//再次查询商品当前的具体信息
	goods = &common.Goods{}
	goodsRowsAffected = goodsDao.QueryOneGoodsInfo(goods, goodsRequest.GoodsId)

	if goodsRowsAffected == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	row = goodsDao.UpdateAuto(goods, "storage_count", "-1")
	if row == 0 {
		message = response.MessageResponse{
			Code: util.ReduceStorageCountFailed,
			Msg:  "减少仓库货物失败",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	message = response.MessageResponse{
		Code: util.PaymentWithSCoinOK,
		Msg:  "购买商品成功",
		Data: "null",
	}

	//解除互斥锁
	mutex.Unlock()
	return
}

// BuyGoodsByCNY 购买商品(人民币支付)
func (gs *GoodsService) BuyGoodsByCNY(c *gin.Context) (message response.MessageResponse) {

	goodsRequest := request.GoodsRequest{}
	err := c.ShouldBindQuery(&goodsRequest)
	if err != nil {
		fmt.Println(err)
	}

	fmt.Println("CNY...")
	//加锁
	mutex.Lock()

	//查询存货是否足够
	//查询商品的具体信息
	var goodsDao dao.GoodsDao
	goods := &common.Goods{}
	goodsRowsAffected := goodsDao.QueryOneGoodsInfo(goods, goodsRequest.GoodsId)

	if goodsRowsAffected == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//没货就return
	if goods.StorageCount <= 0 {
		message = response.MessageResponse{
			Code: util.StorageCountNotEnough,
			Msg:  "存货不足无法购买",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//查询用户信息
	var userDao dao.UserDao
	user := &common.User{
		UserId: goodsRequest.UserId,
	}

	row := userDao.QueryInfoByUserId(user)
	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//创建订单
	order := &common.Order{}
	//订单id号
	order.OrderId = util.NanoIdGenerate()
	//商品id
	order.GoodsId = goodsRequest.GoodsId
	//用户id
	order.UserId = goodsRequest.UserId
	//订单商品名
	order.Name = goods.GoodsName
	//订单结束时间(5分钟后)
	order.EndTime = time.Now().Add(5 * time.Minute)
	//商品单价
	order.Price = goods.Price
	//数量
	order.Count = 1 //todo(一般数量为1，架构设计，暂不处理)
	//货币类型
	order.MoneyType = goods.MoneyType
	//金额
	order.Payment = goods.Price * float64(order.Count)
	//订单状态(0:未支付)
	order.PayStatus = 0

	//如果订单货币类型为CNY
	//则继续执行以下步骤
	if order.MoneyType != "CNY" {
		message = response.MessageResponse{
			//todo
			Code: util.GetGoodsInfoUnusual,
			Msg:  "暂时不支持SCoin",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//将订单插入数据库
	orderDao := dao.OrderDao{}
	orderResult := orderDao.Insert(order)
	if orderResult == 0 {
		message = response.MessageResponse{
			Code: util.CreateOrderFailed,
			Msg:  "创建订单失败",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	//处理支付订单并生成支付宝链接
	fmt.Println("订单号：：：", order.OrderId)
	url := util.Alipay(c, order.OrderId, "星币充值 "+strconv.FormatFloat(goods.Price, 'f', 2, 64)+" 个", strconv.FormatFloat(goods.Price, 'f', 2, 64))

	//设置支付链接
	order.PayUrl = url

	//把订单加入redis
	var orderService OrderService
	orderService.AddOrderToRedis(order)

	//解除互斥锁
	mutex.Unlock()

	c.String(http.StatusOK, url)

	fmt.Println("prepared to print in alipay...")
	return
}

// PayNotify 支付成功的回调
func (gs *GoodsService) PayNotify(c *gin.Context) (message response.MessageResponse) {
	fmt.Println("收到来自支付成功的回调...")
	//校验是否通过验签
	result := util.AlipayNotify(c)

	if result != true {
		message = response.MessageResponse{
			Code: util.VerifyPaymentWithSCoinUnusual,
			Msg:  "充值星币验签异常",
			Data: "null",
		}
		return
	}

	//查询订单信息
	orderDao := dao.OrderDao{}

	//获取订单号
	orderId := c.Request.Form.Get("out_trade_no")
	order := &common.Order{}
	order.OrderId = orderId
	orderResult := orderDao.QueryByData(order, "order_id", order.OrderId)
	if orderResult == 0 {
		message = response.MessageResponse{
			Code: util.VerifyPaymentWithSCoinUnusual,
			Msg:  "充值星币异常",
			Data: "null",
		}
		fmt.Println("failed")
		return
	}

	//查询商品的具体信息
	var goodsDao dao.GoodsDao
	goods := &common.Goods{}
	goodsRowsAffected := goodsDao.QueryOneGoodsInfo(goods, order.GoodsId)
	if goodsRowsAffected == 0 {
		message = response.MessageResponse{
			Code: util.VerifyPaymentWithSCoinUnusual,
			Msg:  "充值星币异常",
			Data: "null",
		}
		return
	}

	//创建发送物品模型
	//计算实际物品数量值
	itemValue := goods.ItemValue * float64(order.Count)
	sendGoodsInfo := &common.SendGoodsInfo{
		UserId:    order.UserId,
		GoodsType: goods.GoodsType,
		ItemValue: itemValue,
	}

	//更改订单信息为1(已支付)
	order.PayStatus = 1
	orderResult = orderDao.Update(order, "pay_status", "1")
	if orderResult == 0 {
		message = response.MessageResponse{
			Code: util.UpdateOrderFailed,
			Msg:  "更改订单失败",
			Data: "null",
		}
		fmt.Println("更改订单失败了！")
		return
	}

	//根据商品类型去调用实现对应的业务
	//将发送物品信息模型发送到派遣模型自动处理
	dispatchService := &DispatchService{}
	dispatchService.SendGoodsToUser(sendGoodsInfo)

	//减少存货数量
	//再次查询商品当前的具体信息
	goods = &common.Goods{}
	goodsRowsAffected = goodsDao.QueryOneGoodsInfo(goods, order.GoodsId)

	if goodsRowsAffected == 0 {
		message = response.MessageResponse{
			Code: util.GetGoodsInfoUnusual,
			Msg:  "购买商品异常",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	row := goodsDao.UpdateAuto(goods, "storage_count", "-1")
	if row == 0 {
		message = response.MessageResponse{
			Code: util.ReduceStorageCountFailed,
			Msg:  "减少仓库货物失败",
			Data: "null",
		}
		mutex.Unlock()
		return
	}

	message = response.MessageResponse{
		Code: util.RechargeSCoinOK,
		Msg:  "充值星币成功",
		Data: "null",
	}
	return
}
