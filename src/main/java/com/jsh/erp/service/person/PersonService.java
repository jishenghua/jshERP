package com.jsh.erp.service.person;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.datasource.entities.PersonExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.PersonMapper;
import com.jsh.erp.datasource.mappers.PersonMapperEx;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class PersonService {
    private Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Resource
    private PersonMapper personMapper;

    @Resource
    private PersonMapperEx personMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public Person getPerson(long id) {
        return personMapper.selectByPrimaryKey(id);
    }

    public List<Person> getPerson() {
        PersonExample example = new PersonExample();
        return personMapper.selectByExample(example);
    }

    public List<Person> select(String name, String type, int offset, int rows) {
        return personMapperEx.selectByConditionPerson(name, type, offset, rows);
    }

    public Long countPerson(String name, String type) {
        return personMapperEx.countsByPerson(name, type);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertPerson(String beanJson, HttpServletRequest request) {
        Person person = JSONObject.parseObject(beanJson, Person.class);
        return personMapper.insertSelective(person);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updatePerson(String beanJson, Long id) {
        Person person = JSONObject.parseObject(beanJson, Person.class);
        person.setId(id);
        return personMapper.updateByPrimaryKeySelective(person);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deletePerson(Long id) {
        return personMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePerson(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        PersonExample example = new PersonExample();
        example.createCriteria().andIdIn(idList);
        return personMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        PersonExample example = new PersonExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Person> list = personMapper.selectByExample(example);
        return list.size();
    }

    public String getPersonByIds(String personIDs) {
        List<Long> ids = StringUtil.strToLongList(personIDs);
        PersonExample example = new PersonExample();
        example.createCriteria().andIdIn(ids);
        example.setOrderByClause("Id asc");
        List<Person> list = personMapper.selectByExample(example);
        StringBuffer sb = new StringBuffer();
        if (null != list) {
            for (Person person : list) {
                sb.append(person.getName() + " ");
            }
        }
        return  sb.toString();
    }

    public List<Person> getPersonByType(String type) {
        PersonExample example = new PersonExample();
        example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("Id asc");
        return personMapper.selectByExample(example);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePersonByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_PERSON,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return personMapperEx.batchDeletePersonByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
