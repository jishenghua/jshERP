<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css"/>
    <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/outlook.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
    <script type="text/javascript">
        function NewTab(name, url, funId) {
            window.funId = funId;
            addTab(name, url, '');
        }
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<script>
    var pageid = getUrlParam('id');  //获取传值id
    var id =${sessionScope.user.id};
    //alert(pageid);

    var userBusinessList = null;
    var userBusinessID = null;
    var type = null;
    var options = "";
    var kid = null;
    var lei = null;
    var last = "";
    var functions = "";
    var btnStrList = []; //按钮权限列表

    //初始化界面
    $(function () {
        //初始化系统基础信息
        initSystemData(id, 'UserRole');
        initSelectInfo(0);

        initSelect();

        //ceshi();
    });

    //初始化系统基础信息
    function initSystemData(kid, type) {
        $.ajax({
            type: "post",
            url: "<%=path%>/userBusiness/getBasicData.action",
            data: ({
                KeyId: kid,
                Type: type
            }),
            //设置为同步
            async: false,
            dataType: "json",
            success: function (systemInfo) {
                if (systemInfo) {
                    userBusinessList = systemInfo.showModel.map.userBusinessList;
                    var msgTip = systemInfo.showModel.msgTip;
                    if (msgTip == "exceptoin") {
                        $.messager.alert('提示', '查找UserBusiness异常,请与管理员联系！', 'error');
                        return;
                    }
                }
                else {
                    userBusinessList = null;
                }
            }
        });

    }

    //初始化页面选项卡
    function initSelectInfo(lei) {
        if (userBusinessList != null) {
            if (userBusinessList.length > 0) {
                options = userBusinessList[0].value;
                if (options != "") {
                    options = options.substring(1, options.length - 1);
                }
                //app内列表赋值
                if (lei == 1) {
                    last += options + '][';
                }
                //功能菜单列表
                else if (lei == 2) {
                    functions += options + '][';
                }
                //按钮权限列表
                else if (lei == 3) {
                    var btnStr = userBusinessList[0].btnStr;
                    if (btnStr != null) {
                        btnStr = JSON.parse(btnStr);
                        for (var j = 0; j < btnStr.length; j++) {
                            btnStrList.push(btnStr[j]);
                        }
                    }
                }
            }
        }
    }

    //初始化页面
    function initSelect() {
        var arr = options.split('][');
        for (var i in arr) {
            initSystemData(arr[i], 'RoleAPP'); //根据角色找app
            initSelectInfo(1);//app内列表赋值

            initSystemData(arr[i], 'RoleFunctions'); //根据角色找functions
            initSelectInfo(2); //功能菜单列表
            initSelectInfo(3); //查询角色对应的按钮权限
        }
        if (last != "") {
            last = "[" + last.substring(0, last.length - 1);
            //alert(last);

            if (last.indexOf("[" + pageid + "]") != -1) {
                //alert("存在");
                $("#west").show();
                $("#mainPanle").show();
                $("#mm").show();
            }
            else {
                //alert("不存在");
                $("div").remove();
                $("<div style='width:100%;text-align:center;padding-top:20px'><b>抱歉，您没有该权限！</b></div>").appendTo("body");
            }
        }
        if (functions != "") {
            functions = "[" + functions.substring(0, functions.length - 1);
            //alert(functions);
        }
        if (btnStrList.length > 0) {
            window.winBtnStrList = JSON.stringify(btnStrList); //将按钮功能列表存为全局变量
        }
    }

    //测试自定义hql
    function ceshi() {
        $.ajax({
            type: "post",
            url: "<%=path%>/userBusiness/getceshi.action",
            data: ({
                Type: "UserRole"
            }),
            //设置为同步
            async: false,
            dataType: "json",
            success: function (systemInfo) {
                if (systemInfo) {
                    userBusinessList = systemInfo.showModel.map.userBusinessList;
                    var msgTip = systemInfo.showModel.msgTip;
                    if (msgTip == "exceptoin") {
                        $.messager.alert('提示', '查找UserBusiness异常,请与管理员联系！', 'error');
                        return;
                    }
                }
                else {
                    userBusinessList = null;
                }
            }
        });

        if (userBusinessList != null) {
            if (userBusinessList.length > 0) {
                alert(userBusinessList[0][0]);
                alert(userBusinessList[0][1]);
            }
        }
    }

</script>

<div region="west" hide="true" split="true" title="导航菜单" style="width:125px;" id="west">
    <div id="nav" fit="true" border="false">
        <!--  导航内容 -->
        <div>
            <ul id="tt"></ul>
            <script>
                var PNumber = getUrlParam('appID');  //获取传值appid
                $(function () {
                    $('#tt').tree({
                        url: '<%=path%>/functions/findMenu.action?PNumber=' + PNumber + '&hasFunctions=' + functions,
                        animate: true
                    });
                });
            </script>
        </div>
    </div>
</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
    <div id="tabs" class="easyui-tabs" fit="true" border="false"></div>
</div>
<div id="mm" class="easyui-menu" style="width: 120px;">
    <div id="mm-tabupdate">
        刷新
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-tabclose">
        关闭
    </div>
    <div id="mm-tabcloseall">
        全部关闭
    </div>
    <div id="mm-tabcloseother">
        关闭其他页
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-tabcloseright">
        关闭右侧页面
    </div>
    <div id="mm-tabcloseleft">
        关闭左侧页面
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-version">
        华夏ERP官网
    </div>
</div>
</body>
</html>