/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：uploadhandler.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:55:19
 * # 上次修改时间：2021年07月15日 23:38:10
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/app/item"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func UploadHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		l := logic.NewUploadLogic(r.Context(), ctx)
		resp, err := l.Upload(r)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
