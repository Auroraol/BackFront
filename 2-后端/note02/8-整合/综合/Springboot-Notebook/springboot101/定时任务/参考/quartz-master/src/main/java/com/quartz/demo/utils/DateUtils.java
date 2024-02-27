package com.quartz.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackgeName: com.quartz.demo.utils
 * @ClassName: DateUtils
 * @Author: zjy
 * Date: 2020/6/14 10:41
 * project name: quartz
 * @Version:
 * @Description:
 */
public class DateUtils {

    /**
     * 将时间格式的字符串转换成cron表达式
     *
     * @param time yyyy-mm-dd HH:mm:ss
     * @return 0/5 * * * * ?        * 25 11 14 6 ?
     */
    public static String timeToCron(String time) {

        //前面传的是""或者null则把定时启动时间设置为2060年1月1日0点0分0秒执行
        if (time == null || "".equals(time)) {
            return "0 0 0 1 1 ? 2060";
        }

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat("0 mm HH dd MM ? yyyy");
            String formatTimeStr = sdf.format(date);
            return formatTimeStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "0 0 0 1 1 ? 2060";
    }

    /**
     * 将时间格式的字符串转换成Date
     *
     * @param time yyyy-mm-dd HH:mm:ss
     */
    public static Date timeToDate(String time) {


        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
