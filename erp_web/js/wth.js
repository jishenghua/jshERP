// JavaScript Document
function switchTableRow(conid,evenRowClassName,hoverRowClassName)
{
	$(conid + " tr:even").addClass(evenRowClassName);
	$(conid + " tr").hover(function()
									{
										$(this).addClass(hoverRowClassName);
									},
									function()
									{
										$(this).removeClass(hoverRowClassName);
									});
}
function switchTableRowView(conid,evenRowClassName)
{
	$(conid + " tr:odd").addClass(evenRowClassName);
}
function selectAll(selectAll,selectName)
{
	var checkboxName = document.getElementsByName(selectName);
    for (var i=0; i<checkboxName.length; i++)
    checkboxName[i].checked = selectAll.checked;
}
function tabSwitch(conid,onid,event)
{
	var tablist = document.getElementById(conid).getElementsByTagName("a");
	for(var i=0;i<tablist.length;i++)
	{
		tablist[i].removeAttribute("id");
	}
	var event = window.event || event;
	var obj = event.srcElement || event.target;
	obj.setAttribute("id",onid);
}
function dialogList(dT,dW,dH,dData,dstyle,tipstyle,obj)
{
	var headH = 61;
	var webH = document.documentElement.clientHeight;
	var webSH = document.documentElement.scrollHeight;
	var webW = document.documentElement.clientWidth;
	if(dstyle == 0)
	{
		if(tipstyle == 0)
		{
			$("body").append("<div id='dialog_bg'></div><div id='dialog'><div id='dialog_title'>"+dT+"<div id='dialog_close'></div></div><table id='dialogFrame' width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td scope='row' align='center' valign='middle' style='color:#F00'><img src='../images/bg_tipfailth.png' width='15' height='13' />"+dData+"</td></tr></table><div id='dialog_btnlist'><input class='inputstyle' type='button' value='确 定'/></div></div>");	
			$("#dialog img").css({margin:"0 5px 0 0"});
		}
		else if(tipstyle == 1)
		{
			$("body").append("<div id='dialog_bg'></div><div id='dialog'><div id='dialog_title'>"+dT+"<div id='dialog_close'></div></div><table id='dialogFrame' width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td scope='row' align='center' valign='middle'><img src='../images/bg_tipsuccess.png' width='15' height='13' />"+dData+"</td></tr></table><div id='dialog_btnlist'><input class='inputstyle' type='button' value='确 定'/></div></div>");	
			$("#dialog img").css({margin:"0 5px 0 0"});
		}
		else
		{
			$("body").append("<div id='dialog_bg'></div><div id='dialog'><div id='dialog_title'>"+dT+"<div id='dialog_close'></div></div><table id='dialogFrame' width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td scope='row' align='center' valign='middle'>"+dData+"</td></tr></table><div id='dialog_btnlist'><input class='inputstyle' type='button' value='确 定'/><input class='inputstyle' type='button' value='取 消'/></div></div>");	
		}	
	}
	else
	{
	    $("body").append("<div id='dialog_bg'></div><div id='dialog'><div id='dialog_title'>"+dT+"<div id='dialog_close'></div></div><iframe scrolling='auto' frameborder='0' width='100%' height='auto' id='dialogFrame' name='dialogFrame' src='"+dData+"' ></iframe><div id='dialog_btnlist'><input class='inputstyle' type='button' value='确 定'/><input class='inputstyle' type='button' value='取 消'/></div></div>");	
	}
	var leftpx = (webW-dW)/2;
	var toppx = (webH-dH-headH)/2;
	$("#dialog").css({height:(dH)+"px",width:dW+"px",left:leftpx+"px",top:toppx+"px"});
	$("#dialogFrame").css({height:(dH-32-33)+"px",width:dW+"px",margin:"2px 0 0 0"});
	$("#dialog_bg").css({height:webSH+"px"});
	$("#dialog_btnlist").css({width:(dW-30)+"px","padding-left":"30px"});
	$(obj).blur();
	MoveWindow('dialog_title','dialog')
	$("#dialog_close").click(function()
									  {
										  $("#dialog").remove();
										  $("#dialog_bg").remove();
									  });
	$("#dialog_btnlist").click(function()
									  {
										  $("#dialog").remove();
										  $("#dialog_bg").remove();
									  });
	
	
}
function MoveWindow(hanldID,windowID)
{
	var posx,posy,posx1,posx1,posx2,posx2,mbx,mby;
	document.getElementById(hanldID).style.cursor = "move";
	var handle = document.getElementById(hanldID);
	var moveWindow = document.getElementById(windowID);
	function mdown(event)
	{
		event = window.event || event;
		posx = event.clientX;
		posy = event.clientY;
		mbx = event.clientX - moveWindow.offsetLeft;
		mby = event.clientY - moveWindow.offsetTop;
		moveWindow.onmousemove = mmove;
		moveWindow.onmouseup = mup;
		moveWindow.onmouseout = mout;
	}
	var mmove = function(event)
	{
		event = window.event || event;
		posx1 = event.clientX;
		posy1 = event.clientY;
		moveWindow.style.left = posx1 - mbx + "px";
		moveWindow.style.top = posy1 - mby + "px";
	}
	function mup(event)
	{
		event = window.event || event;
		posx2 = event.clientX;
		posy2 = event.clientY;
		moveWindow.onmousemove = "";
	}
	function mout(event)
	{
		event = window.event || event;
		moveWindow.onmousemove = "";
	}
	handle.onmousedown = mdown;
}