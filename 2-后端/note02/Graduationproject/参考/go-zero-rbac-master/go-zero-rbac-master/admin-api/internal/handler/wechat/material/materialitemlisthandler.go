/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：materialitemlisthandler.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:04:45
 * # 上次修改时间：2021年07月17日 11:54:49
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/wechat/material"
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/rest/httpx"
)

func MaterialItemListHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var req types.MaterialItemListReq
		if err := httpx.Parse(r, &req); err != nil {
			httpx.Error(w, err)
			return
		}

		l := logic.NewMaterialItemListLogic(r.Context(), ctx)
		resp, err := l.MaterialItemList(req)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
