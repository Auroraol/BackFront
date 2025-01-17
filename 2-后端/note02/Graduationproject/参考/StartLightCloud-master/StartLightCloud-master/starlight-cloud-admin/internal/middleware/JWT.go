/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 20:07
 */

package MiddleWare

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"strings"
	"system-admin/starlight-cloud-admin/internal/util"
)

func JWTAuthMiddleware() func(c *gin.Context) {
	fmt.Println("JWT...")
	return func(c *gin.Context) {
		// 客户端携带Token有三种方式 1.放在请求头 2.放在请求体 3.放在URI
		// 这里假设Token放在Header的Authorization中，并使用Bearer开头
		// 这里的具体实现方式要依据你的实际业务情况决定
		//authHeader := c.Request.Header.Get("Authorization")
		authHeader := c.GetHeader("Authorization")
		if authHeader == "" {
			c.JSON(http.StatusOK, gin.H{
				"code": 2003,
				"msg":  "请求头中auth为空",
			})
			c.Abort()
			return
		}
		// 按空格划分为数组，最后提取有效部分
		parts := strings.SplitN(authHeader, " ", 2)
		if !(len(parts) == 2 && parts[0] == "Bearer") {
			c.JSON(http.StatusOK, gin.H{
				"code": 2004,
				"msg":  "请求头中auth格式有误",
			})
			c.Abort()
			return
		}
		// parts[1]是获取到的tokenString，我使用之前定义好的解析JWT的函数来解析它
		mc, err := util.DecipherJWT(parts[1])
		if err != nil {
			c.JSON(http.StatusOK, gin.H{
				"code": 2005,
				"msg":  "无效的Token",
			})
			c.Abort()
			return
		}

		//接下来这一步应该去数据库里面验证操作
		//如果验证失败说明这个token无效
		//如果验证成功将当前请求的username信息保存到请求的上下文c上
		c.Set("username", mc.UserId)
		c.Next() // 后续的处理函数可以用过c.Get("username")来获取当前请求的用户信息

		fmt.Println("通过")
	}
}
