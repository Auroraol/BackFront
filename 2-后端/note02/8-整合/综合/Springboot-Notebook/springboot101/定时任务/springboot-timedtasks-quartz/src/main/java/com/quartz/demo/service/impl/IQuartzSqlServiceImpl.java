package com.quartz.demo.service.impl;

import cn.hutool.core.date.DateUnit;
import com.quartz.demo.service.IQuartzSqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import cn.hutool.core.date.DateUtil;

/**
 * @Author: LFJ
 * @Date: 2024-02-15 17:11
 */
@Slf4j
@Service
public class IQuartzSqlServiceImpl implements IQuartzSqlService, Serializable {

	// 使用@Value注解来注入数据库连接信息
	@Value("${backup.username}")
	private String userName;

	@Value("${backup.password}")
	private String passWord;

	@Value("${backup.dbName}")
	private String dbName;

	@Value("${backup.day}")
	private int day;

	// 指定数据库备份资源路径
	@Value("${backup.folder}")
	private String backupFolderPath;

	@Override
	public void mysqlBackupTask() {

		String resourcePath = backupFolderPath;
		log.info("======执行定时器:定时备份数据库=======");
		// 构建备份文件夹路径
		String backUpPath = resourcePath + "/sql/" + java.sql.Date.valueOf(LocalDate.now());
		File backUpFile = new File(backUpPath);
		if (!backUpFile.exists()) {
			backUpFile.mkdirs();
		}
		// 构建备份数据文件路径  resourcePath/sql/当前日期（精确到日）/项目名+当前毫秒值.sql
		File dataFile = new File(backUpPath + "/springboot-timedtasks-quartz" + System.currentTimeMillis() + ".sql");
		// 拼接cmd命令
		StringBuilder sb = new StringBuilder();
		sb.append("mysqldump");
		sb.append(" -u" + userName); // 使用直接硬编码的用户名
		sb.append(" -p" + passWord); // 使用直接硬编码的密码
		sb.append(" " + dbName + " > ");
		sb.append(dataFile);
		log.info("======数据库备份cmd命令为：" + sb.toString() + "=======");
		try {
			// 执行cmd命令
			Process exec = Runtime.getRuntime().exec("cmd /c" + sb.toString());
			if (exec.waitFor() == 0) {
				log.info("======数据库备份成功，路径为：" + dataFile + "=======");
			}
		} catch (Exception e) {
			log.info("======数据库备份失败，异常为：" + e.getMessage() + "=======");
		}
	}


	@Override
	public void removeMysqlBackupTask() {
		log.info("======执行定时器:定时删除备份数据库文件=======");
		// 指定数据库备份资源路径
		String resourcePath = backupFolderPath;
		String backUpPath = resourcePath + "/sql";
		File backUpFile = new File(backUpPath);
		if (backUpFile.exists()) {
			File[] files = backUpFile.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					Date date1 = Date.valueOf(file.getName());
					Date date2 = Date.valueOf(LocalDate.now());
					// 计算文件夹名字所表示的日期与当前日期之间的天数差
					// 保存的是7天内的备份，因此7天前的备份会被删除
					long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
					if (betweenDay > day) {
						File[] subFiles = file.listFiles();
						for (File subFile : subFiles) {
							subFile.delete();
						}
						file.delete();
					}
				}
			}
		}
	}
}
