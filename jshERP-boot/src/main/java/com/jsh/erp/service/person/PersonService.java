package com.jsh.erp.service.person;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.AccountHead;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.datasource.entities.PersonExample;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.PersonMapper;
import com.jsh.erp.datasource.mappers.PersonMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;

    public Person getPerson(long id)throws Exception {
        Person result=null;
        try{
            result=personMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Person> getPersonListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Person> list = new ArrayList<>();
        try{
            PersonExample example = new PersonExample();
            example.createCriteria().andIdIn(idList);
            list = personMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Person> getPerson()throws Exception {
        PersonExample example = new PersonExample();
        example.createCriteria().andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Person> list=null;
        try{
            list=personMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Person> select(String name, String type, int offset, int rows)throws Exception {
        List<Person> list=null;
        try{
            list=personMapperEx.selectByConditionPerson(name, type, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countPerson(String name, String type)throws Exception {
        Long result=null;
        try{
            result=personMapperEx.countsByPerson(name, type);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertPerson(JSONObject obj, HttpServletRequest request)throws Exception {
        Person person = JSONObject.parseObject(obj.toJSONString(), Person.class);
        int result=0;
        try{
            person.setEnabled(true);
            result=personMapper.insertSelective(person);
            logService.insertLog("经手人",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(person.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updatePerson(JSONObject obj, HttpServletRequest request)throws Exception {
        Person person = JSONObject.parseObject(obj.toJSONString(), Person.class);
        int result=0;
        try{
            result=personMapper.updateByPrimaryKeySelective(person);
            logService.insertLog("经手人",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(person.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deletePerson(Long id, HttpServletRequest request)throws Exception {
        return batchDeletePersonByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePerson(String ids, HttpServletRequest request) throws Exception{
        return batchDeletePersonByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePersonByIds(String ids)throws Exception {
        int result =0;
        String [] idArray=ids.split(",");
        //校验财务主表	jsh_accounthead
        List<AccountHead> accountHeadList =null;
        try{
            accountHeadList=accountHeadMapperEx.getAccountHeadListByHandsPersonIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsPersonIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //校验单据主表	jsh_depot_head
        List<DepotHead> depotHeadList =null;
        try{
            depotHeadList=depotHeadMapperEx.getDepotHeadListByCreator(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsPersonIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Person> list = getPersonListByIds(ids);
        for(Person person: list){
            sb.append("[").append(person.getName()).append("]");
        }
        logService.insertLog("经手人", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //删除经手人
        try{
            result=personMapperEx.batchDeletePersonByIds(idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name) throws Exception{
        PersonExample example = new PersonExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Person> list =null;
        try{
            list=personMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public Map<Long,String> getPersonMap() throws Exception {
        List<Person> personList = getPerson();
        Map<Long,String> personMap = new HashMap<>();
        for(Person person : personList){
            personMap.put(person.getId(), person.getName());
        }
        return personMap;
    }

    public String getPersonByMapAndIds(Map<Long,String> personMap, String personIds)throws Exception {
        List<Long> ids = StringUtil.strToLongList(personIds);
        StringBuffer sb = new StringBuffer();
        for(Long id: ids){
            sb.append(personMap.get(id) + " ");
        }
        return sb.toString();
    }

    public List<Person> getPersonByType(String type)throws Exception {
        PersonExample example = new PersonExample();
        example.createCriteria().andTypeEqualTo(type).andEnabledEqualTo(true)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("sort asc, id desc");
        List<Person> list =null;
        try{
            list=personMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("经手人",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ENABLED).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> personIds = StringUtil.strToLongList(ids);
        Person person = new Person();
        person.setEnabled(status);
        PersonExample example = new PersonExample();
        example.createCriteria().andIdIn(personIds);
        int result=0;
        try{
            result = personMapper.updateByExampleSelective(person, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
}
