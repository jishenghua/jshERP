package com.jsh.erp.service.supplier;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.SupplierMapper;
import com.jsh.erp.datasource.mappers.SupplierMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.accountHead.AccountHeadService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
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
import java.util.*;

import static com.jsh.erp.utils.Tools.getNow3;

@Service
public class SupplierService {
    private Logger logger = LoggerFactory.getLogger(SupplierService.class);

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private SupplierMapperEx supplierMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;
    @Resource
    private DepotHeadService depotHeadService;
    @Resource
    private AccountHeadService accountHeadService;

    public Supplier getSupplier(long id)throws Exception {
        Supplier result=null;
        try{
            result=supplierMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Supplier> getSupplierListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Supplier> list = new ArrayList<>();
        try{
            SupplierExample example = new SupplierExample();
            example.createCriteria().andIdIn(idList);
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> getSupplier()throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Supplier> list=null;
        try{
            list=supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> select(String supplier, String type, String phonenum, String telephone, int offset, int rows) throws Exception{
        List<Supplier> resList = new ArrayList<Supplier>();
        try{
            List<Supplier> list = supplierMapperEx.selectByConditionSupplier(supplier, type, phonenum, telephone, offset, rows);
            for(Supplier s : list) {
                Integer supplierId = s.getId().intValue();
                String endTime = getNow3();
                String supType = s.getType();
                BigDecimal sum = BigDecimal.ZERO;
                BigDecimal beginNeedGet = s.getBeginNeedGet();
                if(beginNeedGet==null) {
                    beginNeedGet = BigDecimal.ZERO;
                }
                BigDecimal beginNeedPay = s.getBeginNeedPay();
                if(beginNeedPay==null) {
                    beginNeedPay = BigDecimal.ZERO;
                }
                sum = sum.add(depotHeadService.findTotalPay(supplierId, endTime, supType));
                sum = sum.add(accountHeadService.findTotalPay(supplierId, endTime, supType));
                if(("客户").equals(s.getType())) {
                    sum = sum.add(beginNeedGet).subtract(beginNeedPay);
                    s.setAllNeedGet(sum);
                    s.setAllNeedPay(BigDecimal.ZERO);
                } else if(("供应商").equals(s.getType())) {
                    sum = sum.add(beginNeedPay).subtract(beginNeedGet);
                    s.setAllNeedGet(BigDecimal.ZERO);
                    s.setAllNeedPay(sum);
                }
                resList.add(s);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return resList;
    }

    public Long countSupplier(String supplier, String type, String phonenum, String telephone) throws Exception{
        Long result=null;
        try{
            result=supplierMapperEx.countsBySupplier(supplier, type, phonenum, telephone);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSupplier(JSONObject obj, HttpServletRequest request)throws Exception {
        Supplier supplier = JSONObject.parseObject(obj.toJSONString(), Supplier.class);
        int result=0;
        try{
            supplier.setEnabled(true);
            result=supplierMapper.insertSelective(supplier);
            logService.insertLog("商家",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(supplier.getSupplier()).toString(),request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSupplier(JSONObject obj, HttpServletRequest request)throws Exception {
        Supplier supplier = JSONObject.parseObject(obj.toJSONString(), Supplier.class);
        if(supplier.getBeginNeedPay() == null) {
            supplier.setBeginNeedPay(BigDecimal.ZERO);
        }
        if(supplier.getBeginNeedGet() == null) {
            supplier.setBeginNeedGet(BigDecimal.ZERO);
        }
        int result=0;
        try{
            result=supplierMapper.updateByPrimaryKeySelective(supplier);
            logService.insertLog("商家",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(supplier.getSupplier()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSupplier(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteSupplierByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSupplier(String ids, HttpServletRequest request) throws Exception{
        return batchDeleteSupplierByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSupplierByIds(String ids)throws Exception {
        int result=0;
        String [] idArray=ids.split(",");
        //校验财务主表	jsh_accounthead
        List<AccountHead> accountHeadList=null;
        try{
            accountHeadList = accountHeadMapperEx.getAccountHeadListByOrganIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,OrganIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //校验单据主表	jsh_depot_head
        List<DepotHead> depotHeadList=null;
        try{
            depotHeadList = depotHeadMapperEx.getDepotHeadListByOrganIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,OrganIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Supplier> list = getSupplierListByIds(ids);
        for(Supplier supplier: list){
            sb.append("[").append(supplier.getSupplier()).append("]");
        }
        logService.insertLog("商家", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            result = supplierMapperEx.batchDeleteSupplierByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdNotEqualTo(id).andSupplierEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Supplier> list=null;
        try{
            list= supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAdvanceIn(Long supplierId, BigDecimal advanceIn)throws Exception{
        logService.insertLog("商家",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(supplierId).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        Supplier supplier=null;
        try{
            supplier = supplierMapper.selectByPrimaryKey(supplierId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        int result=0;
        try{
            if(supplier!=null){
                supplier.setAdvanceIn(supplier.getAdvanceIn().add(advanceIn));  //增加预收款的金额，可能增加的是负值
                 result=supplierMapper.updateByPrimaryKeySelective(supplier);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<Supplier> findBySelectCus()throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("客户").andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Supplier> list=null;
        try{
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> findBySelectSup()throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("供应商").andEnabledEqualTo(true)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Supplier> list=null;
        try{
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> findBySelectRetail()throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeLike("会员").andEnabledEqualTo(true)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Supplier> list=null;
        try{
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> findById(Long supplierId)throws Exception {
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdEqualTo(supplierId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Supplier> list=null;
        try{
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("商家",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> supplierIds = StringUtil.strToLongList(ids);
        Supplier supplier = new Supplier();
        supplier.setEnabled(status);
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdIn(supplierIds);
        int result=0;
        try{
            result = supplierMapper.updateByExampleSelective(supplier, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<Supplier> findUserCustomer()throws Exception{
        SupplierExample example = new SupplierExample();
        example.createCriteria().andTypeEqualTo("客户")
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Supplier> list=null;
        try{
            list = supplierMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Supplier> findByAll(String supplier, String type, String phonenum, String telephone) throws Exception{
        List<Supplier> list=null;
        try{
            list = supplierMapperEx.findByAll(supplier, type, phonenum, telephone);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BaseResponseInfo importExcel(List<Supplier> mList) throws Exception {
        logService.insertLog("商家",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_IMPORT).append(mList.size()).append(BusinessConstants.LOG_DATA_UNIT).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            for(Supplier s: mList) {
                SupplierExample example = new SupplierExample();
                example.createCriteria().andSupplierEqualTo(s.getSupplier()).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                List<Supplier> list= supplierMapper.selectByExample(example);
                if(list.size() <= 0) {
                    supplierMapper.insertSelective(s);
                } else {
                    Long id = list.get(0).getId();
                    s.setId(id);
                    supplierMapper.updateByPrimaryKeySelective(s);
                }
            }
            info.code = 200;
            data.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            info.code = 500;
            data.put("message", e.getMessage());
        }
        info.data = data;
        return info;
    }
}
