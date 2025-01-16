package utils

import (
	"crypto/md5"
	"crypto/sha1"
	"encoding/hex"
	"encoding/json"
	"errors"
	"io"
	"io/ioutil"
	"log"
	"mime/multipart"
	"os"
	"path/filepath"
	"strings"
	"time"
)

//文件sha1
func Sha1(data []byte) string {
	_sha1 := sha1.New()
	_sha1.Write(data)
	return hex.EncodeToString(_sha1.Sum([]byte("")))
}

//路径是否存在
func PathExists(path string) (bool, error) {
	var err error
	if _, err := os.Stat(path); err == nil {
		return true, nil
	}

	if os.IsNotExist(err) {
		return false, nil
	}

	return false, err
}

//获取文件大小
func GetFileSize(filename string) (bit int64, err error) {
	err = filepath.Walk(filename, func(path string, info os.FileInfo, err error) error {
		bit = info.Size()
		return nil
	})

	return
}

func FileSha1(file io.Reader) (string, error) {
	var err error
	_sha1 := sha1.New()
	if _, err = io.Copy(_sha1, file); err != nil {
		return "", err
	}

	return hex.EncodeToString(_sha1.Sum(nil)), nil
}

func MD5(data []byte) string {
	_md5 := md5.New()
	_md5.Write(data)

	return hex.EncodeToString(_md5.Sum([]byte("")))
}

func FileMD5(file *os.File) string {
	_md5 := md5.New()
	io.Copy(_md5, file)

	return hex.EncodeToString(_md5.Sum(nil))
}

//判定文件是否存在
func IsFileExist(filename string) bool {
	if _, err := os.Stat(filename); os.IsNotExist(err) {
		return false
	}
	return true
}

//获取文件修改时间 返回unix时间戳
func GetFileModTime(path string) (int64, error) {
	f, err := os.Open(path)
	if err != nil {
		return 0, err
	}
	defer f.Close()

	fi, err := f.Stat()
	if err != nil {
		log.Println("stat fileinfo error")
		return 0, err
	}

	return fi.ModTime().Unix(), nil
}

//将数据刷入文件
func FlashData2File(filePath string, data interface{}) error {

	//文件不存在创建文件
	if !IsFileExist(filePath) {
		file, err := os.Create(filePath)
		if err != nil {
			return err
		}
		defer file.Close()
	}
	content, err := json.Marshal(data)
	if err != nil {
		return err
	}

	return ioutil.WriteFile(filePath, content, 0777)

}

//将文件数据载入到变量中
func FlashFile2Data(filePath string, data interface{}) error {
	if !IsFileExist(filePath) {
		return nil
	}
	content, err := ioutil.ReadFile(filePath)
	if err != nil {
		return err
	}

	return json.Unmarshal(content, data)
}

//按照日期存储文件
func SaveFileWithDate(dir string, file multipart.File, fileHeader *multipart.FileHeader, randName bool) (filePath string /*上传的文件路径*/, err error) {

	var (
		fileNameArr []string
		fileSuffix  string //文件后缀

		newFile *os.File
		ok      bool
	)

	//取文件前缀
	if fileHeader == nil {
		return "", errors.New("文件不存在")
	}

	fileNameArr = strings.Split(fileHeader.Filename, ".")
	if len(fileNameArr) < 1 {
		return "", errors.New("文件名错误")
	}

	fileSuffix = "." + fileNameArr[len(fileNameArr)-1]

	//计算目录
	today := time.Now().Format("2006-01-02")

	dir = strings.TrimRight(dir, "/") + "/" + today

	if ok, err = PathExists(dir); err != nil {
		return
	}
	//今日目录不存在创建
	if !ok {
		if err = os.MkdirAll(dir, os.ModePerm); err != nil {
			return
		}
	}

	if randName {
		filePath = dir + "/" + RandString(16) + fileSuffix
	} else {
		filePath = dir + "/" + fileHeader.Filename
	}

	//检查文件是否存在
	if ok, err = PathExists(filePath); err != nil {
		return
	}

	if ok {
		return "", errors.New("文件已存在")
	}

	//新文件
	if newFile, err = os.Create(filePath); err != nil {
		return "", err
	}
	defer newFile.Close()

	if _, err = io.Copy(newFile, file); err != nil {
		return "", err
	}

	return
}

//计算文件路径
func FilePathWithDate(dir string, fileName string) (path string, err error) {
	var ok bool

	//计算目录
	today := time.Now().Format("2006-01-02")

	dir = strings.TrimRight(dir, "/") + "/" + today

	if ok, err = PathExists(dir); err != nil {
		return
	}
	//今日目录不存在创建
	if !ok {
		if err = os.MkdirAll(dir, os.ModePerm); err != nil {
			return
		}
	}

	path = dir + "/" + fileName

	return
}

//检查文件是否符合要求
func CheckFile(fileHeader *multipart.FileHeader, size int64 /*支持的大小，单位kb*/, suffix []string /*支持的后缀*/) error {
	var (
		fileNameArr []string
		fileSuffix  string //文件后缀
	)

	if fileHeader == nil {
		return errors.New("文件不存在")
	}

	fileNameArr = strings.Split(fileHeader.Filename, ".")
	if len(fileNameArr) < 1 {
		return errors.New("文件名错误")
	}

	fileSuffix = "." + fileNameArr[len(fileNameArr)-1]

	//格式检查
	if len(suffix) > 0 && !InArray(fileSuffix, suffix) {
		return errors.New("不支持的文件格式")
	}

	//大小检查
	if fileHeader.Size > size {
		return errors.New("文件过大")
	}

	return nil
}
