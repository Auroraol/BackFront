/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/04 20:56
 */

package common

import (
	"gorm.io/gorm"
	"time"
)

type Order struct {
	gorm.Model
	OrderId   string    `gorm:"order_id" json:"order_id"`            //订单id
	GoodsId   string    `gorm:"goods_id" json:"goods_id"`            //商品id
	UserId    string    `gorm:"user_id" json:"user_id"`              //用户id
	Name      string    `gorm:"name" json:"name"`                    //订单商品名
	EndTime   time.Time `gorm:"end_time" json:"end_time"`            //订单结束时间
	Price     float64   `gorm:"price;type:decimal" json:"price"`     //商品单价
	Count     int8      `gorm:"count" json:"count"`                  //购买数量
	Payment   float64   `gorm:"payment;type:decimal" json:"payment"` //支付金额
	MoneyType string    `gorm:"money_type" json:"money_type"`        //货币类型
	PayStatus int8      `gorm:"pay_status" json:"pay_status"`        //支付状态 0:未支付，1:已支付，2：已超时，3：已取消)
	PayUrl    string    `gorm:"-" json:"pay_url"`                    //支付链接
}
