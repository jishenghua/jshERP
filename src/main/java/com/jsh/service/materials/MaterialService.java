package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.materials.MaterialIDAO;
import com.jsh.model.po.Material;
import com.jsh.model.po.MaterialCategory;
import com.jsh.util.JshException;
import com.jsh.util.MaterialConstants;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
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

public class MaterialService extends BaseService<Material> implements MaterialIService
{
	@SuppressWarnings("unused")
	private MaterialIDAO materialDao;

	
	public void setMaterialDao(MaterialIDAO materialDao) {
		this.materialDao = materialDao;
	}

	public void batchSetEnable(Boolean enable,String supplierIDs){
		materialDao.batchSetEnable(enable, supplierIDs);
	}


	@Override
	public void findUnitName(PageUtil<Material> pageUtil, Long mId) throws JshException
	{
		materialDao.findUnitName(pageUtil, mId);
	}

	@Override
	protected Class<Material> getEntityClass()
	{
		return Material.class;
	}

	/**
	 * 初始化加载所有系统基础数据
	 */
	@SuppressWarnings({"rawtypes"})
	private static Map<String,List> mapData = new HashMap<String, List>();

	/**
	 * 错误的表格数据
	 */
	private static List<Material> wrongData = new ArrayList<Material>();
	/**
	 * 导出Excel表格
	 */
	@Override
	public InputStream exmportExcel(String isAllPage, PageUtil<Material> pageUtil)throws JshException
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
	private void putDataOnOutputStream(OutputStream os, List<Material> dataList) {
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("信息报表", 0);
			//增加列头
			String[] colunmName = {"品名","类型","型号","安全存量","单位","零售价","最低售价","预计采购价","批发价","备注","状态"};
			for(int i = 0 ;i < colunmName.length;i ++) {
				sheet.setColumnView(i, 10);
				sheet.addCell(new Label(i, 0, colunmName[i]));
			}
			if (null != dataList && dataList.size() > 0) {
				int i = 1;
				for (Material material: dataList){
					int j = 0;
					Map<Integer,String> cellInfo = material.getCellInfo();
					sheet.addCell(new Label(j++,i, material.getName()));
					sheet.addCell(new Label(j++,i, material.getMaterialCategory().getName()));
					sheet.addCell(new Label(j++,i, material.getModel() == null ?"": material.getModel()));
					sheet.addCell(getLabelInfo(cellInfo,j++,i, material.getSafetyStock() == null ?"": material.getSafetyStock().toString(),material));
					sheet.addCell(new Label(j++,i, material.getUnit() == null ?"": material.getUnit()));
					sheet.addCell(new Label(j++,i, material.getRetailPrice() == null ?"": material.getRetailPrice().toString()));
					sheet.addCell(new Label(j++,i, material.getLowPrice() == null ?"": material.getLowPrice().toString()));
					sheet.addCell(new Label(j++,i, material.getPresetPriceOne() == null ?"": material.getPresetPriceOne().toString()));
					sheet.addCell(new Label(j++,i, material.getPresetPriceTwo() == null ?"": material.getPresetPriceTwo().toString()));
					sheet.addCell(new Label(j++,i, material.getRemark() == null ?"": material.getRemark()));
					sheet.addCell(new Label(j++,i, material.getEnabled()?"启用":"禁用"));
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
	private Label getLabelInfo(Map<Integer,String> cellInfo,int cellNum,int columnNum,String value,Material material)
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
				if(cellNum == MaterialConstants.BusinessForExcel.EXCEL_SAFETY_STOCK) {
					label = new Label(cellNum, columnNum, material.getSafetyStockStr(), cellFormat);
				}
			}
			else{
				label =  new Label(cellNum, columnNum, value);
			}
		}
		return label;
	}

	@Override
	public InputStream importExcel(File materialFile) throws JshException {
		//全局变量--每次调用前需要清空数据
		mapData.clear();
		//2、解析文件成资产数据
		parseFile(materialFile);

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

				//excel表格第几行数据 从1开始 0 是表头
				int rowNum = row.getRowNum();
				/**
				 * 表头跳过不读
				 */
				if(MaterialConstants.BusinessForExcel.EXCEL_TABLE_HEAD ==  rowNum)
					continue;

				//开始处理excel表格内容 --每行数据读取,同时统计总共行数
				totalRow ++;

				//获取excel表格的每格数据内容
				Iterator<Cell> it = row.cellIterator();
				//资产子类型--添加了一些excel表格数据
				Material material = new Material();
				//保存每个单元格错误类型
				Map<Integer,String> cellType = new HashMap<Integer,String>();
				//设置列号
				material.setRowLineNum(rowNum);

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

					//每行中数据顺序  "品名","类型","型号","安全存量","单位","零售价","最低售价","预计采购价","批发价","备注","状态"
					switch(cellIndex) {
						case MaterialConstants.BusinessForExcel.EXCEL_NAME :
							String materialName = cell.getStringCellValue();
							if(null == materialName || "".equals(materialName)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(品名)信息");
								break;
							}
							material.setName(materialName);
							break;
						case MaterialConstants.BusinessForExcel.EXCEL_CATEGORY :
							String category = cell.getStringCellValue();
							if(null == category || "".equals(category)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(类型)信息");
								break;
							}
							material.setMaterialCategory(new MaterialCategory(1l)); //根目录
							break;
						case MaterialConstants.BusinessForExcel.EXCEL_MODEL:
							String model = cell.getStringCellValue();
							if(null == model || "".equals(model)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(型号)信息");
								break;
							}
							material.setModel(model);
							break;
						case MaterialConstants.BusinessForExcel.EXCEL_SAFETY_STOCK :
							String safetyStock = cell.getStringCellValue();
							if(null == safetyStock || "".equals(safetyStock)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(安全存量)信息");
								break;
							}
							if(Tools.checkStrIsNum(safetyStock)) {
								material.setSafetyStock(Double.parseDouble(safetyStock));
							}
							else{
								Log.errorFileSync(">>>>>>>>>>>>>>>>>(安全存量)不是数字格式");
								cellType.put(cellIndex, "wrong");
								material.setSafetyStock(0.00d);
								material.setSafetyStockStr(safetyStock);
							}
							break;
						case MaterialConstants.BusinessForExcel.EXCEL_UNIT:
							String unit = cell.getStringCellValue();
							if(null == unit || "".equals(unit)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(单位)信息");
								break;
							}
							material.setUnit(unit);
							break;
						case MaterialConstants.BusinessForExcel.EXCEL_REMARK :
							String remark = cell.getStringCellValue();
							if(null == remark || "".equals(remark)) {
								Log.errorFileSync(">>>>>>>>>>>>>>>>列表没有填写(备注)信息");
								break;
							}
							material.setRemark(remark);
							break;
					}
				}
				material.setCellInfo(cellType);

				Log.infoFileSync(totalRow + "行总共有" + cellIndex + "列");

				//判断完成后增加数据
				if((null!=cellType && cellType.size() >0) || material.getName() == null) {
					wrongData.add(material);
				}
				else {
					material.setEnabled(true);
					materialDao.save(material);
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
