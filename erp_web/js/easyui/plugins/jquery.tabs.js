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
function _1(c){
var w=0;
$(c).children().each(function(){
w+=$(this).outerWidth(true);
});
return w;
};
function _2(_3){
var _4=$.data(_3,"tabs").options;
if(!_4.showHeader){
return;
}
var _5=$(_3).children("div.tabs-header");
var _6=_5.children("div.tabs-tool:not(.tabs-tool-hidden)");
var _7=_5.children("div.tabs-scroller-left");
var _8=_5.children("div.tabs-scroller-right");
var _9=_5.children("div.tabs-wrap");
if(_4.tabPosition=="left"||_4.tabPosition=="right"){
if(!_6.length){
return;
}
_6._outerWidth(_5.width());
var _a={left:_4.tabPosition=="left"?"auto":0,right:_4.tabPosition=="left"?0:"auto",top:_4.toolPosition=="top"?0:"auto",bottom:_4.toolPosition=="top"?"auto":0};
var _b={marginTop:_4.toolPosition=="top"?_6.outerHeight():0};
_6.css(_a);
_9.css(_b);
return;
}
var _c=_5.outerHeight();
if(_4.plain){
_c-=_c-_5.height();
}
_6._outerHeight(_c);
var _d=_1(_5.find("ul.tabs"));
var _e=_5.width()-_6._outerWidth();
if(_d>_e){
_7.add(_8).show()._outerHeight(_c);
if(_4.toolPosition=="left"){
_6.css({left:_7.outerWidth(),right:""});
_9.css({marginLeft:_7.outerWidth()+_6._outerWidth(),marginRight:_8._outerWidth(),width:_e-_7.outerWidth()-_8.outerWidth()});
}else{
_6.css({left:"",right:_8.outerWidth()});
_9.css({marginLeft:_7.outerWidth(),marginRight:_8.outerWidth()+_6._outerWidth(),width:_e-_7.outerWidth()-_8.outerWidth()});
}
}else{
_7.add(_8).hide();
if(_4.toolPosition=="left"){
_6.css({left:0,right:""});
_9.css({marginLeft:_6._outerWidth(),marginRight:0,width:_e});
}else{
_6.css({left:"",right:0});
_9.css({marginLeft:0,marginRight:_6._outerWidth(),width:_e});
}
}
};
function _f(_10){
var _11=$.data(_10,"tabs").options;
var _12=$(_10).children("div.tabs-header");
if(_11.tools){
if(typeof _11.tools=="string"){
$(_11.tools).addClass("tabs-tool").appendTo(_12);
$(_11.tools).show();
}else{
_12.children("div.tabs-tool").remove();
var _13=$("<div class=\"tabs-tool\"><table cellspacing=\"0\" cellpadding=\"0\" style=\"height:100%\"><tr></tr></table></div>").appendTo(_12);
var tr=_13.find("tr");
for(var i=0;i<_11.tools.length;i++){
var td=$("<td></td>").appendTo(tr);
var _14=$("<a href=\"javascript:;\"></a>").appendTo(td);
_14[0].onclick=eval(_11.tools[i].handler||function(){
});
_14.linkbutton($.extend({},_11.tools[i],{plain:true}));
}
}
}else{
_12.children("div.tabs-tool").remove();
}
};
function _15(_16,_17){
var _18=$.data(_16,"tabs");
var _19=_18.options;
var cc=$(_16);
if(!_19.doSize){
return;
}
if(_17){
$.extend(_19,{width:_17.width,height:_17.height});
}
cc._size(_19);
var _1a=cc.children("div.tabs-header");
var _1b=cc.children("div.tabs-panels");
var _1c=_1a.find("div.tabs-wrap");
var ul=_1c.find(".tabs");
ul.children("li").removeClass("tabs-first tabs-last");
ul.children("li:first").addClass("tabs-first");
ul.children("li:last").addClass("tabs-last");
if(_19.tabPosition=="left"||_19.tabPosition=="right"){
_1a._outerWidth(_19.showHeader?_19.headerWidth:0);
_1b._outerWidth(cc.width()-_1a.outerWidth());
_1a.add(_1b)._size("height",isNaN(parseInt(_19.height))?"":cc.height());
_1c._outerWidth(_1a.width());
ul._outerWidth(_1c.width()).css("height","");
}else{
_1a.children("div.tabs-scroller-left,div.tabs-scroller-right,div.tabs-tool:not(.tabs-tool-hidden)").css("display",_19.showHeader?"block":"none");
_1a._outerWidth(cc.width()).css("height","");
if(_19.showHeader){
_1a.css("background-color","");
_1c.css("height","");
}else{
_1a.css("background-color","transparent");
_1a._outerHeight(0);
_1c._outerHeight(0);
}
ul._outerHeight(_19.tabHeight).css("width","");
ul._outerHeight(ul.outerHeight()-ul.height()-1+_19.tabHeight).css("width","");
_1b._size("height",isNaN(parseInt(_19.height))?"":(cc.height()-_1a.outerHeight()));
_1b._size("width",cc.width());
}
if(_18.tabs.length){
var d1=ul.outerWidth(true)-ul.width();
var li=ul.children("li:first");
var d2=li.outerWidth(true)-li.width();
var _1d=_1a.width()-_1a.children(".tabs-tool:not(.tabs-tool-hidden)")._outerWidth();
var _1e=Math.floor((_1d-d1-d2*_18.tabs.length)/_18.tabs.length);
$.map(_18.tabs,function(p){
_1f(p,(_19.justified&&$.inArray(_19.tabPosition,["top","bottom"])>=0)?_1e:undefined);
});
if(_19.justified&&$.inArray(_19.tabPosition,["top","bottom"])>=0){
var _20=_1d-d1-_1(ul);
_1f(_18.tabs[_18.tabs.length-1],_1e+_20);
}
}
_2(_16);
function _1f(p,_21){
var _22=p.panel("options");
var p_t=_22.tab.find("a.tabs-inner");
var _21=_21?_21:(parseInt(_22.tabWidth||_19.tabWidth||undefined));
if(_21){
p_t._outerWidth(_21);
}else{
p_t.css("width","");
}
p_t._outerHeight(_19.tabHeight);
p_t.css("lineHeight",p_t.height()+"px");
p_t.find(".easyui-fluid:visible").triggerHandler("_resize");
};
};
function _23(_24){
var _25=$.data(_24,"tabs").options;
var tab=_26(_24);
if(tab){
var _27=$(_24).children("div.tabs-panels");
var _28=_25.width=="auto"?"auto":_27.width();
var _29=_25.height=="auto"?"auto":_27.height();
tab.panel("resize",{width:_28,height:_29});
}
};
function _2a(_2b){
var _2c=$.data(_2b,"tabs").tabs;
var cc=$(_2b).addClass("tabs-container");
var _2d=$("<div class=\"tabs-panels\"></div>").insertBefore(cc);
cc.children("div").each(function(){
_2d[0].appendChild(this);
});
cc[0].appendChild(_2d[0]);
$("<div class=\"tabs-header\">"+"<div class=\"tabs-scroller-left\"></div>"+"<div class=\"tabs-scroller-right\"></div>"+"<div class=\"tabs-wrap\">"+"<ul class=\"tabs\"></ul>"+"</div>"+"</div>").prependTo(_2b);
cc.children("div.tabs-panels").children("div").each(function(i){
var _2e=$.extend({},$.parser.parseOptions(this),{disabled:($(this).attr("disabled")?true:undefined),selected:($(this).attr("selected")?true:undefined)});
_3e(_2b,_2e,$(this));
});
cc.children("div.tabs-header").find(".tabs-scroller-left, .tabs-scroller-right")._bind("mouseenter",function(){
$(this).addClass("tabs-scroller-over");
})._bind("mouseleave",function(){
$(this).removeClass("tabs-scroller-over");
});
cc._bind("_resize",function(e,_2f){
if($(this).hasClass("easyui-fluid")||_2f){
_15(_2b);
_23(_2b);
}
return false;
});
};
function _30(_31){
var _32=$.data(_31,"tabs");
var _33=_32.options;
$(_31).children("div.tabs-header")._unbind()._bind("click",function(e){
if($(e.target).hasClass("tabs-scroller-left")){
$(_31).tabs("scrollBy",-_33.scrollIncrement);
}else{
if($(e.target).hasClass("tabs-scroller-right")){
$(_31).tabs("scrollBy",_33.scrollIncrement);
}else{
var li=$(e.target).closest("li");
if(li.hasClass("tabs-disabled")){
return false;
}
var a=$(e.target).closest("a.tabs-close");
if(a.length){
_5d(_31,_34(li));
}else{
if(li.length){
var _35=_34(li);
var _36=_32.tabs[_35].panel("options");
if(_36.collapsible){
_36.closed?_53(_31,_35):_7b(_31,_35);
}else{
_53(_31,_35);
}
}
}
return false;
}
}
})._bind("contextmenu",function(e){
var li=$(e.target).closest("li");
if(li.hasClass("tabs-disabled")){
return;
}
if(li.length){
_33.onContextMenu.call(_31,e,li.find("span.tabs-title").html(),_34(li));
}
});
function _34(li){
var _37=0;
li.parent().children("li").each(function(i){
if(li[0]==this){
_37=i;
return false;
}
});
return _37;
};
};
function _38(_39){
var _3a=$.data(_39,"tabs").options;
var _3b=$(_39).children("div.tabs-header");
var _3c=$(_39).children("div.tabs-panels");
_3b.removeClass("tabs-header-top tabs-header-bottom tabs-header-left tabs-header-right");
_3c.removeClass("tabs-panels-top tabs-panels-bottom tabs-panels-left tabs-panels-right");
if(_3a.tabPosition=="top"){
_3b.insertBefore(_3c);
}else{
if(_3a.tabPosition=="bottom"){
_3b.insertAfter(_3c);
_3b.addClass("tabs-header-bottom");
_3c.addClass("tabs-panels-top");
}else{
if(_3a.tabPosition=="left"){
_3b.addClass("tabs-header-left");
_3c.addClass("tabs-panels-right");
}else{
if(_3a.tabPosition=="right"){
_3b.addClass("tabs-header-right");
_3c.addClass("tabs-panels-left");
}
}
}
}
if(_3a.plain==true){
_3b.addClass("tabs-header-plain");
}else{
_3b.removeClass("tabs-header-plain");
}
_3b.removeClass("tabs-header-narrow").addClass(_3a.narrow?"tabs-header-narrow":"");
var _3d=_3b.find(".tabs");
_3d.removeClass("tabs-pill").addClass(_3a.pill?"tabs-pill":"");
_3d.removeClass("tabs-narrow").addClass(_3a.narrow?"tabs-narrow":"");
_3d.removeClass("tabs-justified").addClass(_3a.justified?"tabs-justified":"");
if(_3a.border==true){
_3b.removeClass("tabs-header-noborder");
_3c.removeClass("tabs-panels-noborder");
}else{
_3b.addClass("tabs-header-noborder");
_3c.addClass("tabs-panels-noborder");
}
_3a.doSize=true;
};
function _3e(_3f,_40,pp){
_40=_40||{};
var _41=$.data(_3f,"tabs");
var _42=_41.tabs;
if(_40.index==undefined||_40.index>_42.length){
_40.index=_42.length;
}
if(_40.index<0){
_40.index=0;
}
var ul=$(_3f).children("div.tabs-header").find("ul.tabs");
var _43=$(_3f).children("div.tabs-panels");
var tab=$("<li>"+"<a href=\"javascript:;\" class=\"tabs-inner\">"+"<span class=\"tabs-title\"></span>"+"<span class=\"tabs-icon\"></span>"+"</a>"+"</li>");
if(!pp){
pp=$("<div></div>");
}
if(_40.index>=_42.length){
tab.appendTo(ul);
pp.appendTo(_43);
_42.push(pp);
}else{
tab.insertBefore(ul.children("li:eq("+_40.index+")"));
pp.insertBefore(_43.children("div.panel:eq("+_40.index+")"));
_42.splice(_40.index,0,pp);
}
pp.panel($.extend({},_40,{tab:tab,border:false,noheader:true,closed:true,doSize:false,iconCls:(_40.icon?_40.icon:undefined),onLoad:function(){
if(_40.onLoad){
_40.onLoad.apply(this,arguments);
}
_41.options.onLoad.call(_3f,$(this));
},onBeforeOpen:function(){
if(_40.onBeforeOpen){
if(_40.onBeforeOpen.call(this)==false){
return false;
}
}
var p=$(_3f).tabs("getSelected");
if(p){
if(p[0]!=this){
$(_3f).tabs("unselect",_4d(_3f,p));
p=$(_3f).tabs("getSelected");
if(p){
return false;
}
}else{
_23(_3f);
return false;
}
}
var _44=$(this).panel("options");
_44.tab.addClass("tabs-selected");
var _45=$(_3f).find(">div.tabs-header>div.tabs-wrap");
var _46=_44.tab.position().left;
var _47=_46+_44.tab.outerWidth();
if(_46<0||_47>_45.width()){
var _48=_46-(_45.width()-_44.tab.width())/2;
$(_3f).tabs("scrollBy",_48);
}else{
$(_3f).tabs("scrollBy",0);
}
var _49=$(this).panel("panel");
_49.css("display","block");
_23(_3f);
_49.css("display","none");
},onOpen:function(){
if(_40.onOpen){
_40.onOpen.call(this);
}
var _4a=$(this).panel("options");
var _4b=_4d(_3f,this);
_41.selectHis.push(_4b);
_41.options.onSelect.call(_3f,_4a.title,_4b);
},onBeforeClose:function(){
if(_40.onBeforeClose){
if(_40.onBeforeClose.call(this)==false){
return false;
}
}
$(this).panel("options").tab.removeClass("tabs-selected");
},onClose:function(){
if(_40.onClose){
_40.onClose.call(this);
}
var _4c=$(this).panel("options");
_41.options.onUnselect.call(_3f,_4c.title,_4d(_3f,this));
}}));
$(_3f).tabs("update",{tab:pp,options:pp.panel("options"),type:"header"});
};
function _4e(_4f,_50){
var _51=$.data(_4f,"tabs");
var _52=_51.options;
if(_50.selected==undefined){
_50.selected=true;
}
_3e(_4f,_50);
_52.onAdd.call(_4f,_50.title,_50.index);
if(_50.selected){
_53(_4f,_50.index);
}
};
function _54(_55,_56){
_56.type=_56.type||"all";
var _57=$.data(_55,"tabs").selectHis;
var pp=_56.tab;
var _58=pp.panel("options");
var _59=_58.title;
$.extend(_58,_56.options,{iconCls:(_56.options.icon?_56.options.icon:undefined)});
if(_56.type=="all"||_56.type=="body"){
pp.panel();
}
if(_56.type=="all"||_56.type=="header"){
var tab=_58.tab;
if(_58.header){
tab.find(".tabs-inner").html($(_58.header));
}else{
var _5a=tab.find("span.tabs-title");
var _5b=tab.find("span.tabs-icon");
_5a.html(_58.title);
_5b.attr("class","tabs-icon");
tab.find("a.tabs-close").remove();
if(_58.closable){
_5a.addClass("tabs-closable");
$("<a href=\"javascript:;\" class=\"tabs-close\"></a>").appendTo(tab);
}else{
_5a.removeClass("tabs-closable");
}
if(_58.iconCls){
_5a.addClass("tabs-with-icon");
_5b.addClass(_58.iconCls);
}else{
_5a.removeClass("tabs-with-icon");
}
if(_58.tools){
var _5c=tab.find("span.tabs-p-tool");
if(!_5c.length){
var _5c=$("<span class=\"tabs-p-tool\"></span>").insertAfter(tab.find("a.tabs-inner"));
}
if($.isArray(_58.tools)){
_5c.empty();
for(var i=0;i<_58.tools.length;i++){
var t=$("<a href=\"javascript:;\"></a>").appendTo(_5c);
t.addClass(_58.tools[i].iconCls);
if(_58.tools[i].handler){
t._bind("click",{handler:_58.tools[i].handler},function(e){
if($(this).parents("li").hasClass("tabs-disabled")){
return;
}
e.data.handler.call(this);
});
}
}
}else{
$(_58.tools).children().appendTo(_5c);
}
var pr=_5c.children().length*12;
if(_58.closable){
pr+=8;
_5c.css("right","");
}else{
pr-=3;
_5c.css("right","5px");
}
_5a.css("padding-right",pr+"px");
}else{
tab.find("span.tabs-p-tool").remove();
_5a.css("padding-right","");
}
}
}
if(_58.disabled){
_58.tab.addClass("tabs-disabled");
}else{
_58.tab.removeClass("tabs-disabled");
}
_15(_55);
$.data(_55,"tabs").options.onUpdate.call(_55,_58.title,_4d(_55,pp));
};
function _5d(_5e,_5f){
var _60=$.data(_5e,"tabs");
var _61=_60.options;
var _62=_60.tabs;
var _63=_60.selectHis;
if(!_64(_5e,_5f)){
return;
}
var tab=_65(_5e,_5f);
var _66=tab.panel("options").title;
var _67=_4d(_5e,tab);
if(_61.onBeforeClose.call(_5e,_66,_67)==false){
return;
}
var tab=_65(_5e,_5f,true);
tab.panel("options").tab.remove();
tab.panel("destroy");
_61.onClose.call(_5e,_66,_67);
_15(_5e);
var his=[];
for(var i=0;i<_63.length;i++){
var _68=_63[i];
if(_68!=_67){
his.push(_68>_67?_68-1:_68);
}
}
_60.selectHis=his;
var _69=$(_5e).tabs("getSelected");
if(!_69&&his.length){
_67=_60.selectHis.pop();
$(_5e).tabs("select",_67);
}
};
function _65(_6a,_6b,_6c){
var _6d=$.data(_6a,"tabs").tabs;
var tab=null;
if(typeof _6b=="number"){
if(_6b>=0&&_6b<_6d.length){
tab=_6d[_6b];
if(_6c){
_6d.splice(_6b,1);
}
}
}else{
var tmp=$("<span></span>");
for(var i=0;i<_6d.length;i++){
var p=_6d[i];
tmp.html(p.panel("options").title);
var _6e=tmp.text();
tmp.html(_6b);
_6b=tmp.text();
if(_6e==_6b){
tab=p;
if(_6c){
_6d.splice(i,1);
}
break;
}
}
tmp.remove();
}
return tab;
};
function _4d(_6f,tab){
var _70=$.data(_6f,"tabs").tabs;
for(var i=0;i<_70.length;i++){
if(_70[i][0]==$(tab)[0]){
return i;
}
}
return -1;
};
function _26(_71){
var _72=$.data(_71,"tabs").tabs;
for(var i=0;i<_72.length;i++){
var tab=_72[i];
if(tab.panel("options").tab.hasClass("tabs-selected")){
return tab;
}
}
return null;
};
function _73(_74){
var _75=$.data(_74,"tabs");
var _76=_75.tabs;
for(var i=0;i<_76.length;i++){
var _77=_76[i].panel("options");
if(_77.selected&&!_77.disabled){
_53(_74,i);
return;
}
}
_53(_74,_75.options.selected);
};
function _53(_78,_79){
var p=_65(_78,_79);
if(p&&!p.is(":visible")){
_7a(_78);
if(!p.panel("options").disabled){
p.panel("open");
}
}
};
function _7b(_7c,_7d){
var p=_65(_7c,_7d);
if(p&&p.is(":visible")){
_7a(_7c);
p.panel("close");
}
};
function _7a(_7e){
$(_7e).children("div.tabs-panels").each(function(){
$(this).stop(true,true);
});
};
function _64(_7f,_80){
return _65(_7f,_80)!=null;
};
function _81(_82,_83){
var _84=$.data(_82,"tabs").options;
_84.showHeader=_83;
$(_82).tabs("resize");
};
function _85(_86,_87){
var _88=$(_86).find(">.tabs-header>.tabs-tool");
if(_87){
_88.removeClass("tabs-tool-hidden").show();
}else{
_88.addClass("tabs-tool-hidden").hide();
}
$(_86).tabs("resize").tabs("scrollBy",0);
};
$.fn.tabs=function(_89,_8a){
if(typeof _89=="string"){
return $.fn.tabs.methods[_89](this,_8a);
}
_89=_89||{};
return this.each(function(){
var _8b=$.data(this,"tabs");
if(_8b){
$.extend(_8b.options,_89);
}else{
$.data(this,"tabs",{options:$.extend({},$.fn.tabs.defaults,$.fn.tabs.parseOptions(this),_89),tabs:[],selectHis:[]});
_2a(this);
}
_f(this);
_38(this);
_15(this);
_30(this);
_73(this);
});
};
$.fn.tabs.methods={options:function(jq){
var cc=jq[0];
var _8c=$.data(cc,"tabs").options;
var s=_26(cc);
_8c.selected=s?_4d(cc,s):-1;
return _8c;
},tabs:function(jq){
return $.data(jq[0],"tabs").tabs;
},resize:function(jq,_8d){
return jq.each(function(){
_15(this,_8d);
_23(this);
});
},add:function(jq,_8e){
return jq.each(function(){
_4e(this,_8e);
});
},close:function(jq,_8f){
return jq.each(function(){
_5d(this,_8f);
});
},getTab:function(jq,_90){
return _65(jq[0],_90);
},getTabIndex:function(jq,tab){
return _4d(jq[0],tab);
},getSelected:function(jq){
return _26(jq[0]);
},select:function(jq,_91){
return jq.each(function(){
_53(this,_91);
});
},unselect:function(jq,_92){
return jq.each(function(){
_7b(this,_92);
});
},exists:function(jq,_93){
return _64(jq[0],_93);
},update:function(jq,_94){
return jq.each(function(){
_54(this,_94);
});
},enableTab:function(jq,_95){
return jq.each(function(){
var _96=$(this).tabs("getTab",_95).panel("options");
_96.tab.removeClass("tabs-disabled");
_96.disabled=false;
});
},disableTab:function(jq,_97){
return jq.each(function(){
var _98=$(this).tabs("getTab",_97).panel("options");
_98.tab.addClass("tabs-disabled");
_98.disabled=true;
});
},showHeader:function(jq){
return jq.each(function(){
_81(this,true);
});
},hideHeader:function(jq){
return jq.each(function(){
_81(this,false);
});
},showTool:function(jq){
return jq.each(function(){
_85(this,true);
});
},hideTool:function(jq){
return jq.each(function(){
_85(this,false);
});
},scrollBy:function(jq,_99){
return jq.each(function(){
var _9a=$(this).tabs("options");
var _9b=$(this).find(">div.tabs-header>div.tabs-wrap");
var pos=Math.min(_9b._scrollLeft()+_99,_9c());
_9b.animate({scrollLeft:pos},_9a.scrollDuration);
function _9c(){
var w=0;
var ul=_9b.children("ul");
ul.children("li").each(function(){
w+=$(this).outerWidth(true);
});
return w-_9b.width()+(ul.outerWidth()-ul.width());
};
});
}};
$.fn.tabs.parseOptions=function(_9d){
return $.extend({},$.parser.parseOptions(_9d,["tools","toolPosition","tabPosition",{fit:"boolean",border:"boolean",plain:"boolean"},{headerWidth:"number",tabWidth:"number",tabHeight:"number",selected:"number"},{showHeader:"boolean",justified:"boolean",narrow:"boolean",pill:"boolean"}]));
};
$.fn.tabs.defaults={width:"auto",height:"auto",headerWidth:150,tabWidth:"auto",tabHeight:32,selected:0,showHeader:true,plain:false,fit:false,border:true,justified:false,narrow:false,pill:false,tools:null,toolPosition:"right",tabPosition:"top",scrollIncrement:100,scrollDuration:400,onLoad:function(_9e){
},onSelect:function(_9f,_a0){
},onUnselect:function(_a1,_a2){
},onBeforeClose:function(_a3,_a4){
},onClose:function(_a5,_a6){
},onAdd:function(_a7,_a8){
},onUpdate:function(_a9,_aa){
},onContextMenu:function(e,_ab,_ac){
}};
})(jQuery);

