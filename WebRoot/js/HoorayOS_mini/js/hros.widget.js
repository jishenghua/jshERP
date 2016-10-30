/*
**  小挂件
*/
HROS.widget = (function(){
	return {
		init : function(){
			//挂件上各个按钮
			HROS.widget.handle();
			//挂件移动
			HROS.widget.move();
		},
		/*
		**  创建挂件
		**  自定义挂件：HROS.widget.createTemp({url,width,height,left,top});
		**      示例：HROS.widget.createTemp({url:"http://www.baidu.com",width:800,height:400,left:100,top:100});
		*/
		createTemp : function(obj){
			var appid = obj.appid == null ? Date.parse(new Date()) : obj.appid;
			//判断窗口是否已打开
			var iswidgetopen = false;
			$('#desk .widget').each(function(){
				if($(this).attr('appid') == appid){
					iswidgetopen = true;
					return false;
				}
			});
			//如果没有打开，则进行创建
			if(!iswidgetopen){
				function nextDo(options){
					$('#desk').append(widgetWindowTemp({
						'width' : options.width,
						'height' : options.height,
						'type' : 'widget',
						'id' : 'w_' + options.appid,
						'appid' : options.appid,
						'top' : options.top,
						'right' : options.right,
						'url' : options.url,
						'zIndex' : HROS.CONFIG.widgetIndexid
					}));
					HROS.CONFIG.widgetIndexid += 1;
				}
				nextDo({
					appid : appid,
					url : obj.url,
					width : obj.width,
					height : obj.height,
					top : obj.top == null ? 0 : obj.top,
					right : obj.right == null ? 0 : obj.right
				});
			}
		},
		create : function(appid){
			//判断窗口是否已打开
			var iswidgetopen = false;
			$('#desk .widget').each(function(){
				if($(this).attr('appid') == appid){
					iswidgetopen = true;
					return false;
				}
			});
			//如果没有打开，则进行创建
			if(!iswidgetopen){
				function nextDo(options){
					var widgetId = '#w_' + options.appid;
					TEMP.widgetTemp = {
						'title' : options.title,
						'width' : options.width,
						'height' : options.height,
						'type' : options.type,
						'id' : 'w_' + options.appid,
						'appid' : options.appid,
						'top' : options.top,
						'right' : options.right,
						'url' : options.url,
						'zIndex' : HROS.CONFIG.widgetIndexid,
						'issetbar' : 1
					};
					$('#desk').append(widgetWindowTemp(TEMP.widgetTemp));
					$(widgetId).data('info', TEMP.widgetTemp);
					HROS.CONFIG.widgetIndexid += 1;
				}
				$(HROS.VAR.dock).each(function(){
					if(this.id == appid){
						nextDo({
							appid : this.id,
							title : this.title,
							url : this.url,
							type : this.type,
							width : this.width,
							height : this.height,
							top : typeof(this.top) == 'undefined' ? 0 : this.top,
							right : typeof(this.right) == 'undefined' ? 0 : this.right
						});
					}
				});
				$(HROS.VAR.desk).each(function(){
					if(this.id == appid){
						nextDo({
							appid : this.id,
							title : this.title,
							url : this.url,
							type : this.type,
							width : this.width,
							height : this.height,
							top : typeof(this.top) == 'undefined' ? 0 : this.top,
							right : typeof(this.right) == 'undefined' ? 0 : this.right
						});
					}
				});
			}
		},
		move : function(){
			$('#desk').on('mousedown', '.widget .move', function(e){
				var obj = $(this).parents('.widget');
				HROS.widget.show2top(obj.attr('appid'));
				var lay, x, y;
				x = e.clientX - obj.offset().left;
				y = e.clientY - obj.offset().top;
				//绑定鼠标移动事件
				$(document).on('mousemove', function(e){
					lay = HROS.maskBox.desk();
					lay.show();
					_r = e.clientX - x;
					_t = e.clientY - y;
					_t = _t < 0 ? 0 : _t;
					_r = $(window).width() - obj.width() - _r;
					obj.css({
						right : _r,
						top : _t
					});
				}).on('mouseup', function(){
					$(this).off('mousemove').off('mouseup');
					if(typeof(lay) !== 'undefined'){
						lay.hide();
					}
				});
			});
		},
		close : function(appid){
			var widgetId = '#w_' + appid;
			$(widgetId).html('').remove();
		},
		show2top : function(appid){
			var widgetId = '#w_' + appid;
			$(widgetId).css('z-index', HROS.CONFIG.widgetIndexid);
			HROS.CONFIG.widgetIndexid += 1;
		},
		handle : function(){
			$('#desk').on('click', '.widget .ha-close', function(e){
				var obj = $(this).parents('.widget');
				HROS.widget.close(obj.attr('appid'));
			});
		}
	}
})();