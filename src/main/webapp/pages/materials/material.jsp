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
    	<title>商品信息</title>
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
			    	<td>类别：</td>
					<td>
						<select name="searchCategoryId_f" id="searchCategoryId_f"  style="width:100px;"></select>
						<select name="searchCategoryId_s" id="searchCategoryId_s"  style="width:100px;"></select>
						<select name="searchCategoryId_t" id="searchCategoryId_t"  style="width:100px;"></select>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="商品列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="materialDlg" class="easyui-dialog" style="width:580px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="materialFM" method="post"  novalidate>
	            <table>
	            <tr>
	            <td>名称</td>
	            <td style="padding:5px" colspan="3"><input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 180px;height: 20px"/></td>
	            </tr>
	            <tr>
	            <td>类别</td>
	            <td style="padding:5px" colspan="3">
                <select name="CategoryId_f" id="CategoryId_f" style="width:145px;height: 20px"></select>
                <select name="CategoryId_s" id="CategoryId_s" style="width:145px;height: 20px"></select>
                <select name="CategoryId_t" id="CategoryId_t" style="width:145px;height: 20px"></select>
                </td>
	            </tr>
	            <tr>
					<td>型号</td>
					<td style="padding:5px"><input name="Model" id="Model" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>规格</td>
					<td style="padding:5px"><input name="Standard" id="Standard" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
				</tr>
	            <tr>
					<td>颜色</td>
					<td style="padding:5px"><input name="Color" id="Color" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>单位</td>
					<td style="padding:5px"><input name="Unit" id="Unit" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
	            </tr>
	            <tr>
					<td>零售价</td>
					<td style="padding:5px"><input name="RetailPrice" id="RetailPrice" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>最低售价</td>
					<td style="padding:5px"><input name="LowPrice" id="LowPrice" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
				</tr>
	            <tr>      
					<td>预设售价一</td>
					<td style="padding:5px"><input name="PresetPriceOne" id="PresetPriceOne" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>预设售价二</td>
					<td style="padding:5px"><input name="PresetPriceTwo" id="PresetPriceTwo" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
				</tr>
	            <tr>
	            <td>备注</td>
	            <td style="padding:5px" colspan="3"><input name="Remark" id="Remark" style="width: 440px;height: 20px"/></td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveMaterial" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelMaterial" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#materialDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			var materialCategoryList = null;
			var materialID = null;
			var parentid_search=null;
			var lei=null;
			var types="";
			var setCategoryId="1";
			var cid=1;
			//初始化界面
			$(function()
			{
				//初始化系统基础信息
				initSystemData(1);
				initSelectInfo(1);
				initSelectInfo(11);
				initTableData();
				ininPager();
				initForm();
			});	
		
			//初始化系统基础信息
			function initSystemData(parentid_search)
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/materialCategory/getBasicData.action",
					data: ({
						ParentId:parentid_search
					}),
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						materialCategoryList = systemInfo.showModel.map.materialCategoryList;
						var msgTip = systemInfo.showModel.msgTip;
						if(msgTip == "exceptoin")
						{
							$.messager.alert('提示','查找商品异常,请与管理员联系！','error');
							return;
						}	
					}
				});				
			}
			//初始化页面选项卡
			function initSelectInfo(lei)
			{
				var options = "";
				
				if(materialCategoryList !=null)
				{
					options = "";
					for(var i = 0 ;i < materialCategoryList.length;i ++)
					{
						var materialCategory = materialCategoryList[i];
						if(0 == i)
						{
							materialID = materialCategory.id;
						}
						options += '<option value="' + materialCategory.id + '">' + materialCategory.name + '</option>';
						if(lei==2||lei==222||lei==3)
						{
							types+=materialCategory.id+',';
						}
					}	
					//$("#CategoryId").empty().append(options);
					if(lei==1)
					{
						$("#searchCategoryId_f").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei==2)
					{
						$("#searchCategoryId_s").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei==3)
					{
						$("#searchCategoryId_t").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei==11)
					{
						$("#CategoryId_f").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei==12)
					{
						$("#CategoryId_s").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei==13)
					{
						$("#CategoryId_t").empty().append('<option value="">全部</option>').append(options);
					}
				}
			}
			
			$("#searchCategoryId_f").change(
				function(){
					var parentid_search=$("#searchCategoryId_f").val();
					if(parentid_search!='')
					{
						initSystemData(parentid_search);
						initSelectInfo(2);
						
						var arr=new Array();
						arr=types.split(',');
						types="";
						for(var i=0;i<arr.length;i++)
						{
						   if(arr[i]!='')
						   {
							   initSystemData(arr[i]);
							   initSelectInfo(222);
						   }
						}
						types=types.substring(0,types.length-1);
						//alert(types);
						setCategoryId=types;
						types="";
					}
				}
			);
			
			$("#searchCategoryId_s").change(
				function(){
					var parentid_search=$("#searchCategoryId_s").val();
					if(parentid_search!='')
					{
						initSystemData(parentid_search);
						initSelectInfo(3);
						
						types=types.substring(0,types.length-1);
						//alert(types);
						setCategoryId=types;
						types="";
					}
				}
			);
			
			$("#searchCategoryId_t").change(
				function(){
					var parentid_search=$("#searchCategoryId_t").val();
					if(parentid_search!='')
					{
						types=parentid_search;
						
						//alert(types);
						setCategoryId=types;
						types="";
					}
				}
			);
			
			$("#CategoryId_f").change(
				function(){					
					var CategoryId_f=$("#CategoryId_f").val();
					if(CategoryId_f!='')
					{
						initSystemData(CategoryId_f);
						initSelectInfo(12);
					}
				}
			);
			
			$("#CategoryId_s").change(
				function(){
					var CategoryId_s=$("#CategoryId_s").val();
					if(CategoryId_s!='')
					{
						initSystemData(CategoryId_s);
						initSelectInfo(13);
					}
				}
			);
			
			//防止表单提交重复
			function initForm()
			{
				$('#materialFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'商品列表',
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
					url:'<%=path %>/material/findBy.action?pageSize=' + initPageSize+'&CategoryIds='+setCategoryId+'&CategoryId='+cid,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '名称',field: 'Name',width:80},
			          { title: '型号',field: 'Model',width:80},
					  { title: '规格',field: 'Standard',width:80},
    		          { title: '颜色',field: 'Color',width:60},
			          { title: '单位',field: 'Unit',width:60},
			          { title: '零售价',field: 'RetailPrice',width:60},
			          { title: '最低售价',field: 'LowPrice',width:70},
			          { title: '预设售价一',field: 'PresetPriceOne',width:70},
			          { title: '预设售价二',field: 'PresetPriceTwo',width:70},
			          { title: '操作',field: 'op',align:"center",width:110,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Name+ 'AaBb' + rec.Model + 'AaBb' + rec.Color + 'AaBb' + rec.Unit + 'AaBb' + rec.RetailPrice + 'AaBb' + rec.LowPrice + 'AaBb' + rec.PresetPriceOne + 'AaBb' + rec.PresetPriceTwo + 'AaBb' + rec.Remark + 'AaBb' + rec.Standard;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editMaterial(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editMaterial(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteMaterial('+ rec.Id +');"/>&nbsp;<a onclick="deleteMaterial('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addMaterial',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addMaterial();
							}
						},
						{
							id:'deleteMaterial',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteMaterial();	
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
			    if(k == "13"&&(obj.id=="CategoryLevel"||obj.id=="Name"))
			    {  
			        $("#saveMaterial").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchCategoryId"))
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
							showMaterialDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除商品信息
			function deleteMaterial(materialID)
			{
				$.messager.confirm('删除确认','确定要删除此商品信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/material/delete.action",
							dataType: "json",
							data: ({
								materialID : materialID,
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
									$.messager.alert('删除提示','删除商品信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除商品信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除商品
			function batDeleteMaterial()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条商品信息吗？',function(r)
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
								url: "<%=path %>/material/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									materialIDs : ids,
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
										$.messager.alert('删除提示','删除商品信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除商品信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var materialID = 0;
			//保存编辑前的名称
			var orgMaterial = "";
			
			function addMaterial()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#materialFM').form('clear');
				$('#materialDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加商品信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#Name").val("").focus();
	            
	            orgMaterial = "";
	            materialID = 0;
	            url = '<%=path %>/material/create.action';
			}
			
			//保存信息
			$("#saveMaterial").unbind().bind({
				click:function()
				{
					if(!$('#materialFM').form('validate'))
						return;
					else 
					{
						var parent=1;
						if($("#CategoryId_f").val()!=""&&$("#CategoryId_f").val()!=null)
					    {
							parent=$("#CategoryId_f").val();
					    }
					    if($("#CategoryId_s").val()!=""&&$("#CategoryId_s").val()!=null)
					    {
							parent=$("#CatetgoryId_s").val();
					    }
					    if($("#CategoryId_t").val()!=""&&$("#CategoryId_t").val()!=null)
					    {
							parent=$("#CategoryId_t").val();
					    }
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								CategoryId : parent,
								Name : $.trim($("#Name").val()),
								Model : $.trim($("#Model").val()),
								Standard : $.trim($("#Standard").val()),
								Color : $.trim($("#Color").val()),
								Unit : $.trim($("#Unit").val()),
								RetailPrice : $.trim($("#RetailPrice").val()),
								LowPrice : $.trim($("#LowPrice").val()),
								PresetPriceOne : $.trim($("#PresetPriceOne").val()),
								PresetPriceTwo : $.trim($("#PresetPriceTwo").val()),
								Remark : $.trim($("#Remark").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									$('#materialDlg').dialog('close');
			                        
									var opts = $("#tableData").datagrid('options'); 
									showMaterialDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存商品信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存商品信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editMaterial(materialTotalInfo)
	        {
	        	var materialInfo = materialTotalInfo.split("AaBb");
	            
	            $("#clientIp").val('<%=clientIp %>');
	            $("#Name").focus().val(materialInfo[1]);
	            $("#Model").val(materialInfo[2]);
				$("#Standard").val(materialInfo[10]=="undefined"?"":materialInfo[10]);
	            $("#Color").val(materialInfo[3]=="undefined"?"":materialInfo[3]);
	            $("#Unit").val(materialInfo[4]=="undefined"?"":materialInfo[4]);
	            $("#RetailPrice").val(materialInfo[5]);
	            $("#LowPrice").val(materialInfo[6]);
	            $("#PresetPriceOne").val(materialInfo[7]);
	            $("#PresetPriceTwo").val(materialInfo[8]);
	            $("#Remark").val(materialInfo[9]);
	            
	            //orgMaterial = materialInfo[1];
                $('#materialDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑商品信息');
                $(".window-mask").css({ width: webW ,height: webH});
                materialID = materialInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#Name").val("").focus().val(materialInfo[1]);
                url = '<%=path %>/material/update.action?materialID=' + materialInfo[0];
	        }
	        
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showMaterialDetails(1,initPageSize);	
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
			
			
			function showMaterialDetails(pageNo,pageSize)
			{
				if(setCategoryId!="1")
				{cid=2;}
				$.ajax({
					type:"post",
					url: "<%=path %>/material/findBy.action",
					dataType: "json",
					data: ({
						CategoryId:cid,
						CategoryIds:setCategoryId,
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
					$("#searchCategoryId_f").val("");
					$("#searchCategoryId_s").val("");
					$("#searchCategoryId_t").val("");
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
			
		</script>
	</body>
</html>
