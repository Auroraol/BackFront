package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/utils/encryption"
	"context"
	"time"

	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserUpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserUpdateLogic {
	return &UserUpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

//  更新用户
func (l *UserUpdateLogic) UserUpdate(in *sys.UserUpdateRequest) (*sys.UserUpdateResp, error) {
	// todo: add your logic here and delete this line
	err := l.svcCtx.UserModel.Update(sysmodel.SysUserChangeReq{
		SysUser: sysmodel.SysUser{
			Id:           in.Id,
			UpdateTime:   time.Now(),
			DepartmentId: in.DepartmentId,
			Name:         in.Name,
			Password:     encryption.EncryptData(in.Password, l.svcCtx.Config.EncryptKey),
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
	return &sys.UserUpdateResp{}, nil
}
