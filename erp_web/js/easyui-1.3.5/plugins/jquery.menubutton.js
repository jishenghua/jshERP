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
function _1(_2){
var _3=$.data(_2,"menubutton").options;
var _4=$(_2);
_4.removeClass(_3.cls.btn1+" "+_3.cls.btn2).addClass("m-btn");
_4.linkbutton($.extend({},_3,{text:_3.text+"<span class=\""+_3.cls.arrow+"\">&nbsp;</span>"}));
if(_3.menu){
$(_3.menu).menu();
var _5=$(_3.menu).menu("options");
var _6=_5.onShow;
var _7=_5.onHide;
$.extend(_5,{onShow:function(){
var _8=$(this).menu("options");
var _9=$(_8.alignTo);
var _a=_9.menubutton("options");
_9.addClass((_a.plain==true)?_a.cls.btn2:_a.cls.btn1);
_6.call(this);
},onHide:function(){
var _b=$(this).menu("options");
var _c=$(_b.alignTo);
var _d=_c.menubutton("options");
_c.removeClass((_d.plain==true)?_d.cls.btn2:_d.cls.btn1);
_7.call(this);
}});
}
_e(_2,_3.disabled);
};
function _e(_f,_10){
var _11=$.data(_f,"menubutton").options;
_11.disabled=_10;
var btn=$(_f);
var t=btn.find("."+_11.cls.trigger);
if(!t.length){
t=btn;
}
t.unbind(".menubutton");
if(_10){
btn.linkbutton("disable");
}else{
btn.linkbutton("enable");
var _12=null;
t.bind("click.menubutton",function(){
_13(_f);
return false;
}).bind("mouseenter.menubutton",function(){
_12=setTimeout(function(){
_13(_f);
},_11.duration);
return false;
}).bind("mouseleave.menubutton",function(){
if(_12){
clearTimeout(_12);
}
});
}
};
function _13(_14){
var _15=$.data(_14,"menubutton").options;
if(_15.disabled||!_15.menu){
return;
}
$("body>div.menu-top").menu("hide");
var btn=$(_14);
var mm=$(_15.menu);
if(mm.length){
mm.menu("options").alignTo=btn;
mm.menu("show",{alignTo:btn});
}
btn.blur();
};
$.fn.menubutton=function(_16,_17){
if(typeof _16=="string"){
var _18=$.fn.menubutton.methods[_16];
if(_18){
return _18(this,_17);
}else{
return this.linkbutton(_16,_17);
}
}
_16=_16||{};
return this.each(function(){
var _19=$.data(this,"menubutton");
if(_19){
$.extend(_19.options,_16);
}else{
$.data(this,"menubutton",{options:$.extend({},$.fn.menubutton.defaults,$.fn.menubutton.parseOptions(this),_16)});
$(this).removeAttr("disabled");
}
_1(this);
});
};
$.fn.menubutton.methods={options:function(jq){
var _1a=jq.linkbutton("options");
var _1b=$.data(jq[0],"menubutton").options;
_1b.toggle=_1a.toggle;
_1b.selected=_1a.selected;
return _1b;
},enable:function(jq){
return jq.each(function(){
_e(this,false);
});
},disable:function(jq){
return jq.each(function(){
_e(this,true);
});
},destroy:function(jq){
return jq.each(function(){
var _1c=$(this).menubutton("options");
if(_1c.menu){
$(_1c.menu).menu("destroy");
}
$(this).remove();
});
}};
$.fn.menubutton.parseOptions=function(_1d){
var t=$(_1d);
return $.extend({},$.fn.linkbutton.parseOptions(_1d),$.parser.parseOptions(_1d,["menu",{plain:"boolean",duration:"number"}]));
};
$.fn.menubutton.defaults=$.extend({},$.fn.linkbutton.defaults,{plain:true,menu:null,duration:100,cls:{btn1:"m-btn-active",btn2:"m-btn-plain-active",arrow:"m-btn-downarrow",trigger:"m-btn"}});
})(jQuery);

