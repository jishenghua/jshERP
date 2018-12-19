package com.jsh.erp.service.app;

import com.jsh.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
@ResourceInfo(value = "app", type = 15)
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppResource {
}
