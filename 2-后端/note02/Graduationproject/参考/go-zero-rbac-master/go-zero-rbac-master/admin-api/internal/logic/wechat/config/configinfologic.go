/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：configinfologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月17日 23:31:34
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
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"context"
	"strings"

	"github.com/tal-tech/go-zero/core/logx"
)

type ConfigInfoLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewConfigInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) ConfigInfoLogic {
	return ConfigInfoLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ConfigInfoLogic) ConfigInfo(req types.ConfigInfoReq) (*types.Resp, error) {
	resp, err := l.svcCtx.Wechat.Info(l.ctx, &wechatclient.WechatInfoRequest{
		Type: req.Type,
	})
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	// 针对微信菜单特殊处理，替换空值为[]
	if req.Type == 3 || req.Type == 4 {
		resp.Value = strings.Replace(resp.Value, "null", "[]", -1)
	}

	return &types.Resp{
		Code: 200,
		Data: resp.Value,
		Msg:  "success",
	}, nil
}
