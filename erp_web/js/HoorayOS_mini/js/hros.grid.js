/*
**  应用布局格子
**  这篇文章里有简单说明格子的作用
**  http://www.cnblogs.com/hooray/archive/2012/03/23/2414410.html
*/
HROS.grid = (function(){
	return {
		getAppGrid : function(){
			var width, height;
			width = $('#desk-1').width() - HROS.CONFIG.appButtonLeft;
			height = $('#desk-1').height() - HROS.CONFIG.appButtonTop;
			var appGrid = [], _top = HROS.CONFIG.appButtonTop, _left = HROS.CONFIG.appButtonLeft;
			for(var i = 0; i < 10000; i++){
				appGrid.push({
					startY : _top,
					endY : _top + 100,
					startX : _left,
					endX : _left + 120
				});
				_top += 100;
				if(_top + 70 > height){
					_top = HROS.CONFIG.appButtonTop;
					_left += 120;
				}
			}
			return appGrid;
		},
		searchAppGrid : function(x, y){
			var grid = HROS.grid.getAppGrid(), j = grid.length;
			var flags = 0, appLength = $('#desk-1 li.appbtn:not(.add)').length - 1;
			for(var i = 0; i < j; i++){
				if(x >= grid[i].startX && x <= grid[i].endX){
					flags += 1;
				}
				if(y >= grid[i].startY && y <= grid[i].endY){
					flags += 1;
				}
				if(flags === 2){
					return i > appLength ? appLength : i;
				}else{
					flags = 0;
				}
			}
			return null;
		},
		getDockAppGrid : function(){
			var height = $('#dock-bar .dock-applist').height();
			var dockAppGrid = [], _left = 0, _top = 0;
			for(var i = 0; i < 7; i++){
				dockAppGrid.push({
					startY : _top,
					endY : _top + 62,
					startX : _left,
					endX : _left + 62
				});
				_top += 62;
				if(_top + 62 > height){
					_top = 0;
					_left += 62;
				}
			}
			return dockAppGrid;
		},
		searchDockAppGrid : function(x, y){
			var grid = HROS.grid.getDockAppGrid(), j = grid.length, flags = 0,
				appLength = $('#dock-bar .dock-applist li').length - 1;
			for(var i = 0; i < j; i++){
				if(x >= grid[i].startX && x <= grid[i].endX){
					flags += 1;
				}
				if(y >= grid[i].startY && y <= grid[i].endY){
					flags += 1;
				}
				if(flags === 2){
					return i > appLength ? appLength : i;
				}else{
					flags = 0;
				}
			}
			return null;
		}
	}
})();