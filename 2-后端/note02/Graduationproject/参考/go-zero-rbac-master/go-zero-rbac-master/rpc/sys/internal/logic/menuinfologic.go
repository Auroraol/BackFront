package logic

import (
	"aso/utils/gconv"
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

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

// MenuInfo 菜单详情
func (l *MenuInfoLogic) MenuInfo(in *sys.MenuInfoRequest) (*sys.MenuInfoResp, error) {
	resp, err := l.svcCtx.MenuModel.FindOne(in.Id)
	if err != nil {
		return nil, err
	}
	return &sys.MenuInfoResp{
		Id:         resp.Id,
		CreateTime: gconv.String(resp.CreateTime),
		UpdateTime: gconv.String(resp.UpdateTime),
		ParentId:   resp.ParentId,
		Name:       resp.Name,
		Router:     resp.Router,
		Perms:      resp.Perms,
		Type:       resp.Type,
		Icon:       resp.Icon,
		OrderNum:   resp.OrderNum,
		ViewPath:   resp.ViewPath,
		KeepAlive:  resp.KeepAlive,
		IsShow:     resp.IsShow,
	}, nil
}
