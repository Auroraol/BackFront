package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"context"
	"fmt"
	"github.com/silenceper/wechat/v2/officialaccount/menu"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuAddLogic {
	return MenuAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuAddLogic) MenuAdd(req types.WechatMenuAddReq) (*types.Resp, error) {
	// 先清空数据
	_, err := l.svcCtx.Wechat.MenuClear(l.ctx, &wechatclient.MenuClearReq{})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	for _, item := range req.Data {
		add, err := l.svcCtx.Wechat.MenuAdd(l.ctx, &wechatclient.MenuAddReq{
			Type:       item.Type,
			Name:       item.Name,
			URL:        item.URL,
			MediaID:    item.MediaID,
			MsgType:    item.MsgType,
			AppID:      item.AppID,
			PagePath:   item.PagePath,
			CreateTime: item.Pic,
			UpdateTime: item.Text,
			Text:       item.Text,
			Pic:        item.Pic,
		})
		if err != nil {
			return nil, errorx.NewDefaultError(err.Error())
		}
		// 如果存在下级
		if item.SubButtons != nil {
			pid := add.Id
			for _, child := range item.SubButtons {
				_, err := l.svcCtx.Wechat.MenuAdd(l.ctx, &wechatclient.MenuAddReq{
					Pid:      pid,
					Type:     child.Type,
					Name:     child.Name,
					URL:      child.URL,
					MediaID:  child.MediaID,
					MsgType:  child.MsgType,
					AppID:    child.AppID,
					PagePath: child.PagePath,
					Text:     child.Text,
					Pic:      child.Pic,
				})
				if err != nil {
					return nil, errorx.NewDefaultError(err.Error())
				}
			}
		}
	}
	err = l.addWechat()
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	return &types.Resp{
		Code: 200,
		Data: nil,
		Msg:  "success",
	}, nil
}

// 同步给到微信
func (l *MenuAddLogic) addWechat() error {
	tree, err := l.wechatMenuTree(0)
	if err != nil {
		return err
	}
	// 初始化微信sdk
	var cfg map[string]string
	info, err := l.svcCtx.Wechat.Info(l.ctx, &wechatclient.WechatInfoRequest{
		Type: 1,
	})
	if err != nil {
		return err
	}
	cfg = gconv.JsonToMap(info.Value)
	ex := l.svcCtx.WechatSdk.Register(cfg).Wechat.GetMenu()
	fmt.Println("开始同步微信")
	err = ex.SetMenu(tree)
	if err != nil {
		logx.Error("同步微信出错：", err.Error())
		return err
	}
	fmt.Println("同步微信成功")
	return nil
}

// 将数据库数据查询出来赋值给到sdk结构体
func (l *MenuAddLogic) wechatMenuTree(pid int64) ([]*menu.Button, error) {
	info, err := l.svcCtx.Wechat.MenuList(l.ctx, &wechatclient.MenuListReq{
		Id: pid,
	})
	if err != nil {
		return nil, err
	}
	tree := make([]*menu.Button, 0)
	for _, v := range info.List {
		child, _ := l.wechatMenuTree(v.Id)
		node := &menu.Button{
			Type:     v.Type,
			Name:     v.Name,
			Key:      gconv.String(v.Id),
			URL:      v.URL,
			MediaID:  gconv.String(v.MediaID),
			AppID:    v.AppID,
			PagePath: v.PagePath,
		}
		node.SubButtons = child
		tree = append(tree, node)
	}
	return tree, nil
}
