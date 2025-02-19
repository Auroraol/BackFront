package entitys

import (
	"encoding/json"
	"errors"
	"reflect"
)

type Pager struct {
	Page      int         //当前页
	PageSize  int         //页大小 ,页大小 小于 0 返回所有数据
	Total     int64       //总量
	SliceData interface{} //切片源数据
}

//切片分页 pageSize=-1 返回全部数据
func (this *Pager) Pagination() error {
	if this.SliceData == nil {
		return nil
	}

	var (
		resSlice []interface{}
		arrSlice []interface{}
		start    int64
		end      int64
		err      error
		content  []byte
	)

	if arrSlice, err = this.toSlice(this.SliceData); err != nil {
		return err
	}

	this.Total = int64(len(arrSlice))

	//分页大小=-1 或者 当前页大小不大于0 不进行分页处理
	if this.PageSize == -1 || this.Page <= 0 {
		return nil
	}

	start = int64((this.Page - 1) * this.PageSize)
	end = int64(this.Page * this.PageSize)
	if end > this.Total {
		end = this.Total
	}

	for start < end {
		resSlice = append(resSlice, arrSlice[start])
		start++
	}

	if content, err = json.Marshal(resSlice); err != nil {
		return err
	}

	return json.Unmarshal(content, this.SliceData)
}

//设置切片源数据
func (this *Pager) Data(data interface{}) *Pager {
	this.SliceData = data
	return this
}

//页大小
func (this *Pager) Size() int {

	//分页量小于0，返回全部数据
	if this.PageSize < 0 {
		return int(this.Total)
	}

	return this.PageSize
}

//偏移量
func (this *Pager) Offset() int {

	if this.PageSize < 0 {
		return 0
	}

	offset := (this.Page - 1) * this.PageSize
	if offset < 0 {
		offset = 0
	}

	return offset
}

//slice interface 变数组
func (this *Pager) toSlice(arr interface{}) ([]interface{}, error) {
	v := reflect.ValueOf(arr)
	if v.Kind() != reflect.Ptr {
		return []interface{}{}, errors.New("分页源数据必须为切片指针")
	}

	ve := reflect.ValueOf(arr).Elem()
	if ve.Kind() != reflect.Slice {
		return []interface{}{}, errors.New("分页源数据必须为切片指针.")
	}

	l := ve.Len()
	ret := make([]interface{}, l)
	for i := 0; i < l; i++ {
		ret[i] = ve.Index(i).Interface()
	}
	return ret, nil
}
