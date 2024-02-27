package com.quartz.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

/**
 * @program: vehicle-remote-control
 * @description:    定时任务执行的方法
 * @create: 2019-09-06 17:10
 * @author: zhoujy
 */
@Slf4j
@Service
public class RemindJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getMergedJobDataMap();
        Integer taskId = params.getInt("taskId");
        String taskName = params.getString("taskName");
        String execution = params.getString("execution");

        log.info("发送:{}任务, taskId为:{}, 执行时间为:{}", taskName, taskId, execution);
    }

}
