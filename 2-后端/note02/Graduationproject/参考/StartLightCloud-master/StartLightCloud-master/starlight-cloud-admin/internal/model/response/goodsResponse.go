/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/4 10:03
 */

package response

type GoodsResponse struct {
	Id           int32  `gorm:"id" json:"id"`                         //商品Id（gorm里的id）
	GoodsName    string `gorm:"goods_name" json:"goods_name"`         //商品名
	Description  string `gorm:"description" json:"description"`       //描述
	DisplayDay   string `gorm:"display_day" json:"display_day"`       //展示的天数（只读）
	Second       int64  `gorm:"second" json:"second"`                 //持续时间（秒）（-1为永久）
	Price        int32  `gorm:"price" json:"price"`                   //价格
	MoneyType    string `gorm:"money_type" json:"money_type"`         //货币类型
	GoodsType    int8   `gorm:"goods_type" json:"goods_type"`         //产品类型（1：增加存储空间，2：星币充值，0：其它）
	StorageCount int32  `gorm:"storage_count" json:"storage_count"`   //数量
	ClassTag     string `gorm:"class_tag" json:"class_tag"`           //分类标签
	Icon         string `gorm:"profile" json:"profile"`               //主图标
	Status       int8   `gorm:"status;type:tinyint(1)" json:"status"` //状态（0：下架，1：上架，2：售罄）
}
