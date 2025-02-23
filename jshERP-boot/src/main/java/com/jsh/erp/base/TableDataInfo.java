package com.jsh.erp.base;

import java.io.Serializable;

/**
 * 表格分页数据对象
 * 
 * @author ji-sheng-hua
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 消息状态码 */
    private int code;

    /** 消息状态码 */
    private Object data;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}