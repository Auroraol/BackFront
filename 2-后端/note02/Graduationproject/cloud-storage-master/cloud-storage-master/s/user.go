package s

import (
	"errors"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/utils"
	"github.com/jinzhu/gorm"
)

var UserService *userService

//用户服务
type userService struct {
}

func init() {
	UserService = new(userService)
}

//创建用户
func (this *userService) Create(item entitys.User) (err error) {
	item.Salt = utils.RandString(8)

	item.Password = utils.Md5(item.Password + item.Salt)

	if Repeat(&item, "Id", "UserName") {
		return errors.New("账号已被注册")
	}

	return Db.Create(&item).Error
}

//编辑用户
func (this *userService) Update(item entitys.User) (err error) {
	if Repeat(&item, "Id", "UserName") {
		return errors.New("账号已被注册")
	}

	return Db.Model(&item).Updates(item).Error

}

//删除用户
func (this *userService) Del(id int) (err error) {
	return Db.Delete(&entitys.User{Id: id}).Error
}

//获取用户信息
func (this *userService) User(id int) (user entitys.User, err error) {
	err = Db.First(&user, id).Error
	return
}

//获取多条用户信息
func (this *userService) Users() (users []entitys.User, err error) {
	err = Db.Find(&users).Error
	return
}

//添加用户文件
func (this *userService) AddUserFile(userFile entitys.UserFile, tran *Transactor) (err error) {
	return tran.Transaction(func(db *gorm.DB) error {
		//数据已经存在
		if Repeat(&userFile, "Id", "UserId", "FileSha1") {
			return nil
		}
		return db.Debug().Create(&userFile).Error
	})

}

//更新用户文件
func (this *userService) EditUserFile(userFile entitys.UserFile, tran *Transactor) (err error) {
	return tran.Transaction(func(db *gorm.DB) error {
		return db.Model(&userFile).Omit("id", "file_sha1", "file_size").Updates(userFile).Error
	})
}

//删除用户文件
func (this *userService) DelUserFile(id int, tran *Transactor) (err error) {
	return tran.Transaction(func(db *gorm.DB) error {
		return db.Delete(&entitys.User{Id: id}).Error
	})
}

//获取用户文件列表
func (this *userService) UserFiles(req entitys.UserFilesReq) (ack entitys.UserFilesAck, err error) {

	if req.PageSize > 0 {
		Db = Db.Limit(req.Size()).Offset(req.Offset())
	}

	if err = Db.Debug().Where("user_id = ?", req.UserId).Find(&ack.Data).Count(&ack.Total).Error; err != nil {
		return
	}

	return
}
