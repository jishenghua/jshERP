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
        $("textarea.autoHeight").textareaAutoHieght();
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
+function(d) {
    var b = "lte.boxwidget";
    var g = {
        animationSpeed: 500,
        collapseTrigger: '[data-widget="collapse"]',
        removeTrigger: '[data-widget="remove"]',
        collapseIcon: "fa-minus",
        expandIcon: "fa-plus",
        removeIcon: "fa-times"
    };
    var a = {
        data: ".box",
        collapsed: ".collapsed-box",
        body: ".box-body",
        footer: ".box-footer",
        tools: ".box-tools"
    };
    var i = {
        collapsed: "collapsed-box"
    };
    var j = {
        collapsed: "collapsed.boxwidget",
        expanded: "expanded.boxwidget",
        removed: "removed.boxwidget"
    };
    var f = function(l, k) {
        this.element = l;
        this.options = k;
        this._setUpListeners()
    };
    f.prototype.toggle = function() {
        var k = !d(this.element).is(a.collapsed);
        if (k) {
            this.collapse()
        } else {
            this.expand()
        }
    }
    ;
    f.prototype.expand = function() {
        var m = d.Event(j.expanded);
        var l = this.options.collapseIcon;
        var k = this.options.expandIcon;
        d(this.element).removeClass(i.collapsed);
        d(this.element).find(a.tools).find("." + k).removeClass(k).addClass(l);
        d(this.element).find(a.body + ", " + a.footer).slideDown(this.options.animationSpeed, function() {
            d(this.element).trigger(m)
        }
        .bind(this))
    }
    ;
    f.prototype.collapse = function() {
        var l = d.Event(j.collapsed);
        var m = this.options.collapseIcon;
        var k = this.options.expandIcon;
        d(this.element).find(a.tools).find("." + m).removeClass(m).addClass(k);
        d(this.element).find(a.body + ", " + a.footer).slideUp(this.options.animationSpeed, function() {
            d(this.element).addClass(i.collapsed);
            d(this.element).trigger(l)
        }
        .bind(this))
    }
    ;
    f.prototype.remove = function() {
        var k = d.Event(j.removed);
        d(this.element).slideUp(this.options.animationSpeed, function() {
            d(this.element).trigger(k);
            d(this.element).remove()
        }
        .bind(this))
    }
    ;
    f.prototype._setUpListeners = function() {
        var k = this;
        d(this.element).on("click", this.options.collapseTrigger, function(l) {
            if (l) {
                l.preventDefault()
            }
            k.toggle()
        });
        d(this.element).on("click", this.options.removeTrigger, function(l) {
            if (l) {
                l.preventDefault()
            }
            k.remove()
        });
        d(this.options.collapseTrigger).css({
            cursor: "pointer"
        });
        d(this.options.removeTrigger).css({
            cursor: "pointer"
        })
    }
    ;
    function h(k) {
        return this.each(function() {
            var n = d(this);
            var m = n.data(b);
            if (!m) {
                var l = d.extend({}, g, n.data(), typeof k == "object" && k);
                n.data(b, (m = new f(n,l)))
            }
            if (typeof k == "string") {
                if (typeof m[k] == "undefined") {
                    throw new Error("No method named " + k)
                }
                m[k]()
            }
        })
    }
    var c = d.fn.boxWidget;
    d.fn.boxWidget = h;
    d.fn.boxWidget.Constructor = f;
    d.fn.boxWidget.noConflict = function() {
        d.fn.boxWidget = c;
        return this
    }
    ;
    d(function() {
        d(a.data).each(function() {
            h.call(d(this))
        });
        d(".box-child").boxWidget({
            collapseTrigger: '[data-widget="collapse-child"]',
            removeTrigger: '[data-widget="remove-child"]'
        })
    })
}(jQuery);
!(function(a) {
    a.fn.extend({
        placeholder: function(b) {
            b = a.extend({
                placeholderColor: "#ACA899",
                isUseSpan: true,
                onInput: true,
                noFixClass: "placeholder-no-fix"
            }, b);
            a(this).each(function() {
                var h = this;
                var c = "placeholder"in document.createElement("input");
                if (!c) {
                    if (a(h).hasClass(b.noFixClass)) {
                        return
                    }
                    var i = a(h).attr("placeholder");
                    var d = a(h).css("color");
                    if (b.isUseSpan == false) {
                        a(h).focus(function() {
                            var n = new RegExp("^" + i + "$|^$");
                            n.test(a(h).val()) && a(h).val("").css("color", d)
                        }).blur(function() {
                            if (a(h).val() == i) {
                                a(h).css("color", d)
                            } else {
                                if (a(h).val().length == 0) {
                                    a(h).val(i).css("color", b.placeholderColor)
                                }
                            }
                        }).trigger("blur")
                    } else {
                        var f = "";
                        if (a(h).parent().hasClass("input-group")) {
                            f = "left:" + a(h).position().left + "px;"
                        }
                        var k = a(h).width() == 0 ? 150 : a(h).width();
                        var l = a(h).hasClass("input-sm") ? a(h).height() : a(h).outerHeight();
                        var m = a('<span class="wrap-placeholder" style="position:absolute;display:inline-block;overflow:hidden;z-index:1000;color:' + b.placeholderColor + "; width:" + k + "px; height:" + l + "px;" + f + '">' + i + "</span>");
                        m.css({
                            "margin-left": a(h).css("margin-left"),
                            "margin-top": a(h).css("margin-top"),
                            "font-size": a(h).css("font-size"),
                            "font-family": a(h).css("font-family"),
                            "font-weight": a(h).css("font-weight"),
                            "line-height": h.nodeName.toLowerCase() == "textarea" ? a(h).css("line-weight") : l + "px",
                            "padding-left": parseInt(a(h).css("padding-left")) + 2 + "px",
                            "padding-top": h.nodeName.toLowerCase() == "textarea" ? parseInt(a(h).css("padding-top")) + 2 : 0
                        });
                        var g = a(h).prev();
                        if (g.hasClass("wrap-placeholder")) {
                            g.remove()
                        }
                        a(h).before(m.click(function() {
                            a(h).trigger("focus")
                        }));
                        a(h).val().length != 0 && m.hide();
                        if (b.onInput) {
                            var j = typeof (h.oninput) == "object" ? "input" : "propertychange";
                            a(h).bind(j, function() {
                                m[0].style.display = a(h).val().length != 0 ? "none" : "inline-block"
                            })
                        } else {
                            a(h).focus(function() {
                                m.hide()
                            }).blur(function() {
                                /^$/.test(a(h).val()) && m.show()
                            })
                        }
                    }
                }
            });
            return this
        }
    })
}
)(jQuery);
(function(a) {
    a.fn.textareaAutoHieght = function(b) {
        var d = {
            maxHeight: null,
            minHeight: a(this).attr("rows") * 18
        };
        var c = a.extend({}, d, b);
        return a(this).each(function() {
            a(this).bind("paste cut keydown keyup focus blur", function() {
                var f, g = this.style;
                this.style.height = c.minHeight + "px";
                if (this.scrollHeight > c.minHeight) {
                    if (c.maxHeight && this.scrollHeight > c.maxHeight) {
                        f = c.maxHeight;
                        g.overflowY = "scroll"
                    } else {
                        f = this.scrollHeight;
                        g.overflowY = "hidden"
                    }
                    g.height = f + "px"
                }
            }).trigger("blur")
        })
    }
}
)(jQuery);
(function(a) {
    a.fn.iframeWindow = function() {
        var b = a(this).find("iframe");
        if (b.length > 0) {
            return b[0].contentWindow
        }
        return null
    }
}
)(jQuery);
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
(function(f, d, a) {
    var b, g, c;
    c = "resizeEnd";
    g = {
        delay: 150
    };
    b = function(i, h, j) {
        if (typeof h === "function") {
            j = h;
            h = {}
        }
        j = j || null;
        this.element = i;
        this.settings = f.extend({}, g, h);
        this._defaults = g;
        this._name = c;
        this._timeout = false;
        this._callback = j;
        return this.init()
    }
    ;
    b.prototype = {
        init: function() {
            var h, i;
            i = this;
            h = f(this.element);
            return h.on("resize", function() {
                return i.initResize()
            })
        },
        getUTCDate: function(i) {
            var h;
            i = i || new Date();
            h = Date.UTC(i.getUTCFullYear(), i.getUTCMonth(), i.getUTCDate(), i.getUTCHours(), i.getUTCMinutes(), i.getUTCSeconds(), i.getUTCMilliseconds());
            return h
        },
        initResize: function() {
            var h;
            h = this;
            h.controlTime = h.getUTCDate();
            if (h._timeout === false) {
                h._timeout = true;
                return setTimeout(function() {
                    return h.runCallback(h)
                }, h.settings.delay)
            }
        },
        runCallback: function(i) {
            var h;
            h = i.getUTCDate();
            if (h - i.controlTime < i.settings.delay) {
                return setTimeout(function() {
                    return i.runCallback(i)
                }, i.settings.delay)
            } else {
                i._timeout = false;
                return i._callback()
            }
        }
    };
    return f.fn[c] = function(h, i) {
        return this.each(function() {
            if (!f.data(this, "plugin_" + c)) {
                return f.data(this, "plugin_" + c, new b(this,h,i))
            }
        })
    }
}
)(jQuery, window, document);
!function(a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : a("undefined" != typeof jQuery ? jQuery : window.Zepto)
}(function(e) {
    function t(t) {
        var r = t.data;
        t.isDefaultPrevented() || (t.preventDefault(),
        e(t.target).ajaxSubmit(r))
    }
    function r(t) {
        var r = t.target
          , a = e(r);
        if (!a.is("[type=submit],[type=image]")) {
            var n = a.closest("[type=submit]");
            if (0 === n.length) {
                return
            }
            r = n[0]
        }
        var i = this;
        if (i.clk = r,
        "image" == r.type) {
            if (void 0 !== t.offsetX) {
                i.clk_x = t.offsetX,
                i.clk_y = t.offsetY
            } else {
                if ("function" == typeof e.fn.offset) {
                    var o = a.offset();
                    i.clk_x = t.pageX - o.left,
                    i.clk_y = t.pageY - o.top
                } else {
                    i.clk_x = t.pageX - r.offsetLeft,
                    i.clk_y = t.pageY - r.offsetTop
                }
            }
        }
        setTimeout(function() {
            i.clk = i.clk_x = i.clk_y = null
        }, 100)
    }
    function a() {
        if (e.fn.ajaxSubmit.debug) {
            var t = "[jquery.form] " + Array.prototype.join.call(arguments, "");
            window.console && window.console.log ? window.console.log(t) : window.opera && window.opera.postError && window.opera.postError(t)
        }
    }
    var n = {};
    n.fileapi = void 0 !== e("<input type='file'/>").get(0).files,
    n.formdata = void 0 !== window.FormData;
    var i = !!e.fn.prop;
    e.fn.attr2 = function() {
        if (!i) {
            return this.attr.apply(this, arguments)
        }
        var e = this.prop.apply(this, arguments);
        return e && e.jquery || "string" == typeof e ? e : this.attr.apply(this, arguments)
    }
    ,
    e.fn.ajaxSubmit = function(t) {
        function r(r) {
            var a, n, i = e.param(r, t.traditional).split("&"), o = i.length, s = [];
            for (a = 0; o > a; a++) {
                i[a] = i[a].replace(/\+/g, " "),
                n = i[a].split("="),
                s.push([decodeURIComponent(n[0]), decodeURIComponent(n[1])])
            }
            return s
        }
        function o(a) {
            for (var n = new FormData, i = 0; i < a.length; i++) {
                n.append(a[i].name, a[i].value)
            }
            if (t.extraData) {
                var o = r(t.extraData);
                for (i = 0; i < o.length; i++) {
                    o[i] && n.append(o[i][0], o[i][1])
                }
            }
            t.data = null;
            var s = e.extend(!0, {}, e.ajaxSettings, t, {
                contentType: !1,
                processData: !1,
                cache: !1,
                type: u || "POST"
            });
            t.uploadProgress && (s.xhr = function() {
                var r = e.ajaxSettings.xhr();
                return r.upload && r.upload.addEventListener("progress", function(e) {
                    var r = 0
                      , a = e.loaded || e.position
                      , n = e.total;
                    e.lengthComputable && (r = Math.ceil(a / n * 100)),
                    t.uploadProgress(e, a, n, r)
                }, !1),
                r
            }
            ),
            s.data = null;
            var c = s.beforeSend;
            return s.beforeSend = function(e, r) {
                r.data = t.formData ? t.formData : n,
                c && c.call(this, e, r)
            }
            ,
            e.ajax(s)
        }
        function s(r) {
            function n(e) {
                var t = null;
                try {
                    e.contentWindow && (t = e.contentWindow.document)
                } catch (r) {
                    a("cannot get iframe.contentWindow document: " + r)
                }
                if (t) {
                    return t
                }
                try {
                    t = e.contentDocument ? e.contentDocument : e.document
                } catch (r) {
                    a("cannot get iframe.contentDocument: " + r),
                    t = e.document
                }
                return t
            }
            function o() {
                function t() {
                    try {
                        var e = n(g).readyState;
                        a("state = " + e),
                        e && "uninitialized" == e.toLowerCase() && setTimeout(t, 50)
                    } catch (r) {
                        a("Server abort: ", r, " (", r.name, ")"),
                        s(k),
                        j && clearTimeout(j),
                        j = void 0
                    }
                }
                var r = f.attr2("target")
                  , i = f.attr2("action")
                  , o = "multipart/form-data"
                  , c = f.attr("enctype") || f.attr("encoding") || o;
                w.setAttribute("target", p),
                (!u || /post/i.test(u)) && w.setAttribute("method", "POST"),
                i != m.url && w.setAttribute("action", m.url),
                m.skipEncodingOverride || u && !/post/i.test(u) || f.attr({
                    encoding: "multipart/form-data",
                    enctype: "multipart/form-data"
                }),
                m.timeout && (j = setTimeout(function() {
                    T = !0,
                    s(D)
                }, m.timeout));
                var l = [];
                try {
                    if (m.extraData) {
                        for (var d in m.extraData) {
                            m.extraData.hasOwnProperty(d) && l.push(e.isPlainObject(m.extraData[d]) && m.extraData[d].hasOwnProperty("name") && m.extraData[d].hasOwnProperty("value") ? e('<input type="hidden" name="' + m.extraData[d].name + '">').val(m.extraData[d].value).appendTo(w)[0] : e('<input type="hidden" name="' + d + '">').val(m.extraData[d]).appendTo(w)[0])
                        }
                    }
                    m.iframeTarget || v.appendTo("body"),
                    g.attachEvent ? g.attachEvent("onload", s) : g.addEventListener("load", s, !1),
                    setTimeout(t, 15);
                    try {
                        w.submit()
                    } catch (h) {
                        var x = document.createElement("form").submit;
                        x.apply(w)
                    }
                } finally {
                    w.setAttribute("action", i),
                    w.setAttribute("enctype", c),
                    r ? w.setAttribute("target", r) : f.removeAttr("target"),
                    e(l).remove()
                }
            }
            function s(t) {
                if (!x.aborted && !F) {
                    if (M = n(g),
                    M || (a("cannot access response document"),
                    t = k),
                    t === D && x) {
                        return x.abort("timeout"),
                        void S.reject(x, "timeout")
                    }
                    if (t == k && x) {
                        return x.abort("server abort"),
                        void S.reject(x, "error", "server abort")
                    }
                    if (M && M.location.href != m.iframeSrc || T) {
                        g.detachEvent ? g.detachEvent("onload", s) : g.removeEventListener("load", s, !1);
                        var r, i = "success";
                        try {
                            if (T) {
                                throw "timeout"
                            }
                            var o = "xml" == m.dataType || M.XMLDocument || e.isXMLDoc(M);
                            if (a("isXml=" + o),
                            !o && window.opera && (null === M.body || !M.body.innerHTML) && --O) {
                                return a("requeing onLoad callback, DOM not available"),
                                void setTimeout(s, 250)
                            }
                            var u = M.body ? M.body : M.documentElement;
                            x.responseText = u ? u.innerHTML : null,
                            x.responseXML = M.XMLDocument ? M.XMLDocument : M,
                            o && (m.dataType = "xml"),
                            x.getResponseHeader = function(e) {
                                var t = {
                                    "content-type": m.dataType
                                };
                                return t[e.toLowerCase()]
                            }
                            ,
                            u && (x.status = Number(u.getAttribute("status")) || x.status,
                            x.statusText = u.getAttribute("statusText") || x.statusText);
                            var c = (m.dataType || "").toLowerCase()
                              , l = /(json|script|text)/.test(c);
                            if (l || m.textarea) {
                                var f = M.getElementsByTagName("textarea")[0];
                                if (f) {
                                    x.responseText = f.value,
                                    x.status = Number(f.getAttribute("status")) || x.status,
                                    x.statusText = f.getAttribute("statusText") || x.statusText
                                } else {
                                    if (l) {
                                        var p = M.getElementsByTagName("pre")[0]
                                          , h = M.getElementsByTagName("body")[0];
                                        p ? x.responseText = p.textContent ? p.textContent : p.innerText : h && (x.responseText = h.textContent ? h.textContent : h.innerText)
                                    }
                                }
                            } else {
                                "xml" == c && !x.responseXML && x.responseText && (x.responseXML = X(x.responseText))
                            }
                            try {
                                E = _(x, c, m)
                            } catch (y) {
                                i = "parsererror",
                                x.error = r = y || i
                            }
                        } catch (y) {
                            a("error caught: ", y),
                            i = "error",
                            x.error = r = y || i
                        }
                        x.aborted && (a("upload aborted"),
                        i = null),
                        x.status && (i = x.status >= 200 && x.status < 300 || 304 === x.status ? "success" : "error"),
                        "success" === i ? (m.success && m.success.call(m.context, E, "success", x),
                        S.resolve(x.responseText, "success", x),
                        d && e.event.trigger("ajaxSuccess", [x, m])) : i && (void 0 === r && (r = x.statusText),
                        m.error && m.error.call(m.context, x, i, r),
                        S.reject(x, "error", r),
                        d && e.event.trigger("ajaxError", [x, m, r])),
                        d && e.event.trigger("ajaxComplete", [x, m]),
                        d && !--e.active && e.event.trigger("ajaxStop"),
                        m.complete && m.complete.call(m.context, x, i),
                        F = !0,
                        m.timeout && clearTimeout(j),
                        setTimeout(function() {
                            m.iframeTarget ? v.attr("src", m.iframeSrc) : v.remove(),
                            x.responseXML = null
                        }, 100)
                    }
                }
            }
            var c, l, m, d, p, v, g, x, y, b, T, j, w = f[0], S = e.Deferred();
            if (S.abort = function(e) {
                x.abort(e)
            }
            ,
            r) {
                for (l = 0; l < h.length; l++) {
                    c = e(h[l]),
                    i ? c.prop("disabled", !1) : c.removeAttr("disabled")
                }
            }
            if (m = e.extend(!0, {}, e.ajaxSettings, t),
            m.context = m.context || m,
            p = "jqFormIO" + (new Date).getTime(),
            m.iframeTarget ? (v = e(m.iframeTarget),
            b = v.attr2("name"),
            b ? p = b : v.attr2("name", p)) : (v = e('<iframe name="' + p + '" src="' + m.iframeSrc + '" />'),
            v.css({
                position: "absolute",
                top: "-1000px",
                left: "-1000px"
            })),
            g = v[0],
            x = {
                aborted: 0,
                responseText: null,
                responseXML: null,
                status: 0,
                statusText: "n/a",
                getAllResponseHeaders: function() {},
                getResponseHeader: function() {},
                setRequestHeader: function() {},
                abort: function(t) {
                    var r = "timeout" === t ? "timeout" : "aborted";
                    a("aborting upload... " + r),
                    this.aborted = 1;
                    try {
                        g.contentWindow.document.execCommand && g.contentWindow.document.execCommand("Stop")
                    } catch (n) {}
                    v.attr("src", m.iframeSrc),
                    x.error = r,
                    m.error && m.error.call(m.context, x, r, t),
                    d && e.event.trigger("ajaxError", [x, m, r]),
                    m.complete && m.complete.call(m.context, x, r)
                }
            },
            d = m.global,
            d && 0 === e.active++ && e.event.trigger("ajaxStart"),
            d && e.event.trigger("ajaxSend", [x, m]),
            m.beforeSend && m.beforeSend.call(m.context, x, m) === !1) {
                return m.global && e.active--,
                S.reject(),
                S
            }
            if (x.aborted) {
                return S.reject(),
                S
            }
            y = w.clk,
            y && (b = y.name,
            b && !y.disabled && (m.extraData = m.extraData || {},
            m.extraData[b] = y.value,
            "image" == y.type && (m.extraData[b + ".x"] = w.clk_x,
            m.extraData[b + ".y"] = w.clk_y)));
            var D = 1
              , k = 2
              , A = e("meta[name=csrf-token]").attr("content")
              , L = e("meta[name=csrf-param]").attr("content");
            L && A && (m.extraData = m.extraData || {},
            m.extraData[L] = A),
            m.forceSync ? o() : setTimeout(o, 10);
            var E, M, F, O = 50, X = e.parseXML || function(e, t) {
                return window.ActiveXObject ? (t = new ActiveXObject("Microsoft.XMLDOM"),
                t.async = "false",
                t.loadXML(e)) : t = (new DOMParser).parseFromString(e, "text/xml"),
                t && t.documentElement && "parsererror" != t.documentElement.nodeName ? t : null
            }
            , C = e.parseJSON || function(e) {
                return window.eval("(" + e + ")")
            }
            , _ = function(t, r, a) {
                var n = t.getResponseHeader("content-type") || ""
                  , i = "xml" === r || !r && n.indexOf("xml") >= 0
                  , o = i ? t.responseXML : t.responseText;
                return i && "parsererror" === o.documentElement.nodeName && e.error && e.error("parsererror"),
                a && a.dataFilter && (o = a.dataFilter(o, r)),
                "string" == typeof o && ("json" === r || !r && n.indexOf("json") >= 0 ? o = C(o) : ("script" === r || !r && n.indexOf("javascript") >= 0) && e.globalEval(o)),
                o
            };
            return S
        }
        if (!this.length) {
            return a("ajaxSubmit: skipping submit process - no element selected"),
            this
        }
        var u, c, l, f = this;
        "function" == typeof t ? t = {
            success: t
        } : void 0 === t && (t = {}),
        u = t.type || this.attr2("method"),
        c = t.url || this.attr2("action"),
        l = "string" == typeof c ? e.trim(c) : "",
        l = l || window.location.href || "",
        l && (l = (l.match(/^([^#]+)/) || [])[1]),
        t = e.extend(!0, {
            url: l,
            success: e.ajaxSettings.success,
            type: u || e.ajaxSettings.type,
            iframeSrc: /^https/i.test(window.location.href || "") ? "javascript:false" : "about:blank"
        }, t);
        var m = {};
        if (this.trigger("form-pre-serialize", [this, t, m]),
        m.veto) {
            return a("ajaxSubmit: submit vetoed via form-pre-serialize trigger"),
            this
        }
        if (t.beforeSerialize && t.beforeSerialize(this, t) === !1) {
            return a("ajaxSubmit: submit aborted via beforeSerialize callback"),
            this
        }
        var d = t.traditional;
        void 0 === d && (d = e.ajaxSettings.traditional);
        var p, h = [], v = this.formToArray(t.semantic, h);
        if (t.data && (t.extraData = t.data,
        p = e.param(t.data, d)),
        t.beforeSubmit && t.beforeSubmit(v, this, t) === !1) {
            return a("ajaxSubmit: submit aborted via beforeSubmit callback"),
            this
        }
        if (this.trigger("form-submit-validate", [v, this, t, m]),
        m.veto) {
            return a("ajaxSubmit: submit vetoed via form-submit-validate trigger"),
            this
        }
        var g = e.param(v, d);
        p && (g = g ? g + "&" + p : p),
        "GET" == t.type.toUpperCase() ? (t.url += (t.url.indexOf("?") >= 0 ? "&" : "?") + g,
        t.data = null) : t.data = g;
        var x = [];
        if (t.resetForm && x.push(function() {
            f.resetForm()
        }),
        t.clearForm && x.push(function() {
            f.clearForm(t.includeHidden)
        }),
        !t.dataType && t.target) {
            var y = t.success || function() {}
            ;
            x.push(function(r) {
                var a = t.replaceTarget ? "replaceWith" : "html";
                e(t.target)[a](r).each(y, arguments)
            })
        } else {
            t.success && x.push(t.success)
        }
        if (t.success = function(e, r, a) {
            for (var n = t.context || this, i = 0, o = x.length; o > i; i++) {
                x[i].apply(n, [e, r, a || f, f])
            }
        }
        ,
        t.error) {
            var b = t.error;
            t.error = function(e, r, a) {
                var n = t.context || this;
                b.apply(n, [e, r, a, f])
            }
        }
        if (t.complete) {
            var T = t.complete;
            t.complete = function(e, r) {
                var a = t.context || this;
                T.apply(a, [e, r, f])
            }
        }
        var j = e("input[type=file]:enabled", this).filter(function() {
            return "" !== e(this).val()
        })
          , w = j.length > 0
          , S = "multipart/form-data"
          , D = f.attr("enctype") == S || f.attr("encoding") == S
          , k = n.fileapi && n.formdata;
        a("fileAPI :" + k);
        var A, L = (w || D) && !k;
        t.iframe !== !1 && (t.iframe || L) ? t.closeKeepAlive ? e.get(t.closeKeepAlive, function() {
            A = s(v)
        }) : A = s(v) : A = (w || D) && k ? o(v) : e.ajax(t),
        f.removeData("jqxhr").data("jqxhr", A);
        for (var E = 0; E < h.length; E++) {
            h[E] = null
        }
        return this.trigger("form-submit-notify", [this, t]),
        this
    }
    ,
    e.fn.ajaxForm = function(n) {
        if (n = n || {},
        n.delegation = n.delegation && e.isFunction(e.fn.on),
        !n.delegation && 0 === this.length) {
            var i = {
                s: this.selector,
                c: this.context
            };
            return !e.isReady && i.s ? (a("DOM not ready, queuing ajaxForm"),
            e(function() {
                e(i.s, i.c).ajaxForm(n)
            }),
            this) : (a("terminating; zero elements found by selector" + (e.isReady ? "" : " (DOM not ready)")),
            this)
        }
        return n.delegation ? (e(document).off("submit.form-plugin", this.selector, t).off("click.form-plugin", this.selector, r).on("submit.form-plugin", this.selector, n, t).on("click.form-plugin", this.selector, n, r),
        this) : this.ajaxFormUnbind().bind("submit.form-plugin", n, t).bind("click.form-plugin", n, r)
    }
    ,
    e.fn.ajaxFormUnbind = function() {
        return this.unbind("submit.form-plugin click.form-plugin")
    }
    ,
    e.fn.formToArray = function(t, r) {
        var a = [];
        if (0 === this.length) {
            return a
        }
        var i, o = this[0], s = this.attr("id"), u = t ? o.getElementsByTagName("*") : o.elements;
        if (u && !/MSIE [678]/.test(navigator.userAgent) && (u = e(u).get()),
        s && (i = e(':input[form="' + s + '"]').get(),
        i.length && (u = (u || []).concat(i))),
        !u || !u.length) {
            return a
        }
        var c, l, f, m, d, p, h;
        for (c = 0,
        p = u.length; p > c; c++) {
            if (d = u[c],
            f = d.name,
            f && !d.disabled) {
                if (t && o.clk && "image" == d.type) {
                    o.clk == d && (a.push({
                        name: f,
                        value: e(d).val(),
                        type: d.type
                    }),
                    a.push({
                        name: f + ".x",
                        value: o.clk_x
                    }, {
                        name: f + ".y",
                        value: o.clk_y
                    }))
                } else {
                    if (m = e.fieldValue(d, !0),
                    m && m.constructor == Array) {
                        for (r && r.push(d),
                        l = 0,
                        h = m.length; h > l; l++) {
                            a.push({
                                name: f,
                                value: m[l]
                            })
                        }
                    } else {
                        if (n.fileapi && "file" == d.type) {
                            r && r.push(d);
                            var v = d.files;
                            if (v.length) {
                                for (l = 0; l < v.length; l++) {
                                    a.push({
                                        name: f,
                                        value: v[l],
                                        type: d.type
                                    })
                                }
                            } else {
                                a.push({
                                    name: f,
                                    value: "",
                                    type: d.type
                                })
                            }
                        } else {
                            null !== m && "undefined" != typeof m && (r && r.push(d),
                            a.push({
                                name: f,
                                value: m,
                                type: d.type,
                                required: d.required
                            }))
                        }
                    }
                }
            }
        }
        if (!t && o.clk) {
            var g = e(o.clk)
              , x = g[0];
            f = x.name,
            f && !x.disabled && "image" == x.type && (a.push({
                name: f,
                value: g.val()
            }),
            a.push({
                name: f + ".x",
                value: o.clk_x
            }, {
                name: f + ".y",
                value: o.clk_y
            }))
        }
        return a
    }
    ,
    e.fn.formSerialize = function(t) {
        return e.param(this.formToArray(t))
    }
    ,
    e.fn.fieldSerialize = function(t) {
        var r = [];
        return this.each(function() {
            var a = this.name;
            if (a) {
                var n = e.fieldValue(this, t);
                if (n && n.constructor == Array) {
                    for (var i = 0, o = n.length; o > i; i++) {
                        r.push({
                            name: a,
                            value: n[i]
                        })
                    }
                } else {
                    null !== n && "undefined" != typeof n && r.push({
                        name: this.name,
                        value: n
                    })
                }
            }
        }),
        e.param(r)
    }
    ,
    e.fn.fieldValue = function(t) {
        for (var r = [], a = 0, n = this.length; n > a; a++) {
            var i = this[a]
              , o = e.fieldValue(i, t);
            null === o || "undefined" == typeof o || o.constructor == Array && !o.length || (o.constructor == Array ? e.merge(r, o) : r.push(o))
        }
        return r
    }
    ,
    e.fieldValue = function(t, r) {
        var a = t.name
          , n = t.type
          , i = t.tagName.toLowerCase();
        if (void 0 === r && (r = !0),
        r && (!a || t.disabled || "reset" == n || "button" == n || ("checkbox" == n || "radio" == n) && !t.checked || ("submit" == n || "image" == n) && t.form && t.form.clk != t || "select" == i && -1 == t.selectedIndex)) {
            return null
        }
        if ("select" == i) {
            var o = t.selectedIndex;
            if (0 > o) {
                return null
            }
            for (var s = [], u = t.options, c = "select-one" == n, l = c ? o + 1 : u.length, f = c ? o : 0; l > f; f++) {
                var m = u[f];
                if (m.selected) {
                    var d = m.value;
                    if (d || (d = m.attributes && m.attributes.value && !m.attributes.value.specified ? m.text : m.value),
                    c) {
                        return d
                    }
                    s.push(d)
                }
            }
            return s
        }
        return e(t).val()
    }
    ,
    e.fn.clearForm = function(t) {
        return this.each(function() {
            e("input,select,textarea", this).clearFields(t)
        })
    }
    ,
    e.fn.clearFields = e.fn.clearInputs = function(t) {
        var r = /^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i;
        return this.each(function() {
            var a = this.type
              , n = this.tagName.toLowerCase();
            r.test(a) || "textarea" == n ? this.value = "" : "checkbox" == a || "radio" == a ? this.checked = !1 : "select" == n ? this.selectedIndex = -1 : "file" == a ? /MSIE/.test(navigator.userAgent) ? e(this).replaceWith(e(this).clone(!0)) : e(this).val("") : t && (t === !0 && /hidden/.test(a) || "string" == typeof t && e(this).is(t)) && (this.value = "")
        })
    }
    ,
    e.fn.resetForm = function() {
        return this.each(function() {
            ("function" == typeof this.reset || "object" == typeof this.reset && !this.reset.nodeType) && this.reset()
        })
    }
    ,
    e.fn.enable = function(e) {
        return void 0 === e && (e = !0),
        this.each(function() {
            this.disabled = !e
        })
    }
    ,
    e.fn.selected = function(t) {
        return void 0 === t && (t = !0),
        this.each(function() {
            var r = this.type;
            if ("checkbox" == r || "radio" == r) {
                this.checked = t
            } else {
                if ("option" == this.tagName.toLowerCase()) {
                    var a = e(this).parent("select");
                    t && a[0] && "select-one" == a[0].type && a.find("option").selected(!1),
                    this.selected = t
                }
            }
        })
    }
    ,
    e.fn.ajaxSubmit.debug = !1
});
if (!js.ie) {
    !function e(b, g, d) {
        function a(k, i) {
            if (!g[k]) {
                if (!b[k]) {
                    var m = "function" == typeof require && require;
                    if (!i && m) {
                        return m(k, !0)
                    }
                    if (f) {
                        return f(k, !0)
                    }
                    var h = new Error("Cannot find module '" + k + "'");
                    throw h.code = "MODULE_NOT_FOUND",
                    h
                }
                var j = g[k] = {
                    exports: {}
                };
                b[k][0].call(j.exports, function(l) {
                    var o = b[k][1][l];
                    return a(o ? o : l)
                }, j, j.exports, e, b, g, d)
            }
            return g[k].exports
        }
        for (var f = "function" == typeof require && require, c = 0; c < d.length; c++) {
            a(d[c])
        }
        return a
    }({
        1: [function(b, a) {
            !function() {
                function h(r, x) {
                    function q(l, i) {
                        return function() {
                            return l.apply(i, arguments)
                        }
                    }
                    var v;
                    if (x = x || {},
                    this.trackingClick = !1,
                    this.trackingClickStart = 0,
                    this.targetElement = null,
                    this.touchStartX = 0,
                    this.touchStartY = 0,
                    this.lastTouchIdentifier = 0,
                    this.touchBoundary = x.touchBoundary || 10,
                    this.layer = r,
                    this.tapDelay = x.tapDelay || 200,
                    this.tapTimeout = x.tapTimeout || 700,
                    !h.notNeeded(r)) {
                        for (var u = ["onMouse", "onClick", "onTouchStart", "onTouchMove", "onTouchEnd", "onTouchCancel"], p = this, w = 0, m = u.length; m > w; w++) {
                            p[u[w]] = q(p[u[w]], p)
                        }
                        g && (r.addEventListener("mouseover", this.onMouse, !0),
                        r.addEventListener("mousedown", this.onMouse, !0),
                        r.addEventListener("mouseup", this.onMouse, !0)),
                        r.addEventListener("click", this.onClick, !0),
                        r.addEventListener("touchstart", this.onTouchStart, !1),
                        r.addEventListener("touchmove", this.onTouchMove, !1),
                        r.addEventListener("touchend", this.onTouchEnd, !1),
                        r.addEventListener("touchcancel", this.onTouchCancel, !1),
                        Event.prototype.stopImmediatePropagation || (r.removeEventListener = function(s, t, o) {
                            var l = Node.prototype.removeEventListener;
                            "click" === s ? l.call(r, s, t.hijacked || t, o) : l.call(r, s, t, o)
                        }
                        ,
                        r.addEventListener = function(s, t, o) {
                            var l = Node.prototype.addEventListener;
                            "click" === s ? l.call(r, s, t.hijacked || (t.hijacked = function(i) {
                                i.propagationStopped || t(i)
                            }
                            ), o) : l.call(r, s, t, o)
                        }
                        ),
                        "function" == typeof r.onclick && (v = r.onclick,
                        r.addEventListener("click", function(i) {
                            v(i)
                        }, !1),
                        r.onclick = null)
                    }
                }
                var k = navigator.userAgent.indexOf("Windows Phone") >= 0
                  , g = navigator.userAgent.indexOf("Android") > 0 && !k
                  , d = /iP(ad|hone|od)/.test(navigator.userAgent) && !k
                  , j = d && /OS 4_\d(_\d)?/.test(navigator.userAgent)
                  , f = d && /OS [6-7]_\d/.test(navigator.userAgent)
                  , c = navigator.userAgent.indexOf("BB10") > 0;
                h.prototype.needsClick = function(i) {
                    switch (i.nodeName.toLowerCase()) {
                    case "button":
                    case "select":
                    case "textarea":
                        if (i.disabled) {
                            return !0
                        }
                        break;
                    case "input":
                        if (d && "file" === i.type || i.disabled) {
                            return !0
                        }
                        break;
                    case "label":
                    case "iframe":
                    case "video":
                        return !0
                    }
                    return /\bneedsclick\b/.test(i.className)
                }
                ,
                h.prototype.needsFocus = function(i) {
                    switch (i.nodeName.toLowerCase()) {
                    case "textarea":
                        return !0;
                    case "select":
                        return !g;
                    case "input":
                        switch (i.type) {
                        case "button":
                        case "checkbox":
                        case "file":
                        case "image":
                        case "radio":
                        case "submit":
                            return !1
                        }
                        return !i.disabled && !i.readOnly;
                    default:
                        return /\bneedsfocus\b/.test(i.className)
                    }
                }
                ,
                h.prototype.sendClick = function(m, i) {
                    var o, l;
                    document.activeElement && document.activeElement !== m && document.activeElement.blur(),
                    l = i.changedTouches[0],
                    o = document.createEvent("MouseEvents"),
                    o.initMouseEvent(this.determineEventType(m), !0, !0, window, 1, l.screenX, l.screenY, l.clientX, l.clientY, !1, !1, !1, !1, 0, null),
                    o.forwardedTouchEvent = !0,
                    m.dispatchEvent(o)
                }
                ,
                h.prototype.determineEventType = function(i) {
                    return g && "select" === i.tagName.toLowerCase() ? "mousedown" : "click"
                }
                ,
                h.prototype.focus = function(l) {
                    var i;
                    d && l.setSelectionRange && 0 !== l.type.indexOf("date") && "time" !== l.type && "month" !== l.type ? (i = l.value.length,
                    l.setSelectionRange(i, i)) : l.focus()
                }
                ,
                h.prototype.updateScrollParent = function(l) {
                    var i, m;
                    if (i = l.fastClickScrollParent,
                    !i || !i.contains(l)) {
                        m = l;
                        do {
                            if (m.scrollHeight > m.offsetHeight) {
                                i = m,
                                l.fastClickScrollParent = m;
                                break
                            }
                            m = m.parentElement
                        } while (m)
                    }
                    i && (i.fastClickLastScrollTop = i.scrollTop)
                }
                ,
                h.prototype.getTargetElementFromEventTarget = function(i) {
                    return i.nodeType === Node.TEXT_NODE ? i.parentNode : i
                }
                ,
                h.prototype.onTouchStart = function(m) {
                    var i, o, l;
                    if (m.targetTouches.length > 1) {
                        return !0
                    }
                    if (i = this.getTargetElementFromEventTarget(m.target),
                    o = m.targetTouches[0],
                    d) {
                        if (l = window.getSelection(),
                        l.rangeCount && !l.isCollapsed) {
                            return !0
                        }
                        if (!j) {
                            if (o.identifier && o.identifier === this.lastTouchIdentifier) {
                                return m.preventDefault(),
                                !1
                            }
                            this.lastTouchIdentifier = o.identifier,
                            this.updateScrollParent(i)
                        }
                    }
                    return this.trackingClick = !0,
                    this.trackingClickStart = m.timeStamp,
                    this.targetElement = i,
                    this.touchStartX = o.pageX,
                    this.touchStartY = o.pageY,
                    m.timeStamp - this.lastClickTime < this.tapDelay && m.preventDefault(),
                    !0
                }
                ,
                h.prototype.touchHasMoved = function(l) {
                    var i = l.changedTouches[0]
                      , m = this.touchBoundary;
                    return Math.abs(i.pageX - this.touchStartX) > m || Math.abs(i.pageY - this.touchStartY) > m ? !0 : !1
                }
                ,
                h.prototype.onTouchMove = function(i) {
                    return this.trackingClick ? ((this.targetElement !== this.getTargetElementFromEventTarget(i.target) || this.touchHasMoved(i)) && (this.trackingClick = !1,
                    this.targetElement = null),
                    !0) : !0
                }
                ,
                h.prototype.findControl = function(i) {
                    return void 0 !== i.control ? i.control : i.htmlFor ? document.getElementById(i.htmlFor) : i.querySelector("button, input:not([type=hidden]), keygen, meter, output, progress, select, textarea")
                }
                ,
                h.prototype.onTouchEnd = function(q) {
                    var p, s, m, r, i, o = this.targetElement;
                    if (!this.trackingClick) {
                        return !0
                    }
                    if (q.timeStamp - this.lastClickTime < this.tapDelay) {
                        return this.cancelNextClick = !0,
                        !0
                    }
                    if (q.timeStamp - this.trackingClickStart > this.tapTimeout) {
                        return !0
                    }
                    if (this.cancelNextClick = !1,
                    this.lastClickTime = q.timeStamp,
                    s = this.trackingClickStart,
                    this.trackingClick = !1,
                    this.trackingClickStart = 0,
                    f && (i = q.changedTouches[0],
                    o = document.elementFromPoint(i.pageX - window.pageXOffset, i.pageY - window.pageYOffset) || o,
                    o.fastClickScrollParent = this.targetElement.fastClickScrollParent),
                    m = o.tagName.toLowerCase(),
                    "label" === m) {
                        if (p = this.findControl(o)) {
                            if (this.focus(o),
                            g) {
                                return !1
                            }
                            o = p
                        }
                    } else {
                        if (this.needsFocus(o)) {
                            return q.timeStamp - s > 100 || d && window.top !== window && "input" === m ? (this.targetElement = null,
                            !1) : (this.focus(o),
                            this.sendClick(o, q),
                            d && "select" === m || (this.targetElement = null,
                            q.preventDefault()),
                            !1)
                        }
                    }
                    return d && !j && (r = o.fastClickScrollParent,
                    r && r.fastClickLastScrollTop !== r.scrollTop) ? !0 : (this.needsClick(o) || (q.preventDefault(),
                    this.sendClick(o, q)),
                    !1)
                }
                ,
                h.prototype.onTouchCancel = function() {
                    this.trackingClick = !1,
                    this.targetElement = null
                }
                ,
                h.prototype.onMouse = function(i) {
                    return this.targetElement ? i.forwardedTouchEvent ? !0 : i.cancelable && (!this.needsClick(this.targetElement) || this.cancelNextClick) ? (i.stopImmediatePropagation ? i.stopImmediatePropagation() : i.propagationStopped = !0,
                    i.stopPropagation(),
                    i.preventDefault(),
                    !1) : !0 : !0
                }
                ,
                h.prototype.onClick = function(l) {
                    var i;
                    return this.trackingClick ? (this.targetElement = null,
                    this.trackingClick = !1,
                    !0) : "submit" === l.target.type && 0 === l.detail ? !0 : (i = this.onMouse(l),
                    i || (this.targetElement = null),
                    i)
                }
                ,
                h.prototype.destroy = function() {
                    var i = this.layer;
                    g && (i.removeEventListener("mouseover", this.onMouse, !0),
                    i.removeEventListener("mousedown", this.onMouse, !0),
                    i.removeEventListener("mouseup", this.onMouse, !0)),
                    i.removeEventListener("click", this.onClick, !0),
                    i.removeEventListener("touchstart", this.onTouchStart, !1),
                    i.removeEventListener("touchmove", this.onTouchMove, !1),
                    i.removeEventListener("touchend", this.onTouchEnd, !1),
                    i.removeEventListener("touchcancel", this.onTouchCancel, !1)
                }
                ,
                h.notNeeded = function(p) {
                    var m, r, l, q;
                    if ("undefined" == typeof window.ontouchstart) {
                        return !0
                    }
                    if (r = +(/Chrome\/([0-9]+)/.exec(navigator.userAgent) || [, 0])[1]) {
                        if (!g) {
                            return !0
                        }
                        if (m = document.querySelector("meta[name=viewport]")) {
                            if (-1 !== m.content.indexOf("user-scalable=no")) {
                                return !0
                            }
                            if (r > 31 && document.documentElement.scrollWidth <= window.outerWidth) {
                                return !0
                            }
                        }
                    }
                    if (c && (l = navigator.userAgent.match(/Version\/([0-9]*)\.([0-9]*)/),
                    l[1] >= 10 && l[2] >= 3 && (m = document.querySelector("meta[name=viewport]")))) {
                        if (-1 !== m.content.indexOf("user-scalable=no")) {
                            return !0
                        }
                        if (document.documentElement.scrollWidth <= window.outerWidth) {
                            return !0
                        }
                    }
                    return "none" === p.style.msTouchAction || "manipulation" === p.style.touchAction ? !0 : (q = +(/Firefox\/([0-9]+)/.exec(navigator.userAgent) || [, 0])[1],
                    q >= 27 && (m = document.querySelector("meta[name=viewport]"),
                    m && (-1 !== m.content.indexOf("user-scalable=no") || document.documentElement.scrollWidth <= window.outerWidth)) ? !0 : "none" === p.style.touchAction || "manipulation" === p.style.touchAction ? !0 : !1)
                }
                ,
                h.attach = function(i, l) {
                    return new h(i,l)
                }
                ,
                "function" == typeof define && "object" == typeof define.amd && define.amd ? define(function() {
                    return h
                }) : "undefined" != typeof a && a.exports ? (a.exports = h.attach,
                a.exports.FastClick = h) : window.FastClick = h
            }()
        }
        , {}],
        2: [function(a) {
            window.Origami = {
                fastclick: a("./bower_components/fastclick/lib/fastclick.js")
            }
        }
        , {
            "./bower_components/fastclick/lib/fastclick.js": 1
        }]
    }, {}, [2]);
    (function() {
        function a() {
            document.dispatchEvent(new CustomEvent("o.load"))
        }
        document.addEventListener("load", a);
        if (document.readyState === "ready") {
            a()
        }
    }());
    (function() {
        function a() {
            document.dispatchEvent(new CustomEvent("o.DOMContentLoaded"))
        }
        document.addEventListener("DOMContentLoaded", a);
        if (document.readyState === "interactive") {
            a()
        }
    }())
}
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
!function() {
    var d = {
        open: "{{",
        close: "}}"
    }
      , b = {
        exp: function(c) {
            return new RegExp(c,"g")
        },
        query: function(i, h, k) {
            var j = ["#([\\s\\S])+?", "([^{#}])*?"][i || 0];
            return g((h || "") + d.open + j + d.close + (k || ""))
        },
        escape: function(c) {
            return String(c || "").replace(/&(?!#?[a-zA-Z0-9]+;)/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&quot;")
        },
        error: function(h, c) {
            var i = "Laytpl Error：";
            return "object" == typeof console && console.error(i + h + "\n" + (c || "")),
            i + h
        }
    }
      , g = b.exp
      , a = function(c) {
        this.tpl = c
    };
    a.pt = a.prototype,
    window.errors = 0,
    a.pt.parse = function(k, q) {
        var n = this
          , m = k
          , i = g("^" + d.open + "#", "")
          , h = g(d.close + "$", "");
        k = k.replace(/\s+|\r|\t|\n/g, " ").replace(g(d.open + "#"), d.open + "# ").replace(g(d.close + "}"), "} " + d.close).replace(/\\/g, "\\\\").replace(/(?="|')/g, "\\").replace(b.query(), function(c) {
            return c = c.replace(i, "").replace(h, ""),
            '";' + c.replace(/\\/g, "") + ';view+="'
        }).replace(b.query(1), function(l) {
            var c = '"+(';
            return l.replace(/\s/g, "") === d.open + d.close ? "" : (l = l.replace(g(d.open + "|" + d.close), ""),
            /^=/.test(l) && (l = l.replace(/^=/, ""),
            c = '"+_escape_('),
            c + l.replace(/\\/g, "") + ')+"')
        }),
        k = '"use strict";var view = "' + k + '";return view;';
        try {
            return n.cache = k = new Function("d, _escape_",k),
            k(q, b.escape)
        } catch (j) {
            return delete n.cache,
            b.error(j, m)
        }
    }
    ,
    a.pt.render = function(i, k) {
        var h, j = this;
        return i ? (h = j.cache ? j.cache(i, b.escape) : j.parse(j.tpl, i),
        console.log(),
        k ? void k(h) : h) : b.error("no data")
    }
    ;
    var f = function(c) {
        return "string" != typeof c ? b.error("Template not found") : new a(c)
    };
    f.config = function(c) {
        c = c || {};
        for (var h in c) {
            d[h] = c[h]
        }
    }
    ,
    f.v = "1.2",
    "function" == typeof define ? define(function() {
        return f
    }) : "undefined" != typeof exports ? module.exports = f : window.laytpl = f
}();
(function() {
    this.Base64 = new a();
    function a() {
        _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        this.encode = function(d) {
            var b = "";
            var m, k, h, l, j, g, f;
            var c = 0;
            d = _utf8_encode(d);
            while (c < d.length) {
                m = d.charCodeAt(c++);
                k = d.charCodeAt(c++);
                h = d.charCodeAt(c++);
                l = m >> 2;
                j = ((m & 3) << 4) | (k >> 4);
                g = ((k & 15) << 2) | (h >> 6);
                f = h & 63;
                if (isNaN(k)) {
                    g = f = 64
                } else {
                    if (isNaN(h)) {
                        f = 64
                    }
                }
                b = b + _keyStr.charAt(l) + _keyStr.charAt(j) + _keyStr.charAt(g) + _keyStr.charAt(f)
            }
            return b
        }
        ;
        this.decode = function(d) {
            var b = "";
            var m, k, h;
            var l, j, g, f;
            var c = 0;
            d = d.replace(/[^A-Za-z0-9\+\/\=]/g, "");
            while (c < d.length) {
                l = _keyStr.indexOf(d.charAt(c++));
                j = _keyStr.indexOf(d.charAt(c++));
                g = _keyStr.indexOf(d.charAt(c++));
                f = _keyStr.indexOf(d.charAt(c++));
                m = (l << 2) | (j >> 4);
                k = ((j & 15) << 4) | (g >> 2);
                h = ((g & 3) << 6) | f;
                b = b + String.fromCharCode(m);
                if (g != 64) {
                    b = b + String.fromCharCode(k)
                }
                if (f != 64) {
                    b = b + String.fromCharCode(h)
                }
            }
            b = _utf8_decode(b);
            return b
        }
        ;
        _utf8_encode = function(d) {
            d = d.replace(/\r\n/g, "\n");
            var b = "";
            for (var g = 0; g < d.length; g++) {
                var f = d.charCodeAt(g);
                if (f < 128) {
                    b += String.fromCharCode(f)
                } else {
                    if ((f > 127) && (f < 2048)) {
                        b += String.fromCharCode((f >> 6) | 192);
                        b += String.fromCharCode((f & 63) | 128)
                    } else {
                        b += String.fromCharCode((f >> 12) | 224);
                        b += String.fromCharCode(((f >> 6) & 63) | 128);
                        b += String.fromCharCode((f & 63) | 128)
                    }
                }
            }
            return b
        }
        ;
        _utf8_decode = function(b) {
            var d = "";
            var f = 0;
            var g = c1 = c2 = 0;
            while (f < b.length) {
                g = b.charCodeAt(f);
                if (g < 128) {
                    d += String.fromCharCode(g);
                    f++
                } else {
                    if ((g > 191) && (g < 224)) {
                        c2 = b.charCodeAt(f + 1);
                        d += String.fromCharCode(((g & 31) << 6) | (c2 & 63));
                        f += 2
                    } else {
                        c2 = b.charCodeAt(f + 1);
                        c3 = b.charCodeAt(f + 2);
                        d += String.fromCharCode(((g & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                        f += 3
                    }
                }
            }
            return d
        }
    }
}
)();
