/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : jsh_erp

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2021-04-08 00:24:41
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='账户信息';

-- ----------------------------
-- Records of jsh_account
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('17', '账户1', 'zzz111', '100.000000', '829.000000', 'aabb', '', '63', '0');
INSERT INTO `jsh_account` VALUES ('18', '账户2', '1234131324', '200.000000', '-1681.000000', 'bbbb', '\0', '63', '0');
INSERT INTO `jsh_account` VALUES ('19', '11111', '444444', '0.000000', null, null, '\0', '63', '1');
INSERT INTO `jsh_account` VALUES ('20', 'asdfad', 'ewrwrqer', '500.000000', '50.000000', 'dddddd', '\0', '63', '0');
INSERT INTO `jsh_account` VALUES ('21', 'fdasdf', 'fasdfasdf', '0.000000', null, null, '\0', '63', '1');
INSERT INTO `jsh_account` VALUES ('22', 'afwef', 'asdfawe', '0.000000', null, null, '\0', '63', '1');
INSERT INTO `jsh_account` VALUES ('23', 'adsfasdf', 'fasdf', '0.000000', null, null, '\0', '63', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='财务主表';

-- ----------------------------
-- Records of jsh_account_head
-- ----------------------------
INSERT INTO `jsh_account_head` VALUES ('97', '收入', '58', '16', '63', '10.000000', '10.000000', '17', 'SR20191228121609', '2019-12-28 00:00:00', '备注1', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('98', '支出', '57', '16', '63', '20.000000', '20.000000', '17', 'ZC20191228121854', '2019-12-28 12:18:54', 'gggggggg1', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('99', '收款', '58', '16', '63', null, '20.000000', null, 'SK20191228121908', '2019-12-28 12:19:08', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('100', '付款', '68', '16', '63', null, '-20.000000', null, 'FK20191228121920', '2019-12-28 12:19:20', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('101', '转账', null, '16', '63', '-20.000000', '-20.000000', '18', 'ZZ20191228121932', '2019-12-28 12:19:32', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('102', '收预付款', '60', '16', '63', null, '1000.000000', null, 'SYF20191228121945', '2019-12-28 12:19:45', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('103', '收入', '58', '16', '63', '20.000000', '20.000000', '18', 'SR20200721163125', '2020-07-21 16:31:25', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('104', '收入', '71', '16', '63', '60.000000', '50.000000', '18', 'SR20200721225712', '2020-07-21 00:00:00', 'bb', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('105', '转账', null, '16', '63', '-11.000000', '-11.000000', '18', 'ZZ20200722005429', '2020-07-22 00:54:29', '', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('106', '收入', '58', '16', '131', '1000.000000', '1000.000000', '17', 'SR20200913184412', '2020-09-13 18:44:12', '', '63', '1');
INSERT INTO `jsh_account_head` VALUES ('107', '收入', '58', '16', '63', '22.000000', null, '17', 'SR00000000546', '2021-03-16 23:18:32', null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('108', '收入', '59', '16', '63', '55.000000', '55.000000', '18', 'SR00000000547', '2021-03-16 23:21:26', 'bbbb', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('109', '收入', '58', '16', '63', '99.000000', '99.000000', '17', 'SR00000000548', '2021-03-16 23:34:40', null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('110', '收入', '59', '16', '63', '66.000000', '66.000000', '18', 'SR00000000550', '2021-03-17 00:06:18', null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('111', '收入', '59', '16', '63', '44.000000', '44.000000', '17', 'SR00000000551', '2021-03-17 00:17:49', null, '63', '1');
INSERT INTO `jsh_account_head` VALUES ('112', '收入', '58', '16', '63', '444.000000', '444.000000', '17', 'SR00000000552', '2021-03-17 00:19:54', 'hhhhhh222', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('113', '收入', '58', '16', '63', '0.000000', '22.000000', null, 'SR00000000572', '2021-03-17 21:58:33', null, '63', '1');
INSERT INTO `jsh_account_head` VALUES ('114', '收款', '58', '16', '63', '0.000000', '6.000000', null, 'SK00000000580', '2021-03-17 22:07:09', '酷酷酷酷酷', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('115', '付款', '68', '16', '63', '0.000000', '30.000000', null, 'FK00000000591', '2021-03-17 22:18:39', '咩咩咩咩1', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('116', '收预付款', '79', '17', '63', '0.000000', '13.000000', null, 'SYF00000000593', '2021-03-17 22:35:08', '灌灌灌灌', '63', '0');
INSERT INTO `jsh_account_head` VALUES ('117', '转账', null, '17', '63', '6.000000', '66.000000', '18', 'ZZ00000000601', '2021-03-17 22:53:16', 'kkkkk2', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8 COMMENT='财务子表';

-- ----------------------------
-- Records of jsh_account_item
-- ----------------------------
INSERT INTO `jsh_account_item` VALUES ('98', '97', null, '22', '10.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('100', '99', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('101', '100', '17', null, '-20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('102', '101', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('103', '102', '17', null, '1000.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('104', '103', null, '22', '20.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('105', '104', null, '22', '50.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('106', '105', '17', null, '11.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('107', '106', null, '22', '1000.000000', '', '63', '1');
INSERT INTO `jsh_account_item` VALUES ('108', '107', null, null, '0.000000', null, '63', '0');
INSERT INTO `jsh_account_item` VALUES ('109', '108', null, null, '0.000000', null, '63', '0');
INSERT INTO `jsh_account_item` VALUES ('111', '110', null, '22', '10.000000', '6666', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('112', '111', null, null, '0.000000', null, '63', '1');
INSERT INTO `jsh_account_item` VALUES ('121', '112', null, '23', '333.000000', 'gggggg111', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('122', '112', null, '22', '111.000000', 'ddddd', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('123', '109', null, '23', '99.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('125', '98', null, '21', '20.000000', 'dddd', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('126', '113', '18', null, '22.000000', '', '63', '1');
INSERT INTO `jsh_account_item` VALUES ('128', '114', '18', null, '6.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('132', '115', '18', null, '-30.000000', 'kkkk', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('138', '116', '17', null, '5.000000', 'pppp', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('139', '116', '18', null, '8.000000', 'qqqq', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('142', '117', '17', null, '66.000000', 'ggggg1', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='仓库表';

-- ----------------------------
-- Records of jsh_depot
-- ----------------------------
INSERT INTO `jsh_depot` VALUES ('14', '仓库1', 'dizhi', '12.000000', '12.000000', '0', '1', '描述', '131', '63', '0', '');
INSERT INTO `jsh_depot` VALUES ('15', '仓库2', '地址100', '555.000000', '666.000000', '0', '2', 'dfdf', '131', '63', '0', '\0');
INSERT INTO `jsh_depot` VALUES ('17', '仓库3', '123123', '123.000000', '123.000000', '0', '3', '123', '131', '63', '0', '\0');
INSERT INTO `jsh_depot` VALUES ('18', 'fff', 'ggg', '12.000000', '22.000000', null, '4', 'ccccc', null, '63', '1', '\0');

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
  `bill_type` varchar(50) DEFAULT NULL COMMENT '单据类型',
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
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COMMENT='单据主表';

-- ----------------------------
-- Records of jsh_depot_head
-- ----------------------------
INSERT INTO `jsh_depot_head` VALUES ('189', '入库', '采购', 'CGRK00000000261', 'CGRK00000000261', '2019-04-10 22:25:49', '2020-02-20 23:51:03', '57', null, null, '17', '-120.000000', '-120.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '120.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('191', '入库', '采购', 'CGRK00000000264', 'CGRK00000000264', '2019-04-13 19:57:58', '2020-02-20 23:50:55', '57', null, null, '17', '-10.000000', '-10.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '10.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('192', '入库', '采购', 'CGRK00000000265', 'CGRK00000000265', '2019-04-20 00:36:24', '2020-02-20 23:50:47', '57', null, null, '17', '-220.000000', '-220.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '220.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('193', '出库', '销售', 'XSCK00000000268', 'XSCK00000000268', '2019-04-29 23:41:02', '2020-02-20 23:52:17', '58', null, null, '17', '300.000000', '300.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '300.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('196', '入库', '采购', 'CGRK00000000274', 'CGRK00000000274', '2019-04-30 22:35:53', '2020-02-20 23:49:07', '57', null, null, '18', '-1930.000000', '-1930.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '1930.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('197', '出库', '销售', 'XSCK00000000290', 'XSCK00000000290', '2019-04-30 23:15:27', '2020-02-20 23:52:01', '58', null, null, '17', '270.000000', '270.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '270.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('199', '其它', '采购订单', 'CGDD00000000305', 'CGDD00000000305', '2019-12-28 12:16:36', '2020-02-20 23:47:56', '57', '63', '63', null, '0.000000', '-22.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('200', '出库', '采购退货', 'CGTH00000000306', 'CGTH00000000306', '2019-12-28 12:16:55', '2020-02-20 23:51:28', '57', '63', '63', '17', '11.000000', '11.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '11.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('201', '其它', '销售订单', 'XSDD00000000307', 'XSDD00000000307', '2019-12-28 12:17:09', '2020-02-20 23:51:37', '58', '63', '63', null, '0.000000', '15.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('202', '入库', '销售退货', 'XSTH00000000308', 'XSTH00000000308', '2019-12-28 12:17:22', '2020-02-20 23:52:33', '58', '63', '63', '17', '-15.000000', '-15.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '15.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('203', '入库', '其它', 'QTRK00000000309', 'QTRK00000000309', '2019-12-28 12:17:40', '2020-02-20 23:52:51', '57', '63', '63', null, '0.000000', '42.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('204', '出库', '其它', 'QTCK00000000310', 'QTCK00000000310', '2019-12-28 12:17:48', '2020-02-20 23:53:04', '58', '63', '63', null, '0.000000', '15.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('205', '出库', '调拨', 'DBCK00000000311', 'DBCK00000000311', '2019-12-28 12:17:58', '2020-02-20 23:53:21', null, '63', '63', null, '0.000000', '15.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('206', '其它', '组装单', 'ZZD00000000312', 'ZZD00000000312', '2019-12-28 12:18:09', '2020-02-20 23:54:02', null, '63', '63', null, '0.000000', '10.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('207', '其它', '拆卸单', 'CXD00000000313', 'CXD00000000313', '2019-12-28 12:18:47', '2020-02-20 23:54:21', null, '63', '63', null, '0.000000', '0.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('208', '出库', '零售', 'LSCK00000000314', 'LSCK00000000314', '2019-12-28 12:20:26', '2019-12-28 12:20:14', '60', '63', '63', '17', '30.000000', '30.000000', '预付款', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('209', '入库', '零售退货', 'LSTH00000000315', 'LSTH00000000315', '2019-12-28 12:20:39', '2019-12-28 12:20:29', '60', '63', '63', '17', '-15.000000', '-15.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('210', '入库', '采购', 'CGRK00000000318', 'CGRK00000000318', '2020-02-20 23:22:38', '2020-02-20 23:22:27', '57', '63', '63', '17', '-110.000000', '-110.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('211', '入库', '采购', 'CGRK00000000319', 'CGRK00000000319', '2020-02-20 23:54:48', '2020-02-20 23:54:33', '57', '63', '63', '17', '-2400.000000', '-2400.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '2400.000000', null, '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('212', '入库', '采购', 'CGRK00000000320', 'CGRK00000000320', '2020-07-14 00:28:15', '2020-07-14 00:27:59', '57', '63', '63', '17', '-535.000000', '-535.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '535.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('214', '出库', '销售', 'XSCK00000000321', 'XSCK00000000321', '2020-07-15 00:38:07', '2020-07-15 00:37:36', '58', '63', '63', '17', '2400.000000', '2500.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '2500.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('215', '入库', '采购', 'CGRK00000000329', 'CGRK00000000329', '2020-07-21 00:35:16', '2020-07-21 00:35:05', '57', '63', '63', '17', '-800.000000', '-800.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '800.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('216', '出库', '销售', 'XSCK00000000330', 'XSCK00000000330', '2020-07-21 00:35:37', '2020-07-21 00:35:26', '58', '63', '63', '17', '308.000000', '308.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '308.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('217', '其它', '采购订单', 'CGDD00000000333', 'CGDD00000000333', '2020-07-21 01:15:15', '2020-07-21 01:15:07', '57', '63', '63', null, '0.000000', '-96.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('218', '入库', '采购', 'CGRK00000000334', 'CGRK00000000334', '2020-07-21 01:15:32', '2020-07-21 01:15:28', '57', '63', '63', '17', '-270.000000', '-270.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '270.000000', null, null, null, null, '0', 'CGDD00000000333', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('219', '出库', '销售', 'XSCK00000000336', 'XSCK00000000336', '2020-07-21 15:06:02', '2020-07-21 15:05:49', '59', '63', '63', '17', '70.000000', '70.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '70.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('222', '出库', '销售', 'XSCK00000000338', 'XSCK00000000338', '2020-07-21 23:05:55', '2020-07-21 23:04:05', '58', '63', '63', '17', '110.000000', '110.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('223', '入库', '采购', 'CGRK00000000339', 'CGRK00000000339', '2020-07-21 23:06:55', '2020-07-21 23:06:43', '68', '63', '63', '17', '-110.000000', '-110.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('224', '出库', '销售', 'XSCK00000000340', 'XSCK00000000340', '2020-07-21 23:07:06', '2020-07-21 23:06:59', '71', '63', '63', '17', '44.000000', '44.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '44.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('225', '其它', '盘点录入', 'PDLR00000000342', 'PDLR00000000342', '2020-07-22 00:07:13', '2020-07-22 00:06:53', null, '63', '63', null, '0.000000', '16.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('226', '其它', '盘点复盘', 'PDFP00000000344', 'PDFP00000000344', '2020-07-22 00:07:41', '2020-07-22 00:08:06', null, '63', '63', null, '0.000000', '16.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '1', 'PDFP00000000343', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('227', '其它', '采购订单', 'CGDD00000000345', 'CGDD00000000345', '2020-07-22 00:41:37', '2020-07-22 00:41:27', '74', '63', '63', null, '0.000000', '-110.000000', '现付', null, '', '', null, '', null, null, null, null, null, null, null, '2', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('228', '入库', '采购', 'CGRK00000000351', 'CGRK00000000351', '2020-07-22 00:48:09', '2020-07-22 00:47:48', '74', '63', '63', '17', '-110.000000', '-110.000000', '现付', null, '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', 'CGDD00000000345', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('229', '入库', '采购', 'CGRK00000000352', 'CGRK00000000352', '2020-09-13 18:43:56', '2020-09-13 18:43:45', '57', null, '131', '17', '90.000000', '90.000000', '现付', null, 'adfasdfasdf', '', null, '', '0.000000', '0.000000', '90.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('231', '其它', '采购订单', '41341341', '41341341', '2021-03-10 22:17:24', '2021-03-10 22:17:19', '57', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('232', '其它', '采购订单', 'CGDD00000000376', 'CGDD00000000376', '2021-03-11 00:20:16', '2021-03-11 00:19:52', '57', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('233', '其它', '采购订单', 'CGDD00000000379', 'CGDD00000000379', '2021-03-11 21:53:32', '2021-03-11 21:51:52', '57', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('234', '其它', '采购订单', 'CGDD00000000401', 'CGDD00000000401', '2021-03-11 23:38:02', '2021-03-11 23:34:57', '74', null, '63', null, null, '4422.000000', null, null, 'ggggggggggg', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('235', '其它', '采购订单', 'CGDD00000000403', 'CGDD00000000403', '2021-03-11 23:40:35', '2021-03-11 23:39:12', '57', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('236', '其它', '采购订单', 'CGDD00000000404', 'CGDD00000000404', '2021-03-11 23:44:02', '2021-03-11 23:43:42', '68', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('237', '其它', '采购订单', 'CGDD00000000405', 'CGDD00000000405', '2021-03-11 23:47:01', '2021-03-11 23:46:48', '74', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('238', '其它', '采购订单', 'CGDD00000000407', 'CGDD00000000407', '2021-03-11 23:49:41', '2021-03-11 23:49:18', '74', null, '63', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('239', '其它', '采购订单', 'CGDD00000000409', 'CGDD00000000409', '2021-03-12 00:03:03', '2021-03-12 00:02:20', '57', null, '63', null, null, '244.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('240', '其它', '采购订单', 'CGDD00000000428', 'CGDD00000000428', '2021-03-12 21:56:59', '2021-03-12 21:56:43', '68', null, '63', null, null, '22.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('241', '其它', '采购订单', 'CGDD00000000449', 'CGDD00000000449', '2021-03-13 00:51:18', '2021-03-13 00:51:03', '74', null, '63', null, null, '154.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('242', '其它', '采购订单', 'CGDD00000000454', 'CGDD00000000454', '2021-03-13 01:00:15', '2021-03-13 00:59:59', '57', null, '63', null, null, '28.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('243', '其它', '采购订单', 'CGDD00000000456', 'CGDD00000000456', '2021-03-13 01:02:10', '2021-03-13 01:01:53', '74', null, '63', null, null, '112.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('244', '其它', '采购订单', 'CGDD00000000461', 'CGDD00000000461', '2021-03-13 01:04:40', '2021-03-13 01:04:25', '88', null, '63', null, null, '864.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('245', '其它', '采购订单', 'CGDD00000000465', 'CGDD00000000465', '2021-03-13 01:09:14', '2021-03-13 01:09:05', '57', null, '63', null, null, '222.000000', null, null, 'ggggggggggg', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('246', '入库', '采购', 'CGDD00000000467', 'CGDD00000000467', '2021-03-13 15:59:22', '2021-03-13 15:58:41', '57', null, '63', null, null, '234.000000', null, null, '方法方法方法11111', null, null, null, null, null, '234.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('247', '入库', '采购', 'CGDD00000000469', 'CGDD00000000469', '2021-03-14 23:14:05', '2021-03-14 23:13:52', '68', null, '63', null, null, '444.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('248', '入库', '采购', 'CGDD00000000484', 'CGDD00000000484', '2021-03-14 23:51:58', '2021-03-14 23:51:29', '68', null, '63', null, null, '2220.000000', null, null, null, null, null, null, '0.000000', '0.000000', '2442.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('249', '入库', '采购', 'CGDD00000000508', 'CGDD00000000508', '2021-03-15 00:37:40', '2021-03-15 00:36:35', '57', null, '63', null, '560.000000', '554.000000', null, null, null, null, null, null, '0.000000', '0.000000', '565.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('250', '出库', '采购退货', 'CGTH00000000509', 'CGTH00000000509', '2021-03-15 23:26:21', '2021-03-15 23:26:01', '57', null, '63', null, '44.000000', '44.000000', null, null, null, null, null, null, '0.000000', '0.000000', '44.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('251', '其它', '采购订单', 'CGDD00000000511', 'CGDD00000000511', '2021-03-15 23:27:02', '2021-03-15 23:26:46', '57', null, '63', null, null, '22.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('252', '其它', '销售订单', 'XSDD00000000515', 'XSDD00000000515', '2021-03-15 23:35:00', '2021-03-15 23:34:42', '58', null, '63', null, '444.000000', '444.000000', null, null, null, null, null, null, '0.000000', '0.000000', '444.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('253', '出库', '销售', 'XSCK00000000516', 'XSCK00000000516', '2021-03-15 23:36:44', '2021-03-15 23:36:27', '68', null, '63', null, '440.000000', '440.000000', null, null, null, null, null, null, '0.000000', '0.000000', '440.000000', null, null, null, null, '0', null, '63', '1');
INSERT INTO `jsh_depot_head` VALUES ('254', '入库', '销售退货', 'XSTH00000000517', 'XSTH00000000517', '2021-03-15 23:39:57', '2021-03-15 23:39:37', '59', null, '63', null, '1110.000000', '1110.000000', null, null, null, null, null, null, '0.000000', '0.000000', '1110.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('255', '出库', '销售', 'XSCK00000000519', 'XSCK00000000519', '2021-03-15 23:42:08', '2021-03-15 23:41:50', '59', null, '63', null, '666.000000', '666.000000', null, null, null, null, null, null, '0.000000', '0.000000', '666.000000', null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('256', '入库', '采购', 'CGRK00000000628', 'CGRK00000000628', '2021-04-07 23:25:23', '2021-04-07 23:25:12', '57', null, '63', '17', '44.000000', '44.000000', null, null, null, null, '', '', '0.000000', '0.000000', '44.000000', '0.000000', null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('257', '出库', '销售', 'XSCK00000000629', 'XSCK00000000629', '2021-04-07 23:25:47', '2021-04-07 23:25:35', '58', null, '63', '17', '70.000000', '70.000000', null, null, null, null, null, null, '0.000000', '0.000000', '70.000000', '0.000000', null, null, null, '0', null, '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=utf8 COMMENT='单据子表';

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
INSERT INTO `jsh_depot_item` VALUES ('249', '230', '579', '7', '个', '1.000000', '1.000000', '20.000000', null, '20.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('250', '231', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('251', '231', '612', '20', '个', '1.000000', '1.000000', '222.000000', null, '222.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('252', '232', '579', '7', '个', '5.000000', '5.000000', '20.000000', null, '100.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('253', '233', '587', '1', '个', '2.000000', '2.000000', '11.000000', null, '22.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('254', '233', '577', '5', '个', '2.000000', '2.000000', '10.000000', null, '20.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('257', '235', '613', '23', '个', '2.000000', null, '22.000000', null, '44.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('258', '236', '612', '20', '个', '1.000000', '1.000000', '222.000000', null, '222.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('259', '237', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('260', '238', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('261', '239', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('262', '239', '612', '20', '个', '1.000000', '1.000000', '222.000000', null, '222.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('268', '240', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('269', '234', '613', '23', '个', '2.000000', null, '22.000000', null, '44.000000', 'aa', null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('270', '234', '588', '10', '个', '2.000000', '2.000000', '11.000000', null, '22.000000', 'bb', null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('271', '241', '613', '23', '个', '7.000000', null, '22.000000', null, '154.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('273', '242', '610', '17', '个', '2.000000', '2.000000', '14.000000', null, '28.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('275', '243', '610', '17', '个', '8.000000', '8.000000', '14.000000', null, '112.000000', null, null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('281', '244', '613', '23', '个', '9.000000', null, '22.000000', null, '198.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('282', '244', '612', '20', '个', '3.000000', '3.000000', '222.000000', null, '666.000000', null, null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('291', '245', '612', '20', '个', '1.000000', '1.000000', '222.000000', null, '222.000000', null, null, null, '15', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('292', '246', '613', '23', '个', '10.000000', null, '22.000000', null, '220.000000', 'hhhh', null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('293', '246', '610', '17', '个', '1.000000', '1.000000', '14.000000', null, '14.000000', 'jjjjj', null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('294', '229', '569', '3', '只', '9.000000', '9.000000', '10.000000', null, '90.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('297', '247', '612', '20', '个', '2.000000', '2.000000', '222.000000', null, '444.000000', null, null, null, '17', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('298', '248', '612', '20', '个', '10.000000', '10.000000', '222.000000', '222.000000', '2220.000000', null, null, null, '15', null, '10.000000', '222.000000', '2442.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('300', '249', '613', '23', '个', '5.000000', null, '22.000000', '22.000000', '110.000000', null, null, null, '14', null, '10.000000', '11.000000', '121.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('301', '249', '612', '20', '个', '2.000000', '2.000000', '222.000000', '222.000000', '444.000000', null, null, null, '15', null, '0.000000', '0.000000', '444.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('302', '250', '613', '23', '个', '2.000000', null, '22.000000', '22.000000', '44.000000', null, null, null, '14', null, '0.000000', '0.000000', '44.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('303', '251', '613', '23', '个', '1.000000', null, '22.000000', null, '22.000000', null, null, null, '14', null, null, null, null, null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('304', '252', '612', '20', '个', '2.000000', '2.000000', '222.000000', '222.000000', '444.000000', null, null, null, '14', null, '0.000000', '0.000000', '444.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('306', '253', '613', '23', '个', '20.000000', null, '22.000000', '22.000000', '440.000000', null, null, null, '14', null, '0.000000', '0.000000', '440.000000', null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_depot_item` VALUES ('308', '255', '612', '20', '个', '3.000000', '3.000000', '222.000000', '222.000000', '666.000000', null, null, null, '14', null, '0.000000', '0.000000', '666.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('309', '254', '612', '20', '个', '5.000000', '5.000000', '222.000000', '222.000000', '1110.000000', null, null, null, '14', null, '0.000000', '0.000000', '1110.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('310', '256', '613', '23', '个', '2.000000', null, '22.000000', '22.000000', '44.000000', null, null, null, '14', null, '0.000000', '0.000000', '44.000000', null, null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('311', '257', '610', '17', '个', '5.000000', '5.000000', '14.000000', '14.000000', '70.000000', null, null, null, '14', null, '0.000000', '0.000000', '70.000000', null, null, null, null, null, null, '63', '0');

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
  `component` varchar(100) DEFAULT NULL COMMENT '组件',
  `state` bit(1) DEFAULT NULL COMMENT '收缩',
  `sort` varchar(50) DEFAULT NULL COMMENT '排序',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `push_btn` varchar(50) DEFAULT NULL COMMENT '功能按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of jsh_function
-- ----------------------------
INSERT INTO `jsh_function` VALUES ('1', '0001', '系统管理', '0', '/system', '/layouts/TabLayout', '', '0910', '', '电脑版', '', 'setting', '0');
INSERT INTO `jsh_function` VALUES ('13', '000102', '角色管理', '0001', '/system/role', '/system/RoleList', '\0', '0130', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('14', '000103', '用户管理', '0001', '/system/user', '/system/UserList', '\0', '0140', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('15', '000104', '日志管理', '0001', '/system/log', '/system/LogList', '\0', '0160', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('16', '000105', '功能管理', '0001', '/system/function', '/system/FunctionList', '\0', '0166', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('21', '0101', '商品管理', '0', '/material', '/layouts/TabLayout', '\0', '0620', '', '电脑版', null, 'shopping', '0');
INSERT INTO `jsh_function` VALUES ('22', '010101', '商品类别', '0101', '/material/material_category', '/material/MaterialCategoryList', '\0', '0230', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('23', '010102', '商品信息', '0101', '/material/material', '/material/MaterialList', '\0', '0240', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('24', '0102', '基本资料', '0', '/systemA', '/layouts/TabLayout', '\0', '0750', '', '电脑版', null, 'appstore', '0');
INSERT INTO `jsh_function` VALUES ('25', '01020101', '供应商信息', '0102', '/system/vendor', '/system/VendorList', '\0', '0260', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('26', '010202', '仓库信息', '0102', '/system/depot', '/system/DepotList', '\0', '0270', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('31', '010206', '经手人管理', '0102', '/system/person', '/system/PersonList', '\0', '0284', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('32', '0502', '采购管理', '0', '/bill', '/layouts/TabLayout', '\0', '0330', '', '电脑版', '', 'retweet', '0');
INSERT INTO `jsh_function` VALUES ('33', '050201', '采购入库', '0502', '/bill/purchase_in', '/bill/PurchaseInList', '\0', '0340', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('38', '0603', '销售管理', '0', '/billB', '/layouts/TabLayout', '\0', '0390', '', '电脑版', '', 'shopping-cart', '0');
INSERT INTO `jsh_function` VALUES ('40', '080107', '调拨出库', '0801', '/bill/allocation_out', '/bill/AllocationOutList', '\0', '0807', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('41', '060303', '销售出库', '0603', '/bill/sale_out', '/bill/SaleOutList', '\0', '0394', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('44', '0704', '财务管理', '0', '/financial', '/layouts/TabLayout', '\0', '0450', '', '电脑版', '', 'money-collect', '0');
INSERT INTO `jsh_function` VALUES ('59', '030101', '库存状况', '0301', '/report/in_out_stock_report', '/report/InOutStockReport', '\0', '0600', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('194', '010204', '收支项目', '0102', '/system/in_out_item', '/system/InOutItemList', '\0', '0282', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('195', '010205', '结算账户', '0102', '/system/account', '/system/AccountList', '\0', '0283', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('197', '070402', '收入单', '0704', '/financial/item_in', '/financial/ItemInList', '\0', '0465', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('198', '0301', '报表查询', '0', '/report', '/layouts/TabLayout', '\0', '0570', '', '电脑版', null, 'pie-chart', '0');
INSERT INTO `jsh_function` VALUES ('199', '050204', '采购退货', '0502', '/bill/purchase_back', '/bill/PurchaseBackList', '\0', '0345', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('200', '060305', '销售退货', '0603', '/bill/sale_back', '/bill/SaleBackList', '\0', '0396', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('201', '080103', '其它入库', '0801', '/bill/other_in', '/bill/OtherInList', '\0', '0803', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('202', '080105', '其它出库', '0801', '/bill/other_out', '/bill/OtherOutList', '\0', '0805', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('203', '070403', '支出单', '0704', '/financial/item_out', '/financial/ItemOutList', '\0', '0470', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('204', '070404', '收款单', '0704', '/financial/money_in', '/financial/MoneyInList', '\0', '0475', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('205', '070405', '付款单', '0704', '/financial/money_out', '/financial/MoneyOutList', '\0', '0480', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('206', '070406', '转账单', '0704', '/financial/giro', '/financial/GiroList', '\0', '0490', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('207', '030102', '账户统计', '0301', '/report/account_report', '/report/AccountReport', '\0', '0610', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('208', '030103', '进货统计', '0301', '/report/buy_in_report', '/report/BuyInReport', '\0', '0620', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('209', '030104', '销售统计', '0301', '/report/sale_out_report', '/report/SaleOutReport', '\0', '0630', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('210', '040102', '零售出库', '0401', '/bill/retail_out', '/bill/RetailOutList', '\0', '0405', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('211', '040104', '零售退货', '0401', '/bill/retail_back', '/bill/RetailBackList', '\0', '0407', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('212', '070407', '收预付款', '0704', '/financial/advance_in', '/financial/AdvanceInList', '\0', '0495', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('217', '01020102', '客户信息', '0102', '/system/customer', '/system/CustomerList', '\0', '0262', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('218', '01020103', '会员信息', '0102', '/system/member', '/system/MemberList', '\0', '0263', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('220', '010103', '计量单位', '0101', '/system/unit', '/system/UnitList', '\0', '0245', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('225', '0401', '零售管理', '0', '/billC', '/layouts/TabLayout', '\0', '0101', '', '电脑版', '[]', 'gift', '0');
INSERT INTO `jsh_function` VALUES ('226', '030106', '入库明细', '0301', '/report/in_detail', '/report/InDetail', '\0', '0640', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('227', '030107', '出库明细', '0301', '/report/out_detail', '/report/OutDetail', '\0', '0645', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('228', '030108', '入库汇总', '0301', '/report/in_material_count', '/report/InMaterialCount', '\0', '0650', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('229', '030109', '出库汇总', '0301', '/report/out_material_count', '/report/OutMaterialCount', '\0', '0655', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('232', '080109', '组装单', '0801', '/bill/assemble', '/bill/AssembleList', '\0', '0809', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('233', '080111', '拆卸单', '0801', '/bill/disassemble', '/bill/DisassembleList', '\0', '0811', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('234', '000105', '系统配置', '0001', '/system/system_config', '/system/SystemConfigList', '\0', '0165', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('235', '030110', '客户对账', '0301', '/report/customer_account', '/report/CustomerAccount', '\0', '0660', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('236', '000106', '商品属性', '0001', '/material/material_property', '/material/MaterialPropertyList', '\0', '0168', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('237', '030111', '供应商对账', '0301', '/report/vendor_account', '/report/VendorAccount', '\0', '0665', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('239', '0801', '仓库管理', '0', '/billD', '/layouts/TabLayout', '\0', '0420', '', '电脑版', '', 'hdd', '0');
INSERT INTO `jsh_function` VALUES ('240', '010104', '序列号', '0101', '/system/serial_number', '/system/SerialNumberList', '\0', '0246', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('241', '050202', '采购订单', '0502', '/bill/purchase_order', '/bill/PurchaseOrderList', '\0', '0335', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('242', '060301', '销售订单', '0603', '/bill/sale_order', '/bill/SaleOrderList', '\0', '0392', '', '电脑版', '1,2', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('243', '000108', '机构管理', '0001', '/system/organization', '/system/OrganizationList', '', '0150', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('244', '030112', '库存预警', '0301', '/report/stock_warning_report', '/report/StockWarningReport', '\0', '0670', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('245', '000107', '插件管理', '0001', '/system/plugin', '/system/PluginList', '\0', '0170', '', '电脑版', '1', 'profile', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='收支项目';

-- ----------------------------
-- Records of jsh_in_out_item
-- ----------------------------
INSERT INTO `jsh_in_out_item` VALUES ('21', '快递费', '支出', '', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('22', '房租收入', '收入', '', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('23', '利息收入', '收入', '收入', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('24', '11111', '收入', '2222222', '63', '0');
INSERT INTO `jsh_in_out_item` VALUES ('25', '3333', '支出', '44444455555', '63', '1');
INSERT INTO `jsh_in_out_item` VALUES ('26', '6666', '支出', '7777777', '63', '1');
INSERT INTO `jsh_in_out_item` VALUES ('27', '7777', '支出', '88888', '63', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=7492 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of jsh_log
-- ----------------------------
INSERT INTO `jsh_log` VALUES ('6800', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-21 00:25:48', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6801', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-21 00:26:17', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6802', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-21 00:32:25', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6803', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-21 23:16:29', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6804', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 10:02:53', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6805', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 10:58:35', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6806', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 13:23:52', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6807', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 13:27:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6808', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 13:43:38', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6809', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 13:49:49', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6810', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-24 15:18:13', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6811', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 15:06:33', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6812', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 15:33:09', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6813', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 15:40:48', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6814', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 15:50:08', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6815', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 15:57:19', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6816', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 16:00:53', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('6817', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 16:06:14', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6818', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:35:52', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6819', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:36:34', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6820', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:37:05', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6821', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:47:19', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6822', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:48:18', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6823', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:50:13', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6824', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:58:14', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6825', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 17:59:46', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6826', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:00:02', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6827', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:00:34', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6828', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:01:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6829', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:02:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6830', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:02:54', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6831', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:04:28', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6832', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:05:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6833', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 18:05:49', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6834', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:39:31', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6835', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:54:27', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6836', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:54:43', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6837', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:54:56', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('6838', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:55:05', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6839', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:56:13', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6840', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:56:31', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6841', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:57:50', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6842', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:58:06', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('6843', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:58:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6844', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:58:22', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('6845', '131', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:58:33', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('6846', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-25 19:59:19', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6847', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-27 15:42:45', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6848', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 16:59:08', '0', '新增11111', '63');
INSERT INTO `jsh_log` VALUES ('6849', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 17:03:56', '0', '修改11111', '63');
INSERT INTO `jsh_log` VALUES ('6850', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 17:04:01', '0', '删除[11111]', '63');
INSERT INTO `jsh_log` VALUES ('6851', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 17:04:06', '0', '新增asdfad', '63');
INSERT INTO `jsh_log` VALUES ('6852', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:39:38', '0', '修改asdfad', '63');
INSERT INTO `jsh_log` VALUES ('6853', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:51:40', '0', '修改账户1', '63');
INSERT INTO `jsh_log` VALUES ('6854', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:51:50', '0', '修改账户1', '63');
INSERT INTO `jsh_log` VALUES ('6855', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:52:52', '0', '修改账户2', '63');
INSERT INTO `jsh_log` VALUES ('6856', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:53:45', '0', '修改账户1', '63');
INSERT INTO `jsh_log` VALUES ('6857', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:53:50', '0', '修改账户1', '63');
INSERT INTO `jsh_log` VALUES ('6858', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:55:57', '0', '修改asdfad', '63');
INSERT INTO `jsh_log` VALUES ('6859', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 18:56:20', '0', '修改asdfad', '63');
INSERT INTO `jsh_log` VALUES ('6860', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:01:43', '0', '新增fdasdf', '63');
INSERT INTO `jsh_log` VALUES ('6861', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:01:48', '0', '删除[fdasdf]', '63');
INSERT INTO `jsh_log` VALUES ('6862', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:01:55', '0', '新增afwef', '63');
INSERT INTO `jsh_log` VALUES ('6863', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:02:00', '0', '新增adsfasdf', '63');
INSERT INTO `jsh_log` VALUES ('6864', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:02:05', '0', '删除[afwef][adsfasdf]', '63');
INSERT INTO `jsh_log` VALUES ('6865', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:03:09', '0', '修改asdfad', '63');
INSERT INTO `jsh_log` VALUES ('6866', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-27 19:14:08', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6867', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-27 19:40:22', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6868', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-27 19:42:16', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6869', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-27 19:43:32', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6870', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:43:38', '0', '修改20', '63');
INSERT INTO `jsh_log` VALUES ('6871', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:43:47', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('6872', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:44:01', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6873', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:44:03', '0', '修改20', '63');
INSERT INTO `jsh_log` VALUES ('6874', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:44:06', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('6875', '63', '经手人', '127.0.0.1/127.0.0.1', '2021-01-27 19:44:29', '0', '修改快递费', '63');
INSERT INTO `jsh_log` VALUES ('6876', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:45:15', '0', '修改20', '63');
INSERT INTO `jsh_log` VALUES ('6877', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-27 19:45:17', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('6878', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:57:53', '0', '新增11111', '63');
INSERT INTO `jsh_log` VALUES ('6879', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:58:20', '0', '新增3333', '63');
INSERT INTO `jsh_log` VALUES ('6880', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:58:25', '0', '修改3333', '63');
INSERT INTO `jsh_log` VALUES ('6881', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:58:41', '0', '新增6666', '63');
INSERT INTO `jsh_log` VALUES ('6882', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:59:01', '0', '新增7777', '63');
INSERT INTO `jsh_log` VALUES ('6883', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:59:04', '0', '删除[7777]', '63');
INSERT INTO `jsh_log` VALUES ('6884', '63', '收支项目', '127.0.0.1/127.0.0.1', '2021-01-27 19:59:13', '0', '删除[3333][6666]', '63');
INSERT INTO `jsh_log` VALUES ('6885', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-28 13:27:09', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6886', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:02:09', '0', '新增ffffff', '63');
INSERT INTO `jsh_log` VALUES ('6887', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:02:28', '0', '修改ffffff', '63');
INSERT INTO `jsh_log` VALUES ('6888', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:02:37', '0', '修改ffffff', '63');
INSERT INTO `jsh_log` VALUES ('6889', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:11', '0', '修改ffffff', '63');
INSERT INTO `jsh_log` VALUES ('6890', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:18', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6891', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:23', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6892', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:29', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6893', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:35', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6894', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:51', '0', '修改仓库2', '63');
INSERT INTO `jsh_log` VALUES ('6895', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:05:57', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6896', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:06:05', '0', '修改仓库3', '63');
INSERT INTO `jsh_log` VALUES ('6897', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:06:12', '0', '修改fff', '63');
INSERT INTO `jsh_log` VALUES ('6898', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:21:32', '0', '修改仓库2', '63');
INSERT INTO `jsh_log` VALUES ('6899', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:21:41', '0', '修改仓库2', '63');
INSERT INTO `jsh_log` VALUES ('6900', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-28 15:50:07', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6901', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:50:26', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6902', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:51:48', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6903', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 15:54:08', '0', '修改15', '63');
INSERT INTO `jsh_log` VALUES ('6904', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:01:29', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6905', '63', '账户', '127.0.0.1/127.0.0.1', '2021-01-28 16:02:27', '0', '修改20', '63');
INSERT INTO `jsh_log` VALUES ('6906', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:02:36', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('6907', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:05:42', '0', '修改15', '63');
INSERT INTO `jsh_log` VALUES ('6908', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-28 16:11:16', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6909', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:11:37', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6910', '63', '用户', '127.0.0.1/127.0.0.1', '2021-01-28 16:13:38', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6911', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:13:48', '0', '修改15', '63');
INSERT INTO `jsh_log` VALUES ('6912', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:13:50', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6913', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:13:54', '0', '修改14', '63');
INSERT INTO `jsh_log` VALUES ('6914', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:14:04', '0', '修改仓库1', '63');
INSERT INTO `jsh_log` VALUES ('6915', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:14:11', '0', '修改仓库2', '63');
INSERT INTO `jsh_log` VALUES ('6916', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:14:38', '0', '修改18', '63');
INSERT INTO `jsh_log` VALUES ('6917', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:14:41', '0', '修改14', '63');
INSERT INTO `jsh_log` VALUES ('6918', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:15:19', '0', '删除[fff]', '63');
INSERT INTO `jsh_log` VALUES ('6919', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:16:47', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('6920', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-01-28 16:16:54', '0', '修改14', '63');
INSERT INTO `jsh_log` VALUES ('6921', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-04 22:32:34', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6922', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-04 22:36:06', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6923', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 09:36:42', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6924', '63', '角色', '127.0.0.1/127.0.0.1', '2021-02-05 09:41:44', '0', '新增dddd', '63');
INSERT INTO `jsh_log` VALUES ('6925', '63', '角色', '127.0.0.1/127.0.0.1', '2021-02-05 09:42:01', '0', '修改dddd1', '63');
INSERT INTO `jsh_log` VALUES ('6926', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 10:27:25', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6927', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 10:28:01', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6928', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 10:31:17', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6929', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 10:31:51', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6930', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:18:06', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('6931', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:18:31', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6932', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:20:26', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('6933', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:20:34', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6934', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:24:59', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6935', '63', '角色', '127.0.0.1/127.0.0.1', '2021-02-05 11:27:29', '0', '删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]删除[dddd1]', '63');
INSERT INTO `jsh_log` VALUES ('6936', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-05 11:27:45', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6937', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 11:37:06', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6938', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:28:11', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('6939', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:30:29', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('6940', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:31:13', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('6941', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:31:36', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('6942', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:33:10', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('6943', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:33:54', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('6944', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:36:14', '0', '删除[hhhhhhhh]', '63');
INSERT INTO `jsh_log` VALUES ('6945', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-07 21:41:21', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('6946', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-08 21:49:33', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6947', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-08 21:50:01', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('6948', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 21:50:11', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6949', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 21:50:22', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6950', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 21:50:26', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6951', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 21:50:32', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6952', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:07:19', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6953', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:07:25', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6954', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:07:50', '0', '修改自定义1', null);
INSERT INTO `jsh_log` VALUES ('6955', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:07:53', '0', '修改自定义1', null);
INSERT INTO `jsh_log` VALUES ('6956', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:07:57', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6957', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:08:03', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6958', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:08:17', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6959', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:17:49', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6960', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:17:52', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6961', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:18:24', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6962', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:19:00', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6963', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:19:04', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6964', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:19:52', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6965', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:19:55', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6966', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:20:43', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6967', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-02-08 22:20:50', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('6968', '120', '经手人', '127.0.0.1/127.0.0.1', '2021-02-08 22:40:48', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6969', '120', '经手人', '127.0.0.1/127.0.0.1', '2021-02-08 22:43:21', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6970', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-08 22:44:44', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6971', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-08 22:44:55', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6972', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-08 22:47:37', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6973', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-08 22:49:00', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('6974', '120', '仓库', '127.0.0.1/127.0.0.1', '2021-02-08 22:52:35', '0', '修改15', null);
INSERT INTO `jsh_log` VALUES ('6975', '120', '仓库', '127.0.0.1/127.0.0.1', '2021-02-08 22:52:37', '0', '修改14', null);
INSERT INTO `jsh_log` VALUES ('6976', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-09 09:16:17', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6977', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-09 16:08:43', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6978', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-09 23:08:07', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6979', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-12 20:34:09', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6980', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:20:38', '0', '修改机构1', '63');
INSERT INTO `jsh_log` VALUES ('6981', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:20:48', '0', '修改测试机构', '63');
INSERT INTO `jsh_log` VALUES ('6982', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:22:25', '0', '修改测试机构', '63');
INSERT INTO `jsh_log` VALUES ('6983', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:27:30', '0', '修改测试机构', '63');
INSERT INTO `jsh_log` VALUES ('6984', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:28:05', '0', '修改机构1', '63');
INSERT INTO `jsh_log` VALUES ('6985', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:28:16', '0', '修改机构2', '63');
INSERT INTO `jsh_log` VALUES ('6986', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:38:14', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6987', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:40:02', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6988', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:40:38', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6989', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:42:05', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6990', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 17:42:22', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6991', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-13 20:50:48', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('6992', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-13 20:51:10', '0', '删除[机构2]', '63');
INSERT INTO `jsh_log` VALUES ('6993', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:11:56', '0', '新增null', '63');
INSERT INTO `jsh_log` VALUES ('6994', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:14:09', '0', '删除[null]', '63');
INSERT INTO `jsh_log` VALUES ('6995', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:14:34', '0', '新增null', '63');
INSERT INTO `jsh_log` VALUES ('6996', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:22:38', '0', '修改个,盒(1:10)', '63');
INSERT INTO `jsh_log` VALUES ('6997', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:22:47', '0', '修改个,盒(1:15)', '63');
INSERT INTO `jsh_log` VALUES ('6998', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:24:19', '0', '新增盒,箱(1:8)', '63');
INSERT INTO `jsh_log` VALUES ('6999', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:28:45', '0', '删除[盒,箱(1:8)]', '63');
INSERT INTO `jsh_log` VALUES ('7000', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:29:46', '0', '修改盒a,箱(1:8)', '63');
INSERT INTO `jsh_log` VALUES ('7001', '63', '计量单位', '127.0.0.1/127.0.0.1', '2021-02-14 20:29:52', '0', '修改盒,箱(1:8)', '63');
INSERT INTO `jsh_log` VALUES ('7002', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-14 20:53:08', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7003', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 17:36:36', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7004', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 17:44:26', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7005', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 17:51:31', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7006', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 17:54:45', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7007', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 17:59:41', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7008', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-15 20:41:49', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7009', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 21:44:38', '0', '新增fasdf', '63');
INSERT INTO `jsh_log` VALUES ('7010', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 21:48:31', '0', '新增fdafsd', '63');
INSERT INTO `jsh_log` VALUES ('7011', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 21:50:19', '0', '新增gqweqw', '63');
INSERT INTO `jsh_log` VALUES ('7012', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 21:55:17', '0', '新增eeee', '63');
INSERT INTO `jsh_log` VALUES ('7013', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:01:26', '0', '删除[eeee]', '63');
INSERT INTO `jsh_log` VALUES ('7014', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:18:23', '0', '修改机构2', '63');
INSERT INTO `jsh_log` VALUES ('7015', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:18:30', '0', '修改机构2', '63');
INSERT INTO `jsh_log` VALUES ('7016', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:41:36', '0', '新增fasdf2', '63');
INSERT INTO `jsh_log` VALUES ('7017', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:49:23', '0', '修改fasdf2', '63');
INSERT INTO `jsh_log` VALUES ('7018', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:51:47', '0', '删除[fasdf2]', '63');
INSERT INTO `jsh_log` VALUES ('7019', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:54:15', '0', '新增rwetw', '63');
INSERT INTO `jsh_log` VALUES ('7020', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-15 22:54:22', '0', '修改rwetw', '63');
INSERT INTO `jsh_log` VALUES ('7021', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-16 20:43:43', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7022', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:44:00', '0', '新增adsfa', '63');
INSERT INTO `jsh_log` VALUES ('7023', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:44:37', '0', '删除[adsfa]', '63');
INSERT INTO `jsh_log` VALUES ('7024', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:50:01', '0', '新增tytyty', '63');
INSERT INTO `jsh_log` VALUES ('7025', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:50:10', '0', '修改tytyty', '63');
INSERT INTO `jsh_log` VALUES ('7026', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:50:18', '0', '修改tytyty', '63');
INSERT INTO `jsh_log` VALUES ('7027', '63', '机构', '127.0.0.1/127.0.0.1', '2021-02-16 20:50:52', '0', '新增trertwert', '63');
INSERT INTO `jsh_log` VALUES ('7028', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-16 22:07:01', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7029', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-16 22:07:17', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7030', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:34:09', '0', '新增afads', '63');
INSERT INTO `jsh_log` VALUES ('7031', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:39:26', '0', '删除[afads]', '63');
INSERT INTO `jsh_log` VALUES ('7032', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:39:44', '0', '新增fdfadsf', '63');
INSERT INTO `jsh_log` VALUES ('7033', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:39:55', '0', '删除[fdfadsf]', '63');
INSERT INTO `jsh_log` VALUES ('7034', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:40:23', '0', '新增gsfddsfg', '63');
INSERT INTO `jsh_log` VALUES ('7035', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:40:34', '0', '新增fsgsdfgs', '63');
INSERT INTO `jsh_log` VALUES ('7036', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:40:41', '0', '修改eeeeee', '63');
INSERT INTO `jsh_log` VALUES ('7037', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:40:46', '0', '修改eeeeee', '63');
INSERT INTO `jsh_log` VALUES ('7038', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:41:05', '0', '新增gertwer', '63');
INSERT INTO `jsh_log` VALUES ('7039', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:45:30', '0', '删除[gertwer]', '63');
INSERT INTO `jsh_log` VALUES ('7040', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:45:43', '0', '新增bbbb', '63');
INSERT INTO `jsh_log` VALUES ('7041', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:45:47', '0', '修改bbbb', '63');
INSERT INTO `jsh_log` VALUES ('7042', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-16 22:45:52', '0', '修改bbbb111', '63');
INSERT INTO `jsh_log` VALUES ('7043', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-17 14:56:03', '0', '修改gsfddsfg', '63');
INSERT INTO `jsh_log` VALUES ('7044', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-17 14:56:09', '0', '删除[eeeeee]', '63');
INSERT INTO `jsh_log` VALUES ('7045', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-17 14:56:21', '0', '新增rrrr', '63');
INSERT INTO `jsh_log` VALUES ('7046', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-17 14:56:31', '0', '修改rrrr111111', '63');
INSERT INTO `jsh_log` VALUES ('7047', '63', '商品类型', '127.0.0.1/127.0.0.1', '2021-02-17 15:11:35', '0', '修改目录1', '63');
INSERT INTO `jsh_log` VALUES ('7048', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-17 21:55:59', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7049', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-17 22:00:57', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7050', '140', '用户', '127.0.0.1/127.0.0.1', '2021-02-17 23:24:50', '0', '登录opopo', '140');
INSERT INTO `jsh_log` VALUES ('7051', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-17 23:31:05', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7052', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-18 21:06:41', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7053', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-18 21:09:20', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7054', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-18 21:17:42', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7055', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-19 22:30:04', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7056', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 20:42:42', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7057', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 21:11:14', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7058', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 21:55:06', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7059', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 22:02:38', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7060', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 22:57:35', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7061', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-20 23:27:06', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7062', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-22 22:08:38', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7063', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:26:39', '0', '新增sssss', '63');
INSERT INTO `jsh_log` VALUES ('7064', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:30:31', '0', '新增adsfadf', '63');
INSERT INTO `jsh_log` VALUES ('7065', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:31:52', '0', '删除[sssss]', '63');
INSERT INTO `jsh_log` VALUES ('7066', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:31:56', '0', '新增afsdfasdf', '63');
INSERT INTO `jsh_log` VALUES ('7067', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:32:17', '0', '删除[afsdfasdf]', '63');
INSERT INTO `jsh_log` VALUES ('7068', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:32:25', '0', '新增ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7069', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:37:12', '0', '修改供应商3', '63');
INSERT INTO `jsh_log` VALUES ('7070', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:38:10', '0', '修改供应商5', '63');
INSERT INTO `jsh_log` VALUES ('7071', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:38:24', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7072', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-22 23:38:52', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7073', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:51:48', '0', '修改供应商3', '63');
INSERT INTO `jsh_log` VALUES ('7074', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:51:57', '0', '修改供应商3', '63');
INSERT INTO `jsh_log` VALUES ('7075', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:52:10', '0', '修改供应商1', '63');
INSERT INTO `jsh_log` VALUES ('7076', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:54:54', '0', '修改供应商5', '63');
INSERT INTO `jsh_log` VALUES ('7077', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:55:09', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7078', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:55:16', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7079', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:55:35', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7080', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 21:56:45', '0', '删除[ffffddddd]', '63');
INSERT INTO `jsh_log` VALUES ('7081', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:06:01', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7082', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-23 22:11:24', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7083', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:14:03', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7084', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:17:21', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7085', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:18:13', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7086', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:20:45', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7087', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:21:00', '0', '修改供应商5', '63');
INSERT INTO `jsh_log` VALUES ('7088', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:21:37', '0', '修改供应商1', '63');
INSERT INTO `jsh_log` VALUES ('7089', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:22:05', '0', '修改供应商5', '63');
INSERT INTO `jsh_log` VALUES ('7090', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:47:51', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7091', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:47:57', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7092', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:49:39', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7093', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:49:52', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7094', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:50:09', '0', '修改ffffddddd', '63');
INSERT INTO `jsh_log` VALUES ('7095', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 22:53:07', '0', '删除[ffffddddd]', '63');
INSERT INTO `jsh_log` VALUES ('7096', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 23:02:01', '0', '新增3123123', '63');
INSERT INTO `jsh_log` VALUES ('7097', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 23:02:13', '0', '修改3123123', '63');
INSERT INTO `jsh_log` VALUES ('7098', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 23:02:22', '0', '修改3123123', '63');
INSERT INTO `jsh_log` VALUES ('7099', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-02-23 23:05:52', '0', '修改15', '63');
INSERT INTO `jsh_log` VALUES ('7100', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-02-23 23:05:54', '0', '修改14', '63');
INSERT INTO `jsh_log` VALUES ('7101', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-23 23:17:41', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7102', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-23 23:29:20', '0', '修改3123123', '63');
INSERT INTO `jsh_log` VALUES ('7103', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-23 23:36:02', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7104', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:02:12', '0', '删除[dfadsf]', '63');
INSERT INTO `jsh_log` VALUES ('7105', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:02:39', '0', '删除[fasdfadf]', '63');
INSERT INTO `jsh_log` VALUES ('7106', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:02:45', '0', '删除[gggg]', '63');
INSERT INTO `jsh_log` VALUES ('7107', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:23:42', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7108', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:42:56', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7109', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-24 23:54:10', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7110', '63', '账户', '127.0.0.1/127.0.0.1', '2021-02-25 00:02:22', '0', '修改asdfad', '63');
INSERT INTO `jsh_log` VALUES ('7111', '63', '账户', '127.0.0.1/127.0.0.1', '2021-02-25 00:02:28', '0', '修改17', '63');
INSERT INTO `jsh_log` VALUES ('7112', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-02-25 00:04:44', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7113', '120', '用户', '127.0.0.1/127.0.0.1', '2021-02-25 00:07:43', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7114', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-25 00:08:20', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('7115', '120', '功能', '127.0.0.1/127.0.0.1', '2021-02-25 00:08:27', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('7116', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-25 00:08:49', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7117', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-25 22:54:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7118', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-26 20:30:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7119', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 21:47:59', '0', '修改588,', '63');
INSERT INTO `jsh_log` VALUES ('7120', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 21:48:15', '0', '修改588,', '63');
INSERT INTO `jsh_log` VALUES ('7121', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 21:50:19', '0', '修改588,587,586,', '63');
INSERT INTO `jsh_log` VALUES ('7122', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 21:50:28', '0', '修改586,587,588,', '63');
INSERT INTO `jsh_log` VALUES ('7123', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 21:59:42', '0', '修改579,', '63');
INSERT INTO `jsh_log` VALUES ('7124', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:06:52', '0', '修改71,', '63');
INSERT INTO `jsh_log` VALUES ('7125', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:06:58', '0', '修改59,71,', '63');
INSERT INTO `jsh_log` VALUES ('7126', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:07:03', '0', '修改71,59,58,', '63');
INSERT INTO `jsh_log` VALUES ('7127', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:07:10', '0', '修改71,59,58,', '63');
INSERT INTO `jsh_log` VALUES ('7128', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:09:34', '0', '修改78,74,', '63');
INSERT INTO `jsh_log` VALUES ('7129', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:09:45', '0', '修改78,74,68,', '63');
INSERT INTO `jsh_log` VALUES ('7130', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:09:52', '0', '修改79,60,', '63');
INSERT INTO `jsh_log` VALUES ('7131', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:09:56', '0', '修改79,60,', '63');
INSERT INTO `jsh_log` VALUES ('7132', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 22:14:04', '0', '修改588,', '63');
INSERT INTO `jsh_log` VALUES ('7133', '63', '商品', '127.0.0.1/127.0.0.1', '2021-02-26 22:14:11', '0', '修改588,', '63');
INSERT INTO `jsh_log` VALUES ('7134', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:19:57', '0', '删除[ffffddddd]', '63');
INSERT INTO `jsh_log` VALUES ('7135', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-26 22:41:01', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7136', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:46:03', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7137', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:47:45', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7138', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:49:52', '0', '删除[abcd]', '63');
INSERT INTO `jsh_log` VALUES ('7139', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:55:24', '0', '删除[abcd]', '63');
INSERT INTO `jsh_log` VALUES ('7140', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:55:29', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7141', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:57:03', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7142', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 22:57:40', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7143', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:01:32', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7144', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:01:45', '0', '删除[abcd][abcd][abcd][abcd]', '63');
INSERT INTO `jsh_log` VALUES ('7145', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:01:54', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7146', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:04:08', '0', '删除[abcd]', '63');
INSERT INTO `jsh_log` VALUES ('7147', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:04:12', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7148', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:07:00', '0', '删除[abcd]', '63');
INSERT INTO `jsh_log` VALUES ('7149', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:07:07', '0', '导入1条', '63');
INSERT INTO `jsh_log` VALUES ('7150', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:07:24', '0', '修改ffffdddd', '63');
INSERT INTO `jsh_log` VALUES ('7151', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:46:04', '0', '新增gsdfadf', '63');
INSERT INTO `jsh_log` VALUES ('7152', '63', '商家', '127.0.0.1/127.0.0.1', '2021-02-26 23:46:15', '0', '修改89,', '63');
INSERT INTO `jsh_log` VALUES ('7153', '143', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:08:37', '0', '登录15962969210', '143');
INSERT INTO `jsh_log` VALUES ('7154', '143', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:08:49', '0', '登录15962969210', '143');
INSERT INTO `jsh_log` VALUES ('7155', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:09:07', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7156', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:09:50', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7157', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:10:08', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7158', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:13:48', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7159', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:14:20', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7160', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:14:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7161', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:15:17', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7162', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:28:16', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7163', '143', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:28:46', '0', '登录15962969210', '143');
INSERT INTO `jsh_log` VALUES ('7164', '63', '用户', '127.0.0.1/127.0.0.1', '2021-02-27 00:28:58', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7165', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-01 20:47:57', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7166', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-02 22:08:20', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7167', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-02 22:48:05', '0', '新增asdfads', '63');
INSERT INTO `jsh_log` VALUES ('7168', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-02 23:43:03', '0', '修改商品200', '63');
INSERT INTO `jsh_log` VALUES ('7169', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-02 23:43:19', '0', '修改商品200', '63');
INSERT INTO `jsh_log` VALUES ('7170', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:05:15', '0', '新增adsfads', '63');
INSERT INTO `jsh_log` VALUES ('7171', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:52:09', '0', '新增ggggg', '63');
INSERT INTO `jsh_log` VALUES ('7172', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:52:46', '0', '新增afdfadf', '63');
INSERT INTO `jsh_log` VALUES ('7173', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:54:10', '0', '新增afdfadfqerqerq', '63');
INSERT INTO `jsh_log` VALUES ('7174', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:57:29', '0', '新增fada213', '63');
INSERT INTO `jsh_log` VALUES ('7175', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 00:58:46', '0', '新增fsdfasd', '63');
INSERT INTO `jsh_log` VALUES ('7176', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:01:00', '0', '新增fadasdf', '63');
INSERT INTO `jsh_log` VALUES ('7177', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:07', '0', '修改fadasdfeqweqw11', '63');
INSERT INTO `jsh_log` VALUES ('7178', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:13', '0', '修改fadasdfe33', '63');
INSERT INTO `jsh_log` VALUES ('7179', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:17', '0', '删除[fsdfasd]', '63');
INSERT INTO `jsh_log` VALUES ('7180', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:21', '0', '删除[fada213]', '63');
INSERT INTO `jsh_log` VALUES ('7181', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:28', '0', '删除[fadasdfe33]', '63');
INSERT INTO `jsh_log` VALUES ('7182', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:45', '0', '新增afdsfa123123', '63');
INSERT INTO `jsh_log` VALUES ('7183', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:51', '0', '修改afdsfa123123', '63');
INSERT INTO `jsh_log` VALUES ('7184', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:05:58', '0', '修改afdsfa123123', '63');
INSERT INTO `jsh_log` VALUES ('7185', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:06:03', '0', '修改afdsfa123123', '63');
INSERT INTO `jsh_log` VALUES ('7186', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 01:06:18', '0', '修改afdsfa123123', '63');
INSERT INTO `jsh_log` VALUES ('7187', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 21:37:47', '0', '新增商品gggg', '63');
INSERT INTO `jsh_log` VALUES ('7188', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 21:38:17', '0', '修改商品gggg', '63');
INSERT INTO `jsh_log` VALUES ('7189', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 21:38:24', '0', '修改商品gggg', '63');
INSERT INTO `jsh_log` VALUES ('7190', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 21:41:11', '0', '修改商品gggg', '63');
INSERT INTO `jsh_log` VALUES ('7191', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 21:41:45', '0', '新增fasdfadf', '63');
INSERT INTO `jsh_log` VALUES ('7192', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-03 22:11:34', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7193', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:15:53', '0', '新增e123123', '63');
INSERT INTO `jsh_log` VALUES ('7194', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:16:41', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7195', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:18:20', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7196', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:18:31', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7197', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:18:53', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7198', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:19:08', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7199', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:20:10', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7200', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:20:41', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7201', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:21:39', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7202', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:21:47', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7203', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:24:12', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7204', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:29:15', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7205', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:31:11', '0', '新增fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7206', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 22:59:53', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7207', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-03 23:01:47', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7208', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:09:46', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7209', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:10:07', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7210', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:11:54', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7211', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:12:41', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7212', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:13:27', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7213', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 00:19:34', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7214', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 00:21:15', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7215', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 21:47:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7216', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-04 22:43:28', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7217', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:10:03', '0', '导入2条', '63');
INSERT INTO `jsh_log` VALUES ('7218', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:14:25', '0', '删除[啤酒test]', '63');
INSERT INTO `jsh_log` VALUES ('7219', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:14:28', '0', '删除[可乐test]', '63');
INSERT INTO `jsh_log` VALUES ('7220', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:14:40', '0', '导入2条', '63');
INSERT INTO `jsh_log` VALUES ('7221', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:41:44', '0', '修改啤酒test', '63');
INSERT INTO `jsh_log` VALUES ('7222', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:41:51', '0', '修改啤酒test', '63');
INSERT INTO `jsh_log` VALUES ('7223', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:53:00', '0', '删除[啤酒test]', '63');
INSERT INTO `jsh_log` VALUES ('7224', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:53:02', '0', '删除[可乐test]', '63');
INSERT INTO `jsh_log` VALUES ('7225', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-04 23:53:09', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7226', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:29:54', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7227', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:41:31', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7228', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:42:43', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7229', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:42:55', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7230', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:43:09', '0', '修改e123123', '63');
INSERT INTO `jsh_log` VALUES ('7231', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:44:22', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7232', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 00:44:32', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7233', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 01:01:37', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7234', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 01:27:53', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7235', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 01:28:17', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7236', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 01:28:44', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7237', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 18:58:18', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7238', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:22:31', '0', '新增fasdfad商品1', '63');
INSERT INTO `jsh_log` VALUES ('7239', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:22:45', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7240', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:22:58', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7241', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:26:49', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7242', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:26:57', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7243', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:27:17', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7244', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:30:25', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7245', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:30:38', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7246', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:32:01', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7247', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:32:27', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7248', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:32:46', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7249', '63', '商品', '127.0.0.1/127.0.0.1', '2021-03-05 19:33:08', '0', '修改fasdfad商品', '63');
INSERT INTO `jsh_log` VALUES ('7250', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 21:47:05', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7251', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 21:47:29', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7252', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-03-07 22:23:49', '0', '修改仓库1111', '63');
INSERT INTO `jsh_log` VALUES ('7253', '63', '仓库', '127.0.0.1/127.0.0.1', '2021-03-07 22:24:03', '0', '修改仓库1', '63');
INSERT INTO `jsh_log` VALUES ('7254', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 22:41:09', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7255', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 23:18:22', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7256', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 23:34:58', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7257', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 23:35:44', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7258', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-07 23:36:21', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7259', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-08 23:54:19', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7260', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-09 23:55:40', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7261', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-10 21:59:01', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7262', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-10 22:09:04', '0', '新增asd1231231', '63');
INSERT INTO `jsh_log` VALUES ('7263', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-10 22:16:26', '0', '删除230', '63');
INSERT INTO `jsh_log` VALUES ('7264', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-10 22:17:24', '0', '新增41341341', '63');
INSERT INTO `jsh_log` VALUES ('7265', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 00:20:16', '0', '新增CGDD00000000376', '63');
INSERT INTO `jsh_log` VALUES ('7266', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 21:53:32', '0', '新增CGDD00000000379', '63');
INSERT INTO `jsh_log` VALUES ('7267', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-11 22:04:00', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7268', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 23:38:03', '0', '新增CGDD00000000401', '63');
INSERT INTO `jsh_log` VALUES ('7269', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 23:40:36', '0', '新增CGDD00000000403', '63');
INSERT INTO `jsh_log` VALUES ('7270', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 23:44:02', '0', '新增CGDD00000000404', '63');
INSERT INTO `jsh_log` VALUES ('7271', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 23:47:01', '0', '新增CGDD00000000405', '63');
INSERT INTO `jsh_log` VALUES ('7272', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-11 23:49:41', '0', '新增CGDD00000000407', '63');
INSERT INTO `jsh_log` VALUES ('7273', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:03:03', '0', '新增CGDD00000000409', '63');
INSERT INTO `jsh_log` VALUES ('7274', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:03:42', '0', '删除CGDD00000000407', '63');
INSERT INTO `jsh_log` VALUES ('7275', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:03:47', '0', '删除CGDD00000000405', '63');
INSERT INTO `jsh_log` VALUES ('7276', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:04:19', '0', '删除CGDD00000000407', '63');
INSERT INTO `jsh_log` VALUES ('7277', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:04:19', '0', '删除CGDD00000000404', '63');
INSERT INTO `jsh_log` VALUES ('7278', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 00:04:19', '0', '删除CGDD00000000403', '63');
INSERT INTO `jsh_log` VALUES ('7279', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 21:56:59', '0', '新增CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7280', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-12 22:09:46', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7281', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 23:01:30', '0', '修改CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7282', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 23:01:49', '0', '修改CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7283', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-12 23:02:00', '0', '修改CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7284', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 00:38:57', '0', '修改CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7285', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 00:43:34', '0', '修改CGDD00000000428', '63');
INSERT INTO `jsh_log` VALUES ('7286', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 00:50:37', '0', '修改CGDD00000000401', '63');
INSERT INTO `jsh_log` VALUES ('7287', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 00:51:18', '0', '新增CGDD00000000449', '63');
INSERT INTO `jsh_log` VALUES ('7288', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:00:15', '0', '新增CGDD00000000454', '63');
INSERT INTO `jsh_log` VALUES ('7289', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:01:28', '0', '修改CGDD00000000454', '63');
INSERT INTO `jsh_log` VALUES ('7290', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:01:40', '0', '删除CGDD00000000379', '63');
INSERT INTO `jsh_log` VALUES ('7291', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:01:40', '0', '删除CGDD00000000376', '63');
INSERT INTO `jsh_log` VALUES ('7292', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:01:40', '0', '删除41341341', '63');
INSERT INTO `jsh_log` VALUES ('7293', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:02:10', '0', '新增CGDD00000000456', '63');
INSERT INTO `jsh_log` VALUES ('7294', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:04:25', '0', '修改CGDD00000000456', '63');
INSERT INTO `jsh_log` VALUES ('7295', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:04:40', '0', '新增CGDD00000000461', '63');
INSERT INTO `jsh_log` VALUES ('7296', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:05:40', '0', '修改CGDD00000000461', '63');
INSERT INTO `jsh_log` VALUES ('7297', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:06:15', '0', '修改CGDD00000000461', '63');
INSERT INTO `jsh_log` VALUES ('7298', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:07:03', '0', '修改CGDD00000000461', '63');
INSERT INTO `jsh_log` VALUES ('7299', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 01:09:14', '0', '新增CGDD00000000465', '63');
INSERT INTO `jsh_log` VALUES ('7300', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 15:59:22', '0', '新增CGDD00000000467', '63');
INSERT INTO `jsh_log` VALUES ('7301', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-13 16:00:24', '0', '修改CGDD00000000467', '63');
INSERT INTO `jsh_log` VALUES ('7302', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-14 20:29:22', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7303', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 20:54:27', '0', '修改CGDD00000000467', '63');
INSERT INTO `jsh_log` VALUES ('7304', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 20:54:53', '0', '修改CGDD00000000467', '63');
INSERT INTO `jsh_log` VALUES ('7305', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 20:55:24', '0', '修改CGDD00000000465', '63');
INSERT INTO `jsh_log` VALUES ('7306', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 20:56:53', '0', '修改CGDD00000000467', '63');
INSERT INTO `jsh_log` VALUES ('7307', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 20:56:58', '0', '修改CGRK00000000352', '63');
INSERT INTO `jsh_log` VALUES ('7308', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-14 22:13:52', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7309', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-14 22:41:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7310', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 23:14:05', '0', '新增CGDD00000000469', '63');
INSERT INTO `jsh_log` VALUES ('7311', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 23:14:11', '0', '修改CGDD00000000469', '63');
INSERT INTO `jsh_log` VALUES ('7312', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 23:14:27', '0', '修改CGDD00000000469', '63');
INSERT INTO `jsh_log` VALUES ('7313', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-14 23:51:58', '0', '新增CGDD00000000484', '63');
INSERT INTO `jsh_log` VALUES ('7314', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 00:37:40', '0', '新增CGDD00000000508', '63');
INSERT INTO `jsh_log` VALUES ('7315', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 00:40:23', '0', '修改CGDD00000000508', '63');
INSERT INTO `jsh_log` VALUES ('7316', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-15 23:25:05', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7317', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:26:21', '0', '新增CGTH00000000509', '63');
INSERT INTO `jsh_log` VALUES ('7318', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:27:02', '0', '新增CGDD00000000511', '63');
INSERT INTO `jsh_log` VALUES ('7319', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:35:00', '0', '新增XSDD00000000515', '63');
INSERT INTO `jsh_log` VALUES ('7320', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:36:44', '0', '新增XSCK00000000516', '63');
INSERT INTO `jsh_log` VALUES ('7321', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:37:14', '0', '修改XSCK00000000516', '63');
INSERT INTO `jsh_log` VALUES ('7322', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:39:57', '0', '新增XSTH00000000517', '63');
INSERT INTO `jsh_log` VALUES ('7323', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:41:48', '0', '删除XSCK00000000516', '63');
INSERT INTO `jsh_log` VALUES ('7324', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:42:08', '0', '新增XSCK00000000519', '63');
INSERT INTO `jsh_log` VALUES ('7325', '63', '单据', '127.0.0.1/127.0.0.1', '2021-03-15 23:42:33', '0', '修改XSTH00000000517', '63');
INSERT INTO `jsh_log` VALUES ('7326', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-16 23:18:43', '0', '新增SR00000000546', '63');
INSERT INTO `jsh_log` VALUES ('7327', '63', '财务', '127.0.0.1/127.0.0.1', '2021-03-16 23:19:03', '0', '删除[SR20200913184412]', '63');
INSERT INTO `jsh_log` VALUES ('7328', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-16 23:21:47', '0', '新增SR00000000547', '63');
INSERT INTO `jsh_log` VALUES ('7329', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-16 23:30:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7330', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-16 23:34:53', '0', '新增SR00000000548', '63');
INSERT INTO `jsh_log` VALUES ('7331', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:06:31', '0', '新增SR00000000550', '63');
INSERT INTO `jsh_log` VALUES ('7332', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:18:02', '0', '新增SR00000000551', '63');
INSERT INTO `jsh_log` VALUES ('7333', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:20:06', '0', '新增SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7334', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:20:34', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7335', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:20:44', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7336', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:20:57', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7337', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:21:56', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7338', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:22:09', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7339', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:22:17', '0', '修改SR00000000552', '63');
INSERT INTO `jsh_log` VALUES ('7340', '63', '财务', '127.0.0.1/127.0.0.1', '2021-03-17 00:23:07', '0', '删除[SR00000000551]', '63');
INSERT INTO `jsh_log` VALUES ('7341', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 00:23:37', '0', '修改SR00000000548', '63');
INSERT INTO `jsh_log` VALUES ('7342', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 21:29:13', '0', '修改ZC20191228121854', '63');
INSERT INTO `jsh_log` VALUES ('7343', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 21:29:21', '0', '修改ZC20191228121854', '63');
INSERT INTO `jsh_log` VALUES ('7344', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 21:58:52', '0', '新增SR00000000572', '63');
INSERT INTO `jsh_log` VALUES ('7345', '63', '财务', '127.0.0.1/127.0.0.1', '2021-03-17 21:59:27', '0', '删除[SR00000000572]', '63');
INSERT INTO `jsh_log` VALUES ('7346', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:07:25', '0', '新增SK00000000580', '63');
INSERT INTO `jsh_log` VALUES ('7347', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:07:40', '0', '修改SK00000000580', '63');
INSERT INTO `jsh_log` VALUES ('7348', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:18:54', '0', '新增FK00000000591', '63');
INSERT INTO `jsh_log` VALUES ('7349', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:19:10', '0', '修改FK00000000591', '63');
INSERT INTO `jsh_log` VALUES ('7350', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:19:16', '0', '修改FK00000000591', '63');
INSERT INTO `jsh_log` VALUES ('7351', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:19:26', '0', '修改FK00000000591', '63');
INSERT INTO `jsh_log` VALUES ('7352', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:35:29', '0', '新增SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7353', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:35:50', '0', '修改SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7354', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:36:32', '0', '修改SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7355', '63', '经手人', '127.0.0.1/127.0.0.1', '2021-03-17 22:36:55', '0', '新增小曹', '63');
INSERT INTO `jsh_log` VALUES ('7356', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:37:15', '0', '修改SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7357', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:37:45', '0', '修改SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7358', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:38:08', '0', '修改SYF00000000593', '63');
INSERT INTO `jsh_log` VALUES ('7359', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:53:52', '0', '新增ZZ00000000601', '63');
INSERT INTO `jsh_log` VALUES ('7360', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:55:12', '0', '修改ZZ00000000601', '63');
INSERT INTO `jsh_log` VALUES ('7361', '63', '财务单据', '127.0.0.1/127.0.0.1', '2021-03-17 22:57:33', '0', '修改ZZ00000000601', '63');
INSERT INTO `jsh_log` VALUES ('7362', '120', '用户', '127.0.0.1/127.0.0.1', '2021-03-17 23:17:32', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7363', '120', '功能', '127.0.0.1/127.0.0.1', '2021-03-17 23:18:31', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('7364', '120', '功能', '127.0.0.1/127.0.0.1', '2021-03-17 23:18:37', '0', '修改零售管理', null);
INSERT INTO `jsh_log` VALUES ('7365', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-17 23:22:11', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7366', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-17 23:54:36', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7367', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:00:54', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7368', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:01:07', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7369', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:01:11', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7370', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:01:14', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7371', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:01:19', '0', '修改公司1', '63');
INSERT INTO `jsh_log` VALUES ('7372', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:22:17', '0', '修改公司11', '63');
INSERT INTO `jsh_log` VALUES ('7373', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:22:21', '0', '修改公司1122', '63');
INSERT INTO `jsh_log` VALUES ('7374', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:22:30', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7375', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:29:03', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7376', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:30:37', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7377', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:34:20', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7378', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:36:03', '0', '删除9', '63');
INSERT INTO `jsh_log` VALUES ('7379', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:12', '0', '新增公司test1231323', '63');
INSERT INTO `jsh_log` VALUES ('7380', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:23', '0', '修改公司test1231323', '63');
INSERT INTO `jsh_log` VALUES ('7381', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:32', '0', '修改公司test1231323', '63');
INSERT INTO `jsh_log` VALUES ('7382', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:38', '0', '删除10', '63');
INSERT INTO `jsh_log` VALUES ('7383', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:43', '0', '新增公司1222', '63');
INSERT INTO `jsh_log` VALUES ('7384', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:43:52', '0', '修改公司1222', '63');
INSERT INTO `jsh_log` VALUES ('7385', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:44:09', '0', '修改公司1222', '63');
INSERT INTO `jsh_log` VALUES ('7386', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 00:44:15', '0', '修改公司1222', '63');
INSERT INTO `jsh_log` VALUES ('7387', '63', '经手人', '127.0.0.1/127.0.0.1', '2021-03-18 00:45:30', '0', '修改小夏', '63');
INSERT INTO `jsh_log` VALUES ('7388', '63', '系统配置', '127.0.0.1/127.0.0.1', '2021-03-18 21:27:06', '0', '修改公司1222', '63');
INSERT INTO `jsh_log` VALUES ('7389', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 21:34:54', '0', '删除[gggg]', '63');
INSERT INTO `jsh_log` VALUES ('7390', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 21:49:35', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7391', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:00:14', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7392', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:06:06', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7393', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:07:23', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7394', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:10:10', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7395', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:24:25', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7396', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:29:33', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7397', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:31:13', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7398', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:31:39', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7399', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:31:44', '0', '修改133', '63');
INSERT INTO `jsh_log` VALUES ('7400', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:31:55', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7401', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:32:04', '0', '修改133', '63');
INSERT INTO `jsh_log` VALUES ('7402', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:32:10', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7403', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:32:46', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7404', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:32:54', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7405', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:03', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('7406', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:10', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7407', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:19', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('7408', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:30', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7409', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:37', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7410', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:33:44', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7411', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 22:54:02', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7412', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:01:34', '0', '删除[fasdfadf]', '63');
INSERT INTO `jsh_log` VALUES ('7413', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:10:41', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7414', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:11:31', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7415', '131', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:12:16', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('7416', '131', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:12:31', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('7417', '131', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:12:48', '0', '登录test123', '63');
INSERT INTO `jsh_log` VALUES ('7418', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:13:25', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7419', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:15:36', '0', '修改131', '63');
INSERT INTO `jsh_log` VALUES ('7420', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:41:19', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7421', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:51:39', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7422', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:52:02', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7423', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:59:50', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7424', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-18 23:59:55', '0', '修改134', '63');
INSERT INTO `jsh_log` VALUES ('7426', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:00:40', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('7427', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:01:04', '0', '修改144', '63');
INSERT INTO `jsh_log` VALUES ('7428', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:03:44', '0', '新增', '63');
INSERT INTO `jsh_log` VALUES ('7429', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:08:07', '0', '修改144', '63');
INSERT INTO `jsh_log` VALUES ('7430', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:09:23', '0', '修改135', '63');
INSERT INTO `jsh_log` VALUES ('7431', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:11:28', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7432', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:11:32', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7433', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:11:40', '0', '修改63', '63');
INSERT INTO `jsh_log` VALUES ('7434', '120', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 00:16:05', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7435', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-03-19 00:17:32', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('7436', '120', '商品属性', '127.0.0.1/127.0.0.1', '2021-03-19 00:17:34', '0', '修改制造商', null);
INSERT INTO `jsh_log` VALUES ('7437', '120', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 18:41:17', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7438', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 19:34:36', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7439', '120', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 19:38:35', '0', '登录admin', '0');
INSERT INTO `jsh_log` VALUES ('7440', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-19 19:44:54', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7441', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-21 20:26:16', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7442', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-21 22:31:56', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7443', '63', '用户', '127.0.0.1/127.0.0.1', '2021-03-21 22:41:37', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7444', '63', '用户', '127.0.0.1', '2021-03-21 23:25:41', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7445', '63', '用户', '127.0.0.1', '2021-03-21 23:46:13', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7446', '63', '用户', '127.0.0.1', '2021-03-22 16:56:42', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7447', '63', '用户', '127.0.0.1', '2021-03-22 16:57:16', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7448', '63', '用户', '127.0.0.1', '2021-03-22 17:04:33', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7449', '63', '用户', '127.0.0.1', '2021-03-22 20:16:44', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7450', '63', '用户', '127.0.0.1', '2021-03-22 21:36:40', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7451', '63', '用户', '127.0.0.1', '2021-03-22 22:10:29', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7452', '63', '用户', '127.0.0.1', '2021-03-22 22:34:31', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7453', '63', '用户', '127.0.0.1', '2021-03-22 22:39:13', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7454', '63', '用户', '127.0.0.1', '2021-03-22 22:40:10', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7455', '63', '用户', '127.0.0.1', '2021-03-22 22:40:52', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7456', '63', '用户', '127.0.0.1', '2021-03-22 22:44:51', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7457', '63', '用户', '127.0.0.1', '2021-03-22 23:03:30', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7458', '63', '用户', '127.0.0.1', '2021-03-22 23:12:27', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7459', '63', '用户', '127.0.0.1', '2021-03-22 23:27:27', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7460', '63', '用户', '127.0.0.1', '2021-03-22 23:28:19', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7461', '63', '用户', '127.0.0.1', '2021-03-22 23:28:53', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7462', '63', '用户', '127.0.0.1', '2021-03-22 23:32:57', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7463', '63', '用户', '127.0.0.1', '2021-03-22 23:52:14', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7464', '63', '用户', '127.0.0.1', '2021-03-23 00:10:10', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7465', '63', '用户', '127.0.0.1', '2021-03-23 00:12:28', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7466', '63', '用户', '127.0.0.1', '2021-03-23 00:21:03', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7467', '63', '用户', '127.0.0.1', '2021-03-23 00:22:45', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7468', '63', '用户', '127.0.0.1', '2021-03-23 00:28:06', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7469', '63', '用户', '127.0.0.1', '2021-03-23 23:37:27', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7470', '63', '用户', '127.0.0.1', '2021-03-23 23:37:59', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7471', '63', '用户', '127.0.0.1', '2021-03-24 00:04:26', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7472', '63', '用户', '127.0.0.1', '2021-03-24 00:21:38', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7473', '63', '用户', '127.0.0.1', '2021-03-24 00:34:26', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7474', '63', '用户', '127.0.0.1', '2021-03-24 01:58:54', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7475', '63', '用户', '127.0.0.1', '2021-03-24 16:08:35', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7476', '63', '用户', '127.0.0.1', '2021-03-24 16:11:55', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7477', '63', '用户', '127.0.0.1', '2021-03-24 16:12:08', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7478', '63', '用户', '127.0.0.1', '2021-03-25 15:59:09', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7479', '63', '用户', '127.0.0.1', '2021-03-25 22:49:22', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7480', '63', '用户', '127.0.0.1', '2021-03-28 20:44:43', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7481', '63', '用户', '127.0.0.1', '2021-03-30 21:38:58', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7482', '63', '用户', '127.0.0.1', '2021-03-31 19:58:03', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7483', '63', '用户', '127.0.0.1', '2021-04-05 17:27:03', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7484', '63', '用户', '127.0.0.1', '2021-04-07 22:54:15', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7485', '63', '用户', '127.0.0.1', '2021-04-07 23:15:01', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7486', '63', '用户', '127.0.0.1', '2021-04-07 23:15:12', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7487', '63', '用户', '127.0.0.1', '2021-04-07 23:20:45', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7488', '63', '用户', '127.0.0.1', '2021-04-07 23:24:40', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7489', '63', '用户', '127.0.0.1', '2021-04-07 23:24:52', '0', '登录jsh', '63');
INSERT INTO `jsh_log` VALUES ('7490', '63', '单据', '127.0.0.1', '2021-04-07 23:25:24', '0', '新增CGRK00000000628', '63');
INSERT INTO `jsh_log` VALUES ('7491', '63', '单据', '127.0.0.1', '2021-04-07 23:25:47', '0', '新增XSCK00000000629', '63');

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
) ENGINE=InnoDB AUTO_INCREMENT=619 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of jsh_material
-- ----------------------------
INSERT INTO `jsh_material` VALUES ('568', '17', '商品1', '制1', '100.000000', 'sp1', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('569', '17', '商品2', '', '200.000000', 'sp2', '', '', '只', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('570', '17', '商品3', '', '300.000000', 'sp3', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('577', null, '商品8', '', null, 'sp8', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('579', '21', '商品17', '', null, 'sp17', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('586', '17', '序列号商品测试', '', null, 'xlh123', '', '', '个', '', null, '', '', '', '', '1', '63', '0');
INSERT INTO `jsh_material` VALUES ('587', '17', '商品test1', '南通中远', null, '', 'test1', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('588', '21', '商品200', 'fafda', '112.000000', 'weqwe', '300ml', '红色', '个', 'aaaabbbbb', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('593', null, 'asdfads', null, null, 'afsdf', 'fasdf', '灰色', '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('595', null, 'adsfads', null, null, 'asdgadsf', 'adgasdf', null, null, 'sdfads', null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('602', null, 'ggggg', null, null, 'dd2', 'gg1', null, '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('603', null, 'afdfadf', null, null, 'afsdf', 'adsfa', 'adsf', null, null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('604', null, 'afdfadfqerqerq', null, null, 'afsdf', 'adsfaerq', 'adsf', null, null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('606', null, 'fada213', null, null, 'afsdf', 'adsfaerq', 'adsf', null, null, null, '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('607', null, 'fsdfasd', null, null, null, 'afsdf', null, null, null, null, '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('608', null, 'fadasdfe33', null, null, null, '123', null, '个', null, null, '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('609', null, 'afdsfa123123', null, '12.000000', '123123', '1232', null, '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('610', '21', '商品gggg', null, '12.000000', 'asdf2', 'gg1', 'ffff', '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('611', '21', 'fasdfadf', null, null, 'adf', 'adsf', '灰色', '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('612', '21', 'e123123', null, '8.000000', '12412', '123123', '3123', '个', null, null, '', null, null, null, '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('613', '17', 'fasdfad商品', '制造1213', '33.000000', '123123', '666', '灰色', '', 'ggggggggggggggggg', '20', '', '111', '222', '333', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('614', '0', '可乐test', null, '12.000000', 'kele', '250ml', '白色', null, null, '0', '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('615', '0', '啤酒test', null, '14.000000', 'pijiu', '500ml', '黑色', null, null, '0', '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('616', '0', '可乐test', null, '12.000000', 'kele', '250ml', '白色', null, null, '0', '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('617', '0', '啤酒test', null, '14.000000', 'pijiu', '500ml', '黑色', null, null, '0', '', null, null, null, '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('618', '17', 'fasdfad商品1', '制造1213', null, '123123', '111', '灰色', '', null, '15', '', '111', '222', '333', '0', '63', '0');

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
  `serial_no` varchar(100) DEFAULT NULL COMMENT '编号',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK3EE7F725237A77D8` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='产品类型表';

-- ----------------------------
-- Records of jsh_material_category
-- ----------------------------
INSERT INTO `jsh_material_category` VALUES ('17', '目录1', null, null, '11', 'wae12', 'eee', '2019-04-10 22:18:12', '2021-02-17 15:11:35', '63', '0');
INSERT INTO `jsh_material_category` VALUES ('21', '目录2', null, '17', '22', 'ada112', 'ddd', '2020-07-20 23:08:44', '2020-07-20 23:08:44', '63', '0');
INSERT INTO `jsh_material_category` VALUES ('22', 'afads', null, null, '22', 'd2123', 'eerqer', '2021-02-16 22:39:26', '2021-02-16 22:39:26', '63', '1');
INSERT INTO `jsh_material_category` VALUES ('23', 'fdfadsf', null, null, '22', '324234', 'fsdfadsf', '2021-02-16 22:39:44', '2021-02-16 22:39:55', '63', '1');
INSERT INTO `jsh_material_category` VALUES ('24', 'gsfddsfg', null, '27', '33', 'sdfg', 'sdfgsdfgsg', '2021-02-16 22:40:23', '2021-02-17 14:56:02', '63', '0');
INSERT INTO `jsh_material_category` VALUES ('25', 'eeeeee', null, '21', '35', 'dfgsdfg', 'sdfgsdfgsfdg', '2021-02-16 22:40:34', '2021-02-17 14:56:09', '63', '1');
INSERT INTO `jsh_material_category` VALUES ('26', 'gertwer', null, '17', '44', 'wertwretw', '4rrerer', '2021-02-16 22:41:05', '2021-02-16 22:45:30', '63', '1');
INSERT INTO `jsh_material_category` VALUES ('27', 'bbbb111', null, '17', '44', 'bbbbbb', 'bbbbbbb', '2021-02-16 22:45:43', '2021-02-16 22:45:52', '63', '0');
INSERT INTO `jsh_material_category` VALUES ('28', 'rrrr111111', null, '21', '22', 'ttttt', 'aedasdasd', '2021-02-17 14:56:21', '2021-02-17 14:56:31', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品当前库存';

-- ----------------------------
-- Records of jsh_material_current_stock
-- ----------------------------
INSERT INTO `jsh_material_current_stock` VALUES ('1', '587', '14', '-30.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('2', '570', '14', '48.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('3', '568', '14', '19.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('4', '569', '14', '50.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('5', '588', '14', '13.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('6', '569', '15', '122.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('7', '579', '14', '120.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('8', '613', '14', '1.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('9', '612', '14', '6.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('10', '577', '14', '0.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('11', '613', '17', '3.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('12', '613', '15', '2.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('13', '588', '17', '0.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('14', '610', '15', '0.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('15', '610', '17', '1.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('16', '612', '17', '6.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('17', '612', '15', '16.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('18', '610', '14', '-5.000000', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品价格扩展';

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
INSERT INTO `jsh_material_extend` VALUES ('10', '588', '1009', '个', '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-07-21 00:58:15', 'jsh', 'jsh', '1614699799073', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('11', '606', '123123123134', 'fa', '12.000000', '12.000000', '12.000000', '12.000000', '1', '2021-03-03 00:57:29', 'jsh', 'jsh', '1614704249133', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('12', '607', '123123', '个', '121.000000', '12.000000', '121.000000', '2.000000', '1', '2021-03-03 00:58:46', 'jsh', 'jsh', '1614704326332', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('13', '608', '41234132413', '个', '33.000000', '33.000000', '33.000000', '33.000000', '1', '2021-03-03 01:01:00', 'jsh', 'jsh', '1614704712738', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('14', '609', '1232314234', '个', '44.000000', '66.000000', '66.000000', '66.000000', '1', '2021-03-03 01:05:45', 'jsh', 'jsh', '1614704778334', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('15', '609', '2341341', '个', '22.000000', '33.000000', null, null, '0', '2021-03-03 01:06:18', 'jsh', 'jsh', '1614704778346', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('16', '610', '21312341', '个', '14.000000', '14.000000', '14.000000', '14.000000', '1', '2021-03-03 21:37:47', 'jsh', 'jsh', '1614778703780', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('17', '610', '21312341555', '个', '14.000000', '14.000000', '14.000000', '14.000000', '1', '2021-03-03 21:41:11', 'jsh', 'jsh', '1614778870541', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('18', '611', '3434343434', '个', '34.000000', '34.000000', '34.000000', '34.000000', '1', '2021-03-03 21:41:45', 'jsh', 'jsh', '1614778904753', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('19', '612', '123123123', '块', '2.000000', '3.000000', null, null, '1', '2021-03-03 22:15:53', 'jsh', 'jsh', '1614781099614', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('20', '612', '111111', '个', '222.000000', '333.000000', '300.000000', '290.000000', '1', '2021-03-03 22:16:40', 'jsh', 'jsh', '1614876188940', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('21', '612', '123123123555', '块', '2.000000', '3.000000', null, null, '0', '2021-03-03 22:18:31', 'jsh', 'jsh', '1614781133037', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('22', '612', '12312315', '块', '2.000000', '3.000000', null, null, '0', '2021-03-03 22:19:08', 'jsh', 'jsh', '1614781210315', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('23', '613', '4234134', '个', '22.000000', '33.000000', null, null, '1', '2021-03-03 22:31:11', 'jsh', 'jsh', '1614943988369', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('24', '614', '2001', '瓶', '5.000000', '12.000000', '10.000000', '9.000000', '1', '2021-03-04 23:10:03', 'jsh', 'jsh', '1614870603351', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('25', '614', '2002', '箱', '60.000000', '144.000000', '120.000000', '108.000000', '0', '2021-03-04 23:10:03', 'jsh', 'jsh', '1614870603370', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('26', '615', '2011', '瓶', '3.000000', '6.000000', '5.000000', '4.000000', '1', '2021-03-04 23:10:03', 'jsh', 'jsh', '1614870603443', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('27', '615', '2012', '箱', '36.000000', '72.000000', '60.000000', '48.000000', '0', '2021-03-04 23:10:03', 'jsh', 'jsh', '1614870603452', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('28', '616', '2001', '瓶', '5.000000', '12.000000', '10.000000', '9.000000', '1', '2021-03-04 23:14:40', 'jsh', 'jsh', '1614870879591', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('29', '616', '2002', '箱', '60.000000', '144.000000', '120.000000', '108.000000', '0', '2021-03-04 23:14:40', 'jsh', 'jsh', '1614870879606', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('30', '617', '2011', '瓶', '3.000000', '6.000000', '5.000000', '4.000000', '1', '2021-03-04 23:14:40', 'jsh', 'jsh', '1614872510505', '63', '1');
INSERT INTO `jsh_material_extend` VALUES ('31', '617', '2012', '箱', '36.000000', '72.000000', '60.000000', '48.000000', '0', '2021-03-04 23:14:40', 'jsh', 'jsh', '1614872510517', '63', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品初始库存';

-- ----------------------------
-- Records of jsh_material_initial_stock
-- ----------------------------
INSERT INTO `jsh_material_initial_stock` VALUES ('123', '587', '14', '5.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('124', '614', '14', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('125', '614', '15', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('126', '614', '17', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('127', '615', '14', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('128', '615', '15', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('129', '615', '17', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('130', '616', '14', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('131', '616', '15', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('132', '616', '17', '10.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('133', '617', '14', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('134', '617', '15', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('135', '617', '17', '20.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('145', '612', '14', '4.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('146', '612', '15', '4.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('147', '612', '17', '4.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('169', '618', '14', '1.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('170', '618', '15', '2.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('171', '618', '17', '3.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('202', '613', '14', '1.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('203', '613', '15', '2.000000', '63', '0');
INSERT INTO `jsh_material_initial_stock` VALUES ('204', '613', '17', '3.000000', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='产品扩展字段表';

-- ----------------------------
-- Records of jsh_material_property
-- ----------------------------
INSERT INTO `jsh_material_property` VALUES ('1', '制造商', '', '01', '制造商', '0');
INSERT INTO `jsh_material_property` VALUES ('2', '自定义1', '', '02', '自定义1', '0');
INSERT INTO `jsh_material_property` VALUES ('3', '自定义2', '', '03', '自定义2', '0');
INSERT INTO `jsh_material_property` VALUES ('4', '自定义3', '', '04', '自定义3', '0');

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
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父机构id',
  `sort` varchar(20) DEFAULT NULL COMMENT '机构显示顺序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of jsh_organization
-- ----------------------------
INSERT INTO `jsh_organization` VALUES ('12', '001', '测试机构', '测试机构', null, '2', 'aaaa2', '2019-12-28 12:13:01', '2019-12-28 12:13:01', '63', '0');
INSERT INTO `jsh_organization` VALUES ('13', 'jg1', '机构1', '机构1', '12', '3', '', '2020-07-21 00:09:57', '2020-07-21 00:10:22', '63', '0');
INSERT INTO `jsh_organization` VALUES ('14', '12', '机构2', '机构2', '13', '4', '', '2020-07-21 22:45:42', '2021-02-15 22:18:30', '63', '0');
INSERT INTO `jsh_organization` VALUES ('18', '5555', 'eeee', 'rrrrr', '13', '4', '444444', '2021-02-15 21:55:17', '2021-02-15 22:01:26', '63', '1');
INSERT INTO `jsh_organization` VALUES ('19', '12', 'fasdf2', 'fasdfa', '14', '2', '3', '2021-02-15 22:41:36', '2021-02-15 22:51:47', '63', '1');
INSERT INTO `jsh_organization` VALUES ('20', '234', 'rwetw', '百货', '14', '33', '444444', '2021-02-15 22:54:15', '2021-02-15 22:54:22', '63', '0');
INSERT INTO `jsh_organization` VALUES ('21', '123', 'adsfa', 'adsf', '14', '23', 'sdfadsf', '2021-02-16 20:44:00', '2021-02-16 20:44:37', '63', '1');
INSERT INTO `jsh_organization` VALUES ('22', '123123', 'tytyty', 'trtrtr', '12', '10', 'aaaaaaaa', '2021-02-16 20:50:01', '2021-02-16 20:50:18', '63', '0');
INSERT INTO `jsh_organization` VALUES ('23', '1231s', 'trertwert', 'wretwertwer', '12', '22', 'rfasdasdf', '2021-02-16 20:50:52', '2021-02-16 20:50:52', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='机构用户关系表';

-- ----------------------------
-- Records of jsh_orga_user_rel
-- ----------------------------
INSERT INTO `jsh_orga_user_rel` VALUES ('10', '13', '131', '2', '0', '2019-12-28 12:13:15', '63', '2021-03-18 22:33:19', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('11', '12', '63', '15', '0', '2020-09-13 18:42:45', '63', '2021-03-19 00:11:40', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('12', '13', '135', '9', '0', '2021-03-18 22:24:25', '63', '2021-03-19 00:09:23', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('13', '13', '134', '1', '0', '2021-03-18 22:31:39', '63', '2021-03-18 23:59:55', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('14', '22', '133', '22', '0', '2021-03-18 22:31:44', '63', '2021-03-18 22:32:04', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('15', '12', '144', null, '0', '2021-03-19 00:00:40', '63', '2021-03-19 00:08:07', '63', '63');
INSERT INTO `jsh_orga_user_rel` VALUES ('16', '12', '145', null, '0', '2021-03-19 00:03:44', '63', '2021-03-19 00:03:44', '63', '63');

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='经手人表';

-- ----------------------------
-- Records of jsh_person
-- ----------------------------
INSERT INTO `jsh_person` VALUES ('14', '业务员', '小李', '63', '0');
INSERT INTO `jsh_person` VALUES ('15', '仓管员', '小军', '63', '0');
INSERT INTO `jsh_person` VALUES ('16', '财务员', '小夏', '63', '0');
INSERT INTO `jsh_person` VALUES ('17', '财务员', '小曹', '63', '0');

-- ----------------------------
-- Table structure for jsh_platform_config
-- ----------------------------
DROP TABLE IF EXISTS `jsh_platform_config`;
CREATE TABLE `jsh_platform_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform_key` varchar(100) DEFAULT NULL COMMENT '关键词',
  `platform_key_info` varchar(100) DEFAULT NULL COMMENT '关键词名称',
  `platform_value` varchar(200) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='平台参数';

-- ----------------------------
-- Records of jsh_platform_config
-- ----------------------------
INSERT INTO `jsh_platform_config` VALUES ('1', 'platform_name', '平台名称', '华夏ERP');
INSERT INTO `jsh_platform_config` VALUES ('2', 'activation_code', '激活码', 'b687998cc991278832e0ebcb7a6e7d0f6870c29d21679e58fa91e388985b9df5c2cd553a8d2f91de');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of jsh_role
-- ----------------------------
INSERT INTO `jsh_role` VALUES ('4', '管理员', '全部数据', null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('10', '租户', '全部数据', null, '', null, '0');
INSERT INTO `jsh_role` VALUES ('16', '销售经理', '全部数据', null, 'ddd', '63', '0');
INSERT INTO `jsh_role` VALUES ('17', '销售代表', '个人数据', null, 'rrr', '63', '0');
INSERT INTO `jsh_role` VALUES ('18', '角色abc', '本机构数据', null, '33333', '63', '0');
INSERT INTO `jsh_role` VALUES ('19', '11111', '个人数据', null, '222222', null, '1');
INSERT INTO `jsh_role` VALUES ('20', 'dddd1', '个人数据', null, 'ddddddd1', '63', '1');

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
INSERT INTO `jsh_sequence` VALUES ('depot_number_seq', '1', '999999999999999999', '629', '1', '单据编号sequence');

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
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='供应商/客户信息表';

-- ----------------------------
-- Records of jsh_supplier
-- ----------------------------
INSERT INTO `jsh_supplier` VALUES ('57', '供应商1', '小军', '12345678', '', '', null, '供应商', '', '0.000000', '8.000000', '0.000000', '0.000000', '0.000000', '', '15000000000', '地址1', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('58', '客户1', '小李', '12345678', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '-100.000000', null, '', '', '', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('59', '客户2', '小陈', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('60', '12312666', '小曹', '', '', '', null, '会员', '', '970.000000', '0.000000', '0.000000', null, null, '', '13000000000', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('68', '供应商3', '晓丽', '12345678', '', 'fasdfadf', null, '供应商', '', '0.000000', '15.000000', '0.000000', '0.000000', '-35.000000', '', '13000000000', 'aaaa', '1341324', '', '', '13.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('71', '客户3', '小周', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('74', '供应商5', '小季', '77779999', '', '', null, '供应商', '', '0.000000', '0.000000', '5.000000', '0.000000', '5.000000', '', '15806283912', '', '', '', '', '3.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('75', 'sssss', null, null, null, null, null, '供应商', '', '0.000000', null, null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('77', 'afsdfasdf', null, null, null, null, null, '供应商', '', '0.000000', null, null, null, null, null, null, null, null, null, null, null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('78', 'ffffdddd', '小曹', '88883333', null, null, null, '供应商', '', '0.000000', '30.000000', '0.000000', '0.000000', '-30.000000', null, '15962969210', null, 'asdf2123123123', null, null, '5.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('79', '3123123', '刘能', null, null, null, null, '会员', '', '0.000000', '55.000000', '0.000000', null, null, null, '15800000000', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('80', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('81', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('82', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('83', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('84', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('85', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('86', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('87', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('88', 'abcd', '季圣华', '1341341332', '', '', '1', '供应商', '', '0.000000', '30.000000', '0.000000', null, null, '', '15962969210', '', 'asdf2123123123', '', '', '5.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('89', 'gsdfadf', null, null, null, null, null, '供应商', '\0', '0.000000', null, null, null, null, null, null, null, null, null, null, null, '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数';

-- ----------------------------
-- Records of jsh_system_config
-- ----------------------------
INSERT INTO `jsh_system_config` VALUES ('9', '公司1122', '小军', '地址1', '12313', '1233', '4231', '0', '0', '1', '63', '1');
INSERT INTO `jsh_system_config` VALUES ('10', '公司test1231323', '123123', '24123', '1234', '141', '4144', '0', '0', '1', '63', '1');
INSERT INTO `jsh_system_config` VALUES ('11', '公司1222', '小李', 'asdfa', 'adfadfs', null, null, '0', '0', '1', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='租户';

-- ----------------------------
-- Records of jsh_tenant
-- ----------------------------
INSERT INTO `jsh_tenant` VALUES ('13', '63', 'jsh', '20', '2000', null);
INSERT INTO `jsh_tenant` VALUES ('14', '137', 'alibaba', '1000000', '100000000', '2021-02-17 23:19:17');
INSERT INTO `jsh_tenant` VALUES ('15', '138', 'xiaomi', '1000000', '100000000', '2021-02-17 23:21:25');
INSERT INTO `jsh_tenant` VALUES ('16', '139', 'tenxun', '1000000', '100000000', '2021-02-17 23:23:09');
INSERT INTO `jsh_tenant` VALUES ('17', '140', 'opopo', '1000000', '100000000', '2021-02-17 23:24:31');
INSERT INTO `jsh_tenant` VALUES ('18', '141', 'gaode', '1000000', '100000000', '2021-02-17 23:26:06');
INSERT INTO `jsh_tenant` VALUES ('19', '142', 'xiaoli', '1000000', '100000000', '2021-02-17 23:28:11');
INSERT INTO `jsh_tenant` VALUES ('20', '143', '15962969210', '1000000', '100000000', '2021-02-27 00:08:03');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='多单位表';

-- ----------------------------
-- Records of jsh_unit
-- ----------------------------
INSERT INTO `jsh_unit` VALUES ('15', '个,箱(1:12)', '个', '箱', '12', '63', '0');
INSERT INTO `jsh_unit` VALUES ('16', '个,台(1:10)', '个', '台', '10', '63', '0');
INSERT INTO `jsh_unit` VALUES ('17', '个,只(1:12)', '个', '只', '12', '63', '0');
INSERT INTO `jsh_unit` VALUES ('19', '个,盒(1:15)', '个', '盒', '15', '63', '0');
INSERT INTO `jsh_unit` VALUES ('20', '盒,箱(1:8)', '盒', '箱', '8', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of jsh_user
-- ----------------------------
INSERT INTO `jsh_user` VALUES ('63', '季圣华', 'jsh', 'e10adc3949ba59abbe56e057f20f883e', '主管', null, '666666@qq.com', '1123123123132', '1', '1', '0', '', null, '63');
INSERT INTO `jsh_user` VALUES ('120', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '0');
INSERT INTO `jsh_user` VALUES ('131', '测试用户', 'test123', 'e10adc3949ba59abbe56e057f20f883e', '总监', null, '752718920@qq.com', '', '1', '0', '0', '', null, '63');
INSERT INTO `jsh_user` VALUES ('132', 'ggggggg', 'gggg', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '1', null, null, '63');
INSERT INTO `jsh_user` VALUES ('133', 'asdfasdfaf', 'fasdfadf', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '1', null, null, '63');
INSERT INTO `jsh_user` VALUES ('134', 'adsfasdfaf', 'dfadsf', 'e10adc3949ba59abbe56e057f20f883e', '主管', null, '434123444@qq.com', null, '1', '0', '0', null, null, '63');
INSERT INTO `jsh_user` VALUES ('135', 'hhhhhh', 'hhhhhhhh', 'e10adc3949ba59abbe56e057f20f883e', 'ooooooo', null, '752718920@qq.com', '11111122222', '1', '0', '0', 'aaaaadd', null, '63');
INSERT INTO `jsh_user` VALUES ('137', 'alibaba', 'alibaba', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '137');
INSERT INTO `jsh_user` VALUES ('138', 'xiaomi', 'xiaomi', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '138');
INSERT INTO `jsh_user` VALUES ('139', 'tenxun', 'tenxun', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '139');
INSERT INTO `jsh_user` VALUES ('140', 'opopo', 'opopo', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '140');
INSERT INTO `jsh_user` VALUES ('141', 'gaode', 'gaode', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '141');
INSERT INTO `jsh_user` VALUES ('142', 'xiaoli', 'xiaoli', 'c2b5f977762fbe5af5231917984dcbf9', null, null, null, null, '1', '0', '0', null, null, '142');
INSERT INTO `jsh_user` VALUES ('143', '15962969210', '15962969210', 'e71d4adc67f2edc3d653d18b2258ffb8', null, null, null, null, '1', '0', '0', null, null, '143');
INSERT INTO `jsh_user` VALUES ('144', 'alibaba123', 'alibaba123', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '63');
INSERT INTO `jsh_user` VALUES ('145', 'caoyuli', 'caoyuli', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '63');

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
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='用户/角色/模块关系表';

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
INSERT INTO `jsh_user_business` VALUES ('32', 'RoleFunctions', '10', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][59][207][208][209][226][227][228][229][235][237][244][246][22][23][220][240][25][217][218][26][194][195][31][13][243][14][15][234][255][256][257]', '[{\"funId\":\"13\",\"btnStr\":\"1\"},{\"funId\":\"243\",\"btnStr\":\"1\"},{\"funId\":\"14\",\"btnStr\":\"1\"},{\"funId\":\"234\",\"btnStr\":\"1\"},{\"funId\":\"22\",\"btnStr\":\"1\"},{\"funId\":\"23\",\"btnStr\":\"1\"},{\"funId\":\"220\",\"btnStr\":\"1\"},{\"funId\":\"240\",\"btnStr\":\"1\"},{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"26\",\"btnStr\":\"1\"},{\"funId\":\"194\",\"btnStr\":\"1\"},{\"funId\":\"195\",\"btnStr\":\"1\"},{\"funId\":\"31\",\"btnStr\":\"1\"},{\"funId\":\"241\",\"btnStr\":\"1,2\"},{\"funId\":\"33\",\"btnStr\":\"1,2\"},{\"funId\":\"199\",\"btnStr\":\"1,2\"},{\"funId\":\"242\",\"btnStr\":\"1,2\"},{\"funId\":\"41\",\"btnStr\":\"1,2\"},{\"funId\":\"200\",\"btnStr\":\"1,2\"},{\"funId\":\"210\",\"btnStr\":\"1,2\"},{\"funId\":\"211\",\"btnStr\":\"1,2\"},{\"funId\":\"197\",\"btnStr\":\"1\"},{\"funId\":\"203\",\"btnStr\":\"1\"},{\"funId\":\"204\",\"btnStr\":\"1\"},{\"funId\":\"205\",\"btnStr\":\"1\"},{\"funId\":\"206\",\"btnStr\":\"1\"},{\"funId\":\"212\",\"btnStr\":\"1\"},{\"funId\":\"201\",\"btnStr\":\"1,2\"},{\"funId\":\"202\",\"btnStr\":\"1,2\"},{\"funId\":\"40\",\"btnStr\":\"1,2\"},{\"funId\":\"232\",\"btnStr\":\"1,2\"},{\"funId\":\"233\",\"btnStr\":\"1,2\"}]', '0');
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
INSERT INTO `jsh_user_business` VALUES ('71', 'UserRole', '137', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('72', 'UserRole', '138', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('73', 'UserRole', '139', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('74', 'UserRole', '140', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('75', 'UserRole', '141', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('76', 'UserRole', '142', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('77', 'UserRole', '143', '[10]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('78', 'UserRole', '134', '[18]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('80', 'UserRole', '144', '[18]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('81', 'UserRole', '145', '[18]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('82', 'UserRole', '135', '[18]', null, '0');
