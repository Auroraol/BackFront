-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: fileserver
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_file_meta`
--

DROP TABLE IF EXISTS `tbl_file_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_file_meta` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sha1` char(40) NOT NULL DEFAULT '' COMMENT '文件hash',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '文件名称',
  `size` bigint(20) DEFAULT '0' COMMENT '文件大小',
  `addr` varchar(1024) NOT NULL DEFAULT '' COMMENT '文件存储地址',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `ext1` int(11) DEFAULT '0' COMMENT '备用字段1',
  `ext2` text COMMENT '备用字段2',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 1=可用，2=禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sha1_UNIQUE` (`sha1`),
  KEY `index3` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='文件元数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_file_meta`
--

LOCK TABLES `tbl_file_meta` WRITE;
/*!40000 ALTER TABLE `tbl_file_meta` DISABLE KEYS */;
INSERT INTO `tbl_file_meta` VALUES (1,'6e99b447950dbad20208cbc61f49ea7b9cd1dd82','input.txt',10,'static/upload/2020-02-09/6e99b447950dbad20208cbc61f49ea7b9cd1dd82.txt','2020-02-09 20:47:23','2020-02-09 20:47:23',NULL,0,NULL,1),(2,'d7e0b1ba57bbc508cd387a5225a9de7b58cded81','input.txt',14,'static/upload/2020-02-09/d7e0b1ba57bbc508cd387a5225a9de7b58cded81.txt','2020-02-09 20:49:30','2020-02-09 20:49:30',NULL,0,NULL,1),(7,'c200525cd1314657c57269470fc9a3e8d8fb723b','input.txt',17,'static/upload/2020-02-27/c200525cd1314657c57269470fc9a3e8d8fb723b.txt','2020-02-27 07:41:02','2020-02-27 07:41:02',NULL,0,NULL,1),(8,'f20bb3e915e3d52ceb3a78afe56fd92003116ce3','input.txt',23,'static/upload/2020-02-27/f20bb3e915e3d52ceb3a78afe56fd92003116ce3.txt','2020-02-27 07:43:09','2020-02-27 07:43:09',NULL,0,NULL,1),(9,'2582f7577ef4b4a361b51e312794f562fd3d96e4','input.txt',27,'static/upload/2020-02-27/2582f7577ef4b4a361b51e312794f562fd3d96e4.txt','2020-02-27 07:46:56','2020-02-27 07:46:56',NULL,0,NULL,1),(10,'2dc40059bd8a8a8066fe58287ff6e5ec1ea6595c','input.txt',33,'static/upload/2020-02-27/2dc40059bd8a8a8066fe58287ff6e5ec1ea6595c.txt','2020-02-27 07:53:05','2020-02-27 07:53:05',NULL,0,NULL,1),(11,'072e2ba28360c675bf94ced2b8b2a4a1068fc742','input.txt',37,'static/upload/2020-02-27/072e2ba28360c675bf94ced2b8b2a4a1068fc742.txt','2020-02-27 07:58:05','2020-02-27 07:58:05',NULL,0,NULL,1),(16,'9798f7bb36b780585966e7d0938a7eb954d77f15','go1.13.8.linux-amd64.tar.gz',120075156,'static/upload/2020-03-03/9798f7bb36b780585966e7d0938a7eb954d77f15.gz','2020-03-03 03:24:15','2020-03-03 03:24:15',NULL,0,NULL,1);
/*!40000 ALTER TABLE `tbl_file_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(255) NOT NULL DEFAULT '' COMMENT 'slat',
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '用户名称',
  `mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `enable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态 1=启用，2=禁用',
  `is_super` tinyint(4) DEFAULT '0' COMMENT '超级用户标识 1=超级用户',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `memo` text COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index3` (`user_name`,`deleted_at`),
  KEY `index2` (`deleted_at`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'chenyu','8323465f5e59c4c8e576b3455e393186','KGh9uZvH','','','test1@qq.com',0,0,'2020-02-09 16:19:04','2020-02-19 17:46:46',NULL,''),(2,'test','aedfc81167baaf8e96f3ca5074f02994','V2EXXO2u','','','test@qq.com',0,0,'2020-02-09 17:56:16','2020-02-19 13:45:37','2020-02-19 13:45:37',''),(8,'test','f70bbfca5d08f2c2cbcb3f6200f3d2d3','4iVzHZpq','','','',0,0,'2020-02-19 17:47:07','2020-02-19 17:47:07',NULL,'');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_file`
--

DROP TABLE IF EXISTS `tbl_user_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `file_sha1` varchar(64) NOT NULL DEFAULT '' COMMENT '文件hash',
  `file_size` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件大小 ，单位bit',
  `file_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户定义的文件名称',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '1=正常，2=禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index4` (`user_id`,`file_sha1`,`deleted_at`),
  KEY `index2` (`user_id`),
  KEY `index3` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户文件中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_file`
--

LOCK TABLES `tbl_user_file` WRITE;
/*!40000 ALTER TABLE `tbl_user_file` DISABLE KEYS */;
INSERT INTO `tbl_user_file` VALUES (2,1,'d7e0b1ba57bbc508cd387a5225a9de7b58cded81',14,'test.txt','2020-02-20 17:14:21','2020-02-20 17:25:06',NULL,1),(10,1,'6e99b447950dbad20208cbc61f49ea7b9cd1dd82',10,'adfaf1','2020-02-25 09:03:55','2020-02-25 09:03:55',NULL,1),(13,1,'c200525cd1314657c57269470fc9a3e8d8fb723b',17,'input.txt','2020-02-27 07:41:03','2020-02-27 07:41:03',NULL,1),(14,1,'f20bb3e915e3d52ceb3a78afe56fd92003116ce3',23,'input.txt','2020-02-27 07:43:10','2020-02-27 07:43:10',NULL,1),(15,1,'2582f7577ef4b4a361b51e312794f562fd3d96e4',27,'input.txt','2020-02-27 07:46:56','2020-02-27 07:46:56',NULL,1),(16,1,'2dc40059bd8a8a8066fe58287ff6e5ec1ea6595c',33,'input.txt','2020-02-27 07:53:06','2020-02-27 07:53:06',NULL,1),(17,1,'072e2ba28360c675bf94ced2b8b2a4a1068fc742',37,'input.txt','2020-02-27 07:58:05','2020-02-27 07:58:05',NULL,1),(21,1,'9798f7bb36b780585966e7d0938a7eb954d77f15',120075156,'go1.13.8.linux-amd64.tar.gz','2020-03-03 03:24:16','2020-03-03 03:24:16',NULL,1);
/*!40000 ALTER TABLE `tbl_user_file` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-03 13:48:06
