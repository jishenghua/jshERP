package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.SystemConfig;
import com.jsh.model.vo.basic.SystemConfigModel;
import com.jsh.service.basic.SystemConfigIService;
import com.jsh.util.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 系统配置
 * @author jishenghua  qq:7-5-2-7 1-8-9-2-0
 */
@SuppressWarnings("serial")
public class SystemConfigAction extends BaseAction<SystemConfigModel> {
    private SystemConfigIService systemConfigService;
    private SystemConfigModel model = new SystemConfigModel();

    /**
     * 更新系统配置
     *
     * @return
     */
    public void update() {
        Boolean flag = false;
        try {
            SystemConfig sysConfig = systemConfigService.get(model.getId());
            sysConfig.setType(sysConfig.getType());
            sysConfig.setName(sysConfig.getName());
            sysConfig.setValue(model.getValue());
            sysConfig.setDescription(sysConfig.getDescription());
            systemConfigService.update(sysConfig);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改系统配置ID为 ： " + model.getId() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改系统配置回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新系统配置", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新系统配置ID为  " + model.getId() + " " + tipMsg + "！", "更新系统配置" + tipMsg));
    }

    /**
     * 查找系统配置信息
     *
     * @return
     */
    public void findBy() {
        try {
            PageUtil<SystemConfig> pageUtil = new PageUtil<SystemConfig>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition());
            systemConfigService.find(pageUtil);
            List<SystemConfig> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (SystemConfig sysConfig : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", sysConfig.getId());
                    item.put("type", sysConfig.getType());
                    item.put("name", sysConfig.getName());
                    item.put("value", sysConfig.getValue());
                    item.put("description", sysConfig.getDescription());
                    item.put("op", 1);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找系统配置信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询系统配置信息结果异常", e);
        }
    }

    /**
     * 拼接搜索条件
     *
     * @return
     */
    private Map<String, Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("id_s_order", "asc");
        return condition;
    }


    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public SystemConfigModel getModel() {
        return model;
    }

    public void setSystemConfigService(SystemConfigIService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }
}
