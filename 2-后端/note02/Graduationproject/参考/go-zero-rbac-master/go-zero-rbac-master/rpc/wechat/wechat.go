/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechat.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月12日 20:06:19
 * # 上次修改时间：2021年07月11日 22:39:43
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package main

import (
	"flag"
	"fmt"

	"aso/rpc/wechat/internal/config"
	"aso/rpc/wechat/internal/server"
	"aso/rpc/wechat/internal/svc"
	"aso/rpc/wechat/wechat"

	"github.com/tal-tech/go-zero/core/conf"
	"github.com/tal-tech/go-zero/zrpc"
	"google.golang.org/grpc"
)

var configFile = flag.String("f", "etc/wechat.yaml", "the config file")

func main() {
	flag.Parse()

	var c config.Config
	conf.MustLoad(*configFile, &c)
	ctx := svc.NewServiceContext(c)
	srv := server.NewWechatServer(ctx)

	s := zrpc.MustNewServer(c.RpcServerConf, func(grpcServer *grpc.Server) {
		wechat.RegisterWechatServer(grpcServer, srv)
	})
	defer s.Stop()

	fmt.Printf("Starting rpc server at %s...\n", c.ListenOn)
	s.Start()
}
