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
    	<title>销售出库</title>
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
		<script type="text/javascript" src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body>
  		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:3px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
			    	<td>发货仓库：</td>
					<td>
						<select name="searchProjectId" id="searchProjectId"  style="width:80px;"></select>
					</td>
					<td>单据号：</td>
					<td>
						<input type="text" name="searchNumber" id="searchNumber" style="width:60px;"/>
					</td>
					<td>出库时间：</td>
					<td>
						<input type="text" name="searchBeginTime" id="searchBeginTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:80px;"/>
					</td>
					<td>-</td>
					<td>
						<input type="text" name="searchEndTime" id="searchEndTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:80px;"/>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a> 
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id="tablePanel" class="easyui-panel" style="padding:1px; top:300px;" title="销售出库列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="depotHeadDlg" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="depotHeadFM" method="post"  novalidate>
	            <table>
	            <tr>
	            <td>发货仓库：</td>
	            <td style="padding:5px">
                <select name="ProjectId" id="ProjectId" style="width:120px;"></select>
                </td>
                <td>出库时间：</td>
                <td style="padding:5px">
                <input type="text" name="OperTime" id="OperTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:120px;"/>
                </td>
	            <td>客户：</td>
	            <td style="padding:5px">
	            <input id="OrganId" name="OrganId" style="width:120px;" />  
	            </td>
	            <td style="width:50px;"></td>
	            <td style="padding:5px;width:120px;"></td>
	            </tr>
	            <tr>
	            <td>仓管员：</td>
	            <td style="padding:5px">
	            <select name="WareHousePersonId" id="WareHousePersonId" style="width:120px;"></select>
	            </td>
	            <td>备注：</td>
	            <td style="padding:5px">
	            <input name="Remark" id="Remark" class="easyui-validatebox" style="width: 120px;"/>
	            </td>
	            <td>单据号：</td>
	            <td style="padding:5px"><input name="Number" id="Number" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 120px;"/>
	            </td>
	            <td></td>
				<td></td>
	            </tr>
	            <tr>
	            <td>商品列表：</td>
	            <td colspan="7">
			    <!-- 商品列表table -->
				<table id="materialData" style="top:100px;border-bottom-color:#FFFFFF"></table>
	            </td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveDepotHead" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelDepotHead" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#depotHeadDlg').dialog('close')">取消</a>
	    </div>
	    <div id="depotHeadDlgShow" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" modal="true" cache="false" collapsible="false" closable="true">
	            <table>
	            <tr>
	            <td>发货仓库：</td>
	            <td style="padding:5px;width:120px;">
                <span id="ProjectIdShow"></span>
                </td>
                <td>出库时间：</td>
                <td style="padding:5px;width:120px;">
                <span id="OperTimeShow"></span>
                </td>
	            <td>客户：</td>
	            <td style="padding:5px;width:120px;">
	            <span id="OrganIdShow"></span>
	            </td>
	            <td style="width:50px;"></td>
	            <td style="padding:5px;width:120px;">
	            </td>
	            </tr>
	            <tr>
	            <td>仓管员：</td>
	            <td style="padding:5px">
	            <span id="WareHousePersonIdShow"></span>
	            </td>
	            <td>备注：</td>
	            <td style="padding:5px">
	            <span id="RemarkShow"></span>
	            </td>
	            <td>单据号：</td>	            
	            <td style="padding:5px">
	            <span id="NumberShow"></span>
	            </td>
	            <td></td>
				<td style="padding:5px"></td>
	            </tr>
	            <tr>
	            <td>商品列表：</td>
	            <td colspan="7">
			    <!-- 商品列表table -->
				<table id="materialDataShow" style="top:100px;border-bottom-color:#FFFFFF"></table>
	            </td>
	            </tr>
	            </table>
	    </div>
	    
		<script type="text/javascript">
			var depotList = null;
			var depotID = null;
			var supplierList = null;
			var supplierID = null;
			var personList = null;
			var personID = null;
			var ProjectSearch=null;
			var kid=${sessionScope.user.id};
			var userBusinessList=null;
			var userdepot=null;
			var depotHeadMaxId=null; //获取最大的Id
			var accepId=null; //保存的主表id
			//初始化界面
			$(function()
			{
				//初始化系统基础信息
				initSystemData_UB();
				initSelectInfo_UB();
				initSystemData_depot();
				initSelectInfo_depot();
				initSupplier(); //供应商
				initTableData();
				ininPager();
				initForm();
				
				
			});	
			
			//初始化系统基础信息
			function initSystemData_UB()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/userBusiness/getBasicData.action",
					data: ({
						KeyId:kid,
						Type:"UserDepot"
					}),
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						if(systemInfo)
						{
							userBusinessList = systemInfo.showModel.map.userBusinessList;
							var msgTip = systemInfo.showModel.msgTip;
							if(msgTip == "exceptoin")
							{
								$.messager.alert('提示','查找UserBusiness异常,请与管理员联系！','error');
								return;
							}
						}
						else
						{
							userBusinessList=null;
						}
					}
				});		
				
			}
			//初始化页面选项卡
			function initSelectInfo_UB()
			{
				
				if(userBusinessList !=null)
				{
					if(userBusinessList.length>0)
					{
						//用户对应的部门列表 [1][2][3]...
						userdepot =userBusinessList[0].value;
					}
				}
			}
			
			
			//初始化系统基础信息
			function initSystemData_depot()
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
			function initSelectInfo_depot()
			{
				var options = "";
				
				if(depotList !=null)
				{
					options = "";
					for(var i = 0 ;i < depotList.length;i ++)
					{
						var depot = depotList[i];
						
						if(userdepot!=null)
						{
							if(userdepot.indexOf("["+depot.id+"]")!=-1)
							{
								options += '<option value="' + depot.id + '">' + depot.name + '</option>';
							}
						}
					}	
					$("#ProjectId").empty().append(options);
					$("#searchProjectId").empty().append('<option value="">全部</option>').append(options);
				}
			}
			
			//初始化系统基础信息
			function initSupplier()
			{
				$('#OrganId').combobox({    
					url: "<%=path%>/supplier/findBySelect_cus.action",
				    valueField:'id',    
				    textField:'supplier'
				});  
			}
			
			//初始化系统基础信息
			function initSystemData_person(ProjectSearch)
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/person/getBasicData.action?ProjectId="+ProjectSearch,
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						personList = systemInfo.showModel.map.personList;
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
			function initSelectInfo_person()
			{
				var options1 = "";
				var options2 = "";
				
				if(personList !=null)
				{
					for(var i = 0 ;i < personList.length;i ++)
					{
						var person = personList[i];
						if(0 == i)
						{
							personID = person.id;
						}
						if(person.type=="采购人")
						{
							options1 += '<option value="' + person.id + '">' + person.name + '</option>';
						}
						else if(person.type=="仓管员")
						{
							options2 += '<option value="' + person.id + '">' + person.name + '</option>';
						}		
					}
					$("#HandsPersonId").empty().append(options1);
					$("#WareHousePersonId").empty().append(options2);
				}
			}
			
			$("#ProjectId").change(
					function(){
						var ProjectId=$("#ProjectId").val();
						if(ProjectId!='')
						{
							initSystemData_person(ProjectId);
							initSelectInfo_person();
						}
					}
				);
			
			//防止表单提交重复
			function initForm()
			{
				$('#depotHeadFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'销售出库列表',
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
					//checkOnSelect : false,
					//url:'<%=path %>/depotHead/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: 5,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '单据号',field: 'Number',width:100},
			          { title: '出库时间 ',field: 'OperTime',width:100},
			          { title: '创建时间',field: 'CreateTime',width:100},
			          { title: '操作员',field: 'OperPersonName',width:100},
			          { title: '备注',field: 'Remark',width:100},
			          { title: '操作',field: 'op',align:"center",width:180,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.ProjectId+ 'AaBb' + rec.Number+ 'AaBb' + rec.OperPersonName
							+ 'AaBb' + rec.OperTime+ 'AaBb' + rec.OrganId+ 'AaBb' + rec.HandsPersonId
							+ 'AaBb' + rec.WareHousePersonId+ 'AaBb' + rec.SettlementWay+ 'AaBb' + rec.Remark
							+ 'AaBb' + rec.ProjectName+ 'AaBb' + rec.OrganName+ 'AaBb' + rec.HandsPersonName+ 'AaBb' + rec.WareHousePersonName;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="editDepotHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="showDepotHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editDepotHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editDepotHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteDepotHead('+ rec.Id +');"/>&nbsp;<a onclick="deleteDepotHead('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addDepotHead',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addDepotHead();
							}
						},
						{
							id:'deleteDepotHead',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteDepotHead();	
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
			
			//初始化表格数据-商品列表-编辑状态
			function initTableData_material()
			{
				$('#materialData').datagrid({
					height:300,
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
					url:'<%=path %>/depotItem/findBy.action?HeaderId=' + depotHeadID,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					onClickRow: onClickRow,
					pageSize: 5,
					pageList: [5,10,15],
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '名称',field: 'MaterialId',width:230,
						  formatter:function(value,row,index){
								  return row.MaterialName;
	                      },
						  editor:{
                          type:'combobox',
                          options:{
                              valueField:'Id',
                              textField:'MaterialName',
                              method:'get',
                              url: "<%=path%>/material/findBySelect.action"
                          }
			            }
					  },
			          { title: '数量',field: 'OperNumber',editor:'validatebox',width:50},
			          { title: '单价',field: 'UnitPrice',editor:'validatebox',width:50},
			          { title: '备注',field: 'Remark',editor:'validatebox',width:150},
			          { title: '图片',field: 'Img',editor:'validatebox',width:110},
					]],
					toolbar:[
						{
							id:'append',
							text:'新增',
							iconCls:'icon-add',
							handler:function()
							{
								append(); //新增
							}
						},
						{
							id:'delete',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								removeit(); //删除	
							}
						},
						{
							id:'reject',
							text:'撤销',
							iconCls:'icon-undo',
							handler:function()
							{
								reject(); //撤销	
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
			
			
			//初始化表格数据-商品列表-查看状态
			function initTableData_material_show()
			{
				$('#materialDataShow').datagrid({
					height:300,
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
					url:'<%=path %>/depotItem/findBy.action?HeaderId=' + depotHeadID,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					onClickRow: onClickRow,
					pageSize: 5,
					pageList: [5,10,15],
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '名称',field: 'MaterialName',width:230},
			          { title: '数量',field: 'OperNumber',width:50},
			          { title: '单价',field: 'UnitPrice',width:50},
			          { title: '备注',field: 'Remark',width:150},
			          { title: '图片',field: 'Img',width:110},
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
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
			    if(k == "13"&&(obj.id=="State"||obj.id=="Number"))
			    {  
			        $("#saveDepotHead").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchState"||obj.id=="searchNumber"))
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
							showDepotHeadDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除销售出库信息
			function deleteDepotHead(depotHeadID)
			{
				$.messager.confirm('删除确认','确定要删除此销售出库信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/depotHead/delete.action",
							dataType: "json",
							data: ({
								depotHeadID : depotHeadID,
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
									$.messager.alert('删除提示','删除销售出库信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除销售出库信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除销售出库
			function batDeleteDepotHead()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条销售出库信息吗？',function(r)
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
								url: "<%=path %>/depotHead/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									depotHeadIDs : ids,
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
										$.messager.alert('删除提示','删除销售出库信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除销售出库信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var depotHeadID = 0;
			//保存编辑前的名称
			var orgDepotHead = "";
			
			function addDepotHead()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#depotHeadFM').form('clear');
				$('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加销售出库信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#Number").val("").focus();
	            
	            orgDepotHead = "";
	            depotHeadID = 0;
	            initTableData_material(); //商品列表
	            reject(); //撤销下、刷新商品列表
	            url = '<%=path %>/depotHead/create.action';
			}
			
			//保存信息
			$("#saveDepotHead").unbind().bind({
				click:function()
				{
					if(!$('#depotHeadFM').form('validate'))
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								Type:"出库",
								SubType:"销售",
								ProjectId : $.trim($("#ProjectId").val()),
								Number : $.trim($("#Number").val()),
								OperTime: $("#OperTime").val(),
								OrganId: $('#OrganId').combobox('getValue'),
								HandsPersonId: $.trim($("#HandsPersonId").val()),
								WareHousePersonId: $.trim($("#WareHousePersonId").val()),
								SettlementWay: $.trim($("#SettlementWay").val()),
								Remark: $.trim($("#Remark").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									//保存明细记录
									if(depotHeadID ==0)
									{
										getMaxId(); //查找最大的Id
										accept(depotHeadMaxId); //新增
									}
									else
									{
										accept(depotHeadID); //修改
									}
									
									
									$('#depotHeadDlg').dialog('close');
									var opts = $("#tableData").datagrid('options'); 
									showDepotHeadDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存销售出库信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存销售出库信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editDepotHead(depotHeadTotalInfo)
	        {
	        	var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
	            $("#clientIp").val('<%=clientIp %>');
	            $("#ProjectId").focus().val(depotHeadInfo[1]);
	            var ProjectId=depotHeadInfo[1];
				if(ProjectId!='')
				{
					initSystemData_person(ProjectId);
					initSelectInfo_person();
				}
	            $("#Number").val(depotHeadInfo[2]);
	            $("#OperTime").val(depotHeadInfo[4]);
	            $('#OrganId').combobox('setValue', depotHeadInfo[5]);
	            $("#HandsPersonId").val(depotHeadInfo[6]);
	            $("#WareHousePersonId").val(depotHeadInfo[7]);
	            $("#SettlementWay").val(depotHeadInfo[8]);
	            $("#Remark").val(depotHeadInfo[9]);
	            
	            //orgDepotHead = depotHeadInfo[1];
                $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑销售出库信息');
                $(".window-mask").css({ width: webW ,height: webH});
                depotHeadID = depotHeadInfo[0];
                
                initTableData_material(); //商品列表
                reject(); //撤销下、刷新商品列表                
                url = '<%=path %>/depotHead/update.action?depotHeadID=' + depotHeadInfo[0];
	        }
	        
	        //查看信息
	        function showDepotHead(depotHeadTotalInfo)
	        {
	        	var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
	            $("#ProjectIdShow").text(depotHeadInfo[10]);
	            $("#NumberShow").text(depotHeadInfo[2]);
	            $("#OperTimeShow").text(depotHeadInfo[4]);
	            $('#OrganIdShow').text(depotHeadInfo[11]);
	            $("#HandsPersonIdShow").text(depotHeadInfo[12]);
	            $("#WareHousePersonIdShow").text(depotHeadInfo[13]);
	            $("#SettlementWayShow").text(depotHeadInfo[8]);
	            $("#RemarkShow").text(depotHeadInfo[9]);
                $('#depotHeadDlgShow').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看销售出库信息');
                $(".window-mask").css({ width: webW ,height: webH});
                
                depotHeadID = depotHeadInfo[0];
                initTableData_material_show(); //商品列表-查看状态
	        }
	        
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					if($("#searchProjectId").val()=="")
					{
						$.messager.alert('查询提示','请选择一个仓库！','info');		
					}
					else
					{
						showDepotHeadDetails(1,initPageSize);	
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
				}
			});
						
			function showDepotHeadDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/depotHead/findBy.action",
					dataType: "json",
					data: ({
						ProjectId:$.trim($("#searchProjectId").val()),
						Type:"出库",
						SubType:"销售",
						State:$.trim($("#searchState").val()),
						Number:$.trim($("#searchNumber").val()),
						BeginTime:$("#searchBeginTime").val(),
						EndTime:$("#searchEndTime").val(),
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
					//$("#searchProjectId").val("");
					$("#searchState").val("");
					$("#searchBeginTime").val("");
					$("#searchEndTime").val("");
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
			
			//结束编辑
			var editIndex = undefined;
	        function endEditing() {
	            if (editIndex == undefined) { return true }
	            if ($('#materialData').datagrid('validateRow', editIndex)) {
	            	var ed = $('#materialData').datagrid('getEditor', {index:editIndex,field:'MaterialId'});
	                var MaterialName = $(ed.target).combobox('getText');
	                $('#materialData').datagrid('getRows')[editIndex]['MaterialName'] = MaterialName;
	                $('#materialData').datagrid('endEdit', editIndex);
	                editIndex = undefined;
	                return true;
	            } else {
	                return false;
	            }
	        }
	        //单击
	        function onClickRow(index) {
	            if (editIndex != index) {
	                if (endEditing()) {
	                    $('#materialData').datagrid('selectRow', index)
	                            .datagrid('beginEdit', index);
	                    editIndex = index;
	                } else {
	                    $('#materialData').datagrid('selectRow', editIndex);
	                }
	            }
	        }
			//新增
			function append()  
			{
	            if (endEditing()) {
	                $('#materialData').datagrid('appendRow', {});
	                editIndex = $('#materialData').datagrid('getRows').length - 1;
	                $('#materialData').datagrid('selectRow', editIndex)
	                        .datagrid('beginEdit', editIndex);
	            }
			}
			//删除
	        function removeit() {
	            if (editIndex == undefined) { return }
	            $('#materialData').datagrid('cancelEdit', editIndex)
	                    .datagrid('deleteRow', editIndex);
	            editIndex = undefined;
	        }
	        //撤销
	        function reject() {
	            $('#materialData').datagrid('rejectChanges');
	            editIndex = undefined;
	        }
	        //判断
	        function CheckData() {
	            var row = $('#materialData').datagrid('getRows');
	            var totalRowNum = "";
	            for (var i = 0; i < row.length; i++) {
	                if (row[i].MaterialId == "") {
	                    totalRowNum += (i + 1) + "、";
	                }
	            }
	            if (totalRowNum != "") {
	                var totalRowNum = totalRowNum.substring(0, totalRowNum.length - 1);
	                $.messager.alert('提示',"第" + totalRowNum + "行数据填写不完整！",'info');	
	                return false;
	            }
	            return true;
	        }
	        //保存
	        function accept(accepId) {
	            append();
	            removeit();
	            if ($("#materialData").datagrid('getChanges').length) {
	                if (!CheckData())
	                    return false;
	                var inserted = $("#materialData").datagrid('getChanges', "inserted");
	                var deleted = $("#materialData").datagrid('getChanges', "deleted");
	                var updated = $("#materialData").datagrid('getChanges', "updated");
	                $.ajax({
	                    type: "post",
	                    url: "<%=path%>/depotItem/saveDetials.action",
	                    data: {
	                        Inserted: JSON.stringify(inserted),
	                        Deleted: JSON.stringify(deleted),
	                        Updated: JSON.stringify(updated),
	                        HeaderId:accepId,
	                        clientIp:'<%=clientIp %>'
	                    },
	                    success: function (tipInfo) 
	                    { 
	                        if (tipInfo) {
	                            $.messager.alert('提示','保存成功！','info');	
	                        }
	                        else
	                        	$.messager.alert('提示','保存失败！','error');	

	                    },
	                    error: function (XmlHttpRequest, textStatus, errorThrown) 
	                    {
	                        $.messager.alert('提示',XmlHttpRequest.responseText,'error');	
	                    }
	                });
	            }
	            if (endEditing()) {
	                $('#materialData').datagrid('acceptChanges');
	            }
	        }
	        //获取MaxId
	        function getMaxId()
	        {
	    	    var depotHeadMax=null;
	        	$.ajax({
	        		type:"post",
	        		url: "<%=path%>/depotHead/getMaxId.action",
	        		//设置为同步
	        		async:false,
	        		dataType: "json",
	        		success: function (systemInfo)
	        		{
	        			if(systemInfo)
	        			{
	        				depotHeadMax = systemInfo.showModel.map.depotHeadMax;
	        				var msgTip = systemInfo.showModel.msgTip;
	        				if(msgTip == "exceptoin")
	        				{
	        					$.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
	        					return;
	        				}
	        			}
	        			else
	        			{
	        				depotHeadMax=null;
	        			}
	        		}
	        	});
	        	
	        	if(depotHeadMax !=null)
	        	{
	        		if(depotHeadMax.length>0)
	        		{
	        			depotHeadMaxId=depotHeadMax[0];
	        		}
	        	}
	        }
		</script>
	</body>
</html>