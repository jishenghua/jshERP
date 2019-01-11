package com.jsh.erp.service.depotItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.DepotItemMapper;
import com.jsh.erp.service.material.MaterialService;
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
    private MaterialService materialService;

    public DepotItem getDepotItem(long id) {
        return depotItemMapper.selectByPrimaryKey(id);
    }

    public List<DepotItem> getDepotItem() {
        DepotItemExample example = new DepotItemExample();
        return depotItemMapper.selectByExample(example);
    }

    public List<DepotItem> select(String name, Integer type, String remark, int offset, int rows) {
        return depotItemMapper.selectByConditionDepotItem(name, type, remark, offset, rows);
    }

    public int countDepotItem(String name, Integer type, String remark) {
        return depotItemMapper.countsByDepotItem(name, type, remark);
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
        return depotItemMapper.getHeaderIdByMaterial(materialParam, depotIds);
    }

    public List<DepotItemVo4DetailByTypeAndMId> findDetailByTypeAndMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapper.findDetailByTypeAndMaterialIdList(mId, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findDetailByTypeAndMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        return depotItemMapper.findDetailByTypeAndMaterialIdCounts(mId);
    }

    public List<DepotItemVo4Material> findStockNumByMaterialIdList(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapper.findStockNumByMaterialIdList(mId, monthTime, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    public int findStockNumByMaterialIdCounts(Map<String, String> map) {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        String monthTime = map.get("monthTime");
        return depotItemMapper.findStockNumByMaterialIdCounts(mId, monthTime);
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
            return depotItemMapper.findByTypeAndMaterialIdIn(mId);
        } else {
            return depotItemMapper.findByTypeAndMaterialIdOut(mId);
        }
    }

    public List<DepotItemVo4WithInfoEx> getDetailList(Long headerId) {
        return depotItemMapper.getDetailList(headerId);
    }

    public List<DepotItemVo4WithInfoEx> findByAll(String headIds, String materialIds, Integer offset, Integer rows) {
        return depotItemMapper.findByAll(headIds, materialIds, offset, rows);
    }

    public int findByAllCount(String headIds, String materialIds) {
        return depotItemMapper.findByAllCount(headIds, materialIds);
    }

    public Double findByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapper.findByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapper.findByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public Double findPriceByType(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        if (TYPE.equals(type)) {
            if (isPrev) {
                return depotItemMapper.findPriceByTypeInIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findPriceByTypeInIsNotPrev(ProjectId, MId, MonthTime);
            }
        } else {
            if (isPrev) {
                return depotItemMapper.findPriceByTypeOutIsPrev(ProjectId, MId, MonthTime);
            } else {
                return depotItemMapper.findPriceByTypeOutIsNotPrev(ProjectId, MId, MonthTime);
            }
        }
    }

    public Double buyOrSale(String type, String subType, Long MId, String MonthTime, String sumType) {
        if (SUM_TYPE.equals(sumType)) {
            return depotItemMapper.buyOrSaleNumber(type, subType, MId, MonthTime, sumType);
        } else {
            return depotItemMapper.buyOrSalePrice(type, subType, MId, MonthTime, sumType);
        }
    }

    public Double findGiftByType(String subType, Integer ProjectId, Long MId, String type) {
        if (IN.equals(type)) {
            return depotItemMapper.findGiftByTypeIn(subType, ProjectId, MId);
        } else {
            return depotItemMapper.findGiftByTypeOut(subType, ProjectId, MId);
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String saveDetials(String inserted, String deleted, String updated, Long headerId) throws DataAccessException{
            //转为json
            JSONArray insertedJson = JSONArray.parseArray(inserted);
            JSONArray deletedJson = JSONArray.parseArray(deleted);
            JSONArray updatedJson = JSONArray.parseArray(updated);
            if (null != insertedJson) {
                for (int i = 0; i < insertedJson.size(); i++) {
                    DepotItem depotItem = new DepotItem();
                    JSONObject tempInsertedJson = JSONObject.parseObject(insertedJson.getString(i));
                    depotItem.setHeaderid(headerId);
                    depotItem.setMaterialid(tempInsertedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempInsertedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempInsertedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempInsertedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempInsertedJson.get("Unit").toString();
                            Double oNumber = tempInsertedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempInsertedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if (!StringUtil.isEmpty(UnitName)) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber * ratio); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempInsertedJson.getDouble("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempInsertedJson.getDouble("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempInsertedJson.getDouble("AllPrice"));
                    }
                    depotItem.setRemark(tempInsertedJson.getString("Remark"));
                    if (tempInsertedJson.get("DepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempInsertedJson.getLong("DepotId"));
                    }
                    if (tempInsertedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempInsertedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempInsertedJson.getDouble("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempInsertedJson.getDouble("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempInsertedJson.getDouble("TaxLastMoney"));
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
                    this.insertDepotItemWithObj(depotItem);
                }
            }
            if (null != deletedJson) {
                for (int i = 0; i < deletedJson.size(); i++) {
                    JSONObject tempDeletedJson = JSONObject.parseObject(deletedJson.getString(i));
                    this.deleteDepotItem(tempDeletedJson.getLong("Id"));
                }
            }
            if (null != updatedJson) {
                for (int i = 0; i < updatedJson.size(); i++) {
                    JSONObject tempUpdatedJson = JSONObject.parseObject(updatedJson.getString(i));
                    DepotItem depotItem = this.getDepotItem(tempUpdatedJson.getLong("Id"));
                    depotItem.setId(tempUpdatedJson.getLong("Id"));
                    depotItem.setMaterialid(tempUpdatedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempUpdatedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempUpdatedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempUpdatedJson.get("Unit").toString();
                            Double oNumber = tempUpdatedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempUpdatedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if (!StringUtil.isEmpty(UnitName)) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber * ratio); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempUpdatedJson.getDouble("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempUpdatedJson.getDouble("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempUpdatedJson.getDouble("AllPrice"));
                    }
                    depotItem.setRemark(tempUpdatedJson.getString("Remark"));
                    if (tempUpdatedJson.get("DepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempUpdatedJson.getLong("DepotId"));
                    }
                    if (tempUpdatedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempUpdatedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempUpdatedJson.getDouble("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempUpdatedJson.getDouble("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempUpdatedJson.getDouble("TaxLastMoney"));
                    }
                    depotItem.setOtherfield1(tempUpdatedJson.getString("OtherField1"));
                    depotItem.setOtherfield2(tempUpdatedJson.getString("OtherField2"));
                    depotItem.setOtherfield3(tempUpdatedJson.getString("OtherField3"));
                    depotItem.setOtherfield4(tempUpdatedJson.getString("OtherField4"));
                    depotItem.setOtherfield5(tempUpdatedJson.getString("OtherField5"));
                    depotItem.setMtype(tempUpdatedJson.getString("MType"));
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
}
