package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.AccountIDAO;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public class AccountService extends BaseService<Account> implements AccountIService {
    @SuppressWarnings("unused")
    private AccountIDAO accountDao;

    public void setAccountDao(AccountIDAO accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    public void findAccountInOutList(PageUtil<Account> pageUtil, Long accountId) throws JshException {
        accountDao.findAccountInOutList(pageUtil, accountId);
    }

}
