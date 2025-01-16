package entitys

import (
	"fmt"
	"gitee.com/phpdi/cloud-storage/utils"
)

func init() {
	utils.PosLog(utils.FlashFile2Data("config/config.json", &MyAppConfig))
	if MyAppConfig.JwtSecretKey == "" {
		MyAppConfig.JwtSecretKey = "fajkrKJLA8kljfaf"
	}

	if MyAppConfig.ChunkSize == 0 {
		MyAppConfig.ChunkSize = 5
	}
}

const (
	//文件上传路径
	UploadFilePath = "static/upload"
)

//数据库配置
type AppConfig struct {
	//数据库配置
	DbHost        string //主机
	DbPort        int    //端口
	DbUser        string //用户名
	DbPassword    string //密码
	DbDatabase    string //数据库名称
	DbTablePrefix string //数据库表前缀

	//Http配置
	HttpPort         string //http监听端口
	HttpReadTimeout  int    //读超时时间 单位：秒
	HttpWriteTimeout int    //读超时时间 单位：秒

	//jwt加密密码 16位字符串
	JwtSecretKey string

	//redis配置
	RedisHost        string
	RedisPassword    string
	RedisMaxIdle     int
	RedisMaxActive   int
	RedisIdleTimeout int

	//分块大小
	ChunkSize     int    //单位MB
	MergeAllShell string //合并文件命令

}

func (this AppConfig) DbConnect() string {
	return fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local", this.DbUser, this.DbPassword, this.DbHost, this.DbPort, this.DbDatabase)
}

var (
	//应用配置
	MyAppConfig AppConfig
)
