package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleListPageLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleListPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleListPageLogic {
	return &RoleListPageLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleListPage 角色列表
func (l *RoleListPageLogic) RoleListPage(in *sys.RoleListPageRequest) (*sys.RoleListPageResp, error) {
	resp, err := l.svcCtx.RoleModel.FindAll(in.KeyWord, in.Sort, in.Order, in.Page, in.Size)
	if err != nil {
		return nil, err
	}
	count, err := l.svcCtx.RoleModel.Count(in.KeyWord)
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

	return &sys.RoleListPageResp{
		Total: count,
		List:  list,
	}, nil
}
