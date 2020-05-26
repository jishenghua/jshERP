if (self.frameElement && self.frameElement.tagName == "IFRAME") {
    top.location.reload();
    /*!
     * Copyright (c) 2013-Now 华夏erp All rights reserved.
     *
     * @author jishenghua
     * @version 2019-09-14
     */
}
+function (d) {
    var b = "lte.pushmenu";
    var e = {collapseScreenSize: 767, expandOnHover: false, expandTransitionDelay: 50};
    var a = {
        collapsed: ".sidebar-collapse",
        open: ".sidebar-open",
        mainSidebar: ".main-sidebar",
        contentWrapper: ".content-wrapper",
        searchInput: ".sidebar-form .form-control",
        button: '[data-toggle="push-menu"]',
        mini: ".sidebar-mini",
        expanded: ".sidebar-expanded-on-hover",
        layoutFixed: ".fixed"
    };
    var h = {
        collapsed: "sidebar-collapse",
        open: "sidebar-open",
        mini: "sidebar-mini",
        expanded: "sidebar-expanded-on-hover",
        expandFeature: "sidebar-mini-expand-feature",
        layoutFixed: "fixed"
    };
    var i = {expanded: "expanded.pushMenu", collapsed: "collapsed.pushMenu"};
    var g = function (j) {
        this.options = j;
        this.init()
    };
    g.prototype.init = function () {
        if (this.options.expandOnHover || (d("body").is(a.mini + a.layoutFixed))) {
            this.expandOnHover();
            d("body").addClass(h.expandFeature)
        }
        d(a.contentWrapper).click(function () {
            if (d(window).width() <= this.options.collapseScreenSize && d("body").hasClass(h.open)) {
                this.close()
            }
        }.bind(this));
        d(a.searchInput).click(function (j) {
            j.stopPropagation()
        })
    };
    g.prototype.toggle = function () {
        var k = d(window).width();
        var j = !d("body").hasClass(h.collapsed);
        if (k <= this.options.collapseScreenSize) {
            j = d("body").hasClass(h.open)
        }
        if (!j) {
            this.open()
        } else {
            this.close()
        }
        window.setTimeout(function () {
            d(window).resize()
        }, 100)
    };
    g.prototype.open = function () {
        var j = d(window).width();
        if (j > this.options.collapseScreenSize) {
            d("body").removeClass(h.collapsed).trigger(d.Event(i.expanded))
        } else {
            d("body").addClass(h.open).trigger(d.Event(i.expanded))
        }
    };
    g.prototype.close = function () {
        var j = d(window).width();
        if (j > this.options.collapseScreenSize) {
            d("body").addClass(h.collapsed).trigger(d.Event(i.collapsed))
        } else {
            d("body").removeClass(h.open + " " + h.collapsed).trigger(d.Event(i.collapsed))
        }
    };
    g.prototype.expandOnHover = function () {
        d(a.mainSidebar).hover(function () {
            if (d("body").is(a.mini + a.collapsed) && d(window).width() > this.options.collapseScreenSize) {
                this.expand()
            }
        }.bind(this), function () {
            if (d("body").is(a.expanded)) {
                this.collapse()
            }
        }.bind(this))
    };
    g.prototype.expand = function () {
        setTimeout(function () {
            d("body").removeClass(h.collapsed).addClass(h.expanded)
        }, this.options.expandTransitionDelay)
    };
    g.prototype.collapse = function () {
        setTimeout(function () {
            d("body").removeClass(h.expanded).addClass(h.collapsed)
        }, this.options.expandTransitionDelay)
    };

    function f(j) {
        return this.each(function () {
            var m = d(this);
            var l = m.data(b);
            if (!l) {
                var k = d.extend({}, e, m.data(), typeof j == "object" && j);
                m.data(b, (l = new g(k)))
            }
            if (j == "toggle") {
                l.toggle()
            }
        })
    }

    var c = d.fn.pushMenu;
    d.fn.pushMenu = f;
    d.fn.pushMenu.Constructor = g;
    d.fn.pushMenu.noConflict = function () {
        d.fn.pushMenu = c;
        return this
    };
    d(document).on("click", a.button, function (j) {
        j.preventDefault();
        f.call(d(this), "toggle")
    });
    d(function () {
        f.call(d(a.button));
        d(a.button).css({cursor: "pointer"})
    })
}(jQuery) + function (e) {
    var b = "lte.tree";
    var f = {animationSpeed: 50, accordion: true, followLink: true, trigger: ".treeview a"};
    var a = {
        tree: ".tree",
        treeview: ".treeview",
        treeviewMenu: ".treeview-menu",
        open: ".menu-open, .active",
        li: "li",
        data: '[data-widget="tree"]',
        active: ".active"
    };
    var h = {open: "menu-open", tree: "tree"};
    var i = {collapsed: "collapsed.tree", expanded: "expanded.tree"};
    var d = function (k, j) {
        this.element = k;
        this.options = j;
        e(this.element).addClass(h.tree);
        e(a.treeview + a.active, this.element).addClass(h.open);
        this._setUpListeners()
    };
    d.prototype.toggle = function (n, m) {
        var l = n.next(a.treeviewMenu);
        var j = n.parent();
        var k = j.hasClass(h.open);
        if (!j.is(a.treeview)) {
            return
        }
        if (!this.options.followLink || n.attr("href") == "#") {
            m.preventDefault()
        }
        if (k) {
            this.collapse(l, j)
        } else {
            this.expand(l, j)
        }
        if (l.children().length === 0) {
            e(".active", this.element).removeClass("treeview-item active");
            j.parents(".treeview:not(.active)").addClass("menu-open active");
            j.addClass("treeview-item active")
        }
    };
    d.prototype.expand = function (k, l) {
        var n = e.Event(i.expanded);
        if (this.options.accordion) {
            var j = l.siblings(a.open);
            var m = j.children(a.treeviewMenu);
            this.collapse(m, j)
        }
        l.addClass(h.open);
        k.slideDown(this.options.animationSpeed, function () {
            e(this.element).trigger(n)
        }.bind(this))
    };
    d.prototype.collapse = function (k, j) {
        var l = e.Event(i.collapsed);
        k.find(a.open).removeClass(h.open);
        j.removeClass(h.open);
        k.slideUp(this.options.animationSpeed, function () {
            k.find(a.open + " > " + a.treeview).slideUp();
            e(this.element).trigger(l)
        }.bind(this))
    };
    d.prototype._setUpListeners = function () {
        var j = this;
        e(this.element).on("click", this.options.trigger, function (k) {
            j.toggle(e(this), k)
        })
    };

    function g(j) {
        return this.each(function () {
            var m = e(this);
            var l = m.data(b);
            if (!l) {
                var k = e.extend({}, f, m.data(), typeof j == "object" && j);
                m.data(b, new d(m, k))
            }
        })
    }

    var c = e.fn.tree;
    e.fn.tree = g;
    e.fn.tree.Constructor = d;
    e.fn.tree.noConflict = function () {
        e.fn.tree = c;
        return this
    };
    e(function () {
        e(a.data).each(function () {
            g.call(e(this))
        })
    })
}(jQuery);
$(function () {
    js.initTabPage("tabpanel", {
        height: function () {
            var g = $(window).height(), e = $(".main-header:visible").outerHeight(),
                f = $(".main-footer:visible").outerHeight(), d = g - e - f;
            return d < 300 ? 300 : d
        }
    });
    $(window).resize();
    var b = $("#desktopTabPage");
    if (b.length > 0) {
        var a = b.data("url");
        if (a != "" && a != ctx) {
            js.addTabPage(null, '<i class="fa fa-home"></i> ' + b.data("title"), a, false, false)
        }
    }
    var c = $("#modifyPasswordTip");
    if (c.length > 0 && c.data("message") != "") {
        js.confirm(c.data("message"), function () {
            $("#modifyPassword").click()
        })
    }
    window.isMenuClickFlag = false;
    $(window).bind("hashchange", function (h) {
        if (!window.isMenuClickFlag) {
            var g = window.location.hash.replace("#", "");
            if (g && g != "" && g != window.location.pathname) {
                var f = $('a.addTabPage[data-href="' + g + '"]:eq(0)');
                if (f && f.length > 0) {
                    f.click()
                } else {
                    if ($(".sidebar-menu").length > 0) {
                        js.addTabPage(null, js.text("tabpanel.newTabPage"), g)
                    }
                }
            } else {
                var d = $(".sidebar-menu > li:eq(0):not(.active) > a:eq(0)");
                if (d.data("href") == "blank") {
                    d.click()
                }
            }
        }
        window.isMenuClickFlag = false
    }).trigger("hashchange");
    $("#fullScreen").click(function () {
        if ($(this).data("isOpen") == "true") {
            $(this).data("isOpen", "false");
            if (document.exitFullscreen) {
                document.exitFullscreen()
            } else {
                if (document.msExitFullscreen) {
                    document.msExitFullscreen()
                } else {
                    if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen()
                    } else {
                        if (document.webkitCancelFullScreen) {
                            document.webkitCancelFullScreen()
                        }
                    }
                }
            }
        } else {
            $(this).data("isOpen", "true");
            var d = document.documentElement;
            if (d.requestFullscreen) {
                d.requestFullscreen()
            } else {
                if (d.msRequestFullscreen) {
                    d.msRequestFullscreen()
                } else {
                    if (d.mozRequestFullScreen) {
                        d.mozRequestFullScreen()
                    } else {
                        if (d.webkitRequestFullScreen) {
                            d.webkitRequestFullScreen()
                        }
                    }
                }
            }
        }
        return false
    }).tooltip();
    $("#switchSkin").click(function () {
        js.layer.open({
            type: 2,
            shadeClose: true,
            title: $(this).attr("data-original-title"),
            area: ["530px", "440px"],
            content: ctx + "/switchSkin/select",
            success: function (d, e) {
                if ($(js.layer.window).width() < 500 || $(js.layer.window).height() < 390) {
                    js.layer.full(e)
                }
            },
        })
    }).tooltip()
});
+function (h) {
    var g = "lte.layout";
    var c = {slimscroll: true, resetHeight: true};
    var b = {
        wrapper: ".wrapper",
        contentWrapper: ".content-wrapper",
        layoutBoxed: ".layout-boxed",
        mainFooter: ".main-footer:visible",
        mainHeader: ".main-header:visible",
        sidebar: ".sidebar",
        controlSidebar: ".control-sidebar",
        fixed: ".fixed",
        sidebarMenu: ".sidebar-menu",
        logo: ".main-header .logo"
    };
    var f = {fixed: "fixed", holdTransition: "hold-transition"};
    var e = function (i) {
        this.options = i;
        this.bindedResize = false;
        this.activate()
    };
    e.prototype.activate = function () {
        this.fix();
        this.fixSidebar();
        h("body").removeClass(f.holdTransition);
        if (!this.bindedResize) {
            h(window).resize(function () {
                this.fix();
                this.fixSidebar();
                h(b.logo + ", " + b.sidebar).one("webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend", function () {
                    this.fix();
                    this.fixSidebar()
                }.bind(this));
                var i = this;
                setTimeout(function () {
                    i.fix();
                    i.fixSidebar()
                }, 500)
            }.bind(this));
            this.bindedResize = true
        }
    };
    e.prototype.fix = function () {
    };
    e.prototype.fixSidebar = function () {
        if (!h("body").hasClass(f.fixed)) {
            return
        }
        if (this.options.slimscroll) {
            if (typeof h.fn.slimScroll !== "undefined") {
                h(b.sidebar).slimScroll({height: (h(b.contentWrapper).height()) + "px", color: "#aaa", size: "3px"})
            }
        }
    };

    function d(i) {
        return this.each(function () {
            var l = h(this);
            var k = l.data(g);
            if (!k) {
                var j = h.extend({}, c, l.data(), typeof i === "object" && i);
                l.data(g, (k = new e(j)))
            }
            if (typeof i === "string") {
                if (typeof k[i] === "undefined") {
                    throw new Error("No method named " + i)
                }
                k[i]()
            }
        })
    }

    var a = h.fn.layout;
    h.fn.layout = d;
    h.fn.layout.Constuctor = e;
    h.fn.layout.noConflict = function () {
        h.fn.layout = a;
        return this
    };
    h(function () {
        d.call(h("body"))
    })
}(jQuery);


////////////////////
$(function () {
    $('#sidebarLoginOut, #loginOut').off("click").on("click", function () {
        $.messager.confirm("提示","确认要退出系统吗？",function(r){
            if(r) {
                sessionStorage.removeItem("userId");
                location.href = '/user/logout';
            }
        });
    });

    //广告循环
    var loginName = sessionStorage.getItem("loginName");
    if(loginName == "jsh") {
        $(".tencent").show();
        $(".aliyun").hide();
    }

    //更新消息条数
    function getMsgCountByStatus() {
        $.ajax({
            type: "get",
            url: "/msg/getMsgCountByStatus?status=1",
            dataType: "json",
            success: function (res) {
                if (res && res.code === 200) {
                    if(res.data) {
                        var count = res.data.count;
                        $("#msgNum, #msgNum2").text(count);
                    }
                }
            }
        });
    }
    getMsgCountByStatus();
    setInterval(function() {
        getMsgCountByStatus()
    }, 1000*60);

});