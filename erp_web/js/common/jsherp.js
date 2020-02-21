/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 *
 * 通用公共JavaScript，注意：项目请不要修改公共CSS样式，若需要新增或调整，请在common.js中覆盖
 * @author ThinkGem
 * @version 2019-7-10
 */
if (typeof jQuery === "undefined") {
    throw new Error("JSHERP's JavaScript requires jQuery")
}
(function($, window, undefined) {
    parent.$("iframe.ui-layout-content").each(function() {
        if (document.body.ownerDocument === this.contentWindow.document) {
            $("body").addClass("ui-layout-content");
            return
        }
    });
    $(function() {
        if ($.fn.iCheck !== undefined) {
            $("input[type=checkbox].form-control:not(.noicheck),input[type=radio].form-control:not(.noicheck)").each(function() {
                $(this).iCheck({
                    checkboxClass: "icheckbox_" + ($(this).data("style") || "minimal-grey"),
                    radioClass: "iradio_" + ($(this).data("style") || "minimal-grey")
                }).on("ifChanged", function() {
                    try {
                        $(this).resetValid()
                    } catch (e) {}
                })
            })
        }
        if ($.fn.select2 !== undefined) {
            $("select.form-control:not(.noselect2)").each(function() {
                $(this).select2({
                    minimumResultsForSearch: 10,
                    templateResult: function(result, container) {
                        var element = $(result.element)
                          , parent = $(container)
                          , style = element.attr("style")
                          , clazz = element.attr("class");
                        if (style && style != "") {
                            parent.attr("style", style)
                        }
                        if (clazz && clazz != "") {
                            parent.addClass(clazz)
                        }
                        return result.text
                    }
                }).on("change", function() {
                    try {
                        $(this).resetValid()
                    } catch (e) {}
                })
            })
        }
        if (js.ie) {
            $("a").bind("focus", function() {
                if (this.blur) {
                    this.blur()
                }
            })
        }
        $("#inputForm input[type=text]:not([readonly]):not([disabled]):not(.nofocus):eq(0)").focus();
        $(document).on("click", ".addTabPage", function(e) {
            var $this = $(this)
              , href = $this.data("href") || $this.attr("href")
              , title = $this.data("title") || $this.attr("title") || $this.text();
            if (href && href != "" && href != "blank") {
                js.addTabPage($this, $.trim(title || js.text("tabpanel.newTabPage")), href);
                if ($this.parent().hasClass("treeview")) {
                    window.funId = $this.data("code"); //功能id
                    top.window.isMenuClickFlag = true;
                    top.window.location.hash = href.replace("#", "")
                }
                return false
            }
            return true
        });
        if (js.ie && js.ie <= 9) {
            setTimeout(function() {
                $("input[placeholder],textarea[placeholder]").placeholder()
            }, 500)
        }
        if ($("#scroll-up").length > 0) {
            var btnScrollTop = $("#scroll-up")
              , isVisible = false;
            $(window).on("scroll.btnScrollTop", function() {
                var scroll = $(document).scrollTop()
                  , h = $(window).height()
                  , sh = document.body.scrollHeight;
                if (scroll > parseInt(h / 4) || (scroll > 0 && sh >= h && h + scroll >= sh - 1)) {
                    if (!isVisible) {
                        btnScrollTop.addClass("display");
                        isVisible = true
                    }
                } else {
                    if (isVisible) {
                        btnScrollTop.removeClass("display");
                        isVisible = false
                    }
                }
            }).triggerHandler("scroll.btnScrollTop");
            btnScrollTop.on("click", function() {
                $("html,body").animate({
                    scrollTop: 0
                }, 500);
                return false
            })
        }
        if (typeof FastClick != "undefined") {
            FastClick.attach(document.body)
        }
    });
    var js = {
        log: function(msg) {
            if (typeof (console) !== "undefined") {
                console.log(msg)
            }
        },
        error: function(msg) {
            if (typeof (console) !== "undefined") {
                console.error(msg)
            }
        },
        encodeUrl: function(url) {
            return encodeURIComponent(url)
        },
        decodeUrl: function(url) {
            return decodeURIComponent(url)
        },
        ie: function() {
            var agent = navigator.userAgent.toLowerCase();
            return (!!window.ActiveXObject || "ActiveXObject"in window) ? ((agent.match(/msie\s(\d+)/) || [])[1] || (agent.match(/Trident/i) && agent.match(/rv:(\d+)/) || [])[1] || false) : false
        }(),
        val: function(jsonObj, attrName) {
            if (jsonObj === undefined) {
                return ""
            }
            if (attrName === undefined || attrName == "") {
                return typeof jsonObj === "string" ? jsonObj : ""
            }
            var ret = jsonObj[attrName], prm = [], p, i;
            if (ret === undefined) {
                try {
                    if (typeof attrName === "string") {
                        prm = attrName.split(".")
                    }
                    i = prm.length;
                    if (i) {
                        ret = jsonObj;
                        while (ret && i--) {
                            p = prm.shift();
                            ret = ret[p]
                        }
                    }
                } catch (e) {}
            }
            if (ret === undefined) {
                return ""
            }
            return ret
        },
        hashCode: function(str, caseSensitive) {
            if (caseSensitive != true) {
                str = str.toLowerCase()
            }
            var hash = 1315423911, i, ch;
            for (i = str.length - 1; i >= 0; i--) {
                ch = str.charCodeAt(i);
                hash ^= ((hash << 5) + ch + (hash >> 2))
            }
            return (hash & 2147483647)
        },
        loadFile: function(file, callback, error) {
            callback = callback || function() {}
            ;
            error = error || function(data) {
                js.showMessage(data)
            }
            ;
            var files = typeof file == "string" ? [file] : file;
            var htmlDoc = document.getElementsByTagName("head")[0], okCounts = 0, fileCounts = files.length, i, loadFilePath = null;
            for (i = 0; i < fileCounts; i++) {
                var includeFile = null, att = null, ext, hash;
                loadFilePath = files[i];
                hash = js.hashCode(loadFilePath);
                if (document.getElementById("loadHash_" + hash)) {
                    okCounts += 1;
                    if (okCounts == fileCounts) {
                        callback();
                        return true
                    }
                    continue
                }
                att = loadFilePath.split("?")[0].split(".");
                ext = att[att.length - 1].toLowerCase();
                switch (ext) {
                case "css":
                    includeFile = document.createElement("link");
                    includeFile.setAttribute("rel", "stylesheet");
                    includeFile.setAttribute("type", "text/css");
                    includeFile.setAttribute("href", loadFilePath);
                    break;
                case "js":
                    includeFile = document.createElement("script");
                    includeFile.setAttribute("type", "text/javascript");
                    includeFile.setAttribute("src", loadFilePath);
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                    includeFile = document.createElement("img");
                    includeFile.setAttribute("src", loadFilePath);
                    break;
                default:
                    error("载入的格式不支持:" + loadFilePath);
                    return false
                }
                if (typeof includeFile != "undefined") {
                    includeFile.setAttribute("id", "loadHash_" + hash);
                    htmlDoc.appendChild(includeFile);
                    includeFile.onreadystatechange = function() {
                        if (includeFile.readyState == "loaded" || includeFile.readyState == "complete") {
                            okCounts += 1;
                            if (okCounts == fileCounts) {
                                callback();
                                return true
                            }
                        }
                    }
                    ;
                    includeFile.onload = function() {
                        okCounts += 1;
                        if (okCounts == fileCounts) {
                            callback();
                            return true
                        }
                    }
                    ;
                    includeFile.onerror = function() {
                        $("#loadhash_" + hash).remove();
                        return false
                    }
                }
            }
        },
        windowOpen: function(url, name, width, height) {
            if (!(width && height)) {
                width = window.screen.width - 200;
                height = window.screen.height - 150
            }
            var top = parseInt((window.screen.height - height) / 2 - 20, 10)
              , left = parseInt((window.screen.width - width) / 2, 10)
              , options = "location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height + ",top=" + top + ",left=" + left;
            window.open(url, name, options)
        },
        windowClose: function() {
            setTimeout(function() {
                window.opener = null;
                window.open("", "_self");
                window.close()
            }, 100)
        },
        addParam: function(url, params) {
            if (params != "") {
                url += (url.indexOf("?") == -1 ? "?" : "&");
                url += params
            }
            return url
        },
        getParam: function(paramName, url) {
            var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)","i");
            if (!url || url == "") {
                url = window.location.search
            } else {
                url = url.substring(url.indexOf("?"))
            }
            var val = url.substr(1).match(reg);
            if (val != null) {
                return unescape(val[2])
            }
            return null
        },
        removeParam: function(paramName, url) {
            var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)","i"), params;
            if (!url || url == "") {
                params = window.location.search
            } else {
                params = url.substring(url.indexOf("?"))
            }
            if (params != "") {
                if (js.startsWith(params, "?") || js.startsWith(params, "&")) {
                    params = params.substr(1)
                }
                url = url.substring(0, url.indexOf("?") + 1) + params.replace(reg, "$1");
                if (js.endsWith(url, "?") || js.endsWith(url, "&")) {
                    return url.substring(0, url.length - 1)
                }
            }
            return url
        },
        alertObj: function(obj) {
            var ob = eval(obj);
            var index = 1
              , property = "";
            for (var p in ob) {
                property += (index++) + "、" + p + " = " + ob[p] + "\n"
            }
            alert(property)
        },
        text: function(code, params) {
            if (code) {
                js.i18n = js.i18n || {};
                var text = js.i18n[code];
                if (!(text && text != "")) {
                    text = code
                }
                if (params) {
                    for (var i = 1; i < arguments.length; i++) {
                        var re = new RegExp("\\{" + (i - 1) + "\\}","gm");
                        text = text.replace(re, arguments[i])
                    }
                }
                return text
            }
            return code
        },
        getDictLabel: function(dictListJson, value, defaultValue, inCss) {
            var result = [];
            for (var i = 0; i < dictListJson.length; i++) {
                var row = dictListJson[i];
                if (("," + value + ",").indexOf("," + row.dictValue + ",") != -1) {
                    var str = "";
                    if (inCss && (row.cssClass || row.cssStyle)) {
                        str += "<span";
                        if (row.cssClass) {
                            str += ' class="' + row.cssClass + '"'
                        }
                        if (row.cssStyle) {
                            str += ' style="' + row.cssStyle + '"'
                        }
                        result.push(str + ">" + row.dictLabel + "</span>")
                    } else {
                        result.push(row.dictLabel)
                    }
                }
            }
            return result.length > 0 ? result.join(",") : defaultValue
        },
        loading: function(message, ignoreMessageIfExists) {
            var topJs;
            try {
                top.loadingFlag = true;
                topJs = top.js || parent.parent.js || parent.js
            } catch (e) {}
            if (typeof loadingFlag == "undefined" && topJs) {
                if (typeof topJs.loading == "function") {
                    topJs.loading(message);
                    return
                }
            }
            if (message == undefined || message == "") {
                message = js.text("loading.message")
            }
            if (message == "none") {
                return
            }
            setTimeout(function() {
                if (!js.pageLoadingNum) {
                    js.pageLoadingNum = 0
                }
                if (!js.pageLoadingStyle) {
                    if ($("body").hasClass("loading-topline")) {
                        js.pageLoadingStyle = 2
                    } else {
                        js.pageLoadingStyle = 1
                    }
                }
                if (js.pageLoadingStyle == 1) {
                    message += '<em onclick="js.closeLoading(0, true)">×</em>';
                    if ($("#page-loading").length == 0) {
                        $("body").append('<div id="page-loading" onmouseover="$(this).find(\'em\').show()" onmouseout="$(this).find(\'em\').hide()">' + message + "</div>")
                    } else {
                        if (!(ignoreMessageIfExists == true)) {
                            $("#page-loading").html(message)
                        }
                    }
                } else {
                    if (js.pageLoadingStyle == 2) {
                        if ($("#page-loading-top").length == 0) {
                            $("body").append('<div id="page-loading-top" class="page-loading-top"></div>');
                            $("#page-loading-top").animate({
                                width: "65%"
                            }, 2000, function() {
                                $(this).animate({
                                    width: "85%"
                                }, 8000)
                            })
                        }
                    }
                }
                js.pageLoadingNum++
            }, 0)
        },
        closeLoading: function(timeout, forceClose) {
            var topJs;
            try {
                top.loadingFlag = true;
                topJs = top.js || parent.parent.js || parent.js
            } catch (e) {}
            if (typeof loadingFlag == "undefined" && topJs) {
                if (typeof topJs.closeLoading == "function") {
                    topJs.closeLoading(timeout, forceClose);
                    return
                }
            }
            setTimeout(function() {
                if (!js.pageLoadingNum) {
                    js.pageLoadingNum = 0
                }
                js.pageLoadingNum--;
                if (forceClose || js.pageLoadingNum <= 0) {
                    if (js.pageLoadingStyle == 1) {
                        $("#page-loading").remove()
                    } else {
                        if (js.pageLoadingStyle == 2) {
                            $("#page-loading-top").stop().animate({
                                width: "100%"
                            }, 200, function() {
                                $(this).fadeOut(300, function() {
                                    $(this).remove()
                                })
                            })
                        }
                    }
                    js.pageLoadingNum = 0
                }
            }, timeout == undefined ? 0 : timeout)
        },
        layer: function() {
            try {
                if (top.layer && top.layer.window) {
                    return top.layer
                }
                if (parent.parent.layer && parent.parent.layer.window) {
                    return parent.parent.layer
                }
                if (parent.layer && parent.layer.window) {
                    return parent.layer
                }
            } catch (e) {}
            if (window.layer) {
                return layer
            }
            return null
        }(),
        showMessage: function(message, title, type, timeout) {
            var msgType, layerIcon, msg = String(message), msgTimeout = timeout == undefined ? 4000 : timeout;
            var contains = function(str, searchs) {
                if (searchs) {
                    var ss = searchs.split(",");
                    for (var i = 0; i < ss.length; i++) {
                        if (msg.indexOf(ss[i]) >= 0) {
                            return true
                        }
                    }
                }
                return false
            };
            if (type == "error" || contains(msg, js.text("showMessage.error"))) {
                msgType = "error";
                layerIcon = 2
            } else {
                if (type == "warning" || contains(msg, js.text("showMessage.warning"))) {
                    msgType = "warning";
                    layerIcon = 5
                } else {
                    if (type == "success" || contains(msg, js.text("showMessage.success"))) {
                        msgType = "success";
                        layerIcon = 1
                    } else {
                        msgType = "info";
                        layerIcon = 6
                    }
                }
            }
            try {
                if (top.toastr) {
                    var positionClass = "toast-bottom-right";
                    if (msg && msg.length >= 8 && msg.indexOf("posfull:") >= 0) {
                        if (timeout == undefined) {
                            msgTimeout = 0
                        }
                        positionClass = "toast-top-full-width";
                        msg = msg.substring(8);
                        js.log(msg)
                    }
                    top.toastr.options = {
                        closeButton: true,
                        positionClass: positionClass,
                        timeOut: msgTimeout
                    };
                    top.toastr[msgType](msg, title);
                    return
                }
            } catch (e) {}
            if (!js.layer) {
                alert(msg);
                return
            }
            if (layerIcon) {
                js.layer.msg(msg, {
                    icon: layerIcon,
                    time: msgTimeout
                })
            } else {
                js.layer.msg(msg, {
                    time: msgTimeout
                })
            }
        },
        showErrorMessage: function(responseText) {
            if (responseText && responseText != "") {
                js.error(js.abbr(responseText, 500));
                if (responseText.indexOf("<html ") != -1 || responseText.indexOf("<head ") != -1 || responseText.indexOf("<body ") != -1) {
                    js.showMessage("未知错误，F12查看异常信息！", null, "error")
                } else {
                    try {
                        var json = JSON.parse(responseText);
                        if (typeof json == "object" && typeof json.message != "undefined") {
                            js.showMessage(json.message, null, "error");
                            return
                        }
                    } catch (e) {}
                    js.showMessage(responseText, null, "error")
                }
            }
        },
        closeMessage: function() {
            try {
                if (top.toastr) {
                    top.toastr.clear()
                }
            } catch (e) {}
        },
        alert: function(message, options, closed) {
            if (typeof options != "object") {
                closed = options;
                options = {
                    icon: 1
                }
            }
            if (!js.layer) {
                alert(message);
                if (typeof closed == "function") {
                    closed()
                }
                return
            }
            js.layer.alert(message, options, function(index) {
                if (typeof closed == "function") {
                    closed(index)
                }
                js.layer.close(index)
            })
        },
        confirm: function(message, urlOrFun, data, callback, dataType, async, loadingMessage) {
            if (typeof data == "function") {
                loadingMessage = async;
                async = dataType;
                dataType = callback;
                callback = data;
                data = undefined
            }
            var sendAjax = function() {
                js.loading(loadingMessage == undefined ? js.text("loading.submitMessage") : loadingMessage);
                $.ajax({
                    type: "POST",
                    url: urlOrFun,
                    data: data,
                    dataType: dataType == undefined ? "json" : dataType,
                    async: async == undefined ? true : async,
                    error: function(data) {
                        js.showErrorMessage(data.responseText);
                        js.closeLoading(0, true)
                    },
                    success: function(data) {
                        if (typeof callback == "function") {
                            callback(data)
                        }
                        js.closeLoading()
                    }
                })
            };
            if (!js.layer) {
                if (confirm(message)) {
                    if (typeof urlOrFun == "function") {
                        urlOrFun()
                    } else {
                        sendAjax()
                    }
                }
                return
            }
            var options = {
                icon: 3
            };
            js.layer.confirm(message, options, function(index) {
                if (typeof urlOrFun == "function") {
                    urlOrFun()
                } else {
                    sendAjax()
                }
                js.layer.close(index)
            });
            return false
        },
        template: function(id, data, callback) {
            var tpl = String($("#" + id).html()).replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "")
              , data = data || [];
            if (typeof callback == "function") {
                laytpl(tpl).render(data || [], function(render) {
                    callback(render)
                });
                return null
            }
            return laytpl(tpl).render(data || [])
        },
        ajaxSubmit: function(url, data, callback, dataType, async, message) {
            $(".btn").attr("disabled", true);
            if (typeof data == "function") {
                message = async;
                async = dataType;
                dataType = callback;
                callback = data;
                data = undefined
            }
            var options = {};
            if (typeof callback == "object") {
                options = callback;
                callback = options.callback;
                dataType = options.dataType;
                async = options.async;
                message = options.message
            }
            js.loading(message == undefined ? js.text("loading.submitMessage") : message);
            $.ajax($.extend(true, {
                type: "POST",
                url: url,
                data: data,
                dataType: dataType == undefined ? "json" : dataType,
                async: async == undefined ? true : async,
                error: function(data) {
                    $(".btn").attr("disabled", false);
                    js.showErrorMessage(data.responseText);
                    js.closeLoading(0, true)
                },
                success: function(data, status, xhr) {
                    $(".btn").attr("disabled", false);
                    js.closeLoading();
                    if (typeof callback == "function") {
                        callback(data, status, xhr)
                    } else {
                        js.log(data)
                    }
                }
            }, options))
        },
        ajaxSubmitForm: function(formJqueryObj, callback, dataType, async, message) {
            $(".btn").attr("disabled", true);
            var options = {};
            if (typeof callback == "object") {
                options = callback;
                callback = options.callback;
                dataType = options.dataType;
                async = options.async;
                message = options.message
            }
            js.loading(message == undefined ? js.text("loading.submitMessage") : message);
            if (options.downloadFile === true) {
                options.iframe = true
            }
            formJqueryObj.ajaxSubmit($.extend(true, {
                type: "POST",
                xhrFields: {
                    withCredentials: true
                },
                url: formJqueryObj.attr("action"),
                dataType: dataType == undefined ? "json" : dataType,
                async: async == undefined ? true : async,
                error: function(data) {
                    $(".btn").attr("disabled", false);
                    js.showErrorMessage(data.responseText);
                    js.closeLoading(0, true)
                },
                success: function(data, status, xhr) {
                    $(".btn").attr("disabled", false);
                    js.closeLoading();
                    if (typeof callback == "function") {
                        callback(data, status, xhr)
                    } else {
                        js.log(data)
                    }
                }
            }, options));
            if (options.downloadFile === true) {
                $(".btn").attr("disabled", false);
                js.closeLoading()
            }
        },
        trim: function(str) {
            return jQuery.trim(str)
        },
        startWith: function(str, start) {
            var reg = new RegExp("^" + start);
            return reg.test(str)
        },
        startsWith: function(str, prefix) {
            if (!str || !prefix || str.length < prefix.length) {
                return false
            }
            return str.slice(0, prefix.length) === prefix
        },
        endWith: function(str, end) {
            var reg = new RegExp(end + "$");
            return reg.test(str)
        },
        endsWith: function(str, suffix) {
            if (!str || !suffix || str.length < suffix.length) {
                return false
            }
            return str.indexOf(suffix, str.length - suffix.length) !== -1
        },
        abbr: function(name, maxLength) {
            if (!maxLength) {
                maxLength = 20
            }
            if (name == null || name.length < 1) {
                return ""
            }
            var w = 0;
            var s = 0;
            var p = false;
            var b = false;
            var nameSub;
            for (var i = 0; i < name.length; i++) {
                if (i > 1 && b == false) {
                    p = false
                }
                if (i > 1 && b == true) {
                    p = true
                }
                var c = name.charCodeAt(i);
                if ((c >= 1 && c <= 126) || (65376 <= c && c <= 65439)) {
                    w++;
                    b = false
                } else {
                    w += 2;
                    s++;
                    b = true
                }
                if (w > maxLength && i <= name.length - 1) {
                    if (b == true && p == true) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    if (b == false && p == false) {
                        nameSub = name.substring(0, i - 3) + "..."
                    }
                    if (b == true && p == false) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    if (p == true) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    break
                }
            }
            if (w <= maxLength) {
                return name
            }
            return nameSub
        },
        formatNumber: function(num, cent, isThousand, defaultValue) {
            if (typeof num == "undefined" || num == null || num === "") {
                return defaultValue || ""
            }
            num = String(num).replace(/\$|\,/g, "");
            if (isNaN(num)) {
                num = "0"
            }
            var sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * Math.pow(10, cent) + 0.50000000001);
            var cents = num % Math.pow(10, cent);
            num = Math.floor(num / Math.pow(10, cent)).toString();
            cents = cents.toString();
            while (cents.length < cent) {
                cents = "0" + cents
            }
            if (isThousand) {
                for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
                    num = num.substring(0, num.length - (4 * i + 3)) + "," + num.substring(num.length - (4 * i + 3))
                }
            }
            if (cent > 0) {
                return (((sign) ? "" : "-") + num + "." + cents)
            } else {
                return (((sign) ? "" : "-") + num)
            }
        },
        formatMoney: function(s, n) {
            if (s == undefined || s == "") {
                return "0.00"
            }
            n = n >= 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1], i, t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "")
            }
            return t.split("").reverse().join("") + (r ? "." + r : "")
        },
        numberPad: function(num, n) {
            var len = num.toString().length;
            while (len < n) {
                num = "0" + num;
                len++
            }
            return num
        },
        formatDate: function(date, f) {
            if (date == undefined) {
                return ""
            }
            if (f == undefined) {
                f = "yyyy-MM-dd HH:mm"
            }
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "H+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                S: date.getMilliseconds()
            };
            if (/(y+)/.test(f)) {
                f = f.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length))
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(f)) {
                    f = f.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length))
                }
            }
            return f
        },
        parseDate: function(date) {
            if (date == undefined) {
                return null
            }
            return new Date(date.replace(/-/g, "/"))
        },
        addDate: function(date, dadd) {
            date = date.valueOf();
            date = date + dadd * 24 * 60 * 60 * 1000;
            return new Date(date)
        },
        quickSelectDate: function(type, beginDateId, endDateId) {
            var now = new Date()
              , nowYear = now.getFullYear()
              , nowMonth = now.getMonth()
              , nowDay = now.getDate()
              , nowDayOfWeek = now.getDay()
              , beginDate = $("#" + beginDateId)
              , endDate = $("#" + endDateId)
              , formatDate = function(date) {
                return js.formatDate(date, "yyyy-MM-dd")
            }
              , getMonthDays = function(myMonth) {
                var monthStartDate = new Date(nowYear,myMonth,1);
                var monthEndDate = new Date(nowYear,myMonth + 1,1);
                var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
                return days
            };
            if (type == "1") {
                beginDate.val(formatDate(now));
                endDate.val(beginDate.val())
            } else {
                if (type == "2") {
                    nowDayOfWeek = nowDayOfWeek == 0 ? 6 : nowDayOfWeek - 1;
                    var weekStartDate = new Date(nowYear,nowMonth,nowDay - nowDayOfWeek);
                    var weekEndDate = new Date(nowYear,nowMonth,nowDay + (6 - nowDayOfWeek));
                    beginDate.val(formatDate(weekStartDate));
                    endDate.val(formatDate(weekEndDate))
                } else {
                    if (type == "3") {
                        var monthStartDate = new Date(nowYear,nowMonth,1);
                        var monthEndDate = new Date(nowYear,nowMonth,getMonthDays(nowMonth));
                        beginDate.val(formatDate(monthStartDate));
                        endDate.val(formatDate(monthEndDate))
                    } else {
                        if (type == "4") {
                            var quarterStartMonth = 0;
                            if (nowMonth < 3) {
                                quarterStartMonth = 0
                            }
                            if (2 < nowMonth && nowMonth < 6) {
                                quarterStartMonth = 3
                            }
                            if (5 < nowMonth && nowMonth < 9) {
                                quarterStartMonth = 6
                            }
                            if (nowMonth > 8) {
                                quarterStartMonth = 9
                            }
                            var quarterEndMonth = quarterStartMonth + 2;
                            var quarterStartDate = new Date(nowYear,quarterStartMonth,1);
                            var quarterEndDate = new Date(nowYear,quarterEndMonth,getMonthDays(quarterEndMonth));
                            beginDate.val(formatDate(quarterStartDate));
                            endDate.val(formatDate(quarterEndDate))
                        } else {
                            if (type == "5") {
                                var lastMonthDate = new Date();
                                lastMonthDate.setDate(1);
                                lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
                                var lastYear = lastMonthDate.getYear();
                                var lastMonth = lastMonthDate.getMonth();
                                var lastMonthStartDate = new Date(nowYear,lastMonth,1);
                                var lastMonthEndDate = new Date(nowYear,lastMonth,getMonthDays(lastMonth));
                                beginDate.val(formatDate(lastMonthStartDate));
                                endDate.val(formatDate(lastMonthEndDate))
                            }
                        }
                    }
                }
            }
            beginDate.change();
            endDate.change()
        },
        cookie: function(name, value, options) {
            if (typeof value != "undefined") {
                options = options || {};
                if (value === null) {
                    value = "";
                    options.expires = -1
                }
                var expires = "";
                if (options.expires && (typeof options.expires == "number" || options.expires.toUTCString)) {
                    var date;
                    if (typeof options.expires == "number") {
                        date = new Date();
                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000))
                    } else {
                        date = options.expires
                    }
                    expires = "; expires=" + date.toUTCString()
                }
                var path = "; path=" + (options.path ? options.path : (window.ctxPath ? window.ctxPath : "/"));
                var domain = options.domain ? "; domain=" + options.domain : "";
                var secure = options.secure ? "; secure" : "";
                document.cookie = [name, "=", encodeURIComponent(value), expires, path, domain, secure].join("")
            } else {
                var cookieValue = null;
                if (document.cookie && document.cookie != "") {
                    var cookies = document.cookie.split(";");
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        if (cookie.substring(0, name.length + 1) == (name + "=")) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break
                        }
                    }
                }
                return cookieValue
            }
        },
        tabPage: function() {
            if (window.tabPage) {
                return tabPage
            }
            try {
                if (parent.tabPage) {
                    return parent.tabPage
                }
                if (parent.parent.tabPage) {
                    return parent.parent.tabPage
                }
                if (top.tabPage) {
                    return top.tabPage
                }
            } catch (e) {}
            return null
        }(),
        initTabPage: function(id, options) {
            if (js.cookie("tabPageModel") == "true") {
                options.maxLength = 1
            }
            var tabPage = window.tabPage;
            return tabPage.initTabPage(id, options)
        },
        addTabPage: function($this, title, url, closeable, refresh) {
            top.prevWindow = window;
            var tabPage = js.tabPage;
            if (!tabPage || typeof tabPage.addTabPage != "function" || (js.cookie("formLayerModel") == "true" && $this && $this.data("layer") != false && ($this.hasClass("btnTool") || $this.hasClass("btnList"))) || ($this && $this.data("layer") == true)) {
                if (js.layer) {
                    var layerWidth, layerHeight, layerLeft, layerTop;
                    if ($this) {
                        layerWidth = $this.data("layerWidth");
                        layerHeight = $this.data("layerHeight");
                        layerLeft = $this.data("layerLeft");
                        layerTop = $this.data("layerTop")
                    }
                    if (layerWidth == null || layerWidth == "") {
                        layerWidth = $(top.window).width();
                        if (layerLeft != null && layerLeft != "") {
                            layerWidth -= parseInt(layerLeft) * 2
                        } else {
                            layerWidth -= 100 * 2
                        }
                    }
                    if (layerHeight == null || layerHeight == "") {
                        layerHeight = $(top.window).height();
                        if (layerTop != null && layerTop != "") {
                            layerHeight -= parseInt(layerTop) * 2
                        } else {
                            layerHeight -= 50 * 2
                        }
                    }
                    js.layer.open({
                        type: 2,
                        maxmin: true,
                        shadeClose: false,
                        title: title,
                        area: [layerWidth + "px", layerHeight + "px"],
                        content: url,
                        contentFormData: {
                            __layer: true
                        },
                        success: function(layero, index) {
                            if ($(js.layer.window).width() < layerWidth || $(js.layer.window).height() < layerHeight) {
                                js.layer.full(index)
                            }
                        }
                    })
                } else {
                    js.windowOpen(url, title, "auto", "auto")
                }
                return
            }
            return tabPage.addTabPage($this, title, url, closeable, refresh)
        },
        getCurrentTabPage: function(currentTabCallback) {
            var tabPage = js.tabPage;
            if (!tabPage || typeof tabPage.getCurrentTabPage != "function") {
                return
            }
            tabPage.getCurrentTabPage(currentTabCallback)
        },
        getPrevTabPage: function(preTabCallback, isCloseCurrentTab) {
            var tabPage = js.tabPage;
            if (!tabPage || typeof tabPage.getPrevTabPage != "function") {
                return
            }
            tabPage.getPrevTabPage(preTabCallback, isCloseCurrentTab)
        },
        closeCurrentTabPage: function(preTabCallback) {
            var tabPage = js.tabPage;
            if (!tabPage || typeof tabPage.closeCurrentTabPage != "function" || window.name.indexOf("layui-layer") != -1) {
                var layerIndex;
                if (js.layer) {
                    layerIndex = js.layer.getFrameIndex(window.name)
                }
                if (layerIndex) {
                    js.layer.close(layerIndex)
                } else {
                    js.windowClose()
                }
                if (typeof preTabCallback == "function") {
                    try {
                        preTabCallback(top.prevWindow)
                    } catch (e) {
                        js.error(e)
                    }
                }
                return
            }
            tabPage.closeCurrentTabPage(preTabCallback)
        }
    };
    window.js = js;
    window.log = js.log;
    window.error = js.error;
    window.lang = window.lang || "zh_CN";
    window.text = js.text
}
)(window.jQuery, window);

!function(a, c) {
    var b = a();
    a.fn.dropdownHover = function(d) {
        return "ontouchstart"in document ? this : (b = b.add(this.parent()),
        this.each(function() {
            function f() {
                m.parents(".navbar").find(".navbar-toggle").is(":visible") || (c.clearTimeout(o),
                c.clearTimeout(j),
                j = c.setTimeout(function() {
                    b.find(":focus").blur(),
                    p.instantlyCloseOthers === !0 && b.removeClass("open"),
                    c.clearTimeout(j),
                    m.attr("aria-expanded", "true"),
                    t.addClass("open"),
                    m.trigger(k)
                }, p.hoverDelay))
            }
            var o, j, m = a(this), t = m.parent(), q = {
                delay: 500,
                hoverDelay: 0,
                instantlyCloseOthers: !0
            }, g = {
                delay: a(this).data("delay"),
                hoverDelay: a(this).data("hover-delay"),
                instantlyCloseOthers: a(this).data("close-others")
            }, k = "show.bs.dropdown", n = "hide.bs.dropdown", p = a.extend(!0, {}, q, d, g);
            t.hover(function(h) {
                return t.hasClass("open") || m.is(h.target) ? void f(h) : !0
            }, function() {
                c.clearTimeout(j),
                o = c.setTimeout(function() {
                    m.attr("aria-expanded", "false"),
                    t.removeClass("open"),
                    m.trigger(n)
                }, p.delay)
            }),
            m.hover(function(h) {
                return t.hasClass("open") || t.is(h.target) ? void f(h) : !0
            }),
            t.find(".dropdown-submenu").each(function() {
                var i, h = a(this);
                h.hover(function() {
                    c.clearTimeout(i),
                    h.children(".dropdown-menu").show(),
                    h.siblings().children(".dropdown-menu").hide()
                }, function() {
                    var l = h.children(".dropdown-menu");
                    i = c.setTimeout(function() {
                        l.hide()
                    }, p.delay)
                })
            })
        }))
    }
    ,
    a(document).ready(function() {
        a('[data-hover="dropdown"]').dropdownHover()
    })
}(jQuery, window);

(function(W) {
        function B(k, h, n) {
            var p = k[0]
                , m = /er/.test(n) ? T : /bl/.test(n) ? O : U
                , l = n == j ? {
                checked: p[U],
                disabled: p[O],
                indeterminate: "true" == k.attr(T) || "false" == k.attr(o)
            } : p[m];
            if (/^(ch|di|in)/.test(n) && !l) {
                P(k, m)
            } else {
                if (/^(un|en|de)/.test(n) && l) {
                    N(k, m)
                } else {
                    if (n == j) {
                        for (m in l) {
                            l[m] ? P(k, m, !0) : N(k, m, !0)
                        }
                    } else {
                        if (!h || "toggle" == n) {
                            if (!h) {
                                k[R]("ifClicked")
                            }
                            l ? p[S] !== C && N(k, m) : P(k, m)
                        }
                    }
                }
            }
        }
        function P(y, x, u) {
            var v = y[0]
                , t = y.parent()
                , q = x == U
                , n = x == T
                , m = x == O
                , w = n ? o : q ? K : "enabled"
                , l = V(y, w + i(v[S]))
                , s = V(y, x + i(v[S]));
            if (!0 !== v[x]) {
                if (!u && x == U && v[S] == C && v.name) {
                    var k = y.closest("form")
                        , h = 'input[name="' + v.name + '"]'
                        , h = k.length ? k.find(h) : W(h);
                    h.each(function() {
                        this !== v && W(this).data(Q) && N(W(this), x)
                    })
                }
                n ? (v[x] = !0,
                v[U] && N(y, U, "force")) : (u || (v[x] = !0),
                q && v[T] && N(y, T, !1));
                b(y, q, x, u)
            }
            v[O] && V(y, f, !0) && t.find("." + g).css(f, "default");
            t[A](s || V(y, x) || "");
            m ? t.attr("aria-disabled", "true") : t.attr("aria-checked", n ? "mixed" : "true");
            t[c](l || V(y, w) || "")
        }
        function N(y, x, v) {
            var w = y[0]
                , s = y.parent()
                , n = x == U
                , m = x == T
                , k = x == O
                , l = m ? o : n ? K : "enabled"
                , D = V(y, l + i(w[S]))
                , z = V(y, x + i(w[S]));
            if (!1 !== w[x]) {
                if (m || !v || "force" == v) {
                    w[x] = !1
                }
                b(y, n, l, v)
            }
            !w[O] && V(y, f, !0) && s.find("." + g).css(f, "pointer");
            s[c](z || V(y, x) || "");
            k ? s.attr("aria-disabled", "false") : s.attr("aria-checked", "false");
            s[A](D || V(y, l) || "")
        }
        function a(k, h) {
            if (k.data(Q)) {
                k.parent().html(k.attr("style", k.data(Q).s || ""));
                if (h) {
                    k[R](h)
                }
                k.off(".i").unwrap();
                W(r + '[for="' + k[0].id + '"]').add(k.closest(r)).off(".i")
            }
        }
        function V(k, h, l) {
            if (k.data(Q)) {
                return k.data(Q).o[h + (l ? "" : "Class")]
            }
        }
        function i(h) {
            return h.charAt(0).toUpperCase() + h.slice(1)
        }
        function b(k, h, l, m) {
            if (!m) {
                if (h) {
                    k[R]("ifToggled")
                }
                k[R]("ifChanged")[R]("if" + i(l))
            }
        }
        var Q = "iCheck"
            , g = Q + "-helper"
            , C = "radio"
            , U = "checked"
            , K = "un" + U
            , O = "disabled"
            , o = "determinate"
            , T = "in" + o
            , j = "update"
            , S = "type"
            , A = "addClass"
            , c = "removeClass"
            , R = "trigger"
            , r = "label"
            , f = "cursor"
            , d = /ipad|iphone|ipod|android|blackberry|windows phone|opera mini|silk/i.test(navigator.userAgent);
        W.fn[Q] = function(D, v) {
            var s = 'input[type="checkbox"], input[type="' + C + '"]'
                , t = W()
                , q = function(k) {
                k.each(function() {
                    var w = W(this);
                    t = w.is(s) ? t.add(w) : t.add(w.find(s))
                })
            };
            if (/^(check|uncheck|toggle|indeterminate|determinate|disable|enable|update|destroy)$/i.test(D)) {
                return D = D.toLowerCase(),
                    q(this),
                    t.each(function() {
                        var k = W(this);
                        "destroy" == D ? a(k, "ifDestroyed") : B(k, !0, D);
                        W.isFunction(v) && v()
                    })
            }
            if ("object" != typeof D && D) {
                return this
            }
            var p = W.extend({
                checkedClass: U,
                disabledClass: O,
                indeterminateClass: T,
                checkboxClass: "icheckbox_minimal-grey",
                radioClass: "iradio_minimal-grey",
                labelHover: !0,
                aria: !1
            }, D)
                , n = p.handle
                , m = p.hoverClass || "hover"
                , z = p.focusClass || "focus"
                , E = p.activeClass || "active"
                , u = !!p.labelHover
                , l = p.labelHoverClass || "hover"
                , h = ("" + p.increaseArea).replace("%", "") | 0;
            if ("checkbox" == n || n == C) {
                s = 'input[type="' + n + '"]'
            }
            -50 > h && (h = -50);
            q(this);
            return t.each(function() {
                var L = W(this);
                a(L);
                var I = this
                    , J = I.id
                    , G = -h + "%"
                    , H = 100 + 2 * h + "%"
                    , H = {
                    position: "absolute",
                    top: G,
                    left: G,
                    display: "block",
                    width: H,
                    height: H,
                    margin: 0,
                    padding: 0,
                    background: "#fff",
                    border: 0,
                    opacity: 0
                }
                    , G = d ? {
                    position: "absolute",
                    visibility: "hidden"
                } : h ? H : {
                    position: "absolute",
                    opacity: 0
                }
                    , y = "checkbox" == I[S] ? p.checkboxClass || "icheckbox" : p.radioClass || "i" + C
                    , x = W(r + '[for="' + J + '"]').add(L.closest(r))
                    , w = !!p.aria
                    , M = Q + "-" + Math.random().toString(36).replace("0.", "")
                    , F = '<div class="' + y + '" ' + (w ? 'role="' + I[S] + '" ' : "");
                x.length && w && x.each(function() {
                    F += 'aria-labelledby="';
                    this.id ? F += this.id : (this.id = M,
                        F += M);
                    F += '"'
                });
                F = L.wrap(F + "/>")[R]("ifCreated").parent().append(p.insert);
                H = W('<ins class="' + g + '"/>').css(H).appendTo(F);
                L.data(Q, {
                    o: p,
                    s: L.attr("style")
                }).css(G);
                p.inheritClass && F[A](I.className || "");
                p.inheritID && J && F.attr("id", Q + "-" + J);
                "static" == F.css("position") && F.css("position", "relative");
                B(L, !0, j);
                if (x.length) {
                    x.on("click.i mouseover.i mouseout.i touchbegin.i touchend.i", function(k) {
                        var Y = k[S]
                            , X = W(this);
                        if (!I[O]) {
                            if ("click" == Y) {
                                if (W(k.target).is("a")) {
                                    return
                                }
                                B(L, !1, !0)
                            } else {
                                u && (/ut|nd/.test(Y) ? (F[c](m),
                                    X[c](l)) : (F[A](m),
                                    X[A](l)))
                            }
                            if (d) {
                                k.stopPropagation()
                            } else {
                                return !1
                            }
                        }
                    })
                }
                L.on("click.i focus.i blur.i keyup.i keydown.i keypress.i", function(k) {
                    var X = k[S];
                    k = k.keyCode;
                    if ("click" == X) {
                        return !1
                    }
                    if ("keydown" == X && 32 == k) {
                        return I[S] == C && I[U] || (I[U] ? N(L, U) : P(L, U)),
                            !1
                    }
                    if ("keyup" == X && I[S] == C) {
                        !I[U] && P(L, U)
                    } else {
                        if (/us|ur/.test(X)) {
                            F["blur" == X ? c : A](z)
                        }
                    }
                });
                H.on("click mousedown mouseup mouseover mouseout touchbegin.i touchend.i", function(k) {
                    var Y = k[S]
                        , X = /wn|up/.test(Y) ? E : m;
                    if (!I[O]) {
                        if ("click" == Y) {
                            B(L, !1, !0)
                        } else {
                            if (/wn|er|in/.test(Y)) {
                                F[A](X)
                            } else {
                                F[c](X + " " + E)
                            }
                            if (x.length && u && X == m) {
                                x[/ut|nd/.test(Y) ? c : A](l)
                            }
                        }
                        if (d) {
                            k.stopPropagation()
                        } else {
                            return !1
                        }
                    }
                })
            })
        }
    }
)(window.jQuery || window.Zepto);