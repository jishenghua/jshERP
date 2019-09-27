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
function _1(_2,_3){
_3=_3||{};
var _4={};
if(_3.onSubmit){
if(_3.onSubmit.call(_2,_4)==false){
return;
}
}
var _5=$(_2);
if(_3.url){
_5.attr("action",_3.url);
}
var _6="easyui_frame_"+(new Date().getTime());
var _7=$("<iframe id="+_6+" name="+_6+"></iframe>").attr("src",window.ActiveXObject?"javascript:false":"about:blank").css({position:"absolute",top:-1000,left:-1000});
var t=_5.attr("target"),a=_5.attr("action");
_5.attr("target",_6);
var _8=$();
try{
_7.appendTo("body");
_7.bind("load",cb);
for(var n in _4){
var f=$("<input type=\"hidden\" name=\""+n+"\">").val(_4[n]).appendTo(_5);
_8=_8.add(f);
}
_9();
_5[0].submit();
}
finally{
_5.attr("action",a);
t?_5.attr("target",t):_5.removeAttr("target");
_8.remove();
}
function _9(){
var f=$("#"+_6);
if(!f.length){
return;
}
try{
var s=f.contents()[0].readyState;
if(s&&s.toLowerCase()=="uninitialized"){
setTimeout(_9,100);
}
}
catch(e){
cb();
}
};
var _a=10;
function cb(){
var _b=$("#"+_6);
if(!_b.length){
return;
}
_b.unbind();
var _c="";
try{
var _d=_b.contents().find("body");
_c=_d.html();
if(_c==""){
if(--_a){
setTimeout(cb,100);
return;
}
}
var ta=_d.find(">textarea");
if(ta.length){
_c=ta.val();
}else{
var _e=_d.find(">pre");
if(_e.length){
_c=_e.html();
}
}
}
catch(e){
}
if(_3.success){
_3.success(_c);
}
setTimeout(function(){
_b.unbind();
_b.remove();
},100);
};
};
function _f(_10,_11){
if(!$.data(_10,"form")){
$.data(_10,"form",{options:$.extend({},$.fn.form.defaults)});
}
var _12=$.data(_10,"form").options;
if(typeof _11=="string"){
var _13={};
if(_12.onBeforeLoad.call(_10,_13)==false){
return;
}
$.ajax({url:_11,data:_13,dataType:"json",success:function(_14){
_15(_14);
},error:function(){
_12.onLoadError.apply(_10,arguments);
}});
}else{
_15(_11);
}
function _15(_16){
var _17=$(_10);
for(var _18 in _16){
var val=_16[_18];
var rr=_19(_18,val);
if(!rr.length){
var _1a=_1b(_18,val);
if(!_1a){
$("input[name=\""+_18+"\"]",_17).val(val);
$("textarea[name=\""+_18+"\"]",_17).val(val);
$("select[name=\""+_18+"\"]",_17).val(val);
}
}
_1c(_18,val);
}
_12.onLoadSuccess.call(_10,_16);
_28(_10);
};
function _19(_1d,val){
var rr=$(_10).find("input[name=\""+_1d+"\"][type=radio], input[name=\""+_1d+"\"][type=checkbox]");
rr._propAttr("checked",false);
rr.each(function(){
var f=$(this);
if(f.val()==String(val)||$.inArray(f.val(),$.isArray(val)?val:[val])>=0){
f._propAttr("checked",true);
}
});
return rr;
};
function _1b(_1e,val){
var _1f=0;
var pp=["numberbox","slider"];
for(var i=0;i<pp.length;i++){
var p=pp[i];
var f=$(_10).find("input["+p+"Name=\""+_1e+"\"]");
if(f.length){
f[p]("setValue",val);
_1f+=f.length;
}
}
return _1f;
};
function _1c(_20,val){
var _21=$(_10);
var cc=["combobox","combotree","combogrid","datetimebox","datebox","combo"];
var c=_21.find("[comboName=\""+_20+"\"]");
if(c.length){
for(var i=0;i<cc.length;i++){
var _22=cc[i];
if(c.hasClass(_22+"-f")){
if(c[_22]("options").multiple){
c[_22]("setValues",val);
}else{
c[_22]("setValue",val);
}
return;
}
}
}
};
};
function _23(_24){
$("input,select,textarea",_24).each(function(){
var t=this.type,tag=this.tagName.toLowerCase();
if(t=="text"||t=="hidden"||t=="password"||tag=="textarea"){
this.value="";
}else{
if(t=="file"){
var _25=$(this);
_25.after(_25.clone().val(""));
_25.remove();
}else{
if(t=="checkbox"||t=="radio"){
this.checked=false;
}else{
if(tag=="select"){
this.selectedIndex=-1;
}
}
}
}
});
var t=$(_24);
var _26=["combo","combobox","combotree","combogrid","slider"];
for(var i=0;i<_26.length;i++){
var _27=_26[i];
var r=t.find("."+_27+"-f");
if(r.length&&r[_27]){
r[_27]("clear");
}
}
_28(_24);
};
function _29(_2a){
_2a.reset();
var t=$(_2a);
var _2b=["combo","combobox","combotree","combogrid","datebox","datetimebox","spinner","timespinner","numberbox","numberspinner","slider"];
for(var i=0;i<_2b.length;i++){
var _2c=_2b[i];
var r=t.find("."+_2c+"-f");
if(r.length&&r[_2c]){
r[_2c]("reset");
}
}
_28(_2a);
};
function _2d(_2e){
var _2f=$.data(_2e,"form").options;
var _30=$(_2e);
_30.unbind(".form").bind("submit.form",function(){
setTimeout(function(){
_1(_2e,_2f);
},0);
return false;
});
};
function _28(_31){
if($.fn.validatebox){
var t=$(_31);
t.find(".validatebox-text:not(:disabled)").validatebox("validate");
var _32=t.find(".validatebox-invalid");
_32.filter(":not(:disabled):first").focus();
return _32.length==0;
}
return true;
};
function _33(_34,_35){
$(_34).find(".validatebox-text:not(:disabled)").validatebox(_35?"disableValidation":"enableValidation");
};
$.fn.form=function(_36,_37){
if(typeof _36=="string"){
return $.fn.form.methods[_36](this,_37);
}
_36=_36||{};
return this.each(function(){
if(!$.data(this,"form")){
$.data(this,"form",{options:$.extend({},$.fn.form.defaults,_36)});
}
_2d(this);
});
};
$.fn.form.methods={submit:function(jq,_38){
return jq.each(function(){
_1(this,$.extend({},$.fn.form.defaults,_38||{}));
});
},load:function(jq,_39){
return jq.each(function(){
_f(this,_39);
});
},clear:function(jq){
return jq.each(function(){
_23(this);
});
},reset:function(jq){
return jq.each(function(){
_29(this);
});
},validate:function(jq){
return _28(jq[0]);
},disableValidation:function(jq){
return jq.each(function(){
_33(this,true);
});
},enableValidation:function(jq){
return jq.each(function(){
_33(this,false);
});
}};
$.fn.form.defaults={url:null,onSubmit:function(_3a){
return $(this).form("validate");
},success:function(_3b){
},onBeforeLoad:function(_3c){
},onLoadSuccess:function(_3d){
},onLoadError:function(){
}};
})(jQuery);

