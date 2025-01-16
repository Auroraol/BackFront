/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：appdeletetypelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 23:02:22
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"context"

	"aso/rpc/app/app"
	"aso/rpc/app/internal/svc"

	"github.com/tal-tech/go-zero/core/logx"
)

type AppDeleteTypeLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewAppDeleteTypeLogic(ctx context.Context, svcCtx *svc.ServiceContext) *AppDeleteTypeLogic {
	return &AppDeleteTypeLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// AppDeleteType 相册类型删除
func (l *AppDeleteTypeLogic) AppDeleteType(in *app.AppDeleteTypeReq) (*app.AppDeleteTypeResp, error) {
	err := l.svcCtx.TypeModel.Deletes(in.Ids)
	if err != nil {
		return nil, err
	}
	// todo 待完成删除文件
	return &app.AppDeleteTypeResp{}, nil
}
