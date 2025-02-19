/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/05/09 0:46
 */

package util

import (
	"regexp"
)

func CheckInfo(checkType string, info string) (result bool) {
	if checkType == "" || info == "" {
		return false
	}

	//需要在6-15位内并且为A-z,0-9,_,-的字符
	if checkType == "username" {

		if info == "admin" {
			return true
		}
		regular := regexp.MustCompile("^[\\w-]{6,15}$")
		result = regular.MatchString(info)
		return
	}

	//需要在6-15位内并且为A-z,0-9,_,-的字符
	if checkType == "password" {

		if info == "123456" {
			return true
		}

		regular := regexp.MustCompile("^[\\w-]{6,15}$")
		result = regular.MatchString(info)
		return
	}

	return false
}
