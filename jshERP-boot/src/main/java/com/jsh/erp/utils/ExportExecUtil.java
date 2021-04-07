package com.jsh.erp.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class ExportExecUtil {

	public static void showExec(File excelFile,String fileName,HttpServletResponse response) throws Exception{
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
