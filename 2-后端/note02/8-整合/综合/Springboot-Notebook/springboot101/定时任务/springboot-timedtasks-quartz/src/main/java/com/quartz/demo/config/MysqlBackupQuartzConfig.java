package com.quartz.demo.config;
import com.quartz.demo.service.IQuartzSqlService;
import com.quartz.demo.task.MysqlBackupTask;
import com.quartz.demo.task.RemoveMysqlBackupTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
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
	private IQuartzSqlService iQuartzSqlService;

	// 创建任务
	@Bean
	public JobDetail mysqlBackupJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("quartzSqlService", iQuartzSqlService);
		JobDetail jobDetail = JobBuilder.newJob(MysqlBackupTask.class)
				.withIdentity("mysqlBackupJobDetail", MYSQL_BACKUP_JOB_GROUP_NAME)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
		return jobDetail;
	}

	// 创建Cron触发器
	@Bean
	public Trigger mysqlBackupTriggerQuartz() {
		//使用固定的Cron表达式（每天凌晨1点执行）
		CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
		Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(mysqlBackupJobDetail()) //关联上Test述的JobDetail
				.withIdentity("mysqlBackupJobDetail", MYSQL_BACKUP_TRIGGER_GROUP_NAME) //给Trigger起个名字
				.withSchedule(cronScheduleBuilder)
				.build();
		return trigger;
	}

	@Bean
	public JobDetail removeMysqlBackupJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("quartzSqlService", iQuartzSqlService);
		JobDetail jobDetail = JobBuilder.newJob(RemoveMysqlBackupTask.class)
				.withIdentity("removeMysqlBackupJobDetail", REMOVE_MYSQL_BACKUP_JOB_GROUP_NAME)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
		return jobDetail;
	}

	@Bean
	public Trigger removeMysqlBackupTriggerQuartz() {
		// 使用固定的Cron表达式（每天零点执行）
		CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.cronSchedule("0 0 0 * * ?");
		Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(removeMysqlBackupJobDetail()) //关联上Test述的JobDetail
				.withIdentity("removeMysqlBackupJobDetail", REMOVE_MYSQL_BACKUP_TRIGGER_GROUP_NAME) //给Trigger起个名字
				.withSchedule(cronScheduleBuilder)
				.build();
		return trigger;
	}
}
