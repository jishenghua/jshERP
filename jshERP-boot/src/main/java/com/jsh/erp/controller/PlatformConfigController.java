package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.PlatformConfig;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.service.platformConfig.PlatformConfigService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua 华夏erp QQ7827-18920
 */
@RestController
@RequestMapping(value = "/platformConfig")
public class PlatformConfigController {
    private Logger logger = LoggerFactory.getLogger(PlatformConfigController.class);

    @Value("${demonstrate.open}")
    private boolean demonstrateOpen;

    @Resource
    private PlatformConfigService platformConfigService;

    @Resource
    private UserService userService;

    private static final String TEST_USER = "jsh";

    /**
     * 获取平台名称
     * @param request
     * @return
     */
    @GetMapping(value = "/getPlatform/name")
    public String getPlatformName(HttpServletRequest request)throws Exception {
        String res;
        try {
            String platformKey = "platform_name";
            PlatformConfig platformConfig = platformConfigService.getPlatformConfigByKey(platformKey);
            res = platformConfig.getPlatformValue();
        } catch(Exception e){
            e.printStackTrace();
            res = "ERP系统";
        }
        return res;
    }

    /**
     * 获取官方网站地址
     * @param request
     * @return
     */
    @GetMapping(value = "/getPlatform/url")
    public String getPlatformUrl(HttpServletRequest request)throws Exception {
        String res;
        try {
            String platformKey = "platform_url";
            PlatformConfig platformConfig = platformConfigService.getPlatformConfigByKey(platformKey);
            res = platformConfig.getPlatformValue();
        } catch(Exception e){
            e.printStackTrace();
            res = "#";
        }
        return res;
    }

    /**
     * 是否显示广告
     * @param request
     * @return
     */
    @GetMapping(value = "/isShowAd")
    public BaseResponseInfo isShowAd(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            User user = userService.getCurrentUser();
            if (demonstrateOpen && TEST_USER.equals(user.getLoginName())) {
                res.code = 200;
                res.data = true;
            } else {
                res.code = 200;
                res.data = false;
            }
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据platformKey更新platformValue
     * @param object
     * @param request
     * @return
     */
    @PostMapping(value = "/updatePlatformConfigByKey")
    public String updatePlatformConfigByKey(@RequestBody JSONObject object,
                                            HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        String platformKey = object.getString("platformKey");
        String platformValue = object.getString("platformValue");
        int res = platformConfigService.updatePlatformConfigByKey(platformKey, platformValue);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 根据platformKey查询信息
     * @param platformKey
     * @param request
     * @return
     */
    @GetMapping(value = "/getPlatformConfigByKey")
    public BaseResponseInfo getPlatformConfigByKey(@RequestParam("platformKey") String platformKey,
                                            HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            PlatformConfig platformConfig = platformConfigService.getPlatformConfigByKey(platformKey);
            res.code = 200;
            res.data = platformConfig;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
