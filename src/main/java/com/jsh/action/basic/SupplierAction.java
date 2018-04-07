package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.basic.SupplierModel;
import com.jsh.service.basic.SupplierIService;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SupplierConstants;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 单位管理
 * @author ji-sheng-hua  qq:7 5 2 7 1 8 9 2 0
 */
@SuppressWarnings("serial")
public class SupplierAction extends BaseAction<SupplierModel> {
    public static final String EXCEL = "excel";  //action返回excel结果
    private final static Integer ISYSTEM = 1;
    private SupplierIService supplierService;
    private UserBusinessIService userBusinessService;
    private SupplierModel model = new SupplierModel();

    /**
     * 增加供应商
     *
     * @return
     */
    public void create() {
        Log.infoFileSync("==================开始调用增加供应商方法===================");
        Boolean flag = false;
        try {
            Supplier supplier = new Supplier();
            supplier.setContacts(model.getContacts());
            supplier.setType(model.getType());
            supplier.setDescription(model.getDescription());
            supplier.setEmail(model.getEmail());
            supplier.setAdvanceIn(0.0);
            supplier.setBeginNeedGet(model.getBeginNeedGet());
            supplier.setBeginNeedPay(model.getBeginNeedPay());
            supplier.setIsystem((short) 1);
            supplier.setEnabled(true);
            supplier.setPhonenum(model.getPhonenum());
            supplier.setSupplier(model.getSupplier());

            supplier.setFax(model.getFax());
            supplier.setTelephone(model.getTelephone());
            supplier.setAddress(model.getAddress());
            supplier.setTaxNum(model.getTaxNum());
            supplier.setBankName(model.getBankName());
            supplier.setAccountNumber(model.getAccountNumber());
            supplier.setTaxRate(model.getTaxRate());

            supplierService.create(supplier);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加供应商异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加供应商回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加供应商名称为  " + model.getSupplier() + " " + tipMsg + "！", "增加供应商" + tipMsg));
        Log.infoFileSync("==================结束调用增加供应商方法===================");
    }

    /**
     * 删除供应商
     *
     * @return
     */
    public String delete() {
        Log.infoFileSync("====================开始调用删除供应商信息方法delete()================");
        try {
            supplierService.delete(model.getSupplierID());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getSupplierID() + "  的供应商异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除供应商ID为  " + model.getSupplierID() + ",名称为  " + model.getSupplier() + tipMsg + "！", "删除供应商" + tipMsg));
        Log.infoFileSync("====================结束调用删除供应商信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 更新供应商
     *
     * @return
     */
    public void update() {
        Boolean flag = false;
        try {
            Supplier supplier = supplierService.get(model.getSupplierID());
            supplier.setContacts(model.getContacts());
            supplier.setType(model.getType());
            supplier.setDescription(model.getDescription());
            supplier.setEmail(model.getEmail());
            supplier.setAdvanceIn(supplier.getAdvanceIn());
            supplier.setBeginNeedGet(model.getBeginNeedGet());
            supplier.setBeginNeedPay(model.getBeginNeedPay());
            supplier.setIsystem((short) 1);
            supplier.setPhonenum(model.getPhonenum());
            supplier.setSupplier(model.getSupplier());

            supplier.setFax(model.getFax());
            supplier.setTelephone(model.getTelephone());
            supplier.setAddress(model.getAddress());
            supplier.setTaxNum(model.getTaxNum());
            supplier.setBankName(model.getBankName());
            supplier.setAccountNumber(model.getAccountNumber());
            supplier.setTaxRate(model.getTaxRate());

            supplier.setEnabled(supplier.getEnabled());
            supplierService.update(supplier);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改供应商ID为 ： " + model.getSupplierID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改供应商回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新供应商ID为  " + model.getSupplierID() + " " + tipMsg + "！", "更新供应商" + tipMsg));
    }

    /**
     * 更新供应商-只更新预付款，其余用原来的值
     *
     * @return
     */
    public void updateAdvanceIn() {
        Boolean flag = false;
        try {
            Supplier supplier = supplierService.get(model.getSupplierID());
            supplier.setContacts(supplier.getContacts());
            supplier.setType(supplier.getType());
            supplier.setDescription(supplier.getDescription());
            supplier.setEmail(supplier.getEmail());
            supplier.setAdvanceIn(supplier.getAdvanceIn() + model.getAdvanceIn()); //增加预收款的金额，可能增加的是负值
            supplier.setBeginNeedGet(supplier.getBeginNeedGet());
            supplier.setBeginNeedPay(supplier.getBeginNeedPay());
            supplier.setIsystem((short) 1);
            supplier.setPhonenum(supplier.getPhonenum());
            supplier.setSupplier(supplier.getSupplier());

            supplier.setFax(supplier.getFax());
            supplier.setTelephone(supplier.getTelephone());
            supplier.setAddress(supplier.getAddress());
            supplier.setTaxNum(supplier.getTaxNum());
            supplier.setBankName(supplier.getBankName());
            supplier.setAccountNumber(supplier.getAccountNumber());
            supplier.setTaxRate(supplier.getTaxRate());

            supplier.setEnabled(supplier.getEnabled());
            supplierService.update(supplier);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改供应商ID为 ： " + model.getSupplierID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改供应商回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新供应商预付款", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新供应商ID为  " + model.getSupplierID() + " " + tipMsg + "！", "更新供应商" + tipMsg));
    }

    /**
     * 批量删除指定ID供应商
     *
     * @return
     */
    public String batchDelete() {
        try {
            supplierService.batchDelete(model.getSupplierIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除供应商ID为：" + model.getSupplierIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除供应商ID为  " + model.getSupplierIDs() + " " + tipMsg + "！", "批量删除供应商" + tipMsg));
        return SUCCESS;
    }

    /**
     * 批量设置状态-启用或者禁用
     *
     * @return
     */
    public String batchSetEnable() {
        try {
            supplierService.batchSetEnable(model.getEnabled(), model.getSupplierIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，单位ID为：" + model.getSupplierIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量修改单位状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改状态，单位ID为  " + model.getSupplierIDs() + " " + tipMsg + "！", "批量修改单位状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 检查输入名称是否存在
     */
    public void checkIsNameExist() {
        Boolean flag = false;
        try {
            flag = supplierService.checkIsNameExist("supplier", model.getSupplier(), "id", model.getSupplierID());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查供应商名称为：" + model.getSupplier() + " ID为： " + model.getSupplierID() + " 是否存在异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查供应商名称为：" + model.getSupplier() + " ID为： " + model.getSupplierID() + " 是否存在异常！", e);
            }
        }
    }

    /**
     * 查找供应商信息
     *
     * @return
     */
    public void findBy() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            supplierService.find(pageUtil);
            String sName = "";
            if ((model.getType()).equals("供应商")) {
                sName = "pageUtilVendor";
            } else if ((model.getType()).equals("客户")) {
                sName = "pageUtilCustomer";
            } else if ((model.getType()).equals("会员")) {
                sName = "pageUtilMember";
            }
            getSession().put(sName, pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //供应商名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("type", supplier.getType());
                    item.put("contacts", supplier.getContacts());
                    item.put("phonenum", supplier.getPhonenum());
                    item.put("email", supplier.getEmail());
                    item.put("AdvanceIn", supplier.getAdvanceIn());
                    item.put("BeginNeedGet", supplier.getBeginNeedGet());
                    item.put("BeginNeedPay", supplier.getBeginNeedPay());
                    item.put("isystem", supplier.getIsystem() == (short) 0 ? "是" : "否");
                    item.put("description", supplier.getDescription());

                    item.put("fax", supplier.getFax());
                    item.put("telephone", supplier.getTelephone());
                    item.put("address", supplier.getAddress());
                    item.put("taxNum", supplier.getTaxNum());
                    item.put("bankName", supplier.getBankName());
                    item.put("accountNumber", supplier.getAccountNumber());
                    item.put("taxRate", supplier.getTaxRate());

                    item.put("enabled", supplier.getEnabled());
                    item.put("op", supplier.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
    }

    /**
     * 根据id查找信息
     *
     * @return
     */
    public void findById() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionById());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("type", supplier.getType());
                    item.put("contacts", supplier.getContacts());
                    item.put("phonenum", supplier.getPhonenum());
                    item.put("email", supplier.getEmail());
                    item.put("AdvanceIn", supplier.getAdvanceIn());
                    item.put("BeginNeedGet", supplier.getBeginNeedGet());
                    item.put("BeginNeedPay", supplier.getBeginNeedPay());
                    item.put("isystem", supplier.getIsystem() == (short) 0 ? "是" : "否");
                    item.put("description", supplier.getDescription());

                    item.put("fax", supplier.getFax());
                    item.put("telephone", supplier.getTelephone());
                    item.put("address", supplier.getAddress());
                    item.put("taxNum", supplier.getTaxNum());
                    item.put("bankName", supplier.getBankName());
                    item.put("accountNumber", supplier.getAccountNumber());
                    item.put("taxRate", supplier.getTaxRate());

                    item.put("enabled", supplier.getEnabled());
                    item.put("op", supplier.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询信息结果异常", e);
        }
    }

    /**
     * 查找供应商信息-下拉框
     *
     * @return
     */
    public void findBySelect_sup() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_sup());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //供应商名称
                    item.put("supplier", supplier.getSupplier());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
    }

    /**
     * 查找客户信息-下拉框
     *
     * @return
     */
    public void findBySelect_cus() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_cus());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist("Type", model.getUBType(), "KeyId", model.getUBKeyId(), "Value", "[" + supplier.getId().toString() + "]");
                    } catch (DataAccessException e) {
                        Log.errorFileSync(">>>>>>>>>>>>>>>>>查询用户对应的客户：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("id", supplier.getId());
                        item.put("supplier", supplier.getSupplier()); //客户名称
                        dataArray.add(item);
                    }
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
    }

    /**
     * 查找会员信息-下拉框
     *
     * @return
     */
    public void findBySelect_retail() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_retail());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //客户名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("advanceIn", supplier.getAdvanceIn()); //预付款金额
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
    }

    /**
     * 查找非会员的id
     */
    public void findBySelectRetailNoPeople() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_retail_no_people());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //客户名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("advanceIn", supplier.getAdvanceIn()); //预付款金额
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
    }

    /**
     * 用户对应客户显示
     *
     * @return
     */
    public void findUserCustomer() {
        try {
            PageUtil<Supplier> pageUtil = new PageUtil<Supplier>();
            pageUtil.setPageSize(500);

            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("type_s_eq", "客户");
            condition.put("id_s_order", "desc");

            pageUtil.setAdvSearch(condition);
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();

            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("text", "客户列表");
            outer.put("state", "open");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Supplier supplier : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    item.put("text", supplier.getSupplier());
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist("Type", model.getUBType(), "KeyId", model.getUBKeyId(), "Value", "[" + supplier.getId().toString() + "]");
                    } catch (DataAccessException e) {
                        Log.errorFileSync(">>>>>>>>>>>>>>>>>设置用户对应的客户：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    //结束
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            //回写查询结果
            toClient("[" + outer.toString() + "]");
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找客户异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询客户结果异常", e);
        }
    }

    public String importFun() {
        //excel表格file
        Boolean result = false;
        String returnStr = "";
        try {
            InputStream in = supplierService.importExcel(model.getSupplierFile());

            if (null != in) {
                model.setFileName(Tools.getRandomChar() + Tools.getNow2(Calendar.getInstance().getTime()) + "_wrong.xls");
                model.setExcelStream(in);
                returnStr = SupplierConstants.BusinessForExcel.EXCEL;
            } else {
                result = true;
                try {
                    toClient(result.toString());
                } catch (IOException e) {
                    Log.errorFileSync(">>>>>>>>>回写导入信息结果异常", e);
                }
                //导入数据成功
                returnStr = SUCCESS;
            }

        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>导入excel表格信息异常", e);
        }
        return returnStr;
    }

    /**
     * 导入excel表格-供应商
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importExcelVendor() {
        return importFun();
    }

    /**
     * 导入excel表格-客户
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importExcelCustomer() {
        return importFun();
    }

    /**
     * 导入excel表格-会员
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importExcelMember() {
        return importFun();
    }


    /**
     * 导出excel表格
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String exportExcel() {
        Log.infoFileSync("===================调用导出信息action方法exportExcel开始=======================");
        try {
            String sName = "pageUtil" + model.getType();
            PageUtil<Supplier> pageUtil = (PageUtil<Supplier>) getSession().get(sName);

            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            String isCurrentPage = "allPage";
            model.setFileName(Tools.changeUnicode("report" + System.currentTimeMillis() + ".xls", model.getBrowserType()));
            model.setExcelStream(supplierService.exmportExcel(isCurrentPage, pageUtil));
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出信息action方法exportExcel异常", e);
            model.getShowModel().setMsgTip("export excel exception");
        }
        Log.infoFileSync("===================调用导出信息action方法exportExcel结束==================");
        return EXCEL;
    }

    /**
     * 拼接搜索条件
     *
     * @return
     */
    private Map<String, Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("supplier_s_like", model.getSupplier());
        condition.put("type_s_like", model.getType());
        condition.put("phonenum_s_like", model.getPhonenum());
        condition.put("telephone_s_like", model.getTelephone());
        condition.put("description_s_like", model.getDescription());
        condition.put("isystem_n_eq", ISYSTEM);
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 搜索条件
     */
    private Map<String, Object> getConditionById() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("Id_n_eq", model.getSupplierID());
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-供应商
     *
     * @return
     */
    private Map<String, Object> getCondition_Select_sup() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("type_s_like", "供应商");
        condition.put("enabled_s_eq", 1);
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-客户
     *
     * @return
     */
    private Map<String, Object> getCondition_Select_cus() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("type_s_like", "客户");
        condition.put("enabled_s_eq", 1);
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-会员
     *
     * @return
     */
    private Map<String, Object> getCondition_Select_retail() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("type_s_like", "会员");
        condition.put("enabled_s_eq", 1);
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-非会员
     *
     * @return
     */
    private Map<String, Object> getCondition_Select_retail_no_people() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("type_s_like", "会员");
        condition.put("isystem_n_eq", 0);
        condition.put("id_s_order", "desc");
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public SupplierModel getModel() {
        return model;
    }

    public void setSupplierService(SupplierIService supplierService) {
        this.supplierService = supplierService;
    }

    public void setUserBusinessService(UserBusinessIService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }
}
