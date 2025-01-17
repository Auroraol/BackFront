/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:28
 */

package dao

import (
	"gorm.io/gorm"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type GoodsDao struct{}

// Insert 插入
func (gd *GoodsDao) Insert(goods *common.Goods) (row int64) {
	db := util.GetDB()
	result := db.Table("goods").Create(&goods)
	return result.RowsAffected
}

// Delete 删除
func (gd *GoodsDao) Delete(goods *common.Goods) (row int64) {
	db := util.GetDB()
	result := db.Delete(&goods, 1)
	return result.RowsAffected
}

// Update
// 更新（单/多改）
func (gd *GoodsDao) Update(goods *common.Goods, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("goods").Model(&goods).Update(key, value)
	return result.RowsAffected
	//db.Model(&user).Updates(Product{Price: 666, Code: "1003"}) //多
}

// 自增更新（单）
func (gd *GoodsDao) UpdateAuto(goods *common.Goods, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("goods").Model(&goods).UpdateColumn(key, gorm.Expr(key+" + ?", value))
	return result.RowsAffected
}

// QueryOneGoodsInfo 查询（根据商品ID查单个商品所有数据）（因gorm自带id转换较麻烦，所以直接传参进来）
func (gd *GoodsDao) QueryOneGoodsInfo(goods *common.Goods, goodsId string) (row int64) {
	db := util.GetDB()
	result := db.Table("goods").Where("id = ?", goodsId).Select("created_at", "updated_at", "id", "goods_name", "description", "price", "money_type", "storage_count", "class_tag", "icon", "status", "second", "display_day", "goods_type", "item_value").First(&goods)
	return result.RowsAffected
}

// QueryAllGoodsInfo 查询（查询全部商品所有数据）
func (gd *GoodsDao) QueryAllGoodsInfo(goodsList *[]response.GoodsResponse) (row int64) {
	db := util.GetDB()
	result := db.Debug().Table("goods").Select("created_at", "updated_at", "id", "goods_type", "goods_name", "description", "price", "money_type", "storage_count", "class_tag", "icon", "status", "second", "display_day").Find(&goodsList)
	return result.RowsAffected
}

// QueryByData 查询（单/根据数据查询）
func (gd *GoodsDao) QueryByData(goods *common.Goods, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("goods").First(&goods, key+" = ?", value)
	return result.RowsAffected
}
