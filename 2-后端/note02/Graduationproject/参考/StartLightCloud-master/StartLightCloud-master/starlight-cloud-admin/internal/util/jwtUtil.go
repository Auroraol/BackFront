/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 17:24
 */

package util

import (
	"errors"
	jwt2 "github.com/golang-jwt/jwt"
	"log"
	"time"
)

type MyClaims struct {
	UserId string `json:"user_id"`
	jwt2.StandardClaims
}

// 设置过期时间
const TokenExpireDuration = time.Second * 10000

// 获取密钥
func GetSigningKey() (signingKey string) {
	signingKey = "7A1a5r2o9nT"
	return
}

// 加密token
func EncryptionJWT(userId string) (tokenString string) {
	signingKey := GetSigningKey
	key := []byte(signingKey())

	//创建token结构体
	c := MyClaims{
		userId,
		jwt2.StandardClaims{
			Audience:  "",
			ExpiresAt: time.Now().Add(TokenExpireDuration).Unix(), // 过期时间,
			Id:        userId,
			IssuedAt:  time.Now().Unix(), //发放时间,
			Issuer:    "starLight",       // 签发人
			NotBefore: 0,
			Subject:   "",
		},
	}
	token := jwt2.NewWithClaims(jwt2.SigningMethodHS256, c)

	//调用加密方法
	tokenString, err := token.SignedString(key)
	if err != nil {
		log.Println(err.Error())
		return
	}
	return tokenString
}

// 解密token
func DecipherJWT(tokenString string) (*MyClaims, error) {
	signingKey := GetSigningKey
	key := []byte(signingKey())

	// 解析token
	token, err := jwt2.ParseWithClaims(tokenString, &MyClaims{}, func(token *jwt2.Token) (i interface{}, err error) {
		return key, nil
	})
	if err != nil {
		log.Println(err.Error())
	}

	if claims, ok := token.Claims.(*MyClaims); ok && token.Valid { // 校验token
		return claims, nil
	}
	return nil, errors.New("invalid token")

}
