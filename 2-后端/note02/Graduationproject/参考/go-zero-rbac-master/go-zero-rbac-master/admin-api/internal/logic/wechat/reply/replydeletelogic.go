package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyDeleteLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewReplyDeleteLogic(ctx context.Context, svcCtx *svc.ServiceContext) ReplyDeleteLogic {
	return ReplyDeleteLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ReplyDeleteLogic) ReplyDelete(req types.ReplyDeleteReq) (*types.Resp, error) {
	_, err := l.svcCtx.Wechat.KeyWordDelete(l.ctx, &wechatclient.KeyWordReplyDeleteReq{
		Ids: req.Ids,
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
