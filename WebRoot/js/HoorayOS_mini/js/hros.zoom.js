/*
**  该功能是从QQ空间里提取出来的
**  用于判断页面是否处于缩放状态中，并给予提示
**  可在浏览页时按住ctrl+鼠标滚轮进行测试预览
*/
HROS.zoom = (function(){
	return {
		/*
		**  初始化
		**  其实也不用初始化，可以直接把object代码写在页面上
		**  需要注意的是onchange参数，调用的是HROS.zoom.check方法
		*/
		init : function(){
			$('body').append('<div id="zoombox"></div>');
			/*
			**  使用SWFObject.js插入flash
			**  http://www.cnblogs.com/wuxinxi007/archive/2009/10/27/1590709.html
			*/
			swfobject.embedSWF('js/zoom.swf?onchange=HROS.zoom.check', 'zoombox', '10', '10', '6.0.0', 'expressInstall.swf', '', {allowScriptAccess : 'always', wmode : 'transparent', scale : 'noScale'}, {id : 'accessory_zoom', name : 'zoom_detect'});
		},
		/*
		**  为什么会有个参数o？其实我也不知道
		**  o.scale的值是数字，当o.scale大于1时，页面处于放大状态，反之则为缩小状态
		*/
		check : function(o){
			var s = o.scale, m = s > 1 ? '放大' : '缩小';
			if(s != 1){
				HROS.VAR.zoomLevel = s;
				$('#zoom-tip').show().find('span').text('您的浏览器目前处于' + m + '状态，会导致显示不正常，您可以键盘按“ctrl+数字0”组合键恢复初始状态！');
			}else{
				if(s != HROS.VAR.zoomLevel){
					$('#zoom-tip').fadeOut();
				}
			}
		},
		/*
		**  关闭，其实是删除，如果想做关闭，把代码改成hide()即可
		*/
		close : function(){
			$('#zoom-tip').remove();
		}
	}
})();