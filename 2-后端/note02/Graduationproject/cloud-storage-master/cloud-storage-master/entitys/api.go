package entitys

//接口返回
type Response struct {
	Code int         //错误码
	Msg  string      //错误消息
	Data interface{} //输出数据
}

const (
	CodeSuccess = 0
	CodeFail    = 1 //失败错误码
)
