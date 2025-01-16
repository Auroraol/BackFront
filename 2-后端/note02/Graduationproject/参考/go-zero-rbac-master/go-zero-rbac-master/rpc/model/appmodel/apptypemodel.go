/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：apptypemodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 22:59:59
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package appmodel

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
	appTypeFieldNames          = builderx.RawFieldNames(&AppType{})
	appTypeRows                = strings.Join(appTypeFieldNames, ",")
	appTypeRowsExpectAutoSet   = strings.Join(stringx.Remove(appTypeFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	appTypeRowsWithPlaceHolder = strings.Join(stringx.Remove(appTypeFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	AppTypeModel interface {
		Insert(data AppType) (sql.Result, error)
		FindOne(id int64) (*AppType, error)
		Update(data AppType) error
		Deletes(ids []int64) error
		FindList() (*[]AppType, error)
	}

	defaultAppTypeModel struct {
		sqlc.CachedConn
		table string
	}

	AppType struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		Name       string    `db:"name"`       // 类别名称
	}
)

func NewAppTypeModel(conn sqlx.SqlConn, c cache.CacheConf) AppTypeModel {
	return &defaultAppTypeModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`app_type`",
	}
}

func (m *defaultAppTypeModel) Insert(data AppType) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?)", m.table, appTypeRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Name)
	_ = m.CachedConn.DelCache(prefix.CacheAppTypeAllPrefix)

	return ret, err
}

func (m *defaultAppTypeModel) FindOne(id int64) (*AppType, error) {
	appTypeIdKey := fmt.Sprintf("%s%v", prefix.CacheAppTypeIdPrefix, id)
	var resp AppType
	err := m.QueryRow(&resp, appTypeIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", appTypeRows, m.table)
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

func (m *defaultAppTypeModel) Update(data AppType) error {
	appTypeIdKey := fmt.Sprintf("%s%v", prefix.CacheAppTypeIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, appTypeRowsWithPlaceHolder)
		return conn.Exec(query, data.Name, data.Id)
	}, appTypeIdKey)
	_ = m.CachedConn.DelCache(prefix.CacheAppTypeAllPrefix)

	return err
}

func (m *defaultAppTypeModel) Deletes(ids []int64) error {
	appTypeIdKey := fmt.Sprintf("%s", prefix.CacheAppTypeIdPrefix)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` in(%v)", m.table, gconv.ReplaceByInt(ids))
		return conn.Exec(query)
	}, appTypeIdKey)
	_ = m.CachedConn.DelCache(prefix.CacheAppTypeAllPrefix)

	return err
}

func (m defaultAppTypeModel) FindList() (*[]AppType, error) {
	var resp []AppType
	appTypeIdKey := fmt.Sprintf("%s", prefix.CacheAppTypeAllPrefix)
	err := m.QueryRow(&resp, appTypeIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s", appTypeRows, m.table)
		return conn.QueryRows(v, query)
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
