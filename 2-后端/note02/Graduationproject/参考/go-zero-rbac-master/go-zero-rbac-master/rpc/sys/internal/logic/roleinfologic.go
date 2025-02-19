/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：roleinfologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 15:47:52
 * # 上次修改时间：2021年07月11日 15:34:23
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

type RoleInfoLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewRoleInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RoleInfoLogic {
	return &RoleInfoLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// RoleInfo 角色详情
func (l *RoleInfoLogic) RoleInfo(in *sys.RoleInfoRequest) (*sys.RoleInfoResp, error) {
	resp, err := l.svcCtx.RoleModel.FindOne(in.Id)

	menuList, _ := l.svcCtx.RoleMenuModel.FindListByMenu(resp.Id)
	deptList, _ := l.svcCtx.RoleDepartment.FindListByDept(resp.Id)
	if err != nil {
		return nil, err
	}

	return &sys.RoleInfoResp{
		Id:               resp.Id,
		CreateTime:       resp.CreateTime.Format("2006-04-02 15:04:05"),
		UpdateTime:       resp.UpdateTime.Format("2006-04-02 15:04:05"),
		Name:             resp.Name,
		Label:            resp.Label,
		Remark:           resp.Remark,
		Relevance:        resp.Relevance,
		MenuIdList:       menuList,
		DepartmentIdList: deptList,
	}, nil
}
