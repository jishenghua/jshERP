$.get("../../pages/template/base.html", function(tem) {
    if(tem) {
        var template = Handlebars.compile(tem);
        /**
         * 加载供应商、客户模块
         */
        $("body").append(template({
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
    }
});