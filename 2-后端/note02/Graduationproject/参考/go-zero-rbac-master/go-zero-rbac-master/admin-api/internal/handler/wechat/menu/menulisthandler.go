package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/wechat/menu"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func MenuListHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {

		l := logic.NewMenuListLogic(r.Context(), ctx)
		resp, err := l.MenuList()
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
