package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.service.app.AppService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/app")
public class AppController {
    private Logger logger = LoggerFactory.getLogger(AppController.class);

    @Resource
    private AppService appService;

    @Resource
    private UserBusinessService userBusinessService;

    @GetMapping(value = "/findDesk")
    public JSONObject findDesk(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        List<App> dockList = appService.findDock();
        JSONArray dockArray = new JSONArray();
        if (null != dockList) {
            for (App app : dockList) {
                JSONObject item = new JSONObject();
                item.put("id", app.getId());
                item.put("title", app.getName());
                item.put("type", app.getType());
                item.put("icon", "../../upload/images/deskIcon/" + app.getIcon());
                item.put("url", app.getUrl());
                item.put("width", app.getWidth());
                item.put("height", app.getHeight());
                item.put("isresize", app.getResize());
                item.put("isopenmax", app.getOpenmax());
                item.put("isflash", app.getFlash());
                dockArray.add(item);
            }
        }
        obj.put("dock",dockArray);

        List<App> deskList = appService.findDesk();
        JSONArray deskArray = new JSONArray();
        if (null != deskList) {
            for (App app : deskList) {
                JSONObject item = new JSONObject();
                item.put("id", app.getId());
                item.put("title", app.getName());
                item.put("type", app.getType());
                item.put("icon", "../../upload/images/deskIcon/" + app.getIcon());
                item.put("url", "../../pages/common/menu.html?appID=" + app.getNumber() + "&id=" + app.getId());
                item.put("width", app.getWidth());
                item.put("height", app.getHeight());
                item.put("isresize", app.getResize());
                item.put("isopenmax", app.getOpenmax());
                item.put("isflash", app.getFlash());
                deskArray.add(item);
            }
        }
        obj.put("desk",deskArray);
        return obj;
    }

    /**
     * 角色对应应用显示
     * @param request
     * @return
     */
    @PostMapping(value = "/findRoleAPP")
    public JSONArray findRoleAPP(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                  HttpServletRequest request) {
        JSONArray arr = new JSONArray();
        try {
            List<App> dataList = appService.findRoleAPP();
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("text", "应用列表");
            outer.put("state", "open");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (App app : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", app.getId());
                    item.put("text", app.getName());
                    //勾选判断1
                    Boolean flag = false;
                    try {
                        flag = userBusinessService.checkIsUserBusinessExist(type, keyId, "[" + app.getId().toString() + "]");
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>>>设置角色对应的应用：类型" + type + " KeyId为： " + keyId + " 存在异常！");
                    }
                    if (flag == true) {
                        item.put("checked", true);
                    }
                    //结束
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            arr.add(outer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}
