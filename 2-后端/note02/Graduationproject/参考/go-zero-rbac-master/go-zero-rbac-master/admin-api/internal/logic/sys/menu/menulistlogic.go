package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuListLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuListLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuListLogic {
	return MenuListLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuListLogic) MenuList() (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.MenuList(l.ctx, &sysclient.MenuListRequest{})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
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
		Data: list,
		Msg:  "success",
	}, nil
}
