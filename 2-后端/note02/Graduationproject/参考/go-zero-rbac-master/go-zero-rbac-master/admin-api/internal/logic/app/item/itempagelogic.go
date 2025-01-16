/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：itempagelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月16日 00:26:03
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

type ItemPageLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewItemPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) ItemPageLogic {
	return ItemPageLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ItemPageLogic) ItemPage(req types.ItemPageReq) (*types.PageResp, error) {
	resp, err := l.svcCtx.App.AppPageItem(l.ctx, &appclient.AppPageItemReq{
		ClassifyId: req.ClassifyId,
		Page:       req.Page,
		Size:       req.Size,
		Total:      req.Total,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	var list []*types.ItemPageData
	for _, item := range resp.List {
		list = append(list, &types.ItemPageData{
			Id:         item.Id,
			CreateTime: item.CreateTime,
			UpdateTime: item.UpdateTime,
			Url:        item.Url,
			Type:       item.Type,
			ClassifyId: item.ClassifyId,
		})
	}
	// 解决无数据返回null
	if list == nil {
		list = make([]*types.ItemPageData, 0)
	}
	return &types.PageResp{
		Code: 200,
		Data: types.PageListData{
			List: list,
			Pagination: types.PageData{
				Page:  req.Page,
				Size:  req.Size,
				Total: resp.Total,
			},
		},
		Msg: "success",
	}, nil
}
