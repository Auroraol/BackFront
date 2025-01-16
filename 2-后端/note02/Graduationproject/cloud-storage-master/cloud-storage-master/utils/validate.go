package utils

import "github.com/phpdi/ant/validation"

//数据验证函数
func NewValidate()*validation.Validation  {
	this:=validation.NewValidation()
	return this
}