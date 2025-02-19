/**
 * @Author: FxShadow
 * @Description:该文件用于业务层间调用的功能服务
 * @Date: 2023/05/07 23:35
 */

package common

import (
	"fmt"
	"strconv"
	"system-admin/starlight-cloud-admin/internal/dao"
	"system-admin/starlight-cloud-admin/internal/model/common"
)

type Service struct{}

func GetService() (service Service) {
	return service
}

// AddUserRAM 增加用户存储空间
func (s *Service) AddUserRAM(userId string, GiB int) (result int8) {

	//校验用户id是否存在
	var userDao dao.UserDao
	user := &common.User{UserId: userId}
	row := userDao.QueryUserId(user)
	if row == 0 {
		return 0
	}

	//计算TotalRAM
	//将GiB转换为b
	GiBToByte := strconv.Itoa(GiB * 1024 * 1024 * 1024)

	fmt.Println("GiBToByte::", GiBToByte)

	//更新信息
	row = int8(userDao.UpdateRAM(userId, GiBToByte, GiBToByte, GiB))
	if row == 0 {
		return 0
	}

	return 1
}

// AddUserSCoin 增加用户星币
func (s *Service) AddUserSCoin(userId string, count float64) (result int8) {

	//查询用户信息
	var userDao dao.UserDao
	user := &common.User{UserId: userId}
	row := userDao.QueryInfoByUserId(user)
	if row == 0 {
		return 0
	}
	fmt.Println("查询用户信息")

	//更新用户星币
	row, tx, _ := userDao.UpdateMoney(user, user.Money+count, user.CanMoney+count, 0)
	if row == 0 {
		tx.Rollback()
		return 0
	}
	tx.Commit()
	fmt.Println("更新用户星币")
	fmt.Println("AddUserSCoin over")

	return 1
}
