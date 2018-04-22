package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.UserBusiness;
import com.jsh.model.vo.basic.UserBusinessModel;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.PageUtil;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 权限关系管理
 * @author jishenghua  qq:752718920
 */
@SuppressWarnings("serial")
public class UserBusinessAction extends BaseAction<UserBusinessModel> {
    private UserBusinessIService userBusinessService;
    private UserBusinessModel model = new UserBusinessModel();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getBasicData() {
        Map<String, List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            Map<String, Object> condition = pageUtil.getAdvSearch();
            condition.put("KeyId_s_eq", model.getKeyId());
            condition.put("Type_s_eq", model.getType());
            userBusinessService.find(pageUtil);
            mapData.put("userBusinessList", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找UserBusiness信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }

    /*
     * 测试hql语句的写法
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getceshi() {
        Map<String, List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            Map<String, Object> condition = pageUtil.getAdvSearch();
            condition.put("Type_s_eq", model.getType());
            userBusinessService.find(pageUtil, "ceshi");
            mapData.put("userBusinessList", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找UserBusiness信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }

    /**
     * 增加UserBusiness
     *
     * @return
     */
    public void create() {
        Log.infoFileSync("==================开始调用增加UserBusiness信息方法create()===================");
        Boolean flag = false;
        try {
            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setType(model.getType());
            userBusiness.setKeyId(model.getKeyId());
            userBusiness.setValue(model.getValue());
            userBusinessService.create(userBusiness);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加UserBusiness信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加UserBusiness信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加UserBusiness", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加UserBusiness为  " + model.getType() + " " + tipMsg + "！", "增加UserBusiness" + tipMsg));
        Log.infoFileSync("==================结束调用增加UserBusiness方法create()===================");
    }

    /**
     * 更新UserBusiness
     *
     * @return
     */
    public void update() {
        Boolean flag = false;
        Long id = 0l;
        try {
            PageUtil<UserBusiness> pageUtil = new PageUtil<UserBusiness>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition_RoleAPP());
            userBusinessService.find(pageUtil);
            List<UserBusiness> dataList = pageUtil.getPageList();
            if (null != dataList) {
                for (UserBusiness userBusiness : dataList) {
                    id = userBusiness.getId();
                }
                UserBusiness userBusiness = userBusinessService.get(id);
                userBusiness.setType(model.getType());
                userBusiness.setKeyId(model.getKeyId());
                userBusiness.setValue(model.getValue());
                userBusinessService.update(userBusiness);
            }

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改UserBusiness的ID为 ： " + id + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改UserBusiness回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新UserBusiness", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新UserBusiness的ID为  " + id + " " + tipMsg + "！", "更新UserBusiness" + tipMsg));
    }

    /**
     * 更新角色的按钮权限
     *
     * @return
     */
    public void updateBtnStr() {
        Boolean flag = false;
        try {
            UserBusiness userBusiness = userBusinessService.get(model.getUserBusinessID());
            userBusiness.setType(userBusiness.getType());
            userBusiness.setKeyId(userBusiness.getKeyId());
            userBusiness.setValue(userBusiness.getValue());
            userBusiness.setBtnStr(model.getBtnStr());
            userBusinessService.update(userBusiness);
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改角色按钮权限的ID为 ： " + model.getUserBusinessID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改功能回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新角色按钮权限", model.getClientIp(),
                new Timestamp(System.currentTimeMillis()), tipType,
                "角色按钮权限的ID为  " + model.getUserBusinessID() + " " + tipMsg + "！", "更新角色按钮权限" + tipMsg));
    }

    /**
     * 拼接搜索条件-RoleAPP
     *
     * @return
     */
    private Map<String, Object> getCondition_RoleAPP() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("Type_s_eq", model.getType());
        condition.put("KeyId_s_eq", model.getKeyId());
        return condition;
    }

    /**
     * 检查角色对应应用/功能是否存在
     */
    public void checkIsValueExist() {
        Boolean flag = false;
        try {
            flag = userBusinessService.checkIsValueExist("Type", model.getType(), "KeyId", model.getKeyId());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查角色对应应用/功能的类型为：" + model.getType() + " KeyId为： " + model.getKeyId() + " 是否存在异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查角色对应应用/功能的类型为：" + model.getType() + " KeyId为： " + model.getKeyId() + " 是否存在异常！", e);
            }
        }
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public UserBusinessModel getModel() {
        return model;
    }

    public void setUserBusinessService(UserBusinessIService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }
}
