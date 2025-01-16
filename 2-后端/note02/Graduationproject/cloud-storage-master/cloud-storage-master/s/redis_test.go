package s

import (
	"fmt"
	"github.com/gomodule/redigo/redis"
	"testing"
)

func TestRedisService_Do(t *testing.T) {
	res, err := redis.String(RedisService.Do("GET", "aaa"))

	if err != nil {
		t.Error(err)
		return
	}

	fmt.Println(res)
}

func TestRedisService_DoWithCheck(t *testing.T) {
	err := RedisService.DoWithCheck("SET", "aaa", "bbb")
	if err != nil {
		t.Error(err)
		return
	}

}
