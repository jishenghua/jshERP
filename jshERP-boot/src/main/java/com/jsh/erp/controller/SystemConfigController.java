package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.FileUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Description
 * @Author: jishenghua
 * @Date: 2021-3-13 0:01
 */
@RestController
@RequestMapping(value = "/systemConfig")
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

    @Value(value="${file.path}")
    private String filePath;

    @Value(value="${spring.servlet.multipart.max-file-size}")
    private Long maxFileSize;

    @Value(value="${spring.servlet.multipart.max-request-size}")
    private Long maxRequestSize;

    @GetMapping(value = "/getDictItems/{dictCode}")
    public BaseResponseInfo getDictItems(@PathVariable String dictCode,
                                       HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Long userId = userService.getUserId(request);
            JSONArray arr = new JSONArray();
            if(StringUtil.isNotEmpty(dictCode)) {
                if (dictCode.equals("depotDict")) {
                    List<Depot> dataList = depotService.findUserDepot();
                    //开始拼接json数据
                    if (null != dataList) {
                        boolean depotFlag = systemConfigService.getDepotFlag();
                        for (Depot depot : dataList) {
                            JSONObject item = new JSONObject();
                            //勾选判断1
                            Boolean flag = false;
                            String type = "UserDepot";
                            try {
                                flag = userBusinessService.checkIsUserBusinessExist(type, userId.toString(), "[" + depot.getId().toString() + "]");
                            } catch (DataAccessException e) {
                                logger.error(">>>>>>>>>>>>>>>>>查询用户对应的仓库：类型" + type + " KeyId为： " + userId + " 存在异常！");
                            }
                            if (!depotFlag || flag) {
                                item.put("value", depot.getId().toString());
                                item.put("text", depot.getName());
                                item.put("title", depot.getName());
                                arr.add(item);
                            }
                        }
                    }
                }
            }
            res.code = 200;
            res.data = arr;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 获取当前租户的配置信息
     * @param request
     * @return
     */
    @GetMapping(value = "/getCurrentInfo")
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
    public BaseResponseInfo upload(HttpServletRequest request, HttpServletResponse response) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String savePath = "";
            String bizPath = request.getParameter("biz");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件对象
            if(StringUtil.isEmpty(bizPath)){
                bizPath = "";
            }
            savePath = this.uploadLocal(file,bizPath);
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
     * 本地文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @return
     */
    private String uploadLocal(MultipartFile mf,String bizPath){
        try {
            String ctxPath = filePath;
            String fileName = null;
            File file = new File(ctxPath + File.separator + bizPath + File.separator );
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String orgName = mf.getOriginalFilename();// 获取文件名
            orgName = FileUtils.getFileName(orgName);
            if(orgName.indexOf(".")!=-1){
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            }else{
                fileName = orgName+ "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = null;
            if(StringUtil.isNotEmpty(bizPath)){
                dbpath = bizPath + File.separator + fileName;
            }else{
                dbpath = fileName;
            }
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", "/");
            }
            return dbpath;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 预览图片&下载文件
     * 请求地址：http://localhost:8080/common/static/{financial/afsdfasdfasdf_1547866868179.txt}
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/**")
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
            String fileUrl = filePath + File.separator + imgPath;
            File file = new File(fileUrl);
            if(!file.exists()){
                response.setStatus(404);
                throw new RuntimeException("文件不存在..");
            }
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
            inputStream = new BufferedInputStream(new FileInputStream(fileUrl));
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
