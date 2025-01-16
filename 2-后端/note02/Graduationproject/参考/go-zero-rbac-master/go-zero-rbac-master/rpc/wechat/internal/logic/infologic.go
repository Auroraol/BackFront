/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：infologic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月12日 20:06:19
 * # 上次修改时间：2021年07月11日 23:17:15
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"context"

	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/logx"
)

type InfoLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewInfoLogic(ctx context.Context, svcCtx *svc.ServiceContext) *InfoLogic {
	return &InfoLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// Info 微信信息
func (l *InfoLogic) Info(in *wechat.WechatInfoRequest) (*wechat.WechatInfoResponse, error) {
	resp, err := l.svcCtx.ConfigModel.FindOne(in.Type)
	if err != nil {
		return nil, err
	}

	return &wechat.WechatInfoResponse{
		Id:    resp.Id,
		Type:  resp.Type,
		Value: resp.Value,
	}, nil
}
