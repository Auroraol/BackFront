package com.example.springbootredissontest1.controller;

import com.example.springbootredissontest1.utils.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janwes
 * @version 1.0.0
 * @package com.janwes.controller
 * @date 2022/4/11 16:41
 * @description 库存控制层 测试案例
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/buy")
    public String buy() {
        boolean flag = redisLockUtil.lock("product");
        try {
            if (flag) {
                String num = redisTemplate.opsForValue().get("stock");
                if (num != null && Integer.parseInt(num) > 0) {
                    redisTemplate.opsForValue().set("stock", Integer.parseInt(num) - 1 + "");
                    return "下单成功";
                }
            }
        } finally {
            redisLockUtil.unlock("product");
        }
        return "下单失败";
    }
}
