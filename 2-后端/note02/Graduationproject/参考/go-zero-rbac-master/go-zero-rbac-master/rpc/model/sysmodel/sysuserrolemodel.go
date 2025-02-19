package sysmodel

import (
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
	sysUserRoleFieldNames          = builderx.RawFieldNames(&SysUserRole{})
	sysUserRoleRows                = strings.Join(sysUserRoleFieldNames, ",")
	sysUserRoleRowsExpectAutoSet   = strings.Join(stringx.Remove(sysUserRoleFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysUserRoleRowsWithPlaceHolder = strings.Join(stringx.Remove(sysUserRoleFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysUserRoleModel interface {
		Insert(data SysUserRole) (sql.Result, error)
		FindOne(id int64) (*SysUserRole, error)
		Update(data SysUserRole) error
		Delete(ids []int64) error
		DeleteByUid(id int64) error
		FindRoleIds(id int64) ([]int64, error)
	}

	defaultSysUserRoleModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysUserRole struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		UserId     int64     `db:"userId"`     // 用户ID
		RoleId     int64     `db:"roleId"`     // 角色ID
	}
)

func NewSysUserRoleModel(conn sqlx.SqlConn) SysUserRoleModel {
	return &defaultSysUserRoleModel{
		conn:  conn,
		table: "`sys_user_role`",
	}
}

func (m *defaultSysUserRoleModel) Insert(data SysUserRole) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?)", m.table, sysUserRoleRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.UserId, data.RoleId)
	return ret, err
}

func (m *defaultSysUserRoleModel) FindOne(id int64) (*SysUserRole, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysUserRoleRows, m.table)
	var resp SysUserRole
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

func (m *defaultSysUserRoleModel) Update(data SysUserRole) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysUserRoleRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.UserId, data.RoleId, data.Id)
	return err
}

func (m *defaultSysUserRoleModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}

func (m *defaultSysUserRoleModel) DeleteByUid(id int64) error {
	query := fmt.Sprintf("delete from %s where `userId` = ?", m.table)
	_, err := m.conn.Exec(query, id)
	return err
}

func (m *defaultSysUserRoleModel) FindRoleIds(id int64) ([]int64, error) {
	query := fmt.Sprintf("select roleId from %s where userId = ? order by id", m.table)
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
