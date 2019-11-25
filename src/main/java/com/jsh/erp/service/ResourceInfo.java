package com.jsh.erp.service;

import java.lang.annotation.*;

/**
 * @author jishenghua 2018-10-7 15:25:39
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResourceInfo {
    String value();
    int type();
}