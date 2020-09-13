/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : jsh_erp

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2020-09-13 18:47:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jsh_account
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account`;
CREATE TABLE `jsh_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `serial_no` varchar(50) DEFAULT NULL COMMENT '编号',
  `initial_amount` decimal(24,6) DEFAULT NULL COMMENT '期初金额',
  `current_amount` decimal(24,6) DEFAULT NULL COMMENT '当前余额',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_default` bit(1) DEFAULT NULL COMMENT '是否默认',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='账户信息';

-- ----------------------------
-- Records of jsh_account
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('17', '账户1', 'zzz111', '100.000000', '-2096.000000', 'aabb', '', '63', '0');
INSERT INTO `jsh_account` VALUES ('18', '账户2', '1234131324', '200.000000', '-1750.000000', 'bb', '\0', '63', '0');

-- ----------------------------
-- Table structure for jsh_account_head
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account_head`;
CREATE TABLE `jsh_account_head` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) DEFAULT NULL COMMENT '类型(支出/收入/收款/付款/转账)',
  `organ_id` bigint(20) DEFAULT NULL COMMENT '单位Id(收款/付款单位)',
  `hands_person_id` bigint(20) DEFAULT NULL COMMENT '经手人id',
  `creator` bigint(20) DEFAULT NULL COMMENT '操作员',
  `change_amount` decimal(24,6) DEFAULT NULL COMMENT '变动金额(优惠/收款/付款/实付)',
  `total_price` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户(收款/付款)',
  `bill_no` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `bill_time` datetime DEFAULT NULL COMMENT '单据日期',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK9F4C0D8DB610FC06` (`organ_id`),
  KEY `FK9F4C0D8DAAE50527` (`account_id`),
  KEY `FK9F4C0D8DC4170B37` (`hands_person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='财务主表';

-- ----------------------------
-- Records of jsh_account_head
-- ----------------------------
INSERT INTO `jsh_account_head` VALUES ('97', '收入', '58', '16', '63', '10.000000', '10.000000', '17', 'SR20191228121609', '2019-12-28 00:00:00', '备注1', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('98', '支出', '57', '16', '63', '-20.000000', '-20.000000', '17', 'ZC20191228121854', '2019-12-28 12:18:54', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('99', '收款', '58', '16', '63', null, '20.000000', null, 'SK20191228121908', '2019-12-28 12:19:08', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('100', '付款', '68', '16', '63', null, '-20.000000', null, 'FK20191228121920', '2019-12-28 12:19:20', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('101', '转账', null, '16', '63', '-20.000000', '-20.000000', '18', 'ZZ20191228121932', '2019-12-28 12:19:32', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('102', '收预付款', '60', '16', '63', null, '1000.000000', null, 'SYF20191228121945', '2019-12-28 12:19:45', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('103', '收入', '58', '16', '63', '20.000000', '20.000000', '18', 'SR20200721163125', '2020-07-21 16:31:25', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('104', '收入', '71', '16', '63', '60.000000', '50.000000', '18', 'SR20200721225712', '2020-07-21 00:00:00', 'bb', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('105', '转账', null, '16', '63', '-11.000000', '-11.000000', '18', 'ZZ20200722005429', '2020-07-22 00:54:29', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('106', '收入', '58', '16', '131', '1000.000000', '1000.000000', '17', 'SR20200913184412', '2020-09-13 18:44:12', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_account_item
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account_item`;
CREATE TABLE `jsh_account_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `header_id` bigint(20) NOT NULL COMMENT '表头Id',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `in_out_item_id` bigint(20) DEFAULT NULL COMMENT '收支项目Id',
  `each_amount` decimal(24,6) DEFAULT NULL COMMENT '单项金额',
  `remark` varchar(100) DEFAULT NULL COMMENT '单据备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK9F4CBAC0AAE50527` (`account_id`),
  KEY `FK9F4CBAC0C5FE6007` (`header_id`),
  KEY `FK9F4CBAC0D203EDC5` (`in_out_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='财务子表';

-- ----------------------------
-- Records of jsh_account_item
-- ----------------------------
INSERT INTO `jsh_account_item` VALUES ('98', '97', null, '22', '10.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('99', '98', null, '21', '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('100', '99', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('101', '100', '17', null, '-20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('102', '101', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('103', '102', '17', null, '1000.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('104', '103', null, '22', '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('105', '104', null, '22', '50.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('106', '105', '17', null, '11.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('107', '106', null, '22', '1000.000000', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_depot
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depot`;
CREATE TABLE `jsh_depot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '仓库名称',
  `address` varchar(50) DEFAULT NULL COMMENT '仓库地址',
  `warehousing` decimal(24,6) DEFAULT NULL COMMENT '仓储费',
  `truckage` decimal(24,6) DEFAULT NULL COMMENT '搬运费',
  `type` int(10) DEFAULT NULL COMMENT '类型',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `principal` bigint(20) DEFAULT NULL COMMENT '负责人',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  `is_default` bit(1) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='仓库表';

-- ----------------------------
-- Records of jsh_depot
-- ----------------------------
INSERT INTO `jsh_depot` VALUES ('14', '仓库1', 'dizhi', '12.000000', '12.000000', '0', '2', '描述', '131', '63', '0', '');
INSERT INTO `jsh_depot` VALUES ('15', '仓库2', '地址100', null, null, '0', '', '', '131', '63', '0', '\0');
INSERT INTO `jsh_depot` VALUES ('17', '仓库3', '123123', '123.000000', '123.000000', '0', '123', '123', '131', '63', '0', '\0');

-- ----------------------------
-- Table structure for jsh_depot_head
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depot_head`;
CREATE TABLE `jsh_depot_head` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) DEFAULT NULL COMMENT '类型(出库/入库)',
  `sub_type` varchar(50) DEFAULT NULL COMMENT '出入库分类',
  `default_number` varchar(50) DEFAULT NULL COMMENT '初始票据号',
  `number` varchar(50) DEFAULT NULL COMMENT '票据号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `oper_time` datetime DEFAULT NULL COMMENT '出入库时间',
  `organ_id` bigint(20) DEFAULT NULL COMMENT '供应商id',
  `hands_person_id` bigint(20) DEFAULT NULL COMMENT '采购/领料-经手人id',
  `creator` bigint(20) DEFAULT NULL COMMENT '操作员',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id',
  `change_amount` decimal(24,6) DEFAULT NULL COMMENT '变动金额(收款/付款)',
  `total_price` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `pay_type` varchar(50) DEFAULT NULL COMMENT '付款类型(现金、记账等)',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `sales_man` varchar(50) DEFAULT NULL COMMENT '业务员（可以多个）',
  `account_id_list` varchar(50) DEFAULT NULL COMMENT '多账户ID列表',
  `account_money_list` varchar(200) DEFAULT NULL COMMENT '多账户金额列表',
  `discount` decimal(24,6) DEFAULT NULL COMMENT '优惠率',
  `discount_money` decimal(24,6) DEFAULT NULL COMMENT '优惠金额',
  `discount_last_money` decimal(24,6) DEFAULT NULL COMMENT '优惠后金额',
  `other_money` decimal(24,6) DEFAULT NULL COMMENT '销售或采购费用合计',
  `other_money_list` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目Id数组（包括快递、招待等）',
  `other_money_item` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目（包括快递、招待等）',
  `account_day` int(10) DEFAULT NULL COMMENT '结算天数',
  `status` varchar(1) DEFAULT NULL COMMENT '状态，0未审核、1已审核、2已转采购|销售',
  `link_number` varchar(50) DEFAULT NULL COMMENT '关联订单号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK2A80F214C4170B37` (`hands_person_id`),
  KEY `FK2A80F214B610FC06` (`organ_id`),
  KEY `FK2A80F214AAE50527` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COMMENT='单据主表';

-- ----------------------------
-- Records of jsh_depot_head
-- ----------------------------
INSERT INTO `jsh_depot_head` VALUES ('189', '入库', '采购', 'CGRK00000000261', 'CGRK00000000261', '2019-04-10 22:25:49', '2020-02-20 23:51:03', '57', null, null, '17', '-120.000000', '-120.000000', '现付', '', '', null, '', '0.000000', '0.000000', '120.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('191', '入库', '采购', 'CGRK00000000264', 'CGRK00000000264', '2019-04-13 19:57:58', '2020-02-20 23:50:55', '57', null, null, '17', '-10.000000', '-10.000000', '现付', '', '', null, '', '0.000000', '0.000000', '10.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('192', '入库', '采购', 'CGRK00000000265', 'CGRK00000000265', '2019-04-20 00:36:24', '2020-02-20 23:50:47', '57', null, null, '17', '-220.000000', '-220.000000', '现付', '', '', null, '', '0.000000', '0.000000', '220.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('193', '出库', '销售', 'XSCK00000000268', 'XSCK00000000268', '2019-04-29 23:41:02', '2020-02-20 23:52:17', '58', null, null, '17', '300.000000', '300.000000', '现付', '', '', null, '', '0.000000', '0.000000', '300.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('196', '入库', '采购', 'CGRK00000000274', 'CGRK00000000274', '2019-04-30 22:35:53', '2020-02-20 23:49:07', '57', null, null, '18', '-1930.000000', '-1930.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1930.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('197', '出库', '销售', 'XSCK00000000290', 'XSCK00000000290', '2019-04-30 23:15:27', '2020-02-20 23:52:01', '58', null, null, '17', '270.000000', '270.000000', '现付', '', '', null, '', '0.000000', '0.000000', '270.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('199', '其它', '采购订单', 'CGDD00000000305', 'CGDD00000000305', '2019-12-28 12:16:36', '2020-02-20 23:47:56', '57', '63', '63', null, '0.000000', '-22.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('200', '出库', '采购退货', 'CGTH00000000306', 'CGTH00000000306', '2019-12-28 12:16:55', '2020-02-20 23:51:28', '57', '63', '63', '17', '11.000000', '11.000000', '现付', '', '', null, '', '0.000000', '0.000000', '11.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('201', '其它', '销售订单', 'XSDD00000000307', 'XSDD00000000307', '2019-12-28 12:17:09', '2020-02-20 23:51:37', '58', '63', '63', null, '0.000000', '15.000000', '现付', '', '<14>', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('202', '入库', '销售退货', 'XSTH00000000308', 'XSTH00000000308', '2019-12-28 12:17:22', '2020-02-20 23:52:33', '58', '63', '63', '17', '-15.000000', '-15.000000', '现付', '', '', null, '', '0.000000', '0.000000', '15.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('203', '入库', '其它', 'QTRK00000000309', 'QTRK00000000309', '2019-12-28 12:17:40', '2020-02-20 23:52:51', '57', '63', '63', null, '0.000000', '42.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('204', '出库', '其它', 'QTCK00000000310', 'QTCK00000000310', '2019-12-28 12:17:48', '2020-02-20 23:53:04', '58', '63', '63', null, '0.000000', '15.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('205', '出库', '调拨', 'DBCK00000000311', 'DBCK00000000311', '2019-12-28 12:17:58', '2020-02-20 23:53:21', null, '63', '63', null, '0.000000', '15.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('206', '其它', '组装单', 'ZZD00000000312', 'ZZD00000000312', '2019-12-28 12:18:09', '2020-02-20 23:54:02', null, '63', '63', null, '0.000000', '10.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('207', '其它', '拆卸单', 'CXD00000000313', 'CXD00000000313', '2019-12-28 12:18:47', '2020-02-20 23:54:21', null, '63', '63', null, '0.000000', '0.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('208', '出库', '零售', 'LSCK00000000314', 'LSCK00000000314', '2019-12-28 12:20:26', '2019-12-28 12:20:14', '60', '63', '63', '17', '30.000000', '30.000000', '预付款', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('209', '入库', '零售退货', 'LSTH00000000315', 'LSTH00000000315', '2019-12-28 12:20:39', '2019-12-28 12:20:29', '60', '63', '63', '17', '-15.000000', '-15.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('210', '入库', '采购', 'CGRK00000000318', 'CGRK00000000318', '2020-02-20 23:22:38', '2020-02-20 23:22:27', '57', '63', '63', '17', '-110.000000', '-110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('211', '入库', '采购', 'CGRK00000000319', 'CGRK00000000319', '2020-02-20 23:54:48', '2020-02-20 23:54:33', '57', '63', '63', '17', '-2400.000000', '-2400.000000', '现付', '', '', null, '', '0.000000', '0.000000', '2400.000000', null, '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('212', '入库', '采购', 'CGRK00000000320', 'CGRK00000000320', '2020-07-14 00:28:15', '2020-07-14 00:27:59', '57', '63', '63', '17', '-535.000000', '-535.000000', '现付', '', '', null, '', '0.000000', '0.000000', '535.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('214', '出库', '销售', 'XSCK00000000321', 'XSCK00000000321', '2020-07-15 00:38:07', '2020-07-15 00:37:36', '58', '63', '63', '17', '2400.000000', '2500.000000', '现付', '', '', null, '', '0.000000', '0.000000', '2500.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('215', '入库', '采购', 'CGRK00000000329', 'CGRK00000000329', '2020-07-21 00:35:16', '2020-07-21 00:35:05', '57', '63', '63', '17', '-800.000000', '-800.000000', '现付', '', '', null, '', '0.000000', '0.000000', '800.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('216', '出库', '销售', 'XSCK00000000330', 'XSCK00000000330', '2020-07-21 00:35:37', '2020-07-21 00:35:26', '58', '63', '63', '17', '308.000000', '308.000000', '现付', '', '', null, '', '0.000000', '0.000000', '308.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('217', '其它', '采购订单', 'CGDD00000000333', 'CGDD00000000333', '2020-07-21 01:15:15', '2020-07-21 01:15:07', '57', '63', '63', null, '0.000000', '-96.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('218', '入库', '采购', 'CGRK00000000334', 'CGRK00000000334', '2020-07-21 01:15:32', '2020-07-21 01:15:28', '57', '63', '63', '17', '-270.000000', '-270.000000', '现付', '', '', null, '', '0.000000', '0.000000', '270.000000', null, null, null, null, '0', 'CGDD00000000333', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('219', '出库', '销售', 'XSCK00000000336', 'XSCK00000000336', '2020-07-21 15:06:02', '2020-07-21 15:05:49', '59', '63', '63', '17', '70.000000', '70.000000', '现付', '', '', null, '', '0.000000', '0.000000', '70.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('222', '出库', '销售', 'XSCK00000000338', 'XSCK00000000338', '2020-07-21 23:05:55', '2020-07-21 23:04:05', '58', '63', '63', '17', '110.000000', '110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('223', '入库', '采购', 'CGRK00000000339', 'CGRK00000000339', '2020-07-21 23:06:55', '2020-07-21 23:06:43', '68', '63', '63', '17', '-110.000000', '-110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('224', '出库', '销售', 'XSCK00000000340', 'XSCK00000000340', '2020-07-21 23:07:06', '2020-07-21 23:06:59', '71', '63', '63', '17', '44.000000', '44.000000', '现付', '', '', null, '', '0.000000', '0.000000', '44.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('225', '其它', '盘点录入', 'PDLR00000000342', 'PDLR00000000342', '2020-07-22 00:07:13', '2020-07-22 00:06:53', null, '63', '63', null, '0.000000', '16.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('226', '其它', '盘点复盘', 'PDFP00000000344', 'PDFP00000000344', '2020-07-22 00:07:41', '2020-07-22 00:08:06', null, '63', '63', null, '0.000000', '16.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '1', 'PDFP00000000343', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('227', '其它', '采购订单', 'CGDD00000000345', 'CGDD00000000345', '2020-07-22 00:41:37', '2020-07-22 00:41:27', '74', '63', '63', null, '0.000000', '-110.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('228', '入库', '采购', 'CGRK00000000351', 'CGRK00000000351', '2020-07-22 00:48:09', '2020-07-22 00:47:48', '74', '63', '63', '17', '-110.000000', '-110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', 'CGDD00000000345', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('229', '入库', '采购', 'CGRK00000000352', 'CGRK00000000352', '2020-09-13 18:43:56', '2020-09-13 18:43:45', '57', null, '131', '17', '-90.000000', '-90.000000', '现付', '', '', null, '', '0.000000', '0.000000', '90.000000', null, null, null, null, '0', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_depot_item
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depot_item`;
CREATE TABLE `jsh_depot_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `header_id` bigint(20) NOT NULL COMMENT '表头Id',
  `material_id` bigint(20) NOT NULL COMMENT '商品Id',
  `material_extend_id` bigint(20) DEFAULT NULL COMMENT '商品扩展id',
  `material_unit` varchar(20) DEFAULT NULL COMMENT '商品计量单位',
  `oper_number` decimal(24,6) DEFAULT NULL COMMENT '数量',
  `basic_number` decimal(24,6) DEFAULT NULL COMMENT '基础数量，如kg、瓶',
  `unit_price` decimal(24,6) DEFAULT NULL COMMENT '单价',
  `tax_unit_price` decimal(24,6) DEFAULT NULL COMMENT '含税单价',
  `all_price` decimal(24,6) DEFAULT NULL COMMENT '金额',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `img` varchar(50) DEFAULT NULL COMMENT '图片',
  `incidentals` decimal(24,6) DEFAULT NULL COMMENT '运杂费',
  `depot_id` bigint(20) DEFAULT NULL COMMENT '仓库ID',
  `another_depot_id` bigint(20) DEFAULT NULL COMMENT '调拨时，对方仓库Id',
  `tax_rate` decimal(24,6) DEFAULT NULL COMMENT '税率',
  `tax_money` decimal(24,6) DEFAULT NULL COMMENT '税额',
  `tax_last_money` decimal(24,6) DEFAULT NULL COMMENT '价税合计',
  `other_field1` varchar(50) DEFAULT NULL COMMENT '自定义字段1-名称',
  `other_field2` varchar(50) DEFAULT NULL COMMENT '自定义字段2-型号',
  `other_field3` varchar(50) DEFAULT NULL COMMENT '自定义字段3-制造商',
  `other_field4` varchar(50) DEFAULT NULL COMMENT '自定义字段4-名称',
  `other_field5` varchar(50) DEFAULT NULL COMMENT '自定义字段5-名称',
  `material_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK2A819F475D61CCF7` (`material_id`),
  KEY `FK2A819F474BB6190E` (`header_id`),
  KEY `FK2A819F479485B3F5` (`depot_id`),
  KEY `FK2A819F47729F5392` (`another_depot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COMMENT='单据子表';

-- ----------------------------
-- Records of jsh_depot_item
-- ----------------------------
INSERT INTO `jsh_depot_item` VALUES ('198', '189', '569', '3', '只', '12.000000', '12.000000', '10.000000', '10.000000', '120.000000', '', null, null, '14', null, '0.000000', '0.000000', '120.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('200', '191', '569', '3', '只', '1.000000', '1.000000', '10.000000', '10.000000', '10.000000', '', null, null, '14', null, '0.000000', '0.000000', '10.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('201', '192', '569', '3', '只', '22.000000', '22.000000', '10.000000', '10.000000', '220.000000', '', null, null, '14', null, '0.000000', '0.000000', '220.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('202', '193', '569', '3', '只', '20.000000', '0.000000', '15.000000', '15.000000', '300.000000', '', null, null, '14', null, '0.000000', '0.000000', '300.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('205', '196', '569', '3', '只', '2.000000', '122.000000', '10.000000', '10.000000', '20.000000', '', null, null, '15', null, '0.000000', '0.000000', '20.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('206', '197', '569', '3', '只', '18.000000', '0.000000', '15.000000', '15.000000', '270.000000', '', null, null, '14', null, '0.000000', '0.000000', '270.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('207', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '15', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('208', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '15', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('209', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('210', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('211', '196', '568', '2', '个', '10.000000', '3.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('212', '196', '568', '2', '个', '10.000000', '4.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('213', '196', '568', '2', '个', '100.000000', '5.000000', '11.000000', '11.000000', '1100.000000', '', null, null, '14', null, '0.000000', '0.000000', '1100.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('214', '196', '569', '3', '只', '15.000000', '6.000000', '10.000000', '10.000000', '150.000000', '', null, null, '14', null, '0.000000', '0.000000', '150.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('216', '199', '568', '2', '个', '2.000000', '2.000000', '11.000000', '11.000000', '22.000000', '', null, null, '14', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('217', '200', '568', '2', '个', '1.000000', '0.000000', '11.000000', '11.000000', '11.000000', '', null, null, '14', null, '0.000000', '0.000000', '11.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('218', '201', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('219', '202', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('220', '203', '568', '2', '个', '2.000000', '2.000000', '11.000000', '11.000000', '22.000000', '', null, null, '14', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('221', '203', '569', '3', '只', '2.000000', '2.000000', '10.000000', '10.000000', '20.000000', '', null, null, '14', null, '0.000000', '0.000000', '20.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('222', '204', '569', '3', '只', '1.000000', '0.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('223', '205', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', '15', '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('224', '206', '568', '2', '个', '1.000000', '1.000000', '5.000000', '5.000000', '5.000000', '', null, null, '14', null, '0.000000', '0.000000', '5.000000', '', '', '', '', '', '组合件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('225', '206', '569', '3', '只', '1.000000', '1.000000', '5.000000', '5.000000', '5.000000', '', null, null, '14', null, '0.000000', '0.000000', '5.000000', '', '', '', '', '', '普通子件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('226', '207', '569', '3', '只', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '组合件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('227', '207', '568', '2', '个', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '普通子件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('228', '208', '568', '2', '个', '2.000000', '2.000000', '15.000000', '15.000000', '30.000000', '', null, null, '14', null, '0.000000', '0.000000', '30.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('229', '209', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('230', '210', '587', '1', '个', '10.000000', '10.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('231', '211', '579', '8', '箱', '10.000000', '120.000000', '240.000000', '240.000000', '2400.000000', '', null, null, '14', null, '0.000000', '0.000000', '2400.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('232', '212', '587', '1', '个', '5.000000', '5.000000', '11.000000', '11.000000', '55.000000', '', null, null, '14', null, '0.000000', '0.000000', '55.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('233', '212', '570', '4', '个', '60.000000', '60.000000', '8.000000', '8.000000', '480.000000', '', null, null, '14', null, '0.000000', '0.000000', '480.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('234', '214', '570', '4', '个', '100.000000', '100.000000', '14.000000', '14.000000', '1400.000000', '', null, null, '14', null, '0.000000', '0.000000', '1400.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('235', '214', '587', '1', '个', '50.000000', '50.000000', '22.000000', '22.000000', '1100.000000', '', null, null, '14', null, '0.000000', '0.000000', '1100.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('236', '215', '570', '4', '个', '100.000000', '100.000000', '8.000000', '8.000000', '800.000000', '', null, null, '14', null, '0.000000', '0.000000', '800.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('237', '216', '570', '4', '个', '22.000000', '22.000000', '14.000000', '14.000000', '308.000000', '', null, null, '14', null, '0.000000', '0.000000', '308.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('238', '217', '570', '4', '个', '12.000000', '12.000000', '8.000000', '8.000000', '96.000000', '', null, null, '14', null, '0.000000', '0.000000', '96.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('239', '218', '570', '4', '个', '15.000000', '15.000000', '18.000000', '18.000000', '270.000000', '', null, null, '14', null, '0.000000', '0.000000', '270.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('240', '219', '570', '4', '个', '5.000000', '5.000000', '14.000000', '14.000000', '70.000000', '', null, null, '14', null, '0.000000', '0.000000', '70.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('241', '222', '588', '10', '个', '5.000000', '5.000000', '22.000000', '22.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('242', '223', '588', '10', '个', '10.000000', '10.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('243', '224', '588', '10', '个', '2.000000', '2.000000', '22.000000', '22.000000', '44.000000', '', null, null, '14', null, '0.000000', '0.000000', '44.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('244', '225', '570', '4', '个', '2.000000', '2.000000', '8.000000', '8.000000', '16.000000', '', null, null, '14', null, '0.000000', '0.000000', '16.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('245', '226', '570', '4', '个', '2.000000', '2.000000', '8.000000', '8.000000', '16.000000', '', null, null, '14', null, '0.000000', '0.000000', '16.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('246', '227', '588', '10', '个', '10.000000', '10.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('247', '228', '588', '10', '个', '10.000000', '10.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('248', '229', '569', '3', '只', '9.000000', '9.000000', '10.000000', '10.000000', '90.000000', '', null, null, '14', null, '0.000000', '0.000000', '90.000000', null, null, null, null, null, '', '63', '0');

-- ----------------------------
-- Table structure for jsh_function
-- ----------------------------
DROP TABLE IF EXISTS `jsh_function`;
CREATE TABLE `jsh_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `parent_number` varchar(50) DEFAULT NULL COMMENT '上级编号',
  `url` varchar(100) DEFAULT NULL COMMENT '链接',
  `state` bit(1) DEFAULT NULL COMMENT '收缩',
  `sort` varchar(50) DEFAULT NULL COMMENT '排序',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `push_btn` varchar(50) DEFAULT NULL COMMENT '功能按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of jsh_function
-- ----------------------------
INSERT INTO `jsh_function` VALUES ('1', '0001', '系统管理', '0', '', '', '0910', '', '电脑版', '', 'icon-settings', '0');
INSERT INTO `jsh_function` VALUES ('13', '000102', '角色管理', '0001', '/pages/manage/role.html', '\0', '0130', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('14', '000103', '用户管理', '0001', '/pages/manage/user.html', '\0', '0140', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('15', '000104', '日志管理', '0001', '/pages/manage/log.html', '\0', '0160', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('16', '000105', '功能管理', '0001', '/pages/manage/functions.html', '\0', '0166', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('21', '0101', '商品管理', '0', '', '\0', '0620', '', '电脑版', null, 'icon-social-dropbox', '0');
INSERT INTO `jsh_function` VALUES ('22', '010101', '商品类别', '0101', '/pages/materials/materialcategory.html', '\0', '0230', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('23', '010102', '商品信息', '0101', '/pages/materials/material.html', '\0', '0240', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('24', '0102', '基本资料', '0', '', '\0', '0750', '', '电脑版', null, 'icon-grid', '0');
INSERT INTO `jsh_function` VALUES ('25', '01020101', '供应商信息', '0102', '/pages/manage/vendor.html', '\0', '0260', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('26', '010202', '仓库信息', '0102', '/pages/manage/depot.html', '\0', '0270', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('31', '010206', '经手人管理', '0102', '/pages/materials/person.html', '\0', '0284', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('32', '0502', '采购管理', '0', '', '\0', '0330', '', '电脑版', '', 'icon-loop', '0');
INSERT INTO `jsh_function` VALUES ('33', '050201', '采购入库', '0502', '/pages/materials/purchase_in_list.html', '\0', '0340', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('38', '0603', '销售管理', '0', '', '\0', '0390', '', '电脑版', '', 'icon-briefcase', '0');
INSERT INTO `jsh_function` VALUES ('40', '080107', '调拨出库', '0801', '/pages/materials/allocation_out_list.html', '\0', '0807', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('41', '060303', '销售出库', '0603', '/pages/materials/sale_out_list.html', '\0', '0394', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('44', '0704', '财务管理', '0', '', '\0', '0450', '', '电脑版', '', 'icon-map', '0');
INSERT INTO `jsh_function` VALUES ('59', '030101', '库存状况', '0301', '/pages/reports/in_out_stock_report.html', '\0', '0600', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('194', '010204', '收支项目', '0102', '/pages/manage/inOutItem.html', '\0', '0282', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('195', '010205', '结算账户', '0102', '/pages/manage/account.html', '\0', '0283', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('197', '070402', '收入单', '0704', '/pages/financial/item_in.html', '\0', '0465', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('198', '0301', '报表查询', '0', '', '\0', '0570', '', '电脑版', null, 'icon-pie-chart', '0');
INSERT INTO `jsh_function` VALUES ('199', '050204', '采购退货', '0502', '/pages/materials/purchase_back_list.html', '\0', '0345', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('200', '060305', '销售退货', '0603', '/pages/materials/sale_back_list.html', '\0', '0396', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('201', '080103', '其它入库', '0801', '/pages/materials/other_in_list.html', '\0', '0803', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('202', '080105', '其它出库', '0801', '/pages/materials/other_out_list.html', '\0', '0805', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('203', '070403', '支出单', '0704', '/pages/financial/item_out.html', '\0', '0470', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('204', '070404', '收款单', '0704', '/pages/financial/money_in.html', '\0', '0475', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('205', '070405', '付款单', '0704', '/pages/financial/money_out.html', '\0', '0480', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('206', '070406', '转账单', '0704', '/pages/financial/giro.html', '\0', '0490', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('207', '030102', '账户统计', '0301', '/pages/reports/account_report.html', '\0', '0610', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('208', '030103', '进货统计', '0301', '/pages/reports/buy_in_report.html', '\0', '0620', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('209', '030104', '销售统计', '0301', '/pages/reports/sale_out_report.html', '\0', '0630', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('210', '040102', '零售出库', '0401', '/pages/materials/retail_out_list.html', '\0', '0405', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('211', '040104', '零售退货', '0401', '/pages/materials/retail_back_list.html', '\0', '0407', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('212', '070407', '收预付款', '0704', '/pages/financial/advance_in.html', '\0', '0495', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('217', '01020102', '客户信息', '0102', '/pages/manage/customer.html', '\0', '0262', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('218', '01020103', '会员信息', '0102', '/pages/manage/member.html', '\0', '0263', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('220', '010103', '计量单位', '0101', '/pages/manage/unit.html', '\0', '0245', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('225', '0401', '零售管理', '0', '', '\0', '0101', '', '电脑版', '', 'icon-present', '0');
INSERT INTO `jsh_function` VALUES ('226', '030106', '入库明细', '0301', '/pages/reports/in_detail.html', '\0', '0640', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('227', '030107', '出库明细', '0301', '/pages/reports/out_detail.html', '\0', '0645', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('228', '030108', '入库汇总', '0301', '/pages/reports/in_material_count.html', '\0', '0650', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('229', '030109', '出库汇总', '0301', '/pages/reports/out_material_count.html', '\0', '0655', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('232', '080109', '组装单', '0801', '/pages/materials/assemble_list.html', '\0', '0809', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('233', '080111', '拆卸单', '0801', '/pages/materials/disassemble_list.html', '\0', '0811', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('234', '000105', '系统配置', '0001', '/pages/manage/systemConfig.html', '\0', '0165', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('235', '030110', '客户对账', '0301', '/pages/reports/customer_account.html', '\0', '0660', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('236', '000106', '商品属性', '0001', '/pages/materials/materialProperty.html', '\0', '0168', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('237', '030111', '供应商对账', '0301', '/pages/reports/vendor_account.html', '\0', '0665', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('239', '0801', '仓库管理', '0', '', '\0', '0420', '', '电脑版', '', 'icon-layers', '0');
INSERT INTO `jsh_function` VALUES ('240', '010104', '序列号', '0101', '/pages/manage/serialNumber.html', '\0', '0246', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('241', '050202', '采购订单', '0502', '/pages/materials/purchase_orders_list.html', '\0', '0335', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('242', '060301', '销售订单', '0603', '/pages/materials/sale_orders_list.html', '\0', '0392', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('243', '000108', '机构管理', '0001', '/pages/manage/organization.html', '', '0150', '', '电脑版', '1', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('244', '030112', '库存预警', '0301', '/pages/reports/stock_warning_report.html', '\0', '0670', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_function` VALUES ('245', '000107', '插件管理', '0001', '/pages/manage/plugin.html', '\0', '0170', '', '电脑版', '1', 'icon-notebook', '0');

-- ----------------------------
-- Table structure for jsh_in_out_item
-- ----------------------------
DROP TABLE IF EXISTS `jsh_in_out_item`;
CREATE TABLE `jsh_in_out_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='收支项目';

-- ----------------------------
-- Records of jsh_in_out_item
-- ----------------------------
INSERT INTO `jsh_in_out_item` VALUES ('21', '快递费', '支出', '', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('22', '房租收入', '收入', '', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('23', '利息收入', '收入', '收入', '63', '0');

-- ----------------------------
-- Table structure for jsh_log
-- ----------------------------
DROP TABLE IF EXISTS `jsh_log`;
CREATE TABLE `jsh_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `operation` varchar(500) DEFAULT NULL COMMENT '操作模块名称',
  `client_ip` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '操作状态 0==成功，1==失败',
  `content` varchar(1000) DEFAULT NULL COMMENT '详情',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `FKF2696AA13E226853` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6798 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of jsh_log
-- ----------------------------

-- ----------------------------
-- Table structure for jsh_material
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material`;
CREATE TABLE `jsh_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) DEFAULT NULL COMMENT '产品类型id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `mfrs` varchar(50) DEFAULT NULL COMMENT '制造商',
  `safety_stock` decimal(24,6) DEFAULT NULL COMMENT '安全存量（KG）',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `standard` varchar(50) DEFAULT NULL COMMENT '规格',
  `color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `unit` varchar(50) DEFAULT NULL COMMENT '单位-单个',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `unit_id` bigint(20) DEFAULT NULL COMMENT '计量单位Id',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用 0-禁用  1-启用',
  `other_field1` varchar(50) DEFAULT NULL COMMENT '自定义1',
  `other_field2` varchar(50) DEFAULT NULL COMMENT '自定义2',
  `other_field3` varchar(50) DEFAULT NULL COMMENT '自定义3',
  `enable_serial_number` varchar(1) DEFAULT '0' COMMENT '是否开启序列号，0否，1是',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK675951272AB6672C` (`category_id`),
  KEY `UnitId` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=589 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of jsh_material
-- ----------------------------
INSERT INTO `jsh_material` VALUES ('568', '17', '商品1', '制1', '100.000000', 'sp1', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('569', '17', '商品2', '', '200.000000', 'sp2', '', '', '只', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('570', '17', '商品3', '', '300.000000', 'sp3', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('577', null, '商品8', '', null, 'sp8', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('579', '21', '商品17', '', null, 'sp17', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('586', '17', '序列号商品测试', '', null, 'xlh123', '', '', '个', '', null, '', '', '', '', '1', '63', '0');
INSERT INTO `jsh_material` VALUES ('587', '17', '商品test1', '南通中远', null, '', 'test1', '', '个', '', null, '\0', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('588', '21', '商品200', '', '112.000000', '', '300ml', '', '个', '', null, '', '', '', '', '0', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_category
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_category`;
CREATE TABLE `jsh_material_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `category_level` smallint(6) DEFAULT NULL COMMENT '等级',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `sort` varchar(10) DEFAULT NULL COMMENT '显示顺序',
  `status` varchar(1) DEFAULT '0' COMMENT '状态，0系统默认，1启用，2删除',
  `serial_no` varchar(100) DEFAULT NULL COMMENT '编号',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `FK3EE7F725237A77D8` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='产品类型表';

-- ----------------------------
-- Records of jsh_material_category
-- ----------------------------
INSERT INTO `jsh_material_category` VALUES ('17', '目录1', null, '-1', '', '1', '', '', '2019-04-10 22:18:12', '63', '2019-04-10 22:18:12', '63', '63');
INSERT INTO `jsh_material_category` VALUES ('21', '目录2', null, '17', '', '1', '', '', '2020-07-20 23:08:44', '63', '2020-07-20 23:08:44', '63', '63');

-- ----------------------------
-- Table structure for jsh_material_current_stock
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_current_stock`;
CREATE TABLE `jsh_material_current_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `depot_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `current_number` decimal(24,6) DEFAULT NULL COMMENT '当前库存数量',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品当前库存';

-- ----------------------------
-- Records of jsh_material_current_stock
-- ----------------------------
INSERT INTO `jsh_material_current_stock` VALUES ('1', '587', '14', '-30.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('2', '570', '14', '48.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('3', '568', '14', '19.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('4', '569', '14', '50.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('5', '588', '14', '13.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('6', '569', '15', '122.000000', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_extend
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_extend`;
CREATE TABLE `jsh_material_extend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `bar_code` varchar(50) DEFAULT NULL COMMENT '商品条码',
  `commodity_unit` varchar(50) DEFAULT NULL COMMENT '商品单位',
  `purchase_decimal` decimal(24,6) DEFAULT NULL COMMENT '采购价格',
  `commodity_decimal` decimal(24,6) DEFAULT NULL COMMENT '零售价格',
  `wholesale_decimal` decimal(24,6) DEFAULT NULL COMMENT '销售价格',
  `low_decimal` decimal(24,6) DEFAULT NULL COMMENT '最低售价',
  `default_flag` varchar(1) DEFAULT '1' COMMENT '是否为默认单位，1是，0否',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `create_serial` varchar(50) DEFAULT NULL COMMENT '创建人编码',
  `update_serial` varchar(50) DEFAULT NULL COMMENT '更新人编码',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间戳',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品价格扩展';

-- ----------------------------
-- Records of jsh_material_extend
-- ----------------------------
INSERT INTO `jsh_material_extend` VALUES ('1', '587', '1000', '个', '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-02-20 23:22:03', 'jsh', 'jsh', '1595263657135', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('2', '568', '1001', '个', '11.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:44:57', 'jsh', 'jsh', '1595265439418', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('3', '569', '1002', '只', '10.000000', '15.000000', '15.000000', '13.000000', '1', '2020-02-20 23:45:15', 'jsh', 'jsh', '1582213514731', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('4', '570', '1003', '个', '8.000000', '15.000000', '14.000000', '13.000000', '1', '2020-02-20 23:45:37', 'jsh', 'jsh', '1587657604430', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('5', '577', '1004', '个', '10.000000', '20.000000', '20.000000', '20.000000', '1', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596494', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('6', '577', '1005', '箱', '120.000000', '240.000000', '240.000000', '240.000000', '0', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596497', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('7', '579', '1006', '个', '20.000000', '30.000000', '30.000000', '30.000000', '1', '2020-02-20 23:47:04', 'jsh', 'jsh', '1595264270458', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('8', '579', '1007', '箱', '240.000000', '360.000000', '360.000000', '360.000000', '0', '2020-02-20 23:47:04', 'jsh', 'jsh', '1595264270466', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('9', '586', '1008', '个', '12.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:47:23', 'jsh', 'jsh', '1595254981896', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('10', '588', '1009', '个', '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-07-21 00:58:15', 'jsh', 'jsh', '1595264315873', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_initial_stock
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_initial_stock`;
CREATE TABLE `jsh_material_initial_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `depot_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `number` decimal(24,6) DEFAULT NULL COMMENT '初始库存数量',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品初始库存';

-- ----------------------------
-- Records of jsh_material_initial_stock
-- ----------------------------
INSERT INTO `jsh_material_initial_stock` VALUES ('123', '587', '14', '5.000000', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_property
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_property`;
CREATE TABLE `jsh_material_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `native_name` varchar(50) DEFAULT NULL COMMENT '原始名称',
  `enabled` bit(1) DEFAULT NULL COMMENT '是否启用',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `another_name` varchar(50) DEFAULT NULL COMMENT '别名',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='产品扩展字段表';

-- ----------------------------
-- Records of jsh_material_property
-- ----------------------------
INSERT INTO `jsh_material_property` VALUES ('1', '规格', '', '02', '规格', '0');
INSERT INTO `jsh_material_property` VALUES ('2', '颜色', '', '01', '颜色', '0');
INSERT INTO `jsh_material_property` VALUES ('3', '制造商', '', '03', '制造商', '0');
INSERT INTO `jsh_material_property` VALUES ('4', '自定义1', '\0', '04', '自定义1', '0');
INSERT INTO `jsh_material_property` VALUES ('5', '自定义2', '\0', '05', '自定义2', '0');
INSERT INTO `jsh_material_property` VALUES ('6', '自定义3', '\0', '06', '自定义3', '0');

-- ----------------------------
-- Table structure for jsh_msg
-- ----------------------------
DROP TABLE IF EXISTS `jsh_msg`;
CREATE TABLE `jsh_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg_title` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `msg_content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` varchar(20) DEFAULT NULL COMMENT '消息类型',
  `status` varchar(1) DEFAULT NULL COMMENT '状态，1未读 2已读',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='消息表';

-- ----------------------------
-- Records of jsh_msg
-- ----------------------------
INSERT INTO `jsh_msg` VALUES ('2', '标题1', '内容1', '2019-09-10 00:11:39', '类型1', '1', '63', '0');

-- ----------------------------
-- Table structure for jsh_organization
-- ----------------------------
DROP TABLE IF EXISTS `jsh_organization`;
CREATE TABLE `jsh_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_no` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `org_full_name` varchar(500) DEFAULT NULL COMMENT '机构全称',
  `org_abr` varchar(20) DEFAULT NULL COMMENT '机构简称',
  `org_tpcd` varchar(9) DEFAULT NULL COMMENT '机构类型',
  `org_stcd` char(1) DEFAULT NULL COMMENT '机构状态,1未营业、2正常营业、3暂停营业、4终止营业、5已除名',
  `org_parent_no` varchar(20) DEFAULT NULL COMMENT '机构父节点编号',
  `sort` varchar(20) DEFAULT NULL COMMENT '机构显示顺序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `org_create_time` datetime DEFAULT NULL COMMENT '机构创建时间',
  `org_stop_time` datetime DEFAULT NULL COMMENT '机构停运时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of jsh_organization
-- ----------------------------
INSERT INTO `jsh_organization` VALUES ('12', '001', '测试机构', '测试机构', null, '2', '-1', '001', '', '2019-12-28 12:13:01', '63', '2019-12-28 12:13:01', '63', null, null, '63');
INSERT INTO `jsh_organization` VALUES ('13', 'jg1', '机构1', '机构1', null, '2', '001', '22', '', '2020-07-21 00:09:57', '63', '2020-07-21 00:10:22', '63', null, null, '63');
INSERT INTO `jsh_organization` VALUES ('14', '12', '机构2', '机构2', null, '', 'jg1', '12', '', '2020-07-21 22:45:42', '63', '2020-07-21 22:45:51', '63', null, null, '63');

-- ----------------------------
-- Table structure for jsh_orga_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `jsh_orga_user_rel`;
CREATE TABLE `jsh_orga_user_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orga_id` bigint(20) NOT NULL COMMENT '机构id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_blng_orga_dspl_seq` varchar(20) DEFAULT NULL COMMENT '用户在所属机构中显示顺序',
  `delete_flag` char(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='机构用户关系表';

-- ----------------------------
-- Records of jsh_orga_user_rel
-- ----------------------------
INSERT INTO `jsh_orga_user_rel` VALUES ('10', '13', '131', '2', '0', '2019-12-28 12:13:15', '63', '2020-09-13 18:42:52', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('11', '12', '63', '', '0', '2020-09-13 18:42:45', '63', '2020-09-13 18:42:45', '63', '63');

-- ----------------------------
-- Table structure for jsh_person
-- ----------------------------
DROP TABLE IF EXISTS `jsh_person`;
CREATE TABLE `jsh_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='经手人表';

-- ----------------------------
-- Records of jsh_person
-- ----------------------------
INSERT INTO `jsh_person` VALUES ('14', '业务员', '小李', '63', '0');
INSERT INTO `jsh_person` VALUES ('15', '仓管员', '小军', '63', '0');
INSERT INTO `jsh_person` VALUES ('16', '财务员', '小夏', '63', '0');

-- ----------------------------
-- Table structure for jsh_role
-- ----------------------------
DROP TABLE IF EXISTS `jsh_role`;
CREATE TABLE `jsh_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of jsh_role
-- ----------------------------
INSERT INTO `jsh_role` VALUES ('4', '管理员', '全部数据', null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('10', '租户', '全部数据', null, '', null, '0');
INSERT INTO `jsh_role` VALUES ('16', '销售经理', '全部数据', null, 'ddd', '63', '0');
INSERT INTO `jsh_role` VALUES ('17', '销售代表', '个人数据', null, 'rrr', '63', '0');
INSERT INTO `jsh_role` VALUES ('18', '角色abc', '本机构数据', null, '33333', '63', '0');

-- ----------------------------
-- Table structure for jsh_sequence
-- ----------------------------
DROP TABLE IF EXISTS `jsh_sequence`;
CREATE TABLE `jsh_sequence` (
  `seq_name` varchar(50) NOT NULL COMMENT '序列名称',
  `min_value` bigint(20) NOT NULL COMMENT '最小值',
  `max_value` bigint(20) NOT NULL COMMENT '最大值',
  `current_val` bigint(20) NOT NULL COMMENT '当前值',
  `increment_val` int(11) NOT NULL DEFAULT '1' COMMENT '增长步数',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单据编号表';

-- ----------------------------
-- Records of jsh_sequence
-- ----------------------------
INSERT INTO `jsh_sequence` VALUES ('depot_number_seq', '1', '999999999999999999', '352', '1', '单据编号sequence');

-- ----------------------------
-- Table structure for jsh_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `jsh_serial_number`;
CREATE TABLE `jsh_serial_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '产品表id',
  `serial_number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `is_sell` varchar(1) DEFAULT '0' COMMENT '是否卖出，0未卖出，1卖出',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `depot_head_id` bigint(20) DEFAULT NULL COMMENT '单据主表id，用于跟踪序列号流向',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='序列号表';

-- ----------------------------
-- Records of jsh_serial_number
-- ----------------------------
INSERT INTO `jsh_serial_number` VALUES ('105', '586', '12312323423223', '0', 'abab', '0', '2019-12-28 12:14:39', '63', '2020-07-21 00:30:32', '63', null, '63');
INSERT INTO `jsh_serial_number` VALUES ('106', '586', '143132415952626404571', '0', '', '1', '2020-07-21 00:30:40', '63', '2020-07-21 00:30:49', '63', null, '63');
INSERT INTO `jsh_serial_number` VALUES ('107', '586', '143132415952626404572', '0', '', '1', '2020-07-21 00:30:40', '63', '2020-07-21 00:30:47', '63', null, '63');
INSERT INTO `jsh_serial_number` VALUES ('108', '586', '3215952626621201', '0', '', '0', '2020-07-21 00:31:02', '63', '2020-07-21 00:31:02', '63', null, '63');
INSERT INTO `jsh_serial_number` VALUES ('109', '586', '3215952626621202', '0', '', '0', '2020-07-21 00:31:02', '63', '2020-07-21 00:31:02', '63', null, '63');

-- ----------------------------
-- Table structure for jsh_supplier
-- ----------------------------
DROP TABLE IF EXISTS `jsh_supplier`;
CREATE TABLE `jsh_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `supplier` varchar(255) NOT NULL COMMENT '供应商名称',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `phone_num` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `description` varchar(500) DEFAULT NULL COMMENT '备注',
  `isystem` tinyint(4) DEFAULT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用',
  `advance_in` decimal(24,6) DEFAULT '0.000000' COMMENT '预收款',
  `begin_need_get` decimal(24,6) DEFAULT NULL COMMENT '期初应收',
  `begin_need_pay` decimal(24,6) DEFAULT NULL COMMENT '期初应付',
  `all_need_get` decimal(24,6) DEFAULT NULL COMMENT '累计应收',
  `all_need_pay` decimal(24,6) DEFAULT NULL COMMENT '累计应付',
  `fax` varchar(30) DEFAULT NULL COMMENT '传真',
  `telephone` varchar(30) DEFAULT NULL COMMENT '手机',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `tax_num` varchar(50) DEFAULT NULL COMMENT '纳税人识别号',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '开户行',
  `account_number` varchar(50) DEFAULT NULL COMMENT '账号',
  `tax_rate` decimal(24,6) DEFAULT NULL COMMENT '税率',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='供应商/客户信息表';

-- ----------------------------
-- Records of jsh_supplier
-- ----------------------------
INSERT INTO `jsh_supplier` VALUES ('57', '供应商1', '小军', '12345678', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, '0.000000', '', '', '地址1', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('58', '客户1', '小李', '12345678', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '-100.000000', null, '', '', '', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('59', '客户2', '小陈', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('60', '12312666', '小曹', '', '', '', null, '会员', '', '970.000000', '0.000000', '0.000000', null, null, '', '13000000000', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('68', '供应商3', '晓丽', '', '', '', null, '供应商', '', '0.000000', '15.000000', '0.000000', null, '-15.000000', '', '13000000000', '', '1341324', '', '', '22.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('71', '客户3', '小周', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('74', '供应商5', '小季', '', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, null, '', '', '', '', '', '', null, '63', '0');

-- ----------------------------
-- Table structure for jsh_system_config
-- ----------------------------
DROP TABLE IF EXISTS `jsh_system_config`;
CREATE TABLE `jsh_system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `company_contacts` varchar(20) DEFAULT NULL COMMENT '公司联系人',
  `company_address` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `company_tel` varchar(20) DEFAULT NULL COMMENT '公司电话',
  `company_fax` varchar(20) DEFAULT NULL COMMENT '公司传真',
  `company_post_code` varchar(20) DEFAULT NULL COMMENT '公司邮编',
  `depot_flag` varchar(1) DEFAULT '0' COMMENT '仓库启用标记，0未启用，1启用',
  `customer_flag` varchar(1) DEFAULT '0' COMMENT '客户启用标记，0未启用，1启用',
  `minus_stock_flag` varchar(1) DEFAULT '0' COMMENT '负库存启用标记，0未启用，1启用',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数';

-- ----------------------------
-- Records of jsh_system_config
-- ----------------------------
INSERT INTO `jsh_system_config` VALUES ('9', '公司1', '小军', '地址1', '12313', '1233', '4231', '0', '0', '1', '63', '0');

-- ----------------------------
-- Table structure for jsh_tenant
-- ----------------------------
DROP TABLE IF EXISTS `jsh_tenant`;
CREATE TABLE `jsh_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `login_name` varchar(255) DEFAULT NULL COMMENT '登录名',
  `user_num_limit` int(11) DEFAULT NULL COMMENT '用户数量限制',
  `bills_num_limit` int(11) DEFAULT NULL COMMENT '单据数量限制',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='租户';

-- ----------------------------
-- Records of jsh_tenant
-- ----------------------------
INSERT INTO `jsh_tenant` VALUES ('13', '63', 'jsh', '20', '2000', null);

-- ----------------------------
-- Table structure for jsh_unit
-- ----------------------------
DROP TABLE IF EXISTS `jsh_unit`;
CREATE TABLE `jsh_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称，支持多单位',
  `basic_unit` varchar(50) DEFAULT NULL COMMENT '基础单位',
  `other_unit` varchar(50) DEFAULT NULL COMMENT '副单位',
  `ratio` int(11) DEFAULT NULL COMMENT '比例',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='多单位表';

-- ----------------------------
-- Records of jsh_unit
-- ----------------------------
INSERT INTO `jsh_unit` VALUES ('15', '个,箱(1:12)', '个', '箱', '12', '63', '0');
INSERT INTO `jsh_unit` VALUES ('16', '个,台(1:10)', '个', '台', '10', '63', '1');
INSERT INTO `jsh_unit` VALUES ('17', '个,只(1:12)', '个', '只', '12', '63', '1');

-- ----------------------------
-- Table structure for jsh_user
-- ----------------------------
DROP TABLE IF EXISTS `jsh_user`;
CREATE TABLE `jsh_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '用户姓名--例如张三',
  `login_name` varchar(255) NOT NULL COMMENT '登录用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '登陆密码',
  `position` varchar(200) DEFAULT NULL COMMENT '职位',
  `department` varchar(255) DEFAULT NULL COMMENT '所属部门',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `phonenum` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `ismanager` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为管理者 0==管理者 1==员工',
  `isystem` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否系统自带数据 ',
  `Status` tinyint(4) DEFAULT '0' COMMENT '状态，0：正常，1：删除，2封禁',
  `description` varchar(500) DEFAULT NULL COMMENT '用户描述信息',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of jsh_user
-- ----------------------------
INSERT INTO `jsh_user` VALUES ('63', '季圣华', 'jsh', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '1', '0', '', null, '63');
INSERT INTO `jsh_user` VALUES ('120', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, null);
INSERT INTO `jsh_user` VALUES ('131', '测试用户', 'test123', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '0', '', null, '63');

-- ----------------------------
-- Table structure for jsh_user_business
-- ----------------------------
DROP TABLE IF EXISTS `jsh_user_business`;
CREATE TABLE `jsh_user_business` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) DEFAULT NULL COMMENT '类别',
  `key_id` varchar(50) DEFAULT NULL COMMENT '主id',
  `value` varchar(10000) DEFAULT NULL COMMENT '值',
  `btn_str` varchar(2000) DEFAULT NULL COMMENT '按钮权限',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='用户/角色/模块关系表';

-- ----------------------------
-- Records of jsh_user_business
-- ----------------------------
INSERT INTO `jsh_user_business` VALUES ('5', 'RoleFunctions', '4', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][59][207][208][209][226][227][228][229][235][237][244][246][22][23][220][240][25][217][218][26][194][195][31][13][16][243][14][15][234][236][245]', '[{\"funId\":\"13\",\"btnStr\":\"1\"},{\"funId\":\"16\",\"btnStr\":\"1\"},{\"funId\":\"243\",\"btnStr\":\"1\"},{\"funId\":\"14\",\"btnStr\":\"1\"},{\"funId\":\"234\",\"btnStr\":\"1\"},{\"funId\":\"236\",\"btnStr\":\"1\"},{\"funId\":\"245\",\"btnStr\":\"1\"},{\"funId\":\"22\",\"btnStr\":\"1\"},{\"funId\":\"23\",\"btnStr\":\"1\"},{\"funId\":\"220\",\"btnStr\":\"1\"},{\"funId\":\"240\",\"btnStr\":\"1\"},{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"26\",\"btnStr\":\"1\"},{\"funId\":\"194\",\"btnStr\":\"1\"},{\"funId\":\"195\",\"btnStr\":\"1\"},{\"funId\":\"31\",\"btnStr\":\"1\"},{\"funId\":\"241\",\"btnStr\":\"1,2\"},{\"funId\":\"33\",\"btnStr\":\"1,2\"},{\"funId\":\"199\",\"btnStr\":\"1,2\"},{\"funId\":\"242\",\"btnStr\":\"1,2\"},{\"funId\":\"41\",\"btnStr\":\"1,2\"},{\"funId\":\"200\",\"btnStr\":\"1,2\"},{\"funId\":\"210\",\"btnStr\":\"1,2\"},{\"funId\":\"211\",\"btnStr\":\"1,2\"},{\"funId\":\"197\",\"btnStr\":\"1\"},{\"funId\":\"203\",\"btnStr\":\"1\"},{\"funId\":\"204\",\"btnStr\":\"1\"},{\"funId\":\"205\",\"btnStr\":\"1\"},{\"funId\":\"206\",\"btnStr\":\"1\"},{\"funId\":\"212\",\"btnStr\":\"1\"},{\"funId\":\"201\",\"btnStr\":\"1,2\"},{\"funId\":\"202\",\"btnStr\":\"1,2\"},{\"funId\":\"40\",\"btnStr\":\"1,2\"},{\"funId\":\"232\",\"btnStr\":\"1,2\"},{\"funId\":\"233\",\"btnStr\":\"1,2\"}]', '0');
INSERT INTO `jsh_user_business` VALUES ('6', 'RoleFunctions', '5', '[22][23][25][26][194][195][31][33][200][201][41][199][202]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('7', 'RoleFunctions', '6', '[22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]', '[{\"funId\":\"33\",\"btnStr\":\"4\"}]', '0');
INSERT INTO `jsh_user_business` VALUES ('9', 'RoleFunctions', '7', '[168][13][12][16][14][15][189][18][19][132]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('10', 'RoleFunctions', '8', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('11', 'RoleFunctions', '9', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('12', 'UserRole', '1', '[5]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('13', 'UserRole', '2', '[6][7]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('14', 'UserDepot', '2', '[1][2][6][7]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('15', 'UserDepot', '1', '[1][2][5][6][7][10][12][14][15][17]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('16', 'UserRole', '63', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('18', 'UserDepot', '63', '[14][15]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('19', 'UserDepot', '5', '[6][45][46][50]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('20', 'UserRole', '5', '[5]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('21', 'UserRole', '64', '[13]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('22', 'UserDepot', '64', '[1]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('23', 'UserRole', '65', '[5]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('24', 'UserDepot', '65', '[1]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('25', 'UserCustomer', '64', '[5][2]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('26', 'UserCustomer', '65', '[6]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('27', 'UserCustomer', '63', '[58]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('28', 'UserDepot', '96', '[7]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('29', 'UserRole', '96', '[6]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('30', 'UserRole', '113', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('32', 'RoleFunctions', '10', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][59][207][208][209][226][227][228][229][235][237][244][246][22][23][220][240][25][217][218][26][194][195][31][13][243][14][15][234]', '[{\"funId\":\"13\",\"btnStr\":\"1\"},{\"funId\":\"243\",\"btnStr\":\"1\"},{\"funId\":\"14\",\"btnStr\":\"1\"},{\"funId\":\"234\",\"btnStr\":\"1\"},{\"funId\":\"22\",\"btnStr\":\"1\"},{\"funId\":\"23\",\"btnStr\":\"1\"},{\"funId\":\"220\",\"btnStr\":\"1\"},{\"funId\":\"240\",\"btnStr\":\"1\"},{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"26\",\"btnStr\":\"1\"},{\"funId\":\"194\",\"btnStr\":\"1\"},{\"funId\":\"195\",\"btnStr\":\"1\"},{\"funId\":\"31\",\"btnStr\":\"1\"},{\"funId\":\"241\",\"btnStr\":\"1,2\"},{\"funId\":\"33\",\"btnStr\":\"1,2\"},{\"funId\":\"199\",\"btnStr\":\"1,2\"},{\"funId\":\"242\",\"btnStr\":\"1,2\"},{\"funId\":\"41\",\"btnStr\":\"1,2\"},{\"funId\":\"200\",\"btnStr\":\"1,2\"},{\"funId\":\"210\",\"btnStr\":\"1,2\"},{\"funId\":\"211\",\"btnStr\":\"1,2\"},{\"funId\":\"197\",\"btnStr\":\"1\"},{\"funId\":\"203\",\"btnStr\":\"1\"},{\"funId\":\"204\",\"btnStr\":\"1\"},{\"funId\":\"205\",\"btnStr\":\"1\"},{\"funId\":\"206\",\"btnStr\":\"1\"},{\"funId\":\"212\",\"btnStr\":\"1\"},{\"funId\":\"201\",\"btnStr\":\"1,2\"},{\"funId\":\"202\",\"btnStr\":\"1,2\"},{\"funId\":\"40\",\"btnStr\":\"1,2\"},{\"funId\":\"232\",\"btnStr\":\"1,2\"},{\"funId\":\"233\",\"btnStr\":\"1,2\"}]', '0');
INSERT INTO `jsh_user_business` VALUES ('34', 'UserRole', '115', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('35', 'UserRole', '117', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('36', 'UserDepot', '117', '[8][9]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('37', 'UserCustomer', '117', '[52]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('38', 'UserRole', '120', '[4]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('39', 'UserDepot', '120', '[7][8][9][10][11][12][2][1][3]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('40', 'UserCustomer', '120', '[52][48][6][5][2]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('41', 'RoleFunctions', '12', '', null, '0');
INSERT INTO `jsh_user_business` VALUES ('48', 'RoleFunctions', '13', '[59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('51', 'UserRole', '74', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('52', 'UserDepot', '121', '[13]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('54', 'UserDepot', '115', '[13]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('56', 'UserCustomer', '115', '[56]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('57', 'UserCustomer', '121', '[56]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('58', 'UserRole', '121', '[15]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('59', 'UserRole', '123', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('60', 'UserRole', '124', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('61', 'UserRole', '125', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('62', 'UserRole', '126', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('63', 'UserRole', '127', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('64', 'UserRole', '128', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('65', 'UserRole', '129', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('66', 'UserRole', '130', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('67', 'UserRole', '131', '[17]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('68', 'RoleFunctions', '16', '[210]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('69', 'RoleFunctions', '17', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]', '[{\"funId\":\"241\",\"btnStr\":\"1,2\"},{\"funId\":\"33\",\"btnStr\":\"1,2\"},{\"funId\":\"199\",\"btnStr\":\"1,2\"},{\"funId\":\"242\",\"btnStr\":\"1,2\"},{\"funId\":\"41\",\"btnStr\":\"1,2\"},{\"funId\":\"200\",\"btnStr\":\"1,2\"},{\"funId\":\"210\",\"btnStr\":\"1,2\"},{\"funId\":\"211\",\"btnStr\":\"1,2\"},{\"funId\":\"197\",\"btnStr\":\"1\"},{\"funId\":\"203\",\"btnStr\":\"1\"},{\"funId\":\"204\",\"btnStr\":\"1\"},{\"funId\":\"205\",\"btnStr\":\"1\"},{\"funId\":\"206\",\"btnStr\":\"1\"},{\"funId\":\"212\",\"btnStr\":\"1\"},{\"funId\":\"201\",\"btnStr\":\"1,2\"},{\"funId\":\"202\",\"btnStr\":\"1,2\"},{\"funId\":\"40\",\"btnStr\":\"1,2\"},{\"funId\":\"232\",\"btnStr\":\"1,2\"},{\"funId\":\"233\",\"btnStr\":\"1,2\"}]', '0');
