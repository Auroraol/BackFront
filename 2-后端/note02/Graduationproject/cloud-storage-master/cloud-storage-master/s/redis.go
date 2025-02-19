package s

import (
	"errors"
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"github.com/gomodule/redigo/redis"
	"reflect"
	"time"
)

var RedisService *redisService

type redisService struct {
	pool *redis.Pool
}

func init() {
	RedisService = new(redisService)
	RedisService.pool = NewRedisPool(entitys.MyAppConfig.RedisHost, entitys.MyAppConfig.RedisPassword,
		entitys.MyAppConfig.RedisMaxIdle, entitys.MyAppConfig.RedisMaxActive, entitys.MyAppConfig.RedisMaxIdle)
}

//address		链接地址
//password		链接密码
//maxIdle		最大空闲连接数
//maxActive		最大的激活连接数，同时最多有N个连接：0=不限制连接数
//idleTimeout	空闲连接等待时间，超过此时间后，空闲连接将被关闭：0=超时也不断开空闲连接
func NewRedisPool(address string, password string, maxIdle int, maxActive int, idleTimeout int) *redis.Pool {
	redisPool := &redis.Pool{
		MaxIdle:     maxIdle,
		MaxActive:   maxActive,
		IdleTimeout: time.Duration(idleTimeout) * time.Second,
		Dial: func() (redis.Conn, error) {

			c, err := redis.DialURL("redis://" + address)
			if err != nil {
				return nil, fmt.Errorf("redis connection error: %s", err)
			}

			//验证redis密码
			if password != "" {
				if _, authErr := c.Do("AUTH", password); authErr != nil {
					c.Close()
					return nil, fmt.Errorf("redis auth password error: %s", authErr)
				}
			}

			return c, err
		},
		TestOnBorrow: func(c redis.Conn, t time.Time) error {
			if time.Since(t) < time.Minute {
				return nil
			}
			_, err := c.Do("PING")
			return err
		},
	}

	return redisPool
}

//执行命令 查询类命令可用这个
func (this *redisService) Do(commandName string, args ...interface{}) (reply interface{}, err error) {
	conn := this.pool.Get()
	if err := conn.Err(); err != nil {
		return nil, err
	}
	defer conn.Close()

	return conn.Do(commandName, args...)
}

//执行命令 设置类命令可用这个
func (this *redisService) DoWithCheck(commandName string, args ...interface{}) error {
	res, err := checkCommandResult(this.Do(commandName, args...))
	if err != nil {
		return err
	}

	if !res {
		return errors.New("数据入缓存失败")
	}

	return nil
}

//检查命令执行结果是
func checkCommandResult(ok interface{}, err error) (bool, error) {
	if err != nil {
		return false, err
	}

	switch value := ok.(type) {
	case nil:
		return false, errors.New("redisdb: nil returned")
	case int, int64, uint64:
		if fmt.Sprintf("%d", value) != "1" {
			return false, fmt.Errorf("(integer)  %v", value)
		}

	case []int:
		for _, v := range value {
			if v != 1 {
				return false, fmt.Errorf("(integer)  %v", value)
			}
		}
	case []int64:
		for _, v := range value {
			if v != 1 {
				return false, fmt.Errorf("(integer[]int64)  %v", value)
			}
		}
	case []uint64:
		for _, v := range value {
			if v != 1 {
				return false, fmt.Errorf("(integer[]uint64)  %v", value)
			}
		}

	case string:

		if value != "OK" && value != "QUEUED" {
			return false, fmt.Errorf("(string) %v", value)
		}

	case []string:
		for _, v := range value {
			if v != "OK" && v != "QUEUED" {
				return false, fmt.Errorf("(string)%v", value)
			}
		}
	default:
		return false, fmt.Errorf("错误的类型返回: %v.", reflect.TypeOf(ok))
	}

	return true, nil
}
