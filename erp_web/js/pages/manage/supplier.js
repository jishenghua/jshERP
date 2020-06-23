    //初始化界面
    $(function() {
        var listTitle = ""; //单据标题
        var listType = ""; //类型
        var listTypeEn = ""; //英文类型
        getType();
        initTableData();
        ininPager();
        bindEvent();
    });

    //根据名称获取类型
    function getType(){
        listTitle = $("#tablePanel").prev().text();
        if(listTitle === "供应商信息列表"){
            listType = "供应商";
            listTypeEn = "Vendor";
        }
        else if(listTitle === "客户信息列表"){
            listType = "客户";
            listTypeEn = "Customer";
        }
        else if(listTitle === "会员信息列表"){
            listType = "会员";
            listTypeEn = "Member";
        }
    }

    //初始化表格数据
    function initTableData() {
        //改变宽度和高度
        $("#searchPanel").panel({width:webW-2});
        $("#tablePanel").panel({width:webW-2});
        $('#tableData').datagrid({
            //title:'单位列表',
            //iconCls:'icon-save',
            //width:700,
            height:heightInfo,
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
            pagination: true,
            //自动截取数据
            //nowrap : true,
            //loadFilter: pagerFilter,
            pageSize: initPageSize,
            pageList: initPageNum,
            columns:[[
                { field: 'id',width:35,align:"center",checkbox:true},
                { title: '操作',field: 'op',align:"center",width:60,
                    formatter:function(value,rec,index) {
                        var str = '';
                        str += '<img title="编辑" src="/js/easyui/themes/icons/pencil.png" style="cursor: pointer;" onclick="editSupplier(\'' + index + '\');"/>&nbsp;&nbsp;&nbsp;';
                        str += '<img title="删除" src="/js/easyui/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteSupplier(\'' + rec.id + '\');"/>';
                        return str;
                    }
                },
                { title: '名称',field: 'supplier',width:150},
                { title: '联系人', field: 'contacts',width:50,align:"center"},
                { title: '手机号码', field: 'telephone',width:100,align:"center"},
                { title: '电子邮箱',field: 'email',width:80,align:"center"},
                { title: '联系电话', field: 'phonenum',width:100,align:"center"},
                { title: '传真', field: 'fax',width:100,align:"center"},
                { title: '预付款',field: 'advancein',width:70,align:"center"},
                { title: '期初应收',field: 'beginneedget',width:70,align:"center"},
                { title: '期初应付',field: 'beginneedpay',width:70,align:"center"},
                { title: '期末应收',field: 'allneedget',width:70,align:"center"},
                { title: '期末应付',field: 'allneedpay',width:70,align:"center"},
                { title: '税率(%)', field: 'taxrate',width:60,align:"center"},
                { title: '状态',field: 'enabled',width:70,align:"center",formatter:function(value){
                    return value? "<span style='color:green'>启用</span>":"<span style='color:red'>禁用</span>";
                }}
            ]],
            toolbar:[
                {
                    id:'addSupplier',
                    text:'增加',
                    iconCls:'icon-add',
                    handler:function() {
                        addSuppler();
                    }
                },'-',
                {
                    id:'deleteSupplier',
                    text:'删除',
                    iconCls:'icon-remove',
                    handler:function() {
                        batDeleteSupplier();
                    }
                },'-',
                {
                    id:'setEnable',
                    text:'启用',
                    iconCls:'icon-ok',
                    handler:function() {
                        setEnableFun();
                    }
                },'-',
                {
                    id:'setDisEnable',
                    text:'禁用',
                    iconCls:'icon-no',
                    handler:function() {
                        setDisEnableFun();
                    }
                },'-',
                {
                    id:'setInput',
                    text:'导入',
                    iconCls:'icon-excel',
                    handler:function() {
                        setInputFun();
                    }
                },'-',
                {
                    id:'setOutput',
                    text:'导出',
                    iconCls:'icon-excel',
                    handler:function() {
                        setOutputFun();
                    }
                }
            ],
            onLoadError:function() {
                $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                return;
            }
        });
        dgResize();
        showSupplierDetails(1,initPageSize);
    }


    //分页信息处理
    function ininPager() {
        try {
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            pager.pagination({
                onSelectPage:function(pageNum, pageSize)
                {
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh', {
                        pageNumber:pageNum,
                        pageSize:pageSize
                    });
                    showSupplierDetails(pageNum,pageSize);
                }
            });
        }
        catch (e) {
            $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
        }
    }

    //删除信息
    function deleteSupplier(id) {
        $.messager.confirm('删除确认','确定要删除此条信息吗？',function(r) {
            if (r) {
                $.ajax({
                    type:"post",
                    url: "/supplier/batchDeleteSupplierByIds",
                    dataType: "json",
                    data: ({
                        ids : id
                    }),
                    success: function (res) {
                        if(res && res.code == 200) {
                            $("#searchBtn").click();
                        } else {
                            if(res && res.code == 601){
                                var jsondata={};
                                jsondata.ids=id;
                                jsondata.deleteType='2';
                                var type='single';
                                batDeleteSupplierForceConfirm(res,"/supplier/batchDeleteSupplierByIds",jsondata,type);
                            }else if(res && res.code == 600){
                                $.messager.alert('删除提示', res.msg, 'error');
                            }else{
                                $.messager.alert('删除提示', '删除信息失败，请稍后再试！', 'error');
                            }
                        }
                    },
                    //此处添加错误处理
                    error:function() {
                        $.messager.alert('删除提示','删除信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }

    //批量删除单位
    function batDeleteSupplier() {
        var row = $('#tableData').datagrid('getChecked');
        if(row.length == 0) {
            $.messager.alert('删除提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0) {
            $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条信息吗？',function(r) {
                if (r) {
                    var ids = "";
                    for(var i = 0;i < row.length; i ++) {
                        if(i == row.length-1)
                        {
                            ids += row[i].id;
                            break;
                        }
                        ids += row[i].id + ",";
                    }
                    $.ajax({
                        type:"post",
                        url: "/supplier/batchDeleteSupplierByIds",
                        dataType: "json",
                        async :  false,
                        data: ({
                            ids : ids
                        }),
                        success: function (res) {
                            if(res && res.code === 200) {
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            } else {
                                if(res && res.code == 601){
                                    var jsondata={};
                                    jsondata.ids=ids;
                                    jsondata.deleteType='2';
                                    var type='batch';
                                    batDeleteSupplierForceConfirm(res,"/supplier/batchDeleteSupplierByIds",jsondata,type);
                                }else if(res && res.code == 600){
                                    $.messager.alert('删除提示', res.msg, 'error');
                                }else{
                                    $.messager.alert('删除提示', '删除信息失败，请稍后再试！', 'error');
                                }
                            }
                        },
                        //此处添加错误处理
                        error:function() {
                            $.messager.alert('删除提示','删除信息异常，请稍后再试！','error');
                            return;
                        }
                    });
                }
            });
        }
    }
    /**
     * 确认强制删除
     * */
    function batDeleteSupplierForceConfirm(res,url,jsondata) {
        $.messager.confirm('删除确认', res.msg, function (r) {
            if (r) {
                $.ajax({
                    type: "post",
                    url: url,
                    dataType: "json",
                    data: (jsondata),
                    success: function (res) {
                        if(res && res.code == 200) {
                            $("#searchBtn").click();
                            if(type=='batch'){
                                $(":checkbox").attr("checked", false);
                            }
                        }else if(res && res.code == 600){
                            $.messager.alert('删除提示', res.msg, 'error');
                        }else {
                            $.messager.alert('删除提示', '删除信息失败，请稍后再试！', 'error');
                        }
                    },
                    //此处添加错误处理
                    error: function () {
                        $.messager.alert('删除提示', '删除信息失败，请稍后再试！', 'error');
                        return;
                    }
                });
            }
        });
    }

    //批量启用
    function setEnableFun() {
        var row = $('#tableData').datagrid('getChecked');
        if(row.length == 0) {
            $.messager.alert('启用提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0) {
            $.messager.confirm('启用确认','确定要启用选中的' + row.length + '条信息吗？',function(r) {
                if (r) {
                    var ids = "";
                    for(var i = 0;i < row.length; i ++) {
                        if(i == row.length-1) {
                            ids += row[i].id;
                            break;
                        }
                        ids += row[i].id + ",";
                    }
                    $.ajax({
                        type:"post",
                        url: "/supplier/batchSetEnable",
                        dataType: "json",
                        async :  false,
                        data: ({
                            enabled: true,
                            supplierIDs : ids
                        }),
                        success: function (res) {
                            if(res && res.code === 200) {
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            } else {
                                $.messager.alert('启用提示', '启用信息失败，请稍后再试！', 'error');
                            }
                        },
                        //此处添加错误处理
                        error:function() {
                            $.messager.alert('启用提示','启用信息异常，请稍后再试！','error');
                            return;
                        }
                    });
                }
            });
        }
    }

    //批量禁用
    function setDisEnableFun() {
        var row = $('#tableData').datagrid('getChecked');
        if(row.length == 0) {
            $.messager.alert('禁用提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0) {
            $.messager.confirm('禁用确认','确定要禁用选中的' + row.length + '条信息吗？',function(r) {
                if (r) {
                    var ids = "";
                    for(var i = 0;i < row.length; i ++) {
                        if(i == row.length-1) {
                            ids += row[i].id;
                            break;
                        }
                        ids += row[i].id + ",";
                    }
                    $.ajax({
                        type:"post",
                        url: "/supplier/batchSetEnable",
                        dataType: "json",
                        async :  false,
                        data: ({
                            enabled: false,
                            supplierIDs : ids
                        }),
                        success: function (res) {
                            if(res && res.code === 200) {
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked", false);
                            } else {
                                $.messager.alert('禁用提示', '禁用信息失败，请稍后再试！', 'error');
                            }
                        },
                        //此处添加错误处理
                        error:function() {
                            $.messager.alert('禁用提示','禁用信息异常，请稍后再试！','error');
                            return;
                        }
                    });
                }
            });
        }
    }

    //导入数据
    function setInputFun(){
        //IE下不允许编辑 input=file的值  解决思路：重新克隆input=file，把这个input元素复制一个，然后将原来的删除。
        //在IE下复制元素的时候，其中的值是不会被复制的，所以就达到了清空文件域的目的了。
        //而在Firefox下，其中的值也会被一同复制，清空一下就做到兼容
        var fileUploadInput = $("#supplierFile");
        fileUploadInput.after(fileUploadInput.clone().val(""));
        fileUploadInput.remove();
        $("#isCheck").val(1);
        $('#importExcelDlg').dialog('open').dialog('setTitle','导入' + listType + '信息');
        $(".window-mask").css({ width: webW-20 ,height: webH});
        $("#supplierFile").focus();
    }

    //导出数据
    function setOutputFun(){
        var supplier = $.trim($("#searchSupplier").val());
        var phonenum = $.trim($("#searchPhonenum").val());
        var telephone = $.trim($("#searchTelephone").val());
        var description = $.trim($("#searchDesc").val());
        window.location.href = "/supplier/exportExcel?browserType=" + getOs()
            + "&supplier=" + supplier + "&type=" + listType + "&phonenum=" + phonenum + "&telephone=" + telephone + "&description=" + description;
    }

    //增加单位
    var url;
    var supplierID = 0;
    //保存编辑前的名称
    var oldSupplier = "";

    function addSuppler() {
        if(checkPower()){
            return;
        }
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="/js/easyui/themes/icons/edit_add.png"/>&nbsp;增加'+listType+"信息");
        $(".window-mask").css({ width: webW ,height: webH});
        $("#supplier").focus();
        $('#supplierFM').form('clear');
        oldSupplier = "";
        supplierID = 0;
        url = '/supplier/add';
    }

    function bindEvent(){
        //导入excel对话框
        $('#importExcelDlg').dialog({
            width: 400,
            closed: true,
            cache: false,
            modal: true,
            collapsible:false,
            closable: true,
            buttons:'#dlg-buttons5'
        });
        //导入excel表格
        $("#saveimport").unbind().bind({
            click:function() {
                if($("#supplierFile").val().length == 0)
                {
                    $.messager.alert('提示','请选择文件！','info');
                    return;
                }
                $("#importExcelFM").submit();
                $('#importExcelDlg').dialog('close');

                $.messager.progress({
                    title:'请稍候',
                    msg:'数据处理ing...'
                });
                setTimeout(function(){
                    $.messager.progress('close');
                    var opts = $("#tableData").datagrid('options');
                    showSupplierDetails(opts.pageNumber,opts.pageSize);
                },3300);
            }
        });
        //保存信息
        $("#saveSupplier").off("click").on("click", function () {
            if(validateForm("supplierFM")) {
                return;
            }
            if (checkSupplierName()) {
                return;
            }
            var reg = /^([0-9])+$/;
            var phonenum = $.trim($("#phonenum").val());
            if(phonenum.length>0 && !reg.test(phonenum)) {
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

            var obj = $("#supplierFM").serializeObject();
            obj.type = listType;
            obj.enabled = 1;
            $.ajax({
                url: url,
                type:"post",
                dataType: "json",
                data:{
                    info: JSON.stringify(obj)
                },
                success: function(res) {
                    if(res && res.code === 200) {
                        $('#supplierDlg').dialog('close');
                        //加载完以后重新初始化
                        var opts = $("#tableData").datagrid('options');
                        showSupplierDetails(opts.pageNumber, opts.pageSize);
                    }
                }
            });
        });

        //初始化键盘enter事件
        $(document).keydown(function(event) {
            //兼容 IE和firefox 事件
            var e = window.event || event;
            var k = e.keyCode||e.which||e.charCode;
            //兼容 IE,firefox 兼容
            var obj = e.srcElement ? e.srcElement : e.target;
            //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件
            if(k == "13"&&(obj.id=="supplier" || obj.id=="contacts"|| obj.id=="phonenum"
                || obj.id=="email" || obj.id=="description" ))
            {
                $("#saveSupplier").click();
            }

            //搜索按钮添加快捷键
            if(k == "13"&&(obj.id=="searchSupplier" || obj.id=="searchContacts"|| obj.id=="searchPhonenum"
                || obj.id=="searchEmail" || obj.id=="searchDesc" ))
            {
                $("#searchBtn").click();
            }
        });

        //搜索处理
        $("#searchBtn").unbind().bind({
            click:function() {
                showSupplierDetails(1,initPageSize);
                var opts = $("#tableData").datagrid('options');
                var pager = $("#tableData").datagrid('getPager');
                opts.pageNumber = 1;
                opts.pageSize = initPageSize;
                pager.pagination('refresh', {
                    pageNumber:1,
                    pageSize:initPageSize
                });
            }
        });

        $("#searchBtn").click();

        //重置按钮
        $("#searchResetBtn").unbind().bind({
            click:function(){
                $("#searchSupplier").textbox("clear");
                $("#searchPhonenum").textbox("clear");
                $("#searchTelephone").textbox("clear");
                $("#searchDesc").textbox("clear");
                //加载完以后重新初始化
                $("#searchBtn").click();
            }
        });

    }

    //编辑信息
    function editSupplier(index) {
        var res = $("#tableData").datagrid("getRows")[index];
        var sId = res.id;
        var beginNeedGet = res.beginneedget;
        var beginNeedPay = res.beginneedpay;
        var row = {
            supplier : res.supplier,
            contacts : res.contacts,
            phonenum : res.phonenum,
            email : res.email,
            BeginNeedGet : beginNeedGet == "0"? "":beginNeedGet,
            BeginNeedPay : beginNeedPay == "0"? "":beginNeedPay,
            AllNeedGet: "",
            AllNeedPay: "",
            description : res.description,
            type : res.type,
            fax : res.fax,
            telephone : res.telephone,
            address : res.address,
            taxNum : res.taxnum,
            bankName : res.bankname,
            accountNumber : res.accountnumber,
            taxRate : res.taxrate
        };
        oldSupplier = res.supplier;
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="/js/easyui/themes/icons/pencil.png"/>&nbsp;编辑'+listType +"信息");
        $(".window-mask").css({ width: webW ,height: webH});
        $('#supplierFM').form('load',row);
        supplierID = sId;
        //焦点在名称输入框==定焦在输入文字后面
        $("#supplier").focus().val(res.supplier);
        url = '/supplier/update?id=' + sId;

        //显示累计应收和累计应付
        var thisDateTime = getNowFormatDateTime(); //当前时间
        var supType = "customer";
        if(listType === "客户"){
            supType = "customer"
        }
        else if(listType === "供应商"){
            supType = "vendor"
        }
        $.ajax({
            type:"get",
            url: "/depotHead/findTotalPay",
            dataType: "json",
            async:  false,
            data: ({
                supplierId: sId,
                endTime:thisDateTime,
                supType: supType
            }),
            success: function(res){
                if (res && res.code === 200 && res.data && res.data.rows && res.data.rows.getAllMoney !== "") {
                    var moneyA = res.data.rows.getAllMoney.toFixed(2)-0;
                    $.ajax({
                        type:"get",
                        url: "/accountHead/findTotalPay",
                        dataType: "json",
                        async:  false,
                        data: ({
                            supplierId: sId,
                            endTime:thisDateTime,
                            supType: supType
                        }),
                        success: function(res){
                            if (res && res.code === 200 && res.data && res.data.rows && res.data.rows.getAllMoney !== "") {
                                var moneyB = res.data.rows.getAllMoney.toFixed(2)-0;
                                var money = moneyA+moneyB;
                                var moneyBeginNeedGet = $("#BeginNeedGet").val()-0; //期初应收
                                var moneyBeginNeedPay = $("#BeginNeedPay").val()-0; //期初应付
                                if(listType === "客户"){
                                    money = (money + moneyBeginNeedGet - moneyBeginNeedPay).toFixed(2);
                                    $("#AllNeedGet").val(money);  //累计应收
                                }
                                else if(listType === "供应商"){
                                    money = (money + moneyBeginNeedPay - moneyBeginNeedGet).toFixed(2);
                                    $("#AllNeedPay").val(money); //累计应付
                                }
                            }
                        },
                        error: function(){
                            $.messager.alert('提示','网络异常请稍后再试！','error');
                            return;
                        }
                    });
                }
            },
            error: function(){
                $.messager.alert('提示','网络异常请稍后再试！','error');
                return;
            }
        });
    }


    //检查单位名称是否存在 ++ 重名无法提示问题需要跟进
    function checkSupplierName() {
        var supplierName = $.trim($("#supplier").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if(supplierName.length > 0 &&( oldSupplier.length ==0 || supplierName != oldSupplier)) {
            $.ajax({
                type:"get",
                url: "/supplier/checkIsNameExist",
                dataType: "json",
                async :  false,
                data: ({
                    id : supplierID,
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


    function showSupplierDetails(pageNo,pageSize) {
        var supplier = $.trim($("#searchSupplier").val());
        var phonenum = $.trim($("#searchPhonenum").val());
        var telephone = $.trim($("#searchTelephone").val());
        var description = $.trim($("#searchDesc").val());
        $.ajax({
            type:"get",
            url: "/supplier/list",
            dataType: "json",
            data: ({
                search: JSON.stringify({
                    supplier: supplier,
                    type: listType,
                    phonenum: phonenum,
                    telephone: telephone,
                    description: description
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
            error:function() {
                $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
                return;
            }
        });
    }

