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
     * 系统运行时未知错误
     **/
    public static final int SERVICE_SYSTEM_ERROR_CODE = 500;
    public static final String SERVICE_SYSTEM_ERROR_MSG = "未知异常";

    /**
     * 序列号
     * type = 105
     * */
    /**序列号已存在*/
    public static final int SERIAL_NUMBERE_ALREADY_EXISTS_CODE = 10500000;
    public static final String SERIAL_NUMBERE_ALREADY_EXISTS_MSG = "序列号已存在";
    /**序列号不能为为空*/
    public static final int SERIAL_NUMBERE_NOT_BE_EMPTY_CODE = 10500000;
    public static final String SERIAL_NUMBERE_NOT_BE_EMPTY_MSG = "序列号不能为为空";
    /**商品%s下序列号不充足，请补充后重试*/
    public static final int MATERIAL_SERIAL_NUMBERE_NOT_ENOUGH_CODE = 10500000;
    public static final String MATERIAL_SERIAL_NUMBERE_NOT_ENOUGH_MSG = "商品:%s下序列号不充足，请补充后重试";



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
    //该商品已绑定序列号数量小于等于商品现有库存
    public static final int MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_CODE = 8000003;
    public static final String MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_MSG = "该商品已绑定序列号数量大于等于商品现有库存";

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
