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
function _1(_2){
var _3=$.data(_2,"timepicker");
var _4=_3.options;
$(_2).addClass("timepicker-f").combo($.extend({},_4,{onShowPanel:function(){
_5(this);
_6(_2);
_13(_2,$(_2).timepicker("getValue"));
}}));
$(_2).timepicker("initValue",_4.value);
function _5(_7){
var _8=$(_7).timepicker("options");
var _9=$(_7).combo("panel");
_9._unbind(".timepicker")._bind("click.timepicker",function(e){
if($(e.target).hasClass("datebox-button-a")){
var _a=parseInt($(e.target).attr("datebox-button-index"));
_8.buttons[_a].handler.call(e.target,_7);
}
});
};
function _6(_b){
var _c=$(_b).combo("panel");
if(_c.children("div.datebox-button").length){
return;
}
var _d=$("<div class=\"datebox-button\"><table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%\"><tr></tr></table></div>").appendTo(_c);
var tr=_d.find("tr");
for(var i=0;i<_4.buttons.length;i++){
var td=$("<td></td>").appendTo(tr);
var _e=_4.buttons[i];
var t=$("<a class=\"datebox-button-a\" href=\"javascript:;\"></a>").html($.isFunction(_e.text)?_e.text(_b):_e.text).appendTo(td);
t.attr("datebox-button-index",i);
}
tr.find("td").css("width",(100/_4.buttons.length)+"%");
};
};
function _f(_10,_11){
var _12=$(_10).data("timepicker").options;
_13(_10,_11);
_12.value=_14(_10);
$(_10).combo("setValue",_12.value).combo("setText",_12.value);
};
function _13(_15,_16){
var _17=$(_15).data("timepicker").options;
if(_16){
var _18=_16.split(" ");
var hm=_18[0].split(":");
_17.selectingHour=parseInt(hm[0],10);
_17.selectingMinute=parseInt(hm[1],10);
_17.selectingAmpm=_18[1];
}else{
_17.selectingHour=12;
_17.selectingMinute=0;
_17.selectingAmpm=_17.ampm[0];
}
_19(_15);
};
function _14(_1a){
var _1b=$(_1a).data("timepicker").options;
var h=_1b.selectingHour;
var m=_1b.selectingMinute;
var _1c=_1b.selectingAmpm;
if(!_1c){
_1c=_1b.ampm[0];
}
return (h<10?"0"+h:h)+":"+(m<10?"0"+m:m)+" "+_1c;
};
function _19(_1d){
var _1e=$(_1d).data("timepicker").options;
var _1f=$(_1d).combo("panel");
var _20=_1f.children(".timepicker-panel");
if(!_20.length){
var _20=$("<div class=\"timepicker-panel f-column\"></div>").prependTo(_1f);
}
_20.empty();
if(_1e.panelHeight!="auto"){
var _21=_1f.height()-_1f.find(".datebox-button").outerHeight();
_20._outerHeight(_21);
}
_22(_1d);
_23(_1d);
_20.off(".timepicker");
_20.on("click.timepicker",".title-hour",function(e){
_1e.selectingType="hour";
_19(_1d);
}).on("click.timepicker",".title-minute",function(e){
_1e.selectingType="minute";
_19(_1d);
}).on("click.timepicker",".title-am",function(e){
_1e.selectingAmpm=_1e.ampm[0];
_19(_1d);
}).on("click.timepicker",".title-pm",function(e){
_1e.selectingAmpm=_1e.ampm[1];
_19(_1d);
}).on("click.timepicker",".item",function(e){
var _24=parseInt($(this).text(),10);
if(_1e.selectingType=="hour"){
_1e.selectingHour=_24;
}else{
_1e.selectingMinute=_24;
}
_19(_1d);
});
};
function _22(_25){
var _26=$(_25).data("timepicker").options;
var _27=$(_25).combo("panel");
var _28=_27.find(".timepicker-panel");
var _29=_26.selectingHour;
var _2a=_26.selectingMinute;
$("<div class=\"panel-header f-noshrink f-row f-content-center\">"+"<div class=\"title title-hour\">"+(_29<10?"0"+_29:_29)+"</div>"+"<div class=\"sep\">:</div>"+"<div class=\"title title-minute\">"+(_2a<10?"0"+_2a:_2a)+"</div>"+"<div class=\"ampm f-column\">"+"<div class=\"title title-am\">"+_26.ampm[0]+"</div>"+"<div class=\"title title-pm\">"+_26.ampm[1]+"</div>"+"</div>"+"</div>").appendTo(_28);
var _2b=_28.find(".panel-header");
if(_26.selectingType=="hour"){
_2b.find(".title-hour").addClass("title-selected");
}else{
_2b.find(".title-minute").addClass("title-selected");
}
if(_26.selectingAmpm==_26.ampm[0]){
_2b.find(".title-am").addClass("title-selected");
}
if(_26.selectingAmpm==_26.ampm[1]){
_2b.find(".title-pm").addClass("title-selected");
}
};
function _23(_2c){
var _2d=$(_2c).data("timepicker").options;
var _2e=$(_2c).combo("panel");
var _2f=_2e.find(".timepicker-panel");
var _30=$("<div class=\"clock-wrap f-full f-column f-content-center\">"+"</div>").appendTo(_2f);
var _31=_30.outerWidth();
var _32=_30.outerHeight();
var _33=Math.min(_31,_32)-20;
var _34=_33/2;
_31=_33;
_32=_33;
var _35=_2d.selectingType=="hour"?_2d.selectingHour:_2d.selectingMinute;
var _36=_35/(_2d.selectingType=="hour"?12:60)*360;
_36=parseFloat(_36).toFixed(4);
var _37={transform:"rotate("+_36+"deg)"};
var _38={width:_31+"px",height:_32+"px",marginLeft:-_31/2+"px",marginTop:-_32/2+"px"};
var _39=[];
_39.push("<div class=\"clock\">");
_39.push("<div class=\"center\"></div>");
_39.push("<div class=\"hand\">");
_39.push("<div class=\"drag\"></div>");
_39.push("</div>");
var _3a=_3b();
for(var i=0;i<_3a.length;i++){
var _3c=_3a[i];
var cls="item f-column f-content-center";
if(_3c==_35){
cls+=" item-selected";
}
var _36=_3c/(_2d.selectingType=="hour"?12:60)*360*Math.PI/180;
var x=(_34-20)*Math.sin(_36);
var y=-(_34-20)*Math.cos(_36);
_36=parseFloat(_36).toFixed(4);
x=parseFloat(x).toFixed(4);
y=parseFloat(y).toFixed(4);
var _3d={transform:"translate("+x+"px,"+y+"px)"};
var _3d="transform:translate("+x+"px,"+y+"px)";
_39.push("<div class=\""+cls+"\" style=\""+_3d+"\">"+_3c+"</div>");
}
_39.push("</div>");
_30.html(_39.join(""));
_30.find(".clock").css(_38);
_30.find(".hand").css(_37);
function _3b(){
var _3e=[];
if(_2d.selectingType=="hour"){
for(var i=0;i<12;i++){
_3e.push(String(i));
}
_3e[0]="12";
}else{
for(var i=0;i<60;i+=5){
_3e.push(i<10?"0"+i:String(i));
}
_3e[0]="00";
}
return _3e;
};
};
$.fn.timepicker=function(_3f,_40){
if(typeof _3f=="string"){
var _41=$.fn.timepicker.methods[_3f];
if(_41){
return _41(this,_40);
}else{
return this.combo(_3f,_40);
}
}
_3f=_3f||{};
return this.each(function(){
var _42=$.data(this,"timepicker");
if(_42){
$.extend(_42.options,_3f);
}else{
$.data(this,"timepicker",{options:$.extend({},$.fn.timepicker.defaults,$.fn.timepicker.parseOptions(this),_3f)});
}
_1(this);
});
};
$.fn.timepicker.methods={options:function(jq){
var _43=jq.combo("options");
return $.extend($.data(jq[0],"timepicker").options,{width:_43.width,height:_43.height,originalValue:_43.originalValue,disabled:_43.disabled,readonly:_43.readonly});
},initValue:function(jq,_44){
return jq.each(function(){
var _45=$(this).timepicker("options");
_45.value=_44;
_13(this,_44);
if(_44){
_45.value=_14(this);
$(this).combo("initValue",_45.value).combo("setText",_45.value);
}
});
},setValue:function(jq,_46){
return jq.each(function(){
_f(this,_46);
});
},reset:function(jq){
return jq.each(function(){
var _47=$(this).timepicker("options");
$(this).timepicker("setValue",_47.originalValue);
});
}};
$.fn.timepicker.parseOptions=function(_48){
return $.extend({},$.fn.combo.parseOptions(_48),$.parser.parseOptions(_48,[]));
};
$.fn.timepicker.defaults=$.extend({},$.fn.combo.defaults,{closeText:"Close",okText:"Ok",buttons:[{text:function(_49){
return $(_49).timepicker("options").okText;
},handler:function(_4a){
$(_4a).timepicker("setValue",_14(_4a));
$(this).closest("div.combo-panel").panel("close");
}},{text:function(_4b){
return $(_4b).timepicker("options").closeText;
},handler:function(_4c){
$(this).closest("div.combo-panel").panel("close");
}}],editable:false,ampm:["am","pm"],value:"",selectingHour:12,selectingMinute:0,selectingType:"hour"});
})(jQuery);

