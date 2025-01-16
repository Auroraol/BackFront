package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/sys/common"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func PermsMenusHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {

		l := logic.NewPermsMenusLogic(r.Context(), ctx)
		resp, err := l.PermsMenus()
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
