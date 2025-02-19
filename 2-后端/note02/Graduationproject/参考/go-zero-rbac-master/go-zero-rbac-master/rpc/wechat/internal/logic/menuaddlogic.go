package logic

import (
	"aso/rpc/model/wechatmodel"
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"
	"aso/utils/gconv"
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
func (l *MenuAddLogic) MenuAdd(in *wechat.MenuAddReq) (*wechat.MenuAddResp, error) {
	resp, err := l.svcCtx.MenuModel.Insert(wechatmodel.WechatMenu{
		Id:       in.Id,
		Pid:      in.Pid,
		Type:     in.Type,
		Name:     in.Name,
		Url:      in.URL,
		MediaId:  in.MediaID,
		AppId:    in.AppID,
		PagePath: in.PagePath,
		MsgType:  in.MsgType,
		Text:     in.Text,
		Pic:      in.Pic,
		Audio:    in.Type,
		Video:    in.Type,
	})
	if err != nil {
		return nil, err
	}
	id, _ := resp.LastInsertId()
	return &wechat.MenuAddResp{
		Id: gconv.Int64(id),
	}, nil
}
