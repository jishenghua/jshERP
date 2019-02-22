-- ---------------------------------------------------------------------
-- 特别注意!!!此sql文档为数据库升级时使用，第一次安装，请使用jsh_erp.sql
-- ---------------------------------------------------------------------


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
DROP function IF EXISTS `_nextval`;
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
-- ----------------------------
update jsh_materialcategory set ParentId='-1' where id='1';

-- ----------------------------
-- 删除礼品卡管理、礼品充值、礼品销售、礼品卡统计的功能数据
-- ----------------------------
delete from jsh_functions where id in (213,214,215,216);

-- ----------------------------
-- 新增采购订单、销售订单的功能数据
-- 主键自增长，直接指定主键插入数据的方式可能会和本地数据冲突
-- ----------------------------
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`) VALUES ('050202', '采购订单', '0502', '../materials/purchase_orders_list.html', b'0', '0335',b'1', '电脑版', '');
insert into `jsh_functions`(`Number`, `Name`, `PNumber`, `URL`, `State`, `Sort`, `Enabled`, `Type`, `PushBtn`) VALUES ('060301', '销售订单', '0603', '../materials/sale_orders_list.html', b'0', '0392', b'1', '电脑版', '');