/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/04 22:44
 */

package common

import "gorm.io/gorm"

type UserAccount struct {
	gorm.Model
	UserId      string  `gorm:"user_id" json:"user_id"`                        //用户id
	Money       float64 `gorm:"money;type:decimal" json:"money"`               //星币数（账户金额）
	CanMoney    float64 `gorm:"can_money;type:decimal" json:"can_money"`       //可用的星币数
	FrozenMoney float64 `gorm:"frozen_money;type:decimal" json:"frozen_money"` //冻结的星币数
}
