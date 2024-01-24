package com.example.redisredlock.connfig;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * redisson配置类
// */
//@Configuration
//public class RedissonConfig {
//
//    @Bean
//    public RedissonClient redissonClient() {
//       Config config = new Config();
//       config.useClusterServers()
//               .setScanInterval(2000)
//               .addNodeAddress("redis://10.211.55.4:6379", "redis://redis://10.211.55.4:6380")
//               .addNodeAddress("redis://redis://10.211.55.4:6381");
//       RedissonClient redisson = Redisson.create(config);
//       return redisson;
//    }
//
//}
////


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class RedissonConfig {

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson.yaml").getInputStream());
		return Redisson.create(config);
	}
}