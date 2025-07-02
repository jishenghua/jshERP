package com.jsh.erp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.MaterialVoSearch;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MaterialService {
    private Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private MaterialExtendMapper materialExtendMapper;
    @Resource
    private MaterialMapperEx materialMapperEx;
    @Resource
    private MaterialCategoryMapperEx materialCategoryMapperEx;
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
    private MaterialInitialStockMapper materialInitialStockMapper;
    @Resource
    private MaterialInitialStockMapperEx materialInitialStockMapperEx;
    @Resource
    private MaterialCurrentStockMapper materialCurrentStockMapper;
    @Resource
    private MaterialCurrentStockMapperEx materialCurrentStockMapperEx;
    @Resource
    private DepotService depotService;
    @Resource
    private MaterialExtendService materialExtendService;
    @Resource
    private SystemConfigService systemConfigService;

    @Value(value="${file.uploadType}")
    private Long fileUploadType;

    private static final Integer EXPORT_LIMIT = 10000;

    public Material getMaterial(long id)throws Exception {
        Material result=null;
        try{
            result=materialMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Material> getMaterialListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Material> list = new ArrayList<>();
        try{
            MaterialExample example = new MaterialExample();
            example.createCriteria().andIdIn(idList);
            list = materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
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

    public List<MaterialVo4Unit> select(String materialParam, String standard, String model, String color, String brand, String mfrs,
                                        String otherField1, String otherField2, String otherField3, String weight, String expiryNum, String enableSerialNumber,
                                        String enableBatchNumber, String position, String enabled, String remark, String categoryId,
                                        String mpList)
            throws Exception{
        String[] mpArr = new String[]{};
        if(StringUtil.isNotEmpty(mpList)){
            mpArr= mpList.split(",");
        }
        List<MaterialVo4Unit> list = new ArrayList<>();
        try{
            List<Long> idList = new ArrayList<>();
            if(StringUtil.isNotEmpty(categoryId)){
                idList = getListByParentId(Long.parseLong(categoryId));
            }
            PageUtils.startPage();
            list= materialMapperEx.selectByConditionMaterial(materialParam, standard, model, color, brand, mfrs,
                    otherField1, otherField2, otherField3, weight, expiryNum,
                    enableSerialNumber, enableBatchNumber, position, enabled, remark, idList, mpList);
            if (null != list && list.size()>0) {
                Map<Long,BigDecimal> initialStockMap = getInitialStockMapByMaterialList(list);
                Map<Long,BigDecimal> currentStockMap = getCurrentStockMapByMaterialList(list);
                for (MaterialVo4Unit m : list) {
                    if(fileUploadType == 2) {
                        m.setImgSmall("small");
                        m.setImgLarge("large");
                    }
                    m.setMaterialOther(getMaterialOtherByParam(mpArr, m));
                    m.setInitialStock(initialStockMap.get(m.getId())!=null? initialStockMap.get(m.getId()): BigDecimal.ZERO);
                    m.setBigUnitInitialStock(getBigUnitStock(m.getInitialStock(), m.getUnitId()));
                    m.setStock(currentStockMap.get(m.getId())!=null? currentStockMap.get(m.getId()): BigDecimal.ZERO);
                    m.setBigUnitStock(getBigUnitStock(m.getStock(), m.getUnitId()));
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterial(JSONObject obj, HttpServletRequest request)throws Exception {
        Material m = JSONObject.parseObject(obj.toJSONString(), Material.class);
        m.setEnabled(true);
        //构造多属性数组字符串
        m.setAttribute(parseAttributeBySku(obj));
        try{
            materialMapperEx.insertSelectiveEx(m);
            Long mId = m.getId();
            materialExtendService.saveDetials(obj, obj.getString("sortList"), mId, "insert");
            if(obj.get("stock")!=null) {
                JSONArray stockArr = obj.getJSONArray("stock");
                for (int i = 0; i < stockArr.size(); i++) {
                    JSONObject jsonObj = stockArr.getJSONObject(i);
                    if(jsonObj.get("id")!=null && jsonObj.get("initStock")!=null) {
                        String number = jsonObj.getString("initStock");
                        BigDecimal lowSafeStock = null;
                        BigDecimal highSafeStock = null;
                        if(jsonObj.get("lowSafeStock")!=null) {
                            lowSafeStock = jsonObj.getBigDecimal("lowSafeStock");
                        }
                        if(jsonObj.get("highSafeStock")!=null) {
                            highSafeStock = jsonObj.getBigDecimal("highSafeStock");
                        }
                        Long depotId = jsonObj.getLong("id");
                        if(StringUtil.isNotEmpty(number) && Double.parseDouble(number)>0 || lowSafeStock!=null || highSafeStock!=null) {
                            insertInitialStockByMaterialAndDepot(depotId, mId, parseBigDecimalEx(number), lowSafeStock, highSafeStock);
                            insertCurrentStockByMaterialAndDepot(depotId, mId, parseBigDecimalEx(number));
                        }
                    }
                }
            }
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(m.getName()).toString(), request);
            return 1;
        }
        catch (BusinessRunTimeException ex) {
            throw new BusinessRunTimeException(ex.getCode(), ex.getMessage());
        }
        catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterial(JSONObject obj, HttpServletRequest request) throws Exception{
        Material material = JSONObject.parseObject(obj.toJSONString(), Material.class);
        //构造多属性数组字符串
        material.setAttribute(parseAttributeBySku(obj));
        try{
            materialMapper.updateByPrimaryKeySelective(material);
            if(material.getUnitId() == null) {
                materialMapperEx.setUnitIdToNull(material.getId());
            }
            if(material.getExpiryNum() == null) {
                materialMapperEx.setExpiryNumToNull(material.getId());
            }
            materialExtendService.saveDetials(obj, obj.getString("sortList"),material.getId(), "update");
            BigDecimal currentUnitPrice = materialCurrentStockMapperEx.getCurrentUnitPriceByMId(material.getId());
            if(obj.get("stock")!=null) {
                JSONArray stockArr = obj.getJSONArray("stock");
                for (int i = 0; i < stockArr.size(); i++) {
                    JSONObject jsonObj = stockArr.getJSONObject(i);
                    if (jsonObj.get("id") != null && jsonObj.get("initStock") != null) {
                        String number = jsonObj.getString("initStock");
                        BigDecimal lowSafeStock = null;
                        BigDecimal highSafeStock = null;
                        if(jsonObj.get("lowSafeStock")!=null) {
                            lowSafeStock = jsonObj.getBigDecimal("lowSafeStock");
                        }
                        if(jsonObj.get("highSafeStock")!=null) {
                            highSafeStock = jsonObj.getBigDecimal("highSafeStock");
                        }
                        Long depotId = jsonObj.getLong("id");
                        //初始库存-先清除再插入
                        MaterialInitialStockExample example = new MaterialInitialStockExample();
                        example.createCriteria().andMaterialIdEqualTo(material.getId()).andDepotIdEqualTo(depotId);
                        materialInitialStockMapper.deleteByExample(example);
                        if (StringUtil.isNotEmpty(number) || lowSafeStock!=null || highSafeStock!=null) {
                            insertInitialStockByMaterialAndDepot(depotId, material.getId(), parseBigDecimalEx(number), lowSafeStock, highSafeStock);
                        }
                        //更新当前库存
                        depotItemService.updateCurrentStockFun(material.getId(), depotId, currentUnitPrice);
                    }
                }
            }
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(material.getName()).toString(), request);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterial(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterial(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialByIds(String ids) throws Exception{
        String [] idArray=ids.split(",");
        //校验单据子表	jsh_depot_item
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
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        //路径列表
        List<String> pathList = new ArrayList<>();
        List<Material> list = getMaterialListByIds(ids);
        for(Material material: list){
            sb.append("[").append(material.getName()).append("]");
            if(StringUtil.isNotEmpty(material.getImgName())) {
                pathList.add(material.getImgName());
            }
        }
        logService.insertLog("商品", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            //逻辑删除商品
            materialMapperEx.batchDeleteMaterialByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
            //逻辑删除商品价格扩展
            materialExtendMapperEx.batchDeleteMaterialExtendByMIds(idArray);
            //逻辑删除文件
            systemConfigService.deleteFileByPathList(pathList);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
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
        return materialMapperEx.checkIsExist(id, name, model, color, standard, mfrs, otherField1,
                otherField2, otherField3, unit, unitId);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("商品",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> materialIds = StringUtil.strToLongList(ids);
        Material material = new Material();
        material.setEnabled(status);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(materialIds);
        int result =0;
        try{
            result=  materialMapper.updateByExampleSelective(material, example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public Unit findUnit(Long mId)throws Exception{
        Unit unit = new Unit();
        try{
            List<Unit> list = materialMapperEx.findUnitList(mId);
            if(list!=null && list.size()>0) {
                unit = list.get(0);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return unit;
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

    public List<Long> getListByParentId(Long parentId) {
        List<Long> idList = new ArrayList<Long>();
        List<MaterialCategory> list = materialCategoryMapperEx.getListByParentId(parentId);
        idList.add(parentId);
        if(list!=null && list.size()>0) {
            getIdListByParentId(idList, parentId);
        }
        return idList;
    }

    public List<Long> getIdListByParentId(List<Long> idList, Long parentId){
        List<MaterialCategory> list = materialCategoryMapperEx.getListByParentId(parentId);
        if(list!=null && list.size()>0) {
            for(MaterialCategory mc : list){
                idList.add(mc.getId());
                getIdListByParentId(idList, mc.getId());
            }
        }
        return idList;
    }

    public JSONArray getMaterialByParam(String materialParam) {
        JSONArray arr = new JSONArray();
        List<MaterialVoSearch> list = materialMapperEx.getMaterialByParam(materialParam);
        for(MaterialVoSearch item: list) {
            JSONObject obj = new JSONObject();
            StringBuilder sb = new StringBuilder();
            sb.append(item.getBarCode());
            sb.append("_").append(item.getName());
            if(StringUtil.isNotEmpty(item.getMnemonic())) {
                sb.append("(").append(item.getMnemonic()).append(")");
            }
            if(StringUtil.isNotEmpty(item.getStandard())) {
                sb.append("(").append(item.getStandard()).append(")");
            }
            if(StringUtil.isNotEmpty(item.getModel())) {
                sb.append("(").append(item.getModel()).append(")");
            }
            if(StringUtil.isNotEmpty(item.getColor())) {
                sb.append("(").append(item.getColor()).append(")");
            }
            if(StringUtil.isNotEmpty(item.getUnit())) {
                sb.append("(").append(item.getUnit()).append(")");
            }
            obj.put("barCode", item.getBarCode());
            obj.put("materialStr", sb.toString());
            arr.add(obj);
        }
        return arr;
    }

    public List<MaterialVo4Unit> findBySelectWithBarCode(Long categoryId, String q, String standardOrModel, String color,
                                                         String brand, String mfrs, String otherField1, String otherField2, String otherField3,
                                                         String enableSerialNumber, String enableBatchNumber, Integer offset, Integer rows) throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            List<Long> idList = new ArrayList<>();
            if(categoryId!=null){
                Long parentId = categoryId;
                idList = getListByParentId(parentId);
            }
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
                q = q.trim();
            }
            list=  materialMapperEx.findBySelectWithBarCode(idList, q, standardOrModel, color, brand, mfrs,
                    otherField1, otherField2, otherField3, enableSerialNumber, enableBatchNumber, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findBySelectWithBarCodeCount(Long categoryId, String q, String standardOrModel, String color,
                                            String brand, String mfrs, String otherField1, String otherField2, String otherField3,
                                            String enableSerialNumber, String enableBatchNumber) throws Exception{
        int result=0;
        try{
            List<Long> idList = new ArrayList<>();
            if(categoryId!=null){
                Long parentId = categoryId;
                idList = getListByParentId(parentId);
            }
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
            }
            result = materialMapperEx.findBySelectWithBarCodeCount(idList, q, standardOrModel, color, brand, mfrs,
                    otherField1, otherField2, otherField3, enableSerialNumber, enableBatchNumber);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    public void exportExcel(String categoryId, String materialParam, String color, String materialOther, String weight,
                                             String expiryNum, String enabled, String enableSerialNumber, String enableBatchNumber,
                                             String remark, String mpList, HttpServletResponse response)throws Exception {
        List<Long> idList = new ArrayList<>();
        if(StringUtil.isNotEmpty(categoryId)){
            idList = getListByParentId(Long.parseLong(categoryId));
        }
        //查询商品主条码相关列表
        List<MaterialVo4Unit> dataList = materialMapperEx.exportExcel(materialParam, color, materialOther, weight, expiryNum, enabled, enableSerialNumber,
                enableBatchNumber, remark, idList);
        if (null != dataList && dataList.size() > EXPORT_LIMIT) {
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXPORT_LIMIT_CODE,
                    ExceptionConstants.MATERIAL_EXPORT_LIMIT_MSG);
        }
        //查询商品副条码相关列表
        Map<Long, MaterialExtend> otherMaterialMap = new HashMap<>();
        List<MaterialExtend> otherDataList = materialMapperEx.getOtherMaterialList();
        for(MaterialExtend me: otherDataList) {
            //遇到多个副条码的情况，只加第一个
            otherMaterialMap.putIfAbsent(me.getMaterialId(), me);
        }
        String otherField = "扩展1,扩展2,扩展3";
        if(StringUtil.isNotEmpty(mpList)) {
            otherField = mpList;
        }
        String nameStr = "名称*,规格,型号,颜色,品牌,类别,基础重量(kg),保质期(天),基本单位*,副单位,基本条码*,副条码,比例,多属性," +
                "采购价,零售价,销售价,最低售价,状态*,序列号,批号,仓位货架,制造商," + otherField + ",备注";
        List<String> nameList = StringUtil.strToStringList(nameStr);
        //仓库列表
        List<Depot> depotList = depotService.getAllList();
        if (nameList != null) {
            for(Depot depot: depotList) {
                nameList.add(depot.getName());
            }
        }
        //期初库存缓存
        List<MaterialInitialStock> misList = materialInitialStockMapperEx.getListExceptZero();
        Map<String, BigDecimal> misMap = new HashMap<>();
        if (misList != null) {
            for (MaterialInitialStock mis : misList) {
                misMap.put(mis.getMaterialId() + "_" + mis.getDepotId(), mis.getNumber());
            }
        }
        String[] names = StringUtil.listToStringArray(nameList);
        String title = "商品信息";
        List<Object[]> objects = new ArrayList<>();
        if (null != dataList) {
            for (MaterialVo4Unit m : dataList) {
                Object[] objs = new Object[names.length];
                objs[0] = m.getName();
                objs[1] = m.getStandard();
                objs[2] = m.getModel();
                objs[3] = m.getColor();
                objs[4] = m.getBrand();
                objs[5] = m.getCategoryName();
                objs[6] = m.getWeight() == null ? "" : m.getWeight().setScale(3, BigDecimal.ROUND_HALF_UP);
                objs[7] = m.getExpiryNum() == null ? "" : m.getExpiryNum();
                objs[8] = m.getCommodityUnit();
                objs[9] = otherMaterialMap.get(m.getId()) == null ? "" : otherMaterialMap.get(m.getId()).getCommodityUnit();
                objs[10] = m.getmBarCode();
                objs[11] = otherMaterialMap.get(m.getId()) == null ? "" : otherMaterialMap.get(m.getId()).getBarCode();
                objs[12] = m.getRatio() == null ? "" : m.getRatio();
                objs[13] = m.getSku();
                objs[14] = m.getPurchaseDecimal() == null ? "" : m.getPurchaseDecimal().setScale(3, BigDecimal.ROUND_HALF_UP);
                objs[15] = m.getCommodityDecimal() == null ? "" : m.getCommodityDecimal().setScale(3, BigDecimal.ROUND_HALF_UP);
                objs[16] = m.getWholesaleDecimal() == null ? "" : m.getWholesaleDecimal().setScale(3, BigDecimal.ROUND_HALF_UP);
                objs[17] = m.getLowDecimal() == null ? "" : m.getLowDecimal().setScale(3, BigDecimal.ROUND_HALF_UP);
                objs[18] = m.getEnabled() ? "1" : "0";
                objs[19] = m.getEnableSerialNumber();
                objs[20] = m.getEnableBatchNumber();
                objs[21] = m.getPosition();
                objs[22] = m.getMfrs();
                objs[23] = m.getOtherField1();
                objs[24] = m.getOtherField2();
                objs[25] = m.getOtherField3();
                objs[26] = m.getRemark();
                //仓库期初库存
                int i = 27;
                for(Depot depot: depotList) {
                    BigDecimal number = misMap.get(m.getId() + "_" + depot.getId());
                    objs[i] = number == null ? BigDecimal.ZERO : number.setScale(2, BigDecimal.ROUND_HALF_UP);
                    i++;
                }
                objects.add(objs);
            }
        }
        File file = ExcelUtils.exportObjectsOneSheet(title, "*导入时本行内容请勿删除，切记！", names, title, objects);
        ExcelUtils.downloadExcel(file, file.getName(), response);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public BaseResponseInfo importExcel(MultipartFile file, HttpServletRequest request) throws Exception {
        BaseResponseInfo info = new BaseResponseInfo();
        try {
            Long beginTime = System.currentTimeMillis();
            //文件扩展名只能为xls
            String fileName = file.getOriginalFilename();
            if(StringUtil.isNotEmpty(fileName)) {
                String fileExt = fileName.substring(fileName.indexOf(".")+1);
                if(!"xls".equals(fileExt)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXTENSION_ERROR_CODE,
                            ExceptionConstants.MATERIAL_EXTENSION_ERROR_MSG);
                }
            }
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            Sheet src = workbook.getSheet(0);
            //获取真实的行数，剔除掉空白行
            int rightRows = ExcelUtils.getRightRows(src);
            List<Depot> depotList= depotService.getDepot();
            int depotCount = depotList.size();
            Map<String, Long> depotMap = parseDepotToMap(depotList);
            User user = userService.getCurrentUser();
            List<MaterialWithInitStock> mList = new ArrayList<>();
            //单次导入超出1000条
            if(rightRows > 1002) {
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_IMPORT_OVER_LIMIT_CODE,
                        String.format(ExceptionConstants.MATERIAL_IMPORT_OVER_LIMIT_MSG));
            }
            for (int i = 2; i < rightRows; i++) {
                String name = ExcelUtils.getContent(src, i, 0); //名称
                String standard = ExcelUtils.getContent(src, i, 1); //规格
                String model = ExcelUtils.getContent(src, i, 2); //型号
                String color = ExcelUtils.getContent(src, i, 3); //颜色
                String brand = ExcelUtils.getContent(src, i, 4); //品牌
                String categoryName = ExcelUtils.getContent(src, i, 5); //类别
                String weight = ExcelUtils.getContent(src, i, 6); //基础重量(kg)
                String expiryNum = ExcelUtils.getContent(src, i, 7); //保质期(天)
                String unit = ExcelUtils.getContent(src, i, 8); //基本单位
                //名称为空
                if(StringUtil.isEmpty(name)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NAME_EMPTY_CODE,
                            String.format(ExceptionConstants.MATERIAL_NAME_EMPTY_MSG, i+1));
                }
                //名称长度超出
                if(name.length()>100) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_NAME_OVER_CODE,
                            String.format(ExceptionConstants.MATERIAL_NAME_OVER_MSG, i+1));
                }
                //规格长度超出
                if(StringUtil.isNotEmpty(standard) && standard.length()>100) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_STANDARD_OVER_CODE,
                            String.format(ExceptionConstants.MATERIAL_STANDARD_OVER_MSG, i+1));
                }
                //型号长度超出
                if(StringUtil.isNotEmpty(model) && model.length()>100) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_MODEL_OVER_CODE,
                            String.format(ExceptionConstants.MATERIAL_MODEL_OVER_MSG, i+1));
                }
                //基本单位为空
                if(StringUtil.isEmpty(unit)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_UNIT_EMPTY_CODE,
                            String.format(ExceptionConstants.MATERIAL_UNIT_EMPTY_MSG, i+1));
                }
                MaterialWithInitStock m = new MaterialWithInitStock();
                m.setName(name);
                m.setStandard(standard);
                m.setModel(model);
                m.setColor(color);
                m.setBrand(brand);
                //通过名称生成助记码
                m.setMnemonic(PinYinUtil.getFirstLettersLo(name));
                Long categoryId = materialCategoryService.getCategoryIdByName(categoryName);
                if(null!=categoryId){
                    m.setCategoryId(categoryId);
                }
                if(StringUtil.isNotEmpty(weight)) {
                    //校验基础重量是否是数字（含小数）
                    if(!StringUtil.isPositiveBigDecimal(weight)) {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_WEIGHT_NOT_DECIMAL_CODE,
                                String.format(ExceptionConstants.MATERIAL_WEIGHT_NOT_DECIMAL_MSG, i+1));
                    }
                    m.setWeight(new BigDecimal(weight));
                }
                if(StringUtil.isNotEmpty(expiryNum)) {
                    //校验保质期是否是正整数
                    if(!StringUtil.isPositiveLong(expiryNum)) {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXPIRY_NUM_NOT_INTEGER_CODE,
                                String.format(ExceptionConstants.MATERIAL_EXPIRY_NUM_NOT_INTEGER_MSG, i+1));
                    }
                    m.setExpiryNum(Integer.parseInt(expiryNum));
                }
                String manyUnit = ExcelUtils.getContent(src, i, 9); //副单位
                String barCode = ExcelUtils.getContent(src, i, 10); //基础条码
                String manyBarCode = ExcelUtils.getContent(src, i, 11); //副条码
                String ratio = ExcelUtils.getContent(src, i, 12); //比例
                String sku = ExcelUtils.getContent(src, i, 13); //多属性
                String purchaseDecimal = ExcelUtils.getContent(src, i, 14); //采购价
                String commodityDecimal = ExcelUtils.getContent(src, i, 15); //零售价
                String wholesaleDecimal = ExcelUtils.getContent(src, i, 16); //销售价
                String lowDecimal = ExcelUtils.getContent(src, i, 17); //最低售价
                String enabled = ExcelUtils.getContent(src, i, 18); //状态
                String enableSerialNumber = ExcelUtils.getContent(src, i, 19); //序列号
                String enableBatchNumber = ExcelUtils.getContent(src, i, 20); //批号
                String position = ExcelUtils.getContent(src, i, 21); //仓位货架
                String mfrs = ExcelUtils.getContent(src, i, 22); //制造商
                String otherField1 = ExcelUtils.getContent(src, i, 23); //自定义1
                String otherField2 = ExcelUtils.getContent(src, i, 24); //自定义2
                String otherField3 = ExcelUtils.getContent(src, i, 25); //自定义3
                String remark = ExcelUtils.getContent(src, i, 26); //备注
                m.setPosition(StringUtil.isNotEmpty(position)?position:null);
                m.setMfrs(StringUtil.isNotEmpty(mfrs)?mfrs:null);
                m.setOtherField1(StringUtil.isNotEmpty(otherField1)?otherField1:null);
                m.setOtherField2(StringUtil.isNotEmpty(otherField2)?otherField2:null);
                m.setOtherField3(StringUtil.isNotEmpty(otherField3)?otherField3:null);
                m.setRemark(remark);
                //状态格式错误
                if(!"1".equals(enabled) && !"0".equals(enabled)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_ENABLED_ERROR_CODE,
                            String.format(ExceptionConstants.MATERIAL_ENABLED_ERROR_MSG, i+1));
                }
                //基本条码为空
                if(StringUtil.isEmpty(barCode)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_BARCODE_EMPTY_CODE,
                            String.format(ExceptionConstants.MATERIAL_BARCODE_EMPTY_MSG, i+1));
                }
                //校验基本条码长度为4到40位
                if(!StringUtil.checkBarCodeLength(barCode)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_BARCODE_LENGTH_ERROR_CODE,
                            String.format(ExceptionConstants.MATERIAL_BARCODE_LENGTH_ERROR_MSG, barCode));
                }
                //校验副条码长度为4到40位
                if(StringUtil.isNotEmpty(manyBarCode) && !StringUtil.checkBarCodeLength(manyBarCode)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_BARCODE_LENGTH_ERROR_CODE,
                            String.format(ExceptionConstants.MATERIAL_BARCODE_LENGTH_ERROR_MSG, manyBarCode));
                }
                //批量校验excel中有无重复商品，是指名称、规格、型号、颜色、单位、多属性
                batchCheckExistMaterialListByParam(mList, name, standard, model, color, unit, sku);
                //批量校验excel中有无重复条码（1-文档自身校验，2-和数据库里面的商品校验）
                batchCheckExistBarCodeByParam(mList, barCode, manyBarCode);
                JSONObject materialExObj = new JSONObject();
                JSONObject basicObj = new JSONObject();
                basicObj.put("barCode", barCode);
                basicObj.put("commodityUnit", unit);
                basicObj.put("sku", sku);
                basicObj.put("purchaseDecimal", purchaseDecimal);
                basicObj.put("commodityDecimal", commodityDecimal);
                basicObj.put("wholesaleDecimal", wholesaleDecimal);
                basicObj.put("lowDecimal", lowDecimal);
                materialExObj.put("basic", basicObj);
                if(StringUtil.isNotEmpty(manyUnit) && StringUtil.isNotEmpty(ratio)){ //多单位
                    //校验比例是否是数字（含小数）
                    if(!StringUtil.isPositiveBigDecimal(ratio.trim())) {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_RATIO_NOT_INTEGER_CODE,
                                String.format(ExceptionConstants.MATERIAL_RATIO_NOT_INTEGER_MSG, i+1));
                    }
                    Long unitId = unitService.getUnitIdByParam(unit, manyUnit, new BigDecimal(ratio.trim()));
                    if(unitId != null) {
                        m.setUnitId(unitId);
                        m.setUnit("");
                    } else {
                        throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_UNIT_MATE_CODE,
                                String.format(ExceptionConstants.MATERIAL_UNIT_MATE_MSG, manyBarCode));
                    }
                    JSONObject otherObj = new JSONObject();
                    otherObj.put("barCode", manyBarCode);
                    otherObj.put("commodityUnit", manyUnit);
                    otherObj.put("purchaseDecimal", parsePrice(purchaseDecimal,ratio));
                    otherObj.put("commodityDecimal", parsePrice(commodityDecimal,ratio));
                    otherObj.put("wholesaleDecimal", parsePrice(wholesaleDecimal,ratio));
                    otherObj.put("lowDecimal", parsePrice(lowDecimal,ratio));
                    materialExObj.put("other", otherObj);
                } else {
                    m.setUnit(unit);
                    m.setUnitId(null);
                }
                m.setMaterialExObj(materialExObj);
                m.setEnabled("1".equals(enabled));
                if(StringUtil.isNotEmpty(enableSerialNumber) && "1".equals(enableSerialNumber)) {
                    m.setEnableSerialNumber("1");
                } else {
                    m.setEnableSerialNumber("0");
                }
                if(StringUtil.isNotEmpty(enableBatchNumber) && "1".equals(enableBatchNumber)) {
                    m.setEnableBatchNumber("1");
                } else {
                    m.setEnableBatchNumber("0");
                }
                if("1".equals(enableSerialNumber) && "1".equals(enableBatchNumber)) {
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_ENABLE_MUST_ONE_CODE,
                            String.format(ExceptionConstants.MATERIAL_ENABLE_MUST_ONE_MSG, barCode));
                }
                m.setStockMap(getStockMapCache(src, depotCount, depotMap, i));
                mList.add(m);
            }
            List<Long> deleteInitialStockMaterialIdList = new ArrayList<>();
            List<Long> deleteCurrentStockMaterialIdList = new ArrayList<>();
            List<MaterialInitialStock> insertInitialStockMaterialList = new ArrayList<>();
            List<MaterialCurrentStock> insertCurrentStockMaterialList = new ArrayList<>();
            //防止初始库存和当前库存出现重复
            Map<String, String> materialDepotInitialMap = new HashMap<>();
            Map<String, String> materialDepotCurrentMap = new HashMap<>();
            for(MaterialWithInitStock m: mList) {
                Long mId = 0L;
                //判断该商品是否存在，如果不存在就新增，如果存在就更新
                String basicBarCode = getBasicBarCode(m);
                List<Material> materials = getMaterialListByParam(m.getName(),m.getStandard(),m.getModel(),m.getColor(),m.getUnit(),m.getUnitId(), basicBarCode);
                if(materials.size() == 0) {
                    materialMapperEx.insertSelectiveEx(m);
                    mId = m.getId();
                } else {
                    mId = materials.get(0).getId();
                    String materialJson = JSON.toJSONString(m);
                    Material material = JSONObject.parseObject(materialJson, Material.class);
                    material.setId(mId);
                    materialMapper.updateByPrimaryKeySelective(material);
                    //更新多单位
                    if(material.getUnitId() == null) {
                        materialMapperEx.setUnitIdToNull(material.getId());
                    }
                    //如果之前有保质期，则更新保质期
                    if(materials.get(0).getExpiryNum()!=null && material.getExpiryNum() == null) {
                        materialMapperEx.setExpiryNumToNull(material.getId());
                    }
                }
                //给商品新增或更新条码与价格相关信息
                JSONObject materialExObj = m.getMaterialExObj();
                insertOrUpdateMaterialExtend(materialExObj, "basic", "1", mId, user);
                insertOrUpdateMaterialExtend(materialExObj, "other", "0", mId, user);
                //给商品更新库存
                Map<Long, BigDecimal> stockMap = m.getStockMap();
                for(Depot depot: depotList){
                    Long depotId = depot.getId();
                    String materialDepotKey = mId + "_" + depotId;
                    //获取初始库存
                    BigDecimal initStock = getInitStock(mId, depotId);
                    //excel里面的当前库存
                    BigDecimal stock = stockMap.get(depot.getId());
                    //新增或更新初始库存
                    if(stock!=null && stock.compareTo(BigDecimal.ZERO)!=0) {
                        String basicStr = materialExObj.getString("basic");
                        MaterialExtend materialExtend = JSONObject.parseObject(basicStr, MaterialExtend.class);
                        if(StringUtil.isNotEmpty(materialExtend.getSku())) {
                            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_SKU_BEGIN_STOCK_FAILED_CODE,
                                    String.format(ExceptionConstants.MATERIAL_SKU_BEGIN_STOCK_FAILED_MSG, materialExtend.getBarCode()));
                        }
                        buildChangeInitialStock(deleteInitialStockMaterialIdList, insertInitialStockMaterialList, materialDepotInitialMap, mId, depotId, materialDepotKey, stock);
                    } else {
                        if(initStock.compareTo(BigDecimal.ZERO)!=0) {
                            buildChangeInitialStock(deleteInitialStockMaterialIdList, insertInitialStockMaterialList, materialDepotInitialMap, mId, depotId, materialDepotKey, stock);
                        }
                    }
                    //新增或更新当前库存
                    Long billCount = depotItemService.getCountByMaterialAndDepot(mId, depotId);
                    if(billCount == 0) {
                        if(stock!=null && stock.compareTo(BigDecimal.ZERO)!=0) {
                            buildChangeCurrentStock(deleteCurrentStockMaterialIdList, insertCurrentStockMaterialList, materialDepotCurrentMap, mId, depotId, materialDepotKey, stock);
                        } else {
                            if(initStock.compareTo(BigDecimal.ZERO)!=0) {
                                buildChangeCurrentStock(deleteCurrentStockMaterialIdList, insertCurrentStockMaterialList, materialDepotCurrentMap, mId, depotId, materialDepotKey, stock);
                            }
                        }
                    } else {
                        BigDecimal currentNumber = getCurrentStockByMaterialIdAndDepotId(mId, depotId);
                        //当前库存的更新：减去初始库存，再加上导入的新初始库存
                        if(currentNumber!=null && initStock!=null && stock!=null) {
                            currentNumber = currentNumber.subtract(initStock).add(stock);
                        }
                        buildChangeCurrentStock(deleteCurrentStockMaterialIdList, insertCurrentStockMaterialList, materialDepotCurrentMap, mId, depotId, materialDepotKey, currentNumber);
                    }
                }
            }
            //批量更新库存,先删除后新增
            if(insertInitialStockMaterialList.size()>0) {
                batchDeleteInitialStockByMaterialList(deleteInitialStockMaterialIdList);
                materialInitialStockMapperEx.batchInsert(insertInitialStockMaterialList);
            }
            if(insertCurrentStockMaterialList.size()>0) {
                batchDeleteCurrentStockByMaterialList(deleteCurrentStockMaterialIdList);
                materialCurrentStockMapperEx.batchInsert(insertCurrentStockMaterialList);
            }
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_IMPORT).append(mList.size()).append(BusinessConstants.LOG_DATA_UNIT).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            Long endTime = System.currentTimeMillis();
            logger.info("导入耗时：{}", endTime-beginTime);
            info.code = 200;
            info.data = "导入成功";
        } catch (BusinessRunTimeException e) {
            info.code = e.getCode();
            info.data = e.getData().get("message");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            info.code = 500;
            info.data = "导入失败";
        }
        return info;
    }

    /**
     * 构造初始库存的变化
     */
    private void buildChangeInitialStock(List<Long> deleteInitialStockMaterialIdList, List<MaterialInitialStock> insertInitialStockMaterialList,
                                         Map<String, String> materialDepotInitialMap, Long mId, Long depotId, String materialDepotKey, BigDecimal stock) {
        if(materialDepotInitialMap.get(materialDepotKey)==null) {
            MaterialInitialStock materialInitialStock = new MaterialInitialStock();
            materialInitialStock.setMaterialId(mId);
            materialInitialStock.setDepotId(depotId);
            materialInitialStock.setNumber(stock);
            insertInitialStockMaterialList.add(materialInitialStock);
            deleteInitialStockMaterialIdList.add(mId);
            materialDepotInitialMap.put(materialDepotKey, materialDepotKey);
        }
    }

    /**
     * 构造当前库存的变化
     */
    private void buildChangeCurrentStock(List<Long> deleteCurrentStockMaterialIdList, List<MaterialCurrentStock> insertCurrentStockMaterialList,
                                         Map<String, String> materialDepotCurrentMap, Long mId, Long depotId, String materialDepotKey, BigDecimal stock) {
        if(materialDepotCurrentMap.get(materialDepotKey)==null) {
            MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
            materialCurrentStock.setMaterialId(mId);
            materialCurrentStock.setDepotId(depotId);
            materialCurrentStock.setCurrentNumber(stock);
            insertCurrentStockMaterialList.add(materialCurrentStock);
            deleteCurrentStockMaterialIdList.add(mId);
            materialDepotCurrentMap.put(materialDepotKey, materialDepotKey);
        }
    }

    private Map<String, Long> parseDepotToMap(List<Depot> depotList) {
        Map<String, Long> map = new HashMap<>();
        for(Depot depot: depotList) {
            map.put(depot.getName(), depot.getId());
        }
        return map;
    }

    /**
     * 缓存各个仓库的库存信息
     * @param src
     * @param depotCount
     * @param depotMap
     * @param i
     * @return
     * @throws Exception
     */
    private Map<Long, BigDecimal> getStockMapCache(Sheet src, int depotCount, Map<String, Long> depotMap, int i) throws Exception {
        Map<Long, BigDecimal> stockMap = new HashMap<>();
        for(int j = 1; j<= depotCount; j++) {
            int col = 26 + j;
            if(col < src.getColumns()){
                String depotName = ExcelUtils.getContent(src, 1, col); //获取仓库名称
                if(StringUtil.isNotEmpty(depotName)) {
                    Long depotId = depotMap.get(depotName);
                    if(depotId!=null && depotId!=0L){
                        String stockStr = ExcelUtils.getContent(src, i, col);
                        if(StringUtil.isNotEmpty(stockStr)) {
                            stockMap.put(depotId, parseBigDecimalEx(stockStr));
                        }
                    }
                }
            }
        }
        return stockMap;
    }

    /**
     * 批量校验excel中有无重复商品，是指名称、规格、型号、颜色、单位
     * @param mList
     */
    public void batchCheckExistMaterialListByParam(List<MaterialWithInitStock> mList, String name, String standard,
                                                   String model, String color, String unit, String sku) {
        for(MaterialWithInitStock material: mList){
            String materialSku = "";
            JSONObject materialExObj = material.getMaterialExObj();
            if(materialExObj!=null && materialExObj.get("basic")!=null) {
                JSONObject basicObj = materialExObj.getJSONObject("basic");
                if(basicObj!=null && materialExObj.get("sku")!=null) {
                    materialSku = basicObj.getString("sku");
                }
            }
            if(name.equals(material.getName()) &&
                    standard.equals(material.getStandard()) &&
                    model.equals(material.getModel()) &&
                    color.equals(material.getColor()) &&
                    unit.equals(material.getUnit()) &&
                    sku.equals(materialSku)) {
                String info = name + "-" + standard + "-" + model + "-" + color + "-" + unit + "-" + sku;
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXCEL_IMPORT_EXIST_CODE,
                        String.format(ExceptionConstants.MATERIAL_EXCEL_IMPORT_EXIST_MSG, info));
            }
        }
    }

    /**
     * 批量校验excel中有无重复条码（1-文档自身校验，2-和数据库里面的商品校验）
     * @param mList
     */
    public void batchCheckExistBarCodeByParam(List<MaterialWithInitStock> mList,
                                              String barCode, String manyBarCode) throws Exception {
        if(StringUtil.isNotEmpty(manyBarCode)) {
            if(barCode.equals(manyBarCode)) {
                //同一个商品的主副条码重复了，进行提醒
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_CODE,
                        String.format(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_MSG, manyBarCode));
            }
            //EXCEL中有副条码在系统中已存在（除自身商品之外）
            int count = materialExtendService.getCountByManyBarCodeWithoutUs(manyBarCode, barCode);
            if (count>0) {
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXCEL_IMPORT_MANY_BARCODE_EXIST_CODE,
                        String.format(ExceptionConstants.MATERIAL_EXCEL_IMPORT_MANY_BARCODE_EXIST_MSG, manyBarCode));
            }
        }
        for(MaterialWithInitStock material: mList){
            JSONObject materialExObj = material.getMaterialExObj();
            String basicBarCode = "";
            String otherBarCode = "";
            if(materialExObj.get("basic")!=null) {
                JSONObject basicObj = materialExObj.getJSONObject("basic");
                basicBarCode = basicObj.getString("barCode");
            }
            if(materialExObj.get("other")!=null) {
                JSONObject otherObj = materialExObj.getJSONObject("other");
                otherBarCode = otherObj.getString("barCode");
            }
            if(barCode.equals(basicBarCode) || barCode.equals(otherBarCode)){
                throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_CODE,
                        String.format(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_MSG, barCode));
            }
            if(StringUtil.isNotEmpty(manyBarCode)) {
                if(manyBarCode.equals(basicBarCode) || manyBarCode.equals(otherBarCode)){
                    throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_CODE,
                            String.format(ExceptionConstants.MATERIAL_EXCEL_IMPORT_BARCODE_EXIST_MSG, manyBarCode));
                }
            }
        }
    }

    /**
     * 给商品新增或更新条码与价格相关信息
     * @param materialExObj
     * @param type
     * @param defaultFlag
     * @param mId
     * @param user
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void insertOrUpdateMaterialExtend(JSONObject materialExObj, String type, String defaultFlag, Long mId, User user) throws Exception {
        if(StringUtil.isExist(materialExObj.get(type))){
            String basicStr = materialExObj.getString(type);
            MaterialExtend materialExtend = JSONObject.parseObject(basicStr, MaterialExtend.class);
            materialExtend.setMaterialId(mId);
            materialExtend.setDefaultFlag(defaultFlag);
            materialExtend.setCreateTime(new Date());
            materialExtend.setUpdateTime(System.currentTimeMillis());
            materialExtend.setCreateSerial(user.getLoginName());
            materialExtend.setUpdateSerial(user.getLoginName());
            Long meId = 0L;
            if(StringUtil.isNotEmpty(materialExtend.getSku())){
                //含sku的商品，特殊逻辑
                meId = materialExtendService.selectIdByMaterialIdAndBarCode(mId, materialExtend.getBarCode());
                List<MaterialExtend> meList = materialExtendService.getListByMaterialIdAndDefaultFlagAndBarCode(mId, "1", materialExtend.getBarCode());
                if(meList.size() == 0) {
                    materialExtend.setDefaultFlag("1");
                } else {
                    materialExtend.setDefaultFlag("0");
                }
            } else {
                meId = materialExtendService.selectIdByMaterialIdAndDefaultFlag(mId, defaultFlag);
            }
            if(meId==0L){
                materialExtendMapper.insertSelective(materialExtend);
            } else {
                materialExtend.setId(meId);
                materialExtendMapper.updateByPrimaryKeySelective(materialExtend);
                //如果金额为空，此处单独置空
                materialExtendMapperEx.specialUpdatePrice(materialExtend);
            }
        }
    }

    public String getBasicBarCode(MaterialWithInitStock m) {
        String barCode = "";
        JSONObject materialExObj = m.getMaterialExObj();
        if(StringUtil.isExist(materialExObj.get("basic"))) {
            String basicStr = materialExObj.getString("basic");
            MaterialExtend basicMaterialExtend = JSONObject.parseObject(basicStr, MaterialExtend.class);
            barCode = basicMaterialExtend.getBarCode();
        }
        return barCode;
    }

    /**
     * 根据条件返回产品列表
     * @param name
     * @param standard
     * @param model
     * @param color
     * @param unit
     * @param unitId
     * @return
     */
    private List<Material> getMaterialListByParam(String name, String standard, String model, String color, String unit, Long unitId, String basicBarCode) throws Exception {
        List<Material> list = new ArrayList<>();
        MaterialExample example = new MaterialExample();
        MaterialExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (StringUtil.isNotEmpty(model)) {
            criteria.andModelEqualTo(model);
        }
        if (StringUtil.isNotEmpty(color)) {
            criteria.andColorEqualTo(color);
        }
        if (StringUtil.isNotEmpty(standard)) {
            criteria.andStandardEqualTo(standard);
        }
        if (StringUtil.isNotEmpty(unit)) {
            criteria.andUnitEqualTo(unit);
        }
        if (unitId !=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        criteria.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        list = materialMapper.selectByExample(example);
        if(list.size()==0) {
            //如果通过组合条件没有查到该商品，则通过条码再查一次
            MaterialExtend materialExtend = materialExtendService.getInfoByBarCode(basicBarCode);
            if(materialExtend != null && materialExtend.getMaterialId()!=null) {
                Material material = new Material();
                material.setId(materialExtend.getMaterialId());
                list.add(material);
            }
        }
        return list;
    }

    /**
     * 写入初始库存
     * @param depotId
     * @param mId
     * @param stock
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void insertInitialStockByMaterialAndDepot(Long depotId, Long mId, BigDecimal stock, BigDecimal lowSafeStock, BigDecimal highSafeStock){
        MaterialInitialStock materialInitialStock = new MaterialInitialStock();
        materialInitialStock.setDepotId(depotId);
        materialInitialStock.setMaterialId(mId);
        stock = stock == null? BigDecimal.ZERO: stock;
        materialInitialStock.setNumber(stock);
        if(lowSafeStock!=null) {
            materialInitialStock.setLowSafeStock(lowSafeStock);
        }
        if(highSafeStock!=null) {
            materialInitialStock.setHighSafeStock(highSafeStock);
        }
        materialInitialStockMapper.insertSelective(materialInitialStock); //存入初始库存
    }

    /**
     * 写入当前库存
     * @param depotId
     * @param mId
     * @param stock
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void insertCurrentStockByMaterialAndDepot(Long depotId, Long mId, BigDecimal stock){
        MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
        materialCurrentStock.setDepotId(depotId);
        materialCurrentStock.setMaterialId(mId);
        materialCurrentStock.setCurrentNumber(stock);
        materialCurrentStockMapper.insertSelective(materialCurrentStock); //存入初始库存
    }

    /**
     * 批量删除初始库存
     * @param mIdList
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batchDeleteInitialStockByMaterialList(List<Long> mIdList){
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        example.createCriteria().andMaterialIdIn(mIdList);
        materialInitialStockMapper.deleteByExample(example);
    }

    /**
     * 批量删除当前库存
     * @param mIdList
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batchDeleteCurrentStockByMaterialList(List<Long> mIdList){
        MaterialCurrentStockExample example = new MaterialCurrentStockExample();
        example.createCriteria().andMaterialIdIn(mIdList);
        materialCurrentStockMapper.deleteByExample(example);
    }

    public List<MaterialVo4Unit> getMaterialEnableSerialNumberList(String q, Integer offset, Integer rows)throws Exception {
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.getMaterialEnableSerialNumberList(q, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long getMaterialEnableSerialNumberCount(String q)throws Exception {
        Long count =null;
        try{
            count=  materialMapperEx.getMaterialEnableSerialNumberCount(q);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return count;
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
     * 根据商品获取初始库存-多仓库
     * @param depotList
     * @param materialId
     * @return
     */
    public BigDecimal getInitStockByMidAndDepotList(List<Long> depotList, Long materialId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        if(depotList!=null && depotList.size()>0) {
            example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdIn(depotList)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else {
            example.createCriteria().andMaterialIdEqualTo(materialId)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            for(MaterialInitialStock ms: list) {
                if(ms!=null) {
                    stock = stock.add(ms.getNumber());
                }
            }
        }
        return stock;
    }

    /**
     * 根据商品和仓库获取初始库存
     * @param materialId
     * @param depotId
     * @return
     */
    public BigDecimal getInitStock(Long materialId, Long depotId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            stock = list.get(0).getNumber();
        }
        return stock;
    }

    /**
     * 根据商品和仓库获取当前库存
     * @param materialId
     * @param depotId
     * @return
     */
    public BigDecimal getCurrentStockByMaterialIdAndDepotId(Long materialId, Long depotId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialCurrentStockExample example = new MaterialCurrentStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialCurrentStock> list = materialCurrentStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            stock = list.get(0).getCurrentNumber();
        } else {
            stock = getInitStock(materialId,depotId);
        }
        return stock;
    }

    /**
     * 根据商品列表获取初始库存Map
     * @param list
     * @return
     */
    public Map<Long,BigDecimal> getInitialStockMapByMaterialList(List<MaterialVo4Unit> list) {
        Map<Long,BigDecimal> map = new HashMap<>();
        List<Long> materialIdList = new ArrayList<>();
        for(MaterialVo4Unit materialVo4Unit: list) {
            materialIdList.add(materialVo4Unit.getId());
        }
        List<MaterialInitialStock> mcsList = materialInitialStockMapperEx.getInitialStockMapByIdList(materialIdList);
        for(MaterialInitialStock materialInitialStock: mcsList) {
            map.put(materialInitialStock.getMaterialId(), materialInitialStock.getNumber());
        }
        return map;
    }

    /**
     * 根据商品列表获取当前库存Map
     * @param list
     * @return
     */
    public Map<Long,BigDecimal> getCurrentStockMapByMaterialList(List<MaterialVo4Unit> list) {
        Map<Long,BigDecimal> map = new HashMap<>();
        List<Long> materialIdList = new ArrayList<>();
        for(MaterialVo4Unit materialVo4Unit: list) {
            materialIdList.add(materialVo4Unit.getId());
        }
        List<MaterialCurrentStock> mcsList = materialCurrentStockMapperEx.getCurrentStockMapByIdList(materialIdList);
        for(MaterialCurrentStock materialCurrentStock: mcsList) {
            map.put(materialCurrentStock.getMaterialId(), materialCurrentStock.getCurrentNumber());
        }
        return map;
    }

    /**
     * 根据商品和仓库获取安全库存信息
     * @param materialId
     * @param depotId
     * @return
     */
    public MaterialInitialStock getSafeStock(Long materialId, Long depotId) {
        MaterialInitialStock materialInitialStock = new MaterialInitialStock();
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            materialInitialStock = list.get(0);
        }
        return materialInitialStock;
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
        List<String> barCodeOldList = materialMapperEx.getBarCodeList();
        // 使用 Stream API 处理条码列表
        OptionalLong maxBarcode = barCodeOldList.stream()
                .filter(StringUtil::isNumeric)   // 过滤掉非数字条码
                .mapToLong(Long::parseLong)      // 将字符串转换为 Long 类型
                .max();                          // 获取最大值
        // 如果存在最大值，返回它；否则返回 1000L
        Long maxBarCodeOld = maxBarcode.orElse(1000L);
        return maxBarCodeOld + "";
    }

    public List<String> getMaterialNameList() {
        return materialMapperEx.getMaterialNameList();
    }

    public List<MaterialVo4Unit> getMaterialByBarCode(String barCode) {
        String [] barCodeArray=barCode.split(",");
        return materialMapperEx.getMaterialByBarCode(barCodeArray);
    }

    public List<MaterialVo4Unit> getMaterialByBarCodeAndWithOutMId(String barCode, Long mId) {
        String [] barCodeArray=barCode.split(",");
        return materialMapperEx.getMaterialByBarCodeAndWithOutMId(barCodeArray, mId);
    }

    public List<MaterialInitialStockWithMaterial> getInitialStockWithMaterial(List<Long> depotList) {
        return materialMapperEx.getInitialStockWithMaterial(depotList);
    }

    public List<MaterialVo4Unit> getListWithStock(List<Long> depotList, List<Long> idList, String position, String materialParam,
                                                  Boolean moveAvgPriceFlag, Integer zeroStock, String column, String order,
                                                  Integer offset, Integer rows) throws Exception {
        Map<Long, BigDecimal> initialStockMap = new HashMap<>();
        List<MaterialInitialStockWithMaterial> initialStockList = getInitialStockWithMaterial(depotList);
        for (MaterialInitialStockWithMaterial mism: initialStockList) {
            initialStockMap.put(mism.getMaterialId(), mism.getNumber());
        }
        List<MaterialVo4Unit> dataList = materialMapperEx.getListWithStock(depotList, idList, position, materialParam, zeroStock, column, order, offset, rows);
        for(MaterialVo4Unit item: dataList) {
            if(moveAvgPriceFlag) {
                item.setPurchaseDecimal(item.getCurrentUnitPrice());
                item.setCurrentStockPrice(item.getCurrentStockMovePrice());
            }
            item.setUnitName(null!=item.getUnitId()?item.getUnitName() + "[多单位]":item.getUnitName());
            item.setInitialStock(null!=initialStockMap.get(item.getId())?initialStockMap.get(item.getId()):BigDecimal.ZERO);
            item.setBigUnitStock(getBigUnitStock(item.getCurrentStock(), item.getUnitId()));
            if(fileUploadType == 2) {
                item.setImgSmall("small");
                item.setImgLarge("large");
            }
        }
        return dataList;
    }

    public int getListWithStockCount(List<Long> depotList, List<Long> idList, String position, String materialParam, Integer zeroStock) {
        return materialMapperEx.getListWithStockCount(depotList, idList, position, materialParam, zeroStock);
    }

    public MaterialVo4Unit getTotalStockAndPrice(List<Long> depotList, List<Long> idList, String position, String materialParam) {
        return materialMapperEx.getTotalStockAndPrice(depotList, idList, position, materialParam);
    }

    /**
     * 将小单位的库存换算为大单位的库存
     * @param stock
     * @param unitId
     * @return
     * @throws Exception
     */
    public String getBigUnitStock(BigDecimal stock, Long unitId) throws Exception {
        String bigUnitStock = "";
        if(null!= unitId) {
            Unit unit = unitService.getUnit(unitId);
            if(unit.getRatio()!=null && unit.getRatio().compareTo(BigDecimal.ZERO)!=0 && stock!=null) {
                bigUnitStock = stock.divide(unit.getRatio(),2,BigDecimal.ROUND_HALF_UP) + unit.getOtherUnit();
            }
        }
        return bigUnitStock;
    }

    /**
     * 构造扩展信息
     * @param mpArr
     * @param m
     * @return
     */
    public String getMaterialOtherByParam(String[] mpArr, MaterialVo4Unit m) {
        String materialOther = "";
        for (int i = 0; i < mpArr.length; i++) {
            if (mpArr[i].equals("自定义1")) {
                materialOther = materialOther + ((m.getOtherField1() == null || m.getOtherField1().equals("")) ? "" : "(" + m.getOtherField1() + ")");
            }
            if (mpArr[i].equals("自定义2")) {
                materialOther = materialOther + ((m.getOtherField2() == null || m.getOtherField2().equals("")) ? "" : "(" + m.getOtherField2() + ")");
            }
            if (mpArr[i].equals("自定义3")) {
                materialOther = materialOther + ((m.getOtherField3() == null || m.getOtherField3().equals("")) ? "" : "(" + m.getOtherField3() + ")");
            }
        }
        return materialOther;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetMaterialCurrentStock(String ids) throws Exception {
        int res = 0;
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Depot> depotList = depotService.getAllList();
        for(Long mId: idList) {
            BigDecimal currentUnitPrice = materialCurrentStockMapperEx.getCurrentUnitPriceByMId(mId);
            for(Depot depot: depotList) {
                depotItemService.updateCurrentStockFun(mId, depot.getId(), currentUnitPrice);
                res = 1;
            }
        }
        return res;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetMaterialCurrentUnitPrice(String ids) throws Exception {
        int res = 0;
        List<Long> idList = StringUtil.strToLongList(ids);
        for(Long mId: idList) {
            DepotItem depotItem = new DepotItem();
            depotItem.setMaterialId(mId);
            depotItemService.updateCurrentUnitPrice(depotItem);
            res = 1;
        }
        return res;
    }

    public int batchUpdate(JSONObject jsonObject) {
        String ids = jsonObject.getString("ids");
        String materialStr = jsonObject.getString("material");
        List<Long> idList = StringUtil.strToLongList(ids);
        Material material = JSONObject.parseObject(materialStr, Material.class);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(idList).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        return materialMapper.updateByExampleSelective(material, example);
    }

    public MaterialExtend getMaterialExtendBySerialNumber(String serialNumber) {
        return materialMapperEx.getMaterialExtendBySerialNumber(serialNumber);
    }

    /**
     * 构造多属性数组字符串
     * @param obj
     * @return
     */
    public String parseAttributeBySku(JSONObject obj) {
        if(obj!=null) {
            JSONObject attributeObj = new JSONObject();
            JSONArray manySku = obj.getJSONArray("manySku");
            JSONArray skuOne = obj.getJSONArray("skuOne");
            JSONArray skuTwo = obj.getJSONArray("skuTwo");
            JSONArray skuThree = obj.getJSONArray("skuThree");
            attributeObj.put("manySku", manySku);
            attributeObj.put("skuOne", skuOne);
            attributeObj.put("skuTwo", skuTwo);
            attributeObj.put("skuThree", skuThree);
            return attributeObj.toJSONString();
        } else {
            return null;
        }
    }
}
