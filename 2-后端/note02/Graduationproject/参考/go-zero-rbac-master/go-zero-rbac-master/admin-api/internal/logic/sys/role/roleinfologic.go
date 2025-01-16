/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：roleinfologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 15:48:15
 * # 上次修改时间：2021年07月11日 15:35:15
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type RoleInfoLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewRoleInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) RoleInfoLogic {
	return RoleInfoLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *RoleInfoLogic) RoleInfo(req types.RoleInfoReq) (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.RoleInfo(l.ctx, &sysclient.RoleInfoRequest{
		Id: req.Id,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	info := types.RoleData{
		Id:               resp.Id,
		CreateTime:       resp.CreateTime,
		UpdateTime:       resp.UpdateTime,
		Name:             resp.Name,
		Label:            resp.Label,
		Remark:           resp.Remark,
		Relevance:        resp.Relevance,
		MenuIdList:       resp.MenuIdList,
		DepartmentIdList: resp.DepartmentIdList,
	}

	return &types.Resp{
		Code: 200,
		Data: info,
		Msg:  "success",
	}, nil
}
