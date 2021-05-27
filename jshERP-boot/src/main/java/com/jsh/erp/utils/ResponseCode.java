package com.jsh.erp.utils;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class ResponseCode {

    public final int code;
    public final Object data;

    /**
     *
     * @param code
     * @param data
     */
    @JSONCreator
    public ResponseCode(@JSONField(name = "code") int code, @JSONField(name = "data")Object data) {
        this.code = code;
        this.data = data;
    }
}