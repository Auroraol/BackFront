/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sysroledepartmentmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 21:34:59
 * # 上次修改时间：2021年07月11日 21:23:08
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package sysmodel

import (
	"aso/utils/gconv"
	"database/sql"
	"fmt"
	"strings"
	"time"

	"github.com/tal-tech/go-zero/core/stores/sqlc"
	"github.com/tal-tech/go-zero/core/stores/sqlx"
	"github.com/tal-tech/go-zero/core/stringx"
	"github.com/tal-tech/go-zero/tools/goctl/model/sql/builderx"
)

var (
	sysRoleDepartmentFieldNames          = builderx.RawFieldNames(&SysRoleDepartment{})
	sysRoleDepartmentRows                = strings.Join(sysRoleDepartmentFieldNames, ",")
	sysRoleDepartmentRowsExpectAutoSet   = strings.Join(stringx.Remove(sysRoleDepartmentFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysRoleDepartmentRowsWithPlaceHolder = strings.Join(stringx.Remove(sysRoleDepartmentFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysRoleDepartmentModel interface {
		Insert(data SysRoleDepartment) (sql.Result, error)
		Inserts(data []*SysRoleDepartment) error
		FindOne(id int64) (*SysRoleDepartment, error)
		FindListByDept(id int64) ([]int64, error)
		Update(data SysRoleDepartment) error
		Delete(id int64) error
		DeleteByField(ids []int64, field string) error
	}

	defaultSysRoleDepartmentModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysRoleDepartment struct {
		Id           int64     `db:"id"`           // ID
		CreateTime   time.Time `db:"createTime"`   // 创建时间
		UpdateTime   time.Time `db:"updateTime"`   // 更新时间
		RoleId       int64     `db:"roleId"`       // 角色ID
		DepartmentId int64     `db:"departmentId"` // 部门ID
	}
)

func NewSysRoleDepartmentModel(conn sqlx.SqlConn) SysRoleDepartmentModel {
	return &defaultSysRoleDepartmentModel{
		conn:  conn,
		table: "`sys_role_department`",
	}
}

func (m *defaultSysRoleDepartmentModel) Insert(data SysRoleDepartment) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?)", m.table, sysRoleDepartmentRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.RoleId, data.DepartmentId)
	return ret, err
}

// Inserts 多条插入
func (m *defaultSysRoleDepartmentModel) Inserts(data []*SysRoleDepartment) error {
	// 存放 (?, ?) 的slice
	valueStrings := make([]string, 0, len(data))
	// 存放values的slice
	valueArgs := make([]interface{}, 0, len(data)*2)
	// 遍历users准备相关数据
	for _, v := range data {
		// 此处占位符要与插入值的个数对应
		valueStrings = append(valueStrings, "(?, ?)")
		valueArgs = append(valueArgs, v.RoleId)
		valueArgs = append(valueArgs, v.DepartmentId)
	}
	// 自行拼接要执行的具体语句
	stmt := fmt.Sprintf("INSERT INTO %s (%s) VALUES %s", m.table, sysRoleDepartmentRowsExpectAutoSet, strings.Join(valueStrings, ","))
	_, err := m.conn.Exec(stmt, valueArgs...)
	return err
}

func (m *defaultSysRoleDepartmentModel) FindOne(id int64) (*SysRoleDepartment, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysRoleDepartmentRows, m.table)
	var resp SysRoleDepartment
	err := m.conn.QueryRow(&resp, query, id)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultSysRoleDepartmentModel) FindListByDept(id int64) ([]int64, error) {
	query := fmt.Sprintf("select `departmentId` from %s where `roleId` = ?", m.table)
	var resp []int64
	err := m.conn.QueryRows(&resp, query, id)
	switch err {
	case nil:
		return resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultSysRoleDepartmentModel) Update(data SysRoleDepartment) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysRoleDepartmentRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.RoleId, data.DepartmentId, data.Id)
	return err
}

func (m *defaultSysRoleDepartmentModel) Delete(id int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, id)
	return err
}

func (m *defaultSysRoleDepartmentModel) DeleteByField(ids []int64, field string) error {
	query := fmt.Sprintf("delete from %s where `%s` in(%v)", m.table, field, gconv.ReplaceByInt(ids))
	_, err := m.conn.Exec(query)
	return err
}

// RefreshPerms 刷新权限
// @param uid int64
// @return err
func (m defaultSysRoleDepartmentModel) RefreshPerms(uid int64) {
	// todo 待完成
}

// DepartmentIds 根据用户ID获得部门权限
// @param uid int64
// @return 部门ID数组, err
func (m defaultSysRoleDepartmentModel) DepartmentIds(uid int64) {
	// todo 待完成
}
