/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 0:25
 */

package config

import (
	"github.com/spf13/viper"
	"log"
	"system-admin/starlight-cloud-admin/internal/middleware/mq"
	"system-admin/starlight-cloud-admin/internal/router"
	"system-admin/starlight-cloud-admin/internal/service"
	"system-admin/starlight-cloud-admin/internal/util"
)

// 配置文件信息
var (
	MysqlUsername            = ""
	MysqlPassword            = ""
	MysqlAddress             = ""
	DSN                      = ""
	AlipayAppId              = ""
	AlipayPrivateKey         = ""
	AppPublicCertPath        = ""
	AlipayRootCertPath       = ""
	AlipayPublicCertPath     = ""
	AlipayNotifyURL          = ""
	AlipayReturnURL          = ""
	RedisAddress             = ""
	AliYunOSSEndpoint        = ""
	AliYunOSSAccessKeyId     = ""
	AliYunOSSAccessKeySecret = ""
	ProfileBucket            = ""
	OSSClientURL             = ""
	SenderEmail              = ""
	SMTPServer               = ""
	EmailSMTPPort            = ""
	EmailSMTPKey             = ""
)

// InitSystem 只需要加载这个即可，若有需要可单独加载其它功能
func InitSystem() {
	InitConfig()
	InitMysql()
	InitRedis()
	InitAlipay()
	InitAliYunOSS()
	InitEmail()
	InitGoruntime()
	InitRouter()
}

// InitConfig 配置文件信息
func InitConfig() {
	v := viper.New()
	v.SetConfigFile("../config/config.yaml")

	// 读取配置文件
	if err := v.ReadInConfig(); err != nil {
		log.Fatalf("Failed to read config file: %v", err)
	}

	//读取mysql字段
	MysqlUsername = v.GetString("mysql.username")
	MysqlPassword = v.GetString("mysql.password")
	MysqlAddress = v.GetString("mysql.address")
	DSN = MysqlUsername + ":" + MysqlPassword + "@tcp" + "(" + MysqlAddress + ")" + "/starlight_db?charset=utf8&parseTime=True&loc=Local"

	//读取alipay字段
	AlipayAppId = v.GetString("alipay.appId")
	AlipayPrivateKey = v.GetString("alipay.privateKey")
	AppPublicCertPath = v.GetString("alipay.cert.appPublicCertPath")
	AlipayRootCertPath = v.GetString("alipay.cert.alipayRootCertPath")
	AlipayPublicCertPath = v.GetString("alipay.cert.alipayPublicCertPath")
	AlipayNotifyURL = v.GetString("alipay.url.notifyURL")
	AlipayReturnURL = v.GetString("alipay.url.returnURL")

	//读取redis字段
	RedisAddress = v.GetString("redis.address")

	//读取阿里云OSS字段
	AliYunOSSEndpoint = v.GetString("aliYunOSS.endpoint")
	AliYunOSSAccessKeyId = v.GetString("aliYunOSS.accessKeyId")
	AliYunOSSAccessKeySecret = v.GetString("aliYunOSS.accessKeySecret")
	ProfileBucket = v.GetString("aliYunOSS.profileBucket")
	OSSClientURL = v.GetString("aliYunOSS.OSSClientURL")

	//读取Email字段
	SenderEmail = v.GetString("email.senderEmail")
	SMTPServer = v.GetString("email.SMTPServer")
	EmailSMTPPort = v.GetString("email.SMTPPort")
	EmailSMTPKey = v.GetString("email.SMTPKey")

}

// InitMysql 加载数据库
func InitMysql() {
	util.InitMysql(DSN)
}

// InitAlipay 支付宝相关配置
func InitAlipay() {
	//初始化配置文件
	util.InitAlipay(AlipayAppId, AlipayPrivateKey, AppPublicCertPath, AlipayRootCertPath, AlipayPublicCertPath, AlipayNotifyURL, AlipayReturnURL)

}

// InitRedis 加载redis
func InitRedis() {
	util.InitRedis(RedisAddress)
}

// InitAliYunOSS 加载阿里云OSS
func InitAliYunOSS() {
	util.InitAliYunOSS(AliYunOSSEndpoint, AliYunOSSAccessKeyId, AliYunOSSAccessKeySecret, ProfileBucket, OSSClientURL)
}

// InitEmail 加载Email
func InitEmail() {
	util.InitEmail(SenderEmail, SMTPServer, EmailSMTPPort, EmailSMTPKey)
}

// InitRouter 加载路由
func InitRouter() {
	router.RunRouter()
}

// InitGoruntime 加载协程
func InitGoruntime() {
	dispatchMQ := mq.DispatchMQ{}

	//开启消费商品信息协程
	go func() {
		dispatchMQ.ConsumeAddRAMMQ()
	}()

	//开启消费充值星币协程
	go func() {
		dispatchMQ.ConsumeAddSCoinMQ()
	}()

	//开启redis轮询协程
	go func() {
		orderService := service.OrderService{}
		orderService.TimerTaskCheckOrder()
	}()
}
