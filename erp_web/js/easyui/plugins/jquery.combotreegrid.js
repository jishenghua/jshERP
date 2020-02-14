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
var _3=$.data(_2,"combotreegrid");
var _4=_3.options;
$(_2).addClass("combotreegrid-f").combo($.extend({},_4,{onShowPanel:function(){
var p=$(this).combotreegrid("panel");
var _5=p.outerHeight()-p.height();
var _6=p._size("minHeight");
var _7=p._size("maxHeight");
var dg=$(this).combotreegrid("grid");
dg.treegrid("resize",{width:"100%",height:(isNaN(parseInt(_4.panelHeight))?"auto":"100%"),minHeight:(_6?_6-_5:""),maxHeight:(_7?_7-_5:"")});
var _8=dg.treegrid("getSelected");
if(_8){
dg.treegrid("scrollTo",_8[_4.idField]);
}
_4.onShowPanel.call(this);
}}));
if(!_3.grid){
var _9=$(_2).combo("panel");
_3.grid=$("<table></table>").appendTo(_9);
}
_3.grid.treegrid($.extend({},_4,{border:false,checkbox:_4.multiple,onLoadSuccess:function(_a,_b){
var _c=$(_2).combotreegrid("getValues");
if(_4.multiple){
$.map($(this).treegrid("getCheckedNodes"),function(_d){
$.easyui.addArrayItem(_c,_d[_4.idField]);
});
}
_16(_2,_c);
_4.onLoadSuccess.call(this,_a,_b);
_3.remainText=false;
},onClickRow:function(_e){
if(_4.multiple){
$(this).treegrid(_e.checked?"uncheckNode":"checkNode",_e[_4.idField]);
$(this).treegrid("unselect",_e[_4.idField]);
}else{
$(_2).combo("hidePanel");
}
_11(_2);
_4.onClickRow.call(this,_e);
},onCheckNode:function(_f,_10){
_11(_2);
_4.onCheckNode.call(this,_f,_10);
}}));
};
function _11(_12){
var _13=$.data(_12,"combotreegrid");
var _14=_13.options;
var _15=_13.grid;
var vv=[];
if(_14.multiple){
vv=$.map(_15.treegrid("getCheckedNodes"),function(row){
return row[_14.idField];
});
}else{
var row=_15.treegrid("getSelected");
if(row){
vv.push(row[_14.idField]);
}
}
vv=vv.concat(_14.unselectedValues);
_16(_12,vv);
};
function _16(_17,_18){
var _19=$.data(_17,"combotreegrid");
var _1a=_19.options;
var _1b=_19.grid;
var _1c=_1b.datagrid("options");
var _1d=_1c.onBeforeCheck;
var _1e=_1c.onCheck;
var _1f=_1c.onBeforeSelect;
var _20=_1c.onSelect;
_1c.onBeforeCheck=_1c.onCheck=_1c.onBeforeSelect=_1c.onSelect=function(){
};
if(!$.isArray(_18)){
_18=_18.split(_1a.separator);
}
if(!_1a.multiple){
_18=_18.length?[_18[0]]:[""];
}
var vv=$.map(_18,function(_21){
return String(_21);
});
vv=$.grep(vv,function(v,_22){
return _22===$.inArray(v,vv);
});
var _23=_1b.treegrid("getSelected");
if(_23){
_1b.treegrid("unselect",_23[_1a.idField]);
}
$.map(_1b.treegrid("getCheckedNodes"),function(row){
if($.inArray(String(row[_1a.idField]),vv)==-1){
_1b.treegrid("uncheckNode",row[_1a.idField]);
}
});
var ss=[];
_1a.unselectedValues=[];
$.map(vv,function(v){
var row=_1b.treegrid("find",v);
if(row){
if(_1a.multiple){
_1b.treegrid("checkNode",v);
}else{
_1b.treegrid("select",v);
}
ss.push(_24(row));
}else{
ss.push(_25(v,_1a.mappingRows)||v);
_1a.unselectedValues.push(v);
}
});
if(_1a.multiple){
$.map(_1b.treegrid("getCheckedNodes"),function(row){
var id=String(row[_1a.idField]);
if($.inArray(id,vv)==-1){
vv.push(id);
ss.push(_24(row));
}
});
}
_1c.onBeforeCheck=_1d;
_1c.onCheck=_1e;
_1c.onBeforeSelect=_1f;
_1c.onSelect=_20;
if(!_19.remainText){
var s=ss.join(_1a.separator);
if($(_17).combo("getText")!=s){
$(_17).combo("setText",s);
}
}
$(_17).combo("setValues",vv);
function _25(_26,a){
var _27=$.easyui.getArrayItem(a,_1a.idField,_26);
return _27?_24(_27):undefined;
};
function _24(row){
return row[_1a.textField||""]||row[_1a.treeField];
};
};
function _28(_29,q){
var _2a=$.data(_29,"combotreegrid");
var _2b=_2a.options;
var _2c=_2a.grid;
_2a.remainText=true;
var qq=_2b.multiple?q.split(_2b.separator):[q];
qq=$.grep(qq,function(q){
return $.trim(q)!="";
});
_2c.treegrid("clearSelections").treegrid("clearChecked").treegrid("highlightRow",-1);
if(_2b.mode=="remote"){
_2d(qq);
_2c.treegrid("load",$.extend({},_2b.queryParams,{q:q}));
}else{
if(q){
var _2e=_2c.treegrid("getData");
var vv=[];
$.map(qq,function(q){
q=$.trim(q);
if(q){
var v=undefined;
$.easyui.forEach(_2e,true,function(row){
if(q.toLowerCase()==String(row[_2b.treeField]).toLowerCase()){
v=row[_2b.idField];
return false;
}else{
if(_2b.filter.call(_29,q,row)){
_2c.treegrid("expandTo",row[_2b.idField]);
_2c.treegrid("highlightRow",row[_2b.idField]);
return false;
}
}
});
if(v==undefined){
$.easyui.forEach(_2b.mappingRows,false,function(row){
if(q.toLowerCase()==String(row[_2b.treeField])){
v=row[_2b.idField];
return false;
}
});
}
if(v!=undefined){
vv.push(v);
}else{
vv.push(q);
}
}
});
_2d(vv);
_2a.remainText=false;
}
}
function _2d(vv){
if(!_2b.reversed){
$(_29).combotreegrid("setValues",vv);
}
};
};
function _2f(_30){
var _31=$.data(_30,"combotreegrid");
var _32=_31.options;
var _33=_31.grid;
var tr=_32.finder.getTr(_33[0],null,"highlight");
_31.remainText=false;
if(tr.length){
var id=tr.attr("node-id");
if(_32.multiple){
if(tr.hasClass("datagrid-row-selected")){
_33.treegrid("uncheckNode",id);
}else{
_33.treegrid("checkNode",id);
}
}else{
_33.treegrid("selectRow",id);
}
}
var vv=[];
if(_32.multiple){
$.map(_33.treegrid("getCheckedNodes"),function(row){
vv.push(row[_32.idField]);
});
}else{
var row=_33.treegrid("getSelected");
if(row){
vv.push(row[_32.idField]);
}
}
$.map(_32.unselectedValues,function(v){
if($.easyui.indexOfArray(_32.mappingRows,_32.idField,v)>=0){
$.easyui.addArrayItem(vv,v);
}
});
$(_30).combotreegrid("setValues",vv);
if(!_32.multiple){
$(_30).combotreegrid("hidePanel");
}
};
$.fn.combotreegrid=function(_34,_35){
if(typeof _34=="string"){
var _36=$.fn.combotreegrid.methods[_34];
if(_36){
return _36(this,_35);
}else{
return this.combo(_34,_35);
}
}
_34=_34||{};
return this.each(function(){
var _37=$.data(this,"combotreegrid");
if(_37){
$.extend(_37.options,_34);
}else{
_37=$.data(this,"combotreegrid",{options:$.extend({},$.fn.combotreegrid.defaults,$.fn.combotreegrid.parseOptions(this),_34)});
}
_1(this);
});
};
$.fn.combotreegrid.methods={options:function(jq){
var _38=jq.combo("options");
return $.extend($.data(jq[0],"combotreegrid").options,{width:_38.width,height:_38.height,originalValue:_38.originalValue,disabled:_38.disabled,readonly:_38.readonly});
},grid:function(jq){
return $.data(jq[0],"combotreegrid").grid;
},setValues:function(jq,_39){
return jq.each(function(){
var _3a=$(this).combotreegrid("options");
if($.isArray(_39)){
_39=$.map(_39,function(_3b){
if(_3b&&typeof _3b=="object"){
$.easyui.addArrayItem(_3a.mappingRows,_3a.idField,_3b);
return _3b[_3a.idField];
}else{
return _3b;
}
});
}
_16(this,_39);
});
},setValue:function(jq,_3c){
return jq.each(function(){
$(this).combotreegrid("setValues",$.isArray(_3c)?_3c:[_3c]);
});
},clear:function(jq){
return jq.each(function(){
$(this).combotreegrid("setValues",[]);
});
},reset:function(jq){
return jq.each(function(){
var _3d=$(this).combotreegrid("options");
if(_3d.multiple){
$(this).combotreegrid("setValues",_3d.originalValue);
}else{
$(this).combotreegrid("setValue",_3d.originalValue);
}
});
}};
$.fn.combotreegrid.parseOptions=function(_3e){
var t=$(_3e);
return $.extend({},$.fn.combo.parseOptions(_3e),$.fn.treegrid.parseOptions(_3e),$.parser.parseOptions(_3e,["mode",{limitToGrid:"boolean"}]));
};
$.fn.combotreegrid.defaults=$.extend({},$.fn.combo.defaults,$.fn.treegrid.defaults,{editable:false,singleSelect:true,limitToGrid:false,unselectedValues:[],mappingRows:[],mode:"local",textField:null,keyHandler:{up:function(e){
},down:function(e){
},left:function(e){
},right:function(e){
},enter:function(e){
_2f(this);
},query:function(q,e){
_28(this,q);
}},inputEvents:$.extend({},$.fn.combo.defaults.inputEvents,{blur:function(e){
$.fn.combo.defaults.inputEvents.blur(e);
var _3f=e.data.target;
var _40=$(_3f).combotreegrid("options");
if(_40.limitToGrid){
_2f(_3f);
}
}}),filter:function(q,row){
var _41=$(this).combotreegrid("options");
return (row[_41.treeField]||"").toLowerCase().indexOf(q.toLowerCase())>=0;
}});
})(jQuery);

