package logic

import (
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"
	"time"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUserAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) UserAddLogic {
	return UserAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UserAddLogic) UserAdd(req types.UserAddReq) (*types.Resp, error) {
	_, err := l.svcCtx.Sys.UserAdd(l.ctx, &sysclient.UserAddRequest{
		CreateTime:   time.Now().Format("2006-01-02 15:01:05"),
		UpdateTime:   time.Now().Format("2006-01-02 15:01:05"),
		DepartmentId: req.DepartmentId,
		Name:         req.Name,
		Username:     req.Username,
		NickName:     req.NickName,
		HeadImg:      req.HeadImg,
		Phone:        req.Phone,
		Email:        req.Email,
		Status:       req.Status,
		Remark:       req.Remark,
		RoleIdList:   req.RoleIdList,
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
