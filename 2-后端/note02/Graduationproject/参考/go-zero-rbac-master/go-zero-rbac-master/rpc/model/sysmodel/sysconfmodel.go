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
	sysConfFieldNames          = builderx.RawFieldNames(&SysConf{})
	sysConfRows                = strings.Join(sysConfFieldNames, ",")
	sysConfRowsExpectAutoSet   = strings.Join(stringx.Remove(sysConfFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysConfRowsWithPlaceHolder = strings.Join(stringx.Remove(sysConfFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysConfModel interface {
		Insert(data SysConf) (sql.Result, error)
		FindOne(id int64) (*SysConf, error)
		FindOneByCKey(cKey string) (*SysConf, error)
		Update(data SysConf) error
		Delete(ids []int64) error
	}

	defaultSysConfModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysConf struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		CKey       string    `db:"cKey"`       // 配置键
		CValue     string    `db:"cValue"`     // 配置值
	}
)

func NewSysConfModel(conn sqlx.SqlConn) SysConfModel {
	return &defaultSysConfModel{
		conn:  conn,
		table: "`sys_conf`",
	}
}

func (m *defaultSysConfModel) Insert(data SysConf) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?)", m.table, sysConfRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.CKey, data.CValue)
	return ret, err
}

func (m *defaultSysConfModel) FindOne(id int64) (*SysConf, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysConfRows, m.table)
	var resp SysConf
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

func (m *defaultSysConfModel) FindOneByCKey(cKey string) (*SysConf, error) {
	var resp SysConf
	query := fmt.Sprintf("select %s from %s where `cKey` = ? limit 1", sysConfRows, m.table)
	err := m.conn.QueryRow(&resp, query, cKey)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultSysConfModel) Update(data SysConf) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysConfRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.CKey, data.CValue, data.Id)
	return err
}

func (m *defaultSysConfModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}
