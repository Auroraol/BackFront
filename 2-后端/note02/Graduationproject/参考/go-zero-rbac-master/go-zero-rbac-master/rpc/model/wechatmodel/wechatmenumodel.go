/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatmenumodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月22日 20:25:59
 * # 上次修改时间：2021年07月22日 20:25:50
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package wechatmodel

import (
	"aso/utils/prefix"
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
	wechatMenuFieldNames          = builderx.RawFieldNames(&WechatMenu{})
	wechatMenuRows                = strings.Join(wechatMenuFieldNames, ",")
	wechatMenuRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatMenuFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	wechatMenuRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatMenuFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	WechatMenuModel interface {
		Insert(data WechatMenu) (sql.Result, error)
		FindOne(id int64, field string) (*WechatMenu, error)
		Update(data WechatMenu) error
		Delete(id int64) error
		Clear() error
		FindList(pid int64) (*[]WechatMenu, error)
	}

	defaultWechatMenuModel struct {
		sqlc.CachedConn
		table string
	}

	WechatMenu struct {
		Id         int64     `db:"id"`
		Pid        int64     `db:"pid"`        // 上级ID
		Type       string    `db:"type"`       // 菜单类型
		Name       string    `db:"name"`       // 菜单名
		Url        string    `db:"url"`        // 跳转网页地址
		MediaId    int64     `db:"media_id"`   // 素材ID
		AppId      string    `db:"app_id"`     // 小程序ID
		PagePath   string    `db:"page_path"`  // 小程序页面路径
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		MsgType    string    `db:"msgType"`    // 消息类型
		Text       string    `db:"text"`       // 文本消息内容
		Pic        string    `db:"pic"`        // 图片消息内容
		Audio      string    `db:"audio"`      // 音频消息内容
		Video      string    `db:"video"`      // 视频消息内容
	}
)

func NewWechatMenuModel(conn sqlx.SqlConn, c cache.CacheConf) WechatMenuModel {
	return &defaultWechatMenuModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_menu`",
	}
}

func (m *defaultWechatMenuModel) Insert(data WechatMenu) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", m.table, wechatMenuRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Pid, data.Type, data.Name, data.Url, data.MediaId, data.AppId, data.PagePath, data.MsgType, data.Text, data.Pic, data.Audio, data.Video)

	return ret, err
}

func (m *defaultWechatMenuModel) FindOne(id int64, field string) (*WechatMenu, error) {
	var resp WechatMenu
	query := fmt.Sprintf("select %s from %s where `%s` = ? limit 1", wechatMenuRows, m.table, field)
	err := m.QueryRowsNoCache(&resp, query, id)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultWechatMenuModel) Update(data WechatMenu) error {
	wechatMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatMenuIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, wechatMenuRowsWithPlaceHolder)
		return conn.Exec(query, data.Pid, data.Type, data.Name, data.Url, data.MediaId, data.AppId, data.PagePath, data.MsgType, data.Text, data.Pic, data.Audio, data.Video, data.Id)
	}, wechatMenuIdKey)
	return err
}

func (m *defaultWechatMenuModel) Delete(id int64) error {
	wechatMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatMenuIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatMenuIdKey)
	return err
}

func (m defaultWechatMenuModel) Clear() error {
	query := fmt.Sprintf("TRUNCATE TABLE %s", m.table)
	_, err := m.ExecNoCache(query)
	return err
}

func (m defaultWechatMenuModel) FindList(pid int64) (*[]WechatMenu, error) {
	var resp []WechatMenu
	query := fmt.Sprintf("select %s from %s where `pid` = ?", wechatMenuRows, m.table)
	err := m.QueryRowsNoCache(&resp, query, pid)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultWechatMenuModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", prefix.CacheWechatMenuIdPrefix, primary)
}

func (m *defaultWechatMenuModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatMenuRows, m.table)
	return conn.QueryRow(v, query, primary)
}
