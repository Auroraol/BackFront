package com.quartz.demo.pojo;
import lombok.Data;

/**
 * 定时任务添加实体
 */
@Data
public class AddRequest {

    /**
     * 任务id
     */
    private long taskId;

    /**
     * 第一次提醒name
     */
    private String oneName;

    /**
     * 第一次提醒时间
     */
    private String oneTime;

    /**
     * 第二次提醒name
     */
    private String twoName;

    /**
     * 第二次提醒时间
     */
    private String twoTime;

    /**
     * 第三次提醒name
     */
    private String thirdName;

    /**
     * 第三次提醒时间
     */
    private String thirdTime;

}
