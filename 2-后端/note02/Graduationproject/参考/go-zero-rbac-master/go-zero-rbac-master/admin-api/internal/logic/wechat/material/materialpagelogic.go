/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialpagelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 10:39:02
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"

	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialPageLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewMaterialPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) MaterialPageLogic {
	return MaterialPageLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *MaterialPageLogic) MaterialPage(req types.PageData) (*types.PageResp, error) {
	resp, err := l.svcCtx.Wechat.MaterialPage(l.ctx, &wechatclient.MaterialReq{
		Page:  req.Page,
		Size:  req.Size,
		Total: req.Total,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	return &types.PageResp{
		Code: 200,
		Data: types.PageListData{
			List: resp.List,
			Pagination: types.PageData{
				Page:  req.Page,
				Size:  req.Size,
				Total: resp.Total,
			},
		},
		Msg: "success",
	}, nil
}
