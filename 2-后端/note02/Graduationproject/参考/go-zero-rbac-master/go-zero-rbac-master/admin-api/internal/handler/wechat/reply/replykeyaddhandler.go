package handler

import (
	"net/http"

	"aso/admin-api/internal/logic/wechat/reply"
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/rest/httpx"
)

func ReplyKeyAddHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var req types.AddReplyReq
		if err := httpx.Parse(r, &req); err != nil {
			httpx.Error(w, err)
			return
		}

		l := logic.NewReplyKeyAddLogic(r.Context(), ctx)
		resp, err := l.ReplyKeyAdd(req)
		if err != nil {
			httpx.Error(w, err)
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
