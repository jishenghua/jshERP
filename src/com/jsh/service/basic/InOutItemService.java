package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.InOutItemIDAO;
import com.jsh.model.po.InOutItem;

public class InOutItemService extends BaseService<InOutItem> implements InOutItemIService
{
    @SuppressWarnings("unused")
    private InOutItemIDAO inOutItemDao;

    public void setInOutItemDao(InOutItemIDAO inOutItemDao)
    {
        this.inOutItemDao = inOutItemDao;
    }

    @Override
    protected Class<InOutItem> getEntityClass()
    {
        return InOutItem.class;
    }

}
