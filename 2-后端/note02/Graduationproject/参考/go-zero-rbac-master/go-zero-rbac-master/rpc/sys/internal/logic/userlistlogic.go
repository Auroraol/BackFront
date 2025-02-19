/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：userlistlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月13日 23:45:30
 * # 上次修改时间：2021年07月13日 20:35:22
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/sysmodel"
	"aso/rpc/sys/internal/svc"
	"aso/rpc/sys/sys"
	"context"
	"github.com/tal-tech/go-zero/core/logx"
)

type UserListLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUserListLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UserListLogic {
	return &UserListLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// UserList 用户列表
func (l *UserListLogic) UserList(in *sys.UserListRequest) (*sys.UserListResp, error) {
	all, err := l.svcCtx.UserModel.FindAll(&sysmodel.SysUserReq{
		DeptId:  in.DeptId,
		KeyWord: in.KeyWord,
		Page:    in.Page,
		Size:    in.Size,
		Sort:    in.Sort,
		Oder:    in.Order,
	})
	count, _ := l.svcCtx.UserModel.Count(&sysmodel.SysUserReq{
		DeptId:  in.DeptId,
		KeyWord: in.KeyWord,
		Page:    in.Page,
		Size:    in.Size,
		Sort:    in.Sort,
		Oder:    in.Order,
	})
	if err != nil {
		return nil, err
	}

	var list []*sys.UserListData
	for _, user := range *all {
		list = append(list, &sys.UserListData{
			Id:             user.Id,
			CreateTime:     user.CreateTime.Format("2006-01-02 15:04:05"),
			UpdateTime:     user.UpdateTime.Format("2006-01-02 15:04:05"),
			DepartmentId:   user.DepartmentId,
			Name:           user.Name,
			Username:       user.Username,
			NickName:       user.NickName,
			HeadImg:        user.HeadImg,
			Phone:          user.Phone,
			Email:          user.Email,
			Status:         user.Status,
			Remark:         user.Remark,
			RoleName:       user.RoleName,
			DepartmentName: user.DepartmentName,
		})
	}
	return &sys.UserListResp{
		Total: count,
		List:  list,
	}, nil
}
