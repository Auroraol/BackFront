package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"
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
	resp, err := l.svcCtx.Wechat.MenuList(l.ctx, &wechatclient.MenuListReq{
		Id: 0,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	var list []*types.WechatMenuData
	for i, data := range resp.List {
		list = append(list, &types.WechatMenuData{
			Type:     data.Type,
			Name:     data.Name,
			URL:      data.URL,
			MediaID:  data.MediaID,
			MsgType:  data.MsgType,
			AppID:    data.AppID,
			PagePath: data.PagePath,
			Text:     data.Text,
			Pic:      data.Pic,
		})
		if len(data.SubButton) > 0 {
			for _, c := range data.SubButton {
				// 将此菜单插入到结构体内
				list[i].SubButtons = append(list[i].SubButtons, &types.WechatMenuData{
					Type:     c.Type,
					Name:     c.Name,
					URL:      c.URL,
					MediaID:  c.MediaID,
					MsgType:  c.MsgType,
					AppID:    c.AppID,
					PagePath: c.PagePath,
					Text:     c.Text,
					Pic:      c.Pic,
				})
			}
		} else {
			list[i].SubButtons = make([]*types.WechatMenuData, 0)
		}
	}
	// 解决无数据返回null
	if list == nil {
		list = make([]*types.WechatMenuData, 0)
	}
	return &types.Resp{
		Code: 200,
		Data: list,
		Msg:  "success",
	}, nil
}
