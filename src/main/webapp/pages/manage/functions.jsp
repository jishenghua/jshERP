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
    	<title>功能管理</title>
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
					<td>类型: </td>
					<td>&nbsp;</td>
					<td>
						<select name="searchType" id="searchType" style="width:100px; height:20px">
					    	<option value="">全部</option>
	                		<option value="电脑版">电脑版</option>
	                		<option value="手机版">手机版</option>
	                	</select>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="功能列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="functionsDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="functionsFM" method="post"  novalidate>
	            <table>
					<tr>
					<td style="width:65px;">编号</td>
					<td style="padding:5px"><input name="Number" id="Number" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
					<td>名称</td>
					<td style="padding:5px"><input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,20]'" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
					<td>上级编号</td>
					<td style="padding:5px"><input name="PNumber" id="PNumber" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
					<td>链接</td>
					<td style="padding:5px"><input name="URL" id="URL" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
					<td>排序</td>
					<td style="padding:5px"><input name="Sort" id="Sort" class="easyui-textbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
						<td>功能按钮</td>
						<td style="padding:5px">
							<input id="PushBtn" name="PushBtn" style="width:230px;" />
						</td>
					</tr>
					<tr>
						<td>收缩</td>
						<td style="padding:5px"><input name="State" id="State" type="checkbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
						<td>启用</td>
						<td style="padding:5px"><input name="Enabled" id="Enabled" type="checkbox" style="width: 230px;height: 20px"/></td>
					</tr>
					<tr>
						<td>类型</td>
						<td style="padding:5px"><select name="Type" id="Type" style="width: 230px;height: 20px">
							<option value="电脑版">电脑版</option>
							<option value="手机版">手机版</option></select>
						</td>
					</tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveFunctions" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelFunctions" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#functionsDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				initForm();
				bindEvent();
			});	
			
			//防止表单提交重复
			function initForm()
			{
				$('#functionsFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'功能列表',
					//iconCls:'icon-save',
					//width:700,
					height:heightInfo,
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
					url:'<%=path %>/functions/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '编号 ',field: 'Number',width:60},
			          { title: '名称',field: 'Name',width:80},
			          { title: '上级编号',field: 'PNumber',width:60},
			          { title: '链接',field: 'URL',width:120},
			          { title: '排序',field: 'Sort',width:50},
			          { title: '收缩',field: 'State',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: '启用',field: 'Enabled',width:50,formatter:function(value){
						  return value? "开":"关";
					  }},
			          { title: '类型',field: 'Type',width:50},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Number+ 'AaBb' + rec.Name+ 'AaBb' + rec.PNumber+ 'AaBb' + rec.URL
							+ 'AaBb' + rec.State+ 'AaBb' + rec.Sort+ 'AaBb' + rec.Enabled+ 'AaBb' + rec.Type + 'AaBb' + rec.PushBtn;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editFunctions(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editFunctions(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteFunctions('+ rec.Id +');"/>&nbsp;<a onclick="deleteFunctions('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addFunctions',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addFunctions();
							}
						},
						{
							id:'deleteFunctions',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteFunctions();	
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
			    if(k == "13"&&(obj.id=="Name"||obj.id=="PNumber"|| obj.id=="URL" ))
			    {  
			        $("#saveFunctions").click();
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
							showFunctionsDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}

			//绑定事件
			function bindEvent(){
				$('#PushBtn').combobox({
					url: '<%=path %>/js/pages/manage/pushBtn.json',
					valueField:'id',
					textField:'text',
					panelHeight:120,
					multiple:true
				});
			}
			
			//删除供应商信息
			function deleteFunctions(functionsID)
			{
				$.messager.confirm('删除确认','确定要删除此功能信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/functions/delete.action",
							dataType: "json",
							data: ({
								functionsID : functionsID,
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
									$.messager.alert('删除提示','删除功能信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除功能信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除
			function batDeleteFunctions()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条功能信息吗？',function(r)
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
								url: "<%=path %>/functions/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									functionsIDs : ids,
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
										$.messager.alert('删除提示','删除功能信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除功能信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var functionsID = 0;
			//保存编辑前的名称
			var orgFunctions = "";
			
			function addFunctions()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#functionsDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加功能信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#Name").val("").focus();
	            $('#functionsFM').form('clear');
	            
	            orgFunctions = "";
	            functionsID = 0;
	            url = '<%=path %>/functions/create.action';
			}
			
			//保存信息
			$("#saveFunctions").unbind().bind({
				click:function()
				{
					if(!$('#functionsFM').form('validate'))
						return;
					else if(checkFunctionsName())
						return;
					else 
					{
						if(!$("#Type").val()){
							$.messager.alert('提示','请选择类型！','warning');
							return;
						}
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								Number : $.trim($("#Number").val()),
								Name : $.trim($("#Name").val()),
								PNumber : $.trim($("#PNumber").val()),
								URL : $.trim($("#URL").val()),
								State : $("#State").is(':checked'),
								Sort : $.trim($("#Sort").val()),
								Enabled : $("#Enabled").is(':checked'),
								Type : $.trim($("#Type").val()),
								PushBtn : $('#PushBtn').combobox('getValues').toString(), //功能按钮
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#functionsDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showFunctionsDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存功能信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存功能信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editFunctions(functionsTotalInfo)
	        {
	        	var functionsInfo = functionsTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#Number").focus().val(functionsInfo[1]);
	            $("#Name").focus().val(functionsInfo[2]);
	            $("#PNumber").focus().val(functionsInfo[3]);
	            $("#URL").focus().val(functionsInfo[4]);
	            $("#State").attr("checked",functionsInfo[5]=='true'?true:false);
	            $("#Sort").focus().val(functionsInfo[6]);
	            $("#Enabled").attr("checked",functionsInfo[7]=='true'?true:false);
	            $("#Type").focus().val(functionsInfo[8]);
				if(functionsInfo[9] !== "undefined" && functionsInfo[9] !== ""){
					var arr = functionsInfo[9].split(",");
					var pushBtnArray = [];
					for(var i=0;i<arr.length;i++){
						if(arr[i]){
							pushBtnArray.push(arr[i]);
						}
					}
					if(pushBtnArray.length){
						$("#PushBtn").combobox('setValues', pushBtnArray);
					}
				}
				else {
					$("#PushBtn").combobox('setValues', '');
				}
	            
	            orgFunctions = functionsInfo[2];
                $('#functionsDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑功能信息');
                $(".window-mask").css({ width: webW ,height: webH});
                functionsID = functionsInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#Name").val("").focus().val(functionsInfo[2]);
                url = '<%=path %>/functions/update.action?functionsID=' + functionsInfo[0];
	        }
	        
	        //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkFunctionsName()
	        {
	        	var Name = $.trim($("#Name").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(name.length > 0 &&( orgFunctions.length ==0 || name != orgFunctions))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/functions/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							functionsID : functionsID,
							Name : Name
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','功能名称已经存在','info');
								//alert("功能名称已经存在");
								//$("#name").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查功能名称是否存在异常，请稍后再试！','error');
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
					showFunctionsDetails(1,initPageSize);	
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
			
			function showFunctionsDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/functions/findBy.action",
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
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>