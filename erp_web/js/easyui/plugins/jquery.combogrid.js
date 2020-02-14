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
var _3=$.data(_2,"combogrid");
var _4=_3.options;
var _5=_3.grid;
$(_2).addClass("combogrid-f").combo($.extend({},_4,{onShowPanel:function(){
_22(this,$(this).combogrid("getValues"),true);
var p=$(this).combogrid("panel");
var _6=p.outerHeight()-p.height();
var _7=p._size("minHeight");
var _8=p._size("maxHeight");
var dg=$(this).combogrid("grid");
dg.datagrid("resize",{width:"100%",height:(isNaN(parseInt(_4.panelHeight))?"auto":"100%"),minHeight:(_7?_7-_6:""),maxHeight:(_8?_8-_6:"")});
var _9=dg.datagrid("getSelected");
if(_9){
dg.datagrid("scrollTo",dg.datagrid("getRowIndex",_9));
}
_4.onShowPanel.call(this);
}}));
var _a=$(_2).combo("panel");
if(!_5){
_5=$("<table></table>").appendTo(_a);
_3.grid=_5;
}
_5.datagrid($.extend({},_4,{border:false,singleSelect:(!_4.multiple),onLoadSuccess:_b,onClickRow:_c,onSelect:_d("onSelect"),onUnselect:_d("onUnselect"),onSelectAll:_d("onSelectAll"),onUnselectAll:_d("onUnselectAll")}));
function _e(dg){
return $(dg).closest(".combo-panel").panel("options").comboTarget||_2;
};
function _b(_f){
var _10=_e(this);
var _11=$(_10).data("combogrid");
var _12=_11.options;
var _13=$(_10).combo("getValues");
_22(_10,_13,_11.remainText);
_12.onLoadSuccess.call(this,_f);
};
function _c(_14,row){
var _15=_e(this);
var _16=$(_15).data("combogrid");
var _17=_16.options;
_16.remainText=false;
_18.call(this);
if(!_17.multiple){
$(_15).combo("hidePanel");
}
_17.onClickRow.call(this,_14,row);
};
function _d(_19){
return function(_1a,row){
var _1b=_e(this);
var _1c=$(_1b).combogrid("options");
if(_19=="onUnselectAll"){
if(_1c.multiple){
_18.call(this);
}
}else{
_18.call(this);
}
_1c[_19].call(this,_1a,row);
};
};
function _18(){
var dg=$(this);
var _1d=_e(dg);
var _1e=$(_1d).data("combogrid");
var _1f=_1e.options;
var vv=$.map(dg.datagrid("getSelections"),function(row){
return row[_1f.idField];
});
vv=vv.concat(_1f.unselectedValues);
var _20=dg.data("datagrid").dc.body2;
var _21=_20.scrollTop();
_22(_1d,vv,_1e.remainText);
_20.scrollTop(_21);
};
};
function nav(_23,dir){
var _24=$.data(_23,"combogrid");
var _25=_24.options;
var _26=_24.grid;
var _27=_26.datagrid("getRows").length;
if(!_27){
return;
}
var tr=_25.finder.getTr(_26[0],null,"highlight");
if(!tr.length){
tr=_25.finder.getTr(_26[0],null,"selected");
}
var _28;
if(!tr.length){
_28=(dir=="next"?0:_27-1);
}else{
var _28=parseInt(tr.attr("datagrid-row-index"));
_28+=(dir=="next"?1:-1);
if(_28<0){
_28=_27-1;
}
if(_28>=_27){
_28=0;
}
}
_26.datagrid("highlightRow",_28);
if(_25.selectOnNavigation){
_24.remainText=false;
_26.datagrid("selectRow",_28);
}
};
function _22(_29,_2a,_2b){
var _2c=$.data(_29,"combogrid");
var _2d=_2c.options;
var _2e=_2c.grid;
var _2f=$(_29).combo("getValues");
var _30=$(_29).combo("options");
var _31=_30.onChange;
_30.onChange=function(){
};
var _32=_2e.datagrid("options");
var _33=_32.onSelect;
var _34=_32.onUnselectAll;
_32.onSelect=_32.onUnselectAll=function(){
};
if(!$.isArray(_2a)){
_2a=_2a.split(_2d.separator);
}
if(!_2d.multiple){
_2a=_2a.length?[_2a[0]]:[""];
}
var vv=$.map(_2a,function(_35){
return String(_35);
});
vv=$.grep(vv,function(v,_36){
return _36===$.inArray(v,vv);
});
var _37=$.grep(_2e.datagrid("getSelections"),function(row,_38){
return $.inArray(String(row[_2d.idField]),vv)>=0;
});
_2e.datagrid("clearSelections");
_2e.data("datagrid").selectedRows=_37;
var ss=[];
_2d.unselectedValues=[];
$.map(vv,function(v){
var _39=_2e.datagrid("getRowIndex",v);
if(_39>=0){
_2e.datagrid("selectRow",_39);
}else{
_2d.unselectedValues.push(v);
}
ss.push(_3a(v,_2e.datagrid("getRows"))||_3a(v,_37)||_3a(v,_2d.mappingRows)||v);
});
$(_29).combo("setValues",_2f);
_30.onChange=_31;
_32.onSelect=_33;
_32.onUnselectAll=_34;
if(!_2b){
var s=ss.join(_2d.separator);
if($(_29).combo("getText")!=s){
$(_29).combo("setText",s);
}
}
$(_29).combo("setValues",_2a);
function _3a(_3b,a){
var _3c=$.easyui.getArrayItem(a,_2d.idField,_3b);
return _3c?_3c[_2d.textField]:undefined;
};
};
function _3d(_3e,q){
var _3f=$.data(_3e,"combogrid");
var _40=_3f.options;
var _41=_3f.grid;
_3f.remainText=true;
var qq=_40.multiple?q.split(_40.separator):[q];
qq=$.grep(qq,function(q){
return $.trim(q)!="";
});
if(_40.mode=="remote"){
_42(qq);
_41.datagrid("load",$.extend({},_40.queryParams,{q:q}));
}else{
_41.datagrid("highlightRow",-1);
var _43=_41.datagrid("getRows");
var vv=[];
$.map(qq,function(q){
q=$.trim(q);
var _44=q;
_45(_40.mappingRows,q);
_45(_41.datagrid("getSelections"),q);
var _46=_45(_43,q);
if(_46>=0){
if(_40.reversed){
_41.datagrid("highlightRow",_46);
}
}else{
$.map(_43,function(row,i){
if(_40.filter.call(_3e,q,row)){
_41.datagrid("highlightRow",i);
}
});
}
});
_42(vv);
}
function _45(_47,q){
for(var i=0;i<_47.length;i++){
var row=_47[i];
if((row[_40.textField]||"").toLowerCase()==q.toLowerCase()){
vv.push(row[_40.idField]);
return i;
}
}
return -1;
};
function _42(vv){
if(!_40.reversed){
_22(_3e,vv,true);
}
};
};
function _48(_49){
var _4a=$.data(_49,"combogrid");
var _4b=_4a.options;
var _4c=_4a.grid;
var tr=_4b.finder.getTr(_4c[0],null,"highlight");
_4a.remainText=false;
if(tr.length){
var _4d=parseInt(tr.attr("datagrid-row-index"));
if(_4b.multiple){
if(tr.hasClass("datagrid-row-selected")){
_4c.datagrid("unselectRow",_4d);
}else{
_4c.datagrid("selectRow",_4d);
}
}else{
_4c.datagrid("selectRow",_4d);
}
}
var vv=[];
$.map(_4c.datagrid("getSelections"),function(row){
vv.push(row[_4b.idField]);
});
$.map(_4b.unselectedValues,function(v){
if($.easyui.indexOfArray(_4b.mappingRows,_4b.idField,v)>=0){
$.easyui.addArrayItem(vv,v);
}
});
$(_49).combogrid("setValues",vv);
if(!_4b.multiple){
$(_49).combogrid("hidePanel");
}
};
$.fn.combogrid=function(_4e,_4f){
if(typeof _4e=="string"){
var _50=$.fn.combogrid.methods[_4e];
if(_50){
return _50(this,_4f);
}else{
return this.combo(_4e,_4f);
}
}
_4e=_4e||{};
return this.each(function(){
var _51=$.data(this,"combogrid");
if(_51){
$.extend(_51.options,_4e);
}else{
_51=$.data(this,"combogrid",{options:$.extend({},$.fn.combogrid.defaults,$.fn.combogrid.parseOptions(this),_4e)});
}
_1(this);
});
};
$.fn.combogrid.methods={options:function(jq){
var _52=jq.combo("options");
return $.extend($.data(jq[0],"combogrid").options,{width:_52.width,height:_52.height,originalValue:_52.originalValue,disabled:_52.disabled,readonly:_52.readonly});
},cloneFrom:function(jq,_53){
return jq.each(function(){
$(this).combo("cloneFrom",_53);
$.data(this,"combogrid",{options:$.extend(true,{cloned:true},$(_53).combogrid("options")),combo:$(this).next(),panel:$(_53).combo("panel"),grid:$(_53).combogrid("grid")});
});
},grid:function(jq){
return $.data(jq[0],"combogrid").grid;
},setValues:function(jq,_54){
return jq.each(function(){
var _55=$(this).combogrid("options");
if($.isArray(_54)){
_54=$.map(_54,function(_56){
if(_56&&typeof _56=="object"){
$.easyui.addArrayItem(_55.mappingRows,_55.idField,_56);
return _56[_55.idField];
}else{
return _56;
}
});
}
_22(this,_54);
});
},setValue:function(jq,_57){
return jq.each(function(){
$(this).combogrid("setValues",$.isArray(_57)?_57:[_57]);
});
},clear:function(jq){
return jq.each(function(){
$(this).combogrid("setValues",[]);
});
},reset:function(jq){
return jq.each(function(){
var _58=$(this).combogrid("options");
if(_58.multiple){
$(this).combogrid("setValues",_58.originalValue);
}else{
$(this).combogrid("setValue",_58.originalValue);
}
});
}};
$.fn.combogrid.parseOptions=function(_59){
var t=$(_59);
return $.extend({},$.fn.combo.parseOptions(_59),$.fn.datagrid.parseOptions(_59),$.parser.parseOptions(_59,["idField","textField","mode"]));
};
$.fn.combogrid.defaults=$.extend({},$.fn.combo.defaults,$.fn.datagrid.defaults,{loadMsg:null,idField:null,textField:null,unselectedValues:[],mappingRows:[],mode:"local",keyHandler:{up:function(e){
nav(this,"prev");
e.preventDefault();
},down:function(e){
nav(this,"next");
e.preventDefault();
},left:function(e){
},right:function(e){
},enter:function(e){
_48(this);
},query:function(q,e){
_3d(this,q);
}},inputEvents:$.extend({},$.fn.combo.defaults.inputEvents,{blur:function(e){
$.fn.combo.defaults.inputEvents.blur(e);
var _5a=e.data.target;
var _5b=$(_5a).combogrid("options");
if(_5b.reversed){
$(_5a).combogrid("setValues",$(_5a).combogrid("getValues"));
}
}}),panelEvents:{mousedown:function(e){
}},filter:function(q,row){
var _5c=$(this).combogrid("options");
return (row[_5c.textField]||"").toLowerCase().indexOf(q.toLowerCase())>=0;
}});
})(jQuery);

