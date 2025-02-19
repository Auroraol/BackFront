/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sysdepartmentmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 02:01:40
 * # 上次修改时间：2021年07月11日 01:56:06
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
	sysDepartmentFieldNames                  = builderx.RawFieldNames(&SysDepartment{})
	sysDepartmentRows                        = strings.Join(sysDepartmentFieldNames, ",")
	sysDepartmentRowsExpectAutoSet           = strings.Join(stringx.Remove(sysDepartmentFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysDepartmentRowsWithPlaceHolder         = strings.Join(stringx.Remove(sysDepartmentFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
	sysDepartmentRowsWithNoUpdatePlaceHolder = strings.Join(stringx.Remove(sysDepartmentFieldNames, "`id`", "`createTime`", "`name`"), "=?,") + "=?"
)

type (
	SysDepartmentModel interface {
		Insert(data SysDepartment) (sql.Result, error)
		FindPid(ids []int64) (int64, error)
		FindOne(id int64) (*SysDepartment, error)
		Update(data SysDepartment) error
		UpdateByOrder(data SysDepartment) error
		Delete(ids []int64) error
		FindAll() (*[]SysDepartment, error)
	}

	defaultSysDepartmentModel struct {
		sqlc.CachedConn
		table string
	}

	SysDepartment struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		Name       string    `db:"name"`       // 部门名称
		ParentId   int64     `db:"parentId"`   // 上级部门ID
		OrderNum   int64     `db:"orderNum"`   // 排序
	}
)

func NewSysDepartmentModel(conn sqlx.SqlConn, c cache.CacheConf) SysDepartmentModel {
	return &defaultSysDepartmentModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`sys_department`",
	}
}

func (m *defaultSysDepartmentModel) Insert(data SysDepartment) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?)", m.table, sysDepartmentRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Name, data.ParentId, data.OrderNum)
	_ = m.CachedConn.DelCache(prefix.CacheSysDepartmentAllPrefix)

	return ret, err
}

func (m *defaultSysDepartmentModel) FindPid(ids []int64) (int64, error) {
	sysDepartmentIdKey := fmt.Sprintf("%s%v", prefix.CacheSysDepartmentIdPrefix, gconv.ReplaceByInt(ids))
	var resp int64
	err := m.QueryRow(&resp, sysDepartmentIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select `parentId` from %s where `id` in (%v) limit 1", m.table, gconv.ReplaceByInt(ids))
		return conn.QueryRow(v, query)
	})
	switch err {
	case nil:
		return resp, nil
	case sqlc.ErrNotFound:
		return 0, ErrNotFound
	default:
		return 0, err
	}
}

func (m *defaultSysDepartmentModel) FindOne(id int64) (*SysDepartment, error) {
	sysDepartmentIdKey := fmt.Sprintf("%s%v", prefix.CacheSysDepartmentIdPrefix, id)
	var resp SysDepartment
	err := m.QueryRow(&resp, sysDepartmentIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysDepartmentRows, m.table)
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

func (m *defaultSysDepartmentModel) Update(data SysDepartment) error {
	sysDepartmentIdKey := fmt.Sprintf("%s%v", prefix.CacheSysDepartmentIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysDepartmentRowsWithPlaceHolder)
		return conn.Exec(query, data.Name, data.ParentId, data.OrderNum, data.Id)
	}, sysDepartmentIdKey)

	return err
}

func (m *defaultSysDepartmentModel) UpdateByOrder(data SysDepartment) error {
	sysDepartmentIdKey := fmt.Sprintf("%s%v", prefix.CacheSysDepartmentIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysDepartmentRowsWithNoUpdatePlaceHolder)
		return conn.Exec(query, data.UpdateTime, data.ParentId, data.OrderNum, data.Id)
	}, sysDepartmentIdKey)
	_ = m.CachedConn.DelCache(prefix.CacheSysDepartmentAllPrefix)

	return err
}

func (m *defaultSysDepartmentModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` in (%v)", m.table, gconv.ReplaceByInt(ids))
	_, err := m.ExecNoCache(query)
	_ = m.CachedConn.DelCache(prefix.CacheSysDepartmentAllPrefix)

	return err
}

func (m *defaultSysDepartmentModel) FindAll() (*[]SysDepartment, error) {
	sysDepartmentIdKey := fmt.Sprintf("%s", prefix.CacheSysDepartmentAllPrefix)
	var resp []SysDepartment
	err := m.QueryRow(&resp, sysDepartmentIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s ORDER BY `orderNum` ASC", sysDepartmentRows, m.table)
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
