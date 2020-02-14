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
var _3=$.data(_2,"numberbox");
var _4=_3.options;
$(_2).addClass("numberbox-f").textbox(_4);
$(_2).textbox("textbox").css({imeMode:"disabled"});
$(_2).attr("numberboxName",$(_2).attr("textboxName"));
_3.numberbox=$(_2).next();
_3.numberbox.addClass("numberbox");
var _5=_4.parser.call(_2,_4.value);
var _6=_4.formatter.call(_2,_5);
$(_2).numberbox("initValue",_5).numberbox("setText",_6);
};
function _7(_8,_9){
var _a=$.data(_8,"numberbox");
var _b=_a.options;
_b.value=parseFloat(_9);
var _9=_b.parser.call(_8,_9);
var _c=_b.formatter.call(_8,_9);
_b.value=_9;
$(_8).textbox("setText",_c).textbox("setValue",_9);
_c=_b.formatter.call(_8,$(_8).textbox("getValue"));
$(_8).textbox("setText",_c);
};
$.fn.numberbox=function(_d,_e){
if(typeof _d=="string"){
var _f=$.fn.numberbox.methods[_d];
if(_f){
return _f(this,_e);
}else{
return this.textbox(_d,_e);
}
}
_d=_d||{};
return this.each(function(){
var _10=$.data(this,"numberbox");
if(_10){
$.extend(_10.options,_d);
}else{
_10=$.data(this,"numberbox",{options:$.extend({},$.fn.numberbox.defaults,$.fn.numberbox.parseOptions(this),_d)});
}
_1(this);
});
};
$.fn.numberbox.methods={options:function(jq){
var _11=jq.data("textbox")?jq.textbox("options"):{};
return $.extend($.data(jq[0],"numberbox").options,{width:_11.width,originalValue:_11.originalValue,disabled:_11.disabled,readonly:_11.readonly});
},cloneFrom:function(jq,_12){
return jq.each(function(){
$(this).textbox("cloneFrom",_12);
$.data(this,"numberbox",{options:$.extend(true,{},$(_12).numberbox("options"))});
$(this).addClass("numberbox-f");
});
},fix:function(jq){
return jq.each(function(){
var _13=$(this).numberbox("options");
_13.value=null;
var _14=_13.parser.call(this,$(this).numberbox("getText"));
$(this).numberbox("setValue",_14);
});
},setValue:function(jq,_15){
return jq.each(function(){
_7(this,_15);
});
},clear:function(jq){
return jq.each(function(){
$(this).textbox("clear");
$(this).numberbox("options").value="";
});
},reset:function(jq){
return jq.each(function(){
$(this).textbox("reset");
$(this).numberbox("setValue",$(this).numberbox("getValue"));
});
}};
$.fn.numberbox.parseOptions=function(_16){
var t=$(_16);
return $.extend({},$.fn.textbox.parseOptions(_16),$.parser.parseOptions(_16,["decimalSeparator","groupSeparator","suffix",{min:"number",max:"number",precision:"number"}]),{prefix:(t.attr("prefix")?t.attr("prefix"):undefined)});
};
$.fn.numberbox.defaults=$.extend({},$.fn.textbox.defaults,{inputEvents:{keypress:function(e){
var _17=e.data.target;
var _18=$(_17).numberbox("options");
return _18.filter.call(_17,e);
},blur:function(e){
$(e.data.target).numberbox("fix");
},keydown:function(e){
if(e.keyCode==13){
$(e.data.target).numberbox("fix");
}
}},min:null,max:null,precision:0,decimalSeparator:".",groupSeparator:"",prefix:"",suffix:"",filter:function(e){
var _19=$(this).numberbox("options");
var s=$(this).numberbox("getText");
if(e.metaKey||e.ctrlKey){
return true;
}
if($.inArray(String(e.which),["46","8","13","0"])>=0){
return true;
}
var tmp=$("<span></span>");
tmp.html(String.fromCharCode(e.which));
var c=tmp.text();
tmp.remove();
if(!c){
return true;
}
if(c=="-"&&_19.min!=null&&_19.min>=0){
return false;
}
if(c=="-"||c==_19.decimalSeparator){
return (s.indexOf(c)==-1)?true:false;
}else{
if(c==_19.groupSeparator){
return true;
}else{
if("0123456789".indexOf(c)>=0){
return true;
}else{
return false;
}
}
}
},formatter:function(_1a){
if(!_1a){
return _1a;
}
_1a=_1a+"";
var _1b=$(this).numberbox("options");
var s1=_1a,s2="";
var _1c=_1a.indexOf(".");
if(_1c>=0){
s1=_1a.substring(0,_1c);
s2=_1a.substring(_1c+1,_1a.length);
}
if(_1b.groupSeparator){
var p=/(\d+)(\d{3})/;
while(p.test(s1)){
s1=s1.replace(p,"$1"+_1b.groupSeparator+"$2");
}
}
if(s2){
return _1b.prefix+s1+_1b.decimalSeparator+s2+_1b.suffix;
}else{
return _1b.prefix+s1+_1b.suffix;
}
},parser:function(s){
s=s+"";
var _1d=$(this).numberbox("options");
if(_1d.prefix){
s=$.trim(s.replace(new RegExp("\\"+$.trim(_1d.prefix),"g"),""));
}
if(_1d.suffix){
s=$.trim(s.replace(new RegExp("\\"+$.trim(_1d.suffix),"g"),""));
}
if(parseFloat(s)!=_1d.value){
if(_1d.groupSeparator){
s=$.trim(s.replace(new RegExp("\\"+_1d.groupSeparator,"g"),""));
}
if(_1d.decimalSeparator){
s=$.trim(s.replace(new RegExp("\\"+_1d.decimalSeparator,"g"),"."));
}
s=s.replace(/\s/g,"");
}
var val=parseFloat(s).toFixed(_1d.precision);
if(isNaN(val)){
val="";
}else{
if(typeof (_1d.min)=="number"&&val<_1d.min){
val=_1d.min.toFixed(_1d.precision);
}else{
if(typeof (_1d.max)=="number"&&val>_1d.max){
val=_1d.max.toFixed(_1d.precision);
}
}
}
return val;
}});
})(jQuery);

