/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：userupdatelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:58
 * # 上次修改时间：2021年07月07日 22:32:23
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

type UserUpdateLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUserUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) UserUpdateLogic {
	return UserUpdateLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UserUpdateLogic) UserUpdate(req types.UserUpdateReq) (*types.Resp, error) {
	_, err := l.svcCtx.Sys.UserUpdate(l.ctx, &sysclient.UserUpdateRequest{
		Id:           req.Id,
		CreateTime:   req.CreateTime,
		UpdateTime:   req.UpdateTime,
		DepartmentId: req.DepartmentId,
		Name:         req.Name,
		Password:     req.Remark,
		NickName:     req.NickName,
		HeadImg:      req.HeadImg,
		Phone:        req.Phone,
		Email:        req.Email,
		Status:       req.Status,
		Remark:       req.Remark,
		RoleIdList:   req.RoleIdList,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	return &types.Resp{
		Code: 200,
		Data: nil,
		Msg:  "success",
	}, nil
}
