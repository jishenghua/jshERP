/*!
 * 消息推送实现
 * @author jishenghua
 */
$(function () {
    // unreadMsg()
});

// function unreadMsg() {
//     $.get(ctx + "/msg/unreadMsg?__notUpdateSession=true&__t=" + new Date().getTime(), function (d) {
//         var b = $("#msgList").empty();
//         var a = d.count || 0, c = d.list || [];
//         for (i = 0; i < c.length; i++) {
//             b.append(js.template("msgListTpl", c[i]))
//         }
//         $("#msgNum, #msgNum2").text(a);
//         $(".timeago").timeago();
//         pullPoolMsg();
//         if (window.ppmInt) {
//             clearInterval(window.ppmInt)
//         }
//         window.ppmInt = setInterval(pullPoolMsg, 60 * 1000)
//     })
// }
//
// function pullPoolMsg() {
//     var a = $("#msgList");
//     var b = a.attr("data-mergeMsgLimit");
//     $.get(ctx + "/msg/pullPoolMsg?__notUpdateSession=true&__t=" + new Date().getTime(), function (e) {
//         for (i = 0; i < e.length; i++) {
//             if (!(e.length > b)) {
//                 var g = js.template("msgTipTpl", e[i]);
//                 js.showMessage(g, e[i].msgContentEntity.title, "info", 1000 * 60);
//                 doFlashTitle()
//             }
//             if (e[i].id && e[i].id != "") {
//                 a.prepend(js.template("msgListTpl", e[i]))
//             }
//         }
//         if (e.length > b) {
//             var f = {
//                 msgContentEntity: {
//                     title: a.attr("data-mergeMsgTitle"),
//                     content: js.text(a.attr("data-mergeMsgContent"), e.length)
//                 }, sendDate: "", sendUserName: "", id: ""
//             };
//             var g = js.template("msgTipTpl", f);
//             js.showMessage(g, null, "info", 1000 * 60);
//             doFlashTitle()
//         }
//         var c = parseInt($("#msgNum").text());
//         if (!isNaN(c)) {
//             c += e.length || 0
//         } else {
//             c = a.find("li").length
//         }
//         $("#msgNum, #msgNum2").text(c);
//         $(".timeago").timeago()
//     })
// }

function readMsg(c, d, e) {
    var a = $(c).data("href");
    if (e == "") {
        a = ctx + "/msg/list"
    }
    var b = js.addTabPage($(c), d, a);
    if (b) {
        $("#" + b + "-frame").on("load", function () {
            setTimeout(unreadMsg, 1000)
        })
    }
}

var isWindowFocus = true;
if ("onfocusin" in document) {
    document.onfocusin = function () {
        isWindowFocus = true
    };
    document.onfocusout = function () {
        isWindowFocus = false
    }
} else {
    window.onfocus = function () {
        isWindowFocus = true
    };
    window.onblur = function () {
        isWindowFocus = false
    }
}
var flashStep = 0;
var flashTitleRun = false;
var normalTitle = document.title;
var flashTitle = function () {
    if (isWindowFocus) {
        document.title = normalTitle;
        flashTitleRun = false;
        return
    }
    flashTitleRun = true;
    flashStep++;
    if (flashStep == 3) {
        flashStep = 1
    }
    if (flashStep == 1) {
        document.title = "【新消息】" + normalTitle
    }
    if (flashStep == 2) {
        document.title = "【　　　】" + normalTitle
    }
    setTimeout("flashTitle()", 500)
};

function doFlashTitle() {
    if (!flashTitleRun) {
        flashTitle()
    }
    var a = document.getElementById("audioMessage");
    if (a) {
        a.play()
    }
};