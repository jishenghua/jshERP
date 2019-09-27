/*
**  HoorayOS开源桌面应用框架
**  作者：胡尐睿丶
**  地址：http://hoorayos.com
**  我希望能将这项目继续开源下去，所以请手下留情，保留以上这段版权信息，授权用户可删除代码中任何信息
*/

var TEMP      = {};
var HROS      = {};

HROS.CONFIG = {
	appButtonTop    : 20,       //快捷方式top初始位置
	appButtonLeft   : 20,       //快捷方式left初始位置
	windowIndexid   : 10000,    //窗口z-index初始值
	widgetIndexid   : 1,        //挂件z-index初始值
	windowMinWidth  : 215,      //窗口最小宽度
	windowMinHeight : 59,       //窗口最小高度
	wallpaper       : ''        //壁纸
};

HROS.VAR = {
	zoomLevel       : 1,
	dock            : '',
	desk            : ''
};