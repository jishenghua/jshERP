<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资产管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/css.css"/>
</head>
<body>
<div id="foot">Copyright © 2015 季圣华 版权所有 版本号 V1.0</div>
</body>
</html>