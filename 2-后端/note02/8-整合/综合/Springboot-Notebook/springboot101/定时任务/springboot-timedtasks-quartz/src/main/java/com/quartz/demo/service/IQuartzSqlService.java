package com.quartz.demo.service;

/**
 * 实现:
 * 定时数据库备份
 * 定期删除过期数据库备份
 * @Author: LFJ
 * @Date: 2024-02-15 17:11
 */
public interface IQuartzSqlService {
		void mysqlBackupTask();

		void removeMysqlBackupTask();
}
