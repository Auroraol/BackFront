//package com.springboot101.limit.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.Serializable;
//
//
///**
// * 配置类用于配置 RedisTemplate，用于存储限流器相关的数据。
// */
//@Configuration
//public class RedisLimiterHelper {
//
//    /**
//     * 配置 RedisTemplate 用于存储限流器相关的数据。
//     *
//     * @param redisConnectionFactory Lettuce 连接工厂，用于创建 Redis 连接。
//     * @return 配置好的 RedisTemplate。
//     */
//    @Bean
//    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
//
//        // 设置键序列化器为 StringRedisSerializer
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // 设置值序列化器为 GenericJackson2JsonRedisSerializer，用于将对象序列化为 JSON 格式
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        // 设置连接工厂
//        template.setConnectionFactory(redisConnectionFactory);
//
//        return template;
//    }
//}