/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：menuaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月13日 23:46:35
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
	"context"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuAddLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuAddLogic {
	return MenuAddLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuAddLogic) MenuAdd(req types.MenuAddReq) (*types.Resp, error) {
	_, err := l.svcCtx.Sys.MenuAdd(l.ctx, &sysclient.MenuAddRequest{
		ParentId:  req.ParentId,
		Name:      req.Name,
		Router:    req.Router,
		Perms:     req.Perms,
		Type:      req.Type,
		Icon:      req.Icon,
		OrderNum:  req.OrderNum,
		ViewPath:  req.ViewPath,
		KeepAlive: req.KeepAlive,
		IsShow:    req.IsShow,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	return &types.Resp{
		Code: 200,
		Msg:  "添加成功",
	}, nil
}
