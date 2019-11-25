	//baidu-tongji
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?1cd9bcbaae133f03a6eb19da6579aaba";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();

	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	$(function() {
		domresize();
	});
	//========================页面高度自动调节================================
	var heightInfo;
	var widthInfo;
	var initPageSize;
	var initPageNum;
	var webH;
	var webW;
	//改变表格宽高
	function domresize()
	{
		webH = document.documentElement.clientHeight;
		webW = document.documentElement.offsetWidth;
		widthInfo = $("body").outerWidth() -27;
		var mtopH = $("#searchTable").outerHeight();
		var positionH = $("#position").outerHeight();
		heightInfo = webH - mtopH - 86;

		//分页信息修改成 15条
		if(heightInfo > 450)
		{
			initPageSize = 15;
			initPageNum = [15,30,50];
		}
		else
		{
			initPageSize = 10;
			initPageNum = [10,20,30,50];
		}
	}
    function dgResize() {
		var searchTabHeight = $('#searchTable').height();
		if($('#tableData').length) {
            $('#tableData').datagrid('resize', {
                width: $(window).width() - 6,
                height: $(window).height() - searchTabHeight -46
            });
		}
    }
    $(window).resize(function () {
        dgResize();
    });
	//========================页面高度自动调节================================

	//判断浏览器的类型
	function getOs()
	{
		if(navigator.userAgent.indexOf("MSIE")>0)
		{
			return "MSIE";
		}
		else if(isFirefox=navigator.userAgent.indexOf("Firefox")>0)
		{
			return "Firefox";
		}
		else if(isSafari=navigator.userAgent.indexOf("Safari")>0)
		{
			return "Safari";
		}
		else if(isCamino=navigator.userAgent.indexOf("Camino")>0)
		{
			return "Camino";
		}
		else if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0)
		{
			return "Gecko";
		}
	}

    /**
	 * 获取公司信息
     */
	function getSystemConfig() {
		var info = null;
        $.ajax({
            type:"get",
            url: "/systemConfig/list",
            dataType: "json",
            data: ({
                currentPage: 1,
                pageSize: 100
            }),
            async: false,
            success: function (res) {
                if (res && res.code === 200) {
                    if(res.data && res.data.page) {
                        var array = res.data.page.rows;
                        if(array.length > 0) {
                            info = array[0];
                        }
                    }
                }
            },
            //此处添加错误处理
            error:function() {
                $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
                return;
            }
        });
        return info;
    }

	/**
	 * js生成唯一ID值 32位值随机值
	 * @returns 生成的字符串
	 */
	function uuid()
	{
		var s = [];
		var hexDigits = "0123456789abcdef";
		for (var i = 0; i < 36; i++)
			s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
		s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
		s[8] = s[13] = s[18] = s[23] = "";
		return s.join("");
	}
	/**
	 * js获取浏览器的地址参数
	 * @param name 地址参数
	 * @return
	 */
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r != null) return unescape(r[2]); return null; //返回参数值
	}

	/**
	 * 获取按钮的权限
	 */
	function getBtnStr() {
		var funId = window.parent.window.funId; //功能id
		var btnStrList = window.parent.window.winBtnStrList; //按钮功能列表 JSON字符串
		var btnEnableList =""; //按钮列表
		if(funId && btnStrList) {
			btnStrList = JSON.parse(btnStrList);
			for(var i=0; i<btnStrList.length; i++){
				if(btnStrList[i].funId ==funId){
					if(btnStrList[i].btnStr) {
						btnEnableList = btnEnableList + btnStrList[i].btnStr + ",";
					}
				}
			}
			if(btnEnableList) {
				btnEnableList = btnEnableList.substring(0,btnEnableList.length-1);
			}
		}
		return btnEnableList;
	}

	/**
	 * js获取当前时间， 格式“yyyy-MM-dd HH:MM:SS”
	 */
	function getNowFormatDateTime() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		var strHours = date.getHours();
		var strMinutes = date.getMinutes();
		var strSeconds = date.getSeconds();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		if (strHours >= 0 && strHours <= 9) {
			strHours = "0" + strHours;
		}
		if (strMinutes >= 0 && strMinutes <= 9) {
			strMinutes = "0" + strMinutes;
		}
		if (strSeconds >= 0 && strSeconds <= 9) {
			strSeconds = "0" + strSeconds;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
			+ " " + strHours + seperator2 + strMinutes
			+ seperator2 + strSeconds;
		return currentdate;
	}

	/**
	 * js获取当前时间， 格式“yyyy-MM”
	 */
	function getNowFormatMonth() {
		var date = new Date();
		var seperator1 = "-";
		var month = date.getMonth() + 1;
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		var currentdate = date.getFullYear() + seperator1 + month;
		return currentdate;
	}

	/**
	 * js获取当前时间， 格式“yyyy-MM-dd”
	 */
	function getNowFormatDate() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
		return currentdate;
	}

	/**
	 * js获取当前时间， 格式“yyyyMMdd”
	 */
	function getNowFormatDateTwo() {
		var date = new Date();
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + month.toString() + strDate.toString();
		return currentdate;
	}

	/**
	 * js根据时间生成编号， 格式“yyyyMMddHHMMSS”
	 */
	function getNowFormatDateNum() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		var strHours = date.getHours();
		var strMinutes = date.getMinutes();
		var strSeconds = date.getSeconds();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		if (strHours >= 0 && strHours <= 9) {
			strHours = "0" + strHours;
		}
		if (strMinutes >= 0 && strMinutes <= 9) {
			strMinutes = "0" + strMinutes;
		}
		if (strSeconds >= 0 && strSeconds <= 9) {
			strSeconds = "0" + strSeconds;
		}
		var currentdate = date.getFullYear() + month.toString() + strDate.toString() + strHours
			+ strMinutes + strSeconds;
		return currentdate;
	}

	function encode(name){
		return encodeURI(encodeURI(name));
	}

	/**
	 * 将数组单个金额中的数值转为正数
	 * @param arr
	 * @returns {Array}
	 */
	function changeListFmtPlus(arr) {
		var newArr = new Array();
		for(var i=0; i<arr.length; i++) {
			if(arr[i] < 0){
				newArr.push((0 - arr[i]).toString());
			}
			else {
				newArr.push((arr[i]-0).toString());
			}
		}
		return newArr;
	}

	/**
	 * 将数组单个金额中的数值转为负数
	 * @param arr
	 * @returns {Array}
	 */
	function changeListFmtMinus(arr) {
		var newArr = new Array();
		for(var i=0; i<arr.length; i++) {
			if(arr[i] < 0){
				newArr.push((arr[i]-0).toString());
			}
			else {
				newArr.push((0 - arr[i]).toString());
			}
		}
		return newArr;
	}

    /**
	 * 验证单据
     * @param id
     */
    function validateForm(id) {
        if(!$('#'+ id).form('validate')) {
        	return true;
		} else {
        	return false;
		}
	}

    function turnBillDetailPage(number, type) {
        js.addTabPage(null, "单据明细", "/pages/materials/bill_detail.html?n="+ number + "&type=" + type);
    }

    /**
	 * 验证手机号码
     * @param phoneInput
     * @returns {boolean}
     */
    function isPhoneAvailable(phoneInput) {
        var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
        if (!myreg.test(phoneInput.val())) {
            return false;
        } else {
            return true;
        }
    }

    /**
	 * 检查当前用户是否是管理员
     */
    function checkPower() {
    	var res = false;
        var loginName = sessionStorage.getItem("loginName");
        if(loginName == "admin") {
            $.messager.alert('提示','管理员不能增加表单数据！','warning');
            res = true;
        } else {
            res = false;
		}
		return res;
	}