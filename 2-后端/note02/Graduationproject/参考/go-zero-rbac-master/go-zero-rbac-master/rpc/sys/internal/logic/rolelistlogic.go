package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleListLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleListLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleListLogic {
	return &RoleListLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleList 角色列表
func (l *RoleListLogic) RoleList(in *sys.RoleListRequest) (*sys.RoleListResp, error) {
	resp, err := l.svcCtx.RoleModel.FindList()
	if err != nil {
		return nil, err
	}
	var list []*sys.RoleListData
	for _, role := range *resp {
		list = append(list, &sys.RoleListData{
			Id:         role.Id,
			CreateTime: role.CreateTime.Format("2006-01-02 15:04:05"),
			UpdateTime: role.UpdateTime.Format("2006-01-02 15:04:05"),
			Name:       role.Name,
			Label:      role.Label,
			Remark:     role.Remark,
			Relevance:  role.Relevance,
		})
	}

	return &sys.RoleListResp{
		List: list,
	}, nil
}
