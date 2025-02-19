/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/03 23:57
 */

package common

import (
	"gorm.io/gorm"
)

// Goods 产品模型
type Goods struct {
	gorm.Model
	GoodsName    string  `gorm:"goods_name" json:"goods_name"`         //商品名
	Description  string  `gorm:"description" json:"description"`       //描述
	DisplayDay   string  `gorm:"display_day" json:"display_day"`       //展示的天数（只读）
	Second       int64   `gorm:"second" json:"second"`                 //持续时间（秒）（-1为永久）
	Price        float64 `gorm:"price" json:"price"`                   //价格
	ItemValue    float64 `gorm:"item_value" json:"item_value"`         //物品实际数量值（例如5GB就是5，10星币就是10）
	MoneyType    string  `gorm:"money_type" json:"money_type"`         //货币类型
	StorageCount int8    `gorm:"storage_count" json:"storage_count"`   //仓库数量
	ClassTag     string  `gorm:"class_tag" json:"class_tag"`           //分类标签
	GoodsType    int8    `gorm:"goods_type" json:"goods_type"`         //产品类型（1：增加存储空间，2：星币充值，0：其它）
	Icon         string  `gorm:"profile;type:blob" json:"profile"`     //主图标
	Status       int8    `gorm:"status;type:tinyint(1)" json:"status"` //状态（0：下架，1：上架，2：售罄）
}

// Item 物品模型
type Item struct {
	ItemId   string `gorm:"item_id" json:"item_id"`       //物品Id
	ItemName string `gorm:"item_name" json:"item_name"`   //物品名
	ItemType int8   `gorm:"goods_type" json:"goods_type"` //产品类型（1：存储空间，2：星币，0：其它）
}

// SendGoodsInfo 发放内容模型
type SendGoodsInfo struct {
	gorm.Model
	UserId    string  `gorm:"auto_increment;not null;" json:"user_id"` //用户Id
	GoodsType int8    `gorm:"goods_type" json:"goods_type"`            //产品类型（0：未知，1：增加存储空间，2：星币充值，3：其它）
	ItemValue float64 `gorm:"item_value" json:"item_value"`            //物品实际数量值（例如5GB就是5，10星币就是10）
}
