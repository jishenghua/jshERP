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
    	<title>应用管理</title>
        <meta charset="utf-8">
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
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>名称：</td>
					<td>
						<input type="text" name="searchName" id="searchName"  style="width:100px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>种类：</td>
					<td>
						<input type="text" name="searchType" id="searchType"  style="width:100px;"/>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="应用列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="appDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="appFM" method="post" enctype="multipart/form-data">
	            <table>
	            <tr>
	            <td>代号</td>
	            <td style="padding:1px"><input name="Number" id="Number" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>名称</td>
	            <td style="padding:1px"><input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>类型</td>
	            <td style="padding:1px"><input name="Type" id="Type" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>图标</td>
	            <td style="padding:1px"><input name="Icon" id="Icon" type="file" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>链接</td>
	            <td style="padding:1px"><input name="URL" id="URL" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>宽度</td>
	            <td style="padding:1px"><input name="Width" id="Width" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>高度</td>
	            <td style="padding:1px"><input name="Height" id="Height" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>拉伸</td>
	            <td style="padding:1px"><input name="ReSize" id="ReSize" type="checkbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>最大化</td>
	            <td style="padding:1px"><input name="OpenMax" id="OpenMax" type="checkbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>Flash</td>
	            <td style="padding:1px"><input name="Flash" id="Flash" type="checkbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>种类</td>
	            <td style="padding:1px"><input name="ZL" id="ZL" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>排序号</td>
	            <td style="padding:1px"><input name="Sort" id="Sort" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>备注</td>
	            <td style="padding:1px"><input name="Remark" id="Remark" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
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
	        <a href="javascript:void(0)" id="saveApp" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelApp" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#appDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
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
				$('#appFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'应用列表',
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
					url:'<%=path %>/app/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '代号',field: 'Number',width:50},
			          { title: '应用名称',field: 'Name',width:100},
			          { title: '类型',field: 'Type',width:50},
			          { title: '图标',field: 'Icon',width:100,formatter:function(value,row)
			          {
			            if (value!= null)
				        {
				            return "<img alt='图标' style='width:32px;height:32px;' src=\"../../upload/images/deskIcon/"+value+"\" />";
				        }
			          }
			          },
			          { title: '链接',field: 'URL',width:100},
			          { title: '宽度',field: 'Width',width:50},
			          { title: '高度',field: 'Height',width:50},
			          { title: '拉伸',field: 'ReSize',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: '最大化',field: 'OpenMax',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: 'Flash',field: 'Flash',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: '种类',field: 'ZL',width:50},
			          { title: '排序号',field: 'Sort',width:50},
			          { title: '备注',field: 'Remark',width:50},
			          { title: '启用',field: 'Enabled',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Number + 'AaBb' + rec.Name + 'AaBb' + rec.Type + 'AaBb' + rec.Icon
							 + 'AaBb' + rec.URL + 'AaBb' + rec.Width + 'AaBb' + rec.Height + 'AaBb' + rec.ReSize + 'AaBb' + rec.OpenMax
							  + 'AaBb' + rec.Flash + 'AaBb' + rec.ZL + 'AaBb' + rec.Sort + 'AaBb' + rec.Remark + 'AaBb' + rec.Enabled;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editApp(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editApp(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteApp('+ rec.Id +');"/>&nbsp;<a onclick="deleteApp('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addApp',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addApp();
							}
						},
						{
							id:'deleteApp',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteApp();	
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
			    if(k == "13"&&(obj.id=="Number"||obj.id=="Name"|| obj.id=="Type"||obj.id=="Icon"|| obj.id=="URL"
			    ||obj.id=="Width"|| obj.id=="Height"||obj.id=="ZL"|| obj.id=="Sort"||obj.id=="Remark"))
			    {  
			        $("#saveApp").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchName" || obj.id=="searchType" ))
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
							showAppDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除应用信息
			function deleteApp(appID)
			{
				$.messager.confirm('删除确认','确定要删除此应用信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/app/delete.action",
							dataType: "json",
							data: ({
								appID : appID,
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
									$.messager.alert('删除提示','删除应用信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除应用信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除
			function batDeleteApp()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条应用信息吗？',function(r)
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
								url: "<%=path %>/app/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									appIDs : ids,
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
										$.messager.alert('删除提示','删除应用信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除应用信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var appID = 0;
			//保存编辑前的名称
			var orgApp = "";
			
			function addApp()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#appFM').form('clear');
				$('#appDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加应用信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#name").val("").focus();
	            
	            orgApp = "";
	            appID = 0;
	            url = '<%=path %>/app/create.action';
			}
			
			//保存信息
			$("#saveApp").unbind().bind({
				click:function()
				{
					if(!$('#appFM').form('validate'))
						return;
					else if(checkAppName())
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							fileElementId:'Icon',
							data: ({
								Number : $.trim($("#Number").val()),
								Name : $.trim($("#Name").val()),
								Type : $.trim($("#Type").val()),
								Icon : $("#Icon").val(),
								URL : $.trim($("#URL").val()),
								Width : $.trim($("#Width").val()),
								Height : $.trim($("#Height").val()),
								ReSize : $("#ReSize").is(':checked'),
								OpenMax : $("#OpenMax").is(':checked'),
								Flash : $("#Flash").is(':checked'),
								ZL : $.trim($("#ZL").val()),
								Sort : $.trim($("#Sort").val()),
								Remark : $.trim($("#Remark").val()),
								Enabled : $("#Enabled").is(':checked'),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#appDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showAppDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存应用信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存应用信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editApp(appTotalInfo)
	        {
	        	var appInfo = appTotalInfo.split("AaBb");	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#Number").focus().val(appInfo[1]);
	            $("#Name").val(appInfo[2]);
	            $("#Type").val(appInfo[3]);
	            //$("#Icon").val(appInfo[4]);
	            $("#URL").val(appInfo[5]);
	            $("#Width").val(appInfo[6]);
	            $("#Height").val(appInfo[7]);
	            $("#ReSize").attr("checked",appInfo[8]=='true'?true:false);
	            $("#OpenMax").attr("checked",appInfo[9]=='true'?true:false);
	            $("#Flash").attr("checked",appInfo[10]=='true'?true:false);
	            $("#ZL").val(appInfo[11]);
	            $("#Sort").val(appInfo[12]);
	            $("#Remark").val(appInfo[13]);
	            $("#Enabled").attr("checked",appInfo[14]=='true'?true:false);
	            
	            orgApp = appInfo[2];
                $('#appDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑应用信息');
                $(".window-mask").css({ width: webW ,height: webH});
                appID = appInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#name").val("").focus().val(appInfo[1]);
                url = '<%=path %>/app/update.action?appID=' + appInfo[0];
	        }
	        
	        //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkAppName()
	        {
	        	var name = $.trim($("#Name").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(name.length > 0 &&( orgApp.length ==0 || name != orgApp))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/app/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							appID : appID,
							name : name
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','应用名称已经存在','info');
								//alert("应用名称已经存在");
								//$("#name").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查应用名称是否存在异常，请稍后再试！','error');
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
					showAppDetails(1,initPageSize);	
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
			
			function showAppDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/app/findBy.action",
					dataType: "json",
					data: ({
						Name:$.trim($("#searchName").val()),
						Type:$.trim($("#searchType").val()),
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
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>