package com.jsh.erp.constants;

/**
 * @ClassName:BusinessConstants
 * @Description 业务字典类
 * @Author qiankunpingtai
 * @Date 2019-3-6 17:58
 * @Version 1.0
 **/
public class BusinessConstants {

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认的分页起始页页码
     */
    public static final Integer DEFAULT_PAGINATION_PAGE_NUMBER = 1;
    /**
     * 无数据时列表返回的默认数据条数
     */
    public static final Long DEFAULT_LIST_NULL_NUMBER = 0L;
    /**
     * 默认的分页条数
     */
    public static final Integer DEFAULT_PAGINATION_PAGE_SIZE = 10;
    /**
     * 单据主表出入库类型 type 入库 出库
     * depothead
     * */
    public static final String DEPOTHEAD_TYPE_STORAGE = "入库";
    public static final String DEPOTHEAD_TYPE_OUT = "出库";
    /**
     * 付款类型 payType //现付/预付款
     * */
    public static final String PAY_TYPE_PREPAID = "预付款";
    public static final String PAY_TYPE_BY_CASH = "现付";
    /**
     * 删除标记 deleteFlag  '0'未删除 '1'已删除
     * */
    public static final String DELETE_FLAG_DELETED = "1";
    public static final String DELETE_FLAG_EXISTS = "0";
    /**
     * 是否卖出 isSell  '0'未卖出 '1'已卖出
     * */
    public static final String IS_SELL_SELLED = "1";
    public static final String IS_SELL_HOLD = "0";
    /**
     * 商品是否开启序列号标识enableSerialNumber  '0'未启用 '1'启用
     * */
    public static final String ENABLE_SERIAL_NUMBER_ENABLED = "1";
    public static final String ENABLE_SERIAL_NUMBER_NOT_ENABLED = "0";
    /**
     * 单据状态 billsStatus '0'未审核 '1'审核 '2'已转采购|销售
     * */
    public static final String BILLS_STATUS_UN_AUDIT = "0";
    public static final String BILLS_STATUS_AUDIT = "1";
    public static final String BILLS_STATUS_SKIP = "2";
    /**
     * 出入库分类
     *采购、采购退货、其它、零售、销售、调拨
     * */
    public static final String SUB_TYPE_PURCHASE = "采购";
    public static final String SUB_TYPE_PURCHASE_TETURNS = "采购退货";
    public static final String SUB_TYPE_OTHER = "其它";
    public static final String SUB_TYPE_RETAIL = "零售";
    public static final String SUB_TYPE_SALES = "销售";
    public static final String SUB_TYPE_TRANSFER = "调拨";
    /**
     * 批量插入sql时最大的数据条数
     * */
    public static final int BATCH_INSERT_MAX_NUMBER = 500;
    /**
     * sequence名称
     * */
    //sequence返回字符串的最小长度
    public static final Long SEQ_TO_STRING_MIN_LENGTH = 10000000000L;
    //sequence长度小于基准长度时前追加基础值
    public static final String SEQ_TO_STRING_LESS_INSERT = "0";
    //单据编号
    public static final String DEPOT_NUMBER_SEQ = "depot_number_seq";
    /**
     * 商品类别根目录id
     * */
    /**
     * create by: qiankunpingtai
     * create time: 2019/3/14 11:41
     * description: 
     * 为了使用户可以自己建初始目录，设定根目录的父级目录id为-1
     *
     */
    public static final Long MATERIAL_CATEGORY_ROOT_PARENT_ID = -1L;
    /**
     * 商品类别状态
     * 0系统默认，1启用，2删除
     * */
    public static final String MATERIAL_CATEGORY_STATUS_DEFAULT = "0";
    public static final String MATERIAL_CATEGORY_STATUS_ENABLE = "1";
    public static final String MATERIAL_CATEGORY_STATUS_DELETE = "2";
    /**
     * 机构状态
     *  1未营业、2正常营业、3暂停营业、4终止营业,5已除名
     * */
    public static final String ORGANIZATION_STCD_NOT_OPEN = "1";
    public static final String ORGANIZATION_STCD_OPEN = "2";
    public static final String ORGANIZATION_STCD_BUSINESS_SUSPENDED = "3";
    public static final String ORGANIZATION_STCD_BUSINESS_TERMINATED = "4";
    public static final String ORGANIZATION_STCD_REMOVED = "5";
    /**
     * 根机构父级编号
     * 根机父级构编号默认为-1
     * */
    public static final String ORGANIZATION_ROOT_PARENT_NO = "-1";
    /**
     * 新增用户默认密码
     * */
    public static final String USER_DEFAULT_PASSWORD = "123456";
    /**
     * 用户是否系统自带
     * 0、非系统自带，1系统自带
     * */
    public static final byte USER_NOT_SYSTEM = 0;
    public static final byte USER_IS_SYSTEM = 1;
    /**
     * 用户是否为管理者
     * 0、管理者，1员工
     * */
    public static final byte USER_IS_MANAGER = 0;
    public static final byte USER_NOT_MANAGER = 1;
    /**
     * 用户状态
     * 0：正常，1：删除，2封禁
     * */
    public static final byte USER_STATUS_NORMAL = 0;
    public static final byte USER_STATUS_DELETE = 1;
    public static final byte USER_STATUS_BANNED = 2;
    /**
     * 日志操作
     * 新增、修改、删除
     * */
    public static final String LOG_OPERATION_TYPE_ADD = "新增";
    public static final String LOG_OPERATION_TYPE_BATCH_ADD = "批量新增";
    public static final String LOG_OPERATION_TYPE_EDIT = "修改,id:";
    public static final String LOG_OPERATION_TYPE_DELETE = "删除,id:";
    public static final String LOG_OPERATION_TYPE_IMPORT = "导入";

    /**
     * 数据数量单位
     * 条
     * */
    public static final String LOG_DATA_UNIT = "条";
    /**
     * 模块中文名称
     * 模块对应的标识
     * */
    public static final String LOG_MODULE_NAME_USER= "用户";
    public static final String LOG_INTERFACE_NAME_USER= "user";
    public static final String LOG_MODULE_NAME_ROLE= "角色";
    public static final String LOG_INTERFACE_NAME_ROLE= "role";
    public static final String LOG_MODULE_NAME_APP= "应用";
    public static final String LOG_INTERFACE_NAME_APP= "app";
    public static final String LOG_MODULE_NAME_DEPOT= "仓库";
    public static final String LOG_INTERFACE_NAME_DEPOT= "depot";
    public static final String LOG_MODULE_NAME_FUNCTIONS= "功能";
    public static final String LOG_INTERFACE_NAME_FUNCTIONS= "functions";
    public static final String LOG_MODULE_NAME_IN_OUT_ITEM= "收支项目";
    public static final String LOG_INTERFACE_NAME_IN_OUT_ITEM= "inOutItem";
    public static final String LOG_MODULE_NAME_UNIT= "计量单位";
    public static final String LOG_INTERFACE_NAME_UNIT= "unit";
    public static final String LOG_MODULE_NAME_PERSON= "经手人";
    public static final String LOG_INTERFACE_NAME_PERSON= "person";
    public static final String LOG_MODULE_NAME_USER_BUSINESS= "关联关系";
    public static final String LOG_INTERFACE_NAME_USER_BUSINESS= "userBusiness";
    public static final String LOG_MODULE_NAME_SYSTEM_CONFIG= "系统配置";
    public static final String LOG_INTERFACE_NAME_SYSTEM_CONFIG= "systemConfig";
    public static final String LOG_MODULE_NAME_MATERIAL_PROPERTY= "商品属性";
    public static final String LOG_INTERFACE_NAME_MATERIAL_PROPERTY= "materialProperty";
    public static final String LOG_MODULE_NAME_ACCOUNT= "账户";
    public static final String LOG_INTERFACE_NAME_ACCOUNT= "account";
    public static final String LOG_MODULE_NAME_SUPPLIER= "商家";
    public static final String LOG_INTERFACE_NAME_SUPPLIER= "supplier";
    public static final String LOG_MODULE_NAME_MATERIAL_CATEGORY= "商品类型";
    public static final String LOG_INTERFACE_NAME_MATERIAL_CATEGORY= "materialCategory";
    public static final String LOG_MODULE_NAME_MATERIAL= "商品";
    public static final String LOG_INTERFACE_NAME_MATERIAL= "material";
    public static final String LOG_MODULE_NAME_DEPOT_HEAD= "单据";
    public static final String LOG_INTERFACE_NAME_DEPOT_HEAD= "depotHead";
    public static final String LOG_MODULE_NAME_DEPOT_ITEM= "单据明细";
    public static final String LOG_INTERFACE_NAME_DEPOT_ITEM= "depotItem";
    public static final String LOG_MODULE_NAME_ACCOUNT_HEAD= "财务";
    public static final String LOG_INTERFACE_NAME_ACCOUNT_HEAD= "accountHead";
    public static final String LOG_MODULE_NAME_ACCOUNT_ITEM= "财务明细";
    public static final String LOG_INTERFACE_NAME_ACCOUNT_ITEM= "accountItem";
    public static final String LOG_MODULE_NAME_SERIAL_NUMBER= "序列号";
    public static final String LOG_INTERFACE_NAME_SERIAL_NUMBER= "serialNumber";
    public static final String LOG_MODULE_NAME_ORGANIZATION= "机构";
    public static final String LOG_INTERFACE_NAME_ORGANIZATION= "organization";

    public static final String TYPE_NAME_ROLE_APP = "RoleAPP";
    public static final String TYPE_NAME_ROLE_FUNCTIONS = "RoleFunctions";

    /**
     * 删除类型
     * 1正常删除
     * 2强制删除
     * */
    public static final String DELETE_TYPE_NORMAL = "1";
    public static final String DELETE_TYPE_FORCE = "2";

    /**
     * 默认管理员账号
     */
    public static final String DEFAULT_MANAGER = "admin";















}
