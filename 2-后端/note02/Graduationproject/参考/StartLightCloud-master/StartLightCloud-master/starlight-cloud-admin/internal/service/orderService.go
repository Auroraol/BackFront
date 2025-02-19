/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 13:25
 */

package service

import (
	"encoding/json"
	"fmt"
	"github.com/aliyun/aliyun-oss-go-sdk/oss"
	"github.com/gin-gonic/gin"
	"github.com/gomodule/redigo/redis"
	"strings"
	"system-admin/starlight-cloud-admin/internal/dao"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type OrderService struct{}

func GetOrderService() (orderService OrderService) {
	return orderService
}

// GetUserOrderList 获取用户订单列表
func (gs *OrderService) GetUserOrderList(c *gin.Context) (message response.MessageResponse) {
	userId, _ := c.GetQuery("userId")

	var orderList []response.OrderResponse
	user := &common.User{UserId: userId}

	var orderDao dao.OrderDao
	row := orderDao.QueryUserOrderListByUserId(user, &orderList)

	orderListJSON, err := json.Marshal(orderList)
	if err != nil {
		return response.MessageResponse{}
	}

	fmt.Println("OSS Go SDK Version: ", oss.Version)

	if row == 0 {
		message = response.MessageResponse{
			Code: util.GetUserOrderListFailed,
			Msg:  "获取用户所有订单失败",
			Data: "null",
		}
		return
	}

	message = response.MessageResponse{
		Code: util.GetUserOrderListOK,
		Msg:  "获取用户所有订单成功",
		Data: string(orderListJSON),
	}

	return
}

// CancelOrder 取消订单
func (gs *OrderService) CancelOrder(c *gin.Context) (message response.MessageResponse) {
	orderId, _ := c.GetQuery("orderId")
	conn := util.GetRedis()
	defer conn.Close()

	order := &common.Order{OrderId: orderId}
	var orderDao dao.OrderDao

	//取消支付宝订单
	result := util.AlipayCancel(orderId)

	//查询订单状态
	row := orderDao.QueryOrderStatus(order)
	//fmt.Printf("%+v", order, row == 1 && order.PayStatus == 2, row, order.PayStatus)
	if row == 1 && order.PayStatus == 2 {
		message = response.MessageResponse{
			Code: util.OrderoverTimeCancelOrderFailed,
			Msg:  "取消订单失败，订单已超时",
			Data: "null",
		}
		return
	}

	//修改订单状态
	row = orderDao.UpdateOrderStatus(order, "3")

	if row == 0 || result == false {
		message = response.MessageResponse{
			Code: util.CancelOrderFailed,
			Msg:  "取消订单失败",
			Data: "null",
		}
		return
	}

	message = response.MessageResponse{
		Code: util.CancelOrderOK,
		Msg:  "取消订单成功",
		Data: "null",
	}

	return
}

// AddOrderToRedis 将订单加入redis
func (gs *OrderService) AddOrderToRedis(order *common.Order) (result bool) {

	conn := util.GetRedis()
	defer conn.Close()

	key := "oid:" + order.OrderId
	data, err := json.Marshal(order)
	if err != nil {
		fmt.Println(err)
	}

	//将订单信息存储到redis
	conn.Do("SET", key, data, "EX", 300)

	//将订单存储到另一个数据结构保存下来，防止主订单key消失没有value可用
	conn.Do("HSET", "orders", "oid:"+order.OrderId, data)

	//300秒后会取消订单
	fmt.Println("已加入订单")
	return true
}

// GetOrderPayUrlFromRedis 从redis中获取订单的支付链接
func (gs *OrderService) GetOrderPayUrlFromRedis(c *gin.Context) (message response.MessageResponse) {
	orderId, _ := c.GetQuery("orderId")
	conn := util.GetRedis()
	defer conn.Close()

	key := "oid:" + orderId
	redisResult, err := redis.Bytes(conn.Do("get", key))
	if err != nil {
		fmt.Println("not found:", err)
	}

	if redisResult == nil {
		fmt.Println("从redis中没有查到对应订单")
		message = response.MessageResponse{
			Code: util.GetOrderPayUrlFromRedisFailed,
			Msg:  "从redis中没有查到订该单",
			Data: "null",
		}
		return
	}

	fmt.Println("redis:::::result::", string(redisResult))
	order := common.Order{}
	json.Unmarshal(redisResult, &order)

	message = response.MessageResponse{
		Code: util.GetOrderPayUrlFromRedisOK,
		Msg:  "从redis中获取订单的支付链接成功",
		Data: order.PayUrl,
	}
	return
}

// TimerTaskCheckOrder 轮询Redis内的订单
func (gs *OrderService) TimerTaskCheckOrder() (result bool) {

	conn := util.GetRedis()
	defer conn.Close()

	// 订阅键空间通知（过期事件）
	//订阅key超时频道
	conn.Do("PSUBSCRIBE", "__keyevent@0__:expired")

	//创建支持发布/订阅功能的连接
	pubSubConn := redis.PubSubConn{Conn: conn}

	//创建普通连接用于普通的查询
	conn2 := util.GetRedis()
	defer conn2.Close()

	// 循环获取过期事件
	for {
		reply := pubSubConn.Receive()

		switch v := reply.(type) {
		case redis.Message:
			// 获取过期键名和值
			redisResult := string(v.Data)
			fmt.Printf("收到了： %s\n", redisResult)
			//拆解订单id字符串
			orderId := ""
			if len(redisResult) > 4 && redisResult[0:4] == "oid:" {
				splitResult := strings.Split(redisResult, ":")
				orderId = splitResult[1]

				//从redis里的orders获取订单价格类型
				orderInfoString, err := redis.String(conn2.Do("HGET", "orders", "oid:"+orderId))
				if err != nil {
					fmt.Println("从redis里的orders获取订单价格类型:", err)
				}

				//解析成json
				var order common.Order
				json.Unmarshal([]byte(orderInfoString), &order)

				if order.MoneyType == "CNY" {
					//取消支付宝订单
					cancel := util.AlipayCancel(orderId)
					fmt.Println("cancel", cancel)
				}

				//更新数据库
				orderDao := dao.OrderDao{}
				row := orderDao.UpdateOrderStatus(&common.Order{OrderId: orderId}, "2")

				if row > 0 {
					fmt.Println("超时自动取消订单成功")
				}
			}

		default:
			fmt.Printf("不知道什么类型啊")
		}
	}

}
