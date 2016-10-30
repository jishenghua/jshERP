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
    <meta charset="utf-8">
    <title>ERP系统</title>
    <link href="<%=path%>/js/HoorayOS_mini/js/HoorayLibs/hooraylibs.css" rel="stylesheet" />
    <link href="<%=path%>/js/HoorayOS_mini/img/ui/index.css" rel="stylesheet" />
    <style type="text/css">
        .newsTitle {
            text-align: center;
            color: red;
            font-weight: bold;
            font-size: 20px;
        }

        .newsContent {
            padding-top: 10px;
            color: red;
            font-weight: bold;
            height: 140px;
            overflow: hidden;
            line-height: 20px;
        }

        .newsFoot { 
            text-align: right;
            color: red;
            font-weight: bold;
        }

        .newsTime {
            text-align: right;
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="loading"></div>
    <!-- 浏览器升级提示 -->
    <div class="update_browser_box">
        <div class="update_browser">
            <div class="subtitle">您正在使用的IE浏览器版本过低，<br>我们建议您升级或者更换浏览器，以便体验顺畅、兼容、安全的互联网。</div>
            <div class="title">选择一款<span>新</span>浏览器吧</div>
            <div class="browser">
                <a href="http://windows.microsoft.com/zh-CN/internet-explorer/downloads/ie" class="ie" target="_blank" title="ie浏览器">ie浏览器</a>
                <a href="http://www.google.cn/chrome/" class="chrome" target="_blank" title="谷歌浏览器">谷歌浏览器</a>
                <a href="http://www.firefox.com.cn" class="firefox" target="_blank" title="火狐浏览器">火狐浏览器</a>
                <a href="http://www.opera.com" class="opera" target="_blank" title="opera浏览器">opera浏览器</a>
                <a href="http://www.apple.com.cn/safari" class="safari" target="_blank" title="safari浏览器">safari浏览器</a>
            </div>
            <div class="bottomtitle">[&nbsp;<a href="http://www.baidu.com/search/theie6countdown.html" target="_blank">对IE6说再见</a>&nbsp;]</div>
        </div>
    </div>
    <!-- 桌面 -->
    <div id="desktop">
        <div id="zoom-tip"><div><i>​</i>​<span></span></div><a href="javascript:;" class="close" onclick="HROS.zoom.close();">×</a></div>
        <div id="desk">
            <div id="desk-1" class="desktop-container"><div class="scrollbar scrollbar-x"></div><div class="scrollbar scrollbar-y"></div></div>
            <div id="dock-bar">
                <div id="dock-container">
                    <div class="dock-middle">
                        <div class="dock-applist"></div>
                        <div class="dock-toollist">
                            <a id="loginOut" href="#">
                                <img src="<%=path%>/js/HoorayOS_mini/img/exit.png" style="height:50px;width:50px;">
                            </a>
                            <span style="text-align:center;color:white; position:absolute;top:435px; left:15px;">
                                ${sessionScope.user.username}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="task-bar-bg1"></div>
        <div id="task-bar-bg2"></div>
        <div id="task-bar">
            <div id="task-next"><a href="javascript:;" id="task-next-btn" hidefocus="true"></a></div>
            <div id="task-content">
                <div id="task-content-inner"></div>
            </div>
            <div id="task-pre"><a href="javascript:;" id="task-pre-btn" hidefocus="true"></a></div>
        </div>
    </div>
    <script src="<%=path%>/js/HoorayOS_mini/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/HoorayLibs/hooraylibs.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/templates.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/core.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.app.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.base.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.desktop.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.dock.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.grid.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.maskBox.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.taskbar.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.popupMenu.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.wallpaper.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.widget.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.window.js"></script>
    <script src="<%=path%>/js/HoorayOS_mini/js/hros.zoom.js"></script>

    <script type="text/javascript" src="<%=path%>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <link href="<%=path%>/js/easyui-1.3.5/themes/default/easyui.css" rel="stylesheet" type="text/css" />

    <script>
        $(function () {
            $('#loginOut').click(function () {
                if (confirm("确认要退出系统吗？"))
                    location.href = '<%=path%>/user/logout.action?clientIp=<%=clientIp%>';
            })
            //IE下禁止选中
            document.body.onselectstart = document.body.ondrag = function () { return false; }
            //隐藏加载遮罩层
            $('.loading').hide();
            //IE6升级提示
            if ($.browser.msie && $.browser.version < 7) {
                if ($.browser.version < 7) {
                    //虽然不支持IE6，但还是得修复PNG图片透明的问题
                    DD_belatedPNG.fix('.update_browser .browser');
                }
                $('.update_browser_box').show();
            } else {
                $('#desktop').show();
                //初始化一些桌面信息
                HROS.CONFIG.wallpaper = '<%=path%>/js/HoorayOS_mini/img/wallpaper/wallpaper.jpg';
                //加载桌面
                HROS.base.init();
            }
            if ('@ViewData["News"].ToString()'.length > 0) {
                $.messager.show({
                    title: '公 告',
                    msg: '最新公告',
                    timeout: 20000,
                    showType: 'slide',
                    width: 500,
                    height: 280
                });
            }
            function JSGetCookie(Name) {
                var search = Name + "="
                if (document.cookie.length > 0) {
                    offset = document.cookie.indexOf(search)
                    if (offset != -1) {
                        offset += search.length
                        end = document.cookie.indexOf(";", offset)
                        if (end == -1) end = document.cookie.length
                        return unescape(document.cookie.substring(offset, end))
                    }
                    else return ""
                }
            }
            function UserOut() {
                if (JSGetCookie("UserId") == null)
                    top.location.href = '../../';
            }
            setInterval(UserOut, 5000);
            
        });

    </script>
</body>
</html>