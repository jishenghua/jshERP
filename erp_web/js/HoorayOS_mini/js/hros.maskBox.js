/*
**  透明遮罩层
**  当拖动应用、窗口等一切可拖动的对象时，会加载一个遮罩层
**  避免拖动时触发或选中一些不必要的操作，安全第一
*/
HROS.maskBox = (function(){
	return {
		desk : function(){
			if(!TEMP.maskBoxDesk){
				TEMP.maskBoxDesk = $('<div id="maskbox"></div>');
				$('body').append(TEMP.maskBoxDesk);
			}
			return TEMP.maskBoxDesk;
		}
	}
})();