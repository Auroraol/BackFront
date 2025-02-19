package c

import (
	"errors"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"gitee.com/phpdi/cloud-storage/utils"
	"github.com/gin-gonic/gin"
	"io/ioutil"
	"mime/multipart"
)

//登录
func Login(this *gin.Context) {
	var (
		req s.Auth
		err error

		user entitys.User

		token string
	)

	parseForm(this, &req, "UserName", "Password")

	user, err = s.AuthService.Auth(req)
	checkErr(this, err)

	token, err = s.AuthService.CreateToken(user.Id)
	result(this, token, err)
}

//文件上传接口
func Upload(this *gin.Context) {
	var (
		err      error
		file     multipart.File
		fileMeta entitys.FileMeta
		user     entitys.User
	)

	//解析表单
	checkErr(this, this.Request.ParseMultipartForm(50*1024*1024))

	//获取文件
	file, fileHeader, err := this.Request.FormFile("file")
	checkErr(this, err)

	//检查文件格式
	checkErr(this, utils.CheckFile(fileHeader, 5*1024, []string{}))

	//文件基本信息
	fileMeta.Size = fileHeader.Size
	fileMeta.Name = fileHeader.Filename

	user, err = authUser(this)
	checkErr(this, err)

	//文件hash
	fileMeta.Sha1, err = utils.FileSha1(file)
	checkErr(this, err)

	//存储文件
	//因为前面utils.FileSha1函数已经吧Reader中的内容读走了，所以这里需要重新打开文件
	file, err = fileHeader.Open()
	checkErr(this, err)
	defer file.Close()

	checkErr(this, s.FileMgrService.SaveFile(fileMeta, file, &user))

	result(this)
}

//秒传文件接口
func FastUpload(this *gin.Context) {
	var (
		err error
		req entitys.FileMeta
	)

	parseForm(this, &req, "Sha1", "Name", "Size")

	//验证文件是否已经存在
	if !s.FileMgrService.FileExist(req.Sha1) {
		checkErr(this, errors.New("file not found"))
	}

	//保存用户与文件关系
	authUser, err := authUser(this)
	checkErr(this, err)

	userFile := entitys.UserFile{
		UserId:   authUser.Id,
		FileSha1: req.Sha1,
		FileSize: req.Size,
		FileName: req.Name,
		Status:   entitys.UserFileStatusEnable,
	}

	checkErr(this, s.UserService.AddUserFile(userFile, s.NewTransactor()))

	result(this)
}

//下载文件
func Download(this *gin.Context) {
	var (
		req     entitys.FileMeta
		err     error
		content []byte
	)

	parseForm(this, &req, "Sha1")

	if req, err = s.FileMgrService.FileMeta(req.Sha1); err != nil {
		checkErr(this, errors.New("文件不存在"))
	}

	//将文件内容读取出来
	content, err = ioutil.ReadFile(req.Addr)
	checkErr(this, err)

	//输出文件
	downloadStream(this, content, req.Name)
}
