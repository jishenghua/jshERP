<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>结算账户查询</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
    	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/print/print.js"></script>
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
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
			    	<td>名称：</td>
					<td>
						<input type="text" name="searchName" id="searchName"  style="width:70px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
                    <td>编号：</td>
					<td>
						<input type="text" name="searchSerialNo" id="searchSerialNo"  style="width:70px;"/>
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" id="printBtn">打印</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="结算账户列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>

		<div id="accountDetailListDlg" class="easyui-dialog" style="width:900px;height:500px;padding:10px 20px" closed="true" modal="true" collapsible="false" closable="true">
			<table id="accountTableData" style="top:50px;border-bottom-color:#FFFFFF"></table>
		</div>
			    
		<script type="text/javascript">
			//初始化界面
			$(function()
			{
				initTableData();
				ininPager();
				print();
			});								
			
			//初始化表格数据
			function initTableData(){
                $('#tableData').datagrid({
                    //iconCls:'icon-save',
                    //width:700,
                    height:heightInfo,
                    nowrap: false,
                    rownumbers: true,
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
                    url:'<%=path %>/account/findBy.action?pageSize=' + initPageSize,
                    pagination: true,
                    //自动截取数据
                    //nowrap : true,
                    //loadFilter: pagerFilter,
                    pageSize: initPageSize,
                    pageList: initPageNum,
                    columns:[[
						{ title: '名称',field: 'name',width:100},
						{ title: '编号', field: 'serialNo',width:150,align:"center"},
						{ title: '期初金额', field: 'initialAmount',width:100,align:"center"},
						{ title: '本月发生额', field: 'thisMonthAmount',width:100,align:"center"},
						{ title: '当前余额', field: 'currentAmount',width:100,align:"center"},
						{ title: '操作', field: 'op',width:100,align:"center",formatter:function(value,rec) {
								var str = '';
								var rowInfo = rec.id;
								str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showAccountInOutList(\'' + rowInfo + '\');"/>&nbsp;<a onclick="showAccountInOutList(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">流水</a>&nbsp;&nbsp;';
								return str;
							}
						}
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
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchName"||obj.id=="searchSerialNo"))
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
							showAccountDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showAccountDetails(1,initPageSize);	
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
			
			function showAccountDetails(pageNo,pageSize)
            {
                $.ajax({
                    type:"post",
                    url: "<%=path %>/account/findBy.action",
                    dataType: "json",
                    data: ({
                        name:$.trim($("#searchName").val()),
                        serialNo:$.trim($("#searchSerialNo").val()),
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

			function showAccountInOutList(accountInfo){
				var info = accountInfo.split("AaBb");
				var accountId = info[0];
				$('#accountDetailListDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;查看账户流水');
				$(".window-mask").css({ width: webW ,height: webH});
				initAccountDetailData(accountId);
				getAccountInOutList(accountId,1,initPageSize);
				ininAccountDetailPager(accountId);
			}

			//初始化表格数据
			function initAccountDetailData(accountId){
				$('#accountTableData').datagrid({
					height:heightInfo,
					nowrap: false,
					rownumbers: false,
					//动画效果
					animate:false,
					//选中单行
					singleSelect : true,
					collapsible:false,
					selectOnCheck:false,
					//单击行是否选中
					checkOnSelect : false,
					//交替出现背景
					striped : true,
					pagination: true,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[
						{ title: '单据编号',field: 'number',width:150},
						{ title: '类型', field: 'type',width:100},
						{ title: '单位信息', field: 'supplierName',width:150},
						{ title: '金额', field: 'changeAmount',width:80,
							formatter: function (value, row) {
								if(row.aList && row.amList) {
									var aListArr = row.aList.toString().split(",");
									var amListArr = row.amList.toString().split(",");
									var res = "";
									for(var i=0; i<aListArr.length; i++) {
										if(aListArr[i] == accountId) {
											res = amListArr[i];
										}
									}
									return res + "[多账户]";
								}
								else {
									return row.changeAmount;
								}
							}
						},
						{ title: '入库出库日期',field: 'operTime',width:180}
					]],
					onLoadError:function() {
						$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
						return;
					}
				});
			}

			//分页信息处理
			function ininAccountDetailPager(accountId){
				try {
					var opts = $("#accountTableData").datagrid('options');
					var pager = $("#accountTableData").datagrid('getPager');
					pager.pagination({
						onSelectPage:function(pageNum, pageSize) {
							opts.pageNumber = pageNum;
							opts.pageSize = pageSize;
							pager.pagination('refresh', {
								pageNumber:pageNum,
								pageSize:pageSize
							});
							getAccountInOutList(accountId,pageNum,pageSize);
						}
					});
				}
				catch (e) {
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}

			function getAccountInOutList(accountId,pageNo,pageSize){
				$.ajax({
					type:"get",
					url: "<%=path %>/account/findAccountInOutList.action",
					dataType: "json",
					data: ({
						accountID: accountId,
						pageNo:pageNo,
						pageSize:pageSize
					}),
					success: function (res) {
						$("#accountTableData").datagrid('loadData',res);
					},
					//此处添加错误处理
					error:function() {
						$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
						return;
					}
				});
			}

			//报表打印
			function print() {
				$("#printBtn").off("click").on("click",function(){
					var path = "<%=path %>";
					CreateFormPage('打印报表', $('#tableData'), path);
				});
			}
		</script>
	</body>
</html>