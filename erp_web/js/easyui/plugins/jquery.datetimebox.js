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
var _3=$.data(_2,"datetimebox");
var _4=_3.options;
$(_2).datebox($.extend({},_4,{onShowPanel:function(){
var _5=$(this).datetimebox("getValue");
_d(this,_5,true);
_4.onShowPanel.call(this);
},formatter:$.fn.datebox.defaults.formatter,parser:$.fn.datebox.defaults.parser}));
$(_2).removeClass("datebox-f").addClass("datetimebox-f");
$(_2).datebox("calendar").calendar({onSelect:function(_6){
_4.onSelect.call(this.target,_6);
}});
if(!_3.spinner){
var _7=$(_2).datebox("panel");
var p=$("<div style=\"padding:2px\"><input></div>").insertAfter(_7.children("div.datebox-calendar-inner"));
_3.spinner=p.children("input");
}
_3.spinner.timespinner({width:_4.spinnerWidth,showSeconds:_4.showSeconds,separator:_4.timeSeparator,hour12:_4.hour12});
$(_2).datetimebox("initValue",_4.value);
};
function _8(_9){
var c=$(_9).datetimebox("calendar");
var t=$(_9).datetimebox("spinner");
var _a=c.calendar("options").current;
return new Date(_a.getFullYear(),_a.getMonth(),_a.getDate(),t.timespinner("getHours"),t.timespinner("getMinutes"),t.timespinner("getSeconds"));
};
function _b(_c,q){
_d(_c,q,true);
};
function _e(_f){
var _10=$.data(_f,"datetimebox").options;
var _11=_8(_f);
_d(_f,_10.formatter.call(_f,_11));
$(_f).combo("hidePanel");
};
function _d(_12,_13,_14){
var _15=$.data(_12,"datetimebox").options;
$(_12).combo("setValue",_13);
if(!_14){
if(_13){
var _16=_15.parser.call(_12,_13);
$(_12).combo("setText",_15.formatter.call(_12,_16));
$(_12).combo("setValue",_15.formatter.call(_12,_16));
}else{
$(_12).combo("setText",_13);
}
}
var _16=_15.parser.call(_12,_13);
$(_12).datetimebox("calendar").calendar("moveTo",_16);
$(_12).datetimebox("spinner").timespinner("setValue",_17(_16));
function _17(_18){
function _19(_1a){
return (_1a<10?"0":"")+_1a;
};
var tt=[_19(_18.getHours()),_19(_18.getMinutes())];
if(_15.showSeconds){
tt.push(_19(_18.getSeconds()));
}
return tt.join($(_12).datetimebox("spinner").timespinner("options").separator);
};
};
$.fn.datetimebox=function(_1b,_1c){
if(typeof _1b=="string"){
var _1d=$.fn.datetimebox.methods[_1b];
if(_1d){
return _1d(this,_1c);
}else{
return this.datebox(_1b,_1c);
}
}
_1b=_1b||{};
return this.each(function(){
var _1e=$.data(this,"datetimebox");
if(_1e){
$.extend(_1e.options,_1b);
}else{
$.data(this,"datetimebox",{options:$.extend({},$.fn.datetimebox.defaults,$.fn.datetimebox.parseOptions(this),_1b)});
}
_1(this);
});
};
$.fn.datetimebox.methods={options:function(jq){
var _1f=jq.datebox("options");
return $.extend($.data(jq[0],"datetimebox").options,{originalValue:_1f.originalValue,disabled:_1f.disabled,readonly:_1f.readonly});
},cloneFrom:function(jq,_20){
return jq.each(function(){
$(this).datebox("cloneFrom",_20);
$.data(this,"datetimebox",{options:$.extend(true,{},$(_20).datetimebox("options")),spinner:$(_20).datetimebox("spinner")});
$(this).removeClass("datebox-f").addClass("datetimebox-f");
});
},spinner:function(jq){
return $.data(jq[0],"datetimebox").spinner;
},initValue:function(jq,_21){
return jq.each(function(){
var _22=$(this).datetimebox("options");
var _23=_22.value;
if(_23){
var _24=_22.parser.call(this,_23);
_23=_22.formatter.call(this,_24);
$(this).datetimebox("calendar").calendar("moveTo",_24);
}
$(this).combo("initValue",_23).combo("setText",_23);
});
},setValue:function(jq,_25){
return jq.each(function(){
_d(this,_25);
});
},reset:function(jq){
return jq.each(function(){
var _26=$(this).datetimebox("options");
$(this).datetimebox("setValue",_26.originalValue);
});
},setDate:function(jq,_27){
return jq.each(function(){
var _28=$(this).datetimebox("options");
$(this).datetimebox("calendar").calendar("moveTo",_27);
_d(this,_27?_28.formatter.call(this,_27):"");
});
},getDate:function(jq){
if(jq.datetimebox("getValue")){
return jq.datetimebox("calendar").calendar("options").current;
}else{
return null;
}
}};
$.fn.datetimebox.parseOptions=function(_29){
var t=$(_29);
return $.extend({},$.fn.datebox.parseOptions(_29),$.parser.parseOptions(_29,["timeSeparator","spinnerWidth",{showSeconds:"boolean"}]));
};
$.fn.datetimebox.defaults=$.extend({},$.fn.datebox.defaults,{spinnerWidth:"100%",showSeconds:true,timeSeparator:":",hour12:false,panelEvents:{mousedown:function(e){
}},keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
_e(this);
},query:function(q,e){
_b(this,q);
}},buttons:[{text:function(_2a){
return $(_2a).datetimebox("options").currentText;
},handler:function(_2b){
var _2c=$(_2b).datetimebox("options");
_d(_2b,_2c.formatter.call(_2b,new Date()));
$(_2b).datetimebox("hidePanel");
}},{text:function(_2d){
return $(_2d).datetimebox("options").okText;
},handler:function(_2e){
_e(_2e);
}},{text:function(_2f){
return $(_2f).datetimebox("options").closeText;
},handler:function(_30){
$(_30).datetimebox("hidePanel");
}}],formatter:function(_31){
if(!_31){
return "";
}
return $.fn.datebox.defaults.formatter.call(this,_31)+" "+$.fn.timespinner.defaults.formatter.call($(this).datetimebox("spinner")[0],_31);
},parser:function(s){
s=$.trim(s);
if(!s){
return new Date();
}
var dt=s.split(" ");
var _32=$.fn.datebox.defaults.parser.call(this,dt[0]);
if(dt.length<2){
return _32;
}
var _33=$.fn.timespinner.defaults.parser.call($(this).datetimebox("spinner")[0],dt[1]+(dt[2]?" "+dt[2]:""));
return new Date(_32.getFullYear(),_32.getMonth(),_32.getDate(),_33.getHours(),_33.getMinutes(),_33.getSeconds());
}});
})(jQuery);

