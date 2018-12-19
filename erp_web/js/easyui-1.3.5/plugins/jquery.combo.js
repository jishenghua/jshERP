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
function _1(_2,_3){
var _4=$.data(_2,"combo");
var _5=_4.options;
var _6=_4.combo;
var _7=_4.panel;
if(_3){
_5.width=_3;
}
if(isNaN(_5.width)){
var c=$(_2).clone();
c.css("visibility","hidden");
c.appendTo("body");
_5.width=c.outerWidth();
c.remove();
}
_6.appendTo("body");
var _8=_6.find("input.combo-text");
var _9=_6.find(".combo-arrow");
var _a=_5.hasDownArrow?_9._outerWidth():0;
_6._outerWidth(_5.width)._outerHeight(_5.height);
_8._outerWidth(_6.width()-_a);
_8.css({height:_6.height()+"px",lineHeight:_6.height()+"px"});
_9._outerHeight(_6.height());
_7.panel("resize",{width:(_5.panelWidth?_5.panelWidth:_6.outerWidth()),height:_5.panelHeight});
_6.insertAfter(_2);
};
function _b(_c){
$(_c).addClass("combo-f").hide();
var _d=$("<span class=\"combo\">"+"<input type=\"text\" class=\"combo-text\" autocomplete=\"off\">"+"<span><span class=\"combo-arrow\"></span></span>"+"<input type=\"hidden\" class=\"combo-value\">"+"</span>").insertAfter(_c);
var _e=$("<div class=\"combo-panel\"></div>").appendTo("body");
_e.panel({doSize:false,closed:true,cls:"combo-p",style:{position:"absolute",zIndex:10},onOpen:function(){
$(this).panel("resize");
},onClose:function(){
var _f=$.data(_c,"combo");
if(_f){
_f.options.onHidePanel.call(_c);
}
}});
var _10=$(_c).attr("name");
if(_10){
_d.find("input.combo-value").attr("name",_10);
$(_c).removeAttr("name").attr("comboName",_10);
}
return {combo:_d,panel:_e};
};
function _11(_12){
var _13=$.data(_12,"combo");
var _14=_13.options;
var _15=_13.combo;
if(_14.hasDownArrow){
_15.find(".combo-arrow").show();
}else{
_15.find(".combo-arrow").hide();
}
_16(_12,_14.disabled);
_17(_12,_14.readonly);
};
function _18(_19){
var _1a=$.data(_19,"combo");
var _1b=_1a.combo.find("input.combo-text");
_1b.validatebox("destroy");
_1a.panel.panel("destroy");
_1a.combo.remove();
$(_19).remove();
};
function _1c(_1d){
$(_1d).find(".combo-f").each(function(){
var p=$(this).combo("panel");
if(p.is(":visible")){
p.panel("close");
}
});
};
function _1e(_1f){
var _20=$.data(_1f,"combo");
var _21=_20.options;
var _22=_20.panel;
var _23=_20.combo;
var _24=_23.find(".combo-text");
var _25=_23.find(".combo-arrow");
$(document).unbind(".combo").bind("mousedown.combo",function(e){
var p=$(e.target).closest("span.combo,div.combo-p");
if(p.length){
_1c(p);
return;
}
$("body>div.combo-p>div.combo-panel:visible").panel("close");
});
_24.unbind(".combo");
_25.unbind(".combo");
if(!_21.disabled&&!_21.readonly){
_24.bind("click.combo",function(e){
if(!_21.editable){
_26.call(this);
}else{
var p=$(this).closest("div.combo-panel");
$("div.combo-panel:visible").not(_22).not(p).panel("close");
}
}).bind("keydown.combo",function(e){
switch(e.keyCode){
case 38:
_21.keyHandler.up.call(_1f,e);
break;
case 40:
_21.keyHandler.down.call(_1f,e);
break;
case 37:
_21.keyHandler.left.call(_1f,e);
break;
case 39:
_21.keyHandler.right.call(_1f,e);
break;
case 13:
e.preventDefault();
_21.keyHandler.enter.call(_1f,e);
return false;
case 9:
case 27:
_27(_1f);
break;
default:
if(_21.editable){
if(_20.timer){
clearTimeout(_20.timer);
}
_20.timer=setTimeout(function(){
var q=_24.val();
if(_20.previousValue!=q){
_20.previousValue=q;
$(_1f).combo("showPanel");
_21.keyHandler.query.call(_1f,_24.val(),e);
$(_1f).combo("validate");
}
},_21.delay);
}
}
});
_25.bind("click.combo",function(){
_26.call(this);
}).bind("mouseenter.combo",function(){
$(this).addClass("combo-arrow-hover");
}).bind("mouseleave.combo",function(){
$(this).removeClass("combo-arrow-hover");
});
}
function _26(){
if(_22.is(":visible")){
_1c(_22);
_27(_1f);
}else{
var p=$(this).closest("div.combo-panel");
$("div.combo-panel:visible").not(_22).not(p).panel("close");
$(_1f).combo("showPanel");
}
_24.focus();
};
};
function _28(_29){
var _2a=$.data(_29,"combo").options;
var _2b=$.data(_29,"combo").combo;
var _2c=$.data(_29,"combo").panel;
if($.fn.window){
_2c.panel("panel").css("z-index",$.fn.window.defaults.zIndex++);
}
_2c.panel("move",{left:_2b.offset().left,top:_2d()});
if(_2c.panel("options").closed){
_2c.panel("open");
_2a.onShowPanel.call(_29);
}
(function(){
if(_2c.is(":visible")){
_2c.panel("move",{left:_2e(),top:_2d()});
setTimeout(arguments.callee,200);
}
})();
function _2e(){
var _2f=_2b.offset().left;
if(_2f+_2c._outerWidth()>$(window)._outerWidth()+$(document).scrollLeft()){
_2f=$(window)._outerWidth()+$(document).scrollLeft()-_2c._outerWidth();
}
if(_2f<0){
_2f=0;
}
return _2f;
};
function _2d(){
var top=_2b.offset().top+_2b._outerHeight();
if(top+_2c._outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
top=_2b.offset().top-_2c._outerHeight();
}
if(top<$(document).scrollTop()){
top=_2b.offset().top+_2b._outerHeight();
}
return top;
};
};
function _27(_30){
var _31=$.data(_30,"combo").panel;
_31.panel("close");
};
function _32(_33){
var _34=$.data(_33,"combo").options;
var _35=$(_33).combo("textbox");
_35.validatebox($.extend({},_34,{deltaX:(_34.hasDownArrow?_34.deltaX:(_34.deltaX>0?1:-1))}));
};
function _16(_36,_37){
var _38=$.data(_36,"combo");
var _39=_38.options;
var _3a=_38.combo;
if(_37){
_39.disabled=true;
$(_36).attr("disabled",true);
_3a.find(".combo-value").attr("disabled",true);
_3a.find(".combo-text").attr("disabled",true);
}else{
_39.disabled=false;
$(_36).removeAttr("disabled");
_3a.find(".combo-value").removeAttr("disabled");
_3a.find(".combo-text").removeAttr("disabled");
}
};
function _17(_3b,_3c){
var _3d=$.data(_3b,"combo");
var _3e=_3d.options;
_3e.readonly=_3c==undefined?true:_3c;
var _3f=_3e.readonly?true:(!_3e.editable);
_3d.combo.find(".combo-text").attr("readonly",_3f).css("cursor",_3f?"pointer":"");
};
function _40(_41){
var _42=$.data(_41,"combo");
var _43=_42.options;
var _44=_42.combo;
if(_43.multiple){
_44.find("input.combo-value").remove();
}else{
_44.find("input.combo-value").val("");
}
_44.find("input.combo-text").val("");
};
function _45(_46){
var _47=$.data(_46,"combo").combo;
return _47.find("input.combo-text").val();
};
function _48(_49,_4a){
var _4b=$.data(_49,"combo");
var _4c=_4b.combo.find("input.combo-text");
if(_4c.val()!=_4a){
_4c.val(_4a);
$(_49).combo("validate");
_4b.previousValue=_4a;
}
};
function _4d(_4e){
var _4f=[];
var _50=$.data(_4e,"combo").combo;
_50.find("input.combo-value").each(function(){
_4f.push($(this).val());
});
return _4f;
};
function _51(_52,_53){
var _54=$.data(_52,"combo").options;
var _55=_4d(_52);
var _56=$.data(_52,"combo").combo;
_56.find("input.combo-value").remove();
var _57=$(_52).attr("comboName");
for(var i=0;i<_53.length;i++){
var _58=$("<input type=\"hidden\" class=\"combo-value\">").appendTo(_56);
if(_57){
_58.attr("name",_57);
}
_58.val(_53[i]);
}
var tmp=[];
for(var i=0;i<_55.length;i++){
tmp[i]=_55[i];
}
var aa=[];
for(var i=0;i<_53.length;i++){
for(var j=0;j<tmp.length;j++){
if(_53[i]==tmp[j]){
aa.push(_53[i]);
tmp.splice(j,1);
break;
}
}
}
if(aa.length!=_53.length||_53.length!=_55.length){
if(_54.multiple){
_54.onChange.call(_52,_53,_55);
}else{
_54.onChange.call(_52,_53[0],_55[0]);
}
}
};
function _59(_5a){
var _5b=_4d(_5a);
return _5b[0];
};
function _5c(_5d,_5e){
_51(_5d,[_5e]);
};
function _5f(_60){
var _61=$.data(_60,"combo").options;
var fn=_61.onChange;
_61.onChange=function(){
};
if(_61.multiple){
if(_61.value){
if(typeof _61.value=="object"){
_51(_60,_61.value);
}else{
_5c(_60,_61.value);
}
}else{
_51(_60,[]);
}
_61.originalValue=_4d(_60);
}else{
_5c(_60,_61.value);
_61.originalValue=_61.value;
}
_61.onChange=fn;
};
$.fn.combo=function(_62,_63){
if(typeof _62=="string"){
var _64=$.fn.combo.methods[_62];
if(_64){
return _64(this,_63);
}else{
return this.each(function(){
var _65=$(this).combo("textbox");
_65.validatebox(_62,_63);
});
}
}
_62=_62||{};
return this.each(function(){
var _66=$.data(this,"combo");
if(_66){
$.extend(_66.options,_62);
}else{
var r=_b(this);
_66=$.data(this,"combo",{options:$.extend({},$.fn.combo.defaults,$.fn.combo.parseOptions(this),_62),combo:r.combo,panel:r.panel,previousValue:null});
$(this).removeAttr("disabled");
}
_11(this);
_1(this);
_1e(this);
_32(this);
_5f(this);
});
};
$.fn.combo.methods={options:function(jq){
return $.data(jq[0],"combo").options;
},panel:function(jq){
return $.data(jq[0],"combo").panel;
},textbox:function(jq){
return $.data(jq[0],"combo").combo.find("input.combo-text");
},destroy:function(jq){
return jq.each(function(){
_18(this);
});
},resize:function(jq,_67){
return jq.each(function(){
_1(this,_67);
});
},showPanel:function(jq){
return jq.each(function(){
_28(this);
});
},hidePanel:function(jq){
return jq.each(function(){
_27(this);
});
},disable:function(jq){
return jq.each(function(){
_16(this,true);
_1e(this);
});
},enable:function(jq){
return jq.each(function(){
_16(this,false);
_1e(this);
});
},readonly:function(jq,_68){
return jq.each(function(){
_17(this,_68);
_1e(this);
});
},isValid:function(jq){
var _69=$.data(jq[0],"combo").combo.find("input.combo-text");
return _69.validatebox("isValid");
},clear:function(jq){
return jq.each(function(){
_40(this);
});
},reset:function(jq){
return jq.each(function(){
var _6a=$.data(this,"combo").options;
if(_6a.multiple){
$(this).combo("setValues",_6a.originalValue);
}else{
$(this).combo("setValue",_6a.originalValue);
}
});
},getText:function(jq){
return _45(jq[0]);
},setText:function(jq,_6b){
return jq.each(function(){
_48(this,_6b);
});
},getValues:function(jq){
return _4d(jq[0]);
},setValues:function(jq,_6c){
return jq.each(function(){
_51(this,_6c);
});
},getValue:function(jq){
return _59(jq[0]);
},setValue:function(jq,_6d){
return jq.each(function(){
_5c(this,_6d);
});
}};
$.fn.combo.parseOptions=function(_6e){
var t=$(_6e);
return $.extend({},$.fn.validatebox.parseOptions(_6e),$.parser.parseOptions(_6e,["width","height","separator",{panelWidth:"number",editable:"boolean",hasDownArrow:"boolean",delay:"number",selectOnNavigation:"boolean"}]),{panelHeight:(t.attr("panelHeight")=="auto"?"auto":parseInt(t.attr("panelHeight"))||undefined),multiple:(t.attr("multiple")?true:undefined),disabled:(t.attr("disabled")?true:undefined),readonly:(t.attr("readonly")?true:undefined),value:(t.val()||undefined)});
};
$.fn.combo.defaults=$.extend({},$.fn.validatebox.defaults,{width:"auto",height:22,panelWidth:null,panelHeight:200,multiple:false,selectOnNavigation:true,separator:",",editable:true,disabled:false,readonly:false,hasDownArrow:true,value:"",delay:200,deltaX:19,keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
},query:function(q,e){
}},onShowPanel:function(){
},onHidePanel:function(){
},onChange:function(_6f,_70){
}});
})(jQuery);

