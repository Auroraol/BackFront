package c

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"github.com/gin-gonic/gin"
)

//创建用户
func UserCreate(this *gin.Context) {
	var (
		req entitys.User
		err error
	)

	parseForm(this, &req, "UserName", "Password")

	err = s.UserService.Create(req)
	result(this, err)
}

//编辑用户
func UserUpdate(this *gin.Context) {
	var (
		req entitys.User
		err error
	)

	//禁止编辑字段
	req.Password = ""
	req.IsSuper = 0

	parseForm(this, &req, "Id")

	err = s.UserService.Update(req)
	result(this, err)
}

//删除用户
func UserDel(this *gin.Context) {
	var (
		req entitys.User
		err error
	)
	parseForm(this, &req, "Id")

	err = s.UserService.Del(req.Id)
	result(this, err)
}

//获取单个用户信息
func User(this *gin.Context) {
	var (
		req entitys.User
		err error
	)
	parseForm(this, &req, "Id")

	req, err = s.UserService.User(req.Id)
	result(this, req, err)
}

//批量获取用户信息
func Users(this *gin.Context) {
	var (
		ack []entitys.User
		err error
	)

	ack, err = s.UserService.Users()
	result(this, ack, err)
}

//获取用户文件列表
func UserFiles(this *gin.Context) {
	var (
		req  entitys.UserFilesReq
		ack  entitys.UserFilesAck
		user entitys.User
		err  error
	)

	parseForm(this, &req)

	//当前用户
	user, err = authUser(this)
	checkErr(this, err)
	req.UserId = user.Id

	ack, err = s.UserService.UserFiles(req)
	result(this, ack, err)
}
