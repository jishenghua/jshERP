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
 * form - EasyUI for jQuery
 * 
 */
(function($){
	/**
	 * submit the form
	 */
	function ajaxSubmit(target, options){
		var opts = $.data(target, 'form').options;
		$.extend(opts, options||{});
		
		var param = $.extend({}, opts.queryParams);
		if (opts.onSubmit.call(target, param) == false){return;}

		// $(target).find('.textbox-text:focus').blur();
		var input = $(target).find('.textbox-text:focus');
		input.triggerHandler('blur');
		input.focus();

		var disabledFields = null;	// the fields to be disabled
		if (opts.dirty){
			var ff = [];	// all the dirty fields
			$.map(opts.dirtyFields, function(f){
				if ($(f).hasClass('textbox-f')){
					$(f).next().find('.textbox-value').each(function(){
						ff.push(this);
					});
				} else {
					ff.push(f);
				}
			});
			disabledFields = $(target).find('input[name]:enabled,textarea[name]:enabled,select[name]:enabled').filter(function(){
				return $.inArray(this, ff) == -1;
			});
			// disabledFields.attr('disabled', 'disabled');
			disabledFields._propAttr('disabled', true);
		}

		if (opts.ajax){
			if (opts.iframe){
				submitIframe(target, param);
			} else {
				if (window.FormData !== undefined){
					submitXhr(target, param);
				} else {
					submitIframe(target, param);
				}
			}
		} else {
			$(target).submit();
		}

		if (opts.dirty){
			// disabledFields.removeAttr('disabled');
			disabledFields._propAttr('disabled', false);
		}
	}

	function submitIframe(target, param){
		var opts = $.data(target, 'form').options;
		var frameId = 'easyui_frame_' + (new Date().getTime());
		var frame = $('<iframe id='+frameId+' name='+frameId+'></iframe>').appendTo('body')
		frame.attr('src', window.ActiveXObject ? 'javascript:false' : 'about:blank');
		frame.css({
			position:'absolute',
			top:-1000,
			left:-1000
		});
		frame.bind('load', cb);
		
		submit(param);
		
		function submit(param){
			var form = $(target);
			if (opts.url){
				form.attr('action', opts.url);
			}
			var t = form.attr('target'), a = form.attr('action');
			form.attr('target', frameId);
			var paramFields = $();
			try {
				for(var n in param){
					var field = $('<input type="hidden" name="' + n + '">').val(param[n]).appendTo(form);
					paramFields = paramFields.add(field);
				}
				checkState();
				form[0].submit();
			} finally {
				form.attr('action', a);
				t ? form.attr('target', t) : form.removeAttr('target');
				paramFields.remove();
			}
		}
		
		function checkState(){
			var f = $('#'+frameId);
			if (!f.length){return}
			try{
				var s = f.contents()[0].readyState;
				if (s && s.toLowerCase() == 'uninitialized'){
					setTimeout(checkState, 100);
				}
			} catch(e){
				cb();
			}
		}
		
		var checkCount = 10;
		function cb(){
			var f = $('#'+frameId);
			if (!f.length){return}
			f.unbind();
			var data = '';
			try{
				var body = f.contents().find('body');
				data = body.html();
				if (data == ''){
					if (--checkCount){
						setTimeout(cb, 100);
						return;
					}
				}
				var ta = body.find('>textarea');
				if (ta.length){
					data = ta.val();
				} else {
					var pre = body.find('>pre');
					if (pre.length){
						data = pre.html();
					}
				}
			} catch(e){
			}
			opts.success.call(target, data);
			setTimeout(function(){
				f.unbind();
				f.remove();
			}, 100);
		}
	}

	function submitXhr(target, param){
		var opts = $.data(target, 'form').options;
		var formData = new FormData($(target)[0]);
		for(var name in param){
			formData.append(name, param[name]);
		}
		$.ajax({
			url: opts.url,
			type: 'post',
			xhr: function(){
				var xhr = $.ajaxSettings.xhr();
				if (xhr.upload) {
					xhr.upload.addEventListener('progress', function(e){
						if (e.lengthComputable) {
							var total = e.total;
							var position = e.loaded || e.position;
							var percent = Math.ceil(position * 100 / total);
							opts.onProgress.call(target, percent);
						}
					}, false);
				}
				return xhr;
			},
			data: formData,
			dataType: 'html',
			cache: false,
			contentType: false,
			processData: false,
			complete: function(res){
				opts.success.call(target, res.responseText);
			}
		});
	}
	
	
	/**
	 * load form data
	 * if data is a URL string type load from remote site, 
	 * otherwise load from local data object. 
	 */
	function load(target, data){
		var opts = $.data(target, 'form').options;
		
		if (typeof data == 'string'){
			var param = {};
			if (opts.onBeforeLoad.call(target, param) == false) return;
			
			$.ajax({
				url: data,
				data: param,
				dataType: 'json',
				success: function(data){
					_load(data);
				},
				error: function(){
					opts.onLoadError.apply(target, arguments);
				}
			});
		} else {
			_load(data);
		}
		
		function _load(data){
			var form = $(target);
			for(var name in data){
				var val = data[name];
				if (!_checkField(name, val)){
					if (!_loadBox(name, val)){
						form.find('input[name="'+name+'"]').val(val);
						form.find('textarea[name="'+name+'"]').val(val);
						form.find('select[name="'+name+'"]').val(val);
					}
				}
			}
			opts.onLoadSuccess.call(target, data);
			form.form('validate');
		}
		
		/**
		 * check the checkbox and radio fields
		 */
		function _checkField(name, val){
			var plugins = ['switchbutton','radiobutton','checkbox'];
			for(var i=0; i<plugins.length; i++){
				var plugin = plugins[i];
				var cc = $(target).find('['+plugin+'Name="'+name+'"]');
				if (cc.length){
					cc[plugin]('uncheck');
					cc.each(function(){
						if (_isChecked($(this)[plugin]('options').value, val)){
							$(this)[plugin]('check');
						}
					});
					return true;
				}
			}
			var cc = $(target).find('input[name="'+name+'"][type=radio], input[name="'+name+'"][type=checkbox]');
			if (cc.length){
				cc._propAttr('checked', false);
				cc.each(function(){
					if (_isChecked($(this).val(), val)){
						$(this)._propAttr('checked', true);
					}
				});
				return true;
			}
			return false;
		}
		function _isChecked(v, val){
			if (v == String(val) || $.inArray(v, $.isArray(val)?val:[val]) >= 0){
				return true;
			} else {
				return false;
			}
		}
		
		function _loadBox(name, val){
			var field = $(target).find('[textboxName="'+name+'"],[sliderName="'+name+'"]');
			if (field.length){
				for(var i=0; i<opts.fieldTypes.length; i++){
					var type = opts.fieldTypes[i];
					var state = field.data(type);
					if (state){
						if (state.options.multiple || state.options.range){
							field[type]('setValues', val);
						} else {
							field[type]('setValue', val);
						}
						return true;
					}
				}
			}
			return false;
		}
	}
	
	/**
	 * clear the form fields
	 */
	function clear(target){
		$('input,select,textarea', target).each(function(){
			if ($(this).hasClass('textbox-value')){return;}
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
				this.value = '';
			} else if (t == 'file'){
				var file = $(this);
				if (!file.hasClass('textbox-value')){
					var newfile = file.clone().val('');
					newfile.insertAfter(file);
					if (file.data('validatebox')){
						file.validatebox('destroy');
						newfile.validatebox();
					} else {
						file.remove();
					}
				}
			} else if (t == 'checkbox' || t == 'radio'){
				this.checked = false;
			} else if (tag == 'select'){
				this.selectedIndex = -1;
			}
			
		});
		
		var tmp = $();
		var form = $(target);
		var opts = $.data(target, 'form').options;
		for(var i=0; i<opts.fieldTypes.length; i++){
			var type = opts.fieldTypes[i];
			var field = form.find('.'+type+'-f').not(tmp);
			if (field.length && field[type]){
				field[type]('clear');
				tmp = tmp.add(field);
			}
		}
		form.form('validate');
	}
	
	function reset(target){
		target.reset();
		var form = $(target);
		var opts = $.data(target, 'form').options;
		for(var i=opts.fieldTypes.length-1; i>=0; i--){
			var type = opts.fieldTypes[i];
			var field = form.find('.'+type+'-f');
			if (field.length && field[type]){
				field[type]('reset');
			}
		}
		form.form('validate');
	}
	
	/**
	 * set the form to make it can submit with ajax.
	 */
	function setForm(target){
		var options = $.data(target, 'form').options;
		$(target).unbind('.form');
		if (options.ajax){
			$(target).bind('submit.form', function(){
				setTimeout(function(){
					ajaxSubmit(target, options);
				}, 0);
				return false;
			});
		}
		$(target).bind('_change.form', function(e, t){
			if ($.inArray(t, options.dirtyFields) == -1){
				options.dirtyFields.push(t);
			}
			options.onChange.call(this, t);
		}).bind('change.form', function(e){
			var t = e.target;
			if (!$(t).hasClass('textbox-text')){
				if ($.inArray(t, options.dirtyFields) == -1){
					options.dirtyFields.push(t);
				}
				options.onChange.call(this, t);
			}
		});
		setValidation(target, options.novalidate);
	}
	
	function initForm(target, options){
		options = options || {};
		var state = $.data(target, 'form');
		if (state){
			$.extend(state.options, options);
		} else {
			$.data(target, 'form', {
				options: $.extend({}, $.fn.form.defaults, $.fn.form.parseOptions(target), options)
			});
		}
	}
	
	function validate(target){
		if ($.fn.validatebox){
			var t = $(target);
			t.find('.validatebox-text:not(:disabled)').validatebox('validate');
			var invalidbox = t.find('.validatebox-invalid');
			invalidbox.filter(':not(:disabled):first').focus();
			return invalidbox.length == 0;
		}
		return true;
	}
	
	function setValidation(target, novalidate){
		var opts = $.data(target, 'form').options;
		opts.novalidate = novalidate;
		$(target).find('.validatebox-text:not(:disabled)').validatebox(novalidate ? 'disableValidation' : 'enableValidation');
	}
	
	$.fn.form = function(options, param){
		if (typeof options == 'string'){
			this.each(function(){
				initForm(this);
			});
			return $.fn.form.methods[options](this, param);
		}
		
		return this.each(function(){
			initForm(this, options);
			setForm(this);
		});
	};
	
	$.fn.form.methods = {
		options: function(jq){
			return $.data(jq[0], 'form').options;
		},
		submit: function(jq, options){
			return jq.each(function(){
				ajaxSubmit(this, options);
			});
		},
		load: function(jq, data){
			return jq.each(function(){
				load(this, data);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				clear(this);
			});
		},
		reset: function(jq){
			return jq.each(function(){
				reset(this);
			});
		},
		validate: function(jq){
			return validate(jq[0]);
		},
		disableValidation: function(jq){
			return jq.each(function(){
				setValidation(this, true);
			});
		},
		enableValidation: function(jq){
			return jq.each(function(){
				setValidation(this, false);
			});
		},
		resetValidation: function(jq){
			return jq.each(function(){
				$(this).find('.validatebox-text:not(:disabled)').validatebox('resetValidation');
			});
		},
		resetDirty: function(jq){
			return jq.each(function(){
				$(this).form('options').dirtyFields = [];
			});
		}
	};
	
	$.fn.form.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [
			{ajax:'boolean',dirty:'boolean'}
		]), {
			url: (t.attr('action') ? t.attr('action') : undefined)
		});
	};
	
	$.fn.form.defaults = {
		fieldTypes: ['tagbox','combobox','combotree','combogrid','combotreegrid','datetimebox','datebox','timepicker','combo',
		        'datetimespinner','timespinner','numberspinner','spinner',
		        'slider','searchbox','numberbox','passwordbox','filebox','textbox','switchbutton','radiobutton','checkbox'],
		novalidate: false,
		ajax: true,
		iframe: true,
		dirty: false,
		dirtyFields: [],
		url: null,
		queryParams: {},
		onSubmit: function(param){return $(this).form('validate');},
		onProgress: function(percent){},
		success: function(data){},
		onBeforeLoad: function(param){},
		onLoadSuccess: function(data){},
		onLoadError: function(){},
		onChange: function(target){}
	};
})(jQuery);
