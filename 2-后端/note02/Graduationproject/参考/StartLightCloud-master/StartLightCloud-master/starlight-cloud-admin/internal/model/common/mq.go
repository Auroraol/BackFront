/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/07 15:57
 */

package common

import (
	"time"
)

type Message struct {
	Sender    string        // 消息发送者UserId
	Timestamp time.Time     // 消息发送时间
	Data      SendGoodsInfo // 实际的消息数据
}
