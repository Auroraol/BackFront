/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialitemlistlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 15:58:06
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/wechat/wechatclient"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialItemListLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMaterialItemListLogic(ctx context.Context, svcCtx *svc.ServiceContext) MaterialItemListLogic {
	return MaterialItemListLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MaterialItemListLogic) MaterialItemList(req types.MaterialItemListReq) (*types.Resp, error) {
	item, err := l.svcCtx.Wechat.MaterialItem(l.ctx, &wechatclient.WechatMaterialItemReq{
		MediaId: req.Id,
	})
	if err != nil {
		return nil, err
	}
	var list []*types.MaterialData
	for _, data := range item.List {
		list = append(list, &types.MaterialData{
			Url:              data.Url,
			Type:             data.Type,
			Title:            data.Title,
			Author:           data.Author,
			Cover:            data.Cover,
			Summary:          data.Summary,
			ContentSourceUrl: data.ContentSourceUrl,
			Sort:             data.Sort,
			Hits:             data.Hits,
			MediaKey:         data.MediaKey,
		})
	}
	return &types.Resp{
		Code: 200,
		Data: list,
		Msg:  "success",
	}, nil
}
