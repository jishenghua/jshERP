<%@page import="com.jsh.util.common.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
  		<div id="position" class="easyui-panel" title="当前位置：系统管理 &gt; 资产名称" collapsible="false" closable="false"/>
		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>类型名称：</td>
					<td>
						<input type="text" name="searchAssetName" id="searchAssetName"  style="width:230px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>资产类型：</td>
					<td>
						<select name="searchCategory" id="searchCategory"  style="width:230px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>是否耗材：</td>
					<td>
						<select name="searchConsume" id="searchConsume"  style="width:230px;">
							<option value="">请选择</option>
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td id="searchDescLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
					<td>
						<input type="text" name="searchDesc" id="searchDesc"  style="width:230px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
					</td>
					<td colspan="7">&nbsp;</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="资产名称列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="assetnameDlg" class="easyui-dialog" style="padding: 10px 20px;" closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="assetnameFM" method="post"  novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="assetNameLabel">资产名称&nbsp;&nbsp;</label>
	                <input name="assetName" id="assetName" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="categoryLabel">资产类型&nbsp;&nbsp;</label>
	                <select name="category" id="category" style="width:230px;height: 20px">
	                </select>
	                &nbsp;&nbsp;<img id="addCategory" src="<%=path %>/js/easyui-1.3.5/themes/icons/edit_add.png" style="cursor: pointer;" alt="增加资产类型" title="增加资产类型" />
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="consumableLabel">是否耗材&nbsp;&nbsp;</label>
	                <select name="consumable" id="consumable" style="width: 230px;height: 20px">
	                	<option value="0">是</option>
	                	<option value="1">否</option>
	                </select>
	            </div>
	            <div class="fitem" style="padding:5px;">
	                <label id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
	                <textarea name="description" id="description" rows="2" cols="2" style="width: 230px;"></textarea>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveAssetName" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelAssetName" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#assetnameDlg').dialog('close')">取消</a>
	    </div>
		<!--增加类型信息 -->
	    <div id="categoryDlg" class="easyui-dialog" style="padding: 10px 20px;" closed="true" buttons="#dlg-buttons1" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="categoryFM" method="post"  novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="categoryNameLabel">类型名称&nbsp;&nbsp;</label>
	                <input name="categoryName" id="categoryName" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px;">
	                <label id="catedescriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
	                <textarea name="descriptioncate" id="descriptioncate" rows="2" cols="2" style="width: 230px;"></textarea>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons1">
	        <a href="javascript:void(0)" id="saveCategory" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelCategory" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#categoryDlg').dialog('close')">取消</a>
	    </div>
		<script type="text/javascript">
			var categoryList = null;
			var categoryID = null;
			//初始化界面
			$(function()
			{
				//初始化系统基础信息
				initSystemData();
				initSelectInfo();
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
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
					$("#assetNameLabel").empty().append("资产名称&nbsp;&nbsp;");
					$("#categoryLabel").empty().append("资产类型&nbsp;&nbsp;");
					$("#consumableLabel").empty().append("是否耗材&nbsp;&nbsp;");
					
					$("#categoryNameLabel").empty().append("类型名称&nbsp;&nbsp;");
					$("#catedescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
					
				}
				else
				{
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
					$("#assetNameLabel").empty().append("资产名称&nbsp;");
					$("#categoryLabel").empty().append("资产类型&nbsp;");
					$("#consumableLabel").empty().append("是否耗材&nbsp;");
					
					$("#categoryNameLabel").empty().append("类型名称&nbsp;");
					$("#catedescriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
				}
			}
			
			//初始化系统基础信息
			function initSystemData()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/asset/getBasicData.action",
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						categoryList = systemInfo.showModel.map.categoryList;
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
				
				if(categoryList !=null)
				{
					options = "";
					for(var i = 0 ;i < categoryList.length;i ++)
					{
						var category = categoryList[i];
						if(0 == i)
						{
							categoryID = category.id;
						}
						options += '<option value="' + category.id + '">' + category.assetname + '</option>';
					}	
					$("#category").empty().append(options);
					$("#searchCategory").empty().append('<option value="">请选择</option>').append(options);
				}
			}
			//防止表单提交重复
			function initForm()
			{
				$('#assetnameFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
				
				$('#categoryFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'资产名称列表',
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
					url:'<%=path %>/assetname/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'id',width:35,align:"center",checkbox:true},
			          { title: '资产名称',field: 'assetname',width:200},
			          { title: '资产类型',field: 'category',width:200},
			          { title: '是否耗材',field: 'consumable',width:100,align:"center"},
			          { title: '描述',field: 'description',width:400},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.id + 'AaBb' + rec.assetname  + 'AaBb' + rec.consumableStatus + 'AaBb' + rec.isystem + 'AaBb' + rec.description + 'AaBb' + rec.categoryID;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAssetName(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editAssetName(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAssetName('+ rec.id +');"/>&nbsp;<a onclick="deleteAssetName('+ rec.id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addAssetName',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addAssetName();
							}
						},
						{
							id:'deleteAssetName',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteAssetName();	
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
			    if(k == "13"&&(obj.id=="assetName"|| obj.id=="description"|| obj.id=="consumable" || obj.id=="category" ))
			    {  
			        $("#saveAssetName").click();
			    }
			    
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
			    if(k == "13"&&(obj.id=="categoryName"|| obj.id=="description" ))
			    {  
			        $("#saveCategory").click();
			    }
			    
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchAssetName" || obj.id=="searchDesc" || obj.id=="searchCategory" ))
			    {  
			        $("#searchBtn").click();
			    }  
			}); 
			
			$("#addCategory").unbind().bind({
				click:function()
				{
					$("#clientIp").val('<%=clientIp %>');
		            $("#categoryName").val("").focus();
		            $("#descriptioncate").val("");
		            $('#categoryDlg').dialog({width : 390}).dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加资产类型信息');
					$("#categoryName").focus();
				}
			});
			
			//保存信息
			$("#saveCategory").unbind().bind({
				click:function()
				{
					if(!$('#categoryFM').form('validate'))
						return;
					else if(checkCategoryName())
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: '<%=path %>/category/create.action',
							dataType: "json",
							async :  false,
							data: ({
								categoryName : $.trim($("#categoryName").val()),
								description : $.trim($("#descriptioncate").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#categoryDlg').dialog('close');
			                        //初始化系统基础信息
									initSystemData();
									initSelectInfo();
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存资产类别信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存资产类型信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			 //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkCategoryName()
	        {
	        	var categoryName = $.trim($("#categoryName").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(categoryName.length > 0)
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/category/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							categoryID : 0,
							categoryName : categoryName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','资产类型名称已经存在','info');
								//alert("资产名称名称已经存在");
								//$("#supplier").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查资产类型名称是否存在异常，请稍后再试！','error');
							return;
						}
					});	
        		}
        		return flag;
	        }
			
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
							showAssetNameDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除资产名称信息
			function deleteAssetName(assetNameID)
			{
				$.messager.confirm('删除确认','确定要删除此资产名称信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/assetname/delete.action",
							dataType: "json",
							data: ({
								assetNameID : assetNameID,
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
									$.messager.alert('删除提示','删除资产名称信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除资产名称信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除资产名称
			function batDeleteAssetName()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条资产名称信息吗？',function(r)
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
	                        	//alert(row[i].id);
	                        	ids += row[i].id + ",";
	                        }
	                        $.ajax({
								type:"post",
								url: "<%=path %>/assetname/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									assetNameIDs : ids,
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
										$.messager.alert('删除提示','删除资产名称信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除资产名称信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var assetNameID = 0;
			//保存编辑前的名称
			var orgAssetName = "";
			
			function addAssetName()
			{
				$('#assetnameDlg').dialog({width : 390}).dialog('open')
				.dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加资产名称信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $('#assetnameFM').form('clear');
	            var row = {
	            	consumable:0,
	            	clientIp:'<%=clientIp %>',
	            	category:categoryID
	            };
                $('#assetnameFM').form('load',row);
	            $("#assetName").focus();
	            orgAssetName = "";
	            assetNameID = 0;
	            url = '<%=path %>/assetname/create.action';
			}
			
			//保存信息
			$("#saveAssetName").unbind().bind({
				click:function()
				{
					if(!$('#assetnameFM').form('validate'))
						return;
					else if(checkAssetName())
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								assetName : $.trim($("#assetName").val()),
								consumable : $("#consumable").val(),
								description : $.trim($("#description").val()),
								clientIp:'<%=clientIp %>',
								categoryID: $.trim($("#category").val()),
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#assetnameDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showAssetNameDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存资产名称信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存资产名称信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editAssetName(assetNameTotalInfo)
	        {
	        	var assetnameInfo = assetNameTotalInfo.split("AaBb");
	            var row = {
	            	assetName : assetnameInfo[1],
	            	consumable : assetnameInfo[2],
	            	description : assetnameInfo[4],
	            	clientIp:'<%=clientIp %>',
	            	category:assetnameInfo[5]
	            };
	            
	            orgAssetName = assetnameInfo[1];
	            
	            $('#assetnameDlg').dialog({width : 390}).dialog('open')
				.dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑资产名称信息');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#assetnameFM').form('load',row);
                assetNameID = assetnameInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#assetName").val("").focus().val(assetnameInfo[1]);
                url = '<%=path %>/assetname/update.action?assetNameID=' + assetnameInfo[0];
	        }
	        
	        //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkAssetName()
	        {
	        	var assetName = $.trim($("#assetName").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(assetName.length > 0 &&( orgAssetName.length ==0 || assetName != orgAssetName))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/assetname/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							assetNameID : assetNameID,
							assetName : assetName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','资产名称已经存在','info');
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查资产名称是否存在异常，请稍后再试！','error');
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
					showAssetNameDetails(1,initPageSize);	
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
			
			function showAssetNameDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/assetname/findBy.action",
					dataType: "json",
					data: ({
						assetName:$.trim($("#searchAssetName").val()),
						description:$.trim($("#searchDesc").val()),
						categoryID : $("#searchCategory").val(),
						consumable : $("#searchConsume").val(),
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
					$("#searchAssetName").val("");
					$("#searchDesc").val("");
					$("#searchCategory").val("");
					$("#searchConsume").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>