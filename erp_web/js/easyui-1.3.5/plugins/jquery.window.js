/**
 * jQuery EasyUI 1.3.5
 * 
 * Copyright (c) 2009-2013 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL or commercial licenses
 * To use it on other terms please contact us: info@jeasyui.com
 * http://www.gnu.org/licenses/gpl.txt
 * http://www.jeasyui.com/license_commercial.php
 *
 */
(function($){
function _1(_2,_3){
var _4=$.data(_2,"window").options;
if(_3){
$.extend(_4,_3);
}
$(_2).panel("resize",_4);
};
function _5(_6,_7){
var _8=$.data(_6,"window");
if(_7){
if(_7.left!=null){
_8.options.left=_7.left;
}
if(_7.top!=null){
_8.options.top=_7.top;
}
}
$(_6).panel("move",_8.options);
if(_8.shadow){
_8.shadow.css({left:_8.options.left,top:_8.options.top});
}
};
function _9(_a,_b){
var _c=$.data(_a,"window");
var _d=_c.options;
var _e=_d.width;
if(isNaN(_e)){
_e=_c.window._outerWidth();
}
if(_d.inline){
var _f=_c.window.parent();
_d.left=(_f.width()-_e)/2+_f.scrollLeft();
}else{
_d.left=($(window)._outerWidth()-_e)/2+$(document).scrollLeft();
}
if(_b){
_5(_a);
}
};
function _10(_11,_12){
var _13=$.data(_11,"window");
var _14=_13.options;
var _15=_14.height;
if(isNaN(_15)){
_15=_13.window._outerHeight();
}
if(_14.inline){
var _16=_13.window.parent();
_14.top=(_16.height()-_15)/2+_16.scrollTop();
}else{
_14.top=($(window)._outerHeight()-_15)/2+$(document).scrollTop();
}
if(_12){
_5(_11);
}
};
function _17(_18){
var _19=$.data(_18,"window");
var win=$(_18).panel($.extend({},_19.options,{border:false,doSize:true,closed:true,cls:"window",headerCls:"window-header",bodyCls:"window-body "+(_19.options.noheader?"window-body-noheader":""),onBeforeDestroy:function(){
if(_19.options.onBeforeDestroy.call(_18)==false){
return false;
}
if(_19.shadow){
_19.shadow.remove();
}
if(_19.mask){
_19.mask.remove();
}
},onClose:function(){
if(_19.shadow){
_19.shadow.hide();
}
if(_19.mask){
_19.mask.hide();
}
_19.options.onClose.call(_18);
},onOpen:function(){
if(_19.mask){
_19.mask.css({display:"block",zIndex:$.fn.window.defaults.zIndex++});
}
if(_19.shadow){
_19.shadow.css({display:"block",zIndex:$.fn.window.defaults.zIndex++,left:_19.options.left,top:_19.options.top,width:_19.window._outerWidth(),height:_19.window._outerHeight()});
}
_19.window.css("z-index",$.fn.window.defaults.zIndex++);
_19.options.onOpen.call(_18);
},onResize:function(_1a,_1b){
var _1c=$(this).panel("options");
$.extend(_19.options,{width:_1c.width,height:_1c.height,left:_1c.left,top:_1c.top});
if(_19.shadow){
_19.shadow.css({left:_19.options.left,top:_19.options.top,width:_19.window._outerWidth(),height:_19.window._outerHeight()});
}
_19.options.onResize.call(_18,_1a,_1b);
},onMinimize:function(){
if(_19.shadow){
_19.shadow.hide();
}
if(_19.mask){
_19.mask.hide();
}
_19.options.onMinimize.call(_18);
},onBeforeCollapse:function(){
if(_19.options.onBeforeCollapse.call(_18)==false){
return false;
}
if(_19.shadow){
_19.shadow.hide();
}
},onExpand:function(){
if(_19.shadow){
_19.shadow.show();
}
_19.options.onExpand.call(_18);
}}));
_19.window=win.panel("panel");
if(_19.mask){
_19.mask.remove();
}
if(_19.options.modal==true){
_19.mask=$("<div class=\"window-mask\"></div>").insertAfter(_19.window);
_19.mask.css({width:(_19.options.inline?_19.mask.parent().width():_1d().width),height:(_19.options.inline?_19.mask.parent().height():_1d().height),display:"none"});
}
if(_19.shadow){
_19.shadow.remove();
}
if(_19.options.shadow==true){
_19.shadow=$("<div class=\"window-shadow\"></div>").insertAfter(_19.window);
_19.shadow.css({display:"none"});
}
if(_19.options.left==null){
_9(_18);
}
if(_19.options.top==null){
_10(_18);
}
_5(_18);
if(_19.options.closed==false){
win.window("open");
}
};
function _1e(_1f){
var _20=$.data(_1f,"window");
_20.window.draggable({handle:">div.panel-header>div.panel-title",disabled:_20.options.draggable==false,onStartDrag:function(e){
if(_20.mask){
_20.mask.css("z-index",$.fn.window.defaults.zIndex++);
}
if(_20.shadow){
_20.shadow.css("z-index",$.fn.window.defaults.zIndex++);
}
_20.window.css("z-index",$.fn.window.defaults.zIndex++);
if(!_20.proxy){
_20.proxy=$("<div class=\"window-proxy\"></div>").insertAfter(_20.window);
}
_20.proxy.css({display:"none",zIndex:$.fn.window.defaults.zIndex++,left:e.data.left,top:e.data.top});
_20.proxy._outerWidth(_20.window._outerWidth());
_20.proxy._outerHeight(_20.window._outerHeight());
setTimeout(function(){
if(_20.proxy){
_20.proxy.show();
}
},500);
},onDrag:function(e){
_20.proxy.css({display:"block",left:e.data.left,top:e.data.top});
return false;
},onStopDrag:function(e){
_20.options.left=e.data.left;
_20.options.top=e.data.top;
$(_1f).window("move");
_20.proxy.remove();
_20.proxy=null;
}});
_20.window.resizable({disabled:_20.options.resizable==false,onStartResize:function(e){
_20.pmask=$("<div class=\"window-proxy-mask\"></div>").insertAfter(_20.window);
_20.pmask.css({zIndex:$.fn.window.defaults.zIndex++,left:e.data.left,top:e.data.top,width:_20.window._outerWidth(),height:_20.window._outerHeight()});
if(!_20.proxy){
_20.proxy=$("<div class=\"window-proxy\"></div>").insertAfter(_20.window);
}
_20.proxy.css({zIndex:$.fn.window.defaults.zIndex++,left:e.data.left,top:e.data.top});
_20.proxy._outerWidth(e.data.width);
_20.proxy._outerHeight(e.data.height);
},onResize:function(e){
_20.proxy.css({left:e.data.left,top:e.data.top});
_20.proxy._outerWidth(e.data.width);
_20.proxy._outerHeight(e.data.height);
return false;
},onStopResize:function(e){
$.extend(_20.options,{left:e.data.left,top:e.data.top,width:e.data.width,height:e.data.height});
_1(_1f);
_20.pmask.remove();
_20.pmask=null;
_20.proxy.remove();
_20.proxy=null;
}});
};
function _1d(){
if(document.compatMode=="BackCompat"){
return {width:Math.max(document.body.scrollWidth,document.body.clientWidth),height:Math.max(document.body.scrollHeight,document.body.clientHeight)};
}else{
return {width:Math.max(document.documentElement.scrollWidth,document.documentElement.clientWidth),height:Math.max(document.documentElement.scrollHeight,document.documentElement.clientHeight)};
}
};
$(window).resize(function(){
$("body>div.window-mask").css({width:$(window)._outerWidth(),height:$(window)._outerHeight()});
setTimeout(function(){
$("body>div.window-mask").css({width:_1d().width,height:_1d().height});
},50);
});
$.fn.window=function(_21,_22){
if(typeof _21=="string"){
var _23=$.fn.window.methods[_21];
if(_23){
return _23(this,_22);
}else{
return this.panel(_21,_22);
}
}
_21=_21||{};
return this.each(function(){
var _24=$.data(this,"window");
if(_24){
$.extend(_24.options,_21);
}else{
_24=$.data(this,"window",{options:$.extend({},$.fn.window.defaults,$.fn.window.parseOptions(this),_21)});
if(!_24.options.inline){
document.body.appendChild(this);
}
}
_17(this);
_1e(this);
});
};
$.fn.window.methods={options:function(jq){
var _25=jq.panel("options");
var _26=$.data(jq[0],"window").options;
return $.extend(_26,{closed:_25.closed,collapsed:_25.collapsed,minimized:_25.minimized,maximized:_25.maximized});
},window:function(jq){
return $.data(jq[0],"window").window;
},resize:function(jq,_27){
return jq.each(function(){
_1(this,_27);
});
},move:function(jq,_28){
return jq.each(function(){
_5(this,_28);
});
},hcenter:function(jq){
return jq.each(function(){
_9(this,true);
});
},vcenter:function(jq){
return jq.each(function(){
_10(this,true);
});
},center:function(jq){
return jq.each(function(){
_9(this);
_10(this);
_5(this);
});
}};
$.fn.window.parseOptions=function(_29){
return $.extend({},$.fn.panel.parseOptions(_29),$.parser.parseOptions(_29,[{draggable:"boolean",resizable:"boolean",shadow:"boolean",modal:"boolean",inline:"boolean"}]));
};
$.fn.window.defaults=$.extend({},$.fn.panel.defaults,{zIndex:9000,draggable:true,resizable:true,shadow:true,modal:false,inline:false,title:"New Window",collapsible:true,minimizable:true,maximizable:true,closable:true,closed:false});
})(jQuery);

