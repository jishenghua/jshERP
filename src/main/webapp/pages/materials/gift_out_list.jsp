<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>礼品销售</title>
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
					<td>单据编号：</td>
					<td>
						<input type="text" name="searchNumber" id="searchNumber" style="width:100px;"/>
					</td>
					<td>单据日期：</td>
					<td>
						<input type="text" name="searchBeginTime" id="searchBeginTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:100px;"/>
					</td>
					<td>-</td>
					<td>
						<input type="text" name="searchEndTime" id="searchEndTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width:100px;"/>
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
		<div id="tablePanel" class="easyui-panel" style="padding:1px; top:300px;" title="礼品销售列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="depotHeadDlg" class="easyui-dialog" style="width:1200px;padding:10px 20px;top:50px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="depotHeadFM" method="post"  novalidate>
	            <table>
					<tr>
						<td style="width:70px;">单据日期：</td>
						<td style="padding:5px">
						<input type="text" name="OperTime" id="OperTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate" style="width:140px;"/>
						</td>
						<td style="width:70px;">单据编号：</td>
						<td style="padding:5px">
							<input name="Number" id="Number" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 140px;"/>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td style="width:100px;"></td>
					</tr>
					<tr>
						<td colspan="9">
							<!-- 商品列表table -->
							<table id="materialData" style="top:100px;border-bottom-color:#FFFFFF"></table>
						</td>
					</tr>
					<tr>
						<td colspan="9">
							<textarea name="Remark" id="Remark" rows="2" cols="2" placeholder="暂无备注信息" style="width: 1130px; height:35px;"></textarea>
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
	    <div id="depotHeadDlgShow" class="easyui-dialog" style="width:1200px;padding:10px 20px;top:50px"
	            closed="true" modal="true" cache="false" collapsible="false" closable="true">
			<table>
				<tr>
					<td style="width:70px;">单据日期：</td>
					<td style="padding:5px;width:140px;">
						<span id="OperTimeShow"></span>
					</td>
					<td style="width:70px;">单据编号：</td>
					<td style="padding:5px;width:140px;">
						<span id="NumberShow"></span>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td style="width:100px;"></td>
				</tr>
				<tr>
					<td colspan="9" style="width: 1130px;">
						<!-- 商品列表table -->
						<table id="materialDataShow" style="top:100px;border-bottom-color:#FFFFFF"></table>
					</td>
				</tr>
				<tr>
					<td style="width:60px;">单据备注：</td>
					<td colspan="8" style="height:35px;">
						<span id="RemarkShow" style="width: 1070px; height:35px;"></span>
					</td>
				</tr>
			</table>
	    </div>	    
	</body>
</html>