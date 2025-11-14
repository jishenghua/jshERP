package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.vo.SupplierSimple;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.SupplierService;
import com.jsh.erp.service.SystemConfigService;
import com.jsh.erp.service.UserService;
import com.jsh.erp.service.UserBusinessService;
import com.jsh.erp.utils.*;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * @author ji|sheng|hua 管伊佳erp
 */
@RestController
@RequestMapping(value = "/supplier")
@Api(tags = {"商家管理"})
public class SupplierController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    private SupplierService supplierService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    @Resource
    private UserService userService;

    @GetMapping(value = "/info")
    @ApiOperation(value = "根据id获取信息")
    public String getList(@RequestParam("id") Long id,
                          HttpServletRequest request) throws Exception {
        Supplier supplier = supplierService.getSupplier(id);
        Map<String, Object> objectMap = new HashMap<>();
        if(supplier != null) {
            objectMap.put("info", supplier);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取信息列表")
    public TableDataInfo getList(@RequestParam(value = Constants.SEARCH, required = false) String search,
                                 HttpServletRequest request)throws Exception {
        String supplier = StringUtil.getInfo(search, "supplier");
        String type = StringUtil.getInfo(search, "type");
        String contacts = StringUtil.getInfo(search, "contacts");
        String phonenum = StringUtil.getInfo(search, "phonenum");
        String telephone = StringUtil.getInfo(search, "telephone");
        List<Supplier> list = supplierService.select(supplier, type, contacts, phonenum, telephone);
        return getDataTable(list);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增")
    public String addResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int insert = supplierService.insertSupplier(obj, request);
        return returnStr(objectMap, insert);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改")
    public String updateResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int update = supplierService.updateSupplier(obj, request);
        return returnStr(objectMap, update);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = supplierService.deleteSupplier(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = supplierService.batchDeleteSupplier(ids, request);
        return returnStr(objectMap, delete);
    }

    @GetMapping(value = "/checkIsNameExist")
    @ApiOperation(value = "检查名称是否存在")
    public String checkIsNameExist(@RequestParam Long id, @RequestParam(value ="name", required = false) String name,
                                   HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int exist = supplierService.checkIsNameExist(id, name);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

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
    public JSONArray findBySelectCus(@RequestBody JSONObject jsonObject,
                                     HttpServletRequest request) {
        JSONArray arr = new JSONArray();
        try {
            String key = jsonObject.get("key")!=null ? jsonObject.getString("key") : null;
            Long organId = jsonObject.get("organId")!=null ? jsonObject.getLong("organId") : null;
            Integer limit = jsonObject.get("limit")!=null ? jsonObject.getInteger("limit") : null;
            String type = "UserCustomer";
            Long userId = userService.getUserId(request);
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, userId.toString());
            List<Supplier> supplierList = supplierService.findBySelectCus(key, organId, limit);
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
    public JSONArray findBySelectSup(@RequestBody JSONObject jsonObject,
                                     HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            String key = jsonObject.get("key")!=null ? jsonObject.getString("key") : null;
            Long organId = jsonObject.get("organId")!=null ? jsonObject.getLong("organId") : null;
            Integer limit = jsonObject.get("limit")!=null ? jsonObject.getInteger("limit") : null;
            List<Supplier> supplierList = supplierService.findBySelectSup(key, organId, limit);
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
    public JSONArray findBySelectOrgan(@RequestBody JSONObject jsonObject,
                                       HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            String key = jsonObject.get("key")!=null ? jsonObject.getString("key") : null;
            Long organId = jsonObject.get("organId")!=null ? jsonObject.getLong("organId") : null;
            Integer limit = jsonObject.get("limit")!=null ? jsonObject.getInteger("limit") : null;
            JSONArray dataArray = new JSONArray();
            //1、获取供应商信息
            List<Supplier> supplierList = supplierService.findBySelectSup(key, organId, limit);
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
            List<Supplier> customerList = supplierService.findBySelectCus(key, organId, limit);
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
    public JSONArray findBySelectRetail(@RequestBody JSONObject jsonObject,
                                        HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            String key = jsonObject.get("key")!=null ? jsonObject.getString("key") : null;
            Long organId = jsonObject.get("organId")!=null ? jsonObject.getLong("organId") : null;
            Integer limit = jsonObject.get("limit")!=null ? jsonObject.getInteger("limit") : null;
            List<Supplier> supplierList = supplierService.findBySelectRetail(key, organId, limit);
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
     * 获取全部客户信息
     * @param search
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getAllCustomer")
    @ApiOperation(value = "获取全部客户信息")
    public TableDataInfo getAllCustomer(@RequestParam(value = Constants.SEARCH, required = false) String search,
                                        HttpServletRequest request)throws Exception {
        List<SupplierSimple> list = supplierService.getAllCustomer();
        return getDataTable(list);
    }

    /**
     * 获取用户对应客户的关系数组
     * @param type
     * @param keyId
     * @param request
     * @return
     */
    @GetMapping(value = "/getUserCustomerValue")
    @ApiOperation(value = "获取用户对应客户的关系数组")
    public JSONObject getUserCustomerValue(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                           HttpServletRequest request) throws Exception{
        JSONObject obj = new JSONObject();
        try {
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
            if(StringUtil.isNotEmpty(ubValue)) {
                String ubStr = ubValue.substring(1, ubValue.length()-1);
                String [] ubArr = ubStr.split("]\\[");
                Long[] ubLongArray = new Long[ubArr.length];
                for (int i = 0; i < ubArr.length; i++) {
                    ubLongArray[i] = Long.parseLong(ubArr[i]);
                }
                obj.put("data", ubLongArray);
            } else {
                obj.put("data", null);
            }
            obj.put("code", 200);
        } catch (Exception e) {
            obj.put("code", 500);
            obj.put("data", "服务内部错误");
            logger.error(e.getMessage(), e);
        }
        return obj;
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
            supplierService.checkFileExt(file);
            supplierService.importVendor(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(BusinessRunTimeException e) {
            res.code = e.getCode();
            res.data = e.getData().get("message");
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
            supplierService.checkFileExt(file);
            supplierService.importCustomer(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(BusinessRunTimeException e) {
            res.code = e.getCode();
            res.data = e.getData().get("message");
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
            supplierService.checkFileExt(file);
            supplierService.importMember(file, request);
            res.code = 200;
            res.data = "导入成功";
        } catch(BusinessRunTimeException e) {
            res.code = e.getCode();
            res.data = e.getData().get("message");
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

    @GetMapping(value = "/getInfoByName")
    @ApiOperation(value = "根据名称获取信息")
    public String getInfoByName(@RequestParam("name") String name,
                                @RequestParam("type") String type,
                                HttpServletRequest request) throws Exception {
        Supplier supplier = supplierService.getInfoByName(name, type);
        Map<String, Object> objectMap = new HashMap<>();
        if(supplier != null) {
            objectMap.put("info", supplier);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

}
