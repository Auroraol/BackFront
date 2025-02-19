/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sdk.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月13日 23:45:30
 * # 上次修改时间：2021年07月13日 23:13:24
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package wechat

import (
	"fmt"
	"github.com/silenceper/wechat/v2"
	wchatcache "github.com/silenceper/wechat/v2/cache"
	"github.com/silenceper/wechat/v2/officialaccount"
	offConfig "github.com/silenceper/wechat/v2/officialaccount/config"
	"github.com/tal-tech/go-zero/core/stores/cache"
)

// OfficialAccount ExampleOfficialAccount 公众号操作样例
type OfficialAccount struct {
	wc     *wechat.Wechat
	Wechat *officialaccount.OfficialAccount
}

type (
	Wc interface {
		Register(cfg map[string]string) *OfficialAccount
	}
	defaultWechat struct {
		Cache      cache.Cache
		Host, Pass string
	}
)

// NewWechatSdk 声明方法
// @Description: 主要用到上下文菜单中
// @param host 主机
// @param pass 密码配置
// @param cache 缓存配置
// @return *defaultWechat
func NewWechatSdk(host, pass string, cache cache.Cache) *defaultWechat {
	return &defaultWechat{
		Cache: cache,
		Host:  host,
		Pass:  pass,
	}
}

// Register 注册微信sdk方法
// @Description: 注册微信方法，并链式调用sdk中的方法
// @receiver w
// @param cfg 微信相关配置
// @return *OfficialAccount
func (w *defaultWechat) Register(cfg map[string]string) *OfficialAccount {
	wc := wechat.NewWechat()

	redisOpts := &wchatcache.RedisOpts{
		Host:     fmt.Sprintf("%s", w.Host),
		Password: w.Pass,
		Database: 1,
	}
	redisCache := wchatcache.NewRedis(redisOpts)
	wc.SetCache(redisCache)
	offCfg := &offConfig.Config{
		AppID:          cfg["appId"],
		AppSecret:      cfg["appSecret"],
		Token:          cfg["Token"],
		EncodingAESKey: cfg["EncodingAESKey"],
		Cache:          redisCache,
	}
	officialAccount := wc.GetOfficialAccount(offCfg)
	return &OfficialAccount{
		wc:     wc,
		Wechat: officialAccount,
	}
}
