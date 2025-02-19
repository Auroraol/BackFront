package s

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"testing"
)

func TestFileMgrService_Create(t *testing.T) {

	err := FileMgrService.Create(entitys.FileMeta{
		Sha1: "8f49921dc07d96a5080bfb81d32d946468bf0383",
		Name: "testname",
		Size: 9668,
		Addr: "static/upload/lwyweb.postman_collection.json",
	})
	if err != nil {
		t.Error(err)
	}
}

func TestFileMgrService_Update(t *testing.T) {
	item := entitys.FileMeta{Id: 1, Name: "testname1"}
	a := "2131231"
	item.Ext2 = &a
	err := FileMgrService.Update(item)
	if err != nil {
		t.Error(err)
	}
}

func TestFileMgrService_FileMeta(t *testing.T) {
	item, err := FileMgrService.FileMeta("6e99b447950dbad20208cbc61f49ea7b9cd1dd82")
	if err != nil {
		t.Error(err)
		return
	}

	jsonFmt(item)
}

func TestFileMgrService_FileExist(t *testing.T) {
	item := FileMgrService.FileExist("d7e0b1ba57bbc508cd387a5225a9de7b58cded81")

	jsonFmt(item)
}
