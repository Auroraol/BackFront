package c

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"testing"
)

func TestInitMultipart(t *testing.T) {
	defer stopPanic()
	setPost(ctx, entitys.MultipartUploadInfo{
		FileHash: "aaadfadfadfafaf",
		FileSize: 20 * 1024 * 1024,
	})
	InitMultipart(ctx)
}
