package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type DeleteUpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewDeleteUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeleteUpdateLogic {
	return &DeleteUpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

//  DeleteUpdate 部门更新
func (l *DeleteUpdateLogic) DeleteUpdate(in *sys.DeptUpdateRequest) (*sys.DeptUpdateResp, error) {
	// todo: add your logic here and delete this line

	return &sys.DeptUpdateResp{}, nil
}
