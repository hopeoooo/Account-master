/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.65
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 192.168.1.65:3306
 Source Schema         : account

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 30/05/2022 14:14:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_access_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_access_code`;
CREATE TABLE `sys_access_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `chip_balance` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '已存筹码余额-美金',
  `cash_balance` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '已存现金余额-美金',
  `chip_balance_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '已存筹码余额-泰铢',
  `cash_balance_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '已存现金余额-泰铢',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存取码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_access_code
-- ----------------------------

-- ----------------------------
-- Table structure for sys_access_code_detailed
-- ----------------------------
DROP TABLE IF EXISTS `sys_access_code_detailed`;
CREATE TABLE `sys_access_code_detailed`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '操作类型: 1:存码 2:取码',
  `chip_amount_before` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动前金额-美金',
  `chip_amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动金额-美金',
  `chip_amount_after` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动后金额-美金',
  `cash_amount_before` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动前金额-美金',
  `cash_amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动金额-美金',
  `cash_amount_after` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动后金额-美金',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `chip_amount_before_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动前金额-泰铢',
  `chip_amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动金额-泰铢',
  `chip_amount_after_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码变动后金额-泰铢',
  `cash_amount_before_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动前金额-泰铢',
  `cash_amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动金额-泰铢',
  `cash_amount_after_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金变动后金额-泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存取明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_access_code_detailed
-- ----------------------------

-- ----------------------------
-- Table structure for sys_bet
-- ----------------------------
DROP TABLE IF EXISTS `sys_bet`;
CREATE TABLE `sys_bet`  (
  `bet_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '注单号',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `table_id` int(11) NULL DEFAULT NULL COMMENT '台号',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记',
  `boot_num` int(11) NULL DEFAULT NULL COMMENT '靴号',
  `game_num` int(11) NULL DEFAULT NULL COMMENT '局数',
  `type` tinyint(2) NULL DEFAULT 0 COMMENT '投注类型 0筹码美元 1现金美元 2筹码泰铢 3现金泰铢',
  `game_id` tinyint(2) NULL DEFAULT NULL COMMENT '游戏类型',
  `game_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开奖结果',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`bet_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '注单录入表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_bet
-- ----------------------------

-- ----------------------------
-- Table structure for sys_bet_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_bet_info`;
CREATE TABLE `sys_bet_info`  (
  `bet_id` bigint(20) NULL DEFAULT NULL COMMENT '注单号',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记',
  `table_id` int(11) NULL DEFAULT NULL COMMENT '台号',
  `boot_num` int(11) NULL DEFAULT NULL COMMENT '靴号',
  `game_num` int(11) NULL DEFAULT NULL COMMENT '局数',
  `type` tinyint(2) NULL DEFAULT 0 COMMENT '投注类型 0筹码美元 1现金美元 2筹码泰铢 3现金泰铢 4 保险美元 5保险泰铢',
  `game_id` tinyint(2) NULL DEFAULT NULL COMMENT '游戏类型',
  `bet_option` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投注选项',
  `bet_money` decimal(15, 4) NULL DEFAULT NULL COMMENT '投注金额',
  `game_result` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开奖结果',
  `win_lose` decimal(15, 4) NULL DEFAULT NULL COMMENT '玩家输赢',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `water` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码量',
  `water_amount` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码金额',
  `tie` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '和钱',
  `pump` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '抽水',
  `dealer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '荷官工号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '注单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_bet_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_bet_update_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_bet_update_record`;
CREATE TABLE `sys_bet_update_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `game_id` int(11) NULL DEFAULT NULL COMMENT '游戏id',
  `card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `table_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '台号',
  `boot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '靴号',
  `game` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '局号',
  `option` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下注玩法',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '币种',
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下注金额',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开牌结果',
  `win` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输赢',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `bet_time` datetime NULL DEFAULT NULL COMMENT '下注时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最近修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '注单修改记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_bet_update_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_chip_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_chip_record`;
CREATE TABLE `sys_chip_record`  (
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '变动类型(1:存码,2:取码,3:下注赢,4:下注输,5:签单,6:还单,7:换现,13:买码,8:注单修改,9:注单补录,10:洗码结算,11:汇入,12:汇出)',
  `before` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码变动前数量',
  `change` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '变动数量',
  `after` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码变动后数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `bet_id` bigint(20) NULL DEFAULT NULL COMMENT '注单号',
  `before_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码变动前 泰铢',
  `change_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '变动值 泰铢',
  `after_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '变动后 泰铢',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '筹码变动记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_chip_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dealer
-- ----------------------------
DROP TABLE IF EXISTS `sys_dealer`;
CREATE TABLE `sys_dealer`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phonenumber` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '用户性别（0男 1女 2未知）',
  `post` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `join_time` datetime NULL DEFAULT NULL COMMENT '入职日期',
  `brithday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生年份',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dealer
-- ----------------------------

-- ----------------------------
-- Table structure for sys_game_result
-- ----------------------------
DROP TABLE IF EXISTS `sys_game_result`;
CREATE TABLE `sys_game_result`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `game_id` int(11) NULL DEFAULT NULL COMMENT '游戏类型',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记',
  `table_id` int(11) NULL DEFAULT NULL COMMENT '台号',
  `boot_num` int(11) NULL DEFAULT NULL COMMENT '靴号',
  `game_num` int(11) NULL DEFAULT NULL COMMENT '局数',
  `game_result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '赛果',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '游戏赛果表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_game_result
-- ----------------------------

-- ----------------------------
-- Table structure for sys_game_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_game_type`;
CREATE TABLE `sys_game_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(20) NOT NULL COMMENT '游戏id',
  `game_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '游戏名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '游戏类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_game_type
-- ----------------------------
INSERT INTO `sys_game_type` VALUES (1, 1, '百家乐');
INSERT INTO `sys_game_type` VALUES (2, 2, '龙虎');
INSERT INTO `sys_game_type` VALUES (3, 3, '牛牛');
INSERT INTO `sys_game_type` VALUES (4, 4, '三公');
INSERT INTO `sys_game_type` VALUES (5, 5, '推筒子');

-- ----------------------------
-- Table structure for sys_members
-- ----------------------------
DROP TABLE IF EXISTS `sys_members`;
CREATE TABLE `sys_members`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主卡号',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '状态 0正常 1停用',
  `card_type` tinyint(2) NULL DEFAULT 0 COMMENT '卡类型 0主卡 1字卡',
  `is_admin` tinyint(2) NULL DEFAULT 0 COMMENT '是否内部账号 0否 1是',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '性别 0男 1女 2未知',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户人',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开户时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deposit` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '押金',
  `repair` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '补卡费',
  `share_ratio` int(11) NULL DEFAULT 0 COMMENT '占股比例',
  `rebate_ratio` int(11) NULL DEFAULT 0 COMMENT '返点比例',
  `is_pump` tinyint(2) NULL DEFAULT 1 COMMENT '是否抽水 0否 1是',
  `is_cash` tinyint(2) UNSIGNED NULL DEFAULT 1 COMMENT '是否换现  0否 1是',
  `is_settlement` tinyint(2) UNSIGNED NULL DEFAULT 1 COMMENT '是否可结算洗码 0否 1是',
  `is_out` tinyint(2) NULL DEFAULT 1 COMMENT '是否可汇出 0否 1是',
  `is_bill` tinyint(2) NULL DEFAULT 1 COMMENT '是否走账 0否 1是',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `baccarat_rolling_ratio_chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(筹码)',
  `baccarat_rolling_ratio_cash` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(现金)',
  `dragon_tiger_ratio_chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(筹码)',
  `dragon_tiger_ratio_cash` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(现金)',
  `chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '用户筹码余额',
  `chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码余额 泰铢',
  `baccarat_rolling_ratio_chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(筹码) 泰铢',
  `baccarat_rolling_ratio_cash_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(现金) 泰铢',
  `dragon_tiger_ratio_chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(筹码) 泰铢',
  `dragon_tiger_ratio_cash_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(现金) 泰铢',
  `deposit_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '押金 泰铢',
  `repair_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '补卡费 泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_members
-- ----------------------------

-- ----------------------------
-- Table structure for sys_members_water
-- ----------------------------
DROP TABLE IF EXISTS `sys_members_water`;
CREATE TABLE `sys_members_water`  (
  `card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员卡号',
  `water` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码量',
  `water_amount` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '未结算洗码费',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `water_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码量 泰铢',
  `water_amount_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '未结算洗码费 泰铢',
  PRIMARY KEY (`card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员 洗码数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_members_water
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称(英文)',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '账号管理', 'Acc Management', 0, 1, 'front/account', NULL, '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 13:01:55', '');
INSERT INTO `sys_menu` VALUES (2, '注单管理', 'Bet Slip Management', 0, 2, 'front/bet', NULL, '', 1, 0, 'M', '0', '0', '', 'build', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:46:36', '');
INSERT INTO `sys_menu` VALUES (3, '报表统计', 'Stat', 0, 3, 'front/report', NULL, '', 1, 0, 'M', '0', '0', '', 'chart', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:46:56', '');
INSERT INTO `sys_menu` VALUES (4, '码房管理', 'Cage Management', 0, 4, 'front/coderoom', NULL, '', 1, 0, 'M', '0', '0', '', 'money', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:47:08', '');
INSERT INTO `sys_menu` VALUES (5, '系统设置', 'System Settings', 0, 5, 'front/sys', NULL, NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2022-04-19 19:59:05', 'admin', '2022-04-20 17:32:08', '');
INSERT INTO `sys_menu` VALUES (100, '会员管理', 'Member Management', 1, 1, 'member', 'front/account/member/index', '', 1, 0, 'C', '0', '0', 'account:mambers:list', 'user', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:55:51', '');
INSERT INTO `sys_menu` VALUES (101, '员工管理', 'Staff Management', 1, 2, 'employee', 'front/account/employee/index', '', 1, 0, 'C', '0', '0', 'account:user:list', 'user', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:58:41', '');
INSERT INTO `sys_menu` VALUES (102, '角色管理', 'Role Management', 1, 3, 'role', 'front/account/role/index', '', 1, 0, 'C', '0', '0', 'account:role:list', 'people', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:59:14', '');
INSERT INTO `sys_menu` VALUES (103, '荷官管理', 'Dealer Management', 1, 4, 'dealer', 'front/account/dealer/index', NULL, 1, 0, 'C', '0', '0', 'account:dealer:list', 'user', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (200, '百家乐', 'Baccarat', 2, 1, 'baccarat', 'front/bet/baccarat/index', '', 1, 0, 'C', '0', '0', 'bet:baccarat:list', 'online', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (201, '龙虎', 'DT', 2, 2, 'draganTiger', 'front/bet/draganTiger/index', '', 1, 0, 'C', '0', '0', 'bet:dragontiger:list', 'job', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (202, '牛牛', 'Niu Niu', 2, 3, 'niuniu', 'front/bet/niuniu/index', '', 1, 0, 'C', '0', '0', 'bet:niuniu:list', 'druid', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (203, '三公', 'San Gong', 2, 4, 'sangong', 'front/bet/sangong/index', '', 1, 0, 'C', '0', '0', 'bet:sangong:list', 'server', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (204, '推筒子', 'Tui Tong Zi', 2, 5, 'pusher', 'front/bet/pusher/index', '', 1, 0, 'C', '0', '0', 'bet:pusher:list', 'redis', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (205, '百家乐投屏', 'Baccarat screen synchronisation', 2, 6, 'screencastbaccarat', 'front/screencast/baccarat/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (206, '龙虎投屏', 'Dragon Tiger screen synchronisation', 2, 7, 'screencastdraganTiger', 'front/screencast/draganTiger/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (207, '牛牛投屏', 'Niu Niu screen synchronisation', 2, 8, 'screencastniuniu', 'front/screencast/niuniu/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (208, '三公投屏', 'San Gong screen synchronisation', 2, 9, 'screencastsangong', 'front/screencast/sangong/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (209, '推筒子投屏', 'Tui Tong Zi screen synchronisation\r\n\r\n', 2, 10, 'screencastpusher', 'front/screencast/pusher/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (300, '注单记录', 'Bet Slip Records', 3, 1, 'bet', 'front/report/bet/index', '', 1, 0, 'C', '0', '0', 'bet:record:list', 'code', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (301, '注单修改记录', 'Bet Slip modification records', 3, 2, 'betResive', 'front/report/betResive/index', '', 1, 0, 'C', '0', '0', 'bet:update:list', 'swagger', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (302, '输赢报表', 'Win/Loss Reports', 3, 3, 'winOrLose', 'front/report/winOrLose/index', '', 1, 0, 'C', '0', '0', 'sys:winLose:list', 'form', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (303, '点码报表', 'Point chip records', 3, 4, 'pointCode', 'front/report/pointCode/index', '', 1, 0, 'C', '0', '0', 'sys:porint:list', 'logininfor', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (304, '点码修改记录', 'Point chip modification records', 3, 5, 'pointUpdate', 'front/report/pointUpdate/index', NULL, 1, 0, 'C', '0', '0', 'porint:update:list', 'cascader', 'admin', '2022-04-25 14:39:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (305, '收码报表', 'Receipt Report', 3, 6, 'receipt', 'front/report/receipt/index', NULL, 1, 0, 'C', '0', '0', 'sys:receipt:list', 'eye', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (306, '客户日报表', 'Cust Daily Report', 3, 7, 'memberDay', 'front/report/memberDay/index', NULL, 1, 0, 'C', '0', '0', 'sys:report:list', 'clipboard', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (307, '台面上下水报表', 'Table commissioning Report', 3, 8, 'tableWater', 'front/report/tableWater/index', NULL, 1, 0, 'C', '0', '0', 'sys:tablePlumbing:list', 'component', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (308, '员工录入错帐报表', 'Staff Entry Error Report', 3, 9, 'error', 'front/report/error/index', NULL, 1, 0, 'C', '0', '0', 'sys:inputError:list', 'edit', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (309, '洗码费结算明细表', 'Rolling fee settlement statement', 3, 10, 'waterInfo', 'front/report/waterInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:waterDetailed:list', 'dashboard', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (310, '存取码明细表', 'Deposit and Withdrawal Code Schedule', 3, 11, 'depositInfo', 'front/report/depositInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:accessCodeDetailed:list', 'eye-open', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (311, '签单明细表', 'Sign-up Sheet', 3, 12, 'signInfo', 'front/report/signInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:signedDetailed:list', 'button', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (312, '客户筹码变动明细表', 'Customer Chip Movement Statement', 3, 13, 'cashInfo', 'front/report/cashInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:chipRecord:list', 'date-range', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (313, '汇款明细表', 'Remittance Schedule', 3, 14, 'transferInfo', 'front/report/transferInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:remittanceDetailed:list', 'code', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (314, '荷官上下水', 'Dealer commissioning Report', 3, 15, 'DealerWater', 'front/report/dealerWater/index', NULL, 1, 0, 'C', '0', '0', 'sys:dealer:list', 'code', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (400, '洗码费结算', 'Rolling fees settlement list', 4, 1, 'washCode', 'front/codeRoom/washCode/index', NULL, 1, 0, 'C', '0', '0', 'system:water:list', 'post', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:33', '');
INSERT INTO `sys_menu` VALUES (401, '存取码', 'Deposit and Withdrawal of Chips Management List', 4, 2, 'accessCode', 'front/codeRoom/accessCode/index', NULL, 1, 0, 'C', '0', '0', 'system:accessCode:list', 'edit', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:41', '');
INSERT INTO `sys_menu` VALUES (402, '签单', 'Sign Up List', 4, 3, 'sign', 'front/codeRoom/sign/index', NULL, 1, 0, 'C', '0', '0', 'system:signed:list', 'icon', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:49', '');
INSERT INTO `sys_menu` VALUES (403, '买码换现', 'Buy chip for cash list', 4, 4, 'cashToCode', 'front/codeRoom/cashToCode/index', NULL, 1, 0, 'C', '0', '0', 'system:businessCashChip:list', 'date', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:56', '');
INSERT INTO `sys_menu` VALUES (404, '汇款', 'Remittance details list', 4, 5, 'transfer', 'front/codeRoom/transfer/index', NULL, 1, 0, 'C', '0', '0', 'system:remittance:list', 'component', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:49:02', '');
INSERT INTO `sys_menu` VALUES (500, '赔率设置', 'Odds Settings', 5, 1, 'odds', 'front/sys/odds/index', NULL, 1, 0, 'C', '0', '0', 'system:oddsConfig:list', 'bug', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:33:08', '');
INSERT INTO `sys_menu` VALUES (501, '桌台管理', 'Table Settings', 5, 2, 'table', 'front/sys/table/index', NULL, 1, 0, 'C', '0', '0', 'system:table:list', 'education', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:34:00', '');
INSERT INTO `sys_menu` VALUES (502, '修改密码', 'Change Password', 5, 3, 'pass', 'front/sys/pass/index', NULL, 1, 0, 'C', '0', '0', 'system:user:profile', 'lock', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:34:46', '');
INSERT INTO `sys_menu` VALUES (204003, '首页', 'Home', 0, 0, '/', NULL, NULL, 1, 0, 'M', '0', '0', NULL, '#', '', NULL, '', NULL, '');

-- ----------------------------
-- Table structure for sys_menu_copy1
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_copy1`;
CREATE TABLE `sys_menu_copy1`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称(英文)',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_copy1
-- ----------------------------
INSERT INTO `sys_menu_copy1` VALUES (1, '账号管理', '', 0, 1, 'front/account', NULL, '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 13:01:55', '');
INSERT INTO `sys_menu_copy1` VALUES (2, '注单管理', '', 0, 2, 'front/bet', NULL, '', 1, 0, 'M', '0', '0', '', 'build', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:46:36', '');
INSERT INTO `sys_menu_copy1` VALUES (3, '报表统计', '', 0, 3, 'front/report', NULL, '', 1, 0, 'M', '0', '0', '', 'chart', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:46:56', '');
INSERT INTO `sys_menu_copy1` VALUES (4, '码房管理', '', 0, 4, 'front/coderoom', NULL, '', 1, 0, 'M', '0', '0', '', 'money', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:47:08', '');
INSERT INTO `sys_menu_copy1` VALUES (5, '系统设置', '', 0, 5, 'front/sys', NULL, NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2022-04-19 19:59:05', 'admin', '2022-04-20 17:32:08', '');
INSERT INTO `sys_menu_copy1` VALUES (100, '会员管理', '', 1, 1, 'member', 'front/account/member/index', '', 1, 0, 'C', '0', '0', 'account:mambers:list', 'user', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:55:51', '');
INSERT INTO `sys_menu_copy1` VALUES (101, '员工管理', '', 1, 2, 'employee', 'front/account/employee/index', '', 1, 0, 'C', '0', '0', 'account:user:list', 'user', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:58:41', '');
INSERT INTO `sys_menu_copy1` VALUES (102, '角色管理', '', 1, 3, 'role', 'front/account/role/index', '', 1, 0, 'C', '0', '0', 'account:role:list', 'people', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 14:59:14', '');
INSERT INTO `sys_menu_copy1` VALUES (103, '荷官管理', '', 1, 4, 'dealer', 'front/account/dealer/index', NULL, 1, 0, 'C', '0', '0', 'account:dealer:list', 'user', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (200, '百家乐', '', 2, 1, 'baccarat', 'front/bet/baccarat/index', '', 1, 0, 'C', '0', '0', 'bet:baccarat:list', 'online', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (201, '龙虎', '', 2, 2, 'draganTiger', 'front/bet/draganTiger/index', '', 1, 0, 'C', '0', '0', 'bet:dragontiger:list', 'job', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (202, '牛牛', '', 2, 3, 'niuniu', 'front/bet/niuniu/index', '', 1, 0, 'C', '0', '0', 'bet:niuniu:list', 'druid', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (203, '三公', '', 2, 4, 'sangong', 'front/bet/sangong/index', '', 1, 0, 'C', '0', '0', 'bet:sangong:list', 'server', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (204, '推筒子', '', 2, 5, 'pusher', 'front/bet/pusher/index', '', 1, 0, 'C', '0', '0', 'bet:pusher:list', 'redis', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (205, '百家乐投屏', '', 2, 6, 'screencastbaccarat', 'front/screencast/baccarat/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (206, '龙虎投屏', '', 2, 7, 'screencastdraganTiger', 'front/screencast/draganTiger/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (207, '牛牛投屏', '', 2, 8, 'screencastniuniu', 'front/screencast/niuniu/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (208, '三公投屏', '', 2, 9, 'screencastsangong', 'front/screencast/sangong/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (209, '推筒子投屏', '', 2, 10, 'screencastpusher', 'front/screencast/pusher/show', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (300, '注单记录', '', 3, 1, 'bet', 'front/report/bet/index', '', 1, 0, 'C', '0', '0', 'bet:record:list', 'code', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (301, '注单修改记录', '', 3, 2, 'betResive', 'front/report/betResive/index', '', 1, 0, 'C', '0', '0', 'bet:update:list', 'swagger', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (302, '输赢报表', '', 3, 3, 'winOrLose', 'front/report/winOrLose/index', '', 1, 0, 'C', '0', '0', 'sys:winLose:list', 'form', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (303, '点码报表', '', 3, 4, 'pointCode', 'front/report/pointCode/index', '', 1, 0, 'C', '0', '0', 'sys:porint:list', 'logininfor', 'admin', '2022-04-19 06:24:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (304, '点码修改记录', '', 3, 5, 'pointUpdate', 'front/report/pointUpdate/index', NULL, 1, 0, 'C', '0', '0', 'porint:update:list', 'cascader', 'admin', '2022-04-25 14:39:57', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (305, '收码报表', '', 3, 6, 'receipt', 'front/report/receipt/index', NULL, 1, 0, 'C', '0', '0', 'sys:receipt:list', 'eye', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (306, '客户日报表', '', 3, 7, 'memberDay', 'front/report/memberDay/index', NULL, 1, 0, 'C', '0', '0', 'sys:report:list', 'clipboard', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (307, '台面上下水报表', '', 3, 8, 'tableWater', 'front/report/tableWater/index', NULL, 1, 0, 'C', '0', '0', 'sys:tablePlumbing:list', 'component', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (308, '员工录入错帐报表', '', 3, 9, 'error', 'front/report/error/index', NULL, 1, 0, 'C', '0', '0', 'sys:inputError:list', 'edit', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (309, '洗码费结算明细表', '', 3, 10, 'waterInfo', 'front/report/waterInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:waterDetailed:list', 'dashboard', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (310, '存取码明细表', '', 3, 11, 'depositInfo', 'front/report/depositInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:accessCodeDetailed:list', 'eye-open', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (311, '签单明细表', '', 3, 12, 'signInfo', 'front/report/signInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:signedDetailed:list', 'button', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (312, '客户筹码变动明细表', '', 3, 13, 'cashInfo', 'front/report/cashInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:chipRecord:list', 'date-range', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (313, '汇款明细表', '', 3, 14, 'transferInfo', 'front/report/transferInfo/index', NULL, 1, 0, 'C', '0', '0', 'system:remittanceDetailed:list', 'code', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (314, '荷官上下水', '', 3, 15, 'DealerWater', 'front/report/dealerWater/index', NULL, 1, 0, 'C', '0', '0', 'sys:dealer:list', 'code', 'admin', '2022-04-25 14:40:05', '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (400, '洗码费结算', '', 4, 1, 'washCode', 'front/codeRoom/washCode/index', NULL, 1, 0, 'C', '0', '0', 'system:water:list', 'post', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:33', '');
INSERT INTO `sys_menu_copy1` VALUES (401, '存取码', '', 4, 2, 'accessCode', 'front/codeRoom/accessCode/index', NULL, 1, 0, 'C', '0', '0', 'system:accessCode:list', 'edit', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:41', '');
INSERT INTO `sys_menu_copy1` VALUES (402, '签单', '', 4, 3, 'sign', 'front/codeRoom/sign/index', NULL, 1, 0, 'C', '0', '0', 'system:signed:list', 'icon', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:49', '');
INSERT INTO `sys_menu_copy1` VALUES (403, '买码换现', '', 4, 4, 'cashToCode', 'front/codeRoom/cashToCode/index', NULL, 1, 0, 'C', '0', '0', 'system:businessCashChip:list', 'date', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:48:56', '');
INSERT INTO `sys_menu_copy1` VALUES (404, '汇款', '', 4, 5, 'transfer', 'front/codeRoom/transfer/index', NULL, 1, 0, 'C', '0', '0', 'system:remittance:list', 'component', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-19 12:49:02', '');
INSERT INTO `sys_menu_copy1` VALUES (500, '赔率设置', '', 5, 1, 'odds', 'front/sys/odds/index', NULL, 1, 0, 'C', '0', '0', 'system:oddsConfig:list', 'bug', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:33:08', '');
INSERT INTO `sys_menu_copy1` VALUES (501, '桌台管理', '', 5, 2, 'table', 'front/sys/table/index', NULL, 1, 0, 'C', '0', '0', 'system:table:list', 'education', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:34:00', '');
INSERT INTO `sys_menu_copy1` VALUES (502, '修改密码', '', 5, 3, 'pass', 'front/sys/pass/index', NULL, 1, 0, 'C', '0', '0', 'system:user:profile', 'lock', 'admin', '2022-04-19 06:24:05', 'admin', '2022-04-20 17:34:46', '');
INSERT INTO `sys_menu_copy1` VALUES (200001, '百家乐', '', 200, 1, '', 'front/bet/baccarat/index', NULL, 1, 0, 'F', '0', '0', 'bet:baccarat:list', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (200002, '修改路珠', '', 200, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'baccarat:result:update', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (200003, '点码||收码', '', 200, 3, '', '', NULL, 1, 0, 'F', '0', '0', 'bet:baccarat:edit', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (201001, '龙虎', '', 201, 1, '', 'front/bet/draganTiger/index', NULL, 1, 0, 'F', '0', '0', 'bet:dragontiger:list', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (201002, '修改赛果', '', 201, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'dragontiger:result:update', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (201003, '点码||收码', '', 201, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'bet:dragontiger:edit', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (202001, '牛牛', '', 202, 1, '', 'front/bet/niuniu/index', NULL, 1, 0, 'F', '0', '0', 'bet:niuniu:list', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (202002, '点码||收码', '', 202, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'bet:niuniu:edit', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (203001, '三公', '', 203, 1, '', 'front/bet/sangong/index', NULL, 1, 0, 'F', '0', '0', 'bet:sangong:list', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (203002, '点码||收码', '', 203, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'bet:sangong:edit', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (204001, '推筒子', '', 204, 1, '', 'front/bet/pusher/index', NULL, 1, 0, 'F', '0', '0', 'bet:pusher:list', '#', '', NULL, '', NULL, '');
INSERT INTO `sys_menu_copy1` VALUES (204002, '点码||收码', '', 204, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'bet:pusher:edit', '#', '', NULL, '', NULL, '');

-- ----------------------------
-- Table structure for sys_odds_configure
-- ----------------------------
DROP TABLE IF EXISTS `sys_odds_configure`;
CREATE TABLE `sys_odds_configure`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baccarat_pump` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '百家乐-庄赢抽水率',
  `baccarat_banker_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-庄赢',
  `baccarat_player_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-闲赢',
  `baccarat_tie_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-和赢',
  `baccarat_banker_pair` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-庄对',
  `baccarat_player_pair` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-闲对',
  `baccarat_large` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-大',
  `baccarat_small` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '百家乐-小',
  `dragon_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '龙虎-龙赢',
  `tiger_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '龙虎-虎赢',
  `tie_win` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '龙虎-和赢',
  `baccarat_rolling_ratio_chip` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(筹码)-美金',
  `baccarat_rolling_ratio_cash` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(现金)-美金',
  `dragon_tiger_ratio_chip` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(筹码)-美金',
  `dragon_tiger_ratio_cash` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(现金)-美金',
  `rolling_commission_rounding` tinyint(2) NOT NULL DEFAULT 0 COMMENT '洗码佣金取整(0:未勾选 、1:已勾选)',
  `banker_win_pump_rounding` tinyint(2) NOT NULL DEFAULT 0 COMMENT '庄赢抽水取整(0:未勾选 、1:已勾选)',
  `baccarat_rolling_ratio_chip_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(筹码)-泰铢',
  `baccarat_rolling_ratio_cash_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '百家乐洗码比例(现金)-泰铢',
  `dragon_tiger_ratio_chip_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(筹码)-泰铢',
  `dragon_tiger_ratio_cash_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '龙虎洗码比例(现金)-泰铢',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `baccarat_two` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '幸运六 两张牌',
  `baccarat_there` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '幸运六 三张牌',
  `password` varchar(100) CHARACTER SET utf16le COLLATE utf16le_general_ci NULL DEFAULT '123456' COMMENT '操作密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_odds_configure
-- ----------------------------
INSERT INTO `sys_odds_configure` VALUES (1, 5.0000, '1', '1', '8', '12', '12', '10', '10', '1', '1', '8', 2.0000, 2.0000, 0.0000, 0.0000, 1, 1, 2.0000, 1.0000, 1.0000, 1.0000, '666', '12', '20', '123456');

-- ----------------------------
-- Table structure for sys_porint
-- ----------------------------
DROP TABLE IF EXISTS `sys_porint`;
CREATE TABLE `sys_porint`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '桌号',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记号',
  `boot_num` bigint(20) NULL DEFAULT NULL COMMENT '靴号',
  `sys_chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '系统点码数',
  `person_chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '手动点码数',
  `chip_gap` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码差距',
  `chip_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码增减',
  `sys_cash` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金系统点码数',
  `person_cash` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金手动点码数',
  `cash_gap` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金差距',
  `cash_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金增减',
  `sys_insurance` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险系统点码数',
  `person_insurance` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险手动点码数',
  `insurance_gap` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险差距',
  `insurance_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险增减',
  `water` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码量',
  `chip_win` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码现金输赢',
  `insurance_win` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险输赢',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点码备注',
  `sys_chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '系统点码数 泰铢',
  `person_chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '手动点码数 泰铢',
  `chip_gap_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码差距 泰铢',
  `chip_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码增减 泰铢',
  `sys_cash_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金系统点码数 泰铢',
  `person_cash_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金手动点码数 泰铢',
  `cash_gap_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金差距 泰铢',
  `cash_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金增减 泰铢',
  `sys_insurance_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险系统点码数 泰铢',
  `person_insurance_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险手动点码数 泰铢',
  `insurance_gap_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险差距 泰铢',
  `insurance_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险增减 泰铢',
  `water_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码量 泰铢',
  `chip_win_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码现金输赢 泰铢',
  `insurance_win_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险输赢 泰铢',
  `game_id` int(11) NULL DEFAULT NULL COMMENT '游戏id',
  `info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点码详情',
  `info_th` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点码详情 泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点码记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_porint
-- ----------------------------

-- ----------------------------
-- Table structure for sys_porint_update
-- ----------------------------
DROP TABLE IF EXISTS `sys_porint_update`;
CREATE TABLE `sys_porint_update`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '桌号',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记号',
  `boot_num` bigint(20) NULL DEFAULT NULL COMMENT '靴号',
  `sys_chip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '系统点码数 现金加筹码',
  `person_chip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '手动点码数  现金加筹码',
  `chip_gap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码差距',
  `chip_add` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码增减',
  `cash_gap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '现金差距',
  `cash_add` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '现金增减',
  `sys_insurance` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险系统点码数',
  `person_insurance` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险手动点码数',
  `insurance_gap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险差距',
  `insurance_add` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险增减',
  `water` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '洗码量',
  `chip_win` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码输赢 现金加筹码',
  `insurance_win` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险输赢',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作备注',
  `sys_chip_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '系统点码数 现金加筹码 泰铢',
  `person_chip_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '手动点码数  现金加筹码 泰铢',
  `chip_gap_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码差距 泰铢',
  `chip_add_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码增减 泰铢',
  `cash_gap_th` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '现金差距 泰铢',
  `cash_add_th` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '现金增减 泰铢',
  `sys_insurance_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险系统点码数 泰铢',
  `person_insurance_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险手动点码数 泰铢',
  `insurance_gap_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险差距 泰铢',
  `insurance_add_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险增减 泰铢',
  `water_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '洗码量 泰铢',
  `chip_win_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '筹码输赢 现金加筹码 泰铢',
  `insurance_win_th` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0.0000' COMMENT '保险输赢 泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点码记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_porint_update
-- ----------------------------

-- ----------------------------
-- Table structure for sys_receipt
-- ----------------------------
DROP TABLE IF EXISTS `sys_receipt`;
CREATE TABLE `sys_receipt`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_id` int(11) NULL DEFAULT NULL COMMENT '台号',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '标记号',
  `chip` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码收',
  `cash` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现在收',
  `insurance` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险收',
  `chip_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码增减',
  `cash_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金增减',
  `insurance_add` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险增减',
  `water` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码',
  `win` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '输赢',
  `insurance_win` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险输赢',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作备注',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `chip_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码收 泰铢',
  `cash_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现在收 泰铢',
  `insurance_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险收 泰铢',
  `chip_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '筹码增减 泰铢',
  `cash_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '现金增减 泰铢',
  `insurance_add_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险增减 泰铢',
  `water_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '洗码 泰铢',
  `win_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '输赢 泰铢',
  `insurance_win_th` decimal(15, 4) NULL DEFAULT 0.0000 COMMENT '保险输赢 泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收码记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for sys_remittance_detailed
-- ----------------------------
DROP TABLE IF EXISTS `sys_remittance_detailed`;
CREATE TABLE `sys_remittance_detailed`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '操作类型: 11:汇入 12:汇出',
  `operation_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '类型: 0:筹码,1:现金',
  `amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动金额-泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '汇款明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_remittance_detailed
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '普通管理员', 'admin', '2022-04-19 06:24:05', 'admin', '2022-05-03 17:57:25', '普通管理员');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 100);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 200);
INSERT INTO `sys_role_menu` VALUES (1, 201);
INSERT INTO `sys_role_menu` VALUES (1, 202);
INSERT INTO `sys_role_menu` VALUES (1, 203);
INSERT INTO `sys_role_menu` VALUES (1, 204);
INSERT INTO `sys_role_menu` VALUES (1, 300);
INSERT INTO `sys_role_menu` VALUES (1, 301);
INSERT INTO `sys_role_menu` VALUES (1, 302);
INSERT INTO `sys_role_menu` VALUES (1, 303);
INSERT INTO `sys_role_menu` VALUES (1, 304);
INSERT INTO `sys_role_menu` VALUES (1, 305);
INSERT INTO `sys_role_menu` VALUES (1, 306);
INSERT INTO `sys_role_menu` VALUES (1, 307);
INSERT INTO `sys_role_menu` VALUES (1, 308);
INSERT INTO `sys_role_menu` VALUES (1, 309);
INSERT INTO `sys_role_menu` VALUES (1, 310);
INSERT INTO `sys_role_menu` VALUES (1, 311);
INSERT INTO `sys_role_menu` VALUES (1, 312);
INSERT INTO `sys_role_menu` VALUES (1, 313);
INSERT INTO `sys_role_menu` VALUES (1, 400);
INSERT INTO `sys_role_menu` VALUES (1, 401);
INSERT INTO `sys_role_menu` VALUES (1, 402);
INSERT INTO `sys_role_menu` VALUES (1, 403);
INSERT INTO `sys_role_menu` VALUES (1, 404);
INSERT INTO `sys_role_menu` VALUES (1, 500);
INSERT INTO `sys_role_menu` VALUES (1, 501);
INSERT INTO `sys_role_menu` VALUES (1, 502);

-- ----------------------------
-- Table structure for sys_signed_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_signed_record`;
CREATE TABLE `sys_signed_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `signed_amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '签单额',
  `signed_amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '签单额-泰铢',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '签单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_signed_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_signed_record_detailed
-- ----------------------------
DROP TABLE IF EXISTS `sys_signed_record_detailed`;
CREATE TABLE `sys_signed_record_detailed`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '操作类型:  5:签单 6:还单',
  `amount_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '金额类型: 0:筹码',
  `amount_before` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动前金额',
  `amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动金额',
  `amount_after` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动后金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `amount_before_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动前金额-泰铢',
  `amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动金额-泰铢',
  `amount_after_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '变动后金额-泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '签单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_signed_record_detailed
-- ----------------------------

-- ----------------------------
-- Table structure for sys_table_management
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_management`;
CREATE TABLE `sys_table_management`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_id` bigint(20) NOT NULL COMMENT '桌台编号',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '桌台ip',
  `game_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '游戏id',
  `game_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '游戏名称',
  `is_delete` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除（0否,1是）',
  `chip_point_base` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码点码基数 美元',
  `cash_point_base` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金点码基数  美元',
  `insurance_point_base` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险筹码点码基数 美元',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者',
  `version` bigint(20) NOT NULL DEFAULT 1 COMMENT '标记号',
  `boot_num` int(10) NOT NULL DEFAULT 1 COMMENT '靴号',
  `game_num` int(10) NOT NULL DEFAULT 0 COMMENT '局数',
  `chip` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码 美元',
  `cash` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金 美元',
  `insurance` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险 美元',
  `chip_add` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码增减 美元',
  `insurance_add` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险增减 美元',
  `cash_add` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金增减 美元',
  `chip_point_base_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码点码基数 泰铢',
  `cash_point_base_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金点码基数 泰铢',
  `insurance_point_base_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险点码基数 泰铢',
  `chip_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码 泰铢',
  `cash_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金 泰铢',
  `insurance_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险 泰铢',
  `chip_add_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '筹码增减 泰铢',
  `cash_add_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '现金增减 泰铢',
  `insurance_add_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '保险增减 泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '桌台信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_table_management
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phonenumber` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系方式',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '用户性别（0男 1女 2未知）',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `post` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `join_time` datetime NULL DEFAULT NULL COMMENT '入职日期',
  `brithday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入职年份',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'abc', '15888888888', 1, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 1, NULL, NULL, '2022-04-26 00:00:00', NULL, 'admin', '2022-04-19 06:24:05', 'hope', '2022-05-03 16:10:20', '管理员', NULL);

-- ----------------------------
-- Table structure for sys_water_detailed
-- ----------------------------
DROP TABLE IF EXISTS `sys_water_detailed`;
CREATE TABLE `sys_water_detailed`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员卡号',
  `operation_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '类型: 0:筹码,1:现金',
  `water` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '结算洗码量-美金',
  `water_amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '应结算洗码费-美金',
  `actual_water_amount` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '实际结算洗码费-美金',
  `deadline` datetime NULL DEFAULT NULL COMMENT '截至日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `water_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '结算洗码量-泰铢',
  `water_amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '应结算洗码费-泰铢',
  `actual_water_amount_th` decimal(15, 4) NOT NULL DEFAULT 0.0000 COMMENT '实际结算洗码费-泰铢',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '洗码结算明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_water_detailed
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
