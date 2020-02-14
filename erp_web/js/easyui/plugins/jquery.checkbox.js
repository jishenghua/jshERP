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
var _1=1;
function _2(_3){
var _4=$("<span class=\"checkbox inputbox\">"+"<span class=\"checkbox-inner\">"+"<svg xml:space=\"preserve\" focusable=\"false\" version=\"1.1\" viewBox=\"0 0 24 24\"><path d=\"M4.1,12.7 9,17.6 20.3,6.3\" fill=\"none\" stroke=\"white\"></path></svg>"+"</span>"+"<input type=\"checkbox\" class=\"checkbox-value\">"+"</span>").insertAfter(_3);
var t=$(_3);
t.addClass("checkbox-f").hide();
var _5=t.attr("name");
if(_5){
t.removeAttr("name").attr("checkboxName",_5);
_4.find(".checkbox-value").attr("name",_5);
}
return _4;
};
function _6(_7){
var _8=$.data(_7,"checkbox");
var _9=_8.options;
var _a=_8.checkbox;
var _b="_easyui_checkbox_"+(++_1);
var _c=_a.find(".checkbox-value").attr("id",_b);
_c._unbind(".checkbox")._bind("change.checkbox",function(e){
return false;
});
if(_9.label){
if(typeof _9.label=="object"){
_8.label=$(_9.label);
_8.label.attr("for",_b);
}else{
$(_8.label).remove();
_8.label=$("<label class=\"textbox-label\"></label>").html(_9.label);
_8.label.css("textAlign",_9.labelAlign).attr("for",_b);
if(_9.labelPosition=="after"){
_8.label.insertAfter(_a);
}else{
_8.label.insertBefore(_7);
}
_8.label.removeClass("textbox-label-left textbox-label-right textbox-label-top");
_8.label.addClass("textbox-label-"+_9.labelPosition);
}
}else{
$(_8.label).remove();
}
$(_7).checkbox("setValue",_9.value);
_d(_7,_9.checked);
_e(_7,_9.readonly);
_f(_7,_9.disabled);
};
function _10(_11){
var _12=$.data(_11,"checkbox");
var _13=_12.options;
var _14=_12.checkbox;
_14._unbind(".checkbox")._bind("click.checkbox",function(){
if(!_13.disabled&&!_13.readonly){
_d(_11,!_13.checked);
}
});
};
function _15(_16){
var _17=$.data(_16,"checkbox");
var _18=_17.options;
var _19=_17.checkbox;
_19._size(_18,_19.parent());
if(_18.label&&_18.labelPosition){
if(_18.labelPosition=="top"){
_17.label._size({width:_18.labelWidth},_19);
}else{
_17.label._size({width:_18.labelWidth,height:_19.outerHeight()},_19);
_17.label.css("lineHeight",_19.outerHeight()+"px");
}
}
};
function _d(_1a,_1b){
var _1c=$.data(_1a,"checkbox");
var _1d=_1c.options;
var _1e=_1c.checkbox;
_1e.find(".checkbox-value")._propAttr("checked",_1b);
var _1f=_1e.find(".checkbox-inner").css("display",_1b?"":"none");
if(_1b){
_1e.addClass("checkbox-checked");
$(_1c.label).addClass("textbox-label-checked");
}else{
_1e.removeClass("checkbox-checked");
$(_1c.label).removeClass("textbox-label-checked");
}
if(_1d.checked!=_1b){
_1d.checked=_1b;
_1d.onChange.call(_1a,_1b);
$(_1a).closest("form").trigger("_change",[_1a]);
}
};
function _e(_20,_21){
var _22=$.data(_20,"checkbox");
var _23=_22.options;
_23.readonly=_21==undefined?true:_21;
if(_23.readonly){
_22.checkbox.addClass("checkbox-readonly");
$(_22.label).addClass("textbox-label-readonly");
}else{
_22.checkbox.removeClass("checkbox-readonly");
$(_22.label).removeClass("textbox-label-readonly");
}
};
function _f(_24,_25){
var _26=$.data(_24,"checkbox");
var _27=_26.options;
var _28=_26.checkbox;
var rv=_28.find(".checkbox-value");
_27.disabled=_25;
if(_25){
$(_24).add(rv)._propAttr("disabled",true);
_28.addClass("checkbox-disabled");
$(_26.label).addClass("textbox-label-disabled");
}else{
$(_24).add(rv)._propAttr("disabled",false);
_28.removeClass("checkbox-disabled");
$(_26.label).removeClass("textbox-label-disabled");
}
};
$.fn.checkbox=function(_29,_2a){
if(typeof _29=="string"){
return $.fn.checkbox.methods[_29](this,_2a);
}
_29=_29||{};
return this.each(function(){
var _2b=$.data(this,"checkbox");
if(_2b){
$.extend(_2b.options,_29);
}else{
_2b=$.data(this,"checkbox",{options:$.extend({},$.fn.checkbox.defaults,$.fn.checkbox.parseOptions(this),_29),checkbox:_2(this)});
}
_2b.options.originalChecked=_2b.options.checked;
_6(this);
_10(this);
_15(this);
});
};
$.fn.checkbox.methods={options:function(jq){
var _2c=jq.data("checkbox");
return $.extend(_2c.options,{value:_2c.checkbox.find(".checkbox-value").val()});
},setValue:function(jq,_2d){
return jq.each(function(){
$(this).val(_2d);
$.data(this,"checkbox").checkbox.find(".checkbox-value").val(_2d);
});
},enable:function(jq){
return jq.each(function(){
_f(this,false);
});
},disable:function(jq){
return jq.each(function(){
_f(this,true);
});
},readonly:function(jq,_2e){
return jq.each(function(){
_e(this,_2e);
});
},check:function(jq){
return jq.each(function(){
_d(this,true);
});
},uncheck:function(jq){
return jq.each(function(){
_d(this,false);
});
},clear:function(jq){
return jq.each(function(){
_d(this,false);
});
},reset:function(jq){
return jq.each(function(){
var _2f=$(this).checkbox("options");
_d(this,_2f.originalChecked);
});
}};
$.fn.checkbox.parseOptions=function(_30){
var t=$(_30);
return $.extend({},$.parser.parseOptions(_30,["label","labelPosition","labelAlign",{labelWidth:"number"}]),{value:(t.val()||undefined),checked:(t.attr("checked")?true:undefined),disabled:(t.attr("disabled")?true:undefined),readonly:(t.attr("readonly")?true:undefined)});
};
$.fn.checkbox.defaults={width:20,height:20,value:null,disabled:false,readonly:false,checked:false,label:null,labelWidth:"auto",labelPosition:"before",labelAlign:"left",onChange:function(_31){
}};
})(jQuery);

