package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"context"
	"github.com/tal-tech/go-zero/core/logx"
)

type CaptchaLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewCaptchaLogic(ctx context.Context, svcCtx *svc.ServiceContext) CaptchaLogic {
	return CaptchaLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *CaptchaLogic) Captcha(req types.CaptchaRequest) (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.Captcha(l.ctx, &sysclient.CaptchaRequest{
		Width:  gconv.Int64(req.Width),
		Height: gconv.Int64(req.Height),
	})

	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	return &types.Resp{
		Code: 200,
		Data: gconv.H{
			"captchaId": resp.CaptchaId,
			"data":      resp.Data,
		},
		Msg: "success",
	}, nil
}
