/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 0:53
 */

package router

import (
	"github.com/gin-gonic/gin"
	"system-admin/starlight-cloud-admin/internal/controller"
)

func UserRouter(ginServer *gin.Engine) {
	userGroup := ginServer.Group("/user")
	userController := controller.GetUserController()
	{
		userGroup.POST("/login", LimitRequestCountHandle(), userController.UserLogin)                            //用户登录
		userGroup.POST("/register", LimitRequestCountHandle(), userController.UserRegister)                      //用户注册
		userGroup.GET("/getUserInfo", userController.GetUserInfo)                                                //获取用户信息
		userGroup.POST("/uploadProfile", LimitRequestCountHandle(), userController.UploadProfile)                //上传头像
		userGroup.GET("/getProfile", userController.GetProfile)                                                  //获取用户头像
		userGroup.GET("/getRegisterVerifyCode", LimitRequestCountHandle(), userController.GetRegisterVerifyCode) //获取邮箱注册验证码
	}
	return
}
