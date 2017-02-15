<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>资产管理</title>
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
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;position: center;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>操作模块：</td>
					<td>
						<input type="text" name="searchOperation" id="searchOperation"  style="width:90px;"/>
					</td>
				
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>操作人员：</td>
					<td>
						<select name="searchUsernameID" id="searchUsernameID"  style="width:90px;" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>操作IP：</td>
					<td>
						<input type="text" name="searchIP" id="searchIP"  style="width:90px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>操作状态：</td>
					<td>
						<select name="searchStatus" id="searchStatus"  style="width:90px;">
							<option value="">请选择</option>
							<option value="0">成功</option>
							<option value="1">失败</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<input type="text" name="searchBeginTime" id="searchBeginTime" class="easyui-datebox" style="width:90px;"/> 
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>结束时间：</td>
					<td>
						<input type="text" name="searchEndTime" id="searchEndTime" class="easyui-datebox" style="width:90px;"/> 
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>操作详情：</td>
					<td>
						<input type="text" name="searchDesc" id="searchDesc"  style="width:90px;"/>
					</td>	
					<td>&nbsp;</td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
					</td>															
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="操作日志列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="height:340px;top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		<script type="text/javascript">
			var userList = null;
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				//初始化系统基础信息
				initSystemData();
				//初始化页面系统基础信息选项卡
				initSelectInfo();	
			});	
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'供应商列表',
					//iconCls:'icon-save',
					//width:700,
					height:heightInfo,
					nowrap: false,
					rownumbers: true,
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
					url:'<%=path %>/log/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
			          { title: '操作模块',field: 'operation',width:120},
			          { title: '操作人员', field: 'username',width:100,align:"center"},
			          { title: '操作IP', field: 'clientIP',width:90,align:"center"},
			          { title: '操作时间',field: 'createTime',width:150,align:"center"},
			          { title: '操作详情',field: 'details',width:380},
			          { title: '操作状态',field: 'status',width:70},
			          { title: '备注',field: 'remark',width:180}
					]],
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
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchOperation" || obj.id=="searchUsernameID"|| obj.id=="searchIP" 
			    	|| obj.id=="searchStatus" || obj.id=="searchBeginTime" || obj.id=="searchEndTime"
			    	|| obj.id=="searchDesc"))
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
							showLogDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			
			//初始化系统基础信息
			function initSystemData()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/log/getBasicData.action",
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						//成功关闭loading
						userList = systemInfo.showModel.map.userList;
						var msgTip = systemInfo.showModel.msgTip;
						if(msgTip == "exceptoin")
						{
							$.messager.alert('提示','查找系统基础信息异常,请与管理员联系！','error');
							return;
						}	
					}
				});				
			}
			
			//初始化页面选项卡
			function initSelectInfo()
			{
				var options = "";
				
				if(userList !=null)
				{
					options = "";
					for(var i = 0 ;i < userList.length;i ++)
					{
						var user = userList[i];
						if(0 == i)
						{
							userID = user.id
						}
						options += '<option value="' + user.id + '">' + user.username + '</option>';
					}	
					$("#searchUsernameID").empty().append('<option value="">请选择</option>').append(options);
				}
			}
			
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showLogDetails(1,initPageSize);	
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
			
			function showLogDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/log/findBy.action",
					dataType: "json",
					data: ({
						operation:$.trim($("#searchOperation").val()),
						usernameID:$.trim($("#searchUsernameID").val()),
						clientIp:$.trim($("#searchIP").val()),
						status:$.trim($("#searchStatus").val()),
						beginTime:$.trim($("#searchBeginTime").datebox("getValue")),
						endTime:$.trim($("#searchEndTime").datebox("getValue")),
						contentdetails:$.trim($("#searchDesc").val()),
						pageNo:pageNo,
						pageSize:pageSize
					}),
					success: function (data)
					{
						$("#tableData").datagrid('loadData',data);
						//$('#tableData').datagrid('reload');
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
				click:function()
				{
					$("#searchOperation").val("");
					$("#searchUsernameID").val("");
					$("#searchIP").val("");
					$("#searchStatus").val("");
					$("#searchBeginTime").datebox("clear");
					$("#searchEndTime").datebox("clear");
					$("#searchDesc").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>