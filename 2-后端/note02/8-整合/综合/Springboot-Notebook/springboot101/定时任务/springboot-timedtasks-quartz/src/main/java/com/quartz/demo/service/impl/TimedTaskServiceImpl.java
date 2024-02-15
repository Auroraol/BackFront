package com.quartz.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quartz.demo.task.RemindTask;
import com.quartz.demo.entity.Task;
import com.quartz.demo.entity.TimedTask;
import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;
import com.quartz.demo.service.TaskService;
import com.quartz.demo.service.TimedTaskService;
import com.quartz.demo.mapper.TimedTaskMapper;
import com.quartz.demo.utils.DateUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务实现
* @author 16658
* @description 针对表【t_timed_task】的数据库操作Service实现
* @createDate 2024-02-09 18:12:48
*/
@Service
public class TimedTaskServiceImpl extends ServiceImpl<TimedTaskMapper, TimedTask>
    implements TimedTaskService{
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TimedTaskMapper timedTaskMapper;

	/**
	 * 添加定时任务作业
	 * @param addRequest
	 */
	@Override
	public ResultBO addQuartzJob(AddRequest addRequest) {

		ResultBO resultBO = new ResultBO();
		//根据主键id查询任务表, 任务是否存在
		Task task = taskService.getById(addRequest.getTaskId());

		if(null == task){
			resultBO.setCode(400);
			resultBO.setMsg("请核对任务id是否正确!");
			resultBO.setSucceed(false);
			return resultBO;
		}
		resultBO = addQuartz(addRequest, task);
		return resultBO;
	}

	/**
	 * 添加定时任务
	 * @param request
	 * @param task
	 */
	public ResultBO addQuartz(AddRequest request, Task task) {
		try {
			addRemindTask(request, task, request.getOneName(), request.getOneTime());
			addRemindTask(request, task, request.getTwoName(), request.getTwoTime());
			addRemindTask(request, task, request.getThirdName(), request.getThirdTime());

			return ResultBO.success("添加定时任务成功!");
		} catch (Exception e){
			e.printStackTrace();
			return ResultBO.fail("添加定时任务失败!");
		}
	}

	/**
	 * 添加提醒任务
	 */
	public void addRemindTask(AddRequest request, Task task, String type, String executionTime) {
		TimedTask timedTask = new TimedTask();
		String taskId = task.getId().toString();
		String taskName = task.getName();

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		//

		// 创建Cron触发器
		CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.cronSchedule(DateUtils.timeToCron(executionTime));  //使用Cron表达式设置触发器的调度规则
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(type + taskId, "remind") // 标识符唯一性
				.withSchedule(cronScheduleBuilder)
				.usingJobData("taskId", taskId)
				.usingJobData("taskName", taskName)
				.usingJobData("execution", executionTime)
				.build();

		// 创建任务
		JobDetail jobDetail = JobBuilder.newJob(RemindTask.class)   //指定任务实例为RemindTask类
				.usingJobData("taskId", taskId)
				.usingJobData("taskName", taskName)
				.usingJobData("execution", executionTime)
				.withIdentity(type + taskId, "remind") //设置 JobDetail 的唯一标识符
				.storeDurably()
				.build();

		try {
			// 如果任务存在了就删除掉
			if (scheduler.checkExists(jobDetail.getKey())) {
				scheduler.deleteJob(jobDetail.getKey());
			}

			// 设置任务信息, 保存到数据库(定时任务表中)
			timedTask.setTaskId(Integer.valueOf(taskId));
			timedTask.setCron(DateUtils.timeToCron(executionTime));
			timedTask.setName(taskName);
			timedTask.setJobGroup("remind");
			timedTask.setCreateTime(LocalDateTime.now());
			timedTask.setDeleteFlag(0);
			timedTask.setExecutionTime(DateUtils.timeToLocalDateTime(executionTime));
			timedTaskMapper.insert(timedTask);

			// 添加任务和触发器到调度程序
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 删除定时任务作业
	 * @param taskId
	 * @return
	 */
	@Override
	public ResultBO deleteQuartzJob(Integer taskId) throws Exception {

		ResultBO resultBO = new ResultBO();
		//根据主键id查询任务表, 任务是否存在
		Task task = taskService.getById(taskId);

		if(null == task){
			resultBO.setCode(400);
			resultBO.setMsg("请核对任务id是否正确!");
			resultBO.setSucceed(false);
			return resultBO;
		}
		resultBO = deleteQuartz(task);

		return resultBO;
	}

	/**
	 * 删除定时任务
	 * @param task
	 * @return
	 */
	private ResultBO deleteQuartz(Task task) throws Exception{

		Integer id = task.getId();
		ResultBO resultBO = new ResultBO();
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		//根据任务id查询定时任务列表
		List<TimedTask> timedTaskList = selectByTaskId(id);
		for(TimedTask timedTask : timedTaskList){
			// 尝试删除Quartz作业
			boolean isJobDeleted = scheduler.deleteJob(JobKey.jobKey(timedTask.getName(), "remind"));
			if (isJobDeleted) {
				// 如果成功删除了作业，则从数据库中删除相应的TimedTask记录
				deleteByTaskId(id);
			}
		}
		resultBO.setSucceed(true);
		resultBO.setMsg("删除定时任务成功!");
		resultBO.setCode(200);
		return resultBO;
	}

	/**
	 * 根据任务id删除定时任务列表
	 * @param taskId
	 */
	private void deleteByTaskId(Integer taskId) {
		timedTaskMapper.deleteByTaskId(taskId);
	}

	/**
	 * 根据任务id查询定时任务列表
	 * @param taskId
	 * @return
	 */
	private List<TimedTask> selectByTaskId(Integer taskId) {
		List<TimedTask> timedTasks = timedTaskMapper.selectByTaskId(taskId);
		return timedTasks;
	}

}




