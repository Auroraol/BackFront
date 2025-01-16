package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyInfoLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewReplyInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) ReplyInfoLogic {
	return ReplyInfoLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ReplyInfoLogic) ReplyInfo(req types.ReplyInfoReq) (*types.Resp, error) {
	resp, err := l.svcCtx.Wechat.KeyWordInfo(l.ctx, &wechatclient.KeyWordReplyInfoReq{
		Id: req.Id,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	info := &types.ReplyInfoData{
		Id:         resp.Id,
		Name:       resp.Name,
		Key:        resp.Key,
		Match:      resp.Match,
		Type:       resp.Type,
		MediaId:    resp.MediaId,
		Text:       resp.Text,
		Url:        resp.Url,
		CreateTime: resp.CreateTime,
		UpdateTime: resp.UpdateTime,
	}

	return &types.Resp{
		Code: 200,
		Data: info,
		Msg:  "success",
	}, nil
}
