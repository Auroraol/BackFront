package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuUpdateLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuUpdateLogic {
	return MenuUpdateLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuUpdateLogic) MenuUpdate(req types.MenuUpdateReq) (*types.Resp, error) {
	_, err := l.svcCtx.Sys.MenuUpdate(l.ctx, &sysclient.MenuUpdateRequest{
		Id:        req.Id,
		ParentId:  req.ParentId,
		Name:      req.Name,
		Router:    req.Router,
		Perms:     req.Perms,
		Type:      req.Type,
		Icon:      req.Icon,
		OrderNum:  req.OrderNum,
		ViewPath:  req.ViewPath,
		KeepAlive: req.KeepAlive,
		IsShow:    req.IsShow,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	return &types.Resp{
		Code: 200,
		Msg:  "success",
	}, nil
}
