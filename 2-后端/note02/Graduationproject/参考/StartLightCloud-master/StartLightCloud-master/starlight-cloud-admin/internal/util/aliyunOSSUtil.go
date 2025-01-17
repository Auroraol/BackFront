/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/06/21 15:21
 */

package util

import (
	"fmt"
	"github.com/aliyun/aliyun-oss-go-sdk/oss"
)

var (
	OSSClient *oss.Client

	aliYunOSSEndpoint        = ""
	aliYunOSSAccessKeyId     = ""
	aliYunOSSAccessKeySecret = ""
	profileBucket            = ""
	OSSClientURL             = ""
)

func InitAliYunOSS(configAliYunOSSEndpoint string, configAliYunOSSAccessKeyId string, configAliYunOSSAccessKeySecret string, configAliYunOSSBucketName string, configOSSClientURL string) {
	aliYunOSSEndpoint = configAliYunOSSEndpoint
	aliYunOSSAccessKeyId = configAliYunOSSAccessKeyId
	aliYunOSSAccessKeySecret = configAliYunOSSAccessKeySecret
	profileBucket = configAliYunOSSBucketName
	OSSClientURL = configOSSClientURL

	fmt.Println("init..", aliYunOSSEndpoint, aliYunOSSAccessKeyId, aliYunOSSAccessKeySecret, profileBucket)
	//创建OSSClient实例
	OSSClient, _ = oss.New(aliYunOSSEndpoint, aliYunOSSAccessKeyId, aliYunOSSAccessKeySecret)

	return
}

func GetProfileBucket() (bucket *oss.Bucket) {
	bucket, err := OSSClient.Bucket(profileBucket)
	if err != nil {
		fmt.Println(err)
	}
	return
}

func GetOSSClientURL() (URL string) {
	URL = OSSClientURL
	return
}
