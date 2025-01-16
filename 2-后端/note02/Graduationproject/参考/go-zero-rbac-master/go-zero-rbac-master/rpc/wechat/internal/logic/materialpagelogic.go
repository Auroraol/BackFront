/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialpagelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 10:35:28
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

type MaterialPageLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewMaterialPageLogic(ctx context.Context, svcCtx *svc.ServiceContext) *MaterialPageLogic {
	return &MaterialPageLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// MaterialPage 素材管理
func (l *MaterialPageLogic) MaterialPage(in *wechat.MaterialReq) (*wechat.MaterialResp, error) {
	resp, err := l.svcCtx.MaterialModel.FindList(in.Page, in.Size)
	count, _ := l.svcCtx.MaterialModel.Count()
	if err != nil {
		return nil, err
	}
	var list []*wechat.MaterialData
	for _, v := range *resp {
		list = append(list, &wechat.MaterialData{
			Id:         v.Id,
			Title:      v.Title,
			CreateTime: v.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: v.UpdateTime.Format("2006-04-02 15:04:05"),
			Type:       v.Type,
			Thumb:      v.Thumb,
		})
	}

	return &wechat.MaterialResp{
		List:  list,
		Total: count,
	}, nil
}
