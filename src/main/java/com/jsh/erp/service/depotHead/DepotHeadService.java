package com.jsh.erp.service.depotHead;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadExample;
import com.jsh.erp.datasource.entities.DepotItem;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.DepotHeadMapper;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotItemMapperEx;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.service.supplier.SupplierService;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jsh.erp.utils.Tools.getCenternTime;

@Service
public class DepotHeadService {
    private Logger logger = LoggerFactory.getLogger(DepotHeadService.class);

    @Resource
    private DepotHeadMapper depotHeadMapper;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;
    @Resource
    private UserService userService;
    @Resource
    DepotItemService depotItemService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private SerialNumberService serialNumberService;
    @Resource
    DepotItemMapperEx depotItemMapperEx;
    @Resource
    private LogService logService;


    public DepotHead getDepotHead(long id) {
        return depotHeadMapper.selectByPrimaryKey(id);
    }

    public List<DepotHead> getDepotHead() {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4List> select(String type, String subType, String number, String beginTime, String endTime, String dhIds, int offset, int rows) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapperEx.selectByConditionDepotHead(type, subType, number, beginTime, endTime, dhIds, offset, rows);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(dh.getChangeamount().abs());
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(dh.getTotalprice().abs());
                }
                dh.setOpertimeStr(getCenternTime(dh.getOpertime()));
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }



    public Long countDepotHead(String type, String subType, String number, String beginTime, String endTime, String dhIds) {
        return depotHeadMapperEx.countsByDepotHead(type, subType, number, beginTime, endTime, dhIds);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotHead(String beanJson, HttpServletRequest request) {
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        Object userInfo = request.getSession().getAttribute("user");
        if (userInfo != null) {
            User sessionUser = (User) userInfo;
            String uName = sessionUser.getUsername();
            depotHead.setOperpersonname(uName);
        }
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(BusinessConstants.BILLS_STATUS_UN_AUDIT);
        return depotHeadMapper.insert(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotHead(String beanJson, Long id) {
        DepotHead dh = depotHeadMapper.selectByPrimaryKey(id);
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        depotHead.setId(id);
        depotHead.setStatus(dh.getStatus());
        depotHead.setCreatetime(dh.getCreatetime());
        depotHead.setOperpersonname(dh.getOperpersonname());
        return depotHeadMapper.updateByPrimaryKey(depotHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotHead(Long id) {
        return depotHeadMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHead(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(idList);
        return depotHeadMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdNotEqualTo(id).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list = depotHeadMapper.selectByExample(example);
        return list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(String status, String depotHeadIDs) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(depotHeadIDs).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> ids = StringUtil.strToLongList(depotHeadIDs);
        DepotHead depotHead = new DepotHead();
        depotHead.setStatus(status);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(ids);
        return depotHeadMapper.updateByExampleSelective(depotHead, example);
    }
    /**
     * 创建一个唯一的序列号
     * */
    public  String buildOnlyNumber(){
        Long buildOnlyNumber=null;
        synchronized (this){
            buildOnlyNumber= depotHeadMapperEx.getBuildOnlyNumber(BusinessConstants.DEPOT_NUMBER_SEQ);
        }
        if(buildOnlyNumber<BusinessConstants.SEQ_TO_STRING_MIN_LENGTH){
           StringBuffer sb=new StringBuffer(buildOnlyNumber.toString());
           int len=BusinessConstants.SEQ_TO_STRING_MIN_LENGTH.toString().length()-sb.length();
            for(int i=0;i<len;i++){
                sb.insert(0,BusinessConstants.SEQ_TO_STRING_LESS_INSERT);
            }
            return sb.toString();
        }else{
            return buildOnlyNumber.toString();
        }
    }

    public Long getMaxId() {
        return depotHeadMapperEx.getMaxId();
    }

    public String findMaterialsListByHeaderId(Long id) {
        String allReturn = depotHeadMapperEx.findMaterialsListByHeaderId(id);
        return allReturn;
    }

    public List<DepotHead> findByMonth(String monthTime) {
        DepotHeadExample example = new DepotHeadExample();
        monthTime = monthTime + "-31 23:59:59";
        Date month = StringUtil.getDateByString(monthTime, null);
        example.createCriteria().andOpertimeLessThanOrEqualTo(month).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHead> getDepotHeadGiftOut(String projectId) {
        DepotHeadExample example = new DepotHeadExample();
        if (projectId != null) {
            example.createCriteria().andProjectidEqualTo(Long.parseLong(projectId));
        }
        return depotHeadMapper.selectByExample(example);
    }

    public List<DepotHeadVo4InDetail> findByAll(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapperEx.findByAll(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findByAllCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapperEx.findByAllCount(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4InOutMCount> findInOutMaterialCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) {
        return depotHeadMapperEx.findInOutMaterialCount(beginTime, endTime, type, pid, dids, oId, offset, rows);
    }

    public int findInOutMaterialCountTotal(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) {
        return depotHeadMapperEx.findInOutMaterialCountTotal(beginTime, endTime, type, pid, dids, oId);
    }

    public List<DepotHeadVo4StatementAccount> findStatementAccount(String beginTime, String endTime, Integer organId, String supType, Integer offset, Integer rows) {
        return depotHeadMapperEx.findStatementAccount(beginTime, endTime, organId, supType, offset, rows);
    }

    public int findStatementAccountCount(String beginTime, String endTime, Integer organId, String supType) {
        return depotHeadMapperEx.findStatementAccountCount(beginTime, endTime, organId, supType);
    }

    public BigDecimal findAllMoney(Integer supplierId, String type, String subType, String mode, String endTime) {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "DiscountLastMoney";
        }
        return depotHeadMapperEx.findAllMoney(supplierId, type, subType, modeName, endTime);
    }

    public List<DepotHeadVo4List> getDetailByNumber(String number) {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = depotHeadMapperEx.getDetailByNumber(number);
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getOthermoneyitem() != null) {
                    String otherMoneyItemStr = dh.getOthermoneyitem().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneyitem(otherMoneyItemStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(dh.getChangeamount().abs());
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(dh.getTotalprice().abs());
                }
                dh.setOpertimeStr(getCenternTime(dh.getOpertime()));
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }

    /**
     * create by: cjl
     * description:
     *  新增单据主表及单据子表信息
     * create time: 2019/1/25 14:36
     * @Param: beanJson
     * @Param: inserted
     * @Param: deleted
     * @Param: updated
     * @return java.lang.String
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addDepotHeadAndDetail(String beanJson, String inserted, String deleted, String updated) throws Exception {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                BusinessConstants.LOG_OPERATION_TYPE_ADD,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        /**处理单据主表数据*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        User userInfo=userService.getCurrentUser();
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(BusinessConstants.BILLS_STATUS_UN_AUDIT);
        depotHeadMapperEx.adddepotHead(depotHead);
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null) {
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice()));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(inserted,deleted,updated,depotHead.getId());
        /**如果关联单据号非空则更新订单的状态为2 */
        if(depotHead.getLinknumber()!=null) {
            DepotHead depotHeadOrders = new DepotHead();
            depotHeadOrders.setStatus(BusinessConstants.BILLS_STATUS_SKIP);
            DepotHeadExample example = new DepotHeadExample();
            example.createCriteria().andNumberEqualTo(depotHead.getLinknumber());
            depotHeadMapper.updateByExampleSelective(depotHeadOrders, example);
        }
    }
    /**
     * create by: cjl
     * description:
     * 更新单据主表及单据子表信息
     * create time: 2019/1/28 14:47
     * @Param: id
     * @Param: beanJson
     * @Param: inserted
     * @Param: deleted
     * @Param: updated
     * @Param: preTotalPrice
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateDepotHeadAndDetail(Long id, String beanJson, String inserted, String deleted, String updated, BigDecimal preTotalPrice)throws Exception {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        /**更新单据主表信息*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        depotHead.setId(id);
        User userInfo=userService.getCurrentUser();
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        depotHead.setOpertime(new Timestamp(System.currentTimeMillis()));
        depotHeadMapperEx.updatedepotHead(depotHead);
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null){
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice().subtract(preTotalPrice)));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(inserted,deleted,updated,depotHead.getId());
    }

    /**
     * create by: cjl
     * description:
     *  删除单据主表及子表信息
     * create time: 2019/1/28 17:29
     * @Param: id
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteDepotHeadAndDetail(Long id) throws Exception {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //查询单据主表信息
        DepotHead depotHead =getDepotHead(id);
        User userInfo=userService.getCurrentUser();
        //删除出库数据回收序列号
        if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())){
            //查询单据子表列表
            List<DepotItem> depotItemList = depotItemMapperEx.findDepotItemListBydepotheadId(id,BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED);
            /**回收序列号*/
            if(depotItemList!=null&&depotItemList.size()>0){
                for(DepotItem depotItem:depotItemList){
                    //BasicNumber=OperNumber*ratio
                    serialNumberService.cancelSerialNumber(depotItem.getMaterialid(), depotItem.getHeaderid(),(depotItem.getBasicnumber()==null?0:depotItem.getBasicnumber()).intValue(),userInfo);
                }
            }
        }
        /**删除单据子表数据*/
        depotItemMapperEx.batchDeleteDepotItemByDepotHeadIds(new Long []{id});
        /**删除单据主表信息*/
        batchDeleteDepotHeadByIds(id.toString());
    }
    /**
     * create by: cjl
     * description:
     *  批量删除单据主表及子表信息
     * create time: 2019/1/28 17:29
     * @Param: id
     * @return java.lang.Object
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batchDeleteDepotHeadAndDetail(String ids) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        if(StringUtil.isNotEmpty(ids)){
            String [] headIds=ids.split(",");
            for(int i=0;i<headIds.length;i++){
                deleteDepotHeadAndDetail(Long.valueOf(headIds[i]));
            }
        }
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHeadByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return depotHeadMapperEx.batchDeleteDepotHeadByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
