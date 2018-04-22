package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.FunctionsIDAO;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.Functions;

public class FunctionsService extends BaseService<Functions> implements FunctionsIService {
    @SuppressWarnings("unused")
    private FunctionsIDAO functionsDao;
    @SuppressWarnings("unused")
    private UserBusinessIDAO userBusinessDao;


    public void setFunctionsDao(FunctionsIDAO functionsDao) {
        this.functionsDao = functionsDao;
    }

    public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
        this.userBusinessDao = userBusinessDao;
    }

    @Override
    protected Class<Functions> getEntityClass() {
        return Functions.class;
    }

}
