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
var _3=$.data(_2,"passwordbox");
var _4=_3.options;
var _5=$.extend(true,[],_4.icons);
if(_4.showEye){
_5.push({iconCls:"passwordbox-open",handler:function(e){
_4.revealed=!_4.revealed;
_6(_2);
}});
}
$(_2).addClass("passwordbox-f").textbox($.extend({},_4,{icons:_5}));
_6(_2);
};
function _7(_8,_9,_a){
var _b=$(_8).data("passwordbox");
var t=$(_8);
var _c=t.passwordbox("options");
if(_c.revealed){
t.textbox("setValue",_9);
return;
}
_b.converting=true;
var _d=unescape(_c.passwordChar);
var cc=_9.split("");
var vv=t.passwordbox("getValue").split("");
for(var i=0;i<cc.length;i++){
var c=cc[i];
if(c!=vv[i]){
if(c!=_d){
vv.splice(i,0,c);
}
}
}
var _e=t.passwordbox("getSelectionStart");
if(cc.length<vv.length){
vv.splice(_e,vv.length-cc.length,"");
}
for(var i=0;i<cc.length;i++){
if(_a||i!=_e-1){
cc[i]=_d;
}
}
t.textbox("setValue",vv.join(""));
t.textbox("setText",cc.join(""));
t.textbox("setSelectionRange",{start:_e,end:_e});
setTimeout(function(){
_b.converting=false;
},0);
};
function _6(_f,_10){
var t=$(_f);
var _11=t.passwordbox("options");
var _12=t.next().find(".passwordbox-open");
var _13=unescape(_11.passwordChar);
_10=_10==undefined?t.textbox("getValue"):_10;
t.textbox("setValue",_10);
t.textbox("setText",_11.revealed?_10:_10.replace(/./ig,_13));
_11.revealed?_12.addClass("passwordbox-close"):_12.removeClass("passwordbox-close");
};
function _14(e){
var _15=e.data.target;
var t=$(e.data.target);
var _16=t.data("passwordbox");
var _17=t.data("passwordbox").options;
_16.checking=true;
_16.value=t.passwordbox("getText");
(function(){
if(_16.checking){
var _18=t.passwordbox("getText");
if(_16.value!=_18){
_16.value=_18;
if(_16.lastTimer){
clearTimeout(_16.lastTimer);
_16.lastTimer=undefined;
}
_7(_15,_18);
_16.lastTimer=setTimeout(function(){
_7(_15,t.passwordbox("getText"),true);
_16.lastTimer=undefined;
},_17.lastDelay);
}
setTimeout(arguments.callee,_17.checkInterval);
}
})();
};
function _19(e){
var _1a=e.data.target;
var _1b=$(_1a).data("passwordbox");
_1b.checking=false;
if(_1b.lastTimer){
clearTimeout(_1b.lastTimer);
_1b.lastTimer=undefined;
}
_6(_1a);
};
$.fn.passwordbox=function(_1c,_1d){
if(typeof _1c=="string"){
var _1e=$.fn.passwordbox.methods[_1c];
if(_1e){
return _1e(this,_1d);
}else{
return this.textbox(_1c,_1d);
}
}
_1c=_1c||{};
return this.each(function(){
var _1f=$.data(this,"passwordbox");
if(_1f){
$.extend(_1f.options,_1c);
}else{
_1f=$.data(this,"passwordbox",{options:$.extend({},$.fn.passwordbox.defaults,$.fn.passwordbox.parseOptions(this),_1c)});
}
_1(this);
});
};
$.fn.passwordbox.methods={options:function(jq){
return $.data(jq[0],"passwordbox").options;
},setValue:function(jq,_20){
return jq.each(function(){
_6(this,_20);
});
},clear:function(jq){
return jq.each(function(){
_6(this,"");
});
},reset:function(jq){
return jq.each(function(){
$(this).textbox("reset");
_6(this);
});
},showPassword:function(jq){
return jq.each(function(){
var _21=$(this).passwordbox("options");
_21.revealed=true;
_6(this);
});
},hidePassword:function(jq){
return jq.each(function(){
var _22=$(this).passwordbox("options");
_22.revealed=false;
_6(this);
});
}};
$.fn.passwordbox.parseOptions=function(_23){
return $.extend({},$.fn.textbox.parseOptions(_23),$.parser.parseOptions(_23,["passwordChar",{checkInterval:"number",lastDelay:"number",revealed:"boolean",showEye:"boolean"}]));
};
$.fn.passwordbox.defaults=$.extend({},$.fn.textbox.defaults,{passwordChar:"%u25CF",checkInterval:200,lastDelay:500,revealed:false,showEye:true,inputEvents:{focus:_14,blur:_19,keydown:function(e){
var _24=$(e.data.target).data("passwordbox");
return !_24.converting;
}},val:function(_25){
return $(_25).parent().prev().passwordbox("getValue");
}});
})(jQuery);

