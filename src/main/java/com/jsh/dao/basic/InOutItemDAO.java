package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.InOutItem;

public class InOutItemDAO extends BaseDAO<InOutItem> implements InOutItemIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<InOutItem> getEntityClass() {
        return InOutItem.class;
    }
}
