package c

import (
	"errors"
	"fmt"
	s "gitee.com/phpdi/cloud-storage/s"
	"github.com/gin-gonic/gin"
	"net/http"
)

//错误处理中间件
func ErrHandler() gin.HandlerFunc {
	return func(this *gin.Context) {
		defer func() {
			//用户主动推出
			if err := recover(); err == StopFunc {
				return
			}
		}()
		this.Next()
	}
}

//登陆认证中间件
func AuthRequired() gin.HandlerFunc {
	return func(this *gin.Context) {
		token := this.Request.Header.Get("Token")
		if token == "" {
			token = this.Query("Token")
			fmt.Println("toekn:", token)
		}

		if _, err := s.AuthService.AuthToken(token); err != nil {
			checkErr(this, errors.New("token认证失败"))
		}

		this.Next()
	}
}

// 处理跨域请求,支持options访问
func Cors() gin.HandlerFunc {
	return func(this *gin.Context) {
		method := this.Request.Method
		this.Header("Access-Control-Allow-Origin", "*")
		this.Header("Access-Control-Allow-Headers", "Content-Type,AccessToken,X-CSRF-Token, Authorization, Token")
		this.Header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, PATCH, DELETE")
		this.Header("Access-Control-Expose-Headers", "Content-Length, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Content-Type")
		this.Header("Access-Control-Allow-Credentials", "true")

		// 放行所有OPTIONS方法，因为有的模板是要请求两次的
		if method == "OPTIONS" {
			this.AbortWithStatus(http.StatusNoContent)
		}

		// 处理请求
		this.Next()
	}
}
