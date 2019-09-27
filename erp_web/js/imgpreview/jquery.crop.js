(function(win, $, doc){
	var islteie7 /*@cc_on = (document.documentMode || 7) < 8 @*/,
		cropmask = '<div class="mask_right"></div>';
	cropmask = '<div class="cropmask"><div class="mask_top"></div><div class="mask_middle"><div class="mask_left"></div>' + ( islteie7 ? cropmask : "" ) + '<div class="mask_center"><div class="viewport"><div class="resize resize_n"><div class="point"></div></div><div class="resize resize_e"><div class="point"></div></div><div class="resize resize_s"><div class="point"></div></div><div class="resize resize_w"><div class="point"></div></div><div class="point point_ne"></div><div class="point point_nw"></div><div class="point point_se"></div><div class="point point_sw"></div></div></div>' + ( islteie7 ? "" : cropmask ) + '</div><div class="mask_bottom"></div></div>';

	$.fn.crop = function(onChange, thumb){

		var aera = $("<div>").addClass("cropaera").css("position", "relative"),
			image = $(this).css("margin", "auto"),
			parent = image.parent();

		if(thumb){
			thumb = $(thumb);
			setTimeout(function(){
				thumb.html("");
				thumb.append(image.clone().removeAttr("id").css({
					position: "relative"
				}));
				setThumb();
			}, 300);
		}

		if(parent.hasClass("cropaera")){
			parent.find(".mask_top, .mask_middle, .mask_left, .mask_right").attr("style", "");
			return this;
		}

		aera.insertBefore(image);
		aera.append(image);
		aera.append(cropmask);

		aera.bind("selectstart", function(e){
			e.stopPropagation();
			e.preventDefault();
			return false;
		});

		var drag,
			size,
			maskbox = aera.find(".cropmask");
		mask = {
			bottom: maskbox.find(".mask_bottom"),
			middle: maskbox.find(".mask_middle"),
			viewport: maskbox.find(".viewport"),
			right: maskbox.find(".mask_right"),
			left: maskbox.find(".mask_left"),
			top: maskbox.find(".mask_top"),
			mask: maskbox
		};

		function posint(n){
			return Math.max(n, 0);
		}

		function prec(n){
			return Math.round(n * 100) + "%";
		}

		function getSize(){
			return {
				aeraHeight: mask.mask.height(),
				aeraWidth: mask.mask.width(),
				height: mask.middle.height(),
				width: mask.viewport.width(),
				right: mask.right.width(),
				left: mask.left.width(),
				top: mask.top.height()
			};
		}

		function setThumb(){
			var cropSize = getSize(),
				rx = cropSize.aeraWidth / cropSize.width * thumb.width(),
				ry = cropSize.aeraHeight / cropSize.height * thumb.height();
			$(thumb.children()).css({
				width: rx,
				height: ry,
				left: cropSize.left / cropSize.aeraWidth * -rx,
				top: cropSize.top / cropSize.aeraHeight * -ry
			});
		};

		var setSize = {
			height: function(o) {
				mask.middle.height(Math.min(mask.mask.height() - mask.top.height(), posint(size.height + o.y)));
			},
			right: function(o) {
				mask.right.width(Math.min(mask.mask.width() - mask.left.width(), posint(size.right - o.x)));
			},
			left: function(o) {
				mask.left.width(Math.min(mask.mask.width() - mask.right.width(), posint(size.left + o.x)));
			},
			top: function(o) {
				return posint(size.top + o.y);
			}
		};

		aera.mousedown(function(e) {
			var cursor = $(e.target).css("cursor");
			drag = {
				x: e.pageX,
				y: e.pageY,
				type: cursor.replace(/-resize$/, "")
			};
			size = getSize();
			aera.css("cursor", cursor)
			mask.mask.addClass("ondrag");
		})
		$(document).bind("mouseup blur",function(e) {
			if(drag){
				onChange(getSize());
			}
			aera.css("cursor", "")
			mask.mask.removeClass("ondrag");
			drag = null;
		}).mousemove(function(e) {
			if(drag){
				if(thumb){
					setThumb();
				}
				var type = drag.type,
					offset = {
						x: e.pageX - drag.x,
						y: e.pageY - drag.y
					};
				if(type == "move"){
					if(mask.left.width()){
						setSize.right(offset);
					}
					if(mask.right.width()){
						setSize.left(offset);
					}

					mask.top.height(Math.min(mask.mask.height() - mask.middle.height(), setSize.top(offset)));
				} else {
					if(/n/.test(type)){
						var top = Math.min(mask.bottom.position().top, setSize.top(offset));
						mask.top.height(top);
						mask.middle.height(size.height + size.top - top);
					}
					if(/w/.test(type)){
						setSize.left(offset);
					}
					if(/e/.test(type)){
						setSize.right(offset);
					}
					if(/s/.test(type)){
						setSize.height(offset);
					}
				}
			}
		});
		
		return this;
	};
})(this, this.jQuery, this.document);
