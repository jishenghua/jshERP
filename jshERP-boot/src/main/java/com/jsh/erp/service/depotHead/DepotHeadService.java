package com.jsh.erp.service.depotHead;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.DepotHeadMapper;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotItemMapperEx;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.account.AccountService;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.orgaUserRel.OrgaUserRelService;
import com.jsh.erp.service.person.PersonService;
import com.jsh.erp.service.redis.RedisService;
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
import java.util.*;

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
    private DepotService depotService;
    @Resource
    DepotItemService depotItemService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private SerialNumberService serialNumberService;
    @Resource
    private OrgaUserRelService orgaUserRelService;
    @Resource
    private PersonService personService;
    @Resource
    private AccountService accountService;
    @Resource
    DepotItemMapperEx depotItemMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private RedisService redisService;

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

    public List<DepotHeadVo4List> select(String type, String subType, String roleType, String status, String number, String beginTime, String endTime,
                                         String materialParam, int offset, int rows)throws Exception {
        List<DepotHeadVo4List> resList = new ArrayList<DepotHeadVo4List>();
        List<DepotHeadVo4List> list=new ArrayList<>();
        try{
            String depotIds = depotService.findDepotStrByCurrentUser();
            String [] creatorArray = getCreatorArray(roleType);
            Map<Long,String> personMap = personService.getPersonMap();
            Map<Long,String> accountMap = accountService.getAccountMap();
            list=depotHeadMapperEx.selectByConditionDepotHead(type, subType, creatorArray, status, number, beginTime, endTime, materialParam, depotIds, offset, rows);
            if (null != list) {
                for (DepotHeadVo4List dh : list) {
                    if(accountMap!=null && StringUtil.isNotEmpty(dh.getAccountIdList()) && StringUtil.isNotEmpty(dh.getAccountMoneyList())) {
                        String accountStr = accountService.getAccountStrByIdAndMoney(accountMap, dh.getAccountIdList(), dh.getAccountMoneyList());
                        dh.setAccountName(accountStr);
                    }
                    if(dh.getAccountIdList() != null) {
                        String accountidlistStr = dh.getAccountIdList().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setAccountIdList(accountidlistStr);
                    }
                    if(dh.getAccountMoneyList() != null) {
                        String accountmoneylistStr = dh.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setAccountMoneyList(accountmoneylistStr);
                    }
                    if(dh.getChangeAmount() != null) {
                        dh.setChangeAmount(dh.getChangeAmount().abs());
                    }
                    if(dh.getTotalPrice() != null) {
                        dh.setTotalPrice(dh.getTotalPrice().abs());
                    }
                    if(StringUtil.isNotEmpty(dh.getSalesMan())) {
                        dh.setSalesManStr(personService.getPersonByMapAndIds(personMap,dh.getSalesMan()));
                    }
                    if(dh.getOperTime() != null) {
                        dh.setOperTimeStr(getCenternTime(dh.getOperTime()));
                    }
                    dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                    resList.add(dh);
                }
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return resList;
    }

    public Long countDepotHead(String type, String subType, String roleType, String status, String number, String beginTime, String endTime,
                               String materialParam) throws Exception{
        Long result=null;
        try{
            String depotIds = depotService.findDepotStrByCurrentUser();
            String [] creatorArray = getCreatorArray(roleType);
            result=depotHeadMapperEx.countsByDepotHead(type, subType, creatorArray, status, number, beginTime, endTime, materialParam, depotIds);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    /**
     * 根据角色类型获取操作员数组
     * @param roleType
     * @return
     * @throws Exception
     */
    public String[] getCreatorArray(String roleType) throws Exception {
        String creator = getCreatorByRoleType(roleType);
        String [] creatorArray=null;
        if(StringUtil.isNotEmpty(creator)){
            creatorArray = creator.split(",");
        }
        return creatorArray;
    }

    /**
     * 根据角色类型获取操作员
     * @param roleType
     * @return
     * @throws Exception
     */
    public String getCreatorByRoleType(String roleType) throws Exception {
        String creator = "";
        User user = userService.getCurrentUser();
        if(BusinessConstants.ROLE_TYPE_PRIVATE.equals(roleType)) {
            creator = user.getId().toString();
        } else if(BusinessConstants.ROLE_TYPE_THIS_ORG.equals(roleType)) {
            creator = orgaUserRelService.getUserIdListByUserId(user.getId());
        }
        return creator;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotHead(JSONObject obj, HttpServletRequest request)throws Exception {
        DepotHead depotHead = JSONObject.parseObject(obj.toJSONString(), DepotHead.class);
        depotHead.setCreateTime(new Timestamp(System.currentTimeMillis()));
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
    public int updateDepotHead(JSONObject obj, HttpServletRequest request) throws Exception{
        DepotHead depotHead = JSONObject.parseObject(obj.toJSONString(), DepotHead.class);
        DepotHead dh=null;
        try{
            dh = depotHeadMapper.selectByPrimaryKey(depotHead.getId());
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        depotHead.setStatus(dh.getStatus());
        depotHead.setCreateTime(dh.getCreateTime());
        int result=0;
        try{
            result = depotHeadMapper.updateByPrimaryKey(depotHead);
            logService.insertLog("单据",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(depotHead.getId()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotHead(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            //查询单据主表信息
            DepotHead depotHead =getDepotHead(id);
            User userInfo=userService.getCurrentUser();
            //删除出库数据回收序列号
            if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                    &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())){
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
                        serialNumberService.cancelSerialNumber(depotItem.getMaterialId(), depotItem.getHeaderId(),(depotItem.getBasicNumber()==null?0:depotItem.getBasicNumber()).intValue(),userInfo);
                    }
                }
            }
            /**删除单据子表数据*/
            try{
                depotItemMapperEx.batchDeleteDepotItemByDepotHeadIds(new Long []{id});
                //更新当前库存
                List<DepotItem> list = depotItemService.getListByHeaderId(id);
                for(DepotItem depotItem: list){
                    Long tenantId = redisService.getTenantId(request);
                    depotItemService.updateCurrentStock(depotItem,tenantId);
                }
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
            /**删除单据主表信息*/
            batchDeleteDepotHeadByIds(id.toString());
            logService.insertLog("单据",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(depotHead.getNumber()).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            result = 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHead(String ids, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            if(StringUtil.isNotEmpty(ids)){
                String [] headIds=ids.split(",");
                for(int i=0;i<headIds.length;i++){
                    deleteDepotHead(Long.valueOf(headIds[i]), request);
                }
            }
            result = 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotHeadByIds(String ids)throws Exception {
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

    public String findMaterialsListByHeaderId(Long id)throws Exception {
        String result = null;
        try{
            result = depotHeadMapperEx.findMaterialsListByHeaderId(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHeadVo4InDetail> findByAll(String beginTime, String endTime, String type, String materialParam, Integer depotId, Integer oId, Integer offset, Integer rows) throws Exception{
        List<DepotHeadVo4InDetail> list = null;
        try{
            list =depotHeadMapperEx.findByAll(beginTime, endTime, type, materialParam, depotId, oId, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findByAllCount(String beginTime, String endTime, String type, String materialParam, Integer depotId, Integer oId) throws Exception{
        int result = 0;
        try{
            result =depotHeadMapperEx.findByAllCount(beginTime, endTime, type, materialParam, depotId, oId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotHeadVo4InOutMCount> findInOutMaterialCount(String beginTime, String endTime, String type, String materialParam, Integer depotId, Integer oId, Integer offset, Integer rows)throws Exception {
        List<DepotHeadVo4InOutMCount> list = null;
        try{
            list =depotHeadMapperEx.findInOutMaterialCount(beginTime, endTime, type, materialParam, depotId, oId, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findInOutMaterialCountTotal(String beginTime, String endTime, String type, String materialParam, Integer depotId, Integer oId)throws Exception {
        int result = 0;
        try{
            result =depotHeadMapperEx.findInOutMaterialCountTotal(beginTime, endTime, type, materialParam, depotId, oId);
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
        BigDecimal allOtherMoney = BigDecimal.ZERO;
        if (mode.equals("实际")) {
            modeName = "change_amount";
        } else if (mode.equals("合计")) {
            modeName = "discount_last_money";
            allOtherMoney = depotHeadMapperEx.findAllOtherMoney(supplierId, type, subType, endTime);
        }
        BigDecimal result = BigDecimal.ZERO;
        try{
            result =depotHeadMapperEx.findAllMoney(supplierId, type, subType, modeName, endTime);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(allOtherMoney!=null) {
            result = result.add(allOtherMoney);
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
            Map<Long,String> personMap = personService.getPersonMap();
            Map<Long,String> accountMap = accountService.getAccountMap();
            list = depotHeadMapperEx.getDetailByNumber(number);
            if (null != list) {
                for (DepotHeadVo4List dh : list) {
                    if(accountMap!=null && StringUtil.isNotEmpty(dh.getAccountIdList()) && StringUtil.isNotEmpty(dh.getAccountMoneyList())) {
                        String accountStr = accountService.getAccountStrByIdAndMoney(accountMap, dh.getAccountIdList(), dh.getAccountMoneyList());
                        dh.setAccountName(accountStr);
                    }
                    if(dh.getAccountIdList() != null) {
                        String accountidlistStr = dh.getAccountIdList().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setAccountIdList(accountidlistStr);
                    }
                    if(dh.getAccountMoneyList() != null) {
                        String accountmoneylistStr = dh.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setAccountMoneyList(accountmoneylistStr);
                    }
                    if(dh.getOtherMoneyList() != null) {
                        String otherMoneyListStr = dh.getOtherMoneyList().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setOtherMoneyList(otherMoneyListStr);
                    }
                    if(dh.getOtherMoneyItem() != null) {
                        String otherMoneyItemStr = dh.getOtherMoneyItem().replace("[", "").replace("]", "").replaceAll("\"", "");
                        dh.setOtherMoneyItem(otherMoneyItemStr);
                    }
                    if(dh.getChangeAmount() != null) {
                        dh.setChangeAmount(dh.getChangeAmount().abs());
                    }
                    if(dh.getTotalPrice() != null) {
                        dh.setTotalPrice(dh.getTotalPrice().abs());
                    }
                    if(StringUtil.isNotEmpty(dh.getSalesMan())) {
                        dh.setSalesManStr(personService.getPersonByMapAndIds(personMap,dh.getSalesMan()));
                    }
                    dh.setOperTimeStr(getCenternTime(dh.getOperTime()));
                    dh.setMaterialsList(findMaterialsListByHeaderId(dh.getId()));
                    resList.add(dh);
                }
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return resList;
    }

    /**
     * 新增单据主表及单据子表信息
     * @param beanJson
     * @param rows
     * @param tenantId
     * @param request
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addDepotHeadAndDetail(String beanJson, String rows, Long tenantId,
                                      HttpServletRequest request) throws Exception {
        /**处理单据主表数据*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        if(StringUtil.isEmpty(depotHead.getAccountIdList()) && depotHead.getAccountId()==null) {
            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ACCOUNT_FAILED_CODE,
                    String.format(ExceptionConstants.DEPOT_HEAD_ACCOUNT_FAILED_MSG));
        }
        //判断用户是否已经登录过，登录过不再处理
        User userInfo=userService.getCurrentUser();
        depotHead.setCreator(userInfo==null?null:userInfo.getId());
        depotHead.setCreateTime(new Timestamp(System.currentTimeMillis()));
        depotHead.setStatus(BusinessConstants.BILLS_STATUS_UN_AUDIT);
        depotHead.setPayType(depotHead.getPayType()==null?"现付":depotHead.getPayType());
        if(StringUtil.isNotEmpty(depotHead.getAccountIdList())){
            depotHead.setAccountIdList(depotHead.getAccountIdList().replace("[", "").replace("]", "").replaceAll("\"", ""));
        }
        if(StringUtil.isNotEmpty(depotHead.getAccountMoneyList())) {
            depotHead.setAccountMoneyList(depotHead.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", ""));
        }
        try{
            depotHeadMapper.insertSelective(depotHead);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPayType())){
            if(depotHead.getOrganId()!=null) {
                supplierService.updateAdvanceIn(depotHead.getOrganId(), BigDecimal.ZERO.subtract(depotHead.getTotalPrice()));
            }
        }
        //根据单据编号查询单据id
        DepotHeadExample dhExample = new DepotHeadExample();
        dhExample.createCriteria().andNumberEqualTo(depotHead.getNumber()).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotHead> list = depotHeadMapper.selectByExample(dhExample);
        if(list!=null) {
            Long headId = list.get(0).getId();
            /**入库和出库处理单据子表信息*/
            depotItemService.saveDetials(rows,headId,tenantId, request);
        }
        /**如果关联单据号非空则更新订单的状态为2 */
        if(depotHead.getLinkNumber()!=null) {
            DepotHead depotHeadOrders = new DepotHead();
            depotHeadOrders.setStatus(BusinessConstants.BILLS_STATUS_SKIP);
            DepotHeadExample example = new DepotHeadExample();
            example.createCriteria().andNumberEqualTo(depotHead.getLinkNumber());
            try{
                depotHeadMapper.updateByExampleSelective(depotHeadOrders, example);
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
        }
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(depotHead.getNumber()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    /**
     * 更新单据主表及单据子表信息
     * @param beanJson
     * @param rows
     * @param preTotalPrice
     * @param tenantId
     * @param request
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateDepotHeadAndDetail(String beanJson, String rows,
                                         BigDecimal preTotalPrice, Long tenantId,HttpServletRequest request)throws Exception {
        /**更新单据主表信息*/
        DepotHead depotHead = JSONObject.parseObject(beanJson, DepotHead.class);
        if(StringUtil.isEmpty(depotHead.getAccountIdList()) && depotHead.getAccountId()==null) {
            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ACCOUNT_FAILED_CODE,
                    String.format(ExceptionConstants.DEPOT_HEAD_ACCOUNT_FAILED_MSG));
        }
        if(StringUtil.isNotEmpty(depotHead.getAccountIdList())){
            depotHead.setAccountIdList(depotHead.getAccountIdList().replace("[", "").replace("]", "").replaceAll("\"", ""));
        }
        if(StringUtil.isNotEmpty(depotHead.getAccountMoneyList())) {
            depotHead.setAccountMoneyList(depotHead.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", ""));
        }
        try{
            depotHeadMapper.updateByPrimaryKeySelective(depotHead);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        /**入库和出库处理预付款信息*/
        if(BusinessConstants.PAY_TYPE_PREPAID.equals(depotHead.getPayType())){
            if(depotHead.getOrganId()!=null){
                supplierService.updateAdvanceIn(depotHead.getOrganId(), BigDecimal.ZERO.subtract(depotHead.getTotalPrice().subtract(preTotalPrice)));
            }
        }
        /**入库和出库处理单据子表信息*/
        depotItemService.saveDetials(rows,depotHead.getId(),tenantId,request);
        logService.insertLog("单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(depotHead.getNumber()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    public BigDecimal getBuyAndSaleStatistics(String type, String subType, Integer hasSupplier, String beginTime, String endTime) {
        return depotHeadMapperEx.getBuyAndSaleStatistics(type, subType, hasSupplier, beginTime, endTime);
    }

    public BigDecimal getBuyAndSaleRetailStatistics(String type, String subType, Integer hasSupplier, String beginTime, String endTime) {
        return depotHeadMapperEx.getBuyAndSaleRetailStatistics(type, subType, hasSupplier, beginTime, endTime);
    }
}
