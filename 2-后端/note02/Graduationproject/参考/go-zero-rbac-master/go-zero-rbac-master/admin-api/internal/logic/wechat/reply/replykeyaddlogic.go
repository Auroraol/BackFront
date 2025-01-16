package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyKeyAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewReplyKeyAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) ReplyKeyAddLogic {
	return ReplyKeyAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ReplyKeyAddLogic) ReplyKeyAdd(req types.AddReplyReq) (*types.Resp, error) {
	_, err := l.svcCtx.Wechat.KeyWordAdd(l.ctx, &wechatclient.KeyWordReplyAddData{
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
