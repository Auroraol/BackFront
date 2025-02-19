/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatmaterialmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月17日 11:31:02
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
	wechatMaterialFieldNames          = builderx.RawFieldNames(&WechatMaterial{})
	wechatMaterialRows                = strings.Join(wechatMaterialFieldNames, ",")
	wechatMaterialRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatMaterialFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	wechatMaterialRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatMaterialFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"

	cacheWechatMaterialIdPrefix = "cache:wechatMaterial:id:"
)

type (
	WechatMaterialModel interface {
		Insert(data WechatMaterial) (sql.Result, error)
		FindOne(id int64) (*WechatMaterial, error)
		Update(data WechatMaterial) error
		Delete(id int64) error
		Count() (int64, error)
		FindList(page, size int64) (*[]WechatMaterial, error)
	}

	defaultWechatMaterialModel struct {
		sqlc.CachedConn
		table string
	}

	WechatMaterial struct {
		Id         int64     `db:"id"`    // 主键
		Title      string    `db:"title"` // 文章标题
		CreateTime time.Time `db:"createTime"`
		UpdateTime time.Time `db:"updateTime"`
		Type       bool      `db:"type"`  // 0单图文、1多图文
		Thumb      string    `db:"thumb"` // 缩略图
	}
)

func NewWechatMaterialModel(conn sqlx.SqlConn, c cache.CacheConf) WechatMaterialModel {
	return &defaultWechatMaterialModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_material`",
	}
}

func (m *defaultWechatMaterialModel) Insert(data WechatMaterial) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?)", m.table, wechatMaterialRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Title, data.Type, data.Thumb)

	return ret, err
}

func (m *defaultWechatMaterialModel) FindOne(id int64) (*WechatMaterial, error) {
	wechatMaterialIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialIdPrefix, id)
	var resp WechatMaterial
	err := m.QueryRow(&resp, wechatMaterialIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatMaterialRows, m.table)
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

func (m *defaultWechatMaterialModel) Update(data WechatMaterial) error {
	wechatMaterialIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, wechatMaterialRowsWithPlaceHolder)
		return conn.Exec(query, data.Title, data.Type, data.Thumb, data.Id)
	}, wechatMaterialIdKey)
	return err
}

func (m *defaultWechatMaterialModel) Delete(id int64) error {

	wechatMaterialIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatMaterialIdKey)
	return err
}

func (m defaultWechatMaterialModel) Count() (int64, error) {
	var count int64
	query := fmt.Sprintf("select count(*) as count from %s", m.table)
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

func (m defaultWechatMaterialModel) FindList(page, size int64) (*[]WechatMaterial, error) {
	var resp []WechatMaterial
	query := fmt.Sprintf("select %s from %s limit ?,?", wechatMaterialRows, m.table)
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

func (m *defaultWechatMaterialModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", cacheWechatMaterialIdPrefix, primary)
}

func (m *defaultWechatMaterialModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatMaterialRows, m.table)
	return conn.QueryRow(v, query, primary)
}
