package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji_sheng_hua 华夏ERP
 */
@RestController
@RequestMapping(value = "/userBusiness")
public class UserBusinessController {
    private Logger logger = LoggerFactory.getLogger(UserBusinessController.class);

    @Resource
    private UserBusinessService userBusinessService;

    @GetMapping(value = "/getBasicData")
    public BaseResponseInfo getBasicData(@RequestParam(value = "KeyId") String keyId,
                                         @RequestParam(value = "Type") String type,
                                         HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<UserBusiness> list = userBusinessService.getBasicData(keyId, type);
            Map<String, List> mapData = new HashMap<String, List>();
            mapData.put("userBusinessList", list);
            res.code = 200;
            res.data = mapData;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "查询权限失败";
        }
        return res;
    }

    @GetMapping(value = "/checkIsValueExist")
    public String checkIsValueExist(@RequestParam(value ="type", required = false) String type,
                                   @RequestParam(value ="keyId", required = false) String keyId,
                                   HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Long id = userBusinessService.checkIsValueExist(type, keyId);
        if(id != null) {
            objectMap.put("id", id);
        } else {
            objectMap.put("id", null);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 更新角色的按钮权限
     * @param userBusinessId
     * @param btnStr
     * @param request
     * @return
     */
    @PostMapping(value = "/updateBtnStr")
    public BaseResponseInfo updateBtnStr(@RequestParam(value ="userBusinessId", required = false) Long userBusinessId,
                                    @RequestParam(value ="btnStr", required = false) String btnStr,
                                    HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int back = userBusinessService.updateBtnStr(userBusinessId, btnStr);
            if(back > 0) {
                res.code = 200;
                res.data = "成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "查询权限失败";
        }
        return res;
    }
}
