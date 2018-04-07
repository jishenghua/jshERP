package com.jsh.base;

import com.jsh.model.po.Basicuser;
import com.jsh.service.basic.LogIService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * struts2工具类
 *
 * @author jishenghua qq752718920
 * struts2 base action 一些常用方法获取
 */
@SuppressWarnings("serial")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    public LogIService logService;

    /**
     * 操作日志使用 是否成功表示
     */
    public String tipMsg = "成功";

    /**
     * 操作日志使用 是否成功表示 0 ==成功 1==失败
     */
    public short tipType = 0;

    /**
     * 获取session
     *
     * @return
     */
    public static Map<String, Object> getSession() {
        return ActionContext.getContext().getSession();
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 获取response
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public void setLogService(LogIService logService) {
        this.logService = logService;
    }

    /**
     * 添加错误信息
     *
     * @param anErrorMessage
     */
    public void addActionError(String anErrorMessage) {
        super.addActionError(anErrorMessage);
    }

    /**
     * 添加消息
     *
     * @param aMessage
     */
    public void addActionMessage(String aMessage) {
        clearErrorsAndMessages();
        super.addActionMessage(aMessage);
    }

    /**
     * 添加字段错误
     *
     * @param fieldName
     * @param errorMessage
     */
    public void addFieldError(String fieldName, String errorMessage) {
        clearErrorsAndMessages();
        super.addFieldError(fieldName, errorMessage);
    }

    /**
     * 登录用户信息
     *
     * @return 登录用户对象
     */
    public Basicuser getUser() {
        return (Basicuser) getSession().get("user");
    }

    /**
     * 回写客户端数据
     *
     * @throws IOException
     */
    public void toClient(String jsonData) throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(jsonData);
    }
}
