<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  	<head>
    	<title>ERP系统</title>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
   		<link type="text/css" rel="stylesheet" href="<%=path %>/css/css.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body id="login_body">
		<form action="admin.html">
		  	<div id="login_main">
<!--		    	<div id="login_logo"><img src="<%=path%>/images/logo.png" height="18" alt="深圳市风向标信息技术有限公司" />&nbsp;资产管理系统</div>-->
		    	<div id="login">
		      		<p id="login_signal"><img src="<%=path%>/images/login_tip.jpg" width="108" height="29" alt="login" /></p>
			      	<p id="txt_username">
			        	<label for="user_name">用户名：</label>
			      	</p>
		      		<p id="txt_userpwd">
		        		<label for="user_vcode" id="user_vcodepwd">密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
		      		</p>
		      		<!-- <p id="txt_vcode">
		        		<label for="user_pwd">验证码:</label>
		      		</p> -->
		      		<input type="text" id="user_name" />
		      		<input type="password" id="user_pwd" />
		      		<%-- <input type="text" id="vcode" />
		      		<p id="vcode_pic"><img src="<%=path%>/images/valid_code.jpg" width="59" height="22" alt="vpic" /></p> --%>
		      		<p id="tip_username" style="display: none"><span class="error_input">用户名不正确</span></p>
		      		<p id="tip_userpwd" style="display: none">密码不能为空</p>
		      		<!-- <p id="tip_vcode">验证码不能为空</p> -->
		      		<input type="button" value=" " id="btn_login" />
		    	</div>
		    	<p id="login_tip">如果忘记密码，请与管理员联系。</p>
		    	<!-- <p id="copyright">Copyright © 2015  季圣华  jishenghua  V1.0</p> -->
		  	</div>
		</form>
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				 //进入页面聚焦在username输入框,方便 用户输入
				$("#user_name").focus();
				browserFit();	
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
					$("#user_vcodepwd").empty().append("密&nbsp;&nbsp;&nbsp;&nbsp;码：");
				else
					$("#user_vcodepwd").empty().append("密&nbsp;&nbsp;&nbsp;&nbsp;码：");
			}
			
			//初始化键盘enter事件
			$(document).keydown(function(event)
			{  
			   	//兼容 IE和firefox 事件 
			    var e = window.event || event;  
			    var k = e.keyCode||e.which||e.charCode;  
			    //兼容 IE,firefox 兼容  
			    var obj = e.srcElement ? e.srcElement : e.target;  
			    //绑定键盘事件为 id是usename 和password的输入框才可以触发键盘事件 13键盘事件
			    if(k == "13"&&(obj.id=="user_name"||obj.id=="user_pwd"))
			        checkUserInput();
			}); 
			 
			//登录按钮绑定处理事件
			$('#btn_login').bind({
				click:checkUserInput
			});
			
			//检测用户输入数据
			function checkUserInput ()
			{
				var username = $.trim($('#user_name').val());
				var password = $.trim($('#user_pwd').val());
				if(null == username ||0 == username.length)
				{
					$("#user_name").val("").focus();
				    $("#tip_userpwd").hide();
					$("#tip_username").empty().append('<span class="error_input">用户名不能为空</span>').show();
					return;
				}
				else
					$("#tip_username").hide();	
				
				if(null == password || 0 == password.length)
				{
					$("#user_pwd").val("").focus();
					$("#tip_userpwd").empty().append('<span class="error_input">密码不能为空</span>').show();
		    		return;	
				}
				else
					$("#tip_userpwd").hide();	
				if(username != null&& username.length != 0
					&&password != null&&password.length != 0)
				{
					$("#user_name").focus();
					$("#tip_username").hide();
					$("#tip_userpwd").hide();
					$.ajax({
						type:"post",
						url: "<%=path%>/user/login.action",
						dataType: "json",
						data: ({
							loginame : username,
							password : password,
							clientIp:"<%=clientIp%>"
						}),
						success: function (loginInfo){
							var loginInfoTip = loginInfo.showModel.msgTip;
							//用户名不存在，清空输入框并定位到用户名输入框
							if(loginInfoTip.indexOf("user is not exist") != -1)
							{
								$("#user_pwd").val("");
							    $("#user_name").val("").focus();
							    $("#tip_userpwd").hide();
							    $("#tip_username").empty().append('<span class="error_input">用户名不存在</span>').show();
							    return;
							}
							else if(loginInfoTip.indexOf("user password error") != -1)
							{
								$("#user_pwd").val("").focus();
								$("#tip_userpwd").empty().append('<span class="error_input">用户密码错误</span>').show();
							    return;
							}
							else if(loginInfoTip.indexOf("access service error") != -1)
							{
								//$("#user_name").val("").focus();
							    $("#tip_userpwd").hide();
							    $("#tip_username").empty().append('<span class="error_input">后台访问错误</span>').show();
							    return;
							}
							//跳转到用户管理界面
							else if(loginInfoTip.indexOf("user can login") != -1||loginInfoTip == "user already login")
							{
								window.location.href="<%=path%>/login.action";
							}
						},
			    		//此处添加错误处理
			    		error:function()
			    		{
							alert("后台访问错误，请联系管理员！");
						}
					}); 
				}
			}
			
			//处理tip提示
			//定义变量控制密码提示显示
			var temp_value = "";
			$("#user_name").add("#user_pwd").unbind().bind({
				'click keyup':function()
				{
					var value = $.trim($(this).val());
					if(value.length >0)
					{
						$("#tip_username").hide();
						$("#tip_userpwd").hide();	
					}	
				},
				blur:function()
				{
					//兼容 IE和firefox 事件 
				    var e = window.event || event;  
				    //兼容 IE,firefox 兼容  
				    var obj = e.srcElement ? e.srcElement : e.target;  
					var value = $.trim($(this).val());
					if(obj.id=="user_name")
						temp_value = value;
					if(value.length ==0)
					{
						 if(obj.id=="user_name")
							$("#tip_username").empty().append('<span class="error_input">用户名不能为空</span>').show();
					     
					     if(obj.id=="user_pwd" &&temp_value.length>0)
							$("#tip_userpwd").empty().append('<span class="error_input">密码不能为空</span>').show();;
					}
					else
					{
						if(obj.id=="user_pwd"&&value.length>0&&temp_value.length ==0)
						{
							$("#tip_username").show();
							$("#tip_userpwd").hide();	
						}
						else
						{
							$("#tip_username").hide();
							$("#tip_userpwd").hide();	
						}
					}
				}
			});
		</script>
	</body>
</html>