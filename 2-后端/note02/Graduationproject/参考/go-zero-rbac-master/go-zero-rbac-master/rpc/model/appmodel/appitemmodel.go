/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：appitemmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月16日 00:23:07
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package appmodel

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
	appItemFieldNames          = builderx.RawFieldNames(&AppItem{})
	appItemRows                = strings.Join(appItemFieldNames, ",")
	appItemRowsExpectAutoSet   = strings.Join(stringx.Remove(appItemFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	appItemRowsWithPlaceHolder = strings.Join(stringx.Remove(appItemFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"

	cacheAppItemIdPrefix = "cache:appItem:id:"
)

type (
	AppItemModel interface {
		Insert(data AppItem) (sql.Result, error)
		FindOne(id int64) (*AppItem, error)
		Update(data AppItem) error
		Delete(id int64) error
		Count(cid int64) (int64, error)
		FindAll(cid, page, size int64) (*[]AppItem, error)
	}

	defaultAppItemModel struct {
		sqlc.CachedConn
		table string
	}

	AppItem struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		Url        string    `db:"url"`        // 地址
		Type       string    `db:"type"`       // 类型
		ClassifyId int64     `db:"classifyId"` // 分类ID
	}
)

func NewAppItemModel(conn sqlx.SqlConn, c cache.CacheConf) AppItemModel {
	return &defaultAppItemModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`app_item`",
	}
}

func (m *defaultAppItemModel) Insert(data AppItem) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?)", m.table, appItemRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Url, data.Type, data.ClassifyId)

	return ret, err
}

func (m *defaultAppItemModel) FindOne(id int64) (*AppItem, error) {
	appItemIdKey := fmt.Sprintf("%s%v", cacheAppItemIdPrefix, id)
	var resp AppItem
	err := m.QueryRow(&resp, appItemIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", appItemRows, m.table)
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

func (m defaultAppItemModel) Count(cid int64) (int64, error) {
	where := ""
	if cid > 0 {
		where = fmt.Sprintf(" where `classifyId` = %v", cid)
	}
	var count int64
	query := fmt.Sprintf("select count(*) as count from %s %s", m.table, where)
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

func (m defaultAppItemModel) FindAll(cid, page, size int64) (*[]AppItem, error) {
	where := ""
	if cid > 0 {
		where = fmt.Sprintf(" where `classifyId` = %v", cid)
	}
	var resp []AppItem
	query := fmt.Sprintf("select %s from %s %s limit ?,?", appItemRows, m.table, where)
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

func (m *defaultAppItemModel) Update(data AppItem) error {
	appItemIdKey := fmt.Sprintf("%s%v", cacheAppItemIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, appItemRowsWithPlaceHolder)
		return conn.Exec(query, data.Url, data.Type, data.ClassifyId, data.Id)
	}, appItemIdKey)
	return err
}

func (m *defaultAppItemModel) Delete(id int64) error {

	appItemIdKey := fmt.Sprintf("%s%v", cacheAppItemIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, appItemIdKey)
	return err
}

func (m *defaultAppItemModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", cacheAppItemIdPrefix, primary)
}

func (m *defaultAppItemModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", appItemRows, m.table)
	return conn.QueryRow(v, query, primary)
}
