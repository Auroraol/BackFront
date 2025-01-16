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
	sysLogFieldNames          = builderx.RawFieldNames(&SysLog{})
	sysLogRows                = strings.Join(sysLogFieldNames, ",")
	sysLogRowsExpectAutoSet   = strings.Join(stringx.Remove(sysLogFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysLogRowsWithPlaceHolder = strings.Join(stringx.Remove(sysLogFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysLogModel interface {
		Insert(data SysLog) (sql.Result, error)
		FindOne(id int64) (*SysLog, error)
		Update(data SysLog) error
		Delete(ids []int64) error
	}

	defaultSysLogModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysLog struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		UserId     string    `db:"userId"`     // 用户ID
		Action     string    `db:"action"`     // 行为
		Ip         string    `db:"ip"`         // ip
		IpAddr     string    `db:"ipAddr"`     // ip地址
		Params     string    `db:"params"`     // 参数
	}
)

func NewSysLogModel(conn sqlx.SqlConn) SysLogModel {
	return &defaultSysLogModel{
		conn:  conn,
		table: "`sys_log`",
	}
}

func (m *defaultSysLogModel) Insert(data SysLog) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?)", m.table, sysLogRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.UserId, data.Action, data.Ip, data.IpAddr, data.Params)
	return ret, err
}

func (m *defaultSysLogModel) FindOne(id int64) (*SysLog, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysLogRows, m.table)
	var resp SysLog
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

func (m *defaultSysLogModel) Update(data SysLog) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysLogRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.UserId, data.Action, data.Ip, data.IpAddr, data.Params, data.Id)
	return err
}

func (m *defaultSysLogModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}
