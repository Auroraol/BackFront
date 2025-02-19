/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 11:41:04
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMaterialAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) MaterialAddLogic {
	return MaterialAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MaterialAddLogic) MaterialAdd(req types.MaterialAddReq) (*types.Resp, error) {
	t := false
	if len(req.Data) > 1 {
		t = true
	}
	result, err := l.svcCtx.Wechat.MaterialAdd(l.ctx, &wechatclient.MaterialAddReq{
		Title: req.Title,
		Thumb: req.Thumb,
		Type:  t,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	for _, v := range req.Data {
		_, err := l.svcCtx.Wechat.MaterialItemAdd(l.ctx, &wechatclient.MaterialItemAddReq{
			Url:              v.Url,
			Type:             v.Type,
			Title:            v.Title,
			Author:           v.Author,
			Cover:            v.Cover,
			Summary:          v.Summary,
			Content:          v.Content,
			ContentSourceUrl: v.ContentSourceUrl,
			Sort:             v.Sort,
			Hits:             v.Hits,
			MediaKey:         result.Id,
		})
		if err != nil {
			return nil, errorx.NewDefaultError(err.Error())
		}
	}
	return &types.Resp{
		Code: 200,
		Data: nil,
		Msg:  "success",
	}, nil
}
