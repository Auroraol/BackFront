package logic

import (
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"
	"context"
	"github.com/tal-tech/go-zero/core/logx"
)

type KeyWordInfoLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewKeyWordInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) *KeyWordInfoLogic {
	return &KeyWordInfoLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// KeyWordInfo 关键词详情
func (l *KeyWordInfoLogic) KeyWordInfo(in *wechat.KeyWordReplyInfoReq) (*wechat.KeyWordReplyInfoResp, error) {
	one, err := l.svcCtx.KeyReplyModel.FindOne(in.Id, "id")
	if err != nil {
		return nil, err
	}

	return &wechat.KeyWordReplyInfoResp{
		Id:         one.Id,
		Name:       one.Name,
		Key:        one.Key,
		Match:      one.Match,
		Type:       one.Type,
		MediaId:    one.MediaId,
		Text:       one.Text,
		Url:        one.Url,
		CreateTime: one.CreateTime.Format("2006-04-02 15:04:05"),
		UpdateTime: one.UpdateTime.Format("2006-04-02 15:04:05"),
	}, nil
}
