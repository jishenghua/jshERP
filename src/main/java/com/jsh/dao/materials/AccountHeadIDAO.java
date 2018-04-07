package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.AccountHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface AccountHeadIDAO extends BaseIDAO<AccountHead> {
    /*
     * 获取MaxId
     */
    void find(PageUtil<AccountHead> pageUtil, String maxid) throws JshException;

    void findAllMoney(PageUtil<AccountHead> pageUtil, Integer supplierId, String type, String mode) throws JshException;
}
