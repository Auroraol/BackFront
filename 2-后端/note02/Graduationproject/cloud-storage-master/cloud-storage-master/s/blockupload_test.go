package s

import (
	"fmt"
	"math"
	"path"
	"testing"
)

func TestBlockUploadService_GetUploadInfoFromCache(t *testing.T) {
	res, err := BlockUploadService.GetUploadInfoFromCache("17D22986-667C-FB2B-BD65-1E2B82C6207")
	if err != nil {
		t.Error(err)
		return
	}

	jsonFmt(res)
}

func TestBlockUploadService_A(t *testing.T) {
	b := 120075156 / 5242880
	fmt.Println(b)
	a := math.Ceil(float64(120075156) / float64(5242880))
	fmt.Println(a)
}

func TestBlockUploadService_CompleteUpload(t *testing.T) {
	err := BlockUploadService.CompleteUpload("C0425C65-1455-35DB-42CA-677EBB91B5A8", nil)
	if err != nil {
		t.Error(err)
		return
	}
}

func Test_GET(t *testing.T) {
	fmt.Println(path.Ext("/aaa/bbb.tar.gz"))

}
