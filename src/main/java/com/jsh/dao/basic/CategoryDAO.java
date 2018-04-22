package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Category;

public class CategoryDAO extends BaseDAO<Category> implements CategoryIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Category> getEntityClass() {
        return Category.class;
    }
}
