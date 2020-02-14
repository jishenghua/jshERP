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
function _1(_2){
var _3=$.data(_2,"combotree");
var _4=_3.options;
var _5=_3.tree;
$(_2).addClass("combotree-f");
$(_2).combo($.extend({},_4,{onShowPanel:function(){
if(_4.editable){
_5.tree("doFilter","");
}
_4.onShowPanel.call(this);
}}));
var _6=$(_2).combo("panel");
if(!_5){
_5=$("<ul></ul>").appendTo(_6);
_3.tree=_5;
}
_5.tree($.extend({},_4,{checkbox:_4.multiple,onLoadSuccess:function(_7,_8){
var _9=$(_2).combotree("getValues");
if(_4.multiple){
$.map(_5.tree("getChecked"),function(_a){
$.easyui.addArrayItem(_9,_a.id);
});
}
_15(_2,_9,_3.remainText);
_4.onLoadSuccess.call(this,_7,_8);
},onClick:function(_b){
if(_4.multiple){
$(this).tree(_b.checked?"uncheck":"check",_b.target);
}else{
$(_2).combo("hidePanel");
}
_3.remainText=false;
_e(_2);
_4.onClick.call(this,_b);
},onCheck:function(_c,_d){
_3.remainText=false;
_e(_2);
_4.onCheck.call(this,_c,_d);
}}));
};
function _e(_f){
var _10=$.data(_f,"combotree");
var _11=_10.options;
var _12=_10.tree;
var vv=[];
if(_11.multiple){
vv=$.map(_12.tree("getChecked"),function(_13){
return _13.id;
});
}else{
var _14=_12.tree("getSelected");
if(_14){
vv.push(_14.id);
}
}
vv=vv.concat(_11.unselectedValues);
_15(_f,vv,_10.remainText);
};
function _15(_16,_17,_18){
var _19=$.data(_16,"combotree");
var _1a=_19.options;
var _1b=_19.tree;
var _1c=_1b.tree("options");
var _1d=_1c.onBeforeCheck;
var _1e=_1c.onCheck;
var _1f=_1c.onBeforeSelect;
var _20=_1c.onSelect;
_1c.onBeforeCheck=_1c.onCheck=_1c.onBeforeSelect=_1c.onSelect=function(){
};
if(!$.isArray(_17)){
_17=_17.split(_1a.separator);
}
if(!_1a.multiple){
_17=_17.length?[_17[0]]:[""];
}
var vv=$.map(_17,function(_21){
return String(_21);
});
_1b.find("div.tree-node-selected").removeClass("tree-node-selected");
$.map(_1b.tree("getChecked"),function(_22){
if($.inArray(String(_22.id),vv)==-1){
_1b.tree("uncheck",_22.target);
}
});
var ss=[];
_1a.unselectedValues=[];
$.map(vv,function(v){
var _23=_1b.tree("find",v);
if(_23){
_1b.tree("check",_23.target).tree("select",_23.target);
ss.push(_24(_23));
}else{
ss.push(_25(v,_1a.mappingRows)||v);
_1a.unselectedValues.push(v);
}
});
if(_1a.multiple){
$.map(_1b.tree("getChecked"),function(_26){
var id=String(_26.id);
if($.inArray(id,vv)==-1){
vv.push(id);
ss.push(_24(_26));
}
});
}
_1c.onBeforeCheck=_1d;
_1c.onCheck=_1e;
_1c.onBeforeSelect=_1f;
_1c.onSelect=_20;
if(!_18){
var s=ss.join(_1a.separator);
if($(_16).combo("getText")!=s){
$(_16).combo("setText",s);
}
}
$(_16).combo("setValues",vv);
function _25(_27,a){
var _28=$.easyui.getArrayItem(a,"id",_27);
return _28?_24(_28):undefined;
};
function _24(_29){
return _29[_1a.textField||""]||_29.text;
};
};
function _2a(_2b,q){
var _2c=$.data(_2b,"combotree");
var _2d=_2c.options;
var _2e=_2c.tree;
_2c.remainText=true;
_2e.tree("doFilter",_2d.multiple?q.split(_2d.separator):q);
};
function _2f(_30){
var _31=$.data(_30,"combotree");
_31.remainText=false;
$(_30).combotree("setValues",$(_30).combotree("getValues"));
$(_30).combotree("hidePanel");
};
$.fn.combotree=function(_32,_33){
if(typeof _32=="string"){
var _34=$.fn.combotree.methods[_32];
if(_34){
return _34(this,_33);
}else{
return this.combo(_32,_33);
}
}
_32=_32||{};
return this.each(function(){
var _35=$.data(this,"combotree");
if(_35){
$.extend(_35.options,_32);
}else{
$.data(this,"combotree",{options:$.extend({},$.fn.combotree.defaults,$.fn.combotree.parseOptions(this),_32)});
}
_1(this);
});
};
$.fn.combotree.methods={options:function(jq){
var _36=jq.combo("options");
return $.extend($.data(jq[0],"combotree").options,{width:_36.width,height:_36.height,originalValue:_36.originalValue,disabled:_36.disabled,readonly:_36.readonly});
},clone:function(jq,_37){
var t=jq.combo("clone",_37);
t.data("combotree",{options:$.extend(true,{},jq.combotree("options")),tree:jq.combotree("tree")});
return t;
},tree:function(jq){
return $.data(jq[0],"combotree").tree;
},loadData:function(jq,_38){
return jq.each(function(){
var _39=$.data(this,"combotree").options;
_39.data=_38;
var _3a=$.data(this,"combotree").tree;
_3a.tree("loadData",_38);
});
},reload:function(jq,url){
return jq.each(function(){
var _3b=$.data(this,"combotree").options;
var _3c=$.data(this,"combotree").tree;
if(url){
_3b.url=url;
}
_3c.tree({url:_3b.url});
});
},setValues:function(jq,_3d){
return jq.each(function(){
var _3e=$(this).combotree("options");
if($.isArray(_3d)){
_3d=$.map(_3d,function(_3f){
if(_3f&&typeof _3f=="object"){
$.easyui.addArrayItem(_3e.mappingRows,"id",_3f);
return _3f.id;
}else{
return _3f;
}
});
}
_15(this,_3d);
});
},setValue:function(jq,_40){
return jq.each(function(){
$(this).combotree("setValues",$.isArray(_40)?_40:[_40]);
});
},clear:function(jq){
return jq.each(function(){
$(this).combotree("setValues",[]);
});
},reset:function(jq){
return jq.each(function(){
var _41=$(this).combotree("options");
if(_41.multiple){
$(this).combotree("setValues",_41.originalValue);
}else{
$(this).combotree("setValue",_41.originalValue);
}
});
}};
$.fn.combotree.parseOptions=function(_42){
return $.extend({},$.fn.combo.parseOptions(_42),$.fn.tree.parseOptions(_42));
};
$.fn.combotree.defaults=$.extend({},$.fn.combo.defaults,$.fn.tree.defaults,{editable:false,textField:null,unselectedValues:[],mappingRows:[],keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
_2f(this);
},query:function(q,e){
_2a(this,q);
}}});
})(jQuery);

