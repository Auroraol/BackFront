package utils

import "reflect"

func InArray(element interface{}, slice interface{}) bool {
	if reflect.TypeOf(slice).Kind() != reflect.Slice {
		panic("reflect: InSlice of non-slice type")
	}

	n := reflect.ValueOf(slice).Len()
	for i := 0; i < n; i++ {
		if reflect.ValueOf(slice).Index(i).Interface() == reflect.ValueOf(element).Interface() {
			return true
		}
	}

	return false
}
