package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/sys/common"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func PersonHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {

		l := logic.NewPersonLogic(r.Context(), ctx)
		resp, err := l.Person()
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
