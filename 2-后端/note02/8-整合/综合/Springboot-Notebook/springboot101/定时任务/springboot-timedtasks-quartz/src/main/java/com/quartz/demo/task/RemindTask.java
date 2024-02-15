package com.quartz.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

/**
 * @program: vehicle-remote-control
 * @description:    执行定时任务
 * @create: 2019-09-06 17:10
 * @author: zhoujy
 */
@Slf4j
@Service
public class RemindTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 从作业上下文中获取作业参数
        JobDataMap params = jobExecutionContext.getMergedJobDataMap();
        // 获取任务ID
        int taskId = params.getInt("taskId");
        // 获取任务名称
        String taskName = params.getString("taskName");
        // 获取执行时间
        String execution = params.getString("execution");
        // 记录日志，记录发送提醒任务的信息，包括任务名称、任务ID和执行时间
        log.info("发送:{}任务, taskId为:{}, 执行时间为:{}", taskName, taskId, execution);
        //  TODO 具体定时业务

    }

}
