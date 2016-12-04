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
    	<title>单元管理</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
    	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
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
					<td>名称：</td>
					<td>
						<input type="text" name="searchName" id="searchName"  style="width:150px;"/>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="单元列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="buildingDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="buildingFM" method="post"  novalidate>
	            <table>
	            <tr>
	            <td>名称</td>
	            <td style="padding:5px"><input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>备注</td>
	            <td style="padding:5px"><input name="Remark" id="Remark" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>启用</td>
	            <td style="padding:1px"><input name="Enabled" id="Enabled" type="checkbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveBuilding" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelBuilding" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#buildingDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			var depotID = getUrlParam('ProjectId');  //获取传值ProjectId（仓库ProjectId）
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				initForm();
			});	
		
			//防止表单提交重复
			function initForm()
			{
				$('#buildingFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'单元列表',
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
					url:'<%=path %>/building/findBy.action?pageSize=' + initPageSize+'&ProjectId='+depotID,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '名称',field: 'Name',width:180},
			          { title: '备注',field: 'Remark',width:180},
			          { title: '启用',field: 'Enabled',width:180},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Name+ 'AaBb' + rec.Remark+ 'AaBb' + rec.Enabled;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editBuilding(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editBuilding(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteBuilding('+ rec.Id +');"/>&nbsp;<a onclick="deleteBuilding('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addBuilding',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addBuilding();
							}
						},
						{
							id:'deleteBuilding',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteBuilding();	
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
			    if(k == "13"&&(obj.id=="Name"||obj.id=="Remark"))
			    {  
			        $("#saveBuilding").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchName" ))
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
							showBuildingDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除单元信息
			function deleteBuilding(buildingID)
			{
				$.messager.confirm('删除确认','确定要删除此单元信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/building/delete.action",
							dataType: "json",
							data: ({
								buildingID : buildingID,
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
									$.messager.alert('删除提示','删除单元信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除单元信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除单元
			function batDeleteBuilding()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条单元信息吗？',function(r)
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
								url: "<%=path %>/building/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									buildingIDs : ids,
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
										$.messager.alert('删除提示','删除单元信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除单元信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var buildingID = 0;
			//保存编辑前的名称
			var orgBuilding = "";
			
			function addBuilding()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#buildingFM').form('clear');
				$('#buildingDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加单元信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#name").val("").focus();
	            
	            orgBuilding = "";
	            buildingID = 0;
	            url = '<%=path %>/building/create.action';
			}
			
			//保存信息
			$("#saveBuilding").unbind().bind({
				click:function()
				{
					if(!$('#buildingFM').form('validate'))
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								ProjectId : depotID,
								Name : $.trim($("#Name").val()),
								Remark : $.trim($("#Remark").val()),
								Enabled : $("#Enabled").is(':checked'),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#buildingDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showBuildingDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存单元信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存单元信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editBuilding(buildingTotalInfo)
	        {
	        	var buildingInfo = buildingTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#Name").val(buildingInfo[1]);
	            $("#Remark").val(buildingInfo[2]);
	            $("#Enabled").attr("checked",buildingInfo[3]=='true'?true:false);
	            
	            //orgBuilding = buildingInfo[1];
                $('#buildingDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑单元信息');
                $(".window-mask").css({ width: webW ,height: webH});
                buildingID = buildingInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#Name").val("").focus().val(buildingInfo[1]);
                url = '<%=path %>/building/update.action?buildingID=' + buildingInfo[0];
	        }
	        
	      //搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showBuildingDetails(1,initPageSize);	
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
			
			
			function showBuildingDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/building/findBy.action",
					dataType: "json",
					data: ({
						ProjectId:depotID,
						Name:$.trim($("#searchName").val()),
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
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>