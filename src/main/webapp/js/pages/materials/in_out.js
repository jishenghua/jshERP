	//初始化界面
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
	var preTotalPrice = 0; //前一次加载的金额
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
	var orgDefaultList; //存储查询出来的会员列表
	var accountList; //账户列表
	var outItemList; //支出项目列表
	var thisTaxRate = 0; //当前税率，选择供应商或者客户的时候设置
	var oldNumber = ""; //编辑前的单据编号
	var oldId = 0; //编辑前的单据Id
	var otherColumns = true; //明细中的‘别名’列是否显示
	var btnEnableList = getBtnStr(); //获取按钮的权限
	var mPropertyList = ""; //商品属性列表
	$(function(){
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
		initSalesman(); //销售人员
		initGift(); //初始化礼品卡
		initOutItemList(); //初始化支出项目
		initMProperty(); //初始化商品属性
		initTableData();
		ininPager();
		initForm();	
		bindEvent();//绑定操作事件
	});
	//根据单据名称获取类型
	function getType(){
		listTitle = $("#tablePanel").prev().text();
		depotString = "|";
		//改变宽度和高度
		$("#searchPanel").panel({width:webW-2});
		$("#tablePanel").panel({width:webW-2});
		var supUrl = path + "/supplier/findBySelect_sup.action"; //供应商接口
		var cusUrl = path + "/supplier/findBySelect_cus.action?UBType=UserCustomer&UBKeyId=" + kid; //客户接口
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
			listSubType = "零售退货"; //注：用预付款购买的产品不能退货
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
		else if(listTitle === "礼品充值列表"){
			listType = "出库";
			listSubType = "礼品充值";
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "LPCZ";
		}
		else if(listTitle === "礼品销售列表"){
			listType = "出库";
			listSubType = "礼品销售";
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "LPXS";
		}
		else if(listTitle === "组装单列表"){
			listType = "其它";
			listSubType = "组装单";
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "ZZD";
		}
		else if(listTitle === "拆卸单列表"){
			listType = "其它";
			listSubType = "拆卸单";
			payTypeTitle = "隐藏";
			organUrl = supUrl;
			amountNum = "CSD";
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
	
	//初始化供应商、客户、散户信息
	function initSupplier(){
		$('#OrganId').combobox({
			url: organUrl,
		    valueField:'id',    
		    textField:'supplier',
			filter: function(q, row){
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >-1;
			},
			onLoadSuccess: function(res) {
				var data = $(this).combobox('getData');
				for(var i = 0; i<= data.length; i++){
					if(data && data[i] && data[i].supplier === "非会员"){
						orgDefaultId = data[i].id;
					}
				}
				if(listSubType === "零售"){
					orgDefaultList = res;
				}
			},
			onSelect: function(rec){
				if(listSubType === "零售"){
					var option = "";
					if(rec.supplier !== "非会员" && rec.advanceIn >0){
						option = '<option value="预付款">预付款(' + rec.advanceIn + ')</option>';
						option += '<option value="现付">现付</option>';
					}
					else {
						option += '<option value="现付">现付</option>';
					}
					$("#payType").empty().append(option);
				}
				else{
					$.ajax({
						type:"get",
						url: path + "/supplier/findById.action",
						data: {
							SupplierID: rec.id
						},
						dataType: "json",
						success: function (res){
							if(res && res.rows && res.rows[0]){
								thisTaxRate = res.rows[0].taxRate; //设置当前的税率
							}
						},
						error:function(){

						}
					});
				}
			}
		});  
	}

	//初始化销售人员
	function initSalesman(){
		$('#Salesman').combobox({
			url: path + "/person/getPersonByNumType.action?type=1",
			valueField:'id',
			textField:'name',
			multiple: true
		});
	}

	//初始化-礼品卡
	function initGift(){
		if(listSubType == "礼品充值"|| listSubType == "礼品销售"){
			$('#GiftId').combobox({
				url: path + "/depot/findGiftByType.action?type=1",
				valueField:'id',
				textField:'name'
			});
			$('#searchGiftId').combobox({
				url: path + "/depot/findGiftByType.action?type=1",
				valueField:'id',
				textField:'name'
			});
		}
		if(listSubType == "礼品销售"){
			$.ajax({
				type:"post",
				url: path + "/supplier/findBySelectRetailNoPeople.action", //散户接口
				dataType: "json",
				success: function (res){
					if(res && res[0]){
						orgDefaultId = res[0].id;
					}
				}
			});
		}
	}

	//初始化收入项目列表
	function initOutItemList(){
		$.ajax({
			type:"post",
			url: path + "/inOutItem/findBySelect.action?type=out",
			//设置为同步
			async:false,
			dataType: "json",
			success: function (res){
				if(res){
					outItemList = res;
				}
			},
			error:function(){

			}
		});
	}

	//初始化商品属性
	function initMProperty(){
		$.ajax({
			type: "post",
			url: path + "/materialProperty/findBy.action",
			dataType: "json",
			success: function (res) {
				if (res && res.rows) {
					var thisRows = res.rows;
					for(var i=0; i < thisRows.length; i++) {
						if(thisRows[i].enabled){
							mPropertyList += thisRows[i].nativeName +",";
						}
					}
					if(mPropertyList){
						mPropertyList = mPropertyList.substring(0,mPropertyList.length-1);
					}
				}
			},
			//此处添加错误处理
			error:function() {
				$.messager.alert('查询提示','查询信息异常，请稍后再试！','error');
				return;
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
		if(accountList !=null){
			options = "<option value=''>(空)</option>";
			options += "<option value='many' class='many' data-manyAmount=''>多账户</option>";
			for(var i = 0 ;i < accountList.length;i++) {
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
		var isHiddenStatus = true;
		if(payTypeTitle === "隐藏"){
			hideType = true; //隐藏当前列
		}
		var tableToolBar = [
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
		];
		//如果允许的按钮列表中存在就显示，3-代表审核|反审核的权限
		if(btnEnableList && btnEnableList.indexOf(3)>-1){
			isHiddenStatus = false;  //显示
			tableToolBar.push({
				id:'okDepotHead',
				text:'审核',
				iconCls:'icon-ok',
				handler:function() {
					setStatusFun();
				}
			},
			{
				id:'undoDepotHead',
				text:'反审核',
				iconCls:'icon-undo',
				handler:function() {
					setUnStatusFun();
				}
			});
		}
		else {
			isHiddenStatus = true; //隐藏
		}
		var isShowLastMoneyColumn = false; //是否显示优惠后金额和价税合计,true为隐藏,false为显示
		if(listSubType == "调拨" || listSubType == "其它" || listSubType == "零售" || listSubType == "零售退货" || listSubType == "礼品充值" || listSubType == "礼品销售" || listSubType == "组装单" || listSubType == "拆卸单"){
			isShowLastMoneyColumn = true; //隐藏
		}
		var isShowOrganNameColumn = false; //是否显示供应商、客户等信息,true为隐藏,false为显示
		var organNameTitle = ""; //组织名称标题
		if(listSubType == "调拨" || listSubType == "礼品充值" || listSubType == "礼品销售" || listSubType == "组装单" || listSubType == "拆卸单"){
			isShowOrganNameColumn = true; //隐藏
		}
		else {
			if(listTitle == "采购入库列表" || listTitle == "采购退货列表" || listTitle == "其它入库列表"){
				organNameTitle = "供应商名称";
			}
			else if(listTitle == "销售退货列表" || listTitle == "销售出库列表" || listTitle == "其它出库列表"){
				organNameTitle = "客户名称";
			}
			else if(listTitle == "零售出库列表" || listTitle == "零售退货列表"){
				organNameTitle = "会员卡号";
			}
		}
		$('#tableData').datagrid({
			height:heightInfo,
			rownumbers: false,
			//动画效果
			animate:false,
			//选中单行
			singleSelect : true,
			collapsible:false,
			selectOnCheck:false,
			pagination: true,
			//交替出现背景
			striped : true,
			pageSize: 10,
			pageList: initPageNum,
			columns:[[
				{ field: 'Id',width:35,align:"center",checkbox:true},
				{ title: '操作',field: 'op',align:"center",width:90,
					formatter:function(value,rec) {
						var str = '';
						var rowInfo = rec.Id + 'AaBb' + rec.ProjectId+ 'AaBb' + rec.Number+ 'AaBb' + rec.OperPersonName
							+ 'AaBb' + rec.OperTime+ 'AaBb' + rec.OrganId+ 'AaBb' + rec.HandsPersonId
							+ 'AaBb' + rec.AccountId+ 'AaBb' + rec.ChangeAmount+ 'AaBb' + rec.Remark
							+ 'AaBb' + rec.ProjectName+ 'AaBb' + rec.OrganName+ 'AaBb' + rec.HandsPersonName
							+ 'AaBb' + rec.AccountName + 'AaBb' + rec.TotalPrice + 'AaBb' + rec.AllocationProjectId
							+ 'AaBb' + rec.AllocationProjectName + 'AaBb' + rec.payType + 'AaBb' + rec.Salesman
							+ 'AaBb' + rec.Discount + 'AaBb' + rec.DiscountMoney + 'AaBb' + rec.DiscountLastMoney
							+ 'AaBb' + rec.AccountIdList + 'AaBb' + rec.AccountMoneyList
							+ 'AaBb' + rec.OtherMoney + 'AaBb' + rec.OtherMoneyList + 'AaBb' + rec.OtherMoneyItem + 'AaBb' + rec.AccountDay;
						if(1 == value) {
							var orgId = rec.OrganId? rec.OrganId:0;
							str += '<img title="查看" src="' + path + '/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showDepotHead(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
							str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editDepotHead(\'' + rowInfo + '\''+',' + rec.Status + ');"/>&nbsp;&nbsp;&nbsp;';
							str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteDepotHead('+ rec.Id +',' + orgId +',' + rec.TotalPrice+',' + rec.Status + ');"/>';
						}
						return str;
					}
				},
				{ title: organNameTitle, field: 'OrganName',width:120, hidden:isShowOrganNameColumn},
				{ title: '单据编号',field: 'Number',width:130},
				{ title: '商品信息',field: 'MaterialsList',width:180,formatter:function(value){
						return value.replace(",","，");
					}
				},
				{ title: '单据日期 ',field: 'OperTime',width:130},
				{ title: '操作员',field: 'OperPersonName',width:60},
				{ title: '金额合计',field: 'TotalPrice',width:60},
				{ title: '含税合计',field: 'TotalTaxLastMoney',hidden:isShowLastMoneyColumn,width:60,formatter:function(value,rec){
						return (rec.DiscountMoney + rec.DiscountLastMoney).toFixed(2);
					}
				},
				{ title: '优惠后金额',field: 'DiscountLastMoney',hidden:isShowLastMoneyColumn,width:80},
				{ title: payTypeTitle,field: 'ChangeAmount',width:50,hidden:hideType},
				{ title: '状态',field: 'Status',hidden:isHiddenStatus, width:70,align:"center",formatter:function(value){
						return value? "<span style='color:green;'>已审核</span>":"<span style='color:red;'>未审核</span>";
					}
				}
			]],
			toolbar:tableToolBar,
			onLoadError:function() {
				$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
				return;
			}    
		});
	}

	//查找库存的方法
	function findStockNumById(depotId, mId, monthTime, body, input, ratio, type){
		var thisRatio = 1; //比例
		$.ajax({
			url: path + "/material/findById.action",
			type: "get",
			dataType: "json",
			data: {
				"MaterialID": mId
			},
			success: function (rec) {
				if(rec && rec.rows && rec.rows[0]) {
					var loadRatio = 1; //在单位输入框上面加载比例字段
					if(rec.rows[0].Unit) { //如果存在计量单位信息
						loadRatio = 1;
					}
					else{
						var unitName = rec.rows[0].UnitName;
						if(unitName) {
							thisRatio = unitName.substring(unitName.indexOf(":")+1).replace(")","");
							unitName = unitName.substring(0, unitName.indexOf("("));
						}
						var unitArr = unitName.split(",");
						var basicUnit = unitArr[0]; //基础单位
						var otherUnit = unitArr[1]; //副单位
						var unitSetInput =""; //单位
						if(listSubType === "采购" || listSubType === "采购退货"){
							unitSetInput = rec.rows[0].FirstInUnit;
							if(basicUnit==unitSetInput){ //基础单位等于选择的单位
								loadRatio = 1;
							}
							else if(otherUnit==unitSetInput){ //副单位等于选择的单位
								loadRatio = thisRatio;
							}
						}
						else if(listSubType === "销售" || listSubType === "销售退货" || listTitle == "礼品充值" || listTitle == "礼品销售" || listSubType === "零售" || listSubType === "零售退货"){
							unitSetInput = rec.rows[0].FirstOutUnit;
							if(basicUnit==unitSetInput){ //基础单位等于选择的单位
								loadRatio = 1;
							}
							else if(otherUnit==unitSetInput){ //副单位等于选择的单位
								loadRatio = thisRatio;
							}
						}
					}
					//查询库存
					$.ajax({
						type: "get",
						url: path + '/depotItem/findStockNumById.action',
						data:{
							ProjectId: depotId,
							MaterialId: mId,
							MonthTime: monthTime
						},
						dataType: "json",
						success: function (res) {
							if(res && res.rows && res.rows[0]){
								var thisStock = res.rows[0].thisSum;
								if(type == "select"){ //选择下拉框的时候
									if(ratio!=undefined && ratio!=1){
										loadRatio = ratio;
									}
								}
								else if(type == "click"){ //点击库存的时候
									if(ratio!=undefined){
										loadRatio = ratio;
									}
								}
								thisStock = (thisStock/loadRatio).toFixed(2);
								body.find("[field='Stock']").find(input).val(thisStock).attr("data-stock",res.rows[0].thisSum); //加载库存数据
							}
							else{
								body.find("[field='Stock']").find(input).val(0).attr("data-stock",0); //加载库存数据
							}
							body.find("[field='Stock']").find(input).prop("readonly","readonly"); //设置库存数据为只读
						},
						error:function() {
							$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
						}
					});
				}
			},
			error: function () {
				$.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
			}
		});
	}
	//优惠率、合计的统计方法
	function statisticsFun(body,UnitPrice,OperNumber,footer,taxRate){
		var TotalPrice = 0;
		var taxLastMoneyTotal = 0;
		//金额的合计
		body.find("[field='AllPrice']").each(function(){
			if($(this).find("div").text()!==""){
				TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
			}
		});
		TotalPrice = TotalPrice + UnitPrice*OperNumber;
		footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2)); //金额的合计
		//价税合计的总计
		body.find("[field='TaxLastMoney']").each(function(){
			if($(this).find("div").text()!==""){
				taxLastMoneyTotal = taxLastMoneyTotal + (parseFloat($(this).find("div").text().toString())-0);
			}
		});
		taxLastMoneyTotal = taxLastMoneyTotal + (UnitPrice*OperNumber*(1+taxRate/100));
		footer.find("[field='TaxLastMoney']").find("div").text((taxLastMoneyTotal).toFixed(2)); //价税合计的页脚总计
		var discount = $("#Discount").val(); //优惠率
		var discountMoney = (taxLastMoneyTotal*discount/100).toFixed(2);
		$("#DiscountMoney").val(discountMoney);//优惠金额
		var discountLastMoney = (taxLastMoneyTotal*(1-discount/100)).toFixed(2)
		$("#DiscountLastMoney").val(discountLastMoney);//优惠后金额
		if($("#AccountId").val()!=="many"){
			$("#ChangeAmount").val(discountLastMoney); //本次付、收款
		}
		var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
		$("#Debt").val((discountLastMoney-changeAmountNum).toFixed(2)); //本次欠款

		if(listSubType == "零售" || listSubType == "零售退货") {
			$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
			$("#backAmount").val(0);
		}
	}
	//初始化表格数据-商品列表-编辑状态
	function initTableData_material(type,TotalPrice){
		var body,footer,input; //定义表格和文本框
		var ratio = 1; //比例-品名专用
		var ratioDepot = 1; //比例-仓库用
		var monthTime = getNowFormatMonth();
		var isShowAnotherDepot = true; //显示对方仓库,true为隐藏,false为显示
		var depotHeadName = ""; //仓库名称
		var depotUrl = ""; //仓库接口地址
		var depotTextField = ""; //仓库下拉名称
		var anotherDepotHeadName = ""; //对方仓库的列的标题
		var anotherDepotUrl = ""; //对方仓库接口地址
		var anotherDepotTextField = "";
		if(listSubType == "调拨"){
			isShowAnotherDepot = false; //调拨时候显示对方仓库
			anotherDepotHeadName = "调入仓库";
			anotherDepotUrl = path + '/depot/findDepotByUserId.action?UBType=UserDepot&UBKeyId='+kid;
			anotherDepotTextField = "depotName";
		}
		if(listSubType == "礼品充值"){
			isShowAnotherDepot = false; //礼品充值时候显示礼品卡
			anotherDepotHeadName = "礼品卡";
			anotherDepotUrl = path + "/depot/findGiftByType.action?type=1";
			anotherDepotTextField = "name";
		}
		if(listSubType == "礼品销售"){
			depotHeadName = "礼品卡";
			depotUrl = path + "/depot/findGiftByType.action?type=1";
			depotTextField = "name";
		}
		else {
			depotHeadName = "仓库名称";
			depotUrl = path + '/depot/findDepotByUserId.action?UBType=UserDepot&UBKeyId='+kid;
			depotTextField = "depotName";
		}
		var isShowTaxColumn = false; //是否显示税率相关的列,true为隐藏,false为显示
		if(listSubType == "调拨" || listSubType == "其它" || listSubType == "零售" || listSubType == "零售退货" || listSubType == "礼品充值" || listSubType == "礼品销售" || listSubType == "组装单" || listSubType == "拆卸单"){
			isShowTaxColumn = true; //隐藏
		}
		var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示
		if(listSubType == "组装单" || listSubType == "拆卸单"){
			isShowMaterialTypeColumn = false; //显示
		}
		$('#materialData').datagrid({
			height:245,
			rownumbers: false,
			//动画效果
			animate:false,
			//选中单行
			singleSelect : true,
			collapsible:false,
			selectOnCheck:false,
			//单击行是否选中
			checkOnSelect : false,
			pagination: false,
			//交替出现背景
			striped : true,
			showFooter: true,
			//loadFilter: pagerFilter,
			onClickRow: onClickRow,
			columns:[[
			  	{ field: 'Id',width:35,align:"center",checkbox:true},
				{ title: '商品类型',field: 'MType',editor:'validatebox',hidden:isShowMaterialTypeColumn,width:80},
				{ title: depotHeadName, field: 'DepotId', editor: 'validatebox', width: 90,
					formatter: function (value, row, index) {
						return row.DepotName;
					},
					editor: {
						type: 'combobox',
						options: {
							valueField: 'id',
							textField: depotTextField,
							method: 'get',
							url: depotUrl,
							onSelect:function(rec){
								var depotId = rec.id;
								body =$("#depotHeadFM .datagrid-body");
								footer =$("#depotHeadFM .datagrid-footer");
								input = ".datagrid-editable-input";
								var mId = body.find("[field='MaterialId']").find(".combo-value").val();
								if(mId){
									var type = "select"; //type 类型：点击 click，选择 select
									findStockNumById(depotId, mId, monthTime, body, input, ratioDepot, type);
								}
							}
						}
					}
				},
	          	{ title: '品名(型号)(扩展信息)(单位)',field: 'MaterialId',width:230,
				  	formatter:function(value,row,index){
						return row.MaterialName;
	              	},
				  	editor:{
	                  	type:'combobox',
	                  	options:{
	                      	valueField:'Id',
	                      	textField:'MaterialName',
	                      	method:'post',
	                      	url: path + "/material/findBySelect.action",
							panelWidth: 300, //下拉框的宽度
							//全面模糊匹配，过滤字段
							filter: function(q, row){
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) >-1;
							},
							onBeforeLoad: function(param){
								param.mpList = mPropertyList; //商品属性
							},
							onSelect:function(rec){
								if(rec) {
									var mId = rec.Id;
									$.ajax({
										url: path + "/material/findById.action",
										type: "get",
										dataType: "json",
										data: {
											"MaterialID": mId
										},
										success: function (res) {
											if(res && res.rows && res.rows[0]) {
												var retailPrice = res.rows[0].RetailPrice-0; //零售价格
												var presetPriceOne = res.rows[0].PresetPriceOne-0; //预计采购价
												var presetPriceTwo = res.rows[0].PresetPriceTwo-0; //批发价
												var firstInUnit = res.rows[0].FirstInUnit; //首选入库单位
												var firstOutUnit = res.rows[0].FirstOutUnit; //首选出库单位
												var basicPresetPriceOne = ""; //多单位-入库-基础价格
												var basicPresetPriceTwo = ""; //多单位-出库-基础价格
												var retailPriceOne = ""; //多单位-入库-零售价格
												var otherPresetPriceOne = ""; //多单位-入库-其他价格
												var otherPresetPriceTwo = ""; //多单位-出库-其他价格
												var retailPriceTwo = ""; //多单位-出库-零售价格
												var basicUnit = ""; //基础单位
												var otherUnit = ""; //其他单位
												if(!res.rows[0].Unit){
													basicPresetPriceOne = res.rows[0].PriceStrategy[0].basic.PresetPriceOne-0;
													basicPresetPriceTwo = res.rows[0].PriceStrategy[0].basic.PresetPriceTwo-0;
													retailPriceOne = res.rows[0].PriceStrategy[0].basic.RetailPrice-0;
													otherPresetPriceOne = res.rows[0].PriceStrategy[1].other.PresetPriceOne-0;
													otherPresetPriceTwo = res.rows[0].PriceStrategy[1].other.PresetPriceTwo-0;
													retailPriceTwo = res.rows[0].PriceStrategy[1].other.RetailPrice-0;
													basicUnit = res.rows[0].PriceStrategy[0].basic.Unit;
													otherUnit = res.rows[0].PriceStrategy[1].other.Unit;
												}
												body =$("#depotHeadFM .datagrid-body");
												footer =$("#depotHeadFM .datagrid-footer");
												input = ".datagrid-editable-input";
												if(res.rows[0].Unit){ //如果存在计量单位信息
													ratio = 1; //重置比例为1
													body.find("[field='Unit']").find(input).val(res.rows[0].Unit); //设置-计量单位信息
													body.find("[field='Unit']").find(input).prop("readonly","readonly"); //设置计量单位为只读
													body.find("[field='Unit']").find(input).off("click"); //移除点击事件
													body.find("[field='Unit']").find(input).attr("data-ratio",ratio); //修改比例缓存信息
												}
												else {
													var unitName = res.rows[0].UnitName;
													if(unitName) {
														ratio = unitName.substring(unitName.indexOf(":")+1).replace(")",""); //给比例赋值
														unitName = unitName.substring(0, unitName.indexOf("("));
													}
													var unitArr = unitName.split(",");
													var basicUnit = unitArr[0]; //基础单位
													var otherUnit = unitArr[1]; //副单位
													var unitSetInput =""; //单位
													body.find("[field='Unit']").find(input).prop("readonly","readonly"); //设置计量单位为只读
													var loadRatio = 1; //在单位输入框上面加载比例字段
													if(listSubType === "采购" || listSubType === "采购退货"){
														unitSetInput = res.rows[0].FirstInUnit; //给单位文本框赋值
														if(basicUnit==unitSetInput){ //基础单位等于选择的单位
															loadRatio = 1;
														}
														else if(otherUnit==unitSetInput){ //副单位等于选择的单位
															loadRatio = ratio;
														}
													}
													else if(listSubType === "销售" || listSubType === "销售退货" || listSubType === "礼品充值" || listSubType === "礼品销售" || listSubType === "零售" || listSubType === "零售退货"){
														unitSetInput = res.rows[0].FirstOutUnit; //给单位文本框赋值
														if(basicUnit==unitSetInput){ //基础单位等于选择的单位
															loadRatio = 1;
														}
														else if(otherUnit==unitSetInput){ //副单位等于选择的单位
															loadRatio = ratio;
														}
													}
													body.find("[field='Unit']").find(input).val(unitSetInput).attr("data-ratio", loadRatio); //设置-首选单位

													body.find("[field='Unit']").find(input).off("click").on("click",function(){
														if(basicUnit && otherUnit) {
															var self = this;
															//定义模版
															var temp = "<div class='unit-list'>";
															temp +="<ul>";
															temp +="<li data-type='basic' data-ratio='1'>" + basicUnit + "</li>";
															temp +="<li data-type='other' data-ratio='" + ratio + "'>" + otherUnit + "</li>";
															temp +="</ul>";
															temp +="</div>";
															if($('.unit-list').length){
																$('.unit-list').remove(); //如果存在计量单位列表先移除
															}
															else {
																$(self).after(temp); //加载列表信息
															}
															//计量单位列表的单击事件
															$('.unit-list ul li').off("click").on("click",function(){
																var unit = $(this).text();
																var thisRatio = $(this).attr("data-ratio"); //获取比例
																$(self).val(unit).attr("data-ratio", thisRatio);
																$(self).keyup(); //模拟键盘操作
																$('.unit-list').remove(); //移除计量单位列表
																var stock = body.find("[field='Stock']").find(input).attr("data-stock"); //从缓存中取值
																var type = $(this).attr("data-type");
																var UnitPrice = 0;
																if(type === "basic"){
																	if(listTitle == "采购入库列表" || listTitle == "销售退货列表" || listTitle == "其它入库列表") {
																		UnitPrice = basicPresetPriceOne;
																	}
																	else if(listTitle == "销售出库列表" || listTitle == "采购退货列表" || listTitle == "其它出库列表" || listTitle == "调拨出库列表" || listTitle == "礼品充值列表" || listTitle == "礼品销售列表") {
																		UnitPrice = basicPresetPriceTwo;
																	}
																	else if(listTitle == "零售出库列表" || listTitle == "零售退货列表"){
																		UnitPrice = retailPriceOne;
																	}
																	body.find("[field='Stock']").find(input).val(stock); //修改库存
																}
																else if(type === "other"){
																	if(listTitle == "采购入库列表" || listTitle == "销售退货列表" || listTitle == "其它入库列表") {
																		UnitPrice = otherPresetPriceOne;
																	}
																	else if(listTitle == "销售出库列表" || listTitle == "采购退货列表" || listTitle == "其它出库列表" || listTitle == "调拨出库列表" || listTitle == "礼品充值列表" || listTitle == "礼品销售列表") {
																		UnitPrice = otherPresetPriceTwo;
																	}
																	else if(listTitle == "零售出库列表" || listTitle == "零售退货列表"){
																		UnitPrice = retailPriceTwo;
																	}
																	body.find("[field='Stock']").find(input).val((stock/ratio).toFixed(2)); //修改库存
																}
																body.find("[field='UnitPrice']").find(input).val(UnitPrice); //单价
																var OperNumber = body.find("[field='OperNumber']").find(input).val(); //获取数量
																var taxRate = body.find("[field='TaxRate']").find(input).val(); //获取税率
																body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
																body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
																body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
																body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
																statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
															});
															//点击空白处移除计量单位列表
															$(".datagrid-body").off("click").on("click",function(){
																$('.unit-list').remove(); //移除计量单位列表
															});
														}
													});
												}
												var detailPrice = 0; //明细列表-单价
												if(listSubType == "零售" || listSubType == "零售退货") {
													if(res.rows[0].Unit) { //如果存在计量单位信息
														detailPrice = retailPrice;
													}
													else {
														if (firstOutUnit == basicUnit) {
															detailPrice = retailPriceOne;
														}
														else if (firstOutUnit == otherUnit) {
															detailPrice = retailPriceTwo;
														}
													}
												}
												else if(listTitle == "采购入库列表" || listTitle == "销售退货列表" || listTitle == "其它入库列表") {
													if(res.rows[0].Unit) { //如果存在计量单位信息
														detailPrice = presetPriceOne;
													}
													else {
														if (firstInUnit == basicUnit) {
															detailPrice = basicPresetPriceOne;
														}
														else if (firstInUnit == otherUnit) {
															detailPrice = otherPresetPriceOne;
														}
													}
												}
												else if(listTitle == "销售出库列表" || listTitle == "采购退货列表" || listTitle == "其它出库列表" || listTitle == "调拨出库列表" || listTitle == "礼品充值列表" || listTitle == "礼品销售列表") {
													if(res.rows[0].Unit) { //如果存在计量单位信息
														detailPrice = presetPriceTwo;
													}
													else {
														if(firstOutUnit==basicUnit) {
															detailPrice = basicPresetPriceTwo;
														}
														else if(firstOutUnit==otherUnit){
															detailPrice = otherPresetPriceTwo;
														}
													}
												}
												body.find("[field='OperNumber']").find(input).val(1); //数量初始化为1
												//单价和总价赋值
												body.find("[field='UnitPrice']").find(input).val(detailPrice);
												body.find("[field='AllPrice']").find(input).val(detailPrice);
												var taxRate = body.find("[field='TaxRate']").find(input).val()-0; //获取税率
												body.find("[field='TaxUnitPrice']").find(input).val((detailPrice*(1+taxRate/100)).toFixed(2));  //含税单价
												body.find("[field='TaxMoney']").find(input).val((detailPrice*(taxRate/100)).toFixed(2));  //税额
												body.find("[field='TaxLastMoney']").find(input).val((detailPrice*(1+taxRate/100)).toFixed(2));  //价税合计
												statisticsFun(body,detailPrice,1,footer,taxRate);

												//查询库存信息
												var depotId = body.find("[field='DepotId']").find(".combo-value").val();
												if(depotId) {
													var type = "select"; //type 类型：点击 click，选择 select
													findStockNumById(depotId, mId, monthTime, body, input, loadRatio, type);
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
				{ title: '库存',field: 'Stock',editor:'validatebox',width:70},
				{ title: anotherDepotHeadName, field: 'AnotherDepotId',editor:'validatebox',hidden:isShowAnotherDepot,width:90,
					formatter: function (value, row, index) {
						return row.AnotherDepotName;
					},
					editor: {
						type: 'combobox',
						options: {
							valueField: 'id',
							textField: anotherDepotTextField,
							method: 'get',
							url: anotherDepotUrl
						}
					}
				},
				{ title: '单位',field: 'Unit',editor:'validatebox',width:60},
	            { title: '数量',field: 'OperNumber',editor:'validatebox',width:60},
	            { title: '单价',field: 'UnitPrice',editor:'validatebox',width:60},
				{ title: '含税单价',field: 'TaxUnitPrice',editor:'validatebox',hidden:isShowTaxColumn,width:75},
	            { title: '金额',field: 'AllPrice',editor:'validatebox',width:75},
				{ title: '税率(%)',field: 'TaxRate',editor:'validatebox',hidden:isShowTaxColumn,width:75},
				{ title: '税额',field: 'TaxMoney',editor:'validatebox',hidden:isShowTaxColumn,width:75},
				{ title: '价税合计',field: 'TaxLastMoney',editor:'validatebox',hidden:isShowTaxColumn,width:75},
	            { title: '备注',field: 'Remark',editor:'validatebox',width:120},
				{ title: '品名-别',field: 'OtherField1',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '型号-别',field: 'OtherField2',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '颜色-别',field: 'OtherField3',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '备注1',field: 'OtherField4',editor:'validatebox',hidden:true,width:60},
				{ title: '备注2',field: 'OtherField5',editor:'validatebox',hidden:true,width:60}
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
			data: {
				mpList: mPropertyList
			},
			dataType: "json",
			success: function (res) {
				var AllPrice = 0;
				var TaxLastMoney = 0;
				var DiscountMoney = $("#DiscountMoney").val()-0; //优惠金额
				var DiscountLastMoney = $("#DiscountLastMoney").val()-0; //优惠后金额
				if(type === "edit") {
					AllPrice = TotalPrice;
					TaxLastMoney = DiscountMoney + DiscountLastMoney;
				}
				var array = [];
				array.push({
					"AllPrice": AllPrice,
					"TaxLastMoney": TaxLastMoney
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
		var isShowAnotherDepot = true; //显示对方仓库,true为隐藏,false为显示
		var anotherDepotHeadName = ""; //对方仓库的列的标题
		var depotHeadName = ""; //仓库的列的标题
		if(listSubType == "调拨"){
			isShowAnotherDepot = false; //调拨时候显示对方仓库
			anotherDepotHeadName = "调入仓库";
		}
		if(listSubType == "礼品充值"){
			isShowAnotherDepot = false; //礼品充值时候显示礼品卡
			anotherDepotHeadName = "礼品卡";
		}
		if(listSubType == "礼品销售"){
			depotHeadName = "礼品卡";
		}
		else {
			depotHeadName = "仓库名称";
		}
		var isShowTaxColumn = false; //是否显示税率相关的列,true为隐藏,false为显示
		if(listSubType == "调拨" || listSubType == "其它" || listSubType == "零售" || listSubType == "零售退货" || listSubType == "礼品充值" || listSubType == "礼品销售" || listSubType == "组装单" || listSubType == "拆卸单"){
			isShowTaxColumn = true; //隐藏
		}
		var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示
		if(listSubType == "组装单" || listSubType == "拆卸单"){
			isShowMaterialTypeColumn = false; //显示
		}
		$('#materialDataShow').datagrid({
			height:245,
			rownumbers: true,
			//动画效果
			animate:false,
			//选中单行
			singleSelect : true,
			collapsible:false,
			selectOnCheck:false,
			pagination: false,
			//交替出现背景
			striped : true,
			showFooter: true,
			onClickRow: onClickRow,
			columns:[[
				{ title: '商品类型',field: 'MType',width:80, hidden:isShowMaterialTypeColumn},
				{ title: depotHeadName,field: 'DepotName',editor:'validatebox',width:90},
				{ title: '品名(型号)(扩展信息)(单位)',field: 'MaterialName',width:230},
				{ title: anotherDepotHeadName,field: 'AnotherDepotName',hidden:isShowAnotherDepot,width:90},
				{ title: '单位',field: 'Unit',editor:'validatebox',width:60},
				{ title: '数量',field: 'OperNumber',editor:'validatebox',width:60},
				{ title: '单价',field: 'UnitPrice',editor:'validatebox',width:60},
				{ title: '含税单价',field: 'TaxUnitPrice',editor:'validattebox',hidden:isShowTaxColumn,width:75},
				{ title: '金额',field: 'AllPrice',editor:'validatebox',width:75},
				{ title: '税率',field: 'TaxRate',editor:'validatebox',hidden:isShowTaxColumn,width:75},
				{ title: '税额',field: 'TaxMoney',editor:'validatebox',hidden:isShowTaxColumn,width:75},
				{ title: '价税合计',field: 'TaxLastMoney',editor:'validatebox',hidden:isShowTaxColumn,width:75},
				{ title: '备注',field: 'Remark',editor:'validatebox',width:120},
				{ title: '品名-别',field: 'OtherField1',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '型号-别',field: 'OtherField2',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '颜色-别',field: 'OtherField3',editor:'validatebox',hidden:otherColumns,width:60},
				{ title: '备注1',field: 'OtherField4',editor:'validatebox',hidden:true,width:60},
				{ title: '备注2',field: 'OtherField5',editor:'validatebox',hidden:true,width:60}
			]],
			onLoadError:function() {
				$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
				return;
			}    
		});
		$.ajax({
			type:"post",
			url: path + '/depotItem/findBy.action?HeaderId=' + depotHeadID,
			data: {
				mpList: mPropertyList
			},
			dataType: "json",
			success: function (res) {
				var AllPrice = TotalPrice;
				var DiscountMoney = $("#DiscountMoneyShow").text()-0; //优惠金额
				var DiscountLastMoney = $("#DiscountLastMoneyShow").text()-0; //优惠后金额
				var array = [];
				array.push({
					"AllPrice": AllPrice,
					"TaxLastMoney": DiscountMoney + DiscountLastMoney
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
	
	//删除单据信息
	function deleteDepotHead(depotHeadID, thisOrganId, totalPrice, status){
		if(status) {
			$.messager.alert('删除提示','已审核的单据不能删除！','warning');
			return;
		}
		$.messager.confirm('删除确认','确定要删除此单据信息吗？',function(r) {
	        if (r) {
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
							$.messager.alert('删除提示','删除单据信息失败，请稍后再试！','error');
					},
					//此处添加错误处理
		    		error:function()
		    		{
		    			$.messager.alert('删除提示','删除单据信息异常，请稍后再试！','error');
						return;
					}
				});

				//更新会员的预收款信息
				if(listSubType === "零售") {
					$.ajax({
						type:"post",
						url: path + "/supplier/updateAdvanceIn.action",
						dataType: "json",
						data:{
							SupplierID: thisOrganId, //会员id
							AdvanceIn: totalPrice  //删除时同时返还用户的预付款
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

	//批量删除单据信息
	function batDeleteDepotHead(){
		var row = $('#tableData').datagrid('getChecked');	
		if(row.length == 0)
		{
			$.messager.alert('删除提示','没有记录被选中！','info');				
			return;	
		}
		if(row.length > 0)
		{
			$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条单据信息吗？',function(r)
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
					//批量更新会员的预收款信息
					for(var i = 0;i < row.length; i ++) {
						if(listSubType === "零售") {
							$.ajax({
								type:"post",
								url: path + "/supplier/updateAdvanceIn.action",
								dataType: "json",
								data:{
									SupplierID: row[i].OrganId, //会员id
									AdvanceIn: row[i].TotalPrice  //删除时同时返还用户的预付款
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
								$.messager.alert('删除提示','删除单据信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
			    		error:function()
			    		{
			    			$.messager.alert('删除提示','删除单据信息异常，请稍后再试！','error');
							return;
						}
					});	
	            }
	        });
		 }
	}

	//批量审核
	function setStatusFun() {
		var row = $('#tableData').datagrid('getChecked');
		if(row.length == 0) {
			$.messager.alert('审核提示','没有记录被选中！','info');
			return;
		}
		if(row.length > 0) {
			$.messager.confirm('审核确认','确定要审核选中的' + row.length + '条信息吗？',function(r)
			{
				if (r)
				{
					var ids = "";
					for(var i = 0;i < row.length; i ++)
					{
						if(i == row.length-1)
						{
							ids += row[i].Id;
							break;
						}
						ids += row[i].Id + ",";
					}
					$.ajax({
						type:"post",
						url: path + "/depotHead/batchSetStatus.action",
						dataType: "json",
						async :  false,
						data: ({
							Status: true,
							DepotHeadIDs : ids,
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
								$.messager.alert('审核提示','审核信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
						error:function()
						{
							$.messager.alert('审核提示','审核信息异常，请稍后再试！','error');
							return;
						}
					});
				}
			});
		}
	}

	//批量反审核
	function setUnStatusFun() {
		var row = $('#tableData').datagrid('getChecked');
		if(row.length == 0)
		{
			$.messager.alert('反审核提示','没有记录被选中！','info');
			return;
		}
		if(row.length > 0)
		{
			$.messager.confirm('反审核确认','确定要反审核选中的' + row.length + '条信息吗？',function(r)
			{
				if (r)
				{
					var ids = "";
					for(var i = 0;i < row.length; i ++)
					{
						if(i == row.length-1)
						{
							ids += row[i].Id;
							break;
						}
						ids += row[i].Id + ",";
					}
					$.ajax({
						type:"post",
						url: path + "/depotHead/batchSetStatus.action",
						dataType: "json",
						async :  false,
						data: ({
							Status: false,
							DepotHeadIDs : ids,
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
								$.messager.alert('反审核提示','反审核信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
						error:function()
						{
							$.messager.alert('反审核提示','反审核信息异常，请稍后再试！','error');
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
		var thisDateTime = getNowFormatDateTime(); //当前时间
		$("#OperTime").val(thisDateTime);
		var thisNumber = getNowFormatDateNum(); //根据时间生成编号
		var thisDate = getNowFormatDate(); //当前日期
		var beginTime = thisDate + " 00:00:00";
		var endTime = thisDate + " 23:59:59";
		//生成单据编号
		$.ajax({
			type: "post",
			url: path + "/depotHead/buildNumber.action",
			data: {
				Type: listType,
				SubType: listSubType,
				BeginTime: beginTime,
				EndTime: endTime
			},
			success:function(res){
				if(res){
					res = JSON.parse(res);
					var defaultNumber = res.DefaultNumber;
					var thisDateTwo = getNowFormatDateTwo(); //当前日期
					var newNumber = amountNum + thisDateTwo + defaultNumber
					$("#Number").val(newNumber).attr("data-defaultNumber",newNumber);
				}
			},
			error:function(){
				$.messager.alert('提示','生成单据编号失败！','error');
			}
		});
		//初始化优惠率、优惠金额、优惠后金额、本次付|收款、本次欠款 为0
		$("#Discount").val(0);
		$("#DiscountMoney").val(0);
		$("#DiscountLastMoney").val(0);
		$("#ChangeAmount").val(0);
		$("#Debt").val(0);
		var addTitle = listTitle.replace("列表","信息");
		$('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加' + addTitle);
		$(".window-mask").css({ width: webW ,height: webH});
	    
	    orgDepotHead = "";
	    depotHeadID = 0;
	    initTableData_material("add"); //商品列表
	    reject(); //撤销下、刷新商品列表
		$("#addOrgan").off("click").on("click",function(){
			$('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加供应商');
		});
	    url = path + '/depotHead/create.action';

		//零售单据修改收款时，自动计算找零
		if(listSubType == "零售" || listSubType == "零售退货") {
			$("#payType").val("现付");
			$("#OrganId").combobox("setValue", orgDefaultId); //自动默认选择非会员
			//当会员卡号长度超过10位后，自动点击下拉框，用于兼容刷卡器
			$("#OrganId").next().find("input").off("keyup").on("keyup",function(){
				var self = this;
				if($(this).val().length === 10){
					setTimeout(function(){
						$(".combo-panel .combobox-item-selected").click();
						//更新付款类型，加载会员的预付款的金额
						for(var i=0; i<orgDefaultList.length; i++){
							var rec = orgDefaultList[i];
							if(rec.supplier == $(self).val()){
								var option = "";
								if(rec.supplier !== "非会员" && rec.advanceIn >0){
									option = '<option value="预付款">预付款(' + rec.advanceIn + ')</option>';
									option += '<option value="现付">现付</option>';
								}
								else {
									option += '<option value="现付">现付</option>';
								}
								$("#payType").empty().append(option);
							}
						}
					},500);
				}
			});
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
	function editDepotHead(depotHeadTotalInfo, status){
		if(status) {
			$.messager.alert('编辑提示','已审核的单据不能编辑！','warning');
			return;
		}
		var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
	    $("#clientIp").val(clientIp);
		if(listSubType==="礼品销售"){
			$("#GiftId").combobox('setValue',depotHeadInfo[1]);
		}
		else{
			$("#ProjectId").focus().val(depotHeadInfo[1]);
		}
	    var ProjectId=depotHeadInfo[1];
		if(ProjectId!='')
		{
			initSystemData_person(ProjectId);
			initSelectInfo_person();
		}
	    $("#Number").val(depotHeadInfo[2]).attr("data-defaultNumber",depotHeadInfo[2]);
	    $("#OperTime").val(depotHeadInfo[4]);
	    $('#OrganId').combobox('setValue', depotHeadInfo[5]);
	    $("#HandsPersonId").val(depotHeadInfo[6]);
	    $("#AccountId").val(depotHeadInfo[7]);
	    $("#ChangeAmount").val(depotHeadInfo[8]);
	    $("#ChangeAmount").attr("data-changeamount", depotHeadInfo[8]);
	    $("#Remark").val(depotHeadInfo[9]);
		$("#Discount").val(depotHeadInfo[19]);
		$("#DiscountMoney").val(depotHeadInfo[20]);
		$("#DiscountLastMoney").val(depotHeadInfo[21]);
		$("#Debt").val((depotHeadInfo[21]-depotHeadInfo[8]).toFixed(2));
		$("#AccountDay").val(depotHeadInfo[27].replace("undefined","")); //结算天数
	    var TotalPrice = depotHeadInfo[14];
		preTotalPrice = depotHeadInfo[14]; //记录前一次合计金额，用于扣预付款
	    $("#AllocationProjectId").val(depotHeadInfo[15]);
		if(listSubType==="礼品充值"){
			$("#GiftId").combobox('setValue', depotHeadInfo[15]);
		}
	    oldNumber = depotHeadInfo[2]; //记录编辑前的单据编号
		oldId = depotHeadInfo[0]; //记录单据Id
	    var editTitle = listTitle.replace("列表","信息");
	    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑' + editTitle);
	    $(".window-mask").css({ width: webW ,height: webH});
	    depotHeadID = depotHeadInfo[0];

		if(listSubType == "零售"){
			var option = "";
			if(depotHeadInfo[17] === "预付款"){
				option = '<option value="预付款">预付款</option>';
				option += '<option value="现付">现付</option>';
			}
			else {
				option += '<option value="现付">现付</option>';
			}
			$("#payType").empty().append(option);
		}

		if(listSubType === "销售" || listSubType === "销售退货"){
			if(depotHeadInfo[18]){
				var arr = depotHeadInfo[18].split(",");
				var salesmanArray = [];
				for(var i=0;i<arr.length;i++){
					if(arr[i]){
						salesmanArray.push(arr[i].replace("<","").replace(">",""));
					}
				}
				$("#Salesman").combobox('setValues', salesmanArray);
			}
		}

		//采购入库、销售出库的多账户加载
		if(depotHeadInfo[22]!="undefined" && depotHeadInfo[23]!="undefined"){
			$("#AccountId").val("many"); //下拉框选中多账户
			var accountArr = depotHeadInfo[22].split(",");
			var accountMoneyArr = depotHeadInfo[23].split(",");
			accountMoneyArr = changeListFmtPlus(accountMoneyArr)  //将数组单个金额中的数值转为正数

			if(listSubType == "零售" || listSubType == "零售退货") {
				var manyAccountMoney = 0; //多账户合计-零售
				for (var j = 0; j < accountArr.length; j++) {
					if (accountList != null) {
						for (var i = 0; i < accountList.length; i++) {
							var account = accountList[i];
							if (accountArr[j] == account.id) {
								manyAccountMoney += accountMoneyArr[j] - 0; //多账户合计-零售
							}
						}
					}
				}
				$("#getAmount").val(manyAccountMoney); //收款金额、付款金额
				var changeAmount = $("#ChangeAmount").val()-0;
				$("#backAmount").val((manyAccountMoney-changeAmount).toFixed(2)); //找零
			}

			$("#AccountId").attr("data-accountArr", JSON.stringify(accountArr)).attr("data-accountMoneyArr", JSON.stringify(accountMoneyArr));  //json数据存储
			$(".many-account-ico").show(); //显示多账户的ico图标
		}

		//采购入库、销售出库的费用数据加载
		if(depotHeadInfo[25] && depotHeadInfo[26]){
			$("#OtherMoney").val(depotHeadInfo[24].replace("undefined","0")); //采购费用、销售费用
			var itemArr = depotHeadInfo[25].split(",");
			var itemMoneyArr = depotHeadInfo[26].split(",");
			$("#OtherMoney").attr("data-itemArr",JSON.stringify(itemArr)).attr("data-itemMoneyArr",JSON.stringify(itemMoneyArr));  //json数据存储
		}
	    
	    initTableData_material("edit",TotalPrice); //商品列表
	    reject(); //撤销下、刷新商品列表                
	    url = path + '/depotHead/update.action?depotHeadID=' + depotHeadInfo[0];
	}
	
	//查看信息
	function showDepotHead(depotHeadTotalInfo){
		var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
		var manyAccountMoney = 0; //多账户合计-零售
	    $("#ProjectIdShow").text(depotHeadInfo[10]);
	    $("#NumberShow").text(depotHeadInfo[2]);
	    $("#OperTimeShow").text(depotHeadInfo[4]);
	    $('#OrganIdShow').text(depotHeadInfo[11]);
	    $("#HandsPersonIdShow").text(depotHeadInfo[12]);
		if(depotHeadInfo[13]){
			$("#AccountIdShow").text(depotHeadInfo[13]); //结算账户
		}
	    else if(depotHeadInfo[22] && depotHeadInfo[23]){
			var accountArr = depotHeadInfo[22].split(","); //账户id列表
			var accountMoneyArr = depotHeadInfo[23].split(","); //账户金额列表
			var accountIdShow = "";
			for(var j =0;j<accountArr.length; j++){
				if(accountList !=null){
					for(var i = 0 ;i < accountList.length;i++){
						var account = accountList[i];
						if(accountArr[j] == account.id) {
							var currentAccountMoney = accountMoneyArr[j]-0;
							if(currentAccountMoney < 0){
								currentAccountMoney = 0-currentAccountMoney;
							}
							accountIdShow = accountIdShow + account.name + "(" + currentAccountMoney +"元) ";
							manyAccountMoney += accountMoneyArr[j]-0; //多账户合计-零售
						}
					}
				}
			}
			$("#AccountIdShow").text(accountIdShow);
		}
	    $("#ChangeAmountShow").text(depotHeadInfo[8]);
	    $("#RemarkShow").text(depotHeadInfo[9]);
		$("#DiscountShow").text(depotHeadInfo[19]);
		$("#DiscountMoneyShow").text(depotHeadInfo[20]);
		$("#DiscountLastMoneyShow").text(depotHeadInfo[21]);
		$("#DebtShow").text((depotHeadInfo[21]-depotHeadInfo[8]).toFixed(2));
		$("#AccountDayShow").text(depotHeadInfo[27].replace("undefined",""));  //结算天数
		if(depotHeadInfo[25] && depotHeadInfo[26]){
			var itemArr = depotHeadInfo[25].split(","); //支出项目id列表
			var itemMoneyArr = depotHeadInfo[26].split(","); //支出项目金额列表
			var otherMoneyShow = "";
			for(var j =0;j<itemArr.length; j++) {
				if (outItemList != null) {
					for (var i = 0; i < outItemList.length; i++) {
						var money = outItemList[i];
						if(itemArr[j] == money.Id) {
							otherMoneyShow = otherMoneyShow + money.InOutItemName + "(" + itemMoneyArr[j] +"元) ";
						}
					}
				}
			}
			$("#OtherMoneyShow").text(otherMoneyShow +"总计："+ depotHeadInfo[24].replace("undefined","0") + "元 "); //采购费用、销售费用
		}
		else {
			$("#OtherMoneyShow").text(depotHeadInfo[24].replace("undefined","0")); //采购费用、销售费用
		}
		$("#payTypeShow").text(depotHeadInfo[17]);
	    var TotalPrice = depotHeadInfo[14];
	    $("#AllocationProjectIdShow").text(depotHeadInfo[16]);
	    var showTitle = listTitle.replace("列表","信息");
	    $('#depotHeadDlgShow').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看' + showTitle);
	    $(".window-mask").css({ width: webW ,height: webH});
	    
	    depotHeadID = depotHeadInfo[0];
	    initTableData_material_show(TotalPrice); //商品列表-查看状态

		//零售单据展示数据
		if(listSubType == "零售" || listSubType == "零售退货"){
			var changeAccount = $("#depotHeadDlgShow .change-amount-show").text() -0;
			if(manyAccountMoney!==0){
				$("#depotHeadDlgShow .get-amount-show").text((manyAccountMoney).toFixed(2));
				$("#depotHeadDlgShow .back-amount-show").text((manyAccountMoney -changeAccount).toFixed(2));
			}
			else {
				$("#depotHeadDlgShow .get-amount-show").text((changeAccount).toFixed(2));
				$("#depotHeadDlgShow .back-amount-show").text(0);
			}
		}
		if(listSubType === "销售" || listSubType === "销售退货"){
			if(depotHeadInfo[18]){
				var arr = depotHeadInfo[18].split(",");
				var salesmanStr = "";
				for(var i=0;i<arr.length;i++){
					if(arr[i]){
						if(i === arr.length-1){
							salesmanStr += arr[i].replace("<","").replace(">","");
						}
						else {
							salesmanStr += arr[i].replace("<","").replace(">","") + ",";
						}
					}
				}
				$.ajax({
					type: "post",
					url: path + "/person/getPersonByIds.action",
					data: {
						PersonIDs: salesmanStr
					},
					success:function(res){
						if(res){
							$("#SalesmanShow").text(res); //销售人员列表
						}
					},
					error:function(){

					}
				});
			}
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

		//检查单据编号是否存在
		function checkDepotHeadNumber() {
			var thisNumber = $.trim($("#Number").val());
			//表示是否存在 true == 存在 false = 不存在
			var flag = false;
			//开始ajax名称检验，不能重名
			if(thisNumber.length > 0 &&( oldNumber.length ==0 || thisNumber != oldNumber))
			{
				$.ajax({
					type:"post",
					url: path + "/depotHead/checkIsNumberExist.action",
					dataType: "json",
					async :  false,
					data: ({
						DepotHeadID : oldId,
						Number : thisNumber
					}),
					success: function (tipInfo)
					{
						flag = tipInfo;
						if(tipInfo)
						{
							$.messager.alert('提示','抱歉，该单据编号已经存在','warning');
							return;
						}
					},
					//此处添加错误处理
					error:function()
					{
						$.messager.alert('提示','检查单据编号是否存在异常，请稍后再试！','error');
						return;
					}
				});
			}
			return flag;
		}
		//保存信息
		$("#saveDepotHead").off("click").on("click",function(){
			if(!$('#depotHeadFM').form('validate')){
				return;
			}
			else {
				//如果初始编号被修改了，就要判断单据编号是否存在
				if($.trim($("#Number").val()) != $('#Number').attr("data-defaultNumber")){
					//调用查询单据编号是否重名的方法
					if(checkDepotHeadNumber()){
						return;
					}
				}
				//输入框提示
				if(listTitle === "采购入库列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择供应商！','warning');
						return;
					}
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择结算账户！','warning');
						return;
					}
				}
				else if(listTitle === "零售退货列表"){
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择付款账户！','warning');
						return;
					}
					if($("#AccountId").val() == "many" && $("#backAmount").val()-0 >0) {
						$.messager.alert('提示', '选择多账户时的找零金额不能大于0！', 'warning');
						return;
					}
				}
				else if(listTitle === "销售退货列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择退货单位！','warning');
						return;
					}
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择付款账户！','warning');
						return;
					}
				}
				else if(listTitle === "其它入库列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择往来单位！','warning');
						return;
					}
				}
				else if(listTitle === "零售出库列表"){
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择收款账户！','warning');
						return;
					}
					if($("#backAmount").val()-0 <0){
						$.messager.alert('提示','找零金额不能小于0！','warning');
						return;
					}
					if($("#AccountId").val() == "many" && $("#backAmount").val()-0 >0) {
						$.messager.alert('提示', '选择多账户时的找零金额不能大于0！', 'warning');
						return;
					}
				}
				else if(listTitle === "销售出库列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择购买单位！','warning');
						return;
					}
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择收款账户！','warning');
						return;
					}
				}
				else if(listTitle === "采购退货列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择收货单位！','warning');
						return;
					}
					if(!$('#AccountId').val()){
						$.messager.alert('提示','请选择收款账户！','warning');
						return;
					}
				}
				else if(listTitle === "其它出库列表"){
					if(!$('#OrganId').combobox('getValue')){
						$.messager.alert('提示','请选择往来单位！','warning');
						return;
					}
				}
				else if(listTitle === "调拨出库列表"){

				}
				var OrganId = null, ProjectId = null,AllocationProjectId = null;
				var ChangeAmount = $.trim($("#ChangeAmount").val())-0;
				var TotalPrice = $("#depotHeadFM .datagrid-footer [field='AllPrice'] div").text();
				if($('#OrganId').length){
					OrganId = $('#OrganId').combobox('getValue');
				}
				var accountMoneyList = $("#AccountId").attr("data-accountmoneyarr"); //账户金额列表-多账户
				var accountMoneyArr;
				if(accountMoneyList) {
					accountMoneyList = accountMoneyList.replace("[","").replace("]","").toString();
					var reg=new RegExp("\"","g"); //创建正则RegExp对象
					accountMoneyList = accountMoneyList.replace(reg,""); //替换所有的双引号
					accountMoneyArr = accountMoneyList.split(","); //转为数组
				}
				if(listSubType === "采购"||listSubType === "零售退货"||listSubType === "销售退货"){
					//付款为负数
					ChangeAmount = 0 - ChangeAmount;
					TotalPrice = 0 - TotalPrice;
					if(accountMoneyArr) {
						accountMoneyArr = changeListFmtMinus(accountMoneyArr); //将数组单个金额中的数值转为负数
					}
				}
				//零售时候，可以从会员预付款中扣款
				var thisPayType = "现付";
				if(listSubType === "零售") {
					if($("#payType").val() ==="预付款") {
						thisPayType = "预付款";
					}
				}
				var SalesmanStr = "";
				if(listSubType === "销售" || listSubType === "销售退货"){
					var Salesman = $('#Salesman').combobox('getValues').toString(); //销售人员
					var SalesmanArray = Salesman.split(",");
					for (var i = 0; i < SalesmanArray.length; i++) {
						if (i === SalesmanArray.length - 1) {
							SalesmanStr += "<" + SalesmanArray[i] + ">";
						}
						else {
							SalesmanStr += "<" + SalesmanArray[i] + ">,";
						}
					}
				}
				var getAccountID = $.trim($("#AccountId").val());
				if($("#AccountId").val() === "many"){ //多账户
					getAccountID = null;
				}
				$.ajax({
					type:"post",
					url: url,
					dataType: "json",
					async :  false,
					data: ({
						Type: listType,
						SubType: listSubType,
						ProjectId: ProjectId,
						AllocationProjectId: AllocationProjectId,
						DefaultNumber: $.trim($("#Number").attr("data-defaultNumber")),//初始编号
						Number: $.trim($("#Number").val()),
						OperTime: $("#OperTime").val(),
						OrganId: OrganId,
						HandsPersonId: $.trim($("#HandsPersonId").val()),
						Salesman: SalesmanStr, //销售人员
						AccountId: getAccountID,
						ChangeAmount: ChangeAmount, //付款/收款
						TotalPrice: TotalPrice, //合计
						PayType: thisPayType, //现付/预付款
						Remark: $.trim($("#Remark").val()),
						AccountIdList: $("#AccountId").attr("data-accountarr"), //账户列表-多账户
						AccountMoneyList: accountMoneyArr ? JSON.stringify(accountMoneyArr):"", //账户金额列表-多账户
						Discount: $.trim($("#Discount").val()),
						DiscountMoney: $.trim($("#DiscountMoney").val()),
						DiscountLastMoney: $.trim($("#DiscountLastMoney").val()),
						OtherMoney: $.trim($("#OtherMoney").val()), //采购费用、销售费用
						OtherMoneyList: $("#OtherMoney").attr("data-itemarr"), //支出项目列表-涉及费用
						OtherMoneyItem: $("#OtherMoney").attr("data-itemmoneyarr"), //支出项目金额列表-涉及费用
						AccountDay: $("#AccountDay").val(), //结算天数
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

							if(thisPayType === "预付款") {
								//更新用户信息-预付款
								var advanceIn = 0; //预付款金额
								if(depotHeadID){
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
										SupplierID: OrganId, //会员id
										AdvanceIn: 0 - advanceIn  //保存的同时扣掉用户的预付款
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

							//保存明细记录
							if(depotHeadID ==0)
							{
								getMaxId(); //查找最大的Id
								accept(depotHeadMaxId,closeDialog); //新增
							}
							else
							{
								accept(depotHeadID,closeDialog); //修改
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
		    if(k == "13"&&(obj.id=="searchState"||obj.id=="searchNumber"||obj.id=="searchMaterial"))
		    {
		        $("#searchBtn").click();
		    }
		});

		//优惠率输入框事件
		$("#Discount").off("keyup").on("keyup",function(){
			var footer =$("#depotHeadFM .datagrid-footer");
			var totalPrice = footer.find("[field='TaxLastMoney']").find("div").text();
			var discountNum = $(this).val();
			var discountMoney  = (discountNum/100*totalPrice).toFixed(2);
			var discountLastMoney  = (totalPrice - discountMoney).toFixed(2);
			$("#DiscountMoney").val(discountMoney); //优惠金额
			$("#DiscountLastMoney").val(discountLastMoney); //优惠后金额
			if($("#AccountId").val()!=="many"){
				$("#ChangeAmount").val(discountLastMoney); //本次付、收款
			}
			var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
			$("#Debt").val((discountLastMoney-changeAmountNum).toFixed(2)); //本次欠款
		});

		//优惠金额输入框事件
		$("#DiscountMoney").off("keyup").on("keyup",function(){
			var footer =$("#depotHeadFM .datagrid-footer");
			var totalPrice = footer.find("[field='TaxLastMoney']").find("div").text();
			var discountMoney = $(this).val();
			var discount  = (discountMoney/totalPrice).toFixed(2)*100;
			var discountLastMoney  = (totalPrice - discountMoney).toFixed(2);
			$("#Discount").val(discount); //优惠金额
			$("#DiscountLastMoney").val(discountLastMoney); //优惠后金额
			if($("#AccountId").val()!=="many"){
				$("#ChangeAmount").val(discountLastMoney); //本次付、收款
			}
			var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
			$("#Debt").val((discountLastMoney-changeAmountNum).toFixed(2)); //本次欠款
		});

		//付款、收款输入框事件
		$("#ChangeAmount").off("keyup").on("keyup",function(){
			var discountLastMoney = $("#DiscountLastMoney").val();
			var changeAmount = $(this).val();
			var debtMoney  = (discountLastMoney - changeAmount).toFixed(2);
			$("#Debt").val(debtMoney); //本次欠款
		});

		//多账户结算窗口弹出事件
		function depotHeadAccountDlgFun(){
			$('#depotHeadAccountDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;多账户结算');
			$("#depotHeadAccountDlg .account-dlg .account-content-tmp").remove(); //先移除输入栏目
			$("#accountMoneyTotalDlg").text(0); //将合计初始化为0
			for(var i=0; i<6; i++) {
				$("#depotHeadAccountDlg .tabs-tmp .account-content-tmp").attr("data-index",5-i); //添加索引
				var contentTmp = $("#depotHeadAccountDlg .tabs-tmp tbody").html();
				var accountDlgHead = $("#depotHeadAccountDlg .account-head-tmp");
				accountDlgHead.after(contentTmp);
			}

			//获取账户信息
			function accountDlgFun() {
				var options = "";
				if(accountList !=null){
					for(var i = 0 ;i < accountList.length;i++) {
						var account = accountList[i];
						options += '<option value="' + account.id + '" data-currentAmount="' + account.currentAmount + '">' + account.name + '</option>';
					}
					$(".account-id-dlg").empty().append("<option></option>").append(options);
				}
			}
			accountDlgFun(); //获取账户信息
			$("#depotHeadAccountDlg .tabs-tmp").hide(); //隐藏模板

			//账户金额输入框事件-多账户
			$("#depotHeadAccountDlg .account-dlg .account-money-dlg").off("keyup").on("keyup",function(){
				var totalAccoutNum = 0;
				$("#depotHeadAccountDlg .account-dlg .account-content-tmp").each(function(){
					var eachAccountMoney = $(this).find(".account-money-dlg").val()-0;
					totalAccoutNum += eachAccountMoney;
				});
				$("#accountMoneyTotalDlg").text(totalAccoutNum);
			});

			//结算多账户列表的切换事件
			$("#depotHeadAccountDlg .account-dlg .account-id-dlg").off("change").on("change",function(){
				var selectAccount = $(this).children('option:selected').text();
				if(selectAccount === ""){
					var thisMoneyDom = $(this).closest(".account-content-tmp").find(".account-money-dlg");
					var thisMoney = thisMoneyDom.val()-0;
					var accountMoneyTotal = $("#accountMoneyTotalDlg").text() - 0;
					$("#accountMoneyTotalDlg").text(accountMoneyTotal - thisMoney);
					thisMoneyDom.val("");  //账户为空时候，将金额也置为空
				}
			});

			//保存按钮事件
			$("#saveDepotHeadAccountDlg").off("click").on("click", function(){
				//完成多账户的json数据存储
				var accountArr = []; //账户id数组
				var accountMoneyArr = []; //账户金额数组
				var errorIndex = -1;
				$("#depotHeadAccountDlg .account-dlg .account-content-tmp").each(function(){
					var thisAccId = $(this).find(".account-id-dlg").val();
					var thisAccMoney =  $(this).find(".account-money-dlg").val();
					if(!thisAccId && thisAccMoney) {
						errorIndex = $(this).attr("data-index")-0;
						return;
					}
					if(thisAccId && !thisAccMoney) {
						errorIndex = $(this).attr("data-index")-0;
						return;
					}
					if(thisAccId && thisAccMoney) {
						accountArr.push(thisAccId);
						accountMoneyArr.push(thisAccMoney);
					}
				});
				if(errorIndex >-1){
					$.messager.alert('错误提示',"第" + (errorIndex+1) + "行数据存在问题，请修改",'warning');
					return;
				}
				var discountLastMoneyNum =$("#DiscountLastMoney").val()-0; //优惠后金额
				var accountMoneyTotal = $("#accountMoneyTotalDlg").text()-0; //本次付款或者收款
				if(accountMoneyTotal===0){
					$.messager.alert('错误提示',"请填写金额后保存",'warning');
					return;
				}
				if(accountArr.length && accountMoneyArr.length) {
					$("#AccountId").attr("data-accountArr",JSON.stringify(accountArr)).attr("data-accountMoneyArr",JSON.stringify(accountMoneyArr));  //json数据存储
				}
				if(listSubType==="零售" || listSubType==="零售退货") {
					$("#getAmount").val(accountMoneyTotal); //给付款或者收款金额赋值
					var backAmount = $("#getAmount").val() - $("#ChangeAmount").val();
					$("#backAmount").val((backAmount - 0).toFixed(2)); //计算找零金额
				}
				else {
					$("#ChangeAmount").val(accountMoneyTotal); //给付款或者收款金额赋值
				}
				$("#Debt").val((discountLastMoneyNum-accountMoneyTotal).toFixed(2)); //本次欠款
				$("#depotHeadAccountDlg").dialog('close');
			});

			//取消事件
			function cancelFun(){
				if($("#AccountId").attr("data-accountArr")){
					$("#depotHeadAccountDlg").dialog('close');
				}
				else {
					$("#depotHeadAccountDlg").dialog('close');
					$("#AccountId").val("").removeAttr("data-accountArr").removeAttr("data-accountMoneyArr"); //将下拉置空并把缓存参数清空
					if(listSubType==="零售" || listSubType==="零售退货"){
						$("#ChangeAmount").prop("readonly","readonly");
					}
					else {
						$("#ChangeAmount").removeProp("readonly","readonly");
					}
					$(".many-account-ico").hide(); //隐藏多账户小图标
				}
			}
			//多账户-取消按钮
			$("#cancelDepotHeadAccountDlg").off("click").on("click", function(){
				cancelFun();
			});

			//多账户-右上角的关闭按钮
			$("#depotHeadAccountDlg").prev().find(".panel-tool-close").off("click").on("click", function(){
				cancelFun();
			});
		}
		//点击多账户，弹出输入框
		$("#AccountId").off("change").on("change",function(){
			var selectText = $(this).children('option:selected').text();
			if(selectText === "多账户"){
				$("#ChangeAmount").prop("readonly","readonly");
				depotHeadAccountDlgFun();
				$(".many-account-ico").show(); //显示多账户小图标
			}
			else{
				$(this).removeAttr("data-accountArr").removeAttr("data-accountMoneyArr"); //将下拉置空并把缓存参数清空
				if(listSubType==="零售" || listSubType==="零售退货"){
					$("#ChangeAmount").prop("readonly","readonly");
				}
				else {
					$("#ChangeAmount").removeProp("readonly","readonly");
				}
				$(".many-account-ico").hide(); //隐藏多账户小图标
			}
		});

		//结算账户-多账户小图标-点击事件
		$(".many-account-ico").off("click").on("click",function(){
			depotHeadAccountDlgFun();
			//给弹窗赋值-多账户数据
			var accountArr = $("#AccountId").attr("data-accountArr");
			accountArr = JSON.parse(accountArr);
			var accountMoneyArr = $("#AccountId").attr("data-accountMoneyArr");
			accountMoneyArr = JSON.parse(accountMoneyArr);
			$("#depotHeadAccountDlg .account-dlg .account-content-tmp").each(function(){
				var index = $(this).attr("data-index");
				$(this).find(".account-id-dlg").val(accountArr[index]);
				$(this).find(".account-money-dlg").val(accountMoneyArr[index]);
			});
			if(listSubType==="零售" || listSubType==="零售退货") {
				$("#accountMoneyTotalDlg").text($("#getAmount").val());
			}
			else {
				$("#accountMoneyTotalDlg").text($("#ChangeAmount").val());
			}
		});

		//点击采购费用、销售费用的事件
		$(".other-money-ico").off("click").on("click",function(){
			$('#otherMoneyDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;'+ listSubType +'费用');
			$("#otherMoneyDlg .money-dlg .money-content-tmp").remove(); //先移除输入栏目
			$("#otherMoneyTotalDlg").text(0); //将合计初始化为0
			for(var i=0; i<6; i++) {
				$("#otherMoneyDlg .tabs-tmp .money-content-tmp").attr("data-index",5-i); //添加索引
				var contentTmp = $("#otherMoneyDlg .tabs-tmp tbody").html();
				var moneyDlgHead = $("#otherMoneyDlg .money-head-tmp");
				moneyDlgHead.after(contentTmp);
			}

			//获取支出项目信息
			function moneyDlgFun() {
				var options = "";
				if(outItemList !=null){
					for(var i = 0 ;i < outItemList.length;i++) {
						var money = outItemList[i];
						options += '<option value="' + money.Id + '">' + money.InOutItemName + '</option>';
					}
					$(".money-id-dlg").empty().append("<option></option>").append(options);
				}
			}
			moneyDlgFun(); //获取支出项目信息
			$("#otherMoneyDlg .tabs-tmp").hide(); //隐藏模板

			//支出项目的金额输入框事件
			$("#otherMoneyDlg .money-dlg .other-money-dlg").off("keyup").on("keyup",function(){
				var totalMoneyNum = 0;
				$("#otherMoneyDlg .money-dlg .money-content-tmp").each(function(){
					var eachOtherMoney = $(this).find(".other-money-dlg").val()-0;
					totalMoneyNum += eachOtherMoney;
				});
				$("#otherMoneyTotalDlg").text(totalMoneyNum);
			});

			//支出项目列表的切换事件
			$("#otherMoneyDlg .money-dlg .money-id-dlg").off("change").on("change",function(){
				var selectItem = $(this).children('option:selected').text();
				if(selectItem === ""){
					var thisMoneyDom = $(this).closest(".money-content-tmp").find(".other-money-dlg");
					var thisMoney = thisMoneyDom.val()-0;
					var otherMoneyTotal = $("#otherMoneyTotalDlg").text() - 0;
					$("#otherMoneyTotalDlg").text(otherMoneyTotal - thisMoney);
					thisMoneyDom.val("");  //支出项目为空时候，将金额也置为空
				}
			});

			//保存按钮事件
			$("#saveOtherMoneyDlg").off("click").on("click", function(){
				//完成支出项目的json数据存储
				var itemArr = []; //支出项目id数组
				var itemMoneyArr = []; //支出项目金额数组
				var errorIndex = -1;
				$("#otherMoneyDlg .money-dlg .money-content-tmp").each(function(){
					var thisId = $(this).find(".money-id-dlg").val();
					var thisMoney =  $(this).find(".other-money-dlg").val();
					if(!thisId && thisMoney) {
						errorIndex = $(this).attr("data-index")-0;
						return;
					}
					if(thisId && !thisMoney) {
						errorIndex = $(this).attr("data-index")-0;
						return;
					}
					if(thisId && thisMoney) {
						itemArr.push(thisId);
						itemMoneyArr.push(thisMoney);
					}
				});
				if(errorIndex >-1){
					$.messager.alert('错误提示',"第" + (errorIndex+1) + "行数据存在问题，请修改",'warning');
					return;
				}
				var otherMoneyTotal = $("#otherMoneyTotalDlg").text()-0; //合计金额
				if(otherMoneyTotal === 0){ //0的时候清空缓存数据
					$("#OtherMoney").removeAttr("data-itemArr").removeAttr("data-itemMoneyArr");
				}
				if(itemArr.length && itemMoneyArr.length) {
					$("#OtherMoney").attr("data-itemArr",JSON.stringify(itemArr)).attr("data-itemMoneyArr",JSON.stringify(itemMoneyArr));  //json数据存储
				}
				$("#OtherMoney").val(otherMoneyTotal); //给采购费用、销售费用赋值
				$("#otherMoneyDlg").dialog('close');
			});

			//取消事件
			function cancelFun(){
				if($("#OtherMoney").attr("data-itemArr")){
					$("#otherMoneyDlg").dialog('close');
				}
				else {
					$("#otherMoneyDlg").dialog('close');
					$("#OtherMoney").val("").removeAttr("data-itemArr").removeAttr("data-itemMoneyArr"); //将下拉置空并把缓存参数清空
				}
			}
			//费用-取消按钮
			$("#cancelOtherMoneyDlg").off("click").on("click", function(){
				cancelFun();
			});

			//费用-右上角的关闭按钮
			$("#otherMoneyDlg").prev().find(".panel-tool-close").off("click").on("click", function(){
				cancelFun();
			});

			//给弹窗赋值-采购费用、销售费用数据
			var itemArr = $("#OtherMoney").attr("data-itemArr");
			itemArr = JSON.parse(itemArr);
			var itemMoneyArr = $("#OtherMoney").attr("data-itemMoneyArr");
			itemMoneyArr = JSON.parse(itemMoneyArr);
			$("#otherMoneyDlg .money-dlg .money-content-tmp").each(function(){
				var index = $(this).attr("data-index");
				$(this).find(".money-id-dlg").val(itemArr[index]);
				if(itemMoneyArr[index]!="undefined"){
					$(this).find(".other-money-dlg").val(itemMoneyArr[index]);
				}
			});
			$("#otherMoneyTotalDlg").text($("#OtherMoney").val());
		});

		if(listTitle === "采购入库列表"){
			//检查单位名称是否存在 ++ 重名无法提示问题需要跟进
			function checkSupplierName() {
				var supplierName = $.trim($("#supplier").val());
				var orgSupplier = "";
				//表示是否存在 true == 存在 false = 不存在
				var flag = false;
				//开始ajax名称检验，不能重名
				if(supplierName.length > 0 &&( orgSupplier.length ==0 || supplierName != orgSupplier))
				{
					$.ajax({
						type:"post",
						url: path + "/supplier/checkIsNameExist.action",
						dataType: "json",
						async :  false,
						data: ({
							supplierID : 0,
							supplier : supplierName
						}),
						success: function (tipInfo)
						{
							flag = tipInfo;
							if(tipInfo)
							{
								$.messager.alert('提示','单位名称已经存在','info');
								return;
							}
						},
						//此处添加错误处理
						error:function()
						{
							$.messager.alert('提示','检查单位名称是否存在异常，请稍后再试！','error');
							return;
						}
					});
				}
				return flag;
			}
			//保存供应商信息
			$("#saveSupplier").off("click").on("click",function() {
				if(checkSupplierName()){
					return;
				}
				var reg = /^([0-9])+$/;
				var phonenum = $.trim($("#phonenum").val());
				if(phonenum.length>0 && !reg.test(phonenum))
				{
					$.messager.alert('提示','电话号码只能是数字','info');
					$("#phonenum").val("").focus();
					return;
				}
				var beginNeedGet = $.trim($("#BeginNeedGet").val());
				var beginNeedPay = $.trim($("#BeginNeedPay").val());
				if(beginNeedGet && beginNeedPay) {
					$.messager.alert('提示','期初应收和期初应付不能同时输入','info');
					return;
				}
				var url = path + '/supplier/create.action';
				$.ajax({
					url: url,
					type:"post",
					dataType: "json",
					data:{
						supplier:$("#supplier").val(),
						type: "供应商",
						contacts:$("#contacts").val(),
						phonenum:$("#phonenum").val(),
						telephone:$("#telephone").val(),
						email:$("#email").val(),
						address:$("#address").val(),
						fax:$("#fax").val(),
						BeginNeedGet:$("#BeginNeedGet").val(),
						BeginNeedPay:$("#BeginNeedPay").val(),
						taxNum:$("#taxNum").val(),
						taxRate:$("#taxRate").val(),
						bankName:$("#bankName").val(),
						accountNumber:$("#accountNumber").val(),
						description:$("#description").val(),
						enabled:1,
						clientIp: clientIp
					},
					success: function(res) {
						if (res) {
							$('#supplierDlg').dialog('close');
							initSupplier(); //刷新供应商
						}
					}
				});
			});
		}
	}

	function showDepotHeadDetails(pageNo,pageSize){
		var materialParam = $.trim($("#searchMaterial").val());
		$.ajax({
			type:"post",
			url: path + "/depotHead/getHeaderIdByMaterial.action",
			dataType: "json",
			data: ({
				MaterialParam: materialParam
			}),
			success: function (res) {
				if(res) {
					var ids = res.ret;
					if(ids){
						$.ajax({
							type: "post",
							url: path + "/depotHead/findBy.action",
							dataType: "json",
							data: ({
								Type: listType,
								SubType: listSubType,
								State: $.trim($("#searchState").val()),
								Number: $.trim($("#searchNumber").val()),
								BeginTime: $("#searchBeginTime").val(),
								EndTime: $("#searchEndTime").val(),
								dhIds: ids,
								pageNo: pageNo,
								pageSize: pageSize
							}),
							success: function (data) {
								$("#tableData").datagrid('loadData', data);
							},
							//此处添加错误处理
							error: function () {
								$.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
								return;
							}
						});
					}
					else {
						$("#tableData").datagrid('loadData', []);
					}
				}
			},
			//此处添加错误处理
			error:function() {
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
			body.find("[field='Stock']").find(input).prop("readonly","readonly");
			//点击库存文本框，字段计算库存
			body.find("[field='Stock']").find(input).off("click").on("click",function(){
				var depotId = body.find("[field='DepotId']").find(".combo-value").val();
				var mId = body.find("[field='MaterialId']").find(".combo-value").val();
				var monthTime = getNowFormatMonth();
				if(depotId && mId){
					var ratio = body.find("[field='Unit']").find(input).attr("data-ratio");
					body.find("[field='Stock']").find(input).prop("readonly","readonly");
					//在新增的时候，这个ratio有值；在编辑的时候，这个ratio为undefined
					var type = "click"; //type 类型：点击 click，选择 select
					findStockNumById(depotId, mId, monthTime, body, input, ratio, type);
				}
				else{
					body.find("[field='Stock']").find(input).val(0).attr("data-stock",0); //加载库存数据
				}
			});
			//修改数量，自动计算金额和合计，另外计算含税单价、税额、价税合计
			body.find("[field='OperNumber']").find(input).off("keyup").on("keyup",function(){
				var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
				var taxRate = body.find("[field='TaxRate']").find(input).val(); //税率
				var OperNumber =$(this).val()-0; //数量
				body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);

			});
	    	//修改单价，自动计算金额和合计
	    	body.find("[field='UnitPrice']").find(input).off("keyup").on("keyup",function(){
	    		var UnitPrice =$(this).val()-0; //单价
				var taxRate = body.find("[field='TaxRate']").find(input).val(); //税率
	    		var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
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
								temp +="<li>批发价：" + presetPriceTwo + "</li>";
								temp +="<li>零售价：" + retailPrice + "</li>";
								temp +="</ul>";
								temp +="</div>";
								if($('.price-list').length){
									$('.price-list').remove(); //如果存在价格列表先移除
								}
								else {
									if(presetPriceTwo != undefined){ //多单位的商品
										$(self).after(temp); //加载列表信息
									}
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
			//修改含税单价，自动计算单价、金额、税额、价税合计和合计
			body.find("[field='TaxUnitPrice']").find(input).off("keyup").on("keyup",function(){
				var TaxUnitPrice =$(this).val()-0; //含税单价
				var taxRate = body.find("[field='TaxRate']").find(input).val(); //税率
				var UnitPrice = TaxUnitPrice/(1+taxRate/100); //计算单价
				body.find("[field='UnitPrice']").find(input).val((UnitPrice).toFixed(2)); //单价
				var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
	    	//修改金额，自动计算单价、税额、价税合计和合计
	    	body.find("[field='AllPrice']").find(input).off("keyup").on("keyup",function(){
	    		var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				var taxRate = body.find("[field='TaxRate']").find(input).val(); //税率
	    		var AllPrice =$(this).val()-0; //金额
				var UnitPrice = (AllPrice/OperNumber).toFixed(2);
	    		body.find("[field='UnitPrice']").find(input).val(UnitPrice); //单价
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
	    	});
			//修改税率，自动计算含税单价、税额、价税合计和合计
			body.find("[field='TaxRate']").find(input).off("keyup").on("keyup",function(){
				var taxRate =$(this).val()-0; //税率
				var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改税额，自动计算税率、含税单价、价税合计和合计
			body.find("[field='TaxMoney']").find(input).off("keyup").on("keyup",function(){
				var taxMoney =$(this).val()-0; //税额
				var AllPrice = body.find("[field='AllPrice']").find(input).val(); //金额
				var taxRate = taxMoney/AllPrice*100; //税率
				var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxRate']").find(input).val((taxRate).toFixed(2)); //税率
				body.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改价税合计，自动计算税率、含税单价、税额和合计
			body.find("[field='TaxLastMoney']").find(input).off("keyup").on("keyup",function(){
				var taxLastMoney =$(this).val()-0; //价税合计
				var AllPrice = body.find("[field='AllPrice']").find(input).val(); //金额
				var taxRate = (taxLastMoney-AllPrice)/AllPrice*100; //税率
				var OperNumber = body.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
				body.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				body.find("[field='TaxRate']").find(input).val((taxRate).toFixed(2)); //税率
				body.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});

			//加载税率
			if(thisTaxRate) {
				body.find("[field='TaxRate']").find(input).val(thisTaxRate);
			}
			else {
				body.find("[field='TaxRate']").find(input).val(0); //默认为0
			}

			//在商品类型加载 组装件、普通子件
			var mType = body.find("[field='MType']");
			var rowListLength = mType.find(input).closest(".datagrid-row").attr("datagrid-row-index");
			var mTypeValue = "组合件";
			if(rowListLength > 0){
				mTypeValue = "普通子件";
			}
			if(listSubType == "组装单" || listSubType == "拆卸单"){
				mType.find(input).val(mTypeValue).prop("readonly","readonly");
			}
	    },500);
	}
	
	//结束编辑	
	function endEditing() {
	    if (editIndex == undefined) { return true }
	    if ($('#materialData').datagrid('validateRow', editIndex)) {
			//仓库信息
			var edDepot = $('#materialData').datagrid('getEditor', {index:editIndex,field:'DepotId'});
			var DepotName = $(edDepot.target).combobox('getText');
			$('#materialData').datagrid('getRows')[editIndex]['DepotName'] = DepotName;
			//商品信息
	    	var edMaterial = $('#materialData').datagrid('getEditor', {index:editIndex,field:'MaterialId'});
	        var MaterialName = $(edMaterial.target).combobox('getText');
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
	        if (row[i].MaterialId == "" || row[i].OperNumber == "" || row[i].UnitPrice == "" || row[i].AllPrice == "") {
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
	function accept(accepId,fun) {
	    append();
	    removeit();
	    if ($("#materialData").datagrid('getChanges').length) {
	        if (!CheckData()) {
				return;
			}
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
	                else {
						$.messager.alert('提示', '保存失败！', 'error');
					}
					fun && fun();
	            },
	            error: function (XmlHttpRequest, textStatus, errorThrown) 
	            {
	                $.messager.alert('提示',XmlHttpRequest.responseText,'error');
					fun && fun();
	            }
	        });
	    }
		else {
			fun && fun();
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






