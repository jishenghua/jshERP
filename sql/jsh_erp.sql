/*
MySQL Backup
Source Server Version: 5.1.54
Source Database: jsh_erp
Date: 2016-11-27 13:25:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `jsh_account`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_account`;
CREATE TABLE `jsh_account` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `SerialNo` varchar(50) DEFAULT NULL COMMENT '编号',
  `InitialAmount` double DEFAULT NULL COMMENT '期初金额',
  `CurrentAmount` double DEFAULT NULL COMMENT '当前余额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_app`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_app`;
CREATE TABLE `jsh_app` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Number` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Icon` varchar(50) DEFAULT NULL,
  `URL` varchar(50) DEFAULT NULL,
  `Width` varchar(50) DEFAULT NULL,
  `Height` varchar(50) DEFAULT NULL,
  `ReSize` bit(1) DEFAULT NULL,
  `OpenMax` bit(1) DEFAULT NULL,
  `Flash` bit(1) DEFAULT NULL,
  `ZL` varchar(50) DEFAULT NULL,
  `Sort` varchar(50) DEFAULT NULL,
  `Remark` varchar(200) DEFAULT NULL,
  `Enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_asset`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_asset`;
CREATE TABLE `jsh_asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assetnameID` bigint(20) NOT NULL,
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `labels` varchar(255) DEFAULT NULL COMMENT '标签：以空格为分隔符',
  `status` smallint(6) DEFAULT NULL COMMENT '资产的状态：0==在库，1==在用，2==消费',
  `userID` bigint(20) DEFAULT NULL,
  `price` double DEFAULT NULL COMMENT '购买价格',
  `purchasedate` datetime DEFAULT NULL COMMENT '购买日期',
  `periodofvalidity` datetime DEFAULT NULL COMMENT '有效日期',
  `warrantydate` datetime DEFAULT NULL COMMENT '保修日期',
  `assetnum` varchar(255) DEFAULT NULL COMMENT '资产编号',
  `serialnum` varchar(255) DEFAULT NULL COMMENT '资产序列号',
  `supplier` bigint(20) NOT NULL,
  `description` longtext COMMENT '描述信息',
  `addMonth` longtext COMMENT '资产添加时间，统计报表使用',
  `createtime` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `updator` bigint(20) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_assetcategory`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_assetcategory`;
CREATE TABLE `jsh_assetcategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assetname` varchar(255) NOT NULL COMMENT '资产类型名称',
  `isystem` tinyint(4) NOT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `description` varchar(500) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_assetname`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_assetname`;
CREATE TABLE `jsh_assetname` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assetname` varchar(255) NOT NULL COMMENT '资产名称',
  `assetcategoryID` bigint(20) NOT NULL,
  `isystem` smallint(6) NOT NULL COMMENT '是否系统自带 0==系统 1==非系统',
  `description` longtext COMMENT '描述信息',
  `isconsumables` smallint(6) DEFAULT NULL COMMENT '是否为耗材 0==否 1==是 耗材状态只能是消费',
  PRIMARY KEY (`id`),
  KEY `FKA4ADCCF866BC8AD3` (`assetcategoryID`),
  CONSTRAINT `FKA4ADCCF866BC8AD3` FOREIGN KEY (`assetcategoryID`) REFERENCES `jsh_assetcategory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_building`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_building`;
CREATE TABLE `jsh_building` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ProjectId` bigint(20) NOT NULL,
  `Name` varchar(20) DEFAULT NULL COMMENT '名称',
  `Remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `Enabled` bit(1) DEFAULT NULL COMMENT '启用',
  PRIMARY KEY (`Id`),
  KEY `FK3FFEB42888F9A` (`ProjectId`),
  CONSTRAINT `FK3FFEB42888F9A` FOREIGN KEY (`ProjectId`) REFERENCES `jsh_depot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_depot`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depot`;
CREATE TABLE `jsh_depot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '仓库名称',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_depothead`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depothead`;
CREATE TABLE `jsh_depothead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(出库/入库)',
  `SubType` varchar(50) DEFAULT NULL COMMENT '出入库分类',
  `ProjectId` bigint(20) NOT NULL COMMENT '项目Id',
  `Number` varchar(50) DEFAULT NULL COMMENT '票据号',
  `OperPersonName` varchar(50) DEFAULT NULL COMMENT '操作员名字',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `OperTime` datetime DEFAULT NULL COMMENT '出入库时间',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '供应商Id',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '采购/领料-经手人Id',
  `WareHousePersonId` bigint(20) NOT NULL COMMENT '仓管员-经手人Id',
  `SettlementWay` varchar(50) DEFAULT NULL COMMENT '现金/记账',
  `BuildingId` bigint(20) DEFAULT NULL COMMENT '单元Id',
  `AllocationProjectId` bigint(20) DEFAULT NULL COMMENT '调拨时，对方项目Id',
  `Remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `State` varchar(50) DEFAULT NULL COMMENT '草稿/已生效/废弃/待审核/未通过',
  `ReAuditPersonName` varchar(50) DEFAULT NULL COMMENT '撤审人',
  `Reason` varchar(100) DEFAULT NULL COMMENT '撤审原因',
  PRIMARY KEY (`Id`),
  KEY `FK2A80F214CA633ABA` (`AllocationProjectId`),
  KEY `FK2A80F214C4170B37` (`HandsPersonId`),
  KEY `FK2A80F214B610FC06` (`OrganId`),
  KEY `FK2A80F2148E0FA916` (`WareHousePersonId`),
  KEY `FK2A80F2142888F9A` (`ProjectId`),
  KEY `FK2A80F214F039F8D1` (`BuildingId`),
  CONSTRAINT `jsh_depothead_ibfk_1` FOREIGN KEY (`ProjectId`) REFERENCES `jsh_depot` (`id`),
  CONSTRAINT `jsh_depothead_ibfk_2` FOREIGN KEY (`WareHousePersonId`) REFERENCES `jsh_person` (`Id`),
  CONSTRAINT `jsh_depothead_ibfk_3` FOREIGN KEY (`OrganId`) REFERENCES `jsh_supplier` (`id`),
  CONSTRAINT `jsh_depothead_ibfk_4` FOREIGN KEY (`HandsPersonId`) REFERENCES `jsh_person` (`Id`),
  CONSTRAINT `jsh_depothead_ibfk_5` FOREIGN KEY (`AllocationProjectId`) REFERENCES `jsh_depot` (`id`),
  CONSTRAINT `jsh_depothead_ibfk_6` FOREIGN KEY (`BuildingId`) REFERENCES `jsh_building` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_depotitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_depotitem`;
CREATE TABLE `jsh_depotitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `MaterialId` bigint(20) NOT NULL COMMENT '材料Id',
  `OperNumber` double DEFAULT NULL COMMENT '数量',
  `UnitPrice` double DEFAULT NULL COMMENT '单价',
  `Incidentals` double DEFAULT NULL COMMENT '运杂费',
  `Remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `Img` varchar(50) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`Id`),
  KEY `FK2A819F475D61CCF7` (`MaterialId`),
  KEY `FK2A819F474BB6190E` (`HeaderId`),
  CONSTRAINT `jsh_depotitem_ibfk_1` FOREIGN KEY (`HeaderId`) REFERENCES `jsh_depothead` (`Id`) ON DELETE CASCADE,
  CONSTRAINT `jsh_depotitem_ibfk_2` FOREIGN KEY (`MaterialId`) REFERENCES `jsh_material` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_functions`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_functions`;
CREATE TABLE `jsh_functions` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Number` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `PNumber` varchar(50) DEFAULT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `State` bit(1) DEFAULT NULL,
  `Sort` varchar(50) DEFAULT NULL,
  `Enabled` bit(1) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_inoutitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_inoutitem`;
CREATE TABLE `jsh_inoutitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_log`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_log`;
CREATE TABLE `jsh_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=1731 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_material`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_material`;
CREATE TABLE `jsh_material` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CategoryId` bigint(20) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `Model` varchar(50) DEFAULT NULL COMMENT '型号',
  `Color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `Unit` varchar(50) DEFAULT NULL COMMENT '单位',
  `Remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`),
  KEY `FK675951272AB6672C` (`CategoryId`),
  CONSTRAINT `FK675951272AB6672C` FOREIGN KEY (`CategoryId`) REFERENCES `jsh_materialcategory` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=499 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_materialcategory`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_materialcategory`;
CREATE TABLE `jsh_materialcategory` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL COMMENT '名称',
  `CategoryLevel` smallint(6) DEFAULT NULL COMMENT '等级',
  `ParentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK3EE7F725237A77D8` (`ParentId`),
  CONSTRAINT `FK3EE7F725237A77D8` FOREIGN KEY (`ParentId`) REFERENCES `jsh_materialcategory` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_person`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_person`;
CREATE TABLE `jsh_person` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ProjectId` bigint(20) NOT NULL,
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`Id`),
  KEY `FK5AF487552888F9A` (`ProjectId`),
  CONSTRAINT `FK5AF487552888F9A` FOREIGN KEY (`ProjectId`) REFERENCES `jsh_depot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_role`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_role`;
CREATE TABLE `jsh_role` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_supplier`;
CREATE TABLE `jsh_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier` varchar(255) NOT NULL COMMENT '供应商名称',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `phonenum` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `description` varchar(500) DEFAULT NULL,
  `isystem` tinyint(4) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `enabled` bit(1) DEFAULT NULL COMMENT '启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_user`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_user`;
CREATE TABLE `jsh_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户姓名--例如张三',
  `loginame` varchar(255) DEFAULT NULL COMMENT '登录用户名--可能为空',
  `password` varchar(50) DEFAULT NULL COMMENT '登陆密码',
  `position` varchar(200) DEFAULT NULL COMMENT '职位',
  `department` varchar(255) DEFAULT NULL COMMENT '所属部门',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `phonenum` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `ismanager` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为管理者 0==管理者 1==员工',
  `isystem` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否系统自带数据 ',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态',
  `description` varchar(500) DEFAULT NULL COMMENT '用户描述信息',
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_userbusiness`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_userbusiness`;
CREATE TABLE `jsh_userbusiness` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Type` varchar(50) DEFAULT NULL,
  `KeyId` varchar(50) DEFAULT NULL,
  `Value` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_visitaccount`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_visitaccount`;
CREATE TABLE `jsh_visitaccount` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ProjectId` bigint(20) NOT NULL,
  `LouHao` varchar(50) DEFAULT NULL,
  `HuHao` varchar(50) DEFAULT NULL,
  `HuiFang` varchar(50) DEFAULT NULL,
  `LuoShi` varchar(50) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Tel` varchar(50) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FKFF4AAE822888F9A` (`ProjectId`),
  CONSTRAINT `FKFF4AAE822888F9A` FOREIGN KEY (`ProjectId`) REFERENCES `jsh_depot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  View definition for `jsh_view_in_out_stock`
-- ----------------------------
DROP VIEW IF EXISTS `jsh_view_in_out_stock`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `jsh_view_in_out_stock` AS select `jsh_depotitem`.`Id` AS `Id`,`jsh_depotitem`.`HeaderId` AS `HeaderId`,`jsh_depotitem`.`MaterialId` AS `MaterialId`,`jsh_depotitem`.`OperNumber` AS `OperNumber`,`jsh_depotitem`.`UnitPrice` AS `UnitPrice`,`jsh_depotitem`.`Incidentals` AS `Incidentals`,`jsh_depotitem`.`Remark` AS `Remark`,`jsh_depotitem`.`Img` AS `Img` from `jsh_depotitem` group by `jsh_depotitem`.`MaterialId`;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('4','aa','bb','12','34','66666'), ('9','4312412','4123411','423','41244','');
INSERT INTO `jsh_app` VALUES ('1','','企业邮箱','app','0000000001.png','../EmailManage/Email','600','400','\0','\0','\0','desk','010','','\0'), ('3','00','系统管理','app','0000000004.png','','1024','600','','\0','\0','desk','198','',''), ('6','','个人信息','app','0000000005.png','../user/password.jsp','600','400','\0','\0','\0','dock','200','',''), ('7','01','基础数据','app','0000000006.png','','1024','600','','\0','\0','desk','100','',''), ('8','02','进销存','app','0000000007.png','','1024','600','','\0','\0','desk','030','',''), ('20','13','公告管理','app','0000000020.png',NULL,'1024','600','','\0','\0','desk','125',NULL,'\0'), ('21','','今日留言','app','0000000021.png','../phone/msg','1024','600','','\0','\0','dock','000','','\0'), ('22','11','快捷审批','app','0000000022.png',NULL,'1024','600','','\0','\0','desk','210',NULL,'\0');
INSERT INTO `jsh_asset` VALUES ('1','27','weizhi','','0',NULL,'11','2016-10-22 00:00:00','2016-10-21 00:00:00','2016-11-03 00:00:00','1231241','123124123','2','','2016-10','2016-10-22 20:04:48','63','2016-10-22 20:04:48','63');
INSERT INTO `jsh_assetcategory` VALUES ('14','递延资产','1','递延资产'), ('15','无形资产','1','无形资产'), ('16','长期投资','1','长期投资'), ('17','固定资产','1','固定资产'), ('18','流动资产','1','流动资产');
INSERT INTO `jsh_assetname` VALUES ('1','联想Y450','17','1','','1'), ('2','惠普打印机','15','1','','0'), ('12','乐萌水杯','16','1','','1'), ('13','机顶盒','17','1','机顶盒','0'), ('14','TCL电视','17','1','','1'), ('15','手机','17','1','','1'), ('16','硬盘','16','1','','0'), ('17','毛笔','17','1','','0'), ('18','杯子','17','1','','0'), ('19','建造师证书','15','1','','0'), ('20','算量软件','14','1','','1'), ('21','cad软件','15','1','','0'), ('22','办公桌','17','1','','0'), ('23','笔记本','17','1','笔记本','1'), ('24','打印机','17','1','打印机','0'), ('25','电脑','17','1','电脑','0'), ('26','电动车','16','1','电动车','0'), ('27','电源线','17','1','电源线','0');
INSERT INTO `jsh_depot` VALUES ('1','上海花边店','1','');
INSERT INTO `jsh_depothead` VALUES ('29','入库','采购','1','1234','李四','2016-11-08 22:14:37','2016-11-08 00:00:00','1','2','1','记账',NULL,NULL,'','草稿',NULL,NULL), ('30','入库','采购','1','1235','李四','2016-11-08 22:15:16','2016-11-08 00:00:00','1','2','1','记账',NULL,NULL,'','草稿',NULL,NULL), ('31','出库','销售','1','123A','李四','2016-11-08 22:16:11','2016-11-08 00:00:00','2',NULL,'1','',NULL,NULL,'','草稿',NULL,NULL), ('32','入库','采购','1','ww123','季圣华','2016-11-25 22:14:46','2016-11-25 00:00:00','1','2','1','现金',NULL,NULL,'','草稿',NULL,NULL);
INSERT INTO `jsh_depotitem` VALUES ('1014','29','485','300','0.4',NULL,'',''), ('1015','29','487','200','0.2',NULL,'',''), ('1016','30','498','432','0.5',NULL,'',''), ('1017','31','485','100','0.6',NULL,'',''), ('1018','32','487','1','0.9',NULL,'','');
INSERT INTO `jsh_functions` VALUES ('1','00','系统管理','0','','','0010','','电脑版'), ('2','01','基础数据','0','','','0020','','电脑版'), ('3','02','物资管理','0','','','0030','','电脑版'), ('10','09','留言管理','0','','\0','0100','','手机版'), ('11','0001','系统管理','00','','\0','0110','','电脑版'), ('12','000101','应用管理','0001','../manage/app.jsp','\0','0132','','电脑版'), ('13','000102','角色管理','0001','../manage/role.jsp','\0','0130','','电脑版'), ('14','000103','用户管理','0001','../user/user.jsp','\0','0140','','电脑版'), ('15','000104','日志管理','0001','../log/operatelog.jsp','\0','0160','','电脑版'), ('16','000105','功能管理','0001','../manage/functions.jsp','\0','0135','','电脑版'), ('21','0101','物料管理','01','','\0','0220','','电脑版'), ('22','010101','物料类别管理','0101','../materials/materialcategory.jsp','\0','0230','','电脑版'), ('23','010102','物料档案管理','0101','../materials/material.jsp','\0','0240','','电脑版'), ('24','0102','公司数据管理','01','','\0','0250','','电脑版'), ('25','010201','单位信息','0102','../manage/vendor.jsp','\0','0260','','电脑版'), ('26','010202','部门管理','0102','../manage/depot.jsp','\0','0270','','电脑版'), ('30','0201','其他管理','02','','\0','0310','','电脑版'), ('31','020101','经手人管理','0201','../materials/person.jsp','\0','0312','','电脑版'), ('32','0202','入库管理','02','','\0','0330','','电脑版'), ('33','020201','采购入库','0202','../materials/purchase_in_list.jsp','\0','0340','','电脑版'), ('34','020202','甲供入库','0202','../materials/supply_in_list.jsp','\0','0350','','电脑版'), ('35','020203','租赁入库','0202','../materials/lease_in_list.jsp','\0','0360','','电脑版'), ('36','020204','回收入库','0202','../materials/recover_in_list.jsp','\0','0370','','电脑版'), ('37','020205','调拨入库','0202','../materials/allocation_in_list.jsp','\0','0380','','电脑版'), ('38','0203','出库管理','02','','\0','0390','','电脑版'), ('39','020301','领用出库','0203','../materials/consuming_out_list.jsp','\0','0400','','电脑版'), ('40','020302','调拨出库','0203','../materials/allocation_out_list.jsp','\0','0410','','电脑版'), ('41','020303','销售出库','0203','../materials/sale_out_list.jsp','\0','0420','','电脑版'), ('42','020304','退还出库','0203','../materials/return_out_list.jsp','\0','0430','','电脑版'), ('43','020305','报废出库','0203','../materials/other_out_list.jsp','\0','0430','','电脑版'), ('44','0204','财务管理','02','','\0','0450','','电脑版'), ('45','020401','单据审核','0204','','','0460','','电脑版'), ('46','02040101','采购入库审核','020401','../materials/audit_purchase_in_list.jsp','\0','0470','','电脑版'), ('47','02040102','甲供入库审核','020401','../materials/audit_supply_in_list.jsp','\0','0480','','电脑版'), ('48','02040103','租赁入库审核','020401','../materials/audit_lease_in_list.jsp','\0','0490','','电脑版'), ('49','02040104','调拨出库审核','020401','../materials/audit_allocation_out_list.jsp','\0','0500','','电脑版'), ('50','02040105','领用出库审核','020401','../materials/audit_consuming_out_list.jsp','\0','0510','','电脑版'), ('51','02040106','退还出库审核','020401','../materials/audit_return_out_list.jsp','\0','0520','','电脑版'), ('52','020402','采购定价','0204','../materials/fix_purchase_in_list.jsp','\0','0530','','电脑版'), ('53','020403','租赁定价','0204','../materials/fix_lease_in_list.jsp','\0','0540','','电脑版'), ('54','020404','调拨定价','0204','../materials/fix_allocation_out_list.jsp','\0','0550','','电脑版'), ('55','020405','销售定价','0204','../materials/fix_sale_out_list.jsp','\0','0560','','电脑版'), ('58','0205','报表中心','02','','\0','0590','','电脑版'), ('59','020501','项目进销存报表','0205','../reports/in_out_stock_report.jsp','\0','0600','','电脑版'), ('193','02040107','销售出库审核','020401','../materials/audit_sale_out_list.jsp','\0','0525','','电脑版'), ('194','010204','收支项目','0102','../manage/inOutItem.jsp','\0','0282','','电脑版'), ('195','010205','结算账户','0102','../manage/account.jsp','\0','0283','','电脑版');
INSERT INTO `jsh_inoutitem` VALUES ('1','山山水水','支出','yyyy'), ('5','啊啊啊','收入','ttttttt'), ('7','44454','收入','33333');
INSERT INTO `jsh_log` VALUES ('1722','63','登录系统','192.168.1.104','2016-11-27 13:17:17','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1723','63','登录系统','192.168.1.104','2016-11-27 13:17:30','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1724','63','退出系统','192.168.1.104','2016-11-27 13:17:48','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1725','65','登录系统','192.168.1.104','2016-11-27 13:17:52','0','管理用户：ls 登录系统','ls 登录系统'), ('1726','65','退出系统','192.168.1.104','2016-11-27 13:18:18','0','管理用户：ls 退出系统','ls 退出系统'), ('1727','63','登录系统','192.168.1.104','2016-11-27 13:18:22','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1728','63','更新UserBusiness','192.168.1.104','2016-11-27 13:18:44','0','更新UserBusiness的ID为  6 成功！','更新UserBusiness成功'), ('1729','63','退出系统','192.168.1.104','2016-11-27 13:18:48','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1730','65','登录系统','192.168.1.104','2016-11-27 13:18:53','0','管理用户：ls 登录系统','ls 登录系统');
INSERT INTO `jsh_material` VALUES ('485','1','棉线','A21-4321','米色','码',''), ('487','1','网布','12343','红色','码',''), ('498','2','蕾丝','B123','蓝色','码','');
INSERT INTO `jsh_materialcategory` VALUES ('1','根目录','1','1'), ('2','花边分类','1','1');
INSERT INTO `jsh_person` VALUES ('1','1','仓管员','张三'), ('2','1','采购人','李四');
INSERT INTO `jsh_role` VALUES ('4','管理员'), ('5','仓管员');
INSERT INTO `jsh_supplier` VALUES ('1','上海某某花边工厂','','','','','1','供应商','\0'), ('2','客户AAAA','','','','','1','客户','\0');
INSERT INTO `jsh_user` VALUES ('63','季圣华','jsh','e10adc3949ba59abbe56e057f20f883e','','','','','1','1','-1','',NULL), ('64','张三','zs','e10adc3949ba59abbe56e057f20f883e','','销售','','','1','1',NULL,'',NULL), ('65','李四','ls','e10adc3949ba59abbe56e057f20f883e','','销售','','','1','1',NULL,'',NULL);
INSERT INTO `jsh_userbusiness` VALUES ('1','RoleAPP','4','[21][1][8][11][10][19][16][15][12][7][17][20][18][3][6][22][23][24][25]'), ('2','RoleAPP','5','[8][7][3][6]'), ('3','RoleAPP','6','[21][1][8]'), ('4','RoleAPP','7','[21][1][8][11]'), ('5','RoleFunctions','4','[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][194][195][31][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][193][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]'), ('6','RoleFunctions','5','[22][23][25][26][194][195][31][33][41][46][193][52][55][59]'), ('7','RoleFunctions','6','[168][13][12][16][14][15][189][18][19]'), ('8','RoleAPP','8','[21][1][8][11][10]'), ('9','RoleFunctions','7','[168][13][12][16][14][15][189][18][19][132]'), ('10','RoleFunctions','8','[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187]'), ('11','RoleFunctions','9','[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]'), ('12','UserRole','1','[5]'), ('13','UserRole','2','[6][7]'), ('14','UserDepot','2','[1][2][6][7]'), ('15','UserDepot','1','[1][2][5][6][7][10][12][14][15][17]'), ('16','UserRole','63','[4]'), ('17','RoleFunctions','13','[46][47][48][49]'), ('18','UserDepot','63','[1][6][45][46][50][51]'), ('19','UserDepot','5','[6][45][46][50]'), ('20','UserRole','5','[5]'), ('21','UserRole','64','[5]'), ('22','UserDepot','64','[1]'), ('23','UserRole','65','[5]'), ('24','UserDepot','65','[1]');
