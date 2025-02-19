/**
 * @Author: FxShadow
 * @Description:用户模型
 * @Date: 2023/03/15 22:31
 */

package request

type UserLoginRequest struct {
	Username string `gorm:"username" json:"username"` //账号
	Password string `gorm:"password" json:"password"` //密码
	Token    string `gorm:"token" json:"token"`       //Token
	IP       string //IP地址
}

type UserRegisterRequest struct {
	Username   string `json:"username"`    //账号
	Password   string `json:"password"`    //密码
	Email      string `json:"email"`       //邮箱
	VerifyCode string `json:"verify_code"` //邮箱
}
