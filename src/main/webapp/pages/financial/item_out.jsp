<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>支出单</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />    	
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
		<script src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=path %>/js/common/common.js"></script>
		<script src="<%=path %>/js/pages/financial/financial_base.js"></script>
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
					<td>单据编号：</td>
					<td>
						<input type="text" name="searchBillNo" id="searchBillNo" style="width:60px;"/>
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
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="支出单列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="accountHeadDlg" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="accountHeadFM" method="post"  novalidate>
	            <table>
	            <tr>	
	            <td>单据编号：</td>
	            <td style="padding:5px">
	            <input name="BillNo" id="BillNo" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 110px;"/>
	            </td>            
                <td>单据日期：</td>
                <td style="padding:5px">
                <input type="text" name="BillTime" id="BillTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:110px;"/>
                </td>         
	            <td>付款账户：</td>
	            <td style="padding:5px">
	            <select id="AccountId" name="AccountId" style="width:110px;"></select>
	            </td>
	            <td>付款金额：</td>
				<td>
					<input type="text" name="ChangeAmount" id="ChangeAmount" class="easyui-numberbox" data-options="min:0,precision:2" style="width: 60px;"></input>
				</td>
	            </tr>
	            <tr>
	            <td>往来单位：</td>
	            <td style="padding:5px">
                <select name="OrganId" id="OrganId" style="width:110px;"></select>
                </td>
	            <td>经手人：</td>
	            <td style="padding:5px">
	            <select name="HandsPersonId" id="HandsPersonId" style="width:110px;"></select>
	            </td>
	            <td>单据备注：</td>
	            <td style="padding:5px">
	            <input name="Remark" id="Remark" class="easyui-validatebox" style="width: 110px;"/>
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
                <td style="padding:5px;width:110px;">
                <span id="BillNoShow"></span>
                </td>
	            <td>单据日期：</td>
	            <td style="padding:5px;width:110px;">
                <span id="BillTimeShow"></span>
                </td>                
	            <td>付款账户：</td>
	            <td style="padding:5px;width:110px;">
	            <span id="AccountIdShow"></span>
	            </td>
	            <td>付款金额：</td>
	            <td style="padding:5px;width:110px;">
	            <span id="ChangeAmountShow"></span>
	            </td>
	            </tr>
	            <tr>
	            <td>往来单位：</td>
	            <td style="padding:5px">
	            <span id="OrganIdShow"></span>
	            </td>
	            <td>经手人：</td>
				<td style="padding:5px">
				<span id="HandsPersonIdShow"></span>
				</td>
	            <td>单据备注：</td>
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
	</body>
</html>
