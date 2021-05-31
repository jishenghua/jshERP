package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.DepotEx;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua jshERP
 */
@RestController
@RequestMapping(value = "/material")
public class MaterialController {
    private Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private MaterialService materialService;

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private RedisService redisService;

    @GetMapping(value = "/checkIsExist")
    public String checkIsExist(@RequestParam("id") Long id, @RequestParam("name") String name,
                               @RequestParam("model") String model, @RequestParam("color") String color,
                               @RequestParam("standard") String standard, @RequestParam("mfrs") String mfrs,
                               @RequestParam("otherField1") String otherField1, @RequestParam("otherField2") String otherField2,
                               @RequestParam("otherField3") String otherField3, @RequestParam("unit") String unit,@RequestParam("unitId") Long unitId,
                               HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = materialService.checkIsExist(id, name, model, color, standard, mfrs,
                otherField1, otherField2, otherField3, unit, unitId);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/batchSetStatus")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = materialService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 根据id来查询商品名称
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/findById")
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialVo4Unit> list = materialService.findById(id);
            res.code = 200;
            res.data = list;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据meId来查询商品名称
     * @param meId
     * @param request
     * @return
     */
    @GetMapping(value = "/findByIdWithBarCode")
    public BaseResponseInfo findByIdWithBarCode(@RequestParam("meId") Long meId,
                                                @RequestParam("mpList") String mpList,
                                                HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String[] mpArr = mpList.split(",");
            MaterialVo4Unit mu = new MaterialVo4Unit();
            List<MaterialVo4Unit> list = materialService.findByIdWithBarCode(meId);
            if(list!=null && list.size()>0) {
                mu = list.get(0);
                String expand = ""; //扩展信息
                for (int i = 0; i < mpArr.length; i++) {
                    if (mpArr[i].equals("制造商")) {
                        expand = expand + ((mu.getMfrs() == null || mu.getMfrs().equals("")) ? "" : "(" + mu.getMfrs() + ")");
                    }
                    if (mpArr[i].equals("自定义1")) {
                        expand = expand + ((mu.getOtherField1() == null || mu.getOtherField1().equals("")) ? "" : "(" + mu.getOtherField1() + ")");
                    }
                    if (mpArr[i].equals("自定义2")) {
                        expand = expand + ((mu.getOtherField2() == null || mu.getOtherField2().equals("")) ? "" : "(" + mu.getOtherField2() + ")");
                    }
                    if (mpArr[i].equals("自定义3")) {
                        expand = expand + ((mu.getOtherField3() == null || mu.getOtherField3().equals("")) ? "" : "(" + mu.getOtherField3() + ")");
                    }
                }
                mu.setMaterialOther(expand);
            }
            res.code = 200;
            res.data = mu;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 查找商品信息-下拉框
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/findBySelect")
    public JSONObject findBySelect(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                  @RequestParam(value = "q", required = false) String q,
                                  @RequestParam("mpList") String mpList,
                                  @RequestParam(value = "depotId", required = false) Long depotId,
                                  @RequestParam("page") Integer currentPage,
                                  @RequestParam("rows") Integer pageSize,
                                  HttpServletRequest request) throws Exception{
        JSONObject object = new JSONObject();
        try {
            Long tenantId = redisService.getTenantId(request);
            List<MaterialVo4Unit> dataList = materialService.findBySelectWithBarCode(categoryId, q, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = materialService.findBySelectWithBarCodeCount(categoryId, q);
            object.put("total", total);
            JSONArray dataArray = new JSONArray();
            //存放数据json数组
            if (null != dataList) {
                for (MaterialVo4Unit material : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", material.getMeId()); //商品扩展表的id
                    String ratio; //比例
                    if (material.getUnitId() == null || material.getUnitId().equals("")) {
                        ratio = "";
                    } else {
                        ratio = material.getUnitName();
                        if(ratio!=null) {
                            ratio = ratio.substring(ratio.indexOf("("));
                        }
                    }
                    //名称/型号/扩展信息/包装
                    String MaterialName = "";
                    String mBarCode = "";
                    if(material.getmBarCode()!=null) {
                        mBarCode = material.getmBarCode();
                        MaterialName = MaterialName + mBarCode + "_";
                    }
                    item.put("mBarCode", mBarCode);
                    MaterialName = MaterialName + " " + material.getName()
                            + ((material.getStandard() == null || material.getStandard().equals("")) ? "" : "(" + material.getStandard() + ")")
                            + ((material.getModel() == null || material.getModel().equals("")) ? "" : "(" + material.getModel() + ")");
                    String expand = ""; //扩展信息
                    for (int i = 0; i < mpArr.length; i++) {
                        if (mpArr[i].equals("制造商")) {
                            expand = expand + ((material.getMfrs() == null || material.getMfrs().equals("")) ? "" : "(" + material.getMfrs() + ")");
                        }
                        if (mpArr[i].equals("自定义1")) {
                            expand = expand + ((material.getOtherField1() == null || material.getOtherField1().equals("")) ? "" : "(" + material.getOtherField1() + ")");
                        }
                        if (mpArr[i].equals("自定义2")) {
                            expand = expand + ((material.getOtherField2() == null || material.getOtherField2().equals("")) ? "" : "(" + material.getOtherField2() + ")");
                        }
                        if (mpArr[i].equals("自定义3")) {
                            expand = expand + ((material.getOtherField3() == null || material.getOtherField3().equals("")) ? "" : "(" + material.getOtherField3() + ")");
                        }
                    }
                    MaterialName = MaterialName + expand + ((material.getCommodityUnit() == null || material.getCommodityUnit().equals("")) ? "" : "(" + material.getCommodityUnit() + ")") + ratio;
                    item.put("materialName", MaterialName);
                    item.put("categoryName", material.getCategoryName());
                    item.put("name", material.getName());
                    item.put("expand", expand);
                    item.put("model", material.getModel());
                    item.put("standard", material.getStandard());
                    item.put("unit", material.getCommodityUnit() + ratio);
                    if(depotId!=null) {
                        BigDecimal stock = depotItemService.getStockByParam(depotId,material.getId(),null,null,tenantId);
                        item.put("stock", stock);
                    }
                    dataArray.add(item);
                }
            }
            object.put("rows", dataArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 根据商品id查找商品信息
     * @param meId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getMaterialByMeId")
    public JSONObject getMaterialByMeId(@RequestParam(value = "meId", required = false) Long meId,
                                        @RequestParam("mpList") String mpList,
                                        HttpServletRequest request) throws Exception{
        JSONObject item = new JSONObject();
        try {
            String[] mpArr = mpList.split(",");
            List<MaterialVo4Unit> materialList = materialService.getMaterialByMeId(meId);
            if(materialList!=null && materialList.size()!=1) {
                return item;
            } else if(materialList.size() == 1) {
                MaterialVo4Unit material = materialList.get(0);
                item.put("Id", material.getMeId()); //商品扩展表的id
                String ratio; //比例
                if (material.getUnitId() == null || material.getUnitId().equals("")) {
                    ratio = "";
                } else {
                    ratio = material.getUnitName();
                    ratio = ratio.substring(ratio.indexOf("("));
                }
                //名称/型号/扩展信息/包装
                String MaterialName = "";
                MaterialName = MaterialName + material.getmBarCode() + "_" + material.getName()
                        + ((material.getStandard() == null || material.getStandard().equals("")) ? "" : "(" + material.getStandard() + ")");
                String expand = ""; //扩展信息
                for (int i = 0; i < mpArr.length; i++) {
                    if (mpArr[i].equals("颜色")) {
                        expand = expand + ((material.getColor() == null || material.getColor().equals("")) ? "" : "(" + material.getColor() + ")");
                    }
                    if (mpArr[i].equals("制造商")) {
                        expand = expand + ((material.getMfrs() == null || material.getMfrs().equals("")) ? "" : "(" + material.getMfrs() + ")");
                    }
                    if (mpArr[i].equals("自定义1")) {
                        expand = expand + ((material.getOtherField1() == null || material.getOtherField1().equals("")) ? "" : "(" + material.getOtherField1() + ")");
                    }
                    if (mpArr[i].equals("自定义2")) {
                        expand = expand + ((material.getOtherField2() == null || material.getOtherField2().equals("")) ? "" : "(" + material.getOtherField2() + ")");
                    }
                    if (mpArr[i].equals("自定义3")) {
                        expand = expand + ((material.getOtherField3() == null || material.getOtherField3().equals("")) ? "" : "(" + material.getOtherField3() + ")");
                    }
                }
                MaterialName = MaterialName + expand + ((material.getUnit() == null || material.getUnit().equals("")) ? "" : "(" + material.getUnit() + ")") + ratio;
                item.put("MaterialName", MaterialName);
                item.put("name", material.getName());
                item.put("expand", expand);
                item.put("model", material.getModel());
                item.put("standard", material.getStandard());
                item.put("unit", material.getUnit() + ratio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * 生成excel表格
     * @param barCode
     * @param name
     * @param standard
     * @param model
     * @param categoryId
     * @param request
     * @param response
     */
    @GetMapping(value = "/exportExcel")
    public void exportExcel(@RequestParam("categoryId") String categoryId,
                            @RequestParam("barCode") String barCode,
                            @RequestParam("name") String name,
                            @RequestParam("standard") String standard,
                            @RequestParam("model") String model,
                            @RequestParam("mpList") String mpList,
                            HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MaterialVo4Unit> dataList = materialService.findByAll(StringUtil.toNull(barCode), StringUtil.toNull(name),
                    StringUtil.toNull(standard), StringUtil.toNull(model), StringUtil.toNull(categoryId));
            String[] names = {"名称", "类型", "型号", "安全存量", "单位", "零售价", "最低售价", "采购价", "销售价", "备注", "状态"};
            String title = "商品信息";
            List<String[]> objects = new ArrayList<String[]>();
            if (null != dataList) {
                for (MaterialVo4Unit m : dataList) {
                    String[] objs = new String[11];
                    objs[0] = m.getName();
                    objs[1] = m.getCategoryName();
                    objs[2] = m.getModel();
                    objs[3] = m.getSafetyStock() == null? "" : m.getSafetyStock().toString();
                    objs[4] = m.getCommodityUnit();
                    objs[5] = m.getCommodityDecimal() == null? "" : m.getCommodityDecimal().toString();
                    objs[6] = m.getLowDecimal() == null? "" : m.getLowDecimal().toString();
                    objs[7] = m.getPurchaseDecimal() == null? "" : m.getPurchaseDecimal().toString();
                    objs[8] = m.getWholesaleDecimal() == null? "" : m.getWholesaleDecimal().toString();
                    objs[9] = m.getRemark();
                    objs[10] = m.getEnabled() ? "启用" : "禁用";
                    objects.add(objs);
                }
            }
            File file = ExcelUtils.exportObjectsWithoutTitle(title, names, title, objects);
            ExportExecUtil.showExec(file, file.getName(), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * excel表格导入产品（含初始库存）
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importExcel")
    public BaseResponseInfo importExcel(MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        String message = "成功";
        try {
            Sheet src = null;
            //文件合法性校验
            try {
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                src = workbook.getSheet(0);
            } catch (Exception e) {
            }
            res = materialService.importExcel(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public BigDecimal parseBigDecimalEx(String str)throws Exception{
        if(!StringUtil.isEmpty(str)) {
            return  new BigDecimal(str);
        } else {
            return null;
        }
    }
    @GetMapping(value = "/getMaterialEnableSerialNumberList")
    public JSONObject getMaterialEnableSerialNumberList(
                                @RequestParam(value = "q", required = false) String q,
                                @RequestParam("page") Integer currentPage,
                                @RequestParam("rows") Integer pageSize,
                                HttpServletRequest request,
                                HttpServletResponse response)throws Exception {
        JSONObject object= new JSONObject();
        try {
            List<MaterialVo4Unit> list = materialService.getMaterialEnableSerialNumberList(q, (currentPage-1)*pageSize, pageSize);
            Long count = materialService.getMaterialEnableSerialNumberCount(q);
            object.put("rows", list);
            object.put("total", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @GetMapping(value = "/getMaxBarCode")
    public BaseResponseInfo getMaxBarCode() throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String barCode = materialService.getMaxBarCode();
        map.put("barCode", barCode);
        res.code = 200;
        res.data = map;
        return res;
    }

    /**
     * 商品名称模糊匹配
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getMaterialNameList")
    public JSONArray getMaterialNameList() throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<String> list = materialService.getMaterialNameList();
            for (String s : list) {
                JSONObject item = new JSONObject();
                item.put("value", s);
                item.put("text", s);
                arr.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 根据条码查询商品信息
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getMaterialByBarCode")
    public BaseResponseInfo getMaterialByBarCode(@RequestParam("barCode") String barCode,
                                          @RequestParam("mpList") String mpList,
                                          HttpServletRequest request) throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String[] mpArr = mpList.split(",");
            MaterialVo4Unit mu = new MaterialVo4Unit();
            List<MaterialVo4Unit> list = materialService.getMaterialByBarCode(barCode);
            if(list!=null && list.size()>0) {
                mu = list.get(0);
                String expand = ""; //扩展信息
                for (int i = 0; i < mpArr.length; i++) {
                    if (mpArr[i].equals("制造商")) {
                        expand = expand + ((mu.getMfrs() == null || mu.getMfrs().equals("")) ? "" : "(" + mu.getMfrs() + ")");
                    }
                    if (mpArr[i].equals("自定义1")) {
                        expand = expand + ((mu.getOtherField1() == null || mu.getOtherField1().equals("")) ? "" : "(" + mu.getOtherField1() + ")");
                    }
                    if (mpArr[i].equals("自定义2")) {
                        expand = expand + ((mu.getOtherField2() == null || mu.getOtherField2().equals("")) ? "" : "(" + mu.getOtherField2() + ")");
                    }
                    if (mpArr[i].equals("自定义3")) {
                        expand = expand + ((mu.getOtherField3() == null || mu.getOtherField3().equals("")) ? "" : "(" + mu.getOtherField3() + ")");
                    }
                }
                mu.setMaterialOther(expand);
            }
            res.code = 200;
            res.data = mu;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
