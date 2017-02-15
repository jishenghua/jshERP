package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Person;

public class PersonDAO extends BaseDAO<Person> implements PersonIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<Person> getEntityClass()
    {
        return Person.class;
    }
}
