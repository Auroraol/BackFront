package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type RolePageLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewRolePageLogic(ctx context.Context, svcCtx *svc.ServiceContext) RolePageLogic {
	return RolePageLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *RolePageLogic) RolePage(req types.RoleListPageReq) (*types.PageResp, error) {
	resp, err := l.svcCtx.Sys.RoleListPage(l.ctx, &sysclient.RoleListPageRequest{
		Page:    req.Page,
		Size:    req.Size,
		Order:   req.Oder,
		Sort:    req.Sort,
		KeyWord: req.KeyWord,
	})
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
	return &types.PageResp{
		Code: 200,
		Data: types.PageListData{
			List: list,
			Pagination: types.PageData{
				Page:  req.Page,
				Size:  req.Size,
				Total: resp.Total,
			},
		},
		Msg: "",
	}, nil
}
