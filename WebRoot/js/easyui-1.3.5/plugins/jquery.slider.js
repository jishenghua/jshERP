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
var _3=$("<div class=\"slider\">"+"<div class=\"slider-inner\">"+"<a href=\"javascript:void(0)\" class=\"slider-handle\"></a>"+"<span class=\"slider-tip\"></span>"+"</div>"+"<div class=\"slider-rule\"></div>"+"<div class=\"slider-rulelabel\"></div>"+"<div style=\"clear:both\"></div>"+"<input type=\"hidden\" class=\"slider-value\">"+"</div>").insertAfter(_2);
var t=$(_2);
t.addClass("slider-f").hide();
var _4=t.attr("name");
if(_4){
_3.find("input.slider-value").attr("name",_4);
t.removeAttr("name").attr("sliderName",_4);
}
return _3;
};
function _5(_6,_7){
var _8=$.data(_6,"slider");
var _9=_8.options;
var _a=_8.slider;
if(_7){
if(_7.width){
_9.width=_7.width;
}
if(_7.height){
_9.height=_7.height;
}
}
if(_9.mode=="h"){
_a.css("height","");
_a.children("div").css("height","");
if(!isNaN(_9.width)){
_a.width(_9.width);
}
}else{
_a.css("width","");
_a.children("div").css("width","");
if(!isNaN(_9.height)){
_a.height(_9.height);
_a.find("div.slider-rule").height(_9.height);
_a.find("div.slider-rulelabel").height(_9.height);
_a.find("div.slider-inner")._outerHeight(_9.height);
}
}
_b(_6);
};
function _c(_d){
var _e=$.data(_d,"slider");
var _f=_e.options;
var _10=_e.slider;
var aa=_f.mode=="h"?_f.rule:_f.rule.slice(0).reverse();
if(_f.reversed){
aa=aa.slice(0).reverse();
}
_11(aa);
function _11(aa){
var _12=_10.find("div.slider-rule");
var _13=_10.find("div.slider-rulelabel");
_12.empty();
_13.empty();
for(var i=0;i<aa.length;i++){
var _14=i*100/(aa.length-1)+"%";
var _15=$("<span></span>").appendTo(_12);
_15.css((_f.mode=="h"?"left":"top"),_14);
if(aa[i]!="|"){
_15=$("<span></span>").appendTo(_13);
_15.html(aa[i]);
if(_f.mode=="h"){
_15.css({left:_14,marginLeft:-Math.round(_15.outerWidth()/2)});
}else{
_15.css({top:_14,marginTop:-Math.round(_15.outerHeight()/2)});
}
}
}
};
};
function _16(_17){
var _18=$.data(_17,"slider");
var _19=_18.options;
var _1a=_18.slider;
_1a.removeClass("slider-h slider-v slider-disabled");
_1a.addClass(_19.mode=="h"?"slider-h":"slider-v");
_1a.addClass(_19.disabled?"slider-disabled":"");
_1a.find("a.slider-handle").draggable({axis:_19.mode,cursor:"pointer",disabled:_19.disabled,onDrag:function(e){
var _1b=e.data.left;
var _1c=_1a.width();
if(_19.mode!="h"){
_1b=e.data.top;
_1c=_1a.height();
}
if(_1b<0||_1b>_1c){
return false;
}else{
var _1d=_32(_17,_1b);
_1e(_1d);
return false;
}
},onBeforeDrag:function(){
_18.isDragging=true;
},onStartDrag:function(){
_19.onSlideStart.call(_17,_19.value);
},onStopDrag:function(e){
var _1f=_32(_17,(_19.mode=="h"?e.data.left:e.data.top));
_1e(_1f);
_19.onSlideEnd.call(_17,_19.value);
_19.onComplete.call(_17,_19.value);
_18.isDragging=false;
}});
_1a.find("div.slider-inner").unbind(".slider").bind("mousedown.slider",function(e){
if(_18.isDragging){
return;
}
var pos=$(this).offset();
var _20=_32(_17,(_19.mode=="h"?(e.pageX-pos.left):(e.pageY-pos.top)));
_1e(_20);
_19.onComplete.call(_17,_19.value);
});
function _1e(_21){
var s=Math.abs(_21%_19.step);
if(s<_19.step/2){
_21-=s;
}else{
_21=_21-s+_19.step;
}
_22(_17,_21);
};
};
function _22(_23,_24){
var _25=$.data(_23,"slider");
var _26=_25.options;
var _27=_25.slider;
var _28=_26.value;
if(_24<_26.min){
_24=_26.min;
}
if(_24>_26.max){
_24=_26.max;
}
_26.value=_24;
$(_23).val(_24);
_27.find("input.slider-value").val(_24);
var pos=_29(_23,_24);
var tip=_27.find(".slider-tip");
if(_26.showTip){
tip.show();
tip.html(_26.tipFormatter.call(_23,_26.value));
}else{
tip.hide();
}
if(_26.mode=="h"){
var _2a="left:"+pos+"px;";
_27.find(".slider-handle").attr("style",_2a);
tip.attr("style",_2a+"margin-left:"+(-Math.round(tip.outerWidth()/2))+"px");
}else{
var _2a="top:"+pos+"px;";
_27.find(".slider-handle").attr("style",_2a);
tip.attr("style",_2a+"margin-left:"+(-Math.round(tip.outerWidth()))+"px");
}
if(_28!=_24){
_26.onChange.call(_23,_24,_28);
}
};
function _b(_2b){
var _2c=$.data(_2b,"slider").options;
var fn=_2c.onChange;
_2c.onChange=function(){
};
_22(_2b,_2c.value);
_2c.onChange=fn;
};
function _29(_2d,_2e){
var _2f=$.data(_2d,"slider");
var _30=_2f.options;
var _31=_2f.slider;
if(_30.mode=="h"){
var pos=(_2e-_30.min)/(_30.max-_30.min)*_31.width();
if(_30.reversed){
pos=_31.width()-pos;
}
}else{
var pos=_31.height()-(_2e-_30.min)/(_30.max-_30.min)*_31.height();
if(_30.reversed){
pos=_31.height()-pos;
}
}
return pos.toFixed(0);
};
function _32(_33,pos){
var _34=$.data(_33,"slider");
var _35=_34.options;
var _36=_34.slider;
if(_35.mode=="h"){
var _37=_35.min+(_35.max-_35.min)*(pos/_36.width());
}else{
var _37=_35.min+(_35.max-_35.min)*((_36.height()-pos)/_36.height());
}
return _35.reversed?_35.max-_37.toFixed(0):_37.toFixed(0);
};
$.fn.slider=function(_38,_39){
if(typeof _38=="string"){
return $.fn.slider.methods[_38](this,_39);
}
_38=_38||{};
return this.each(function(){
var _3a=$.data(this,"slider");
if(_3a){
$.extend(_3a.options,_38);
}else{
_3a=$.data(this,"slider",{options:$.extend({},$.fn.slider.defaults,$.fn.slider.parseOptions(this),_38),slider:_1(this)});
$(this).removeAttr("disabled");
}
var _3b=_3a.options;
_3b.min=parseFloat(_3b.min);
_3b.max=parseFloat(_3b.max);
_3b.value=parseFloat(_3b.value);
_3b.step=parseFloat(_3b.step);
_3b.originalValue=_3b.value;
_16(this);
_c(this);
_5(this);
});
};
$.fn.slider.methods={options:function(jq){
return $.data(jq[0],"slider").options;
},destroy:function(jq){
return jq.each(function(){
$.data(this,"slider").slider.remove();
$(this).remove();
});
},resize:function(jq,_3c){
return jq.each(function(){
_5(this,_3c);
});
},getValue:function(jq){
return jq.slider("options").value;
},setValue:function(jq,_3d){
return jq.each(function(){
_22(this,_3d);
});
},clear:function(jq){
return jq.each(function(){
var _3e=$(this).slider("options");
_22(this,_3e.min);
});
},reset:function(jq){
return jq.each(function(){
var _3f=$(this).slider("options");
_22(this,_3f.originalValue);
});
},enable:function(jq){
return jq.each(function(){
$.data(this,"slider").options.disabled=false;
_16(this);
});
},disable:function(jq){
return jq.each(function(){
$.data(this,"slider").options.disabled=true;
_16(this);
});
}};
$.fn.slider.parseOptions=function(_40){
var t=$(_40);
return $.extend({},$.parser.parseOptions(_40,["width","height","mode",{reversed:"boolean",showTip:"boolean",min:"number",max:"number",step:"number"}]),{value:(t.val()||undefined),disabled:(t.attr("disabled")?true:undefined),rule:(t.attr("rule")?eval(t.attr("rule")):undefined)});
};
$.fn.slider.defaults={width:"auto",height:"auto",mode:"h",reversed:false,showTip:false,disabled:false,value:0,min:0,max:100,step:1,rule:[],tipFormatter:function(_41){
return _41;
},onChange:function(_42,_43){
},onSlideStart:function(_44){
},onSlideEnd:function(_45){
},onComplete:function(_46){
}};
})(jQuery);

