package errorx

import "errors"

const defaultCode = 400

var (
	ErrNotFound               = errors.New("data not find")
	ErrorUserNotFound         = errors.New("用户不存在")
	ErrorNoRequiredParameters = errors.New("必要参数不能为空")
	ErrorUserOperation        = errors.New("用户正在操作中，请稍后重试")
)

type CodeError struct {
	Code int    `json:"code"`
	Msg  string `json:"msg"`
}

type CodeErrorResponse struct {
	Code int    `json:"code"`
	Msg  string `json:"msg"`
}

func NewCodeError(code int, msg string) error {
	return &CodeError{Code: code, Msg: msg}
}

func NewDefaultError(msg string) error {
	return NewCodeError(defaultCode, msg)
}

func (e *CodeError) Error() string {
	return e.Msg
}

func (e *CodeError) Data() *CodeErrorResponse {
	return &CodeErrorResponse{
		Code: e.Code,
		Msg:  e.Msg,
	}
}
