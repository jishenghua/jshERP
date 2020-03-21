package com.jsh.erp.service.depotHead;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
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
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
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


    public DepotHead getDepotHead(long id)throws Exception {
        DepotHead result=null;
        try{
            result=depotHeadMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHead> getDepotHead()throws Exception {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list=null;
        try{
            list=depotHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<DepotHeadVo4List> select(String type, String subType, String number, String beginTime, String endTime,
                                         String materialParam, String depotIds, int offset, int rows)throws Exception {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list=null;
        try{
            list=depotHeadMapperEx.selectByConditionDepotHead(type, subType, number, beginTime, endTime, materialParam, depotIds, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if (null != list) {
            for (DepotHeadVo4List dh : list) {
                if(dh.getAccountidlist() != null) {
                    String accountidlistStr = dh.getAccountidlist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setAccountidlist(accountidlistStr);
                }
                if(dh.getAccountmoneylist() != null) {
                    String accountmoneylistStr = dh.getAccountmoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setAccountmoneylist(accountmoneylistStr);
                }
                if(dh.getOthermoneylist() != null) {
                    String otherMoneyListStr = dh.getOthermoneylist().replace("[", "").replace("]", "").replaceAll("\"", "");
                    dh.setOthermoneylist(otherMoneyListStr);
                }
                if(dh.getChangeamount() != null) {
                    dh.setChangeamount(dh.getChangeamount().abs());
                }
                if(dh.getTotalprice() != null) {
                    dh.setTotalprice(dh.getTotalprice().abs());
                }
                if(dh.getOpertime() != null) {
                    dh.setOpertimeStr(getCenternTime(dh.getOpertime()));
                }
                dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                resList.add(dh);
            }
        }
        return resList;
    }



    public Long countDepotHead(String type, String subType, String number, String beginTime, String endTime,
                               String materialParam, String depotIds) throws Exception{
        Long result=null;
        try{
            result=depotHeadMapperEx.countsByDepotHead(type, subType, number, beginTime, endTime, materialParam, depotIds);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotHead(String beanJson, HttpServletRequest request)throws Exception {
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
        int result=0;
        try{
            result=depotHeadMapper.insert(depotHead);
            logService.insertLog("单据", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotHead(String beanJson, Long id, HttpServletRequest request) throws Exception{
        DepotHead dh=null;
        try{
            dh = depotHeadMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        depotHead.setId(id);
        depotHead.setStatus(dh.getStatus());
        depotHead.setCreatetime(dh.getCreatetime());
        depotHead.setOperpersonname(dh.getOperpersonname());
        int result=0;
        try{
            result = depotHeadMapper.updateByPrimaryKey(depotHead);
            logService.insertLog("单据",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotHead(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result = depotHeadMapper.deleteByPrimaryKey(id);
            logService.insertLog("单据",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHead(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result = depotHeadMapper.deleteByExample(example);
            logService.insertLog("单据", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdNotEqualTo(id).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list = null;
        try{
            list = depotHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(String status, String depotHeadIDs)throws Exception {
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(depotHeadIDs).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> ids = StringUtil.strToLongList(depotHeadIDs);
        DepotHead depotHead = new DepotHead();
        depotHead.setStatus(status);
        DepotHeadExample example = new DepotHeadExample();
        example.createCriteria().andIdIn(ids);
        int result = 0;
        try{
            result = depotHeadMapper.updateByExampleSelective(depotHead, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    /**
     * 创建一个唯一的序列号
     * */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String buildOnlyNumber()throws Exception{
        Long buildOnlyNumber=null;
        synchronized (this){
            try{
                depotHeadMapperEx.updateBuildOnlyNumber(); //编号+1
                buildOnlyNumber= depotHeadMapperEx.getBuildOnlyNumber(BusinessConstants.DEPOT_NUMBER_SEQ);
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
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

    public Long getMaxId()throws Exception {
        Long result = null;
        try{
            result = depotHeadMapperEx.getMaxId();
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public String findMaterialsListByHeaderId(Long id)throws Exception {
        String result = null;
        try{
            result = depotHeadMapperEx.findMaterialsListByHeaderId(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHead> findByMonth(String monthTime)throws Exception {
        DepotHeadExample example = new DepotHeadExample();
        monthTime = monthTime + "-31 23:59:59";
        Date month = StringUtil.getDateByString(monthTime, null);
        example.createCriteria().andOpertimeLessThanOrEqualTo(month).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list = null;
        try{
            list = depotHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<DepotHeadVo4InDetail> findByAll(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows) throws Exception{
        List<DepotHeadVo4InDetail> list = null;
        try{
            list =depotHeadMapperEx.findByAll(beginTime, endTime, type, pid, dids, oId, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findByAllCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId) throws Exception{
        int result = 0;
        try{
            result =depotHeadMapperEx.findByAllCount(beginTime, endTime, type, pid, dids, oId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHeadVo4InOutMCount> findInOutMaterialCount(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId, Integer offset, Integer rows)throws Exception {
        List<DepotHeadVo4InOutMCount> list = null;
        try{
            list =depotHeadMapperEx.findInOutMaterialCount(beginTime, endTime, type, pid, dids, oId, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findInOutMaterialCountTotal(String beginTime, String endTime, String type, Integer pid, String dids, Integer oId)throws Exception {
        int result = 0;
        try{
            result =depotHeadMapperEx.findInOutMaterialCountTotal(beginTime, endTime, type, pid, dids, oId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHeadVo4StatementAccount> findStatementAccount(String beginTime, String endTime, Integer organId, String supType, Integer offset, Integer rows)throws Exception {
        List<DepotHeadVo4StatementAccount> list = null;
        try{
            list =depotHeadMapperEx.findStatementAccount(beginTime, endTime, organId, supType, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findStatementAccountCount(String beginTime, String endTime, Integer organId, String supType) throws Exception{
        int result = 0;
        try{
            result =depotHeadMapperEx.findStatementAccountCount(beginTime, endTime, organId, supType);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public BigDecimal findAllMoney(Integer supplierId, String type, String subType, String mode, String endTime)throws Exception {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "DiscountLastMoney";
        }
        BigDecimal result = null;
        try{
            result =depotHeadMapperEx.findAllMoney(supplierId, type, subType, modeName, endTime);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    /**
     * 统计总金额
     * @param getS
     * @param type
     * @param subType
     * @param mode 合计或者金额
     * @return
     */
    public BigDecimal allMoney(String getS, String type, String subType, String mode, String endTime) {
        BigDecimal allMoney = BigDecimal.ZERO;
        try {
            Integer supplierId = Integer.valueOf(getS);
            BigDecimal sum = findAllMoney(supplierId, type, subType, mode, endTime);
            if(sum != null) {
                allMoney = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回正数，如果负数也转为正数
        if ((allMoney.compareTo(BigDecimal.ZERO))==-1) {
            allMoney = allMoney.abs();
        }
        return allMoney;
    }

    /**
     * 查询单位的累计应收和累计应付，零售不能计入
     * @param supplierId
     * @param endTime
     * @param supType
     * @return
     */
    public BigDecimal findTotalPay(Integer supplierId, String endTime, String supType) {
        BigDecimal sum = BigDecimal.ZERO;
        String getS = supplierId.toString();
        int i = 1;
        if (("customer").equals(supType)) { //客户
            i = 1;
        } else if (("vendor").equals(supType)) { //供应商
            i = -1;
        }
        //进销部分
        sum = sum.subtract((allMoney(getS, "入库", "采购", "合计",endTime).subtract(allMoney(getS, "入库", "采购", "实际",endTime))).multiply(new BigDecimal(i)));
        sum = sum.subtract((allMoney(getS, "入库", "销售退货", "合计",endTime).subtract(allMoney(getS, "入库", "销售退货", "实际",endTime))).multiply(new BigDecimal(i)));
        sum = sum.add((allMoney(getS, "出库", "销售", "合计",endTime).subtract(allMoney(getS, "出库", "销售", "实际",endTime))).multiply(new BigDecimal(i)));
        sum = sum.add((allMoney(getS, "出库", "采购退货", "合计",endTime).subtract(allMoney(getS, "出库", "采购退货", "实际",endTime))).multiply(new BigDecimal(i)));
        return sum;
    }

    public List<DepotHeadVo4List> getDetailByNumber(String number)throws Exception {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list = null;
        try{
            list = depotHeadMapperEx.getDetailByNumber(number);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
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
     * 新增单据主表及单据子表信息
     * @param beanJson
     * @param inserted
     * @param deleted
     * @param updated
     * @param tenantId
     * @param request
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addDepotHeadAndDetail(String beanJson, String inserted, String deleted, String updated,Long tenantId,
                                      HttpServletRequest request) throws Exception {
        logService.insertLog("单据",
                BusinessConstants.LOG_OPERATION_TYPE_ADD,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        /**处理单据主表数据*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        User userInfo=userService.getCurrentUser();
        depotHead.setHandspersonid(userInfo==null?null:userInfo.getId());
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        depotHead.setCreatetime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(BusinessConstants.BILLS_STATUS_UN_AUDIT);
        try{
            depotHeadMapper.insertSelective(depotHead);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null) {
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice()));
            }
        }
        //根据单据编号查询单据id
        DepotHeadExample dhExample = new DepotHeadExample();
        dhExample.createCriteria().andDefaultnumberEqualTo(depotHead.getDefaultnumber()).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list = depotHeadMapper.selectByExample(dhExample);
        if(list!=null) {
            Long headId = list.get(0).getId();
            /**入库和出库处理单据子表信息*/
            depotItemService.saveDetials(inserted,deleted,updated,headId,tenantId, request);
        }
        /**如果关联单据号非空则更新订单的状态为2 */
        if(depotHead.getLinknumber()!=null) {
            DepotHead depotHeadOrders = new DepotHead();
            depotHeadOrders.setStatus(BusinessConstants.BILLS_STATUS_SKIP);
            DepotHeadExample example = new DepotHeadExample();
            example.createCriteria().andNumberEqualTo(depotHead.getLinknumber());
            try{
                depotHeadMapper.updateByExampleSelective(depotHeadOrders, example);
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
        }
    }

    /**
     * 更新单据主表及单据子表信息
     * @param id
     * @param beanJson
     * @param inserted
     * @param deleted
     * @param updated
     * @param preTotalPrice
     * @param tenantId
     * @param request
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateDepotHeadAndDetail(Long id, String beanJson, String inserted, String deleted, String updated,
                                         BigDecimal preTotalPrice, Long tenantId,HttpServletRequest request)throws Exception {
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        /**更新单据主表信息*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        //判断用户是否已经登录过，登录过不再处理
        depotHead.setId(id);
        User userInfo=userService.getCurrentUser();
        depotHead.setOperpersonname(userInfo==null?null:userInfo.getUsername());
        try{
            depotHeadMapper.updateByPrimaryKeySelective(depotHead);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPaytype())){
            if(depotHead.getOrganid()!=null){
                supplierService.updateAdvanceIn(depotHead.getOrganid(), BigDecimal.ZERO.subtract(depotHead.getTotalprice().subtract(preTotalPrice)));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(inserted,deleted,updated,depotHead.getId(),tenantId,request);
    }

    /**
     * 删除单据主表及子表信息
     * @param id
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteDepotHeadAndDetail(Long id) throws Exception {
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //查询单据主表信息
        DepotHead depotHead =getDepotHead(id);
        User userInfo=userService.getCurrentUser();
        //删除出库数据回收序列号
        if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())){
            //查询单据子表列表
            List<DepotItem> depotItemList=null;
            try{
                depotItemList = depotItemMapperEx.findDepotItemListBydepotheadId(id,BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED);
            }catch(Exception e){
                JshException.readFail(logger, e);
            }

            /**回收序列号*/
            if(depotItemList!=null&&depotItemList.size()>0){
                for(DepotItem depotItem:depotItemList){
                    //BasicNumber=OperNumber*ratio
                    serialNumberService.cancelSerialNumber(depotItem.getMaterialid(), depotItem.getHeaderid(),(depotItem.getBasicnumber()==null?0:depotItem.getBasicnumber()).intValue(),userInfo);
                }
            }
        }
        /**删除单据子表数据*/
        try{
            depotItemMapperEx.batchDeleteDepotItemByDepotHeadIds(new Long []{id});
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }

        /**删除单据主表信息*/
        batchDeleteDepotHeadByIds(id.toString());
    }

    /**
     * 批量删除单据主表及子表信息
     * @param ids
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batchDeleteDepotHeadAndDetail(String ids) throws Exception{
        logService.insertLog("单据",
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
    public int batchDeleteDepotHeadByIds(String ids)throws Exception {
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result = depotHeadMapperEx.batchDeleteDepotHeadByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public BigDecimal getBuyAndSaleStatistics(String type, String subType, Integer hasSupplier, String beginTime, String endTime) {
        return depotHeadMapperEx.getBuyAndSaleStatistics(type, subType, hasSupplier, beginTime, endTime);
    }

    public BigDecimal getBuyAndSaleRetailStatistics(String type, String subType, Integer hasSupplier, String beginTime, String endTime) {
        return depotHeadMapperEx.getBuyAndSaleRetailStatistics(type, subType, hasSupplier, beginTime, endTime);
    }
}
