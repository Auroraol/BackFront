package com.crayon.ratelimiterdemo.annotation;

import java.lang.annotation.*;

/**
 * redis限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     * @return
     */
    String key() default "rate:limiter";
    /**
     * 单位时间限制通过请求数
     * @return
     */
    long limit() default 3L;

    /**
     * 过期时间，单位秒
     * @return
     */
    long expire() default 30L;

    /**
     * 返回值
     * @return
     */
    String message() default "false";
}
