package com.bank.utils;

/**
 * @description: 字符串工具类
 * @date 2023-07-16 13:30
 */
public class StringUtils {
    /**
     * 判断一个字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && !"".equals(str));
    }
}
