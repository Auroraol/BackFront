package logic

import (
	"aso/utils/gconv"
	"context"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserInfoLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserInfoLogic {
	return &UserInfoLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// UserInfo 用户详情
func (l *UserInfoLogic) UserInfo(in *sys.UserInfoRequest) (*sys.UserInfoResp, error) {
	// todo: add your logic here and delete this line
	resp, err := l.svcCtx.UserModel.FindOne(in.Id)
	if err != nil {
		return nil, err
	}
	roleIds, _ := l.svcCtx.UserRoleModel.FindRoleIds(resp.Id)
	return &sys.UserInfoResp{
		Id:           resp.Id,
		CreateTime:   gconv.String(resp.CreateTime),
		UpdateTime:   gconv.String(resp.UpdateTime),
		DepartmentId: resp.DepartmentId,
		Name:         resp.Name,
		Username:     resp.Username,
		NickName:     resp.NickName,
		HeadImg:      resp.HeadImg,
		Phone:        resp.Phone,
		Email:        resp.Email,
		Status:       resp.Status,
		Remark:       resp.Remark,
		RoleIdList:   roleIds,
	}, nil
}
