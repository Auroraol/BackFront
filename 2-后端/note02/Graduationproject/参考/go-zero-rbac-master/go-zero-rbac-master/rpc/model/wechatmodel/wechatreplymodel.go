/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatreplymodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 17:10:38
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package wechatmodel

import (
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
	wechatReplyFieldNames          = builderx.RawFieldNames(&WechatReply{})
	wechatReplyRows                = strings.Join(wechatReplyFieldNames, ",")
	wechatReplyRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatReplyFieldNames, "`createTime`", "`update_time`"), ",")
	wechatReplyRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatReplyFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"

	cacheWechatReplyIdPrefix = "cache:wechatReply:id:"
)

type (
	WechatReplyModel interface {
		Insert(data WechatReply) (sql.Result, error)
		FindOne(id int64) (*WechatReply, error)
		Update(data WechatReply) error
		Delete(id int64) error
	}

	defaultWechatReplyModel struct {
		sqlc.CachedConn
		table string
	}

	WechatReply struct {
		Id         int64     `db:"id"`
		Type       string    `db:"type"`       // keyword关键词autoReply自动回复attention关注时回复
		Value      string    `db:"value"`      // 键值
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
	}
)

func NewWechatReplyModel(conn sqlx.SqlConn, c cache.CacheConf) WechatReplyModel {
	return &defaultWechatReplyModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_reply`",
	}
}

func (m *defaultWechatReplyModel) Insert(data WechatReply) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?)", m.table, wechatReplyRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Id, data.Type, data.Value)

	return ret, err
}

func (m *defaultWechatReplyModel) FindOne(id int64) (*WechatReply, error) {
	wechatReplyIdKey := fmt.Sprintf("%s%v", cacheWechatReplyIdPrefix, id)
	var resp WechatReply
	err := m.QueryRow(&resp, wechatReplyIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatReplyRows, m.table)
		return conn.QueryRow(v, query, id)
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

func (m *defaultWechatReplyModel) Update(data WechatReply) error {
	wechatReplyIdKey := fmt.Sprintf("%s%v", cacheWechatReplyIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, wechatReplyRowsWithPlaceHolder)
		return conn.Exec(query, data.Type, data.Value, data.Id)
	}, wechatReplyIdKey)
	return err
}

func (m *defaultWechatReplyModel) Delete(id int64) error {

	wechatReplyIdKey := fmt.Sprintf("%s%v", cacheWechatReplyIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatReplyIdKey)
	return err
}

func (m *defaultWechatReplyModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", cacheWechatReplyIdPrefix, primary)
}

func (m *defaultWechatReplyModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatReplyRows, m.table)
	return conn.QueryRow(v, query, primary)
}
