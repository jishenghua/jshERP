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
function _1(_2,_3,_4,_5){
var _6=$.data(_2,"combobox");
var _7=_6.options;
if(_5){
return _8(_6.groups,_4,_3);
}else{
return _8(_6.data,(_4?_4:_6.options.valueField),_3);
}
function _8(_9,_a,_b){
for(var i=0;i<_9.length;i++){
var _c=_9[i];
if(_c[_a]==_b){
return _c;
}
}
return null;
};
};
function _d(_e,_f){
var _10=$(_e).combo("panel");
var row=_1(_e,_f);
if(row){
var _11=$("#"+row.domId);
if(_11.position().top<=0){
var h=_10.scrollTop()+_11.position().top;
_10.scrollTop(h);
}else{
if(_11.position().top+_11.outerHeight()>_10.height()){
var h=_10.scrollTop()+_11.position().top+_11.outerHeight()-_10.height();
_10.scrollTop(h);
}
}
}
};
function nav(_12,dir){
var _13=$.data(_12,"combobox").options;
var _14=$(_12).combobox("panel");
var _15=_14.children("div.combobox-item-hover");
if(!_15.length){
_15=_14.children("div.combobox-item-selected");
}
_15.removeClass("combobox-item-hover");
var _16="div.combobox-item:visible:not(.combobox-item-disabled):first";
var _17="div.combobox-item:visible:not(.combobox-item-disabled):last";
if(!_15.length){
_15=_14.children(dir=="next"?_16:_17);
}else{
if(dir=="next"){
_15=_15.nextAll(_16);
if(!_15.length){
_15=_14.children(_16);
}
}else{
_15=_15.prevAll(_16);
if(!_15.length){
_15=_14.children(_17);
}
}
}
if(_15.length){
_15.addClass("combobox-item-hover");
var row=_1(_12,_15.attr("id"),"domId");
if(row){
_d(_12,row[_13.valueField]);
if(_13.selectOnNavigation){
_18(_12,row[_13.valueField]);
}
}
}
};
function _18(_19,_1a){
var _1b=$.data(_19,"combobox").options;
var _1c=$(_19).combo("getValues");
if($.inArray(_1a+"",_1c)==-1){
if(_1b.multiple){
_1c.push(_1a);
}else{
_1c=[_1a];
}
_1d(_19,_1c);
_1b.onSelect.call(_19,_1(_19,_1a));
}
};
function _1e(_1f,_20){
var _21=$.data(_1f,"combobox").options;
var _22=$(_1f).combo("getValues");
var _23=$.inArray(_20+"",_22);
if(_23>=0){
_22.splice(_23,1);
_1d(_1f,_22);
_21.onUnselect.call(_1f,_1(_1f,_20));
}
};
function _1d(_24,_25,_26){
var _27=$.data(_24,"combobox").options;
var _28=$(_24).combo("panel");
_28.find("div.combobox-item-selected").removeClass("combobox-item-selected");
var vv=[],ss=[];
for(var i=0;i<_25.length;i++){
var v=_25[i];
var s=v;
var row=_1(_24,v);
if(row){
s=row[_27.textField];
$("#"+row.domId).addClass("combobox-item-selected");
}
vv.push(v);
ss.push(s);
}
$(_24).combo("setValues",vv);
if(!_26){
$(_24).combo("setText",ss.join(_27.separator));
}
};
var _29=1;
function _2a(_2b,_2c,_2d){
var _2e=$.data(_2b,"combobox");
var _2f=_2e.options;
_2e.data=_2f.loadFilter.call(_2b,_2c);
_2e.groups=[];
_2c=_2e.data;
var _30=$(_2b).combobox("getValues");
var dd=[];
var _31=undefined;
for(var i=0;i<_2c.length;i++){
var row=_2c[i];
var v=row[_2f.valueField]+"";
var s=row[_2f.textField];
var g=row[_2f.groupField];
if(g){
if(_31!=g){
_31=g;
var _32={value:g,domId:("_easyui_combobox_"+_29++)};
_2e.groups.push(_32);
dd.push("<div id=\""+_32.domId+"\" class=\"combobox-group\">");
dd.push(_2f.groupFormatter?_2f.groupFormatter.call(_2b,g):g);
dd.push("</div>");
}
}else{
_31=undefined;
}
var cls="combobox-item"+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
row.domId="_easyui_combobox_"+_29++;
dd.push("<div id=\""+row.domId+"\" class=\""+cls+"\">");
dd.push(_2f.formatter?_2f.formatter.call(_2b,row):s);
dd.push("</div>");
if(row["selected"]&&$.inArray(v,_30)==-1){
_30.push(v);
}
}
$(_2b).combo("panel").html(dd.join(""));
if(_2f.multiple){
_1d(_2b,_30,_2d);
}else{
_1d(_2b,_30.length?[_30[_30.length-1]]:[],_2d);
}
_2f.onLoadSuccess.call(_2b,_2c);
};
function _33(_34,url,_35,_36){
var _37=$.data(_34,"combobox").options;
if(url){
_37.url=url;
}
_35=_35||{};
if(_37.onBeforeLoad.call(_34,_35)==false){
return;
}
_37.loader.call(_34,_35,function(_38){
_2a(_34,_38,_36);
},function(){
_37.onLoadError.apply(this,arguments);
});
};
function _39(_3a,q){
var _3b=$.data(_3a,"combobox");
var _3c=_3b.options;
if(_3c.multiple&&!q){
_1d(_3a,[],true);
}else{
_1d(_3a,[q],true);
}
if(_3c.mode=="remote"){
_33(_3a,null,{q:q},true);
}else{
var _3d=$(_3a).combo("panel");
_3d.find("div.combobox-item,div.combobox-group").hide();
var _3e=_3b.data;
var _3f=undefined;
for(var i=0;i<_3e.length;i++){
var row=_3e[i];
if(_3c.filter.call(_3a,q,row)){
var v=row[_3c.valueField];
var s=row[_3c.textField];
var g=row[_3c.groupField];
var _40=$("#"+row.domId).show();
if(s.toLowerCase()==q.toLowerCase()){
_1d(_3a,[v]);
_40.addClass("combobox-item-selected");
}
if(_3c.groupField&&_3f!=g){
var _41=_1(_3a,g,"value",true);
if(_41){
$("#"+_41.domId).show();
}
_3f=g;
}
}
}
}
};
function _42(_43){
var t=$(_43);
var _44=t.combobox("options");
var _45=t.combobox("panel");
var _46=_45.children("div.combobox-item-hover");
if(!_46.length){
_46=_45.children("div.combobox-item-selected");
}
if(!_46.length){
return;
}
var row=_1(_43,_46.attr("id"),"domId");
if(!row){
return;
}
var _47=row[_44.valueField];
if(_44.multiple){
if(_46.hasClass("combobox-item-selected")){
t.combobox("unselect",_47);
}else{
t.combobox("select",_47);
}
}else{
t.combobox("select",_47);
t.combobox("hidePanel");
}
var vv=[];
var _48=t.combobox("getValues");
for(var i=0;i<_48.length;i++){
if(_1(_43,_48[i])){
vv.push(_48[i]);
}
}
t.combobox("setValues",vv);
};
function _49(_4a){
var _4b=$.data(_4a,"combobox").options;
$(_4a).addClass("combobox-f");
$(_4a).combo($.extend({},_4b,{onShowPanel:function(){
$(_4a).combo("panel").find("div.combobox-item,div.combobox-group").show();
_d(_4a,$(_4a).combobox("getValue"));
_4b.onShowPanel.call(_4a);
}}));
$(_4a).combo("panel").unbind().bind("mouseover",function(e){
$(this).children("div.combobox-item-hover").removeClass("combobox-item-hover");
var _4c=$(e.target).closest("div.combobox-item");
if(!_4c.hasClass("combobox-item-disabled")){
_4c.addClass("combobox-item-hover");
}
e.stopPropagation();
}).bind("mouseout",function(e){
$(e.target).closest("div.combobox-item").removeClass("combobox-item-hover");
e.stopPropagation();
}).bind("click",function(e){
var _4d=$(e.target).closest("div.combobox-item");
if(!_4d.length||_4d.hasClass("combobox-item-disabled")){
return;
}
var row=_1(_4a,_4d.attr("id"),"domId");
if(!row){
return;
}
var _4e=row[_4b.valueField];
if(_4b.multiple){
if(_4d.hasClass("combobox-item-selected")){
_1e(_4a,_4e);
}else{
_18(_4a,_4e);
}
}else{
_18(_4a,_4e);
$(_4a).combo("hidePanel");
}
e.stopPropagation();
});
};
$.fn.combobox=function(_4f,_50){
if(typeof _4f=="string"){
var _51=$.fn.combobox.methods[_4f];
if(_51){
return _51(this,_50);
}else{
return this.combo(_4f,_50);
}
}
_4f=_4f||{};
return this.each(function(){
var _52=$.data(this,"combobox");
if(_52){
$.extend(_52.options,_4f);
_49(this);
}else{
_52=$.data(this,"combobox",{options:$.extend({},$.fn.combobox.defaults,$.fn.combobox.parseOptions(this),_4f),data:[]});
_49(this);
var _53=$.fn.combobox.parseData(this);
if(_53.length){
_2a(this,_53);
}
}
if(_52.options.data){
_2a(this,_52.options.data);
}
_33(this);
});
};
$.fn.combobox.methods={options:function(jq){
var _54=jq.combo("options");
return $.extend($.data(jq[0],"combobox").options,{originalValue:_54.originalValue,disabled:_54.disabled,readonly:_54.readonly});
},getData:function(jq){
return $.data(jq[0],"combobox").data;
},setValues:function(jq,_55){
return jq.each(function(){
_1d(this,_55);
});
},setValue:function(jq,_56){
return jq.each(function(){
_1d(this,[_56]);
});
},clear:function(jq){
return jq.each(function(){
$(this).combo("clear");
var _57=$(this).combo("panel");
_57.find("div.combobox-item-selected").removeClass("combobox-item-selected");
});
},reset:function(jq){
return jq.each(function(){
var _58=$(this).combobox("options");
if(_58.multiple){
$(this).combobox("setValues",_58.originalValue);
}else{
$(this).combobox("setValue",_58.originalValue);
}
});
},loadData:function(jq,_59){
return jq.each(function(){
_2a(this,_59);
});
},reload:function(jq,url){
return jq.each(function(){
_33(this,url);
});
},select:function(jq,_5a){
return jq.each(function(){
_18(this,_5a);
});
},unselect:function(jq,_5b){
return jq.each(function(){
_1e(this,_5b);
});
}};
$.fn.combobox.parseOptions=function(_5c){
var t=$(_5c);
return $.extend({},$.fn.combo.parseOptions(_5c),$.parser.parseOptions(_5c,["valueField","textField","groupField","mode","method","url"]));
};
$.fn.combobox.parseData=function(_5d){
var _5e=[];
var _5f=$(_5d).combobox("options");
$(_5d).children().each(function(){
if(this.tagName.toLowerCase()=="optgroup"){
var _60=$(this).attr("label");
$(this).children().each(function(){
_61(this,_60);
});
}else{
_61(this);
}
});
return _5e;
function _61(el,_62){
var t=$(el);
var row={};
row[_5f.valueField]=t.attr("value")!=undefined?t.attr("value"):t.html();
row[_5f.textField]=t.html();
row["selected"]=t.is(":selected");
row["disabled"]=t.is(":disabled");
if(_62){
_5f.groupField=_5f.groupField||"group";
row[_5f.groupField]=_62;
}
_5e.push(row);
};
};
$.fn.combobox.defaults=$.extend({},$.fn.combo.defaults,{valueField:"value",textField:"text",groupField:null,groupFormatter:function(_63){
return _63;
},mode:"local",method:"post",url:null,data:null,keyHandler:{up:function(e){
nav(this,"prev");
e.preventDefault();
},down:function(e){
nav(this,"next");
e.preventDefault();
},left:function(e){
},right:function(e){
},enter:function(e){
_42(this);
},query:function(q,e){
_39(this,q);
}},filter:function(q,row){
var _64=$(this).combobox("options");
return row[_64.textField].toLowerCase().indexOf(q.toLowerCase())==0;
},formatter:function(row){
var _65=$(this).combobox("options");
return row[_65.textField];
},loader:function(_66,_67,_68){
var _69=$(this).combobox("options");
if(!_69.url){
return false;
}
$.ajax({type:_69.method,url:_69.url,data:_66,dataType:"json",success:function(_6a){
_67(_6a);
},error:function(){
_68.apply(this,arguments);
}});
},loadFilter:function(_6b){
return _6b;
},onBeforeLoad:function(_6c){
},onLoadSuccess:function(){
},onLoadError:function(){
},onSelect:function(_6d){
},onUnselect:function(_6e){
}});
})(jQuery);

