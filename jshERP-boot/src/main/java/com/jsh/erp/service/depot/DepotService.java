package com.jsh.erp.service.depot;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DepotService {
    private Logger logger = LoggerFactory.getLogger(DepotService.class);

    @Resource
    private DepotMapper depotMapper;

    @Resource
    private DepotMapperEx depotMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;

    public Depot getDepot(long id)throws Exception {
        Depot result=null;
        try{
            result=depotMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Depot> getDepotListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Depot> list = new ArrayList<>();
        try{
            DepotExample example = new DepotExample();
            example.createCriteria().andIdIn(idList);
            list = depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Depot> getDepot()throws Exception {
        DepotExample example = new DepotExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Depot> list=null;
        try{
            list=depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Depot> getAllList()throws Exception {
        DepotExample example = new DepotExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("sort");
        List<Depot> list=null;
        try{
            list=depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<DepotEx> select(String name, Integer type, String remark, int offset, int rows)throws Exception {
        List<DepotEx> list=null;
        try{
            list=depotMapperEx.selectByConditionDepot(name, type, remark, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countDepot(String name, Integer type, String remark)throws Exception {
        Long result=null;
        try{
            result=depotMapperEx.countsByDepot(name, type, remark);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepot(JSONObject obj, HttpServletRequest request)throws Exception {
        Depot depot = JSONObject.parseObject(obj.toJSONString(), Depot.class);
        int result=0;
        try{
            depot.setType(0);
            depot.setIsDefault(false);
            result=depotMapper.insertSelective(depot);
            logService.insertLog("仓库",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(depot.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepot(JSONObject obj, HttpServletRequest request) throws Exception{
        Depot depot = JSONObject.parseObject(obj.toJSONString(), Depot.class);
        int result=0;
        try{
            result= depotMapper.updateByPrimaryKeySelective(depot);
            logService.insertLog("仓库",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(depot.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepot(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteDepotByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepot(String ids, HttpServletRequest request) throws Exception{
        return batchDeleteDepotByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotByIds(String ids)throws Exception {
        int result=0;
        String [] idArray=ids.split(",");
        //校验单据子表	jsh_depot_item
        List<DepotItem> depotItemList=null;
        try{
            depotItemList = depotItemMapperEx.getDepotItemListListByDepotIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotItemList!=null&&depotItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,DepotIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Depot> list = getDepotListByIds(ids);
        for(Depot depot: list){
            sb.append("[").append(depot.getName()).append("]");
        }
        logService.insertLog("仓库", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            result = depotMapperEx.batchDeleteDepotByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        DepotExample example = new DepotExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Depot> list=null;
        try{
            list= depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public List<Depot> findUserDepot()throws Exception{
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(0).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<Depot> list=null;
        try{
            list= depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Depot> findGiftByType(Integer type)throws Exception{
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("Sort");
        List<Depot> list=null;
        try{
            list=  depotMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateIsDefault(Long depotId) throws Exception{
        int result=0;
        try{
            //全部取消默认
            Depot allDepot = new Depot();
            allDepot.setIsDefault(false);
            DepotExample allExample = new DepotExample();
            allExample.createCriteria();
            depotMapper.updateByExampleSelective(allDepot, allExample);
            //给指定仓库设为默认
            Depot depot = new Depot();
            depot.setIsDefault(true);
            DepotExample example = new DepotExample();
            example.createCriteria().andIdEqualTo(depotId);
            depotMapper.updateByExampleSelective(depot, example);
            logService.insertLog("仓库",BusinessConstants.LOG_OPERATION_TYPE_EDIT+depotId,
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            result = 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    /**
     * 根据名称获取id
     * @param name
     */
    public Long getIdByName(String name){
        Long id = 0L;
        DepotExample example = new DepotExample();
        example.createCriteria().andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Depot> list = depotMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            id = list.get(0).getId();
        }
        return id;
    }
}
