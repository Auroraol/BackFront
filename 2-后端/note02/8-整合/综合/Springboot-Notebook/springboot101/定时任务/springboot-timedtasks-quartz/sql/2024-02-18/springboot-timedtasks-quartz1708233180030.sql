-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: quartz
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêç',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '     Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='‰ª•Blob Á±ªÂûãÂ≠òÂÇ®ÁöÑËß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Êó•ÂéÜ‰ø°ÊÅØ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '     Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Êó∂Èó¥Ë°®ËææÂºè',
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Êó∂Âå∫ID     nvarchar     80',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='ÂÆöÊó∂Ëß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_TRIGGER','0 13 13 ? * *','Asia/Shanghai'),('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_TRIGGER_GROUP_NAME','0 31 21 ? * *','Asia/Shanghai'),('schedulerFactoryBean','oneTime10','remind','0 46 16 * * ?','GMT+08:00'),('schedulerFactoryBean','removeMysqlBackupJobDetail','REMOVE_MYSQL_BACKUP_TRIGGER','0 0 0 ? * *','Asia/Shanghai'),('schedulerFactoryBean','thireTime30','remind','0 47 16 * * ?','GMT+08:00'),('schedulerFactoryBean','twoTime20','remind','0 48 16 * * ?','GMT+08:00');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÁªÑÊ†áËØÜ',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÂΩìÂâçÂÆû‰æãÁöÑÂêçÁß∞',
  `FIRED_TIME` bigint NOT NULL COMMENT 'ÂΩìÂâçÊâßË°åÊó∂Èó¥',
  `SCHED_TIME` bigint NOT NULL COMMENT '     ËÆ°ÂàíÊó∂Èó¥',
  `PRIORITY` int NOT NULL COMMENT 'ÊùÉÈáç',
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Áä∂ÊÄÅ',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '‰Ωú‰∏öÂêçÁß∞',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '‰Ωú‰∏öÁªÑ',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ÊòØÂê¶Âπ∂Ë°å',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ÊòØÂê¶Ë¶ÅÊ±ÇÂî§ÈÜí',
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='‰øùÂ≠òÂ∑≤ÁªèËß¶ÂèëÁöÑËß¶ÂèëÂô®Áä∂ÊÄÅ‰ø°ÊÅØ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_fired_triggers` VALUES ('schedulerFactoryBean','NON_CLUSTERED1708233147557','mysqlBackupJobDetail','MYSQL_BACKUP_TRIGGER','NON_CLUSTERED',1708233180005,1708233180000,5,'EXECUTING','mysqlBackupJobDetail','MYSQL_BACKUP_JOB','0','0');
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '‰Ωú‰∏öÂêçÁß∞',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '‰Ωú‰∏öÁªÑ',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ÊèèËø∞',
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '‰Ωú‰∏öÁ®ãÂ∫èÁ±ªÂêç',
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÊòØÂê¶ÊåÅ‰πÖ',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÊòØÂê¶Âπ∂Ë°å',
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÊòØÂê¶Êõ¥Êñ∞',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÊòØÂê¶Ë¶ÅÊ±ÇÂî§ÈÜí',
  `JOB_DATA` blob COMMENT '‰Ωú‰∏öÂêçÁß∞',
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='job ËØ¶ÁªÜ‰ø°ÊÅØ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_JOB',NULL,'com.quartz.demo.quartz.job.MysqlBackupJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0quartzSqlServicesr\02com.quartz.demo.service.impl.IQuartzSqlServiceImplÑéPn\ﬁmΩ˝\0I\0dayL\0backupFolderPatht\0Ljava/lang/String;L\0dbNameq\0~\0	L\0passWordq\0~\0	L\0userNameq\0~\0	xp\0\0\0t\0¢C:\\\\Users\\\\16658\\\\Documents\\\\GitHub\\\\BackFront\\\\2-ÂêéÁ´Ø\\\\note02\\\\8-Êï¥Âêà\\\\ÁªºÂêà\\\\Springboot-Notebook\\\\springboot101\\\\ÂÆöÊó∂‰ªªÂä°\\\\springboot-timedtasks-quartzt\0quartzt\0741106t\0rootx\0'),('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_JOB_GROUP_NAME',NULL,'com.quartz.demo.task.MysqlBackupTask','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0quartzSqlServicesr\02com.quartz.demo.service.impl.IQuartzSqlServiceImpl\r,c\ÂMa2\0L\0backupFolderPatht\0Ljava/lang/String;L\0dbNameq\0~\0	L\0passWordq\0~\0	L\0userNameq\0~\0	xpt\0¢C:\\\\Users\\\\16658\\\\Documents\\\\GitHub\\\\BackFront\\\\2-ÂêéÁ´Ø\\\\note02\\\\8-Êï¥Âêà\\\\ÁªºÂêà\\\\Springboot-Notebook\\\\springboot101\\\\ÂÆöÊó∂‰ªªÂä°\\\\springboot-timedtasks-quartzt\0quartzt\0741106t\0rootx\0'),('schedulerFactoryBean','oneTime10','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0oneTime1x\0'),('schedulerFactoryBean','oneTime11','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0taskNamet\0oneTime1t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','oneTime12','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0	executiont\02024-02-15 00:45t\0taskNamet\0ÊâìÂç°t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','oneTime13','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0	executiont\02020-06-14 15:12t\0taskNamet\0‰∏äÁè≠t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','removeMysqlBackupJobDetail','REMOVE_MYSQL_BACKUP_JOB',NULL,'com.quartz.demo.quartz.job.RemoveMysqlBackupJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0quartzSqlServicesr\02com.quartz.demo.service.impl.IQuartzSqlServiceImplÑéPn\ﬁmΩ˝\0I\0dayL\0backupFolderPatht\0Ljava/lang/String;L\0dbNameq\0~\0	L\0passWordq\0~\0	L\0userNameq\0~\0	xp\0\0\0t\0¢C:\\\\Users\\\\16658\\\\Documents\\\\GitHub\\\\BackFront\\\\2-ÂêéÁ´Ø\\\\note02\\\\8-Êï¥Âêà\\\\ÁªºÂêà\\\\Springboot-Notebook\\\\springboot101\\\\ÂÆöÊó∂‰ªªÂä°\\\\springboot-timedtasks-quartzt\0quartzt\0741106t\0rootx\0'),('schedulerFactoryBean','third2','remind',NULL,'com.quartz.demo.task.RemindTask','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0	executiont\02024-02-17 14:39t\0taskNamet\0ÊâìÂç°t\0taskIdt\02x\0'),('schedulerFactoryBean','thireTime30','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0\nthireTime3x\0'),('schedulerFactoryBean','thireTime31','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0taskNamet\0\nthireTime3t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','thireTime32','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0opert\0\nthireTime3t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','twoTime20','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0twoTime2x\0'),('schedulerFactoryBean','twoTime21','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0taskNamet\0twoTime2t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','twoTime22','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0opert\0twoTime2t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','‰∏äÁè≠','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0taskNamet\0‰∏äÁè≠t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0'),('schedulerFactoryBean','ÊâìÂç°','remind',NULL,'com.quartz.demo.config.RemindJob','1','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0taskNamet\0ÊâìÂç°t\0taskIdsr\0java.lang.Integer‚†§\˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0x\0');
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÈîÅÂêçÁß∞',
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Â≠òÂÇ®Á®ãÂ∫èÁöÑÊÇ≤ËßÇÈîÅÁöÑ‰ø°ÊÅØ(ÂÅáÂ¶Ç‰ΩøÁî®‰∫ÜÊÇ≤ËßÇÈîÅ) ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Â≠òÊîæÊöÇÂÅúÊéâÁöÑËß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ÂÆû‰æãÂêçÁß∞',
  `LAST_CHECKIN_TIME` bigint NOT NULL COMMENT 'ÊúÄÂêéÁöÑÊ£ÄÊü•Êó∂Èó¥',
  `CHECKIN_INTERVAL` bigint NOT NULL COMMENT 'Ê£ÄÊü•Èó¥Èöî',
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Ë∞ÉÂ∫¶Âô®Áä∂ÊÄÅ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `REPEAT_COUNT` bigint NOT NULL COMMENT 'ÈáçÂ§çÊ¨°Êï∞',
  `REPEAT_INTERVAL` bigint NOT NULL COMMENT 'ÈáçÂ§çÈó¥Èöî',
  `TIMES_TRIGGERED` bigint NOT NULL COMMENT 'Ëß¶ÂèëÊ¨°Êï∞',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='ÁÆÄÂçïÁöÑËß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Â≠òÂÇ®CalendarIntervalTriggerÂíåDailyTimeIntervalTrigger‰∏§ÁßçÁ±ªÂûãÁöÑËß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ËÆ°ÂàíÂêçÁß∞',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÂêçÁß∞',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®ÁªÑ',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '‰Ωú‰∏öÂêçÁß∞',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '‰Ωú‰∏öÁªÑ',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ÊèèËø∞',
  `NEXT_FIRE_TIME` bigint DEFAULT NULL COMMENT '‰∏ãÊ¨°ÊâßË°åÊó∂Èó¥',
  `PREV_FIRE_TIME` bigint DEFAULT NULL COMMENT 'Ââç‰∏ÄÊ¨°',
  `PRIORITY` int DEFAULT NULL COMMENT '‰ºòÂÖàÊùÉ',
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®Áä∂ÊÄÅ',
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Ëß¶ÂèëÂô®Á±ªÂûã',
  `START_TIME` bigint NOT NULL COMMENT 'ÂºÄÂßãÊó∂Èó¥',
  `END_TIME` bigint DEFAULT NULL COMMENT 'ÁªìÊùüÊó∂Èó¥',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Êó•ÂéÜÂêçÁß∞',
  `MISFIRE_INSTR` smallint DEFAULT NULL COMMENT 'Â§±Ë¥•Ê¨°Êï∞',
  `JOB_DATA` blob COMMENT '‰Ωú‰∏öÊï∞ÊçÆ',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Ëß¶ÂèëÂô®';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_TRIGGER','mysqlBackupJobDetail','MYSQL_BACKUP_JOB',NULL,1708319580000,1708233180000,5,'WAITING','CRON',1708233147000,0,NULL,0,''),('schedulerFactoryBean','mysqlBackupJobDetail','MYSQL_BACKUP_TRIGGER_GROUP_NAME','mysqlBackupJobDetail','MYSQL_BACKUP_JOB_GROUP_NAME',NULL,1708263060000,1708176660000,5,'WAITING','CRON',1708176568000,0,NULL,0,''),('schedulerFactoryBean','oneTime10','remind','oneTime10','remind',NULL,1708073160000,1707986760000,5,'ERROR','CRON',1592037869000,0,NULL,0,_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0oneTime1x\0'),('schedulerFactoryBean','removeMysqlBackupJobDetail','REMOVE_MYSQL_BACKUP_TRIGGER','removeMysqlBackupJobDetail','REMOVE_MYSQL_BACKUP_JOB',NULL,1708272000000,-1,5,'WAITING','CRON',1708233147000,0,NULL,0,''),('schedulerFactoryBean','thireTime30','remind','thireTime30','remind',NULL,1708073220000,1707986820000,5,'ERROR','CRON',1592037869000,0,NULL,0,_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0\nthireTime3x\0'),('schedulerFactoryBean','twoTime20','remind','twoTime20','remind',NULL,1708073280000,1707986880000,5,'ERROR','CRON',1592037869000,0,NULL,0,_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0vint\00t\0opert\0twoTime2x\0');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task`
--

DROP TABLE IF EXISTS `t_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '‰∏ªÈîÆid',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '‰ªªÂä°ÂêçÁß∞',
  `create_time` datetime DEFAULT NULL COMMENT 'ÂàõÂª∫Êó∂Èó¥',
  `update_time` datetime DEFAULT NULL COMMENT '‰øÆÊîπÊó∂Èó¥',
  `delete_flag` int DEFAULT NULL COMMENT 'ÊòØÂê¶Âà†Èô§ 0:Âê¶  1:Âà†Èô§',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task`
--

LOCK TABLES `t_task` WRITE;
/*!40000 ALTER TABLE `t_task` DISABLE KEYS */;
INSERT INTO `t_task` VALUES (1,'ÈóπÈíü','2020-06-14 10:55:56',NULL,0),(2,'ÊâìÂç°','2020-06-14 10:56:20',NULL,0),(3,'‰∏äÁè≠','2020-06-14 10:56:34',NULL,0);
/*!40000 ALTER TABLE `t_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_timed_task`
--

DROP TABLE IF EXISTS `t_timed_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_timed_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_id` int NOT NULL COMMENT '‰ªªÂä°id',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '‰ªªÂä°ÂàÜÁªÑ',
  `execution_time` datetime DEFAULT NULL COMMENT 'ÊâßË°åÊó∂Èó¥',
  `cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ÊâßË°åÁöÑcron',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '‰ªªÂä°ËØ¥Êòé',
  `create_time` datetime DEFAULT NULL COMMENT 'ÂàõÂª∫Êó∂Èó¥',
  `update_time` datetime DEFAULT NULL COMMENT '‰øÆÊîπÊó∂Èó¥',
  `delete_flag` tinyint DEFAULT '0' COMMENT 'ÊòØÂê¶Âà†Èô§',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_timed_task`
--

LOCK TABLES `t_timed_task` WRITE;
/*!40000 ALTER TABLE `t_timed_task` DISABLE KEYS */;
INSERT INTO `t_timed_task` VALUES (24,2,'remind','2024-02-18 00:58:00','0 58 00 18 02 ? 2024','ÊâìÂç°','2024-02-17 13:37:43',NULL,0),(25,2,'remind','2024-02-17 14:39:00','0 39 14 17 02 ? 2024','ÊâìÂç°','2024-02-17 14:38:18',NULL,0);
/*!40000 ALTER TABLE `t_timed_task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-18 13:13:00
