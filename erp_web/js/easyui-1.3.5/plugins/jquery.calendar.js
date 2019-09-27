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
var _3=$.data(_2,"calendar").options;
var t=$(_2);
_3.fit?$.extend(_3,t._fit()):t._fit(false);
var _4=t.find(".calendar-header");
t._outerWidth(_3.width);
t._outerHeight(_3.height);
t.find(".calendar-body")._outerHeight(t.height()-_4._outerHeight());
};
function _5(_6){
$(_6).addClass("calendar").html("<div class=\"calendar-header\">"+"<div class=\"calendar-prevmonth\"></div>"+"<div class=\"calendar-nextmonth\"></div>"+"<div class=\"calendar-prevyear\"></div>"+"<div class=\"calendar-nextyear\"></div>"+"<div class=\"calendar-title\">"+"<span>Aprial 2010</span>"+"</div>"+"</div>"+"<div class=\"calendar-body\">"+"<div class=\"calendar-menu\">"+"<div class=\"calendar-menu-year-inner\">"+"<span class=\"calendar-menu-prev\"></span>"+"<span><input class=\"calendar-menu-year\" type=\"text\"></input></span>"+"<span class=\"calendar-menu-next\"></span>"+"</div>"+"<div class=\"calendar-menu-month-inner\">"+"</div>"+"</div>"+"</div>");
$(_6).find(".calendar-title span").hover(function(){
$(this).addClass("calendar-menu-hover");
},function(){
$(this).removeClass("calendar-menu-hover");
}).click(function(){
var _7=$(_6).find(".calendar-menu");
if(_7.is(":visible")){
_7.hide();
}else{
_14(_6);
}
});
$(".calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear",_6).hover(function(){
$(this).addClass("calendar-nav-hover");
},function(){
$(this).removeClass("calendar-nav-hover");
});
$(_6).find(".calendar-nextmonth").click(function(){
_9(_6,1);
});
$(_6).find(".calendar-prevmonth").click(function(){
_9(_6,-1);
});
$(_6).find(".calendar-nextyear").click(function(){
_f(_6,1);
});
$(_6).find(".calendar-prevyear").click(function(){
_f(_6,-1);
});
$(_6).bind("_resize",function(){
var _8=$.data(_6,"calendar").options;
if(_8.fit==true){
_1(_6);
}
return false;
});
};
function _9(_a,_b){
var _c=$.data(_a,"calendar").options;
_c.month+=_b;
if(_c.month>12){
_c.year++;
_c.month=1;
}else{
if(_c.month<1){
_c.year--;
_c.month=12;
}
}
_d(_a);
var _e=$(_a).find(".calendar-menu-month-inner");
_e.find("td.calendar-selected").removeClass("calendar-selected");
_e.find("td:eq("+(_c.month-1)+")").addClass("calendar-selected");
};
function _f(_10,_11){
var _12=$.data(_10,"calendar").options;
_12.year+=_11;
_d(_10);
var _13=$(_10).find(".calendar-menu-year");
_13.val(_12.year);
};
function _14(_15){
var _16=$.data(_15,"calendar").options;
$(_15).find(".calendar-menu").show();
if($(_15).find(".calendar-menu-month-inner").is(":empty")){
$(_15).find(".calendar-menu-month-inner").empty();
var t=$("<table></table>").appendTo($(_15).find(".calendar-menu-month-inner"));
var idx=0;
for(var i=0;i<3;i++){
var tr=$("<tr></tr>").appendTo(t);
for(var j=0;j<4;j++){
$("<td class=\"calendar-menu-month\"></td>").html(_16.months[idx++]).attr("abbr",idx).appendTo(tr);
}
}
$(_15).find(".calendar-menu-prev,.calendar-menu-next").hover(function(){
$(this).addClass("calendar-menu-hover");
},function(){
$(this).removeClass("calendar-menu-hover");
});
$(_15).find(".calendar-menu-next").click(function(){
var y=$(_15).find(".calendar-menu-year");
if(!isNaN(y.val())){
y.val(parseInt(y.val())+1);
}
});
$(_15).find(".calendar-menu-prev").click(function(){
var y=$(_15).find(".calendar-menu-year");
if(!isNaN(y.val())){
y.val(parseInt(y.val()-1));
}
});
$(_15).find(".calendar-menu-year").keypress(function(e){
if(e.keyCode==13){
_17();
}
});
$(_15).find(".calendar-menu-month").hover(function(){
$(this).addClass("calendar-menu-hover");
},function(){
$(this).removeClass("calendar-menu-hover");
}).click(function(){
var _18=$(_15).find(".calendar-menu");
_18.find(".calendar-selected").removeClass("calendar-selected");
$(this).addClass("calendar-selected");
_17();
});
}
function _17(){
var _19=$(_15).find(".calendar-menu");
var _1a=_19.find(".calendar-menu-year").val();
var _1b=_19.find(".calendar-selected").attr("abbr");
if(!isNaN(_1a)){
_16.year=parseInt(_1a);
_16.month=parseInt(_1b);
_d(_15);
}
_19.hide();
};
var _1c=$(_15).find(".calendar-body");
var _1d=$(_15).find(".calendar-menu");
var _1e=_1d.find(".calendar-menu-year-inner");
var _1f=_1d.find(".calendar-menu-month-inner");
_1e.find("input").val(_16.year).focus();
_1f.find("td.calendar-selected").removeClass("calendar-selected");
_1f.find("td:eq("+(_16.month-1)+")").addClass("calendar-selected");
_1d._outerWidth(_1c._outerWidth());
_1d._outerHeight(_1c._outerHeight());
_1f._outerHeight(_1d.height()-_1e._outerHeight());
};
function _20(_21,_22,_23){
var _24=$.data(_21,"calendar").options;
var _25=[];
var _26=new Date(_22,_23,0).getDate();
for(var i=1;i<=_26;i++){
_25.push([_22,_23,i]);
}
var _27=[],_28=[];
var _29=-1;
while(_25.length>0){
var _2a=_25.shift();
_28.push(_2a);
var day=new Date(_2a[0],_2a[1]-1,_2a[2]).getDay();
if(_29==day){
day=0;
}else{
if(day==(_24.firstDay==0?7:_24.firstDay)-1){
_27.push(_28);
_28=[];
}
}
_29=day;
}
if(_28.length){
_27.push(_28);
}
var _2b=_27[0];
if(_2b.length<7){
while(_2b.length<7){
var _2c=_2b[0];
var _2a=new Date(_2c[0],_2c[1]-1,_2c[2]-1);
_2b.unshift([_2a.getFullYear(),_2a.getMonth()+1,_2a.getDate()]);
}
}else{
var _2c=_2b[0];
var _28=[];
for(var i=1;i<=7;i++){
var _2a=new Date(_2c[0],_2c[1]-1,_2c[2]-i);
_28.unshift([_2a.getFullYear(),_2a.getMonth()+1,_2a.getDate()]);
}
_27.unshift(_28);
}
var _2d=_27[_27.length-1];
while(_2d.length<7){
var _2e=_2d[_2d.length-1];
var _2a=new Date(_2e[0],_2e[1]-1,_2e[2]+1);
_2d.push([_2a.getFullYear(),_2a.getMonth()+1,_2a.getDate()]);
}
if(_27.length<6){
var _2e=_2d[_2d.length-1];
var _28=[];
for(var i=1;i<=7;i++){
var _2a=new Date(_2e[0],_2e[1]-1,_2e[2]+i);
_28.push([_2a.getFullYear(),_2a.getMonth()+1,_2a.getDate()]);
}
_27.push(_28);
}
return _27;
};
function _d(_2f){
var _30=$.data(_2f,"calendar").options;
$(_2f).find(".calendar-title span").html(_30.months[_30.month-1]+" "+_30.year);
var _31=$(_2f).find("div.calendar-body");
_31.find(">table").remove();
var t=$("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><thead></thead><tbody></tbody></table>").prependTo(_31);
var tr=$("<tr></tr>").appendTo(t.find("thead"));
for(var i=_30.firstDay;i<_30.weeks.length;i++){
tr.append("<th>"+_30.weeks[i]+"</th>");
}
for(var i=0;i<_30.firstDay;i++){
tr.append("<th>"+_30.weeks[i]+"</th>");
}
var _32=_20(_2f,_30.year,_30.month);
for(var i=0;i<_32.length;i++){
var _33=_32[i];
var tr=$("<tr></tr>").appendTo(t.find("tbody"));
for(var j=0;j<_33.length;j++){
var day=_33[j];
$("<td class=\"calendar-day calendar-other-month\"></td>").attr("abbr",day[0]+","+day[1]+","+day[2]).html(day[2]).appendTo(tr);
}
}
t.find("td[abbr^=\""+_30.year+","+_30.month+"\"]").removeClass("calendar-other-month");
var now=new Date();
var _34=now.getFullYear()+","+(now.getMonth()+1)+","+now.getDate();
t.find("td[abbr=\""+_34+"\"]").addClass("calendar-today");
if(_30.current){
t.find(".calendar-selected").removeClass("calendar-selected");
var _35=_30.current.getFullYear()+","+(_30.current.getMonth()+1)+","+_30.current.getDate();
t.find("td[abbr=\""+_35+"\"]").addClass("calendar-selected");
}
var _36=6-_30.firstDay;
var _37=_36+1;
if(_36>=7){
_36-=7;
}
if(_37>=7){
_37-=7;
}
t.find("tr").find("td:eq("+_36+")").addClass("calendar-saturday");
t.find("tr").find("td:eq("+_37+")").addClass("calendar-sunday");
t.find("td").hover(function(){
$(this).addClass("calendar-hover");
},function(){
$(this).removeClass("calendar-hover");
}).click(function(){
t.find(".calendar-selected").removeClass("calendar-selected");
$(this).addClass("calendar-selected");
var _38=$(this).attr("abbr").split(",");
_30.current=new Date(_38[0],parseInt(_38[1])-1,_38[2]);
_30.onSelect.call(_2f,_30.current);
});
};
$.fn.calendar=function(_39,_3a){
if(typeof _39=="string"){
return $.fn.calendar.methods[_39](this,_3a);
}
_39=_39||{};
return this.each(function(){
var _3b=$.data(this,"calendar");
if(_3b){
$.extend(_3b.options,_39);
}else{
_3b=$.data(this,"calendar",{options:$.extend({},$.fn.calendar.defaults,$.fn.calendar.parseOptions(this),_39)});
_5(this);
}
if(_3b.options.border==false){
$(this).addClass("calendar-noborder");
}
_1(this);
_d(this);
$(this).find("div.calendar-menu").hide();
});
};
$.fn.calendar.methods={options:function(jq){
return $.data(jq[0],"calendar").options;
},resize:function(jq){
return jq.each(function(){
_1(this);
});
},moveTo:function(jq,_3c){
return jq.each(function(){
$(this).calendar({year:_3c.getFullYear(),month:_3c.getMonth()+1,current:_3c});
});
}};
$.fn.calendar.parseOptions=function(_3d){
var t=$(_3d);
return $.extend({},$.parser.parseOptions(_3d,["width","height",{firstDay:"number",fit:"boolean",border:"boolean"}]));
};
$.fn.calendar.defaults={width:180,height:180,fit:false,border:true,firstDay:0,weeks:["S","M","T","W","T","F","S"],months:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],year:new Date().getFullYear(),month:new Date().getMonth()+1,current:new Date(),onSelect:function(_3e){
}};
})(jQuery);

