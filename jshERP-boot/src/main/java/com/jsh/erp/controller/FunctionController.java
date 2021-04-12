package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Function;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.functions.FunctionService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ji-sheng-hua  jshERP
 */
@RestController
@RequestMapping(value = "/function")
public class FunctionController {
    private Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Resource
    private FunctionService functionService;

    @Resource
    private UserBusinessService userBusinessService;

    public JSONArray getMenuByFunction(List<Function> dataList, String fc) throws Exception {
        JSONArray dataArray = new JSONArray();
        for (Function function : dataList) {
            JSONObject item = new JSONObject();
            List<Function> newList = functionService.getRoleFunction(function.getNumber());
            item.put("id", function.getId());
            item.put("text", function.getName());
            item.put("icon", function.getIcon());
            item.put("url", function.getUrl());
            //if (Tools.isPluginUrl(function.getUrl())) {
            //    item.put("path", Tools.md5Encryp(function.getUrl()));
            //} else {
            //    item.put("path", function.getUrl());
            //}
            item.put("component", function.getComponent());
            if (newList.size()>0) {
                JSONArray childrenArr = getMenuByFunction(newList, fc);
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

    @PostMapping(value = "/findMenuByPNumber")
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
            List<Function> dataList = functionService.getRoleFunction(pNumber);
            if (dataList.size() != 0) {
                dataArray = getMenuByFunction(dataList, fc);
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

    /**
     * 角色对应功能显示
     * @param request
     * @return
     */
    @GetMapping(value = "/findRoleFunction")
    public JSONArray findRoleFunction(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                 HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<Function> dataListFun = functionService.findRoleFunction("0");
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("key", 1);
            outer.put("value", 1);
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
        if (null != dataList) {
            for (Function function : dataList) {
                JSONObject item = new JSONObject();
                item.put("id", function.getId());
                item.put("key", function.getId());
                item.put("value", function.getId());
                item.put("title", function.getName());
                item.put("attributes", function.getName());
                Boolean flag = false;
                try {
                    flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + function.getId().toString() + "]");
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                }
                if (flag == true) {
                    item.put("checked", true);
                }
                List<Function> funList = functionService.findRoleFunction(function.getNumber());
                if(funList.size()>0) {
                    JSONArray funArr = getFunctionList(funList, type, keyId);
                    item.put("children", funArr);
                    dataArray.add(item);
                } else {
                    dataArray.add(item);
                }
            }
        }
        return dataArray;
    }

    /**
     * 根据id列表查找功能信息
     * @param functionsIds
     * @param request
     * @return
     */
    @GetMapping(value = "/findByIds")
    public BaseResponseInfo findByIds(@RequestParam("functionsIds") String functionsIds,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<Function> dataList = functionService.findByIds(functionsIds);
            JSONObject outer = new JSONObject();
            outer.put("total", dataList.size());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Function function : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", function.getId());
                    item.put("Name", function.getName());
                    item.put("PushBtn", function.getPushBtn());
                    item.put("op", 1);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            res.code = 200;
            res.data = outer;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
