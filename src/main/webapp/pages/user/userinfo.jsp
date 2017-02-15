<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
  		<div id="position" class="easyui-panel" title="当前位置：系统管理 &gt; 个人资料" collapsible="false" closable="false"/>
	    	<div id="userDlg" class="easyui-panel" title="个人资料" style="height:400px;padding:10px 20px;background-color:#EAF2FD "
	            buttons="#dlg-buttons" iconCls="icon-comment" collapsible="true" closable="false">
		        <form id="usernameFM" method="post" novalidate action="<%=path %>/user/update.action">
		            <div class="fitem" style="padding:5px">
		                <label id="usernameLabel">用户名称&nbsp;&nbsp;</label>
		                <input name="username" id="username" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" value="${sessionScope.user.username}" style="width: 230px;height: 20px"/>
		            </div>
		            
		            <div class="fitem" style="padding:5px">
		                <label id="departmentLabel">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;</label>
		                <input name="department" id="department" style="width: 230px;height: 20px" value="${sessionScope.user.department}"/>
		            </div>
		            <div class="fitem" style="padding:5px">
		                <label id="positionLabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;</label>
		                <input name="position" id="position" style="width: 230px;height: 20px" value="${sessionScope.user.position}"/>
		            </div>
		            <div class="fitem" style="padding:5px">
		                <label id="phonenumLabel">联系电话&nbsp;&nbsp;</label>
		                <input name="phonenum" id="phonenum" class="easyui-numberbox" style="width: 230px;height: 20px" value="${sessionScope.user.phonenum}"/>
		            </div>
		            <div class="fitem" style="padding:5px">
		                <label id="emailLabel">电子邮箱&nbsp;&nbsp;</label>
		                <input name="email" id="email" class="easyui-validatebox" validType="email" style="width: 230px;height: 20px" value="${sessionScope.user.email}"/>
		            </div>
		            <div class="fitem" style="padding:5px">
		                <label id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
		                <textarea name="description" id="description" rows="3" cols="3" style="width: 230px;font-size: 12px;">${sessionScope.user.description}</textarea>
		            </div>
		            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
		            <input type="hidden" name="userID" id="userID" value="${sessionScope.user.id}"/>
		        </form>
		        <div style="clear: both;">&nbsp;</div>
		        <div id="dlg-buttons">
			        <a href="javascript:void(0)" id="saveusername" class="easyui-linkbutton" iconCls="icon-save">修改</a>
			        <a href="javascript:void(0)" id="cancelusername" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			    </div>
		    </div>
	    </div>
	    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				browserFit();
				$("#userDlg").panel({height:webH-35});
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
				{
					$("#usernameLabel").empty().append("用户名称&nbsp;&nbsp;");
					$("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;");
					$("#positionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;");
					$("#phonenumLabel").empty().append("联系电话&nbsp;&nbsp;");
					$("#emailLabel").empty().append("电子邮箱&nbsp;&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
				}
				else
				{
					$("#usernameLabel").empty().append("用户名称&nbsp;");
					$("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;");
					$("#positionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;");
					$("#phonenumLabel").empty().append("联系电话&nbsp;");
					$("#emailLabel").empty().append("电子邮箱&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
				}
			}
			
			$("#cancelusername").unbind().bind({
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
			    if(k == "13"&&(obj.id=="username" || obj.id=="department"|| obj.id=="phonenum" 
			    	|| obj.id=="email" || obj.id=="description" || obj.id=="position"))
			    {  
			        $("#saveusername").click();
			    }  
			}); 
			
			//保存用户信息
			$("#saveusername").unbind().bind({
				click:function()
				{
					if(checkusernameName())
						return;
						
					var reg = /^([0-9])+$/;
					var phonenum = $.trim($("#phonenum").val());
					if(phonenum.length>0 && !reg.test(phonenum))
					{
						$.messager.alert('提示','电话号码只能是数字','info');
						$("#phonenum").val("").focus();
						return;
					}
					
					$('#usernameFM').form('submit',{
		                onSubmit: function()
		                {
		                    return $(this).form('validate');
		                },
		                success: function(result)
		                {
		                    var result = eval('('+result+')');
		                    if (!result)
		                    {
		                        $.messager.show({
		                            title: '错误提示',
		                            msg: '保存用户信息失败，请稍后重试!'
		                        });
		                    } 
		                    else 
		                    {
		                        $.messager.alert('提示','修改个人资料成功！','info');
		                    }
		                }
		            });	
				}
			});
			//检查用户 名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkusernameName()
	        {
	        	var username = $.trim($("#username").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(username.length > 0 && username != '${sessionScope.user.username}')
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/user/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							userID : '${sessionScope.user.id}',
							username : username
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','用户名称已经存在','info');
								//alert("用户名称已经存在");
								//$("#username").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查用户名称是否存在异常，请稍后再试！','error');
							return;
						}
					});	
        		}
        		return flag;
	        }
		</script>
	</body>
</html>