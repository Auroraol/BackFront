package logic

import (
	"context"
	"fmt"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserDeleteLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserDeleteLogic {
	return &UserDeleteLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

//  删除用户
func (l *UserDeleteLogic) UserDelete(in *sys.UserDeleteReq) (*sys.UserDeleteResp, error) {
	for i, _ := range in.Ids {
		fmt.Println(i)
	}
	//l.svcCtx.UserModel.Delete(in.)
	return &sys.UserDeleteResp{}, nil
}
