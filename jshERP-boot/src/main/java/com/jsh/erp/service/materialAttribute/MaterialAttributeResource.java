package com.jsh.erp.service.materialAttribute;

import com.jsh.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author jishenghua qq752718920  2021-07-21 22:26:27
 */
@ResourceInfo(value = "materialAttribute")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaterialAttributeResource {
}
