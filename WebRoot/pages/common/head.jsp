<%@page import="com.jsh.util.common.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  	<head>
    	<title>资产管理</title>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
   		<link type="text/css" rel="stylesheet" href="<%=path %>/css/css.css" />
		<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
  	</head>
  	<body>
		<div id="header">
			<div id="logo"><img src="<%=path%>/images/windvane.png" height="50" alt="资产管理" title="资产管理"/></div>
<!-- 			<div id="logo"><img src="<%=path%>/images/suma_logo.png" height="45" alt="资产管理系统" title="资产管理系统"/></div> -->
			<div id="nav_top">
				<ul>
					<li><a href="<%=path%>/pages/other/clock.jsp" style="background:url(<%=path %>/images/clock.png) 0 1px no-repeat;color:#333;text-decoration:none;" target="mainFrame"><span id="showNowTime"></span></a></li>
					<li>|</li>
					<li><a href="javascript:void(0)" style="background:url(<%=path %>/images/user_business_boss.png) 0 1px no-repeat;color:#333;text-decoration:none;">用户名：${sessionScope.user.loginame}</a></li>
					<li>|</li>
					<li id="1"><a href="<%=path %>/pages/common/home.jsp" id="navtop_home" target="mainFrame" style="color:#333;text-decoration:none;">首页</a></li>
					<li id="2">|</li>
					<li id="3"><a href="<%=path %>/pages/user/userinfo.jsp" id="person_file" target="mainFrame" style="background:url(<%=path %>/images/comment.png) 0 1px no-repeat;color:#333;text-decoration:none;">个人资料</a></li>
					<li id="4">|</li>
					<li id="5"><a href="<%=path %>/pages/user/password.jsp" target="mainFrame" id="navtop_editpwd" style="background:url(<%=path %>/images/lock_unlock.png) 0 1px no-repeat;color:#333;text-decoration:none;">修改密码</a></li>
					<!--<li>|</li>
					<li><a href="javascript:void(0)" id="navtop_help">帮助</a></li>
					-->
					<li id="6">|</li>
					<li><a href="javascript:void(0)" id="navtop_logout">注销</a></li>
				</ul>
			</div>
		</div>
		<script type="text/javascript">
			$(init); 
			//页面初始化
			function init()
			{
				//初始化时间
				showTime();	
				if('${sessionScope.user.loginame}'!= 'guest')
					$("#1").add("#2").add("#3").add("#4").add("#5").add("#6").show();	
				else
					$("#1").add("#2").add("#3").add("#4").add("#5").add("#6").hide();	
			}
			
			function showDate()
		 	{
				var calendar = new Date();
				var day = calendar.getDay();
				var month = calendar.getMonth();
				var date = calendar.getDate();
				var year = calendar.getYear();
				if (year< 1900)
				{
					year = 1900 + year;
				} 
				var cent = parseInt(year/100);
				var g = year % 19;
				var k = parseInt((cent - 17)/25);
				var i = (cent - parseInt(cent/4) - parseInt((cent - k)/3) + 19*g + 15) % 30;
				i = i - parseInt(i/28)*(1 - parseInt(i/28)*parseInt(29/(i+1))*parseInt((21-g)/11));
				var j = (year + parseInt(year/4) + i + 2 - cent + parseInt(cent/4)) % 7;
				var l = i - j;
				var emonth = 3 + parseInt((l + 40)/44);
				var edate = l + 28 - 31*parseInt((emonth/4));
				emonth--;
				var dayname = new Array ("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
				var monthname =new Array ("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月" );
				var message = "";
				if(getOs().indexOf("MSIE")!= -1)
				{	
					//控制时间和图片之间的距离
					$("#blankSpace").empty().append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");			
					message = year +  "年" + monthname[month]+ date + "日&nbsp;&nbsp;" + dayname[day]+"&nbsp;&nbsp;";
					
				}
				else if(getOs().indexOf("Firefox") != -1)
				{
					$("#blankSpace").empty().append("&nbsp;&nbsp;&nbsp;");
					message = year +  "年 " + monthname[month]+ date + "日&nbsp;&nbsp;" + dayname[day]+"&nbsp;&nbsp;";
				}
				else
				{
					$("#blankSpace").empty().append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					message = year +  "年 " + monthname[month]+ date + "日&nbsp;&nbsp;  " + dayname[day] + "&nbsp;&nbsp;";
				}
				return message;
		 	}
		 	function showTime()
			{
				var Digital=new Date();
			 	var hours=Digital.getHours();
			 	var minutes=Digital.getMinutes();
			 	var seconds=Digital.getSeconds();
			 	if(hours==0)
			 	{			 	
			 		hours="00";
			 	} 
			 	if(hours <= 9 && hours > 0)
			 	{
			 		hours="0"+hours;
			 	}	
			 	if(minutes<=9) 
			 		minutes="0"+minutes;
			 	if(seconds<=9) 
			 		seconds="0"+seconds;
				var myclock=hours+":"+minutes+":"+seconds;
				//定时器
			 	setTimeout("showTime()",1000);
				var message = showDate()+" "+myclock;
				$("#showNowTime").empty().append(message);
			 }
			 //判断浏览器的类型
			 function getOs()  
			 {  
		    	var OsObject = "";  
		   		if(navigator.userAgent.indexOf("MSIE")>0)
				{  
		        	return "MSIE";  
		   		}  
			    else if(navigator.userAgent.indexOf("Firefox")>0)
			    {  
			        return "Firefox";  
			    }  
			    else if(navigator.userAgent.indexOf("Safari")>0) 
			    {  
			        return "Safari";  
			    }   
			    else if(navigator.userAgent.indexOf("Camino")>0)
			    {  
			        return "Camino";  
			    }  
			    else if(navigator.userAgent.indexOf("Gecko/")>0)
			    {  
			        return "Gecko";  
			  	}
			  	else if(navigator.userAgent.indexOf("Opera/")>0)
			    {  
			        return "Opera";  
			  	}
			  	else
			  	{
			  		return "unknown";
			  	}
		   	}  
			//退出系统
		   $("#navtop_logout").bind({
		   		click:function()
		   		{
		   			if(confirm("确认要退出系统吗？"))
		   			{
		   				window.location.href ="<%=path%>/user/logout.action?clientIp=<%=clientIp%>" ;
		   			}
		   		}
		   });	
		</script>
	</body>
</html>