package com.jsh.junitest.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.jsh.model.po.Asset;

public class ImportExcelTest
{

	/**
	 * 文件路径
	 */
	private static final String excelPath = "D:/movie/qq.xls";
	
	/**
	 * 解析后的data
	 */
	private static Map<String,Asset> data = new LinkedHashMap<String, Asset>();
	
	
	/**
	 * 解析数据格式 
	 */
	public static void paseData()
	{
	    int total = 0;
		try
		{  
			//创建对Excel工作簿文件的引用  
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelPath));  
			//创建对工作表的引用,获取第一个工作表的内容
			HSSFSheet sheet = workbook.getSheetAt(0);
			/**
			 * =====================================
			 * 1、此处要增加报盘文件的验证，如果不是报盘文件需要进行特殊的处理,3列
			 * 2、报盘文件内容为空处理
			 * 3、如果是修改过的报盘文件内容
			 */
			Iterator<Row> itsheet = sheet.rowIterator();
			while(itsheet.hasNext())
			{
				System.out.println();
				Row row = itsheet.next();
				
				//excel表格第几行数据 从1开始 0 是表头
				int rowNum = row.getRowNum();
				/**
				 * 表头跳过不读
				 */
				if(0 ==  rowNum)
					continue;
				total ++;
				Iterator<Cell> it = row.cellIterator();
//				PreAuthExcelTip preAuth = new PreAuthExcelTip();
//				preAuth.setAuthState(authState);
//				preAuth.setWrongRow(rowNum);
				Cell cell = null;
				String preAuthMacAddr = "";
				//判断行号
				int i = 0;
				while(it.hasNext())
				{
					cell = it.next();
					i = cell.getColumnIndex();
					
					System.out.print(cell.getCellType()  + "  " +  cell.getStringCellValue());
					if(1 == i)
					{
						String contentValue = cell.getStringCellValue();
						if(null == contentValue || "".equals(contentValue))
							break;
//						preAuth.setSnNo(cell.getStringCellValue());
					}
					if(2 == i)
					{
						String contentValue = cell.getStringCellValue();
						if(null == contentValue || "".equals(contentValue))
							break;
						preAuthMacAddr = contentValue;
//						preAuth.setMacAddr(PreAuthUtil.changeMacFormt(preAuthMacAddr));
					}					
				}
				System.out.println("此行总共有" + i + "列");
				//报盘文件为3列，否则不是报盘文件
//				if(i != 2)
//				{
//					PreAuthUtil.showMessageDialog("导入文件格式不合法，请重新选择文件进行操作！");
//					return null;
//				}
//				//MAC地址合法添加到正确的MAC地址数组
//				if(PreAuthUtil.isMacAddress(preAuthMacAddr))
//				{
//					if(allMacAddress.contains(preAuthMacAddr))
//					{
//						repeatPreAuth.add(preAuth);
//					}
//					else
//					{						
//						allMacAddress.add(preAuthMacAddr);
//						rightPreAuth.add(preAuth);
//					}
//				}
//				else
//				{
//					wrongPreAuth.add(preAuth);
//				}
			}  
			
//			if((null==rightPreAuth || rightPreAuth.size()==0)&& (null == wrongPreAuth|| wrongPreAuth.size() ==0)&&(null == repeatPreAuth ||repeatPreAuth.size() ==0))
//			{
//				PreAuthUtil.showMessageDialog("报盘文件内容为空，请重新选择！");
//				return map;
//			}
//			//处理完读取文件，返回处理结果
//			map.put("rightMacAddr", rightPreAuth);
//			map.put("wrongMacAddr", wrongPreAuth);
//			map.put("repeatMacAddr", repeatPreAuth);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("预授权读取文件异常 : " + e );  
			e.printStackTrace();
//			PreAuthUtil.showMessageDialog("预授权读取excel文件异常:找不到指定文件！");
//			return map;
		}
		catch (IOException e)
		{
			System.out.println("预授权读取文件IO异常 : " + e ); 
			e.printStackTrace();
//			PreAuthUtil.showMessageDialog("读取excel文件异常，请确认文件格式是否正确！");
//			return map;
		}  
		
		System.out.println("excel表格总共有 " + total + " 条记录!");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ImportExcelTest().paseData();
	}

}
