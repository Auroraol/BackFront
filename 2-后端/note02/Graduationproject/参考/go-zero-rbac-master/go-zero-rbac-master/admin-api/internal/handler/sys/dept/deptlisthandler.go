package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/sys/dept"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func DeptListHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {

		l := logic.NewDeptListLogic(r.Context(), ctx)
		resp, err := l.DeptList()
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
