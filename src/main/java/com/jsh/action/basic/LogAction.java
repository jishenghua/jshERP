package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.LogModel;
import com.jsh.service.basic.UserIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *日志管理
 * @author jishenghua  qq:7-5-2-7-1-8-9-2-0
 */
@SuppressWarnings("serial")
public class LogAction extends BaseAction<LogModel> {
    private LogModel model = new LogModel();
    private UserIService userService;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getBasicData() {
        Map<String, List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            Map<String, Object> condition = pageUtil.getAdvSearch();
            condition.clear();
            condition.put("ismanager_n_eq", 0);
            userService.find(pageUtil);
            mapData.put("userList", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }

    /**
     * 删除日志
     *
     * @return
     */
    public String delete() {
        Log.infoFileSync("====================开始调用删除日志信息方法delete()================");
        try {
            logService.delete(model.getLogID());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getLogID() + "  的日志异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除日志", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除日志ID为  " + model.getLogID() + " " + tipMsg + "！", "删除日志" + tipMsg));
        Log.infoFileSync("====================结束调用删除日志信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 批量删除指定ID日志
     *
     * @return
     */
    public String batchDelete() {
        try {
            logService.batchDelete(model.getLogIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除日志ID为：" + model.getLogIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除日志", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除日志ID为  " + model.getLogIDs() + " " + tipMsg + "！", "批量删除日志" + tipMsg));
        return SUCCESS;
    }

    /**
     * 查找日志信息
     *
     * @return
     */
    public void findBy() {
        try {
            PageUtil<Logdetails> pageUtil = new PageUtil<Logdetails>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            logService.find(pageUtil);
            List<Logdetails> dataList = pageUtil.getPageList();

            //开始拼接json数据
//            {"total":28,"rows":[
//                {"productid":"AV-CB-01","attr1":"Adult Male","itemid":"EST-18"}
//            ]}
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Logdetails log : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", log.getId());
                    item.put("clientIP", log.getClientIp());
                    item.put("details", log.getContentdetails());
                    item.put("createTime", Tools.getCenternTime(log.getCreatetime()));
                    item.put("operation", log.getOperation());
                    item.put("remark", log.getRemark());
                    item.put("status", log.getStatus() == 0 ? "成功" : "失败");
                    item.put("statusShort", log.getStatus());
                    item.put("username", log.getUser() == null ? "" : log.getUser().getUsername());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找日志信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询日志信息结果异常", e);
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
        condition.put("user.id_n_eq", model.getUsernameID());
        condition.put("createtime_s_gteq", model.getBeginTime());
        condition.put("createtime_s_lteq", model.getEndTime());
        condition.put("operation_s_like", model.getOperation());
        condition.put("clientIp_s_like", model.getClientIp());
        condition.put("status_n_eq", model.getStatus());
        condition.put("contentdetails_s_like", model.getContentdetails());
        condition.put("remark_s_like", model.getRemark());
        condition.put("createtime_s_order", "desc");
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    public void setUserService(UserIService userService) {
        this.userService = userService;
    }

    @Override
    public LogModel getModel() {
        return model;
    }
}
