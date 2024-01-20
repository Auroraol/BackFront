package com.springboot101.limit.aspect;

import com.springboot101.limit.enmu.LimitType;

import java.lang.annotation.*;

/**
 * @description redis限流注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimter {

    /**
     * 名字
     */
    String name() default "";

    /**
     * key
     */
    String key() default "";

    /**
     * Key的前缀
     */
    String prefix() default "";

    /**
     * 过期时间，单位秒
     */
    int period();

    /**
     * 单位时间限制通过请求数
     */
    int count();

    /**
     * 限流的类型(用户自定义key 或者 请求ip)
     */
    LimitType limitType() default LimitType.CUSTOMER;

    /**
     * 返回值
     */
    String message() default "false";
}
