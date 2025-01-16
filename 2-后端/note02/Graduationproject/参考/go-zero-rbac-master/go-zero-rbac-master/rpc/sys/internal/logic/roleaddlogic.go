/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：roleaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 21:34:59
 * # 上次修改时间：2021年07月11日 21:31:55
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

type RoleAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleAddLogic {
	return &RoleAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleAdd 角色添加
func (l *RoleAddLogic) RoleAdd(in *sys.RoleAddRequest) (*sys.RoleAddResp, error) {
	ret, err := l.svcCtx.RoleModel.Insert(sysmodel.SysRole{
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
		Name:       in.Name,
		Label:      in.Label,
		Remark:     in.Remark,
		Relevance:  in.Relevance,
	})
	roleId, _ := ret.LastInsertId()

	var menuList []*sysmodel.SysRoleMenu
	for _, menuId := range in.MenuIdList {
		menuList = append(menuList, &sysmodel.SysRoleMenu{
			RoleId: roleId,
			MenuId: menuId,
		})
	}
	err = l.svcCtx.RoleMenuModel.Inserts(menuList)

	var deptList []*sysmodel.SysRoleDepartment
	for _, deptId := range in.DepartmentIdList {
		deptList = append(deptList, &sysmodel.SysRoleDepartment{
			RoleId:       roleId,
			DepartmentId: deptId,
		})
	}
	err = l.svcCtx.RoleDepartment.Inserts(deptList)
	if err != nil {
		return nil, err
	}

	return &sys.RoleAddResp{}, nil
}
