/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:21
 */

package response

type MessageResponse struct {
	Code int    `json:"code"` //状态码
	Msg  string `json:"msg"`  //消息
	Data string `json:"data"` //数据
}
