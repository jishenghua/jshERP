package com.jsh.erp.service.depot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
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
    private SystemConfigService systemConfigService;
    @Resource
    private UserBusinessService userBusinessService;
    @Resource
    private LogService logService;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;
    @Resource
    private MaterialInitialStockMapperEx materialInitialStockMapperEx;
    @Resource
    private MaterialCurrentStockMapperEx materialCurrentStockMapperEx;

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
        DepotExample example = new DepotExample();
        example.createCriteria().andIdIn(idList);
        return depotMapper.selectByExample(example);
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
        example.createCriteria().andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("sort asc, id desc");
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
            List<Depot> depotList = getDepot();
            if(depotList.size() == 0) {
                depot.setIsDefault(true);
            } else {
                depot.setIsDefault(false);
            }
            depot.setEnabled(true);
            result=depotMapper.insertSelective(depot);
            //新增仓库时给当前用户自动授权
            Long userId = userService.getUserId(request);
            Long depotId = getIdByName(depot.getName());
            String ubKey = "[" + depotId + "]";
            List<UserBusiness> ubList = userBusinessService.getBasicData(userId.toString(), "UserDepot");
            if(ubList ==null || ubList.size() == 0) {
                JSONObject ubObj = new JSONObject();
                ubObj.put("type", "UserDepot");
                ubObj.put("keyId", userId);
                ubObj.put("value", ubKey);
                userBusinessService.insertUserBusiness(ubObj, request);
            } else {
                UserBusiness ubInfo = ubList.get(0);
                JSONObject ubObj = new JSONObject();
                ubObj.put("id", ubInfo.getId());
                ubObj.put("type", ubInfo.getType());
                ubObj.put("keyId", ubInfo.getKeyId());
                ubObj.put("value", ubInfo.getValue() + ubKey);
                userBusinessService.updateUserBusiness(ubObj, request);
            }
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
        List<DepotItem> depotItemList = depotItemMapperEx.getDepotItemListListByDepotIds(idArray);
        if(depotItemList!=null&&depotItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,DepotIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        try{
            //记录日志
            StringBuffer sb = new StringBuffer();
            sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
            List<Depot> list = getDepotListByIds(ids);
            for(Depot depot: list){
                sb.append("[").append(depot.getName()).append("]");
            }
            User userInfo=userService.getCurrentUser();
            //校验通过执行删除操作
            //删除仓库关联的商品的初始库存
            materialInitialStockMapperEx.batchDeleteByDepots(idArray);
            //删除仓库关联的商品的当前库存
            materialCurrentStockMapperEx.batchDeleteByDepots(idArray);
            //删除仓库
            result = depotMapperEx.batchDeleteDepotByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
            //记录日志
            logService.insertLog("仓库", sb.toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } catch (Exception e) {
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
        example.createCriteria().andTypeEqualTo(0).andEnabledEqualTo(true)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("sort asc, id desc");
        List<Depot> list=null;
        try{
            list= depotMapper.selectByExample(example);
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

    public List<Long> parseDepotList(Long depotId) throws Exception {
        List<Long> depotList = new ArrayList<>();
        if(depotId !=null) {
            depotList.add(depotId);
        } else {
            //未选择仓库时默认为当前用户有权限的仓库
            JSONArray depotArr = findDepotByCurrentUser();
            for(Object obj: depotArr) {
                JSONObject object = JSONObject.parseObject(obj.toString());
                depotList.add(object.getLong("id"));
            }
        }
        return depotList;
    }

    public JSONArray findDepotByCurrentUser() throws Exception {
        JSONArray arr = new JSONArray();
        String type = "UserDepot";
        Long userId = userService.getCurrentUser().getId();
        List<Depot> dataList = findUserDepot();
        //开始拼接json数据
        if (null != dataList) {
            boolean depotFlag = systemConfigService.getDepotFlag();
            if(depotFlag) {
                List<UserBusiness> list = userBusinessService.getBasicData(userId.toString(), type);
                if(list!=null && list.size()>0) {
                    String depotStr = list.get(0).getValue();
                    if(StringUtil.isNotEmpty(depotStr)){
                        depotStr = depotStr.replaceAll("\\[", "").replaceAll("]", ",");
                        String[] depotArr = depotStr.split(",");
                        for (Depot depot : dataList) {
                            for(String depotId: depotArr) {
                                if(depot.getId() == Long.parseLong(depotId)){
                                    JSONObject item = new JSONObject();
                                    item.put("id", depot.getId());
                                    item.put("depotName", depot.getName());
                                    item.put("isDefault", depot.getIsDefault());
                                    arr.add(item);
                                }
                            }
                        }
                    }
                }
            } else {
                for (Depot depot : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", depot.getId());
                    item.put("depotName", depot.getName());
                    item.put("isDefault", depot.getIsDefault());
                    arr.add(item);
                }
            }
        }
        return arr;
    }

    /**
     * 当前用户有权限使用的仓库列表的id，用逗号隔开
     * @return
     * @throws Exception
     */
    public String findDepotStrByCurrentUser() throws Exception {
        JSONArray arr =  findDepotByCurrentUser();
        StringBuffer sb = new StringBuffer();
        for(Object object: arr) {
            JSONObject obj = (JSONObject)object;
            sb.append(obj.getLong("id")).append(",");
        }
        String depotStr = sb.toString();
        if(StringUtil.isNotEmpty(depotStr)){
            depotStr = depotStr.substring(0, depotStr.length()-1);
        }
        return depotStr;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("仓库",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ENABLED).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> depotIds = StringUtil.strToLongList(ids);
        Depot depot = new Depot();
        depot.setEnabled(status);
        DepotExample example = new DepotExample();
        example.createCriteria().andIdIn(depotIds);
        int result=0;
        try{
            result = depotMapper.updateByExampleSelective(depot, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
}
