//初始化界面
var defDepotId = null;
var kid = sessionStorage.getItem("userId");
var pageType = getUrlParam('t');  //获取页面类型传值
var depotList = null;
var depotID = null;
var supplierList = null;
var supplierID = null;
var personList = null;
var personID = null;
var ProjectSearch=null;
var userBusinessList=null;
var userdepot=null;
var url;
var depotHeadID = 0;
var preTotalPrice = 0; //前一次加载的金额
var orgDepotHead = "";
var editIndex = undefined;
/**默认编号有意义吗，不如空着吧*/
var orgDefaultId=''; //单位默认编号
var orgDefaultList; //存储查询出来的会员列表
var accountList; //账户列表
var outItemList; //支出项目列表
var thisTaxRate = 0; //当前税率，选择供应商或者客户的时候设置
var oldNumber = ""; //编辑前的单据编号
var oldId = 0; //编辑前的单据Id
var otherColumns = true; //明细中的‘别名’列是否显示
var btnEnableList = getBtnStr(); //获取按钮的权限
var mPropertyList = ""; //商品属性列表
var defaultAccountId = 0; //默认账户id
//改变宽度和高度
$("#searchPanel").panel({width:webW-2});
$("#tablePanel").panel({width:webW-2});
var listTitle = "零售出库列表"; //单据标题
var depotString = "|"; //店铺id列表
var listType ="出库";//入库 出库
var listSubType = "零售";//采购 销售等
var payTypeTitle = "收款"; //付款 收款
var organUrl ="/supplier/findBySelect_retail"; //组织数据接口地址
var amountNum ="LSCK";//单据编号开头字符
$(function(){
    //初始化系统基础信息
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
    initOutItemList(); //初始化支出项目
    initMProperty(); //初始化商品属性
    initTableData();
    ininPager();
    initForm();
    bindEvent();//绑定操作事件
});

//初始化系统基础信息
function initSystemData_UB(){
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
            }
            else {
                userBusinessList = null;
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
        type:"get",
        url: "/depot/getAllList",
        //设置为同步
        async:false,
        dataType: "json",
        success: function (res) {
            if(res && res.code === 200){
                depotList = res.data;
            } else {
                $.messager.alert('提示', '查找系统基础信息异常,请与管理员联系！', 'error');
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
                    if(depot.isDefault){
                        defDepotId =  depot.id;
                    }
                    options += '<option value="' + depot.id + '">' + depot.name + '</option>';
                    depotString = depotString + depot.id + ",";
                }
            }
            if(depot.type === 1){
                depotString = depotString + depot.id + ",";
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
            orgDefaultList = res;
        },
        onSelect: function(rec){
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
    });
}

//初始化销售人员
function initSalesman(){
    $('#Salesman').combobox({
        url: "/person/getPersonByNumType?type=1",
        valueField:'id',
        textField:'name',
        multiple: true
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
}

//初始化系统基础信息
function initSystemData_person(){
    $.ajax({
        type:"get",
        url: "/person/getAllList",
        //设置为同步
        async:false,
        dataType: "json",
        success: function (res) {
            if(res && res.code === 200) {
                if(res.data) {
                    personList = res.data.personList;
                }
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
        type:"get",
        url: "/account/getAccount",
        //设置为同步
        async:false,
        dataType: "json",
        success: function (res) {
            if(res && res.code === 200) {
                if(res.data) {
                    accountList = res.data.accountList;
                }
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
            options += '<option value="' + account.id + '" data-currentAmount="' + account.currentamount + '">' + account.name + '</option>';
            if(account.isdefault) {
                defaultAccountId = account.id; //给账户赋值默认id
            }
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
    if(pageType === "skip") {
        var oldRowInfo = sessionStorage.getItem("rowInfo");
        editDepotHead(oldRowInfo); //自动弹出编辑框，带缓存数据
    }
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
    var isShowLastMoneyColumn = true; //是否显示优惠后金额和价税合计,true为隐藏,false为显示
    var isShowOrganNameColumn = false; //是否显示供应商、客户等信息,true为隐藏,false为显示
    var organNameTitle = "会员卡号"; //组织名称标题
    var opWidth = 90; //操作宽度
    var opTitle = ""; //跳转按钮的标题
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
            { field: 'id',width:35,align:"center",checkbox:true},
            { title: '操作',field: 'op',align:"center",width:opWidth,
                formatter:function(value, rec,index) {
                    var str = '';
                    var orgId = rec.organid? rec.organid:0;
                    str += '<img title="查看" src="/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showDepotHead(\'' + index + '\');"/>&nbsp;&nbsp;&nbsp;';
                    str += '<img title="编辑" src="/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editDepotHead(\'' + index + '\''+',' + rec.status + ');"/>&nbsp;&nbsp;&nbsp;';
                    str += '<img title="删除" src="/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteDepotHead('+ rec.id +',' + orgId +',' + rec.totalprice+',' + rec.status + ');"/>';
                    return str;
                }
            },
            { title: organNameTitle, field: 'organName',width:120, hidden:isShowOrganNameColumn},
            { title: '单据编号',field: 'number',width:135, formatter:function (value,rec) {
                    if(rec.linknumber) {
                        return value + "[转]";
                    } else {
                        return value;
                    }
                }
            },
            { title: '商品信息',field: 'materialsList',width:180,formatter:function(value){
                    if(value) {
                        return value.replace(",","，");
                    }
                }
            },
            { title: '单据日期',field: 'opertimeStr',width:130},
            { title: '操作员',field: 'operpersonname',width:60},
            { title: '金额合计',field: 'totalprice',width:60},
            { title: '含税合计',field: 'totaltaxlastmoney',hidden:isShowLastMoneyColumn,width:60,formatter:function(value,rec){
                    return (rec.discountmoney + rec.discountlastmoney).toFixed(2);
                }
            },
            { title: '优惠后金额',field: 'discountlastmoney',hidden:isShowLastMoneyColumn,width:80},
            { title: payTypeTitle,field: 'changeamount',width:50,hidden:hideType},
            { title: '状态',field: 'status',hidden:isHiddenStatus, width:70,align:"center",formatter:function(value){
                    if(value === "0") {
                        return "<span style='color:red;'>未审核</span>";
                    } else if(value === "1") {
                        return "<span style='color:green;'>已审核</span>";
                    } else if(value === "2") {
                    }
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
        url: "/material/findById",
        type: "get",
        dataType: "json",
        data: {
            id: mId
        },
        success: function (rec) {
            if(rec && rec.code === 200 && rec.data && rec.data[0]) {
                var loadRatio = 1; //在单位输入框上面加载比例字段
                if(rec.data[0].unit) { //如果存在计量单位信息
                    loadRatio = 1;
                }
                else{
                    var unitName = rec.data[0].unitName;
                    var basicUnit="";
                    var otherUnit="";
                    if(unitName) {
                        thisRatio = unitName.substring(unitName.indexOf(":")+1).replace(")","");
                        unitName = unitName.substring(0, unitName.indexOf("("));
                        var unitArr = unitName.split(",");
                        basicUnit = unitArr[0]; //基础单位
                        otherUnit = unitArr[1]; //副单位
                    }
                    var unitSetInput =""; //单位
                    unitSetInput = rec.data[0].firstoutunit;
                    if(basicUnit==unitSetInput){ //基础单位等于选择的单位
                        loadRatio = 1;
                    }
                    else if(otherUnit==unitSetInput){ //副单位等于选择的单位
                        loadRatio = thisRatio;
                    }
                }
                //查询库存
                $.ajax({
                    type: "get",
                    url: '/depotItem/findStockNumById',
                    data:{
                        projectId: depotId,
                        materialId: mId,
                        monthTime: monthTime,
                        currentPage: 1,
                        pageSize: 10
                    },
                    dataType: "json",
                    success: function (res) {
                        if(res && res.code === 200) {
                            if (res.data && res.data.page && res.data.page[0]) {
                                var thisStock = res.data.page[0].thisSum;
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
                                body.find("[field='Stock']").find(input).val(thisStock).attr("data-stock", res.data.page[0].thisSum); //加载库存数据
                            }
                            else {
                                body.find("[field='Stock']").find(input).val(0).attr("data-stock", 0); //加载库存数据
                            }
                            body.find("[field='Stock']").find(input).prop("readonly", "readonly"); //设置库存数据为只读
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

    $("#ChangeAmount, #getAmount").val((TotalPrice).toFixed(2));
    $("#backAmount").val(0);
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
    depotHeadName = "仓库名称";
    depotUrl = '/depot/findDepotByUserId?UBType=UserDepot&UBKeyId='+kid;
    depotTextField = "depotName";
    var isShowTaxColumn = true; //是否显示税率相关的列,true为隐藏,false为显示
    var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示
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
                        method:'get',
                        url: "/material/findBySelect",
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
                                    url: "/material/findById",
                                    type: "get",
                                    dataType: "json",
                                    data: {
                                        id: mId
                                    },
                                    success: function (res) {
                                        if(res && res.code === 200 && res.data && res.data[0]) {
                                            var retailPrice = res.data[0].retailprice-0; //零售价格
                                            var presetPriceOne = res.data[0].presetpriceone-0; //预计采购价
                                            var presetPriceTwo = res.data[0].presetpricetwo-0; //批发价
                                            var firstInUnit = res.data[0].firstinunit; //首选入库单位
                                            var firstOutUnit = res.data[0].firstoutunit; //首选出库单位
                                            var basicPresetPriceOne = ""; //多单位-入库-基础价格
                                            var basicPresetPriceTwo = ""; //多单位-出库-基础价格
                                            var retailPriceOne = ""; //多单位-入库-零售价格
                                            var otherPresetPriceOne = ""; //多单位-入库-其他价格
                                            var otherPresetPriceTwo = ""; //多单位-出库-其他价格
                                            var retailPriceTwo = ""; //多单位-出库-零售价格
                                            var basicUnit = ""; //基础单位
                                            var otherUnit = ""; //其他单位
                                            if(!res.data[0].unit){
                                                var ps = res.data[0].pricestrategy;
                                                var psObj = JSON.parse(ps);
                                                basicPresetPriceOne = psObj[0].basic.PresetPriceOne-0;
                                                basicPresetPriceTwo = psObj[0].basic.PresetPriceTwo-0;
                                                retailPriceOne = psObj[0].basic.RetailPrice-0;
                                                otherPresetPriceOne = psObj[1].other.PresetPriceOne-0;
                                                otherPresetPriceTwo = psObj[1].other.PresetPriceTwo-0;
                                                retailPriceTwo = psObj[1].other.RetailPrice-0;
                                                basicUnit = psObj[0].basic.Unit;
                                                otherUnit = psObj[1].other.Unit;
                                            }
                                            body =$("#depotHeadFM .datagrid-body");
                                            footer =$("#depotHeadFM .datagrid-footer");
                                            input = ".datagrid-editable-input";
                                            if(res.data[0].unit){ //如果存在计量单位信息
                                                ratio = 1; //重置比例为1
                                                body.find("[field='Unit']").find(input).val(res.data[0].unit); //设置-计量单位信息
                                                body.find("[field='Unit']").find(input).prop("readonly","readonly"); //设置计量单位为只读
                                                body.find("[field='Unit']").find(input).off("click"); //移除点击事件
                                                body.find("[field='Unit']").find(input).attr("data-ratio",ratio); //修改比例缓存信息
                                            }
                                            else {
                                                var unitName = res.data[0].unitName;
                                                var basicUnit="";
                                                var otherUnit="";
                                                if(unitName) {
                                                    ratio = unitName.substring(unitName.indexOf(":")+1).replace(")",""); //给比例赋值
                                                    unitName = unitName.substring(0, unitName.indexOf("("));
                                                    var unitArr = unitName.split(",");
                                                    basicUnit = unitArr[0]; //基础单位
                                                    otherUnit = unitArr[1]; //副单位
                                                }
                                                var unitSetInput =""; //单位
                                                body.find("[field='Unit']").find(input).prop("readonly","readonly"); //设置计量单位为只读
                                                var loadRatio = 1; //在单位输入框上面加载比例字段
                                                unitSetInput = res.data[0].firstoutunit; //给单位文本框赋值
                                                if(basicUnit==unitSetInput){ //基础单位等于选择的单位
                                                    loadRatio = 1;
                                                }
                                                else if(otherUnit==unitSetInput){ //副单位等于选择的单位
                                                    loadRatio = ratio;
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
                                                                UnitPrice = retailPriceOne;
                                                                body.find("[field='Stock']").find(input).val(stock); //修改库存
                                                            }
                                                            else if(type === "other"){
                                                                UnitPrice = retailPriceTwo;
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
                                            if(res.data[0].unit) { //如果存在计量单位信息
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
                                            body.find("[field='OperNumber']").find(input).val(1); //数量初始化为1
                                            //单价和总价赋值
                                            if(!detailPrice) {
                                                detailPrice = 0;
                                            }
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
                    batchDel(); //删除
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
                data.footer = array;
                $("#materialData").datagrid('loadData',data);
                //如果是订单跳转到采购或销售
                if(pageType === "skip") {
                    var skipList = $("#depotHeadFM .datagrid-body tr");
                    //逐条自动点击每行数据
                    skipList.each(function (i) {
                        setTimeout(function () {
                            skipList.eq(i).find("[field='Stock']").click();
                        },(i+1)*1000);
                    });
                }
            }
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
    depotHeadName = "仓库名称";
    var isShowTaxColumn = true; //是否显示税率相关的列,true为隐藏,false为显示
    var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示
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
                var DiscountMoney = $("#DiscountMoneyShow").text() - 0; //优惠金额
                var DiscountLastMoney = $("#DiscountLastMoneyShow").text() - 0; //优惠后金额
                var array = [];
                array.push({
                    "AllPrice": AllPrice,
                    "TaxLastMoney": DiscountMoney + DiscountLastMoney
                });
                data.footer = array;
                $("#materialDataShow").datagrid('loadData', data);
            }
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
    if(status == "1" || status == "2") {
        $.messager.alert('删除提示','已审核和已转的单据不能删除！','warning');
        return;
    }
    $.messager.confirm('删除确认','确定要删除此单据信息吗？',function(r) {
        if (r) {
            $.ajax({
                type:"post",
                // url: "/depotHead/" + depotHeadID + "/delete",
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
    });
}

//订单转采购或销售
function skipDepotHead(index, status){
    //获取当前行
    var rowsdata = $("#tableData").datagrid("getRows")[index];
    if(status == "0" || status == "2") {
        $.messager.alert('提示','未审核和已转的单据禁止操作！','warning');
    } else {
        sessionStorage.setItem("rowInfo", JSON.stringify(rowsdata)); //将单据信息存入缓存中
    }
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
                            $.ajax({
                                type: "post",
                                url: "/supplier/updateAdvanceIn",
                                dataType: "json",
                                data: {
                                    supplierId: row[i].organid, //会员id
                                    advanceIn: row[i].totalprice  //删除时同时返还用户的预付款
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
                    //批量删除
                    $.ajax({
                        type: "post",
                        // url: "/depotHead/batchDelete",
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
                            status: "1",
                            depotHeadIDs : ids
                        }),
                        success: function (res) {
                            if(res && res.code === 200) {
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            } else {
                                $.messager.alert('审核提示', '审核信息失败，请稍后再试！', 'error');
                            }
                        },
                        //此处添加错误处理
                        error:function() {
                            $.messager.alert('审核提示','审核信息异常，请稍后再试！','error');
                            return;
                        }
                    });
                } else {
                    $.messager.alert('审核提示','没有能审核的单据！','warning');
                }
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
                        if(row[i].status != "2") {
                            ids += row[i].id;
                        }
                        break;
                    }
                    ids += row[i].id + ",";
                }
                if(ids) {
                    $.ajax({
                        type: "post",
                        url: "/depotHead/batchSetStatus",
                        dataType: "json",
                        async: false,
                        data: ({
                            status: "0",
                            depotHeadIDs: ids
                        }),
                        success: function (res) {
                            if (res && res.code === 200) {
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            } else {
                                $.messager.alert('反审核提示', '反审核信息失败，请稍后再试！', 'error');
                            }
                        },
                        //此处添加错误处理
                        error: function () {
                            $.messager.alert('反审核提示', '反审核信息异常，请稍后再试！', 'error');
                            return;
                        }
                    });
                } else {
                    $.messager.alert('反审核提示','没有能反审核的单据！','warning');
                }
            }
        });
    }
}

//生成单据编号
function buildNumber() {
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
}

//新增信息
function addDepotHead(){
    $('#depotHeadFM').form('clear');
    var thisDateTime = getNowFormatDateTime(); //当前时间
    $("#OperTime").val(thisDateTime);
    buildNumber(); //生成单据编号
    //初始化优惠率、优惠金额、优惠后金额、本次付|收款、本次欠款 为0
    $("#Discount").val(0);
    $("#DiscountMoney").val(0);
    $("#DiscountLastMoney").val(0);
    $("#ChangeAmount").val(0);
    $("#Debt").val(0);
    $("#AccountId").val(defaultAccountId); //初始化默认的账户Id
    var addTitle = listTitle.replace("列表","信息");
    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加' + addTitle);
    $(".window-mask").css({ width: webW ,height: webH});

    orgDepotHead = "";
    depotHeadID = 0;
    initTableData_material("add"); //商品列表
    reject(); //撤销下、刷新商品列表
    $("#addOrgan").off("click").on("click",function(){
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加供应商信息');
        $('#supplierFM').form('clear');
    });

    $("#addMember").off("click").on("click",function(){
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加会员信息');
        $('#supplierFM').form('clear');
    });
    $("#addCustomer").off("click").on("click",function(){
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加客户信息');
        $('#supplierFM').form('clear');
    });
    url = '/depotHead/addDepotHeadAndDetail';

    //零售单据修改收款时，自动计算找零
        $("#payType").val("现付");
        $("#OrganId").combobox("setValue", orgDefaultId); //自动默认选择非会员
        // 鼠标点下时清空选择项
        $("#OrganId").next().find("input").off("mousedown").on("mousedown",function(){
            $("#OrganId").combobox("setValue", "");
        });
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
                },1000);
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

//编辑信息
function editDepotHead(index, status){
    if(status == "1" || status == "2") {
        $.messager.alert('编辑提示','已审核和已转的单据不能编辑！','warning');
        return;
    }
    // var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
    //获取当前行
    var rowsdata ='';
    if(Number(index).toString()=='NaN'){
        rowsdata=$.parseJSON(index);
    }else{
        rowsdata=$("#tableData").datagrid("getRows")[index];
    }
    $("#ProjectId").focus().val(rowsdata.projectid);
    var ProjectId=rowsdata.projectid;
    if(ProjectId!='') {
        initSystemData_person(ProjectId);
        initSelectInfo_person();
    }
    var TotalPrice = rowsdata.totalprice; //合计金额
    if(pageType === "skip") { //从订单跳转过来
        buildNumber(); //生成单据编号
        var thisDateTime = getNowFormatDateTime(); //当前时间
        $("#OperTime").val(thisDateTime);
        $("#LinkNumber").val(rowsdata.number);  //关联订单号
        $("#AccountId").val(defaultAccountId); //初始化默认的账户Id
        $("#DiscountLastMoney").val(TotalPrice); //优惠后金额
        $("#ChangeAmount").val(TotalPrice).attr("data-changeamount", TotalPrice);
    } else {
        $("#Number").val(rowsdata.number).attr("data-defaultNumber",rowsdata.number);
        $("#OperTime").val(rowsdata.opertimeStr);
        $("#LinkNumber").val(rowsdata.linknumber==undefined?"":rowsdata.linknumber); //关联订单号
        $("#AccountId").val(rowsdata.accountid); //账户Id
        $("#DiscountLastMoney").val(rowsdata.discountlastmoney==undefined?"0.00":rowsdata.discountlastmoney); //优惠后金额
        $("#ChangeAmount").val(rowsdata.changeamount).attr("data-changeamount", rowsdata.changeamount);
    }
    $('#OrganId').combobox('setValue', rowsdata.organid==undefined?'':rowsdata.organid);
    $("#HandsPersonId").val(rowsdata.handspersonid);
    $("#Remark").val(rowsdata.remark);
    $("#Discount").val(rowsdata.discount==undefined?"0":rowsdata.discount);
    $("#DiscountMoney").val(rowsdata.discountmoney==undefined?"0.00":rowsdata.discountmoney);
    $("#Debt").val(Number((rowsdata.discountlastmoney==undefined?'0.00':rowsdata.discountlastmoney-rowsdata.changeamount)).toFixed(2));
    $("#AccountDay").val(rowsdata.accountday==undefined?"":rowsdata.accountday); //结算天数
    preTotalPrice = rowsdata.totalprice; //记录前一次合计金额，用于扣预付款
    $("#AllocationProjectId").val(rowsdata.allocationprojectid);
    oldNumber = rowsdata.number; //记录编辑前的单据编号
    oldId = rowsdata.id; //记录单据Id
    var editTitle = listTitle.replace("列表","信息");
    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑' + editTitle);
    $(".window-mask").css({ width: webW ,height: webH});
    depotHeadID = rowsdata.id;

        var option = "";
        if(rowsdata.paytype == "预付款"){
            option = '<option value="预付款">预付款</option>';
            option += '<option value="现付">现付</option>';
        }
        else {
            option += '<option value="现付">现付</option>';
        }
        $("#payType").empty().append(option);


    //采购入库、销售出库的多账户加载
    if(rowsdata.accountidlist!=undefined && rowsdata.accountmoneylist!=undefined){
        $("#AccountId").val("many"); //下拉框选中多账户
        var accountArr = rowsdata.accountidlist.split(",");
        var accountMoneyArr = rowsdata.accountmoneylist.split(",");
        accountMoneyArr = changeListFmtPlus(accountMoneyArr)  //将数组单个金额中的数值转为正数

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

        $("#AccountId").attr("data-accountArr", JSON.stringify(accountArr)).attr("data-accountMoneyArr", JSON.stringify(accountMoneyArr));  //json数据存储
        $(".many-account-ico").show(); //显示多账户的ico图标
    }

    //采购入库、销售出库的费用数据加载
    if(rowsdata.othermoneylist &&rowsdata.othermoneyitem){
        $("#OtherMoney").val(rowsdata.othermoney==undefined?"0":rowsdata.othermoney); //采购费用、销售费用
        var itemArr = rowsdata.othermoneylist.split(",");
        var itemMoneyArr = rowsdata.othermoneyitem.split(",");
        $("#OtherMoney").attr("data-itemArr",JSON.stringify(itemArr)).attr("data-itemMoneyArr",JSON.stringify(itemMoneyArr));  //json数据存储
    }

    initTableData_material("edit",TotalPrice); //商品列表
    reject(); //撤销下、刷新商品列表
    if(pageType === "skip") {
        url = '/depotHead/addDepotHeadAndDetail'; //如果是从订单跳转过来，则此处为新增的接口
        //jshjshjsh
        $("#depotHeadFM .datagrid-body").find("[field='DepotId']").click();
    } else {
        url = '/depotHead/updateDepotHeadAndDetail?id=' + rowsdata.id; //更新接口
    }
}

//查看信息
function showDepotHead(index){
    // var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
    //获取当前行
    var rowsdata = $("#tableData").datagrid("getRows")[index];
    var manyAccountMoney = 0; //多账户合计-零售
    $("#ProjectIdShow").text(rowsdata.projectName);
    $("#NumberShow").text(rowsdata.number);
    $("#OperTimeShow").text(rowsdata.opertimeStr);
    $('#OrganIdShow').text(rowsdata.organName==undefined?'':rowsdata.organName);
    $("#HandsPersonIdShow").text(rowsdata.handsPersonName);
    if(rowsdata.accountName && rowsdata.accountName!=undefined){
        $("#AccountIdShow").text(rowsdata.accountName); //结算账户
    } else {
        var accountArr = (rowsdata.accountidlist==undefined?"":rowsdata.accountidlist).split(","); //账户id列表
        var accountMoneyArr =(rowsdata.accountmoneylist==undefined?"":rowsdata.accountmoneylist).split(","); //账户金额列表
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
    $("#ChangeAmountShow").text(rowsdata.changeamount);
    $("#RemarkShow").text(rowsdata.remark);
    $("#DiscountShow").text(rowsdata.discount);
    $("#DiscountMoneyShow").text(rowsdata.discountmoney);
    $("#DiscountLastMoneyShow").text(rowsdata.discountlastmoney);
    $("#DebtShow").text((rowsdata.discountlastmoney-rowsdata.changeamount).toFixed(2));
    $("#AccountDayShow").text(rowsdata.accountday==undefined?"":rowsdata.accountday);  //结算天数
    $("#LinkNumberShow").text(rowsdata.linknumber==undefined?"":rowsdata.linknumber); //关联订单号
    if(rowsdata.othermoneylist && rowsdata.othermoneyitem){
        var itemArr = rowsdata.othermoneylist.split(","); //支出项目id列表
        var itemMoneyArr = rowsdata.othermoneyitem.split(","); //支出项目金额列表
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
        $("#OtherMoneyShow").text(otherMoneyShow +"总计："+ rowsdata.othermoney==undefined?"0":rowsdata.othermoney + "元 "); //采购费用、销售费用
    }
    else {
        $("#OtherMoneyShow").text(rowsdata.othermoney==undefined?"0":rowsdata.othermoney); //采购费用、销售费用
    }
    $("#payTypeShow").text(rowsdata.paytype);
    var TotalPrice = rowsdata.totalprice;
    $("#AllocationProjectIdShow").text(rowsdata.allocationProjectName);
    var showTitle = listTitle.replace("列表","信息");
    $('#depotHeadDlgShow').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看' + showTitle);
    $(".window-mask").css({ width: webW ,height: webH});

    depotHeadID = rowsdata.id;
    initTableData_material_show(TotalPrice); //商品列表-查看状态

    //零售单据展示数据
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
    //保存信息
    /**
     * 2019-01-25
     * 我对这个方法的实现结果存在严重怀疑，决定重构
     * 有如下疑点：
     * 1、保存完主表，返回结果后，再发起请求去操作子表，如何保证事务的一致性（业务数据一致性）
     * 2、保存完主表后，再通过selectmax的方式去获取主表主键id完全就是看人品的不可靠的思路
     * 修改计划：
     * 2019-01-25修改零售出库相关操作
     * 修改方式，重写url，将主从表操作合并
     *
     *
     * **/
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
            //进行明细的校验
            if(depotHeadID ==0) {
                //新增模式下
                if (!CheckData("add")) {
                    return;
                }
            }
            else {
                //编辑模式下
                if (!CheckData("edit")) {
                    return;
                }
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
            //零售时候，可以从会员预付款中扣款
            var thisPayType = "现付";
            if($("#payType").val() ==="预付款") {
                thisPayType = "预付款";
            }
            var SalesmanStr = "";
            var getAccountID = $.trim($("#AccountId").val());
            if($("#AccountId").val() === "many"){ //多账户
                getAccountID = null;
            }
            var infoStr=JSON.stringify({
                Type: listType,
                SubType: listSubType,
                ProjectId: ProjectId,
                AllocationProjectId: AllocationProjectId,
                DefaultNumber: $.trim($("#Number").attr("data-defaultNumber")),//初始编号
                Number: $.trim($("#Number").val()),
                LinkNumber: $.trim($("#LinkNumber").val()),
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
                AccountMoneyList: accountMoneyArr ? JSON.stringify(accountMoneyArr) : "", //账户金额列表-多账户
                Discount: $.trim($("#Discount").val()),
                DiscountMoney: $.trim($("#DiscountMoney").val()),
                DiscountLastMoney: $.trim($("#DiscountLastMoney").val()),
                OtherMoney: $.trim($("#OtherMoney").val()), //采购费用、销售费用
                OtherMoneyList: $("#OtherMoney").attr("data-itemarr"), //支出项目列表-涉及费用
                OtherMoneyItem: $("#OtherMoney").attr("data-itemmoneyarr"), //支出项目金额列表-涉及费用
                AccountDay: $("#AccountDay").val() //结算天数
            });
            /**
             * 零售出库，单独操作
             * */
            if(url.indexOf("/depotHead/addDepotHeadAndDetail")>=0){
                addDepotHeadAndDetail(url,infoStr);
                return;
            }else if(url.indexOf("/depotHead/updateDepotHeadAndDetail")>=0){
                updateDepotHeadAndDetail(url,infoStr,preTotalPrice);
                return;
            }
        }
    });

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
        $('#depotHeadAccountDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;多账户结算');
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
            $("#getAmount").val(accountMoneyTotal); //给付款或者收款金额赋值
            var backAmount = $("#getAmount").val() - $("#ChangeAmount").val();
            $("#backAmount").val((backAmount - 0).toFixed(2)); //计算找零金额
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
                $("#ChangeAmount").prop("readonly","readonly");
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
            $("#ChangeAmount").prop("readonly","readonly");
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
        $("#accountMoneyTotalDlg").text($("#getAmount").val());
    });

    //点击采购费用、销售费用的事件
    $(".other-money-ico").off("click").on("click",function(){
        $('#otherMoneyDlg').dialog('open').dialog('setTitle','<img src="/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;'+ listSubType +'费用');
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

        var supplierType = "会员";
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
                    type:"get",
                    url: "/supplier/checkIsNameExist",
                    dataType: "json",
                    async :  false,
                    data: ({
                        id : 0,
                        name : supplierName
                    }),
                    success: function (res) {
                        if(res && res.code === 200) {
                            if(res.data && res.data.status) {
                                flag = res.data.status;
                                if (flag) {
                                    $.messager.alert('提示', '单位名称已经存在', 'info');
                                    return;
                                }
                            }
                        }
                    },
                    //此处添加错误处理
                    error:function() {
                        $.messager.alert('提示','检查单位名称是否存在异常，请稍后再试！','error');
                        return;
                    }
                });
            }
            return flag;
        }

        //保存供应商信息
        $("#saveSupplier").off("click").on("click",function() {
            if(validateForm("supplierFM")) {
                return;
            }
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
            var url = '/supplier/add';
            var supObj = $("#supplierFM").serializeObject();
            supObj.type = supplierType;
            supObj.enabled = 1;
            $.ajax({
                url: url,
                type:"post",
                dataType: "json",
                data:{
                    info: JSON.stringify(supObj)
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

function showDepotHeadDetails(pageNo,pageSize){
    var materialParam = $.trim($("#searchMaterial").val());
    $.ajax({
        type:"get",
        url: "/depotItem/getHeaderIdByMaterial",
        dataType: "json",
        data: ({
            materialParam: materialParam,
            depotIds: depotString
        }),
        success: function (res) {
            if(res && res.code === 200) {
                var ids = res.data;
                if(ids){
                    $.ajax({
                        type: "get",
                        url: "/depotHead/list",
                        dataType: "json",
                        data: ({
                            search: JSON.stringify({
                                type: listType,
                                subType: listSubType,
                                state: $.trim($("#searchState").val()),
                                number: $.trim($("#searchNumber").val()),
                                beginTime: $("#searchBeginTime").val(),
                                endTime: $("#searchEndTime").val(),
                                dhIds: ids
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
                    return;
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
        $('#materialData').datagrid('appendRow', {DepotId:defDepotId});
        editIndex = $('#materialData').datagrid('getRows').length - 1;
        $('#materialData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
        autoReckon();
    }
}
//删除
function batchDel(){
    /**
     * 重写一下删除的逻辑
     * 获取所有选中行,直接从列表中移除
     * 点击保存时，将需要后台删除的数据提交到服务器
     * **/
    /**
     * create by: qiankunpingtai
     * create time: 2019/3/20 16:26
     * description:
     *	这个地方比较坑的，花了一个多小时才搞明白为什么
     *	1、删除之前必须先调用endEditing结束编辑
     *	2、如果只是调用endEditing结束编辑那么正在编辑行的被选中状态会被去掉
     *	所以要在调用endEditing先获取选中的行
     */
        //如果编辑的行一开始是选中状态，结束编辑后仍然是选中状态
    var row = $('#materialData').datagrid('getChecked');
    if (endEditing()) {
        if (row.length == 0) {
            $.messager.alert('删除提示', '没有记录被选中！', 'info');
            return;
        }
        if (row.length > 0) {
            $.messager.confirm('删除确认', '确定要删除选中的' + row.length + '条单据信息吗？', function (r) {
                if (r) {
                    for (var i = 0; i < row.length; i++) {
                        $('#materialData').datagrid('deleteRow', $('#materialData').datagrid("getRowIndex", row[i]));
                    }
                }
            });
        }
    }
}
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
function CheckData(type) {
    append();
    removeit();
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
        if (row[i].DepotId == "" || row[i].MaterialId == "" || row[i].OperNumber == "" || row[i].UnitPrice === "" || row[i].AllPrice === "") {
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


/**
 * 新增单据主表及单据子表
 * */
function addDepotHeadAndDetail(url,infoStr){
    var inserted = null;
    if(pageType === "skip") {
        inserted = $("#materialData").datagrid('getChanges', "updated");
    } else {
        inserted = $("#materialData").datagrid('getChanges', "inserted");
    }
    var deleted = $("#materialData").datagrid('getChanges', "deleted");
    var updated = $("#materialData").datagrid('getChanges', "updated");
    $.ajax({
        type:"post",
        url: url,
        dataType: "json",
        async :  false,
        data: ({
            info:infoStr,
            inserted: JSON.stringify(inserted),
            deleted: JSON.stringify(deleted),
            updated: JSON.stringify(updated)
        }),
        success: function (tipInfo){

            if(tipInfo){
                if(tipInfo.code!=200){
                    $.messager.alert('提示', tipInfo.msg, 'error');
                    return;
                }
                $.messager.alert('提示','保存成功！','info');
                $('#depotHeadDlg').dialog('close');
                var opts = $("#tableData").datagrid('options');
                showDepotHeadDetails(opts.pageNumber,opts.pageSize);

            }else {
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
/**
 * 修改单据主表及单据子表
 * */
function updateDepotHeadAndDetail(url,infoStr,preTotalPrice){
    var inserted = $("#materialData").datagrid('getChanges', "inserted");
    var deleted = $("#materialData").datagrid('getChanges', "deleted");
    var updated = $("#materialData").datagrid('getChanges', "updated");
    $.ajax({
        type:"post",
        url: url,
        dataType: "json",
        async :  false,
        data: ({
            id:url.substring(url.lastIndexOf("?id=")+4,url.length),
            info:infoStr,
            inserted: JSON.stringify(inserted),
            deleted: JSON.stringify(deleted),
            updated: JSON.stringify(updated),
            preTotalPrice:preTotalPrice
        }),
        success: function (tipInfo){
            if(tipInfo){
                if(tipInfo.code!=200){
                    $.messager.alert('提示', tipInfo.msg, 'error');
                    return;
                }
                $.messager.alert('提示','保存成功！','info');
                $('#depotHeadDlg').dialog('close');
                var opts = $("#tableData").datagrid('options');
                showDepotHeadDetails(opts.pageNumber,opts.pageSize);
                if (endEditing()) {
                    $('#materialData').datagrid('acceptChanges');
                }
            }else {
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




