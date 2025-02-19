package config

import (
	"github.com/tal-tech/go-zero/core/stores/cache"
	"github.com/tal-tech/go-zero/zrpc"
)

type Config struct {
	zrpc.RpcServerConf
	CacheRedis cache.CacheConf
	Mysql      struct {
		Datasource string
	}
	JWT struct {
		AccessSecret string
		AccessExpire int64
	}
	EncryptKey string
}
