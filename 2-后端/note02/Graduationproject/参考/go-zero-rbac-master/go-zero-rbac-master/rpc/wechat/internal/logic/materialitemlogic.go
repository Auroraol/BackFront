/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialitemlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月17日 15:57:33
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialItemLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMaterialItemLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MaterialItemLogic {
	return &MaterialItemLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MaterialItem 素材内容
func (l *MaterialItemLogic) MaterialItem(in *wechat.WechatMaterialItemReq) (*wechat.WechatMaterialItemResponse, error) {
	resp, err := l.svcCtx.MaterialItemModel.FindAllByMediaKey(in.MediaId)
	if err != nil {
		return nil, err
	}
	var list []*wechat.WechatMaterialItemData
	for _, v := range *resp {
		list = append(list, &wechat.WechatMaterialItemData{
			CreateTime:       v.CreateTime.Format("2006-01-02 15:04:05"),
			UpdateTime:       v.UpdateTime.Format("2006-04-02 15:04:05"),
			Url:              v.Url,
			Type:             v.Type,
			Title:            v.Title,
			Author:           v.Author,
			Cover:            v.Cover,
			Summary:          v.Summary,
			ContentSourceUrl: v.ContentSourceUrl,
			Sort:             v.Sort,
			Hits:             v.Hits,
			MediaKey:         v.MediaKey,
		})
	}

	return &wechat.WechatMaterialItemResponse{
		List: list,
	}, nil
}
