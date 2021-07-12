package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua 华夏erp
 */
@RestController
@RequestMapping(value = "/supplier")
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

    /**
     * 更新供应商-只更新预付款，其余用原来的值
     * @param supplierId
     * @param advanceIn
     * @param request
     * @return
     */
    @PostMapping(value = "/updateAdvanceIn")
    public String updateAdvanceIn(@RequestParam("supplierId") Long supplierId,
                                            @RequestParam("advanceIn") BigDecimal advanceIn,
                                            HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = supplierService.updateAdvanceIn(supplierId, advanceIn);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 查找客户信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_cus")
    public JSONArray findBySelectCus(HttpServletRequest request) {
        JSONArray arr = new JSONArray();
        try {
            String type = "UserCustomer";
            Long userId = userService.getUserId(request);
            List<Supplier> supplierList = supplierService.findBySelectCus();
            JSONArray dataArray = new JSONArray();
            if (null != supplierList) {
                boolean customerFlag = systemConfigService.getCustomerFlag();
                for (Supplier supplier : supplierList) {
                    JSONObject item = new JSONObject();
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, userId.toString(), "[" + supplier.getId().toString() + "]");
                    } catch (DataAccessException e) {
                        logger.error(">>>>>>>>>>>>>>>>>查询用户对应的客户：存在异常！");
                    }
                    if (!customerFlag || flag) {
                        item.put("id", supplier.getId());
                        item.put("supplier", supplier.getSupplier()); //客户名称
                        dataArray.add(item);
                    }
                }
            }
            arr = dataArray;
        } catch(Exception e){
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 查找供应商信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_sup")
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
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 查找会员信息-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_retail")
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
            e.printStackTrace();
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
    public JSONArray findUserCustomer(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                   HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
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
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + supplier.getId().toString() + "]");
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>>>设置用户对应的客户：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    //结束
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            arr.add(outer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
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
    public void exportExcel(@RequestParam("supplier") String supplier,
                                        @RequestParam("type") String type,
                                        @RequestParam("phonenum") String phonenum,
                                        @RequestParam("telephone") String telephone,
                                        HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Supplier> dataList = supplierService.findByAll(supplier, type, phonenum, telephone);
            String[] names = {"名称", "类型", "联系人", "电话", "电子邮箱", "预收款", "期初应收", "期初应付", "备注", "传真", "手机", "地址", "纳税人识别号", "开户行", "账号", "税率", "状态"};
            String title = "信息报表";
            List<String[]> objects = new ArrayList<String[]>();
            if (null != dataList) {
                for (Supplier s : dataList) {
                    String[] objs = new String[17];
                    objs[0] = s.getSupplier();
                    objs[1] = s.getType();
                    objs[2] = s.getContacts();
                    objs[3] = s.getPhoneNum();
                    objs[4] = s.getEmail();
                    objs[5] = s.getAdvanceIn() == null? "" : s.getAdvanceIn().toString();
                    objs[6] = s.getBeginNeedGet() == null? "" : s.getBeginNeedGet().toString();
                    objs[7] = s.getBeginNeedPay() == null? "" : s.getBeginNeedPay().toString();
                    objs[8] = s.getDescription();
                    objs[9] = s.getFax();
                    objs[10] = s.getTelephone();
                    objs[11] = s.getAddress();
                    objs[12] = s.getTaxNum();
                    objs[13] = s.getBankName();
                    objs[14] = s.getAccountNumber();
                    objs[15] = s.getTaxRate() == null? "" : s.getTaxRate().toString();
                    objs[16] = s.getEnabled() ? "启用" : "禁用";
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
     * 导入excel表格
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importExcel")
    public BaseResponseInfo importExcel(MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            importFun(file);
            res.code = 200;
            res.data = "导入成功";
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "导入失败";
        }
        return res;
    }

    public String importFun(MultipartFile file)throws Exception{
        BaseResponseInfo info = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        String message = "成功";
        try {
            Sheet src = null;
            //文件合法性校验
            try {
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                src = workbook.getSheet(0);
            } catch (Exception e) {
                message = "导入文件不合法，请检查";
                data.put("message", message);
                info.code = 400;
                info.data = data;
            }
            //每行中数据顺序 "名称","类型","联系人","电话","电子邮箱","预收款","期初应收","期初应付","备注","传真","手机","地址","纳税人识别号","开户行","账号","税率","状态"
            List<Supplier> sList = new ArrayList<Supplier>();
            for (int i = 1; i < src.getRows(); i++) {
                Supplier s = new Supplier();
                s.setSupplier(ExcelUtils.getContent(src, i, 0));
                s.setType(ExcelUtils.getContent(src, i, 1));
                s.setContacts(ExcelUtils.getContent(src, i, 2));
                s.setPhoneNum(ExcelUtils.getContent(src, i, 3));
                s.setEmail(ExcelUtils.getContent(src, i, 4));
                s.setAdvanceIn(parseBigDecimalEx(ExcelUtils.getContent(src, i, 5)));
                s.setBeginNeedGet(parseBigDecimalEx(ExcelUtils.getContent(src, i, 6)));
                s.setBeginNeedPay(parseBigDecimalEx(ExcelUtils.getContent(src, i, 7)));
                s.setDescription(ExcelUtils.getContent(src, i, 8));
                s.setFax(ExcelUtils.getContent(src, i, 9));
                s.setTelephone(ExcelUtils.getContent(src, i, 10));
                s.setAddress(ExcelUtils.getContent(src, i, 11));
                s.setTaxNum(ExcelUtils.getContent(src, i, 12));
                s.setBankName(ExcelUtils.getContent(src, i, 13));
                s.setAccountNumber(ExcelUtils.getContent(src, i, 14));
                s.setTaxRate(parseBigDecimalEx(ExcelUtils.getContent(src, i, 15)));
                String enabled = ExcelUtils.getContent(src, i, 16);
                s.setEnabled(enabled.equals("启用")? true: false);
                s.setIsystem(Byte.parseByte("1"));
                sList.add(s);
            }
            info = supplierService.importExcel(sList);
        } catch (Exception e) {
            e.printStackTrace();
            message = "导入失败";
            info.code = 500;
            data.put("message", message);
            info.data = data;
        }
        return null;
    }

    public BigDecimal parseBigDecimalEx(String str)throws Exception{
        if(!StringUtil.isEmpty(str)) {
            return new BigDecimal(str);
        } else {
            return null;
        }
    }
}
