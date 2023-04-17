package com.jsh.erp.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
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

	public static void downloadFile(InputStream inputStream, String fileName , HttpServletResponse response) {
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] buff = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buff)) != -1) {
				outputStream.write(buff, 0, length);
			}
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("关闭资源出错" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
}
