package s

import (
	"encoding/json"
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"testing"
)

func TestUserService_Create(t *testing.T) {
	err := UserService.Create(entitys.User{
		UserName: "test",
		Password: "123456",
	})

	if err != nil {
		t.Error(err)
	}
}

func TestUserService_Update(t *testing.T) {
	err := UserService.Update(entitys.User{
		Id:       1,
		UserName: "chenyu",
		Email:    "test1@qq.com",
	})

	if err != nil {
		t.Error(err)
	}
}

func TestUserService_Del(t *testing.T) {
	err := UserService.Del(2)

	if err != nil {
		t.Error(err)
	}
}

func TestUserService_User(t *testing.T) {
	res, err := UserService.User(1)
	if err != nil {
		t.Error(err)
	}

	jsonFmt(res)

}

func jsonFmt(obj interface{}) {
	s, _ := json.MarshalIndent(obj, "", "     ")
	fmt.Println(string(s))
}

func TestUserService_EditUserFile(t *testing.T) {
	err := UserService.EditUserFile(entitys.UserFile{
		Id:       2,
		FileSha1: "entitys.UserFile",
		FileName: "test.txt",
	}, NewTransactor())

	if err != nil {
		t.Error(err)
	}
}

func TestUserService_UserFiles(t *testing.T) {
	req := entitys.UserFilesReq{UserId: 1}
	req.Page = 1
	req.PageSize = 10
	res, err := UserService.UserFiles(req)
	if err != nil {
		t.Error(err)
		return
	}

	jsonFmt(res)
}
