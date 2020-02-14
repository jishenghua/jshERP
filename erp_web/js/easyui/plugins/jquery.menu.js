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
$(function(){
$(document)._unbind(".menu")._bind("mousedown.menu",function(e){
var m=$(e.target).closest("div.menu,div.combo-p");
if(m.length){
return;
}
$("body>div.menu-top:visible").not(".menu-inline").menu("hide");
_1($("body>div.menu:visible").not(".menu-inline"));
});
});
function _2(_3){
var _4=$.data(_3,"menu").options;
$(_3).addClass("menu-top");
_4.inline?$(_3).addClass("menu-inline"):$(_3).appendTo("body");
$(_3)._bind("_resize",function(e,_5){
if($(this).hasClass("easyui-fluid")||_5){
$(_3).menu("resize",_3);
}
return false;
});
var _6=_7($(_3));
for(var i=0;i<_6.length;i++){
_b(_3,_6[i]);
}
function _7(_8){
var _9=[];
_8.addClass("menu");
_9.push(_8);
if(!_8.hasClass("menu-content")){
_8.children("div").each(function(){
var _a=$(this).children("div");
if(_a.length){
_a.appendTo("body");
this.submenu=_a;
var mm=_7(_a);
_9=_9.concat(mm);
}
});
}
return _9;
};
};
function _b(_c,_d){
var _e=$(_d).addClass("menu");
if(!_e.data("menu")){
_e.data("menu",{options:$.parser.parseOptions(_e[0],["width","height"])});
}
if(!_e.hasClass("menu-content")){
_e.children("div").each(function(){
_f(_c,this);
});
$("<div class=\"menu-line\"></div>").prependTo(_e);
}
_10(_c,_e);
if(!_e.hasClass("menu-inline")){
_e.hide();
}
_11(_c,_e);
};
function _f(_12,div,_13){
var _14=$(div);
var _15=$.extend({},$.parser.parseOptions(_14[0],["id","name","iconCls","href",{separator:"boolean"}]),{disabled:(_14.attr("disabled")?true:undefined),text:$.trim(_14.html()),onclick:_14[0].onclick},_13||{});
_15.onclick=_15.onclick||_15.handler||null;
_14.data("menuitem",{options:_15});
if(_15.separator){
_14.addClass("menu-sep");
}
if(!_14.hasClass("menu-sep")){
_14.addClass("menu-item");
_14.empty().append($("<div class=\"menu-text\"></div>").html(_15.text));
if(_15.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_15.iconCls).appendTo(_14);
}
if(_15.id){
_14.attr("id",_15.id);
}
if(_15.onclick){
if(typeof _15.onclick=="string"){
_14.attr("onclick",_15.onclick);
}else{
_14[0].onclick=eval(_15.onclick);
}
}
if(_15.disabled){
_16(_12,_14[0],true);
}
if(_14[0].submenu){
$("<div class=\"menu-rightarrow\"></div>").appendTo(_14);
}
}
};
function _10(_17,_18){
var _19=$.data(_17,"menu").options;
var _1a=_18.attr("style")||"";
var _1b=_18.is(":visible");
_18.css({display:"block",left:-10000,height:"auto",overflow:"hidden"});
_18.find(".menu-item").each(function(){
$(this)._outerHeight(_19.itemHeight);
$(this).find(".menu-text").css({height:(_19.itemHeight-2)+"px",lineHeight:(_19.itemHeight-2)+"px"});
});
_18.removeClass("menu-noline").addClass(_19.noline?"menu-noline":"");
var _1c=_18.data("menu").options;
var _1d=_1c.width;
var _1e=_1c.height;
if(isNaN(parseInt(_1d))){
_1d=0;
_18.find("div.menu-text").each(function(){
if(_1d<$(this).outerWidth()){
_1d=$(this).outerWidth();
}
});
_1d=_1d?_1d+40:"";
}
var _1f=_18.outerHeight();
if(isNaN(parseInt(_1e))){
_1e=_1f;
if(_18.hasClass("menu-top")&&_19.alignTo){
var at=$(_19.alignTo);
var h1=at.offset().top-$(document).scrollTop();
var h2=$(window)._outerHeight()+$(document).scrollTop()-at.offset().top-at._outerHeight();
_1e=Math.min(_1e,Math.max(h1,h2));
}else{
if(_1e>$(window)._outerHeight()){
_1e=$(window).height();
}
}
}
_18.attr("style",_1a);
_18.show();
_18._size($.extend({},_1c,{width:_1d,height:_1e,minWidth:_1c.minWidth||_19.minWidth,maxWidth:_1c.maxWidth||_19.maxWidth}));
_18.find(".easyui-fluid").triggerHandler("_resize",[true]);
_18.css("overflow",_18.outerHeight()<_1f?"auto":"hidden");
_18.children("div.menu-line")._outerHeight(_1f-2);
if(!_1b){
_18.hide();
}
};
function _11(_20,_21){
var _22=$.data(_20,"menu");
var _23=_22.options;
_21._unbind(".menu");
for(var _24 in _23.events){
_21._bind(_24+".menu",{target:_20},_23.events[_24]);
}
};
function _25(e){
var _26=e.data.target;
var _27=$.data(_26,"menu");
if(_27.timer){
clearTimeout(_27.timer);
_27.timer=null;
}
};
function _28(e){
var _29=e.data.target;
var _2a=$.data(_29,"menu");
if(_2a.options.hideOnUnhover){
_2a.timer=setTimeout(function(){
_2b(_29,$(_29).hasClass("menu-inline"));
},_2a.options.duration);
}
};
function _2c(e){
var _2d=e.data.target;
var _2e=$(e.target).closest(".menu-item");
if(_2e.length){
_2e.siblings().each(function(){
if(this.submenu){
_1(this.submenu);
}
$(this).removeClass("menu-active");
});
_2e.addClass("menu-active");
if(_2e.hasClass("menu-item-disabled")){
_2e.addClass("menu-active-disabled");
return;
}
var _2f=_2e[0].submenu;
if(_2f){
$(_2d).menu("show",{menu:_2f,parent:_2e});
}
}
};
function _30(e){
var _31=$(e.target).closest(".menu-item");
if(_31.length){
_31.removeClass("menu-active menu-active-disabled");
var _32=_31[0].submenu;
if(_32){
if(e.pageX>=parseInt(_32.css("left"))){
_31.addClass("menu-active");
}else{
_1(_32);
}
}else{
_31.removeClass("menu-active");
}
}
};
function _33(e){
var _34=e.data.target;
var _35=$(e.target).closest(".menu-item");
if(_35.length){
var _36=$(_34).data("menu").options;
var _37=_35.data("menuitem").options;
if(_37.disabled){
return;
}
if(!_35[0].submenu){
_2b(_34,_36.inline);
if(_37.href){
location.href=_37.href;
}
}
_35.trigger("mouseenter");
_36.onClick.call(_34,$(_34).menu("getItem",_35[0]));
}
};
function _2b(_38,_39){
var _3a=$.data(_38,"menu");
if(_3a){
if($(_38).is(":visible")){
_1($(_38));
if(_39){
$(_38).show();
}else{
_3a.options.onHide.call(_38);
}
}
}
return false;
};
function _3b(_3c,_3d){
_3d=_3d||{};
var _3e,top;
var _3f=$.data(_3c,"menu").options;
var _40=$(_3d.menu||_3c);
$(_3c).menu("resize",_40[0]);
if(_40.hasClass("menu-top")){
$.extend(_3f,_3d);
_3e=_3f.left;
top=_3f.top;
if(_3f.alignTo){
var at=$(_3f.alignTo);
_3e=at.offset().left;
top=at.offset().top+at._outerHeight();
if(_3f.align=="right"){
_3e+=at.outerWidth()-_40.outerWidth();
}
}
if(_3e+_40.outerWidth()>$(window)._outerWidth()+$(document)._scrollLeft()){
_3e=$(window)._outerWidth()+$(document).scrollLeft()-_40.outerWidth()-5;
}
if(_3e<0){
_3e=0;
}
top=_41(top,_3f.alignTo);
}else{
var _42=_3d.parent;
_3e=_42.offset().left+_42.outerWidth()-2;
if(_3e+_40.outerWidth()+5>$(window)._outerWidth()+$(document).scrollLeft()){
_3e=_42.offset().left-_40.outerWidth()+2;
}
top=_41(_42.offset().top-3);
}
function _41(top,_43){
if(top+_40.outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
if(_43){
top=$(_43).offset().top-_40._outerHeight();
}else{
top=$(window)._outerHeight()+$(document).scrollTop()-_40.outerHeight();
}
}
if(top<0){
top=0;
}
return top;
};
_40.css(_3f.position.call(_3c,_40[0],_3e,top));
_40.show(0,function(){
if(!_40[0].shadow){
_40[0].shadow=$("<div class=\"menu-shadow\"></div>").insertAfter(_40);
}
_40[0].shadow.css({display:(_40.hasClass("menu-inline")?"none":"block"),zIndex:$.fn.menu.defaults.zIndex++,left:_40.css("left"),top:_40.css("top"),width:_40.outerWidth(),height:_40.outerHeight()});
_40.css("z-index",$.fn.menu.defaults.zIndex++);
if(_40.hasClass("menu-top")){
_3f.onShow.call(_3c);
}
});
};
function _1(_44){
if(_44&&_44.length){
_45(_44);
_44.find("div.menu-item").each(function(){
if(this.submenu){
_1(this.submenu);
}
$(this).removeClass("menu-active");
});
}
function _45(m){
m.stop(true,true);
if(m[0].shadow){
m[0].shadow.hide();
}
m.hide();
};
};
function _46(_47,_48){
var _49=null;
var fn=$.isFunction(_48)?_48:function(_4a){
for(var p in _48){
if(_4a[p]!=_48[p]){
return false;
}
}
return true;
};
function _4b(_4c){
_4c.children("div.menu-item").each(function(){
var _4d=$(this).data("menuitem").options;
if(fn.call(_47,_4d)==true){
_49=$(_47).menu("getItem",this);
}else{
if(this.submenu&&!_49){
_4b(this.submenu);
}
}
});
};
_4b($(_47));
return _49;
};
function _16(_4e,_4f,_50){
var t=$(_4f);
if(t.hasClass("menu-item")){
var _51=t.data("menuitem").options;
_51.disabled=_50;
if(_50){
t.addClass("menu-item-disabled");
t[0].onclick=null;
}else{
t.removeClass("menu-item-disabled");
t[0].onclick=_51.onclick;
}
}
};
function _52(_53,_54){
var _55=$.data(_53,"menu").options;
var _56=$(_53);
if(_54.parent){
if(!_54.parent.submenu){
var _57=$("<div></div>").appendTo("body");
_54.parent.submenu=_57;
$("<div class=\"menu-rightarrow\"></div>").appendTo(_54.parent);
_b(_53,_57);
}
_56=_54.parent.submenu;
}
var div=$("<div></div>").appendTo(_56);
_f(_53,div,_54);
};
function _58(_59,_5a){
function _5b(el){
if(el.submenu){
el.submenu.children("div.menu-item").each(function(){
_5b(this);
});
var _5c=el.submenu[0].shadow;
if(_5c){
_5c.remove();
}
el.submenu.remove();
}
$(el).remove();
};
_5b(_5a);
};
function _5d(_5e,_5f,_60){
var _61=$(_5f).parent();
if(_60){
$(_5f).show();
}else{
$(_5f).hide();
}
_10(_5e,_61);
};
function _62(_63){
$(_63).children("div.menu-item").each(function(){
_58(_63,this);
});
if(_63.shadow){
_63.shadow.remove();
}
$(_63).remove();
};
$.fn.menu=function(_64,_65){
if(typeof _64=="string"){
return $.fn.menu.methods[_64](this,_65);
}
_64=_64||{};
return this.each(function(){
var _66=$.data(this,"menu");
if(_66){
$.extend(_66.options,_64);
}else{
_66=$.data(this,"menu",{options:$.extend({},$.fn.menu.defaults,$.fn.menu.parseOptions(this),_64)});
_2(this);
}
$(this).css({left:_66.options.left,top:_66.options.top});
});
};
$.fn.menu.methods={options:function(jq){
return $.data(jq[0],"menu").options;
},show:function(jq,pos){
return jq.each(function(){
_3b(this,pos);
});
},hide:function(jq){
return jq.each(function(){
_2b(this);
});
},destroy:function(jq){
return jq.each(function(){
_62(this);
});
},setText:function(jq,_67){
return jq.each(function(){
var _68=$(_67.target).data("menuitem").options;
_68.text=_67.text;
$(_67.target).children("div.menu-text").html(_67.text);
});
},setIcon:function(jq,_69){
return jq.each(function(){
var _6a=$(_69.target).data("menuitem").options;
_6a.iconCls=_69.iconCls;
$(_69.target).children("div.menu-icon").remove();
if(_69.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_69.iconCls).appendTo(_69.target);
}
});
},getItem:function(jq,_6b){
var _6c=$(_6b).data("menuitem").options;
return $.extend({},_6c,{target:$(_6b)[0]});
},findItem:function(jq,_6d){
if(typeof _6d=="string"){
return _46(jq[0],function(_6e){
return $("<div>"+_6e.text+"</div>").text()==_6d;
});
}else{
return _46(jq[0],_6d);
}
},appendItem:function(jq,_6f){
return jq.each(function(){
_52(this,_6f);
});
},removeItem:function(jq,_70){
return jq.each(function(){
_58(this,_70);
});
},enableItem:function(jq,_71){
return jq.each(function(){
_16(this,_71,false);
});
},disableItem:function(jq,_72){
return jq.each(function(){
_16(this,_72,true);
});
},showItem:function(jq,_73){
return jq.each(function(){
_5d(this,_73,true);
});
},hideItem:function(jq,_74){
return jq.each(function(){
_5d(this,_74,false);
});
},resize:function(jq,_75){
return jq.each(function(){
_10(this,_75?$(_75):$(this));
});
}};
$.fn.menu.parseOptions=function(_76){
return $.extend({},$.parser.parseOptions(_76,[{minWidth:"number",itemHeight:"number",duration:"number",hideOnUnhover:"boolean"},{fit:"boolean",inline:"boolean",noline:"boolean"}]));
};
$.fn.menu.defaults={zIndex:110000,left:0,top:0,alignTo:null,align:"left",minWidth:150,itemHeight:32,duration:100,hideOnUnhover:true,inline:false,fit:false,noline:false,events:{mouseenter:_25,mouseleave:_28,mouseover:_2c,mouseout:_30,click:_33},position:function(_77,_78,top){
return {left:_78,top:top};
},onShow:function(){
},onHide:function(){
},onClick:function(_79){
}};
})(jQuery);

