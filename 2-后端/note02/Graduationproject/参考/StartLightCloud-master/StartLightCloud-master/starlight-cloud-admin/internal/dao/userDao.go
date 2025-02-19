/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:28
 */

package dao

import (
	"errors"
	"gorm.io/gorm"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/util"
)

type UserDao struct{}

// Insert 插入
func (ud *UserDao) Insert(user common.User) (row int64) {
	db := util.GetDB()
	result := db.Table("users").Create(&user)
	return result.RowsAffected
}

// Delete 删除
func (ud *UserDao) Delete(user common.User) (row int64) {
	db := util.GetDB()
	result := db.Delete(&user, 1)
	return result.RowsAffected
}

// Update 更新（单）
func (ud *UserDao) Update(user common.User, key string, value string) (row int64) {
	db := util.GetDB()
	//db.First(&user, 1)
	result := db.Table("users").Model(&user).Where("user_id = ?", user.UserId).Update(key, value)
	return result.RowsAffected
	//db.Model(&user).Updates(Product{Price: 666, Code: "1003"}) //多
}

// UpdateMoney 更新（星币更新专用）
func (ud *UserDao) UpdateMoney(user *common.User, money float64, canMoney float64, frozenMoney float64) (row int64, tx *gorm.DB, error error) {
	db := util.GetDB()
	//开启一个事务
	tx = db.Begin()

	result := tx.Model(&user).Select("money", "can_money", "frozen_money").Where("user_id = ?", user.UserId).Updates(&common.User{Money: money, CanMoney: canMoney, FrozenMoney: frozenMoney}).First(&user)
	if result.RowsAffected == 0 {
		tx.Rollback()
		return 0, tx, errors.New("update failed")
	}

	//返回一个tx，用于service层手动提交事务
	return result.RowsAffected, tx, result.Error
}

// UpdateRAM 自增更新（增加存储空间专用）
func (ud *UserDao) UpdateRAM(userId string, RAM string, TotalRAM string, DisplayRAM int) (row int64) {
	db := util.GetDB()

	user := common.User{}
	result := db.Model(&user).Where("user_id = ?", userId).Updates(map[string]interface{}{"ram": gorm.Expr("ram + ?", RAM), "total_ram": gorm.Expr("total_ram + ?", TotalRAM), "display_ram": gorm.Expr("display_ram + ?", DisplayRAM)})

	return result.RowsAffected
}

// UpdateWithAutoIncrement
// 自增更新
func (ud *UserDao) UpdateWithAutoIncrement(user *common.User, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("users").Debug().Model(&user).Where("user_id = ?", user.UserId).UpdateColumn(key, gorm.Expr(key+" + ?", value))
	return result.RowsAffected
}

// QueryInfoByUserId 查询（查用户所有数据(除了创更删和密码字段)）
func (ud *UserDao) QueryInfoByUserId(user *common.User) (row int64) {
	db := util.GetDB()
	result := db.Debug().Table("users").Select("user_id", "username", "profile", "phone_number", "role", "ram", "total_ram", "display_ram", "money", "can_money", "frozen_money", "last_login", "display_name").Where("user_id = ?", user.UserId).First(&user)
	return result.RowsAffected
}

// QueryByUserId 查询（根据UID直接查询到单字段的值）
func (ud *UserDao) QueryByUserId(user *common.User, target string) (row int64) {
	db := util.GetDB()
	result := db.Table("users").Select(target).Where("user_id = ?", user.UserId).First(&user)
	return result.RowsAffected
}

// QueryByData 查询（单/根据数据查询）
func (ud *UserDao) QueryByData(user common.User, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("users").First(&user, key+" = ?", value)
	return result.RowsAffected
}

// QueryUserId 查询（查用户id）
func (ud *UserDao) QueryUserId(user *common.User) (row int8) {
	db := util.GetDB()
	result := db.Table("users").Select("user_id").First(&user)
	return int8(result.RowsAffected)
}

// QueryByUsernameAndPassword 查询（根据账号密码）
func (ud *UserDao) QueryByUsernameAndPassword(user *common.User) (row int64) {
	db := util.GetDB()
	result := db.Table("users").Where("username = ? AND password = ?", user.Username, user.Password).Find(&user)
	return result.RowsAffected
}

// QueryLevelByUserId 查询用户角色（返回权限等级）（0:user, 1:admin）
func (ud *UserDao) QueryLevelByUserId(user *common.User) (level int) {
	db := util.GetDB()
	db.Table("users").Where("user_id = ?", user.UserId).Select("role").First(&user)
	return user.Role
}
