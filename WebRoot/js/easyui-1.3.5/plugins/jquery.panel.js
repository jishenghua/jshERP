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
$.fn._remove=function(){
return this.each(function(){
$(this).remove();
try{
this.outerHTML="";
}
catch(err){
}
});
};
function _1(_2){
_2._remove();
};
function _3(_4,_5){
var _6=$.data(_4,"panel").options;
var _7=$.data(_4,"panel").panel;
var _8=_7.children("div.panel-header");
var _9=_7.children("div.panel-body");
if(_5){
$.extend(_6,{width:_5.width,height:_5.height,left:_5.left,top:_5.top});
}
_6.fit?$.extend(_6,_7._fit()):_7._fit(false);
_7.css({left:_6.left,top:_6.top});
if(!isNaN(_6.width)){
_7._outerWidth(_6.width);
}else{
_7.width("auto");
}
_8.add(_9)._outerWidth(_7.width());
if(!isNaN(_6.height)){
_7._outerHeight(_6.height);
_9._outerHeight(_7.height()-_8._outerHeight());
}else{
_9.height("auto");
}
_7.css("height","");
_6.onResize.apply(_4,[_6.width,_6.height]);
$(_4).find(">div,>form>div").triggerHandler("_resize");
};
function _a(_b,_c){
var _d=$.data(_b,"panel").options;
var _e=$.data(_b,"panel").panel;
if(_c){
if(_c.left!=null){
_d.left=_c.left;
}
if(_c.top!=null){
_d.top=_c.top;
}
}
_e.css({left:_d.left,top:_d.top});
_d.onMove.apply(_b,[_d.left,_d.top]);
};
function _f(_10){
$(_10).addClass("panel-body");
var _11=$("<div class=\"panel\"></div>").insertBefore(_10);
_11[0].appendChild(_10);
_11.bind("_resize",function(){
var _12=$.data(_10,"panel").options;
if(_12.fit==true){
_3(_10);
}
return false;
});
return _11;
};
function _13(_14){
var _15=$.data(_14,"panel").options;
var _16=$.data(_14,"panel").panel;
if(_15.tools&&typeof _15.tools=="string"){
_16.find(">div.panel-header>div.panel-tool .panel-tool-a").appendTo(_15.tools);
}
_1(_16.children("div.panel-header"));
if(_15.title&&!_15.noheader){
var _17=$("<div class=\"panel-header\"><div class=\"panel-title\">"+_15.title+"</div></div>").prependTo(_16);
if(_15.iconCls){
_17.find(".panel-title").addClass("panel-with-icon");
$("<div class=\"panel-icon\"></div>").addClass(_15.iconCls).appendTo(_17);
}
var _18=$("<div class=\"panel-tool\"></div>").appendTo(_17);
_18.bind("click",function(e){
e.stopPropagation();
});
if(_15.tools){
if($.isArray(_15.tools)){
for(var i=0;i<_15.tools.length;i++){
var t=$("<a href=\"javascript:void(0)\"></a>").addClass(_15.tools[i].iconCls).appendTo(_18);
if(_15.tools[i].handler){
t.bind("click",eval(_15.tools[i].handler));
}
}
}else{
$(_15.tools).children().each(function(){
$(this).addClass($(this).attr("iconCls")).addClass("panel-tool-a").appendTo(_18);
});
}
}
if(_15.collapsible){
$("<a class=\"panel-tool-collapse\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
if(_15.collapsed==true){
_3c(_14,true);
}else{
_2c(_14,true);
}
return false;
});
}
if(_15.minimizable){
$("<a class=\"panel-tool-min\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
_47(_14);
return false;
});
}
if(_15.maximizable){
$("<a class=\"panel-tool-max\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
if(_15.maximized==true){
_4b(_14);
}else{
_2b(_14);
}
return false;
});
}
if(_15.closable){
$("<a class=\"panel-tool-close\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
_19(_14);
return false;
});
}
_16.children("div.panel-body").removeClass("panel-body-noheader");
}else{
_16.children("div.panel-body").addClass("panel-body-noheader");
}
};
function _1a(_1b){
var _1c=$.data(_1b,"panel");
var _1d=_1c.options;
if(_1d.href){
if(!_1c.isLoaded||!_1d.cache){
if(_1d.onBeforeLoad.call(_1b)==false){
return;
}
_1c.isLoaded=false;
_1e(_1b);
if(_1d.loadingMessage){
$(_1b).html($("<div class=\"panel-loading\"></div>").html(_1d.loadingMessage));
}
$.ajax({url:_1d.href,cache:false,dataType:"html",success:function(_1f){
_20(_1d.extractor.call(_1b,_1f));
_1d.onLoad.apply(_1b,arguments);
_1c.isLoaded=true;
}});
}
}else{
if(_1d.content){
if(!_1c.isLoaded){
_1e(_1b);
_20(_1d.content);
_1c.isLoaded=true;
}
}
}
function _20(_21){
$(_1b).html(_21);
if($.parser){
$.parser.parse($(_1b));
}
};
};
function _1e(_22){
var t=$(_22);
t.find(".combo-f").each(function(){
$(this).combo("destroy");
});
t.find(".m-btn").each(function(){
$(this).menubutton("destroy");
});
t.find(".s-btn").each(function(){
$(this).splitbutton("destroy");
});
t.find(".tooltip-f").each(function(){
$(this).tooltip("destroy");
});
};
function _23(_24){
$(_24).find("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible").each(function(){
$(this).triggerHandler("_resize",[true]);
});
};
function _25(_26,_27){
var _28=$.data(_26,"panel").options;
var _29=$.data(_26,"panel").panel;
if(_27!=true){
if(_28.onBeforeOpen.call(_26)==false){
return;
}
}
_29.show();
_28.closed=false;
_28.minimized=false;
var _2a=_29.children("div.panel-header").find("a.panel-tool-restore");
if(_2a.length){
_28.maximized=true;
}
_28.onOpen.call(_26);
if(_28.maximized==true){
_28.maximized=false;
_2b(_26);
}
if(_28.collapsed==true){
_28.collapsed=false;
_2c(_26);
}
if(!_28.collapsed){
_1a(_26);
_23(_26);
}
};
function _19(_2d,_2e){
var _2f=$.data(_2d,"panel").options;
var _30=$.data(_2d,"panel").panel;
if(_2e!=true){
if(_2f.onBeforeClose.call(_2d)==false){
return;
}
}
_30._fit(false);
_30.hide();
_2f.closed=true;
_2f.onClose.call(_2d);
};
function _31(_32,_33){
var _34=$.data(_32,"panel").options;
var _35=$.data(_32,"panel").panel;
if(_33!=true){
if(_34.onBeforeDestroy.call(_32)==false){
return;
}
}
_1e(_32);
_1(_35);
_34.onDestroy.call(_32);
};
function _2c(_36,_37){
var _38=$.data(_36,"panel").options;
var _39=$.data(_36,"panel").panel;
var _3a=_39.children("div.panel-body");
var _3b=_39.children("div.panel-header").find("a.panel-tool-collapse");
if(_38.collapsed==true){
return;
}
_3a.stop(true,true);
if(_38.onBeforeCollapse.call(_36)==false){
return;
}
_3b.addClass("panel-tool-expand");
if(_37==true){
_3a.slideUp("normal",function(){
_38.collapsed=true;
_38.onCollapse.call(_36);
});
}else{
_3a.hide();
_38.collapsed=true;
_38.onCollapse.call(_36);
}
};
function _3c(_3d,_3e){
var _3f=$.data(_3d,"panel").options;
var _40=$.data(_3d,"panel").panel;
var _41=_40.children("div.panel-body");
var _42=_40.children("div.panel-header").find("a.panel-tool-collapse");
if(_3f.collapsed==false){
return;
}
_41.stop(true,true);
if(_3f.onBeforeExpand.call(_3d)==false){
return;
}
_42.removeClass("panel-tool-expand");
if(_3e==true){
_41.slideDown("normal",function(){
_3f.collapsed=false;
_3f.onExpand.call(_3d);
_1a(_3d);
_23(_3d);
});
}else{
_41.show();
_3f.collapsed=false;
_3f.onExpand.call(_3d);
_1a(_3d);
_23(_3d);
}
};
function _2b(_43){
var _44=$.data(_43,"panel").options;
var _45=$.data(_43,"panel").panel;
var _46=_45.children("div.panel-header").find("a.panel-tool-max");
if(_44.maximized==true){
return;
}
_46.addClass("panel-tool-restore");
if(!$.data(_43,"panel").original){
$.data(_43,"panel").original={width:_44.width,height:_44.height,left:_44.left,top:_44.top,fit:_44.fit};
}
_44.left=0;
_44.top=0;
_44.fit=true;
_3(_43);
_44.minimized=false;
_44.maximized=true;
_44.onMaximize.call(_43);
};
function _47(_48){
var _49=$.data(_48,"panel").options;
var _4a=$.data(_48,"panel").panel;
_4a._fit(false);
_4a.hide();
_49.minimized=true;
_49.maximized=false;
_49.onMinimize.call(_48);
};
function _4b(_4c){
var _4d=$.data(_4c,"panel").options;
var _4e=$.data(_4c,"panel").panel;
var _4f=_4e.children("div.panel-header").find("a.panel-tool-max");
if(_4d.maximized==false){
return;
}
_4e.show();
_4f.removeClass("panel-tool-restore");
$.extend(_4d,$.data(_4c,"panel").original);
_3(_4c);
_4d.minimized=false;
_4d.maximized=false;
$.data(_4c,"panel").original=null;
_4d.onRestore.call(_4c);
};
function _50(_51){
var _52=$.data(_51,"panel").options;
var _53=$.data(_51,"panel").panel;
var _54=$(_51).panel("header");
var _55=$(_51).panel("body");
_53.css(_52.style);
_53.addClass(_52.cls);
if(_52.border){
_54.removeClass("panel-header-noborder");
_55.removeClass("panel-body-noborder");
}else{
_54.addClass("panel-header-noborder");
_55.addClass("panel-body-noborder");
}
_54.addClass(_52.headerCls);
_55.addClass(_52.bodyCls);
if(_52.id){
$(_51).attr("id",_52.id);
}else{
$(_51).attr("id","");
}
};
function _56(_57,_58){
$.data(_57,"panel").options.title=_58;
$(_57).panel("header").find("div.panel-title").html(_58);
};
var TO=false;
var _59=true;
$(window).unbind(".panel").bind("resize.panel",function(){
if(!_59){
return;
}
if(TO!==false){
clearTimeout(TO);
}
TO=setTimeout(function(){
_59=false;
var _5a=$("body.layout");
if(_5a.length){
_5a.layout("resize");
}else{
$("body").children("div.panel,div.accordion,div.tabs-container,div.layout").triggerHandler("_resize");
}
_59=true;
TO=false;
},200);
});
$.fn.panel=function(_5b,_5c){
if(typeof _5b=="string"){
return $.fn.panel.methods[_5b](this,_5c);
}
_5b=_5b||{};
return this.each(function(){
var _5d=$.data(this,"panel");
var _5e;
if(_5d){
_5e=$.extend(_5d.options,_5b);
_5d.isLoaded=false;
}else{
_5e=$.extend({},$.fn.panel.defaults,$.fn.panel.parseOptions(this),_5b);
$(this).attr("title","");
_5d=$.data(this,"panel",{options:_5e,panel:_f(this),isLoaded:false});
}
_13(this);
_50(this);
if(_5e.doSize==true){
_5d.panel.css("display","block");
_3(this);
}
if(_5e.closed==true||_5e.minimized==true){
_5d.panel.hide();
}else{
_25(this);
}
});
};
$.fn.panel.methods={options:function(jq){
return $.data(jq[0],"panel").options;
},panel:function(jq){
return $.data(jq[0],"panel").panel;
},header:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-header");
},body:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-body");
},setTitle:function(jq,_5f){
return jq.each(function(){
_56(this,_5f);
});
},open:function(jq,_60){
return jq.each(function(){
_25(this,_60);
});
},close:function(jq,_61){
return jq.each(function(){
_19(this,_61);
});
},destroy:function(jq,_62){
return jq.each(function(){
_31(this,_62);
});
},refresh:function(jq,_63){
return jq.each(function(){
$.data(this,"panel").isLoaded=false;
if(_63){
$.data(this,"panel").options.href=_63;
}
_1a(this);
});
},resize:function(jq,_64){
return jq.each(function(){
_3(this,_64);
});
},move:function(jq,_65){
return jq.each(function(){
_a(this,_65);
});
},maximize:function(jq){
return jq.each(function(){
_2b(this);
});
},minimize:function(jq){
return jq.each(function(){
_47(this);
});
},restore:function(jq){
return jq.each(function(){
_4b(this);
});
},collapse:function(jq,_66){
return jq.each(function(){
_2c(this,_66);
});
},expand:function(jq,_67){
return jq.each(function(){
_3c(this,_67);
});
}};
$.fn.panel.parseOptions=function(_68){
var t=$(_68);
return $.extend({},$.parser.parseOptions(_68,["id","width","height","left","top","title","iconCls","cls","headerCls","bodyCls","tools","href",{cache:"boolean",fit:"boolean",border:"boolean",noheader:"boolean"},{collapsible:"boolean",minimizable:"boolean",maximizable:"boolean"},{closable:"boolean",collapsed:"boolean",minimized:"boolean",maximized:"boolean",closed:"boolean"}]),{loadingMessage:(t.attr("loadingMessage")!=undefined?t.attr("loadingMessage"):undefined)});
};
$.fn.panel.defaults={id:null,title:null,iconCls:null,width:"auto",height:"auto",left:null,top:null,cls:null,headerCls:null,bodyCls:null,style:{},href:null,cache:true,fit:false,border:true,doSize:true,noheader:false,content:null,collapsible:false,minimizable:false,maximizable:false,closable:false,collapsed:false,minimized:false,maximized:false,closed:false,tools:null,href:null,loadingMessage:"Loading...",extractor:function(_69){
var _6a=/<body[^>]*>((.|[\n\r])*)<\/body>/im;
var _6b=_6a.exec(_69);
if(_6b){
return _6b[1];
}else{
return _69;
}
},onBeforeLoad:function(){
},onLoad:function(){
},onBeforeOpen:function(){
},onOpen:function(){
},onBeforeClose:function(){
},onClose:function(){
},onBeforeDestroy:function(){
},onDestroy:function(){
},onResize:function(_6c,_6d){
},onMove:function(_6e,top){
},onMaximize:function(){
},onRestore:function(){
},onMinimize:function(){
},onBeforeCollapse:function(){
},onBeforeExpand:function(){
},onCollapse:function(){
},onExpand:function(){
}};
})(jQuery);

