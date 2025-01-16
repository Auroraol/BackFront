/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sysmenumodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月14日 15:52:24
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
	sysMenuFieldNames          = builderx.RawFieldNames(&SysMenu{})
	sysMenuRows                = strings.Join(sysMenuFieldNames, ",")
	sysMenuRowsExpectAutoSet   = strings.Join(stringx.Remove(sysMenuFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysMenuRowsWithPlaceHolder = strings.Join(stringx.Remove(sysMenuFieldNames, "`id`", "`createTime`", "`updateTime`"), "=?,") + "=?"
)

type (
	SysMenuModel interface {
		Insert(data SysMenu) (sql.Result, error)
		FindOne(id int64) (*SysMenu, error)
		Update(data SysMenu) error
		Delete(ids []int64) error
		FindAll() (*[]SysMenu, error)
		PermsMenusFind(uid int64) (*[]SysMenu, []string, error)
		PermsMenusFindAll() (*[]SysMenu, []string, error)
		PermsMenusFindById(uid int64) (*[]SysMenu, []string, error)
		PermsFindById(uid int64) ([]string, error)
	}

	defaultSysMenuModel struct {
		sqlc.CachedConn
		table string
	}

	SysMenu struct {
		Id         int64     `db:"id"`         // ID
		CreateTime time.Time `db:"createTime"` // 创建时间
		UpdateTime time.Time `db:"updateTime"` // 更新时间
		ParentId   int64     `db:"parentId"`   // 父菜单ID
		Name       string    `db:"name"`       // 菜单名称
		Router     string    `db:"router"`     // 菜单地址
		Perms      string    `db:"perms"`      // 权限标识
		Type       int64     `db:"type"`       // 类型 0：目录 1：菜单 2：按钮
		Icon       string    `db:"icon"`       // 图标
		OrderNum   int64     `db:"orderNum"`   // 排序
		ViewPath   string    `db:"viewPath"`   // 视图地址
		KeepAlive  bool      `db:"keepAlive"`  // 路由缓存
		IsShow     bool      `db:"isShow"`     // 父菜单名称
	}
)

func NewSysMenuModel(conn sqlx.SqlConn, c cache.CacheConf) SysMenuModel {
	return &defaultSysMenuModel{
		CachedConn: sqlc.NewConn(conn, c),
		table:      "`sys_menu`",
	}
}

func (m *defaultSysMenuModel) Insert(data SysMenu) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", m.table, sysMenuRowsExpectAutoSet)
	ret, err := m.ExecNoCache(query, data.ParentId, data.Name, data.Router, data.Perms, data.Type, data.Icon, data.OrderNum, data.ViewPath, data.KeepAlive, data.IsShow)
	_ = m.CachedConn.DelCache(prefix.CacheSysMenuAllPrefix)
	_ = m.CachedConn.DelCache(prefix.CacheSysUserMenuAllPrefix)

	return ret, err
}

func (m *defaultSysMenuModel) FindOne(id int64) (*SysMenu, error) {
	sysMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheSysMenuIdPrefix, id)
	var resp SysMenu
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysMenuRows, m.table)
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

func (m *defaultSysMenuModel) Update(data SysMenu) error {
	sysMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheSysMenuIdPrefix, data.Id)
	_, err := m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysMenuRowsWithPlaceHolder)
		return conn.Exec(query, data.ParentId, data.Name, data.Router, data.Perms, data.Type, data.Icon, data.OrderNum, data.ViewPath, data.KeepAlive, data.IsShow, data.Id)
	}, sysMenuIdKey)
	_ = m.CachedConn.DelCache(prefix.CacheSysMenuAllPrefix)
	_ = m.CachedConn.DelCache(prefix.CacheSysUserMenuAllPrefix)
	return err
}

func (m *defaultSysMenuModel) Delete(ids []int64) error {
	var err error
	// 删除子菜单
	for _, id := range ids {
		sysMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheSysMenuIdPrefix, id)
		_, err = m.Exec(func(conn sqlx.SqlConn) (result sql.Result, err error) {
			query := fmt.Sprintf("delete from %s where `id` = ?", m.table)
			return conn.Exec(query, id)
		}, sysMenuIdKey)
		m.deleteChildMenu(id)
	}
	_ = m.CachedConn.DelCache(prefix.CacheSysMenuAllPrefix)
	return err
}

func (m defaultSysMenuModel) deleteChildMenu(id int64) {
	var delMenu []int64
	query := fmt.Sprintf("select id from %s where `parentId` = ? ", m.table)
	_ = m.QueryRowsNoCache(&delMenu, query, id)
	if len(delMenu) <= 0 {
		return
	}
	del := fmt.Sprintf("delete from %s where `id` in(%s)", m.table, gconv.ReplaceByInt(delMenu))
	_, _ = m.ExecNoCache(del)
	for _, menuId := range delMenu {
		// 递归查询删除
		m.deleteChildMenu(menuId)
	}
}

// FindAll 菜单管理查询所有菜单列表
func (m *defaultSysMenuModel) FindAll() (*[]SysMenu, error) {
	sysMenuIdKey := fmt.Sprintf("%s", prefix.CacheSysMenuAllPrefix)
	var resp []SysMenu
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s ORDER BY `orderNum` ASC", sysMenuRows, m.table)
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

// PermsMenusFind 角色菜单查询
func (m *defaultSysMenuModel) PermsMenusFind(uid int64) (*[]SysMenu, []string, error) {
	if uid != 1 {
		resp, perms, err := m.PermsMenusFindById(uid)
		if err != nil {
			return nil, nil, err
		}
		return resp, perms, nil
	} else {
		resp, perms, err := m.PermsMenusFindAll()
		if err != nil {
			return nil, nil, err
		}
		return resp, perms, nil
	}
}

// PermsMenusFindAll 用户动态菜单不做判断
func (m *defaultSysMenuModel) PermsMenusFindAll() (*[]SysMenu, []string, error) {
	sysMenuIdKey := fmt.Sprintf("%s", prefix.CacheSysUserMenuAllPrefix)
	var resp []SysMenu
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select %s from %s where `isShow` = '1' ORDER BY `orderNum` ASC", sysMenuRows, m.table)
		return conn.QueryRows(v, query)
	})
	perms, err := m.PermsFindAll()
	switch err {
	case nil:
		return &resp, perms, nil
	case sqlc.ErrNotFound:
		return nil, nil, ErrNotFound
	default:
		return nil, nil, err
	}
}

//PermsFindAll 用户按钮权限不做判断
func (m defaultSysMenuModel) PermsFindAll() ([]string, error) {
	// 查询菜单权限
	sysMenuIdKey := fmt.Sprintf("%s", prefix.CacheSysUserPermsAllPrefix)
	var resp []string
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("select `perms` from %s where `perms` != ''", m.table)
		return conn.QueryRows(v, query)
	})
	switch err {
	case nil:
		return resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

// PermsMenusFindById 用户动态菜单根据uid查询
func (m *defaultSysMenuModel) PermsMenusFindById(uid int64) (*[]SysMenu, []string, error) {
	sysMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheSysMenuUidPrefix, uid)
	var resp []SysMenu
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("SELECT a.* FROM %s a JOIN sys_role_menu b on a.id = b.menuId where 1=1 and b.roleId in (select roleid from sys_user_role where userId = ?) ORDER BY `orderNum` ASC", m.table)
		return conn.QueryRows(v, query, uid)
	})
	perms, err := m.PermsFindById(uid)
	switch err {
	case nil:
		return &resp, perms, nil
	case sqlc.ErrNotFound:
		return nil, nil, ErrNotFound
	default:
		return nil, nil, err
	}
}

// PermsFindById 用户按钮权限根据uid查询
func (m defaultSysMenuModel) PermsFindById(uid int64) ([]string, error) {
	sysMenuIdKey := fmt.Sprintf("%s%v", prefix.CacheSysPermsUidPrefix, uid)
	var resp []string
	err := m.QueryRow(&resp, sysMenuIdKey, func(conn sqlx.SqlConn, v interface{}) error {
		query := fmt.Sprintf("SELECT a.perms FROM %s a JOIN sys_role_menu b on a.id = b.menuId where 1=1 and b.roleId in (select roleid from sys_user_role where userId = ?) and a.perms != ''", m.table)
		return conn.QueryRows(v, query, uid)
	})
	switch err {
	case nil:
		return resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}
