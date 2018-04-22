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
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css"/>
    <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
</head>
<body>
<div id="position" class="easyui-panel" title="当前位置：资产管理 &gt; 资产管理" collapsible="false" closable="false"/>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>资产名称：</td>
            <td alt="选项卡中内容为：资产类型-->资产名称">
                <select name="searchAssetNameID" id="searchAssetNameID" style="width:230px;"></select>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>资产类型：</td>
            <td>
                <select name="searchCategoryID" id="searchCategoryID" style="width:230px;"></select>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>用户姓名：</td>
            <td>
                <select name="searchuserID" id="searchuserID" style="width:230px;"></select>
            </td>
        </tr>
        <tr>
            <td id="searchStatusLabel">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
            <td>
                <select name="searchStatus" id="searchStatus" style="width:230px;">
                    <option value="">请选择</option>
                    <option value="0">在库</option>
                    <option value="1">在用</option>
                    <option value="2">消费</option>
                </select>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td id="searchSupplierIDLabel">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
            <td>
                <select name="searchSupplierID" id="searchSupplierID" style="width:230px;"></select>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="资产列表" iconCls="icon-list"
     collapsible="true" closable="false">
    <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
</div>

<div id="assetDlg" style="padding:10px 20px;">
    <form id="assetFM" method="post" novalidate>
        <table>
            <tr>
                <td>资产名称：</td>
                <td>
                    <select id="assetNameID" name="assetNameID" style="width:200px;height: 21px"></select>
                    &nbsp;&nbsp;<img id="addAssetName" src="<%=path %>/js/easyui-1.3.5/themes/icons/edit_add.png"
                                     style="cursor: pointer;" alt="增加资产"/>
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td id="statusLabel">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
                <td>
                    <select id="status" name="status" style="width:230px;height: 21px">
                        <option value="0">在库</option>
                        <option value="1">在用</option>
                        <option value="2">消费</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td id="locationLabel">位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</td>
                <td>
                    <input type="text" name="location" id="location" style="width:230px;height: 21px"/>
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td id="userIDLabel">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</td>
                <td>
                    <select id="userID" name="userID" style="width:230px;height: 21px"></select>
                    &nbsp;&nbsp;<img id="addUserName" src="<%=path %>/js/easyui-1.3.5/themes/icons/edit_add.png"
                                     style="cursor: pointer;display: none;" alt="增加用户"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td>订购单价：</td>
                <td>
                    <input type="text" name="price" id="price" class="easyui-numberbox" style="width:200px;height: 21px"
                           data-options="min:0,precision:2"/>&nbsp;&nbsp;元
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>购买日期：</td>
                <td>
                    <input type="text" name="purchasedate" id="purchasedate" class="easyui-datebox"
                           style="width:230px;height: 21px"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td>有效日期：</td>
                <td>
                    <input type="text" name="periodofvalidity" id="periodofvalidity" class="easyui-datebox"
                           style="width:230px;height: 21px"/>
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>保修日期：</td>
                <td>
                    <input type="text" name="warrantydate" id="warrantydate" class="easyui-datebox"
                           style="width:230px;height: 21px"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td>资产编号：</td>
                <td>
                    <input type="text" name="assetnum" id="assetnum" style="width:230px;height: 21px"/>
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td id="serialnumLabel">序&nbsp;&nbsp;&nbsp;列&nbsp;&nbsp;&nbsp;号：</td>
                <td>
                    <input type="text" name="serialnum" id="serialnum" style="width:230px;height: 21px"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td>资产标签：</td>
                <td>
                    <input type="text" name="labels" id="labels" style="width:230px;height: 21px"/>
                </td>

                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td id="supplierIDLabel">供&nbsp;&nbsp;&nbsp;应&nbsp;&nbsp;&nbsp;商：</td>
                <td>
                    <select id="supplierID" name="supplierID" style="width:200px;height: 21px"></select>
                    &nbsp;&nbsp;<img id="addSupplerName" src="<%=path %>/js/easyui-1.3.5/themes/icons/edit_add.png"
                                     style="cursor: pointer;" alt="增加供应商"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">&nbsp;</td>
            </tr>
            <tr>
                <td id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
                <td colspan="5">
                    <textarea name="description" id="description" cols="2" rows="2" style="width:550px;"></textarea>
                </td>
            </tr>
        </table>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
</div>
<div id="dlg-buttons0">
    <a href="javascript:void(0)" id="saveAsset" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="saveKeepAsset" class="easyui-linkbutton" iconCls="icon-save">保存并新建</a>
    <a href="javascript:void(0)" id="cancelSupplier" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#assetDlg').dialog('close')">取消</a>
</div>
<!-- 增加资产名称 -->
<div id="assetnameDlg" style="padding:10px 20px">
    <form id="assetnameFM" method="post" novalidate>
        <div class="fitem" style="padding:5px">
            <label id="assetNameLabel">资产名称&nbsp;&nbsp;</label>
            <input name="assetName" id="assetName" class="easyui-validatebox"
                   data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="categoryLabel">资产类型&nbsp;&nbsp;</label>
            <select name="category" id="category" style="width:230px;height: 20px"></select>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="consumableLabel">是否耗材&nbsp;&nbsp;</label>
            <select name="consumable" id="consumable" style="width: 230px;height: 20px">
                <option value="0">是</option>
                <option value="1">否</option>
            </select>
        </div>
        <div class="fitem" style="padding:5px;">
            <label id="namedescriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
            <textarea name="description" id="descriptioname" rows="2" cols="2" style="width: 230px;"></textarea>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
</div>
<div id="dlg-buttons1">
    <a href="javascript:void(0)" id="saveAssetName" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelAssetName" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#assetnameDlg').dialog('close')">取消</a>
</div>


<div id="supplierDlg" style="padding:10px 20px">
    <form id="supplierFM" method="post" novalidate>

        <div class="fitem" style="padding:5px">
            <label id="supplierLabel">供&nbsp;&nbsp;应&nbsp;&nbsp;商 &nbsp;</label>
            <input name="supplier" id="supplier" class="easyui-validatebox"
                   data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="contactsLabel">联&nbsp;&nbsp;系&nbsp;&nbsp;人&nbsp;&nbsp;</label>
            <input name="contacts" id="contacts" class="easyui-validatebox" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="phonenumLabel">联系电话&nbsp;&nbsp;</label>
            <input name="phonenum" id="phonenum" class="easyui-numberbox" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="emailLabel">电子邮箱&nbsp;&nbsp;</label>
            <input name="email" id="email" class="easyui-validatebox" validType="email"
                   style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="supplierdescriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
            <textarea name="description" id="descriptionSupplier" rows="2" cols="2" style="width: 230px;"></textarea>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
</div>
<div id="dlg-buttons2">
    <a href="javascript:void(0)" id="saveSupplier" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelSupplier" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
</div>

<div id="userDlg" style="padding:10px 20px">
    <form id="usernameFM" method="post" novalidate>
        <div class="fitem" style="padding:5px">
            <label id="usernameLabel">用户名称&nbsp;&nbsp;</label>
            <input name="username" id="username" class="easyui-validatebox"
                   data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
        </div>

        <div class="fitem" style="padding:5px">
            <label id="departmentLabel">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;</label>
            <input name="department" id="department" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="userpositionLabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;</label>
            <input name="position" id="position" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="userphonenumLabel">联系电话&nbsp;&nbsp;</label>
            <input name="phonenum" id="phonenum" class="easyui-numberbox" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="useremailLabel">电子邮箱&nbsp;&nbsp;</label>
            <input name="email" id="email" class="easyui-validatebox" validType="email"
                   style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="userdescriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
            <textarea name="description" id="descriptionUser" rows="2" cols="2" style="width: 230px;"></textarea>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
    <div id="dlg-buttons3">
        <a href="javascript:void(0)" id="saveusername" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
        <a href="javascript:void(0)" id="cancelusername" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#userDlg').dialog('close')">取消</a>
    </div>
</div>

<!-- 导出excel表格 -->
<div id="exportExcelDlg" style="padding:10px 20px">
    <div class="fitem" style="padding:5px">
        <label>文件名称&nbsp;&nbsp;</label>
        <input name="fileName" id="fileName" type="text" class="easyui-validatebox"
               data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px" maxlength="20"/>
    </div>

    <div class="fitem" style="padding:5px">
        <label>是否全部&nbsp;&nbsp;</label>
        <select style="width: 230px;height: 20px" id="isAllData" name="isAllData">
            <option value="currentPage" selected="selected">当前页</option>
            <option value="allPage">全部页</option>
        </select>
    </div>
    <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    <div id="dlg-buttons4">
        <a href="javascript:void(0)" id="saveexport" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
        <a href="javascript:void(0)" id="cancelexport" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#exportExcelDlg').dialog('close')">取消</a>
    </div>
</div>

<!-- 导入excel表格 -->
<div id="importExcelDlg" style="padding:10px 20px">
    <form id="importExcelFM" method="post" enctype="multipart/form-data" action="<%=path%>/asset/importExcel.action">
        <div class="fitem" style="padding:5px">
            <label>文件名称&nbsp;&nbsp;</label>
            <input name="assetFile" id="assetFile" type="file" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px;display: none;">
            <label>是否审查&nbsp;&nbsp;</label>
            <select id="isCheck" name="isCheck" style="width: 230px;height: 20px">
                <option value="0">是</option>
                <option value="1" selected="selected">否</option>
            </select>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
        <div id="dlg-buttons5">
            <a href="javascript:void(0)" id="saveimport" class="easyui-linkbutton" iconCls="icon-ok">导入</a>
            <a href="javascript:void(0)" id="cancelimport" class="easyui-linkbutton" iconCls="icon-cancel"
               onclick="javascript:$('#importExcelDlg').dialog('close')">取消</a>
        </div>
    </form>
</div>

<div id="rightMenu" class="easyui-menu" style="width:120px;">
    <div data-options="iconCls:'icon-edit'" id="rightEdit">编辑</div>
    <div data-options="iconCls:'icon-remove'" id="rightDelete">删除</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-cancel'" id="rightCancel">取消</div>
</div>
<script type="text/javascript">

    var userList = null;
    var categoryList = null;
    var supplierList = null;
    var nameList = null;
    //界面selet选显卡有问题解决方案
    var nameID = null;
    var userID = null;
    var categoryID = null;
    var supplierID = null;

    //右键编辑信息
    var rightRowInfo;
    var rightRowID;
    var rightRowIndex;
    //初始化界面
    $(function () {
        $.messager.progress({
            title: '请稍候',
            msg: '数据加载ing...'
        });
        initTableData();
        ininPager();
        //初始化系统基础信息
        initSystemData();
        //初始化页面系统基础信息选项卡
        initSelectInfo();
        //隐藏操作按钮
        hideOperation();
        //防止表格刷新
        //initForm();
        browserFit();
        $.messager.progress('close');
    });

    //资产对话框
    $('#assetDlg').dialog({
        width: 700,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons0'
    });

    //资产名称对话框
    $('#assetnameDlg').dialog({
        width: 390,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons1'
    });

    //供应商对话框
    $('#supplierDlg').dialog({
        width: 380,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons2'
    });

    //用户对话框
    $('#userDlg').dialog({
        width: 380,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons3'
    });

    //导出excel对话框
    $('#exportExcelDlg').dialog({
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons4'
    });

    //导入excel对话框
    $('#importExcelDlg').dialog({
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        collapsible: false,
        closable: true,
        buttons: '#dlg-buttons5'
    });

    //浏览器适配
    function browserFit() {
        if (getOs() == 'MSIE') {
            $("#searchStatusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：");
            $("#searchSupplierIDLabel").empty().append("供&nbsp;&nbsp;应&nbsp;&nbsp;商：");
            $("#statusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：");
            $("#locationLabel").empty().append("位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：");
            $("#userIDLabel").empty().append("用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：");
            $("#serialnumLabel").empty().append("序&nbsp;&nbsp;&nbsp;列&nbsp;&nbsp;&nbsp;号：");
            $("#supplierIDLabel").empty().append("供&nbsp;&nbsp;&nbsp;应&nbsp;&nbsp;&nbsp;商：");
            $("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");

            //供应商
            $("#supplierLabel").empty().append("供&nbsp;&nbsp;应&nbsp;&nbsp;商 &nbsp;");
            $("#contactsLabel").empty().append("联&nbsp;&nbsp;系&nbsp;&nbsp;人&nbsp;&nbsp;");
            $("#phonenumLabel").empty().append("联系电话&nbsp;&nbsp;");
            $("#emailLabel").empty().append("电子邮箱&nbsp;&nbsp;");
            $("#supplierdescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");

            //资产名称
            $("#namedescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
            $("#assetNameLabel").empty().append("资产名称&nbsp;&nbsp;");
            $("#categoryLabel").empty().append("资产类型&nbsp;&nbsp;");
            $("#consumableLabel").empty().append("是否耗材&nbsp;&nbsp;");

            //用户
            $("#usernameLabel").empty().append("用户名称&nbsp;&nbsp;");
            $("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;");
            $("#userpositionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;");
            $("#userphonenumLabel").empty().append("联系电话&nbsp;&nbsp;");
            $("#useremailLabel").empty().append("电子邮箱&nbsp;&nbsp;");
            $("#userdescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
        }
        else {
            $("#searchStatusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;态：");
            $("#searchSupplierIDLabel").empty().append("供&nbsp;应&nbsp;商：");
            $("#statusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;态：");
            $("#locationLabel").empty().append("位&nbsp;&nbsp;&nbsp;&nbsp;置：");
            $("#userIDLabel").empty().append("用&nbsp;&nbsp;&nbsp;&nbsp;户：");
            $("#serialnumLabel").empty().append("序&nbsp;&nbsp;列&nbsp;&nbsp;号：");
            $("#supplierIDLabel").empty().append("供&nbsp;&nbsp;应&nbsp;&nbsp;商：");
            $("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");

            //供应商
            $("#supplierLabel").empty().append("供&nbsp;应&nbsp;商 ");
            $("#contactsLabel").empty().append("联&nbsp;系&nbsp;人");
            $("#phonenumLabel").empty().append("联系电话");
            $("#emailLabel").empty().append("电子邮箱");
            $("#supplierdescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述");

            //资产名称
            $("#namedescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
            $("#assetNameLabel").empty().append("资产名称&nbsp;");
            $("#categoryLabel").empty().append("资产类型&nbsp;");
            $("#consumableLabel").empty().append("是否耗材&nbsp;");

            //用户
            $("#usernameLabel").empty().append("用户名称&nbsp;");
            $("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;");
            $("#userpositionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;");
            $("#userphonenumLabel").empty().append("联系电话&nbsp;");
            $("#useremailLabel").empty().append("电子邮箱&nbsp;");
            $("#userdescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
        }
    }

    //隐藏操作按钮
    function hideOperation() {
        if ('${sessionScope.user.loginame}' == 'guest')
            $("#addAsset").add("#deleteAsset").add("#importAsset").add("#exportAsset").add("#editAsset").hide();
        else
            $("#addAsset").add("#deleteAsset").add("#importAsset").add("#exportAsset").add("#editAsset").show();
    }

    //防止表单提交重复
    function initForm() {
        $('#importExcelFM').form({
            onSubmit: function () {
                return false;
            }
        });
    }

    //导入excel表格
    $("#saveimport").unbind().bind({
        click: function () {
            if ($("#assetFile").val().length == 0) {
                $.messager.alert('提示', '请选择文件！', 'info');
                return;
            }
            $("#importExcelFM").submit();
            $('#importExcelDlg').dialog('close');

            $.messager.progress({
                title: '请稍候',
                msg: '数据处理ing...'
            });
            setTimeout(function () {
                $.messager.progress('close');
                var opts = $("#tableData").datagrid('options');
                showAssetDetails(opts.pageNumber, opts.pageSize);
            }, 3300);
        }
    });

    //初始化表格数据
    function initTableData() {
        $('#tableData').datagrid({
            //title:'资产列表',
            //iconCls:'icon-save',
            //width:700,
            height: heightInfo,
            nowrap: false,
            rownumbers: false,
            //动画效果
            animate: false,
            //选中单行
            singleSelect: true,
            collapsible: false,
            selectOnCheck: false,
            //fitColumns:true,
            //单击行是否选中
            checkOnSelect: false,
            //交替出现背景
            striped: true,
            url: '<%=path %>/asset/findBy.action?pageSize=' + initPageSize,
            pagination: true,
            //loadFilter: pagerFilter,
            pageSize: initPageSize,
            pageList: initPageNum,
            rowStyler: function (index, row) {
                return 'cursor: pointer;';
            },
            frozenColumns: [[
                {field: 'id', width: 35, align: "center", checkbox: true},
                {title: '资产名称', field: 'assetname', width: 100},
                {title: '资产类型', field: 'category', width: 115, align: "center"},
                {
                    title: '单价', field: 'price', width: 70, align: "center", formatter: function (value, row) {
                        if (value == '0.0')
                            return '';
                        return value;
                    }
                }
            ]],
            columns: [[
                {title: '用户', field: 'username', width: 100, align: "center"},
                {title: '购买日期', field: 'purchasedate', width: 90, align: "center"},
                {title: '状态', field: 'status', width: 50, align: "center"},
                {title: '位置', field: 'location', width: 100, align: "center"},
                {title: '资产编号', field: 'assetnum', width: 120, align: "center"},
                {title: '序列号', field: 'serialnum', width: 120, align: "center"},
                {title: '有效日期', field: 'periodofvalidity', width: 90, align: "center"},
                {title: '保修日期', field: 'warrantydate', width: 90, align: "center"},
                {title: '供应商', field: 'supplier', width: 100, align: "center"},
                {title: '标签', field: 'labels', width: 180, align: "center"},
                {title: '描述', field: 'description', width: 300},
                {
                    title: '操作', field: 'op', align: "center", width: 130, formatter: function (value, rec) {
                        var price;
                        if (rec.price == '0.0')
                            price = '';
                        else
                            price = rec.price;
                        var str = '';
                        var rowInfo = rec.id + 'AaBb' + rec.assetnameID + 'AaBb' + price
                            + 'AaBb' + rec.categoryID + 'AaBb' + rec.userID + 'AaBb' + rec.purchasedate
                            + 'AaBb' + rec.statushort + 'AaBb' + rec.location + 'AaBb' + rec.periodofvalidity
                            + 'AaBb' + rec.warrantydate + 'AaBb' + rec.supplierID + 'AaBb' + rec.labels
                            + 'AaBb' + rec.description + 'AaBb' + rec.assetnum + 'AaBb' + rec.serialnum;
                        if (1 == value && '${sessionScope.user.loginame}' != 'guest') {
                            str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAsset(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editAsset(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
                            str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAsset(' + rec.id + ');"/>&nbsp;<a onclick="deleteAsset(' + rec.id + ');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
                        }
                        return str;
                    }
                }
            ]],
            toolbar: [
                {
                    id: 'addAsset',
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: function () {
                        addAsset();
                    }
                }
                , '-',
                {
                    id: 'deleteAsset',
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        batDeleteAsset();
                    }
                }
                , '-',
                {
                    id: 'exportAsset',
                    text: '导出',
                    iconCls: 'icon-excel',
                    handler: function () {
                        $("#fileName").val("").focus();
                        $("#isAllData").val("currentPage");
                        $('#exportExcelDlg').dialog('open').dialog('setTitle', '导出资产信息');
                        $(".window-mask").css({width: webW - 20, height: webH});
                        $("#fileName").focus();
                    }
                }
                , '-',
                {
                    id: 'importAsset',
                    text: '导入',
                    iconCls: 'icon-excel',
                    handler: function () {

                        //IE下不允许编辑 input=file的值  解决思路：重新克隆input=file，把这个input元素复制一个，然后将原来的删除。
                        //在IE下复制元素的时候，其中的值是不会被复制的，所以就达到了清空文件域的目的了。
                        //而在Firefox下，其中的值也会被一同复制，清空一下就做到兼容
                        var fileUploadInput = $("#assetFile");
                        fileUploadInput.after(fileUploadInput.clone().val(""));
                        fileUploadInput.remove();
                        $("#isCheck").val(1);
                        //$("#assetFile").val("").focus();
                        //$("#isAllData").val("currentPage");
                        $('#importExcelDlg').dialog('open').dialog('setTitle', '导入资产信息');
                        $(".window-mask").css({width: webW - 20, height: webH});
                        $("#assetFile").focus();
                    }
                }
            ],
            onLoadError: function () {
                $.messager.alert('页面加载提示', '页面加载异常，请稍后再试！', 'error');
                return;
            },
            onRowContextMenu: function (e, rowIndex, rec) {
                e.preventDefault();
                $(this).datagrid('selectRow', rowIndex);
                $('#rightMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
                //$("#" + rec.id).click();

                rightRowInfo = rec.id + 'AaBb' + rec.assetnameID + 'AaBb' + rec.price
                    + 'AaBb' + rec.categoryID + 'AaBb' + rec.userID + 'AaBb' + rec.purchasedate
                    + 'AaBb' + rec.statushort + 'AaBb' + rec.location + 'AaBb' + rec.periodofvalidity
                    + 'AaBb' + rec.warrantydate + 'AaBb' + rec.supplierID + 'AaBb' + rec.labels
                    + 'AaBb' + rec.description + 'AaBb' + rec.assetnum + 'AaBb' + rec.serialnum;
                rightRowID = rec.id;
                rightRowIndex = rowIndex;
            },
            onDblClickRow: function (rowIndex, rec) {
                var rowDBInfo = rec.id + 'AaBb' + rec.assetnameID + 'AaBb' + rec.price
                    + 'AaBb' + rec.categoryID + 'AaBb' + rec.userID + 'AaBb' + rec.purchasedate
                    + 'AaBb' + rec.statushort + 'AaBb' + rec.location + 'AaBb' + rec.periodofvalidity
                    + 'AaBb' + rec.warrantydate + 'AaBb' + rec.supplierID + 'AaBb' + rec.labels
                    + 'AaBb' + rec.description + 'AaBb' + rec.assetnum + 'AaBb' + rec.serialnum;
                editAsset(rowDBInfo);
            }
        });
    }

    //获取编辑条目
    function showEditInfo() {
        var rec = $("#tableData").datagrid('getSelected');
        if (null == rec) {
            $.messager.alert('提示', '没有资产信息被选中！', 'info');
            return;
        }
        var rowDBInfo = rec.id + 'AaBb' + rec.assetnameID + 'AaBb' + rec.price
            + 'AaBb' + rec.categoryID + 'AaBb' + rec.userID + 'AaBb' + rec.purchasedate
            + 'AaBb' + rec.statushort + 'AaBb' + rec.location + 'AaBb' + rec.periodofvalidity
            + 'AaBb' + rec.warrantydate + 'AaBb' + rec.supplierID + 'AaBb' + rec.labels
            + 'AaBb' + rec.description + 'AaBb' + rec.assetnum + 'AaBb' + rec.serialnum;
        editAsset(rowDBInfo);
    }

    //右键编辑
    $("#rightEdit").unbind().bind({
        click: function () {
            editAsset(rightRowInfo);
        }
    });

    //右键删除
    $("#rightDelete").unbind().bind({
        click: function () {
            deleteAsset(rightRowID);
        }
    });

    //右键取消
    $("#rightCancel").unbind().bind({
        click: function () {
            //$("#searchBtn").click();
            $("#tableData").datagrid('unselectRow', rightRowIndex);
        }
    });

    //导出资产为excel表格
    $("#saveexport").unbind().bind({
        click: function () {
            var fileName = $.trim($("#fileName").val());
            var isAllData = $("#isAllData").val();
            if (!$(this).form('validate'))
                return;
            var opts = $("#tableData").datagrid('options');
            window.location.href = "<%=path%>/asset/exportExcel.action?fileName=" + encodeURI(fileName) + "&isAllData=" + isAllData + "&browserType=" + getOs() + "&pageSize=" + opts.pageSize + "&pageNo=" + opts.pageNumber;
            $('#exportExcelDlg').dialog('close');
        }
    });

    //初始化键盘enter事件
    $(document).keydown(function (event) {
        //兼容 IE和firefox 事件
        var e = window.event || event;
        var k = e.keyCode || e.which || e.charCode;
        //兼容 IE,firefox 兼容
        var obj = e.srcElement ? e.srcElement : e.target;
        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
        if (k == "13" && (obj.id == "assetNameID" || obj.id == "location"
                || obj.id == "status" || obj.id == "userID" || obj.id == "price" || obj.id == "purchasedate"
                || obj.id == "supplierID" || obj.id == "periodofvalidity" || obj.id == "warrantydate"
                || obj.id == "assetnum" || obj.id == "serialnum" || obj.id == "labels"
                || obj.id == "supplierID" || obj.id == "description")) {
            $("#saveAsset").click();
        }

        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
        if (k == "13" && (obj.id == "supplier" || obj.id == "contacts" || obj.id == "phonenum"
                || obj.id == "email" || obj.id == "descriptionSupplier")) {
            $("#saveSupplier").click();
        }

        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
        if (k == "13" && (obj.id == "assetName" || obj.id == "descriptioname" || obj.id == "consumable" || obj.id == "category")) {
            $("#saveAssetName").click();
        }

        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
        if (k == "13" && (obj.id == "username" || obj.id == "department" || obj.id == "phonenum0"
                || obj.id == "email" || obj.id == "descriptionUser" || obj.id == "position")) {
            $("#saveusername").click();
        }
        //搜索按钮添加快捷键
        if (k == "13" && (obj.id == "searchSupplier" || obj.id == "searchCategoryID" || obj.id == "searchuserID"
                || obj.id == "searchStatus" || obj.id == "searchSupplierID")) {
            $("#searchBtn").click();
        }

        //导出添加快捷键
        if (k == "13" && (obj.id == "fileName" || obj.id == "isAllData")) {
            $("#saveexport").click();
        }
    });

    //增加资产名称
    $("#addAssetName").unbind().bind({
        click: function () {
            $('#assetnameDlg').dialog('open').dialog('setTitle', '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加资产名称信息');
            $('#assetnameFM').form('clear');
            var row = {
                consumable: 0,
                clientIp: '<%=clientIp %>',
                category: categoryID
            };
            $('#assetnameFM').form('load', row);
            $("#assetName").focus();
        }
    });

    //保存信息
    $("#saveAssetName").unbind().bind({
        click: function () {
            if (!$('#assetnameFM').form('validate'))
                return;
            else if (checkAssetName())
                return;
            else {
                $.ajax({
                    type: "post",
                    url: '<%=path %>/assetname/create.action',
                    dataType: "json",
                    async: false,
                    data: ({
                        assetName: $.trim($("#assetName").val()),
                        consumable: $("#consumable").val(),
                        description: $.trim($("#descriptioname").val()),
                        clientIp: '<%=clientIp %>',
                        categoryID: $.trim($("#category").val())
                    }),
                    success: function (tipInfo) {
                        if (tipInfo) {
                            $('#assetnameDlg').dialog('close');
                            //初始化系统基础信息
                            initSystemData();
                            //初始化页面系统基础信息选项卡
                            initSelectInfo();
                        }
                        else {
                            $.messager.show({
                                title: '错误提示',
                                msg: '保存资产名称信息失败，请稍后重试!'
                            });
                        }
                    },
                    //此处添加错误处理
                    error: function () {
                        $.messager.alert('提示', '保存资产名称信息异常，请稍后再试！', 'error');
                        return;
                    }
                });
            }
        }
    });

    //检查名称是否存在 ++ 重名无法提示问题需要跟进
    function checkAssetName() {
        var assetName = $.trim($("#assetName").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if (assetName.length > 0) {
            $.ajax({
                type: "post",
                url: "<%=path %>/assetname/checkIsNameExist.action",
                dataType: "json",
                async: false,
                data: ({
                    assetNameID: 0,
                    assetName: assetName
                }),
                success: function (tipInfo) {
                    flag = tipInfo;
                    if (tipInfo) {
                        $.messager.alert('提示', '资产名称已经存在', 'info');
                        return;
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('提示', '检查资产名称是否存在异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
        return flag;
    }

    //增加用户名称
    $("#addUserName").unbind().bind({
        click: function () {
            $('#userDlg').dialog('open').dialog('setTitle', '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加用户');
            $('#usernameFM').form('clear');
            var row = {
                clientIp: '<%=clientIp %>'
            };
            $('#usernameFM').form('load', row);
            $("#username").focus();
        }
    });

    //保存用户信息
    $("#saveusername").unbind().bind({
        click: function () {
            if (checkusernameName())
                return;

            var reg = /^([0-9])+$/;
            var phonenum = $.trim($("#phonenum0").val());
            if (phonenum.length > 0 && !reg.test(phonenum)) {
                $.messager.alert('提示', '电话号码只能是数字', 'info');
                $("#phonenum").val("").focus();
                return;
            }

            $('#usernameFM').form('submit', {
                url: '<%=path %>/user/create.action',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (!result) {
                        $.messager.show({
                            title: '错误提示',
                            msg: '保存用户信息失败，请稍后重试!'
                        });
                    }
                    else {
                        $('#userDlg').dialog('close');
                        //初始化系统基础信息
                        initSystemData();
                        //初始化页面系统基础信息选项卡
                        initSelectInfo();
                    }
                }
            });
        }
    });

    //检查用户 名称是否存在 ++ 重名无法提示问题需要跟进
    function checkusernameName() {
        var usernameName = $.trim($("#username").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if (usernameName.length > 0) {
            $.ajax({
                type: "post",
                url: "<%=path %>/user/checkIsNameExist.action",
                dataType: "json",
                async: false,
                data: ({
                    userID: 0,
                    username: usernameName
                }),
                success: function (tipInfo) {
                    flag = tipInfo;
                    if (tipInfo) {
                        $.messager.alert('提示', '用户名称已经存在', 'info');
                        return;
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('提示', '检查用户名称是否存在异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
        return flag;
    }

    //增加供应商名称
    $("#addSupplerName").unbind().bind({
        click: function () {
            $('#supplierDlg').dialog('open').dialog('setTitle', '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加供应商');
            $('#supplierFM').form('clear');

            var row = {
                clientIp: '<%=clientIp %>'
            };
            $('#supplierFM').form('load', row);

            $("#supplier").focus();
        }
    });

    //保存供应商信息
    $("#saveSupplier").unbind().bind({
        click: function () {
            if (checkSupplierName())
                return;

            var reg = /^([0-9])+$/;
            var phonenum = $.trim($("#phonenum").val());
            if (phonenum.length > 0 && !reg.test(phonenum)) {
                $.messager.alert('提示', '电话号码只能是数字', 'info');
                $("#phonenum").val("").focus();
                return;
            }

            $('#supplierFM').form('submit', {
                url: '<%=path %>/supplier/create.action',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (!result) {
                        $.messager.show({
                            title: '错误提示',
                            msg: '保存供应商信息失败，请稍后重试!'
                        });
                    }
                    else {
                        $('#supplierDlg').dialog('close');
                        //初始化系统基础信息
                        initSystemData();
                        //初始化页面系统基础信息选项卡
                        initSelectInfo();
                    }
                }
            });
        }
    });

    //检查供应商 名称是否存在 ++ 重名无法提示问题需要跟进
    function checkSupplierName() {
        var supplierName = $.trim($("#supplier").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if (supplierName.length > 0) {
            $.ajax({
                type: "post",
                url: "<%=path %>/supplier/checkIsNameExist.action",
                dataType: "json",
                async: false,
                data: ({
                    supplierID: 0,
                    supplier: supplierName
                }),
                success: function (tipInfo) {
                    flag = tipInfo;
                    if (tipInfo) {
                        $.messager.alert('提示', '供应商名称已经存在', 'info');
                        //alert("供应商名称已经存在");
                        //$("#supplier").val("");
                        return;
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('提示', '检查供应商名称是否存在异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
        return flag;
    }

    //分页信息处理
    function ininPager() {
        try {
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            pager.pagination({
                onSelectPage: function (pageNum, pageSize) {
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh',
                        {
                            pageNumber: pageNum,
                            pageSize: pageSize
                        });
                    showAssetDetails(pageNum, pageSize);
                }
            });
        }
        catch (e) {
            $.messager.alert('异常处理提示', "分页信息异常 :  " + e.name + ": " + e.message, 'error');
        }
    }

    //初始化系统基础信息
    function initSystemData() {
        $.ajax({
            type: "post",
            url: "<%=path%>/asset/getBasicData.action",
            //设置为同步
            async: false,
            dataType: "json",
            success: function (systemInfo) {
                //成功关闭loading
                userList = systemInfo.showModel.map.userList;
                categoryList = systemInfo.showModel.map.categoryList;
                supplierList = systemInfo.showModel.map.supplierList;
                nameList = systemInfo.showModel.map.assetnameList;
                var msgTip = systemInfo.showModel.msgTip;
                if (msgTip == "exceptoin") {
                    $.messager.alert('提示', '查找系统基础信息异常,请与管理员联系！', 'error');
                    return;
                }
            }
        });
    }

    //初始化页面选项卡
    function initSelectInfo() {
        var options = "";
        if (nameList != null) {
            for (var i = 0; i < nameList.length; i++) {
                var nameInfo = nameList[i];
                if (0 == i) {
                    nameID = nameInfo.id;
                }
                options += '<option value="' + nameInfo.id + '">' + nameInfo.assetname + '</option>';
            }
            $("#assetNameID").empty().append(options);
            $("#searchAssetNameID").empty().append('<option value="">请选择</option>').append(options);
        }

        if (userList != null) {
            options = "";
            for (var i = 0; i < userList.length; i++) {
                var user = userList[i];
                if (0 == i) {
                    userID = user.id
                }
                options += '<option value="' + user.id + '">' + user.username + '</option>';
            }
            $("#userID").empty().append(options);
            $("#searchuserID").empty().append('<option value="">请选择</option>').append(options);

        }

        if (categoryList != null) {
            options = "";
            for (var i = 0; i < categoryList.length; i++) {
                var category = categoryList[i];
                if (0 == i) {
                    categoryID = category.id;
                }
                options += '<option value="' + category.id + '">' + category.assetname + '</option>';
            }
            $("#searchCategoryID").empty().append('<option value="">请选择</option>').append(options);
            $("#category").empty().append(options);
        }

        if (supplierList != null) {
            options = "";
            for (var i = 0; i < supplierList.length; i++) {
                var supplier = supplierList[i];
                if (0 == i) {
                    supplierID = supplier.id;
                }
                options += '<option value="' + supplier.id + '">' + supplier.supplier + '</option>';
            }
            $("#supplierID").empty().append(options);
            $("#searchSupplierID").empty().append('<option value="">请选择</option>').append(options);
        }
    }

    //控制用户数据是否可用
    $("#status").unbind().bind({
        change: function () {
            if ($(this).val() == 1 || $(this).val() == 2) {
                $("#userID").removeAttr("disabled").css({width: "200px"});
                $("#addUserName").show();
            }
            else {
                $("#userID").attr("disabled", "true").css({width: "230px"});
                $("#addUserName").hide();
            }
        }
    });

    //删除资产信息
    function deleteAsset(assetID) {
        $.messager.confirm('删除确认', '确定要删除此资产信息吗？', function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: "<%=path %>/asset/delete.action",
                    dataType: "json",
                    data: ({
                        assetID: assetID,
                        clientIp: '<%=clientIp %>'
                    }),
                    success: function (tipInfo) {
                        var msg = tipInfo.showModel.msgTip;
                        if (msg == '成功') {
                            //$('#tableData').datagrid('reload');
                            //加载完以后重新初始化
                            $("#searchBtn").click();
                            //var opts = $("#tableData").datagrid('options');
                            //showAssetDetails(opts.pageNumber,opts.pageSize);
                        }
                        else
                            $.messager.alert('删除提示', '删除资产信息失败，请稍后再试！', 'error');
                    },
                    //此处添加错误处理
                    error: function () {
                        $.messager.alert('删除提示', '删除资产信息异常，请稍后再试！', 'error');
                        return;
                    }
                });
            }
        });
    }

    //批量删除资产
    function batDeleteAsset() {
        var row = $('#tableData').datagrid('getChecked');
        if (row.length == 0) {
            $.messager.alert('删除提示', '没有记录被选中！', 'info');
            return;
        }
        if (row.length > 0) {
            $.messager.confirm('删除确认', '确定要删除选中的' + row.length + '条资产信息吗？', function (r) {
                if (r) {
                    var ids = "";
                    for (var i = 0; i < row.length; i++) {
                        if (i == row.length - 1) {
                            ids += row[i].id;
                            break;
                        }
                        //alert(row[i].id);
                        ids += row[i].id + ",";
                    }
                    $.ajax({
                        type: "post",
                        url: "<%=path %>/asset/batchDelete.action",
                        dataType: "json",
                        async: false,
                        data: ({
                            assetIDs: ids,
                            clientIp: '<%=clientIp %>'
                        }),
                        success: function (tipInfo) {
                            var msg = tipInfo.showModel.msgTip;
                            if (msg == '成功') {
                                //$('#tableData').datagrid('reload');
                                //加载完以后重新初始化
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            }
                            else
                                $.messager.alert('删除提示', '删除资产信息失败，请稍后再试！', 'error');
                        },
                        //此处添加错误处理
                        error: function () {
                            $.messager.alert('删除提示', '删除资产信息异常，请稍后再试！', 'error');
                            return;
                        }
                    });
                }
            });
        }
    }

    //增加资产
    var url;
    var assetID = 0;

    function addAsset() {
        $('#assetDlg').dialog('open').dialog('setTitle', '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加资产');
        $(".window-mask").css({width: webW - 20, height: webH});
        $('#assetFM').form('clear');
        $("#userID").attr("disabled", "true");
        $("#assetNameID").focus();
        var row = {
            assetNameID: nameID,
            assetCategoryID: categoryID,
            userID: userID,
            supplierID: supplierID,
            status: 0,
            clientIp: '<%=clientIp %>'
        };
        $('#assetFM').form('load', row);
        $("#saveKeepAsset").show();
        assetID = 0;
        url = '<%=path %>/asset/create.action';
    }

    //保存资产信息
    $("#saveKeepAsset").unbind().bind({
        click: function () {
            $('#assetFM').form('submit', {
                url: url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (!result) {
                        $.messager.show({
                            title: '错误提示',
                            msg: '保存资产信息失败，请稍后重试!'
                        });
                    }
                    else {
                        $("#serialnum").val("");
                        $("#assetnum").val("").focus();
                        var opts = $("#tableData").datagrid('options');
                        showAssetDetails(opts.pageNumber, opts.pageSize);
                    }
                }
            });
        }
    });

    //保存资产信息
    $("#saveAsset").unbind().bind({
        click: function () {
            $('#assetFM').form('submit', {
                url: url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (!result) {
                        $.messager.show({
                            title: '错误提示',
                            msg: '保存资产信息失败，请稍后重试!'
                        });
                    }
                    else {
                        $('#assetDlg').dialog('close');
                        var opts = $("#tableData").datagrid('options');
                        showAssetDetails(opts.pageNumber, opts.pageSize);
                    }
                }
            });
        }
    });

    //编辑资产信息
    function editAsset(assetTotalInfo) {
        $("#saveKeepAsset").hide();
        var assetInfo = assetTotalInfo.split("AaBb");
        var row = {
            assetNameID: assetInfo[1],
            assetCategoryID: assetInfo[3],
            location: assetInfo[7],
            status: assetInfo[6],
            userID: assetInfo[4],
            price: assetInfo[2],
            purchasedate: assetInfo[5],
            supplierID: assetInfo[10],
            periodofvalidity: assetInfo[8],
            warrantydate: assetInfo[9],
            assetnum: assetInfo[13],
            serialnum: assetInfo[14],
            labels: assetInfo[11],
            supplierID: assetInfo[10],
            description: assetInfo[12],
            clientIp: '<%=clientIp %>'
        };

        if (assetInfo[6] == 1 || assetInfo[6] == 2)
            $("#userID").removeAttr("disabled");
        else
            $("#userID").attr("disabled", "true");

        $('#assetDlg').dialog('open').dialog('setTitle', '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑资产信息');
        $(".window-mask").css({width: webW - 20, height: webH});
        $('#assetFM').form('load', row);
        assetID = assetInfo[0];
        $("#assetNameID").focus();
        url = '<%=path %>/asset/update.action?assetID=' + assetInfo[0];
    }

    //搜索处理
    $("#searchBtn").unbind().bind({
        click: function () {
            showAssetDetails(1, initPageSize);
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            opts.pageNumber = 1;
            opts.pageSize = initPageSize;
            pager.pagination('refresh',
                {
                    pageNumber: 1,
                    pageSize: initPageSize
                });
        }
    });

    function showAssetDetails(pageNo, pageSize) {
        $.ajax({
            type: "post",
            url: "<%=path %>/asset/findBy.action",
            dataType: "json",
            data: ({
                assetNameID: $.trim($("#searchAssetNameID").val()),
                assetCategoryID: $.trim($("#searchCategoryID").val()),
                userID: $.trim($("#searchuserID").val()),
                status: $.trim($("#searchStatus").val()),
                supplierID: $.trim($("#searchSupplierID").val()),
                pageNo: pageNo,
                pageSize: pageSize
            }),
            success: function (data) {
                $("#tableData").datagrid('loadData', data);
                //$('#tableData').datagrid('reload');
            },
            //此处添加错误处理
            error: function () {
                $.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
                return;
            }
        });
    }

    //重置按钮
    $("#searchResetBtn").unbind().bind({
        click: function () {
            $("#searchAssetNameID").val("");
            $("#searchCategoryID").val("");
            $("#searchuserID").val("");
            $("#searchStatus").val("");
            $("#searchSupplierID").val("");

            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
</script>
</body>
</html>