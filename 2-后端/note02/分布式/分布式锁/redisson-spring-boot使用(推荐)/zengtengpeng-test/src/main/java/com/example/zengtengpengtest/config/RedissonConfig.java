package com.example.zengtengpengtest.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2024-01-23 20:10
 */
@Configuration
public class RedissonConfig {

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
		return Redisson.create(config);
	}
}
