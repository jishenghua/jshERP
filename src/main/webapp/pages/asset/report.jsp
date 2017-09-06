<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  	<head>
    	<title>资产管理</title>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path%>/js/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="<%=path%>/js/highcharts/exporting.js"></script>
		<script type="text/javascript" src="<%=path%>/js/StringBuffer.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
	  	<style>
			body{ margin:0; height:100%}
			html{ height:100%} /*兼容firefox的div高度100%*/
			#left{ width:150px; height:100%; float:left; _margin-right:-3px;}
			#right{ height:100%;}
			<!--页面展示特殊要求-->
			.datagrid-body,.datagrid-footer,.datagrid-pager ,.datagrid-view
			{
				background-color:#EAF2FD;
			}
		</style>
  	</head>
  	<body>
  		<div id="position" class="easyui-panel" title="当前位置：资产管理 &gt; 资产报表" collapsible="false" closable="false"/>
		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>资产名称：</td>
					<td>
						<select name="searchAssetNameID" id="searchAssetNameID"  style="width:200px;"></select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>资产类型：</td>
					<td>
						<select name="searchCategoryID" id="searchCategoryID"  style="width:200px;"></select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>用户姓名：</td>
					<td>
						<select name="searchUsernameID" id="searchUsernameID"  style="width:200px;"></select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td id="searchStatusLabel">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
					<td>
						<select name="searchStatus" id="searchStatus"  style="width:200px;">
							<option value="">请选择</option>
							<option value="0">在库</option>	
							<option value="1">在用</option>	
							<option value="2">消费</option>	
						</select>
					</td>
				</tr>
				<tr>
					<td id="searchSupplierIDLabel">供&nbsp;&nbsp;&nbsp;&nbsp;应&nbsp;&nbsp;&nbsp;&nbsp;商：</td>
					<td>
						<select name="searchSupplierID" id="searchSupplierID"  style="width:200px;"></select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>统计类型：</td>
					<td>
						<select name="searchReportType" id="searchReportType"  style="width:200px;">
							<option value="0">资产状态</option>	
							<option value="1">资产类型</option>	
							<option value="2">供应商</option>	
							<option value="3">资产名称</option>	
							<option value="4">所属用户</option>	
						</select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>是否前十：</td>
					<td>
						<select name="searchTopten" id="searchTopten"  style="width:200px;">
							<option value="0">是</option>	
							<option value="1">否</option>	
						</select>
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
<%--		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="资产列表" iconCls="icon-chart-column" collapsible="true" closable="false">--%>
<%--			<table id="tableData" style="height:360px;top:300px;border-bottom-color:#FFFFFF"></table>--%>
<%--		</div>--%>

		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="资产报表" iconCls="icon-chart-column" collapsible="true" maximizable="false" closable="false">
			<div id="left" class="easyui-tabs" style="padding:1px;">
				<div title="综合图" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;" data-options="iconCls:'icon-chart-zonghe'">
		            <div id="zongheContainer">综合图</div>
		        </div>
		        <div title="柱状图" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;" data-options="iconCls:'icon-chart-statistics'">
		        	<div id="culumnContainer" >柱状图</div>
		        </div>
		        <div title="饼状图" data-options="iconCls:'icon-chart-pie'" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;">
		            <div id="pieContainer">饼状图</div>
		        </div>
		        <div title="折线图" data-options="iconCls:'icon-chart-polygram'" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;">
		            <div id="zxianContainer">折现图</div>
		        </div>
			</div>
			<div id="right" class="easyui-tabs" style="padding:1px;">
				<div title="表格" data-options="iconCls:'icon-list'" style="padding:10px;background-color: #EAF2FD;top:300px;">
		            <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF;background-color: #EAF2FD;"></table>
		        </div>	
			</div>
		</div>
	    
		<script type="text/javascript">
			
			var userList = null;
			var categoryList = null;
			var supplierList = null;
			var nameList = null;
			//界面selet选显卡有问题解决方案
			var nameID = null;
			var userID = null;
			var categoryID = null;
			var supplierID = null;
			
			//报表数据
			var pageData = null;
			
			//报表显示个数
			var showNum = 10;
			//初始化界面
			$(function()
			{
				//progress();
				//页面自定义高度
			    heightInfo = heightInfo - 50;
				//是否限制条件	默认限制
			    initShowDataNum();
				//initTableData();
				//ininPager();
				//初始化系统基础信息
				initSystemData();
				//初始化页面系统基础信息选项卡
				initSelectInfo();
				//获取报表数据			    
			    getDataInfo();
			    //初始化报表高度
			    initChartsHight();
			    //初始化表格数据
			    initTableData();	
			    //综合图
			    showComboChart();
			    //饼状图
			    showPieChart();
			    //折线图
			    showSpline();
			    //显示柱状图
			    showHistogram();
			    browserFit();	
			});	
			
			//浏览器适配
			function browserFit()
			{
				if(getOs()=='MSIE')
				{
					$("#searchStatusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：");
					$("#searchSupplierIDLabel").empty().append("供&nbsp;&nbsp;&nbsp;&nbsp;应&nbsp;&nbsp;&nbsp;&nbsp;商：");
				}
				else
				{
					$("#searchStatusLabel").empty().append("状&nbsp;&nbsp;&nbsp;&nbsp;态：");
					$("#searchSupplierIDLabel").empty().append("供&nbsp;&nbsp;应&nbsp;&nbsp;商：");
				}
			}
			
			$("#left").tabs({width:630});
			
			var chartHight;
			function initChartsHight()
			{
				
				$("#zxianContainer").add("#pieContainer").add("#culumnContainer")
				.add("#zongheContainer").css({height: heightInfo});
				
				chartHight = $("#zxianContainer").outerHeight();
			}
			
			//是否限制条件	
			function initShowDataNum()
			{
				var type = $("#searchTopten").val();
				if(0 == type)
					showNum = 10;
				else
					//表示不限制
					showNum = 10000000000;
			}
			
			//加载进度条
			function progress()
			{
	            $.messager.progress({
	                title:'请稍候',
	                msg:'数据加载ing...'
	            });
	            setTimeout(function(){
	                $.messager.progress('close');
	            },3300)
	        }
			//获取查询数据
			function getDataInfo()
			{
				$.ajax({
						type:"post",
						url: "<%=path%>/report/find.action",
						dataType: "json",
						//取消异步机制，保证页面数据返回再进行处理
						async: false,
						data: ({
							assetNameID:$.trim($("#searchAssetNameID").val()),
							assetCategoryID:$.trim($("#searchCategoryID").val()),
							usernameID:$.trim($("#searchUsernameID").val()),
							status:$.trim($("#searchStatus").val()),
							supplierID:$.trim($("#searchSupplierID").val()),
							reportType : $.trim($("#searchReportType").val()),
						}),
						success: function (reportInfo)
						{
							pageData = reportInfo.showModel.reportData;
							var msgTip = reportInfo.showModel.msgTip;
							if(msgTip == "get report data exception")
							{
								alert("查找报表信息异常,请与管理员联系！");
								return;
							}	
						}
				});
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'资产列表',
					//iconCls:'icon-save',
					//width:700,
					height : chartHight,
					nowrap: false,
					rownumbers: false,
					//动画效果
					animate:false,
					//选中单行
					singleSelect : true,
					collapsible:false,
					selectOnCheck:false,
					//列宽自适应
					//fitColumns:true,
					//单击行是否选中
					checkOnSelect : false,
					url: null,
					//pagination: true,
					//loadFilter: pagerFilter,
					//交替出现背景
					striped : true,
					//pageSize: 10,
					//pageList: [10,20,30,50],
					rowStyler:function(index,row){    
				      	return 'background-color:#EAF2FD;';    
				    }, 
				    showFooter: true,
					columns:[[
			          { title: getReportType() + '类型',id:"dataTypeColumn",width:300,field: 'name',align:"center"},
			          { title: '总数',field: 'sum',width:130,align:"center"}
					]],
					onLoadError:function()
					{
						$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
						return;
					}    
				});
				$('#tableData').datagrid('loadData',initTableDetails());
			}
			
			//初始化键盘enter事件
			$(document).keydown(function(event)
			{  
			   	//兼容 IE和firefox 事件 
			    var e = window.event || event;  
			    var k = e.keyCode||e.which||e.charCode;  
			    //兼容 IE,firefox 兼容  
			    var obj = e.srcElement ? e.srcElement : e.target;  
			    
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchSupplier" || obj.id=="searchCategoryID"|| obj.id=="searchUsernameID" 
			    	|| obj.id=="searchStatus" || obj.id=="searchSupplierID" ))
			    {  
			        $("#searchBtn").click();
			    }  
			}); 
			
			
			//初始化系统基础信息
			function initSystemData()
			{
				$.ajax({
					type:"post",
					url: "<%=path%>/asset/getBasicData.action",
					//设置为同步
					async:false,
					dataType: "json",
					success: function (systemInfo)
					{
						userList = systemInfo.showModel.map.userList;
						categoryList = systemInfo.showModel.map.categoryList;
						supplierList = systemInfo.showModel.map.supplierList;
						nameList = systemInfo.showModel.map.assetnameList;
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
			function initSelectInfo()
			{
				var options = "";
				if(nameList !=null)
				{
					for(var i = 0 ;i < nameList.length;i ++)
					{
						var nameInfo = nameList[i];
						if(0== i)
						{
							nameID = nameInfo.id;
						}
						options += '<option value="' + nameInfo.id + '">' + nameInfo.assetname + '</option>';
					}	
					$("#searchAssetNameID").empty().append('<option value="">请选择</option>').append(options);
				}	
				
				if(userList !=null)
				{
					options = "";
					for(var i = 0 ;i < userList.length;i ++)
					{
						var user = userList[i];
						if(0 == i)
						{
							userID = user.id
						}
						options += '<option value="' + user.id + '">' + user.username + '</option>';
					}	
					$("#searchUsernameID").empty().append('<option value="">请选择</option>').append(options);
					
				}
				
				if(categoryList !=null)
				{
					options = "";
					for(var i = 0 ;i < categoryList.length;i ++)
					{
						var category = categoryList[i];
						if(0 == i)
						{
							categoryID = category.id;
						}
						options += '<option value="' + category.id + '">' + category.assetname + '</option>';
					}	
					$("#searchCategoryID").empty().append('<option value="">请选择</option>').append(options);
				}
				
				if(supplierList !=null)
				{
					options = "";
					for(var i = 0 ;i < supplierList.length;i ++)
					{
						var supplier = supplierList[i];
						if(0 == i)
						{
							supplierID = supplier.id;
						}
						options += '<option value="' + supplier.id + '">' + supplier.supplier + '</option>';
					}	
					$("#searchSupplierID").empty().append('<option value="">请选择</option>').append(options);
				}
			}
			
			
			//搜索处理
			$("#searchBtn").unbind().bind({
				click:function()
				{
					//是否限制条件	默认限制
				    initShowDataNum();
					//系统基础数据
					getDataInfo();
					//综合图
				    showComboChart();
				    //饼状图
				    showPieChart();
				    //折线图
				    showSpline();
				    //显示柱状图
				    showHistogram();
				    //表格数据
				    initTableData();
				}
			});
			
			
			//重置按钮
			$("#searchResetBtn").unbind().bind({
				click:function(){
					$("#searchAssetNameID").val("");
					$("#searchCategoryID").val("");
					$("#searchUsernameID").val("");
					$("#searchStatus").val("");
					$("#searchSupplierID").val("");
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
			
			//设置饼状图为渐变色
			Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
				   return {
				        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
				        stops: [
				            [0, color],
				            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
				       ]
				   };
			});
			
			
			//饼状图
			function showPieChart()
			{
				//解决初始化范围变小问题
				$("#pieContainer").empty();	
				var getReportTypeInfo = getReportType();
				var reportType = $("#searchReportType").val();
				//封装数据到数组中
				var allDataInfo = new Array();
				
				var showNumInfo = 0;
				if(pageData.length >= showNum)
					showNumInfo = 10;
				else
					showNumInfo = pageData.length;
				for(var i = 0;i < showNumInfo; i++)
				{
					var dataInfo = new Array();
					var totalInfo = pageData[i]
					
					if(reportType == 0)
					{
						if(0 == totalInfo[1])
							dataInfo.push("在库");
						else if(1 == totalInfo[1])
							dataInfo.push("在用");
						else if(2 == totalInfo[1])
							dataInfo.push("消费");
					}
					else
						dataInfo.push(totalInfo[1]);
						
					dataInfo.push(totalInfo[0]);
					allDataInfo.push(dataInfo); 	
				}
								
				new Highcharts.Chart({
					chart: {
				    	renderTo: 'pieContainer',
				        plotBackgroundColor: null,
				        plotBorderWidth: null,
				        backgroundColor:'#EAF2FD',
				        plotShadow: false
					},
				    title: {
				    	text: getReportTypeInfo + "饼状图"
				    },
					tooltip: {
						formatter: function() {
							return this.point.name +':'+ this.y + "个";
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									if(this.point.name.length >10)
				        				return '<b>' + this.point.name.substr(0,10) + "</b>...:"+ this.y + "个";
									return '<b>' + this.point.name +'</b>:'+ this.y + "个";
								}
							}
						}
					},
				    series: [{
				    	type: 'pie',
				        name: '',
				        data: allDataInfo
					}]
				});
			}
			    
			//综合图
			function showComboChart()
			{
				//解决初始化范围变小问题
				$("#zongheContainer").empty();	
				var getReportTypeInfo = getReportType();
				var reportType = $("#searchReportType").val();
				//按照统计数据封装显示数据
				var xName = new Array();
				var columnData = new Array();
				var columnDataForm = null;
				var averageDataForm = null;
				var allDataSum = 0;
				
				var showNumInfo = 0;
				if(pageData.length >= showNum)
					showNumInfo = 10;
				else
					showNumInfo = pageData.length;	
				for(var i = 0 ;i < showNumInfo;i ++)
				{
					var eachData = pageData[i];
					var sum = eachData[0];
					var totalInfo = eachData[1];
					if(reportType == 0)
					{
						if(0 == totalInfo)
							xName.push("在库");
						else if(1 == totalInfo)
							xName.push("在用");
						else if(2 == totalInfo)
							xName.push("消费");
					}
					else
						xName.push(totalInfo);
					columnData.push(sum);
					allDataSum += sum;
				}	
				columnDataForm = 
					{
						type: 'column',
				        name: "资产总数",
				        data: columnData
					};	
					
				averageDataForm = {
			               type: 'spline',
			               name: getReportTypeInfo + '资产概况曲线',
			               data: columnData,
			               marker: {
			                	lineWidth: 2,
			                	lineColor: Highcharts.getOptions().colors[3],
			                	fillColor: 'white'
			               }
			           };
				sumDataForm =
				{
		              type: 'pie',
		              name: '总数',
		              data: [{
		                    name: '资产总数',
		                    y: allDataSum
		               }],
		               center: [450, 1],
		               size: 80,
		               showInLegend: false,
		               dataLabels: {
		                   enabled: true,
		                   align:'center'
		               }
		         }
				new Highcharts.Chart({
					chart: {
				    	renderTo: 'zongheContainer',
				    	backgroundColor:'#EAF2FD'
				  	},
				    title: {
				    	text: getReportTypeInfo + "综合图"
				    },
				    xAxis: {
				    	categories: xName,
				    	labels: { 
		                    rotation: -45,  //逆时针旋转45°，标签名称太长。 
		                    align: 'right',  //设置右对齐 
	                    	formatter: function() {
			        			if(this.value.length >10)
			        				return this.value.substr(0,10) + "...";
			            		return this.value ;
			            	}
		                }
				   	},
				   	yAxis: {
			        	title: {
			        		text: ''
			      		},
			        	labels: {
			        		formatter: function() {
			            		return this.value;
			            	}
			        	}
			        },
			        plotOptions: {
		                column: {
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: true,
		                        style: {
		                            fontWeight: 'bold'
		                        },
		                        formatter: function() {
		                            return this.y;
		                        }
		                    },
		                    //设置是否显示最下面选项
		                    showInLegend: false
		                },
				    	spline: {
							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							},
							showInLegend: false
						},
						line: {
                    		dataLabels: {
                        		enabled: false
                    		},
                   	 		enableMouseTracking: true,
                			showInLegend: false
                		},
		                pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '资产总数: '+ this.y;
								}
							},
							showInLegend: false
						}
            		},
				    tooltip: {
				    	formatter: function() {
				    		var s;
			            	if(this.x == 0)
			            		s =   '在库:'+ this.y +' 个';
			            	else if(this.x ==1)
			            		s =   '在用:'+ this.y +' 个';
			            	else if(this.x ==2)
			            		s =   '消费:'+ this.y +' 个';
			            	else
			            		s = this.x  +':'+ this.y +' 个';
				            return s;
				     	}
				 	},
				    labels: {
		                items: [{
		                    html: '',
		                    style: {
		                        left: '40px',
		                        top: '8px',
		                        color: 'black'
		                    }
		                }]
		            },
				    series: [columnDataForm,averageDataForm,sumDataForm]
				});
			}         
			
			//折线图
			function showSpline()
			{
				//解决初始化范围变小问题
				$("#zxianContainer").empty();	
				var getReportTypeInfo = getReportType();
				var reportType = $("#searchReportType").val();
				var dataInfo = new Array();
				var nameData = new Array();
				var consumeSumInfo= null;
				
				var showNumInfo = 0;
				if(pageData.length >= showNum)
					showNumInfo = 10;
				else
					showNumInfo = pageData.length;
				for(var i = 0 ;i < showNumInfo;i ++)
				{
					var totalInfo = pageData[i]
					dataInfo.push(totalInfo[0]);
					if(reportType == 0)
					{
						if(0 == totalInfo[1])
							nameData.push("在库");
						else if(1 == totalInfo[1])
							nameData.push("在用");
						else if(2 == totalInfo[1])
							nameData.push("消费");
					}
					else
						nameData.push(totalInfo[1]);
				}
				consumeSumInfo= {
						name: getReportTypeInfo + '总额',
						marker: {
							symbol: 'square',
							labels: {
								formatter: function()
								{
									return this.value +'个';
								}
							}
						},
						data: dataInfo
				};
				
				new Highcharts.Chart({
					chart: {
			        	renderTo: 'zxianContainer',
			            type: 'line',
			            backgroundColor:'#EAF2FD'
			        },
			        title: {
			        	text: getReportTypeInfo + "曲线图"
			        },
			        subtitle: {
			        	text: ''
			       	},
			        xAxis: {
			        	categories: nameData,
			        	allowDecimals:false,
			        	labels: { 
		                    rotation: -45,  //逆时针旋转45°，标签名称太长。 
		                    align: 'right',  //设置右对齐 
		                    formatter: function() {
			        			if(this.value.length >10)
			        				return this.value.substr(0,10) + "...";
			            		return this.value ;
			            	}
		                }
			       	},
			        yAxis: {
			        	title: {
			        		text: ''
			      		},
			        	labels: {
			        		formatter: function() {
			            		return this.value;
			            	}
			        	}
			        },
			       	tooltip: {
			        	crosshairs: false,
			            shared: true,
			            formatter: function() {  //格式化提示框的内容样式
			                return this.x +': '+ this.y +'个';
		                }
			      	},
			       	plotOptions: {
			        	spline: {
							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							},
							showInLegend: false
						},
						line: {
                    		dataLabels: {
                        		enabled: true
                    		},
                   	 		enableMouseTracking: true,
                   	 		showInLegend: false
                		}
			      	},
			        series: [consumeSumInfo]
				});
			}
			
			//柱状图
			function showHistogram()
			{	
				//解决初始化范围变小问题
				$("#culumnContainer").empty();
				var getReportTypeInfo = getReportType();
				var reportType = $("#searchReportType").val();				
				var dataInfo = new Array();
				var nameData = new Array();
				var consumeSumInfo= null;
				
				var showNumInfo = 0;
				if(pageData.length >= showNum)
					showNumInfo = 10;
				else
					showNumInfo = pageData.length;
				for(var i = 0 ;i < showNumInfo;i ++)
				{
					var totalInfo = pageData[i]
					dataInfo.push(totalInfo[0]);
					if(reportType == 0)
					{
						if(0 == totalInfo[1])
							nameData.push("在库");
						else if(1 == totalInfo[1])
							nameData.push("在用");
						else if(2 == totalInfo[1])
							nameData.push("消费");
					}
					else
						nameData.push(totalInfo[1]);
						
				}
				consumeSumInfo= {
		        	name: getReportTypeInfo + "柱状图",
		           	data: dataInfo
		        }
				
        		new Highcharts.Chart({
		            chart: {
		                renderTo: 'culumnContainer',
		                type: 'column',
		                backgroundColor:'#EAF2FD'
		            },
		            title: {
		                text: getReportTypeInfo + "柱状图"
		            },
		            xAxis: {
		                categories: nameData,
		                labels: { 
		                    rotation: -45,  //逆时针旋转45°，标签名称太长。 
		                    align: 'right',  //设置右对齐 
		                    formatter: function() {
			        			if(this.value.length >10)
			        				return this.value.substr(0,10) + "...";
			            		return this.value ;
			            	}
		                }
		            },
		            yAxis: {
			        	title: {
			        		text: ''
			      		},
			        	labels: {
			        		formatter: function() {
			            		return this.value;
			            	}
			        	}
			        },
		            plotOptions: {
		                column: {
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: true,
		                        style: {
		                            fontWeight: 'bold'
		                        },
		                        formatter: function() {
		                            return this.y;
		                        }
		                    },
		                    showInLegend: false
		                }
            		},
		            tooltip: {
		                formatter: function() {
		                    return this.x +':'+ this.y +'个';
		                }
		            },
		            credits: {
		                enabled: false
		            },
		            series: [consumeSumInfo]
		        });
			}
			
			//填充表格数据	
			function initTableDetails()
			{
				var reportType = $("#searchReportType").val();
				var dataJson = {};
				dataJson.total = pageData.length;
				var dataArray = [];
				var totalSum = 0;
				var typeSum = 0;
				var footerArray = [];
				
				var showNumInfo = 0;
				if(pageData.length >= showNum)
					showNumInfo = 10;
				else
					showNumInfo = pageData.length;
				for(var i = 0 ;i < showNumInfo;i ++)
				{
					var totalInfo = pageData[i];
					var name = "";
					if(reportType == 0)
					{
						if(0 == totalInfo[1])
							name = "在库";
						else if(1 == totalInfo[1])
							name = "在用";
						else if(2 == totalInfo[1])
							name = "消费";
					}
					else
						name = totalInfo[1];
					var dataInfo = {};
					dataInfo.name = name;
					dataInfo.sum = totalInfo[0];
					totalSum += totalInfo[0];
					dataArray.push(dataInfo);
					typeSum ++;
				}
				
				var footerInfo = {
						sum : totalSum,
						name:getReportType()+ ' ' + typeSum + ' 种类型总数'
				};
				footerArray.push(footerInfo);
				dataJson.rows = dataArray;
				dataJson.footer = footerArray;
				return dataJson;
			}
			//返回统计类型字符串
			function getReportType()
			{
				var reportType = $("#searchReportType").val();
				if(reportType==0)
				{				    	
				  	return '按资产状态统计';
				}
				else if(reportType==1)
				{
				   	return '按资产类型统计';
				}
				else if(reportType==2)
				{
					return '按供应商统计';
				}
				else if(reportType==3)
				{
					return '按资产名称统计';
				}
				else if(reportType==4)
				{
					return '按所属用户统计';
				}				
			}
		</script>
	</body>
</html>