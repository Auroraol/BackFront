package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type DeptListLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewDeptListLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeptListLogic {
	return &DeptListLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// DeptList 部门列表
func (l *DeptListLogic) DeptList(in *sys.DeptListRequest) (*sys.DeptListResp, error) {
	resp, err := l.svcCtx.DeptModel.FindAll()
	if err != nil {
		return nil, err
	}
	var list []*sys.DeptListData
	for _, dept := range *resp {
		list = append(list, &sys.DeptListData{
			Id:         dept.Id,
			CreateTime: dept.CreateTime.Format("2006-01-02 15:04:05"),
			UpdateTime: dept.UpdateTime.Format("2006-01-02 15:04:05"),
			Name:       dept.Name,
			OrderNum:   dept.OrderNum,
			ParentId:   dept.ParentId,
		})
	}
	return &sys.DeptListResp{
		List: list,
	}, nil
}
