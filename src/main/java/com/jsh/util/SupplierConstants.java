package com.jsh.util;

/**
 * 定义供应商、客户管理常量
 * @author jishenghua
 */
public interface SupplierConstants
{
    /**
     * 公共常量
     * @author jishenghua
     */
    public class Common
    {

    }

    /**
     * 常量--导入导出excel表格业务相关
     * @author jishenghua
     */
    public class BusinessForExcel
    {
        /**
         * 名称
         */
        public static final int EXCEL_SUPPLIER = 0;

        /**
         * 类型
         */
        public static final int EXCEL_TYPE = 1;

        /**
         * 联系人
         */
        public static final int EXCEL_CONTACTS = 2;

        /**
         * 电话
         */
        public static final int EXCEL_PHONE_NUM = 3;

        /**
         * 电子邮箱
         */
        public static final int EXCEL_EMAIL = 4;

        /**
         * 预收款
         */
        public static final int EXCEL_ADVANCE_IN = 5;

        /**
         * 期初应收
         */
        public static final int EXCEL_BEGIN_NEED_GET = 6;

        /**
         * 期初应付
         */
        public static final int EXCEL_BEGIN_NEED_PAY = 7;

        /**
         * 备注
         */
        public static final int EXCEL_DESCRIPTION = 8;

        /**
         * 传真
         */
        public static final int EXCEL_FAX = 9;

        /**
         * 手机
         */
        public static final int EXCEL_TELEPHONE = 10;

        /**
         * 地址
         */
        public static final int EXCEL_ADDRESS = 11;

        /**
         * 纳税人识别号
         */
        public static final int EXCEL_TAX_NUM = 12;

        /**
         * 开户行
         */
        public static final int EXCEL_BANK_NAME = 13;

        /**
         * 账号
         */
        public static final int EXCEL_ACCOUNT_NUMBER = 14;

        /**
         * 税率
         */
        public static final int EXCEL_TAX_RATE = 15;


        /**
         * 表头
         */
        public static final int EXCEL_TABLE_HEAD = 0;

        /**
         * action返回excel结果
         */
        public static final String EXCEL = "excel";
    }
}
