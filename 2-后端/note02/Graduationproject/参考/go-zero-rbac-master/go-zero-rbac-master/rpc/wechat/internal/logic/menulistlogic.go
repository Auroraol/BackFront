package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

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
func (l *MenuListLogic) MenuList(in *wechat.MenuListReq) (*wechat.MenuListResp, error) {
	list, err := l.GetMenus(in.Id)
	if err != nil {
		return nil, err
	}
	return &wechat.MenuListResp{
		List: list,
	}, nil
}

func (l *MenuListLogic) GetMenus(pid int64) ([]*wechat.MenuListData, error) {
	m, err := l.svcCtx.MenuModel.FindList(pid)
	if err != nil {
		return nil, err
	}
	tree := make([]*wechat.MenuListData, 0)
	for _, menu := range *m {
		child, _ := l.GetMenus(menu.Id)
		node := &wechat.MenuListData{
			Id:         menu.Id,
			Pid:        menu.Pid,
			Type:       menu.Type,
			Name:       menu.Name,
			URL:        menu.Url,
			MediaID:    menu.MediaId,
			MsgType:    menu.MsgType,
			AppID:      menu.AppId,
			PagePath:   menu.PagePath,
			CreateTime: menu.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: menu.UpdateTime.Format("2006-04-02 15:04:05"),
			Text:       menu.Text,
			Pic:        menu.Pic,
		}
		node.SubButton = child
		tree = append(tree, node)
	}
	return tree, nil
}
