package com.jsh.erp.service.systemConfig;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.SystemConfigMapper;
import com.jsh.erp.datasource.mappers.SystemConfigMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.platformConfig.PlatformConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.FileUtils;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
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
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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

    @Value(value="${file.path}")
    private String filePath;

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
    public List<SystemConfig> select(String companyName, int offset, int rows)throws Exception {
        List<SystemConfig> list=null;
        try{
            list=systemConfigMapperEx.selectByConditionSystemConfig(companyName, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countSystemConfig(String companyName)throws Exception {
        Long result=null;
        try{
            result=systemConfigMapperEx.countsBySystemConfig(companyName);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSystemConfig(JSONObject obj, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(obj.toJSONString(), SystemConfig.class);
        int result=0;
        try{
            result=systemConfigMapper.insertSelective(systemConfig);
            logService.insertLog("系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(systemConfig.getCompanyName()).toString(), request);
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
            logService.insertLog("系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(systemConfig.getCompanyName()).toString(), request);
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
     * @param name  自定义文件名
     * @return
     */
    public String uploadLocal(MultipartFile mf, String bizPath, String name, HttpServletRequest request) {
        try {
            if(StringUtil.isEmpty(bizPath)){
                bizPath = "";
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
            if(orgName.contains(".")){
                if(StringUtil.isNotEmpty(name)) {
                    fileName = name.substring(0, name.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
                } else {
                    fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
                }
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
     * 阿里Oss文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @param name  自定义文件名
     * @return
     */
    public String uploadAliOss(MultipartFile mf, String bizPath, String name, HttpServletRequest request) throws Exception {
        if(StringUtil.isEmpty(bizPath)){
            bizPath = "";
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
        if(orgName.contains(".")){
            if(StringUtil.isNotEmpty(name)) {
                fileName = name.substring(0, name.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            } else {
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            }
        }else{
            fileName = orgName+ "_" + System.currentTimeMillis();
        }
        String filePathStr = StringUtil.isNotEmpty(filePath)? filePath.substring(1):"";
        String objectName = filePathStr + "/" + bizPath + "/" + fileName;
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        byte [] byteArr = mf.getBytes();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
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
}