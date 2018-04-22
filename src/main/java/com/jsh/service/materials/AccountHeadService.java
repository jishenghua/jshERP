package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.AccountHeadIDAO;
import com.jsh.model.po.AccountHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public class AccountHeadService extends BaseService<AccountHead> implements AccountHeadIService {
    @SuppressWarnings("unused")
    private AccountHeadIDAO accountHeadDao;


    public void setAccountHeadDao(AccountHeadIDAO accountHeadDao) {
        this.accountHeadDao = accountHeadDao;
    }


    @Override
    protected Class<AccountHead> getEntityClass() {
        return AccountHead.class;
    }

    public void find(PageUtil<AccountHead> pageUtil, String maxid) throws JshException {
        accountHeadDao.find(pageUtil, maxid);
    }

    public void findAllMoney(PageUtil<AccountHead> pageUtil, Integer supplierId, String type, String mode) throws JshException {
        accountHeadDao.findAllMoney(pageUtil, supplierId, type, mode);
    }
}
