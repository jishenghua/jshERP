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
 * parser - EasyUI for jQuery
 * 
 */

(function($){
	$.easyui = {
		/**
		 * Get the index of array item, return -1 when the item is not found.
		 */
		indexOfArray: function(a, o, id){
			for(var i=0,len=a.length; i<len; i++){
				if (id == undefined){
					if (a[i] == o){return i;}
				} else {
					if (a[i][o] == id){return i;}
				}
			}
			return -1;
		},
		/**
		 * Remove array item, 'o' parameter can be item object or id field name.
		 * When 'o' parameter is the id field name, the 'id' parameter is valid.
		 */
		removeArrayItem: function(a, o, id){
			if (typeof o == 'string'){
				for(var i=0,len=a.length; i<len; i++){
					if (a[i][o] == id){
						a.splice(i, 1);
						return;
					}
				}
			} else {
				var index = this.indexOfArray(a,o);
				if (index != -1){
					a.splice(index, 1);
				}
			}
		},
		/**
		 * Add un-duplicate array item, 'o' parameter is the id field name, if the 'r' object is exists, deny the action.
		 */
		addArrayItem: function(a, o, r){
			var index = this.indexOfArray(a, o, r ? r[o] : undefined);
			if (index == -1){
				a.push(r ? r : o);
			} else {
				a[index] = r ? r : o;
			}
		},
		getArrayItem: function(a, o, id){
			var index = this.indexOfArray(a, o, id);
			return index==-1 ? null : a[index];
		},
		forEach: function(data, deep, callback){
			var nodes = [];
			for(var i=0; i<data.length; i++){
				nodes.push(data[i]);
			}
			while(nodes.length){
				var node = nodes.shift();
				if (callback(node) == false){return;}
				if (deep && node.children){
					for(var i=node.children.length-1; i>=0; i--){
						nodes.unshift(node.children[i]);
					}
				}
			}
		}
	};

	$.parser = {
		auto: true,
		emptyFn: function(){},
		onComplete: function(context){},
		plugins:['draggable','droppable','resizable','pagination','tooltip',
		         'linkbutton','menu','sidemenu','menubutton','splitbutton','switchbutton','progressbar','radiobutton','checkbox',
				 'tree','textbox','passwordbox','maskedbox','filebox','combo','combobox','combotree','combogrid','combotreegrid','tagbox','numberbox','validatebox','searchbox',
				 'spinner','numberspinner','timespinner','datetimespinner','calendar','datebox','datetimebox','timepicker','slider',
				 'layout','panel','datagrid','propertygrid','treegrid','datalist','tabs','accordion','window','dialog','form'
		],
		parse: function(context){
			var aa = [];
			for(var i=0; i<$.parser.plugins.length; i++){
				var name = $.parser.plugins[i];
				var r = $('.easyui-' + name, context);
				if (r.length){
					if (r[name]){
						r.each(function(){
							$(this)[name]($.data(this, 'options')||{});
						});
					} else {
						aa.push({name:name,jq:r});
					}
				}
			}
			if (aa.length && window.easyloader){
				var names = [];
				for(var i=0; i<aa.length; i++){
					names.push(aa[i].name);
				}
				easyloader.load(names, function(){
					for(var i=0; i<aa.length; i++){
						var name = aa[i].name;
						var jq = aa[i].jq;
						jq.each(function(){
							$(this)[name]($.data(this, 'options')||{});
						});
					}
					$.parser.onComplete.call($.parser, context);
				});
			} else {
				$.parser.onComplete.call($.parser, context);
			}
		},
		
		parseValue: function(property, value, parent, delta){
			delta = delta || 0;
			var v = $.trim(String(value||''));
			var endchar = v.substr(v.length-1, 1);
			if (endchar == '%'){
				v = parseFloat(v.substr(0, v.length-1));
				if (property.toLowerCase().indexOf('width') >= 0){
					delta += parent[0].offsetWidth-parent[0].clientWidth;
					v = Math.floor((parent.width()-delta) * v / 100.0);
				} else {
					delta += parent[0].offsetHeight-parent[0].clientHeight;
					v = Math.floor((parent.height()-delta) * v / 100.0);
				}
			} else {
				v = parseInt(v) || undefined;
			}
			return v;
		},
		
		/**
		 * parse options, including standard 'data-options' attribute.
		 * 
		 * calling examples:
		 * $.parser.parseOptions(target);
		 * $.parser.parseOptions(target, ['id','title','width',{fit:'boolean',border:'boolean'},{min:'number'}]);
		 */
		parseOptions: function(target, properties){
			var t = $(target);
			var options = {};
			
			var s = $.trim(t.attr('data-options'));
			if (s){
				if (s.substring(0, 1) != '{'){
					s = '{' + s + '}';
				}
				options = (new Function('return ' + s))();
			}
			$.map(['width','height','left','top','minWidth','maxWidth','minHeight','maxHeight'], function(p){
				var pv = $.trim(target.style[p] || '');
				if (pv){
					if (pv.indexOf('%') == -1){
						pv = parseInt(pv);
						if (isNaN(pv)){
							pv = undefined;
						}
					}
					options[p] = pv;
				}
			});
				
			if (properties){
				var opts = {};
				for(var i=0; i<properties.length; i++){
					var pp = properties[i];
					if (typeof pp == 'string'){
						opts[pp] = t.attr(pp);
					} else {
						for(var name in pp){
							var type = pp[name];
							if (type == 'boolean'){
								opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
							} else if (type == 'number'){
								opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
							}
						}
					}
				}
				$.extend(options, opts);
			}
			return options;
		},
		parseVars: function(){
			var d = $('<div style="position:absolute;top:-1000px;width:100px;height:100px;padding:5px"></div>').appendTo('body');
			$._boxModel = d.outerWidth()!=100;
			d.remove();
			d = $('<div style="position:fixed"></div>').appendTo('body');
			$._positionFixed = (d.css('position') == 'fixed');
			d.remove();
		}
	};
	$(function(){
		$.parser.parseVars();
		if (!window.easyloader && $.parser.auto){
			$.parser.parse();
		}
	});
	
	/**
	 * extend plugin to set box model width
	 */
	$.fn._outerWidth = function(width){
		if (width == undefined){
			if (this[0] == window){
				return this.width() || document.body.clientWidth;
			}
			return this.outerWidth()||0;
		}
		return this._size('width', width);
	};
	
	/**
	 * extend plugin to set box model height
	 */
	$.fn._outerHeight = function(height){
		if (height == undefined){
			if (this[0] == window){
				return this.height() || document.body.clientHeight;
			}
			return this.outerHeight()||0;
		}
		return this._size('height', height);
	};
	
	$.fn._scrollLeft = function(left){
		if (left == undefined){
			return this.scrollLeft();
		} else {
			return this.each(function(){$(this).scrollLeft(left)});
		}
	};
	
	$.fn._propAttr = $.fn.prop || $.fn.attr;
	$.fn._bind = $.fn.on;
	$.fn._unbind = $.fn.off;
	
	$.fn._size = function(options, parent){
		if (typeof options == 'string'){
			if (options == 'clear'){
				return this.each(function(){
					$(this).css({width:'',minWidth:'',maxWidth:'',height:'',minHeight:'',maxHeight:''});
				});
			} else if (options == 'fit'){
				return this.each(function(){
					_fit(this, this.tagName=='BODY' ? $('body') : $(this).parent(), true);
				});
			} else if (options == 'unfit'){
				return this.each(function(){
					_fit(this, $(this).parent(), false);
				});
			} else {
				if (parent == undefined){
					return _css(this[0], options);
				} else {
					return this.each(function(){
						_css(this, options, parent);
					});
				}
			}
		} else {
			return this.each(function(){
				parent = parent || $(this).parent();
				$.extend(options, _fit(this, parent, options.fit)||{});
				var r1 = _setSize(this, 'width', parent, options);
				var r2 = _setSize(this, 'height', parent, options);
				if (r1 || r2){
					$(this).addClass('easyui-fluid');
				} else {
					$(this).removeClass('easyui-fluid');
				}
			});
		}
		
		function _fit(target, parent, fit){
			if (!parent.length){return false;}
			var t = $(target)[0];
			var p = parent[0];
			var fcount = p.fcount || 0;
			if (fit){
				if (!t.fitted){
					t.fitted = true;
					p.fcount = fcount + 1;
					$(p).addClass('panel-noscroll');
					if (p.tagName == 'BODY'){
						$('html').addClass('panel-fit');
					}
				}
				return {
					width: ($(p).width()||1),
					height: ($(p).height()||1)
				};
			} else {
				if (t.fitted){
					t.fitted = false;
					p.fcount = fcount - 1;
					if (p.fcount == 0){
						$(p).removeClass('panel-noscroll');
						if (p.tagName == 'BODY'){
							$('html').removeClass('panel-fit');
						}
					}
				}
				return false;
			}
		}
		function _setSize(target, property, parent, options){
			var t = $(target);
			var p = property;
			var p1 = p.substr(0,1).toUpperCase() + p.substr(1);
			var min = $.parser.parseValue('min'+p1, options['min'+p1], parent);// || 0;
			var max = $.parser.parseValue('max'+p1, options['max'+p1], parent);// || 99999;
			var val = $.parser.parseValue(p, options[p], parent);
			var fluid = (String(options[p]||'').indexOf('%') >= 0 ? true : false);
			
			if (!isNaN(val)){
				var v = Math.min(Math.max(val, min||0), max||99999);
				if (!fluid){
					options[p] = v;
				}
				t._size('min'+p1, '');
				t._size('max'+p1, '');
				t._size(p, v);
			} else {
				t._size(p, '');
				t._size('min'+p1, min);
				t._size('max'+p1, max);
			}
			return fluid || options.fit;
		}
		function _css(target, property, value){
			var t = $(target);
			if (value == undefined){
				value = parseInt(target.style[property]);
				if (isNaN(value)){return undefined;}
				if ($._boxModel){
					value += getDeltaSize();
				}
				return value;
			} else if (value === ''){
				t.css(property, '');
			} else {
				if ($._boxModel){
					value -= getDeltaSize();
					if (value < 0){value = 0;}
				}
				t.css(property, value+'px');
			}
			function getDeltaSize(){
				if (property.toLowerCase().indexOf('width') >= 0){
					return t.outerWidth() - t.width();
				} else {
					return t.outerHeight() - t.height();
				}
			}
		}
	};
	
})(jQuery);

/**
 * support for mobile devices
 */
(function($){
	var longTouchTimer = null;
	var dblTouchTimer = null;
	var isDblClick = false;
	
	function onTouchStart(e){
		if (e.touches.length != 1){return}
		if (!isDblClick){
			isDblClick = true;
			dblClickTimer = setTimeout(function(){
				isDblClick = false;
			}, 500);
		} else {
			clearTimeout(dblClickTimer);
			isDblClick = false;
			fire(e, 'dblclick');
//			e.preventDefault();
		}
		longTouchTimer = setTimeout(function(){
			fire(e, 'contextmenu', 3);
		}, 1000);
		fire(e, 'mousedown');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	function onTouchMove(e){
		if (e.touches.length != 1){return}
		if (longTouchTimer){
			clearTimeout(longTouchTimer);
		}
		fire(e, 'mousemove');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	function onTouchEnd(e){
//		if (e.touches.length > 0){return}
		if (longTouchTimer){
			clearTimeout(longTouchTimer);
		}
		fire(e, 'mouseup');
		if ($.fn.draggable.isDragging || $.fn.resizable.isResizing){
			e.preventDefault();
		}
	}
	
	function fire(e, name, which){
		var event = new $.Event(name);
		event.pageX = e.changedTouches[0].pageX;
		event.pageY = e.changedTouches[0].pageY;
		event.which = which || 1;
		$(e.target).trigger(event);
	}
	
	if (document.addEventListener){
		document.addEventListener("touchstart", onTouchStart, true);
		document.addEventListener("touchmove", onTouchMove, true);
		document.addEventListener("touchend", onTouchEnd, true);
	}
})(jQuery);

