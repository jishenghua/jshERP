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
            String fc = hasFunctions; //当前用户所拥有的功能列表，格式如：[1][2][5]
            List<Functions> dataList = functionsService.getRoleFunctions(pNumber);
            if (null != dataList) {
                for (Functions functions : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", functions.getId());
                    List<Functions> dataList1 = functionsService.getRoleFunctions(functions.getNumber());
                    JSONArray dataArray1 = new JSONArray();
                    if (dataList1.size() != 0) {
                        item.put("text", functions.getName()); //是目录就没链接
                        item.put("icon", functions.getIcon());
                        for (Functions functions1 : dataList1) {
                            item.put("state", "open");   //如果不为空，节点展开
                            JSONObject item1 = new JSONObject();
                            List<Functions> dataList2 = functionsService.getRoleFunctions(functions1.getNumber());
                            if (fc.indexOf("[" + functions1.getId().toString() + "]") != -1 || dataList2.size() != 0) {
                                item1.put("id", functions1.getId());
                                JSONArray dataArray2 = new JSONArray();
                                if (dataList2.size() != 0) {
                                    item1.put("text", functions1.getName());//是目录就没链接
                                    item1.put("icon", functions1.getIcon());
                                    for (Functions functions2 : dataList2) {
                                        item1.put("state", "closed");   //如果不为空，节点不展开
                                        JSONObject item2 = new JSONObject();
                                        List<Functions> dataList3 = functionsService.getRoleFunctions(functions2.getNumber());
                                        if (fc.indexOf("[" + functions2.getId().toString() + "]") != -1 || dataList3.size() != 0) {
                                            item2.put("id", functions2.getId());
                                            JSONArray dataArray3 = new JSONArray();
                                            if (dataList3.size() != 0) {
                                                item2.put("text", functions2.getName());//是目录就没链接
                                                item2.put("icon", functions2.getIcon());
                                                for (Functions functions3 : dataList3) {
                                                    item2.put("state", "closed");   //如果不为空，节点不展开
                                                    JSONObject item3 = new JSONObject();
                                                    item3.put("id", functions3.getId());
                                                    item3.put("text", functions3.getName());
                                                    item3.put("icon", functions3.getIcon());
                                                    //
                                                    dataArray3.add(item3);
                                                    item2.put("children", dataArray3);
                                                }
                                            } else {
                                                //不是目录，有链接
                                                item2.put("text", functions2.getName());
                                                item2.put("icon", functions2.getIcon());
                                                item2.put("url", functions2.getUrl());
                                                dataArray2.add(item2);
                                                item1.put("children", dataArray2);
                                            }
                                        } else {
                                            //不是目录，有链接
                                        }
                                    }
                                } else {
                                    //不是目录，有链接
                                    item1.put("text", functions1.getName());
                                    item1.put("icon", functions1.getIcon());
                                    item1.put("url", functions1.getUrl());
                                    dataArray1.add(item1);
                                    item.put("children", dataArray1);
                                }
                            } else {
                                //不是目录，有链接
                            }
                        }
                    } else {
                        //不是目录，有链接
                        item.put("text", functions.getName());
                        item.put("icon", functions.getIcon());
                        item.put("url", functions.getUrl());
                    }
                    dataArray.add(item);
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>>>>>>>>>>>查找应用异常", e);
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
                        loginName = user.getLoginame();
                    }
                    if(("admin").equals(loginName)) {
                        dataList.add(fun);
                    } else {
                        if(!("系统管理").equals(fun.getName())) {
                            dataList.add(fun);
                        }
                    }
                }

                //筛选功能列表
                for (Functions functions : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", functions.getId());
                    item.put("text", functions.getName());

                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + functions.getId().toString() + "]");
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    //结束

                    List<Functions> dataList1 = functionsService.findRoleFunctions(functions.getNumber());
                    JSONArray dataArray1 = new JSONArray();
                    if (null != dataList1) {

                        for (Functions functions1 : dataList1) {
                            item.put("state", "open");   //如果不为空，节点不展开
                            JSONObject item1 = new JSONObject();
                            item1.put("id", functions1.getId());
                            item1.put("text", functions1.getName());

                            //勾选判断2
                            //Boolean flag = false;
                            try {
                                flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + functions1.getId().toString() + "]");
                            } catch (Exception e) {
                                logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                            }
                            if (flag == true) {
                                item1.put("checked", true);
                            }
                            //结束

                            List<Functions> dataList2 = functionsService.findRoleFunctions(functions1.getNumber());
                            JSONArray dataArray2 = new JSONArray();
                            if (null != dataList2) {

                                for (Functions functions2 : dataList2) {
                                    item1.put("state", "closed");   //如果不为空，节点不展开
                                    JSONObject item2 = new JSONObject();
                                    item2.put("id", functions2.getId());
                                    item2.put("text", functions2.getName());

                                    //勾选判断3
                                    //Boolean flag = false;
                                    try {
                                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + functions2.getId().toString() + "]");
                                    } catch (Exception e) {
                                        logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                                    }
                                    if (flag == true) {
                                        item2.put("checked", true);
                                    }
                                    //结束

                                    List<Functions> dataList3 = functionsService.findRoleFunctions(functions2.getNumber());
                                    JSONArray dataArray3 = new JSONArray();
                                    if (null != dataList3) {

                                        for (Functions functions3 : dataList3) {
                                            item2.put("state", "closed");   //如果不为空，节点不展开
                                            JSONObject item3 = new JSONObject();
                                            item3.put("id", functions3.getId());
                                            item3.put("text", functions3.getName());

                                            //勾选判断4
                                            //Boolean flag = false;
                                            try {
                                                flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + functions3.getId().toString() + "]");
                                            } catch (Exception e) {
                                                logger.error(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                                            }
                                            if (flag == true) {
                                                item3.put("checked", true);
                                            }
                                            //结束

                                            dataArray3.add(item3);
                                            item2.put("children", dataArray3);
                                        }
                                    }

                                    dataArray2.add(item2);
                                    item1.put("children", dataArray2);
                                }
                            }

                            dataArray1.add(item1);
                            item.put("children", dataArray1);
                        }

                    }
                    dataArray.add(item);
                }
                outer.put("children", dataArray);
                arr.add(outer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
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
