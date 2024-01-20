package com.springboot101.limit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**

 * @Description:  演示方法  用于在 Redis 中添加大量键值对
 */
@Controller
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
//    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 映射 GET 请求 "/addKey" 到此方法，用于在 Redis 中添加大量键值对。
     *
     * @return 返回 null，因为这是一个演示方法，不返回实际内容。
     */
    @GetMapping("/addKey")
    @ResponseBody
    public String addkey() {

        for (int i = 0; i < 500000; i++) {

            redisTemplate.opsForValue().set("test_key_" + i, i);
        }
        System.out.println("end");
        return null;
    }
}