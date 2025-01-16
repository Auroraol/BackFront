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
	sysParamFieldNames          = builderx.RawFieldNames(&SysParam{})
	sysParamRows                = strings.Join(sysParamFieldNames, ",")
	sysParamRowsExpectAutoSet   = strings.Join(stringx.Remove(sysParamFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysParamRowsWithPlaceHolder = strings.Join(stringx.Remove(sysParamFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysParamModel interface {
		Insert(data SysParam) (sql.Result, error)
		FindOne(id int64) (*SysParam, error)
		Update(data SysParam) error
		Delete(ids []int64) error
	}

	defaultSysParamModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysParam struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		KeyName    string    `db:"keyName"`    // 键位
		Name       string    `db:"name"`       // 名称
		Data       string    `db:"data"`       // 数据
		DataType   int64     `db:"dataType"`   // 数据类型 0:字符串 1：数组 2：键值对
		Remark     string    `db:"remark"`     // 备注
	}
)

func NewSysParamModel(conn sqlx.SqlConn) SysParamModel {
	return &defaultSysParamModel{
		conn:  conn,
		table: "`sys_param`",
	}
}

func (m *defaultSysParamModel) Insert(data SysParam) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?)", m.table, sysParamRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.KeyName, data.Name, data.Data, data.DataType, data.Remark)
	return ret, err
}

func (m *defaultSysParamModel) FindOne(id int64) (*SysParam, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysParamRows, m.table)
	var resp SysParam
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

func (m *defaultSysParamModel) Update(data SysParam) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysParamRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.KeyName, data.Name, data.Data, data.DataType, data.Remark, data.Id)
	return err
}

func (m *defaultSysParamModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}
