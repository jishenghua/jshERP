	//初始化界面
	$(function(){
		var depotList = null;
		var depotID = null;
		var supplierList = null;
		var supplierID = null;
		var personList = null;
		var personID = null;
		var ProjectSearch=null;
		var userBusinessList=null;
		var userdepot=null;
		var depotHeadMaxId=null; //获取最大的Id
		var accepId=null; //保存的主表id
		var url;
		var depotHeadID = 0;	
		var orgDepotHead = "";
		var editIndex = undefined;
		var listTitle = ""; //单据标题
		var listType = ""; //入库 出库
		var listSubType = ""; //采购 销售等
		var payTypeTitle = "";//付款 收款
		var organUrl = ""; //组织数据接口地址
		var amountNum = ""; //单据编号开头字符
		var depotString = ""; //店铺列表
		var orgDefaultId = 0; //单位默认编号
		//初始化系统基础信息
		getType();
		initSystemData_UB();
		initSelectInfo_UB();
		initSystemData_depot();
		initSelectInfo_depot();
		initSystemData_person();
		initSelectInfo_person();
		initSystemData_account();
		initSelectInfo_account();
		initSupplier(); //供应商
		initTableData();
		ininPager();
		initForm();	
		bindEvent();//绑定操作事件
	});
	//根据单据名称获取类型
	function getType(){
		listTitle = $("#tablePanel").prev().text();
		depotString = "|";
		var supUrl = path + "/supplier/findBySelect_sup.action"; //供应商接口
		var cusUrl = path + "/supplier/findBySelect_cus.action"; //客户接口
		var retailUrl = path + "/supplier/findBySelect_retail.action"; //散户接口
		if(listTitle === "采购入库列表"){
			listType = "入库"; 
			listSubType = "采购"; 
			payTypeTitle = "付款";
			organUrl = supUrl;
			amountNum = "CGRK";
		}
		else if(listTitle === "零售退货列表"){
			listType = "入库";
			listSubType = "零售退货";
			payTypeTitle = "付款";
			organUrl = retailUrl;
			amountNum = "LSTH";
		}
		else if(listTitle === "销售退货列表"){
			listType = "入库"; 
			listSubType = "销售退货"; 
			payTypeTitle = "付款";
			organUrl = cusUrl;
			amountNum = "XSTH";
		}
		else if(listTitle === "其它入库列表"){
			listType = "入库"; 
			listSubType = "其它"; 
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "QTRK";
		}
		else if(listTitle === "零售出库列表"){
			listType = "出库";
			listSubType = "零售";
			payTypeTitle = "收款";
			organUrl = retailUrl;
			amountNum = "LSCK";
		}
		else if(listTitle === "销售出库列表"){
			listType = "出库"; 
			listSubType = "销售"; 
			payTypeTitle = "收款";
			organUrl = cusUrl;
			amountNum = "XSCK";
		}
		else if(listTitle === "采购退货列表"){
			listType = "出库"; 
			listSubType = "采购退货";
			payTypeTitle = "收款";
			organUrl = supUrl;
			amountNum = "CGTH";
		}
		else if(listTitle === "其它出库列表"){
			listType = "出库"; 
			listSubType = "其它"; 
			payTypeTitle = "隐藏";
			organUrl = cusUrl;
			amountNum = "QTCK";
		}
		else if(listTitle === "调拨出库列表"){
			listType = "出库"; 
			listSubType = "调拨"; 
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "DBCK";
		}
	}
	//初始化系统基础信息
	function initSystemData_UB(){
		$.ajax({
			type:"post",
			url: path + "/userBusiness/getBasicData.action",
			data: ({
				KeyId:kid,
				Type:"UserDepot"
			}),
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				if(systemInfo)
				{
					userBusinessList = systemInfo.showModel.map.userBusinessList;
					var msgTip = systemInfo.showModel.msgTip;
					if(msgTip == "exceptoin")
					{
						$.messager.alert('提示','查找UserBusiness异常,请与管理员联系！','error');
						return;
					}
				}
				else
				{
					userBusinessList=null;
				}
			}
		});		
		
	}
	//初始化页面选项卡
	function initSelectInfo_UB(){
		
		if(userBusinessList !=null)
		{
			if(userBusinessList.length>0)
			{
				//用户对应的仓库列表 [1][2][3]...
				userdepot =userBusinessList[0].value;
			}
		}
	}
	
	
	//初始化系统基础信息
	function initSystemData_depot(){
		$.ajax({
			type:"post",
			url: path + "/depot/getBasicData.action",
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				depotList = systemInfo.showModel.map.depotList;
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
	function initSelectInfo_depot(){
		var options = "";
		
		if(depotList !=null)
		{
			options = "";
			for(var i = 0 ;i < depotList.length;i++)
			{
				var depot = depotList[i];
				
				if(userdepot!=null)
				{
					if(userdepot.indexOf("["+depot.id+"]")!=-1)
					{
						options += '<option value="' + depot.id + '">' + depot.name + '</option>';
						depotString = depotString + depot.id + ",";
					}
				}
			}
			depotString = depotString.substring(1, depotString.length-1);
			$("#ProjectId").empty().append(options);
			$("#AllocationProjectId").empty().append(options);			
			$("#searchProjectId").empty().append('<option value="">全部</option>').append(options);
		}
	}
	
	//初始化系统基础信息
	function initSupplier(){
		$('#OrganId').combobox({    
			url: organUrl,
		    valueField:'id',    
		    textField:'supplier',
			onLoadSuccess: function(res) {
				var data = $(this).combobox('getData');
				for(var i = 0; i<= data.length; i++){
					if(data[i].supplier === "非会员"){
						orgDefaultId = data[i].id;
					}
				}
			}
		});  
	}
	
	//初始化系统基础信息
	function initSystemData_person(){
		$.ajax({
			type:"post",
			url: path + "/person/getBasicData.action",
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
	//初始化页面选项卡
	function initSelectInfo_person(){
		var options1 = "";
		
		if(personList !=null)
		{
			for(var i = 0 ;i < personList.length;i++)
			{
				var person = personList[i];
				if(0 == i)
				{
					personID = person.id;
				}
				if(person.type=="仓管员")
				{
					options1 += '<option value="' + person.id + '">' + person.name + '</option>';
				}	
			}
			$("#HandsPersonId").empty().append(options1);
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
			for(var i = 0 ;i < accountList.length;i++)
			{
				var account = accountList[i];
				options += '<option value="' + account.id + '" data-currentAmount="' + account.currentAmount + '">' + account.name + '</option>';
			}	
			$("#AccountId").empty().append(options);
		}
	}
	
	//防止表单提交重复
	function initForm(){
		$('#depotHeadFM').form({
		    onSubmit: function(){
		        return false;
		    }
		});
	}
	
	//初始化表格数据
	function initTableData(){
		var hideType = undefined;
		if(payTypeTitle === "隐藏"){
			hideType = true; //隐藏当前列
		}
		$('#tableData').datagrid({
			//iconCls:'icon-save',
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
			//url: path + '/depotHead/findBy.action?pageSize=' + initPageSize,
			pagination: true,
			//交替出现背景
			striped : true,
			//loadFilter: pagerFilter,
			pageSize: 5,
			pageList: initPageNum,
			columns:[[
			  { field: 'Id',width:35,align:"center",checkbox:true},
	          { title: '单据编号',field: 'Number',width:100},
	          { title: '单据日期 ',field: 'OperTime',width:100},
	          { title: '创建时间',field: 'CreateTime',width:100},
	          { title: '操作员',field: 'OperPersonName',width:80},			          
	          { title: '合计',field: 'TotalPrice',width:50},
	          { title: payTypeTitle,field: 'ChangeAmount',width:50,hidden:hideType},
	          { title: '单据备注',field: 'Remark',width:100},
	          { title: '操作',field: 'op',align:"center",width:180,formatter:function(value,rec)
	         	{
					var str = '';
					var rowInfo = rec.Id + 'AaBb' + rec.ProjectId+ 'AaBb' + rec.Number+ 'AaBb' + rec.OperPersonName
					+ 'AaBb' + rec.OperTime+ 'AaBb' + rec.OrganId+ 'AaBb' + rec.HandsPersonId
					+ 'AaBb' + rec.AccountId+ 'AaBb' + rec.ChangeAmount+ 'AaBb' + rec.Remark
					+ 'AaBb' + rec.ProjectName+ 'AaBb' + rec.OrganName+ 'AaBb' + rec.HandsPersonName
					+ 'AaBb' + rec.AccountName + 'AaBb' + rec.TotalPrice + 'AaBb' + rec.AllocationProjectId + 'AaBb' + rec.AllocationProjectName;
					if(1 == value)
					{
						str += '<a onclick="showDepotHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)"><span class="action-show">查看</span></a>';
						str += '<a onclick="editDepotHead(\'' + rowInfo + '\');" style="text-decoration:none;color:black;" href="javascript:void(0)"><span class="action-edit">编辑</span></a>';
						str += '<a onclick="deleteDepotHead('+ rec.Id +');" style="text-decoration:none;color:black;" href="javascript:void(0)"><span class="action-delete">删除</span></a>';
					}
					return str;
				}
	          }
			]],
			toolbar:[
				{
					id:'addDepotHead',
					text:'增加',
					iconCls:'icon-add',
					handler:function()
					{
						addDepotHead();
					}
				},
				{
					id:'deleteDepotHead',
					text:'删除',
					iconCls:'icon-remove',
					handler:function()
					{
						batDeleteDepotHead();	
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

	//初始化表格数据-商品列表-编辑状态
	function initTableData_material(type,TotalPrice){
		$('#materialData').datagrid({
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
	          	{ title: '商品名称',field: 'MaterialId',width:230,
				  	formatter:function(value,row,index){
						return row.MaterialName;
	              	},
				  	editor:{
	                  	type:'combobox',
	                  	options:{
	                      	valueField:'Id',
	                      	textField:'MaterialName',
	                      	method:'get',
	                      	url: path + "/material/findBySelect.action",
							onSelect:function(rec){
								if(rec) {
									$.ajax({
										url: path + "/material/findById.action",
										type: "get",
										dataType: "json",
										data: {
											"MaterialID": rec.Id
										},
										success: function (res) {
											if(res && res.rows && res.rows[0]) {
												var retailPrice = res.rows[0].RetailPrice; //零售价格
												var presetPriceOne = res.rows[0].PresetPriceOne; //价格1
												var presetPriceTwo = res.rows[0].PresetPriceTwo; //价格2
												var TotalPrice = 0;
												var allPrice = 0;
												var body =$("#depotHeadFM .datagrid-body");
												var footer =$("#depotHeadFM .datagrid-footer");
												var input = ".datagrid-editable-input";
												body.find("[field='OperNumber']").find(input).val(1);
												if(listSubType == "零售" || listSubType == "零售退货") {
													body.find("[field='UnitPrice']").find(input).val(retailPrice);
													body.find("[field='AllPrice']").find(input).val(retailPrice);
													allPrice = retailPrice;
												}
												else if(listTitle == "销售出库列表" || listTitle == "采购退货列表" || listTitle == "其它出库列表" || listTitle == "调拨出库列表") {
													body.find("[field='UnitPrice']").find(input).val(presetPriceOne);
													body.find("[field='AllPrice']").find(input).val(presetPriceOne);
													allPrice = presetPriceOne;
												}
												body.find("[field='AllPrice']").each(function(){
													if($(this).find("div").text()!==""){
														TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
													}
												});
												TotalPrice = TotalPrice + allPrice;
												footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2));
												if(listSubType == "零售" || listSubType == "零售退货") {
													$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
													$("#backAmount").val(0);
												}
											}
										},
										error: function() {
											$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
										}
									});
								}
							}
	                  	}
		            }
			    },
	            { title: '数量',field: 'OperNumber',editor:'validatebox',width:50},
	            { title: '单价',field: 'UnitPrice',editor:'validatebox',width:50},
	            { title: '金额',field: 'AllPrice',editor:'validatebox',width:50},
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
			url: path + '/depotItem/findBy.action?HeaderId=' + depotHeadID,
			dataType: "json",
			success: function (res) {
				var AllPrice = 0
				if(type === "edit") {
					AllPrice = TotalPrice;							
				}
				var array = [];
				array.push({
					"AllPrice": AllPrice
				});
				res.footer = array;
				$("#materialData").datagrid('loadData',res);
			
			},
			error:function() {
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
			}
		});
	}			
	
	//初始化表格数据-商品列表-查看状态
	function initTableData_material_show(TotalPrice){
		$('#materialDataShow').datagrid({
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
	          { title: '商品名称',field: 'MaterialName',width:230},
	          { title: '数量',field: 'OperNumber',width:50},
	          { title: '单价',field: 'UnitPrice',width:50},
	          { title: '金额',field: 'AllPrice',width:50},
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
			url: path + '/depotItem/findBy.action?HeaderId=' + depotHeadID,
			dataType: "json",
			success: function (res) {
				var AllPrice = TotalPrice;			
				var array = [];
				array.push({
					"AllPrice": AllPrice
				});
				res.footer = array;
				$("#materialDataShow").datagrid('loadData',res);
			
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
					showDepotHeadDetails(pageNum,pageSize);
				}  
			}); 
		}
		catch (e) 
		{
			$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
		}
	}
	
	//删除采购入库信息
	function deleteDepotHead(depotHeadID){
		$.messager.confirm('删除确认','确定要删除此采购入库信息吗？',function(r)
	 	{
	        if (r)
	        {
				$.ajax({
					type:"post",
					url: path + "/depotHead/delete.action",
					dataType: "json",
					data: ({
						depotHeadID : depotHeadID,
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
							$.messager.alert('删除提示','删除采购入库信息失败，请稍后再试！','error');
					},
					//此处添加错误处理
		    		error:function()
		    		{
		    			$.messager.alert('删除提示','删除采购入库信息异常，请稍后再试！','error');
						return;
					}
				});			
	        }
	    });
	}

	//批量删除采购入库
	function batDeleteDepotHead(){
		var row = $('#tableData').datagrid('getChecked');	
		if(row.length == 0)
		{
			$.messager.alert('删除提示','没有记录被选中！','info');				
			return;	
		}
		if(row.length > 0)
		{
			$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条采购入库信息吗？',function(r)
		 	{
	            if (r)
	            {
	            	var ids = "";
	                for(var i = 0;i < row.length; i++)
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
						url: path + "/depotHead/batchDelete.action",
						dataType: "json",
						async :  false,
						data: ({
							depotHeadIDs : ids,
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
								$.messager.alert('删除提示','删除采购入库信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('删除提示','删除采购入库信息异常，请稍后再试！','error');
							return;
						}
					});	
	            }
	        });
		 }
	}
	//新增信息
	function addDepotHead(){
		$("#clientIp").val(clientIp);
		$('#depotHeadFM').form('clear');
		var thisDate = getNowFormatDate(); //当前日期
		$("#OperTime").val(thisDate);
		var thisNumber = getNowFormatDateNum(); //根据时间生成编号
		$("#Number").val(amountNum + thisNumber).focus();
		var addTitle = listTitle.replace("列表","信息");
		$('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加' + addTitle);
		$(".window-mask").css({ width: webW ,height: webH});
	    
	    orgDepotHead = "";
	    depotHeadID = 0;
	    initTableData_material("add"); //商品列表
	    reject(); //撤销下、刷新商品列表
	    url = path + '/depotHead/create.action';

		//零售单据修改收款时，自动计算找零
		if(listSubType == "零售" || listSubType == "零售退货") {
			$("#OrganId").combobox("setValue", orgDefaultId);
			var getAmount = $("#depotHeadFM .get-amount");
			var changeAmount = $("#depotHeadFM .change-amount");
			var backAmount = $("#depotHeadFM .back-amount");
			getAmount.val(0); changeAmount.val(0); backAmount.val(0); //时间初始化
			getAmount.off("keyup").on("keyup",function() {
				if(changeAmount.val()){
					backAmount.val((getAmount.val()-changeAmount.val()).toFixed(2));
				}
			});
		}
	}	

	//编辑信息
	function editDepotHead(depotHeadTotalInfo){
		var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
	    $("#clientIp").val(clientIp);
	    $("#ProjectId").focus().val(depotHeadInfo[1]);
	    var ProjectId=depotHeadInfo[1];
		if(ProjectId!='')
		{
			initSystemData_person(ProjectId);
			initSelectInfo_person();
		}
	    $("#Number").val(depotHeadInfo[2]);
	    $("#OperTime").val(depotHeadInfo[4]);
	    $('#OrganId').combobox('setValue', depotHeadInfo[5]);
	    $("#HandsPersonId").val(depotHeadInfo[6]);
	    $("#AccountId").val(depotHeadInfo[7]);
	    $("#ChangeAmount").val(depotHeadInfo[8]);
	    $("#ChangeAmount").attr("data-changeamount", depotHeadInfo[8]);
	    $("#Remark").val(depotHeadInfo[9]);
	    var TotalPrice = depotHeadInfo[14];
	    $("#AllocationProjectId").val(depotHeadInfo[15]);
	    //orgDepotHead = depotHeadInfo[1];
	    var editTitle = listTitle.replace("列表","信息");
	    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑' + editTitle);
	    $(".window-mask").css({ width: webW ,height: webH});
	    depotHeadID = depotHeadInfo[0];
	    
	    initTableData_material("edit",TotalPrice); //商品列表
	    reject(); //撤销下、刷新商品列表                
	    url = path + '/depotHead/update.action?depotHeadID=' + depotHeadInfo[0];
	}
	
	//查看信息
	function showDepotHead(depotHeadTotalInfo){
		var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
	    $("#ProjectIdShow").text(depotHeadInfo[10]);
	    $("#NumberShow").text(depotHeadInfo[2]);
	    $("#OperTimeShow").text(depotHeadInfo[4]);
	    $('#OrganIdShow').text(depotHeadInfo[11]);
	    $("#HandsPersonIdShow").text(depotHeadInfo[12]);
	    $("#AccountIdShow").text(depotHeadInfo[13]);
	    $("#ChangeAmountShow").text(depotHeadInfo[8]);
	    $("#RemarkShow").text(depotHeadInfo[9]);
	    var TotalPrice = depotHeadInfo[14];
	    $("#AllocationProjectIdShow").text(depotHeadInfo[16]);
	    var showTitle = listTitle.replace("列表","信息");
	    $('#depotHeadDlgShow').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看' + showTitle);
	    $(".window-mask").css({ width: webW ,height: webH});
	    
	    depotHeadID = depotHeadInfo[0];
	    initTableData_material_show(TotalPrice); //商品列表-查看状态

		//零售单据修改收款时，自动计算找零
		if(listSubType == "零售"){
			$("#depotHeadDlgShow .get-amount-show").text($("#depotHeadDlgShow .change-amount-show").text());
			$("#depotHeadDlgShow .back-amount-show").text(0);
		}
	}
	
	//绑定操作事件
	function bindEvent(){
		showDepotHeadDetails(1,initPageSize); //初始化时自动查询
		//搜索处理
		$("#searchBtn").off("click").on("click",function(){
			showDepotHeadDetails(1,initPageSize);
			var opts = $("#tableData").datagrid('options');
			var pager = $("#tableData").datagrid('getPager');
			opts.pageNumber = 1;
			opts.pageSize = initPageSize;
			pager.pagination('refresh',
			{
				pageNumber:1,
				pageSize:initPageSize
			});
		});
		
		//重置按钮
		$("#searchResetBtn").unbind().bind({
			click:function(){
				//$("#searchProjectId").val("");
				$("#searchState").val("");
				$("#searchBeginTime").val("");
				$("#searchEndTime").val("");
				//加载完以后重新初始化
				$("#searchBtn").click();
		    }	
		});
		
		//保存信息
		$("#saveDepotHead").unbind().bind({
			click:function()
			{
				if(!$('#depotHeadFM').form('validate'))
					return;
				else 
				{
					var OrganId = null, AllocationProjectId = null;
					var ChangeAmount = $.trim($("#ChangeAmount").val());
					var TotalPrice = $("#depotHeadFM .datagrid-footer [field='AllPrice'] div").text();					
					if(listSubType !=="调拨"){
						OrganId = $('#OrganId').combobox('getValue');
					}
					else {
						AllocationProjectId = $.trim($("#AllocationProjectId").val()); //收货仓库-对方
					}
					if(listSubType === "采购"||listSubType === "零售退货"||listSubType === "销售退货"){
						//付款为负数
						ChangeAmount = 0 - ChangeAmount;
						TotalPrice = 0 - TotalPrice;
					}
					$.ajax({
						type:"post",
						url: url,
						dataType: "json",
						async :  false,
						data: ({
							Type: listType,
							SubType: listSubType,
							ProjectId: $.trim($("#ProjectId").val()),
							AllocationProjectId: AllocationProjectId,
							Number: $.trim($("#Number").val()),
							OperTime: $("#OperTime").val(),
							OrganId: OrganId,
							HandsPersonId: $.trim($("#HandsPersonId").val()),
							AccountId: $.trim($("#AccountId").val()),
							ChangeAmount: ChangeAmount, //付款/收款
							TotalPrice: TotalPrice, //合计
							Remark: $.trim($("#Remark").val()),
							clientIp: clientIp
						}),
						success: function (tipInfo)
						{
							if(tipInfo)
							{
								function closeDialog(){
									$('#depotHeadDlg').dialog('close');
									var opts = $("#tableData").datagrid('options'); 
									showDepotHeadDetails(opts.pageNumber,opts.pageSize); 
								}
								//保存明细记录
								if(depotHeadID ==0)
								{
									getMaxId(); //查找最大的Id
									accept(depotHeadMaxId); //新增
									closeDialog();
								}
								else
								{
									accept(depotHeadID); //修改
									closeDialog();
								}															
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
		$(document).keydown(function(event){  
		   	//兼容 IE和firefox 事件 
		    var e = window.event || event;  
		    var k = e.keyCode||e.which||e.charCode;  
		    //兼容 IE,firefox 兼容  
		    var obj = e.srcElement ? e.srcElement : e.target;  
		    //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
		    if(k == "13"&&(obj.id=="State"||obj.id=="Number"))
		    {  
		        $("#saveDepotHead").click();
		    }
		    //搜索按钮添加快捷键
		    if(k == "13"&&(obj.id=="searchState"||obj.id=="searchNumber"))
		    {  
		        $("#searchBtn").click();
		    }  
		}); 
	}
	

	function showDepotHeadDetails(pageNo,pageSize){
		$.ajax({
			type:"post",
			url: path + "/depotHead/findBy.action",
			dataType: "json",
			data: ({
				ProjectId:$.trim($("#searchProjectId").val()),
				DepotIds: depotString,
				Type: listType,
				SubType:listSubType,
				State:$.trim($("#searchState").val()),
				Number:$.trim($("#searchNumber").val()),
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
	    	var body =$("#depotHeadFM .datagrid-body");
	    	var footer =$("#depotHeadFM .datagrid-footer");
	    	var input = ".datagrid-editable-input";
	    	//点击商品下拉框，自动加载数量、单价、金额

	    	//修改单价，自动计算金额和合计
	    	body.find("[field='UnitPrice']").find(input).off("keyup").on("keyup",function(){
	    		var UnitPrice =$(this).val()-0; //单价
	    		var TotalPrice = 0;
	    		var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量                		
	    		body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2));
	    		body.find("[field='AllPrice']").each(function(){
	    			if($(this).find("div").text()!==""){
	    				TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
	    			}
	    		});
	    		TotalPrice = TotalPrice + UnitPrice*OperNumber;
	    		footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2));
				if(listSubType == "零售" || listSubType == "零售退货") {
					$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
					$("#backAmount").val(0);
				}
	    	});
			//点击单价，自动提示参考价格列表
			body.find("[field='UnitPrice']").find(input).off("click").on("click",function(){
				var self = this;
				var mValue = body.find("[field='MaterialId'] .combo-value").attr("value"); //获取选中的商品id
				if(!mValue) {
					return;
				}
				else {
					if(listTitle!="销售出库列表" && listTitle!="采购退货列表" && listTitle!="其它出库列表" && listTitle!="调拨出库列表") {
						return;
					}
					$.ajax({
						url: path + "/material/findById.action",
						type: "get",
						dataType: "json",
						data: {
							"MaterialID": mValue - 0
						},
						success: function(res){
							if(res && res.rows && res.rows[0]) {
								var retailPrice = res.rows[0].RetailPrice;
								var presetPriceOne = res.rows[0].PresetPriceOne;
								var presetPriceTwo = res.rows[0].PresetPriceTwo;
								//定义模版
								var temp = "<div class='price-list'>";
								temp +="<ul>";
								temp +="<li>预设售价1：" + presetPriceOne + "</li>";
								temp +="<li>预设售价2：" + presetPriceTwo + "</li>";
								temp +="<li>零售价：" + retailPrice + "</li>";
								temp +="</ul>";
								temp +="</div>";
								if($('.price-list').length){
									$('.price-list').remove(); //如果存在价格列表先移除
								}
								else {
									$(self).after(temp); //加载列表信息
								}
								$('.price-list ul li').off("click").on("click",function(){
									var price = $(this).text();
									price = price.substring(price.indexOf("：") + 1);
									$(self).val(price);
									$(self).keyup(); //模拟键盘操作
									$('.price-list').remove(); //移除价格列表
								});
								//点击空白处移除价格列表
								$(".datagrid-body").off("click").on("click",function(){
									$('.price-list').remove(); //移除价格列表
								});
							}
						},
						error: function(){
							$.messager.alert('错误提示','查询商品信息异常，请稍后再试！','error');
							return;
						}
					});
				}
			});
	    	//修改数量，自动计算金额和合计
	    	body.find("[field='OperNumber']").find(input).off("keyup").on("keyup",function(){
	    		var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
	    		var TotalPrice = 0;
	    		var OperNumber =$(this).val()-0; //数量
	    		body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2));
	    		body.find("[field='AllPrice']").each(function(){
	    			if($(this).find("div").text()!==""){
	    				TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
	    			}
	    		});
	    		TotalPrice = TotalPrice + UnitPrice*OperNumber;
	    		footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2));
				if(listSubType == "零售" || listSubType == "零售退货") {
					$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
					$("#backAmount").val(0);
				}
	    	});
	    	//修改金额，自动计算单价和合计
	    	body.find("[field='AllPrice']").find(input).off("keyup").on("keyup",function(){
	    		var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
	    		var TotalPrice = 0;
	    		var AllPrice =$(this).val()-0; //金额
	    		body.find("[field='UnitPrice']").find(input).val((AllPrice/OperNumber).toFixed(2));
	    		body.find("[field='AllPrice']").each(function(){
	    			if($(this).find("div").text()!==""){
	    				TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
	    			}
	    		});
	    		TotalPrice = TotalPrice + AllPrice;
	    		footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2));
				if(listSubType == "零售" || listSubType == "零售退货") {
					$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
					$("#backAmount").val(0);
				}
	    	});
	    },500);
	}
	
	//结束编辑	
	function endEditing() {
	    if (editIndex == undefined) { return true }
	    if ($('#materialData').datagrid('validateRow', editIndex)) {
	    	var ed = $('#materialData').datagrid('getEditor', {index:editIndex,field:'MaterialId'});
	        var MaterialName = $(ed.target).combobox('getText');
	        $('#materialData').datagrid('getRows')[editIndex]['MaterialName'] = MaterialName;
	        $('#materialData').datagrid('endEdit', editIndex);
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
	            $('#materialData').datagrid('selectRow', index).datagrid('beginEdit', index);
	            editIndex = index;
	            autoReckon();
	        } else {
	            $('#materialData').datagrid('selectRow', editIndex);
	        }
	    }
	}
	//新增
	function append(){
	    if (endEditing()) {
	        $('#materialData').datagrid('appendRow', {});
	        editIndex = $('#materialData').datagrid('getRows').length - 1;
	        $('#materialData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	        autoReckon();
	    }
	}
	//删除
	function removeit(){
	    if (editIndex == undefined) { return }
	    $('#materialData').datagrid('cancelEdit', editIndex)
	            .datagrid('deleteRow', editIndex);
	    editIndex = undefined;
	}
	//撤销
	function reject() {
	    $('#materialData').datagrid('rejectChanges');
	    editIndex = undefined;
	}
	//判断
	function CheckData() {
	    var row = $('#materialData').datagrid('getRows');
	    var totalRowNum = "";
	    for (var i = 0; i < row.length; i++) {
	        if (row[i].MaterialId == "") {
	            totalRowNum += (i + 1) + "、";
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
	    if ($("#materialData").datagrid('getChanges').length) {
	        if (!CheckData())
	            return false;
	        var inserted = $("#materialData").datagrid('getChanges', "inserted");
	        var deleted = $("#materialData").datagrid('getChanges', "deleted");
	        var updated = $("#materialData").datagrid('getChanges', "updated");
	        $.ajax({
	            type: "post",
	            url: path + "/depotItem/saveDetials.action",
	            data: {
	                Inserted: JSON.stringify(inserted),
	                Deleted: JSON.stringify(deleted),
	                Updated: JSON.stringify(updated),
	                HeaderId:accepId,
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
	        $('#materialData').datagrid('acceptChanges');
	    }
	}
	//获取MaxId
	function getMaxId(){
	    var depotHeadMax=null;
		$.ajax({
			type:"post",
			url: path + "/depotHead/getMaxId.action",
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				if(systemInfo)
				{
					depotHeadMax = systemInfo.showModel.map.depotHeadMax;
					var msgTip = systemInfo.showModel.msgTip;
					if(msgTip == "exceptoin")
					{
						$.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
						return;
					}
				}
				else
				{
					depotHeadMax=null;
				}
			}
		});
		
		if(depotHeadMax !=null)
		{
			if(depotHeadMax.length>0)
			{
				depotHeadMaxId=depotHeadMax[0];
			}
		}
	}






