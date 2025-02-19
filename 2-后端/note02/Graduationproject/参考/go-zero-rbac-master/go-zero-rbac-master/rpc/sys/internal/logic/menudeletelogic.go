package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuDeleteLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuDeleteLogic {
	return &MenuDeleteLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuDelete 菜单删除
func (l *MenuDeleteLogic) MenuDelete(in *sys.MenuDeleteRequest) (*sys.MenuDeleteResp, error) {
	err := l.svcCtx.MenuModel.Delete(in.Ids)

	if err != nil {
		return nil, err
	}
	// todo 删除后同时删除role菜单表
	return &sys.MenuDeleteResp{}, nil
}
