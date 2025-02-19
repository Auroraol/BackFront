package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"
	"encoding/json"

	"github.com/tal-tech/go-zero/core/logx"
)

type PersonLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewPersonLogic(ctx context.Context, svcCtx *svc.ServiceContext) PersonLogic {
	return PersonLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *PersonLogic) Person() (*types.Resp, error) {
	// 这里的key和生成jwt token时传入的key一致
	userId, _ := l.ctx.Value("userId").(json.Number).Int64()
	resp, err := l.svcCtx.Sys.UserInfo(l.ctx, &sysclient.UserInfoRequest{
		Id: userId,
	})

	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}

	return &types.Resp{
		Code: 200,
		Data: resp,
		Msg:  "success",
	}, nil
}
