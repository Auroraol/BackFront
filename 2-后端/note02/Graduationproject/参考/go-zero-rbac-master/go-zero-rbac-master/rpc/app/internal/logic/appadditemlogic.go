/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：appadditemlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月15日 22:23:58
 * # 上次修改时间：2021年07月15日 21:24:53
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

type AppAddItemLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewAppAddItemLogic(ctx context.Context, svcCtx *svc.ServiceContext) *AppAddItemLogic {
	return &AppAddItemLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// AppAddItem 相册列表插入
func (l *AppAddItemLogic) AppAddItem(in *app.AppAddItemReq) (*app.AppAddItemResp, error) {
	_, err := l.svcCtx.ItemModel.Insert(appmodel.AppItem{
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
		Url:        in.Url,
		Type:       in.Type,
		ClassifyId: in.ClassifyId,
	})
	if err != nil {
		return nil, err
	}

	return &app.AppAddItemResp{}, nil
}
