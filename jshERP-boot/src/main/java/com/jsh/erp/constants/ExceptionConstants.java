package com.jsh.erp.constants;

import com.alibaba.fastjson.JSONObject;

public class ExceptionConstants {
    /**
     * code 格式 type+五位数字，例如3500000
     * ResourceInfo(value = "inOutItem", type = 35)
     *
     * */

    public static final String GLOBAL_RETURNS_CODE = "code";
    public static final String GLOBAL_RETURNS_MESSAGE = "msg";
    public static final String GLOBAL_RETURNS_DATA = "data";

    /**
     * 正常返回/操作成功
     **/
    public static final int SERVICE_SUCCESS_CODE = 200;
    public static final String SERVICE_SUCCESS_MSG = "操作成功";
    /**
     * 数据查询异常
     */
    public static final int DATA_READ_FAIL_CODE = 300;
    public static final String DATA_READ_FAIL_MSG = "数据查询异常";
    /**
     * 数据写入异常
     */
    public static final int DATA_WRITE_FAIL_CODE = 301;
    public static final String DATA_WRITE_FAIL_MSG = "数据写入异常";

    /**
     * 系统运行时未知错误
     **/
    public static final int SERVICE_SYSTEM_ERROR_CODE = 500;
    public static final String SERVICE_SYSTEM_ERROR_MSG = "未知异常";
    /**
     * 检测到存在依赖数据，是否强制删除？
     **/
    public static final int DELETE_FORCE_CONFIRM_CODE = 601;
    public static final String DELETE_FORCE_CONFIRM_MSG = "检测到存在依赖数据，不能删除！";
    /**
     * 用户信息
     * type = 5
     * */
    //添加用户信息失败
    public static final int USER_ADD_FAILED_CODE = 500000;
    public static final String USER_ADD_FAILED_MSG = "添加用户信息失败";
    //删除用户信息失败
    public static final int USER_DELETE_FAILED_CODE = 500001;
    public static final String USER_DELETE_FAILED_MSG = "删除用户信息失败";
    //修改用户信息失败
    public static final int USER_EDIT_FAILED_CODE = 500002;
    public static final String USER_EDIT_FAILED_MSG = "修改用户信息失败";
    //登录名已存在
    public static final int USER_LOGIN_NAME_ALREADY_EXISTS_CODE = 500003;
    public static final String USER_LOGIN_NAME_ALREADY_EXISTS_MSG = "登录名在本系统已存在";
    //用户录入数量超出限制
    public static final int USER_OVER_LIMIT_FAILED_CODE = 500004;
    public static final String USER_OVER_LIMIT_FAILED_MSG = "用户录入数量超出限制，请联系平台管理员";
    //此用户名限制使用
    public static final int USER_NAME_LIMIT_USE_CODE = 500005;
    public static final String USER_NAME_LIMIT_USE_MSG = "此用户名限制使用";
    //启用的用户数量超出限制
    public static final int USER_ENABLE_OVER_LIMIT_FAILED_CODE = 500006;
    public static final String USER_ENABLE_OVER_LIMIT_FAILED_MSG = "启用的用户数量超出限制，请联系平台管理员";
    //租户不能被删除
    public static final int USER_LIMIT_TENANT_DELETE_CODE = 500008;
    public static final String USER_LIMIT_TENANT_DELETE_MSG = "抱歉，租户不能被删除";
    //当前机构已经存在经理
    public static final int USER_LEADER_IS_EXIST_CODE = 500009;
    public static final String USER_LEADER_IS_EXIST_MSG = "抱歉，当前机构已经存在经理";
    //验证码错误
    public static final int USER_JCAPTCHA_ERROR_CODE = 500010;
    public static final String USER_JCAPTCHA_ERROR_MSG = "验证码错误";
    //验证码已失效
    public static final int USER_JCAPTCHA_EXPIRE_CODE = 500011;
    public static final String USER_JCAPTCHA_EXPIRE_MSG = "验证码已失效";
    //验证码不能为空
    public static final int USER_JCAPTCHA_EMPTY_CODE = 500012;
    public static final String USER_JCAPTCHA_EMPTY_MSG = "验证码不能为空";

    /**
     * 角色信息
     * type = 10
     * */
    //添加角色信息失败
    public static final int ROLE_ADD_FAILED_CODE = 1000000;
    public static final String ROLE_ADD_FAILED_MSG = "添加角色信息失败";
    //删除角色信息失败
    public static final int ROLE_DELETE_FAILED_CODE = 1000001;
    public static final String ROLE_DELETE_FAILED_MSG = "删除角色信息失败";
    //修改角色信息失败
    public static final int ROLE_EDIT_FAILED_CODE = 1000002;
    public static final String ROLE_EDIT_FAILED_MSG = "修改角色信息失败";
    /**
     * 应用信息
     * type = 15
     * */
    //添加角色信息失败
    public static final int APP_ADD_FAILED_CODE = 1500000;
    public static final String APP_ADD_FAILED_MSG = "添加应用信息失败";
    //删除角色信息失败
    public static final int APP_DELETE_FAILED_CODE = 1500001;
    public static final String APP_DELETE_FAILED_MSG = "删除应用信息失败";
    //修改角色信息失败
    public static final int APP_EDIT_FAILED_CODE = 1500002;
    public static final String APP_EDIT_FAILED_MSG = "修改应用信息失败";
    /**
     *  仓库信息
     * type = 20
     * */
    //添加仓库信息失败
    public static final int DEPOT_ADD_FAILED_CODE = 2000000;
    public static final String DEPOT_ADD_FAILED_MSG = "添加仓库信息失败";
    //删除仓库信息失败
    public static final int DEPOT_DELETE_FAILED_CODE = 2000001;
    public static final String DEPOT_DELETE_FAILED_MSG = "删除仓库信息失败";
    //修改仓库信息失败
    public static final int DEPOT_EDIT_FAILED_CODE = 2000002;
    public static final String DEPOT_EDIT_FAILED_MSG = "修改仓库信息失败";

    /**
     * 功能模块信息
     * type = 30
     * */
    //添加角色信息失败
    public static final int FUNCTIONS_ADD_FAILED_CODE = 3000000;
    public static final String FUNCTIONS_ADD_FAILED_MSG = "添加功能模块信息失败";
    //删除角色信息失败
    public static final int FUNCTIONS_DELETE_FAILED_CODE = 3000001;
    public static final String FUNCTIONS_DELETE_FAILED_MSG = "删除功能模块信息失败";
    //修改角色信息失败
    public static final int FUNCTIONS_EDIT_FAILED_CODE = 3000002;
    public static final String FUNCTIONS_EDIT_FAILED_MSG = "修改功能模块信息失败";
    /**
     * 收支项目信息
     * type = 35
     * */
    //添加收支项目信息失败
    public static final int IN_OUT_ITEM_ADD_FAILED_CODE = 3500000;
    public static final String IN_OUT_ITEM_ADD_FAILED_MSG = "添加收支项目信息失败";
    //删除收支项目信息失败
    public static final int IN_OUT_ITEM_DELETE_FAILED_CODE = 3500001;
    public static final String IN_OUT_ITEM_DELETE_FAILED_MSG = "删除收支项目信息失败";
    //修改收支项目信息失败
    public static final int IN_OUT_ITEM_EDIT_FAILED_CODE = 3500002;
    public static final String IN_OUT_ITEM_EDIT_FAILED_MSG = "修改收支项目信息失败";
    /**
     *  多单位信息
     * type = 40
     * */
    //添加多单位信息失败
    public static final int UNIT_ADD_FAILED_CODE = 4000000;
    public static final String UNIT_ADD_FAILED_MSG = "添加多单位信息失败";
    //删除多单位信息失败
    public static final int UNIT_DELETE_FAILED_CODE = 4000001;
    public static final String UNIT_DELETE_FAILED_MSG = "删除多单位信息失败";
    //修改多单位信息失败
    public static final int UNIT_EDIT_FAILED_CODE = 4000002;
    public static final String UNIT_EDIT_FAILED_MSG = "修改多单位信息失败";
    /**
     *  经手人信息
     * type = 45
     * */
    //添加经手人信息失败
    public static final int PERSON_ADD_FAILED_CODE = 4500000;
    public static final String PERSON_ADD_FAILED_MSG = "添加经手人信息失败";
    //删除经手人信息失败
    public static final int PERSON_DELETE_FAILED_CODE = 4500001;
    public static final String PERSON_DELETE_FAILED_MSG = "删除经手人信息失败";
    //修改经手人信息失败
    public static final int PERSON_EDIT_FAILED_CODE = 4500002;
    public static final String PERSON_EDIT_FAILED_MSG = "修改经手人信息失败";
    /**
     * 用户角色模块关系信息
     * type = 50
     * */
    //添加用户角色模块关系信息失败
    public static final int USER_BUSINESS_ADD_FAILED_CODE = 5000000;
    public static final String USER_BUSINESS_ADD_FAILED_MSG = "添加用户角色模块关系信息失败";
    //删除用户角色模块关系信息失败
    public static final int USER_BUSINESS_DELETE_FAILED_CODE = 5000001;
    public static final String USER_BUSINESS_DELETE_FAILED_MSG = "删除用户角色模块关系信息失败";
    //修改用户角色模块关系信息失败
    public static final int USER_BUSINESS_EDIT_FAILED_CODE = 5000002;
    public static final String USER_BUSINESS_EDIT_FAILED_MSG = "修改用户角色模块关系信息失败";
    /**
     *  系统参数信息
     * type = 55
     * */
    //添加系统参数信息失败
    public static final int SYSTEM_CONFIG_ADD_FAILED_CODE = 5500000;
    public static final String SYSTEM_CONFIG_ADD_FAILED_MSG = "添加系统参数信息失败";
    //删除系统参数信息失败
    public static final int SYSTEM_CONFIG_DELETE_FAILED_CODE = 5500001;
    public static final String SYSTEM_CONFIG_DELETE_FAILED_MSG = "删除系统参数信息失败";
    //修改系统参数信息失败
    public static final int SYSTEM_CONFIG_EDIT_FAILED_CODE = 5500002;
    public static final String SYSTEM_CONFIG_EDIT_FAILED_MSG = "修改系统参数信息失败";
    /**
     * 商品扩展信息
     * type = 60
     * */
    //添加商品扩展信息失败
    public static final int MATERIAL_PROPERTY_ADD_FAILED_CODE = 6000000;
    public static final String MATERIAL_PROPERTY_ADD_FAILED_MSG = "添加商品扩展信息失败";
    //删除商品扩展信息失败
    public static final int MATERIAL_PROPERTY_DELETE_FAILED_CODE = 6000001;
    public static final String MATERIAL_PROPERTY_DELETE_FAILED_MSG = "删除商品扩展信息失败";
    //修改商品扩展信息失败
    public static final int MATERIAL_PROPERTY_EDIT_FAILED_CODE = 6000002;
    public static final String MATERIAL_PROPERTY_EDIT_FAILED_MSG = "修改商品扩展信息失败";
    /**
     *  账户信息
     * type = 65
     * */
    //添加账户信息失败
    public static final int ACCOUNT_ADD_FAILED_CODE = 6500000;
    public static final String ACCOUNT_ADD_FAILED_MSG = "添加账户信息失败";
    //删除账户信息失败
    public static final int ACCOUNT_DELETE_FAILED_CODE = 6500001;
    public static final String ACCOUNT_DELETE_FAILED_MSG = "删除账户信息失败";
    //修改账户信息失败
    public static final int ACCOUNT_EDIT_FAILED_CODE = 6500002;
    public static final String ACCOUNT_EDIT_FAILED_MSG = "修改账户信息失败";
    /**
     *  供应商信息
     * type = 70
     * */
    //添加供应商信息失败
    public static final int SUPPLIER_ADD_FAILED_CODE = 7000000;
    public static final String SUPPLIER_ADD_FAILED_MSG = "添加供应商信息失败";
    //删除供应商信息失败
    public static final int SUPPLIER_DELETE_FAILED_CODE = 7000001;
    public static final String SUPPLIER_DELETE_FAILED_MSG = "删除供应商信息失败";
    //修改供应商信息失败
    public static final int SUPPLIER_EDIT_FAILED_CODE = 7000002;
    public static final String SUPPLIER_EDIT_FAILED_MSG = "修改供应商信息失败";
    /**
     * 商品类别信息
     * type = 75
     * */
    //添加商品类别信息失败
    public static final int MATERIAL_CATEGORY_ADD_FAILED_CODE = 7500000;
    public static final String MATERIAL_CATEGORY_ADD_FAILED_MSG = "添加商品类别信息失败";
    //删除商品类别信息失败
    public static final int MATERIAL_CATEGORY_DELETE_FAILED_CODE = 7500001;
    public static final String MATERIAL_CATEGORY_DELETE_FAILED_MSG = "删除商品类别信息失败";
    //修改商品类别信息失败
    public static final int MATERIAL_CATEGORY_EDIT_FAILED_CODE = 7500002;
    public static final String MATERIAL_CATEGORY_EDIT_FAILED_MSG = "修改商品类别信息失败";
    //商品类别编号已存在
    public static final int MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE = 7500003;
    public static final String MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG = "商品类别编号已存在";
    //根类别不支持修改
    public static final int MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_EDIT_CODE = 7500004;
    public static final String MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_EDIT_MSG = "根类别不支持修改";
    //根类别不支持删除
    public static final int MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_DELETE_CODE = 7500005;
    public static final String MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_DELETE_MSG = "根类别不支持删除";
    //该类别存在下级不允许删除
    public static final int MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_CODE = 7500006;
    public static final String MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_MSG = "该类别存在下级不允许删除";
    /**
     * 商品信息
     * type = 80
     * */
    //商品信息不存在
    public static final int MATERIAL_NOT_EXISTS_CODE = 8000000;
    public static final String MATERIAL_NOT_EXISTS_MSG = "商品信息不存在";
    //商品信息不唯一
    public static final int MATERIAL_NOT_ONLY_CODE = 8000001;
    public static final String MATERIAL_NOT_ONLY_MSG = "商品信息不唯一";
    //该商品未开启序列号
    public static final int MATERIAL_NOT_ENABLE_SERIAL_NUMBER_CODE = 8000002;
    public static final String MATERIAL_NOT_ENABLE_SERIAL_NUMBER_MSG = "该商品未开启序列号功能";
    //商品的序列号不能为空
    public static final int MATERIAL_SERIAL_NUMBERE_EMPTY_CODE = 8000003;
    public static final String MATERIAL_SERIAL_NUMBERE_EMPTY_MSG = "抱歉，商品条码:%s的序列号不能为空";
    //商品库存不足
    public static final int MATERIAL_STOCK_NOT_ENOUGH_CODE = 8000004;
    public static final String MATERIAL_STOCK_NOT_ENOUGH_MSG = "商品:%s库存不足";
    //商品条码重复
    public static final int MATERIAL_BARCODE_EXISTS_CODE = 8000005;
    public static final String MATERIAL_BARCODE_EXISTS_MSG = "商品条码:%s重复";
    //商品-单位匹配不上
    public static final int MATERIAL_UNIT_MATE_CODE = 8000006;
    public static final String MATERIAL_UNIT_MATE_MSG = "抱歉，商品条码:%s的单位匹配不上，请完善多单位信息！";
    //商品条码长度应该为4到40位
    public static final int MATERIAL_BARCODE_LENGTH_ERROR_CODE = 8000007;
    public static final String MATERIAL_BARCODE_LENGTH_ERROR_MSG = "商品条码:%s的长度应该为4到40位";
    //序列号和批号只能有一项
    public static final int MATERIAL_ENABLE_MUST_ONE_CODE = 8000008;
    public static final String MATERIAL_ENABLE_MUST_ONE_MSG = "抱歉，商品条码:%s的序列号和批号不能同时填1";
    //抱歉，文件扩展名必须为xls
    public static final int MATERIAL_EXTENSION_ERROR_CODE = 8000009;
    public static final String MATERIAL_EXTENSION_ERROR_MSG = "抱歉，文件扩展名必须为xls";
    //名称为空
    public static final int MATERIAL_NAME_EMPTY_CODE = 8000010;
    public static final String MATERIAL_NAME_EMPTY_MSG = "第%s行名称为空";
    //基本单位为空
    public static final int MATERIAL_UNIT_EMPTY_CODE = 8000011;
    public static final String MATERIAL_UNIT_EMPTY_MSG = "第%s行基本单位为空";
    //状态格式错误
    public static final int MATERIAL_ENABLED_ERROR_CODE = 8000012;
    public static final String MATERIAL_ENABLED_ERROR_MSG = "第%s行状态格式错误";
    //单次导入超出1000条
    public static final int MATERIAL_IMPORT_OVER_LIMIT_CODE = 8000013;
    public static final String MATERIAL_IMPORT_OVER_LIMIT_MSG = "抱歉，单次导入不能超出1000条";
    //基础重量格式错误
    public static final int MATERIAL_WEIGHT_NOT_DECIMAL_CODE = 8000014;
    public static final String MATERIAL_WEIGHT_NOT_DECIMAL_MSG = "第%s行基础重量格式错误";
    //保质期格式错误
    public static final int MATERIAL_EXPIRY_NUM_NOT_INTEGER_CODE = 8000015;
    public static final String MATERIAL_EXPIRY_NUM_NOT_INTEGER_MSG = "第%s行保质期格式错误";
    //比例格式错误
    public static final int MATERIAL_RATIO_NOT_INTEGER_CODE = 8000016;
    public static final String MATERIAL_RATIO_NOT_INTEGER_MSG = "第%s行比例格式错误";
    //组装拆卸单不能选择批号或序列号商品
    public static final int MATERIAL_ASSEMBLE_SELECT_ERROR_CODE = 80000017;
    public static final String MATERIAL_ASSEMBLE_SELECT_ERROR_MSG = "抱歉，组装拆卸单不能选择批号或序列号商品:%s";
    //调拨单不能选择批号或序列号商品
    public static final int MATERIAL_TRANSFER_SELECT_ERROR_CODE = 80000018;
    public static final String MATERIAL_TRANSFER_SELECT_ERROR_MSG = "抱歉，调拨单不能选择批号或序列号商品:%s，建议走其它入库和出库单";
    //盘点业务不能选择批号或序列号商品
    public static final int MATERIAL_STOCK_CHECK_ERROR_CODE = 80000019;
    public static final String MATERIAL_STOCK_CHECK_ERROR_MSG = "抱歉，盘点业务不能选择批号或序列号商品:%s，建议走其它入库和出库单";
    //EXCEL中存在重复的商品
    public static final int MATERIAL_EXCEL_IMPORT_EXIST_CODE = 80000020;
    public static final String MATERIAL_EXCEL_IMPORT_EXIST_MSG = "抱歉，EXCEL中存在重复的商品，具体信息为：%s";
    //EXCEL中存在重复的条码
    public static final int MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_CODE = 80000021;
    public static final String MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_MSG = "抱歉，EXCEL中存在重复的条码，具体条码为：%s";
    //名称长度超出
    public static final int MATERIAL_NAME_OVER_CODE = 8000022;
    public static final String MATERIAL_NAME_OVER_MSG = "第%s行名称长度超出100个字符";
    //规格长度超出
    public static final int MATERIAL_STANDARD_OVER_CODE = 8000023;
    public static final String MATERIAL_STANDARD_OVER_MSG = "第%s行规格长度超出100个字符";
    //型号长度超出
    public static final int MATERIAL_MODEL_OVER_CODE = 8000024;
    public static final String MATERIAL_MODEL_OVER_MSG = "第%s行型号长度超出100个字符";
    //多属性商品不能输入库存，建议进行盘点录入
    public static final int MATERIAL_SKU_BEGIN_STOCK_FAILED_CODE = 8000025;
    public static final String MATERIAL_SKU_BEGIN_STOCK_FAILED_MSG = "多属性商品%s不能输入库存，建议进行盘点录入";
    //商品条码不存在，请重新选择
    public static final int MATERIAL_BARCODE_IS_NOT_EXIST_CODE = 8000026;
    public static final String MATERIAL_BARCODE_IS_NOT_EXIST_MSG = "商品条码%s不存在，请重新选择";
    //基本条码为空
    public static final int MATERIAL_BARCODE_EMPTY_CODE = 8000027;
    public static final String MATERIAL_BARCODE_EMPTY_MSG = "第%s行基本条码为空";
    //EXCEL中有副条码在系统中已存在（除自身商品之外）
    public static final int MATERIAL_EXCEL_IMPORT_MANY_BARCODE_EXIST_CODE = 80000028;
    public static final String MATERIAL_EXCEL_IMPORT_MANY_BARCODE_EXIST_MSG = "抱歉，EXCEL中有副条码在系统中已存在，具体副条码为：%s";

    /**
     *  单据信息
     * type = 85
     * */
    //添加单据信息失败
    public static final int DEPOT_HEAD_ADD_FAILED_CODE = 8500000;
    public static final String DEPOT_HEAD_ADD_FAILED_MSG = "添加单据信息失败";
    //删除单据信息失败
    public static final int DEPOT_HEAD_DELETE_FAILED_CODE = 8500001;
    public static final String DEPOT_HEAD_DELETE_FAILED_MSG = "删除单据信息失败";
    //修改单据信息失败
    public static final int DEPOT_HEAD_EDIT_FAILED_CODE = 8500002;
    public static final String DEPOT_HEAD_EDIT_FAILED_MSG = "修改单据信息失败";
    //单据录入-仓库不能为空
    public static final int DEPOT_HEAD_DEPOT_FAILED_CODE = 8500004;
    public static final String DEPOT_HEAD_DEPOT_FAILED_MSG = "仓库不能为空";
    //单据录入-调入仓库不能为空
    public static final int DEPOT_HEAD_ANOTHER_DEPOT_FAILED_CODE = 8500005;
    public static final String DEPOT_HEAD_ANOTHER_DEPOT_FAILED_MSG = "调入仓库不能为空";
    //单据录入-明细不能为空
    public static final int DEPOT_HEAD_ROW_FAILED_CODE = 8500006;
    public static final String DEPOT_HEAD_ROW_FAILED_MSG = "单据明细不能为空";
    //单据录入-账户不能为空
    public static final int DEPOT_HEAD_ACCOUNT_FAILED_CODE = 8500007;
    public static final String DEPOT_HEAD_ACCOUNT_FAILED_MSG = "结算账户不能为空";
    //单据录入-请修改多账户的结算金额
    public static final int DEPOT_HEAD_MANY_ACCOUNT_FAILED_CODE = 8500008;
    public static final String DEPOT_HEAD_MANY_ACCOUNT_FAILED_MSG = "请修改多账户的结算金额";
    //单据录入-调入仓库与原仓库不能重复
    public static final int DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_CODE = 8500010;
    public static final String DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_MSG = "调入仓库与原仓库不能重复";
    //单据删除-只有未审核的单据才能删除，请先进行反审核
    public static final int DEPOT_HEAD_UN_AUDIT_DELETE_FAILED_CODE = 8500011;
    public static final String DEPOT_HEAD_UN_AUDIT_DELETE_FAILED_MSG = "抱歉，只有未审核的单据才能删除，请先进行反审核";
    //单据审核-只有未审核的单据才能审核
    public static final int DEPOT_HEAD_UN_AUDIT_TO_AUDIT_FAILED_CODE = 8500012;
    public static final String DEPOT_HEAD_UN_AUDIT_TO_AUDIT_FAILED_MSG = "抱歉，只有未审核的单据才能审核";
    //单据反审核-只有已审核的单据才能反审核
    public static final int DEPOT_HEAD_AUDIT_TO_UN_AUDIT_FAILED_CODE = 8500013;
    public static final String DEPOT_HEAD_AUDIT_TO_UN_AUDIT_FAILED_MSG = "抱歉，只有已审核的单据才能反审核";
    //单据录入-商品条码XXX的数量需要修改下
    public static final int DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_CODE = 85000014;
    public static final String DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG = "商品条码%s的数量需要修改下";
    //单据录入-商品的批号不能为空
    public static final int DEPOT_HEAD_BATCH_NUMBERE_EMPTY_CODE = 8500015;
    public static final String DEPOT_HEAD_BATCH_NUMBERE_EMPTY_MSG = "抱歉，商品条码:%s的批号不能为空";
    //单据录入-会员预付款余额不足
    public static final int DEPOT_HEAD_MEMBER_PAY_LACK_CODE = 8500016;
    public static final String DEPOT_HEAD_MEMBER_PAY_LACK_MSG = "抱歉，会员预付款余额不足";
    //单据录入-累计订金超出原订单中的订金
    public static final int DEPOT_HEAD_DEPOSIT_OVER_PRE_CODE = 8500017;
    public static final String DEPOT_HEAD_DEPOSIT_OVER_PRE_MSG = "抱歉，累计订金超出原订单中的订金";
    //单据录入-商品条码XXX的单价低于最低售价
    public static final int DEPOT_HEAD_UNIT_PRICE_LOW_CODE = 8500018;
    public static final String DEPOT_HEAD_UNIT_PRICE_LOW_MSG = "商品条码%s的单价低于最低售价";
    //单据录入-单据明细中必须要有组合件和普通子件
    public static final int DEPOT_HEAD_CHECK_ASSEMBLE_EMPTY_CODE = 8500020;
    public static final String DEPOT_HEAD_CHECK_ASSEMBLE_EMPTY_MSG = "抱歉，单据明细中必须要有组合件和普通子件";
    //单据录入-商品条码XXX的数量与序列号不一致
    public static final int DEPOT_HEAD_SN_NUMBERE_FAILED_CODE = 8500021;
    public static final String DEPOT_HEAD_SN_NUMBERE_FAILED_MSG = "抱歉，商品条码:%s的数量与序列号不一致";
    //单据录入-单据编号已经存在
    public static final int DEPOT_HEAD_BILL_NUMBER_EXIST_CODE = 8500022;
    public static final String DEPOT_HEAD_BILL_NUMBER_EXIST_MSG = "抱歉，单据编号已经存在";
    //单据录入-单据当前状态下不能修改
    public static final int DEPOT_HEAD_BILL_CANNOT_EDIT_CODE = 8500023;
    public static final String DEPOT_HEAD_BILL_CANNOT_EDIT_MSG = "抱歉，单据当前状态下不能修改";
    //单据删除-单据中的序列号已经出库，不能删除
    public static final int DEPOT_HEAD_SERIAL_IS_SELL_CODE = 8500024;
    public static final String DEPOT_HEAD_SERIAL_IS_SELL_MSG = "抱歉，单据%s的序列号已经出库，不能删除";
    //单据录入-单据附件不能超过规定数量
    public static final int DEPOT_HEAD_FILE_NUM_LIMIT_CODE = 8500025;
    public static final String DEPOT_HEAD_FILE_NUM_LIMIT_MSG = "抱歉，单据附件不能超过%s份";
    //单据录入-完成采购的单据不能进行反审核
    public static final int DEPOT_HEAD_PURCHASE_STATUS_TWO_CODE = 8500026;
    public static final String DEPOT_HEAD_PURCHASE_STATUS_TWO_MSG = "抱歉，完成采购的单据不能进行反审核";
    //单据录入-部分采购的单据不能进行反审核
    public static final int DEPOT_HEAD_PURCHASE_STATUS_THREE_CODE = 8500027;
    public static final String DEPOT_HEAD_PURCHASE_STATUS_THREE_MSG = "抱歉，部分采购的单据不能进行反审核";
    //单据录入-单据中存在部分状态，需要到新增界面关联单据
    public static final int DEPOT_ITEM_EXIST_PARTIALLY_STATUS_FAILED_CODE = 8500028;
    public static final String DEPOT_ITEM_EXIST_PARTIALLY_STATUS_FAILED_MSG = "抱歉，单据:%s是部分%s状态，需要到新增界面关联单据";
    //单据录入-关联请购单号和关联订单号不能同时录入
    public static final int DEPOT_ITEM_EXIST_REPEAT_NO_FAILED_CODE = 8500029;
    public static final String DEPOT_ITEM_EXIST_REPEAT_NO_FAILED_MSG = "抱歉，关联请购单号和关联订单号不能同时录入";
    //单据录入-单据最新状态不能进行批量操作
    public static final int DEPOT_ITEM_EXIST_NEW_STATUS_FAILED_CODE = 8500030;
    public static final String DEPOT_ITEM_EXIST_NEW_STATUS_FAILED_MSG = "抱歉，单据:%s最新状态不能进行批量操作";

    /**
     *  单据明细信息
     * type = 90
     * */
    //添加单据明细信息失败
    public static final int DEPOT_ITEM_ADD_FAILED_CODE = 9000000;
    public static final String DEPOT_ITEM_ADD_FAILED_MSG = "添加单据明细信息失败";
    //删除单据明细信息失败
    public static final int DEPOT_ITEM_DELETE_FAILED_CODE = 9000001;
    public static final String DEPOT_ITEM_DELETE_FAILED_MSG = "删除单据明细信息失败";
    //修改单据明细信息失败
    public static final int DEPOT_ITEM_EDIT_FAILED_CODE = 9000002;
    public static final String DEPOT_ITEM_EDIT_FAILED_MSG = "修改单据明细信息失败";
    //单据明细-明细中商品不存在
    public static final int DEPOT_ITEM_BARCODE_IS_NOT_EXIST_CODE = 9000003;
    public static final String DEPOT_ITEM_BARCODE_IS_NOT_EXIST_MSG = "抱歉，商品条码:%s在商品管理中不存在";
    //单据明细-明细中仓库不存在
    public static final int DEPOT_ITEM_DEPOTNAME_IS_NOT_EXIST_CODE = 9000004;
    public static final String DEPOT_ITEM_DEPOTNAME_IS_NOT_EXIST_MSG = "抱歉，仓库:%s在基础资料-仓库信息中不存在";
    //单据明细-单据中存在序列号，需要到新增界面关联单据
    public static final int DEPOT_ITEM_EXIST_SERIAL_NUMBER_FAILED_CODE = 9000005;
    public static final String DEPOT_ITEM_EXIST_SERIAL_NUMBER_FAILED_MSG = "抱歉，单据:%s里面存在序列号，需要到新增界面关联单据";
    //单据明细-单据中存在批号，需要到新增界面关联单据
    public static final int DEPOT_ITEM_EXIST_BATCH_NUMBER_FAILED_CODE = 9000006;
    public static final String DEPOT_ITEM_EXIST_BATCH_NUMBER_FAILED_MSG = "抱歉，单据:%s里面存在批号，需要到新增界面关联单据";
    //原关联单据已被修改，请重新关联
    public static final int DEPOT_ITEM_PRE_BILL_IS_CHANGE_CODE = 9000007;
    public static final String DEPOT_ITEM_PRE_BILL_IS_CHANGE_MSG = "抱歉，原关联单据已被修改，请重新关联";

    /**
     *  财务信息
     * type = 95
     * */
    //添加财务信息失败
    public static final int ACCOUNT_HEAD_ADD_FAILED_CODE = 9500000;
    public static final String ACCOUNT_HEAD_ADD_FAILED_MSG = "添加财务信息失败";
    //删除财务信息失败
    public static final int ACCOUNT_HEAD_DELETE_FAILED_CODE = 9500001;
    public static final String ACCOUNT_HEAD_DELETE_FAILED_MSG = "删除财务信息失败";
    //修改财务信息失败
    public static final int ACCOUNT_HEAD_EDIT_FAILED_CODE = 9500002;
    public static final String ACCOUNT_HEAD_EDIT_FAILED_MSG = "修改财务信息失败";
    //单据录入-明细不能为空
    public static final int ACCOUNT_HEAD_ROW_FAILED_CODE = 9500003;
    public static final String ACCOUNT_HEAD_ROW_FAILED_MSG = "单据明细不能为空";
    //单据删除-只有未审核的单据才能删除，请先进行反审核
    public static final int ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_CODE = 9500004;
    public static final String ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_MSG = "抱歉，只有未审核的单据才能删除，请先进行反审核";
    //财务信息录入-单据编号已经存在
    public static final int ACCOUNT_HEAD_BILL_NO_EXIST_CODE = 9500005;
    public static final String ACCOUNT_HEAD_BILL_NO_EXIST_MSG = "抱歉，单据编号已经存在";
    //财务信息录入-付款账户和明细中的账户重复
    public static final int ACCOUNT_HEAD_ACCOUNT_REPEAT_CODE = 9500006;
    public static final String ACCOUNT_HEAD_ACCOUNT_REPEAT_MSG = "抱歉，付款账户:%s和明细中的账户重复";
    /**
     *  财务明细信息
     * type = 100
     * */
    //添加财务明细信息失败
    public static final int ACCOUNT_ITEM_ADD_FAILED_CODE = 10000000;
    public static final String ACCOUNT_ITEM_ADD_FAILED_MSG = "添加财务明细信息失败";
    //删除财务明细信息失败
    public static final int ACCOUNT_ITEM_DELETE_FAILED_CODE = 10000001;
    public static final String ACCOUNT_ITEM_DELETE_FAILED_MSG = "删除财务明细信息失败";
    //修改财务明细信息失败
    public static final int ACCOUNT_ITEM_EDIT_FAILED_CODE = 10000002;
    public static final String ACCOUNT_ITEM_EDIT_FAILED_MSG = "修改财务明细信息失败";
    /**
     * 序列号
     * type = 105
     * */
    /**序列号已存在*/
    public static final int SERIAL_NUMBERE_ALREADY_EXISTS_CODE = 10500000;
    public static final String SERIAL_NUMBERE_ALREADY_EXISTS_MSG = "序列号:%s已存在";
    /**序列号不存在或者已经出库*/
    public static final int SERIAL_NUMBERE_NOT_EXISTS_CODE = 10500001;
    public static final String SERIAL_NUMBERE_NOT_EXISTS_MSG = "序列号:%s不存在或者已经出库";
    /**
     * 机构信息
     * type = 110
     * */
    //添加机构信息失败
    public static final int ORGANIZATION_ADD_FAILED_CODE = 11000000;
    public static final String ORGANIZATION_ADD_FAILED_MSG = "添加机构信息失败";
    //删除机构信息失败
    public static final int ORGANIZATION_DELETE_FAILED_CODE = 11000001;
    public static final String ORGANIZATION_DELETE_FAILED_MSG = "删除机构信息失败";
    //修改机构信息失败
    public static final int ORGANIZATION_EDIT_FAILED_CODE = 11000002;
    public static final String ORGANIZATION_EDIT_FAILED_MSG = "修改机构信息失败";
    //机构编号已存在
    public static final int ORGANIZATION_NO_ALREADY_EXISTS_CODE = 11000003;
    public static final String ORGANIZATION_NO_ALREADY_EXISTS_MSG = "机构编号已存在";
    //根机构不允许删除
    public static final int ORGANIZATION_ROOT_NOT_ALLOWED_DELETE_CODE = 11000004;
    public static final String ORGANIZATION_ROOT_NOT_ALLOWED_DELETE_MSG = "根机构不允许删除";
    //根机构不允许修改
    public static final int ORGANIZATION_ROOT_NOT_ALLOWED_EDIT_CODE = 11000005;
    public static final String ORGANIZATION_ROOT_NOT_ALLOWED_EDIT_MSG = "根机构不允许修改";
    //该机构存在下级不允许删除
    public static final int ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_CODE = 11000006;
    public static final String ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_MSG = "该机构存在下级不允许删除";
    /**
     * 机构用户关联关系
     * type = 115
     * */
    //添加机构用户关联关系失败
    public static final int ORGA_USER_REL_ADD_FAILED_CODE = 11500000;
    public static final String ORGA_USER_REL_ADD_FAILED_MSG = "添加机构用户关联关系失败";
    //删除机构用户关联关系失败
    public static final int ORGA_USER_REL_DELETE_FAILED_CODE = 11500001;
    public static final String ORGA_USER_REL_DELETE_FAILED_MSG = "删除机构用户关联关系失败";
    //修改机构用户关联关系失败
    public static final int ORGA_USER_REL_EDIT_FAILED_CODE = 11500002;
    public static final String ORGA_USER_REL_EDIT_FAILED_MSG = "修改机构用户关联关系失败";

    //进销存统计，如果有权限的仓库数量太多则提示要选择仓库
    public static final int REPORT_TWO_MANY_DEPOT_FAILED_CODE = 510;
    public static final String REPORT_TWO_MANY_DEPOT_FAILED_MSG = "请选择仓库，再进行查询";

    //演示用户禁止操作
    public static final int SYSTEM_CONFIG_TEST_USER_CODE = -1;
    public static final String SYSTEM_CONFIG_TEST_USER_MSG = "演示用户禁止操作";


    /**
     * 标准正常返回/操作成功返回
     * @return
     */
    public static JSONObject standardSuccess () {
        JSONObject success = new JSONObject();
        success.put(GLOBAL_RETURNS_CODE, SERVICE_SUCCESS_CODE);
        success.put(GLOBAL_RETURNS_MESSAGE, SERVICE_SUCCESS_MSG);
        return success;
    }
}
