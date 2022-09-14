package com.jsh.erp.service.depotItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.DepotItemStockWarningCount;
import com.jsh.erp.datasource.vo.DepotItemVo4Stock;
import com.jsh.erp.datasource.vo.DepotItemVoBatchNumberList;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.materialExtend.MaterialExtendService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DepotItemService {
    private Logger logger = LoggerFactory.getLogger(DepotItemService.class);

    private final static String TYPE = "入库";
    private final static String SUM_TYPE = "number";
    private final static String IN = "in";
    private final static String OUT = "out";

    @Resource
    private DepotItemMapper depotItemMapper;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;
    @Resource
    private MaterialService materialService;
    @Resource
    private MaterialExtendService materialExtendService;
    @Resource
    private SerialNumberMapperEx serialNumberMapperEx;
    @Resource
    private DepotHeadService depotHeadService;
    @Resource
    private DepotHeadMapper depotHeadMapper;
    @Resource
    private SerialNumberService serialNumberService;
    @Resource
    private UserService userService;
    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private MaterialCurrentStockMapper materialCurrentStockMapper;
    @Resource
    private LogService logService;

    public DepotItem getDepotItem(long id)throws Exception {
        DepotItem result=null;
        try{
            result=depotItemMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotItem> getDepotItem()throws Exception {
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotItem> list=null;
        try{
            list=depotItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<DepotItem> select(String name, Integer type, String remark, int offset, int rows)throws Exception {
        List<DepotItem> list=null;
        try{
            list=depotItemMapperEx.selectByConditionDepotItem(name, type, remark, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countDepotItem(String name, Integer type, String remark) throws Exception{
        Long result =null;
        try{
            result=depotItemMapperEx.countsByDepotItem(name, type, remark);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotItem(JSONObject obj, HttpServletRequest request)throws Exception {
        DepotItem depotItem = JSONObject.parseObject(obj.toJSONString(), DepotItem.class);
        int result =0;
        try{
            result=depotItemMapper.insertSelective(depotItem);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotItem(JSONObject obj, HttpServletRequest request)throws Exception {
        DepotItem depotItem = JSONObject.parseObject(obj.toJSONString(), DepotItem.class);
        int result =0;
        try{
            result=depotItemMapper.updateByPrimaryKeySelective(depotItem);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotItem(Long id, HttpServletRequest request)throws Exception {
        int result =0;
        try{
            result=depotItemMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotItem(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdIn(idList);
        int result =0;
        try{
            result=depotItemMapper.deleteByExample(example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdNotEqualTo(id).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<DepotItem> list =null;
        try{
            list=depotItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public List<DepotItemVo4DetailByTypeAndMId> findDetailByDepotIdsAndMaterialIdList(String depotIds, String sku, Long mId, int offset, int rows)throws Exception {
        String [] depotIdArray = null;
        if(StringUtil.isNotEmpty(depotIds)) {
            depotIdArray = depotIds.split(",");
        }
        List<DepotItemVo4DetailByTypeAndMId> list =null;
        try{
            list = depotItemMapperEx.findDetailByDepotIdsAndMaterialIdList(depotIdArray, sku, mId, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long findDetailByDepotIdsAndMaterialIdCount(String depotIds, String sku, Long mId)throws Exception {
        String [] depotIdArray = null;
        if(StringUtil.isNotEmpty(depotIds)) {
            depotIdArray = depotIds.split(",");
        }
        Long result =null;
        try{
            result = depotItemMapperEx.findDetailByDepotIdsAndMaterialIdCount(depotIdArray, sku, mId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotItemWithObj(DepotItem depotItem)throws Exception {
        int result =0;
        try{
            result = depotItemMapper.insertSelective(depotItem);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotItemWithObj(DepotItem depotItem)throws Exception {
        int result =0;
        try{
            result = depotItemMapper.updateByPrimaryKeySelective(depotItem);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<DepotItem> getListByHeaderId(Long headerId)throws Exception {
        List<DepotItem> list =null;
        try{
            DepotItemExample example = new DepotItemExample();
            example.createCriteria().andHeaderIdEqualTo(headerId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            list = depotItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    /**
     * 查询当前单据中指定商品的明细信息
     * @param headerId
     * @param meId
     * @return
     * @throws Exception
     */
    public DepotItem getItemByHeaderIdAndMaterial(Long headerId, Long meId)throws Exception {
        DepotItem depotItem = new DepotItem();
        try{
            DepotItemExample example = new DepotItemExample();
            example.createCriteria().andHeaderIdEqualTo(headerId).andMaterialExtendIdEqualTo(meId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            List<DepotItem> list = depotItemMapper.selectByExample(example);
            if(list!=null && list.size()>0) {
                depotItem = list.get(0);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return depotItem;
    }

    /**
     * 查询被关联订单中指定商品的明细信息
     * @param linkNumber
     * @param meId
     * @return
     * @throws Exception
     */
    public DepotItem getPreItemByHeaderIdAndMaterial(String linkNumber, Long meId, Long linkId)throws Exception {
        DepotItem depotItem = new DepotItem();
        try{
            DepotHead depotHead = depotHeadService.getDepotHead(linkNumber);
            DepotItemExample example = new DepotItemExample();
            example.createCriteria().andHeaderIdEqualTo(depotHead.getId()).andMaterialExtendIdEqualTo(meId).andIdEqualTo(linkId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            List<DepotItem> list = depotItemMapper.selectByExample(example);
            if(list!=null && list.size()>0) {
                depotItem = list.get(0);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return depotItem;
    }

    public List<DepotItemVo4WithInfoEx> getDetailList(Long headerId)throws Exception {
        List<DepotItemVo4WithInfoEx> list =null;
        try{
            list = depotItemMapperEx.getDetailList(headerId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<DepotItemVo4WithInfoEx> findByAll(String materialParam, String endTime, Integer offset, Integer rows)throws Exception {
        List<DepotItemVo4WithInfoEx> list =null;
        try{
            list = depotItemMapperEx.findByAll(materialParam, endTime, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findByAllCount(String materialParam, String endTime)throws Exception {
        int result=0;
        try{
            result = depotItemMapperEx.findByAllCount(materialParam, endTime);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<DepotItemVo4WithInfoEx> getListWithBugOrSale(String materialParam, String billType,
                     String beginTime, String endTime, String[] creatorArray, Integer offset, Integer rows)throws Exception {
        List<DepotItemVo4WithInfoEx> list =null;
        try{
            list = depotItemMapperEx.getListWithBugOrSale(materialParam, billType, beginTime, endTime, creatorArray, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int getListWithBugOrSaleCount(String materialParam, String billType,
                     String beginTime, String endTime, String[] creatorArray)throws Exception {
        int result=0;
        try{
            result = depotItemMapperEx.getListWithBugOrSaleCount(materialParam, billType, beginTime, endTime, creatorArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public BigDecimal buyOrSale(String type, String subType, Long MId, String beginTime, String endTime,
                                String[] creatorArray, String sumType) throws Exception{
        BigDecimal result= BigDecimal.ZERO;
        try{
            if (SUM_TYPE.equals(sumType)) {
                result= depotItemMapperEx.buyOrSaleNumber(type, subType, MId, beginTime, endTime, creatorArray, sumType);
            } else {
                result= depotItemMapperEx.buyOrSalePrice(type, subType, MId, beginTime, endTime, creatorArray, sumType);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;

    }

    /**
     * 统计采购或销售的总金额
     * @param type
     * @param subType
     * @param month
     * @return
     * @throws Exception
     */
    public BigDecimal inOrOutPrice(String type, String subType, String month, String roleType) throws Exception{
        BigDecimal result= BigDecimal.ZERO;
        try{
            String [] creatorArray = depotHeadService.getCreatorArray(roleType);
            String beginTime = Tools.firstDayOfMonth(month) + BusinessConstants.DAY_FIRST_TIME;
            String endTime = Tools.lastDayOfMonth(month) + BusinessConstants.DAY_LAST_TIME;
            result = depotItemMapperEx.inOrOutPrice(type, subType, beginTime, endTime, creatorArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    /**
     * 统计零售的总金额
     * @param type
     * @param subType
     * @param month
     * @return
     * @throws Exception
     */
    public BigDecimal inOrOutRetailPrice(String type, String subType, String month, String roleType) throws Exception{
        BigDecimal result= BigDecimal.ZERO;
        try{
            String [] creatorArray = depotHeadService.getCreatorArray(roleType);
            String beginTime = Tools.firstDayOfMonth(month) + BusinessConstants.DAY_FIRST_TIME;
            String endTime = Tools.lastDayOfMonth(month) + BusinessConstants.DAY_LAST_TIME;
            result = depotItemMapperEx.inOrOutRetailPrice(type, subType, beginTime, endTime, creatorArray);
            result = result.abs();
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void saveDetials(String rows, Long headerId, String actionType, HttpServletRequest request) throws Exception{
        //查询单据主表信息
        DepotHead depotHead =depotHeadMapper.selectByPrimaryKey(headerId);
        //删除序列号和回收序列号
        deleteOrCancelSerialNumber(actionType, depotHead, headerId);
        //删除单据的明细
        deleteDepotItemHeadId(headerId);
        JSONArray rowArr = JSONArray.parseArray(rows);
        if (null != rowArr && rowArr.size()>0) {
            for (int i = 0; i < rowArr.size(); i++) {
                DepotItem depotItem = new DepotItem();
                JSONObject rowObj = JSONObject.parseObject(rowArr.getString(i));
                depotItem.setHeaderId(headerId);
                String barCode = rowObj.getString("barCode");
                MaterialExtend materialExtend = materialExtendService.getInfoByBarCode(barCode);
                depotItem.setMaterialId(materialExtend.getMaterialId());
                depotItem.setMaterialExtendId(materialExtend.getId());
                depotItem.setMaterialUnit(rowObj.getString("unit"));
                Material material= materialService.getMaterial(depotItem.getMaterialId());
                if (BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber()) ||
                        BusinessConstants.ENABLE_BATCH_NUMBER_ENABLED.equals(material.getEnableBatchNumber())) {
                    //组装拆卸单不能选择批号或序列号商品
                    if(BusinessConstants.SUB_TYPE_ASSEMBLE.equals(depotHead.getSubType()) ||
                            BusinessConstants.SUB_TYPE_DISASSEMBLE.equals(depotHead.getSubType())) {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_ASSEMBLE_SELECT_ERROR_CODE,
                                String.format(ExceptionConstants.MATERIAL_ASSEMBLE_SELECT_ERROR_MSG, barCode));
                    }
                    //调拨单不能选择批号或序列号商品（该场景走出库和入库单）
                    if(BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_TRANSFER_SELECT_ERROR_CODE,
                                String.format(ExceptionConstants.MATERIAL_TRANSFER_SELECT_ERROR_MSG, barCode));
                    }
                }
                if (StringUtil.isExist(rowObj.get("snList"))) {
                    depotItem.setSnList(rowObj.getString("snList"));
                    if(StringUtil.isExist(rowObj.get("depotId"))) {
                        Long depotId = rowObj.getLong("depotId");
                        serialNumberService.addSerialNumberByBill(depotHead.getType(), depotHead.getSubType(),
                                depotHead.getNumber(), materialExtend.getMaterialId(), depotId, depotItem.getSnList());
                    }
                } else {
                    //入库或出库
                    if(BusinessConstants.DEPOTHEAD_TYPE_IN.equals(depotHead.getType()) ||
                            BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())) {
                        //序列号不能为空
                        if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_SERIAL_NUMBERE_EMPTY_CODE,
                                    String.format(ExceptionConstants.MATERIAL_SERIAL_NUMBERE_EMPTY_MSG, barCode));
                        }
                    }
                }
                if (StringUtil.isExist(rowObj.get("batchNumber"))) {
                    depotItem.setBatchNumber(rowObj.getString("batchNumber"));
                } else {
                    //入库或出库
                    if(BusinessConstants.DEPOTHEAD_TYPE_IN.equals(depotHead.getType()) ||
                            BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())) {
                        //批号不能为空
                        if (BusinessConstants.ENABLE_BATCH_NUMBER_ENABLED.equals(material.getEnableBatchNumber())) {
                            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_BATCH_NUMBERE_EMPTY_CODE,
                                    String.format(ExceptionConstants.DEPOT_HEAD_BATCH_NUMBERE_EMPTY_MSG, barCode));
                        }
                    }
                }
                if (StringUtil.isExist(rowObj.get("expirationDate"))) {
                    depotItem.setExpirationDate(rowObj.getDate("expirationDate"));
                }
                if (StringUtil.isExist(rowObj.get("sku"))) {
                    depotItem.setSku(rowObj.getString("sku"));
                }
                if (StringUtil.isExist(rowObj.get("linkId"))) {
                    depotItem.setLinkId(rowObj.getLong("linkId"));
                }
                //以下进行单位换算
                Unit unitInfo = materialService.findUnit(materialExtend.getMaterialId()); //查询计量单位信息
                if (StringUtil.isExist(rowObj.get("operNumber"))) {
                    depotItem.setOperNumber(rowObj.getBigDecimal("operNumber"));
                    String unit = rowObj.get("unit").toString();
                    BigDecimal oNumber = rowObj.getBigDecimal("operNumber");
                    if (StringUtil.isNotEmpty(unitInfo.getName())) {
                        String basicUnit = unitInfo.getBasicUnit(); //基本单位
                        if (unit.equals(basicUnit)) { //如果等于基本单位
                            depotItem.setBasicNumber(oNumber); //数量一致
                        } else if (unit.equals(unitInfo.getOtherUnit())) { //如果等于副单位
                            depotItem.setBasicNumber(oNumber.multiply(new BigDecimal(unitInfo.getRatio())) ); //数量乘以比例
                        } else if (unit.equals(unitInfo.getOtherUnitTwo())) { //如果等于副单位2
                            depotItem.setBasicNumber(oNumber.multiply(new BigDecimal(unitInfo.getRatioTwo())) ); //数量乘以比例
                        } else if (unit.equals(unitInfo.getOtherUnitThree())) { //如果等于副单位3
                            depotItem.setBasicNumber(oNumber.multiply(new BigDecimal(unitInfo.getRatioThree())) ); //数量乘以比例
                        }
                    } else {
                        depotItem.setBasicNumber(oNumber); //其他情况
                    }
                }
                //如果数量+已完成数量>原订单数量，给出预警(判断前提是存在关联订单)
                if (StringUtil.isNotEmpty(depotHead.getLinkNumber())
                        && StringUtil.isExist(rowObj.get("preNumber")) && StringUtil.isExist(rowObj.get("finishNumber"))) {
                    if("add".equals(actionType)) {
                        //在新增模式进行状态赋值
                        BigDecimal preNumber = rowObj.getBigDecimal("preNumber");
                        BigDecimal finishNumber = rowObj.getBigDecimal("finishNumber");
                        if(depotItem.getOperNumber().add(finishNumber).compareTo(preNumber)>0) {
                            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_CODE,
                                    String.format(ExceptionConstants.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG, barCode));
                        }
                    } else if("update".equals(actionType)) {
                        //在更新模式进行状态赋值
                        String unit = rowObj.get("unit").toString();
                        Long preHeaderId = depotHeadService.getDepotHead(depotHead.getLinkNumber()).getId();
                        //前一个单据的数量
                        BigDecimal preNumber = getPreItemByHeaderIdAndMaterial(depotHead.getLinkNumber(), depotItem.getMaterialExtendId(), depotItem.getLinkId()).getOperNumber();
                        //除去此单据之外的已入库|已出库
                        BigDecimal realFinishNumber = getRealFinishNumber(depotItem.getMaterialExtendId(), depotItem.getLinkId(), preHeaderId, headerId, unitInfo, unit);
                        if(depotItem.getOperNumber().add(realFinishNumber).compareTo(preNumber)>0) {
                            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_CODE,
                                    String.format(ExceptionConstants.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG, barCode));
                        }
                    }
                }
                if (StringUtil.isExist(rowObj.get("unitPrice"))) {
                    BigDecimal unitPrice = rowObj.getBigDecimal("unitPrice");
                    depotItem.setUnitPrice(unitPrice);
                    if(materialExtend.getLowDecimal()!=null) {
                        //零售或销售单价低于最低售价，进行提示
                        if("零售".equals(depotHead.getSubType()) || "销售".equals(depotHead.getSubType())) {
                            if (unitPrice.compareTo(materialExtend.getLowDecimal()) < 0) {
                                throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_UNIT_PRICE_LOW_CODE,
                                        String.format(ExceptionConstants.DEPOT_HEAD_UNIT_PRICE_LOW_MSG, barCode));
                            }
                        }
                    }
                }
                if (StringUtil.isExist(rowObj.get("taxUnitPrice"))) {
                    depotItem.setTaxUnitPrice(rowObj.getBigDecimal("taxUnitPrice"));
                }
                if (StringUtil.isExist(rowObj.get("allPrice"))) {
                    depotItem.setAllPrice(rowObj.getBigDecimal("allPrice"));
                }
                if (StringUtil.isExist(rowObj.get("depotId"))) {
                    depotItem.setDepotId(rowObj.getLong("depotId"));
                } else {
                    if(!BusinessConstants.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())
                            && !BusinessConstants.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
                        throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_DEPOT_FAILED_CODE,
                                String.format(ExceptionConstants.DEPOT_HEAD_DEPOT_FAILED_MSG));
                    }
                }
                if(BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                    if (StringUtil.isExist(rowObj.get("anotherDepotId"))) {
                        if(rowObj.getLong("anotherDepotId").equals(rowObj.getLong("depotId"))) {
                            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_CODE,
                                    String.format(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_MSG));
                        } else {
                            depotItem.setAnotherDepotId(rowObj.getLong("anotherDepotId"));
                        }
                    } else {
                        throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_FAILED_CODE,
                                String.format(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_FAILED_MSG));
                    }
                }
                if (StringUtil.isExist(rowObj.get("taxRate"))) {
                    depotItem.setTaxRate(rowObj.getBigDecimal("taxRate"));
                }
                if (StringUtil.isExist(rowObj.get("taxMoney"))) {
                    depotItem.setTaxMoney(rowObj.getBigDecimal("taxMoney"));
                }
                if (StringUtil.isExist(rowObj.get("taxLastMoney"))) {
                    depotItem.setTaxLastMoney(rowObj.getBigDecimal("taxLastMoney"));
                }
                if (StringUtil.isExist(rowObj.get("mType"))) {
                    depotItem.setMaterialType(rowObj.getString("mType"));
                }
                if (StringUtil.isExist(rowObj.get("remark"))) {
                    depotItem.setRemark(rowObj.getString("remark"));
                }
                //出库时判断库存是否充足
                if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())){
                    BigDecimal stock = getStockByParam(depotItem.getDepotId(),depotItem.getMaterialId(),null,null);
                    if(StringUtil.isNotEmpty(depotItem.getSku())) {
                        //对于sku商品要换个方式计算库存
                        stock = getSkuStockByParam(depotItem.getDepotId(),depotItem.getMaterialExtendId(),null,null);
                    }
                    BigDecimal thisBasicNumber = depotItem.getBasicNumber()==null?BigDecimal.ZERO:depotItem.getBasicNumber();
                    if(!systemConfigService.getMinusStockFlag() && stock.compareTo(thisBasicNumber)<0){
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_CODE,
                                String.format(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_MSG, material.getName()));
                    }
                    //出库时处理序列号
                    if(!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                        //判断商品是否开启序列号，开启的售出序列号，未开启的跳过
                        if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                            //售出序列号，获得当前操作人
                            User userInfo=userService.getCurrentUser();
                            serialNumberService.checkAndUpdateSerialNumber(depotItem, depotHead.getNumber(), userInfo, StringUtil.toNull(depotItem.getSnList()));
                        }
                    }
                }
                this.insertDepotItemWithObj(depotItem);
                //更新当前库存
                updateCurrentStock(depotItem);
            }
            //如果关联单据号非空则更新订单的状态,单据类型：采购入库单或销售出库单或盘点复盘单
            if(BusinessConstants.SUB_TYPE_PURCHASE.equals(depotHead.getSubType())
                    || BusinessConstants.SUB_TYPE_SALES.equals(depotHead.getSubType())
                    || BusinessConstants.SUB_TYPE_REPLAY.equals(depotHead.getSubType())) {
                if(StringUtil.isNotEmpty(depotHead.getLinkNumber())) {
                    //单据状态:是否全部完成 2-全部完成 3-部分完成（针对订单的分批出入库）
                    String billStatus = getBillStatusByParam(depotHead);
                    changeBillStatus(depotHead, billStatus);
                }
            }
            //如果关联单据号非空则更新订单的状态,此处针对销售订单转采购订单的场景
            if(BusinessConstants.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())) {
                if(StringUtil.isNotEmpty(depotHead.getLinkNumber())) {
                    String billStatus = getBillStatusByParam(depotHead);
                    changeBillPurchaseStatus(depotHead, billStatus);
                }
            }
        } else {
            throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ROW_FAILED_CODE,
                    String.format(ExceptionConstants.DEPOT_HEAD_ROW_FAILED_MSG));
        }
    }
    /**
     * 判断单据的状态
     * 通过数组对比：原单据的商品和商品数量（汇总） 与 分批操作后单据的商品和商品数量（汇总）
     * @param depotHead
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String getBillStatusByParam(DepotHead depotHead) {
        String res = BusinessConstants.BILLS_STATUS_SKIPED;
        //获取原单据的商品和商品数量（汇总）
        List<DepotItemVo4MaterialAndSum> linkList = depotItemMapperEx.getLinkBillDetailMaterialSum(depotHead.getLinkNumber());
        //获取分批操作后单据的商品和商品数量（汇总）
        List<DepotItemVo4MaterialAndSum> batchList = depotItemMapperEx.getBatchBillDetailMaterialSum(depotHead.getLinkNumber(), depotHead.getType());
        //将分批操作后的单据的商品和商品数据构造成Map
        Map<Long, BigDecimal> materialSumMap = new HashMap<>();
        for(DepotItemVo4MaterialAndSum materialAndSum : batchList) {
            materialSumMap.put(materialAndSum.getMaterialExtendId(), materialAndSum.getOperNumber());
        }
        for(DepotItemVo4MaterialAndSum materialAndSum : linkList) {
            BigDecimal materialSum = materialSumMap.get(materialAndSum.getMaterialExtendId());
            if(materialSum!=null) {
                if(materialSum.compareTo(materialAndSum.getOperNumber()) != 0) {
                    res = BusinessConstants.BILLS_STATUS_SKIPING;
                }
            } else {
                res = BusinessConstants.BILLS_STATUS_SKIPING;
            }
        }
        return res;
    }

    /**
     * 更新单据状态
     * @param depotHead
     * @param billStatus
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void changeBillStatus(DepotHead depotHead, String billStatus) {
        DepotHead depotHeadOrders = new DepotHead();
        depotHeadOrders.setStatus(billStatus);
        DepotHeadExample example = new DepotHeadExample();
        List<String> linkNumberList = StringUtil.strToStringList(depotHead.getLinkNumber());
        example.createCriteria().andNumberIn(linkNumberList);
        try{
            depotHeadMapper.updateByExampleSelective(depotHeadOrders, example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
    }

    /**
     * 更新单据状态,此处针对销售订单转采购订单的场景
     * @param depotHead
     * @param billStatus
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void changeBillPurchaseStatus(DepotHead depotHead, String billStatus) {
        DepotHead depotHeadOrders = new DepotHead();
        depotHeadOrders.setPurchaseStatus(billStatus);
        DepotHeadExample example = new DepotHeadExample();
        List<String> linkNumberList = StringUtil.strToStringList(depotHead.getLinkNumber());
        example.createCriteria().andNumberIn(linkNumberList);
        try{
            depotHeadMapper.updateByExampleSelective(depotHeadOrders, example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteDepotItemHeadId(Long headerId)throws Exception {
        try{
            //1、查询删除前的单据明细
            List<DepotItem> depotItemList = getListByHeaderId(headerId);
            //2、删除单据明细
            DepotItemExample example = new DepotItemExample();
            example.createCriteria().andHeaderIdEqualTo(headerId);
            depotItemMapper.deleteByExample(example);
            //3、计算删除之后单据明细中商品的库存
            for(DepotItem depotItem : depotItemList){
                updateCurrentStock(depotItem);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
    }

    /**
     * 删除序列号和回收序列号
     * @param actionType
     * @throws Exception
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteOrCancelSerialNumber(String actionType, DepotHead depotHead, Long headerId) throws Exception {
        if(actionType.equals("update")) {
            User userInfo = userService.getCurrentUser();
            if(BusinessConstants.DEPOTHEAD_TYPE_IN.equals(depotHead.getType())){
                //入库逻辑
                String number = depotHead.getNumber();
                SerialNumberExample example = new SerialNumberExample();
                example.createCriteria().andInBillNoEqualTo(number);
                serialNumberService.deleteByExample(example);
            } else if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())){
                //出库逻辑
                DepotItemExample example = new DepotItemExample();
                example.createCriteria().andHeaderIdEqualTo(headerId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                List<DepotItem> depotItemList = depotItemMapper.selectByExample(example);
                if(null != depotItemList && depotItemList.size() > 0){
                    for (DepotItem depotItem : depotItemList){
                        if(StringUtil.isNotEmpty(depotItem.getSnList())){
                            serialNumberService.cancelSerialNumber(depotItem.getMaterialId(), depotHead.getNumber(), (depotItem.getBasicNumber() == null ? 0 : depotItem.getBasicNumber()).intValue(), userInfo);
                        }
                    }
                }
            }
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public List<DepotItemStockWarningCount> findStockWarningCount(Integer offset, Integer rows, String materialParam, List<Long> depotList) {
        List<DepotItemStockWarningCount> list = null;
        try{
            list =depotItemMapperEx.findStockWarningCount(offset, rows, materialParam, depotList);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int findStockWarningCountTotal(String materialParam, List<Long> depotList) {
        int result = 0;
        try{
            result =depotItemMapperEx.findStockWarningCountTotal(materialParam, depotList);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    /**
     * 库存统计-sku
     * @param depotId
     * @param meId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getSkuStockByParam(Long depotId, Long meId, String beginTime, String endTime){
        DepotItemVo4Stock stockObj = depotItemMapperEx.getSkuStockByParam(depotId, meId, beginTime, endTime);
        BigDecimal stockSum = BigDecimal.ZERO;
        if(stockObj!=null) {
            BigDecimal inTotal = stockObj.getInTotal();
            BigDecimal transfInTotal = stockObj.getTransfInTotal();
            BigDecimal assemInTotal = stockObj.getAssemInTotal();
            BigDecimal disAssemInTotal = stockObj.getDisAssemInTotal();
            BigDecimal outTotal = stockObj.getOutTotal();
            BigDecimal transfOutTotal = stockObj.getTransfOutTotal();
            BigDecimal assemOutTotal = stockObj.getAssemOutTotal();
            BigDecimal disAssemOutTotal = stockObj.getDisAssemOutTotal();
            stockSum = inTotal.add(transfInTotal).add(assemInTotal).add(disAssemInTotal)
                    .subtract(outTotal).subtract(transfOutTotal).subtract(assemOutTotal).subtract(disAssemOutTotal);
        }
        return stockSum;
    }

    /**
     * 库存统计-单仓库
     * @param depotId
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getStockByParam(Long depotId, Long mId, String beginTime, String endTime){
        List<Long> depotList = new ArrayList<>();
        if(depotId != null) {
            depotList.add(depotId);
        }
        return getStockByParamWithDepotList(depotList, mId, beginTime, endTime);
    }

    /**
     * 库存统计-多仓库
     * @param depotList
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getStockByParamWithDepotList(List<Long> depotList, Long mId, String beginTime, String endTime){
        //初始库存
        BigDecimal initStock = materialService.getInitStockByMidAndDepotList(depotList, mId);
        //盘点复盘后数量的变动
        BigDecimal stockCheckSum = depotItemMapperEx.getStockCheckSumByDepotList(depotList, mId, beginTime, endTime);
        DepotItemVo4Stock stockObj = depotItemMapperEx.getStockByParamWithDepotList(depotList, mId, beginTime, endTime);
        BigDecimal stockSum = BigDecimal.ZERO;
        if(stockObj!=null) {
            BigDecimal inTotal = stockObj.getInTotal();
            BigDecimal transfInTotal = stockObj.getTransfInTotal();
            BigDecimal assemInTotal = stockObj.getAssemInTotal();
            BigDecimal disAssemInTotal = stockObj.getDisAssemInTotal();
            BigDecimal outTotal = stockObj.getOutTotal();
            BigDecimal transfOutTotal = stockObj.getTransfOutTotal();
            BigDecimal assemOutTotal = stockObj.getAssemOutTotal();
            BigDecimal disAssemOutTotal = stockObj.getDisAssemOutTotal();
            stockSum = inTotal.add(transfInTotal).add(assemInTotal).add(disAssemInTotal)
                    .subtract(outTotal).subtract(transfOutTotal).subtract(assemOutTotal).subtract(disAssemOutTotal);
        }
        return initStock.add(stockCheckSum).add(stockSum);
    }

    /**
     * 统计时间段内的入库和出库数量-多仓库
     * @param depotList
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public Map<String, BigDecimal> getIntervalMapByParamWithDepotList(List<Long> depotList, Long mId, String beginTime, String endTime){
        Map<String,BigDecimal> intervalMap = new HashMap<>();
        BigDecimal inSum = BigDecimal.ZERO;
        BigDecimal outSum = BigDecimal.ZERO;
        //盘点复盘后数量的变动
        BigDecimal stockCheckSum = depotItemMapperEx.getStockCheckSumByDepotList(depotList, mId, beginTime, endTime);
        DepotItemVo4Stock stockObj = depotItemMapperEx.getStockByParamWithDepotList(depotList, mId, beginTime, endTime);
        if(stockObj!=null) {
            BigDecimal inTotal = stockObj.getInTotal();
            BigDecimal transfInTotal = stockObj.getTransfInTotal();
            BigDecimal assemInTotal = stockObj.getAssemInTotal();
            BigDecimal disAssemInTotal = stockObj.getDisAssemInTotal();
            inSum = inTotal.add(transfInTotal).add(assemInTotal).add(disAssemInTotal);
            BigDecimal outTotal = stockObj.getOutTotal();
            BigDecimal transfOutTotal = stockObj.getTransfOutTotal();
            BigDecimal assemOutTotal = stockObj.getAssemOutTotal();
            BigDecimal disAssemOutTotal = stockObj.getDisAssemOutTotal();
            outSum = outTotal.add(transfOutTotal).add(assemOutTotal).add(disAssemOutTotal);
        }
        if(stockCheckSum.compareTo(BigDecimal.ZERO)>0) {
            inSum = inSum.add(stockCheckSum);
        } else {
            //盘点复盘数量为负数代表出库
            outSum = outSum.subtract(stockCheckSum);
        }
        intervalMap.put("inSum", inSum);
        intervalMap.put("outSum", outSum);
        return intervalMap;
    }

    /**
     * 根据单据明细来批量更新当前库存
     * @param depotItem
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateCurrentStock(DepotItem depotItem){
        updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getDepotId());
        if(depotItem.getAnotherDepotId()!=null){
            updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getAnotherDepotId());
        }
    }

    /**
     * 根据商品和仓库来更新当前库存
     * @param mId
     * @param dId
     */
    public void updateCurrentStockFun(Long mId, Long dId) {
        if(mId!=null && dId!=null) {
            MaterialCurrentStockExample example = new MaterialCurrentStockExample();
            example.createCriteria().andMaterialIdEqualTo(mId).andDepotIdEqualTo(dId)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            List<MaterialCurrentStock> list = materialCurrentStockMapper.selectByExample(example);
            MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
            materialCurrentStock.setMaterialId(mId);
            materialCurrentStock.setDepotId(dId);
            materialCurrentStock.setCurrentNumber(getStockByParam(dId,mId,null,null));
            if(list!=null && list.size()>0) {
                Long mcsId = list.get(0).getId();
                materialCurrentStock.setId(mcsId);
                materialCurrentStockMapper.updateByPrimaryKeySelective(materialCurrentStock);
            } else {
                materialCurrentStockMapper.insertSelective(materialCurrentStock);
            }
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BigDecimal getFinishNumber(Long meId, Long id, Long headerId, Unit unitInfo, String materialUnit, String linkType) {
        Long linkId = id;
        String goToType = "";
        DepotHead depotHead =depotHeadMapper.selectByPrimaryKey(headerId);
        String linkNumber = depotHead.getNumber(); //订单号
        if("purchase".equals(linkType)) {
            //针对以销定购的情况
            if(BusinessConstants.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
                goToType = BusinessConstants.SUB_TYPE_PURCHASE_ORDER;
            }
        } else {
            //采购订单转采购入库
            if(BusinessConstants.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())) {
                goToType = BusinessConstants.SUB_TYPE_PURCHASE;
            }
            //销售订单转销售出库
            if(BusinessConstants.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
                goToType = BusinessConstants.SUB_TYPE_SALES;
            }
            //采购入库转采购退货
            if(BusinessConstants.SUB_TYPE_PURCHASE.equals(depotHead.getSubType())) {
                goToType = BusinessConstants.SUB_TYPE_PURCHASE_RETURN;
            }
            //销售出库转销售退货
            if(BusinessConstants.SUB_TYPE_SALES.equals(depotHead.getSubType())) {
                goToType = BusinessConstants.SUB_TYPE_SALES_RETURN;
            }
        }
        BigDecimal count = depotItemMapperEx.getFinishNumber(meId, linkId, linkNumber, goToType);
        //根据多单位情况进行数量的转换
        if(materialUnit.equals(unitInfo.getOtherUnit()) && unitInfo.getRatio() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatio()),2,BigDecimal.ROUND_HALF_UP);
        }
        if(materialUnit.equals(unitInfo.getOtherUnitTwo()) && unitInfo.getRatioTwo() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatioTwo()),2,BigDecimal.ROUND_HALF_UP);
        }
        if(materialUnit.equals(unitInfo.getOtherUnitThree()) && unitInfo.getRatioThree() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatioThree()),2,BigDecimal.ROUND_HALF_UP);
        }
        return count;
    }

    /**
     * 除去此单据之外的已入库|已出库
     * @param meId
     * @param linkId
     * @param preHeaderId
     * @param currentHeaderId
     * @param unitInfo
     * @param materialUnit
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BigDecimal getRealFinishNumber(Long meId, Long linkId, Long preHeaderId, Long currentHeaderId, Unit unitInfo, String materialUnit) {
        String goToType = "";
        DepotHead depotHead =depotHeadMapper.selectByPrimaryKey(preHeaderId);
        String linkNumber = depotHead.getNumber(); //订单号
        if(BusinessConstants.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())) {
            goToType = BusinessConstants.SUB_TYPE_PURCHASE;
        }
        if(BusinessConstants.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
            goToType = BusinessConstants.SUB_TYPE_SALES;
        }
        BigDecimal count = depotItemMapperEx.getRealFinishNumber(meId, linkId, linkNumber, currentHeaderId, goToType);
        //根据多单位情况进行数量的转换
        if(materialUnit.equals(unitInfo.getOtherUnit()) && unitInfo.getRatio() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatio()),2,BigDecimal.ROUND_HALF_UP);
        }
        if(materialUnit.equals(unitInfo.getOtherUnitTwo()) && unitInfo.getRatioTwo() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatioTwo()),2,BigDecimal.ROUND_HALF_UP);
        }
        if(materialUnit.equals(unitInfo.getOtherUnitThree()) && unitInfo.getRatioThree() != 0) {
            count = count.divide(BigDecimal.valueOf(unitInfo.getRatioThree()),2,BigDecimal.ROUND_HALF_UP);
        }
        return count;
    }

    public List<DepotItemVoBatchNumberList> getBatchNumberList(String number, String name, Long depotId, String barCode, String batchNumber){
        return depotItemMapperEx.getBatchNumberList(StringUtil.toNull(number), name, depotId, barCode, batchNumber);
    }

    public Long getCountByMaterialAndDepot(Long mId, Long depotId) {
        return depotItemMapperEx.getCountByMaterialAndDepot(mId, depotId);
    }
}
