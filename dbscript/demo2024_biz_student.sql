/*
 Navicat Premium Data Transfer

 Source Server         : Demo2024
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : 192.168.50.240:3306
 Source Schema         : demo2024_biz_student

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 31/10/2024 13:55:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint NOT NULL COMMENT '主键',
  `true_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像地址',
  `identity_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证',
  `age` int DEFAULT NULL COMMENT '年龄',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生表';

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` bigint NOT NULL COMMENT '主键',
  `student_id` bigint DEFAULT NULL COMMENT '学生id',
  `registration_id` bigint DEFAULT NULL COMMENT '报名资格id',
  `study_plan_id` bigint DEFAULT NULL COMMENT '学习计划课程id',
  `course_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程名字',
  `course_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程缩略图',
  `unit` decimal(32,8) DEFAULT NULL COMMENT '学分',
  `study_plan_course_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程类别',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识(0未删除，1删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生课程';

-- ----------------------------
-- Table structure for student_registration
-- ----------------------------
DROP TABLE IF EXISTS `student_registration`;
CREATE TABLE `student_registration` (
  `id` bigint NOT NULL COMMENT '主键',
  `student_id` bigint DEFAULT NULL COMMENT '学生id',
  `identity_code` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证',
  `registration_num` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '报名序列号',
  `year` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '年度',
  `used` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否使用',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识(0未删除，1删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生报名资格';

SET FOREIGN_KEY_CHECKS = 1;
