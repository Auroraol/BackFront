/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：appupdatetypelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 22:27:46
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/appmodel"
	"context"
	"time"

	"aso/rpc/app/app"
	"aso/rpc/app/internal/svc"

	"github.com/tal-tech/go-zero/core/logx"
)

type AppUpdateTypeLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewAppUpdateTypeLogic(ctx context.Context, svcCtx *svc.ServiceContext) *AppUpdateTypeLogic {
	return &AppUpdateTypeLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// AppUpdateType 相册类型更新
func (l *AppUpdateTypeLogic) AppUpdateType(in *app.AppUpdateTypeReq) (*app.AppUpdateTypeResp, error) {
	err := l.svcCtx.TypeModel.Update(appmodel.AppType{
		Id:         in.Id,
		UpdateTime: time.Now(),
		Name:       in.Name,
	})
	if err != nil {
		return nil, err
	}

	return &app.AppUpdateTypeResp{}, nil
}
