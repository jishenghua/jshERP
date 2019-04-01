package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.DepotEx;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.material.MaterialService;
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
 * @author ji|sheng|hua 华夏ERP
 */
@RestController
@RequestMapping(value = "/material")
public class MaterialController {
    private Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private MaterialService materialService;

    @GetMapping(value = "/checkIsExist")
    public String checkIsExist(@RequestParam("id") Long id, @RequestParam("name") String name,
                               @RequestParam("model") String model, @RequestParam("color") String color,
                               @RequestParam("standard") String standard, @RequestParam("mfrs") String mfrs,
                               @RequestParam("otherField1") String otherField1, @RequestParam("otherField2") String otherField2,
                               @RequestParam("otherField3") String otherField3, @RequestParam("unit") String unit,@RequestParam("unitId") Long unitId,
                               HttpServletRequest request) {
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
     * @param enabled
     * @param materialIDs
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetEnable")
    public String batchSetEnable(@RequestParam("enabled") Boolean enabled,
                                 @RequestParam("materialIDs") String materialIDs,
                                 HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = materialService.batchSetEnable(enabled, materialIDs);
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
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request) {
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
     * 查找商品信息-下拉框
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/findBySelect")
    public JSONArray findBySelect(@RequestParam("mpList") String mpList, HttpServletRequest request) {
        JSONArray dataArray = new JSONArray();
        try {
            List<MaterialVo4Unit> dataList = materialService.findBySelect();
            String[] mpArr = mpList.split(",");
            //存放数据json数组
            if (null != dataList) {
                for (MaterialVo4Unit material : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    String ratio; //比例
                    if (material.getUnitid() == null || material.getUnitid().equals("")) {
                        ratio = "";
                    } else {
                        ratio = material.getUnitName();
                        ratio = ratio.substring(ratio.indexOf("("));
                    }
                    //品名/型号/扩展信息/包装
                    String MaterialName = material.getName() + ((material.getModel() == null || material.getModel().equals("")) ? "" : "(" + material.getModel() + ")");
                    for (int i = 0; i < mpArr.length; i++) {
                        if (mpArr[i].equals("颜色")) {
                            MaterialName = MaterialName + ((material.getColor() == null || material.getColor().equals("")) ? "" : "(" + material.getColor() + ")");
                        }
                        if (mpArr[i].equals("规格")) {
                            MaterialName = MaterialName + ((material.getStandard() == null || material.getStandard().equals("")) ? "" : "(" + material.getStandard() + ")");
                        }
                        if (mpArr[i].equals("制造商")) {
                            MaterialName = MaterialName + ((material.getMfrs() == null || material.getMfrs().equals("")) ? "" : "(" + material.getMfrs() + ")");
                        }
                        if (mpArr[i].equals("自定义1")) {
                            MaterialName = MaterialName + ((material.getOtherfield1() == null || material.getOtherfield1().equals("")) ? "" : "(" + material.getOtherfield1() + ")");
                        }
                        if (mpArr[i].equals("自定义2")) {
                            MaterialName = MaterialName + ((material.getOtherfield2() == null || material.getOtherfield2().equals("")) ? "" : "(" + material.getOtherfield2() + ")");
                        }
                        if (mpArr[i].equals("自定义3")) {
                            MaterialName = MaterialName + ((material.getOtherfield3() == null || material.getOtherfield3().equals("")) ? "" : "(" + material.getOtherfield3() + ")");
                        }
                    }
                    MaterialName = MaterialName + ((material.getUnit() == null || material.getUnit().equals("")) ? "" : "(" + material.getUnit() + ")") + ratio;
                    item.put("MaterialName", MaterialName);
                    dataArray.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;
    }


    /**
     * 查找商品信息-统计排序
     * @param request
     * @return
     */
    @GetMapping(value = "/findByOrder")
    public BaseResponseInfo findByOrder(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Material> dataList = materialService.findByOrder();
            String mId = "";
            if (null != dataList) {
                for (Material material : dataList) {
                    mId = mId + material.getId() + ",";
                }
            }
            if (mId != "") {
                mId = mId.substring(0, mId.lastIndexOf(","));
            }
            map.put("mIds", mId);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 生成excel表格
     * @param name
     * @param model
     * @param categoryId
     * @param categoryIds
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/exportExcel")
    public BaseResponseInfo exportExcel(@RequestParam("name") String name,
                                        @RequestParam("model") String model,
                                        @RequestParam("categoryId") Long categoryId,
                                        @RequestParam("categoryIds") String categoryIds,
                                        HttpServletRequest request, HttpServletResponse response) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "成功";
        try {
            List<MaterialVo4Unit> dataList = materialService.findByAll(name, model, categoryId, categoryIds);
            String[] names = {"品名", "类型", "型号", "安全存量", "单位", "零售价", "最低售价", "预计采购价", "批发价", "备注", "状态"};
            String title = "商品信息";
            List<String[]> objects = new ArrayList<String[]>();
            if (null != dataList) {
                for (MaterialVo4Unit m : dataList) {
                    String[] objs = new String[11];
                    objs[0] = m.getName();
                    objs[1] = m.getCategoryName();
                    objs[2] = m.getModel();
                    objs[3] = m.getSafetystock() == null? "" : m.getSafetystock().toString();
                    objs[4] = m.getUnit();
                    objs[5] = m.getRetailprice() == null ? "" : m.getRetailprice().toString();
                    objs[6] = m.getLowprice() == null ? "" : m.getLowprice().toString();
                    objs[7] = m.getPresetpriceone() == null ? "" : m.getPresetpriceone().toString();
                    objs[8] = m.getPresetpricetwo() == null ? "" : m.getPresetpricetwo().toString();
                    objs[9] = m.getRemark();
                    objs[10] = m.getEnabled() ? "启用" : "禁用";
                    objects.add(objs);
                }
            }
            File file = ExcelUtils.exportObjectsWithoutTitle(title, names, title, objects);
            ExportExecUtil.showExec(file, file.getName(), response);
            res.code = 200;
        } catch (Exception e) {
            e.printStackTrace();
            message = "导出失败";
            res.code = 500;
        } finally {
            map.put("message", message);
            res.data = map;
        }
        return res;
    }

    /**
     * excel表格导入
     * @param materialFile
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importExcel")
    public void importExcel(MultipartFile materialFile,
                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        String message = "成功";
        try {
            Sheet src = null;
            //文件合法性校验
            try {
                Workbook workbook = Workbook.getWorkbook(materialFile.getInputStream());
                src = workbook.getSheet(0);
            } catch (Exception e) {
                message = "导入文件不合法，请检查";
                data.put("message", message);
                info.code = 400;
                info.data = data;
            }
            //读取所有的摄像机编码
            //每行中数据顺序  "品名","类型","型号","安全存量","单位","零售价","最低售价","预计采购价","批发价","备注","状态"
            List<Material> mList = new ArrayList<Material>();
            for (int i = 1; i < src.getRows(); i++) {
                Material m = new Material();
                m.setName(ExcelUtils.getContent(src, i, 0));
                m.setCategoryid(1L); //根目录
                m.setModel(ExcelUtils.getContent(src, i, 2));
                String safetyStock = ExcelUtils.getContent(src, i, 3);
                m.setSafetystock(parseBigDecimalEx(safetyStock));
                m.setUnit(ExcelUtils.getContent(src, i, 4));
                String retailprice = ExcelUtils.getContent(src, i, 5);
                m.setRetailprice(parseBigDecimalEx(retailprice));
                String lowPrice = ExcelUtils.getContent(src, i, 6);
                m.setLowprice(parseBigDecimalEx(lowPrice));
                String presetpriceone = ExcelUtils.getContent(src, i, 7);
                m.setPresetpriceone(parseBigDecimalEx(presetpriceone));
                String presetpricetwo = ExcelUtils.getContent(src, i, 8);
                m.setPresetpricetwo(parseBigDecimalEx(presetpricetwo));
                m.setRemark(ExcelUtils.getContent(src, i, 9));
                String enabled = ExcelUtils.getContent(src, i, 10);
                m.setEnabled(enabled.equals("启用")? true: false);
                mList.add(m);
            }
            info = materialService.importExcel(mList);
        } catch (Exception e) {
            e.printStackTrace();
            message = "导入失败";
            info.code = 500;
            data.put("message", message);
            info.data = data;
        }
        response.sendRedirect("../pages/materials/material.html");
    }

    public BigDecimal parseBigDecimalEx(String str){
        if(!StringUtil.isEmpty(str)) {
            return  new BigDecimal(str);
        } else {
            return null;
        }
    }
    @RequestMapping(value = "/getMaterialEnableSerialNumberList")
    public String getMaterialEnableSerialNumberList(@RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
                               @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
                               @RequestParam(value = Constants.SEARCH, required = false) String search) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        //查询参数
        JSONObject obj=JSON.parseObject(search);
        Set<String> key= obj.keySet();
        for(String keyEach: key){
            parameterMap.put(keyEach,obj.getString(keyEach));
        }
        PageQueryInfo queryInfo = new PageQueryInfo();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize == null || pageSize <= 0) {
            pageSize = BusinessConstants.DEFAULT_PAGINATION_PAGE_SIZE;
        }
        if (currentPage == null || currentPage <= 0) {
            currentPage = BusinessConstants.DEFAULT_PAGINATION_PAGE_NUMBER;
        }
        PageHelper.startPage(currentPage,pageSize,true);
        List<Material> list = materialService.getMaterialEnableSerialNumberList(parameterMap);
        //获取分页查询后的数据
        PageInfo<Material> pageInfo = new PageInfo<>(list);
        objectMap.put("page", queryInfo);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(BusinessConstants.DEFAULT_LIST_NULL_NUMBER);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(list);
        queryInfo.setTotal(pageInfo.getTotal());
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }
    /**
     * create by: qiankunpingtai
     * website：http://39.105.146.63/symphony/
     * description:
     *  批量删除商品信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteMaterialByIds")
    public Object batchDeleteMaterialByIds(@RequestParam("ids") String ids) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i= materialService.batchDeleteMaterialByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.MATERIAL_DELETE_FAILED_CODE,ExceptionConstants.MATERIAL_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_DELETE_FAILED_CODE,
                    ExceptionConstants.MATERIAL_DELETE_FAILED_MSG);
        }
        return result;
    }
}
