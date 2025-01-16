/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatkeyreplaymodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月20日 23:41:30
 * # 上次修改时间：2021年07月18日 23:03:53
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package wechatmodel

import (
	"aso/utils/gconv"
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
	wechatKeyReplayFieldNames          = builderx.RawFieldNames(&WechatKeyReplay{})
	wechatKeyReplayRows                = strings.Join(wechatKeyReplayFieldNames, ",")
	wechatKeyReplayRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatKeyReplayFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	wechatKeyReplayRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatKeyReplayFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	WechatKeyReplayModel interface {
		Insert(data WechatKeyReplay) (sql.Result, error)
		FindOne(value interface{}, field interface{}) (*WechatKeyReplay, error)
		Update(data WechatKeyReplay) error
		Delete(id int64) error
		Deletes(ids []int64) error
		FindAll(keyWord, sort, order string, page, size int64) (*[]WechatKeyReplay, error)
		Count(keyWord string) (int64, error)
		Like(key string) (*WechatKeyReplay, error)
	}

	defaultWechatKeyReplayModel struct {
		sqlc.CachedConn
		table string
	}

	WechatKeyReplay struct {
		Id         int64     `db:"id"`
		Name       string    `db:"name"`
		Key        string    `db:"key"`   // 关键词
		Match      bool      `db:"match"` // 回复类型
		Type       string    `db:"type"`
		MediaId    int64     `db:"mediaId"` // 回复媒体内容id
		Text       string    `db:"text"`    // 回复文本
		Url        string    `db:"url"`
		CreateTime time.Time `db:"createTime"`
		UpdateTime time.Time `db:"updateTime"`
	}
)

func NewWechatKeyReplayModel(conn sqlx.SqlConn, c cache.CacheConf) WechatKeyReplayModel {
	return &defaultWechatKeyReplayModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_key_replay`",
	}
}

func (m *defaultWechatKeyReplayModel) Insert(data WechatKeyReplay) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?)", m.table, wechatKeyReplayRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Name, data.Key, data.Match, data.Type, data.MediaId, data.Text, data.Url)

	return ret, err
}

func (m *defaultWechatKeyReplayModel) FindOne(value interface{}, field interface{}) (*WechatKeyReplay, error) {
	var resp WechatKeyReplay
	query := fmt.Sprintf("select %s from %s where `%s` = ? limit 1", wechatKeyReplayRows, m.table, field)
	err := m.QueryRowNoCache(&resp, query, value)

	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultWechatKeyReplayModel) Update(data WechatKeyReplay) error {
	wechatKeyReplayIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatKeyReplayIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, wechatKeyReplayRowsWithPlaceHolder)
		return conn.Exec(query, data.Name, data.Key, data.Match, data.Type, data.MediaId, data.Text, data.Url, data.Id)
	}, wechatKeyReplayIdKey)
	return err
}

func (m *defaultWechatKeyReplayModel) Delete(id int64) error {

	wechatKeyReplayIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatKeyReplayIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatKeyReplayIdKey)
	return err
}

func (m *defaultWechatKeyReplayModel) Deletes(ids []int64) error {
	wechatKeyReplayIdKey := fmt.Sprintf("%s", prefix.CacheWechatKeyReplayIdPrefix)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` in (%v)", m.table, gconv.ReplaceByInt(ids))
		return conn.Exec(query)
	}, wechatKeyReplayIdKey)
	return err
}

func (m defaultWechatKeyReplayModel) FindAll(keyWord, sort, order string, page, size int64) (*[]WechatKeyReplay, error) {
	search := ""
	if len(keyWord) > 0 {
		search = fmt.Sprintf("and `name` LIKE %s", keyWord)
	}
	orders := ""
	if len(order) > 0 {
		orders = fmt.Sprintf("order by `%s` %s", order, sort)
	}
	var resp []WechatKeyReplay
	query := fmt.Sprintf("select %s from %s where `id` <> 1 %s %s limit ?,?", wechatKeyReplayRows, m.table, search, orders)
	err := m.QueryRowsNoCache(&resp, query, (page-1)*size, size)

	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m defaultWechatKeyReplayModel) Count(keyWord string) (int64, error) {
	search := ""
	if len(keyWord) > 0 {
		search = fmt.Sprintf("where `name` LIKE %s", keyWord)
	}
	var count int64
	query := fmt.Sprintf("select count(*) as count from %s %s", m.table, search)
	err := m.QueryRowNoCache(&count, query)

	switch err {
	case nil:
		return count, nil
	case sqlc.ErrNotFound:
		return 0, ErrNotFound
	default:
		return 0, err
	}
}

func (m *defaultWechatKeyReplayModel) Like(key string) (*WechatKeyReplay, error) {
	var resp WechatKeyReplay
	query := fmt.Sprintf("select %s from %s where `key` LIKE ?", wechatKeyReplayRows, m.table)
	err := m.QueryRowNoCache(&resp, query, "%"+key+"%")

	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultWechatKeyReplayModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", prefix.CacheWechatKeyReplayIdPrefix, primary)
}

func (m *defaultWechatKeyReplayModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatKeyReplayRows, m.table)
	return conn.QueryRow(v, query, primary)
}
