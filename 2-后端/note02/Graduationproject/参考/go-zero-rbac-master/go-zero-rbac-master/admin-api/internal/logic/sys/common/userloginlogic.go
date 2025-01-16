package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"aso/utils/prefix"
	"context"
	"fmt"
	"strings"
	"time"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserLoginLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUserLoginLogic(ctx context.Context, svcCtx *svc.ServiceContext) UserLoginLogic {
	return UserLoginLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UserLoginLogic) UserLogin(req types.LoginRequest) (*types.Resp, error) {
	if len(strings.TrimSpace(req.Username)) == 0 || len(strings.TrimSpace(req.Password)) == 0 {
		return nil, errorx.NewDefaultError("参数错误")
	}
	if len(strings.TrimSpace(req.CaptchaId)) == 0 || len(strings.TrimSpace(req.VerifyCode)) == 0 {
		return nil, errorx.NewDefaultError("参数错误")
	}
	resp, err := l.svcCtx.Sys.Login(l.ctx, &sysclient.LoginRequest{
		Username:   req.Username,
		Password:   req.Password,
		CaptchaId:  req.CaptchaId,
		VerifyCode: req.VerifyCode,
	})

	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	// 缓存当前用户的token数据
	cacheKey := fmt.Sprintf("%s%v", prefix.CacheSysUserTokenPrefix, resp.Uid)
	times := time.Second * time.Duration(l.svcCtx.Config.Auth.AccessExpire)
	_ = l.svcCtx.Cache.SetWithExpire(cacheKey, resp.Token, times)

	return &types.Resp{
		Code: 200,
		Msg:  "success",
		Data: gconv.H{
			"token": resp.Token,
		},
	}, nil
}
