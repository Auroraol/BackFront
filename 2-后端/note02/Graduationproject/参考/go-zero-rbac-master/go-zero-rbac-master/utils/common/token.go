/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：token.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月16日 21:55:09
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package common

import "github.com/dgrijalva/jwt-go"

// GetJwtToken 创建token
func GetJwtToken(secretKey string, iat, seconds, userId int64) (string, error) {
	claims := make(jwt.MapClaims)
	claims["exp"] = iat + seconds
	claims["iat"] = iat
	claims["userId"] = userId
	token := jwt.New(jwt.SigningMethodHS256)
	token.Claims = claims
	return token.SignedString([]byte(secretKey))
}

// ParseToken 解析token
func ParseToken(tokenString, AccessSecret string) (jwt.MapClaims, error) {
	fn := func(token *jwt.Token) (interface{}, error) {
		return []byte(AccessSecret), nil //校验字符串
	}

	result, err := jwt.Parse(tokenString, fn)
	if err != nil {
		return nil, err //signature is invalid or Token is expired
	}

	//解析存入的jwt信息
	resp := result.Claims.(jwt.MapClaims)

	return resp, nil
}
