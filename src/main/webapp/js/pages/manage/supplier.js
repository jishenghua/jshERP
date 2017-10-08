    //初始化界面
    $(function() {
        var listTitle = ""; //单据标题
        var listType = ""; //类型
        var listTypeEn = ""; //英文类型
        getType();
        getPersonList(); //获取业务员
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

    //获取业务员
    function getPersonList(){
        $.ajax({
            type:"post",
            url: path + "/person/getBasicData.action",
            dataType: "json",
            success: function (systemInfo)
            {
                var msgTip = systemInfo.showModel.msgTip;
                if(msgTip !== "exceptoin"){
                    var personList = systemInfo.showModel.map.personList;
                    var personID,options;
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
                                options += '<option value="' + person.id + '">' + person.name + '</option>';
                            }
                        }
                        $("#personId").empty().append('<option>不关联业务员</option>').append(options);
                    }
                }
                else {
                    $.messager.alert('提示','查找系统基础信息异常,请与管理员联系！','error');
                    return;
                }
            }
        });
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
                { title: '操作',field: 'op',align:"center",width:60,formatter:function(value,rec)
                    {
                        var str = '';
                        var rowInfo = rec.id + 'AaBb' + rec.supplier +'AaBb' + rec.contacts + 'AaBb'+ rec.phonenum + 'AaBb'+ rec.email + 'AaBb'+ rec.BeginNeedGet + 'AaBb'+ rec.BeginNeedPay + 'AaBb' + rec.isystem + 'AaBb' + rec.description+ 'AaBb' + rec.type
                            + 'AaBb' + rec.fax + 'AaBb' + rec.telephone + 'AaBb' + rec.address + 'AaBb' + rec.taxNum + 'AaBb' + rec.bankName + 'AaBb' + rec.accountNumber + 'AaBb' + rec.taxRate;
                        if(1 == value)
                        {
                            str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editSupplier(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                            str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteSupplier(\'' + rowInfo + '\');"/>';
                        }
                        return str;
                    }
                },
                { title: '名称',field: 'supplier',width:150},
                { title: '联系人', field: 'contacts',width:50,align:"center"},
                { title: '手机号码', field: 'telephone',width:100,align:"center"},
                { title: '电子邮箱',field: 'email',width:80,align:"center"},
                { title: '联系电话', field: 'phonenum',width:100,align:"center"},
                { title: '传真', field: 'fax',width:100,align:"center"},
                { title: '预付款',field: 'AdvanceIn',width:70,align:"center"},
                { title: '期初应收',field: 'BeginNeedGet',width:70,align:"center"},
                { title: '期初应付',field: 'BeginNeedPay',width:70,align:"center"},
                { title: '税率', field: 'taxRate',width:50,align:"center"},
                { title: '状态',field: 'enabled',width:70,align:"center",formatter:function(value){
                    return value? "启用":"禁用";
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
            onLoadError:function()
            {
                $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                return;
            }
        });
    }


    //分页信息处理
    function ininPager() {
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
                    showSupplierDetails(pageNum,pageSize);
                }
            });
        }
        catch (e)
        {
            $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
        }
    }

    //删除信息
    function deleteSupplier(supplierInfo) {
        $.messager.confirm('删除确认','确定要删除此条信息吗？',function(r)
        {
            if (r)
            {
                var supplierTotalInfo = supplierInfo.split("AaBb");
                $.ajax({
                    type:"post",
                    url: path + "/supplier/delete.action",
                    dataType: "json",
                    data: ({
                        supplierID : supplierTotalInfo[0],
                        supplier:supplierTotalInfo[1],
                        clientIp: clientIp
                    }),
                    success: function (tipInfo)
                    {
                        var msg = tipInfo.showModel.msgTip;
                        if(msg == '成功')
                        //加载完以后重新初始化
                            $("#searchBtn").click();
                        else
                            $.messager.alert('删除提示','删除信息失败，请稍后再试！','error');
                    },
                    //此处添加错误处理
                    error:function()
                    {
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
        if(row.length == 0)
        {
            $.messager.alert('删除提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0)
        {
            $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条信息吗？',function(r)
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
                        url: path + "/supplier/batchDelete.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            supplierIDs : ids,
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
                                $.messager.alert('删除提示','删除信息失败，请稍后再试！','error');
                        },
                        //此处添加错误处理
                        error:function()
                        {
                            $.messager.alert('删除提示','删除信息异常，请稍后再试！','error');
                            return;
                        }
                    });
                }
            });
        }
    }

    //批量启用
    function setEnableFun() {
        var row = $('#tableData').datagrid('getChecked');
        if(row.length == 0)
        {
            $.messager.alert('启用提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0)
        {
            $.messager.confirm('启用确认','确定要启用选中的' + row.length + '条信息吗？',function(r)
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
                        url: path + "/supplier/batchSetEnable.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            enabled: true,
                            supplierIDs : ids,
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
                                $.messager.alert('启用提示','启用信息失败，请稍后再试！','error');
                        },
                        //此处添加错误处理
                        error:function()
                        {
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
        if(row.length == 0)
        {
            $.messager.alert('禁用提示','没有记录被选中！','info');
            return;
        }
        if(row.length > 0)
        {
            $.messager.confirm('禁用确认','确定要禁用选中的' + row.length + '条信息吗？',function(r)
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
                        url: path + "/supplier/batchSetEnable.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            enabled: false,
                            supplierIDs : ids,
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
                                $.messager.alert('禁用提示','禁用信息失败，请稍后再试！','error');
                        },
                        //此处添加错误处理
                        error:function()
                        {
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
        window.location.href = path + "/supplier/exportExcel.action?browserType=" + getOs() + "&type=" + listTypeEn;
    }

    //增加单位
    var url;
    var supplierID = 0;
    //保存编辑前的名称
    var orgSupplier = "";

    function addSuppler() {
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加'+listType+"信息");
        $(".window-mask").css({ width: webW ,height: webH});
        $("#supplier").focus();
        $('#supplierFM').form('clear');
        orgSupplier = "";
        supplierID = 0;
        url = path + '/supplier/create.action';
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
            click:function()
            {
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
        $("#saveSupplier").off("click").on("click",function() {
            if(!$('#supplierFM').form('validate')){
                return;
            }
            else if(checkSupplierName()){
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

            $.ajax({
                url: url,
                type:"post",
                dataType: "json",
                data:{
                    supplier:$("#supplier").val(),
                    type: listType,
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
                        var opts = $("#tableData").datagrid('options');
                        showSupplierDetails(opts.pageNumber,opts.pageSize);
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
            click:function()
            {
                showSupplierDetails(1,initPageSize);
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

        $("#searchBtn").click();

        //重置按钮
        $("#searchResetBtn").unbind().bind({
            click:function(){
                $("#searchSupplier").val("");
                $("#searchType").val("");
                $("#searchPhonenum").val("");
                $("#searchTelephone").val("");
                $("#searchDesc").val("");

                //加载完以后重新初始化
                $("#searchBtn").click();
            }
        });

    }


    //编辑信息
    function editSupplier(supplierTotalInfo) {
        var supplierInfo = supplierTotalInfo.split("AaBb");
        var row = {
            supplier : supplierInfo[1],
            contacts : supplierInfo[2].replace("undefined",""),
            phonenum : supplierInfo[3].replace("undefined",""),
            email : supplierInfo[4].replace("undefined",""),
            BeginNeedGet : supplierInfo[5],
            BeginNeedPay : supplierInfo[6],
            AllNeedGet: "",
            AllNeedPay: "",
            description : supplierInfo[8].replace("undefined",""),
            type : supplierInfo[9],
            fax : supplierInfo[10].replace("undefined",""),
            telephone : supplierInfo[11].replace("undefined",""),
            address : supplierInfo[12].replace("undefined",""),
            taxNum : supplierInfo[13].replace("undefined",""),
            bankName : supplierInfo[14].replace("undefined",""),
            accountNumber : supplierInfo[15].replace("undefined",""),
            taxRate : supplierInfo[16].replace("undefined",""),
            clientIp: clientIp
        };
        orgSupplier = supplierInfo[1];
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑'+listType +"信息");
        $(".window-mask").css({ width: webW ,height: webH});
        $('#supplierFM').form('load',row);
        supplierID = supplierInfo[0];
        //焦点在名称输入框==定焦在输入文字后面
        $("#supplier").val("").focus().val(supplierInfo[1]);
        url = path + '/supplier/update.action?supplierID=' + supplierInfo[0];

        //显示累计应收和累计应付
        $.ajax({
            type:"post",
            url: path + "/depotHead/findTotalPay.action",
            dataType: "json",
            async:  false,
            data: ({
                supplierId: supplierInfo[0]
            }),
            success: function(res){
                if(res) {
                    var moneyA = res.getAllMoney.toFixed(2)-0;
                    $.ajax({
                        type:"post",
                        url: path +"/accountHead/findTotalPay.action",
                        dataType: "json",
                        async:  false,
                        data: ({
                            supplierId: supplierInfo[0]
                        }),
                        success: function(res){
                            if(res) {
                                var moneyB = res.getAllMoney.toFixed(2)-0;
                                var money = moneyA+moneyB;
                                var moneyBeginNeedGet = $("#BeginNeedGet").val()-0; //期初应收
                                var moneyBeginNeedPay = $("#BeginNeedPay").val()-0; //期初应付
                                money = (money + moneyBeginNeedPay - moneyBeginNeedGet).toFixed(2);
                                if(money>0) {
                                    $("#AllNeedGet").val(""); //累计应收-置空
                                    $("#AllNeedPay").val(money); //累计应付
                                }
                                else {
                                    $("#AllNeedGet").val(-money);  //累计应收
                                    $("#AllNeedPay").val(""); //累计应付-置空
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
        })
    }


    //检查单位名称是否存在 ++ 重名无法提示问题需要跟进
    function checkSupplierName() {
        var supplierName = $.trim($("#supplier").val());
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
                    supplierID : supplierID,
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


    function showSupplierDetails(pageNo,pageSize) {
        $.ajax({
            type:"post",
            url: path + "/supplier/findBy.action",
            dataType: "json",
            data: ({
                supplier:$.trim($("#searchSupplier").val()),
                type: listType,
                phonenum:$.trim($("#searchPhonenum").val()),
                telephone:$.trim($("#searchTelephone").val()),
                description:$.trim($("#searchDesc").val()),
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

