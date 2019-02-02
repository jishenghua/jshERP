package com.jsh.erp.service.depotItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.DepotHeadMapper;
import com.jsh.erp.datasource.mappers.DepotItemMapper;
import com.jsh.erp.datasource.mappers.DepotItemMapperEx;
import com.jsh.erp.datasource.mappers.SerialNumberMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

@Service
public class DepotItemService {
    private Logger logger = LoggerFactory.getLogger(DepotItemService.class);

    private final static String TYPE = "入库";
    private final static String SUM_TYPE = "Number";
    private final static String IN = "in";
    private final static String OUT = "out";

    @Resource
    private DepotItemMapper depotItemMapper;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;
    @Resource
    private MaterialService materialService;
    @Resource
    SerialNumberMapperEx serialNumberMapperEx;
    @Resource
    private DepotHeadMapper depotHeadMapper;
    @Resource
    SerialNumberService serialNumberService;
    @Resource
    private UserService userService;

    public DepotItem getDepotItem(long id) {
        return depotItemMapper.selectByPrimaryKey(id);
    }

    public List<DepotItem> getDepotItem() {
        DepotItemExample example = new DepotItemExample();
        return depotItemMapper.selectByExample(example);
    }

    public List<DepotItem> select(String name, Integer type, String remark, int offset, int rows) {
        return depotItemMapperEx.selectByConditionDepotItem(name, type, remark, offset, rows);
    }

    public int countDepotItem(String name, Integer type, String remark) {
        return depotItemMapperEx.countsByDepotItem(name, type, remark);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotItem(String beanJson, HttpServletRequest request) {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        return depotItemMapper.insertSelective(depotItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotItem(String beanJson, Long id) {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        depotItem.setId(id);
        return depotItemMapper.updateByPrimaryKeySelective(depotItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepotItem(Long id) {
        return depotItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotItem(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdIn(idList);
        return depotItemMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<DepotItem> list = depotItemMapper.selectByExample(example);
        return list.size();
    }

    public List<DepotItemVo4HeaderId> getHeaderIdByMaterial(String materialParam, String depotIds) {
        return depotItemMapperEx.getHeaderIdByMaterial(materialParam, depotIds);
    }

    public List<DepotItemVo4DetailByTypeAndMId> findDetailByTypeAndMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapperEx.findDetailByTypeAndMaterialIdList(mId, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findDetailByTypeAndMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapperEx.findDetailByTypeAndMaterialIdCounts(mId);
    }

    public List<DepotItemVo4Material> findStockNumByMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapperEx.findStockNumByMaterialIdList(mId, monthTime, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findStockNumByMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapperEx.findStockNumByMaterialIdCounts(mId, monthTime);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepotItemWithObj(DepotItem depotItem) {
        return depotItemMapper.insertSelective(depotItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotItemWithObj(DepotItem depotItem) {
        return depotItemMapper.updateByPrimaryKeySelective(depotItem);
    }

    public int findByTypeAndMaterialId(String type, Long mId) {
        if(type.equals(TYPE)) {
            return depotItemMapperEx.findByTypeAndMaterialIdIn(mId);
        } else {
            return depotItemMapperEx.findByTypeAndMaterialIdOut(mId);
        }
    }

    public List<DepotItemVo4WithInfoEx> getDetailList(Long headerId) {
        return depotItemMapperEx.getDetailList(headerId);
    }

    public List<DepotItemVo4WithInfoEx> findByAll(String headIds, String materialIds, Integer offset, Integer rows) {
        return depotItemMapperEx.findByAll(headIds, materialIds, offset, rows);
    }

    public int findByAllCount(String headIds, String materialIds) {
        return depotItemMapperEx.findByAllCount(headIds, materialIds);
    }

    public BigDecimal findByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapperEx.findByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapperEx.findByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapperEx.findByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapperEx.findByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public BigDecimal findPriceByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapperEx.findPriceByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapperEx.findPriceByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapperEx.findPriceByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapperEx.findPriceByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public BigDecimal buyOrSale(String type, String subType, Long MId, String MonthTime, String sumType) {
        if (SUM_TYPE.equals(sumType)) {
            return depotItemMapperEx.buyOrSaleNumber(type, subType, MId, MonthTime, sumType);
        } else {
            return depotItemMapperEx.buyOrSalePrice(type, subType, MId, MonthTime, sumType);
        }
    }

    public BigDecimal findGiftByType(String subType, Integer ProjectId, Long MId, String type) {
        if (IN.equals(type)) {
            return depotItemMapperEx.findGiftByTypeIn(subType, ProjectId, MId);
        } else {
            return depotItemMapperEx.findGiftByTypeOut(subType, ProjectId, MId);
        }
    }
    /**
     * 2019-02-02修改
     * 我之前对操作数量的理解有偏差
     * 这里重点重申一下：BasicNumber=OperNumber*ratio
     * */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String saveDetials(String inserted, String deleted, String updated, Long headerId) throws Exception{
        //查询单据主表信息
        DepotHead depotHead=depotHeadMapper.selectByPrimaryKey(headerId);
        //获得当前操作人
        User userInfo=userService.getCurrentUser();
        //转为json
            JSONArray insertedJson = JSONArray.parseArray(inserted);
            JSONArray deletedJson = JSONArray.parseArray(deleted);
            JSONArray updatedJson = JSONArray.parseArray(updated);
            /**
             * 2019-01-28优先处理删除的
             * 删除的可以继续卖，删除的需要将使用的序列号回收
             * 插入的需要判断当前货源是否充足
             * 更新的需要判断货源是否充足
             * */
            if (null != deletedJson) {
                for (int i = 0; i < deletedJson.size(); i++) {
                    //首先回收序列号，如果是调拨，不用处理序列号
                    JSONObject tempDeletedJson = JSONObject.parseObject(deletedJson.getString(i));
                    if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                            &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())){
                        DepotItem depotItem = getDepotItem(tempDeletedJson.getLong("Id"));
                        if(depotItem==null){
                            continue;
                        }
                        /**
                         * 判断商品是否开启序列号，开启的收回序列号，未开启的跳过
                         * */
                        Material material= materialService.getMaterial(depotItem.getMaterialid());
                        if(material==null){
                            continue;
                        }
                        if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())){
                            serialNumberMapperEx.cancelSerialNumber(depotItem.getMaterialid(),depotItem.getHeaderid(),depotItem.getBasicnumber().intValue(),
                                    new Date(),userInfo==null?null:userInfo.getId());
                        }
                    }
                    this.deleteDepotItem(tempDeletedJson.getLong("Id"));
                }
            }
            if (null != insertedJson) {
                for (int i = 0; i < insertedJson.size(); i++) {
                    DepotItem depotItem = new DepotItem();
                    JSONObject tempInsertedJson = JSONObject.parseObject(insertedJson.getString(i));
                    depotItem.setHeaderid(headerId);
                    depotItem.setMaterialid(tempInsertedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempInsertedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempInsertedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempInsertedJson.getBigDecimal("OperNumber"));
                        try {
                            String Unit = tempInsertedJson.get("Unit").toString();
                            BigDecimal oNumber = tempInsertedJson.getBigDecimal("OperNumber");
                            Long mId = Long.parseLong(tempInsertedJson.get("MaterialId").toString());
                            /***
                             * 为什么调用的方法要先把基础单位去掉，去掉之后后续还能获取到？
                             * */
                            //以下进行单位换算
//                            String UnitName = findUnitName(mId); //查询计量单位名称
                            String unitName = materialService.findUnitName(mId);
                            if (!StringUtil.isEmpty(unitName)) {
                                String unitList = unitName.substring(0, unitName.indexOf("("));
                                String ratioList = unitName.substring(unitName.indexOf("("));
                                String basicUnit = unitList.substring(0, unitList.indexOf(",")); //基本单位
                                String otherUnit = unitList.substring(unitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(ratioList.substring(ratioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber.multiply(new BigDecimal(ratio)) ); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempInsertedJson.getBigDecimal("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempInsertedJson.getBigDecimal("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempInsertedJson.getBigDecimal("AllPrice"));
                    }
                    depotItem.setRemark(tempInsertedJson.getString("Remark"));
                    if (tempInsertedJson.get("DepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempInsertedJson.getLong("DepotId"));
                    }
                    if (tempInsertedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempInsertedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempInsertedJson.getBigDecimal("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempInsertedJson.getBigDecimal("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempInsertedJson.getBigDecimal("TaxLastMoney"));
                    }
                    if (tempInsertedJson.get("OtherField1") != null) {
                        depotItem.setOtherfield1(tempInsertedJson.getString("OtherField1"));
                    }
                    if (tempInsertedJson.get("OtherField2") != null) {
                        depotItem.setOtherfield2(tempInsertedJson.getString("OtherField2"));
                    }
                    if (tempInsertedJson.get("OtherField3") != null) {
                        depotItem.setOtherfield3(tempInsertedJson.getString("OtherField3"));
                    }
                    if (tempInsertedJson.get("OtherField4") != null) {
                        depotItem.setOtherfield4(tempInsertedJson.getString("OtherField4"));
                    }
                    if (tempInsertedJson.get("OtherField5") != null) {
                        depotItem.setOtherfield5(tempInsertedJson.getString("OtherField5"));
                    }
                    if (tempInsertedJson.get("MType") != null) {
                        depotItem.setMtype(tempInsertedJson.getString("MType"));
                    }
                    /**
                     * 出库时判断库存是否充足
                     * */
                    if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())){
                        if(depotItem==null){
                            continue;
                        }
                        Material material= materialService.getMaterial(depotItem.getMaterialid());
                        if(material==null){
                            continue;
                        }
                        if(getCurrentInStock(depotItem.getMaterialid())<depotItem.getBasicnumber().intValue()){
                            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_CODE,
                                    String.format(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_MSG,material==null?"":material.getName()));
                        }

                            /**出库时处理序列号*/
                        if(!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())) {
                            /**
                             * 判断商品是否开启序列号，开启的收回序列号，未开启的跳过
                             * */
                            if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                                //查询单据子表中开启序列号的数据列表
                                serialNumberService.checkAndUpdateSerialNumber(depotItem, userInfo);
                            }
                        }
                    }
                    this.insertDepotItemWithObj(depotItem);
                }
            }

            if (null != updatedJson) {
                for (int i = 0; i < updatedJson.size(); i++) {
                    JSONObject tempUpdatedJson = JSONObject.parseObject(updatedJson.getString(i));
                    DepotItem depotItem = this.getDepotItem(tempUpdatedJson.getLong("Id"));
                    if(depotItem==null){
                        continue;
                    }
                    Material material= materialService.getMaterial(depotItem.getMaterialid());
                    if(material==null){
                        continue;
                    }
                    //首先回收序列号
                    if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
                            &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())) {
                        /**
                         * 判断商品是否开启序列号，开启的收回序列号，未开启的跳过
                         * */
                        if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                            serialNumberMapperEx.cancelSerialNumber(depotItem.getMaterialid(), depotItem.getHeaderid(), depotItem.getBasicnumber().intValue(),
                                    new Date(), userInfo == null ? null : userInfo.getId());
                        }
                        /**收回序列号的时候释放库存*/
                        depotItem.setOpernumber(BigDecimal.ZERO);
                        depotItem.setBasicnumber(BigDecimal.ZERO);
                        this.updateDepotItemWithObj(depotItem);
                    }
                    depotItem.setId(tempUpdatedJson.getLong("Id"));
                    depotItem.setMaterialid(tempUpdatedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempUpdatedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempUpdatedJson.getBigDecimal("OperNumber"));
                        try {
                            String Unit = tempUpdatedJson.get("Unit").toString();
                            BigDecimal oNumber = tempUpdatedJson.getBigDecimal("OperNumber");
                            Long mId = Long.parseLong(tempUpdatedJson.get("MaterialId").toString());
                            //以下进行单位换算
//                            String UnitName = findUnitName(mId); //查询计量单位名称
                            String unitName = materialService.findUnitName(mId);
                            if (!StringUtil.isEmpty(unitName)) {
                                String unitList = unitName.substring(0, unitName.indexOf("("));
                                String ratioList = unitName.substring(unitName.indexOf("("));
                                String basicUnit = unitList.substring(0, unitList.indexOf(",")); //基本单位
                                String otherUnit = unitList.substring(unitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(ratioList.substring(ratioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber.multiply(new BigDecimal(ratio))); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempUpdatedJson.getBigDecimal("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempUpdatedJson.getBigDecimal("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempUpdatedJson.getBigDecimal("AllPrice"));
                    }
                    depotItem.setRemark(tempUpdatedJson.getString("Remark"));
                    if (tempUpdatedJson.get("DepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempUpdatedJson.getLong("DepotId"));
                    }
                    if (tempUpdatedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempUpdatedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempUpdatedJson.getBigDecimal("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempUpdatedJson.getBigDecimal("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempUpdatedJson.getBigDecimal("TaxLastMoney"));
                    }
                    depotItem.setOtherfield1(tempUpdatedJson.getString("OtherField1"));
                    depotItem.setOtherfield2(tempUpdatedJson.getString("OtherField2"));
                    depotItem.setOtherfield3(tempUpdatedJson.getString("OtherField3"));
                    depotItem.setOtherfield4(tempUpdatedJson.getString("OtherField4"));
                    depotItem.setOtherfield5(tempUpdatedJson.getString("OtherField5"));
                    depotItem.setMtype(tempUpdatedJson.getString("MType"));
                    /**出库时处理序列号*/
                    if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())){
                        if(getCurrentInStock(depotItem.getMaterialid())<depotItem.getBasicnumber().intValue()){
                            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_CODE,
                                    String.format(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_MSG,material==null?"":material.getName()));
                        }
                        if(!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubtype())) {
                                /**
                                 * 判断商品是否开启序列号，开启的收回序列号，未开启的跳过
                                 * */
                            if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                                //查询单据子表中开启序列号的数据列表
                                serialNumberService.checkAndUpdateSerialNumber(depotItem, userInfo);
                            }
                        }
                    }
                    this.updateDepotItemWithObj(depotItem);
                }
            }
        return null;
    }
    /**
     * 查询计量单位信息
     *
     * @return
     */
    public String findUnitName(Long mId) {
        String unitName = "";
        try {
            unitName = materialService.findUnitName(mId);
            if (unitName != null) {
                unitName = unitName.substring(1, unitName.length() - 1);
                if (unitName.equals("null")) {
                    unitName = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitName;
    }
    /**
     * 查询商品当前库存数量是否充足，
     *
     * */
    public int getCurrentInStock(Long materialId){
        //入库数量
        int inSum = findByTypeAndMaterialId(BusinessConstants.DEPOTHEAD_TYPE_STORAGE, materialId);
        //出库数量
        int outSum = findByTypeAndMaterialId(BusinessConstants.DEPOTHEAD_TYPE_OUT, materialId);
        return (inSum-outSum);
    }

}
