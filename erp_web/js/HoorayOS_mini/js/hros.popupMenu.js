/*
**  右键菜单
*/
HROS.popupMenu = (function(){
	return {
		/*
		**  任务栏右键
		*/
		task : function(obj){
			HROS.window.show2under();
			if(!TEMP.popupMenuTask){
				TEMP.popupMenuTask = $('<div class="popup-menu task-menu" style="z-index:9990;display:none"><ul><li><a menu="max" href="javascript:;">最大化</a></li><li style="border-bottom:1px solid #F0F0F0"><a menu="hide" href="javascript:;">最小化</a></li><li><a menu="close" href="javascript:;">关闭</a></li></ul></div>');
				$('body').append(TEMP.popupMenuTask);
				$('.task-menu').on('contextmenu', function(){
					return false;
				});
			}
			//绑定事件
			$('.task-menu a[menu="max"]').off('click').on('click', function(){
				HROS.window.max(obj.attr('appid'), obj.attr('type'));
				$('.popup-menu').hide();
			});
			$('.task-menu a[menu="hide"]').off('click').on('click', function(){
				HROS.window.hide(obj.attr('appid'), obj.attr('type'));
				$('.popup-menu').hide();
			});
			$('.task-menu a[menu="close"]').off('click').on('click', function(){
				HROS.window.close(obj.attr('appid'), obj.attr('type'));
				$('.popup-menu').hide();
			});
			return TEMP.popupMenuTask;
		},
		hide : function(){
			$('.popup-menu').hide();
		}
	}
})();