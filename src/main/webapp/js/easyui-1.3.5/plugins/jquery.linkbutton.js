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
var _3=$.data(_2,"linkbutton").options;
var t=$(_2);
t.addClass("l-btn").removeClass("l-btn-plain l-btn-selected l-btn-plain-selected");
if(_3.plain){
t.addClass("l-btn-plain");
}
if(_3.selected){
t.addClass(_3.plain?"l-btn-selected l-btn-plain-selected":"l-btn-selected");
}
t.attr("group",_3.group||"");
t.attr("id",_3.id||"");
t.html("<span class=\"l-btn-left\">"+"<span class=\"l-btn-text\"></span>"+"</span>");
if(_3.text){
t.find(".l-btn-text").html(_3.text);
if(_3.iconCls){
t.find(".l-btn-text").addClass(_3.iconCls).addClass(_3.iconAlign=="left"?"l-btn-icon-left":"l-btn-icon-right");
}
}else{
t.find(".l-btn-text").html("<span class=\"l-btn-empty\">&nbsp;</span>");
if(_3.iconCls){
t.find(".l-btn-empty").addClass(_3.iconCls);
}
}
t.unbind(".linkbutton").bind("focus.linkbutton",function(){
if(!_3.disabled){
$(this).find(".l-btn-text").addClass("l-btn-focus");
}
}).bind("blur.linkbutton",function(){
$(this).find(".l-btn-text").removeClass("l-btn-focus");
});
if(_3.toggle&&!_3.disabled){
t.bind("click.linkbutton",function(){
if(_3.selected){
$(this).linkbutton("unselect");
}else{
$(this).linkbutton("select");
}
});
}
_4(_2,_3.selected);
_5(_2,_3.disabled);
};
function _4(_6,_7){
var _8=$.data(_6,"linkbutton").options;
if(_7){
if(_8.group){
$("a.l-btn[group=\""+_8.group+"\"]").each(function(){
var o=$(this).linkbutton("options");
if(o.toggle){
$(this).removeClass("l-btn-selected l-btn-plain-selected");
o.selected=false;
}
});
}
$(_6).addClass(_8.plain?"l-btn-selected l-btn-plain-selected":"l-btn-selected");
_8.selected=true;
}else{
if(!_8.group){
$(_6).removeClass("l-btn-selected l-btn-plain-selected");
_8.selected=false;
}
}
};
function _5(_9,_a){
var _b=$.data(_9,"linkbutton");
var _c=_b.options;
$(_9).removeClass("l-btn-disabled l-btn-plain-disabled");
if(_a){
_c.disabled=true;
var _d=$(_9).attr("href");
if(_d){
_b.href=_d;
$(_9).attr("href","javascript:void(0)");
}
if(_9.onclick){
_b.onclick=_9.onclick;
_9.onclick=null;
}
_c.plain?$(_9).addClass("l-btn-disabled l-btn-plain-disabled"):$(_9).addClass("l-btn-disabled");
}else{
_c.disabled=false;
if(_b.href){
$(_9).attr("href",_b.href);
}
if(_b.onclick){
_9.onclick=_b.onclick;
}
}
};
$.fn.linkbutton=function(_e,_f){
if(typeof _e=="string"){
return $.fn.linkbutton.methods[_e](this,_f);
}
_e=_e||{};
return this.each(function(){
var _10=$.data(this,"linkbutton");
if(_10){
$.extend(_10.options,_e);
}else{
$.data(this,"linkbutton",{options:$.extend({},$.fn.linkbutton.defaults,$.fn.linkbutton.parseOptions(this),_e)});
$(this).removeAttr("disabled");
}
_1(this);
});
};
$.fn.linkbutton.methods={options:function(jq){
return $.data(jq[0],"linkbutton").options;
},enable:function(jq){
return jq.each(function(){
_5(this,false);
});
},disable:function(jq){
return jq.each(function(){
_5(this,true);
});
},select:function(jq){
return jq.each(function(){
_4(this,true);
});
},unselect:function(jq){
return jq.each(function(){
_4(this,false);
});
}};
$.fn.linkbutton.parseOptions=function(_11){
var t=$(_11);
return $.extend({},$.parser.parseOptions(_11,["id","iconCls","iconAlign","group",{plain:"boolean",toggle:"boolean",selected:"boolean"}]),{disabled:(t.attr("disabled")?true:undefined),text:$.trim(t.html()),iconCls:(t.attr("icon")||t.attr("iconCls"))});
};
$.fn.linkbutton.defaults={id:null,disabled:false,toggle:false,selected:false,group:null,plain:false,text:"",iconCls:null,iconAlign:"left"};
})(jQuery);

