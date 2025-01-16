/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialitemaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 11:25:21
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/wechatmodel"
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"
	"context"
	"time"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialItemAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMaterialItemAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MaterialItemAddLogic {
	return &MaterialItemAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MaterialItemAdd 素材内容添加
func (l *MaterialItemAddLogic) MaterialItemAdd(in *wechat.MaterialItemAddReq) (*wechat.MaterialItemResp, error) {
	_, err := l.svcCtx.MaterialItemModel.Insert(wechatmodel.WechatMaterialItem{
		CreateTime:       time.Now(),
		UpdateTime:       time.Now(),
		Url:              in.Url,
		Type:             in.Type,
		Title:            in.Title,
		Author:           in.Author,
		Cover:            in.Cover,
		Summary:          in.Summary,
		Content:          in.Content,
		ContentSourceUrl: in.ContentSourceUrl,
		Sort:             0,
		Hits:             0,
		MediaKey:         in.MediaKey,
	})
	if err != nil {
		return nil, err
	}

	return &wechat.MaterialItemResp{}, nil
}
