package handler

import (
	"aso/utils/common/errorx"
	"net/http"

	"aso/admin-api/internal/logic/wechat/config"
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"

	"github.com/tal-tech/go-zero/rest/httpx"
)

func ConfigUpdateReplyHandler(ctx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var req types.ConfigReplyUpdateReq
		if err := httpx.Parse(r, &req); err != nil {
			httpx.Error(w, errorx.NewDefaultError(err.Error()))
			return
		}

		l := logic.NewConfigUpdateReplyLogic(r.Context(), ctx)
		resp, err := l.ConfigUpdateReply(req)
		if err != nil {
			httpx.Error(w, errorx.NewDefaultError(err.Error()))
		} else {
			httpx.OkJson(w, resp)
		}
	}
}
