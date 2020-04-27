(function($){
	$.extend($.fn.datagrid.defaults, {
		dropAccept: 'tr.datagrid-row',
		dragSelection: false,
		dragDelay: 100,
		onBeforeDrag: function(row){},	// return false to deny drag
		onStartDrag: function(row){},
		onStopDrag: function(row){},
		onDragEnter: function(targetRow, sourceRow){},	// return false to deny drop
		onDragOver: function(targetRow, sourceRow){},	// return false to deny drop
		onDragLeave: function(targetRow, sourceRow){},
		onBeforeDrop: function(targetRow, sourceRow, point){},
		onDrop: function(targetRow, sourceRow, point){},	// point:'append','top','bottom'
	});
	$.extend($.fn.datagrid.methods, {
		_appendRows: function(jq, row){
			return jq.each(function(){
				var dg = $(this);
				var rows = $.isArray(row) ? row : [row];
				$.map(rows, function(row){
					dg.datagrid('appendRow', row).datagrid('enableDnd', dg.datagrid('getRows').length-1);
				});
			});
		},
		_insertRows: function(jq, param){
			return jq.each(function(){
				var dg = $(this);
				var index = param.index;
				var row = param.row;
				var rows = $.isArray(row) ? row : [row];
				$.map(rows, function(row, i){
					dg.datagrid('insertRow', {
						index: (index+i),
						row: row
					}).datagrid('enableDnd', index+i);
				});
			});
		},
		_getRowIndexs: function(jq, row){
			var dg = jq;
			var rows = $.isArray(row) ? row : [row];
			var indexs = $.map(rows, function(row){
				return dg.datagrid('getRowIndex', row);
			});
			return $.grep(indexs, function(index){
				if (index >= 0){return true;}
			});
		},
		_deleteRows: function(jq, indexs){
			return jq.each(function(){
				// sort desc
				indexs.sort(function(x,y){
					if (parseInt(x)>parseInt(y)){
						return -1;
					} else {
						return 1;
					}
				});
				for(var i=0; i<indexs.length; i++){
					$(this).datagrid('deleteRow', indexs[i]);
				}
			});
		},
		_setSelections: function(jq){
			return jq.each(function(){
				var rows = $(this).datagrid('getRows');
				for(var i=0; i<rows.length; i++){
					if (rows[i]._selected){
						$(this).datagrid('selectRow', i);
						rows[i]._selected = undefined;
					}
				}
			});
		},
		clearInsertingFlag: function(jq){
			return jq.each(function(){
				var opts = $(this).datagrid('options');
				if (opts.insertingIndex >= 0){
					var tr = opts.finder.getTr(this, opts.insertingIndex);
					tr.removeClass('datagrid-row-top datagrid-row-bottom');
					opts.insertingIndex = -1;
				}
			});
		}
	});
	
	var disabledDroppingRows = [];

	function enableDroppable(aa){
		$.map(aa, function(row){
			$(row).droppable('enable');
		});
	}
	
	$.extend($.fn.datagrid.methods, {
		resetDroppable: function(jq){
			return jq.each(function(){
				var c = $(this).datagrid('getPanel')[0];
				var my = [];
				var left = [];
				for(var i=0; i<disabledDroppingRows.length; i++){
					var t = disabledDroppingRows[i];
					var p = $(t).closest('div.datagrid-wrap');
					if (p.length && p[0] == c){
						my.push(t);
					} else {
						left.push(t);
					}
				}
				disabledDroppingRows = left;
				enableDroppable(my);
			});
		},
		enableDnd: function(jq, index){
			if (!$('#datagrid-dnd-style').length){
				$('<style id="datagrid-dnd-style">' +
					'.datagrid-row-top>td{border-top:1px solid red}' +
					'.datagrid-row-bottom>td{border-bottom:1px solid red}' +
					'</style>'
				).appendTo('head');
			}
			return jq.each(function(){
				var target = this;
				var state = $.data(this, 'datagrid');
				var dg = $(this);
				var opts = state.options;
				
				var draggableOptions = {
					disabled: false,
					revert: true,
					cursor: 'pointer',
					proxy: function(source) {
						var p = $('<div style="z-index:9999999999999"></div>').appendTo('body');
						var draggingRow = getDraggingRow(source);
						var rows = $.isArray(draggingRow) ? draggingRow : [draggingRow];
						$.map(rows, function(row,i){
							var index = dg.datagrid('getRowIndex', row);
							var tr1 = opts.finder.getTr(target, index, 'body', 1);
							var tr2 = opts.finder.getTr(target, index, 'body', 2);
							tr2.clone().removeAttr('id').removeClass('droppable').appendTo(p);
							tr1.clone().removeAttr('id').removeClass('droppable').find('td').insertBefore(p.find('tr:eq('+i+') td:first'));
							$('<td><span class="tree-dnd-icon tree-dnd-no" style="position:static">&nbsp;</span></td>').insertBefore(p.find('tr:eq('+i+') td:first'));
						});
						p.find('td').css('vertical-align','middle');
						p.hide();
						return p;
					},
					deltaX: 15,
					deltaY: 15,
					delay: opts.dragDelay,
					onBeforeDrag:function(e){
						var draggingRow = getDraggingRow(this);
						if (opts.onBeforeDrag.call(target, draggingRow) == false){return false;}
						if ($(e.target).parent().hasClass('datagrid-cell-check')){return false;}
						if (e.which != 1){return false;}
					},
					onStartDrag: function() {
						$(this).draggable('proxy').css({
							left: -10000,
							top: -10000
						});
						var draggingRow = getDraggingRow(this);
						setValid(draggingRow, false);
						state.draggingRow = draggingRow;
						opts.onStartDrag.call(target, draggingRow);
					},
					onDrag: function(e) {
						var x1=e.pageX,y1=e.pageY,x2=e.data.startX,y2=e.data.startY;
						var d = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
						if (d>3){	// when drag a little distance, show the proxy object
							$(this).draggable('proxy').show();
							var tr = opts.finder.getTr(target, parseInt($(this).attr('datagrid-row-index')), 'body');
							$.extend(e.data, {
								startX: tr.offset().left,
								startY: tr.offset().top,
								offsetWidth: 0,
								offsetHeight: 0
							});
						}
						this.pageY = e.pageY;
					},
					onEndDrag: function(e){
						var dd = $(this).data('draggable').droppables.filter(function(){
							var dropObj = $(this);
							if (dropObj.droppable('options').disabled){return false;}
							if (dropObj.hasClass('datagrid-row') && !dropObj.hasClass('datagrid-row-over')){
								return false;
							}
							var p2 = dropObj.offset();
							if (e.pageX > p2.left && e.pageX < p2.left + dropObj.outerWidth()
									&& e.pageY > p2.top && e.pageY < p2.top + dropObj.outerHeight()){
								return true;
							} else {
								return false;
							}
						});
						var trs = dd.filter(function(){
							return $(this).hasClass('datagrid-row');
						});
						if (trs.length){
							dd = trs;
						}
						$(this).data('draggable').droppables = dd;
					},
					onStopDrag:function(){
						enableDroppable(disabledDroppingRows);
						disabledDroppingRows = [];
						setValid(state.draggingRow, true);
						opts.onStopDrag.call(target, state.draggingRow);
					}
				};
				var droppableOptions = {
					disabled: false,
					accept: opts.dropAccept,
					onDragEnter: function(e, source){
						if ($(this).droppable('options').disabled){return;}
						var dTarget = getDataGridTarget(this);
						var dOpts = $(dTarget).datagrid('options');
						var tr = dOpts.finder.getTr(dTarget, null, 'highlight');
						var sRow = getDraggingRow(source);
						var dRow = getRow(this);
						if (tr.length && dRow){
							cb();							
						}

						function cb(){
							if (opts.onDragEnter.call(target, dRow, sRow) == false){
								$(dTarget).datagrid('clearInsertingFlag');
								tr.droppable('disable');
								tr.each(function(){
									disabledDroppingRows.push(this);
								});
							}							
						}
					},
					onDragOver: function(e, source) {
						if ($(this).droppable('options').disabled){
							return;
						}
						if ($.inArray(this, disabledDroppingRows) >= 0){
							return;
						}
						var dTarget = getDataGridTarget(this);
						var dOpts = $(dTarget).datagrid('options');
						var tr = dOpts.finder.getTr(dTarget, null, 'highlight');
						if (tr.length){
							if (!isValid(tr)){
								setProxyFlag(source, false);
								return;
							}
						}
						setProxyFlag(source, true);

						var sRow = getDraggingRow(source);
						var dRow = getRow(this);
						if (tr.length){
							var pageY = source.pageY;
							var top = tr.offset().top;
							var bottom = tr.offset().top + tr.outerHeight();
							$(dTarget).datagrid('clearInsertingFlag');
							dOpts.insertingIndex = tr.attr('datagrid-row-index');
							if (pageY > top + (bottom - top) / 2) {
								tr.addClass('datagrid-row-bottom');
							} else {
								tr.addClass('datagrid-row-top');
							}
							if (dRow){
								cb();
							}
						}

						function cb(){
							if (opts.onDragOver.call(target, dRow, sRow) == false){
								setProxyFlag(source, false);
								$(dTarget).datagrid('clearInsertingFlag');
								tr.droppable('disable');
								tr.each(function(){
									disabledDroppingRows.push(this);
								});
							}
						}
					},
					onDragLeave: function(e, source) {
						if ($(this).droppable('options').disabled){
							return;
						}
						setProxyFlag(source, false);
						var dTarget = getDataGridTarget(this);
						$(dTarget).datagrid('clearInsertingFlag');
						var sRow = getDraggingRow(source);
						var dRow = getRow(this);
						if (dRow){
							opts.onDragLeave.call(target, dRow, sRow);
						}
					},
					onDrop: function(e, source) {
						if ($(this).droppable('options').disabled){
							return;
						}
						var sTarget = getDataGridTarget(source);
						var dTarget = getDataGridTarget(this);
						var dOpts = $(dTarget).datagrid('options');
						var tr = dOpts.finder.getTr(dTarget, null, 'highlight');

						var point = null;
						var sRow = getDraggingRow(source);
						var dRow = null;
						if (tr.length){
							if (!isValid(tr)){
								return;
							}
							point = tr.hasClass('datagrid-row-top') ? 'top' : 'bottom';
							dRow = getRow(tr);
						}
						
						$(dTarget).datagrid('clearInsertingFlag');
						if (opts.onBeforeDrop.call(target, dRow, sRow, point) == false){
							return;
						}
						insert.call(this);
						opts.onDrop.call(target, dRow, sRow, point);
						
						function insert(){
							var destIndex = parseInt(tr.attr('datagrid-row-index'));
							
							if (!point){
								var indexs = $(sTarget).datagrid('_getRowIndexs', sRow);
								$(dTarget).datagrid('_appendRows', sRow);
								$(sTarget).datagrid('_deleteRows', indexs);
								$(dTarget).datagrid('_setSelections');
							} else if (dTarget != sTarget){
								var index = point == 'top' ? destIndex : (destIndex+1);
								if (index >= 0){
									var indexs = $(sTarget).datagrid('_getRowIndexs', sRow);
									$(dTarget).datagrid('_insertRows', {
										index: index,
										row: sRow
									});
									$(sTarget).datagrid('_deleteRows', indexs);
									$(dTarget).datagrid('_setSelections');
								}
							} else {
								var dg = $(dTarget);
								var index = point == 'top' ? destIndex : (destIndex+1);
								if (index >= 0){
									var indexs = dg.datagrid('_getRowIndexs', sRow);
									var destIndex = parseInt(tr.attr('datagrid-row-index'));
									var index = point == 'top' ? destIndex : (destIndex+1);
									if (index >= 0){
										dg.datagrid('_insertRows', {
											index: index,
											row: sRow
										});
										for(var i=0; i<indexs.length; i++){
											if (indexs[i] > index){
												indexs[i] += indexs.length;
											}
										}
										dg.datagrid('_deleteRows', indexs);
										dg.datagrid('_setSelections');
									}
								}
							}
						}
					}
				}
				
				if (index != undefined){
					var trs = opts.finder.getTr(this, index);
				} else {
					var trs = opts.finder.getTr(this, 0, 'allbody');
				}
				trs.draggable(draggableOptions);
				trs.droppable(droppableOptions);
				setDroppable(target);
				
				function setProxyFlag(source, allowed){
					var icon = $(source).draggable('proxy').find('span.tree-dnd-icon');
					icon.removeClass('tree-dnd-yes tree-dnd-no').addClass(allowed ? 'tree-dnd-yes' : 'tree-dnd-no');
				}
				function getRow(tr){
					if (!$(tr).hasClass('datagrid-row')){return null}
					var target = $(tr).closest('div.datagrid-view').children('table')[0];
					var opts = $(target).datagrid('options');
					return opts.finder.getRow(target, $(tr));
				}
				function getDraggingRow(tr){
					if (!$(tr).hasClass('datagrid-row')){return null}
					var target = getDataGridTarget(tr);
					var opts = $(target).datagrid('options');
					var rows = $(target).datagrid('getRows');
					for(var i=0; i<rows.length; i++){
						rows[i]._selected = undefined;
					}
					if (opts.dragSelection){
						if ($(tr).hasClass('datagrid-row-selected')){
							var rows = $(target).datagrid('getSelections');
							$.map(rows, function(row){
								row._selected = true;
							});
							return rows;
						}
					}
					var row = opts.finder.getRow(target, $(tr));
					row._selected = $(tr).hasClass('datagrid-row-selected');
					return row;
				}
				function setDroppable(target){
					getDroppableBody(target).droppable(droppableOptions).droppable('enable');
				}
				function getDataGridTarget(el){
					return $(el).closest('div.datagrid-view').children('table')[0];
				}
				function getDroppableBody(target){
					var dc = $(target).data('datagrid').dc;
					return dc.view;
				}
				function isValid(tr){
					var opts = $(tr).droppable('options');
					if (opts.disabled || opts.accept == 'no-accept'){
						return false;
					} else {
						return true;
					}
				}
				function setValid(rows, valid){
					var accept = valid ? opts.dropAccept : 'no-accept';
					$.map($.isArray(rows)?rows:[rows], function(row){
						var index = $(target).datagrid('getRowIndex', row);
						opts.finder.getTr(target, index).droppable({accept:accept});
					});
				}
			});
		},
		disableDnd: function(jq){
			return jq.each(function(){
				var target = this;
				var state = $.data(this, 'datagrid');
				var dg = $(this);
				var opts = state.options;
				var trs = opts.finder.getTr(this, 0, 'allbody');
				trs.draggable('disable');
				trs.droppable('disable');
			});
		}
		
	});
})(jQuery);
