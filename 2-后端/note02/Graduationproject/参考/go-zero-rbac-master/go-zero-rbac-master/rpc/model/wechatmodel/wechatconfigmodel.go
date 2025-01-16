/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatconfigmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月13日 23:45:30
 * # 上次修改时间：2021年07月12日 23:00:02
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package wechatmodel

import (
	"aso/utils/prefix"
	"database/sql"
	"fmt"
	"strings"
	"time"

	"github.com/tal-tech/go-zero/core/stores/cache"
	"github.com/tal-tech/go-zero/core/stores/sqlc"
	"github.com/tal-tech/go-zero/core/stores/sqlx"
	"github.com/tal-tech/go-zero/core/stringx"
	"github.com/tal-tech/go-zero/tools/goctl/model/sql/builderx"
)

var (
	wechatConfigFieldNames          = builderx.RawFieldNames(&WechatConfig{})
	wechatConfigRows                = strings.Join(wechatConfigFieldNames, ",")
	wechatConfigRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatConfigFieldNames, "`id`", "`type`", "`createTime`", "`updateTime`"), ",")
	wechatConfigRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatConfigFieldNames, "`id`", "`type`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	WechatConfigModel interface {
		Insert(data WechatConfig) (sql.Result, error)
		FindOne(t int64) (*WechatConfig, error)
		Update(data WechatConfig) error
		Delete(id int64) error
	}

	defaultWechatConfigModel struct {
		sqlc.CachedConn
		table string
	}

	WechatConfig struct {
		Id         int64     `db:"id"`   // 主键
		Type       int64     `db:"type"` // 1微信，2小程序
		Value      string    `db:"value"`
		CreateTime time.Time `db:"createTime"`
		UpdateTime time.Time `db:"updateTime"`
	}
)

func NewWechatConfigModel(conn sqlx.SqlConn, c cache.CacheConf) WechatConfigModel {
	return &defaultWechatConfigModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_config`",
	}
}

func (m *defaultWechatConfigModel) Insert(data WechatConfig) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?)", m.table, wechatConfigRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Type, data.Value)

	return ret, err
}

func (m *defaultWechatConfigModel) FindOne(t int64) (*WechatConfig, error) {
	wechatConfigIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatConfigIdPrefix, t)
	var resp WechatConfig
	err := m.QueryRow(&resp, wechatConfigIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `type` = ? limit 1", wechatConfigRows, m.table)
		return conn.QueryRow(v, query, t)
	})
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultWechatConfigModel) Update(data WechatConfig) error {
	wechatConfigIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatConfigIdPrefix, data.Type)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `type` = ?", m.table, wechatConfigRowsWithPlaceHolder)
		return conn.Exec(query, data.Value, data.Type)
	}, wechatConfigIdKey)
	return err
}

func (m *defaultWechatConfigModel) Delete(id int64) error {

	wechatConfigIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatConfigIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatConfigIdKey)
	return err
}

func (m *defaultWechatConfigModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", prefix.CacheWechatConfigIdPrefix, primary)
}

func (m *defaultWechatConfigModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatConfigRows, m.table)
	return conn.QueryRow(v, query, primary)
}
