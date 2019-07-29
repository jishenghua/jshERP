package com.jsh.erp.service;

import java.lang.annotation.*;

/**
 * @author jishenghua 2018-10-7 15:25:39
 * user-5
 * role-10
 * app-15
 * depot-20
 * log-25
 * functions-30
 * inOutItem-35
 * unit-40
 * person-45
 * userBusiness-50
 * systemConfig-55
 * materialProperty-60
 * account-65
 * supplier-70
 * materialCategory-75
 * material-80
 * depotHead-85
 * depotItem-90
 * accountHead-95
 * accountItem-100
 * serialNumber-105
 * organization-110
 * orgaUserRel-115
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResourceInfo {
    String value();
    int type();
}
