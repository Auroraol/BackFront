/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：admin.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月16日 00:56:21
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package main

import (
	"aso/admin-api/internal/config"
	"aso/admin-api/internal/handler"
	"aso/admin-api/internal/svc"
	"aso/utils/common/errorx"
	"flag"
	"fmt"
	"github.com/tal-tech/go-zero/core/conf"
	"github.com/tal-tech/go-zero/rest"
	"github.com/tal-tech/go-zero/rest/httpx"
	"net/http"
	"strings"
)

var configFile = flag.String("f", "etc/admin-api.yaml", "the config file")

func main() {
	flag.Parse()

	var c config.Config
	conf.MustLoad(*configFile, &c)

	ctx := svc.NewServiceContext(c)
	server := rest.MustNewServer(c.RestConf)
	defer server.Stop()

	handler.RegisterHandlers(server, ctx)
	// 自定义错误
	httpx.SetErrorHandler(func(err error) (int, interface{}) {
		switch e := err.(type) {
		case *errorx.CodeError:
			return e.Data().Code, e.Data()
		default:
			return http.StatusInternalServerError, nil
		}
	})
	fmt.Printf("Starting server at %s:%d...\n", c.Host, c.Port)
	// 注册静态资源路由
	RegisterHandlers(server)
	server.Start()
}

func RegisterHandlers(engine *rest.Server) {
	//这里注册
	dirLevel := []string{":1", ":2", ":3", ":4", ":5", ":6", ":7", ":8"}
	prefix := "/assets/"
	dirPath := "./assets/"
	for i := 1; i < len(dirLevel); i++ {
		path := prefix + strings.Join(dirLevel[:i], "/")
		// 生成相关访问路由
		engine.AddRoute(
			rest.Route{
				Method:  http.MethodGet,
				Path:    path,
				Handler: dirHandle(prefix, dirPath),
			})
	}
	fmt.Println("静态路由生成完毕，默认7级深度")
}

//处理函数,传入文件地址
func dirHandle(prefix, fileDir string) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		handle := http.StripPrefix(prefix, http.FileServer(http.Dir(fileDir)))
		handle.ServeHTTP(w, req)
	}
}
