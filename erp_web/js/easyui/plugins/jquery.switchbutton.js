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
var _4=$("<span class=\"switchbutton\">"+"<span class=\"switchbutton-inner\">"+"<span class=\"switchbutton-on\"></span>"+"<span class=\"switchbutton-handle\"></span>"+"<span class=\"switchbutton-off\"></span>"+"<input class=\"switchbutton-value\" type=\"checkbox\" tabindex=\"-1\">"+"</span>"+"</span>").insertAfter(_3);
var t=$(_3);
t.addClass("switchbutton-f").hide();
var _5=t.attr("name");
if(_5){
t.removeAttr("name").attr("switchbuttonName",_5);
_4.find(".switchbutton-value").attr("name",_5);
}
_4._bind("_resize",function(e,_6){
if($(this).hasClass("easyui-fluid")||_6){
_7(_3);
}
return false;
});
return _4;
};
function _7(_8,_9){
var _a=$.data(_8,"switchbutton");
var _b=_a.options;
var _c=_a.switchbutton;
if(_9){
$.extend(_b,_9);
}
var _d=_c.is(":visible");
if(!_d){
_c.appendTo("body");
}
_c._size(_b);
if(_b.label&&_b.labelPosition){
if(_b.labelPosition=="top"){
_a.label._size({width:_b.labelWidth},_c);
}else{
_a.label._size({width:_b.labelWidth,height:_c.outerHeight()},_c);
_a.label.css("lineHeight",_c.outerHeight()+"px");
}
}
var w=_c.width();
var h=_c.height();
var w=_c.outerWidth();
var h=_c.outerHeight();
var _e=parseInt(_b.handleWidth)||_c.height();
var _f=w*2-_e;
_c.find(".switchbutton-inner").css({width:_f+"px",height:h+"px",lineHeight:h+"px"});
_c.find(".switchbutton-handle")._outerWidth(_e)._outerHeight(h).css({marginLeft:-_e/2+"px"});
_c.find(".switchbutton-on").css({width:(w-_e/2)+"px",textIndent:(_b.reversed?"":"-")+_e/2+"px"});
_c.find(".switchbutton-off").css({width:(w-_e/2)+"px",textIndent:(_b.reversed?"-":"")+_e/2+"px"});
_b.marginWidth=w-_e;
_10(_8,_b.checked,false);
if(!_d){
_c.insertAfter(_8);
}
};
function _11(_12){
var _13=$.data(_12,"switchbutton");
var _14=_13.options;
var _15=_13.switchbutton;
var _16=_15.find(".switchbutton-inner");
var on=_16.find(".switchbutton-on").html(_14.onText);
var off=_16.find(".switchbutton-off").html(_14.offText);
var _17=_16.find(".switchbutton-handle").html(_14.handleText);
if(_14.reversed){
off.prependTo(_16);
on.insertAfter(_17);
}else{
on.prependTo(_16);
off.insertAfter(_17);
}
var _18="_easyui_switchbutton_"+(++_1);
var _19=_15.find(".switchbutton-value")._propAttr("checked",_14.checked).attr("id",_18);
_19._unbind(".switchbutton")._bind("change.switchbutton",function(e){
return false;
});
_15.removeClass("switchbutton-reversed").addClass(_14.reversed?"switchbutton-reversed":"");
if(_14.label){
if(typeof _14.label=="object"){
_13.label=$(_14.label);
_13.label.attr("for",_18);
}else{
$(_13.label).remove();
_13.label=$("<label class=\"textbox-label\"></label>").html(_14.label);
_13.label.css("textAlign",_14.labelAlign).attr("for",_18);
if(_14.labelPosition=="after"){
_13.label.insertAfter(_15);
}else{
_13.label.insertBefore(_12);
}
_13.label.removeClass("textbox-label-left textbox-label-right textbox-label-top");
_13.label.addClass("textbox-label-"+_14.labelPosition);
}
}else{
$(_13.label).remove();
}
_10(_12,_14.checked);
_1a(_12,_14.readonly);
_1b(_12,_14.disabled);
$(_12).switchbutton("setValue",_14.value);
};
function _10(_1c,_1d,_1e){
var _1f=$.data(_1c,"switchbutton");
var _20=_1f.options;
var _21=_1f.switchbutton.find(".switchbutton-inner");
var _22=_21.find(".switchbutton-on");
var _23=_20.reversed?(_1d?_20.marginWidth:0):(_1d?0:_20.marginWidth);
var dir=_22.css("float").toLowerCase();
var css={};
css["margin-"+dir]=-_23+"px";
_1e?_21.animate(css,200):_21.css(css);
var _24=_21.find(".switchbutton-value");
$(_1c).add(_24)._propAttr("checked",_1d);
if(_20.checked!=_1d){
_20.checked=_1d;
_20.onChange.call(_1c,_20.checked);
$(_1c).closest("form").trigger("_change",[_1c]);
}
};
function _1b(_25,_26){
var _27=$.data(_25,"switchbutton");
var _28=_27.options;
var _29=_27.switchbutton;
var _2a=_29.find(".switchbutton-value");
if(_26){
_28.disabled=true;
$(_25).add(_2a)._propAttr("disabled",true);
_29.addClass("switchbutton-disabled");
_29.removeAttr("tabindex");
}else{
_28.disabled=false;
$(_25).add(_2a)._propAttr("disabled",false);
_29.removeClass("switchbutton-disabled");
_29.attr("tabindex",$(_25).attr("tabindex")||"");
}
};
function _1a(_2b,_2c){
var _2d=$.data(_2b,"switchbutton");
var _2e=_2d.options;
_2e.readonly=_2c==undefined?true:_2c;
_2d.switchbutton.removeClass("switchbutton-readonly").addClass(_2e.readonly?"switchbutton-readonly":"");
};
function _2f(_30){
var _31=$.data(_30,"switchbutton");
var _32=_31.options;
_31.switchbutton._unbind(".switchbutton")._bind("click.switchbutton",function(){
if(!_32.disabled&&!_32.readonly){
_10(_30,_32.checked?false:true,true);
}
})._bind("keydown.switchbutton",function(e){
if(e.which==13||e.which==32){
if(!_32.disabled&&!_32.readonly){
_10(_30,_32.checked?false:true,true);
return false;
}
}
});
};
$.fn.switchbutton=function(_33,_34){
if(typeof _33=="string"){
return $.fn.switchbutton.methods[_33](this,_34);
}
_33=_33||{};
return this.each(function(){
var _35=$.data(this,"switchbutton");
if(_35){
$.extend(_35.options,_33);
}else{
_35=$.data(this,"switchbutton",{options:$.extend({},$.fn.switchbutton.defaults,$.fn.switchbutton.parseOptions(this),_33),switchbutton:_2(this)});
}
_35.options.originalChecked=_35.options.checked;
_11(this);
_7(this);
_2f(this);
});
};
$.fn.switchbutton.methods={options:function(jq){
var _36=jq.data("switchbutton");
return $.extend(_36.options,{value:_36.switchbutton.find(".switchbutton-value").val()});
},resize:function(jq,_37){
return jq.each(function(){
_7(this,_37);
});
},enable:function(jq){
return jq.each(function(){
_1b(this,false);
});
},disable:function(jq){
return jq.each(function(){
_1b(this,true);
});
},readonly:function(jq,_38){
return jq.each(function(){
_1a(this,_38);
});
},check:function(jq){
return jq.each(function(){
_10(this,true);
});
},uncheck:function(jq){
return jq.each(function(){
_10(this,false);
});
},clear:function(jq){
return jq.each(function(){
_10(this,false);
});
},reset:function(jq){
return jq.each(function(){
var _39=$(this).switchbutton("options");
_10(this,_39.originalChecked);
});
},setValue:function(jq,_3a){
return jq.each(function(){
$(this).val(_3a);
$.data(this,"switchbutton").switchbutton.find(".switchbutton-value").val(_3a);
});
}};
$.fn.switchbutton.parseOptions=function(_3b){
var t=$(_3b);
return $.extend({},$.parser.parseOptions(_3b,["onText","offText","handleText",{handleWidth:"number",reversed:"boolean"},"label","labelPosition","labelAlign",{labelWidth:"number"}]),{value:(t.val()||undefined),checked:(t.attr("checked")?true:undefined),disabled:(t.attr("disabled")?true:undefined),readonly:(t.attr("readonly")?true:undefined)});
};
$.fn.switchbutton.defaults={handleWidth:"auto",width:60,height:30,checked:false,disabled:false,readonly:false,reversed:false,onText:"ON",offText:"OFF",handleText:"",value:"on",label:null,labelWidth:"auto",labelPosition:"before",labelAlign:"left",onChange:function(_3c){
}};
})(jQuery);

