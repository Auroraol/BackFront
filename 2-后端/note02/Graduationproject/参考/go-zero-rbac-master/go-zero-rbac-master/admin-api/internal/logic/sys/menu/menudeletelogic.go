package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuDeleteLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuDeleteLogic {
	return MenuDeleteLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuDeleteLogic) MenuDelete(req types.MenuDeleteReq) (*types.Resp, error) {
	_, err := l.svcCtx.Sys.MenuDelete(l.ctx, &sysclient.MenuDeleteRequest{
		Ids: req.Ids,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	return &types.Resp{
		Code: 200,
		Msg:  "success",
	}, nil
}
