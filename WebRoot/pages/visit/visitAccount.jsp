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
    	<title>回访管理</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
    	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/colorbox/jquery.colorbox-min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/colorbox/colorboxSet.js"></script>
        <link href="<%=path %>/js/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body>
  		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
			    	<td>店面：</td>
					<td>
						<select name="searchProjectId" id="searchProjectId"  style="width:230px;"></select>
					</td>
					<td>楼号：</td>
					<td>
						<input type="text" name="searchLouHao" id="searchLouHao"  style="width:230px;"/>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="回访列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="visitAccountDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="visitAccountFM" method="post"  novalidate>
	            <table>
	            <tr>
	            <td>店面</td>
	            <td style="padding:5px">
                <select name="ProjectId" id="ProjectId" style="width:230px;height: 20px"></select>
                </td>
	            </tr>
	            <tr>
	            <td>楼号</td>
	            <td style="padding:5px"><input name="LouHao" id="LouHao" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>户号</td>
	            <td style="padding:5px"><input name="HuHao" id="HuHao" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>回访情况</td>
	            <td style="padding:5px"><input name="HuiFang" id="HuiFang" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>落实情况</td>
	            <td style="padding:5px"><input name="LuoShi" id="LuoShi" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>住户姓名</td>
	            <td style="padding:5px"><input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>电话</td>
	            <td style="padding:5px"><input name="Tel" id="Tel" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveVisitAccount" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelVisitAccount" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#visitAccountDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			var depotList = null;
			var depotID = null;
			//初始化界面
			$(function()
			{
				//初始化系统基础信息
				initSystemData();
				initSelectInfo();
				initTableData();
				ininPager();
				initForm();
			});	
		
			//初始化系统基础信息
			function initSystemData()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/depot/getBasicData.action",
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						depotList = systemInfo.showModel.map.depotList;
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
				
				if(depotList !=null)
				{
					options = "";
					for(var i = 0 ;i < depotList.length;i ++)
					{
						var depot = depotList[i];
						if(0 == i)
						{
							depotID = depot.id;
						}
						options += '<option value="' + depot.id + '">' + depot.name + '</option>';
					}	
					$("#ProjectId").empty().append(options);
					$("#searchProjectId").empty().append('<option value="">全部</option>').append(options);
				}
			}
			
			//防止表单提交重复
			function initForm()
			{
				$('#visitAccountFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'回访列表',
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
					//checkOnSelect : false,
					url:'<%=path %>/visitAccount/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '店面',field: 'ProjectName',width:80},
			          { title: '楼号',field: 'LouHao',width:80},
			          { title: '户号',field: 'HuHao',width:80},
			          { title: '回访情况',field: 'HuiFang',width:150},
			          { title: '落实情况',field: 'LuoShi',width:150},
			          { title: '住户姓名',field: 'Name',width:150},
			          { title: '电话',field: 'Tel',width:80},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.ProjectId+ 'AaBb' + rec.LouHao+ 'AaBb' + rec.HuHao
							+ 'AaBb' + rec.HuiFang+ 'AaBb' + rec.LuoShi+ 'AaBb' + rec.Name+ 'AaBb' + rec.Tel;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editVisitAccount(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editVisitAccount(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteVisitAccount('+ rec.Id +');"/>&nbsp;<a onclick="deleteVisitAccount('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addVisitAccount',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addVisitAccount();
							}
						},
						{
							id:'deleteVisitAccount',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteVisitAccount();	
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
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
			    if(k == "13"&&(obj.id=="LouHao"||obj.id=="HuHao"|| obj.id=="HuiFang"|| obj.id=="LuoShi"|| obj.id=="Name"|| obj.id=="Tel" ))
			    {  
			        $("#saveVisitAccount").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchLouHao"))
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
							showVisitAccountDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除回访信息
			function deleteVisitAccount(visitAccountID)
			{
				$.messager.confirm('删除确认','确定要删除此回访信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/visitAccount/delete.action",
							dataType: "json",
							data: ({
								visitAccountID : visitAccountID,
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								var msg = tipInfo.showModel.msgTip;
								if(msg == '成功')
								{
									//加载完以后重新初始化
									$("#searchBtn").click();
								}
								else
									$.messager.alert('删除提示','删除回访信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除回访信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除回访
			function batDeleteVisitAccount()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条回访信息吗？',function(r)
				 	{
	                    if (r)
	                    {
	                    	var ids = "";
	                        for(var i = 0;i < row.length; i ++)
	                        {
	                        	if(i == row.length-1)
	                        	{
	                        		ids += row[i].Id;
	                        		break;
	                        	}
	                        	//alert(row[i].id);
	                        	ids += row[i].Id + ",";
	                        }
	                        $.ajax({
								type:"post",
								url: "<%=path %>/visitAccount/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									visitAccountIDs : ids,
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
										$.messager.alert('删除提示','删除回访信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除回访信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var visitAccountID = 0;
			//保存编辑前的名称
			var orgVisitAccount = "";
			
			function addVisitAccount()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#visitAccountFM').form('clear');
				$('#visitAccountDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加回访信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#name").val("").focus();
	            
	            orgVisitAccount = "";
	            visitAccountID = 0;
	            url = '<%=path %>/visitAccount/create.action';
			}
			
			//保存信息
			$("#saveVisitAccount").unbind().bind({
				click:function()
				{
					if(!$('#visitAccountFM').form('validate'))
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								ProjectId : $.trim($("#ProjectId").val()),
								LouHao : $.trim($("#LouHao").val()),
								HuHao : $.trim($("#HuHao").val()),
								HuiFang : $.trim($("#HuiFang").val()),
								LuoShi : $.trim($("#LuoShi").val()),
								Name : $.trim($("#Name").val()),
								Tel : $.trim($("#Tel").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#visitAccountDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showVisitAccountDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存回访信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存回访信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editVisitAccount(visitAccountTotalInfo)
	        {
	        	var visitAccountInfo = visitAccountTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#ProjectId").focus().val(visitAccountInfo[1]);
	            $("#LouHao").val(visitAccountInfo[2]);
	            $("#HuHao").val(visitAccountInfo[3]);
	            $("#HuiFang").val(visitAccountInfo[4]);
	            $("#LuoShi").val(visitAccountInfo[5]);
	            $("#Name").val(visitAccountInfo[6]);
	            $("#Tel").val(visitAccountInfo[7]);
	            
	            //orgVisitAccount = visitAccountInfo[1];
                $('#visitAccountDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑回访信息');
                $(".window-mask").css({ width: webW ,height: webH});
                visitAccountID = visitAccountInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#name").val("").focus().val(visitAccountInfo[1]);
                url = '<%=path %>/visitAccount/update.action?visitAccountID=' + visitAccountInfo[0];
	        }
	        
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showVisitAccountDetails(1,initPageSize);	
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
			
			function showVisitAccountDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/visitAccount/findBy.action",
					dataType: "json",
					data: ({
						ProjectId:$.trim($("#searchProjectId").val()),
						LouHao:$.trim($("#searchLouHao").val()),
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
					$("#searchProjectId").val("");
					$("#searchLouHao").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
			
		</script>
	</body>
</html>