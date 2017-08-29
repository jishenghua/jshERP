<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>礼品充值</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/in_out.css" />
		<script src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path %>/js/common/common.js"></script>		
		<script src="<%=path %>/js/pages/materials/in_out.js"></script>
		<script>
			var kid = ${sessionScope.user.id};
			var path = "<%=path%>";
			var clientIp = "<%=clientIp%>";
		</script>
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
					<td>单据编号：</td>
					<td>
						<input type="text" name="searchNumber" id="searchNumber" style="width:60px;"/>
					</td>
					<td>单据日期：</td>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="礼品充值列表" iconCls="icon-list" collapsible="true" closable="false">
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
                <td>单据日期：</td>
                <td style="padding:5px">
                <input type="text" name="OperTime" id="OperTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:120px;"/>
                </td>
                <td>经手人：</td>
	            <td style="padding:5px">
	            <select name="HandsPersonId" id="HandsPersonId" style="width:120px;"></select>
	            </td>	            
	            <td style="width:50px;"></td>
	            <td style="padding:5px;width:120px;"></td>
	            </tr>
	            <tr>
	            <td>礼品卡：</td>
	            <td style="padding:5px">
					<input id="GiftId" name="GiftId" style="width:120px;" />
				</td>
	            <td>单据编号：</td>
	            <td style="padding:5px"><input name="Number" id="Number" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 120px;"/>
	            </td>
	            <td>单据备注：</td>
	            <td style="padding:5px">
	            <input name="Remark" id="Remark" class="easyui-validatebox" style="width: 120px;"/>
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
                <td>单据日期：</td>
                <td style="padding:5px;width:120px;">
                <span id="OperTimeShow"></span>
                </td>
                <td>经手人：</td>
	            <td style="padding:5px">
	            <span id="HandsPersonIdShow"></span>
	            </td>	            
	            <td style="width:50px;"></td>
	            <td style="padding:5px;width:120px;">
	            </td>
	            </tr>
	            <tr>
	            <td>礼品卡：</td>
	            <td style="padding:5px;width:120px;">
	            <span id="AllocationProjectIdShow"></span>
	            </td>
	            <td>单据编号：</td>
	            <td style="padding:5px">
	            <span id="NumberShow"></span>
	            </td>
	            <td>单据备注：</td>
	            <td style="padding:5px">
	            <span id="RemarkShow"></span>
	            </td>	            
	            <td></td>
				<td></td>
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
	</body>
</html>