package com.jsh.erp.controller;

import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.FileUtils;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Description
 * @Author: jishenghua
 * @Date: 2021-3-13 0:01
 */
@RestController
@RequestMapping(value = "/systemConfig")
@Api(tags = {"系统参数"})
public class SystemConfigController {
    private Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

    @Resource
    private UserService userService;

    @Resource
    private DepotService depotService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    @Value(value="${file.uploadType}")
    private Long fileUploadType;

    @Value(value="${file.path}")
    private String filePath;

    @Value(value="${spring.servlet.multipart.max-file-size}")
    private Long maxFileSize;

    @Value(value="${spring.servlet.multipart.max-request-size}")
    private Long maxRequestSize;

    /**
     * 获取当前租户的配置信息
     * @param request
     * @return
     */
    @GetMapping(value = "/getCurrentInfo")
    @ApiOperation(value = "获取当前租户的配置信息")
    public BaseResponseInfo getCurrentInfo(HttpServletRequest request) throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try{
            List<SystemConfig> list = systemConfigService.getSystemConfig();
            res.code = 200;
            if(list.size()>0) {
                res.data = list.get(0);
            }
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 获取文件大小限制
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/fileSizeLimit")
    @ApiOperation(value = "获取文件大小限制")
    public BaseResponseInfo fileSizeLimit(HttpServletRequest request) throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try{
            Long limit = 0L;
            if(maxFileSize<maxRequestSize) {
                limit = maxFileSize;
            } else {
                limit = maxRequestSize;
            }
            res.code = 200;
            res.data = limit;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 文件上传统一方法
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "文件上传统一方法")
    public BaseResponseInfo upload(HttpServletRequest request, HttpServletResponse response) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String savePath = "";
            String bizPath = request.getParameter("biz");
            String name = request.getParameter("name");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件对象
            if(fileUploadType == 1) {
                savePath = systemConfigService.uploadLocal(file, bizPath, name, request);
            } else if(fileUploadType == 2) {
                savePath = systemConfigService.uploadAliOss(file, bizPath, name, request);
            }
            if(StringUtil.isNotEmpty(savePath)){
                res.code = 200;
                res.data = savePath;
            }else {
                res.code = 500;
                res.data = "上传失败！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "上传失败！";
        }
        return res;
    }

    /**
     * 预览图片&下载文件
     * 请求地址：http://localhost:8080/common/static/{financial/afsdfasdfasdf_1547866868179.txt}
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/**")
    @ApiOperation(value = "预览图片&下载文件")
    public void view(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if(StringUtil.isEmpty(imgPath) || imgPath=="null"){
            return;
        }
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace("..", "");
            if (imgPath.endsWith(",")) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String fileUrl = "";
            if(fileUploadType == 1) {
                fileUrl = systemConfigService.getFileUrlLocal(imgPath);
                inputStream = new BufferedInputStream(new FileInputStream(fileUrl));
            } else if(fileUploadType == 2) {
                fileUrl = systemConfigService.getFileUrlAliOss(imgPath);
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                inputStream = conn.getInputStream();// 通过输入流获取图片数据
            }
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("预览文件失败" + e.getMessage());
            response.setStatus(404);
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(404);
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 预览缩略图&下载文件
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/mini/**")
    @ApiOperation(value = "预览缩略图&下载文件")
    public void viewMini(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if(StringUtil.isEmpty(imgPath) || imgPath=="null"){
            return;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace("..", "");
            if (imgPath.endsWith(",")) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String fileUrl = "";
            if(fileUploadType == 1) {
                fileUrl = systemConfigService.getFileUrlLocal(imgPath);
                inputStream = new BufferedInputStream(new FileInputStream(fileUrl));
            } else if(fileUploadType == 2) {
                fileUrl = systemConfigService.getFileUrlAliOss(imgPath);
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                inputStream = conn.getInputStream();// 通过输入流获取图片数据
            }
            int index = fileUrl.lastIndexOf(".");
            String ext = fileUrl.substring(index + 1);
            BufferedImage image = systemConfigService.getImageMini(inputStream, 40);
            outputStream = response.getOutputStream();
            ImageIO.write(image, ext, outputStream);
            response.flushBuffer();
        } catch (Exception e) {
            response.setStatus(404);
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     *  把指定URL后的字符串全部截断当成参数
     *  这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
     * @param request
     * @return
     */
    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
}
