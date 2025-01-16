/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：keywordreplypagelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月20日 23:41:30
 * # 上次修改时间：2021年07月18日 23:04:01
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type KeyWordReplyPageLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewKeyWordReplyPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) *KeyWordReplyPageLogic {
	return &KeyWordReplyPageLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// KeyWordReplyPage 关键词回复列表
func (l *KeyWordReplyPageLogic) KeyWordReplyPage(in *wechat.KeyWordRelyPageReq) (*wechat.KeyWordReplyPageResp, error) {
	resp, err := l.svcCtx.KeyReplyModel.FindAll(in.KeyWord, in.Sort, in.Order, in.Page, in.Size)
	if err != nil {
		return nil, err
	}
	total, _ := l.svcCtx.KeyReplyModel.Count(in.KeyWord)
	var list []*wechat.KeyWordReplyData
	for _, item := range *resp {
		list = append(list, &wechat.KeyWordReplyData{
			Id:         item.Id,
			Name:       item.Name,
			Key:        item.Key,
			Match:      item.Match,
			Type:       item.Type,
			MediaId:    item.MediaId,
			Text:       item.Text,
			Url:        item.Url,
			CreateTime: item.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: item.UpdateTime.Format("2006-04-02 15:04:05"),
		})
	}

	return &wechat.KeyWordReplyPageResp{
		List:  list,
		Total: total,
	}, nil
}
