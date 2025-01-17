package com.zhang.musicrs.util.redisUtil;

import org.springframework.stereotype.Component;

/**
 * @author ZhangChaojie
 * @Description: TODO(redis操作工具类 ， 基于RedisTemplate)
 * @date 2021/12/24 22:59
 */
@Component
public class RedisUtils {
//
//     private final RedisTemplate<String, String> redisTemplate;
//
//     @Autowired
//     public RedisUtils(RedisTemplate<String, String> redisTemplate) {
//         this.redisTemplate = redisTemplate;
//     }
//
//     /**
//      * 读取缓存
//      *
//      * @param key
//      * @return
//      */
//     public String get(final String key) {
//         return redisTemplate.opsForValue().get(key);
//     }
//
//     /**
//      * 写入缓存
//      */
//     public boolean set(final String key, String value) {
//         boolean result = false;
//         try {
//             redisTemplate.opsForValue().set(key, value);
//             result = true;
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return result;
//     }
//
//     /**
//      * 写入缓存，设置超时时间
//      */
//     public boolean set(final String key, String value, long milliseconds) {
//         boolean result = false;
//         try {
//             redisTemplate.opsForValue().set(key, value, milliseconds);
//             result = true;
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return result;
//     }
//
//     /**
//      * 更新缓存
//      */
//     public boolean getAndSet(final String key, String value) {
//         boolean result = false;
//         try {
//             redisTemplate.opsForValue().getAndSet(key, value);
//             result = true;
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return result;
//     }
//
//     /**
//      * 删除缓存
//      */
//     public boolean delete(final String key) {
//         boolean result = false;
//         try {
//             redisTemplate.delete(key);
//             result = true;
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return result;
//     }
}
