<%@page import="com.jsh.util.common.Tools"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientIp = Tools.getCurrentUserIP();
%>
<!DOCTYPE html>
<html>
  	<head>
    	<title>用户管理</title>
        <meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    	<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
    	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/colorbox/jquery.colorbox-min.js"></script>
        <script type="text/javascript" src="<%=path %>/js/colorbox/colorboxSet.js"></script>
        <link href="<%=path %>/js/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
		<link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css" />
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
  	</head>
  	<body>
  		<!-- 查询 -->
		<div id = "searchPanel"	class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true" closable="false">
			<table id="searchTable">
				<tr>
					<td>用户名称：</td>
					<td>
						<input type="text" name="searchUsername" id="searchUsername"  style="width:100px;"/>
					</td>				
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>登录名称：</td>
					<td>
						<input type="text" name="searchLoginame" id="searchLoginame"  style="width:100px;"/>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>&nbsp;&nbsp;
						<a id="btnSetRole" class='easyui-linkbutton iframe iframe_LargeForm' href='#' title='分配角色'>分配角色</a>&nbsp;&nbsp;
						<a id="btnSetDepart" class='easyui-linkbutton iframe iframe_LargeForm' href='#' title='分配部门'>分配部门</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 数据显示table -->
		<div id = "tablePanel"	class="easyui-panel" style="padding:1px;top:300px;" title="用户列表" iconCls="icon-list" collapsible="true" closable="false">
			<table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
		</div>
		
	    <div id="userDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
	            closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
	        <form id="usernameFM" method="post" novalidate>
	            <div class="fitem" style="padding:5px">
	                <label id="usernameLabel">用户名称&nbsp;&nbsp;</label>
	                <input name="username" id="username" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="loginameLabel">登录名称 &nbsp;&nbsp;</label>
	                <input name="loginame" id="loginame" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="departmentLabel">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;</label>
	                <input name="department" id="department" style="width: 230px;height: 20px"/>
	            </div>
	            <div class="fitem" style="padding:5px">
	                <label id="positionLabel">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;</label>
	                <input name="position" id="position" style="width: 230px;height: 20px"/>
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
	                <label id="descriptionLabel">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;</label>
	                <textarea name="description" id="description" rows="2" cols="2" style="width: 230px;"></textarea>
	            </div>
	            <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
	        </form>
	    </div>
	    <!--
	    <div id="userLoginDlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#logindlg-buttons" modal="true" iconCls="icon-save" collapsible="false" closable="true">
            	<div class="fitem" style="padding:5px">
	                <label>登录名称&nbsp;&nbsp;</label>
	                <input name="loginname" id="loginname" rows="3" cols="3" style="width: 230px;height: 20px"/>
	            </div>	
            	<div class="fitem" style="padding:5px">
	                <label>登录密码&nbsp;&nbsp;</label>
	                <input name="loginpwd" id="loginpwd" rows="3" cols="3" style="width: 230px;height: 20px"/>
	            </div>	
            	<div class="fitem" style="padding:5px">
	                <label>确定密码&nbsp;&nbsp;</label>
	                <input name="confirmloginpwd" id="confirmloginpwd" rows="3" cols="3" style="width: 230px;height: 20px"/>
	            </div>	
       	</div>
	    <div id="logindlg-buttons">
	        <a href="javascript:void(0)" id="saveloginame" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="canceloginame" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#userLoginDlg').dialog('close')">取消</a>
	    </div>
	    -->
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" id="saveusername" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	        <a href="javascript:void(0)" id="cancelusername" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#userDlg').dialog('close')">取消</a>
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
					$("#usernameLabel").empty().append("用户名称&nbsp;&nbsp;");
					$("#loginameLabel").empty().append("登录名称&nbsp;&nbsp;");
					$("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;&nbsp;");
					$("#positionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;&nbsp;");
					$("#phonenumLabel").empty().append("联系电话&nbsp;&nbsp;");
					$("#emailLabel").empty().append("电子邮箱&nbsp;&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;");
					$("#searchPositionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：");
					$("#searchDeptLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：");
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：");
				}
				else
				{
					$("#usernameLabel").empty().append("用户名称&nbsp;");
					$("#loginameLabel").empty().append("登录名称&nbsp;");
					$("#departmentLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;门&nbsp;");
					$("#positionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;位&nbsp;");
					$("#phonenumLabel").empty().append("联系电话&nbsp;");
					$("#emailLabel").empty().append("电子邮箱&nbsp;");
					$("#descriptionLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;");
					$("#searchPositionLabel").empty().append("职&nbsp;&nbsp;&nbsp;&nbsp;位：");
					$("#searchDeptLabel").empty().append("部&nbsp;&nbsp;&nbsp;&nbsp;门：");
					$("#searchDescLabel").empty().append("描&nbsp;&nbsp;&nbsp;&nbsp;述：");
				}
			}
			
			//初始化表格数据
			function initTableData()
			{
				$('#tableData').datagrid({
					//title:'用户列表',
					//iconCls:'icon-save',
					//width:widthInfo,
					height:heightInfo,
					nowrap: false,
					rownumbers: false,
					//动画效果
					animate:false,
					//选中单行
					singleSelect : true,
					collapsible:false,
					//selectOnCheck:false,
					//fitColumns:true,
					//单击行是否选中
					//checkOnSelect : false,
					url:'<%=path %>/user/findBy.action?pageSize=' + initPageSize,
					pagination: true,
					//交替出现背景
					striped : true,
					//loadFilter: pagerFilter,
					pageSize: initPageSize,
					pageList: initPageNum,
					columns:[[   
						{ field: 'id',width:35,align:"center",checkbox:true},
						{ title: '用户名称',field: 'username',width:80},
						{ title: '登录名称', field: 'loginame',width:80,align:"center"},
						{ title: '职位', field: 'position',width:115,align:"center"},
						{ title: '部门',field: 'department',width:115,align:"center"},   
					
			          	{ title: '电子邮箱',field: 'email',width:150,align:"center"},
			          	{ title: '电话号码',field: 'phonenum',width:150,align:"center"},
			          	{ title: '描述',field: 'description',width:150},
			          	{ title: '操作',field: 'op',width:160,formatter:function(value,rec)
			         	 {
							var str = '';
							var rowInfo = rec.id + 'AaBb' + rec.username +'AaBb' + rec.loginame + 'AaBb'+ rec.position 
								+ 'AaBb'+ rec.department + 'AaBb'+ rec.email + 'AaBb'+ rec.phonenum + 'AaBb'+ rec.ismanager
								+ 'AaBb' + rec.isystem + 'AaBb' + rec.description;
        					if(1 == value)
        					{
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editUser(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editUser(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        						str += '<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteUser('+ rec.id +');"/>&nbsp;<a onclick="deleteUser('+ rec.id +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>&nbsp;&nbsp;';
        						//str += '<img src="<%=path%>/images/user.png" style="cursor: pointer;width: 16px;height: 16px" onclick="editUser(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editUser(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">用户</a>&nbsp;&nbsp;';		
        					}
        					//else
        					//{
        						//str += '<img src="<%=path%>/images/admin.png" style="cursor: pointer;width: 16px;height: 16px" onclick="editUser(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editUser(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">管理</a>&nbsp;&nbsp;';		
        					//}
        					return str;
						}
			          }
					]],
					toolbar:[
						{
							id:'addUser',
							text:'增加',
							iconCls:'icon-add',
							handler:function()
							{
								addUser();
							}
						},
						{
							id:'deleteUser',
							text:'删除',
							iconCls:'icon-remove',
							handler:function()
							{
								batDeleteUser();	
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
			    if(k == "13"&&(obj.id=="username" || obj.id=="department"|| obj.id=="phonenum" 
			    	|| obj.id=="email" || obj.id=="description" || obj.id=="position"))
			    {  
			        $("#saveusername").click();
			    }  
			    
			    //搜索按钮添加快捷键
			    if(k == "13"&&(obj.id=="searchUsername" || obj.id=="searchLoginame"|| obj.id=="searchPhonenum" 
			    	|| obj.id=="searchPosition"|| obj.id=="searchEmail" || obj.id=="searchDesc" || obj.id=="searchDept" ))
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
							showUserDetails(pageNum,pageSize);
						}  
					}); 
				}
				catch (e) 
				{
					$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
				}
			}
			
			//删除用户信息
			function deleteUser(userID)
			{
				$.messager.confirm('删除确认','确定要删除此用户信息吗？',function(r)
			 	{
                    if (r)
                    {
						$.ajax({
							type:"post",
							url: "<%=path %>/user/delete.action",
							dataType: "json",
							data: ({
								userID : userID,
								clientIp:'<%=clientIp %>'
							}),
							success: function (tipInfo)
							{
								var msg = tipInfo.showModel.msgTip;
								if(msg == '成功')
								{
									//$('#tableData').datagrid('reload');
									//加载完以后重新初始化
									$("#searchBtn").click();
									//var opts = $("#tableData").datagrid('options'); 
									//showUserDetails(opts.pageNumber,opts.pageSize);
								}
								else
									$.messager.alert('删除提示','删除用户信息失败，请稍后再试！','error');
							},
							//此处添加错误处理
				    		error:function()
				    		{
				    			$.messager.alert('删除提示','删除用户信息异常，请稍后再试！','error');
								return;
							}
						});			
                    }
                });
			}
			
			//批量删除用户
			function batDeleteUser()
			{
				var row = $('#tableData').datagrid('getChecked');
				if(row.length == 0)
				{
					$.messager.alert('删除提示','没有记录被选中！','info');				
					return;	
				}
				if(row.length > 0)
				{
					$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条用户信息吗？',function(r)
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
								url: "<%=path %>/user/batchDelete.action",
								dataType: "json",
								async :  false,
								data: ({
									userIDs : ids,
									clientIp:'<%=clientIp %>'
								}),
								success: function (tipInfo)
								{
									var msg = tipInfo.showModel.msgTip;
									if(msg == '成功')
									{
										//$('#tableData').datagrid('reload');
										//加载完以后重新初始化
										$("#searchBtn").click();
										$(":checkbox").attr("checked",false);
									}
									else
										$.messager.alert('删除提示','删除用户信息失败，请稍后再试！','error');
								},
								//此处添加错误处理
					    		error:function()
					    		{
					    			$.messager.alert('删除提示','删除用户信息异常，请稍后再试！','error');
									return;
								}
							});	
	                    }
	                });
				 }
			}
			
			//增加用户
			var url;
			var userID = 0;
			//保存编辑前的名称
			var orgusername = "";
			
			function addUser()
			{
				$('#userDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加用户');
				$(".window-mask").css({ width: webW ,height: webH});
	            $('#usernameFM').form('clear');
	            var row = {
	            	clientIp:'<%=clientIp %>'
	            };
                $('#usernameFM').form('load',row);
	            $("#username").focus();
	            orgusername = "";
	            userID = 0;
	            url = '<%=path %>/user/create.action';
			}
			
			//保存用户信息
			$("#saveusername").unbind().bind({
				click:function()
				{
					if(checkusernameName())
						return;
						
					var reg = /^([0-9])+$/;
					var phonenum = $.trim($("#phonenum").val());
					if(phonenum.length>0 && !reg.test(phonenum))
					{
						$.messager.alert('提示','电话号码只能是数字','info');
						$("#phonenum").val("").focus();
						return;
					}
					
					$('#usernameFM').form('submit',{
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
		                            msg: '保存用户信息失败，请稍后重试!'
		                        });
		                    } 
		                    else 
		                    {
		                        $('#userDlg').dialog('close');
		                        //$('#tableData').datagrid('reload');
		                        //加载完以后重新初始化
								//$("#searchBtn").click(); 
								var opts = $("#tableData").datagrid('options'); 
								showUserDetails(opts.pageNumber,opts.pageSize);   
		                    }
		                }
		            });	
				}
			});
			
			//编辑用户信息
	        function editUser(usernameTotalInfo)
	        {
	        	var usernameInfo = usernameTotalInfo.split("AaBb");
	            var row = {
	            	username : usernameInfo[1],
	            	loginame : usernameInfo[2],
	            	position : usernameInfo[3],
	            	department : usernameInfo[4],
	            	email : usernameInfo[5],
	            	phonenum : usernameInfo[6],
	            	description : usernameInfo[9],
	            	clientIp:'<%=clientIp %>'
	            };
	            orgusername = usernameInfo[1];
                $('#userDlg').dialog('open').dialog('setTitle','<img src="<%=path%>/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑用户信息');
                $(".window-mask").css({ width: webW ,height: webH});
                $('#usernameFM').form('load',row);
                userID = usernameInfo[0];
                //焦点在名称输入框==定焦在输入文字后面 
                $("#username").val("").focus().val(usernameInfo[1]);
                url = '<%=path %>/user/update.action?userID=' + usernameInfo[0];
	        }
	        
	        //$("#username").unbind().bind({
	        	//blur:checkusernameName
	        //});
	        
	        //检查用户 名称是否存在 ++ 重名无法提示问题需要跟进
	        function checkusernameName()
	        {
	        	var usernameName = $.trim($("#username").val());
	        	//表示是否存在 true == 存在 false = 不存在
	        	var flag = false;
        		//开始ajax名称检验，不能重名
        		if(usernameName.length > 0 &&( orgusername.length ==0 || usernameName != orgusername))
        		{
        			$.ajax({
						type:"post",
						url: "<%=path %>/user/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							userID : userID,
							username : usernameName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','用户名称已经存在','info');
								//alert("用户名称已经存在");
								//$("#username").val("");
								return;
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','检查用户名称是否存在异常，请稍后再试！','error');
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
					showUserDetails(1,initPageSize);	
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
			
			function showUserDetails(pageNo,pageSize)
			{
				$.ajax({
					type:"post",
					url: "<%=path %>/user/findBy.action",
					dataType: "json",
					data: ({
						username:$.trim($("#searchUsername").val()),
						loginame:$.trim($("#searchLoginame").val()),
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
					$("#searchUsername").val("");
					$("#searchLoginame").val("");
					$("#searchPhonenum").val("");
					$("#searchPosition").val("");
					$("#searchDept").val("");
					$("#searchEmail").val("");
					$("#searchDesc").val("");
					
					
					//加载完以后重新初始化
					$("#searchBtn").click();
			    }	
			});
			
			//分配角色
			$('#btnSetRole').click(function () {
			    var currentRow = $("#tableData").datagrid("getSelected");
	            if (currentRow == null) {
	                alert("请选择一条数据再操作！");
	                return false;
	            }
	            this.href = "<%=path %>/pages/user/userRole.jsp?id=" + currentRow.id;
            });
			
			//分配部门
			$('#btnSetDepart').click(function () {
			    var currentRow = $("#tableData").datagrid("getSelected");
	            if (currentRow == null) {
	                alert("请选择一条数据再操作！");
	                return false;
	            }
	            this.href = "<%=path %>/pages/user/userDepot.jsp?id=" + currentRow.id;
            });
		</script>
	</body>
</html>