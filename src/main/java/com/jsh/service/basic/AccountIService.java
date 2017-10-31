package com.jsh.service.basic;

import com.jsh.base.BaseIService;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface AccountIService extends BaseIService<Account>
{
    public void findAccountInOutList(PageUtil<Account> depotHead, Long accountId)throws JshException;
}
