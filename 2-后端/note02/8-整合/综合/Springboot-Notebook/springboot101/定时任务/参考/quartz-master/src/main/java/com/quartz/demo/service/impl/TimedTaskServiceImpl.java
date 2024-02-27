package com.quartz.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.quartz.demo.config.RemindJob;
import com.quartz.demo.mapper.TimedTaskMapper;
import com.quartz.demo.pojo.AddRequest;
import com.quartz.demo.pojo.ResultBO;
import com.quartz.demo.pojo.Task;
import com.quartz.demo.pojo.TimedTask;
import com.quartz.demo.service.TaskService;
import com.quartz.demo.service.TimedTaskService;
import com.quartz.demo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @PackgeName: com.quartz.demo.service.impl
 * @ClassName: QuartzServiceImpl
 * @Author: zjy
 * Date: 2020/6/6 21:08
 * project name: quartz
 * @Version:
 * @Description:
 */
@Slf4j
@Service
public class TimedTaskServiceImpl extends ServiceImpl<TimedTaskMapper, TimedTask> implements TimedTaskService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TimedTaskMapper timedTaskMapper;

    @Override
    public ResultBO addQuartzJob(AddRequest addRequest) {

        ResultBO resultBO = new ResultBO();
        Task task = taskService.selectById(addRequest.getTaskId());

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
     * 删除定时任务
     * @param taskId
     * @return
     */
    @Override
    public ResultBO deleteQuartzJob(Integer taskId) throws Exception {

        ResultBO resultBO = new ResultBO();
        Task task = taskService.selectById(taskId);

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
     * 删除timedTask中的数据
     * @param taskId
     */
    private void deleteByTaskId(Integer taskId) {
        timedTaskMapper.deleteByTaskId(taskId);
    }

    /**
     * 添加定时任务
     * @param request
     * @param task
     */
    public ResultBO addQuartz(AddRequest request, Task task) {

        try {
            addOneTime(request, task);
            addTwoTime(request, task);
            addThirdTime(request, task);

            return ResultBO.success("添加定时任务成功!");
        } catch (Exception e){
            e.printStackTrace();
            return ResultBO.fail("添加定时任务失败!");
        }
    }

    /**
     * 第一次提醒
     */
    public void addOneTime(AddRequest request, Task task) {

        TimedTask timedTask = new TimedTask();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(DateUtils.timeToCron(request.getOneTime()));
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("oneTime1" + task.getId(), "remind")
                .withSchedule(cronScheduleBuilder)
                .usingJobData("taskId", task.getId())
                .usingJobData("taskName", task.getName())
                .usingJobData("execution", request.getOneTime())
                .build();

        JobDetail jobDetail = JobBuilder.newJob(RemindJob.class)
                .usingJobData("taskId", task.getId())
                .usingJobData("taskName", task.getName())
                .usingJobData("execution", request.getOneTime())
                .withIdentity("oneTime1" + task.getId(), "remind")
                .storeDurably()
                .build();

        try {
            // 如果任务存在了就删除掉
            if (scheduler.checkExists(jobDetail.getKey())) {
                scheduler.deleteJob(jobDetail.getKey());
            }

            timedTask.setTaskId(task.getId());
            timedTask.setCron(DateUtils.timeToCron(request.getOneTime()));
            timedTask.setName(task.getName());
            timedTask.setJobGroup("remind");
            timedTask.setCreateTime(new Date());
            timedTask.setDeleteFlag(0);
            timedTask.setExecutionTime(DateUtils.timeToDate(request.getOneTime()));
            insert(timedTask);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第二次提醒
     */
    public void addTwoTime(AddRequest request, Task task) {

        String time = request.getThirdTime();
        Integer taskId = task.getId();
        String taskName = task.getName();

        TimedTask timedTask = new TimedTask();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(DateUtils.timeToCron(time));
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(taskName, "remind")
                .withSchedule(cronScheduleBuilder)
                .usingJobData("taskId", taskId)
                .usingJobData("taskName", taskName)
                .build();

        JobDetail jobDetail = JobBuilder.newJob(RemindJob.class)
                .usingJobData("taskId", taskId)
                .usingJobData("taskName", taskName)
                .withIdentity(taskName, "remind")
                .storeDurably()
                .build();

        try {
            // 如果任务存在了就删除掉
            if (scheduler.checkExists(jobDetail.getKey())) {
                scheduler.deleteJob(jobDetail.getKey());
            }
            timedTask.setTaskId(taskId);
            timedTask.setCron(DateUtils.timeToCron(time));
            timedTask.setName(taskName);
            timedTask.setJobGroup("remind");
            timedTask.setCreateTime(new Date());
            timedTask.setDeleteFlag(0);
            timedTask.setExecutionTime(DateUtils.timeToDate(time));
            insert(timedTask);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三次提醒
     */
    public void addThirdTime(AddRequest request, Task task) {
        String time = request.getThirdTime();
        Integer taskId = task.getId();
        String taskName = task.getName();

        TimedTask timedTask = new TimedTask();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(DateUtils.timeToCron(time));
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(taskName, "remind")
                .withSchedule(cronScheduleBuilder)
                .usingJobData("taskId", taskId)
                .usingJobData("taskName", taskName)
                .build();

        JobDetail jobDetail = JobBuilder.newJob(RemindJob.class)
                .usingJobData("taskId", taskId)
                .usingJobData("taskName", taskName)
                .withIdentity(taskName, "remind")
                .storeDurably()
                .build();

        try {
            // 如果任务存在了就删除掉
            if (scheduler.checkExists(jobDetail.getKey())) {
                scheduler.deleteJob(jobDetail.getKey());
            }

            scheduler.scheduleJob(jobDetail, trigger);
            timedTask.setTaskId(taskId);
            timedTask.setCron(DateUtils.timeToCron(time));
            timedTask.setName(taskName);
            timedTask.setJobGroup("remind");
            timedTask.setCreateTime(new Date());
            timedTask.setDeleteFlag(0);
            timedTask.setExecutionTime(DateUtils.timeToDate(time));
            insert(timedTask);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     * @param task
     * @return
     */
    private ResultBO deleteQuartz(Task task) throws Exception{

        ResultBO resultBO = new ResultBO();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<TimedTask> timedTaskList = selectByTaskId(task.getId());
        for(TimedTask timedTask : timedTaskList){
            scheduler.deleteJob(JobKey.jobKey(timedTask.getName(), "remind"));
        }
        resultBO.setSucceed(true);
        resultBO.setMsg("删除定时任务成功!");
        resultBO.setCode(200);
        return resultBO;
    }

    /**
     * 根据任务id查询定时任务列表
     * @param id
     * @return
     */
    private List<TimedTask> selectByTaskId(Integer id) {

        List<TimedTask> timedTasks = timedTaskMapper.selectByTaskId(id);
        return timedTasks;
    }
}
