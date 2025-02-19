package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/sys/role"
	"aso/admin-api/internal/svc"
	"github.com/tal-tech/go-zero/rest/httpx"
)

func RoleListHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		l := logic.NewRoleListLogic(r.Context(), ctx)
		resp, err := l.RoleList()
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
