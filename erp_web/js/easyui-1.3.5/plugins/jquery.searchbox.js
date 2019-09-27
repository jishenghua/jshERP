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
$(_2).addClass("searchbox-f").hide();
var _3=$("<span class=\"searchbox\"></span>").insertAfter(_2);
var _4=$("<input type=\"text\" class=\"searchbox-text\">").appendTo(_3);
$("<span><span class=\"searchbox-button\"></span></span>").appendTo(_3);
var _5=$(_2).attr("name");
if(_5){
_4.attr("name",_5);
$(_2).removeAttr("name").attr("searchboxName",_5);
}
return _3;
};
function _6(_7,_8){
var _9=$.data(_7,"searchbox").options;
var sb=$.data(_7,"searchbox").searchbox;
if(_8){
_9.width=_8;
}
sb.appendTo("body");
if(isNaN(_9.width)){
_9.width=sb._outerWidth();
}
var _a=sb.find("span.searchbox-button");
var _b=sb.find("a.searchbox-menu");
var _c=sb.find("input.searchbox-text");
sb._outerWidth(_9.width)._outerHeight(_9.height);
_c._outerWidth(sb.width()-_b._outerWidth()-_a._outerWidth());
_c.css({height:sb.height()+"px",lineHeight:sb.height()+"px"});
_b._outerHeight(sb.height());
_a._outerHeight(sb.height());
var _d=_b.find("span.l-btn-left");
_d._outerHeight(sb.height());
_d.find("span.l-btn-text,span.m-btn-downarrow").css({height:_d.height()+"px",lineHeight:_d.height()+"px"});
sb.insertAfter(_7);
};
function _e(_f){
var _10=$.data(_f,"searchbox");
var _11=_10.options;
if(_11.menu){
_10.menu=$(_11.menu).menu({onClick:function(_12){
_13(_12);
}});
var _14=_10.menu.children("div.menu-item:first");
_10.menu.children("div.menu-item").each(function(){
var _15=$.extend({},$.parser.parseOptions(this),{selected:($(this).attr("selected")?true:undefined)});
if(_15.selected){
_14=$(this);
return false;
}
});
_14.triggerHandler("click");
}else{
_10.searchbox.find("a.searchbox-menu").remove();
_10.menu=null;
}
function _13(_16){
_10.searchbox.find("a.searchbox-menu").remove();
var mb=$("<a class=\"searchbox-menu\" href=\"javascript:void(0)\"></a>").html(_16.text);
mb.prependTo(_10.searchbox).menubutton({menu:_10.menu,iconCls:_16.iconCls});
_10.searchbox.find("input.searchbox-text").attr("name",_16.name||_16.text);
_6(_f);
};
};
function _17(_18){
var _19=$.data(_18,"searchbox");
var _1a=_19.options;
var _1b=_19.searchbox.find("input.searchbox-text");
var _1c=_19.searchbox.find(".searchbox-button");
_1b.unbind(".searchbox").bind("blur.searchbox",function(e){
_1a.value=$(this).val();
if(_1a.value==""){
$(this).val(_1a.prompt);
$(this).addClass("searchbox-prompt");
}else{
$(this).removeClass("searchbox-prompt");
}
}).bind("focus.searchbox",function(e){
if($(this).val()!=_1a.value){
$(this).val(_1a.value);
}
$(this).removeClass("searchbox-prompt");
}).bind("keydown.searchbox",function(e){
if(e.keyCode==13){
e.preventDefault();
_1a.value=$(this).val();
_1a.searcher.call(_18,_1a.value,_1b._propAttr("name"));
return false;
}
});
_1c.unbind(".searchbox").bind("click.searchbox",function(){
_1a.searcher.call(_18,_1a.value,_1b._propAttr("name"));
}).bind("mouseenter.searchbox",function(){
$(this).addClass("searchbox-button-hover");
}).bind("mouseleave.searchbox",function(){
$(this).removeClass("searchbox-button-hover");
});
};
function _1d(_1e){
var _1f=$.data(_1e,"searchbox");
var _20=_1f.options;
var _21=_1f.searchbox.find("input.searchbox-text");
if(_20.value==""){
_21.val(_20.prompt);
_21.addClass("searchbox-prompt");
}else{
_21.val(_20.value);
_21.removeClass("searchbox-prompt");
}
};
$.fn.searchbox=function(_22,_23){
if(typeof _22=="string"){
return $.fn.searchbox.methods[_22](this,_23);
}
_22=_22||{};
return this.each(function(){
var _24=$.data(this,"searchbox");
if(_24){
$.extend(_24.options,_22);
}else{
_24=$.data(this,"searchbox",{options:$.extend({},$.fn.searchbox.defaults,$.fn.searchbox.parseOptions(this),_22),searchbox:_1(this)});
}
_e(this);
_1d(this);
_17(this);
_6(this);
});
};
$.fn.searchbox.methods={options:function(jq){
return $.data(jq[0],"searchbox").options;
},menu:function(jq){
return $.data(jq[0],"searchbox").menu;
},textbox:function(jq){
return $.data(jq[0],"searchbox").searchbox.find("input.searchbox-text");
},getValue:function(jq){
return $.data(jq[0],"searchbox").options.value;
},setValue:function(jq,_25){
return jq.each(function(){
$(this).searchbox("options").value=_25;
$(this).searchbox("textbox").val(_25);
$(this).searchbox("textbox").blur();
});
},getName:function(jq){
return $.data(jq[0],"searchbox").searchbox.find("input.searchbox-text").attr("name");
},selectName:function(jq,_26){
return jq.each(function(){
var _27=$.data(this,"searchbox").menu;
if(_27){
_27.children("div.menu-item[name=\""+_26+"\"]").triggerHandler("click");
}
});
},destroy:function(jq){
return jq.each(function(){
var _28=$(this).searchbox("menu");
if(_28){
_28.menu("destroy");
}
$.data(this,"searchbox").searchbox.remove();
$(this).remove();
});
},resize:function(jq,_29){
return jq.each(function(){
_6(this,_29);
});
}};
$.fn.searchbox.parseOptions=function(_2a){
var t=$(_2a);
return $.extend({},$.parser.parseOptions(_2a,["width","height","prompt","menu"]),{value:t.val(),searcher:(t.attr("searcher")?eval(t.attr("searcher")):undefined)});
};
$.fn.searchbox.defaults={width:"auto",height:22,prompt:"",value:"",menu:null,searcher:function(_2b,_2c){
}};
})(jQuery);

