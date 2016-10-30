package com.jsh.filter.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 用户登录session处理类
 * 过滤session是否超时
 * @author  andy
 * @version  [版本号, 2012-3-6]
 * @see  [相关类/方法]
 * @since  
 */
public class UserFilter implements Filter
{
    /**
     * 初始化过滤器  暂不处理
     * 重载方法
     * @param arg0
     * @throws ServletException
     */
  
    public void init(FilterConfig arg0)
        throws ServletException
    {
        
    }
    /**
     * 判断用户session是否存在 不存在则跳转到登录页面
     * 重载方法
     * @param srequest
     * @param sresponse
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) srequest;
        HttpServletResponse response = (HttpServletResponse) sresponse;
        HttpSession session = request.getSession();
//        //定义登录页面不被过滤===后续考虑
//        List<String> notFilterFile = new ArrayList<String>();
//        notFilterFile.add("/login.jsp");
//        notFilterFile.add("/logout.jsp");
//        notFilterFile.add("/css/*");
//        notFilterFile.add("/css/zTreeStyle/*");
//        notFilterFile.add("/css/zTreeStyle/img/*");
//        notFilterFile.add("/css/zTreeStyle/img/diy/*");
        
        //获取工程路径
        String path = request.getContextPath();
        String requestURl = request.getRequestURI();
        
        //提示用户没有权限操作
//        response.setCharacterEncoding("utf-8");
//        PrintWriter pw=response.getWriter();     
//        pw.write("<script language='javascript'>alert('您没有权限进行此项操作！')</script>");
        //session中有值则不进行处理
        if(requestURl.contains("/pages") &&null != session.getAttribute("user"))
            chain.doFilter(request, response);
        else
            response.sendRedirect(path + "/logout.jsp");
    }
    
    /**
     * 销毁过滤器
     */  
    public void destroy()
    {
    
    }
}