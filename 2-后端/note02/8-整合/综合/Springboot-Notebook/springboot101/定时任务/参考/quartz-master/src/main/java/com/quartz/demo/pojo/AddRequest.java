package com.quartz.demo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "添加定时任务", description = "添加定时任务")
public class AddRequest {

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private long taskId;

    /**
     * 第一次提醒name
     */
    @ApiModelProperty("第一次提醒name")
    private String oneName;

    /**
     * 第一次提醒时间
     */
    @ApiModelProperty("第一次提醒时间")
    private String oneTime;

    /**
     * 第二次提醒name
     */
    @ApiModelProperty("第二次提醒name")
    private String twoName;

    /**
     * 第二次提醒时间
     */
    @ApiModelProperty("第二次提醒时间")
    private String twoTime;

    /**
     * 第三次提醒name
     */
    @ApiModelProperty("第三次提醒name")
    private String thirdName;

    /**
     * 第三次提醒时间
     */
    @ApiModelProperty("第三次提醒时间")
    private String thirdTime;

}
