package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ConfigUpdateReplyLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewConfigUpdateReplyLogic(ctx context.Context, svcCtx *svc.ServiceContext) ConfigUpdateReplyLogic {
	return ConfigUpdateReplyLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ConfigUpdateReplyLogic) ConfigUpdateReply(req types.ConfigReplyUpdateReq) (*types.Resp, error) {
	_, err := l.svcCtx.Wechat.Update(l.ctx, &wechatclient.WechatInfoUpdateRequest{
		Type:  req.Type,
		Value: gconv.String(req.Data),
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	return &types.Resp{
		Code: 200,
		Data: nil,
		Msg:  "success",
	}, nil

	return &types.Resp{}, nil
}
