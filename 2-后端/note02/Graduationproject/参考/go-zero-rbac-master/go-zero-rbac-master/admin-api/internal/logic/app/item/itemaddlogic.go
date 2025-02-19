/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：itemaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月15日 22:23:58
 * # 上次修改时间：2021年07月15日 22:16:00
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/app/appclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ItemAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewItemAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) ItemAddLogic {
	return ItemAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ItemAddLogic) ItemAdd(req types.ItemAddReq) (*types.Resp, error) {
	_, err := l.svcCtx.App.AppAddItem(l.ctx, &appclient.AppAddItemReq{
		ClassifyId: req.ClassifyId,
		Type:       req.Type,
		Url:        req.Url,
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
