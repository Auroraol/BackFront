package main

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/router"
	"net/http"
	"time"
)

func main() {

	s := &http.Server{
		Addr:           entitys.MyAppConfig.HttpPort,
		Handler:        router.Router(),
		ReadTimeout:    time.Duration(entitys.MyAppConfig.HttpReadTimeout) * time.Second,
		WriteTimeout:   time.Duration(entitys.MyAppConfig.HttpWriteTimeout) * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	s.ListenAndServe()
}
