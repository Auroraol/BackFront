package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type PermsMenusLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewPermsMenusLogic(ctx context.Context, svcCtx *svc.ServiceContext) *PermsMenusLogic {
	return &PermsMenusLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// PermsMenus 权限菜单
func (l *PermsMenusLogic) PermsMenus(in *sys.PermsMenusReq) (*sys.PermsMenusResp, error) {
	resp, perms, err := l.svcCtx.MenuModel.PermsMenusFind(in.Id)

	if err != nil {
		return nil, err
	}
	var list []*sys.PermsMenusData
	for _, menu := range *resp {
		list = append(list, &sys.PermsMenusData{
			Id:         menu.Id,
			CreateTime: menu.CreateTime.Format("2006-01-02 15:04:05"),
			UpdateTime: menu.UpdateTime.Format("2006-01-02 15:04:05"),
			ParentId:   menu.ParentId,
			Name:       menu.Name,
			Router:     menu.Router,
			Perms:      menu.Perms,
			Type:       menu.Type,
			Icon:       menu.Icon,
			OrderNum:   menu.OrderNum,
			ViewPath:   menu.ViewPath,
			KeepAlive:  menu.KeepAlive,
			IsShow:     menu.IsShow,
		})
	}
	return &sys.PermsMenusResp{
		List:  list,
		Perms: perms,
	}, nil
}
