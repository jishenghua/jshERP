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
        <title>收支项目</title>

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
                    <td id="searchTypeLabel">类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
                    <td>
                    <select name="searchType" id="searchType"  style="width:70px;">
                        <option value="">全部</option>
                        <option value="收入">收入</option>
                        <option value="支出">支出</option>
                    </select>
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
        <div id = "tablePanel"  class="easyui-panel" style="padding:1px;top:300px;" title="收支项目" iconCls="icon-list" collapsible="true" closable="false">
            <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
        </div>
        <div id="inOutItemDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
                closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
            <form id="inOutItemFM" method="post" novalidate>
                <div class="fitem" style="padding:5px">
                    <label id="nameLabel">名&nbsp;&nbsp;&nbsp;&nbsp;称</label>
                    <input name="name" id="name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="typeLabel">类&nbsp;&nbsp;&nbsp;&nbsp;型</label>
                 <select name="type" id="type"  style="width:230px;">
                        <option value="收入">收入</option>
                        <option value="支出">支出</option>
                 </select>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="remarkLabel">备&nbsp;&nbsp;&nbsp;&nbsp;注</label>
                    <textarea name="remark" id="remark" rows="2" cols="2" style="width: 230px;"></textarea>
                </div>
                <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" id="saveInOutItem" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
            <a href="javascript:void(0)" id="cancelInOutItem" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#inOutItemDlg').dialog('close')">取消</a>
        </div>
        
        <script type="text/javascript">
            //初始化界面
            $(function()
            {
                initTableData();
                ininPager();
                browserFit();   
            }); 
            
            //浏览器适配
            function browserFit()
            {
                if(getOs()=='MSIE')
                {
                    $("#searchTypeLabel").empty().append("类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：");
                    $("#searchRemarkLabel").empty().append("备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：");
                    $("#nameLabel").empty().append("名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称");
                    $("#typeLabel").empty().append("类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型");
                    $("#remarkLabel").empty().append("备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注");
                }
                else
                {
                    $("#searchTypeLabel").empty().append("类&nbsp;&nbsp;&nbsp;&nbsp;型：");
                    $("#searchRemarkLabel").empty().append("备&nbsp;&nbsp;&nbsp;&nbsp;注：");
                    $("#nameLabel").empty().append("名&nbsp;&nbsp;&nbsp;&nbsp;称");
                    $("#typeLabel").empty().append("类&nbsp;&nbsp;&nbsp;&nbsp;型");
                    $("#remarkLabel").empty().append("备&nbsp;&nbsp;&nbsp;&nbsp;注");                   
                }
            }
            
            //初始化表格数据
            function initTableData()
            {
                $('#tableData').datagrid({
                    //title:'收支项目',
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
                    url:'<%=path %>/inOutItem/findBy.action?pageSize=' + initPageSize,
                    pagination: true,
                    //自动截取数据
                    //nowrap : true,
                    //loadFilter: pagerFilter,
                    pageSize: initPageSize,
                    pageList: initPageNum,
                    columns:[[
                      { field: 'id',width:35,align:"center",checkbox:true},
                      { title: '名称',field: 'name',width:200},
                      { title: '类型', field: 'type',width:100,align:"center"},
                      { title: '描述',field: 'remark',width:200},
                      { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
                        {
                            var str = '';
                            var rowInfo = rec.id + 'AaBb' + rec.name +'AaBb' + rec.type + 'AaBb'+ rec.remark;
                            if(1 == value)
                            {
                                str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editInOutItem(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editInOutItem(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
                                str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteInOutItem(\'' + rowInfo + '\');"/>&nbsp;<a onclick="deleteInOutItem(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
                            }
                            return str;
                        }
                      }
                    ]],
                    toolbar:[
                        {
                            id:'addInOutItem',
                            text:'增加',
                            iconCls:'icon-add',
                            handler:function()
                            {
                                addInOutItem();
                            }
                        },
                        {
                            id:'deleteInOutItem',
                            text:'删除',
                            iconCls:'icon-remove',
                            handler:function()
                            {
                                batDeleteInOutItem();    
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
            $(document).keydown(function(event)
            {  
                //兼容 IE和firefox 事件 
                var e = window.event || event;  
                var k = e.keyCode||e.which||e.charCode;  
                //兼容 IE,firefox 兼容  
                var obj = e.srcElement ? e.srcElement : e.target;  
                //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
                if(k == "13"&&(obj.id=="name" || obj.id=="remark"))
                {  
                    $("#saveInOutItem").click();
                }  
                
                //搜索按钮添加快捷键
                if(k == "13"&&(obj.id=="searchName" || obj.id=="searchRemark"))
                {  
                    $("#searchBtn").click();
                }  
            }); 
            //分页信息处理
            function ininPager()
            {
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
                            showInOutItemDetails(pageNum,pageSize);
                        }  
                    }); 
                }
                catch (e) 
                {
                    $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
                }
            }
            
            //删除收支项目
            function deleteInOutItem(inOutItemInfo)
            {
                $.messager.confirm('删除确认','确定要删除此收支项目吗？',function(r)
                {
                    if (r)
                    {
                        var inOutItemTotalInfo = inOutItemInfo.split("AaBb");
                        $.ajax({
                            type:"post",
                            url: "<%=path %>/inOutItem/delete.action",
                            dataType: "json",
                            data: ({
                                inOutItemID : inOutItemTotalInfo[0],
                                name:inOutItemTotalInfo[1],
                                clientIp:'<%=clientIp %>'
                            }),
                            success: function (tipInfo)
                            {
                                var msg = tipInfo.showModel.msgTip;
                                if(msg == '成功')
                                    //加载完以后重新初始化
                                    $("#searchBtn").click();
                                else
                                    $.messager.alert('删除提示','删除收支项目失败，请稍后再试！','error');
                            },
                            //此处添加错误处理
                            error:function()
                            {
                                $.messager.alert('删除提示','删除收支项目异常，请稍后再试！','error');
                                return;
                            }
                        });         
                    }
                });
            }
            
            //批量删除收支项目
            function batDeleteInOutItem()
            {
                var row = $('#tableData').datagrid('getChecked');
                if(row.length == 0)
                {
                    $.messager.alert('删除提示','没有记录被选中！','info');             
                    return; 
                }
                if(row.length > 0)
                {
                    $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条收支项目吗？',function(r)
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
                                url: "<%=path %>/inOutItem/batchDelete.action",
                                dataType: "json",
                                async :  false,
                                data: ({
                                    inOutItemIDs : ids,
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
                                        $.messager.alert('删除提示','删除收支项目失败，请稍后再试！','error');
                                },
                                //此处添加错误处理
                                error:function()
                                {
                                    $.messager.alert('删除提示','删除收支项目异常，请稍后再试！','error');
                                    return;
                                }
                            }); 
                        }
                    });
                 }
            }
            
            //增加收支项目
            var url;
            var inOutItemID = 0;
            //保存编辑前的名称
            var orgInOutItem = "";
            
            function addInOutItem()
            {
                $('#inOutItemDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加收支项目');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#inOutItemFM').form('clear');
                
                var row = {
                    clientIp:'<%=clientIp %>'
                };
                $('#inOutItemFM').form('load',row);
                
                $("#inOutItem").focus();
                orgInOutItem = "";
                inOutItemID = 0;
                url = '<%=path %>/inOutItem/create.action';
            }
            
            //保存收支项目
            $("#saveInOutItem").unbind().bind({
                click:function()
                {
                    if(checkInOutItemName())
                        return;
                    if(!$("#type").val()){
                        $.messager.alert('提示','请选择类型！','warning');
                        return;
                    }
                    $('#inOutItemFM').form('submit',{
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
                                    msg: '保存收支项目失败，请稍后重试!'
                                });
                            } 
                            else 
                            {
                                $('#inOutItemDlg').dialog('close');
                                //$('#tableData').datagrid('reload');
                                //加载完以后重新初始化
                                //$("#searchBtn").click(); 
                                var opts = $("#tableData").datagrid('options'); 
                                showInOutItemDetails(opts.pageNumber,opts.pageSize);   
                            }
                        }
                    }); 
                }
            });
            
            //编辑收支项目
            function editInOutItem(inOutItemTotalInfo)
            {
                var inOutItemInfo = inOutItemTotalInfo.split("AaBb");
                var row = {
                    name : inOutItemInfo[1],
                    type : inOutItemInfo[2],
                    remark : inOutItemInfo[3],
                    clientIp:'<%=clientIp %>'
                };
                orgInOutItem = inOutItemInfo[1];
                $('#inOutItemDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑收支项目');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#inOutItemFM').form('load',row);
                inOutItemID = inOutItemInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#inOutItem").val("").focus().val(inOutItemInfo[1]);
                url = '<%=path %>/inOutItem/update.action?inOutItemID=' + inOutItemInfo[0];
            }
            
            //检查收支项目 名称是否存在 ++ 重名无法提示问题需要跟进
            function checkInOutItemName()
            {
                var inOutItemName = $.trim($("#name").val());
                //表示是否存在 true == 存在 false = 不存在
                var flag = false;
                //开始ajax名称检验，不能重名
                if(inOutItemName.length > 0 &&( orgInOutItem.length ==0 || inOutItemName != orgInOutItem))
                {
                    $.ajax({
                        type:"post",
                        url: "<%=path %>/inOutItem/checkIsNameExist.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            inOutItemID : inOutItemID,
                            name : inOutItemName
                        }),
                        success: function (tipInfo)
                        {
                            flag = tipInfo;
                            if(tipInfo)
                            {
                                $.messager.alert('提示','收支项目名称已经存在','info');
                                return;
                            }
                        },
                        //此处添加错误处理
                        error:function()
                        {
                            $.messager.alert('提示','检查收支项目名称是否存在异常，请稍后再试！','error');
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
                    showInOutItemDetails(1,initPageSize);    
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
            
            function showInOutItemDetails(pageNo,pageSize)
            {
                $.ajax({
                    type:"post",
                    url: "<%=path %>/inOutItem/findBy.action",
                    dataType: "json",
                    data: ({
                        name:$.trim($("#searchName").val()),
                        type:$.trim($("#searchType").val()),
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
            
            //重置按钮
            $("#searchResetBtn").unbind().bind({
                click:function(){
                    $("#searchName").val("");
                    $("#searchType").val("");
                    $("#searchRemark").val("");                 
                    //加载完以后重新初始化
                    $("#searchBtn").click();
                }   
            });
        </script>
    </body>
</html>
