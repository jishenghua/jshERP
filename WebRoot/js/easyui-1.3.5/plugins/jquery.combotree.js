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
var _3=$.data(_2,"combotree").options;
var _4=$.data(_2,"combotree").tree;
$(_2).addClass("combotree-f");
$(_2).combo(_3);
var _5=$(_2).combo("panel");
if(!_4){
_4=$("<ul></ul>").appendTo(_5);
$.data(_2,"combotree").tree=_4;
}
_4.tree($.extend({},_3,{checkbox:_3.multiple,onLoadSuccess:function(_6,_7){
var _8=$(_2).combotree("getValues");
if(_3.multiple){
var _9=_4.tree("getChecked");
for(var i=0;i<_9.length;i++){
var id=_9[i].id;
(function(){
for(var i=0;i<_8.length;i++){
if(id==_8[i]){
return;
}
}
_8.push(id);
})();
}
}
$(_2).combotree("setValues",_8);
_3.onLoadSuccess.call(this,_6,_7);
},onClick:function(_a){
_d(_2);
$(_2).combo("hidePanel");
_3.onClick.call(this,_a);
},onCheck:function(_b,_c){
_d(_2);
_3.onCheck.call(this,_b,_c);
}}));
};
function _d(_e){
var _f=$.data(_e,"combotree").options;
var _10=$.data(_e,"combotree").tree;
var vv=[],ss=[];
if(_f.multiple){
var _11=_10.tree("getChecked");
for(var i=0;i<_11.length;i++){
vv.push(_11[i].id);
ss.push(_11[i].text);
}
}else{
var _12=_10.tree("getSelected");
if(_12){
vv.push(_12.id);
ss.push(_12.text);
}
}
$(_e).combo("setValues",vv).combo("setText",ss.join(_f.separator));
};
function _13(_14,_15){
var _16=$.data(_14,"combotree").options;
var _17=$.data(_14,"combotree").tree;
_17.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass("tree-checkbox1 tree-checkbox2");
var vv=[],ss=[];
for(var i=0;i<_15.length;i++){
var v=_15[i];
var s=v;
var _18=_17.tree("find",v);
if(_18){
s=_18.text;
_17.tree("check",_18.target);
_17.tree("select",_18.target);
}
vv.push(v);
ss.push(s);
}
$(_14).combo("setValues",vv).combo("setText",ss.join(_16.separator));
};
$.fn.combotree=function(_19,_1a){
if(typeof _19=="string"){
var _1b=$.fn.combotree.methods[_19];
if(_1b){
return _1b(this,_1a);
}else{
return this.combo(_19,_1a);
}
}
_19=_19||{};
return this.each(function(){
var _1c=$.data(this,"combotree");
if(_1c){
$.extend(_1c.options,_19);
}else{
$.data(this,"combotree",{options:$.extend({},$.fn.combotree.defaults,$.fn.combotree.parseOptions(this),_19)});
}
_1(this);
});
};
$.fn.combotree.methods={options:function(jq){
var _1d=jq.combo("options");
return $.extend($.data(jq[0],"combotree").options,{originalValue:_1d.originalValue,disabled:_1d.disabled,readonly:_1d.readonly});
},tree:function(jq){
return $.data(jq[0],"combotree").tree;
},loadData:function(jq,_1e){
return jq.each(function(){
var _1f=$.data(this,"combotree").options;
_1f.data=_1e;
var _20=$.data(this,"combotree").tree;
_20.tree("loadData",_1e);
});
},reload:function(jq,url){
return jq.each(function(){
var _21=$.data(this,"combotree").options;
var _22=$.data(this,"combotree").tree;
if(url){
_21.url=url;
}
_22.tree({url:_21.url});
});
},setValues:function(jq,_23){
return jq.each(function(){
_13(this,_23);
});
},setValue:function(jq,_24){
return jq.each(function(){
_13(this,[_24]);
});
},clear:function(jq){
return jq.each(function(){
var _25=$.data(this,"combotree").tree;
_25.find("div.tree-node-selected").removeClass("tree-node-selected");
var cc=_25.tree("getChecked");
for(var i=0;i<cc.length;i++){
_25.tree("uncheck",cc[i].target);
}
$(this).combo("clear");
});
},reset:function(jq){
return jq.each(function(){
var _26=$(this).combotree("options");
if(_26.multiple){
$(this).combotree("setValues",_26.originalValue);
}else{
$(this).combotree("setValue",_26.originalValue);
}
});
}};
$.fn.combotree.parseOptions=function(_27){
return $.extend({},$.fn.combo.parseOptions(_27),$.fn.tree.parseOptions(_27));
};
$.fn.combotree.defaults=$.extend({},$.fn.combo.defaults,$.fn.tree.defaults,{editable:false});
})(jQuery);

