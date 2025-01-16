package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type UserPageLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUserPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) UserPageLogic {
	return UserPageLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UserPageLogic) UserPage(req types.UserListReq) (*types.PageResp, error) {
	resp, err := l.svcCtx.Sys.UserList(l.ctx, &sysclient.UserListRequest{
		Page:    req.Page,
		Size:    req.Size,
		Order:   req.Oder,
		Sort:    req.Sort,
		KeyWord: req.KeyWord,
		DeptId:  req.DeptId,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	var list []*types.UserListData
	for _, user := range resp.List {
		list = append(list, &types.UserListData{
			Id:             user.Id,
			CreateTime:     user.CreateTime,
			UpdateTime:     user.UpdateTime,
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
	// 解决无数据返回null
	if list == nil {
		list = make([]*types.UserListData, 0)
	}
	return &types.PageResp{
		Code: 200,
		Data: types.PageListData{
			List: list,
			Pagination: types.PageData{
				Page:  req.Page,
				Size:  req.Size,
				Total: resp.Total,
			},
		},
		Msg: "success",
	}, nil
}
