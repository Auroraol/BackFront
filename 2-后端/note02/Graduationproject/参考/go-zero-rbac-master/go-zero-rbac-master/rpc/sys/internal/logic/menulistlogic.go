package logic

import (
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuListLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuListLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuListLogic {
	return &MenuListLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuList 菜单列表
func (l *MenuListLogic) MenuList(in *sys.MenuListRequest) (*sys.MenuListResp, error) {
	resp, err := l.svcCtx.MenuModel.FindAll()

	if err != nil {
		return nil, err
	}
	var list []*sys.MenuListData
	for _, menu := range *resp {
		list = append(list, &sys.MenuListData{
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
	return &sys.MenuListResp{
		List: list,
	}, nil
}
