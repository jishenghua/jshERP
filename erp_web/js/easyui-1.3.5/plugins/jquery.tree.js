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
var _3=$(_2);
_3.addClass("tree");
return _3;
};
function _4(_5){
var _6=$.data(_5,"tree").options;
$(_5).unbind().bind("mouseover",function(e){
var tt=$(e.target);
var _7=tt.closest("div.tree-node");
if(!_7.length){
return;
}
_7.addClass("tree-node-hover");
if(tt.hasClass("tree-hit")){
if(tt.hasClass("tree-expanded")){
tt.addClass("tree-expanded-hover");
}else{
tt.addClass("tree-collapsed-hover");
}
}
e.stopPropagation();
}).bind("mouseout",function(e){
var tt=$(e.target);
var _8=tt.closest("div.tree-node");
if(!_8.length){
return;
}
_8.removeClass("tree-node-hover");
if(tt.hasClass("tree-hit")){
if(tt.hasClass("tree-expanded")){
tt.removeClass("tree-expanded-hover");
}else{
tt.removeClass("tree-collapsed-hover");
}
}
e.stopPropagation();
}).bind("click",function(e){
var tt=$(e.target);
var _9=tt.closest("div.tree-node");
if(!_9.length){
return;
}
if(tt.hasClass("tree-hit")){
_7e(_5,_9[0]);
return false;
}else{
if(tt.hasClass("tree-checkbox")){
_32(_5,_9[0],!tt.hasClass("tree-checkbox1"));
return false;
}else{
_d6(_5,_9[0]);
_6.onClick.call(_5,_c(_5,_9[0]));
}
}
e.stopPropagation();
}).bind("dblclick",function(e){
var _a=$(e.target).closest("div.tree-node");
if(!_a.length){
return;
}
_d6(_5,_a[0]);
_6.onDblClick.call(_5,_c(_5,_a[0]));
e.stopPropagation();
}).bind("contextmenu",function(e){
var _b=$(e.target).closest("div.tree-node");
if(!_b.length){
return;
}
_6.onContextMenu.call(_5,e,_c(_5,_b[0]));
e.stopPropagation();
});
};
function _d(_e){
var _f=$.data(_e,"tree").options;
_f.dnd=false;
var _10=$(_e).find("div.tree-node");
_10.draggable("disable");
_10.css("cursor","pointer");
};
function _11(_12){
var _13=$.data(_12,"tree");
var _14=_13.options;
var _15=_13.tree;
_13.disabledNodes=[];
_14.dnd=true;
_15.find("div.tree-node").draggable({disabled:false,revert:true,cursor:"pointer",proxy:function(_16){
var p=$("<div class=\"tree-node-proxy\"></div>").appendTo("body");
p.html("<span class=\"tree-dnd-icon tree-dnd-no\">&nbsp;</span>"+$(_16).find(".tree-title").html());
p.hide();
return p;
},deltaX:15,deltaY:15,onBeforeDrag:function(e){
if(_14.onBeforeDrag.call(_12,_c(_12,this))==false){
return false;
}
if($(e.target).hasClass("tree-hit")||$(e.target).hasClass("tree-checkbox")){
return false;
}
if(e.which!=1){
return false;
}
$(this).next("ul").find("div.tree-node").droppable({accept:"no-accept"});
var _17=$(this).find("span.tree-indent");
if(_17.length){
e.data.offsetWidth-=_17.length*_17.width();
}
},onStartDrag:function(){
$(this).draggable("proxy").css({left:-10000,top:-10000});
_14.onStartDrag.call(_12,_c(_12,this));
var _18=_c(_12,this);
if(_18.id==undefined){
_18.id="easyui_tree_node_id_temp";
_54(_12,_18);
}
_13.draggingNodeId=_18.id;
},onDrag:function(e){
var x1=e.pageX,y1=e.pageY,x2=e.data.startX,y2=e.data.startY;
var d=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
if(d>3){
$(this).draggable("proxy").show();
}
this.pageY=e.pageY;
},onStopDrag:function(){
$(this).next("ul").find("div.tree-node").droppable({accept:"div.tree-node"});
for(var i=0;i<_13.disabledNodes.length;i++){
$(_13.disabledNodes[i]).droppable("enable");
}
_13.disabledNodes=[];
var _19=_c9(_12,_13.draggingNodeId);
if(_19&&_19.id=="easyui_tree_node_id_temp"){
_19.id="";
_54(_12,_19);
}
_14.onStopDrag.call(_12,_19);
}}).droppable({accept:"div.tree-node",onDragEnter:function(e,_1a){
if(_14.onDragEnter.call(_12,this,_c(_12,_1a))==false){
_1b(_1a,false);
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
$(this).droppable("disable");
_13.disabledNodes.push(this);
}
},onDragOver:function(e,_1c){
if($(this).droppable("options").disabled){
return;
}
var _1d=_1c.pageY;
var top=$(this).offset().top;
var _1e=top+$(this).outerHeight();
_1b(_1c,true);
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
if(_1d>top+(_1e-top)/2){
if(_1e-_1d<5){
$(this).addClass("tree-node-bottom");
}else{
$(this).addClass("tree-node-append");
}
}else{
if(_1d-top<5){
$(this).addClass("tree-node-top");
}else{
$(this).addClass("tree-node-append");
}
}
if(_14.onDragOver.call(_12,this,_c(_12,_1c))==false){
_1b(_1c,false);
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
$(this).droppable("disable");
_13.disabledNodes.push(this);
}
},onDragLeave:function(e,_1f){
_1b(_1f,false);
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
_14.onDragLeave.call(_12,this,_c(_12,_1f));
},onDrop:function(e,_20){
var _21=this;
var _22,_23;
if($(this).hasClass("tree-node-append")){
_22=_24;
_23="append";
}else{
_22=_25;
_23=$(this).hasClass("tree-node-top")?"top":"bottom";
}
if(_14.onBeforeDrop.call(_12,_21,_c2(_12,_20),_23)==false){
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
return;
}
_22(_20,_21,_23);
$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
}});
function _1b(_26,_27){
var _28=$(_26).draggable("proxy").find("span.tree-dnd-icon");
_28.removeClass("tree-dnd-yes tree-dnd-no").addClass(_27?"tree-dnd-yes":"tree-dnd-no");
};
function _24(_29,_2a){
if(_c(_12,_2a).state=="closed"){
_72(_12,_2a,function(){
_2b();
});
}else{
_2b();
}
function _2b(){
var _2c=$(_12).tree("pop",_29);
$(_12).tree("append",{parent:_2a,data:[_2c]});
_14.onDrop.call(_12,_2a,_2c,"append");
};
};
function _25(_2d,_2e,_2f){
var _30={};
if(_2f=="top"){
_30.before=_2e;
}else{
_30.after=_2e;
}
var _31=$(_12).tree("pop",_2d);
_30.data=_31;
$(_12).tree("insert",_30);
_14.onDrop.call(_12,_2e,_31,_2f);
};
};
function _32(_33,_34,_35){
var _36=$.data(_33,"tree").options;
if(!_36.checkbox){
return;
}
var _37=_c(_33,_34);
if(_36.onBeforeCheck.call(_33,_37,_35)==false){
return;
}
var _38=$(_34);
var ck=_38.find(".tree-checkbox");
ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
if(_35){
ck.addClass("tree-checkbox1");
}else{
ck.addClass("tree-checkbox0");
}
if(_36.cascadeCheck){
_39(_38);
_3a(_38);
}
_36.onCheck.call(_33,_37,_35);
function _3a(_3b){
var _3c=_3b.next().find(".tree-checkbox");
_3c.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
if(_3b.find(".tree-checkbox").hasClass("tree-checkbox1")){
_3c.addClass("tree-checkbox1");
}else{
_3c.addClass("tree-checkbox0");
}
};
function _39(_3d){
var _3e=_89(_33,_3d[0]);
if(_3e){
var ck=$(_3e.target).find(".tree-checkbox");
ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
if(_3f(_3d)){
ck.addClass("tree-checkbox1");
}else{
if(_40(_3d)){
ck.addClass("tree-checkbox0");
}else{
ck.addClass("tree-checkbox2");
}
}
_39($(_3e.target));
}
function _3f(n){
var ck=n.find(".tree-checkbox");
if(ck.hasClass("tree-checkbox0")||ck.hasClass("tree-checkbox2")){
return false;
}
var b=true;
n.parent().siblings().each(function(){
if(!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox1")){
b=false;
}
});
return b;
};
function _40(n){
var ck=n.find(".tree-checkbox");
if(ck.hasClass("tree-checkbox1")||ck.hasClass("tree-checkbox2")){
return false;
}
var b=true;
n.parent().siblings().each(function(){
if(!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox0")){
b=false;
}
});
return b;
};
};
};
function _41(_42,_43){
var _44=$.data(_42,"tree").options;
if(!_44.checkbox){
return;
}
var _45=$(_43);
if(_46(_42,_43)){
var ck=_45.find(".tree-checkbox");
if(ck.length){
if(ck.hasClass("tree-checkbox1")){
_32(_42,_43,true);
}else{
_32(_42,_43,false);
}
}else{
if(_44.onlyLeafCheck){
$("<span class=\"tree-checkbox tree-checkbox0\"></span>").insertBefore(_45.find(".tree-title"));
}
}
}else{
var ck=_45.find(".tree-checkbox");
if(_44.onlyLeafCheck){
ck.remove();
}else{
if(ck.hasClass("tree-checkbox1")){
_32(_42,_43,true);
}else{
if(ck.hasClass("tree-checkbox2")){
var _47=true;
var _48=true;
var _49=_4a(_42,_43);
for(var i=0;i<_49.length;i++){
if(_49[i].checked){
_48=false;
}else{
_47=false;
}
}
if(_47){
_32(_42,_43,true);
}
if(_48){
_32(_42,_43,false);
}
}
}
}
}
};
function _4b(_4c,ul,_4d,_4e){
var _4f=$.data(_4c,"tree");
var _50=_4f.options;
var _51=$(ul).prevAll("div.tree-node:first");
_4d=_50.loadFilter.call(_4c,_4d,_51[0]);
var _52=_53(_4c,"domId",_51.attr("id"));
if(!_4e){
_52?_52.children=_4d:_4f.data=_4d;
$(ul).empty();
}else{
if(_52){
_52.children?_52.children=_52.children.concat(_4d):_52.children=_4d;
}else{
_4f.data=_4f.data.concat(_4d);
}
}
_50.view.render.call(_50.view,_4c,ul,_4d);
if(_50.dnd){
_11(_4c);
}
if(_52){
_54(_4c,_52);
}
var _55=[];
var _56=[];
for(var i=0;i<_4d.length;i++){
var _57=_4d[i];
if(!_57.checked){
_55.push(_57);
}
}
_58(_4d,function(_59){
if(_59.checked){
_56.push(_59);
}
});
if(_55.length){
_32(_4c,$("#"+_55[0].domId)[0],false);
}
for(var i=0;i<_56.length;i++){
_32(_4c,$("#"+_56[i].domId)[0],true);
}
setTimeout(function(){
_5a(_4c,_4c);
},0);
_50.onLoadSuccess.call(_4c,_52,_4d);
};
function _5a(_5b,ul,_5c){
var _5d=$.data(_5b,"tree").options;
if(_5d.lines){
$(_5b).addClass("tree-lines");
}else{
$(_5b).removeClass("tree-lines");
return;
}
if(!_5c){
_5c=true;
$(_5b).find("span.tree-indent").removeClass("tree-line tree-join tree-joinbottom");
$(_5b).find("div.tree-node").removeClass("tree-node-last tree-root-first tree-root-one");
var _5e=$(_5b).tree("getRoots");
if(_5e.length>1){
$(_5e[0].target).addClass("tree-root-first");
}else{
if(_5e.length==1){
$(_5e[0].target).addClass("tree-root-one");
}
}
}
$(ul).children("li").each(function(){
var _5f=$(this).children("div.tree-node");
var ul=_5f.next("ul");
if(ul.length){
if($(this).next().length){
_60(_5f);
}
_5a(_5b,ul,_5c);
}else{
_61(_5f);
}
});
var _62=$(ul).children("li:last").children("div.tree-node").addClass("tree-node-last");
_62.children("span.tree-join").removeClass("tree-join").addClass("tree-joinbottom");
function _61(_63,_64){
var _65=_63.find("span.tree-icon");
_65.prev("span.tree-indent").addClass("tree-join");
};
function _60(_66){
var _67=_66.find("span.tree-indent, span.tree-hit").length;
_66.next().find("div.tree-node").each(function(){
$(this).children("span:eq("+(_67-1)+")").addClass("tree-line");
});
};
};
function _68(_69,ul,_6a,_6b){
var _6c=$.data(_69,"tree").options;
_6a=_6a||{};
var _6d=null;
if(_69!=ul){
var _6e=$(ul).prev();
_6d=_c(_69,_6e[0]);
}
if(_6c.onBeforeLoad.call(_69,_6d,_6a)==false){
return;
}
var _6f=$(ul).prev().children("span.tree-folder");
_6f.addClass("tree-loading");
var _70=_6c.loader.call(_69,_6a,function(_71){
_6f.removeClass("tree-loading");
_4b(_69,ul,_71);
if(_6b){
_6b();
}
},function(){
_6f.removeClass("tree-loading");
_6c.onLoadError.apply(_69,arguments);
if(_6b){
_6b();
}
});
if(_70==false){
_6f.removeClass("tree-loading");
}
};
function _72(_73,_74,_75){
var _76=$.data(_73,"tree").options;
var hit=$(_74).children("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-expanded")){
return;
}
var _77=_c(_73,_74);
if(_76.onBeforeExpand.call(_73,_77)==false){
return;
}
hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
hit.next().addClass("tree-folder-open");
var ul=$(_74).next();
if(ul.length){
if(_76.animate){
ul.slideDown("normal",function(){
_77.state="open";
_76.onExpand.call(_73,_77);
if(_75){
_75();
}
});
}else{
ul.css("display","block");
_77.state="open";
_76.onExpand.call(_73,_77);
if(_75){
_75();
}
}
}else{
var _78=$("<ul style=\"display:none\"></ul>").insertAfter(_74);
_68(_73,_78[0],{id:_77.id},function(){
if(_78.is(":empty")){
_78.remove();
}
if(_76.animate){
_78.slideDown("normal",function(){
_77.state="open";
_76.onExpand.call(_73,_77);
if(_75){
_75();
}
});
}else{
_78.css("display","block");
_77.state="open";
_76.onExpand.call(_73,_77);
if(_75){
_75();
}
}
});
}
};
function _79(_7a,_7b){
var _7c=$.data(_7a,"tree").options;
var hit=$(_7b).children("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-collapsed")){
return;
}
var _7d=_c(_7a,_7b);
if(_7c.onBeforeCollapse.call(_7a,_7d)==false){
return;
}
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
hit.next().removeClass("tree-folder-open");
var ul=$(_7b).next();
if(_7c.animate){
ul.slideUp("normal",function(){
_7d.state="closed";
_7c.onCollapse.call(_7a,_7d);
});
}else{
ul.css("display","none");
_7d.state="closed";
_7c.onCollapse.call(_7a,_7d);
}
};
function _7e(_7f,_80){
var hit=$(_80).children("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-expanded")){
_79(_7f,_80);
}else{
_72(_7f,_80);
}
};
function _81(_82,_83){
var _84=_4a(_82,_83);
if(_83){
_84.unshift(_c(_82,_83));
}
for(var i=0;i<_84.length;i++){
_72(_82,_84[i].target);
}
};
function _85(_86,_87){
var _88=[];
var p=_89(_86,_87);
while(p){
_88.unshift(p);
p=_89(_86,p.target);
}
for(var i=0;i<_88.length;i++){
_72(_86,_88[i].target);
}
};
function _8a(_8b,_8c){
var c=$(_8b).parent();
while(c[0].tagName!="BODY"&&c.css("overflow-y")!="auto"){
c=c.parent();
}
var n=$(_8c);
var _8d=n.offset().top;
if(c[0].tagName!="BODY"){
var _8e=c.offset().top;
if(_8d<_8e){
c.scrollTop(c.scrollTop()+_8d-_8e);
}else{
if(_8d+n.outerHeight()>_8e+c.outerHeight()-18){
c.scrollTop(c.scrollTop()+_8d+n.outerHeight()-_8e-c.outerHeight()+18);
}
}
}else{
c.scrollTop(_8d);
}
};
function _8f(_90,_91){
var _92=_4a(_90,_91);
if(_91){
_92.unshift(_c(_90,_91));
}
for(var i=0;i<_92.length;i++){
_79(_90,_92[i].target);
}
};
function _93(_94,_95){
var _96=$(_95.parent);
var _97=_95.data;
if(!_97){
return;
}
_97=$.isArray(_97)?_97:[_97];
if(!_97.length){
return;
}
var ul;
if(_96.length==0){
ul=$(_94);
}else{
if(_46(_94,_96[0])){
var _98=_96.find("span.tree-icon");
_98.removeClass("tree-file").addClass("tree-folder tree-folder-open");
var hit=$("<span class=\"tree-hit tree-expanded\"></span>").insertBefore(_98);
if(hit.prev().length){
hit.prev().remove();
}
}
ul=_96.next();
if(!ul.length){
ul=$("<ul></ul>").insertAfter(_96);
}
}
_4b(_94,ul[0],_97,true);
_41(_94,ul.prev());
};
function _99(_9a,_9b){
var ref=_9b.before||_9b.after;
var _9c=_89(_9a,ref);
var _9d=_9b.data;
if(!_9d){
return;
}
_9d=$.isArray(_9d)?_9d:[_9d];
if(!_9d.length){
return;
}
_93(_9a,{parent:(_9c?_9c.target:null),data:_9d});
var li=$();
for(var i=0;i<_9d.length;i++){
li=li.add($("#"+_9d[i].domId).parent());
}
if(_9b.before){
li.insertBefore($(ref).parent());
}else{
li.insertAfter($(ref).parent());
}
};
function _9e(_9f,_a0){
var _a1=del(_a0);
$(_a0).parent().remove();
if(_a1){
if(!_a1.children||!_a1.children.length){
var _a2=$(_a1.target);
_a2.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
_a2.find(".tree-hit").remove();
$("<span class=\"tree-indent\"></span>").prependTo(_a2);
_a2.next().remove();
}
_54(_9f,_a1);
_41(_9f,_a1.target);
}
_5a(_9f,_9f);
function del(_a3){
var id=$(_a3).attr("id");
var _a4=_89(_9f,_a3);
var cc=_a4?_a4.children:$.data(_9f,"tree").data;
for(var i=0;i<cc.length;i++){
if(cc[i].domId==id){
cc.splice(i,1);
break;
}
}
return _a4;
};
};
function _54(_a5,_a6){
var _a7=$.data(_a5,"tree").options;
var _a8=$(_a6.target);
var _a9=_c(_a5,_a6.target);
var _aa=_a9.checked;
if(_a9.iconCls){
_a8.find(".tree-icon").removeClass(_a9.iconCls);
}
$.extend(_a9,_a6);
_a8.find(".tree-title").html(_a7.formatter.call(_a5,_a9));
if(_a9.iconCls){
_a8.find(".tree-icon").addClass(_a9.iconCls);
}
if(_aa!=_a9.checked){
_32(_a5,_a6.target,_a9.checked);
}
};
function _ab(_ac){
var _ad=_ae(_ac);
return _ad.length?_ad[0]:null;
};
function _ae(_af){
var _b0=$.data(_af,"tree").data;
for(var i=0;i<_b0.length;i++){
_b1(_b0[i]);
}
return _b0;
};
function _4a(_b2,_b3){
var _b4=[];
var n=_c(_b2,_b3);
var _b5=n?n.children:$.data(_b2,"tree").data;
_58(_b5,function(_b6){
_b4.push(_b1(_b6));
});
return _b4;
};
function _89(_b7,_b8){
var p=$(_b8).closest("ul").prevAll("div.tree-node:first");
return _c(_b7,p[0]);
};
function _b9(_ba,_bb){
_bb=_bb||"checked";
if(!$.isArray(_bb)){
_bb=[_bb];
}
var _bc=[];
for(var i=0;i<_bb.length;i++){
var s=_bb[i];
if(s=="checked"){
_bc.push("span.tree-checkbox1");
}else{
if(s=="unchecked"){
_bc.push("span.tree-checkbox0");
}else{
if(s=="indeterminate"){
_bc.push("span.tree-checkbox2");
}
}
}
}
var _bd=[];
$(_ba).find(_bc.join(",")).each(function(){
var _be=$(this).parent();
_bd.push(_c(_ba,_be[0]));
});
return _bd;
};
function _bf(_c0){
var _c1=$(_c0).find("div.tree-node-selected");
return _c1.length?_c(_c0,_c1[0]):null;
};
function _c2(_c3,_c4){
var _c5=_c(_c3,_c4);
if(_c5&&_c5.children){
_58(_c5.children,function(_c6){
_b1(_c6);
});
}
return _c5;
};
function _c(_c7,_c8){
return _53(_c7,"domId",$(_c8).attr("id"));
};
function _c9(_ca,id){
return _53(_ca,"id",id);
};
function _53(_cb,_cc,_cd){
var _ce=$.data(_cb,"tree").data;
var _cf=null;
_58(_ce,function(_d0){
if(_d0[_cc]==_cd){
_cf=_b1(_d0);
return false;
}
});
return _cf;
};
function _b1(_d1){
var d=$("#"+_d1.domId);
_d1.target=d[0];
_d1.checked=d.find(".tree-checkbox").hasClass("tree-checkbox1");
return _d1;
};
function _58(_d2,_d3){
var _d4=[];
for(var i=0;i<_d2.length;i++){
_d4.push(_d2[i]);
}
while(_d4.length){
var _d5=_d4.shift();
if(_d3(_d5)==false){
return;
}
if(_d5.children){
for(var i=_d5.children.length-1;i>=0;i--){
_d4.unshift(_d5.children[i]);
}
}
}
};
function _d6(_d7,_d8){
var _d9=$.data(_d7,"tree").options;
var _da=_c(_d7,_d8);
if(_d9.onBeforeSelect.call(_d7,_da)==false){
return;
}
$(_d7).find("div.tree-node-selected").removeClass("tree-node-selected");
$(_d8).addClass("tree-node-selected");
_d9.onSelect.call(_d7,_da);
};
function _46(_db,_dc){
return $(_dc).children("span.tree-hit").length==0;
};
function _dd(_de,_df){
var _e0=$.data(_de,"tree").options;
var _e1=_c(_de,_df);
if(_e0.onBeforeEdit.call(_de,_e1)==false){
return;
}
$(_df).css("position","relative");
var nt=$(_df).find(".tree-title");
var _e2=nt.outerWidth();
nt.empty();
var _e3=$("<input class=\"tree-editor\">").appendTo(nt);
_e3.val(_e1.text).focus();
_e3.width(_e2+20);
_e3.height(document.compatMode=="CSS1Compat"?(18-(_e3.outerHeight()-_e3.height())):18);
_e3.bind("click",function(e){
return false;
}).bind("mousedown",function(e){
e.stopPropagation();
}).bind("mousemove",function(e){
e.stopPropagation();
}).bind("keydown",function(e){
if(e.keyCode==13){
_e4(_de,_df);
return false;
}else{
if(e.keyCode==27){
_ea(_de,_df);
return false;
}
}
}).bind("blur",function(e){
e.stopPropagation();
_e4(_de,_df);
});
};
function _e4(_e5,_e6){
var _e7=$.data(_e5,"tree").options;
$(_e6).css("position","");
var _e8=$(_e6).find("input.tree-editor");
var val=_e8.val();
_e8.remove();
var _e9=_c(_e5,_e6);
_e9.text=val;
_54(_e5,_e9);
_e7.onAfterEdit.call(_e5,_e9);
};
function _ea(_eb,_ec){
var _ed=$.data(_eb,"tree").options;
$(_ec).css("position","");
$(_ec).find("input.tree-editor").remove();
var _ee=_c(_eb,_ec);
_54(_eb,_ee);
_ed.onCancelEdit.call(_eb,_ee);
};
$.fn.tree=function(_ef,_f0){
if(typeof _ef=="string"){
return $.fn.tree.methods[_ef](this,_f0);
}
var _ef=_ef||{};
return this.each(function(){
var _f1=$.data(this,"tree");
var _f2;
if(_f1){
_f2=$.extend(_f1.options,_ef);
_f1.options=_f2;
}else{
_f2=$.extend({},$.fn.tree.defaults,$.fn.tree.parseOptions(this),_ef);
$.data(this,"tree",{options:_f2,tree:_1(this),data:[]});
var _f3=$.fn.tree.parseData(this);
if(_f3.length){
_4b(this,this,_f3);
}
}
_4(this);
if(_f2.data){
_4b(this,this,_f2.data);
}
_68(this,this);
});
};
$.fn.tree.methods={options:function(jq){
return $.data(jq[0],"tree").options;
},loadData:function(jq,_f4){
return jq.each(function(){
_4b(this,this,_f4);
});
},getNode:function(jq,_f5){
return _c(jq[0],_f5);
},getData:function(jq,_f6){
return _c2(jq[0],_f6);
},reload:function(jq,_f7){
return jq.each(function(){
if(_f7){
var _f8=$(_f7);
var hit=_f8.children("span.tree-hit");
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
_f8.next().remove();
_72(this,_f7);
}else{
$(this).empty();
_68(this,this);
}
});
},getRoot:function(jq){
return _ab(jq[0]);
},getRoots:function(jq){
return _ae(jq[0]);
},getParent:function(jq,_f9){
return _89(jq[0],_f9);
},getChildren:function(jq,_fa){
return _4a(jq[0],_fa);
},getChecked:function(jq,_fb){
return _b9(jq[0],_fb);
},getSelected:function(jq){
return _bf(jq[0]);
},isLeaf:function(jq,_fc){
return _46(jq[0],_fc);
},find:function(jq,id){
return _c9(jq[0],id);
},select:function(jq,_fd){
return jq.each(function(){
_d6(this,_fd);
});
},check:function(jq,_fe){
return jq.each(function(){
_32(this,_fe,true);
});
},uncheck:function(jq,_ff){
return jq.each(function(){
_32(this,_ff,false);
});
},collapse:function(jq,_100){
return jq.each(function(){
_79(this,_100);
});
},expand:function(jq,_101){
return jq.each(function(){
_72(this,_101);
});
},collapseAll:function(jq,_102){
return jq.each(function(){
_8f(this,_102);
});
},expandAll:function(jq,_103){
return jq.each(function(){
_81(this,_103);
});
},expandTo:function(jq,_104){
return jq.each(function(){
_85(this,_104);
});
},scrollTo:function(jq,_105){
return jq.each(function(){
_8a(this,_105);
});
},toggle:function(jq,_106){
return jq.each(function(){
_7e(this,_106);
});
},append:function(jq,_107){
return jq.each(function(){
_93(this,_107);
});
},insert:function(jq,_108){
return jq.each(function(){
_99(this,_108);
});
},remove:function(jq,_109){
return jq.each(function(){
_9e(this,_109);
});
},pop:function(jq,_10a){
var node=jq.tree("getData",_10a);
jq.tree("remove",_10a);
return node;
},update:function(jq,_10b){
return jq.each(function(){
_54(this,_10b);
});
},enableDnd:function(jq){
return jq.each(function(){
_11(this);
});
},disableDnd:function(jq){
return jq.each(function(){
_d(this);
});
},beginEdit:function(jq,_10c){
return jq.each(function(){
_dd(this,_10c);
});
},endEdit:function(jq,_10d){
return jq.each(function(){
_e4(this,_10d);
});
},cancelEdit:function(jq,_10e){
return jq.each(function(){
_ea(this,_10e);
});
}};
$.fn.tree.parseOptions=function(_10f){
var t=$(_10f);
return $.extend({},$.parser.parseOptions(_10f,["url","method",{checkbox:"boolean",cascadeCheck:"boolean",onlyLeafCheck:"boolean"},{animate:"boolean",lines:"boolean",dnd:"boolean"}]));
};
$.fn.tree.parseData=function(_110){
var data=[];
_111(data,$(_110));
return data;
function _111(aa,tree){
tree.children("li").each(function(){
var node=$(this);
var item=$.extend({},$.parser.parseOptions(this,["id","iconCls","state"]),{checked:(node.attr("checked")?true:undefined)});
item.text=node.children("span").html();
if(!item.text){
item.text=node.html();
}
var _112=node.children("ul");
if(_112.length){
item.children=[];
_111(item.children,_112);
}
aa.push(item);
});
};
};
var _113=1;
var _114={render:function(_115,ul,data){
var opts=$.data(_115,"tree").options;
var _116=$(ul).prev("div.tree-node").find("span.tree-indent, span.tree-hit").length;
var cc=_117(_116,data);
$(ul).append(cc.join(""));
function _117(_118,_119){
var cc=[];
for(var i=0;i<_119.length;i++){
var item=_119[i];
if(item.state!="open"&&item.state!="closed"){
item.state="open";
}
item.domId="_easyui_tree_"+_113++;
cc.push("<li>");
cc.push("<div id=\""+item.domId+"\" class=\"tree-node\">");
for(var j=0;j<_118;j++){
cc.push("<span class=\"tree-indent\"></span>");
}
if(item.state=="closed"){
cc.push("<span class=\"tree-hit tree-collapsed\"></span>");
cc.push("<span class=\"tree-icon tree-folder "+(item.iconCls?item.iconCls:"")+"\"></span>");
}else{
if(item.children&&item.children.length){
cc.push("<span class=\"tree-hit tree-expanded\"></span>");
cc.push("<span class=\"tree-icon tree-folder tree-folder-open "+(item.iconCls?item.iconCls:"")+"\"></span>");
}else{
cc.push("<span class=\"tree-indent\"></span>");
cc.push("<span class=\"tree-icon tree-file "+(item.iconCls?item.iconCls:"")+"\"></span>");
}
}
if(opts.checkbox){
if((!opts.onlyLeafCheck)||(opts.onlyLeafCheck&&(!item.children||!item.children.length))){
cc.push("<span class=\"tree-checkbox tree-checkbox0\"></span>");
}
}
cc.push("<span class=\"tree-title\">"+opts.formatter.call(_115,item)+"</span>");
cc.push("</div>");
if(item.children&&item.children.length){
var tmp=_117(_118+1,item.children);
cc.push("<ul style=\"display:"+(item.state=="closed"?"none":"block")+"\">");
cc=cc.concat(tmp);
cc.push("</ul>");
}
cc.push("</li>");
}
return cc;
};
}};
$.fn.tree.defaults={url:null,method:"post",animate:false,checkbox:false,cascadeCheck:true,onlyLeafCheck:false,lines:false,dnd:false,data:null,formatter:function(node){
return node.text;
},loader:function(_11a,_11b,_11c){
var opts=$(this).tree("options");
if(!opts.url){
return false;
}
$.ajax({type:opts.method,url:opts.url,data:_11a,dataType:"json",success:function(data){
_11b(data);
},error:function(){
_11c.apply(this,arguments);
}});
},loadFilter:function(data,_11d){
return data;
},view:_114,onBeforeLoad:function(node,_11e){
},onLoadSuccess:function(node,data){
},onLoadError:function(){
},onClick:function(node){
},onDblClick:function(node){
},onBeforeExpand:function(node){
},onExpand:function(node){
},onBeforeCollapse:function(node){
},onCollapse:function(node){
},onBeforeCheck:function(node,_11f){
},onCheck:function(node,_120){
},onBeforeSelect:function(node){
},onSelect:function(node){
},onContextMenu:function(e,node){
},onBeforeDrag:function(node){
},onStartDrag:function(node){
},onStopDrag:function(node){
},onDragEnter:function(_121,_122){
},onDragOver:function(_123,_124){
},onDragLeave:function(_125,_126){
},onBeforeDrop:function(_127,_128,_129){
},onDrop:function(_12a,_12b,_12c){
},onBeforeEdit:function(node){
},onAfterEdit:function(node){
},onCancelEdit:function(node){
}};
})(jQuery);

