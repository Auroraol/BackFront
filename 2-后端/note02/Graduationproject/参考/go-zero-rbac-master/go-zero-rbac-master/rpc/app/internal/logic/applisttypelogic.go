/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：applisttypelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:55:19
 * # 上次修改时间：2021年07月15日 22:46:23
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

type AppListTypeLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewAppListTypeLogic(ctx context.Context, svcCtx *svc.ServiceContext) *AppListTypeLogic {
	return &AppListTypeLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// AppListType 相册类型
func (l *AppListTypeLogic) AppListType(in *app.AppListTypeReq) (*app.AppListTypeResp, error) {
	resp, err := l.svcCtx.TypeModel.FindList()
	if err != nil {
		return nil, err
	}
	var list []*app.AppListTypeData
	for _, v := range *resp {
		list = append(list, &app.AppListTypeData{
			Id:         v.Id,
			CreateTime: v.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: v.UpdateTime.Format("2006-04-02 15:04:05"),
			Name:       v.Name,
		})
	}
	return &app.AppListTypeResp{
		List: list,
	}, nil
}
