/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sysrolemodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 21:47:31
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package sysmodel

import (
	"aso/utils/gconv"
	"aso/utils/prefix"
	"database/sql"
	"fmt"
	"github.com/tal-tech/go-zero/core/stores/cache"
	"strings"
	"time"

	"github.com/tal-tech/go-zero/core/stores/sqlc"
	"github.com/tal-tech/go-zero/core/stores/sqlx"
	"github.com/tal-tech/go-zero/core/stringx"
	"github.com/tal-tech/go-zero/tools/goctl/model/sql/builderx"
)

var (
	sysRoleFieldNames          = builderx.RawFieldNames(&SysRole{})
	sysRoleRows                = strings.Join(sysRoleFieldNames, ",")
	sysRoleRowsExpectAutoSet   = strings.Join(stringx.Remove(sysRoleFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysRoleRowsWithPlaceHolder = strings.Join(stringx.Remove(sysRoleFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysRoleModel interface {
		Insert(data SysRole) (sql.Result, error)
		FindOne(id int64) (*SysRole, error)
		Update(data SysRole) error
		Delete(id int64) error
		Deletes(ids []int64) error
		Count(keyWord string) (int64, error)
		FindList() (*[]SysRole, error)
		FindAll(keyWord, sort, order string, page, size int64) (*[]SysRole, error)
	}

	defaultSysRoleModel struct {
		sqlc.CachedConn
		table string
	}

	SysRole struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		Name       string    `db:"name"`       // 名称
		Label      string    `db:"label"`      // 角色标签
		Remark     string    `db:"remark"`     // 备注
		Relevance  int64     `db:"relevance"`  // 数据权限是否关联上下级
	}
)

func NewSysRoleModel(conn sqlx.SqlConn, c cache.CacheConf) SysRoleModel {
	return &defaultSysRoleModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`sys_role`",
	}
}

func (m *defaultSysRoleModel) Insert(data SysRole) (sql.Result, error) {
	ret, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?)", m.table, sysRoleRowsExpectAutoSet)
		return conn.Exec(query, data.Name, data.Label, data.Remark, data.Relevance)
	})
	return ret, err
}

func (m *defaultSysRoleModel) FindOne(id int64) (*SysRole, error) {
	sysRoleIdKey := fmt.Sprintf("%s%v", prefix.CacheSysRoleIdPrefix, id)
	var resp SysRole
	err := m.QueryRow(&resp, sysRoleIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysRoleRows, m.table)
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

func (m *defaultSysRoleModel) Update(data SysRole) error {
	sysRoleIdKey := fmt.Sprintf("%s%v", prefix.CacheSysRoleIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysRoleRowsWithPlaceHolder)
		return conn.Exec(query, data.Name, data.Label, data.Remark, data.Relevance, data.Id)
	}, sysRoleIdKey)
	return err
}

func (m *defaultSysRoleModel) Delete(id int64) error {
	sysRoleIdKey := fmt.Sprintf("%s%v", prefix.CacheSysRoleIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, sysRoleIdKey)

	return err
}

func (m *defaultSysRoleModel) Deletes(ids []int64) error {
	sysRoleIdKey := fmt.Sprintf("%s%v", prefix.CacheSysRoleIdPrefix, ids)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` in (%v)", m.table, gconv.ReplaceByInt(ids))
		return conn.Exec(query)
	}, sysRoleIdKey)

	return err
}

func (m defaultSysRoleModel) Count(keyWord string) (int64, error) {
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

func (m defaultSysRoleModel) FindList() (*[]SysRole, error) {
	var resp []SysRole
	sysRoleKey := fmt.Sprintf("%s", prefix.CacheSysRoleAllPrefix)
	err := m.QueryRow(&resp, sysRoleKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` <> 1", sysRoleRows, m.table)
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

func (m defaultSysRoleModel) FindAll(keyWord, sort, order string, page, size int64) (*[]SysRole, error) {
	search := ""
	if len(keyWord) > 0 {
		search = fmt.Sprintf("and `name` LIKE %s", keyWord)
	}
	orders := ""
	if len(order) > 0 {
		orders = fmt.Sprintf("order by `%s` %s", order, sort)
	}
	var resp []SysRole
	query := fmt.Sprintf("select %s from %s where `id` <> 1 %s %s limit ?,?", sysRoleRows, m.table, search, orders)
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
