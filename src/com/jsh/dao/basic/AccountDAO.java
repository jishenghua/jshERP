package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Account;

public class AccountDAO extends BaseDAO<Account> implements AccountIDAO
{
    /**
     * 设置dao映射基类
     * @return
     */
    @Override
    public Class<Account> getEntityClass()
    {
        return Account.class;
    }
}
