package c

import (
	"fmt"
	"gitee.com/phpdi/cloud-storage/s"
	"strings"
	"testing"
)

func TestLogin(t *testing.T) {
	defer stopPanic()
	setPost(ctx, s.Auth{"chenyu", "123456"})
	Login(ctx)
}

func Test_Join(t *testing.T) {
	sstr := "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjEsImV4cCI6MTU3NzYwNjYyNH0"
	sig := "lPjUkcX7BD5BxAoYOaqaqgLYyWJozhqaRb7qd1rZ32w"
	ssss := strings.Join([]string{sstr, sig}, ".")
	fmt.Println(ssss)
}
