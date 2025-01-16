/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：apppageitemlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:55:19
 * # 上次修改时间：2021年07月15日 23:04:54
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"context"

	"aso/rpc/app/app"
	"aso/rpc/app/internal/svc"

	"github.com/tal-tech/go-zero/core/logx"
)

type AppPageItemLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewAppPageItemLogic(ctx context.Context, svcCtx *svc.ServiceContext) *AppPageItemLogic {
	return &AppPageItemLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// AppPageItem 相册列表
func (l *AppPageItemLogic) AppPageItem(in *app.AppPageItemReq) (*app.AppPageItemResp, error) {
	resp, err := l.svcCtx.ItemModel.FindAll(in.ClassifyId, in.Page, in.Size)
	total, _ := l.svcCtx.ItemModel.Count(in.ClassifyId)
	if err != nil {
		return nil, err
	}
	var list []*app.AppPageItemData
	for _, item := range *resp {
		list = append(list, &app.AppPageItemData{
			Id:         item.Id,
			CreateTime: item.CreateTime.Format("2006-04-02 15:04:05"),
			UpdateTime: item.UpdateTime.Format("2006-04-02 15:04:05"),
			Url:        item.Url,
			Type:       item.Type,
			ClassifyId: item.ClassifyId,
		})
	}
	return &app.AppPageItemResp{
		List:  list,
		Total: total,
	}, nil
}
