package com.jsh.erp.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class BusinessRunTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private Map<String, Object> data;

    public BusinessRunTimeException(int code, String reason) {
        super(reason);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", reason);
        this.code = code;
        this.data = objectMap;
    }

    public BusinessRunTimeException(int code, String reason, Throwable throwable) {
        super(reason, throwable);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", reason);
        this.code = code;
        this.data = objectMap;
    }
}
