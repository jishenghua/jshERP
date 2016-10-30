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
		<title>用户对应角色</title>
		<meta charset="utf-8">
		<!-- 指定以IE8的方式来渲染 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
		<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=path%>/js/easyui-1.3.5/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/js/easyui-1.3.5/themes/icon.css" />
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/common.css" />
		<script type="text/javascript" src="<%=path%>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path%>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
	</head>
	<body>
		<!-- 数据显示table -->
		<div>
			<a id="btnOK" class="easyui-linkbutton">保存</a>
		</div>
		<div>
		<ul id="tt"></ul>
		</div>

		<script type="text/javascript">
		var url_id = getUrlParam('id');  //获取传值id（用户id）
		var type="UserRole";
		var url;//定义链接地址
	    function GetNode(ctype) {
	        var node = $('#tt').tree('getChecked');
	        var cnodes = '';
	        var pnodes = '';
	
	        var prevNode = ''; //保存上一步所选父节点
	        for (var i = 0; i < node.length; i++) {
	
	            if ($('#tt').tree('isLeaf', node[i].target)) {
	                cnodes += '[' + node[i].id + ']';
	
	                var pnode = $('#tt').tree('getParent', node[i].target); //获取当前节点的父节点
	                if (prevNode != pnode.id) //保证当前父节点与上一次父节点不同
	                {
	                    pnodes += '[' + pnode.id + ']';
	                    prevNode = pnode.id; //保存当前节点
	                }
	            }
	        }
	        //cnodes = cnodes.substring(0, cnodes.length - 1);
	        pnodes = pnodes.substring(0, pnodes.length - 1);
	
	        if (ctype == 'child') { return cnodes; }
	        else { return pnodes };
	    };
		
		$(function () {
		
			$('#tt').tree({   
			   url:'<%=path%>/role/findUserRole.action?UBType='+type+'&UBKeyId='+url_id,
			   animate:true,
			   checkbox:true
			});  
			
			
	        $("#btnOK").click(	        
		        function() {
			        if(!checkUserRole())
					{
					    url = '<%=path%>/userBusiness/create.action';
					}
					else
					{
					    url = '<%=path%>/userBusiness/update.action';
					}
		        
		        if (confirm("您确定要保存吗？")) {
		        
			        $.ajax({
	                    type: "post",
	                    url:url,
	                    data: { Type: type, KeyId: url_id, Value: GetNode('child'),clientIp:'<%=clientIp %>'
	                    },
	                    dataType: "json",
						async :  false,
	                    success: function (tipInfo) {
	                        if (tipInfo) {
	                            self.parent.$.colorbox.close();
	                            alert("操作成功！");
	                        }
	                        else
	                            alert(tipInfo);
	                    }
	                });
			      }
		        }
	        );
	        
        });
        
        //检查记录是否存在 
        function checkUserRole()
        {
        	//表示是否存在 true == 存在 false = 不存在
        	var flag = false;
       		//开始ajax名称检验，是否存在
      			$.ajax({
				type:"post",
				url: "<%=path %>/userBusiness/checkIsValueExist.action",
				dataType: "json",
				async :  false,
				data: ({
					Type : type,
					KeyId : url_id
				}),
				success: function (tipInfo)
				{
					flag = tipInfo;
				},
				//此处添加错误处理
	    		error:function()
	    		{
	    			$.messager.alert('提示','检查用户对应功能是否存在异常，请稍后再试！','error');
					return;
				}
			});	
       		return flag;
        }
		</script>
	</body>
</html>