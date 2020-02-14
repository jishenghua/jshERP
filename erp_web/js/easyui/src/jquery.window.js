/**
 * EasyUI for jQuery 1.9.4
 * 
 * Copyright (c) 2009-2020 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the freeware license: http://www.jeasyui.com/license_freeware.php
 * To use it on other terms please contact us: info@jeasyui.com
 *
 */
/**
 * window - EasyUI for jQuery
 * 
 * Dependencies:
 * 	 panel
 *   draggable
 *   resizable
 * 
 */
(function($){
	function moveWindow(target, param){
		var state = $.data(target, 'window');
		if (param){
			if (param.left != null) state.options.left = param.left;
			if (param.top != null) state.options.top = param.top;
		}
		$(target).panel('move', state.options);
		if (state.shadow){
			state.shadow.css({
				left: state.options.left,
				top: state.options.top
			});
		}
	}
	
	/**
	 *  center the window only horizontally
	 */
	function hcenter(target, tomove){
		var opts = $.data(target, 'window').options;
		var pp = $(target).window('panel');
		var width = pp._outerWidth();
		if (opts.inline){
			var parent = pp.parent();
			opts.left = Math.ceil((parent.width() - width) / 2 + parent.scrollLeft());
		} else {
			opts.left = Math.ceil(($(window)._outerWidth() - width) / 2 + $(document).scrollLeft());
		}
		if (tomove){moveWindow(target);}
	}
	
	/**
	 * center the window only vertically
	 */
	function vcenter(target, tomove){
		var opts = $.data(target, 'window').options;
		var pp = $(target).window('panel');
		var height = pp._outerHeight();
		if (opts.inline){
			var parent = pp.parent();
			opts.top = Math.ceil((parent.height() - height) / 2 + parent.scrollTop());
		} else {
			opts.top = Math.ceil(($(window)._outerHeight() - height) / 2 + $(document).scrollTop());
		}
		if (tomove){moveWindow(target);}
	}
	
	function create(target){
		var state = $.data(target, 'window');
		var opts = state.options;
		var win = $(target).panel($.extend({}, state.options, {
			border: false,
			doSize: true,	// size the panel, the property undefined in window component
			closed: true,	// close the panel
			cls: 'window ' + (!opts.border?'window-thinborder window-noborder ':(opts.border=='thin'?'window-thinborder ':'')) + (opts.cls || ''),
			headerCls: 'window-header ' + (opts.headerCls || ''),
			bodyCls: 'window-body ' + (opts.noheader ? 'window-body-noheader ' : ' ') + (opts.bodyCls||''),
			
			onBeforeDestroy: function(){
				if (opts.onBeforeDestroy.call(target) == false){return false;}
				if (state.shadow){state.shadow.remove();}
				if (state.mask){state.mask.remove();}
			},
			onClose: function(){
				if (state.shadow){state.shadow.hide();}
				if (state.mask){state.mask.hide();}
				opts.onClose.call(target);
			},
			onOpen: function(){
				if (state.mask){
					state.mask.css($.extend({
						display:'block',
						zIndex: $.fn.window.defaults.zIndex++
					}, $.fn.window.getMaskSize(target)));
				}
				if (state.shadow){
					state.shadow.css({
						display:'block',
						zIndex: $.fn.window.defaults.zIndex++,
						left: opts.left,
						top: opts.top,
						width: state.window._outerWidth(),
						height: state.window._outerHeight()
					});
				}
				state.window.css('z-index', $.fn.window.defaults.zIndex++);
				
				opts.onOpen.call(target);
			},
			onResize: function(width, height){
				var popts = $(this).panel('options');
				$.extend(opts, {
					width: popts.width,
					height: popts.height,
					left: popts.left,
					top: popts.top
				});
				if (state.shadow){
					state.shadow.css({
						left: opts.left,
						top: opts.top,
						width: state.window._outerWidth(),
						height: state.window._outerHeight()
					});
				}
				opts.onResize.call(target, width, height);
			},
			onMinimize: function(){
				if (state.shadow){state.shadow.hide();}
				if (state.mask){state.mask.hide();}
				state.options.onMinimize.call(target);
			},
			onBeforeCollapse: function(){
				if (opts.onBeforeCollapse.call(target) == false){return false;}
				if (state.shadow){state.shadow.hide();}
			},
			onExpand: function(){
				if (state.shadow){state.shadow.show();}
				opts.onExpand.call(target);
			}
		}));
		
		state.window = win.panel('panel');
		
		// create mask
		if (state.mask){state.mask.remove();}
		if (opts.modal){
			state.mask = $('<div class="window-mask" style="display:none"></div>').insertAfter(state.window);
		}
		
		// create shadow
		if (state.shadow){state.shadow.remove();}
		if (opts.shadow){
			state.shadow = $('<div class="window-shadow" style="display:none"></div>').insertAfter(state.window);
		}
		
		// center and open the window
		var closed = opts.closed;
		if (opts.left == null){hcenter(target);}
		if (opts.top == null){vcenter(target);}
		moveWindow(target);
		if (!closed){win.window('open');}
	}

	function constrain(left, top, width, height){
		var target = this;
		var state = $.data(target, 'window');
		var opts = state.options;
		if (!opts.constrain){return {};}
		if ($.isFunction(opts.constrain)){
			return opts.constrain.call(target, left, top, width, height);
		}
		var win = $(target).window('window');
		var parent = opts.inline ? win.parent() : $(window);
		if (left < 0){left = 0;}
		if (top < parent.scrollTop()){top = parent.scrollTop();}
		if (left + width > parent.width()){
			if (width == win.outerWidth()){	// moving
				left = parent.width() - width;
			} else {	// resizing
				width = parent.width() - left;
			}
		}
		if (top - parent.scrollTop() + height > parent.height()){
			if (height == win.outerHeight()){	// moving
				top = parent.height() - height + parent.scrollTop();
			} else {	// resizing
				height = parent.height() - top + parent.scrollTop();
			}
		}

		return {
			left:left,
			top:top,
			width:width,
			height:height
		};
	}
	
	
	/**
	 * set window drag and resize property
	 */
	function setProperties(target){
		var state = $.data(target, 'window');
		
		state.window.draggable({
			handle: '>div.panel-header>div.panel-title',
			disabled: state.options.draggable == false,
			onBeforeDrag: function(e){
				if (state.mask) state.mask.css('z-index', $.fn.window.defaults.zIndex++);
				if (state.shadow) state.shadow.css('z-index', $.fn.window.defaults.zIndex++);
				state.window.css('z-index', $.fn.window.defaults.zIndex++);
			},
			onStartDrag: function(e){
				start1(e);
			},
			onDrag: function(e){
				proc1(e);
				return false;
			},
			onStopDrag: function(e){
				stop1(e, 'move');
			}
		});
		
		state.window.resizable({
			disabled: state.options.resizable == false,
			onStartResize:function(e){
				start1(e);
			},
			onResize: function(e){
				proc1(e);
				return false;
			},
			onStopResize: function(e){
				stop1(e, 'resize');
			}
		});

		function start1(e){
			if (state.pmask){state.pmask.remove();}
			state.pmask = $('<div class="window-proxy-mask"></div>').insertAfter(state.window);
			state.pmask.css({
				display: 'none',
				zIndex: $.fn.window.defaults.zIndex++,
				left: e.data.left,
				top: e.data.top,
				width: state.window._outerWidth(),
				height: state.window._outerHeight()
			});
			if (state.proxy){state.proxy.remove();}
			state.proxy = $('<div class="window-proxy"></div>').insertAfter(state.window);
			state.proxy.css({
				display: 'none',
				zIndex: $.fn.window.defaults.zIndex++,
				left: e.data.left,
				top: e.data.top
			});
			state.proxy._outerWidth(e.data.width)._outerHeight(e.data.height);
			state.proxy.hide();
			setTimeout(function(){
				if (state.pmask){state.pmask.show();}
				if (state.proxy){state.proxy.show();}
			}, 500);
		}
		function proc1(e){
			$.extend(e.data, constrain.call(target, e.data.left, e.data.top, e.data.width, e.data.height));
			state.pmask.show();
			state.proxy.css({
				display: 'block',
				left: e.data.left,
				top: e.data.top
			});
			state.proxy._outerWidth(e.data.width);
			state.proxy._outerHeight(e.data.height);
		}
		function stop1(e, method){
			$.extend(e.data, constrain.call(target, e.data.left, e.data.top, e.data.width+0.1, e.data.height+0.1));
			$(target).window(method, e.data);
			state.pmask.remove();
			state.pmask = null;
			state.proxy.remove();
			state.proxy = null;
		}
	}
		
	// when window resize, reset the width and height of the window's mask
	$(function(){
		if (!$._positionFixed){
			$(window).resize(function(){
				$('body>div.window-mask:visible').css({
					width: '',
					height: ''
				});
				setTimeout(function(){
					$('body>div.window-mask:visible').css($.fn.window.getMaskSize());
				}, 50);
			});
		}
	});
	
	$.fn.window = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.window.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.panel(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'window');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'window', {
					options: $.extend({}, $.fn.window.defaults, $.fn.window.parseOptions(this), options)
				});
				if (!state.options.inline){
					document.body.appendChild(this);
				}
			}
			create(this);
			setProperties(this);
		});
	};
	
	$.fn.window.methods = {
		options: function(jq){
			var popts = jq.panel('options');
			var wopts = $.data(jq[0], 'window').options;
			return $.extend(wopts, {
				closed: popts.closed,
				collapsed: popts.collapsed,
				minimized: popts.minimized,
				maximized: popts.maximized
			});
		},
		window: function(jq){
			return $.data(jq[0], 'window').window;
		},
		move: function(jq, param){
			return jq.each(function(){
				moveWindow(this, param);
			});
		},
		hcenter: function(jq){
			return jq.each(function(){
				hcenter(this, true);
			});
		},
		vcenter: function(jq){
			return jq.each(function(){
				vcenter(this, true);
			});
		},
		center: function(jq){
			return jq.each(function(){
				hcenter(this);
				vcenter(this);
				moveWindow(this);
			});
		}
	};

	$.fn.window.getMaskSize = function(target){
		var state = $(target).data('window');
		if (state && state.options.inline){
			return {};
		} else if ($._positionFixed){
			return {position: 'fixed'};
		} else {
			return {
				width: $(document).width(),
				height: $(document).height()
			};
		}
	};
	
	$.fn.window.parseOptions = function(target){
		return $.extend({}, $.fn.panel.parseOptions(target), $.parser.parseOptions(target, [
			{draggable:'boolean',resizable:'boolean',shadow:'boolean',modal:'boolean',inline:'boolean'}
		]));
	};
	
	// Inherited from $.fn.panel.defaults
	$.fn.window.defaults = $.extend({}, $.fn.panel.defaults, {
		zIndex: 9000,
		draggable: true,
		resizable: true,
		shadow: true,
		modal: false,
		border: true,	// possible values are: true,false,'thin','thick'
		inline: false,	// true to stay inside its parent, false to go on top of all elements
		
		// window's property which difference from panel
		title: 'New Window',
		collapsible: true,
		minimizable: true,
		maximizable: true,
		closable: true,
		closed: false,
		constrain: false
		/*
		constrain: function(left,top,width,height){
			return {
				left:left,
				top:top,
				width:width,
				height:height
			};
		}
		*/
	});
})(jQuery);
