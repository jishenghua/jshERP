package com.jsh.erp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ResponseJsonUtil {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static {
        FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    /**
     * 响应过滤器
     */
    public static final class ResponseFilter extends ExtJsonUtils.ExtFilter implements ValueFilter {
        @Override
        public Object process(Object object, String name, Object value) {
            if (name.equals("createTime") || name.equals("modifyTime")||name.equals("updateTime")) {
                return value;
            } else if (value instanceof Date) {
                return FORMAT.format(value);
            } else {
                return value;
            }
        }
    }

    /**
     *
     * @param responseCode
     * @return
     */
    public static String backJson4HttpApi(ResponseCode responseCode) {
        if (responseCode != null) {
            String result = JSON.toJSONString(responseCode, new ResponseFilter(),
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteNonStringKeyAsString);
            result = result.replaceFirst("\"data\":\\{", "");
            return result.substring(0, result.length() - 1);
        }
        return null;
    }

    /**
     * 验证失败的json串
     * @param code
     * @return
     */
    public static String backJson4VerifyFailure(int code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "未通过验证");
        return JSON.toJSONString(new ResponseCode(code, map), new ResponseFilter(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNonStringKeyAsString);
    }

    /**
     * 成功的json串
     * @param responseCode
     * @return
     */
    public static String backJson(ResponseCode responseCode) {
        if (responseCode != null) {
            return JSON.toJSONString(responseCode, new ResponseFilter(),
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteNonStringKeyAsString);
        }
        return null;
    }

    public static String returnJson(Map<String, Object> map, String message, int code) {
        map.put("message", message);
        return backJson(new ResponseCode(code, map));
    }
}
