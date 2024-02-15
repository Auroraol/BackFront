延时任务几种实现方法(了解)



```


Quartz 是一个优秀的开源任务调度框架，完全基于 Java 实现，作用相当于一个定时器，可以在指定的时间点或时间间隔执行任务。可以使用在如“月底总结“，”每日结算”等需要在指定时间执行任务的需求中。

特点
强大的调度功能，例如支持丰富多样的调度方法，可以满足各种常规及特殊需求。
灵活的应用方式，例如支持任务和调度的多种组合方式，支持调度数据的多种存储方式。
分布式和集群能力，Quartz 能够使用多个单独 Quartz 程序作为节点共用一套数据库表来实现集群
```



### 使用Cron表达式

用于设置触发器的调度规则

```
常用示例: 
0 0 12 * * ? 每天12点触发 
0 15 10 ? * * 每天10点15分触发 
0 15 10 * * ? 每天10点15分触发 
0 15 10 * * ? * 每天10点15分触发 
0 15 10 * * ? 2005 2005年每天10点15分触发 
0 * 14 * * ? 每天下午的 2点到2点59分每分触发 
0 0/5 14 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发) 
0 0/5 14,18 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发) 
每天下午的 18点到18点59分(整点开始，每隔5分触发) 
0 0-5 14 * * ? 每天下午的 2点到2点05分每分触发 
0 10,44 14 ? 3 WED 3月分每周三下午的 2点10分和2点44分触发 （特殊情况，在一个时间设置里，执行两次或 两次以上的情况） 
0 59 2 ? * FRI    每周5凌晨2点59分触发； 
0 15 10 ? * MON-FRI 从周一到周五每天上午的10点15分触发 
0 15 10 15 * ? 每月15号上午10点15分触发 
0 15 10 L * ? 每月最后一天的10点15分触发 
0 15 10 ? * 6L 每月最后一周的星期五的10点15分触发 
0 15 10 ? * 6L 2002-2005 从2002年到2005年每月最后一周的星期五的10点15分触发 
0 15 10 ? * 6#3 每月的第三周的星期五开始触发 
0 0 12 1/5 * ? 每月的第一个中午开始每隔5天触发一次 
0 11 11 11 11 ? 每年的11月11号 11点11分触发(光棍节)
```





```java
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import java.time.LocalDate;
import java.io.File;
import java.util.Map;

@Slf4j
@Service
public class QuartzServiceImpl implements IQuartzService {
    @Autowired
    private JdbcUtils jdbcUtils;

    @Override
    public void mysqlBackupTask() {
        String resourcePath = "C:\\lancoo\\file\\mysqlBackup";
        log.info("======执行定时器:定时备份数据库=======");
        String backUpPath = resourcePath + "/sql/" + java.sql.Date.valueOf(LocalDate.now());
        File backUpFile = new File(backUpPath);
        if (!backUpFile.exists()) {
            backUpFile.mkdirs();
        }
        File dataFile = new File(backUpPath + "/yikatong" + System.currentTimeMillis() + ".sql");
        //拼接cmd命令
        StringBuffer sb = new StringBuffer();
        Map<String, String> dbInfo = jdbcUtils.getDBInfo();
        sb.append("mysqldump");
        sb.append(" -u" + dbInfo.get("userName"));
        sb.append(" -p" + dbInfo.get("passWord"));
        sb.append(" " + dbInfo.get("dbName") + " > ");
        sb.append(dataFile);
        log.info("======数据库备份cmd命令为：" + sb.toString() + "=======");
        try {
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
        String resourcePath = "C:\\lancoo\\file\\mysqlBackup";
        String backUpPath = resourcePath + "/sql";
        File backUpFile = new File(backUpPath);
        if (backUpFile.exists()) {
            File[] files = backUpFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    Date date1 = Date.valueOf(file.getName());
                    Date date2 = Date.valueOf(LocalDate.now());
                    long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
                    if (betweenDay > 7) {
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

```



```java
import com.lancoo.hydropower.quartz.task.MysqlBackupTask;
import com.lancoo.hydropower.quartz.task.RemoveMysqlBackupTask;
import com.lancoo.hydropower.service.IQuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MysqlBackupQuartzConfig {
    private static final String MYSQL_BACKUP_JOB_GROUP_NAME = "MYSQL_BACKUP_JOB";
    private static final String MYSQL_BACKUP_TRIGGER_GROUP_NAME = "MYSQL_BACKUP_TRIGGER";

    private static final String REMOVE_MYSQL_BACKUP_JOB_GROUP_NAME = "REMOVE_MYSQL_BACKUP_JOB";
    private static final String REMOVE_MYSQL_BACKUP_TRIGGER_GROUP_NAME = "REMOVE_MYSQL_BACKUP_TRIGGER";

    @Autowired
    private IQuartzService quartzService;

    @Bean
    public JobDetail mysqlBackupJobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("quartzService", quartzService);
        JobDetail jobDetail = JobBuilder.newJob(MysqlBackupTask.class)
                .withIdentity("mysqlBackupJobDetail", MYSQL_BACKUP_JOB_GROUP_NAME)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger mysqlBackupTriggerQuartz() {
    	//使用固定的Cron表达式（每天凌晨1点执行）
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
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
        jobDataMap.put("quartzService", quartzService);
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
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(removeMysqlBackupJobDetail()) //关联上Test述的JobDetail
                .withIdentity("removeMysqlBackupJobDetail", REMOVE_MYSQL_BACKUP_TRIGGER_GROUP_NAME) //给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }
}

```





任务表



定时任务表

