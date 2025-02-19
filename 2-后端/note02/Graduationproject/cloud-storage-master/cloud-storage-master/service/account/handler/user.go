package handler

import (
	"context"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/s"
	"gitee.com/phpdi/cloud-storage/service/account/proto"
	"gitee.com/phpdi/cloud-storage/utils"
)

type User struct {
}

//注册用户
func (u User) Signup(ctx context.Context, req *proto.SignupReq, ack *proto.CommonAck) error {

	item := entitys.User{
		UserName: req.UserName,
	}
	item.Salt = utils.RandString(8)

	item.Password = utils.Md5(req.Password + item.Salt)

	if s.Repeat(&item, "Id", "UserName") {
		ack.Code = entitys.CodeFail
		ack.Message = "账号已被注册"
		return nil
	}

	if err := s.Db.Create(&item).Error; err != nil {
		ack.Code = entitys.CodeFail
		ack.Message = "注册失败"

	}
	return nil
}
