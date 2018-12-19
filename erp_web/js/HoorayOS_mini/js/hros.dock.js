/*
**  应用码头
*/
HROS.dock = (function(){
	return {
		/*
		**	初始化
		*/
		init : function(){
			$(window).resize(function(){
				HROS.dock.setPos();
			});
			HROS.dock.setPos();
		},
		setPos : function(){
			var desktop = $('#desk-1'), desktops = $('#desk .desktop-container');
			var desk_w = desktop.css('width', '100%').width(), desk_h = desktop.css('height', '100%').height();
			//清除dock位置样式
			$('#dock-container').removeClass('dock-top').removeClass('dock-left').removeClass('dock-right');
			$('#dock-bar').removeClass('top-bar').removeClass('left-bar').removeClass('right-bar').hide();
			
			$('#dock-bar').addClass('left-bar').children('#dock-container').addClass('dock-left');
			desktops.css({
				'width' : desk_w - 73,
				'height' : desk_h - 70,
				'left' : desk_w + 73,
				'top' : 0
			});
			desktop.css({
				'left' : 73
			});
			
			$('#dock-bar').show();
			HROS.taskbar.resize();
		}
	}
})();