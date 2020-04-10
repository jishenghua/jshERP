package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Functions;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.functions.FunctionsService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ji-sheng-hua  华夏ERP
 */
@RestController
@RequestMapping(value = "/functions")
public class FunctionsController {
    private Logger logger = LoggerFactory.getLogger(FunctionsController.class);

    @Resource
    private FunctionsService functionsService;

    @Resource
    private UserBusinessService userBusinessService;

    @PostMapping(value = "/findMenu")
    public JSONArray findMenu(@RequestParam(value="pNumber") String pNumber,
                              @RequestParam(value="hasFunctions") String hasFunctions,
                              HttpServletRequest request)throws Exception {
        //存放数据json数组
        JSONArray dataArray = new JSONArray();
        try {
            //当前用户所拥有的功能列表，格式如：[1][2][5]
            String fc = hasFunctions;
            List<Functions> dataList = functionsService.getRoleFunctions(pNumber);
            if (dataList.size() != 0) {
                dataArray = getMenuByFunction(dataList, fc);
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>>>>>>>>>>>查找异常", e);
        }
        return dataArray;
    }

    public JSONArray getMenuByFunction(List<Functions> dataList, String fc) throws Exception {
        JSONArray dataArray = new JSONArray();
        for (Functions functions : dataList) {
            JSONObject item = new JSONObject();
            List<Functions> newList = functionsService.getRoleFunctions(functions.getNumber());
            item.put("id", functions.getId());
            item.put("text", functions.getName());
            item.put("icon", functions.getIcon());
            if (newList.size()>0) {
                JSONArray childrenArr = getMenuByFunction(newList, fc);
                if(childrenArr.size()>0) {
                    item.put("children", childrenArr);
                    dataArray.add(item);
                }
            } else {
                item.put("url", functions.getUrl());
                if (fc.indexOf("[" + functions.getId().toString() + "]") != -1) {
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
    @PostMapping(value = "/findRoleFunctions")
    public JSONArray findRoleFunctions(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                 HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<Functions> dataListFun = functionsService.findRoleFunctions("0");
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("text", "功能列表");
            outer.put("state", "open");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataListFun) {
                //根据条件从列表里面移除"系统管理"
                List<Functions> dataList = new ArrayList<Functions>();
                for (Functions fun : dataListFun) {
                    //从session中获取租户id
                    String loginName = null;
                    Object userInfo = request.getSession().getAttribute("user");
                    if(userInfo != null) {
                        User user = (User) userInfo;
                        loginName = user.getLoginName();
                    }
                    if(("admin").equals(loginName)) {
                        dataList.add(fun);
                    } else {
                        if(!("系统管理").equals(fun.getName())) {
                            dataList.add(fun);
                        }
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

    public JSONArray getFunctionList(List<Functions> dataList, String type, String keyId) throws Exception {
        JSONArray dataArray = new JSONArray();
        if (null != dataList) {
            for (Functions functions : dataList) {
                JSONObject item = new JSONObject();
                item.put("id", functions.getId());
                item.put("text", functions.getName());
                Boolean flag = false;
                try {
                    flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + functions.getId().toString() + "]");
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                }
                if (flag == true) {
                    item.put("checked", true);
                }
                List<Functions> funList = functionsService.findRoleFunctions(functions.getNumber());
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
            List<Functions> dataList = functionsService.findByIds(functionsIds);
            JSONObject outer = new JSONObject();
            outer.put("total", dataList.size());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Functions functions : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", functions.getId());
                    item.put("Name", functions.getName());
                    item.put("PushBtn", functions.getPushbtn());
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
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除功能模块信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteFunctionsByIds")
    public Object batchDeleteFunctionsByIds(@RequestParam("ids") String ids) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i= functionsService.batchDeleteFunctionsByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.FUNCTIONS_DELETE_FAILED_CODE,ExceptionConstants.FUNCTIONS_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.FUNCTIONS_DELETE_FAILED_CODE,
                    ExceptionConstants.FUNCTIONS_DELETE_FAILED_MSG);
        }
        return result;
    }
}
