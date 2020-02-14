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
var _6=$.data(_4,"panel");
var _7=_6.options;
var _8=_6.panel;
var _9=_8.children(".panel-header");
var _a=_8.children(".panel-body");
var _b=_8.children(".panel-footer");
var _c=(_7.halign=="left"||_7.halign=="right");
if(_5){
$.extend(_7,{width:_5.width,height:_5.height,minWidth:_5.minWidth,maxWidth:_5.maxWidth,minHeight:_5.minHeight,maxHeight:_5.maxHeight,left:_5.left,top:_5.top});
_7.hasResized=false;
}
var _d=_8.outerWidth();
var _e=_8.outerHeight();
_8._size(_7);
var _f=_8.outerWidth();
var _10=_8.outerHeight();
if(_7.hasResized&&(_d==_f&&_e==_10)){
return;
}
_7.hasResized=true;
if(!_c){
_9._outerWidth(_8.width());
}
_a._outerWidth(_8.width());
if(!isNaN(parseInt(_7.height))){
if(_c){
if(_7.header){
var _11=$(_7.header)._outerWidth();
}else{
_9.css("width","");
var _11=_9._outerWidth();
}
var _12=_9.find(".panel-title");
_11+=Math.min(_12._outerWidth(),_12._outerHeight());
var _13=_8.height();
_9._outerWidth(_11)._outerHeight(_13);
_12._outerWidth(_9.height());
_a._outerWidth(_8.width()-_11-_b._outerWidth())._outerHeight(_13);
_b._outerHeight(_13);
_a.css({left:"",right:""});
if(_9.length){
_a.css(_7.halign,(_9.position()[_7.halign]+_11)+"px");
}
_7.panelCssWidth=_8.css("width");
if(_7.collapsed){
_8._outerWidth(_11+_b._outerWidth());
}
}else{
_a._outerHeight(_8.height()-_9._outerHeight()-_b._outerHeight());
}
}else{
_a.css("height","");
var min=$.parser.parseValue("minHeight",_7.minHeight,_8.parent());
var max=$.parser.parseValue("maxHeight",_7.maxHeight,_8.parent());
var _14=_9._outerHeight()+_b._outerHeight()+_8._outerHeight()-_8.height();
_a._size("minHeight",min?(min-_14):"");
_a._size("maxHeight",max?(max-_14):"");
}
_8.css({height:(_c?undefined:""),minHeight:"",maxHeight:"",left:_7.left,top:_7.top});
_7.onResize.apply(_4,[_7.width,_7.height]);
$(_4).panel("doLayout");
};
function _15(_16,_17){
var _18=$.data(_16,"panel");
var _19=_18.options;
var _1a=_18.panel;
if(_17){
if(_17.left!=null){
_19.left=_17.left;
}
if(_17.top!=null){
_19.top=_17.top;
}
}
_1a.css({left:_19.left,top:_19.top});
_1a.find(".tooltip-f").each(function(){
$(this).tooltip("reposition");
});
_19.onMove.apply(_16,[_19.left,_19.top]);
};
function _1b(_1c){
$(_1c).addClass("panel-body")._size("clear");
var _1d=$("<div class=\"panel\"></div>").insertBefore(_1c);
_1d[0].appendChild(_1c);
_1d._bind("_resize",function(e,_1e){
if($(this).hasClass("easyui-fluid")||_1e){
_3(_1c,{});
}
return false;
});
return _1d;
};
function _1f(_20){
var _21=$.data(_20,"panel");
var _22=_21.options;
var _23=_21.panel;
_23.css(_22.style);
_23.addClass(_22.cls);
_23.removeClass("panel-hleft panel-hright").addClass("panel-h"+_22.halign);
_24();
_25();
var _26=$(_20).panel("header");
var _27=$(_20).panel("body");
var _28=$(_20).siblings(".panel-footer");
if(_22.border){
_26.removeClass("panel-header-noborder");
_27.removeClass("panel-body-noborder");
_28.removeClass("panel-footer-noborder");
}else{
_26.addClass("panel-header-noborder");
_27.addClass("panel-body-noborder");
_28.addClass("panel-footer-noborder");
}
_26.addClass(_22.headerCls);
_27.addClass(_22.bodyCls);
$(_20).attr("id",_22.id||"");
if(_22.content){
$(_20).panel("clear");
$(_20).html(_22.content);
$.parser.parse($(_20));
}
function _24(){
if(_22.noheader||(!_22.title&&!_22.header)){
_1(_23.children(".panel-header"));
_23.children(".panel-body").addClass("panel-body-noheader");
}else{
if(_22.header){
$(_22.header).addClass("panel-header").prependTo(_23);
}else{
var _29=_23.children(".panel-header");
if(!_29.length){
_29=$("<div class=\"panel-header\"></div>").prependTo(_23);
}
if(!$.isArray(_22.tools)){
_29.find("div.panel-tool .panel-tool-a").appendTo(_22.tools);
}
_29.empty();
var _2a=$("<div class=\"panel-title\"></div>").html(_22.title).appendTo(_29);
if(_22.iconCls){
_2a.addClass("panel-with-icon");
$("<div class=\"panel-icon\"></div>").addClass(_22.iconCls).appendTo(_29);
}
if(_22.halign=="left"||_22.halign=="right"){
_2a.addClass("panel-title-"+_22.titleDirection);
}
var _2b=$("<div class=\"panel-tool\"></div>").appendTo(_29);
_2b._bind("click",function(e){
e.stopPropagation();
});
if(_22.tools){
if($.isArray(_22.tools)){
$.map(_22.tools,function(t){
_2c(_2b,t.iconCls,eval(t.handler));
});
}else{
$(_22.tools).children().each(function(){
$(this).addClass($(this).attr("iconCls")).addClass("panel-tool-a").appendTo(_2b);
});
}
}
if(_22.collapsible){
_2c(_2b,"panel-tool-collapse",function(){
if(_22.collapsed==true){
_57(_20,true);
}else{
_43(_20,true);
}
});
}
if(_22.minimizable){
_2c(_2b,"panel-tool-min",function(){
_62(_20);
});
}
if(_22.maximizable){
_2c(_2b,"panel-tool-max",function(){
if(_22.maximized==true){
_66(_20);
}else{
_42(_20);
}
});
}
if(_22.closable){
_2c(_2b,"panel-tool-close",function(){
_44(_20);
});
}
}
_23.children("div.panel-body").removeClass("panel-body-noheader");
}
};
function _2c(c,_2d,_2e){
var a=$("<a href=\"javascript:;\"></a>").addClass(_2d).appendTo(c);
a._bind("click",_2e);
};
function _25(){
if(_22.footer){
$(_22.footer).addClass("panel-footer").appendTo(_23);
$(_20).addClass("panel-body-nobottom");
}else{
_23.children(".panel-footer").remove();
$(_20).removeClass("panel-body-nobottom");
}
};
};
function _2f(_30,_31){
var _32=$.data(_30,"panel");
var _33=_32.options;
if(_34){
_33.queryParams=_31;
}
if(!_33.href){
return;
}
if(!_32.isLoaded||!_33.cache){
var _34=$.extend({},_33.queryParams);
if(_33.onBeforeLoad.call(_30,_34)==false){
return;
}
_32.isLoaded=false;
if(_33.loadingMessage){
$(_30).panel("clear");
$(_30).html($("<div class=\"panel-loading\"></div>").html(_33.loadingMessage));
}
_33.loader.call(_30,_34,function(_35){
var _36=_33.extractor.call(_30,_35);
$(_30).panel("clear");
$(_30).html(_36);
$.parser.parse($(_30));
_33.onLoad.apply(_30,arguments);
_32.isLoaded=true;
},function(){
_33.onLoadError.apply(_30,arguments);
});
}
};
function _37(_38){
var t=$(_38);
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
t.children("div").each(function(){
$(this)._size("unfit");
});
t.empty();
};
function _39(_3a){
$(_3a).panel("doLayout",true);
};
function _3b(_3c,_3d){
var _3e=$.data(_3c,"panel");
var _3f=_3e.options;
var _40=_3e.panel;
if(_3d!=true){
if(_3f.onBeforeOpen.call(_3c)==false){
return;
}
}
_40.stop(true,true);
if($.isFunction(_3f.openAnimation)){
_3f.openAnimation.call(_3c,cb);
}else{
switch(_3f.openAnimation){
case "slide":
_40.slideDown(_3f.openDuration,cb);
break;
case "fade":
_40.fadeIn(_3f.openDuration,cb);
break;
case "show":
_40.show(_3f.openDuration,cb);
break;
default:
_40.show();
cb();
}
}
function cb(){
_3f.closed=false;
_3f.minimized=false;
var _41=_40.children(".panel-header").find("a.panel-tool-restore");
if(_41.length){
_3f.maximized=true;
}
_3f.onOpen.call(_3c);
if(_3f.maximized==true){
_3f.maximized=false;
_42(_3c);
}
if(_3f.collapsed==true){
_3f.collapsed=false;
_43(_3c);
}
if(!_3f.collapsed){
if(_3f.href&&(!_3e.isLoaded||!_3f.cache)){
_2f(_3c);
_39(_3c);
_3f.doneLayout=true;
}
}
if(!_3f.doneLayout){
_3f.doneLayout=true;
_39(_3c);
}
};
};
function _44(_45,_46){
var _47=$.data(_45,"panel");
var _48=_47.options;
var _49=_47.panel;
if(_46!=true){
if(_48.onBeforeClose.call(_45)==false){
return;
}
}
_49.find(".tooltip-f").each(function(){
$(this).tooltip("hide");
});
_49.stop(true,true);
_49._size("unfit");
if($.isFunction(_48.closeAnimation)){
_48.closeAnimation.call(_45,cb);
}else{
switch(_48.closeAnimation){
case "slide":
_49.slideUp(_48.closeDuration,cb);
break;
case "fade":
_49.fadeOut(_48.closeDuration,cb);
break;
case "hide":
_49.hide(_48.closeDuration,cb);
break;
default:
_49.hide();
cb();
}
}
function cb(){
_48.closed=true;
_48.onClose.call(_45);
};
};
function _4a(_4b,_4c){
var _4d=$.data(_4b,"panel");
var _4e=_4d.options;
var _4f=_4d.panel;
if(_4c!=true){
if(_4e.onBeforeDestroy.call(_4b)==false){
return;
}
}
$(_4b).panel("clear").panel("clear","footer");
_1(_4f);
_4e.onDestroy.call(_4b);
};
function _43(_50,_51){
var _52=$.data(_50,"panel").options;
var _53=$.data(_50,"panel").panel;
var _54=_53.children(".panel-body");
var _55=_53.children(".panel-header");
var _56=_55.find("a.panel-tool-collapse");
if(_52.collapsed==true){
return;
}
_54.stop(true,true);
if(_52.onBeforeCollapse.call(_50)==false){
return;
}
_56.addClass("panel-tool-expand");
if(_51==true){
if(_52.halign=="left"||_52.halign=="right"){
_53.animate({width:_55._outerWidth()+_53.children(".panel-footer")._outerWidth()},function(){
cb();
});
}else{
_54.slideUp("normal",function(){
cb();
});
}
}else{
if(_52.halign=="left"||_52.halign=="right"){
_53._outerWidth(_55._outerWidth()+_53.children(".panel-footer")._outerWidth());
}
cb();
}
function cb(){
_54.hide();
_52.collapsed=true;
_52.onCollapse.call(_50);
};
};
function _57(_58,_59){
var _5a=$.data(_58,"panel").options;
var _5b=$.data(_58,"panel").panel;
var _5c=_5b.children(".panel-body");
var _5d=_5b.children(".panel-header").find("a.panel-tool-collapse");
if(_5a.collapsed==false){
return;
}
_5c.stop(true,true);
if(_5a.onBeforeExpand.call(_58)==false){
return;
}
_5d.removeClass("panel-tool-expand");
if(_59==true){
if(_5a.halign=="left"||_5a.halign=="right"){
_5c.show();
_5b.animate({width:_5a.panelCssWidth},function(){
cb();
});
}else{
_5c.slideDown("normal",function(){
cb();
});
}
}else{
if(_5a.halign=="left"||_5a.halign=="right"){
_5b.css("width",_5a.panelCssWidth);
}
cb();
}
function cb(){
_5c.show();
_5a.collapsed=false;
_5a.onExpand.call(_58);
_2f(_58);
_39(_58);
};
};
function _42(_5e){
var _5f=$.data(_5e,"panel").options;
var _60=$.data(_5e,"panel").panel;
var _61=_60.children(".panel-header").find("a.panel-tool-max");
if(_5f.maximized==true){
return;
}
_61.addClass("panel-tool-restore");
if(!$.data(_5e,"panel").original){
$.data(_5e,"panel").original={width:_5f.width,height:_5f.height,left:_5f.left,top:_5f.top,fit:_5f.fit};
}
_5f.left=0;
_5f.top=0;
_5f.fit=true;
_3(_5e);
_5f.minimized=false;
_5f.maximized=true;
_5f.onMaximize.call(_5e);
};
function _62(_63){
var _64=$.data(_63,"panel").options;
var _65=$.data(_63,"panel").panel;
_65._size("unfit");
_65.hide();
_64.minimized=true;
_64.maximized=false;
_64.onMinimize.call(_63);
};
function _66(_67){
var _68=$.data(_67,"panel").options;
var _69=$.data(_67,"panel").panel;
var _6a=_69.children(".panel-header").find("a.panel-tool-max");
if(_68.maximized==false){
return;
}
_69.show();
_6a.removeClass("panel-tool-restore");
$.extend(_68,$.data(_67,"panel").original);
_3(_67);
_68.minimized=false;
_68.maximized=false;
$.data(_67,"panel").original=null;
_68.onRestore.call(_67);
};
function _6b(_6c,_6d){
$.data(_6c,"panel").options.title=_6d;
$(_6c).panel("header").find("div.panel-title").html(_6d);
};
var _6e=null;
$(window)._unbind(".panel")._bind("resize.panel",function(){
if(_6e){
clearTimeout(_6e);
}
_6e=setTimeout(function(){
var _6f=$("body.layout");
if(_6f.length){
_6f.layout("resize");
$("body").children(".easyui-fluid:visible").each(function(){
$(this).triggerHandler("_resize");
});
}else{
$("body").panel("doLayout");
}
_6e=null;
},100);
});
$.fn.panel=function(_70,_71){
if(typeof _70=="string"){
return $.fn.panel.methods[_70](this,_71);
}
_70=_70||{};
return this.each(function(){
var _72=$.data(this,"panel");
var _73;
if(_72){
_73=$.extend(_72.options,_70);
_72.isLoaded=false;
}else{
_73=$.extend({},$.fn.panel.defaults,$.fn.panel.parseOptions(this),_70);
$(this).attr("title","");
_72=$.data(this,"panel",{options:_73,panel:_1b(this),isLoaded:false});
}
_1f(this);
$(this).show();
if(_73.doSize==true){
_72.panel.css("display","block");
_3(this);
}
if(_73.closed==true||_73.minimized==true){
_72.panel.hide();
}else{
_3b(this);
}
});
};
$.fn.panel.methods={options:function(jq){
return $.data(jq[0],"panel").options;
},panel:function(jq){
return $.data(jq[0],"panel").panel;
},header:function(jq){
return $.data(jq[0],"panel").panel.children(".panel-header");
},footer:function(jq){
return jq.panel("panel").children(".panel-footer");
},body:function(jq){
return $.data(jq[0],"panel").panel.children(".panel-body");
},setTitle:function(jq,_74){
return jq.each(function(){
_6b(this,_74);
});
},open:function(jq,_75){
return jq.each(function(){
_3b(this,_75);
});
},close:function(jq,_76){
return jq.each(function(){
_44(this,_76);
});
},destroy:function(jq,_77){
return jq.each(function(){
_4a(this,_77);
});
},clear:function(jq,_78){
return jq.each(function(){
_37(_78=="footer"?$(this).panel("footer"):this);
});
},refresh:function(jq,_79){
return jq.each(function(){
var _7a=$.data(this,"panel");
_7a.isLoaded=false;
if(_79){
if(typeof _79=="string"){
_7a.options.href=_79;
}else{
_7a.options.queryParams=_79;
}
}
_2f(this);
});
},resize:function(jq,_7b){
return jq.each(function(){
_3(this,_7b||{});
});
},doLayout:function(jq,all){
return jq.each(function(){
_7c(this,"body");
_7c($(this).siblings(".panel-footer")[0],"footer");
function _7c(_7d,_7e){
if(!_7d){
return;
}
var _7f=_7d==$("body")[0];
var s=$(_7d).find("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible,.easyui-fluid:visible").filter(function(_80,el){
var p=$(el).parents(".panel-"+_7e+":first");
return _7f?p.length==0:p[0]==_7d;
});
s.each(function(){
$(this).triggerHandler("_resize",[all||false]);
});
};
});
},move:function(jq,_81){
return jq.each(function(){
_15(this,_81);
});
},maximize:function(jq){
return jq.each(function(){
_42(this);
});
},minimize:function(jq){
return jq.each(function(){
_62(this);
});
},restore:function(jq){
return jq.each(function(){
_66(this);
});
},collapse:function(jq,_82){
return jq.each(function(){
_43(this,_82);
});
},expand:function(jq,_83){
return jq.each(function(){
_57(this,_83);
});
}};
$.fn.panel.parseOptions=function(_84){
var t=$(_84);
var hh=t.children(".panel-header,header");
var ff=t.children(".panel-footer,footer");
return $.extend({},$.parser.parseOptions(_84,["id","width","height","left","top","title","iconCls","cls","headerCls","bodyCls","tools","href","method","header","footer","halign","titleDirection",{cache:"boolean",fit:"boolean",border:"boolean",noheader:"boolean"},{collapsible:"boolean",minimizable:"boolean",maximizable:"boolean"},{closable:"boolean",collapsed:"boolean",minimized:"boolean",maximized:"boolean",closed:"boolean"},"openAnimation","closeAnimation",{openDuration:"number",closeDuration:"number"},]),{loadingMessage:(t.attr("loadingMessage")!=undefined?t.attr("loadingMessage"):undefined),header:(hh.length?hh.removeClass("panel-header"):undefined),footer:(ff.length?ff.removeClass("panel-footer"):undefined)});
};
$.fn.panel.defaults={id:null,title:null,iconCls:null,width:"auto",height:"auto",left:null,top:null,cls:null,headerCls:null,bodyCls:null,style:{},href:null,cache:true,fit:false,border:true,doSize:true,noheader:false,content:null,halign:"top",titleDirection:"down",collapsible:false,minimizable:false,maximizable:false,closable:false,collapsed:false,minimized:false,maximized:false,closed:false,openAnimation:false,openDuration:400,closeAnimation:false,closeDuration:400,tools:null,footer:null,header:null,queryParams:{},method:"get",href:null,loadingMessage:"Loading...",loader:function(_85,_86,_87){
var _88=$(this).panel("options");
if(!_88.href){
return false;
}
$.ajax({type:_88.method,url:_88.href,cache:false,data:_85,dataType:"html",success:function(_89){
_86(_89);
},error:function(){
_87.apply(this,arguments);
}});
},extractor:function(_8a){
var _8b=/<body[^>]*>((.|[\n\r])*)<\/body>/im;
var _8c=_8b.exec(_8a);
if(_8c){
return _8c[1];
}else{
return _8a;
}
},onBeforeLoad:function(_8d){
},onLoad:function(){
},onLoadError:function(){
},onBeforeOpen:function(){
},onOpen:function(){
},onBeforeClose:function(){
},onClose:function(){
},onBeforeDestroy:function(){
},onDestroy:function(){
},onResize:function(_8e,_8f){
},onMove:function(_90,top){
},onMaximize:function(){
},onRestore:function(){
},onMinimize:function(){
},onBeforeCollapse:function(){
},onBeforeExpand:function(){
},onCollapse:function(){
},onExpand:function(){
}};
})(jQuery);

