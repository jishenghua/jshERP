//桌面应用
var appbtnTemp = template(
	'<li class="appbtn" id="<%=id%>" appid="<%=appid%>" type="<%=type%>" top="<%=top%>" left="<%=left%>" style="top:<%=top%>px;left:<%=left%>px">'+
		'<div><img src="<%=imgsrc%>" alt="<%=title%>"></div>'+
		'<span><%=title%></span>'+
	'</li>'
);
//任务栏
var taskTemp = template(
	'<a id="<%=id%>" appid="<%=appid%>" type="<%=type%>" class="task-item task-item-current">'+
		'<div class="task-item-icon">'+
			'<img src="<%=imgsrc%>">'+
		'</div>'+
		'<div class="task-item-txt"><%=title%></div>'+
	'</a>'
);
//小挂件
var widgetWindowTemp = template(
	'<div id="<%=id%>" appid="<%=appid%>" type="<%=type%>" class="widget" style="z-index:<%=zIndex%>;width:<%=width%>px;height:<%=height%>px;top:<%=top%>px;right:<%=right%>px">'+
		'<div class="move"></div>'+
		'<a class="ha-close" href="javascript:;" title="关闭"></a>'+
		'<div class="frame">'+
			'<iframe src="<%=url%>" frameborder="0" allowtransparency="true"></iframe>'+
		'</div>'+
	'</div>'
);
//应用窗口
var windowTemp = template(
	'<div id="<%=id%>" appid="<%=appid%>" type="<%=type%>" state="show" class="window-container window-current<% if(isflash){ %> window-container-flash<% } %>" style="<% if(isopenmax){ %>width:100%;height:100%;left:0;top:0;<% }else{ %>width:<%=width%>px;height:<%=height%>px;top:<%=top%>px;left:<%=left%>px;<% } %>z-index:<%=zIndex%>" ismax="<% if(isopenmax){ %>1<% }else{ %>0<% } %>">'+
		'<div style="height:100%">'+
			'<div class="title-bar">'+
				'<img class="icon" src="<%=imgsrc%>"><span class="title"><%=title%></span>'+
			'</div>'+
			'<div class="title-handle">'+
				'<a class="ha-hide" btn="hide" href="javascript:;" title="最小化"><b class="hide-b"></b></a>'+
				'<% if(istitlebar){ %>'+
					'<a class="ha-max" btn="max" href="javascript:;" title="最大化" <% if(isopenmax){ %>style="display:none"<% } %>><b class="max-b"></b></a>'+
					'<a class="ha-revert" btn="revert" href="javascript:;" title="还原" <% if(!isopenmax){ %>style="display:none"<% } %>><b class="revert-b"></b><b class="revert-t"></b></a>'+
				'<% } %>'+
				'<% if(istitlebarFullscreen){ %>'+
					'<a class="ha-fullscreen" btn="fullscreen" href="javascript:;" title="全屏">+</a>'+
				'<% } %>'+
				'<a class="ha-close" btn="close" href="javascript:;" title="关闭">×</a>'+
			'</div>'+
			'<div class="window-frame">'+
				'<% if(isflash){ %>'+
					'<div class="window-mask"><div class="maskbg"></div><div>运行中，点击恢复显示 :)</div></div>'+
				'<% }else{ %>'+
					'<div class="window-mask window-mask-noflash"></div>'+
				'<% } %>'+
				'<div class="window-loading"></div>'+
				'<iframe id="<%=id%>_iframe" frameborder="0" src="<%=url%>"></iframe>'+
			'</div>'+
		'</div>'+
		'<% if(isresize){ %>'+
			'<div class="window-resize window-resize-t" resize="t"></div>'+
			'<div class="window-resize window-resize-r" resize="r"></div>'+
			'<div class="window-resize window-resize-b" resize="b"></div>'+
			'<div class="window-resize window-resize-l" resize="l"></div>'+
			'<div class="window-resize window-resize-rt" resize="rt"></div>'+
			'<div class="window-resize window-resize-rb" resize="rb"></div>'+
			'<div class="window-resize window-resize-lt" resize="lt"></div>'+
			'<div class="window-resize window-resize-lb" resize="lb"></div>'+
		'<% } %>'+
	'</div>'
);