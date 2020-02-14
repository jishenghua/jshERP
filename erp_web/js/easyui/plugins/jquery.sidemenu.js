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
var _1=1;
function _2(_3){
$(_3).addClass("sidemenu");
};
function _4(_5,_6){
var _7=$(_5).sidemenu("options");
if(_6){
$.extend(_7,{width:_6.width,height:_6.height});
}
$(_5)._size(_7);
$(_5).find(".accordion").accordion("resize");
};
function _8(_9,_a,_b){
var _c=$(_9).sidemenu("options");
var tt=$("<ul class=\"sidemenu-tree\"></ul>").appendTo(_a);
tt.tree({data:_b,animate:_c.animate,onBeforeSelect:function(_d){
if(_d.children){
return false;
}
},onSelect:function(_e){
_12(_9,_e.id,true);
},onExpand:function(_f){
_25(_9,_f);
},onCollapse:function(_10){
_25(_9,_10);
},onClick:function(_11){
if(_11.children){
if(_11.state=="open"){
$(_11.target).addClass("tree-node-nonleaf-collapsed");
}else{
$(_11.target).removeClass("tree-node-nonleaf-collapsed");
}
$(this).tree("toggle",_11.target);
}
}});
tt._unbind(".sidemenu")._bind("mouseleave.sidemenu",function(){
$(_a).trigger("mouseleave");
});
_12(_9,_c.selectedItemId);
};
function _13(_14,_15,_16){
var _17=$(_14).sidemenu("options");
$(_15).tooltip({content:$("<div></div>"),position:_17.floatMenuPosition,valign:"top",data:_16,onUpdate:function(_18){
var _19=$(this).tooltip("options");
var _1a=_19.data;
_18.accordion({width:_17.floatMenuWidth,multiple:false}).accordion("add",{title:_1a.text,collapsed:false,collapsible:false});
_8(_14,_18.accordion("panels")[0],_1a.children);
},onShow:function(){
var t=$(this);
var tip=t.tooltip("tip").addClass("sidemenu-tooltip");
tip.children(".tooltip-content").addClass("sidemenu");
tip.find(".accordion").accordion("resize");
tip.add(tip.find("ul.tree"))._unbind(".sidemenu")._bind("mouseover.sidemenu",function(){
t.tooltip("show");
})._bind("mouseleave.sidemenu",function(){
t.tooltip("hide");
});
t.tooltip("reposition");
},onPosition:function(_1b,top){
var tip=$(this).tooltip("tip");
if(!_17.collapsed){
tip.css({left:-999999});
}else{
if(top+tip.outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
top=$(window)._outerHeight()+$(document).scrollTop()-tip.outerHeight();
tip.css("top",top);
}
}
}});
};
function _1c(_1d,_1e){
$(_1d).find(".sidemenu-tree").each(function(){
_1e($(this));
});
$(_1d).find(".tooltip-f").each(function(){
var tip=$(this).tooltip("tip");
if(tip){
tip.find(".sidemenu-tree").each(function(){
_1e($(this));
});
$(this).tooltip("reposition");
}
});
};
function _12(_1f,_20,_21){
var _22=null;
var _23=$(_1f).sidemenu("options");
_1c(_1f,function(t){
t.find("div.tree-node-selected").removeClass("tree-node-selected");
var _24=t.tree("find",_20);
if(_24){
$(_24.target).addClass("tree-node-selected");
_23.selectedItemId=_24.id;
t.trigger("mouseleave.sidemenu");
_22=_24;
}
});
if(_21&&_22){
_23.onSelect.call(_1f,_22);
}
};
function _25(_26,_27){
_1c(_26,function(t){
var _28=t.tree("find",_27.id);
if(_28){
var _29=t.tree("options");
var _2a=_29.animate;
_29.animate=false;
t.tree(_27.state=="open"?"expand":"collapse",_28.target);
_29.animate=_2a;
}
});
};
function _2b(_2c){
var _2d=$(_2c).sidemenu("options");
$(_2c).empty();
if(_2d.data){
$.easyui.forEach(_2d.data,true,function(_2e){
if(!_2e.id){
_2e.id="_easyui_sidemenu_"+(_1++);
}
if(!_2e.iconCls){
_2e.iconCls="sidemenu-default-icon";
}
if(_2e.children){
_2e.nodeCls="tree-node-nonleaf";
if(!_2e.state){
_2e.state="closed";
}
if(_2e.state=="open"){
_2e.nodeCls="tree-node-nonleaf";
}else{
_2e.nodeCls="tree-node-nonleaf tree-node-nonleaf-collapsed";
}
}
});
var acc=$("<div></div>").appendTo(_2c);
acc.accordion({fit:_2d.height=="auto"?false:true,border:_2d.border,multiple:_2d.multiple});
var _2f=_2d.data;
for(var i=0;i<_2f.length;i++){
acc.accordion("add",{title:_2f[i].text,selected:_2f[i].state=="open",iconCls:_2f[i].iconCls,onBeforeExpand:function(){
return !_2d.collapsed;
}});
var ap=acc.accordion("panels")[i];
_8(_2c,ap,_2f[i].children);
_13(_2c,ap.panel("header"),_2f[i]);
}
}
};
function _30(_31,_32){
var _33=$(_31).sidemenu("options");
_33.collapsed=_32;
var acc=$(_31).find(".accordion");
var _34=acc.accordion("panels");
acc.accordion("options").animate=false;
if(_33.collapsed){
$(_31).addClass("sidemenu-collapsed");
for(var i=0;i<_34.length;i++){
var _35=_34[i];
if(_35.panel("options").collapsed){
_33.data[i].state="closed";
}else{
_33.data[i].state="open";
acc.accordion("unselect",i);
}
var _36=_35.panel("header");
_36.find(".panel-title").html("");
_36.find(".panel-tool").hide();
}
}else{
$(_31).removeClass("sidemenu-collapsed");
for(var i=0;i<_34.length;i++){
var _35=_34[i];
if(_33.data[i].state=="open"){
acc.accordion("select",i);
}
var _36=_35.panel("header");
_36.find(".panel-title").html(_35.panel("options").title);
_36.find(".panel-tool").show();
}
}
acc.accordion("options").animate=_33.animate;
};
function _37(_38){
$(_38).find(".tooltip-f").each(function(){
$(this).tooltip("destroy");
});
$(_38).remove();
};
$.fn.sidemenu=function(_39,_3a){
if(typeof _39=="string"){
var _3b=$.fn.sidemenu.methods[_39];
return _3b(this,_3a);
}
_39=_39||{};
return this.each(function(){
var _3c=$.data(this,"sidemenu");
if(_3c){
$.extend(_3c.options,_39);
}else{
_3c=$.data(this,"sidemenu",{options:$.extend({},$.fn.sidemenu.defaults,$.fn.sidemenu.parseOptions(this),_39)});
_2(this);
}
_4(this);
_2b(this);
_30(this,_3c.options.collapsed);
});
};
$.fn.sidemenu.methods={options:function(jq){
return jq.data("sidemenu").options;
},resize:function(jq,_3d){
return jq.each(function(){
_4(this,_3d);
});
},collapse:function(jq){
return jq.each(function(){
_30(this,true);
});
},expand:function(jq){
return jq.each(function(){
_30(this,false);
});
},destroy:function(jq){
return jq.each(function(){
_37(this);
});
}};
$.fn.sidemenu.parseOptions=function(_3e){
var t=$(_3e);
return $.extend({},$.parser.parseOptions(_3e,["width","height"]));
};
$.fn.sidemenu.defaults={width:200,height:"auto",border:true,animate:true,multiple:true,collapsed:false,data:null,floatMenuWidth:200,floatMenuPosition:"right",onSelect:function(_3f){
}};
})(jQuery);

