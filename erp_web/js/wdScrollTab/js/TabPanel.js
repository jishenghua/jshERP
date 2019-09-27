TabPanel = function (a, b) {
    this.renderTo = a.renderTo || $(document.body);
    this.border = a.border || "none";
    this.render = typeof b != "undefined" ? b : typeof this.renderTo == "string" ? $("#" + this.renderTo) : this.renderTo;
    this.widthResizable = a.widthResizable;
    this.heightResizable = a.heightResizable;
    this.autoResizable = a.autoResizable ? true : false;
    this.width = a.width || "100%";
    this.height = a.height || "100%";
    this.items = a.items;
    this.active = a.active || 0;
    this.tabs = [];
    this.scrolled = false;
    this.tabWidth = a.tabWidth || -1;
    this.fixNum = 2;
    this.scrollFinish = true;
    this.maxLength = a.maxLength || 20;
    this.maxzindex = 0;
    this.init()
};
TabPanel.prototype = {
    init: function () {
        var d = this;
        if (this.autoResizable) {
            this.widthResizable = this.heightResizable = true;
            this.render.css("overflow", "hidden");
            $(window).resize(function () {
                d.resize();
                window.setTimeout(function () {
                    d.resize()
                }, 200)
            })
        }
        if (typeof this.width == "function") {
            this.render.width = this.width
        } else {
            this.render.width(this.width)
        }
        if (typeof this.height == "function") {
            this.render.height = this.height
        } else {
            this.render.height(this.height)
        }
        var e = this.border != "none" ? 2 : 0;
        this.tabpanel = $("<DIV></DIV>");
        this.tabpanel.addClass("tabpanel");
        this.tabpanel.width(this.render.width() - e);
        this.tabpanel.height(this.render.height() - e);
        this.render.append(this.tabpanel);
        this.tabpanel_tab_content = $("<DIV></DIV>");
        this.tabpanel_tab_content.addClass("tabpanel_tab_content");
        if (this.maxLength == 1) {
            this.tabpanel_tab_content.addClass("hide")
        }
        this.tabpanel_tab_content.appendTo(this.tabpanel);
        this.tabpanel_left_scroll = $("<DIV></DIV>");
        this.tabpanel_left_scroll.bind("click", function () {
            d.moveLeft()
        });
        this.tabpanel_left_scroll.addClass("tabpanel_left_scroll");
        this.tabpanel_left_scroll.addClass("display_none");
        this.tabpanel_left_scroll.bind("mouseover", function () {
            var f = $(this);
            f.addClass("tabpanel_scroll_over");
            f.bind("mouseout", function () {
                f.unbind("mouseout");
                f.removeClass("tabpanel_scroll_over")
            })
        });
        this.tabpanel_left_scroll.appendTo(this.tabpanel_tab_content);
        this.tabpanel_right_scroll = $("<DIV></DIV>");
        this.tabpanel_right_scroll.bind("click", function () {
            d.moveRight()
        });
        this.tabpanel_right_scroll.addClass("tabpanel_right_scroll");
        this.tabpanel_right_scroll.addClass("display_none");
        this.tabpanel_right_scroll.bind("mouseover", function () {
            var f = $(this);
            f.addClass("tabpanel_scroll_over");
            f.bind("mouseout", function () {
                f.unbind("mouseout");
                f.removeClass("tabpanel_scroll_over")
            })
        });
        this.tabpanel_right_scroll.appendTo(this.tabpanel_tab_content);
        this.tabpanel_move_content = $("<DIV></DIV>");
        this.tabpanel_move_content.addClass("tabpanel_move_content");
        this.tabpanel_move_content.appendTo(this.tabpanel_tab_content);
        this.tabpanel_mover = $("<UL></UL>");
        this.tabpanel_mover.addClass("tabpanel_mover");
        this.tabpanel_mover.appendTo(this.tabpanel_move_content);
        this.tabpanel_tab_spacer = $("<DIV></DIV>");
        this.tabpanel_tab_spacer.addClass("tabpanel_tab_spacer");
        this.tabpanel_tab_spacer.appendTo(this.tabpanel_tab_content);
        this.tabpanel_content = $("<DIV></DIV>");
        this.tabpanel_content.addClass("tabpanel_content");
        this.tabpanel_content.appendTo(this.tabpanel);
        var a = this.tabpanel.width();
        var c = this.tabpanel.height();
        if (this.border == "none") {
            this.tabpanel.css("border", "none")
        }
        this.tabpanel_tab_content.width(a);
        this.tabpanel_content.width(a);
        this.tabpanel_content.height(c - this.tabpanel_tab_content.get(0).offsetHeight);
        this.update();
        for (var b = 0; b < this.items.length; b++) {
            this.items[b].notExecuteMoveSee = true;
            this.addTab(this.items[b])
        }
        if (this.active >= 0) {
            this.show(this.active, false)
        }
    }, moveLeft: function () {
        if (this.scrollFinish) {
            this.disableScroll();
            this.scrollFinish = false;
            Fader.apply(this, new Array({
                element: this.tabpanel_mover,
                style: "marginLeft",
                num: this.tabWidth,
                maxMove: this.maxMove,
                onFinish: this.useableScroll
            }));
            this.run()
        }
    }, moveRight: function () {
        if (this.scrollFinish) {
            this.disableScroll();
            this.scrollFinish = false;
            Fader.apply(this, new Array({
                element: this.tabpanel_mover,
                style: "marginLeft",
                num: this.tabWidth * -1,
                maxMove: this.maxMove,
                onFinish: this.useableScroll
            }));
            this.run()
        }
    }, moveToLeft: function () {
        if (this.scrolled && this.scrollFinish) {
            this.disableScroll();
            this.scrollFinish = false;
            var a = parseInt(this.tabpanel_mover.css("marginLeft")) * -1;
            Fader.apply(this, new Array({
                element: this.tabpanel_mover,
                style: "marginLeft",
                num: a,
                maxMove: this.maxMove,
                interval: 20,
                step: (a / 10) < 10 ? 10 : a / 10,
                onFinish: this.useableScroll
            }));
            this.run()
        }
    }, moveToRight: function () {
        if (this.scrolled && this.scrollFinish) {
            this.disableScroll();
            this.scrollFinish = false;
            var d = parseInt(this.tabpanel_mover.css("marginLeft")) * -1;
            var c = this.tabpanel_mover.children().length * this.tabWidth;
            var a = this.tabpanel_move_content.width();
            var b = (c - a - d + this.fixNum) * -1;
            Fader.apply(this, new Array({
                element: this.tabpanel_mover,
                style: "marginLeft",
                num: b,
                maxMove: this.maxMove,
                step: (b * -1 / 10) < 10 ? 10 : b * -1 / 10,
                onFinish: this.useableScroll
            }));
            this.run()
        }
    }, moveToSee: function (a) {
        if (this.scrolled) {
            var c = this.tabWidth * a;
            var b = parseInt(this.tabpanel_mover.css("marginLeft"));
            var d;
            if (b <= 0) {
                d = (b + c) * -1;
                if (((d + b) * -1) >= this.maxMove) {
                    this.moveToRight()
                } else {
                    this.disableScroll();
                    this.scrollFinish = false;
                    Fader.apply(this, new Array({
                        element: this.tabpanel_mover,
                        style: "marginLeft",
                        num: d,
                        maxMove: this.maxMove,
                        step: (d / 10) < 10 ? 10 : d / 10,
                        onFinish: this.useableScroll
                    }));
                    this.run()
                }
            } else {
                d = (c - b) * -1;
                if ((d * -1) >= this.maxMove) {
                    this.moveToRight()
                } else {
                    this.disableScroll();
                    this.scrollFinish = false;
                    Fader.apply(this, new Array({
                        element: this.tabpanel_mover,
                        style: "marginLeft",
                        num: d,
                        maxMove: this.maxMove,
                        onFinish: this.useableScroll
                    }));
                    this.run()
                }
            }
        }
    }, disableScroll: function () {
        this.tabpanel_left_scroll.addClass("tabpanel_left_scroll_disabled");
        this.tabpanel_left_scroll.attr("disabled", true);
        this.tabpanel_right_scroll.addClass("tabpanel_right_scroll_disabled");
        this.tabpanel_right_scroll.attr("disabled", true)
    }, useableScroll: function () {
        var a = this;
        if (this.scrolled) {
            if (parseInt(a.tabpanel_mover.css("marginLeft")) == 0) {
                a.tabpanel_left_scroll.addClass("tabpanel_left_scroll_disabled");
                a.tabpanel_left_scroll.attr("disabled", true);
                a.tabpanel_right_scroll.removeClass("tabpanel_right_scroll_disabled");
                a.tabpanel_right_scroll.removeAttr("disabled")
            } else {
                if (parseInt(a.tabpanel_mover.css("marginLeft")) * -1 == a.maxMove) {
                    a.tabpanel_left_scroll.removeClass("tabpanel_left_scroll_disabled");
                    a.tabpanel_left_scroll.removeAttr("disabled", true);
                    a.tabpanel_right_scroll.addClass("tabpanel_right_scroll_disabled");
                    a.tabpanel_right_scroll.attr("disabled")
                } else {
                    a.tabpanel_left_scroll.removeClass("tabpanel_left_scroll_disabled");
                    a.tabpanel_left_scroll.removeAttr("disabled", true);
                    a.tabpanel_right_scroll.removeClass("tabpanel_right_scroll_disabled");
                    a.tabpanel_right_scroll.removeAttr("disabled")
                }
            }
        }
        a.scrollFinish = true
    }, update: function () {
        var a = this.tabpanel_tab_content.width();
        if (this.scrolled) {
            a -= (this.tabpanel_left_scroll.width() + this.tabpanel_right_scroll.width())
        }
        this.tabpanel_move_content.width(a);
        this.maxMove = (this.tabpanel_mover.children().length * this.tabWidth) - a + this.fixNum
    }, showScroll: function () {
        var a = this.tabpanel_mover.children().length * this.tabWidth;
        var b = this.tabpanel_tab_content.width();
        if (a > b && !this.scrolled) {
            this.tabpanel_move_content.addClass("tabpanel_move_content_scroll");
            this.tabpanel_left_scroll.removeClass("display_none");
            this.tabpanel_right_scroll.removeClass("display_none");
            this.scrolled = true
        } else {
            if (a < b && this.scrolled) {
                this.moveToLeft();
                this.tabpanel_move_content.removeClass("tabpanel_move_content_scroll");
                this.tabpanel_left_scroll.addClass("display_none");
                this.tabpanel_right_scroll.addClass("display_none");
                this.scrolled = false;
                this.scrollFinish = true
            }
        }
    }, addTab: function (c) {
        c.id = c.id || Math.uuid();
        if ($("#" + c.id).length > 0) {
            this.show(c.id, false);
            if (c.refresh == true) {
                $("#" + c.id + " .refresher").click()
            }
        } else {
            if (this.scrollFinish) {
                var e = this;
                if (this.maxLength != -1 && this.maxLength <= this.tabs.length) {
                    for (var f = 0; f < this.tabs.length; f++) {
                        if (this.tabs[f].closable) {
                            e.kill(f, true);
                            break
                        }
                    }
                }
                var d = $("<LI></LI>");
                d.attr("id", c.id);
                d.attr("unselectable", "on");
                d.attr("style", "-moz-user-select:none;");
                d.attr("onselectstart", "return false;");
                d.appendTo(this.tabpanel_mover);
                if (this.tabWidth == -1) {
                    this.tabWidth = d.outerWidth(true)
                }
                var j = $("<DIV></DIV>");
                j.html(c.title);
                j.appendTo(d);
                var g = c.closable == false ? 0 : 5;
                if (c.icon) {
                    j.addClass("icon_title");
                    j.css("background-image", 'url("' + c.icon + '")')
                } else {
                    j.addClass("title")
                }
                var b = $("<DIV></DIV>");
                b.addClass("refresher");
                b.appendTo(d);
                var k = $("<DIV></DIV>");
                k.addClass("closer");
                k.attr("title", TabPanel.i18n.close);
                k.appendTo(d);
                var h = $("<DIV></DIV>");
                h.addClass("html_content");
                if (navigator.userAgent.match(/iPad|iPhone/i)) {
                    h.addClass("iframe-ios")
                }
                h.appendTo(this.tabpanel_content);
                var a = this.tabpanel_mover.children().index(this.tabpanel_mover.find(".active")[0]);
                if (a < 0) {
                    a = 0
                }
                if (this.tabs.length > a) {
                    c.preTabId = this.tabs[a].id
                } else {
                    c.preTabId = ""
                }
                c.tab = d;
                c.title = j;
                c.refresher = b;
                c.closer = k;
                c.content = h;
                c.disable = c.disable == undefined ? false : c.disable;
                c.closable = c.closable == undefined ? true : c.closable;
                if (c.closable == false) {
                    k.addClass("display_none")
                }
                if (c.disabled == true) {
                    d.attr("disabled", true);
                    j.addClass("disabled")
                }
                this.tabs.push(c);
                d.bind("click", function (i) {
                    return function () {
                        e.show(i, false)
                    }
                }(this.tabs.length - 1));
                b.bind("click", function (i) {
                    return function () {
                        e.refresh(i)
                    }
                }(this.tabs.length - 1));
                k.bind("click", function (i) {
                    return function () {
                        e.kill(i)
                    }
                }(this.tabs.length - 1));
                if (c.closable) {
                    d.bind("dblclick", function (i) {
                        return function () {
                            e.kill(i)
                        }
                    }(this.tabs.length - 1))
                }
                if (!c.lazyload) {
                    this.show(this.tabs.length - 1, c.notExecuteMoveSee)
                }
                this.showScroll();
                this.update();
                if (!c.lazyload && !c.notExecuteMoveSee) {
                    this.moveToRight()
                }
                $("#" + c.id).RightMenu(c.id + "-rightmenu", {
                    menuList: [{
                        menuName: TabPanel.i18n.refreshTab,
                        menuClass: "",
                        clickEvent: "$('#" + c.id + " .refresher').click();"
                    }, {
                        menuName: TabPanel.i18n.closeOther,
                        menuClass: "",
                        clickEvent: "$('#" + c.id + "').click();$('#" + $(e.render).attr("id") + " .tabpanel_tab_content li:not(.active) .closer:not(.display_none)').click();"
                    }, {
                        menuName: TabPanel.i18n.closeLeft,
                        menuClass: "",
                        clickEvent: "$('#" + c.id + "').click();$('#" + $(e.render).attr("id") + " .tabpanel_tab_content li:lt('+$('#" + $(e.render).attr("id") + " .tabpanel_tab_content li.active').index()+') .closer:not(.display_none)').click();"
                    }, {
                        menuName: TabPanel.i18n.closeRight,
                        menuClass: "",
                        clickEvent: "$('#" + c.id + "').click();$('#" + $(e.render).attr("id") + " .tabpanel_tab_content li:gt('+$('#" + $(e.render).attr("id") + " .tabpanel_tab_content li.active').index()+') .closer:not(.display_none)').click();"
                    }]
                })
            }
        }
    }, getTabPosision: function (a) {
        if (typeof a == "string") {
            for (var b = 0; b < this.tabs.length; b++) {
                if (a == this.tabs[b].id) {
                    a = b;
                    break
                }
            }
        }
        return a
    }, refresh: function (a) {
        a = this.getTabPosision(a);
        if (typeof a == "string") {
            return false
        } else {
            this.tabs[a].tab.removeClass("active");
            this.tabs[a].content.html("");
            this.show(a)
        }
    }, iterateFlush: function (c) {
        if (c.window.frames.length > 0) {
            for (var a = 0; a < c.window.frames.length; a++) {
                this.iterateFlush(c.window.frames[a])
            }
        } else {
            if (c.document.forms.length > 0) {
                for (var a = 0; a < c.document.forms.length; a++) {
                    try {
                        c.document.forms[a].submit()
                    } catch (b) {
                        c.location.reload()
                    }
                }
            } else {
                c.location.reload()
            }
        }
    }, show: function (a, b) {
        if (this.tabs.length < 1) {
            return false
        }
        a = this.getTabPosision(a);
        if (typeof a == "string") {
            a = 0
        }
        if (this.scrollFinish) {
            if (a >= this.tabs.length) {
                a = 0
            }
            $(".html_content").css("visibility", "hidden");
            this.tabs[a].content.css("visibility", "visible");
            this.tabs[a].content.css("z-index", ++this.maxzindex);
            if (this.tabs[a].tab.hasClass("active")) {
                if (!b) {
                    this.moveToSee(a)
                }
            } else {
                if (this.tabs[a].content.html() == "") {
                    this.tabs[a].content.html(this.tabs[a].html)
                }
                this.tabpanel_mover.find(".active").removeClass("active");
                this.tabs[a].tab.addClass("active");
                if (!b) {
                    this.moveToSee(a)
                }
            }
        }
    }, kill: function (a, d) {
        var f = this;
        a = this.getTabPosision(a);
        if (typeof this.tabs[a].onPreClose == "function") {
            if (this.tabs[a].onPreClose(a) == false) {
                return
            }
        }
        var c = this.tabs[a].preTabId;
        if ($("#" + c).length <= 0 && this.tabs[a - 1]) {
            c = this.tabs[a - 1].id
        }
        this.tabs[a].closer.remove();
        this.tabs[a].title.remove();
        this.tabs[a].tab.remove();
        this.tabs[a].content.remove();
        $("#" + this.tabs[a].id + "-rightmenu").remove();
        this.tabs.splice(a, 1);
        for (var e = 0; e < this.tabs.length; e++) {
            this.tabs[e].tab.unbind("click");
            this.tabs[e].tab.bind("click", function (g) {
                return function () {
                    f.show(g, false)
                }
            }(e));
            this.tabs[e].refresher.unbind("click");
            this.tabs[e].refresher.bind("click", function (g) {
                return function () {
                    f.refresh(g)
                }
            }(e));
            this.tabs[e].closer.unbind("click");
            this.tabs[e].closer.bind("click", function (g) {
                return function () {
                    f.kill(g)
                }
            }(e));
            if (this.tabs[e].closable) {
                this.tabs[e].tab.unbind("dblclick");
                this.tabs[e].tab.bind("dblclick", function (g) {
                    return function () {
                        f.kill(g)
                    }
                }(e))
            }
        }
        if (d != true) {
            var b = this;
            setTimeout(function () {
                b.update();
                b.showScroll();
                b.show(c, false)
            }, 500)
        }
    }, getTabsCount: function () {
        return this.tabs.length
    }, setTitle: function (a, b) {
        a = this.getTabPosision(a);
        if (a < this.tabs.length) {
            this.tabs[a].title.text(b)
        }
    }, getTitle: function (a) {
        a = this.getTabPosision(a);
        return this.tabs[a].title.text()
    }, setContent: function (a, b) {
        a = this.getTabPosision(a);
        if (a < this.tabs.length) {
            this.tabs[a].content.html(b)
        }
    }, getContent: function (a) {
        a = this.getTabPosision(a);
        return this.tabs[a].content.html()
    }, setDisable: function (a, b) {
        a = this.getTabPosision(a);
        if (a < this.tabs.length) {
            this.tabs[a].disable = b;
            if (b) {
                this.tabs[a].tab.attr("disabled", true);
                this.tabs[a].title.addClass("disabled")
            } else {
                this.tabs[a].tab.removeAttr("disabled");
                this.tabs[a].title.removeClass("disabled")
            }
        }
    }, getDisable: function (a) {
        a = this.getTabPosision(a);
        return this.tabs[a].disable
    }, setClosable: function (a, b) {
        a = this.getTabPosision(a);
        if (a < this.tabs.length) {
            this.tabs[a].closable = b;
            if (b) {
                this.tabs[a].closer.addClass("display_none")
            } else {
                this.tabs[a].closer.addClass("closer");
                this.tabs[a].closer.removeClass("display_none")
            }
        }
    }, getClosable: function (a) {
        a = this.getTabPosision(a);
        return this.tabs[a].closable
    }, getActiveIndex: function () {
        return this.tabpanel_mover.children().index(this.tabpanel_mover.find(".active")[0])
    }, getActiveTab: function () {
        var a = this.tabpanel_mover.children().index(this.tabpanel_mover.find(".active")[0]);
        if (this.tabs.length > a) {
            return this.tabs[a]
        } else {
            return null
        }
    }, resize: function () {
        var a = this.border == "none" ? 0 : 2;
        if (this.widthResizable) {
            this.width = this.render.width();
            this.tabpanel.width(this.width - a);
            this.tabpanel_tab_content.width(this.width - a);
            this.tabpanel_content.width(this.width - a)
        }
        if (this.heightResizable) {
            this.height = this.render.height();
            this.tabpanel.height(this.height - a);
            this.tabpanel_content.height(this.height - this.tabpanel_tab_content.get(0).offsetHeight)
        }
        this.showScroll();
        this.useableScroll();
        this.update()
    }, setRenderWH: function (a) {
        if (a) {
            if (a.width != undefined) {
                this.render.width(a.width)
            }
            if (a.height != undefined) {
                this.render.height(a.height)
            }
            this.resize()
        }
    }
};
TabPanel.i18n = {closeTab: "关闭页签", refreshTab: "刷新页签", closeOther: "关闭其他", closeLeft: "关闭左侧", closeRight: "关闭右侧"};
Fader = function (a) {
    this.element = a.element;
    this.elementID = a.elementID;
    this.style = a.style;
    this.num = a.num;
    this.maxMove = a.maxMove;
    this.finishNum = "string";
    this.interval = a.interval || 10;
    this.step = a.step || 20;
    this.onFinish = a.onFinish;
    this.isFinish = false;
    this.timer = null;
    this.method = this.num >= 0;
    this.c = this.elementID ? $("#" + this.elementID) : this.element;
    this.run = function () {
        clearInterval(this.timer);
        this.fade();
        if (this.isFinish) {
            this.onFinish && this.onFinish()
        } else {
            var b = this;
            this.timer = setInterval(function () {
                b.run()
            }, this.interval)
        }
    };
    this.fade = function () {
        if (this.finishNum == "string") {
            this.finishNum = (parseInt(this.c.css(this.style)) || 0) + this.num
        }
        var b = parseInt(this.c.css(this.style)) || 0;
        if (this.finishNum > b && this.method) {
            b += this.step;
            if (b >= 0) {
                this.finishNum = b = 0
            }
        } else {
            if (this.finishNum < b && !this.method) {
                b -= this.step;
                if (b * -1 >= this.maxMove) {
                    this.finishNum = b = this.maxMove * -1
                }
            }
        }
        if (this.finishNum <= b && this.method || this.finishNum >= b && !this.method) {
            this.c.css(this.style, this.finishNum + "px");
            this.isFinish = true;
            this.finishNum = "string"
        } else {
            this.c.css(this.style, b + "px")
        }
    }
};
Math.uuid = (function () {
    var a = "0123456789abcdefghijklmnopqrstuvwxyz".split("");
    return function (c, d) {
        var h = a, e = [], g = Math.random;
        d = d || h.length;
        if (c) {
            for (var i = 0; i < c; i++) {
                e[i] = h[0 | g() * d]
            }
        } else {
            var b = 0, f;
            e[8] = e[13] = e[18] = e[23] = "-";
            e[14] = "4";
            for (i = 0; i < 36; i++) {
                if (!e[i]) {
                    f = 0 | g() * 16;
                    e[i] = h[(i == 19) ? (f & 3) | 8 : f & 15]
                }
            }
        }
        return e.join("").replace(/-/ig, "").substring(0, 10)
    }
})();
$.fn.tabPanel = function (b, f, h, g, e, d) {
    var c;
    var a = this.each(function () {
        var k = $(this);
        var j = k.data("tabPanel");
        var i = typeof b === "object" && b;
        if (!j) {
            k.data("tabPanel", (j = new TabPanel(i, k)))
        }
        if (typeof b === "string" && typeof j[b] === "function") {
            if (f instanceof Array) {
                c = j[b].apply(j, f, h, g, e, d)
            } else {
                c = j[b](f, h, g, e, d)
            }
        }
    });
    return (c === undefined) ? a : c
};
var pos_mx = 0, pos_my = 0;
$(function () {
    document.oncontextmenu = function () {
        return false
    };
    document.onmousemove = function (b) {
        ev = b || window.event;
        var a;
        if (ev.pageX || ev.pageY) {
            a = {x: ev.pageX, y: ev.pageY}
        } else {
            a = {x: ev.clientX, y: ev.clientY + $(document).scrollTop()}
        }
        pos_mx = a.x;
        pos_my = a.y
    }
});
$.fn.RightMenu = function (g, b) {
    b = $.extend({menuList: []}, b);
    var f = b.menuList.length;
    if (!$("#" + g)[0]) {
        var d = '<div id="' + g + '" class="div_RightMenu"><div><ul class=\'ico\'>';
        for (var c = 0; c < f; c++) {
            d += '<li class="' + b.menuList[c].menuClass + '" onclick="' + b.menuList[c].clickEvent + '">' + b.menuList[c].menuName + "</li>"
        }
        d += "</ul></div><div>";
        $("body").append(d);
        var a = $("#" + g), e;
        a.find("li").bind("mouseover", function () {
            $(this).addClass("RM_mouseover")
        }).bind("mouseout", function () {
            $(this).removeClass("RM_mouseover")
        }).bind("click", function () {
            window.clearTimeout(e);
            a.hide()
        });
        a.hover(function () {
            window.clearTimeout(e)
        }, function () {
            window.clearTimeout(e);
            e = window.setTimeout(function () {
                a.hide()
            }, 500)
        })
    }
    return this.each(function () {
        this.oncontextmenu = function () {
            $(".div_RightMenu").hide();
            var n = $("body").width(), m = $("html").height(), l = $("body").height(), i = $("#" + g).width(),
                k = $("#" + g).height(), j = (m > l) ? m : l;
            if (j < k + pos_my) {
                pos_my = j - k
            }
            if (n < i + pos_mx) {
                pos_mx = n - i
            }
            $("#" + g).hide().css({top: pos_my, left: pos_mx}).show()
        }
    })
};