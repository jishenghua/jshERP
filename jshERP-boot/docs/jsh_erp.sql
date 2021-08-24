/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : jsh_erp

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2021-07-28 01:03:33
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
  `discount_money` decimal(24,6) DEFAULT NULL COMMENT '优惠金额',
  `total_price` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户(收款/付款)',
  `bill_no` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `bill_time` datetime DEFAULT NULL COMMENT '单据日期',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `file_name` varchar(500) DEFAULT NULL COMMENT '附件名称',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK9F4C0D8DB610FC06` (`organ_id`),
  KEY `FK9F4C0D8DAAE50527` (`account_id`),
  KEY `FK9F4C0D8DC4170B37` (`hands_person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COMMENT='财务主表';

-- ----------------------------
-- Records of jsh_account_head
-- ----------------------------
INSERT INTO `jsh_account_head` VALUES ('118', '收入', '58', '16', '63', '55.000000', null, '55.000000', '17', 'SR00000000643', '2021-06-02 00:24:49', null, null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('119', '支出', '68', '16', '63', '66.000000', null, '66.000000', '17', 'ZC00000000644', '2021-06-02 00:25:01', null, null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('122', '转账', null, '17', '63', '11.000000', null, '11.000000', '17', 'ZZ00000000647', '2021-06-02 00:25:32', null, null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('124', '收预付款', '60', '17', '63', '80.000000', '0.000000', '80.000000', null, 'SYF00000000649', '2021-07-06 23:43:48', null, null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('125', '收款', '58', '17', '63', '10.000000', '0.000000', '10.000000', '17', 'SK00000000653', '2021-07-06 23:46:38', null, null, '63', '0');
INSERT INTO `jsh_account_head` VALUES ('126', '付款', '57', '17', '63', '50.000000', '0.000000', '-50.000000', '17', 'FK00000000654', '2021-07-06 23:47:23', null, null, '63', '0');

-- ----------------------------
-- Table structure for jsh_account_item
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account_item`;
CREATE TABLE `jsh_account_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `header_id` bigint(20) NOT NULL COMMENT '表头Id',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `in_out_item_id` bigint(20) DEFAULT NULL COMMENT '收支项目Id',
  `bill_id` bigint(20) DEFAULT NULL COMMENT '单据id',
  `need_debt` decimal(24,6) DEFAULT NULL COMMENT '应收欠款',
  `finish_debt` decimal(24,6) DEFAULT NULL COMMENT '已收欠款',
  `each_amount` decimal(24,6) DEFAULT NULL COMMENT '单项金额',
  `remark` varchar(100) DEFAULT NULL COMMENT '单据备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK9F4CBAC0AAE50527` (`account_id`),
  KEY `FK9F4CBAC0C5FE6007` (`header_id`),
  KEY `FK9F4CBAC0D203EDC5` (`in_out_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COMMENT='财务子表';

-- ----------------------------
-- Records of jsh_account_item
-- ----------------------------
INSERT INTO `jsh_account_item` VALUES ('143', '118', null, '23', null, null, null, '55.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('144', '119', null, '21', null, null, null, '66.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('147', '122', '17', null, null, null, null, '11.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('149', '124', '17', null, null, null, null, '80.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('150', '125', null, null, '272', '20.000000', '0.000000', '10.000000', '', '63', '0');
INSERT INTO `jsh_account_item` VALUES ('151', '126', null, null, '271', '60.000000', '0.000000', '-50.000000', '', '63', '0');

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
  `creator` bigint(20) DEFAULT NULL COMMENT '操作员',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id',
  `change_amount` decimal(24,6) DEFAULT NULL COMMENT '变动金额(收款/付款)',
  `total_price` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `pay_type` varchar(50) DEFAULT NULL COMMENT '付款类型(现金、记账等)',
  `bill_type` varchar(50) DEFAULT NULL COMMENT '单据类型',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `file_name` varchar(500) DEFAULT NULL COMMENT '附件名称',
  `sales_man` varchar(50) DEFAULT NULL COMMENT '业务员（可以多个）',
  `account_id_list` varchar(50) DEFAULT NULL COMMENT '多账户ID列表',
  `account_money_list` varchar(200) DEFAULT NULL COMMENT '多账户金额列表',
  `discount` decimal(24,6) DEFAULT NULL COMMENT '优惠率',
  `discount_money` decimal(24,6) DEFAULT NULL COMMENT '优惠金额',
  `discount_last_money` decimal(24,6) DEFAULT NULL COMMENT '优惠后金额',
  `other_money` decimal(24,6) DEFAULT NULL COMMENT '销售或采购费用合计',
  `status` varchar(1) DEFAULT NULL COMMENT '状态，0未审核、1已审核、2已转采购|销售',
  `link_number` varchar(50) DEFAULT NULL COMMENT '关联订单号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK2A80F214B610FC06` (`organ_id`),
  KEY `FK2A80F214AAE50527` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8 COMMENT='单据主表';

-- ----------------------------
-- Records of jsh_depot_head
-- ----------------------------
INSERT INTO `jsh_depot_head` VALUES ('258', '其它', '采购订单', 'CGDD00000000630', 'CGDD00000000630', '2021-06-02 00:21:54', '2021-06-02 00:21:44', '57', '63', null, null, '-110.000000', '现付', null, null, null, null, null, null, null, null, null, null, '2', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('259', '入库', '采购', 'CGRK00000000631', 'CGRK00000000631', '2021-06-02 00:22:23', '2021-06-02 00:22:05', '57', '63', '17', '-110.000000', '-110.000000', '现付', null, null, null, null, '', '', '0.000000', '0.000000', '110.000000', '0.000000', '0', 'CGDD00000000630', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('260', '出库', '采购退货', 'CGTH00000000632', 'CGTH00000000632', '2021-06-02 00:22:35', '2021-06-02 00:22:26', '57', '63', '17', '22.000000', '22.000000', '现付', null, null, null, null, null, null, '0.000000', '0.000000', '22.000000', '0.000000', '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('261', '其它', '销售订单', 'XSDD00000000633', 'XSDD00000000633', '2021-06-02 00:22:48', '2021-06-02 00:22:39', '58', '63', null, null, '44.000000', '现付', null, null, null, '', null, null, null, null, null, null, '2', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('262', '出库', '销售', 'XSCK00000000634', 'XSCK00000000634', '2021-06-02 00:23:03', '2021-06-02 00:22:54', '58', '63', '17', '44.000000', '44.000000', '现付', null, null, null, '', '', '', '0.000000', '0.000000', '44.000000', '0.000000', '0', 'XSDD00000000633', '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('263', '入库', '销售退货', 'XSTH00000000635', 'XSTH00000000635', '2021-06-02 00:23:12', '2021-06-02 00:23:05', '71', '63', '17', '-22.000000', '-22.000000', '现付', null, null, null, '', '', '', '0.000000', '0.000000', '22.000000', '0.000000', '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('264', '出库', '零售', 'LSCK00000000636', 'LSCK00000000636', '2021-06-02 00:23:21', '2021-06-02 00:23:14', '60', '63', '17', '22.000000', '22.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('265', '入库', '零售退货', 'LSTH00000000637', 'LSTH00000000637', '2021-06-02 00:23:29', '2021-06-02 00:23:23', '60', '63', '17', '-22.000000', '-22.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('266', '入库', '其它', 'QTRK00000000638', 'QTRK00000000638', '2021-06-02 00:23:48', '2021-06-02 00:23:36', '57', '63', null, null, '55.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('267', '出库', '其它', 'QTCK00000000639', 'QTCK00000000639', '2021-06-02 00:23:59', '2021-06-02 00:23:50', '58', '63', null, null, '30.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('268', '出库', '调拨', 'DBCK00000000640', 'DBCK00000000640', '2021-06-02 00:24:09', '2021-06-02 00:24:00', null, '63', null, null, '11.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('269', '其它', '组装单', 'ZZD00000000641', 'ZZD00000000641', '2021-06-02 00:24:29', '2021-06-02 00:24:11', null, '63', null, null, '0.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('270', '其它', '拆卸单', 'CXD00000000642', 'CXD00000000642', '2021-06-02 00:24:45', '2021-06-02 00:24:32', null, '63', null, null, '0.000000', '现付', null, null, null, null, null, null, null, null, null, null, '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('271', '入库', '采购', 'CGRK00000000651', 'CGRK00000000651', '2021-07-06 23:45:20', '2021-07-06 23:44:45', '57', '63', '17', '-20.000000', '-80.000000', '现付', null, null, null, null, '', '', '0.000000', '0.000000', '80.000000', '0.000000', '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('272', '出库', '销售', 'XSCK00000000652', 'XSCK00000000652', '2021-07-06 23:46:07', '2021-07-06 23:45:24', '58', '63', '17', '8.000000', '28.000000', '现付', null, null, null, '', '', '', '0.000000', '0.000000', '28.000000', '0.000000', '0', null, '63', '0');
INSERT INTO `jsh_depot_head` VALUES ('273', '入库', '采购', 'CGRK00000000658', 'CGRK00000000658', '2021-07-28 00:58:12', '2021-07-28 00:58:02', '57', '63', '17', '-60.000000', '-60.000000', '现付', null, null, null, null, '', '', '0.000000', '0.000000', '60.000000', '0.000000', '0', null, '63', '0');

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
  `sku` varchar(50) DEFAULT NULL COMMENT '多属性',
  `oper_number` decimal(24,6) DEFAULT NULL COMMENT '数量',
  `basic_number` decimal(24,6) DEFAULT NULL COMMENT '基础数量，如kg、瓶',
  `unit_price` decimal(24,6) DEFAULT NULL COMMENT '单价',
  `tax_unit_price` decimal(24,6) DEFAULT NULL COMMENT '含税单价',
  `all_price` decimal(24,6) DEFAULT NULL COMMENT '金额',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `depot_id` bigint(20) DEFAULT NULL COMMENT '仓库ID',
  `another_depot_id` bigint(20) DEFAULT NULL COMMENT '调拨时，对方仓库Id',
  `tax_rate` decimal(24,6) DEFAULT NULL COMMENT '税率',
  `tax_money` decimal(24,6) DEFAULT NULL COMMENT '税额',
  `tax_last_money` decimal(24,6) DEFAULT NULL COMMENT '价税合计',
  `material_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`),
  KEY `FK2A819F475D61CCF7` (`material_id`),
  KEY `FK2A819F474BB6190E` (`header_id`),
  KEY `FK2A819F479485B3F5` (`depot_id`),
  KEY `FK2A819F47729F5392` (`another_depot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8 COMMENT='单据子表';

-- ----------------------------
-- Records of jsh_depot_item
-- ----------------------------
INSERT INTO `jsh_depot_item` VALUES ('312', '258', '588', '10', '个', null, '10.000000', '10.000000', '11.000000', null, '110.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('313', '259', '588', '10', '个', null, '10.000000', '10.000000', '11.000000', null, '110.000000', null, '14', null, null, '0.000000', '110.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('314', '260', '588', '10', '个', null, '2.000000', '2.000000', '11.000000', '11.000000', '22.000000', null, '14', null, '0.000000', '0.000000', '22.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('315', '261', '588', '10', '个', null, '2.000000', '2.000000', '22.000000', null, '44.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('316', '262', '588', '10', '个', null, '2.000000', '2.000000', '22.000000', null, '44.000000', null, '14', null, null, '0.000000', '44.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('317', '263', '588', '10', '个', null, '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', null, '14', null, '0.000000', '0.000000', '22.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('318', '264', '588', '10', '个', null, '1.000000', '1.000000', '22.000000', null, '22.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('319', '265', '588', '10', '个', null, '1.000000', '1.000000', '22.000000', null, '22.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('320', '266', '568', '2', '个', null, '5.000000', '5.000000', '11.000000', null, '55.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('321', '267', '568', '2', '个', null, '2.000000', '2.000000', '15.000000', null, '30.000000', null, '14', null, null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('322', '268', '568', '2', '个', null, '1.000000', '1.000000', '11.000000', null, '11.000000', null, '14', '15', null, null, null, null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('323', '269', '588', '10', '个', null, '1.000000', '1.000000', '0.000000', null, '0.000000', null, '14', null, null, null, null, '组合件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('324', '269', '568', '2', '个', null, '1.000000', '1.000000', '0.000000', null, '0.000000', null, '14', null, null, null, null, '普通子件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('325', '270', '588', '10', '个', null, '1.000000', '1.000000', '0.000000', null, '0.000000', null, '14', null, null, null, null, '组合件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('326', '270', '568', '2', '个', null, '1.000000', '1.000000', '0.000000', null, '0.000000', null, '14', null, null, null, null, '普通子件', '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('327', '271', '570', '4', '个', null, '10.000000', '10.000000', '8.000000', '8.000000', '80.000000', null, '14', null, '0.000000', '0.000000', '80.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('328', '272', '570', '4', '个', null, '2.000000', '2.000000', '14.000000', '14.000000', '28.000000', null, '14', null, '0.000000', '0.000000', '28.000000', null, '63', '0');
INSERT INTO `jsh_depot_item` VALUES ('330', '273', '619', '37', '件', '橙色,L', '5.000000', '5.000000', '12.000000', '12.000000', '60.000000', null, '14', null, '0.000000', '0.000000', '60.000000', null, '63', '0');

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
INSERT INTO `jsh_function` VALUES ('18', '000109', '租户管理', '0001', '/system/tenant', '/system/TenantList', '\0', '0167', '', '电脑版', '1', 'profile', '0');
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
INSERT INTO `jsh_function` VALUES ('59', '030101', '进销存统计', '0301', '/report/in_out_stock_report', '/report/InOutStockReport', '\0', '0658', '', '电脑版', '', 'profile', '0');
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
INSERT INTO `jsh_function` VALUES ('246', '030113', '商品库存', '0301', '/report/material_stock', '/report/MaterialStock', '\0', '0605', '', '电脑版', '', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('247', '010105', '多属性', '0101', '/material/material_attribute', '/material/MaterialAttributeList', '\0', '0250', '', '电脑版', '1', 'profile', '0');
INSERT INTO `jsh_function` VALUES ('248', '030150', '调拨明细', '0301', '/report/allocation_detail', '/report/AllocationDetail', '\0', '0646', '', '电脑版', '', 'profile', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=7536 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of jsh_log
-- ----------------------------
INSERT INTO `jsh_log` VALUES ('7530', '63', '用户', '127.0.0.1/127.0.0.1', '2021-07-22 22:54:31', '0', '登录jsh', '63');

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
  `img_name` varchar(500) DEFAULT NULL COMMENT '图片名称',
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
) ENGINE=InnoDB AUTO_INCREMENT=620 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of jsh_material
-- ----------------------------
INSERT INTO `jsh_material` VALUES ('568', '17', '商品1', '制1', '100.000000', 'sp1', '', '', '个', '', null, null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('569', '17', '商品2', '', '200.000000', 'sp2', '', '', '只', '', null, null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('570', '17', '商品3', '', '300.000000', 'sp3', '', '', '个', '', null, null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('577', null, '商品8', '', null, 'sp8', '', '', '', '', null, '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('579', '21', '商品17', '', null, 'sp17', '', '', '', '', null, '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('586', '17', '序列号商品测试', '', null, 'xlh123', '', '', '个', '', null, null, '', '', '', '', '1', '63', '0');
INSERT INTO `jsh_material` VALUES ('587', '17', '商品test1', '南通中远', null, '', 'test1', '', '个', '', null, null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('588', '21', '商品200', 'fafda', '112.000000', 'weqwe', '300ml', '红色', '个', 'aaaabbbbb', null, null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('619', null, '衣服', null, null, null, null, null, '件', null, '', null, '', null, null, null, '0', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_attribute
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_attribute`;
CREATE TABLE `jsh_material_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribute_field` varchar(50) DEFAULT NULL COMMENT '属性字段',
  `attribute_name` varchar(50) DEFAULT NULL COMMENT '属性名',
  `attribute_value` varchar(500) DEFAULT NULL COMMENT '属性值',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='产品属性表';

-- ----------------------------
-- Records of jsh_material_attribute
-- ----------------------------
INSERT INTO `jsh_material_attribute` VALUES ('1', 'manyColor', '多颜色', '红色|橙色|黄色|绿色|蓝色|紫色', '63', '0');
INSERT INTO `jsh_material_attribute` VALUES ('2', 'manySize', '多尺寸', 'S|M|L|XL|XXL|XXXL', '63', '0');
INSERT INTO `jsh_material_attribute` VALUES ('3', 'other1', '自定义1', '小米|华为', '63', '0');
INSERT INTO `jsh_material_attribute` VALUES ('4', 'other2', '自定义2', null, '63', '0');
INSERT INTO `jsh_material_attribute` VALUES ('5', 'other3', '自定义3', null, '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品当前库存';

-- ----------------------------
-- Records of jsh_material_current_stock
-- ----------------------------
INSERT INTO `jsh_material_current_stock` VALUES ('19', '588', '14', '7.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('20', '568', '14', '2.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('21', '568', '15', '1.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('22', '570', '14', '8.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('23', '619', '14', '5.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('24', '619', '15', '0.000000', '63', '0');
INSERT INTO `jsh_material_current_stock` VALUES ('25', '619', '17', '0.000000', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_extend
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_extend`;
CREATE TABLE `jsh_material_extend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `bar_code` varchar(50) DEFAULT NULL COMMENT '商品条码',
  `commodity_unit` varchar(50) DEFAULT NULL COMMENT '商品单位',
  `sku` varchar(50) DEFAULT NULL COMMENT '多属性',
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品价格扩展';

-- ----------------------------
-- Records of jsh_material_extend
-- ----------------------------
INSERT INTO `jsh_material_extend` VALUES ('1', '587', '1000', '个', null, '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-02-20 23:22:03', 'jsh', 'jsh', '1595263657135', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('2', '568', '1001', '个', null, '11.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:44:57', 'jsh', 'jsh', '1595265439418', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('3', '569', '1002', '只', null, '10.000000', '15.000000', '15.000000', '13.000000', '1', '2020-02-20 23:45:15', 'jsh', 'jsh', '1582213514731', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('4', '570', '1003', '个', null, '8.000000', '15.000000', '14.000000', '13.000000', '1', '2020-02-20 23:45:37', 'jsh', 'jsh', '1587657604430', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('5', '577', '1004', '个', null, '10.000000', '20.000000', '20.000000', '20.000000', '1', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596494', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('6', '577', '1005', '箱', null, '120.000000', '240.000000', '240.000000', '240.000000', '0', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596497', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('7', '579', '1006', '个', null, '20.000000', '30.000000', '30.000000', '30.000000', '1', '2020-02-20 23:47:04', 'jsh', 'jsh', '1595264270458', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('8', '579', '1007', '箱', null, '240.000000', '360.000000', '360.000000', '360.000000', '0', '2020-02-20 23:47:04', 'jsh', 'jsh', '1595264270466', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('9', '586', '1008', '个', null, '12.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:47:23', 'jsh', 'jsh', '1595254981896', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('10', '588', '1009', '个', null, '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-07-21 00:58:15', 'jsh', 'jsh', '1614699799073', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('36', '619', '1014', '件', '橙色,M', '12.000000', '15.000000', '14.000000', null, '1', '2021-07-28 01:00:20', 'jsh', 'jsh', '1627405220316', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('37', '619', '1015', '件', '橙色,L', '12.000000', '15.000000', '14.000000', null, '0', '2021-07-28 01:00:20', 'jsh', 'jsh', '1627405220327', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('38', '619', '1016', '件', '绿色,M', '12.000000', '15.000000', '14.000000', null, '0', '2021-07-28 01:00:20', 'jsh', 'jsh', '1627405220336', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('39', '619', '1017', '件', '绿色,L', '12.000000', '15.000000', '14.000000', null, '0', '2021-07-28 01:00:20', 'jsh', 'jsh', '1627405220346', '63', '0');

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
INSERT INTO `jsh_organization` VALUES ('12', '001', '测试机构', null, '2', 'aaaa2', '2019-12-28 12:13:01', '2019-12-28 12:13:01', '63', '0');
INSERT INTO `jsh_organization` VALUES ('13', 'jg1', '机构1', '12', '3', '', '2020-07-21 00:09:57', '2020-07-21 00:10:22', '63', '0');
INSERT INTO `jsh_organization` VALUES ('14', '12', '机构2', '13', '4', '', '2020-07-21 22:45:42', '2021-02-15 22:18:30', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='平台参数';

-- ----------------------------
-- Records of jsh_platform_config
-- ----------------------------
INSERT INTO `jsh_platform_config` VALUES ('1', 'platform_name', '平台名称', '华夏ERP');
INSERT INTO `jsh_platform_config` VALUES ('2', 'activation_code', '激活码', 'e20cc75acddfe869428bcb8ce5e37b161b2490c81c178fba384938e5b3df5fbac2cd553a8d2f91de');
INSERT INTO `jsh_platform_config` VALUES ('3', 'platform_url', '官方网站', 'http://www.huaxiaerp.com/');

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
INSERT INTO `jsh_sequence` VALUES ('depot_number_seq', '1', '999999999999999999', '658', '1', '单据编号sequence');

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
INSERT INTO `jsh_supplier` VALUES ('57', '供应商1', '小军', '12345678', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', '0.000000', '4.000000', '', '15000000000', '地址1', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('58', '客户1', '小李', '12345678', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '-100.000000', null, '', '', '', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('59', '客户2', '小陈', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('60', '12312666', '小曹', '', '', '', null, '会员', '', '970.000000', '0.000000', '0.000000', null, null, '', '13000000000', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('68', '供应商3', '晓丽', '12345678', '', 'fasdfadf', null, '供应商', '', '0.000000', '0.000000', '0.000000', '0.000000', '-35.000000', '', '13000000000', 'aaaa', '1341324', '', '', '13.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('71', '客户3', '小周', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('74', '供应商5', '小季', '77779999', '', '', null, '供应商', '', '0.000000', '0.000000', '5.000000', '0.000000', '5.000000', '', '15806283912', '', '', '', '', '3.000000', '63', '0');

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
INSERT INTO `jsh_system_config` VALUES ('11', '公司test', '小李', '地址1', '12345678', null, null, '0', '0', '1', '63', '0');

-- ----------------------------
-- Table structure for jsh_tenant
-- ----------------------------
DROP TABLE IF EXISTS `jsh_tenant`;
CREATE TABLE `jsh_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `login_name` varchar(255) DEFAULT NULL COMMENT '登录名',
  `user_num_limit` int(11) DEFAULT NULL COMMENT '用户数量限制',
  `type` varchar(1) DEFAULT '0' COMMENT '租户类型，0免费租户，1付费租户',
  `enabled` bit(1) DEFAULT b'1' COMMENT '启用 0-禁用  1-启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expire_time` datetime DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='租户';

-- ----------------------------
-- Records of jsh_tenant
-- ----------------------------
INSERT INTO `jsh_tenant` VALUES ('13', '63', 'jsh', '2000', '1', '', '2021-02-17 23:19:17', '2099-02-17 23:19:17');

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='多单位表';

-- ----------------------------
-- Records of jsh_unit
-- ----------------------------
INSERT INTO `jsh_unit` VALUES ('15', '个,箱(1:12)', '个', '箱', '12', '63', '0');
INSERT INTO `jsh_unit` VALUES ('19', '个,盒(1:15)', '个', '盒', '15', '63', '0');
INSERT INTO `jsh_unit` VALUES ('20', '盒,箱(1:8)', '盒', '箱', '8', '63', '0');
INSERT INTO `jsh_unit` VALUES ('21', '瓶,箱(1:12)', '瓶', '箱', '12', '63', '0');

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
INSERT INTO `jsh_user_business` VALUES ('5', 'RoleFunctions', '4', '[225][210][211][32][241][33][199][38][242][41][200][239][201][202][40][232][233][44][197][203][204][205][206][212][198][246][207][208][209][226][227][228][229][59][235][237][244][22][23][220][240][24][25][217][218][26][194][195][31][1][13][14][243][15][234][16][18][236][245][247][21][0]', '[{\"funId\":13,\"btnStr\":\"1\"},{\"funId\":14,\"btnStr\":\"1\"},{\"funId\":243,\"btnStr\":\"1\"},{\"funId\":234,\"btnStr\":\"1\"},{\"funId\":16,\"btnStr\":\"1\"},{\"funId\":18,\"btnStr\":\"1\"},{\"funId\":236,\"btnStr\":\"1\"},{\"funId\":245,\"btnStr\":\"1\"},{\"funId\":22,\"btnStr\":\"1\"},{\"funId\":23,\"btnStr\":\"1\"},{\"funId\":220,\"btnStr\":\"1\"},{\"funId\":240,\"btnStr\":\"1\"},{\"funId\":247,\"btnStr\":\"1\"},{\"funId\":25,\"btnStr\":\"1\"},{\"funId\":217,\"btnStr\":\"1\"},{\"funId\":218,\"btnStr\":\"1\"},{\"funId\":26,\"btnStr\":\"1\"},{\"funId\":194,\"btnStr\":\"1\"},{\"funId\":195,\"btnStr\":\"1\"},{\"funId\":31,\"btnStr\":\"1\"},{\"funId\":241,\"btnStr\":\"1,2\"},{\"funId\":33,\"btnStr\":\"1,2\"},{\"funId\":199,\"btnStr\":\"1,2\"},{\"funId\":242,\"btnStr\":\"1,2\"},{\"funId\":41,\"btnStr\":\"1,2\"},{\"funId\":200,\"btnStr\":\"1,2\"},{\"funId\":210,\"btnStr\":\"1,2\"},{\"funId\":211,\"btnStr\":\"1,2\"},{\"funId\":197,\"btnStr\":\"1\"},{\"funId\":203,\"btnStr\":\"1\"},{\"funId\":204,\"btnStr\":\"1\"},{\"funId\":205,\"btnStr\":\"1\"},{\"funId\":206,\"btnStr\":\"1\"},{\"funId\":212,\"btnStr\":\"1\"},{\"funId\":201,\"btnStr\":\"1,2\"},{\"funId\":202,\"btnStr\":\"1,2\"},{\"funId\":40,\"btnStr\":\"1,2\"},{\"funId\":232,\"btnStr\":\"1,2\"},{\"funId\":233,\"btnStr\":\"1,2\"}]', '0');
INSERT INTO `jsh_user_business` VALUES ('6', 'RoleFunctions', '5', '[22][23][25][26][194][195][31][33][200][201][41][199][202]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('7', 'RoleFunctions', '6', '[22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]', '[{\"funId\":\"33\",\"btnStr\":\"4\"}]', '0');
INSERT INTO `jsh_user_business` VALUES ('9', 'RoleFunctions', '7', '[168][13][12][16][14][15][189][18][19][132]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('10', 'RoleFunctions', '8', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][247]', null, '0');
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
INSERT INTO `jsh_user_business` VALUES ('32', 'RoleFunctions', '10', '[225][210][211][32][241][33][199][38][242][41][200][239][201][202][40][232][233][44][197][203][204][205][206][212][198][246][207][208][209][226][227][228][229][59][235][237][244][21][22][23][220][240][247][24][25][217][218][26][194][195][31][13][14][243][15][234]', '[{\"funId\":13,\"btnStr\":\"1\"},{\"funId\":14,\"btnStr\":\"1\"},{\"funId\":243,\"btnStr\":\"1\"},{\"funId\":234,\"btnStr\":\"1\"},{\"funId\":22,\"btnStr\":\"1\"},{\"funId\":23,\"btnStr\":\"1\"},{\"funId\":220,\"btnStr\":\"1\"},{\"funId\":240,\"btnStr\":\"1\"},{\"funId\":247,\"btnStr\":\"1\"},{\"funId\":25,\"btnStr\":\"1\"},{\"funId\":217,\"btnStr\":\"1\"},{\"funId\":218,\"btnStr\":\"1\"},{\"funId\":26,\"btnStr\":\"1\"},{\"funId\":194,\"btnStr\":\"1\"},{\"funId\":195,\"btnStr\":\"1\"},{\"funId\":31,\"btnStr\":\"1\"},{\"funId\":241,\"btnStr\":\"1,2\"},{\"funId\":33,\"btnStr\":\"1,2\"},{\"funId\":199,\"btnStr\":\"1,2\"},{\"funId\":242,\"btnStr\":\"1,2\"},{\"funId\":41,\"btnStr\":\"1,2\"},{\"funId\":200,\"btnStr\":\"1,2\"},{\"funId\":210,\"btnStr\":\"1,2\"},{\"funId\":211,\"btnStr\":\"1,2\"},{\"funId\":197,\"btnStr\":\"1\"},{\"funId\":203,\"btnStr\":\"1\"},{\"funId\":204,\"btnStr\":\"1\"},{\"funId\":205,\"btnStr\":\"1\"},{\"funId\":206,\"btnStr\":\"1\"},{\"funId\":212,\"btnStr\":\"1\"},{\"funId\":201,\"btnStr\":\"1,2\"},{\"funId\":202,\"btnStr\":\"1,2\"},{\"funId\":40,\"btnStr\":\"1,2\"},{\"funId\":232,\"btnStr\":\"1,2\"},{\"funId\":233,\"btnStr\":\"1,2\"}]', '0');
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
INSERT INTO `jsh_user_business` VALUES ('67', 'UserRole', '131', '[17]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('68', 'RoleFunctions', '16', '[210]', null, '0');
INSERT INTO `jsh_user_business` VALUES ('69', 'RoleFunctions', '17', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]', '[{\"funId\":\"241\",\"btnStr\":\"1,2\"},{\"funId\":\"33\",\"btnStr\":\"1,2\"},{\"funId\":\"199\",\"btnStr\":\"1,2\"},{\"funId\":\"242\",\"btnStr\":\"1,2\"},{\"funId\":\"41\",\"btnStr\":\"1,2\"},{\"funId\":\"200\",\"btnStr\":\"1,2\"},{\"funId\":\"210\",\"btnStr\":\"1,2\"},{\"funId\":\"211\",\"btnStr\":\"1,2\"},{\"funId\":\"197\",\"btnStr\":\"1\"},{\"funId\":\"203\",\"btnStr\":\"1\"},{\"funId\":\"204\",\"btnStr\":\"1\"},{\"funId\":\"205\",\"btnStr\":\"1\"},{\"funId\":\"206\",\"btnStr\":\"1\"},{\"funId\":\"212\",\"btnStr\":\"1\"},{\"funId\":\"201\",\"btnStr\":\"1,2\"},{\"funId\":\"202\",\"btnStr\":\"1,2\"},{\"funId\":\"40\",\"btnStr\":\"1,2\"},{\"funId\":\"232\",\"btnStr\":\"1,2\"},{\"funId\":\"233\",\"btnStr\":\"1,2\"}]', '0');
