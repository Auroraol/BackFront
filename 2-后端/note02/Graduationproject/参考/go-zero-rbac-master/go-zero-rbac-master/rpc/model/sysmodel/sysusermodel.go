/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：sysusermodel.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月11日 21:34:59
 * # 上次修改时间：2021年07月11日 21:34:03
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
	"github.com/tal-tech/go-zero/core/logx"
	"strings"
	"time"

	"github.com/tal-tech/go-zero/core/stores/sqlc"
	"github.com/tal-tech/go-zero/core/stores/sqlx"
	"github.com/tal-tech/go-zero/core/stringx"
	"github.com/tal-tech/go-zero/tools/goctl/model/sql/builderx"
)

var (
	sysUserFieldNames          = builderx.RawFieldNames(&SysUser{})
	sysUserRows                = strings.Join(sysUserFieldNames, ",")
	sysUserRowsExpectAutoSet   = strings.Join(stringx.Remove(sysUserFieldNames, "`id`", "`createTime`", "`updateTime`"), ",")
	sysUserRowsWithPlaceHolder = strings.Join(stringx.Remove(sysUserFieldNames, "`id`", "`createTime`", "`updateTime`", "`username`"), "=?,") + "=?"
)

type (
	SysUserModel interface {
		Insert(data SysUserChangeReq) (sql.Result, error)
		FindOne(id int64) (*SysUser, error)
		FindOneByUsername(username string) (*SysUser, error)
		Update(data SysUserChangeReq) error
		UpdateByDept(deptId []int64, pid int64) error
		UpdateByIds(deptId int64, uid []int64) error
		Delete(ids []int64) error
		DeleteByDeptIds(ids []int64) error
		FindAll(data *SysUserReq) (*[]SysUserResp, error)
		Count(in *SysUserReq) (int64, error)
	}

	defaultSysUserModel struct {
		conn  sqlx.SqlConn
		table string
	}

	SysUser struct {
		Id           int64     `db:"id"`           // ID
		CreateTime   time.Time `db:"createTime"`   // 创建时间
		UpdateTime   time.Time `db:"updateTime"`   // 更新时间
		DepartmentId int64     `db:"departmentId"` // 部门ID
		Name         string    `db:"name"`         // 姓名
		Username     string    `db:"username"`     // 用户名
		Password     string    `db:"password"`     // 密码
		NickName     string    `db:"nickName"`     // 昵称
		HeadImg      string    `db:"headImg"`      // 头像
		Phone        string    `db:"phone"`        // 手机
		Email        string    `db:"email"`        // 邮箱
		Status       bool      `db:"status"`       // 状态 0:禁用 1：启用
		Remark       string    `db:"remark"`       // 备注
	}
	SysUserChangeReq struct {
		SysUser
		RoleIdList []int64
	}
	SysUserReq struct {
		DeptId  []int64 // 部门ids
		KeyWord string  // 关键字
		Page    int64   // 页码
		Size    int64   // 每页条数
		Sort    string  // 排序字段
		Oder    string  // 排序方式
	}
	SysUserResp struct {
		SysUser
		RoleName       string `db:"roleName"`       // 角色名称
		DepartmentName string `db:"departmentName"` // 部门名称
	}
)

func NewSysUserModel(conn sqlx.SqlConn) SysUserModel {
	return &defaultSysUserModel{
		conn:  conn,
		table: "`sys_user`",
	}
}

func (m *defaultSysUserModel) Insert(data SysUserChangeReq) (sql.Result, error) {
	query := fmt.Sprintf("insert into %s (%s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", m.table, sysUserRowsExpectAutoSet)
	ret, err := m.conn.Exec(query, data.DepartmentId, data.Name, data.Username, data.Password, data.NickName, data.HeadImg, data.Phone, data.Email, data.Status, data.Remark)
	userRole := NewSysUserRoleModel(m.conn)
	id, _ := ret.LastInsertId()
	// 更新用户角色关系
	for _, i := range data.RoleIdList {
		_, err = userRole.Insert(SysUserRole{
			CreateTime: time.Now(),
			UpdateTime: time.Now(),
			UserId:     id,
			RoleId:     gconv.Int64(i),
		})
		if err != nil {
			logx.Error("插入用户角色表失败, err=%v", err)
			return ret, err
		}
	}
	return ret, err
}

func (m *defaultSysUserModel) FindOne(id int64) (*SysUser, error) {
	query := fmt.Sprintf("select %s from %s where `id` = ? limit 1", sysUserRows, m.table)
	var resp SysUser
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

func (m *defaultSysUserModel) FindOneByUsername(username string) (*SysUser, error) {
	var resp SysUser
	query := fmt.Sprintf("select %s from %s where `username` = ? limit 1", sysUserRows, m.table)
	err := m.conn.QueryRow(&resp, query, username)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m *defaultSysUserModel) Update(data SysUserChangeReq) error {
	// 如果有更新密码那么则更新
	if len(data.Password) > 1 {
		query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysUserRowsWithPlaceHolder)
		_, err := m.conn.Exec(query, data.DepartmentId, data.Name, data.Password, data.NickName, data.HeadImg, data.Phone, data.Email, data.Status, data.Remark, data.Id)
		if err != nil {
			return err
		}
	}
	sysUserRowsWithPlace := strings.Join(stringx.Remove(sysUserFieldNames, "`id`", "`createTime`", "`updateTime`", "`userName`"), "=?,") + "=?"
	query := fmt.Sprintf("update %s set %s where `id` = ?", m.table, sysUserRowsWithPlace)
	_, err := m.conn.Exec(query, data.DepartmentId, data.Name, data.NickName, data.HeadImg, data.Phone, data.Email, data.Status, data.Remark, data.Id)
	// 更新角色关联表逻辑
	if len(data.RoleIdList) > 0 {
		userRole := NewSysUserRoleModel(m.conn)
		err = userRole.DeleteByUid(data.Id)
		for _, i := range data.RoleIdList {
			_, err = userRole.Insert(SysUserRole{
				CreateTime: time.Now(),
				UpdateTime: time.Now(),
				UserId:     data.Id,
				RoleId:     gconv.Int64(i),
			})
			if err != nil {
				return err
			}
		}
	}

	return err
}

// UpdateByDept 更新部门id
func (m *defaultSysUserModel) UpdateByDept(deptId []int64, pid int64) error {
	query := fmt.Sprintf("update %s set departmentId = %v where departmentId in (%v)", m.table, pid, gconv.ReplaceByInt(deptId))
	_, err := m.conn.Exec(query)

	return err
}

// UpdateByIds 批量转移用户部门
func (m defaultSysUserModel) UpdateByIds(deptId int64, uid []int64) error {
	query := fmt.Sprintf("update %s set `departmentId` = %v where `id` in (%v)", m.table, deptId, gconv.ReplaceByInt(uid))
	_, err := m.conn.Exec(query)

	return err
}

func (m *defaultSysUserModel) Delete(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `id` in (%v)", m.table, gconv.ReplaceByInt(ids))
	_, err := m.conn.Exec(query)
	return err
}

func (m *defaultSysUserModel) DeleteByDeptIds(ids []int64) error {
	query := fmt.Sprintf("delete from %s where `departmentId` in (%v)", m.table, gconv.ReplaceByInt(ids))
	_, err := m.conn.Exec(query)
	return err
}

func (m *defaultSysUserModel) FindAll(in *SysUserReq) (*[]SysUserResp, error) {
	dept := ""
	if len(in.DeptId) > 0 {
		dept = fmt.Sprintf("and a.departmentId in(%v)", gconv.ReplaceByInt(in.DeptId))
	}
	keyWord := ""
	if len(in.KeyWord) > 0 {
		keyWord = fmt.Sprintf("and (a.name LIKE '%s' or a.username LIKE '%s')", in.KeyWord, in.KeyWord)
	}
	orders := ""
	if len(in.Oder) > 0 {
		orders = fmt.Sprintf("order by `%s` %s", in.Oder, in.Sort)
	}
	query := fmt.Sprintf("select %s, roleName,departmentName from (SELECT "+
		"a.*, "+
		"GROUP_CONCAT(CASE WHEN (ISNULL(c.name)=1) THEN  '' ELSE c.name END) AS roleName, "+
		"d.name as departmentName "+
		"FROM "+
		"sys_user a "+
		"LEFT JOIN sys_user_role b ON a.id = b.userId "+
		"LEFT JOIN sys_role c ON b.roleId = c.id "+
		"LEFT JOIN sys_department d on a.departmentId = d.id "+
		"WHERE 1 = 1 %s %s and a.username != 'admin' GROUP BY a.id) t %s limit ?,?", sysUserRows, dept, keyWord, orders)
	var resp []SysUserResp
	err := m.conn.QueryRows(&resp, query, (in.Page-1)*in.Size, in.Size)
	switch err {
	case nil:
		return &resp, nil
	case sqlc.ErrNotFound:
		return nil, ErrNotFound
	default:
		return nil, err
	}
}

func (m defaultSysUserModel) Count(in *SysUserReq) (int64, error) {
	dept := ""
	if len(in.DeptId) > 0 {
		dept = fmt.Sprintf("and departmentId in(%v)", gconv.ReplaceByInt(in.DeptId))
	}

	keyWord := ""
	if len(in.KeyWord) > 0 {
		keyWord = fmt.Sprintf("and (`name` LIKE '%s' or `username` LIKE '%s' or `nickName` LIKE '%s')", in.KeyWord, in.KeyWord, in.KeyWord)
	}
	query := fmt.Sprintf("select count(*) as count from %s where 1=1 %s %s", m.table, dept, keyWord)
	var count int64
	err := m.conn.QueryRow(&count, query)

	switch err {
	case nil:
		return count, nil
	case sqlc.ErrNotFound:
		return 0, ErrNotFound
	default:
		return 0, err
	}
}
