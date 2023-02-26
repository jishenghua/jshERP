package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Function;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.functions.FunctionService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji-sheng-hua  jshERP
 */
@RestController
@RequestMapping(value = "/function")
@Api(tags = {"功能管理"})
public class FunctionController {
    private Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Resource
    private FunctionService functionService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    @GetMapping(value = "/checkIsNumberExist")
    @ApiOperation(value = "检查编号是否存在")
    public String checkIsNumberExist(@RequestParam Long id,
                                     @RequestParam(value ="number", required = false) String number,
                                     HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = functionService.checkIsNumberExist(id, number);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 根据父编号查询菜单
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findMenuByPNumber")
    @ApiOperation(value = "根据父编号查询菜单")
    public JSONArray findMenuByPNumber(@RequestBody JSONObject jsonObject,
                              HttpServletRequest request)throws Exception {
        String pNumber = jsonObject.getString("pNumber");
        String userId = jsonObject.getString("userId");
        //存放数据json数组
        JSONArray dataArray = new JSONArray();
        try {
            Long roleId = 0L;
            String fc = "";
            List<UserBusiness> roleList = userBusinessService.getBasicData(userId, "UserRole");
            if(roleList!=null && roleList.size()>0){
                String value = roleList.get(0).getValue();
                if(StringUtil.isNotEmpty(value)){
                    String roleIdStr = value.replace("[", "").replace("]", "");
                    roleId = Long.parseLong(roleIdStr);
                }
            }
            //当前用户所拥有的功能列表，格式如：[1][2][5]
            List<UserBusiness> funList = userBusinessService.getBasicData(roleId.toString(), "RoleFunctions");
            if(funList!=null && funList.size()>0){
                fc = funList.get(0).getValue();
            }
            //获取系统配置信息-是否开启多级审核
            String approvalFlag = "0";
            List<SystemConfig> list = systemConfigService.getSystemConfig();
            if(list.size()>0) {
                approvalFlag = list.get(0).getMultiLevelApprovalFlag();
            }
            List<Function> dataList = functionService.getRoleFunction(pNumber);
            if (dataList.size() != 0) {
                dataArray = getMenuByFunction(dataList, fc, approvalFlag);
                //增加首页菜单项
                JSONObject homeItem = new JSONObject();
                homeItem.put("id", 0);
                homeItem.put("text", "首页");
                homeItem.put("icon", "home");
                homeItem.put("url", "/dashboard/analysis");
                homeItem.put("component", "/layouts/TabLayout");
                dataArray.add(0,homeItem);
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>>>>>>>>>>>查找异常", e);
        }
        return dataArray;
    }

    public JSONArray getMenuByFunction(List<Function> dataList, String fc, String approvalFlag) throws Exception {
        JSONArray dataArray = new JSONArray();
        for (Function function : dataList) {
            //如果关闭多级审核，遇到任务审核菜单直接跳过
            if("0".equals(approvalFlag) && "/workflow".equals(function.getUrl())) {
                continue;
            }
            JSONObject item = new JSONObject();
            List<Function> newList = functionService.getRoleFunction(function.getNumber());
            item.put("id", function.getId());
            item.put("text", function.getName());
            item.put("icon", function.getIcon());
            item.put("url", function.getUrl());
            item.put("component", function.getComponent());
            if (newList.size()>0) {
                JSONArray childrenArr = getMenuByFunction(newList, fc, approvalFlag);
                if(childrenArr.size()>0) {
                    item.put("children", childrenArr);
                    dataArray.add(item);
                }
            } else {
                if (fc.indexOf("[" + function.getId().toString() + "]") != -1) {
                    dataArray.add(item);
                }
            }
        }
        return dataArray;
    }

    /**
     * 角色对应功能显示
     * @param request
     * @return
     */
    @GetMapping(value = "/findRoleFunction")
    @ApiOperation(value = "角色对应功能显示")
    public JSONArray findRoleFunction(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                 HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<Function> dataListFun = functionService.findRoleFunction("0");
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 0);
            outer.put("key", 0);
            outer.put("value", 0);
            outer.put("title", "功能列表");
            outer.put("attributes", "功能列表");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataListFun) {
                //根据条件从列表里面移除"系统管理"
                List<Function> dataList = new ArrayList<>();
                for (Function fun : dataListFun) {
                    String token = request.getHeader("X-Access-Token");
                    Long tenantId = Tools.getTenantIdByToken(token);
                    if (tenantId!=0L) {
                        if(!("系统管理").equals(fun.getName())) {
                            dataList.add(fun);
                        }
                    } else {
                        //超管
                        dataList.add(fun);
                    }
                }
                dataArray = getFunctionList(dataList, type, keyId);
                outer.put("children", dataArray);
            }
            arr.add(outer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public JSONArray getFunctionList(List<Function> dataList, String type, String keyId) throws Exception {
        JSONArray dataArray = new JSONArray();
        //获取权限信息
        String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
        if (null != dataList) {
            for (Function function : dataList) {
                JSONObject item = new JSONObject();
                item.put("id", function.getId());
                item.put("key", function.getId());
                item.put("value", function.getId());
                item.put("title", function.getName());
                item.put("attributes", function.getName());
                List<Function> funList = functionService.findRoleFunction(function.getNumber());
                if(funList.size()>0) {
                    JSONArray funArr = getFunctionList(funList, type, keyId);
                    item.put("children", funArr);
                    dataArray.add(item);
                } else {
                    Boolean flag = ubValue.contains("[" + function.getId().toString() + "]");
                    item.put("checked", flag);
                    dataArray.add(item);
                }
            }
        }
        return dataArray;
    }

    /**
     * 根据id列表查找功能信息
     * @param roleId
     * @param request
     * @return
     */
    @GetMapping(value = "/findRoleFunctionsById")
    @ApiOperation(value = "根据id列表查找功能信息")
    public BaseResponseInfo findByIds(@RequestParam("roleId") Long roleId,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<UserBusiness> list = userBusinessService.getBasicData(roleId.toString(), "RoleFunctions");
            if(null!=list && list.size()>0) {
                //按钮
                Map<Long,String> btnMap = new HashMap<>();
                String btnStr = list.get(0).getBtnStr();
                if(StringUtil.isNotEmpty(btnStr)) {
                    JSONArray btnArr = JSONArray.parseArray(btnStr);
                    for(Object obj: btnArr) {
                        JSONObject btnObj = JSONObject.parseObject(obj.toString());
                        if(btnObj.get("funId")!=null && btnObj.get("btnStr")!=null) {
                            btnMap.put(btnObj.getLong("funId"), btnObj.getString("btnStr"));
                        }
                    }
                }
                //菜单
                String funIds = list.get(0).getValue();
                funIds = funIds.substring(1, funIds.length() - 1);
                funIds = funIds.replace("][",",");
                List<Function> dataList = functionService.findByIds(funIds);
                JSONObject outer = new JSONObject();
                outer.put("total", dataList.size());
                //存放数据json数组
                JSONArray dataArray = new JSONArray();
                if (null != dataList) {
                    for (Function function : dataList) {
                        JSONObject item = new JSONObject();
                        item.put("id", function.getId());
                        item.put("name", function.getName());
                        item.put("pushBtn", function.getPushBtn());
                        item.put("btnStr", btnMap.get(function.getId()));
                        dataArray.add(item);
                    }
                }
                outer.put("rows", dataArray);
                res.code = 200;
                res.data = outer;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
