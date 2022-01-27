package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Tenant;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.service.tenant.TenantService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji_sheng_hua 华夏erp
 */
@RestController
@RequestMapping(value = "/tenant")
@Api(tags = {"租户管理"})
public class TenantController {
    private Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Resource
    private TenantService tenantService;

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    @ApiOperation(value = "批量设置状态")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = tenantService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }
}
