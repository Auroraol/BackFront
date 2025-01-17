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

type FileController struct{}

func GetFileController() (fileController FileController) {
	return fileController
}

// UpLoadFile 文件上传
func (fc *FileController) UpLoadFile(c *gin.Context) {
	fileService := service.GetFileService()
	message := fileService.UploadFile(c)

	c.JSON(200, message)
}

// GetFileList 获取文件列表
func (fc *FileController) GetFileList(c *gin.Context) {
	fileService := service.GetFileService()
	message := fileService.GetFileList(c)

	c.JSON(200, message)
}

// DeleteFile 删除文件
func (fc *FileController) DeleteFile(c *gin.Context) {
	fileService := service.GetFileService()
	message := fileService.DeleteFile(c)

	c.JSON(200, message)
}

// DownloadFile 下载文件
func (fc *FileController) DownloadFile(c *gin.Context) {
	fileService := service.GetFileService()
	fileService.DownloadFile(c)
}

// CreateFolder 新建文件夹
func (fc *FileController) CreateFolder(c *gin.Context) {
	fileService := service.GetFileService()
	message := fileService.CreateFolder(c)

	c.JSON(200, message)
}

// SearchFile文件
func (fc *FileController) SearchFileList(c *gin.Context) {
	fileService := service.GetFileService()
	message := fileService.SearchFileList(c)

	c.JSON(200, message)
}
