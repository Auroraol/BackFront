/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/22 15:55
 */

package controller

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"strings"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type JWTController struct{}

func GetJWTController() (jwtController JWTController) {
	return jwtController
}

// 鉴权token是否有效
func (jc *JWTController) CheckJWTHandler() gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Request.Header.Get("Authorization")
		newToken := strings.Replace(token, "Bearer ", "", 1)
		result, err := util.DecipherJWT(newToken)
		if err != nil {
			fmt.Println(err)
		}

		if result == nil {
			c.JSON(util.TokenInvalid, response.MessageResponse{
				Code: util.TokenInvalid,
				Msg:  "token已失效",
				Data: "null",
			})
			c.Abort()
		} else {
			c.Next()
		}
	}
}
