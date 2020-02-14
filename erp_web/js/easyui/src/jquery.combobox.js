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
 * combobox - EasyUI for jQuery
 * 
 * Dependencies:
 *   combo
 * 
 */
(function($){
	function getRowIndex(target, value){
		var state = $.data(target, 'combobox');
		return $.easyui.indexOfArray(state.data, state.options.valueField, value);
	}
	
	/**
	 * scroll panel to display the specified item
	 */
	function scrollTo(target, value){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		var item = opts.finder.getEl(target, value);
		if (item.length){
			if (item.position().top <= 0){
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else if (item.position().top + item.outerHeight() > panel.height()){
				var h = panel.scrollTop() + item.position().top + item.outerHeight() - panel.height();
				panel.scrollTop(h);
			}
		}
		panel.triggerHandler('scroll');	// trigger the group sticking
	}
	
	function nav(target, dir){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combobox('panel');
		var item = panel.children('div.combobox-item-hover');
		if (!item.length){
			item = panel.children('div.combobox-item-selected');
		}
		item.removeClass('combobox-item-hover');
		var firstSelector = 'div.combobox-item:visible:not(.combobox-item-disabled):first';
		var lastSelector = 'div.combobox-item:visible:not(.combobox-item-disabled):last';
		if (!item.length){
			item = panel.children(dir=='next' ? firstSelector : lastSelector);
		} else {
			if (dir == 'next'){
				item = item.nextAll(firstSelector);
				if (!item.length){
					item = panel.children(firstSelector);
				}
			} else {
				item = item.prevAll(firstSelector);
				if (!item.length){
					item = panel.children(lastSelector);
				}
			}
		}
		if (item.length){
			item.addClass('combobox-item-hover');
			var row = opts.finder.getRow(target, item);
			if (row){
				$(target).combobox('scrollTo', row[opts.valueField]);
				if (opts.selectOnNavigation){
					select(target, row[opts.valueField]);
				}
			}
		}
	}
	
	/**
	 * select the specified value
	 */
	function select(target, value, remainText){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		if ($.inArray(value+'', values) == -1){
			if (opts.multiple){
				values.push(value);
			} else {
				values = [value];
			}
			setValues(target, values, remainText);
		}
	}
	
	/**
	 * unselect the specified value
	 */
	function unselect(target, value){
		var opts = $.data(target, 'combobox').options;
		var values = $(target).combo('getValues');
		var index = $.inArray(value+'', values);
		if (index >= 0){
			values.splice(index, 1);
			setValues(target, values);
		}
	}
	
	/**
	 * set values
	 */
	function setValues(target, values, remainText){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		
		if (!$.isArray(values)){
			values = values.split(opts.separator);
		}
		if (!opts.multiple){
			values = values.length ? [values[0]] : [''];
		}

		// unselect the old rows
		var oldValues = $(target).combo('getValues');
		if (panel.is(':visible')){
			panel.find('.combobox-item-selected').each(function(){
				var row = opts.finder.getRow(target, $(this));
				if (row){
					if ($.easyui.indexOfArray(oldValues, row[opts.valueField]) == -1){
						$(this).removeClass('combobox-item-selected');
					}
				}
			});
		}
		$.map(oldValues, function(v){
			if ($.easyui.indexOfArray(values, v) == -1){
				var el = opts.finder.getEl(target, v);
				if (el.hasClass('combobox-item-selected')){
					el.removeClass('combobox-item-selected');
					opts.onUnselect.call(target, opts.finder.getRow(target, v));
				}
			}
		});

		var theRow = null;
		var vv = [], ss = [];
		for(var i=0; i<values.length; i++){
			var v = values[i];
			var s = v;
			var row = opts.finder.getRow(target, v);
			if (row){
				s = row[opts.textField];
				theRow = row;
				var el = opts.finder.getEl(target, v);
				if (!el.hasClass('combobox-item-selected')){
					el.addClass('combobox-item-selected');
					opts.onSelect.call(target, row);
				}
			} else {
				s = findText(v, opts.mappingRows) || v;
			}
			vv.push(v);
			ss.push(s);
		}

		if (!remainText){
			$(target).combo('setText', ss.join(opts.separator));
		}
		if (opts.showItemIcon){
			var tb = $(target).combobox('textbox');
			tb.removeClass('textbox-bgicon ' + opts.textboxIconCls);
			if (theRow && theRow.iconCls){
				tb.addClass('textbox-bgicon ' + theRow.iconCls);
				opts.textboxIconCls = theRow.iconCls;
			}
		}
		$(target).combo('setValues', vv);
		panel.triggerHandler('scroll');	// trigger the group sticking

		function findText(value, a){
			var item = $.easyui.getArrayItem(a, opts.valueField, value);
			return item ? item[opts.textField] : undefined;
		}
	}
	
	/**
	 * load data, the old list items will be removed.
	 */
	function loadData(target, data, remainText){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		state.data = opts.loadFilter.call(target, data);

		opts.view.render.call(opts.view, target, $(target).combo('panel'), state.data);		

		var vv = $(target).combobox('getValues');
		$.easyui.forEach(state.data, false, function(row){
			if (row['selected']){
				$.easyui.addArrayItem(vv, row[opts.valueField]+'');
			}
		});
		if (opts.multiple){
			setValues(target, vv, remainText);
		} else {
			setValues(target, vv.length ? [vv[vv.length-1]] : [], remainText);
		}
		
		opts.onLoadSuccess.call(target, data);
	}
	
	/**
	 * request remote data if the url property is setted.
	 */
	function request(target, url, param, remainText){
		var opts = $.data(target, 'combobox').options;
		if (url){
			opts.url = url;
		}
		param = $.extend({}, opts.queryParams, param||{});
//		param = param || {};
		
		if (opts.onBeforeLoad.call(target, param) == false) return;

		opts.loader.call(target, param, function(data){
			loadData(target, data, remainText);
		}, function(){
			opts.onLoadError.apply(this, arguments);
		});
	}
	
	/**
	 * do the query action
	 */
	function doQuery(target, q){
		var state = $.data(target, 'combobox');
		var opts = state.options;

		var highlightItem = $();
		var qq = opts.multiple ? q.split(opts.separator) : [q];
		if (opts.mode == 'remote'){
			_setValues(qq);
			request(target, null, {q:q}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('.combobox-item-hover').removeClass('combobox-item-hover');
			panel.find('.combobox-item,.combobox-group').hide();
			var data = state.data;
			var vv = [];
			$.map(qq, function(q){
				q = $.trim(q);
				var value = q;
				var group = undefined;
				highlightItem = $();
				for(var i=0; i<data.length; i++){
					var row = data[i];
					if (opts.filter.call(target, q, row)){
						var v = row[opts.valueField];
						var s = row[opts.textField];
						var g = row[opts.groupField];
						var item = opts.finder.getEl(target, v).show();
						if (s.toLowerCase() == q.toLowerCase()){
							value = v;
							if (opts.reversed){
								highlightItem = item;
							} else {
								select(target, v, true);
							}
						}
						if (opts.groupField && group != g){
							opts.finder.getGroupEl(target, g).show();
							group = g;
						}
					}
				}
				vv.push(value);
			});
			_setValues(vv);
		}
		function _setValues(vv){
			if (opts.reversed){
				highlightItem.addClass('combobox-item-hover');
			} else {
				setValues(target, opts.multiple ? (q?vv:[]) : vv, true);
			}
		}
	}
	
	function doEnter(target){
		var t = $(target);
		var opts = t.combobox('options');
		var panel = t.combobox('panel');
		var item = panel.children('div.combobox-item-hover');
		if (item.length){
			item.removeClass('combobox-item-hover');
			var row = opts.finder.getRow(target, item);
			var value = row[opts.valueField];
			if (opts.multiple){
				if (item.hasClass('combobox-item-selected')){
					t.combobox('unselect', value);
				} else {
					t.combobox('select', value);
				}
			} else {
				t.combobox('select', value);
			}
		}
		var vv = [];
		$.map(t.combobox('getValues'), function(v){
			if (getRowIndex(target, v) >= 0){
				vv.push(v);
			}
		});
		t.combobox('setValues', vv);
		if (!opts.multiple){
			t.combobox('hidePanel');
		}
	}
	
	/**
	 * create the component
	 */
	function create(target){
		var state = $.data(target, 'combobox');
		var opts = state.options;
		
		$(target).addClass('combobox-f');
		$(target).combo($.extend({}, opts, {
			onShowPanel: function(){
				$(this).combo('panel').find('div.combobox-item:hidden,div.combobox-group:hidden').show();
				setValues(this, $(this).combobox('getValues'), true);
				$(this).combobox('scrollTo', $(this).combobox('getValue'));
				opts.onShowPanel.call(this);
			}
		}));

	}

	function mouseoverHandler(e){
		$(this).children('div.combobox-item-hover').removeClass('combobox-item-hover');
		var item = $(e.target).closest('div.combobox-item');
		if (!item.hasClass('combobox-item-disabled')){
			item.addClass('combobox-item-hover');
		}
		e.stopPropagation();
	}
	function mouseoutHandler(e){
		$(e.target).closest('div.combobox-item').removeClass('combobox-item-hover');
		e.stopPropagation();
	}
	function clickHandler(e){
		var target = $(this).panel('options').comboTarget;
		if (!target){return;}
		var opts = $(target).combobox('options');
		var item = $(e.target).closest('div.combobox-item');
		if (!item.length || item.hasClass('combobox-item-disabled')){return}
		var row = opts.finder.getRow(target, item);
		if (!row){return;}
		if (opts.blurTimer){
			clearTimeout(opts.blurTimer);
			opts.blurTimer = null;
		}
		opts.onClick.call(target, row);
		var value = row[opts.valueField];
		if (opts.multiple){
			if (item.hasClass('combobox-item-selected')){
				unselect(target, value);
			} else {
				select(target, value);
			}
		} else {
			$(target).combobox('setValue', value).combobox('hidePanel');
		}
		e.stopPropagation();
	}
	function scrollHandler(e){
		var target = $(this).panel('options').comboTarget;
		if (!target){return;}
		var opts = $(target).combobox('options');
		if (opts.groupPosition == 'sticky'){
			var stick = $(this).children('.combobox-stick');
			if (!stick.length){
				stick = $('<div class="combobox-stick"></div>').appendTo(this);
			}
			stick.hide();
			var state = $(target).data('combobox');
			$(this).children('.combobox-group:visible').each(function(){
				var g = $(this);
				var groupData = opts.finder.getGroup(target, g);
				var rowData = state.data[groupData.startIndex + groupData.count - 1];
				var last = opts.finder.getEl(target, rowData[opts.valueField]);
				if (g.position().top < 0 && last.position().top > 0){
					stick.show().html(g.html());
					return false;
				}
			});
		}
	}
	
	$.fn.combobox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.combobox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'combobox');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'combobox', {
					options: $.extend({}, $.fn.combobox.defaults, $.fn.combobox.parseOptions(this), options),
					data: []
				});
			}
			create(this);
			if (state.options.data){
				loadData(this, state.options.data);
			} else {
				var data = $.fn.combobox.parseData(this);
				if (data.length){
					loadData(this, data);
				}
			}
			request(this);
		});
	};
	
	
	$.fn.combobox.methods = {
		options: function(jq){
			var copts = jq.combo('options');
			return $.extend($.data(jq[0], 'combobox').options, {
				width: copts.width,
				height: copts.height,
				originalValue: copts.originalValue,
				disabled: copts.disabled,
				readonly: copts.readonly
			});
		},
		cloneFrom: function(jq, from){
			return jq.each(function(){
				$(this).combo('cloneFrom', from);
				$.data(this, 'combobox', $(from).data('combobox'));
				$(this).addClass('combobox-f').attr('comboboxName', $(this).attr('textboxName'));
			});
		},
		getData: function(jq){
			return $.data(jq[0], 'combobox').data;
		},
		setValues: function(jq, values){
			return jq.each(function(){
				var opts = $(this).combobox('options');
				if ($.isArray(values)){
					values = $.map(values, function(value){
						if (value && typeof value == 'object'){
							$.easyui.addArrayItem(opts.mappingRows, opts.valueField, value);
							return value[opts.valueField];
						} else {
							return value;
						}
					});
				}
				setValues(this, values);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				$(this).combobox('setValues', $.isArray(value)?value:[value]);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				setValues(this, []);
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).combobox('options');
				if (opts.multiple){
					$(this).combobox('setValues', opts.originalValue);
				} else {
					$(this).combobox('setValue', opts.originalValue);
				}
			});
		},
		loadData: function(jq, data){
			return jq.each(function(){
				loadData(this, data);
			});
		},
		reload: function(jq, url){
			return jq.each(function(){
				if (typeof url == 'string'){
					request(this, url);
				} else {
					if (url){
						var opts = $(this).combobox('options');
						opts.queryParams = url;
					}
					request(this);
				}
			});
		},
		select: function(jq, value){
			return jq.each(function(){
				select(this, value);
			});
		},
		unselect: function(jq, value){
			return jq.each(function(){
				unselect(this, value);
			});
		},
		scrollTo: function(jq, value){
			return jq.each(function(){
				scrollTo(this, value);
			});
		}
	};
	
	$.fn.combobox.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target,[
			'valueField','textField','groupField','groupPosition','mode','method','url',
			{showItemIcon:'boolean',limitToList:'boolean'}
		]));
	};
	
	$.fn.combobox.parseData = function(target){
		var data = [];
		var opts = $(target).combobox('options');
		$(target).children().each(function(){
			if (this.tagName.toLowerCase() == 'optgroup'){
				var group = $(this).attr('label');
				$(this).children().each(function(){
					_parseItem(this, group);
				});
			} else {
				_parseItem(this);
			}
		});
		return data;
		
		function _parseItem(el, group){
			var t = $(el);
			var row = {};
			row[opts.valueField] = t.attr('value')!=undefined ? t.attr('value') : t.text();
			row[opts.textField] = t.text();
			row['iconCls'] = $.parser.parseOptions(el, ['iconCls']).iconCls;
			row['selected'] = t.is(':selected');
			row['disabled'] = t.is(':disabled');
			if (group){
				opts.groupField = opts.groupField || 'group';
				row[opts.groupField] = group;
			}
			data.push(row);
		}
	};

	var COMBOBOX_SERNO = 0;
	var defaultView = {
		render: function(target, container, data){
			var state = $.data(target, 'combobox');
			var opts = state.options;
			var prefixId = $(target).attr('id')||'';
			
			COMBOBOX_SERNO++;
			state.itemIdPrefix = prefixId + '_easyui_combobox_i' + COMBOBOX_SERNO;
			state.groupIdPrefix = prefixId + '_easyui_combobox_g' + COMBOBOX_SERNO;		
			state.groups = [];
			
			var dd = [];
			var group = undefined;
			for(var i=0; i<data.length; i++){
				var row = data[i];
				var v = row[opts.valueField]+'';
				var s = row[opts.textField];
				var g = row[opts.groupField];
				
				if (g){
					if (group != g){
						group = g;
						state.groups.push({
							value: g,
							startIndex: i,
							count: 1
						});
						dd.push('<div id="' + (state.groupIdPrefix+'_'+(state.groups.length-1)) + '" class="combobox-group">');
						dd.push(opts.groupFormatter ? opts.groupFormatter.call(target, g) : g);
						dd.push('</div>');
					} else {
						state.groups[state.groups.length-1].count++;
					}
				} else {
					group = undefined;
				}
				
				var cls = 'combobox-item' + (row.disabled ? ' combobox-item-disabled' : '') + (g ? ' combobox-gitem' : '');
				dd.push('<div id="' + (state.itemIdPrefix+'_'+i) + '" class="' + cls + '">');
				if (opts.showItemIcon && row.iconCls){
					dd.push('<span class="combobox-icon ' + row.iconCls + '"></span>');
				}
				dd.push(opts.formatter ? opts.formatter.call(target, row) : s);
				dd.push('</div>');
			}
			$(container).html(dd.join(''));
		}
	};
	
	$.fn.combobox.defaults = $.extend({}, $.fn.combo.defaults, {
		valueField: 'value',
		textField: 'text',
		groupPosition: 'static',	// or 'sticky'
		groupField: null,
		groupFormatter: function(group){return group;},
		mode: 'local',	// or 'remote'
		method: 'post',
		url: null,
		data: null,
		queryParams: {},
		showItemIcon: false,
		limitToList: false,	// limit the inputed values to the listed items
		unselectedValues: [],
		mappingRows: [],
		view: defaultView,
		
		keyHandler: {
			up: function(e){nav(this,'prev');e.preventDefault()},
			down: function(e){nav(this,'next');e.preventDefault()},
			left: function(e){},
			right: function(e){},
			enter: function(e){doEnter(this)},
			query: function(q,e){doQuery(this, q)}
		},
		inputEvents: $.extend({}, $.fn.combo.defaults.inputEvents, {
			blur: function(e){
				$.fn.combo.defaults.inputEvents.blur(e);
				var target = e.data.target;
				var opts = $(target).combobox('options');
				if (opts.reversed || opts.limitToList){
					if (opts.blurTimer){
						clearTimeout(opts.blurTimer);
					}
					opts.blurTimer = setTimeout(function(){
						var existing = $(target).parent().length;
						if (existing){
							if (opts.reversed){
								$(target).combobox('setValues', $(target).combobox('getValues'));
							} else if (opts.limitToList){
								//doEnter(target);
								var vv = [];
								$.map($(target).combobox('getValues'), function(v){
									var index = $.easyui.indexOfArray($(target).combobox('getData'), opts.valueField, v);
									if (index >= 0){
										vv.push(v);
									}
								});
								$(target).combobox('setValues', vv);
							}
							opts.blurTimer = null;
						}
					},50);
				}
			}
		}),
		panelEvents: {
			mouseover: mouseoverHandler,
			mouseout: mouseoutHandler,
			mousedown: function(e){
				e.preventDefault();
				e.stopPropagation();
			},
			click: clickHandler,
			scroll: scrollHandler
		},
		filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase()) >= 0;
		},
		formatter: function(row){
			var opts = $(this).combobox('options');
			return row[opts.textField];
		},
		loader: function(param, success, error){
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					success(data);
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		},
		loadFilter: function(data){
			return data;
		},
		finder:{
			getEl:function(target, value){
				var index = getRowIndex(target, value);
				var id = $.data(target, 'combobox').itemIdPrefix + '_' + index;
				return $('#'+id);
			},
			getGroupEl:function(target, gvalue){
				var state = $.data(target, 'combobox');
				var index = $.easyui.indexOfArray(state.groups, 'value', gvalue);
				var id = state.groupIdPrefix + '_' + index;
				return $('#'+id);
			},
			getGroup:function(target, p){
				var state = $.data(target, 'combobox');
				var index = p.attr('id').substr(state.groupIdPrefix.length+1);
				return state.groups[parseInt(index)];
			},
			getRow:function(target, p){
				var state = $.data(target, 'combobox');
				var index = (p instanceof $) ? p.attr('id').substr(state.itemIdPrefix.length+1) : getRowIndex(target, p);
				return state.data[parseInt(index)];
			}
		},
		
		onBeforeLoad: function(param){},
		onLoadSuccess: function(data){},
		onLoadError: function(){},
		onSelect: function(record){},
		onUnselect: function(record){},
		onClick: function(record){}
	});
})(jQuery);
