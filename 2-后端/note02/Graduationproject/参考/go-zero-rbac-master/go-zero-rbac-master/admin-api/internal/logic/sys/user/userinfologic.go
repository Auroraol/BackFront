/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：userinfologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:58
 * # 上次修改时间：2021年07月07日 22:08:35
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

type UserInfoLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUserInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) UserInfoLogic {
	return UserInfoLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UserInfoLogic) UserInfo(req types.UserInfoReq) (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.UserInfo(l.ctx, &sysclient.UserInfoRequest{
		Id: req.Id,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	info := types.UserInfoData{
		Id:           resp.Id,
		CreateTime:   resp.CreateTime,
		UpdateTime:   resp.UpdateTime,
		DepartmentId: resp.DepartmentId,
		Name:         resp.Name,
		NickName:     resp.NickName,
		HeadImg:      resp.HeadImg,
		Phone:        resp.Phone,
		Email:        resp.Email,
		Status:       resp.Status,
		Remark:       resp.Remark,
		RoleIdList:   resp.RoleIdList,
	}

	return &types.Resp{
		Code: 200,
		Data: info,
		Msg:  "success",
	}, nil
}
