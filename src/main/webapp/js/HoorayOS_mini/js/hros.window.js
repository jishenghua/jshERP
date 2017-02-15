/*
**  应用窗口
*/
HROS.window = (function(){
	return {
		init : function(){
			//窗口上各个按钮
			HROS.window.handle();
			//窗口移动
			HROS.window.move();
			//窗口拉伸
			HROS.window.resize();
			//绑定窗口遮罩层点击事件
			$('#desk').on('click', '.window-container .window-mask', function(){
				HROS.window.show2top($(this).parents('.window-container').attr('appid'), true);
			});
			//屏蔽窗口右键
			$('#desk').on('contextmenu', '.window-container', function(){
				return false;
			});
		},
		/*
		**  创建窗口
		**  自定义窗口：HROS.window.createTemp({title,url,width,height,top,left,resize,isflash});
		**      后面参数依次为：标题、地址、宽、高、是否可拉伸、是否打开默认最大化、是否为flash
		**      示例：HROS.window.createTemp({title:"百度",url:"http://www.baidu.com",width:800,height:400,top:0,left:100,isresize:false,isopenmax:false,isflash:false});
		*/
		createTemp : function(obj){
			var type = 'app', appid = obj.appid == null ? Date.parse(new Date()) : obj.appid;
			//判断窗口是否已打开
			var iswindowopen = false;
			$('#task-content-inner a.task-item').each(function(){
				if($(this).attr('appid') == appid){
					iswindowopen = true;
					HROS.window.show2top($(this).attr('appid'));
					return false;
				}
			});
			//如果没有打开，则进行创建
			if(!iswindowopen){
				function nextDo(options){
					var windowId = '#w_' + options.appid;
					//新增任务栏
					$('#task-content-inner').prepend(taskTemp({
						'type' : options.type,
						'id' : 't_' + options.appid,
						'appid' : options.appid,
						'title' : options.title,
						'imgsrc' : options.imgsrc
					}));
					HROS.taskbar.resize();
					//新增窗口
					TEMP.windowTemp = {
						'width' : options.width,
						'height' : options.height,
						'top' : options.top,
						'left' : options.left,
						'emptyW' : $(window).width() - options.width,
						'emptyH' : $(window).height() - options.height,
						'zIndex' : HROS.CONFIG.windowIndexid,
						'type' : options.type,
						'id' : 'w_' + options.appid,
						'appid' : options.appid,
						'title' : options.title,
						'url' : options.url,
						'imgsrc' : options.imgsrc,
						'isresize' : options.isresize,
						'isopenmax' : options.isopenmax,
						'istitlebar' : options.isresize,
						'istitlebarFullscreen' : options.isresize ? window.fullScreenApi.supportsFullScreen == true ? true : false : false,
						'isflash' : options.isflash
					};
					$('#desk').append(windowTemp(TEMP.windowTemp));
					$(windowId).data('info', TEMP.windowTemp);
					HROS.CONFIG.windowIndexid += 1;
					//iframe加载完毕后，隐藏loading遮罩层
					$(windowId + ' iframe').load(function(){
						$(windowId + ' .window-frame').children('div').eq(1).fadeOut();
					});
					HROS.window.show2top(options.appid);
				}
				nextDo({
					type : type,
					appid : appid,
					imgsrc : 'img/ui/default_icon.png',
					title : obj.title,
					url : obj.url,
					width : obj.width,
					height : obj.height,
					top : typeof(obj.top) == 'undefined' ? (($(window).height() - obj.height) / 2 <= 0 ? 0 : ($(window).height() - obj.height) / 2) : obj.top,
					left : typeof(obj.left) == 'undefined' ? (($(window).width() - obj.width) / 2 <= 0 ? 0 : ($(window).width() - obj.width) / 2) : obj.left,
					isresize : typeof(obj.isresize) == 'undefined' ? false : obj.isresize,
					isopenmax : typeof(obj.isopenmax) == 'undefined' ? false : obj.isopenmax,
					isflash : typeof(obj.isflash) == 'undefined' ? true : obj.isflash
				});
			}else{
				//如果设置强制刷新
				if(obj.refresh){
					var windowId = '#w_' + appid;
					$(windowId).find('iframe').attr('src', obj.url);
				}
			}
		},
		/*
		**  创建窗口
		**  系统窗口：HROS.window.create(appid);
		**      示例：HROS.window.create(12);
		*/
		create : function(appid){
			//判断窗口是否已打开
			var iswindowopen = false;
			$('#task-content-inner a.task-item').each(function(){
				if($(this).attr('appid') == appid){
					iswindowopen = true;
					HROS.window.show2top(appid);
					return false;
				}
			});
			//如果没有打开，则进行创建
			if(!iswindowopen){
				function nextDo(options){
					var windowId = '#w_' + options.appid;
					//新增任务栏
					$('#task-content-inner').prepend(taskTemp({
						'type' : options.type,
						'id' : 't_' + options.appid,
						'appid' : options.appid,
						'title' : options.title,
						'imgsrc' : options.imgsrc
					}));
					HROS.taskbar.resize();
					//新增窗口
					TEMP.windowTemp = {
						'width' : options.width,
						'height' : options.height,
						'top' : options.top,
						'left' : options.left,
						'emptyW' : $(window).width() - options.width,
						'emptyH' : $(window).height() - options.height,
						'zIndex' : HROS.CONFIG.windowIndexid,
						'type' : options.type,
						'id' : 'w_' + options.appid,
						'appid' : options.appid,
						'title' : options.title,
						'url' : options.url,
						'imgsrc' : options.imgsrc,
						'isresize' : options.isresize == 1 ? true : false,
						'isopenmax' : options.isresize == 1 ? options.isopenmax == 1 ? true : false : false,
						'istitlebar' : options.isresize == 1 ? true : false,
						'istitlebarFullscreen' : options.isresize == 1 ? window.fullScreenApi.supportsFullScreen == true ? true : false : false,
						'isflash' : options.isflash == 1 ? true : false
					};
					$('#desk').append(windowTemp(TEMP.windowTemp));
					$(windowId).data('info', TEMP.windowTemp);
					HROS.CONFIG.windowIndexid += 1;
					//iframe加载完毕后，隐藏loading遮罩层
					$(windowId + ' iframe').load(function(){
						$(windowId + ' .window-frame').children('div').eq(1).fadeOut();
					});
					HROS.window.show2top(options.appid);
				}
				$(HROS.VAR.dock).each(function(){
					if(this.id == appid){
						nextDo({
							type : this.type,
							id : this.id,
							appid : this.id,
							title : this.title,
							imgsrc : this.icon,
							url : this.url,
							width : this.width,
							height : this.height,
							top : typeof(this.top) == 'undefined' ? (($(window).height() - this.height) / 2 <= 0 ? 0 : ($(window).height() - this.height) / 2) : this.top,
							left : typeof(this.left) == 'undefined' ? (($(window).width() - this.width) / 2 <= 0 ? 0 : ($(window).width() - this.width) / 2) : this.left,
							isresize : this.isresize,
							isopenmax : this.isopenmax,
							isflash : this.isflash
						});
					}
				});
				$(HROS.VAR.desk).each(function(){
					if(this.id == appid){
						nextDo({
							type : this.type,
							id : this.id,
							appid : this.id,
							title : this.title,
							imgsrc : this.icon,
							url : this.url,
							width : this.width,
							height : this.height,
							top : typeof(this.top) == 'undefined' ? (($(window).height() - this.height) / 2 <= 0 ? 0 : ($(window).height() - this.height) / 2) : this.top,
							left : typeof(this.left) == 'undefined' ? (($(window).width() - this.width) / 2 <= 0 ? 0 : ($(window).width() - this.width) / 2) : this.left,
							isresize : this.isresize,
							isopenmax : this.isopenmax,
							isflash : this.isflash
						});
					}
				});
			}
		},
		close : function(appid){
			var windowId = '#w_' + appid, taskId = '#t_' + appid;
			$(windowId).removeData('info').html('').remove();
			$('#task-content-inner ' + taskId).html('').remove();
			$('#task-content-inner').css('width', $('#task-content-inner .task-item').length * 114);
			$('#task-bar, #nav-bar').removeClass('min-zIndex');
			HROS.taskbar.resize();
		},
		closeAll : function(){
			$('#desk .window-container').each(function(){
				HROS.window.close($(this).attr('appid'));
			});
		},
		hide : function(appid){
			HROS.window.show2top(appid);
			var windowId = '#w_' + appid, taskId = '#t_' + appid;
			$(windowId).css('left', -10000).attr('state', 'hide');
			$('#task-content-inner ' + taskId).removeClass('task-item-current');
			if($(windowId).attr('ismax') == 1){
				$('#task-bar, #nav-bar').removeClass('min-zIndex');
			}
		},
		hideAll : function(){
			$('#task-content-inner a.task-item').removeClass('task-item-current');
			$('#desk-1').nextAll('div.window-container').css('left', -10000).attr('state', 'hide');
		},
		max : function(appid){
			HROS.window.show2top(appid);
			var windowId = '#w_' + appid, taskId = '#t_' + appid;
			$(windowId + ' .title-handle .ha-max').hide().next(".ha-revert").show();
			$(windowId).addClass('window-maximize').attr('ismax',1).animate({
				width : '100%',
				height : '100%',
				top : 0,
				left : 0
			}, 200);
			$('#task-bar, #nav-bar').addClass('min-zIndex');
		},
		revert : function(appid){
			HROS.window.show2top(appid);
			var windowId = '#w_' + appid, taskId = '#t_' + appid;
			$(windowId + ' .title-handle .ha-revert').hide().prev('.ha-max').show();
			var obj = $(windowId), windowdata = obj.data('info');
			obj.removeClass('window-maximize').attr('ismax',0).animate({
				width : windowdata['width'],
				height : windowdata['height'],
				left : windowdata['left'],
				top : windowdata['top']
			}, 500);
			$('#task-bar, #nav-bar').removeClass('min-zIndex');
		},
		show2top : function(appid, isanimate){
			isanimate = isanimate == null ? false : isanimate;
			var windowId = '#w_' + appid, taskId = '#t_' + appid;
			var windowdata = $(windowId).data('info');
			var arr = [];
			function show(){
				HROS.window.show2under();
				//改变当前任务栏样式
				$('#task-content-inner ' + taskId).addClass('task-item-current');
				if($(windowId).attr('ismax') == 1){
					$('#task-bar, #nav-bar').addClass('min-zIndex');
				}
				//改变当前窗口样式
				$(windowId).addClass('window-current').css({
					'z-index' : HROS.CONFIG.windowIndexid,
					'left' : windowdata['left'],
					'top' : windowdata['top']
				}).attr('state', 'show');
				//如果窗口最小化前是最大化状态的，则坐标位置设为0
				if($(windowId).attr('ismax') == 1){
					$(windowId).css({
						'left' : 0,
						'top' : 0
					});
				}
				//改变当前窗口遮罩层样式
				$(windowId + ' .window-mask').hide();
				//改变当前iframe显示
				$(windowId + ' iframe').show();
				HROS.CONFIG.windowIndexid += 1;
			}
			if(isanimate){
				var baseStartX = $(windowId).offset().left, baseEndX = baseStartX + $(windowId).width();
				var baseStartY = $(windowId).offset().top, baseEndY = baseStartY + $(windowId).height();
				var baseCenterX = baseStartX + ($(windowId).width() / 2), baseCenterY = baseStartY + ($(windowId).height() / 2);
				var baseZIndex = parseInt($(windowId).css('zIndex'));
				$('#desk .window-container:not(' + windowId + ')').each(function(){
					var thisStartX = $(this).offset().left, thisEndX = thisStartX + $(this).width();
					var thisStartY = $(this).offset().top, thisEndY = thisStartY + $(this).height();
					var thisCenterX = thisStartX + ($(this).width() / 2), thisCenterY = thisStartY + ($(this).height() / 2);
					var thisZIndex = parseInt($(this).css('zIndex'));
					var flag = '';
					if(thisZIndex > baseZIndex){
						//  常规情况，只要有一个角处于区域内，则可以判断窗口有覆盖
						//   _______            _______        _______    _______
						//  |    ___|___    ___|       |   ___|___    |  |       |___
						//  |   |       |  |   |       |  |       |   |  |       |   |
						//  |___|       |  |   |_______|  |       |___|  |_______|   |
						//      |_______|  |_______|      |_______|          |_______|
						if(
							(thisStartX >= baseStartX && thisStartX <= baseEndX && thisStartY >= baseStartY && thisStartY <= baseEndY)
							||
							(thisStartX >= baseStartX && thisStartX <= baseEndX && thisEndY >= baseStartY && thisEndY <= baseEndY)
							||
							(thisEndX >= baseStartX && thisEndX <= baseEndX && thisStartY >= baseStartY && thisStartY <= baseEndY)
							||
							(thisEndX >= baseStartX && thisEndX <= baseEndX && thisEndY >= baseStartY && thisEndY <= baseEndY)
						){
							flag = 'x';
						}
						//  非常规情况
						//       _______    _______          _____
						//   ___|       |  |       |___    _|     |___
						//  |   |       |  |       |   |  | |     |   |
						//  |___|       |  |       |___|  |_|     |___|
						//      |_______|  |_______|        |_____|
						if(
							(thisStartX >= baseStartX && thisStartX <= baseEndX && thisStartY < baseStartY && thisEndY > baseEndY)
							||
							(thisEndX >= baseStartX && thisEndX <= baseEndX && thisStartY < baseStartY && thisEndY > baseEndY)
						){
							flag = 'x';
						}
						//      _____       ___________      _____
						//   __|_____|__   |           |   _|_____|___
						//  |           |  |           |  |           |
						//  |           |  |___________|  |___________|
						//  |___________|     |_____|       |_____|
						if(
							(thisStartY >= baseStartY && thisStartY <= baseEndY && thisStartX < baseStartX && thisEndX > baseEndX)
							||
							(thisEndY >= baseStartY && thisEndY <= baseEndY && thisStartX < baseStartX && thisEndX > baseEndX)
						){
							flag = 'y';
						}
						//  两个角处于区域内，另外两种情况不用处理，因为这两种情况下，被移动的窗口是需要进行上下滑动，而非左右
						//      _____       ___________
						//   __|     |__   |   _____   |
						//  |  |     |  |  |  |     |  |
						//  |  |_____|  |  |__|     |__|
						//  |___________|     |_____|
						if(
							(thisStartX >= baseStartX && thisStartX <= baseEndX && thisEndY >= baseStartY && thisEndY <= baseEndY)
							&&
							(thisEndX >= baseStartX && thisEndX <= baseEndX && thisEndY >= baseStartY && thisEndY <= baseEndY)
							||
							(thisStartX >= baseStartX && thisStartX <= baseEndX && thisStartY >= baseStartY && thisStartY <= baseEndY)
							&&
							(thisEndX >= baseStartX && thisEndX <= baseEndX && thisStartY >= baseStartY && thisStartY <= baseEndY)
						){
							flag = 'y';
						}
					}
					if(flag != ''){
						var direction, distance;
						if(flag == 'x'){
							if(thisCenterX > baseCenterX){
								direction = 'right';
								distance = baseEndX - thisStartX + 30;
							}else{
								direction = 'left';
								distance = thisEndX - baseStartX + 30;
							}
						}else{
							if(thisCenterY > baseCenterY){
								direction = 'bottom';
								distance = baseEndY - thisStartY + 30;
							}else{
								direction = 'top';
								distance = thisEndY - baseStartY + 30;
							}
						}
						arr.push({
							id : $(this).attr('id'),
							direction : direction, //移动方向
							distance : distance //移动距离
						});
					}
				});
				//开始移动
				var delayTime = 0;
				for(var i = 0; i < arr.length; i++){
					var baseLeft = $('#' + arr[i].id).offset().left, baseTop = $('#' + arr[i].id).offset().top;
					if(arr[i].direction == 'left'){
						$('#' + arr[i].id).delay(delayTime).animate({
							left : baseLeft - arr[i].distance
						}, 300).animate({
							left : baseLeft
						}, 300);
					}else if(arr[i].direction == 'right'){
						$('#' + arr[i].id).delay(delayTime).animate({
							left : baseLeft + arr[i].distance
						}, 300).animate({
							left : baseLeft
						}, 300);
					}else if(arr[i].direction == 'top'){
						$('#' + arr[i].id).delay(delayTime).animate({
							top : baseTop - arr[i].distance
						}, 300).animate({
							top : baseTop
						}, 300);
					}else if(arr[i].direction == 'bottom'){
						$('#' + arr[i].id).delay(delayTime).animate({
							top : baseTop + arr[i].distance
						}, 300).animate({
							top : baseTop
						}, 300);
					}
					delayTime += 100;
				}
				setTimeout(show, delayTime + 100);
			}else{
				show();
			}
		},
		show2under : function(){
			//改变任务栏样式
			$('#task-content-inner a.task-item').removeClass('task-item-current');
			//改变窗口样式
			$('#desk .window-container').removeClass('window-current');
			//改变窗口遮罩层样式
			$('#desk .window-container .window-mask').show();
			//改变iframe显示
			$('#desk .window-container-flash iframe').hide();
		},
		handle : function(){
			$('#desk').on('dblclick', '.window-container .title-bar', function(e){
				var obj = $(this).parents('.window-container');
				//判断当前窗口是否已经是最大化
				if(obj.find('.ha-max').is(':hidden')){
					obj.find('.ha-revert').click();
				}else{
					obj.find('.ha-max').click();
				}
			}).on('click', '.window-container .ha-hide', function(){
				var obj = $(this).parents('.window-container');
				HROS.window.hide(obj.attr('appid'));
			}).on('click', '.window-container .ha-max', function(){
				var obj = $(this).parents('.window-container');
				HROS.window.max(obj.attr('appid'));
			}).on('click', '.window-container .ha-revert', function(){
				var obj = $(this).parents('.window-container');
				HROS.window.revert(obj.attr('appid'));
			}).on('click', '.window-container .ha-fullscreen', function(){
				var obj = $(this).parents('.window-container');
				window.fullScreenApi.requestFullScreen(document.getElementById(obj.find('iframe').attr('id')));
			}).on('click', '.window-container .ha-close', function(){
				var obj = $(this).parents('.window-container');
				HROS.window.close(obj.attr('appid'));
			}).on('contextmenu', '.window-container', function(){
				$('.popup-menu').hide();
				$('.quick_view_container').remove();
				return false;
			});
		},
		move : function(){
			$('#desk').on('mousedown', '.window-container .title-bar', function(e){
				var obj = $(this).parents('.window-container');
				if(obj.attr('ismax') == 1){
					return false;
				}
				HROS.window.show2top(obj.attr('appid'));
				var windowdata = obj.data('info'), lay, x, y;
				x = e.clientX - obj.offset().left;
				y = e.clientY - obj.offset().top;
				//绑定鼠标移动事件
				$(document).on('mousemove', function(e){
					lay = HROS.maskBox.desk();
					lay.show();
					//强制把右上角还原按钮隐藏，最大化按钮显示
					obj.find('.ha-revert').hide().prev('.ha-max').show();
					_l = e.clientX - x;
					_t = e.clientY - y;
					_w = windowdata['width'];
					_h = windowdata['height'];
					//窗口贴屏幕顶部10px内 || 底部60px内
					_t = _t <= 10 ? 0 : _t >= lay.height()-30 ? lay.height()-30 : _t;
					obj.css({
						width : _w,
						height : _h,
						left : _l,
						top : _t
					});
					obj.data('info').left = obj.offset().left;
					obj.data('info').top = obj.offset().top;
				}).on('mouseup', function(){
					$(this).off('mousemove').off('mouseup');
					if(typeof(lay) !== 'undefined'){
						lay.hide();
					}
				});
			});
		},
		resize : function(obj){
			$('#desk').on('mousedown', '.window-container .window-resize', function(e){
				var obj = $(this).parents('.window-container');
				//增加背景遮罩层
				var resizeobj = $(this), lay, x = e.clientX, y = e.clientY, w = obj.width(), h = obj.height();
				$(document).on('mousemove', function(e){
					lay = HROS.maskBox.desk();
					lay.show();
					_x = e.clientX;
					_y = e.clientY;
					//当拖动到屏幕边缘时，自动贴屏
					_x = _x <= 10 ? 0 : _x >= (lay.width()-12) ? (lay.width()-2) : _x;
					_y = _y <= 10 ? 0 : _y >= (lay.height()-12) ? lay.height() : _y;
					switch(resizeobj.attr('resize')){
						case 't':
							h + y - _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h + y - _y,
								top : _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							break;
						case 'r':
							w - x + _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w - x + _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							break;
						case 'b':
							h - y + _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h - y + _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							break;
						case 'l':
							w + x - _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w + x - _x,
								left : _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							break;
						case 'rt':
							h + y - _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h + y - _y,
								top : _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							w - x + _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w - x + _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							break;
						case 'rb':
							w - x + _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w - x + _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							h - y + _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h - y + _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							break;
						case 'lt':
							w + x - _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w + x - _x,
								left : _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							h + y - _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h + y - _y,
								top : _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							break;
						case 'lb':
							w + x - _x > HROS.CONFIG.windowMinWidth ? obj.css({
								width : w + x - _x,
								left : _x
							}) : obj.css({
								width : HROS.CONFIG.windowMinWidth
							});
							h - y + _y > HROS.CONFIG.windowMinHeight ? obj.css({
								height : h - y + _y
							}) : obj.css({
								height : HROS.CONFIG.windowMinHeight
							});
							break;
					}
				}).on('mouseup',function(){
					if(typeof(lay) !== 'undefined'){
						lay.hide();
					}
					obj.data('info').width = obj.width();
					obj.data('info').height = obj.height();
					obj.data('info').left = obj.offset().left;
					obj.data('info').top = obj.offset().top;
					obj.data('info').emptyW = $(window).width() - obj.width();
					obj.data('info').emptyH = $(window).height() - obj.height();
					$(this).off('mousemove').off('mouseup');
				});
			});
		}
	}
})();