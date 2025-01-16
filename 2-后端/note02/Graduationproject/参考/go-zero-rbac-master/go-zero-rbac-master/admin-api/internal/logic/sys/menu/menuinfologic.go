/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：menuinfologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月13日 23:46:31
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/sys/sysclient"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MenuInfoLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMenuInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) MenuInfoLogic {
	return MenuInfoLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MenuInfoLogic) MenuInfo(req types.MenuInfoReq) (*types.Resp, error) {
	resp, err := l.svcCtx.Sys.MenuInfo(l.ctx, &sysclient.MenuInfoRequest{
		Id: req.Id,
	})
	if err != nil {
		return nil, err
	}
	info := types.MenuData{
		Id:         resp.Id,
		CreateTime: resp.CreateTime,
		UpdateTime: resp.UpdateTime,
		ParentId:   resp.ParentId,
		Name:       resp.Name,
		Router:     resp.Router,
		Perms:      resp.Perms,
		Type:       resp.Type,
		Icon:       resp.Icon,
		OrderNum:   resp.OrderNum,
		ViewPath:   resp.ViewPath,
		KeepAlive:  resp.KeepAlive,
		IsShow:     resp.IsShow,
	}

	return &types.Resp{
		Code: 200,
		Data: info,
		Msg:  "success",
	}, nil
}
