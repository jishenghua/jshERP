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
var _3=$(_2).data("maskedbox");
var _4=_3.options;
$(_2).textbox(_4);
$(_2).maskedbox("initValue",_4.value);
};
function _5(_6,_7){
var _8=$(_6).maskedbox("options");
var tt=(_7||$(_6).maskedbox("getText")||"").split("");
var vv=[];
for(var i=0;i<_8.mask.length;i++){
if(_8.masks[_8.mask[i]]){
var t=tt[i];
vv.push(t!=_8.promptChar?t:" ");
}
}
return vv.join("");
};
function _9(_a,_b){
var _c=$(_a).maskedbox("options");
var cc=_b.split("");
var tt=[];
for(var i=0;i<_c.mask.length;i++){
var m=_c.mask[i];
var r=_c.masks[m];
if(r){
var c=cc.shift();
if(c!=undefined){
var d=new RegExp(r,"i");
if(d.test(c)){
tt.push(c);
continue;
}
}
tt.push(_c.promptChar);
}else{
tt.push(m);
}
}
return tt.join("");
};
function _d(_e,c){
var _f=$(_e).maskedbox("options");
var _10=$(_e).maskedbox("getSelectionRange");
var _11=_12(_e,_10.start);
var end=_12(_e,_10.end);
if(_11!=-1){
var r=new RegExp(_f.masks[_f.mask[_11]],"i");
if(r.test(c)){
var vv=_5(_e).split("");
var _13=_11-_14(_e,_11);
var _15=end-_14(_e,end);
vv.splice(_13,_15-_13,c);
$(_e).maskedbox("setValue",_9(_e,vv.join("")));
_11=_12(_e,++_11);
$(_e).maskedbox("setSelectionRange",{start:_11,end:_11});
}
}
};
function _16(_17,_18){
var _19=$(_17).maskedbox("options");
var vv=_5(_17).split("");
var _1a=$(_17).maskedbox("getSelectionRange");
if(_1a.start==_1a.end){
if(_18){
var _1b=_1c(_17,_1a.start);
}else{
var _1b=_12(_17,_1a.start);
}
var _1d=_1b-_14(_17,_1b);
if(_1d>=0){
vv.splice(_1d,1);
}
}else{
var _1b=_12(_17,_1a.start);
var end=_1c(_17,_1a.end);
var _1d=_1b-_14(_17,_1b);
var _1e=end-_14(_17,end);
vv.splice(_1d,_1e-_1d+1);
}
$(_17).maskedbox("setValue",_9(_17,vv.join("")));
$(_17).maskedbox("setSelectionRange",{start:_1b,end:_1b});
};
function _14(_1f,pos){
var _20=$(_1f).maskedbox("options");
var _21=0;
if(pos>=_20.mask.length){
pos--;
}
for(var i=pos;i>=0;i--){
if(_20.masks[_20.mask[i]]==undefined){
_21++;
}
}
return _21;
};
function _12(_22,pos){
var _23=$(_22).maskedbox("options");
var m=_23.mask[pos];
var r=_23.masks[m];
while(pos<_23.mask.length&&!r){
pos++;
m=_23.mask[pos];
r=_23.masks[m];
}
return pos;
};
function _1c(_24,pos){
var _25=$(_24).maskedbox("options");
var m=_25.mask[--pos];
var r=_25.masks[m];
while(pos>=0&&!r){
pos--;
m=_25.mask[pos];
r=_25.masks[m];
}
return pos<0?0:pos;
};
function _26(e){
if(e.metaKey||e.ctrlKey){
return;
}
var _27=e.data.target;
var _28=$(_27).maskedbox("options");
var _29=[9,13,35,36,37,39];
if($.inArray(e.keyCode,_29)!=-1){
return true;
}
if(e.keyCode>=96&&e.keyCode<=105){
e.keyCode-=48;
}
var c=String.fromCharCode(e.keyCode);
if(e.keyCode>=65&&e.keyCode<=90&&!e.shiftKey){
c=c.toLowerCase();
}else{
if(e.keyCode==189){
c="-";
}else{
if(e.keyCode==187){
c="+";
}else{
if(e.keyCode==190){
c=".";
}
}
}
}
if(e.keyCode==8){
_16(_27,true);
}else{
if(e.keyCode==46){
_16(_27,false);
}else{
_d(_27,c);
}
}
return false;
};
$.extend($.fn.textbox.methods,{inputMask:function(jq,_2a){
return jq.each(function(){
var _2b=this;
var _2c=$.extend({},$.fn.maskedbox.defaults,_2a);
$.data(_2b,"maskedbox",{options:_2c});
var _2d=$(_2b).textbox("textbox");
_2d._unbind(".maskedbox");
for(var _2e in _2c.inputEvents){
_2d._bind(_2e+".maskedbox",{target:_2b},_2c.inputEvents[_2e]);
}
});
}});
$.fn.maskedbox=function(_2f,_30){
if(typeof _2f=="string"){
var _31=$.fn.maskedbox.methods[_2f];
if(_31){
return _31(this,_30);
}else{
return this.textbox(_2f,_30);
}
}
_2f=_2f||{};
return this.each(function(){
var _32=$.data(this,"maskedbox");
if(_32){
$.extend(_32.options,_2f);
}else{
$.data(this,"maskedbox",{options:$.extend({},$.fn.maskedbox.defaults,$.fn.maskedbox.parseOptions(this),_2f)});
}
_1(this);
});
};
$.fn.maskedbox.methods={options:function(jq){
var _33=jq.textbox("options");
return $.extend($.data(jq[0],"maskedbox").options,{width:_33.width,value:_33.value,originalValue:_33.originalValue,disabled:_33.disabled,readonly:_33.readonly});
},initValue:function(jq,_34){
return jq.each(function(){
_34=_9(this,_5(this,_34));
$(this).textbox("initValue",_34);
});
},setValue:function(jq,_35){
return jq.each(function(){
_35=_9(this,_5(this,_35));
$(this).textbox("setValue",_35);
});
}};
$.fn.maskedbox.parseOptions=function(_36){
var t=$(_36);
return $.extend({},$.fn.textbox.parseOptions(_36),$.parser.parseOptions(_36,["mask","promptChar"]),{});
};
$.fn.maskedbox.defaults=$.extend({},$.fn.textbox.defaults,{mask:"",promptChar:"_",masks:{"9":"[0-9]","a":"[a-zA-Z]","*":"[0-9a-zA-Z]"},inputEvents:{keydown:_26}});
})(jQuery);

