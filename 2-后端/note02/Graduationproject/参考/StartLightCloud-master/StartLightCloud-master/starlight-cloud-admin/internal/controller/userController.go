/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/15 23:48
 */

package controller

import (
	"github.com/gin-gonic/gin"
	"system-admin/starlight-cloud-admin/internal/service"
)

type UserController struct{}

func GetUserController() (userController UserController) {
	return userController
}

func (uc *UserController) UserLogin(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.UserLogin(c)

	c.JSON(200, message)
}

func (uc *UserController) UserRegister(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.UserRegister(c)

	c.JSON(200, message)
}

func (uc *UserController) GetUserInfo(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.GetUserInfo(c)

	c.JSON(200, message)
}

func (uc *UserController) UploadProfile(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.UploadProfile(c)

	c.JSON(200, message)
}

func (uc *UserController) GetProfile(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.GetProfile(c)

	c.JSON(200, message)
}

func (uc *UserController) GetRegisterVerifyCode(c *gin.Context) {

	userService := service.GetUserService()
	message := userService.GetRegisterVerifyCode(c)

	c.JSON(200, message)
}
