/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：servicecontext.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 22:06:56
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package svc

import (
	"aso/admin-api/internal/config"
	"aso/admin-api/internal/middleware"
	"aso/rpc/app/appclient"
	"aso/rpc/sys/sysclient"
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"aso/utils/sdk/wechat"
	"github.com/tal-tech/go-zero/core/stores/cache"
	"github.com/tal-tech/go-zero/core/syncx"
	"github.com/tal-tech/go-zero/rest"
	"github.com/tal-tech/go-zero/zrpc"
)

type ServiceContext struct {
	Config    config.Config
	Authority rest.Middleware
	Cache     cache.Cache
	WechatSdk wechat.Wc
	Sys       sysclient.Sys
	Wechat    wechatclient.Wechat
	App       appclient.App
}

func NewServiceContext(c config.Config) *ServiceContext {
	//缓存
	redis := cache.New(c.CacheRedis, syncx.NewSharedCalls(), cache.NewStat("admin-api"), errorx.ErrNotFound)
	return &ServiceContext{
		Config:    c,
		Authority: middleware.NewAuthorityMiddleware(c).Handle,
		Sys:       sysclient.NewSys(zrpc.MustNewClient(c.SysRpc)),
		Wechat:    wechatclient.NewWechat(zrpc.MustNewClient(c.WechatRpc)),
		Cache:     redis,
		WechatSdk: wechat.NewWechatSdk(c.CacheRedis[0].Host, c.CacheRedis[0].Pass, redis),
		App:       appclient.NewApp(zrpc.MustNewClient(c.AppRpc)),
	}
}
