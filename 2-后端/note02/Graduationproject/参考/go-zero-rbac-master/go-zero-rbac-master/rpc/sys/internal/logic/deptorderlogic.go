/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：deptorderlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:40
 * # 上次修改时间：2021年07月11日 01:52:05
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/sysmodel"
	"context"
	"time"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type DeptOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewDeptOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeptOrderLogic {
	return &DeptOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// DeptOrder 部门排序
func (l *DeptOrderLogic) DeptOrder(in *sys.DeptOderRequest) (*sys.DeptOrderResp, error) {
	err := l.svcCtx.DeptModel.UpdateByOrder(sysmodel.SysDepartment{
		Id:         in.Id,
		UpdateTime: time.Now(),
		ParentId:   in.ParentId,
		OrderNum:   in.OrderNum,
	})
	if err != nil {
		return nil, err
	}

	return &sys.DeptOrderResp{}, nil
}
