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
	taskInfoFieldNames          = builderx.RawFieldNames(&TaskInfo{})
	taskInfoRows                = strings.Join(taskInfoFieldNames, ",")
	taskInfoRowsExpectAutoSet   = strings.Join(stringx.Remove(taskInfoFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	taskInfoRowsWithPlaceHolder = strings.Join(stringx.Remove(taskInfoFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	TaskInfoModel interface {
		Insert(data TaskInfo) (sql.Result, error)
		FindOne(id int64) (*TaskInfo, error)
		Update(data TaskInfo) error
		Delete(ids []int64) error
	}

	defaultTaskInfoModel struct {
		conn  sqlx.SqlConn
		table string
	}

	TaskInfo struct {
		Id          int64        `db:"id"`          // ID
		CreateTime  time.Time    `db:"createTime"`  // 创建时间
		UpdateTime  time.Time    `db:"updateTime"`  // 更新时间
		JobId       string       `db:"jobId"`       // 任务ID
		RepeatConf  string       `db:"repeatConf"`  // 任务配置
		Name        string       `db:"name"`        // 名称
		Cron        string       `db:"cron"`        // cron
		Limit       string       `db:"limit"`       // 最大执行次数 不传为无限次
		Every       string       `db:"every"`       // 每间隔多少毫秒执行一次 如果cron设置了 这项设置就无效
		Remark      string       `db:"remark"`      // 备注
		Status      int64        `db:"status"`      // 状态 0:停止 1：运行
		StartDate   sql.NullTime `db:"startDate"`   // 开始时间
		EndDate     sql.NullTime `db:"endDate"`     // 结束时间
		Data        string       `db:"data"`        // 数据
		Service     string       `db:"service"`     // 执行的service实例ID
		Type        int64        `db:"type"`        // 状态 0:系统 1：用户
		NextRunTime sql.NullTime `db:"nextRunTime"` // 下一次执行时间
		TaskType    int64        `db:"taskType"`    // 状态 0:cron 1：时间间隔
	}
)

func NewTaskInfoModel(conn sqlx.SqlConn) TaskInfoModel {
	return &defaultTaskInfoModel{
		conn:  conn,
		table: "`task_info`",
	}
}

func (m *defaultTaskInfoModel) Insert(data TaskInfo) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", m.table, taskInfoRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.JobId, data.RepeatConf, data.Name, data.Cron, data.Limit, data.Every, data.Remark, data.Status, data.StartDate, data.EndDate, data.Data, data.Service, data.Type, data.NextRunTime, data.TaskType)
	return ret, err
}

func (m *defaultTaskInfoModel) FindOne(id int64) (*TaskInfo, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", taskInfoRows, m.table)
	var resp TaskInfo
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

func (m *defaultTaskInfoModel) Update(data TaskInfo) error {
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, taskInfoRowsWithPlaceHolder)
	_, err := m.conn.Exec(query, data.JobId, data.RepeatConf, data.Name, data.Cron, data.Limit, data.Every, data.Remark, data.Status, data.StartDate, data.EndDate, data.Data, data.Service, data.Type, data.NextRunTime, data.TaskType, data.Id)
	return err
}

func (m *defaultTaskInfoModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
	_, err := m.conn.Exec(query, ids)
	return err
}
