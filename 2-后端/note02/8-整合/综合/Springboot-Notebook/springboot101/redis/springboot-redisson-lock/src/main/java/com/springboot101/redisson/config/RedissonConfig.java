package com.springboot101.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.io.IOException;

/**
 
 * @Description:
 */
@Configuration
public class RedissonConfig {

    /**
    //不建议
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {

//        Spring Boot 会自动配置 RedissonClient
//        通过application.yml这个
//        * spring:
//              redis:
//                redisson:
//                  config: "classpath:redisson.yml"

        return Redisson.create();}
    **/

    // 推荐方式
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        return Redisson.create(config);
    }
}
