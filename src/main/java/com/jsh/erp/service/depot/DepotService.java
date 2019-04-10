package com.jsh.erp.service.depot;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.exception.BusinessRunTimeException;
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

    public Depot getDepot(long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> getDepot() {
        DepotExample example = new DepotExample();
        return depotMapper.selectByExample(example);
    }

    public List<Depot> getAllList() {
        DepotExample example = new DepotExample();
        example.setOrderByClause("sort");
        return depotMapper.selectByExample(example);
    }

    public List<Depot> select(String name, Integer type, String remark, int offset, int rows) {
        return depotMapperEx.selectByConditionDepot(name, type, remark, offset, rows);
    }

    public Long countDepot(String name, Integer type, String remark) {
        return depotMapperEx.countsByDepot(name, type, remark);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepot(String beanJson, HttpServletRequest request) {
        Depot depot = JSONObject.parseObject(beanJson, Depot.class);
        return depotMapper.insertSelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepot(String beanJson, Long id) {
        Depot depot = JSONObject.parseObject(beanJson, Depot.class);
        depot.setId(id);
        return depotMapper.updateByPrimaryKeySelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepot(Long id) {
        return depotMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepot(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotExample example = new DepotExample();
        example.createCriteria().andIdIn(idList);
        return depotMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotExample example = new DepotExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Depot> list = depotMapper.selectByExample(example);
        return list.size();
    }

    public List<Depot> findUserDepot(){
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(0);
        example.setOrderByClause("Sort");
        List<Depot> list = depotMapper.selectByExample(example);
        return list;
    }

    public List<Depot> findGiftByType(Integer type){
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("Sort");
        List<Depot> list = depotMapper.selectByExample(example);
        return list;
    }

    public List<DepotEx> getDepotList(Map<String, Object> parameterMap) {
        return depotMapperEx.getDepotList(parameterMap);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return depotMapperEx.batchDeleteDepotByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 16:52
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotByIdsNormal(String ids) throws Exception {
        /**
         * 校验
         * 1、单据主表	jsh_depothead
         * 2、单据子表	jsh_depotitem
         * 是否有相关数据
         * */
        int deleteTotal=0;
        if(StringUtils.isEmpty(ids)){
            return deleteTotal;
        }
        String [] idArray=ids.split(",");

        /**
         * 校验单据主表	jsh_depothead
         * */
        List<DepotHead> depotHeadList=depotHeadMapperEx.getDepotHeadListByDepotIds(idArray);
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,DepotIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验单据子表	jsh_depotitem
         * */
        List<DepotItem> depotItemList=depotItemMapperEx.getDepotItemListListByDepotIds(idArray);
        if(depotItemList!=null&&depotItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,DepotIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteDepotByIds(ids);
        return deleteTotal;

    }
}
