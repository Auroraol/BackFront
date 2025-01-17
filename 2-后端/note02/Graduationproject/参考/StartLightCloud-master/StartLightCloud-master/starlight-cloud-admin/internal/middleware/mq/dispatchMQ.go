/**
 * @Author: FxShadow
 * @Description:用于收发商品的消息队列
 * @Date: 2023/05/07 11:36
 */

package mq

import (
	common2 "system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/service/common"
)

type DispatchMQ struct{}

// 定义1号服务（增加存储空间）
var addRAMChannel = make(chan *common2.Message, 100)

// 定义2号服务（增加星币）
var addSCoinChannel = make(chan *common2.Message, 10)

// PublishAddRAMMQ 发布增加存储空间商品信息
func (dim *DispatchMQ) PublishAddRAMMQ(mqMessage *common2.Message) {
	addRAMChannel <- mqMessage
}

// ConsumeAddRAMMQ 消费增加存储空间商品信息
func (dim *DispatchMQ) ConsumeAddRAMMQ() {
	defer close(addRAMChannel)
	for {
		mqMessage := <-addRAMChannel
		service := common.GetService()
		service.AddUserRAM(mqMessage.Data.UserId, int(mqMessage.Data.ItemValue))
	}
}

// PublishAddSCoinMQ 发布增加星币
func (dim *DispatchMQ) PublishAddSCoinMQ(mqMessage *common2.Message) {
	addSCoinChannel <- mqMessage
}

// ConsumeAddSCoinMQ 消费增加星币
func (dim *DispatchMQ) ConsumeAddSCoinMQ() {
	defer close(addSCoinChannel)
	for {
		mqMessage := <-addSCoinChannel
		service := common.GetService()
		service.AddUserSCoin(mqMessage.Data.UserId, mqMessage.Data.ItemValue)
	}
}
