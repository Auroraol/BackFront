/*
 Navicat Premium Data Transfer


 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 30/03/2024 09:08:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commonfile
-- ----------------------------
DROP TABLE IF EXISTS `commonfile`;
CREATE TABLE `commonfile`  (
  `commonFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户文件id',
  PRIMARY KEY (`commonFileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commonfile
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `fileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户id',
  `fileSize` bigint(10) NULL DEFAULT NULL COMMENT '文件大小',
  `fileStatus` int(1) NULL DEFAULT NULL COMMENT '文件状态(0-失效，1-生效)',
  `fileUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件url',
  `identifier` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5唯一标识',
  `modifyTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户id',
  `storageType` int(1) NULL DEFAULT NULL COMMENT '存储类型',
  PRIMARY KEY (`fileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fileclassification
-- ----------------------------
INSERT INTO `fileclassification` VALUES (1, 'bmp', 1);
INSERT INTO `fileclassification` VALUES (2, 'jpg', 1);
INSERT INTO `fileclassification` VALUES (3, 'png', 1);
INSERT INTO `fileclassification` VALUES (4, 'tif', 1);
INSERT INTO `fileclassification` VALUES (5, 'gif', 1);
INSERT INTO `fileclassification` VALUES (6, 'jpeg', 1);
INSERT INTO `fileclassification` VALUES (7, 'doc', 2);
INSERT INTO `fileclassification` VALUES (8, 'docx', 2);
INSERT INTO `fileclassification` VALUES (9, 'docm', 2);
INSERT INTO `fileclassification` VALUES (10, 'dot', 2);
INSERT INTO `fileclassification` VALUES (11, 'dotx', 2);
INSERT INTO `fileclassification` VALUES (12, 'dotm', 2);
INSERT INTO `fileclassification` VALUES (13, 'odt', 2);
INSERT INTO `fileclassification` VALUES (14, 'fodt', 2);
INSERT INTO `fileclassification` VALUES (15, 'ott', 2);
INSERT INTO `fileclassification` VALUES (40, 'ppt', 2);
INSERT INTO `fileclassification` VALUES (41, 'pptx', 2);
INSERT INTO `fileclassification` VALUES (42, 'pptm', 2);
INSERT INTO `fileclassification` VALUES (43, 'pot', 2);
INSERT INTO `fileclassification` VALUES (44, 'potx', 2);
INSERT INTO `fileclassification` VALUES (45, 'potm', 2);
INSERT INTO `fileclassification` VALUES (46, 'odp', 2);
INSERT INTO `fileclassification` VALUES (47, 'fodp', 2);
INSERT INTO `fileclassification` VALUES (48, 'otp', 2);
INSERT INTO `fileclassification` VALUES (49, 'hlp', 2);
INSERT INTO `fileclassification` VALUES (50, 'wps', 2);
INSERT INTO `fileclassification` VALUES (51, 'java', 2);
INSERT INTO `fileclassification` VALUES (52, 'js', 2);
INSERT INTO `fileclassification` VALUES (53, 'css', 2);
INSERT INTO `fileclassification` VALUES (54, 'json', 2);
INSERT INTO `fileclassification` VALUES (55, 'avi', 3);
INSERT INTO `fileclassification` VALUES (56, 'mp4', 3);
INSERT INTO `fileclassification` VALUES (57, 'mpg', 3);
INSERT INTO `fileclassification` VALUES (58, 'mov', 3);
INSERT INTO `fileclassification` VALUES (59, 'swf', 3);
INSERT INTO `fileclassification` VALUES (60, 'wav', 4);
INSERT INTO `fileclassification` VALUES (61, 'aif', 4);
INSERT INTO `fileclassification` VALUES (62, 'au', 4);
INSERT INTO `fileclassification` VALUES (63, 'mp3', 4);
INSERT INTO `fileclassification` VALUES (64, 'ram', 4);
INSERT INTO `fileclassification` VALUES (65, 'wma', 4);
INSERT INTO `fileclassification` VALUES (66, 'mmf', 4);
INSERT INTO `fileclassification` VALUES (67, 'amr', 4);
INSERT INTO `fileclassification` VALUES (68, 'aac', 4);
INSERT INTO `fileclassification` VALUES (69, 'flac', 4);
INSERT INTO `fileclassification` VALUES (70, 'md', 2);
INSERT INTO `fileclassification` VALUES (71, 'markdown', 2);

-- ----------------------------
-- Table structure for fileextend
-- ----------------------------
DROP TABLE IF EXISTS `fileextend`;
CREATE TABLE `fileextend`  (
  `fileExtendName` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fileExtendDesc` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件扩展名描述',
  `fileExtendImgUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件扩展名预览图',
  PRIMARY KEY (`fileExtendName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fileextend
-- ----------------------------
INSERT INTO `fileextend` VALUES ('aac', NULL, NULL);
INSERT INTO `fileextend` VALUES ('aif', NULL, NULL);
INSERT INTO `fileextend` VALUES ('amr', NULL, NULL);
INSERT INTO `fileextend` VALUES ('au', NULL, NULL);
INSERT INTO `fileextend` VALUES ('avi', NULL, NULL);
INSERT INTO `fileextend` VALUES ('bmp', NULL, NULL);
INSERT INTO `fileextend` VALUES ('css', NULL, NULL);
INSERT INTO `fileextend` VALUES ('csv', NULL, NULL);
INSERT INTO `fileextend` VALUES ('djvu', NULL, NULL);
INSERT INTO `fileextend` VALUES ('doc', NULL, NULL);
INSERT INTO `fileextend` VALUES ('docm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('docx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('dot', NULL, NULL);
INSERT INTO `fileextend` VALUES ('dotm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('dotx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('epub', NULL, NULL);
INSERT INTO `fileextend` VALUES ('fb2', NULL, NULL);
INSERT INTO `fileextend` VALUES ('flac', NULL, NULL);
INSERT INTO `fileextend` VALUES ('fodp', NULL, NULL);
INSERT INTO `fileextend` VALUES ('fods', NULL, NULL);
INSERT INTO `fileextend` VALUES ('fodt', NULL, NULL);
INSERT INTO `fileextend` VALUES ('gif', NULL, NULL);
INSERT INTO `fileextend` VALUES ('hlp', NULL, NULL);
INSERT INTO `fileextend` VALUES ('htm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('html', NULL, NULL);
INSERT INTO `fileextend` VALUES ('java', NULL, NULL);
INSERT INTO `fileextend` VALUES ('ppsx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('ppt', NULL, NULL);
INSERT INTO `fileextend` VALUES ('pptm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('pptx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('ram', NULL, NULL);
INSERT INTO `fileextend` VALUES ('rtf', NULL, NULL);
INSERT INTO `fileextend` VALUES ('swf', NULL, NULL);
INSERT INTO `fileextend` VALUES ('tif', NULL, NULL);
INSERT INTO `fileextend` VALUES ('txt', NULL, NULL);
INSERT INTO `fileextend` VALUES ('wav', NULL, NULL);
INSERT INTO `fileextend` VALUES ('wma', NULL, NULL);
INSERT INTO `fileextend` VALUES ('wps', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xls', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xlsm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xlsx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xlt', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xltm', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xltx', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xml', NULL, NULL);
INSERT INTO `fileextend` VALUES ('xps', NULL, NULL);

-- ----------------------------
-- Table structure for filepermission
-- ----------------------------
DROP TABLE IF EXISTS `filepermission`;
CREATE TABLE `filepermission`  (
  `filePermissionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `commonFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '共享文件id',
  `filePermissionCode` int(2) NULL DEFAULT NULL COMMENT '用户对文件的权限码',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`filePermissionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of filepermission
-- ----------------------------

-- ----------------------------
-- Table structure for filetype
-- ----------------------------
DROP TABLE IF EXISTS `filetype`;
CREATE TABLE `filetype`  (
  `fileTypeId` int(11) NOT NULL,
  `fileTypeName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型名',
  `orderNum` int(2) NULL DEFAULT NULL COMMENT '次序',
  PRIMARY KEY (`fileTypeId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of filetype
-- ----------------------------
INSERT INTO `filetype` VALUES (0, '全部', NULL);
INSERT INTO `filetype` VALUES (1, '图片', NULL);
INSERT INTO `filetype` VALUES (2, '文档', NULL);
INSERT INTO `filetype` VALUES (3, '视频', NULL);
INSERT INTO `filetype` VALUES (4, '音乐', NULL);
INSERT INTO `filetype` VALUES (5, '其他', NULL);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `imageId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件id',
  `imageHeight` int(5) NULL DEFAULT NULL COMMENT '图像的高',
  `imageWidth` int(5) NULL DEFAULT NULL COMMENT '图像的宽',
  PRIMARY KEY (`imageId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (1, '1751943149752217600', 344, 500);
INSERT INTO `image` VALUES (2, '1751943609796063232', 511, 799);
INSERT INTO `image` VALUES (3, '1767607954999672832', 1080, 1080);
INSERT INTO `image` VALUES (4, '1767608058770948096', 200, 200);

-- ----------------------------
-- Table structure for music
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE `music`  (
  `musicId` bigint(20) NOT NULL AUTO_INCREMENT,
  `album` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `albumArtist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `albumImage` mediumblob NULL,
  `artist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `composer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `encoder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fileId` bigint(20) NULL DEFAULT NULL COMMENT '文件id',
  `genre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lyrics` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '歌词',
  `originalArtist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `publicer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `track` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `trackLength` float NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`musicId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of music
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `noticeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'html内容',
  `createTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `isLongValidData` int(1) NULL DEFAULT NULL COMMENT '是否长期有效(0-否,1-是)',
  `markdownContent` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'markdown原文',
  `modifyTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `platform` int(2) NULL DEFAULT NULL COMMENT '平台(1-社区,2-管理端,3-网盘,4-股票)',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `validDateTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有效时间',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for operationlog
-- ----------------------------
DROP TABLE IF EXISTS `operationlog`;
CREATE TABLE `operationlog`  (
  `operationLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作详情',
  `logLevel` int(11) NULL DEFAULT NULL,
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作',
  `operationObj` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `platform` int(2) NULL DEFAULT NULL COMMENT '平台(1-社区,2-管理端,3-网盘,4-股票)',
  `requestMethod` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `requestURI` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作结果',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `terminal` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终端ip地址',
  `time` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作时间',
  `userId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`operationLogId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operationlog
-- ----------------------------
INSERT INTO `operationlog` VALUES (1, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-01-28 21:07:06', '1');
INSERT INTO `operationlog` VALUES (2, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-01-29 20:19:24', '1');
INSERT INTO `operationlog` VALUES (3, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 20:19:30', '1');
INSERT INTO `operationlog` VALUES (4, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 20:19:31', '1');
INSERT INTO `operationlog` VALUES (5, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 20:21:20', '1');
INSERT INTO `operationlog` VALUES (6, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 20:21:20', '1');
INSERT INTO `operationlog` VALUES (7, '操作成功', NULL, '分享文件', NULL, 3, 'POST', '/share/sharefile', '成功', '文件分享', '127.0.0.1', '2024-01-29 20:25:52', '1');
INSERT INTO `operationlog` VALUES (8, '操作成功', NULL, '分享文件', NULL, 3, 'POST', '/share/sharefile', '成功', '文件分享', '127.0.0.1', '2024-01-29 20:26:08', '1');
INSERT INTO `operationlog` VALUES (9, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-01-29 20:29:51', '1');
INSERT INTO `operationlog` VALUES (10, '操作成功', NULL, '用户注册', NULL, 3, 'POST', '/user/register', '成功', '用户管理', '127.0.0.1', '2024-01-29 20:47:39', '');
INSERT INTO `operationlog` VALUES (11, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-01-29 20:47:45', '1751950231649972224');
INSERT INTO `operationlog` VALUES (12, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-01-29 21:03:29', '1');
INSERT INTO `operationlog` VALUES (13, '操作成功', NULL, '创建文件夹', NULL, 3, 'POST', '/file/createFold', '成功', '文件接口', '127.0.0.1', '2024-01-29 21:05:16', '1');
INSERT INTO `operationlog` VALUES (14, '操作成功', NULL, '文件移动', NULL, 3, 'POST', '/file/movefile', '成功', '文件接口', '127.0.0.1', '2024-01-29 21:05:48', '1');
INSERT INTO `operationlog` VALUES (15, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 21:06:24', '1');
INSERT INTO `operationlog` VALUES (16, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 21:06:25', '1');
INSERT INTO `operationlog` VALUES (17, '操作成功', NULL, '批量删除文件', NULL, 3, 'POST', '/file/batchdeletefile', '成功', '文件接口', '127.0.0.1', '2024-01-29 21:07:21', '1');
INSERT INTO `operationlog` VALUES (18, '操作成功', NULL, '批量删除回收文件', NULL, 3, 'POST', '/recoveryfile/batchdelete', '成功', '回收站文件接口', '127.0.0.1', '2024-01-29 21:07:29', '1');
INSERT INTO `operationlog` VALUES (19, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 21:10:15', '1');
INSERT INTO `operationlog` VALUES (20, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-01-29 21:10:15', '1');
INSERT INTO `operationlog` VALUES (21, '操作成功', NULL, '解压文件', NULL, 3, 'POST', '/file/unzipfile', '成功', '文件接口', '127.0.0.1', '2024-01-29 21:10:25', '1');
INSERT INTO `operationlog` VALUES (22, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-03-13 00:11:06', '1');
INSERT INTO `operationlog` VALUES (23, '操作成功', NULL, '批量删除文件', NULL, 3, 'POST', '/file/batchdeletefile', '成功', '文件接口', '127.0.0.1', '2024-03-13 00:11:15', '1');
INSERT INTO `operationlog` VALUES (24, '操作成功', NULL, '批量删除回收文件', NULL, 3, 'POST', '/recoveryfile/batchdelete', '成功', '回收站文件接口', '127.0.0.1', '2024-03-13 00:11:20', '1');
INSERT INTO `operationlog` VALUES (25, '操作成功', NULL, '用户登录', NULL, 3, 'GET', '/user/login', '成功', '用户管理', '127.0.0.1', '2024-03-13 01:45:02', '1');
INSERT INTO `operationlog` VALUES (26, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-03-13 01:45:50', '1');
INSERT INTO `operationlog` VALUES (27, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-03-13 01:45:51', '1');
INSERT INTO `operationlog` VALUES (28, '操作成功', NULL, '批量删除文件', NULL, 3, 'POST', '/file/batchdeletefile', '成功', '文件接口', '127.0.0.1', '2024-03-13 01:45:58', '1');
INSERT INTO `operationlog` VALUES (29, '操作成功', NULL, '极速上传', NULL, 3, 'GET', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-03-13 01:46:16', '1');
INSERT INTO `operationlog` VALUES (30, '操作成功', NULL, '上传文件', NULL, 3, 'POST', '/filetransfer/uploadfile', '成功', '文件传输接口', '127.0.0.1', '2024-03-13 01:46:16', '1');
INSERT INTO `operationlog` VALUES (31, '操作成功', NULL, '分享文件', NULL, 3, 'POST', '/share/sharefile', '成功', '文件分享', '127.0.0.1', '2024-03-13 01:46:52', '1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permissionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `modifyTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `orderNum` int(2) NULL DEFAULT NULL COMMENT '次序',
  `parentId` bigint(20) NULL DEFAULT NULL COMMENT '父编号',
  `permissionCode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识码',
  `permissionName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `resourceType` int(2) NULL DEFAULT NULL COMMENT '资源类型',
  PRIMARY KEY (`permissionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for picturefile
-- ----------------------------
DROP TABLE IF EXISTS `picturefile`;
CREATE TABLE `picturefile`  (
  `pictureFileId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `extendName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展名',
  `fileName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `fileSize` bigint(10) NULL DEFAULT NULL COMMENT '文件大小',
  `fileUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件url',
  `modifyTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `storageType` int(1) NULL DEFAULT NULL COMMENT '存储类型',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`pictureFileId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of picturefile
-- ----------------------------

-- ----------------------------
-- Table structure for recoveryfile
-- ----------------------------
DROP TABLE IF EXISTS `recoveryfile`;
CREATE TABLE `recoveryfile`  (
  `recoveryFileId` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleteBatchNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除批次号',
  `deleteTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除时间',
  `userFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户文件id',
  PRIMARY KEY (`recoveryFileId`) USING BTREE,
  UNIQUE INDEX `user_file_id_index3`(`userFileId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recoveryfile
-- ----------------------------
INSERT INTO `recoveryfile` VALUES (5, '2903dcdd-f650-4c62-a0f3-675ab082e6dd', '2024-03-13 01:45:58', '1767607955016450048');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` int(2) NULL DEFAULT NULL COMMENT '是否可用(0-不可用,1-可用)',
  `createTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `modifyTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `roleName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 1, NULL, NULL, '超级管理员', '2021-11-10 20:46:06', NULL, '超级管理员');
INSERT INTO `role` VALUES (2, 1, NULL, NULL, '普通用户', NULL, NULL, '普通用户');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permissionId` bigint(20) NULL DEFAULT NULL COMMENT '权限id',
  `roleId` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `shareId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `endTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失效时间',
  `extractionCode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提取码',
  `shareBatchNum` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分享批次号',
  `shareStatus` int(2) NULL DEFAULT NULL COMMENT '分享状态(0正常,1已失效,2已撤销)',
  `shareTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分享时间',
  `shareType` int(2) NULL DEFAULT NULL COMMENT '分享类型(0公共,1私密,2好友)',
  `userId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`shareId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share
-- ----------------------------
INSERT INTO `share` VALUES ('1767608210021744640', '2024-03-08 00:00:00', NULL, '1500ed9389bc4666895591b7c05714ca', 0, '2024-03-13 01:46:52', 0, '1');

-- ----------------------------
-- Table structure for sharefile
-- ----------------------------
DROP TABLE IF EXISTS `sharefile`;
CREATE TABLE `sharefile`  (
  `shareFileId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `shareBatchNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分享批次号',
  `shareFilePath` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分享文件路径',
  `userFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户文件id',
  PRIMARY KEY (`shareFileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sharefile
-- ----------------------------
INSERT INTO `sharefile` VALUES ('1767608210042716160', '1500ed9389bc4666895591b7c05714ca', '/', '1767608058775142400');

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`  (
  `storageId` bigint(20) NOT NULL AUTO_INCREMENT,
  `modifyTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `storageSize` bigint(20) NULL DEFAULT NULL COMMENT '占用存储大小',
  `totalStorageSize` bigint(20) NULL DEFAULT NULL COMMENT '总存储大小',
  `userId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`storageId`) USING BTREE,
  UNIQUE INDEX `userid_index`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `sysparam` VALUES (3, NULL, '当前脚本的版本号', 'version', '1');

-- ----------------------------
-- Table structure for uploadtask
-- ----------------------------
DROP TABLE IF EXISTS `uploadtask`;
CREATE TABLE `uploadtask`  (
  `uploadTaskId` bigint(20) NOT NULL AUTO_INCREMENT,
  `extendName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展名',
  `fileName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `filePath` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `identifier` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5唯一标识',
  `uploadStatus` int(1) NULL DEFAULT NULL COMMENT '上传状态(1-成功,0-失败或未完成)',
  `uploadTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传时间',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`uploadTaskId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uploadtask
-- ----------------------------
INSERT INTO `uploadtask` VALUES (1, 'jpg', 'avatar', '/', '9d3189a2cc664d50ee1364f842c79678', 1, '2024-01-29 20:19:30', 1);
INSERT INTO `uploadtask` VALUES (2, 'png', 'qiwenshare_1706530877588', '/', '4c02a3aa5bef330cfc3b7f7341c85eb4', 1, '2024-01-29 20:21:20', 1);
INSERT INTO `uploadtask` VALUES (3, 'mp4', 'QQ短视频20220508110827', '/', '1af657125d76ef6c81244b812159ea64', 1, '2024-01-29 21:06:24', 1);
INSERT INTO `uploadtask` VALUES (4, 'zip', 'config', '/', '4a0818a80803ebc5e48c3457b34e1a16', 1, '2024-01-29 21:10:15', 1);
INSERT INTO `uploadtask` VALUES (5, 'jpg', 'avatar2', '/', '5f3a3bee0a7c74ddf5d25115d9f82176', 1, '2024-03-13 01:45:50', 1);
INSERT INTO `uploadtask` VALUES (6, 'jpg', 'avatar3', '/', '469abce4bcd0d8727aae1ef2259495f4', 1, '2024-03-13 01:46:16', 1);

-- ----------------------------
-- Table structure for uploadtaskdetail
-- ----------------------------
DROP TABLE IF EXISTS `uploadtaskdetail`;
CREATE TABLE `uploadtaskdetail`  (
  `uploadTaskDetailId` bigint(20) NOT NULL AUTO_INCREMENT,
  `chunkNumber` int(5) NULL DEFAULT NULL COMMENT '当前分片数',
  `chunkSize` bigint(10) NULL DEFAULT NULL COMMENT '当前分片大小',
  `filePath` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `filename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `identifier` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件md5唯一标识',
  `relativePath` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件相对路径',
  `totalChunks` int(5) NULL DEFAULT NULL COMMENT '文件总分片数',
  `totalSize` bigint(10) NULL DEFAULT NULL COMMENT '文件总大小',
  PRIMARY KEY (`uploadTaskDetailId`) USING BTREE

  `birthday` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生日',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `imageUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `industry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业',
  `intro` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `lastLoginTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录时间',
  `modifyTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `password` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
  `registerTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册时间',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `sex` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `telephone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `wxOpenId` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'open id',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'df655ad8d3229f3269fad2a8bab59b6c', NULL, NULL, 'admin', NULL, 'admin', 'admin', NULL);
INSERT INTO `user` VALUES ('1751950231649972224', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '65e1ad2781ceaee205e1cba0ad1b6652', NULL, '2024-01-29 20:47:39', '5459268283566161', NULL, '13852366325', '张三', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `userRoleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NULL DEFAULT NULL,
  `userId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userRoleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, '1');
INSERT INTO `user_role` VALUES (2, 2, '1751950231649972224');

-- ----------------------------
-- Table structure for userfile
-- ----------------------------
DROP TABLE IF EXISTS `userfile`;
CREATE TABLE `userfile`  (
  `userFileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `createUserId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户id',
  `deleteBatchNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除批次号',
  `deleteFlag` int(11) NULL DEFAULT NULL COMMENT '删除标识(0-未删除，1-已删除)',
  `deleteTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除时间',
  `extendName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '扩展名',
  `fileId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件id',
  `fileName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `filePath` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `isDir` int(1) NULL DEFAULT NULL COMMENT '是否是目录(0-否,1-是)',
  `modifyTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `modifyUserId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户id',
  `uploadTime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`userFileId`) USING BTREE,
  UNIQUE INDEX `fileindex`(`userId`, `filePath`, `fileName`, `extendName`, `deleteFlag`, `isDir`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userfile
-- ----------------------------
INSERT INTO `userfile` VALUES ('1767607955016450048', '2024-03-13 01:45:51', '1', '2903dcdd-f650-4c62-a0f3-
CREATE TABLE `userlogininfo`  (
  `userLoginId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `userloginDate` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户登录日期',
  PRIMARY KEY (`userLoginId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userlogininfo
-- ----------------------------
INSERT INTO `userlogininfo` VALUES (13, '1', '2024-01-28 21:07:31');
INSERT INTO `userlogininfo` VALUES (53, '1751950231649972224', '2024-01-29 21:03:08');
INSERT INTO `userlogininfo` VALUES (106, '1', '2024-01-29 21:17:32');
INSERT INTO `userlogininfo` VALUES (126, '1', '2024-03-13 01:46:57');

SET FOREIGN_KEY_CHECKS = 1;
