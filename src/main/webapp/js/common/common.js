$(function()
{
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
