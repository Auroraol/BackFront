/**
 * @Author: FxShadow
 * @Description:商品模型
 * @Date: 2023/03/15 22:31
 */

package request

type GoodsRequest struct {
	UserId  string `gorm:"user_id" json:"user_id" form:"userId"`    //用户id
	GoodsId string `gorm:"goods_id" json:"goods_id" form:"goodsId"` //商品id
}
