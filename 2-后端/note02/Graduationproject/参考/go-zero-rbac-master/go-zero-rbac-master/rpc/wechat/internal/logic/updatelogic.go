/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：updatelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月12日 20:06:19
 * # 上次修改时间：2021年07月12日 00:07:57
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/wechatmodel"
	"context"
	"time"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type UpdateLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewUpdateLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UpdateLogic {
	return &UpdateLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// Update 更新配置
func (l *UpdateLogic) Update(in *wechat.WechatInfoUpdateRequest) (*wechat.WechatInfoUpdateResponse, error) {
	err := l.svcCtx.ConfigModel.Update(wechatmodel.WechatConfig{
		Type:       in.Type,
		Value:      in.Value,
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
	})
	if err != nil {
		return nil, err
	}

	return &wechat.WechatInfoUpdateResponse{}, nil
}
