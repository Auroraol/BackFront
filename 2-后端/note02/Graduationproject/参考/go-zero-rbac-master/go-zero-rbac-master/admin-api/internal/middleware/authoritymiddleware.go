/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：authoritymiddleware.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:40
 * # 上次修改时间：2021年07月10日 22:47:27
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package middleware

import (
	"aso/admin-api/internal/config"
	"aso/utils/common"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"aso/utils/prefix"
	"errors"
	"fmt"
	"github.com/tal-tech/go-zero/core/stores/cache"
	"github.com/tal-tech/go-zero/core/syncx"
	"github.com/tal-tech/go-zero/rest/httpx"
	"net/http"
	"regexp"
	"strings"
	"time"
)

var (
	TokenExpired     = errors.New("Token is expired")
	TokenNotValidYet = errors.New("Token not active yet")
	TokenMalformed   = errors.New("That's not even a token")
	TokenInvalid     = errors.New("Couldn't handle this token:")
)

type AuthorityMiddleware struct {
	Config     config.Config
	SigningKey []byte
	Cache      cache.Cache
}

func NewAuthorityMiddleware(c config.Config) *AuthorityMiddleware {
	//缓存
	redis := cache.New(c.CacheRedis, syncx.NewSharedCalls(), cache.NewStat("admin-api"), errorx.ErrNotFound)
	return &AuthorityMiddleware{
		Config:     c,
		SigningKey: []byte(c.Auth.AccessSecret),
		Cache:      redis,
	}
}

func (m *AuthorityMiddleware) Handle(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// 判断权限
		hasPermission := false
		url := r.URL.Path
		uid := r.Context().Value("userId")
		// 现将缓存的菜单权限替换
		if gconv.Int64(uid) != 1 {
			cacheKey := fmt.Sprintf("%s%v", prefix.CacheSysPermsUidPrefix, uid)
			var perms []string
			_ = m.Cache.Get(cacheKey, &perms)
			for _, data := range gconv.Strings(perms) {
				re3, _ := regexp.Compile(":")
				rep := re3.ReplaceAllString(gconv.String(data), "/")
				// 如果有多条的情况拆分成数组
				permsArr := gconv.Split(rep, ",")
				for _, v := range permsArr {
					// 字符串匹配
					if ok := strings.EqualFold("/api/"+v, url); ok {
						hasPermission = true
						break
					}
				}
			}
		} else {
			hasPermission = true
		}

		// 验证不通过直接返回403
		if !hasPermission {
			httpx.Error(w, errorx.NewCodeError(403, "无访问权限"))
			return
		}

		// 从redis获取token
		headerToken := r.Header.Get("Authorization")
		token := ""
		cacheKey := fmt.Sprintf("%s%v", prefix.CacheSysUserTokenPrefix, uid)
		err := m.Cache.Get(cacheKey, &token)
		if err != nil {
			httpx.Error(w, errorx.NewCodeError(401, "未登录"))
			return
		}
		// 限制多端登录
		if ok := strings.EqualFold(headerToken, token); !ok {
			httpx.Error(w, errorx.NewCodeError(401, "未登录"))
			return
		}
		// 解析token
		claims, _ := common.ParseToken(headerToken, m.Config.Auth.AccessSecret)
		// 判断是否已过期
		if gconv.Int64(claims["exp"])-time.Now().Unix() < m.Config.Auth.BufferTime {
			// 创建token
			now := time.Now().Unix()
			newToken, _ := common.GetJwtToken(m.Config.Auth.AccessSecret, now, m.Config.Auth.AccessExpire, gconv.Int64(uid))
			// 将token写入到返回头，由前端获取
			w.Header().Set("new-token", newToken)
			times := time.Second * time.Duration(m.Config.Auth.AccessExpire)
			err = m.Cache.SetWithExpire(cacheKey, newToken, times)
			if err != nil {
				httpx.Error(w, errorx.NewCodeError(401, "未登录"))
				return
			}
		}

		// 继续执行
		next(w, r)
	}
}
