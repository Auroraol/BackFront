package taskmodel

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
	taskLogFieldNames          = builderx.RawFieldNames(&TaskLog{})
	taskLogRows                = strings.Join(taskLogFieldNames, ",")
	taskLogRowsExpectAutoSet   = strings.Join(stringx.Remove(taskLogFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	taskLogRowsWithPlaceHolder = strings.Join(stringx.Remove(taskLogFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	TaskLogModel interface {
		Insert(data TaskLog) (sql.Result, error)
		FindOne(id int64) (*TaskLog, error)
		Update(data TaskLog) error
		Delete(ids []int64) error
	}

	defaultTaskLogModel struct {
		conn  sqlx.SqlConn
		table string
	}

	TaskLog struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		TaskId     string    `db:"taskId"`     // 任务ID
		Status     int64     `db:"status"`     // 状态 0:失败 1：成功
		Detail     string    `db:"detail"`     // 详情描述
	}
)

func NewTaskLogModel(conn sqlx.SqlConn) TaskLogModel {
	return &defaultTaskLogModel{
		conn:  conn,
		table: "`task_log`",
	}
}

func (m *defaultTaskLogModel) Insert(data TaskLog) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?)", m.table, taskLogRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.TaskId, data.Status, data.Detail)
	return ret, err
}

func (m *defaultTaskLogModel) FindOne(id int64) (*TaskLog, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", taskLogRows, m.table)
	var resp TaskLog
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

func (m *defaultTaskLogModel) Update(data TaskLog) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, taskLogRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.TaskId, data.Status, data.Detail, data.Id)
	return err
}

func (m *defaultTaskLogModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}
