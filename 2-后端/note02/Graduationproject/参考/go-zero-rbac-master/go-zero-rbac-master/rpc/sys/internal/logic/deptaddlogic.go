package logic

import (
	"aso/rpc/model/sysmodel"
	"context"
	"time"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type DeptAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewDeptAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeptAddLogic {
	return &DeptAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// DeptAdd 部门添加
// @param *sys.DeptAddRequest
// @return *sys.DeptAddResp, error
func (l *DeptAddLogic) DeptAdd(in *sys.DeptAddRequest) (*sys.DeptAddResp, error) {
	_, err := l.svcCtx.DeptModel.Insert(sysmodel.SysDepartment{
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
		Name:       in.Name,
		ParentId:   in.ParentId,
		OrderNum:   in.OrderNum,
	})
	if err != nil {
		return nil, err
	}

	return &sys.DeptAddResp{}, nil
}
