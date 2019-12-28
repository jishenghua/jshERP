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
            type:"get",
            url: "/account/getAccount",
            //设置为同步
            async:false,
            dataType: "json",
            success: function (res) {
                if(res && res.code === 200){
                    accountList = res.data.accountList;
                }
            }
        });
    }
    //初始化商品属性
    function initMProperty(){
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
            //设置为同步
            async:false,
            success: function (res) {
                if(res && res.code === 200){
                    if(res.data && res.data.page) {
                        var thisRows = res.data.page.rows;
                        for (var i = 0; i < thisRows.length; i++) {
                            if (thisRows[i].enabled) {
                                mPropertyList += thisRows[i].nativename + ",";
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
    }
    //初始化收入项目列表
    function initOutItemList(){
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
            tableString = $(".purchase_in").html();
        }
        else if(listSubType == "采购退货出库") {
            billType = "material";
            $("#bill .purchase_back").show();
            tableString = $(".purchase_back").html();
        }
        else if(listSubType == "销售出库") {
            billType = "material";
            $("#bill .sale_out").show();
            tableString = $(".sale_out").html();
        }
        else if(listSubType == "销售退货入库") {
            billType = "material";
            $("#bill .sale_back").show();
            tableString = $(".sale_back").html();
        }
        else if(listSubType == "零售出库") {
            billType = "material";
            $("#bill .retail_out").show();
            tableString = $(".retail_out").html();
        }
        else if(listSubType == "零售退货入库") {
            billType = "material";
            $("#bill .retail_back").show();
            tableString = $(".retail_back").html();
        }
        else if(listSubType == "其它入库") {
            billType = "material";
            $("#bill .other_in").show();
            tableString = $(".other_in").html();
        }
        else if(listSubType == "其它出库") {
            billType = "material";
            $("#bill .other_out").show();
            tableString = $(".other_out").html();
        }
        else if(listSubType == "调拨出库") {
            billType = "material";
            $("#bill .allocation_out").show();
            tableString = $(".allocation_out").html();
        }
        else if(listSubType == "收入") {
            billType = "account";
            payTypeTitle = "收入项目";
            itemType = false; //显示当前列
            moneyType = true; //隐藏当前列
            $("#bill .item_in").show();
            tableString = $(".item_in").html();
        }
        else if(listSubType == "支出") {
            billType = "account";
            payTypeTitle = "支出项目";
            itemType = false; //显示当前列
            moneyType = true; //隐藏当前列
            $("#bill .item_out").show();
            tableString = $(".item_out").html();
        }
        else if(listSubType == "收款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .money_in").show();
            tableString = $(".money_in").html();
        }
        else if(listSubType == "付款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .money_out").show();
            tableString = $(".money_out").html();
        }
        else if(listSubType == "转账") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .giro").show();
            tableString = $(".giro").html();
        }
        else if(listSubType == "收预付款") {
            billType = "account";
            payTypeTitle = "无标题";
            itemType = true; //隐藏当前列
            moneyType = false; //显示当前列
            $("#bill .advance_in").show();
            tableString = $(".advance_in").html();
        }

        //如果是进货、销售
        if(billType == "material"){
            $.ajax({
                url: "/depotHead/getDetailByNumber",
                data: {
                    number: number
                },
                type: "get",
                success: function (res) {
                    if(res && res.code === 200){
                        var data = res.data;
                        var manyAccountMoney = 0; //多账户合计-零售
                        if(data.accountName){
                            $("#bill .AccountIdShow").text(data.accountName); //结算账户
                        }
                        else if(data.accountidlist && data.accountmoneylist) {
                            var accountArr = data.accountidlist; //账户id列表
                            var accountMoneyArr = data.accountmoneylist; //账户金额列表
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
                        $("#bill .OrganIdShow").text(data.organName);
                        $("#bill .OperTimeShow").text(data.opertimeStr);
                        $("#bill .NumberShow").text(data.number);
                        $("#bill .LinkNumberShow").text(data.linknumber? data.linknumber : "");
                        $("#bill .RemarkShow").text(data.remark);
                        $("#bill .DiscountShow").text(data.discount);
                        $("#bill .DiscountMoneyShow").text(data.discountmoney);
                        $("#bill .DiscountLastMoneyShow").text(data.discountlastmoney);
                        $("#bill .ChangeAmountShow").text(data.changeamount==null ? "":data.changeamount);
                        $("#bill .DebtShow").text((data.discountlastmoney-data.changeamount).toFixed(2));
                        $("#bill .OtherMoneyShow").text(data.othermoney==null ? "": data.othermoney);
                        $("#bill .AccountDayShow").text(data.accountday==null ? "": data.accountday); //结算天数
                        var otherMoney = data.othermoney + "";
                        var otherMoneyList = data.othermoneylist + "";
                        var otherMoneyItem = data.othermoneyitem + "";
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
                        var TotalPrice = data.totalprice;
                        depotHeadID = data.id;
                        initTableData_material_show(TotalPrice,listSubType); //商品列表-查看状态

                        //零售单据展示数据
                        if(listSubType == "零售出库" || listSubType == "零售退货入库"){
                            $("#bill .change-amount-show").text(data.changeamount);
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
                            var salesManInfo = data.salesman;
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
                                    type: "get",
                                    url: "/person/getPersonByIds",
                                    data: {
                                        personIDs: salesmanStr
                                    },
                                    success:function(res){
                                        if(res && res.code === 200 && res.data){
                                            $("#bill .SalesmanShow").text(res.data.names); //销售人员列表
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
                url: "/accountHead/getDetailByNumber",
                data: {
                    billNo: number
                },
                type: "get",
                success: function (res) {
                    if(res && res.code === 200){
                        var data = res.data;
                        $("#bill .BillNoShow").text(data.billno);
                        $("#bill .BillTimeShow").text(data.billtime);
                        $("#bill .RemarkShow").text(data.remark);
                        $("#bill .AccountIdShow").text(data.accountname);
                        $('#bill .OrganIdShow').text(data.organname);
                        $("#bill .HandsPersonIdShow").text(data.handspersonname);
                        $("#bill .ChangeAmountShow").text(data.changeamount==null ? "":data.changeamount);
                        var totalprice = data.totalprice;
                        var accountHeadID  = data.id;
                        initTableData_account_show(totalprice, accountHeadID); //明细列表-查看状态
                    }
                }
            });
        }
        //打印table
        $("#print_bill_detail").off("click").on("click",function(){
            var tableString = "";//打印table
            if(listSubType == "采购入库") {
                tableString = $(".purchase_in").html();
            }
            else if(listSubType == "采购退货出库") {
                tableString = $(".purchase_back").html();
            }
            else if(listSubType == "销售出库") {
                tableString = $(".sale_out").html();
            }
            else if(listSubType == "销售退货入库") {
                tableString = $(".sale_back").html();
            }
            else if(listSubType == "零售出库") {
                tableString = $(".retail_out").html();
            }
            else if(listSubType == "零售退货入库") {
                tableString = $(".retail_back").html();
            }
            else if(listSubType == "其它入库") {
                tableString = $(".other_in").html();
            }
            else if(listSubType == "其它出库") {
                tableString = $(".other_out").html();
            }
            else if(listSubType == "调拨出库") {
                tableString = $(".allocation_out").html();
            }
            else if(listSubType == "收入") {
                tableString = $(".item_in").html();
            }
            else if(listSubType == "支出") {
                tableString = $(".item_out").html();
            }
            else if(listSubType == "收款") {
                tableString = $(".money_in").html();
            }
            else if(listSubType == "付款") {
                tableString = $(".money_out").html();
            }
            else if(listSubType == "转账") {
                tableString = $(".giro").html();
            }
            else if(listSubType == "收预付款") {
                tableString = $(".advance_in").html();
            }
            localStorage.setItem("tableString",tableString);
            window.open("../../js/print/print_form.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
        });
    }

    //初始化表格数据-商品列表-查看状态
    function initTableData_material_show(TotalPrice,listSubType){
        var isShowAnotherDepot = true; //显示对方仓库,true为隐藏,false为显示
        var anotherDepotHeadName = ""; //对方仓库的列的标题
        var depotHeadName = ""; //仓库的列的标题
        if(listSubType == "调拨出库"){
            isShowAnotherDepot = false; //调拨时候显示对方仓库
            anotherDepotHeadName = "调入仓库";
        } else {
            depotHeadName = "仓库名称";
        }
        var isShowTaxColumn = false; //是否显示税率相关的列,true为隐藏,false为显示
        if(listSubType == "调拨出库" || listSubType == "其它出库" || listSubType == "其它入库" || listSubType == "零售出库" || listSubType == "零售退货入库"){
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
            type:"get",
            url: '/depotItem/getDetailList',
            data: {
                headerId: depotHeadID,
                mpList: mPropertyList
            },
            dataType: "json",
            success: function (res) {
                if(res && res.code === 200) {
                    var data = res.data;
                    var AllPrice = TotalPrice;
                    var DiscountMoney = $("#bill .DiscountMoneyShow:visible").text() - 0; //优惠金额
                    var DiscountLastMoney = $("#bill .DiscountLastMoneyShow:visible").text() - 0; //优惠后金额
                    var array = [];
                    array.push({
                        "AllPrice": AllPrice,
                        "TaxLastMoney": DiscountMoney + DiscountLastMoney
                    });
                    data.footer = array;
                    $("#bill .materialDataShow").datagrid('loadData', data);
                }
            },
            error:function() {
                $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
            }
        });
    }

    //初始化表格数据-明细列表-查看状态
    function initTableData_account_show(totalprice, accountHeadID){
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
            type:"get",
            url: '/accountItem/getDetailList',
            data: {
                headerId: accountHeadID
            },
            dataType: "json",
            success: function (res) {
                if(res && res.code === 200) {
                    var data = res.data;
                    var EachAmount = totalprice;
                    var array = [];
                    array.push({
                        "EachAmount": EachAmount
                    });
                    data.footer = array;
                    $("#bill .accountDataShow").datagrid('loadData', data);
                }
            },
            error:function() {
                $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
            }
        });
    }

    function print(){
        alert(listSubType);
    }
