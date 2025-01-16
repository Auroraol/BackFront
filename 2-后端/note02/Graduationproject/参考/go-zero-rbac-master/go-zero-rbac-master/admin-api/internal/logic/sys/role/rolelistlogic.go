package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleListLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewRoleListLogic(ctx context.Context, svcCtx *svc.ServiceContext) RoleListLogic {
	return RoleListLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *RoleListLogic) RoleList() (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.RoleList(l.ctx, &sysclient.RoleListRequest{})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	var list []*types.RoleData
	for _, role := range resp.List {
		list = append(list, &types.RoleData{
			Id:         role.Id,
			CreateTime: role.CreateTime,
			UpdateTime: role.UpdateTime,
			Name:       role.Name,
			Label:      role.Label,
			Remark:     role.Remark,
			Relevance:  role.Relevance,
		})
	}
	return &types.Resp{
		Code: 200,
		Data: list,
		Msg:  "",
	}, nil
}
