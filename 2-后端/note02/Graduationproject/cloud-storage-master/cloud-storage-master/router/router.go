package router

import (
	c "gitee.com/phpdi/cloud-storage/controller"
	"github.com/gin-gonic/gin"
)

//路由设置
func Router() *gin.Engine {
	router := gin.Default()

	//静态资源处理
	router.Static("/static", "./static")

	//错误处理
	router.Use(c.ErrHandler()).
		Use(c.Cors()) //跨域

	//登录
	router.POST("/login", c.Login)

	//需要认证的路由组
	authorized := router.Group("/", c.AuthRequired())
	{
		authorized.
			//文件模块
			POST("/file", c.File).          //文件信息
			POST("/file/edit", c.FileEdit). //文件编辑

			//基础模块
			POST("/upload", c.Upload).         //文件上传
			POST("/download", c.Download).     //文件下载
			POST("/fastupload", c.FastUpload). //文件秒传接口

			//用户模块
			POST("/user/create", c.UserCreate). //用户添加
			POST("/user/update", c.UserUpdate). //用户编辑
			POST("/user/del", c.UserDel).       //用户删除
			POST("/user", c.User).              //获取单个用户
			GET("/users", c.Users).             //获取多个用户
			POST("/userfiles", c.UserFiles).    //获取用户文件

			//分块上传
			POST("/initmultipart", c.InitMultipart).                //初始化分块接口
			POST("/blockupload", c.BlockUpload).                    //上传分块
			POST("/completeupload", c.CompleteUpload).              //完成分块上传接口
			POST("/cancelupload", c.CancelUpload).                  //取消上传分块
			POST("/multipartuploadstatus", c.MultipartUploadStatus) //获取分块上传整体状态

	}

	return router

}
