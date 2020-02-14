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
$(document)._unbind(".combo")._bind("mousedown.combo mousewheel.combo",function(e){
var p=$(e.target).closest("span.combo,div.combo-p,div.menu");
if(p.length){
_1(p);
return;
}
$("body>div.combo-p>div.combo-panel:visible").panel("close");
});
});
function _2(_3){
var _4=$.data(_3,"combo");
var _5=_4.options;
if(!_4.panel){
_4.panel=$("<div class=\"combo-panel\"></div>").appendTo("body");
_4.panel.panel({minWidth:_5.panelMinWidth,maxWidth:_5.panelMaxWidth,minHeight:_5.panelMinHeight,maxHeight:_5.panelMaxHeight,doSize:false,closed:true,cls:"combo-p",style:{position:"absolute",zIndex:10},onOpen:function(){
var _6=$(this).panel("options").comboTarget;
var _7=$.data(_6,"combo");
if(_7){
_7.options.onShowPanel.call(_6);
}
},onBeforeClose:function(){
_1($(this).parent());
},onClose:function(){
var _8=$(this).panel("options").comboTarget;
var _9=$(_8).data("combo");
if(_9){
_9.options.onHidePanel.call(_8);
}
}});
}
var _a=$.extend(true,[],_5.icons);
if(_5.hasDownArrow){
_a.push({iconCls:"combo-arrow",handler:function(e){
_10(e.data.target);
}});
}
$(_3).addClass("combo-f").textbox($.extend({},_5,{icons:_a,onChange:function(){
}}));
$(_3).attr("comboName",$(_3).attr("textboxName"));
_4.combo=$(_3).next();
_4.combo.addClass("combo");
_4.panel._unbind(".combo");
for(var _b in _5.panelEvents){
_4.panel._bind(_b+".combo",{target:_3},_5.panelEvents[_b]);
}
};
function _c(_d){
var _e=$.data(_d,"combo");
var _f=_e.options;
var p=_e.panel;
if(p.is(":visible")){
p.panel("close");
}
if(!_f.cloned){
p.panel("destroy");
}
$(_d).textbox("destroy");
};
function _10(_11){
var _12=$.data(_11,"combo").panel;
if(_12.is(":visible")){
var _13=_12.combo("combo");
_14(_13);
if(_13!=_11){
$(_11).combo("showPanel");
}
}else{
var p=$(_11).closest("div.combo-p").children(".combo-panel");
$("div.combo-panel:visible").not(_12).not(p).panel("close");
$(_11).combo("showPanel");
}
$(_11).combo("textbox").focus();
};
function _1(_15){
$(_15).find(".combo-f").each(function(){
var p=$(this).combo("panel");
if(p.is(":visible")){
p.panel("close");
}
});
};
function _16(e){
var _17=e.data.target;
var _18=$.data(_17,"combo");
var _19=_18.options;
if(!_19.editable){
_10(_17);
}else{
var p=$(_17).closest("div.combo-p").children(".combo-panel");
$("div.combo-panel:visible").not(p).each(function(){
var _1a=$(this).combo("combo");
if(_1a!=_17){
_14(_1a);
}
});
}
};
function _1b(e){
var _1c=e.data.target;
var t=$(_1c);
var _1d=t.data("combo");
var _1e=t.combo("options");
_1d.panel.panel("options").comboTarget=_1c;
switch(e.keyCode){
case 38:
_1e.keyHandler.up.call(_1c,e);
break;
case 40:
_1e.keyHandler.down.call(_1c,e);
break;
case 37:
_1e.keyHandler.left.call(_1c,e);
break;
case 39:
_1e.keyHandler.right.call(_1c,e);
break;
case 13:
e.preventDefault();
_1e.keyHandler.enter.call(_1c,e);
return false;
case 9:
case 27:
_14(_1c);
break;
default:
if(_1e.editable){
if(_1d.timer){
clearTimeout(_1d.timer);
}
_1d.timer=setTimeout(function(){
var q=t.combo("getText");
if(_1d.previousText!=q){
_1d.previousText=q;
t.combo("showPanel");
_1e.keyHandler.query.call(_1c,q,e);
t.combo("validate");
}
},_1e.delay);
}
}
};
function _1f(e){
var _20=e.data.target;
var _21=$(_20).data("combo");
if(_21.timer){
clearTimeout(_21.timer);
}
};
function _22(_23){
var _24=$.data(_23,"combo");
var _25=_24.combo;
var _26=_24.panel;
var _27=$(_23).combo("options");
var _28=_26.panel("options");
_28.comboTarget=_23;
if(_28.closed){
_26.panel("panel").show().css({zIndex:($.fn.menu?$.fn.menu.defaults.zIndex++:($.fn.window?$.fn.window.defaults.zIndex++:99)),left:-999999});
_26.panel("resize",{width:(_27.panelWidth?_27.panelWidth:_25._outerWidth()),height:_27.panelHeight});
_26.panel("panel").hide();
_26.panel("open");
}
(function(){
if(_28.comboTarget==_23&&_26.is(":visible")){
_26.panel("move",{left:_29(),top:_2a()});
setTimeout(arguments.callee,200);
}
})();
function _29(){
var _2b=_25.offset().left;
if(_27.panelAlign=="right"){
_2b+=_25._outerWidth()-_26._outerWidth();
}
if(_2b+_26._outerWidth()>$(window)._outerWidth()+$(document).scrollLeft()){
_2b=$(window)._outerWidth()+$(document).scrollLeft()-_26._outerWidth();
}
if(_2b<0){
_2b=0;
}
return _2b;
};
function _2a(){
if(_27.panelValign=="top"){
var top=_25.offset().top-_26._outerHeight();
}else{
if(_27.panelValign=="bottom"){
var top=_25.offset().top+_25._outerHeight();
}else{
var top=_25.offset().top+_25._outerHeight();
if(top+_26._outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
top=_25.offset().top-_26._outerHeight();
}
if(top<$(document).scrollTop()){
top=_25.offset().top+_25._outerHeight();
}
}
}
return top;
};
};
function _14(_2c){
var _2d=$.data(_2c,"combo").panel;
_2d.panel("close");
};
function _2e(_2f,_30){
var _31=$.data(_2f,"combo");
var _32=$(_2f).textbox("getText");
if(_32!=_30){
$(_2f).textbox("setText",_30);
}
_31.previousText=_30;
};
function _33(_34){
var _35=$.data(_34,"combo");
var _36=_35.options;
var _37=$(_34).next();
var _38=[];
_37.find(".textbox-value").each(function(){
_38.push($(this).val());
});
if(_36.multivalue){
return _38;
}else{
return _38.length?_38[0].split(_36.separator):_38;
}
};
function _39(_3a,_3b){
var _3c=$.data(_3a,"combo");
var _3d=_3c.combo;
var _3e=$(_3a).combo("options");
if(!$.isArray(_3b)){
_3b=_3b.split(_3e.separator);
}
var _3f=_33(_3a);
_3d.find(".textbox-value").remove();
if(_3b.length){
if(_3e.multivalue){
for(var i=0;i<_3b.length;i++){
_40(_3b[i]);
}
}else{
_40(_3b.join(_3e.separator));
}
}
function _40(_41){
var _42=$(_3a).attr("textboxName")||"";
var _43=$("<input type=\"hidden\" class=\"textbox-value\">").appendTo(_3d);
_43.attr("name",_42);
if(_3e.disabled){
_43.attr("disabled","disabled");
}
_43.val(_41);
};
var _44=(function(){
if(_3e.onChange==$.parser.emptyFn){
return false;
}
if(_3f.length!=_3b.length){
return true;
}
for(var i=0;i<_3b.length;i++){
if(_3b[i]!=_3f[i]){
return true;
}
}
return false;
})();
if(_44){
$(_3a).val(_3b.join(_3e.separator));
if(_3e.multiple){
_3e.onChange.call(_3a,_3b,_3f);
}else{
_3e.onChange.call(_3a,_3b[0],_3f[0]);
}
$(_3a).closest("form").trigger("_change",[_3a]);
}
};
function _45(_46){
var _47=_33(_46);
return _47[0];
};
function _48(_49,_4a){
_39(_49,[_4a]);
};
function _4b(_4c){
var _4d=$.data(_4c,"combo").options;
var _4e=_4d.onChange;
_4d.onChange=$.parser.emptyFn;
if(_4d.multiple){
_39(_4c,_4d.value?_4d.value:[]);
}else{
_48(_4c,_4d.value);
}
_4d.onChange=_4e;
};
$.fn.combo=function(_4f,_50){
if(typeof _4f=="string"){
var _51=$.fn.combo.methods[_4f];
if(_51){
return _51(this,_50);
}else{
return this.textbox(_4f,_50);
}
}
_4f=_4f||{};
return this.each(function(){
var _52=$.data(this,"combo");
if(_52){
$.extend(_52.options,_4f);
if(_4f.value!=undefined){
_52.options.originalValue=_4f.value;
}
}else{
_52=$.data(this,"combo",{options:$.extend({},$.fn.combo.defaults,$.fn.combo.parseOptions(this),_4f),previousText:""});
if(_52.options.multiple&&_52.options.value==""){
_52.options.originalValue=[];
}else{
_52.options.originalValue=_52.options.value;
}
}
_2(this);
_4b(this);
});
};
$.fn.combo.methods={options:function(jq){
var _53=jq.textbox("options");
return $.extend($.data(jq[0],"combo").options,{width:_53.width,height:_53.height,disabled:_53.disabled,readonly:_53.readonly});
},cloneFrom:function(jq,_54){
return jq.each(function(){
$(this).textbox("cloneFrom",_54);
$.data(this,"combo",{options:$.extend(true,{cloned:true},$(_54).combo("options")),combo:$(this).next(),panel:$(_54).combo("panel")});
$(this).addClass("combo-f").attr("comboName",$(this).attr("textboxName"));
});
},combo:function(jq){
return jq.closest(".combo-panel").panel("options").comboTarget;
},panel:function(jq){
return $.data(jq[0],"combo").panel;
},destroy:function(jq){
return jq.each(function(){
_c(this);
});
},showPanel:function(jq){
return jq.each(function(){
_22(this);
});
},hidePanel:function(jq){
return jq.each(function(){
_14(this);
});
},clear:function(jq){
return jq.each(function(){
$(this).textbox("setText","");
var _55=$.data(this,"combo").options;
if(_55.multiple){
$(this).combo("setValues",[]);
}else{
$(this).combo("setValue","");
}
});
},reset:function(jq){
return jq.each(function(){
var _56=$.data(this,"combo").options;
if(_56.multiple){
$(this).combo("setValues",_56.originalValue);
}else{
$(this).combo("setValue",_56.originalValue);
}
});
},setText:function(jq,_57){
return jq.each(function(){
_2e(this,_57);
});
},getValues:function(jq){
return _33(jq[0]);
},setValues:function(jq,_58){
return jq.each(function(){
_39(this,_58);
});
},getValue:function(jq){
return _45(jq[0]);
},setValue:function(jq,_59){
return jq.each(function(){
_48(this,_59);
});
}};
$.fn.combo.parseOptions=function(_5a){
var t=$(_5a);
return $.extend({},$.fn.textbox.parseOptions(_5a),$.parser.parseOptions(_5a,["separator","panelAlign",{panelWidth:"number",hasDownArrow:"boolean",delay:"number",reversed:"boolean",multivalue:"boolean",selectOnNavigation:"boolean"},{panelMinWidth:"number",panelMaxWidth:"number",panelMinHeight:"number",panelMaxHeight:"number"}]),{panelHeight:(t.attr("panelHeight")=="auto"?"auto":parseInt(t.attr("panelHeight"))||undefined),multiple:(t.attr("multiple")?true:undefined)});
};
$.fn.combo.defaults=$.extend({},$.fn.textbox.defaults,{inputEvents:{click:_16,keydown:_1b,paste:_1b,drop:_1b,blur:_1f},panelEvents:{mousedown:function(e){
e.preventDefault();
e.stopPropagation();
}},panelWidth:null,panelHeight:300,panelMinWidth:null,panelMaxWidth:null,panelMinHeight:null,panelMaxHeight:null,panelAlign:"left",panelValign:"auto",reversed:false,multiple:false,multivalue:true,selectOnNavigation:true,separator:",",hasDownArrow:true,delay:200,keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
},query:function(q,e){
}},onShowPanel:function(){
},onHidePanel:function(){
},onChange:function(_5b,_5c){
}});
})(jQuery);

