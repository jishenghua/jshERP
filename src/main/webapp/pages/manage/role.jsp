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
    	<title>角色管理</title>
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
					<td>角色名称：</td>
					<td>
						<input type="text" name="searchName" id="searchName"  style="width:150px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>&nbsp;&nbsp;
						<a id="btnSetApp" class='easyui-linkbutton iframe iframe_LargeForm' href='#' title='分配应用'>分配应用</a>&nbsp;&nbsp;
						<a id="btnSetFunctions" class='easyui-linkbutton iframe iframe_LargeForm' href='#' title='分配功能'>分配功能</a>&nbsp;&nbsp;
						<a id="btnSetPushBtn" class='easyui-linkbutton iframe iframe_LargeForm' href='#' title='分配按钮'>分配按钮</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="角色列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="roleDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="roleFM" method="post"  novalidate>
	            <table>
	            <tr>
	            <td><label id="nameLabel">角色名称&nbsp;&nbsp;</label></td>
	            <td style="padding:5px"><input name="name" id="name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/></td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveRole" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelRole" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#roleDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				initForm();
				browserFit();	
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
				{
					$("#searchRemarkLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#nameLabel").empty().append("角色名称&nbsp;&nbsp;");
				}
				else
				{
					$("#searchRemarkLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#nameLabel").empty().append("角色名称&nbsp;");
				}
			}
			
			//防止表单提交重复
			function initForm()
			{
				$('#roleFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'角色列表',
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
					//selectOnCheck:false,
					//fitColumns:true,
					//单击行是否选中
					//checkOnSelect : false,
					url:'<%=path %>/role/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '角色名称',field: 'Name',width:200},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Name;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editRole(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editRole(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteRole('+ rec.Id +');"/>&nbsp;<a onclick="deleteRole('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addRole',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addRole();
							}
						},
						{
							id:'deleteRole',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteRole();	
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
			    if(k == "13"&&(obj.id=="name"||obj.id=="sort"|| obj.id=="remark" ))
			    {  
			        $("#saveRole").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchName" || obj.id=="searchRemark" ))
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
							showRoleDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除供应商信息
			function deleteRole(roleID)
			{
				$.messager.confirm('删除确认','确定要删除此角色信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/role/delete.action",
							dataType: "json",
							data: ({
								roleID : roleID,
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
									$.messager.alert('删除提示','删除角色信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除角色信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除供应商
			function batDeleteRole()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条角色信息吗？',function(r)
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
								url: "<%=path %>/role/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									roleIDs : ids,
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
										$.messager.alert('删除提示','删除角色信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除角色信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var roleID = 0;
			//保存编辑前的名称
			var orgRole = "";
			
			function addRole()
			{
				$("#clientIp").val('<%=clientIp %>');
				$("#sort").val("");
	            $("#remark").val("");
				$('#roleDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加角色信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#name").val("").focus();
	            //$('#roleFM').form('clear');
	            
	            orgRole = "";
	            roleID = 0;
	            url = '<%=path %>/role/create.action';
			}
			
			//保存信息
			$("#saveRole").unbind().bind({
				click:function()
				{
					if(!$('#roleFM').form('validate'))
						return;
					else if(checkRoleName())
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								name : $.trim($("#name").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#roleDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showRoleDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存角色信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存角色信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editRole(roleTotalInfo)
	        {
	        	var roleInfo = roleTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#name").focus().val(roleInfo[1]);
	            
	            orgRole = roleInfo[1];
                $('#roleDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑角色信息');
                $(".window-mask").css({ width: webW ,height: webH});
                roleID = roleInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#name").val("").focus().val(roleInfo[1]);
                url = '<%=path %>/role/update.action?roleID=' + roleInfo[0];
	        }
	        
	        //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkRoleName()
	        {
	        	var name = $.trim($("#name").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(name.length > 0 &&( orgRole.length ==0 || name != orgRole))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/role/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							roleID : roleID,
							name : name
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','角色名称已经存在','info');
								//alert("角色名称已经存在");
								//$("#name").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查角色名称是否存在异常，请稍后再试！','error');
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
					showRoleDetails(1,initPageSize);	
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
			
			function showRoleDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/role/findBy.action",
					dataType: "json",
					data: ({
						name:$.trim($("#searchName").val()),
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
			
			//分配应用
			$('#btnSetApp').click(function () {
			    var currentRow = $("#tableData").datagrid("getSelected");
	            if (currentRow == null) {
	                alert("请选择一条数据再操作！");
	                return false;
	            }
	            this.href = "<%=path %>/pages/manage/roleApp.jsp?id=" + currentRow.Id;
            });
            
            //分配功能
			$('#btnSetFunctions').click(function () {
			    var currentRow = $("#tableData").datagrid("getSelected");
	            if (currentRow == null) {
	                alert("请选择一条数据再操作！");
	                return false;
	            }
	            this.href = "<%=path %>/pages/manage/roleFunctions.jsp?id=" + currentRow.Id;
            });

			//分配按钮
			$('#btnSetPushBtn').click(function () {
				var currentRow = $("#tableData").datagrid("getSelected");
				if (currentRow == null) {
					alert("请选择一条数据再操作！");
					return false;
				}
				this.href = "<%=path %>/pages/manage/rolePushBtn.jsp?id=" + currentRow.Id;
			});
		</script>
	</body>
</html>