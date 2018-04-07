package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.AppIDAO;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.App;

public class AppService extends BaseService<App> implements AppIService {
    @SuppressWarnings("unused")
    private AppIDAO appDao;
    @SuppressWarnings("unused")
    private UserBusinessIDAO userBusinessDao;


    public void setAppDao(AppIDAO appDao) {
        this.appDao = appDao;
    }

    public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
        this.userBusinessDao = userBusinessDao;
    }

    @Override
    protected Class<App> getEntityClass() {
        return App.class;
    }

}
