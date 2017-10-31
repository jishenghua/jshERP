package com.jsh.dao.basic;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface AccountIDAO extends BaseIDAO<Account> {

    public void findAccountInOutList(PageUtil<Account> pageUtil, Long accountId) throws JshException;

}
