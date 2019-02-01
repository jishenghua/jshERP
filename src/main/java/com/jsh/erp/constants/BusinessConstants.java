package com.jsh.erp.constants;

/**
 * @ClassName:BusinessConstants
 * @Description 业务字典类
 * @Author linshengming
 * @Date 2018-9-15 17:58
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
    public static final String DEFAULT_PAGINATION_PAGE_NUMBER = "1";
    /**
     * 默认的分页页数
     */
    public static final String DEFAULT_PAGINATION_PAGE_SIZE = "10";
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
     * 出入库分类
     *采购、采购退货、其它、零售、销售、调拨、礼品充值
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
    public static final Long SEQ_TO_STRING_MIN_LENGTH = 1000000L;
    //sequence长度小于基准长度时前追加基础值
    public static final String SEQ_TO_STRING_LESS_INSERT = "0";
    //单据编号
    public static final String DEPOT_NUMBER_SEQ = "depot_number_seq";











}
