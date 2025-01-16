package s

import (
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/utils"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
	"reflect"
	"strings"
	"time"
)

var Db *gorm.DB

func init() {
	var err error
	if Db, err = gorm.Open("mysql", entitys.MyAppConfig.DbConnect()); err != nil {
		utils.PosLog(err)
		return
	}

	// SetMaxIdleConns 设置空闲连接池中的最大连接数。
	Db.DB().SetMaxIdleConns(10)

	// SetMaxOpenConns 设置数据库连接最大打开数。
	Db.DB().SetMaxOpenConns(100)

	// SetConnMaxLifetime 设置可重用连接的最长时间
	Db.DB().SetConnMaxLifetime(time.Hour)

	//禁用复数表名
	Db.SingularTable(true)

	gorm.DefaultTableNameHandler = func(db *gorm.DB, defaultTableName string) string {
		return entitys.MyAppConfig.DbTablePrefix + defaultTableName
	}

}

//检查数据重复
func Repeat(obj interface{}, pk string, fields ...string) (ok bool) {

	objT := reflect.TypeOf(obj)
	if objT.Kind() != reflect.Ptr {
		return
	}

	objV := reflect.ValueOf(obj).Elem()

	q := Db.New()

	//设置的过滤字段
	for _, field := range fields {
		q = q.Where(fmt.Sprintf("%s = ?", Field2DatabaseField(field)), objV.FieldByName(field).Interface())
	}

	//主键
	pkVal := objV.FieldByName(pk).Interface()
	if !reflect.DeepEqual(pkVal, 0) {
		q = q.Where(fmt.Sprintf("%s != ?", Field2DatabaseField(pk)), pkVal)
	}

	//检查是否有DeleteAt字段
	if _, ok := objT.Elem().FieldByName("DeletedAt"); ok {
		q = q.Where("deleted_at is null")
	}

	num := 0
	table := gorm.DefaultTableNameHandler(q, Field2DatabaseField(objT.Elem().Name()))

	q.Table(table).Count(&num)

	if num == 0 {
		//没有重复的数据
		return false
	}

	return true
}

//结构体字段转数据库字段 例如UserName 转换为user_name
func Field2DatabaseField(s string) string {
	var ss string

	for k := range s {
		if k == 0 {
			ss += string(s[k])
			continue
		}

		if s[k] >= 'A' && s[k] <= 'Z' {
			ss += "_" + string(s[k])
		} else {
			ss += string(s[k])
		}
	}
	return strings.ToLower(ss)
}
