package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"aso/utils/encryption"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserAddLogic {
	return &UserAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// UserAdd 添加用户
func (l *UserAddLogic) UserAdd(in *sys.UserAddRequest) (*sys.UserAddResp, error) {
	_, err := l.svcCtx.UserModel.Insert(sysmodel.SysUserChangeReq{
		SysUser: sysmodel.SysUser{
			DepartmentId: in.DepartmentId,
			Name:         in.Name,
			Password:     encryption.EncryptData("123456", l.svcCtx.Config.EncryptKey),
			NickName:     in.NickName,
			HeadImg:      in.HeadImg,
			Phone:        in.Phone,
			Email:        in.Email,
			Status:       in.Status,
			Remark:       in.Remark,
		},
		RoleIdList: in.RoleIdList,
	})

	if err != nil {
		return nil, err
	}

	return &sys.UserAddResp{}, nil
}
