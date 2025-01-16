package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuAddLogic {
	return &MenuAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuAdd 菜单添加
func (l *MenuAddLogic) MenuAdd(in *sys.MenuAddRequest) (*sys.MenuAddResp, error) {
	_, err := l.svcCtx.MenuModel.Insert(sysmodel.SysMenu{
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

	return &sys.MenuAddResp{}, nil
}
