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
function _1(_2,_3){
var _4=$.data(_2,"form").options;
$.extend(_4,_3||{});
var _5=$.extend({},_4.queryParams);
if(_4.onSubmit.call(_2,_5)==false){
return;
}
var _6=$(_2).find(".textbox-text:focus");
_6.triggerHandler("blur");
_6.focus();
var _7=null;
if(_4.dirty){
var ff=[];
$.map(_4.dirtyFields,function(f){
if($(f).hasClass("textbox-f")){
$(f).next().find(".textbox-value").each(function(){
ff.push(this);
});
}else{
ff.push(f);
}
});
_7=$(_2).find("input[name]:enabled,textarea[name]:enabled,select[name]:enabled").filter(function(){
return $.inArray(this,ff)==-1;
});
_7._propAttr("disabled",true);
}
if(_4.ajax){
if(_4.iframe){
_8(_2,_5);
}else{
if(window.FormData!==undefined){
_9(_2,_5);
}else{
_8(_2,_5);
}
}
}else{
$(_2).submit();
}
if(_4.dirty){
_7._propAttr("disabled",false);
}
};
function _8(_a,_b){
var _c=$.data(_a,"form").options;
var _d="easyui_frame_"+(new Date().getTime());
var _e=$("<iframe id="+_d+" name="+_d+"></iframe>").appendTo("body");
_e.attr("src",window.ActiveXObject?"javascript:false":"about:blank");
_e.css({position:"absolute",top:-1000,left:-1000});
_e.bind("load",cb);
_f(_b);
function _f(_10){
var _11=$(_a);
if(_c.url){
_11.attr("action",_c.url);
}
var t=_11.attr("target"),a=_11.attr("action");
_11.attr("target",_d);
var _12=$();
try{
for(var n in _10){
var _13=$("<input type=\"hidden\" name=\""+n+"\">").val(_10[n]).appendTo(_11);
_12=_12.add(_13);
}
_14();
_11[0].submit();
}
finally{
_11.attr("action",a);
t?_11.attr("target",t):_11.removeAttr("target");
_12.remove();
}
};
function _14(){
var f=$("#"+_d);
if(!f.length){
return;
}
try{
var s=f.contents()[0].readyState;
if(s&&s.toLowerCase()=="uninitialized"){
setTimeout(_14,100);
}
}
catch(e){
cb();
}
};
var _15=10;
function cb(){
var f=$("#"+_d);
if(!f.length){
return;
}
f.unbind();
var _16="";
try{
var _17=f.contents().find("body");
_16=_17.html();
if(_16==""){
if(--_15){
setTimeout(cb,100);
return;
}
}
var ta=_17.find(">textarea");
if(ta.length){
_16=ta.val();
}else{
var pre=_17.find(">pre");
if(pre.length){
_16=pre.html();
}
}
}
catch(e){
}
_c.success.call(_a,_16);
setTimeout(function(){
f.unbind();
f.remove();
},100);
};
};
function _9(_18,_19){
var _1a=$.data(_18,"form").options;
var _1b=new FormData($(_18)[0]);
for(var _1c in _19){
_1b.append(_1c,_19[_1c]);
}
$.ajax({url:_1a.url,type:"post",xhr:function(){
var xhr=$.ajaxSettings.xhr();
if(xhr.upload){
xhr.upload.addEventListener("progress",function(e){
if(e.lengthComputable){
var _1d=e.total;
var _1e=e.loaded||e.position;
var _1f=Math.ceil(_1e*100/_1d);
_1a.onProgress.call(_18,_1f);
}
},false);
}
return xhr;
},data:_1b,dataType:"html",cache:false,contentType:false,processData:false,complete:function(res){
_1a.success.call(_18,res.responseText);
}});
};
function _20(_21,_22){
var _23=$.data(_21,"form").options;
if(typeof _22=="string"){
var _24={};
if(_23.onBeforeLoad.call(_21,_24)==false){
return;
}
$.ajax({url:_22,data:_24,dataType:"json",success:function(_25){
_26(_25);
},error:function(){
_23.onLoadError.apply(_21,arguments);
}});
}else{
_26(_22);
}
function _26(_27){
var _28=$(_21);
for(var _29 in _27){
var val=_27[_29];
if(!_2a(_29,val)){
if(!_2b(_29,val)){
_28.find("input[name=\""+_29+"\"]").val(val);
_28.find("textarea[name=\""+_29+"\"]").val(val);
_28.find("select[name=\""+_29+"\"]").val(val);
}
}
}
_23.onLoadSuccess.call(_21,_27);
_28.form("validate");
};
function _2a(_2c,val){
var _2d=["switchbutton","radiobutton","checkbox"];
for(var i=0;i<_2d.length;i++){
var _2e=_2d[i];
var cc=$(_21).find("["+_2e+"Name=\""+_2c+"\"]");
if(cc.length){
cc[_2e]("uncheck");
cc.each(function(){
if(_2f($(this)[_2e]("options").value,val)){
$(this)[_2e]("check");
}
});
return true;
}
}
var cc=$(_21).find("input[name=\""+_2c+"\"][type=radio], input[name=\""+_2c+"\"][type=checkbox]");
if(cc.length){
cc._propAttr("checked",false);
cc.each(function(){
if(_2f($(this).val(),val)){
$(this)._propAttr("checked",true);
}
});
return true;
}
return false;
};
function _2f(v,val){
if(v==String(val)||$.inArray(v,$.isArray(val)?val:[val])>=0){
return true;
}else{
return false;
}
};
function _2b(_30,val){
var _31=$(_21).find("[textboxName=\""+_30+"\"],[sliderName=\""+_30+"\"]");
if(_31.length){
for(var i=0;i<_23.fieldTypes.length;i++){
var _32=_23.fieldTypes[i];
var _33=_31.data(_32);
if(_33){
if(_33.options.multiple||_33.options.range){
_31[_32]("setValues",val);
}else{
_31[_32]("setValue",val);
}
return true;
}
}
}
return false;
};
};
function _34(_35){
$("input,select,textarea",_35).each(function(){
if($(this).hasClass("textbox-value")){
return;
}
var t=this.type,tag=this.tagName.toLowerCase();
if(t=="text"||t=="hidden"||t=="password"||tag=="textarea"){
this.value="";
}else{
if(t=="file"){
var _36=$(this);
if(!_36.hasClass("textbox-value")){
var _37=_36.clone().val("");
_37.insertAfter(_36);
if(_36.data("validatebox")){
_36.validatebox("destroy");
_37.validatebox();
}else{
_36.remove();
}
}
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
var tmp=$();
var _38=$(_35);
var _39=$.data(_35,"form").options;
for(var i=0;i<_39.fieldTypes.length;i++){
var _3a=_39.fieldTypes[i];
var _3b=_38.find("."+_3a+"-f").not(tmp);
if(_3b.length&&_3b[_3a]){
_3b[_3a]("clear");
tmp=tmp.add(_3b);
}
}
_38.form("validate");
};
function _3c(_3d){
_3d.reset();
var _3e=$(_3d);
var _3f=$.data(_3d,"form").options;
for(var i=_3f.fieldTypes.length-1;i>=0;i--){
var _40=_3f.fieldTypes[i];
var _41=_3e.find("."+_40+"-f");
if(_41.length&&_41[_40]){
_41[_40]("reset");
}
}
_3e.form("validate");
};
function _42(_43){
var _44=$.data(_43,"form").options;
$(_43).unbind(".form");
if(_44.ajax){
$(_43).bind("submit.form",function(){
setTimeout(function(){
_1(_43,_44);
},0);
return false;
});
}
$(_43).bind("_change.form",function(e,t){
if($.inArray(t,_44.dirtyFields)==-1){
_44.dirtyFields.push(t);
}
_44.onChange.call(this,t);
}).bind("change.form",function(e){
var t=e.target;
if(!$(t).hasClass("textbox-text")){
if($.inArray(t,_44.dirtyFields)==-1){
_44.dirtyFields.push(t);
}
_44.onChange.call(this,t);
}
});
_45(_43,_44.novalidate);
};
function _46(_47,_48){
_48=_48||{};
var _49=$.data(_47,"form");
if(_49){
$.extend(_49.options,_48);
}else{
$.data(_47,"form",{options:$.extend({},$.fn.form.defaults,$.fn.form.parseOptions(_47),_48)});
}
};
function _4a(_4b){
if($.fn.validatebox){
var t=$(_4b);
t.find(".validatebox-text:not(:disabled)").validatebox("validate");
var _4c=t.find(".validatebox-invalid");
_4c.filter(":not(:disabled):first").focus();
return _4c.length==0;
}
return true;
};
function _45(_4d,_4e){
var _4f=$.data(_4d,"form").options;
_4f.novalidate=_4e;
$(_4d).find(".validatebox-text:not(:disabled)").validatebox(_4e?"disableValidation":"enableValidation");
};
$.fn.form=function(_50,_51){
if(typeof _50=="string"){
this.each(function(){
_46(this);
});
return $.fn.form.methods[_50](this,_51);
}
return this.each(function(){
_46(this,_50);
_42(this);
});
};
$.fn.form.methods={options:function(jq){
return $.data(jq[0],"form").options;
},submit:function(jq,_52){
return jq.each(function(){
_1(this,_52);
});
},load:function(jq,_53){
return jq.each(function(){
_20(this,_53);
});
},clear:function(jq){
return jq.each(function(){
_34(this);
});
},reset:function(jq){
return jq.each(function(){
_3c(this);
});
},validate:function(jq){
return _4a(jq[0]);
},disableValidation:function(jq){
return jq.each(function(){
_45(this,true);
});
},enableValidation:function(jq){
return jq.each(function(){
_45(this,false);
});
},resetValidation:function(jq){
return jq.each(function(){
$(this).find(".validatebox-text:not(:disabled)").validatebox("resetValidation");
});
},resetDirty:function(jq){
return jq.each(function(){
$(this).form("options").dirtyFields=[];
});
}};
$.fn.form.parseOptions=function(_54){
var t=$(_54);
return $.extend({},$.parser.parseOptions(_54,[{ajax:"boolean",dirty:"boolean"}]),{url:(t.attr("action")?t.attr("action"):undefined)});
};
$.fn.form.defaults={fieldTypes:["tagbox","combobox","combotree","combogrid","combotreegrid","datetimebox","datebox","timepicker","combo","datetimespinner","timespinner","numberspinner","spinner","slider","searchbox","numberbox","passwordbox","filebox","textbox","switchbutton","radiobutton","checkbox"],novalidate:false,ajax:true,iframe:true,dirty:false,dirtyFields:[],url:null,queryParams:{},onSubmit:function(_55){
return $(this).form("validate");
},onProgress:function(_56){
},success:function(_57){
},onBeforeLoad:function(_58){
},onLoadSuccess:function(_59){
},onLoadError:function(){
},onChange:function(_5a){
}};
})(jQuery);

