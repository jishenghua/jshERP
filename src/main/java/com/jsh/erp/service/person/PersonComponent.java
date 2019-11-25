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
    public Object selectOne(Long id) throws Exception {
        return personService.getPerson(id);
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
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return personService.updatePerson(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return personService.deletePerson(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return personService.batchDeletePerson(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return personService.checkIsNameExist(id, name);
    }

}
