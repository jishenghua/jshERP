<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资产管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/jdigiclock/css/jquery.jdigiclock.css"/>
    <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/jdigiclock/lib/jquery.jdigiclock.js"></script>
    <!-- 图片上次预览js开始 -->
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/imgpreview/imgup.css"/>
    <script type="text/javascript" src="<%=path %>/js/imgpreview/imagepreview.js"></script>
    <script type="text/javascript" src="<%=path %>/js/imgpreview/jquery.crop.js"></script>
    <!-- 图片上次预览js结束 -->

    <style type="text/css">
        ::-moz-focus-inner {
            padding: 0;
            border: 0;
        }

        .ui_button,
        .ui_fileup {
            vertical-align: middle;
            border: 1px solid #ccc;
            display: inline-block;
            padding: 5px 10px;
            background: #FFF;
            font-size: 12px;
            *overflow: visible;
            *display: inline;
            *zoom: 1;
        }

        .ui_fileup {
            position: relative;
            overflow: hidden;
        }

        .ui_fileup input {
            filter: alpha(opacity=1);
            position: absolute;
            background: #fff;
            font-size: 60px;
            cursor: default;
            width: auto;
            opacity: 0;
            z-index: 1;
            left: auto;
            right: 0;
            top: 0;
        }

        .avataria .cont {
            text-align: center;
            min-height: 1%;
            margin: 25px;
            width: 302px;
            _zoom: 1;
        }

        .avataria form {
            text-align: left;
            overflow: hidden;
        }

        .avataria .cont:after {
            content: "";
            display: block;
            overflow: hidden;
            height: 0;
            clear: both;
        }

        .thumb {
            float: right;
            height: 120px;
            width: 120px;
        }

        .cropaera {
            clear: both;
        }

        .preview {
            height: 300px;
            width: 300px;
        }
    </style>
</head>
<body>

<!-- <div class="avataria" title="头像上传前预览">
<div class="cont">
<div class="thumb"></div>
<form enctype="application/x-www-form-urlencoded">
  <div class="ui_fileup"><input type="file" id="file" accept="image/*" onerror="alert('请选择一个图片')">浏览</div>
  &nbsp;
  <input type="submit" class="ui_button" value="保存">
  <input name="crop" type="hidden">
</form>
</div>
<div class="cont">
<div id="preview" class="preview"></div>
</div>
</div> -->
<div id="digiclock"></div>
<script type="text/javascript">
    //初始化界面
    $(function () {
        initColck();

        //imagepreview(document.getElementById("file"), document.getElementById("preview"), function(info){
        //alert("文件名:" + info.name + "\r\n图片原始高度:" + info.height + "\r\n图片原始宽度:" + info.width);
        //这里若return false则不预览图片

        //$("#preview").css({
        //background: "none"
        //});

        //$("#preview").crop( function(e){
        //$("input[type='hidden']").val([e.top, e.left, e.height, e.width].toString());
        //}, ".thumb");
//});
    });

    //初始化钟
    function initColck() {
        $('#digiclock').jdigiclock({
            clockImagesPath: "<%=path%>/js/jdigiclock/images/clock/",
            weatherImagesPath: "<%=path%>/js/jdigiclock/images/weather/"
        });
    }
</script>
</body>
</html>