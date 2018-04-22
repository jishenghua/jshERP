package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.basic.LogIDAO;
import com.jsh.model.po.Logdetails;

public class LogService extends BaseService<Logdetails> implements LogIService {
    @SuppressWarnings("unused")
    private LogIDAO logDao;

    public void setLogDao(LogIDAO logDao) {
        this.logDao = logDao;
    }

    @Override
    protected Class<Logdetails> getEntityClass() {
        return Logdetails.class;
    }

    @Override
    public void save(Logdetails t) {
        try {
            super.save(t);
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>创建操作日志异常", e);
        }
    }
}