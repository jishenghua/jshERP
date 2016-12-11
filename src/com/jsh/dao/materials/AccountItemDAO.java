package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.AccountItem;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;

public class AccountItemDAO extends BaseDAO<AccountItem> implements AccountItemIDAO
{
    /**
     * 设置dao映射基类
     * @return
     */
    @Override
    public Class<AccountItem> getEntityClass()
    {
        return AccountItem.class;
    }
}
