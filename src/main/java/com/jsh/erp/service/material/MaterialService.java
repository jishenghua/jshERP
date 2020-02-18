package com.jsh.erp.service.material;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.MaterialExtend.MaterialExtendService;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.materialCategory.MaterialCategoryService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ExcelUtils;
import com.jsh.erp.utils.StringUtil;
import jxl.Sheet;
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

@Service
public class MaterialService {
    private Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private MaterialMapperEx materialMapperEx;
    @Resource
    private MaterialExtendMapperEx materialExtendMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;
    @Resource
    private DepotItemService depotItemService;
    @Resource
    private MaterialCategoryService materialCategoryService;
    @Resource
    private UnitService unitService;
    @Resource
    private MaterialStockMapper materialStockMapper;
    @Resource
    private DepotService depotService;
    @Resource
    private MaterialExtendService materialExtendService;

    public Material getMaterial(long id)throws Exception {
        Material result=null;
        try{
            result=materialMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Material> getMaterial() throws Exception{
        MaterialExample example = new MaterialExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Material> list=null;
        try{
            list=materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialVo4Unit> select(String name, String standard, String model, String categoryIds,String mpList, int offset, int rows)
            throws Exception{
        String[] mpArr = mpList.split(",");
        List<MaterialVo4Unit> resList = new ArrayList<MaterialVo4Unit>();
        List<MaterialVo4Unit> list =null;
        try{
            list= materialMapperEx.selectByConditionMaterial(name, standard, model, categoryIds, mpList, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if (null != list) {
            List<Long> idList = new ArrayList<Long>();
            for (MaterialVo4Unit m : list) {
                idList.add(m.getId());
            }
            List<MaterialExtend> meList = materialExtendService.getListByMIds(idList);
            for (MaterialVo4Unit m : list) {
                //扩展信息
                String materialOther = "";
                for (int i = 0; i < mpArr.length; i++) {
                    if (mpArr[i].equals("制造商")) {
                        materialOther = materialOther + ((m.getMfrs() == null || m.getMfrs().equals("")) ? "" : "(" + m.getMfrs() + ")");
                    }
                    if (mpArr[i].equals("自定义1")) {
                        materialOther = materialOther + ((m.getOtherfield1() == null || m.getOtherfield1().equals("")) ? "" : "(" + m.getOtherfield1() + ")");
                    }
                    if (mpArr[i].equals("自定义2")) {
                        materialOther = materialOther + ((m.getOtherfield2() == null || m.getOtherfield2().equals("")) ? "" : "(" + m.getOtherfield2() + ")");
                    }
                    if (mpArr[i].equals("自定义3")) {
                        materialOther = materialOther + ((m.getOtherfield3() == null || m.getOtherfield3().equals("")) ? "" : "(" + m.getOtherfield3() + ")");
                    }
                }
                m.setMaterialOther(materialOther);
                Long tenantId = m.getTenantId();
                m.setStock(depotItemService.getStockByParam(null,m.getId(),null,null,tenantId));
                for(MaterialExtend me:meList) {
                    if(me.getMaterialId().longValue() == m.getId().longValue()) {
                        m.setPurchaseDecimal(me.getPurchaseDecimal()); //采购价
                        m.setCommodityDecimal(me.getCommodityDecimal()); //零售价
                        m.setWholesaleDecimal(me.getWholesaleDecimal()); //销售价
                        m.setLowDecimal(me.getLowDecimal()); //最低售价
                    }
                }
                resList.add(m);
            }
        }
        return resList;
    }

    public Long countMaterial(String name, String standard, String model, String categoryIds,String mpList)throws Exception {
        Long result =null;
        try{
            result= materialMapperEx.countsByMaterial(name, standard, model, categoryIds, mpList);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterial(String beanJson, HttpServletRequest request)throws Exception {
        Material m = JSONObject.parseObject(beanJson, Material.class);
        m.setEnabled(true);
        try{
            Long mId = null;
            materialMapper.insertSelective(m);
            List<Material> materials = getMaterialListByParam(m.getName(),m.getModel(),m.getColor(),
                    m.getStandard(), m.getMfrs(),m.getUnit(),m.getUnitid());
            if(materials!=null && materials.size()>0) {
                mId = materials.get(0).getId();
            }
            JSONObject mObj = JSON.parseObject(beanJson);
            materialExtendService.saveDetials(mObj.getString("inserted"), mObj.getString("deleted"), mObj.getString("updated"), mId);
            if(mObj.get("stock")!=null) {
                String stockStr = mObj.getString("stock");
                JSONArray stockArr = JSONArray.parseArray(stockStr);
                for(Object object: stockArr) {
                    JSONObject jsonObj = (JSONObject)object;
                    if(jsonObj.get("depotId")!=null && jsonObj.get("number")!=null) {
                        String number = jsonObj.getString("number");
                        Long depotId = jsonObj.getLong("depotId");
                        if(number!=null && Double.valueOf(number)>0) {
                            insertStockByMaterialAndDepot(depotId, mId, parseBigDecimalEx(number));
                        }
                    }
                }
            }
            logService.insertLog("商品", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterial(String beanJson, Long id, HttpServletRequest request) throws Exception{
        Material material = JSONObject.parseObject(beanJson, Material.class);
        material.setId(id);
        try{
            materialMapper.updateByPrimaryKeySelective(material);
            Long unitId = material.getUnitid();
            if(unitId != null) {
                materialMapperEx.updatePriceNullByPrimaryKey(id); //将价格置空
            } else {
                materialMapperEx.updateUnitIdNullByPrimaryKey(id); //将多单位置空
            }
            JSONObject mObj = JSON.parseObject(beanJson);
            materialExtendService.saveDetials(mObj.getString("inserted"),mObj.getString("deleted"),mObj.getString("updated"),id);
            if(mObj.get("stock")!=null) {
                String stockStr = mObj.getString("stock");
                JSONArray stockArr = JSONArray.parseArray(stockStr);
                for (Object object : stockArr) {
                    JSONObject jsonObj = (JSONObject) object;
                    if (jsonObj.get("depotId") != null && jsonObj.get("number") != null) {
                        String number = jsonObj.getString("number");
                        Long depotId = jsonObj.getLong("depotId");
                        //先清除再插入
                        MaterialStockExample example = new MaterialStockExample();
                        example.createCriteria().andMaterialIdEqualTo(id).andDepotIdEqualTo(depotId);
                        materialStockMapper.deleteByExample(example);
                        if (number != null && Double.valueOf(number) > 0) {
                            insertStockByMaterialAndDepot(depotId, id, parseBigDecimalEx(number));
                        }
                    }
                }
            }
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterial(Long id, HttpServletRequest request)throws Exception {
        int result =0;
        try{
            result= materialMapper.deleteByPrimaryKey(id);
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterial(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(idList);
        int result =0;
        try{
            result= materialMapper.deleteByExample(example);
            logService.insertLog("商品", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Material> list =null;
        try{
            list=  materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public int checkIsExist(Long id, String name, String model, String color, String standard, String mfrs,
                            String otherField1, String otherField2, String otherField3, String unit, Long unitId)throws Exception {
        MaterialExample example = new MaterialExample();
        MaterialExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name).andModelEqualTo(model).andColorEqualTo(color)
                .andStandardEqualTo(standard).andMfrsEqualTo(mfrs)
                .andOtherfield1EqualTo(otherField1).andOtherfield2EqualTo(otherField2).andOtherfield2EqualTo(otherField3);
        if (id > 0) {
            criteria.andIdNotEqualTo(id);
        }
        if (!StringUtil.isEmpty(unit)) {
            criteria.andUnitEqualTo(unit);
        }
        if (unitId !=null) {
            criteria.andUnitidEqualTo(unitId);
        }
        List<Material> list =null;
        try{
            list=  materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetEnable(Boolean enabled, String materialIDs)throws Exception {
        logService.insertLog("商品",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(materialIDs).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> ids = StringUtil.strToLongList(materialIDs);
        Material material = new Material();
        material.setEnabled(enabled);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(ids);
        int result =0;
        try{
            result=  materialMapper.updateByExampleSelective(material, example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public String findUnitName(Long mId)throws Exception{
        String result =null;
        try{
            result=  materialMapperEx.findUnitName(mId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<MaterialVo4Unit> findById(Long id)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findById(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialVo4Unit> findByIdWithBarCode(Long meId)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findByIdWithBarCode(meId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialVo4Unit> findBySelect()throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findBySelect();
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Material> findByOrder()throws Exception{
        MaterialExample example = new MaterialExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Name,Model asc");
        List<Material> list =null;
        try{
            list=  materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialVo4Unit> findBySelectWithBarCode(String q,Integer offset, Integer rows)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
            }
            list=  materialMapperEx.findBySelectWithBarCode(q, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findBySelectWithBarCodeCount(String q)throws Exception{
        int result=0;
        try{
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
            }
            result = materialMapperEx.findBySelectWithBarCodeCount(q);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    public List<MaterialVo4Unit> findByAll(String name, String model, String categoryIds)throws Exception {
        List<MaterialVo4Unit> resList = new ArrayList<MaterialVo4Unit>();
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findByAll(name, model, categoryIds);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if (null != list) {
            for (MaterialVo4Unit m : list) {
                resList.add(m);
            }
        }
        return resList;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BaseResponseInfo importExcel(Sheet src) throws Exception {
        List<Depot> depotList= depotService.getDepot();
        int depotCount = depotList.size();
        List<MaterialWithInitStock> mList = new ArrayList<MaterialWithInitStock>();
        for (int i = 2; i < src.getRows(); i++) {
            String name = ExcelUtils.getContent(src, i, 0); //名称
            String model = ExcelUtils.getContent(src, i, 1); //型号
            String categoryName = ExcelUtils.getContent(src, i, 2); //类型
            String safetyStock = ExcelUtils.getContent(src, i, 3); //安全存量
            String color = ExcelUtils.getContent(src, i, 4); //颜色
            String unit = ExcelUtils.getContent(src, i, 5); //单位
            //校验名称、型号、单位是否为空
            if(StringUtil.isNotEmpty(name) && StringUtil.isNotEmpty(model) && StringUtil.isNotEmpty(unit)) {
                MaterialWithInitStock m = new MaterialWithInitStock();
                m.setName(name);
                m.setModel(model);
                Long categoryId = materialCategoryService.getCategoryIdByName(categoryName);
                m.setCategoryid(categoryId);
                m.setSafetystock(parseBigDecimalEx(safetyStock));
                m.setColor(color);
                String manyUnit = ExcelUtils.getContent(src, i, 6); //多单位
                String ratio = ExcelUtils.getContent(src, i, 7); //比例
                String retailPrice = ExcelUtils.getContent(src, i, 8); //零售价
                String lowPrice = ExcelUtils.getContent(src, i, 9); //最低售价
                String presetpriceone = ExcelUtils.getContent(src, i, 10); //预计采购价
                String presetpricetwo = ExcelUtils.getContent(src, i, 11); //销售价
                if(StringUtil.isNotEmpty(manyUnit.trim())){ //多单位
                    String manyUnitAll = unit + "," + manyUnit + "(1:" + ratio + ")";
                    Long unitId = unitService.getUnitIdByName(manyUnitAll);
                    m.setUnitid(unitId);
                    m.setFirstoutunit(unit); //首选出库单位
                    m.setFirstinunit(manyUnit); //首选入库单位
                    JSONArray arr = new JSONArray();
                    JSONObject basicObj = new JSONObject();
                    basicObj.put("Unit", unit);
                    basicObj.put("RetailPrice", retailPrice);
                    basicObj.put("LowPrice", lowPrice);
                    basicObj.put("PresetPriceOne", presetpriceone);
                    basicObj.put("PresetPriceTwo", presetpricetwo);
                    JSONObject basicObjEx = new JSONObject();
                    basicObjEx.put("basic", basicObj);
                    JSONObject otherObj = new JSONObject();
                    otherObj.put("Unit", manyUnit);
                    otherObj.put("RetailPrice", parsePrice(retailPrice,ratio));
                    otherObj.put("LowPrice", parsePrice(lowPrice,ratio));
                    otherObj.put("PresetPriceOne", parsePrice(presetpriceone,ratio));
                    otherObj.put("PresetPriceTwo", parsePrice(presetpricetwo,ratio));
                    JSONObject otherObjEx = new JSONObject();
                    otherObjEx.put("other", otherObj);
                    arr.add(basicObjEx);
                    arr.add(otherObjEx);
                    m.setPricestrategy(arr.toJSONString());
                } else {
                    m.setUnit(unit);
                    m.setRetailprice(parseBigDecimalEx(retailPrice));
                    m.setLowprice(parseBigDecimalEx(lowPrice));
                    m.setPresetpriceone(parseBigDecimalEx(presetpriceone));
                    m.setPresetpricetwo(parseBigDecimalEx(presetpricetwo));
                }
                String enabled = ExcelUtils.getContent(src, i, 12); //状态
                m.setEnabled(enabled.equals("1")? true: false);
                //缓存各个仓库的库存信息
                Map<Long, BigDecimal> stockMap = new HashMap<Long, BigDecimal>();
                for(int j=1; j<=depotCount;j++) {
                    int col = 12+j;
                    if(col <= src.getColumns()){
                        String depotName = ExcelUtils.getContent(src, 1, col); //获取仓库名称
                        Long depotId = depotService.getIdByName(depotName);
                        if(depotId!=0L){
                            String stockStr = ExcelUtils.getContent(src, i, col);
                            if(StringUtil.isNotEmpty(stockStr)) {
                                stockMap.put(depotId, parseBigDecimalEx(stockStr));
                            }
                        }
                    }
                }
                m.setStockMap(stockMap);
                mList.add(m);
            }
        }
        logService.insertLog("商品",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_IMPORT).append(mList.size()).append(BusinessConstants.LOG_DATA_UNIT).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            Long mId = 0L;
            for(MaterialWithInitStock m: mList) {
                //判断该商品是否存在，如果不存在就新增，如果存在就更新
                List<Material> materials = getMaterialListByParam(m.getName(),m.getModel(),m.getColor(),m.getStandard(),
                        m.getMfrs(),m.getUnit(),m.getUnitid());
                if(materials.size()<=0) {
                    materialMapper.insertSelective(m);
                    List<Material> newList = getMaterialListByParam(m.getName(),m.getModel(),m.getColor(),m.getStandard(),
                            m.getMfrs(),m.getUnit(),m.getUnitid());
                    if(newList!=null && newList.size()>0) {
                        mId = newList.get(0).getId();
                    }
                } else {
                    mId = materials.get(0).getId();
                    String materialJson = JSON.toJSONString(m);
                    Material material = JSONObject.parseObject(materialJson, Material.class);
                    material.setId(mId);
                    materialMapper.updateByPrimaryKeySelective(material);
                }
                //给商品初始化库存
                Map<Long, BigDecimal> stockMap = m.getStockMap();
                Long depotId = null;
                for(Depot depot: depotList){
                    BigDecimal stock = stockMap.get(depot.getId());
                    //先清除再插入
                    MaterialStockExample example = new MaterialStockExample();
                    example.createCriteria().andMaterialIdEqualTo(mId).andDepotIdEqualTo(depot.getId());
                    materialStockMapper.deleteByExample(example);
                    if(stock!=null && stock.compareTo(BigDecimal.ZERO)!=0) {
                        depotId = depot.getId();
                        insertStockByMaterialAndDepot(depotId, mId, stock);
                    }
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

    /**
     * 根据条件返回产品列表
     * @param name
     * @param model
     * @param color
     * @param standard
     * @param mfrs
     * @param unit
     * @param unitId
     * @return
     */
    private List<Material> getMaterialListByParam(String name, String model, String color,
                                                  String standard, String mfrs, String unit, Long unitId) {
        MaterialExample example = new MaterialExample();
        MaterialExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name).andModelEqualTo(model);
        if (StringUtil.isNotEmpty(color)) {
            criteria.andColorEqualTo(color);
        }
        if (StringUtil.isNotEmpty(standard)) {
            criteria.andStandardEqualTo(standard);
        }
        if (StringUtil.isNotEmpty(mfrs)) {
            criteria.andMfrsEqualTo(mfrs);
        }
        if (StringUtil.isNotEmpty(unit)) {
            criteria.andUnitEqualTo(unit);
        }
        if (unitId !=null) {
            criteria.andUnitidEqualTo(unitId);
        }
        criteria.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Material> list = materialMapper.selectByExample(example);
        return list;
    }

    /**
     * 写入初始库存
     * @param depotId
     * @param mId
     * @param stock
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void insertStockByMaterialAndDepot(Long depotId, Long mId, BigDecimal stock){
        MaterialStock materialStock = new MaterialStock();
        materialStock.setDepotId(depotId);
        materialStock.setMaterialId(mId);
        materialStock.setNumber(stock);
        materialStockMapper.insertSelective(materialStock); //存入初始库存
    }

    public List<Material> getMaterialEnableSerialNumberList(Map<String, Object> parameterMap)throws Exception {
        List<Material> list =null;
        try{
            list=  materialMapperEx.getMaterialEnableSerialNumberList(parameterMap);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialByIds(String ids) throws Exception{
        logService.insertLog("商品",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        try{
            //逻辑删除商品
            materialMapperEx.batchDeleteMaterialByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
            //逻辑删除商品价格扩展
            materialExtendMapperEx.batchDeleteMaterialExtendByMIds(idArray);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 18:00
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialByIdsNormal(String ids) throws Exception{
        /**
         * 校验
         * 1、单据子表	jsh_depotitem
         * 是否有相关数据
         * */
        int deleteTotal=0;
        if(StringUtils.isEmpty(ids)){
            return deleteTotal;
        }
        String [] idArray=ids.split(",");

        /**
         * 校验单据子表	jsh_depotitem
         * */
        List<DepotItem> depotItemList =null;
        try{
            depotItemList=  depotItemMapperEx.getDepotItemListListByMaterialIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotItemList!=null&&depotItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,MaterialIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteMaterialByIds(ids);
        return deleteTotal;

    }

    public BigDecimal parseBigDecimalEx(String str) throws Exception{
        if(!StringUtil.isEmpty(str)) {
            return  new BigDecimal(str);
        } else {
            return null;
        }
    }

    public BigDecimal parsePrice(String price, String ratio) throws Exception{
        if(StringUtil.isEmpty(price) || StringUtil.isEmpty(ratio)) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal pr=new BigDecimal(price);
            BigDecimal r=new BigDecimal(ratio);
            return pr.multiply(r);
        }
    }

    /**
     * 根据产品和仓库获取初始库存
     * @param materialId
     * @param depotId
     * @return
     */
    public BigDecimal getInitStock(Long materialId, Long depotId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialStockExample example = new MaterialStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialStock> list = materialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            stock = list.get(0).getNumber();
        }
        return stock;
    }

    /**
     * 根据产品获取初始库存
     * @param materialId
     * @return
     */
    public BigDecimal getInitStockByMid(Long depotId, Long materialId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialStockExample example = new MaterialStockExample();
        if(depotId!=null) {
            example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                    .andDeleteFagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else {
            example.createCriteria().andMaterialIdEqualTo(materialId)
                    .andDeleteFagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        List<MaterialStock> list = materialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            for(MaterialStock ms: list) {
                if(ms!=null) {
                    stock = stock.add(ms.getNumber());
                }
            }
        }
        return stock;
    }

    public List<MaterialVo4Unit> getMaterialByMeId(Long meId) {
        List<MaterialVo4Unit> result = new ArrayList<MaterialVo4Unit>();
        try{
            if(meId!=null) {
                result= materialMapperEx.getMaterialByMeId(meId);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public String getMaxBarCode() {
        String maxBarCodeOld = materialMapperEx.getMaxBarCode();
        return Long.parseLong(maxBarCodeOld)+"";
    }
}
