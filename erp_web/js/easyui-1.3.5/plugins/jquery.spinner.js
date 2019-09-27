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
var _3=$("<span class=\"spinner\">"+"<span class=\"spinner-arrow\">"+"<span class=\"spinner-arrow-up\"></span>"+"<span class=\"spinner-arrow-down\"></span>"+"</span>"+"</span>").insertAfter(_2);
$(_2).addClass("spinner-text spinner-f").prependTo(_3);
return _3;
};
function _4(_5,_6){
var _7=$.data(_5,"spinner").options;
var _8=$.data(_5,"spinner").spinner;
if(_6){
_7.width=_6;
}
var _9=$("<div style=\"display:none\"></div>").insertBefore(_8);
_8.appendTo("body");
if(isNaN(_7.width)){
_7.width=$(_5).outerWidth();
}
var _a=_8.find(".spinner-arrow");
_8._outerWidth(_7.width)._outerHeight(_7.height);
$(_5)._outerWidth(_8.width()-_a.outerWidth());
$(_5).css({height:_8.height()+"px",lineHeight:_8.height()+"px"});
_a._outerHeight(_8.height());
_a.find("span")._outerHeight(_a.height()/2);
_8.insertAfter(_9);
_9.remove();
};
function _b(_c){
var _d=$.data(_c,"spinner").options;
var _e=$.data(_c,"spinner").spinner;
_e.find(".spinner-arrow-up,.spinner-arrow-down").unbind(".spinner");
if(!_d.disabled){
_e.find(".spinner-arrow-up").bind("mouseenter.spinner",function(){
$(this).addClass("spinner-arrow-hover");
}).bind("mouseleave.spinner",function(){
$(this).removeClass("spinner-arrow-hover");
}).bind("click.spinner",function(){
_d.spin.call(_c,false);
_d.onSpinUp.call(_c);
$(_c).validatebox("validate");
});
_e.find(".spinner-arrow-down").bind("mouseenter.spinner",function(){
$(this).addClass("spinner-arrow-hover");
}).bind("mouseleave.spinner",function(){
$(this).removeClass("spinner-arrow-hover");
}).bind("click.spinner",function(){
_d.spin.call(_c,true);
_d.onSpinDown.call(_c);
$(_c).validatebox("validate");
});
}
};
function _f(_10,_11){
var _12=$.data(_10,"spinner").options;
if(_11){
_12.disabled=true;
$(_10).attr("disabled",true);
}else{
_12.disabled=false;
$(_10).removeAttr("disabled");
}
};
$.fn.spinner=function(_13,_14){
if(typeof _13=="string"){
var _15=$.fn.spinner.methods[_13];
if(_15){
return _15(this,_14);
}else{
return this.validatebox(_13,_14);
}
}
_13=_13||{};
return this.each(function(){
var _16=$.data(this,"spinner");
if(_16){
$.extend(_16.options,_13);
}else{
_16=$.data(this,"spinner",{options:$.extend({},$.fn.spinner.defaults,$.fn.spinner.parseOptions(this),_13),spinner:_1(this)});
$(this).removeAttr("disabled");
}
_16.options.originalValue=_16.options.value;
$(this).val(_16.options.value);
$(this).attr("readonly",!_16.options.editable);
_f(this,_16.options.disabled);
_4(this);
$(this).validatebox(_16.options);
_b(this);
});
};
$.fn.spinner.methods={options:function(jq){
var _17=$.data(jq[0],"spinner").options;
return $.extend(_17,{value:jq.val()});
},destroy:function(jq){
return jq.each(function(){
var _18=$.data(this,"spinner").spinner;
$(this).validatebox("destroy");
_18.remove();
});
},resize:function(jq,_19){
return jq.each(function(){
_4(this,_19);
});
},enable:function(jq){
return jq.each(function(){
_f(this,false);
_b(this);
});
},disable:function(jq){
return jq.each(function(){
_f(this,true);
_b(this);
});
},getValue:function(jq){
return jq.val();
},setValue:function(jq,_1a){
return jq.each(function(){
var _1b=$.data(this,"spinner").options;
_1b.value=_1a;
$(this).val(_1a);
});
},clear:function(jq){
return jq.each(function(){
var _1c=$.data(this,"spinner").options;
_1c.value="";
$(this).val("");
});
},reset:function(jq){
return jq.each(function(){
var _1d=$(this).spinner("options");
$(this).spinner("setValue",_1d.originalValue);
});
}};
$.fn.spinner.parseOptions=function(_1e){
var t=$(_1e);
return $.extend({},$.fn.validatebox.parseOptions(_1e),$.parser.parseOptions(_1e,["width","height","min","max",{increment:"number",editable:"boolean"}]),{value:(t.val()||undefined),disabled:(t.attr("disabled")?true:undefined)});
};
$.fn.spinner.defaults=$.extend({},$.fn.validatebox.defaults,{width:"auto",height:22,deltaX:19,value:"",min:null,max:null,increment:1,editable:true,disabled:false,spin:function(_1f){
},onSpinUp:function(){
},onSpinDown:function(){
}});
})(jQuery);

