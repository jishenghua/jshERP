package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.basic.SupplierIDAO;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.Supplier;
import com.jsh.util.*;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.lang.Boolean;
import java.util.*;

public class SupplierService extends BaseService<Supplier> implements SupplierIService
{
	@SuppressWarnings("unused")
	private SupplierIDAO supplierDao;
	@SuppressWarnings("unused")
	private UserBusinessIDAO userBusinessDao;

	/**
	 * 设置映射基类
	 * @return
	 */
	@Override
	protected Class<Supplier> getEntityClass()
	{
		return Supplier.class;
	}
	
	public void setSupplierDao(SupplierIDAO supplierDao)
	{
		this.supplierDao = supplierDao;
	}

	public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
		this.userBusinessDao = userBusinessDao;
	}

	public void batchSetEnable(Boolean enable,String supplierIDs){
		supplierDao.batchSetEnable(enable, supplierIDs);
	}

	/**
	 * 初始化加载所有系统基础数据
	 */
	@SuppressWarnings({"rawtypes"})
	private static Map<String,List> mapData = new HashMap<String, List>();

	/**
	 * 错误的表格数据
	 */
	private static List<Supplier> wrongData = new ArrayList<Supplier>();
	/**
	 * 导出Excel表格
	 */
	@Override
	public InputStream exmportExcel(String isAllPage, PageUtil<Supplier> pageUtil)throws JshException
	{
		try
		{
			//将OutputStream转化为InputStream
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			putDataOnOutputStream(out, pageUtil.getPageList());
			return new ByteArrayInputStream(out.toByteArray());
		}
		catch (Exception e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出信息为excel表格异常", e);
			throw new JshException("导出信息为excel表格异常",e);
		}
	}

	/**
	 * 生成excel表格
	 * @param os
	 */
	@SuppressWarnings("deprecation")
	private void putDataOnOutputStream(OutputStream os, List<Supplier> dataList) {
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("信息报表", 0);
			//增加列头
			String[] colunmName = {"名称","类型","联系人","电话","电子邮箱","预收款","期初应收","期初应付","备注","传真","手机","地址","纳税人识别号","开户行","账号","税率","状态"};
			for(int i = 0 ;i < colunmName.length;i ++) {
				sheet.setColumnView(i, 10);
				sheet.addCell(new Label(i, 0, colunmName[i]));
			}
			if (null != dataList && dataList.size() > 0) {
				int i = 1;
				for (Supplier supplier: dataList){
					int j = 0;
					Map<Integer,String> cellInfo = supplier.getCellInfo();
					sheet.addCell(new Label(j++,i, supplier.getSupplier()));
					sheet.addCell(new Label(j++,i, supplier.getType()));
					sheet.addCell(new Label(j++,i, supplier.getContacts() == null ?"": supplier.getContacts()));
					sheet.addCell(new Label(j++,i, supplier.getPhonenum() == null ?"": supplier.getPhonenum()));
					sheet.addCell(new Label(j++,i, supplier.getEmail() == null ?"": supplier.getEmail()));
					sheet.addCell(getLabelInfo(cellInfo,j++,i, supplier.getAdvanceIn() == null ?"": supplier.getAdvanceIn().toString(),supplier));
					sheet.addCell(getLabelInfo(cellInfo,j++,i, supplier.getBeginNeedGet() == null ?"": supplier.getBeginNeedGet().toString(),supplier));
					sheet.addCell(getLabelInfo(cellInfo,j++,i, supplier.getBeginNeedPay() == null ?"": supplier.getBeginNeedPay().toString(),supplier));
					sheet.addCell(new Label(j++,i, supplier.getDescription() == null ?"": supplier.getDescription()));
					sheet.addCell(new Label(j++,i, supplier.getFax() == null ?"": supplier.getFax()));
					sheet.addCell(new Label(j++,i, supplier.getTelephone() == null ?"": supplier.getTelephone()));
					sheet.addCell(new Label(j++,i, supplier.getAddress() == null ?"": supplier.getAddress()));
					sheet.addCell(new Label(j++,i, supplier.getTaxNum() == null ?"": supplier.getTaxNum()));
					sheet.addCell(new Label(j++,i, supplier.getBankName() == null ?"": supplier.getBankName()));
					sheet.addCell(new Label(j++,i, supplier.getAccountNumber() == null ?"": supplier.getAccountNumber()));
					sheet.addCell(getLabelInfo(cellInfo,j++,i, supplier.getTaxRate() == null ?"": supplier.getTaxRate().toString(),supplier));
					sheet.addCell(new Label(j++,i, supplier.getEnabled()?"启用":"禁用"));
					i++;
				}
			}
			workbook.write();
			workbook.close();
		}
		catch (Exception e) {
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出信息为excel表格异常", e);
		}
	}

	/**
	 * 根据错误信息进行提示--excel表格背景设置为红色，表示导入信息有误
	 * @param cellInfo
	 * @param cellNum
	 * @param columnNum
	 * @param value
	 * @return
	 */
	private Label getLabelInfo(Map<Integer,String> cellInfo,int cellNum,int columnNum,String value,Supplier supplier)
	{
		Label label = null;

		//设置背景颜色
		WritableCellFormat cellFormat = new WritableCellFormat();
		try
		{
			cellFormat.setBackground(Colour.RED);
		}
		catch (WriteException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>设置单元格背景颜色错误", e);
		}

		if(null == cellInfo || cellInfo.size() == 0) {
			label = new Label(cellNum, columnNum, value);
		}
		else {
			//表示此单元格有错误
			if(cellInfo.containsKey(cellNum)) {
				if(cellNum == SupplierConstants.BusinessForExcel.EXCEL_ADVANCE_IN) {
					label = new Label(cellNum, columnNum, supplier.getAdvanceInStr(), cellFormat);
				}
				else if(cellNum == SupplierConstants.BusinessForExcel.EXCEL_BEGIN_NEED_GET) {
					label = new Label(cellNum, columnNum, supplier.getBeginNeedGetStr(), cellFormat);
				}
				else if(cellNum == SupplierConstants.BusinessForExcel.EXCEL_BEGIN_NEED_PAY) {
					label = new Label(cellNum, columnNum, supplier.getBeginNeedPayStr(), cellFormat);
				}
				else if(cellNum == SupplierConstants.BusinessForExcel.EXCEL_TAX_RATE) {
					label = new Label(cellNum, columnNum, supplier.getTaxRateStr(), cellFormat);
				}
			}
			else{
				label =  new Label(cellNum, columnNum, value);
			}
		}
		return label;
	}

	@Override
	public InputStream importExcel(File assetFile) throws JshException {
		//全局变量--每次调用前需要清空数据
		mapData.clear();
		//2、解析文件成资产数据
		parseFile(assetFile);

		if(null != wrongData && wrongData.size()>0) {
			//将OutputStream转化为InputStream
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			putDataOnOutputStream(out,wrongData);
			return new ByteArrayInputStream(out.toByteArray());
		}
		else{
			return null;
		}
	}


	/**
	 * 解析excel表格
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
			while(itsheet.hasNext()) {
				//获取当前行数据
				Row row = itsheet.next();
				//获取一行有多少单元格
//                System.out.println(row.getLastCellNum());

				//excel表格第几行数据 从1开始 0 是表头
				int rowNum = row.getRowNum();
				/**
				 * 表头跳过不读
				 */
				if(SupplierConstants.BusinessForExcel.EXCEL_TABLE_HEAD ==  rowNum)
					continue;

				//开始处理excel表格内容 --每行数据读取,同时统计总共行数
				totalRow ++;

				//获取excel表格的每格数据内容
				Iterator<Cell> it = row.cellIterator();
				//资产子类型--添加了一些excel表格数据
				Supplier supplier = new Supplier();
				//保存每个单元格错误类型
				Map<Integer,String> cellType = new HashMap<Integer,String>();
				Boolean hasBeginNeedGet = false; //是否存在期初应付
				//设置列号
				supplier.setRowLineNum(rowNum);

				Cell cell = null;
				//判断列号--从零开始
				int cellIndex = 0;
				while(it.hasNext()) {
					//获取每个单元格对象
					cell = it.next();
					//获取列号
					cellIndex = cell.getColumnIndex();
					//设置此单元格为字符串类型
					cell.setCellType(Cell.CELL_TYPE_STRING);

					Log.infoFileSync("==================excel表格中第" + totalRow + "行的第 " + cellIndex + "列的值为" + cell.getStringCellValue());

					//每行中数据顺序 "名称","类型","联系人","电话","电子邮箱","预收款","期初应收","期初应付","备注","传真","手机","地址","纳税人识别号","开户行","账号","税率"
					switch(cellIndex) {
						case SupplierConstants.BusinessForExcel.EXCEL_SUPPLIER :
							String supplierName = cell.getStringCellValue();
							if(null == supplierName || "".equals(supplierName)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(名称)信息");
								break;
							}
							supplier.setSupplier(supplierName);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_TYPE :
							String type = cell.getStringCellValue();
							if(null == type || "".equals(type)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(类型)信息");
								break;
							}
							supplier.setType(type);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_CONTACTS:
							String contacts = cell.getStringCellValue();
							if(null == contacts || "".equals(contacts)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(联系人)信息");
								break;
							}
							supplier.setContacts(contacts);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_PHONE_NUM :
							String phoneNum = cell.getStringCellValue();
							if(null == phoneNum || "".equals(phoneNum)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(电话)信息");
								break;
							}
							supplier.setPhonenum(phoneNum);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_EMAIL :
							String email = cell.getStringCellValue();
							if(null == email || "".equals(email)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(电子邮箱)信息");
								break;
							}
							supplier.setEmail(email);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_ADVANCE_IN :
							String advanceIn = cell.getStringCellValue();
							if(null == advanceIn || "".equals(advanceIn)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(预收款)信息");
								break;
							}
							if(Tools.checkStrIsNum(advanceIn)) {
								supplier.setAdvanceIn(Double.parseDouble(advanceIn));
							}
							else{
								Log.errorFileSync(">>>>>>>>>>>>>>>>>(预收款)不是数字格式");
								cellType.put(cellIndex, "wrong");
								supplier.setAdvanceIn(0.00d);
								supplier.setAdvanceInStr(advanceIn);
							}
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_BEGIN_NEED_GET :
							String beginNeedGet = cell.getStringCellValue();
							if(null == beginNeedGet || "".equals(beginNeedGet)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(期初应收)信息");
								break;
							}
							if(Tools.checkStrIsNum(beginNeedGet) && Double.parseDouble(beginNeedGet)>=0) {
								if(Double.parseDouble(beginNeedGet)>0) {
									hasBeginNeedGet = true; //存在期初应付信息
								}
								supplier.setBeginNeedGet(Double.parseDouble(beginNeedGet));
							}
							else{
								Log.errorFileSync(">>>>>>>>>>>>>>>>>(期初应收)不是数字格式");
								cellType.put(cellIndex, "wrong");
								supplier.setBeginNeedGet(0.00d);
								supplier.setBeginNeedGetStr(beginNeedGet);
							}
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_BEGIN_NEED_PAY :
							String beginNeedPay = cell.getStringCellValue();
							if(null == beginNeedPay || "".equals(beginNeedPay)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(期初应付)信息");
								break;
							}
							if(Tools.checkStrIsNum(beginNeedPay) && Double.parseDouble(beginNeedPay)>=0) {
								if(hasBeginNeedGet){ //同时存在不允许
									Log.errorFileSync(">>>>>>>>>>>>>>>>>(期初应付)和期初应收不能同时存在");
									cellType.put(cellIndex, "wrong");
									supplier.setBeginNeedPay(0.00d);
									supplier.setBeginNeedPayStr(beginNeedPay);
								}
								else {
									supplier.setBeginNeedPay(Double.parseDouble(beginNeedPay));
								}
							}
							else{
								Log.errorFileSync(">>>>>>>>>>>>>>>>>(期初应付)不是数字格式");
								cellType.put(cellIndex, "wrong");
								supplier.setBeginNeedPay(0.00d);
								supplier.setBeginNeedPayStr(beginNeedPay);
							}
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_DESCRIPTION :
							String description = cell.getStringCellValue();
							if(null == description || "".equals(description)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(备注)信息");
								break;
							}
							supplier.setDescription(description);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_FAX :
							String fax = cell.getStringCellValue();
							if(null == fax || "".equals(fax)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(传真)信息");
								break;
							}
							supplier.setFax(fax);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_TELEPHONE :
							String telephone = cell.getStringCellValue();
							if(null == telephone || "".equals(telephone)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(手机)信息");
								break;
							}
							supplier.setTelephone(telephone);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_ADDRESS :
							String address = cell.getStringCellValue();
							if(null == address || "".equals(address)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(地址)信息");
								break;
							}
							supplier.setAddress(address);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_TAX_NUM :
							String taxNum = cell.getStringCellValue();
							if(null == taxNum || "".equals(taxNum)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(纳税人识别号)信息");
								break;
							}
							supplier.setTaxNum(taxNum);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_BANK_NAME :
							String bankName = cell.getStringCellValue();
							if(null == bankName || "".equals(bankName)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(开户行)信息");
								break;
							}
							supplier.setBankName(bankName);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_ACCOUNT_NUMBER :
							String accountNumber = cell.getStringCellValue();
							if(null == accountNumber || "".equals(accountNumber)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(账号)信息");
								break;
							}
							supplier.setAccountNumber(accountNumber);
							break;
						case SupplierConstants.BusinessForExcel.EXCEL_TAX_RATE :
							String taxRate = cell.getStringCellValue();
							if(null == taxRate || "".equals(taxRate)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(税率)信息");
								break;
							}
							if(Tools.checkStrIsNum(taxRate)) {
								supplier.setTaxRate(Double.parseDouble(taxRate));
							}
							else{
								Log.errorFileSync(">>>>>>>>>>>>>>>>>(税率)不是数字格式");
								cellType.put(cellIndex, "wrong");
								supplier.setTaxRate(0.00d);
								supplier.setTaxRateStr(taxRate);
							}
							break;
					}
				}
				supplier.setCellInfo(cellType);

				Log.infoFileSync(totalRow + "行总共有" + cellIndex + "列");

				//判断完成后增加数据
				if((null!=cellType && cellType.size() >0) || supplier.getSupplier() == null) {
					wrongData.add(supplier);
				}
				else {
					supplier.setEnabled(true);
					supplier.setIsystem((short)1);
					supplierDao.save(supplier);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>读取excel文件异常:找不到指定文件！",e);
		}
		catch (IOException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>读取excel文件异常，请确认文件格式是否正确 ！",e);
		}
		Log.infoFileSync("===================excel表格总共有 " + totalRow + " 条记录!");
	}
}
