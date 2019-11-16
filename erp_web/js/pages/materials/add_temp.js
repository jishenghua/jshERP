$.get("../../pages/template/base.html?789", function(tem) {
    if(tem) {
        var template = Handlebars.compile(tem);
        /**
         * 加载供应商、客户模块
         */
        $("#supplier").html(template({
            supplierSelect: true
        }));
        $('#supplierDlg').dialog({
            closed: true,
            modal: true,
            collapsible: false,
            closable: true
        });
        $("#supplierDlg #supplier").validatebox({
            required: true,
            validType: 'length[2,30]'
        });
        $("#supplierDlg #email").validatebox({
            validType: 'email'
        });
        $("#BeginNeedGet,#BeginNeedPay,#AllNeedGet,#AllNeedPay").numberbox({
            min:0,
            precision:2
        });
        $("#saveSupplier").linkbutton({
            iconCls: 'icon-ok'
        });
        $("#cancelSupplier").linkbutton({
            iconCls: 'icon-cancel'
        });

        /**
         * 加载仓库
         */
        $("#depot").html(template({
            depotSelect: true
        }));
        $('#depotDlg').dialog({
            closed: true,
            modal: true,
            collapsible: false,
            closable: true
        });
        $("#depotDlg #name,#depotDlg #address").validatebox({
            required: true,
            validType: 'length[2,30]'
        });
        $("#depotDlg #warehousing,#depotDlg #truckage").numberbox({
            min:0,
            precision:2
        });
        $("#saveDepot").linkbutton({
            iconCls: 'icon-ok'
        });
        $("#cancelDepot").linkbutton({
            iconCls: 'icon-cancel'
        });

        /**
         * 加载账户
         */
        $("#account").html(template({
            accountSelect: true
        }));
        $('#accountDlg').dialog({
            closed: true,
            modal: true,
            collapsible: false,
            closable: true
        });
        $("#accountDlg #name,#accountDlg #serialNo").validatebox({
            required: true,
            validType: 'length[2,30]'
        });
        $("#accountDlg #initialAmount").numberbox({
            min:0,
            precision:2
        });
        $("#saveAccount").linkbutton({
            iconCls: 'icon-ok'
        });
        $("#cancelAccount").linkbutton({
            iconCls: 'icon-cancel'
        });
    }
});

//绑定供应商、客户事件
function bindSupplierEvent() {
    if(listTitle === "采购入库列表" || listTitle === "其它入库列表" || listTitle === "采购订单列表"
        || listTitle === "零售出库列表"|| listTitle === "销售出库列表"|| listTitle === "销售订单列表"){
        var supplierType = "供应商";
        if(listTitle === "零售出库列表"){
            supplierType = "会员";
        }else if(listTitle === "销售出库列表" || listTitle === "销售订单列表"){
            supplierType = "客户";
        }
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
}

//绑定仓库事件
function bindDepotEvent() {
    $("#depotFM #name").focus();
    $("#selectType").val("principal");
    //检查名称是否存在 ++ 重名无法提示问题需要跟进
    function checkDepotName() {
        var name = $.trim($("#depotDlg #name").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if (name.length > 0) {
            $.ajax({
                type: "get",
                url: "/depot/checkIsNameExist",
                dataType: "json",
                async: false,
                data: ({
                    id: 0,
                    name: name
                }),
                success: function (res) {
                    if(res && res.code === 200) {
                        if(res.data && res.data.status) {
                            flag = res.data.status;
                            if (flag) {
                                $.messager.alert('提示', '仓库名称已经存在', 'info');
                                return;
                            }
                        }
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('提示', '检查仓库名称是否存在异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
        return flag;
    }
    $("#saveDepot").off("click").on("click", function () {
        var infoObj = $("#depotFM").serializeObject();
        infoObj.type = 0;
        if (checkDepotName()) {
            return;
        }
        $.ajax({
            url: "/depot/add",
            type: "post",
            dataType: "json",
            data: ({
                info: JSON.stringify(infoObj)
            }),
            success: function(res) {
                if(res && res.code === 200) {
                    $('#depotDlg').dialog('close');
                }
            },
            //此处添加错误处理
            error: function () {
                $.messager.alert('提示', '保存仓库信息异常，请稍后再试！', 'error');
                return;
            }
        });
    });
}

//绑定账户事件
function bindAccountEvent() {
    function checkAccountName() {
        var accountName = $.trim($("#accountDlg #name").val());
        //表示是否存在 true == 存在 false = 不存在
        var flag = false;
        //开始ajax名称检验，不能重名
        if (accountName.length > 0) {
            $.ajax({
                type: "get",
                url: "/account/checkIsNameExist",
                dataType: "json",
                async: false,
                data: ({
                    id: 0,
                    name: accountName
                }),
                success: function (res) {
                    if(res && res.code === 200) {
                        if(res.data && res.data.status) {
                            flag = res.data.status;
                            if (flag) {
                                $.messager.alert('提示', '结算账户名称已经存在', 'info');
                                return;
                            }
                        }
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('提示', '检查结算账户名称是否存在异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
        return flag;
    }
    //保存结算账户
    $("#saveAccount").off("click").on("click", function () {
        if (checkAccountName()) {
            return;
        }
        $.ajax({
            url: '/account/add',
            type: "post",
            dataType: "json",
            data: ({
                info: JSON.stringify($("#accountFM").serializeObject())
            }),
            success: function(res) {
                if(res && res.code === 200) {
                    $('#accountDlg').dialog('close');
                    initSystemData_account(); //刷新账户
                }
            },
            //此处添加错误处理
            error: function () {
                $.messager.alert('提示', '保存结算账户异常，请稍后再试！', 'error');
                return;
            }
        });
    });
}