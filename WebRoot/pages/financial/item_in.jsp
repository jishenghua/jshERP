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
    	<title>收入单</title>
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
					<td>单据号：</td>
					<td>
						<input type="text" name="searchBillNo" id="searchBillNo" style="width:60px;"/>
					</td>
					<td>单据时间：</td>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="收入单列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="accountHeadDlg" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="accountHeadFM" method="post"  novalidate>
	            <table>
	            <tr>	
	            <td>单据编号：</td>
	            <td style="padding:5px"><input name="BillNo" id="BillNo" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 120px;"/>
	            </td>            
                <td>单据日期：</td>
                <td style="padding:5px">
                <input type="text" name="BillTime" id="BillTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:120px;"/>
                </td>         
	            <td>账户：</td>
	            <td style="padding:5px">
	            <select id="AccountId" name="AccountId" style="width:120px;"></select>
	            </td>
	            <td>金额：</td>
				<td>
					<input type="text" name="ChangeAmount" id="ChangeAmount" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 60px;"></input>
				</td>
	            </tr>
	            <tr>
	            <td>单位：</td>
	            <td style="padding:5px">
                <select name="OrganId" id="OrganId" style="width:120px;"></select>
                </td>
	            <td>经手人：</td>
	            <td style="padding:5px">
	            <select name="HandsPersonId" id="HandsPersonId" style="width:120px;"></select>
	            </td>
	            <td>备注：</td>
	            <td style="padding:5px">
	            <input name="Remark" id="Remark" class="easyui-validatebox" style="width: 120px;"/>
	            </td>
	            <td></td>
	            <td></td>
	            </tr>
	            <tr>
	            <td>单据明细：</td>
	            <td colspan="7">
			    <!-- 单据列表table -->
				<table id="accountData" style="top:100px;border-bottom-color:#FFFFFF"></table>
	            </td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveAccountHead" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelAccountHead" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#accountHeadDlg').dialog('close')">取消</a>
	    </div>
	    <div id="accountHeadDlgShow" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" modal="true" cache="false" collapsible="false" closable="true">
	            <table>
	            <tr>
	            <td>单据编号：</td>
                <td style="padding:5px;width:120px;">
                <span id="BillNoShow"></span>
                </td>
	            <td>单据日期：</td>
	            <td style="padding:5px;width:120px;">
                <span id="BillTimeShow"></span>
                </td>                
	            <td>账户：</td>
	            <td style="padding:5px;width:120px;">
	            <span id="AccountIdShow"></span>
	            </td>
	            <td>金额：</td>
	            <td style="padding:5px;width:120px;">
	            <span id="ChangeAmountShow"></span>
	            </td>
	            </tr>
	            <tr>
	            <td>单位：</td>
	            <td style="padding:5px">
	            <span id="OrganIdShow"></span>
	            </td>
	            <td>经手人：</td>
				<td style="padding:5px">
				<span id="HandsPersonIdShow"></span>
				</td>
	            <td>备注：</td>
	            <td style="padding:5px">
	            <span id="RemarkShow"></span>
	            </td>
	            <td></td>	            
	            <td></td>
	            </tr>
	            <tr>
	            <td>单据明细：</td>
	            <td colspan="7">
			    <!-- 单据列表table -->
				<table id="accountDataShow" style="top:100px;border-bottom-color:#FFFFFF"></table>
	            </td>
	            </tr>
	            </table>
	    </div>
	    
		<script type="text/javascript">
			var accountList = null;
			var accountID = null;
			var supplierList = null;
			var supplierID = null;
			var personList = null;
			var personID = null;
			var ProjectSearch=null;
			var kid=${sessionScope.user.id};
			var accountHeadMaxId=null; //获取最大的Id
			var accepId=null; //保存的主表id
			//初始化界面
			$(function()
			{
				initSystemData_person(); //经手人数据
				initSelectInfo_person(); //经手人信息
				initSystemData_account(); //账户数据
				initSelectInfo_account(); //账户信息
				initSupplier(); //供应商
				initTableData();
				ininPager();
				initForm();			
				$("#searchBtn").click();	
			});	
			
			//获取账户信息
			function initSystemData_account()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/account/getAccount.action",
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						accountList = systemInfo.showModel.map.accountList;
						var msgTip = systemInfo.showModel.msgTip;
						if(msgTip == "exceptoin")
						{
							$.messager.alert('提示','查找账户信息异常,请与管理员联系！','error');
							return;
						}	
					}
				});				
			}
			//获取账户信息
			function initSelectInfo_account()
			{
				var options = "";				
				if(accountList !=null)
				{
					options = "";
					for(var i = 0 ;i < accountList.length;i ++)
					{
						var account = accountList[i];
						options += '<option value="' + account.id + '">' + account.name + '</option>';
					}	
					$("#AccountId").empty().append(options);
				}
			}
			
			//初始化单位信息
			function initSupplier()
			{
				$('#OrganId').combobox({    
					url: "<%=path%>/supplier/findBySelect_sup.action",
				    valueField:'id',    
				    textField:'supplier'
				});  
			}
			
			//获取财务员
			function initSystemData_person()
			{
				var type = "财务员";
				$.ajax({
					type:"post",
					url: "<%=path%>/person/getPersonByType.action",
					data: {
						Type: type
					},
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
			//获取财务员
			function initSelectInfo_person()
			{
				var options = "";
				
				if(personList !=null)
				{
					for(var i = 0 ;i < personList.length;i ++)
					{
						var person = personList[i];
						if(0 == i)
						{
							personID = person.id;
						}
						if(person.type=="财务员")
						{
							options += '<option value="' + person.id + '">' + person.name + '</option>';
						}		
					}
					$("#HandsPersonId").empty().append(options);
				}
			}
			
			//防止表单提交重复
			function initForm()
			{
				$('#accountHeadFM').form({
				    onSubmit: function(){
				        return false;
				    }
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
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
					//url:'<%=path %>/accountHead/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: 5,
					pageList: initPageNum,
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '单据编号',field: 'BillNo',width:100},
			          { title: '单据时间 ',field: 'BillTime',width:100},
			          { title: '备注',field: 'Remark',width:100},
			          { title: '操作',field: 'op',align:"center",width:180,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.BillNo+ 'AaBb' + rec.BillTime+ 'AaBb' + rec.Remark
							+ 'AaBb' + rec.AccountId+ 'AaBb' + rec.AccountName + 'AaBb' + rec.OrganId + 'AaBb' + rec.OrganName 
							+ 'AaBb' + rec.HandsPersonId + 'AaBb' + rec.HandsPersonName + 'AaBb' + rec.ChangeAmount;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showAccountHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="showAccountHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAccountHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editAccountHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAccountHead('+ rec.Id +');"/>&nbsp;<a onclick="deleteAccountHead('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>';
        					}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addAccountHead',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addAccountHead();
							}
						},
						{
							id:'deleteAccountHead',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteAccountHead();	
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
			
			//初始化表格数据-明细列表-编辑状态
			function initTableData_account()
			{
				$('#accountData').datagrid({
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
					url:'<%=path %>/accountItem/findBy.action?HeaderId=' + accountHeadID,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					onClickRow: onClickRow,
					pageSize: 50,
					pageList: [50,100,150],
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '收入项目',field: 'InOutItemId',width:230,
						  formatter:function(value,row,index){
								  return row.InOutItemName;
	                      },
						  editor:{
                          type:'combobox',
                          options:{
                              valueField:'Id',
                              textField:'InOutItemName',
                              method:'get',
                              url: "<%=path%>/inOutItem/findBySelect.action?type=in"
                          }
			            }
					  },
			          { title: '金额',field: 'EachAmount',editor:'validatebox',width:50},
			          { title: '备注',field: 'Remark',editor:'validatebox',width:150}
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
			
			
			//初始化表格数据-明细列表-查看状态
			function initTableData_account_show()
			{
				$('#accountDataShow').datagrid({
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
					url:'<%=path %>/accountItem/findBy.action?HeaderId=' + accountHeadID,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					onClickRow: onClickRow,
					pageSize: 50,
					pageList: [50,100,150],
					columns:[[
					  { field: 'Id',width:35,align:"center",checkbox:true},
			          { title: '收入项目',field: 'InOutItemName',width:230},
			          { title: '金额',field: 'EachAmount',width:50},
			          { title: '备注',field: 'Remark',width:150}
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
			    if(k == "13"&&(obj.id=="BillNo"||obj.id=="BillTime"))
			    {  
			        $("#saveAccountHead").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchBillNo"))
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
							showAccountHeadDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除财务信息
			function deleteAccountHead(accountHeadID)
			{
				$.messager.confirm('删除确认','确定要删除此财务信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/accountHead/delete.action",
							dataType: "json",
							data: ({
								accountHeadID : accountHeadID,
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
									$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除财务信息
			function batDeleteAccountHead()
			{
				var row = $('#tableData').datagrid('getChecked');	
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条财务信息吗？',function(r)
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
								url: "<%=path %>/accountHead/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									accountHeadIDs : ids,
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
										$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加
			var url;
			var accountHeadID = 0;
			//保存编辑前的名称
			var orgAccountHead = "";
			
			function addAccountHead()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#accountHeadFM').form('clear');
				$('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加财务信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $("#BillNo").val("").focus();
	            
	            orgAccountHead = "";
	            accountHeadID = 0;
	            initTableData_account(); //明细列表
	            reject(); //撤销下、刷新材料列表
	            url = '<%=path %>/accountHead/create.action';
			}
			
			//保存信息
			$("#saveAccountHead").unbind().bind({
				click:function()
				{
					if(!$('#accountHeadFM').form('validate'))
						return;
					else 
					{
						$.ajax({
							type:"post",
							url: url,
							dataType: "json",
							async :  false,
							data: ({
								Type: "收入",
								BillNo : $.trim($("#BillNo").val()),
								BillTime : $.trim($("#BillTime").val()),
								AccountId: $.trim($("#AccountId").val()),
								ChangeAmount: $.trim($("#ChangeAmount").val()),
								OrganId: $('#OrganId').combobox('getValue'),
								HandsPersonId: $.trim($("#HandsPersonId").val()),
								Remark: $.trim($("#Remark").val()),
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								if(tipInfo)
								{
									//保存明细记录
									if(accountHeadID ==0)
									{
										getMaxId(); //查找最大的Id
										accept(accountHeadMaxId); //新增
									}
									else
									{
										accept(accountHeadID); //修改
									}
									
									
									$('#accountHeadDlg').dialog('close');
									var opts = $("#tableData").datagrid('options'); 
									showAccountHeadDetails(opts.pageNumber,opts.pageSize); 
								}
								else
								{
									$.messager.show({
			                            title: '错误提示',
			                            msg: '保存信息失败，请稍后重试!'
			                        });
								}
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('提示','保存信息异常，请稍后再试！','error');
								return;
							}
						});	
					}
				}
			});
			
			//编辑信息
	        function editAccountHead(accountHeadTotalInfo)
	        {
	        	var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
	            $("#clientIp").val('<%=clientIp %>');
	            $("#BillNo").val(accountHeadInfo[1]);
	            $("#BillTime").val(accountHeadInfo[2]);
	            $("#Remark").val(accountHeadInfo[3]);
	            $("#AccountId").val(accountHeadInfo[4]);
	            $('#OrganId').combobox('setValue', accountHeadInfo[6]);
	            $("#HandsPersonId").val(accountHeadInfo[8]);
	            $("#ChangeAmount").val(accountHeadInfo[10]);	            	            
                $('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑财务信息');
                $(".window-mask").css({ width: webW ,height: webH});
                accountHeadID = accountHeadInfo[0];
                
                initTableData_account(); //明细列表
                reject(); //撤销下、刷新材料列表                
                url = '<%=path %>/accountHead/update.action?accountHeadID=' + accountHeadInfo[0];
	        }
	        
	        //查看信息
	        function showAccountHead(accountHeadTotalInfo)
	        {
	        	var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
	            $("#BillNoShow").text(accountHeadInfo[1]);
	            $("#BillTimeShow").text(accountHeadInfo[2]);	     
	            $("#RemarkShow").text(accountHeadInfo[3]);       
	            $("#AccountIdShow").text(accountHeadInfo[5]);
	            $('#OrganIdShow').text(accountHeadInfo[7]);
	            $("#HandsPersonIdShow").text(accountHeadInfo[9]);	            
	            $("#ChangeAmountShow").text(accountHeadInfo[10]);
	            $('#accountHeadDlgShow').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看财务信息');
                $(".window-mask").css({ width: webW ,height: webH});
                
                accountHeadID = accountHeadInfo[0];
                initTableData_account_show(); //明细列表-查看状态
	        }
	        
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showAccountHeadDetails(1,initPageSize);	
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
						
			function showAccountHeadDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/accountHead/findBy.action",
					dataType: "json",
					data: ({
						Type:"收入",
						BillNo:$.trim($("#searchBillNo").val()),
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
					$("#searchBillNo").val("");
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
	            if ($('#accountData').datagrid('validateRow', editIndex)) {
	            	var ed = $('#accountData').datagrid('getEditor', {index:editIndex,field:'InOutItemId'});
	                var InOutItemName = $(ed.target).combobox('getText');
	                $('#accountData').datagrid('getRows')[editIndex]['InOutItemName'] = InOutItemName;
	                $('#accountData').datagrid('endEdit', editIndex);
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
	                    $('#accountData').datagrid('selectRow', index)
	                            .datagrid('beginEdit', index);
	                    editIndex = index;
	                } else {
	                    $('#accountData').datagrid('selectRow', editIndex);
	                }
	            }
	        }
			//新增
			function append()  
			{
	            if (endEditing()) {
	                $('#accountData').datagrid('appendRow', {});
	                editIndex = $('#accountData').datagrid('getRows').length - 1;
	                $('#accountData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	            }
			}
			//删除
	        function removeit() {
	            if (editIndex == undefined) { return }
	            $('#accountData').datagrid('cancelEdit', editIndex)
	                    .datagrid('deleteRow', editIndex);
	            editIndex = undefined;
	        }
	        //撤销
	        function reject() {
	            $('#accountData').datagrid('rejectChanges');
	            editIndex = undefined;
	        }
	        //判断
	        function CheckData() {
	            var row = $('#accountData').datagrid('getRows');
	            var totalRowNum = "";
	            for (var i = 0; i < row.length; i++) {
	                if (row[i].InOutItemId == "") {
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
	            if ($("#accountData").datagrid('getChanges').length) {
	                if (!CheckData())
	                    return false;
	                var inserted = $("#accountData").datagrid('getChanges', "inserted");
	                var deleted = $("#accountData").datagrid('getChanges', "deleted");
	                var updated = $("#accountData").datagrid('getChanges', "updated");
	                $.ajax({
	                    type: "post",
	                    url: "<%=path%>/accountItem/saveDetials.action",
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
	                $('#accountData').datagrid('acceptChanges');
	            }
	        }
	        //获取MaxId
	        function getMaxId()
	        {
	    	    var accountHeadMax=null;
	        	$.ajax({
	        		type:"post",
	        		url: "<%=path%>/accountHead/getMaxId.action",
	        		//设置为同步
	        		async:false,
	        		dataType: "json",
	        		success: function (systemInfo)
	        		{
	        			if(systemInfo)
	        			{
	        				accountHeadMax = systemInfo.showModel.map.accountHeadMax;
	        				var msgTip = systemInfo.showModel.msgTip;
	        				if(msgTip == "exceptoin")
	        				{
	        					$.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
	        					return;
	        				}
	        			}
	        			else
	        			{
	        				accountHeadMax=null;
	        			}
	        		}
	        	});
	        	
	        	if(accountHeadMax !=null)
	        	{
	        		if(accountHeadMax.length>0)
	        		{
	        			accountHeadMaxId=accountHeadMax[0];
	        		}
	        	}
	        }
		</script>
	</body>
</html>
