/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：deptdeletelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:40
 * # 上次修改时间：2021年07月11日 01:28:53
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"context"
	"github.com/tal-tech/go-zero/core/logx"
)

type DeptDeleteLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewDeptDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeptDeleteLogic {
	return &DeptDeleteLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// DeptDelete 删除部门
func (l *DeptDeleteLogic) DeptDelete(in *sys.DeptDeleteRequest) (*sys.DeptDeleteResp, error) {
	pid, _ := l.svcCtx.DeptModel.FindPid(in.Id)
	err := l.svcCtx.DeptModel.Delete(in.Id)
	if err != nil {
		return nil, err
	}
	if in.DeleteUser {
		err = l.svcCtx.UserModel.DeleteByDeptIds(in.Id)
	} else {
		err = l.svcCtx.UserModel.UpdateByDept(in.Id, pid)
	}

	return &sys.DeptDeleteResp{}, nil
}
