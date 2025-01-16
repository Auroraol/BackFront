package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuClearLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuClearLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuClearLogic {
	return &MenuClearLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuClear 菜单清空
func (l *MenuClearLogic) MenuClear(in *wechat.MenuClearReq) (*wechat.MenuClearResp, error) {
	err := l.svcCtx.MenuModel.Clear()
	if err != nil {
		return nil, err
	}

	return &wechat.MenuClearResp{}, nil
}
