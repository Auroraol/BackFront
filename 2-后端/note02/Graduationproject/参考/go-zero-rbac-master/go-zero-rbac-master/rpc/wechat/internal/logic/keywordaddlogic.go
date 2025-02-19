/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：keywordaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月20日 23:41:30
 * # 上次修改时间：2021年07月18日 23:04:12
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/wechatmodel"
	"context"
	"time"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type KeyWordAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewKeyWordAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *KeyWordAddLogic {
	return &KeyWordAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// KeyWordAdd 关键词回复添加
func (l *KeyWordAddLogic) KeyWordAdd(in *wechat.KeyWordReplyAddData) (*wechat.KeyWordReplyAddResp, error) {
	_, err := l.svcCtx.KeyReplyModel.Insert(wechatmodel.WechatKeyReplay{
		Name:       in.Name,
		Key:        in.Key,
		Match:      in.Match,
		Type:       in.Type,
		MediaId:    in.MediaId,
		Text:       in.Text,
		Url:        in.Url,
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
	})
	if err != nil {
		return nil, err
	}

	return &wechat.KeyWordReplyAddResp{}, nil
}
