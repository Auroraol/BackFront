CREATE DATABASE xdb;
USE xdb;
-- 1. 用户表
CREATE TABLE `x_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
   `deleted` INT(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('1','admin','123456','super@aliyun.com','18677778888','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');
insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('2','zhangsan','123456','zhangsan@gmail.com','13966667777','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');
insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('3','lisi','123456','lisi@gmail.com','13966667778','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');
insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('4','wangwu','123456','wangwu@gmail.com','13966667772','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');
insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('5','zhaoer','123456','zhaoer@gmail.com','13966667776','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');
insert into `x_user` (`id`, `username`, `password`, `email`, `phone`, `status`, `avatar`, `deleted`) values('6','songliu','123456','songliu@gmail.com','13966667771','1','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','0');


-- 2. 角色表
CREATE TABLE `x_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  `role_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

insert into `x_role` (`role_id`, `role_name`, `role_desc`) values('1','admin','超级管理员');
insert into `x_role` (`role_id`, `role_name`, `role_desc`) values('2','hr','人事专员');
insert into `x_role` (`role_id`, `role_name`, `role_desc`) values('3','normal','普通员工');

-- 3. 菜单表
CREATE TABLE `x_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `component` varchar(100) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `redirect` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `is_leaf` varchar(1) DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

insert  into `x_menu`(`menu_id`,`component`,`path`,`redirect`,`name`,`title`,`icon`,`parent_id`,`is_leaf`,`hidden`) values (1,'Layout','/user','/user/list','userManage','用户管理','userManage',0,'N',0),(2,'user/user','list',NULL,'userList','用户列表','userList',1,'Y',0),(3,'user/role','role',NULL,'roleList','角色列表','role',1,'Y',0),(4,'user/permission','permission',NULL,'permissionList','权限列表','permission',1,'Y',0);

-- 4. 用户角色映射表
CREATE TABLE `x_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

insert into `x_user_role` (`id`, `user_id`, `role_id`) values('1','1','1');

-- 5. 角色菜单映射表
CREATE TABLE `x_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
