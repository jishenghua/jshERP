package com.jsh.service.basic;

import com.jsh.base.BaseIService;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface UserBusinessIService extends BaseIService<UserBusiness> {
    /*
     * 测试一下自定义hql语句
     */
    void find(PageUtil<UserBusiness> userBusiness, String ceshi) throws JshException;

}
