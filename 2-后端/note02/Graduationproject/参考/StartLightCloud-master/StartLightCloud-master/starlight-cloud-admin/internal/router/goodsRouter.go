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

func GoodsRouter(ginServer *gin.Engine) {
	userGroup := ginServer.Group("/goods")
	goodsController := controller.GetGoodsController()
	{
		userGroup.GET("/getAllGoodsInfo", goodsController.GetAllGoodsInfo)  //获取商品数据
		userGroup.POST("/buyGoodsBySCoin", goodsController.BuyGoodsBySCoin) //购买商品(星币支付)
		userGroup.POST("/buyGoodsByCNY", goodsController.BuyGoodsByCNY)     //购买商品(CNY支付)
		userGroup.POST("/payNotify", goodsController.PayNotify)             //购买商品回调(CNY支付)
	}
	return
}
