DROP DATABASE IF EXISTS lesson3;
CREATE DATABASE lesson3 DEFAULT CHARACTER SET utf8;
USE lesson3;

DROP TABLE IF EXISTS `t_user`;
SET NAMES utf8mb4;

CREATE TABLE `t_user` (
                          id INT PRIMARY KEY,
                          姓名 VARCHAR(255) NOT NULL COMMENT '用户名（不能重复）',
                          年龄 INT,
                          性别 VARCHAR(2)
);


INSERT INTO t_user VALUES (1, '刘丰洁', 21, '男');
INSERT INTO t_user VALUES (2, '任世兴', 18, '男');
INSERT INTO t_user VALUES (3, '杨俊', 22, '男');
INSERT INTO t_user VALUES (4, '任兴', 10, '女');
INSERT INTO t_user VALUES (5, '刘俊', 42, '女');

# select * from t_user;