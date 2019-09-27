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
var _3=$.data(_2,"datebox");
var _4=_3.options;
$(_2).addClass("datebox-f").combo($.extend({},_4,{onShowPanel:function(){
_5();
_10(_2,$(_2).datebox("getText"));
_4.onShowPanel.call(_2);
}}));
$(_2).combo("textbox").parent().addClass("datebox");
if(!_3.calendar){
_6();
}
function _6(){
var _7=$(_2).combo("panel").css("overflow","hidden");
var cc=$("<div class=\"datebox-calendar-inner\"></div>").appendTo(_7);
if(_4.sharedCalendar){
_3.calendar=$(_4.sharedCalendar).appendTo(cc);
if(!_3.calendar.hasClass("calendar")){
_3.calendar.calendar();
}
}else{
_3.calendar=$("<div></div>").appendTo(cc).calendar();
}
$.extend(_3.calendar.calendar("options"),{fit:true,border:false,onSelect:function(_8){
var _9=$(this.target).datebox("options");
_10(this.target,_9.formatter(_8));
$(this.target).combo("hidePanel");
_9.onSelect.call(_2,_8);
}});
_10(_2,_4.value);
var _a=$("<div class=\"datebox-button\"><table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%\"><tr></tr></table></div>").appendTo(_7);
var tr=_a.find("tr");
for(var i=0;i<_4.buttons.length;i++){
var td=$("<td></td>").appendTo(tr);
var _b=_4.buttons[i];
var t=$("<a href=\"javascript:void(0)\"></a>").html($.isFunction(_b.text)?_b.text(_2):_b.text).appendTo(td);
t.bind("click",{target:_2,handler:_b.handler},function(e){
e.data.handler.call(this,e.data.target);
});
}
tr.find("td").css("width",(100/_4.buttons.length)+"%");
};
function _5(){
var _c=$(_2).combo("panel");
var cc=_c.children("div.datebox-calendar-inner");
_c.children()._outerWidth(_c.width());
_3.calendar.appendTo(cc);
_3.calendar[0].target=_2;
if(_4.panelHeight!="auto"){
var _d=_c.height();
_c.children().not(cc).each(function(){
_d-=$(this).outerHeight();
});
cc._outerHeight(_d);
}
_3.calendar.calendar("resize");
};
};
function _e(_f,q){
_10(_f,q);
};
function _11(_12){
var _13=$.data(_12,"datebox");
var _14=_13.options;
var _15=_14.formatter(_13.calendar.calendar("options").current);
_10(_12,_15);
$(_12).combo("hidePanel");
};
function _10(_16,_17){
var _18=$.data(_16,"datebox");
var _19=_18.options;
$(_16).combo("setValue",_17).combo("setText",_17);
_18.calendar.calendar("moveTo",_19.parser(_17));
};
$.fn.datebox=function(_1a,_1b){
if(typeof _1a=="string"){
var _1c=$.fn.datebox.methods[_1a];
if(_1c){
return _1c(this,_1b);
}else{
return this.combo(_1a,_1b);
}
}
_1a=_1a||{};
return this.each(function(){
var _1d=$.data(this,"datebox");
if(_1d){
$.extend(_1d.options,_1a);
}else{
$.data(this,"datebox",{options:$.extend({},$.fn.datebox.defaults,$.fn.datebox.parseOptions(this),_1a)});
}
_1(this);
});
};
$.fn.datebox.methods={options:function(jq){
var _1e=jq.combo("options");
return $.extend($.data(jq[0],"datebox").options,{originalValue:_1e.originalValue,disabled:_1e.disabled,readonly:_1e.readonly});
},calendar:function(jq){
return $.data(jq[0],"datebox").calendar;
},setValue:function(jq,_1f){
return jq.each(function(){
_10(this,_1f);
});
},reset:function(jq){
return jq.each(function(){
var _20=$(this).datebox("options");
$(this).datebox("setValue",_20.originalValue);
});
}};
$.fn.datebox.parseOptions=function(_21){
return $.extend({},$.fn.combo.parseOptions(_21),$.parser.parseOptions(_21,["sharedCalendar"]));
};
$.fn.datebox.defaults=$.extend({},$.fn.combo.defaults,{panelWidth:180,panelHeight:"auto",sharedCalendar:null,keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
_11(this);
},query:function(q,e){
_e(this,q);
}},currentText:"Today",closeText:"Close",okText:"Ok",buttons:[{text:function(_22){
return $(_22).datebox("options").currentText;
},handler:function(_23){
$(_23).datebox("calendar").calendar({year:new Date().getFullYear(),month:new Date().getMonth()+1,current:new Date()});
_11(_23);
}},{text:function(_24){
return $(_24).datebox("options").closeText;
},handler:function(_25){
$(this).closest("div.combo-panel").panel("close");
}}],formatter:function(_26){
var y=_26.getFullYear();
var m=_26.getMonth()+1;
var d=_26.getDate();
return m+"/"+d+"/"+y;
},parser:function(s){
var t=Date.parse(s);
if(!isNaN(t)){
return new Date(t);
}else{
return new Date();
}
},onSelect:function(_27){
}});
})(jQuery);

