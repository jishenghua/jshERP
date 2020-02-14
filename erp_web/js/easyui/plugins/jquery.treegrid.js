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
var _3=$.data(_2,"treegrid");
var _4=_3.options;
$(_2).datagrid($.extend({},_4,{url:null,data:null,loader:function(){
return false;
},onBeforeLoad:function(){
return false;
},onLoadSuccess:function(){
},onResizeColumn:function(_5,_6){
_16(_2);
_4.onResizeColumn.call(_2,_5,_6);
},onBeforeSortColumn:function(_7,_8){
if(_4.onBeforeSortColumn.call(_2,_7,_8)==false){
return false;
}
},onSortColumn:function(_9,_a){
_4.sortName=_9;
_4.sortOrder=_a;
if(_4.remoteSort){
_15(_2);
}else{
var _b=$(_2).treegrid("getData");
_56(_2,null,_b);
}
_4.onSortColumn.call(_2,_9,_a);
},onClickCell:function(_c,_d){
_4.onClickCell.call(_2,_d,_37(_2,_c));
},onDblClickCell:function(_e,_f){
_4.onDblClickCell.call(_2,_f,_37(_2,_e));
},onRowContextMenu:function(e,_10){
_4.onContextMenu.call(_2,e,_37(_2,_10));
}}));
var _11=$.data(_2,"datagrid").options;
_4.columns=_11.columns;
_4.frozenColumns=_11.frozenColumns;
_3.dc=$.data(_2,"datagrid").dc;
if(_4.pagination){
var _12=$(_2).datagrid("getPager");
_12.pagination({total:0,pageNumber:_4.pageNumber,pageSize:_4.pageSize,pageList:_4.pageList,onSelectPage:function(_13,_14){
_4.pageNumber=_13||1;
_4.pageSize=_14;
_12.pagination("refresh",{pageNumber:_13,pageSize:_14});
_15(_2);
}});
_4.pageSize=_12.pagination("options").pageSize;
}
};
function _16(_17,_18){
var _19=$.data(_17,"datagrid").options;
var dc=$.data(_17,"datagrid").dc;
if(!dc.body1.is(":empty")&&(!_19.nowrap||_19.autoRowHeight)){
if(_18!=undefined){
var _1a=_1b(_17,_18);
for(var i=0;i<_1a.length;i++){
_1c(_1a[i][_19.idField]);
}
}
}
$(_17).datagrid("fixRowHeight",_18);
function _1c(_1d){
var tr1=_19.finder.getTr(_17,_1d,"body",1);
var tr2=_19.finder.getTr(_17,_1d,"body",2);
tr1.css("height","");
tr2.css("height","");
var _1e=Math.max(tr1.height(),tr2.height());
tr1.css("height",_1e);
tr2.css("height",_1e);
};
};
function _1f(_20){
var dc=$.data(_20,"datagrid").dc;
var _21=$.data(_20,"treegrid").options;
if(!_21.rownumbers){
return;
}
dc.body1.find("div.datagrid-cell-rownumber").each(function(i){
$(this).html(i+1);
});
};
function _22(_23){
return function(e){
$.fn.datagrid.defaults.rowEvents[_23?"mouseover":"mouseout"](e);
var tt=$(e.target);
var fn=_23?"addClass":"removeClass";
if(tt.hasClass("tree-hit")){
tt.hasClass("tree-expanded")?tt[fn]("tree-expanded-hover"):tt[fn]("tree-collapsed-hover");
}
};
};
function _24(e){
var tt=$(e.target);
var tr=tt.closest("tr.datagrid-row");
if(!tr.length||!tr.parent().length){
return;
}
var _25=tr.attr("node-id");
var _26=_27(tr);
if(tt.hasClass("tree-hit")){
_28(_26,_25);
}else{
if(tt.hasClass("tree-checkbox")){
_29(_26,_25);
}else{
var _2a=$(_26).datagrid("options");
if(!tt.parent().hasClass("datagrid-cell-check")&&!_2a.singleSelect&&e.shiftKey){
var _2b=$(_26).treegrid("getChildren");
var _2c=$.easyui.indexOfArray(_2b,_2a.idField,_2a.lastSelectedIndex);
var _2d=$.easyui.indexOfArray(_2b,_2a.idField,_25);
var _2e=Math.min(Math.max(_2c,0),_2d);
var to=Math.max(_2c,_2d);
var row=_2b[_2d];
var td=tt.closest("td[field]",tr);
if(td.length){
var _2f=td.attr("field");
_2a.onClickCell.call(_26,_25,_2f,row[_2f]);
}
$(_26).treegrid("clearSelections");
for(var i=_2e;i<=to;i++){
$(_26).treegrid("selectRow",_2b[i][_2a.idField]);
}
_2a.onClickRow.call(_26,row);
}else{
$.fn.datagrid.defaults.rowEvents.click(e);
}
}
}
};
function _27(t){
return $(t).closest("div.datagrid-view").children(".datagrid-f")[0];
};
function _29(_30,_31,_32,_33){
var _34=$.data(_30,"treegrid");
var _35=_34.checkedRows;
var _36=_34.options;
if(!_36.checkbox){
return;
}
var row=_37(_30,_31);
if(!row.checkState){
return;
}
var tr=_36.finder.getTr(_30,_31);
var ck=tr.find(".tree-checkbox");
if(_32==undefined){
if(ck.hasClass("tree-checkbox1")){
_32=false;
}else{
if(ck.hasClass("tree-checkbox0")){
_32=true;
}else{
if(row._checked==undefined){
row._checked=ck.hasClass("tree-checkbox1");
}
_32=!row._checked;
}
}
}
row._checked=_32;
if(_32){
if(ck.hasClass("tree-checkbox1")){
return;
}
}else{
if(ck.hasClass("tree-checkbox0")){
return;
}
}
if(!_33){
if(_36.onBeforeCheckNode.call(_30,row,_32)==false){
return;
}
}
if(_36.cascadeCheck){
_38(_30,row,_32);
_39(_30,row);
}else{
_3a(_30,row,_32?"1":"0");
}
if(!_33){
_36.onCheckNode.call(_30,row,_32);
}
};
function _3a(_3b,row,_3c){
var _3d=$.data(_3b,"treegrid");
var _3e=_3d.checkedRows;
var _3f=_3d.options;
if(!row.checkState||_3c==undefined){
return;
}
var tr=_3f.finder.getTr(_3b,row[_3f.idField]);
var ck=tr.find(".tree-checkbox");
if(!ck.length){
return;
}
row.checkState=["unchecked","checked","indeterminate"][_3c];
row.checked=(row.checkState=="checked");
ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
ck.addClass("tree-checkbox"+_3c);
if(_3c==0){
$.easyui.removeArrayItem(_3e,_3f.idField,row[_3f.idField]);
}else{
$.easyui.addArrayItem(_3e,_3f.idField,row);
}
};
function _38(_40,row,_41){
var _42=_41?1:0;
_3a(_40,row,_42);
$.easyui.forEach(row.children||[],true,function(r){
_3a(_40,r,_42);
});
};
function _39(_43,row){
var _44=$.data(_43,"treegrid").options;
var _45=_46(_43,row[_44.idField]);
if(_45){
_3a(_43,_45,_47(_45));
_39(_43,_45);
}
};
function _47(row){
var len=0;
var c0=0;
var c1=0;
$.easyui.forEach(row.children||[],false,function(r){
if(r.checkState){
len++;
if(r.checkState=="checked"){
c1++;
}else{
if(r.checkState=="unchecked"){
c0++;
}
}
}
});
if(len==0){
return undefined;
}
var _48=0;
if(c0==len){
_48=0;
}else{
if(c1==len){
_48=1;
}else{
_48=2;
}
}
return _48;
};
function _49(_4a,_4b){
var _4c=$.data(_4a,"treegrid").options;
if(!_4c.checkbox){
return;
}
var row=_37(_4a,_4b);
var tr=_4c.finder.getTr(_4a,_4b);
var ck=tr.find(".tree-checkbox");
if(_4c.view.hasCheckbox(_4a,row)){
if(!ck.length){
row.checkState=row.checkState||"unchecked";
$("<span class=\"tree-checkbox\"></span>").insertBefore(tr.find(".tree-title"));
}
if(row.checkState=="checked"){
_29(_4a,_4b,true,true);
}else{
if(row.checkState=="unchecked"){
_29(_4a,_4b,false,true);
}else{
var _4d=_47(row);
if(_4d===0){
_29(_4a,_4b,false,true);
}else{
if(_4d===1){
_29(_4a,_4b,true,true);
}
}
}
}
}else{
ck.remove();
row.checkState=undefined;
row.checked=undefined;
_39(_4a,row);
}
};
function _4e(_4f,_50){
var _51=$.data(_4f,"treegrid").options;
var tr1=_51.finder.getTr(_4f,_50,"body",1);
var tr2=_51.finder.getTr(_4f,_50,"body",2);
var _52=$(_4f).datagrid("getColumnFields",true).length+(_51.rownumbers?1:0);
var _53=$(_4f).datagrid("getColumnFields",false).length;
_54(tr1,_52);
_54(tr2,_53);
function _54(tr,_55){
$("<tr class=\"treegrid-tr-tree\">"+"<td style=\"border:0px\" colspan=\""+_55+"\">"+"<div></div>"+"</td>"+"</tr>").insertAfter(tr);
};
};
function _56(_57,_58,_59,_5a,_5b){
var _5c=$.data(_57,"treegrid");
var _5d=_5c.options;
var dc=_5c.dc;
_59=_5d.loadFilter.call(_57,_59,_58);
var _5e=_37(_57,_58);
if(_5e){
var _5f=_5d.finder.getTr(_57,_58,"body",1);
var _60=_5d.finder.getTr(_57,_58,"body",2);
var cc1=_5f.next("tr.treegrid-tr-tree").children("td").children("div");
var cc2=_60.next("tr.treegrid-tr-tree").children("td").children("div");
if(!_5a){
_5e.children=[];
}
}else{
var cc1=dc.body1;
var cc2=dc.body2;
if(!_5a){
_5c.data=[];
}
}
if(!_5a){
cc1.empty();
cc2.empty();
}
if(_5d.view.onBeforeRender){
_5d.view.onBeforeRender.call(_5d.view,_57,_58,_59);
}
_5d.view.render.call(_5d.view,_57,cc1,true);
_5d.view.render.call(_5d.view,_57,cc2,false);
if(_5d.showFooter){
_5d.view.renderFooter.call(_5d.view,_57,dc.footer1,true);
_5d.view.renderFooter.call(_5d.view,_57,dc.footer2,false);
}
if(_5d.view.onAfterRender){
_5d.view.onAfterRender.call(_5d.view,_57);
}
if(!_58&&_5d.pagination){
var _61=$.data(_57,"treegrid").total;
var _62=$(_57).datagrid("getPager");
var _63=_62.pagination("options");
if(_63.total!=_59.total){
_62.pagination("refresh",{pageNumber:_5d.pageNumber,total:_59.total});
if(_5d.pageNumber!=_63.pageNumber&&_63.pageNumber>0){
_5d.pageNumber=_63.pageNumber;
_15(_57);
}
}
}
_16(_57);
_1f(_57);
$(_57).treegrid("showLines");
$(_57).treegrid("setSelectionState");
$(_57).treegrid("autoSizeColumn");
if(!_5b){
_5d.onLoadSuccess.call(_57,_5e,_59);
}
};
function _15(_64,_65,_66,_67,_68){
var _69=$.data(_64,"treegrid").options;
var _6a=$(_64).datagrid("getPanel").find("div.datagrid-body");
if(_65==undefined&&_69.queryParams){
_69.queryParams.id=undefined;
}
if(_66){
_69.queryParams=_66;
}
var _6b=$.extend({},_69.queryParams);
if(_69.pagination){
$.extend(_6b,{page:_69.pageNumber,rows:_69.pageSize});
}
if(_69.sortName){
$.extend(_6b,{sort:_69.sortName,order:_69.sortOrder});
}
var row=_37(_64,_65);
if(_69.onBeforeLoad.call(_64,row,_6b)==false){
return;
}
var _6c=_6a.find("tr[node-id=\""+_65+"\"] span.tree-folder");
_6c.addClass("tree-loading");
$(_64).treegrid("loading");
var _6d=_69.loader.call(_64,_6b,function(_6e){
_6c.removeClass("tree-loading");
$(_64).treegrid("loaded");
_56(_64,_65,_6e,_67);
if(_68){
_68();
}
},function(){
_6c.removeClass("tree-loading");
$(_64).treegrid("loaded");
_69.onLoadError.apply(_64,arguments);
if(_68){
_68();
}
});
if(_6d==false){
_6c.removeClass("tree-loading");
$(_64).treegrid("loaded");
}
};
function _6f(_70){
var _71=_72(_70);
return _71.length?_71[0]:null;
};
function _72(_73){
return $.data(_73,"treegrid").data;
};
function _46(_74,_75){
var row=_37(_74,_75);
if(row._parentId){
return _37(_74,row._parentId);
}else{
return null;
}
};
function _1b(_76,_77){
var _78=$.data(_76,"treegrid").data;
if(_77){
var _79=_37(_76,_77);
_78=_79?(_79.children||[]):[];
}
var _7a=[];
$.easyui.forEach(_78,true,function(_7b){
_7a.push(_7b);
});
return _7a;
};
function _7c(_7d,_7e){
var _7f=$.data(_7d,"treegrid").options;
var tr=_7f.finder.getTr(_7d,_7e);
var _80=tr.children("td[field=\""+_7f.treeField+"\"]");
return _80.find("span.tree-indent,span.tree-hit").length;
};
function _37(_81,_82){
var _83=$.data(_81,"treegrid");
var _84=_83.options;
var _85=null;
$.easyui.forEach(_83.data,true,function(_86){
if(_86[_84.idField]==_82){
_85=_86;
return false;
}
});
return _85;
};
function _87(_88,_89){
var _8a=$.data(_88,"treegrid").options;
var row=_37(_88,_89);
var tr=_8a.finder.getTr(_88,_89);
var hit=tr.find("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-collapsed")){
return;
}
if(_8a.onBeforeCollapse.call(_88,row)==false){
return;
}
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
hit.next().removeClass("tree-folder-open");
row.state="closed";
tr=tr.next("tr.treegrid-tr-tree");
var cc=tr.children("td").children("div");
if(_8a.animate){
cc.slideUp("normal",function(){
$(_88).treegrid("autoSizeColumn");
_16(_88,_89);
_8a.onCollapse.call(_88,row);
});
}else{
cc.hide();
$(_88).treegrid("autoSizeColumn");
_16(_88,_89);
_8a.onCollapse.call(_88,row);
}
};
function _8b(_8c,_8d){
var _8e=$.data(_8c,"treegrid").options;
var tr=_8e.finder.getTr(_8c,_8d);
var hit=tr.find("span.tree-hit");
var row=_37(_8c,_8d);
if(hit.length==0){
return;
}
if(hit.hasClass("tree-expanded")){
return;
}
if(_8e.onBeforeExpand.call(_8c,row)==false){
return;
}
hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
hit.next().addClass("tree-folder-open");
var _8f=tr.next("tr.treegrid-tr-tree");
if(_8f.length){
var cc=_8f.children("td").children("div");
_90(cc);
}else{
_4e(_8c,row[_8e.idField]);
var _8f=tr.next("tr.treegrid-tr-tree");
var cc=_8f.children("td").children("div");
cc.hide();
var _91=$.extend({},_8e.queryParams||{});
_91.id=row[_8e.idField];
_15(_8c,row[_8e.idField],_91,true,function(){
if(cc.is(":empty")){
_8f.remove();
}else{
_90(cc);
}
});
}
function _90(cc){
row.state="open";
if(_8e.animate){
cc.slideDown("normal",function(){
$(_8c).treegrid("autoSizeColumn");
_16(_8c,_8d);
_8e.onExpand.call(_8c,row);
});
}else{
cc.show();
$(_8c).treegrid("autoSizeColumn");
_16(_8c,_8d);
_8e.onExpand.call(_8c,row);
}
};
};
function _28(_92,_93){
var _94=$.data(_92,"treegrid").options;
var tr=_94.finder.getTr(_92,_93);
var hit=tr.find("span.tree-hit");
if(hit.hasClass("tree-expanded")){
_87(_92,_93);
}else{
_8b(_92,_93);
}
};
function _95(_96,_97){
var _98=$.data(_96,"treegrid").options;
var _99=_1b(_96,_97);
if(_97){
_99.unshift(_37(_96,_97));
}
for(var i=0;i<_99.length;i++){
_87(_96,_99[i][_98.idField]);
}
};
function _9a(_9b,_9c){
var _9d=$.data(_9b,"treegrid").options;
var _9e=_1b(_9b,_9c);
if(_9c){
_9e.unshift(_37(_9b,_9c));
}
for(var i=0;i<_9e.length;i++){
_8b(_9b,_9e[i][_9d.idField]);
}
};
function _9f(_a0,_a1){
var _a2=$.data(_a0,"treegrid").options;
var ids=[];
var p=_46(_a0,_a1);
while(p){
var id=p[_a2.idField];
ids.unshift(id);
p=_46(_a0,id);
}
for(var i=0;i<ids.length;i++){
_8b(_a0,ids[i]);
}
};
function _a3(_a4,_a5){
var _a6=$.data(_a4,"treegrid");
var _a7=_a6.options;
if(_a5.parent){
var tr=_a7.finder.getTr(_a4,_a5.parent);
if(tr.next("tr.treegrid-tr-tree").length==0){
_4e(_a4,_a5.parent);
}
var _a8=tr.children("td[field=\""+_a7.treeField+"\"]").children("div.datagrid-cell");
var _a9=_a8.children("span.tree-icon");
if(_a9.hasClass("tree-file")){
_a9.removeClass("tree-file").addClass("tree-folder tree-folder-open");
var hit=$("<span class=\"tree-hit tree-expanded\"></span>").insertBefore(_a9);
if(hit.prev().length){
hit.prev().remove();
}
}
}
_56(_a4,_a5.parent,_a5.data,_a6.data.length>0,true);
};
function _aa(_ab,_ac){
var ref=_ac.before||_ac.after;
var _ad=$.data(_ab,"treegrid").options;
var _ae=_46(_ab,ref);
_a3(_ab,{parent:(_ae?_ae[_ad.idField]:null),data:[_ac.data]});
var _af=_ae?_ae.children:$(_ab).treegrid("getRoots");
for(var i=0;i<_af.length;i++){
if(_af[i][_ad.idField]==ref){
var _b0=_af[_af.length-1];
_af.splice(_ac.before?i:(i+1),0,_b0);
_af.splice(_af.length-1,1);
break;
}
}
_b1(true);
_b1(false);
_1f(_ab);
$(_ab).treegrid("showLines");
function _b1(_b2){
var _b3=_b2?1:2;
var tr=_ad.finder.getTr(_ab,_ac.data[_ad.idField],"body",_b3);
var _b4=tr.closest("table.datagrid-btable");
tr=tr.parent().children();
var _b5=_ad.finder.getTr(_ab,ref,"body",_b3);
if(_ac.before){
tr.insertBefore(_b5);
}else{
var sub=_b5.next("tr.treegrid-tr-tree");
tr.insertAfter(sub.length?sub:_b5);
}
_b4.remove();
};
};
function _b6(_b7,_b8){
var _b9=$.data(_b7,"treegrid");
var _ba=_b9.options;
var _bb=_46(_b7,_b8);
$(_b7).datagrid("deleteRow",_b8);
$.easyui.removeArrayItem(_b9.checkedRows,_ba.idField,_b8);
_1f(_b7);
if(_bb){
_49(_b7,_bb[_ba.idField]);
}
_b9.total-=1;
$(_b7).datagrid("getPager").pagination("refresh",{total:_b9.total});
$(_b7).treegrid("showLines");
};
function _bc(_bd){
var t=$(_bd);
var _be=t.treegrid("options");
if(_be.lines){
t.treegrid("getPanel").addClass("tree-lines");
}else{
t.treegrid("getPanel").removeClass("tree-lines");
return;
}
t.treegrid("getPanel").find("span.tree-indent").removeClass("tree-line tree-join tree-joinbottom");
t.treegrid("getPanel").find("div.datagrid-cell").removeClass("tree-node-last tree-root-first tree-root-one");
var _bf=t.treegrid("getRoots");
if(_bf.length>1){
_c0(_bf[0]).addClass("tree-root-first");
}else{
if(_bf.length==1){
_c0(_bf[0]).addClass("tree-root-one");
}
}
_c1(_bf);
_c2(_bf);
function _c1(_c3){
$.map(_c3,function(_c4){
if(_c4.children&&_c4.children.length){
_c1(_c4.children);
}else{
var _c5=_c0(_c4);
_c5.find(".tree-icon").prev().addClass("tree-join");
}
});
if(_c3.length){
var _c6=_c0(_c3[_c3.length-1]);
_c6.addClass("tree-node-last");
_c6.find(".tree-join").removeClass("tree-join").addClass("tree-joinbottom");
}
};
function _c2(_c7){
$.map(_c7,function(_c8){
if(_c8.children&&_c8.children.length){
_c2(_c8.children);
}
});
for(var i=0;i<_c7.length-1;i++){
var _c9=_c7[i];
var _ca=t.treegrid("getLevel",_c9[_be.idField]);
var tr=_be.finder.getTr(_bd,_c9[_be.idField]);
var cc=tr.next().find("tr.datagrid-row td[field=\""+_be.treeField+"\"] div.datagrid-cell");
cc.find("span:eq("+(_ca-1)+")").addClass("tree-line");
}
};
function _c0(_cb){
var tr=_be.finder.getTr(_bd,_cb[_be.idField]);
var _cc=tr.find("td[field=\""+_be.treeField+"\"] div.datagrid-cell");
return _cc;
};
};
$.fn.treegrid=function(_cd,_ce){
if(typeof _cd=="string"){
var _cf=$.fn.treegrid.methods[_cd];
if(_cf){
return _cf(this,_ce);
}else{
return this.datagrid(_cd,_ce);
}
}
_cd=_cd||{};
return this.each(function(){
var _d0=$.data(this,"treegrid");
if(_d0){
$.extend(_d0.options,_cd);
}else{
_d0=$.data(this,"treegrid",{options:$.extend({},$.fn.treegrid.defaults,$.fn.treegrid.parseOptions(this),_cd),data:[],checkedRows:[],tmpIds:[]});
}
_1(this);
if(_d0.options.data){
$(this).treegrid("loadData",_d0.options.data);
}
_15(this);
});
};
$.fn.treegrid.methods={options:function(jq){
return $.data(jq[0],"treegrid").options;
},resize:function(jq,_d1){
return jq.each(function(){
$(this).datagrid("resize",_d1);
});
},fixRowHeight:function(jq,_d2){
return jq.each(function(){
_16(this,_d2);
});
},loadData:function(jq,_d3){
return jq.each(function(){
_56(this,_d3.parent,_d3);
});
},load:function(jq,_d4){
return jq.each(function(){
$(this).treegrid("options").pageNumber=1;
$(this).treegrid("getPager").pagination({pageNumber:1});
$(this).treegrid("reload",_d4);
});
},reload:function(jq,id){
return jq.each(function(){
var _d5=$(this).treegrid("options");
var _d6={};
if(typeof id=="object"){
_d6=id;
}else{
_d6=$.extend({},_d5.queryParams);
_d6.id=id;
}
if(_d6.id){
var _d7=$(this).treegrid("find",_d6.id);
if(_d7.children){
_d7.children.splice(0,_d7.children.length);
}
_d5.queryParams=_d6;
var tr=_d5.finder.getTr(this,_d6.id);
tr.next("tr.treegrid-tr-tree").remove();
tr.find("span.tree-hit").removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
_8b(this,_d6.id);
}else{
_15(this,null,_d6);
}
});
},reloadFooter:function(jq,_d8){
return jq.each(function(){
var _d9=$.data(this,"treegrid").options;
var dc=$.data(this,"datagrid").dc;
if(_d8){
$.data(this,"treegrid").footer=_d8;
}
if(_d9.showFooter){
_d9.view.renderFooter.call(_d9.view,this,dc.footer1,true);
_d9.view.renderFooter.call(_d9.view,this,dc.footer2,false);
if(_d9.view.onAfterRender){
_d9.view.onAfterRender.call(_d9.view,this);
}
$(this).treegrid("fixRowHeight");
}
});
},getData:function(jq){
return $.data(jq[0],"treegrid").data;
},getFooterRows:function(jq){
return $.data(jq[0],"treegrid").footer;
},getRoot:function(jq){
return _6f(jq[0]);
},getRoots:function(jq){
return _72(jq[0]);
},getParent:function(jq,id){
return _46(jq[0],id);
},getChildren:function(jq,id){
return _1b(jq[0],id);
},getLevel:function(jq,id){
return _7c(jq[0],id);
},find:function(jq,id){
return _37(jq[0],id);
},isLeaf:function(jq,id){
var _da=$.data(jq[0],"treegrid").options;
var tr=_da.finder.getTr(jq[0],id);
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
_87(this,id);
});
},expand:function(jq,id){
return jq.each(function(){
_8b(this,id);
});
},toggle:function(jq,id){
return jq.each(function(){
_28(this,id);
});
},collapseAll:function(jq,id){
return jq.each(function(){
_95(this,id);
});
},expandAll:function(jq,id){
return jq.each(function(){
_9a(this,id);
});
},expandTo:function(jq,id){
return jq.each(function(){
_9f(this,id);
});
},append:function(jq,_db){
return jq.each(function(){
_a3(this,_db);
});
},insert:function(jq,_dc){
return jq.each(function(){
_aa(this,_dc);
});
},remove:function(jq,id){
return jq.each(function(){
_b6(this,id);
});
},pop:function(jq,id){
var row=jq.treegrid("find",id);
jq.treegrid("remove",id);
return row;
},refresh:function(jq,id){
return jq.each(function(){
var _dd=$.data(this,"treegrid").options;
_dd.view.refreshRow.call(_dd.view,this,id);
});
},update:function(jq,_de){
return jq.each(function(){
var _df=$.data(this,"treegrid").options;
var row=_de.row;
_df.view.updateRow.call(_df.view,this,_de.id,row);
if(row.checked!=undefined){
row=_37(this,_de.id);
$.extend(row,{checkState:row.checked?"checked":(row.checked===false?"unchecked":undefined)});
_49(this,_de.id);
}
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
},showLines:function(jq){
return jq.each(function(){
_bc(this);
});
},setSelectionState:function(jq){
return jq.each(function(){
$(this).datagrid("setSelectionState");
var _e0=$(this).data("treegrid");
for(var i=0;i<_e0.tmpIds.length;i++){
_29(this,_e0.tmpIds[i],true,true);
}
_e0.tmpIds=[];
});
},getCheckedNodes:function(jq,_e1){
_e1=_e1||"checked";
var _e2=[];
$.easyui.forEach(jq.data("treegrid").checkedRows,false,function(row){
if(row.checkState==_e1){
_e2.push(row);
}
});
return _e2;
},checkNode:function(jq,id){
return jq.each(function(){
_29(this,id,true);
});
},uncheckNode:function(jq,id){
return jq.each(function(){
_29(this,id,false);
});
},clearChecked:function(jq){
return jq.each(function(){
var _e3=this;
var _e4=$(_e3).treegrid("options");
$(_e3).datagrid("clearChecked");
$.map($(_e3).treegrid("getCheckedNodes"),function(row){
_29(_e3,row[_e4.idField],false,true);
});
});
}};
$.fn.treegrid.parseOptions=function(_e5){
return $.extend({},$.fn.datagrid.parseOptions(_e5),$.parser.parseOptions(_e5,["treeField",{checkbox:"boolean",cascadeCheck:"boolean",onlyLeafCheck:"boolean"},{animate:"boolean"}]));
};
var _e6=$.extend({},$.fn.datagrid.defaults.view,{render:function(_e7,_e8,_e9){
var _ea=$.data(_e7,"treegrid").options;
var _eb=$(_e7).datagrid("getColumnFields",_e9);
var _ec=$.data(_e7,"datagrid").rowIdPrefix;
if(_e9){
if(!(_ea.rownumbers||(_ea.frozenColumns&&_ea.frozenColumns.length))){
return;
}
}
var _ed=this;
if(this.treeNodes&&this.treeNodes.length){
var _ee=_ef.call(this,_e9,this.treeLevel,this.treeNodes);
$(_e8).append(_ee.join(""));
}
function _ef(_f0,_f1,_f2){
var _f3=$(_e7).treegrid("getParent",_f2[0][_ea.idField]);
var _f4=(_f3?_f3.children.length:$(_e7).treegrid("getRoots").length)-_f2.length;
var _f5=["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_f2.length;i++){
var row=_f2[i];
if(row.state!="open"&&row.state!="closed"){
row.state="open";
}
var css=_ea.rowStyler?_ea.rowStyler.call(_e7,row):"";
var cs=this.getStyleValue(css);
var cls="class=\"datagrid-row "+(_f4++%2&&_ea.striped?"datagrid-row-alt ":" ")+cs.c+"\"";
var _f6=cs.s?"style=\""+cs.s+"\"":"";
var _f7=_ec+"-"+(_f0?1:2)+"-"+row[_ea.idField];
_f5.push("<tr id=\""+_f7+"\" node-id=\""+row[_ea.idField]+"\" "+cls+" "+_f6+">");
_f5=_f5.concat(_ed.renderRow.call(_ed,_e7,_eb,_f0,_f1,row));
_f5.push("</tr>");
if(row.children&&row.children.length){
var tt=_ef.call(this,_f0,_f1+1,row.children);
var v=row.state=="closed"?"none":"block";
_f5.push("<tr class=\"treegrid-tr-tree\"><td style=\"border:0px\" colspan="+(_eb.length+(_ea.rownumbers?1:0))+"><div style=\"display:"+v+"\">");
_f5=_f5.concat(tt);
_f5.push("</div></td></tr>");
}
}
_f5.push("</tbody></table>");
return _f5;
};
},renderFooter:function(_f8,_f9,_fa){
var _fb=$.data(_f8,"treegrid").options;
var _fc=$.data(_f8,"treegrid").footer||[];
var _fd=$(_f8).datagrid("getColumnFields",_fa);
var _fe=["<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_fc.length;i++){
var row=_fc[i];
row[_fb.idField]=row[_fb.idField]||("foot-row-id"+i);
_fe.push("<tr class=\"datagrid-row\" node-id=\""+row[_fb.idField]+"\">");
_fe.push(this.renderRow.call(this,_f8,_fd,_fa,0,row));
_fe.push("</tr>");
}
_fe.push("</tbody></table>");
$(_f9).html(_fe.join(""));
},renderRow:function(_ff,_100,_101,_102,row){
var _103=$.data(_ff,"treegrid");
var opts=_103.options;
var cc=[];
if(_101&&opts.rownumbers){
cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">0</div></td>");
}
for(var i=0;i<_100.length;i++){
var _104=_100[i];
var col=$(_ff).datagrid("getColumnOption",_104);
if(col){
var css=col.styler?(col.styler(row[_104],row)||""):"";
var cs=this.getStyleValue(css);
var cls=cs.c?"class=\""+cs.c+"\"":"";
var _105=col.hidden?"style=\"display:none;"+cs.s+"\"":(cs.s?"style=\""+cs.s+"\"":"");
cc.push("<td field=\""+_104+"\" "+cls+" "+_105+">");
var _105="";
if(!col.checkbox){
if(col.align){
_105+="text-align:"+col.align+";";
}
if(!opts.nowrap){
_105+="white-space:normal;height:auto;";
}else{
if(opts.autoRowHeight){
_105+="height:auto;";
}
}
}
cc.push("<div style=\""+_105+"\" ");
if(col.checkbox){
cc.push("class=\"datagrid-cell-check ");
}else{
cc.push("class=\"datagrid-cell "+col.cellClass);
}
if(_104==opts.treeField){
cc.push(" tree-node");
}
cc.push("\">");
if(col.checkbox){
if(row.checked){
cc.push("<input type=\"checkbox\" checked=\"checked\"");
}else{
cc.push("<input type=\"checkbox\"");
}
cc.push(" name=\""+_104+"\" value=\""+(row[_104]!=undefined?row[_104]:"")+"\">");
}else{
var val=null;
if(col.formatter){
val=col.formatter(row[_104],row);
}else{
val=row[_104];
}
if(_104==opts.treeField){
for(var j=0;j<_102;j++){
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
if(this.hasCheckbox(_ff,row)){
var flag=0;
var crow=$.easyui.getArrayItem(_103.checkedRows,opts.idField,row[opts.idField]);
if(crow){
flag=crow.checkState=="checked"?1:2;
row.checkState=crow.checkState;
row.checked=crow.checked;
$.easyui.addArrayItem(_103.checkedRows,opts.idField,row);
}else{
var prow=$.easyui.getArrayItem(_103.checkedRows,opts.idField,row._parentId);
if(prow&&prow.checkState=="checked"&&opts.cascadeCheck){
flag=1;
row.checked=true;
$.easyui.addArrayItem(_103.checkedRows,opts.idField,row);
}else{
if(row.checked){
$.easyui.addArrayItem(_103.tmpIds,row[opts.idField]);
}
}
row.checkState=flag?"checked":"unchecked";
}
cc.push("<span class=\"tree-checkbox tree-checkbox"+flag+"\"></span>");
}else{
row.checkState=undefined;
row.checked=undefined;
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
},hasCheckbox:function(_106,row){
var opts=$.data(_106,"treegrid").options;
if(opts.checkbox){
if($.isFunction(opts.checkbox)){
if(opts.checkbox.call(_106,row)){
return true;
}else{
return false;
}
}else{
if(opts.onlyLeafCheck){
if(row.state=="open"&&!(row.children&&row.children.length)){
return true;
}
}else{
return true;
}
}
}
return false;
},refreshRow:function(_107,id){
this.updateRow.call(this,_107,id,{});
},updateRow:function(_108,id,row){
var opts=$.data(_108,"treegrid").options;
var _109=$(_108).treegrid("find",id);
$.extend(_109,row);
var _10a=$(_108).treegrid("getLevel",id)-1;
var _10b=opts.rowStyler?opts.rowStyler.call(_108,_109):"";
var _10c=$.data(_108,"datagrid").rowIdPrefix;
var _10d=_109[opts.idField];
function _10e(_10f){
var _110=$(_108).treegrid("getColumnFields",_10f);
var tr=opts.finder.getTr(_108,id,"body",(_10f?1:2));
var _111=tr.find("div.datagrid-cell-rownumber").html();
var _112=tr.find("div.datagrid-cell-check input[type=checkbox]").is(":checked");
tr.html(this.renderRow(_108,_110,_10f,_10a,_109));
tr.attr("style",_10b||"");
tr.find("div.datagrid-cell-rownumber").html(_111);
if(_112){
tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked",true);
}
if(_10d!=id){
tr.attr("id",_10c+"-"+(_10f?1:2)+"-"+_10d);
tr.attr("node-id",_10d);
}
};
_10e.call(this,true);
_10e.call(this,false);
$(_108).treegrid("fixRowHeight",id);
},deleteRow:function(_113,id){
var opts=$.data(_113,"treegrid").options;
var tr=opts.finder.getTr(_113,id);
tr.next("tr.treegrid-tr-tree").remove();
tr.remove();
var _114=del(id);
if(_114){
if(_114.children.length==0){
tr=opts.finder.getTr(_113,_114[opts.idField]);
tr.next("tr.treegrid-tr-tree").remove();
var cell=tr.children("td[field=\""+opts.treeField+"\"]").children("div.datagrid-cell");
cell.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
cell.find(".tree-hit").remove();
$("<span class=\"tree-indent\"></span>").prependTo(cell);
}
}
this.setEmptyMsg(_113);
function del(id){
var cc;
var _115=$(_113).treegrid("getParent",id);
if(_115){
cc=_115.children;
}else{
cc=$(_113).treegrid("getData");
}
for(var i=0;i<cc.length;i++){
if(cc[i][opts.idField]==id){
cc.splice(i,1);
break;
}
}
return _115;
};
},onBeforeRender:function(_116,_117,data){
if($.isArray(_117)){
data={total:_117.length,rows:_117};
_117=null;
}
if(!data){
return false;
}
var _118=$.data(_116,"treegrid");
var opts=_118.options;
if(data.length==undefined){
if(data.footer){
_118.footer=data.footer;
}
if(data.total){
_118.total=data.total;
}
data=this.transfer(_116,_117,data.rows);
}else{
function _119(_11a,_11b){
for(var i=0;i<_11a.length;i++){
var row=_11a[i];
row._parentId=_11b;
if(row.children&&row.children.length){
_119(row.children,row[opts.idField]);
}
}
};
_119(data,_117);
}
this.sort(_116,data);
this.treeNodes=data;
this.treeLevel=$(_116).treegrid("getLevel",_117);
var node=_37(_116,_117);
if(node){
if(node.children){
node.children=node.children.concat(data);
}else{
node.children=data;
}
}else{
_118.data=_118.data.concat(data);
}
},sort:function(_11c,data){
var opts=$.data(_11c,"treegrid").options;
if(!opts.remoteSort&&opts.sortName){
var _11d=opts.sortName.split(",");
var _11e=opts.sortOrder.split(",");
_11f(data);
}
function _11f(rows){
rows.sort(function(r1,r2){
var r=0;
for(var i=0;i<_11d.length;i++){
var sn=_11d[i];
var so=_11e[i];
var col=$(_11c).treegrid("getColumnOption",sn);
var _120=col.sorter||function(a,b){
return a==b?0:(a>b?1:-1);
};
r=_120(r1[sn],r2[sn])*(so=="asc"?1:-1);
if(r!=0){
return r;
}
}
return r;
});
for(var i=0;i<rows.length;i++){
var _121=rows[i].children;
if(_121&&_121.length){
_11f(_121);
}
}
};
},transfer:function(_122,_123,data){
var opts=$.data(_122,"treegrid").options;
var rows=$.extend([],data);
var _124=_125(_123,rows);
var toDo=$.extend([],_124);
while(toDo.length){
var node=toDo.shift();
var _126=_125(node[opts.idField],rows);
if(_126.length){
if(node.children){
node.children=node.children.concat(_126);
}else{
node.children=_126;
}
toDo=toDo.concat(_126);
}
}
return _124;
function _125(_127,rows){
var rr=[];
for(var i=0;i<rows.length;i++){
var row=rows[i];
if(row._parentId==_127){
rr.push(row);
rows.splice(i,1);
i--;
}
}
return rr;
};
}});
$.fn.treegrid.defaults=$.extend({},$.fn.datagrid.defaults,{treeField:null,checkbox:false,cascadeCheck:true,onlyLeafCheck:false,lines:false,animate:false,singleSelect:true,view:_e6,rowEvents:$.extend({},$.fn.datagrid.defaults.rowEvents,{mouseover:_22(true),mouseout:_22(false),click:_24}),loader:function(_128,_129,_12a){
var opts=$(this).treegrid("options");
if(!opts.url){
return false;
}
$.ajax({type:opts.method,url:opts.url,data:_128,dataType:"json",success:function(data){
_129(data);
},error:function(){
_12a.apply(this,arguments);
}});
},loadFilter:function(data,_12b){
return data;
},finder:{getTr:function(_12c,id,type,_12d){
type=type||"body";
_12d=_12d||0;
var dc=$.data(_12c,"datagrid").dc;
if(_12d==0){
var opts=$.data(_12c,"treegrid").options;
var tr1=opts.finder.getTr(_12c,id,type,1);
var tr2=opts.finder.getTr(_12c,id,type,2);
return tr1.add(tr2);
}else{
if(type=="body"){
var tr=$("#"+$.data(_12c,"datagrid").rowIdPrefix+"-"+_12d+"-"+id);
if(!tr.length){
tr=(_12d==1?dc.body1:dc.body2).find("tr[node-id=\""+id+"\"]");
}
return tr;
}else{
if(type=="footer"){
return (_12d==1?dc.footer1:dc.footer2).find("tr[node-id=\""+id+"\"]");
}else{
if(type=="selected"){
return (_12d==1?dc.body1:dc.body2).find("tr.datagrid-row-selected");
}else{
if(type=="highlight"){
return (_12d==1?dc.body1:dc.body2).find("tr.datagrid-row-over");
}else{
if(type=="checked"){
return (_12d==1?dc.body1:dc.body2).find("tr.datagrid-row-checked");
}else{
if(type=="last"){
return (_12d==1?dc.body1:dc.body2).find("tr:last[node-id]");
}else{
if(type=="allbody"){
return (_12d==1?dc.body1:dc.body2).find("tr[node-id]");
}else{
if(type=="allfooter"){
return (_12d==1?dc.footer1:dc.footer2).find("tr[node-id]");
}
}
}
}
}
}
}
}
}
},getRow:function(_12e,p){
var id=(typeof p=="object")?p.attr("node-id"):p;
return $(_12e).treegrid("find",id);
},getRows:function(_12f){
return $(_12f).treegrid("getChildren");
}},onBeforeLoad:function(row,_130){
},onLoadSuccess:function(row,data){
},onLoadError:function(){
},onBeforeCollapse:function(row){
},onCollapse:function(row){
},onBeforeExpand:function(row){
},onExpand:function(row){
},onClickRow:function(row){
},onDblClickRow:function(row){
},onClickCell:function(_131,row){
},onDblClickCell:function(_132,row){
},onContextMenu:function(e,row){
},onBeforeEdit:function(row){
},onAfterEdit:function(row,_133){
},onCancelEdit:function(row){
},onBeforeCheckNode:function(row,_134){
},onCheckNode:function(row,_135){
}});
})(jQuery);

