/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialaddlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 11:36:35
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/model/wechatmodel"
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"
	"context"
	"time"

	"github.com/tal-tech/go-zero/core/logx"
)

type MaterialAddLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMaterialAddLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MaterialAddLogic {
	return &MaterialAddLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MaterialAdd 图文添加
func (l *MaterialAddLogic) MaterialAdd(in *wechat.MaterialAddReq) (*wechat.MaterialAddResp, error) {
	resp, err := l.svcCtx.MaterialModel.Insert(wechatmodel.WechatMaterial{
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
		Title:      in.Title,
		Type:       in.Type,
		Thumb:      in.Thumb,
	})
	if err != nil {
		return nil, err
	}
	id, _ := resp.LastInsertId()
	return &wechat.MaterialAddResp{
		Id: id,
	}, nil
}
