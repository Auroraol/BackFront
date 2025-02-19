/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 13:32
 */

package response

type UserLoginResponse struct {
	Code      int    `json:"code"`                         //状态码
	UserId    string `json:"user_id"`                      //用户Id
	Token     string `json:"token"`                        //token
	Role      string `json:"role"`                         //角色等级
	LastLogin string `gorm:"last_login" json:"last_login"` //最后一次登录时间
}

type UserInfoResponse struct {
	Code        int     `json:"code"`                                    //状态码
	UserId      string  `json:"user_id"`                                 //用户Id
	Msg         string  `json:"msg"`                                     //消息
	Email       string  `gorm:"email" json:"email"`                      //邮箱
	DisplayName string  `gorm:"display_name" json:"display_name"`        //用户名字
	RAM         string  `json:"ram"`                                     //用户实际云盘存储空间
	TotalRAM    string  `gorm:"totalram" json:"total_ram"`               //用户云盘总存储空间(B)(仅供校验)
	DisplayRAM  int     `gorm:"displayram" json:"display_ram"`           //用户云盘总存储空间(仅供显示)
	CanMoney    float64 `gorm:"can_money;type:decimal" json:"can_money"` //可用的星币数
	PhoneNumber string  `gorm:"phone_number" json:"phone_number"`        //用户手机号
}
