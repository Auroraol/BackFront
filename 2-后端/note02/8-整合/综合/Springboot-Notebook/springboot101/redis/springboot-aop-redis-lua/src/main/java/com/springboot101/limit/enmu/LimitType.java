package com.springboot101.limit.enmu;


/**
 * @author xiaofu
 * @description 限流类型
 * @date 2020/4/8 13:47
 */
public enum LimitType {

    /**
     * 自定义key
     */
    CUSTOMER,

    /**
     * 请求者IP
     */
    IP;
}