package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuInfoLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMenuInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MenuInfoLogic {
	return &MenuInfoLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MenuInfo 菜单信息
func (l *MenuInfoLogic) MenuInfo(in *wechat.MenuInfoReq) (*wechat.MenuInfoResp, error) {
	resp, err := l.svcCtx.MenuModel.FindOne(in.Id, "id")
	if err != nil {
		return nil, err
	}
	return &wechat.MenuInfoResp{
		Id:         resp.Id,
		Pid:        resp.Pid,
		Type:       resp.Type,
		Name:       resp.Name,
		URL:        resp.Url,
		MediaID:    resp.MediaId,
		MsgType:    resp.MsgType,
		AppID:      resp.AppId,
		PagePath:   resp.PagePath,
		CreateTime: resp.CreateTime.Format("2006-04-02 15:04:05"),
		UpdateTime: resp.UpdateTime.Format("2006-04-02 15:04:05"),
		Text:       resp.Text,
		Pic:        resp.Pic,
	}, nil
}
