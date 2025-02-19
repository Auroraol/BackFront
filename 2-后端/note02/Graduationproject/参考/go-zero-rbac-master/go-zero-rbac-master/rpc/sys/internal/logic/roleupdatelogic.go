/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：roleupdatelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 21:34:59
 * # 上次修改时间：2021年07月11日 21:30:34
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/utils/gconv"
	"context"
	"time"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleUpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleUpdateLogic {
	return &RoleUpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleUpdate 角色更新
func (l *RoleUpdateLogic) RoleUpdate(in *sys.RoleUpdateRequest) (*sys.RoleUpdateResp, error) {
	err := l.svcCtx.RoleMenuModel.DeleteByField(gconv.Int64s(in.Id), "roleId")
	err = l.svcCtx.RoleDepartment.DeleteByField(gconv.Int64s(in.Id), "roleId")
	err = l.svcCtx.RoleModel.Update(sysmodel.SysRole{
		Id:         in.Id,
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
		Name:       in.Name,
		Label:      in.Label,
		Remark:     in.Remark,
		Relevance:  in.Relevance,
	})

	var menuList []*sysmodel.SysRoleMenu
	for _, menuId := range in.MenuIdList {
		menuList = append(menuList, &sysmodel.SysRoleMenu{
			RoleId: in.Id,
			MenuId: menuId,
		})
	}
	err = l.svcCtx.RoleMenuModel.Inserts(menuList)

	var deptList []*sysmodel.SysRoleDepartment
	for _, deptId := range in.DepartmentIdList {
		deptList = append(deptList, &sysmodel.SysRoleDepartment{
			RoleId:       in.Id,
			DepartmentId: deptId,
		})
	}
	err = l.svcCtx.RoleDepartment.Inserts(deptList)

	if err != nil {
		return nil, err
	}

	return &sys.RoleUpdateResp{}, nil
}
