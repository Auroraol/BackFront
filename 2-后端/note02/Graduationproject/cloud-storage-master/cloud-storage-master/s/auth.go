package s

import (
	"errors"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/utils"
	"github.com/jinzhu/gorm"
	"time"
)

//登录验证服务
type (
	authService struct {
		jwt           *utils.Jwt    //jwt认证服务
		tokenLifeTime time.Duration //token有效时间 单位秒
	}

	//用于认证的结构体
	Auth struct {
		UserName string `field:"用户名"`
		Password string `field:"密码"`
	}
)

var AuthService *authService

func init() {
	AuthService = NewAuth()
}

func NewAuth() *authService {
	this := &authService{
		jwt:           utils.NewJwtService([]byte(entitys.MyAppConfig.JwtSecretKey)),
		tokenLifeTime: 86400,
	}

	return this
}

//获取token认证密码
func (this *authService) CreateToken(userId int) (token string, err error) {
	customClaims := utils.CustomClaims{
		UserId: userId,
	}

	customClaims.ExpiresAt = time.Now().Add(this.tokenLifeTime * time.Second).Unix()

	token, err = this.jwt.CreateToken(customClaims)

	return
}

//通过token设置登录用户
func (this *authService) AuthToken(token string) (user entitys.User, err error) {
	var claims *utils.CustomClaims
	if claims, err = this.jwt.ParseToken(token); err != nil {
		return
	}

	return this.getUser(claims.UserId)
}

//记录认证用户
func (this *authService) getUser(userId int) (user entitys.User, err error) {
	err = Db.First(&user, userId).Error
	return
}

//获取认证用户
func (this *authService) Auth(req Auth) (user entitys.User, err error) {

	if err = Db.Where("user_name = ?", req.UserName).First(&user).Error; err != nil {
		if err == gorm.ErrRecordNotFound {
			err = errors.New("用户不存在")
		}

		return
	}

	if utils.Md5(req.Password+user.Salt) != user.Password {
		err = errors.New("密码错误")
	}

	return
}
