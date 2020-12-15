	//初始化界面
	var defDepotId = null;
	var kid = sessionStorage.getItem("userId");
    var pageType = getUrlParam('t');  //获取页面类型传值
	var depotList = null;
	var userBusinessList=null;
	var userdepot=null;
	var url;
	var editIndex = undefined;
	var depotHeadID = 0;
	var preTotalPrice = 0; //前一次加载的金额
	var listTitle = ""; //单据标题
	var listType = ""; //入库 出库
	var listSubType = ""; //采购 销售等
	var payTypeTitle = "";//付款 收款
	var organUrl = ""; //组织数据接口地址
	var amountNum = ""; //单据编号开头字符
	var depotString = ""; //店铺id列表
	var orgDefaultId=''; //单位默认编号
	var orgDefaultList; //存储查询出来的会员列表
	var accountList; //账户列表
	var outItemList; //支出项目列表
	var oldNumber = ""; //编辑前的单据编号
	var oldId = 0; //编辑前的单据Id
	var otherColumns = true; //明细中的‘别名’列是否显示
	var btnEnableList = getBtnStr(); //获取按钮的权限
	var mPropertyList = ""; //商品属性列表
	var defaultAccountId = 0; //默认账户id
	var roleType = ""; //角色类型
	var inOutService = {
		getRoleType: function () {
			$.ajax({
				type:"get",
				url: "/user/getRoleTypeByUserId",
				async: false,
				success: function (res) {
					if (res && res.code === 200) {
						roleType = res.data.roleType;
					}
					else {
						roleType = null;
					}
				}
			});
		},
		//初始化系统基础信息
		initSystemData_UB: function () {
			$.ajax({
				type:"get",
				url: "/userBusiness/getBasicData",
				data: ({
					KeyId:kid,
					Type:"UserDepot"
				}),
				//设置为同步
				async:false,
				dataType: "json",
				success: function (res) {
					if (res && res.code === 200) {
						userBusinessList = res.data.userBusinessList;
						if(userBusinessList !=null) {
							if(userBusinessList.length>0) {
								//用户对应的仓库列表 [1][2][3]...
								userdepot =userBusinessList[0].value;
							}
						}
					}
					else {
						userBusinessList = null;
					}
				}
			});
		},
		//初始化系统仓库信息
		initSystemData_depot: function () {
			var config = getSystemConfig();
			var depotList = getSystemDepot();
			if(depotList !=null) {
				for(var i = 0 ;i < depotList.length;i++) {
					var depot = depotList[i];
					if(config && config.depotFlag == "1") {
						if(userdepot!=null) {
							if(userdepot.indexOf("["+depot.id+"]")!=-1) {
								if(depot.isDefault){
									defDepotId =  depot.id;
								}
								depotString = depotString + depot.id + ",";
							}
						}
					} else {
						if(depot.isDefault){
							defDepotId =  depot.id;
						}
					}
				}
				depotString = depotString.substring(0, depotString.length-1);
			}
		},
		//初始化供应商、客户、散户信息
		initSupplier: function () {
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
				}
			});
		},
		//初始化销售人员
		initSalesman: function () {
			$('#Salesman').combobox({
				url: "/person/getPersonByNumType?type=1",
				valueField:'id',
				textField:'name',
				multiple: true
			});
		},
		//初始化收入项目列表
		initOutItemList: function () {
			$.ajax({
				type:"get",
				url: "/inOutItem/findBySelect?type=out",
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
		},
		//初始化商品属性
		initMProperty: function () {
			$.ajax({
				type: "get",
				url: "/materialProperty/list",
				dataType: "json",
				data: ({
					search: JSON.stringify({
						name: ""
					}),
					currentPage: 1,
					pageSize: 100
				}),
				success: function (res) {
					if(res && res.code === 200){
						if(res.data && res.data.page) {
							var thisRows = res.data.page.rows;
							for (var i = 0; i < thisRows.length; i++) {
								if (thisRows[i].enabled) {
									mPropertyList += thisRows[i].nativeName + ",";
								}
							}
							if (mPropertyList) {
								mPropertyList = mPropertyList.substring(0, mPropertyList.length - 1);
							}
						}
					}
				},
				//此处添加错误处理
				error:function() {
					$.messager.alert('查询提示','查询信息异常，请稍后再试！','error');
					return;
				}
			});
		},
		//获取账户信息
		initSystemData_account: function () {
			$.ajax({
				type:"get",
				url: "/account/getAccount",
				//设置为同步
				async:false,
				dataType: "json",
				success: function (res) {
					if(res && res.code === 200) {
						if(res.data) {
							accountList = res.data.accountList;
							var options = "";
							if(accountList !=null){
								options = "<option value=''>(空)</option>";
								options += "<option value='many' class='many' data-manyAmount=''>多账户</option>";
								for(var i = 0 ;i < accountList.length;i++) {
									var account = accountList[i];
									options += '<option value="' + account.id + '" data-currentAmount="' + account.currentAmount + '">' + account.name + '</option>';
									if(account.isDefault) {
										defaultAccountId = account.id; //给账户赋值默认id
									}
								}
								$("#AccountId").empty().append(options);
							}
						}
					}
				}
			});
		},
		//防止表单提交重复
		initForm: function () {
			$('#depotHeadFM').form({
				onSubmit: function(){
					return false;
				}
			});
		},
		//查找库存的方法
		findStockNumById: function (depotId, meId, monthTime, currentRowDom, input, ratio, type) {
			var thisRatio = 1; //比例
			$.ajax({
				url: "/material/findByIdWithBarCode",
				type: "get",
				dataType: "json",
				data: {
					meId: meId,
					mpList: mPropertyList
				},
				success: function (rec) {
					if(rec && rec.code === 200) {
						var info = rec.data;
						var commodityUnit = info.commodityUnit; //商品单位
						var loadRatio = 1; //在单位输入框上面加载比例字段
						if(info.unit) { //如果存在计量单位信息
							loadRatio = 1;
						}
						else{
							var unitName = info.unitName;
							if(unitName) {
								thisRatio = unitName.substring(unitName.indexOf(":")+1).replace(")","");
								unitName = unitName.substring(0, unitName.indexOf("("));
							}
							var unitArr = unitName.split(",");
							var basicUnit = unitArr[0]; //基础单位
							var otherUnit = unitArr[1]; //副单位
							if(basicUnit==commodityUnit){ //基础单位等于选择的单位
								loadRatio = 1;
							}
							else if(otherUnit==commodityUnit){ //副单位等于选择的单位
								loadRatio = thisRatio;
							}
						}
						//查询库存
						$.ajax({
							type: "get",
							url: '/depotItem/findStockNumById',
							data:{
								depotId: depotId,
								mId: info.id
							},
							dataType: "json",
							success: function (res) {
								if(res && res.code === 200) {
									if (res.data) {
										var thisStock = res.data.stock;
										if (type == "select") { //选择下拉框的时候
											if (ratio != undefined && ratio != 1) {
												loadRatio = ratio;
											}
										}
										else if (type == "click") { //点击库存的时候
											if (ratio != undefined) {
												loadRatio = ratio;
											}
										}
										thisStock = (thisStock / loadRatio).toFixed(2);
										currentRowDom.find("[field='Stock']").find(input).val(thisStock).attr("data-stock", thisStock); //加载库存数据
									}
									else {
										currentRowDom.find("[field='Stock']").find(input).val(0).attr("data-stock", 0); //加载库存数据
									}
									currentRowDom.find("[field='Stock']").find(input).prop("readonly", "readonly"); //设置库存数据为只读
								}
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
		},
		getInfoByBarCode: function(barCode){
			var materialExtendId = "";
			$.ajax({
				type: "get",
				url: '/materialsExtend/getInfoByBarCode',
				data:{
					barCode: barCode
				},
				async:false,
				dataType: "json",
				success: function (res) {
					if(res && res.code === 200) {
						materialExtendId = res.data.id;
					}
				},
				error: function () {
					$.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
				}
			});
			return materialExtendId;
		},
		//优惠率、合计的统计方法
		statisticsFun: function (body,UnitPrice,OperNumber,footer,taxRate) {
			var TotalPrice = 0;
			var taxLastMoneyTotal = 0;
			//金额的合计
			body.find("[field='AllPrice']").each(function(){
				if($(this).find("input").val()!==""){
					TotalPrice = TotalPrice + parseFloat($(this).find("input").val().toString());
				}
			});
			footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(2)); //金额的合计
			//价税合计的总计
			body.find("[field='TaxLastMoney']").each(function(){
				if($(this).find("input").val()!==""){
					taxLastMoneyTotal = taxLastMoneyTotal + (parseFloat($(this).find("input").val().toString())-0);
				}
			});
			footer.find("[field='TaxLastMoney']").find("div").text((taxLastMoneyTotal).toFixed(2)); //价税合计的页脚总计
			var discount = $("#Discount").val(); //优惠率
			var discountMoney = (taxLastMoneyTotal*discount/100).toFixed(2);
			$("#DiscountMoney").val(discountMoney);//优惠金额
			var discountLastMoney = (taxLastMoneyTotal*(1-discount/100)).toFixed(2);
			$("#DiscountLastMoney").val(discountLastMoney);//优惠后金额
			if($("#AccountId").val()!=="many"){
				var otherMoney = $("#OtherMoney").val()-0;
				var changeAmount = discountLastMoney-0+otherMoney;
				$("#ChangeAmount").val(changeAmount.toFixed(2)); //本次付、收款
			}
			var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
			$("#Debt").val((discountLastMoney-0+otherMoney-changeAmountNum).toFixed(2)); //本次欠款

			if(listSubType == "零售" || listSubType == "零售退货") {
				$("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
				$("#backAmount").val(0);
			}
		},
		//分页信息处理
		ininPager: function () {
			var self = this;
			try {
				var opts = $("#tableData").datagrid('options');
				var pager = $("#tableData").datagrid('getPager');
				pager.pagination({
					onSelectPage:function(pageNum, pageSize) {
						opts.pageNumber = pageNum;
						opts.pageSize = pageSize;
						pager.pagination('refresh', {
							pageNumber:pageNum,
							pageSize:pageSize
						});
						self.showDepotHeadDetails(pageNum,pageSize);
					}
				});
			}
			catch (e) {
				$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
			}
		},
		//删除单据信息
		deleteDepotHead: function (depotHeadID, thisOrganId, totalPrice, status) {
			if(status == "1" || status == "2") {
				$.messager.alert('删除提示','已审核和已转的单据不能删除！','warning');
				return;
			}
			$.messager.confirm('删除确认','确定要删除此单据信息吗？',function(r) {
				if (r) {
					$.ajax({
						type:"post",
						url: "/depotHead/deleteDepotHeadAndDetail",
						dataType: "json",
						data:{
							id: depotHeadID
						},
						success: function (res) {
							if(res && res.code == 200) {
								$("#searchBtn").click();
							} else {
								$.messager.alert('删除提示', '删除单据信息失败，请稍后再试！', 'error');
							}
						},
						//此处添加错误处理
						error:function() {
							$.messager.alert('删除提示','删除单据信息异常，请稍后再试！','error');
							return;
						}
					});

					//更新会员的预收款信息
					if(listSubType === "零售") {
						$.ajax({
							type:"post",
							url: "/supplier/updateAdvanceIn",
							dataType: "json",
							data:{
								supplierId: thisOrganId, //会员id
								advanceIn: totalPrice  //删除时同时返还用户的预付款
							},
							success: function(res){
								if(res && res.code === 200) {
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
		},
		//批量删除单据信息
		batDeleteDepotHead: function () {
			var row = $('#tableData').datagrid('getChecked');
			if(row.length == 0) {
				$.messager.alert('删除提示','没有记录被选中！','info');
				return;
			}
			if(row.length > 0) {
				$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条单据信息吗？',function(r) {
					if (r) {
						var ids = "";
						for (var i = 0; i < row.length; i++) {
							if (i == row.length - 1) {
								if (row[i].status == 0) {
									ids += row[i].id;
								}
								break;
							}
							ids += row[i].id + ",";
						}
						if (ids) {
							//批量更新会员的预收款信息
							for (var i = 0; i < row.length; i++) {
								if (listSubType === "零售") {
									$.ajax({
										type: "post",
										url: "/supplier/updateAdvanceIn",
										dataType: "json",
										data: {
											supplierId: row[i].organId, //会员id
											advanceIn: row[i].totalPrice  //删除时同时返还用户的预付款
										},
										success: function (res) {
											if (res && res.code === 200) {
												//保存会员预收款成功
											}
										},
										error: function () {
											$.messager.alert('提示', '保存信息异常，请稍后再试！', 'error');
											return;
										}
									});
								}
							}
							//批量删除
							$.ajax({
								type: "post",
								url: "/depotHead/batchDeleteDepotHeadAndDetail",
								dataType: "json",
								async: false,
								data: ({
									ids: ids
								}),
								success: function (res) {
									if (res && res.code === 200) {
										$("#searchBtn").click();
										$(":checkbox").attr("checked", false);
									} else {
										$.messager.alert('删除提示', '删除单据信息失败，请稍后再试！', 'error');
									}
								},
								//此处添加错误处理
								error: function () {
									$.messager.alert('删除提示', '删除单据信息异常，请稍后再试！', 'error');
									return;
								}
							});
						} else {
							$.messager.alert('删除提示','没有能删除的单据！','warning');
						}
					}
				});
			}
		},
		//批量审核|反审核
		setStatusFun: function (status) {
			var row = $('#tableData').datagrid('getChecked');
			if(row.length == 0) {
				$.messager.alert('提示','没有记录被选中！','info');
				return;
			}
			if(row.length > 0) {
				$.messager.confirm('确认','确定要操作选中的' + row.length + '条信息吗？',function(r) {
					if (r) {
						var ids = "";
						for(var i = 0;i < row.length; i ++) {
							if(i == row.length-1) {
								if(row[i].status != "2") {
									ids += row[i].id;
								}
								break;
							}
							ids += row[i].id + ",";
						}
						if(ids) {
							$.ajax({
								type:"post",
								url: "/depotHead/batchSetStatus",
								dataType: "json",
								async :  false,
								data: ({
									status: status,
									depotHeadIDs : ids
								}),
								success: function (res) {
									if(res && res.code === 200) {
										$("#searchBtn").click();
										$(":checkbox").attr("checked", false);
									} else {
										$.messager.alert('提示', '操作信息失败，请稍后再试！', 'error');
									}
								},
								//此处添加错误处理
								error:function() {
									$.messager.alert('提示','操作信息异常，请稍后再试！','error');
									return;
								}
							});
						} else {
							$.messager.alert('提示','没有能操作的单据！','warning');
						}
					}
				});
			}
		},
		//生成单据编号
		buildNumber: function () {
			$.ajax({
				type: "get",
				url: "/depotHead/buildNumber",
				success:function(res){
					if(res && res.code === 200){
						var obj = res.data;
						var defaultNumber = obj.DefaultNumber;
						var newNumber = amountNum + defaultNumber;
						$("#Number").val(newNumber).attr("data-defaultNumber",newNumber);
					}
				},
				error:function(){
					$.messager.alert('提示','生成单据编号失败！','error');
				}
			});
		},
		//绑定操作事件
		bindEvent: function () {
			var self = this;
			//重置按钮
			$("#searchResetBtn").unbind().bind({
				click:function(){
					$("#searchNumber").textbox("clear");
					$("#searchMaterial").textbox("clear");
					$("#searchBeginTime").datebox("clear");
					$("#searchEndTime").datebox("clear");
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
						type:"get",
						url: "/depotHead/checkIsNumberExist",
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

			//打印单据
			$("#printDepotHeadShow").off("click").on("click",function(){
				var tableString = $("#depotHeadDlgShow").html();
				localStorage.setItem("tableString",tableString);
				window.open("../../js/print/print_form.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
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
				if(k == "13"&&(obj.id=="searchNumber"||obj.id=="searchMaterial"))
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
					var otherMoney = $("#OtherMoney").val()-0;
					var changeAmount = discountLastMoney-0+otherMoney;
					$("#ChangeAmount").val(changeAmount.toFixed(2)); //本次付、收款
				}
				var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
				$("#Debt").val((discountLastMoney-0+otherMoney-changeAmountNum).toFixed(2)); //本次欠款
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
					var otherMoney = $("#OtherMoney").val()-0;
					var changeAmount = discountLastMoney-0+otherMoney;
					$("#ChangeAmount").val(changeAmount.toFixed(2)); //本次付、收款
				}
				var changeAmountNum = $("#ChangeAmount").val()-0; //本次付款或者收款
				$("#Debt").val((discountLastMoney-0+otherMoney-changeAmountNum).toFixed(2)); //本次欠款
			});

			//付款、收款输入框事件
			$("#ChangeAmount").off("keyup").on("keyup",function(){
				var discountLastMoney = $("#DiscountLastMoney").val()-0;
				var otherMoney = $("#OtherMoney").val()-0;
				var changeAmount = $(this).val();
				var debtMoney  = (discountLastMoney + otherMoney - changeAmount).toFixed(2);
				$("#Debt").val(debtMoney); //本次欠款
			});

			//多账户结算窗口弹出事件
			function depotHeadAccountDlgFun(){
				$('#depotHeadAccountDlg').dialog('open').dialog('setTitle','<img src="/js/easyui/themes/icons/pencil.png"/>&nbsp;多账户结算');
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
					var otherMoney = $("#OtherMoney").val()-0;
					$("#Debt").val((discountLastMoneyNum+otherMoney-accountMoneyTotal).toFixed(2)); //本次欠款
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

			//点击其它费用的事件
			$(".other-money-ico").off("click").on("click",function(){
				$('#otherMoneyDlg').dialog('open').dialog('setTitle','<img src="/js/easyui/themes/icons/pencil.png"/>&nbsp;其它费用');
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
							var itemMoneyObj = {};
							itemMoneyObj.otherId = thisId;
							itemMoneyObj.otherMoney = thisMoney;
							itemMoneyArr.push(itemMoneyObj);
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
					$("#OtherMoney").val(otherMoneyTotal); //给其它费用赋值
					var discountLastMoney = $("#DiscountLastMoney").val()-0;
					var changeAmount = (discountLastMoney + otherMoneyTotal).toFixed(2);
					$("#ChangeAmount").val(changeAmount); //付款或者收款
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

				//给弹窗赋值-其它费用数据
				var itemArr = $("#OtherMoney").attr("data-itemArr");
				if(itemArr) {
					itemArr = JSON.parse(itemArr);
					var itemMoneyArr = $("#OtherMoney").attr("data-itemMoneyArr");
					itemMoneyArr = JSON.parse(itemMoneyArr);
					$("#otherMoneyDlg .money-dlg .money-content-tmp").each(function(){
						var index = $(this).attr("data-index");
						$(this).find(".money-id-dlg").val(itemArr[index]);
						if(itemMoneyArr[index]!="undefined"){
							for(var k =0;k<itemMoneyArr.length; k++) {
								if (itemMoneyArr[k].otherId == itemArr[index]) {
									$(this).find(".other-money-dlg").val(itemMoneyArr[k].otherMoney);
								}
							}

						}
					});
				}
				$("#otherMoneyTotalDlg").text($("#OtherMoney").val());
			});
		},
		//查询单据列表信息
		showDepotHeadDetails: function (pageNo,pageSize) {
			var materialParam = $.trim($("#searchMaterial").val());
			var beginTime = $.trim($("#searchBeginTime").val());
			var endTime = $.trim($("#searchEndTime").val());
			if(beginTime) {
				beginTime = beginTime + ' 00:00:00';
			}
			if(endTime) {
				endTime = endTime + ' 23:59:59';
			}
			$.ajax({
				type: "get",
				url: "/depotHead/list",
				dataType: "json",
				data: ({
					search: JSON.stringify({
						type: listType,
						subType: listSubType,
						roleType: roleType,
						status: "",
						number: $.trim($("#searchNumber").val()),
						beginTime: beginTime,
						endTime: endTime,
						materialParam: materialParam,
						depotIds: depotString
					}),
					currentPage: pageNo,
					pageSize: pageSize
				}),
				success: function (res) {
					if(res && res.code === 200){
						if(res.data && res.data.page) {
							$("#tableData").datagrid('loadData', res.data.page);
						}
					}
				},
				//此处添加错误处理
				error: function () {
					$.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
					return;
				}
			});
		},
		//自动计算事件
		autoReckon: function () {
			var self = this;
			var inputDom = $("#depotHeadFM .panel.datagrid .datagrid-view2 .datagrid-body");
			var appendDom = $("#depotHeadDlg #append");
			autoJumpNextInput(inputDom, appendDom); //敲回车键自动跳转到下一个文本框
			var body =$("#depotHeadFM .datagrid-view2 .datagrid-body");
			var footer =$("#depotHeadFM .datagrid-view2 .datagrid-footer");
			var input = "input[type=text]";
			body.find(".datagrid-row").find("input").off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
			});
			body.find("[field='MaterialExtendId']").find(".textbox-text").focus().select(); //默认选择商品框
			//点击商品下拉框，自动加载数量、单价、金额
			body.find("[field='name'],[field='standard'],[field='model'],[field='materialOther'],[field='Stock'],[field='Unit']")
				.find(input).prop("readonly","readonly");
			//点击商品名称
			body.find("[field='MaterialExtendId']").find(input).next().off("mouseup").on("mouseup",function(){
				var that = $(this);
				var meId = that.find('input.textbox-text').val()-0;
				if(myIsNaN(meId)) {
					$.ajax({
						type: "get",
						url: '/material/getMaterialByMeId',
						data: {
							meId: meId,
							mpList: mPropertyList
						},
						dataType: "json",
						success: function (res) {
							if (res && res.MaterialName) {
								that.find('input.textbox-value').val(meId);
								that.find('input.textbox-text').val(res.MaterialName);
							}
						}
					});
				}
			});
			//单击删除按钮
			body.find("[field='op']").find("img").off("click").on("click",function() {
				$(this).closest(".datagrid-cell").click(); //点击操作
				var row = $('#materialData').datagrid('getChecked');
				$('#materialData').datagrid('deleteRow', $('#materialData').datagrid("getRowIndex", row[0]));
				self.statisticsFun(body,0,0,footer,0);
			});
			//修改数量，自动计算金额和合计，另外计算含税单价、税额、价税合计
			body.find("[field='OperNumber']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var UnitPrice = rowDom.find("[field='UnitPrice']").find(input).val(); //单价
				var taxRate = rowDom.find("[field='TaxRate']").find(input).val(); //税率
				var OperNumber =$(this).val()-0; //数量
				rowDom.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改单价，自动计算金额和合计
			body.find("[field='UnitPrice']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var UnitPrice =$(this).val()-0; //单价
				var taxRate = rowDom.find("[field='TaxRate']").find(input).val(); //税率
				var OperNumber = rowDom.find("[field='OperNumber']").find(input).val(); //数量
				rowDom.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改含税单价，自动计算单价、金额、税额、价税合计和合计
			body.find("[field='TaxUnitPrice']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var TaxUnitPrice =$(this).val()-0; //含税单价
				var taxRate = rowDom.find("[field='TaxRate']").find(input).val(); //税率
				var UnitPrice = TaxUnitPrice/(1+taxRate/100); //计算单价
				rowDom.find("[field='UnitPrice']").find(input).val((UnitPrice).toFixed(2)); //单价
				var OperNumber = currentRowDom.find("[field='OperNumber']").find(input).val(); //数量
				rowDom.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改金额，自动计算单价、税额、价税合计和合计
			body.find("[field='AllPrice']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var OperNumber = rowDom.find("[field='OperNumber']").find(input).val(); //数量
				var taxRate = rowDom.find("[field='TaxRate']").find(input).val(); //税率
				var AllPrice =$(this).val()-0; //金额
				var UnitPrice = (AllPrice/OperNumber).toFixed(2);
				rowDom.find("[field='UnitPrice']").find(input).val(UnitPrice); //单价
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改税率，自动计算含税单价、税额、价税合计和合计
			body.find("[field='TaxRate']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var taxRate =$(this).val()-0; //税率
				var OperNumber = rowDom.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = rowDom.find("[field='UnitPrice']").find(input).val(); //单价
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改税额，自动计算税率、含税单价、价税合计和合计
			body.find("[field='TaxMoney']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var taxMoney =$(this).val()-0; //税额
				var AllPrice = rowDom.find("[field='AllPrice']").find(input).val(); //金额
				var taxRate = taxMoney/AllPrice*100; //税率
				var OperNumber = rowDom.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = rowDom.find("[field='UnitPrice']").find(input).val(); //单价
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxRate']").find(input).val((taxRate).toFixed(2)); //税率
				rowDom.find("[field='TaxLastMoney']").find(input).val((UnitPrice*OperNumber*(1+taxRate/100)).toFixed(2)); //价税合计
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			//修改价税合计，自动计算税率、含税单价、税额和合计
			body.find("[field='TaxLastMoney']").find(input).off("keyup").on("keyup",function(){
				editIndex = $(this).closest(".datagrid-row").attr("datagrid-row-index");
				var rowDom = body.find(".datagrid-row").eq(editIndex);
				var taxLastMoney =$(this).val()-0; //价税合计
				var AllPrice = rowDom.find("[field='AllPrice']").find(input).val(); //金额
				var taxRate = (taxLastMoney-AllPrice)/AllPrice*100; //税率
				var OperNumber = rowDom.find("[field='OperNumber']").find(input).val(); //数量
				var UnitPrice = rowDom.find("[field='UnitPrice']").find(input).val(); //单价
				rowDom.find("[field='TaxUnitPrice']").find(input).val((UnitPrice*(1+taxRate/100)).toFixed(2)); //含税单价
				rowDom.find("[field='TaxRate']").find(input).val((taxRate).toFixed(2)); //税率
				rowDom.find("[field='TaxMoney']").find(input).val((UnitPrice*OperNumber*(taxRate/100)).toFixed(2)); //税额
				self.statisticsFun(body,UnitPrice,OperNumber,footer,taxRate);
			});
			setTimeout(function(){
				var body =$("#depotHeadFM .datagrid-view2 .datagrid-body");
				var input = "input[type=text]";
				//默认税率为0
				var taxRateDom = body.find(".datagrid-row").eq(editIndex).find("[field='TaxRate']").find(input);
				if(taxRateDom.val() == "") {
					taxRateDom.val(0);
				}
				//在商品类型加载 组装件、普通子件
				var mType = body.find(".datagrid-row").eq(editIndex).find("[field='MType']");
				var rowListLength = mType.find(input).closest(".datagrid-row").attr("datagrid-row-index");
				var mTypeValue = "组合件";
				if(rowListLength > 0){
					mTypeValue = "普通子件";
				}
				if(listSubType == "组装单" || listSubType == "拆卸单"){
					mType.find(input).val(mTypeValue).prop("readonly","readonly");
				}
			},100);
		},
		//新增明细
		append: function () {
			$('#materialData').datagrid('appendRow', {DepotId:defDepotId});
			editIndex = $('#materialData').datagrid('getRows').length - 1;
			$('#materialData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			this.autoReckon();
		},
		//新增仓库
		appendDepot: function () {
			$('#depotDlg').dialog('open').dialog('setTitle', '<img src="/js/easyui/themes/icons/edit_add.png"/>&nbsp;增加仓库信息');
			$(".window-mask").css({width: webW, height: webH});
			$('#depotFM').form('clear');
			bindDepotEvent();
		},
		//扫码
		switchToBarCode: function () {
			if($('#sweepBarCode').css('display') == "none") {
				$("#sweepBarCode").show().val("");
				$("#sweepBarCode").off("keyup").on("keyup",function(e){
					var evt = window.event || e;
					if (evt.keyCode == 13) {
						var meId = inOutService.getInfoByBarCode($("#sweepBarCode").val());
						var rec = {}; rec.Id = meId;
						var body =$("#depotHeadFM .datagrid-view2 .datagrid-body");
						var rowDom = body.find(".datagrid-row").eq(editIndex);
						var materialName = rowDom.find("[field='name']").find("input[type=text]").val();
						if(materialName){
							inOutService.append(); //新增行
						}
						inOutService.materialSelect(rec, getNowFormatMonth());
					}
				});
			} else {
				$("#sweepBarCode").hide();
			}
		},
		//新增商品
		appendMaterial: function () {
			js.addTabPage(null, "商品信息", "/pages/materials/material.html");
		},
		//判断明细
		CheckData: function (type) {
			var change = $('#materialData').datagrid('getChanges').length;
			if(type =="add" && !change) {
				$.messager.alert('提示','请输入明细信息！','warning');
				return false;
			}
			var row = $('#materialData').datagrid('getRows');
			if(!row.length){
				$.messager.alert('提示',"请输入明细信息！",'info');
				return false;
			}
			var totalRowNum = "";
			for (var i = 0; i < row.length; i++) {
				if (row[i].DepotId == "" || row[i].barCode == "" || row[i].OperNumber == "" || row[i].UnitPrice === "" || row[i].AllPrice === "") {
					totalRowNum += (i + 1) + "、";
				}
			}
			if (totalRowNum != "") {
				var totalRowNum = totalRowNum.substring(0, totalRowNum.length - 1);
				$.messager.alert('提示',"第" + totalRowNum + "行数据填写不完整！",'info');
				return false;
			}
			return true;
		},
		//商品选择列表
		initMaterialSelectData: function(){
			var self = this;
			$('#materialSelectData').datagrid({
				height: 480,
				rownumbers: false,
				//动画效果
				animate: false,
				pagination: true,
				//选中单行
				singleSelect: true,
				collapsible: false,
				//交替出现背景
				striped: true,
				pageSize: 10,
				pageList: initPageNum,
				columns: [[
					{field: 'id',width:35,align:"center",checkbox:true},
					{field: 'mBarCode', title: '条码', width: 120},
					{field: 'name', title: '名称', width: 140},
					{field: 'categoryName', title: '类别', width: 80},
					{field: 'standard', title: '规格', width: 80},
					{field: 'model', title: '型号', width: 80},
					{field: 'unit', title: '单位', width: 60},
					{field: 'stock', title: '库存', width: 50},
					{field: 'expand', title: '扩展信息', width: 80}
				]],
				onBeforeLoad: function(param){
					param.mpList = mPropertyList; //商品属性
				},
				onDblClickRow: function (index, row) {
					var monthTime = getNowFormatMonth();
					$('#materialSelectDlg').dialog('close');
					self.materialSelect(row,monthTime);
				}
			});
		},
		//商品选择列表-分页
		initMaterialSelectPager: function(depotId){
			var self = this;
			try {
				var opts = $("#materialSelectData").datagrid('options');
				var pager = $("#materialSelectData").datagrid('getPager');
				pager.pagination({
					onSelectPage: function (pageNum, pageSize) {
						opts.pageNumber = pageNum;
						opts.pageSize = pageSize;
						pager.pagination('refresh', {
							pageNumber: pageNum,
							pageSize: pageSize
						});
						self.showMaterialSelect(depotId, pageNum, pageSize);
					}
				});
			}
			catch (e) {
				$.messager.alert('异常处理提示', "分页信息异常 :  " + e.name + ": " + e.message, 'error');
			}
		},
		//商品选择列表-查询
		showMaterialSelect: function(depotId, pageNo, pageSize){
			$.ajax({
				type: "get",
				url: "/material/findBySelect",
				dataType: "json",
				data: ({
					categoryId: $("#searchMaterialCategory").combotree("getValue"),
					q: $("#searchBarCode").textbox("getValue"),
					mpList: mPropertyList,
					depotId: depotId,
					page: pageNo,
					rows: pageSize
				}),
				success: function (res) {
					if(res) {
						$("#materialSelectData").datagrid('loadData', res);
					}
				},
				//此处添加错误处理
				error: function () {
					$.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
					return;
				}
			});
		},
		//商品选择列表-事件
		materialSelectFun: function(depotId){
			var self = this;
			var monthTime = getNowFormatMonth();
			$("#searchBarCode").textbox("setValue","");
			$("#searchMaterialBtn").off("click").on("click",function(){
				self.showMaterialSelect(depotId,1,initPageSize);
				var opts = $("#materialSelectData").datagrid('options');
				var pager = $("#materialSelectData").datagrid('getPager');
				opts.pageNumber = 1;
				opts.pageSize = initPageSize;
				pager.pagination('refresh', {
					pageNumber: 1,
					pageSize: initPageSize
				});
			});
			//重置按钮
			$("#searchMaterialResetBtn").unbind().bind({
				click: function () {
					$("#searchMaterialCategory").combotree("setValue","");
					$("#searchBarCode").textbox("setValue","");
					$("#searchMaterialBtn").click();
				}
			});
			$("#appendMaterial").off("click").on("click",function(){
				js.addTabPage(null, "商品信息", "/pages/materials/material.html");
			});
			$("#checkMaterial").off("click").on("click",function(){
				var rowData = $("#materialSelectData").datagrid('getChecked')[0];
				$('#materialSelectDlg').dialog('close');
				self.materialSelect(rowData,monthTime);
			});
			$("#searchMaterialBtn").click();
		},
		//选择商品
		materialSelect: function(rec,monthTime) {
			var self = this;
			var body,footer,input; //定义表格和文本框
			var ratio = 1; //比例-名称专用
			var loadRatio = 1; //在单位输入框上面加载比例字段
			if(rec) {
				var meId = rec.Id;
				$.ajax({
					url: "/material/findByIdWithBarCode",
					type: "get",
					dataType: "json",
					data: {
						meId: meId,
						mpList: mPropertyList
					},
					success: function (res) {
						if(res && res.code === 200) {
							var info = res.data;
							var commodityDecimal = info.commodityDecimal-0; //零售价
							var purchaseDecimal = info.purchaseDecimal-0; //采购价
							var wholesaleDecimal = info.wholesaleDecimal-0; //销售价
							var lowDecimal = info.lowDecimal-0; //最低售价
							var commodityUnit = info.commodityUnit; //商品单位
							body =$("#depotHeadFM .datagrid-view2 .datagrid-body");
							footer =$("#depotHeadFM .datagrid-view2 .datagrid-footer");
							input = "input[type=text]";
							var currentRowDom = body.find(".datagrid-row").eq(editIndex);
							currentRowDom.find("[field='barCode']").find(".datagrid-editable-input").textbox("setValue",info.mBarCode).prop("readonly","readonly");
							currentRowDom.find("[field='name']").find(input).val(info.name).prop("readonly","readonly");
							currentRowDom.find("[field='standard']").find(input).val(info.standard).prop("readonly","readonly");
							currentRowDom.find("[field='model']").find(input).val(info.model).prop("readonly","readonly");
							currentRowDom.find("[field='materialOther']").find(input).val(info.materialOther).prop("readonly","readonly");
							currentRowDom.find("[field='Unit']").find(input).prop("readonly","readonly"); //设置计量单位为只读
							currentRowDom.find("[field='Unit']").find(input).val(commodityUnit); //设置单位
							if(info.unit){ //如果存在计量单位信息
								ratio = 1; //重置比例为1
								loadRatio = ratio;
							}
							else {
								var unitName = info.unitName;
								if(unitName) {
									ratio = unitName.substring(unitName.indexOf(":")+1).replace(")",""); //给比例赋值
									unitName = unitName.substring(0, unitName.indexOf("("));
								}
								var unitArr = unitName.split(",");
								var basicUnit = unitArr[0]; //基础单位
								var otherUnit = unitArr[1]; //副单位
								if(basicUnit==commodityUnit){ //基础单位等于选择的单位
									loadRatio = 1;
								}
								else if(otherUnit==commodityUnit){ //副单位等于选择的单位
									loadRatio = ratio;
								}
							}
							var detailPrice = 0; //明细列表-单价
							if(listSubType == "零售" || listSubType == "零售退货") {
								detailPrice = commodityDecimal;
							}
							else if(listTitle == "采购订单列表" || listTitle == "采购入库列表" || listTitle == "采购退货列表" || listTitle == "其它入库列表") {
								detailPrice = purchaseDecimal;
							}
							else if(listTitle == "销售订单列表" || listTitle == "销售出库列表" || listTitle == "销售退货列表" || listTitle == "其它出库列表" || listTitle == "调拨出库列表") {
								detailPrice = wholesaleDecimal;
							}
							//单价和总价赋值
							if(!detailPrice) {
								detailPrice = 0;
							}
							var operNumber = 1;
							currentRowDom.find("[field='OperNumber']").find(input).val(operNumber); //数量初始化
							if($('#sweepBarCode').css('display') == "none") {
								currentRowDom.find("[field='OperNumber']").find(input).focus().select();
							} else {
								$("#sweepBarCode").val("").focus().select();
							}
							currentRowDom.find("[field='UnitPrice']").find(input).val(detailPrice);
							currentRowDom.find("[field='AllPrice']").find(input).val(detailPrice);
							var taxRate = currentRowDom.find("[field='TaxRate']").find(input).val()-0; //获取税率
							currentRowDom.find("[field='TaxUnitPrice']").find(input).val((detailPrice*(1+taxRate/100)).toFixed(2));  //含税单价
							currentRowDom.find("[field='TaxMoney']").find(input).val((detailPrice*(taxRate/100)).toFixed(2));  //税额
							currentRowDom.find("[field='TaxLastMoney']").find(input).val((detailPrice*(1+taxRate/100)).toFixed(2));  //价税合计
							self.statisticsFun(body,detailPrice,1,footer,taxRate);

							//查询库存信息
							var depotId = currentRowDom.find("[field='DepotId']").find(".textbox-value").val();
							if(depotId) {
								var type = "select"; //type 类型：点击 click，选择 select
								self.findStockNumById(depotId, meId, monthTime, currentRowDom, input, loadRatio, type);
							}
						}
					},
					error: function() {
						$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
					}
				});
			}
		},
		//结束全部的编辑
		endAllEdit: function(){
			var rowLen = $("#materialData").datagrid('getRows').length;
			for(var i=0; i<rowLen; i++){
				$('#materialData').datagrid('endEdit', i);
			}
		},
		//新增单据主表及单据子表
		addDepotHeadAndDetail: function (url,infoStr) {
			var self = this;
			if(pageType=="skip") {
				sessionStorage.removeItem("rowInfo");
			}
			this.endAllEdit();
			var rows = $("#materialData").datagrid('getRows');
			$.ajax({
				type:"post",
				url: url,
				contentType:"application/json",
				dataType: "json",
				async :  false,
				data: JSON.stringify({
					info:infoStr,
					rows: JSON.stringify(rows)
				}),
				success: function (tipInfo){
					if(tipInfo){
						if(tipInfo.code!=200){
							$.messager.alert('提示', tipInfo.msg, 'warning');
							for(var i=0; i<rows.length; i++){
								$('#materialData').datagrid('selectRow', i).datagrid('beginEdit', i);
								self.autoReckon();
							}
							return;
						}
						$.messager.alert('提示','保存成功！','info');
						$('#depotHeadDlg').dialog('close');
						var opts = $("#tableData").datagrid('options');
						self.showDepotHeadDetails(opts.pageNumber,opts.pageSize);
					}else {
						$.messager.show({
							title: '错误提示',
							msg: '保存信息失败，请稍后重试!'
						});
					}
				},
				//此处添加错误处理
				error:function() {
					$.messager.alert('提示','保存信息异常，请稍后再试！','error');
					return;
				}
			});
		},
		//修改单据主表及单据子表
		updateDepotHeadAndDetail: function (url,infoStr,preTotalPrice) {
			var self = this;
			this.endAllEdit();
			var rows = $("#materialData").datagrid('getRows');
			$.ajax({
				type:"post",
				url: url,
				contentType:"application/json",
				dataType: "json",
				async :  false,
				data: JSON.stringify({
					id:url.substring(url.lastIndexOf("?id=")+4,url.length),
					info:infoStr,
					rows: JSON.stringify(rows),
					preTotalPrice:preTotalPrice
				}),
				success: function (tipInfo){
					if(tipInfo){
						if(tipInfo.code!=200){
							$.messager.alert('提示', tipInfo.msg, 'warning');
							for(var i=0; i<rows.length; i++){
								$('#materialData').datagrid('selectRow', i).datagrid('beginEdit', i);
								self.autoReckon();
							}
							return;
						}
						$.messager.alert('提示','保存成功！','info');
						$('#depotHeadDlg').dialog('close');
						var opts = $("#tableData").datagrid('options');
						self.showDepotHeadDetails(opts.pageNumber,opts.pageSize);
						$('#materialData').datagrid('acceptChanges');
					}else {
						$.messager.show({
							title: '错误提示',
							msg: '保存信息失败，请稍后重试!'
						});
					}
				},
				//此处添加错误处理
				error:function() {
					$.messager.alert('提示','保存信息异常，请稍后再试！','error');
					return;
				}
			});
		}
	}