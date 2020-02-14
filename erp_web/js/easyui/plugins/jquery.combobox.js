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
var _4=$.data(_2,"combobox");
return $.easyui.indexOfArray(_4.data,_4.options.valueField,_3);
};
function _5(_6,_7){
var _8=$.data(_6,"combobox").options;
var _9=$(_6).combo("panel");
var _a=_8.finder.getEl(_6,_7);
if(_a.length){
if(_a.position().top<=0){
var h=_9.scrollTop()+_a.position().top;
_9.scrollTop(h);
}else{
if(_a.position().top+_a.outerHeight()>_9.height()){
var h=_9.scrollTop()+_a.position().top+_a.outerHeight()-_9.height();
_9.scrollTop(h);
}
}
}
_9.triggerHandler("scroll");
};
function _b(_c,_d){
var _e=$.data(_c,"combobox").options;
var _f=$(_c).combobox("panel");
var _10=_f.children("div.combobox-item-hover");
if(!_10.length){
_10=_f.children("div.combobox-item-selected");
}
_10.removeClass("combobox-item-hover");
var _11="div.combobox-item:visible:not(.combobox-item-disabled):first";
var _12="div.combobox-item:visible:not(.combobox-item-disabled):last";
if(!_10.length){
_10=_f.children(_d=="next"?_11:_12);
}else{
if(_d=="next"){
_10=_10.nextAll(_11);
if(!_10.length){
_10=_f.children(_11);
}
}else{
_10=_10.prevAll(_11);
if(!_10.length){
_10=_f.children(_12);
}
}
}
if(_10.length){
_10.addClass("combobox-item-hover");
var row=_e.finder.getRow(_c,_10);
if(row){
$(_c).combobox("scrollTo",row[_e.valueField]);
if(_e.selectOnNavigation){
_13(_c,row[_e.valueField]);
}
}
}
};
function _13(_14,_15,_16){
var _17=$.data(_14,"combobox").options;
var _18=$(_14).combo("getValues");
if($.inArray(_15+"",_18)==-1){
if(_17.multiple){
_18.push(_15);
}else{
_18=[_15];
}
_19(_14,_18,_16);
}
};
function _1a(_1b,_1c){
var _1d=$.data(_1b,"combobox").options;
var _1e=$(_1b).combo("getValues");
var _1f=$.inArray(_1c+"",_1e);
if(_1f>=0){
_1e.splice(_1f,1);
_19(_1b,_1e);
}
};
function _19(_20,_21,_22){
var _23=$.data(_20,"combobox").options;
var _24=$(_20).combo("panel");
if(!$.isArray(_21)){
_21=_21.split(_23.separator);
}
if(!_23.multiple){
_21=_21.length?[_21[0]]:[""];
}
var _25=$(_20).combo("getValues");
if(_24.is(":visible")){
_24.find(".combobox-item-selected").each(function(){
var row=_23.finder.getRow(_20,$(this));
if(row){
if($.easyui.indexOfArray(_25,row[_23.valueField])==-1){
$(this).removeClass("combobox-item-selected");
}
}
});
}
$.map(_25,function(v){
if($.easyui.indexOfArray(_21,v)==-1){
var el=_23.finder.getEl(_20,v);
if(el.hasClass("combobox-item-selected")){
el.removeClass("combobox-item-selected");
_23.onUnselect.call(_20,_23.finder.getRow(_20,v));
}
}
});
var _26=null;
var vv=[],ss=[];
for(var i=0;i<_21.length;i++){
var v=_21[i];
var s=v;
var row=_23.finder.getRow(_20,v);
if(row){
s=row[_23.textField];
_26=row;
var el=_23.finder.getEl(_20,v);
if(!el.hasClass("combobox-item-selected")){
el.addClass("combobox-item-selected");
_23.onSelect.call(_20,row);
}
}else{
s=_27(v,_23.mappingRows)||v;
}
vv.push(v);
ss.push(s);
}
if(!_22){
$(_20).combo("setText",ss.join(_23.separator));
}
if(_23.showItemIcon){
var tb=$(_20).combobox("textbox");
tb.removeClass("textbox-bgicon "+_23.textboxIconCls);
if(_26&&_26.iconCls){
tb.addClass("textbox-bgicon "+_26.iconCls);
_23.textboxIconCls=_26.iconCls;
}
}
$(_20).combo("setValues",vv);
_24.triggerHandler("scroll");
function _27(_28,a){
var _29=$.easyui.getArrayItem(a,_23.valueField,_28);
return _29?_29[_23.textField]:undefined;
};
};
function _2a(_2b,_2c,_2d){
var _2e=$.data(_2b,"combobox");
var _2f=_2e.options;
_2e.data=_2f.loadFilter.call(_2b,_2c);
_2f.view.render.call(_2f.view,_2b,$(_2b).combo("panel"),_2e.data);
var vv=$(_2b).combobox("getValues");
$.easyui.forEach(_2e.data,false,function(row){
if(row["selected"]){
$.easyui.addArrayItem(vv,row[_2f.valueField]+"");
}
});
if(_2f.multiple){
_19(_2b,vv,_2d);
}else{
_19(_2b,vv.length?[vv[vv.length-1]]:[],_2d);
}
_2f.onLoadSuccess.call(_2b,_2c);
};
function _30(_31,url,_32,_33){
var _34=$.data(_31,"combobox").options;
if(url){
_34.url=url;
}
_32=$.extend({},_34.queryParams,_32||{});
if(_34.onBeforeLoad.call(_31,_32)==false){
return;
}
_34.loader.call(_31,_32,function(_35){
_2a(_31,_35,_33);
},function(){
_34.onLoadError.apply(this,arguments);
});
};
function _36(_37,q){
var _38=$.data(_37,"combobox");
var _39=_38.options;
var _3a=$();
var qq=_39.multiple?q.split(_39.separator):[q];
if(_39.mode=="remote"){
_3b(qq);
_30(_37,null,{q:q},true);
}else{
var _3c=$(_37).combo("panel");
_3c.find(".combobox-item-hover").removeClass("combobox-item-hover");
_3c.find(".combobox-item,.combobox-group").hide();
var _3d=_38.data;
var vv=[];
$.map(qq,function(q){
q=$.trim(q);
var _3e=q;
var _3f=undefined;
_3a=$();
for(var i=0;i<_3d.length;i++){
var row=_3d[i];
if(_39.filter.call(_37,q,row)){
var v=row[_39.valueField];
var s=row[_39.textField];
var g=row[_39.groupField];
var _40=_39.finder.getEl(_37,v).show();
if(s.toLowerCase()==q.toLowerCase()){
_3e=v;
if(_39.reversed){
_3a=_40;
}else{
_13(_37,v,true);
}
}
if(_39.groupField&&_3f!=g){
_39.finder.getGroupEl(_37,g).show();
_3f=g;
}
}
}
vv.push(_3e);
});
_3b(vv);
}
function _3b(vv){
if(_39.reversed){
_3a.addClass("combobox-item-hover");
}else{
_19(_37,_39.multiple?(q?vv:[]):vv,true);
}
};
};
function _41(_42){
var t=$(_42);
var _43=t.combobox("options");
var _44=t.combobox("panel");
var _45=_44.children("div.combobox-item-hover");
if(_45.length){
_45.removeClass("combobox-item-hover");
var row=_43.finder.getRow(_42,_45);
var _46=row[_43.valueField];
if(_43.multiple){
if(_45.hasClass("combobox-item-selected")){
t.combobox("unselect",_46);
}else{
t.combobox("select",_46);
}
}else{
t.combobox("select",_46);
}
}
var vv=[];
$.map(t.combobox("getValues"),function(v){
if(_1(_42,v)>=0){
vv.push(v);
}
});
t.combobox("setValues",vv);
if(!_43.multiple){
t.combobox("hidePanel");
}
};
function _47(_48){
var _49=$.data(_48,"combobox");
var _4a=_49.options;
$(_48).addClass("combobox-f");
$(_48).combo($.extend({},_4a,{onShowPanel:function(){
$(this).combo("panel").find("div.combobox-item:hidden,div.combobox-group:hidden").show();
_19(this,$(this).combobox("getValues"),true);
$(this).combobox("scrollTo",$(this).combobox("getValue"));
_4a.onShowPanel.call(this);
}}));
};
function _4b(e){
$(this).children("div.combobox-item-hover").removeClass("combobox-item-hover");
var _4c=$(e.target).closest("div.combobox-item");
if(!_4c.hasClass("combobox-item-disabled")){
_4c.addClass("combobox-item-hover");
}
e.stopPropagation();
};
function _4d(e){
$(e.target).closest("div.combobox-item").removeClass("combobox-item-hover");
e.stopPropagation();
};
function _4e(e){
var _4f=$(this).panel("options").comboTarget;
if(!_4f){
return;
}
var _50=$(_4f).combobox("options");
var _51=$(e.target).closest("div.combobox-item");
if(!_51.length||_51.hasClass("combobox-item-disabled")){
return;
}
var row=_50.finder.getRow(_4f,_51);
if(!row){
return;
}
if(_50.blurTimer){
clearTimeout(_50.blurTimer);
_50.blurTimer=null;
}
_50.onClick.call(_4f,row);
var _52=row[_50.valueField];
if(_50.multiple){
if(_51.hasClass("combobox-item-selected")){
_1a(_4f,_52);
}else{
_13(_4f,_52);
}
}else{
$(_4f).combobox("setValue",_52).combobox("hidePanel");
}
e.stopPropagation();
};
function _53(e){
var _54=$(this).panel("options").comboTarget;
if(!_54){
return;
}
var _55=$(_54).combobox("options");
if(_55.groupPosition=="sticky"){
var _56=$(this).children(".combobox-stick");
if(!_56.length){
_56=$("<div class=\"combobox-stick\"></div>").appendTo(this);
}
_56.hide();
var _57=$(_54).data("combobox");
$(this).children(".combobox-group:visible").each(function(){
var g=$(this);
var _58=_55.finder.getGroup(_54,g);
var _59=_57.data[_58.startIndex+_58.count-1];
var _5a=_55.finder.getEl(_54,_59[_55.valueField]);
if(g.position().top<0&&_5a.position().top>0){
_56.show().html(g.html());
return false;
}
});
}
};
$.fn.combobox=function(_5b,_5c){
if(typeof _5b=="string"){
var _5d=$.fn.combobox.methods[_5b];
if(_5d){
return _5d(this,_5c);
}else{
return this.combo(_5b,_5c);
}
}
_5b=_5b||{};
return this.each(function(){
var _5e=$.data(this,"combobox");
if(_5e){
$.extend(_5e.options,_5b);
}else{
_5e=$.data(this,"combobox",{options:$.extend({},$.fn.combobox.defaults,$.fn.combobox.parseOptions(this),_5b),data:[]});
}
_47(this);
if(_5e.options.data){
_2a(this,_5e.options.data);
}else{
var _5f=$.fn.combobox.parseData(this);
if(_5f.length){
_2a(this,_5f);
}
}
_30(this);
});
};
$.fn.combobox.methods={options:function(jq){
var _60=jq.combo("options");
return $.extend($.data(jq[0],"combobox").options,{width:_60.width,height:_60.height,originalValue:_60.originalValue,disabled:_60.disabled,readonly:_60.readonly});
},cloneFrom:function(jq,_61){
return jq.each(function(){
$(this).combo("cloneFrom",_61);
$.data(this,"combobox",$(_61).data("combobox"));
$(this).addClass("combobox-f").attr("comboboxName",$(this).attr("textboxName"));
});
},getData:function(jq){
return $.data(jq[0],"combobox").data;
},setValues:function(jq,_62){
return jq.each(function(){
var _63=$(this).combobox("options");
if($.isArray(_62)){
_62=$.map(_62,function(_64){
if(_64&&typeof _64=="object"){
$.easyui.addArrayItem(_63.mappingRows,_63.valueField,_64);
return _64[_63.valueField];
}else{
return _64;
}
});
}
_19(this,_62);
});
},setValue:function(jq,_65){
return jq.each(function(){
$(this).combobox("setValues",$.isArray(_65)?_65:[_65]);
});
},clear:function(jq){
return jq.each(function(){
_19(this,[]);
});
},reset:function(jq){
return jq.each(function(){
var _66=$(this).combobox("options");
if(_66.multiple){
$(this).combobox("setValues",_66.originalValue);
}else{
$(this).combobox("setValue",_66.originalValue);
}
});
},loadData:function(jq,_67){
return jq.each(function(){
_2a(this,_67);
});
},reload:function(jq,url){
return jq.each(function(){
if(typeof url=="string"){
_30(this,url);
}else{
if(url){
var _68=$(this).combobox("options");
_68.queryParams=url;
}
_30(this);
}
});
},select:function(jq,_69){
return jq.each(function(){
_13(this,_69);
});
},unselect:function(jq,_6a){
return jq.each(function(){
_1a(this,_6a);
});
},scrollTo:function(jq,_6b){
return jq.each(function(){
_5(this,_6b);
});
}};
$.fn.combobox.parseOptions=function(_6c){
var t=$(_6c);
return $.extend({},$.fn.combo.parseOptions(_6c),$.parser.parseOptions(_6c,["valueField","textField","groupField","groupPosition","mode","method","url",{showItemIcon:"boolean",limitToList:"boolean"}]));
};
$.fn.combobox.parseData=function(_6d){
var _6e=[];
var _6f=$(_6d).combobox("options");
$(_6d).children().each(function(){
if(this.tagName.toLowerCase()=="optgroup"){
var _70=$(this).attr("label");
$(this).children().each(function(){
_71(this,_70);
});
}else{
_71(this);
}
});
return _6e;
function _71(el,_72){
var t=$(el);
var row={};
row[_6f.valueField]=t.attr("value")!=undefined?t.attr("value"):t.text();
row[_6f.textField]=t.text();
row["iconCls"]=$.parser.parseOptions(el,["iconCls"]).iconCls;
row["selected"]=t.is(":selected");
row["disabled"]=t.is(":disabled");
if(_72){
_6f.groupField=_6f.groupField||"group";
row[_6f.groupField]=_72;
}
_6e.push(row);
};
};
var _73=0;
var _74={render:function(_75,_76,_77){
var _78=$.data(_75,"combobox");
var _79=_78.options;
var _7a=$(_75).attr("id")||"";
_73++;
_78.itemIdPrefix=_7a+"_easyui_combobox_i"+_73;
_78.groupIdPrefix=_7a+"_easyui_combobox_g"+_73;
_78.groups=[];
var dd=[];
var _7b=undefined;
for(var i=0;i<_77.length;i++){
var row=_77[i];
var v=row[_79.valueField]+"";
var s=row[_79.textField];
var g=row[_79.groupField];
if(g){
if(_7b!=g){
_7b=g;
_78.groups.push({value:g,startIndex:i,count:1});
dd.push("<div id=\""+(_78.groupIdPrefix+"_"+(_78.groups.length-1))+"\" class=\"combobox-group\">");
dd.push(_79.groupFormatter?_79.groupFormatter.call(_75,g):g);
dd.push("</div>");
}else{
_78.groups[_78.groups.length-1].count++;
}
}else{
_7b=undefined;
}
var cls="combobox-item"+(row.disabled?" combobox-item-disabled":"")+(g?" combobox-gitem":"");
dd.push("<div id=\""+(_78.itemIdPrefix+"_"+i)+"\" class=\""+cls+"\">");
if(_79.showItemIcon&&row.iconCls){
dd.push("<span class=\"combobox-icon "+row.iconCls+"\"></span>");
}
dd.push(_79.formatter?_79.formatter.call(_75,row):s);
dd.push("</div>");
}
$(_76).html(dd.join(""));
}};
$.fn.combobox.defaults=$.extend({},$.fn.combo.defaults,{valueField:"value",textField:"text",groupPosition:"static",groupField:null,groupFormatter:function(_7c){
return _7c;
},mode:"local",method:"post",url:null,data:null,queryParams:{},showItemIcon:false,limitToList:false,unselectedValues:[],mappingRows:[],view:_74,keyHandler:{up:function(e){
_b(this,"prev");
e.preventDefault();
},down:function(e){
_b(this,"next");
e.preventDefault();
},left:function(e){
},right:function(e){
},enter:function(e){
_41(this);
},query:function(q,e){
_36(this,q);
}},inputEvents:$.extend({},$.fn.combo.defaults.inputEvents,{blur:function(e){
$.fn.combo.defaults.inputEvents.blur(e);
var _7d=e.data.target;
var _7e=$(_7d).combobox("options");
if(_7e.reversed||_7e.limitToList){
if(_7e.blurTimer){
clearTimeout(_7e.blurTimer);
}
_7e.blurTimer=setTimeout(function(){
var _7f=$(_7d).parent().length;
if(_7f){
if(_7e.reversed){
$(_7d).combobox("setValues",$(_7d).combobox("getValues"));
}else{
if(_7e.limitToList){
var vv=[];
$.map($(_7d).combobox("getValues"),function(v){
var _80=$.easyui.indexOfArray($(_7d).combobox("getData"),_7e.valueField,v);
if(_80>=0){
vv.push(v);
}
});
$(_7d).combobox("setValues",vv);
}
}
_7e.blurTimer=null;
}
},50);
}
}}),panelEvents:{mouseover:_4b,mouseout:_4d,mousedown:function(e){
e.preventDefault();
e.stopPropagation();
},click:_4e,scroll:_53},filter:function(q,row){
var _81=$(this).combobox("options");
return row[_81.textField].toLowerCase().indexOf(q.toLowerCase())>=0;
},formatter:function(row){
var _82=$(this).combobox("options");
return row[_82.textField];
},loader:function(_83,_84,_85){
var _86=$(this).combobox("options");
if(!_86.url){
return false;
}
$.ajax({type:_86.method,url:_86.url,data:_83,dataType:"json",success:function(_87){
_84(_87);
},error:function(){
_85.apply(this,arguments);
}});
},loadFilter:function(_88){
return _88;
},finder:{getEl:function(_89,_8a){
var _8b=_1(_89,_8a);
var id=$.data(_89,"combobox").itemIdPrefix+"_"+_8b;
return $("#"+id);
},getGroupEl:function(_8c,_8d){
var _8e=$.data(_8c,"combobox");
var _8f=$.easyui.indexOfArray(_8e.groups,"value",_8d);
var id=_8e.groupIdPrefix+"_"+_8f;
return $("#"+id);
},getGroup:function(_90,p){
var _91=$.data(_90,"combobox");
var _92=p.attr("id").substr(_91.groupIdPrefix.length+1);
return _91.groups[parseInt(_92)];
},getRow:function(_93,p){
var _94=$.data(_93,"combobox");
var _95=(p instanceof $)?p.attr("id").substr(_94.itemIdPrefix.length+1):_1(_93,p);
return _94.data[parseInt(_95)];
}},onBeforeLoad:function(_96){
},onLoadSuccess:function(_97){
},onLoadError:function(){
},onSelect:function(_98){
},onUnselect:function(_99){
},onClick:function(_9a){
}});
})(jQuery);

