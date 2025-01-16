/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：usermovelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:58
 * # 上次修改时间：2021年07月11日 00:05:01
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

type UserMoveLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserMoveLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserMoveLogic {
	return &UserMoveLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// UserMove 转移用户
func (l *UserMoveLogic) UserMove(in *sys.UserMoveReq) (*sys.UserMoveResp, error) {
	err := l.svcCtx.UserModel.UpdateByIds(in.DepartmentId, in.UserIds)
	if err != nil {
		return nil, err
	}

	return &sys.UserMoveResp{}, nil
}
