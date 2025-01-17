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

func FileRouter(ginServer *gin.Engine) {
	fileGroup := ginServer.Group("/file")
	fileController := controller.GetFileController()
	jwtController := controller.GetJWTController()
	{
		fileGroup.POST("/upload", jwtController.CheckJWTHandler(), fileController.UpLoadFile)               //上传文件
		fileGroup.GET("/getFileList", jwtController.CheckJWTHandler(), fileController.GetFileList)          //获取文件列表
		fileGroup.POST("/deleteFile", jwtController.CheckJWTHandler(), fileController.DeleteFile)           //删除文件
		fileGroup.POST("/downloadFile", jwtController.CheckJWTHandler(), fileController.DownloadFile)       //下载文件
		fileGroup.POST("/createFolder", jwtController.CheckJWTHandler(), fileController.CreateFolder)       //新建文件夹
		fileGroup.GET("/getSearchFileList", jwtController.CheckJWTHandler(), fileController.SearchFileList) //获取查询文件列表
	}
	return
}
