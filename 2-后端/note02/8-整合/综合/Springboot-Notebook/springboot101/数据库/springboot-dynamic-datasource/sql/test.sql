-- 分别在两个数据库(mydruid和mydruid2)中创建一张相同的表
DROP TABLE if EXISTS t_dynamic_datasource_data;

CREATE TABLE IF NOT EXISTS `t_dynamic_datasource_data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `source_name` varchar(25) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ;

--在mydruid中插入如下数据
INSERT INTO t_dynamic_datasource_data (source_name) VALUE ('dynamic_datasource_master');
--在mydruid2中插入如下数据
INSERT INTO t_dynamic_datasource_data (source_name) VALUE ('dynamic_datasource_slave');