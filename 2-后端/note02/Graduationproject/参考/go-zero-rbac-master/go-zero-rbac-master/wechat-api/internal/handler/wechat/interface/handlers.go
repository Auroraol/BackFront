/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：handlers.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月16日 15:37:05
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package handler

import (
	logic "aso/wechat-api/internal/logic/wechat/interface"
	"net/http"

	"aso/wechat-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func WechatInterfaceHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		l := logic.NewWechatInterfaceLogic(r.Context(), ctx)
		err := l.WechatInterface(w, r)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.Ok(w)
		}
	}
}

func WechatHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		l := logic.NewWechatInterfaceLogic(r.Context(), ctx)
		err := l.WechatInterface(w, r)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.Ok(w)
		}
	}
}
