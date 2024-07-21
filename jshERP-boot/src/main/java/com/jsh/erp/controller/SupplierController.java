package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua 管伊佳erp
 */
@RestController
@RequestMapping(value = "/supplier")
@Api(tags = {"商家管理"})
public class SupplierController {
    private Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    private SupplierService supplierService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    @Resource
    private UserService userService;

    @GetMapping(value = "/checkIsNameAndTypeExist")
    @ApiOperation(value = "检查名称和类型是否存在")
    public String checkIsNameAndTypeExist(@RequestParam Long id,
                                          @RequestParam(value ="name", required = false) String name,
                                          @RequestParam(value ="type") String type,
                                          HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int exist = supplierService.checkIsNameAndTypeExist(id, name, type);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 查找客户信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_cus")
    @ApiOperation(value = "查找客户信息")
    public JSONArray findBySelectCus(HttpServletRequest request) {
        JSONArray arr = new JSONArray();
        try {
            String type = "UserCustomer";
            Long userId = userService.getUserId(request);
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, userId.toString());
            List<Supplier> supplierList = supplierService.findBySelectCus();
            JSONArray dataArray = new JSONArray();
            if (null != supplierList) {
                boolean customerFlag = systemConfigService.getCustomerFlag();
                for (Supplier supplier : supplierList) {
                    JSONObject item = new JSONObject();
                    Boolean flag = ubValue.contains("[" + supplier.getId().toString() + "]");
                    if (!customerFlag || flag) {
                        item.put("id", supplier.getId());
                        item.put("supplier", supplier.getSupplier()); //客户名称
                        dataArray.add(item);
                    }
                }
            }
            arr = dataArray;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    /**
     * 查找供应商信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_sup")
    @ApiOperation(value = "查找供应商信息")
    public JSONArray findBySelectSup(HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            List<Supplier> supplierList = supplierService.findBySelectSup();
            JSONArray dataArray = new JSONArray();
            if (null != supplierList) {
                for (Supplier supplier : supplierList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //供应商名称
                    item.put("supplier", supplier.getSupplier());
                    dataArray.add(item);
                }
            }
            arr = dataArray;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    /**
     * 查找往来单位，含供应商和客户信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_organ")
    @ApiOperation(value = "查找往来单位，含供应商和客户信息")
    public JSONArray findBySelectOrgan(HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            JSONArray dataArray = new JSONArray();
            //1、获取供应商信息
            List<Supplier> supplierList = supplierService.findBySelectSup();
            if (null != supplierList) {
                for (Supplier supplier : supplierList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    item.put("supplier", supplier.getSupplier() + "[供应商]"); //供应商名称
                    dataArray.add(item);
                }
            }
            //2、获取客户信息
            String type = "UserCustomer";
            Long userId = userService.getUserId(request);
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, userId.toString());
            List<Supplier> customerList = supplierService.findBySelectCus();
            if (null != customerList) {
                boolean customerFlag = systemConfigService.getCustomerFlag();
                for (Supplier supplier : customerList) {
                    JSONObject item = new JSONObject();
                    Boolean flag = ubValue.contains("[" + supplier.getId().toString() + "]");
                    if (!customerFlag || flag) {
                        item.put("id", supplier.getId());
                        item.put("supplier", supplier.getSupplier() + "[客户]"); //客户名称
                        dataArray.add(item);
                    }
                }
            }
            arr = dataArray;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    /**
     * 查找会员信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_retail")
    @ApiOperation(value = "查找会员信息")
    public JSONArray findBySelectRetail(HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<Supplier> supplierList = supplierService.findBySelectRetail();
            JSONArray dataArray = new JSONArray();
            if (null != supplierList) {
                for (Supplier supplier : supplierList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //客户名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("advanceIn", supplier.getAdvanceIn()); //预付款金额
                    dataArray.add(item);
                }
            }
            arr = dataArray;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    @ApiOperation(value = "批量设置状态")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = supplierService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 用户对应客户显示
     * @param type
     * @param keyId
     * @param request
     * @return
     */
    @GetMapping(value = "/findUserCustomer")
    @ApiOperation(value = "用户对应客户显示")
    public JSONArray findUserCustomer(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                   HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
            List<Supplier> dataList = supplierService.findUserCustomer();
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 0);
            outer.put("key", 0);
            outer.put("value", 0);
            outer.put("title", "客户列表");
            outer.put("attributes", "客户列表");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    item.put("key", supplier.getId());
                    item.put("value", supplier.getId());
                    item.put("title", supplier.getSupplier());
                    item.put("attributes", supplier.getSupplier());
                    Boolean flag = ubValue.contains("[" + supplier.getId().toString() + "]");
                    if (flag) {
                        item.put("checked", true);
                    }
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            arr.add(outer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    /**
     * 根据客户或供应商查询期初、期初已收等信息
     * @param organId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getBeginNeedByOrganId")
    @ApiOperation(value = "根据客户或供应商查询期初、期初已收等信息")
    public BaseResponseInfo getBeginNeedByOrganId(@RequestParam("organId") Long organId,
                                        HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> map = supplierService.getBeginNeedByOrganId(organId);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 导入供应商
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importVendor")
    @ApiOperation(value = "导入供应商")
    public BaseResponseInfo importVendor(MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            supplierService.importVendor(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "导入失败";
        }
        return res;
    }

    /**
     * 导入客户
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importCustomer")
    @ApiOperation(value = "导入客户")
    public BaseResponseInfo importCustomer(MultipartFile file,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            supplierService.importCustomer(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "导入失败";
        }
        return res;
    }

    /**
     * 导入会员
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importMember")
    @ApiOperation(value = "导入会员")
    public BaseResponseInfo importMember(MultipartFile file,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            supplierService.importMember(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "导入失败";
        }
        return res;
    }

    /**
     * 生成excel表格
     * @param supplier
     * @param type
     * @param phonenum
     * @param telephone
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/exportExcel")
    public void exportExcel(@RequestParam(value = "supplier", required = false) String supplier,
                            @RequestParam("type") String type,
                            @RequestParam(value = "phonenum", required = false) String phonenum,
                            @RequestParam(value = "telephone", required = false) String telephone,
                            HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Supplier> dataList = supplierService.findByAll(supplier, type, phonenum, telephone);
            File file = supplierService.exportExcel(dataList, type);
            ExcelUtils.downloadExcel(file, file.getName(), response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 批量设置会员当前的预付款
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/batchSetAdvanceIn")
    @ApiOperation(value = "批量设置会员当前的预付款")
    public String batchSetAdvanceIn(@RequestBody JSONObject jsonObject,
                                    HttpServletRequest request)throws Exception {
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = supplierService.batchSetAdvanceIn(ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

}
