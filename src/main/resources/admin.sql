/*
 Navicat MySQL Data Transfer

 Source Server         : msun_test
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 47.107.61.79:9527
 Source Schema         : beta_msun_wp_admin

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 26/08/2021 12:14:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限主键',
  `permission_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '允许放行的url',
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `gmt_modified_man` int(11) NULL DEFAULT NULL COMMENT '最后修改用户所对应的id',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_create_man` int(11) NULL DEFAULT NULL COMMENT '创建用户所对应的id',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除位',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '/', '所有', '2021-06-21 14:52:39', 1, '2021-06-21 14:52:39', 1, 0);
INSERT INTO `permission` VALUES (2, '/**', '基本管理', '2021-06-21 14:52:39', 1, '2021-06-21 14:52:39', 1, 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名（Sercurity框架配合）',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `role_nick_name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色昵称英文名',
  `role_nick_name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色昵称中文名',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `gmt_modified_man` int(11) NULL DEFAULT NULL COMMENT '最后修改用户所对应的id',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_create_man` int(11) NULL DEFAULT NULL COMMENT '创建用户所对应的id',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除位',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '管理整个系统', 'administrator', '管理员', '2021-08-23 17:18:41', 1, '2021-08-23 17:18:45', 1, 0);
INSERT INTO `role` VALUES (2, 'ROLE_TEST', '测试整个系统', 'tester', '测试一号', '2021-08-23 17:18:41', 1, '2021-08-23 17:18:45', 1, 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int(11) NOT NULL COMMENT 'position职位id',
  `permission_id` int(11) NOT NULL COMMENT 'permission权限id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `user_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `is_credentials_expired` tinyint(1) NULL DEFAULT NULL COMMENT '证书是否过期',
  `is_expired` tinyint(1) NULL DEFAULT NULL COMMENT '登录是否过期',
  `is_locked` tinyint(1) NULL DEFAULT NULL COMMENT '账户是否锁定',
  `gmt_last_login` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `gmt_modified_man` int(11) NULL DEFAULT NULL COMMENT '最后修改用户所对应的id',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_create_man` int(11) NULL DEFAULT NULL COMMENT '创建用户所对应的id',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除位',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '0136d6c720312b0b4c52daf18af4ba70', 0, 0, 0, '2021-06-17 10:16:09', '2021-06-17 10:16:09', 1, '2021-06-10 14:41:11', 1, 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);

SET FOREIGN_KEY_CHECKS = 1;
