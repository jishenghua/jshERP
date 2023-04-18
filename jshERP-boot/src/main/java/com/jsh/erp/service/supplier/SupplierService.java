package com.jsh.erp.service.supplier;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.accountHead.AccountHeadService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ExcelUtils;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;


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
    private AccountItemMapperEx accountItemMapperEx;
    @Resource
    private DepotHeadService depotHeadService;
    @Resource
    private AccountHeadService accountHeadService;
    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private UserBusinessService userBusinessService;

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
                String beginTime = Tools.getYearBegin();
                String endTime = Tools.getCenternTime(new Date());
                BigDecimal sum = BigDecimal.ZERO;
                String supplierType = type;
                String inOutType = "";
                String subType = "";
                String typeBack = "";
                String subTypeBack = "";
                String billType = "";
                if (("供应商").equals(supplierType)) {
                    inOutType = "入库";
                    subType = "采购";
                    typeBack = "出库";
                    subTypeBack = "采购退货";
                    billType = "付款";
                } else if (("客户").equals(supplierType)) {
                    inOutType = "出库";
                    subType = "销售";
                    typeBack = "入库";
                    subTypeBack = "销售退货";
                    billType = "收款";
                }
                List<DepotHeadVo4StatementAccount> saList = depotHeadService.getStatementAccount(beginTime, endTime, supplierId, null,
                        supplierType, inOutType, subType, typeBack, subTypeBack, billType, null, null);
                if(saList.size()>0) {
                    DepotHeadVo4StatementAccount item = saList.get(0);
                    //期初 = 起始期初金额+上期欠款金额-上期退货的欠款金额-上期收付款
                    BigDecimal preNeed = item.getBeginNeed().add(item.getPreDebtMoney()).subtract(item.getPreReturnDebtMoney()).subtract(item.getPreBackMoney());
                    item.setPreNeed(preNeed);
                    //实际欠款 = 本期欠款-本期退货的欠款金额
                    BigDecimal realDebtMoney = item.getDebtMoney().subtract(item.getReturnDebtMoney());
                    item.setDebtMoney(realDebtMoney);
                    //期末 = 期初+实际欠款-本期收款
                    BigDecimal allNeedGet = preNeed.add(realDebtMoney).subtract(item.getBackMoney());
                    sum = sum.add(allNeedGet);
                }
                if(("客户").equals(s.getType())) {
                    s.setAllNeedGet(sum);
                } else if(("供应商").equals(s.getType())) {
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
            //新增客户时给当前用户自动授权
            if("客户".equals(supplier.getType())) {
                Long userId = userService.getUserId(request);
                Supplier sInfo = supplierMapperEx.getSupplierByNameAndType(supplier.getSupplier(), supplier.getType());
                String ubKey = "[" + sInfo.getId() + "]";
                List<UserBusiness> ubList = userBusinessService.getBasicData(userId.toString(), "UserCustomer");
                if(ubList ==null || ubList.size() == 0) {
                    JSONObject ubObj = new JSONObject();
                    ubObj.put("type", "UserCustomer");
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
            }
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

    public int checkIsNameAndTypeExist(Long id, String name, String type)throws Exception {
        name = name == null? "": name;
        SupplierExample example = new SupplierExample();
        example.createCriteria().andIdNotEqualTo(id).andSupplierEqualTo(name).andTypeEqualTo(type)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
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
        example.setOrderByClause("sort asc, id desc");
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
        example.setOrderByClause("sort asc, id desc");
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
        example.setOrderByClause("sort asc, id desc");
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
        example.setOrderByClause("sort asc, id desc");
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
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ENABLED).toString(),
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
        example.setOrderByClause("sort asc, id desc");
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

    public Map<String, Object> getBeginNeedByOrganId(Long organId) throws Exception {
        Supplier supplier = getSupplier(organId);
        Map<String, Object> map = new HashMap<>();
        BigDecimal needDebt = BigDecimal.ZERO;
        if("供应商".equals(supplier.getType())) {
            needDebt = supplier.getBeginNeedPay();
        } else if("客户".equals(supplier.getType())) {
            needDebt = supplier.getBeginNeedGet();
        }
        BigDecimal finishDebt = accountItemMapperEx.getFinishDebtByOrganId(organId).abs();
        BigDecimal eachAmount = BigDecimal.ZERO;
        if(needDebt != null) {
            eachAmount = needDebt.subtract(finishDebt);
        }
        //应收欠款
        map.put("needDebt", needDebt);
        //已收欠款
        map.put("finishDebt", finishDebt);
        //本次收款
        map.put("eachAmount", eachAmount);
        return map;
    }

    public void importVendor(MultipartFile file, HttpServletRequest request) throws Exception{
        String type = "供应商";
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        Sheet src = workbook.getSheet(0);
        //'名称', '联系人', '手机号码', '联系电话', '电子邮箱', '传真', '期初应付', '纳税人识别号', '税率(%)', '开户行', '账号', '地址', '备注', '排序', '状态'
        List<Supplier> sList = new ArrayList<>();
        for (int i = 2; i < src.getRows(); i++) {
            String supplierName = ExcelUtils.getContent(src, i, 0);
            String enabled = ExcelUtils.getContent(src, i, 14);
            if(StringUtil.isNotEmpty(supplierName) && StringUtil.isNotEmpty(enabled)) {
                Supplier s = new Supplier();
                s.setType(type);
                s.setSupplier(supplierName);
                s.setContacts(ExcelUtils.getContent(src, i, 1));
                s.setTelephone(ExcelUtils.getContent(src, i, 2));
                s.setPhoneNum(ExcelUtils.getContent(src, i, 3));
                s.setEmail(ExcelUtils.getContent(src, i, 4));
                s.setFax(ExcelUtils.getContent(src, i, 5));
                s.setBeginNeedPay(parseBigDecimalEx(ExcelUtils.getContent(src, i, 6)));
                s.setTaxNum(ExcelUtils.getContent(src, i, 7));
                s.setTaxRate(parseBigDecimalEx(ExcelUtils.getContent(src, i, 8)));
                s.setBankName(ExcelUtils.getContent(src, i, 9));
                s.setAccountNumber(ExcelUtils.getContent(src, i, 10));
                s.setAddress(ExcelUtils.getContent(src, i, 11));
                s.setDescription(ExcelUtils.getContent(src, i, 12));
                s.setSort(ExcelUtils.getContent(src, i, 13));
                s.setEnabled("1".equals(enabled));
                sList.add(s);
            }
        }
        importExcel(sList, type);
    }

    public void importCustomer(MultipartFile file, HttpServletRequest request) throws Exception{
        String type = "客户";
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        Sheet src = workbook.getSheet(0);
        //'名称', '联系人', '手机号码', '联系电话', '电子邮箱', '传真', '期初应收', '纳税人识别号', '税率(%)', '开户行', '账号', '地址', '备注', '排序', '状态'
        List<Supplier> sList = new ArrayList<>();
        for (int i = 2; i < src.getRows(); i++) {
            String supplierName = ExcelUtils.getContent(src, i, 0);
            String enabled = ExcelUtils.getContent(src, i, 14);
            if(StringUtil.isNotEmpty(supplierName) && StringUtil.isNotEmpty(enabled)) {
                Supplier s = new Supplier();
                s.setType(type);
                s.setSupplier(supplierName);
                s.setContacts(ExcelUtils.getContent(src, i, 1));
                s.setTelephone(ExcelUtils.getContent(src, i, 2));
                s.setPhoneNum(ExcelUtils.getContent(src, i, 3));
                s.setEmail(ExcelUtils.getContent(src, i, 4));
                s.setFax(ExcelUtils.getContent(src, i, 5));
                s.setBeginNeedGet(parseBigDecimalEx(ExcelUtils.getContent(src, i, 6)));
                s.setTaxNum(ExcelUtils.getContent(src, i, 7));
                s.setTaxRate(parseBigDecimalEx(ExcelUtils.getContent(src, i, 8)));
                s.setBankName(ExcelUtils.getContent(src, i, 9));
                s.setAccountNumber(ExcelUtils.getContent(src, i, 10));
                s.setAddress(ExcelUtils.getContent(src, i, 11));
                s.setDescription(ExcelUtils.getContent(src, i, 12));
                s.setSort(ExcelUtils.getContent(src, i, 13));
                s.setEnabled("1".equals(enabled));
                sList.add(s);
            }
        }
        importExcel(sList, type);
    }

    public void importMember(MultipartFile file, HttpServletRequest request) throws Exception{
        String type = "会员";
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        Sheet src = workbook.getSheet(0);
        //'名称', '联系人', '手机号码', '联系电话', '电子邮箱', '备注', '排序', '状态'
        List<Supplier> sList = new ArrayList<>();
        for (int i = 2; i < src.getRows(); i++) {
            String supplierName = ExcelUtils.getContent(src, i, 0);
            String enabled = ExcelUtils.getContent(src, i, 7);
            if(StringUtil.isNotEmpty(supplierName) && StringUtil.isNotEmpty(enabled)) {
                Supplier s = new Supplier();
                s.setType(type);
                s.setSupplier(supplierName);
                s.setContacts(ExcelUtils.getContent(src, i, 1));
                s.setTelephone(ExcelUtils.getContent(src, i, 2));
                s.setPhoneNum(ExcelUtils.getContent(src, i, 3));
                s.setEmail(ExcelUtils.getContent(src, i, 4));
                s.setDescription(ExcelUtils.getContent(src, i, 5));
                s.setSort(ExcelUtils.getContent(src, i, 6));
                s.setEnabled("1".equals(enabled));
                sList.add(s);
            }
        }
        importExcel(sList, type);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BaseResponseInfo importExcel(List<Supplier> mList, String type) throws Exception {
        logService.insertLog(type,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_IMPORT).append(mList.size()).append(BusinessConstants.LOG_DATA_UNIT).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<>();
        try {
            for(Supplier s: mList) {
                SupplierExample example = new SupplierExample();
                example.createCriteria().andSupplierEqualTo(s.getSupplier()).andTypeEqualTo(type).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
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

    public BigDecimal parseBigDecimalEx(String str)throws Exception{
        if(!StringUtil.isEmpty(str)) {
            return new BigDecimal(str);
        } else {
            return null;
        }
    }

    public File exportExcel(List<Supplier> dataList, String type) throws Exception {
        if("供应商".equals(type)) {
            return exportExcelVendorOrCustomer(dataList, type);
        } else if("客户".equals(type)) {
            return exportExcelVendorOrCustomer(dataList, type);
        } else {
            //会员
            String[] names = {"会员卡号", "联系人", "手机号码", "联系电话", "电子邮箱", "预付款", "备注", "排序", "状态"};
            String title = "信息内容";
            List<String[]> objects = new ArrayList<String[]>();
            if (null != dataList) {
                for (Supplier s : dataList) {
                    String[] objs = new String[10];
                    objs[0] = s.getSupplier();
                    objs[1] = s.getContacts();
                    objs[2] = s.getTelephone();
                    objs[3] = s.getPhoneNum();
                    objs[4] = s.getEmail();
                    objs[5] = s.getAdvanceIn() == null? "" : s.getAdvanceIn().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                    objs[6] = s.getDescription();
                    objs[7] = s.getSort();
                    objs[8] = s.getEnabled() ? "启用" : "禁用";
                    objects.add(objs);
                }
            }
            return ExcelUtils.exportObjectsWithoutTitle(title, "导入时本行内容请勿删除，切记！", names, title, objects);
        }
    }

    private File exportExcelVendorOrCustomer(List<Supplier> dataList, String type) throws Exception {
        String beginNeedStr = "";
        String allNeedStr = "";
        if("供应商".equals(type)) {
            beginNeedStr = "期初应付";
            allNeedStr = "期末应付";
        } else if("客户".equals(type)) {
            beginNeedStr = "期初应收";
            allNeedStr = "期末应收";
        }
        String[] names = {"名称", "联系人", "手机号码", "联系电话", "电子邮箱", "传真", beginNeedStr,
                allNeedStr, "纳税人识别号", "税率(%)", "开户行", "账号", "地址", "备注", "排序", "状态"};
        String title = "信息内容";
        List<String[]> objects = new ArrayList<String[]>();
        if (null != dataList) {
            for (Supplier s : dataList) {
                Integer supplierId = s.getId().intValue();

                String beginTime = Tools.getYearBegin();
                String endTime = Tools.getCenternTime(new Date());
                BigDecimal sum = BigDecimal.ZERO;
                String supplierType = type;
                String inOutType = "";
                String subType = "";
                String typeBack = "";
                String subTypeBack = "";
                String billType = "";
                if (("供应商").equals(supplierType)) {
                    inOutType = "入库";
                    subType = "采购";
                    typeBack = "出库";
                    subTypeBack = "采购退货";
                    billType = "付款";
                } else if (("客户").equals(supplierType)) {
                    inOutType = "出库";
                    subType = "销售";
                    typeBack = "入库";
                    subTypeBack = "销售退货";
                    billType = "收款";
                }
                List<DepotHeadVo4StatementAccount> saList = depotHeadService.getStatementAccount(beginTime, endTime, supplierId, null,
                        supplierType, inOutType, subType, typeBack, subTypeBack, billType, null, null);
                if(saList.size()>0) {
                    DepotHeadVo4StatementAccount item = saList.get(0);
                    //期初 = 起始期初金额+上期欠款金额-上期退货的欠款金额-上期收付款
                    BigDecimal preNeed = item.getBeginNeed().add(item.getPreDebtMoney()).subtract(item.getPreReturnDebtMoney()).subtract(item.getPreBackMoney());
                    item.setPreNeed(preNeed);
                    //实际欠款 = 本期欠款-本期退货的欠款金额
                    BigDecimal realDebtMoney = item.getDebtMoney().subtract(item.getReturnDebtMoney());
                    item.setDebtMoney(realDebtMoney);
                    //期末 = 期初+实际欠款-本期收款
                    BigDecimal allNeedGet = preNeed.add(realDebtMoney).subtract(item.getBackMoney());
                    sum = sum.add(allNeedGet);
                }
                if(("客户").equals(s.getType())) {
                    s.setAllNeedGet(sum);
                } else if(("供应商").equals(s.getType())) {
                    s.setAllNeedPay(sum);
                }
                String[] objs = new String[20];
                objs[0] = s.getSupplier();
                objs[1] = s.getContacts();
                objs[2] = s.getTelephone();
                objs[3] = s.getPhoneNum();
                objs[4] = s.getEmail();
                objs[5] = s.getFax();
                if(("客户").equals(s.getType())) {
                    objs[6] = s.getBeginNeedGet() == null? "" : s.getBeginNeedGet().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                    objs[7] = s.getAllNeedGet() == null? "" : s.getAllNeedGet().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                } else if(("供应商").equals(s.getType())) {
                    objs[6] = s.getBeginNeedPay() == null? "" : s.getBeginNeedPay().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                    objs[7] = s.getAllNeedPay() == null? "" : s.getAllNeedPay().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                }
                objs[8] = s.getTaxNum();
                objs[9] = s.getTaxRate() == null? "" : s.getTaxRate().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                objs[10] = s.getBankName();
                objs[11] = s.getAccountNumber();
                objs[12] = s.getAddress();
                objs[13] = s.getDescription();
                objs[14] = s.getSort();
                objs[15] = s.getEnabled() ? "启用" : "禁用";
                objects.add(objs);
            }
        }
        return ExcelUtils.exportObjectsWithoutTitle(title, "导入时本行内容请勿删除，切记！", names, title, objects);
    }
}
