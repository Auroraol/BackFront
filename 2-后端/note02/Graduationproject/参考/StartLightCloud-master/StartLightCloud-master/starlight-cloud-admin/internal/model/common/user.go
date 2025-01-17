/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:58
 */

package common

import (
	"gorm.io/gorm"
	"time"
)

type User struct {
	gorm.Model
	Username       string    `gorm:"username" json:"username"`                                    //账号
	Password       string    `gorm:"password" json:"password"`                                    //密码
	UserId         string    `gorm:"auto_increment;not null;" json:"user_id"`                     //Id(NanoID)
	DisplayName    string    `gorm:"display_name" json:"display_name"`                            //名字
	Profile        string    `gorm:"profile" json:"profile"`                                      //头像
	ProfileVersion string    `gorm:"profile_version" json:"profile_version"`                      //头像版本
	PhoneNumber    string    `gorm:"phone_number" json:"phone_number"`                            //手机号
	Email          string    `gorm:"email" json:"email"`                                          //邮箱
	Role           int       `gorm:"role" json:"role"`                                            //角色(0:user,1:admin)
	RAM            string    `gorm:"ram" json:"ram"`                                              //云盘实际存储空间(B)
	TotalRAM       string    `gorm:"total_ram" json:"total_ram"`                                  //云盘总存储空间(B)(仅供校验)
	DisplayRAM     int       `gorm:"display_ram" json:"display_ram"`                              //云盘总存储空间(仅供显示)
	Money          float64   `gorm:"money;type:decimal" json:"money"`                             //星币数
	CanMoney       float64   `gorm:"can_money;type:decimal" json:"can_money"`                     //可用的星币数
	FrozenMoney    float64   `gorm:"frozen_money;type:decimal;update_column" json:"frozen_money"` //冻结的星币数
	LastLogin      time.Time `gorm:"last_login" json:"last_login"`                                //最后登录时间
}
