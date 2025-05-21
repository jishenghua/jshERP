package com.jsh.erp.utils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import jxl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import jxl.format.*;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExcelUtils {

	public static InputStream getPathByFileName(String template, String tmpFileName) {
		File tmpFile = new File(template, tmpFileName);
		InputStream path = null;
		//判断文件或文件夹是否存在
		if (tmpFile.exists()) {
			try {
				path = new FileInputStream(tmpFile);
			} catch (FileNotFoundException e) {
				log.error("", e);
			}
		}
		return path;
	}

	/**
	 * 导出excel，带多sheet
	 *
	 * @param wtwb
	 * @param tip
	 * @param names
	 * @param title
	 * @param index
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public static void exportObjectsManySheet(WritableWorkbook wtwb, String tip,
											  String[] names, String title, int index, List<String[]> objects) throws Exception {
		WritableSheet sheet = wtwb.createSheet(title, index);
		sheet.getSettings().setDefaultColumnWidth(12);

		// 标题的格式-红色
		WritableFont redWF = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.RED);
		WritableCellFormat redWFFC = new WritableCellFormat(redWF);
		redWFFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		redWFFC.setBorder(Border.ALL, BorderLineStyle.THIN);

		// 标题的格式-黑色
		WritableFont blackWF = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat blackWFFC = new WritableCellFormat(blackWF);
		blackWFFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		blackWFFC.setBorder(Border.ALL, BorderLineStyle.THIN);

		// 设置字体以及单元格格式
		WritableFont wfont = new WritableFont(WritableFont.createFont("楷书"), 12);
		WritableCellFormat format = new WritableCellFormat(wfont);
		format.setAlignment(Alignment.LEFT);
		format.setVerticalAlignment(VerticalAlignment.TOP);
		format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

		// 第一行写入提示
		if(com.jsh.erp.utils.StringUtil.isNotEmpty(tip) && tip.contains("*")) {
			sheet.addCell(new Label(0, 0, tip, redWFFC));
		} else {
			sheet.addCell(new Label(0, 0, tip, blackWFFC));
		}

		// 第二行写入标题
		for (int i = 0; i < names.length; i++) {
			if(StringUtil.isNotEmpty(names[i]) && names[i].contains("*")) {
				sheet.addCell(new Label(i, 1, names[i], redWFFC));
			} else {
				sheet.addCell(new Label(i, 1, names[i], blackWFFC));
			}
		}

		// 其余行依次写入数据
		int rowNum = 2;
		for (int j = 0; j < objects.size(); j++) {
			String[] obj = objects.get(j);
			for (int h = 0; h < obj.length; h++) {
				sheet.addCell(new Label(h, rowNum, obj[h], format));
			}
			rowNum = rowNum + 1;
		}
	}

	/**
	 * 导出excel，带单个sheet
	 *
	 * @param fileName
	 * @param names
	 * @param title
	 * @param objects
	 * @return
	 * @throws Exception
	 */

	public static File exportObjectsOneSheet(String fileName, String tip,
											 String[] names, String title, List<Object[]> objects) throws Exception {
		File excelFile = new File("/opt/"+ fileName);
		WritableWorkbook wtwb = Workbook.createWorkbook(excelFile);
		WritableSheet sheet = wtwb.createSheet(title, 0);
		sheet.getSettings().setDefaultColumnWidth(12);

		// 标题的格式-红色
		WritableFont redWF = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat redWFFC = new WritableCellFormat(redWF);
		redWFFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		redWFFC.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

		// 标题的格式-黑色
		WritableFont blackWF = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat blackWFFC = new WritableCellFormat(blackWF);
		blackWFFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		blackWFFC.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

		// 设置字体以及单元格格式
		WritableFont wfont = new WritableFont(WritableFont.createFont("楷书"), 12);
		WritableCellFormat format = new WritableCellFormat(wfont);
		format.setAlignment(Alignment.LEFT);
		format.setVerticalAlignment(VerticalAlignment.TOP);
		format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

		// 第一行写入提示
		if(StringUtil.isNotEmpty(tip) && tip.contains("*")) {
			sheet.addCell(new Label(0, 0, tip, redWFFC));
		} else {
			sheet.addCell(new Label(0, 0, tip, blackWFFC));
		}

		// 第二行写入标题
		for (int i = 0; i < names.length; i++) {
			if(StringUtil.isNotEmpty(names[i]) && names[i].contains("*")) {
				sheet.addCell(new Label(i, 1, names[i], redWFFC));
			} else {
				sheet.addCell(new Label(i, 1, names[i], blackWFFC));
			}
		}

		// 其余行依次写入数据
		int rowNum = 2;
		for (int j = 0; j < objects.size(); j++) {
			Object[] obj = objects.get(j);
			for (int h = 0; h < obj.length; h++) {
				if(obj[h] instanceof String) {
					sheet.addCell(new Label(h, rowNum, obj[h].toString(), format));
				} else if(obj[h] instanceof BigDecimal || obj[h] instanceof Double || obj[h] instanceof Integer || obj[h] instanceof Long) {
					sheet.addCell(new jxl.write.Number(h, rowNum, Double.parseDouble(obj[h].toString()), format));
				} else {
					String cont = obj[h]!=null?obj[h].toString():"";
					sheet.addCell(new Label(h, rowNum, cont, format));
				}
			}
			rowNum = rowNum + 1;
		}
		wtwb.write();
		wtwb.close();
		return excelFile;
	}

	public static String getContent(Sheet src, int rowNum, int colNum) {
		if(colNum < src.getRow(rowNum).length) {
			return src.getRow(rowNum)[colNum].getContents().trim();
		} else {
			return null;
		}
	}

	/**
	 * 获取真实的行数，剔除掉空白行
	 * @param src
	 * @return
	 */
	public static int getRightRows(Sheet src) {
		int rsRows = src.getRows(); //行数
		int rsCols = src.getColumns(); //列数
		int nullCellNum;
		int rightRows = rsRows;
		for (int i = 1; i < rsRows; i++) { //统计行中为空的单元格数
			nullCellNum = 0;
			for (int j = 0; j < rsCols; j++) {
				String val = src.getCell(j, i).getContents().trim();
				if (StringUtils.isEmpty(val)) {
					nullCellNum++;
				}
			}
			if (nullCellNum >= rsCols) { //如果nullCellNum大于或等于总的列数
				rightRows--; //行数减一
			}
		}
		return rightRows;
	}

	public static void downloadExcel(File excelFile, String fileName, HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		fileName = new String(fileName.getBytes("gbk"),"ISO8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".xls" + "\"");
		FileInputStream fis = new FileInputStream(excelFile);
		OutputStream out = response.getOutputStream();

		int SIZE = 1024 * 1024;
		byte[] bytes = new byte[SIZE];
		int LENGTH = -1;
		while((LENGTH = fis.read(bytes)) != -1){
			out.write(bytes,0,LENGTH);
		}
		out.flush();
		fis.close();
	}
}
