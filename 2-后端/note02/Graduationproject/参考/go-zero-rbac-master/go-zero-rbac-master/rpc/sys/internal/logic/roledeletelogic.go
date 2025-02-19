/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：roledeletelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 21:34:59
 * # 上次修改时间：2021年07月11日 21:12:14
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleDeleteLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleDeleteLogic {
	return &RoleDeleteLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleDelete 角色删除
func (l *RoleDeleteLogic) RoleDelete(in *sys.RoleDeleteRequest) (*sys.RoleDeleteResp, error) {
	err := l.svcCtx.RoleModel.Deletes(in.Ids)
	err = l.svcCtx.RoleMenuModel.DeleteByField(in.Ids, "roleId")
	err = l.svcCtx.RoleDepartment.DeleteByField(in.Ids, "roleId")
	if err != nil {
		return nil, err
	}

	return &sys.RoleDeleteResp{}, nil
}
