package com.quartz.demo.task;

/**
 * @Author: LFJ
 * @Date: 2024-02-15 17:37
 */
import com.quartz.demo.service.IQuartzSqlService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MysqlBackupTask extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		//获取JobDetail中传递的参数
		IQuartzSqlService quartzService =
				(IQuartzSqlService) jobExecutionContext.getJobDetail().getJobDataMap().get("quartzSqlService");
		//具体定时业务
		quartzService.mysqlBackupTask();
	}
}
