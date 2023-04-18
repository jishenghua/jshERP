package com.jsh.erp.utils;

import java.io.*;
import java.text.SimpleDateFormat;
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
	 * 导出excel，不需要第一行的title
	 *
	 * @param fileName
	 * @param names
	 * @param title
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public static File exportObjectsWithoutTitle(String fileName, String tip,
			String[] names, String title, List<String[]> objects)
			throws Exception {
		File excelFile = new File(fileName);
		WritableWorkbook wtwb = Workbook.createWorkbook(excelFile);
		WritableSheet sheet = wtwb.createSheet(title, 0);
		sheet.getSettings().setDefaultColumnWidth(20);

		// 标题的格式
		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 15,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

		// 设置字体以及单元格格式
		WritableFont wfont = new WritableFont(WritableFont.createFont("楷书"), 15);
		WritableCellFormat format = new WritableCellFormat(wfont);
		format.setAlignment(Alignment.LEFT);
		format.setVerticalAlignment(VerticalAlignment.TOP);

		// 第一行写入提示
		sheet.addCell(new Label(0, 0, tip, wcfFC));
		// 第二行写入标题
		for (int i = 0; i < names.length; i++) {
			sheet.addCell(new Label(i, 1, names[i], wcfFC));
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
