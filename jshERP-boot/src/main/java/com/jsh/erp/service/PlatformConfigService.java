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
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
     * 发送微信订阅消息(该方法将在一个单独的线程中执行)
     * @return
     * @throws Exception
     */
    @Async
    public void sendSubscribeMessage(String accessToken, String weixinUrl, String platformName, String templateId, String page, String openId) throws Exception {
        String weixinMessageSend = weixinUrl + BusinessConstants.WEIXIN_MESSAGE_SEND;
        String url = weixinMessageSend + "?access_token=" + accessToken;
        JSONObject paramObj = new JSONObject();
        paramObj.put("template_id", templateId);
        if (StringUtil.isNotEmpty(page)) {
            paramObj.put("page", page);
        }
        paramObj.put("touser", openId);
        JSONObject dataObj = new JSONObject();
        //登录状态
        JSONObject phraseObj = new JSONObject();
        //登陆方式
        JSONObject thing2Obj = new JSONObject();
        //登陆应用
        JSONObject thing3Obj = new JSONObject();
        //登录时间
        JSONObject time4Obj = new JSONObject();
        //备注
        JSONObject thing5Obj = new JSONObject();
        phraseObj.put("value", "已登录");
        thing2Obj.put("value", "账号登录");
        thing3Obj.put("value", platformName);
        time4Obj.put("value", Tools.getCenternTime(new Date()));
        thing5Obj.put("value", "欢迎" + BusinessConstants.DEFAULT_MANAGER + "登录！");
        dataObj.put("phrase1", phraseObj);
        dataObj.put("thing2", thing2Obj);
        dataObj.put("thing3", thing3Obj);
        dataObj.put("time4", time4Obj);
        dataObj.put("thing5", thing5Obj);
        paramObj.put("data", dataObj);
        paramObj.put("miniprogram_state", "formal");
        paramObj.put("lang", "zh_CN");
        String param = paramObj.toJSONString();
        HttpClient.httpPost(url, param);
    }
}
