<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>礼品卡统计</title>
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
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>礼品卡：</td>
					<td>
						<input id="searchGiftId" name="searchGiftId" style="width:110px;" />
					</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="礼品卡统计列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
			    
		<script type="text/javascript">
			//初始化界面
			$(function(){
				var userBusinessList=null;
				var userdepot=null;
				initGift();
				initTableData();
				ininPager();
			});

			//初始化-礼品卡
			function initGift(){
				$('#searchGiftId').combobox({
					url: "<%=path %>/depot/findGiftByType.action?type=1",
					valueField:'id',
					textField:'name'
				});

				//当会员卡号长度超过10位后，自动点击下拉框，用于兼容刷卡器
				$("#searchGiftId").next().find("input").off("keyup").on("keyup",function(){
					var self = this;
					if($(this).val().length === 10){
						setTimeout(function(){
							$(".combo-panel .combobox-item-selected").click();
							search();
						},500);
					}
				});
			}

			//初始化表格数据
			function initTableData(){
				$('#tableData').datagrid({
					height:heightInfo,
					nowrap: false,
					rownumbers: true,
					//动画效果
					animate:false,
					//选中单行
					singleSelect : true,
					pagination: true,
					//交替出现背景
					striped : true,
					pageSize: 10,
					pageList: [10,50,100],
					columns:[[
			          { title: '名称',field: 'MaterialName',width:60},
			          { title: '型号',field: 'MaterialModel',width:80},
					  { title: '规格',field: 'MaterialStandard',width:80},
			          { title: '颜色',field: 'MaterialColor',width:80,hidden:true},
					  { title: '单位',field: 'MaterialUnit',width:80},
			          { title: '结存数量',field: 'thisSum',width:80}
					]],
					onLoadError:function() {
						$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
						return;
					}    
				});
			}
			
			//初始化键盘enter事件
			$(document).keydown(function(event) {
			   	//兼容 IE和firefox 事件 
			    var e = window.event || event;  
			    var k = e.keyCode||e.which||e.charCode;  
			    //兼容 IE,firefox 兼容  
			    var obj = e.srcElement ? e.srcElement : e.target;  
			    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
			    if(k == "13"&&(obj.id=="Type"||obj.id=="Name")) {
			        $("#savePerson").click();
			    }
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchType")) {
			        $("#searchBtn").click();
			    }  
			}); 
			//分页信息处理
			function ininPager() {
				try {
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
							showPersonDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}

			//搜索处理
			function search() {
				showPersonDetails(1,initPageSize);
				var opts = $("#tableData").datagrid('options');
				var pager = $("#tableData").datagrid('getPager');
				opts.pageNumber = 1;
				opts.pageSize = initPageSize;
				pager.pagination('refresh', {
					pageNumber:1,
					pageSize:initPageSize
				});
			}
			$("#searchBtn").unbind().bind({
				click:function() {
					var giftId = $('#searchGiftId').combobox('getValue'); //礼品卡Id
					if(giftId) {
						search();
					}
					else {
						$.messager.alert('查询提示','请选择一张礼品卡！','warning');
					}
				}
			});
			
			function showPersonDetails(pageNo,pageSize) {
				var searchGiftId =  $("#searchGiftId").combobox("getValue");
				$.ajax({
					type:"post",
					url: "<%=path %>/depotHead/findGiftReport.action",
					dataType: "json",
					success: function (res) {
						var HeadIds = res.HeadIds;
						if(HeadIds) {
							//获取排序后的产品ID
							$.ajax({
								type:"post",
								url: "<%=path %>/material/findByOrder.action",
								dataType: "json",
								success: function (resNew) {
									var MIds = resNew.mIds;
									if(MIds) {
										$.ajax({
											type:"get",
											url: "<%=path %>/depotItem/findGiftByAll.action",
											dataType: "json",
											data: ({
												pageNo:pageNo,
												pageSize:pageSize,
												ProjectId: searchGiftId,
												HeadIds: HeadIds,
												MaterialIds: MIds
											}),
											success: function (data) {
												$("#tableData").datagrid('loadData',data);
											},
											//此处添加错误处理
											error:function() {
												$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
												return;
											}
										});
									}
									else {
										$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
									}
								},
								//此处添加错误处理							
					    		error:function() {
					    			$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
									return;
								}
							});
						}
						else {
							$.messager.alert('查询提示','该礼品卡无数据！','warning');
						}
					},
					//此处添加错误处理
		    		error:function() {
		    			$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
						return;
					}
				});
			}			
		</script>
	</body>
</html>