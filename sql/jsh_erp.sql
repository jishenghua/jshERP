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
INSERT INTO `jsh_depothead` VALUES ('60', '出库', '礼品充值', null, 'LPCZ201709050001', 'LPCZ201709050001', '季圣华', '2017-09-05 23:45:48', '2017-09-05 23:42:17', null, null, null, null, null, '13', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('61', '出库', '礼品销售', null, 'LPXS201709050001', 'LPXS201709050001', '季圣华', '2017-09-05 23:48:10', '2017-09-05 23:46:04', null, null, null, null, null, '6.5', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
INSERT INTO `jsh_depothead` VALUES ('62', '出库', '礼品充值', null, 'LPCZ201709050002', 'LPCZ201709050002', '季圣华', '2017-09-05 23:52:41', '2017-09-05 23:51:26', null, null, null, null, null, '4', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
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
INSERT INTO `jsh_depothead` VALUES ('100', '出库', '礼品销售', null, 'LPXS201711010001', 'LPXS201711010001', '季圣华', '2017-11-01 23:06:40', '2017-11-01 23:06:13', null, null, null, '0', null, '1', '现付', '', '', null, null, null, null, null, null, null, null, null, '');
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
INSERT INTO `jsh_depotitem` VALUES ('60', '60', '500', '码', '10', '10', '1.3', '1.3', '13', '', null, null, '3', '4', '0', '0', '13', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('61', '61', '500', '码', '5', '5', '1.3', '1.3', '6.5', '', null, null, '4', null, '0', '0', '6.5', '', '', '', '', '', null);
INSERT INTO `jsh_depotitem` VALUES ('62', '62', '517', '瓶', '1', '1', '4', '4', '4', '', null, null, '1', '6', '0', '0', '4', '', '', '', '', '', null);
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
INSERT INTO `jsh_depotitem` VALUES ('100', '93', '500', '码', '2', '2', '1.3', '1.3', '2.6', '', null, null, '1', null, '0', '0', '2.6', '', '', '', '', '', '');
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
INSERT INTO `jsh_log` VALUES ('1743', '63', '更新功能', '192.168.1.104', '2016-12-04 13:06:24', '0', '更新功能ID为  31 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1744', '63', '删除功能', '192.168.1.104', '2016-12-04 13:06:47', '0', '删除功能ID为  30 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('1745', '63', '更新功能', '192.168.1.104', '2016-12-04 13:08:35', '0', '更新功能ID为  24 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1746', '63', '更新功能', '192.168.1.104', '2016-12-04 13:09:52', '0', '更新功能ID为  24 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1747', '63', '更新功能', '192.168.1.104', '2016-12-04 13:11:00', '0', '更新功能ID为  21 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1748', '63', '更新功能', '192.168.1.104', '2016-12-04 13:11:08', '0', '更新功能ID为  22 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1749', '63', '更新功能', '192.168.1.104', '2016-12-04 13:11:16', '0', '更新功能ID为  23 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1750', '63', '更新功能', '192.168.1.104', '2016-12-04 13:11:31', '0', '更新功能ID为  23 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1751', '63', '更新应用', '192.168.1.104', '2016-12-04 13:34:39', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1752', '63', '更新应用', '192.168.1.104', '2016-12-04 13:35:13', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1753', '63', '登录系统', '192.168.1.104', '2016-12-04 13:36:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1754', '63', '登录系统', '192.168.1.104', '2016-12-04 13:38:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1755', '63', '增加功能', '192.168.1.104', '2016-12-04 13:40:10', '0', '增加功能名称为  报表管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1756', '63', '更新功能', '192.168.1.104', '2016-12-04 13:40:38', '0', '更新功能ID为  58 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1757', '63', '更新功能', '192.168.1.104', '2016-12-04 13:40:54', '0', '更新功能ID为  59 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1758', '63', '更新应用', '192.168.1.104', '2016-12-04 13:42:15', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1759', '63', '更新功能', '192.168.1.104', '2016-12-04 13:43:23', '0', '更新功能ID为  58 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1760', '63', '更新应用', '192.168.1.104', '2016-12-04 13:43:44', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1761', '63', '更新应用', '192.168.1.104', '2016-12-04 13:47:17', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1762', '63', '登录系统', '192.168.112.102', '2016-12-04 21:00:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1763', '63', '更新应用', '192.168.112.102', '2016-12-04 21:01:40', '0', '更新应用ID为  7 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1764', '63', '更新应用', '192.168.112.102', '2016-12-04 21:02:40', '0', '更新应用ID为  7 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('1765', '63', '登录系统', '192.168.112.102', '2016-12-04 21:14:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1766', '63', '登录系统', '192.168.112.102', '2016-12-04 21:49:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1767', '63', '登录系统', '192.168.4.108', '2016-12-10 14:24:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1768', '63', '登录系统', '192.168.4.108', '2016-12-10 14:30:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1769', '63', '退出系统', '192.168.4.108', '2016-12-10 14:31:27', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('1770', '63', '登录系统', '192.168.4.108', '2016-12-10 14:31:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1771', '63', '增加经手人', '192.168.4.108', '2016-12-10 14:55:24', '0', '增加经手人名称为  赵五-财务 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1772', '63', '更新经手人', '192.168.4.108', '2016-12-10 14:55:33', '0', '更新经手人ID为  2 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1773', '63', '更新经手人', '192.168.4.108', '2016-12-10 14:55:45', '0', '更新经手人ID为  1 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1774', '63', '增加功能', '192.168.4.108', '2016-12-10 15:29:27', '0', '增加功能名称为  收入单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1775', '63', '更新UserBusiness', '192.168.4.108', '2016-12-10 15:30:47', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1776', '63', '更新功能', '192.168.4.108', '2016-12-10 15:35:04', '0', '更新功能ID为  58 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1777', '63', '更新功能', '192.168.4.108', '2016-12-10 15:35:59', '0', '更新功能ID为  58 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1778', '63', '更新功能', '192.168.4.108', '2016-12-10 15:37:40', '0', '更新功能ID为  196 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1779', '63', '更新功能', '192.168.4.108', '2016-12-10 15:39:07', '0', '更新功能ID为  196 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1780', '63', '更新功能', '192.168.4.108', '2016-12-10 15:39:23', '0', '更新功能ID为  59 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1781', '63', '删除功能', '192.168.4.108', '2016-12-10 15:39:45', '0', '删除功能ID为  58 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('1782', '63', '更新功能', '192.168.4.108', '2016-12-10 15:40:03', '0', '更新功能ID为  59 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1783', '63', '更新UserBusiness', '192.168.4.108', '2016-12-10 15:41:52', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1784', '63', '增加功能', '192.168.4.108', '2016-12-10 15:44:39', '0', '增加功能名称为  报表管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1785', '63', '更新功能', '192.168.4.108', '2016-12-10 15:44:51', '0', '更新功能ID为  59 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1786', '63', '更新功能', '192.168.4.108', '2016-12-10 15:46:30', '0', '更新功能ID为  198 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1787', '63', '增加经手人', '192.168.4.108', '2016-12-10 16:48:36', '0', '增加经手人名称为  赵六-财务 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1788', '63', '更新经手人', '192.168.4.108', '2016-12-10 16:48:43', '0', '更新经手人ID为  3 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1789', '63', '更新经手人', '192.168.4.108', '2016-12-10 16:48:49', '0', '更新经手人ID为  3 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1790', '63', '增加仓库', '192.168.4.108', '2016-12-10 16:59:41', '0', '增加仓库名称为  总部 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('1791', '63', '更新仓库', '192.168.4.108', '2016-12-10 16:59:52', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('1792', '63', '更新仓库', '192.168.4.108', '2016-12-10 17:00:03', '0', '更新仓库ID为  1 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('1793', '63', '更新经手人', '192.168.4.108', '2016-12-10 17:00:15', '0', '更新经手人ID为  3 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1794', '63', '更新经手人', '192.168.4.108', '2016-12-10 17:00:23', '0', '更新经手人ID为  4 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1795', '63', '登录系统', '192.168.4.108', '2016-12-10 17:10:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1796', '63', '退出系统', '192.168.4.108', '2016-12-10 17:39:05', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('1797', '63', '登录系统', '192.168.4.108', '2016-12-10 17:39:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1798', '63', '批量删除功能', '192.168.4.108', '2016-12-10 17:44:39', '0', '批量删除功能ID为  51,193 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1799', '63', '批量删除功能', '192.168.4.108', '2016-12-10 17:44:54', '0', '批量删除功能ID为  46,47,48,49,50 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1800', '63', '批量删除功能', '192.168.4.108', '2016-12-10 17:45:26', '0', '批量删除功能ID为  52,53,54,55 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1801', '63', '批量删除功能', '192.168.4.108', '2016-12-10 17:45:49', '0', '批量删除功能ID为  45 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1802', '63', '登录系统', '192.168.112.102', '2016-12-10 20:19:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1803', '63', '增加财务', '192.168.112.102', '2016-12-10 20:20:06', '0', '增加财务编号为  33 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1804', '63', '增加财务', '192.168.112.102', '2016-12-10 20:20:11', '0', '增加财务编号为  33 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1805', '63', '增加财务', '192.168.112.102', '2016-12-10 20:20:12', '0', '增加财务编号为  33 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1806', '63', '增加财务', '192.168.112.102', '2016-12-10 20:25:48', '0', '增加财务编号为  123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1807', '63', '登录系统', '192.168.112.102', '2016-12-10 20:50:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1808', '63', '增加财务', '192.168.112.102', '2016-12-10 20:51:41', '0', '增加财务编号为  3123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1809', '63', '增加财务', '192.168.112.102', '2016-12-10 20:53:58', '0', '增加财务编号为  3123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1810', '63', '增加财务', '192.168.112.102', '2016-12-10 21:08:57', '0', '增加财务编号为  123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1811', '63', '增加财务', '192.168.112.102', '2016-12-10 21:47:45', '0', '增加财务编号为  123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1812', '63', '保存财务明细', '192.168.112.102', '2016-12-10 21:47:46', '0', '保存财务明细对应主表编号为  8 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1813', '63', '登录系统', '192.168.112.102', '2016-12-10 22:49:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1814', '63', '删除财务', '192.168.112.102', '2016-12-10 22:52:43', '0', '删除财务ID为  5 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1815', '63', '批量删除财务', '192.168.112.102', '2016-12-10 22:52:49', '0', '批量删除财务ID为  4 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('1816', '63', '增加财务', '192.168.112.102', '2016-12-10 22:53:19', '0', '增加财务编号为  3134 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1817', '63', '保存财务明细', '192.168.112.102', '2016-12-10 22:53:20', '0', '保存财务明细对应主表编号为  9 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1818', '63', '删除财务', '192.168.112.102', '2016-12-10 22:53:35', '0', '删除财务ID为  7 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1819', '63', '增加财务', '192.168.112.102', '2016-12-10 22:54:05', '0', '增加财务编号为  N123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1820', '63', '保存财务明细', '192.168.112.102', '2016-12-10 22:54:06', '0', '保存财务明细对应主表编号为  10 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1821', '63', '更新财务', '192.168.112.102', '2016-12-10 22:54:28', '0', '更新财务ID为  10 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1822', '63', '更新财务', '192.168.112.102', '2016-12-10 23:06:54', '0', '更新财务ID为  10 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1823', '63', '增加财务', '192.168.112.102', '2016-12-10 23:07:51', '0', '增加财务编号为  124 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1824', '63', '保存财务明细', '192.168.112.102', '2016-12-10 23:07:51', '0', '保存财务明细对应主表编号为  11 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1825', '63', '登录系统', '192.168.112.102', '2016-12-10 23:09:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1826', '63', '更新财务', '192.168.112.102', '2016-12-10 23:09:55', '0', '更新财务ID为  10 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1827', '63', '更新收支项目', '192.168.112.102', '2016-12-10 23:10:18', '0', '更新收支项目ID为  7 成功！', '更新收支项目成功');
INSERT INTO `jsh_log` VALUES ('1828', '63', '更新收支项目', '192.168.112.102', '2016-12-10 23:10:32', '0', '更新收支项目ID为  5 成功！', '更新收支项目成功');
INSERT INTO `jsh_log` VALUES ('1829', '63', '更新收支项目', '192.168.112.102', '2016-12-10 23:10:47', '0', '更新收支项目ID为  1 成功！', '更新收支项目成功');
INSERT INTO `jsh_log` VALUES ('1830', '63', '增加收支项目', '192.168.112.102', '2016-12-10 23:11:05', '0', '增加收支项目名称为  水电费 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('1831', '63', '增加收支项目', '192.168.112.102', '2016-12-10 23:11:18', '0', '增加收支项目名称为  快递费 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('1832', '63', '增加收支项目', '192.168.112.102', '2016-12-10 23:11:30', '0', '增加收支项目名称为  交通报销费 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('1833', '63', '增加收支项目', '192.168.112.102', '2016-12-10 23:11:50', '0', '增加收支项目名称为  差旅费 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('1834', '63', '增加财务', '192.168.112.102', '2016-12-10 23:12:45', '0', '增加财务编号为  NO123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1835', '63', '保存财务明细', '192.168.112.102', '2016-12-10 23:12:46', '0', '保存财务明细对应主表编号为  12 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1836', '63', '更新财务', '192.168.112.102', '2016-12-10 23:27:12', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1837', '63', '更新财务', '192.168.112.102', '2016-12-10 23:27:46', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1838', '63', '登录系统', '192.168.112.102', '2016-12-10 23:29:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1839', '63', '更新财务', '192.168.112.102', '2016-12-10 23:30:01', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1840', '63', '更新财务', '192.168.112.102', '2016-12-10 23:30:43', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1841', '63', '更新财务', '192.168.112.102', '2016-12-10 23:31:17', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1842', '63', '更新财务', '192.168.112.102', '2016-12-10 23:38:58', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1843', '63', '更新财务', '192.168.112.102', '2016-12-10 23:42:13', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1844', '63', '登录系统', '192.168.112.102', '2016-12-10 23:45:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1845', '63', '更新财务', '192.168.112.102', '2016-12-10 23:45:34', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1846', '63', '增加财务', '192.168.112.102', '2016-12-10 23:46:22', '0', '增加财务编号为  12312 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1847', '63', '保存财务明细', '192.168.112.102', '2016-12-10 23:46:23', '0', '保存财务明细对应主表编号为  13 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1848', '63', '更新财务', '192.168.112.102', '2016-12-10 23:46:50', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1849', '63', '删除财务', '192.168.112.102', '2016-12-10 23:47:54', '1', '删除财务ID为  9 失败！', '删除财务失败');
INSERT INTO `jsh_log` VALUES ('1850', '63', '删除财务', '192.168.112.102', '2016-12-10 23:48:00', '1', '删除财务ID为  10 失败！', '删除财务失败');
INSERT INTO `jsh_log` VALUES ('1851', '63', '批量删除财务', '192.168.112.102', '2016-12-10 23:48:07', '1', '批量删除财务ID为  10 失败！', '批量删除财务失败');
INSERT INTO `jsh_log` VALUES ('1852', '63', '删除财务', '192.168.112.102', '2016-12-10 23:53:36', '0', '删除财务ID为  10 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1853', '63', '删除财务', '192.168.112.102', '2016-12-10 23:53:39', '0', '删除财务ID为  8 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1854', '63', '批量删除财务', '192.168.112.102', '2016-12-10 23:53:44', '0', '批量删除财务ID为  6 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('1855', '63', '登录系统', '192.168.4.107', '2016-12-11 09:50:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1856', '63', '删除财务', '192.168.4.107', '2016-12-11 09:51:09', '0', '删除财务ID为  11 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1857', '63', '增加财务', '192.168.4.107', '2016-12-11 09:51:42', '0', '增加财务编号为  N666 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1858', '63', '保存财务明细', '192.168.4.107', '2016-12-11 09:51:43', '0', '保存财务明细对应主表编号为  13 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1859', '63', '批量删除功能', '192.168.4.107', '2016-12-11 10:08:23', '0', '批量删除功能ID为  34,35,36,37,39 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1860', '63', '批量删除功能', '192.168.4.107', '2016-12-11 10:08:49', '0', '批量删除功能ID为  42,43 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('1861', '63', '更新功能', '192.168.4.107', '2016-12-11 10:15:14', '0', '更新功能ID为  40 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1862', '63', '更新功能', '192.168.4.107', '2016-12-11 10:15:32', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1863', '63', '更新功能', '192.168.4.107', '2016-12-11 10:17:58', '0', '更新功能ID为  3 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1864', '63', '更新功能', '192.168.4.107', '2016-12-11 10:18:32', '0', '更新功能ID为  3 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1865', '63', '删除功能', '192.168.4.107', '2016-12-11 10:18:56', '0', '删除功能ID为  10 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('1866', '63', '更新功能', '192.168.4.107', '2016-12-11 10:20:32', '0', '更新功能ID为  196 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1867', '63', '更新功能', '192.168.4.107', '2016-12-11 10:33:16', '0', '更新功能ID为  15 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1868', '63', '更新功能', '192.168.4.107', '2016-12-11 10:34:36', '0', '更新功能ID为  14 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1869', '63', '登录系统', '192.168.4.107', '2016-12-11 14:36:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1870', '63', '增加功能', '192.168.4.107', '2016-12-11 15:11:33', '0', '增加功能名称为  入库退货 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1871', '63', '增加功能', '192.168.4.107', '2016-12-11 15:12:44', '0', '增加功能名称为  销售退货 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1872', '63', '更新功能', '192.168.4.107', '2016-12-11 15:13:08', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1873', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:13:39', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1874', '63', '增加功能', '192.168.4.107', '2016-12-11 15:20:11', '0', '增加功能名称为  其他入库 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1875', '63', '增加功能', '192.168.4.107', '2016-12-11 15:21:11', '0', '增加功能名称为  其他出库 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1876', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:21:26', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1877', '63', '更新功能', '192.168.4.107', '2016-12-11 15:24:06', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1878', '63', '更新功能', '192.168.4.107', '2016-12-11 15:24:43', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1879', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:25:05', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1880', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:25:28', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1881', '63', '更新功能', '192.168.4.107', '2016-12-11 15:27:25', '0', '更新功能ID为  197 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1882', '63', '更新功能', '192.168.4.107', '2016-12-11 15:30:42', '0', '更新功能ID为  197 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1883', '63', '增加功能', '192.168.4.107', '2016-12-11 15:31:43', '0', '增加功能名称为  支出单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1884', '63', '更新功能', '192.168.4.107', '2016-12-11 15:31:56', '0', '更新功能ID为  203 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1885', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:32:12', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1886', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:32:23', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1887', '63', '更新功能', '192.168.4.107', '2016-12-11 15:39:07', '0', '更新功能ID为  197 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1888', '63', '更新功能', '192.168.4.107', '2016-12-11 15:39:14', '0', '更新功能ID为  203 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1889', '63', '增加功能', '192.168.4.107', '2016-12-11 15:40:32', '0', '增加功能名称为  收款单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1890', '63', '增加功能', '192.168.4.107', '2016-12-11 15:41:18', '0', '增加功能名称为  付款单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1891', '63', '增加功能', '192.168.4.107', '2016-12-11 15:42:11', '0', '增加功能名称为  转账单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('1892', '63', '更新功能', '192.168.4.107', '2016-12-11 15:42:26', '0', '更新功能ID为  204 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1893', '63', '更新UserBusiness', '192.168.4.107', '2016-12-11 15:42:37', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1894', '63', '更新功能', '192.168.4.107', '2016-12-11 15:43:50', '0', '更新功能ID为  206 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1895', '63', '登录系统', '192.168.112.100', '2016-12-11 18:02:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1896', '63', '增加仓管通', '192.168.112.100', '2016-12-11 18:15:39', '0', '增加仓管通编号为  234234 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('1897', '63', '保存仓管通明细', '192.168.112.100', '2016-12-11 18:15:40', '0', '保存仓管通明细对应主表编号为  33 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1898', '63', '更新功能', '192.168.112.100', '2016-12-11 18:23:48', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1899', '63', '更新功能', '192.168.112.100', '2016-12-11 18:23:58', '0', '更新功能ID为  202 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('1900', '63', '增加仓管通', '192.168.112.100', '2016-12-11 18:44:50', '0', '增加仓管通编号为  1234 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('1901', '63', '保存仓管通明细', '192.168.112.100', '2016-12-11 18:44:51', '0', '保存仓管通明细对应主表编号为  34 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1902', '63', '登录系统', '192.168.112.100', '2016-12-11 20:14:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1903', '63', '更新财务', '192.168.112.100', '2016-12-11 21:29:17', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1904', '63', '更新财务', '192.168.112.100', '2016-12-11 21:29:47', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1905', '63', '更新财务', '192.168.112.100', '2016-12-11 21:30:26', '0', '更新财务ID为  9 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1906', '63', '更新财务', '192.168.112.100', '2016-12-11 21:32:31', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1907', '63', '更新财务', '192.168.112.100', '2016-12-11 21:44:38', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1908', '63', '登录系统', '192.168.112.100', '2016-12-11 21:50:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1909', '63', '更新财务', '192.168.112.100', '2016-12-11 21:51:18', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1910', '63', '更新财务', '192.168.112.100', '2016-12-11 21:59:18', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1911', '63', '保存财务明细', '192.168.112.100', '2016-12-11 21:59:19', '0', '保存财务明细对应主表编号为  13 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1912', '63', '更新财务', '192.168.112.100', '2016-12-11 22:00:04', '0', '更新财务ID为  12 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1913', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:00:05', '0', '保存财务明细对应主表编号为  12 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1914', '63', '更新财务', '192.168.112.100', '2016-12-11 22:01:32', '0', '更新财务ID为  13 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1915', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:01:33', '0', '保存财务明细对应主表编号为  13 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1916', '63', '更新仓管通', '192.168.112.100', '2016-12-11 22:08:09', '0', '更新仓管通ID为  32 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1917', '63', '保存仓管通明细', '192.168.112.100', '2016-12-11 22:08:09', '0', '保存仓管通明细对应主表编号为  32 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1918', '63', '登录系统', '192.168.112.100', '2016-12-11 22:29:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1919', '63', '登录系统', '192.168.112.100', '2016-12-11 22:29:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1920', '63', '删除财务', '192.168.112.100', '2016-12-11 22:30:04', '0', '删除财务ID为  1 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('1921', '63', '批量删除财务', '192.168.112.100', '2016-12-11 22:30:09', '0', '批量删除财务ID为  2 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('1922', '63', '更新财务', '192.168.112.100', '2016-12-11 22:30:25', '0', '更新财务ID为  3 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1923', '63', '更新财务', '192.168.112.100', '2016-12-11 22:30:35', '0', '更新财务ID为  3 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1924', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:30:35', '0', '保存财务明细对应主表编号为  3 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1925', '63', '更新财务', '192.168.112.100', '2016-12-11 22:30:44', '0', '更新财务ID为  3 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1926', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:30:44', '0', '保存财务明细对应主表编号为  3 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1927', '63', '更新财务', '192.168.112.100', '2016-12-11 22:30:53', '0', '更新财务ID为  3 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1928', '63', '增加仓管通', '192.168.112.100', '2016-12-11 22:31:49', '0', '增加仓管通编号为  bbb 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('1929', '63', '保存仓管通明细', '192.168.112.100', '2016-12-11 22:31:50', '0', '保存仓管通明细对应主表编号为  35 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1930', '63', '更新仓管通', '192.168.112.100', '2016-12-11 22:32:16', '0', '更新仓管通ID为  35 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1931', '63', '增加仓管通', '192.168.112.100', '2016-12-11 22:32:52', '0', '增加仓管通编号为  bb22 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('1932', '63', '保存仓管通明细', '192.168.112.100', '2016-12-11 22:32:53', '0', '保存仓管通明细对应主表编号为  36 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1933', '63', '增加财务', '192.168.112.100', '2016-12-11 22:37:44', '0', '增加财务编号为  aa 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1934', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:37:45', '0', '保存财务明细对应主表编号为  14 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1935', '63', '增加财务', '192.168.112.100', '2016-12-11 22:38:12', '0', '增加财务编号为  34124 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1936', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:38:12', '0', '保存财务明细对应主表编号为  15 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1937', '63', '增加财务', '192.168.112.100', '2016-12-11 22:38:44', '0', '增加财务编号为  234234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1938', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:38:44', '0', '保存财务明细对应主表编号为  16 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1939', '63', '增加财务', '192.168.112.100', '2016-12-11 22:39:23', '0', '增加财务编号为  234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1940', '63', '保存财务明细', '192.168.112.100', '2016-12-11 22:39:23', '0', '保存财务明细对应主表编号为  17 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1941', '63', '登录系统', '192.168.1.103', '2016-12-18 11:47:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1942', '63', '增加财务', '192.168.1.103', '2016-12-18 12:57:39', '0', '增加财务编号为  123123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('1943', '63', '保存财务明细', '192.168.1.103', '2016-12-18 12:57:40', '0', '保存财务明细对应主表编号为  18 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1944', '63', '更新供应商', '192.168.1.103', '2016-12-18 13:02:15', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('1945', '63', '更新财务', '192.168.1.103', '2016-12-18 13:02:40', '0', '更新财务ID为  18 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1946', '63', '保存财务明细', '192.168.1.103', '2016-12-18 13:02:41', '0', '保存财务明细对应主表编号为  18 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1947', '63', '更新财务', '192.168.1.103', '2016-12-18 13:03:01', '0', '更新财务ID为  18 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1948', '63', '保存财务明细', '192.168.1.103', '2016-12-18 13:03:02', '0', '保存财务明细对应主表编号为  18 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1949', '63', '更新财务', '192.168.1.103', '2016-12-18 13:05:58', '0', '更新财务ID为  15 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('1950', '63', '保存财务明细', '192.168.1.103', '2016-12-18 13:05:58', '0', '保存财务明细对应主表编号为  15 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('1951', '63', '登录系统', '192.168.112.101', '2016-12-18 21:32:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1952', '63', '更新UserBusiness', '192.168.112.101', '2016-12-18 22:00:16', '0', '更新UserBusiness的ID为  18 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1953', '63', '登录系统', '127.0.0.1', '2016-12-20 12:30:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1954', '63', '登录系统', '127.0.0.1', '2016-12-20 12:45:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1955', '63', '登录系统', '192.168.112.102', '2016-12-23 22:08:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1956', '63', '增加仓管通', '192.168.112.102', '2016-12-23 23:12:33', '1', '增加仓管通编号为  wrwq 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('1957', '63', '登录系统', '192.168.112.102', '2016-12-24 08:45:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1958', '63', '登录系统', '192.168.112.102', '2016-12-24 12:12:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1959', '63', '增加仓库', '192.168.112.102', '2016-12-24 12:33:18', '0', '增加仓库名称为  苏州花边店 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('1960', '63', '更新UserBusiness', '192.168.112.102', '2016-12-24 12:44:03', '0', '更新UserBusiness的ID为  18 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1961', '63', '更新UserBusiness', '192.168.112.102', '2016-12-24 13:10:12', '0', '更新UserBusiness的ID为  18 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('1962', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:28:51', '1', '增加经手人名称为   失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('1963', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:36:27', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1964', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:40:00', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1965', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:40:06', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1966', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:42:26', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1967', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:45:11', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1968', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:46:29', '0', '增加经手人名称为   成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1969', '63', '批量删除经手人', '192.168.112.102', '2016-12-24 13:48:20', '0', '批量删除经手人ID为  5,6,7,8,9,10 成功！', '批量删除经手人成功');
INSERT INTO `jsh_log` VALUES ('1970', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:49:03', '0', '增加经手人名称为  655aaaa 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1971', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:50:08', '0', '增加经手人名称为  11111 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1972', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:50:11', '0', '删除经手人ID为  12 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('1973', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:50:15', '0', '删除经手人ID为  11 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('1974', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:50:31', '0', '增加经手人名称为  rrrrr 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1975', '63', '更新经手人', '192.168.112.102', '2016-12-24 13:50:36', '0', '更新经手人ID为  13 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1976', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:53:27', '0', '删除经手人ID为  13 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('1977', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:55:13', '1', '删除经手人ID为  2 失败！', '删除经手人失败');
INSERT INTO `jsh_log` VALUES ('1978', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:55:19', '1', '删除经手人ID为  2 失败！', '删除经手人失败');
INSERT INTO `jsh_log` VALUES ('1979', '63', '增加经手人', '192.168.112.102', '2016-12-24 13:55:25', '0', '增加经手人名称为  123123 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('1980', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:55:28', '0', '删除经手人ID为  14 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('1981', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:55:32', '1', '删除经手人ID为  2 失败！', '删除经手人失败');
INSERT INTO `jsh_log` VALUES ('1982', '63', '删除经手人', '192.168.112.102', '2016-12-24 13:56:05', '1', '删除经手人ID为  2 失败！', '删除经手人失败');
INSERT INTO `jsh_log` VALUES ('1983', '63', '更新经手人', '192.168.112.102', '2016-12-24 13:56:18', '0', '更新经手人ID为  2 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1984', '63', '更新经手人', '192.168.112.102', '2016-12-24 13:56:33', '0', '更新经手人ID为  2 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('1985', '63', '增加仓管通', '192.168.112.102', '2016-12-24 14:07:11', '1', '增加仓管通编号为  1111 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('1986', '63', '增加仓管通', '192.168.112.102', '2016-12-24 14:07:20', '1', '增加仓管通编号为  1111 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('1987', '63', '增加仓管通', '192.168.112.102', '2016-12-24 14:17:53', '1', '增加仓管通编号为  33 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('1988', '63', '增加仓管通', '192.168.112.102', '2016-12-24 14:18:15', '1', '增加仓管通编号为  33 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('1989', '63', '增加仓管通', '192.168.112.102', '2016-12-24 14:33:03', '0', '增加仓管通编号为  123 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('1990', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 14:33:03', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1991', '63', '更新仓管通', '192.168.112.102', '2016-12-24 14:35:40', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1992', '63', '更新仓管通', '192.168.112.102', '2016-12-24 14:37:08', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1993', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 14:37:08', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1994', '63', '更新仓管通', '192.168.112.102', '2016-12-24 14:37:32', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1995', '63', '更新仓管通', '192.168.112.102', '2016-12-24 14:51:08', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1996', '63', '更新仓管通', '192.168.112.102', '2016-12-24 14:51:47', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('1997', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 14:51:48', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('1998', '63', '登录系统', '192.168.112.102', '2016-12-24 15:32:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('1999', '63', '登录系统', '192.168.112.102', '2016-12-24 16:14:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2000', '63', '登录系统', '192.168.112.102', '2016-12-24 17:43:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2001', '63', '登录系统', '192.168.112.102', '2016-12-24 17:45:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2002', '63', '登录系统', '192.168.112.102', '2016-12-24 19:47:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2003', '63', '更新仓管通', '192.168.112.102', '2016-12-24 22:12:26', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2004', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 22:12:27', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2005', '63', '更新仓管通', '192.168.112.102', '2016-12-24 22:54:10', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2006', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 22:54:11', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2007', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:01:32', '0', '更新仓管通ID为  37 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2008', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:01:32', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2009', '63', '增加仓管通', '192.168.112.102', '2016-12-24 23:14:22', '0', '增加仓管通编号为  123132 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2010', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:29:31', '0', '更新仓管通ID为  38 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2011', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:33:25', '0', '更新仓管通ID为  38 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2012', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:37:28', '0', '更新仓管通ID为  38 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2013', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:37:29', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2014', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:41:41', '0', '更新仓管通ID为  38 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2015', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:41:41', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2016', '63', '增加仓管通', '192.168.112.102', '2016-12-24 23:43:50', '0', '增加仓管通编号为  222 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2017', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:43:51', '0', '保存仓管通明细对应主表编号为  39 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2018', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:44:16', '0', '更新仓管通ID为  39 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2019', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:44:17', '0', '保存仓管通明细对应主表编号为  39 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2020', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:46:56', '0', '更新仓管通ID为  39 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2021', '63', '保存仓管通明细', '192.168.112.102', '2016-12-24 23:46:57', '0', '保存仓管通明细对应主表编号为  39 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2022', '63', '更新仓管通', '192.168.112.102', '2016-12-24 23:50:37', '0', '更新仓管通ID为  39 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2023', '63', '登录系统', '192.168.112.102', '2016-12-25 19:58:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2024', '63', '登录系统', '192.168.112.102', '2016-12-25 20:28:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2025', '63', '更新结算账户', '192.168.112.102', '2016-12-25 21:06:07', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2026', '63', '更新结算账户', '192.168.112.102', '2016-12-25 21:06:15', '0', '更新结算账户ID为  4 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2027', '63', '登录系统', '192.168.1.102', '2017-01-02 12:49:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2028', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:17:47', '1', '增加仓管通编号为  12 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2029', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:18:35', '1', '增加仓管通编号为  12 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2030', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:19:41', '1', '增加仓管通编号为  12 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2031', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:21:06', '1', '增加仓管通编号为  12 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2032', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:23:30', '1', '增加仓管通编号为  222 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2033', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:28:34', '1', '增加仓管通编号为  222 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2034', '63', '增加仓管通', '192.168.1.102', '2017-01-02 13:32:18', '0', '增加仓管通编号为  222 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2035', '63', '保存仓管通明细', '192.168.1.102', '2017-01-02 13:32:18', '0', '保存仓管通明细对应主表编号为  40 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2036', '63', '登录系统', '127.0.0.1', '2017-01-02 15:52:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2037', '63', '增加仓管通', '127.0.0.1', '2017-01-02 15:53:30', '0', '增加仓管通编号为  555 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2038', '63', '保存仓管通明细', '127.0.0.1', '2017-01-02 15:53:30', '0', '保存仓管通明细对应主表编号为  41 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2039', '63', '删除仓管通', '127.0.0.1', '2017-01-02 15:55:22', '0', '删除仓管通ID为  40 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2040', '63', '增加仓管通', '127.0.0.1', '2017-01-02 15:58:29', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2041', '63', '保存仓管通明细', '127.0.0.1', '2017-01-02 15:58:29', '0', '保存仓管通明细对应主表编号为  42 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2042', '63', '增加仓管通', '127.0.0.1', '2017-01-02 15:59:30', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2043', '63', '增加仓管通', '127.0.0.1', '2017-01-02 15:59:33', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2044', '63', '登录系统', '192.168.112.102', '2017-01-02 20:29:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2045', '63', '增加仓管通', '192.168.112.102', '2017-01-02 20:30:33', '1', '增加仓管通编号为  1212 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2046', '63', '增加仓管通', '192.168.112.102', '2017-01-02 20:47:06', '0', '增加仓管通编号为  1212 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2047', '63', '更新结算账户', '', '2017-01-02 20:47:06', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2048', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 20:47:07', '0', '保存仓管通明细对应主表编号为  45 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2049', '63', '增加仓管通', '192.168.112.102', '2017-01-02 21:11:45', '0', '增加仓管通编号为  555 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2050', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 21:11:45', '0', '保存仓管通明细对应主表编号为  46 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2051', '63', '增加仓管通', '192.168.112.102', '2017-01-02 21:13:01', '0', '增加仓管通编号为  66 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2052', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 21:13:02', '0', '保存仓管通明细对应主表编号为  47 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2053', '63', '更新结算账户', '', '2017-01-02 21:13:02', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2054', '63', '增加仓管通', '192.168.112.102', '2017-01-02 21:27:19', '0', '增加仓管通编号为  344 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2055', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 21:27:20', '0', '保存仓管通明细对应主表编号为  48 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2056', '63', '增加仓管通', '192.168.112.102', '2017-01-02 21:33:19', '0', '增加仓管通编号为  333 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2057', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 21:33:20', '0', '保存仓管通明细对应主表编号为  49 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2058', '63', '更新结算账户', '', '2017-01-02 21:33:22', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2059', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:33:50', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2060', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:33:52', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2061', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:33:54', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2062', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:33:55', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2063', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:33:57', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2064', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:35:22', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2065', '63', '更新结算账户', '', '2017-01-02 21:35:24', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2066', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:35:38', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2067', '63', '更新结算账户', '', '2017-01-02 21:35:40', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2068', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:36:30', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2069', '63', '更新结算账户', '', '2017-01-02 21:36:31', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2070', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:37:44', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2071', '63', '更新结算账户', '', '2017-01-02 21:37:44', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2072', '63', '更新仓管通', '192.168.112.102', '2017-01-02 21:43:52', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2073', '63', '更新结算账户', '', '2017-01-02 21:43:52', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2074', '63', '登录系统', '192.168.112.102', '2017-01-02 21:57:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2075', '63', '增加仓管通', '192.168.112.102', '2017-01-02 21:58:30', '0', '增加仓管通编号为  4444 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2076', '63', '保存仓管通明细', '192.168.112.102', '2017-01-02 21:58:31', '0', '保存仓管通明细对应主表编号为  50 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2077', '63', '更新结算账户', '', '2017-01-02 21:58:31', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2078', '63', '登录系统', '192.168.8.100', '2017-01-05 12:56:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2079', '63', '登录系统', '192.168.4.108', '2017-01-07 13:24:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2080', '63', '登录系统', '192.168.4.108', '2017-01-07 13:42:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2081', '63', '登录系统', '192.168.4.108', '2017-01-07 14:48:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2082', '63', '登录系统', '192.168.4.108', '2017-01-07 15:00:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2083', '63', '登录系统', '192.168.4.108', '2017-01-07 15:03:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2084', '63', '登录系统', '192.168.4.108', '2017-01-07 15:26:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2085', '63', '登录系统', '192.168.4.108', '2017-01-07 15:34:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2086', '63', '登录系统', '192.168.4.108', '2017-01-07 15:53:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2087', '63', '登录系统', '192.168.4.108', '2017-01-07 16:07:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2088', '63', '登录系统', '192.168.4.108', '2017-01-07 16:31:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2089', '63', '登录系统', '192.168.4.108', '2017-01-07 18:00:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2090', '63', '登录系统', '192.168.4.108', '2017-01-07 18:11:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2091', '63', '增加仓管通', '192.168.4.108', '2017-01-07 18:25:34', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2092', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:25:34', '0', '保存仓管通明细对应主表编号为  51 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2093', '63', '更新结算账户', '', '2017-01-07 18:25:34', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2094', '63', '删除仓管通', '192.168.4.108', '2017-01-07 18:30:40', '0', '删除仓管通ID为  43 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2095', '63', '删除仓管通', '192.168.4.108', '2017-01-07 18:30:42', '0', '删除仓管通ID为  42 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2096', '63', '更新仓管通', '192.168.4.108', '2017-01-07 18:31:07', '0', '更新仓管通ID为  51 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2097', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:31:07', '0', '保存仓管通明细对应主表编号为  51 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2098', '63', '更新结算账户', '', '2017-01-07 18:31:07', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2099', '63', '增加仓管通', '192.168.4.108', '2017-01-07 18:32:28', '0', '增加仓管通编号为  aaaaaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2100', '63', '更新结算账户', '', '2017-01-07 18:32:29', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2101', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:32:29', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2102', '63', '更新仓管通', '192.168.4.108', '2017-01-07 18:33:01', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2103', '63', '更新结算账户', '', '2017-01-07 18:33:01', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2104', '63', '更新仓管通', '192.168.4.108', '2017-01-07 18:33:13', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2105', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:33:14', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2106', '63', '更新结算账户', '', '2017-01-07 18:33:14', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2107', '63', '删除仓管通', '192.168.4.108', '2017-01-07 18:33:49', '0', '删除仓管通ID为  52 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2108', '63', '增加仓管通', '192.168.4.108', '2017-01-07 18:34:16', '0', '增加仓管通编号为  aaaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2109', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:34:17', '0', '保存仓管通明细对应主表编号为  53 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2110', '63', '更新结算账户', '', '2017-01-07 18:34:17', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2111', '63', '更新仓管通', '192.168.4.108', '2017-01-07 18:34:29', '0', '更新仓管通ID为  53 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2112', '63', '保存仓管通明细', '192.168.4.108', '2017-01-07 18:34:29', '0', '保存仓管通明细对应主表编号为  53 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2113', '63', '更新结算账户', '', '2017-01-07 18:34:29', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2114', '63', '批量删除仓管通', '192.168.4.108', '2017-01-07 18:34:41', '0', '批量删除仓管通ID为  53 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2115', '63', '登录系统', '192.168.4.108', '2017-01-07 20:50:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2116', '63', '增加供应商', '192.168.4.108', '2017-01-07 21:01:21', '0', '增加供应商名称为  客户BBBB 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('2117', '63', '登录系统', '192.168.4.108', '2017-01-08 10:44:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2118', '63', '增加仓管通', '192.168.4.108', '2017-01-08 10:49:21', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2119', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 10:49:21', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2120', '63', '更新结算账户', '', '2017-01-08 10:49:21', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2121', '63', '增加仓管通', '192.168.4.108', '2017-01-08 10:50:58', '0', '增加仓管通编号为  dddd 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2122', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 10:50:59', '0', '保存仓管通明细对应主表编号为  53 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2123', '63', '更新结算账户', '', '2017-01-08 10:50:59', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2124', '63', '增加仓管通', '192.168.4.108', '2017-01-08 10:56:45', '0', '增加仓管通编号为  abcde 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2125', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 10:56:45', '0', '保存仓管通明细对应主表编号为  54 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2126', '63', '增加仓管通', '192.168.4.108', '2017-01-08 10:58:41', '0', '增加仓管通编号为  abbbb 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2127', '63', '更新结算账户', '', '2017-01-08 10:58:41', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2128', '63', '登录系统', '192.168.4.108', '2017-01-08 11:19:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2129', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:42:30', '0', '更新仓管通ID为  54 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2130', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:42:34', '0', '更新仓管通ID为  54 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2131', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:47:06', '0', '更新仓管通ID为  54 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2132', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:47:08', '0', '更新仓管通ID为  54 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2133', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:49:38', '0', '更新仓管通ID为  54 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2134', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:50:13', '0', '增加仓管通编号为  AAF 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2135', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:50:14', '0', '保存仓管通明细对应主表编号为  56 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2136', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:53:00', '0', '增加仓管通编号为  666aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2137', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:53:00', '0', '保存仓管通明细对应主表编号为  57 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2138', '63', '删除仓管通', '192.168.4.108', '2017-01-08 11:53:31', '0', '删除仓管通ID为  55 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2139', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:53:59', '0', '增加仓管通编号为  34234ww 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2140', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:53:59', '0', '保存仓管通明细对应主表编号为  58 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2141', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:55:00', '0', '增加仓管通编号为  22 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2142', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:55:00', '0', '保存仓管通明细对应主表编号为  59 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2143', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:55:53', '0', '增加仓管通编号为  we 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2144', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:55:54', '0', '保存仓管通明细对应主表编号为  60 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2145', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:56:10', '0', '更新仓管通ID为  60 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2146', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:56:11', '0', '保存仓管通明细对应主表编号为  60 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2147', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:56:58', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2148', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:56:59', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2149', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:57:10', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2150', '63', '更新仓管通', '192.168.4.108', '2017-01-08 11:57:39', '0', '更新仓管通ID为  31 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2151', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:57:39', '0', '保存仓管通明细对应主表编号为  31 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2152', '63', '增加仓管通', '192.168.4.108', '2017-01-08 11:58:58', '0', '增加仓管通编号为  rrrr 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2153', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 11:58:59', '0', '保存仓管通明细对应主表编号为  61 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2154', '63', '批量删除仓管通', '192.168.4.108', '2017-01-08 11:59:17', '0', '批量删除仓管通ID为  35 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2155', '63', '批量删除仓管通', '192.168.4.108', '2017-01-08 11:59:21', '0', '批量删除仓管通ID为  34 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2156', '63', '增加仓管通', '192.168.4.108', '2017-01-08 12:15:56', '0', '增加仓管通编号为  asas 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2157', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 12:16:05', '0', '保存仓管通明细对应主表编号为  62 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2158', '63', '删除仓管通', '192.168.4.108', '2017-01-08 12:20:42', '0', '删除仓管通ID为  36 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2159', '63', '更新仓管通', '192.168.4.108', '2017-01-08 12:20:58', '0', '更新仓管通ID为  62 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2160', '63', '删除仓管通', '192.168.4.108', '2017-01-08 12:21:57', '0', '删除仓管通ID为  62 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2161', '63', '增加仓管通', '192.168.4.108', '2017-01-08 12:22:19', '0', '增加仓管通编号为  aaaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2162', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 12:22:19', '0', '保存仓管通明细对应主表编号为  63 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2163', '63', '增加仓管通', '192.168.4.108', '2017-01-08 12:33:19', '0', '增加仓管通编号为  42342qqq 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2164', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 12:33:20', '0', '保存仓管通明细对应主表编号为  64 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2165', '63', '增加仓管通', '192.168.4.108', '2017-01-08 12:33:46', '0', '增加仓管通编号为  42 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2166', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 12:33:47', '0', '保存仓管通明细对应主表编号为  65 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2167', '63', '更新仓管通', '192.168.4.108', '2017-01-08 12:34:07', '0', '更新仓管通ID为  65 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2168', '63', '更新仓管通', '192.168.4.108', '2017-01-08 12:34:42', '0', '更新仓管通ID为  65 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2169', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 12:34:42', '0', '保存仓管通明细对应主表编号为  65 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2170', '63', '更新用户', '', '2017-01-08 12:46:53', '0', '更新用户ID为  63密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('2171', '63', '登录系统', '192.168.4.108', '2017-01-08 12:47:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2172', '63', '更新用户', '', '2017-01-08 12:47:22', '1', '更新用户ID为  63密码信息 失败！', '更新用户失败');
INSERT INTO `jsh_log` VALUES ('2173', '63', '更新用户', '', '2017-01-08 12:47:34', '0', '更新用户ID为  63密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('2174', '63', '登录系统', '192.168.4.108', '2017-01-08 12:47:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2175', '63', '登录系统', '192.168.4.108', '2017-01-08 12:47:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2176', '63', '登录系统', '192.168.4.108', '2017-01-08 16:28:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2177', '63', '更新仓管通', '192.168.4.108', '2017-01-08 16:30:24', '0', '更新仓管通ID为  64 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2178', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:30:25', '0', '保存仓管通明细对应主表编号为  64 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2179', '63', '增加仓管通', '192.168.4.108', '2017-01-08 16:45:12', '0', '增加仓管通编号为  ababab 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2180', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:45:12', '0', '保存仓管通明细对应主表编号为  66 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2181', '63', '登录系统', '192.168.4.108', '2017-01-08 16:46:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2182', '63', '删除仓管通', '192.168.4.108', '2017-01-08 16:46:16', '0', '删除仓管通ID为  66 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2183', '63', '增加仓管通', '192.168.4.108', '2017-01-08 16:46:53', '0', '增加仓管通编号为  abcdefg 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2184', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:46:54', '0', '保存仓管通明细对应主表编号为  67 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2185', '63', '更新仓管通', '192.168.4.108', '2017-01-08 16:55:47', '0', '更新仓管通ID为  67 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2186', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:55:47', '0', '保存仓管通明细对应主表编号为  67 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2187', '63', '更新仓管通', '192.168.4.108', '2017-01-08 16:59:12', '0', '更新仓管通ID为  32 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2188', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:59:12', '0', '保存仓管通明细对应主表编号为  32 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2189', '63', '更新仓管通', '192.168.4.108', '2017-01-08 16:59:24', '0', '更新仓管通ID为  30 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2190', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 16:59:24', '0', '保存仓管通明细对应主表编号为  30 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2191', '63', '删除仓管通', '192.168.4.108', '2017-01-08 16:59:28', '0', '删除仓管通ID为  29 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2192', '63', '更新仓管通', '192.168.4.108', '2017-01-08 16:59:41', '0', '更新仓管通ID为  41 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2193', '63', '删除仓管通', '192.168.4.108', '2017-01-08 16:59:57', '0', '删除仓管通ID为  44 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2194', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:03', '0', '更新仓管通ID为  45 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2195', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:08', '0', '更新仓管通ID为  46 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2196', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:11', '0', '更新仓管通ID为  47 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2197', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:14', '0', '更新仓管通ID为  48 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2198', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:17', '0', '更新仓管通ID为  49 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2199', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:20', '0', '更新仓管通ID为  50 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2200', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:22', '0', '更新仓管通ID为  51 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2201', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:25', '0', '更新仓管通ID为  59 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2202', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:27', '0', '更新仓管通ID为  67 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2203', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:33', '0', '更新仓管通ID为  39 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2204', '63', '删除仓管通', '192.168.4.108', '2017-01-08 17:00:36', '0', '删除仓管通ID为  37 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2205', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:39', '0', '更新仓管通ID为  38 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2206', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:00:43', '0', '更新仓管通ID为  39 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2207', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:02:22', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2208', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:02:25', '0', '更新仓管通ID为  31 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2209', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:03:44', '0', '更新仓管通ID为  60 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2210', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:03:48', '0', '更新仓管通ID为  53 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2211', '63', '登录系统', '192.168.4.108', '2017-01-08 17:43:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2212', '63', '更新仓管通', '192.168.4.108', '2017-01-08 17:53:51', '0', '更新仓管通ID为  52 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2213', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 17:53:51', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2214', '63', '登录系统', '192.168.4.108', '2017-01-08 18:34:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2215', '63', '更新结算账户', '192.168.4.108', '2017-01-08 18:57:21', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2216', '63', '更新结算账户', '192.168.4.108', '2017-01-08 18:57:43', '0', '更新结算账户ID为  4 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2217', '63', '增加仓管通', '192.168.4.108', '2017-01-08 18:58:35', '0', '增加仓管通编号为  asdfasdf 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2218', '63', '保存仓管通明细', '192.168.4.108', '2017-01-08 18:58:35', '0', '保存仓管通明细对应主表编号为  68 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2219', '63', '登录系统', '192.168.4.104', '2017-01-10 22:12:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2220', '63', '增加功能', '192.168.4.104', '2017-01-10 22:57:51', '0', '增加功能名称为  结算账户查询 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2221', '63', '更新UserBusiness', '192.168.4.104', '2017-01-10 22:58:13', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2222', '63', '更新UserBusiness', '192.168.4.104', '2017-01-10 22:58:23', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2223', '63', '登录系统', '192.168.4.104', '2017-01-10 23:01:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2224', '63', '增加功能', '192.168.4.104', '2017-01-10 23:12:46', '0', '增加功能名称为  进货统计 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2225', '63', '增加功能', '192.168.4.104', '2017-01-10 23:13:43', '0', '增加功能名称为  销售统计 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2226', '63', '更新功能', '192.168.4.104', '2017-01-10 23:13:59', '0', '更新功能ID为  208 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2227', '63', '更新UserBusiness', '192.168.4.104', '2017-01-10 23:14:56', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2228', '63', '更新UserBusiness', '192.168.4.104', '2017-01-10 23:15:05', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2229', '63', '更新功能', '192.168.4.104', '2017-01-10 23:16:37', '0', '更新功能ID为  59 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2230', '63', '更新功能', '192.168.4.104', '2017-01-10 23:16:44', '0', '更新功能ID为  207 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2231', '63', '登录系统', '192.168.4.104', '2017-01-11 23:34:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2232', '63', '登录系统', '192.168.112.100', '2017-01-11 23:41:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2233', '63', '登录系统', '192.168.112.100', '2017-01-11 23:51:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2234', '63', '登录系统', '192.168.4.104', '2017-01-12 00:16:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2235', '63', '登录系统', '192.168.4.104', '2017-01-12 00:16:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2236', '63', '登录系统', '192.168.4.104', '2017-01-12 00:17:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2237', '63', '登录系统', '192.168.4.104', '2017-01-12 00:21:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2238', '63', '登录系统', '192.168.4.104', '2017-01-12 00:24:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2239', '63', '登录系统', '192.168.4.104', '2017-01-12 00:26:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2240', '63', '登录系统', '192.168.4.104', '2017-01-12 00:27:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2241', '63', '登录系统', '192.168.4.104', '2017-01-12 00:32:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2242', '63', '登录系统', '192.168.8.108', '2017-01-12 12:39:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2243', '63', '登录系统', '192.168.8.108', '2017-01-12 12:42:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2244', '63', '登录系统', '192.168.8.108', '2017-01-12 18:20:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2245', '63', '登录系统', '172.16.128.41', '2017-01-14 01:55:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2246', '63', '增加财务', '172.16.128.41', '2017-01-14 02:18:56', '0', '增加财务编号为  1234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2247', '63', '保存财务明细', '172.16.128.41', '2017-01-14 02:18:56', '0', '保存财务明细对应主表编号为  19 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2248', '63', '增加财务', '172.16.128.41', '2017-01-14 03:44:01', '0', '增加财务编号为  21341 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2249', '63', '保存财务明细', '172.16.128.41', '2017-01-14 03:44:01', '0', '保存财务明细对应主表编号为  20 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2250', '63', '增加财务', '172.16.128.41', '2017-01-14 03:44:58', '0', '增加财务编号为  eqw 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2251', '63', '保存财务明细', '172.16.128.41', '2017-01-14 03:44:59', '0', '保存财务明细对应主表编号为  21 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2252', '63', '增加财务', '172.16.128.41', '2017-01-14 03:45:21', '0', '增加财务编号为  234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2253', '63', '保存财务明细', '172.16.128.41', '2017-01-14 03:45:21', '0', '保存财务明细对应主表编号为  22 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2254', '63', '增加财务', '172.16.128.41', '2017-01-14 03:45:46', '0', '增加财务编号为  3123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2255', '63', '保存财务明细', '172.16.128.41', '2017-01-14 03:45:47', '0', '保存财务明细对应主表编号为  23 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2256', '63', '增加财务', '172.16.128.41', '2017-01-14 03:52:40', '0', '增加财务编号为  sssaaa 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2257', '63', '保存财务明细', '172.16.128.41', '2017-01-14 03:52:41', '0', '保存财务明细对应主表编号为  24 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2258', '63', '登录系统', '192.168.1.104', '2017-01-15 11:38:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2259', '63', '登录系统', '192.168.1.104', '2017-01-15 12:46:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2260', '63', '增加财务', '192.168.1.104', '2017-01-15 13:44:36', '0', '增加财务编号为  3214 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2261', '63', '保存财务明细', '192.168.1.104', '2017-01-15 13:44:36', '0', '保存财务明细对应主表编号为  25 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2262', '63', '更新财务', '192.168.1.104', '2017-01-15 13:44:48', '0', '更新财务ID为  25 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2263', '63', '保存财务明细', '192.168.1.104', '2017-01-15 13:44:49', '0', '保存财务明细对应主表编号为  25 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2264', '63', '登录系统', '192.168.112.102', '2017-01-15 21:05:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2265', '63', '增加财务', '192.168.112.102', '2017-01-15 21:06:45', '0', '增加财务编号为  1234we 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2266', '63', '保存财务明细', '192.168.112.102', '2017-01-15 21:06:46', '0', '保存财务明细对应主表编号为  26 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2267', '63', '增加财务', '192.168.112.102', '2017-01-15 23:20:48', '0', '增加财务编号为  a1 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2268', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:20:49', '0', '保存财务明细对应主表编号为  27 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2269', '63', '增加财务', '192.168.112.102', '2017-01-15 23:21:21', '0', '增加财务编号为  a2 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2270', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:21:22', '0', '保存财务明细对应主表编号为  28 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2271', '63', '增加财务', '192.168.112.102', '2017-01-15 23:21:51', '0', '增加财务编号为  123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2272', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:21:51', '0', '保存财务明细对应主表编号为  29 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2273', '63', '更新财务', '192.168.112.102', '2017-01-15 23:22:15', '0', '更新财务ID为  29 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2274', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:22:16', '0', '保存财务明细对应主表编号为  29 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2275', '63', '更新财务', '192.168.112.102', '2017-01-15 23:22:54', '0', '更新财务ID为  29 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2276', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:22:55', '0', '保存财务明细对应主表编号为  29 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2277', '63', '更新财务', '192.168.112.102', '2017-01-15 23:23:02', '0', '更新财务ID为  29 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2278', '63', '增加财务', '192.168.112.102', '2017-01-15 23:23:28', '0', '增加财务编号为  341aaa 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2279', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:23:28', '0', '保存财务明细对应主表编号为  30 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2280', '63', '增加财务', '192.168.112.102', '2017-01-15 23:35:02', '0', '增加财务编号为  aass 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2281', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:35:03', '0', '保存财务明细对应主表编号为  31 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2282', '63', '增加财务', '192.168.112.102', '2017-01-15 23:36:00', '0', '增加财务编号为  hhhh 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2283', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:36:01', '0', '保存财务明细对应主表编号为  32 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2284', '63', '增加财务', '192.168.112.102', '2017-01-15 23:36:48', '0', '增加财务编号为  s1qw 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2285', '63', '保存财务明细', '192.168.112.102', '2017-01-15 23:36:49', '0', '保存财务明细对应主表编号为  33 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2286', '63', '更新财务', '192.168.112.102', '2017-01-15 23:40:56', '0', '更新财务ID为  32 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2287', '63', '更新财务', '192.168.112.102', '2017-01-15 23:47:06', '0', '更新财务ID为  33 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2288', '63', '更新财务', '192.168.112.102', '2017-01-15 23:47:28', '0', '更新财务ID为  32 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2289', '63', '更新财务', '192.168.112.102', '2017-01-15 23:47:58', '0', '更新财务ID为  30 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2290', '63', '更新财务', '192.168.112.102', '2017-01-15 23:48:37', '0', '更新财务ID为  31 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2291', '63', '登录系统', '192.168.4.103', '2017-01-17 22:29:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2292', '63', '登录系统', '192.168.4.103', '2017-01-17 23:03:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2293', '63', '登录系统', '192.168.4.103', '2017-01-17 23:05:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2294', '63', '登录系统', '192.168.4.103', '2017-01-17 23:07:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2295', '63', '登录系统', '192.168.4.103', '2017-01-17 23:12:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2296', '63', '登录系统', '192.168.4.103', '2017-01-17 23:15:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2297', '63', '登录系统', '192.168.4.103', '2017-01-17 23:37:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2298', '63', '登录系统', '192.168.4.103', '2017-01-17 23:40:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2299', '63', '登录系统', '192.168.4.103', '2017-01-17 23:42:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2300', '63', '登录系统', '192.168.4.103', '2017-01-17 23:43:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2301', '63', '登录系统', '192.168.4.103', '2017-01-17 23:43:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2302', '63', '登录系统', '192.168.4.103', '2017-01-17 23:44:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2303', '63', '登录系统', '192.168.4.103', '2017-01-17 23:45:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2304', '63', '登录系统', '192.168.4.103', '2017-01-17 23:46:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2305', '63', '登录系统', '192.168.4.103', '2017-01-17 23:46:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2306', '63', '登录系统', '192.168.112.100', '2017-01-18 22:30:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2307', '63', '登录系统', '192.168.112.100', '2017-01-18 22:32:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2308', '63', '登录系统', '192.168.112.100', '2017-01-18 22:39:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2309', '63', '登录系统', '192.168.4.103', '2017-01-18 23:57:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2310', '63', '登录系统', '192.168.4.103', '2017-01-19 00:00:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2311', '63', '登录系统', '192.168.4.103', '2017-01-19 00:03:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2312', '63', '登录系统', '192.168.4.103', '2017-01-19 00:08:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2313', '63', '登录系统', '192.168.4.103', '2017-01-19 00:15:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2314', '63', '登录系统', '192.168.4.103', '2017-01-19 00:17:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2315', '63', '登录系统', '192.168.4.103', '2017-01-19 00:17:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2316', '63', '登录系统', '192.168.4.103', '2017-01-19 00:18:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2317', '63', '登录系统', '192.168.4.103', '2017-01-19 00:19:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2318', '63', '登录系统', '192.168.4.103', '2017-01-19 00:20:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2319', '63', '退出系统', '192.168.4.103', '2017-01-19 00:21:26', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2320', '63', '登录系统', '192.168.4.103', '2017-01-19 00:21:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2321', '63', '退出系统', '192.168.4.103', '2017-01-19 00:21:55', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2322', '63', '登录系统', '192.168.4.103', '2017-01-19 00:22:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2323', '63', '登录系统', '192.168.4.103', '2017-01-19 00:23:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2324', '63', '登录系统', '192.168.4.103', '2017-01-19 00:27:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2325', '63', '登录系统', '192.168.4.103', '2017-01-19 00:30:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2326', '63', '登录系统', '192.168.4.103', '2017-01-19 22:02:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2327', '63', '登录系统', '192.168.4.103', '2017-01-19 22:12:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2328', '63', '登录系统', '192.168.4.103', '2017-01-19 22:17:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2329', '63', '登录系统', '192.168.4.103', '2017-01-19 22:18:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2330', '63', '登录系统', '192.168.4.103', '2017-01-19 22:19:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2331', '63', '登录系统', '192.168.4.103', '2017-01-19 22:21:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2332', '63', '登录系统', '192.168.4.103', '2017-01-19 22:24:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2333', '63', '登录系统', '192.168.4.103', '2017-01-19 22:27:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2334', '63', '登录系统', '192.168.4.103', '2017-01-19 22:29:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2335', '63', '登录系统', '192.168.4.103', '2017-01-19 22:32:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2336', '63', '登录系统', '192.168.4.103', '2017-01-19 22:32:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2337', '63', '登录系统', '192.168.4.103', '2017-01-19 22:32:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2338', '63', '登录系统', '192.168.4.103', '2017-01-19 22:33:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2339', '63', '登录系统', '192.168.4.103', '2017-01-19 22:35:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2340', '63', '登录系统', '192.168.4.103', '2017-01-19 22:39:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2341', '63', '登录系统', '192.168.4.103', '2017-01-19 22:40:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2342', '63', '登录系统', '192.168.4.103', '2017-01-19 22:42:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2343', '63', '登录系统', '192.168.4.103', '2017-01-19 22:43:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2344', '63', '登录系统', '192.168.4.103', '2017-01-19 22:43:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2345', '63', '登录系统', '192.168.4.103', '2017-01-19 22:44:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2346', '63', '登录系统', '192.168.4.103', '2017-01-19 22:44:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2347', '63', '登录系统', '192.168.4.103', '2017-01-19 22:45:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2348', '63', '登录系统', '192.168.4.103', '2017-01-19 22:47:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2349', '63', '登录系统', '192.168.4.103', '2017-01-19 22:49:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2350', '63', '登录系统', '192.168.4.103', '2017-01-19 23:07:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2351', '63', '登录系统', '192.168.4.103', '2017-01-19 23:16:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2352', '63', '更新财务', '192.168.4.103', '2017-01-19 23:17:43', '0', '更新财务ID为  33 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2353', '63', '更新财务', '192.168.4.103', '2017-01-19 23:17:51', '0', '更新财务ID为  33 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2354', '63', '更新供应商', '192.168.4.103', '2017-01-19 23:18:10', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2355', '63', '更新供应商', '192.168.4.103', '2017-01-19 23:18:21', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2356', '63', '登录系统', '192.168.4.103', '2017-01-19 23:44:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2357', '63', '登录系统', '192.168.4.103', '2017-01-19 23:49:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2358', '63', '登录系统', '192.168.4.103', '2017-01-19 23:55:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2359', '63', '登录系统', '192.168.4.103', '2017-01-20 00:11:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2360', '63', '登录系统', '192.168.4.103', '2017-01-20 00:15:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2361', '63', '登录系统', '192.168.4.103', '2017-01-20 00:15:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2362', '63', '登录系统', '192.168.4.103', '2017-01-20 00:19:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2363', '63', '登录系统', '192.168.4.103', '2017-01-21 00:30:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2364', '63', '登录系统', '192.168.4.103', '2017-01-21 00:30:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2365', '63', '登录系统', '127.0.0.1', '2017-01-21 00:34:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2366', '63', '登录系统', '127.0.0.1', '2017-01-21 00:42:15', '0', '管理用户：JSH 登录系统', 'JSH 登录系统');
INSERT INTO `jsh_log` VALUES ('2367', '63', '登录系统', '192.168.4.103', '2017-01-21 00:43:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2368', '63', '登录系统', '192.168.112.104', '2017-01-21 10:52:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2369', '63', '增加仓管通', '192.168.112.104', '2017-01-21 10:53:57', '0', '增加仓管通编号为  x123 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2370', '63', '保存仓管通明细', '192.168.112.104', '2017-01-21 10:53:58', '0', '保存仓管通明细对应主表编号为  69 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2371', '63', '更新仓管通', '192.168.112.104', '2017-01-21 10:54:53', '0', '更新仓管通ID为  69 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2372', '63', '保存仓管通明细', '192.168.112.104', '2017-01-21 10:54:54', '0', '保存仓管通明细对应主表编号为  69 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2373', '63', '登录系统', '192.168.4.103', '2017-01-21 13:39:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2374', '63', '登录系统', '192.168.4.103', '2017-01-21 15:23:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2375', '63', '登录系统', '192.168.4.103', '2017-01-21 19:02:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2376', '63', '登录系统', '192.168.112.100', '2017-01-21 22:05:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2377', '63', '增加财务', '192.168.112.100', '2017-01-21 22:39:33', '0', '增加财务编号为  123 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2378', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:39:34', '0', '保存财务明细对应主表编号为  34 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2379', '63', '登录系统', '192.168.112.100', '2017-01-21 22:41:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2380', '63', '增加财务', '192.168.112.100', '2017-01-21 22:42:06', '0', '增加财务编号为  aabb 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2381', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:42:07', '0', '保存财务明细对应主表编号为  35 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2382', '63', '更新财务', '192.168.112.100', '2017-01-21 22:42:54', '0', '更新财务ID为  35 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2383', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:42:54', '0', '保存财务明细对应主表编号为  35 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2384', '63', '更新财务', '192.168.112.100', '2017-01-21 22:44:36', '0', '更新财务ID为  35 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2385', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:44:37', '0', '保存财务明细对应主表编号为  35 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2386', '63', '增加财务', '192.168.112.100', '2017-01-21 22:52:46', '0', '增加财务编号为  bianhao1 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2387', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:52:47', '0', '保存财务明细对应主表编号为  36 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2388', '63', '更新财务', '192.168.112.100', '2017-01-21 22:53:42', '0', '更新财务ID为  36 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2389', '63', '更新财务', '192.168.112.100', '2017-01-21 22:54:58', '0', '更新财务ID为  36 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2390', '63', '保存财务明细', '192.168.112.100', '2017-01-21 22:54:59', '0', '保存财务明细对应主表编号为  36 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2391', '63', '删除财务', '192.168.112.100', '2017-01-21 22:55:52', '0', '删除财务ID为  20 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('2392', '63', '删除财务', '192.168.112.100', '2017-01-21 22:55:57', '0', '删除财务ID为  13 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('2393', '63', '增加财务', '192.168.112.100', '2017-01-21 23:01:09', '0', '增加财务编号为  fff 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2394', '63', '保存财务明细', '192.168.112.100', '2017-01-21 23:01:10', '0', '保存财务明细对应主表编号为  37 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2395', '63', '增加财务', '192.168.112.100', '2017-01-21 23:01:40', '0', '增加财务编号为  234eq 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2396', '63', '保存财务明细', '192.168.112.100', '2017-01-21 23:01:41', '0', '保存财务明细对应主表编号为  38 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2397', '63', '增加财务', '192.168.112.100', '2017-01-21 23:04:17', '0', '增加财务编号为  aasss 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2398', '63', '保存财务明细', '192.168.112.100', '2017-01-21 23:04:17', '0', '保存财务明细对应主表编号为  39 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2399', '63', '登录系统', '192.168.112.102', '2017-01-22 22:07:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2400', '63', '增加结算账户', '192.168.112.102', '2017-01-22 22:08:15', '0', '增加结算账户名称为  1231 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('2401', '63', '增加结算账户', '192.168.112.102', '2017-01-22 22:16:52', '0', '增加结算账户名称为  模型111 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('2402', '63', '登录系统', '192.168.112.102', '2017-01-22 22:33:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2403', '63', '增加经手人', '192.168.112.102', '2017-01-22 22:33:20', '1', '增加经手人名称为  qaaa 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('2404', '63', '增加商品类别', '192.168.112.102', '2017-01-22 22:35:14', '0', '增加商品类别名称为  aa 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('2405', '63', '删除商品类别', '192.168.112.102', '2017-01-22 22:35:19', '0', '删除商品类别ID为  3 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('2406', '63', '增加经手人', '192.168.112.102', '2017-01-22 22:35:51', '1', '增加经手人名称为  ddd 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('2407', '63', '登录系统', '192.168.112.102', '2017-01-22 23:08:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2408', '63', '增加经手人', '192.168.112.102', '2017-01-22 23:09:05', '1', '增加经手人名称为  aaa 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('2409', '63', '增加仓管通', '192.168.112.102', '2017-01-22 23:10:08', '0', '增加仓管通编号为  aa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2410', '63', '保存仓管通明细', '192.168.112.102', '2017-01-22 23:10:08', '0', '保存仓管通明细对应主表编号为  70 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2411', '63', '更新商品类别', '192.168.112.102', '2017-01-22 23:10:35', '0', '更新商品类别ID为  2 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('2412', '63', '更新经手人', '192.168.112.102', '2017-01-22 23:10:50', '0', '更新经手人ID为  1 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('2413', '63', '删除结算账户', '192.168.112.102', '2017-01-22 23:10:59', '0', '删除结算账户ID为  11,名称为  模型111成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('2414', '63', '删除结算账户', '192.168.112.102', '2017-01-22 23:11:02', '0', '删除结算账户ID为  10,名称为  1231成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('2415', '63', '增加结算账户', '192.168.112.102', '2017-01-22 23:11:13', '0', '增加结算账户名称为  aaaa 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('2416', '63', '删除结算账户', '192.168.112.102', '2017-01-22 23:11:17', '0', '删除结算账户ID为  14,名称为  aaaa成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('2417', '63', '增加经手人', '192.168.112.102', '2017-01-22 23:11:30', '1', '增加经手人名称为  ddd 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('2418', '63', '增加仓库', '192.168.112.102', '2017-01-22 23:12:24', '0', '增加仓库名称为  aa 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('2419', '63', '删除仓库', '192.168.112.102', '2017-01-22 23:12:29', '0', '删除仓库ID为  4 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('2420', '63', '登录系统', '192.168.112.102', '2017-01-22 23:21:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2421', '63', '增加结算账户', '192.168.112.102', '2017-01-22 23:26:48', '0', '增加结算账户名称为  43345q 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('2422', '63', '更新结算账户', '192.168.112.102', '2017-01-22 23:26:57', '0', '更新结算账户ID为  15 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2423', '63', '删除结算账户', '192.168.112.102', '2017-01-22 23:27:03', '0', '删除结算账户ID为  15,名称为  43345q成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('2424', '63', '登录系统', '192.168.112.102', '2017-01-22 23:38:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2425', '63', '增加结算账户', '192.168.112.102', '2017-01-22 23:38:23', '0', '增加结算账户名称为  3253 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('2426', '63', '删除结算账户', '192.168.112.102', '2017-01-22 23:38:28', '0', '删除结算账户ID为  16,名称为  3253成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('2427', '63', '登录系统', '192.168.112.102', '2017-01-22 23:50:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2428', '63', '登录系统', '192.168.112.102', '2017-01-23 00:32:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2429', '63', '登录系统', '192.168.112.102', '2017-01-23 00:40:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2430', '63', '登录系统', '192.168.112.102', '2017-01-23 00:47:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2431', '63', '登录系统', '192.168.8.102', '2017-02-13 15:57:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2432', '63', '登录系统', '192.168.112.102', '2017-02-13 22:47:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2433', '63', '退出系统', '192.168.112.102', '2017-02-13 22:57:22', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2434', '63', '登录系统', '192.168.4.107', '2017-02-14 23:42:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2435', '63', '登录系统', '192.168.8.102', '2017-02-15 11:09:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2436', '63', '登录系统', '192.168.8.102', '2017-02-15 11:28:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2437', '63', '更新仓管通', '192.168.8.102', '2017-02-15 11:28:27', '0', '更新仓管通ID为  70 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2438', '63', '删除仓管通', '192.168.8.102', '2017-02-15 11:28:35', '0', '删除仓管通ID为  70 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2439', '63', '登录系统', '192.168.8.102', '2017-02-15 11:31:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2440', '63', '登录系统', '192.168.8.102', '2017-02-15 11:42:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2441', '63', '登录系统', '192.168.8.102', '2017-02-15 11:53:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2442', '63', '登录系统', '192.168.8.102', '2017-02-15 12:35:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2443', '63', '登录系统', '192.168.8.102', '2017-02-15 12:39:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2444', '63', '登录系统', '192.168.4.108', '2017-02-15 22:09:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2445', '63', '登录系统', '192.168.4.108', '2017-02-15 22:16:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2446', '63', '登录系统', '192.168.4.108', '2017-02-15 23:23:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2447', '63', '登录系统', '192.168.4.108', '2017-02-15 23:27:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2448', '63', '登录系统', '192.168.8.102', '2017-02-17 16:14:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2449', '63', '登录系统', '192.168.8.100', '2017-02-21 18:12:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2450', '63', '登录系统', '192.168.112.102', '2017-02-21 21:24:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2451', '63', '登录系统', '192.168.112.100', '2017-02-22 20:48:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2452', '63', '登录系统', '192.168.112.101', '2017-02-26 19:01:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2453', '63', '登录系统', '192.168.4.106', '2017-02-26 20:10:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2454', '63', '登录系统', '192.168.4.106', '2017-02-26 20:13:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2455', '63', '登录系统', '192.168.112.101', '2017-02-26 20:44:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2456', '63', '登录系统', '192.168.112.101', '2017-02-26 20:53:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2457', '63', '登录系统', '192.168.112.101', '2017-02-26 21:00:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2458', '63', '登录系统', '192.168.112.101', '2017-02-26 21:04:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2459', '63', '登录系统', '192.168.112.101', '2017-02-26 21:05:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2460', '63', '登录系统', '192.168.112.101', '2017-03-05 19:26:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2461', '63', '登录系统', '192.168.112.101', '2017-03-05 20:31:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2462', '63', '登录系统', '192.168.112.101', '2017-03-05 20:49:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2463', '63', '登录系统', '192.168.112.101', '2017-03-05 20:53:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2464', '63', '登录系统', '192.168.112.101', '2017-03-05 21:02:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2465', '63', '登录系统', '192.168.112.101', '2017-03-05 21:05:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2466', '63', '登录系统', '192.168.112.101', '2017-03-05 21:07:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2467', '63', '登录系统', '192.168.112.101', '2017-03-05 21:20:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2468', '63', '登录系统', '192.168.112.101', '2017-03-05 21:27:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2469', '63', '登录系统', '192.168.112.101', '2017-03-05 21:41:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2470', '63', '登录系统', '192.168.112.101', '2017-03-05 21:50:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2471', '63', '登录系统', '192.168.112.101', '2017-03-05 22:04:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2472', '63', '登录系统', '192.168.112.101', '2017-03-05 22:09:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2473', '63', '登录系统', '192.168.112.101', '2017-03-05 22:09:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2474', '63', '登录系统', '192.168.112.101', '2017-03-05 22:11:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2475', '63', '登录系统', '192.168.112.102', '2017-03-12 20:12:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2476', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:16:33', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2477', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:29:58', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2478', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:31:09', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2479', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:31:45', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2480', '63', '登录系统', '192.168.112.102', '2017-03-12 20:32:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2481', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:33:05', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2482', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:33:21', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2483', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:33:28', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2484', '63', '更新供应商', '192.168.112.102', '2017-03-12 20:40:39', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2485', '63', '登录系统', '192.168.112.102', '2017-03-12 22:13:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2486', '63', '登录系统', '192.168.112.102', '2017-03-12 22:19:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2487', '63', '登录系统', '192.168.112.102', '2017-03-12 22:22:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2488', '63', '登录系统', '192.168.112.102', '2017-03-12 22:24:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2489', '63', '登录系统', '192.168.112.102', '2017-03-19 20:56:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2490', '63', '登录系统', '192.168.112.102', '2017-03-19 21:08:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2491', '63', '登录系统', '192.168.112.102', '2017-03-19 21:15:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2492', '63', '登录系统', '192.168.112.102', '2017-03-19 21:18:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2493', '63', '登录系统', '192.168.112.102', '2017-03-19 21:23:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2494', '63', '删除仓管通', '192.168.112.102', '2017-03-19 21:27:22', '0', '删除仓管通ID为  45 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2495', '63', '删除仓管通', '192.168.112.102', '2017-03-19 21:27:40', '0', '删除仓管通ID为  30 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2496', '63', '登录系统', '192.168.112.102', '2017-03-19 21:36:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2497', '63', '登录系统', '192.168.1.104', '2017-04-04 09:51:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2498', '63', '登录系统', '192.168.1.104', '2017-04-04 10:43:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2499', '63', '登录系统', '192.168.1.104', '2017-04-04 10:49:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2500', '63', '登录系统', '192.168.1.104', '2017-04-04 10:53:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2501', '63', '登录系统', '192.168.1.104', '2017-04-04 10:54:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2502', '63', '登录系统', '192.168.1.104', '2017-04-04 10:56:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2503', '63', '登录系统', '192.168.1.104', '2017-04-04 11:02:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2504', '63', '登录系统', '192.168.1.104', '2017-04-04 11:15:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2505', '63', '登录系统', '192.168.1.104', '2017-04-04 11:20:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2506', '63', '登录系统', '192.168.1.104', '2017-04-04 11:23:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2507', '63', '登录系统', '192.168.1.104', '2017-04-04 11:28:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2508', '63', '登录系统', '192.168.112.102', '2017-04-08 10:06:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2509', '63', '登录系统', '192.168.112.102', '2017-04-08 10:38:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2510', '63', '登录系统', '192.168.112.102', '2017-04-08 10:51:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2511', '63', '登录系统', '192.168.112.102', '2017-04-08 11:10:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2512', '63', '登录系统', '192.168.112.102', '2017-04-08 11:33:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2513', '63', '登录系统', '192.168.112.102', '2017-04-08 11:37:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2514', '63', '登录系统', '192.168.112.102', '2017-04-08 12:21:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2515', '63', '登录系统', '192.168.112.102', '2017-04-08 12:27:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2516', '63', '登录系统', '192.168.112.102', '2017-04-08 12:30:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2517', '63', '登录系统', '192.168.112.102', '2017-04-08 12:33:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2518', '63', '登录系统', '192.168.112.102', '2017-04-08 12:47:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2519', '63', '登录系统', '192.168.112.102', '2017-04-08 13:03:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2520', '63', '登录系统', '192.168.112.102', '2017-04-08 13:13:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2521', '63', '登录系统', '192.168.112.102', '2017-04-08 13:19:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2522', '63', '登录系统', '192.168.112.102', '2017-04-08 13:26:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2523', '63', '增加仓管通', '192.168.112.102', '2017-04-08 13:31:17', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2524', '63', '保存仓管通明细', '192.168.112.102', '2017-04-08 13:31:18', '0', '保存仓管通明细对应主表编号为  70 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2525', '63', '更新仓管通', '192.168.112.102', '2017-04-08 13:31:59', '0', '更新仓管通ID为  70 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2526', '63', '登录系统', '192.168.112.102', '2017-04-08 13:36:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2527', '63', '登录系统', '192.168.112.102', '2017-04-08 21:09:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2528', '63', '登录系统', '192.168.112.102', '2017-04-08 21:21:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2529', '63', '登录系统', '192.168.4.105', '2017-04-09 16:08:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2530', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:08:55', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2531', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:09:13', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2532', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:11:32', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2533', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:16:13', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2534', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:16:52', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2535', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:17:05', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2536', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:17:26', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2537', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:17:48', '0', '更新供应商ID为  1 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2538', '63', '增加供应商', '192.168.4.105', '2017-04-09 16:18:31', '0', '增加供应商名称为  南通宝贝家纺 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('2539', '63', '增加仓管通', '192.168.4.105', '2017-04-09 16:19:22', '0', '增加仓管通编号为  aaa123 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2540', '63', '保存仓管通明细', '192.168.4.105', '2017-04-09 16:19:22', '0', '保存仓管通明细对应主表编号为  71 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2541', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:19:56', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2542', '63', '更新仓管通', '192.168.4.105', '2017-04-09 16:20:18', '0', '更新仓管通ID为  71 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2543', '63', '登录系统', '192.168.4.105', '2017-04-09 16:32:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2544', '63', '增加财务', '192.168.4.105', '2017-04-09 16:33:32', '0', '增加财务编号为  aaxx11 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2545', '63', '保存财务明细', '192.168.4.105', '2017-04-09 16:33:33', '0', '保存财务明细对应主表编号为  40 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2546', '63', '更新财务', '192.168.4.105', '2017-04-09 16:34:52', '0', '更新财务ID为  40 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2547', '63', '登录系统', '192.168.4.105', '2017-04-09 16:48:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2548', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:48:53', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2549', '63', '更新供应商', '192.168.4.105', '2017-04-09 16:49:13', '0', '更新供应商ID为  1 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2550', '63', '登录系统', '192.168.100.163', '2017-06-01 00:28:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2551', '63', '登录系统', '192.168.100.163', '2017-06-01 21:42:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2552', '63', '登录系统', '192.168.100.163', '2017-06-01 21:50:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2553', '63', '登录系统', '192.168.100.163', '2017-06-01 21:50:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2554', '63', '增加仓管通', '192.168.100.163', '2017-06-01 22:51:42', '0', '增加仓管通编号为  abcd1234 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2555', '63', '保存仓管通明细', '192.168.100.163', '2017-06-01 22:51:42', '0', '保存仓管通明细对应主表编号为  72 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2556', '63', '删除仓管通', '192.168.100.163', '2017-06-01 22:52:30', '0', '删除仓管通ID为  72 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2557', '63', '登录系统', '192.168.100.163', '2017-06-01 23:14:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2558', '63', '登录系统', '192.168.100.163', '2017-06-02 22:46:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2559', '63', '登录系统', '192.168.100.163', '2017-06-03 20:35:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2560', '63', '登录系统', '192.168.100.163', '2017-06-03 22:17:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2561', '63', '增加商品', '192.168.100.163', '2017-06-03 22:24:55', '0', '增加商品名称为  棉线 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('2562', '63', '增加仓管通', '192.168.100.163', '2017-06-03 22:26:00', '0', '增加仓管通编号为  22aa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2563', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 22:26:00', '0', '保存仓管通明细对应主表编号为  72 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2564', '63', '增加仓管通', '192.168.100.163', '2017-06-03 22:29:35', '0', '增加仓管通编号为  afds123 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2565', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 22:29:35', '0', '保存仓管通明细对应主表编号为  73 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2566', '63', '更新仓管通', '192.168.100.163', '2017-06-03 22:30:46', '0', '更新仓管通ID为  73 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2567', '63', '更新仓管通', '192.168.100.163', '2017-06-03 22:31:09', '0', '更新仓管通ID为  73 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2568', '63', '更新仓管通', '192.168.100.163', '2017-06-03 22:31:19', '0', '更新仓管通ID为  73 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2569', '63', '增加仓管通', '192.168.100.163', '2017-06-03 22:47:31', '0', '增加仓管通编号为  dsfs 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2570', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 22:47:31', '0', '保存仓管通明细对应主表编号为  74 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2571', '63', '更新仓管通', '192.168.100.163', '2017-06-03 22:47:59', '0', '更新仓管通ID为  74 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2572', '63', '增加财务', '192.168.100.163', '2017-06-03 22:53:38', '0', '增加财务编号为  aa 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2573', '63', '保存财务明细', '192.168.100.163', '2017-06-03 22:53:38', '0', '保存财务明细对应主表编号为  41 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2574', '63', '删除财务', '192.168.100.163', '2017-06-03 22:54:04', '0', '删除财务ID为  31 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('2575', '63', '删除财务', '192.168.100.163', '2017-06-03 22:54:05', '0', '删除财务ID为  24 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('2576', '63', '删除财务', '192.168.100.163', '2017-06-03 22:54:07', '0', '删除财务ID为  17 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('2577', '63', '增加仓管通', '192.168.100.163', '2017-06-03 22:59:46', '0', '增加仓管通编号为  aaa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2578', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 22:59:46', '0', '保存仓管通明细对应主表编号为  75 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2579', '63', '删除仓管通', '192.168.100.163', '2017-06-03 23:00:05', '0', '删除仓管通ID为  75 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2580', '63', '增加仓管通', '192.168.100.163', '2017-06-03 23:00:53', '0', '增加仓管通编号为  asdf 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2581', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 23:00:53', '0', '保存仓管通明细对应主表编号为  76 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2582', '63', '更新仓管通', '192.168.100.163', '2017-06-03 23:01:06', '0', '更新仓管通ID为  76 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2583', '63', '保存仓管通明细', '192.168.100.163', '2017-06-03 23:01:06', '0', '保存仓管通明细对应主表编号为  76 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2584', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:01:21', '0', '更新供应商ID为  1 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2585', '63', '更新仓库', '192.168.100.163', '2017-06-03 23:02:15', '0', '更新仓库ID为  1 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('2586', '63', '更新仓库', '192.168.100.163', '2017-06-03 23:02:21', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('2587', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:03:15', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2588', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:03:33', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2589', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:03:50', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2590', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:04:11', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2591', '63', '更新供应商', '192.168.100.163', '2017-06-03 23:04:16', '0', '更新供应商ID为  1 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2592', '63', '更新结算账户', '192.168.100.163', '2017-06-03 23:04:42', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2593', '63', '更新结算账户', '192.168.100.163', '2017-06-03 23:04:49', '0', '更新结算账户ID为  4 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2594', '63', '更新结算账户', '192.168.100.163', '2017-06-03 23:05:01', '0', '更新结算账户ID为  9 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2595', '63', '更新结算账户', '192.168.100.163', '2017-06-03 23:05:05', '0', '更新结算账户ID为  4 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2596', '63', '更新结算账户', '192.168.100.163', '2017-06-03 23:05:21', '0', '更新结算账户ID为  4 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('2597', '63', '增加商品类别', '192.168.100.163', '2017-06-03 23:07:34', '0', '增加商品类别名称为  其他 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('2598', '63', '增加商品类别', '192.168.100.163', '2017-06-03 23:08:09', '0', '增加商品类别名称为  其他 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('2599', '63', '增加商品类别', '192.168.100.163', '2017-06-03 23:08:33', '0', '增加商品类别名称为  其他 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('2600', '63', '更新商品', '192.168.100.163', '2017-06-03 23:10:50', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2601', '63', '更新商品', '192.168.100.163', '2017-06-03 23:10:56', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2602', '63', '更新商品', '192.168.100.163', '2017-06-03 23:11:07', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2603', '63', '更新商品', '192.168.100.163', '2017-06-03 23:12:08', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2604', '63', '更新商品', '192.168.100.163', '2017-06-03 23:12:13', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2605', '63', '更新商品', '192.168.100.163', '2017-06-03 23:12:36', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2606', '63', '更新用户', '', '2017-06-03 23:29:02', '0', '更新用户ID为  63密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('2607', '63', '登录系统', '192.168.100.163', '2017-06-03 23:29:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2608', '63', '退出系统', '192.168.100.163', '2017-06-03 23:29:12', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2609', '63', '登录系统', '192.168.100.163', '2017-06-03 23:29:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2610', '63', '更新用户', '', '2017-06-03 23:29:32', '0', '更新用户ID为  63密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('2611', '63', '登录系统', '192.168.100.163', '2017-06-03 23:29:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2612', '63', '退出系统', '192.168.100.163', '2017-06-03 23:29:37', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2613', '63', '登录系统', '192.168.100.163', '2017-06-03 23:29:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2614', '63', '更新UserBusiness', '192.168.100.163', '2017-06-03 23:34:30', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2615', '63', '更新UserBusiness', '192.168.100.163', '2017-06-03 23:34:45', '0', '更新UserBusiness的ID为  2 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2616', '63', '更新UserBusiness', '192.168.100.163', '2017-06-03 23:35:01', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2617', '63', '退出系统', '192.168.100.163', '2017-06-03 23:35:31', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2618', '64', '登录系统', '192.168.100.163', '2017-06-03 23:35:37', '0', '管理用户：zs 登录系统', 'zs 登录系统');
INSERT INTO `jsh_log` VALUES ('2619', '64', '退出系统', '192.168.100.163', '2017-06-03 23:36:13', '0', '管理用户：zs 退出系统', 'zs 退出系统');
INSERT INTO `jsh_log` VALUES ('2620', '63', '登录系统', '192.168.100.163', '2017-06-03 23:36:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2621', '63', '更新UserBusiness', '192.168.100.163', '2017-06-03 23:36:40', '0', '更新UserBusiness的ID为  6 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2622', '63', '退出系统', '192.168.100.163', '2017-06-03 23:36:46', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2623', '64', '登录系统', '192.168.100.163', '2017-06-03 23:36:53', '0', '管理用户：zs 登录系统', 'zs 登录系统');
INSERT INTO `jsh_log` VALUES ('2624', '64', '退出系统', '192.168.100.163', '2017-06-03 23:37:18', '0', '管理用户：zs 退出系统', 'zs 退出系统');
INSERT INTO `jsh_log` VALUES ('2625', '63', '登录系统', '192.168.100.163', '2017-06-03 23:37:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2626', '63', '登录系统', '192.168.100.163', '2017-06-04 12:48:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2627', '63', '登录系统', '192.168.100.163', '2017-06-04 13:02:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2628', '63', '登录系统', '192.168.100.163', '2017-06-04 16:51:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2629', '63', '登录系统', '192.168.100.163', '2017-06-04 18:05:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2630', '63', '登录系统', '192.168.100.163', '2017-06-04 18:52:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2631', '63', '登录系统', '192.168.100.163', '2017-06-04 18:54:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2632', '63', '登录系统', '192.168.100.163', '2017-06-04 18:55:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2633', '63', '退出系统', '192.168.100.163', '2017-06-04 18:56:00', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2634', '63', '登录系统', '192.168.100.163', '2017-06-04 18:56:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2635', '63', '退出系统', '192.168.100.163', '2017-06-04 18:56:04', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2636', '63', '登录系统', '192.168.100.163', '2017-06-04 18:56:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2637', '63', '登录系统', '192.168.100.163', '2017-06-04 18:58:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2638', '63', '登录系统', '192.168.100.163', '2017-06-04 19:01:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2639', '63', '登录系统', '192.168.100.163', '2017-06-04 19:18:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2640', '63', '登录系统', '192.168.100.163', '2017-06-04 19:24:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2641', '63', '登录系统', '192.168.100.163', '2017-06-04 19:29:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2642', '63', '登录系统', '192.168.100.163', '2017-06-04 20:24:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2643', '63', '登录系统', '192.168.100.163', '2017-06-04 20:30:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2644', '63', '登录系统', '192.168.100.163', '2017-06-04 20:32:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2645', '63', '登录系统', '192.168.100.163', '2017-06-04 20:33:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2646', '63', '登录系统', '192.168.100.163', '2017-06-04 20:41:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2647', '63', '登录系统', '192.168.100.163', '2017-06-04 20:54:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2648', '63', '登录系统', '192.168.100.163', '2017-06-04 21:05:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2649', '63', '登录系统', '192.168.100.163', '2017-06-04 21:09:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2650', '63', '登录系统', '192.168.100.163', '2017-06-04 21:17:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2651', '63', '登录系统', '192.168.100.163', '2017-06-04 21:21:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2652', '63', '登录系统', '192.168.100.163', '2017-06-04 21:28:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2653', '63', '登录系统', '192.168.100.163', '2017-06-04 21:31:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2654', '63', '登录系统', '192.168.100.163', '2017-06-04 21:37:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2655', '63', '登录系统', '192.168.100.163', '2017-06-04 21:40:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2656', '63', '登录系统', '192.168.100.163', '2017-06-04 21:57:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2657', '63', '登录系统', '192.168.100.163', '2017-06-04 22:00:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2658', '63', '登录系统', '192.168.100.163', '2017-06-04 22:02:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2659', '63', '登录系统', '192.168.100.163', '2017-06-04 22:03:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2660', '63', '登录系统', '192.168.100.163', '2017-06-04 22:07:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2661', '63', '登录系统', '192.168.100.163', '2017-06-04 22:16:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2662', '63', '登录系统', '192.168.100.163', '2017-06-04 22:28:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2663', '63', '登录系统', '192.168.100.163', '2017-06-04 22:42:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2664', '63', '登录系统', '192.168.100.163', '2017-06-04 22:45:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2665', '63', '登录系统', '192.168.100.163', '2017-06-04 22:50:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2666', '63', '登录系统', '192.168.100.163', '2017-06-04 22:54:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2667', '63', '登录系统', '192.168.100.163', '2017-06-04 22:57:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2668', '63', '登录系统', '192.168.100.163', '2017-06-04 23:07:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2669', '63', '登录系统', '192.168.100.163', '2017-06-04 23:09:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2670', '63', '登录系统', '192.168.100.163', '2017-06-04 23:28:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2671', '63', '登录系统', '192.168.100.163', '2017-06-04 23:35:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2672', '63', '登录系统', '192.168.100.163', '2017-06-04 23:37:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2673', '63', '登录系统', '192.168.100.163', '2017-06-04 23:40:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2674', '63', '登录系统', '192.168.100.163', '2017-06-04 23:44:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2675', '63', '登录系统', '192.168.100.163', '2017-06-04 23:51:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2676', '63', '登录系统', '192.168.100.163', '2017-06-04 23:54:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2677', '63', '登录系统', '192.168.100.163', '2017-06-05 00:03:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2678', '63', '登录系统', '192.168.100.163', '2017-06-05 00:07:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2679', '63', '登录系统', '192.168.100.163', '2017-06-05 00:09:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2680', '63', '更新商品', '192.168.100.163', '2017-06-05 00:10:04', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('2681', '63', '登录系统', '192.168.100.163', '2017-06-05 00:11:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2682', '63', '增加仓管通', '192.168.100.163', '2017-06-05 00:13:47', '0', '增加仓管通编号为  22aa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2683', '63', '保存仓管通明细', '192.168.100.163', '2017-06-05 00:13:48', '0', '保存仓管通明细对应主表编号为  77 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2684', '63', '更新仓管通', '192.168.100.163', '2017-06-05 00:14:43', '0', '更新仓管通ID为  77 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2685', '63', '保存仓管通明细', '192.168.100.163', '2017-06-05 00:14:43', '0', '保存仓管通明细对应主表编号为  77 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2686', '63', '登录系统', '192.168.100.163', '2017-06-05 00:22:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2687', '63', '登录系统', '192.168.100.163', '2017-06-05 00:25:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2688', '63', '登录系统', '192.168.100.163', '2017-06-05 21:35:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2689', '63', '登录系统', '192.168.100.163', '2017-06-05 21:40:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2690', '63', '登录系统', '192.168.100.163', '2017-06-05 22:05:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2691', '63', '登录系统', '192.168.100.163', '2017-06-05 22:08:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2692', '63', '登录系统', '192.168.100.163', '2017-06-05 22:10:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2693', '63', '登录系统', '192.168.100.163', '2017-06-05 22:12:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2694', '63', '登录系统', '192.168.100.163', '2017-06-05 22:14:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2695', '63', '登录系统', '192.168.100.163', '2017-06-05 22:16:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2696', '63', '登录系统', '192.168.100.163', '2017-06-06 21:44:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2697', '63', '登录系统', '192.168.100.163', '2017-06-08 21:34:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2698', '63', '登录系统', '192.168.100.163', '2017-06-08 22:33:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2699', '63', '更新仓管通', '192.168.100.163', '2017-06-08 22:33:33', '0', '更新仓管通ID为  76 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2700', '63', '更新仓管通', '192.168.100.163', '2017-06-08 22:33:42', '0', '更新仓管通ID为  76 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2701', '63', '更新应用', '192.168.100.163', '2017-06-08 22:33:55', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2702', '63', '更新应用', '192.168.100.163', '2017-06-08 22:33:59', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2703', '63', '更新应用', '192.168.100.163', '2017-06-08 22:34:05', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2704', '63', '更新应用', '192.168.100.163', '2017-06-08 22:34:11', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2705', '63', '更新应用', '192.168.100.163', '2017-06-08 22:34:53', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2706', '63', '更新应用', '192.168.100.163', '2017-06-08 22:35:34', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2707', '63', '增加应用', '192.168.100.163', '2017-06-08 22:37:14', '0', '增加应用名称为  ssss 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2708', '63', '更新应用', '192.168.100.163', '2017-06-08 22:38:30', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2709', '63', '登录系统', '192.168.100.163', '2017-06-08 22:39:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2710', '63', '更新应用', '192.168.100.163', '2017-06-08 22:39:33', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2711', '63', '更新应用', '192.168.100.163', '2017-06-08 22:57:28', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2712', '63', '更新应用', '192.168.100.163', '2017-06-08 22:57:42', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2713', '63', '更新应用', '192.168.100.163', '2017-06-08 22:57:56', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2714', '63', '更新应用', '192.168.100.163', '2017-06-08 22:58:04', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2715', '63', '更新应用', '192.168.100.163', '2017-06-08 22:58:21', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2716', '63', '更新应用', '192.168.100.163', '2017-06-08 22:59:26', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2717', '63', '更新应用', '192.168.100.163', '2017-06-08 23:00:00', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2718', '63', '增加应用', '192.168.100.163', '2017-06-08 23:02:47', '0', '增加应用名称为  123123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2719', '63', '删除应用', '192.168.100.163', '2017-06-08 23:04:46', '0', '删除应用ID为  24 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2720', '63', '增加应用', '192.168.100.163', '2017-06-08 23:05:02', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2721', '63', '删除应用', '192.168.100.163', '2017-06-08 23:05:22', '0', '删除应用ID为  25 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2722', '63', '增加应用', '192.168.100.163', '2017-06-08 23:05:56', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2723', '63', '删除应用', '192.168.100.163', '2017-06-08 23:07:18', '0', '删除应用ID为  26 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2724', '63', '增加应用', '192.168.100.163', '2017-06-08 23:07:40', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2725', '63', '删除应用', '192.168.100.163', '2017-06-08 23:11:06', '0', '删除应用ID为  27 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2726', '63', '增加应用', '192.168.100.163', '2017-06-08 23:11:19', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2727', '63', '登录系统', '192.168.100.163', '2017-06-08 23:13:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2728', '63', '删除应用', '192.168.100.163', '2017-06-08 23:14:15', '0', '删除应用ID为  28 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2729', '63', '增加应用', '192.168.100.163', '2017-06-08 23:14:29', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2730', '63', '登录系统', '192.168.100.163', '2017-06-08 23:27:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2731', '63', '删除应用', '192.168.100.163', '2017-06-08 23:27:20', '0', '删除应用ID为  29 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2732', '63', '增加应用', '192.168.100.163', '2017-06-08 23:27:38', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2733', '63', '登录系统', '192.168.100.163', '2017-06-08 23:30:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2734', '63', '删除应用', '192.168.100.163', '2017-06-08 23:30:52', '0', '删除应用ID为  30 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2735', '63', '删除应用', '192.168.100.163', '2017-06-08 23:30:55', '0', '删除应用ID为  23 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2736', '63', '增加应用', '192.168.100.163', '2017-06-08 23:31:39', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2737', '63', '删除应用', '192.168.100.163', '2017-06-08 23:33:05', '0', '删除应用ID为  31 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2738', '63', '增加应用', '192.168.100.163', '2017-06-08 23:33:14', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2739', '63', '登录系统', '192.168.100.163', '2017-06-08 23:39:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2740', '63', '删除应用', '192.168.100.163', '2017-06-08 23:40:01', '0', '删除应用ID为  32 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2741', '63', '增加应用', '192.168.100.163', '2017-06-08 23:40:09', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2742', '63', '增加应用', '192.168.100.163', '2017-06-08 23:47:40', '0', '增加应用名称为  12311 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2743', '63', '登录系统', '192.168.100.163', '2017-06-08 23:50:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2744', '63', '删除应用', '192.168.100.163', '2017-06-08 23:50:45', '0', '删除应用ID为  33 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2745', '63', '删除应用', '192.168.100.163', '2017-06-08 23:50:47', '0', '删除应用ID为  34 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2746', '63', '增加应用', '192.168.100.163', '2017-06-08 23:51:09', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2747', '63', '登录系统', '192.168.100.163', '2017-06-08 23:54:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2748', '63', '删除应用', '192.168.100.163', '2017-06-08 23:54:44', '0', '删除应用ID为  35 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2749', '63', '增加应用', '192.168.100.163', '2017-06-08 23:55:01', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2750', '63', '登录系统', '192.168.100.163', '2017-06-09 21:26:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2751', '63', '删除应用', '192.168.100.163', '2017-06-09 21:28:03', '0', '删除应用ID为  36 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2752', '63', '增加应用', '192.168.100.163', '2017-06-09 21:28:14', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2753', '63', '登录系统', '192.168.100.163', '2017-06-09 21:52:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2754', '63', '删除应用', '192.168.100.163', '2017-06-09 21:52:41', '0', '删除应用ID为  23 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2755', '63', '增加应用', '192.168.100.163', '2017-06-09 21:52:59', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2756', '63', '登录系统', '192.168.100.163', '2017-06-09 21:58:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2757', '63', '增加应用', '192.168.100.163', '2017-06-09 21:58:51', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2758', '63', '登录系统', '192.168.100.163', '2017-06-09 22:01:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2759', '63', '删除应用', '192.168.100.163', '2017-06-09 22:01:33', '0', '删除应用ID为  25 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2760', '63', '登录系统', '192.168.100.163', '2017-06-11 18:49:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2761', '63', '登录系统', '192.168.100.163', '2017-06-11 18:51:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2762', '63', '登录系统', '192.168.100.163', '2017-06-11 18:56:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2763', '63', '登录系统', '192.168.100.163', '2017-06-11 19:01:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2764', '63', '登录系统', '192.168.100.163', '2017-06-11 19:03:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2765', '63', '登录系统', '192.168.100.163', '2017-06-11 19:18:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2766', '63', '登录系统', '192.168.100.163', '2017-06-11 20:43:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2767', '63', '登录系统', '192.168.100.163', '2017-06-11 21:24:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2768', '63', '登录系统', '192.168.100.163', '2017-06-11 21:36:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2769', '63', '登录系统', '192.168.100.163', '2017-06-11 23:17:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2770', '63', '登录系统', '192.168.100.163', '2017-06-11 23:26:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2771', '63', '登录系统', '192.168.100.163', '2017-06-11 23:29:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2772', '63', '增加应用', '192.168.100.163', '2017-06-11 23:30:23', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2773', '63', '删除应用', '192.168.100.163', '2017-06-11 23:31:27', '0', '删除应用ID为  23 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2774', '63', '增加应用', '192.168.100.163', '2017-06-11 23:32:04', '0', '增加应用名称为  123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2775', '63', '登录系统', '192.168.100.163', '2017-06-11 23:35:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2776', '63', '增加应用', '192.168.100.163', '2017-06-11 23:36:11', '0', '增加应用名称为  132 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2777', '63', '删除应用', '192.168.100.163', '2017-06-11 23:36:15', '0', '删除应用ID为  24 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2778', '63', '删除应用', '192.168.100.163', '2017-06-11 23:36:16', '0', '删除应用ID为  25 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2779', '63', '登录系统', '192.168.100.163', '2017-06-11 23:38:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2780', '63', '增加应用', '192.168.100.163', '2017-06-11 23:38:28', '0', '增加应用名称为  3123 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2781', '63', '登录系统', '192.168.100.163', '2017-06-12 22:11:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2782', '63', '登录系统', '192.168.100.163', '2017-06-12 22:45:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2783', '63', '登录系统', '192.168.100.163', '2017-06-12 22:46:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2784', '63', '删除应用', '192.168.100.163', '2017-06-12 22:46:22', '0', '删除应用ID为  26 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2785', '63', '登录系统', '192.168.100.163', '2017-06-12 22:51:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2786', '63', '登录系统', '192.168.100.163', '2017-06-12 22:54:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2787', '63', '登录系统', '192.168.100.163', '2017-06-12 23:02:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2788', '63', '登录系统', '192.168.100.163', '2017-06-12 23:12:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2789', '63', '登录系统', '192.168.100.163', '2017-06-12 23:15:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2790', '63', '登录系统', '192.168.100.163', '2017-06-12 23:24:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2791', '63', '登录系统', '192.168.100.163', '2017-06-13 20:59:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2792', '63', '登录系统', '192.168.100.163', '2017-06-13 21:10:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2793', '63', '登录系统', '192.168.100.163', '2017-06-13 21:12:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2794', '63', '登录系统', '192.168.100.163', '2017-06-13 21:31:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2795', '63', '登录系统', '192.168.100.163', '2017-06-13 21:35:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2796', '63', '登录系统', '192.168.100.163', '2017-06-13 22:19:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2797', '63', '登录系统', '192.168.100.163', '2017-06-13 22:24:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2798', '63', '登录系统', '192.168.100.163', '2017-06-13 22:28:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2799', '63', '登录系统', '192.168.100.163', '2017-06-13 22:38:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2800', '63', '登录系统', '192.168.100.163', '2017-06-13 22:57:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2801', '63', '登录系统', '192.168.100.163', '2017-06-13 23:03:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2802', '63', '登录系统', '192.168.100.163', '2017-06-13 23:07:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2803', '63', '登录系统', '192.168.100.163', '2017-06-13 23:16:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2804', '63', '登录系统', '192.168.100.163', '2017-06-13 23:20:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2805', '63', '登录系统', '192.168.100.163', '2017-06-13 23:30:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2806', '63', '登录系统', '192.168.100.163', '2017-06-13 23:36:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2807', '63', '登录系统', '192.168.100.163', '2017-06-13 23:38:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2808', '63', '登录系统', '192.168.100.163', '2017-06-13 23:39:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2809', '63', '登录系统', '192.168.100.163', '2017-06-13 23:42:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2810', '63', '登录系统', '192.168.100.163', '2017-06-14 21:03:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2811', '63', '登录系统', '192.168.100.163', '2017-06-14 21:03:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2812', '63', '登录系统', '192.168.100.163', '2017-06-14 21:03:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2813', '63', '登录系统', '192.168.100.163', '2017-06-14 21:16:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2814', '63', '增加应用', '192.168.100.163', '2017-06-14 21:30:03', '0', '增加应用名称为  333 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2815', '63', '删除应用', '192.168.100.163', '2017-06-14 21:30:52', '0', '删除应用ID为  23 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2816', '63', '增加应用', '192.168.100.163', '2017-06-14 21:31:09', '0', '增加应用名称为  6666 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2817', '63', '更新应用', '192.168.100.163', '2017-06-14 21:31:36', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2818', '63', '退出系统', '192.168.100.163', '2017-06-14 21:31:59', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2819', '63', '登录系统', '192.168.100.163', '2017-06-14 21:32:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2820', '63', '更新应用', '192.168.100.163', '2017-06-14 21:32:23', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2821', '63', '更新应用', '192.168.100.163', '2017-06-14 21:33:36', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2822', '63', '更新应用', '192.168.100.163', '2017-06-14 21:35:24', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2823', '63', '更新应用', '192.168.100.163', '2017-06-14 21:35:30', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2824', '63', '更新应用', '192.168.100.163', '2017-06-14 21:36:12', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2825', '63', '更新应用', '192.168.100.163', '2017-06-14 21:37:08', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2826', '63', '删除应用', '192.168.100.163', '2017-06-14 21:52:08', '0', '删除应用ID为  24 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2827', '63', '增加应用', '192.168.100.163', '2017-06-14 21:52:36', '0', '增加应用名称为  aaa 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2828', '63', '删除应用', '192.168.100.163', '2017-06-14 21:53:02', '0', '删除应用ID为  25 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2829', '63', '增加应用', '192.168.100.163', '2017-06-14 21:53:39', '0', '增加应用名称为  aaa 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2830', '63', '删除应用', '192.168.100.163', '2017-06-14 21:54:06', '0', '删除应用ID为  26 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2831', '63', '增加应用', '192.168.100.163', '2017-06-14 21:56:45', '0', '增加应用名称为  afdd 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2832', '63', '删除应用', '192.168.100.163', '2017-06-14 21:57:09', '0', '删除应用ID为  27 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2833', '63', '增加应用', '192.168.100.163', '2017-06-14 21:58:32', '0', '增加应用名称为  aaaa 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2834', '63', '删除应用', '192.168.100.163', '2017-06-14 21:58:36', '0', '删除应用ID为  28 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2835', '63', '增加应用', '192.168.100.163', '2017-06-14 21:58:54', '0', '增加应用名称为  add 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2836', '63', '删除应用', '192.168.100.163', '2017-06-14 21:58:59', '0', '删除应用ID为  29 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2837', '63', '增加应用', '192.168.100.163', '2017-06-14 22:00:16', '0', '增加应用名称为  addd 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2838', '63', '删除应用', '192.168.100.163', '2017-06-14 22:00:19', '0', '删除应用ID为  30 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2839', '63', '增加应用', '192.168.100.163', '2017-06-14 22:00:27', '0', '增加应用名称为  sdsd 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2840', '63', '删除应用', '192.168.100.163', '2017-06-14 22:01:33', '0', '删除应用ID为  31 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2841', '63', '增加应用', '192.168.100.163', '2017-06-14 22:03:05', '0', '增加应用名称为  qweqw 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2842', '63', '删除应用', '192.168.100.163', '2017-06-14 22:03:13', '0', '删除应用ID为  32 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2843', '63', '增加应用', '192.168.100.163', '2017-06-14 22:07:07', '0', '增加应用名称为  adf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2844', '63', '删除应用', '192.168.100.163', '2017-06-14 22:07:13', '0', '删除应用ID为  33 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2845', '63', '增加应用', '192.168.100.163', '2017-06-14 22:08:22', '0', '增加应用名称为  adadf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2846', '63', '删除应用', '192.168.100.163', '2017-06-14 22:09:54', '0', '删除应用ID为  34 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2847', '63', '增加应用', '192.168.100.163', '2017-06-14 22:10:02', '0', '增加应用名称为  asdfadsf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2848', '63', '删除应用', '192.168.100.163', '2017-06-14 22:10:28', '0', '删除应用ID为  35 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2849', '63', '增加应用', '192.168.100.163', '2017-06-14 22:10:34', '0', '增加应用名称为  asdf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2850', '63', '删除应用', '192.168.100.163', '2017-06-14 22:11:31', '0', '删除应用ID为  36 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2851', '63', '增加应用', '192.168.100.163', '2017-06-14 22:13:04', '0', '增加应用名称为  asdfadf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2852', '63', '删除应用', '192.168.100.163', '2017-06-14 22:13:16', '0', '删除应用ID为  37 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2853', '63', '增加应用', '192.168.100.163', '2017-06-14 22:13:49', '0', '增加应用名称为  adfadf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2854', '63', '删除应用', '192.168.100.163', '2017-06-14 22:13:52', '0', '删除应用ID为  38 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2855', '63', '增加应用', '192.168.100.163', '2017-06-14 22:18:05', '0', '增加应用名称为  dsaff 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2856', '63', '增加应用', '192.168.100.163', '2017-06-14 22:19:11', '0', '增加应用名称为  sadf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2857', '63', '删除应用', '192.168.100.163', '2017-06-14 22:19:28', '0', '删除应用ID为  39 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2858', '63', '删除应用', '192.168.100.163', '2017-06-14 22:19:34', '0', '删除应用ID为  40 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2859', '63', '增加应用', '192.168.100.163', '2017-06-14 22:52:27', '0', '增加应用名称为  werwqer 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2860', '63', '删除应用', '192.168.100.163', '2017-06-14 22:52:32', '0', '删除应用ID为  41 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2861', '63', '增加应用', '192.168.100.163', '2017-06-14 22:54:05', '0', '增加应用名称为  wqrqreq 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2862', '63', '删除应用', '192.168.100.163', '2017-06-14 22:54:14', '0', '删除应用ID为  42 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2863', '63', '更新应用', '192.168.100.163', '2017-06-14 22:56:48', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2864', '63', '更新应用', '192.168.100.163', '2017-06-14 22:56:54', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2865', '63', '增加应用', '192.168.100.163', '2017-06-14 22:58:19', '0', '增加应用名称为  asdfasdf 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2866', '63', '更新应用', '192.168.100.163', '2017-06-14 22:58:44', '0', '更新应用ID为  43 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('2867', '63', '删除应用', '192.168.100.163', '2017-06-14 22:58:50', '0', '删除应用ID为  43 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2868', '63', '增加应用', '192.168.100.163', '2017-06-14 23:00:48', '0', '增加应用名称为  qewrrr 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2869', '63', '删除应用', '192.168.100.163', '2017-06-14 23:00:55', '0', '删除应用ID为  44 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2870', '63', '增加应用', '192.168.100.163', '2017-06-14 23:01:02', '0', '增加应用名称为  qweqrewre 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('2871', '63', '删除应用', '192.168.100.163', '2017-06-14 23:01:08', '0', '删除应用ID为  45 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('2872', '63', '登录系统', '192.168.100.163', '2017-06-14 23:35:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2873', '63', '登录系统', '192.168.100.163', '2017-06-15 23:07:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2874', '63', '登录系统', '192.168.100.163', '2017-06-16 23:12:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2875', '63', '增加收支项目', '192.168.100.163', '2017-06-16 23:50:15', '0', '增加收支项目名称为  全车贴膜-普通 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('2876', '63', '增加收支项目', '192.168.100.163', '2017-06-16 23:50:28', '0', '增加收支项目名称为  全车贴膜-高档 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('2877', '63', '增加收支项目', '192.168.100.163', '2017-06-16 23:50:42', '0', '增加收支项目名称为  洗车 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('2878', '63', '增加收支项目', '192.168.100.163', '2017-06-16 23:51:09', '0', '增加收支项目名称为  保养汽车 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('2879', '63', '增加财务', '192.168.100.163', '2017-06-16 23:52:46', '0', '增加财务编号为  ffff 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2880', '63', '保存财务明细', '192.168.100.163', '2017-06-16 23:52:47', '0', '保存财务明细对应主表编号为  42 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2881', '63', '登录系统', '192.168.100.163', '2017-06-17 00:00:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2882', '63', '增加功能', '192.168.100.163', '2017-06-17 00:15:06', '0', '增加功能名称为  零售出库 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2883', '63', '更新功能', '192.168.100.163', '2017-06-17 00:15:18', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2884', '63', '更新功能', '192.168.100.163', '2017-06-17 00:15:42', '0', '更新功能ID为  40 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2885', '63', '更新功能', '192.168.100.163', '2017-06-17 00:15:49', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2886', '63', '更新UserBusiness', '192.168.100.163', '2017-06-17 00:16:12', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2887', '63', '增加功能', '192.168.100.163', '2017-06-17 00:18:39', '0', '增加功能名称为  零售退货 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2888', '63', '更新功能', '192.168.100.163', '2017-06-17 00:18:53', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2889', '63', '更新功能', '192.168.100.163', '2017-06-17 00:19:28', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2890', '63', '更新功能', '192.168.100.163', '2017-06-17 00:19:34', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2891', '63', '更新功能', '192.168.100.163', '2017-06-17 00:19:37', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2892', '63', '更新UserBusiness', '192.168.100.163', '2017-06-17 00:19:51', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2893', '63', '更新功能', '192.168.100.163', '2017-06-17 00:27:01', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2894', '63', '更新功能', '192.168.100.163', '2017-06-17 00:27:11', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('2895', '63', '登录系统', '192.168.100.163', '2017-06-18 18:19:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2896', '63', '登录系统', '192.168.100.163', '2017-06-18 18:33:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2897', '63', '增加仓管通', '192.168.100.163', '2017-06-18 18:53:02', '0', '增加仓管通编号为  sfd 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2898', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 18:53:03', '0', '保存仓管通明细对应主表编号为  78 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2899', '63', '增加仓管通', '192.168.100.163', '2017-06-18 18:59:49', '0', '增加仓管通编号为  fsdf 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2900', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 18:59:49', '0', '保存仓管通明细对应主表编号为  79 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2901', '63', '删除仓管通', '192.168.100.163', '2017-06-18 19:03:30', '0', '删除仓管通ID为  78 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2902', '63', '增加仓管通', '192.168.100.163', '2017-06-18 19:35:34', '0', '增加仓管通编号为  sfsdf 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2903', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 19:35:34', '0', '保存仓管通明细对应主表编号为  80 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2904', '63', '增加仓管通', '192.168.100.163', '2017-06-18 19:36:33', '0', '增加仓管通编号为  fsfsd 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2905', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 19:36:33', '0', '保存仓管通明细对应主表编号为  81 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2906', '63', '删除仓管通', '192.168.100.163', '2017-06-18 19:46:39', '0', '删除仓管通ID为  79 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2907', '63', '增加仓管通', '192.168.100.163', '2017-06-18 19:53:49', '0', '增加仓管通编号为  fasdf 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2908', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 19:53:49', '0', '保存仓管通明细对应主表编号为  82 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2909', '63', '增加仓管通', '192.168.100.163', '2017-06-18 22:56:17', '0', '增加仓管通编号为  qwe3 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2910', '63', '保存仓管通明细', '192.168.100.163', '2017-06-18 22:56:17', '0', '保存仓管通明细对应主表编号为  83 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2911', '63', '登录系统', '192.168.100.163', '2017-06-18 23:50:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2912', '63', '登录系统', '192.168.100.163', '2017-06-19 00:03:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2913', '63', '登录系统', '192.168.100.163', '2017-06-19 00:03:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2914', '63', '登录系统', '192.168.100.163', '2017-06-19 21:12:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2915', '63', '登录系统', '192.168.100.163', '2017-06-19 21:18:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2916', '63', '增加仓管通', '192.168.100.163', '2017-06-20 00:01:48', '1', '增加仓管通编号为  32141 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('2917', '63', '增加仓管通', '192.168.100.163', '2017-06-20 00:01:52', '0', '增加仓管通编号为  32141 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2918', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 00:01:52', '0', '保存仓管通明细对应主表编号为  84 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2919', '63', '登录系统', '192.168.100.163', '2017-06-20 00:32:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2920', '63', '登录系统', '192.168.100.163', '2017-06-20 00:34:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2921', '63', '增加仓管通', '192.168.100.163', '2017-06-20 00:34:45', '0', '增加仓管通编号为  1233 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2922', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 00:34:45', '0', '保存仓管通明细对应主表编号为  85 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2923', '63', '更新仓管通', '192.168.100.163', '2017-06-20 00:35:36', '0', '更新仓管通ID为  85 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2924', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 00:35:36', '0', '保存仓管通明细对应主表编号为  85 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2925', '63', '登录系统', '192.168.100.163', '2017-06-20 21:08:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2926', '63', '增加供应商', '192.168.100.163', '2017-06-20 21:18:12', '0', '增加供应商名称为  hy123456 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('2927', '63', '更新供应商', '192.168.100.163', '2017-06-20 21:18:19', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2928', '63', '更新供应商', '192.168.100.163', '2017-06-20 21:18:22', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2929', '63', '更新供应商', '192.168.100.163', '2017-06-20 21:23:34', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2930', '63', '更新供应商', '192.168.100.163', '2017-06-20 21:23:40', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2931', '63', '增加供应商', '192.168.100.163', '2017-06-20 21:25:21', '0', '增加供应商名称为  hy00001 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('2932', '63', '增加供应商', '192.168.100.163', '2017-06-20 21:25:52', '0', '增加供应商名称为  hy00002 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('2933', '63', '登录系统', '192.168.100.163', '2017-06-20 21:42:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2934', '63', '退出系统', '192.168.100.163', '2017-06-20 21:47:41', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('2935', '63', '登录系统', '192.168.100.163', '2017-06-20 21:47:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2936', '63', '增加仓管通', '192.168.100.163', '2017-06-20 21:56:51', '0', '增加仓管通编号为  hb3124312431 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2937', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 21:56:51', '0', '保存仓管通明细对应主表编号为  86 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2938', '63', '更新仓管通', '192.168.100.163', '2017-06-20 21:57:24', '0', '更新仓管通ID为  86 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2939', '63', '增加仓管通', '192.168.100.163', '2017-06-20 22:03:32', '0', '增加仓管通编号为  1231weraa 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2940', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 22:03:32', '0', '保存仓管通明细对应主表编号为  87 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2941', '63', '更新仓管通', '192.168.100.163', '2017-06-20 22:03:47', '0', '更新仓管通ID为  87 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2942', '63', '更新仓管通', '192.168.100.163', '2017-06-20 22:55:55', '0', '更新仓管通ID为  87 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2943', '63', '增加仓管通', '192.168.100.163', '2017-06-20 23:09:53', '0', '增加仓管通编号为  LSCK2017062023934 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2944', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 23:09:53', '0', '保存仓管通明细对应主表编号为  88 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2945', '63', '增加仓管通', '192.168.100.163', '2017-06-20 23:20:39', '0', '增加仓管通编号为  LSCK20170620232023 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2946', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 23:20:39', '0', '保存仓管通明细对应主表编号为  89 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2947', '63', '更新仓管通', '192.168.100.163', '2017-06-20 23:22:25', '0', '更新仓管通ID为  88 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2948', '63', '更新仓管通', '192.168.100.163', '2017-06-20 23:22:48', '0', '更新仓管通ID为  89 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2949', '63', '增加仓管通', '192.168.100.163', '2017-06-20 23:47:15', '0', '增加仓管通编号为  LSCK20170620234528 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2950', '63', '保存仓管通明细', '192.168.100.163', '2017-06-20 23:47:15', '0', '保存仓管通明细对应主表编号为  90 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2951', '63', '登录系统', '192.168.100.163', '2017-06-21 21:41:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2952', '63', '登录系统', '192.168.100.163', '2017-06-21 21:48:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2953', '63', '登录系统', '192.168.100.163', '2017-06-21 21:52:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2954', '63', '增加仓管通', '192.168.100.163', '2017-06-21 21:55:50', '0', '增加仓管通编号为  XSCK20170621215516 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2955', '63', '保存仓管通明细', '192.168.100.163', '2017-06-21 21:55:50', '0', '保存仓管通明细对应主表编号为  91 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2956', '63', '增加仓管通', '192.168.100.163', '2017-06-21 23:21:37', '0', '增加仓管通编号为  LSCK20170621232052 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2957', '63', '保存仓管通明细', '192.168.100.163', '2017-06-21 23:21:37', '0', '保存仓管通明细对应主表编号为  92 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2958', '63', '增加仓管通', '192.168.100.163', '2017-06-21 23:54:03', '0', '增加仓管通编号为  LSTH20170621235232 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('2959', '63', '保存仓管通明细', '192.168.100.163', '2017-06-21 23:54:03', '0', '保存仓管通明细对应主表编号为  93 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2960', '63', '删除仓管通', '192.168.100.163', '2017-06-21 23:57:51', '0', '删除仓管通ID为  93 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('2961', '63', '登录系统', '192.168.100.163', '2017-06-22 21:18:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2962', '63', '登录系统', '192.168.100.163', '2017-06-22 22:09:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2963', '63', '更新仓管通', '192.168.100.163', '2017-06-22 22:35:46', '0', '更新仓管通ID为  67 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2964', '63', '更新仓管通', '192.168.100.163', '2017-06-22 22:36:03', '0', '更新仓管通ID为  87 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2965', '63', '更新仓管通', '192.168.100.163', '2017-06-22 22:37:32', '0', '更新仓管通ID为  83 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('2966', '63', '登录系统', '192.168.100.163', '2017-06-22 22:56:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2967', '63', '登录系统', '192.168.100.163', '2017-06-22 23:37:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2968', '63', '登录系统', '192.168.100.163', '2017-06-23 22:18:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2969', '63', '登录系统', '192.168.100.163', '2017-06-23 22:44:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2970', '63', '更新用户', '', '2017-06-23 22:48:32', '0', '更新用户ID为  63密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('2971', '63', '登录系统', '192.168.100.163', '2017-06-23 22:48:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2972', '63', '登录系统', '192.168.100.163', '2017-06-23 22:56:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2973', '63', '登录系统', '192.168.100.163', '2017-06-23 23:07:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2974', '63', '登录系统', '192.168.100.163', '2017-06-23 23:17:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2975', '63', '登录系统', '192.168.100.163', '2017-06-23 23:37:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2976', '63', '登录系统', '192.168.100.163', '2017-06-23 23:48:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2977', '63', '登录系统', '192.168.100.163', '2017-06-25 22:12:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2978', '63', '登录系统', '192.168.100.163', '2017-06-25 22:32:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2979', '63', '登录系统', '192.168.100.163', '2017-06-26 20:44:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2980', '63', '增加财务', '192.168.100.163', '2017-06-26 21:16:34', '0', '增加财务编号为  abcdefg 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2981', '63', '保存财务明细', '192.168.100.163', '2017-06-26 21:16:34', '0', '保存财务明细对应主表编号为  43 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2982', '63', '增加功能', '192.168.100.163', '2017-06-26 21:31:06', '0', '增加功能名称为  收预付款 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('2983', '63', '更新UserBusiness', '192.168.100.163', '2017-06-26 21:32:12', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('2984', '63', '增加财务', '192.168.100.163', '2017-06-26 21:50:48', '0', '增加财务编号为  danju 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('2985', '63', '保存财务明细', '192.168.100.163', '2017-06-26 21:50:48', '0', '保存财务明细对应主表编号为  44 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('2986', '63', '登录系统', '192.168.100.163', '2017-06-26 22:38:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2987', '63', '登录系统', '192.168.100.163', '2017-06-26 22:40:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2988', '63', '更新财务', '192.168.100.163', '2017-06-26 23:00:07', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2989', '63', '更新财务', '192.168.100.163', '2017-06-26 23:00:31', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2990', '63', '更新财务', '192.168.100.163', '2017-06-26 23:06:49', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2991', '63', '更新财务', '192.168.100.163', '2017-06-26 23:11:07', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2992', '63', '更新供应商预付款', '', '2017-06-26 23:11:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2993', '63', '更新供应商预付款', '', '2017-06-26 23:11:17', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2994', '63', '更新财务', '192.168.100.163', '2017-06-26 23:11:17', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2995', '63', '更新财务', '192.168.100.163', '2017-06-26 23:16:35', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('2996', '63', '更新供应商预付款', '', '2017-06-26 23:16:35', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2997', '63', '登录系统', '192.168.100.163', '2017-06-26 23:48:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('2998', '63', '更新供应商预付款', '', '2017-06-26 23:49:09', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('2999', '63', '更新财务', '192.168.100.163', '2017-06-26 23:49:09', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3000', '63', '更新财务', '192.168.100.163', '2017-06-26 23:49:55', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3001', '63', '更新供应商预付款', '', '2017-06-26 23:49:55', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3002', '63', '登录系统', '192.168.100.163', '2017-06-26 23:52:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3003', '63', '更新供应商预付款', '', '2017-06-26 23:53:01', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3004', '63', '更新财务', '192.168.100.163', '2017-06-26 23:53:01', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3005', '63', '更新财务', '192.168.100.163', '2017-06-26 23:58:30', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3006', '63', '更新供应商预付款', '', '2017-06-26 23:58:30', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3007', '63', '登录系统', '192.168.100.163', '2017-06-27 00:01:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3008', '63', '更新财务', '192.168.100.163', '2017-06-27 00:02:16', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3009', '63', '更新供应商预付款', '', '2017-06-27 00:02:16', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3010', '63', '登录系统', '192.168.100.163', '2017-06-27 00:27:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3011', '63', '更新财务', '192.168.100.163', '2017-06-27 00:27:23', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3012', '63', '更新供应商预付款', '', '2017-06-27 00:27:23', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3013', '63', '更新财务', '192.168.100.163', '2017-06-27 00:27:42', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3014', '63', '更新供应商预付款', '', '2017-06-27 00:27:42', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3015', '63', '更新供应商', '192.168.100.163', '2017-06-27 00:28:37', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3016', '63', '更新财务', '192.168.100.163', '2017-06-27 00:28:47', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3017', '63', '更新供应商预付款', '', '2017-06-27 00:28:47', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3018', '63', '更新供应商', '192.168.100.163', '2017-06-27 00:30:12', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3019', '63', '更新财务', '192.168.100.163', '2017-06-27 00:30:28', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3020', '63', '保存财务明细', '192.168.100.163', '2017-06-27 00:30:29', '0', '保存财务明细对应主表编号为  44 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3021', '63', '更新供应商预付款', '', '2017-06-27 00:30:29', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3022', '63', '更新财务', '192.168.100.163', '2017-06-27 00:31:29', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3023', '63', '更新供应商预付款', '', '2017-06-27 00:31:29', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3024', '63', '登录系统', '192.168.100.163', '2017-06-27 20:43:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3025', '63', '删除财务', '192.168.100.163', '2017-06-27 21:22:46', '0', '删除财务ID为  44 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3026', '63', '更新供应商预付款', '', '2017-06-27 21:24:37', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3027', '63', '增加财务', '192.168.100.163', '2017-06-27 21:24:37', '0', '增加财务编号为  asdasd 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3028', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:24:38', '0', '保存财务明细对应主表编号为  44 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3029', '63', '更新供应商预付款', '', '2017-06-27 21:25:02', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3030', '63', '更新财务', '192.168.100.163', '2017-06-27 21:25:02', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3031', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:25:02', '0', '保存财务明细对应主表编号为  44 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3032', '63', '更新供应商预付款', '', '2017-06-27 21:25:18', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3033', '63', '更新财务', '192.168.100.163', '2017-06-27 21:25:18', '0', '更新财务ID为  44 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3034', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:25:18', '0', '保存财务明细对应主表编号为  44 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3035', '63', '更新供应商预付款', '', '2017-06-27 21:27:30', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3036', '63', '增加财务', '192.168.100.163', '2017-06-27 21:27:30', '0', '增加财务编号为  545qwe 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3037', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:27:31', '0', '保存财务明细对应主表编号为  45 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3038', '63', '更新供应商预付款', '', '2017-06-27 21:27:46', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3039', '63', '更新财务', '192.168.100.163', '2017-06-27 21:27:46', '0', '更新财务ID为  45 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3040', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:27:46', '0', '保存财务明细对应主表编号为  45 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3041', '63', '更新供应商预付款', '', '2017-06-27 21:28:02', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3042', '63', '更新财务', '192.168.100.163', '2017-06-27 21:28:02', '0', '更新财务ID为  45 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3043', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:28:02', '0', '保存财务明细对应主表编号为  45 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3044', '63', '更新供应商预付款', '', '2017-06-27 21:51:29', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3045', '63', '删除财务', '192.168.100.163', '2017-06-27 21:51:29', '0', '删除财务ID为  44 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3046', '63', '更新供应商预付款', '', '2017-06-27 21:51:56', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3047', '63', '增加财务', '192.168.100.163', '2017-06-27 21:51:56', '0', '增加财务编号为  2314 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3048', '63', '保存财务明细', '192.168.100.163', '2017-06-27 21:51:56', '0', '保存财务明细对应主表编号为  46 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3049', '63', '更新供应商预付款', '', '2017-06-27 22:05:33', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3050', '63', '删除财务', '192.168.100.163', '2017-06-27 22:05:33', '0', '删除财务ID为  46 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3051', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:05:41', '0', '批量删除财务ID为  45 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3052', '63', '更新供应商预付款', '', '2017-06-27 22:07:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3053', '63', '增加财务', '192.168.100.163', '2017-06-27 22:07:07', '0', '增加财务编号为  123412 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3054', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:07:07', '0', '保存财务明细对应主表编号为  47 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3055', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:07:24', '0', '批量删除财务ID为  47 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3056', '63', '更新供应商预付款', '', '2017-06-27 22:08:29', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3057', '63', '增加财务', '192.168.100.163', '2017-06-27 22:08:29', '0', '增加财务编号为  12341 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3058', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:08:29', '0', '保存财务明细对应主表编号为  48 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3059', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:08:33', '0', '批量删除财务ID为  48 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3060', '63', '更新供应商预付款', '', '2017-06-27 22:11:23', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3061', '63', '增加财务', '192.168.100.163', '2017-06-27 22:11:23', '0', '增加财务编号为  wqeqw 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3062', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:11:23', '0', '保存财务明细对应主表编号为  49 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3063', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:15:27', '0', '批量删除财务ID为  49 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3064', '63', '更新供应商预付款', '', '2017-06-27 22:17:18', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3065', '63', '增加财务', '192.168.100.163', '2017-06-27 22:17:18', '0', '增加财务编号为  2342 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3066', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:17:18', '0', '保存财务明细对应主表编号为  50 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3067', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:17:30', '0', '批量删除财务ID为  50 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3068', '63', '更新供应商预付款', '', '2017-06-27 22:18:43', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3069', '63', '增加财务', '192.168.100.163', '2017-06-27 22:18:43', '0', '增加财务编号为  324234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3070', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:18:43', '0', '保存财务明细对应主表编号为  51 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3071', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:18:53', '0', '批量删除财务ID为  51 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3072', '63', '更新供应商预付款', '', '2017-06-27 22:20:01', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3073', '63', '增加财务', '192.168.100.163', '2017-06-27 22:20:01', '0', '增加财务编号为  4234 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3074', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:20:01', '0', '保存财务明细对应主表编号为  52 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3075', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:20:11', '0', '批量删除财务ID为  52 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3076', '63', '更新供应商预付款', '', '2017-06-27 22:21:43', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3077', '63', '增加财务', '192.168.100.163', '2017-06-27 22:21:43', '0', '增加财务编号为  4223 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3078', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:21:44', '0', '保存财务明细对应主表编号为  53 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3079', '63', '更新供应商预付款', '', '2017-06-27 22:21:51', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3080', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:21:51', '0', '批量删除财务ID为  53 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3081', '63', '更新供应商预付款', '', '2017-06-27 22:25:31', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3082', '63', '增加财务', '192.168.100.163', '2017-06-27 22:25:31', '0', '增加财务编号为  243423 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3083', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:25:32', '0', '保存财务明细对应主表编号为  54 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3084', '63', '更新供应商预付款', '', '2017-06-27 22:25:52', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3085', '63', '增加财务', '192.168.100.163', '2017-06-27 22:25:52', '0', '增加财务编号为  2134124 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3086', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:25:52', '0', '保存财务明细对应主表编号为  55 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3087', '63', '增加财务', '192.168.100.163', '2017-06-27 22:26:12', '0', '增加财务编号为  42342 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3088', '63', '更新供应商预付款', '', '2017-06-27 22:26:12', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3089', '63', '保存财务明细', '192.168.100.163', '2017-06-27 22:26:12', '0', '保存财务明细对应主表编号为  56 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3090', '63', '更新供应商预付款', '', '2017-06-27 22:26:21', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3091', '63', '更新供应商预付款', '', '2017-06-27 22:26:21', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3092', '63', '更新供应商预付款', '', '2017-06-27 22:26:21', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3093', '63', '批量删除财务', '192.168.100.163', '2017-06-27 22:26:21', '0', '批量删除财务ID为  56,55,54 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3094', '63', '登录系统', '192.168.100.163', '2017-06-27 23:37:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3095', '63', '更新供应商预付款', '', '2017-06-27 23:38:10', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3096', '63', '增加财务', '192.168.100.163', '2017-06-27 23:38:10', '0', '增加财务编号为  2342134 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3097', '63', '保存财务明细', '192.168.100.163', '2017-06-27 23:38:10', '0', '保存财务明细对应主表编号为  57 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3098', '63', '增加仓管通', '192.168.100.163', '2017-06-27 23:38:45', '0', '增加仓管通编号为  LSCK20170627233726 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3099', '63', '保存仓管通明细', '192.168.100.163', '2017-06-27 23:38:45', '0', '保存仓管通明细对应主表编号为  93 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3100', '63', '更新供应商预付款', '', '2017-06-27 23:38:45', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3101', '63', '登录系统', '127.0.0.1', '2017-06-28 20:48:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3102', '63', '登录系统', '192.168.100.163', '2017-06-28 21:06:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3103', '63', '登录系统', '192.168.100.163', '2017-06-28 21:22:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3104', '63', '更新仓管通', '192.168.100.163', '2017-06-28 21:24:37', '0', '更新仓管通ID为  92 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3105', '63', '登录系统', '192.168.100.163', '2017-06-28 21:27:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3106', '63', '登录系统', '192.168.100.163', '2017-06-28 22:25:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3107', '63', '增加仓管通', '192.168.100.163', '2017-06-28 22:42:13', '0', '增加仓管通编号为  LSCK20170628224159 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3108', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 22:42:13', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3109', '63', '更新供应商预付款', '', '2017-06-28 22:42:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3110', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:01:02', '0', '更新仓管通ID为  94 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3111', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:01:02', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3112', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:03:05', '0', '更新仓管通ID为  94 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3113', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:03:05', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3114', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:03:57', '0', '更新仓管通ID为  94 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3115', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:03:58', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3116', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:05:41', '0', '更新仓管通ID为  94 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3117', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:05:41', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3118', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:06:34', '0', '增加仓管通编号为  LSCK2017062823614 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3119', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:06:34', '0', '保存仓管通明细对应主表编号为  95 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3120', '63', '更新供应商预付款', '', '2017-06-28 23:06:35', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3121', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:07:07', '0', '更新仓管通ID为  95 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3122', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:08:46', '0', '更新仓管通ID为  95 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3123', '63', '更新供应商预付款', '', '2017-06-28 23:08:46', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3124', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:08:47', '0', '保存仓管通明细对应主表编号为  95 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3125', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:09:07', '0', '更新仓管通ID为  95 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3126', '63', '更新供应商预付款', '', '2017-06-28 23:09:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3127', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:09:07', '0', '保存仓管通明细对应主表编号为  95 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3128', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:10:12', '0', '更新仓管通ID为  95 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3129', '63', '更新供应商预付款', '', '2017-06-28 23:10:12', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3130', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:10:12', '0', '保存仓管通明细对应主表编号为  95 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3131', '63', '更新供应商预付款', '', '2017-06-28 23:21:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3132', '63', '删除仓管通', '192.168.100.163', '2017-06-28 23:21:07', '0', '删除仓管通ID为  95 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3133', '63', '批量删除仓管通', '192.168.100.163', '2017-06-28 23:32:12', '0', '批量删除仓管通ID为  94,93 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3134', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:35:41', '0', '增加仓管通编号为  LSCK20170628233445 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3135', '63', '更新供应商预付款', '', '2017-06-28 23:35:41', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3136', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:35:41', '0', '保存仓管通明细对应主表编号为  96 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3137', '63', '更新供应商预付款', '', '2017-06-28 23:36:03', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3138', '63', '批量删除仓管通', '192.168.100.163', '2017-06-28 23:36:03', '0', '批量删除仓管通ID为  96 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3139', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:36:27', '0', '增加仓管通编号为  LSCK20170628233610 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3140', '63', '更新供应商预付款', '', '2017-06-28 23:36:27', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3141', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:36:27', '0', '保存仓管通明细对应主表编号为  97 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3142', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:36:47', '0', '增加仓管通编号为  LSCK20170628233636 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3143', '63', '更新供应商预付款', '', '2017-06-28 23:36:48', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3144', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:36:48', '0', '保存仓管通明细对应主表编号为  98 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3145', '63', '更新供应商预付款', '', '2017-06-28 23:36:59', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3146', '63', '批量删除仓管通', '192.168.100.163', '2017-06-28 23:36:59', '0', '批量删除仓管通ID为  98,97 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3147', '63', '更新供应商预付款', '', '2017-06-28 23:36:59', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3148', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:37:42', '0', '增加仓管通编号为  LSCK20170628233725 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3149', '63', '更新供应商预付款', '', '2017-06-28 23:37:42', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3150', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:37:42', '0', '保存仓管通明细对应主表编号为  99 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3151', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:38:00', '0', '增加仓管通编号为  LSCK20170628233749 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3152', '63', '更新供应商预付款', '', '2017-06-28 23:38:00', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3153', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:38:01', '0', '保存仓管通明细对应主表编号为  100 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3154', '63', '更新供应商预付款', '', '2017-06-28 23:38:04', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3155', '63', '删除仓管通', '192.168.100.163', '2017-06-28 23:38:04', '0', '删除仓管通ID为  100 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3156', '63', '批量删除仓管通', '192.168.100.163', '2017-06-28 23:38:12', '0', '批量删除仓管通ID为  99 成功！', '批量删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3157', '63', '更新供应商预付款', '', '2017-06-28 23:38:12', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3158', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:38:51', '0', '增加仓管通编号为  LSTH20170628233840 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3159', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:38:51', '0', '保存仓管通明细对应主表编号为  101 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3160', '63', '删除仓管通', '192.168.100.163', '2017-06-28 23:38:59', '0', '删除仓管通ID为  101 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3161', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:39:06', '0', '更新仓管通ID为  81 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3162', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:39:06', '0', '保存仓管通明细对应主表编号为  81 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3163', '63', '更新供应商', '192.168.100.163', '2017-06-28 23:43:09', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3164', '63', '增加供应商', '192.168.100.163', '2017-06-28 23:43:27', '0', '增加供应商名称为  42134 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3165', '63', '更新供应商', '192.168.100.163', '2017-06-28 23:43:42', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3166', '63', '更新供应商', '192.168.100.163', '2017-06-28 23:43:50', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3167', '63', '删除供应商', '192.168.100.163', '2017-06-28 23:43:54', '0', '删除供应商ID为  10,名称为  42134成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3168', '63', '增加仓管通', '192.168.100.163', '2017-06-28 23:57:15', '0', '增加仓管通编号为  LSCK2017062823571 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3169', '63', '更新供应商预付款', '', '2017-06-28 23:57:15', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3170', '63', '保存仓管通明细', '192.168.100.163', '2017-06-28 23:57:15', '0', '保存仓管通明细对应主表编号为  102 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3171', '63', '更新仓管通', '192.168.100.163', '2017-06-28 23:57:33', '0', '更新仓管通ID为  102 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3172', '63', '更新供应商预付款', '', '2017-06-28 23:57:33', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3173', '63', '登录系统', '192.168.100.163', '2017-06-29 00:00:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3174', '63', '更新供应商预付款', '', '2017-06-29 00:01:49', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3175', '63', '删除仓管通', '192.168.100.163', '2017-06-29 00:01:49', '0', '删除仓管通ID为  102 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3176', '63', '增加仓管通', '192.168.100.163', '2017-06-29 00:02:24', '0', '增加仓管通编号为  LSCK20170629029 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3177', '63', '更新供应商预付款', '', '2017-06-29 00:02:24', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3178', '63', '保存仓管通明细', '192.168.100.163', '2017-06-29 00:02:24', '0', '保存仓管通明细对应主表编号为  103 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3179', '63', '更新仓管通', '192.168.100.163', '2017-06-29 00:05:40', '0', '更新仓管通ID为  103 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3180', '63', '更新仓管通', '192.168.100.163', '2017-06-29 00:06:10', '0', '更新仓管通ID为  103 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3181', '63', '更新供应商预付款', '', '2017-06-29 00:06:10', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3182', '63', '增加财务', '192.168.100.163', '2017-06-29 00:16:41', '0', '增加财务编号为  SR2017062901617 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3183', '63', '保存财务明细', '192.168.100.163', '2017-06-29 00:16:41', '0', '保存财务明细对应主表编号为  58 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3184', '63', '增加财务', '192.168.100.163', '2017-06-29 00:17:07', '0', '增加财务编号为  SK2017062901657 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3185', '63', '保存财务明细', '192.168.100.163', '2017-06-29 00:17:07', '0', '保存财务明细对应主表编号为  59 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3186', '63', '增加财务', '192.168.100.163', '2017-06-29 00:17:17', '0', '增加财务编号为  FK2017062901710 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3187', '63', '保存财务明细', '192.168.100.163', '2017-06-29 00:17:17', '0', '保存财务明细对应主表编号为  60 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3188', '63', '更新供应商预付款', '', '2017-06-29 00:17:28', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3189', '63', '增加财务', '192.168.100.163', '2017-06-29 00:17:28', '0', '增加财务编号为  SYF2017062901721 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3190', '63', '保存财务明细', '192.168.100.163', '2017-06-29 00:17:28', '0', '保存财务明细对应主表编号为  61 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3191', '63', '更新财务', '192.168.100.163', '2017-06-29 00:25:02', '0', '更新财务ID为  61 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3192', '63', '更新供应商预付款', '', '2017-06-29 00:25:03', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3193', '63', '登录系统', '192.168.100.163', '2017-06-29 22:01:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3194', '63', '增加仓管通', '192.168.100.163', '2017-06-29 22:12:12', '1', '增加仓管通编号为  CGRK2017062922124 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('3195', '63', '增加仓管通', '192.168.100.163', '2017-06-29 22:27:01', '0', '增加仓管通编号为  CGRK20170629222643 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3196', '63', '更新仓管通', '192.168.100.163', '2017-06-29 22:27:06', '0', '更新仓管通ID为  104 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3197', '63', '更新仓管通', '192.168.100.163', '2017-06-29 22:27:21', '0', '更新仓管通ID为  104 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3198', '63', '删除仓管通', '192.168.100.163', '2017-06-29 22:27:52', '0', '删除仓管通ID为  104 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3199', '63', '增加财务', '192.168.100.163', '2017-06-29 23:07:07', '0', '增加财务编号为  SYF2017062923659 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3200', '63', '更新供应商预付款', '', '2017-06-29 23:07:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3201', '63', '删除财务', '192.168.100.163', '2017-06-29 23:07:11', '0', '删除财务ID为  62 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3202', '63', '更新供应商预付款', '', '2017-06-29 23:07:11', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3203', '63', '增加商品', '192.168.100.163', '2017-06-29 23:12:15', '0', '增加商品名称为  143 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3204', '63', '删除商品', '192.168.100.163', '2017-06-29 23:12:25', '0', '删除商品ID为  500 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('3205', '63', '增加供应商', '192.168.100.163', '2017-06-29 23:12:41', '0', '增加供应商名称为  2134234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3206', '63', '删除供应商', '192.168.100.163', '2017-06-29 23:12:48', '0', '删除供应商ID为  10,名称为  2134234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3207', '63', '增加供应商', '192.168.100.163', '2017-06-29 23:15:01', '0', '增加供应商名称为  43234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3208', '63', '删除供应商', '192.168.100.163', '2017-06-29 23:15:05', '0', '删除供应商ID为  11,名称为  43234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3209', '63', '增加收支项目', '192.168.100.163', '2017-06-29 23:15:15', '0', '增加收支项目名称为  234234 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('3210', '63', '删除收支项目', '192.168.100.163', '2017-06-29 23:15:17', '0', '删除收支项目ID为  16,名称为  234234成功！', '删除收支项目成功');
INSERT INTO `jsh_log` VALUES ('3211', '63', '增加收支项目', '192.168.100.163', '2017-06-29 23:16:26', '0', '增加收支项目名称为  234234 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('3212', '63', '删除收支项目', '192.168.100.163', '2017-06-29 23:16:34', '0', '删除收支项目ID为  17,名称为  234234成功！', '删除收支项目成功');
INSERT INTO `jsh_log` VALUES ('3213', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:20:19', '1', '增加经手人名称为  asdf 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('3214', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:20:29', '1', '增加经手人名称为  fasdfa 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('3215', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:20:32', '1', '增加经手人名称为  fasdfa 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('3216', '63', '增加结算账户', '192.168.100.163', '2017-06-29 23:20:49', '0', '增加结算账户名称为  asdf 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('3217', '63', '删除结算账户', '192.168.100.163', '2017-06-29 23:20:52', '0', '删除结算账户ID为  10,名称为  asdf成功！', '删除结算账户成功');
INSERT INTO `jsh_log` VALUES ('3218', '63', '增加收支项目', '192.168.100.163', '2017-06-29 23:21:00', '0', '增加收支项目名称为  adfasdf 成功！', '增加收支项目成功');
INSERT INTO `jsh_log` VALUES ('3219', '63', '删除收支项目', '192.168.100.163', '2017-06-29 23:21:02', '0', '删除收支项目ID为  18,名称为  adfasdf成功！', '删除收支项目成功');
INSERT INTO `jsh_log` VALUES ('3220', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:21:09', '1', '增加经手人名称为  adsfadsf 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('3221', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:21:44', '1', '增加经手人名称为  adsfadsf 失败！', '增加经手人失败');
INSERT INTO `jsh_log` VALUES ('3222', '63', '增加仓管通', '192.168.100.163', '2017-06-29 23:30:31', '0', '增加仓管通编号为  XSCK20170629233015 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3223', '63', '保存仓管通明细', '192.168.100.163', '2017-06-29 23:30:31', '0', '保存仓管通明细对应主表编号为  105 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3224', '63', '更新仓管通', '192.168.100.163', '2017-06-29 23:30:37', '0', '更新仓管通ID为  105 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3225', '63', '删除仓管通', '192.168.100.163', '2017-06-29 23:30:47', '0', '删除仓管通ID为  105 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3226', '63', '增加财务', '192.168.100.163', '2017-06-29 23:31:30', '0', '增加财务编号为  SR20170629233113 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3227', '63', '保存财务明细', '192.168.100.163', '2017-06-29 23:31:30', '0', '保存财务明细对应主表编号为  63 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3228', '63', '删除财务', '192.168.100.163', '2017-06-29 23:31:34', '0', '删除财务ID为  63 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3229', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:32:13', '0', '增加经手人名称为  werwer 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('3230', '63', '删除经手人', '192.168.100.163', '2017-06-29 23:32:16', '0', '删除经手人ID为  5 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('3231', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:32:19', '0', '增加经手人名称为  werwrweqr 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('3232', '63', '删除经手人', '192.168.100.163', '2017-06-29 23:32:22', '0', '删除经手人ID为  6 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('3233', '63', '增加角色', '192.168.100.163', '2017-06-29 23:33:01', '0', '增加角色名称为  wer 成功！', '增加角色成功');
INSERT INTO `jsh_log` VALUES ('3234', '63', '删除角色', '192.168.100.163', '2017-06-29 23:33:03', '0', '删除角色ID为  6 成功！', '删除角色成功');
INSERT INTO `jsh_log` VALUES ('3235', '63', '增加功能', '192.168.100.163', '2017-06-29 23:35:33', '0', '增加功能名称为  qweqweqw 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3236', '63', '更新功能', '192.168.100.163', '2017-06-29 23:35:38', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3237', '63', '更新功能', '192.168.100.163', '2017-06-29 23:35:43', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3238', '63', '删除功能', '192.168.100.163', '2017-06-29 23:35:48', '0', '删除功能ID为  213 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('3239', '63', '增加经手人', '192.168.100.163', '2017-06-29 23:40:12', '0', '增加经手人名称为  adsfasd 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('3240', '63', '更新经手人', '192.168.100.163', '2017-06-29 23:40:16', '0', '更新经手人ID为  7 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('3241', '63', '更新经手人', '192.168.100.163', '2017-06-29 23:40:19', '0', '更新经手人ID为  7 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('3242', '63', '删除经手人', '192.168.100.163', '2017-06-29 23:40:22', '0', '删除经手人ID为  7 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('3243', '63', '增加仓库', '192.168.100.163', '2017-06-29 23:43:47', '0', '增加仓库名称为  2344 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3244', '63', '删除仓库', '192.168.100.163', '2017-06-29 23:43:50', '0', '删除仓库ID为  4 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('3245', '63', '????', '192.168.100.163', '2017-06-30 23:41:08', '0', '?????jsh ????', 'jsh ????');
INSERT INTO `jsh_log` VALUES ('3246', '63', '登录系统', '192.168.100.163', '2017-06-30 23:43:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3247', '63', '增加供应商', '192.168.100.163', '2017-06-30 23:44:46', '0', '增加供应商名称为  1268787965 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3248', '63', '更新供应商', '192.168.100.163', '2017-06-30 23:45:12', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3249', '63', '登录系统', '192.168.100.163', '2017-07-02 19:50:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3250', '63', '增加仓管通', '192.168.100.163', '2017-07-02 21:39:50', '0', '增加仓管通编号为  LSCK20170702213926 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3251', '63', '保存仓管通明细', '192.168.100.163', '2017-07-02 21:39:50', '0', '保存仓管通明细对应主表编号为  104 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3252', '63', '增加财务', '192.168.100.163', '2017-07-02 21:56:57', '0', '增加财务编号为  SYF20170702215630 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3253', '63', '保存财务明细', '192.168.100.163', '2017-07-02 21:56:57', '0', '保存财务明细对应主表编号为  62 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3254', '63', '删除财务', '192.168.100.163', '2017-07-02 21:57:03', '0', '删除财务ID为  62 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3255', '63', '增加财务', '192.168.100.163', '2017-07-02 21:57:17', '0', '增加财务编号为  SYF2017070221578 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3256', '63', '保存财务明细', '192.168.100.163', '2017-07-02 21:57:17', '0', '保存财务明细对应主表编号为  63 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3257', '63', '删除财务', '192.168.100.163', '2017-07-02 21:57:23', '0', '删除财务ID为  63 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3258', '63', '增加财务', '192.168.100.163', '2017-07-02 21:58:54', '0', '增加财务编号为  SYF20170702215841 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3259', '63', '更新财务', '192.168.100.163', '2017-07-02 21:59:04', '0', '更新财务ID为  64 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3260', '63', '保存财务明细', '192.168.100.163', '2017-07-02 21:59:04', '0', '保存财务明细对应主表编号为  64 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3261', '63', '删除财务', '192.168.100.163', '2017-07-02 21:59:21', '0', '删除财务ID为  64 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3262', '63', '增加财务', '192.168.100.163', '2017-07-02 21:59:53', '0', '增加财务编号为  SR20170702215937 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3263', '63', '保存财务明细', '192.168.100.163', '2017-07-02 21:59:53', '0', '保存财务明细对应主表编号为  65 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3264', '63', '删除财务', '192.168.100.163', '2017-07-02 21:59:57', '0', '删除财务ID为  65 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3265', '63', '增加财务', '192.168.100.163', '2017-07-02 22:00:34', '0', '增加财务编号为  SYF2017070222021 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3266', '63', '保存财务明细', '192.168.100.163', '2017-07-02 22:00:34', '0', '保存财务明细对应主表编号为  66 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3267', '63', '删除财务', '192.168.100.163', '2017-07-02 22:02:01', '0', '删除财务ID为  66 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3268', '63', '增加财务', '192.168.100.163', '2017-07-02 22:04:28', '0', '增加财务编号为  SYF2017070222414 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3269', '63', '更新供应商预付款', '', '2017-07-02 22:04:28', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3270', '63', '保存财务明细', '192.168.100.163', '2017-07-02 22:04:28', '0', '保存财务明细对应主表编号为  67 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3271', '63', '增加供应商', '192.168.100.163', '2017-07-02 22:04:56', '0', '增加供应商名称为  2341234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3272', '63', '删除供应商', '192.168.100.163', '2017-07-02 22:05:22', '0', '删除供应商ID为  11,名称为  2341234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3273', '63', '登录系统', '192.168.100.163', '2017-07-02 22:10:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3274', '63', '增加供应商', '192.168.100.163', '2017-07-02 22:10:12', '0', '增加供应商名称为  2131243 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3275', '63', '更新供应商', '192.168.100.163', '2017-07-02 22:10:16', '0', '更新供应商ID为  12 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3276', '63', '更新供应商', '192.168.100.163', '2017-07-02 22:10:19', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3277', '63', '增加财务', '192.168.100.163', '2017-07-02 22:10:44', '0', '增加财务编号为  SYF20170702221030 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3278', '63', '保存财务明细', '192.168.100.163', '2017-07-02 22:10:44', '0', '保存财务明细对应主表编号为  68 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3279', '63', '更新供应商预付款', '', '2017-07-02 22:10:45', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3280', '63', '删除财务', '192.168.100.163', '2017-07-02 22:11:17', '0', '删除财务ID为  68 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3281', '63', '更新供应商预付款', '', '2017-07-02 22:11:17', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3282', '63', '增加财务', '192.168.100.163', '2017-07-02 22:11:31', '0', '增加财务编号为  SYF20170702221121 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3283', '63', '保存财务明细', '192.168.100.163', '2017-07-02 22:11:32', '0', '保存财务明细对应主表编号为  69 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3284', '63', '更新供应商预付款', '', '2017-07-02 22:11:32', '0', '更新供应商ID为  12 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3285', '63', '更新供应商预付款', '', '2017-07-02 22:11:40', '0', '更新供应商ID为  12 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3286', '63', '删除财务', '192.168.100.163', '2017-07-02 22:11:40', '0', '删除财务ID为  69 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3287', '63', '删除供应商', '192.168.100.163', '2017-07-02 22:11:46', '0', '删除供应商ID为  12,名称为  2131243成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3288', '63', '增加仓管通', '192.168.100.163', '2017-07-02 22:13:05', '0', '增加仓管通编号为  LSTH20170702221248 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3289', '63', '保存仓管通明细', '192.168.100.163', '2017-07-02 22:13:05', '0', '保存仓管通明细对应主表编号为  105 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3290', '63', '登录系统', '192.168.100.163', '2017-07-02 22:15:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3291', '63', '增加仓管通', '192.168.100.163', '2017-07-02 23:05:03', '0', '增加仓管通编号为  LSCK2017070223440 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3292', '63', '更新供应商预付款', '', '2017-07-02 23:05:03', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3293', '63', '保存仓管通明细', '192.168.100.163', '2017-07-02 23:05:03', '0', '保存仓管通明细对应主表编号为  106 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3294', '63', '更新供应商预付款', '', '2017-07-02 23:05:27', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3295', '63', '删除仓管通', '192.168.100.163', '2017-07-02 23:05:27', '0', '删除仓管通ID为  106 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3296', '63', '登录系统', '192.168.100.163', '2017-07-03 23:09:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3297', '63', '更新财务', '192.168.100.163', '2017-07-03 23:22:18', '0', '更新财务ID为  58 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3298', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:35:46', '0', '批量删除财务ID为  58,42,35,34,33,27,26,25,19,12 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3299', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:35:48', '0', '批量删除财务ID为  9,3 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3300', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:35:52', '0', '批量删除财务ID为  36,32,28,21,14 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3301', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:35:56', '0', '批量删除财务ID为  59,43,40,37,29,22,18,15 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3302', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:36:00', '0', '批量删除财务ID为  60,38,30,23,16 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3303', '63', '批量删除财务', '192.168.100.163', '2017-07-03 23:36:05', '0', '批量删除财务ID为  41,39 成功！', '批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('3304', '63', '增加财务', '192.168.100.163', '2017-07-03 23:36:36', '0', '增加财务编号为  SR20170703233610 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3305', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:36:36', '0', '保存财务明细对应主表编号为  68 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3306', '63', '增加财务', '192.168.100.163', '2017-07-03 23:37:07', '0', '增加财务编号为  ZC20170703233642 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3307', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:37:07', '0', '保存财务明细对应主表编号为  69 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3308', '63', '删除财务', '192.168.100.163', '2017-07-03 23:37:34', '0', '删除财务ID为  69 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3309', '63', '增加财务', '192.168.100.163', '2017-07-03 23:37:54', '0', '增加财务编号为  ZC20170703233735 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3310', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:37:54', '0', '保存财务明细对应主表编号为  70 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3311', '63', '增加财务', '192.168.100.163', '2017-07-03 23:39:14', '0', '增加财务编号为  SK20170703233836 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3312', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:39:14', '0', '保存财务明细对应主表编号为  71 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3313', '63', '增加财务', '192.168.100.163', '2017-07-03 23:39:26', '0', '增加财务编号为  FK20170703233852 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3314', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:39:26', '0', '保存财务明细对应主表编号为  72 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3315', '63', '增加财务', '192.168.100.163', '2017-07-03 23:40:33', '0', '增加财务编号为  ZZ2017070323403 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3316', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:40:33', '0', '保存财务明细对应主表编号为  73 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3317', '63', '删除财务', '192.168.100.163', '2017-07-03 23:48:08', '0', '删除财务ID为  73 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3318', '63', '增加财务', '192.168.100.163', '2017-07-03 23:48:41', '0', '增加财务编号为  ZZ2017070323489 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3319', '63', '保存财务明细', '192.168.100.163', '2017-07-03 23:48:41', '0', '保存财务明细对应主表编号为  74 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3320', '63', '登录系统', '192.168.100.163', '2017-07-04 00:07:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3321', '63', '增加财务', '192.168.100.163', '2017-07-04 00:07:57', '0', '增加财务编号为  FK201707040746 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3322', '63', '保存财务明细', '192.168.100.163', '2017-07-04 00:07:57', '0', '保存财务明细对应主表编号为  75 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3323', '63', '登录系统', '192.168.100.163', '2017-07-04 00:11:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3324', '63', '更新财务', '192.168.100.163', '2017-07-04 00:12:10', '0', '更新财务ID为  75 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3325', '63', '保存财务明细', '192.168.100.163', '2017-07-04 00:12:10', '0', '保存财务明细对应主表编号为  75 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3326', '63', '登录系统', '192.168.100.163', '2017-07-04 22:19:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3327', '63', '登录系统', '192.168.100.163', '2017-07-04 22:24:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3328', '63', '增加财务', '192.168.100.163', '2017-07-04 22:25:35', '0', '增加财务编号为  SR20170704222510 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3329', '63', '保存财务明细', '192.168.100.163', '2017-07-04 22:25:35', '0', '保存财务明细对应主表编号为  76 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3330', '63', '删除财务', '192.168.100.163', '2017-07-04 22:25:51', '0', '删除财务ID为  76 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3331', '63', '增加财务', '192.168.100.163', '2017-07-04 22:26:48', '0', '增加财务编号为  SR20170704222634 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3332', '63', '保存财务明细', '192.168.100.163', '2017-07-04 22:26:49', '0', '保存财务明细对应主表编号为  77 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3333', '63', '更新财务', '192.168.100.163', '2017-07-04 22:27:27', '0', '更新财务ID为  77 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3334', '63', '保存财务明细', '192.168.100.163', '2017-07-04 22:27:28', '0', '保存财务明细对应主表编号为  77 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3335', '63', '登录系统', '192.168.100.163', '2017-07-04 22:34:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3336', '63', '登录系统', '192.168.100.163', '2017-07-04 23:03:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3337', '63', '登录系统', '192.168.100.163', '2017-07-04 23:15:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3338', '63', '登录系统', '192.168.100.163', '2017-07-04 23:18:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3339', '63', '登录系统', '192.168.100.163', '2017-07-04 23:20:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3340', '63', '登录系统', '192.168.100.163', '2017-07-04 23:29:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3341', '63', '登录系统', '192.168.100.163', '2017-07-04 23:31:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3342', '63', '更新供应商预付款', '', '2017-07-04 23:34:26', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3343', '63', '更新财务', '192.168.100.163', '2017-07-04 23:34:26', '0', '更新财务ID为  67 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3344', '63', '保存财务明细', '192.168.100.163', '2017-07-04 23:34:26', '0', '保存财务明细对应主表编号为  67 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3345', '63', '更新财务', '192.168.100.163', '2017-07-04 23:35:20', '0', '更新财务ID为  61 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3346', '63', '更新供应商预付款', '', '2017-07-04 23:35:20', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3347', '63', '删除财务', '192.168.100.163', '2017-07-04 23:37:23', '0', '删除财务ID为  71 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3348', '63', '登录系统', '192.168.100.163', '2017-07-04 23:40:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3349', '63', '登录系统', '192.168.100.163', '2017-07-04 23:44:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3350', '63', '更新财务', '192.168.100.163', '2017-07-04 23:52:12', '0', '更新财务ID为  67 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('3351', '63', '更新供应商预付款', '', '2017-07-04 23:52:12', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3352', '63', '保存财务明细', '192.168.100.163', '2017-07-04 23:52:12', '0', '保存财务明细对应主表编号为  67 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3353', '63', '登录系统', '192.168.100.163', '2017-07-05 00:01:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3354', '63', '更新供应商预付款', '', '2017-07-05 00:03:31', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3355', '63', '增加财务', '192.168.100.163', '2017-07-05 00:03:31', '0', '增加财务编号为  SYF201707050257 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3356', '63', '保存财务明细', '192.168.100.163', '2017-07-05 00:03:31', '0', '保存财务明细对应主表编号为  78 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3357', '63', '更新供应商预付款', '', '2017-07-05 00:07:24', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3358', '63', '增加财务', '192.168.100.163', '2017-07-05 00:07:24', '0', '增加财务编号为  SYF20170705076 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3359', '63', '保存财务明细', '192.168.100.163', '2017-07-05 00:07:25', '0', '保存财务明细对应主表编号为  79 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3360', '63', '增加财务', '192.168.100.163', '2017-07-05 00:08:19', '0', '增加财务编号为  FK20170705088 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3361', '63', '保存财务明细', '192.168.100.163', '2017-07-05 00:08:19', '0', '保存财务明细对应主表编号为  80 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3362', '63', '增加财务', '192.168.100.163', '2017-07-05 00:08:45', '0', '增加财务编号为  SK201707050836 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3363', '63', '保存财务明细', '192.168.100.163', '2017-07-05 00:08:46', '0', '保存财务明细对应主表编号为  81 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3364', '63', '增加财务', '192.168.100.163', '2017-07-05 00:09:23', '0', '增加财务编号为  ZZ201707050910 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('3365', '63', '保存财务明细', '192.168.100.163', '2017-07-05 00:09:23', '0', '保存财务明细对应主表编号为  82 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('3366', '63', '删除财务', '192.168.100.163', '2017-07-05 00:09:43', '0', '删除财务ID为  82 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('3367', '63', '更新商品', '192.168.100.163', '2017-07-05 00:22:31', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3368', '63', '登录系统', '192.168.100.163', '2017-07-05 00:25:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3369', '63', '登录系统', '192.168.100.163', '2017-07-05 00:27:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3370', '63', '登录系统', '192.168.100.163', '2017-07-05 20:53:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3371', '63', '登录系统', '192.168.100.163', '2017-07-05 22:12:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3372', '63', '增加商品', '192.168.100.163', '2017-07-05 22:20:01', '0', '增加商品名称为  棉线 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3373', '63', '更新商品', '192.168.100.163', '2017-07-05 22:20:19', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3374', '63', '登录系统', '192.168.100.163', '2017-07-05 22:24:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3375', '63', '更新商品', '192.168.100.163', '2017-07-05 22:28:44', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3376', '63', '更新商品', '192.168.100.163', '2017-07-05 22:28:49', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3377', '63', '更新商品', '192.168.100.163', '2017-07-05 22:38:37', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3378', '63', '更新商品', '192.168.100.163', '2017-07-05 22:38:53', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3379', '63', '更新商品', '192.168.100.163', '2017-07-05 22:59:45', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3380', '63', '更新商品', '192.168.100.163', '2017-07-05 22:59:50', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3381', '63', '更新商品', '192.168.100.163', '2017-07-05 23:06:22', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3382', '63', '更新商品', '192.168.100.163', '2017-07-05 23:22:28', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3383', '63', '更新商品', '192.168.100.163', '2017-07-05 23:23:00', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3384', '63', '更新商品', '192.168.100.163', '2017-07-05 23:24:23', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3385', '63', '增加仓管通', '192.168.100.163', '2017-07-05 23:26:46', '0', '增加仓管通编号为  CGRK20170705232428 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3386', '63', '保存仓管通明细', '192.168.100.163', '2017-07-05 23:26:46', '0', '保存仓管通明细对应主表编号为  106 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3387', '63', '登录系统', '192.168.100.163', '2017-07-05 23:31:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3388', '63', '登录系统', '192.168.100.163', '2017-07-05 23:39:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3389', '63', '登录系统', '192.168.100.163', '2017-07-05 23:55:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3390', '63', '登录系统', '192.168.100.164', '2017-07-10 22:04:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3391', '63', '增加商品', '192.168.100.164', '2017-07-10 22:07:48', '0', '增加商品名称为  测试 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3392', '63', '增加仓管通', '192.168.100.164', '2017-07-10 22:15:45', '0', '增加仓管通编号为  CGRK2017071022811 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3393', '63', '保存仓管通明细', '192.168.100.164', '2017-07-10 22:15:45', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3394', '63', '更新仓管通', '192.168.100.164', '2017-07-10 22:16:05', '0', '更新仓管通ID为  107 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3395', '63', '更新仓管通', '192.168.100.164', '2017-07-10 22:16:59', '0', '更新仓管通ID为  107 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3396', '63', '保存仓管通明细', '192.168.100.164', '2017-07-10 22:17:00', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3397', '63', '更新仓管通', '192.168.100.164', '2017-07-10 22:17:14', '0', '更新仓管通ID为  107 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3398', '63', '保存仓管通明细', '192.168.100.164', '2017-07-10 22:17:14', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3399', '63', '更新商品', '192.168.100.164', '2017-07-10 22:19:24', '0', '更新商品ID为  501 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3400', '63', '增加商品类别', '192.168.100.164', '2017-07-10 22:45:43', '0', '增加商品类别名称为  花边二级 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3401', '63', '增加商品类别', '192.168.100.164', '2017-07-10 22:46:02', '0', '增加商品类别名称为  花边三级 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3402', '63', '更新商品', '192.168.100.164', '2017-07-10 22:49:59', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3403', '63', '更新商品', '192.168.100.164', '2017-07-10 22:51:50', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3404', '63', '更新商品', '192.168.100.164', '2017-07-10 22:51:57', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3405', '63', '更新商品', '192.168.100.164', '2017-07-10 22:52:02', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3406', '63', '更新商品', '192.168.100.164', '2017-07-10 22:52:07', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3407', '63', '更新商品', '192.168.100.164', '2017-07-10 22:52:09', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3408', '63', '更新商品', '192.168.100.164', '2017-07-10 22:52:31', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3409', '63', '更新商品', '192.168.100.164', '2017-07-10 22:53:05', '0', '更新商品ID为  501 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3410', '63', '登录系统', '192.168.100.164', '2017-07-10 23:00:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3411', '63', '更新商品', '192.168.100.164', '2017-07-10 23:00:48', '0', '更新商品ID为  501 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3412', '63', '更新商品', '192.168.100.164', '2017-07-10 23:00:58', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3413', '63', '增加用户', '192.168.100.164', '2017-07-10 23:35:19', '0', '增加用户名称为  3123 成功！', '增加用户成功');
INSERT INTO `jsh_log` VALUES ('3414', '63', '删除用户', '192.168.100.164', '2017-07-10 23:35:25', '0', '删除用户ID为  66 成功！', '删除用户成功');
INSERT INTO `jsh_log` VALUES ('3415', '63', '登录系统', '192.168.100.164', '2017-07-11 00:04:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3416', '63', '登录系统', '192.168.100.164', '2017-07-11 00:10:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3417', '63', '登录系统', '192.168.100.164', '2017-07-11 00:18:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3418', '63', '登录系统', '192.168.100.164', '2017-07-11 00:20:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3419', '63', '增加仓管通', '192.168.100.164', '2017-07-11 00:21:27', '0', '增加仓管通编号为  CGRK201707110216 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3420', '63', '保存仓管通明细', '192.168.100.164', '2017-07-11 00:21:27', '0', '保存仓管通明细对应主表编号为  108 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3421', '63', '登录系统', '192.168.100.164', '2017-07-11 00:24:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3422', '63', '登录系统', '192.168.100.164', '2017-07-11 00:28:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3423', '63', '登录系统', '192.168.100.163', '2017-07-11 21:45:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3424', '63', '登录系统', '192.168.100.163', '2017-07-11 21:51:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3425', '63', '登录系统', '192.168.100.163', '2017-07-11 22:34:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3426', '63', '登录系统', '192.168.100.163', '2017-07-12 20:46:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3427', '63', '登录系统', '192.168.100.163', '2017-07-12 21:32:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3428', '63', '增加功能', '192.168.100.163', '2017-07-12 21:35:58', '0', '增加功能名称为  礼品卡管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3429', '63', '更新功能', '192.168.100.163', '2017-07-12 21:36:09', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3430', '63', '更新功能', '192.168.100.163', '2017-07-12 21:40:48', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3431', '63', '更新功能', '192.168.100.163', '2017-07-12 21:40:56', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3432', '63', '更新UserBusiness', '192.168.100.163', '2017-07-12 21:41:59', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3433', '63', '更新功能', '192.168.100.163', '2017-07-12 21:42:59', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3434', '63', '增加仓库', '192.168.100.163', '2017-07-12 21:51:26', '0', '增加仓库名称为  1268200294 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3435', '63', '增加仓库', '192.168.100.163', '2017-07-12 21:51:47', '0', '增加仓库名称为  1268787965 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3436', '63', '增加仓库', '192.168.100.163', '2017-07-12 21:52:03', '0', '增加仓库名称为  1269520625 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3437', '63', '更新仓库', '192.168.100.163', '2017-07-12 21:52:35', '0', '更新仓库ID为  4 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3438', '63', '更新仓库', '192.168.100.163', '2017-07-12 21:52:39', '0', '更新仓库ID为  6 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3439', '63', '更新仓库', '192.168.100.163', '2017-07-12 21:52:44', '0', '更新仓库ID为  5 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3440', '63', '增加功能', '192.168.100.163', '2017-07-12 22:25:29', '0', '增加功能名称为  礼品卡充值 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3441', '63', '更新功能', '192.168.100.163', '2017-07-12 22:25:51', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3442', '63', '退出系统', '192.168.100.163', '2017-07-12 22:48:49', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('3443', '63', '登录系统', '192.168.100.163', '2017-07-12 22:48:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3444', '63', '登录系统', '192.168.100.163', '2017-07-12 23:34:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3445', '63', '登录系统', '192.168.100.163', '2017-07-13 00:08:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3446', '63', '登录系统', '192.168.100.163', '2017-07-13 00:21:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3447', '63', '登录系统', '192.168.100.163', '2017-07-13 20:19:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3448', '63', '增加供应商', '192.168.100.163', '2017-07-13 20:25:42', '0', '增加供应商名称为  2131 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3449', '63', '删除供应商', '192.168.100.163', '2017-07-13 20:26:00', '0', '删除供应商ID为  11,名称为  2131成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3450', '63', '更新功能', '192.168.100.163', '2017-07-13 20:53:10', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3451', '63', '更新UserBusiness', '192.168.100.163', '2017-07-13 20:54:17', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3452', '63', '增加功能', '192.168.100.163', '2017-07-13 20:57:40', '0', '增加功能名称为  礼品卡销售 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3453', '63', '更新UserBusiness', '192.168.100.163', '2017-07-13 20:59:29', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3454', '63', '更新功能', '192.168.100.163', '2017-07-13 21:02:53', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3455', '63', '更新功能', '192.168.100.163', '2017-07-13 21:02:58', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3456', '63', '更新功能', '192.168.100.163', '2017-07-14 00:30:31', '0', '更新功能ID为  22 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3457', '63', '更新功能', '192.168.100.163', '2017-07-14 00:30:36', '0', '更新功能ID为  23 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3458', '63', '登录系统', '192.168.100.163', '2017-07-14 01:13:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3459', '63', '增加仓管通', '192.168.100.163', '2017-07-14 01:19:58', '0', '增加仓管通编号为  LPCZ2017071411946 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3460', '63', '保存仓管通明细', '192.168.100.163', '2017-07-14 01:19:58', '0', '保存仓管通明细对应主表编号为  109 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3461', '63', '登录系统', '192.168.100.163', '2017-07-14 21:43:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3462', '63', '增加仓管通', '192.168.100.163', '2017-07-14 22:15:37', '0', '增加仓管通编号为  LPCZ20170714215844 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3463', '63', '保存仓管通明细', '192.168.100.163', '2017-07-14 22:15:37', '0', '保存仓管通明细对应主表编号为  110 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3464', '63', '登录系统', '192.168.100.163', '2017-07-14 23:48:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3465', '63', '增加仓管通', '192.168.100.163', '2017-07-14 23:51:20', '1', '增加仓管通编号为  LPXS20170714235110 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('3466', '63', '增加仓管通', '192.168.100.163', '2017-07-14 23:55:15', '1', '增加仓管通编号为  LPXS2017071423552 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('3467', '63', '增加仓管通', '192.168.100.163', '2017-07-15 00:00:55', '1', '增加仓管通编号为  LPXS201707150047 失败！', '增加仓管通失败');
INSERT INTO `jsh_log` VALUES ('3468', '63', '增加仓管通', '192.168.100.163', '2017-07-15 00:02:37', '0', '增加仓管通编号为  LPXS201707150222 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3469', '63', '保存仓管通明细', '192.168.100.163', '2017-07-15 00:02:37', '0', '保存仓管通明细对应主表编号为  111 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3470', '63', '更新仓管通', '192.168.100.163', '2017-07-15 00:25:12', '0', '更新仓管通ID为  111 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3471', '63', '增加仓管通', '192.168.100.163', '2017-07-15 00:26:30', '0', '增加仓管通编号为  LPXS2017071502620 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3472', '63', '保存仓管通明细', '192.168.100.163', '2017-07-15 00:26:30', '0', '保存仓管通明细对应主表编号为  112 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3473', '63', '登录系统', '192.168.1.105', '2017-07-16 10:20:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3474', '63', '增加功能', '192.168.1.105', '2017-07-16 10:31:05', '0', '增加功能名称为  礼品卡统计 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3475', '63', '更新功能', '192.168.1.105', '2017-07-16 10:31:16', '0', '更新功能ID为  216 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3476', '63', '更新UserBusiness', '192.168.1.105', '2017-07-16 10:31:36', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3477', '63', '更新功能', '192.168.1.105', '2017-07-16 10:35:28', '0', '更新功能ID为  216 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3478', '63', '登录系统', '192.168.1.105', '2017-07-16 11:59:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3479', '63', '登录系统', '192.168.1.105', '2017-07-16 13:15:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3480', '63', '更新商品', '192.168.1.105', '2017-07-16 13:17:13', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3481', '63', '更新商品', '192.168.1.105', '2017-07-16 13:17:19', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3482', '63', '登录系统', '192.168.1.105', '2017-07-16 13:22:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3483', '63', '登录系统', '192.168.1.105', '2017-07-16 13:41:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3484', '63', '登录系统', '192.168.100.163', '2017-07-16 18:36:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3485', '63', '登录系统', '192.168.100.163', '2017-07-16 18:50:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3486', '63', '登录系统', '192.168.100.163', '2017-07-16 19:35:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3487', '63', '增加仓管通', '192.168.100.163', '2017-07-16 19:40:50', '0', '增加仓管通编号为  CGRK20170716194037 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3488', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 19:40:50', '0', '保存仓管通明细对应主表编号为  113 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3489', '63', '增加仓管通', '192.168.100.163', '2017-07-16 19:57:43', '0', '增加仓管通编号为  LPCZ20170716195720 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3490', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 19:57:43', '0', '保存仓管通明细对应主表编号为  114 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3491', '63', '登录系统', '192.168.100.163', '2017-07-16 20:11:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3492', '63', '登录系统', '192.168.100.163', '2017-07-16 20:14:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3493', '63', '删除仓管通', '192.168.100.163', '2017-07-16 20:23:49', '0', '删除仓管通ID为  63 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3494', '63', '删除仓管通', '192.168.100.163', '2017-07-16 20:23:58', '0', '删除仓管通ID为  109 成功！', '删除仓管通成功');
INSERT INTO `jsh_log` VALUES ('3495', '63', '更新仓管通', '192.168.100.163', '2017-07-16 20:25:05', '0', '更新仓管通ID为  110 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3496', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 20:25:05', '0', '保存仓管通明细对应主表编号为  110 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3497', '63', '更新仓管通', '192.168.100.163', '2017-07-16 20:25:18', '0', '更新仓管通ID为  110 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3498', '63', '更新仓管通', '192.168.100.163', '2017-07-16 20:25:39', '0', '更新仓管通ID为  114 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3499', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 20:25:39', '0', '保存仓管通明细对应主表编号为  114 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3500', '63', '更新仓管通', '192.168.100.163', '2017-07-16 20:30:14', '0', '更新仓管通ID为  114 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3501', '63', '增加仓管通', '192.168.100.163', '2017-07-16 20:30:39', '0', '增加仓管通编号为  LPCZ20170716203027 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3502', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 20:30:39', '0', '保存仓管通明细对应主表编号为  115 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3503', '63', '登录系统', '192.168.100.163', '2017-07-16 20:40:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3504', '63', '登录系统', '192.168.100.163', '2017-07-16 20:53:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3505', '63', '登录系统', '192.168.100.163', '2017-07-16 21:54:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3506', '63', '更新商品', '192.168.100.163', '2017-07-16 21:56:17', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3507', '63', '更新商品', '192.168.100.163', '2017-07-16 21:56:27', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3508', '63', '增加仓管通', '192.168.100.163', '2017-07-16 22:21:10', '0', '增加仓管通编号为  LPXS20170716222058 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3509', '63', '保存仓管通明细', '192.168.100.163', '2017-07-16 22:21:11', '0', '保存仓管通明细对应主表编号为  116 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3510', '63', '更新供应商', '192.168.100.163', '2017-07-16 22:46:14', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3511', '63', '登录系统', '192.168.100.163', '2017-07-17 00:09:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3512', '63', '登录系统', '192.168.100.163', '2017-07-17 00:21:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3513', '63', '增加仓管通', '192.168.100.163', '2017-07-17 00:25:20', '0', '增加仓管通编号为  LPXS201707170259 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3514', '63', '保存仓管通明细', '192.168.100.163', '2017-07-17 00:25:21', '0', '保存仓管通明细对应主表编号为  117 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3515', '63', '更新仓管通', '192.168.100.163', '2017-07-17 00:26:20', '0', '更新仓管通ID为  117 成功！', '更新仓管通成功');
INSERT INTO `jsh_log` VALUES ('3516', '63', '保存仓管通明细', '192.168.100.163', '2017-07-17 00:26:21', '0', '保存仓管通明细对应主表编号为  117 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3517', '63', '增加仓管通', '192.168.100.163', '2017-07-17 00:28:38', '0', '增加仓管通编号为  LSCK2017071702826 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3518', '63', '更新供应商预付款', '', '2017-07-17 00:28:38', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3519', '63', '保存仓管通明细', '192.168.100.163', '2017-07-17 00:28:38', '0', '保存仓管通明细对应主表编号为  118 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3520', '63', '增加仓管通', '192.168.100.163', '2017-07-17 00:28:58', '0', '增加仓管通编号为  XSCK2017071702842 成功！', '增加仓管通成功');
INSERT INTO `jsh_log` VALUES ('3521', '63', '保存仓管通明细', '192.168.100.163', '2017-07-17 00:28:58', '0', '保存仓管通明细对应主表编号为  119 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3522', '63', '登录系统', '192.168.100.163', '2017-07-17 00:46:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3523', '63', '登录系统', '192.168.100.163', '2017-07-17 22:33:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3524', '63', '退出系统', '192.168.100.163', '2017-07-17 22:33:49', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('3525', '63', '登录系统', '192.168.100.163', '2017-07-17 22:33:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3526', '63', '登录系统', '192.168.100.163', '2017-07-17 22:52:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3527', '63', '更新功能', '192.168.100.163', '2017-07-17 22:59:47', '0', '更新功能ID为  25 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3528', '63', '更新功能', '192.168.100.163', '2017-07-17 23:00:56', '0', '更新功能ID为  25 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3529', '63', '增加功能', '192.168.100.163', '2017-07-17 23:02:24', '0', '增加功能名称为  客户管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3530', '63', '增加功能', '192.168.100.163', '2017-07-17 23:03:09', '0', '增加功能名称为  会员信息 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3531', '63', '更新功能', '192.168.100.163', '2017-07-17 23:03:35', '0', '更新功能ID为  217 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3532', '63', '更新UserBusiness', '192.168.100.163', '2017-07-17 23:03:51', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3533', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:54:58', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3534', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:06', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3535', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:15', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3536', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:17', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3537', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:23', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3538', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:30', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3539', '63', '更新供应商', '192.168.100.163', '2017-07-17 23:55:35', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3540', '63', '增加供应商', '192.168.100.163', '2017-07-18 00:22:21', '0', '增加供应商名称为  2342 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3541', '63', '更新供应商', '192.168.100.163', '2017-07-18 00:22:32', '0', '更新供应商ID为  11 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3542', '63', '删除供应商', '192.168.100.163', '2017-07-18 00:22:35', '0', '删除供应商ID为  11,名称为  2342111成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3543', '63', '更新应用', '192.168.100.163', '2017-07-18 00:27:29', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3544', '63', '更新应用', '192.168.100.163', '2017-07-18 00:28:40', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3545', '63', '更新应用', '192.168.100.163', '2017-07-18 00:28:44', '0', '更新应用ID为  21 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3546', '63', '更新应用', '192.168.100.163', '2017-07-18 00:28:51', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3547', '63', '更新应用', '192.168.100.163', '2017-07-18 00:28:55', '0', '更新应用ID为  7 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3548', '63', '更新应用', '192.168.100.163', '2017-07-18 00:29:00', '0', '更新应用ID为  20 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3549', '63', '更新应用', '192.168.100.163', '2017-07-18 00:29:07', '0', '更新应用ID为  20 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3550', '63', '更新应用', '192.168.100.163', '2017-07-18 00:29:15', '0', '更新应用ID为  3 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3551', '63', '更新应用', '192.168.100.163', '2017-07-18 00:29:20', '0', '更新应用ID为  20 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3552', '63', '登录系统', '192.168.100.163', '2017-07-18 21:01:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3553', '63', '登录系统', '192.168.100.163', '2017-07-18 22:02:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3554', '63', '登录系统', '192.168.100.163', '2017-07-18 22:17:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3555', '63', '增加供应商', '192.168.100.163', '2017-07-18 22:26:05', '0', '增加供应商名称为  321312 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3556', '63', '删除供应商', '192.168.100.163', '2017-07-18 22:26:09', '0', '删除供应商ID为  11,名称为  321312成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3557', '63', '增加供应商', '192.168.100.163', '2017-07-18 22:57:08', '0', '增加供应商名称为  4123 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3558', '63', '删除供应商', '192.168.100.163', '2017-07-18 22:58:44', '0', '删除供应商ID为  12,名称为  4123成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3559', '63', '增加供应商', '192.168.100.163', '2017-07-18 22:59:13', '0', '增加供应商名称为  234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3560', '63', '增加供应商', '', '2017-07-18 23:00:14', '0', '增加供应商名称为  2423423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3561', '63', '增加供应商', '', '2017-07-18 23:00:22', '0', '增加供应商名称为  23423423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3562', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:00:31', '0', '删除供应商ID为  14,名称为  2423423成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3563', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:00:33', '0', '删除供应商ID为  15,名称为  23423423成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3564', '63', '增加供应商', '', '2017-07-18 23:00:51', '0', '增加供应商名称为  2342123 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3565', '63', '增加供应商', '', '2017-07-18 23:01:54', '0', '增加供应商名称为  123 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3566', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:06:23', '0', '增加供应商名称为  342 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3567', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:06:56', '0', '删除供应商ID为  17,名称为  123成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3568', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:06:59', '0', '删除供应商ID为  19,名称为  342成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3569', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:07:14', '0', '增加供应商名称为  23423423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3570', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:10:50', '0', '增加供应商名称为  erqwer 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3571', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:12:02', '0', '增加供应商名称为  2235254 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3572', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:12:10', '0', '删除供应商ID为  22,名称为  2235254成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3573', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:12:12', '0', '删除供应商ID为  21,名称为  erqwer成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3574', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:12:17', '0', '增加供应商名称为  234345234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3575', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:12:59', '0', '增加供应商名称为  23234234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3576', '63', '增加供应商', '', '2017-07-18 23:13:18', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3577', '63', '登录系统', '192.168.100.163', '2017-07-18 23:16:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3578', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:16:49', '0', '增加供应商名称为  12341234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3579', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:19:48', '0', '增加供应商名称为  21341231234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3580', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:19:55', '0', '删除供应商ID为  27,名称为  21341231234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3581', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:19:56', '0', '删除供应商ID为  26,名称为  12341234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3582', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:19:58', '0', '删除供应商ID为  24,名称为  23234234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3583', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:20:40', '0', '增加供应商名称为  42314 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3584', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:21:11', '0', '增加供应商名称为  1234123412 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3585', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:22:00', '0', '增加供应商名称为  213424 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3586', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:22:11', '0', '删除供应商ID为  30,名称为  213424成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3587', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:24:06', '0', '增加供应商名称为  werwer 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3588', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:24:17', '0', '删除供应商ID为  31,名称为  werwer成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3589', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:24:49', '0', '增加供应商名称为  123412 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3590', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:28:05', '0', '增加供应商名称为  32423423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3591', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:30:37', '0', '增加供应商名称为  asdfasdf 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3592', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:32:52', '0', '增加供应商名称为  432432345 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3593', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:32:55', '0', '删除供应商ID为  35,名称为  432432345成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3594', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:32:59', '0', '增加供应商名称为  3452345234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3595', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:33:02', '0', '删除供应商ID为  36,名称为  3452345234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3596', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:07', '0', '删除供应商ID为  34,名称为  asdfasdf成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3597', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:08', '0', '删除供应商ID为  33,名称为  32423423成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3598', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:09', '0', '删除供应商ID为  32,名称为  123412成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3599', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:09', '0', '删除供应商ID为  29,名称为  1234123412成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3600', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:10', '0', '删除供应商ID为  28,名称为  42314成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3601', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:11', '0', '删除供应商ID为  23,名称为  234345234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3602', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:34:11', '0', '删除供应商ID为  20,名称为  23423423成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3603', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:34:43', '0', '增加供应商名称为  aaaaaaa 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3604', '63', '登录系统', '192.168.100.163', '2017-07-18 23:38:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3605', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:38:28', '0', '增加供应商名称为  bbbb 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3606', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:38:32', '0', '删除供应商ID为  38,名称为  bbbb成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3607', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:38:53', '0', '增加供应商名称为  cccc 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3608', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:39:01', '0', '删除供应商ID为  39,名称为  cccc成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3609', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:48:54', '0', '增加供应商名称为  2341234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3610', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:48:56', '0', '删除供应商ID为  40,名称为  2341234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3611', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:49:00', '0', '增加供应商名称为  23412345324534 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3612', '63', '增加供应商', '192.168.100.163', '2017-07-18 23:49:17', '0', '增加供应商名称为  etrwerwqe 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3613', '63', '删除供应商', '192.168.100.163', '2017-07-18 23:52:47', '0', '删除供应商ID为  42,名称为  etrwerwqe成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3614', '63', '增加供应商', '192.168.100.163', '2017-07-19 00:05:42', '0', '增加供应商名称为  432535345 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3615', '63', '增加供应商', '192.168.100.163', '2017-07-19 00:13:40', '0', '增加供应商名称为  南通某某企业 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3616', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:34:47', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3617', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:41:26', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3618', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:44:24', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3619', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:45:40', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3620', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:46:02', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3621', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:46:26', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3622', '63', '登录系统', '192.168.100.163', '2017-07-19 00:49:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3623', '63', '登录系统', '192.168.100.163', '2017-07-19 00:52:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3624', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:52:55', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3625', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:53:03', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3626', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:53:33', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3627', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:53:41', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3628', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:53:50', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3629', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:53:58', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3630', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:54:12', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3631', '63', '批量删除供应商', '192.168.100.163', '2017-07-19 00:54:54', '0', '批量删除供应商ID为  43 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3632', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:57:41', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3633', '63', '更新供应商', '192.168.100.163', '2017-07-19 00:57:46', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3634', '63', '增加供应商', '192.168.100.163', '2017-07-19 01:00:27', '0', '增加供应商名称为  234234234234 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3635', '63', '删除供应商', '192.168.100.163', '2017-07-19 01:00:30', '0', '删除供应商ID为  45,名称为  234234234234成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3636', '63', '登录系统', '192.168.100.163', '2017-07-19 01:24:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3637', '63', '登录系统', '192.168.100.163', '2017-07-19 20:39:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3638', '63', '登录系统', '192.168.100.163', '2017-07-19 21:13:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3639', '63', '登录系统', '192.168.100.163', '2017-07-19 21:42:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3640', '63', '登录系统', '192.168.100.163', '2017-07-19 21:45:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3641', '63', '增加UserBusiness', '192.168.100.163', '2017-07-19 21:48:14', '0', '增加UserBusiness为  UserCustomer 成功！', '增加UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3642', '63', '增加UserBusiness', '192.168.100.163', '2017-07-19 21:49:16', '0', '增加UserBusiness为  UserCustomer 成功！', '增加UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3643', '63', '增加UserBusiness', '192.168.100.163', '2017-07-19 21:50:40', '0', '增加UserBusiness为  UserCustomer 成功！', '增加UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3644', '63', '登录系统', '192.168.100.163', '2017-07-19 22:07:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3645', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:18:49', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3646', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:19:01', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3647', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:19:07', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3648', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:19:43', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3649', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:19:57', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3650', '63', '登录系统', '192.168.100.163', '2017-07-19 22:37:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3651', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:44:05', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3652', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:50:04', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3653', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:50:51', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3654', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:51:16', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3655', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:51:25', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3656', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:53:26', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3657', '63', '更新供应商', '192.168.100.163', '2017-07-19 22:53:34', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3658', '63', '登录系统', '192.168.100.163', '2017-07-19 23:26:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3659', '63', '更新UserBusiness', '192.168.100.163', '2017-07-19 23:27:55', '0', '更新UserBusiness的ID为  27 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3660', '63', '登录系统', '192.168.100.163', '2017-07-19 23:35:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3661', '63', '更新UserBusiness', '192.168.100.163', '2017-07-19 23:41:34', '0', '更新UserBusiness的ID为  27 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3662', '63', '登录系统', '192.168.100.163', '2017-07-19 23:47:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3663', '63', '更新UserBusiness', '192.168.100.163', '2017-07-19 23:48:01', '0', '更新UserBusiness的ID为  27 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3664', '63', '登录系统', '192.168.100.163', '2017-07-20 00:18:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3665', '63', '登录系统', '192.168.100.163', '2017-07-20 21:05:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3666', '63', '更新功能', '192.168.100.163', '2017-07-20 21:06:18', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3667', '63', '更新功能', '192.168.100.163', '2017-07-20 21:06:28', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3668', '63', '更新功能', '192.168.100.163', '2017-07-20 21:06:34', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3669', '63', '更新功能', '192.168.100.163', '2017-07-20 21:06:41', '0', '更新功能ID为  216 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3670', '63', '更新功能', '192.168.100.163', '2017-07-20 21:07:54', '0', '更新功能ID为  16 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3671', '63', '更新功能', '192.168.100.163', '2017-07-20 21:08:12', '0', '更新功能ID为  16 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3672', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:46:14', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3673', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:46:39', '0', '更新仓库ID为  1 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3674', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:49:38', '0', '更新仓库ID为  1 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3675', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:49:48', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3676', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:49:56', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3677', '63', '更新仓库', '192.168.100.163', '2017-07-20 22:54:47', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3678', '63', '退出系统', '192.168.100.163', '2017-07-20 23:32:43', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('3679', '63', '登录系统', '192.168.100.163', '2017-07-20 23:32:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3680', '63', '登录系统', '192.168.100.163', '2017-07-20 23:41:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3681', '63', '更新仓库', '192.168.100.163', '2017-07-20 23:56:18', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3682', '63', '登录系统', '192.168.100.163', '2017-07-21 00:01:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3683', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:02:16', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3684', '63', '增加仓库', '192.168.100.163', '2017-07-21 00:03:02', '0', '增加仓库名称为  42134 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3685', '63', '删除仓库', '192.168.100.163', '2017-07-21 00:03:06', '0', '删除仓库ID为  7 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('3686', '63', '登录系统', '192.168.100.163', '2017-07-21 00:05:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3687', '63', '登录系统', '192.168.100.163', '2017-07-21 00:21:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3688', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:21:56', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3689', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:22:00', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3690', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:22:07', '0', '更新仓库ID为  2 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3691', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:22:18', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3692', '63', '增加仓库', '192.168.100.163', '2017-07-21 00:22:32', '0', '增加仓库名称为  11 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3693', '63', '更新仓库', '192.168.100.163', '2017-07-21 00:22:37', '0', '更新仓库ID为  8 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('3694', '63', '删除仓库', '192.168.100.163', '2017-07-21 00:22:41', '0', '删除仓库ID为  8 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('3695', '63', '登录系统', '192.168.100.163', '2017-07-21 21:27:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3696', '63', '登录系统', '192.168.100.163', '2017-07-21 21:41:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3697', '63', '登录系统', '192.168.100.163', '2017-07-21 21:51:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3698', '63', '登录系统', '192.168.100.163', '2017-07-21 21:53:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3699', '63', '登录系统', '192.168.100.163', '2017-07-21 22:36:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3700', '63', '登录系统', '192.168.100.163', '2017-07-21 22:39:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3701', '63', '登录系统', '192.168.100.163', '2017-07-21 22:41:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3702', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:41:51', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3703', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:42:26', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3704', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:43:28', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3705', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:45:11', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3706', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:45:20', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3707', '63', '登录系统', '192.168.100.163', '2017-07-21 22:48:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3708', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:49:50', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3709', '63', '登录系统', '192.168.100.163', '2017-07-21 22:54:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3710', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:54:16', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3711', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:54:19', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3712', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:55:16', '0', '批量删除供应商ID为  44 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3713', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:55:20', '0', '批量删除供应商ID为  41,37 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3714', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:55:25', '0', '批量删除供应商ID为  41,4 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3715', '63', '批量删除供应商', '192.168.100.163', '2017-07-21 22:55:30', '0', '批量删除供应商ID为  4,1 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3716', '63', '登录系统', '192.168.100.163', '2017-07-22 11:01:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3717', '63', '更新供应商', '192.168.100.163', '2017-07-22 11:03:49', '0', '更新供应商ID为  44 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3718', '63', '登录系统', '192.168.100.163', '2017-07-22 12:57:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3719', '63', '登录系统', '192.168.100.163', '2017-07-22 15:39:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3720', '63', '登录系统', '192.168.100.163', '2017-07-22 15:59:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3721', '63', '登录系统', '192.168.100.163', '2017-07-22 16:03:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3722', '63', '登录系统', '192.168.100.163', '2017-07-22 16:15:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3723', '63', '登录系统', '192.168.100.163', '2017-07-22 16:54:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3724', '63', '登录系统', '192.168.100.163', '2017-07-22 16:55:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3725', '63', '登录系统', '192.168.100.163', '2017-07-22 17:01:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3726', '63', '登录系统', '192.168.100.163', '2017-07-22 17:05:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3727', '63', '登录系统', '192.168.100.163', '2017-07-22 17:14:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3728', '63', '登录系统', '192.168.100.163', '2017-07-22 17:16:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3729', '63', '登录系统', '192.168.100.163', '2017-07-22 17:21:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3730', '63', '登录系统', '192.168.100.163', '2017-07-22 17:23:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3731', '63', '登录系统', '192.168.100.163', '2017-07-22 17:25:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3732', '63', '登录系统', '192.168.100.163', '2017-07-22 17:35:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3733', '63', '登录系统', '192.168.100.163', '2017-07-22 17:42:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3734', '63', '登录系统', '192.168.100.163', '2017-07-22 17:57:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3735', '63', '更新供应商', '192.168.100.163', '2017-07-22 17:59:23', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3736', '63', '增加功能', '192.168.100.163', '2017-07-22 18:25:53', '0', '增加功能名称为  资产管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('3737', '63', '更新UserBusiness', '192.168.100.163', '2017-07-22 18:26:09', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3738', '63', '删除资产', '192.168.100.163', '2017-07-22 18:41:27', '0', '删除资产ID为  2 成功！', '删除资产成功');
INSERT INTO `jsh_log` VALUES ('3739', '63', '登录系统', '192.168.100.163', '2017-07-22 23:49:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3740', '63', '登录系统', '192.168.100.163', '2017-07-22 23:52:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3741', '63', '登录系统', '192.168.100.163', '2017-07-23 00:03:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3742', '63', '登录系统', '192.168.100.163', '2017-07-23 00:09:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3743', '63', '登录系统', '192.168.100.163', '2017-07-23 00:14:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3744', '63', '登录系统', '192.168.100.163', '2017-07-23 00:39:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3745', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 00:40:13', '0', '批量删除供应商ID为  45 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3746', '63', '登录系统', '192.168.100.163', '2017-07-23 10:50:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3747', '63', '删除供应商', '192.168.100.163', '2017-07-23 10:50:31', '0', '删除供应商ID为  44,名称为  南通某某企业成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3748', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 10:51:11', '0', '批量删除供应商ID为  4,1 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3749', '63', '登录系统', '192.168.100.163', '2017-07-23 10:58:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3750', '63', '登录系统', '192.168.100.163', '2017-07-23 11:49:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3751', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 12:30:23', '0', '批量删除供应商ID为  48,47 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3752', '63', '登录系统', '192.168.100.163', '2017-07-23 12:35:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3753', '63', '登录系统', '192.168.100.163', '2017-07-23 14:03:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3754', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:11:33', '0', '删除供应商ID为  43,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3755', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:11:36', '0', '删除供应商ID为  42,名称为  南通广通控股成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3756', '63', '登录系统', '192.168.100.163', '2017-07-23 14:23:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3757', '63', '登录系统', '192.168.100.163', '2017-07-23 14:31:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3758', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:36:11', '0', '删除供应商ID为  45,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3759', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:36:13', '0', '删除供应商ID为  44,名称为  南通广通控股成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3760', '63', '登录系统', '192.168.100.163', '2017-07-23 14:42:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3761', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:42:10', '0', '删除供应商ID为  46,名称为  南通广通控股成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3762', '63', '登录系统', '192.168.100.163', '2017-07-23 14:56:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3763', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:56:38', '0', '删除供应商ID为  49,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3764', '63', '删除供应商', '192.168.100.163', '2017-07-23 14:56:40', '0', '删除供应商ID为  48,名称为  南通广通控股成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3765', '63', '登录系统', '192.168.100.163', '2017-07-23 15:01:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3766', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:01:37', '0', '删除供应商ID为  50,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3767', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:02:58', '0', '删除供应商ID为  51,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3768', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:04:05', '0', '删除供应商ID为  52,名称为  南通智达建筑成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3769', '63', '登录系统', '192.168.100.163', '2017-07-23 15:21:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3770', '63', '增加供应商', '192.168.100.163', '2017-07-23 15:27:51', '0', '增加供应商名称为  2134213 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3771', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:28:19', '0', '删除供应商ID为  53,名称为  2134213成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3772', '63', '增加供应商', '192.168.100.163', '2017-07-23 15:28:26', '0', '增加供应商名称为  123 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3773', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:29:28', '0', '删除供应商ID为  54,名称为  123成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3774', '63', '增加供应商', '192.168.100.163', '2017-07-23 15:29:36', '0', '增加供应商名称为  123123 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3775', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:31:18', '0', '删除供应商ID为  55,名称为  123123成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3776', '63', '增加供应商', '192.168.100.163', '2017-07-23 15:31:25', '0', '增加供应商名称为  3223423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3777', '63', '登录系统', '192.168.100.163', '2017-07-23 15:34:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3778', '63', '增加供应商', '192.168.100.163', '2017-07-23 15:34:19', '0', '增加供应商名称为  3423423 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('3779', '63', '更新供应商', '192.168.100.163', '2017-07-23 15:34:24', '0', '更新供应商ID为  57 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3780', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:34:28', '0', '批量删除供应商ID为  57 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3781', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:34:31', '0', '批量删除供应商ID为  57 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3782', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:34:35', '0', '删除供应商ID为  57,名称为  3423423aa成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3783', '63', '删除供应商', '192.168.100.163', '2017-07-23 15:34:39', '0', '删除供应商ID为  56,名称为  3223423成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3784', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:39:10', '0', '批量删除供应商ID为  6 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3785', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:39:18', '0', '批量删除供应商ID为  41 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3786', '63', '登录系统', '192.168.100.163', '2017-07-23 15:43:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3787', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:44:26', '0', '批量删除供应商ID为  4 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3788', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:45:10', '0', '批量删除供应商ID为  4 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3789', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:45:13', '0', '批量删除供应商ID为  1 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3790', '63', '登录系统', '192.168.100.163', '2017-07-23 15:51:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3791', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:52:36', '0', '批量删除供应商ID为  5 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3792', '63', '批量删除供应商', '192.168.100.163', '2017-07-23 15:56:09', '0', '批量删除供应商ID为  5 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3793', '63', '增加仓库', '192.168.100.163', '2017-07-23 16:03:35', '0', '增加仓库名称为  rrrr 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('3794', '63', '删除仓库', '192.168.100.163', '2017-07-23 16:03:38', '0', '删除仓库ID为  7 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('3795', '63', '登录系统', '192.168.100.163', '2017-07-23 17:12:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3796', '63', '登录系统', '192.168.100.163', '2017-07-23 17:40:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3797', '63', '登录系统', '192.168.100.163', '2017-07-23 17:59:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3798', '63', '登录系统', '192.168.100.163', '2017-07-23 18:03:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3799', '63', '更新商品', '192.168.100.163', '2017-07-23 18:06:40', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3800', '63', '更新商品', '192.168.100.163', '2017-07-23 18:17:39', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3801', '63', '更新商品', '192.168.100.163', '2017-07-23 18:18:01', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3802', '63', '更新商品', '192.168.100.163', '2017-07-23 18:30:06', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3803', '63', '更新商品', '192.168.100.163', '2017-07-23 18:35:47', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3804', '63', '更新商品', '192.168.100.163', '2017-07-23 18:35:56', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3805', '63', '更新商品', '192.168.100.163', '2017-07-23 18:36:03', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3806', '63', '更新商品', '192.168.100.163', '2017-07-23 18:36:09', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3807', '63', '登录系统', '192.168.100.163', '2017-07-23 18:51:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3808', '63', '登录系统', '192.168.100.163', '2017-07-23 18:54:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3809', '63', '更新商品', '192.168.100.163', '2017-07-23 19:15:51', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3810', '63', '更新商品', '192.168.100.163', '2017-07-23 19:16:08', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3811', '63', '更新商品', '192.168.100.163', '2017-07-23 19:16:35', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3812', '63', '更新商品', '192.168.100.163', '2017-07-23 19:17:02', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3813', '63', '更新商品', '192.168.100.163', '2017-07-23 19:21:26', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3814', '63', '更新商品', '192.168.100.163', '2017-07-23 19:21:36', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3815', '63', '更新商品', '192.168.100.163', '2017-07-23 19:21:42', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3816', '63', '增加商品类别', '192.168.100.163', '2017-07-23 20:28:00', '0', '增加商品类别名称为  花边二级B 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3817', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:28:20', '0', '更新商品类别ID为  2 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3818', '63', '增加商品类别', '192.168.100.163', '2017-07-23 20:28:39', '0', '增加商品类别名称为  花边一级B 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3819', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:29:01', '0', '更新商品类别ID为  6 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3820', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:29:15', '0', '更新商品类别ID为  6 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3821', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:30:16', '0', '更新商品类别ID为  6 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3822', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:31:11', '0', '更新商品类别ID为  7 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3823', '63', '更新商品类别', '192.168.100.163', '2017-07-23 20:31:16', '0', '更新商品类别ID为  7 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('3824', '63', '增加商品类别', '192.168.100.163', '2017-07-23 20:31:32', '0', '增加商品类别名称为  花边三级B 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3825', '63', '更新商品', '192.168.100.163', '2017-07-23 20:36:03', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3826', '63', '更新商品', '192.168.100.163', '2017-07-23 21:52:43', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3827', '63', '更新商品', '192.168.100.163', '2017-07-23 21:53:05', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3828', '63', '更新商品', '192.168.100.163', '2017-07-23 21:53:22', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3829', '63', '更新商品', '192.168.100.163', '2017-07-23 21:54:33', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3830', '63', '更新商品', '192.168.100.163', '2017-07-23 21:55:11', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3831', '63', '更新商品', '192.168.100.163', '2017-07-23 21:55:48', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3832', '63', '更新商品', '192.168.100.163', '2017-07-23 21:57:09', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3833', '63', '更新商品', '192.168.100.163', '2017-07-23 21:57:20', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3834', '63', '更新商品', '192.168.100.163', '2017-07-23 21:58:07', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3835', '63', '更新商品', '192.168.100.163', '2017-07-23 21:58:15', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3836', '63', '更新商品', '192.168.100.163', '2017-07-23 22:00:21', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3837', '63', '更新商品', '192.168.100.163', '2017-07-23 22:01:23', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3838', '63', '更新商品', '192.168.100.163', '2017-07-23 22:01:36', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3839', '63', '更新商品', '192.168.100.163', '2017-07-23 22:01:56', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3840', '63', '更新商品', '192.168.100.163', '2017-07-23 22:03:04', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3841', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:16', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3842', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:36', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3843', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:44', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3844', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:49', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3845', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:53', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3846', '63', '更新商品', '192.168.100.163', '2017-07-23 22:04:58', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3847', '63', '更新商品', '192.168.100.163', '2017-07-23 22:05:49', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3848', '63', '更新商品', '192.168.100.163', '2017-07-23 22:05:55', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3849', '63', '更新商品', '192.168.100.163', '2017-07-23 22:06:01', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3850', '63', '更新商品', '192.168.100.163', '2017-07-23 22:06:10', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3851', '63', '更新商品', '192.168.100.163', '2017-07-23 22:06:15', '0', '更新商品ID为  501 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3852', '63', '增加商品类别', '192.168.100.163', '2017-07-23 22:08:37', '0', '增加商品类别名称为  aaa 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3853', '63', '删除商品类别', '192.168.100.163', '2017-07-23 22:08:41', '0', '删除商品类别ID为  11 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('3854', '63', '增加商品类别', '192.168.100.163', '2017-07-23 22:09:01', '0', '增加商品类别名称为  aaaa 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3855', '63', '删除商品类别', '192.168.100.163', '2017-07-23 22:09:07', '0', '删除商品类别ID为  12 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('3856', '63', '增加商品类别', '192.168.100.163', '2017-07-23 22:10:37', '0', '增加商品类别名称为  bbbb 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3857', '63', '删除商品类别', '192.168.100.163', '2017-07-23 22:10:59', '0', '删除商品类别ID为  13 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('3858', '63', '增加商品类别', '192.168.100.163', '2017-07-23 22:11:14', '0', '增加商品类别名称为  4444 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('3859', '63', '删除商品类别', '192.168.100.163', '2017-07-23 22:11:22', '0', '删除商品类别ID为  14 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('3860', '63', '更新商品', '192.168.100.163', '2017-07-23 22:11:57', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3861', '63', '登录系统', '192.168.100.163', '2017-07-23 22:33:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3862', '63', '登录系统', '192.168.100.163', '2017-07-23 22:39:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3863', '63', '登录系统', '192.168.100.163', '2017-07-23 22:54:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3864', '63', '登录系统', '192.168.100.163', '2017-07-23 23:01:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3865', '63', '登录系统', '192.168.100.163', '2017-07-23 23:04:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3866', '63', '增加商品', '192.168.100.163', '2017-07-23 23:17:17', '0', '增加商品名称为  432134 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3867', '63', '更新商品', '192.168.100.163', '2017-07-23 23:38:02', '0', '更新商品ID为  502 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3868', '63', '更新商品', '192.168.100.163', '2017-07-23 23:38:12', '0', '更新商品ID为  502 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3869', '63', '更新商品', '192.168.100.163', '2017-07-23 23:38:18', '0', '更新商品ID为  502 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('3870', '63', '增加商品', '192.168.100.163', '2017-07-23 23:44:56', '0', '增加商品名称为  棉线 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3871', '63', '批量删除商品', '192.168.100.163', '2017-07-23 23:45:00', '1', '批量删除商品ID为  485 失败！', '批量删除商品失败');
INSERT INTO `jsh_log` VALUES ('3872', '63', '批量删除商品', '192.168.100.163', '2017-07-23 23:45:12', '0', '批量删除商品ID为  503 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('3873', '63', '批量删除商品', '192.168.100.163', '2017-07-23 23:45:19', '0', '批量删除商品ID为  502 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('3874', '63', '批量删除商品', '192.168.100.163', '2017-07-23 23:45:23', '1', '批量删除商品ID为  485 失败！', '批量删除商品失败');
INSERT INTO `jsh_log` VALUES ('3875', '63', '删除结算账户', '192.168.100.163', '2017-07-23 23:45:52', '1', '删除结算账户ID为  9,名称为  流动总账失败！', '删除结算账户失败');
INSERT INTO `jsh_log` VALUES ('3876', '63', '登录系统', '192.168.100.163', '2017-07-24 21:11:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3877', '63', '登录系统', '192.168.100.163', '2017-07-24 21:21:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3878', '63', '登录系统', '192.168.100.163', '2017-07-24 21:30:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3879', '63', '登录系统', '192.168.100.163', '2017-07-24 21:35:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3880', '63', '登录系统', '192.168.100.163', '2017-07-24 21:39:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3881', '63', '登录系统', '192.168.100.163', '2017-07-24 21:41:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3882', '63', '登录系统', '192.168.100.163', '2017-07-24 21:43:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3883', '63', '登录系统', '192.168.100.163', '2017-07-24 21:46:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3884', '63', '登录系统', '192.168.100.163', '2017-07-24 21:49:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3885', '63', '登录系统', '192.168.100.163', '2017-07-24 21:52:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3886', '63', '登录系统', '192.168.100.163', '2017-07-24 21:59:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3887', '63', '登录系统', '192.168.100.163', '2017-07-24 22:01:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3888', '63', '登录系统', '192.168.100.163', '2017-07-24 22:31:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3889', '63', '登录系统', '192.168.100.163', '2017-07-24 22:36:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3890', '63', '删除供应商', '192.168.100.163', '2017-07-24 22:38:40', '0', '删除供应商ID为  42,名称为  666666666成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3891', '63', '登录系统', '192.168.100.163', '2017-07-24 22:46:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3892', '63', '删除供应商', '192.168.100.163', '2017-07-24 22:46:41', '0', '删除供应商ID为  43,名称为  666666666成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3893', '63', '登录系统', '192.168.100.163', '2017-07-24 22:49:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3894', '63', '删除供应商', '192.168.100.163', '2017-07-24 22:54:45', '0', '删除供应商ID为  44,名称为  666666666成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('3895', '63', '登录系统', '192.168.100.163', '2017-07-24 23:48:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3896', '63', '登录系统', '192.168.100.163', '2017-07-25 20:34:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3897', '63', '登录系统', '192.168.100.163', '2017-07-25 22:26:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3898', '63', '更新应用', '192.168.100.163', '2017-07-25 22:38:46', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3899', '63', '更新应用', '192.168.100.163', '2017-07-25 22:39:19', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3900', '63', '更新应用', '192.168.100.163', '2017-07-25 22:39:52', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3901', '63', '更新应用', '192.168.100.163', '2017-07-25 22:43:45', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3902', '63', '更新应用', '192.168.100.163', '2017-07-25 22:45:48', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3903', '63', '更新应用', '192.168.100.163', '2017-07-25 22:46:34', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('3904', '63', '登录系统', '192.168.100.163', '2017-07-25 22:54:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3905', '63', '登录系统', '192.168.100.163', '2017-07-25 23:00:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3906', '63', '增加商品', '192.168.100.163', '2017-07-25 23:01:07', '0', '增加商品名称为  3123 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('3907', '63', '批量删除商品', '192.168.100.163', '2017-07-25 23:01:10', '0', '批量删除商品ID为  502 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('3908', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:09:36', '0', '批量修改状态，商品ID为  501 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3909', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:09:46', '0', '批量修改状态，商品ID为  501 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3910', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:11:23', '0', '批量修改状态，商品ID为  485 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3911', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:11:45', '0', '批量修改状态，商品ID为  485 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3912', '63', '登录系统', '192.168.100.163', '2017-07-25 23:13:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3913', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:14:10', '0', '批量修改状态，商品ID为  501 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3914', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:14:14', '0', '批量修改状态，商品ID为  485 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3915', '63', '批量修改商品状态', '192.168.100.163', '2017-07-25 23:16:13', '0', '批量修改状态，商品ID为  501 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('3916', '63', '批量修改单位状态', '192.168.100.163', '2017-07-25 23:16:20', '0', '批量修改状态，单位ID为  8 成功！', '批量修改单位状态成功');
INSERT INTO `jsh_log` VALUES ('3917', '63', '批量修改单位状态', '192.168.100.163', '2017-07-25 23:16:24', '0', '批量修改状态，单位ID为  8 成功！', '批量修改单位状态成功');
INSERT INTO `jsh_log` VALUES ('3918', '63', '登录系统', '192.168.100.163', '2017-07-26 00:00:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3919', '63', '登录系统', '192.168.100.163', '2017-07-26 20:35:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3920', '63', '登录系统', '192.168.100.163', '2017-07-26 22:28:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3921', '63', '更新功能', '192.168.100.163', '2017-07-27 00:26:14', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3922', '63', '更新功能', '192.168.100.163', '2017-07-27 00:26:26', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3923', '63', '登录系统', '192.168.100.163', '2017-07-27 22:48:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3924', '63', '登录系统', '192.168.100.163', '2017-07-27 23:09:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3925', '63', '登录系统', '192.168.100.163', '2017-07-27 23:25:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3926', '63', '批量修改单位状态', '192.168.100.163', '2017-07-27 23:54:53', '0', '批量修改状态，单位ID为  1 成功！', '批量修改单位状态成功');
INSERT INTO `jsh_log` VALUES ('3927', '63', '增加单据', '192.168.100.163', '2017-07-27 23:57:38', '1', '增加单据编号为  CGRK20170727235720 失败！', '增加单据失败');
INSERT INTO `jsh_log` VALUES ('3928', '63', '登录系统', '192.168.100.163', '2017-07-27 23:58:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3929', '63', '增加单据', '192.168.100.163', '2017-07-28 00:00:25', '0', '增加单据编号为  CGRK20170727235957 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3930', '63', '增加单据', '192.168.100.163', '2017-07-28 00:19:48', '0', '增加单据编号为  CGRK2017072801925 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3931', '63', '增加单据', '192.168.100.163', '2017-07-28 00:24:38', '0', '增加单据编号为  CGRK2017072801925 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3932', '63', '登录系统', '192.168.100.163', '2017-07-28 00:32:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3933', '63', '增加单据', '192.168.100.163', '2017-07-28 00:32:49', '0', '增加单据编号为  CGRK2017072803223 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3934', '63', '登录系统', '192.168.100.163', '2017-07-28 00:41:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3935', '63', '登录系统', '192.168.100.163', '2017-07-28 20:38:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3936', '63', '登录系统', '192.168.100.163', '2017-07-28 21:50:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3937', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:02:34', '0', '批量删除单据ID为  123,122,121,120,113,108,107,106,76,72 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3938', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:02:38', '0', '批量删除单据ID为  69,67,59,51,50,49,48,47,46,41 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3939', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:02:42', '0', '批量删除单据ID为  39,38,32 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3940', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:02:46', '0', '批量删除单据ID为  105,81 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3941', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:02:55', '0', '批量删除单据ID为  74,60,53 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3942', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:02', '0', '批量删除单据ID为  56,54,33 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3943', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3944', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3945', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3946', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3947', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3948', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  10 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3949', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  8 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3950', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:13', '0', '批量删除单据ID为  118,104,103,92,90,89,88,87,86,85 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3951', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3952', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  9 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3953', '63', '更新供应商预付款', '', '2017-07-28 22:03:13', '0', '更新供应商ID为  5 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3954', '63', '更新供应商预付款', '', '2017-07-28 22:03:16', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3955', '63', '更新供应商预付款', '', '2017-07-28 22:03:16', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3956', '63', '更新供应商预付款', '', '2017-07-28 22:03:16', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('3957', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:16', '0', '批量删除单据ID为  83,82,80 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3958', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:21', '0', '批量删除单据ID为  119,91,77,73,71,70,68,52,31 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3959', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:25', '0', '批量删除单据ID为  58 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3960', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:29', '0', '批量删除单据ID为  61,57 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3961', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:03:33', '0', '批量删除单据ID为  65,64 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3962', '63', '更新功能', '192.168.100.163', '2017-07-28 22:05:32', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3963', '63', '更新功能', '192.168.100.163', '2017-07-28 22:05:55', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3964', '63', '更新功能', '192.168.100.163', '2017-07-28 22:08:23', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3965', '63', '更新功能', '192.168.100.163', '2017-07-28 22:08:27', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3966', '63', '更新UserBusiness', '192.168.100.163', '2017-07-28 22:09:00', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('3967', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:09:10', '0', '批量删除单据ID为  115,114,110 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3968', '63', '批量删除单据', '192.168.100.163', '2017-07-28 22:09:14', '0', '批量删除单据ID为  117,116,112,111 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('3969', '63', '更新功能', '192.168.100.163', '2017-07-28 22:09:42', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3970', '63', '更新功能', '192.168.100.163', '2017-07-28 22:09:46', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('3971', '63', '增加单据', '192.168.100.163', '2017-07-28 22:29:20', '0', '增加单据编号为  CGRK20170728221045 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3972', '63', '删除单据', '192.168.100.163', '2017-07-28 22:36:22', '0', '删除单据ID为  1 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3973', '63', '增加单据', '192.168.100.163', '2017-07-28 22:36:48', '0', '增加单据编号为  CGRK20170728223624 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3974', '63', '增加单据', '192.168.100.163', '2017-07-28 22:57:39', '0', '增加单据编号为  CGRK20170728225646 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3975', '63', '登录系统', '192.168.100.163', '2017-07-28 23:03:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3976', '63', '增加单据', '192.168.100.163', '2017-07-28 23:04:40', '0', '增加单据编号为  CGRK2017072823359 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3977', '63', '删除单据', '192.168.100.163', '2017-07-28 23:08:19', '0', '删除单据ID为  4 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3978', '63', '删除单据', '192.168.100.163', '2017-07-28 23:08:21', '0', '删除单据ID为  3 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3979', '63', '删除单据', '192.168.100.163', '2017-07-28 23:08:22', '0', '删除单据ID为  2 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3980', '63', '增加单据', '192.168.100.163', '2017-07-28 23:09:36', '0', '增加单据编号为  CGRK2017072823836 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3981', '63', '删除单据', '192.168.100.163', '2017-07-28 23:16:13', '0', '删除单据ID为  5 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3982', '63', '增加单据', '192.168.100.163', '2017-07-28 23:17:19', '0', '增加单据编号为  CGRK20170728231642 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3983', '63', '保存仓管通明细', '192.168.100.163', '2017-07-28 23:17:19', '1', '保存仓管通明细对应主表编号为  6 失败！', '保存仓管通明细失败');
INSERT INTO `jsh_log` VALUES ('3984', '63', '登录系统', '192.168.100.163', '2017-07-28 23:29:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3985', '63', '删除单据', '192.168.100.163', '2017-07-28 23:30:06', '0', '删除单据ID为  6 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3986', '63', '增加单据', '192.168.100.163', '2017-07-28 23:30:47', '0', '增加单据编号为  CGRK2017072823309 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3987', '63', '保存仓管通明细', '192.168.100.163', '2017-07-28 23:30:48', '1', '保存仓管通明细对应主表编号为  7 失败！', '保存仓管通明细失败');
INSERT INTO `jsh_log` VALUES ('3988', '63', '登录系统', '192.168.100.163', '2017-07-28 23:39:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3989', '63', '删除单据', '192.168.100.163', '2017-07-28 23:40:21', '0', '删除单据ID为  7 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3990', '63', '增加单据', '192.168.100.163', '2017-07-28 23:40:58', '0', '增加单据编号为  CGRK20170728234026 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3991', '63', '保存仓管通明细', '192.168.100.163', '2017-07-28 23:40:58', '1', '保存仓管通明细对应主表编号为  8 失败！', '保存仓管通明细失败');
INSERT INTO `jsh_log` VALUES ('3992', '63', '登录系统', '192.168.100.163', '2017-07-28 23:48:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3993', '63', '删除单据', '192.168.100.163', '2017-07-28 23:48:12', '0', '删除单据ID为  8 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3994', '63', '增加单据', '192.168.100.163', '2017-07-28 23:48:42', '0', '增加单据编号为  CGRK20170728234814 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3995', '63', '保存仓管通明细', '192.168.100.163', '2017-07-28 23:48:42', '0', '保存仓管通明细对应主表编号为  9 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('3996', '63', '登录系统', '192.168.100.163', '2017-07-28 23:55:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('3997', '63', '删除单据', '192.168.100.163', '2017-07-28 23:55:18', '0', '删除单据ID为  9 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('3998', '63', '增加单据', '192.168.100.163', '2017-07-28 23:55:42', '0', '增加单据编号为  CGRK20170728235520 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('3999', '63', '删除单据', '192.168.100.163', '2017-07-28 23:56:25', '0', '删除单据ID为  10 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4000', '63', '增加单据', '192.168.100.163', '2017-07-28 23:56:48', '0', '增加单据编号为  CGRK20170728235627 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4001', '63', '保存仓管通明细', '192.168.100.163', '2017-07-28 23:56:48', '0', '保存仓管通明细对应主表编号为  11 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4002', '63', '登录系统', '192.168.100.163', '2017-07-29 00:06:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4003', '63', '删除单据', '192.168.100.163', '2017-07-29 00:06:39', '0', '删除单据ID为  11 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4004', '63', '增加单据', '192.168.100.163', '2017-07-29 00:07:25', '0', '增加单据编号为  CGRK201707290641 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4005', '63', '登录系统', '192.168.100.163', '2017-07-29 00:13:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4006', '63', '删除单据', '192.168.100.163', '2017-07-29 00:14:03', '0', '删除单据ID为  12 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4007', '63', '增加单据', '192.168.100.163', '2017-07-29 00:14:42', '0', '增加单据编号为  CGRK201707290145 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4008', '63', '登录系统', '192.168.100.163', '2017-07-29 00:18:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4009', '63', '删除单据', '192.168.100.163', '2017-07-29 00:18:49', '0', '删除单据ID为  13 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4010', '63', '增加单据', '192.168.100.163', '2017-07-29 00:19:12', '0', '增加单据编号为  CGRK2017072901851 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4011', '63', '增加单据', '192.168.100.163', '2017-07-29 00:20:57', '0', '增加单据编号为  CGRK2017072902040 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4012', '63', '保存仓管通明细', '192.168.100.163', '2017-07-29 00:20:57', '0', '保存仓管通明细对应主表编号为  15 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4013', '63', '增加单据', '192.168.100.163', '2017-07-29 00:21:53', '0', '增加单据编号为  CGRK2017072902114 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4014', '63', '登录系统', '192.168.100.163', '2017-07-29 00:24:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4015', '63', '删除单据', '192.168.100.163', '2017-07-29 00:25:08', '0', '删除单据ID为  16 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4016', '63', '删除单据', '192.168.100.163', '2017-07-29 00:25:10', '0', '删除单据ID为  15 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4017', '63', '删除单据', '192.168.100.163', '2017-07-29 00:25:11', '0', '删除单据ID为  14 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4018', '63', '增加单据', '192.168.100.163', '2017-07-29 00:25:50', '0', '增加单据编号为  CGRK2017072902512 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4019', '63', '登录系统', '192.168.100.163', '2017-07-29 00:30:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4020', '63', '删除单据', '192.168.100.163', '2017-07-29 00:30:41', '0', '删除单据ID为  17 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4021', '63', '增加单据', '192.168.100.163', '2017-07-29 00:31:09', '0', '增加单据编号为  CGRK2017072903043 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4022', '63', '登录系统', '192.168.100.163', '2017-07-29 00:44:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4023', '63', '删除单据', '192.168.100.163', '2017-07-29 00:44:41', '0', '删除单据ID为  18 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4024', '63', '增加单据', '192.168.100.163', '2017-07-29 00:44:58', '0', '增加单据编号为  CGRK2017072904442 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4025', '63', '登录系统', '192.168.100.163', '2017-07-29 00:48:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4026', '63', '删除单据', '192.168.100.163', '2017-07-29 00:48:25', '0', '删除单据ID为  1 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4027', '63', '增加单据', '192.168.100.163', '2017-07-29 00:48:40', '0', '增加单据编号为  CGRK2017072904828 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4028', '63', '登录系统', '192.168.100.163', '2017-07-29 00:51:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4029', '63', '删除单据', '192.168.100.163', '2017-07-29 00:51:27', '0', '删除单据ID为  2 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4030', '63', '增加单据', '192.168.100.163', '2017-07-29 00:51:42', '0', '增加单据编号为  CGRK2017072905128 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4031', '63', '删除单据', '192.168.100.163', '2017-07-29 00:54:15', '0', '删除单据ID为  3 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4032', '63', '增加单据', '192.168.100.163', '2017-07-29 00:54:31', '0', '增加单据编号为  CGRK2017072905415 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4033', '63', '保存仓管通明细', '192.168.100.163', '2017-07-29 00:54:31', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4034', '63', '删除单据', '192.168.100.163', '2017-07-29 00:54:44', '0', '删除单据ID为  4 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4035', '63', '增加单据', '192.168.100.163', '2017-07-29 00:55:22', '0', '增加单据编号为  CGRK2017072905446 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4036', '63', '登录系统', '192.168.100.163', '2017-07-29 00:58:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4037', '63', '删除单据', '192.168.100.163', '2017-07-29 00:58:13', '0', '删除单据ID为  5 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4038', '63', '增加单据', '192.168.100.163', '2017-07-29 00:58:26', '0', '增加单据编号为  CGRK2017072905814 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4039', '63', '登录系统', '192.168.100.163', '2017-07-29 01:06:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4040', '63', '删除单据', '192.168.100.163', '2017-07-29 01:06:42', '0', '删除单据ID为  6 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4041', '63', '增加单据', '192.168.100.163', '2017-07-29 01:07:02', '0', '增加单据编号为  CGRK201707291643 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4042', '63', '登录系统', '192.168.100.163', '2017-07-29 01:08:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4043', '63', '删除单据', '192.168.100.163', '2017-07-29 01:09:00', '0', '删除单据ID为  7 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4044', '63', '增加单据', '192.168.100.163', '2017-07-29 01:09:22', '0', '增加单据编号为  CGRK20170729191 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4045', '63', '登录系统', '192.168.100.163', '2017-07-29 01:11:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4046', '63', '删除单据', '192.168.100.163', '2017-07-29 01:11:11', '0', '删除单据ID为  8 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4047', '63', '增加单据', '192.168.100.163', '2017-07-29 01:11:24', '0', '增加单据编号为  CGRK2017072911113 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4048', '63', '登录系统', '192.168.100.163', '2017-07-29 01:13:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4049', '63', '删除单据', '192.168.100.163', '2017-07-29 01:13:22', '0', '删除单据ID为  9 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4050', '63', '增加单据', '192.168.100.163', '2017-07-29 01:13:36', '0', '增加单据编号为  CGRK2017072911322 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4051', '63', '删除单据', '192.168.100.163', '2017-07-29 01:13:49', '0', '删除单据ID为  10 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4052', '63', '增加单据', '192.168.100.163', '2017-07-29 01:14:06', '0', '增加单据编号为  CGRK2017072911350 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4053', '63', '保存仓管通明细', '192.168.100.163', '2017-07-29 01:14:06', '0', '保存仓管通明细对应主表编号为  11 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4054', '63', '登录系统', '192.168.100.163', '2017-07-29 01:16:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4055', '63', '删除单据', '192.168.100.163', '2017-07-29 01:17:04', '0', '删除单据ID为  11 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4056', '63', '增加单据', '192.168.100.163', '2017-07-29 01:17:26', '0', '增加单据编号为  CGRK201707291176 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4057', '63', '增加单据', '192.168.100.163', '2017-07-29 01:17:58', '0', '增加单据编号为  CGRK2017072911744 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4058', '63', '保存仓管通明细', '192.168.100.163', '2017-07-29 01:17:59', '0', '保存仓管通明细对应主表编号为  13 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4059', '63', '登录系统', '192.168.100.163', '2017-07-29 01:26:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4060', '63', '删除单据', '192.168.100.163', '2017-07-29 01:26:13', '0', '删除单据ID为  12 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4061', '63', '增加单据', '192.168.100.163', '2017-07-29 01:26:28', '0', '增加单据编号为  CGRK2017072912614 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4062', '63', '保存仓管通明细', '192.168.100.163', '2017-07-29 01:26:28', '0', '保存仓管通明细对应主表编号为  14 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4063', '63', '登录系统', '192.168.1.103', '2017-07-30 09:53:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4064', '63', '删除单据', '192.168.1.103', '2017-07-30 10:05:20', '0', '删除单据ID为  14 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4065', '63', '增加单据', '192.168.1.103', '2017-07-30 10:06:05', '0', '增加单据编号为  CGRK2017073010522 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4066', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 10:06:06', '0', '保存仓管通明细对应主表编号为  1 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4067', '63', '更新单据', '192.168.1.103', '2017-07-30 10:14:29', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4068', '63', '更新单据', '192.168.1.103', '2017-07-30 10:14:44', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4069', '63', '更新单据', '192.168.1.103', '2017-07-30 10:15:03', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4070', '63', '更新单据', '192.168.1.103', '2017-07-30 10:15:56', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4071', '63', '登录系统', '192.168.1.103', '2017-07-30 10:20:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4072', '63', '更新单据', '192.168.1.103', '2017-07-30 10:20:56', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4073', '63', '更新单据', '192.168.1.103', '2017-07-30 10:21:55', '0', '更新单据ID为  1 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4074', '63', '登录系统', '192.168.1.103', '2017-07-30 10:31:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4075', '63', '删除单据', '192.168.1.103', '2017-07-30 10:32:04', '0', '删除单据ID为  1 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4076', '63', '增加单据', '192.168.1.103', '2017-07-30 10:32:36', '0', '增加单据编号为  CGRK2017073010325 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4077', '63', '登录系统', '192.168.1.103', '2017-07-30 10:39:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4078', '63', '删除单据', '192.168.1.103', '2017-07-30 10:39:37', '0', '删除单据ID为  2 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4079', '63', '增加单据', '192.168.1.103', '2017-07-30 10:40:01', '0', '增加单据编号为  CGRK20170730103938 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4080', '63', '删除单据', '192.168.1.103', '2017-07-30 10:40:22', '0', '删除单据ID为  3 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4081', '63', '增加单据', '192.168.1.103', '2017-07-30 10:40:41', '0', '增加单据编号为  CGRK20170730104024 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4082', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 10:40:41', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4083', '63', '登录系统', '192.168.1.103', '2017-07-30 10:43:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4084', '63', '删除单据', '192.168.1.103', '2017-07-30 10:43:25', '0', '删除单据ID为  4 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4085', '63', '增加单据', '192.168.1.103', '2017-07-30 10:43:53', '0', '增加单据编号为  CGRK20170730104326 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4086', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 10:43:53', '0', '保存仓管通明细对应主表编号为  5 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4087', '63', '登录系统', '192.168.1.103', '2017-07-30 11:03:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4088', '63', '增加单据', '192.168.1.103', '2017-07-30 11:05:20', '0', '增加单据编号为  CGRK201707301142 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4089', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:05:20', '0', '保存仓管通明细对应主表编号为  6 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4090', '63', '登录系统', '192.168.1.103', '2017-07-30 11:13:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4091', '63', '删除单据', '192.168.1.103', '2017-07-30 11:13:47', '0', '删除单据ID为  6 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4092', '63', '增加单据', '192.168.1.103', '2017-07-30 11:14:52', '0', '增加单据编号为  CGRK2017073011145 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4093', '63', '登录系统', '192.168.1.103', '2017-07-30 11:21:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4094', '63', '删除单据', '192.168.1.103', '2017-07-30 11:21:48', '0', '删除单据ID为  7 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4095', '63', '增加单据', '192.168.1.103', '2017-07-30 11:22:20', '0', '增加单据编号为  CGRK20170730112149 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4096', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:22:20', '0', '保存仓管通明细对应主表编号为  8 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4097', '63', '更新单据', '192.168.1.103', '2017-07-30 11:23:01', '0', '更新单据ID为  8 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4098', '63', '更新单据', '192.168.1.103', '2017-07-30 11:23:26', '0', '更新单据ID为  8 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4099', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:23:27', '0', '保存仓管通明细对应主表编号为  8 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4100', '63', '更新单据', '192.168.1.103', '2017-07-30 11:25:23', '0', '更新单据ID为  8 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4101', '63', '删除单据', '192.168.1.103', '2017-07-30 11:28:59', '0', '删除单据ID为  8 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4102', '63', '增加单据', '192.168.1.103', '2017-07-30 11:29:32', '0', '增加单据编号为  CGRK20170730112916 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4103', '63', '更新单据', '192.168.1.103', '2017-07-30 11:29:49', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4104', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:29:49', '0', '保存仓管通明细对应主表编号为  9 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4105', '63', '更新单据', '192.168.1.103', '2017-07-30 11:30:13', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4106', '63', '更新单据', '192.168.1.103', '2017-07-30 11:31:25', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4107', '63', '更新单据', '192.168.1.103', '2017-07-30 11:34:50', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4108', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:07', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4109', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:10', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4110', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:16', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4111', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:17', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4112', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:19', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4113', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:19', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4114', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:19', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4115', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:19', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4116', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:20', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4117', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:24', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4118', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:35:24', '0', '保存仓管通明细对应主表编号为  9 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4119', '63', '更新单据', '192.168.1.103', '2017-07-30 11:35:34', '0', '更新单据ID为  9 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4120', '63', '删除单据', '192.168.1.103', '2017-07-30 11:40:01', '0', '删除单据ID为  9 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4121', '63', '增加单据', '192.168.1.103', '2017-07-30 11:40:18', '0', '增加单据编号为  CGRK2017073011402 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4122', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 11:40:19', '0', '保存仓管通明细对应主表编号为  10 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4123', '63', '更新单据', '192.168.1.103', '2017-07-30 11:40:25', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4124', '63', '更新单据', '192.168.1.103', '2017-07-30 11:40:33', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4125', '63', '更新单据', '192.168.1.103', '2017-07-30 11:44:16', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4126', '63', '更新单据', '192.168.1.103', '2017-07-30 11:45:57', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4127', '63', '更新单据', '192.168.1.103', '2017-07-30 11:46:47', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4128', '63', '更新单据', '192.168.1.103', '2017-07-30 11:46:49', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4129', '63', '更新单据', '192.168.1.103', '2017-07-30 11:49:53', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4130', '63', '更新单据', '192.168.1.103', '2017-07-30 11:49:56', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4131', '63', '更新单据', '192.168.1.103', '2017-07-30 11:50:19', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4132', '63', '更新单据', '192.168.1.103', '2017-07-30 11:51:10', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4133', '63', '更新单据', '192.168.1.103', '2017-07-30 11:52:17', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4134', '63', '更新单据', '192.168.1.103', '2017-07-30 11:53:53', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4135', '63', '更新单据', '192.168.1.103', '2017-07-30 11:54:13', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4136', '63', '更新单据', '192.168.1.103', '2017-07-30 11:54:49', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4137', '63', '更新单据', '192.168.1.103', '2017-07-30 11:56:14', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4138', '63', '更新单据', '192.168.1.103', '2017-07-30 11:56:18', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4139', '63', '更新单据', '192.168.1.103', '2017-07-30 11:56:25', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4140', '63', '更新单据', '192.168.1.103', '2017-07-30 11:56:27', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4141', '63', '更新单据', '192.168.1.103', '2017-07-30 11:57:08', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4142', '63', '更新单据', '192.168.1.103', '2017-07-30 11:58:47', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4143', '63', '更新单据', '192.168.1.103', '2017-07-30 11:58:51', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4144', '63', '更新单据', '192.168.1.103', '2017-07-30 11:59:14', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4145', '63', '更新单据', '192.168.1.103', '2017-07-30 11:59:20', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4146', '63', '更新单据', '192.168.1.103', '2017-07-30 11:59:21', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4147', '63', '更新单据', '192.168.1.103', '2017-07-30 11:59:22', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4148', '63', '更新单据', '192.168.1.103', '2017-07-30 11:59:22', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4149', '63', '更新单据', '192.168.1.103', '2017-07-30 12:00:36', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4150', '63', '更新单据', '192.168.1.103', '2017-07-30 12:01:04', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4151', '63', '更新单据', '192.168.1.103', '2017-07-30 12:02:07', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4152', '63', '更新单据', '192.168.1.103', '2017-07-30 12:02:49', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4153', '63', '更新单据', '192.168.1.103', '2017-07-30 12:04:59', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4154', '63', '更新单据', '192.168.1.103', '2017-07-30 12:26:39', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4155', '63', '更新单据', '192.168.1.103', '2017-07-30 12:27:24', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4156', '63', '更新单据', '192.168.1.103', '2017-07-30 12:28:17', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4157', '63', '更新单据', '192.168.1.103', '2017-07-30 12:28:30', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4158', '63', '更新单据', '192.168.1.103', '2017-07-30 12:32:30', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4159', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 12:32:31', '0', '保存仓管通明细对应主表编号为  10 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4160', '63', '更新单据', '192.168.1.103', '2017-07-30 12:32:37', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4161', '63', '更新单据', '192.168.1.103', '2017-07-30 12:32:48', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4162', '63', '更新单据', '192.168.1.103', '2017-07-30 12:32:55', '0', '更新单据ID为  10 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4163', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 12:32:55', '0', '保存仓管通明细对应主表编号为  10 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4164', '63', '增加单据', '192.168.1.103', '2017-07-30 12:37:43', '0', '增加单据编号为  CGRK20170730123717 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4165', '63', '增加单据', '192.168.1.103', '2017-07-30 12:42:02', '0', '增加单据编号为  CGRK20170730123717 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4166', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 12:42:02', '0', '保存仓管通明细对应主表编号为  12 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4167', '63', '增加单据', '192.168.1.103', '2017-07-30 12:46:14', '0', '增加单据编号为  XSCK20170730124520 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4168', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 12:46:15', '0', '保存仓管通明细对应主表编号为  13 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4169', '63', '更新单据', '192.168.1.103', '2017-07-30 12:47:46', '0', '更新单据ID为  13 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4170', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 12:47:46', '0', '保存仓管通明细对应主表编号为  13 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4171', '63', '增加经手人', '192.168.1.103', '2017-07-30 13:11:16', '0', '增加经手人名称为  小李-业务员 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('4172', '63', '增加经手人', '192.168.1.103', '2017-07-30 13:11:28', '0', '增加经手人名称为  小军-业务员 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('4173', '63', '登录系统', '192.168.1.103', '2017-07-30 13:40:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4174', '63', '登录系统', '192.168.1.103', '2017-07-30 13:51:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4175', '63', '增加单据', '192.168.1.103', '2017-07-30 13:55:51', '0', '增加单据编号为  XSCK20170730135537 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4176', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 13:55:52', '0', '保存仓管通明细对应主表编号为  14 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4177', '63', '增加单据', '192.168.1.103', '2017-07-30 13:59:19', '0', '增加单据编号为  XSCK2017073013592 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4178', '63', '保存仓管通明细', '192.168.1.103', '2017-07-30 13:59:19', '0', '保存仓管通明细对应主表编号为  15 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4179', '63', '登录系统', '192.168.100.163', '2017-07-30 18:47:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4180', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:07', '0', '删除单据ID为  15 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4181', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:09', '0', '删除单据ID为  14 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4182', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:10', '0', '删除单据ID为  13 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4183', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:28', '0', '删除单据ID为  12 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4184', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:30', '0', '删除单据ID为  11 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4185', '63', '删除单据', '192.168.100.163', '2017-07-30 18:57:52', '0', '删除单据ID为  10 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4186', '63', '增加单据', '192.168.100.163', '2017-07-30 19:03:22', '0', '增加单据编号为  XSCK20170730185833 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4187', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:03:22', '0', '保存仓管通明细对应主表编号为  1 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4188', '63', '删除单据', '192.168.100.163', '2017-07-30 19:04:40', '0', '删除单据ID为  1 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4189', '63', '增加单据', '192.168.100.163', '2017-07-30 19:05:09', '0', '增加单据编号为  XSCK2017073019448 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4190', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:05:09', '0', '保存仓管通明细对应主表编号为  2 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4191', '63', '删除单据', '192.168.100.163', '2017-07-30 19:07:49', '0', '删除单据ID为  2 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4192', '63', '增加单据', '192.168.100.163', '2017-07-30 19:08:08', '0', '增加单据编号为  XSCK2017073019751 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4193', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:08:08', '0', '保存仓管通明细对应主表编号为  3 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4194', '63', '登录系统', '192.168.100.163', '2017-07-30 19:14:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4195', '63', '删除单据', '192.168.100.163', '2017-07-30 19:14:48', '0', '删除单据ID为  3 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4196', '63', '增加单据', '192.168.100.163', '2017-07-30 19:15:11', '0', '增加单据编号为  XSCK20170730191450 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4197', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:15:11', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4198', '63', '登录系统', '192.168.100.163', '2017-07-30 19:17:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4199', '63', '删除单据', '192.168.100.163', '2017-07-30 19:17:50', '0', '删除单据ID为  4 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4200', '63', '增加单据', '192.168.100.163', '2017-07-30 19:18:13', '0', '增加单据编号为  XSCK20170730191752 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4201', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:18:14', '0', '保存仓管通明细对应主表编号为  5 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4202', '63', '登录系统', '192.168.100.163', '2017-07-30 19:23:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4203', '63', '删除单据', '192.168.100.163', '2017-07-30 19:23:51', '0', '删除单据ID为  5 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4204', '63', '增加单据', '192.168.100.163', '2017-07-30 19:24:28', '0', '增加单据编号为  XSCK20170730192354 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4205', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:24:29', '0', '保存仓管通明细对应主表编号为  6 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4206', '63', '删除单据', '192.168.100.163', '2017-07-30 19:33:09', '0', '删除单据ID为  6 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4207', '63', '增加单据', '192.168.100.163', '2017-07-30 19:35:42', '0', '增加单据编号为  XSCK20170730193525 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4208', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:35:47', '0', '保存仓管通明细对应主表编号为  7 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4209', '63', '删除单据', '192.168.100.163', '2017-07-30 19:39:34', '0', '删除单据ID为  7 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4210', '63', '增加单据', '192.168.100.163', '2017-07-30 19:39:51', '0', '增加单据编号为  XSCK20170730193936 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4211', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 19:39:51', '0', '保存仓管通明细对应主表编号为  8 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4212', '63', '登录系统', '192.168.100.163', '2017-07-30 19:53:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4213', '63', '登录系统', '192.168.100.163', '2017-07-30 19:59:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4214', '63', '删除单据', '192.168.100.163', '2017-07-30 19:59:55', '0', '删除单据ID为  8 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4215', '63', '增加单据', '192.168.100.163', '2017-07-30 20:00:16', '0', '增加单据编号为  XSCK20170730195958 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4216', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 20:00:16', '0', '保存仓管通明细对应主表编号为  9 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4217', '63', '删除单据', '192.168.100.163', '2017-07-30 21:01:42', '0', '删除单据ID为  9 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4218', '63', '增加单据', '192.168.100.163', '2017-07-30 21:01:57', '0', '增加单据编号为  XSCK2017073021142 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4219', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:01:58', '0', '保存仓管通明细对应主表编号为  10 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4220', '63', '删除单据', '192.168.100.163', '2017-07-30 21:07:16', '0', '删除单据ID为  10 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4221', '63', '增加经手人', '192.168.100.163', '2017-07-30 21:07:46', '0', '增加经手人名称为  小曹 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('4222', '63', '更新经手人', '192.168.100.163', '2017-07-30 21:08:00', '0', '更新经手人ID为  5 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('4223', '63', '更新经手人', '192.168.100.163', '2017-07-30 21:08:05', '0', '更新经手人ID为  5 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('4224', '63', '更新经手人', '192.168.100.163', '2017-07-30 21:08:08', '0', '更新经手人ID为  6 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('4225', '63', '增加单据', '192.168.100.163', '2017-07-30 21:08:41', '0', '增加单据编号为  XSCK2017073021820 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4226', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:08:41', '0', '保存仓管通明细对应主表编号为  11 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4227', '63', '删除单据', '192.168.100.163', '2017-07-30 21:12:50', '0', '删除单据ID为  11 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4228', '63', '增加单据', '192.168.100.163', '2017-07-30 21:13:05', '0', '增加单据编号为  XSCK20170730211251 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4229', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:13:05', '0', '保存仓管通明细对应主表编号为  12 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4230', '63', '增加单据', '192.168.100.163', '2017-07-30 21:14:32', '0', '增加单据编号为  XSCK20170730211418 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4231', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:14:32', '0', '保存仓管通明细对应主表编号为  13 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4232', '63', '增加单据', '192.168.100.163', '2017-07-30 21:15:14', '0', '增加单据编号为  XSCK20170730211459 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4233', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:15:14', '0', '保存仓管通明细对应主表编号为  14 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4234', '63', '批量删除单据', '192.168.100.163', '2017-07-30 21:15:44', '0', '批量删除单据ID为  14,13,12 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4235', '63', '增加单据', '192.168.100.163', '2017-07-30 21:16:02', '0', '增加单据编号为  XSCK20170730211545 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4236', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:16:03', '0', '保存仓管通明细对应主表编号为  15 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4237', '63', '更新单据', '192.168.100.163', '2017-07-30 21:17:25', '0', '更新单据ID为  15 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4238', '63', '删除单据', '192.168.100.163', '2017-07-30 21:25:09', '0', '删除单据ID为  15 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4239', '63', '增加单据', '192.168.100.163', '2017-07-30 21:25:26', '0', '增加单据编号为  XSCK20170730212512 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4240', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 21:25:26', '0', '保存仓管通明细对应主表编号为  16 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4241', '63', '登录系统', '192.168.100.163', '2017-07-30 21:49:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4242', '63', '登录系统', '192.168.100.163', '2017-07-30 21:52:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4243', '63', '更新单据', '192.168.100.163', '2017-07-30 22:11:18', '0', '更新单据ID为  16 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4244', '63', '登录系统', '192.168.100.163', '2017-07-30 22:53:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4245', '63', '登录系统', '192.168.100.163', '2017-07-30 23:12:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4246', '63', '登录系统', '192.168.100.163', '2017-07-30 23:29:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4247', '63', '登录系统', '192.168.100.163', '2017-07-30 23:34:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4248', '63', '删除单据', '192.168.100.163', '2017-07-30 23:36:38', '0', '删除单据ID为  16 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4249', '63', '增加单据', '192.168.100.163', '2017-07-30 23:37:30', '0', '增加单据编号为  XSCK20170730233655 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4250', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 23:37:30', '0', '保存仓管通明细对应主表编号为  17 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4251', '63', '登录系统', '192.168.100.163', '2017-07-30 23:39:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4252', '63', '增加单据', '192.168.100.163', '2017-07-30 23:54:42', '0', '增加单据编号为  XSCK20170730235423 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4253', '63', '保存仓管通明细', '192.168.100.163', '2017-07-30 23:54:43', '0', '保存仓管通明细对应主表编号为  18 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4254', '63', '增加单据', '192.168.100.163', '2017-07-31 00:02:21', '0', '增加单据编号为  CGRK20170731027 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4255', '63', '保存仓管通明细', '192.168.100.163', '2017-07-31 00:02:21', '0', '保存仓管通明细对应主表编号为  19 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4256', '63', '增加单据', '192.168.100.163', '2017-07-31 00:02:50', '0', '增加单据编号为  XSCK201707310229 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4257', '63', '保存仓管通明细', '192.168.100.163', '2017-07-31 00:02:50', '0', '保存仓管通明细对应主表编号为  20 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4258', '63', '更新单据', '192.168.100.163', '2017-07-31 00:03:34', '0', '更新单据ID为  20 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4259', '63', '保存仓管通明细', '192.168.100.163', '2017-07-31 00:03:35', '0', '保存仓管通明细对应主表编号为  20 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4260', '63', '更新单据', '192.168.100.163', '2017-07-31 00:15:52', '0', '更新单据ID为  19 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4261', '63', '增加单据', '192.168.100.163', '2017-07-31 00:22:43', '0', '增加单据编号为  CGRK2017073102226 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4262', '63', '保存仓管通明细', '192.168.100.163', '2017-07-31 00:22:43', '0', '保存仓管通明细对应主表编号为  21 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4263', '63', '删除单据', '192.168.100.163', '2017-07-31 00:24:15', '0', '删除单据ID为  18 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4264', '63', '登录系统', '192.168.100.163', '2017-07-31 20:47:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4265', '63', '更新单据', '192.168.100.163', '2017-07-31 21:13:00', '0', '更新单据ID为  19 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4266', '63', '更新单据', '192.168.100.163', '2017-07-31 21:13:25', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4267', '63', '登录系统', '192.168.100.163', '2017-07-31 21:17:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4268', '63', '更新单据', '192.168.100.163', '2017-07-31 21:17:32', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4269', '63', '更新单据', '192.168.100.163', '2017-07-31 21:17:44', '0', '更新单据ID为  19 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4270', '63', '增加结算账户', '192.168.100.163', '2017-07-31 21:18:54', '0', '增加结算账户名称为  支付宝 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('4271', '63', '增加结算账户', '192.168.100.163', '2017-07-31 21:21:37', '0', '增加结算账户名称为  微信 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('4272', '63', '增加结算账户', '192.168.100.163', '2017-07-31 21:23:07', '0', '增加结算账户名称为  上海农行 成功！', '增加结算账户成功');
INSERT INTO `jsh_log` VALUES ('4273', '63', '更新结算账户', '192.168.100.163', '2017-07-31 21:23:24', '0', '更新结算账户ID为  12 成功！', '更新结算账户成功');
INSERT INTO `jsh_log` VALUES ('4274', '63', '登录系统', '192.168.100.163', '2017-08-01 20:49:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4275', '63', '增加单据', '192.168.100.163', '2017-08-01 23:34:02', '0', '增加单据编号为  CGRK20170801233342 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4276', '63', '保存仓管通明细', '192.168.100.163', '2017-08-01 23:34:03', '0', '保存仓管通明细对应主表编号为  22 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4277', '63', '增加单据', '192.168.100.163', '2017-08-02 00:48:25', '0', '增加单据编号为  CGRK2017080204614 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4278', '63', '保存仓管通明细', '192.168.100.163', '2017-08-02 00:48:26', '0', '保存仓管通明细对应主表编号为  23 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4279', '63', '登录系统', '192.168.100.163', '2017-08-02 20:52:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4280', '63', '登录系统', '192.168.100.163', '2017-08-02 21:55:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4281', '63', '更新单据', '192.168.100.163', '2017-08-02 22:02:06', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4282', '63', '更新单据', '192.168.100.163', '2017-08-02 22:02:36', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4283', '63', '更新单据', '192.168.100.163', '2017-08-02 22:02:55', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4284', '63', '更新单据', '192.168.100.163', '2017-08-02 22:13:59', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4285', '63', '更新单据', '192.168.100.163', '2017-08-02 22:16:46', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4286', '63', '更新单据', '192.168.100.163', '2017-08-02 22:18:13', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4287', '63', '更新单据', '192.168.100.163', '2017-08-02 22:18:44', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4288', '63', '更新单据', '192.168.100.163', '2017-08-02 22:19:03', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4289', '63', '更新单据', '192.168.100.163', '2017-08-02 22:19:46', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4290', '63', '更新单据', '192.168.100.163', '2017-08-02 22:43:25', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4291', '63', '增加单据', '192.168.100.163', '2017-08-02 23:47:17', '0', '增加单据编号为  XSCK20170802234525 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4292', '63', '保存仓管通明细', '192.168.100.163', '2017-08-02 23:47:17', '0', '保存仓管通明细对应主表编号为  24 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4293', '63', '更新单据', '192.168.100.163', '2017-08-03 00:06:50', '0', '更新单据ID为  23 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4294', '63', '保存仓管通明细', '192.168.100.163', '2017-08-03 00:06:50', '0', '保存仓管通明细对应主表编号为  23 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4295', '63', '登录系统', '192.168.100.163', '2017-08-03 00:50:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4296', '63', '登录系统', '192.168.100.163', '2017-08-03 00:55:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4297', '63', '登录系统', '192.168.100.163', '2017-08-03 00:57:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4298', '63', '登录系统', '192.168.100.163', '2017-08-03 20:22:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4299', '63', '登录系统', '192.168.100.163', '2017-08-03 22:09:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4300', '63', '增加功能', '192.168.100.163', '2017-08-03 22:33:58', '0', '增加功能名称为  计量单位 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('4301', '63', '更新功能', '192.168.100.163', '2017-08-03 22:34:19', '0', '更新功能ID为  220 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4302', '63', '更新UserBusiness', '192.168.100.163', '2017-08-03 22:34:42', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4303', '63', '更新功能', '192.168.100.163', '2017-08-03 22:35:49', '0', '更新功能ID为  220 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4304', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:36:23', '1', '增加计量单位名称为  aaaa 失败！', '增加计量单位失败');
INSERT INTO `jsh_log` VALUES ('4305', '63', '登录系统', '192.168.100.163', '2017-08-03 22:37:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4306', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:38:01', '1', '增加计量单位名称为  aaaa 失败！', '增加计量单位失败');
INSERT INTO `jsh_log` VALUES ('4307', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:38:35', '1', '增加计量单位名称为  aaaa 失败！', '增加计量单位失败');
INSERT INTO `jsh_log` VALUES ('4308', '63', '登录系统', '192.168.100.163', '2017-08-03 22:41:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4309', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:41:54', '0', '增加计量单位名称为  123 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4310', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:41:58', '0', '增加计量单位名称为  1234124 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4311', '63', '更新计量单位', '192.168.100.163', '2017-08-03 22:42:22', '0', '更新计量单位ID为  2 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4312', '63', '更新计量单位', '192.168.100.163', '2017-08-03 22:42:36', '0', '更新计量单位ID为  1 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4313', '63', '批量删除计量单位', '192.168.100.163', '2017-08-03 22:44:04', '0', '批量删除计量单位ID为  1 成功！', '批量删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4314', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:44:08', '0', '增加计量单位名称为  ssss 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4315', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:44:13', '0', '增加计量单位名称为  wwww 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4316', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:44:23', '0', '增加计量单位名称为  dddddd 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4317', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:44:26', '0', '增加计量单位名称为  fffff 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4318', '63', '更新计量单位', '192.168.100.163', '2017-08-03 22:44:39', '0', '更新计量单位ID为  5 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4319', '63', '更新计量单位', '192.168.100.163', '2017-08-03 22:44:43', '0', '更新计量单位ID为  5 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4320', '63', '批量删除计量单位', '192.168.100.163', '2017-08-03 22:44:48', '0', '批量删除计量单位ID为  6 成功！', '批量删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4321', '63', '增加计量单位', '192.168.100.163', '2017-08-03 22:44:50', '0', '增加计量单位名称为  12312 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4322', '63', '增加计量单位', '192.168.100.163', '2017-08-03 23:21:44', '0', '增加计量单位名称为  瓶,箱(1:12) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4323', '63', '增加计量单位', '192.168.100.163', '2017-08-03 23:23:01', '0', '增加计量单位名称为  dsd,ad(1:123) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4324', '63', '批量删除计量单位', '192.168.100.163', '2017-08-03 23:23:05', '0', '批量删除计量单位ID为  9 成功！', '批量删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4325', '63', '删除计量单位', '192.168.100.163', '2017-08-03 23:23:27', '0', '删除计量单位ID为  3 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4326', '63', '删除计量单位', '192.168.100.163', '2017-08-03 23:23:29', '0', '删除计量单位ID为  4 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4327', '63', '删除计量单位', '192.168.100.163', '2017-08-03 23:23:31', '0', '删除计量单位ID为  5 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4328', '63', '删除计量单位', '192.168.100.163', '2017-08-03 23:23:34', '0', '删除计量单位ID为  7 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4329', '63', '增加计量单位', '192.168.100.163', '2017-08-03 23:24:49', '0', '增加计量单位名称为  123,234(1:23) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4330', '63', '删除计量单位', '192.168.100.163', '2017-08-03 23:24:54', '0', '删除计量单位ID为  10 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4331', '63', '增加计量单位', '192.168.100.163', '2017-08-03 23:27:00', '0', '增加计量单位名称为  qwe,sed(1:33) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4332', '63', '增加计量单位', '192.168.100.163', '2017-08-03 23:27:14', '0', '增加计量单位名称为  33,44(1:5) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4333', '63', '更新计量单位', '192.168.100.163', '2017-08-03 23:51:16', '0', '更新计量单位ID为  2 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4334', '63', '更新计量单位', '192.168.100.163', '2017-08-03 23:51:21', '0', '更新计量单位ID为  2 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4335', '63', '更新计量单位', '192.168.100.163', '2017-08-03 23:51:24', '0', '更新计量单位ID为  2 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4336', '63', '更新计量单位', '192.168.100.163', '2017-08-03 23:51:38', '0', '更新计量单位ID为  2 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4337', '63', '登录系统', '192.168.100.163', '2017-08-04 00:42:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4338', '63', '登录系统', '192.168.100.163', '2017-08-04 00:45:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4339', '63', '更新商品', '192.168.100.163', '2017-08-04 00:48:05', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4340', '63', '登录系统', '192.168.100.163', '2017-08-04 20:55:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4341', '63', '登录系统', '192.168.100.163', '2017-08-04 23:16:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4342', '63', '增加商品', '192.168.100.163', '2017-08-05 00:06:46', '0', '增加商品名称为  aaaaa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4343', '63', '增加商品', '192.168.100.163', '2017-08-05 00:08:09', '0', '增加商品名称为  aaaa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4344', '63', '登录系统', '192.168.100.163', '2017-08-05 00:11:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4345', '63', '增加商品', '192.168.100.163', '2017-08-05 00:11:54', '0', '增加商品名称为  aaaa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4346', '63', '删除商品', '192.168.100.163', '2017-08-05 00:11:59', '0', '删除商品ID为  504 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4347', '63', '增加商品', '192.168.100.163', '2017-08-05 00:16:08', '0', '增加商品名称为  bbbbb 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4348', '63', '登录系统', '192.168.100.163', '2017-08-05 00:17:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4349', '63', '增加商品', '192.168.100.163', '2017-08-05 00:18:22', '0', '增加商品名称为  cccc 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4350', '63', '删除商品', '192.168.100.163', '2017-08-05 00:22:00', '0', '删除商品ID为  506 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4351', '63', '增加商品', '192.168.100.163', '2017-08-05 00:22:13', '0', '增加商品名称为  cccc 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4352', '63', '增加商品', '192.168.100.163', '2017-08-05 00:24:23', '0', '增加商品名称为  dddd 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4353', '63', '增加商品', '192.168.100.163', '2017-08-05 00:26:40', '0', '增加商品名称为  eeee 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4354', '63', '登录系统', '192.168.100.163', '2017-08-05 00:30:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4355', '63', '增加商品', '192.168.100.163', '2017-08-05 00:31:10', '0', '增加商品名称为  eewe 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4356', '63', '批量删除商品', '192.168.100.163', '2017-08-05 00:31:19', '0', '批量删除商品ID为  508,509,510 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4357', '63', '批量删除商品', '192.168.100.163', '2017-08-05 00:31:23', '0', '批量删除商品ID为  502,503,505 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4358', '63', '批量删除商品', '192.168.100.163', '2017-08-05 00:31:27', '0', '批量删除商品ID为  507 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4359', '63', '增加商品', '192.168.100.163', '2017-08-05 00:31:38', '0', '增加商品名称为  rrrr 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4360', '63', '批量删除商品', '192.168.100.163', '2017-08-05 00:32:14', '0', '批量删除商品ID为  511 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4361', '63', '增加商品', '192.168.100.163', '2017-08-05 00:32:25', '0', '增加商品名称为  gggg 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4362', '63', '登录系统', '192.168.1.108', '2017-08-06 09:58:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4363', '63', '增加商品', '192.168.1.108', '2017-08-06 10:22:35', '1', '增加商品名称为  棉线123 失败！', '增加商品失败');
INSERT INTO `jsh_log` VALUES ('4364', '63', '增加商品', '192.168.1.108', '2017-08-06 10:23:00', '1', '增加商品名称为  棉线123 失败！', '增加商品失败');
INSERT INTO `jsh_log` VALUES ('4365', '63', '增加商品', '192.168.1.108', '2017-08-06 10:28:28', '0', '增加商品名称为  ddd 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4366', '63', '删除商品', '192.168.1.108', '2017-08-06 10:30:03', '0', '删除商品ID为  513 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4367', '63', '增加商品', '192.168.1.108', '2017-08-06 10:30:28', '0', '增加商品名称为  yyyy 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4368', '63', '登录系统', '192.168.1.108', '2017-08-06 11:40:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4369', '63', '增加商品', '192.168.1.108', '2017-08-06 12:55:58', '0', '增加商品名称为  555ffff 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4370', '63', '增加商品', '192.168.1.108', '2017-08-06 12:56:59', '0', '增加商品名称为  酸奶 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4371', '63', '更新商品', '192.168.1.108', '2017-08-06 12:57:36', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4372', '63', '更新商品', '192.168.1.108', '2017-08-06 12:57:44', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4373', '63', '更新商品', '192.168.1.108', '2017-08-06 12:58:10', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4374', '63', '更新商品', '192.168.1.108', '2017-08-06 13:02:49', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4375', '63', '更新商品', '192.168.1.108', '2017-08-06 13:03:06', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4376', '63', '登录系统', '192.168.1.108', '2017-08-06 13:04:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4377', '63', '更新商品', '192.168.1.108', '2017-08-06 13:04:58', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4378', '63', '更新商品', '192.168.1.108', '2017-08-06 13:05:05', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4379', '63', '更新商品', '192.168.1.108', '2017-08-06 13:05:57', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4380', '63', '更新商品', '192.168.1.108', '2017-08-06 13:07:31', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4381', '63', '登录系统', '192.168.1.108', '2017-08-06 13:10:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4382', '63', '更新商品', '192.168.1.108', '2017-08-06 13:10:53', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4383', '63', '更新商品', '192.168.1.108', '2017-08-06 13:16:13', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4384', '63', '更新商品', '192.168.1.108', '2017-08-06 13:17:23', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4385', '63', '更新商品', '192.168.1.108', '2017-08-06 13:24:44', '0', '更新商品ID为  514 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4386', '63', '增加单据', '192.168.1.108', '2017-08-06 13:39:58', '0', '增加单据编号为  CGRK20170806133910 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4387', '63', '保存仓管通明细', '192.168.1.108', '2017-08-06 13:39:59', '0', '保存仓管通明细对应主表编号为  25 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4388', '63', '登录系统', '192.168.100.163', '2017-08-06 18:27:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4389', '63', '增加单据', '192.168.100.163', '2017-08-06 18:58:15', '0', '增加单据编号为  CGRK20170806184916 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4390', '63', '保存仓管通明细', '192.168.100.163', '2017-08-06 18:58:15', '0', '保存仓管通明细对应主表编号为  26 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4391', '63', '批量修改商品状态', '192.168.100.163', '2017-08-06 19:19:26', '0', '批量修改状态，商品ID为  499 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4392', '63', '批量修改商品状态', '192.168.100.163', '2017-08-06 19:19:32', '0', '批量修改状态，商品ID为  499 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4393', '63', '批量修改商品状态', '192.168.100.163', '2017-08-06 19:19:37', '0', '批量修改状态，商品ID为  499 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4394', '63', '更新商品', '192.168.100.163', '2017-08-06 19:21:27', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4395', '63', '更新商品', '192.168.100.163', '2017-08-06 19:21:41', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4396', '63', '更新商品', '192.168.100.163', '2017-08-06 19:21:59', '0', '更新商品ID为  500 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4397', '63', '更新商品', '192.168.100.163', '2017-08-06 19:42:50', '0', '更新商品ID为  487 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4398', '63', '更新商品', '192.168.100.163', '2017-08-06 19:43:00', '0', '更新商品ID为  498 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4399', '63', '更新商品', '192.168.100.163', '2017-08-06 19:43:06', '0', '更新商品ID为  499 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4400', '63', '登录系统', '192.168.100.163', '2017-08-06 19:57:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4401', '63', '更新商品', '192.168.100.163', '2017-08-06 21:31:34', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4402', '63', '更新商品', '192.168.100.163', '2017-08-06 21:32:23', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4403', '63', '增加单据', '192.168.100.163', '2017-08-06 23:15:29', '0', '增加单据编号为  XSCK20170806231357 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4404', '63', '保存仓管通明细', '192.168.100.163', '2017-08-06 23:15:29', '0', '保存仓管通明细对应主表编号为  27 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4405', '63', '更新供应商', '192.168.100.163', '2017-08-06 23:19:43', '0', '更新供应商ID为  1 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('4406', '63', '登录系统', '192.168.100.163', '2017-08-06 23:49:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4407', '63', '更新供应商', '192.168.100.163', '2017-08-06 23:55:46', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('4408', '63', '增加单据', '192.168.100.163', '2017-08-07 00:22:28', '0', '增加单据编号为  CGRK201708070211 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4409', '63', '保存仓管通明细', '192.168.100.163', '2017-08-07 00:22:28', '0', '保存仓管通明细对应主表编号为  28 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4410', '63', '登录系统', '192.168.100.163', '2017-08-07 20:15:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4411', '63', '更新商品', '192.168.100.163', '2017-08-07 20:16:30', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4412', '63', '更新商品', '192.168.100.163', '2017-08-07 20:17:31', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4413', '63', '登录系统', '192.168.100.163', '2017-08-07 21:00:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4414', '63', '登录系统', '192.168.100.163', '2017-08-07 21:31:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4415', '63', '登录系统', '192.168.100.163', '2017-08-07 21:43:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4416', '63', '登录系统', '192.168.100.163', '2017-08-07 23:29:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4417', '63', '登录系统', '192.168.100.163', '2017-08-07 23:37:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4418', '63', '登录系统', '192.168.100.163', '2017-08-07 23:42:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4419', '63', '登录系统', '192.168.100.163', '2017-08-08 00:16:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4420', '63', '登录系统', '192.168.100.163', '2017-08-08 00:29:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4421', '63', '删除单据', '192.168.100.163', '2017-08-08 00:35:18', '0', '删除单据ID为  24 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4422', '63', '登录系统', '192.168.100.163', '2017-08-08 21:54:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4423', '63', '登录系统', '192.168.100.163', '2017-08-08 22:23:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4424', '63', '增加单据', '192.168.100.163', '2017-08-08 22:52:54', '0', '增加单据编号为  CGRK20170808225118 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4425', '63', '保存仓管通明细', '192.168.100.163', '2017-08-08 22:52:54', '0', '保存仓管通明细对应主表编号为  29 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4426', '63', '更新单据', '192.168.100.163', '2017-08-08 22:53:09', '0', '更新单据ID为  29 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4427', '63', '增加单据', '192.168.100.163', '2017-08-08 22:56:18', '0', '增加单据编号为  XSCK20170808225524 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4428', '63', '保存仓管通明细', '192.168.100.163', '2017-08-08 22:56:19', '0', '保存仓管通明细对应主表编号为  30 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4429', '63', '增加单据', '192.168.100.163', '2017-08-08 23:05:07', '0', '增加单据编号为  CGRK2017080823318 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4430', '63', '保存仓管通明细', '192.168.100.163', '2017-08-08 23:05:07', '0', '保存仓管通明细对应主表编号为  31 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4431', '63', '增加单据', '192.168.100.163', '2017-08-08 23:08:08', '0', '增加单据编号为  CGRK2017080823510 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4432', '63', '保存仓管通明细', '192.168.100.163', '2017-08-08 23:08:08', '0', '保存仓管通明细对应主表编号为  32 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4433', '63', '更新单据', '192.168.100.163', '2017-08-08 23:11:24', '0', '更新单据ID为  32 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4434', '63', '删除单据', '192.168.100.163', '2017-08-08 23:37:07', '0', '删除单据ID为  19 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4435', '63', '删除单据', '192.168.100.163', '2017-08-08 23:37:16', '0', '删除单据ID为  21 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4436', '63', '增加单据', '192.168.100.163', '2017-08-09 00:32:02', '0', '增加单据编号为  CGRK2017080903134 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4437', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 00:32:02', '0', '保存仓管通明细对应主表编号为  33 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4438', '63', '登录系统', '192.168.100.163', '2017-08-09 00:33:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4439', '63', '增加单据', '192.168.100.163', '2017-08-09 00:34:25', '0', '增加单据编号为  CGRK201708090340 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4440', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 00:34:25', '0', '保存仓管通明细对应主表编号为  34 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4441', '63', '登录系统', '192.168.100.163', '2017-08-09 21:25:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4442', '63', '登录系统', '192.168.100.163', '2017-08-09 21:32:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4443', '63', '增加单据', '192.168.100.163', '2017-08-09 21:33:22', '0', '增加单据编号为  CGRK20170809213253 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4444', '63', '登录系统', '192.168.100.163', '2017-08-09 21:37:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4445', '63', '增加单据', '192.168.100.163', '2017-08-09 21:38:07', '0', '增加单据编号为  CGRK20170809213743 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4446', '63', '登录系统', '192.168.100.163', '2017-08-09 21:41:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4447', '63', '增加单据', '192.168.100.163', '2017-08-09 21:42:24', '0', '增加单据编号为  CGRK2017080921423 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4448', '63', '登录系统', '192.168.100.163', '2017-08-09 21:44:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4449', '63', '增加单据', '192.168.100.163', '2017-08-09 21:45:10', '0', '增加单据编号为  CGRK20170809214448 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4450', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 21:45:10', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4451', '63', '登录系统', '192.168.100.163', '2017-08-09 21:51:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4452', '63', '增加单据', '192.168.100.163', '2017-08-09 21:51:39', '0', '增加单据编号为  CGRK20170809215119 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4453', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 21:51:40', '0', '保存仓管通明细对应主表编号为  39 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4454', '63', '登录系统', '192.168.100.163', '2017-08-09 22:25:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4455', '63', '增加单据', '192.168.100.163', '2017-08-09 22:25:51', '0', '增加单据编号为  CGRK20170809222524 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4456', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:25:51', '0', '保存仓管通明细对应主表编号为  40 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4457', '63', '登录系统', '192.168.100.163', '2017-08-09 22:31:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4458', '63', '增加单据', '192.168.100.163', '2017-08-09 22:32:10', '0', '增加单据编号为  CGRK20170809223154 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4459', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:32:10', '0', '保存仓管通明细对应主表编号为  41 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4460', '63', '登录系统', '192.168.100.163', '2017-08-09 22:36:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4461', '63', '增加单据', '192.168.100.163', '2017-08-09 22:36:39', '0', '增加单据编号为  CGRK20170809223614 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4462', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:36:39', '0', '保存仓管通明细对应主表编号为  42 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4463', '63', '登录系统', '192.168.100.163', '2017-08-09 22:40:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4464', '63', '增加单据', '192.168.100.163', '2017-08-09 22:40:30', '0', '增加单据编号为  CGRK20170809224010 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4465', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:40:30', '0', '保存仓管通明细对应主表编号为  43 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4466', '63', '登录系统', '192.168.100.163', '2017-08-09 22:41:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4467', '63', '增加单据', '192.168.100.163', '2017-08-09 22:42:18', '0', '增加单据编号为  CGRK2017080922422 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4468', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:42:19', '0', '保存仓管通明细对应主表编号为  44 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4469', '63', '登录系统', '192.168.100.163', '2017-08-09 22:44:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4470', '63', '增加单据', '192.168.100.163', '2017-08-09 22:45:24', '0', '增加单据编号为  CGRK2017080922451 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4471', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:45:25', '0', '保存仓管通明细对应主表编号为  45 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4472', '63', '登录系统', '192.168.100.163', '2017-08-09 22:47:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4473', '63', '增加单据', '192.168.100.163', '2017-08-09 22:47:51', '0', '增加单据编号为  CGRK20170809224731 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4474', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:47:51', '0', '保存仓管通明细对应主表编号为  46 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4475', '63', '登录系统', '192.168.100.163', '2017-08-09 22:50:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4476', '63', '增加单据', '192.168.100.163', '2017-08-09 22:51:00', '0', '增加单据编号为  CGRK20170809225043 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4477', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 22:51:00', '0', '保存仓管通明细对应主表编号为  47 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4478', '63', '登录系统', '192.168.100.163', '2017-08-09 23:02:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4479', '63', '增加单据', '192.168.100.163', '2017-08-09 23:03:05', '0', '增加单据编号为  CGRK2017080923247 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4480', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 23:03:05', '0', '保存仓管通明细对应主表编号为  48 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4481', '63', '登录系统', '192.168.100.163', '2017-08-09 23:05:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4482', '63', '增加单据', '192.168.100.163', '2017-08-09 23:05:34', '0', '增加单据编号为  CGRK2017080923516 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4483', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 23:05:34', '0', '保存仓管通明细对应主表编号为  49 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4484', '63', '登录系统', '192.168.100.163', '2017-08-09 23:07:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4485', '63', '增加单据', '192.168.100.163', '2017-08-09 23:07:37', '0', '增加单据编号为  CGRK2017080923724 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4486', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 23:07:38', '0', '保存仓管通明细对应主表编号为  50 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4487', '63', '登录系统', '192.168.100.163', '2017-08-09 23:10:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4488', '63', '增加单据', '192.168.100.163', '2017-08-09 23:11:19', '0', '增加单据编号为  CGRK2017080923111 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4489', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 23:11:19', '0', '保存仓管通明细对应主表编号为  51 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4490', '63', '登录系统', '192.168.100.163', '2017-08-09 23:12:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4491', '63', '增加单据', '192.168.100.163', '2017-08-09 23:13:04', '0', '增加单据编号为  CGRK20170809231251 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4492', '63', '保存仓管通明细', '192.168.100.163', '2017-08-09 23:13:04', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4493', '63', '登录系统', '192.168.100.163', '2017-08-10 00:03:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4494', '63', '增加单据', '192.168.100.163', '2017-08-10 00:03:43', '0', '增加单据编号为  CGRK201708100324 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4495', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:03:43', '0', '保存仓管通明细对应主表编号为  53 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4496', '63', '删除计量单位', '192.168.100.163', '2017-08-10 00:04:07', '0', '删除计量单位ID为  12 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4497', '63', '增加计量单位', '192.168.100.163', '2017-08-10 00:04:11', '0', '增加计量单位名称为  12,123(1:123) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4498', '63', '更新计量单位', '192.168.100.163', '2017-08-10 00:04:16', '0', '更新计量单位ID为  13 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4499', '63', '更新计量单位', '192.168.100.163', '2017-08-10 00:04:20', '0', '更新计量单位ID为  13 成功！', '更新计量单位成功');
INSERT INTO `jsh_log` VALUES ('4500', '63', '批量删除计量单位', '192.168.100.163', '2017-08-10 00:04:26', '0', '批量删除计量单位ID为  13 成功！', '批量删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4501', '63', '增加计量单位', '192.168.100.163', '2017-08-10 00:04:30', '0', '增加计量单位名称为  sdf,sdf(1:sdf) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4502', '63', '删除计量单位', '192.168.100.163', '2017-08-10 00:04:36', '0', '删除计量单位ID为  14 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4503', '63', '删除单据', '192.168.100.163', '2017-08-10 00:04:58', '0', '删除单据ID为  53 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4504', '63', '登录系统', '192.168.100.163', '2017-08-10 00:07:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4505', '63', '增加单据', '192.168.100.163', '2017-08-10 00:07:31', '0', '增加单据编号为  CGRK201708100716 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4506', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:07:32', '0', '保存仓管通明细对应主表编号为  54 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4507', '63', '登录系统', '192.168.100.163', '2017-08-10 00:26:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4508', '63', '增加单据', '192.168.100.163', '2017-08-10 00:26:49', '0', '增加单据编号为  CGRK2017081002629 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4509', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:26:49', '0', '保存仓管通明细对应主表编号为  55 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4510', '63', '登录系统', '192.168.100.163', '2017-08-10 00:31:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4511', '63', '增加单据', '192.168.100.163', '2017-08-10 00:32:00', '0', '增加单据编号为  CGRK2017081003144 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4512', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:32:01', '0', '保存仓管通明细对应主表编号为  56 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4513', '63', '登录系统', '192.168.100.163', '2017-08-10 00:33:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4514', '63', '增加单据', '192.168.100.163', '2017-08-10 00:34:13', '0', '增加单据编号为  CGRK2017081003357 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4515', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:34:13', '0', '保存仓管通明细对应主表编号为  57 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4516', '63', '登录系统', '192.168.100.163', '2017-08-10 00:39:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4517', '63', '增加单据', '192.168.100.163', '2017-08-10 00:39:34', '0', '增加单据编号为  CGRK2017081003912 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4518', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:39:35', '0', '保存仓管通明细对应主表编号为  58 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4519', '63', '批量删除单据', '192.168.100.163', '2017-08-10 00:39:57', '0', '批量删除单据ID为  58,57,56,55,54,52,51,50,49,48 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4520', '63', '批量删除单据', '192.168.100.163', '2017-08-10 00:40:00', '0', '批量删除单据ID为  47,46,45,44,43,42,41,40,39,38 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4521', '63', '增加单据', '192.168.100.163', '2017-08-10 00:40:28', '0', '增加单据编号为  CGRK2017081004011 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4522', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:40:28', '0', '保存仓管通明细对应主表编号为  59 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4523', '63', '增加单据', '192.168.100.163', '2017-08-10 00:41:02', '0', '增加单据编号为  CGRK2017081004041 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4524', '63', '保存仓管通明细', '192.168.100.163', '2017-08-10 00:41:02', '0', '保存仓管通明细对应主表编号为  60 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4525', '63', '登录系统', '192.168.100.163', '2017-08-10 00:44:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4526', '63', '删除单据', '192.168.100.163', '2017-08-10 00:45:09', '0', '删除单据ID为  60 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4527', '63', '登录系统', '192.168.100.163', '2017-08-10 21:21:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4528', '63', '增加单据', '192.168.100.163', '2017-08-11 00:54:03', '0', '增加单据编号为  CGRK2017081105027 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4529', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 00:54:03', '0', '保存仓管通明细对应主表编号为  60 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4530', '63', '登录系统', '192.168.100.163', '2017-08-11 20:49:41', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4531', '63', '登录系统', '192.168.100.163', '2017-08-11 22:04:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4532', '63', '批量删除单据', '192.168.100.163', '2017-08-11 22:33:48', '0', '批量删除单据ID为  60,59,37,36,35,34,33,32,31,29 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4533', '63', '批量删除单据', '192.168.100.163', '2017-08-11 22:33:51', '0', '批量删除单据ID为  28,26,25,23,22 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4534', '63', '批量删除单据', '192.168.100.163', '2017-08-11 22:34:06', '0', '批量删除单据ID为  30,27,20,17 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('4535', '63', '增加单据', '192.168.100.163', '2017-08-11 22:35:23', '0', '增加单据编号为  CGRK20170811223416 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4536', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 22:35:23', '0', '保存仓管通明细对应主表编号为  1 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4537', '63', '登录系统', '192.168.100.163', '2017-08-11 22:46:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4538', '63', '删除单据', '192.168.100.163', '2017-08-11 22:46:56', '0', '删除单据ID为  1 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4539', '63', '增加单据', '192.168.100.163', '2017-08-11 22:47:21', '0', '增加单据编号为  CGRK20170811224658 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4540', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 22:47:22', '0', '保存仓管通明细对应主表编号为  2 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4541', '63', '登录系统', '192.168.100.163', '2017-08-11 22:57:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4542', '63', '删除单据', '192.168.100.163', '2017-08-11 22:57:54', '0', '删除单据ID为  2 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4543', '63', '增加单据', '192.168.100.163', '2017-08-11 22:58:32', '0', '增加单据编号为  CGRK20170811225755 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4544', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 22:58:32', '0', '保存仓管通明细对应主表编号为  3 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4545', '63', '登录系统', '192.168.100.163', '2017-08-11 23:08:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4546', '63', '更新单据', '192.168.100.163', '2017-08-11 23:13:15', '0', '更新单据ID为  3 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4547', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 23:13:16', '0', '保存仓管通明细对应主表编号为  3 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4548', '63', '增加单据', '192.168.100.163', '2017-08-11 23:42:54', '0', '增加单据编号为  CGRK2017081123385 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4549', '63', '保存仓管通明细', '192.168.100.163', '2017-08-11 23:42:55', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4550', '63', '增加单据', '192.168.100.163', '2017-08-12 00:18:02', '0', '增加单据编号为  CGRK2017081201727 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4551', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 00:18:02', '0', '保存仓管通明细对应主表编号为  5 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4552', '63', '删除单据', '192.168.100.163', '2017-08-12 00:18:34', '0', '删除单据ID为  5 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4553', '63', '更新单据', '192.168.100.163', '2017-08-12 00:38:54', '0', '更新单据ID为  4 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4554', '63', '更新单据', '192.168.100.163', '2017-08-12 00:39:56', '0', '更新单据ID为  4 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4555', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 00:39:57', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4556', '63', '更新单据', '192.168.100.163', '2017-08-12 00:41:51', '0', '更新单据ID为  4 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4557', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 00:41:51', '0', '保存仓管通明细对应主表编号为  4 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4558', '63', '更新单据', '192.168.100.163', '2017-08-12 00:42:33', '0', '更新单据ID为  4 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4559', '63', '登录系统', '192.168.100.163', '2017-08-12 11:29:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4560', '63', '登录系统', '192.168.100.163', '2017-08-12 11:56:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4561', '63', '增加单据', '192.168.100.163', '2017-08-12 11:57:37', '0', '增加单据编号为  CGRK2017081211572 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4562', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 11:57:37', '0', '保存仓管通明细对应主表编号为  5 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4563', '63', '增加单据', '192.168.100.163', '2017-08-12 12:01:02', '0', '增加单据编号为  CGRK2017081212045 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4564', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 12:01:02', '0', '保存仓管通明细对应主表编号为  6 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4565', '63', '登录系统', '192.168.100.163', '2017-08-12 12:03:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4566', '63', '增加单据', '192.168.100.163', '2017-08-12 12:04:07', '0', '增加单据编号为  CGRK2017081212323 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4567', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 12:04:07', '0', '保存仓管通明细对应主表编号为  7 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4568', '63', '登录系统', '192.168.100.163', '2017-08-12 13:10:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4569', '63', '更新单据', '192.168.100.163', '2017-08-12 13:13:20', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4570', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 13:13:20', '0', '保存仓管通明细对应主表编号为  7 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4571', '63', '更新单据', '192.168.100.163', '2017-08-12 13:13:53', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4572', '63', '更新单据', '192.168.100.163', '2017-08-12 13:14:04', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4573', '63', '更新单据', '192.168.100.163', '2017-08-12 13:14:26', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4574', '63', '删除单据', '192.168.100.163', '2017-08-12 15:36:01', '0', '删除单据ID为  6 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4575', '63', '删除单据', '192.168.100.163', '2017-08-12 15:36:03', '0', '删除单据ID为  5 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4576', '63', '删除单据', '192.168.100.163', '2017-08-12 15:36:05', '0', '删除单据ID为  4 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4577', '63', '删除单据', '192.168.100.163', '2017-08-12 15:36:11', '0', '删除单据ID为  3 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4578', '63', '更新单据', '192.168.100.163', '2017-08-12 15:53:00', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4579', '63', '更新单据', '192.168.100.163', '2017-08-12 16:03:25', '0', '更新单据ID为  7 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4580', '63', '登录系统', '192.168.100.163', '2017-08-12 17:25:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4581', '63', '登录系统', '192.168.100.163', '2017-08-12 17:57:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4582', '63', '登录系统', '192.168.100.163', '2017-08-12 18:06:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4583', '63', '登录系统', '192.168.100.163', '2017-08-12 18:09:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4584', '63', '增加单据', '192.168.100.163', '2017-08-12 18:10:14', '0', '增加单据编号为  XHDD20170812001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4585', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 18:10:14', '0', '保存仓管通明细对应主表编号为  8 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4586', '63', '登录系统', '192.168.100.163', '2017-08-12 19:56:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4587', '63', '登录系统', '192.168.100.163', '2017-08-12 21:00:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4588', '63', '增加单据', '192.168.100.163', '2017-08-12 21:01:09', '0', '增加单据编号为  GHDD201708120003 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4589', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 21:01:09', '0', '保存仓管通明细对应主表编号为  9 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4590', '63', '登录系统', '192.168.100.163', '2017-08-12 21:06:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4591', '63', '登录系统', '192.168.100.163', '2017-08-12 21:10:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4592', '63', '增加单据', '192.168.100.163', '2017-08-12 21:10:42', '0', '增加单据编号为  GHDD201708120004 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4593', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 21:10:42', '0', '保存仓管通明细对应主表编号为  10 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4594', '63', '登录系统', '192.168.100.163', '2017-08-12 22:06:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4595', '63', '增加单据', '192.168.100.163', '2017-08-12 22:07:44', '0', '增加单据编号为  jshenghua001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4596', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:07:45', '0', '保存仓管通明细对应主表编号为  11 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4597', '63', '登录系统', '192.168.100.163', '2017-08-12 22:13:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4598', '63', '登录系统', '192.168.100.163', '2017-08-12 22:14:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4599', '63', '增加单据', '192.168.100.163', '2017-08-12 22:17:11', '0', '增加单据编号为  GHDD201708120006 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4600', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:17:11', '0', '保存仓管通明细对应主表编号为  12 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4601', '63', '增加单据', '192.168.100.163', '2017-08-12 22:17:52', '0', '增加单据编号为  jishenghua3 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4602', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:17:52', '0', '保存仓管通明细对应主表编号为  13 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4603', '63', '增加单据', '192.168.100.163', '2017-08-12 22:19:37', '0', '增加单据编号为  jishenghua004 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4604', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:19:37', '0', '保存仓管通明细对应主表编号为  14 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4605', '63', '更新单据', '192.168.100.163', '2017-08-12 22:21:05', '0', '更新单据ID为  14 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4606', '63', '增加单据', '192.168.100.163', '2017-08-12 22:21:33', '0', '增加单据编号为  jishenghua004 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4607', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:21:33', '0', '保存仓管通明细对应主表编号为  15 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4608', '63', '删除单据', '192.168.100.163', '2017-08-12 22:23:02', '0', '删除单据ID为  15 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4609', '63', '增加单据', '192.168.100.163', '2017-08-12 22:26:23', '0', '增加单据编号为  jishenghua005 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4610', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:26:23', '0', '保存仓管通明细对应主表编号为  16 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4611', '63', '更新单据', '192.168.100.163', '2017-08-12 22:27:23', '0', '更新单据ID为  16 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4612', '63', '更新单据', '192.168.100.163', '2017-08-12 22:27:32', '0', '更新单据ID为  12 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4613', '63', '更新单据', '192.168.100.163', '2017-08-12 22:27:38', '0', '更新单据ID为  16 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4614', '63', '增加单据', '192.168.100.163', '2017-08-12 22:28:20', '0', '增加单据编号为  GHDD201708120010 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4615', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:28:20', '0', '保存仓管通明细对应主表编号为  17 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4616', '63', '登录系统', '192.168.100.163', '2017-08-12 22:29:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4617', '63', '增加单据', '192.168.100.163', '2017-08-12 22:30:08', '0', '增加单据编号为  GHDD201708120011 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4618', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:30:09', '0', '保存仓管通明细对应主表编号为  18 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4619', '63', '增加单据', '192.168.100.163', '2017-08-12 22:30:57', '0', '增加单据编号为  GHDD201708120011 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4620', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:30:57', '0', '保存仓管通明细对应主表编号为  19 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4621', '63', '登录系统', '192.168.100.163', '2017-08-12 22:45:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4622', '63', '登录系统', '192.168.100.163', '2017-08-12 22:45:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4623', '63', '增加单据', '192.168.100.163', '2017-08-12 22:46:43', '0', '增加单据编号为  GHDD201708120013 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4624', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:46:44', '0', '保存仓管通明细对应主表编号为  20 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4625', '63', '增加单据', '192.168.100.163', '2017-08-12 22:46:52', '0', '增加单据编号为  GHDD201708120013 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4626', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 22:46:52', '0', '保存仓管通明细对应主表编号为  21 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4627', '63', '更新单据', '192.168.100.163', '2017-08-12 22:50:14', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4628', '63', '更新单据', '192.168.100.163', '2017-08-12 22:52:34', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4629', '63', '更新单据', '192.168.100.163', '2017-08-12 22:52:54', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4630', '63', '更新单据', '192.168.100.163', '2017-08-12 23:09:34', '0', '更新单据ID为  21 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4631', '63', '登录系统', '192.168.100.163', '2017-08-12 23:48:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4632', '63', '增加单据', '192.168.100.163', '2017-08-12 23:49:32', '0', '增加单据编号为  GHDD201708120015 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4633', '63', '保存仓管通明细', '192.168.100.163', '2017-08-12 23:49:33', '0', '保存仓管通明细对应主表编号为  22 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4634', '63', '登录系统', '192.168.100.163', '2017-08-13 00:41:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4635', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 00:44:46', '0', '批量修改状态，单据ID为  22 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4636', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 00:44:49', '0', '批量修改状态，单据ID为  22 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4637', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 00:44:53', '0', '批量修改状态，单据ID为  22 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4638', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 00:44:56', '0', '批量修改状态，单据ID为  22 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4639', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 00:44:59', '0', '批量修改状态，单据ID为  21 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4640', '63', '批量修改单据状态', '192.168.100.163', '2017-08-13 01:03:18', '0', '批量修改状态，单据ID为  19 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4641', '63', '登录系统', '192.168.100.163', '2017-08-13 22:15:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4642', '63', '登录系统', '192.168.100.163', '2017-08-13 22:16:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4643', '63', '登录系统', '192.168.100.163', '2017-08-13 22:18:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4644', '63', '登录系统', '192.168.100.163', '2017-08-13 22:25:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4645', '63', '登录系统', '192.168.100.163', '2017-08-13 23:16:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4646', '63', '增加计量单位', '192.168.100.163', '2017-08-13 23:21:53', '0', '增加计量单位名称为  12341,233(1:22) 成功！', '增加计量单位成功');
INSERT INTO `jsh_log` VALUES ('4647', '63', '删除计量单位', '192.168.100.163', '2017-08-13 23:21:57', '0', '删除计量单位ID为  12 成功！', '删除计量单位成功');
INSERT INTO `jsh_log` VALUES ('4648', '63', '更新商品', '192.168.100.163', '2017-08-13 23:22:14', '0', '更新商品ID为  516 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4649', '63', '增加商品', '192.168.100.163', '2017-08-13 23:25:45', '0', '增加商品名称为  3123 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4650', '63', '更新商品', '192.168.100.163', '2017-08-13 23:26:11', '0', '更新商品ID为  517 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4651', '63', '更新商品', '192.168.100.163', '2017-08-13 23:26:23', '0', '更新商品ID为  517 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4652', '63', '登录系统', '192.168.100.163', '2017-08-14 00:26:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4653', '63', '登录系统', '192.168.100.163', '2017-08-14 00:26:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4654', '63', '登录系统', '192.168.100.163', '2017-08-14 19:52:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4655', '63', '批量修改商品状态', '192.168.100.163', '2017-08-14 20:03:45', '0', '批量修改状态，商品ID为  485 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4656', '63', '批量修改商品状态', '192.168.100.163', '2017-08-14 20:03:49', '0', '批量修改状态，商品ID为  485 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4657', '63', '批量修改商品状态', '192.168.100.163', '2017-08-14 20:04:07', '0', '批量修改状态，商品ID为  516 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4658', '63', '登录系统', '192.168.100.163', '2017-08-14 20:08:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4659', '63', '批量修改商品状态', '192.168.100.163', '2017-08-14 20:08:56', '0', '批量修改状态，商品ID为  516 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4660', '63', '增加单据', '192.168.100.163', '2017-08-14 20:41:54', '0', '增加单据编号为  GHDD201708140001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4661', '63', '保存仓管通明细', '192.168.100.163', '2017-08-14 20:41:54', '0', '保存仓管通明细对应主表编号为  23 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4662', '63', '登录系统', '192.168.100.163', '2017-08-14 23:29:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4663', '63', '登录系统', '192.168.100.163', '2017-08-14 23:46:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4664', '63', '批量修改单据状态', '192.168.100.163', '2017-08-15 00:14:50', '0', '批量修改状态，单据ID为  8 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4665', '63', '登录系统', '192.168.100.163', '2017-08-15 20:51:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4666', '63', '批量删除商品', '192.168.100.163', '2017-08-15 21:23:12', '0', '批量删除商品ID为  512 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4667', '63', '批量删除商品', '192.168.100.163', '2017-08-15 21:23:15', '0', '批量删除商品ID为  514 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4668', '63', '批量删除商品', '192.168.100.163', '2017-08-15 21:23:18', '0', '批量删除商品ID为  515 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4669', '63', '批量删除商品', '192.168.100.163', '2017-08-15 21:23:22', '0', '批量删除商品ID为  516 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4670', '63', '批量修改单据状态', '192.168.100.163', '2017-08-15 21:25:31', '0', '批量修改状态，单据ID为  18,17,16 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('4671', '63', '增加商品', '192.168.100.163', '2017-08-15 21:35:09', '0', '增加商品名称为  安慕希 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4672', '63', '更新商品', '192.168.100.163', '2017-08-15 21:35:29', '0', '更新商品ID为  518 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4673', '63', '增加单据', '192.168.100.163', '2017-08-15 21:36:25', '0', '增加单据编号为  GHDD201708150001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4674', '63', '保存仓管通明细', '192.168.100.163', '2017-08-15 21:36:25', '0', '保存仓管通明细对应主表编号为  24 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4675', '63', '删除商品', '192.168.100.163', '2017-08-15 21:36:34', '1', '删除商品ID为  518 失败！', '删除商品失败');
INSERT INTO `jsh_log` VALUES ('4676', '63', '增加单据', '192.168.100.163', '2017-08-15 22:31:46', '0', '增加单据编号为  GHDD201708150002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4677', '63', '保存仓管通明细', '192.168.100.163', '2017-08-15 22:31:46', '0', '保存仓管通明细对应主表编号为  25 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4678', '63', '登录系统', '192.168.100.163', '2017-08-15 22:49:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4679', '63', '登录系统', '192.168.100.163', '2017-08-16 20:58:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4680', '63', '增加单据', '192.168.100.163', '2017-08-16 23:50:35', '0', '增加单据编号为  GHDD201708160001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4681', '63', '保存仓管通明细', '192.168.100.163', '2017-08-16 23:50:36', '0', '保存仓管通明细对应主表编号为  26 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4682', '63', '增加供应商', '192.168.100.163', '2017-08-17 00:21:04', '0', '增加供应商名称为  南通居梦莱家纺 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4683', '63', '更新UserBusiness', '192.168.100.163', '2017-08-17 00:21:21', '0', '更新UserBusiness的ID为  27 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4684', '63', '登录系统', '192.168.100.163', '2017-08-17 21:04:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4685', '63', '增加供应商', '192.168.100.163', '2017-08-17 21:49:30', '0', '增加供应商名称为  aaa 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4686', '63', '增加供应商', '192.168.100.163', '2017-08-17 21:50:34', '0', '增加供应商名称为  bbb 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4687', '63', '增加供应商', '192.168.100.163', '2017-08-17 21:53:40', '0', '增加供应商名称为  ffff 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4688', '63', '增加供应商', '192.168.100.163', '2017-08-17 21:55:38', '0', '增加供应商名称为  6666 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4689', '63', '增加供应商', '192.168.100.163', '2017-08-17 21:55:46', '0', '增加供应商名称为  cccc 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4690', '63', '批量删除供应商', '192.168.100.163', '2017-08-17 21:56:25', '0', '批量删除供应商ID为  51,50,49 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4691', '63', '更新功能', '192.168.100.163', '2017-08-17 22:18:51', '0', '更新功能ID为  26 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4692', '63', '增加供应商', '192.168.100.163', '2017-08-17 22:23:03', '0', '增加供应商名称为  555555 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4693', '63', '删除供应商', '192.168.100.163', '2017-08-17 22:24:10', '0', '删除供应商ID为  52,名称为  555555成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4694', '63', '增加商品', '192.168.100.163', '2017-08-17 22:33:16', '0', '增加商品名称为  5656 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4695', '63', '批量删除商品', '192.168.100.163', '2017-08-17 22:36:12', '0', '批量删除商品ID为  519 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4696', '63', '增加仓库', '192.168.100.163', '2017-08-17 23:05:00', '0', '增加仓库名称为  aaaa 成功！', '增加仓库成功');
INSERT INTO `jsh_log` VALUES ('4697', '63', '删除仓库', '192.168.100.163', '2017-08-17 23:05:16', '0', '删除仓库ID为  7 成功！', '删除仓库成功');
INSERT INTO `jsh_log` VALUES ('4698', '63', '增加供应商', '192.168.100.163', '2017-08-17 23:07:39', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4699', '63', '删除供应商', '192.168.100.163', '2017-08-17 23:07:45', '0', '删除供应商ID为  53,名称为  成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4700', '63', '增加供应商', '192.168.100.163', '2017-08-17 23:07:48', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4701', '63', '删除供应商', '192.168.100.163', '2017-08-17 23:07:51', '0', '删除供应商ID为  54,名称为  成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4702', '63', '增加供应商', '192.168.100.163', '2017-08-17 23:08:08', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4703', '63', '删除供应商', '192.168.100.163', '2017-08-17 23:08:11', '0', '删除供应商ID为  55,名称为  成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4704', '63', '增加供应商', '192.168.100.163', '2017-08-17 23:08:59', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4705', '63', '删除供应商', '192.168.100.163', '2017-08-17 23:09:04', '0', '删除供应商ID为  56,名称为  成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4706', '63', '增加供应商', '192.168.100.163', '2017-08-17 23:13:38', '0', '增加供应商名称为   成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4707', '63', '删除供应商', '192.168.100.163', '2017-08-17 23:13:50', '0', '删除供应商ID为  57,名称为  成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('4708', '63', '更新供应商', '192.168.100.163', '2017-08-17 23:39:35', '0', '更新供应商ID为  41 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('4709', '63', '更新供应商', '192.168.100.163', '2017-08-17 23:39:42', '0', '更新供应商ID为  41 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('4710', '63', '更新单据', '192.168.100.163', '2017-08-17 23:41:42', '0', '更新单据ID为  25 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4711', '63', '更新单据', '192.168.100.163', '2017-08-17 23:41:51', '0', '更新单据ID为  25 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('4712', '63', '增加单据', '192.168.100.163', '2017-08-18 00:25:58', '0', '增加单据编号为  GHDD201708180001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4713', '63', '保存仓管通明细', '192.168.100.163', '2017-08-18 00:25:59', '0', '保存仓管通明细对应主表编号为  27 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4714', '63', '登录系统', '192.168.100.163', '2017-08-18 21:24:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4715', '63', '增加单据', '192.168.100.163', '2017-08-18 21:47:29', '0', '增加单据编号为  GHDD201708180002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('4716', '63', '保存仓管通明细', '192.168.100.163', '2017-08-18 21:47:29', '0', '保存仓管通明细对应主表编号为  28 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('4717', '63', '登录系统', '192.168.100.163', '2017-08-18 23:46:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4718', '63', '登录系统', '192.168.100.163', '2017-08-18 23:50:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4719', '63', '登录系统', '192.168.100.163', '2017-08-18 23:53:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4720', '63', '登录系统', '192.168.100.163', '2017-08-19 00:32:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4721', '63', '增加商品', '192.168.100.163', '2017-08-19 00:32:40', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4722', '63', '增加商品', '192.168.100.163', '2017-08-19 00:33:00', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4723', '63', '删除商品', '192.168.100.163', '2017-08-19 00:33:11', '0', '删除商品ID为  520 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4724', '63', '登录系统', '192.168.1.106', '2017-08-20 09:34:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4725', '63', '增加商品', '192.168.1.106', '2017-08-20 10:25:56', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4726', '63', '批量删除商品', '192.168.1.106', '2017-08-20 10:26:27', '0', '批量删除商品ID为  520 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4727', '63', '增加商品', '192.168.1.106', '2017-08-20 10:29:26', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4728', '63', '更新商品', '192.168.1.106', '2017-08-20 10:29:32', '0', '更新商品ID为  521 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4729', '63', '更新商品', '192.168.1.106', '2017-08-20 10:29:47', '0', '更新商品ID为  521 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4730', '63', '更新商品', '192.168.1.106', '2017-08-20 10:30:34', '0', '更新商品ID为  521 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4731', '63', '增加商品', '192.168.1.106', '2017-08-20 10:42:14', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4732', '63', '批量删除商品', '192.168.1.106', '2017-08-20 10:42:20', '0', '批量删除商品ID为  522 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4733', '63', '增加商品', '192.168.1.106', '2017-08-20 10:43:41', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4734', '63', '删除商品', '192.168.1.106', '2017-08-20 10:43:49', '0', '删除商品ID为  523 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4735', '63', '批量删除商品', '192.168.1.106', '2017-08-20 10:43:53', '0', '批量删除商品ID为  521 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4736', '63', '增加商品', '192.168.1.106', '2017-08-20 10:44:00', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4737', '63', '删除商品', '192.168.1.106', '2017-08-20 10:44:16', '0', '删除商品ID为  524 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4738', '63', '增加商品', '192.168.1.106', '2017-08-20 10:44:29', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4739', '63', '删除商品', '192.168.1.106', '2017-08-20 10:44:40', '0', '删除商品ID为  525 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4740', '63', '增加商品', '192.168.1.106', '2017-08-20 10:46:18', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4741', '63', '增加商品', '192.168.1.106', '2017-08-20 10:50:50', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4742', '63', '删除商品', '192.168.1.106', '2017-08-20 10:50:55', '0', '删除商品ID为  527 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4743', '63', '批量删除商品', '192.168.1.106', '2017-08-20 10:51:01', '0', '批量删除商品ID为  526 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4744', '63', '增加商品', '192.168.1.106', '2017-08-20 10:51:30', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4745', '63', '删除商品', '192.168.1.106', '2017-08-20 10:51:57', '0', '删除商品ID为  528 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4746', '63', '增加商品', '192.168.1.106', '2017-08-20 10:52:06', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4747', '63', '增加商品', '192.168.1.106', '2017-08-20 10:54:36', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4748', '63', '删除商品', '192.168.1.106', '2017-08-20 10:54:49', '0', '删除商品ID为  530 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4749', '63', '删除商品', '192.168.1.106', '2017-08-20 10:54:52', '0', '删除商品ID为  529 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4750', '63', '增加商品', '192.168.1.106', '2017-08-20 10:55:08', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4751', '63', '批量删除商品', '192.168.1.106', '2017-08-20 10:55:13', '0', '批量删除商品ID为  531 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4752', '63', '增加商品', '192.168.1.106', '2017-08-20 10:55:49', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4753', '63', '删除商品', '192.168.1.106', '2017-08-20 10:55:54', '0', '删除商品ID为  532 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4754', '63', '更新商品', '192.168.1.106', '2017-08-20 10:56:35', '0', '更新商品ID为  519 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4755', '63', '增加商品', '192.168.1.106', '2017-08-20 10:57:03', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4756', '63', '删除商品', '192.168.1.106', '2017-08-20 10:57:23', '0', '删除商品ID为  533 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4757', '63', '增加商品', '192.168.1.106', '2017-08-20 10:57:38', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4758', '63', '删除商品', '192.168.1.106', '2017-08-20 10:58:06', '0', '删除商品ID为  534 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4759', '63', '增加商品', '192.168.1.106', '2017-08-20 10:58:19', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4760', '63', '登录系统', '192.168.1.106', '2017-08-20 11:00:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4761', '63', '删除商品', '192.168.1.106', '2017-08-20 11:00:39', '0', '删除商品ID为  535 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4762', '63', '增加商品', '192.168.1.106', '2017-08-20 11:00:54', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4763', '63', '增加商品', '192.168.1.106', '2017-08-20 11:02:43', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4764', '63', '删除商品', '192.168.1.106', '2017-08-20 11:04:05', '0', '删除商品ID为  536 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4765', '63', '删除商品', '192.168.1.106', '2017-08-20 11:04:07', '0', '删除商品ID为  537 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4766', '63', '增加商品', '192.168.1.106', '2017-08-20 11:04:31', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4767', '63', '更新商品', '192.168.1.106', '2017-08-20 11:05:31', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4768', '63', '更新商品', '192.168.1.106', '2017-08-20 11:10:11', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4769', '63', '更新商品', '192.168.1.106', '2017-08-20 11:10:18', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4770', '63', '更新商品', '192.168.1.106', '2017-08-20 11:10:53', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4771', '63', '更新商品', '192.168.1.106', '2017-08-20 11:11:15', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4772', '63', '更新商品', '192.168.1.106', '2017-08-20 11:11:48', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4773', '63', '更新商品', '192.168.1.106', '2017-08-20 11:12:04', '0', '更新商品ID为  538 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4774', '63', '删除商品', '192.168.1.106', '2017-08-20 11:16:24', '0', '删除商品ID为  519 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4775', '63', '删除商品', '192.168.1.106', '2017-08-20 11:16:32', '0', '删除商品ID为  538 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4776', '63', '增加商品', '192.168.1.106', '2017-08-20 11:16:40', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4777', '63', '删除商品', '192.168.1.106', '2017-08-20 11:17:41', '0', '删除商品ID为  539 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4778', '63', '增加商品', '192.168.1.106', '2017-08-20 11:17:46', '0', '增加商品名称为  asdsd 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4779', '63', '删除商品', '192.168.1.106', '2017-08-20 11:18:27', '0', '删除商品ID为  540 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4780', '63', '增加商品', '192.168.1.106', '2017-08-20 11:18:39', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4781', '63', '批量删除商品', '192.168.1.106', '2017-08-20 11:21:17', '0', '批量删除商品ID为  541 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4782', '63', '增加商品', '192.168.1.106', '2017-08-20 11:21:24', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4783', '63', '登录系统', '192.168.1.106', '2017-08-20 11:27:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4784', '63', '增加商品', '192.168.1.106', '2017-08-20 11:27:41', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4785', '63', '删除商品', '192.168.1.106', '2017-08-20 11:27:56', '0', '删除商品ID为  543 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4786', '63', '删除商品', '192.168.1.106', '2017-08-20 11:27:58', '0', '删除商品ID为  542 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4787', '63', '增加商品', '192.168.1.106', '2017-08-20 11:28:07', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4788', '63', '删除商品', '192.168.1.106', '2017-08-20 11:31:32', '0', '删除商品ID为  544 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4789', '63', '增加商品', '192.168.1.106', '2017-08-20 11:32:06', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4790', '63', '增加商品', '192.168.1.106', '2017-08-20 11:32:36', '0', '增加商品名称为  asdfad 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4791', '63', '增加商品', '192.168.1.106', '2017-08-20 11:34:20', '0', '增加商品名称为  asdf 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4792', '63', '批量删除商品', '192.168.1.106', '2017-08-20 11:34:29', '0', '批量删除商品ID为  545,546 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4793', '63', '批量删除商品', '192.168.1.106', '2017-08-20 11:34:31', '0', '批量删除商品ID为  547 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4794', '63', '增加商品', '192.168.1.106', '2017-08-20 11:34:43', '0', '增加商品名称为  aaaa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4795', '63', '增加商品', '192.168.1.106', '2017-08-20 11:35:40', '0', '增加商品名称为  2134 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4796', '63', '批量删除商品', '192.168.1.106', '2017-08-20 11:35:45', '0', '批量删除商品ID为  548 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4797', '63', '更新商品', '192.168.1.106', '2017-08-20 12:04:41', '0', '更新商品ID为  549 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4798', '63', '更新商品', '192.168.1.106', '2017-08-20 12:04:51', '0', '更新商品ID为  549 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4799', '63', '更新商品', '192.168.1.106', '2017-08-20 12:05:07', '0', '更新商品ID为  549 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4800', '63', '登录系统', '192.168.1.106', '2017-08-20 12:08:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4801', '63', '删除商品', '192.168.1.106', '2017-08-20 12:08:48', '0', '删除商品ID为  549 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4802', '63', '增加商品', '192.168.1.106', '2017-08-20 12:08:54', '0', '增加商品名称为  www 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4803', '63', '删除商品', '192.168.1.106', '2017-08-20 12:09:43', '0', '删除商品ID为  550 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4804', '63', '增加商品', '192.168.1.106', '2017-08-20 12:09:52', '0', '增加商品名称为  eee 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4805', '63', '更新商品', '192.168.1.106', '2017-08-20 12:10:00', '0', '更新商品ID为  551 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4806', '63', '增加商品', '192.168.1.106', '2017-08-20 12:10:08', '0', '增加商品名称为  rrrr 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4807', '63', '更新商品', '192.168.1.106', '2017-08-20 12:10:42', '0', '更新商品ID为  552 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4808', '63', '更新商品', '192.168.1.106', '2017-08-20 12:11:05', '0', '更新商品ID为  552 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4809', '63', '批量删除商品', '192.168.1.106', '2017-08-20 12:11:27', '0', '批量删除商品ID为  551,552 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4810', '63', '增加商品', '192.168.1.106', '2017-08-20 12:11:41', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4811', '63', '增加商品', '192.168.1.106', '2017-08-20 12:11:54', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4812', '63', '更新商品', '192.168.1.106', '2017-08-20 12:12:33', '0', '更新商品ID为  554 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4813', '63', '增加商品', '192.168.1.106', '2017-08-20 12:12:43', '0', '增加商品名称为  asd 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4814', '63', '删除商品', '192.168.1.106', '2017-08-20 12:12:47', '0', '删除商品ID为  555 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4815', '63', '登录系统', '192.168.1.106', '2017-08-20 12:14:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4816', '63', '批量删除商品', '192.168.1.106', '2017-08-20 12:14:37', '0', '批量删除商品ID为  553,554 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4817', '63', '增加商品', '192.168.1.106', '2017-08-20 12:15:17', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4818', '63', '增加商品', '192.168.1.106', '2017-08-20 12:15:28', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4819', '63', '删除商品', '192.168.1.106', '2017-08-20 12:15:30', '0', '删除商品ID为  557 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4820', '63', '更新商品', '192.168.1.106', '2017-08-20 12:15:39', '0', '更新商品ID为  556 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4821', '63', '更新商品', '192.168.1.106', '2017-08-20 12:15:43', '0', '更新商品ID为  556 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4822', '63', '增加商品', '192.168.1.106', '2017-08-20 12:15:55', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4823', '63', '更新商品', '192.168.1.106', '2017-08-20 12:16:09', '0', '更新商品ID为  556 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4824', '63', '更新商品', '192.168.1.106', '2017-08-20 13:05:25', '0', '更新商品ID为  556 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4825', '63', '删除商品', '192.168.1.106', '2017-08-20 13:05:34', '0', '删除商品ID为  558 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4826', '63', '增加商品', '192.168.1.106', '2017-08-20 13:05:39', '0', '增加商品名称为  234 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4827', '63', '删除商品', '192.168.1.106', '2017-08-20 13:05:42', '0', '删除商品ID为  559 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4828', '63', '登录系统', '192.168.1.106', '2017-08-20 13:52:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4829', '63', '登录系统', '192.168.1.106', '2017-08-20 14:01:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4830', '63', '登录系统', '192.168.100.163', '2017-08-20 19:05:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4831', '63', '登录系统', '192.168.100.163', '2017-08-20 19:19:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4832', '63', '登录系统', '192.168.100.163', '2017-08-20 19:30:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4833', '63', '批量修改商品状态', '192.168.100.163', '2017-08-20 19:32:58', '0', '批量修改状态，商品ID为  556 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4834', '63', '批量修改商品状态', '192.168.100.163', '2017-08-20 19:33:03', '0', '批量修改状态，商品ID为  556 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('4835', '63', '登录系统', '192.168.100.163', '2017-08-20 19:35:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4836', '63', '登录系统', '192.168.100.163', '2017-08-20 19:41:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4837', '63', '增加商品', '192.168.100.163', '2017-08-20 19:41:59', '0', '增加商品名称为  sd 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4838', '63', '增加商品', '192.168.100.163', '2017-08-20 19:42:05', '0', '增加商品名称为  sdf 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4839', '63', '更新商品', '192.168.100.163', '2017-08-20 19:44:28', '0', '更新商品ID为  556 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4840', '63', '登录系统', '192.168.100.163', '2017-08-20 19:45:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4841', '63', '登录系统', '192.168.100.163', '2017-08-20 19:48:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4842', '63', '更新商品', '192.168.100.163', '2017-08-20 19:54:52', '0', '更新商品ID为  557 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('4843', '63', '删除商品', '192.168.100.163', '2017-08-20 19:55:28', '0', '删除商品ID为  558 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4844', '63', '删除商品', '192.168.100.163', '2017-08-20 19:55:31', '0', '删除商品ID为  557 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4845', '63', '删除商品', '192.168.100.163', '2017-08-20 19:55:34', '0', '删除商品ID为  556 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4846', '63', '增加商品', '192.168.100.163', '2017-08-20 19:55:53', '0', '增加商品名称为  test 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4847', '63', '登录系统', '192.168.100.163', '2017-08-20 20:05:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4848', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:46:39', '0', '增加供应商名称为  666 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4849', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:46:43', '0', '增加供应商名称为  777 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4850', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:46:47', '0', '增加供应商名称为  888 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4851', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:46:50', '0', '增加供应商名称为  999 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4852', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:46:54', '0', '增加供应商名称为  222 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4853', '63', '增加供应商', '192.168.100.163', '2017-08-20 20:47:01', '0', '增加供应商名称为  4444 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('4854', '63', '登录系统', '192.168.100.163', '2017-08-20 21:52:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4855', '63', '登录系统', '192.168.100.163', '2017-08-20 21:55:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4856', '63', '登录系统', '192.168.100.163', '2017-08-20 21:57:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4857', '63', '删除商品', '192.168.100.163', '2017-08-20 21:58:25', '0', '删除商品ID为  560 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4858', '63', '增加商品', '192.168.100.163', '2017-08-20 22:01:56', '0', '增加商品名称为  123 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4859', '63', '删除商品', '192.168.100.163', '2017-08-20 22:02:52', '0', '删除商品ID为  561 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4860', '63', '增加商品', '192.168.100.163', '2017-08-20 22:03:01', '0', '增加商品名称为  aa 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4861', '63', '批量删除商品', '192.168.100.163', '2017-08-20 22:03:06', '0', '批量删除商品ID为  562 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4862', '63', '增加商品', '192.168.100.163', '2017-08-20 22:03:11', '0', '增加商品名称为  sdfsdf 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('4863', '63', '删除商品', '192.168.100.163', '2017-08-20 22:03:15', '0', '删除商品ID为  563 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4864', '63', '批量删除商品', '192.168.100.163', '2017-08-20 22:04:00', '0', '批量删除商品ID为  565,566 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4865', '63', '删除商品', '192.168.100.163', '2017-08-20 22:04:03', '0', '删除商品ID为  564 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4866', '63', '登录系统', '192.168.100.163', '2017-08-20 22:15:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4867', '63', '批量删除商品', '192.168.100.163', '2017-08-20 22:21:28', '0', '批量删除商品ID为  568,569,570 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4868', '63', '批量删除商品', '192.168.100.163', '2017-08-20 22:21:32', '0', '批量删除商品ID为  567 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4869', '63', '登录系统', '192.168.100.163', '2017-08-21 20:25:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4870', '63', '退出系统', '192.168.100.163', '2017-08-21 21:13:49', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('4871', '63', '登录系统', '192.168.100.163', '2017-08-21 21:14:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4872', '63', '退出系统', '192.168.100.163', '2017-08-21 21:14:30', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('4873', '63', '登录系统', '192.168.100.163', '2017-08-21 21:15:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4874', '63', '退出系统', '192.168.100.163', '2017-08-21 21:15:23', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('4875', '63', '登录系统', '192.168.100.163', '2017-08-21 21:16:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4876', '63', '更新功能', '192.168.100.163', '2017-08-21 22:42:03', '0', '更新功能ID为  219 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4877', '63', '登录系统', '192.168.100.163', '2017-08-21 23:21:33', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4878', '63', '重置用户密码', '', '2017-08-21 23:34:17', '0', '重置用户ID为  63密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4879', '63', '重置用户密码', '', '2017-08-21 23:34:36', '0', '重置用户ID为  63密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4880', '63', '更新用户', '192.168.100.163', '2017-08-21 23:34:50', '0', '更新用户ID为  63 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('4881', '63', '重置用户密码', '', '2017-08-21 23:41:12', '0', '重置用户ID为  63密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4882', '63', '退出系统', '192.168.100.163', '2017-08-21 23:41:39', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('4883', '65', '登录系统', '192.168.100.163', '2017-08-21 23:41:43', '0', '管理用户：ls 登录系统', 'ls 登录系统');
INSERT INTO `jsh_log` VALUES ('4884', '65', '更新用户', '', '2017-08-21 23:41:55', '0', '更新用户ID为  65密码信息 成功！', '更新用户成功');
INSERT INTO `jsh_log` VALUES ('4885', '63', '登录系统', '192.168.100.163', '2017-08-21 23:42:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4886', '63', '重置用户密码', '', '2017-08-21 23:44:27', '0', '重置用户ID为  65密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4887', '63', '重置用户密码', '', '2017-08-21 23:55:47', '0', '重置用户ID为  65密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4888', '63', '重置用户密码', '', '2017-08-22 00:00:06', '0', '重置用户ID为  65密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4889', '63', '重置用户密码', '', '2017-08-22 00:00:55', '0', '重置用户ID为  65密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4890', '63', '重置用户密码', '', '2017-08-22 00:00:58', '0', '重置用户ID为  65密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4891', '63', '重置用户密码', '', '2017-08-22 00:01:08', '0', '重置用户ID为  64密码信息 成功！', '重置用户密码成功');
INSERT INTO `jsh_log` VALUES ('4892', '63', '批量删除商品', '192.168.100.163', '2017-08-22 00:15:33', '0', '批量删除商品ID为  560 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4893', '63', '批量删除商品', '192.168.100.163', '2017-08-22 00:18:15', '0', '批量删除商品ID为  562 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('4894', '63', '删除商品', '192.168.100.163', '2017-08-22 00:18:18', '0', '删除商品ID为  561 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4895', '63', '删除商品', '192.168.100.163', '2017-08-22 00:18:52', '0', '删除商品ID为  563 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4896', '63', '删除商品', '192.168.100.163', '2017-08-22 00:18:54', '0', '删除商品ID为  564 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('4897', '63', '登录系统', '192.168.100.163', '2017-08-22 20:37:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4898', '63', '删除单据', '192.168.100.163', '2017-08-22 21:08:55', '0', '删除单据ID为  28 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('4899', '63', '登录系统', '192.168.100.163', '2017-08-22 22:34:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4900', '63', '增加功能', '192.168.100.163', '2017-08-22 22:44:51', '0', '增加功能名称为  审核 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('4901', '63', '更新功能', '192.168.100.163', '2017-08-22 22:46:03', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4902', '63', '更新功能', '192.168.100.163', '2017-08-22 22:46:07', '0', '更新功能ID为  221 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4903', '63', '更新功能', '192.168.100.163', '2017-08-22 22:47:44', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4904', '63', '删除功能', '192.168.100.163', '2017-08-22 22:47:49', '0', '删除功能ID为  221 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('4905', '63', '增加角色', '192.168.100.163', '2017-08-22 22:57:13', '0', '增加角色名称为  aaaa 成功！', '增加角色成功');
INSERT INTO `jsh_log` VALUES ('4906', '63', '更新UserBusiness', '192.168.100.163', '2017-08-22 22:57:54', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4907', '63', '更新UserBusiness', '192.168.100.163', '2017-08-22 22:59:10', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4908', '63', '更新UserBusiness', '192.168.100.163', '2017-08-22 23:20:35', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4909', '63', '登录系统', '192.168.100.163', '2017-08-23 20:37:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4910', '63', '增加应用', '192.168.100.163', '2017-08-23 20:44:37', '0', '增加应用名称为  采购入库 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('4911', '63', '更新应用', '192.168.100.163', '2017-08-23 20:46:56', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('4912', '63', '更新UserBusiness', '192.168.100.163', '2017-08-23 20:48:21', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4913', '63', '更新UserBusiness', '192.168.100.163', '2017-08-23 20:48:52', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4914', '63', '删除应用', '192.168.100.163', '2017-08-23 20:50:07', '0', '删除应用ID为  23 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('4915', '63', '更新UserBusiness', '192.168.100.163', '2017-08-23 20:54:59', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4916', '63', '更新UserBusiness', '192.168.100.163', '2017-08-23 20:56:23', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4917', '63', '更新功能', '192.168.100.163', '2017-08-23 22:21:18', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4918', '63', '更新功能', '192.168.100.163', '2017-08-23 22:21:44', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4919', '63', '登录系统', '192.168.100.163', '2017-08-23 22:23:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4920', '63', '更新功能', '192.168.100.163', '2017-08-23 22:24:20', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4921', '63', '更新功能', '192.168.100.163', '2017-08-23 22:24:58', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4922', '63', '更新功能', '192.168.100.163', '2017-08-23 22:25:25', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4923', '63', '更新功能', '192.168.100.163', '2017-08-23 22:27:44', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4924', '63', '更新功能', '192.168.100.163', '2017-08-23 22:28:54', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4925', '63', '更新功能', '192.168.100.163', '2017-08-23 22:46:03', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4926', '63', '更新功能', '192.168.100.163', '2017-08-23 22:55:36', '0', '更新功能ID为  2 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4927', '63', '更新功能', '192.168.100.163', '2017-08-23 22:57:13', '0', '更新功能ID为  1 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4928', '63', '更新功能', '192.168.100.163', '2017-08-23 22:59:08', '0', '更新功能ID为  2 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4929', '63', '更新功能', '192.168.100.163', '2017-08-23 22:59:16', '0', '更新功能ID为  3 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4930', '63', '登录系统', '192.168.100.163', '2017-08-24 00:08:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4931', '63', '登录系统', '192.168.100.163', '2017-08-24 00:21:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4932', '63', '登录系统', '192.168.100.164', '2017-08-24 21:14:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4933', '63', '登录系统', '192.168.100.164', '2017-08-25 20:31:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4934', '63', '登录系统', '192.168.100.164', '2017-08-25 20:31:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4935', '63', '登录系统', '192.168.100.164', '2017-08-25 21:10:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4936', '63', '更新角色按钮权限', '', '2017-08-25 21:45:05', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4937', '63', '更新角色按钮权限', '', '2017-08-25 21:53:02', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4938', '63', '更新角色按钮权限', '', '2017-08-25 22:06:19', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4939', '63', '更新角色按钮权限', '', '2017-08-25 22:06:31', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4940', '63', '更新角色按钮权限', '', '2017-08-25 22:07:06', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4941', '63', '更新角色按钮权限', '', '2017-08-25 22:09:23', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4942', '63', '更新角色按钮权限', '', '2017-08-25 23:25:00', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4943', '63', '更新角色按钮权限', '', '2017-08-25 23:27:17', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4944', '63', '更新角色按钮权限', '', '2017-08-25 23:28:16', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4945', '63', '更新角色按钮权限', '', '2017-08-25 23:28:23', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4946', '63', '更新角色按钮权限', '', '2017-08-25 23:29:14', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4947', '63', '更新角色按钮权限', '', '2017-08-25 23:29:22', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4948', '63', '更新角色按钮权限', '', '2017-08-25 23:30:24', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4949', '63', '更新角色按钮权限', '', '2017-08-25 23:31:31', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4950', '63', '更新角色按钮权限', '', '2017-08-25 23:31:40', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4951', '63', '更新角色按钮权限', '', '2017-08-25 23:31:50', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4952', '63', '更新角色按钮权限', '', '2017-08-25 23:32:01', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4953', '63', '更新角色按钮权限', '', '2017-08-25 23:34:09', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4954', '63', '更新角色按钮权限', '', '2017-08-25 23:36:56', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4955', '63', '更新角色按钮权限', '', '2017-08-25 23:37:19', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4956', '63', '更新角色按钮权限', '', '2017-08-25 23:44:54', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4957', '63', '更新角色按钮权限', '', '2017-08-25 23:45:07', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4958', '63', '更新角色按钮权限', '', '2017-08-25 23:45:15', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4959', '63', '更新角色按钮权限', '', '2017-08-25 23:45:27', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4960', '63', '更新角色按钮权限', '', '2017-08-25 23:45:37', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4961', '63', '更新角色按钮权限', '', '2017-08-25 23:45:44', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4962', '63', '更新角色按钮权限', '', '2017-08-25 23:45:53', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4963', '63', '更新角色按钮权限', '', '2017-08-25 23:45:58', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4964', '63', '更新角色按钮权限', '', '2017-08-25 23:46:03', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4965', '63', '更新角色按钮权限', '', '2017-08-25 23:46:08', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4966', '63', '更新角色按钮权限', '', '2017-08-25 23:46:21', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4967', '63', '更新功能', '192.168.100.164', '2017-08-25 23:46:57', '0', '更新功能ID为  13 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4968', '63', '更新功能', '192.168.100.164', '2017-08-25 23:47:04', '0', '更新功能ID为  12 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4969', '63', '更新功能', '192.168.100.164', '2017-08-25 23:47:53', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4970', '63', '更新功能', '192.168.100.164', '2017-08-25 23:48:00', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4971', '63', '更新功能', '192.168.100.164', '2017-08-25 23:48:08', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4972', '63', '更新功能', '192.168.100.164', '2017-08-25 23:48:26', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4973', '63', '更新功能', '192.168.100.164', '2017-08-25 23:48:35', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4974', '63', '更新功能', '192.168.100.164', '2017-08-25 23:48:54', '0', '更新功能ID为  25 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4975', '63', '更新功能', '192.168.100.164', '2017-08-25 23:49:01', '0', '更新功能ID为  217 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4976', '63', '更新功能', '192.168.100.164', '2017-08-25 23:49:07', '0', '更新功能ID为  218 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4977', '63', '更新角色按钮权限', '', '2017-08-25 23:49:38', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4978', '63', '更新角色按钮权限', '', '2017-08-25 23:49:54', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4979', '63', '更新角色按钮权限', '', '2017-08-25 23:50:03', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4980', '63', '更新功能', '192.168.100.164', '2017-08-25 23:54:54', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4981', '63', '更新功能', '192.168.100.164', '2017-08-25 23:55:10', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4982', '63', '更新功能', '192.168.100.164', '2017-08-25 23:55:40', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4983', '63', '更新功能', '192.168.100.164', '2017-08-25 23:56:15', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4984', '63', '更新功能', '192.168.100.164', '2017-08-25 23:56:23', '0', '更新功能ID为  202 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4985', '63', '更新功能', '192.168.100.164', '2017-08-25 23:56:29', '0', '更新功能ID为  40 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('4986', '63', '更新角色按钮权限', '', '2017-08-25 23:57:05', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4987', '63', '更新角色按钮权限', '', '2017-08-25 23:57:11', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4988', '63', '更新UserBusiness', '192.168.100.164', '2017-08-25 23:57:26', '0', '更新UserBusiness的ID为  7 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4989', '63', '更新角色按钮权限', '', '2017-08-25 23:59:28', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4990', '63', '更新角色按钮权限', '', '2017-08-26 00:00:20', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4991', '63', '更新角色按钮权限', '', '2017-08-26 00:02:23', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4992', '63', '更新角色按钮权限', '', '2017-08-26 00:02:27', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4993', '63', '更新角色按钮权限', '', '2017-08-26 00:04:57', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4994', '63', '登录系统', '192.168.100.164', '2017-08-26 00:28:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4995', '63', '登录系统', '192.168.100.164', '2017-08-26 00:52:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4996', '63', '登录系统', '192.168.100.163', '2017-08-27 19:32:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('4997', '63', '更新角色按钮权限', '', '2017-08-27 19:35:29', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('4998', '63', '更新UserBusiness', '192.168.100.163', '2017-08-27 19:57:47', '0', '更新UserBusiness的ID为  16 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('4999', '63', '更新角色按钮权限', '', '2017-08-27 20:32:34', '0', '角色按钮权限的ID为  7 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5000', '63', '更新UserBusiness', '192.168.100.163', '2017-08-27 20:32:50', '0', '更新UserBusiness的ID为  16 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5001', '63', '登录系统', '192.168.100.163', '2017-08-27 20:42:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5002', '63', '更新UserBusiness', '192.168.100.163', '2017-08-27 20:52:24', '0', '更新UserBusiness的ID为  16 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5003', '63', '更新角色按钮权限', '', '2017-08-27 21:06:01', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5004', '63', '更新角色按钮权限', '', '2017-08-27 21:08:34', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5005', '63', '更新角色按钮权限', '', '2017-08-27 21:09:00', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5006', '63', '更新角色按钮权限', '', '2017-08-27 21:09:39', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5007', '63', '登录系统', '192.168.100.163', '2017-08-27 22:12:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5008', '63', '更新功能', '192.168.100.163', '2017-08-27 22:56:24', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5009', '63', '更新功能', '192.168.100.163', '2017-08-27 22:56:38', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5010', '63', '批量删除供应商', '192.168.100.163', '2017-08-27 23:05:28', '0', '批量删除供应商ID为  52,51,50,49,48,47,41,37 成功！', '批量删除供应商成功');
INSERT INTO `jsh_log` VALUES ('5011', '63', '增加单据', '192.168.100.163', '2017-08-27 23:10:44', '0', '增加单据编号为  GHDD201708270001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5012', '63', '保存仓管通明细', '192.168.100.163', '2017-08-27 23:10:44', '0', '保存仓管通明细对应主表编号为  28 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5013', '63', '批量修改单据状态', '192.168.100.163', '2017-08-27 23:16:13', '0', '批量修改状态，单据ID为  28 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5014', '63', '批量修改单据状态', '192.168.100.163', '2017-08-27 23:16:16', '0', '批量修改状态，单据ID为  28 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5015', '63', '登录系统', '192.168.100.163', '2017-08-28 21:12:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5016', '63', '增加单据', '192.168.100.163', '2017-08-28 23:06:40', '0', '增加单据编号为  XSCK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5017', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:06:40', '0', '保存仓管通明细对应主表编号为  29 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5018', '63', '增加单据', '192.168.100.163', '2017-08-28 23:13:08', '0', '增加单据编号为  XSTH201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5019', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:13:09', '0', '保存仓管通明细对应主表编号为  30 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5020', '63', '增加单据', '192.168.100.163', '2017-08-28 23:15:45', '0', '增加单据编号为  CGTH201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5021', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:15:45', '0', '保存仓管通明细对应主表编号为  31 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5022', '63', '更新单据', '192.168.100.163', '2017-08-28 23:16:12', '0', '更新单据ID为  30 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5023', '63', '增加单据', '192.168.100.163', '2017-08-28 23:17:55', '0', '增加单据编号为  QTRK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5024', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:17:55', '0', '保存仓管通明细对应主表编号为  32 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5025', '63', '增加单据', '192.168.100.163', '2017-08-28 23:21:14', '0', '增加单据编号为  QTCK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5026', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:21:15', '0', '保存仓管通明细对应主表编号为  33 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5027', '63', '增加单据', '192.168.100.163', '2017-08-28 23:37:02', '0', '增加单据编号为  DBCK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5028', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:37:02', '0', '保存仓管通明细对应主表编号为  34 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5029', '63', '删除单据', '192.168.100.163', '2017-08-28 23:38:46', '0', '删除单据ID为  34 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5030', '63', '增加单据', '192.168.100.163', '2017-08-28 23:38:58', '0', '增加单据编号为  DBCK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5031', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:38:58', '0', '保存仓管通明细对应主表编号为  35 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5032', '63', '删除单据', '192.168.100.163', '2017-08-28 23:39:18', '0', '删除单据ID为  35 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5033', '63', '增加单据', '192.168.100.163', '2017-08-28 23:39:28', '0', '增加单据编号为  DBCK201708280001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5034', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:39:28', '0', '保存仓管通明细对应主表编号为  36 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5035', '63', '增加单据', '192.168.100.163', '2017-08-28 23:56:34', '0', '增加单据编号为  DBCK201708280002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5036', '63', '保存仓管通明细', '192.168.100.163', '2017-08-28 23:56:35', '0', '保存仓管通明细对应主表编号为  37 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5037', '63', '增加单据', '192.168.100.163', '2017-08-29 00:20:11', '0', '增加单据编号为  DBCK201708290001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5038', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 00:20:11', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5039', '63', '更新单据', '192.168.100.163', '2017-08-29 00:20:49', '0', '更新单据ID为  38 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5040', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 00:20:49', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5041', '63', '更新角色按钮权限', '', '2017-08-29 00:27:51', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5042', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 00:28:19', '0', '批量修改状态，单据ID为  33 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5043', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 00:28:21', '0', '批量修改状态，单据ID为  33 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5044', '63', '登录系统', '192.168.100.163', '2017-08-29 20:33:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5045', '63', '更新角色按钮权限', '', '2017-08-29 20:38:41', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5046', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 20:38:57', '0', '批量修改状态，单据ID为  36 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5047', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 20:39:00', '0', '批量修改状态，单据ID为  36 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5048', '63', '更新单据', '192.168.100.163', '2017-08-29 20:41:12', '0', '更新单据ID为  31 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5049', '63', '批量删除单据', '192.168.100.163', '2017-08-29 20:41:41', '0', '批量删除单据ID为  36 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5050', '63', '更新单据', '192.168.100.163', '2017-08-29 20:41:56', '0', '更新单据ID为  38 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5051', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 20:41:57', '0', '保存仓管通明细对应主表编号为  38 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5052', '63', '更新单据', '192.168.100.163', '2017-08-29 20:51:57', '0', '更新单据ID为  30 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5053', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 20:51:57', '0', '保存仓管通明细对应主表编号为  30 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5054', '63', '更新功能', '192.168.100.163', '2017-08-29 20:54:04', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5055', '63', '更新功能', '192.168.100.163', '2017-08-29 20:54:23', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5056', '63', '更新功能', '192.168.100.163', '2017-08-29 20:54:51', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5057', '63', '退出系统', '192.168.100.163', '2017-08-29 20:57:20', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5058', '63', '登录系统', '192.168.100.163', '2017-08-29 20:57:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5059', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 20:57:45', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5060', '63', '更新功能', '192.168.100.163', '2017-08-29 20:58:15', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5061', '63', '更新功能', '192.168.100.163', '2017-08-29 20:58:25', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5062', '63', '更新角色按钮权限', '', '2017-08-29 20:58:33', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5063', '63', '更新功能', '192.168.100.163', '2017-08-29 20:58:55', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5064', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 20:59:03', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5065', '63', '更新功能', '192.168.100.163', '2017-08-29 21:00:57', '0', '更新功能ID为  216 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5066', '63', '更新功能', '192.168.100.163', '2017-08-29 21:01:03', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5067', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 21:01:18', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5068', '63', '增加应用', '192.168.100.163', '2017-08-29 21:11:53', '0', '增加应用名称为  零售管理 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('5069', '63', '更新应用', '192.168.100.163', '2017-08-29 21:12:08', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5070', '63', '增加应用', '192.168.100.163', '2017-08-29 21:13:16', '0', '增加应用名称为  入库管理 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('5071', '63', '增加应用', '192.168.100.163', '2017-08-29 21:14:22', '0', '增加应用名称为  出库管理 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('5072', '63', '增加应用', '192.168.100.163', '2017-08-29 21:15:19', '0', '增加应用名称为  财务管理 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('5073', '63', '增加功能', '192.168.100.163', '2017-08-29 21:16:55', '0', '增加功能名称为  零售管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5074', '63', '增加功能', '192.168.100.163', '2017-08-29 21:17:23', '0', '增加功能名称为  入库管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5075', '63', '更新功能', '192.168.100.163', '2017-08-29 21:17:38', '0', '更新功能ID为  3 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5076', '63', '增加功能', '192.168.100.163', '2017-08-29 21:18:33', '0', '增加功能名称为  出库管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5077', '63', '增加功能', '192.168.100.163', '2017-08-29 21:18:58', '0', '增加功能名称为  财务管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5078', '63', '更新功能', '192.168.100.163', '2017-08-29 21:19:13', '0', '更新功能ID为  196 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5079', '63', '删除功能', '192.168.100.163', '2017-08-29 21:19:38', '0', '删除功能ID为  3 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('5080', '63', '更新功能', '192.168.100.163', '2017-08-29 21:21:42', '0', '更新功能ID为  32 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5081', '63', '更新功能', '192.168.100.163', '2017-08-29 21:21:59', '0', '更新功能ID为  33 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5082', '63', '更新功能', '192.168.100.163', '2017-08-29 21:22:21', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5083', '63', '更新功能', '192.168.100.163', '2017-08-29 21:22:33', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5084', '63', '更新功能', '192.168.100.163', '2017-08-29 21:22:47', '0', '更新功能ID为  38 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5085', '63', '更新功能', '192.168.100.163', '2017-08-29 21:23:13', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5086', '63', '更新功能', '192.168.100.163', '2017-08-29 21:23:28', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5087', '63', '更新功能', '192.168.100.163', '2017-08-29 21:23:43', '0', '更新功能ID为  202 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5088', '63', '更新功能', '192.168.100.163', '2017-08-29 21:23:59', '0', '更新功能ID为  40 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5089', '63', '增加功能', '192.168.100.163', '2017-08-29 21:25:04', '0', '增加功能名称为  零售管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5090', '63', '更新功能', '192.168.100.163', '2017-08-29 21:25:21', '0', '更新功能ID为  210 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5091', '63', '更新功能', '192.168.100.163', '2017-08-29 21:25:59', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5092', '63', '更新功能', '192.168.100.163', '2017-08-29 21:26:14', '0', '更新功能ID为  211 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5093', '63', '更新功能', '192.168.100.163', '2017-08-29 21:27:55', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5094', '63', '更新功能', '192.168.100.163', '2017-08-29 21:28:20', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5095', '63', '更新功能', '192.168.100.163', '2017-08-29 21:29:06', '0', '更新功能ID为  44 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5096', '63', '更新功能', '192.168.100.163', '2017-08-29 21:29:26', '0', '更新功能ID为  197 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5097', '63', '更新功能', '192.168.100.163', '2017-08-29 21:29:38', '0', '更新功能ID为  203 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5098', '63', '更新功能', '192.168.100.163', '2017-08-29 21:29:53', '0', '更新功能ID为  204 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5099', '63', '更新功能', '192.168.100.163', '2017-08-29 21:30:10', '0', '更新功能ID为  205 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5100', '63', '更新功能', '192.168.100.163', '2017-08-29 21:30:30', '0', '更新功能ID为  206 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5101', '63', '更新功能', '192.168.100.163', '2017-08-29 21:30:42', '0', '更新功能ID为  212 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5102', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 21:32:15', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5103', '63', '更新应用', '192.168.100.163', '2017-08-29 21:33:32', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5104', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 21:34:23', '0', '更新UserBusiness的ID为  1 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5105', '63', '更新应用', '192.168.100.163', '2017-08-29 21:34:59', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5106', '63', '更新应用', '192.168.100.163', '2017-08-29 21:35:07', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5107', '63', '更新应用', '192.168.100.163', '2017-08-29 21:35:13', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5108', '63', '更新应用', '192.168.100.163', '2017-08-29 21:36:15', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5109', '63', '更新应用', '192.168.100.163', '2017-08-29 21:36:20', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5110', '63', '更新应用', '192.168.100.163', '2017-08-29 21:36:24', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5111', '63', '增加单据', '192.168.100.163', '2017-08-29 21:37:30', '0', '增加单据编号为  LSCK201708290001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5112', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 21:37:30', '0', '保存仓管通明细对应主表编号为  39 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5113', '63', '更新供应商预付款', '', '2017-08-29 21:37:36', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5114', '63', '批量删除单据', '192.168.100.163', '2017-08-29 21:37:36', '0', '批量删除单据ID为  39 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5115', '63', '更新功能', '192.168.100.163', '2017-08-29 21:40:46', '0', '更新功能ID为  213 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5116', '63', '更新UserBusiness', '192.168.100.163', '2017-08-29 21:41:02', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5117', '63', '更新仓库', '192.168.100.163', '2017-08-29 21:41:22', '0', '更新仓库ID为  4 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('5118', '63', '更新仓库', '192.168.100.163', '2017-08-29 21:41:26', '0', '更新仓库ID为  4 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('5119', '63', '更新应用', '192.168.100.163', '2017-08-29 21:51:30', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5120', '63', '更新应用', '192.168.100.163', '2017-08-29 21:51:34', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5121', '63', '更新应用', '192.168.100.163', '2017-08-29 21:52:57', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5122', '63', '增加单据', '192.168.100.163', '2017-08-29 23:22:53', '0', '增加单据编号为  LSCK201708290001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5123', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:22:54', '0', '保存仓管通明细对应主表编号为  40 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5124', '63', '增加单据', '192.168.100.163', '2017-08-29 23:29:39', '0', '增加单据编号为  LSCK201708290002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5125', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:29:40', '0', '保存仓管通明细对应主表编号为  41 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5126', '63', '增加单据', '192.168.100.163', '2017-08-29 23:35:12', '0', '增加单据编号为  LSCK201708290003 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5127', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:35:13', '0', '保存仓管通明细对应主表编号为  42 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5128', '63', '增加单据', '192.168.100.163', '2017-08-29 23:39:44', '0', '增加单据编号为  LSCK201708290004 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5129', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:39:44', '0', '保存仓管通明细对应主表编号为  43 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5130', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 23:39:48', '0', '批量修改状态，单据ID为  43 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5131', '63', '批量修改单据状态', '192.168.100.163', '2017-08-29 23:40:02', '0', '批量修改状态，单据ID为  43 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5132', '63', '增加单据', '192.168.100.163', '2017-08-29 23:48:43', '0', '增加单据编号为  LSTH201708290001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5133', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:48:43', '0', '保存仓管通明细对应主表编号为  44 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5134', '63', '增加单据', '192.168.100.163', '2017-08-29 23:51:55', '0', '增加单据编号为  LSTH201708290002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5135', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:51:56', '0', '保存仓管通明细对应主表编号为  45 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5136', '63', '更新单据', '192.168.100.163', '2017-08-29 23:52:09', '0', '更新单据ID为  45 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5137', '63', '保存仓管通明细', '192.168.100.163', '2017-08-29 23:52:09', '0', '保存仓管通明细对应主表编号为  45 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5138', '63', '登录系统', '192.168.100.163', '2017-08-30 00:39:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5139', '63', '登录系统', '192.168.100.163', '2017-08-30 20:37:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5140', '63', '登录系统', '192.168.100.163', '2017-08-30 20:59:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5141', '63', '登录系统', '192.168.100.163', '2017-08-30 23:01:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5142', '63', '更新应用', '192.168.100.163', '2017-08-30 23:08:24', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5143', '63', '更新应用', '192.168.100.163', '2017-08-30 23:08:34', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5144', '63', '更新应用', '192.168.100.163', '2017-08-30 23:08:40', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5145', '63', '更新应用', '192.168.100.163', '2017-08-30 23:09:10', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5146', '63', '更新应用', '192.168.100.163', '2017-08-30 23:09:15', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5147', '63', '更新应用', '192.168.100.163', '2017-08-30 23:09:40', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5148', '63', '更新应用', '192.168.100.163', '2017-08-30 23:10:52', '0', '更新应用ID为  23 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5149', '63', '更新应用', '192.168.100.163', '2017-08-30 23:10:56', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5150', '63', '更新应用', '192.168.100.163', '2017-08-30 23:11:44', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5151', '63', '增加单据', '192.168.100.163', '2017-08-30 23:19:46', '0', '增加单据编号为  LSCK201708300001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5152', '63', '保存仓管通明细', '192.168.100.163', '2017-08-30 23:19:46', '0', '保存仓管通明细对应主表编号为  46 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5153', '63', '增加单据', '192.168.100.163', '2017-08-30 23:38:11', '0', '增加单据编号为  LSCK201708300002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5154', '63', '保存仓管通明细', '192.168.100.163', '2017-08-30 23:38:12', '0', '保存仓管通明细对应主表编号为  47 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5155', '63', '更新供应商预付款', '', '2017-08-30 23:39:45', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5156', '63', '删除单据', '192.168.100.163', '2017-08-30 23:39:45', '0', '删除单据ID为  47 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5157', '63', '更新供应商预付款', '', '2017-08-30 23:59:30', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5158', '63', '批量删除单据', '192.168.100.163', '2017-08-30 23:59:30', '0', '批量删除单据ID为  46 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5159', '63', '增加单据', '192.168.100.163', '2017-08-31 00:30:31', '0', '增加单据编号为  LSCK201708310001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5160', '63', '保存仓管通明细', '192.168.100.163', '2017-08-31 00:30:32', '0', '保存仓管通明细对应主表编号为  48 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5161', '63', '更新单据', '192.168.100.163', '2017-08-31 00:55:26', '0', '更新单据ID为  48 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5162', '63', '增加单据', '192.168.100.163', '2017-08-31 00:57:40', '0', '增加单据编号为  LSCK201708310002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5163', '63', '保存仓管通明细', '192.168.100.163', '2017-08-31 00:57:41', '0', '保存仓管通明细对应主表编号为  49 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5164', '63', '删除单据', '192.168.100.163', '2017-08-31 00:57:47', '0', '删除单据ID为  40 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5165', '63', '更新供应商预付款', '', '2017-08-31 00:57:47', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5166', '63', '登录系统', '192.168.100.163', '2017-08-31 20:27:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5167', '63', '增加功能', '192.168.100.163', '2017-08-31 21:25:40', '0', '增加功能名称为  入库明细 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5168', '63', '增加功能', '192.168.100.163', '2017-08-31 21:28:19', '0', '增加功能名称为  出库明细 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5169', '63', '增加功能', '192.168.100.163', '2017-08-31 21:29:14', '0', '增加功能名称为  入库汇总 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5170', '63', '增加功能', '192.168.100.163', '2017-08-31 21:29:57', '0', '增加功能名称为  出库汇总 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5171', '63', '更新UserBusiness', '192.168.100.163', '2017-08-31 21:30:25', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5172', '63', '登录系统', '192.168.100.163', '2017-08-31 21:51:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5173', '63', '登录系统', '192.168.100.163', '2017-08-31 22:07:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5174', '63', '登录系统', '192.168.100.163', '2017-09-01 23:06:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5175', '63', '登录系统', '192.168.1.106', '2017-09-03 12:46:05', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5176', '63', '增加单据', '192.168.1.106', '2017-09-03 12:51:50', '0', '增加单据编号为  LSCK201709030001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5177', '63', '保存仓管通明细', '192.168.1.106', '2017-09-03 12:51:50', '0', '保存仓管通明细对应主表编号为  50 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5178', '63', '登录系统', '192.168.100.163', '2017-09-03 19:10:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5179', '63', '登录系统', '192.168.100.163', '2017-09-03 23:06:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5180', '63', '登录系统', '192.168.100.163', '2017-09-04 20:12:43', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5181', '63', '登录系统', '192.168.100.163', '2017-09-04 21:24:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5182', '63', '增加单据', '192.168.100.163', '2017-09-04 21:29:44', '0', '增加单据编号为  LSCK201709040001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5183', '63', '保存仓管通明细', '192.168.100.163', '2017-09-04 21:29:45', '0', '保存仓管通明细对应主表编号为  51 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5184', '63', '更新供应商预付款', '', '2017-09-04 21:29:50', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5185', '63', '批量删除单据', '192.168.100.163', '2017-09-04 21:29:50', '0', '批量删除单据ID为  51 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5186', '63', '增加单据', '192.168.100.163', '2017-09-04 21:32:49', '0', '增加单据编号为  LSCK201709040001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5187', '63', '保存仓管通明细', '192.168.100.163', '2017-09-04 21:32:50', '0', '保存仓管通明细对应主表编号为  52 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5188', '63', '增加单据', '192.168.100.163', '2017-09-04 21:34:02', '0', '增加单据编号为  LSCK201709040002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5189', '63', '保存仓管通明细', '192.168.100.163', '2017-09-04 21:34:02', '0', '保存仓管通明细对应主表编号为  53 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5190', '63', '批量修改单据状态', '192.168.100.163', '2017-09-04 21:51:45', '0', '批量修改状态，单据ID为  50 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5191', '63', '增加供应商', '192.168.100.163', '2017-09-04 22:12:38', '0', '增加供应商名称为  aaaa 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('5192', '63', '删除供应商', '192.168.100.163', '2017-09-04 22:12:54', '0', '删除供应商ID为  47,名称为  aaaa成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('5193', '63', '增加单据', '192.168.100.163', '2017-09-04 22:20:12', '0', '增加单据编号为  CGRK201709040001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5194', '63', '保存仓管通明细', '192.168.100.163', '2017-09-04 22:20:12', '0', '保存仓管通明细对应主表编号为  54 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5195', '63', '更新单据', '192.168.100.163', '2017-09-04 22:26:36', '0', '更新单据ID为  54 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5196', '63', '增加单据', '192.168.100.163', '2017-09-05 00:10:03', '0', '增加单据编号为  LPCZ201709050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5197', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 00:10:04', '0', '保存仓管通明细对应主表编号为  55 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5198', '63', '登录系统', '192.168.100.163', '2017-09-05 20:59:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5199', '63', '登录系统', '192.168.100.163', '2017-09-05 21:00:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5200', '63', '增加单据', '192.168.100.163', '2017-09-05 22:30:17', '0', '增加单据编号为  LPXS201709050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5201', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 22:30:17', '0', '保存仓管通明细对应主表编号为  56 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5202', '63', '增加单据', '192.168.100.163', '2017-09-05 22:37:54', '0', '增加单据编号为  CGRK201709050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5203', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 22:37:54', '0', '保存仓管通明细对应主表编号为  57 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5204', '63', '增加单据', '192.168.100.163', '2017-09-05 22:45:23', '0', '增加单据编号为  LPXS201709050002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5205', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 22:45:23', '0', '保存仓管通明细对应主表编号为  58 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5206', '63', '更新单据', '192.168.100.163', '2017-09-05 22:45:31', '0', '更新单据ID为  58 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5207', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 22:45:31', '0', '保存仓管通明细对应主表编号为  58 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5208', '63', '登录系统', '192.168.100.163', '2017-09-05 23:10:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5209', '63', '增加单据', '192.168.100.163', '2017-09-05 23:12:18', '0', '增加单据编号为  LPCZ201709050002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5210', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 23:12:18', '0', '保存仓管通明细对应主表编号为  59 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5211', '63', '更新单据', '192.168.100.163', '2017-09-05 23:15:45', '0', '更新单据ID为  58 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5212', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 23:15:45', '0', '保存仓管通明细对应主表编号为  58 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5213', '63', '登录系统', '192.168.100.163', '2017-09-05 23:25:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5214', '63', '批量删除单据', '192.168.100.163', '2017-09-05 23:25:55', '0', '批量删除单据ID为  58,56 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5215', '63', '批量删除单据', '192.168.100.163', '2017-09-05 23:25:59', '0', '批量删除单据ID为  59,55 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5216', '63', '增加单据', '192.168.100.163', '2017-09-05 23:45:48', '0', '增加单据编号为  LPCZ201709050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5217', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 23:45:48', '0', '保存仓管通明细对应主表编号为  60 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5218', '63', '增加单据', '192.168.100.163', '2017-09-05 23:48:10', '0', '增加单据编号为  LPXS201709050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5219', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 23:48:11', '0', '保存仓管通明细对应主表编号为  61 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5220', '63', '增加单据', '192.168.100.163', '2017-09-05 23:52:42', '0', '增加单据编号为  LPCZ201709050002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5221', '63', '保存仓管通明细', '192.168.100.163', '2017-09-05 23:52:42', '0', '保存仓管通明细对应主表编号为  62 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5222', '63', '登录系统', '192.168.100.163', '2017-09-06 22:21:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5223', '63', '登录系统', '127.0.0.1', '2017-09-16 22:14:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5224', '63', '????', '127.0.0.1', '2017-09-16 22:40:55', '0', '?????jsh ????', 'jsh ????');
INSERT INTO `jsh_log` VALUES ('5225', '63', '????', '127.0.0.1', '2017-09-16 22:41:03', '0', '?????jsh ????', 'jsh ????');
INSERT INTO `jsh_log` VALUES ('5226', '63', '退出系统', '127.0.0.1', '2017-09-16 22:44:59', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5227', '63', '登录系统', '127.0.0.1', '2017-09-16 22:45:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5228', '63', '登录系统', '127.0.0.1', '2017-09-16 23:08:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5229', '63', '登录系统', '127.0.0.1', '2017-09-16 23:23:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5230', '63', '登录系统', '127.0.0.1', '2017-09-16 23:39:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5231', '63', '增加功能', '127.0.0.1', '2017-09-16 23:42:01', '0', '增加功能名称为  啊啊 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5232', '63', '更新功能', '127.0.0.1', '2017-09-16 23:42:44', '0', '更新功能ID为  230 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5233', '63', '增加功能', '127.0.0.1', '2017-09-16 23:44:51', '0', '增加功能名称为  溜溜 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5234', '63', '更新功能', '127.0.0.1', '2017-09-16 23:45:09', '0', '更新功能ID为  231 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5235', '63', '更新UserBusiness', '127.0.0.1', '2017-09-16 23:45:54', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5236', '63', '登录系统', '127.0.0.1', '2017-09-16 23:47:50', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5237', '63', '更新UserBusiness', '127.0.0.1', '2017-09-16 23:49:02', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5238', '63', '批量删除功能', '127.0.0.1', '2017-09-16 23:49:42', '0', '批量删除功能ID为  230,231 成功！', '批量删除功能成功');
INSERT INTO `jsh_log` VALUES ('5239', '63', '登录系统', '127.0.0.1', '2017-09-17 00:03:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5240', '63', '登录系统', '127.0.0.1', '2017-09-17 20:16:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5241', '63', '更新单据', '127.0.0.1', '2017-09-17 20:17:17', '0', '更新单据ID为  57 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5242', '63', '保存仓管通明细', '127.0.0.1', '2017-09-17 20:17:17', '0', '保存仓管通明细对应主表编号为  57 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5243', '63', '登录系统', '127.0.0.1', '2017-09-17 21:38:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5244', '63', '增加单据', '127.0.0.1', '2017-09-17 21:45:14', '0', '增加单据编号为  CGRK201709170001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5245', '63', '保存仓管通明细', '127.0.0.1', '2017-09-17 21:45:14', '0', '保存仓管通明细对应主表编号为  63 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5246', '63', '增加单据', '127.0.0.1', '2017-09-17 21:45:44', '0', '增加单据编号为  CGRK201709170002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5247', '63', '保存仓管通明细', '127.0.0.1', '2017-09-17 21:45:44', '0', '保存仓管通明细对应主表编号为  64 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5248', '63', '批量删除单据', '127.0.0.1', '2017-09-17 21:45:53', '0', '批量删除单据ID为  64 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5249', '63', '增加单据', '127.0.0.1', '2017-09-17 21:47:07', '0', '增加单据编号为  CGRK201709170002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5250', '63', '保存仓管通明细', '127.0.0.1', '2017-09-17 21:47:08', '0', '保存仓管通明细对应主表编号为  65 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5251', '63', '批量修改单据状态', '127.0.0.1', '2017-09-17 23:09:39', '0', '批量修改状态，单据ID为  63 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5252', '63', '登录系统', '106.120.11.23', '2017-09-19 20:56:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5253', '63', '批量修改单据状态', '106.39.148.33', '2017-09-19 20:57:32', '0', '批量修改状态，单据ID为  53 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5254', '63', '批量修改单据状态', '106.39.148.33', '2017-09-19 20:58:19', '0', '批量修改状态，单据ID为  53 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5255', '63', '批量修改单据状态', '106.39.148.33', '2017-09-19 20:58:43', '0', '批量修改状态，单据ID为  45 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5256', '63', '批量修改商品状态', '106.39.148.33', '2017-09-19 20:59:37', '0', '批量修改状态，商品ID为  559 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('5257', '63', '登录系统', '119.86.127.38', '2017-09-19 21:01:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5258', '63', '删除经手人', '106.120.11.23', '2017-09-19 21:02:02', '0', '删除经手人ID为  1 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('5259', '63', '删除经手人', '106.120.11.23', '2017-09-19 21:02:06', '0', '删除经手人ID为  2 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('5260', '63', '批量修改单据状态', '106.120.11.23', '2017-09-19 21:02:30', '0', '批量修改状态，单据ID为  65 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5261', '63', '登录系统', '220.112.121.70', '2017-09-19 21:05:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5262', '63', '登录系统', '115.195.91.50', '2017-09-19 21:07:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5263', '63', '退出系统', '115.195.91.50', '2017-09-19 21:08:05', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5264', '63', '登录系统', '117.64.147.64', '2017-09-19 21:22:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5265', '63', '登录系统', '211.148.101.34', '2017-09-19 22:15:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5266', '63', '登录系统', '211.148.101.34', '2017-09-19 22:16:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5267', '63', '登录系统', '117.175.132.159', '2017-09-19 22:16:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5268', '63', '登录系统', '117.75.19.137', '2017-09-19 22:33:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5269', '63', '登录系统', '183.198.47.127', '2017-09-19 22:35:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5270', '63', '删除应用', '114.62.212.4', '2017-09-19 22:48:34', '0', '删除应用ID为  21 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('5271', '63', '删除应用', '114.62.212.4', '2017-09-19 22:48:50', '0', '删除应用ID为  1 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('5272', '63', '删除应用', '114.62.212.4', '2017-09-19 22:49:20', '0', '删除应用ID为  20 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('5273', '63', '更新应用', '114.62.212.4', '2017-09-19 22:52:57', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5274', '63', '更新应用', '114.62.212.4', '2017-09-19 22:53:02', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5275', '63', '更新应用', '114.62.212.4', '2017-09-19 22:59:14', '0', '更新应用ID为  26 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5276', '63', '增加功能', '114.62.212.4', '2017-09-19 22:59:43', '0', '增加功能名称为  组装拆卸 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5277', '63', '更新功能', '114.62.212.4', '2017-09-19 23:01:10', '0', '更新功能ID为  230 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5278', '63', '更新功能', '114.62.212.4', '2017-09-19 23:01:32', '0', '更新功能ID为  221 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5279', '63', '更新功能', '114.62.212.4', '2017-09-19 23:01:51', '0', '更新功能ID为  196 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5280', '63', '增加功能', '114.62.212.4', '2017-09-19 23:05:09', '0', '增加功能名称为  组装单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5281', '63', '增加功能', '114.62.212.4', '2017-09-19 23:06:05', '0', '增加功能名称为  拆卸单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5282', '63', '更新UserBusiness', '114.62.212.4', '2017-09-19 23:06:52', '0', '更新UserBusiness的ID为  1 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5283', '63', '更新UserBusiness', '114.62.212.4', '2017-09-19 23:07:15', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5284', '63', '更新功能', '114.62.212.4', '2017-09-19 23:08:36', '0', '更新功能ID为  231 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5285', '63', '更新功能', '114.62.212.4', '2017-09-19 23:09:08', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5286', '63', '更新功能', '114.62.212.4', '2017-09-19 23:09:20', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5287', '63', '增加功能', '114.62.212.4', '2017-09-19 23:09:59', '0', '增加功能名称为  拆卸单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5288', '63', '登录系统', '183.38.180.158', '2017-09-19 23:26:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5289', '63', '更新功能', '114.62.212.4', '2017-09-19 23:26:22', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5290', '63', '更新功能', '114.62.212.4', '2017-09-19 23:26:34', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5291', '63', '更新功能', '114.62.212.4', '2017-09-19 23:28:10', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5292', '63', '更新功能', '114.62.212.4', '2017-09-19 23:28:24', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5293', '63', '登录系统', '127.0.0.1', '2017-09-19 23:32:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5294', '63', '登录系统', '127.0.0.1', '2017-09-20 21:32:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5295', '63', '增加单据', '127.0.0.1', '2017-09-20 22:59:53', '0', '增加单据编号为  ZZD201709200001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5296', '63', '保存仓管通明细', '127.0.0.1', '2017-09-20 22:59:53', '0', '保存仓管通明细对应主表编号为  66 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5297', '63', '登录系统', '127.0.0.1', '2017-09-20 23:25:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5298', '63', '增加单据', '127.0.0.1', '2017-09-20 23:26:34', '0', '增加单据编号为  ZZD201709200002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5299', '63', '保存仓管通明细', '127.0.0.1', '2017-09-20 23:26:35', '0', '保存仓管通明细对应主表编号为  67 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5300', '63', '批量删除单据', '127.0.0.1', '2017-09-20 23:28:45', '0', '批量删除单据ID为  66 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5301', '63', '批量删除单据', '127.0.0.1', '2017-09-20 23:28:48', '0', '批量删除单据ID为  67 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5302', '63', '增加单据', '127.0.0.1', '2017-09-20 23:29:28', '0', '增加单据编号为  ZZD201709200001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5303', '63', '保存仓管通明细', '127.0.0.1', '2017-09-20 23:29:28', '0', '保存仓管通明细对应主表编号为  68 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5304', '63', '更新功能', '127.0.0.1', '2017-09-20 23:30:44', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5305', '63', '更新功能', '127.0.0.1', '2017-09-20 23:30:52', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5306', '63', '更新功能', '127.0.0.1', '2017-09-20 23:30:55', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5307', '63', '更新功能', '127.0.0.1', '2017-09-20 23:32:57', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5308', '63', '更新角色按钮权限', '', '2017-09-20 23:33:53', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5309', '63', '增加单据', '127.0.0.1', '2017-09-20 23:40:55', '0', '增加单据编号为  CSD201709200001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5310', '63', '保存仓管通明细', '127.0.0.1', '2017-09-20 23:40:56', '0', '保存仓管通明细对应主表编号为  69 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5311', '63', '增加单据', '127.0.0.1', '2017-09-20 23:42:49', '0', '增加单据编号为  CGRK201709200001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5312', '63', '保存仓管通明细', '127.0.0.1', '2017-09-20 23:42:50', '0', '保存仓管通明细对应主表编号为  70 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5313', '63', '删除单据', '127.0.0.1', '2017-09-20 23:43:08', '0', '删除单据ID为  70 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5314', '63', '登录系统', '127.0.0.1', '2017-09-21 22:32:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5315', '63', '增加单据', '127.0.0.1', '2017-09-21 22:37:20', '0', '增加单据编号为  CGRK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5316', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:37:21', '0', '保存仓管通明细对应主表编号为  70 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5317', '63', '增加单据', '127.0.0.1', '2017-09-21 22:39:00', '0', '增加单据编号为  XSTH201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5318', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:39:01', '0', '保存仓管通明细对应主表编号为  71 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5319', '63', '增加单据', '127.0.0.1', '2017-09-21 22:39:26', '0', '增加单据编号为  QTRK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5320', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:39:26', '0', '保存仓管通明细对应主表编号为  72 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5321', '63', '增加单据', '127.0.0.1', '2017-09-21 22:40:01', '0', '增加单据编号为  XSCK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5322', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:40:01', '0', '保存仓管通明细对应主表编号为  73 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5323', '63', '增加单据', '127.0.0.1', '2017-09-21 22:40:57', '0', '增加单据编号为  CGTH201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5324', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:40:57', '0', '保存仓管通明细对应主表编号为  74 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5325', '63', '增加单据', '127.0.0.1', '2017-09-21 22:41:15', '0', '增加单据编号为  QTCK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5326', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:41:15', '0', '保存仓管通明细对应主表编号为  75 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5327', '63', '增加单据', '127.0.0.1', '2017-09-21 22:41:36', '0', '增加单据编号为  DBCK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5328', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:41:36', '0', '保存仓管通明细对应主表编号为  76 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5329', '63', '增加单据', '127.0.0.1', '2017-09-21 22:42:44', '0', '增加单据编号为  LSCK201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5330', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:42:44', '0', '保存仓管通明细对应主表编号为  77 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5331', '63', '增加单据', '127.0.0.1', '2017-09-21 22:46:07', '0', '增加单据编号为  LSTH201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5332', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 22:46:07', '0', '保存仓管通明细对应主表编号为  78 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5333', '63', '更新功能', '127.0.0.1', '2017-09-21 22:50:07', '0', '更新功能ID为  214 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5334', '63', '更新功能', '127.0.0.1', '2017-09-21 22:50:28', '0', '更新功能ID为  215 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5335', '63', '更新角色按钮权限', '', '2017-09-21 22:50:50', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5336', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 22:51:05', '0', '批量修改状态，单据ID为  62 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5337', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 22:51:08', '0', '批量修改状态，单据ID为  62 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5338', '63', '更新功能', '127.0.0.1', '2017-09-21 22:54:01', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5339', '63', '更新功能', '127.0.0.1', '2017-09-21 22:54:26', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5340', '63', '更新功能', '127.0.0.1', '2017-09-21 22:54:32', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5341', '63', '更新UserBusiness', '127.0.0.1', '2017-09-21 23:04:48', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5342', '63', '更新UserBusiness', '127.0.0.1', '2017-09-21 23:05:30', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5343', '63', '更新角色按钮权限', '', '2017-09-21 23:05:53', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5344', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 23:08:01', '0', '批量修改状态，单据ID为  69 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5345', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 23:08:03', '0', '批量修改状态，单据ID为  69 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5346', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 23:08:07', '0', '批量修改状态，单据ID为  68 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5347', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 23:08:09', '0', '批量修改状态，单据ID为  68 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5348', '63', '增加单据', '127.0.0.1', '2017-09-21 23:16:37', '0', '增加单据编号为  CGRK201709210002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5349', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 23:16:37', '0', '保存仓管通明细对应主表编号为  79 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5350', '63', '增加单据', '127.0.0.1', '2017-09-21 23:17:16', '0', '增加单据编号为  ZZD201709210001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5351', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 23:17:16', '0', '保存仓管通明细对应主表编号为  80 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5352', '63', '更新单据', '127.0.0.1', '2017-09-21 23:18:48', '0', '更新单据ID为  69 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5353', '63', '更新单据', '127.0.0.1', '2017-09-21 23:23:39', '0', '更新单据ID为  80 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5354', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 23:23:40', '0', '保存仓管通明细对应主表编号为  80 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5355', '63', '更新单据', '127.0.0.1', '2017-09-21 23:24:11', '0', '更新单据ID为  68 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5356', '63', '保存仓管通明细', '127.0.0.1', '2017-09-21 23:24:11', '0', '保存仓管通明细对应主表编号为  68 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5357', '63', '批量修改单据状态', '127.0.0.1', '2017-09-21 23:24:17', '0', '批量修改状态，单据ID为  80 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5358', '63', '登录系统', '127.0.0.1', '2017-09-22 21:45:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5359', '63', '登录系统', '127.0.0.1', '2017-09-22 22:30:07', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5360', '63', '批量修改单据状态', '127.0.0.1', '2017-09-22 22:37:57', '0', '批量修改状态，单据ID为  65 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5361', '63', '更新单据', '127.0.0.1', '2017-09-22 22:38:19', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5362', '63', '增加单据', '127.0.0.1', '2017-09-22 22:43:28', '0', '增加单据编号为  CGRK201709220001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5363', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 22:43:28', '0', '保存仓管通明细对应主表编号为  81 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5364', '63', '更新单据', '127.0.0.1', '2017-09-22 22:46:52', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5365', '63', '更新单据', '127.0.0.1', '2017-09-22 22:51:06', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5366', '63', '更新单据', '127.0.0.1', '2017-09-22 22:51:30', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5367', '63', '登录系统', '127.0.0.1', '2017-09-22 22:57:39', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5368', '63', '更新单据', '127.0.0.1', '2017-09-22 22:59:16', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5369', '63', '更新单据', '127.0.0.1', '2017-09-22 23:02:38', '0', '更新单据ID为  81 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5370', '63', '更新单据', '127.0.0.1', '2017-09-22 23:02:49', '0', '更新单据ID为  81 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5371', '63', '更新单据', '127.0.0.1', '2017-09-22 23:03:10', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5372', '63', '更新单据', '127.0.0.1', '2017-09-22 23:03:39', '0', '更新单据ID为  81 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5373', '63', '更新单据', '127.0.0.1', '2017-09-22 23:04:39', '0', '更新单据ID为  81 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5374', '63', '更新单据', '127.0.0.1', '2017-09-22 23:05:17', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5375', '63', '删除单据', '127.0.0.1', '2017-09-22 23:05:37', '0', '删除单据ID为  81 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5376', '63', '增加单据', '127.0.0.1', '2017-09-22 23:06:01', '0', '增加单据编号为  CGRK201709220001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5377', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:06:02', '0', '保存仓管通明细对应主表编号为  82 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5378', '63', '更新单据', '127.0.0.1', '2017-09-22 23:07:13', '0', '更新单据ID为  82 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5379', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:07:14', '0', '保存仓管通明细对应主表编号为  82 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5380', '63', '增加单据', '127.0.0.1', '2017-09-22 23:15:38', '0', '增加单据编号为  LSCK201709220001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5381', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:15:38', '0', '保存仓管通明细对应主表编号为  83 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5382', '63', '更新供应商预付款', '', '2017-09-22 23:15:42', '0', '更新供应商ID为  7 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5383', '63', '批量删除单据', '127.0.0.1', '2017-09-22 23:15:42', '0', '批量删除单据ID为  83 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5384', '63', '增加单据', '127.0.0.1', '2017-09-22 23:22:02', '0', '增加单据编号为  CGRK201709220002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5385', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:22:03', '0', '保存仓管通明细对应主表编号为  84 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5386', '63', '更新单据', '127.0.0.1', '2017-09-22 23:22:54', '0', '更新单据ID为  54 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5387', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:22:55', '0', '保存仓管通明细对应主表编号为  54 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5388', '63', '更新单据', '127.0.0.1', '2017-09-22 23:23:50', '0', '更新单据ID为  70 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5389', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:23:50', '0', '保存仓管通明细对应主表编号为  70 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5390', '63', '更新单据', '127.0.0.1', '2017-09-22 23:25:18', '0', '更新单据ID为  73 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5391', '63', '保存仓管通明细', '127.0.0.1', '2017-09-22 23:25:18', '0', '保存仓管通明细对应主表编号为  73 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5392', '63', '登录系统', '127.0.0.1', '2017-09-24 22:26:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5393', '63', '登录系统', '127.0.0.1', '2017-09-24 22:31:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5394', '63', '增加单据', '127.0.0.1', '2017-09-24 22:46:00', '0', '增加单据编号为  CGRK201709240001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5395', '63', '保存仓管通明细', '127.0.0.1', '2017-09-24 22:46:00', '0', '保存仓管通明细对应主表编号为  85 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5396', '63', '登录系统', '127.0.0.1', '2017-09-25 20:37:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5397', '63', '增加单据', '127.0.0.1', '2017-09-25 22:19:30', '0', '增加单据编号为  XSCK201709250001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5398', '63', '保存仓管通明细', '127.0.0.1', '2017-09-25 22:19:30', '0', '保存仓管通明细对应主表编号为  86 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5399', '63', '批量删除单据', '127.0.0.1', '2017-09-25 22:21:04', '0', '批量删除单据ID为  86 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5400', '63', '增加单据', '127.0.0.1', '2017-09-25 22:24:09', '0', '增加单据编号为  XSCK201709250001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5401', '63', '保存仓管通明细', '127.0.0.1', '2017-09-25 22:24:09', '0', '保存仓管通明细对应主表编号为  87 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5402', '63', '增加单据', '127.0.0.1', '2017-09-25 22:36:51', '0', '增加单据编号为  XSCK201709250002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5403', '63', '保存仓管通明细', '127.0.0.1', '2017-09-25 22:36:51', '0', '保存仓管通明细对应主表编号为  88 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5404', '63', '更新商品', '127.0.0.1', '2017-09-26 00:19:20', '0', '更新商品ID为  517 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5405', '63', '增加单据', '127.0.0.1', '2017-09-26 00:26:52', '0', '增加单据编号为  LSTH201709260001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5406', '63', '保存仓管通明细', '127.0.0.1', '2017-09-26 00:26:53', '0', '保存仓管通明细对应主表编号为  89 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5407', '63', '登录系统', '127.0.0.1', '2017-09-26 22:27:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5408', '63', '批量删除商品', '127.0.0.1', '2017-09-26 22:29:04', '0', '批量删除商品ID为  501 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('5409', '63', '增加单据', '127.0.0.1', '2017-09-26 22:31:24', '0', '增加单据编号为  LSCK201709260001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5410', '63', '保存仓管通明细', '127.0.0.1', '2017-09-26 22:31:24', '0', '保存仓管通明细对应主表编号为  90 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5411', '63', '更新单据', '127.0.0.1', '2017-09-26 22:36:34', '0', '更新单据ID为  90 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5412', '63', '保存仓管通明细', '127.0.0.1', '2017-09-26 22:36:34', '0', '保存仓管通明细对应主表编号为  90 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5413', '63', '登录系统', '127.0.0.1', '2017-09-27 23:28:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5414', '63', '登录系统', '127.0.0.1', '2017-09-27 23:31:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5415', '63', '登录系统', '127.0.0.1', '2017-09-29 21:19:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5416', '63', '更新角色按钮权限', '', '2017-09-29 21:30:06', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5417', '63', '更新角色按钮权限', '', '2017-09-29 21:33:48', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5418', '63', '更新角色按钮权限', '', '2017-09-29 21:37:25', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5419', '63', '更新角色按钮权限', '', '2017-09-29 21:37:34', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5420', '63', '退出系统', '127.0.0.1', '2017-09-29 21:48:56', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5421', '63', '登录系统', '127.0.0.1', '2017-09-29 21:49:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5422', '63', '更新角色按钮权限', '', '2017-09-29 22:00:00', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5423', '63', '更新角色按钮权限', '', '2017-09-29 22:02:55', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5424', '63', '更新角色按钮权限', '', '2017-09-29 22:03:34', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5425', '63', '更新角色按钮权限', '', '2017-09-29 22:04:22', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5426', '63', '更新角色按钮权限', '', '2017-09-29 22:05:42', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5427', '63', '更新角色按钮权限', '', '2017-09-29 22:06:00', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5428', '63', '更新角色按钮权限', '', '2017-09-29 22:06:23', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5429', '63', '更新角色按钮权限', '', '2017-09-29 22:06:33', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5430', '63', '更新角色按钮权限', '', '2017-09-29 22:11:50', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5431', '63', '更新角色按钮权限', '', '2017-09-29 22:12:09', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5432', '63', '更新角色按钮权限', '', '2017-09-29 22:12:31', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5433', '63', '登录系统', '127.0.0.1', '2017-09-29 23:17:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5434', '63', '更新角色按钮权限', '', '2017-09-29 23:17:30', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5435', '63', '更新角色按钮权限', '', '2017-09-29 23:18:38', '0', '角色按钮权限的ID为  5 成功！', '更新角色按钮权限成功');
INSERT INTO `jsh_log` VALUES ('5436', '63', '登录系统', '127.0.0.1', '2017-10-05 11:11:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5437', '63', '登录系统', '127.0.0.1', '2017-10-05 11:42:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5438', '63', '登录系统', '127.0.0.1', '2017-10-05 13:34:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5439', '63', '增加经手人', '127.0.0.1', '2017-10-05 13:34:59', '0', '增加经手人名称为  22222 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('5440', '63', '更新经手人', '127.0.0.1', '2017-10-05 13:35:05', '0', '更新经手人ID为  8 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('5441', '63', '批量删除经手人', '127.0.0.1', '2017-10-05 13:35:13', '0', '批量删除经手人ID为  8 成功！', '批量删除经手人成功');
INSERT INTO `jsh_log` VALUES ('5442', '63', '增加经手人', '127.0.0.1', '2017-10-05 13:35:41', '0', '增加经手人名称为  789 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('5443', '63', '更新经手人', '127.0.0.1', '2017-10-05 13:35:46', '0', '更新经手人ID为  9 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('5444', '63', '删除经手人', '127.0.0.1', '2017-10-05 13:35:52', '0', '删除经手人ID为  9 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('5445', '63', '登录系统', '127.0.0.1', '2017-10-05 13:38:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5446', '63', '登录系统', '127.0.0.1', '2017-10-05 13:41:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5447', '63', '增加经手人', '127.0.0.1', '2017-10-05 13:41:35', '0', '增加经手人名称为  123123 成功！', '增加经手人成功');
INSERT INTO `jsh_log` VALUES ('5448', '63', '更新经手人', '127.0.0.1', '2017-10-05 13:41:40', '0', '更新经手人ID为  10 成功！', '更新经手人成功');
INSERT INTO `jsh_log` VALUES ('5449', '63', '删除经手人', '127.0.0.1', '2017-10-05 13:41:42', '0', '删除经手人ID为  10 成功！', '删除经手人成功');
INSERT INTO `jsh_log` VALUES ('5450', '63', '登录系统', '127.0.0.1', '2017-10-05 13:51:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5451', '63', '增加供应商', '127.0.0.1', '2017-10-05 13:51:43', '0', '增加供应商名称为  456456 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('5452', '63', '删除供应商', '127.0.0.1', '2017-10-05 13:51:47', '0', '删除供应商ID为  47,名称为  456456成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('5453', '63', '增加供应商', '127.0.0.1', '2017-10-05 13:52:05', '0', '增加供应商名称为  66666 成功！', '增加供应商成功');
INSERT INTO `jsh_log` VALUES ('5454', '63', '更新供应商', '127.0.0.1', '2017-10-05 13:52:14', '0', '更新供应商ID为  48 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5455', '63', '删除供应商', '127.0.0.1', '2017-10-05 13:52:23', '0', '删除供应商ID为  48,名称为  666667777成功！', '删除供应商成功');
INSERT INTO `jsh_log` VALUES ('5456', '63', '批量修改单位状态', '127.0.0.1', '2017-10-05 14:06:31', '0', '批量修改状态，单位ID为  46 成功！', '批量修改单位状态成功');
INSERT INTO `jsh_log` VALUES ('5457', '63', '批量修改单位状态', '127.0.0.1', '2017-10-05 14:06:36', '0', '批量修改状态，单位ID为  46 成功！', '批量修改单位状态成功');
INSERT INTO `jsh_log` VALUES ('5458', '63', '增加功能', '127.0.0.1', '2017-10-05 14:29:24', '0', '增加功能名称为  系统配置 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5459', '63', '更新UserBusiness', '127.0.0.1', '2017-10-05 14:30:10', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5460', '63', '登录系统', '127.0.0.1', '2017-10-05 15:36:25', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5461', '63', '更新系统配置', '', '2017-10-05 15:36:33', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5462', '63', '更新系统配置', '', '2017-10-05 15:37:05', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5463', '63', '更新系统配置', '', '2017-10-05 15:38:17', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5464', '63', '更新系统配置', '', '2017-10-05 15:44:50', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5465', '63', '更新系统配置', '', '2017-10-05 15:45:09', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5466', '63', '更新系统配置', '', '2017-10-05 15:45:25', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5467', '63', '更新系统配置', '', '2017-10-05 15:45:30', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5468', '63', '更新系统配置', '', '2017-10-05 15:48:21', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5469', '63', '更新系统配置', '', '2017-10-05 15:48:33', '0', '更新系统配置ID为  2 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5470', '63', '更新系统配置', '', '2017-10-05 15:51:37', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5471', '63', '更新系统配置', '', '2017-10-05 15:51:45', '0', '更新系统配置ID为  2 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5472', '63', '更新系统配置', '', '2017-10-05 15:57:50', '0', '更新系统配置ID为  2 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5473', '63', '更新系统配置', '', '2017-10-05 15:58:04', '0', '更新系统配置ID为  2 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5474', '63', '更新系统配置', '', '2017-10-05 16:14:50', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5475', '63', '更新系统配置', '', '2017-10-05 16:15:22', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5476', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5477', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  6 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5478', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  4 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5479', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  2 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5480', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  3 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5481', '63', '更新系统配置', '', '2017-10-05 16:16:39', '0', '更新系统配置ID为  5 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5482', '63', '登录系统', '127.0.0.1', '2017-10-05 17:03:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5483', '63', '更新系统配置', '', '2017-10-05 17:04:20', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5484', '63', '更新系统配置', '', '2017-10-05 17:04:40', '0', '更新系统配置ID为  1 成功！', '更新系统配置成功');
INSERT INTO `jsh_log` VALUES ('5485', '63', '登录系统', '127.0.0.1', '2017-10-05 17:06:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5486', '63', '登录系统', '127.0.0.1', '2017-10-06 11:23:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5487', '63', '更新商品类别', '127.0.0.1', '2017-10-06 11:24:20', '0', '更新商品类别ID为  9 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('5488', '63', '更新商品类别', '127.0.0.1', '2017-10-06 11:24:24', '0', '更新商品类别ID为  3 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('5489', '63', '登录系统', '127.0.0.1', '2017-10-06 13:22:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5490', '63', '登录系统', '127.0.0.1', '2017-10-06 15:08:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5491', '63', '增加功能', '127.0.0.1', '2017-10-06 15:24:54', '0', '增加功能名称为  客户对账单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5492', '63', '增加功能', '127.0.0.1', '2017-10-06 15:26:09', '0', '增加功能名称为  供应商对账单 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5493', '63', '更新UserBusiness', '127.0.0.1', '2017-10-06 15:27:18', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5494', '63', '更新UserBusiness', '127.0.0.1', '2017-10-06 15:40:27', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5495', '63', '删除功能', '127.0.0.1', '2017-10-06 15:40:41', '0', '删除功能ID为  236 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('5496', '63', '更新功能', '127.0.0.1', '2017-10-06 15:41:12', '0', '更新功能ID为  235 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5497', '63', '更新UserBusiness', '127.0.0.1', '2017-10-06 15:41:24', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5498', '63', '登录系统', '127.0.0.1', '2017-10-06 15:52:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5499', '63', '登录系统', '127.0.0.1', '2017-10-06 15:59:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5500', '63', '登录系统', '127.0.0.1', '2017-10-06 16:02:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5501', '63', '登录系统', '127.0.0.1', '2017-10-06 16:31:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5502', '63', '登录系统', '127.0.0.1', '2017-10-06 16:42:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5503', '63', '增加单据', '127.0.0.1', '2017-10-06 16:59:38', '0', '增加单据编号为  XSCK2027060001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5504', '63', '保存仓管通明细', '127.0.0.1', '2017-10-06 16:59:39', '0', '保存仓管通明细对应主表编号为  91 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5505', '63', '登录系统', '127.0.0.1', '2017-10-08 11:21:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5506', '63', '登录系统', '127.0.0.1', '2017-10-08 18:54:36', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5507', '63', '增加单据', '127.0.0.1', '2017-10-08 18:56:50', '0', '增加单据编号为  XSCK2027080001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5508', '63', '保存仓管通明细', '127.0.0.1', '2017-10-08 18:56:51', '0', '保存仓管通明细对应主表编号为  92 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5509', '63', '批量删除单据', '127.0.0.1', '2017-10-08 19:10:27', '0', '批量删除单据ID为  92,91 成功！', '批量删除单据成功');
INSERT INTO `jsh_log` VALUES ('5510', '63', '更新仓库', '127.0.0.1', '2017-10-08 19:11:19', '0', '更新仓库ID为  1 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('5511', '63', '更新仓库', '127.0.0.1', '2017-10-08 19:11:29', '0', '更新仓库ID为  3 成功！', '更新仓库成功');
INSERT INTO `jsh_log` VALUES ('5512', '63', '增加单据', '127.0.0.1', '2017-10-08 19:12:23', '0', '增加单据编号为  XSCK201710080001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5513', '63', '保存仓管通明细', '127.0.0.1', '2017-10-08 19:12:23', '0', '保存仓管通明细对应主表编号为  93 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5514', '63', '增加财务', '127.0.0.1', '2017-10-08 19:14:56', '0', '增加财务编号为  SK20171008191440 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5515', '63', '保存财务明细', '127.0.0.1', '2017-10-08 19:14:56', '0', '保存财务明细对应主表编号为  82 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5516', '63', '增加单据', '127.0.0.1', '2017-10-08 19:58:55', '0', '增加单据编号为  XSCK201710080002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5517', '63', '保存仓管通明细', '127.0.0.1', '2017-10-08 19:58:55', '0', '保存仓管通明细对应主表编号为  94 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5518', '63', '更新单据', '127.0.0.1', '2017-10-08 20:03:09', '0', '更新单据ID为  94 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5519', '63', '更新单据', '127.0.0.1', '2017-10-08 20:05:31', '0', '更新单据ID为  94 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5520', '63', '更新单据', '127.0.0.1', '2017-10-08 20:05:38', '0', '更新单据ID为  94 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5521', '63', '更新单据', '127.0.0.1', '2017-10-08 20:05:44', '0', '更新单据ID为  94 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5522', '63', '更新应用', '127.0.0.1', '2017-10-08 20:47:54', '0', '更新应用ID为  26 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5523', '63', '更新财务', '127.0.0.1', '2017-10-08 23:27:25', '0', '更新财务ID为  80 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5524', '63', '增加财务', '127.0.0.1', '2017-10-08 23:28:36', '0', '增加财务编号为  FK20171008232825 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5525', '63', '保存财务明细', '127.0.0.1', '2017-10-08 23:28:37', '0', '保存财务明细对应主表编号为  83 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5526', '63', '登录系统', '127.0.0.1', '2017-10-09 00:02:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5527', '63', '增加财务', '127.0.0.1', '2017-10-09 00:03:13', '0', '增加财务编号为  SR20171009000300 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5528', '63', '保存财务明细', '127.0.0.1', '2017-10-09 00:03:13', '0', '保存财务明细对应主表编号为  84 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5529', '63', '登录系统', '127.0.0.1', '2017-10-09 00:04:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5530', '63', '登录系统', '127.0.0.1', '2017-10-09 00:06:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5531', '63', '增加财务', '127.0.0.1', '2017-10-09 00:06:57', '0', '增加财务编号为  SR20171009000637 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5532', '63', '保存财务明细', '127.0.0.1', '2017-10-09 00:06:57', '0', '保存财务明细对应主表编号为  85 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5533', '63', '删除财务', '127.0.0.1', '2017-10-09 00:07:16', '0', '删除财务ID为  68 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('5534', '63', '增加财务', '127.0.0.1', '2017-10-09 00:07:28', '0', '增加财务编号为  ZZ20171009000719 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5535', '63', '保存财务明细', '127.0.0.1', '2017-10-09 00:07:28', '0', '保存财务明细对应主表编号为  86 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5536', '63', '更新财务', '127.0.0.1', '2017-10-09 00:07:40', '0', '更新财务ID为  86 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5537', '63', '增加财务', '127.0.0.1', '2017-10-09 00:08:00', '0', '增加财务编号为  FK20171009000747 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5538', '63', '保存财务明细', '127.0.0.1', '2017-10-09 00:08:00', '0', '保存财务明细对应主表编号为  87 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5539', '63', '更新财务', '127.0.0.1', '2017-10-09 00:08:13', '0', '更新财务ID为  82 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5540', '63', '登录系统', '127.0.0.1', '2017-10-11 00:05:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5541', '63', '更新商品', '127.0.0.1', '2017-10-11 00:06:22', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5542', '63', '更新商品', '127.0.0.1', '2017-10-11 00:06:38', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5543', '63', '更新商品', '127.0.0.1', '2017-10-11 00:09:05', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5544', '63', '登录系统', '127.0.0.1', '2017-10-11 00:14:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5545', '63', '更新商品', '127.0.0.1', '2017-10-11 00:14:53', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5546', '63', '登录系统', '127.0.0.1', '2017-10-12 00:18:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5547', '63', '登录系统', '127.0.0.1', '2017-10-16 00:32:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5548', '63', '更新商品', '127.0.0.1', '2017-10-16 00:33:03', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5549', '63', '更新商品', '127.0.0.1', '2017-10-16 00:33:23', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5550', '63', '登录系统', '127.0.0.1', '2017-10-18 22:24:06', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5551', '63', '更新UserBusiness', '127.0.0.1', '2017-10-18 22:37:56', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5552', '63', '登录系统', '127.0.0.1', '2017-10-18 22:43:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5553', '63', '登录系统', '127.0.0.1', '2017-10-18 23:16:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5554', '63', '更新商品', '127.0.0.1', '2017-10-18 23:16:41', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5555', '63', '更新商品', '127.0.0.1', '2017-10-18 23:16:55', '0', '更新商品ID为  518 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5556', '63', '更新商品属性', '127.0.0.1', '2017-10-18 23:17:47', '0', '更新商品属性ID为  2 成功！', '更新商品属性成功');
INSERT INTO `jsh_log` VALUES ('5557', '63', '更新商品属性', '127.0.0.1', '2017-10-18 23:17:52', '0', '更新商品属性ID为  1 成功！', '更新商品属性成功');
INSERT INTO `jsh_log` VALUES ('5558', '63', '增加单据', '127.0.0.1', '2017-10-18 23:21:24', '0', '增加单据编号为  CGRK201710180001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5559', '63', '保存仓管通明细', '127.0.0.1', '2017-10-18 23:21:24', '0', '保存仓管通明细对应主表编号为  95 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5560', '63', '登录系统', '127.0.0.1', '2017-10-19 23:48:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5561', '63', '登录系统', '127.0.0.1', '2017-10-23 20:36:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5562', '63', '更新商品', '127.0.0.1', '2017-10-23 20:37:15', '0', '更新商品ID为  485 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5563', '63', '删除商品', '127.0.0.1', '2017-10-23 20:40:02', '0', '删除商品ID为  560 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('5564', '63', '登录系统', '127.0.0.1', '2017-10-23 23:37:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5565', '63', '登录系统', '127.0.0.1', '2017-10-24 19:46:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5566', '63', '增加商品', '127.0.0.1', '2017-10-24 19:51:06', '0', '增加商品名称为  网布 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('5567', '63', '更新商品', '127.0.0.1', '2017-10-24 19:51:38', '0', '更新商品ID为  560 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5568', '63', '登录系统', '127.0.0.1', '2017-10-24 21:01:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5569', '63', '批量删除商品', '127.0.0.1', '2017-10-24 21:02:08', '0', '批量删除商品ID为  560 成功！', '批量删除商品成功');
INSERT INTO `jsh_log` VALUES ('5570', '63', '增加商品', '127.0.0.1', '2017-10-24 21:05:54', '0', '增加商品名称为  棉线 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('5571', '63', '更新商品', '127.0.0.1', '2017-10-24 21:06:06', '0', '更新商品ID为  561 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5572', '63', '更新商品', '127.0.0.1', '2017-10-24 21:06:36', '0', '更新商品ID为  561 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5573', '63', '更新商品', '127.0.0.1', '2017-10-24 21:06:43', '0', '更新商品ID为  561 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5574', '63', '删除商品', '127.0.0.1', '2017-10-24 21:06:57', '0', '删除商品ID为  561 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('5575', '63', '增加商品', '127.0.0.1', '2017-10-24 21:07:25', '0', '增加商品名称为  奶酪 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('5576', '63', '更新商品', '127.0.0.1', '2017-10-24 21:07:43', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5577', '63', '更新商品', '127.0.0.1', '2017-10-24 21:08:24', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5578', '63', '更新商品', '127.0.0.1', '2017-10-24 21:08:39', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5579', '63', '更新商品', '127.0.0.1', '2017-10-24 21:08:48', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5580', '63', '更新商品', '127.0.0.1', '2017-10-24 21:08:53', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5581', '63', '更新商品', '127.0.0.1', '2017-10-24 21:11:46', '0', '更新商品ID为  562 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5582', '63', '增加商品', '127.0.0.1', '2017-10-24 21:13:22', '0', '增加商品名称为  红苹果（蛇果） 成功！', '增加商品成功');
INSERT INTO `jsh_log` VALUES ('5583', '63', '更新商品', '127.0.0.1', '2017-10-24 21:14:11', '0', '更新商品ID为  563 成功！', '更新商品成功');
INSERT INTO `jsh_log` VALUES ('5584', '63', '删除商品', '127.0.0.1', '2017-10-24 21:14:24', '0', '删除商品ID为  563 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('5585', '63', '删除商品', '127.0.0.1', '2017-10-24 21:16:56', '0', '删除商品ID为  559 成功！', '删除商品成功');
INSERT INTO `jsh_log` VALUES ('5586', '63', '批量修改商品状态', '127.0.0.1', '2017-10-24 21:18:55', '0', '批量修改状态，商品ID为  562 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('5587', '63', '批量修改商品状态', '127.0.0.1', '2017-10-24 21:18:59', '0', '批量修改状态，商品ID为  562 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('5588', '63', '批量修改商品状态', '127.0.0.1', '2017-10-24 21:19:04', '0', '批量修改状态，商品ID为  562 成功！', '批量修改商品状态成功');
INSERT INTO `jsh_log` VALUES ('5589', '63', '登录系统', '127.0.0.1', '2017-10-24 21:33:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5590', '63', '增加商品类别', '127.0.0.1', '2017-10-24 21:37:38', '0', '增加商品类别名称为  111 成功！', '增加商品类别成功');
INSERT INTO `jsh_log` VALUES ('5591', '63', '更新商品类别', '127.0.0.1', '2017-10-24 21:37:55', '0', '更新商品类别ID为  11 成功！', '更新商品类别成功');
INSERT INTO `jsh_log` VALUES ('5592', '63', '删除商品类别', '127.0.0.1', '2017-10-24 21:38:09', '0', '删除商品类别ID为  11 成功！', '删除商品类别成功');
INSERT INTO `jsh_log` VALUES ('5593', '63', '增加单据', '127.0.0.1', '2017-10-24 22:04:06', '0', '增加单据编号为  XSCK201710240001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5594', '63', '保存仓管通明细', '127.0.0.1', '2017-10-24 22:04:07', '0', '保存仓管通明细对应主表编号为  96 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5595', '63', '更新单据', '127.0.0.1', '2017-10-24 22:05:04', '0', '更新单据ID为  96 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5596', '63', '更新单据', '127.0.0.1', '2017-10-24 22:05:11', '0', '更新单据ID为  96 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5597', '63', '增加财务', '127.0.0.1', '2017-10-24 22:08:21', '0', '增加财务编号为  SK20171024220754 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5598', '63', '保存财务明细', '127.0.0.1', '2017-10-24 22:08:22', '0', '保存财务明细对应主表编号为  88 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5599', '63', '更新财务', '127.0.0.1', '2017-10-24 22:48:40', '0', '更新财务ID为  88 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5600', '63', '保存财务明细', '127.0.0.1', '2017-10-24 22:48:40', '0', '保存财务明细对应主表编号为  88 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5601', '63', '更新财务', '127.0.0.1', '2017-10-24 22:48:47', '0', '更新财务ID为  88 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5602', '63', '保存财务明细', '127.0.0.1', '2017-10-24 22:48:47', '0', '保存财务明细对应主表编号为  88 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5603', '63', '更新单据', '127.0.0.1', '2017-10-24 23:03:17', '0', '更新单据ID为  96 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5604', '63', '更新单据', '127.0.0.1', '2017-10-24 23:03:34', '0', '更新单据ID为  96 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5605', '63', '登录系统', '127.0.0.1', '2017-10-24 23:39:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5606', '63', '登录系统', '127.0.0.1', '2017-10-24 23:47:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5607', '63', '登录系统', '127.0.0.1', '2017-10-24 23:48:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5608', '63', '登录系统', '127.0.0.1', '2017-10-25 00:07:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5609', '63', '登录系统', '127.0.0.1', '2017-10-25 00:15:04', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5610', '63', '登录系统', '127.0.0.1', '2017-10-25 00:21:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5611', '63', '登录系统', '127.0.0.1', '2017-10-25 20:49:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5612', '63', '登录系统', '127.0.0.1', '2017-10-25 21:43:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5613', '63', '登录系统', '127.0.0.1', '2017-10-25 21:57:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5614', '63', '登录系统', '127.0.0.1', '2017-10-25 22:02:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5615', '63', '登录系统', '127.0.0.1', '2017-10-25 22:18:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5616', '63', '登录系统', '127.0.0.1', '2017-10-25 23:58:44', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5617', '63', '登录系统', '127.0.0.1', '2017-10-26 00:02:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5618', '63', '登录系统', '127.0.0.1', '2017-10-26 22:24:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5619', '63', '登录系统', '127.0.0.1', '2017-10-26 22:47:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5620', '63', '登录系统', '127.0.0.1', '2017-10-26 23:25:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5621', '63', '登录系统', '127.0.0.1', '2017-10-26 23:41:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5622', '63', '登录系统', '127.0.0.1', '2017-10-26 23:49:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5623', '63', '登录系统', '127.0.0.1', '2017-10-27 00:19:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5624', '63', '登录系统', '127.0.0.1', '2017-10-27 00:46:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5625', '63', '登录系统', '127.0.0.1', '2017-10-27 00:52:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5626', '63', '登录系统', '127.0.0.1', '2017-10-27 22:19:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5627', '63', '更新功能', '127.0.0.1', '2017-10-27 22:22:20', '0', '更新功能ID为  235 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5628', '63', '登录系统', '127.0.0.1', '2017-10-27 22:45:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5629', '63', '删除财务', '127.0.0.1', '2017-10-27 22:48:33', '0', '删除财务ID为  81 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('5630', '63', '登录系统', '127.0.0.1', '2017-10-27 23:05:42', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5631', '63', '登录系统', '127.0.0.1', '2017-10-27 23:09:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5632', '63', '登录系统', '127.0.0.1', '2017-10-27 23:12:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5633', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:32:39', '0', '更新供应商ID为  2 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5634', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:41:02', '0', '更新供应商ID为  4 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5635', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:41:14', '0', '更新供应商ID为  46 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5636', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:41:20', '0', '更新供应商ID为  46 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5637', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:42:46', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5638', '63', '更新供应商', '127.0.0.1', '2017-10-27 23:42:58', '0', '更新供应商ID为  6 成功！', '更新供应商成功');
INSERT INTO `jsh_log` VALUES ('5639', '63', '登录系统', '127.0.0.1', '2017-10-27 23:51:59', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5640', '63', '登录系统', '127.0.0.1', '2017-10-29 19:24:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5641', '63', '登录系统', '127.0.0.1', '2017-10-29 19:50:24', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5642', '63', '登录系统', '127.0.0.1', '2017-10-29 19:53:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5643', '63', '登录系统', '127.0.0.1', '2017-10-29 20:34:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5644', '63', '登录系统', '127.0.0.1', '2017-10-29 20:46:31', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5645', '63', '登录系统', '127.0.0.1', '2017-10-29 20:48:56', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5646', '63', '更新单据', '127.0.0.1', '2017-10-29 21:10:40', '0', '更新单据ID为  29 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5647', '63', '保存仓管通明细', '127.0.0.1', '2017-10-29 21:10:41', '0', '保存仓管通明细对应主表编号为  29 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5648', '63', '更新单据', '127.0.0.1', '2017-10-29 21:11:05', '0', '更新单据ID为  29 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5649', '63', '更新单据', '127.0.0.1', '2017-10-29 21:11:27', '0', '更新单据ID为  29 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5650', '63', '增加功能', '127.0.0.1', '2017-10-29 21:38:18', '0', '增加功能名称为  供应商对账 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5651', '63', '更新功能', '127.0.0.1', '2017-10-29 21:38:33', '0', '更新功能ID为  235 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5652', '63', '更新UserBusiness', '127.0.0.1', '2017-10-29 21:40:09', '0', '更新UserBusiness的ID为  5 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5653', '63', '更新UserBusiness', '127.0.0.1', '2017-10-29 21:43:27', '0', '更新UserBusiness的ID为  27 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5654', '63', '登录系统', '127.0.0.1', '2017-10-29 22:28:15', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5655', '63', '登录系统', '127.0.0.1', '2017-10-29 22:53:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5656', '63', '增加单据', '127.0.0.1', '2017-10-29 23:30:47', '0', '增加单据编号为  CGRK201710290001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5657', '63', '保存仓管通明细', '127.0.0.1', '2017-10-29 23:30:48', '0', '保存仓管通明细对应主表编号为  97 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5658', '63', '增加单据', '127.0.0.1', '2017-10-29 23:32:07', '0', '增加单据编号为  CGRK201710290002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5659', '63', '保存仓管通明细', '127.0.0.1', '2017-10-29 23:32:08', '0', '保存仓管通明细对应主表编号为  98 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5660', '63', '增加单据', '127.0.0.1', '2017-10-29 23:33:45', '0', '增加单据编号为  CGRK201710290003 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5661', '63', '保存仓管通明细', '127.0.0.1', '2017-10-29 23:33:45', '0', '保存仓管通明细对应主表编号为  99 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5662', '63', '登录系统', '127.0.0.1', '2017-10-30 00:00:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5663', '63', '更新应用', '127.0.0.1', '2017-10-30 00:01:02', '0', '更新应用ID为  8 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5664', '63', '登录系统', '127.0.0.1', '2017-10-30 21:46:00', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5665', '63', '增加财务', '127.0.0.1', '2017-10-30 23:25:48', '0', '增加财务编号为  SK20171030232535 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5666', '63', '保存财务明细', '127.0.0.1', '2017-10-30 23:25:48', '0', '保存财务明细对应主表编号为  89 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5667', '63', '更新财务', '127.0.0.1', '2017-10-30 23:26:57', '0', '更新财务ID为  89 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5668', '63', '保存财务明细', '127.0.0.1', '2017-10-30 23:26:57', '0', '保存财务明细对应主表编号为  89 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5669', '63', '登录系统', '127.0.0.1', '2017-10-31 00:01:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5670', '63', '登录系统', '127.0.0.1', '2017-10-31 00:11:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5671', '63', '登录系统', '127.0.0.1', '2017-10-31 21:14:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5672', '63', '登录系统', '127.0.0.1', '2017-10-31 23:30:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5673', '63', '登录系统', '127.0.0.1', '2017-10-31 23:50:38', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5674', '63', '登录系统', '127.0.0.1', '2017-10-31 23:57:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5675', '63', '登录系统', '127.0.0.1', '2017-10-31 23:58:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5676', '63', '登录系统', '127.0.0.1', '2017-11-01 20:46:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5677', '63', '增加单据', '127.0.0.1', '2017-11-01 23:06:40', '0', '增加单据编号为  LPXS201711010001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5678', '63', '保存仓管通明细', '127.0.0.1', '2017-11-01 23:06:41', '0', '保存仓管通明细对应主表编号为  100 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5679', '63', '登录系统', '127.0.0.1', '2017-11-01 23:37:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5680', '63', '登录系统', '127.0.0.1', '2017-11-02 00:06:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5681', '63', '登录系统', '127.0.0.1', '2017-11-02 00:09:08', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5682', '63', '登录系统', '127.0.0.1', '2017-11-02 21:10:57', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5683', '63', '登录系统', '127.0.0.1', '2017-11-02 21:50:30', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5684', '63', '登录系统', '127.0.0.1', '2017-11-02 22:46:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5685', '63', '增加单据', '127.0.0.1', '2017-11-02 22:51:17', '0', '增加单据编号为  DBCK201711020001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5686', '63', '保存仓管通明细', '127.0.0.1', '2017-11-02 22:51:17', '0', '保存仓管通明细对应主表编号为  101 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5687', '63', '登录系统', '127.0.0.1', '2017-11-02 23:02:53', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5688', '63', '登录系统', '127.0.0.1', '2017-11-02 23:24:19', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5689', '63', '登录系统', '127.0.0.1', '2017-11-02 23:57:52', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5690', '63', '更新UserBusiness', '127.0.0.1', '2017-11-02 23:59:48', '0', '更新UserBusiness的ID为  18 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5691', '63', '更新UserBusiness', '127.0.0.1', '2017-11-03 00:07:32', '0', '更新UserBusiness的ID为  18 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5692', '63', '登录系统', '127.0.0.1', '2017-11-03 00:14:40', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5693', '63', '登录系统', '127.0.0.1', '2017-11-03 00:22:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5694', '63', '登录系统', '127.0.0.1', '2017-11-03 23:13:11', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5695', '63', '批量修改单据状态', '127.0.0.1', '2017-11-03 23:33:39', '0', '批量修改状态，单据ID为  98 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5696', '63', '登录系统', '127.0.0.1', '2017-11-05 17:51:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5697', '63', '登录系统', '127.0.0.1', '2017-11-05 19:24:27', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5698', '63', '退出系统', '127.0.0.1', '2017-11-05 19:42:30', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5699', '63', '登录系统', '127.0.0.1', '2017-11-05 19:42:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5700', '63', '更新单据', '127.0.0.1', '2017-11-05 21:38:01', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5701', '63', '更新单据', '127.0.0.1', '2017-11-05 21:38:40', '0', '更新单据ID为  65 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5702', '63', '登录系统', '127.0.0.1', '2017-11-05 21:47:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5703', '63', '登录系统', '127.0.0.1', '2017-11-05 22:00:16', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5704', '63', '登录系统', '127.0.0.1', '2017-11-05 22:56:58', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5705', '63', '登录系统', '127.0.0.1', '2017-11-05 23:15:35', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5706', '63', '登录系统', '127.0.0.1', '2017-11-05 23:19:20', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5707', '63', '登录系统', '127.0.0.1', '2017-11-05 23:25:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5708', '63', '登录系统', '127.0.0.1', '2017-11-06 20:24:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5709', '63', '增加单据', '127.0.0.1', '2017-11-06 20:38:46', '0', '增加单据编号为  LSCK201711060001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5710', '63', '保存仓管通明细', '127.0.0.1', '2017-11-06 20:38:46', '0', '保存仓管通明细对应主表编号为  102 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5711', '63', '更新单据', '127.0.0.1', '2017-11-06 20:39:00', '0', '更新单据ID为  102 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5712', '63', '登录系统', '127.0.0.1', '2017-11-07 21:00:48', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5713', '63', '增加单据', '127.0.0.1', '2017-11-07 21:07:05', '0', '增加单据编号为  CGRK201711070001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5714', '63', '保存仓管通明细', '127.0.0.1', '2017-11-07 21:07:06', '0', '保存仓管通明细对应主表编号为  103 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5715', '63', '增加单据', '127.0.0.1', '2017-11-07 21:07:40', '0', '增加单据编号为  CGRK201711070002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5716', '63', '保存仓管通明细', '127.0.0.1', '2017-11-07 21:07:40', '0', '保存仓管通明细对应主表编号为  104 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5717', '63', '增加单据', '127.0.0.1', '2017-11-07 21:08:48', '0', '增加单据编号为  XSCK201711070001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5718', '63', '保存仓管通明细', '127.0.0.1', '2017-11-07 21:08:48', '0', '保存仓管通明细对应主表编号为  105 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5719', '63', '增加单据', '127.0.0.1', '2017-11-07 21:09:20', '0', '增加单据编号为  XSCK201711070002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5720', '63', '保存仓管通明细', '127.0.0.1', '2017-11-07 21:09:20', '0', '保存仓管通明细对应主表编号为  106 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5721', '63', '更新单据', '127.0.0.1', '2017-11-19 23:09:26', '0', '更新单据ID为  82 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5722', '63', '更新单据', '127.0.0.1', '2017-11-19 23:10:00', '0', '更新单据ID为  90 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5723', '63', '更新单据', '127.0.0.1', '2017-11-19 23:11:11', '0', '更新单据ID为  70 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5724', '63', '更新单据', '127.0.0.1', '2017-11-19 23:12:07', '0', '更新单据ID为  19 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5725', '63', '增加财务', '127.0.0.1', '2017-11-19 23:15:02', '0', '增加财务编号为  SK20171119231440 成功！', '增加财务成功');
INSERT INTO `jsh_log` VALUES ('5726', '63', '保存财务明细', '127.0.0.1', '2017-11-19 23:15:02', '0', '保存财务明细对应主表编号为  90 成功！', '保存财务明细成功');
INSERT INTO `jsh_log` VALUES ('5727', '63', '增加单据', '127.0.0.1', '2017-12-03 22:38:36', '0', '增加单据编号为  CGRK201712030001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5728', '63', '保存仓管通明细', '127.0.0.1', '2017-12-03 22:38:36', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5729', '63', '更新单据', '127.0.0.1', '2017-12-03 22:39:55', '0', '更新单据ID为  107 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5730', '63', '保存仓管通明细', '127.0.0.1', '2017-12-03 22:39:55', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5731', '63', '更新单据', '127.0.0.1', '2017-12-03 22:40:21', '0', '更新单据ID为  107 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5732', '63', '增加单据', '127.0.0.1', '2017-12-03 22:40:57', '0', '增加单据编号为  sdfasdfa 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5733', '63', '保存仓管通明细', '127.0.0.1', '2017-12-03 22:40:57', '0', '保存仓管通明细对应主表编号为  108 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5734', '63', '增加单据', '127.0.0.1', '2017-12-03 22:41:38', '0', '增加单据编号为  CGRK201712030003 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5735', '63', '保存仓管通明细', '127.0.0.1', '2017-12-03 22:41:38', '0', '保存仓管通明细对应主表编号为  109 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5736', '63', '登录系统', '127.0.0.1', '2017-12-05 22:01:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5737', '63', '登录系统', '127.0.0.1', '2017-12-05 22:25:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5738', '63', '登录系统', '127.0.0.1', '2017-12-05 22:49:37', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5739', '63', '更新单据', '127.0.0.1', '2017-12-05 22:53:07', '0', '更新单据ID为  107 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5740', '63', '保存仓管通明细', '127.0.0.1', '2017-12-05 22:53:07', '0', '保存仓管通明细对应主表编号为  107 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5741', '63', '登录系统', '127.0.0.1', '2017-12-05 23:03:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5742', '63', '增加单据', '127.0.0.1', '2017-12-05 23:05:48', '0', '增加单据编号为  CGRK201712050001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5743', '63', '保存仓管通明细', '127.0.0.1', '2017-12-05 23:05:48', '0', '保存仓管通明细对应主表编号为  110 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5744', '63', '登录系统', '127.0.0.1', '2017-12-05 23:08:46', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5745', '63', '登录系统', '127.0.0.1', '2017-12-05 23:11:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5746', '63', '增加单据', '127.0.0.1', '2017-12-05 23:12:53', '0', '增加单据编号为  CGRK201712050002 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5747', '63', '保存仓管通明细', '127.0.0.1', '2017-12-05 23:12:53', '0', '保存仓管通明细对应主表编号为  111 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5748', '63', '登录系统', '127.0.0.1', '2017-12-07 23:08:01', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5749', '63', '更新单据', '127.0.0.1', '2017-12-07 23:09:21', '0', '更新单据ID为  111 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5750', '63', '保存仓管通明细', '127.0.0.1', '2017-12-07 23:09:21', '0', '保存仓管通明细对应主表编号为  111 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5751', '63', '删除财务', '127.0.0.1', '2017-12-07 23:12:23', '0', '删除财务ID为  80 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('5752', '63', '删除财务', '127.0.0.1', '2017-12-07 23:12:52', '0', '删除财务ID为  75 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('5753', '63', '删除财务', '127.0.0.1', '2017-12-07 23:12:55', '0', '删除财务ID为  72 成功！', '删除财务成功');
INSERT INTO `jsh_log` VALUES ('5754', '63', '登录系统', '127.0.0.1', '2017-12-07 23:39:54', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5755', '63', '登录系统', '127.0.0.1', '2017-12-10 18:40:21', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5756', '63', '增加单据', '127.0.0.1', '2017-12-10 21:07:46', '0', '增加单据编号为  XSCK201712100001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5757', '63', '保存仓管通明细', '127.0.0.1', '2017-12-10 21:07:46', '0', '保存仓管通明细对应主表编号为  112 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5758', '63', '批量修改单据状态', '127.0.0.1', '2017-12-10 21:08:51', '0', '批量修改状态，单据ID为  112 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5759', '63', '批量修改单据状态', '127.0.0.1', '2017-12-10 21:08:54', '0', '批量修改状态，单据ID为  112 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5760', '63', '登录系统', '127.0.0.1', '2017-12-10 22:11:13', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5761', '63', '登录系统', '127.0.0.1', '2017-12-10 22:33:26', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5762', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:33:58', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5763', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:34:11', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5764', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:34:26', '0', '更新账户ID10为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5765', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:40:10', '0', '更新账户ID12为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5766', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:40:15', '0', '更新账户ID4为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5767', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:40:21', '0', '更新账户ID12为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5768', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:40:29', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5769', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:41:22', '0', '更新账户ID10为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5770', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:41:26', '0', '更新账户ID9为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5771', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:41:49', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5772', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:41:52', '0', '更新账户ID12为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5773', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:42:00', '0', '更新账户ID4为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5774', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:42:57', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5775', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:43:18', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5776', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:43:22', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5777', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:52:37', '0', '更新账户ID11为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5778', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:52:48', '0', '更新账户ID12为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5779', '63', '更新默认账户', '127.0.0.1', '2017-12-10 22:53:44', '0', '更新账户ID9为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5780', '63', '更新默认账户', '127.0.0.1', '2017-12-10 23:07:40', '0', '更新账户ID10为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5781', '63', '更新默认账户', '127.0.0.1', '2017-12-10 23:08:10', '0', '更新账户ID12为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5782', '63', '更新默认账户', '127.0.0.1', '2017-12-10 23:08:45', '0', '更新账户ID9为默认账户成功！', '更新默认账户成功');
INSERT INTO `jsh_log` VALUES ('5783', '63', '增加单据', '127.0.0.1', '2017-12-10 23:11:20', '0', '增加单据编号为  CGRK201712100001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5784', '63', '保存仓管通明细', '127.0.0.1', '2017-12-10 23:11:20', '0', '保存仓管通明细对应主表编号为  113 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5785', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-02-25 22:05:49', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5786', '63', '更新应用', '0:0:0:0:0:0:0:1', '2018-02-25 22:11:44', '0', '更新应用ID为  24 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5787', '63', '更新应用', '0:0:0:0:0:0:0:1', '2018-02-25 22:11:53', '0', '更新应用ID为  25 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5788', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:13:30', '0', '更新功能ID为  222 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5789', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:13:48', '0', '更新功能ID为  223 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5790', '63', '增加应用', '0:0:0:0:0:0:0:1', '2018-02-25 22:17:45', '0', '增加应用名称为  仓库管理 成功！', '增加应用成功');
INSERT INTO `jsh_log` VALUES ('5791', '63', '更新应用', '0:0:0:0:0:0:0:1', '2018-02-25 22:22:05', '0', '更新应用ID为  27 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5792', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-02-25 22:26:03', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5793', '63', '增加功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:28:48', '0', '增加功能名称为  仓库管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5794', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:30:32', '0', '更新功能ID为  32 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5795', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:31:17', '0', '更新功能ID为  38 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5796', '63', '增加功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:32:53', '0', '增加功能名称为  仓库管理 成功！', '增加功能成功');
INSERT INTO `jsh_log` VALUES ('5797', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:35:21', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5798', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:35:35', '0', '更新功能ID为  201 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5799', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:36:24', '0', '更新功能ID为  202 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5800', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:37:14', '0', '更新功能ID为  40 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5801', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:38:53', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5802', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:39:16', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5803', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:40:15', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5804', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:40:44', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5805', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:41:10', '0', '更新功能ID为  199 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5806', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:45:02', '0', '更新功能ID为  41 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5807', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:45:13', '0', '更新功能ID为  200 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5808', '63', '更新UserBusiness', '0:0:0:0:0:0:0:1', '2018-02-25 22:46:05', '0', '更新UserBusiness的ID为  1 成功！', '更新UserBusiness成功');
INSERT INTO `jsh_log` VALUES ('5809', '63', '批量修改单据状态', '0:0:0:0:0:0:0:1', '2018-02-25 22:47:10', '0', '批量修改状态，单据ID为  72 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5810', '63', '批量修改单据状态', '0:0:0:0:0:0:0:1', '2018-02-25 22:47:14', '0', '批量修改状态，单据ID为  72 成功！', '批量修改单据状态成功');
INSERT INTO `jsh_log` VALUES ('5811', '63', '增加单据', '0:0:0:0:0:0:0:1', '2018-02-25 22:48:33', '0', '增加单据编号为  CGRK201802250001 成功！', '增加单据成功');
INSERT INTO `jsh_log` VALUES ('5812', '63', '保存仓管通明细', '0:0:0:0:0:0:0:1', '2018-02-25 22:48:33', '0', '保存仓管通明细对应主表编号为  114 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5813', '63', '删除单据', '0:0:0:0:0:0:0:1', '2018-02-25 22:48:39', '0', '删除单据ID为  114 成功！', '删除单据成功');
INSERT INTO `jsh_log` VALUES ('5814', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:51:46', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5815', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:52:05', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5816', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:52:24', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5817', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:52:32', '0', '更新功能ID为  233 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5818', '63', '更新功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:52:47', '0', '更新功能ID为  232 成功！', '更新功能成功');
INSERT INTO `jsh_log` VALUES ('5819', '63', '删除功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:53:25', '0', '删除功能ID为  231 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('5820', '63', '删除功能', '0:0:0:0:0:0:0:1', '2018-02-25 22:53:34', '0', '删除功能ID为  230 成功！', '删除功能成功');
INSERT INTO `jsh_log` VALUES ('5821', '63', '删除应用', '0:0:0:0:0:0:0:1', '2018-02-25 22:53:46', '0', '删除应用ID为  8 成功！', '删除应用成功');
INSERT INTO `jsh_log` VALUES ('5822', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-02-25 22:55:14', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5823', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-02-25 22:56:34', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5824', '63', '登录系统', '127.0.0.1', '2018-03-04 14:09:45', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5825', '63', '退出系统', '127.0.0.1', '2018-03-04 14:10:04', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5826', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-04 14:15:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5827', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-04 14:18:29', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5828', '63', '退出系统', '0:0:0:0:0:0:0:1', '2018-03-04 14:19:28', '0', '管理用户：jsh 退出系统', 'jsh 退出系统');
INSERT INTO `jsh_log` VALUES ('5829', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-04 15:35:10', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5830', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-08 21:12:02', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5831', '63', '更新单据', '0:0:0:0:0:0:0:1', '2018-03-08 21:14:17', '0', '更新单据ID为  113 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5832', '63', '保存仓管通明细', '0:0:0:0:0:0:0:1', '2018-03-08 21:14:17', '0', '保存仓管通明细对应主表编号为  113 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5833', '63', '更新应用', '0:0:0:0:0:0:0:1', '2018-03-08 21:39:57', '0', '更新应用ID为  22 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5834', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-08 22:49:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5835', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 18:17:17', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5836', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 20:04:47', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5837', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 20:40:51', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5838', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 21:48:09', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5839', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 21:52:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5840', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-11 21:56:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5841', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-12 21:36:22', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5842', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-13 22:31:28', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5843', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-14 21:34:32', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5844', '63', '更新单据', '0:0:0:0:0:0:0:1', '2018-03-14 23:53:43', '0', '更新单据ID为  106 成功！', '更新单据成功');
INSERT INTO `jsh_log` VALUES ('5845', '63', '保存仓管通明细', '0:0:0:0:0:0:0:1', '2018-03-14 23:53:43', '0', '保存仓管通明细对应主表编号为  106 成功！', '保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('5846', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-15 21:35:23', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5847', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-15 22:51:18', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5848', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-03-17 21:25:12', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');
INSERT INTO `jsh_log` VALUES ('5849', '63', '更新财务', '0:0:0:0:0:0:0:1', '2018-03-17 23:24:24', '0', '更新财务ID为  85 成功！', '更新财务成功');
INSERT INTO `jsh_log` VALUES ('5850', '63', '更新应用', '0:0:0:0:0:0:0:1', '2018-03-17 23:36:59', '0', '更新应用ID为  7 成功！', '更新应用成功');
INSERT INTO `jsh_log` VALUES ('5851', '63', '登录系统', '0:0:0:0:0:0:0:1', '2018-10-17 23:02:55', '0', '管理用户：jsh 登录系统', 'jsh 登录系统');

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
-- ----------------------------
insert into tbl_sequence (seq_name, min_value, max_value, current_val, increment_val,remark) values ('depot_number_seq', 1, 999999999999999999, 1, 1,'单据编号sequence');
-- ----------------------------
-- 创建function _nextval() 用于获取当前序列号
-- ----------------------------
DELIMITER //
create function _nextval(name varchar(50))
returns long
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
end;
//
DELIMITER ;

