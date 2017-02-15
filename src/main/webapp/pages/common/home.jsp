<%@page import="com.jsh.util.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
	String type = request.getParameter("type");
	String location = "首页";
	if(null != type)
		location = "资产管理 &gt;资产概况";	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  	<head>
    	<title>erp</title>
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
		<style>
			body{ margin:0; height:100%}
			html{ height:100%} /*兼容firefox的div高度100%*/
			#left{ width:150px; height:100%; float:left; _margin-right:-3px;}
			#right{ height:100%;}
			#leftdown{ width:150px; height:100%; float:left; _margin-right:-3px;}
			#rightdown{ height:100%;}
			<!--页面展示特殊要求-->
			.datagrid-body,.datagrid-footer,.datagrid-pager ,.datagrid-view
			{
				background-color:#EAF2FD;
			}
		</style>
  	</head>
  	<body>
  		<div id="position" class="easyui-panel" title="当前位置： <%=location %>" collapsible="false" closable="false"/>
		<!--按月统计资产柱状图 -->
		<div id = "tablePanel0"	class="easyui-panel" style="padding:1px;top:300px;" title="资产报表" iconCls="icon-chart-column" collapsible="true" maximizable="false" closable="false">
			<div id="left" class="easyui-tabs" style="width:630px;height:auto;padding:1px;">
				 <div title="综合图" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;" data-options="iconCls:'icon-chart-zonghe'">
		            <div id="zongheContainer" style="height: 340px;">综合图</div>
		        </div>
			</div>
			<div id="right" class="easyui-tabs" style="height:auto;padding:1px;">
				<div title="柱状图" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;" data-options="iconCls:'icon-chart-statistics'">
		        	<div id="culumnContainer" style="height: 340px;">柱状图</div>
		        </div>		
			</div>
			<div id="leftdown" class="easyui-tabs" style="width:630px;height:auto;padding:1px;">
		        <div title="折线图" data-options="iconCls:'icon-chart-polygram'" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;">
		            <div id="zxianContainer" style="height: 340px;">折现图</div>
		        </div>
			</div>
			<div id="rightdown" class="easyui-tabs" style="height:auto;padding:1px;">
				<div title="饼状图" data-options="iconCls:'icon-chart-pie'" style="padding:10px;background-color: #EAF2FD;height:340px;top:300px;">
		            <div id="pieContainer" style="height: 340px;">饼状图</div>
		        </div>	
			</div>
		</div>	
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="资产列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="height:360px;top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
	    
		<script type="text/javascript">
			//控制图表显示个数
			var showNum = 10;
			//初始化界面
			$(function()
			{
				//progress();
				$.messager.progress({
	                title:'请稍候',
	                msg:'数据加载ing...'
	            });
			    //综合图
			    showComboChart();
			    //显示柱状图
			    showHistogram();
			    //折线图
			    showSpline();
			    //饼状图
			    showPieChart();
				initTableData();
				ininPager();	
				$.messager.progress('close');
			});	
			
			//加载进度条
			function progress()
			{
	            var win = $.messager.progress({
	                title:'请稍候',
	                msg:'数据加载ing...'
	            });
	            setTimeout(function(){
	                $.messager.progress('close');
	            },3300);
	        }
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'资产列表',
					//iconCls:'icon-save',
					//width:700,
					//height:480,
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
					url:'<%=path %>/asset/findBy.action',
					pagination: true,
					//loadFilter: pagerFilter,
					pageSize: 10,
					pageList: [10,20,30,50],
					frozenColumns:[[    
						{ field: 'id',width:35,align:"center",checkbox:true},
			          	{ title: '资产名称',field: 'assetname',width:100},
			          	{ title: '单价', field: 'price',width:70,align:"center"},
			          	{ title: '资产类型', field: 'category',width:115,align:"center"},
			          	{ title: '用户',field: 'username',width:100,align:"center"},
			          	{ title: '购买日期',field: 'purchasedate',width:90,align:"center"},
			          	{ title: '状态',field: 'status',width:50,align:"center"},
			          	{ title: '位置',field: 'location',width:100,align:"center"},
			          	{ title: '资产编号',field: 'assetnum',width:120,align:"center"},
			          	{ title: '序列号',field: 'serialnum',width:120,align:"center"}
					]], 
					columns:[[
			          { title: '有效日期',field: 'periodofvalidity',width:90,align:"center"},
			          { title: '保修日期',field: 'warrantydate',width:90,align:"center"},
			          { title: '供应商',field: 'supplier',width:100,align:"center"},
			          { title: '标签',field: 'labels',width:180,align:"center"},
			          { title: '描述',field: 'description',width:300}
					]],
					onLoadError:function()
					{
						$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
						return;
					}    
				});
			}
			
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
							showAssetDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			function showAssetDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/asset/findBy.action",
					dataType: "json",
					data: ({
						assetNameID:$.trim($("#searchAssetNameID").val()),
						assetCategoryID:$.trim($("#searchCategoryID").val()),
						usernameID:$.trim($("#searchUsernameID").val()),
						status:$.trim($("#searchStatus").val()),
						supplierID:$.trim($("#searchSupplierID").val()),
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
				var pageData = null;
				$.ajax({
						type:"post",
						url: "<%=path%>/report/find.action",
						dataType: "json",
						//取消异步机制，保证页面数据返回再进行处理
						async: false,
						data: ({
							reportType : 2,
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
				//解决初始化范围变小问题
				$("#pieContainer").empty();	
				var getReportTypeInfo = "按供应商统计";
				//封装数据到数组中
				var allDataInfo = new Array();
				for(var i = 0;i < pageData.length; i++)
				{
					var dataInfo = new Array();
					var totalInfo = pageData[i]
					
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
				var pageData = null;
				$.ajax({
						type:"post",
						url: "<%=path%>/report/find.action",
						dataType: "json",
						//取消异步机制，保证页面数据返回再进行处理
						async: false,
						data: ({
							reportType : 0
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
				//解决初始化范围变小问题
				$("#zongheContainer").empty();	
				var getReportTypeInfo = "按资产状态统计";
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
					if(0 == totalInfo)
						xName.push("在库");
					else if(1 == totalInfo)
						xName.push("在用");
					else if(2 == totalInfo)
						xName.push("消费");
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
				    	categories: xName
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
							allowPointSelect: false,
							dataLabels: {
								color: '#000000',
								connectorColor: '#000000',
								formatter: function() {
									return '资产总数: '+ this.y;
								}
							}
						}
            		},
				    tooltip: {
				    	formatter: function() {
	            			return this.key  +':'+ this.y +' 个';
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
				var pageData = null;
				$.ajax({
						type:"post",
						url: "<%=path%>/report/find.action",
						dataType: "json",
						//取消异步机制，保证页面数据返回再进行处理
						async: false,
						data: ({
							reportType : 1
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
				//解决初始化范围变小问题
				$("#zxianContainer").empty();	
				var getReportTypeInfo = "按资产类型统计";
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
					nameData.push(totalInfo[1]);
				}
				consumeSumInfo= {
						name: getReportTypeInfo + '总共',
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
			            		return this.value ;
			            	}
			        	}
			        },
			       	tooltip: {
			        	crosshairs: false,
			            shared: true,
			            formatter: function() {
		                    return  this.x + ":" +this.y +'个';
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
				var pageData = null;
				$.ajax({
						type:"post",
						url: "<%=path%>/report/find.action",
						dataType: "json",
						//取消异步机制，保证页面数据返回再进行处理
						async: false,
						data: ({
							reportType : 3,
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
				//解决初始化范围变小问题
				$("#culumnContainer").empty();
				var getReportTypeInfo = "按资产名称统计";
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
					var totalInfo = pageData[i];
					dataInfo.push(totalInfo[0]);
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
		                    return  this.x + ":" +this.y +'个';
		                }
		            },
		            credits: {
		                enabled: false
		            },
		            series: [consumeSumInfo]
		        });
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