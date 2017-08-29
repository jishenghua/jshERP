<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
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
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/material.css" />
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body>
  		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>品名：</td>
					<td>
						<input name="searchName" id="searchName" style="width:80px;"/>
					</td>
					<td>&nbsp;</td>
					<td>型号：</td>
					<td>
						<input name="searchModel" id="searchModel" style="width:80px;"/>
					</td>
					<td>制造商：</td>
					<td>
						<input name="searchMfrs" id="searchMfrs" style="width:80px;"/>
					</td>
			    	<td>类别：</td>
					<td>
						<select name="searchCategoryId_f" id="searchCategoryId_f"  style="width:100px;"></select>
						<select name="searchCategoryId_s" id="searchCategoryId_s"  style="width:100px;"></select>
						<select name="searchCategoryId_t" id="searchCategoryId_t"  style="width:100px;"></select>
					</td>
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
		
	    <div id="materialDlg" class="easyui-dialog" style="width:880px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
	        <form id="materialFM" method="post"  novalidate>
	            <table height="360px">
	            <tr>
					<td style="width:80px; height:30px;">品名</td>
					<td style="padding:5px;width:180px;">
						<input name="Name" id="Name" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 180px;height: 20px"/>
					</td>
					<td style="width:80px;">型号</td>
					<td style="padding:5px;width:180px;">
						<input name="Model" id="Model" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/>
					</td>
					<td style="width:200px;"></td>
	            </tr>
	            <tr>
					<td style="height:30px;">类别</td>
					<td style="padding:5px" colspan="3">
					<select name="CategoryId_f" id="CategoryId_f" style="width:152px;height: 20px"></select>
					<select name="CategoryId_s" id="CategoryId_s" style="width:152px;height: 20px"></select>
					<select name="CategoryId_t" id="CategoryId_t" style="width:152px;height: 20px"></select>
						<label id="mTypeName"></label>
						<a href="#" id="mTypeChange">修改</a>
					</td>
					<td></td>
	            </tr>
	            <tr>
					<td style="height:30px;">制造商</td>
					<td style="padding:5px">
						<input name="Mfrs" id="Mfrs" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'" style="width: 180px;height: 20px"/>
					</td>
					<td>规格</td>
					<td style="padding:5px"><input name="Standard" id="Standard" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td></td>
				</tr>
	            <tr>
					<td style="height:30px;">安全存量</td>
					<td style="padding:5px">
						<input name="SafetyStock" id="SafetyStock" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/>
					</td>
					<td>单位</td>
					<td style="padding:5px">
						<input name="Unit" id="Unit" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px; "/>
						<select name="manyUnit" id="manyUnit" style="width:180px;height: 20px;display: none"></select>
					</td>
					<td>
						<input id="manyUnitCheck" type="checkbox"/>多单位
					</td>
	            </tr>
				<tr class="first-select-unit">
					<td style="height:30px;">首选出库单位</td>
					<td style="padding:5px">
						<select name="FirstOutUnit" id="FirstOutUnit" style="width:180px;height: 20px"></select>
					</td>
					<td>首选入库单位</td>
					<td style="padding:5px">
						<select name="FirstInUnit" id="FirstInUnit" style="width:180px;height: 20px"></select>
					</td>
					<td></td>
				</tr>
				<tr class="price-list">
					<td colspan="5" style="height:30px;">
						<table width="700px">
							<tr>
								<td width="150px" height="25px"><b>价格列表</b></td>
								<td width="150px">计量单位</td>
								<td width="18%">零售价</td>
								<td width="18%">最低售价</td>
								<td width="18%">预计采购价</td>
								<td width="18%">批发价</td>
							</tr>
							<tr>
								<td>基本单位</td>
								<td style="border: 1px solid #aaa;"><span id="basicUnit"></span></td>
								<td><input id="basicRetailPrice" /></td>
								<td><input id="basicLowPrice" /></td>
								<td><input id="basicPresetPriceOne" /></td>
								<td><input id="basicPresetPriceTwo" /></td>
							</tr>
							<tr>
								<td>副单位</td>
								<td style="border: 1px solid #aaa;"><span id="otherUnit"></span></td>
								<td><input id="otherRetailPrice" /></td>
								<td><input id="otherLowPrice" /></td>
								<td><input id="otherPresetPriceOne" /></td>
								<td><input id="otherPresetPriceTwo" /></td>
							</tr>
						</table>
					</td>
				</tr>
	            <tr class="price-one">
					<td style="height:30px;">零售价</td>
					<td style="padding:5px"><input name="RetailPrice" id="RetailPrice" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>最低售价</td>
					<td style="padding:5px"><input name="LowPrice" id="LowPrice" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td></td>
				</tr>
	            <tr class="price-two">
					<td style="height:30px;">预计采购价</td>
					<td style="padding:5px"><input name="PresetPriceOne" id="PresetPriceOne" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td>批发价</td>
					<td style="padding:5px"><input name="PresetPriceTwo" id="PresetPriceTwo" class="easyui-validatebox" data-options="validType:'length[1,30]'" style="width: 180px;height: 20px"/></td>
					<td></td>
				</tr>
	            <tr>
					<td style="height:30px;">备注</td>
	            	<td style="padding:5px" colspan="3"><input name="Remark" id="Remark" style="width: 468px;height: 20px"/></td>
					<td></td>
	            </tr>
	            </table>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveMaterial" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelMaterial" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#materialDlg').dialog('close')">取消</a>
	    </div>

		<!-- 导入excel表格 -->
		<div id="importExcelDlg" class="easyui-dialog" style="width:400px; padding:10px 20px;"
			 closed="true" buttons="#dlg-buttons5" modal="true" collapsible="false" closable="true">
			<form id="importExcelFM" method="post" enctype="multipart/form-data" action="<%=path%>/material/importExcel.action">
				<div class="fitem" style="padding:5px">
					<label>文件名称&nbsp;&nbsp;</label>
					<input name="materialFile" id="materialFile" type="file" style="width: 230px;height: 20px"/>
				</div>
				<div class="fitem" style="padding:5px">
					<label><b>导入注意</b>&nbsp;&nbsp;</label><span>（多单位清空下，价格请用斜线隔开）</span>
				</div>
				<div class="fitem" style="padding:5px;display: none;">
					<label>是否审查&nbsp;&nbsp;</label>
					<select id ="isCheck" name="isCheck" style="width: 230px;height: 20px">
						<option value="0">是</option>
						<option value="1" selected="selected">否</option>
					</select>
				</div>
				<input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
				<div id="dlg-buttons5">
					<a href="javascript:void(0)" id="saveimport" class="easyui-linkbutton" iconCls="icon-ok">导入</a>
					<a href="javascript:void(0)" id="cancelimport" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#importExcelDlg').dialog('close')">取消</a>
				</div>
			</form>
		</div>
		<script type="text/javascript">
			var materialCategoryList = null;
			var materialID = null;
			var parentid_search=null;
			var lei=null;
			var types="";
			var setCategoryId="1";
			var cid=1;
			var multiple = 0;  //倍数
			//初始化界面
			$(function()
			{
				//初始化系统基础信息
				initSystemData(1);
				initSelectInfo("search_first");
				initSelectInfo("edit_first");
				manyUnitFun();
				priceKeyUp();//价格触发事件
				initTableData();
				ininPager();
				initForm();
				bindEvent();
			});

			//初始化系统基础信息
			function initSystemData(parentid_search) {
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
			function initSelectInfo(lei) {
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
						if(lei=="search_second"||lei=="type_second"||lei=="search_three")
						{
							types+=materialCategory.id+',';
						}
					}
					if(lei=="search_first")
					{
						$("#searchCategoryId_f").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei=="search_second")
					{
						$("#searchCategoryId_s").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei=="search_three")
					{
						$("#searchCategoryId_t").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei=="edit_first")
					{
						$("#CategoryId_f").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei=="edit_second")
					{
						$("#CategoryId_s").empty().append('<option value="">全部</option>').append(options);
					}
					else if(lei=="edit_three")
					{
						$("#CategoryId_t").empty().append('<option value="">全部</option>').append(options);
					}
				}
			}

			//一级目录-查询方法
			var searchFirst = function (){
				var parentid_search=$("#searchCategoryId_f").val();
				if(parentid_search!='')
				{
					types += parentid_search + ',';
					initSystemData(parentid_search);
					initSelectInfo("search_second");

					var arr = types.split(',');
					for(var i=0;i<arr.length;i++)
					{
						if(arr[i]!='' && arr[i]!=parentid_search)
						{
							initSystemData(arr[i]);
							initSelectInfo("type_second");
						}
					}
					types=types.substring(0,types.length-1);
					setCategoryId=types;
					types="";
				}
				else{
					setCategoryId ="1";
					$("#searchCategoryId_s").empty().append('<option value="">全部</option>');
					$("#searchCategoryId_t").empty().append('<option value="">全部</option>');
				}
			}
			//一级目录-查询事件
			$("#searchCategoryId_f").change(
					function(){
						searchFirst();
					}
			);
			//二级目录-查询方法
			var searchSecond = function(){
				var parentid_search=$("#searchCategoryId_s").val();
				if(parentid_search!='')
				{
					initSystemData(parentid_search);
					initSelectInfo("search_three");
					types=types.substring(0,types.length-1);
					setCategoryId=types;
					types="";
				}
				else {
					searchFirst();
					$("#searchCategoryId_t").empty().append('<option value="">全部</option>');
				}
			}
			//二级目录-查询事件
			$("#searchCategoryId_s").change(
					function(){
						searchSecond();
					}
			);
			//三级目录-查询事件
			$("#searchCategoryId_t").change(
					function(){
						var parentid_search=$("#searchCategoryId_t").val();
						if(parentid_search!='')
						{
							types=parentid_search;
							setCategoryId=types;
							types="";
						}
						else {
							searchSecond();
						}
					}
			);
			//一级目录-编辑
			$("#CategoryId_f").change(
					function(){
						var CategoryId_f=$("#CategoryId_f").val();
						if(CategoryId_f!='')
						{
							initSystemData(CategoryId_f);
							initSelectInfo("edit_second");
						}
					}
			);
			//二级目录-编辑
			$("#CategoryId_s").change(
					function(){
						var CategoryId_s=$("#CategoryId_s").val();
						if(CategoryId_s!='')
						{
							initSystemData(CategoryId_s);
							initSelectInfo("edit_three");
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

			//多单位勾选
			function manyUnitFun() {
				$("#manyUnitCheck").off("click").on("click", function(){
					if($(this).prop("checked")==true){
						//当前为选中状态
						$("#Unit").hide();
						$("#manyUnit").show();
						$(".first-select-unit").show();
						$(".price-list").show();
						$(".price-one").hide();
						$(".price-two").hide();

						$("#Unit").val(""); //清除单位的填写内容
						$("#RetailPrice").val("");
						$("#LowPrice").val("");
						$("#PresetPriceOne").val("");
						$("#PresetPriceTwo").val("");
					}
					else{
						//当前为不选中状态
						$("#Unit").show();
						$("#manyUnit").hide();
						$(".first-select-unit").hide();
						$(".price-list").hide();
						$(".price-one").show();
						$(".price-two").show();

						$("#manyUnit").val(""); //清除多单位的下拉框
						$("#FirstOutUnit").val("");
						$("#FirstInUnit").val("");
						$("#basicUnit").text("");
						$("#basicRetailPrice").val("");
						$("#basicLowPrice").val("");
						$("#basicPresetPriceOne").val("");
						$("#basicPresetPriceTwo").val("");
						$("#otherUnit").text("");
						$("#otherRetailPrice").val("");
						$("#otherLowPrice").val("");
						$("#otherPresetPriceOne").val("");
						$("#otherPresetPriceTwo").val("");

					}
				});
			}

			//价格触发事件
			function priceKeyUp(){
				$("#basicRetailPrice").off("keyup").on("keyup",function(){
					if(multiple) {
						$("#otherRetailPrice").val($(this).val() * multiple);
					}
				});
				$("#basicLowPrice").off("keyup").on("keyup",function(){
					if(multiple) {
						$("#otherLowPrice").val($(this).val() * multiple);
					}
				});
				$("#basicPresetPriceOne").off("keyup").on("keyup",function(){
					if(multiple) {
						$("#otherPresetPriceOne").val($(this).val() * multiple);
					}
				});
				$("#basicPresetPriceTwo").off("keyup").on("keyup",function(){
					if(multiple) {
						$("#otherPresetPriceTwo").val($(this).val() * multiple);
					}
				});
			}

			//初始化表格数据
			function initTableData() {
				//改变宽度和高度
				$("#searchPanel").panel({width:webW-2});
				$("#tablePanel").panel({width:webW-2});
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
						{ title: '操作',field: 'op',align:"center",width:60,formatter:function(value,rec){
							var str = '';
							var rowInfo = rec.Id + 'AaBb' + rec.Name+ 'AaBb' + rec.Model + 'AaBb' + rec.Color + 'AaBb' + rec.Unit + 'AaBb' + rec.RetailPrice
									+ 'AaBb' + rec.LowPrice + 'AaBb' + rec.PresetPriceOne + 'AaBb' + rec.PresetPriceTwo + 'AaBb' + rec.Remark + 'AaBb' + rec.Standard
									+ 'AaBb' + rec.Mfrs + 'AaBb' + rec.Packing + 'AaBb' + rec.SafetyStock + 'AaBb' + rec.CategoryId + 'AaBb' + rec.CategoryName
									+ 'AaBb' + rec.UnitId + 'AaBb' + rec.UnitName + 'AaBb' + rec.FirstOutUnit + 'AaBb' + rec.FirstInUnit;
							if(1 == value)
							{
								str += '<img title="编辑" src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editMaterial(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
								str += '<img title="删除" src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteMaterial('+ rec.Id +');"/>';
							}
							return str;
						}
						},
						{ title: '品名',field: 'Name',width:80},
						{ title: '型号',field: 'Model',width:80},
						{ title: '制造商',field: 'Mfrs',width:150},
						{ title: '规格',field: 'Standard',width:80},
						{ title: '单位',field: 'Unit',width:60},
						{ title: '安全存量',field: 'SafetyStock',width:90},
						{ title: '零售价',field: 'RetailPrice',width:60},
						{ title: '最低售价',field: 'LowPrice',width:70},
						{ title: '预计采购价',field: 'PresetPriceOne',width:70},
						{ title: '批发价',field: 'PresetPriceTwo',width:70},
						{ title: '状态',field: 'Enabled',width:50,align:"center",formatter:function(value){
							return value? "启用":"禁用";
						}}
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
						'-',
						{
							id:'deleteMaterial',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteMaterial();
							}
						},
						'-',
						{
							id:'setEnable',
							text:'启用',
							iconCls:'icon-ok',
							handler:function() {
								setEnableFun();
							}
						},
						'-',
						{
							id:'setDisEnable',
							text:'禁用',
							iconCls:'icon-no',
							handler:function() {
								setDisEnableFun();
							}
						},
						'-',
						{
							id:'setInput',
							text:'导入',
							iconCls:'icon-excel',
							handler:function() {
								setInputFun();
							}
						},
						'-',
						{
							id:'setOutput',
							text:'导出',
							iconCls:'icon-excel',
							handler:function() {
								setOutputFun();
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
				if(k == "13"&&(obj.id=="searchCategoryId"||(obj.id=="searchName")))
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

			//批量启用
			function setEnableFun() {
				var row = $('#tableData').datagrid('getChecked');
				if(row.length == 0)
				{
					$.messager.alert('启用提示','没有记录被选中！','info');
					return;
				}
				if(row.length > 0)
				{
					$.messager.confirm('启用确认','确定要启用选中的' + row.length + '条信息吗？',function(r)
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
								ids += row[i].Id + ",";
							}
							$.ajax({
								type:"post",
								url: "<%=path%>/material/batchSetEnable.action",
								dataType: "json",
								async :  false,
								data: ({
									enabled: true,
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
										$.messager.alert('启用提示','启用信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
								error:function()
								{
									$.messager.alert('启用提示','启用信息异常，请稍后再试！','error');
									return;
								}
							});
						}
					});
				}
			}

			//批量禁用
			function setDisEnableFun() {
				var row = $('#tableData').datagrid('getChecked');
				if(row.length == 0)
				{
					$.messager.alert('禁用提示','没有记录被选中！','info');
					return;
				}
				if(row.length > 0)
				{
					$.messager.confirm('禁用确认','确定要禁用选中的' + row.length + '条信息吗？',function(r)
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
								ids += row[i].Id + ",";
							}
							$.ajax({
								type:"post",
								url: "<%=path%>/material/batchSetEnable.action",
								dataType: "json",
								async :  false,
								data: ({
									enabled: false,
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
										$.messager.alert('禁用提示','禁用信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
								error:function()
								{
									$.messager.alert('禁用提示','禁用信息异常，请稍后再试！','error');
									return;
								}
							});
						}
					});
				}
			}

			//导入数据
			function setInputFun(){
				//IE下不允许编辑 input=file的值  解决思路：重新克隆input=file，把这个input元素复制一个，然后将原来的删除。
				//在IE下复制元素的时候，其中的值是不会被复制的，所以就达到了清空文件域的目的了。
				//而在Firefox下，其中的值也会被一同复制，清空一下就做到兼容
				var fileUploadInput = $("#materialFile");
				fileUploadInput.after(fileUploadInput.clone().val(""));
				fileUploadInput.remove();
				$("#isCheck").val(1);
				$('#importExcelDlg').dialog('open').dialog('setTitle','导入商品信息');
				$(".window-mask").css({ width: webW-20 ,height: webH});
				$("#materialFile").focus();
			}

			//导出数据
			function setOutputFun(){
				window.location.href = "<%=path%>/material/exportExcel.action?browserType=" + getOs();
			}

			//增加
			var url;
			var materialID = 0;
			//保存编辑前的名称/型号/制造商
			var oldName = "";
			var oldModel = "";
			var oldMfrs = "";
			var oldUnit = "";
			var oldManyUnit = "";

			function addMaterial()
			{
				$("#clientIp").val('<%=clientIp %>');
				$('#materialFM').form('clear');
				$('#materialDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加商品信息');
				$(".window-mask").css({ width: webW ,height: webH});
				$("#Name").val("").focus();
				$("#CategoryId_f").show(); $("#CategoryId_s").show(); $("#CategoryId_t").show(); //全部显示
				$("#mTypeName").hide();
				$("#mTypeChange").hide(); //按钮隐藏
				$("#CategoryId_s").empty(); $("#CategoryId_t").empty(); //清空类型下拉

				$("#manyUnitCheck").prop("checked",false);
				//当前为不选中状态
				$("#Unit").show();
				$("#manyUnit").hide();
				$(".first-select-unit").hide();
				$(".price-list").hide();
				$(".price-one").show();
				$(".price-two").show();
				oldName = "";
				oldModel = "";
				oldMfrs = "";
				oldUnit = "";
				oldManyUnit = "";
				materialID = 0;
				url = '<%=path %>/material/create.action';
			}

			//检查商品名称是否存在 ++ 重名无法提示问题需要跟进
			function checkIsExist() {
				var mName = $.trim($("#Name").val());
				var mModel = $.trim($("#Model").val());
				var mMfrs = $.trim($("#Mfrs").val());
				var mUnit = $.trim($("#Unit").val());
				var mUnitId = $.trim($("#manyUnit").val());
				//表示是否存在 true == 存在 false = 不存在
				var flag = false;
				//开始ajax名称检验，不能重名(新增或编辑)
				if(materialID==0 || (materialID!=0 && (mName != oldName || mModel != oldModel || mMfrs != oldMfrs || mUnit != oldUnit || mUnitId != oldManyUnit))) {
					$.ajax({
						type:"post",
						url: "<%=path%>/material/checkIsExist.action",
						dataType: "json",
						async :  false,
						data: ({
							MaterialID : materialID,
							Name : mName,
							Model: mModel,
							Mfrs: mMfrs,
							Unit: mUnit,
							UnitId: mUnitId
						}),
						success: function (tipInfo) {
							flag = tipInfo;
							if(tipInfo) {
								$.messager.alert('提示','商品信息已经存在','info');
								return;
							}
						},
						//此处添加错误处理
						error:function() {
							$.messager.alert('提示','检查商品信息是否存在异常，请稍后再试！','error');
							return;
						}
					});
				}
				return flag;
			}

			//保存信息
			$("#saveMaterial").unbind().bind({
				click:function()
				{
					if(!$('#materialFM').form('validate')){
						return;
					}
					if(checkIsExist()){
						return;
					}
					var parent=1;
					if($("#CategoryId_f").val()!=""&&$("#CategoryId_f").val()!=null)
					{
						parent=$("#CategoryId_f").val();
					}
					if($("#CategoryId_s").val()!=""&&$("#CategoryId_s").val()!=null)
					{
						parent=$("#CategoryId_s").val();
					}
					if($("#CategoryId_t").val()!=""&&$("#CategoryId_t").val()!=null)
					{
						parent=$("#CategoryId_t").val();
					}
					//构造价格列表json
					var priceStrategy = [];
					var basicList = {};
					var otherList = {};
					basicList.Unit = $("#basicUnit").text();
					basicList.RetailPrice = $("#basicRetailPrice").val();
					basicList.LowPrice = $("#basicLowPrice").val();
					basicList.PresetPriceOne = $("#basicPresetPriceOne").val();
					basicList.PresetPriceTwo = $("#basicPresetPriceTwo").val();
					otherList.Unit = $("#otherUnit").text();
					otherList.RetailPrice = $("#otherRetailPrice").val();
					otherList.LowPrice = $("#otherLowPrice").val();
					otherList.PresetPriceOne = $("#otherPresetPriceOne").val();
					otherList.PresetPriceTwo = $("#otherPresetPriceTwo").val();
					var basic = {};
					var other = {};
					basic.basic = basicList;
					other.other = otherList;
					priceStrategy.push(basic);
					priceStrategy.push(other);

					$.ajax({
						type:"post",
						url: url,
						dataType: "json",
						async :  false,
						data: ({
							CategoryId : parent,
							Name : $.trim($("#Name").val()),
							Mfrs : $.trim($("#Mfrs").val()),
							Packing : $.trim($("#Packing").val()),
							SafetyStock : $.trim($("#SafetyStock").val()),
							Model : $.trim($("#Model").val()),
							Standard : $.trim($("#Standard").val()),
							Color : "",
							Unit : $.trim($("#Unit").val()),
							RetailPrice : $.trim($("#RetailPrice").val()),
							LowPrice : $.trim($("#LowPrice").val()),
							PresetPriceOne : $.trim($("#PresetPriceOne").val()),
							PresetPriceTwo : $.trim($("#PresetPriceTwo").val()),
							UnitId : $("#manyUnit").val(),
							FirstOutUnit : $.trim($("#FirstOutUnit").val()),
							FirstInUnit : $.trim($("#FirstInUnit").val()),
							PriceStrategy : JSON.stringify(priceStrategy), //价格列表
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
			});

			var mId=0,mName="";
			//根据id查询类型信息
			function findByTypeId(Id) {
				$.ajax({
					type:"post",
					url: "<%=path%>/materialCategory/findById.action",
					data: ({
						MaterialCategoryID: Id
					}),
					//设置为同步
					async:false,
					dataType: "json",
					success: function (res)
					{
						if(res){
							mId = res.parentId;
							mName = res.name;
						}
					}
				});
			}

			//编辑信息
			function editMaterial(materialTotalInfo)
			{
				var materialInfo = materialTotalInfo.split("AaBb");

				$("#clientIp").val('<%=clientIp %>');
				$("#Name").focus().val(materialInfo[1]);
				$("#Mfrs").focus().val(materialInfo[11]);

				var categoryName = ""; //类型名称
				mId = materialInfo[14];
				mName = materialInfo[15];
				//拼接类型名称-多级拼接
				if(mId!=1){
					findByTypeId(mId);
					if(mId!=1) {
						categoryName = mName  +">"+ categoryName;
						findByTypeId(mId);
						if(mId!=1) {
							categoryName = mName  +">"+ categoryName;
							findByTypeId(mId);
							if(mId!=1) {
								categoryName = mName  +">"+ categoryName;
								findByTypeId(mId);
							}
							else{
								categoryName = mName  +">"+ categoryName;
							}
						}
						else{
							categoryName = mName  +">"+ categoryName;
						}
					}
					else{
						categoryName = mName +">"+ categoryName;
					}
				}
				else {
					categoryName = mName + categoryName;
				}
				$("#CategoryId_s").empty(); $("#CategoryId_t").empty(); //清空类型下拉
				$("#CategoryId_f").hide(); $("#CategoryId_s").hide(); $("#CategoryId_t").hide(); //全部隐藏
				$("#mTypeName").text(categoryName).show(); //类型显示
				$("#mTypeChange").show(); //按钮显示
				$("#Packing").focus().val(materialInfo[12]);
				$("#SafetyStock").focus().val(materialInfo[13]);
				$("#Model").val(materialInfo[2]);
				$("#Standard").val(materialInfo[10]=="undefined"?"":materialInfo[10]);
				$("#Color").val(materialInfo[3]=="undefined"?"":materialInfo[3]);
				$("#Unit").val(materialInfo[4]=="undefined"?"":materialInfo[4]);
				$("#RetailPrice").val(materialInfo[5]=="undefined"?"":materialInfo[5]);
				$("#LowPrice").val(materialInfo[6]=="undefined"?"":materialInfo[6]);
				$("#PresetPriceOne").val(materialInfo[7]);
				$("#PresetPriceTwo").val(materialInfo[8]);
				$("#Remark").val(materialInfo[9]);
				$("#manyUnit").val(materialInfo[16]);
				if(materialInfo[16]) {
					$("#manyUnitCheck").prop("checked",true);
					//当前为选中状态
					$("#Unit").hide();
					$("#manyUnit").show();
					$(".first-select-unit").show();
					$(".price-list").show();
					$(".price-one").hide();
					$(".price-two").hide();
					var selectItem = $("#manyUnit").children('option:selected').text();
					if(selectItem !== "(空)"){
						var firstOutUnitOptions = selectItem.substring(0, selectItem.indexOf("("));
						var firstOptions = "";
						var arr = firstOutUnitOptions.split(",");
						var basic = '<option value="' + arr[0] + '">' + arr[0] + '</option>';
						var other = '<option value="' + arr[1] + '">' + arr[1] + '</option>';
						firstOptions = firstOptions + basic + other;
						$("#FirstOutUnit").empty().append('<option value="">(空)</option>').append(firstOptions); //首选出库单位
						$("#FirstInUnit").empty().append('<option value="">(空)</option>').append(firstOptions); //首选入库单位
						$("#FirstOutUnit").val(materialInfo[18]);//首选出库单位
						$("#FirstInUnit").val(materialInfo[19]);//首选入库单位
					}
				}
				else {
					$("#manyUnitCheck").prop("checked",false);
					//当前为不选中状态
					$("#Unit").show();
					$("#manyUnit").hide();
					$(".first-select-unit").hide();
					$(".price-list").hide();
					$(".price-one").show();
					$(".price-two").show();
				}
				//单独查询商品的价格列表
				$.ajax({
					url:  '<%=path %>/material/findById.action',
					data:{
						MaterialID: materialInfo[0]
					},
					type: "get",
					dataType: "json",
					success: function(res) {
						if(res && res.rows && res.rows[0]) {
							var PriceStrategy = res.rows[0].PriceStrategy;
							if(PriceStrategy && PriceStrategy[0] && PriceStrategy[1]){
								//基本单位
								$("#basicUnit").text(PriceStrategy[0].basic.Unit);
								$("#basicRetailPrice").val(PriceStrategy[0].basic.RetailPrice);
								$("#basicLowPrice").val(PriceStrategy[0].basic.LowPrice);
								$("#basicPresetPriceOne").val(PriceStrategy[0].basic.PresetPriceOne);
								$("#basicPresetPriceTwo").val(PriceStrategy[0].basic.PresetPriceTwo);
								//副单位
								$("#otherUnit").text(PriceStrategy[1].other.Unit);
								$("#otherRetailPrice").val(PriceStrategy[1].other.RetailPrice);
								$("#otherLowPrice").val(PriceStrategy[1].other.LowPrice);
								$("#otherPresetPriceOne").val(PriceStrategy[1].other.PresetPriceOne);
								$("#otherPresetPriceTwo").val(PriceStrategy[1].other.PresetPriceTwo);
							}
						}
					},
					error: function(){

					}
				});

				oldName = materialInfo[1];
				oldModel = materialInfo[2];
				oldMfrs = materialInfo[11];
				oldUnit = materialInfo[4];
				oldManyUnit = materialInfo[16];
				$('#materialDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑商品信息');
				$(".window-mask").css({ width: webW ,height: webH});
				materialID = materialInfo[0];
				//焦点在名称输入框==定焦在输入文字后面
				$("#Name").val("").focus().val(materialInfo[1]);
				url = '<%=path %>/material/update.action?materialID=' + materialInfo[0];
			}

			$("#mTypeChange").off("click").on("click",function(){
				$(this).hide();
				$("#mTypeName").hide();
				$("#CategoryId_f").show(); $("#CategoryId_s").show(); $("#CategoryId_t").show(); //全部显示
			});

			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					showMaterialDetails(1,initPageSize);
					var opts = $("#tableData").datagrid('options');
					var pager = $("#tableData").datagrid('getPager');
					opts.pageNumber = 1;
					opts.pageSize = initPageSize;
					pager.pagination('refresh', {
						pageNumber:1,
						pageSize:initPageSize
					});
				}
			});


			function showMaterialDetails(pageNo,pageSize)
			{
				if(setCategoryId!="1") {
					cid = 2;
				}
				else {
					cid = 1;
				}
				$.ajax({
					type:"post",
					url: "<%=path %>/material/findBy.action",
					dataType: "json",
					data: ({
						Name: $.trim($("#searchName").val()),
						Model: $.trim($("#searchModel").val()),
						Mfrs: $.trim($("#searchMfrs").val()),
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
					$("#searchName").val("");
					$("#searchCategoryId_f").val("");
					$("#searchCategoryId_s").val("");
					$("#searchCategoryId_t").val("");
					$("#searchCategoryId_s").empty(); $("#searchCategoryId_t").empty(); //清空类型下拉
					setCategoryId = "1";
					//加载完以后重新初始化
					$("#searchBtn").click();
				}
			});

			//绑定事件
			function bindEvent(){
				//下拉绑定事件
				$.ajax({
					url: "<%=path %>/unit/findUnitDownList.action",
					type: "post",
					dateType: "json",
					success: function(res){
						if(res){
							res = JSON.parse(res);
							var options = "";
							if(res.length) {
								for(var i=0; i<res.length; i++){
									options += '<option value="' + res[i].id + '">' + res[i].UName + '</option>';
								}
								$("#manyUnit").empty().append('<option value="">(空)</option>').append(options);
								//下拉框事件
								$("#manyUnit").off("change").on("change",function(){
									var selectItem = $(this).children('option:selected').text();
									if(selectItem !== "(空)"){
										var firstOutUnitOptions = selectItem.substring(0, selectItem.indexOf("("));
										multiple = selectItem.substring(selectItem.indexOf(":")+1).replace(")","");  //倍数
										var firstOptions = "";
										var arr = firstOutUnitOptions.split(",");
										var basic = '<option value="' + arr[0] + '">' + arr[0] + '</option>';
										var other = '<option value="' + arr[1] + '">' + arr[1] + '</option>';
										firstOptions = firstOptions + basic + other;
										$("#FirstOutUnit").empty().append('<option value="">(空)</option>').append(firstOptions); //首选出库单位
										$("#FirstInUnit").empty().append('<option value="">(空)</option>').append(firstOptions); //首选入库单位
										$("#basicUnit").text(arr[0]);
										$("#otherUnit").text(arr[1]);
									}
								});
							}
						}
					},
					error: function(){
						$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
						return;
					}
				});

				//导入excel表格
				$("#saveimport").unbind().bind({
					click:function()
					{
						if($("#materialFile").val().length == 0)
						{
							$.messager.alert('提示','请选择文件！','info');
							return;
						}
						$("#importExcelFM").submit();
						$('#importExcelDlg').dialog('close');

						$.messager.progress({
							title:'请稍候',
							msg:'数据处理ing...'
						});
						setTimeout(function(){
							$.messager.progress('close');
							var opts = $("#tableData").datagrid('options');
							showMaterialDetails(opts.pageNumber,opts.pageSize);
						},3300);
					}
				});
			}

		</script>
	</body>
</html>
