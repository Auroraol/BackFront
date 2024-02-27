package com.quartz.demo.service;

import com.baomidou.mybatisplus.service.IService;
import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;
import com.quartz.demo.pojo.TimedTask;

/**
 * @PackgeName: com.quartz.demo.service
 * @ClassName: impl
 * @Author: zjy
 * Date: 2020/6/6 21:07
 * project name: quartz
 * @Version:
 * @Description:
 */
public interface TimedTaskService extends IService<TimedTask> {


    /**
     * 添加定时任务
     * @param addRequest
     */
    ResultBO addQuartzJob(AddRequest addRequest);

    /**
     * 删除定时任务
     * @param taskId
     * @return
     */
    ResultBO deleteQuartzJob(Integer taskId) throws Exception;
}
