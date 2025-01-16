package c

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"github.com/gin-gonic/gin"
)

//编辑文件信息
func FileEdit(this *gin.Context) {
	var (
		req entitys.FileMeta
		err error
	)

	parseForm(this, &req, "Sha1")

	err = s.FileMgrService.Update(req)
	result(this, err)
}

//获取单个文件信息
func File(this *gin.Context) {
	var (
		req entitys.FileMeta
		ack entitys.FileMeta

		err error
	)

	parseForm(this, &req, "Sha1")

	ack, err = s.FileMgrService.FileMeta(req.Sha1)

	result(this, ack, err)
}
