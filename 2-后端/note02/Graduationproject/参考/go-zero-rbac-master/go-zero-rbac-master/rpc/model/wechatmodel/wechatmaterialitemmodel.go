/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatmaterialitemmodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月17日 11:40:02
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
	wechatMaterialItemFieldNames          = builderx.RawFieldNames(&WechatMaterialItem{})
	wechatMaterialItemRows                = strings.Join(wechatMaterialItemFieldNames, ",")
	wechatMaterialItemRowsExpectAutoSet   = strings.Join(stringx.Remove(wechatMaterialItemFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	wechatMaterialItemRowsWithPlaceHolder = strings.Join(stringx.Remove(wechatMaterialItemFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"

	cacheWechatMaterialItemIdPrefix = "cache:wechatMaterialItem:id:"
)

type (
	WechatMaterialItemModel interface {
		Insert(data WechatMaterialItem) (sql.Result, error)
		FindOne(id int64) (*WechatMaterialItem, error)
		FindAllByMediaKey(key int64) (*[]WechatMaterialItem, error)
		Update(data WechatMaterialItem) error
		Delete(id int64) error
	}

	defaultWechatMaterialItemModel struct {
		sqlc.CachedConn
		table string
	}

	WechatMaterialItem struct {
		Id               int64     `db:"id"`                 // ID
		CreateTime       time.Time `db:"createTime"`         // 创建时间
		UpdateTime       time.Time `db:"updateTime"`         // 更新时间
		Url              string    `db:"url"`                // 地址
		Type             string    `db:"type"`               // 类型
		Title            string    `db:"title"`              // 标题
		Author           string    `db:"author"`             // 作者
		Cover            string    `db:"cover"`              // 图片封面
		Summary          string    `db:"summary"`            // 摘要
		Content          string    `db:"content"`            // 正文
		ContentSourceUrl string    `db:"content_source_url"` // 原文URL
		Sort             int64     `db:"sort"`               // 排序
		Hits             int64     `db:"hits"`               // 阅读次数
		MediaKey         int64     `db:"media_key"`
	}
)

func NewWechatMaterialItemModel(conn sqlx.SqlConn, c cache.CacheConf) WechatMaterialItemModel {
	return &defaultWechatMaterialItemModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`wechat_material_item`",
	}
}

func (m *defaultWechatMaterialItemModel) Insert(data WechatMaterialItem) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", m.table, wechatMaterialItemRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.Url, data.Type, data.Title, data.Author, data.Cover, data.Summary, data.Content, data.ContentSourceUrl, data.Sort, data.Hits, data.MediaKey)

	return ret, err
}

func (m *defaultWechatMaterialItemModel) FindOne(id int64) (*WechatMaterialItem, error) {
	wechatMaterialItemIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialItemIdPrefix, id)
	var resp WechatMaterialItem
	err := m.QueryRow(&resp, wechatMaterialItemIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatMaterialItemRows, m.table)
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

func (m *defaultWechatMaterialItemModel) FindAllByMediaKey(key int64) (*[]WechatMaterialItem, error) {
	wechatMaterialItemIdKey := fmt.Sprintf("%s%v", prefix.CacheWechatMaterialItemKeyPrefix, key)
	var resp []WechatMaterialItem
	err := m.QueryRow(&resp, wechatMaterialItemIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `media_key` = ?", wechatMaterialItemRows, m.table)
		return conn.QueryRows(v, query, key)
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

func (m *defaultWechatMaterialItemModel) Update(data WechatMaterialItem) error {
	wechatMaterialItemIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialItemIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, wechatMaterialItemRowsWithPlaceHolder)
		return conn.Exec(query, data.Url, data.Type, data.Title, data.Author, data.Cover, data.Summary, data.Content, data.ContentSourceUrl, data.Sort, data.Hits, data.MediaKey, data.Id)
	}, wechatMaterialItemIdKey)
	return err
}

func (m *defaultWechatMaterialItemModel) Delete(id int64) error {

	wechatMaterialItemIdKey := fmt.Sprintf("%s%v", cacheWechatMaterialItemIdPrefix, id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
		return conn.Exec(query, id)
	}, wechatMaterialItemIdKey)
	return err
}

func (m *defaultWechatMaterialItemModel) formatPrimary(primary interface{}) string {
	return fmt.Sprintf("%s%v", cacheWechatMaterialItemIdPrefix, primary)
}

func (m *defaultWechatMaterialItemModel) queryPrimary(conn sqlx.SqlConn, v, primary interface{}) error {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", wechatMaterialItemRows, m.table)
	return conn.QueryRow(v, query, primary)
}
