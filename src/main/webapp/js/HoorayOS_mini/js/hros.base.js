/*
**  一个不属于其他模块的模块
*/
HROS.base = (function(){
	return {
		/*
		**	系统初始化
		*/
		init : function(){
			//阻止弹出浏览器默认右键菜单
			$('body').on('contextmenu', function(){
				return false;
			});
			//用于判断网页是否缩放
			HROS.zoom.init();
			//桌面(容器)初始化
			HROS.deskTop.init();
			//初始化壁纸
			HROS.wallpaper.init();
			//初始化任务栏
			HROS.taskbar.init();
			/*
			**      当dockPos为top时          当dockPos为left时         当dockPos为right时
			**  -----------------------   -----------------------   -----------------------
			**  | o o o         dock  |   | o | o               |   | o               | o |
			**  -----------------------   | o | o               |   | o               | o |
			**  | o o                 |   | o | o               |   | o               | o |
			**  | o +                 |   |   | o               |   | o               |   |
			**  | o             desk  |   |   | o         desk  |   | o         desk  |   |
			**  | o                   |   |   | +               |   | +               |   |
			**  -----------------------   -----------------------   -----------------------
			**  因为desk区域的尺寸和定位受dock位置的影响，所以加载应用前必须先定位好dock的位置
			*/
			//初始化应用码头
			HROS.dock.init();
			//初始化桌面应用
			HROS.app.init();
			//初始化widget模块
			HROS.widget.init();
			//初始化窗口模块
			HROS.window.init();
		}
	}
})();