package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.PersonIDAO;
import com.jsh.model.po.Person;

public class PersonService extends BaseService<Person> implements PersonIService {
    @SuppressWarnings("unused")
    private PersonIDAO personDao;


    public void setPersonDao(PersonIDAO personDao) {
        this.personDao = personDao;
    }


    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

}
