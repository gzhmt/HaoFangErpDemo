/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : haofangerp

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 22/03/2020 20:14:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for production_employee_plan
-- ----------------------------
DROP TABLE IF EXISTS `production_employee_plan`;
CREATE TABLE `production_employee_plan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `employee_id` int(11) NOT NULL COMMENT '员工唯一标识',
  `plan_id` bigint(20) NOT NULL COMMENT '计划唯一标识',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_production_employee_system_employee`(`employee_id`) USING BTREE,
  INDEX `fk_production_employee_system_plan_id`(`plan_id`) USING BTREE,
  CONSTRAINT `fk_production_employee_system_employee` FOREIGN KEY (`employee_id`) REFERENCES `system_employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_production_employee_system_plan_id` FOREIGN KEY (`plan_id`) REFERENCES `production_plan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of production_employee_plan
-- ----------------------------

-- ----------------------------
-- Table structure for production_plan
-- ----------------------------
DROP TABLE IF EXISTS `production_plan`;
CREATE TABLE `production_plan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `production_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生产单号',
  `state` int(11) NOT NULL COMMENT '状态',
  `status` int(11) NOT NULL COMMENT '状况',
  `create_date` datetime(0) NOT NULL COMMENT '创建日期',
  `principal_employee_id` int(255) NOT NULL COMMENT '负责人',
  `emergency_level` int(11) NOT NULL COMMENT '紧急程度',
  `closing_date` datetime(0) NOT NULL COMMENT '截止日期',
  `source` int(11) NOT NULL COMMENT '来源',
  `linked_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联订单',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_production_plan_system_employee_id`(`principal_employee_id`) USING BTREE,
  CONSTRAINT `fk_production_plan_system_employee_id` FOREIGN KEY (`principal_employee_id`) REFERENCES `system_employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of production_plan
-- ----------------------------
INSERT INTO `production_plan` VALUES (36, 'string', 1, 1, '2020-03-20 20:21:11', 6, 1, '2020-03-20 12:19:09', 0, 'string', 'string');
INSERT INTO `production_plan` VALUES (37, 'string', 1, 1, '2020-03-20 21:47:00', 5, 1, '2020-03-20 13:45:47', 1, 'string', 'string');

-- ----------------------------
-- Table structure for production_plan_task
-- ----------------------------
DROP TABLE IF EXISTS `production_plan_task`;
CREATE TABLE `production_plan_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `plan_id` bigint(20) NOT NULL COMMENT '生产计划唯一标识',
  `task_id` bigint(20) NOT NULL COMMENT '生产任务唯一标识',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_production_plan_task_production_plan_id`(`plan_id`) USING BTREE,
  INDEX `fk_production_plan_task_production_task_id`(`task_id`) USING BTREE,
  CONSTRAINT `fk_production_plan_task_production_plan_id` FOREIGN KEY (`plan_id`) REFERENCES `production_plan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_production_plan_task_production_task_id` FOREIGN KEY (`task_id`) REFERENCES `production_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of production_plan_task
-- ----------------------------

-- ----------------------------
-- Table structure for production_task
-- ----------------------------
DROP TABLE IF EXISTS `production_task`;
CREATE TABLE `production_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `task_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务编号',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `production_date` datetime(0) NOT NULL COMMENT '生产时间',
  `operator_employee_id` int(255) NOT NULL COMMENT '操作人',
  `state` int(11) NOT NULL COMMENT '状态',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成品名称',
  `forecast_start_time` datetime(0) NOT NULL COMMENT '预计开始时间',
  `forecast_end_time` datetime(0) NOT NULL COMMENT '预计结束时间',
  `product_number` int(11) NOT NULL COMMENT '产品数量',
  `production_plan_id` bigint(20) NOT NULL COMMENT '生产计划id',
  `sequence_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流水号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_production_task_production_plan_id`(`production_plan_id`) USING BTREE,
  INDEX `fk_production_task_system_employee_id`(`operator_employee_id`) USING BTREE,
  CONSTRAINT `fk_production_task_production_plan_id` FOREIGN KEY (`production_plan_id`) REFERENCES `production_plan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_production_task_system_employee_id` FOREIGN KEY (`operator_employee_id`) REFERENCES `system_employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of production_task
-- ----------------------------

-- ----------------------------
-- Table structure for system_employee
-- ----------------------------
DROP TABLE IF EXISTS `system_employee`;
CREATE TABLE `system_employee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `employee_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `birth` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `entry_time` datetime(0) NOT NULL COMMENT '入职时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_employee
-- ----------------------------
INSERT INTO `system_employee` VALUES (5, 'admin', '$2a$10$02VLbIimtktH4xc.kBxWA.z3ffeoecD9u6jTBEnCNSUKtzMoEcX3m', 'admin', '1999-04-22 00:00:00', '2020-03-20 00:00:00');
INSERT INTO `system_employee` VALUES (6, 'normal', '$2a$10$IARPeJMKj56vYNT1q6GtneXuwllsyUlBD6zxcrBleuO/agES.2vhC', '普通工人', '2020-03-20 00:00:00', '2020-03-20 00:00:00');
INSERT INTO `system_employee` VALUES (7, 'YG-2019001', '$2a$10$BzkE9j60Icv5uWCnhOGO0OLJAZr4nndGv53x9E3xcO0Ezr2HeSlge', '张三', '1999-04-23 00:00:00', '2020-03-21 00:00:00');

-- ----------------------------
-- Table structure for system_employee_role
-- ----------------------------
DROP TABLE IF EXISTS `system_employee_role`;
CREATE TABLE `system_employee_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `employee_id` int(11) NOT NULL COMMENT '工人id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_system_employee_role_system_employee_id`(`employee_id`) USING BTREE,
  INDEX `fk_system_employee_role_system_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `fk_system_employee_role_system_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `system_employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_system_employee_role_system_role_id` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_employee_role
-- ----------------------------
INSERT INTO `system_employee_role` VALUES (1, 1, 5);
INSERT INTO `system_employee_role` VALUES (2, 2, 6);
INSERT INTO `system_employee_role` VALUES (3, 2, 7);

-- ----------------------------
-- Table structure for system_power
-- ----------------------------
DROP TABLE IF EXISTS `system_power`;
CREATE TABLE `system_power`  (
  `id` int(11) NOT NULL COMMENT '主键',
  `api_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'API名称',
  `api_url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'API请求地址',
  `api_method` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'API请求方式：GET、POST、PUT、DELETE',
  `api_sort` int(11) NOT NULL COMMENT '排序',
  `pid` int(11) NULL DEFAULT 0 COMMENT '父ID',
  `description` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_power
-- ----------------------------
INSERT INTO `system_power` VALUES (1, '所有API', '/**', 'GET,POST,PUT,DELETE', 100, 0, '所有API');
INSERT INTO `system_power` VALUES (2, '查询计划', '/plan/list/?', 'GET', 200, 0, '查看计划列表');
INSERT INTO `system_power` VALUES (3, '查询任务', '/plan/list/?', 'GET', 200, 0, '查看任务列表');
INSERT INTO `system_power` VALUES (4, '更新计划', '/plan/update', 'POST', 200, 0, '更新计划');
INSERT INTO `system_power` VALUES (5, '删除计划', '/plan/delete', 'POST', 200, 0, '删除计划');
INSERT INTO `system_power` VALUES (6, '创建计划', '/plan/create', 'POST', 200, 0, '创建计划');
INSERT INTO `system_power` VALUES (7, '更新任务', '/task/update', 'POST', 200, 0, '更新任务');
INSERT INTO `system_power` VALUES (8, '删除任务', '/task/delete', 'POST', 200, 0, '删除任务');
INSERT INTO `system_power` VALUES (9, '创建任务', '/task/create', 'POST', 200, 0, '创建任务');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, 'ROLE_ADMIN', '超级管理员');
INSERT INTO `system_role` VALUES (2, 'ROLE_NORMAL', '工人');

-- ----------------------------
-- Table structure for system_role_power
-- ----------------------------
DROP TABLE IF EXISTS `system_role_power`;
CREATE TABLE `system_role_power`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `power_id` int(11) NOT NULL COMMENT '权限id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_system_role_power_system_role_id`(`role_id`) USING BTREE,
  INDEX `fk_system_role_power_system_power_id`(`power_id`) USING BTREE,
  CONSTRAINT `fk_system_role_power_system_power_id` FOREIGN KEY (`power_id`) REFERENCES `system_power` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_system_role_power_system_role_id` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_power
-- ----------------------------
INSERT INTO `system_role_power` VALUES (1, 1, 1);
INSERT INTO `system_role_power` VALUES (2, 2, 2);
INSERT INTO `system_role_power` VALUES (3, 3, 2);

SET FOREIGN_KEY_CHECKS = 1;
