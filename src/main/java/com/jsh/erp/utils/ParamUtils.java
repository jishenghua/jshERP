package com.jsh.erp.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class ParamUtils {
    public static String getPageOffset(Integer currentPage, Integer pageSize) {
        if (currentPage != null && pageSize != null) {
            int offset = (currentPage - 1) * pageSize;
            if (offset <= 0) {
                return "0";
            } else {
                return new StringBuffer().append(offset).toString();
            }
        }
        return null;
    }
    public static Integer getNumberPageOffset(Integer currentPage, Integer pageSize) {
        if (currentPage != null && pageSize != null) {
            int offset = (currentPage - 1) * pageSize;
            if (offset <= 0) {
                return 0;
            } else {
                return offset;
            }
        }
        return null;
    }
    public static Integer getNumberPageRows(Integer currentPage, Integer pageSize) {
        if (currentPage != null && pageSize != null) {
            int rows = (currentPage) * pageSize;
            if (rows <= 0) {
                return 0;
            } else {
                return rows;
            }
        }
        return null;
    }

    public static HashMap<String, String> requestToMap(HttpServletRequest request) {

        HashMap<String, String> parameterMap = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        if (names != null) {
            for (String name : Collections.list(names)) {
                parameterMap.put(name, request.getParameter(name));
                /*HttpMethod method = HttpMethod.valueOf(request.getMethod());
                if (method == GET || method == DELETE)
                    parameterMap.put(name, transcoding(request.getParameter(name)));
                else
                    parameterMap.put(name, request.getParameter(name));*/
            }
        }
        return parameterMap;
    }
}
