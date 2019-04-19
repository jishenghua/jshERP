package com.jsh.erp.service.person;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.depot.DepotResource;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "person_component")
@PersonResource
public class PersonComponent implements ICommonQuery {

    @Resource
    private PersonService personService;

    @Override
    public Object selectOne(String condition)throws Exception {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getPersonList(map);
    }

    private List<?> getPersonList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        String order = QueryUtils.order(map);
        return personService.select(name, type, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        return personService.countPerson(name, type);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request)throws Exception {
        return personService.insertPerson(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id)throws Exception {
        return personService.updatePerson(beanJson, id);
    }

    @Override
    public int delete(Long id)throws Exception {
        return personService.deletePerson(id);
    }

    @Override
    public int batchDelete(String ids)throws Exception {
        return personService.batchDeletePerson(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return personService.checkIsNameExist(id, name);
    }

}
