DROP DATABASE IF EXISTS `lesson4`;

CREATE DATABASE  `lesson4` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `lesson4`;

CREATE TABLE `user` (
                        id INT PRIMARY KEY,
                        name VARCHAR(255),
                        password VARCHAR(255),
                        role VARCHAR(255)
);

CREATE TABLE `diary` (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         title VARCHAR(255),
                         date DATE,
                         content TEXT,
                         name VARCHAR(255)
);

-- 插入用户数据
INSERT INTO `user`
VALUES
    (1665834268, '刘丰洁', '741106', 'admin'),
    (1,'任世兴', '123', 'user'),
    (2,'杨俊', '123', 'user');

-- 插入日记数据
INSERT INTO `diary` (title, date, content, name) VALUES
     ('我的第一篇文章', '2024-01-10', '这是我第一篇日记的内容。', '刘丰洁'),
     ('难忘 Day', '2024-01-15', '今天是值得纪念的一天', '任世兴'),
     ('回旋曲', '2024-01-20', '是否似懂非懂', '杨俊');

# --  测试
# SELECT * FROM user;
# SELECT * FROM diary;
# select name,password from user where id = 1665834268;