package com.jsh.erp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.PutObjectRequest;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.SystemConfigMapper;
import com.jsh.erp.datasource.mappers.SystemConfigMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SystemConfigService {
    private Logger logger = LoggerFactory.getLogger(SystemConfigService.class);

    @Resource
    private SystemConfigMapper systemConfigMapper;
    @Resource
    private SystemConfigMapperEx systemConfigMapperEx;
    @Resource
    private PlatformConfigService platformConfigService;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    @Value(value="${file.uploadType}")
    private Long fileUploadType;

    @Value(value="${file.path}")
    private String filePath;

    private static String DELETED = "deleted";

    public SystemConfig getSystemConfig(long id)throws Exception {
        SystemConfig result=null;
        try{
            result=systemConfigMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<SystemConfig> getSystemConfig()throws Exception {
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SystemConfig> list=null;
        try{
            list=systemConfigMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
    public List<SystemConfig> select(String companyName)throws Exception {
        List<SystemConfig> list=null;
        try{
            PageUtils.startPage();
            list=systemConfigMapperEx.selectByConditionSystemConfig(companyName);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSystemConfig(JSONObject obj, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(obj.toJSONString(), SystemConfig.class);
        int result=0;
        try{
            result=systemConfigMapper.insertSelective(systemConfig);
            String logInfo = StringUtil.isNotEmpty(systemConfig.getCompanyName())?systemConfig.getCompanyName():"配置信息";
            logService.insertLogWithUserId(userService.getCurrentUser().getId(), userService.getCurrentUser().getTenantId(), "系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(logInfo).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSystemConfig(JSONObject obj, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(obj.toJSONString(), SystemConfig.class);
        int result=0;
        try{
            result = systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
            String logInfo = StringUtil.isNotEmpty(systemConfig.getCompanyName())?systemConfig.getCompanyName():"配置信息";
            logService.insertLogWithUserId(userService.getCurrentUser().getId(), userService.getCurrentUser().getTenantId(), "系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(logInfo).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSystemConfig(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteSystemConfigByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfig(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteSystemConfigByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfigByIds(String ids)throws Exception {
        logService.insertLog("系统配置",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result = systemConfigMapperEx.batchDeleteSystemConfigByIds(new Date(), userInfo == null ? null : userInfo.getId(), idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name) throws Exception{
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdNotEqualTo(id).andCompanyNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SystemConfig> list =null;
        try{
            list=systemConfigMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    /**
     * 本地文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @return
     */
    public String uploadLocal(MultipartFile mf, String bizPath, HttpServletRequest request) throws Exception {
        try {
            if(StringUtil.isEmpty(bizPath)){
                bizPath = "";
            }
            // Validate bizPath to prevent directory traversal
            if (bizPath.contains("..") || bizPath.contains("/")) {
                throw new IllegalArgumentException("Invalid bizPath");
            }
            String token = request.getHeader("X-Access-Token");
            Long tenantId = Tools.getTenantIdByToken(token);
            bizPath = bizPath + File.separator + tenantId;
            String ctxPath = filePath;
            String fileName = null;
            File file = new File(ctxPath + File.separator + bizPath + File.separator );
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String orgName = mf.getOriginalFilename();// 获取文件名
            orgName = FileUtils.getFileName(orgName);

            // Validate file extension to allow only specific types
            String[] allowedExtensions = {".gif", ".jpg", ".jpeg", ".png", ".pdf", ".txt",".doc",".docx",".xls",".xlsx",
                    ".ppt",".pptx",".zip",".rar",".mp3",".mp4",".avi"};
            boolean isValidExtension = false;
            for (String ext : allowedExtensions) {
                if (orgName.toLowerCase().endsWith(ext)) {
                    isValidExtension = true;
                    break;
                }
            }
            if (!isValidExtension) {
                throw new IllegalArgumentException("Invalid file type");
            }

            if(orgName.contains(".")){
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            }else{
                fileName = orgName+ "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);

            // 返回路径
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
     * 阿里Oss文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @return
     */
    public String uploadAliOss(MultipartFile mf, String bizPath, HttpServletRequest request) throws Exception {
        if(StringUtil.isEmpty(bizPath)){
            bizPath = "";
        }
        // Validate bizPath to prevent directory traversal
        if (bizPath.contains("..") || bizPath.contains("/")) {
            throw new IllegalArgumentException("Invalid bizPath");
        }
        String token = request.getHeader("X-Access-Token");
        Long tenantId = Tools.getTenantIdByToken(token);
        bizPath = bizPath + "/" + tenantId;
        String endpoint = platformConfigService.getPlatformConfigByKey("aliOss_endpoint").getPlatformValue();
        String accessKeyId = platformConfigService.getPlatformConfigByKey("aliOss_accessKeyId").getPlatformValue();
        String accessKeySecret = platformConfigService.getPlatformConfigByKey("aliOss_accessKeySecret").getPlatformValue();
        String bucketName = platformConfigService.getPlatformConfigByKey("aliOss_bucketName").getPlatformValue();
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String fileName = "";
        String orgName = mf.getOriginalFilename();// 获取文件名
        orgName = FileUtils.getFileName(orgName);

        // Validate file extension to allow only specific types
        String[] allowedExtensions = {".gif", ".jpg", ".jpeg", ".png", ".pdf", ".txt",".doc",".docx",".xls",".xlsx",
                ".ppt",".pptx",".zip",".rar",".mp3",".mp4",".avi"};
        boolean isValidExtension = false;
        for (String ext : allowedExtensions) {
            if (orgName.toLowerCase().endsWith(ext)) {
                isValidExtension = true;
                break;
            }
        }
        if (!isValidExtension) {
            throw new IllegalArgumentException("Invalid file type");
        }

        if(orgName.contains(".")){
            fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
        }else{
            fileName = orgName+ "_" + System.currentTimeMillis();
        }
        String filePathStr = StringUtil.isNotEmpty(filePath)? filePath.substring(1):"";
        String objectName = filePathStr + "/" + bizPath + "/" + fileName;
        String smallObjectName = filePathStr + "-small/" + bizPath + "/" + fileName;
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        byte [] byteArr = mf.getBytes();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 保存原文件
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            ossClient.putObject(putObjectRequest);
            // 如果是图片-保存缩略图
            int index = fileName.lastIndexOf(".");
            String ext = fileName.substring(index + 1);
            if(ext.contains("gif") || ext.contains("jpg") || ext.contains("jpeg") || ext.contains("png")
                    || ext.contains("GIF") || ext.contains("JPG") || ext.contains("JPEG") || ext.contains("PNG")) {
                String fileUrl = getFileUrlAliOss(bizPath + "/" + fileName);
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                InputStream imgInputStream = conn.getInputStream();// 通过输入流获取图片数据
                BufferedImage smallImage = getImageMini(imgInputStream, 80);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
                ImageIO.write(smallImage, ext, imOut);
                InputStream isImg = new ByteArrayInputStream(bs.toByteArray());
                PutObjectRequest putSmallObjectRequest = new PutObjectRequest(bucketName, smallObjectName, isImg);
                ossClient.putObject(putSmallObjectRequest);
            }
            // 返回路径
            return bizPath + "/" + fileName;
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message:" + oe.getErrorMessage());
            logger.error("Error Code:" + oe.getErrorCode());
            logger.error("Request ID:" + oe.getRequestId());
            logger.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "";
    }

    public String getFileUrlLocal(String imgPath) {
        return filePath + File.separator + imgPath;
    }

    public String getFileUrlAliOss(String imgPath) throws Exception {
        String linkUrl = platformConfigService.getPlatformConfigByKey("aliOss_linkUrl").getPlatformValue();
        return linkUrl + filePath + "/" + imgPath;
    }

    /**
     * 逻辑删除文件
     * @param pathList
     */
    public void deleteFileByPathList(List<String> pathList) throws Exception {
        if(fileUploadType == 1) {
            //本地
            for(String pathStr: pathList) {
                if(StringUtil.isNotEmpty(pathStr)) {
                    String[] pathArr = pathStr.split(",");
                    for (String path : pathArr) {
                        // 提取文件的路径
                        String pathDir = getDirByPath(path);
                        if (StringUtil.isNotEmpty(pathDir)) {
                            // 源文件路径
                            Path sourcePath = Paths.get(filePath + File.separator + path);
                            // 目标文件路径（注意这里是新文件的完整路径，包括文件名）
                            Path targetPath = Paths.get(filePath + File.separator + DELETED + File.separator + path);
                            try {
                                File file = new File(filePath + File.separator + DELETED + File.separator + pathDir);
                                if (!file.exists()) {
                                    file.mkdirs();// 创建文件根目录
                                }
                                // 复制文件，如果目标文件已存在则替换它
                                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                                // 删除源文件
                                Files.delete(sourcePath);
                                logger.info("File copied successfully.");
                            } catch (NoSuchFileException e) {
                                logger.error("Source file not found: " + e.getMessage());
                            } catch (IOException e) {
                                logger.error("An I/O error occurred: " + e.getMessage());
                            } catch (SecurityException e) {
                                logger.error("No permission to copy file: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        } else if(fileUploadType == 2) {
            //oss
            String endpoint = platformConfigService.getPlatformConfigByKey("aliOss_endpoint").getPlatformValue();
            String accessKeyId = platformConfigService.getPlatformConfigByKey("aliOss_accessKeyId").getPlatformValue();
            String accessKeySecret = platformConfigService.getPlatformConfigByKey("aliOss_accessKeySecret").getPlatformValue();
            String bucketName = platformConfigService.getPlatformConfigByKey("aliOss_bucketName").getPlatformValue();
            for(String pathStr: pathList) {
                if(StringUtil.isNotEmpty(pathStr)) {
                    String[] pathArr = pathStr.split(",");
                    for (String path : pathArr) {
                        if(StringUtil.isNotEmpty(path)) {
                            // 创建OSSClient实例。
                            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                            try {
                                String filePathStr = StringUtil.isNotEmpty(filePath) ? filePath.substring(1) : "";
                                String sourceObjectKey = filePathStr + "/" + path;
                                String sourceSmallObjectKey = filePathStr + "-small/" + path;
                                String destinationObjectKey = DELETED + "/list/" + sourceObjectKey;
                                String destinationSmallObjectKey = DELETED + "/list/" + sourceSmallObjectKey;
                                this.copySourceToDest(ossClient, bucketName, sourceObjectKey, destinationObjectKey);
                                this.copySourceToDest(ossClient, bucketName, sourceSmallObjectKey, destinationSmallObjectKey);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            } finally {
                                // 关闭OSSClient。
                                if (ossClient != null) {
                                    ossClient.shutdown();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param ossClient
     * @param bucketName
     * @param sourceObjectKey 源文件路径，包括目录和文件名
     * @param destinationObjectKey 目标文件路径，包括新目录和文件名
     */
    public void copySourceToDest(OSS ossClient, String bucketName, String sourceObjectKey, String destinationObjectKey) {
        // 复制文件
        CopyObjectResult copyResult = ossClient.copyObject(bucketName, sourceObjectKey, bucketName, destinationObjectKey);
        // 确认复制成功
        if (copyResult != null && copyResult.getETag() != null) {
            logger.info("文件复制成功，ETag: " + copyResult.getETag());
            // 删除源文件
            ossClient.deleteObject(bucketName, sourceObjectKey);
            logger.info("源文件已删除：" + sourceObjectKey);
        } else {
            logger.info("文件复制失败");
        }
    }

    public String getDirByPath(String path) {
        if(path.lastIndexOf("/")>-1) {
            return path.substring(0, path.lastIndexOf("/"));
        } else {
            return null;
        }
    }

    public BufferedImage getImageMini(InputStream inputStream, int w) throws Exception {
        BufferedImage img = ImageIO.read(inputStream);
        //获取图片的长和宽
        int width = img.getWidth();
        int height = img.getHeight();
        int tempw = 0;
        int temph = 0;
        if(width>height){
            tempw = w;
            temph = height* w/width;
        }else{
            tempw = w*width/height;
            temph = w;
        }
        Image _img = img.getScaledInstance(tempw, temph, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(tempw, temph, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        return image;
    }

    /**
     * 获取仓库开关
     * @return
     * @throws Exception
     */
    public boolean getDepotFlag() throws Exception {
        boolean depotFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getDepotFlag();
            if(("1").equals(flag)) {
                depotFlag = true;
            }
        }
        return depotFlag;
    }

    /**
     * 获取客户开关
     * @return
     * @throws Exception
     */
    public boolean getCustomerFlag() throws Exception {
        boolean customerFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getCustomerFlag();
            if(("1").equals(flag)) {
                customerFlag = true;
            }
        }
        return customerFlag;
    }

    /**
     * 获取负库存开关
     * @return
     * @throws Exception
     */
    public boolean getMinusStockFlag() throws Exception {
        boolean minusStockFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getMinusStockFlag();
            if(("1").equals(flag)) {
                minusStockFlag = true;
            }
        }
        return minusStockFlag;
    }

    /**
     * 获取更新单价开关
     * @return
     * @throws Exception
     */
    public boolean getUpdateUnitPriceFlag() throws Exception {
        boolean updateUnitPriceFlag = true;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getUpdateUnitPriceFlag();
            if(("0").equals(flag)) {
                updateUnitPriceFlag = false;
            }
        }
        return updateUnitPriceFlag;
    }

    /**
     * 获取超出关联单据开关
     * @return
     * @throws Exception
     */
    public boolean getOverLinkBillFlag() throws Exception {
        boolean overLinkBillFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getOverLinkBillFlag();
            if(("1").equals(flag)) {
                overLinkBillFlag = true;
            }
        }
        return overLinkBillFlag;
    }

    /**
     * 获取强审核开关
     * @return
     * @throws Exception
     */
    public boolean getForceApprovalFlag() throws Exception {
        boolean forceApprovalFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getForceApprovalFlag();
            if(("1").equals(flag)) {
                forceApprovalFlag = true;
            }
        }
        return forceApprovalFlag;
    }

    /**
     * 获取多级审核开关
     * @return
     * @throws Exception
     */
    public boolean getMultiLevelApprovalFlag() throws Exception {
        boolean multiLevelApprovalFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getMultiLevelApprovalFlag();
            if(("1").equals(flag)) {
                multiLevelApprovalFlag = true;
            }
        }
        return multiLevelApprovalFlag;
    }

    /**
     * 获取出入库管理开关
     * @return
     * @throws Exception
     */
    public boolean getInOutManageFlag() throws Exception {
        boolean inOutManageFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getInOutManageFlag();
            if(("1").equals(flag)) {
                inOutManageFlag = true;
            }
        }
        return inOutManageFlag;
    }

    /**
     * 获取移动平均价开关
     * @return
     * @throws Exception
     */
    public boolean getMoveAvgPriceFlag() throws Exception {
        boolean moveAvgPriceFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getMoveAvgPriceFlag();
            if(("1").equals(flag)) {
                moveAvgPriceFlag = true;
            }
        }
        return moveAvgPriceFlag;
    }

    /**
     * 获取客户静态单价开关
     * @return
     * @throws Exception
     */
    public boolean getCustomerStaticPriceFlag() throws Exception {
        boolean customerStaticPriceFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getCustomerStaticPriceFlag();
            if(("1").equals(flag)) {
                customerStaticPriceFlag = true;
            }
        }
        return customerStaticPriceFlag;
    }

    /**
     * Excel导出统一方法
     * @param title
     * @param head
     * @param tip
     * @param arr
     * @param response
     * @throws Exception
     */
    public void exportExcelByParam(String title, String head, String tip, JSONArray arr, HttpServletResponse response) throws Exception {
        List<String> nameList = StringUtil.strToStringList(head);
        String[] names = StringUtil.listToStringArray(nameList);
        List<String[]> objects = new ArrayList<>();
        if (null != arr) {
            for (Object object: arr) {
                List<Object> list = (List<Object>) object;
                String[] objs = new String[names.length];
                for (int i = 0; i < list.size(); i++) {
                    if(null != list.get(i)) {
                        objs[i] = list.get(i).toString();
                    }
                }
                objects.add(objs);
            }
        }
        File file = ExcelUtils.exportObjectsOneSheet(title, tip, names, title, objects);
        ExcelUtils.downloadExcel(file, file.getName(), response);
    }
}