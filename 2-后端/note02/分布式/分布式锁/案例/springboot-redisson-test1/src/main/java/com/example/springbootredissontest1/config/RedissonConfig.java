package com.example.springbootredissontest1.config;

import com.example.springbootredissontest1.utils.SecurityUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author Janwes
 * @version 1.0.0
 * @package com.janwes.config
 * @date 2022/4/11 15:39
 * @description
 */
@Configuration
public class RedissonConfig {


//
//    private static final String REDISSON_PREFIX = "redis://";
//
//    private static final String REDISSON_SSL_PREFIX = "rediss://";
//
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient(RedisProperties redisProperties) {
//        Config config = new Config();
//        // 看门狗超时时长 默认30s
//        //config.setLockWatchdogTimeout(50000L);
//        // 单机节点模式
//        SingleServerConfig singleServerConfig = config.useSingleServer();
//        // 通过
//        String url = REDISSON_PREFIX + redisProperties.getHost() + ":" + redisProperties.getPort();
//        // 可以用"rediss://ip:port"来启用SSL连接
//        singleServerConfig.setAddress(url)
//                .setPassword(SecurityUtil.decrypt(redisProperties.getPassword()))
//                .setDatabase(redisProperties.getDatabase());
//
//        /*// cluster集群模式
//        config.useClusterServers()
//                .addNodeAddress(urls);*/
//
//        /*// 主从模式
//        config.useMasterSlaveServers()
//                .setMasterAddress(masterAddress)
//                .setSlaveAddresses(urls);*/
//
//        /*// sentinel哨兵模式
//        config.useSentinelServers()
//                .setMasterName()
//                .setSentinelAddresses();*/
//
//        return Redisson.create(config);
//    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        return Redisson.create(config);
    }
}
