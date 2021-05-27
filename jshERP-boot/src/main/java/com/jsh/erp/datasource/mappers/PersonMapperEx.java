package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.datasource.entities.PersonExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PersonMapperEx {

    List<Person> selectByConditionPerson(
            @Param("name") String name,
            @Param("type") String type,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByPerson(
            @Param("name") String name,
            @Param("type") String type);

    int batchDeletePersonByIds(@Param("ids") String ids[]);
}