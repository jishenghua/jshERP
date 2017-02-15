/*
**  壁纸
*/
HROS.wallpaper = (function(){
	return {
		/*
		**	初始化
		*/
		init : function(){
			HROS.wallpaper.set();
		},
		/*
		**	设置壁纸
		*/
		set : function(isreload){
			/*
			**  判断壁纸是否需要重新载入
			**  比如当浏览器尺寸改变时，只需更新壁纸，而无需重新载入
			*/
			var isreload = typeof(isreload) == 'undefined' ? true : isreload;
			if(isreload){
				$('#zoomWallpaperGrid').remove();
			}
			var w = $(window).width(), h = $(window).height();
			if(isreload){
				$('body').append('<div id="zoomWallpaperGrid" style="position:absolute;z-index:-10;left:0;top:0;overflow:hidden;height:' + h + 'px;width:' + w + 'px"><img id="zoomWallpaper" style="position:absolute;height:' + h + 'px;width:' + w + 'px;top:0;left:0"><div style="position:absolute;height:' + h + 'px;width:' + w + 'px;background:#fff;opacity:0;filter:alpha(opacity=0)"></div></div>');
				$('#zoomWallpaper').attr('src', HROS.CONFIG.wallpaper).on('load', function(){
					$(this).show();
				});
			}else{
				$('#zoomWallpaperGrid, #zoomWallpaperGrid div, #zoomWallpaper').css({
					height : h + 'px',
					width : w + 'px'
				});
			}
		}
	}
})();