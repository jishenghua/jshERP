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
function _1(_2){
var _3=$.data(_2,"treegrid");
var _4=_3.options;
$(_2).datagrid($.extend({},_4,{url:null,data:null,loader:function(){
return false;
},onBeforeLoad:function(){
return false;
},onLoadSuccess:function(){
},onResizeColumn:function(_5,_6){
_20(_2);
_4.onResizeColumn.call(_2,_5,_6);
},onSortColumn:function(_7,_8){
_4.sortName=_7;
_4.sortOrder=_8;
if(_4.remoteSort){
_1f(_2);
}else{
var _9=$(_2).treegrid("getData");
_39(_2,0,_9);
}
_4.onSortColumn.call(_2,_7,_8);
},onBeforeEdit:function(_a,_b){
if(_4.onBeforeEdit.call(_2,_b)==false){
return false;
}
},onAfterEdit:function(_c,_d,_e){
_4.onAfterEdit.call(_2,_d,_e);
},onCancelEdit:function(_f,row){
_4.onCancelEdit.call(_2,row);
},onSelect:function(_10){
_4.onSelect.call(_2,_41(_2,_10));
},onUnselect:function(_11){
_4.onUnselect.call(_2,_41(_2,_11));
},onSelectAll:function(){
_4.onSelectAll.call(_2,$.data(_2,"treegrid").data);
},onUnselectAll:function(){
_4.onUnselectAll.call(_2,$.data(_2,"treegrid").data);
},onCheck:function(_12){
_4.onCheck.call(_2,_41(_2,_12));
},onUncheck:function(_13){
_4.onUncheck.call(_2,_41(_2,_13));
},onCheckAll:function(){
_4.onCheckAll.call(_2,$.data(_2,"treegrid").data);
},onUncheckAll:function(){
_4.onUncheckAll.call(_2,$.data(_2,"treegrid").data);
},onClickRow:function(_14){
_4.onClickRow.call(_2,_41(_2,_14));
},onDblClickRow:function(_15){
_4.onDblClickRow.call(_2,_41(_2,_15));
},onClickCell:function(_16,_17){
_4.onClickCell.call(_2,_17,_41(_2,_16));
},onDblClickCell:function(_18,_19){
_4.onDblClickCell.call(_2,_19,_41(_2,_18));
},onRowContextMenu:function(e,_1a){
_4.onContextMenu.call(_2,e,_41(_2,_1a));
}}));
if(!_4.columns){
var _1b=$.data(_2,"datagrid").options;
_4.columns=_1b.columns;
_4.frozenColumns=_1b.frozenColumns;
}
_3.dc=$.data(_2,"datagrid").dc;
if(_4.pagination){
var _1c=$(_2).datagrid("getPager");
_1c.pagination({pageNumber:_4.pageNumber,pageSize:_4.pageSize,pageList:_4.pageList,onSelectPage:function(_1d,_1e){
_4.pageNumber=_1d;
_4.pageSize=_1e;
_1f(_2);
}});
_4.pageSize=_1c.pagination("options").pageSize;
}
};
function _20(_21,_22){
var _23=$.data(_21,"datagrid").options;
var dc=$.data(_21,"datagrid").dc;
if(!dc.body1.is(":empty")&&(!_23.nowrap||_23.autoRowHeight)){
if(_22!=undefined){
var _24=_25(_21,_22);
for(var i=0;i<_24.length;i++){
_26(_24[i][_23.idField]);
}
}
}
$(_21).datagrid("fixRowHeight",_22);
function _26(_27){
var tr1=_23.finder.getTr(_21,_27,"body",1);
var tr2=_23.finder.getTr(_21,_27,"body",2);
tr1.css("height","");
tr2.css("height","");
var _28=Math.max(tr1.height(),tr2.height());
tr1.css("height",_28);
tr2.css("height",_28);
};
};
function _29(_2a){
var dc=$.data(_2a,"datagrid").dc;
var _2b=$.data(_2a,"treegrid").options;
if(!_2b.rownumbers){
return;
}
dc.body1.find("div.datagrid-cell-rownumber").each(function(i){
$(this).html(i+1);
});
};
function _2c(_2d){
var dc=$.data(_2d,"datagrid").dc;
var _2e=dc.body1.add(dc.body2);
var _2f=($.data(_2e[0],"events")||$._data(_2e[0],"events")).click[0].handler;
dc.body1.add(dc.body2).bind("mouseover",function(e){
var tt=$(e.target);
var tr=tt.closest("tr.datagrid-row");
if(!tr.length){
return;
}
if(tt.hasClass("tree-hit")){
tt.hasClass("tree-expanded")?tt.addClass("tree-expanded-hover"):tt.addClass("tree-collapsed-hover");
}
e.stopPropagation();
}).bind("mouseout",function(e){
var tt=$(e.target);
var tr=tt.closest("tr.datagrid-row");
if(!tr.length){
return;
}
if(tt.hasClass("tree-hit")){
tt.hasClass("tree-expanded")?tt.removeClass("tree-expanded-hover"):tt.removeClass("tree-collapsed-hover");
}
e.stopPropagation();
}).unbind("click").bind("click",function(e){
var tt=$(e.target);
var tr=tt.closest("tr.datagrid-row");
if(!tr.length){
return;
}
if(tt.hasClass("tree-hit")){
_30(_2d,tr.attr("node-id"));
}else{
_2f(e);
}
e.stopPropagation();
});
};
function _31(_32,_33){
var _34=$.data(_32,"treegrid").options;
var tr1=_34.finder.getTr(_32,_33,"body",1);
var tr2=_34.finder.getTr(_32,_33,"body",2);
var _35=$(_32).datagrid("getColumnFields",true).length+(_34.rownumbers?1:0);
var _36=$(_32).datagrid("getColumnFields",false).length;
_37(tr1,_35);
_37(tr2,_36);
function _37(tr,_38){
$("<tr class=\"treegrid-tr-tree\">"+"<td style=\"border:0px\" colspan=\""+_38+"\">"+"<div></div>"+"</td>"+"</tr>").insertAfter(tr);
};
};
function _39(_3a,_3b,_3c,_3d){
var _3e=$.data(_3a,"treegrid");
var _3f=_3e.options;
var dc=_3e.dc;
_3c=_3f.loadFilter.call(_3a,_3c,_3b);
var _40=_41(_3a,_3b);
if(_40){
var _42=_3f.finder.getTr(_3a,_3b,"body",1);
var _43=_3f.finder.getTr(_3a,_3b,"body",2);
var cc1=_42.next("tr.treegrid-tr-tree").children("td").children("div");
var cc2=_43.next("tr.treegrid-tr-tree").children("td").children("div");
if(!_3d){
_40.children=[];
}
}else{
var cc1=dc.body1;
var cc2=dc.body2;
if(!_3d){
_3e.data=[];
}
}
if(!_3d){
cc1.empty();
cc2.empty();
}
if(_3f.view.onBeforeRender){
_3f.view.onBeforeRender.call(_3f.view,_3a,_3b,_3c);
}
_3f.view.render.call(_3f.view,_3a,cc1,true);
_3f.view.render.call(_3f.view,_3a,cc2,false);
if(_3f.showFooter){
_3f.view.renderFooter.call(_3f.view,_3a,dc.footer1,true);
_3f.view.renderFooter.call(_3f.view,_3a,dc.footer2,false);
}
if(_3f.view.onAfterRender){
_3f.view.onAfterRender.call(_3f.view,_3a);
}
_3f.onLoadSuccess.call(_3a,_40,_3c);
if(!_3b&&_3f.pagination){
var _44=$.data(_3a,"treegrid").total;
var _45=$(_3a).datagrid("getPager");
if(_45.pagination("options").total!=_44){
_45.pagination({total:_44});
}
}
_20(_3a);
_29(_3a);
$(_3a).treegrid("autoSizeColumn");
};
function _1f(_46,_47,_48,_49,_4a){
var _4b=$.data(_46,"treegrid").options;
var _4c=$(_46).datagrid("getPanel").find("div.datagrid-body");
if(_48){
_4b.queryParams=_48;
}
var _4d=$.extend({},_4b.queryParams);
if(_4b.pagination){
$.extend(_4d,{page:_4b.pageNumber,rows:_4b.pageSize});
}
if(_4b.sortName){
$.extend(_4d,{sort:_4b.sortName,order:_4b.sortOrder});
}
var row=_41(_46,_47);
if(_4b.onBeforeLoad.call(_46,row,_4d)==false){
return;
}
var _4e=_4c.find("tr[node-id=\""+_47+"\"] span.tree-folder");
_4e.addClass("tree-loading");
$(_46).treegrid("loading");
var _4f=_4b.loader.call(_46,_4d,function(_50){
_4e.removeClass("tree-loading");
$(_46).treegrid("loaded");
_39(_46,_47,_50,_49);
if(_4a){
_4a();
}
},function(){
_4e.removeClass("tree-loading");
$(_46).treegrid("loaded");
_4b.onLoadError.apply(_46,arguments);
if(_4a){
_4a();
}
});
if(_4f==false){
_4e.removeClass("tree-loading");
$(_46).treegrid("loaded");
}
};
function _51(_52){
var _53=_54(_52);
if(_53.length){
return _53[0];
}else{
return null;
}
};
function _54(_55){
return $.data(_55,"treegrid").data;
};
function _56(_57,_58){
var row=_41(_57,_58);
if(row._parentId){
return _41(_57,row._parentId);
}else{
return null;
}
};
function _25(_59,_5a){
var _5b=$.data(_59,"treegrid").options;
var _5c=$(_59).datagrid("getPanel").find("div.datagrid-view2 div.datagrid-body");
var _5d=[];
if(_5a){
_5e(_5a);
}else{
var _5f=_54(_59);
for(var i=0;i<_5f.length;i++){
_5d.push(_5f[i]);
_5e(_5f[i][_5b.idField]);
}
}
function _5e(_60){
var _61=_41(_59,_60);
if(_61&&_61.children){
for(var i=0,len=_61.children.length;i<len;i++){
var _62=_61.children[i];
_5d.push(_62);
_5e(_62[_5b.idField]);
}
}
};
return _5d;
};
function _63(_64){
var _65=_66(_64);
if(_65.length){
return _65[0];
}else{
return null;
}
};
function _66(_67){
var _68=[];
var _69=$(_67).datagrid("getPanel");
_69.find("div.datagrid-view2 div.datagrid-body tr.datagrid-row-selected").each(function(){
var id=$(this).attr("node-id");
_68.push(_41(_67,id));
});
return _68;
};
function _6a(_6b,_6c){
if(!_6c){
return 0;
}
var _6d=$.data(_6b,"treegrid").options;
var _6e=$(_6b).datagrid("getPanel").children("div.datagrid-view");
var _6f=_6e.find("div.datagrid-body tr[node-id=\""+_6c+"\"]").children("td[field=\""+_6d.treeField+"\"]");
return _6f.find("span.tree-indent,span.tree-hit").length;
};
function _41(_70,_71){
var _72=$.data(_70,"treegrid").options;
var _73=$.data(_70,"treegrid").data;
var cc=[_73];
while(cc.length){
var c=cc.shift();
for(var i=0;i<c.length;i++){
var _74=c[i];
if(_74[_72.idField]==_71){
return _74;
}else{
if(_74["children"]){
cc.push(_74["children"]);
}
}
}
}
return null;
};
function _75(_76,_77){
var _78=$.data(_76,"treegrid").options;
var row=_41(_76,_77);
var tr=_78.finder.getTr(_76,_77);
var hit=tr.find("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-collapsed")){
return;
}
if(_78.onBeforeCollapse.call(_76,row)==false){
return;
}
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
hit.next().removeClass("tree-folder-open");
row.state="closed";
tr=tr.next("tr.treegrid-tr-tree");
var cc=tr.children("td").children("div");
if(_78.animate){
cc.slideUp("normal",function(){
$(_76).treegrid("autoSizeColumn");
_20(_76,_77);
_78.onCollapse.call(_76,row);
});
}else{
cc.hide();
$(_76).treegrid("autoSizeColumn");
_20(_76,_77);
_78.onCollapse.call(_76,row);
}
};
function _79(_7a,_7b){
var _7c=$.data(_7a,"treegrid").options;
var tr=_7c.finder.getTr(_7a,_7b);
var hit=tr.find("span.tree-hit");
var row=_41(_7a,_7b);
if(hit.length==0){
return;
}
if(hit.hasClass("tree-expanded")){
return;
}
if(_7c.onBeforeExpand.call(_7a,row)==false){
return;
}
hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
hit.next().addClass("tree-folder-open");
var _7d=tr.next("tr.treegrid-tr-tree");
if(_7d.length){
var cc=_7d.children("td").children("div");
_7e(cc);
}else{
_31(_7a,row[_7c.idField]);
var _7d=tr.next("tr.treegrid-tr-tree");
var cc=_7d.children("td").children("div");
cc.hide();
var _7f=$.extend({},_7c.queryParams||{});
_7f.id=row[_7c.idField];
_1f(_7a,row[_7c.idField],_7f,true,function(){
if(cc.is(":empty")){
_7d.remove();
}else{
_7e(cc);
}
});
}
function _7e(cc){
row.state="open";
if(_7c.animate){
cc.slideDown("normal",function(){
$(_7a).treegrid("autoSizeColumn");
_20(_7a,_7b);
_7c.onExpand.call(_7a,row);
});
}else{
cc.show();
$(_7a).treegrid("autoSizeColumn");
_20(_7a,_7b);
_7c.onExpand.call(_7a,row);
}
};
};
function _30(_80,_81){
var _82=$.data(_80,"treegrid").options;
var tr=_82.finder.getTr(_80,_81);
var hit=tr.find("span.tree-hit");
if(hit.hasClass("tree-expanded")){
_75(_80,_81);
}else{
_79(_80,_81);
}
};
function _83(_84,_85){
var _86=$.data(_84,"treegrid").options;
var _87=_25(_84,_85);
if(_85){
_87.unshift(_41(_84,_85));
}
for(var i=0;i<_87.length;i++){
_75(_84,_87[i][_86.idField]);
}
};
function _88(_89,_8a){
var _8b=$.data(_89,"treegrid").options;
var _8c=_25(_89,_8a);
if(_8a){
_8c.unshift(_41(_89,_8a));
}
for(var i=0;i<_8c.length;i++){
_79(_89,_8c[i][_8b.idField]);
}
};
function _8d(_8e,_8f){
var _90=$.data(_8e,"treegrid").options;
var ids=[];
var p=_56(_8e,_8f);
while(p){
var id=p[_90.idField];
ids.unshift(id);
p=_56(_8e,id);
}
for(var i=0;i<ids.length;i++){
_79(_8e,ids[i]);
}
};
function _91(_92,_93){
var _94=$.data(_92,"treegrid").options;
if(_93.parent){
var tr=_94.finder.getTr(_92,_93.parent);
if(tr.next("tr.treegrid-tr-tree").length==0){
_31(_92,_93.parent);
}
var _95=tr.children("td[field=\""+_94.treeField+"\"]").children("div.datagrid-cell");
var _96=_95.children("span.tree-icon");
if(_96.hasClass("tree-file")){
_96.removeClass("tree-file").addClass("tree-folder tree-folder-open");
var hit=$("<span class=\"tree-hit tree-expanded\"></span>").insertBefore(_96);
if(hit.prev().length){
hit.prev().remove();
}
}
}
_39(_92,_93.parent,_93.data,true);
};
function _97(_98,_99){
var ref=_99.before||_99.after;
var _9a=$.data(_98,"treegrid").options;
var _9b=_56(_98,ref);
_91(_98,{parent:(_9b?_9b[_9a.idField]:null),data:[_99.data]});
_9c(true);
_9c(false);
_29(_98);
function _9c(_9d){
var _9e=_9d?1:2;
var tr=_9a.finder.getTr(_98,_99.data[_9a.idField],"body",_9e);
var _9f=tr.closest("table.datagrid-btable");
tr=tr.parent().children();
var _a0=_9a.finder.getTr(_98,ref,"body",_9e);
if(_99.before){
tr.insertBefore(_a0);
}else{
var sub=_a0.next("tr.treegrid-tr-tree");
tr.insertAfter(sub.length?sub:_a0);
}
_9f.remove();
};
};
function _a1(_a2,_a3){
var _a4=$.data(_a2,"treegrid").options;
var tr=_a4.finder.getTr(_a2,_a3);
tr.next("tr.treegrid-tr-tree").remove();
tr.remove();
var _a5=del(_a3);
if(_a5){
if(_a5.children.length==0){
tr=_a4.finder.getTr(_a2,_a5[_a4.idField]);
tr.next("tr.treegrid-tr-tree").remove();
var _a6=tr.children("td[field=\""+_a4.treeField+"\"]").children("div.datagrid-cell");
_a6.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
_a6.find(".tree-hit").remove();
$("<span class=\"tree-indent\"></span>").prependTo(_a6);
}
}
_29(_a2);
function del(id){
var cc;
var _a7=_56(_a2,_a3);
if(_a7){
cc=_a7.children;
}else{
cc=$(_a2).treegrid("getData");
}
for(var i=0;i<cc.length;i++){
if(cc[i][_a4.idField]==id){
cc.splice(i,1);
break;
}
}
return _a7;
};
};
$.fn.treegrid=function(_a8,_a9){
if(typeof _a8=="string"){
var _aa=$.fn.treegrid.methods[_a8];
if(_aa){
return _aa(this,_a9);
}else{
return this.datagrid(_a8,_a9);
}
}
_a8=_a8||{};
return this.each(function(){
var _ab=$.data(this,"treegrid");
if(_ab){
$.extend(_ab.options,_a8);
}else{
_ab=$.data(this,"treegrid",{options:$.extend({},$.fn.treegrid.defaults,$.fn.treegrid.parseOptions(this),_a8),data:[]});
}
_1(this);
if(_ab.options.data){
$(this).treegrid("loadData",_ab.options.data);
}
_1f(this);
_2c(this);
});
};
$.fn.treegrid.methods={options:function(jq){
return $.data(jq[0],"treegrid").options;
},resize:function(jq,_ac){
return jq.each(function(){
$(this).datagrid("resize",_ac);
});
},fixRowHeight:function(jq,_ad){
return jq.each(function(){
_20(this,_ad);
});
},loadData:function(jq,_ae){
return jq.each(function(){
_39(this,_ae.parent,_ae);
});
},load:function(jq,_af){
return jq.each(function(){
$(this).treegrid("options").pageNumber=1;
$(this).treegrid("getPager").pagination({pageNumber:1});
$(this).treegrid("reload",_af);
});
},reload:function(jq,id){
return jq.each(function(){
var _b0=$(this).treegrid("options");
var _b1={};
if(typeof id=="object"){
_b1=id;
}else{
_b1=$.extend({},_b0.queryParams);
_b1.id=id;
}
if(_b1.id){
var _b2=$(this).treegrid("find",_b1.id);
if(_b2.children){
_b2.children.splice(0,_b2.children.length);
}
_b0.queryParams=_b1;
var tr=_b0.finder.getTr(this,_b1.id);
tr.next("tr.treegrid-tr-tree").remove();
tr.find("span.tree-hit").removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
_79(this,_b1.id);
}else{
_1f(this,null,_b1);
}
});
},reloadFooter:function(jq,_b3){
return jq.each(function(){
var _b4=$.data(this,"treegrid").options;
var dc=$.data(this,"datagrid").dc;
if(_b3){
$.data(this,"treegrid").footer=_b3;
}
if(_b4.showFooter){
_b4.view.renderFooter.call(_b4.view,this,dc.footer1,true);
_b4.view.renderFooter.call(_b4.view,this,dc.footer2,false);
if(_b4.view.onAfterRender){
_b4.view.onAfterRender.call(_b4.view,this);
}
$(this).treegrid("fixRowHeight");
}
});
},getData:function(jq){
return $.data(jq[0],"treegrid").data;
},getFooterRows:function(jq){
return $.data(jq[0],"treegrid").footer;
},getRoot:function(jq){
return _51(jq[0]);
},getRoots:function(jq){
return _54(jq[0]);
},getParent:function(jq,id){
return _56(jq[0],id);
},getChildren:function(jq,id){
return _25(jq[0],id);
},getSelected:function(jq){
return _63(jq[0]);
},getSelections:function(jq){
return _66(jq[0]);
},getLevel:function(jq,id){
return _6a(jq[0],id);
},find:function(jq,id){
return _41(jq[0],id);
},isLeaf:function(jq,id){
var _b5=$.data(jq[0],"treegrid").options;
var tr=_b5.finder.getTr(jq[0],id);
var hit=tr.find("span.tree-hit");
return hit.length==0;
},select:function(jq,id){
return jq.each(function(){
$(this).datagrid("selectRow",id);
});
},unselect:function(jq,id){
return jq.each(function(){
$(this).datagrid("unselectRow",id);
});
},collapse:function(jq,id){
return jq.each(function(){
_75(this,id);
});
},expand:function(jq,id){
return jq.each(function(){
_79(this,id);
});
},toggle:function(jq,id){
return jq.each(function(){
_30(this,id);
});
},collapseAll:function(jq,id){
return jq.each(function(){
_83(this,id);
});
},expandAll:function(jq,id){
return jq.each(function(){
_88(this,id);
});
},expandTo:function(jq,id){
return jq.each(function(){
_8d(this,id);
});
},append:function(jq,_b6){
return jq.each(function(){
_91(this,_b6);
});
},insert:function(jq,_b7){
return jq.each(function(){
_97(this,_b7);
});
},remove:function(jq,id){
return jq.each(function(){
_a1(this,id);
});
},pop:function(jq,id){
var row=jq.treegrid("find",id);
jq.treegrid("remove",id);
return row;
},refresh:function(jq,id){
return jq.each(function(){
var _b8=$.data(this,"treegrid").options;
_b8.view.refreshRow.call(_b8.view,this,id);
});
},update:function(jq,_b9){
return jq.each(function(){
var _ba=$.data(this,"treegrid").options;
_ba.view.updateRow.call(_ba.view,this,_b9.id,_b9.row);
});
},beginEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("beginEdit",id);
$(this).treegrid("fixRowHeight",id);
});
},endEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("endEdit",id);
});
},cancelEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("cancelEdit",id);
});
}};
$.fn.treegrid.parseOptions=function(_bb){
return $.extend({},$.fn.datagrid.parseOptions(_bb),$.parser.parseOptions(_bb,["treeField",{animate:"boolean"}]));
};
var _bc=$.extend({},$.fn.datagrid.defaults.view,{render:function(_bd,_be,_bf){
var _c0=$.data(_bd,"treegrid").options;
var _c1=$(_bd).datagrid("getColumnFields",_bf);
var _c2=$.data(_bd,"datagrid").rowIdPrefix;
if(_bf){
if(!(_c0.rownumbers||(_c0.frozenColumns&&_c0.frozenColumns.length))){
return;
}
}
var _c3=0;
var _c4=this;
var _c5=_c6(_bf,this.treeLevel,this.treeNodes);
$(_be).append(_c5.join(""));
function _c6(_c7,_c8,_c9){
var _ca=["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_c9.length;i++){
var row=_c9[i];
if(row.state!="open"&&row.state!="closed"){
row.state="open";
}
var css=_c0.rowStyler?_c0.rowStyler.call(_bd,row):"";
var _cb="";
var _cc="";
if(typeof css=="string"){
_cc=css;
}else{
if(css){
_cb=css["class"]||"";
_cc=css["style"]||"";
}
}
var cls="class=\"datagrid-row "+(_c3++%2&&_c0.striped?"datagrid-row-alt ":" ")+_cb+"\"";
var _cd=_cc?"style=\""+_cc+"\"":"";
var _ce=_c2+"-"+(_c7?1:2)+"-"+row[_c0.idField];
_ca.push("<tr id=\""+_ce+"\" node-id=\""+row[_c0.idField]+"\" "+cls+" "+_cd+">");
_ca=_ca.concat(_c4.renderRow.call(_c4,_bd,_c1,_c7,_c8,row));
_ca.push("</tr>");
if(row.children&&row.children.length){
var tt=_c6(_c7,_c8+1,row.children);
var v=row.state=="closed"?"none":"block";
_ca.push("<tr class=\"treegrid-tr-tree\"><td style=\"border:0px\" colspan="+(_c1.length+(_c0.rownumbers?1:0))+"><div style=\"display:"+v+"\">");
_ca=_ca.concat(tt);
_ca.push("</div></td></tr>");
}
}
_ca.push("</tbody></table>");
return _ca;
};
},renderFooter:function(_cf,_d0,_d1){
var _d2=$.data(_cf,"treegrid").options;
var _d3=$.data(_cf,"treegrid").footer||[];
var _d4=$(_cf).datagrid("getColumnFields",_d1);
var _d5=["<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_d3.length;i++){
var row=_d3[i];
row[_d2.idField]=row[_d2.idField]||("foot-row-id"+i);
_d5.push("<tr class=\"datagrid-row\" node-id=\""+row[_d2.idField]+"\">");
_d5.push(this.renderRow.call(this,_cf,_d4,_d1,0,row));
_d5.push("</tr>");
}
_d5.push("</tbody></table>");
$(_d0).html(_d5.join(""));
},renderRow:function(_d6,_d7,_d8,_d9,row){
var _da=$.data(_d6,"treegrid").options;
var cc=[];
if(_d8&&_da.rownumbers){
cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">0</div></td>");
}
for(var i=0;i<_d7.length;i++){
var _db=_d7[i];
var col=$(_d6).datagrid("getColumnOption",_db);
if(col){
var css=col.styler?(col.styler(row[_db],row)||""):"";
var _dc="";
var _dd="";
if(typeof css=="string"){
_dd=css;
}else{
if(cc){
_dc=css["class"]||"";
_dd=css["style"]||"";
}
}
var cls=_dc?"class=\""+_dc+"\"":"";
var _de=col.hidden?"style=\"display:none;"+_dd+"\"":(_dd?"style=\""+_dd+"\"":"");
cc.push("<td field=\""+_db+"\" "+cls+" "+_de+">");
if(col.checkbox){
var _de="";
}else{
var _de=_dd;
if(col.align){
_de+=";text-align:"+col.align+";";
}
if(!_da.nowrap){
_de+=";white-space:normal;height:auto;";
}else{
if(_da.autoRowHeight){
_de+=";height:auto;";
}
}
}
cc.push("<div style=\""+_de+"\" ");
if(col.checkbox){
cc.push("class=\"datagrid-cell-check ");
}else{
cc.push("class=\"datagrid-cell "+col.cellClass);
}
cc.push("\">");
if(col.checkbox){
if(row.checked){
cc.push("<input type=\"checkbox\" checked=\"checked\"");
}else{
cc.push("<input type=\"checkbox\"");
}
cc.push(" name=\""+_db+"\" value=\""+(row[_db]!=undefined?row[_db]:"")+"\"/>");
}else{
var val=null;
if(col.formatter){
val=col.formatter(row[_db],row);
}else{
val=row[_db];
}
if(_db==_da.treeField){
for(var j=0;j<_d9;j++){
cc.push("<span class=\"tree-indent\"></span>");
}
if(row.state=="closed"){
cc.push("<span class=\"tree-hit tree-collapsed\"></span>");
cc.push("<span class=\"tree-icon tree-folder "+(row.iconCls?row.iconCls:"")+"\"></span>");
}else{
if(row.children&&row.children.length){
cc.push("<span class=\"tree-hit tree-expanded\"></span>");
cc.push("<span class=\"tree-icon tree-folder tree-folder-open "+(row.iconCls?row.iconCls:"")+"\"></span>");
}else{
cc.push("<span class=\"tree-indent\"></span>");
cc.push("<span class=\"tree-icon tree-file "+(row.iconCls?row.iconCls:"")+"\"></span>");
}
}
cc.push("<span class=\"tree-title\">"+val+"</span>");
}else{
cc.push(val);
}
}
cc.push("</div>");
cc.push("</td>");
}
}
return cc.join("");
},refreshRow:function(_df,id){
this.updateRow.call(this,_df,id,{});
},updateRow:function(_e0,id,row){
var _e1=$.data(_e0,"treegrid").options;
var _e2=$(_e0).treegrid("find",id);
$.extend(_e2,row);
var _e3=$(_e0).treegrid("getLevel",id)-1;
var _e4=_e1.rowStyler?_e1.rowStyler.call(_e0,_e2):"";
function _e5(_e6){
var _e7=$(_e0).treegrid("getColumnFields",_e6);
var tr=_e1.finder.getTr(_e0,id,"body",(_e6?1:2));
var _e8=tr.find("div.datagrid-cell-rownumber").html();
var _e9=tr.find("div.datagrid-cell-check input[type=checkbox]").is(":checked");
tr.html(this.renderRow(_e0,_e7,_e6,_e3,_e2));
tr.attr("style",_e4||"");
tr.find("div.datagrid-cell-rownumber").html(_e8);
if(_e9){
tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked",true);
}
};
_e5.call(this,true);
_e5.call(this,false);
$(_e0).treegrid("fixRowHeight",id);
},onBeforeRender:function(_ea,_eb,_ec){
if($.isArray(_eb)){
_ec={total:_eb.length,rows:_eb};
_eb=null;
}
if(!_ec){
return false;
}
var _ed=$.data(_ea,"treegrid");
var _ee=_ed.options;
if(_ec.length==undefined){
if(_ec.footer){
_ed.footer=_ec.footer;
}
if(_ec.total){
_ed.total=_ec.total;
}
_ec=this.transfer(_ea,_eb,_ec.rows);
}else{
function _ef(_f0,_f1){
for(var i=0;i<_f0.length;i++){
var row=_f0[i];
row._parentId=_f1;
if(row.children&&row.children.length){
_ef(row.children,row[_ee.idField]);
}
}
};
_ef(_ec,_eb);
}
var _f2=_41(_ea,_eb);
if(_f2){
if(_f2.children){
_f2.children=_f2.children.concat(_ec);
}else{
_f2.children=_ec;
}
}else{
_ed.data=_ed.data.concat(_ec);
}
this.sort(_ea,_ec);
this.treeNodes=_ec;
this.treeLevel=$(_ea).treegrid("getLevel",_eb);
},sort:function(_f3,_f4){
var _f5=$.data(_f3,"treegrid").options;
if(!_f5.remoteSort&&_f5.sortName){
var _f6=_f5.sortName.split(",");
var _f7=_f5.sortOrder.split(",");
_f8(_f4);
}
function _f8(_f9){
_f9.sort(function(r1,r2){
var r=0;
for(var i=0;i<_f6.length;i++){
var sn=_f6[i];
var so=_f7[i];
var col=$(_f3).treegrid("getColumnOption",sn);
var _fa=col.sorter||function(a,b){
return a==b?0:(a>b?1:-1);
};
r=_fa(r1[sn],r2[sn])*(so=="asc"?1:-1);
if(r!=0){
return r;
}
}
return r;
});
for(var i=0;i<_f9.length;i++){
var _fb=_f9[i].children;
if(_fb&&_fb.length){
_f8(_fb);
}
}
};
},transfer:function(_fc,_fd,_fe){
var _ff=$.data(_fc,"treegrid").options;
var rows=[];
for(var i=0;i<_fe.length;i++){
rows.push(_fe[i]);
}
var _100=[];
for(var i=0;i<rows.length;i++){
var row=rows[i];
if(!_fd){
if(!row._parentId){
_100.push(row);
rows.splice(i,1);
i--;
}
}else{
if(row._parentId==_fd){
_100.push(row);
rows.splice(i,1);
i--;
}
}
}
var toDo=[];
for(var i=0;i<_100.length;i++){
toDo.push(_100[i]);
}
while(toDo.length){
var node=toDo.shift();
for(var i=0;i<rows.length;i++){
var row=rows[i];
if(row._parentId==node[_ff.idField]){
if(node.children){
node.children.push(row);
}else{
node.children=[row];
}
toDo.push(row);
rows.splice(i,1);
i--;
}
}
}
return _100;
}});
$.fn.treegrid.defaults=$.extend({},$.fn.datagrid.defaults,{treeField:null,animate:false,singleSelect:true,view:_bc,loader:function(_101,_102,_103){
var opts=$(this).treegrid("options");
if(!opts.url){
return false;
}
$.ajax({type:opts.method,url:opts.url,data:_101,dataType:"json",success:function(data){
_102(data);
},error:function(){
_103.apply(this,arguments);
}});
},loadFilter:function(data,_104){
return data;
},finder:{getTr:function(_105,id,type,_106){
type=type||"body";
_106=_106||0;
var dc=$.data(_105,"datagrid").dc;
if(_106==0){
var opts=$.data(_105,"treegrid").options;
var tr1=opts.finder.getTr(_105,id,type,1);
var tr2=opts.finder.getTr(_105,id,type,2);
return tr1.add(tr2);
}else{
if(type=="body"){
var tr=$("#"+$.data(_105,"datagrid").rowIdPrefix+"-"+_106+"-"+id);
if(!tr.length){
tr=(_106==1?dc.body1:dc.body2).find("tr[node-id=\""+id+"\"]");
}
return tr;
}else{
if(type=="footer"){
return (_106==1?dc.footer1:dc.footer2).find("tr[node-id=\""+id+"\"]");
}else{
if(type=="selected"){
return (_106==1?dc.body1:dc.body2).find("tr.datagrid-row-selected");
}else{
if(type=="highlight"){
return (_106==1?dc.body1:dc.body2).find("tr.datagrid-row-over");
}else{
if(type=="checked"){
return (_106==1?dc.body1:dc.body2).find("tr.datagrid-row-checked");
}else{
if(type=="last"){
return (_106==1?dc.body1:dc.body2).find("tr:last[node-id]");
}else{
if(type=="allbody"){
return (_106==1?dc.body1:dc.body2).find("tr[node-id]");
}else{
if(type=="allfooter"){
return (_106==1?dc.footer1:dc.footer2).find("tr[node-id]");
}
}
}
}
}
}
}
}
}
},getRow:function(_107,p){
var id=(typeof p=="object")?p.attr("node-id"):p;
return $(_107).treegrid("find",id);
}},onBeforeLoad:function(row,_108){
},onLoadSuccess:function(row,data){
},onLoadError:function(){
},onBeforeCollapse:function(row){
},onCollapse:function(row){
},onBeforeExpand:function(row){
},onExpand:function(row){
},onClickRow:function(row){
},onDblClickRow:function(row){
},onClickCell:function(_109,row){
},onDblClickCell:function(_10a,row){
},onContextMenu:function(e,row){
},onBeforeEdit:function(row){
},onAfterEdit:function(row,_10b){
},onCancelEdit:function(row){
}});
})(jQuery);

