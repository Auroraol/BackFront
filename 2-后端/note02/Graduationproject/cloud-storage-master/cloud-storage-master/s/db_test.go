package s

import (
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"testing"
)

func TestRepeat(t *testing.T) {
	user := entitys.UserFile{
		UserId: 1,
	}

	ok := Repeat(&user, "Id", "UserName")

	fmt.Println(ok)

}

func TestField2DatabaseField(t *testing.T) {

	fmt.Println(Field2DatabaseField("UserName"))
}
