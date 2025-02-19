package utils

import (
	"crypto/md5"
	cryptorand "crypto/rand"
	"encoding/base64"
	"fmt"
	"io"
	"math/rand"
	"strings"
	"time"
)

//生成随机字符串
func RandString(num int) string {
	var letters = []rune("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
	b := make([]rune, num)

	rand.Seed(time.Now().Unix())
	for i := range b {
		b[i] = letters[rand.Intn(len(letters))]
	}
	return string(b)
}

// 生成md5
func Md5(buf string) string {
	hash := md5.New()
	hash.Write([]byte(buf))
	return fmt.Sprintf("%x", hash.Sum(nil))
}

//生成随机字符串
func UUid(num int) string {
	b := make([]byte, num)

	if _, err := io.ReadFull(cryptorand.Reader, b); err != nil {
		return ""
	}

	md5 := []byte(strings.ToUpper(Md5(base64.URLEncoding.EncodeToString(b))))

	return string(md5[0:8]) + "-" + string(md5[8:12]) + "-" + string(md5[12:16]) + "-" + string(md5[16:20]) + "-" + string(md5[20:])
}
