/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：uploadlogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:55:19
 * # 上次修改时间：2021年07月16日 00:19:36
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/admin-api/internal/svc"
	"aso/admin-api/internal/types"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"aso/utils/upload"
	"context"
	"fmt"
	"net/http"
	"os"
	"strings"
	"time"

	"github.com/tal-tech/go-zero/core/logx"
)

type UploadLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUploadLogic(ctx context.Context, svcCtx *svc.ServiceContext) UploadLogic {
	return UploadLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UploadLogic) Upload(r *http.Request) (*types.Resp, error) {
	file, err := upload.FromFile(r, "file")
	if err != nil {
		return nil, errorx.NewDefaultError(err.Error())
	}
	//获取文件后缀
	extString := upload.Ext(file.Filename)
	if extString == "" {
		return nil, errorx.NewDefaultError("上传的文件不受支持")
	}
	extStrSlice := l.svcCtx.Config.Upload.Type
	if !upload.ContainArray(extString, extStrSlice) {
		return nil, errorx.NewDefaultError(fmt.Sprintf("只支持：%v", l.svcCtx.Config.Upload.Path))
	}

	// 上传到的路径
	filepath := l.svcCtx.Config.Upload.Path + "/" + time.Now().Format("20060102") + "/"
	//如果没有filepath文件目录就创建一个
	if _, err := os.Stat(filepath); err != nil {
		if !os.IsExist(err) {
			err := os.MkdirAll(filepath, os.ModePerm)
			if err != nil {
				return nil, errorx.NewDefaultError(fmt.Sprintf("io错误：%v", err.Error()))
			}
		}
	}
	// 文件名格式 自己可以改 建议保证唯一性
	file.Filename = gconv.String(r.Form.Get("key"))
	path := filepath + file.Filename //路径+文件名上传

	// 上传文件到指定的目录
	err = upload.SaveUploadedFile(file, path)
	if err != nil {
		return nil, errorx.NewDefaultError(fmt.Sprintf("上传错误：%v", err.Error()))
	}
	return &types.Resp{
		Code: 200,
		Data: l.svcCtx.Config.Upload.Domain + strings.Trim(path, "."),
		Msg:  "success",
	}, nil
}
