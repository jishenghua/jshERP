package com.jsh.erp.utils;

public interface ExceptionCodeConstants {
    /**
     * 用户错误码定义
     */
    public class UserExceptionCode {
        /**
         * 用户不存在
         */
        public static final int USER_NOT_EXIST = 1;

        /**
         * 用户密码错误
         */
        public static final int USER_PASSWORD_ERROR = 2;

        /**
         * 用户被加入黑名单
         */
        public static final int BLACK_USER = 3;

        /**
         * 可以登录
         */
        public static final int USER_CONDITION_FIT = 4;

        /**
         * 访问数据库异常
         */
        public static final int USER_ACCESS_EXCEPTION = 5;

        /**
         * 租户被加入黑名单
         */
        public static final int BLACK_TENANT = 6;

        /**
         * 租户已经过期
         */
        public static final int EXPIRE_TENANT = 7;
    }
}
