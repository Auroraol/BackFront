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

type GoodsController struct{}

func GetGoodsController() (goodsController GoodsController) {
	return goodsController
}

func (gc *GoodsController) GetAllGoodsInfo(c *gin.Context) {

	goodsService := service.GetGoodsService()
	message := goodsService.GetAllGoodsInfo(c)

	c.JSON(200, message)
}

func (gc *GoodsController) BuyGoodsBySCoin(c *gin.Context) {

	goodsService := service.GetGoodsService()
	message := goodsService.BuyGoodsBySCoin(c)

	c.JSON(200, message)
}

func (gc *GoodsController) BuyGoodsByCNY(c *gin.Context) {

	goodsService := service.GetGoodsService()
	goodsService.BuyGoodsByCNY(c)

	//c.JSON(200, message)
}

func (gc *GoodsController) PayNotify(c *gin.Context) {

	goodsService := service.GetGoodsService()
	message := goodsService.PayNotify(c)

	c.JSON(200, message)
}
