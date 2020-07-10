//判断是否存在session，如果不存在就跳到登录界面
$.ajax({
    type: "get",
    url: "/user/getUserSession",
    dataType: "json",
    async: false,
    success: function (res) {
        if(res && res.code === 200) {
            if(res.data.user) {
                var user = res.data.user;
                sessionStorage.setItem("userId", user.id);
                if (user.username) {
                    $(".main-header .user-menu .hidden-xs,.main-sidebar .info p").text(user.username);
                }
                if(user.id==user.tenantId) {
                    $("#newHand").show();
                } else {
                    $("#newHand").hide();
                }
            }
        } else {
            top.location.href = '/login.html';
        }
    },
    error: function () {
        top.location.href = '/login.html';
    }
});
