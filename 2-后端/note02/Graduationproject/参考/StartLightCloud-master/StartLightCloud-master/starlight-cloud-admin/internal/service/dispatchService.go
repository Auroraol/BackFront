/**
 * @Author: FxShadow
 * @Description:派遣中间件业务（包装成MQ消息体与管理信息流）
 * @Date: 2023/05/07 12:03
 */

package service

import (
	"fmt"
	MQ "system-admin/starlight-cloud-admin/internal/middleware/mq"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"time"
)

type DispatchService struct{}

type SendGoodsInterface interface {
	SendGoodsToUser(userId string, goodsType int8, count int8) (result int8)
}

// SendGoodsToUser 发送物品（服务）至用户
func (ds *DispatchService) SendGoodsToUser(sendGoodsInfo *common.SendGoodsInfo) (result int8) {

	mqMessage := &common.Message{
		Sender:    sendGoodsInfo.UserId,
		Timestamp: time.Now(),
		Data:      *sendGoodsInfo,
	}

	switch sendGoodsInfo.GoodsType {
	//增加存储空间
	case 1:
		dispatchMQ := &MQ.DispatchMQ{}
		dispatchMQ.PublishAddRAMMQ(mqMessage)

	//星币充值
	case 2:
		dispatchMQ := &MQ.DispatchMQ{}
		dispatchMQ.PublishAddSCoinMQ(mqMessage)

	default:
		fmt.Println("unknown")
	}
	return
}
