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
var _3=$.data(_2,"timespinner").options;
$(_2).addClass("timespinner-f").spinner(_3);
var _4=_3.formatter.call(_2,_3.parser.call(_2,_3.value));
$(_2).timespinner("initValue",_4);
};
function _5(e){
var _6=e.data.target;
var _7=$.data(_6,"timespinner").options;
var _8=$(_6).timespinner("getSelectionStart");
for(var i=0;i<_7.selections.length;i++){
var _9=_7.selections[i];
if(_8>=_9[0]&&_8<=_9[1]){
_a(_6,i);
return;
}
}
};
function _a(_b,_c){
var _d=$.data(_b,"timespinner").options;
if(_c!=undefined){
_d.highlight=_c;
}
var _e=_d.selections[_d.highlight];
if(_e){
var tb=$(_b).timespinner("textbox");
$(_b).timespinner("setSelectionRange",{start:_e[0],end:_e[1]});
tb.focus();
}
};
function _f(_10,_11){
var _12=$.data(_10,"timespinner").options;
var _11=_12.parser.call(_10,_11);
var _13=_12.formatter.call(_10,_11);
$(_10).spinner("setValue",_13);
};
function _14(_15,_16){
var _17=$.data(_15,"timespinner").options;
var s=$(_15).timespinner("getValue");
var _18=_17.selections[_17.highlight];
var s1=s.substring(0,_18[0]);
var s2=s.substring(_18[0],_18[1]);
var s3=s.substring(_18[1]);
if(s2==_17.ampm[0]){
s2=_17.ampm[1];
}else{
if(s2==_17.ampm[1]){
s2=_17.ampm[0];
}else{
s2=parseInt(s2,10)||0;
if(_17.selections.length-4==_17.highlight&&_17.hour12){
if(s2==12){
s2=0;
}else{
if(s2==11&&!_16){
var tmp=s3.replace(_17.ampm[0],_17.ampm[1]);
if(s3!=tmp){
s3=tmp;
}else{
s3=s3.replace(_17.ampm[1],_17.ampm[0]);
}
}
}
}
s2=s2+_17.increment*(_16?-1:1);
}
}
var v=s1+s2+s3;
$(_15).timespinner("setValue",v);
_a(_15);
};
$.fn.timespinner=function(_19,_1a){
if(typeof _19=="string"){
var _1b=$.fn.timespinner.methods[_19];
if(_1b){
return _1b(this,_1a);
}else{
return this.spinner(_19,_1a);
}
}
_19=_19||{};
return this.each(function(){
var _1c=$.data(this,"timespinner");
if(_1c){
$.extend(_1c.options,_19);
}else{
$.data(this,"timespinner",{options:$.extend({},$.fn.timespinner.defaults,$.fn.timespinner.parseOptions(this),_19)});
}
_1(this);
});
};
$.fn.timespinner.methods={options:function(jq){
var _1d=jq.data("spinner")?jq.spinner("options"):{};
return $.extend($.data(jq[0],"timespinner").options,{width:_1d.width,value:_1d.value,originalValue:_1d.originalValue,disabled:_1d.disabled,readonly:_1d.readonly});
},setValue:function(jq,_1e){
return jq.each(function(){
_f(this,_1e);
});
},getHours:function(jq){
var _1f=$.data(jq[0],"timespinner").options;
var _20=_1f.parser.call(jq[0],jq.timespinner("getValue"));
return _20?_20.getHours():null;
},getMinutes:function(jq){
var _21=$.data(jq[0],"timespinner").options;
var _22=_21.parser.call(jq[0],jq.timespinner("getValue"));
return _22?_22.getMinutes():null;
},getSeconds:function(jq){
var _23=$.data(jq[0],"timespinner").options;
var _24=_23.parser.call(jq[0],jq.timespinner("getValue"));
return _24?_24.getSeconds():null;
}};
$.fn.timespinner.parseOptions=function(_25){
return $.extend({},$.fn.spinner.parseOptions(_25),$.parser.parseOptions(_25,["separator",{hour12:"boolean",showSeconds:"boolean",highlight:"number"}]));
};
$.fn.timespinner.defaults=$.extend({},$.fn.spinner.defaults,{inputEvents:$.extend({},$.fn.spinner.defaults.inputEvents,{click:function(e){
_5.call(this,e);
},blur:function(e){
var t=$(e.data.target);
t.timespinner("setValue",t.timespinner("getText"));
},keydown:function(e){
if(e.keyCode==13){
var t=$(e.data.target);
t.timespinner("setValue",t.timespinner("getText"));
}
}}),formatter:function(_26){
if(!_26){
return "";
}
var _27=$(this).timespinner("options");
var _28=_26.getHours();
var _29=_26.getMinutes();
var _2a=_26.getSeconds();
var _2b="";
if(_27.hour12){
_2b=_28>=12?_27.ampm[1]:_27.ampm[0];
_28=_28%12;
if(_28==0){
_28=12;
}
}
var tt=[_2c(_28),_2c(_29)];
if(_27.showSeconds){
tt.push(_2c(_2a));
}
var s=tt.join(_27.separator)+" "+_2b;
return $.trim(s);
function _2c(_2d){
return (_2d<10?"0":"")+_2d;
};
},parser:function(s){
var _2e=$(this).timespinner("options");
var _2f=_30(s);
if(_2f){
var min=_30(_2e.min);
var max=_30(_2e.max);
if(min&&min>_2f){
_2f=min;
}
if(max&&max<_2f){
_2f=max;
}
}
return _2f;
function _30(s){
if(!s){
return null;
}
var ss=s.split(" ");
var tt=ss[0].split(_2e.separator);
var _31=parseInt(tt[0],10)||0;
var _32=parseInt(tt[1],10)||0;
var _33=parseInt(tt[2],10)||0;
if(_2e.hour12){
var _34=ss[1];
if(_34==_2e.ampm[1]&&_31<12){
_31+=12;
}else{
if(_34==_2e.ampm[0]&&_31==12){
_31-=12;
}
}
}
return new Date(1900,0,0,_31,_32,_33);
};
},selections:[[0,2],[3,5],[6,8],[9,11]],separator:":",showSeconds:false,highlight:0,hour12:false,ampm:["AM","PM"],spin:function(_35){
_14(this,_35);
}});
})(jQuery);

