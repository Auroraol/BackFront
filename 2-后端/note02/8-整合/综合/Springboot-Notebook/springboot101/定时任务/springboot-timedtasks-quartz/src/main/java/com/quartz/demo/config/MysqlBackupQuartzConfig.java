package com.quartz.demo.config;
import com.quartz.demo.service.IQuartzSqlService;
import com.quartz.demo.quartz.job.MysqlBackupJob;
import com.quartz.demo.quartz.job.RemoveMysqlBackupJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

/**
 * Mysql备份计划任务在启动
 * @Author: LFJ
 * @Date: 2024-02-15 17:46
 */
@Configuration
public class MysqlBackupQuartzConfig {
	private static final String MYSQL_BACKUP_JOB_GROUP_NAME = "MYSQL_BACKUP_JOB";
	private static final String MYSQL_BACKUP_TRIGGER_GROUP_NAME = "MYSQL_BACKUP_TRIGGER";
	private static final String REMOVE_MYSQL_BACKUP_JOB_GROUP_NAME = "REMOVE_MYSQL_BACKUP_JOB";
	private static final String REMOVE_MYSQL_BACKUP_TRIGGER_GROUP_NAME = "REMOVE_MYSQL_BACKUP_TRIGGER";

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private IQuartzSqlService iQuartzSqlService;

	/**添加备份**/
	// 创建任务
	private JobDetail mysqlBackupJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("quartzSqlService", iQuartzSqlService);
		return JobBuilder.newJob(MysqlBackupJob.class) //[重要] 指定任务实例为MysqlBackupJob类
				.withIdentity("mysqlBackupJobDetail", MYSQL_BACKUP_JOB_GROUP_NAME) // name, group
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	// 创建Cron触发器
	private Trigger mysqlBackupTriggerQuartz() {
		//使用固定的Cron表达式（每天凌晨1点执行）
		CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.dailyAtHourAndMinute(1, 0); //[重要] 使用Cron表达式设置触发器的调度规则
		return TriggerBuilder.newTrigger()
				.withIdentity("mysqlBackupJobDetail", MYSQL_BACKUP_TRIGGER_GROUP_NAME) //给Trigger起个名字
				.withSchedule(cronScheduleBuilder)
				.build();
	}

	/**删除备份**/
	private JobDetail removeMysqlBackupJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("quartzSqlService", iQuartzSqlService);
		return JobBuilder.newJob(RemoveMysqlBackupJob.class)
				.withIdentity("removeMysqlBackupJobDetail", REMOVE_MYSQL_BACKUP_JOB_GROUP_NAME)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	private Trigger removeMysqlBackupTriggerQuartz() {
		// 使用固定的Cron表达式（每天零点执行）
		CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.dailyAtHourAndMinute(0,0);
		return TriggerBuilder.newTrigger()
				.withIdentity("removeMysqlBackupJobDetail", REMOVE_MYSQL_BACKUP_TRIGGER_GROUP_NAME) //给Trigger起个名字
				.withSchedule(cronScheduleBuilder)
				.build();
	}

	@PostConstruct
	public void MysqlBackupScheduleJobsOnStartup() {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobDetail mysqlBackupJobDetail = mysqlBackupJobDetail();
		Trigger mysqlBackupTriggerQuartz = mysqlBackupTriggerQuartz();
		JobDetail removeMysqlBackupJobDetail = removeMysqlBackupJobDetail();
		Trigger removeMysqlBackupTriggerQuartz = removeMysqlBackupTriggerQuartz();

		try {
			// 如果任务存在了就删除掉
			if (scheduler.checkExists(mysqlBackupJobDetail.getKey())) {
				scheduler.deleteJob(mysqlBackupJobDetail.getKey());
			}

			if (scheduler.checkExists(removeMysqlBackupJobDetail.getKey())) {
				scheduler.deleteJob(removeMysqlBackupJobDetail.getKey());
			}
			// 添加任务和触发器到调度程序 * 定时数据库备份
			scheduler.scheduleJob(mysqlBackupJobDetail, mysqlBackupTriggerQuartz);
			// 添加任务和触发器到调度程序 * 定期删除过期数据库备份
			scheduler.scheduleJob(removeMysqlBackupJobDetail, removeMysqlBackupTriggerQuartz);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
