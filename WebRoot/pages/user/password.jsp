<%@page import="com.jsh.util.common.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>个人资料</title>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body>
  		<div id="userDlg" class="easyui-panel" title="修改密码" style="height:370px;padding:10px 20px;background-color:#EAF2FD; "
            buttons="#dlg-buttons" iconCls="icon-unlock" collapsible="true" closable="false">
	        <form id="passwordFM" method="post" novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="passwordLabel">原始密码&nbsp;&nbsp;</label>
	                <input type="password" name="orgpassword" id="orgpassword" class="easyui-validatebox" data-options="required:true,validType:'length[5,20]'" style="width: 230px;height: 20px" missingMessage="请输入原始密码"/>
	                <span id="orgTipMsg"></span>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="newPassword">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;</label>
	                <input type="password" name="password" id="password" class="easyui-validatebox" data-options="required:true,validType:'length[5,20]'" style="width: 230px;height: 20px" missingMessage="请填写新密码"/>
	                <span id="orgTipMsg"></span>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="repasswordLabel">确认密码&nbsp;&nbsp;</label>
	                <input type="password" name="repassword" id="repassword" class="easyui-validatebox" style="width: 230px;height: 20px" required="true" class="easyui-validatebox" validType="equals['#password']"  missingMessage="请再次填写新密码" invalidMessage="确认密码与输入密码不一致"/>
	                <span id="tipMsg"></span>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	        <div style="clear: both;">&nbsp;</div>
	        <div id="dlg-buttons">
		        <a href="javascript:void(0)" id="savepassword" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		        <a href="javascript:void(0)" id="cancelpassword" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		    </div>
	    </div>
	    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				$("#orgpassword").focus();
				$('#passwordFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
				browserFit();
				$("#userDlg").panel({height:webH-35});
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
				{
					$("#passwordLabel").empty().append("原始密码&nbsp;&nbsp;");
					$("#newPassword").empty().append("密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;");
					$("#repasswordLabel").empty().append("确认密码&nbsp;&nbsp;");
				}
				else
				{
					$("#passwordLabel").empty().append("原始密码&nbsp;");
					$("#newPassword").empty().append("密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;");
					$("#repasswordLabel").empty().append("确认密码&nbsp;");
				}
			}
			
			$("#cancelpassword").unbind().bind({
				click:function()
				{
					history.go(-1);
				}
			});
			
			//初始化键盘enter事件
			$(document).keydown(function(event)
			{  
			   	//兼容 IE和firefox 事件 
			    var e = window.event || event;  
			    var k = e.keyCode||e.which||e.charCode;  
			    //兼容 IE,firefox 兼容  
			    var obj = e.srcElement ? e.srcElement : e.target;  
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
			    if(k == "13"&&(obj.id=="orgpassword" || obj.id=="password"|| obj.id=="repassword"))
			        $("#savepassword").click();
			}); 
			
			$("#savepassword").unbind().bind({
				click:function()
				{
					if(!$('#passwordFM').form('validate'))
						return;
        			$.ajax({
						type:"post",
						url: "<%=path %>/user/updatePwd.action",
						dataType: "json",
						async :  false,
						data: ({
							userID : '${sessionScope.user.id}',
							password : $.trim($("#password").val()),
							orgpwd : $.trim($("#orgpassword").val())
						}),
						success: function (tipInfo)
						{
							if(1 == tipInfo)
								//回退到上次访问页面
								history.go(-1);
							else if(2 == tipInfo)
							{
								$("#orgTipMsg").empty().append("<font color='red'>原始密码输入错误</font>");
								return;
							}
							else
							{
								$.messager.alert('提示','更新密码异常，请稍后再试！','error');
								return;	
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','更新密码异常，请稍后再试！','error');
							return;
						}
					});	
				}
			});
			
			//处理tip提示
			$("#orgpassword").unbind().bind({
				'click keyup':function()
				{
					$("#orgTipMsg").empty();	
				},
				'blur':function()
				{
					$("#orgTipMsg").empty();
				}
			});
			
		</script>
	</body>
</html>