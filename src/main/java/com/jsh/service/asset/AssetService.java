package com.jsh.service.asset;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.asset.AssetIDAO;
import com.jsh.dao.basic.AssetNameIDAO;
import com.jsh.dao.basic.CategoryIDAO;
import com.jsh.dao.basic.SupplierIDAO;
import com.jsh.dao.basic.UserIDAO;
import com.jsh.model.po.Asset;
import com.jsh.model.po.Assetname;
import com.jsh.model.po.Category;
import com.jsh.model.po.Supplier;
import com.jsh.util.AssetConstants;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class AssetService extends BaseService<Asset> implements AssetIService {
    /**
     * 初始化加载所有系统基础数据
     */
    @SuppressWarnings({"rawtypes"})
    private static Map<String, List> mapData = new HashMap<String, List>();
    /**
     * 错误的表格数据
     */
    private static List<Asset> wrongData = new ArrayList<Asset>();
    private AssetIDAO assetDao;
    private AssetNameIDAO assetNameDao;
    private CategoryIDAO categoryDao;
    private SupplierIDAO supplierDao;
    private UserIDAO userDao;

    /**
     * 导出Excel表格
     */
    @Override
    public InputStream exmportExcel(String isAllPage, PageUtil<Asset> pageUtil) throws JshException {
        try {
            if ("currentPage".equals(isAllPage)) {
                assetDao.find(pageUtil);
            } else {
                pageUtil.setCurPage(0);
                pageUtil.setPageSize(0);
                assetDao.find(pageUtil);
            }

            //将OutputStream转化为InputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            putDataOnOutputStream(out, pageUtil.getPageList());
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出资产信息为excel表格异常", e);
            throw new JshException("export asset info to excel exception", e);
        }
    }

    @Override
    public InputStream importExcel(File assetFile, int isCheck) throws JshException {
        //全局变量--每次调用前需要清空数据
        mapData.clear();
        //1、加载系统基础数据
        loadSystemData();
        //2、解析文件成资产数据
        parseFile(assetFile);

        if (null != wrongData && wrongData.size() > 0) {
            //将OutputStream转化为InputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            putDataOnOutputStream(out, wrongData);
            return new ByteArrayInputStream(out.toByteArray());
        } else
            return null;
        //2、是否直接插入数据库中
//	    if(0 == isCheck)
//	        System.out.println("手动检查");
//	    else
//	        System.out.println("自动检查插入");
    }

    /**
     * 初始加载系统基础数据--导入过程中，不用频繁查询数据库内容，影响系统性能。
     *
     * @throws JshException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void loadSystemData() throws JshException {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            Map<String, Object> condition = pageUtil.getAdvSearch();
            condition.put("id_s_order", "desc");
            categoryDao.find(pageUtil);
            mapData.put("categoryList", pageUtil.getPageList());

            supplierDao.find(pageUtil);
            mapData.put("supplierList", pageUtil.getPageList());

            condition.put("isystem_n_eq", 1);
            condition.put("id_s_order", "desc");
            userDao.find(pageUtil);
            mapData.put("userList", pageUtil.getPageList());

            //清除搜索条件 防止对查询有影响
            condition.remove("isystem_n_eq");

            assetNameDao.find(pageUtil);
            mapData.put("assetnameList", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
        }
    }

    /**
     * 解析excel表格
     *
     * @param assetFile
     */
    @SuppressWarnings("unchecked")
    private void parseFile(File assetFile) {
        //每次调用前清空
        wrongData.clear();
        int totalRow = 0;
        try {
            //创建对Excel工作簿文件的引用  
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(assetFile));
            //创建对工作表的引用,获取第一个工作表的内容
            HSSFSheet sheet = workbook.getSheetAt(0);
            /**
             * =====================================
             * 1、此处要增加文件的验证，如果不是资产文件需要进行特殊的处理,13列
             * 2、文件内容为空处理
             * 3、如果是修改过的文件内容
             */
            Iterator<Row> itsheet = sheet.rowIterator();
            while (itsheet.hasNext()) {
                //获取当前行数据
                Row row = itsheet.next();
                //获取一行有多少单元格
//                System.out.println(row.getLastCellNum());

                //excel表格第几行数据 从1开始 0 是表头
                int rowNum = row.getRowNum();
                /**
                 * 表头跳过不读
                 */
                if (AssetConstants.BusinessForExcel.EXCEL_TABLE_HEAD == rowNum)
                    continue;

                //开始处理excel表格内容 --每行数据读取,同时统计总共行数
                totalRow++;

                //获取excel表格的每格数据内容
                Iterator<Cell> it = row.cellIterator();
                //资产子类型--添加了一些excel表格数据
                Asset asset = new Asset();
                //保存每个单元格错误类型
                Map<Integer, String> cellType = new HashMap<Integer, String>();

                //名称需要类型字段
                Assetname nameModel = null;
                //资产名称
                @SuppressWarnings("unused")
                String assetname = "";

                //资产类型
                String categoryStr = "";
                //设置列号
                asset.setRowLineNum(rowNum);

                Cell cell = null;
                //判断列号--从零开始
                int cellIndex = 0;
                while (it.hasNext()) {
                    //获取每个单元格对象
                    cell = it.next();
                    //获取列号
                    cellIndex = cell.getColumnIndex();
                    //设置此单元格为字符串类型
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    Log.infoFileSync("==================excel表格中第" + totalRow + "行的第 " + cellIndex + "列的值为" + cell.getStringCellValue());

                    //每行中数据顺序 资产名称-资产类型-单价-用户-购买时间-状态-位置-资产编号-序列号-有效日期-保修日期-供应商-标签-描述
                    switch (cellIndex) {
                        case AssetConstants.BusinessForExcel.EXCEL_ASSETNAME:
                            //资产名称是否存在
                            boolean isAssetnameExist = false;
                            //此处添加资产名称处理
                            String nameValue = cell.getStringCellValue();
                            if (null == nameValue || "".equals(nameValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产名称没有填写");
                                cellType.put(cellIndex, "wrong");
                                break;
                            }
                            assetname = nameValue;

                            List<Assetname> nameList = mapData.get("assetnameList");
                            for (Assetname name : nameList) {
                                //表示名称存在--直接进行保存，不需要判断类型字段
                                if (nameValue.equals(name.getAssetname())) {
                                    isAssetnameExist = true;
                                    //直接进行设置
                                    asset.setAssetname(name);
                                    break;
                                }
                            }
                            //名称不存在 重新创建
                            if (!isAssetnameExist) {
                                isAssetnameExist = false;
                                nameModel = new Assetname();
                                nameModel.setAssetname(nameValue);
                                nameModel.setIsconsumables((short) 0);
                                nameModel.setIsystem((short) 1);
                                nameModel.setDescription("");

                                asset.setAssetnameStr(nameValue);
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_CATEGORY:
                            //此处添加资产类型处理
                            //类型信息是否存在
                            boolean isCategoryExist = false;
                            String categoryValue = cell.getStringCellValue();
                            if ((null == categoryValue || "".equals(categoryValue)) && null != nameModel) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产名称没有指定类型");
                                cellType.put(cellIndex, "wrong");
                                break;
                            }
                            categoryStr = categoryValue;

                            List<Category> categoryList = mapData.get("categoryList");
                            for (Category category : categoryList) {
                                //表示新创建 --名称设置过 不需要再进行处理
                                if (category.getAssetname().equals(categoryValue) && null != nameModel) {
                                    isCategoryExist = true;
                                    nameModel.setCategory(category);
                                    asset.setAssetname(nameModel);
                                    assetNameDao.create(nameModel);
                                    break;
                                }
                            }
                            //重新创建
                            if (null != nameModel && !isCategoryExist) {
                                //首先创建类型信息
                                Category canew = new Category();
                                canew.setAssetname(categoryValue);
                                canew.setIsystem((short) 1);
                                canew.setDescription("");
                                categoryDao.create(canew);

                                nameModel.setCategory(canew);

                                assetNameDao.create(nameModel);

                                asset.setAssetname(nameModel);
                            }
                            //nameModel为空表示 已经处理过类型信息 --此处不需要进行处理
                            else {
                                asset.setCategory(categoryStr);
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_PRICE:
                            //此处添加单价处理
                            String priceValue = cell.getStringCellValue();
                            //String priceValue = getCellFormatValue(cell);
                            if (null == priceValue || "".equals(priceValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写单价");
                                break;
                            }
                            //解析价格
                            if (Tools.checkStrIsNum(priceValue))
                                asset.setPrice(Double.parseDouble(priceValue));
                            else {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>>资产价格不是数字格式");
                                cellType.put(cellIndex, "wrong");
                                asset.setPrice(0.00d);
                                asset.setPriceStr(priceValue);
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_USER:
                            //此处添加用户处理--用户信息不需要进行处理
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_PURCHASE_DATE:
                            //此处添加购买时间处理--时间不需要处理
                            String purchaseValue = cell.getStringCellValue();
                            if (null == purchaseValue || "".equals(purchaseValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写购买日期");
                                break;
                            }
                            try {
                                asset.setPurchasedate(new Timestamp(Tools.parse(purchaseValue, "yyyy-MM-dd").getTime()));
                            } catch (ParseException e) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>>解析购买日期异常", e);
                                try {
                                    asset.setPurchasedate(new Timestamp(DateUtil.getJavaDate(Double.parseDouble(purchaseValue)).getTime()));
                                } catch (Exception t) {
                                    asset.setPurchasedateStr(purchaseValue);
                                    cellType.put(cellIndex, "wrong");
                                }
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_STATUS:
                            //此处添加状态处理--默认为在库状态
                            asset.setStatus((short) 0);
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_LOCATION:
                            //此处添加位置处理--不需要进行处理
                            String locationValue = cell.getStringCellValue();
                            if (null == locationValue || "".equals(locationValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写位置信息");
                                break;
                            }
                            asset.setLocation(locationValue);
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_NUM:
                            //此处添加资产编号处理
                            String assetnumValue = cell.getStringCellValue();
                            if (null == assetnumValue || "".equals(assetnumValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写资产编号");
                                break;
                            }
                            //设置资产编号
                            asset.setAssetnum(assetnumValue);
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_SERIALNO:
                            //此处添加序列号处理
                            String assetseriValue = cell.getStringCellValue();
                            if (null == assetseriValue || "".equals(assetseriValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写序列号");
                                break;
                            }
                            //设置资产编号
                            asset.setSerialnum(assetseriValue);
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_EXPIRATION_DATE:
                            //此处添加有效日期处理--不需要处理
                            String expirationValue = cell.getStringCellValue();
                            if (null == expirationValue || "".equals(expirationValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有有效日期");
                                break;
                            }

                            try {
                                asset.setPeriodofvalidity(new Timestamp(Tools.parse(expirationValue, "yyyy-MM-dd").getTime()));
                            } catch (ParseException e) {
                                try {
                                    asset.setPeriodofvalidity(new Timestamp(DateUtil.getJavaDate(Double.parseDouble(expirationValue)).getTime()));
                                } catch (Exception t) {
                                    Log.errorFileSync(">>>>>>>>>>>>>>>>>解析有效日期异常", t);
                                    asset.setPeriodofvalidityStr(expirationValue);
                                    cellType.put(cellIndex, "wrong");
                                }
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_WARRANTY_DATE:
                            //此处添加保修日期处理--不需要处理
                            String warrantyValue = cell.getStringCellValue();
                            if (null == warrantyValue || "".equals(warrantyValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有保修日期");
                                break;
                            }
                            try {
                                asset.setWarrantydate(new Timestamp(Tools.parse(warrantyValue, "yyyy-MM-dd").getTime()));
                            } catch (ParseException e) {
                                try {
                                    asset.setWarrantydate(new Timestamp(DateUtil.getJavaDate(Double.parseDouble(warrantyValue)).getTime()));
                                } catch (Exception t) {
                                    Log.errorFileSync(">>>>>>>>>>>>>>>>>解析保修日期异常", t);
                                    asset.setWarrantydateStr(warrantyValue);
                                    cellType.put(cellIndex, "wrong");
                                }
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_SUPPLIER:
                            //此处添加供应商处理

                            String supplierValue = cell.getStringCellValue();
                            if (null == supplierValue || "".equals(supplierValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写供应商");
                                cellType.put(cellIndex, "wrong");
                                break;
                            }
                            //供应商
                            List<Supplier> supplierList = mapData.get("supplierList");
                            boolean isSupplerExist = false;
                            for (Supplier supplier : supplierList) {
                                if (supplierValue.equals(supplier.getSupplier())) {
                                    isSupplerExist = true;
                                    asset.setSupplier(supplier);
                                    break;
                                }
                            }
                            if (!isSupplerExist) {
                                Supplier sup = new Supplier();
                                sup.setIsystem((short) 1);
                                sup.setSupplier(supplierValue);
                                sup.setDescription("");
                                supplierDao.create(sup);
                                //保存供应商信息
                                asset.setSupplier(sup);
                            }
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_LABLE:
                            //此处添加标签处理
                            String lableValue = cell.getStringCellValue();
                            if (null == lableValue || "".equals(lableValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写标签信息");
                                break;
                            }
                            asset.setLabels(lableValue);
                            break;
                        case AssetConstants.BusinessForExcel.EXCEL_DESC:
                            //此处添加描述信息处理
                            String descValue = cell.getStringCellValue();
                            if (null == descValue || "".equals(descValue)) {
                                Log.errorFileSync(">>>>>>>>>>>>>>>>资产没有填写描述信息");
                                break;
                            }
                            asset.setDescription(descValue);
                            break;
                    }
                }
                asset.setCreatetime(new Timestamp(Calendar.getInstance().getTime().getTime()));
                asset.setUpdatetime(new Timestamp(Calendar.getInstance().getTime().getTime()));
                asset.setCellInfo(cellType);

                Log.infoFileSync(totalRow + "行总共有" + cellIndex + "列");
                //资产文件为13列，否则不是资产模板文件--不输入的时候 判断会有问题 暂时去掉
//                if(cellIndex != 13)
//                {
//                    Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>导入文件格式不合法，请重新选择文件进行操作！");
//                    return;
//                }

                //判断完成后增加数据
                if ((null != cellType && cellType.size() > 0)
                        || asset.getAssetname() == null || asset.getAssetname().getCategory() == null)
                    wrongData.add(asset);
                else {
                    if (null == asset.getStatus())
                        asset.setStatus((short) 0);
                    assetDao.save(asset);
                }
            }
        } catch (FileNotFoundException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>读取excel文件异常:找不到指定文件！", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>读取excel文件异常，请确认文件格式是否正确 ！", e);
        }
        Log.infoFileSync("===================excel表格总共有 " + totalRow + " 条记录!");
    }

    /**
     * 生成excel表格
     *
     * @param os
     */
    private void putDataOnOutputStream(OutputStream os, List<Asset> dataList) {
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet("资产详细信息", 0);
            //增加列头
            int[] colunmWidth = {30, 30, 10, 15, 20, 10, 30, 30, 30, 20, 20, 20, 30, 80};
            String[] colunmName = {"资产名称", "资产类型", "单价", "用户", "购买时间", "状态", "位置", "资产编号", "序列号", "有效日期", "保修日期", "供应商", "标签", "描述"};
            for (int i = 0; i < colunmWidth.length; i++) {
                sheet.setColumnView(i, colunmWidth[i]);
                sheet.addCell(new Label(i, 0, colunmName[i]));
            }

            if (null != dataList && dataList.size() > 0) {
                int i = 1;
                for (Asset asset : dataList) {
                    int j = 0;
                    Map<Integer, String> cellInfo = asset.getCellInfo();

                    //第一列,填充 数据, Label(列,行,值)
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getAssetname() == null ? "" : asset.getAssetname().getAssetname(), asset));
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getAssetname() == null || asset.getAssetname().getCategory() == null ? "" : asset.getAssetname().getCategory().getAssetname(), asset));
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getPrice() == null ? "" : asset.getPrice().toString(), asset));
                    sheet.addCell(new Label(j++, i, asset.getUser() == null ? "" : asset.getUser().getUsername()));
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getPurchasedate() == null ? "" : Tools.getCurrentMonth(asset.getPurchasedate()), asset));
                    Short status = asset.getStatus();
                    if (null == status)
                        status = 0;
                    if (AssetConstants.BusinessForExcel.EXCEl_STATUS_ZAIKU == status)
                        sheet.addCell(new Label(j++, i, "在库"));
                    else if (AssetConstants.BusinessForExcel.EXCEl_STATUS_INUSE == status)
                        sheet.addCell(new Label(j++, i, "在用"));
                    else if (AssetConstants.BusinessForExcel.EXCEl_STATUS_CONSUME == status)
                        sheet.addCell(new Label(j++, i, "消费"));
                    sheet.addCell(new Label(j++, i, asset.getLocation()));
                    sheet.addCell(new Label(j++, i, asset.getAssetnum()));
                    sheet.addCell(new Label(j++, i, asset.getSerialnum()));
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getPeriodofvalidity() == null ? "" : Tools.getCurrentMonth(asset.getPeriodofvalidity()), asset));
                    sheet.addCell(getLabelInfo(cellInfo, j++, i, asset.getWarrantydate() == null ? "" : Tools.getCurrentMonth(asset.getWarrantydate()), asset));
                    sheet.addCell(new Label(j++, i, asset.getSupplier() == null ? "" : asset.getSupplier().getSupplier()));
                    sheet.addCell(new Label(j++, i, asset.getLabels()));
                    sheet.addCell(new Label(j++, i, asset.getDescription()));

                    i++;
                }
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出资产信息为excel表格异常", e);
        }
    }

    /**
     * 根据错误信息进行提示--execel表格背景设置为红色，表示导入信息有误
     *
     * @param cellInfo
     * @param cellNum
     * @param columnNum
     * @param value
     * @return
     */
    private Label getLabelInfo(Map<Integer, String> cellInfo, int cellNum, int columnNum, String value, Asset asset) {
        Label label = null;

        //设置背景颜色
        WritableCellFormat cellFormat = new WritableCellFormat();
        try {
            cellFormat.setBackground(Colour.RED);
        } catch (WriteException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>设置单元格背景颜色错误", e);
        }

        if (null == cellInfo || cellInfo.size() == 0) {
            if (cellNum == AssetConstants.BusinessForExcel.EXCEL_ASSETNAME) {
                if (null == asset.getAssetname() && null != asset.getAssetnameStr())
                    label = new Label(cellNum, columnNum, asset.getAssetnameStr());
                else if (null != asset.getAssetname())
                    label = new Label(cellNum, columnNum, value);
                else
                    label = new Label(cellNum, columnNum, null, cellFormat);
            } else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_CATEGORY) {
                if (null != asset.getAssetnameStr() && null == asset.getAssetname())
                    label = new Label(cellNum, columnNum, null, cellFormat);
                else if (null == asset.getAssetnameStr() && null == asset.getAssetname()
                        && asset.getCategory() != null && asset.getCategory().length() > 0)
                    label = new Label(cellNum, columnNum, asset.getCategory());
                else
                    label = new Label(cellNum, columnNum, value);
            } else
                label = new Label(cellNum, columnNum, value);
        } else {
            //表示此单元格有错误
            if (cellInfo.containsKey(cellNum)) {
                if (cellNum == AssetConstants.BusinessForExcel.EXCEL_ASSETNAME) {
                    if (null == asset.getAssetname() && null != asset.getAssetnameStr())
                        label = new Label(cellNum, columnNum, asset.getAssetnameStr());
                    if (null != asset.getAssetname())
                        label = new Label(cellNum, columnNum, asset.getAssetname().getAssetname());
                    else
                        label = new Label(cellNum, columnNum, value, cellFormat);
                } else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_CATEGORY) {
                    if (null != asset.getAssetnameStr() && null == asset.getAssetname())
                        label = new Label(cellNum, columnNum, null, cellFormat);
                    else if (null == asset.getAssetnameStr() && null == asset.getAssetname()
                            && asset.getCategory() != null && asset.getCategory().length() > 0)
                        label = new Label(cellNum, columnNum, asset.getCategory());
                } else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_PRICE)
                    label = new Label(cellNum, columnNum, asset.getPriceStr(), cellFormat);
                else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_PURCHASE_DATE)
                    label = new Label(cellNum, columnNum, asset.getPurchasedateStr(), cellFormat);
                else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_WARRANTY_DATE)
                    label = new Label(cellNum, columnNum, asset.getWarrantydateStr(), cellFormat);
                else if (cellNum == AssetConstants.BusinessForExcel.EXCEL_EXPIRATION_DATE)
                    label = new Label(cellNum, columnNum, asset.getPeriodofvalidityStr(), cellFormat);
                else
                    label = new Label(cellNum, columnNum, value, cellFormat);
            } else {
                if (null == asset.getAssetname() && null != asset.getAssetnameStr() && cellNum == 0)
                    label = new Label(cellNum, columnNum, asset.getAssetnameStr());
                else if (null == asset.getAssetnameStr() && null == asset.getAssetname()
                        && asset.getCategory() != null && asset.getCategory().length() > 0 && cellNum == 1)
                    label = new Label(cellNum, columnNum, asset.getCategory());
                else
                    label = new Label(cellNum, columnNum, value);
            }
        }
        return label;
    }

    /*=====================以下处理与业务无关的共用方法=================================*/
    public void setAssetDao(AssetIDAO assetDao) {
        this.assetDao = assetDao;
    }

    public void setAssetNameDao(AssetNameIDAO assetNameDao) {
        this.assetNameDao = assetNameDao;
    }

    public void setCategoryDao(CategoryIDAO categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setSupplierDao(SupplierIDAO supplierDao) {
        this.supplierDao = supplierDao;
    }

    public void setUserDao(UserIDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    protected Class<Asset> getEntityClass() {
        return Asset.class;
    }
}
