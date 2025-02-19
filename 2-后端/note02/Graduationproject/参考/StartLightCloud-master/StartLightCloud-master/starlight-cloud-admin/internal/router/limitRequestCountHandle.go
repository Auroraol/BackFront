/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/06/27 18:12
 */

package router

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/gomodule/redigo/redis"
	"strconv"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

var (
	MaxTime  = 5
	MaxCount = 3
)

// LimitRequestCountHandle 接口频率限制（3秒内不能超过5次）
func LimitRequestCountHandle() gin.HandlerFunc {
	return func(c *gin.Context) {
		conn := util.GetRedis()
		defer conn.Close()
		//检查用户访问次数是否超过5次
		//获取当前IP的访问次数
		remoteAddr := c.ClientIP()
		//如果为本地IP则处理一下
		if remoteAddr == "::1" {
			remoteAddr = "127.0.0.1"
		}
		key := "l:ip:" + remoteAddr

		count, _ := redis.String(conn.Do("get", key))

		//如果获取不到则添加进去
		if count == "" {
			OK, _ := conn.Do("SETEX", key, MaxTime, 1)

			if OK != "OK" {
				message := response.MessageResponse{
					Code: util.LimitRequestCountUnusual,
					Msg:  "接口频率限制异常",
					Data: "null",
				}
				c.JSON(200, message)
				c.Abort()
			}
			fmt.Println("没获取到，已添加进去：", key)
			c.Next()

		} else {
			//接口次数+1
			newCount, _ := strconv.Atoi(count)
			newCount++

			fmt.Println("接口次数准备+1: ", key)

			//获取键的剩余生存时间
			ttl, err := redis.Int(conn.Do("TTL", key))
			if err != nil {
				fmt.Println("Failed to get TTL:", err)
				message := response.MessageResponse{
					Code: util.LimitRequestCountUnusual,
					Msg:  "接口频率限制异常",
					Data: "null",
				}
				c.JSON(200, message)
				c.Abort()
			}

			//如果超过次数限制则禁止访问
			if newCount > MaxCount {
				fmt.Println("超了哥------------------", newCount)
				message := response.MessageResponse{
					Code: util.LimitRequestCountMAX,
					Msg:  "接口频率限制已超出",
					Data: "null",
				}
				c.JSON(200, message)
				c.Abort()
			}

			switch ttl {
			case -1:
				fallthrough
			case -2:
				message := response.MessageResponse{
					Code: util.LimitRequestCountUnusual,
					Msg:  "接口频率限制异常",
					Data: "null",
				}
				c.JSON(200, message)
				c.Abort()
			default:

				OK, _ := conn.Do("SETEX", key, ttl, newCount)
				if OK != "OK" {
					message := response.MessageResponse{
						Code: util.LimitRequestCountUnusual,
						Msg:  "接口频率限制异常",
						Data: "null",
					}
					c.JSON(200, message)
					c.Abort()
				} else {
					message := response.MessageResponse{
						Code: util.LimitRequestCountOK,
						Msg:  "接口频率限制通过",
						Data: "null",
					}
					c.Set("message", message)
					c.Next()
				}
			}
		}
	}
}
