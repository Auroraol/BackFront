package com.crayon.ratelimiterdemo.controllrt;

import com.crayon.ratelimiterdemo.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuqiang
 */
@RestController
@Slf4j
public class TestController {

    /**
     * 设置 30秒内 只能有5次请求 超过后则返回 稍后再试
     * @param request
     * @return
     */
    @GetMapping("/testLateLimiter")
    @RateLimiter(key = "ratedemo:testLateLimiter", limit = 5, expire = 30, message = "稍后再试")
    public String sendPayment(HttpServletRequest request){
        return "正常请求";
    }


}
