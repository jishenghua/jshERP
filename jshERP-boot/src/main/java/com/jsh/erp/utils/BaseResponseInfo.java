package com.jsh.erp.utils;

/**
 * 返回结果
 *
 * @author ji-sheng-hua
 */
public class BaseResponseInfo {
	public int code;
	public Object data;
	
	public BaseResponseInfo() {
		code = 400;
		data = null;
	}
}
