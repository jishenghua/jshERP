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
    	<title>资产管理</title>
    	<meta charset="utf-8" />
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
  		<div id="position" class="easyui-panel" title="当前位置：系统管理 &gt; 资产类型" collapsible="false" closable="false"/>
		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>类型名称：</td>
					<td>
						<input type="text" name="searchCategory" id="searchCategory"  style="width:230px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
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
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="类型列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="categoryDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="categoryFM" method="post"  novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="categoryNameLabel">类型名称&nbsp;&nbsp;</label>
	                <input name="categoryName" id="categoryName" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px;">
	                <label id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
	                <textarea name="description" id="description" rows="2" cols="2" style="width: 230px;"></textarea>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveCategory" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelCategory" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#categoryDlg').dialog('close')">取消</a>
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
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#categoryNameLabel").empty().append("类型名称&nbsp;&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
				}
				else
				{
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#categoryNameLabel").empty().append("类型名称&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
				}
			}
			
			//防止表单提交重复
			function initForm()
			{
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
					//title:'供应商列表',
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
					url:'<%=path %>/category/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'id',width:35,align:"center",checkbox:true},
			          { title: '资产类型',field: 'categoryname',width:200},
			          { title: '描述',field: 'description',width:400},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.id + 'AaBb' + rec.categoryname + 'AaBb' + rec.isystem + 'AaBb' + rec.description;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editCategory(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editCategory(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteCategory('+ rec.id +');"/>&nbsp;<a onclick="deleteCategory('+ rec.id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addSupplier',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addCategory();
							}
						},
						{
							id:'deleteSupplier',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteCategory();	
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
			    if(k == "13"&&(obj.id=="categoryName"|| obj.id=="description" ))
			    {  
			        $("#saveCategory").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchCategory" || obj.id=="searchDesc" ))
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
							showCategoryDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除供应商信息
			function deleteCategory(categoryID)
			{
				$.messager.confirm('删除确认','确定要删除此资产类型信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/category/delete.action",
							dataType: "json",
							data: ({
								categoryID : categoryID,
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
									$.messager.alert('删除提示','删除资产类别信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除资产类别信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除供应商
			function batDeleteCategory()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条资产类别信息吗？',function(r)
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
								url: "<%=path %>/category/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									categoryIDs : ids,
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
										$.messager.alert('删除提示','删除资产类别信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除资产类别信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var categoryID = 0;
			//保存编辑前的名称
			var orgCategory = "";
			
			function addCategory()
			{
				$("#clientIp").val('<%=clientIp %>');
	            $("#description").val("");
				$('#categoryDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加资产类型信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#categoryName").val("").focus();
	            //$('#categoryFM').form('clear');
	            
	            orgCategory = "";
	            categoryID = 0;
	            url = '<%=path %>/category/create.action';
			}
			
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
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								categoryName : $.trim($("#categoryName").val()),
								description : $.trim($("#description").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#categoryDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showCategoryDetails(opts.pageNumber,opts.pageSize); 
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
			
			//编辑信息
	        function editCategory(categoryTotalInfo)
	        {
	        	var categoryInfo = categoryTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#categoryName").focus().val(categoryInfo[1]);
	            $("#description").val(categoryInfo[3]);
	            
	            orgCategory = categoryInfo[1];
                $('#categoryDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑资产类型信息');
                $(".window-mask").css({ width: webW ,height: webH});
                categoryID = categoryInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#categoryName").val("").focus().val(categoryInfo[1]);
                url = '<%=path %>/category/update.action?categoryID=' + categoryInfo[0];
	        }
	        
	        //检查名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkCategoryName()
	        {
	        	var categoryName = $.trim($("#categoryName").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(categoryName.length > 0 &&( orgCategory.length ==0 || categoryName != orgCategory))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/category/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							categoryID : categoryID,
							categoryName : categoryName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','资产类型名称已经存在','info');
								//alert("供应商名称已经存在");
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
			
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showCategoryDetails(1,initPageSize);	
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
			
			function showCategoryDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/category/findBy.action",
					dataType: "json",
					data: ({
						categoryName:$.trim($("#searchCategory").val()),
						description:$.trim($("#searchDesc").val()),
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
					$("#searchCategory").val("");
					$("#searchDesc").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>