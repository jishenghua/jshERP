/*
**  应用
*/
HROS.app = (function(){
	return {
		/*
		**  初始化桌面应用
		*/
		init : function(){
			//绑定应用打开事件
			HROS.app.click();
			//绑定滚动条拖动事件
			HROS.app.moveScrollbar();
			HROS.app.get();
		},
		get : function(){
			var userId = sessionStorage.getItem("userId");
		    $.getJSON('../../app/findAppByUserId?userId=' + userId, function (sc) {
				HROS.VAR.dock = sc['dock'];
				HROS.VAR.desk = sc['desk'];
				//输出桌面应用
				HROS.app.set();
			});
		},
		/*
		**  输出应用
		*/
		set : function(){
			//绘制应用表格
			var grid = HROS.grid.getAppGrid(), dockGrid = HROS.grid.getDockAppGrid();
			//加载应用码头应用
			if(HROS.VAR.dock != ''){
				var dock_append = '';
				$(HROS.VAR.dock).each(function(i){
					dock_append += appbtnTemp({
						'top' : dockGrid[i]['startY'],
						'left' : dockGrid[i]['startX'],
						'title' : this.title,
						'type' : this.type,
						'id' : 'd_' + this.id,
						'appid' : this.id,
						'imgsrc' : this.icon
					});
				});
				$('#dock-bar .dock-applist').html('').append(dock_append);
			}else{
				$('#dock-bar .dock-applist').html('');
			}
			//加载桌面应用
			if(HROS.VAR.desk != ''){
				var desk_append = '';
				$(HROS.VAR.desk).each(function(i){
					desk_append += appbtnTemp({
						'top' : grid[i]['startY'] + 7,
						'left' : grid[i]['startX'] + 16,
						'title' : this.title,
						'type' : this.type,
						'id' : 'd_' + this.id,
						'appid' : this.id,
						'imgsrc' : this.icon
					});
				});
			}
			$('#desk-1 li').remove();
			$('#desk-1').append(desk_append);
			HROS.deskTop.appresize();
			//加载滚动条
			HROS.app.getScrollbar();
		},
		/*
		**  应用打开
		*/
		click : function(){
			//应用码头应用拖动
			$('#dock-bar .dock-applist').on('click', 'li', function(e){
				e.preventDefault();
				e.stopPropagation();
				switch($(this).attr('type')){
					case 'app':
						HROS.window.create($(this).attr('appid'));
						break;
					case 'widget':
						HROS.widget.create($(this).attr('appid'));
						break;
				}
			});
			//桌面应用拖动
			$('#desktop .desktop-container').on('click', 'li:not(.add)', function(e){
				e.preventDefault();
				e.stopPropagation();
				switch($(this).attr('type')){
					case 'app':
						HROS.window.create($(this).attr('appid'));
						break;
					case 'widget':
						HROS.widget.create($(this).attr('appid'));
						break;
				}
			});
		},
		/*
		**  加载滚动条
		*/
		getScrollbar : function(){
			setTimeout(function(){
				$('#desk .desktop-container').each(function(){
					var desk = $(this), scrollbar = desk.children('.scrollbar');
					//先清空所有附加样式
					scrollbar.hide();
					desk.scrollLeft(0);
					var deskW = parseInt(desk.children('.appbtn').last().css('left')) + 106;
					if(desk.width() / deskW < 1){
						desk.children('.scrollbar-x').width(desk.width() / deskW * desk.width()).css('left',0).show();
					}
				});
			}, 500);
		},
		/*
		**  移动滚动条
		*/
		moveScrollbar : function(){
			/*
			**  手动拖动
			*/
			$('#desk .scrollbar').on('mousedown', function(e){
				var x, y, cx, cy, deskrealw, deskrealh, movew, moveh;
				var scrollbar = $(this), desk = scrollbar.parent('.desktop-container');
				deskrealw = parseInt(desk.children('.appbtn').last().css('left')) + 106;
				deskrealh = parseInt(desk.children('.appbtn').last().css('top')) + 108;
				movew = desk.width() - scrollbar.width();
				moveh = desk.height() - scrollbar.height();
				if(scrollbar.hasClass('scrollbar-x')){
					x = e.clientX - scrollbar.offset().left;
				}else{
					y = e.clientY - scrollbar.offset().top;
				}
				$(document).on('mousemove', function(e){
					if(scrollbar.hasClass('scrollbar-x')){
						cx = e.clientX - x - 73 < 0 ? 0 : e.clientX - x - 73 > movew ? movew : e.clientX - x - 73;
						scrollbar.css('left', cx / desk.width() * deskrealw + cx);
						desk.scrollLeft(cx / desk.width() * deskrealw);
					}else{
						cy = e.clientY - y < 0 ? 0 : e.clientY - y > moveh ? moveh : e.clientY - y;
						scrollbar.css('top', cy / desk.height() * deskrealh + cy);
						desk.scrollTop(cy / desk.height() * deskrealh);
					}
				}).on('mouseup', function(){
					$(this).off('mousemove').off('mouseup');
				});
			});
			/*
			**  鼠标滚动
			*/
			$('#desk .desktop-container').each(function(i){
				$('#desk-' + (i + 1)).on('mousewheel', function(event, delta){
					var desk = $(this);
					var deskrealw = parseInt(desk.children('.appbtn').last().css('left')) + 106, scrollleftright;
					if(delta < 0){
						scrollleftright = desk.scrollLeft() + 200 > deskrealw - desk.width() ? deskrealw - desk.width() : desk.scrollLeft() + 200;
					}else{
						scrollleftright = desk.scrollLeft() - 200 < 0 ? 0 : desk.scrollLeft() - 200;
					}
					desk.stop(false, true).animate({scrollLeft : scrollleftright}, 300);
					desk.children('.scrollbar-x').stop(false, true).animate({
						left : scrollleftright / deskrealw * desk.width() + scrollleftright
					}, 300);
				});
			});
		}
	}
})();