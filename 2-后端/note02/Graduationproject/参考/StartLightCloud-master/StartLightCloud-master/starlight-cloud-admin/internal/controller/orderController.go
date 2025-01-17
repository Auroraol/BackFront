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

type OrderController struct{}

func GetOrderController() (orderController OrderController) {
	return orderController
}

func (gc *OrderController) GetUserOrderList(c *gin.Context) {

	orderService := service.GetOrderService()
	message := orderService.GetUserOrderList(c)

	c.JSON(200, message)
}

func (gc *OrderController) CancelOrder(c *gin.Context) {

	orderService := service.GetOrderService()
	message := orderService.CancelOrder(c)

	c.JSON(200, message)
}

func (gc *OrderController) GetOrderPayUrlFromRedis(c *gin.Context) {

	orderService := service.GetOrderService()
	message := orderService.GetOrderPayUrlFromRedis(c)

	c.JSON(200, message)
}
