package com.quartz.demo.service;

import com.quartz.demo.entity.TimedTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;

/**
 * 定时任务接口
* @author 16658
* @description 针对表【t_timed_task】的数据库操作Service
* @createDate 2024-02-09 18:12:48
*/
public interface TimedTaskService extends IService<TimedTask> {
	/**
	 * 添加定时任务作业
	 * @param addRequest
	 */
	ResultBO addQuartzJob(AddRequest addRequest);

	/**
	 * 删除定时任务作业
	 * @param taskId
	 * @return
	 */
	ResultBO deleteQuartzJob(Integer taskId) throws Exception;
}
