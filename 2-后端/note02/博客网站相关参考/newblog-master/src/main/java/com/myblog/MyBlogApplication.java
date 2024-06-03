package com.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wenzhihuai
 * @since 2020/2/21 17:44
 */
@EnableCaching
@EnableScheduling
//@SpringBootApplication
@ComponentScan(basePackages = {"com.myblog.dao"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MyBlogApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(MyBlogApplication.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
