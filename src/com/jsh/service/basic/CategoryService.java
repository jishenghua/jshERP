package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.CategoryIDAO;
import com.jsh.model.po.Category;

public class CategoryService extends BaseService<Category> implements CategoryIService
{
	@SuppressWarnings("unused")
	private CategoryIDAO categoryDao;

	public void setCategoryDao(CategoryIDAO categoryDao)
    {
        this.categoryDao = categoryDao;
    }

	@Override
	protected Class<Category> getEntityClass()
	{
		return Category.class;
	}
    
}
