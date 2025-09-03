package com.jsh.erp.service;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.PlatformConfig;
import com.jsh.erp.datasource.entities.PlatformConfigExample;
import com.jsh.erp.datasource.mappers.PlatformConfigMapper;
import com.jsh.erp.datasource.mappers.PlatformConfigMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.HttpClient;
import com.jsh.erp.utils.PageUtils;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

@Service
public class PlatformConfigService {
    private Logger logger = LoggerFactory.getLogger(PlatformConfigService.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Resource
    private PlatformConfigMapper platformConfigMapper;

    @Resource
    private PlatformConfigMapperEx platformConfigMapperEx;

    public PlatformConfig getPlatformConfig(long id)throws Exception {
        PlatformConfig result=null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = platformConfigMapper.selectByPrimaryKey(id);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<PlatformConfig> getPlatformConfig()throws Exception {
        PlatformConfigExample example = new PlatformConfigExample();
        example.createCriteria();
        List<PlatformConfig> list=null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                list = platformConfigMapper.selectByExample(example);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<PlatformConfig> select(String platformKey)throws Exception {
        List<PlatformConfig> list=null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                PageUtils.startPage();
                list = platformConfigMapperEx.selectByConditionPlatformConfig(platformKey);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertPlatformConfig(JSONObject obj, HttpServletRequest request) throws Exception{
        PlatformConfig platformConfig = JSONObject.parseObject(obj.toJSONString(), PlatformConfig.class);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = platformConfigMapper.insertSelective(platformConfig);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updatePlatformConfig(JSONObject obj, HttpServletRequest request) throws Exception{
        PlatformConfig platformConfig = JSONObject.parseObject(obj.toJSONString(), PlatformConfig.class);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = platformConfigMapper.updateByPrimaryKeySelective(platformConfig);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deletePlatformConfig(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = platformConfigMapper.deleteByPrimaryKey(id);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePlatformConfig(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        PlatformConfigExample example = new PlatformConfigExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = platformConfigMapper.deleteByExample(example);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int updatePlatformConfigByKey(String platformKey, String platformValue)throws Exception {
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                PlatformConfig platformConfig = new PlatformConfig();
                platformConfig.setPlatformValue(platformValue);
                PlatformConfigExample example = new PlatformConfigExample();
                example.createCriteria().andPlatformKeyEqualTo(platformKey);
                result = platformConfigMapper.updateByExampleSelective(platformConfig, example);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public PlatformConfig getInfoByKey(String platformKey)throws Exception {
        PlatformConfig platformConfig = new PlatformConfig();
        try{
            if(platformKey.contains("aliOss") || platformKey.contains("weixin")) {
                platformConfig = null;
            } else {
                PlatformConfigExample example = new PlatformConfigExample();
                example.createCriteria().andPlatformKeyEqualTo(platformKey);
                List<PlatformConfig> list=platformConfigMapper.selectByExample(example);
                if(list!=null && list.size()>0){
                    platformConfig = list.get(0);
                }
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return platformConfig;
    }

    /**
     * 根据key查询平台信息-内部专用方法
     * @param platformKey
     * @return
     * @throws Exception
     */
    public PlatformConfig getPlatformConfigByKey(String platformKey)throws Exception {
        PlatformConfig platformConfig = new PlatformConfig();
        try{
            PlatformConfigExample example = new PlatformConfigExample();
            example.createCriteria().andPlatformKeyEqualTo(platformKey);
            List<PlatformConfig> list=platformConfigMapper.selectByExample(example);
            if(list!=null && list.size()>0){
                platformConfig = list.get(0);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return platformConfig;
    }

    /**
     * 获取微信token信息
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String accessToken = "";
        if(redisService.getCacheObject("weixinToken")==null) {
            //1-获取token
            String weixinToken = getPlatformConfigByKey("weixinUrl").getPlatformValue() + BusinessConstants.WEIXIN_TOKEN;
            String weixinAppid = getPlatformConfigByKey("weixinAppid").getPlatformValue();
            String weixinSecret = getPlatformConfigByKey("weixinSecret").getPlatformValue();
            String url = weixinToken + "?grant_type=client_credential&appid=" + weixinAppid + "&secret=" + weixinSecret;
            JSONObject jsonObject = HttpClient.httpGet(url);
            logger.info("获取到微信token信息:{}", jsonObject);
            if (jsonObject != null) {
                accessToken = jsonObject.getString("access_token");
                Long expiresIn = jsonObject.getLong("expires_in") - 10;
                if (StringUtil.isNotEmpty(accessToken)) {
                    //存redis
                    redisService.storageKeyWithTime("weixinToken", accessToken, expiresIn);
                }
            }
        } else {
            accessToken = redisService.getCacheObject("weixinToken");
        }
        return accessToken;
    }

    /**
     * 发送邮件(该方法将在一个单独的线程中执行)
     * @return
     * @throws Exception
     */
    @Async
    public void sendEmail(String emailFrom, String emailAuthCode, String emailSmtpHost, String toEmail, String emailSubject, String emailBody) {
        // 配置邮件服务器属性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailSmtpHost); // 网易邮箱SMTP服务器
        properties.put("mail.smtp.port", "465"); // SSL端口
        properties.put("mail.smtp.auth", "true"); // 需要认证
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 使用SSL
        properties.put("mail.smtp.socketFactory.port", "465"); // SSL端口
        try {
            // 创建会话
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailFrom, emailAuthCode);
                }
            });
            // 创建邮件
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            // 发送邮件
            Transport.send(message);
            logger.info("邮件发送成功！");
        } catch (Exception e) {
            logger.error("邮件发送失败: " + e.getMessage());
        }
    }
}
