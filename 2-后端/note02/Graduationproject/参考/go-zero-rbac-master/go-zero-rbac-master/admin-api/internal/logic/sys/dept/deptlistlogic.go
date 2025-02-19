/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：deptlistlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:40
 * # 上次修改时间：2021年07月11日 01:14:06
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/rpc/sys/sysclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"context"
	"strings"

	"github.com/tal-tech/go-zero/core/logx"
)

type DeptListLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewDeptListLogic(ctx context.Context, svcCtx *svc.ServiceContext) DeptListLogic {
	return DeptListLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *DeptListLogic) DeptList() (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.DeptList(l.ctx, &sysclient.DeptListRequest{})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	var list []*types.DeptData
	for _, dept := range resp.List {
		ParentName := ""
		if dept.ParentId != 0 {
			for _, v := range resp.List {
				if ok := strings.EqualFold(gconv.String(dept.ParentId), gconv.String(v.Id)); ok {
					ParentName = v.Name
					break
				}
			}
		}
		list = append(list, &types.DeptData{
			Id:         dept.Id,
			CreateTime: dept.CreateTime,
			UpdateTime: dept.UpdateTime,
			Name:       dept.Name,
			ParentId:   dept.ParentId,
			OrderNum:   dept.OrderNum,
			ParentName: ParentName,
		})
	}
	return &types.Resp{
		Code: 200,
		Data: list,
		Msg:  "",
	}, nil
}
