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

func OrderRouter(ginServer *gin.Engine) {
	userGroup := ginServer.Group("/order")
	goodsController := controller.GetOrderController()
	{
		userGroup.GET("/getUserOrderList", goodsController.GetUserOrderList)               //获取用户所有订单数据
		userGroup.POST("/cancelOrder", goodsController.CancelOrder)                        //取消用户订单
		userGroup.GET("/getOrderPayUrlFromRedis", goodsController.GetOrderPayUrlFromRedis) //从redis中获取订单的支付链接
	}
	return
}
