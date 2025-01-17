/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 0:18
 */

package util

import (
	"fmt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"system-admin/starlight-cloud-admin/internal/model/common"
)

var db *gorm.DB

func InitMysql(ConfigDSN string) {
	//连接MYSQL, 获得DB类型实例，用于后面的数据库读写操作。
	var err error
	db, err = gorm.Open(mysql.New(mysql.Config{
		DSN:                       ConfigDSN,
		DefaultStringSize:         256,   // string 类型字段的默认长度
		DisableDatetimePrecision:  true,  // 禁用 datetime 精度，MySQL 5.6 之前的数据库不支持
		DontSupportRenameIndex:    true,  // 重命名索引时采用删除并新建的方式，MySQL 5.7 之前的数据库和 MariaDB 不支持重命名索引
		DontSupportRenameColumn:   true,  // 用 `change` 重命名列，MySQL 8 之前的数据库和 MariaDB 不支持重命名列
		SkipInitializeWithVersion: false, // 根据当前 MySQL 版本自动配置
	}), &gorm.Config{})
	if err != nil {
		fmt.Println("connect database failed")
	}

	sqlDB, _ := db.DB()

	//设置数据库连接池参数
	sqlDB.SetMaxOpenConns(100) //设置数据库连接池最大连接数
	sqlDB.SetMaxIdleConns(30)  //连接池最大允许的空闲连接数

	//迁移用户表
	err = db.AutoMigrate(&common.User{})
	if err != nil {
		return
	}

	//迁移文件表
	err = db.AutoMigrate(&common.File{})
	if err != nil {
		return
	}

	//迁移商品表
	err = db.AutoMigrate(&common.Goods{})
	if err != nil {
		return
	}

	//迁移物品表
	err = db.AutoMigrate(&common.Item{})
	if err != nil {
		return
	}

	//迁移订单表
	err = db.AutoMigrate(&common.Order{})
	if err != nil {
		return
	}

}

// GetDB 每次调用该方法自动从数据库连接池中获取新的连接
func GetDB() *gorm.DB {
	return db
}
