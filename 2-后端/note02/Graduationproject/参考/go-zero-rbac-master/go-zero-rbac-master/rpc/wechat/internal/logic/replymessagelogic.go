package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyMessageLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewReplyMessageLogic(ctx context.Context, svcCtx *svc.ServiceContext) *ReplyMessageLogic {
	return &ReplyMessageLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// ReplyMessage 关键词回复
func (l *ReplyMessageLogic) ReplyMessage(in *wechat.ReplyMessageReq) (*wechat.ReplyMessageResp, error) {
	var resp *wechat.ReplyMessageResp
	result, err := l.svcCtx.KeyReplyModel.FindOne(in.Key, "key")
	if err != nil {
		like, err := l.svcCtx.KeyReplyModel.Like(in.Key)
		if err != nil {
			return nil, err
		}
		resp = &wechat.ReplyMessageResp{
			Id:         like.Id,
			Name:       like.Name,
			Key:        like.Key,
			Match:      like.Match,
			Type:       like.Type,
			MediaId:    like.MediaId,
			Text:       like.Text,
			Url:        like.Url,
			CreateTime: like.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: like.UpdateTime.Format("2006-04-02 15:04:05"),
		}
	} else {
		resp = &wechat.ReplyMessageResp{
			Id:         result.Id,
			Name:       result.Name,
			Key:        result.Key,
			Match:      result.Match,
			Type:       result.Type,
			MediaId:    result.MediaId,
			Text:       result.Text,
			Url:        result.Url,
			CreateTime: result.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: result.UpdateTime.Format("2006-04-02 15:04:05"),
		}
	}
	return resp, nil
}
