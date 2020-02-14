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
var _1;
$(document)._unbind(".propertygrid")._bind("mousedown.propertygrid",function(e){
var p=$(e.target).closest("div.datagrid-view,div.combo-panel");
if(p.length){
return;
}
_2(_1);
_1=undefined;
});
function _3(_4){
var _5=$.data(_4,"propertygrid");
var _6=$.data(_4,"propertygrid").options;
$(_4).datagrid($.extend({},_6,{cls:"propertygrid",view:(_6.showGroup?_6.groupView:_6.view),onBeforeEdit:function(_7,_8){
if(_6.onBeforeEdit.call(_4,_7,_8)==false){
return false;
}
var dg=$(this);
var _8=dg.datagrid("getRows")[_7];
var _9=dg.datagrid("getColumnOption","value");
_9.editor=_8.editor;
},onClickCell:function(_a,_b,_c){
if(_1!=this){
_2(_1);
_1=this;
}
if(_6.editIndex!=_a){
_2(_1);
$(this).datagrid("beginEdit",_a);
var ed=$(this).datagrid("getEditor",{index:_a,field:_b});
if(!ed){
ed=$(this).datagrid("getEditor",{index:_a,field:"value"});
}
if(ed){
var t=$(ed.target);
var _d=t.data("textbox")?t.textbox("textbox"):t;
_d.focus();
_6.editIndex=_a;
}
}
_6.onClickCell.call(_4,_a,_b,_c);
},loadFilter:function(_e){
_2(this);
return _6.loadFilter.call(this,_e);
}}));
};
function _2(_f){
var t=$(_f);
if(!t.length){
return;
}
var _10=$.data(_f,"propertygrid").options;
_10.finder.getTr(_f,null,"editing").each(function(){
var _11=parseInt($(this).attr("datagrid-row-index"));
if(t.datagrid("validateRow",_11)){
t.datagrid("endEdit",_11);
}else{
t.datagrid("cancelEdit",_11);
}
});
_10.editIndex=undefined;
};
$.fn.propertygrid=function(_12,_13){
if(typeof _12=="string"){
var _14=$.fn.propertygrid.methods[_12];
if(_14){
return _14(this,_13);
}else{
return this.datagrid(_12,_13);
}
}
_12=_12||{};
return this.each(function(){
var _15=$.data(this,"propertygrid");
if(_15){
$.extend(_15.options,_12);
}else{
var _16=$.extend({},$.fn.propertygrid.defaults,$.fn.propertygrid.parseOptions(this),_12);
_16.frozenColumns=$.extend(true,[],_16.frozenColumns);
_16.columns=$.extend(true,[],_16.columns);
$.data(this,"propertygrid",{options:_16});
}
_3(this);
});
};
$.fn.propertygrid.methods={options:function(jq){
return $.data(jq[0],"propertygrid").options;
}};
$.fn.propertygrid.parseOptions=function(_17){
return $.extend({},$.fn.datagrid.parseOptions(_17),$.parser.parseOptions(_17,[{showGroup:"boolean"}]));
};
var _18=$.extend({},$.fn.datagrid.defaults.view,{render:function(_19,_1a,_1b){
var _1c=[];
var _1d=this.groups;
for(var i=0;i<_1d.length;i++){
_1c.push(this.renderGroup.call(this,_19,i,_1d[i],_1b));
}
$(_1a).html(_1c.join(""));
},renderGroup:function(_1e,_1f,_20,_21){
var _22=$.data(_1e,"datagrid");
var _23=_22.options;
var _24=$(_1e).datagrid("getColumnFields",_21);
var _25=_23.frozenColumns&&_23.frozenColumns.length;
if(_21){
if(!(_23.rownumbers||_25)){
return "";
}
}
var _26=[];
var css=_23.groupStyler.call(_1e,_20.value,_20.rows);
var cs=_27(css,"datagrid-group");
_26.push("<div group-index="+_1f+" "+cs+">");
if((_21&&(_23.rownumbers||_23.frozenColumns.length))||(!_21&&!(_23.rownumbers||_23.frozenColumns.length))){
_26.push("<span class=\"datagrid-group-expander\">");
_26.push("<span class=\"datagrid-row-expander datagrid-row-collapse\">&nbsp;</span>");
_26.push("</span>");
}
if((_21&&_25)||(!_21)){
_26.push("<span class=\"datagrid-group-title\">");
_26.push(_23.groupFormatter.call(_1e,_20.value,_20.rows));
_26.push("</span>");
}
_26.push("</div>");
_26.push("<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>");
var _28=_20.startIndex;
for(var j=0;j<_20.rows.length;j++){
var css=_23.rowStyler?_23.rowStyler.call(_1e,_28,_20.rows[j]):"";
var _29="";
var _2a="";
if(typeof css=="string"){
_2a=css;
}else{
if(css){
_29=css["class"]||"";
_2a=css["style"]||"";
}
}
var cls="class=\"datagrid-row "+(_28%2&&_23.striped?"datagrid-row-alt ":" ")+_29+"\"";
var _2b=_2a?"style=\""+_2a+"\"":"";
var _2c=_22.rowIdPrefix+"-"+(_21?1:2)+"-"+_28;
_26.push("<tr id=\""+_2c+"\" datagrid-row-index=\""+_28+"\" "+cls+" "+_2b+">");
_26.push(this.renderRow.call(this,_1e,_24,_21,_28,_20.rows[j]));
_26.push("</tr>");
_28++;
}
_26.push("</tbody></table>");
return _26.join("");
function _27(css,cls){
var _2d="";
var _2e="";
if(typeof css=="string"){
_2e=css;
}else{
if(css){
_2d=css["class"]||"";
_2e=css["style"]||"";
}
}
return "class=\""+cls+(_2d?" "+_2d:"")+"\" "+"style=\""+_2e+"\"";
};
},bindEvents:function(_2f){
var _30=$.data(_2f,"datagrid");
var dc=_30.dc;
var _31=dc.body1.add(dc.body2);
var _32=($.data(_31[0],"events")||$._data(_31[0],"events")).click[0].handler;
_31._unbind("click")._bind("click",function(e){
var tt=$(e.target);
var _33=tt.closest("span.datagrid-row-expander");
if(_33.length){
var _34=_33.closest("div.datagrid-group").attr("group-index");
if(_33.hasClass("datagrid-row-collapse")){
$(_2f).datagrid("collapseGroup",_34);
}else{
$(_2f).datagrid("expandGroup",_34);
}
}else{
_32(e);
}
e.stopPropagation();
});
},onBeforeRender:function(_35,_36){
var _37=$.data(_35,"datagrid");
var _38=_37.options;
_39();
var _3a=[];
for(var i=0;i<_36.length;i++){
var row=_36[i];
var _3b=_3c(row[_38.groupField]);
if(!_3b){
_3b={value:row[_38.groupField],rows:[row]};
_3a.push(_3b);
}else{
_3b.rows.push(row);
}
}
var _3d=0;
var _3e=[];
for(var i=0;i<_3a.length;i++){
var _3b=_3a[i];
_3b.startIndex=_3d;
_3d+=_3b.rows.length;
_3e=_3e.concat(_3b.rows);
}
_37.data.rows=_3e;
this.groups=_3a;
var _3f=this;
setTimeout(function(){
_3f.bindEvents(_35);
},0);
function _3c(_40){
for(var i=0;i<_3a.length;i++){
var _41=_3a[i];
if(_41.value==_40){
return _41;
}
}
return null;
};
function _39(){
if(!$("#datagrid-group-style").length){
$("head").append("<style id=\"datagrid-group-style\">"+".datagrid-group{height:"+_38.groupHeight+"px;overflow:hidden;font-weight:bold;border-bottom:1px solid #ccc;white-space:nowrap;word-break:normal;}"+".datagrid-group-title,.datagrid-group-expander{display:inline-block;vertical-align:bottom;height:100%;line-height:"+_38.groupHeight+"px;padding:0 4px;}"+".datagrid-group-title{position:relative;}"+".datagrid-group-expander{width:"+_38.expanderWidth+"px;text-align:center;padding:0}"+".datagrid-row-expander{margin:"+Math.floor((_38.groupHeight-16)/2)+"px 0;display:inline-block;width:16px;height:16px;cursor:pointer}"+"</style>");
}
};
},onAfterRender:function(_42){
$.fn.datagrid.defaults.view.onAfterRender.call(this,_42);
var _43=this;
var _44=$.data(_42,"datagrid");
var _45=_44.options;
if(!_44.onResizeColumn){
_44.onResizeColumn=_45.onResizeColumn;
}
if(!_44.onResize){
_44.onResize=_45.onResize;
}
_45.onResizeColumn=function(_46,_47){
_43.resizeGroup(_42);
_44.onResizeColumn.call(_42,_46,_47);
};
_45.onResize=function(_48,_49){
_43.resizeGroup(_42);
_44.onResize.call($(_42).datagrid("getPanel")[0],_48,_49);
};
_43.resizeGroup(_42);
}});
$.extend($.fn.datagrid.methods,{groups:function(jq){
return jq.datagrid("options").view.groups;
},expandGroup:function(jq,_4a){
return jq.each(function(){
var _4b=$(this).datagrid("options");
var _4c=$.data(this,"datagrid").dc.view;
var _4d=_4c.find(_4a!=undefined?"div.datagrid-group[group-index=\""+_4a+"\"]":"div.datagrid-group");
var _4e=_4d.find("span.datagrid-row-expander");
if(_4e.hasClass("datagrid-row-expand")){
_4e.removeClass("datagrid-row-expand").addClass("datagrid-row-collapse");
_4d.next("table").show();
}
$(this).datagrid("fixRowHeight");
if(_4b.onExpandGroup){
_4b.onExpandGroup.call(this,_4a);
}
});
},collapseGroup:function(jq,_4f){
return jq.each(function(){
var _50=$(this).datagrid("options");
var _51=$.data(this,"datagrid").dc.view;
var _52=_51.find(_4f!=undefined?"div.datagrid-group[group-index=\""+_4f+"\"]":"div.datagrid-group");
var _53=_52.find("span.datagrid-row-expander");
if(_53.hasClass("datagrid-row-collapse")){
_53.removeClass("datagrid-row-collapse").addClass("datagrid-row-expand");
_52.next("table").hide();
}
$(this).datagrid("fixRowHeight");
if(_50.onCollapseGroup){
_50.onCollapseGroup.call(this,_4f);
}
});
},scrollToGroup:function(jq,_54){
return jq.each(function(){
var _55=$.data(this,"datagrid");
var dc=_55.dc;
var _56=dc.body2.children("div.datagrid-group[group-index=\""+_54+"\"]");
if(_56.length){
var _57=_56.outerHeight();
var _58=dc.view2.children("div.datagrid-header")._outerHeight();
var _59=dc.body2.outerHeight(true)-dc.body2.outerHeight();
var top=_56.position().top-_58-_59;
if(top<0){
dc.body2.scrollTop(dc.body2.scrollTop()+top);
}else{
if(top+_57>dc.body2.height()-18){
dc.body2.scrollTop(dc.body2.scrollTop()+top+_57-dc.body2.height()+18);
}
}
}
});
}});
$.extend(_18,{refreshGroupTitle:function(_5a,_5b){
var _5c=$.data(_5a,"datagrid");
var _5d=_5c.options;
var dc=_5c.dc;
var _5e=this.groups[_5b];
var _5f=dc.body1.add(dc.body2).children("div.datagrid-group[group-index="+_5b+"]").find("span.datagrid-group-title");
_5f.html(_5d.groupFormatter.call(_5a,_5e.value,_5e.rows));
},resizeGroup:function(_60,_61){
var _62=$.data(_60,"datagrid");
var dc=_62.dc;
var ht=dc.header2.find("table");
var fr=ht.find("tr.datagrid-filter-row").hide();
var ww=dc.body2.children("table.datagrid-btable:first").width();
if(_61==undefined){
var _63=dc.body2.children("div.datagrid-group");
}else{
var _63=dc.body2.children("div.datagrid-group[group-index="+_61+"]");
}
_63._outerWidth(ww);
var _64=_62.options;
if(_64.frozenColumns&&_64.frozenColumns.length){
var _65=dc.view1.width()-_64.expanderWidth;
var _66=dc.view1.css("direction").toLowerCase()=="rtl";
_63.find(".datagrid-group-title").css(_66?"right":"left",-_65+"px");
}
if(fr.length){
if(_64.showFilterBar){
fr.show();
}
}
},insertRow:function(_67,_68,row){
var _69=$.data(_67,"datagrid");
var _6a=_69.options;
var dc=_69.dc;
var _6b=null;
var _6c;
if(!_69.data.rows.length){
$(_67).datagrid("loadData",[row]);
return;
}
for(var i=0;i<this.groups.length;i++){
if(this.groups[i].value==row[_6a.groupField]){
_6b=this.groups[i];
_6c=i;
break;
}
}
if(_6b){
if(_68==undefined||_68==null){
_68=_69.data.rows.length;
}
if(_68<_6b.startIndex){
_68=_6b.startIndex;
}else{
if(_68>_6b.startIndex+_6b.rows.length){
_68=_6b.startIndex+_6b.rows.length;
}
}
$.fn.datagrid.defaults.view.insertRow.call(this,_67,_68,row);
if(_68>=_6b.startIndex+_6b.rows.length){
_6d(_68,true);
_6d(_68,false);
}
_6b.rows.splice(_68-_6b.startIndex,0,row);
}else{
_6b={value:row[_6a.groupField],rows:[row],startIndex:_69.data.rows.length};
_6c=this.groups.length;
dc.body1.append(this.renderGroup.call(this,_67,_6c,_6b,true));
dc.body2.append(this.renderGroup.call(this,_67,_6c,_6b,false));
this.groups.push(_6b);
_69.data.rows.push(row);
}
this.setGroupIndex(_67);
this.refreshGroupTitle(_67,_6c);
this.resizeGroup(_67);
function _6d(_6e,_6f){
var _70=_6f?1:2;
var _71=_6a.finder.getTr(_67,_6e-1,"body",_70);
var tr=_6a.finder.getTr(_67,_6e,"body",_70);
tr.insertAfter(_71);
};
},updateRow:function(_72,_73,row){
var _74=$.data(_72,"datagrid").options;
$.fn.datagrid.defaults.view.updateRow.call(this,_72,_73,row);
var tb=_74.finder.getTr(_72,_73,"body",2).closest("table.datagrid-btable");
var _75=parseInt(tb.prev().attr("group-index"));
this.refreshGroupTitle(_72,_75);
},deleteRow:function(_76,_77){
var _78=$.data(_76,"datagrid");
var _79=_78.options;
var dc=_78.dc;
var _7a=dc.body1.add(dc.body2);
var tb=_79.finder.getTr(_76,_77,"body",2).closest("table.datagrid-btable");
var _7b=parseInt(tb.prev().attr("group-index"));
$.fn.datagrid.defaults.view.deleteRow.call(this,_76,_77);
var _7c=this.groups[_7b];
if(_7c.rows.length>1){
_7c.rows.splice(_77-_7c.startIndex,1);
this.refreshGroupTitle(_76,_7b);
}else{
_7a.children("div.datagrid-group[group-index="+_7b+"]").remove();
for(var i=_7b+1;i<this.groups.length;i++){
_7a.children("div.datagrid-group[group-index="+i+"]").attr("group-index",i-1);
}
this.groups.splice(_7b,1);
}
this.setGroupIndex(_76);
},setGroupIndex:function(_7d){
var _7e=0;
for(var i=0;i<this.groups.length;i++){
var _7f=this.groups[i];
_7f.startIndex=_7e;
_7e+=_7f.rows.length;
}
}});
$.fn.propertygrid.defaults=$.extend({},$.fn.datagrid.defaults,{groupHeight:28,expanderWidth:20,singleSelect:true,remoteSort:false,fitColumns:true,loadMsg:"",frozenColumns:[[{field:"f",width:20,resizable:false}]],columns:[[{field:"name",title:"Name",width:100,sortable:true},{field:"value",title:"Value",width:100,resizable:false}]],showGroup:false,groupView:_18,groupField:"group",groupStyler:function(_80,_81){
return "";
},groupFormatter:function(_82,_83){
return _82;
}});
})(jQuery);

