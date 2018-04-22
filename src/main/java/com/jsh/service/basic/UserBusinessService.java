package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public class UserBusinessService extends BaseService<UserBusiness> implements UserBusinessIService {
    @SuppressWarnings("unused")
    private UserBusinessIDAO userBusinessDao;

    public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
        this.userBusinessDao = userBusinessDao;
    }

    @Override
    protected Class<UserBusiness> getEntityClass() {
        return UserBusiness.class;
    }

    @Override
    public void find(PageUtil<UserBusiness> pageUtil, String ceshi) throws JshException {
        userBusinessDao.find(pageUtil, ceshi);
    }


}
