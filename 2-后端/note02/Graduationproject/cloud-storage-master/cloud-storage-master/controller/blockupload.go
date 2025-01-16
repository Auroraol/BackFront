package c

import (
	"errors"
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"github.com/gin-gonic/gin"
	"os"
	"path"
	"strconv"
)

//初始化分块信息
func InitMultipart(this *gin.Context) {
	var (
		req entitys.MultipartUploadInfo
		err error
	)

	parseForm(this, &req, "FileHash", "FileSize")

	err = s.BlockUploadService.InitMultipart(&req)

	result(this, req, err)
}

//分块上传接口
func BlockUpload(this *gin.Context) {
	var (
		req entitys.MultipartUpload
		err error
		fd  *os.File
	)

	if req.UploadId = this.Query("UploadId"); req.UploadId == "" {
		checkErr(this, errors.New("UploadId不能为空"))
	}

	if req.Index, _ = strconv.Atoi(this.Query("Index")); req.Index == 0 {
		checkErr(this, errors.New("Index不能为空"))
	}

	filePath := fmt.Sprintf(entitys.MultipartUploadFilePath, req.UploadId, req.Index)
	filePath = path.Dir(filePath)
	err = os.MkdirAll(filePath, 0777)
	checkErr(this, err)

	fd, err = os.Create(fmt.Sprintf(entitys.MultipartUploadFilePath, req.UploadId, req.Index))
	checkErr(this, err)

	defer fd.Close()

	buf := make([]byte, 1024*1024)
	for {
		n, err := this.Request.Body.Read(buf)
		fd.Write(buf[:n])
		if err != nil {
			break
		}
	}

	//存储分块上传进度
	err = s.RedisService.DoWithCheck("HSET",
		fmt.Sprintf(entitys.MultipartUploadCachePrefix, req.UploadId),
		fmt.Sprintf(entitys.MultipartUploadChunkIndex+"%d", req.Index),
		req.Index)
	checkErr(this, err)

	result(this)
}

//完成分块上传接口
func CompleteUpload(this *gin.Context) {

	var (
		req  entitys.MultipartUpload
		user entitys.User
		err  error
	)
	parseForm(this, &req, "UploadId")

	user, err = authUser(this)
	checkErr(this, err)

	err = s.BlockUploadService.CompleteUpload(req.UploadId, &user)
	checkErr(this, err)

	result(this)
}

//取消上传分块
func CancelUpload(this *gin.Context) {

	var (
		req entitys.MultipartUpload
		err error
	)
	parseForm(this, &req, "UploadId")

	err = s.BlockUploadService.CancelUpload(req.UploadId)
	checkErr(this, err)

	result(this)
}

//获取分块上传整体状态
func MultipartUploadStatus(this *gin.Context) {

	var (
		req entitys.MultipartUpload
		ack entitys.MultipartUploadInfo
		err error
	)
	parseForm(this, &req, "UploadId")

	ack, err = s.BlockUploadService.MultipartUploadStatus(req.UploadId)
	checkErr(this, err)

	result(this, ack)
}
