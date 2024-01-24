package com.example.springbootredissontest1.config;

import com.example.springbootredissontest1.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.Arrays;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.config
 * @date 2021/7/21 16:17
 * @description 自定义redis配置类 Redis客户端：Jedis、Lettuce、Redisson(基于分布式)
 */
@Configuration
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    /**
     * spring-boot-starter-data-redis的redis连接器基于Lettuce
     * Lettuce是Spring Data Redis 通过包支持的基于Netty的开源连接器
     *
     * @param redisProperties
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        try {
            RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
            // GenericObjectPoolConfig 连接池配置
            GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
            genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
            genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
            genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
            genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());

            // 构建Lettuce连接池客户端配置
            LettucePoolingClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                    .poolConfig(genericObjectPoolConfig).build();

            // 单点redis
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            // 哨兵redis
            // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
            // 集群redis
            // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
            redisConfig.setHostName(redisProperties.getHost());
            redisConfig.setPort(redisProperties.getPort());
            // 密文解密
            String password = SecurityUtil.decrypt(redisProperties.getPassword());
            redisConfig.setPassword(password);
            redisConfig.setDatabase(redisProperties.getDatabase());

            log.info("===> initialize RedisConfig,connect to redis server success......");
            return new LettuceConnectionFactory(redisConfig, clientConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("===> connect to redis server failure......", e);
        }
    }

    /**
     * redis缓存管理器
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 分别创建String和JSON格式序列化对象，对缓存数据key和value进行转换
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        // 解决查询缓存转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 定制缓存数据序列化方式及时效
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                // 缓存key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                // 缓存value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues();
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
    }

    /**
     * 自定义RedisTemplate  key的序列化方式
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 使用GenericJackson2JsonRedisSerializer序列化器缓存时，对象会有@标识
        //GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 值采用json序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置hash key 和value序列化模式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 重写key生成策略 (类名+方法名+参数名)
     * 自定义key生成策略需extend CachingConfigurerSupport类
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName())
                    .append(".")
                    .append(method.getName())
                    .append(Arrays.toString(objects));
            return stringBuilder;
        };
    }
}
