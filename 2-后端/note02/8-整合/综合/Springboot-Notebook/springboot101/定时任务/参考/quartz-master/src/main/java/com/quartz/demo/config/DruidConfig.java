package com.quartz.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @PackgeName: com.quartz.demo.config
 * @ClassName: DruidConfig
 * @Author: zjy
 * Date: 2020/6/13 16:32
 * project name: quartz
 * @Version:
 * @Description:
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
