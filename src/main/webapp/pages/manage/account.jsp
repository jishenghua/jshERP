<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>结算账户</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!-- 指定以IE8的方式来渲染 -->
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
        <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
        <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
        <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
    </head>
    <body>
        <!-- 查询 -->
        <div id = "searchPanel" class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
            <table id="searchTable">
                <tr>
                    <td>名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                    <td>
                        <input type="text" name="searchName" id="searchName"  style="width:70px;"/>
                    </td>
                
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td id="searchSerialNoLabel">编&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
                    <td>
                        <input type="text" name="searchSerialNo" id="searchSerialNo"  style="width:70px;"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td id="searchRemarkLabel">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                    <td>
                        <input type="text" name="searchRemark" id="searchRemark"  style="width:70px;"/>
                    </td>
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
        <div id = "tablePanel"  class="easyui-panel" style="padding:1px;top:300px;" title="结算账户" iconCls="icon-list" collapsible="true" closable="false">
            <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
        </div>
        <div id="accountDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
                closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
            <form id="accountFM" method="post" novalidate>
                <div class="fitem" style="padding:5px">
                    <label id="nameLabel">名称</label>
                    <input name="name" id="name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="serialNoLabel">编号</label>
                    <input name="serialNo" id="serialNo" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="initialAmountLabel">期初金额</label>
                    <input name="initialAmount" id="initialAmount" type="text" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="currentAmountLabel">当前余额</label>
                    <input name="currentAmount" id="currentAmount" type="text" disabled="disabled" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="remarkLabel">备注</label>
                    <textarea name="remark" id="remark" rows="2" cols="2" style="width: 230px;"></textarea>
                </div>
                <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" id="saveAccount" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
            <a href="javascript:void(0)" id="cancelAccount" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#accountDlg').dialog('close')">取消</a>
        </div>
        <div id="accountDetailListDlg" class="easyui-dialog" style="width:900px;height:500px;padding:10px 20px" closed="true" modal="true" collapsible="false" closable="true">
            <table id="accountTableData" style="top:50px;border-bottom-color:#FFFFFF"></table>
        </div>
        
        <script type="text/javascript">
            //初始化界面
            $(function(){
                initTableData();
                ininPager();  
            }); 
            
            //初始化表格数据
            function initTableData(){
                $('#tableData').datagrid({
                    //title:'结算账户',
                    //iconCls:'icon-save',
                    //width:700,
                    height:heightInfo,
                    nowrap: false,
                    rownumbers: false,
                    //动画效果
                    animate:false,
                    //选中单行
                    singleSelect : true,
                    collapsible:false,
                    selectOnCheck:false,
                    //fitColumns:true,
                    //单击行是否选中
                    checkOnSelect : false,
                    //交替出现背景
                    striped : true,
                    url:'<%=path %>/account/findBy.action?pageSize=' + initPageSize,
                    pagination: true,
                    //自动截取数据
                    //nowrap : true,
                    //loadFilter: pagerFilter,
                    pageSize: initPageSize,
                    pageList: initPageNum,
                    columns:[[
                      { field: 'id',width:35,align:"center",checkbox:true},
                      { title: '名称',field: 'name',width:100},
                      { title: '编号', field: 'serialNo',width:150,align:"center"},
                      { title: '期初金额', field: 'initialAmount',width:100,align:"center"},
                      { title: '当前余额', field: 'currentAmount',width:100,align:"center"},
                      { title: '备注',field: 'remark',width:100},
                      { title: '操作',field: 'op',align:"center",width:180,formatter:function(value,rec)
                        {
                            var str = '';
                            var rowInfo = rec.id + 'AaBb' + rec.name +'AaBb' + rec.serialNo +'AaBb' + rec.initialAmount +'AaBb' + rec.currentAmount + 'AaBb'+ rec.remark;
                            if(1 == value)
                            {
                                str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showAccountInOutList(\'' + rowInfo + '\');"/>&nbsp;<a onclick="showAccountInOutList(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">流水</a>&nbsp;&nbsp;';
                                str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAccount(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editAccount(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
                                str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAccount(\'' + rowInfo + '\');"/>&nbsp;<a onclick="deleteAccount(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
                            }
                            return str;
                        }
                      }
                    ]],
                    toolbar:[
                        {
                            id:'addAccount',
                            text:'增加',
                            iconCls:'icon-add',
                            handler:function()
                            {
                                addAccount();
                            }
                        },
                        {
                            id:'deleteAccount',
                            text:'删除',
                            iconCls:'icon-remove',
                            handler:function()
                            {
                                batDeleteAccount();    
                            }
                        }
                    ],
                    onLoadError:function()
                    {
                        $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                        return;
                    }    
                });
            }
            
            //初始化键盘enter事件
            $(document).keydown(function(event){  
                //兼容 IE和firefox 事件 
                var e = window.event || event;  
                var k = e.keyCode||e.which||e.charCode;  
                //兼容 IE,firefox 兼容  
                var obj = e.srcElement ? e.srcElement : e.target;  
                //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
                if(k == "13"&&(obj.id=="name" || obj.id=="serialNo" || obj.id=="initialAmount" || obj.id=="currentAmount" || obj.id=="remark"))
                {  
                    $("#saveAccount").click();
                }  
                
                //搜索按钮添加快捷键
                if(k == "13"&&(obj.id=="searchName" || obj.id=="searchSerialNo" || obj.id=="searchRemark"))
                {  
                    $("#searchBtn").click();
                }  
            }); 
            //分页信息处理
            function ininPager(){
                try
                {
                    var opts = $("#tableData").datagrid('options');  
                    var pager = $("#tableData").datagrid('getPager'); 
                    pager.pagination({  
                        onSelectPage:function(pageNum, pageSize)
                        {  
                            opts.pageNumber = pageNum;  
                            opts.pageSize = pageSize;  
                            pager.pagination('refresh',
                            {  
                                pageNumber:pageNum,  
                                pageSize:pageSize  
                            });  
                            showAccountDetails(pageNum,pageSize);
                        }  
                    }); 
                }
                catch (e) 
                {
                    $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
                }
            }
            
            //删除结算账户
            function deleteAccount(accountInfo){
                $.messager.confirm('删除确认','确定要删除此结算账户吗？',function(r)
                {
                    if (r)
                    {
                        var accountTotalInfo = accountInfo.split("AaBb");
                        $.ajax({
                            type:"post",
                            url: "<%=path %>/account/delete.action",
                            dataType: "json",
                            data: ({
                                accountID : accountTotalInfo[0],
                                name:accountTotalInfo[1],
                                clientIp:'<%=clientIp %>'
                            }),
                            success: function (tipInfo)
                            {
                                var msg = tipInfo.showModel.msgTip;
                                if(msg == '成功')
                                    //加载完以后重新初始化
                                    $("#searchBtn").click();
                                else
                                    $.messager.alert('删除提示','删除结算账户失败，请稍后再试！','error');
                            },
                            //此处添加错误处理
                            error:function()
                            {
                                $.messager.alert('删除提示','删除结算账户异常，请稍后再试！','error');
                                return;
                            }
                        });         
                    }
                });
            }
            
            //批量删除结算账户
            function batDeleteAccount(){
                var row = $('#tableData').datagrid('getChecked');
                if(row.length == 0)
                {
                    $.messager.alert('删除提示','没有记录被选中！','info');             
                    return; 
                }
                if(row.length > 0)
                {
                    $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条结算账户吗？',function(r)
                    {
                        if (r)
                        {
                            var ids = "";
                            for(var i = 0;i < row.length; i ++)
                            {
                                if(i == row.length-1)
                                {
                                    ids += row[i].id;
                                    break;
                                }
                                ids += row[i].id + ",";
                            }
                            $.ajax({
                                type:"post",
                                url: "<%=path %>/account/batchDelete.action",
                                dataType: "json",
                                async :  false,
                                data: ({
                                    accountIDs : ids,
                                    clientIp:'<%=clientIp %>'
                                }),
                                success: function (tipInfo)
                                {
                                    var msg = tipInfo.showModel.msgTip;
                                    if(msg == '成功')
                                    {
                                        //加载完以后重新初始化
                                        $("#searchBtn").click();
                                        $(":checkbox").attr("checked",false);
                                    }
                                    else
                                        $.messager.alert('删除提示','删除结算账户失败，请稍后再试！','error');
                                },
                                //此处添加错误处理
                                error:function()
                                {
                                    $.messager.alert('删除提示','删除结算账户异常，请稍后再试！','error');
                                    return;
                                }
                            }); 
                        }
                    });
                 }
            }
            
            //增加结算账户
            var url;
            var accountID = 0;
            //保存编辑前的名称
            var orgaccount = "";
            
            function addAccount(){
                $('#accountDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加结算账户');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#accountFM').form('clear');
                
                var row = {
                    clientIp:'<%=clientIp %>'
                };
                $('#accountFM').form('load',row);
                
                $("#account").focus();
                orgAccount = "";
                accountID = 0;
                url = '<%=path %>/account/create.action';
            }
            
            //保存结算账户
            $("#saveAccount").unbind().bind({
                click:function()
                {
                    if(checkAccountName())
                        return;
                    
                    $('#accountFM').form('submit',{
                        url: url,
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
                                    msg: '保存结算账户失败，请稍后重试!'
                                });
                            } 
                            else 
                            {
                                $('#accountDlg').dialog('close');
                                //$('#tableData').datagrid('reload');
                                //加载完以后重新初始化
                                //$("#searchBtn").click(); 
                                var opts = $("#tableData").datagrid('options'); 
                                showAccountDetails(opts.pageNumber,opts.pageSize);   
                            }
                        }
                    }); 
                }
            });
            
            //编辑结算账户
            function editAccount(accountTotalInfo){
                var accountInfo = accountTotalInfo.split("AaBb");
                var row = {
                    name : accountInfo[1],
                    serialNo : accountInfo[2],
                    initialAmount : accountInfo[3],
                    currentAmount : accountInfo[4],
                    remark : accountInfo[5],
                    clientIp:'<%=clientIp %>'
                };
                orgAccount = accountInfo[1];
                $('#accountDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑结算账户');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#accountFM').form('load',row);
                accountID = accountInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#account").val("").focus().val(accountInfo[1]);
                url = '<%=path %>/account/update.action?accountID=' + accountInfo[0];
            }
            
            //检查结算账户 名称是否存在 ++ 重名无法提示问题需要跟进
            function checkAccountName(){
                var accountName = $.trim($("#name").val());
                //表示是否存在 true == 存在 false = 不存在
                var flag = false;
                //开始ajax名称检验，不能重名
                if(accountName.length > 0 &&( orgAccount.length ==0 || accountName != orgAccount))
                {
                    $.ajax({
                        type:"post",
                        url: "<%=path %>/account/checkIsNameExist.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            accountID : accountID,
                            name : accountName
                        }),
                        success: function (tipInfo)
                        {
                            flag = tipInfo;
                            if(tipInfo)
                            {
                                $.messager.alert('提示','结算账户名称已经存在','info');
                                return;
                            }
                        },
                        //此处添加错误处理
                        error:function()
                        {
                            $.messager.alert('提示','检查结算账户名称是否存在异常，请稍后再试！','error');
                            return;
                        }
                    }); 
                }
                return flag;
            }
            
            //搜索处理
            $("#searchBtn").unbind().bind({
                click:function()
                {
                    showAccountDetails(1,initPageSize);    
                    var opts = $("#tableData").datagrid('options');  
                    var pager = $("#tableData").datagrid('getPager'); 
                    opts.pageNumber = 1;  
                    opts.pageSize = initPageSize;  
                    pager.pagination('refresh',
                    {  
                        pageNumber:1,  
                        pageSize:initPageSize  
                    });  
                }
            });
            
            function showAccountDetails(pageNo,pageSize){
                $.ajax({
                    type:"post",
                    url: "<%=path %>/account/findBy.action",
                    dataType: "json",
                    data: ({
                        name:$.trim($("#searchName").val()),
                        serialNo:$.trim($("#searchSerialNo").val()),
                        remark:$.trim($("#searchRemark").val()),
                        pageNo:pageNo,
                        pageSize:pageSize
                    }),
                    success: function (data)
                    {
                        $("#tableData").datagrid('loadData',data);
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
                        return;
                    }
                });
            }

            function showAccountInOutList(accountInfo){
                var info = accountInfo.split("AaBb");
                var accountId = info[0];
                $('#accountDetailListDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;查看账户流水');
                $(".window-mask").css({ width: webW ,height: webH});
                initAccountDetailData(accountId);
                getAccountInOutList(accountId,1,initPageSize);
                ininAccountDetailPager(accountId);
            }

            //初始化表格数据
            function initAccountDetailData(accountId){
                $('#accountTableData').datagrid({
                    height:heightInfo,
                    nowrap: false,
                    rownumbers: false,
                    //动画效果
                    animate:false,
                    //选中单行
                    singleSelect : true,
                    collapsible:false,
                    selectOnCheck:false,
                    //单击行是否选中
                    checkOnSelect : false,
                    //交替出现背景
                    striped : true,
                    pagination: true,
                    pageSize: initPageSize,
                    pageList: initPageNum,
                    columns:[[
                        { title: '单据编号',field: 'number',width:150},
                        { title: '类型', field: 'type',width:100},
                        { title: '单位信息', field: 'supplierName',width:150},
                        { title: '金额', field: 'changeAmount',width:80},
                        { title: '入库出库日期',field: 'operTime',width:180}
                    ]],
                    onLoadError:function() {
                        $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                        return;
                    }
                });
            }

            //分页信息处理
            function ininAccountDetailPager(accountId){
                try {
                    var opts = $("#accountTableData").datagrid('options');
                    var pager = $("#accountTableData").datagrid('getPager');
                    pager.pagination({
                        onSelectPage:function(pageNum, pageSize) {
                            opts.pageNumber = pageNum;
                            opts.pageSize = pageSize;
                            pager.pagination('refresh', {
                                pageNumber:pageNum,
                                pageSize:pageSize
                            });
                            getAccountInOutList(accountId,pageNum,pageSize);
                        }
                    });
                }
                catch (e) {
                    $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
                }
            }

            function getAccountInOutList(accountId,pageNo,pageSize){
                $.ajax({
                    type:"get",
                    url: "<%=path %>/account/findAccountInOutList.action",
                    dataType: "json",
                    data: ({
                        accountID: accountId,
                        pageNo:pageNo,
                        pageSize:pageSize
                    }),
                    success: function (res) {
                        $("#accountTableData").datagrid('loadData',res);
                    },
                    //此处添加错误处理
                    error:function() {
                        $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
                        return;
                    }
                });
            }
            
            //重置按钮
            $("#searchResetBtn").unbind().bind({
                click:function(){
                    $("#searchName").val("");
                    $("#searchSerialNo").val("");
                    $("#searchRemark").val("");                 
                    //加载完以后重新初始化
                    $("#searchBtn").click();
                }   
            });
        </script>
    </body>
</html>
