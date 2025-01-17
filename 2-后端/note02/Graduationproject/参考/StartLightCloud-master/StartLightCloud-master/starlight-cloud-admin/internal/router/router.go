/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 0:47
 */

package router

import (
	"github.com/gin-gonic/gin"
	"system-admin/starlight-cloud-admin/internal/controller"
	"system-admin/starlight-cloud-admin/internal/middleware"
	"system-admin/starlight-cloud-admin/internal/util"
)

func RunRouter() {
	//1.创建一个引擎服务
	router := gin.Default()

	//加载跨域
	router.Use(MiddleWare.Cors())

	//测试接口（仅供获取支付宝回调，免费内网穿透不支持路由）
	goodsController := controller.GetGoodsController()
	router.POST("/", goodsController.PayNotify)

	//加载用户路由模块
	UserRouter(router)
	//加载文件流路由模块
	FileRouter(router)
	//加载商品路由模块
	GoodsRouter(router)
	//加载订单路由模块
	OrderRouter(router)

	//3.服务器端口
	router.Run(":8080")

	//关闭redis连接
	defer util.CloseRedis()
}
