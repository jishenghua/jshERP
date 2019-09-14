/*!
 * 首页Tab页签通用方法
 * @author jishenghua
 * @version 2017-3-26
 */
(function (b) {
    var a = {
        tabPageId: null, initTabPage: function (e, c) {
            this.tabPageId = e;
            var d = b.extend(true, {
                autoResizable: true, height: function () {
                    return b(window).height()
                }, items: []
            }, c);
            return b("#" + this.tabPageId).tabPanel(d)
        }, addTabPage: function (g, h, d, f, e) {
            var c = g ? g.data("tabId") : null;
            if (c == undefined) {
                c = "tabpanel-" + Math.uuid();
                g ? g.attr("data-tab-id", c) : null
            }
            b("#" + this.tabPageId).tabPanel("addTab", {
                id: c,
                title: h,
                html: '<script>js.loading();<\/script><iframe id="' + c + '-frame" src="' + d + '" width="100%" height="100%" frameborder="0" onload="js.closeLoading();"></iframe>',
                closable: (f == undefined ? true : f),
                refresh: (e == undefined ? false : e),
                onPreClose: function (i) {
                    js.closeLoading(1000, true);
                    try {
                        var k = b("#" + c + "-frame")[0].contentWindow;
                        if (k && typeof k.onTablePageClose == "function") {
                            return k.onTablePageClose(c, h)
                        }
                    } catch (j) {
                        js.error(j)
                    }
                }
            });
            return c
        }, getCurrentTabPage: function (d) {
            var c = b("#" + this.tabPageId).tabPanel("getActiveTab");
            var g = b("#" + c.id + "-frame");
            if (g.length > 0 && typeof d == "function") {
                try {
                    d(g[0].contentWindow)
                } catch (f) {
                    js.error(f)
                }
            }
            return g
        }, getPrevTabPage: function (c, f) {
            var d = b("#" + this.tabPageId).tabPanel("getActiveTab");
            var h = b("#" + d.preTabId + "-frame");
            if (h.length > 0 && typeof c == "function") {
                try {
                    c(h[0].contentWindow)
                } catch (g) {
                    js.error(g)
                }
            }
            if (f == true) {
                b("#" + this.tabPageId).tabPanel("kill", d.id)
            }
            return h
        }, closeCurrentTabPage: function (c) {
            this.getPrevTabPage(c, true)
        }
    };
    window.tabPage = a
})(window.jQuery);