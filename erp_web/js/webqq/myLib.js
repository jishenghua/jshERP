/*-----------------------------------------------------------------------------
*作者:Muzi*lei , email:530624206@qq.com
*http://www.muzilei.com/
*version:1.0  , 时间：2011-09-14
-----------------------------------------------------------------------------*/
var myLib={
	//创建子命名空间,用的是yui的方法------
	NS:function(ns) {
       if (!ns || !ns.length) {
        return null;
       }
       var levels = ns.split(".");
       var nsobj = myLib;
      for (var i=(levels[0] == "myLib") ? 1 : 0; i<levels.length; ++i) {
        nsobj[levels[i]] = nsobj[levels[i]] || {};
        nsobj = nsobj[levels[i]];
      }
      return nsobj;
      },
 	 //获取对象类型名,["Array", "Boolean", "Date", "Number", "Object", "RegExp", "String", "Window", "HTMLDocument"]
	_getType:function(object){
		return Object.prototype.toString.call(object).match(/^\[object\s(.*)\]$/)[1];
		},
	//用来判断对象类型
	_is:function(object,typeStr){
		return this._getType(object)==typeStr;
		},
	textLength:function(text){
		var intLength=0;
		for (var i=0;i<text.length;i++){
			if ((text.charCodeAt(i) < 0) || (text.charCodeAt(i) > 255)){
				intLength=intLength+2;
			}else{
				intLength=intLength+1;
				}
		 }
		 return intLength
		 },	
	//加载进度条
	progressBar:function(){
		 $("<div id='myCover'></div><div id='loadimg'><span>正在加载,请稍等O(∩_∩)O哈！</span></div>").appendTo('body');
		 var w=$(window).width(),h=$(window).height();
		 $('#myCover').css({'width':'100%','height':h,'position':'absolute','background':'#fff','z-index':9999,'left':0,'top':0}).fadeTo('slow',0.8);
		 $('#loadimg').css({'position':'absolute','background':'url(themes/default/images/loading.gif) no-repeat center center','z-index':10000,'width':'110px','height':'64px','left':(w-110)/2,'top':((h-64)/2)-50}).find('span').css({'position':'absolute','left':0,'bottom':'-40px','width':110,'display':'block','height':40,'text-align':'center'});
		},
	//停止进度条
	stopProgress:function(){
		$('#myCover').remove();
		$('#loadimg').remove();
		},
	getImgWh:function(url, callback) {
        var width, height, intervalId, check, div, img = new Image(),
            body = document.body;
        img.src = url;

        //从缓存中读取
        if (img.complete) {
          return callback(img.width, img.height);
        };

        //通过占位提前获取图片头部数据
        if (body) {
          div = document.createElement('div');
          div.style.cssText = 'visibility:hidden;position:absolute;left:0;top:0;width:1px;height:1px;overflow:hidden';
          div.appendChild(img)
          body.appendChild(div);
          width = img.offsetWidth;
          height = img.offsetHeight;
          check = function() {
            if (img.offsetWidth !== width || img.offsetHeight !== height) {
              clearInterval(intervalId);
              callback(img.offsetWidth, img.clientHeight);
              img.onload = null;
              div.innerHTML = '';
              div.parentNode.removeChild(div);
            };
          };
          intervalId = setInterval(check, 150);
        };
        // 加载完毕后方式获取
        img.onload = function() {
          callback(img.width, img.height);
          img.onload = img.onerror = null;
          clearInterval(intervalId);
          body && img.parentNode.removeChild(img);
        };
      },
	//全屏
	fullscreen:function(){
		 var docElm = document.documentElement;
             if (docElm.requestFullscreen) {
                 docElm.requestFullscreen();
               }
             else if (docElm.mozRequestFullScreen) {
             docElm.mozRequestFullScreen();
               }
             else if (docElm.webkitRequestFullScreen) {
             docElm.webkitRequestFullScreen();
                    }
		},
	//退出全屏
	exitFullscreen:function(){
		if (document.exitFullscreen) {
                document.exitFullscreen();
				}
				else if (document.mozCancelFullScreen) {
					document.mozCancelFullScreen();
					}
					else if (document.webkitCancelFullScreen) {
						document.webkitCancelFullScreen();
						}
		},
	//IE全屏
	fullscreenIE:function(){
		if($.browser.msie){
						var  wsh =  new  ActiveXObject("WScript.Shell");  
						wsh.sendKeys("{F11}");
					}
		}
	}
	
/*------------------------------------------	
 *jquery扩展，加载技术文件和css文件
-------------------------------------------*/
$.extend({
    includePath: '',
    include: function(file)
    {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++)
        {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});