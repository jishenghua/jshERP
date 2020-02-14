/**
 * EasyUI for jQuery 1.9.4
 * 
 * Copyright (c) 2009-2020 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the freeware license: http://www.jeasyui.com/license_freeware.php
 * To use it on other terms please contact us: info@jeasyui.com
 *
 */
(function($){
function _1(e){
var _2=e.data;
var _3=$.data(_2.target,"resizable").options;
if(_2.dir.indexOf("e")!=-1){
var _4=_2.startWidth+e.pageX-_2.startX;
_4=Math.min(Math.max(_4,_3.minWidth),_3.maxWidth);
_2.width=_4;
}
if(_2.dir.indexOf("s")!=-1){
var _5=_2.startHeight+e.pageY-_2.startY;
_5=Math.min(Math.max(_5,_3.minHeight),_3.maxHeight);
_2.height=_5;
}
if(_2.dir.indexOf("w")!=-1){
var _4=_2.startWidth-e.pageX+_2.startX;
_4=Math.min(Math.max(_4,_3.minWidth),_3.maxWidth);
_2.width=_4;
_2.left=_2.startLeft+_2.startWidth-_2.width;
}
if(_2.dir.indexOf("n")!=-1){
var _5=_2.startHeight-e.pageY+_2.startY;
_5=Math.min(Math.max(_5,_3.minHeight),_3.maxHeight);
_2.height=_5;
_2.top=_2.startTop+_2.startHeight-_2.height;
}
};
function _6(e){
var _7=e.data;
var t=$(_7.target);
t.css({left:_7.left,top:_7.top});
if(t.outerWidth()!=_7.width){
t._outerWidth(_7.width);
}
if(t.outerHeight()!=_7.height){
t._outerHeight(_7.height);
}
};
function _8(e){
$.fn.resizable.isResizing=true;
$.data(e.data.target,"resizable").options.onStartResize.call(e.data.target,e);
return false;
};
function _9(e){
_1(e);
if($.data(e.data.target,"resizable").options.onResize.call(e.data.target,e)!=false){
_6(e);
}
return false;
};
function _a(e){
$.fn.resizable.isResizing=false;
_1(e,true);
_6(e);
$.data(e.data.target,"resizable").options.onStopResize.call(e.data.target,e);
$(document)._unbind(".resizable");
$("body").css("cursor","");
return false;
};
function _b(e){
var _c=$(e.data.target).resizable("options");
var tt=$(e.data.target);
var _d="";
var _e=tt.offset();
var _f=tt.outerWidth();
var _10=tt.outerHeight();
var _11=_c.edge;
if(e.pageY>_e.top&&e.pageY<_e.top+_11){
_d+="n";
}else{
if(e.pageY<_e.top+_10&&e.pageY>_e.top+_10-_11){
_d+="s";
}
}
if(e.pageX>_e.left&&e.pageX<_e.left+_11){
_d+="w";
}else{
if(e.pageX<_e.left+_f&&e.pageX>_e.left+_f-_11){
_d+="e";
}
}
var _12=_c.handles.split(",");
_12=$.map(_12,function(h){
return $.trim(h).toLowerCase();
});
if($.inArray("all",_12)>=0||$.inArray(_d,_12)>=0){
return _d;
}
for(var i=0;i<_d.length;i++){
var _13=$.inArray(_d.substr(i,1),_12);
if(_13>=0){
return _12[_13];
}
}
return "";
};
$.fn.resizable=function(_14,_15){
if(typeof _14=="string"){
return $.fn.resizable.methods[_14](this,_15);
}
return this.each(function(){
var _16=null;
var _17=$.data(this,"resizable");
if(_17){
$(this)._unbind(".resizable");
_16=$.extend(_17.options,_14||{});
}else{
_16=$.extend({},$.fn.resizable.defaults,$.fn.resizable.parseOptions(this),_14||{});
$.data(this,"resizable",{options:_16});
}
if(_16.disabled==true){
return;
}
$(this)._bind("mousemove.resizable",{target:this},function(e){
if($.fn.resizable.isResizing){
return;
}
var dir=_b(e);
$(e.data.target).css("cursor",dir?dir+"-resize":"");
})._bind("mouseleave.resizable",{target:this},function(e){
$(e.data.target).css("cursor","");
})._bind("mousedown.resizable",{target:this},function(e){
var dir=_b(e);
if(dir==""){
return;
}
function _18(css){
var val=parseInt($(e.data.target).css(css));
if(isNaN(val)){
return 0;
}else{
return val;
}
};
var _19={target:e.data.target,dir:dir,startLeft:_18("left"),startTop:_18("top"),left:_18("left"),top:_18("top"),startX:e.pageX,startY:e.pageY,startWidth:$(e.data.target).outerWidth(),startHeight:$(e.data.target).outerHeight(),width:$(e.data.target).outerWidth(),height:$(e.data.target).outerHeight(),deltaWidth:$(e.data.target).outerWidth()-$(e.data.target).width(),deltaHeight:$(e.data.target).outerHeight()-$(e.data.target).height()};
$(document)._bind("mousedown.resizable",_19,_8);
$(document)._bind("mousemove.resizable",_19,_9);
$(document)._bind("mouseup.resizable",_19,_a);
$("body").css("cursor",dir+"-resize");
});
});
};
$.fn.resizable.methods={options:function(jq){
return $.data(jq[0],"resizable").options;
},enable:function(jq){
return jq.each(function(){
$(this).resizable({disabled:false});
});
},disable:function(jq){
return jq.each(function(){
$(this).resizable({disabled:true});
});
}};
$.fn.resizable.parseOptions=function(_1a){
var t=$(_1a);
return $.extend({},$.parser.parseOptions(_1a,["handles",{minWidth:"number",minHeight:"number",maxWidth:"number",maxHeight:"number",edge:"number"}]),{disabled:(t.attr("disabled")?true:undefined)});
};
$.fn.resizable.defaults={disabled:false,handles:"n, e, s, w, ne, se, sw, nw, all",minWidth:10,minHeight:10,maxWidth:10000,maxHeight:10000,edge:5,onStartResize:function(e){
},onResize:function(e){
},onStopResize:function(e){
}};
$.fn.resizable.isResizing=false;
})(jQuery);

