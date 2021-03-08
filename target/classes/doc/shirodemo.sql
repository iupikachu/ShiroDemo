/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : shirodemo

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 08/03/2021 15:07:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'select', '查看');
INSERT INTO `permission` VALUES (2, 'update', '更新');
INSERT INTO `permission` VALUES (3, 'delete', '删除');
INSERT INTO `permission` VALUES (4, 'save', '新增');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'svip');
INSERT INTO `role` VALUES (2, 'vip');
INSERT INTO `role` VALUES (3, 'p');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKf8yllw1ecvwqy3ehyxawqa1qp` (`permission_id`),
  CONSTRAINT `FKa6jx8n8xkesmjmv6jqug6bg68` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `FKf8yllw1ecvwqy3ehyxawqa1qp` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (2, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (2, 4);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL COMMENT '随机盐',
  `status` tinyint(1) DEFAULT NULL COMMENT '0账号正常 -1账号被锁',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'e0b811f5197789ab10d2ac7307266160', 'Jack', 'l^zYh!Qm', 0);
INSERT INTO `user` VALUES (2, '82bce14af6589e70ee2017ba7cfdcfdc', 'Rose', 'babFOSOb', 0);
INSERT INTO `user` VALUES (3, 'd7a86f725116d0bc1210a56988e6f097', 'Paul', 'nn#EYhS$', 0);
INSERT INTO `user` VALUES (5, '6d8e39dcf382315275112d30ee521ec3', 'cqp', '#tOxVx$a', 0);
INSERT INTO `user` VALUES (6, 'faff2fd6ada18f90e4a0344b2a48258c', 'iu', '_D&$Bra*', 0);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (5, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (5, 2);
INSERT INTO `user_role` VALUES (6, 2);
INSERT INTO `user_role` VALUES (3, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
