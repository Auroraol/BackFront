package logic

import (
	"aso/rpc/wechat/wechatclient"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type ReplyPaegLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewReplyPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) ReplyPaegLogic {
	return ReplyPaegLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ReplyPaegLogic) ReplyPage(req types.PageReq) (*types.PageResp, error) {
	page, err := l.svcCtx.Wechat.KeyWordReplyPage(l.ctx, &wechatclient.KeyWordRelyPageReq{
		Page:    req.Page,
		Size:    req.Size,
		Order:   req.Oder,
		Sort:    req.Sort,
		KeyWord: req.KeyWord,
	})
	if err != nil {
		return nil, err
	}

	return &types.PageResp{
		Code: 200,
		Data: types.PageListData{
			List: page.List,
			Pagination: types.PageData{
				Page:  req.Page,
				Size:  req.Size,
				Total: page.Total,
			},
		},
		Msg: "success",
	}, nil
}
