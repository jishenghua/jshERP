/*
MySQL Backup
Source Server Version: 5.1.54
Source Database: jsh_erp
Date: 2016-12-24 23:56:10
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
--  Table structure for `jsh_accounthead`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accounthead`;
CREATE TABLE `jsh_accounthead` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Type` varchar(50) DEFAULT NULL COMMENT '类型(支出/收入/收款/付款/转账)',
  `OrganId` bigint(20) DEFAULT NULL COMMENT '单位Id(收款/付款单位)',
  `HandsPersonId` bigint(20) DEFAULT NULL COMMENT '经手人Id',
  `ChangeAmount` double DEFAULT NULL COMMENT '变动金额(优惠/收款/付款/实付)',
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `jsh_accountitem`
-- ----------------------------
DROP TABLE IF EXISTS `jsh_accountitem`;
CREATE TABLE `jsh_accountitem` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `HeaderId` bigint(20) NOT NULL COMMENT '表头Id',
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `InOutItemId` bigint(20) DEFAULT NULL COMMENT '收支项目Id',
  `EachAmount` double DEFAULT NULL COMMENT '单项金额',
  `Remark` varchar(100) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`Id`),
  KEY `FK9F4CBAC0AAE50527` (`AccountId`),
  KEY `FK9F4CBAC0C5FE6007` (`HeaderId`),
  KEY `FK9F4CBAC0D203EDC5` (`InOutItemId`),
  CONSTRAINT `FK9F4CBAC0AAE50527` FOREIGN KEY (`AccountId`) REFERENCES `jsh_account` (`Id`),
  CONSTRAINT `FK9F4CBAC0C5FE6007` FOREIGN KEY (`HeaderId`) REFERENCES `jsh_accounthead` (`Id`) ON DELETE CASCADE,
  CONSTRAINT `FK9F4CBAC0D203EDC5` FOREIGN KEY (`InOutItemId`) REFERENCES `jsh_inoutitem` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
  `AccountId` bigint(20) DEFAULT NULL COMMENT '账户Id',
  `ChangeAmount` double DEFAULT NULL COMMENT '变动金额(收款/付款)',
  `AllocationProjectId` bigint(20) DEFAULT NULL COMMENT '调拨时，对方项目Id',
  `TotalPrice` double DEFAULT NULL COMMENT '合计金额',
  `Remark` varchar(1000) DEFAULT NULL COMMENT '备注',
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

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
  `AllPrice` double DEFAULT NULL COMMENT '金额',
  `Remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `Img` varchar(50) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`Id`),
  KEY `FK2A819F475D61CCF7` (`MaterialId`),
  KEY `FK2A819F474BB6190E` (`HeaderId`),
  CONSTRAINT `jsh_depotitem_ibfk_1` FOREIGN KEY (`HeaderId`) REFERENCES `jsh_depothead` (`Id`) ON DELETE CASCADE,
  CONSTRAINT `jsh_depotitem_ibfk_2` FOREIGN KEY (`MaterialId`) REFERENCES `jsh_material` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1028 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=2023 DEFAULT CHARSET=utf8;

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
  `RetailPrice` double DEFAULT NULL COMMENT '零售价',
  `LowPrice` double DEFAULT NULL COMMENT '最低售价',
  `PresetPriceOne` double DEFAULT NULL COMMENT '预设售价一',
  `PresetPriceTwo` double DEFAULT NULL COMMENT '预设售价二',
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
  `Type` varchar(20) DEFAULT NULL COMMENT '类型',
  `Name` varchar(50) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

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
  `BeginNeedGet` double DEFAULT NULL COMMENT '期初应收',
  `BeginNeedPay` double DEFAULT NULL COMMENT '期初应付',
  `AllNeedGet` double DEFAULT NULL COMMENT '累计应收',
  `AllNeedPay` double DEFAULT NULL COMMENT '累计应付',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
--  Records 
-- ----------------------------
INSERT INTO `jsh_account` VALUES ('4','南通建行','3241423','121','34','66666'), ('9','公司总账1','4123411','423','412','77777');
INSERT INTO `jsh_accounthead` VALUES ('3','收入','1','3','333','9','33No','2016-12-10 00:00:00','444aa'), ('9','收入','1','3','213','4','3134','2016-12-10 00:00:00','12341'), ('12','收入','1','3','123','4','NO123','2016-12-10 00:00:00','666666'), ('13','收入','1','3','45','4','N666','2016-12-11 00:00:00','111'), ('14','支出','1','3','55','9','aa','2016-12-11 00:00:00','123'), ('15','收款','1','3','123','9','34124','2016-12-11 00:00:00','123'), ('16','付款','1','3','1234','4','234234','2016-12-11 00:00:00','2342'), ('17','转账','1','3','23','4','234','2016-12-11 00:00:00','234'), ('18','收款','1','3','11','4','123123','2016-12-18 00:00:00','123');
INSERT INTO `jsh_accountitem` VALUES ('3','9',NULL,'5',NULL,'123414'), ('6','12',NULL,'8','12','啊啊啊'), ('7','13',NULL,'7','1111','2222'), ('8','3',NULL,'9','11',''), ('9','3',NULL,'8','22',''), ('10','14',NULL,'10','123','123'), ('11','15','4','7','12341','124'), ('12','16',NULL,'1','3423','2342'), ('13','17',NULL,'10','12','1'), ('14','18','9',NULL,'11','123'), ('15','18','4',NULL,'22','aaa');
INSERT INTO `jsh_app` VALUES ('1','','企业邮箱','app','0000000001.png','../EmailManage/Email','600','400','\0','\0','\0','desk','010','','\0'), ('3','00','系统管理','app','0000000004.png','','1024','600','','\0','\0','desk','198','',''), ('6','','个人信息','app','0000000005.png','../user/password.jsp','600','400','\0','\0','\0','dock','200','',''), ('7','01','基础数据','app','0000000006.png','','1024','600','','\0','\0','desk','120','',''), ('8','02','进销存','app','0000000007.png','','1024','600','','\0','\0','desk','030','',''), ('20','13','公告管理','app','0000000020.png',NULL,'1024','600','','\0','\0','desk','125',NULL,'\0'), ('21','','今日留言','app','0000000021.png','../phone/msg','1024','600','','\0','\0','dock','000','','\0'), ('22','03','报表查询','app','0000000022.png','','1024','600','','\0','\0','desk','115','','');
INSERT INTO `jsh_asset` VALUES ('1','27','weizhi','','0',NULL,'11','2016-10-22 00:00:00','2016-10-21 00:00:00','2016-11-03 00:00:00','1231241','123124123','2','','2016-10','2016-10-22 20:04:48','63','2016-10-22 20:04:48','63');
INSERT INTO `jsh_assetcategory` VALUES ('14','递延资产','1','递延资产'), ('15','无形资产','1','无形资产'), ('16','长期投资','1','长期投资'), ('17','固定资产','1','固定资产'), ('18','流动资产','1','流动资产');
INSERT INTO `jsh_assetname` VALUES ('1','联想Y450','17','1','','1'), ('2','惠普打印机','15','1','','0'), ('12','乐萌水杯','16','1','','1'), ('13','机顶盒','17','1','机顶盒','0'), ('14','TCL电视','17','1','','1'), ('15','手机','17','1','','1'), ('16','硬盘','16','1','','0'), ('17','毛笔','17','1','','0'), ('18','杯子','17','1','','0'), ('19','建造师证书','15','1','','0'), ('20','算量软件','14','1','','1'), ('21','cad软件','15','1','','0'), ('22','办公桌','17','1','','0'), ('23','笔记本','17','1','笔记本','1'), ('24','打印机','17','1','打印机','0'), ('25','电脑','17','1','电脑','0'), ('26','电动车','16','1','电动车','0'), ('27','电源线','17','1','电源线','0');
INSERT INTO `jsh_depot` VALUES ('1','上海花边店','2',''), ('2','公司总部','1','总部'), ('3','苏州花边店','3','');
INSERT INTO `jsh_depothead` VALUES ('29','入库','采购','1','1234','李四','2016-11-08 22:14:37','2016-11-08 00:00:00','1','2',NULL,NULL,NULL,NULL,''), ('30','入库','采购','1','1235','李四','2016-11-08 22:15:16','2016-11-08 00:00:00','1','2',NULL,NULL,NULL,NULL,''), ('31','出库','销售','1','123A','李四','2016-11-08 22:16:11','2016-11-08 00:00:00','2',NULL,NULL,NULL,NULL,NULL,''), ('32','入库','采购','1','ww123','季圣华','2016-11-25 22:14:46','2016-11-25 00:00:00','1','2',NULL,NULL,NULL,NULL,''), ('33','入库','其它','1','234234','季圣华','2016-12-11 18:15:39','2016-12-11 00:00:00','1','2',NULL,NULL,NULL,NULL,'12312'), ('34','出库','其它','1','1234','季圣华','2016-12-11 18:44:50','2016-12-11 00:00:00','2',NULL,NULL,NULL,NULL,NULL,'41234'), ('35','出库','其它','1','bbb','季圣华','2016-12-11 22:31:49','2016-12-11 00:00:00','2',NULL,NULL,NULL,NULL,NULL,'aa666'), ('36','出库','调拨','1','bb22','季圣华','2016-12-11 22:32:52','2016-12-11 00:00:00',NULL,NULL,NULL,NULL,'2',NULL,'aa11'), ('37','入库','采购','3','123a','季圣华','2016-12-24 14:33:02','2016-12-24 00:00:00','4','2','9','36',NULL,NULL,'1212a'), ('38','入库','采购','3','123132','季圣华','2016-12-24 23:14:22','2016-12-24 00:00:00','1','1','9','44',NULL,'24','312'), ('39','入库','采购','3','222','季圣华','2016-12-24 23:43:50','2016-12-24 00:00:00','4','2','9','44',NULL,'85.8','3333');
INSERT INTO `jsh_depotitem` VALUES ('1014','29','485','300','0.4',NULL,'',''), ('1015','29','487','200','0.2',NULL,'',''), ('1016','30','498','432','0.5',NULL,'',''), ('1017','31','485','100','0.6',NULL,'',''), ('1018','32','498','1','0.9',NULL,'',''), ('1019','33','487','1','2',NULL,'',''), ('1020','34','498','21','12',NULL,'',''), ('1021','35','487','1','1',NULL,'',''), ('1022','36','498','1',NULL,NULL,'2','3'), ('1023','37','485','12','2.6','31.2','beizhu','img'), ('1024','37','498','12','1.4','16.8','a','b'), ('1025','38','485','12','2','24','',NULL), ('1026','39','498','22','2.1','46.2','',NULL), ('1027','39','487','33','1.2','39.6','',NULL);
INSERT INTO `jsh_functions` VALUES ('1','00','系统管理','0','','','0010','','电脑版'), ('2','01','基础数据','0','','','0020','','电脑版'), ('3','02','进销存','0','','','0030','','电脑版'), ('11','0001','系统管理','00','','\0','0110','','电脑版'), ('12','000101','应用管理','0001','../manage/app.jsp','\0','0132','','电脑版'), ('13','000102','角色管理','0001','../manage/role.jsp','\0','0130','','电脑版'), ('14','000103','用户管理','0001','../manage/user.jsp','\0','0140','','电脑版'), ('15','000104','日志管理','0001','../manage/log.jsp','\0','0160','','电脑版'), ('16','000105','功能管理','0001','../manage/functions.jsp','\0','0135','','电脑版'), ('21','0101','商品管理','01','','\0','0220','','电脑版'), ('22','010101','商品类别管理','0101','../materials/materialcategory.jsp','\0','0230','','电脑版'), ('23','010102','商品信息管理','0101','../materials/material.jsp','\0','0240','','电脑版'), ('24','0102','基本资料','01','','\0','0250','','电脑版'), ('25','010201','单位信息','0102','../manage/vendor.jsp','\0','0260','','电脑版'), ('26','010202','仓库管理','0102','../manage/depot.jsp','\0','0270','','电脑版'), ('31','010206','经手人管理','0102','../materials/person.jsp','\0','0284','','电脑版'), ('32','0202','入库管理','02','','\0','0330','','电脑版'), ('33','020201','采购入库','0202','../materials/purchase_in_list.jsp','\0','0340','','电脑版'), ('38','0203','出库管理','02','','\0','0390','','电脑版'), ('40','020302','调拨出库','0203','../materials/allocation_out_list.jsp','\0','0420','','电脑版'), ('41','020303','销售出库','0203','../materials/sale_out_list.jsp','\0','0410','','电脑版'), ('44','0204','财务管理','02','','\0','0450','','电脑版'), ('59','030101','项目进销存报表','0301','../reports/in_out_stock_report.jsp','\0','0600','','电脑版'), ('194','010204','收支项目','0102','../manage/inOutItem.jsp','\0','0282','','电脑版'), ('195','010205','结算账户','0102','../manage/account.jsp','\0','0283','','电脑版'), ('196','03','报表查询','0','','','0040','','电脑版'), ('197','020402','收入单','0204','../financial/item_in.jsp','\0','0465','','电脑版'), ('198','0301','报表查询','03','','\0','0570','','电脑版'), ('199','020304','采购退货','0203','../materials/purchase_back_list.jsp','\0','0415','','电脑版'), ('200','020202','销售退货','0202','../materials/sale_back_list.jsp','\0','0350','','电脑版'), ('201','020203','其它入库','0202','../materials/other_in_list.jsp','\0','0360','','电脑版'), ('202','020305','其它出库','0203','../materials/other_out_list.jsp','\0','0418','','电脑版'), ('203','020403','支出单','0204','../financial/item_out.jsp','\0','0470','','电脑版'), ('204','020404','收款单','0204','../financial/money_in.jsp','\0','0475','','电脑版'), ('205','020405','付款单','0204','../financial/money_out.jsp','\0','0480','','电脑版'), ('206','020406','转账单','0204','../financial/giro.jsp','\0','0490','','电脑版');
INSERT INTO `jsh_inoutitem` VALUES ('1','办公耗材','支出','办公耗材'), ('5','房租收入','收入','房租收入'), ('7','利息收入','收入','利息收入'), ('8','水电费','支出','水电费水电费'), ('9','快递费','支出','快递费'), ('10','交通报销费','支出','交通报销费'), ('11','差旅费','支出','差旅费');
INSERT INTO `jsh_log` VALUES ('1722','63','登录系统','192.168.1.104','2016-11-27 13:17:17','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1723','63','登录系统','192.168.1.104','2016-11-27 13:17:30','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1724','63','退出系统','192.168.1.104','2016-11-27 13:17:48','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1725','65','登录系统','192.168.1.104','2016-11-27 13:17:52','0','管理用户：ls 登录系统','ls 登录系统'), ('1726','65','退出系统','192.168.1.104','2016-11-27 13:18:18','0','管理用户：ls 退出系统','ls 退出系统'), ('1727','63','登录系统','192.168.1.104','2016-11-27 13:18:22','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1728','63','更新UserBusiness','192.168.1.104','2016-11-27 13:18:44','0','更新UserBusiness的ID为  6 成功！','更新UserBusiness成功'), ('1729','63','退出系统','192.168.1.104','2016-11-27 13:18:48','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1730','65','登录系统','192.168.1.104','2016-11-27 13:18:53','0','管理用户：ls 登录系统','ls 登录系统'), ('1731','63','登录系统','192.168.1.104','2016-12-04 10:38:50','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1732','63','增加物料','192.168.1.104','2016-12-04 10:40:52','0','增加物料名称为  11 成功！','增加物料成功'), ('1733','63','更新物料','192.168.1.104','2016-12-04 10:59:57','0','更新物料ID为  499 成功！','更新物料成功'), ('1734','63','更新物料','192.168.1.104','2016-12-04 11:00:13','0','更新物料ID为  499 成功！','更新物料成功'), ('1735','63','删除物料','192.168.1.104','2016-12-04 11:00:38','0','删除物料ID为  499 成功！','删除物料成功'), ('1736','63','增加物料','192.168.1.104','2016-12-04 11:02:35','0','增加物料名称为  11 成功！','增加物料成功'), ('1737','63','批量删除物料','192.168.1.104','2016-12-04 11:02:41','0','批量删除物料ID为  500 成功！','批量删除物料成功'), ('1738','63','更新功能','192.168.1.104','2016-12-04 11:04:43','0','更新功能ID为  26 成功！','更新功能成功'), ('1739','63','增加供应商','192.168.1.104','2016-12-04 11:38:13','0','增加供应商名称为  aa 成功！','增加供应商成功'), ('1740','63','增加供应商','192.168.1.104','2016-12-04 11:48:36','0','增加供应商名称为  aaaa 成功！','增加供应商成功'), ('1741','63','删除供应商','192.168.1.104','2016-12-04 11:48:53','0','删除供应商ID为  3,名称为  aa成功！','删除供应商成功'), ('1742','63','更新供应商','192.168.1.104','2016-12-04 11:48:59','0','更新供应商ID为  4 成功！','更新供应商成功'), ('1743','63','更新功能','192.168.1.104','2016-12-04 13:06:24','0','更新功能ID为  31 成功！','更新功能成功'), ('1744','63','删除功能','192.168.1.104','2016-12-04 13:06:47','0','删除功能ID为  30 成功！','删除功能成功'), ('1745','63','更新功能','192.168.1.104','2016-12-04 13:08:35','0','更新功能ID为  24 成功！','更新功能成功'), ('1746','63','更新功能','192.168.1.104','2016-12-04 13:09:52','0','更新功能ID为  24 成功！','更新功能成功'), ('1747','63','更新功能','192.168.1.104','2016-12-04 13:11:00','0','更新功能ID为  21 成功！','更新功能成功'), ('1748','63','更新功能','192.168.1.104','2016-12-04 13:11:08','0','更新功能ID为  22 成功！','更新功能成功'), ('1749','63','更新功能','192.168.1.104','2016-12-04 13:11:16','0','更新功能ID为  23 成功！','更新功能成功'), ('1750','63','更新功能','192.168.1.104','2016-12-04 13:11:31','0','更新功能ID为  23 成功！','更新功能成功'), ('1751','63','更新应用','192.168.1.104','2016-12-04 13:34:39','0','更新应用ID为  22 成功！','更新应用成功'), ('1752','63','更新应用','192.168.1.104','2016-12-04 13:35:13','0','更新应用ID为  22 成功！','更新应用成功'), ('1753','63','登录系统','192.168.1.104','2016-12-04 13:36:45','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1754','63','登录系统','192.168.1.104','2016-12-04 13:38:31','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1755','63','增加功能','192.168.1.104','2016-12-04 13:40:10','0','增加功能名称为  报表管理 成功！','增加功能成功'), ('1756','63','更新功能','192.168.1.104','2016-12-04 13:40:38','0','更新功能ID为  58 成功！','更新功能成功'), ('1757','63','更新功能','192.168.1.104','2016-12-04 13:40:54','0','更新功能ID为  59 成功！','更新功能成功'), ('1758','63','更新应用','192.168.1.104','2016-12-04 13:42:15','0','更新应用ID为  22 成功！','更新应用成功'), ('1759','63','更新功能','192.168.1.104','2016-12-04 13:43:23','0','更新功能ID为  58 成功！','更新功能成功'), ('1760','63','更新应用','192.168.1.104','2016-12-04 13:43:44','0','更新应用ID为  22 成功！','更新应用成功'), ('1761','63','更新应用','192.168.1.104','2016-12-04 13:47:17','0','更新应用ID为  22 成功！','更新应用成功'), ('1762','63','登录系统','192.168.112.102','2016-12-04 21:00:14','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1763','63','更新应用','192.168.112.102','2016-12-04 21:01:40','0','更新应用ID为  7 成功！','更新应用成功'), ('1764','63','更新应用','192.168.112.102','2016-12-04 21:02:40','0','更新应用ID为  7 成功！','更新应用成功'), ('1765','63','登录系统','192.168.112.102','2016-12-04 21:14:18','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1766','63','登录系统','192.168.112.102','2016-12-04 21:49:44','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1767','63','登录系统','192.168.4.108','2016-12-10 14:24:27','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1768','63','登录系统','192.168.4.108','2016-12-10 14:30:33','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1769','63','退出系统','192.168.4.108','2016-12-10 14:31:27','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1770','63','登录系统','192.168.4.108','2016-12-10 14:31:33','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1771','63','增加经手人','192.168.4.108','2016-12-10 14:55:24','0','增加经手人名称为  赵五-财务 成功！','增加经手人成功'), ('1772','63','更新经手人','192.168.4.108','2016-12-10 14:55:33','0','更新经手人ID为  2 成功！','更新经手人成功'), ('1773','63','更新经手人','192.168.4.108','2016-12-10 14:55:45','0','更新经手人ID为  1 成功！','更新经手人成功'), ('1774','63','增加功能','192.168.4.108','2016-12-10 15:29:27','0','增加功能名称为  收入单 成功！','增加功能成功'), ('1775','63','更新UserBusiness','192.168.4.108','2016-12-10 15:30:47','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1776','63','更新功能','192.168.4.108','2016-12-10 15:35:04','0','更新功能ID为  58 成功！','更新功能成功'), ('1777','63','更新功能','192.168.4.108','2016-12-10 15:35:59','0','更新功能ID为  58 成功！','更新功能成功'), ('1778','63','更新功能','192.168.4.108','2016-12-10 15:37:40','0','更新功能ID为  196 成功！','更新功能成功'), ('1779','63','更新功能','192.168.4.108','2016-12-10 15:39:07','0','更新功能ID为  196 成功！','更新功能成功'), ('1780','63','更新功能','192.168.4.108','2016-12-10 15:39:23','0','更新功能ID为  59 成功！','更新功能成功'), ('1781','63','删除功能','192.168.4.108','2016-12-10 15:39:45','0','删除功能ID为  58 成功！','删除功能成功'), ('1782','63','更新功能','192.168.4.108','2016-12-10 15:40:03','0','更新功能ID为  59 成功！','更新功能成功'), ('1783','63','更新UserBusiness','192.168.4.108','2016-12-10 15:41:52','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1784','63','增加功能','192.168.4.108','2016-12-10 15:44:39','0','增加功能名称为  报表管理 成功！','增加功能成功'), ('1785','63','更新功能','192.168.4.108','2016-12-10 15:44:51','0','更新功能ID为  59 成功！','更新功能成功'), ('1786','63','更新功能','192.168.4.108','2016-12-10 15:46:30','0','更新功能ID为  198 成功！','更新功能成功'), ('1787','63','增加经手人','192.168.4.108','2016-12-10 16:48:36','0','增加经手人名称为  赵六-财务 成功！','增加经手人成功'), ('1788','63','更新经手人','192.168.4.108','2016-12-10 16:48:43','0','更新经手人ID为  3 成功！','更新经手人成功'), ('1789','63','更新经手人','192.168.4.108','2016-12-10 16:48:49','0','更新经手人ID为  3 成功！','更新经手人成功'), ('1790','63','增加仓库','192.168.4.108','2016-12-10 16:59:41','0','增加仓库名称为  总部 成功！','增加仓库成功'), ('1791','63','更新仓库','192.168.4.108','2016-12-10 16:59:52','0','更新仓库ID为  2 成功！','更新仓库成功'), ('1792','63','更新仓库','192.168.4.108','2016-12-10 17:00:03','0','更新仓库ID为  1 成功！','更新仓库成功'), ('1793','63','更新经手人','192.168.4.108','2016-12-10 17:00:15','0','更新经手人ID为  3 成功！','更新经手人成功'), ('1794','63','更新经手人','192.168.4.108','2016-12-10 17:00:23','0','更新经手人ID为  4 成功！','更新经手人成功'), ('1795','63','登录系统','192.168.4.108','2016-12-10 17:10:40','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1796','63','退出系统','192.168.4.108','2016-12-10 17:39:05','0','管理用户：jsh 退出系统','jsh 退出系统'), ('1797','63','登录系统','192.168.4.108','2016-12-10 17:39:16','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1798','63','批量删除功能','192.168.4.108','2016-12-10 17:44:39','0','批量删除功能ID为  51,193 成功！','批量删除功能成功'), ('1799','63','批量删除功能','192.168.4.108','2016-12-10 17:44:54','0','批量删除功能ID为  46,47,48,49,50 成功！','批量删除功能成功'), ('1800','63','批量删除功能','192.168.4.108','2016-12-10 17:45:26','0','批量删除功能ID为  52,53,54,55 成功！','批量删除功能成功'), ('1801','63','批量删除功能','192.168.4.108','2016-12-10 17:45:49','0','批量删除功能ID为  45 成功！','批量删除功能成功'), ('1802','63','登录系统','192.168.112.102','2016-12-10 20:19:13','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1803','63','增加财务','192.168.112.102','2016-12-10 20:20:06','0','增加财务编号为  33 成功！','增加财务成功'), ('1804','63','增加财务','192.168.112.102','2016-12-10 20:20:11','0','增加财务编号为  33 成功！','增加财务成功'), ('1805','63','增加财务','192.168.112.102','2016-12-10 20:20:12','0','增加财务编号为  33 成功！','增加财务成功'), ('1806','63','增加财务','192.168.112.102','2016-12-10 20:25:48','0','增加财务编号为  123 成功！','增加财务成功'), ('1807','63','登录系统','192.168.112.102','2016-12-10 20:50:11','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1808','63','增加财务','192.168.112.102','2016-12-10 20:51:41','0','增加财务编号为  3123 成功！','增加财务成功'), ('1809','63','增加财务','192.168.112.102','2016-12-10 20:53:58','0','增加财务编号为  3123 成功！','增加财务成功'), ('1810','63','增加财务','192.168.112.102','2016-12-10 21:08:57','0','增加财务编号为  123 成功！','增加财务成功'), ('1811','63','增加财务','192.168.112.102','2016-12-10 21:47:45','0','增加财务编号为  123 成功！','增加财务成功'), ('1812','63','保存财务明细','192.168.112.102','2016-12-10 21:47:46','0','保存财务明细对应主表编号为  8 成功！','保存财务明细成功'), ('1813','63','登录系统','192.168.112.102','2016-12-10 22:49:54','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1814','63','删除财务','192.168.112.102','2016-12-10 22:52:43','0','删除财务ID为  5 成功！','删除财务成功'), ('1815','63','批量删除财务','192.168.112.102','2016-12-10 22:52:49','0','批量删除财务ID为  4 成功！','批量删除财务成功'), ('1816','63','增加财务','192.168.112.102','2016-12-10 22:53:19','0','增加财务编号为  3134 成功！','增加财务成功'), ('1817','63','保存财务明细','192.168.112.102','2016-12-10 22:53:20','0','保存财务明细对应主表编号为  9 成功！','保存财务明细成功'), ('1818','63','删除财务','192.168.112.102','2016-12-10 22:53:35','0','删除财务ID为  7 成功！','删除财务成功'), ('1819','63','增加财务','192.168.112.102','2016-12-10 22:54:05','0','增加财务编号为  N123 成功！','增加财务成功'), ('1820','63','保存财务明细','192.168.112.102','2016-12-10 22:54:06','0','保存财务明细对应主表编号为  10 成功！','保存财务明细成功'), ('1821','63','更新财务','192.168.112.102','2016-12-10 22:54:28','0','更新财务ID为  10 成功！','更新财务成功');
INSERT INTO `jsh_log` VALUES ('1822','63','更新财务','192.168.112.102','2016-12-10 23:06:54','0','更新财务ID为  10 成功！','更新财务成功'), ('1823','63','增加财务','192.168.112.102','2016-12-10 23:07:51','0','增加财务编号为  124 成功！','增加财务成功'), ('1824','63','保存财务明细','192.168.112.102','2016-12-10 23:07:51','0','保存财务明细对应主表编号为  11 成功！','保存财务明细成功'), ('1825','63','登录系统','192.168.112.102','2016-12-10 23:09:32','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1826','63','更新财务','192.168.112.102','2016-12-10 23:09:55','0','更新财务ID为  10 成功！','更新财务成功'), ('1827','63','更新收支项目','192.168.112.102','2016-12-10 23:10:18','0','更新收支项目ID为  7 成功！','更新收支项目成功'), ('1828','63','更新收支项目','192.168.112.102','2016-12-10 23:10:32','0','更新收支项目ID为  5 成功！','更新收支项目成功'), ('1829','63','更新收支项目','192.168.112.102','2016-12-10 23:10:47','0','更新收支项目ID为  1 成功！','更新收支项目成功'), ('1830','63','增加收支项目','192.168.112.102','2016-12-10 23:11:05','0','增加收支项目名称为  水电费 成功！','增加收支项目成功'), ('1831','63','增加收支项目','192.168.112.102','2016-12-10 23:11:18','0','增加收支项目名称为  快递费 成功！','增加收支项目成功'), ('1832','63','增加收支项目','192.168.112.102','2016-12-10 23:11:30','0','增加收支项目名称为  交通报销费 成功！','增加收支项目成功'), ('1833','63','增加收支项目','192.168.112.102','2016-12-10 23:11:50','0','增加收支项目名称为  差旅费 成功！','增加收支项目成功'), ('1834','63','增加财务','192.168.112.102','2016-12-10 23:12:45','0','增加财务编号为  NO123 成功！','增加财务成功'), ('1835','63','保存财务明细','192.168.112.102','2016-12-10 23:12:46','0','保存财务明细对应主表编号为  12 成功！','保存财务明细成功'), ('1836','63','更新财务','192.168.112.102','2016-12-10 23:27:12','0','更新财务ID为  12 成功！','更新财务成功'), ('1837','63','更新财务','192.168.112.102','2016-12-10 23:27:46','0','更新财务ID为  12 成功！','更新财务成功'), ('1838','63','登录系统','192.168.112.102','2016-12-10 23:29:10','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1839','63','更新财务','192.168.112.102','2016-12-10 23:30:01','0','更新财务ID为  12 成功！','更新财务成功'), ('1840','63','更新财务','192.168.112.102','2016-12-10 23:30:43','0','更新财务ID为  12 成功！','更新财务成功'), ('1841','63','更新财务','192.168.112.102','2016-12-10 23:31:17','0','更新财务ID为  12 成功！','更新财务成功'), ('1842','63','更新财务','192.168.112.102','2016-12-10 23:38:58','0','更新财务ID为  12 成功！','更新财务成功'), ('1843','63','更新财务','192.168.112.102','2016-12-10 23:42:13','0','更新财务ID为  12 成功！','更新财务成功'), ('1844','63','登录系统','192.168.112.102','2016-12-10 23:45:16','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1845','63','更新财务','192.168.112.102','2016-12-10 23:45:34','0','更新财务ID为  12 成功！','更新财务成功'), ('1846','63','增加财务','192.168.112.102','2016-12-10 23:46:22','0','增加财务编号为  12312 成功！','增加财务成功'), ('1847','63','保存财务明细','192.168.112.102','2016-12-10 23:46:23','0','保存财务明细对应主表编号为  13 成功！','保存财务明细成功'), ('1848','63','更新财务','192.168.112.102','2016-12-10 23:46:50','0','更新财务ID为  13 成功！','更新财务成功'), ('1849','63','删除财务','192.168.112.102','2016-12-10 23:47:54','1','删除财务ID为  9 失败！','删除财务失败'), ('1850','63','删除财务','192.168.112.102','2016-12-10 23:48:00','1','删除财务ID为  10 失败！','删除财务失败'), ('1851','63','批量删除财务','192.168.112.102','2016-12-10 23:48:07','1','批量删除财务ID为  10 失败！','批量删除财务失败'), ('1852','63','删除财务','192.168.112.102','2016-12-10 23:53:36','0','删除财务ID为  10 成功！','删除财务成功'), ('1853','63','删除财务','192.168.112.102','2016-12-10 23:53:39','0','删除财务ID为  8 成功！','删除财务成功'), ('1854','63','批量删除财务','192.168.112.102','2016-12-10 23:53:44','0','批量删除财务ID为  6 成功！','批量删除财务成功'), ('1855','63','登录系统','192.168.4.107','2016-12-11 09:50:41','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1856','63','删除财务','192.168.4.107','2016-12-11 09:51:09','0','删除财务ID为  11 成功！','删除财务成功'), ('1857','63','增加财务','192.168.4.107','2016-12-11 09:51:42','0','增加财务编号为  N666 成功！','增加财务成功'), ('1858','63','保存财务明细','192.168.4.107','2016-12-11 09:51:43','0','保存财务明细对应主表编号为  13 成功！','保存财务明细成功'), ('1859','63','批量删除功能','192.168.4.107','2016-12-11 10:08:23','0','批量删除功能ID为  34,35,36,37,39 成功！','批量删除功能成功'), ('1860','63','批量删除功能','192.168.4.107','2016-12-11 10:08:49','0','批量删除功能ID为  42,43 成功！','批量删除功能成功'), ('1861','63','更新功能','192.168.4.107','2016-12-11 10:15:14','0','更新功能ID为  40 成功！','更新功能成功'), ('1862','63','更新功能','192.168.4.107','2016-12-11 10:15:32','0','更新功能ID为  41 成功！','更新功能成功'), ('1863','63','更新功能','192.168.4.107','2016-12-11 10:17:58','0','更新功能ID为  3 成功！','更新功能成功'), ('1864','63','更新功能','192.168.4.107','2016-12-11 10:18:32','0','更新功能ID为  3 成功！','更新功能成功'), ('1865','63','删除功能','192.168.4.107','2016-12-11 10:18:56','0','删除功能ID为  10 成功！','删除功能成功'), ('1866','63','更新功能','192.168.4.107','2016-12-11 10:20:32','0','更新功能ID为  196 成功！','更新功能成功'), ('1867','63','更新功能','192.168.4.107','2016-12-11 10:33:16','0','更新功能ID为  15 成功！','更新功能成功'), ('1868','63','更新功能','192.168.4.107','2016-12-11 10:34:36','0','更新功能ID为  14 成功！','更新功能成功'), ('1869','63','登录系统','192.168.4.107','2016-12-11 14:36:43','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1870','63','增加功能','192.168.4.107','2016-12-11 15:11:33','0','增加功能名称为  入库退货 成功！','增加功能成功'), ('1871','63','增加功能','192.168.4.107','2016-12-11 15:12:44','0','增加功能名称为  销售退货 成功！','增加功能成功'), ('1872','63','更新功能','192.168.4.107','2016-12-11 15:13:08','0','更新功能ID为  199 成功！','更新功能成功'), ('1873','63','更新UserBusiness','192.168.4.107','2016-12-11 15:13:39','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1874','63','增加功能','192.168.4.107','2016-12-11 15:20:11','0','增加功能名称为  其他入库 成功！','增加功能成功'), ('1875','63','增加功能','192.168.4.107','2016-12-11 15:21:11','0','增加功能名称为  其他出库 成功！','增加功能成功'), ('1876','63','更新UserBusiness','192.168.4.107','2016-12-11 15:21:26','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1877','63','更新功能','192.168.4.107','2016-12-11 15:24:06','0','更新功能ID为  200 成功！','更新功能成功'), ('1878','63','更新功能','192.168.4.107','2016-12-11 15:24:43','0','更新功能ID为  199 成功！','更新功能成功'), ('1879','63','更新UserBusiness','192.168.4.107','2016-12-11 15:25:05','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1880','63','更新UserBusiness','192.168.4.107','2016-12-11 15:25:28','0','更新UserBusiness的ID为  6 成功！','更新UserBusiness成功'), ('1881','63','更新功能','192.168.4.107','2016-12-11 15:27:25','0','更新功能ID为  197 成功！','更新功能成功'), ('1882','63','更新功能','192.168.4.107','2016-12-11 15:30:42','0','更新功能ID为  197 成功！','更新功能成功'), ('1883','63','增加功能','192.168.4.107','2016-12-11 15:31:43','0','增加功能名称为  支出单 成功！','增加功能成功'), ('1884','63','更新功能','192.168.4.107','2016-12-11 15:31:56','0','更新功能ID为  203 成功！','更新功能成功'), ('1885','63','更新UserBusiness','192.168.4.107','2016-12-11 15:32:12','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1886','63','更新UserBusiness','192.168.4.107','2016-12-11 15:32:23','0','更新UserBusiness的ID为  6 成功！','更新UserBusiness成功'), ('1887','63','更新功能','192.168.4.107','2016-12-11 15:39:07','0','更新功能ID为  197 成功！','更新功能成功'), ('1888','63','更新功能','192.168.4.107','2016-12-11 15:39:14','0','更新功能ID为  203 成功！','更新功能成功'), ('1889','63','增加功能','192.168.4.107','2016-12-11 15:40:32','0','增加功能名称为  收款单 成功！','增加功能成功'), ('1890','63','增加功能','192.168.4.107','2016-12-11 15:41:18','0','增加功能名称为  付款单 成功！','增加功能成功'), ('1891','63','增加功能','192.168.4.107','2016-12-11 15:42:11','0','增加功能名称为  转账单 成功！','增加功能成功'), ('1892','63','更新功能','192.168.4.107','2016-12-11 15:42:26','0','更新功能ID为  204 成功！','更新功能成功'), ('1893','63','更新UserBusiness','192.168.4.107','2016-12-11 15:42:37','0','更新UserBusiness的ID为  5 成功！','更新UserBusiness成功'), ('1894','63','更新功能','192.168.4.107','2016-12-11 15:43:50','0','更新功能ID为  206 成功！','更新功能成功'), ('1895','63','登录系统','192.168.112.100','2016-12-11 18:02:00','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1896','63','增加仓管通','192.168.112.100','2016-12-11 18:15:39','0','增加仓管通编号为  234234 成功！','增加仓管通成功'), ('1897','63','保存仓管通明细','192.168.112.100','2016-12-11 18:15:40','0','保存仓管通明细对应主表编号为  33 成功！','保存仓管通明细成功'), ('1898','63','更新功能','192.168.112.100','2016-12-11 18:23:48','0','更新功能ID为  201 成功！','更新功能成功'), ('1899','63','更新功能','192.168.112.100','2016-12-11 18:23:58','0','更新功能ID为  202 成功！','更新功能成功'), ('1900','63','增加仓管通','192.168.112.100','2016-12-11 18:44:50','0','增加仓管通编号为  1234 成功！','增加仓管通成功'), ('1901','63','保存仓管通明细','192.168.112.100','2016-12-11 18:44:51','0','保存仓管通明细对应主表编号为  34 成功！','保存仓管通明细成功'), ('1902','63','登录系统','192.168.112.100','2016-12-11 20:14:46','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1903','63','更新财务','192.168.112.100','2016-12-11 21:29:17','0','更新财务ID为  13 成功！','更新财务成功'), ('1904','63','更新财务','192.168.112.100','2016-12-11 21:29:47','0','更新财务ID为  13 成功！','更新财务成功'), ('1905','63','更新财务','192.168.112.100','2016-12-11 21:30:26','0','更新财务ID为  9 成功！','更新财务成功'), ('1906','63','更新财务','192.168.112.100','2016-12-11 21:32:31','0','更新财务ID为  13 成功！','更新财务成功'), ('1907','63','更新财务','192.168.112.100','2016-12-11 21:44:38','0','更新财务ID为  13 成功！','更新财务成功'), ('1908','63','登录系统','192.168.112.100','2016-12-11 21:50:51','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1909','63','更新财务','192.168.112.100','2016-12-11 21:51:18','0','更新财务ID为  13 成功！','更新财务成功'), ('1910','63','更新财务','192.168.112.100','2016-12-11 21:59:18','0','更新财务ID为  13 成功！','更新财务成功'), ('1911','63','保存财务明细','192.168.112.100','2016-12-11 21:59:19','0','保存财务明细对应主表编号为  13 成功！','保存财务明细成功'), ('1912','63','更新财务','192.168.112.100','2016-12-11 22:00:04','0','更新财务ID为  12 成功！','更新财务成功'), ('1913','63','保存财务明细','192.168.112.100','2016-12-11 22:00:05','0','保存财务明细对应主表编号为  12 成功！','保存财务明细成功'), ('1914','63','更新财务','192.168.112.100','2016-12-11 22:01:32','0','更新财务ID为  13 成功！','更新财务成功'), ('1915','63','保存财务明细','192.168.112.100','2016-12-11 22:01:33','0','保存财务明细对应主表编号为  13 成功！','保存财务明细成功'), ('1916','63','更新仓管通','192.168.112.100','2016-12-11 22:08:09','0','更新仓管通ID为  32 成功！','更新仓管通成功'), ('1917','63','保存仓管通明细','192.168.112.100','2016-12-11 22:08:09','0','保存仓管通明细对应主表编号为  32 成功！','保存仓管通明细成功'), ('1918','63','登录系统','192.168.112.100','2016-12-11 22:29:23','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1919','63','登录系统','192.168.112.100','2016-12-11 22:29:44','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1920','63','删除财务','192.168.112.100','2016-12-11 22:30:04','0','删除财务ID为  1 成功！','删除财务成功'), ('1921','63','批量删除财务','192.168.112.100','2016-12-11 22:30:09','0','批量删除财务ID为  2 成功！','批量删除财务成功');
INSERT INTO `jsh_log` VALUES ('1922','63','更新财务','192.168.112.100','2016-12-11 22:30:25','0','更新财务ID为  3 成功！','更新财务成功'), ('1923','63','更新财务','192.168.112.100','2016-12-11 22:30:35','0','更新财务ID为  3 成功！','更新财务成功'), ('1924','63','保存财务明细','192.168.112.100','2016-12-11 22:30:35','0','保存财务明细对应主表编号为  3 成功！','保存财务明细成功'), ('1925','63','更新财务','192.168.112.100','2016-12-11 22:30:44','0','更新财务ID为  3 成功！','更新财务成功'), ('1926','63','保存财务明细','192.168.112.100','2016-12-11 22:30:44','0','保存财务明细对应主表编号为  3 成功！','保存财务明细成功'), ('1927','63','更新财务','192.168.112.100','2016-12-11 22:30:53','0','更新财务ID为  3 成功！','更新财务成功'), ('1928','63','增加仓管通','192.168.112.100','2016-12-11 22:31:49','0','增加仓管通编号为  bbb 成功！','增加仓管通成功'), ('1929','63','保存仓管通明细','192.168.112.100','2016-12-11 22:31:50','0','保存仓管通明细对应主表编号为  35 成功！','保存仓管通明细成功'), ('1930','63','更新仓管通','192.168.112.100','2016-12-11 22:32:16','0','更新仓管通ID为  35 成功！','更新仓管通成功'), ('1931','63','增加仓管通','192.168.112.100','2016-12-11 22:32:52','0','增加仓管通编号为  bb22 成功！','增加仓管通成功'), ('1932','63','保存仓管通明细','192.168.112.100','2016-12-11 22:32:53','0','保存仓管通明细对应主表编号为  36 成功！','保存仓管通明细成功'), ('1933','63','增加财务','192.168.112.100','2016-12-11 22:37:44','0','增加财务编号为  aa 成功！','增加财务成功'), ('1934','63','保存财务明细','192.168.112.100','2016-12-11 22:37:45','0','保存财务明细对应主表编号为  14 成功！','保存财务明细成功'), ('1935','63','增加财务','192.168.112.100','2016-12-11 22:38:12','0','增加财务编号为  34124 成功！','增加财务成功'), ('1936','63','保存财务明细','192.168.112.100','2016-12-11 22:38:12','0','保存财务明细对应主表编号为  15 成功！','保存财务明细成功'), ('1937','63','增加财务','192.168.112.100','2016-12-11 22:38:44','0','增加财务编号为  234234 成功！','增加财务成功'), ('1938','63','保存财务明细','192.168.112.100','2016-12-11 22:38:44','0','保存财务明细对应主表编号为  16 成功！','保存财务明细成功'), ('1939','63','增加财务','192.168.112.100','2016-12-11 22:39:23','0','增加财务编号为  234 成功！','增加财务成功'), ('1940','63','保存财务明细','192.168.112.100','2016-12-11 22:39:23','0','保存财务明细对应主表编号为  17 成功！','保存财务明细成功'), ('1941','63','登录系统','192.168.1.103','2016-12-18 11:47:14','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1942','63','增加财务','192.168.1.103','2016-12-18 12:57:39','0','增加财务编号为  123123 成功！','增加财务成功'), ('1943','63','保存财务明细','192.168.1.103','2016-12-18 12:57:40','0','保存财务明细对应主表编号为  18 成功！','保存财务明细成功'), ('1944','63','更新供应商','192.168.1.103','2016-12-18 13:02:15','0','更新供应商ID为  4 成功！','更新供应商成功'), ('1945','63','更新财务','192.168.1.103','2016-12-18 13:02:40','0','更新财务ID为  18 成功！','更新财务成功'), ('1946','63','保存财务明细','192.168.1.103','2016-12-18 13:02:41','0','保存财务明细对应主表编号为  18 成功！','保存财务明细成功'), ('1947','63','更新财务','192.168.1.103','2016-12-18 13:03:01','0','更新财务ID为  18 成功！','更新财务成功'), ('1948','63','保存财务明细','192.168.1.103','2016-12-18 13:03:02','0','保存财务明细对应主表编号为  18 成功！','保存财务明细成功'), ('1949','63','更新财务','192.168.1.103','2016-12-18 13:05:58','0','更新财务ID为  15 成功！','更新财务成功'), ('1950','63','保存财务明细','192.168.1.103','2016-12-18 13:05:58','0','保存财务明细对应主表编号为  15 成功！','保存财务明细成功'), ('1951','63','登录系统','192.168.112.101','2016-12-18 21:32:52','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1952','63','更新UserBusiness','192.168.112.101','2016-12-18 22:00:16','0','更新UserBusiness的ID为  18 成功！','更新UserBusiness成功'), ('1953','63','登录系统','127.0.0.1','2016-12-20 12:30:58','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1954','63','登录系统','127.0.0.1','2016-12-20 12:45:18','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1955','63','登录系统','192.168.112.102','2016-12-23 22:08:55','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1956','63','增加仓管通','192.168.112.102','2016-12-23 23:12:33','1','增加仓管通编号为  wrwq 失败！','增加仓管通失败'), ('1957','63','登录系统','192.168.112.102','2016-12-24 08:45:28','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1958','63','登录系统','192.168.112.102','2016-12-24 12:12:45','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1959','63','增加仓库','192.168.112.102','2016-12-24 12:33:18','0','增加仓库名称为  苏州花边店 成功！','增加仓库成功'), ('1960','63','更新UserBusiness','192.168.112.102','2016-12-24 12:44:03','0','更新UserBusiness的ID为  18 成功！','更新UserBusiness成功'), ('1961','63','更新UserBusiness','192.168.112.102','2016-12-24 13:10:12','0','更新UserBusiness的ID为  18 成功！','更新UserBusiness成功'), ('1962','63','增加经手人','192.168.112.102','2016-12-24 13:28:51','1','增加经手人名称为   失败！','增加经手人失败'), ('1963','63','增加经手人','192.168.112.102','2016-12-24 13:36:27','0','增加经手人名称为   成功！','增加经手人成功'), ('1964','63','增加经手人','192.168.112.102','2016-12-24 13:40:00','0','增加经手人名称为   成功！','增加经手人成功'), ('1965','63','增加经手人','192.168.112.102','2016-12-24 13:40:06','0','增加经手人名称为   成功！','增加经手人成功'), ('1966','63','增加经手人','192.168.112.102','2016-12-24 13:42:26','0','增加经手人名称为   成功！','增加经手人成功'), ('1967','63','增加经手人','192.168.112.102','2016-12-24 13:45:11','0','增加经手人名称为   成功！','增加经手人成功'), ('1968','63','增加经手人','192.168.112.102','2016-12-24 13:46:29','0','增加经手人名称为   成功！','增加经手人成功'), ('1969','63','批量删除经手人','192.168.112.102','2016-12-24 13:48:20','0','批量删除经手人ID为  5,6,7,8,9,10 成功！','批量删除经手人成功'), ('1970','63','增加经手人','192.168.112.102','2016-12-24 13:49:03','0','增加经手人名称为  655aaaa 成功！','增加经手人成功'), ('1971','63','增加经手人','192.168.112.102','2016-12-24 13:50:08','0','增加经手人名称为  11111 成功！','增加经手人成功'), ('1972','63','删除经手人','192.168.112.102','2016-12-24 13:50:11','0','删除经手人ID为  12 成功！','删除经手人成功'), ('1973','63','删除经手人','192.168.112.102','2016-12-24 13:50:15','0','删除经手人ID为  11 成功！','删除经手人成功'), ('1974','63','增加经手人','192.168.112.102','2016-12-24 13:50:31','0','增加经手人名称为  rrrrr 成功！','增加经手人成功'), ('1975','63','更新经手人','192.168.112.102','2016-12-24 13:50:36','0','更新经手人ID为  13 成功！','更新经手人成功'), ('1976','63','删除经手人','192.168.112.102','2016-12-24 13:53:27','0','删除经手人ID为  13 成功！','删除经手人成功'), ('1977','63','删除经手人','192.168.112.102','2016-12-24 13:55:13','1','删除经手人ID为  2 失败！','删除经手人失败'), ('1978','63','删除经手人','192.168.112.102','2016-12-24 13:55:19','1','删除经手人ID为  2 失败！','删除经手人失败'), ('1979','63','增加经手人','192.168.112.102','2016-12-24 13:55:25','0','增加经手人名称为  123123 成功！','增加经手人成功'), ('1980','63','删除经手人','192.168.112.102','2016-12-24 13:55:28','0','删除经手人ID为  14 成功！','删除经手人成功'), ('1981','63','删除经手人','192.168.112.102','2016-12-24 13:55:32','1','删除经手人ID为  2 失败！','删除经手人失败'), ('1982','63','删除经手人','192.168.112.102','2016-12-24 13:56:05','1','删除经手人ID为  2 失败！','删除经手人失败'), ('1983','63','更新经手人','192.168.112.102','2016-12-24 13:56:18','0','更新经手人ID为  2 成功！','更新经手人成功'), ('1984','63','更新经手人','192.168.112.102','2016-12-24 13:56:33','0','更新经手人ID为  2 成功！','更新经手人成功'), ('1985','63','增加仓管通','192.168.112.102','2016-12-24 14:07:11','1','增加仓管通编号为  1111 失败！','增加仓管通失败'), ('1986','63','增加仓管通','192.168.112.102','2016-12-24 14:07:20','1','增加仓管通编号为  1111 失败！','增加仓管通失败'), ('1987','63','增加仓管通','192.168.112.102','2016-12-24 14:17:53','1','增加仓管通编号为  33 失败！','增加仓管通失败'), ('1988','63','增加仓管通','192.168.112.102','2016-12-24 14:18:15','1','增加仓管通编号为  33 失败！','增加仓管通失败'), ('1989','63','增加仓管通','192.168.112.102','2016-12-24 14:33:03','0','增加仓管通编号为  123 成功！','增加仓管通成功'), ('1990','63','保存仓管通明细','192.168.112.102','2016-12-24 14:33:03','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('1991','63','更新仓管通','192.168.112.102','2016-12-24 14:35:40','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('1992','63','更新仓管通','192.168.112.102','2016-12-24 14:37:08','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('1993','63','保存仓管通明细','192.168.112.102','2016-12-24 14:37:08','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('1994','63','更新仓管通','192.168.112.102','2016-12-24 14:37:32','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('1995','63','更新仓管通','192.168.112.102','2016-12-24 14:51:08','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('1996','63','更新仓管通','192.168.112.102','2016-12-24 14:51:47','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('1997','63','保存仓管通明细','192.168.112.102','2016-12-24 14:51:48','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('1998','63','登录系统','192.168.112.102','2016-12-24 15:32:37','0','管理用户：jsh 登录系统','jsh 登录系统'), ('1999','63','登录系统','192.168.112.102','2016-12-24 16:14:10','0','管理用户：jsh 登录系统','jsh 登录系统'), ('2000','63','登录系统','192.168.112.102','2016-12-24 17:43:55','0','管理用户：jsh 登录系统','jsh 登录系统'), ('2001','63','登录系统','192.168.112.102','2016-12-24 17:45:17','0','管理用户：jsh 登录系统','jsh 登录系统'), ('2002','63','登录系统','192.168.112.102','2016-12-24 19:47:51','0','管理用户：jsh 登录系统','jsh 登录系统'), ('2003','63','更新仓管通','192.168.112.102','2016-12-24 22:12:26','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('2004','63','保存仓管通明细','192.168.112.102','2016-12-24 22:12:27','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('2005','63','更新仓管通','192.168.112.102','2016-12-24 22:54:10','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('2006','63','保存仓管通明细','192.168.112.102','2016-12-24 22:54:11','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('2007','63','更新仓管通','192.168.112.102','2016-12-24 23:01:32','0','更新仓管通ID为  37 成功！','更新仓管通成功'), ('2008','63','保存仓管通明细','192.168.112.102','2016-12-24 23:01:32','0','保存仓管通明细对应主表编号为  37 成功！','保存仓管通明细成功'), ('2009','63','增加仓管通','192.168.112.102','2016-12-24 23:14:22','0','增加仓管通编号为  123132 成功！','增加仓管通成功'), ('2010','63','更新仓管通','192.168.112.102','2016-12-24 23:29:31','0','更新仓管通ID为  38 成功！','更新仓管通成功'), ('2011','63','更新仓管通','192.168.112.102','2016-12-24 23:33:25','0','更新仓管通ID为  38 成功！','更新仓管通成功'), ('2012','63','更新仓管通','192.168.112.102','2016-12-24 23:37:28','0','更新仓管通ID为  38 成功！','更新仓管通成功'), ('2013','63','保存仓管通明细','192.168.112.102','2016-12-24 23:37:29','0','保存仓管通明细对应主表编号为  38 成功！','保存仓管通明细成功'), ('2014','63','更新仓管通','192.168.112.102','2016-12-24 23:41:41','0','更新仓管通ID为  38 成功！','更新仓管通成功'), ('2015','63','保存仓管通明细','192.168.112.102','2016-12-24 23:41:41','0','保存仓管通明细对应主表编号为  38 成功！','保存仓管通明细成功'), ('2016','63','增加仓管通','192.168.112.102','2016-12-24 23:43:50','0','增加仓管通编号为  222 成功！','增加仓管通成功'), ('2017','63','保存仓管通明细','192.168.112.102','2016-12-24 23:43:51','0','保存仓管通明细对应主表编号为  39 成功！','保存仓管通明细成功'), ('2018','63','更新仓管通','192.168.112.102','2016-12-24 23:44:16','0','更新仓管通ID为  39 成功！','更新仓管通成功'), ('2019','63','保存仓管通明细','192.168.112.102','2016-12-24 23:44:17','0','保存仓管通明细对应主表编号为  39 成功！','保存仓管通明细成功'), ('2020','63','更新仓管通','192.168.112.102','2016-12-24 23:46:56','0','更新仓管通ID为  39 成功！','更新仓管通成功'), ('2021','63','保存仓管通明细','192.168.112.102','2016-12-24 23:46:57','0','保存仓管通明细对应主表编号为  39 成功！','保存仓管通明细成功');
INSERT INTO `jsh_log` VALUES ('2022','63','更新仓管通','192.168.112.102','2016-12-24 23:50:37','0','更新仓管通ID为  39 成功！','更新仓管通成功');
INSERT INTO `jsh_material` VALUES ('485','1','棉线','A21-4321','米色','码','',NULL,NULL,NULL,NULL), ('487','1','网布','12343','红色','码','',NULL,NULL,NULL,NULL), ('498','2','蕾丝','B123','蓝色','码','',NULL,NULL,NULL,NULL);
INSERT INTO `jsh_materialcategory` VALUES ('1','根目录','1','1'), ('2','花边分类','1','1');
INSERT INTO `jsh_person` VALUES ('1','仓管员','张三-仓管'), ('2','仓管员','李四-仓管'), ('3','财务员','王五-财务'), ('4','财务员','赵六-财务');
INSERT INTO `jsh_role` VALUES ('4','管理员'), ('5','仓管员');
INSERT INTO `jsh_supplier` VALUES ('1','上海某某花边工厂','','','','','1','供应商','\0',NULL,NULL,NULL,NULL), ('2','客户AAAA','','','','','1','客户','\0',NULL,NULL,NULL,NULL), ('4','苏州新源布料厂','11','22','312341@qq.com','55','1','供应商','\0','33','44',NULL,NULL);
INSERT INTO `jsh_user` VALUES ('63','季圣华','jsh','e10adc3949ba59abbe56e057f20f883e','','','','','0','1','-1','',NULL), ('64','张三','zs','e10adc3949ba59abbe56e057f20f883e','','销售','','','0','1',NULL,'',NULL), ('65','李四','ls','e10adc3949ba59abbe56e057f20f883e','','销售','','','0','1',NULL,'',NULL);
INSERT INTO `jsh_userbusiness` VALUES ('1','RoleAPP','4','[21][1][8][11][10][19][16][15][12][7][17][20][18][3][6][22][23][24][25]'), ('2','RoleAPP','5','[8][7][3][6]'), ('3','RoleAPP','6','[21][1][8]'), ('4','RoleAPP','7','[21][1][8][11]'), ('5','RoleFunctions','4','[13][12][16][14][15][22][23][25][26][194][195][31][33][200][201][41][199][202][40][197][203][204][205][206][59]'), ('6','RoleFunctions','5','[22][23][25][26][194][195][31][33][200][201][41][199][202][40][59]'), ('7','RoleFunctions','6','[168][13][12][16][14][15][189][18][19]'), ('8','RoleAPP','8','[21][1][8][11][10]'), ('9','RoleFunctions','7','[168][13][12][16][14][15][189][18][19][132]'), ('10','RoleFunctions','8','[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187]'), ('11','RoleFunctions','9','[168][13][12][16][14][15][189][18][19][132][22][23][25][26][27][157][158][155][156][125][31][127][126][128][33][34][35][36][37][39][40][41][42][43][46][47][48][49][50][51][52][53][54][55][56][57][192][59][60][61][62][63][65][66][68][69][70][71][73][74][76][77][79][191][81][82][83][85][89][161][86][176][165][160][28][134][91][92][29][94][95][97][104][99][100][101][102][105][107][108][110][111][113][114][116][117][118][120][121][131][135][123][122][20][130][146][147][138][148][149][153][140][145][184][152][143][170][171][169][166][167][163][164][172][173][179][178][181][182][183][186][187][188]'), ('12','UserRole','1','[5]'), ('13','UserRole','2','[6][7]'), ('14','UserDepot','2','[1][2][6][7]'), ('15','UserDepot','1','[1][2][5][6][7][10][12][14][15][17]'), ('16','UserRole','63','[4]'), ('17','RoleFunctions','13','[46][47][48][49]'), ('18','UserDepot','63','[1][3]'), ('19','UserDepot','5','[6][45][46][50]'), ('20','UserRole','5','[5]'), ('21','UserRole','64','[5]'), ('22','UserDepot','64','[1]'), ('23','UserRole','65','[5]'), ('24','UserDepot','65','[1]');
