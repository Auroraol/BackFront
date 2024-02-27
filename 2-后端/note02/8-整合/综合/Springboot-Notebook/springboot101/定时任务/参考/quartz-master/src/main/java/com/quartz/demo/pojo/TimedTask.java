package com.quartz.demo.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * @PackgeName: com.mini.demo.quartz
 * @ClassName: TimedTask
 * @Author: zjy
 * Date: 2020/6/6 10:08
 * project name: mini
 * @Version:
 * @Description:
 */
@Data
@TableName("t_timed_task")
public class TimedTask {

    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 执行时间
     */
    private Date executionTime;

    /**
     * 定时任务表达式
     */
    private String cron;

    /**
     * 定时任务说明
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private int deleteFlag;
}
