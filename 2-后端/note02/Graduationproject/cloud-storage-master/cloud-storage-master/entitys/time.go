package entitys

import (
	"encoding/json"
	"fmt"
	"time"
)

//处理时格式化问题
type Time time.Time

func (this Time) MarshalJSON() ([]byte, error) {
	var stamp = fmt.Sprintf("\"%s\"", time.Time(this).Format("2006-01-02 15:04:05"))
	return []byte(stamp), nil
}

func (this *Time) UnmarshalJSON(data []byte) error {
	var rs string
	e := json.Unmarshal(data, &rs)
	if e != nil {
		return e
	}
	t, er := time.Parse("2006-01-02 15:04:05", rs)
	if er != nil {
		return er
	}
	*this = Time(t)
	return nil
}
