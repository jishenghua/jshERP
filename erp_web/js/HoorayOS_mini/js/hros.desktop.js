/*
**  桌面
*/
HROS.deskTop = (function(){
	return {
		init : function(){
			//绑定浏览器resize事件
			$(window).on('resize', function(){
				HROS.deskTop.resize();
			});
			$('body').on('click', '#desktop', function(){
				HROS.popupMenu.hide();
			}).on('contextmenu', '#desktop', function(e){
				HROS.popupMenu.hide();
				return false;
			});
		},
		/*
		**  处理浏览器改变大小后的事件
		*/
		resize : function(){
			HROS.dock.setPos();
			//更新应用定位
			HROS.deskTop.appresize();
			//更新窗口定位
			HROS.deskTop.windowresize();
			HROS.wallpaper.set(false);
		},
		/*
		**  重新排列应用
		*/
		appresize : function(){
			switch(HROS.CONFIG.appSize){
				case 's':
					$('#desk').removeClass('smallIcon').addClass('smallIcon');
					break;
				case 'm':
					$('#desk').removeClass('smallIcon');
					break;
			}
			var grid = HROS.grid.getAppGrid(), dockGrid = HROS.grid.getDockAppGrid();
			$('#dock-bar .dock-applist li').each(function(i){
				$(this).css({
					'left' : dockGrid[i]['startX'],
					'top' : dockGrid[i]['startY']
				});
				$(this).attr('left', $(this).offset().left).attr('top', $(this).offset().top);
			});
			$('#desk-1 li').each(function(i){
				var left = grid[i]['startX'] + 16, top = grid[i]['startY'] + 7;
				$(this).stop(true, false).animate({
					'left' : left,
					'top' : top
				}, 500);
				switch(HROS.CONFIG.dockPos){
					case 'top':
						$(this).attr('left', left).attr('top', top + 73);
						break;
					case 'left':
						$(this).attr('left', left + 73).attr('top', top);
						break;
					case 'right':
						$(this).attr('left', left).attr('top', top);
						break;
				}
			});
			//更新滚动条
			HROS.app.getScrollbar();
		},
		/*
		**  重新定位窗口位置
		*/
		windowresize : function(){
			$('#desk div.window-container').each(function(){
				var windowdata = $(this).data('info');
				currentW = $(window).width() - $(this).width();
				currentH = $(window).height() - $(this).height();
				var _l = windowdata['left'] / windowdata['emptyW'] * currentW >= currentW ? currentW : windowdata['left'] / windowdata['emptyW'] * currentW;
				_l = _l <= 0 ? 0 : _l;
				var _t = windowdata['top'] / windowdata['emptyH'] * currentH >= currentH ? currentH : windowdata['top'] / windowdata['emptyH'] * currentH;
				_t = _t <= 0 ? 0 : _t;
				if($(this).attr('state') != 'hide'){
					$(this).animate({
						'left' : _l,
						'top' : _t
					}, 500, function(){
						windowdata['left'] = _l;
						windowdata['top'] = _t;
						windowdata['emptyW'] = $(window).width() - $(this).width();
						windowdata['emptyH'] = $(window).height() - $(this).height();
					});
				}else{
					windowdata['left'] = _l;
					windowdata['top'] = _t;
					windowdata['emptyW'] = $(window).width() - $(this).width();
					windowdata['emptyH'] = $(window).height() - $(this).height();
				}
			});
		}
	}
})();