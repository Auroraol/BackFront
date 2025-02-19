package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyUpdateLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewReplyUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) ReplyUpdateLogic {
	return ReplyUpdateLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ReplyUpdateLogic) ReplyUpdate(req types.ReplyInfoData) (*types.Resp, error) {
	_, err := l.svcCtx.Wechat.KeyWordUpdate(l.ctx, &wechatclient.KeyWordReplyUpdateReq{
		Id:      req.Id,
		Name:    req.Name,
		Key:     req.Key,
		Match:   req.Match,
		Type:    req.Type,
		MediaId: req.MediaId,
		Text:    req.Text,
		Url:     req.Url,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	return &types.Resp{
		Code: 200,
		Data: nil,
		Msg:  "success",
	}, nil
}
