/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:28
 */

package dao

import (
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type OrderDao struct{}

// Insert 插入
func (od *OrderDao) Insert(order *common.Order) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").Create(&order)
	return result.RowsAffected
}

// Delete 删除
func (od *OrderDao) Delete(order *common.Order) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").Delete(&order, 1)
	return result.RowsAffected
}

// Update 更新（单/多改）
func (od *OrderDao) Update(order *common.Order, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").Model(&order).Update(key, value)
	return result.RowsAffected
	//db.Model(&user).Updates(Product{Price: 666, Code: "1003"}) //多
}

// UpdateOrderStatus 修改订单状态
func (od *OrderDao) UpdateOrderStatus(order *common.Order, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").Model(&order).Where("order_id = ?", order.OrderId).Update("pay_status", value)
	return result.RowsAffected
	//db.Model(&user).Updates(Product{Price: 666, Code: "1003"}) //多
}

// QueryOrderStatus 查询订单状态
func (od *OrderDao) QueryOrderStatus(order *common.Order) (row int64) {
	db := util.GetDB()
	result := db.Debug().Table("orders").Model(&order).Select("pay_status").Where("order_id = ?", order.OrderId).Find(order)
	return result.RowsAffected
}

// QueryOneGoodsInfo 查询（根据商品ID查单个订单所有数据）（因gorm自带id转换较麻烦，所以直接传参进来）
func (od *OrderDao) QueryOneGoodsInfo(order *common.Order, goodsId string) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").Where("id = ?", goodsId).Select("created_at", "updated_at", "id", "goods_name", "description", "price", "money_type", "storage_count", "class_tag", "icon", "status", "second", "display_day").First(&order)
	return result.RowsAffected
}

// QueryUserOrderListByUserId 根据用户Id查询用户所有订单数据
func (od *OrderDao) QueryUserOrderListByUserId(user *common.User, orderList *[]response.OrderResponse) (row int64) {
	db := util.GetDB()
	result := db.Debug().Table("orders").Where("user_id = ?", user.UserId).Order("created_at desc").Limit(10).Select("created_at", "updated_at", "order_id", "goods_id", "user_id", "price", "count", "payment", "money_type", "pay_status", "name", "end_time").Find(&orderList)
	return result.RowsAffected
}

// QueryByData 查询（单/根据数据查询）
func (od *OrderDao) QueryByData(order *common.Order, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("orders").First(&order, key+" = ?", value).First(order)
	return result.RowsAffected
}
