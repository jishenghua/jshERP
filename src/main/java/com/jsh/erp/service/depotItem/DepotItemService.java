package com.jsh.erp.service.depotItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.DepotItemStockWarningCount;
import com.jsh.erp.datasource.vo.DepotItemVo4Stock;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.materialExtend.MaterialExtendService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    SerialNumberMapperEx serialNumberMapperEx;
    @Resource
    private DepotHeadMapper depotHeadMapper;
    @Resource
    SerialNumberService serialNumberService;
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
    public int insertDepotItem(String beanJson, HttpServletRequest request)throws Exception {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        int result =0;
        try{
            result=depotItemMapper.insertSelective(depotItem);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepotItem(String beanJson, Long id, HttpServletRequest request)throws Exception {
        DepotItem depotItem = JSONObject.parseObject(beanJson, DepotItem.class);
        depotItem.setId(id);
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

    public List<DepotItemVo4DetailByTypeAndMId> findDetailByTypeAndMaterialIdList(Map<String, String> map)throws Exception {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        List<DepotItemVo4DetailByTypeAndMId> list =null;
        try{
            list = depotItemMapperEx.findDetailByTypeAndMaterialIdList(mId, QueryUtils.offset(map), QueryUtils.rows(map));
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long findDetailByTypeAndMaterialIdCounts(Map<String, String> map)throws Exception {
        String mIdStr = map.get("mId");
        Long mId = null;
        if(!StringUtil.isEmpty(mIdStr)) {
            mId = Long.parseLong(mIdStr);
        }
        Long result =null;
        try{
            result = depotItemMapperEx.findDetailByTypeAndMaterialIdCounts(mId);
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
            example.createCriteria().andHeaderIdEqualTo(headerId);
            list = depotItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
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

    public BigDecimal buyOrSale(String type, String subType, Long MId, String MonthTime, String sumType) throws Exception{
        BigDecimal result= BigDecimal.ZERO;
        try{
            if (SUM_TYPE.equals(sumType)) {
                result= depotItemMapperEx.buyOrSaleNumber(type, subType, MId, MonthTime, sumType);
            } else {
                result= depotItemMapperEx.buyOrSalePrice(type, subType, MId, MonthTime, sumType);
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
     * @param MonthTime
     * @return
     * @throws Exception
     */
    public BigDecimal inOrOutPrice(String type, String subType, String MonthTime) throws Exception{
        BigDecimal result= BigDecimal.ZERO;
        try{
            result = depotItemMapperEx.inOrOutPrice(type, subType, MonthTime);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void saveDetials(String rows, Long headerId, Long tenantId, HttpServletRequest request) throws Exception{
        //查询单据主表信息
        DepotHead depotHead =depotHeadMapper.selectByPrimaryKey(headerId);
        //获得当前操作人
        User userInfo=userService.getCurrentUser();
        //首先回收序列号，如果是调拨，不用处理序列号
        if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())
            &&!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())){
            List<DepotItem> depotItemList = getListByHeaderId(headerId);
            for(DepotItem depotItem : depotItemList){
                Material material= materialService.getMaterial(depotItem.getMaterialId());
                if(material==null){
                    continue;
                }
                if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())){
                    serialNumberService.cancelSerialNumber(depotItem.getMaterialId(),depotItem.getHeaderId(),
                            (depotItem.getBasicNumber()==null?0:depotItem.getBasicNumber()).intValue(), userInfo);
                }
            }
        }
        //删除单据的明细
        deleteDepotItemHeadId(headerId);
        JSONArray rowArr = JSONArray.parseArray(rows);
        if (null != rowArr) {
            for (int i = 0; i < rowArr.size(); i++) {
                DepotItem depotItem = new DepotItem();
                JSONObject rowObj = JSONObject.parseObject(rowArr.getString(i));
                depotItem.setHeaderId(headerId);
                String barCode = rowObj.getString("barCode");
                MaterialExtend materialExtend = materialExtendService.getInfoByBarCode(barCode);
                depotItem.setMaterialId(materialExtend.getMaterialId());
                depotItem.setMaterialExtendId(materialExtend.getId());
                depotItem.setMaterialUnit(rowObj.getString("Unit"));
                if (StringUtil.isExist(rowObj.get("OperNumber"))) {
                    depotItem.setOperNumber(rowObj.getBigDecimal("OperNumber"));
                    String unit = rowObj.get("Unit").toString();
                    BigDecimal oNumber = rowObj.getBigDecimal("OperNumber");
                    //以下进行单位换算
                    Unit unitInfo = materialService.findUnit(materialExtend.getMaterialId()); //查询计量单位信息
                    if (StringUtil.isNotEmpty(unitInfo.getName())) {
                        String basicUnit = unitInfo.getBasicUnit(); //基本单位
                        String otherUnit = unitInfo.getOtherUnit(); //副单位
                        Integer ratio = unitInfo.getRatio(); //比例
                        if (unit.equals(basicUnit)) { //如果等于基础单位
                            depotItem.setBasicNumber(oNumber); //数量一致
                        } else if (unit.equals(otherUnit)) { //如果等于副单位
                            depotItem.setBasicNumber(oNumber.multiply(new BigDecimal(ratio)) ); //数量乘以比例
                        }
                    } else {
                        depotItem.setBasicNumber(oNumber); //其他情况
                    }
                }
                if (StringUtil.isExist(rowObj.get("UnitPrice"))) {
                    depotItem.setUnitPrice(rowObj.getBigDecimal("UnitPrice"));
                }
                if (StringUtil.isExist(rowObj.get("TaxUnitPrice"))) {
                    depotItem.setTaxUnitPrice(rowObj.getBigDecimal("TaxUnitPrice"));
                }
                if (StringUtil.isExist(rowObj.get("AllPrice"))) {
                    depotItem.setAllPrice(rowObj.getBigDecimal("AllPrice"));
                }
                if (StringUtil.isExist(rowObj.get("DepotId"))) {
                    depotItem.setDepotId(rowObj.getLong("DepotId"));
                } else {
                    throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_DEPOT_FAILED_CODE,
                            String.format(ExceptionConstants.DEPOT_HEAD_DEPOT_FAILED_MSG));
                }
                if(BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                    if (StringUtil.isExist(rowObj.get("AnotherDepotId"))) {
                        depotItem.setAnotherDepotId(rowObj.getLong("AnotherDepotId"));
                    } else {
                        throw new BusinessRunTimeException(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_FAILED_CODE,
                                String.format(ExceptionConstants.DEPOT_HEAD_ANOTHER_DEPOT_FAILED_MSG));
                    }
                }
                if (StringUtil.isExist(rowObj.get("TaxRate"))) {
                    depotItem.setTaxRate(rowObj.getBigDecimal("TaxRate"));
                }
                if (StringUtil.isExist(rowObj.get("TaxMoney"))) {
                    depotItem.setTaxMoney(rowObj.getBigDecimal("TaxMoney"));
                }
                if (StringUtil.isExist(rowObj.get("TaxLastMoney"))) {
                    depotItem.setTaxLastMoney(rowObj.getBigDecimal("TaxLastMoney"));
                }
                if (StringUtil.isExist(rowObj.get("MType"))) {
                    depotItem.setMaterialType(rowObj.getString("MType"));
                }
                if (StringUtil.isExist(rowObj.get("Remark"))) {
                    depotItem.setRemark(rowObj.getString("Remark"));
                }
                //出库时判断库存是否充足
                if(BusinessConstants.DEPOTHEAD_TYPE_OUT.equals(depotHead.getType())){
                    if(depotItem==null){
                        continue;
                    }
                    Material material= materialService.getMaterial(depotItem.getMaterialId());
                    if(material==null){
                        continue;
                    }
                    BigDecimal stock = getStockByParam(depotItem.getDepotId(),depotItem.getMaterialId(),null,null,tenantId);
                    BigDecimal thisBasicNumber = depotItem.getBasicNumber()==null?BigDecimal.ZERO:depotItem.getBasicNumber();
                    if(systemConfigService.getMinusStockFlag() == false && stock.compareTo(thisBasicNumber)<0){
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_CODE,
                                String.format(ExceptionConstants.MATERIAL_STOCK_NOT_ENOUGH_MSG,material==null?"":material.getName()));
                    }
                    //出库时处理序列号
                    if(!BusinessConstants.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                        //判断商品是否开启序列号，开启的收回序列号，未开启的跳过
                        if(BusinessConstants.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())) {
                            //查询单据子表中开启序列号的数据列表
                            serialNumberService.checkAndUpdateSerialNumber(depotItem, userInfo);
                        }
                    }
                }
                this.insertDepotItemWithObj(depotItem);
                //更新当前库存
                updateCurrentStock(depotItem,tenantId);
            }
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void deleteDepotItemHeadId(Long headerId)throws Exception {
        DepotItemExample example = new DepotItemExample();
        example.createCriteria().andHeaderIdEqualTo(headerId);
        try{
            depotItemMapper.deleteByExample(example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepotItemByIds(String ids)throws Exception {
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int  result =0;
        try{
            result =depotItemMapperEx.batchDeleteDepotItemByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public List<DepotItemStockWarningCount> findStockWarningCount(int offset, Integer rows, String materialParam, Integer pid) {
        List<DepotItemStockWarningCount> list = null;
        try{
            list =depotItemMapperEx.findStockWarningCount( offset, rows, materialParam, pid);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int findStockWarningCountTotal(String materialParam, Integer pid) {
        int result = 0;
        try{
            result =depotItemMapperEx.findStockWarningCountTotal(materialParam, pid);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    /**
     * 库存统计
     * @param depotId
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getStockByParam(Long depotId, Long mId, String beginTime, String endTime, Long tenantId){
        //初始库存
        BigDecimal initStock = materialService.getInitStockByMid(depotId, mId);
        //盘点复盘后数量的变动
        BigDecimal stockCheckSum = depotItemMapperEx.getStockCheckSum(depotId, mId, beginTime, endTime);
        DepotItemVo4Stock stockObj = depotItemMapperEx.getStockByParam(depotId, mId, beginTime, endTime, tenantId);
        BigDecimal intNum = stockObj.getInNum();
        BigDecimal outNum = stockObj.getOutNum();
        return initStock.add(intNum).subtract(outNum).add(stockCheckSum);
    }

    /**
     * 入库统计
     * @param depotId
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getInNumByParam(Long depotId, Long mId, String beginTime, String endTime, Long tenantId){
        DepotItemVo4Stock stockObj = depotItemMapperEx.getStockByParam(depotId, mId, beginTime, endTime, tenantId);
        return stockObj.getInNum();
    }

    /**
     * 出库统计
     * @param depotId
     * @param mId
     * @param beginTime
     * @param endTime
     * @return
     */
    public BigDecimal getOutNumByParam(Long depotId, Long mId, String beginTime, String endTime, Long tenantId){
        DepotItemVo4Stock stockObj = depotItemMapperEx.getStockByParam(depotId, mId, beginTime, endTime, tenantId);
        return stockObj.getOutNum();
    }

    /**
     * 根据单据明细来批量更新当前库存
     * @param depotItem
     * @param tenantId
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateCurrentStock(DepotItem depotItem, Long tenantId){
        updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getDepotId(),tenantId);
        if(depotItem.getAnotherDepotId()!=null){
            updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getAnotherDepotId(),tenantId);
        }
    }

    /**
     * 根据商品和仓库来更新当前库存
     * @param mId
     * @param dId
     * @param tenantId
     */
    public void updateCurrentStockFun(Long mId, Long dId, Long tenantId) {
        MaterialCurrentStockExample example = new MaterialCurrentStockExample();
        example.createCriteria().andMaterialIdEqualTo(mId).andDepotIdEqualTo(dId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialCurrentStock> list = materialCurrentStockMapper.selectByExample(example);
        MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
        materialCurrentStock.setMaterialId(mId);
        materialCurrentStock.setDepotId(dId);
        materialCurrentStock.setCurrentNumber(getStockByParam(dId,mId,null,null,tenantId));
        if(list!=null && list.size()>0) {
            Long mcsId = list.get(0).getId();
            materialCurrentStock.setId(mcsId);
            materialCurrentStockMapper.updateByPrimaryKeySelective(materialCurrentStock);
        } else {
            materialCurrentStockMapper.insertSelective(materialCurrentStock);
        }
    }
}
