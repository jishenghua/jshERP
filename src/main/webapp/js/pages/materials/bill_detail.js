    var accountList; //账户列表
    var listSubType = ""; //采购 销售等
    var depotHeadID = 0; //主表id
    var mPropertyList = ""; //商品属性列表
    var outItemList; //支出项目列表
    var otherColumns = true; //明细中的‘别名’列是否显示
    var payTypeTitle = "";//收入 支出
    var itemType = true; //隐藏当前列
    var moneyType = true; //隐藏当前列
    $(function() {
        initSystemData_account(); //获取账户信息
        initMProperty(); //初始化商品属性
        initOutItemList(); //初始化支出项目
        initialize();//初始化系统基础信息
    });
    //获取账户信息
    function initSystemData_account(){
        $.ajax({
            type:"post",
            url: path + "/account/getAccount.action",
            //设置为同步
            async:false,
            dataType: "json",
            success: function (systemInfo) {
                accountList = systemInfo.showModel.map.accountList;
                var msgTip = systemInfo.showModel.msgTip;
                if(msgTip == "exceptoin") {
                    $.messager.alert('提示','查找账户信息异常,请与管理员联系！','error');
                    return;
                }
            }
        });
    }
    //初始化商品属性
    function initMProperty(){
        $.ajax({
            type: "post",
            url: path + "/materialProperty/findBy.action",
            dataType: "json",
            //设置为同步
            async:false,
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
    function initialize() {
    	var url = location.href; //获取浏览器地址
    	var params = url.substring(url.indexOf("?")+1);
    	var number = params.substring(params.indexOf("n=")+2, params.indexOf("&"));
    	var billType = "";
        var listSubType = params.substring(params.indexOf("&type=")+6);
        if(listSubType) {
            listSubType = decodeURI(listSubType);
        }
        if(listSubType == "采购入库") {
            billType = "material";
            $("#bill .purchase_in").show();
        }
        else if(listSubType == "采购退货") {
            billType = "material";
            $("#bill .purchase_back").show();
        }
        else if(listSubType == "销售出库") {
            billType = "material";
            $("#bill .sale_out").show();
        }
        else if(listSubType == "销售退货") {
            billType = "material";
            $("#bill .sale_back").show();
        }
        else if(listSubType == "零售出库") {
            billType = "material";
            $("#bill .retail_out").show();
        }
        else if(listSubType == "零售退货入库") {
            billType = "material";
            $("#bill .retail_back").show();
        }
        else if(listSubType == "其它入库") {
            billType = "material";
            $("#bill .other_in").show();
        }
        else if(listSubType == "其它出库") {
            billType = "material";
            $("#bill .other_out").show();
        }
        else if(listSubType == "调拨出库") {
            billType = "material";
            $("#bill .allocation_out").show();
        }
        else if(listSubType == "礼品充值") {
            billType = "material";
            $("#bill .gift_recharge").show();
        }
        else if(listSubType == "礼品销售") {
            billType = "material";
            $("#bill .gift_out").show();
        }
        else if(listSubType == "收入") {
            billType = "account";
            payTypeTitle = "收入项目";
            itemType = false; //显示当前列
            moneyType = true; //隐藏当前列
            $("#bill .item_in").show();
        }
        else if(listSubType == "支出") {
            billType = "account";
            payTypeTitle = "支出项目";
            itemType = false; //显示当前列
            moneyType = true; //隐藏当前列
            $("#bill .item_out").show();
        }
        else if(listSubType == "收款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .money_in").show();
        }
        else if(listSubType == "付款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .money_out").show();
        }
        else if(listSubType == "转账") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .giro").show();
        }
        else if(listSubType == "收预付款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .advance_in").show();
        }

        //如果是进货、销售
        if(billType == "material"){
            $.ajax({
                url: path + "/depotHead/getDetailByNumber.action",
                data: {
                    Number: number
                },
                type: "get",
                success: function (res) {
                    if(res){
                        var data = JSON.parse(res);
                        var manyAccountMoney = 0; //多账户合计-零售
                        if(data.AccountName){
                            $("#bill .AccountIdShow").text(data.AccountName); //结算账户
                        }
                        else if(data.AccountIdList && data.AccountMoneyList) {
                            var accountArr = data.AccountIdList; //账户id列表
                            var accountMoneyArr = data.AccountMoneyList; //账户金额列表
                            var accountIdShow = "";
                            for (var j = 0; j < accountArr.length; j++) {
                                if (accountList != null) {
                                    for (var i = 0; i < accountList.length; i++) {
                                        var account = accountList[i];
                                        if (accountArr[j] == account.id) {
                                            var currentAccountMoney = accountMoneyArr[j] - 0;
                                            if (currentAccountMoney < 0) {
                                                currentAccountMoney = 0 - currentAccountMoney;
                                            }
                                            accountIdShow = accountIdShow + account.name + "(" + currentAccountMoney + "元) ";
                                            manyAccountMoney += accountMoneyArr[j] - 0; //多账户合计-零售
                                        }
                                    }
                                }
                            }
                            $("#bill .AccountIdShow").text(accountIdShow);
                        }
                        $("#bill .OrganIdShow").text(data.OrganName);
                        $("#bill .OperTimeShow").text(data.OperTime);
                        $("#bill .NumberShow").text(data.Number);
                        $("#bill .RemarkShow").text(data.Remark);
                        $("#bill .DiscountShow").text(data.Discount);
                        $("#bill .DiscountMoneyShow").text(data.DiscountMoney);
                        $("#bill .DiscountLastMoneyShow").text(data.DiscountLastMoney);
                        $("#bill .ChangeAmountShow").text(data.ChangeAmount);
                        $("#bill .DebtShow").text((data.DiscountLastMoney-data.ChangeAmount).toFixed(2));
                        $("#bill .OtherMoneyShow").text(data.OtherMoney);
                        $("#bill .AccountDayShow").text(data.AccountDay); //结算天数
                        var otherMoney = data.OtherMoney + "";
                        var otherMoneyList = data.OtherMoneyList + "";
                        var otherMoneyItem = data.OtherMoneyItem + "";
                        if(otherMoneyList && otherMoneyItem){
                            var itemArr = otherMoneyList.split(","); //支出项目id列表
                            var itemMoneyArr = otherMoneyItem.split(","); //支出项目金额列表
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
                            $("#bill .OtherMoneyShow:visible").text(otherMoneyShow +"总计："+ otherMoney.replace("undefined","0") + "元 "); //采购费用、销售费用
                        }
                        $("#bill .payTypeShow").text(data.payType);
                        var TotalPrice = data.TotalPrice;
                        depotHeadID = data.Id;
                        initTableData_material_show(TotalPrice,listSubType); //商品列表-查看状态

                        //零售单据展示数据
                        if(listSubType == "零售出库" || listSubType == "零售退货入库"){
                            $("#bill .change-amount-show").text(data.ChangeAmount);
                            var changeAccount = $("#bill .change-amount-show:visible").text() - 0;
                            if(manyAccountMoney!==0){
                                $("#bill .get-amount-show").text((manyAccountMoney).toFixed(2));
                                $("#bill .back-amount-show").text((manyAccountMoney -changeAccount).toFixed(2));
                            }
                            else {
                                $("#bill .get-amount-show").text((changeAccount).toFixed(2));
                                $("#bill .back-amount-show").text(0);
                            }
                        }
                        if(listSubType === "销售出库" || listSubType === "销售退货"){
                            var salesManInfo = data.Salesman;
                            if(salesManInfo){
                                var arr = salesManInfo.split(",");
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
                                            $("#bill .SalesmanShow").text(res); //销售人员列表
                                        }
                                    },
                                    error:function(){

                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
        //如果是财务单据
        else if(billType == "account"){
            $.ajax({
                url: path + "/accountHead/getDetailByNumber.action",
                data: {
                    BillNo: number
                },
                type: "get",
                success: function (res) {
                    if (res) {
                        var data = JSON.parse(res);
                        $("#bill .BillNoShow").text(data.BillNo);
                        $("#bill .BillTimeShow").text(data.BillTime);
                        $("#bill .RemarkShow").text(data.Remark);
                        $("#bill .AccountIdShow").text(data.AccountName);
                        $('#bill .OrganIdShow').text(data.OrganName);
                        $("#bill .HandsPersonIdShow").text(data.HandsPersonName);
                        $("#bill .ChangeAmountShow").text(data.ChangeAmount);
                        var TotalPrice = data.TotalPrice;
                        var accountHeadID  = data.Id;
                        initTableData_account_show(TotalPrice, accountHeadID); //明细列表-查看状态
                    }
                }
            });
        }
    }

    //初始化表格数据-商品列表-查看状态
    function initTableData_material_show(TotalPrice,listSubType){
        var isShowAnotherDepot = true; //显示对方仓库,true为隐藏,false为显示
        var anotherDepotHeadName = ""; //对方仓库的列的标题
        var depotHeadName = ""; //仓库的列的标题
        if(listSubType == "调拨出库"){
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
        if(listSubType == "调拨出库" || listSubType == "其它出库" || listSubType == "其它入库" || listSubType == "零售出库" || listSubType == "零售退货入库" || listSubType == "礼品充值" || listSubType == "礼品销售"){
            isShowTaxColumn = true; //隐藏
        }
        var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示
        if(listSubType == "组装单" || listSubType == "拆卸单"){
            isShowMaterialTypeColumn = false; //显示
        }
        $('#bill .materialDataShow').datagrid({
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
                var DiscountMoney = $("#bill .DiscountMoneyShow:visible").text()-0; //优惠金额
                var DiscountLastMoney = $("#bill .DiscountLastMoneyShow:visible").text()-0; //优惠后金额
                var array = [];
                array.push({
                    "AllPrice": AllPrice,
                    "TaxLastMoney": DiscountMoney + DiscountLastMoney
                });
                res.footer = array;
                $("#bill .materialDataShow").datagrid('loadData',res);

            },
            error:function() {
                $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
            }
        });
    }

    //初始化表格数据-明细列表-查看状态
    function initTableData_account_show(TotalPrice, accountHeadID){
        $('#bill .accountDataShow').datagrid({
            height:280,
            rownumbers: true,
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
            columns:[[
                { title: payTypeTitle, field: 'InOutItemName', width:230, hidden:itemType},
                { title: '账户名称', field: 'AccountName', width:230, hidden:moneyType},
                { title: '金额',field: 'EachAmount',width:70},
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
                $("#bill .accountDataShow").datagrid('loadData',res);
            },
            error:function() {
                $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
            }
        });
    }

