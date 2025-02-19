package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/gconv"
	"context"
	"github.com/tal-tech/go-zero/core/logx"
)

type PermsMenusLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewPermsMenusLogic(ctx context.Context, svcCtx *svc.ServiceContext) PermsMenusLogic {
	return PermsMenusLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *PermsMenusLogic) PermsMenus() (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.PermsMenus(l.ctx, &sysclient.PermsMenusReq{
		Id: gconv.Int64(l.ctx.Value("userId")),
	})
	if err != nil {
		return nil, err
	}

	var list []*types.MenuData
	for _, menu := range resp.List {
		list = append(list, &types.MenuData{
			Id:         menu.Id,
			CreateTime: menu.CreateTime,
			UpdateTime: menu.UpdateTime,
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
	return &types.Resp{
		Code: 200,
		Data: gconv.H{
			"menus": list,
			"perms": resp.Perms,
		},
		Msg: "success",
	}, nil
}
