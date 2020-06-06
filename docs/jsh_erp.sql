/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : jsh_erp

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2020-02-21 00:05:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jsh_account
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account`;
CREATE TABLE `jsh_account` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `SerialNo` varchar(50) DEFAULT NULL COMMENT '编号',
  `InitialAmount` decimal(24,6) DEFAULT NULL COMMENT '期初金额',
  `CurrentAmount` decimal(24,6) DEFAULT NULL COMMENT '当前余额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `IsDefault` bit(1) DEFAULT NULL COMMENT '是否默认',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='账户信息';

-- ----------------------------
-- Records of jsh_account
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('4', '南通建行', '652346523465234623', '1200.000000', '215.000000', '建行账户', '\0', null, '0');
INSERT INTO `jsh_account` VALUES ('9', '流动总账', '65234624523452364', '2000.000000', '393.000000', '现在账户', '', null, '0');
INSERT INTO `jsh_account` VALUES ('10', '支付宝', '123456789@qq.com', '10000.000000', null, '', '\0', null, '0');
INSERT INTO `jsh_account` VALUES ('11', '微信', '13000000000', '10000.000000', null, '', '\0', null, '0');
INSERT INTO `jsh_account` VALUES ('12', '上海农行', '65324345234523211', '10000.000000', '0.000000', '', '\0', null, '0');
INSERT INTO `jsh_account` VALUES ('13', '账户1', 'abcd123', '0.000000', null, '', '', '1', '0');
INSERT INTO `jsh_account` VALUES ('14', '账户1', 'zhanghu1', '0.000000', null, '', '', '117', '0');
INSERT INTO `jsh_account` VALUES ('15', '账户2222', 'zh2222', '0.000000', null, '', '\0', '117', '0');
INSERT INTO `jsh_account` VALUES ('16', '账户1', '1231241244', '0.000000', null, '', '', '115', '0');
INSERT INTO `jsh_account` VALUES ('17', '账户1', 'zzz111', '0.000000', null, '', '', '63', '0');
INSERT INTO `jsh_account` VALUES ('18', '账户2', '1234131324', '0.000000', null, '', '\0', '63', '0');

-- ----------------------------
-- Table structure for jsh_accounthead
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accounthead`;
CREATE TABLE `jsh_accounthead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(支出/收入/收款/付款/转账)',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '单位Id(收款/付款单位)',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '经手人Id',
  `ChangeAmount` decimal(24,6) DEFAULT NULL COMMENT '变动金额(优惠/收款/付款/实付)',
  `TotalPrice` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户(收款/付款)',
  `BillNo` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `BillTime` datetime DEFAULT NULL COMMENT '单据日期',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`),
  KEY `FK9F4C0D8DB610FC06` (`OrganId`),
  KEY `FK9F4C0D8DAAE50527` (`AccountId`),
  KEY `FK9F4C0D8DC4170B37` (`HandsPersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='财务主表';

-- ----------------------------
-- Records of jsh_accounthead
-- ----------------------------
INSERT INTO `jsh_accounthead` VALUES ('57', '收预付款', '8', '3', null, '1000.000000', null, '2342134', '2017-06-27 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('61', '收预付款', '9', '3', null, '33.000000', null, 'SYF2017062901721', '2017-06-29 00:00:00', 'aaaaaa', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('67', '收预付款', '10', '4', null, '2100.000000', null, 'SYF2017070222414', '2017-07-02 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('70', '支出', '4', '3', '-60.000000', '-60.000000', '4', 'ZC20170703233735', '2017-07-03 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('74', '转账', null, '3', '-100.000000', '-100.000000', '4', 'ZZ2017070323489', '2017-07-03 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('77', '收入', '2', '3', '40.000000', '40.000000', '4', 'SR20170704222634', '2017-07-04 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('78', '收预付款', '9', '3', null, '200.000000', null, 'SYF201707050257', '2017-07-05 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('79', '收预付款', '9', '3', null, '100.000000', null, 'SYF20170705076', '2017-07-05 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('82', '收款', '2', '3', '0.000000', '2.600000', null, 'SK20171008191440', '2017-10-09 00:08:11', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('83', '付款', '1', '4', '0.000000', '-20.000000', null, 'FK20171008232825', '2017-10-08 00:00:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('84', '收入', '2', '4', '0.000000', '21.000000', '10', 'SR20171009000300', '2017-10-09 00:03:00', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('85', '收入', '2', '3', '22.000000', '22.000000', '11', 'SR20171009000637', '2017-10-09 00:06:37', '备注123 备注123 备注123', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('86', '转账', null, '4', '-22.000000', '-22.000000', '10', 'ZZ20171009000719', '2017-10-09 00:07:19', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('87', '付款', '4', '4', '10.000000', '-33.000000', null, 'FK20171009000747', '2017-10-09 00:07:47', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('88', '收款', '2', '4', '0.000000', '2.800000', null, 'SK20171024220754', '2017-10-24 22:07:54', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('89', '收款', '2', '4', '0.000000', '11.000000', null, 'SK20171030232535', '2017-10-30 23:25:35', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('90', '收款', '2', '4', '0.000000', '10.000000', null, 'SK20171119231440', '2017-11-19 23:14:40', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('91', '收入', '48', '9', '66.000000', '6.000000', '13', 'SR20190319221438', '2019-03-19 22:14:38', '', '1', '0');
INSERT INTO `jsh_accounthead` VALUES ('92', '支出', '50', '9', '-33.000000', '-33.000000', '13', 'ZC20190319221454', '2019-03-19 22:14:54', '', '1', '0');
INSERT INTO `jsh_accounthead` VALUES ('93', '收款', '48', '9', null, '44.000000', null, 'SK20190319221513', '2019-03-19 22:15:13', '', '1', '0');
INSERT INTO `jsh_accounthead` VALUES ('94', '付款', '50', '9', null, '-66.000000', null, 'FK20190319221525', '2019-03-19 22:15:25', '', '1', '0');
INSERT INTO `jsh_accounthead` VALUES ('95', '收预付款', '49', '9', null, '6.000000', null, 'SYF20190319221556', '2019-03-19 22:15:56', '', '1', '0');
INSERT INTO `jsh_accounthead` VALUES ('96', '收入', '5', '4', '22.000000', '22.000000', '12', 'SR20190321235925', '2019-03-21 23:59:25', '', null, '0');
INSERT INTO `jsh_accounthead` VALUES ('97', '收入', '58', '16', '10.000000', '10.000000', '17', 'SR20191228121609', '2019-12-28 12:16:09', '', '63', '0');
INSERT INTO `jsh_accounthead` VALUES ('98', '支出', '57', '16', '-20.000000', '-20.000000', '17', 'ZC20191228121854', '2019-12-28 12:18:54', '', '63', '0');
INSERT INTO `jsh_accounthead` VALUES ('99', '收款', '58', '16', null, '20.000000', null, 'SK20191228121908', '2019-12-28 12:19:08', '', '63', '0');
INSERT INTO `jsh_accounthead` VALUES ('100', '付款', '68', '16', null, '-20.000000', null, 'FK20191228121920', '2019-12-28 12:19:20', '', '63', '0');
INSERT INTO `jsh_accounthead` VALUES ('101', '转账', null, '16', '-20.000000', '-20.000000', '18', 'ZZ20191228121932', '2019-12-28 12:19:32', '', '63', '0');
INSERT INTO `jsh_accounthead` VALUES ('102', '收预付款', '60', '16', null, '1000.000000', null, 'SYF20191228121945', '2019-12-28 12:19:45', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_accountitem
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accountitem`;
CREATE TABLE `jsh_accountitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `InOutItemId` bigint(20) DEFAULT NULL COMMENT '收支项目Id',
  `EachAmount` decimal(24,6) DEFAULT NULL COMMENT '单项金额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '单据备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`),
  KEY `FK9F4CBAC0AAE50527` (`AccountId`),
  KEY `FK9F4CBAC0C5FE6007` (`HeaderId`),
  KEY `FK9F4CBAC0D203EDC5` (`InOutItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='财务子表';

-- ----------------------------
-- Records of jsh_accountitem
-- ----------------------------
INSERT INTO `jsh_accountitem` VALUES ('58', '57', '9', null, '1000.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('62', '61', '4', null, '33.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('68', '67', '4', null, '2100.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('71', '70', null, '11', '60.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('75', '74', '9', null, '100.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('78', '77', null, '14', '40.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('79', '78', '9', null, '200.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('80', '79', '9', null, '100.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('83', '82', '10', null, '2.600000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('84', '83', '10', null, '-20.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('85', '84', null, '13', '21.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('86', '85', null, '12', '22.000000', '44', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('87', '86', '11', null, '22.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('88', '87', '10', null, '-33.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('89', '88', '10', null, '2.800000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('90', '89', '11', null, '11.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('91', '90', '12', null, '10.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('92', '91', null, '16', '66.000000', '', '1', '0');
INSERT INTO `jsh_accountitem` VALUES ('93', '92', null, '17', '33.000000', '', '1', '0');
INSERT INTO `jsh_accountitem` VALUES ('94', '93', '13', null, '44.000000', '', '1', '0');
INSERT INTO `jsh_accountitem` VALUES ('95', '94', '13', null, '-66.000000', '', '1', '0');
INSERT INTO `jsh_accountitem` VALUES ('96', '95', '13', null, '6.000000', '', '1', '0');
INSERT INTO `jsh_accountitem` VALUES ('97', '96', null, '14', '22.000000', '', null, '0');
INSERT INTO `jsh_accountitem` VALUES ('98', '97', null, '22', '10.000000', '', '63', '0');
INSERT INTO `jsh_accountitem` VALUES ('99', '98', null, '21', '20.000000', '', '63', '0');
INSERT INTO `jsh_accountitem` VALUES ('100', '99', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_accountitem` VALUES ('101', '100', '17', null, '-20.000000', '', '63', '0');
INSERT INTO `jsh_accountitem` VALUES ('102', '101', '17', null, '20.000000', '', '63', '0');
INSERT INTO `jsh_accountitem` VALUES ('103', '102', '17', null, '1000.000000', '', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='仓库表';

-- ----------------------------
-- Records of jsh_depot
-- ----------------------------
INSERT INTO `jsh_depot` VALUES ('1', '叠石桥店', '地址222', '33.000000', '22.000000', '0', '2', '上海33', '95', null, '0', null);
INSERT INTO `jsh_depot` VALUES ('2', '公司总部', '地址12355', '44.000000', '22.220000', '0', '1', '总部', '64', null, '0', null);
INSERT INTO `jsh_depot` VALUES ('3', '金沙店', '地址666', '31.000000', '4.000000', '0', '3', '苏州', '64', null, '0', null);
INSERT INTO `jsh_depot` VALUES ('4', '1268200294', '', null, null, '1', '1', '', null, null, '0', null);
INSERT INTO `jsh_depot` VALUES ('5', '1268787965', null, null, null, '1', '3', '', null, null, '0', null);
INSERT INTO `jsh_depot` VALUES ('6', '1269520625', null, null, null, '1', '2', '', null, null, '0', null);
INSERT INTO `jsh_depot` VALUES ('7', '仓库1', '', null, null, '0', '', '', null, '1', '0', null);
INSERT INTO `jsh_depot` VALUES ('8', '仓库1111', '', null, null, '0', '', '', '117', '117', '0', null);
INSERT INTO `jsh_depot` VALUES ('9', '仓库567', '', null, null, '0', '', '', '117', '117', '0', null);
INSERT INTO `jsh_depot` VALUES ('10', '仓库321342', '', null, null, '0', '', '', null, '117', '0', null);
INSERT INTO `jsh_depot` VALUES ('11', '仓库321321', '', null, null, '0', '', '', null, '117', '0', null);
INSERT INTO `jsh_depot` VALUES ('12', '仓库111222', '', null, null, '0', '', '', null, '117', '0', null);
INSERT INTO `jsh_depot` VALUES ('13', '仓库1', '', null, null, '0', '', '', null, '115', '0', null);
INSERT INTO `jsh_depot` VALUES ('14', '仓库1', '', null, null, '0', '', '', null, '63', '0', '');
INSERT INTO `jsh_depot` VALUES ('15', '仓库2', '', null, null, '0', '', '', null, '63', '0', '\0');

-- ----------------------------
-- Table structure for jsh_depothead
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depothead`;
CREATE TABLE `jsh_depothead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(出库/入库)',
  `SubType` varchar(50) DEFAULT NULL COMMENT '出入库分类',
  `DefaultNumber` varchar(50) DEFAULT NULL COMMENT '初始票据号',
  `Number` varchar(50) DEFAULT NULL COMMENT '票据号',
  `OperPersonName` varchar(50) DEFAULT NULL COMMENT '操作员名字',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `OperTime` datetime DEFAULT NULL COMMENT '出入库时间',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '供应商Id',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '采购/领料-经手人Id',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `ChangeAmount` decimal(24,6) DEFAULT NULL COMMENT '变动金额(收款/付款)',
  `TotalPrice` decimal(24,6) DEFAULT NULL COMMENT '合计金额',
  `PayType` varchar(50) DEFAULT NULL COMMENT '付款类型(现金、记账等)',
  `Remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `Salesman` varchar(50) DEFAULT NULL COMMENT '业务员（可以多个）',
  `AccountIdList` varchar(50) DEFAULT NULL COMMENT '多账户ID列表',
  `AccountMoneyList` varchar(200) DEFAULT '' COMMENT '多账户金额列表',
  `Discount` decimal(24,6) DEFAULT NULL COMMENT '优惠率',
  `DiscountMoney` decimal(24,6) DEFAULT NULL COMMENT '优惠金额',
  `DiscountLastMoney` decimal(24,6) DEFAULT NULL COMMENT '优惠后金额',
  `OtherMoney` decimal(24,6) DEFAULT NULL COMMENT '销售或采购费用合计',
  `OtherMoneyList` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目Id数组（包括快递、招待等）',
  `OtherMoneyItem` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目（包括快递、招待等）',
  `AccountDay` int(10) DEFAULT NULL COMMENT '结算天数',
  `Status` varchar(1) DEFAULT '0' COMMENT '状态，0未审核、1已审核、2已转采购|销售',
  `LinkNumber` varchar(50) DEFAULT NULL COMMENT '关联订单号',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`),
  KEY `FK2A80F214C4170B37` (`HandsPersonId`),
  KEY `FK2A80F214B610FC06` (`OrganId`),
  KEY `FK2A80F214AAE50527` (`AccountId`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8 COMMENT='单据主表';

-- ----------------------------
-- Records of jsh_depothead
-- ----------------------------
INSERT INTO `jsh_depothead` VALUES ('165', '入库', '采购', 'CGRK00000000190', 'CGRK00000000190', 'lili', '2019-03-19 22:10:17', '2019-03-19 22:09:49', '47', null, '13', '-220.000000', '-220.000000', '现付', '', '', null, '', '0.000000', '0.000000', '220.000000', null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('166', '其它', '采购订单', 'CGDD00000000191', 'CGDD00000000191', 'lili', '2019-03-19 22:10:35', '2019-03-19 22:10:22', '50', null, null, '0.000000', '-2442.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('167', '出库', '采购退货', 'CGTH00000000193', 'CGTH00000000193', 'lili', '2019-03-19 22:11:39', '2019-03-19 22:11:12', '47', null, '13', '110.000000', '110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('168', '其它', '销售订单', 'XSDD00000000194', 'XSDD00000000194', 'lili', '2019-03-19 22:12:04', '2019-03-19 22:11:55', '48', null, null, '0.000000', '22.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('169', '出库', '销售', 'XSCK00000000195', 'XSCK00000000195', 'lili', '2019-03-19 22:12:18', '2019-03-19 22:12:09', '48', null, '13', '22.000000', '22.000000', '现付', '', '', null, '', '0.000000', '0.000000', '22.000000', null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('170', '入库', '销售退货', 'XSTH00000000196', 'XSTH00000000196', 'lili', '2019-03-19 22:12:29', '2019-03-19 22:12:21', '48', null, '13', '-22.000000', '-22.000000', '现付', '', '', null, '', '0.000000', '0.000000', '22.000000', null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('171', '出库', '零售', 'LSCK00000000197', 'LSCK00000000197', 'lili', '2019-03-19 22:12:43', '2019-03-19 22:12:35', '49', null, '13', '22.000000', '22.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('172', '入库', '零售退货', 'LSTH00000000198', 'LSTH00000000198', 'lili', '2019-03-19 22:12:53', '2019-03-19 22:12:46', '49', null, '13', '-22.000000', '-22.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('173', '入库', '其它', 'QTRK00000000199', 'QTRK00000000199', 'lili', '2019-03-19 22:13:20', '2019-03-19 22:13:09', '50', null, null, '0.000000', '2200.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('174', '出库', '其它', 'QTCK00000000200', 'QTCK00000000200', 'lili', '2019-03-19 22:13:34', '2019-03-19 22:13:23', '48', null, null, '0.000000', '176.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '1', '0');
INSERT INTO `jsh_depothead` VALUES ('180', '入库', '采购', 'CGRK00000000242', 'CGRK00000000242', 'laoba123', '2019-04-02 22:30:01', '2019-04-02 22:29:52', '55', null, '16', '-1221.000000', '-1221.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1221.000000', null, null, null, null, '0', '', '115', '0');
INSERT INTO `jsh_depothead` VALUES ('181', '入库', '采购', 'CGRK00000000243', 'CGRK00000000243', 'laoba123', '2019-04-02 22:30:20', '2019-04-02 22:30:03', '55', null, '16', '-1342.000000', '-1342.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1342.000000', null, null, null, null, '0', '', '115', '0');
INSERT INTO `jsh_depothead` VALUES ('189', '入库', '采购', 'CGRK00000000261', 'CGRK00000000261', '季圣华', '2019-04-10 22:25:49', '2020-02-20 23:51:03', '57', null, '17', '-120.000000', '-120.000000', '现付', '', '', null, '', '0.000000', '0.000000', '120.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('190', '入库', '采购', 'CGRK00000000263', 'CGRK00000000263', '季圣华', '2019-04-13 19:57:43', '2019-04-13 19:57:32', '57', null, '17', '-24.000000', '-24.000000', '现付', '', '', null, '', '0.000000', '0.000000', '24.000000', null, null, null, null, '0', '', '63', '1');
INSERT INTO `jsh_depothead` VALUES ('191', '入库', '采购', 'CGRK00000000264', 'CGRK00000000264', '季圣华', '2019-04-13 19:57:58', '2020-02-20 23:50:55', '57', null, '17', '-10.000000', '-10.000000', '现付', '', '', null, '', '0.000000', '0.000000', '10.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('192', '入库', '采购', 'CGRK00000000265', 'CGRK00000000265', '季圣华', '2019-04-20 00:36:24', '2020-02-20 23:50:47', '57', null, '17', '-220.000000', '-220.000000', '现付', '', '', null, '', '0.000000', '0.000000', '220.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('193', '出库', '销售', 'XSCK00000000268', 'XSCK00000000268', '季圣华', '2019-04-29 23:41:02', '2020-02-20 23:52:17', '58', null, '17', '300.000000', '300.000000', '现付', '', '', null, '', '0.000000', '0.000000', '300.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('194', '入库', '采购', 'CGRK00000000272', 'CGRK00000000272', '季圣华', '2019-04-30 22:33:24', '2020-02-20 23:50:28', '57', null, '17', '-1000.000000', '-1000.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1000.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('195', '入库', '采购', 'CGRK00000000273', 'CGRK00000000273', '季圣华', '2019-04-30 22:34:45', '2020-02-20 23:49:49', '57', null, '17', '-1220.000000', '-1220.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1220.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('196', '入库', '采购', 'CGRK00000000274', 'CGRK00000000274', '季圣华', '2019-04-30 22:35:53', '2020-02-20 23:49:07', '57', null, '18', '-1930.000000', '-1930.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1930.000000', '0.000000', '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('197', '出库', '销售', 'XSCK00000000290', 'XSCK00000000290', '季圣华', '2019-04-30 23:15:27', '2020-02-20 23:52:01', '58', null, '17', '270.000000', '270.000000', '现付', '', '', null, '', '0.000000', '0.000000', '270.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('198', '入库', '采购', 'CGRK00000000292', 'CGRK00000000292', '季圣华', '2019-05-03 14:20:56', '2019-05-03 14:19:38', '57', null, '17', '-1.120000', '-1.000000', '现付', '', '', null, '', '0.000000', '0.000000', '1.120000', null, null, null, null, '0', '', '63', '1');
INSERT INTO `jsh_depothead` VALUES ('199', '其它', '采购订单', 'CGDD00000000305', 'CGDD00000000305', '季圣华', '2019-12-28 12:16:36', '2020-02-20 23:47:56', '57', '63', null, '0.000000', '-11.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('200', '出库', '采购退货', 'CGTH00000000306', 'CGTH00000000306', '季圣华', '2019-12-28 12:16:55', '2020-02-20 23:51:28', '57', '63', '17', '11.000000', '11.000000', '现付', '', '', null, '', '0.000000', '0.000000', '11.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('201', '其它', '销售订单', 'XSDD00000000307', 'XSDD00000000307', '季圣华', '2019-12-28 12:17:09', '2020-02-20 23:51:37', '58', '63', null, '0.000000', '15.000000', '现付', '', '<14>', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('202', '入库', '销售退货', 'XSTH00000000308', 'XSTH00000000308', '季圣华', '2019-12-28 12:17:22', '2020-02-20 23:52:33', '58', '63', '17', '-15.000000', '-15.000000', '现付', '', '', null, '', '0.000000', '0.000000', '15.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('203', '入库', '其它', 'QTRK00000000309', 'QTRK00000000309', '季圣华', '2019-12-28 12:17:40', '2020-02-20 23:52:51', '57', '63', null, '0.000000', '21.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('204', '出库', '其它', 'QTCK00000000310', 'QTCK00000000310', '季圣华', '2019-12-28 12:17:48', '2020-02-20 23:53:04', '58', '63', null, '0.000000', '15.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('205', '出库', '调拨', 'DBCK00000000311', 'DBCK00000000311', '季圣华', '2019-12-28 12:17:58', '2020-02-20 23:53:21', null, '63', null, '0.000000', '15.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('206', '其它', '组装单', 'ZZD00000000312', 'ZZD00000000312', '季圣华', '2019-12-28 12:18:09', '2020-02-20 23:54:02', null, '63', null, '0.000000', '0.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('207', '其它', '拆卸单', 'CXD00000000313', 'CXD00000000313', '季圣华', '2019-12-28 12:18:47', '2020-02-20 23:54:21', null, '63', null, '0.000000', '0.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('208', '出库', '零售', 'LSCK00000000314', 'LSCK00000000314', '季圣华', '2019-12-28 12:20:26', '2019-12-28 12:20:14', '60', '63', '17', '6.000000', '6.000000', '预付款', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('209', '入库', '零售退货', 'LSTH00000000315', 'LSTH00000000315', '季圣华', '2019-12-28 12:20:39', '2019-12-28 12:20:29', '60', '63', '17', '-6.000000', '-6.000000', '现付', '', '', null, '', null, null, null, null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('210', '入库', '采购', 'CGRK00000000318', 'CGRK00000000318', '季圣华', '2020-02-20 23:22:38', '2020-02-20 23:22:27', '57', '63', '17', '-110.000000', '-110.000000', '现付', '', '', null, '', '0.000000', '0.000000', '110.000000', null, null, null, null, '0', '', '63', '0');
INSERT INTO `jsh_depothead` VALUES ('211', '入库', '采购', 'CGRK00000000319', 'CGRK00000000319', '季圣华', '2020-02-20 23:54:48', '2020-02-20 23:54:33', '57', '63', '17', '-2400.000000', '-2400.000000', '现付', '', '', null, '', '0.000000', '0.000000', '2400.000000', null, '[\"undefined\"]', '[\"undefined\"]', null, '0', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_depotitem
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depotitem`;
CREATE TABLE `jsh_depotitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `MaterialId` bigint(20) NOT NULL COMMENT '材料Id',
  `material_extend_id` bigint(20) DEFAULT NULL COMMENT '商品扩展id',
  `MUnit` varchar(20) DEFAULT NULL COMMENT '商品计量单位',
  `OperNumber` decimal(24,6) DEFAULT NULL COMMENT '数量',
  `BasicNumber` decimal(24,6) DEFAULT NULL COMMENT '基础数量，如kg、瓶',
  `UnitPrice` decimal(24,6) DEFAULT NULL COMMENT '单价',
  `TaxUnitPrice` decimal(24,6) DEFAULT NULL COMMENT '含税单价',
  `AllPrice` decimal(24,6) DEFAULT NULL COMMENT '金额',
  `Remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `Img` varchar(50) DEFAULT NULL COMMENT '图片',
  `Incidentals` decimal(24,6) DEFAULT NULL COMMENT '运杂费',
  `DepotId` bigint(20) DEFAULT NULL COMMENT '仓库ID（库存是统计出来的）',
  `AnotherDepotId` bigint(20) DEFAULT NULL COMMENT '调拨时，对方仓库Id',
  `TaxRate` decimal(24,6) DEFAULT NULL COMMENT '税率',
  `TaxMoney` decimal(24,6) DEFAULT NULL COMMENT '税额',
  `TaxLastMoney` decimal(24,6) DEFAULT NULL COMMENT '价税合计',
  `OtherField1` varchar(50) DEFAULT NULL COMMENT '自定义字段1-名称',
  `OtherField2` varchar(50) DEFAULT NULL COMMENT '自定义字段2-型号',
  `OtherField3` varchar(50) DEFAULT NULL COMMENT '自定义字段3-制造商',
  `OtherField4` varchar(50) DEFAULT NULL COMMENT '自定义字段4',
  `OtherField5` varchar(50) DEFAULT NULL COMMENT '自定义字段5',
  `MType` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`),
  KEY `FK2A819F475D61CCF7` (`MaterialId`),
  KEY `FK2A819F474BB6190E` (`HeaderId`),
  KEY `FK2A819F479485B3F5` (`DepotId`),
  KEY `FK2A819F47729F5392` (`AnotherDepotId`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='单据子表';

-- ----------------------------
-- Records of jsh_depotitem
-- ----------------------------
INSERT INTO `jsh_depotitem` VALUES ('172', '165', '564', null, '个', '10.000000', '10.000000', '22.000000', '22.000000', '220.000000', '', null, null, '7', null, '0.000000', '0.000000', '220.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('173', '166', '564', null, '个', '111.000000', '111.000000', '22.000000', '22.000000', '2442.000000', '', null, null, '7', null, '0.000000', '0.000000', '2442.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('174', '167', '564', null, '个', '5.000000', '5.000000', '22.000000', '22.000000', '110.000000', '', null, null, '7', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('175', '168', '564', null, '个', '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', '', null, null, '7', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('176', '169', '564', null, '个', '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', '', null, null, '7', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('177', '170', '564', null, '个', '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', '', null, null, '7', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('178', '171', '564', null, '个', '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', '', null, null, '7', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('179', '172', '564', null, '个', '1.000000', '1.000000', '22.000000', '22.000000', '22.000000', '', null, null, '7', null, '0.000000', '0.000000', '22.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('180', '173', '564', null, '个', '100.000000', '100.000000', '22.000000', '22.000000', '2200.000000', '', null, null, '7', null, '0.000000', '0.000000', '2200.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('181', '174', '564', null, '个', '8.000000', '8.000000', '22.000000', '22.000000', '176.000000', '', null, null, '7', null, '0.000000', '0.000000', '176.000000', '', '', '', '', '', '', '1', '0');
INSERT INTO `jsh_depotitem` VALUES ('187', '180', '567', null, '个', '111.000000', null, '11.000000', '11.000000', '1221.000000', '', null, null, '13', null, '0.000000', '0.000000', '1221.000000', '', '', '', '', '', '', '115', '0');
INSERT INTO `jsh_depotitem` VALUES ('188', '181', '567', null, '个', '122.000000', null, '11.000000', '11.000000', '1342.000000', '', null, null, '13', null, '0.000000', '0.000000', '1342.000000', '', '', '', '', '', '', '115', '0');
INSERT INTO `jsh_depotitem` VALUES ('198', '189', '569', '3', '只', '12.000000', '12.000000', '10.000000', '10.000000', '120.000000', '', null, null, '14', null, '0.000000', '0.000000', '120.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('199', '190', '569', null, '只', '12.000000', '12.000000', '2.000000', '2.000000', '24.000000', '', null, null, '14', null, '0.000000', '0.000000', '24.000000', '', '', '', '', '', '', '63', '1');
INSERT INTO `jsh_depotitem` VALUES ('200', '191', '569', '3', '只', '1.000000', '1.000000', '10.000000', '10.000000', '10.000000', '', null, null, '14', null, '0.000000', '0.000000', '10.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('201', '192', '569', '3', '只', '22.000000', '22.000000', '10.000000', '10.000000', '220.000000', '', null, null, '14', null, '0.000000', '0.000000', '220.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('202', '193', '569', '3', '只', '20.000000', '0.000000', '15.000000', '15.000000', '300.000000', '', null, null, '14', null, '0.000000', '0.000000', '300.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('203', '194', '569', '3', '只', '100.000000', '100.000000', '10.000000', '10.000000', '1000.000000', '', null, null, '15', null, '0.000000', '0.000000', '1000.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('204', '195', '569', '3', '只', '122.000000', '122.000000', '10.000000', '10.000000', '1220.000000', '', null, null, '15', null, '0.000000', '0.000000', '1220.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('205', '196', '569', '3', '只', '2.000000', '122.000000', '10.000000', '10.000000', '20.000000', '', null, null, '15', null, '0.000000', '0.000000', '20.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('206', '197', '569', '3', '只', '18.000000', '0.000000', '15.000000', '15.000000', '270.000000', '', null, null, '14', null, '0.000000', '0.000000', '270.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('207', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '15', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('208', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '15', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('209', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('210', '196', '568', '2', '个', '10.000000', '2.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('211', '196', '568', '2', '个', '10.000000', '3.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('212', '196', '568', '2', '个', '10.000000', '4.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('213', '196', '568', '2', '个', '100.000000', '5.000000', '11.000000', '11.000000', '1100.000000', '', null, null, '14', null, '0.000000', '0.000000', '1100.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('214', '196', '569', '3', '只', '15.000000', '6.000000', '10.000000', '10.000000', '150.000000', '', null, null, '14', null, '0.000000', '0.000000', '150.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('215', '198', '578', null, '箱', '1.000000', '12.000000', '1.000000', '1.120000', '1.000000', '', null, null, '14', null, '12.000000', '0.120000', '1.120000', '', '', '', '', '', '', '63', '1');
INSERT INTO `jsh_depotitem` VALUES ('216', '199', '568', '2', '个', '1.000000', '1.000000', '11.000000', '11.000000', '11.000000', '', null, null, '14', null, '0.000000', '0.000000', '11.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('217', '200', '568', '2', '个', '1.000000', '0.000000', '11.000000', '11.000000', '11.000000', '', null, null, '14', null, '0.000000', '0.000000', '11.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('218', '201', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('219', '202', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('220', '203', '568', '2', '个', '1.000000', '1.000000', '11.000000', '11.000000', '11.000000', '', null, null, '14', null, '0.000000', '0.000000', '11.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('221', '203', '569', '3', '只', '1.000000', '1.000000', '10.000000', '10.000000', '10.000000', '', null, null, '14', null, '0.000000', '0.000000', '10.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('222', '204', '569', '3', '只', '1.000000', '0.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', null, '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('223', '205', '568', '2', '个', '1.000000', '1.000000', '15.000000', '15.000000', '15.000000', '', null, null, '14', '15', '0.000000', '0.000000', '15.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('224', '206', '568', '2', '个', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '组合件', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('225', '206', '569', '3', '只', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '普通子件', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('226', '207', '569', '3', '只', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '组合件', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('227', '207', '568', '2', '个', '1.000000', '1.000000', '0.000000', '0.000000', '0.000000', '', null, null, '14', null, '0.000000', '0.000000', '0.000000', '', '', '', '', '', '普通子件', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('228', '208', '568', null, '个', '1.000000', '1.000000', '6.000000', '6.000000', '6.000000', '', null, null, '14', null, '0.000000', '0.000000', '6.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('229', '209', '568', null, '个', '1.000000', '1.000000', '6.000000', '6.000000', '6.000000', '', null, null, '14', null, '0.000000', '0.000000', '6.000000', '', '', '', '', '', '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('230', '210', '587', '1', '个', '10.000000', '10.000000', '11.000000', '11.000000', '110.000000', '', null, null, '14', null, '0.000000', '0.000000', '110.000000', null, null, null, null, null, '', '63', '0');
INSERT INTO `jsh_depotitem` VALUES ('231', '211', '579', '8', '箱', '10.000000', '120.000000', '240.000000', '240.000000', '2400.000000', '', null, null, '14', null, '0.000000', '0.000000', '2400.000000', null, null, null, null, null, '', '63', '0');

-- ----------------------------
-- Table structure for jsh_functions
-- ----------------------------
DROP TABLE IF EXISTS `jsh_functions`;
CREATE TABLE `jsh_functions` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Number` varchar(50) DEFAULT NULL COMMENT '编号',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `PNumber` varchar(50) DEFAULT NULL COMMENT '上级编号',
  `URL` varchar(100) DEFAULT NULL COMMENT '链接',
  `State` bit(1) DEFAULT NULL COMMENT '收缩',
  `Sort` varchar(50) DEFAULT NULL COMMENT '排序',
  `Enabled` bit(1) DEFAULT NULL COMMENT '启用',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型',
  `PushBtn` varchar(50) DEFAULT NULL COMMENT '功能按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of jsh_functions
-- ----------------------------
INSERT INTO `jsh_functions` VALUES ('1', '0001', '系统管理', '0', '', '', '0910', '', '电脑版', '', 'icon-settings', '0');
INSERT INTO `jsh_functions` VALUES ('13', '000102', '角色管理', '0001', '/pages/manage/role.html', '\0', '0130', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('14', '000103', '用户管理', '0001', '/pages/manage/user.html', '\0', '0140', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('15', '000104', '日志管理', '0001', '/pages/manage/log.html', '\0', '0160', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('16', '000105', '功能管理', '0001', '/pages/manage/functions.html', '\0', '0135', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('21', '0101', '商品管理', '0', '', '\0', '0620', '', '电脑版', null, 'icon-social-dropbox', '0');
INSERT INTO `jsh_functions` VALUES ('22', '010101', '商品类别', '0101', '/pages/materials/materialcategory.html', '\0', '0230', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('23', '010102', '商品信息', '0101', '/pages/materials/material.html', '\0', '0240', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('24', '0102', '基本资料', '0', '', '\0', '0750', '', '电脑版', null, 'icon-grid', '0');
INSERT INTO `jsh_functions` VALUES ('25', '01020101', '供应商信息', '0102', '/pages/manage/vendor.html', '\0', '0260', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('26', '010202', '仓库信息', '0102', '/pages/manage/depot.html', '\0', '0270', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('31', '010206', '经手人管理', '0102', '/pages/materials/person.html', '\0', '0284', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('32', '0502', '采购管理', '0', '', '\0', '0330', '', '电脑版', '', 'icon-loop', '0');
INSERT INTO `jsh_functions` VALUES ('33', '050201', '采购入库', '0502', '/pages/materials/purchase_in_list.html', '\0', '0340', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('38', '0603', '销售管理', '0', '', '\0', '0390', '', '电脑版', '', 'icon-briefcase', '0');
INSERT INTO `jsh_functions` VALUES ('40', '080107', '调拨出库', '0801', '/pages/materials/allocation_out_list.html', '\0', '0807', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('41', '060303', '销售出库', '0603', '/pages/materials/sale_out_list.html', '\0', '0394', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('44', '0704', '财务管理', '0', '', '\0', '0450', '', '电脑版', '', 'icon-map', '0');
INSERT INTO `jsh_functions` VALUES ('59', '030101', '库存状况', '0301', '/pages/reports/in_out_stock_report.html', '\0', '0600', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('194', '010204', '收支项目', '0102', '/pages/manage/inOutItem.html', '\0', '0282', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('195', '010205', '结算账户', '0102', '/pages/manage/account.html', '\0', '0283', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('197', '070402', '收入单', '0704', '/pages/financial/item_in.html', '\0', '0465', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('198', '0301', '报表查询', '0', '', '\0', '0570', '', '电脑版', null, 'icon-pie-chart', '0');
INSERT INTO `jsh_functions` VALUES ('199', '050204', '采购退货', '0502', '/pages/materials/purchase_back_list.html', '\0', '0345', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('200', '060305', '销售退货', '0603', '/pages/materials/sale_back_list.html', '\0', '0396', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('201', '080103', '其它入库', '0801', '/pages/materials/other_in_list.html', '\0', '0803', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('202', '080105', '其它出库', '0801', '/pages/materials/other_out_list.html', '\0', '0805', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('203', '070403', '支出单', '0704', '/pages/financial/item_out.html', '\0', '0470', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('204', '070404', '收款单', '0704', '/pages/financial/money_in.html', '\0', '0475', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('205', '070405', '付款单', '0704', '/pages/financial/money_out.html', '\0', '0480', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('206', '070406', '转账单', '0704', '/pages/financial/giro.html', '\0', '0490', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('207', '030102', '账户统计', '0301', '/pages/reports/account_report.html', '\0', '0610', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('208', '030103', '进货统计', '0301', '/pages/reports/buy_in_report.html', '\0', '0620', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('209', '030104', '销售统计', '0301', '/pages/reports/sale_out_report.html', '\0', '0630', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('210', '040102', '零售出库', '0401', '/pages/materials/retail_out_list.html', '\0', '0405', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('211', '040104', '零售退货', '0401', '/pages/materials/retail_back_list.html', '\0', '0407', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('212', '070407', '收预付款', '0704', '/pages/financial/advance_in.html', '\0', '0495', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('217', '01020102', '客户信息', '0102', '/pages/manage/customer.html', '\0', '0262', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('218', '01020103', '会员信息', '0102', '/pages/manage/member.html', '\0', '0263', '', '电脑版', '1,2', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('220', '010103', '计量单位', '0101', '/pages/manage/unit.html', '\0', '0245', '', '电脑版', null, 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('225', '0401', '零售管理', '0', '', '\0', '0101', '', '电脑版', '', 'icon-present', '0');
INSERT INTO `jsh_functions` VALUES ('226', '030106', '入库明细', '0301', '/pages/reports/in_detail.html', '\0', '0640', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('227', '030107', '出库明细', '0301', '/pages/reports/out_detail.html', '\0', '0645', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('228', '030108', '入库汇总', '0301', '/pages/reports/in_material_count.html', '\0', '0650', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('229', '030109', '出库汇总', '0301', '/pages/reports/out_material_count.html', '\0', '0655', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('232', '080109', '组装单', '0801', '/pages/materials/assemble_list.html', '\0', '0809', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('233', '080111', '拆卸单', '0801', '/pages/materials/disassemble_list.html', '\0', '0811', '', '电脑版', '3,4,5', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('234', '000105', '系统配置', '0001', '/pages/manage/systemConfig.html', '\0', '0165', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('235', '030110', '客户对账', '0301', '/pages/reports/customer_account.html', '\0', '0660', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('236', '000106', '商品属性', '0001', '/pages/materials/materialProperty.html', '\0', '0168', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('237', '030111', '供应商对账', '0301', '/pages/reports/vendor_account.html', '\0', '0665', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('239', '0801', '仓库管理', '0', '', '\0', '0420', '', '电脑版', '', 'icon-layers', '0');
INSERT INTO `jsh_functions` VALUES ('240', '010104', '序列号', '0101', '/pages/manage/serialNumber.html', '\0', '0246', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('241', '050202', '采购订单', '0502', '/pages/materials/purchase_orders_list.html', '\0', '0335', '', '电脑版', '3', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('242', '060301', '销售订单', '0603', '/pages/materials/sale_orders_list.html', '\0', '0392', '', '电脑版', '3', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('243', '000108', '机构管理', '0001', '/pages/manage/organization.html', '', '0139', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('244', '030112', '库存预警', '0301', '/pages/reports/stock_warning_report.html', '\0', '0670', '', '电脑版', '', 'icon-notebook', '0');
INSERT INTO `jsh_functions` VALUES ('245', '000107', '插件管理', '0001', '/pages/manage/plugin.html', '\0', '0170', '', '电脑版', '', 'icon-notebook', '0');

-- ----------------------------
-- Table structure for jsh_inoutitem
-- ----------------------------
DROP TABLE IF EXISTS `jsh_inoutitem`;
CREATE TABLE `jsh_inoutitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='收支项目';

-- ----------------------------
-- Records of jsh_inoutitem
-- ----------------------------
INSERT INTO `jsh_inoutitem` VALUES ('1', '办公耗材', '支出', '办公耗材', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('5', '房租收入', '收入', '房租收入', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('7', '利息收入', '收入', '利息收入', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('8', '水电费', '支出', '水电费水电费', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('9', '快递费', '支出', '快递费', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('10', '交通报销费', '支出', '交通报销费', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('11', '差旅费', '支出', '差旅费', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('12', '全车贴膜-普通', '收入', '', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('13', '全车贴膜-高档', '收入', '', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('14', '洗车', '收入', '', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('15', '保养汽车', '收入', '', null, '0');
INSERT INTO `jsh_inoutitem` VALUES ('16', '收入项目1', '收入', '', '1', '0');
INSERT INTO `jsh_inoutitem` VALUES ('17', '支出项目1', '支出', '', '1', '0');
INSERT INTO `jsh_inoutitem` VALUES ('18', '收入1', '收入', '', '117', '0');
INSERT INTO `jsh_inoutitem` VALUES ('19', '支出1', '支出', '', '117', '0');
INSERT INTO `jsh_inoutitem` VALUES ('20', '支出2', '支出', '', '117', '0');
INSERT INTO `jsh_inoutitem` VALUES ('21', '支出1', '支出', '', '63', '0');
INSERT INTO `jsh_inoutitem` VALUES ('22', '收入1', '收入', '', '63', '0');

-- ----------------------------
-- Table structure for jsh_log
-- ----------------------------
DROP TABLE IF EXISTS `jsh_log`;
CREATE TABLE `jsh_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userID` bigint(20) NOT NULL COMMENT '操作用户ID',
  `operation` varchar(500) DEFAULT NULL COMMENT '操作模块名称',
  `clientIP` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '操作状态 0==成功，1==失败',
  `contentdetails` varchar(1000) DEFAULT NULL COMMENT '操作详情',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `FKF2696AA13E226853` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=6504 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of jsh_log
-- ----------------------------
INSERT INTO `jsh_log` VALUES ('6441', '63', '商品', '127.0.0.1', '2020-02-20 23:47:04', '0', '修改,id:579商品', null, '63');
INSERT INTO `jsh_log` VALUES ('6442', '63', '商品价格扩展', '127.0.0.1', '2020-02-20 23:47:23', '0', '新增商品价格扩展', null, '63');
INSERT INTO `jsh_log` VALUES ('6443', '63', '商品价格扩展', '127.0.0.1', '2020-02-20 23:47:23', '0', '删除,id:商品价格扩展', null, '63');
INSERT INTO `jsh_log` VALUES ('6444', '63', '商品', '127.0.0.1', '2020-02-20 23:47:23', '0', '修改,id:586商品', null, '63');
INSERT INTO `jsh_log` VALUES ('6445', '63', '单据', '127.0.0.1', '2020-02-20 23:47:56', '0', '修改,id:199单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6446', '63', '单据明细', '127.0.0.1', '2020-02-20 23:47:56', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6447', '63', '单据明细', '127.0.0.1', '2020-02-20 23:47:56', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6448', '63', '单据', '127.0.0.1', '2020-02-20 23:49:07', '0', '修改,id:196单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6449', '63', '单据明细', '127.0.0.1', '2020-02-20 23:49:07', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6450', '63', '单据明细', '127.0.0.1', '2020-02-20 23:49:07', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6451', '63', '单据', '127.0.0.1', '2020-02-20 23:49:49', '0', '修改,id:195单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6452', '63', '单据明细', '127.0.0.1', '2020-02-20 23:49:49', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6453', '63', '单据明细', '127.0.0.1', '2020-02-20 23:49:49', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6454', '63', '单据', '127.0.0.1', '2020-02-20 23:50:28', '0', '修改,id:194单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6455', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:28', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6456', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:28', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6457', '63', '单据', '127.0.0.1', '2020-02-20 23:50:47', '0', '修改,id:192单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6458', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:47', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6459', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:47', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6460', '63', '单据', '127.0.0.1', '2020-02-20 23:50:55', '0', '修改,id:191单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6461', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:55', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6462', '63', '单据明细', '127.0.0.1', '2020-02-20 23:50:55', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6463', '63', '单据', '127.0.0.1', '2020-02-20 23:51:03', '0', '修改,id:189单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6464', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:03', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6465', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:03', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6466', '63', '单据', '127.0.0.1', '2020-02-20 23:51:28', '0', '修改,id:200单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6467', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:28', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6468', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:28', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6469', '63', '单据', '127.0.0.1', '2020-02-20 23:51:37', '0', '修改,id:201单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6470', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:37', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6471', '63', '单据明细', '127.0.0.1', '2020-02-20 23:51:37', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6472', '63', '单据', '127.0.0.1', '2020-02-20 23:52:01', '0', '修改,id:197单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6473', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:01', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6474', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:01', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6475', '63', '单据', '127.0.0.1', '2020-02-20 23:52:17', '0', '修改,id:193单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6476', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:17', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6477', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:17', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6478', '63', '单据', '127.0.0.1', '2020-02-20 23:52:33', '0', '修改,id:202单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6479', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:33', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6480', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:33', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6481', '63', '单据', '127.0.0.1', '2020-02-20 23:52:51', '0', '修改,id:203单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6482', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:51', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6483', '63', '单据明细', '127.0.0.1', '2020-02-20 23:52:51', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6484', '63', '单据', '127.0.0.1', '2020-02-20 23:53:04', '0', '修改,id:204单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6485', '63', '单据明细', '127.0.0.1', '2020-02-20 23:53:04', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6486', '63', '单据明细', '127.0.0.1', '2020-02-20 23:53:04', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6487', '63', '单据', '127.0.0.1', '2020-02-20 23:53:21', '0', '修改,id:205单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6488', '63', '单据明细', '127.0.0.1', '2020-02-20 23:53:21', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6489', '63', '单据明细', '127.0.0.1', '2020-02-20 23:53:21', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6493', '63', '单据', '127.0.0.1', '2020-02-20 23:54:02', '0', '修改,id:206单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6494', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:02', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6495', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:02', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6496', '63', '单据', '127.0.0.1', '2020-02-20 23:54:21', '0', '修改,id:207单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6497', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:21', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6498', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:21', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6499', '63', '单据', '127.0.0.1', '2020-02-20 23:54:48', '0', '新增单据', null, '63');
INSERT INTO `jsh_log` VALUES ('6500', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:48', '0', '新增单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6501', '63', '单据明细', '127.0.0.1', '2020-02-20 23:54:48', '0', '删除,id:单据明细', null, '63');
INSERT INTO `jsh_log` VALUES ('6502', '120', '用户', '127.0.0.1', '2020-02-21 00:03:55', '0', '登录,id:120用户', null, null);
INSERT INTO `jsh_log` VALUES ('6503', '63', '用户', '127.0.0.1', '2020-02-21 00:04:38', '0', '登录,id:63用户', null, '63');

-- ----------------------------
-- Table structure for jsh_material
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material`;
CREATE TABLE `jsh_material` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CategoryId` bigint(20) DEFAULT NULL COMMENT '产品类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Mfrs` varchar(50) DEFAULT NULL COMMENT '制造商',
  `SafetyStock` decimal(24,6) DEFAULT NULL COMMENT '安全存量（KG）',
  `Model` varchar(50) DEFAULT NULL COMMENT '型号',
  `Standard` varchar(50) DEFAULT NULL COMMENT '规格',
  `Color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `Unit` varchar(50) DEFAULT NULL COMMENT '单位-单个',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `UnitId` bigint(20) DEFAULT NULL COMMENT '计量单位Id',
  `Enabled` bit(1) DEFAULT NULL COMMENT '启用 0-禁用  1-启用',
  `OtherField1` varchar(50) DEFAULT NULL COMMENT '自定义1',
  `OtherField2` varchar(50) DEFAULT NULL COMMENT '自定义2',
  `OtherField3` varchar(50) DEFAULT NULL COMMENT '自定义3',
  `enableSerialNumber` varchar(1) DEFAULT '0' COMMENT '是否开启序列号，0否，1是',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`),
  KEY `FK675951272AB6672C` (`CategoryId`),
  KEY `UnitId` (`UnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=588 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of jsh_material
-- ----------------------------
INSERT INTO `jsh_material` VALUES ('564', '14', '商品1', '', null, 'sp1', '', '', '个', '', null, '', '', '', '', '0', '1', '0');
INSERT INTO `jsh_material` VALUES ('565', '14', '商品2', '', null, 'sp2', '', '', '个', '', null, '', '', '', '', '1', '1', '0');
INSERT INTO `jsh_material` VALUES ('566', '15', '商品666', '', null, 'sp666', '', '', '个', '', null, '', '', '', '', '0', '117', '0');
INSERT INTO `jsh_material` VALUES ('567', null, '商品1', '', null, 'dsp1', '', '', '个', '', null, '', '', '', '', '0', '115', '0');
INSERT INTO `jsh_material` VALUES ('568', '17', '商品1', '', '100.000000', 'sp1', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('569', '17', '商品2', '', '200.000000', 'sp2', '', '', '只', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('570', '17', '商品3', '', '300.000000', 'sp3', '', '', '个', '', null, '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('571', null, '商品4', '', null, 'sp4', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('572', null, '234234', '', null, '234234', '', '', '', '', null, '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('573', null, '12312', '', null, '12', '', '', '', '', null, '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('574', null, '商品5', '', null, '213qw', '', '', '个', '', null, '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('575', null, '商品6', '', null, 'sp6', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('576', null, '商品7', '', null, 'sp7', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('577', null, '商品8', '', null, 'sp8', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('578', '17', '商品9', '', null, 'sp9', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('579', null, '商品17', '', null, 'sp17', '', '', '', '', '15', '', '', '', '', '0', '63', '0');
INSERT INTO `jsh_material` VALUES ('580', null, '15', '', null, '15', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('581', null, '16', '', null, '16', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('582', null, '商品20', '', null, 'sp2', '', '', '个', '', null, '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('583', null, 'wer', '', null, 'rqwe', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('584', null, 'sfds', '', null, 'a2233', '12', '2', 'ge', '', null, '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('585', null, 'asdf', '', null, 'adsfasdf', '', '', '', '', '15', '', '', '', '', '0', '63', '1');
INSERT INTO `jsh_material` VALUES ('586', null, '序列号商品测试', '', null, 'xlh123', '', '', '个', '', null, '', '', '', '', '1', '63', '0');
INSERT INTO `jsh_material` VALUES ('587', null, '商品test1', '', null, '', 'test1', '', '个', '', null, '', '', '', '', '0', '63', '0');

-- ----------------------------
-- Table structure for jsh_materialcategory
-- ----------------------------
DROP TABLE IF EXISTS `jsh_materialcategory`;
CREATE TABLE `jsh_materialcategory` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `CategoryLevel` smallint(6) DEFAULT NULL COMMENT '等级',
  `ParentId` bigint(20) DEFAULT NULL COMMENT '上级ID',
  `sort` varchar(10) DEFAULT NULL COMMENT '显示顺序',
  `status` varchar(1) DEFAULT '0' COMMENT '状态，0系统默认，1启用，2删除',
  `serial_no` varchar(100) DEFAULT NULL COMMENT '编号',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`Id`),
  KEY `FK3EE7F725237A77D8` (`ParentId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='产品类型表';

-- ----------------------------
-- Records of jsh_materialcategory
-- ----------------------------
INSERT INTO `jsh_materialcategory` VALUES ('1', '根目录', '1', '-1', null, '2', '1', null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('2', '花边一级A', '1', '1', '', '2', '', '', null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('3', '花边一级B', '1', '1', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('4', '其他', '2', '3', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('5', '其他', '3', '4', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('6', '花边二级A', '2', '2', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('7', '花边三级A', '3', '6', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('8', '花边二级B', '2', '2', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('9', '花边一级C', '1', '1', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('10', '花边三级B', '3', '6', null, '2', null, null, null, null, '2019-03-15 23:09:05', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('11', 'ddddd', null, '-1', '', '1', '', '', '2019-03-15 23:09:13', '63', '2019-03-15 23:09:13', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('12', 'ffffff', null, '11', '', '1', '', '', '2019-03-15 23:09:27', '63', '2019-03-15 23:09:27', '63', null);
INSERT INTO `jsh_materialcategory` VALUES ('13', '目录1', null, '-1', '', '1', '111', '', '2019-03-18 22:45:39', '63', '2019-03-18 22:45:39', '63', '1');
INSERT INTO `jsh_materialcategory` VALUES ('14', '目录2', null, '13', '', '1', '234', '', '2019-03-18 23:39:39', '63', '2019-03-18 23:39:39', '63', '1');
INSERT INTO `jsh_materialcategory` VALUES ('15', '目录1', null, '-1', '', '1', '', '', '2019-03-31 21:53:53', '117', '2019-03-31 21:53:53', '117', '117');
INSERT INTO `jsh_materialcategory` VALUES ('16', 'aaaa', null, '-1', '', '1', '', '', '2019-04-02 22:28:07', '115', '2019-04-02 22:28:07', '115', '115');
INSERT INTO `jsh_materialcategory` VALUES ('17', '目录1', null, '-1', '', '1', '', '', '2019-04-10 22:18:12', '63', '2019-04-10 22:18:12', '63', '63');

-- ----------------------------
-- Table structure for jsh_materialproperty
-- ----------------------------
DROP TABLE IF EXISTS `jsh_materialproperty`;
CREATE TABLE `jsh_materialproperty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nativeName` varchar(50) DEFAULT NULL COMMENT '原始名称',
  `enabled` bit(1) DEFAULT NULL COMMENT '是否启用',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `anotherName` varchar(50) DEFAULT NULL COMMENT '别名',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='产品扩展字段表';

-- ----------------------------
-- Records of jsh_materialproperty
-- ----------------------------
INSERT INTO `jsh_materialproperty` VALUES ('1', '规格', '', '02', '规格', '0');
INSERT INTO `jsh_materialproperty` VALUES ('2', '颜色', '', '01', '颜色', '0');
INSERT INTO `jsh_materialproperty` VALUES ('3', '制造商', '', '03', '制造商', '0');
INSERT INTO `jsh_materialproperty` VALUES ('4', '自定义1', '\0', '04', '自定义1', '0');
INSERT INTO `jsh_materialproperty` VALUES ('5', '自定义2', '\0', '05', '自定义2', '0');
INSERT INTO `jsh_materialproperty` VALUES ('6', '自定义3', '\0', '06', '自定义3', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品价格扩展';

-- ----------------------------
-- Records of jsh_material_extend
-- ----------------------------
INSERT INTO `jsh_material_extend` VALUES ('1', '587', '1000', '个', '11.000000', '22.000000', '22.000000', '22.000000', '1', '2020-02-20 23:22:03', 'jsh', 'jsh', '1587656613132', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('2', '568', '1001', '个', '11.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:44:57', 'jsh', 'jsh', '1582213497098', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('3', '569', '1002', '只', '10.000000', '15.000000', '15.000000', '13.000000', '1', '2020-02-20 23:45:15', 'jsh', 'jsh', '1582213514731', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('4', '570', '1003', '个', '8.000000', '15.000000', '14.000000', '13.000000', '1', '2020-02-20 23:45:37', 'jsh', 'jsh', '1587657604430', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('5', '577', '1004', '个', '10.000000', '20.000000', '20.000000', '20.000000', '1', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596494', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('6', '577', '1005', '箱', '120.000000', '240.000000', '240.000000', '240.000000', '0', '2020-02-20 23:46:36', 'jsh', 'jsh', '1582213596497', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('7', '579', '1006', '个', '20.000000', '30.000000', '30.000000', '30.000000', '1', '2020-02-20 23:47:04', 'jsh', 'jsh', '1587784928581', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('8', '579', '1007', '箱', '240.000000', '360.000000', '360.000000', '360.000000', '0', '2020-02-20 23:47:04', 'jsh', 'jsh', '1587784928585', '63', '0');
INSERT INTO `jsh_material_extend` VALUES ('9', '586', '1008', '个', '12.000000', '15.000000', '15.000000', '15.000000', '1', '2020-02-20 23:47:23', 'jsh', 'jsh', '1582213643084', '63', '0');

-- ----------------------------
-- Table structure for jsh_material_stock
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material_stock`;
CREATE TABLE `jsh_material_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `depot_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `number` decimal(24,6) DEFAULT NULL COMMENT '初始库存数量',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_fag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='产品初始库存';

-- ----------------------------
-- Records of jsh_material_stock
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of jsh_organization
-- ----------------------------
INSERT INTO `jsh_organization` VALUES ('1', '01', '根机构', '根机构', null, '5', '-1', '1', '根机构，初始化存在', null, null, '2019-03-15 23:01:21', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('2', null, '销售', '销售', null, '5', '01', '1', '机构表初始化', null, null, '2019-03-15 23:01:21', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('3', null, 'sdf444', 'sdf444', null, '5', '01', '2', '机构表初始化', null, null, '2019-03-15 23:01:19', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('4', null, '1231', '1231', null, '5', '01', '3', '机构表初始化', null, null, '2019-03-15 23:01:19', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('5', null, '23', '23', null, '5', '01', '4', '机构表初始化', null, null, '2019-03-15 23:01:19', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('6', '4444', 'abcd', 'abcd', null, '1', '-1', '', '', '2019-03-15 23:01:30', '63', '2019-03-15 23:01:47', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('7', '123', 'bbbb', 'bbbb', null, '1', 'abcd', '', '', '2019-03-15 23:01:42', '63', '2019-03-15 23:01:42', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('8', 'ddddd', 'ddddd', 'ddddd', null, '1', '4444', '', '', '2019-03-15 23:02:02', '63', '2019-03-15 23:02:02', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('9', '555', 'dddddddddd', 'dddddddddd', null, '1', 'ddddd', '', '', '2019-03-15 23:02:16', '63', '2019-03-15 23:02:16', '63', null, null, null);
INSERT INTO `jsh_organization` VALUES ('10', '23124', 'gaga', 'gaga', null, '1', '-1', '11', '', '2019-03-31 21:52:31', '117', '2019-03-31 21:52:31', '117', null, null, '117');
INSERT INTO `jsh_organization` VALUES ('11', '12312', 'fsadfasdf', 'fsadfasdf', null, '1', '23124', '12312', '', '2019-03-31 21:52:52', '117', '2019-03-31 21:52:52', '117', null, null, '117');
INSERT INTO `jsh_organization` VALUES ('12', '001', '测试机构', '测试机构', null, '2', '-1', '001', '', '2019-12-28 12:13:01', '63', '2019-12-28 12:13:01', '63', null, null, '63');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='机构用户关系表';

-- ----------------------------
-- Records of jsh_orga_user_rel
-- ----------------------------
INSERT INTO `jsh_orga_user_rel` VALUES ('1', '9', '64', '', '0', null, null, '2019-03-15 23:03:39', '63', null);
INSERT INTO `jsh_orga_user_rel` VALUES ('2', '3', '65', null, '0', null, null, null, null, null);
INSERT INTO `jsh_orga_user_rel` VALUES ('3', '3', '67', null, '0', null, null, null, null, null);
INSERT INTO `jsh_orga_user_rel` VALUES ('4', '4', '84', null, '0', null, null, null, null, null);
INSERT INTO `jsh_orga_user_rel` VALUES ('5', '5', '86', null, '0', null, null, null, null, null);
INSERT INTO `jsh_orga_user_rel` VALUES ('6', '3', '91', '', '0', '2019-03-12 21:55:28', '63', '2019-03-12 21:55:28', '63', null);
INSERT INTO `jsh_orga_user_rel` VALUES ('7', '9', '95', '', '0', '2019-03-15 23:03:22', '63', '2019-03-15 23:03:22', '63', null);
INSERT INTO `jsh_orga_user_rel` VALUES ('8', '9', '96', '', '0', '2019-03-17 23:32:08', '63', '2019-03-17 23:32:08', '63', null);
INSERT INTO `jsh_orga_user_rel` VALUES ('9', '10', '117', '', '0', '2019-03-31 21:53:03', '117', '2019-03-31 21:53:12', '117', '117');
INSERT INTO `jsh_orga_user_rel` VALUES ('10', '12', '131', '', '0', '2019-12-28 12:13:15', '63', '2019-12-28 12:13:15', '63', '63');

-- ----------------------------
-- Table structure for jsh_person
-- ----------------------------
DROP TABLE IF EXISTS `jsh_person`;
CREATE TABLE `jsh_person` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='经手人表';

-- ----------------------------
-- Records of jsh_person
-- ----------------------------
INSERT INTO `jsh_person` VALUES ('3', '财务员', '王五-财务', null, '0');
INSERT INTO `jsh_person` VALUES ('4', '财务员', '赵六-财务', null, '0');
INSERT INTO `jsh_person` VALUES ('5', '业务员', '小李', null, '0');
INSERT INTO `jsh_person` VALUES ('6', '业务员', '小军', null, '0');
INSERT INTO `jsh_person` VALUES ('7', '业务员', '小曹', null, '0');
INSERT INTO `jsh_person` VALUES ('8', '仓管员', '小季', '1', '0');
INSERT INTO `jsh_person` VALUES ('9', '财务员', '小月', '1', '0');
INSERT INTO `jsh_person` VALUES ('10', '仓管员', '小张', '117', '0');
INSERT INTO `jsh_person` VALUES ('11', '业务员', '晓丽', '117', '0');
INSERT INTO `jsh_person` VALUES ('12', '财务员', '小草', '117', '0');
INSERT INTO `jsh_person` VALUES ('13', '业务员', '经手人1', '115', '0');
INSERT INTO `jsh_person` VALUES ('14', '业务员', '小李', '63', '0');
INSERT INTO `jsh_person` VALUES ('15', '仓管员', '小军', '63', '0');
INSERT INTO `jsh_person` VALUES ('16', '财务员', '小夏', '63', '0');

-- ----------------------------
-- Table structure for jsh_role
-- ----------------------------
DROP TABLE IF EXISTS `jsh_role`;
CREATE TABLE `jsh_role` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of jsh_role
-- ----------------------------
INSERT INTO `jsh_role` VALUES ('4', '管理员', null, null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('5', '仓管员', null, null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('10', '租户', null, null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('12', '角色123', null, null, null, '117', '0');
INSERT INTO `jsh_role` VALUES ('13', '角色test', null, null, null, null, '0');
INSERT INTO `jsh_role` VALUES ('14', '44444', null, null, null, null, '1');
INSERT INTO `jsh_role` VALUES ('15', 'laoba角色', null, null, null, '115', '0');
INSERT INTO `jsh_role` VALUES ('16', '测试角色123', null, null, null, '63', '0');

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
INSERT INTO `jsh_sequence` VALUES ('depot_number_seq', '1', '999999999999999999', '319', '1', '单据编号sequence');

-- ----------------------------
-- Table structure for jsh_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `jsh_serial_number`;
CREATE TABLE `jsh_serial_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_Id` bigint(20) DEFAULT NULL COMMENT '产品表id',
  `serial_Number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `is_Sell` varchar(1) DEFAULT '0' COMMENT '是否卖出，0未卖出，1卖出',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  `create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_Time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `depothead_Id` bigint(20) DEFAULT NULL COMMENT '单据主表id，用于跟踪序列号流向',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COMMENT='序列号表';

-- ----------------------------
-- Records of jsh_serial_number
-- ----------------------------
INSERT INTO `jsh_serial_number` VALUES ('105', '586', '12312323423223', '0', '', '0', '2019-12-28 12:14:39', '63', '2019-12-28 12:14:39', '63', null, '63');

-- ----------------------------
-- Table structure for jsh_supplier
-- ----------------------------
DROP TABLE IF EXISTS `jsh_supplier`;
CREATE TABLE `jsh_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `supplier` varchar(255) NOT NULL COMMENT '供应商名称',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `phonenum` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `description` varchar(500) DEFAULT NULL COMMENT '备注',
  `isystem` tinyint(4) DEFAULT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用',
  `AdvanceIn` decimal(24,6) DEFAULT '0.000000' COMMENT '预收款',
  `BeginNeedGet` decimal(24,6) DEFAULT NULL COMMENT '期初应收',
  `BeginNeedPay` decimal(24,6) DEFAULT NULL COMMENT '期初应付',
  `AllNeedGet` decimal(24,6) DEFAULT NULL COMMENT '累计应收',
  `AllNeedPay` decimal(24,6) DEFAULT NULL COMMENT '累计应付',
  `fax` varchar(30) DEFAULT NULL COMMENT '传真',
  `telephone` varchar(30) DEFAULT NULL COMMENT '手机',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `taxNum` varchar(50) DEFAULT NULL COMMENT '纳税人识别号',
  `bankName` varchar(50) DEFAULT NULL COMMENT '开户行',
  `accountNumber` varchar(50) DEFAULT NULL COMMENT '账号',
  `taxRate` decimal(24,6) DEFAULT NULL COMMENT '税率',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='供应商/客户信息表';

-- ----------------------------
-- Records of jsh_supplier
-- ----------------------------
INSERT INTO `jsh_supplier` VALUES ('47', '供应商1', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '1', '0');
INSERT INTO `jsh_supplier` VALUES ('48', '客户1', '', '', '', '', null, '客户', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '1', '0');
INSERT INTO `jsh_supplier` VALUES ('49', 'ddddd123', '', '', '', '', null, '会员', '', '6.000000', null, null, null, null, '', '', '', '', '', '', null, '1', '0');
INSERT INTO `jsh_supplier` VALUES ('50', '供应商2', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '1', '0');
INSERT INTO `jsh_supplier` VALUES ('51', '供应商1', '小周', '', '', '', null, '供应商', '', '0.000000', null, '2000.000000', null, null, '', '', '', '', '', '', null, '117', '0');
INSERT INTO `jsh_supplier` VALUES ('52', '客户123', '', '', '', '', null, '客户', '', '0.000000', '1000.000000', null, null, null, '', '', '', '', '', '', null, '117', '0');
INSERT INTO `jsh_supplier` VALUES ('53', '会员123123', '', '', '', '', null, '会员', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '117', '0');
INSERT INTO `jsh_supplier` VALUES ('54', '供应商2222', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '117', '0');
INSERT INTO `jsh_supplier` VALUES ('55', '供应商1', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '115', '0');
INSERT INTO `jsh_supplier` VALUES ('56', '客户666', '', '', '', '', null, '客户', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '115', '0');
INSERT INTO `jsh_supplier` VALUES ('57', '供应商1', '', '', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, '0.000000', '', '', '', '', '', '', '12.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('58', '客户1', '', '', '', '', null, '客户', '', '0.000000', '200.000000', '0.000000', '-100.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('59', '客户2', '', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('60', '12312666', '', '', '', '', null, '会员', '', '994.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('61', '', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', '12312312.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('62', '供if', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('63', '', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('64', 'wrwer', '', '', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, '0.000000', '', '', '', '', '', '', '233.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('65', '123123', '', '', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, '0.000000', '', '', '', '', '', '', '44.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('66', 'rrtt', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('67', '供应商2', '', '', '', '', null, '供应商', '', '0.000000', '0.000000', '0.000000', null, '0.000000', '', '', '', '', '', '', '7.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('68', '供应商3', '', '', '', '', null, '供应商', '', '0.000000', '15.000000', '0.000000', null, '-15.000000', '', '13000000000', '', '', '', '', '22.000000', '63', '0');
INSERT INTO `jsh_supplier` VALUES ('69', '', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', '3123.000000', '63', '1');
INSERT INTO `jsh_supplier` VALUES ('70', 'rrrrr', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('71', '客户3', '', '', '', '', null, '客户', '', '0.000000', '0.000000', '0.000000', '0.000000', null, '', '', '', '', '', '', null, '63', '0');
INSERT INTO `jsh_supplier` VALUES ('72', 'sdfafadsf', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');
INSERT INTO `jsh_supplier` VALUES ('73', 'sadvczXCvz', '', '', '', '', null, '供应商', '', '0.000000', null, null, null, null, '', '', '', '', '', '', null, '63', '1');

-- ----------------------------
-- Table structure for jsh_systemconfig
-- ----------------------------
DROP TABLE IF EXISTS `jsh_systemconfig`;
CREATE TABLE `jsh_systemconfig` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `company_contacts` varchar(20) DEFAULT NULL COMMENT '公司联系人',
  `company_address` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `company_tel` varchar(20) DEFAULT NULL COMMENT '公司电话',
  `company_fax` varchar(20) DEFAULT NULL COMMENT '公司传真',
  `company_post_code` varchar(20) DEFAULT NULL COMMENT '公司邮编',
  `depot_flag` varchar(1) DEFAULT '0' COMMENT '仓库启用标记，0未启用，1启用',
  `customer_flag` varchar(1) DEFAULT '0' COMMENT '客户启用标记，0未启用，1启用',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数';

-- ----------------------------
-- Records of jsh_systemconfig
-- ----------------------------
INSERT INTO `jsh_systemconfig` VALUES ('7', '南通jshERP公司', '张三', '南通市通州区某某路', '0513-10101010', '0513-18181818', '226300', '0', '0', null, '0');
INSERT INTO `jsh_systemconfig` VALUES ('8', '公司123', '', '', '', '', '', '0', '0', '117', '0');
INSERT INTO `jsh_systemconfig` VALUES ('9', '公司1', '小军', '', '', '', '', '0', '0', '63', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='租户';

-- ----------------------------
-- Records of jsh_tenant
-- ----------------------------
INSERT INTO `jsh_tenant` VALUES ('13', '63', 'jsh', '20', '2000', null);
INSERT INTO `jsh_tenant` VALUES ('14', '113', 'abc123', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('15', '115', 'jzh', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('16', '123', 'caoyuli', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('17', '124', 'jchb', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('18', '126', '123123', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('19', '127', '2345123', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('20', '128', 'q12341243', '2', '200', null);
INSERT INTO `jsh_tenant` VALUES ('21', '130', 'jsh666', '2', '200', null);

-- ----------------------------
-- Table structure for jsh_unit
-- ----------------------------
DROP TABLE IF EXISTS `jsh_unit`;
CREATE TABLE `jsh_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `UName` varchar(50) DEFAULT NULL COMMENT '名称，支持多单位',
  `basic_unit` varchar(50) DEFAULT NULL COMMENT '基础单位' ,
  `other_unit` varchar(50) DEFAULT NULL COMMENT '副单位' ,
  `ratio` int(11) NULL DEFAULT NULL COMMENT '比例' ,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='多单位表';

-- ----------------------------
-- Records of jsh_unit
-- ----------------------------
INSERT INTO `jsh_unit` VALUES ('2', 'kg,包(1:25)', 'kg', '包', '25', NULL, '0');
INSERT INTO `jsh_unit` VALUES ('8', '瓶,箱(1:12)', '瓶', '箱', '12', NULL, '0');
INSERT INTO `jsh_unit` VALUES ('14', '个,箱(1:12)', '个', '箱', '12', '117', '0');
INSERT INTO `jsh_unit` VALUES ('15', '个,箱(1:12)', '个', '箱', '12', '63', '0');

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
INSERT INTO `jsh_user` VALUES ('96', 'lili', 'lili', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '0', '', null, '1');
INSERT INTO `jsh_user` VALUES ('113', 'yuyu123', 'yuyu123', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '113');
INSERT INTO `jsh_user` VALUES ('115', 'laoba123', 'laoba123', 'e10adc3949ba59abbe56e057f20f883e', '33333', null, '', '', '1', '0', '0', '', null, '115');
INSERT INTO `jsh_user` VALUES ('116', 'gggg123', 'gggg123', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '1', '', null, '115');
INSERT INTO `jsh_user` VALUES ('120', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, null);
INSERT INTO `jsh_user` VALUES ('121', 'hhhh', 'hhhh', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '0', '', null, '115');
INSERT INTO `jsh_user` VALUES ('122', 'admin1', 'admin1', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '1', '', null, '63');
INSERT INTO `jsh_user` VALUES ('123', 'caoyuli', 'caoyuli', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '123');
INSERT INTO `jsh_user` VALUES ('124', 'jchb', 'jchb', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '124');
INSERT INTO `jsh_user` VALUES ('126', '123123', '123123', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '126');
INSERT INTO `jsh_user` VALUES ('127', '2345123', '2345123', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '127');
INSERT INTO `jsh_user` VALUES ('128', 'q12341243', 'q12341243', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '128');
INSERT INTO `jsh_user` VALUES ('130', 'jsh666', 'jsh666', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', '0', '0', null, null, '130');
INSERT INTO `jsh_user` VALUES ('131', '测试用户', 'test123', 'e10adc3949ba59abbe56e057f20f883e', '', null, '', '', '1', '0', '0', '', null, '63');

-- ----------------------------
-- Table structure for jsh_userbusiness
-- ----------------------------
DROP TABLE IF EXISTS `jsh_userbusiness`;
CREATE TABLE `jsh_userbusiness` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类别',
  `KeyId` varchar(50) DEFAULT NULL COMMENT '主ID',
  `Value` varchar(10000) DEFAULT NULL COMMENT '值',
  `BtnStr` varchar(2000) DEFAULT NULL COMMENT '按钮权限',
  `delete_Flag` varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='用户/角色/模块关系表';

-- ----------------------------
-- Records of jsh_userbusiness
-- ----------------------------
INSERT INTO `jsh_userbusiness` VALUES ('5', 'RoleFunctions', '4', '[245][13][12][16][243][14][15][234][236][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][244][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][246][245]', '[{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"241\",\"btnStr\":\"3\"},{\"funId\":\"242\",\"btnStr\":\"3\"}]', '0');
INSERT INTO `jsh_userbusiness` VALUES ('6', 'RoleFunctions', '5', '[22][23][25][26][194][195][31][33][200][201][41][199][202]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('7', 'RoleFunctions', '6', '[22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]', '[{\"funId\":\"33\",\"btnStr\":\"4\"}]', '0');
INSERT INTO `jsh_userbusiness` VALUES ('9', 'RoleFunctions', '7', '[168][13][12][16][14][15][189][18][19][132]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('10', 'RoleFunctions', '8', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('11', 'RoleFunctions', '9', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('12', 'UserRole', '1', '[5]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('13', 'UserRole', '2', '[6][7]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('14', 'UserDepot', '2', '[1][2][6][7]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('15', 'UserDepot', '1', '[1][2][5][6][7][10][12][14][15][17]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('16', 'UserRole', '63', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('18', 'UserDepot', '63', '[14][15]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('19', 'UserDepot', '5', '[6][45][46][50]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('20', 'UserRole', '5', '[5]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('21', 'UserRole', '64', '[13]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('22', 'UserDepot', '64', '[1]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('23', 'UserRole', '65', '[5]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('24', 'UserDepot', '65', '[1]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('25', 'UserCustomer', '64', '[5][2]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('26', 'UserCustomer', '65', '[6]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('27', 'UserCustomer', '63', '[58]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('28', 'UserDepot', '96', '[7]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('29', 'UserRole', '96', '[6]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('30', 'UserRole', '113', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('32', 'RoleFunctions', '10', '[210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][59][207][208][209][226][227][228][229][235][237][244][22][23][220][240][25][217][218][26][194][195][31][13][243][14][15][234]', '[{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"241\",\"btnStr\":\"3\"},{\"funId\":\"242\",\"btnStr\":\"3\"}]', '0');
INSERT INTO `jsh_userbusiness` VALUES ('34', 'UserRole', '115', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('35', 'UserRole', '117', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('36', 'UserDepot', '117', '[8][9]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('37', 'UserCustomer', '117', '[52]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('38', 'UserRole', '120', '[4]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('39', 'UserDepot', '120', '[7][8][9][10][11][12][2][1][3]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('40', 'UserCustomer', '120', '[52][48][6][5][2]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('41', 'RoleFunctions', '12', '', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('48', 'RoleFunctions', '13', '[59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('51', 'UserRole', '74', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('52', 'UserDepot', '121', '[13]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('54', 'UserDepot', '115', '[13]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('56', 'UserCustomer', '115', '[56]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('57', 'UserCustomer', '121', '[56]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('58', 'UserRole', '121', '[15]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('59', 'UserRole', '123', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('60', 'UserRole', '124', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('61', 'UserRole', '125', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('62', 'UserRole', '126', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('63', 'UserRole', '127', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('64', 'UserRole', '128', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('65', 'UserRole', '129', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('66', 'UserRole', '130', '[10]', null, '0');
INSERT INTO `jsh_userbusiness` VALUES ('67', 'UserRole', '131', '[16]', null, '0');
