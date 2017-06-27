<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>零售出库</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/retail_list.css" />
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
		<div id="tablePanel" class="easyui-panel" style="padding:1px; top:300px;" title="零售出库列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="depotHeadDlg" class="easyui-dialog" style="width:850px;padding:10px 20px;top:20px"
	            closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
	        <form id="depotHeadFM" method="post"  novalidate>
	            <table>
	            <tr>
					<td>会员卡号：</td>
					<td style="padding:5px">
	            		<input id="OrganId" name="OrganId" style="width:110px;" />
	            	</td>
					<td>发货仓库：</td>
					<td style="padding:5px">
						<select name="ProjectId" id="ProjectId" style="width:110px;"></select>
					</td>
					<td>单据编号：</td>
					<td style="padding:5px">
						<input name="Number" id="Number" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 130px;"/>
					</td>
					<td>单据日期：</td>
					<td style="padding:5px">
						<input type="text" name="OperTime" id="OperTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt Wdate" style="width: 125px;"/>
					</td>
	            </tr>
	            <tr>
					<td>单据备注：</td>
					<td style="padding:5px" colspan="3">
						<input name="Remark" id="Remark" class="easyui-validatebox" style="width: 292px;"/>
					</td>
					<td>付款类型：</td>
					<td style="padding:5px">
						<select name="payType" id="payType" style="width:130px;">
							<option value="现付">现付</option>
							<option value="预付款">预付款</option>
						</select>
					</td>
	            </tr>
	            <tr>
					<td colspan="6">
						<!-- 商品列表table -->
						<table id="materialData" style="top:100px;border-bottom-color:#FFFFFF"></table>
					</td>
					<td colspan="2" valign="top">
						<table width="100%" class="retail-amount">
							<tr>
								<td colspan="2">实收金额</td>
							</tr>
							<tr>
								<td colspan="2">
									<input id="ChangeAmount" name="ChangeAmount" class="change-amount" readonly="readonly" data-changeamount="0" />
								</td>
							</tr>
							<tr>
								<td colspan="2">收款金额</td>
							</tr>
							<tr>
								<td colspan="2">
									<input id="getAmount" name="getAmount" onKeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))" class="get-amount" data-changeamount="0" />
								</td>
							</tr>
							<tr>
								<td colspan="2">找零</td>
							</tr>
							<tr>
								<td colspan="2">
									<input id="backAmount" name="backAmount" class="back-amount" readonly="readonly" data-changeamount="0" />
								</td>
							</tr>
							<tr>
								<td>收款账户：</td>
								<td>
									<select name="AccountId" id="AccountId" style="width: 117px;"></select>
								</td>
							</tr>
							<tr>
								<td>经手人：</td>
								<td>
									<select name="HandsPersonId" id="HandsPersonId" style="width: 117px;"></select>
								</td>
							</tr>
						</table>
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
					<td>会员卡号：</td>
					<td style="padding:5px;width:110px;">
						<span id="OrganIdShow"></span>
					</td>
					<td>发货仓库：</td>
					<td style="padding:5px;width:110px;">
						<span id="ProjectIdShow"></span>
					</td>
					<td>单据编号：</td>
					<td style="padding:5px;width:130px;">
						<span id="NumberShow"></span>
					</td>
					<td>单据日期：</td>
					<td style="padding:5px;width:125px;">
						<span id="OperTimeShow"></span>
					</td>
				</tr>
				<tr>
					<td>单据备注：</td>
					<td style="padding:5px;width:292px;" colspan="5">
						<span id="RemarkShow"></span>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<!-- 商品列表table -->
						<table id="materialDataShow" style="top:100px;border-bottom-color:#FFFFFF"></table>
					</td>
					<td colspan="2" valign="top">
						<table width="100%" class="retail-amount-show">
							<tr>
								<td colspan="2">实收金额</td>
							</tr>
							<tr>
								<td colspan="2">
									<span id="ChangeAmountShow" class="change-amount-show"></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">收款金额</td>
							</tr>
							<tr>
								<td colspan="2">
									<span id="getAmountShow" class="get-amount-show"></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">找零</td>
							</tr>
							<tr>
								<td colspan="2">
									<span id="backAmountShow" class="back-amount-show"></span>
								</td>
							</tr>
							<tr>
								<td>收款账户：</td>
								<td align="left" style="width:110px;">
									<span id="AccountIdShow"></span>
								</td>
							</tr>
							<tr>
								<td>经手人：</td>
								<td align="left" style="width:110px;">
									<span id="HandsPersonIdShow"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
	            </table>
	    </div>
	</body>
</html>