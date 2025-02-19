/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/09 1:23
 */

package util

import (
	"github.com/gomodule/redigo/redis"
)

// 定义全局pool
var pool *redis.Pool

// InitRedis 当启动程序时，就初始化连接池
func InitRedis(ConfigAddress string) {
	pool = &redis.Pool{
		MaxIdle:     8,   //最大空闲连接数
		MaxActive:   0,   //表示和数据库的最大连接数， 0 表示没限制
		IdleTimeout: 100, //最大空闲时间
		Dial: func() (redis.Conn, error) { //初始化连接的代码，连接哪个ip
			return redis.Dial("tcp", ConfigAddress)
		},
	}
}

// GetRedis 封装从池中获取连接的方法
func GetRedis() redis.Conn {
	conn := pool.Get()
	return conn
}

// CloseRedis 关闭当前pool连接
func CloseRedis() {
	err := pool.Close()
	if err != nil {
		return
	}
}
