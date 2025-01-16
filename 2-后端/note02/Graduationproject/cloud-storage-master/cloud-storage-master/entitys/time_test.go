package entitys

import (
	"encoding/json"
	"fmt"
	"testing"
	"time"
)

func TestTime_Time(t *testing.T) {
	var s = `
{
               "Id": 2,
               "UserId": 1,
               "FileSha1": "d7e0b1ba57bbc508cd387a5225a9de7b58cded81",
               "FileSize": 14,
               "FileName": "test.txt",
               "Status": 1,
               "CreatedAt": "2020-02-20 17:14:21",
               "UpdatedAt": "2020-02-20 17:25:06",
               "DeletedAt": null
          }
`

	a := UserFile{}

	err := json.Unmarshal([]byte(s), &a)
	if err != nil {
		t.Error(err)
		return
	}

	b := time.Time(a.CreatedAt)
	fmt.Printf("%s", b)

}
