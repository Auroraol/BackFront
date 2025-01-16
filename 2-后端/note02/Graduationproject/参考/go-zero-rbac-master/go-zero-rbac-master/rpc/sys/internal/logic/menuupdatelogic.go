package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuUpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuUpdateLogic {
	return &MenuUpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuUpdate 菜单更新
func (l *MenuUpdateLogic) MenuUpdate(in *sys.MenuUpdateRequest) (*sys.MenuUpdateResp, error) {
	err := l.svcCtx.MenuModel.Update(sysmodel.SysMenu{
		Id:        in.Id,
		ParentId:  in.ParentId,
		Name:      in.Name,
		Router:    in.Router,
		Perms:     in.Perms,
		Type:      in.Type,
		Icon:      in.Icon,
		OrderNum:  in.OrderNum,
		ViewPath:  in.ViewPath,
		KeepAlive: in.KeepAlive,
		IsShow:    in.IsShow,
	})
	if err != nil {
		return nil, err
	}

	return &sys.MenuUpdateResp{}, nil
}
