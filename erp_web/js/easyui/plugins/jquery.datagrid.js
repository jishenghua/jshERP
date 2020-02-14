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
var _1=0;
function _2(a,o){
return $.easyui.indexOfArray(a,o);
};
function _3(a,o,id){
$.easyui.removeArrayItem(a,o,id);
};
function _4(a,o,r){
$.easyui.addArrayItem(a,o,r);
};
function _5(_6,aa){
return $.data(_6,"treegrid")?aa.slice(1):aa;
};
function _7(_8){
var _9=$.data(_8,"datagrid");
var _a=_9.options;
var _b=_9.panel;
var dc=_9.dc;
var ss=null;
if(_a.sharedStyleSheet){
ss=typeof _a.sharedStyleSheet=="boolean"?"head":_a.sharedStyleSheet;
}else{
ss=_b.closest("div.datagrid-view");
if(!ss.length){
ss=dc.view;
}
}
var cc=$(ss);
var _c=$.data(cc[0],"ss");
if(!_c){
_c=$.data(cc[0],"ss",{cache:{},dirty:[]});
}
return {add:function(_d){
var ss=["<style type=\"text/css\" easyui=\"true\">"];
for(var i=0;i<_d.length;i++){
_c.cache[_d[i][0]]={width:_d[i][1]};
}
var _e=0;
for(var s in _c.cache){
var _f=_c.cache[s];
_f.index=_e++;
ss.push(s+"{width:"+_f.width+"}");
}
ss.push("</style>");
$(ss.join("\n")).appendTo(cc);
cc.children("style[easyui]:not(:last)").remove();
},getRule:function(_10){
var _11=cc.children("style[easyui]:last")[0];
var _12=_11.styleSheet?_11.styleSheet:(_11.sheet||document.styleSheets[document.styleSheets.length-1]);
var _13=_12.cssRules||_12.rules;
return _13[_10];
},set:function(_14,_15){
var _16=_c.cache[_14];
if(_16){
_16.width=_15;
var _17=this.getRule(_16.index);
if(_17){
_17.style["width"]=_15;
}
}
},remove:function(_18){
var tmp=[];
for(var s in _c.cache){
if(s.indexOf(_18)==-1){
tmp.push([s,_c.cache[s].width]);
}
}
_c.cache={};
this.add(tmp);
},dirty:function(_19){
if(_19){
_c.dirty.push(_19);
}
},clean:function(){
for(var i=0;i<_c.dirty.length;i++){
this.remove(_c.dirty[i]);
}
_c.dirty=[];
}};
};
function _1a(_1b,_1c){
var _1d=$.data(_1b,"datagrid");
var _1e=_1d.options;
var _1f=_1d.panel;
if(_1c){
$.extend(_1e,_1c);
}
if(_1e.fit==true){
var p=_1f.panel("panel").parent();
_1e.width=p.width();
_1e.height=p.height();
}
_1f.panel("resize",_1e);
};
function _20(_21){
var _22=$.data(_21,"datagrid");
var _23=_22.options;
var dc=_22.dc;
var _24=_22.panel;
if(!_24.is(":visible")){
return;
}
var _25=_24.width();
var _26=_24.height();
var _27=dc.view;
var _28=dc.view1;
var _29=dc.view2;
var _2a=_28.children("div.datagrid-header");
var _2b=_29.children("div.datagrid-header");
var _2c=_2a.find("table");
var _2d=_2b.find("table");
_27.width(_25);
var _2e=_2a.children("div.datagrid-header-inner").show();
_28.width(_2e.find("table").width());
if(!_23.showHeader){
_2e.hide();
}
_29.width(_25-_28._outerWidth());
_28.children()._outerWidth(_28.width());
_29.children()._outerWidth(_29.width());
var all=_2a.add(_2b).add(_2c).add(_2d);
all.css("height","");
var hh=Math.max(_2c.height(),_2d.height());
all._outerHeight(hh);
_27.children(".datagrid-empty").css("top",hh+"px");
dc.body1.add(dc.body2).children("table.datagrid-btable-frozen").css({position:"absolute",top:dc.header2._outerHeight()});
var _2f=dc.body2.children("table.datagrid-btable-frozen")._outerHeight();
var _30=_2f+_2b._outerHeight()+_29.children(".datagrid-footer")._outerHeight();
_24.children(":not(.datagrid-view,.datagrid-mask,.datagrid-mask-msg)").each(function(){
_30+=$(this)._outerHeight();
});
var _31=_24.outerHeight()-_24.height();
var _32=_24._size("minHeight")||"";
var _33=_24._size("maxHeight")||"";
_28.add(_29).children("div.datagrid-body").css({marginTop:_2f,height:(isNaN(parseInt(_23.height))?"":(_26-_30)),minHeight:(_32?_32-_31-_30:""),maxHeight:(_33?_33-_31-_30:"")});
_27.height(_29.height());
};
function _34(_35,_36,_37){
var _38=$.data(_35,"datagrid").data.rows;
var _39=$.data(_35,"datagrid").options;
var dc=$.data(_35,"datagrid").dc;
var tmp=$("<tr class=\"datagrid-row\" style=\"position:absolute;left:-999999px\"></tr>").appendTo("body");
var _3a=tmp.outerHeight();
tmp.remove();
if(!dc.body1.is(":empty")&&(!_39.nowrap||_39.autoRowHeight||_37)){
if(_36!=undefined){
var tr1=_39.finder.getTr(_35,_36,"body",1);
var tr2=_39.finder.getTr(_35,_36,"body",2);
_3b(tr1,tr2);
}else{
var tr1=_39.finder.getTr(_35,0,"allbody",1);
var tr2=_39.finder.getTr(_35,0,"allbody",2);
_3b(tr1,tr2);
if(_39.showFooter){
var tr1=_39.finder.getTr(_35,0,"allfooter",1);
var tr2=_39.finder.getTr(_35,0,"allfooter",2);
_3b(tr1,tr2);
}
}
}
_20(_35);
if(_39.height=="auto"){
var _3c=dc.body1.parent();
var _3d=dc.body2;
var _3e=_3f(_3d);
var _40=_3e.height;
if(_3e.width>_3d.width()){
_40+=18;
}
_40-=parseInt(_3d.css("marginTop"))||0;
_3c.height(_40);
_3d.height(_40);
dc.view.height(dc.view2.height());
}
dc.body2.triggerHandler("scroll");
function _3b(_41,_42){
for(var i=0;i<_42.length;i++){
var tr1=$(_41[i]);
var tr2=$(_42[i]);
tr1.css("height","");
tr2.css("height","");
var _43=Math.max(tr1.outerHeight(),tr2.outerHeight());
if(_43!=_3a){
_43=Math.max(_43,_3a)+1;
tr1.css("height",_43);
tr2.css("height",_43);
}
}
};
function _3f(cc){
var _44=0;
var _45=0;
$(cc).children().each(function(){
var c=$(this);
if(c.is(":visible")){
_45+=c._outerHeight();
if(_44<c._outerWidth()){
_44=c._outerWidth();
}
}
});
return {width:_44,height:_45};
};
};
function _46(_47,_48){
var _49=$.data(_47,"datagrid");
var _4a=_49.options;
var dc=_49.dc;
if(!dc.body2.children("table.datagrid-btable-frozen").length){
dc.body1.add(dc.body2).prepend("<table class=\"datagrid-btable datagrid-btable-frozen\" cellspacing=\"0\" cellpadding=\"0\"></table>");
}
_4b(true);
_4b(false);
_20(_47);
function _4b(_4c){
var _4d=_4c?1:2;
var tr=_4a.finder.getTr(_47,_48,"body",_4d);
(_4c?dc.body1:dc.body2).children("table.datagrid-btable-frozen").append(tr);
};
};
function _4e(_4f,_50){
function _51(){
var _52=[];
var _53=[];
$(_4f).children("thead").each(function(){
var opt=$.parser.parseOptions(this,[{frozen:"boolean"}]);
$(this).find("tr").each(function(){
var _54=[];
$(this).find("th").each(function(){
var th=$(this);
var col=$.extend({},$.parser.parseOptions(this,["id","field","align","halign","order","width",{sortable:"boolean",checkbox:"boolean",resizable:"boolean",fixed:"boolean"},{rowspan:"number",colspan:"number"}]),{title:(th.html()||undefined),hidden:(th.attr("hidden")?true:undefined),formatter:(th.attr("formatter")?eval(th.attr("formatter")):undefined),styler:(th.attr("styler")?eval(th.attr("styler")):undefined),sorter:(th.attr("sorter")?eval(th.attr("sorter")):undefined)});
if(col.width&&String(col.width).indexOf("%")==-1){
col.width=parseInt(col.width);
}
if(th.attr("editor")){
var s=$.trim(th.attr("editor"));
if(s.substr(0,1)=="{"){
col.editor=eval("("+s+")");
}else{
col.editor=s;
}
}
_54.push(col);
});
opt.frozen?_52.push(_54):_53.push(_54);
});
});
return [_52,_53];
};
var _55=$("<div class=\"datagrid-wrap\">"+"<div class=\"datagrid-view\">"+"<div class=\"datagrid-view1\">"+"<div class=\"datagrid-header\">"+"<div class=\"datagrid-header-inner\"></div>"+"</div>"+"<div class=\"datagrid-body\">"+"<div class=\"datagrid-body-inner\"></div>"+"</div>"+"<div class=\"datagrid-footer\">"+"<div class=\"datagrid-footer-inner\"></div>"+"</div>"+"</div>"+"<div class=\"datagrid-view2\">"+"<div class=\"datagrid-header\">"+"<div class=\"datagrid-header-inner\"></div>"+"</div>"+"<div class=\"datagrid-body\"></div>"+"<div class=\"datagrid-footer\">"+"<div class=\"datagrid-footer-inner\"></div>"+"</div>"+"</div>"+"</div>"+"</div>").insertAfter(_4f);
_55.panel({doSize:false,cls:"datagrid"});
$(_4f).addClass("datagrid-f").hide().appendTo(_55.children("div.datagrid-view"));
var cc=_51();
var _56=_55.children("div.datagrid-view");
var _57=_56.children("div.datagrid-view1");
var _58=_56.children("div.datagrid-view2");
return {panel:_55,frozenColumns:cc[0],columns:cc[1],dc:{view:_56,view1:_57,view2:_58,header1:_57.children("div.datagrid-header").children("div.datagrid-header-inner"),header2:_58.children("div.datagrid-header").children("div.datagrid-header-inner"),body1:_57.children("div.datagrid-body").children("div.datagrid-body-inner"),body2:_58.children("div.datagrid-body"),footer1:_57.children("div.datagrid-footer").children("div.datagrid-footer-inner"),footer2:_58.children("div.datagrid-footer").children("div.datagrid-footer-inner")}};
};
function _59(_5a){
var _5b=$.data(_5a,"datagrid");
var _5c=_5b.options;
var dc=_5b.dc;
var _5d=_5b.panel;
_5b.ss=$(_5a).datagrid("createStyleSheet");
_5d.panel($.extend({},_5c,{id:null,doSize:false,onResize:function(_5e,_5f){
if($.data(_5a,"datagrid")){
_20(_5a);
$(_5a).datagrid("fitColumns");
_5c.onResize.call(_5d,_5e,_5f);
}
},onExpand:function(){
if($.data(_5a,"datagrid")){
$(_5a).datagrid("fixRowHeight").datagrid("fitColumns");
_5c.onExpand.call(_5d);
}
}}));
var _60=$(_5a).attr("id")||"";
if(_60){
_60+="_";
}
_5b.rowIdPrefix=_60+"datagrid-row-r"+(++_1);
_5b.cellClassPrefix=_60+"datagrid-cell-c"+_1;
_61(dc.header1,_5c.frozenColumns,true);
_61(dc.header2,_5c.columns,false);
_62();
dc.header1.add(dc.header2).css("display",_5c.showHeader?"block":"none");
dc.footer1.add(dc.footer2).css("display",_5c.showFooter?"block":"none");
if(_5c.toolbar){
if($.isArray(_5c.toolbar)){
$("div.datagrid-toolbar",_5d).remove();
var tb=$("<div class=\"datagrid-toolbar\"><table cellspacing=\"0\" cellpadding=\"0\"><tr></tr></table></div>").prependTo(_5d);
var tr=tb.find("tr");
for(var i=0;i<_5c.toolbar.length;i++){
var btn=_5c.toolbar[i];
if(btn=="-"){
$("<td><div class=\"datagrid-btn-separator\"></div></td>").appendTo(tr);
}else{
var td=$("<td></td>").appendTo(tr);
var _63=$("<a href=\"javascript:;\"></a>").appendTo(td);
_63[0].onclick=eval(btn.handler||function(){
});
_63.linkbutton($.extend({},btn,{plain:true}));
}
}
}else{
$(_5c.toolbar).addClass("datagrid-toolbar").prependTo(_5d);
$(_5c.toolbar).show();
}
}else{
$("div.datagrid-toolbar",_5d).remove();
}
$("div.datagrid-pager",_5d).remove();
if(_5c.pagination){
var _64=$("<div class=\"datagrid-pager\"></div>");
if(_5c.pagePosition=="bottom"){
_64.appendTo(_5d);
}else{
if(_5c.pagePosition=="top"){
_64.addClass("datagrid-pager-top").prependTo(_5d);
}else{
var _65=$("<div class=\"datagrid-pager datagrid-pager-top\"></div>").prependTo(_5d);
_64.appendTo(_5d);
_64=_64.add(_65);
}
}
_64.pagination({total:0,pageNumber:_5c.pageNumber,pageSize:_5c.pageSize,pageList:_5c.pageList,onSelectPage:function(_66,_67){
_5c.pageNumber=_66||1;
_5c.pageSize=_67;
_64.pagination("refresh",{pageNumber:_66,pageSize:_67});
_c1(_5a);
}});
_5c.pageSize=_64.pagination("options").pageSize;
}
function _61(_68,_69,_6a){
if(!_69){
return;
}
$(_68).show();
$(_68).empty();
var tmp=$("<div class=\"datagrid-cell\" style=\"position:absolute;left:-99999px\"></div>").appendTo("body");
tmp._outerWidth(99);
var _6b=100-parseInt(tmp[0].style.width);
tmp.remove();
var _6c=[];
var _6d=[];
var _6e=[];
if(_5c.sortName){
_6c=_5c.sortName.split(",");
_6d=_5c.sortOrder.split(",");
}
var t=$("<table class=\"datagrid-htable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>").appendTo(_68);
for(var i=0;i<_69.length;i++){
var tr=$("<tr class=\"datagrid-header-row\"></tr>").appendTo($("tbody",t));
var _6f=_69[i];
for(var j=0;j<_6f.length;j++){
var col=_6f[j];
var _70="";
if(col.rowspan){
_70+="rowspan=\""+col.rowspan+"\" ";
}
if(col.colspan){
_70+="colspan=\""+col.colspan+"\" ";
if(!col.id){
col.id=["datagrid-td-group"+_1,i,j].join("-");
}
}
if(col.id){
_70+="id=\""+col.id+"\"";
}
var td=$("<td "+_70+"></td>").appendTo(tr);
if(col.checkbox){
td.attr("field",col.field);
$("<div class=\"datagrid-header-check\"></div>").html("<input type=\"checkbox\"/>").appendTo(td);
}else{
if(col.field){
td.attr("field",col.field);
td.append("<div class=\"datagrid-cell\"><span></span><span class=\"datagrid-sort-icon\"></span></div>");
td.find("span:first").html(col.title);
var _71=td.find("div.datagrid-cell");
var pos=_2(_6c,col.field);
if(pos>=0){
_71.addClass("datagrid-sort-"+_6d[pos]);
}
if(col.sortable){
_71.addClass("datagrid-sort");
}
if(col.resizable==false){
_71.attr("resizable","false");
}
if(col.width){
var _72=$.parser.parseValue("width",col.width,dc.view,_5c.scrollbarSize+(_5c.rownumbers?_5c.rownumberWidth:0));
col.deltaWidth=_6b;
col.boxWidth=_72-_6b;
}else{
col.auto=true;
}
_71.css("text-align",(col.halign||col.align||""));
col.cellClass=_5b.cellClassPrefix+"-"+col.field.replace(/[\.|\s]/g,"-");
_71.addClass(col.cellClass);
}else{
$("<div class=\"datagrid-cell-group\"></div>").html(col.title).appendTo(td);
}
}
if(col.hidden){
td.hide();
_6e.push(col.field);
}
}
}
if(_6a&&_5c.rownumbers){
var td=$("<td rowspan=\""+_5c.frozenColumns.length+"\"><div class=\"datagrid-header-rownumber\"></div></td>");
if($("tr",t).length==0){
td.wrap("<tr class=\"datagrid-header-row\"></tr>").parent().appendTo($("tbody",t));
}else{
td.prependTo($("tr:first",t));
}
}
for(var i=0;i<_6e.length;i++){
_c3(_5a,_6e[i],-1);
}
};
function _62(){
var _73=[[".datagrid-header-rownumber",(_5c.rownumberWidth-1)+"px"],[".datagrid-cell-rownumber",(_5c.rownumberWidth-1)+"px"]];
var _74=_75(_5a,true).concat(_75(_5a));
for(var i=0;i<_74.length;i++){
var col=_76(_5a,_74[i]);
if(col&&!col.checkbox){
_73.push(["."+col.cellClass,col.boxWidth?col.boxWidth+"px":"auto"]);
}
}
_5b.ss.add(_73);
_5b.ss.dirty(_5b.cellSelectorPrefix);
_5b.cellSelectorPrefix="."+_5b.cellClassPrefix;
};
};
function _77(_78){
var _79=$.data(_78,"datagrid");
var _7a=_79.panel;
var _7b=_79.options;
var dc=_79.dc;
var _7c=dc.header1.add(dc.header2);
_7c._unbind(".datagrid");
for(var _7d in _7b.headerEvents){
_7c._bind(_7d+".datagrid",_7b.headerEvents[_7d]);
}
var _7e=_7c.find("div.datagrid-cell");
var _7f=_7b.resizeHandle=="right"?"e":(_7b.resizeHandle=="left"?"w":"e,w");
_7e.each(function(){
$(this).resizable({handles:_7f,edge:_7b.resizeEdge,disabled:($(this).attr("resizable")?$(this).attr("resizable")=="false":false),minWidth:25,onStartResize:function(e){
_79.resizing=true;
_7c.css("cursor",$("body").css("cursor"));
if(!_79.proxy){
_79.proxy=$("<div class=\"datagrid-resize-proxy\"></div>").appendTo(dc.view);
}
if(e.data.dir=="e"){
e.data.deltaEdge=$(this)._outerWidth()-(e.pageX-$(this).offset().left);
}else{
e.data.deltaEdge=$(this).offset().left-e.pageX-1;
}
_79.proxy.css({left:e.pageX-$(_7a).offset().left-1+e.data.deltaEdge,display:"none"});
setTimeout(function(){
if(_79.proxy){
_79.proxy.show();
}
},500);
},onResize:function(e){
_79.proxy.css({left:e.pageX-$(_7a).offset().left-1+e.data.deltaEdge,display:"block"});
return false;
},onStopResize:function(e){
_7c.css("cursor","");
$(this).css("height","");
var _80=$(this).parent().attr("field");
var col=_76(_78,_80);
col.width=$(this)._outerWidth()+1;
col.boxWidth=col.width-col.deltaWidth;
col.auto=undefined;
$(this).css("width","");
$(_78).datagrid("fixColumnSize",_80);
_79.proxy.remove();
_79.proxy=null;
if($(this).parents("div:first.datagrid-header").parent().hasClass("datagrid-view1")){
_20(_78);
}
$(_78).datagrid("fitColumns");
_7b.onResizeColumn.call(_78,_80,col.width);
setTimeout(function(){
_79.resizing=false;
},0);
}});
});
var bb=dc.body1.add(dc.body2);
bb._unbind();
for(var _7d in _7b.rowEvents){
bb._bind(_7d,_7b.rowEvents[_7d]);
}
dc.body1._bind("mousewheel DOMMouseScroll MozMousePixelScroll",function(e){
e.preventDefault();
var e1=e.originalEvent||window.event;
var _81=e1.wheelDelta||e1.detail*(-1);
if("deltaY" in e1){
_81=e1.deltaY*-1;
}
var dg=$(e.target).closest("div.datagrid-view").children(".datagrid-f");
var dc=dg.data("datagrid").dc;
dc.body2.scrollTop(dc.body2.scrollTop()-_81);
});
dc.body2._bind("scroll",function(){
var b1=dc.view1.children("div.datagrid-body");
var stv=$(this).scrollTop();
$(this).scrollTop(stv);
b1.scrollTop(stv);
var c1=dc.body1.children(":first");
var c2=dc.body2.children(":first");
if(c1.length&&c2.length){
var _82=c1.offset().top;
var _83=c2.offset().top;
if(_82!=_83){
b1.scrollTop(b1.scrollTop()+_82-_83);
}
}
dc.view2.children("div.datagrid-header,div.datagrid-footer")._scrollLeft($(this)._scrollLeft());
dc.body2.children("table.datagrid-btable-frozen").css("left",-$(this)._scrollLeft());
});
};
function _84(_85){
return function(e){
var td=$(e.target).closest("td[field]");
if(td.length){
var _86=_87(td);
if(!$(_86).data("datagrid").resizing&&_85){
td.addClass("datagrid-header-over");
}else{
td.removeClass("datagrid-header-over");
}
}
};
};
function _88(e){
var _89=_87(e.target);
var _8a=$(_89).datagrid("options");
var ck=$(e.target).closest("input[type=checkbox]");
if(ck.length){
if(_8a.singleSelect&&_8a.selectOnCheck){
return false;
}
if(ck.is(":checked")){
_8b(_89);
}else{
_8c(_89);
}
e.stopPropagation();
}else{
var _8d=$(e.target).closest(".datagrid-cell");
if(_8d.length){
var p1=_8d.offset().left+5;
var p2=_8d.offset().left+_8d._outerWidth()-5;
if(e.pageX<p2&&e.pageX>p1){
_8e(_89,_8d.parent().attr("field"));
}
}
}
};
function _8f(e){
var _90=_87(e.target);
var _91=$(_90).datagrid("options");
var _92=$(e.target).closest(".datagrid-cell");
if(_92.length){
var p1=_92.offset().left+5;
var p2=_92.offset().left+_92._outerWidth()-5;
var _93=_91.resizeHandle=="right"?(e.pageX>p2):(_91.resizeHandle=="left"?(e.pageX<p1):(e.pageX<p1||e.pageX>p2));
if(_93){
var _94=_92.parent().attr("field");
var col=_76(_90,_94);
if(col.resizable==false){
return;
}
$(_90).datagrid("autoSizeColumn",_94);
col.auto=false;
}
}
};
function _95(e){
var _96=_87(e.target);
var _97=$(_96).datagrid("options");
var td=$(e.target).closest("td[field]");
_97.onHeaderContextMenu.call(_96,e,td.attr("field"));
};
function _98(_99){
return function(e){
var tr=_9a(e.target);
if(!tr){
return;
}
var _9b=_87(tr);
if($.data(_9b,"datagrid").resizing){
return;
}
var _9c=_9d(tr);
if(_99){
_9e(_9b,_9c);
}else{
var _9f=$.data(_9b,"datagrid").options;
_9f.finder.getTr(_9b,_9c).removeClass("datagrid-row-over");
}
};
};
function _a0(e){
var tr=_9a(e.target);
if(!tr){
return;
}
var _a1=_87(tr);
var _a2=$.data(_a1,"datagrid").options;
var _a3=_9d(tr);
var tt=$(e.target);
if(tt.parent().hasClass("datagrid-cell-check")){
if(_a2.singleSelect&&_a2.selectOnCheck){
tt._propAttr("checked",!tt.is(":checked"));
_a4(_a1,_a3);
}else{
if(tt.is(":checked")){
tt._propAttr("checked",false);
_a4(_a1,_a3);
}else{
tt._propAttr("checked",true);
_a5(_a1,_a3);
}
}
}else{
var row=_a2.finder.getRow(_a1,_a3);
var td=tt.closest("td[field]",tr);
if(td.length){
var _a6=td.attr("field");
_a2.onClickCell.call(_a1,_a3,_a6,row[_a6]);
}
if(_a2.singleSelect==true){
_a7(_a1,_a3);
}else{
if(_a2.ctrlSelect){
if(e.metaKey||e.ctrlKey){
if(tr.hasClass("datagrid-row-selected")){
_a8(_a1,_a3);
}else{
_a7(_a1,_a3);
}
}else{
if(e.shiftKey){
$(_a1).datagrid("clearSelections");
var _a9=Math.min(_a2.lastSelectedIndex||0,_a3);
var _aa=Math.max(_a2.lastSelectedIndex||0,_a3);
for(var i=_a9;i<=_aa;i++){
_a7(_a1,i);
}
}else{
$(_a1).datagrid("clearSelections");
_a7(_a1,_a3);
_a2.lastSelectedIndex=_a3;
}
}
}else{
if(tr.hasClass("datagrid-row-selected")){
_a8(_a1,_a3);
}else{
_a7(_a1,_a3);
}
}
}
_a2.onClickRow.apply(_a1,_5(_a1,[_a3,row]));
}
};
function _ab(e){
var tr=_9a(e.target);
if(!tr){
return;
}
var _ac=_87(tr);
var _ad=$.data(_ac,"datagrid").options;
var _ae=_9d(tr);
var row=_ad.finder.getRow(_ac,_ae);
var td=$(e.target).closest("td[field]",tr);
if(td.length){
var _af=td.attr("field");
_ad.onDblClickCell.call(_ac,_ae,_af,row[_af]);
}
_ad.onDblClickRow.apply(_ac,_5(_ac,[_ae,row]));
};
function _b0(e){
var tr=_9a(e.target);
if(tr){
var _b1=_87(tr);
var _b2=$.data(_b1,"datagrid").options;
var _b3=_9d(tr);
var row=_b2.finder.getRow(_b1,_b3);
_b2.onRowContextMenu.call(_b1,e,_b3,row);
}else{
var _b4=_9a(e.target,".datagrid-body");
if(_b4){
var _b1=_87(_b4);
var _b2=$.data(_b1,"datagrid").options;
_b2.onRowContextMenu.call(_b1,e,-1,null);
}
}
};
function _87(t){
return $(t).closest("div.datagrid-view").children(".datagrid-f")[0];
};
function _9a(t,_b5){
var tr=$(t).closest(_b5||"tr.datagrid-row");
if(tr.length&&tr.parent().length){
return tr;
}else{
return undefined;
}
};
function _9d(tr){
if(tr.attr("datagrid-row-index")){
return parseInt(tr.attr("datagrid-row-index"));
}else{
return tr.attr("node-id");
}
};
function _8e(_b6,_b7){
var _b8=$.data(_b6,"datagrid");
var _b9=_b8.options;
_b7=_b7||{};
var _ba={sortName:_b9.sortName,sortOrder:_b9.sortOrder};
if(typeof _b7=="object"){
$.extend(_ba,_b7);
}
var _bb=[];
var _bc=[];
if(_ba.sortName){
_bb=_ba.sortName.split(",");
_bc=_ba.sortOrder.split(",");
}
if(typeof _b7=="string"){
var _bd=_b7;
var col=_76(_b6,_bd);
if(!col.sortable||_b8.resizing){
return;
}
var _be=col.order||"asc";
var pos=_2(_bb,_bd);
if(pos>=0){
var _bf=_bc[pos]=="asc"?"desc":"asc";
if(_b9.multiSort&&_bf==_be){
_bb.splice(pos,1);
_bc.splice(pos,1);
}else{
_bc[pos]=_bf;
}
}else{
if(_b9.multiSort){
_bb.push(_bd);
_bc.push(_be);
}else{
_bb=[_bd];
_bc=[_be];
}
}
_ba.sortName=_bb.join(",");
_ba.sortOrder=_bc.join(",");
}
if(_b9.onBeforeSortColumn.call(_b6,_ba.sortName,_ba.sortOrder)==false){
return;
}
$.extend(_b9,_ba);
var dc=_b8.dc;
var _c0=dc.header1.add(dc.header2);
_c0.find("div.datagrid-cell").removeClass("datagrid-sort-asc datagrid-sort-desc");
for(var i=0;i<_bb.length;i++){
var col=_76(_b6,_bb[i]);
_c0.find("div."+col.cellClass).addClass("datagrid-sort-"+_bc[i]);
}
if(_b9.remoteSort){
_c1(_b6);
}else{
_c2(_b6,$(_b6).datagrid("getData"));
}
_b9.onSortColumn.call(_b6,_b9.sortName,_b9.sortOrder);
};
function _c3(_c4,_c5,_c6){
_c7(true);
_c7(false);
function _c7(_c8){
var aa=_c9(_c4,_c8);
if(aa.length){
var _ca=aa[aa.length-1];
var _cb=_2(_ca,_c5);
if(_cb>=0){
for(var _cc=0;_cc<aa.length-1;_cc++){
var td=$("#"+aa[_cc][_cb]);
var _cd=parseInt(td.attr("colspan")||1)+(_c6||0);
td.attr("colspan",_cd);
if(_cd){
td.show();
}else{
td.hide();
}
}
}
}
};
};
function _ce(_cf){
var _d0=$.data(_cf,"datagrid");
var _d1=_d0.options;
var dc=_d0.dc;
var _d2=dc.view2.children("div.datagrid-header");
var _d3=_d2.children("div.datagrid-header-inner");
dc.body2.css("overflow-x","");
_d4();
_d5();
_d6();
_d4(true);
_d3.show();
if(_d2.width()>=_d2.find("table").width()){
dc.body2.css("overflow-x","hidden");
}
if(!_d1.showHeader){
_d3.hide();
}
function _d6(){
if(!_d1.fitColumns){
return;
}
if(!_d0.leftWidth){
_d0.leftWidth=0;
}
var _d7=0;
var cc=[];
var _d8=_75(_cf,false);
for(var i=0;i<_d8.length;i++){
var col=_76(_cf,_d8[i]);
if(_d9(col)){
_d7+=col.width;
cc.push({field:col.field,col:col,addingWidth:0});
}
}
if(!_d7){
return;
}
cc[cc.length-1].addingWidth-=_d0.leftWidth;
_d3.show();
var _da=_d2.width()-_d2.find("table").width()-_d1.scrollbarSize+_d0.leftWidth;
var _db=_da/_d7;
if(!_d1.showHeader){
_d3.hide();
}
for(var i=0;i<cc.length;i++){
var c=cc[i];
var _dc=parseInt(c.col.width*_db);
c.addingWidth+=_dc;
_da-=_dc;
}
cc[cc.length-1].addingWidth+=_da;
for(var i=0;i<cc.length;i++){
var c=cc[i];
if(c.col.boxWidth+c.addingWidth>0){
c.col.boxWidth+=c.addingWidth;
c.col.width+=c.addingWidth;
}
}
_d0.leftWidth=_da;
$(_cf).datagrid("fixColumnSize");
};
function _d5(){
var _dd=false;
var _de=_75(_cf,true).concat(_75(_cf,false));
$.map(_de,function(_df){
var col=_76(_cf,_df);
if(String(col.width||"").indexOf("%")>=0){
var _e0=$.parser.parseValue("width",col.width,dc.view,_d1.scrollbarSize+(_d1.rownumbers?_d1.rownumberWidth:0))-col.deltaWidth;
if(_e0>0){
col.boxWidth=_e0;
_dd=true;
}
}
});
if(_dd){
$(_cf).datagrid("fixColumnSize");
}
};
function _d4(fit){
var _e1=dc.header1.add(dc.header2).find(".datagrid-cell-group");
if(_e1.length){
_e1.each(function(){
$(this)._outerWidth(fit?$(this).parent().width():10);
});
if(fit){
_20(_cf);
}
}
};
function _d9(col){
if(String(col.width||"").indexOf("%")>=0){
return false;
}
if(!col.hidden&&!col.checkbox&&!col.auto&&!col.fixed){
return true;
}
};
};
function _e2(_e3,_e4){
var _e5=$.data(_e3,"datagrid");
var _e6=_e5.options;
var dc=_e5.dc;
var tmp=$("<div class=\"datagrid-cell\" style=\"position:absolute;left:-9999px\"></div>").appendTo("body");
if(_e4){
_1a(_e4);
$(_e3).datagrid("fitColumns");
}else{
var _e7=false;
var _e8=_75(_e3,true).concat(_75(_e3,false));
for(var i=0;i<_e8.length;i++){
var _e4=_e8[i];
var col=_76(_e3,_e4);
if(col.auto){
_1a(_e4);
_e7=true;
}
}
if(_e7){
$(_e3).datagrid("fitColumns");
}
}
tmp.remove();
function _1a(_e9){
var _ea=dc.view.find("div.datagrid-header td[field=\""+_e9+"\"] div.datagrid-cell");
_ea.css("width","");
var col=$(_e3).datagrid("getColumnOption",_e9);
col.width=undefined;
col.boxWidth=undefined;
col.auto=true;
$(_e3).datagrid("fixColumnSize",_e9);
var _eb=Math.max(_ec("header"),_ec("allbody"),_ec("allfooter"))+1;
_ea._outerWidth(_eb-1);
col.width=_eb;
col.boxWidth=parseInt(_ea[0].style.width);
col.deltaWidth=_eb-col.boxWidth;
_ea.css("width","");
$(_e3).datagrid("fixColumnSize",_e9);
_e6.onResizeColumn.call(_e3,_e9,col.width);
function _ec(_ed){
var _ee=0;
if(_ed=="header"){
_ee=_ef(_ea);
}else{
_e6.finder.getTr(_e3,0,_ed).find("td[field=\""+_e9+"\"] div.datagrid-cell").each(function(){
var w=_ef($(this));
if(_ee<w){
_ee=w;
}
});
}
return _ee;
function _ef(_f0){
return _f0.is(":visible")?_f0._outerWidth():tmp.html(_f0.html())._outerWidth();
};
};
};
};
function _f1(_f2,_f3){
var _f4=$.data(_f2,"datagrid");
var _f5=_f4.options;
var dc=_f4.dc;
var _f6=dc.view.find("table.datagrid-btable,table.datagrid-ftable");
_f6.css("table-layout","fixed");
if(_f3){
fix(_f3);
}else{
var ff=_75(_f2,true).concat(_75(_f2,false));
for(var i=0;i<ff.length;i++){
fix(ff[i]);
}
}
_f6.css("table-layout","");
_f7(_f2);
_34(_f2);
_f8(_f2);
function fix(_f9){
var col=_76(_f2,_f9);
if(col.cellClass){
_f4.ss.set("."+col.cellClass,col.boxWidth?col.boxWidth+"px":"auto");
}
};
};
function _f7(_fa,tds){
var dc=$.data(_fa,"datagrid").dc;
tds=tds||dc.view.find("td.datagrid-td-merged");
tds.each(function(){
var td=$(this);
var _fb=td.attr("colspan")||1;
if(_fb>1){
var col=_76(_fa,td.attr("field"));
var _fc=col.boxWidth+col.deltaWidth-1;
for(var i=1;i<_fb;i++){
td=td.next();
col=_76(_fa,td.attr("field"));
_fc+=col.boxWidth+col.deltaWidth;
}
$(this).children("div.datagrid-cell")._outerWidth(_fc);
}
});
};
function _f8(_fd){
var dc=$.data(_fd,"datagrid").dc;
dc.view.find("div.datagrid-editable").each(function(){
var _fe=$(this);
var _ff=_fe.parent().attr("field");
var col=$(_fd).datagrid("getColumnOption",_ff);
_fe._outerWidth(col.boxWidth+col.deltaWidth-1);
var ed=$.data(this,"datagrid.editor");
if(ed.actions.resize){
ed.actions.resize(ed.target,_fe.width());
}
});
};
function _76(_100,_101){
function find(_102){
if(_102){
for(var i=0;i<_102.length;i++){
var cc=_102[i];
for(var j=0;j<cc.length;j++){
var c=cc[j];
if(c.field==_101){
return c;
}
}
}
}
return null;
};
var opts=$.data(_100,"datagrid").options;
var col=find(opts.columns);
if(!col){
col=find(opts.frozenColumns);
}
return col;
};
function _c9(_103,_104){
var opts=$.data(_103,"datagrid").options;
var _105=_104?opts.frozenColumns:opts.columns;
var aa=[];
var _106=_107();
for(var i=0;i<_105.length;i++){
aa[i]=new Array(_106);
}
for(var _108=0;_108<_105.length;_108++){
$.map(_105[_108],function(col){
var _109=_10a(aa[_108]);
if(_109>=0){
var _10b=col.field||col.id||"";
for(var c=0;c<(col.colspan||1);c++){
for(var r=0;r<(col.rowspan||1);r++){
aa[_108+r][_109]=_10b;
}
_109++;
}
}
});
}
return aa;
function _107(){
var _10c=0;
$.map(_105[0]||[],function(col){
_10c+=col.colspan||1;
});
return _10c;
};
function _10a(a){
for(var i=0;i<a.length;i++){
if(a[i]==undefined){
return i;
}
}
return -1;
};
};
function _75(_10d,_10e){
var aa=_c9(_10d,_10e);
return aa.length?aa[aa.length-1]:aa;
};
function _c2(_10f,data){
var _110=$.data(_10f,"datagrid");
var opts=_110.options;
var dc=_110.dc;
data=opts.loadFilter.call(_10f,data);
if($.isArray(data)){
data={total:data.length,rows:data};
}
data.total=parseInt(data.total);
_110.data=data;
if(data.footer){
_110.footer=data.footer;
}
if(!opts.remoteSort&&opts.sortName){
var _111=opts.sortName.split(",");
var _112=opts.sortOrder.split(",");
data.rows.sort(function(r1,r2){
var r=0;
for(var i=0;i<_111.length;i++){
var sn=_111[i];
var so=_112[i];
var col=_76(_10f,sn);
var _113=col.sorter||function(a,b){
return a==b?0:(a>b?1:-1);
};
r=_113(r1[sn],r2[sn],r1,r2)*(so=="asc"?1:-1);
if(r!=0){
return r;
}
}
return r;
});
}
if(opts.view.onBeforeRender){
opts.view.onBeforeRender.call(opts.view,_10f,data.rows);
}
opts.view.render.call(opts.view,_10f,dc.body2,false);
opts.view.render.call(opts.view,_10f,dc.body1,true);
if(opts.showFooter){
opts.view.renderFooter.call(opts.view,_10f,dc.footer2,false);
opts.view.renderFooter.call(opts.view,_10f,dc.footer1,true);
}
if(opts.view.onAfterRender){
opts.view.onAfterRender.call(opts.view,_10f);
}
_110.ss.clean();
var _114=$(_10f).datagrid("getPager");
if(_114.length){
var _115=_114.pagination("options");
if(_115.total!=data.total){
_114.pagination("refresh",{pageNumber:opts.pageNumber,total:data.total});
if(opts.pageNumber!=_115.pageNumber&&_115.pageNumber>0){
opts.pageNumber=_115.pageNumber;
_c1(_10f);
}
}
}
_34(_10f);
dc.body2.triggerHandler("scroll");
$(_10f).datagrid("setSelectionState");
$(_10f).datagrid("autoSizeColumn");
opts.onLoadSuccess.call(_10f,data);
};
function _116(_117){
var _118=$.data(_117,"datagrid");
var opts=_118.options;
var dc=_118.dc;
dc.header1.add(dc.header2).find("input[type=checkbox]")._propAttr("checked",false);
if(opts.idField){
var _119=$.data(_117,"treegrid")?true:false;
var _11a=opts.onSelect;
var _11b=opts.onCheck;
opts.onSelect=opts.onCheck=function(){
};
var rows=opts.finder.getRows(_117);
for(var i=0;i<rows.length;i++){
var row=rows[i];
var _11c=_119?row[opts.idField]:$(_117).datagrid("getRowIndex",row[opts.idField]);
if(_11d(_118.selectedRows,row)){
_a7(_117,_11c,true,true);
}
if(_11d(_118.checkedRows,row)){
_a4(_117,_11c,true);
}
}
opts.onSelect=_11a;
opts.onCheck=_11b;
}
function _11d(a,r){
for(var i=0;i<a.length;i++){
if(a[i][opts.idField]==r[opts.idField]){
a[i]=r;
return true;
}
}
return false;
};
};
function _11e(_11f,row){
var _120=$.data(_11f,"datagrid");
var opts=_120.options;
var rows=_120.data.rows;
if(typeof row=="object"){
return _2(rows,row);
}else{
for(var i=0;i<rows.length;i++){
if(rows[i][opts.idField]==row){
return i;
}
}
return -1;
}
};
function _121(_122){
var _123=$.data(_122,"datagrid");
var opts=_123.options;
var data=_123.data;
if(opts.idField){
return _123.selectedRows;
}else{
var rows=[];
opts.finder.getTr(_122,"","selected",2).each(function(){
rows.push(opts.finder.getRow(_122,$(this)));
});
return rows;
}
};
function _124(_125){
var _126=$.data(_125,"datagrid");
var opts=_126.options;
if(opts.idField){
return _126.checkedRows;
}else{
var rows=[];
opts.finder.getTr(_125,"","checked",2).each(function(){
rows.push(opts.finder.getRow(_125,$(this)));
});
return rows;
}
};
function _127(_128,_129){
var _12a=$.data(_128,"datagrid");
var dc=_12a.dc;
var opts=_12a.options;
var tr=opts.finder.getTr(_128,_129);
if(tr.length){
if(tr.closest("table").hasClass("datagrid-btable-frozen")){
return;
}
var _12b=dc.view2.children("div.datagrid-header")._outerHeight();
var _12c=dc.body2;
var _12d=opts.scrollbarSize;
if(_12c[0].offsetHeight&&_12c[0].clientHeight&&_12c[0].offsetHeight<=_12c[0].clientHeight){
_12d=0;
}
var _12e=_12c.outerHeight(true)-_12c.outerHeight();
var top=tr.offset().top-dc.view2.offset().top-_12b-_12e;
if(top<0){
_12c.scrollTop(_12c.scrollTop()+top);
}else{
if(top+tr._outerHeight()>_12c.height()-_12d){
_12c.scrollTop(_12c.scrollTop()+top+tr._outerHeight()-_12c.height()+_12d);
}
}
}
};
function _9e(_12f,_130){
var _131=$.data(_12f,"datagrid");
var opts=_131.options;
opts.finder.getTr(_12f,_131.highlightIndex).removeClass("datagrid-row-over");
opts.finder.getTr(_12f,_130).addClass("datagrid-row-over");
_131.highlightIndex=_130;
};
function _a7(_132,_133,_134,_135){
var _136=$.data(_132,"datagrid");
var opts=_136.options;
var row=opts.finder.getRow(_132,_133);
if(!row){
return;
}
if(opts.onBeforeSelect.apply(_132,_5(_132,[_133,row]))==false){
return;
}
if(opts.singleSelect){
_137(_132,true);
_136.selectedRows=[];
}
if(!_134&&opts.checkOnSelect){
_a4(_132,_133,true);
}
if(opts.idField){
_4(_136.selectedRows,opts.idField,row);
}
opts.finder.getTr(_132,_133).addClass("datagrid-row-selected");
opts.onSelect.apply(_132,_5(_132,[_133,row]));
if(!_135&&opts.scrollOnSelect){
_127(_132,_133);
}
};
function _a8(_138,_139,_13a){
var _13b=$.data(_138,"datagrid");
var dc=_13b.dc;
var opts=_13b.options;
var row=opts.finder.getRow(_138,_139);
if(!row){
return;
}
if(opts.onBeforeUnselect.apply(_138,_5(_138,[_139,row]))==false){
return;
}
if(!_13a&&opts.checkOnSelect){
_a5(_138,_139,true);
}
opts.finder.getTr(_138,_139).removeClass("datagrid-row-selected");
if(opts.idField){
_3(_13b.selectedRows,opts.idField,row[opts.idField]);
}
opts.onUnselect.apply(_138,_5(_138,[_139,row]));
};
function _13c(_13d,_13e){
var _13f=$.data(_13d,"datagrid");
var opts=_13f.options;
var rows=opts.finder.getRows(_13d);
var _140=$.data(_13d,"datagrid").selectedRows;
if(!_13e&&opts.checkOnSelect){
_8b(_13d,true);
}
opts.finder.getTr(_13d,"","allbody").addClass("datagrid-row-selected");
if(opts.idField){
for(var _141=0;_141<rows.length;_141++){
_4(_140,opts.idField,rows[_141]);
}
}
opts.onSelectAll.call(_13d,rows);
};
function _137(_142,_143){
var _144=$.data(_142,"datagrid");
var opts=_144.options;
var rows=opts.finder.getRows(_142);
var _145=$.data(_142,"datagrid").selectedRows;
if(!_143&&opts.checkOnSelect){
_8c(_142,true);
}
opts.finder.getTr(_142,"","selected").removeClass("datagrid-row-selected");
if(opts.idField){
for(var _146=0;_146<rows.length;_146++){
_3(_145,opts.idField,rows[_146][opts.idField]);
}
}
opts.onUnselectAll.call(_142,rows);
};
function _a4(_147,_148,_149){
var _14a=$.data(_147,"datagrid");
var opts=_14a.options;
var row=opts.finder.getRow(_147,_148);
if(!row){
return;
}
if(opts.onBeforeCheck.apply(_147,_5(_147,[_148,row]))==false){
return;
}
if(opts.singleSelect&&opts.selectOnCheck){
_8c(_147,true);
_14a.checkedRows=[];
}
if(!_149&&opts.selectOnCheck){
_a7(_147,_148,true);
}
var tr=opts.finder.getTr(_147,_148).addClass("datagrid-row-checked");
tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked",true);
tr=opts.finder.getTr(_147,"","checked",2);
if(tr.length==opts.finder.getRows(_147).length){
var dc=_14a.dc;
dc.header1.add(dc.header2).find("input[type=checkbox]")._propAttr("checked",true);
}
if(opts.idField){
_4(_14a.checkedRows,opts.idField,row);
}
opts.onCheck.apply(_147,_5(_147,[_148,row]));
};
function _a5(_14b,_14c,_14d){
var _14e=$.data(_14b,"datagrid");
var opts=_14e.options;
var row=opts.finder.getRow(_14b,_14c);
if(!row){
return;
}
if(opts.onBeforeUncheck.apply(_14b,_5(_14b,[_14c,row]))==false){
return;
}
if(!_14d&&opts.selectOnCheck){
_a8(_14b,_14c,true);
}
var tr=opts.finder.getTr(_14b,_14c).removeClass("datagrid-row-checked");
tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked",false);
var dc=_14e.dc;
var _14f=dc.header1.add(dc.header2);
_14f.find("input[type=checkbox]")._propAttr("checked",false);
if(opts.idField){
_3(_14e.checkedRows,opts.idField,row[opts.idField]);
}
opts.onUncheck.apply(_14b,_5(_14b,[_14c,row]));
};
function _8b(_150,_151){
var _152=$.data(_150,"datagrid");
var opts=_152.options;
var rows=opts.finder.getRows(_150);
if(!_151&&opts.selectOnCheck){
_13c(_150,true);
}
var dc=_152.dc;
var hck=dc.header1.add(dc.header2).find("input[type=checkbox]");
var bck=opts.finder.getTr(_150,"","allbody").addClass("datagrid-row-checked").find("div.datagrid-cell-check input[type=checkbox]");
hck.add(bck)._propAttr("checked",true);
if(opts.idField){
for(var i=0;i<rows.length;i++){
_4(_152.checkedRows,opts.idField,rows[i]);
}
}
opts.onCheckAll.call(_150,rows);
};
function _8c(_153,_154){
var _155=$.data(_153,"datagrid");
var opts=_155.options;
var rows=opts.finder.getRows(_153);
if(!_154&&opts.selectOnCheck){
_137(_153,true);
}
var dc=_155.dc;
var hck=dc.header1.add(dc.header2).find("input[type=checkbox]");
var bck=opts.finder.getTr(_153,"","checked").removeClass("datagrid-row-checked").find("div.datagrid-cell-check input[type=checkbox]");
hck.add(bck)._propAttr("checked",false);
if(opts.idField){
for(var i=0;i<rows.length;i++){
_3(_155.checkedRows,opts.idField,rows[i][opts.idField]);
}
}
opts.onUncheckAll.call(_153,rows);
};
function _156(_157,_158){
var opts=$.data(_157,"datagrid").options;
var tr=opts.finder.getTr(_157,_158);
var row=opts.finder.getRow(_157,_158);
if(tr.hasClass("datagrid-row-editing")){
return;
}
if(opts.onBeforeEdit.apply(_157,_5(_157,[_158,row]))==false){
return;
}
tr.addClass("datagrid-row-editing");
_159(_157,_158);
_f8(_157);
tr.find("div.datagrid-editable").each(function(){
var _15a=$(this).parent().attr("field");
var ed=$.data(this,"datagrid.editor");
ed.actions.setValue(ed.target,row[_15a]);
});
_15b(_157,_158);
opts.onBeginEdit.apply(_157,_5(_157,[_158,row]));
};
function _15c(_15d,_15e,_15f){
var _160=$.data(_15d,"datagrid");
var opts=_160.options;
var _161=_160.updatedRows;
var _162=_160.insertedRows;
var tr=opts.finder.getTr(_15d,_15e);
var row=opts.finder.getRow(_15d,_15e);
if(!tr.hasClass("datagrid-row-editing")){
return;
}
if(!_15f){
if(!_15b(_15d,_15e)){
return;
}
var _163=false;
var _164={};
tr.find("div.datagrid-editable").each(function(){
var _165=$(this).parent().attr("field");
var ed=$.data(this,"datagrid.editor");
var t=$(ed.target);
var _166=t.data("textbox")?t.textbox("textbox"):t;
if(_166.is(":focus")){
_166.triggerHandler("blur");
}
var _167=ed.actions.getValue(ed.target);
if(row[_165]!==_167){
row[_165]=_167;
_163=true;
_164[_165]=_167;
}
});
if(_163){
if(_2(_162,row)==-1){
if(_2(_161,row)==-1){
_161.push(row);
}
}
}
opts.onEndEdit.apply(_15d,_5(_15d,[_15e,row,_164]));
}
tr.removeClass("datagrid-row-editing");
_168(_15d,_15e);
$(_15d).datagrid("refreshRow",_15e);
if(!_15f){
opts.onAfterEdit.apply(_15d,_5(_15d,[_15e,row,_164]));
}else{
opts.onCancelEdit.apply(_15d,_5(_15d,[_15e,row]));
}
};
function _169(_16a,_16b){
var opts=$.data(_16a,"datagrid").options;
var tr=opts.finder.getTr(_16a,_16b);
var _16c=[];
tr.children("td").each(function(){
var cell=$(this).find("div.datagrid-editable");
if(cell.length){
var ed=$.data(cell[0],"datagrid.editor");
_16c.push(ed);
}
});
return _16c;
};
function _16d(_16e,_16f){
var _170=_169(_16e,_16f.index!=undefined?_16f.index:_16f.id);
for(var i=0;i<_170.length;i++){
if(_170[i].field==_16f.field){
return _170[i];
}
}
return null;
};
function _159(_171,_172){
var opts=$.data(_171,"datagrid").options;
var tr=opts.finder.getTr(_171,_172);
tr.children("td").each(function(){
var cell=$(this).find("div.datagrid-cell");
var _173=$(this).attr("field");
var col=_76(_171,_173);
if(col&&col.editor){
var _174,_175;
if(typeof col.editor=="string"){
_174=col.editor;
}else{
_174=col.editor.type;
_175=col.editor.options;
}
var _176=opts.editors[_174];
if(_176){
var _177=cell.html();
var _178=cell._outerWidth();
cell.addClass("datagrid-editable");
cell._outerWidth(_178);
cell.html("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
cell.children("table")._bind("click dblclick contextmenu",function(e){
e.stopPropagation();
});
$.data(cell[0],"datagrid.editor",{actions:_176,target:_176.init(cell.find("td"),$.extend({height:opts.editorHeight},_175)),field:_173,type:_174,oldHtml:_177});
}
}
});
_34(_171,_172,true);
};
function _168(_179,_17a){
var opts=$.data(_179,"datagrid").options;
var tr=opts.finder.getTr(_179,_17a);
tr.children("td").each(function(){
var cell=$(this).find("div.datagrid-editable");
if(cell.length){
var ed=$.data(cell[0],"datagrid.editor");
if(ed.actions.destroy){
ed.actions.destroy(ed.target);
}
cell.html(ed.oldHtml);
$.removeData(cell[0],"datagrid.editor");
cell.removeClass("datagrid-editable");
cell.css("width","");
}
});
};
function _15b(_17b,_17c){
var tr=$.data(_17b,"datagrid").options.finder.getTr(_17b,_17c);
if(!tr.hasClass("datagrid-row-editing")){
return true;
}
var vbox=tr.find(".validatebox-text");
vbox.validatebox("validate");
vbox.trigger("mouseleave");
var _17d=tr.find(".validatebox-invalid");
return _17d.length==0;
};
function _17e(_17f,_180){
var _181=$.data(_17f,"datagrid").insertedRows;
var _182=$.data(_17f,"datagrid").deletedRows;
var _183=$.data(_17f,"datagrid").updatedRows;
if(!_180){
var rows=[];
rows=rows.concat(_181);
rows=rows.concat(_182);
rows=rows.concat(_183);
return rows;
}else{
if(_180=="inserted"){
return _181;
}else{
if(_180=="deleted"){
return _182;
}else{
if(_180=="updated"){
return _183;
}
}
}
}
return [];
};
function _184(_185,_186){
var _187=$.data(_185,"datagrid");
var opts=_187.options;
var data=_187.data;
var _188=_187.insertedRows;
var _189=_187.deletedRows;
$(_185).datagrid("cancelEdit",_186);
var row=opts.finder.getRow(_185,_186);
if(_2(_188,row)>=0){
_3(_188,row);
}else{
_189.push(row);
}
_3(_187.selectedRows,opts.idField,row[opts.idField]);
_3(_187.checkedRows,opts.idField,row[opts.idField]);
opts.view.deleteRow.call(opts.view,_185,_186);
if(opts.height=="auto"){
_34(_185);
}
$(_185).datagrid("getPager").pagination("refresh",{total:data.total});
};
function _18a(_18b,_18c){
var data=$.data(_18b,"datagrid").data;
var view=$.data(_18b,"datagrid").options.view;
var _18d=$.data(_18b,"datagrid").insertedRows;
view.insertRow.call(view,_18b,_18c.index,_18c.row);
_18d.push(_18c.row);
$(_18b).datagrid("getPager").pagination("refresh",{total:data.total});
};
function _18e(_18f,row){
var data=$.data(_18f,"datagrid").data;
var view=$.data(_18f,"datagrid").options.view;
var _190=$.data(_18f,"datagrid").insertedRows;
view.insertRow.call(view,_18f,null,row);
_190.push(row);
$(_18f).datagrid("getPager").pagination("refresh",{total:data.total});
};
function _191(_192,_193){
var _194=$.data(_192,"datagrid");
var opts=_194.options;
var row=opts.finder.getRow(_192,_193.index);
var _195=false;
_193.row=_193.row||{};
for(var _196 in _193.row){
if(row[_196]!==_193.row[_196]){
_195=true;
break;
}
}
if(_195){
if(_2(_194.insertedRows,row)==-1){
if(_2(_194.updatedRows,row)==-1){
_194.updatedRows.push(row);
}
}
opts.view.updateRow.call(opts.view,_192,_193.index,_193.row);
}
};
function _197(_198){
var _199=$.data(_198,"datagrid");
var data=_199.data;
var rows=data.rows;
var _19a=[];
for(var i=0;i<rows.length;i++){
_19a.push($.extend({},rows[i]));
}
_199.originalRows=_19a;
_199.updatedRows=[];
_199.insertedRows=[];
_199.deletedRows=[];
};
function _19b(_19c){
var data=$.data(_19c,"datagrid").data;
var ok=true;
for(var i=0,len=data.rows.length;i<len;i++){
if(_15b(_19c,i)){
$(_19c).datagrid("endEdit",i);
}else{
ok=false;
}
}
if(ok){
_197(_19c);
}
};
function _19d(_19e){
var _19f=$.data(_19e,"datagrid");
var opts=_19f.options;
var _1a0=_19f.originalRows;
var _1a1=_19f.insertedRows;
var _1a2=_19f.deletedRows;
var _1a3=_19f.selectedRows;
var _1a4=_19f.checkedRows;
var data=_19f.data;
function _1a5(a){
var ids=[];
for(var i=0;i<a.length;i++){
ids.push(a[i][opts.idField]);
}
return ids;
};
function _1a6(ids,_1a7){
for(var i=0;i<ids.length;i++){
var _1a8=_11e(_19e,ids[i]);
if(_1a8>=0){
(_1a7=="s"?_a7:_a4)(_19e,_1a8,true);
}
}
};
for(var i=0;i<data.rows.length;i++){
$(_19e).datagrid("cancelEdit",i);
}
var _1a9=_1a5(_1a3);
var _1aa=_1a5(_1a4);
_1a3.splice(0,_1a3.length);
_1a4.splice(0,_1a4.length);
data.total+=_1a2.length-_1a1.length;
data.rows=_1a0;
_c2(_19e,data);
_1a6(_1a9,"s");
_1a6(_1aa,"c");
_197(_19e);
};
function _c1(_1ab,_1ac,cb){
var opts=$.data(_1ab,"datagrid").options;
if(_1ac){
opts.queryParams=_1ac;
}
var _1ad=$.extend({},opts.queryParams);
if(opts.pagination){
$.extend(_1ad,{page:opts.pageNumber||1,rows:opts.pageSize});
}
if(opts.sortName&&opts.remoteSort){
$.extend(_1ad,{sort:opts.sortName,order:opts.sortOrder});
}
if(opts.onBeforeLoad.call(_1ab,_1ad)==false){
opts.view.setEmptyMsg(_1ab);
return;
}
$(_1ab).datagrid("loading");
var _1ae=opts.loader.call(_1ab,_1ad,function(data){
$(_1ab).datagrid("loaded");
$(_1ab).datagrid("loadData",data);
if(cb){
cb();
}
},function(){
$(_1ab).datagrid("loaded");
opts.onLoadError.apply(_1ab,arguments);
});
if(_1ae==false){
$(_1ab).datagrid("loaded");
opts.view.setEmptyMsg(_1ab);
}
};
function _1af(_1b0,_1b1){
var opts=$.data(_1b0,"datagrid").options;
_1b1.type=_1b1.type||"body";
_1b1.rowspan=_1b1.rowspan||1;
_1b1.colspan=_1b1.colspan||1;
if(_1b1.rowspan==1&&_1b1.colspan==1){
return;
}
var tr=opts.finder.getTr(_1b0,(_1b1.index!=undefined?_1b1.index:_1b1.id),_1b1.type);
if(!tr.length){
return;
}
var td=tr.find("td[field=\""+_1b1.field+"\"]");
td.attr("rowspan",_1b1.rowspan).attr("colspan",_1b1.colspan);
td.addClass("datagrid-td-merged");
_1b2(td.next(),_1b1.colspan-1);
for(var i=1;i<_1b1.rowspan;i++){
tr=tr.next();
if(!tr.length){
break;
}
_1b2(tr.find("td[field=\""+_1b1.field+"\"]"),_1b1.colspan);
}
_f7(_1b0,td);
function _1b2(td,_1b3){
for(var i=0;i<_1b3;i++){
td.hide();
td=td.next();
}
};
};
$.fn.datagrid=function(_1b4,_1b5){
if(typeof _1b4=="string"){
return $.fn.datagrid.methods[_1b4](this,_1b5);
}
_1b4=_1b4||{};
return this.each(function(){
var _1b6=$.data(this,"datagrid");
var opts;
if(_1b6){
opts=$.extend(_1b6.options,_1b4);
_1b6.options=opts;
}else{
opts=$.extend({},$.extend({},$.fn.datagrid.defaults,{queryParams:{}}),$.fn.datagrid.parseOptions(this),_1b4);
$(this).css("width","").css("height","");
var _1b7=_4e(this,opts.rownumbers);
if(!opts.columns){
opts.columns=_1b7.columns;
}
if(!opts.frozenColumns){
opts.frozenColumns=_1b7.frozenColumns;
}
opts.columns=$.extend(true,[],opts.columns);
opts.frozenColumns=$.extend(true,[],opts.frozenColumns);
opts.view=$.extend({},opts.view);
$.data(this,"datagrid",{options:opts,panel:_1b7.panel,dc:_1b7.dc,ss:null,selectedRows:[],checkedRows:[],data:{total:0,rows:[]},originalRows:[],updatedRows:[],insertedRows:[],deletedRows:[]});
}
_59(this);
_77(this);
_1a(this);
if(opts.data){
$(this).datagrid("loadData",opts.data);
}else{
var data=$.fn.datagrid.parseData(this);
if(data.total>0){
$(this).datagrid("loadData",data);
}else{
$(this).datagrid("autoSizeColumn");
}
}
_c1(this);
});
};
function _1b8(_1b9){
var _1ba={};
$.map(_1b9,function(name){
_1ba[name]=_1bb(name);
});
return _1ba;
function _1bb(name){
function isA(_1bc){
return $.data($(_1bc)[0],name)!=undefined;
};
return {init:function(_1bd,_1be){
var _1bf=$("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(_1bd);
if(_1bf[name]&&name!="text"){
return _1bf[name](_1be);
}else{
return _1bf;
}
},destroy:function(_1c0){
if(isA(_1c0,name)){
$(_1c0)[name]("destroy");
}
},getValue:function(_1c1){
if(isA(_1c1,name)){
var opts=$(_1c1)[name]("options");
if(opts.multiple){
return $(_1c1)[name]("getValues").join(opts.separator);
}else{
return $(_1c1)[name]("getValue");
}
}else{
return $(_1c1).val();
}
},setValue:function(_1c2,_1c3){
if(isA(_1c2,name)){
var opts=$(_1c2)[name]("options");
if(opts.multiple){
if(_1c3){
$(_1c2)[name]("setValues",_1c3.split(opts.separator));
}else{
$(_1c2)[name]("clear");
}
}else{
$(_1c2)[name]("setValue",_1c3);
}
}else{
$(_1c2).val(_1c3);
}
},resize:function(_1c4,_1c5){
if(isA(_1c4,name)){
$(_1c4)[name]("resize",_1c5);
}else{
$(_1c4)._size({width:_1c5,height:$.fn.datagrid.defaults.editorHeight});
}
}};
};
};
var _1c6=$.extend({},_1b8(["text","textbox","passwordbox","filebox","numberbox","numberspinner","combobox","combotree","combogrid","combotreegrid","datebox","datetimebox","timespinner","datetimespinner"]),{textarea:{init:function(_1c7,_1c8){
var _1c9=$("<textarea class=\"datagrid-editable-input\"></textarea>").appendTo(_1c7);
_1c9.css("vertical-align","middle")._outerHeight(_1c8.height);
return _1c9;
},getValue:function(_1ca){
return $(_1ca).val();
},setValue:function(_1cb,_1cc){
$(_1cb).val(_1cc);
},resize:function(_1cd,_1ce){
$(_1cd)._outerWidth(_1ce);
}},checkbox:{init:function(_1cf,_1d0){
var _1d1=$("<input type=\"checkbox\">").appendTo(_1cf);
_1d1.val(_1d0.on);
_1d1.attr("offval",_1d0.off);
return _1d1;
},getValue:function(_1d2){
if($(_1d2).is(":checked")){
return $(_1d2).val();
}else{
return $(_1d2).attr("offval");
}
},setValue:function(_1d3,_1d4){
var _1d5=false;
if($(_1d3).val()==_1d4){
_1d5=true;
}
$(_1d3)._propAttr("checked",_1d5);
}},validatebox:{init:function(_1d6,_1d7){
var _1d8=$("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(_1d6);
_1d8.validatebox(_1d7);
return _1d8;
},destroy:function(_1d9){
$(_1d9).validatebox("destroy");
},getValue:function(_1da){
return $(_1da).val();
},setValue:function(_1db,_1dc){
$(_1db).val(_1dc);
},resize:function(_1dd,_1de){
$(_1dd)._outerWidth(_1de)._outerHeight($.fn.datagrid.defaults.editorHeight);
}}});
$.fn.datagrid.methods={options:function(jq){
var _1df=$.data(jq[0],"datagrid").options;
var _1e0=$.data(jq[0],"datagrid").panel.panel("options");
var opts=$.extend(_1df,{width:_1e0.width,height:_1e0.height,closed:_1e0.closed,collapsed:_1e0.collapsed,minimized:_1e0.minimized,maximized:_1e0.maximized});
return opts;
},setSelectionState:function(jq){
return jq.each(function(){
_116(this);
});
},createStyleSheet:function(jq){
return _7(jq[0]);
},getPanel:function(jq){
return $.data(jq[0],"datagrid").panel;
},getPager:function(jq){
return $.data(jq[0],"datagrid").panel.children("div.datagrid-pager");
},getColumnFields:function(jq,_1e1){
return _75(jq[0],_1e1);
},getColumnOption:function(jq,_1e2){
return _76(jq[0],_1e2);
},resize:function(jq,_1e3){
return jq.each(function(){
_1a(this,_1e3);
});
},load:function(jq,_1e4){
return jq.each(function(){
var opts=$(this).datagrid("options");
if(typeof _1e4=="string"){
opts.url=_1e4;
_1e4=null;
}
opts.pageNumber=1;
var _1e5=$(this).datagrid("getPager");
_1e5.pagination("refresh",{pageNumber:1});
_c1(this,_1e4);
});
},reload:function(jq,_1e6){
return jq.each(function(){
var opts=$(this).datagrid("options");
if(typeof _1e6=="string"){
opts.url=_1e6;
_1e6=null;
}
_c1(this,_1e6);
});
},reloadFooter:function(jq,_1e7){
return jq.each(function(){
var opts=$.data(this,"datagrid").options;
var dc=$.data(this,"datagrid").dc;
if(_1e7){
$.data(this,"datagrid").footer=_1e7;
}
if(opts.showFooter){
opts.view.renderFooter.call(opts.view,this,dc.footer2,false);
opts.view.renderFooter.call(opts.view,this,dc.footer1,true);
if(opts.view.onAfterRender){
opts.view.onAfterRender.call(opts.view,this);
}
$(this).datagrid("fixRowHeight");
}
});
},loading:function(jq){
return jq.each(function(){
var opts=$.data(this,"datagrid").options;
$(this).datagrid("getPager").pagination("loading");
if(opts.loadMsg){
var _1e8=$(this).datagrid("getPanel");
if(!_1e8.children("div.datagrid-mask").length){
$("<div class=\"datagrid-mask\" style=\"display:block\"></div>").appendTo(_1e8);
var msg=$("<div class=\"datagrid-mask-msg\" style=\"display:block;left:50%\"></div>").html(opts.loadMsg).appendTo(_1e8);
msg._outerHeight(40);
msg.css({marginLeft:(-msg.outerWidth()/2),lineHeight:(msg.height()+"px")});
}
}
});
},loaded:function(jq){
return jq.each(function(){
$(this).datagrid("getPager").pagination("loaded");
var _1e9=$(this).datagrid("getPanel");
_1e9.children("div.datagrid-mask-msg").remove();
_1e9.children("div.datagrid-mask").remove();
});
},fitColumns:function(jq){
return jq.each(function(){
_ce(this);
});
},fixColumnSize:function(jq,_1ea){
return jq.each(function(){
_f1(this,_1ea);
});
},fixRowHeight:function(jq,_1eb){
return jq.each(function(){
_34(this,_1eb);
});
},freezeRow:function(jq,_1ec){
return jq.each(function(){
_46(this,_1ec);
});
},autoSizeColumn:function(jq,_1ed){
return jq.each(function(){
_e2(this,_1ed);
});
},loadData:function(jq,data){
return jq.each(function(){
_c2(this,data);
_197(this);
});
},getData:function(jq){
return $.data(jq[0],"datagrid").data;
},getRows:function(jq){
return $.data(jq[0],"datagrid").data.rows;
},getFooterRows:function(jq){
return $.data(jq[0],"datagrid").footer;
},getRowIndex:function(jq,id){
return _11e(jq[0],id);
},getChecked:function(jq){
return _124(jq[0]);
},getSelected:function(jq){
var rows=_121(jq[0]);
return rows.length>0?rows[0]:null;
},getSelections:function(jq){
return _121(jq[0]);
},clearSelections:function(jq){
return jq.each(function(){
var _1ee=$.data(this,"datagrid");
var _1ef=_1ee.selectedRows;
var _1f0=_1ee.checkedRows;
_1ef.splice(0,_1ef.length);
_137(this);
if(_1ee.options.checkOnSelect){
_1f0.splice(0,_1f0.length);
}
});
},clearChecked:function(jq){
return jq.each(function(){
var _1f1=$.data(this,"datagrid");
var _1f2=_1f1.selectedRows;
var _1f3=_1f1.checkedRows;
_1f3.splice(0,_1f3.length);
_8c(this);
if(_1f1.options.selectOnCheck){
_1f2.splice(0,_1f2.length);
}
});
},scrollTo:function(jq,_1f4){
return jq.each(function(){
_127(this,_1f4);
});
},highlightRow:function(jq,_1f5){
return jq.each(function(){
_9e(this,_1f5);
_127(this,_1f5);
});
},selectAll:function(jq){
return jq.each(function(){
_13c(this);
});
},unselectAll:function(jq){
return jq.each(function(){
_137(this);
});
},selectRow:function(jq,_1f6){
return jq.each(function(){
_a7(this,_1f6);
});
},selectRecord:function(jq,id){
return jq.each(function(){
var opts=$.data(this,"datagrid").options;
if(opts.idField){
var _1f7=_11e(this,id);
if(_1f7>=0){
$(this).datagrid("selectRow",_1f7);
}
}
});
},unselectRow:function(jq,_1f8){
return jq.each(function(){
_a8(this,_1f8);
});
},checkRow:function(jq,_1f9){
return jq.each(function(){
_a4(this,_1f9);
});
},uncheckRow:function(jq,_1fa){
return jq.each(function(){
_a5(this,_1fa);
});
},checkAll:function(jq){
return jq.each(function(){
_8b(this);
});
},uncheckAll:function(jq){
return jq.each(function(){
_8c(this);
});
},beginEdit:function(jq,_1fb){
return jq.each(function(){
_156(this,_1fb);
});
},endEdit:function(jq,_1fc){
return jq.each(function(){
_15c(this,_1fc,false);
});
},cancelEdit:function(jq,_1fd){
return jq.each(function(){
_15c(this,_1fd,true);
});
},getEditors:function(jq,_1fe){
return _169(jq[0],_1fe);
},getEditor:function(jq,_1ff){
return _16d(jq[0],_1ff);
},refreshRow:function(jq,_200){
return jq.each(function(){
var opts=$.data(this,"datagrid").options;
opts.view.refreshRow.call(opts.view,this,_200);
});
},validateRow:function(jq,_201){
return _15b(jq[0],_201);
},updateRow:function(jq,_202){
return jq.each(function(){
_191(this,_202);
});
},appendRow:function(jq,row){
return jq.each(function(){
_18e(this,row);
});
},insertRow:function(jq,_203){
return jq.each(function(){
_18a(this,_203);
});
},deleteRow:function(jq,_204){
return jq.each(function(){
_184(this,_204);
});
},getChanges:function(jq,_205){
return _17e(jq[0],_205);
},acceptChanges:function(jq){
return jq.each(function(){
_19b(this);
});
},rejectChanges:function(jq){
return jq.each(function(){
_19d(this);
});
},mergeCells:function(jq,_206){
return jq.each(function(){
_1af(this,_206);
});
},showColumn:function(jq,_207){
return jq.each(function(){
var col=$(this).datagrid("getColumnOption",_207);
if(col.hidden){
col.hidden=false;
$(this).datagrid("getPanel").find("td[field=\""+_207+"\"]").show();
_c3(this,_207,1);
$(this).datagrid("fitColumns");
}
});
},hideColumn:function(jq,_208){
return jq.each(function(){
var col=$(this).datagrid("getColumnOption",_208);
if(!col.hidden){
col.hidden=true;
$(this).datagrid("getPanel").find("td[field=\""+_208+"\"]").hide();
_c3(this,_208,-1);
$(this).datagrid("fitColumns");
}
});
},sort:function(jq,_209){
return jq.each(function(){
_8e(this,_209);
});
},gotoPage:function(jq,_20a){
return jq.each(function(){
var _20b=this;
var page,cb;
if(typeof _20a=="object"){
page=_20a.page;
cb=_20a.callback;
}else{
page=_20a;
}
$(_20b).datagrid("options").pageNumber=page;
$(_20b).datagrid("getPager").pagination("refresh",{pageNumber:page});
_c1(_20b,null,function(){
if(cb){
cb.call(_20b,page);
}
});
});
}};
$.fn.datagrid.parseOptions=function(_20c){
var t=$(_20c);
return $.extend({},$.fn.panel.parseOptions(_20c),$.parser.parseOptions(_20c,["url","toolbar","idField","sortName","sortOrder","pagePosition","resizeHandle",{sharedStyleSheet:"boolean",fitColumns:"boolean",autoRowHeight:"boolean",striped:"boolean",nowrap:"boolean"},{rownumbers:"boolean",singleSelect:"boolean",ctrlSelect:"boolean",checkOnSelect:"boolean",selectOnCheck:"boolean"},{pagination:"boolean",pageSize:"number",pageNumber:"number"},{multiSort:"boolean",remoteSort:"boolean",showHeader:"boolean",showFooter:"boolean"},{scrollbarSize:"number",scrollOnSelect:"boolean"}]),{pageList:(t.attr("pageList")?eval(t.attr("pageList")):undefined),loadMsg:(t.attr("loadMsg")!=undefined?t.attr("loadMsg"):undefined),rowStyler:(t.attr("rowStyler")?eval(t.attr("rowStyler")):undefined)});
};
$.fn.datagrid.parseData=function(_20d){
var t=$(_20d);
var data={total:0,rows:[]};
var _20e=t.datagrid("getColumnFields",true).concat(t.datagrid("getColumnFields",false));
t.find("tbody tr").each(function(){
data.total++;
var row={};
$.extend(row,$.parser.parseOptions(this,["iconCls","state"]));
for(var i=0;i<_20e.length;i++){
row[_20e[i]]=$(this).find("td:eq("+i+")").html();
}
data.rows.push(row);
});
return data;
};
var _20f={render:function(_210,_211,_212){
var rows=$(_210).datagrid("getRows");
$(_211).empty().html(this.renderTable(_210,0,rows,_212));
},renderFooter:function(_213,_214,_215){
var opts=$.data(_213,"datagrid").options;
var rows=$.data(_213,"datagrid").footer||[];
var _216=$(_213).datagrid("getColumnFields",_215);
var _217=["<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<rows.length;i++){
_217.push("<tr class=\"datagrid-row\" datagrid-row-index=\""+i+"\">");
_217.push(this.renderRow.call(this,_213,_216,_215,i,rows[i]));
_217.push("</tr>");
}
_217.push("</tbody></table>");
$(_214).html(_217.join(""));
},renderTable:function(_218,_219,rows,_21a){
var _21b=$.data(_218,"datagrid");
var opts=_21b.options;
if(_21a){
if(!(opts.rownumbers||(opts.frozenColumns&&opts.frozenColumns.length))){
return "";
}
}
var _21c=$(_218).datagrid("getColumnFields",_21a);
var _21d=["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<rows.length;i++){
var row=rows[i];
var css=opts.rowStyler?opts.rowStyler.call(_218,_219,row):"";
var cs=this.getStyleValue(css);
var cls="class=\"datagrid-row "+(_219%2&&opts.striped?"datagrid-row-alt ":" ")+cs.c+"\"";
var _21e=cs.s?"style=\""+cs.s+"\"":"";
var _21f=_21b.rowIdPrefix+"-"+(_21a?1:2)+"-"+_219;
_21d.push("<tr id=\""+_21f+"\" datagrid-row-index=\""+_219+"\" "+cls+" "+_21e+">");
_21d.push(this.renderRow.call(this,_218,_21c,_21a,_219,row));
_21d.push("</tr>");
_219++;
}
_21d.push("</tbody></table>");
return _21d.join("");
},renderRow:function(_220,_221,_222,_223,_224){
var opts=$.data(_220,"datagrid").options;
var cc=[];
if(_222&&opts.rownumbers){
var _225=_223+1;
if(opts.pagination){
_225+=(opts.pageNumber-1)*opts.pageSize;
}
cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">"+_225+"</div></td>");
}
for(var i=0;i<_221.length;i++){
var _226=_221[i];
var col=$(_220).datagrid("getColumnOption",_226);
if(col){
var _227=_224[_226];
var css=col.styler?(col.styler.call(_220,_227,_224,_223)||""):"";
var cs=this.getStyleValue(css);
var cls=cs.c?"class=\""+cs.c+"\"":"";
var _228=col.hidden?"style=\"display:none;"+cs.s+"\"":(cs.s?"style=\""+cs.s+"\"":"");
cc.push("<td field=\""+_226+"\" "+cls+" "+_228+">");
var _228="";
if(!col.checkbox){
if(col.align){
_228+="text-align:"+col.align+";";
}
if(!opts.nowrap){
_228+="white-space:normal;height:auto;";
}else{
if(opts.autoRowHeight){
_228+="height:auto;";
}
}
}
cc.push("<div style=\""+_228+"\" ");
cc.push(col.checkbox?"class=\"datagrid-cell-check\"":"class=\"datagrid-cell "+col.cellClass+"\"");
cc.push(">");
if(col.checkbox){
cc.push("<input type=\"checkbox\" "+(_224.checked?"checked=\"checked\"":""));
cc.push(" name=\""+_226+"\" value=\""+(_227!=undefined?_227:"")+"\">");
}else{
if(col.formatter){
cc.push(col.formatter(_227,_224,_223));
}else{
cc.push(_227);
}
}
cc.push("</div>");
cc.push("</td>");
}
}
return cc.join("");
},getStyleValue:function(css){
var _229="";
var _22a="";
if(typeof css=="string"){
_22a=css;
}else{
if(css){
_229=css["class"]||"";
_22a=css["style"]||"";
}
}
return {c:_229,s:_22a};
},refreshRow:function(_22b,_22c){
this.updateRow.call(this,_22b,_22c,{});
},updateRow:function(_22d,_22e,row){
var opts=$.data(_22d,"datagrid").options;
var _22f=opts.finder.getRow(_22d,_22e);
$.extend(_22f,row);
var cs=_230.call(this,_22e);
var _231=cs.s;
var cls="datagrid-row "+(_22e%2&&opts.striped?"datagrid-row-alt ":" ")+cs.c;
function _230(_232){
var css=opts.rowStyler?opts.rowStyler.call(_22d,_232,_22f):"";
return this.getStyleValue(css);
};
function _233(_234){
var tr=opts.finder.getTr(_22d,_22e,"body",(_234?1:2));
if(!tr.length){
return;
}
var _235=$(_22d).datagrid("getColumnFields",_234);
var _236=tr.find("div.datagrid-cell-check input[type=checkbox]").is(":checked");
tr.html(this.renderRow.call(this,_22d,_235,_234,_22e,_22f));
var _237=(tr.hasClass("datagrid-row-checked")?" datagrid-row-checked":"")+(tr.hasClass("datagrid-row-selected")?" datagrid-row-selected":"");
tr.attr("style",_231).attr("class",cls+_237);
if(_236){
tr.find("div.datagrid-cell-check input[type=checkbox]")._propAttr("checked",true);
}
};
_233.call(this,true);
_233.call(this,false);
$(_22d).datagrid("fixRowHeight",_22e);
},insertRow:function(_238,_239,row){
var _23a=$.data(_238,"datagrid");
var opts=_23a.options;
var dc=_23a.dc;
var data=_23a.data;
if(_239==undefined||_239==null){
_239=data.rows.length;
}
if(_239>data.rows.length){
_239=data.rows.length;
}
function _23b(_23c){
var _23d=_23c?1:2;
for(var i=data.rows.length-1;i>=_239;i--){
var tr=opts.finder.getTr(_238,i,"body",_23d);
tr.attr("datagrid-row-index",i+1);
tr.attr("id",_23a.rowIdPrefix+"-"+_23d+"-"+(i+1));
if(_23c&&opts.rownumbers){
var _23e=i+2;
if(opts.pagination){
_23e+=(opts.pageNumber-1)*opts.pageSize;
}
tr.find("div.datagrid-cell-rownumber").html(_23e);
}
if(opts.striped){
tr.removeClass("datagrid-row-alt").addClass((i+1)%2?"datagrid-row-alt":"");
}
}
};
function _23f(_240){
var _241=_240?1:2;
var _242=$(_238).datagrid("getColumnFields",_240);
var _243=_23a.rowIdPrefix+"-"+_241+"-"+_239;
var tr="<tr id=\""+_243+"\" class=\"datagrid-row\" datagrid-row-index=\""+_239+"\"></tr>";
if(_239>=data.rows.length){
if(data.rows.length){
opts.finder.getTr(_238,"","last",_241).after(tr);
}else{
var cc=_240?dc.body1:dc.body2;
cc.html("<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"+tr+"</tbody></table>");
}
}else{
opts.finder.getTr(_238,_239+1,"body",_241).before(tr);
}
};
_23b.call(this,true);
_23b.call(this,false);
_23f.call(this,true);
_23f.call(this,false);
data.total+=1;
data.rows.splice(_239,0,row);
this.setEmptyMsg(_238);
this.refreshRow.call(this,_238,_239);
},deleteRow:function(_244,_245){
var _246=$.data(_244,"datagrid");
var opts=_246.options;
var data=_246.data;
function _247(_248){
var _249=_248?1:2;
for(var i=_245+1;i<data.rows.length;i++){
var tr=opts.finder.getTr(_244,i,"body",_249);
tr.attr("datagrid-row-index",i-1);
tr.attr("id",_246.rowIdPrefix+"-"+_249+"-"+(i-1));
if(_248&&opts.rownumbers){
var _24a=i;
if(opts.pagination){
_24a+=(opts.pageNumber-1)*opts.pageSize;
}
tr.find("div.datagrid-cell-rownumber").html(_24a);
}
if(opts.striped){
tr.removeClass("datagrid-row-alt").addClass((i-1)%2?"datagrid-row-alt":"");
}
}
};
opts.finder.getTr(_244,_245).remove();
_247.call(this,true);
_247.call(this,false);
data.total-=1;
data.rows.splice(_245,1);
this.setEmptyMsg(_244);
},onBeforeRender:function(_24b,rows){
},onAfterRender:function(_24c){
var _24d=$.data(_24c,"datagrid");
var opts=_24d.options;
if(opts.showFooter){
var _24e=$(_24c).datagrid("getPanel").find("div.datagrid-footer");
_24e.find("div.datagrid-cell-rownumber,div.datagrid-cell-check").css("visibility","hidden");
}
this.setEmptyMsg(_24c);
},setEmptyMsg:function(_24f){
var _250=$.data(_24f,"datagrid");
var opts=_250.options;
var _251=opts.finder.getRows(_24f).length==0;
if(_251){
this.renderEmptyRow(_24f);
}
if(opts.emptyMsg){
_250.dc.view.children(".datagrid-empty").remove();
if(_251){
var h=_250.dc.header2.parent().outerHeight();
var d=$("<div class=\"datagrid-empty\"></div>").appendTo(_250.dc.view);
d.html(opts.emptyMsg).css("top",h+"px");
}
}
},renderEmptyRow:function(_252){
var opts=$(_252).datagrid("options");
var cols=$.map($(_252).datagrid("getColumnFields"),function(_253){
return $(_252).datagrid("getColumnOption",_253);
});
$.map(cols,function(col){
col.formatter1=col.formatter;
col.styler1=col.styler;
col.formatter=col.styler=undefined;
});
var _254=opts.rowStyler;
opts.rowStyler=function(){
};
var _255=$.data(_252,"datagrid").dc.body2;
_255.html(this.renderTable(_252,0,[{}],false));
_255.find("tbody *").css({height:1,borderColor:"transparent",background:"transparent"});
var tr=_255.find(".datagrid-row");
tr.removeClass("datagrid-row").removeAttr("datagrid-row-index");
tr.find(".datagrid-cell,.datagrid-cell-check").empty();
$.map(cols,function(col){
col.formatter=col.formatter1;
col.styler=col.styler1;
col.formatter1=col.styler1=undefined;
});
opts.rowStyler=_254;
}};
$.fn.datagrid.defaults=$.extend({},$.fn.panel.defaults,{sharedStyleSheet:false,frozenColumns:undefined,columns:undefined,fitColumns:false,resizeHandle:"right",resizeEdge:5,autoRowHeight:true,toolbar:null,striped:false,method:"post",nowrap:true,idField:null,url:null,data:null,loadMsg:"Processing, please wait ...",emptyMsg:"",rownumbers:false,singleSelect:false,ctrlSelect:false,selectOnCheck:true,checkOnSelect:true,pagination:false,pagePosition:"bottom",pageNumber:1,pageSize:10,pageList:[10,20,30,40,50],queryParams:{},sortName:null,sortOrder:"asc",multiSort:false,remoteSort:true,showHeader:true,showFooter:false,scrollOnSelect:true,scrollbarSize:18,rownumberWidth:30,editorHeight:31,headerEvents:{mouseover:_84(true),mouseout:_84(false),click:_88,dblclick:_8f,contextmenu:_95},rowEvents:{mouseover:_98(true),mouseout:_98(false),click:_a0,dblclick:_ab,contextmenu:_b0},rowStyler:function(_256,_257){
},loader:function(_258,_259,_25a){
var opts=$(this).datagrid("options");
if(!opts.url){
return false;
}
$.ajax({type:opts.method,url:opts.url,data:_258,dataType:"json",success:function(data){
_259(data);
},error:function(){
_25a.apply(this,arguments);
}});
},loadFilter:function(data){
return data;
},editors:_1c6,finder:{getTr:function(_25b,_25c,type,_25d){
type=type||"body";
_25d=_25d||0;
var _25e=$.data(_25b,"datagrid");
var dc=_25e.dc;
var opts=_25e.options;
if(_25d==0){
var tr1=opts.finder.getTr(_25b,_25c,type,1);
var tr2=opts.finder.getTr(_25b,_25c,type,2);
return tr1.add(tr2);
}else{
if(type=="body"){
var tr=$("#"+_25e.rowIdPrefix+"-"+_25d+"-"+_25c);
if(!tr.length){
tr=(_25d==1?dc.body1:dc.body2).find(">table>tbody>tr[datagrid-row-index="+_25c+"]");
}
return tr;
}else{
if(type=="footer"){
return (_25d==1?dc.footer1:dc.footer2).find(">table>tbody>tr[datagrid-row-index="+_25c+"]");
}else{
if(type=="selected"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr.datagrid-row-selected");
}else{
if(type=="highlight"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr.datagrid-row-over");
}else{
if(type=="checked"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr.datagrid-row-checked");
}else{
if(type=="editing"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr.datagrid-row-editing");
}else{
if(type=="last"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr[datagrid-row-index]:last");
}else{
if(type=="allbody"){
return (_25d==1?dc.body1:dc.body2).find(">table>tbody>tr[datagrid-row-index]");
}else{
if(type=="allfooter"){
return (_25d==1?dc.footer1:dc.footer2).find(">table>tbody>tr[datagrid-row-index]");
}
}
}
}
}
}
}
}
}
}
},getRow:function(_25f,p){
var _260=(typeof p=="object")?p.attr("datagrid-row-index"):p;
return $.data(_25f,"datagrid").data.rows[parseInt(_260)];
},getRows:function(_261){
return $(_261).datagrid("getRows");
}},view:_20f,onBeforeLoad:function(_262){
},onLoadSuccess:function(){
},onLoadError:function(){
},onClickRow:function(_263,_264){
},onDblClickRow:function(_265,_266){
},onClickCell:function(_267,_268,_269){
},onDblClickCell:function(_26a,_26b,_26c){
},onBeforeSortColumn:function(sort,_26d){
},onSortColumn:function(sort,_26e){
},onResizeColumn:function(_26f,_270){
},onBeforeSelect:function(_271,_272){
},onSelect:function(_273,_274){
},onBeforeUnselect:function(_275,_276){
},onUnselect:function(_277,_278){
},onSelectAll:function(rows){
},onUnselectAll:function(rows){
},onBeforeCheck:function(_279,_27a){
},onCheck:function(_27b,_27c){
},onBeforeUncheck:function(_27d,_27e){
},onUncheck:function(_27f,_280){
},onCheckAll:function(rows){
},onUncheckAll:function(rows){
},onBeforeEdit:function(_281,_282){
},onBeginEdit:function(_283,_284){
},onEndEdit:function(_285,_286,_287){
},onAfterEdit:function(_288,_289,_28a){
},onCancelEdit:function(_28b,_28c){
},onHeaderContextMenu:function(e,_28d){
},onRowContextMenu:function(e,_28e,_28f){
}});
})(jQuery);

