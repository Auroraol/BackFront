package com.quartz.demo.quartz.job;

/**
 * 执行删除数据库备份定时任务
 * @Author: LFJ
 * @Date: 2024-02-15 17:38
 */
import com.quartz.demo.service.IQuartzSqlService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RemoveMysqlBackupJob extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		//获取JobDetail中传递的参数
		IQuartzSqlService quartzService =
				(IQuartzSqlService) jobExecutionContext.getJobDetail().getJobDataMap().get("quartzSqlService");
		//具体定时业务
		quartzService.removeMysqlBackupTask();
	}
}
