package com.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 日期类工具
 * @date 2023-07-16 12:00
 */
public class DateUtils {

    /**
     * 把时间转换成 yyyy-MM-dd hh:mm:ss格式的字符串
     *
     * @param date 时间对象
     * @return String
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }

    /**
     * 把当前时间转换成 yyyy-MM-dd hh:mm:ss格式的字符串
     *
     * @return String
     */
    public static String getNewDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date());
    }
}
