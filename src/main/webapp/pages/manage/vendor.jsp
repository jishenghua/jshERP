<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  	<head>
    	<title>单位信息</title>
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
  		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
					<td>
						<input type="text" name="searchSupplier" id="searchSupplier"  style="width:150px;"/>
					</td>
				
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td id="searchTypeLabel">类&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
					<td>
					<select name="searchType" id="searchType"  style="width:150px;">
					    <option value="">全部</option>
						<option value="供应商">供应商</option>
						<option value="客户">客户</option>
						<option value="散户">散户</option>
				    </select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>联系电话：</td>
					<td>
						<input type="text" name="searchPhonenum" id="searchPhonenum"  style="width:150px;"/>
					</td>
				</tr>
				<tr>
					<td>电子邮箱：</td>
					<td>
						<input type="text" name="searchEmail" id="searchEmail"  style="width:150px;"/> 
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td id="searchDescLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
					<td>
						<input type="text" name="searchDesc" id="searchDesc"  style="width:150px;"/>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="单位列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
	    <div id="supplierDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
	        <form id="supplierFM" method="post" novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="supplierLabel">名&nbsp;&nbsp;称</label>
	                <input name="supplier" id="supplier" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="typeLabel">类&nbsp;&nbsp;&nbsp;&nbsp;型</label>
	             <select name="type" id="type"  style="width:230px;">
						<option value="供应商">供应商</option>
						<option value="客户">客户</option>
					 	<option value="散户">散户</option>
				 </select>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="contactsLabel">联&nbsp;&nbsp;系&nbsp;&nbsp;人&nbsp;&nbsp;</label>
	                <input name="contacts" id="contacts" class="easyui-validatebox" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="phonenumLabel">联系电话&nbsp;&nbsp;</label>
	                <input name="phonenum" id="phonenum" class="easyui-numberbox" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="emailLabel">电子邮箱&nbsp;&nbsp;</label>
	                <input name="email" id="email" class="easyui-validatebox" validType="email" style="width: 230px;height: 20px"/>
	            </div>
	            
	            <div class="fitem" style="padding:5px">
                    <label id="BeginNeedGetLabel">期初应收</label>
                    <input name="BeginNeedGet" id="BeginNeedGet" type="text" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="BeginNeedPayLabel">期初应付</label>
                    <input name="BeginNeedPay" id="BeginNeedPay" type="text" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="AllNeedGetLabel">累计应收</label>
                    <input name="AllNeedGet" id="AllNeedGet" type="text" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px" disabled="true"></input>
                </div>
                <div class="fitem" style="padding:5px">
                    <label id="AllNeedPayLabel">累计应付</label>
                    <input name="AllNeedPay" id="AllNeedPay" type="text" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 230px;height: 20px" disabled="true"></input>
                </div>
	            <div class="fitem" style="padding:5px">
	                <label id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
	                <textarea name="description" id="description" rows="2" cols="2" style="width: 230px;"></textarea>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveSupplier" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelSupplier" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
	    </div>
	    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				browserFit();	
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
				{
					$("#searchContactsLabel").empty().append("联&nbsp;&nbsp;系&nbsp;&nbsp;人：");
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#supplierLabel").empty().append("名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称");
					$("#contactsLabel").empty().append("联&nbsp;&nbsp;系&nbsp;&nbsp;人&nbsp;&nbsp;");
					$("#phonenumLabel").empty().append("联系电话&nbsp;&nbsp;");
					$("#emailLabel").empty().append("电子邮箱&nbsp;&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
				}
				else
				{
					$("#searchContactsLabel").empty().append("联&nbsp;系&nbsp;人：");
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");
					$("#supplierLabel").empty().append("名&nbsp;&nbsp;&nbsp;&nbsp;称");
					$("#contactsLabel").empty().append("联&nbsp;系&nbsp;人");
					$("#phonenumLabel").empty().append("联系电话");
					$("#emailLabel").empty().append("电子邮箱");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述");
				}
			}
			
			//初始化表格数据
			function initTableData()
			{
				//改变宽度和高度
				$("#searchPanel").panel({width:webW-2});
				$("#tablePanel").panel({width:webW-2});
				$('#tableData').datagrid({
					//title:'单位列表',
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
					url:'<%=path %>/supplier/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//自动截取数据
					//nowrap : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
					  { field: 'id',width:35,align:"center",checkbox:true},
			          { title: '名称',field: 'supplier',width:120},
			          { title: '联系人', field: 'contacts',width:50,align:"center"},
			          { title: '联系电话', field: 'phonenum',width:60,align:"center"},
			          { title: '电子邮箱',field: 'email',width:80,align:"center"},
					  { title: '预付款',field: 'AdvanceIn',width:70,align:"center"},
			          { title: '期初应收',field: 'BeginNeedGet',width:70,align:"center"},
			          { title: '期初应付',field: 'BeginNeedPay',width:70,align:"center"},
			          { title: '类型',field: 'type',width:50},
			          { title: '操作',field: 'op',align:"center",width:130,formatter:function(value,rec)
			         	{
							var str = '';
							var rowInfo = rec.id + 'AaBb' + rec.supplier +'AaBb' + rec.contacts + 'AaBb'+ rec.phonenum + 'AaBb'+ rec.email + 'AaBb'+ rec.BeginNeedGet + 'AaBb'+ rec.BeginNeedPay + 'AaBb' + rec.isystem + 'AaBb' + rec.description+ 'AaBb' + rec.type;
	       					if(1 == value)
	       					{
	       						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editSupplier(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editSupplier(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
	       						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteSupplier(\'' + rowInfo + '\');"/>&nbsp;<a onclick="deleteSupplier(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
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
								addSuppler();
							}
						},
						{
							id:'deleteSupplier',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteSupplier();	
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
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
			    if(k == "13"&&(obj.id=="supplier" || obj.id=="contacts"|| obj.id=="phonenum" 
			    	|| obj.id=="email" || obj.id=="description" ))
			    {  
			        $("#saveSupplier").click();
			    }  
			    
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchSupplier" || obj.id=="searchContacts"|| obj.id=="searchPhonenum" 
			    	|| obj.id=="searchEmail" || obj.id=="searchDesc" ))
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
							showSupplierDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除单位信息
			function deleteSupplier(supplierInfo)
			{
				$.messager.confirm('删除确认','确定要删除此单位信息吗？',function(r)
			 	{
                    if (r)
                    {
                    	var supplierTotalInfo = supplierInfo.split("AaBb");
						$.ajax({
							type:"post",
							url: "<%=path %>/supplier/delete.action",
							dataType: "json",
							data: ({
								supplierID : supplierTotalInfo[0],
								supplier:supplierTotalInfo[1],
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								var msg = tipInfo.showModel.msgTip;
								if(msg == '成功')
									//加载完以后重新初始化
									$("#searchBtn").click();
								else
									$.messager.alert('删除提示','删除单位信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除单位信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除单位
			function batDeleteSupplier()
			{
				var row = $('#tableData').datagrid('getChecked');
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条单位信息吗？',function(r)
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
	                        	ids += row[i].id + ",";
	                        }
	                        $.ajax({
								type:"post",
								url: "<%=path %>/supplier/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									supplierIDs : ids,
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
										$.messager.alert('删除提示','删除单位信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除单位信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加单位
			var url;
			var supplierID = 0;
			//保存编辑前的名称
			var orgSupplier = "";
			
			function addSuppler()
			{
				$('#supplierDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加单位信息');
				$(".window-mask").css({ width: webW ,height: webH});
	            $('#supplierFM').form('clear');
	            
	            var row = {
	            	clientIp:'<%=clientIp %>'
	            };
                $('#supplierFM').form('load',row);
	            
	            $("#supplier").focus();
	            orgSupplier = "";
	            supplierID = 0;
	            url = '<%=path %>/supplier/create.action';
			}
			
			//保存单位信息
			$("#saveSupplier").unbind().bind({
				click:function()
				{
					if(checkSupplierName())
						return;

					if(!$("#type").val()){
						$.messager.alert('提示','请选择类型！','warning');
						return;
					}
					var reg = /^([0-9])+$/;
					var phonenum = $.trim($("#phonenum").val());
					if(phonenum.length>0 && !reg.test(phonenum))
					{
						$.messager.alert('提示','电话号码只能是数字','info');
						$("#phonenum").val("").focus();
						return;
					}
					var beginNeedGet = $.trim($("#BeginNeedGet").val());
					var beginNeedPay = $.trim($("#BeginNeedPay").val());
					if(beginNeedGet && beginNeedPay) {
						$.messager.alert('提示','期初应收和期初应付不能同时输入','info');
						return;
					}
					
					$('#supplierFM').form('submit',{
		                url: url,
		                onSubmit: function()
		                {
		                    return $(this).form('validate');
		                },
		                success: function(result)
		                {
		                    var result = eval('('+result+')');
		                    if (!result)
		                    {
		                        $.messager.show({
		                            title: '错误提示',
		                            msg: '保存单位信息失败，请稍后重试!'
		                        });
		                    } 
		                    else 
		                    {
		                        $('#supplierDlg').dialog('close');
								var opts = $("#tableData").datagrid('options'); 
								showSupplierDetails(opts.pageNumber,opts.pageSize);   
		                    }
		                }
		            });	
				}
			});
			
			//编辑单位信息
	        function editSupplier(supplierTotalInfo)
	        {
	        	var supplierInfo = supplierTotalInfo.split("AaBb");
	            var row = {
	            	supplier : supplierInfo[1],
	            	contacts : supplierInfo[2],
	            	phonenum : supplierInfo[3],
	            	email : supplierInfo[4],
	            	BeginNeedGet : supplierInfo[5],
	            	BeginNeedPay : supplierInfo[6],
	            	description : supplierInfo[8],
	            	type : supplierInfo[9],
	            	clientIp:'<%=clientIp %>'
	            };
	            orgSupplier = supplierInfo[1];
                $('#supplierDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑单位信息');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#supplierFM').form('load',row);
                supplierID = supplierInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#supplier").val("").focus().val(supplierInfo[1]);
                url = '<%=path %>/supplier/update.action?supplierID=' + supplierInfo[0];
                
                //显示累计应收和累计应付
                $.ajax({
                	type:"post",
					url: "<%=path %>/depotHead/findTotalPay.action",
					dataType: "json",
					async:  false,
					data: ({
						supplierId: supplierInfo[0]
					}),
					success: function(res){
						if(res) {
							var moneyA = res.getAllMoney.toFixed(2)-0;
							$.ajax({
			                	type:"post",
								url: "<%=path %>/accountHead/findTotalPay.action",
								dataType: "json",
								async:  false,
								data: ({
									supplierId: supplierInfo[0]
								}),
								success: function(res){
									if(res) {
										var moneyB = res.getAllMoney.toFixed(2)-0; 
										var money = moneyA+moneyB;
										var moneyBeginNeedGet = $("#BeginNeedGet").val()-0; //期初应收
										var moneyBeginNeedPay = $("#BeginNeedPay").val()-0; //期初应付
										money = money + moneyBeginNeedPay - moneyBeginNeedGet;
										if(money>0) {											
											$("#AllNeedPay").val(money); //累计应付
										}
										else {
											$("#AllNeedGet").val(-money);  //累计应收
										}
									}
								},
								error: function(){
									$.messager.alert('提示','网络异常请稍后再试！','error');
									return;
								}
							});
						}
					},
					error: function(){
						$.messager.alert('提示','网络异常请稍后再试！','error');
						return;
					}
                })
	        }
	        
	        
	        //检查单位名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkSupplierName()
	        {
	        	var supplierName = $.trim($("#supplier").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(supplierName.length > 0 &&( orgSupplier.length ==0 || supplierName != orgSupplier))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/supplier/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							supplierID : supplierID,
							supplier : supplierName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','单位名称已经存在','info');
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查单位名称是否存在异常，请稍后再试！','error');
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
					showSupplierDetails(1,initPageSize);	
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
			
			function showSupplierDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/supplier/findBy.action",
					dataType: "json",
					data: ({
						supplier:$.trim($("#searchSupplier").val()),
						type:$.trim($("#searchType").val()),
						phonenum:$.trim($("#searchPhonenum").val()),
						email:$.trim($("#searchEmail").val()),
						description:$.trim($("#searchDesc").val()),
						pageNo:pageNo,
						pageSize:pageSize
					}),
					success: function (data)
					{
						$("#tableData").datagrid('loadData',data);
						//$('#tableData').datagrid('reload');
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
					$("#searchSupplier").val("");
					$("#searchType").val("");
					$("#searchPhonenum").val("");
					$("#searchEmail").val("");
					$("#searchDesc").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
		</script>
	</body>
</html>