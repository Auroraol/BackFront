/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：deptorderhandler.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:58
 * # 上次修改时间：2021年07月11日 01:48:43
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package handler

import (
	"aso/utils/common/errorx"
	"net/http"

	"aso/admin-api/internal/logic/sys/dept"
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/rest/httpx"
)

func DeptOrderHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var req types.DeptOrderReq
		if err := httpx.Parse(r, &req); err != nil {
			httpx.Error(w, errorx.NewDefaultError(err.Error()))
			return
		}

		l := logic.NewDeptOrderLogic(r.Context(), ctx)
		resp, err := l.DeptOrder(req)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
