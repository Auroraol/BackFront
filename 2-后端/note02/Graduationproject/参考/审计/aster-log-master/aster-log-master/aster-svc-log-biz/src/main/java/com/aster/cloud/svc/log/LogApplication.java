package com.aster.cloud.svc.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 日志服务启动类
 *
 * @author 王骞
 * @date 2021-01-17
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.heshuo.lmp.log.**.dao"})
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
