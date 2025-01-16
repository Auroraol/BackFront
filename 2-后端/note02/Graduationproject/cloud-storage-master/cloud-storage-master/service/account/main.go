package main

import (
	"gitee.com/phpdi/cloud-storage/service/account/handler"
	"gitee.com/phpdi/cloud-storage/service/account/proto"
	"github.com/micro/go-micro/v2"
	"log"
	"time"
)

func main() {

	//创建一个service
	service := micro.NewService(
		micro.Name("go.micro.service.user"),
		micro.RegisterTTL(time.Second*10),     //10s检查等待时间
		micro.RegisterInterval(time.Second*5), //服务每5s发一次
	)

	// 这里是为了接收命令行的参数
	service.Init()

	if err := proto.RegisterUserServiceHandler(service.Server(), new(handler.User)); err != nil {
		log.Println(err)
	}

	if err := service.Run(); err != nil {
		log.Println(err)
	}
}
