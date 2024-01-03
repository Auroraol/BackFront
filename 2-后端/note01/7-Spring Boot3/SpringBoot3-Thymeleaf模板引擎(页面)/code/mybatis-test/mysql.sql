DROP DATABASE IF EXISTS mp;
CREATE DATABASE mp DEFAULT CHARACTER SET utf8;
USE mp;

DROP TABLE IF EXISTS `t_user`;
SET NAMES utf8mb4;

CREATE TABLE `t_user`
(
    `id`           BIGINT(0) NOT NULL AUTO_INCREMENT,
    `user_name`    VARCHAR(64) NOT NULL COMMENT '用户名（不能重复）',
    `nick_name`    VARCHAR(64) NULL COMMENT '昵称（可以重复）',
    `email`        VARCHAR(64) COMMENT '邮箱',
    `create_time`  DATETIME(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted_flag` BIGINT(0) NOT NULL DEFAULT 0 COMMENT '0：未删除 其他：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `index_user_name_deleted_flag` (`user_name`, `deleted_flag`),
    KEY `index_create_time`(`create_time`)
) ENGINE = InnoDB COMMENT = '用户';

INSERT INTO `t_user` VALUES (1, '刘丰洁', 'lfj', '1665834268@qq.com', NOW(), NOW(), 0);
INSERT INTO `t_user` VALUES (2, '任世兴', 'rsx', '123@qq.com', '2021-01-24 18:12:21', NOW(), 0);
INSERT INTO `t_user` VALUES (3, '杨骏', 'yj', '1235678@qq.com', '2021-01-24 18:12:21', NOW(), 0);

# select * from t_user;