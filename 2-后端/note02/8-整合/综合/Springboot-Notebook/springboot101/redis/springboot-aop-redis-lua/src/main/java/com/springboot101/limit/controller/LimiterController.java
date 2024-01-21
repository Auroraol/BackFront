package com.springboot101.limit.controller;

import com.springboot101.limit.aspect.RateLimter;
import com.springboot101.limit.enmu.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:用于测试使用自定义注解进行速率限制
 */
@RestController
public class LimiterController {

    // 用于测试, 写的不好
    // 原子整数用于跟踪每个端点的请求计数
    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();

    /**
     * @description  基于自定义键（key）进行限制
     * @return 字符串
     */
    @GetMapping("/limitTest")
    @RateLimter(key = "limitTest", period = 10, count = 3, limitType = LimitType.CUSTOMER,  message = "稍后再试")
    public String sendPayment(HttpServletRequest request) {
        return "正常请求";
    }

    /**
     * @description  基于方法名进行限制
     * @return 此端点的请求计数
     */
    @RateLimter(key = "limitTest1", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {
        return ATOMIC_INTEGER_1.incrementAndGet();
    }

    /**
     * @description 基于自定义键（key）进行限制
     * @date 2020/4/8 13:42
     */
    @RateLimter(key = "customer_limit_test", period = 10, count = 3, limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {
        return ATOMIC_INTEGER_2.incrementAndGet();
    }

    /**

     * @description 基于IP地址进行限制
     * @date 2020/4/8 13:42
     */
    @RateLimter(key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {
        return ATOMIC_INTEGER_3.incrementAndGet();
    }

}