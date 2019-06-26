-- noinspection SqlNoDataSourceInspectionForFile

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : jsh_erp

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2018-10-28 23:21:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `databasechangelog`
-- ----------------------------
DROP TABLE IF EXISTS `databasechangelog`;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of databasechangelog
-- ----------------------------
INSERT INTO `databasechangelog` VALUES ('201709282202', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-09-28 23:34:07', '1', 'EXECUTED', '7:ca3a38c3a43ee96bf6c7bbf56123d1fc', 'sql', '增加角色bbbb-测试', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201709282227', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-09-28 23:34:07', '2', 'EXECUTED', '7:fa335b3dcabb52f38c4300e35b7c0b4c', 'sql', '删除角色bbbb-测试', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201709282322', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-09-29 22:39:46', '3', 'EXECUTED', '7:adeea7031bd16af361001ce7d93b1e1a', 'sql', '新增系统配置表', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201709292218', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-09-29 22:39:46', '4', 'EXECUTED', '7:f7079f8d7b3fdb92fb6d319789ea9117', 'sql', '新增系统参数数据-公司相关', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201710122314', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-10-18 22:39:27', '5', 'EXECUTED', '7:c0885501076d6473461f074cc68535e7', 'sql', '新增商品属性-数据', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201712102245', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2017-12-10 22:51:30', '6', 'EXECUTED', '7:9b0df7eba9ad678b08fd435be32397b1', 'sql', '更新账户表-是否默认列', null, '3.1.1');
INSERT INTO `databasechangelog` VALUES ('201809122201', 'jishenghua', 'liquibase/jsh_erp/db.changelog-jsh_erp-1.0.xml', '2018-10-28 19:56:28', '7', 'EXECUTED', '7:62bde21df811efc41b146eac39da7994', 'sql', '更新用户表-是否系统列', null, '3.1.1');

-- ----------------------------
-- Table structure for `databasechangeloglock`
-- ----------------------------
DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of databasechangeloglock
-- ----------------------------
INSERT INTO `databasechangeloglock` VALUES ('1', '', null, null);

-- ----------------------------
-- Table structure for `jsh_account`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account`;
CREATE TABLE `jsh_account` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `SerialNo` varchar(50) DEFAULT NULL COMMENT '编号',
  `InitialAmount` decimal(24, 6) DEFAULT NULL COMMENT '期初金额',
  `CurrentAmount` decimal(24, 6) DEFAULT NULL COMMENT '当前余额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `IsDefault` bit(1) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='账户信息';

-- ----------------------------
-- Records of jsh_account
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('4', '南通建行', '652346523465234623', '1200', '215', '建行账户', '');
INSERT INTO `jsh_account` VALUES ('9', '流动总账', '65234624523452364', '2000', '393', '现在账户', '');
INSERT INTO `jsh_account` VALUES ('10', '支付宝', '123456789@qq.com', '10000', null, '', '');
INSERT INTO `jsh_account` VALUES ('11', '微信', '13000000000', '10000', null, '', '');
INSERT INTO `jsh_account` VALUES ('12', '上海农行', '65324345234523211', '10000', '0', '', '');

-- ----------------------------
-- Table structure for `jsh_accounthead`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accounthead`;
CREATE TABLE `jsh_accounthead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(支出/收入/收款/付款/转账)',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '单位Id(收款/付款单位)',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '经手人Id',
  `ChangeAmount` decimal(24, 6) DEFAULT NULL COMMENT '变动金额(优惠/收款/付款/实付)',
  `TotalPrice` decimal(24, 6) DEFAULT NULL COMMENT '合计金额',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户(收款/付款)',
  `BillNo` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `BillTime` datetime DEFAULT NULL COMMENT '单据日期',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`),
  KEY `FK9F4C0D8DB610FC06` (`OrganId`),
  KEY `FK9F4C0D8DAAE50527` (`AccountId`),
  KEY `FK9F4C0D8DC4170B37` (`HandsPersonId`),
  CONSTRAINT `FK9F4C0D8DAAE50527` FOREIGN KEY (`AccountId`) REFERENCES `jsh_account` (`Id`),
  CONSTRAINT `FK9F4C0D8DB610FC06` FOREIGN KEY (`OrganId`) REFERENCES `jsh_supplier` (`id`),
  CONSTRAINT `FK9F4C0D8DC4170B37` FOREIGN KEY (`HandsPersonId`) REFERENCES `jsh_person` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='财务主表';

-- ----------------------------
-- Records of jsh_accounthead
-- ----------------------------
INSERT INTO `jsh_accounthead` VALUES ('57', '收预付款', '8', '3', null, '1000', null, '2342134', '2017-06-27 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('61', '收预付款', '9', '3', null, '33', null, 'SYF2017062901721', '2017-06-29 00:00:00', 'aaaaaa');
INSERT INTO `jsh_accounthead` VALUES ('67', '收预付款', '10', '4', null, '2100', null, 'SYF2017070222414', '2017-07-02 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('70', '支出', '4', '3', '-60', '-60', '4', 'ZC20170703233735', '2017-07-03 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('74', '转账', null, '3', '-100', '-100', '4', 'ZZ2017070323489', '2017-07-03 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('77', '收入', '2', '3', '40', '40', '4', 'SR20170704222634', '2017-07-04 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('78', '收预付款', '9', '3', null, '200', null, 'SYF201707050257', '2017-07-05 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('79', '收预付款', '9', '3', null, '100', null, 'SYF20170705076', '2017-07-05 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('82', '收款', '2', '3', '0', '2.6', null, 'SK20171008191440', '2017-10-09 00:08:11', '');
INSERT INTO `jsh_accounthead` VALUES ('83', '付款', '1', '4', '0', '-20', null, 'FK20171008232825', '2017-10-08 00:00:00', '');
INSERT INTO `jsh_accounthead` VALUES ('84', '收入', '2', '4', '0', '21', '10', 'SR20171009000300', '2017-10-09 00:03:00', '');
INSERT INTO `jsh_accounthead` VALUES ('85', '收入', '2', '3', '22', '22', '11', 'SR20171009000637', '2017-10-09 00:06:37', '备注123 备注123 备注123');
INSERT INTO `jsh_accounthead` VALUES ('86', '转账', null, '4', '-22', '-22', '10', 'ZZ20171009000719', '2017-10-09 00:07:19', '');
INSERT INTO `jsh_accounthead` VALUES ('87', '付款', '4', '4', '10', '-33', null, 'FK20171009000747', '2017-10-09 00:07:47', '');
INSERT INTO `jsh_accounthead` VALUES ('88', '收款', '2', '4', '0', '2.8', null, 'SK20171024220754', '2017-10-24 22:07:54', '');
INSERT INTO `jsh_accounthead` VALUES ('89', '收款', '2', '4', '0', '11', null, 'SK20171030232535', '2017-10-30 23:25:35', '');
INSERT INTO `jsh_accounthead` VALUES ('90', '收款', '2', '4', '0', '10', null, 'SK20171119231440', '2017-11-19 23:14:40', '');

-- ----------------------------
-- Table structure for `jsh_accountitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accountitem`;
CREATE TABLE `jsh_accountitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `InOutItemId` bigint(20) DEFAULT NULL COMMENT '收支项目Id',
  `EachAmount` decimal(24, 6) DEFAULT NULL COMMENT '单项金额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`Id`),
  KEY `FK9F4CBAC0AAE50527` (`AccountId`),
  KEY `FK9F4CBAC0C5FE6007` (`HeaderId`),
  KEY `FK9F4CBAC0D203EDC5` (`InOutItemId`),
  CONSTRAINT `FK9F4CBAC0AAE50527` FOREIGN KEY (`AccountId`) REFERENCES `jsh_account` (`Id`),
  CONSTRAINT `FK9F4CBAC0C5FE6007` FOREIGN KEY (`HeaderId`) REFERENCES `jsh_accounthead` (`Id`) ON DELETE CASCADE,
  CONSTRAINT `FK9F4CBAC0D203EDC5` FOREIGN KEY (`InOutItemId`) REFERENCES `jsh_inoutitem` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='财务子表';

-- ----------------------------
-- Records of jsh_accountitem
-- ----------------------------
INSERT INTO `jsh_accountitem` VALUES ('58', '57', '9', null, '1000', '');
INSERT INTO `jsh_accountitem` VALUES ('62', '61', '4', null, '33', '');
INSERT INTO `jsh_accountitem` VALUES ('68', '67', '4', null, '2100', '');
INSERT INTO `jsh_accountitem` VALUES ('71', '70', null, '11', '60', '');
INSERT INTO `jsh_accountitem` VALUES ('75', '74', '9', null, '100', '');
INSERT INTO `jsh_accountitem` VALUES ('78', '77', null, '14', '40', '');
INSERT INTO `jsh_accountitem` VALUES ('79', '78', '9', null, '200', '');
INSERT INTO `jsh_accountitem` VALUES ('80', '79', '9', null, '100', '');
INSERT INTO `jsh_accountitem` VALUES ('83', '82', '10', null, '2.6', '');
INSERT INTO `jsh_accountitem` VALUES ('84', '83', '10', null, '-20', '');
INSERT INTO `jsh_accountitem` VALUES ('85', '84', null, '13', '21', '');
INSERT INTO `jsh_accountitem` VALUES ('86', '85', null, '12', '22', '44');
INSERT INTO `jsh_accountitem` VALUES ('87', '86', '11', null, '22', '');
INSERT INTO `jsh_accountitem` VALUES ('88', '87', '10', null, '-33', '');
INSERT INTO `jsh_accountitem` VALUES ('89', '88', '10', null, '2.8', '');
INSERT INTO `jsh_accountitem` VALUES ('90', '89', '11', null, '11', '');
INSERT INTO `jsh_accountitem` VALUES ('91', '90', '12', null, '10', '');

-- ----------------------------
-- Table structure for `jsh_app`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_app`;
CREATE TABLE `jsh_app` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Number` varchar(50) DEFAULT NULL COMMENT '编号',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型',
  `Icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `URL` varchar(50) DEFAULT NULL COMMENT '编号',
  `Width` varchar(50) DEFAULT NULL COMMENT '宽度',
  `Height` varchar(50) DEFAULT NULL COMMENT '高度',
  `ReSize` bit(1) DEFAULT NULL COMMENT '是否可改变大小',
  `OpenMax` bit(1) DEFAULT NULL COMMENT '最大化',
  `Flash` bit(1) DEFAULT NULL COMMENT '是否切换',
  `ZL` varchar(50) DEFAULT NULL COMMENT '类型',
  `Sort` varchar(50) DEFAULT NULL COMMENT '排序',
  `Remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `Enabled` bit(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Records of jsh_app
-- ----------------------------
INSERT INTO `jsh_app` VALUES ('3', '00', '系统管理', 'app', '0000000004.png', '', '1240', '600', '', '', '', 'desk', '198', '', '');
INSERT INTO `jsh_app` VALUES ('6', '', '个人信息', 'app', '0000000005.png', '../user/password.html', '600', '400', '', '', '', 'dock', '200', '', '');
INSERT INTO `jsh_app` VALUES ('7', '01', '基础数据', 'app', '0000000006.png', '', '1350', '630', '', '', '', 'desk', '120', '', '');
INSERT INTO `jsh_app` VALUES ('22', '03', '报表查询', 'app', '0000000022.png', '', '1350', '630', '', '', '', 'desk', '115', '', '');
INSERT INTO `jsh_app` VALUES ('23', '04', '零售管理', 'app', 'resizeApi.png', '', '1350', '630', '', '', '', 'desk', '025', '', '');
INSERT INTO `jsh_app` VALUES ('24', '05', '采购管理', 'app', 'buy.png', '', '1350', '630', '', '', '', 'desk', '027', '', '');
INSERT INTO `jsh_app` VALUES ('25', '06', '销售管理', 'app', 'sale.png', '', '1350', '630', '', '', '', 'desk', '028', '', '');
INSERT INTO `jsh_app` VALUES ('26', '07', '财务管理', 'app', 'money.png', '', '1350', '630', '', '', '', 'desk', '035', '', '');
INSERT INTO `jsh_app` VALUES ('27', '08', '仓库管理', 'app', 'depot.png', '', '1350', '630', '', '', '', 'desk', '029', '', '');

-- ----------------------------
-- Table structure for `jsh_asset`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_asset`;
CREATE TABLE `jsh_asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `assetnameID` bigint(20) NOT NULL COMMENT '资产名称id',
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `labels` varchar(255) DEFAULT NULL COMMENT '标签：以空格为分隔符',
  `status` smallint(6) DEFAULT NULL COMMENT '资产的状态：0==在库，1==在用，2==消费',
  `userID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `price` decimal(24, 6) DEFAULT NULL COMMENT '购买价格',
  `purchasedate` datetime DEFAULT NULL COMMENT '购买日期',
  `periodofvalidity` datetime DEFAULT NULL COMMENT '有效日期',
  `warrantydate` datetime DEFAULT NULL COMMENT '保修日期',
  `assetnum` varchar(255) DEFAULT NULL COMMENT '资产编号',
  `serialnum` varchar(255) DEFAULT NULL COMMENT '资产序列号',
  `supplier` bigint(20) NOT NULL COMMENT '供应商',
  `description` longtext COMMENT '描述信息',
  `addMonth` longtext COMMENT '资产添加时间，统计报表使用',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `FK353690ED9B6CB285` (`assetnameID`),
  KEY `FK353690EDAD45B659` (`creator`),
  KEY `FK353690ED27D23FE4` (`supplier`),
  KEY `FK353690ED61FE182C` (`updator`),
  KEY `FK353690ED3E226853` (`userID`),
  CONSTRAINT `FK353690ED27D23FE4` FOREIGN KEY (`supplier`) REFERENCES `jsh_supplier` (`id`),
  CONSTRAINT `FK353690ED3E226853` FOREIGN KEY (`userID`) REFERENCES `jsh_user` (`id`),
  CONSTRAINT `FK353690ED61FE182C` FOREIGN KEY (`updator`) REFERENCES `jsh_user` (`id`),
  CONSTRAINT `FK353690ED9B6CB285` FOREIGN KEY (`assetnameID`) REFERENCES `jsh_assetname` (`id`),
  CONSTRAINT `FK353690EDAD45B659` FOREIGN KEY (`creator`) REFERENCES `jsh_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='资产记录表';

-- ----------------------------
-- Records of jsh_asset
-- ----------------------------
INSERT INTO `jsh_asset` VALUES ('1', '27', 'weizhi', '', '0', null, '11', '2016-10-22 00:00:00', '2016-10-21 00:00:00', '2016-11-03 00:00:00', '1231241', '123124123', '2', '', '2016-10', '2016-10-22 20:04:48', '63', '2016-10-22 20:04:48', '63');
INSERT INTO `jsh_asset` VALUES ('3', '29', 'weizhi', null, '0', null, '11', '2016-10-22 00:00:00', '2016-10-21 00:00:00', '2016-11-03 00:00:00', '1231241', '123124123', '2', null, null, '2017-07-22 18:42:14', null, '2017-07-22 18:42:14', null);

-- ----------------------------
-- Table structure for `jsh_assetcategory`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_assetcategory`;
CREATE TABLE `jsh_assetcategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `assetname` varchar(255) NOT NULL COMMENT '资产类型名称',
  `isystem` tinyint(4) NOT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `description` varchar(500) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='资产类型';

-- ----------------------------
-- Records of jsh_assetcategory
-- ----------------------------
INSERT INTO `jsh_assetcategory` VALUES ('14', '递延资产', '1', '递延资产');
INSERT INTO `jsh_assetcategory` VALUES ('15', '无形资产', '1', '无形资产');
INSERT INTO `jsh_assetcategory` VALUES ('16', '长期投资', '1', '长期投资');
INSERT INTO `jsh_assetcategory` VALUES ('17', '固定资产', '1', '固定资产');
INSERT INTO `jsh_assetcategory` VALUES ('18', '流动资产', '1', '流动资产');

-- ----------------------------
-- Table structure for `jsh_assetname`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_assetname`;
CREATE TABLE `jsh_assetname` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `assetname` varchar(255) NOT NULL COMMENT '资产名称',
  `assetcategoryID` bigint(20) NOT NULL COMMENT '资产类型',
  `isystem` smallint(6) NOT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `description` longtext COMMENT '描述信息',
  `isconsumables` smallint(6) DEFAULT NULL COMMENT '是否为耗材 0==否 1==是 耗材状态只能是消费',
  PRIMARY KEY (`id`),
  KEY `FKA4ADCCF866BC8AD3` (`assetcategoryID`),
  CONSTRAINT `FKA4ADCCF866BC8AD3` FOREIGN KEY (`assetcategoryID`) REFERENCES `jsh_assetcategory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='资产信息';

-- ----------------------------
-- Records of jsh_assetname
-- ----------------------------
INSERT INTO `jsh_assetname` VALUES ('1', '联想Y450', '17', '1', '', '1');
INSERT INTO `jsh_assetname` VALUES ('2', '惠普打印机', '15', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('12', '乐萌水杯', '16', '1', '', '1');
INSERT INTO `jsh_assetname` VALUES ('13', '机顶盒', '17', '1', '机顶盒', '0');
INSERT INTO `jsh_assetname` VALUES ('14', 'TCL电视', '17', '1', '', '1');
INSERT INTO `jsh_assetname` VALUES ('15', '手机', '17', '1', '', '1');
INSERT INTO `jsh_assetname` VALUES ('16', '硬盘', '16', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('17', '毛笔', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('18', '杯子', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('19', '建造师证书', '15', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('20', '算量软件', '14', '1', '', '1');
INSERT INTO `jsh_assetname` VALUES ('21', 'cad软件', '15', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('22', '办公桌', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('23', '笔记本', '17', '1', '笔记本', '1');
INSERT INTO `jsh_assetname` VALUES ('24', '打印机', '17', '1', '打印机', '0');
INSERT INTO `jsh_assetname` VALUES ('25', '电脑', '17', '1', '电脑', '0');
INSERT INTO `jsh_assetname` VALUES ('26', '电动车', '16', '1', '电动车', '0');
INSERT INTO `jsh_assetname` VALUES ('27', '电源线', '17', '1', '电源线', '0');
INSERT INTO `jsh_assetname` VALUES ('28', '电源线666', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('29', '电源线777', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('30', '电源线8', '17', '1', '', '0');
INSERT INTO `jsh_assetname` VALUES ('31', '电源线9', '17', '1', '', '0');

-- ----------------------------
-- Table structure for `jsh_depot`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depot`;
CREATE TABLE `jsh_depot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '仓库名称',
  `address` varchar(50) DEFAULT NULL COMMENT '仓库地址',
  `warehousing` decimal(24, 6) DEFAULT NULL COMMENT '仓储费',
  `truckage` decimal(24, 6) DEFAULT NULL COMMENT '搬运费',
  `type` int(10) DEFAULT NULL COMMENT '类型',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='仓库表';

-- ----------------------------
-- Records of jsh_depot
-- ----------------------------
INSERT INTO `jsh_depot` VALUES ('1', '叠石桥店', '地址222', '33', '22', '0', '2', '上海33');
INSERT INTO `jsh_depot` VALUES ('2', '公司总部', '地址12355', '44', '22.22', '0', '1', '总部');
INSERT INTO `jsh_depot` VALUES ('3', '金沙店', '地址666', '31', '4', '0', '3', '苏州');
INSERT INTO `jsh_depot` VALUES ('4', '1268200294', '', null, null, '1', '1', '');
INSERT INTO `jsh_depot` VALUES ('5', '1268787965', null, null, null, '1', '3', '');
INSERT INTO `jsh_depot` VALUES ('6', '1269520625', null, null, null, '1', '2', '');

-- ----------------------------
-- Table structure for `jsh_depothead`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depothead`;
-- noinspection SqlNoDataSourceInspection

CREATE TABLE `jsh_depothead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(出库/入库)',
  `SubType` varchar(50) DEFAULT NULL COMMENT '出入库分类',
  `ProjectId` bigint(20) DEFAULT NULL COMMENT '项目Id',
  `DefaultNumber` varchar(50) DEFAULT NULL COMMENT '初始票据号',
  `Number` varchar(50) DEFAULT NULL COMMENT '票据号',
  `OperPersonName` varchar(50) DEFAULT NULL COMMENT '操作员名字',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `OperTime` datetime DEFAULT NULL COMMENT '出入库时间',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '供应商Id',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '采购/领料-经手人Id',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `ChangeAmount` decimal(24, 6) DEFAULT NULL COMMENT '变动金额(收款/付款)',
  `AllocationProjectId` bigint(20) DEFAULT NULL COMMENT '调拨时，对方项目Id',
  `TotalPrice` decimal(24, 6) DEFAULT NULL COMMENT '合计金额',
  `PayType` varchar(50) DEFAULT NULL COMMENT '付款类型(现金、记账等)',
  `Remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `Salesman` varchar(50) DEFAULT NULL COMMENT '业务员（可以多个）',
  `AccountIdList` varchar(50) DEFAULT NULL COMMENT '多账户ID列表',
  `AccountMoneyList` varchar(200) DEFAULT '' COMMENT '多账户金额列表',
  `Discount` decimal(24, 6) DEFAULT NULL COMMENT '优惠率',
  `DiscountMoney` decimal(24, 6) DEFAULT NULL COMMENT '优惠金额',
  `DiscountLastMoney` decimal(24, 6) DEFAULT NULL COMMENT '优惠后金额',
  `OtherMoney` decimal(24, 6) DEFAULT NULL COMMENT '销售或采购费用合计',
  `OtherMoneyList` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目Id数组（包括快递、招待等）',
  `OtherMoneyItem` varchar(200) DEFAULT NULL COMMENT '销售或采购费用涉及项目（包括快递、招待等）',
  `AccountDay` int(10) DEFAULT NULL COMMENT '结算天数',
  `Status` bit(1) DEFAULT NULL COMMENT '单据状态(未审核、已审核)',
  PRIMARY KEY (`Id`),
  KEY `FK2A80F214CA633ABA` (`AllocationProjectId`),
  KEY `FK2A80F214C4170B37` (`HandsPersonId`),
  KEY `FK2A80F214B610FC06` (`OrganId`),
  KEY `FK2A80F2142888F9A` (`ProjectId`),
  KEY `FK2A80F214AAE50527` (`AccountId`),
  CONSTRAINT `FK2A80F214AAE50527` FOREIGN KEY (`AccountId`) REFERENCES `jsh_account` (`Id`),
  CONSTRAINT `jsh_depothead_ibfk_1` FOREIGN KEY (`ProjectId`) REFERENCES `jsh_depot` (`id`),
  CONSTRAINT `jsh_depothead_ibfk_3` FOREIGN KEY (`OrganId`) REFERENCES `jsh_supplier` (`id`),
  CONSTRAINT `jsh_depothead_ibfk_4` FOREIGN KEY (`HandsPersonId`) REFERENCES `jsh_person` (`Id`),
  CONSTRAINT `jsh_depothead_ibfk_5` FOREIGN KEY (`AllocationProjectId`) REFERENCES `jsh_depot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='单据主表';

-- ----------------------------
-- Records of jsh_depothead
-- ----------------------------
INSERT INTO `jsh_depothead` VALUES ('7', '入库', '采购', null, 'GHDD201708120002', 'GHDD201708120002', '季圣华', '2017-08-12 12:04:07', '2017-08-12 12:03:23', '1', null, '12', '-30', null, '-36', '现付', 'abcdefg', '', null, null, '10', '3.6', '32.4', '30', '[\"10\",\"9\"]', '[\"10\",\"20\"]', '45', '');
INSERT INTO `jsh_depothead` VALUES ('8', '出库', '销售', null, 'XHDD201708120001', 'XHDD201708120001', '季圣华', '2017-08-12 18:10:14', '2017-08-12 18:09:45', '2', null, '11', '17', null, '24', '现付', '', '<7>,<6>', null, null, '22', '5.28', '18.72', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('9', '入库', '采购', null, 'GHDD201708120003', 'GHDD201708120003', '季圣华', '2017-08-12 21:01:09', '2017-08-12 21:00:36', '1', null, '11', '-100', null, '-120', '现付', '', '', null, null, '10', '12', '108', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('10', '入库', '采购', null, 'GHDD201708120004', 'GHDD201708120004', '季圣华', '2017-08-12 21:10:42', '2017-08-12 21:10:16', '1', null, '4', '-10', null, '-12', '现付', '', '', null, null, '10', '1.2', '10.8', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('11', '入库', '采购', null, 'GHDD201708120005', 'jshenghua001', '季圣华', '2017-08-12 22:07:44', '2017-08-12 22:06:37', '1', null, '12', '-20', null, '-24', '现付', '', '', null, null, '10', '2.4', '21.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('12', '入库', '采购', null, 'GHDD201708120006', 'GHDD201708120006', '季圣华', '2017-08-12 22:17:11', '2017-08-12 22:16:35', '1', null, '11', '-10', null, '-12', '现付', '', '', null, null, '10', '1.2', '10.8', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('13', '入库', '采购', null, 'GHDD201708120007', 'jishenghua3', '季圣华', '2017-08-12 22:17:52', '2017-08-12 22:17:14', '1', null, '4', '-20', null, '-24', '现付', '', '', null, null, '10', '2.4', '21.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('14', '入库', '采购', null, 'GHDD201708120008', 'jishenghua004', '季圣华', '2017-08-12 22:19:37', '2017-08-12 22:19:07', '1', null, '11', '-30', null, '-36', '现付', '', '', null, null, '10', '3.6', '32.4', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('16', '入库', '采购', null, 'GHDD201708120009', 'jishenghua005', '季圣华', '2017-08-12 22:26:23', '2017-08-12 22:25:14', '1', null, '10', '-20', null, '-24', '现付', '', '', null, null, '10', '2.4', '21.6', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('17', '入库', '采购', null, 'GHDD201708120010', 'GHDD201708120010', '季圣华', '2017-08-12 22:28:20', '2017-08-12 22:28:02', '1', null, '9', '-30', null, '-36', '现付', '', '', null, null, '10', '3.6', '32.4', null, '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('18', '入库', '采购', null, 'GHDD201708120011', 'GHDD201708120011', '季圣华', '2017-08-12 22:30:08', '2017-08-12 22:29:48', '1', null, '4', '-20', null, '-24', '现付', '', '', null, null, '10', '2.4', '21.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('19', '入库', '采购', null, 'GHDD201708120012', 'GHDD201708120012', '季圣华', '2017-08-12 22:30:57', '2017-08-12 22:29:32', '1', null, null, '-10', null, '-26.4', '现付', '', '', '[\"4\"]', '[\"-10\"]', '10', '2.64', '23.76', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('20', '入库', '采购', null, 'GHDD201708120013', 'GHDD201708120013', '季圣华', '2017-08-12 22:46:43', '2017-08-12 22:45:55', '1', null, '10', '-23', null, '-36', '现付', '', '', null, null, '20', '7.2', '28.8', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('21', '入库', '采购', null, 'GHDD201708120014', 'GHDD201708120014', '季圣华', '2017-08-12 22:46:52', '2017-08-12 22:45:59', '1', null, '11', '-20', null, '-26.4', '现付', '', '', null, null, '10', '2.64', '23.76', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('22', '入库', '采购', null, 'GHDD201708120015', 'GHDD201708120015', '季圣华', '2017-08-12 23:49:32', '2017-08-12 23:48:24', '1', null, '11', '-20', null, '-24', '现付', '', '', null, null, '10', '2.4', '21.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('23', '入库', '采购', null, 'GHDD201708140001', 'GHDD201708140001', '季圣华', '2017-08-14 20:41:54', '2017-08-14 20:40:49', '1', null, '4', '-300', null, '-360', '现付', '', '', null, null, '10', '36', '324', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('24', '入库', '采购', null, 'GHDD201708150001', 'GHDD201708150001', '季圣华', '2017-08-15 21:36:25', '2017-08-15 21:35:38', '1', null, '11', '-675', null, '-750', '现付', '', '', null, null, '10', '75', '675', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('25', '入库', '采购', null, 'GHDD201708150002', 'GHDD201708150002', '季圣华', '2017-08-15 22:31:46', '2017-08-15 22:29:47', '1', null, null, '-33', null, '-75', '现付', 'ababab', '', '[\"9\",\"10\"]', '[\"-22\",\"-11\"]', '10', '7.5', '67.5', '22', '[\"10\",\"8\"]', '[\"11\",\"11\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('26', '入库', '采购', null, 'GHDD201708160001', 'GHDD201708160001', '季圣华', '2017-08-16 23:50:35', '2017-08-16 23:47:42', '4', null, '9', '-162', null, '-150', '现付', '', '', null, null, '10', '18', '162', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('27', '入库', '采购', null, 'GHDD201708180001', 'GHDD201708180001', '季圣华', '2017-08-18 00:25:58', '2017-08-18 00:25:43', '1', null, '11', '-74.25', null, '-75', '现付', '', '', null, null, '10', '8.25', '74.25', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('28', '入库', '采购', null, 'GHDD201708270001', 'GHDD201708270001', '季圣华', '2017-08-27 23:10:44', '2017-08-27 23:06:05', '46', null, '10', '-64.8', null, '-72', '现付', '', '', null, null, '10', '7.2', '64.8', '10', '[\"10\"]', '[\"10\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('29', '出库', '销售', null, 'XSCK201708280001', 'XSCK201708280001', '季圣华', '2017-08-28 23:06:40', '2017-08-28 23:05:11', '2', null, '11', '120.85', null, '130', '现付', '', '<7>', null, null, '10', '13.65', '122.85', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('30', '入库', '销售退货', null, 'XSTH201708280001', 'XSTH201708280001', '季圣华', '2017-08-28 23:13:08', '2017-08-28 23:12:48', '2', null, '10', '-48', null, '-48', '现付', '', '<5>,<6>', null, null, '0', '0', '48', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('31', '出库', '采购退货', null, 'CGTH201708280001', 'CGTH201708280001', '季圣华', '2017-08-28 23:15:45', '2017-08-28 23:15:21', '1', null, '10', '28.6', null, '26', '现付', '', '', null, null, '0', '0', '28.6', '12', '[\"10\"]', '[\"12\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('32', '入库', '其它', null, 'QTRK201708280001', 'QTRK201708280001', '季圣华', '2017-08-28 23:17:55', '2017-08-28 23:17:33', '1', null, null, null, null, '12', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('33', '出库', '其它', null, 'QTCK201708280001', 'QTCK201708280001', '季圣华', '2017-08-28 23:21:14', '2017-08-28 23:20:36', '2', null, null, null, null, '65', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('37', '出库', '调拨', null, 'DBCK201708280002', 'DBCK201708280002', '季圣华', '2017-08-28 23:56:34', '2017-08-28 23:56:10', null, null, null, null, null, '1.3', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('38', '出库', '调拨', null, 'DBCK201708290001', 'DBCK201708290001', '季圣华', '2017-08-29 00:20:11', '2017-08-29 00:19:58', null, null, null, null, null, '2.6', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('41', '出库', '零售', null, 'LSCK201708290002', 'LSCK201708290002', '季圣华', '2017-08-29 23:29:39', '2017-08-29 23:29:06', '7', null, '10', '42', null, '42', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('42', '出库', '零售', null, 'LSCK201708290003', 'LSCK201708290003', '季圣华', '2017-08-29 23:35:12', '2017-08-29 23:33:21', '7', null, '11', '11', null, '11', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('43', '出库', '零售', null, 'LSCK201708290004', 'LSCK201708290004', '季圣华', '2017-08-29 23:39:44', '2017-08-29 23:39:28', '7', null, '9', '12.1', null, '12.1', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('44', '入库', '零售退货', null, 'LSTH201708290001', 'LSTH201708290001', '季圣华', '2017-08-29 23:48:43', '2017-08-29 23:46:35', '7', null, '10', '-2.2', null, '-2.2', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('45', '入库', '零售退货', null, 'LSTH201708290002', 'LSTH201708290002', '季圣华', '2017-08-29 23:51:55', '2017-08-29 23:51:31', '7', null, '12', '-3.3', null, '-3.3', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('48', '出库', '零售', null, 'LSCK201708310001', 'LSCK201708310001', '季圣华', '2017-08-31 00:30:31', '2017-08-31 00:29:10', '7', null, null, '12', null, '12', '现付', '', '', '[\"10\",\"11\"]', '[\"15\",\"20\"]', null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('49', '出库', '零售', null, 'LSCK201708310002', 'LSCK201708310002', '季圣华', '2017-08-31 00:57:40', '2017-08-31 00:57:08', '7', null, null, '12', null, '12', '现付', '', '', '[\"9\",\"11\"]', '[\"22\",\"11\"]', null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('50', '出库', '零售', null, 'LSCK201709030001', 'LSCK201709030001', '季圣华', '2017-09-03 12:51:50', '2017-09-03 12:51:21', '10', null, '10', '22', null, '22', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('52', '出库', '零售', null, 'LSCK201709040001', 'LSCK201709040001', '季圣华', '2017-09-04 21:32:49', '2017-09-04 21:31:24', '7', null, '11', '24.2', null, '24.2', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('53', '出库', '零售', null, 'LSCK201709040002', 'LSCK201709040002', '季圣华', '2017-09-04 21:34:02', '2017-09-04 21:33:30', '7', null, '9', '36.3', null, '36.3', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('54', '入库', '采购', null, 'CGRK201709040001', 'CGRK201709040001', '季圣华', '2017-09-04 22:20:12', '2017-09-04 22:13:00', '1', null, '10', '-10.8', null, '-12', '现付', '', '', null, null, '10', '1.2', '10.8', '12', '[\"9\"]', '[\"12\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('57', '入库', '采购', null, 'CGRK201709050001', 'CGRK201709050001', '季圣华', '2017-09-05 22:37:54', '2017-09-05 22:37:31', '1', null, '11', '-182.52', null, '-182.4', '现付', '', '', null, null, '0', '0', '182.52', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('63', '入库', '采购', null, 'CGRK201709170001', 'CGRK201709170001', '季圣华', '2017-09-17 21:45:14', '2017-09-17 21:44:50', '1', null, '10', '-13.2', null, '-12', '现付', '', '', null, null, '0', '0', '13.2', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('65', '入库', '采购', null, 'CGRK201709170002', 'CGRK201709170002', '季圣华', '2017-09-17 21:47:07', '2017-09-17 20:45:55', '1', null, null, '-42', null, '-39', '现付', '', '', '[\"12\",\"9\"]', '[\"-20\",\"-22\"]', '0', '0', '42.9', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('68', '其它', '组装单', null, 'ZZD2017092000001', 'ZZD2017092000001', '季圣华', '2017-09-20 23:29:28', '2017-09-20 23:29:13', null, null, null, null, null, '7', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('69', '其它', '拆卸单', null, 'CSD2017092000001', 'CSD2017092000001', '季圣华', '2017-09-20 23:40:55', '2017-09-20 23:40:41', null, null, null, null, null, '0', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('70', '入库', '采购', null, 'CGRK201709210001', 'CGRK201709210001', '季圣华', '2017-09-21 22:37:20', '2017-09-21 22:36:37', '1', null, null, '-50', null, '-50', '现付', '', '', '[\"4\",\"9\"]', '[\"-10\",\"-40\"]', '0', '0', '50', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('71', '入库', '销售退货', null, 'XSTH201709210001', 'XSTH201709210001', '季圣华', '2017-09-21 22:39:00', '2017-09-21 22:38:37', '2', null, '11', '-48', null, '-48', '现付', '', '<6>,<7>', null, null, '0', '0', '48', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('72', '入库', '其它', null, 'QTRK201709210001', 'QTRK201709210001', '季圣华', '2017-09-21 22:39:26', '2017-09-21 22:39:14', '4', null, null, null, null, '24', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('73', '出库', '销售', null, 'XSCK201709210001', 'XSCK201709210001', '季圣华', '2017-09-21 22:40:01', '2017-09-21 22:39:44', '2', null, '11', '10', null, '10', '现付', '', '<6>', null, null, '0', '0', '10', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('74', '出库', '采购退货', null, 'CGTH201709210001', 'CGTH201709210001', '季圣华', '2017-09-21 22:40:57', '2017-09-21 22:40:38', '4', null, '4', '5', null, '5', '现付', '', '', null, null, '0', '0', '5', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('75', '出库', '其它', null, 'QTCK201709210001', 'QTCK201709210001', '季圣华', '2017-09-21 22:41:15', '2017-09-21 22:41:02', '2', null, null, null, null, '13', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('76', '出库', '调拨', null, 'DBCK201709210001', 'DBCK201709210001', '季圣华', '2017-09-21 22:41:36', '2017-09-21 22:41:19', null, null, null, null, null, '10', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('77', '出库', '零售', null, 'LSCK201709210001', 'LSCK201709210001', '季圣华', '2017-09-21 22:42:44', '2017-09-21 22:42:21', '7', null, '4', '2.2', null, '2.2', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('78', '入库', '零售退货', null, 'LSTH201709210001', 'LSTH201709210001', '季圣华', '2017-09-21 22:46:07', '2017-09-21 22:45:49', '7', null, '4', '-2.2', null, '-2.2', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('79', '入库', '采购', null, 'CGRK201709210002', 'CGRK201709210002', '季圣华', '2017-09-21 23:16:37', '2017-09-21 23:16:21', '1', null, '11', '-23.76', null, '-21.6', '现付', '', '', null, null, '0', '0', '23.76', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('80', '其它', '组装单', null, 'ZZD2017092100001', 'ZZD2017092100001', '季圣华', '2017-09-21 23:17:16', '2017-09-21 23:16:59', null, null, null, null, null, '5', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('82', '入库', '采购', null, 'CGRK201709220001', 'CGRK201709220001', '季圣华', '2017-09-22 23:06:01', '2017-09-22 23:05:39', '1', null, null, '-50', null, '-52', '现付', '', '', '[\"11\",\"9\"]', '[\"-20\",\"-30\"]', '10', '5.72', '51.48', '5', '[\"11\",\"10\"]', '[\"2\",\"3\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('84', '入库', '采购', null, 'CGRK201709220002', 'CGRK201709220002', '季圣华', '2017-09-22 23:22:02', '2017-09-22 23:21:48', '1', null, '10', '-26.4', null, '-24', '现付', '', '', null, null, '0', '0', '26.4', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('85', '入库', '采购', null, 'CGRK201709240001', 'CGRK201709240001', '季圣华', '2017-09-24 22:46:00', '2017-09-24 22:44:35', '4', null, null, '-85', null, '-75', '现付', '', '', '[\"10\",\"9\"]', '[\"-20\",\"-65\"]', '0', '0', '87.75', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('87', '出库', '销售', null, 'XSCK201709250001', 'XSCK201709250001', '季圣华', '2017-09-25 22:24:08', '2017-09-25 22:23:47', '2', null, '10', '4', null, '4', '现付', '', '<6>', null, null, '0', '0', '4', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('88', '出库', '销售', null, 'XSCK201709250002', 'XSCK201709250002', '季圣华', '2017-09-25 22:36:51', '2017-09-25 22:35:09', '2', null, '10', '39.6', null, '40', '现付', '', '<7>', null, null, '10', '4.4', '39.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('89', '入库', '零售退货', null, 'LSTH201709260001', 'LSTH201709260001', '季圣华', '2017-09-26 00:26:52', '2017-09-26 00:26:19', '7', null, '9', '-18', null, '-18', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('90', '出库', '零售', null, 'LSCK201709260001', 'LSCK201709260001', '季圣华', '2017-09-26 22:31:24', '2017-09-26 22:29:50', '7', null, null, '100', null, '100', '现付', '', '', '[\"10\",\"11\"]', '[\"60\",\"40\"]', null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('93', '出库', '销售', null, 'XSCK201710080001', 'XSCK201710080001', '季圣华', '2017-10-08 19:12:23', '2017-10-08 19:11:44', '2', null, '10', '0', null, '2.6', '现付', '', '<7>', null, null, '0', '0', '2.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('94', '出库', '销售', null, 'XSCK201710080002', 'XSCK201710080002', '季圣华', '2017-10-08 19:58:55', '2017-10-08 19:58:27', '5', null, '9', '0', null, '8', '现付', '', '<6>', null, null, '0', '0', '8', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('95', '入库', '采购', null, 'CGRK201710180001', 'CGRK201710180001', '季圣华', '2017-10-18 23:21:24', '2017-10-18 23:21:12', '1', null, '11', '-2.86', null, '-2.6', '现付', '', '', null, null, '0', '0', '2.86', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('96', '出库', '销售', null, 'XSCK201710240001', 'XSCK201710240001', '季圣华', '2017-10-24 22:04:06', '2017-10-24 22:03:08', '2', null, '9', '0', null, '2.8', '现付', '', '<7>', null, null, '10', '0.28', '2.52', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('97', '入库', '采购', null, 'CGRK201710290001', 'CGRK201710290001', '季圣华', '2017-10-29 23:30:47', '2017-10-29 23:30:08', '4', null, '10', '0', null, '-200', '现付', '', '', null, null, '0', '0', '234', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('98', '入库', '采购', null, 'CGRK201710290002', 'CGRK201710290002', '季圣华', '2017-10-29 23:32:07', '2017-10-29 23:30:52', '4', null, '10', '0', null, '-300', '现付', '', '', null, null, '0', '0', '351', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('99', '入库', '采购', null, 'CGRK201710290003', 'CGRK201710290003', '季圣华', '2017-10-29 23:33:45', '2017-10-29 23:32:11', '4', null, '11', '-10', null, '-720', '现付', '', '', null, null, '0', '0', '842.4', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('101', '出库', '调拨', null, 'DBCK201711020001', 'DBCK201711020001', '季圣华', '2017-11-02 22:51:17', '2017-11-02 22:48:58', null, null, null, '0', null, '50', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('102', '出库', '零售', null, 'LSCK201711060001', 'LSCK201711060001', '季圣华', '2017-11-06 20:38:46', '2017-11-06 20:38:01', '7', null, null, '12', null, '12', '现付', '', '', '[\"9\",\"12\"]', '[\"10\",\"2\"]', null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('103', '入库', '采购', null, 'CGRK201711070001', 'CGRK201711070001', '季圣华', '2017-11-07 21:07:05', '2017-11-07 21:06:53', '1', null, '10', '-26.4', null, '-24', '现付', '', '', null, '', '0', '0', '26.4', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('104', '入库', '采购', null, 'CGRK201711070002', 'CGRK201711070002', '季圣华', '2017-11-07 21:07:40', '2017-11-07 21:07:08', '4', null, null, '-11', null, '-10', '现付', '', '', '[\"9\",\"11\"]', '[\"-10\",\"-1\"]', '0', '0', '11.7', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('105', '出库', '销售', null, 'XSCK201711070001', 'XSCK201711070001', '季圣华', '2017-11-07 21:08:48', '2017-11-07 21:08:34', '2', null, '10', '13', null, '13', '现付', '', '<6>', null, '', '0', '0', '13', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('106', '出库', '销售', null, 'XSCK201711070002', 'XSCK201711070002', '季圣华', '2017-11-07 21:09:20', '2017-11-07 21:08:51', '2', null, null, '13', null, '13', '现付', '', '<5>', '[\"9\",\"10\"]', '[\"5\",\"8\"]', '0', '0', '13', '54', '[\"11\",\"10\"]', '[\"21\",\"33\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('107', '入库', '采购', null, 'CGRK201712030001', 'CGRK201712030001', '季圣华', '2017-12-03 22:38:36', '2017-12-03 22:37:26', '4', null, '9', '-1', null, '-1', '现付', '', '', null, '', '0', '0', '1', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('108', '入库', '采购', null, 'CGRK201712030002', 'sdfasdfa', '季圣华', '2017-12-03 22:40:57', '2017-12-03 22:40:38', '4', null, '4', '-42.12', null, '-36', '现付', '', '', null, '', '0', '0', '42.12', null, '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('109', '入库', '采购', null, 'CGRK201712030003', 'CGRK201712030003', '季圣华', '2017-12-03 22:41:38', '2017-12-03 22:41:01', '4', null, '11', '-1.4', null, '-1.2', '现付', '', '', null, '', '0', '0', '1.4', null, '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('110', '入库', '采购', null, 'CGRK201712050001', 'CGRK201712050001', '季圣华', '2017-12-05 23:05:48', '2017-12-05 23:05:34', '1', null, '10', '-11', null, '-10', '现付', '', '', null, '', '0', '0', '11', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('111', '入库', '采购', null, 'CGRK201712050002', 'CGRK201712050002', '季圣华', '2017-12-05 23:12:53', '2017-12-05 23:12:40', '1', null, '10', '0', null, '-20', '现付', '', '', null, '', '0', '0', '22', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');
INSERT INTO `jsh_depothead` VALUES ('112', '出库', '销售', null, 'XSCK201712100001', 'XSCK201712100001', '季圣华', '2017-12-10 21:07:45', '2017-12-10 21:07:25', '2', null, '11', '2.6', null, '2.6', '现付', '', '<>', null, '', '0', '0', '2.6', null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('113', '入库', '采购', null, 'CGRK201712100001', 'CGRK201712100001', '季圣华', '2017-12-10 23:11:20', '2017-12-10 23:11:10', '4', null, '9', '-14.52', null, '-14.3', '现付', '', '', null, '', '0', '0', '14.52', '0', '[\"undefined\"]', '[\"undefined\"]', null, '');

-- ----------------------------
-- Table structure for `jsh_depotitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depotitem`;
CREATE TABLE `jsh_depotitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `MaterialId` bigint(20) NOT NULL COMMENT '材料Id',
  `MUnit` varchar(20) DEFAULT NULL COMMENT '商品计量单位',
  `OperNumber` decimal(24, 6) DEFAULT NULL COMMENT '数量',
  `BasicNumber` decimal(24, 6) DEFAULT NULL COMMENT '基础数量，如kg、瓶',
  `UnitPrice` decimal(24, 6) DEFAULT NULL COMMENT '单价',
  `TaxUnitPrice` decimal(24, 6) DEFAULT NULL COMMENT '含税单价',
  `AllPrice` decimal(24, 6) DEFAULT NULL COMMENT '金额',
  `Remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `Img` varchar(50) DEFAULT NULL COMMENT '图片',
  `Incidentals` decimal(24, 6) DEFAULT NULL COMMENT '运杂费',
  `DepotId` bigint(20) DEFAULT NULL COMMENT '仓库ID（库存是统计出来的）',
  `AnotherDepotId` bigint(20) DEFAULT NULL COMMENT '调拨时，对方仓库Id',
  `TaxRate` decimal(24, 6) DEFAULT NULL COMMENT '税率',
  `TaxMoney` decimal(24, 6) DEFAULT NULL COMMENT '税额',
  `TaxLastMoney` decimal(24, 6) DEFAULT NULL COMMENT '价税合计',
  `OtherField1` varchar(50) DEFAULT NULL COMMENT '自定义字段1-品名',
  `OtherField2` varchar(50) DEFAULT NULL COMMENT '自定义字段2-型号',
  `OtherField3` varchar(50) DEFAULT NULL COMMENT '自定义字段3-制造商',
  `OtherField4` varchar(50) DEFAULT NULL COMMENT '自定义字段4',
  `OtherField5` varchar(50) DEFAULT NULL COMMENT '自定义字段5',
  `MType` varchar(20) DEFAULT NULL COMMENT '商品类型',
  PRIMARY KEY (`Id`),
  KEY `FK2A819F475D61CCF7` (`MaterialId`),
  KEY `FK2A819F474BB6190E` (`HeaderId`),
  KEY `FK2A819F479485B3F5` (`DepotId`),
  KEY `FK2A819F47729F5392` (`AnotherDepotId`),
  CONSTRAINT `FK2A819F47729F5392` FOREIGN KEY (`AnotherDepotId`) REFERENCES `jsh_depot` (`id`),
  CONSTRAINT `FK2A819F479485B3F5` FOREIGN KEY (`DepotId`) REFERENCES `jsh_depot` (`id`),
  CONSTRAINT `jsh_depotitem_ibfk_1` FOREIGN KEY (`HeaderId`) REFERENCES `jsh_depothead` (`Id`) ON DELETE CASCADE,
  CONSTRAINT `jsh_depotitem_ibfk_2` FOREIGN KEY (`MaterialId`) REFERENCES `jsh_material` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='单据子表';

-- ----------------------------
-- Records of jsh_depotitem
-- ----------------------------
INSERT INTO `jsh_depotitem` VALUES ('7', '7', '500', '码', '30', '30', '1.2', '1.32', '36', 'remark', null, null, '3', null, '10', '3.6', '39.6', 'a', 'b', 'c', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('8', '8', '500', '码', '20', '20', '1.2', '1.2', '24', '', null, null, '3', null, null, '0', '24', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('9', '9', '500', '码', '100', '100', '1.2', '1.32', '120', '', null, null, '3', null, '10', '12', '132', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('10', '10', '500', '码', '10', '10', '1.2', '1.32', '12', '', null, null, '3', null, '10', '1.2', '13.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('11', '11', '500', '码', '20', '20', '1.2', '1.32', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('12', '12', '500', '码', '10', '10', '1.2', '1.32', '12', '', null, null, '3', null, '10', '1.2', '13.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('13', '13', '500', '码', '20', '20', '1.2', '1.32', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('14', '14', '500', '码', '30', '30', '1.2', '1.32', '36', '', null, null, '3', null, '10', '3.6', '39.6', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('16', '16', '500', '码', '20', '20', '1.2', '1.32', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('17', '17', '500', '码', '30', '30', '1.2', '1.32', '36', '', null, null, '3', null, '10', '3.6', '39.6', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('18', '18', '500', '码', '20', '20', '1.2', '1.32', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('19', '19', '500', '码', '22', '22', '1.2', '1.32', '26.4', '', null, null, '3', null, '10', '2.64', '29.04', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('20', '20', '500', '码', '30', '30', '1.2', '1.32', '36', '', null, null, '3', null, '10', '3.6', '39.6', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('21', '21', '500', '码', '22', '22', '1.2', '1.32', '26.4', '', null, null, '3', null, '10', '2.64', '29.04', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('22', '22', '500', '码', '20', '20', '1.2', '1.32', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('23', '23', '517', '箱', '10', '120', '36', '36', '360', '', null, null, '3', null, '0', '0', '360', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('24', '24', '518', '包', '10', '250', '75', '75', '750', '', null, null, '3', null, '0', '0', '750', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('25', '25', '518', '包', '1', '25', '75', '75', '75', '', null, null, '3', null, '0', '0', '75', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('26', '26', '518', '包', '2', '50', '75', '90', '150', '', null, null, '3', null, '20', '30', '180', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('27', '27', '518', '包', '1', '25', '75', '82.5', '75', '', null, null, '3', null, '10', '7.5', '82.5', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('28', '28', '517', '箱', '2', '24', '36', '36', '72', '', null, null, '3', null, '0', '0', '72', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('29', '29', '500', '码', '100', '100', '1.3', '1.37', '130', '', null, null, '3', null, '5', '6.5', '136.5', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('30', '30', '500', '码', '40', '40', '1.2', '1.2', '48', '', null, null, '3', null, '0', '0', '48', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('31', '31', '500', '码', '20', '20', '1.3', '1.43', '26', '', null, null, '3', null, '10', '2.6', '28.6', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('32', '32', '500', '码', '10', '10', '1.2', '1.32', '12', '', null, null, '3', null, '10', '1.2', '13.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('33', '33', '500', '码', '50', '50', '1.3', '1.43', '65', '', null, null, '3', null, '10', '6.5', '71.5', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('37', '37', '500', '码', '1', '1', '1.3', '1.3', '1.3', '', null, null, '3', '1', '0', '0', '1.3', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('38', '38', '500', '码', '2', '2', '1.3', '1.3', '2.6', '', null, null, '3', '1', '0', '0', '2.6', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('41', '41', '500', '码', '20', '20', '2.1', '2.31', '42', '', null, null, '3', null, '10', '4.2', '46.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('42', '42', '500', '码', '10', '10', '1.1', '1.1', '11', '', null, null, '3', null, '0', '0', '11', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('43', '43', '500', '码', '11', '11', '1.1', '1.1', '12.1', '', null, null, '3', null, '0', '0', '12.1', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('44', '44', '499', '码', '1', '1', '2.2', '2.2', '2.2', '', null, null, '3', null, '0', '0', '2.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('45', '45', '500', '码', '3', '3', '1.1', '1.1', '3.3', '', null, null, '3', null, '0', '0', '3.3', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('48', '48', '498', '码', '10', '10', '1.2', '1.2', '12', '', null, null, '3', null, '0', '0', '12', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('49', '49', '498', '码', '10', '10', '1.2', '1.2', '12', '', null, null, '3', null, '0', '0', '12', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('50', '50', '500', '码', '20', '20', '1.1', '1.1', '22', '', null, null, '3', null, '0', '0', '22', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('52', '52', '500', '码', '22', '22', '1.1', '1.1', '24.2', '', null, null, '3', null, '0', '0', '24.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('53', '53', '500', '码', '33', '33', '1.1', '1.1', '36.3', '', null, null, '3', null, '0', '0', '36.3', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('54', '54', '500', '码', '10', '10', '1.2', '1.2', '12', '', null, null, '1', null, '10', '0', '12', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('57', '57', '500', '码', '2', '2', '1.2', '1.26', '2.4', '', null, null, '3', null, '0', '0.12', '2.52', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('63', '57', '517', '箱', '5', '60', '36', '36', '180', '', null, null, '3', null, '0', '0', '180', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('64', '63', '500', '码', '10', '10', '1.2', '1.32', '12', '', null, null, '3', null, '10', '1.2', '13.2', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('66', '65', '498', '码', '30', '30', '1.3', '1.43', '39', '', null, null, '3', null, '10', '3.9', '42.9', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('71', '68', '498', '码', '1', '1', '3', '3', '3', '', null, null, '3', null, '0', '0', '3', '', '', '', '', '', '组合件');
INSERT INTO `jsh_depotitem` VALUES ('72', '68', '499', '码', '1', '1', '4', '4', '4', '', null, null, '3', null, '0', '0', '4', '', '', '', '', '', '普通子件');
INSERT INTO `jsh_depotitem` VALUES ('73', '69', '498', '码', '1', '1', '0', '0', '0', '', null, null, '1', null, '0', '0', '0', '', '', '', '', '', '组合件');
INSERT INTO `jsh_depotitem` VALUES ('74', '69', '499', '码', '1', '1', '0', '0', '0', '', null, null, '1', null, '0', '0', '0', '', '', '', '', '', '普通子件');
INSERT INTO `jsh_depotitem` VALUES ('75', '70', '487', '码', '50', '50', '1', '1', '50', '', null, null, '1', null, '10', '0', '50', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('76', '71', '499', '码', '20', '20', '2.4', '2.4', '48', '', null, null, '3', null, '0', '0', '48', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('77', '72', '499', '码', '10', '10', '2.4', '2.81', '24', '', null, null, '3', null, '17', '4.08', '28.08', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('78', '73', '487', '码', '10', '10', '1', '1', '10', '', null, null, '1', null, '0', '0', '10', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('79', '74', '487', '码', '5', '5', '1', '1', '5', '', null, null, '3', null, '0', '0', '5', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('80', '75', '500', '码', '10', '10', '1.3', '1.3', '13', '', null, null, '3', null, '0', '0', '13', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('81', '76', '487', '码', '10', '10', '1', '1', '10', '', null, null, '3', '1', '0', '0', '10', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('82', '77', '499', '码', '1', '1', '2.2', '2.2', '2.2', '', null, null, '3', null, '0', '0', '2.2', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('83', '78', '499', '码', '1', '1', '2.2', '2.2', '2.2', '', null, null, '3', null, '0', '0', '2.2', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('84', '79', '499', '码', '9', '9', '2.4', '2.64', '21.6', '', null, null, '3', null, '10', '2.16', '23.76', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('85', '80', '500', '码', '1', '1', '2', '2', '2', '', null, null, '3', null, '0', '0', '2', '', '', '', '', '', '组合件');
INSERT INTO `jsh_depotitem` VALUES ('86', '80', '498', '码', '1', '1', '3', '3', '3', '', null, null, '3', null, '0', '0', '3', '', '', '', '', '', '普通子件');
INSERT INTO `jsh_depotitem` VALUES ('88', '82', '498', '码', '40', '40', '1.3', '1.43', '52', '', null, null, '3', null, '10', '5.2', '57.2', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('90', '84', '499', '码', '10', '10', '2.4', '2.64', '24', '', null, null, '1', null, '10', '2.4', '26.4', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('91', '85', '518', '包', '1', '25', '75', '87.75', '75', '', null, null, '3', null, '17', '12.75', '87.75', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('93', '87', '518', 'kg', '1', '1', '4', '4', '4', '', null, null, '3', null, '0', '0', '4', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('94', '88', '517', '瓶', '10', '10', '4', '4.4', '40', '', null, null, '3', null, '10', '4', '44', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('95', '89', '517', '箱', '1', '12', '18', '18', '18', '', null, null, '3', null, '0', '0', '18', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('96', '90', '518', '包', '2', '50', '50', '50', '100', '', null, null, '3', null, '0', '0', '100', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('101', '94', '517', '瓶', '2', '2', '4', '4', '8', '', null, null, '3', null, '0', '0', '8', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('102', '95', '498', '码', '2', '2', '1.3', '1.43', '2.6', '', null, null, '3', null, '10', '0.26', '2.86', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('103', '96', '498', '码', '2', '2', '1.4', '1.4', '2.8', '', null, null, '3', null, '0', '0', '2.8', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('104', '97', '485', '码', '200', '200', '1', '1.17', '200', '', null, null, '3', null, '17', '34', '234', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('105', '98', '487', '码', '300', '300', '1', '1.17', '300', '', null, null, '3', null, '17', '51', '351', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('106', '99', '517', '箱', '20', '240', '36', '42.12', '720', '', null, null, '3', null, '17', '122.4', '842.4', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('107', '100', '487', '码', '1', '1', '1', '1', '1', '', null, null, '4', null, '0', '0', '1', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('108', '101', '485', '码', '50', '50', '1', '1', '50', '', null, null, '3', '1', '0', '0', '50', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('109', '102', '498', '码', '10', '10', '1.2', '1.2', '12', '', null, null, '3', null, '0', '0', '12', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('110', '103', '499', '码', '10', '10', '2.4', '2.64', '24', '', null, null, '3', null, '10', '2.4', '26.4', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('111', '104', '487', '码', '10', '10', '1', '1.17', '10', '', null, null, '3', null, '17', '1.7', '11.7', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('112', '105', '499', '码', '5', '5', '2.6', '2.6', '13', '', null, null, '3', null, '0', '0', '13', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('113', '106', '499', '码', '5', '5', '2.6', '2.6', '13', '', null, null, '3', null, '0', '0', '13', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('114', '107', '487', '码', '1', '1', '1', '1', '1', '', null, null, '1', null, '0', '0', '1', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('116', '108', '517', '箱', '1', '12', '36', '42.12', '36', '', null, null, '3', null, '17', '6.12', '42.12', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('117', '109', '500', '码', '1', '1', '1.2', '1.4', '1.2', '', null, null, '3', null, '17', '0.2', '1.4', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('118', '110', '487', '码', '10', '10', '1', '1.1', '10', '', null, null, '3', null, '10', '1', '11', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('119', '111', '487', '码', '20', '20', '1', '1.1', '20', '', null, null, '1', null, '10', '2', '22', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('120', '112', '499', '码', '1', '1', '2.6', '2.6', '2.6', '', null, null, '3', null, '0', '0', '2.6', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('121', '113', '498', '码', '1', '1', '1.3', '1.52', '1.3', '', null, null, '3', null, '17', '0.22', '1.52', '', '', '', '', '', '');
INSERT INTO `jsh_depotitem` VALUES ('122', '113', '498', '码', '10', '10', '1.3', '1.3', '13', '', null, null, '1', null, '0', '0', '13', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for `jsh_functions`
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of jsh_functions
-- ----------------------------
INSERT INTO `jsh_functions` VALUES ('1', '00', '系统管理', '0', '', '', '0010', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('2', '01', '基础数据', '0', '', '', '0020', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('11', '0001', '系统管理', '00', '', '', '0110', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('12', '000101', '应用管理', '0001', '../manage/app.html', '', '0132', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('13', '000102', '角色管理', '0001', '../manage/role.html', '', '0130', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('14', '000103', '用户管理', '0001', '../manage/user.html', '', '0140', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('15', '000104', '日志管理', '0001', '../manage/log.html', '', '0160', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('16', '000105', '功能管理', '0001', '../manage/functions.html', '', '0135', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('21', '0101', '商品管理', '01', '', '', '0220', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('22', '010101', '商品类别', '0101', '../materials/materialcategory.html', '', '0230', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('23', '010102', '商品信息', '0101', '../materials/material.html', '', '0240', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('24', '0102', '基本资料', '01', '', '', '0250', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('25', '01020101', '供应商信息', '0102', '../manage/vendor.html', '', '0260', '', '电脑版', '1,2');
INSERT INTO `jsh_functions` VALUES ('26', '010202', '仓库信息', '0102', '../manage/depot.html', '', '0270', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('31', '010206', '经手人管理', '0102', '../materials/person.html', '', '0284', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('32', '0502', '采购管理', '05', '', '', '0330', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('33', '050201', '采购入库', '0502', '../materials/purchase_in_list.html', '', '0340', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('38', '0603', '销售管理', '06', '', '', '0390', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('40', '080107', '调拨出库', '0801', '../materials/allocation_out_list.html', '', '0807', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('41', '060303', '销售出库', '0603', '../materials/sale_out_list.html', '', '0394', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('44', '0704', '财务管理', '07', '', '', '0450', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('59', '030101', '库存状况', '0301', '../reports/in_out_stock_report.html', '', '0600', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('194', '010204', '收支项目', '0102', '../manage/inOutItem.html', '', '0282', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('195', '010205', '结算账户', '0102', '../manage/account.html', '', '0283', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('196', '03', '报表查询', '0', '', '', '0025', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('197', '070402', '收入单', '0704', '../financial/item_in.html', '', '0465', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('198', '0301', '报表查询', '03', '', '', '0570', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('199', '050204', '采购退货', '0502', '../materials/purchase_back_list.html', '', '0345', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('200', '060305', '销售退货', '0603', '../materials/sale_back_list.html', '', '0396', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('201', '080103', '其它入库', '0801', '../materials/other_in_list.html', '', '0803', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('202', '080105', '其它出库', '0801', '../materials/other_out_list.html', '', '0805', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('203', '070403', '支出单', '0704', '../financial/item_out.html', '', '0470', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('204', '070404', '收款单', '0704', '../financial/money_in.html', '', '0475', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('205', '070405', '付款单', '0704', '../financial/money_out.html', '', '0480', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('206', '070406', '转账单', '0704', '../financial/giro.html', '', '0490', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('207', '030102', '结算账户', '0301', '../reports/account_report.html', '', '0610', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('208', '030103', '进货统计', '0301', '../reports/buy_in_report.html', '', '0620', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('209', '030104', '销售统计', '0301', '../reports/sale_out_report.html', '', '0630', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('210', '040102', '零售出库', '0401', '../materials/retail_out_list.html', '', '0405', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('211', '040104', '零售退货', '0401', '../materials/retail_back_list.html', '', '0407', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('212', '070407', '收预付款', '0704', '../financial/advance_in.html', '', '0495', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('213', '010207', '礼品卡管理', '0102', '../manage/depotGift.html', '', '0290', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('214', '040106', '礼品充值', '0401', '../materials/gift_recharge_list.html', '', '0408', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('215', '040108', '礼品销售', '0401', '../materials/gift_out_list.html', '', '0409', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('216', '030105', '礼品卡统计', '0301', '../reports/gift_manage_report.html', '', '0635', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('217', '01020102', '客户信息', '0102', '../manage/customer.html', '', '0262', '', '电脑版', '1,2');
INSERT INTO `jsh_functions` VALUES ('218', '01020103', '会员信息', '0102', '../manage/member.html', '', '0263', '', '电脑版', '1,2');
INSERT INTO `jsh_functions` VALUES ('219', '000107', '资产管理', '0001', '../asset/asset.html', '', '0170', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('220', '010103', '计量单位', '0101', '../manage/unit.html', '', '0245', '', '电脑版', null);
INSERT INTO `jsh_functions` VALUES ('221', '04', '零售管理', '0', '', '', '0028', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('222', '05', '采购管理', '0', '', '', '0030', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('223', '06', '销售管理', '0', '', '', '0035', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('224', '07', '财务管理', '0', '', '', '0040', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('225', '0401', '零售管理', '04', '', '', '0401', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('226', '030106', '入库明细', '0301', '../reports/in_detail.html', '', '0640', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('227', '030107', '出库明细', '0301', '../reports/out_detail.html', '', '0645', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('228', '030108', '入库汇总', '0301', '../reports/in_material_count.html', '', '0650', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('229', '030109', '出库汇总', '0301', '../reports/out_material_count.html', '', '0655', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('232', '080109', '组装单', '0801', '../materials/assemble_list.html', '', '0809', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('233', '080111', '拆卸单', '0801', '../materials/disassemble_list.html', '', '0811', '', '电脑版', '3,4,5');
INSERT INTO `jsh_functions` VALUES ('234', '000105', '系统配置', '0001', '../manage/systemConfig.html', '', '0165', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('235', '030110', '客户对账', '0301', '../reports/customer_account.html', '', '0660', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('236', '000106', '商品属性', '0001', '../materials/materialProperty.html', '', '0168', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('237', '030111', '供应商对账', '0301', '../reports/vendor_account.html', '', '0665', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('238', '08', '仓库管理', '0', '', '', '0038', '', '电脑版', '');
INSERT INTO `jsh_functions` VALUES ('239', '0801', '仓库管理', '08', '', '', '0801', '', '电脑版', '');

-- ----------------------------
-- Table structure for `jsh_inoutitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_inoutitem`;
CREATE TABLE `jsh_inoutitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='收支项目';

-- ----------------------------
-- Records of jsh_inoutitem
-- ----------------------------
INSERT INTO `jsh_inoutitem` VALUES ('1', '办公耗材', '支出', '办公耗材');
INSERT INTO `jsh_inoutitem` VALUES ('5', '房租收入', '收入', '房租收入');
INSERT INTO `jsh_inoutitem` VALUES ('7', '利息收入', '收入', '利息收入');
INSERT INTO `jsh_inoutitem` VALUES ('8', '水电费', '支出', '水电费水电费');
INSERT INTO `jsh_inoutitem` VALUES ('9', '快递费', '支出', '快递费');
INSERT INTO `jsh_inoutitem` VALUES ('10', '交通报销费', '支出', '交通报销费');
INSERT INTO `jsh_inoutitem` VALUES ('11', '差旅费', '支出', '差旅费');
INSERT INTO `jsh_inoutitem` VALUES ('12', '全车贴膜-普通', '收入', '');
INSERT INTO `jsh_inoutitem` VALUES ('13', '全车贴膜-高档', '收入', '');
INSERT INTO `jsh_inoutitem` VALUES ('14', '洗车', '收入', '');
INSERT INTO `jsh_inoutitem` VALUES ('15', '保养汽车', '收入', '');

-- ----------------------------
-- Table structure for `jsh_log`
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
  PRIMARY KEY (`id`),
  KEY `FKF2696AA13E226853` (`userID`),
  CONSTRAINT `FKF2696AA13E226853` FOREIGN KEY (`userID`) REFERENCES `jsh_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5852 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of jsh_log
-- ----------------------------
INSERT INTO `jsh_log` VALUES ('1722', '63', '登录系统', '192.168.1.104', '2016-11-27 13:17:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1723', '63', '登录系统', '192.168.1.104', '2016-11-27 13:17:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1724', '63', '退出系统', '192.168.1.104', '2016-11-27 13:17:48', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('1725', '65', '登录系统', '192.168.1.104', '2016-11-27 13:17:52', '0', '管理用户：ls 登录系统', 'ls 登录系统');
INSERT INTO `jsh_log` VALUES ('1726', '65', '退出系统', '192.168.1.104', '2016-11-27 13:18:18', '0', '管理用户：ls 退出系统', 'ls 退出系统');
INSERT INTO `jsh_log` VALUES ('1727', '63', '登录系统', '192.168.1.104', '2016-11-27 13:18:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1728', '63', '更新UserBusiness', '192.168.1.104', '2016-11-27 13:18:44', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1729', '63', '退出系统', '192.168.1.104', '2016-11-27 13:18:48', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('1730', '65', '登录系统', '192.168.1.104', '2016-11-27 13:18:53', '0', '管理用户：ls 登录系统', 'ls 登录系统');
INSERT INTO `jsh_log` VALUES ('1731', '63', '登录系统', '192.168.1.104', '2016-12-04 10:38:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1732', '63', '增加物料', '192.168.1.104', '2016-12-04 10:40:52', '0', '增加物料名称为  11 成功！', '增加物料成功');
INSERT INTO `jsh_log` VALUES ('1733', '63', '更新物料', '192.168.1.104', '2016-12-04 10:59:57', '0', '更新物料ID为  499 成功！', '更新物料成功');
INSERT INTO `jsh_log` VALUES ('1734', '63', '更新物料', '192.168.1.104', '2016-12-04 11:00:13', '0', '更新物料ID为  499 成功！', '更新物料成功');
INSERT INTO `jsh_log` VALUES ('1735', '63', '删除物料', '192.168.1.104', '2016-12-04 11:00:38', '0', '删除物料ID为  499 成功！', '删除物料成功');
INSERT INTO `jsh_log` VALUES ('1736', '63', '增加物料', '192.168.1.104', '2016-12-04 11:02:35', '0', '增加物料名称为  11 成功！', '增加物料成功');
INSERT INTO `jsh_log` VALUES ('1737', '63', '批量删除物料', '192.168.1.104', '2016-12-04 11:02:41', '0', '批量删除物料ID为  500 成功！', '批量删除物料成功');
INSERT INTO `jsh_log` VALUES ('1738', '63', '更新功能', '192.168.1.104', '2016-12-04 11:04:43', '0', '更新功能ID为  26 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1739', '63', '增加供应商', '192.168.1.104', '2016-12-04 11:38:13', '0', '增加供应商名称为  aa 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('1740', '63', '增加供应商', '192.168.1.104', '2016-12-04 11:48:36', '0', '增加供应商名称为  aaaa 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('1741', '63', '删除供应商', '192.168.1.104', '2016-12-04 11:48:53', '0', '删除供应商ID为  3,名称为  aa成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('1742', '63', '更新供应商', '192.168.1.104', '2016-12-04 11:48:59', '0', '更新供应商ID为  4 成功！', '更新供应商成功');

-- ----------------------------
-- Table structure for `jsh_material`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material`;
CREATE TABLE `jsh_material` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CategoryId` bigint(20) DEFAULT NULL COMMENT '产品类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Mfrs` varchar(50) DEFAULT NULL COMMENT '制造商',
  `Packing` decimal(24, 6) DEFAULT NULL COMMENT '包装（KG/包）',
  `SafetyStock` decimal(24, 6) DEFAULT NULL COMMENT '安全存量（KG）',
  `Model` varchar(50) DEFAULT NULL COMMENT '型号',
  `Standard` varchar(50) DEFAULT NULL COMMENT '规格',
  `Color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `Unit` varchar(50) DEFAULT NULL COMMENT '单位-单个',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `RetailPrice` decimal(24, 6) DEFAULT NULL COMMENT '零售价',
  `LowPrice` decimal(24, 6) DEFAULT NULL COMMENT '最低售价',
  `PresetPriceOne` decimal(24, 6) DEFAULT NULL COMMENT '预设售价一',
  `PresetPriceTwo` decimal(24, 6) DEFAULT NULL COMMENT '预设售价二',
  `UnitId` bigint(20) DEFAULT NULL COMMENT '计量单位Id',
  `FirstOutUnit` varchar(50) DEFAULT NULL COMMENT '首选出库单位',
  `FirstInUnit` varchar(50) DEFAULT NULL COMMENT '首选入库单位',
  `PriceStrategy` varchar(500) DEFAULT NULL COMMENT '价格策略',
  `Enabled` bit(1) DEFAULT NULL COMMENT '启用 0-禁用  1-启用',
  `OtherField1` varchar(50) DEFAULT NULL COMMENT '自定义1',
  `OtherField2` varchar(50) DEFAULT NULL COMMENT '自定义2',
  `OtherField3` varchar(50) DEFAULT NULL COMMENT '自定义3',
  PRIMARY KEY (`Id`),
  KEY `FK675951272AB6672C` (`CategoryId`),
  KEY `UnitId` (`UnitId`),
  CONSTRAINT `FK675951272AB6672C` FOREIGN KEY (`CategoryId`) REFERENCES `jsh_materialcategory` (`Id`),
  CONSTRAINT `jsh_material_ibfk_1` FOREIGN KEY (`UnitId`) REFERENCES `jsh_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=563 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of jsh_material
-- ----------------------------
INSERT INTO `jsh_material` VALUES ('485', '2', '棉线', 'a1', null, '100', 'A21-4321', '5g', '白色', '码', '', '1', '1', '1', '1', null, '', '', '[{\"basic\":{\"Unit\":\"\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', 'b2', 'c3', 'd4');
INSERT INTO `jsh_material` VALUES ('487', '1', '网布', '制造商b', null, '100', '12343', '10g', '', '码', '', '1', '1', '1', '1', null, '', '', '[{\"basic\":{\"Unit\":\"kg\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"包\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', null, null, null);
INSERT INTO `jsh_material` VALUES ('498', '1', '蕾丝', '制造商c', null, null, 'B123a', '6g', '', '码', '', '1.2', '1', '1.3', '1.4', null, '', '', '[{\"basic\":{\"Unit\":\"kg\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"包\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', null, null, null);
INSERT INTO `jsh_material` VALUES ('499', '1', '棉线', '制造商d', null, null, 'A21-1234', '7g', '', '码', '', '2.2', '2', '2.4', '2.6', null, '', '', '[{\"basic\":{\"Unit\":\"kg\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"包\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', null, null, null);
INSERT INTO `jsh_material` VALUES ('500', '1', '纯棉线', '制造商e', null, null, 'AAA666', '11g', '', '码', '', '1.1', '1', '1.2', '1.3', null, '', '', '[{\"basic\":{\"Unit\":\"kg\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"包\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', null, null, null);
INSERT INTO `jsh_material` VALUES ('517', '1', '奶酪', '制造商', null, null, 'AAAA', '12ml', '', '', '', null, null, null, null, '8', '瓶', '箱', '[{\"basic\":{\"Unit\":\"瓶\",\"RetailPrice\":\"1.5\",\"LowPrice\":\"2\",\"PresetPriceOne\":\"3\",\"PresetPriceTwo\":\"4\"}},{\"other\":{\"Unit\":\"箱\",\"RetailPrice\":\"18\",\"LowPrice\":\"24\",\"PresetPriceOne\":\"36\",\"PresetPriceTwo\":\"48\"}}]', '', null, null, null);
INSERT INTO `jsh_material` VALUES ('518', '1', '安慕希', '伊利', null, null, 'abcd', '350ml', '银白色', '', '', null, null, null, null, '2', 'kg', '包', '[{\"basic\":{\"Unit\":\"kg\",\"RetailPrice\":\"2\",\"LowPrice\":\"1\",\"PresetPriceOne\":\"3\",\"PresetPriceTwo\":\"4\"}},{\"other\":{\"Unit\":\"包\",\"RetailPrice\":\"50\",\"LowPrice\":\"25\",\"PresetPriceOne\":\"75\",\"PresetPriceTwo\":\"100\"}}]', '', '', '', '');
INSERT INTO `jsh_material` VALUES ('562', '1', '红苹果（蛇果）', '', null, null, '60#', '大铁筐', '', '', '', null, null, null, null, null, '', '', '[{\"basic\":{\"Unit\":\"\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}},{\"other\":{\"Unit\":\"\",\"RetailPrice\":\"\",\"LowPrice\":\"\",\"PresetPriceOne\":\"\",\"PresetPriceTwo\":\"\"}}]', '', '', '', '');

-- ----------------------------
-- Table structure for `jsh_materialcategory`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_materialcategory`;
CREATE TABLE `jsh_materialcategory` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `CategoryLevel` smallint(6) DEFAULT NULL COMMENT '等级',
  `ParentId` bigint(20) DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`Id`),
  KEY `FK3EE7F725237A77D8` (`ParentId`),
  CONSTRAINT `FK3EE7F725237A77D8` FOREIGN KEY (`ParentId`) REFERENCES `jsh_materialcategory` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='产品类型表';

-- ----------------------------
-- Records of jsh_materialcategory
-- ----------------------------
INSERT INTO `jsh_materialcategory` VALUES ('1', '根目录', '1', '1');
INSERT INTO `jsh_materialcategory` VALUES ('2', '花边一级A', '1', '1');
INSERT INTO `jsh_materialcategory` VALUES ('3', '花边一级B', '1', '1');
INSERT INTO `jsh_materialcategory` VALUES ('4', '其他', '2', '3');
INSERT INTO `jsh_materialcategory` VALUES ('5', '其他', '3', '4');
INSERT INTO `jsh_materialcategory` VALUES ('6', '花边二级A', '2', '2');
INSERT INTO `jsh_materialcategory` VALUES ('7', '花边三级A', '3', '6');
INSERT INTO `jsh_materialcategory` VALUES ('8', '花边二级B', '2', '2');
INSERT INTO `jsh_materialcategory` VALUES ('9', '花边一级C', '1', '1');
INSERT INTO `jsh_materialcategory` VALUES ('10', '花边三级B', '3', '6');

-- ----------------------------
-- Table structure for `jsh_materialproperty`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_materialproperty`;
CREATE TABLE `jsh_materialproperty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nativeName` varchar(50) DEFAULT NULL COMMENT '原始名称',
  `enabled` bit(1) DEFAULT NULL COMMENT '是否启用',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `anotherName` varchar(50) DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='产品扩展字段表';

-- ----------------------------
-- Records of jsh_materialproperty
-- ----------------------------
INSERT INTO `jsh_materialproperty` VALUES ('1', '规格', '', '02', '规格');
INSERT INTO `jsh_materialproperty` VALUES ('2', '颜色', '', '01', '颜色');
INSERT INTO `jsh_materialproperty` VALUES ('3', '制造商', '', '03', '制造商');
INSERT INTO `jsh_materialproperty` VALUES ('4', '自定义1', '', '04', '自定义1');
INSERT INTO `jsh_materialproperty` VALUES ('5', '自定义2', '', '05', '自定义2');
INSERT INTO `jsh_materialproperty` VALUES ('6', '自定义3', '', '06', '自定义3');

-- ----------------------------
-- Table structure for `jsh_person`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_person`;
CREATE TABLE `jsh_person` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='经手人表';

-- ----------------------------
-- Records of jsh_person
-- ----------------------------
INSERT INTO `jsh_person` VALUES ('3', '财务员', '王五-财务');
INSERT INTO `jsh_person` VALUES ('4', '财务员', '赵六-财务');
INSERT INTO `jsh_person` VALUES ('5', '业务员', '小李');
INSERT INTO `jsh_person` VALUES ('6', '业务员', '小军');
INSERT INTO `jsh_person` VALUES ('7', '业务员', '小曹');

-- ----------------------------
-- Table structure for `jsh_role`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_role`;
CREATE TABLE `jsh_role` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of jsh_role
-- ----------------------------
INSERT INTO `jsh_role` VALUES ('4', '管理员', null, null, null);
INSERT INTO `jsh_role` VALUES ('5', '仓管员', null, null, null);
INSERT INTO `jsh_role` VALUES ('6', 'aaaa', null, null, null);

-- ----------------------------
-- Table structure for `jsh_supplier`
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
  `AdvanceIn` decimal(24, 6) DEFAULT '0' COMMENT '预收款',
  `BeginNeedGet` decimal(24, 6) DEFAULT NULL COMMENT '期初应收',
  `BeginNeedPay` decimal(24, 6) DEFAULT NULL COMMENT '期初应付',
  `AllNeedGet` decimal(24, 6) DEFAULT NULL COMMENT '累计应收',
  `AllNeedPay` decimal(24, 6) DEFAULT NULL COMMENT '累计应付',
  `fax` varchar(30) DEFAULT NULL COMMENT '传真',
  `telephone` varchar(30) DEFAULT NULL COMMENT '手机',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `taxNum` varchar(50) DEFAULT NULL COMMENT '纳税人识别号',
  `bankName` varchar(50) DEFAULT NULL COMMENT '开户行',
  `accountNumber` varchar(50) DEFAULT NULL COMMENT '账号',
  `taxRate` decimal(24, 6) DEFAULT NULL COMMENT '税率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='供应商/客户信息表';

-- ----------------------------
-- Records of jsh_supplier
-- ----------------------------
INSERT INTO `jsh_supplier` VALUES ('1', '上海某某花边工厂', '乔治', '', '', '', '1', '供应商', '', '0', null, '20', null, null, '', '', '', '', '', '', '10');
INSERT INTO `jsh_supplier` VALUES ('2', '客户AAAA', '佩琪', '', '', '', '1', '客户', '', '24', '10', null, null, null, '', '', '', '', '', '', null);
INSERT INTO `jsh_supplier` VALUES ('4', '苏州新源布料厂', '龙哥', '13000000000', '312341@qq.com', '55', '1', '供应商', '', '0', null, '44', null, null, '', '', '', '', '', '', '17');
INSERT INTO `jsh_supplier` VALUES ('5', '客户BBBB', '彪哥', '13000000000', '666@qq.com', '', '1', '客户', '', '36', '20', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jsh_supplier` VALUES ('6', '南通宝贝家纺', '姗姗', '1231', '31243@qq.com', '备注备注备注', '1', '客户', '', '0', '5', null, null, null, '2134', '15678903', '地址地址地址', '纳税人识别号', '开户行', '31234124312', '0.17');
INSERT INTO `jsh_supplier` VALUES ('7', '非会员', '宋江', '13000000000', '123456@qq.com', '', '1', '会员', '', '76.6', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `jsh_supplier` VALUES ('8', 'hy00001', '宋江', '13000000000', '', '', '1', '会员', '', '956', null, null, null, null, '', '', '', '', '', '', null);
INSERT INTO `jsh_supplier` VALUES ('9', 'hy00002', '吴用', '13000000000', '', '', '1', '会员', '', '344', null, null, null, null, '', '', '', '', '', '', null);
INSERT INTO `jsh_supplier` VALUES ('10', '1268787965', '李逵', '82567384', '423@qq.com', '', '1', '会员', '', '2122', null, null, null, null, '', '13000000001', '', '', '', '', null);
INSERT INTO `jsh_supplier` VALUES ('45', '666666666', '武松', '82567384', '423@qq.com', '', '1', '会员', '', '2100', '0', '0', '0', '0', '', '13000000001', '', '', '', '', '0');
INSERT INTO `jsh_supplier` VALUES ('46', '南通居梦莱家纺', '曹操', '', '', '', '1', '供应商', '', '0', null, null, '0', '0', '', '13000000000', '', '', '', '', null);

-- ----------------------------
-- Table structure for `jsh_systemconfig`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_systemconfig`;
CREATE TABLE `jsh_systemconfig` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数';

-- ----------------------------
-- Records of jsh_systemconfig
-- ----------------------------
INSERT INTO `jsh_systemconfig` VALUES ('1', 'basic', 'company_name', '南通jshERP公司', '公司名称');
INSERT INTO `jsh_systemconfig` VALUES ('2', 'basic', 'company_contacts', '张三', '公司联系人');
INSERT INTO `jsh_systemconfig` VALUES ('3', 'basic', 'company_address', '南通市通州区某某路', '公司地址');
INSERT INTO `jsh_systemconfig` VALUES ('4', 'basic', 'company_tel', '0513-10101010', '公司电话');
INSERT INTO `jsh_systemconfig` VALUES ('5', 'basic', 'company_fax', '0513-18181818', '公司传真');
INSERT INTO `jsh_systemconfig` VALUES ('6', 'basic', 'company_post_code', '226300', '公司邮编');

-- ----------------------------
-- Table structure for `jsh_unit`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_unit`;
CREATE TABLE `jsh_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `UName` varchar(50) DEFAULT NULL COMMENT '名称，支持多单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='多单位表';

-- ----------------------------
-- Records of jsh_unit
-- ----------------------------
INSERT INTO `jsh_unit` VALUES ('2', 'kg,包(1:25)');
INSERT INTO `jsh_unit` VALUES ('8', '瓶,箱(1:12)');
INSERT INTO `jsh_unit` VALUES ('11', 'qwe,sed(1:33)');

-- ----------------------------
-- Table structure for `jsh_user`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_user`;
CREATE TABLE `jsh_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '用户姓名--例如张三',
  `loginame` varchar(255) DEFAULT NULL COMMENT '登录用户名--可能为空',
  `password` varchar(50) DEFAULT NULL COMMENT '登陆密码',
  `position` varchar(200) DEFAULT NULL COMMENT '职位',
  `department` varchar(255) DEFAULT NULL COMMENT '所属部门',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `phonenum` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `ismanager` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为管理者 0==管理者 1==员工',
  `isystem` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否系统自带数据 ',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态',
  `description` varchar(500) DEFAULT NULL COMMENT '用户描述信息',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of jsh_user
-- ----------------------------
INSERT INTO `jsh_user` VALUES ('63', '季圣华', 'jsh', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', '', '1', '1', null, '', null);
INSERT INTO `jsh_user` VALUES ('64', '张三', 'zs', 'e10adc3949ba59abbe56e057f20f883e', '', '销售', '', '', '1', '0', null, '', null);
INSERT INTO `jsh_user` VALUES ('65', '李四', 'ls', 'e10adc3949ba59abbe56e057f20f883e', '', '销售', '', '', '1', '0', null, '', null);
INSERT INTO `jsh_user` VALUES ('67', 'fas666', 'asd555', null, 'asdf333', 'sdf444', '11111@qq.com', '222222', '1', '0', null, 'sdf0000', null);
INSERT INTO `jsh_user` VALUES ('74', '21312sfdfsdf', '1231234', null, '', '', '', '', '1', '0', null, '', null);
INSERT INTO `jsh_user` VALUES ('84', '123123', 'jsh123', null, '3123', '1231', '', '', '1', '0', null, '', null);
INSERT INTO `jsh_user` VALUES ('86', '2333', 'sdf111aaa', null, '3232', '23', '', '32323', '1', '0', null, '33232', null);
INSERT INTO `jsh_user` VALUES ('87', '122123132', 'sdfasd1', null, '', '', '', '', '1', '0', null, '', null);
INSERT INTO `jsh_user` VALUES ('90', '232343', '233', null, '', '', '', '', '1', '0', null, '', null);

-- ----------------------------
-- Table structure for `jsh_userbusiness`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_userbusiness`;
CREATE TABLE `jsh_userbusiness` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Type` varchar(50) DEFAULT NULL COMMENT '类别',
  `KeyId` varchar(50) DEFAULT NULL COMMENT '主ID',
  `Value` varchar(10000) DEFAULT NULL COMMENT '值',
  `BtnStr` varchar(2000) DEFAULT NULL COMMENT '按钮权限',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户/角色/模块关系表';

-- ----------------------------
-- Records of jsh_userbusiness
-- ----------------------------
INSERT INTO `jsh_userbusiness` VALUES ('1', 'RoleAPP', '4', '[23][24][25][27][8][26][22][7][3][6]', null);
INSERT INTO `jsh_userbusiness` VALUES ('2', 'RoleAPP', '5', '[8][7][6]', null);
INSERT INTO `jsh_userbusiness` VALUES ('3', 'RoleAPP', '6', '[21][1][8]', null);
INSERT INTO `jsh_userbusiness` VALUES ('4', 'RoleAPP', '7', '[21][1][8][11]', null);
INSERT INTO `jsh_userbusiness` VALUES ('5', 'RoleFunctions', '4', '[13][12][16][14][15][234][236][22][23][220][25][217][218][26][194][195][31][213][232][233][59][207][208][209][216][226][227][228][229][235][237][210][211][214][215][33][200][201][41][199][202][40][197][203][204][205][206][212]', '[{\"funId\":\"25\",\"btnStr\":\"1\"},{\"funId\":\"217\",\"btnStr\":\"1\"},{\"funId\":\"218\",\"btnStr\":\"1\"},{\"funId\":\"232\",\"btnStr\":\"3\"},{\"funId\":\"233\",\"btnStr\":\"3\"},{\"funId\":\"33\",\"btnStr\":\"3\"},{\"funId\":\"200\",\"btnStr\":\"3\"},{\"funId\":\"201\",\"btnStr\":\"3\"},{\"funId\":\"210\",\"btnStr\":\"3\"},{\"funId\":\"211\",\"btnStr\":\"3\"},{\"funId\":\"214\",\"btnStr\":\"3\"},{\"funId\":\"215\",\"btnStr\":\"3\"},{\"funId\":\"41\",\"btnStr\":\"3\"},{\"funId\":\"199\",\"btnStr\":\"3\"},{\"funId\":\"202\",\"btnStr\":\"3\"},{\"funId\":\"40\",\"btnStr\":\"3\"}]');
INSERT INTO `jsh_userbusiness` VALUES ('6', 'RoleFunctions', '5', '[22][23][25][26][194][195][31][33][200][201][41][199][202]', null);
INSERT INTO `jsh_userbusiness` VALUES ('7', 'RoleFunctions', '6', '[13][12][16][33]', '[{\"funId\":\"33\",\"btnStr\":\"4\"}]');
INSERT INTO `jsh_userbusiness` VALUES ('8', 'RoleAPP', '8', '[21][1][8][11][10]', null);
INSERT INTO `jsh_userbusiness` VALUES ('9', 'RoleFunctions', '7', '[168][13][12][16][14][15][189][18][19][132]', null);
INSERT INTO `jsh_userbusiness` VALUES ('10', 'RoleFunctions', '8', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187]', null);
INSERT INTO `jsh_userbusiness` VALUES ('11', 'RoleFunctions', '9', '[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]', null);
INSERT INTO `jsh_userbusiness` VALUES ('12', 'UserRole', '1', '[5]', null);
INSERT INTO `jsh_userbusiness` VALUES ('13', 'UserRole', '2', '[6][7]', null);
INSERT INTO `jsh_userbusiness` VALUES ('14', 'UserDepot', '2', '[1][2][6][7]', null);
INSERT INTO `jsh_userbusiness` VALUES ('15', 'UserDepot', '1', '[1][2][5][6][7][10][12][14][15][17]', null);
INSERT INTO `jsh_userbusiness` VALUES ('16', 'UserRole', '63', '[4]', null);
INSERT INTO `jsh_userbusiness` VALUES ('17', 'RoleFunctions', '13', '[46][47][48][49]', null);
INSERT INTO `jsh_userbusiness` VALUES ('18', 'UserDepot', '63', '[1][3]', null);
INSERT INTO `jsh_userbusiness` VALUES ('19', 'UserDepot', '5', '[6][45][46][50]', null);
INSERT INTO `jsh_userbusiness` VALUES ('20', 'UserRole', '5', '[5]', null);
INSERT INTO `jsh_userbusiness` VALUES ('21', 'UserRole', '64', '[5]', null);
INSERT INTO `jsh_userbusiness` VALUES ('22', 'UserDepot', '64', '[1]', null);
INSERT INTO `jsh_userbusiness` VALUES ('23', 'UserRole', '65', '[5]', null);
INSERT INTO `jsh_userbusiness` VALUES ('24', 'UserDepot', '65', '[1]', null);
INSERT INTO `jsh_userbusiness` VALUES ('25', 'UserCustomer', '64', '[5][2]', null);
INSERT INTO `jsh_userbusiness` VALUES ('26', 'UserCustomer', '65', '[6]', null);
INSERT INTO `jsh_userbusiness` VALUES ('27', 'UserCustomer', '63', '[5][2]', null);
-- ----------------------------
-- 时间：2019年1月21日
-- version：1.0.0
-- 此次更新添加序列号功能
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------

-- ----------------------------
-- 添加序列号表
-- ----------------------------
DROP TABLE IF EXISTS `jsh_serial_number`;
CREATE TABLE `jsh_serial_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_Id` bigint(20) DEFAULT NULL COMMENT '产品表id',
  `serial_Number` varchar(64) DEFAULT NULL COMMENT '序列号',
  `is_Sell` bit(1) DEFAULT 0 COMMENT '是否卖出，0未卖出，1卖出',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `delete_Flag` bit(1) DEFAULT 0 COMMENT '删除标记，0未删除，1删除',
  `create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_Time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='序列号表';

-- ----------------------------
-- 产品表新增字段是否启用序列号
-- ----------------------------
alter table jsh_material add enableSerialNumber bit(1) DEFAULT 0 COMMENT '是否开启序列号，0否，1是';
-- ----------------------------
-- 时间：2019年1月24日
-- version：1.0.1
-- 此次更新添加序列号菜单
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- ----------------------------
-- 添加序列号菜单
-- ----------------------------
delete from `jsh_functions` where Name='序列号';
INSERT INTO `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`) VALUES ('010104', '序列号', '0101', '../manage/serialNumber.html', b'0', '0246', b'1', '电脑版', '');
-- ----------------------------
-- 删除单据主表供应商id字段对应外键约束
-- ----------------------------
ALTER TABLE jsh_depothead DROP FOREIGN KEY jsh_depothead_ibfk_3;
-- ----------------------------
-- 序列号表添加单据主表id字段，用于跟踪序列号流向
-- ----------------------------
alter table jsh_serial_number add depothead_Id bigint(20) DEFAULT null COMMENT '单据主表id，用于跟踪序列号流向';
-- ----------------------------
-- 修改商品表enableSerialNumber字段类型为varchar(1)
-- ----------------------------
alter table jsh_material change enableSerialNumber enableSerialNumber varchar(1) DEFAULT '0' COMMENT '是否开启序列号，0否，1是';
-- ----------------------------
-- 修改序列号表is_Sell字段类型为varchar(1)
-- 修改序列号表delete_Flag字段类型为varchar(1)
-- ----------------------------
alter table jsh_serial_number change is_Sell is_Sell varchar(1) DEFAULT '0' COMMENT '是否卖出，0未卖出，1卖出';
alter table jsh_serial_number change delete_Flag delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- ----------------------------
-- 删除单据子表单据主表id字段对应外键约束
-- ----------------------------
ALTER TABLE jsh_depotitem DROP FOREIGN KEY jsh_depotitem_ibfk_1;
-- ----------------------------
-- 时间：2019年2月1日
-- version：1.0.2
-- 此次更新添加sequence表，用于获取一个唯一的数值
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- ----------------------------
-- 添加表tbl_sequence
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sequence`;
CREATE TABLE tbl_sequence (
  seq_name VARCHAR(50) NOT NULL COMMENT '序列名称',
  min_value bigint(20) NOT NULL COMMENT '最小值',
  max_value bigint(20) NOT NULL COMMENT '最大值',
  current_val bigint(20) NOT NULL COMMENT '当前值',
  increment_val INT DEFAULT '1' NOT NULL COMMENT '增长步数',
  remark VARCHAR(500) DEFAULT null  COMMENT '备注',
  PRIMARY KEY (seq_name)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sequence表';

-- ----------------------------
-- 添加表单据编号sequence
-- 插入数据前判断，防止数据重复插入
-- ----------------------------
insert into tbl_sequence (seq_name, min_value, max_value, current_val, increment_val,remark)
select 'depot_number_seq', 1, 999999999999999999, 1, 1,'单据编号sequence' from dual where not exists
(select * from tbl_sequence where  seq_name='depot_number_seq');
-- ----------------------------
-- 创建function _nextval() 用于获取当前序列号
-- ----------------------------
DROP FUNCTION IF EXISTS `_nextval`;
DELIMITER ;;
CREATE FUNCTION `_nextval`(name varchar(50)) RETURNS mediumtext CHARSET utf8
begin
declare _cur bigint;
declare _maxvalue bigint;  -- 接收最大值
declare _increment int; -- 接收增长步数
set _increment = (select increment_val from tbl_sequence where seq_name = name);
set _maxvalue = (select max_value from tbl_sequence where seq_name = name);
set _cur = (select current_val from tbl_sequence where seq_name = name for update);
update tbl_sequence                      -- 更新当前值
 set current_val = _cur + increment_val
 where seq_name = name ;
if(_cur + _increment >= _maxvalue) then  -- 判断是都达到最大值
      update tbl_sequence
        set current_val = minvalue
        where seq_name = name ;
end if;
return _cur;
end
;;
DELIMITER ;

-- ----------------------------
-- 时间：2019年2月18日
-- version：1.0.3
-- 此次更新修改产品类型表jsh_materialcategory，添加一些字段
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- ----------------------------
-- 产品类型表添加字段sort，显示顺序
-- ----------------------------
alter table jsh_materialcategory add sort varchar(10) DEFAULT null COMMENT '显示顺序';
-- ----------------------------
-- 产品类型表添加字段status，状态，0系统默认，1启用，2删除
-- ----------------------------
alter table jsh_materialcategory add status varchar(1) DEFAULT '0' COMMENT '状态，0系统默认，1启用，2删除';
-- ----------------------------
-- 产品类型表添加字段serial_no，编号
-- ----------------------------
alter table jsh_materialcategory add serial_no varchar(100) DEFAULT null COMMENT '编号';
-- ----------------------------
-- 产品类型表添加字段remark，备注
-- ----------------------------
alter table jsh_materialcategory add remark varchar(1024) DEFAULT null COMMENT '备注';
-- ----------------------------
-- 产品类型表添加字段create_time，创建时间
-- ----------------------------
alter table jsh_materialcategory add create_time datetime DEFAULT null COMMENT '创建时间';
-- ----------------------------
-- 产品类型表添加字段creator，创建人
-- ----------------------------
alter table jsh_materialcategory add creator bigint(20) DEFAULT null COMMENT '创建人';
-- ----------------------------
-- 产品类型表添加字段update_time，更新时间
-- ----------------------------
alter table jsh_materialcategory add update_time datetime DEFAULT null COMMENT '更新时间';
-- ----------------------------
-- 产品类型表添加字段updater，更新人
-- ----------------------------
alter table jsh_materialcategory add updater bigint(20) DEFAULT null COMMENT '更新人';

-- ----------------------------
-- 去掉jsh_materialcategory外键
-- ----------------------------
ALTER TABLE jsh_materialcategory DROP FOREIGN KEY FK3EE7F725237A77D8;

-- ----------------------------
-- 修改根目录父节点id为-1
-- 设置根目录编号为1
-- ----------------------------
update jsh_materialcategory set ParentId='-1' where id='1';

-- ----------------------------
-- 删除礼品卡管理、礼品充值、礼品销售、礼品卡统计的功能数据
-- ----------------------------
delete from jsh_functions where id in (213,214,215,216);

-- ----------------------------
-- 新增采购订单、销售订单的功能数据
-- 主键自增长，直接指定主键插入数据的方式可能会和本地数据冲突
-- 插入数据前判断，防止数据重复插入
-- ----------------------------
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`)
select '050202', '采购订单', '0502', '../materials/purchase_orders_list.html', b'0', '0335',b'1', '电脑版', '' from dual where not exists
(select * from jsh_functions where  Number='050202' and PNumber='0502');
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`)
select '060301', '销售订单', '0603', '../materials/sale_orders_list.html', b'0', '0392', b'1', '电脑版', '' from dual where not exists
(select * from jsh_functions where  Number='060301' and PNumber='0603');

-- ----------------------------
-- 改管理员的功能权限
-- ----------------------------
update jsh_userbusiness SET Type = 'RoleFunctions', KeyId = '4', 
Value = '[13][12][16][14][15][234][236][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][210][211][242][33][199][243][41][200][201][202][40][232][233][197][203][204][205][206][212]' 
where Id = 5;

-- ----------------------------
-- 时间：2019年2月25日
-- version：1.0.4
-- 此次更新仓库添加负责人信息，负责人信息从用户表获取
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- ----------------------------
-- 仓库表添加字段principal，负责人
-- ----------------------------
alter table jsh_depot add principal bigint(20) DEFAULT null COMMENT '负责人';

-- ----------------------------
-- 时间：2019年3月6日
-- version：1.0.5
-- 此次更新
-- 1、添加机构表
-- 2、添加机构用户关系表
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- ----------------------------
-- 添加机构表
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
  remark VARCHAR(500) DEFAULT null  COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
  `org_create_time` datetime DEFAULT NULL COMMENT '机构创建时间',
  `org_stop_time` datetime DEFAULT NULL COMMENT '机构停运时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机构表';
-- ----------------------------
-- 添加机构用户关系表
-- ----------------------------
DROP TABLE IF EXISTS `jsh_orga_user_rel`;
CREATE TABLE `jsh_orga_user_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orga_id` bigint(20) NOT NULL  COMMENT '机构id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_blng_orga_dspl_seq` varchar(20) DEFAULT NULL COMMENT '用户在所属机构中显示顺序',
  `delete_flag` char(1) DEFAULT 0 COMMENT '删除标记，0未删除，1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机构用户关系表';
-- ----------------------------
-- 添加机构管理菜单
-- 插入数据前判断，防止数据重复插入
-- ----------------------------
INSERT INTO `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`)
select '000108', '机构管理', '0001', '../manage/organization.html', b'1', '0139', b'1', '电脑版', '' from dual where not exists
(select * from jsh_functions where  Number='000108' and PNumber='0001');
-- ----------------------------
-- 添加根机构
-- 插入时判断对应数据是否存在，防止多次执行产生重复数据
-- ----------------------------
INSERT INTO jsh_organization (org_no, org_full_name, org_abr, org_tpcd, org_stcd, org_parent_no, sort, remark, create_time, creator, update_time, updater, org_create_time, org_stop_time)
select '01', '根机构', '根机构', NULL, '2', '-1', '1', '根机构，初始化存在', NULL, NULL, NULL, NULL, NULL, NULL from dual where not exists
(select * from jsh_organization where org_no='01' and org_abr='根机构' and org_parent_no='-1'  );
-- ----------------------------
-- 时间：2019年3月9日
-- version：1.0.6
-- 此次更新
-- 整改jsh_systemconfig表的字段
-- ----------------------------
alter table jsh_systemconfig drop type;
alter table jsh_systemconfig drop name;
alter table jsh_systemconfig drop value;
alter table jsh_systemconfig drop description;
alter table jsh_systemconfig add company_name varchar(50) DEFAULT null COMMENT '公司名称';
alter table jsh_systemconfig add company_contacts varchar(20) DEFAULT null COMMENT '公司联系人';
alter table jsh_systemconfig add company_address varchar(50) DEFAULT null COMMENT '公司地址';
alter table jsh_systemconfig add company_tel varchar(20) DEFAULT null COMMENT '公司电话';
alter table jsh_systemconfig add company_fax varchar(20) DEFAULT null COMMENT '公司传真';
alter table jsh_systemconfig add company_post_code varchar(20) DEFAULT null COMMENT '公司邮编';
delete from jsh_systemconfig;
insert into jsh_systemconfig (`company_name`, `company_contacts`, `company_address`, `company_tel`, `company_fax`, `company_post_code`) values("南通jshERP公司","张三","南通市通州区某某路","0513-10101010","0513-18181818","226300");

-- ----------------------------
-- 时间：2019年3月9日
-- version：1.0.7
-- 改管理员的功能权限
-- ----------------------------
update jsh_userbusiness SET
Value = '[13][12][16][243][14][15][234][236][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]'
where Id = 5;
-- ----------------------------
-- 给订单功能加审核和反审核的功能按钮权限
-- ----------------------------
update jsh_functions SET PushBtn = '3' where Number = '050202' and PNumber = '0502';
update jsh_functions SET PushBtn = '3' where Number = '060301' and PNumber = '0603';
-- ----------------------------
-- 改管理员的按钮权限
-- ----------------------------
update jsh_userbusiness SET
BtnStr = '[{"funId":"25","btnStr":"1"},{"funId":"217","btnStr":"1"},{"funId":"218","btnStr":"1"},{"funId":"241","btnStr":"3"},{"funId":"242","btnStr":"3"}]'
where Id = 5;

-- ----------------------------
-- 时间：2019年3月10日
-- version：1.0.8
-- 改状态字段的类型，增加关联单据字段
-- ----------------------------
alter table jsh_depothead change Status Status varchar(1) DEFAULT '0' COMMENT '状态，0未审核、1已审核、2已转采购|销售';
alter table jsh_depothead add `LinkNumber` varchar(50) DEFAULT null COMMENT '关联订单号';
-- ----------------------------
-- 时间：2019年3月12日
-- version：1.0.9
-- 此次更新
-- 1、根据本地用户表中现有部门生成机构表数据，同时重建机构和用户的关联关系
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
DROP FUNCTION IF EXISTS `_buildOrgAndOrgUserRel`;
DELIMITER ;;
CREATE FUNCTION `_buildOrgAndOrgUserRel` (name varchar(50)) RETURNS mediumtext CHARSET utf8
begin

declare _org_full_name varchar(500); -- 机构全称
declare _org_abr varchar(20);  -- 机构简称
declare _sort int default 0;
declare _success_msg varchar(50) default '重建机构及机构用户关系成功'; -- 机构全称
 -- 遍历数据结束标志
declare done int DEFAULT 0;
-- 获取用户表中唯一的部门信息列表
declare orgCur cursor for select distinct department from jsh_user where department!='' and department is not null;

 -- 将结束标志绑定到游标
declare continue handler for not found set done = 1;
  -- 循环部门信息列表在机构表插入数据
  -- 打开游标
  open orgCur;
  -- 开始循环
  read_loop: loop
    -- 提取游标里的数据，这里只有一个，多个的话也一样；
    fetch orgCur into _org_full_name;
    -- 声明结束的时候
    if done=1 then
      leave read_loop;
    end if;
    -- 这里做你想做的循环的事件
    if length(_org_full_name)<=20 then
			set _org_abr=_org_full_name;
		else
			set _org_abr=left(_org_full_name,20);
	end if;
	set _sort=_sort+1;
	insert into jsh_organization (org_full_name, org_abr,  org_stcd, org_parent_no, sort, remark)
	values (_org_full_name,_org_abr, '1', '01', _sort, '机构表初始化');
		begin
			declare _userId bigint;
			declare _orgId bigint;
			 -- 遍历数据结束标志
			declare ogrUserRelDone int DEFAULT 0;
			-- 根据用户表和机构表部门关联关系，重建用户和机构关联关系
			declare ogrUserRelCur cursor for select user.id as userId,org.id as orgId from jsh_user user,jsh_organization org
			where 1=1  and user.department=org.org_full_name and user.department =_org_full_name;
			 -- 将结束标志绑定到游标
			declare continue handler for not found set ogrUserRelDone = 1;
			-- 打开游标
			  open ogrUserRelCur;
			  -- 开始循环
			  rel_read_loop: loop
			    -- 提取游标里的数据，这里只有一个，多个的话也一样；
			    fetch ogrUserRelCur into _userId,_orgId;
			    -- 声明结束的时候
			    if ogrUserRelDone=1 then
			      leave rel_read_loop;
			    end if;
				insert into `jsh_orga_user_rel`(`orga_id`, `user_id`, `delete_flag`) VALUES (_orgId,_userId,'0');

			  end loop rel_read_loop;
		  -- 关闭游标
		  close ogrUserRelCur;
		end;

  end loop read_loop;
  -- 关闭游标
  close orgCur;

-- 清空用户表中的部门信息
update jsh_user set department=null;

return _success_msg;
end
;;
DELIMITER ;
-- ----------------------------
-- 初始化机构数据，重建机构用户关系
-- ----------------------------
select _buildOrgAndOrgUserRel('初始化机构数据，重建机构用户关系') from dual;
-- ----------------------------
-- 删除一次性函数
-- ----------------------------
DROP FUNCTION _buildOrgAndOrgUserRel;

-- ----------------------------
-- 时间：2019年3月13日
-- version：1.0.10
-- 此次更新
-- 1、设置用户表的用户状态status默认值为0
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------

alter table jsh_user change Status Status tinyint(4) DEFAULT '0' COMMENT '状态，0：正常，1：删除，2封禁';
update jsh_user set status='0' where status is null;
-- ----------------------------
-- 设置根目录编号为1
-- ----------------------------
update jsh_materialcategory set serial_no='1' where id='1';

-- ----------------------------
-- 时间：2019年3月18日
-- version：1.0.11
-- 此次更新
-- 1、批量增加大部分表的tenant_id租户字段
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
alter table jsh_account add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_accounthead add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_accountitem add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_asset add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_assetcategory add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_assetname add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_depot add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_depothead add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_depotitem add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_inoutitem add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_log add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_material add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_materialcategory add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_orga_user_rel add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_organization add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_person add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_role add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_serial_number add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_supplier add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_systemconfig add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_unit add tenant_id bigint(20) DEFAULT null COMMENT '租户id';
alter table jsh_user add tenant_id bigint(20) DEFAULT null COMMENT '租户id';

-- ----------------------------
-- 时间：2019年3月27日
-- version：1.0.12
-- 此次更新
-- 添加删除标记，将物理删除修改为逻辑删除
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
-- 角色表	jsh_role
alter table jsh_role add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 用户 角色 模块关系表	jsh_userbusiness
alter table jsh_userbusiness add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 功能模块表	jsh_functions
alter table jsh_functions add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 应用表	jsh_app
alter table jsh_app add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 产品表	jsh_material
alter table jsh_material add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 产品扩展字段表	jsh_materialproperty
alter table jsh_materialproperty add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 经手人表	jsh_person
alter table jsh_person add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 供应商 客户信息表	jsh_supplier
alter table jsh_supplier add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 系统参数表	jsh_systemconfig
alter table jsh_systemconfig add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 多单位表	jsh_unit
alter table jsh_unit add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 仓库表	jsh_depot
alter table jsh_depot add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 账户信息表	jsh_account
alter table jsh_account add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 财务主表	jsh_accounthead
alter table jsh_accounthead add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 财务子表	jsh_accountitem
alter table jsh_accountitem add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 资产记录表	jsh_asset
alter table jsh_asset add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 资产类型表	jsh_assetcategory
alter table jsh_assetcategory add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 资产信息表	jsh_assetname
alter table jsh_assetname add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 单据主表	jsh_depothead
alter table jsh_depothead add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 单据子表	jsh_depotitem
alter table jsh_depotitem add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';
-- 收支项目表	jsh_inoutitem
alter table jsh_inoutitem add  delete_Flag varchar(1) DEFAULT '0' COMMENT '删除标记，0未删除，1删除';

-- ----------------------------
-- 时间：2019年4月11日
-- version：1.0.13
-- 此次更新
-- 删除所有外键
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------

-- ----------------------------
-- 删除财务主表对应外键约束
-- ----------------------------
ALTER TABLE jsh_accounthead DROP FOREIGN KEY FK9F4C0D8DAAE50527;
ALTER TABLE jsh_accounthead DROP FOREIGN KEY FK9F4C0D8DB610FC06;
ALTER TABLE jsh_accounthead DROP FOREIGN KEY FK9F4C0D8DC4170B37;
-- ----------------------------
-- 删除财务子表对应外键约束
-- ----------------------------
ALTER TABLE jsh_accountitem DROP FOREIGN KEY FK9F4CBAC0AAE50527;
ALTER TABLE jsh_accountitem DROP FOREIGN KEY FK9F4CBAC0C5FE6007;
ALTER TABLE jsh_accountitem DROP FOREIGN KEY FK9F4CBAC0D203EDC5;
-- ----------------------------
-- 删除资产记录表对应外键约束
-- ----------------------------
ALTER TABLE jsh_asset DROP FOREIGN KEY FK353690ED27D23FE4;
ALTER TABLE jsh_asset DROP FOREIGN KEY FK353690ED3E226853;
ALTER TABLE jsh_asset DROP FOREIGN KEY FK353690ED61FE182C;
ALTER TABLE jsh_asset DROP FOREIGN KEY FK353690ED9B6CB285;
ALTER TABLE jsh_asset DROP FOREIGN KEY FK353690EDAD45B659;
-- ----------------------------
-- 删除资产信息表对应外键约束
-- ----------------------------
ALTER TABLE jsh_assetname DROP FOREIGN KEY FKA4ADCCF866BC8AD3;
-- ----------------------------
-- 删除单据主表对应外键约束
-- ----------------------------
ALTER TABLE jsh_depothead DROP FOREIGN KEY FK2A80F214AAE50527;
ALTER TABLE jsh_depothead DROP FOREIGN KEY jsh_depothead_ibfk_1;
ALTER TABLE jsh_depothead DROP FOREIGN KEY jsh_depothead_ibfk_4;
ALTER TABLE jsh_depothead DROP FOREIGN KEY jsh_depothead_ibfk_5;
-- ----------------------------
-- 删除单据子表对应外键约束
-- ----------------------------
ALTER TABLE jsh_depotitem DROP FOREIGN KEY FK2A819F47729F5392;
ALTER TABLE jsh_depotitem DROP FOREIGN KEY FK2A819F479485B3F5;
ALTER TABLE jsh_depotitem DROP FOREIGN KEY jsh_depotitem_ibfk_2;
-- ----------------------------
-- 删除操作日志表对应外键约束
-- ----------------------------
ALTER TABLE jsh_log DROP FOREIGN KEY FKF2696AA13E226853;
-- ----------------------------
-- 删除产品表对应外键约束
-- ----------------------------
ALTER TABLE jsh_material DROP FOREIGN KEY FK675951272AB6672C;
ALTER TABLE jsh_material DROP FOREIGN KEY jsh_material_ibfk_1;

-- ----------------------------
-- 时间：2019年4月30日
-- version：1.0.14
-- 此次更新
-- 增加仓库默认功能 增加库存预警功能
-- 特别提醒：之后的sql都是在之前基础上迭代，可以对已存在的系统进行数据保留更新
-- ----------------------------
alter table jsh_depot add  is_default bit(1) DEFAULT NULL COMMENT '是否默认';
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`)
select '030112', '库存预警', '0301', '../reports/stock_warning_report.html', b'0', '0670', b'1', '电脑版', '' from dual where not exists
(select * from jsh_functions where  Number='030112' and PNumber='0301');

-- ----------------------------
-- 改管理员的功能权限
-- ----------------------------
update jsh_userbusiness SET Type = 'RoleFunctions', KeyId = '4',
Value = '[13][12][16][243][14][15][234][236][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][244][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212]'
where Id = 5;

-- ----------------------------
-- 给app的功能增加代号 在功能表增加个人信息
-- ----------------------------
update jsh_app SET Number = '02' where name='个人信息';
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`)
select '02', '个人信息', '0', '', b'1', '0005', b'1', '电脑版', '' from dual where not exists
(select * from jsh_functions where  Number='02' and PNumber='0');

-- ----------------------------
-- 时间：2019年6月23日
-- 增加新手引导模块
-- ----------------------------
INSERT INTO `jsh_app` VALUES ('28', '09', '新手引导', 'app', 'userHelp.png', '../user/userHelp.html', '1000', '500', '\0', '\0', '\0', 'dock', '210', '', '', '0');
INSERT INTO `jsh_functions` VALUES ('246', '09', '新手引导', '0', '', '', '0115', '', '电脑版', '', '0');
update jsh_userbusiness SET Value = '[3][6][7][22][23][24][25][26][27][28]'
where Type = 'RoleAPP' and (KeyId = '4' or KeyId = '10');
update jsh_userbusiness SET
Value = '[245][13][12][16][243][14][15][234][236][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][244][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][246]'
where Type = 'RoleFunctions' and KeyId = '4';
update jsh_userbusiness SET
Value = '[245][13][243][14][15][234][22][23][220][240][25][217][218][26][194][195][31][59][207][208][209][226][227][228][229][235][237][244][210][211][241][33][199][242][41][200][201][202][40][232][233][197][203][204][205][206][212][246]'
where Type = 'RoleFunctions' and KeyId = '10';


-- ----------------------------
-- 时间：2019年6月26日
-- 删除多余的资产相关表
-- ----------------------------
drop table jsh_asset;
drop table jsh_assetcategory;
drop table jsh_assetname;