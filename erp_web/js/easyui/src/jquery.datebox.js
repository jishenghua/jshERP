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
 * datebox - EasyUI for jQuery
 * 
 * Dependencies:
 * 	 calendar
 *   combo
 * 
 */
(function($){
	/**
	 * create date box
	 */
	function createBox(target){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		
		$(target).addClass('datebox-f').combo($.extend({}, opts, {
			onShowPanel:function(){
				bindEvents(this);
				setButtons(this);
				setCalendar(this);
				setValue(this, $(this).datebox('getText'), true);
				opts.onShowPanel.call(this);
			}
		}));
		
		/**
		 * if the calendar isn't created, create it.
		 */
		if (!state.calendar){
			var panel = $(target).combo('panel').css('overflow','hidden');
			panel.panel('options').onBeforeDestroy = function(){
				var c = $(this).find('.calendar-shared');
				if (c.length){
					c.insertBefore(c[0].pholder);
				}
			};
			var cc = $('<div class="datebox-calendar-inner"></div>').prependTo(panel);
			if (opts.sharedCalendar){
				var c = $(opts.sharedCalendar);
				if (!c[0].pholder){
					c[0].pholder = $('<div class="calendar-pholder" style="display:none"></div>').insertAfter(c);
				}
				c.addClass('calendar-shared').appendTo(cc);
				if (!c.hasClass('calendar')){
					c.calendar();
				}
				state.calendar = c;
			} else {
				state.calendar = $('<div></div>').appendTo(cc).calendar();
			}

			$.extend(state.calendar.calendar('options'), {
				fit:true,
				border:false,
				onSelect:function(date){
					var target = this.target;
					var opts = $(target).datebox('options');
					opts.onSelect.call(target, date);
					setValue(target, opts.formatter.call(target, date));
					$(target).combo('hidePanel');
				}
			});
		}

		$(target).combo('textbox').parent().addClass('datebox');
		$(target).datebox('initValue', opts.value);
		
		function bindEvents(target){
			var opts = $(target).datebox('options');
			var panel = $(target).combo('panel');
			panel._unbind('.datebox')._bind('click.datebox', function(e){
				if ($(e.target).hasClass('datebox-button-a')){
					var index = parseInt($(e.target).attr('datebox-button-index'));
					opts.buttons[index].handler.call(e.target, target);
				}
			});
		}
		function setButtons(target){
			var panel = $(target).combo('panel');
			if (panel.children('div.datebox-button').length){return}
			var button = $('<div class="datebox-button"><table cellspacing="0" cellpadding="0" style="width:100%"><tr></tr></table></div>').appendTo(panel);
			var tr = button.find('tr');
			for(var i=0; i<opts.buttons.length; i++){
				var td = $('<td></td>').appendTo(tr);
				var btn = opts.buttons[i];
				var t = $('<a class="datebox-button-a" href="javascript:;"></a>').html($.isFunction(btn.text) ? btn.text(target) : btn.text).appendTo(td);
				t.attr('datebox-button-index', i);
			}
			tr.find('td').css('width', (100/opts.buttons.length)+'%');
		}
		function setCalendar(target){
			var panel = $(target).combo('panel');
			var cc = panel.children('div.datebox-calendar-inner');
			panel.children()._outerWidth(panel.width());
			state.calendar.appendTo(cc);
			state.calendar[0].target = target;
			if (opts.panelHeight != 'auto'){
				var height = panel.height();
				panel.children().not(cc).each(function(){
					height -= $(this).outerHeight();
				});
				cc._outerHeight(height);
			}
			state.calendar.calendar('resize');
		}
	}
	
	/**
	 * called when user inputs some value in text box
	 */
	function doQuery(target, q){
		setValue(target, q, true);
	}
	
	/**
	 * called when user press enter key
	 */
	function doEnter(target){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		var current = state.calendar.calendar('options').current;
		if (current){
			setValue(target, opts.formatter.call(target, current));
			$(target).combo('hidePanel');
		}
	}
	
	function setValue(target, value, remainText){
		var state = $.data(target, 'datebox');
		var opts = state.options;
		var calendar = state.calendar;
		calendar.calendar('moveTo', opts.parser.call(target, value));
		if (remainText){
			$(target).combo('setValue', value);
		} else {
			if (value){
				value = opts.formatter.call(target, calendar.calendar('options').current);
			}
			$(target).combo('setText', value).combo('setValue', value);
		}
	}
	
	$.fn.datebox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.datebox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'datebox');
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, 'datebox', {
					options: $.extend({}, $.fn.datebox.defaults, $.fn.datebox.parseOptions(this), options)
				});
			}
			createBox(this);
		});
	};
	
	$.fn.datebox.methods = {
		options: function(jq){
			var copts = jq.combo('options');
			return $.extend($.data(jq[0], 'datebox').options, {
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
				$.data(this, 'datebox', {
					options: $.extend(true, {}, $(from).datebox('options')),
					calendar: $(from).datebox('calendar')
				});
				$(this).addClass('datebox-f');
			});
		},
		calendar: function(jq){	// get the calendar object
			return $.data(jq[0], 'datebox').calendar;
		},
		initValue: function(jq, value){
			return jq.each(function(){
				var opts = $(this).datebox('options');
				var value = opts.value;
				if (value){
					var date = opts.parser.call(this, value);
					value = opts.formatter.call(this, date);
					$(this).datebox('calendar').calendar('moveTo', date);
				}
				$(this).combo('initValue', value).combo('setText', value);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValue(this, value);
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).datebox('options');
				$(this).datebox('setValue', opts.originalValue);
			});
		},
		setDate: function(jq, date){
			return jq.each(function(){
				var opts = $(this).datebox('options');
				$(this).datebox('calendar').calendar('moveTo', date);
				setValue(this, date ? opts.formatter.call(this, date) : '');
			});
		},
		getDate: function(jq){
			if (jq.datebox('getValue')){
				return jq.datebox('calendar').calendar('options').current;
			} else {
				return null;
			}
		}
	};
	
	$.fn.datebox.parseOptions = function(target){
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target, ['sharedCalendar']));
	};
	
	$.fn.datebox.defaults = $.extend({}, $.fn.combo.defaults, {
		panelWidth:250,
		panelHeight:'auto',
		sharedCalendar:null,
		
		keyHandler: {
			up:function(e){},
			down:function(e){},
			left: function(e){},
			right: function(e){},
			enter:function(e){doEnter(this)},
			query:function(q,e){doQuery(this, q)}
		},
		currentText:'Today',
		closeText:'Close',
		okText:'Ok',
		
		buttons:[{
			text: function(target){return $(target).datebox('options').currentText;},
			handler: function(target){
				var opts = $(target).datebox('options');
				var now = new Date();
				var current = new Date(now.getFullYear(), now.getMonth(), now.getDate());
				$(target).datebox('calendar').calendar({
					year:current.getFullYear(),
					month:current.getMonth()+1,
					current:current
				});
				opts.onSelect.call(target, current);
				doEnter(target);
			}
		},{
			text: function(target){return $(target).datebox('options').closeText;},
			handler: function(target){
				$(this).closest('div.combo-panel').panel('close');
			}
		}],
		
		formatter:function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return (m<10?('0'+m):m)+'/'+(d<10?('0'+d):d)+'/'+y;
		},
		parser:function(s){
			var copts = $(this).datebox('calendar').calendar('options');
			if (!s) return new copts.Date();
			var ss = s.split('/');
			var m = parseInt(ss[0],10);
			var d = parseInt(ss[1],10);
			var y = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new copts.Date(y,m-1,d);
			} else {
				return new copts.Date();
			}
		},
		
		onSelect:function(date){}
	});
})(jQuery);
