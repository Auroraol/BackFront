package logic

import (
	"aso/rpc/model/wechatmodel"
	"context"
	"time"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type KeyWordUpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewKeyWordUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *KeyWordUpdateLogic {
	return &KeyWordUpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// KeyWordUpdate 关键词更新
func (l *KeyWordUpdateLogic) KeyWordUpdate(in *wechat.KeyWordReplyUpdateReq) (*wechat.KeyWordReplyUpdateResp, error) {
	err := l.svcCtx.KeyReplyModel.Update(wechatmodel.WechatKeyReplay{
		Id:         in.Id,
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

	return &wechat.KeyWordReplyUpdateResp{}, nil
}
