-- 使用数据库
CREATE datebase springcloud;

USE springcloud;

-- 用户表
CREATE TABLE `tb_user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(40) DEFAULT NULL COMMENT '用户名',
`password` varchar(40) DEFAULT NULL COMMENT '密码',
`age` int(3) DEFAULT NULL COMMENT '年龄',
`balance` decimal(10,2) DEFAULT NULL COMMENT '余额',
`address` varchar(80) DEFAULT NULL COMMENT '地址',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `tb_product` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`product_name` varchar(40) DEFAULT NULL COMMENT '名称',
`status` int(2) DEFAULT NULL COMMENT '状态',
`price` decimal(10,2) DEFAULT NULL COMMENT '单价',
`product_desc` varchar(255) DEFAULT NULL COMMENT '描述',
`caption` varchar(255) DEFAULT NULL COMMENT '标题',
`inventory` int(11) DEFAULT NULL COMMENT '库存',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

CREATE TABLE `tb_order` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) DEFAULT NULL COMMENT '用户id',
`product_id` int(11) DEFAULT NULL COMMENT '商品id',
`number` int(11) DEFAULT NULL COMMENT '数量',
`price` decimal(10,2) DEFAULT NULL COMMENT '单价',
`amount` decimal(10,2) DEFAULT NULL COMMENT '总额',
`product_name` varchar(40) DEFAULT NULL COMMENT '商品名',
`username` varchar(40) DEFAULT NULL COMMENT '用户名',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
