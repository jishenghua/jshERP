	//初始化界面
	$(function(){
		var accountList = null;
		var accountID = null;
		var supplierList = null;
		var supplierID = null;
		var personList = null;
		var personID = null;
		var ProjectSearch = null;
		var accountHeadMaxId = null; //获取最大的Id
		var accepId = null; //保存的主表id
		var url;
		var accountHeadID = 0;
		var preTotalPrice = 0; //前一次加载的金额
		var orgAccountHead = ""; //保存编辑前的名称
		var editIndex = undefined;
		var listTitle = ""; //单据标题
		var payTypeTitle = "";//收入 支出
		var organUrl = ""; //组织数据接口地址
		var amountNum = ""; //单据编号开头字符
		var itemType = true; //隐藏当前列
		var moneyType = true; //隐藏当前列
		var inOrOut = ""; //链接类型为收入或者支出
		getType();
		initSystemData_person(); //经手人数据
		initSelectInfo_person(); //经手人信息
		initSystemData_account(); //账户数据
		initSelectInfo_account(); //账户信息
		initSupplier(); //供应商
		initTableData();
		ininPager();
		initForm();	
		bindEvent();//绑定操作事件
		$("#searchBtn").click();	
	});
	//根据单据名称获取类型
	function getType(){
		listTitle = $("#tablePanel").prev().text();
		var supUrl = path + "/supplier/findBySelect_sup.action"; //供应商接口
		var cusUrl = path + "/supplier/findBySelect_cus.action"; //客户接口
		var retailUrl = path + "/supplier/findBySelect_retail.action"; //散户接口
		if(listTitle === "收入单列表"){
			listType = "收入"; 
			itemType = false; //显示当前列
			moneyType = true; //隐藏当前列
			payTypeTitle = "收入项目";
			inOrOut = "in";
			organUrl = cusUrl;
			amountNum = "SR";
		}
		else if(listTitle === "支出单列表"){
			listType = "支出"; 
			itemType = false; //显示当前列
			moneyType = true; //隐藏当前列
			payTypeTitle = "支出项目";
		    inOrOut = "out";
			organUrl = supUrl;
			amountNum = "ZC";
		}
		else if(listTitle === "收款单列表"){
			listType = "收款"; 			
			itemType = true; //隐藏当前列
			moneyType = false; //显示当前列
			payTypeTitle = "无标题";
			inOrOut = "";
			organUrl = cusUrl;
			amountNum = "SK";
		}
		else if(listTitle === "付款单列表"){
			listType = "付款"; 
			itemType = true; //隐藏当前列
			moneyType = false; //显示当前列
			payTypeTitle = "无标题";
			inOrOut = "";
			organUrl = supUrl;
			amountNum = "FK";
		}
		else if(listTitle === "转账单列表"){
			listType = "转账"; 
			itemType = true; //隐藏当前列
			moneyType = false; //显示当前列
			payTypeTitle = "无标题";
			inOrOut = "";
			organUrl = supUrl;
			amountNum = "ZZ";
		}
		else if(listTitle === "收预付款列表"){
			listType = "收预付款";
			itemType = true; //隐藏当前列
			moneyType = false; //显示当前列
			payTypeTitle = "无标题";
			inOrOut = "";
			organUrl = retailUrl;
			amountNum = "SYF";
		}
	}
	//获取账户信息
	function initSystemData_account(){
		$.ajax({
			type:"post",
			url: path + "/account/getAccount.action",
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				accountList = systemInfo.showModel.map.accountList;
				var msgTip = systemInfo.showModel.msgTip;
				if(msgTip == "exceptoin")
				{
					$.messager.alert('提示','查找账户信息异常,请与管理员联系！','error');
					return;
				}	
			}
		});				
	}
	//获取账户信息
	function initSelectInfo_account(){
		var options = "";				
		if(accountList !=null)
		{
			options = "";
			for(var i = 0 ;i < accountList.length; i++)
			{
				var account = accountList[i];
				options += '<option value="' + account.id + '">' + account.name + '</option>';
			}	
			$("#AccountId").empty().append(options);
		}
	}
	
	//初始化单位信息
	function initSupplier(){
		$('#OrganId').combobox({    
			url: organUrl,
		    valueField:'id',    
		    textField:'supplier',
			formatter: function(row){
				var opts = $(this).combobox('options');
				if(row[opts.textField]!=="非会员") {
					return row[opts.textField];
				}
			}
		});  
	}
	
	//获取财务员
	function initSystemData_person(){
		var type = "财务员";
		$.ajax({
			type:"post",
			url: path + "/person/getPersonByType.action",
			data: {
				Type: type
			},
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				personList = systemInfo.showModel.map.personList;
				var msgTip = systemInfo.showModel.msgTip;
				if(msgTip == "exceptoin")
				{
					$.messager.alert('提示','查找系统基础信息异常,请与管理员联系！','error');
					return;
				}	
			}
		});				
	}
	//获取财务员
	function initSelectInfo_person(){
		var options = "";
		
		if(personList !=null)
		{
			for(var i = 0 ;i < personList.length;i++)
			{
				var person = personList[i];
				if(0 == i)
				{
					personID = person.id;
				}
				if(person.type=="财务员")
				{
					options += '<option value="' + person.id + '">' + person.name + '</option>';
				}		
			}
			$("#HandsPersonId").empty().append(options);
		}
	}
	
	//防止表单提交重复
	function initForm(){
		$('#accountHeadFM').form({
		    onSubmit: function(){
		        return false;
		    }
		});
	}
	
	//初始化表格数据
	function initTableData(){
		$('#tableData').datagrid({
			//width:700,
			height:heightInfo,
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
			pagination: true,
			//交替出现背景
			striped : true,
			//loadFilter: pagerFilter,
			pageSize: 5,
			pageList: initPageNum,
			columns:[[
			  { field: 'Id',width:35,align:"center",checkbox:true},
			  {field: 'OrganId',width:5, hidden:true},
	          { title: '单据编号',field: 'BillNo',width:100},
	          { title: '单据时间 ',field: 'BillTime',width:100},
	          { title: '合计',field: 'TotalPrice',width:80},
	          { title: '备注',field: 'Remark',width:100},
	          { title: '操作',field: 'op',align:"center",width:180,formatter:function(value,rec)
	         	{
					var str = '';
					var rowInfo = rec.Id + 'AaBb' + rec.BillNo+ 'AaBb' + rec.BillTime+ 'AaBb' + rec.Remark
					+ 'AaBb' + rec.AccountId+ 'AaBb' + rec.AccountName + 'AaBb' + rec.OrganId + 'AaBb' + rec.OrganName 
					+ 'AaBb' + rec.HandsPersonId + 'AaBb' + rec.HandsPersonName + 'AaBb' + rec.ChangeAmount + 'AaBb' + rec.TotalPrice;
					if(1 == value)
					{
						str += '<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showAccountHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="showAccountHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
						str += '<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAccountHead(\'' + rowInfo + '\');"/>&nbsp;<a onclick="editAccountHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
						str += '<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAccountHead('+ rec.Id +',' + rec.OrganId +',' + rec.TotalPrice+ ');"/>&nbsp;<a onclick="deleteAccountHead('+ rec.Id  +',' + rec.OrganId +',' + rec.TotalPrice +');" style="text-decoration:none;color:black;" href="javascript:void(0)">删除</a>';
					}
					return str;
				}
	          }
			]],
			toolbar:[
				{
					id:'addAccountHead',
					text:'增加',
					iconCls:'icon-add',
					handler:function()
					{
						addAccountHead();
					}
				},
				{
					id:'deleteAccountHead',
					text:'删除',
					iconCls:'icon-remove',
					handler:function()
					{
						batDeleteAccountHead();	
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
	
	//初始化表格数据-明细列表-编辑状态
	function initTableData_account(type,TotalPrice){
		$('#accountData').datagrid({
			height:300,
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
			pagination: true,
			//交替出现背景
			striped : true,
			showFooter: true,
			//loadFilter: pagerFilter,
			onClickRow: onClickRow,
			pageSize: 50,
			pageList: [50,100,150],
			columns:[[
			  { field: 'Id',width:35,align:"center",checkbox:true},
	          { title: payTypeTitle,field: 'InOutItemId',width:230,hidden:itemType,
				  formatter:function(value,row,index){
						  return row.InOutItemName;
                  },
				  editor:{
                  type:'combobox',
                  options:{
                      valueField:'Id',
                      textField:'InOutItemName',
                      method:'get',
                      url: path + "/inOutItem/findBySelect.action?type=" + inOrOut
                  }
	            }
			  },
			  { title: '账户名称',field: 'AccountId',width:230,hidden:moneyType,
				  formatter:function(value,row,index){
						  return row.AccountName;
                  },
				  editor:{
                  type:'combobox',
                  options:{
                      valueField:'Id',
                      textField:'AccountName',
                      method:'get',
                      url: path + "/account/findBySelect.action"
                  }
	            }
			  },
	          { title: '金额',field: 'EachAmount',editor:'validatebox',width:50},
	          { title: '备注',field: 'Remark',editor:'validatebox',width:150}
			]],
			toolbar:[
				{
					id:'append',
					text:'新增',
					iconCls:'icon-add',
					handler:function()
					{
						append(); //新增
					}
				},
				{
					id:'delete',
					text:'删除',
					iconCls:'icon-remove',
					handler:function()
					{
						removeit(); //删除	
					}
				},
				{
					id:'reject',
					text:'撤销',
					iconCls:'icon-undo',
					handler:function()
					{
						reject(); //撤销	
					}
				}
			],
			onLoadError:function()
			{
				$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
				return;
			}    
		});
		$.ajax({
			type:"post",
			url: path + '/accountItem/findBy.action?HeaderId=' + accountHeadID,
			dataType: "json",
			success: function (res) {
				var EachAmount = 0;
				if(type === "edit") {
					EachAmount = TotalPrice;							
				}
				var array = [];
				array.push({
					"EachAmount": EachAmount
				});
				res.footer = array;
				$("#accountData").datagrid('loadData',res);
			},
			error:function() {
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
			}
		});
	}
	
	
	//初始化表格数据-明细列表-查看状态
	function initTableData_account_show(TotalPrice){
		$('#accountDataShow').datagrid({
			height:300,
			rownumbers: true,
			//动画效果
			animate:false,
			//选中单行
			singleSelect : true,
			collapsible:false,
			selectOnCheck:false,
			//fitColumns:true,
			//单击行是否选中
			//checkOnSelect : false,
			pagination: true,
			//交替出现背景
			striped : true,
			showFooter: true,
			//loadFilter: pagerFilter,
			onClickRow: onClickRow,
			pageSize: 50,
			pageList: [50,100,150],
			columns:[[
	          { title: payTypeTitle, field: 'InOutItemName', width:230, hidden:itemType},
	          { title: '账户名称', field: 'AccountName', width:230, hidden:moneyType},	         
	          { title: '金额',field: 'EachAmount',width:50},
	          { title: '备注',field: 'Remark',width:150}
			]],
			onLoadError:function()
			{
				$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
				return;
			}    
		});
		$.ajax({
			type:"post",
			url: path + '/accountItem/findBy.action?HeaderId=' + accountHeadID,
			dataType: "json",
			success: function (res) {
				var EachAmount = TotalPrice;			
				var array = [];
				array.push({
					"EachAmount": EachAmount
				});
				res.footer = array;
				$("#accountDataShow").datagrid('loadData',res);
			},
			error:function() {
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
			}
		});
	}
	
	
	//分页信息处理
	function ininPager(){
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
					showAccountHeadDetails(pageNum,pageSize);
				}  
			}); 
		}
		catch (e) 
		{
			$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
		}
	}
	
	//删除财务信息
	function deleteAccountHead(accountHeadID, thisOrganId, totalPrice){
		$.messager.confirm('删除确认','确定要删除此财务信息吗？',function(r)
	 	{
            if (r)
            {
				$.ajax({
					type:"post",
					url: path + "/accountHead/delete.action",
					dataType: "json",
					data: ({
						accountHeadID : accountHeadID,
						clientIp: clientIp
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
							$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
					},
					//此处添加错误处理
		    		error:function()
		    		{
		    			$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
						return;
					}
				});

				//更新会员的预收款信息
				if(listType === "收预付款"){
					$.ajax({
						type:"post",
						url: path + "/supplier/updateAdvanceIn.action",
						dataType: "json",
						data:{
							SupplierID: thisOrganId, //会员id
							AdvanceIn: 0-totalPrice  //删除时同时删除用户的预付款信息
						},
						success: function(res){
							if(res) {
								//保存会员预收款成功
							}
						},
						error: function(){
							$.messager.alert('提示','保存信息异常，请稍后再试！','error');
							return;
						}
					});
				}
            }
        });
	}
	
	//批量删除财务信息
	function batDeleteAccountHead(){
		var row = $('#tableData').datagrid('getChecked');	
		if(row.length == 0)
		{
			$.messager.alert('删除提示','没有记录被选中！','info');				
			return;	
		}
		if(row.length > 0)
		{
			$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条财务信息吗？',function(r)
		 	{
                if (r)
                {
                	var ids = "";
                    for(var i = 0;i < row.length; i ++) {
                    	if(i == row.length-1)
                    	{
                    		ids += row[i].Id;
                    		break;
                    	}
                    	ids += row[i].Id + ",";
                    }
                    //批量更新会员的预收款信息
					for(var i = 0;i < row.length; i ++) {
						if(listType === "收预付款"){
							$.ajax({
								type:"post",
								url: path + "/supplier/updateAdvanceIn.action",
								dataType: "json",
								data:{
									SupplierID: row[i].OrganId, //会员id
									AdvanceIn: 0 - row[i].TotalPrice  //删除时同时删除用户的预付款信息
								},
								success: function(res){
									if(res) {
										//保存会员预收款成功
									}
								},
								error: function(){
									$.messager.alert('提示','保存信息异常，请稍后再试！','error');
									return;
								}
							});
						}
					}
					//批量删除
                    $.ajax({
						type:"post",
						url: path + "/accountHead/batchDelete.action",
						dataType: "json",
						async :  false,
						data: ({
							accountHeadIDs : ids,
							clientIp: clientIp
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
								$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
							return;
						}
					});	
                }
            });
		 }
	}

	//增加
	function addAccountHead(){
		$("#clientIp").val(clientIp);
		$('#accountHeadFM').form('clear');
		var thisDate = getNowFormatDate(); //当前日期
		$("#BillTime").val(thisDate);
		var thisNumber = getNowFormatDateNum(); //根据时间生成编号
		$("#BillNo").val(amountNum + thisNumber).focus();
		var addTitle = listTitle.replace("列表","信息");
		$('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加' + addTitle);
		$(".window-mask").css({ width: webW ,height: webH});
        
        orgAccountHead = "";
        accountHeadID = 0;
        initTableData_account("add"); //明细列表
        reject(); //撤销下、刷新材料列表
        url = path + '/accountHead/create.action';
	}
	
	//编辑信息
    function editAccountHead(accountHeadTotalInfo){
    	var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
        $("#clientIp").val(clientIp);
        $("#BillNo").val(accountHeadInfo[1]);
        $("#BillTime").val(accountHeadInfo[2]);
        $("#Remark").val(accountHeadInfo[3]);
        $("#AccountId").val(accountHeadInfo[4]);
        $('#OrganId').combobox('setValue', accountHeadInfo[6]);
        $("#HandsPersonId").val(accountHeadInfo[8]);
        $("#ChangeAmount").val(accountHeadInfo[10]);
        var TotalPrice = accountHeadInfo[11];
		preTotalPrice = accountHeadInfo[11]; //记录前一次合计金额，用于收预付款
        var editTitle = listTitle.replace("列表","信息");
        $('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑' + editTitle);
        $(".window-mask").css({ width: webW ,height: webH});
        accountHeadID = accountHeadInfo[0];
        
        initTableData_account("edit",TotalPrice); //明细列表
        reject(); //撤销下、刷新列表                
        url = path + '/accountHead/update.action?accountHeadID=' + accountHeadInfo[0];
    }
    
    //查看信息
    function showAccountHead(accountHeadTotalInfo){
    	var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
        $("#BillNoShow").text(accountHeadInfo[1]);
        $("#BillTimeShow").text(accountHeadInfo[2]);	     
        $("#RemarkShow").text(accountHeadInfo[3]);       
        $("#AccountIdShow").text(accountHeadInfo[5]);
        $('#OrganIdShow').text(accountHeadInfo[7]);
        $("#HandsPersonIdShow").text(accountHeadInfo[9]);
        $("#ChangeAmountShow").text(accountHeadInfo[10]);
        var TotalPrice = accountHeadInfo[11];
        var showTitle = listTitle.replace("列表","信息");
        $('#accountHeadDlgShow').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看' + showTitle);
        $(".window-mask").css({ width: webW ,height: webH});
        
        accountHeadID = accountHeadInfo[0];
        initTableData_account_show(TotalPrice); //明细列表-查看状态
    }

    //绑定操作事件
	function bindEvent(){
		//搜索处理
		$("#searchBtn").unbind().bind({
			click:function()
			{
				showAccountHeadDetails(1,initPageSize);	
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

		//重置按钮
		$("#searchResetBtn").unbind().bind({
			click:function(){
				$("#searchBillNo").val("");
				$("#searchBeginTime").val("");
				$("#searchEndTime").val("");
				//加载完以后重新初始化
				$("#searchBtn").click();
		    }	
		});

		//保存信息
		$("#saveAccountHead").unbind().bind({
			click:function()
			{
				if(!$('#accountHeadFM').form('validate'))
					return;
				else 
				{
					if(listTitle === "收入单列表"){
						if(!$('#AccountId').val()){
							$.messager.alert('提示','请选择收款账户！','warning');
							return;
						}
						if(!$('#OrganId').combobox('getValue')){
							$.messager.alert('提示','请选择往来单位！','warning');
							return;
						}
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
					}
					else if(listTitle === "支出单列表"){
						if(!$('#AccountId').val()){
							$.messager.alert('提示','请选择付款账户！','warning');
							return;
						}
						if(!$('#OrganId').combobox('getValue')){
							$.messager.alert('提示','请选择往来单位！','warning');
							return;
						}
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
					}
					else if(listTitle === "收款单列表"){
						if(!$('#OrganId').combobox('getValue')){
							$.messager.alert('提示','请选择付款单位！','warning');
							return;
						}
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
					}
					else if(listTitle === "付款单列表"){
						if(!$('#OrganId').combobox('getValue')){
							$.messager.alert('提示','请选择收款单位！','warning');
							return;
						}
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
					}
					else if(listTitle === "转账单列表"){
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
						if(!$('#AccountId').val()){
							$.messager.alert('提示','请选择付款账户！','warning');
							return;
						}
					}
					else if(listTitle === "收预付款列表"){
						if(!$('#HandsPersonId').val()){
							$.messager.alert('提示','请选择经手人！','warning');
							return;
						}
						if(!$('#OrganId').combobox('getValue')){
							$.messager.alert('提示','请选择付款会员！','warning');
							return;
						}
					}
					var OrganId = null;
					var ChangeAmount = $.trim($("#ChangeAmount").val());		
					var TotalPrice = $("#accountHeadFM .datagrid-footer [field='EachAmount'] div").text();		
					if(listType !=="转账"){
						OrganId = $('#OrganId').combobox('getValue');
					}
					if(listType === "支出"){
						//支出为负数
						ChangeAmount = 0 - ChangeAmount;
					}
					if(listType === "支出" || listType === "付款"){
						//支出和付款为负数
						TotalPrice = 0 - TotalPrice;
					}
					//更新会员的预收款信息
					if(listType === "收预付款"){
						var advanceIn = 0; //预付款金额
						if(accountHeadID){
							advanceIn = TotalPrice - preTotalPrice;  //修改时，预付款=合计金额-加载金额
						}
						else{
							advanceIn = TotalPrice; //新增时，预付款=合计金额
						}
						$.ajax({
							type:"post",
							url: path + "/supplier/updateAdvanceIn.action",
							dataType: "json",
							data:{
								SupplierID: OrganId,
								AdvanceIn: advanceIn
							},
							success: function(res){
								if(res) {
									//保存会员预收款成功
								}
							},
							error: function(){
								$.messager.alert('提示','保存信息异常，请稍后再试！','error');
								return;
							}
						});
					}

					//保存单位信息
					$.ajax({
						type:"post",
						url: url,
						dataType: "json",
						async :  false,
						data: ({
							Type: listType,
							BillNo : $.trim($("#BillNo").val()),
							BillTime : $.trim($("#BillTime").val()),
							AccountId: $.trim($("#AccountId").val()),
							ChangeAmount: ChangeAmount, //付款/收款/优惠/实付
							TotalPrice: TotalPrice, //合计
							OrganId: OrganId,
							HandsPersonId: $.trim($("#HandsPersonId").val()),
							Remark: $.trim($("#Remark").val()),
							clientIp: clientIp
						}),
						success: function (tipInfo)
						{
							if(tipInfo)
							{
								//保存明细记录
								if(accountHeadID ==0)
								{
									getMaxId(); //查找最大的Id
									accept(accountHeadMaxId); //新增
								}
								else
								{
									accept(accountHeadID); //修改
								}
							
								$('#accountHeadDlg').dialog('close');
								var opts = $("#tableData").datagrid('options'); 
								showAccountHeadDetails(opts.pageNumber,opts.pageSize); 
							}
							else
							{
								$.messager.show({
		                            title: '错误提示',
		                            msg: '保存信息失败，请稍后重试!'
		                        });
							}
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('提示','保存信息异常，请稍后再试！','error');
							return;
						}
					});	
				}
			}
		});
		//初始化键盘enter事件
		$(document).keydown(function(event)
		{
		   	//兼容 IE和firefox 事件 
		    var e = window.event || event;  
		    var k = e.keyCode||e.which||e.charCode;  
		    //兼容 IE,firefox 兼容  
		    var obj = e.srcElement ? e.srcElement : e.target;  
		    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
		    if(k == "13"&&(obj.id=="BillNo"||obj.id=="BillTime"))
		    {  
		        $("#saveAccountHead").click();
		    }
		    //搜索按钮添加快捷键
		    if(k == "13"&&(obj.id=="searchBillNo"))
		    {  
		        $("#searchBtn").click();
		    }  
		});
	}			
				
	function showAccountHeadDetails(pageNo,pageSize){
		$.ajax({
			type:"post",
			url: path + "/accountHead/findBy.action",
			dataType: "json",
			data: ({
				Type: listType,
				BillNo:$.trim($("#searchBillNo").val()),
				BeginTime:$("#searchBeginTime").val(),
				EndTime:$("#searchEndTime").val(),
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
	
	//自动计算事件
	function autoReckon() {
		//延时绑定事件
	    setTimeout(function(){
	    	var body =$("#accountHeadFM .datagrid-body");
	    	var footer =$("#accountHeadFM .datagrid-footer");
	    	var input = ".datagrid-editable-input";
	    	
	    	//修改金额，自动计算单价和合计
	    	body.find("[field='EachAmount']").find(input).off("keyup").on("keyup",function(){
	    		var TotalPrice = 0;
	    		var EachAmount =$(this).val()-0; //金额
	    		body.find("[field='EachAmount']").each(function(){
	    			if($(this).find("div").text()!==""){
	    				TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
	    			}
	    		});
	    		TotalPrice = TotalPrice + EachAmount;
	    		footer.find("[field='EachAmount']").find("div").text((TotalPrice).toFixed(2));
	    	});
	    },500);
	}
	
	//结束编辑	
    function endEditing() {    	
    	var edField = "";
    	if(!itemType){
    		edField = "InOutItemId";
    		edName = "InOutItemName";
    	}
    	else {
    		edField = "AccountId";
    		edName = "AccountName";
    	}
        if (editIndex == undefined) { return true }
        if ($('#accountData').datagrid('validateRow', editIndex)) {
        	var ed = $('#accountData').datagrid('getEditor', {index: editIndex, field: edField});
            var textName = $(ed.target).combobox('getText');
            $('#accountData').datagrid('getRows')[editIndex][edName] = textName;
            $('#accountData').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    //单击
    function onClickRow(index) {
        if (editIndex != index) {
            if (endEditing()) {
                $('#accountData').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                editIndex = index;
                autoReckon();
            } else {
                $('#accountData').datagrid('selectRow', editIndex);
            }
        }
    }
	//新增
	function append()  {
        if (endEditing()) {
            $('#accountData').datagrid('appendRow', {});
            editIndex = $('#accountData').datagrid('getRows').length - 1;
            $('#accountData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
            autoReckon();
        }
	}
	//删除
    function removeit() {
        if (editIndex == undefined) { return }
        $('#accountData').datagrid('cancelEdit', editIndex)
                .datagrid('deleteRow', editIndex);
        editIndex = undefined;
    }
    //撤销
    function reject() {
        $('#accountData').datagrid('rejectChanges');
        editIndex = undefined;
    }
    //判断
    function CheckData() {
        var row = $('#accountData').datagrid('getRows');
        var totalRowNum = "";
        for (var i = 0; i < row.length; i++) {
        	if(!itemType){
	            if (row[i].InOutItemId == "") {
	                totalRowNum += (i + 1) + "、";
	            }
        	}
        	else{
        		if (row[i].AccountId == "") {
	                totalRowNum += (i + 1) + "、";
	            }
        	}
        }
        if (totalRowNum != "") {
            var totalRowNum = totalRowNum.substring(0, totalRowNum.length - 1);
            $.messager.alert('提示',"第" + totalRowNum + "行数据填写不完整！",'info');	
            return false;
        }
        return true;
    }
    //保存
    function accept(accepId) {
        append();
        removeit();
        if ($("#accountData").datagrid('getChanges').length) {
            if (!CheckData())
                return false;
            var inserted = $("#accountData").datagrid('getChanges', "inserted");
            var deleted = $("#accountData").datagrid('getChanges', "deleted");
            var updated = $("#accountData").datagrid('getChanges', "updated");
            $.ajax({
                type: "post",
                url: path + "/accountItem/saveDetials.action",
                data: {
                    Inserted: JSON.stringify(inserted),
                    Deleted: JSON.stringify(deleted),
                    Updated: JSON.stringify(updated),
                    HeaderId: accepId,
                    clientIp: clientIp
                },
                success: function (tipInfo) 
                { 
                    if (tipInfo) {
                        $.messager.alert('提示','保存成功！','info');	
                    }
                    else
                    	$.messager.alert('提示','保存失败！','error');	

                },
                error: function (XmlHttpRequest, textStatus, errorThrown) 
                {
                    $.messager.alert('提示',XmlHttpRequest.responseText,'error');	
                }
            });
        }
        if (endEditing()) {
            $('#accountData').datagrid('acceptChanges');
        }
    }
    //获取MaxId
    function getMaxId(){
	    var accountHeadMax=null;
    	$.ajax({
    		type:"post",
    		url: path + "/accountHead/getMaxId.action",
    		//设置为同步
    		async:false,
    		dataType: "json",
    		success: function (systemInfo)
    		{
    			if(systemInfo)
    			{
    				accountHeadMax = systemInfo.showModel.map.accountHeadMax;
    				var msgTip = systemInfo.showModel.msgTip;
    				if(msgTip == "exceptoin")
    				{
    					$.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
    					return;
    				}
    			}
    			else
    			{
    				accountHeadMax=null;
    			}
    		}
    	});
    	
    	if(accountHeadMax !=null)
    	{
    		if(accountHeadMax.length>0)
    		{
    			accountHeadMaxId=accountHeadMax[0];
    		}
    	}
    }