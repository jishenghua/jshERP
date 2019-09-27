function imagepreview(file, view, call) {

	var maxHeight = view.clientHeight,
		maxWidth = view.clientWidth,
		doc = document;
		
	function setsize(info, img){
		var iwidth, iheight;
		if((info.width / maxWidth) > (info.height / maxHeight)){
			iwidth =  maxWidth;
			iheight = Math.round(iwidth * info.height / info.width);
		} else {
			iheight = maxHeight;
			iwidth = Math.round(iheight * info.width / info.height);
		}
		with(view.style){
			height = iheight + "px";
			width = iwidth + "px";
			overflow = "hidden";
		}
		if(img){
			with(img.style){
				height = width = "100%";
			}
			view.innerHTML = "";
			view.appendChild(img);
		}

	}

	try{
		new FileReader();
		file.addEventListener("change", function(e){
			var image = this.files[0];
			function fireError(){
				var evObj = doc.createEvent('Events');
				evObj.initEvent( 'error', true, false );
				file.dispatchEvent(evObj);
				file.value = "";
			}
			if(!/^image\//.test(image.type)){
				e.stopPropagation();
				e.preventDefault();
				fireError();
				return false;
			}
			var reader = new FileReader(),
				img = new Image();
			reader.onerror = img.onerror = fireError;
			img.onload = function(){
				var info = {
					height: img.height,
					width: img.width,
					name: image.name,
					size: image.size
				};
				if( call(info) !== false ){
					setsize(info, img);
				}
				img.onload = img.onerror = null;
			}
			reader.onload = function (){
				img.src = reader.result;
			}
			reader.readAsDataURL(image);

		}, false);
	}catch(ex){
		
		file.attachEvent("onchange", function() {
			var path = file.value,
				tt = doc.createElement("tt"),
				name = path.slice(path.lastIndexOf("\\") + 1 );

			if("XMLHttpRequest" in window){
				file.select();
				path = doc.selection.createRange().text,
				doc.selection.empty();
			}

			function imgloader (mode){
				return "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + path + "', sizingMethod='" + mode + "')";
			}
			(doc.body || doc.documentElement).appendChild(tt);
			with(tt.runtimeStyle){
				filter = imgloader("image");
				zoom = width = height = 1;
				position = "absolute";
				right = "9999em";
				top = "-9999em";
				border = 0;
			}
			var info = {
				height: tt.offsetHeight,
				width: tt.offsetWidth,
				name: name
			};
			if( info.height > 1 || info.width > 1 ){
				if(call(info) !== false ){
					view.style.filter = imgloader("scale");
					setsize(info);
				}
			} else {
				file.fireEvent("onerror");
				event.cancelBubble = true;
				event.returnValue = false;
				this.value = "";
			}
			tt.parentNode.removeChild(tt);
		});
	}
	
}