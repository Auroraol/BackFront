package utils

import (
	"errors"
	"github.com/dgrijalva/jwt-go"
	"time"
)

//jwt认证
type CustomClaims struct {
	UserId int
	jwt.StandardClaims
}

type Jwt struct {
	JwtSecretKey []byte //jwt密码 这里输入参数需要[]byte 否则报错：key is of invalid type
}

func NewJwtService(jwtSecretKey []byte) *Jwt {
	return &Jwt{
		JwtSecretKey: jwtSecretKey,
	}
}

//创建token
func (this *Jwt) CreateToken(claims CustomClaims) (token string, err error) {
	tokenObj := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)

	token, err = tokenObj.SignedString(this.JwtSecretKey)
	return
}

//解析token
func (this *Jwt) ParseToken(tokenString string) (*CustomClaims, error) {
	token, err := jwt.ParseWithClaims(tokenString, &CustomClaims{}, func(token *jwt.Token) (interface{}, error) {
		return this.JwtSecretKey, nil
	})

	if err != nil {
		if ve, ok := err.(*jwt.ValidationError); ok {
			if ve.Errors&jwt.ValidationErrorMalformed != 0 {
				return nil, errors.New("无法解析的token")
			} else if ve.Errors&jwt.ValidationErrorExpired != 0 {
				// Token is expired
				return nil, errors.New("token过期")
			} else if ve.Errors&jwt.ValidationErrorNotValidYet != 0 {
				return nil, errors.New("token验证失败")
			} else {
				return nil, errors.New("token错误")
			}
		}

		return nil, err

	}
	if claims, ok := token.Claims.(*CustomClaims); ok && token.Valid {
		return claims, nil
	}
	return nil, errors.New("token解析失败")
}

//刷新token
func (this *Jwt) RefreshToken(tokenString string, unixTime int64) (string, error) {
	jwt.TimeFunc = func() time.Time {
		return time.Unix(0, 0)
	}
	token, err := jwt.ParseWithClaims(tokenString, &CustomClaims{}, func(token *jwt.Token) (interface{}, error) {
		return this.JwtSecretKey, nil
	})
	if err != nil {
		return "", err
	}
	if claims, ok := token.Claims.(*CustomClaims); ok && token.Valid {
		jwt.TimeFunc = time.Now
		claims.StandardClaims.ExpiresAt = unixTime
		return this.CreateToken(*claims)
	}
	return "", errors.New("token解析失败")

}
