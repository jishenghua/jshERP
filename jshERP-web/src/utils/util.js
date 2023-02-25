import { isURL } from '@/utils/validate'
import XLSX from 'xlsx'
import Vue from 'vue'
import introJs from 'intro.js'

export function timeFix() {
  const time = new Date()
  const hour = time.getHours()
  return hour < 9 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour < 20 ? '下午好' : '晚上好')))
}

export function welcome() {
  const arr = ['休息一会儿吧', '准备吃什么呢?', '要不要打一把 DOTA', '我猜你可能累了']
  let index = Math.floor((Math.random()*arr.length))
  return arr[index]
}

/**
 * 触发 window.resize
 */
export function triggerWindowResizeEvent() {
  let event = document.createEvent('HTMLEvents')
  event.initEvent('resize', true, true)
  event.eventType = 'message'
  window.dispatchEvent(event)
}

/**
 * 过滤对象中为空的属性
 * @param obj
 * @returns {*}
 */
export function filterObj(obj) {
  if (!(typeof obj == 'object')) {
    return;
  }

  for ( let key in obj) {
    if (obj.hasOwnProperty(key)
      && (obj[key] == null || obj[key] == undefined || obj[key] === '')) {
      delete obj[key];
    }
  }
  return obj;
}

/**
 * 时间格式化
 * @param value
 * @param fmt
 * @returns {*}
 */
export function formatDate(value, fmt) {
  let regPos = /^\d+(\.\d+)?$/;
  if(regPos.test(value)){
    //如果是数字
    let getDate = new Date(value);
    let o = {
      'M+': getDate.getMonth() + 1,
      'd+': getDate.getDate(),
      'h+': getDate.getHours(),
      'm+': getDate.getMinutes(),
      's+': getDate.getSeconds(),
      'q+': Math.floor((getDate.getMonth() + 3) / 3),
      'S': getDate.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (let k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
      }
    }
    return fmt;
  }else{
    //TODO
    value = value.trim();
    return value.substr(0,fmt.length);
  }
}

// 生成首页路由
export function generateIndexRouter(data) {
  let indexRouter = generateChildRouters(data)
  indexRouter.splice(0,0, {
    path: '/',
    name: '首页',
    component: () => import('@/components/layouts/TabLayout'),
    meta: {
      title: '首页',
      icon: 'icon-present',
      url: '/dashboard/analysis'
    },
    redirect: '/dashboard/analysis'
  })
  return indexRouter;
}

// 生成嵌套路由（子路由）

function generateChildRouters (data) {
  const routers = [];
  for (let item of data) {
    let componentPath = "";
    item.route = "1";
    if(item.component.indexOf("layouts")>=0){
      componentPath = () => import('@/components'+item.component);
    } else {
      componentPath = () => import('@/views'+item.component);
    }
    // eslint-disable-next-line
    let URL = (item.url|| '').replace(/{{([^}}]+)?}}/g, (s1, s2) => eval(s2)) // URL支持{{ window.xxx }}占位符变量
    if (isURL(URL)) {
      item.url = URL;
    }
    let componentName =''
    if(item.component) {
      let index = item.component.lastIndexOf("\/");
      componentName = item.component.substring(index + 1, item.component.length);
    }
    let menu = {
      path: item.url,
      name: item.text,
      meta: {
        id: item.id,
        title: item.text,
        icon: item.icon,
        url: item.url,
        componentName:componentName,
        internalOrExternal:true,
        keepAlive: true
      }
    }
    if(item.component.indexOf("IframePageView")>-1){
      //给带iframe的页面进行改造
      menu.iframeComponent = componentPath
    } else {
      menu.component = componentPath
    }
    if (item.children && item.children.length > 0) {
      menu.children = [...generateChildRouters( item.children)];
    }
    //--update-begin----author:scott---date:20190320------for:根据后台菜单配置，判断是否路由菜单字段，动态选择是否生成路由（为了支持参数URL菜单）------
    //判断是否生成路由
    if(item.route && item.route === '0'){
      //console.log(' 不生成路由 item.route：  '+item.route);
      //console.log(' 不生成路由 item.path：  '+item.path);
    }else{
      routers.push(menu);
    }
    //--update-end----author:scott---date:20190320------for:根据后台菜单配置，判断是否路由菜单字段，动态选择是否生成路由（为了支持参数URL菜单）------
  }
  return routers
}

/**
 * 深度克隆对象、数组
 * @param obj 被克隆的对象
 * @return 克隆后的对象
 */
export function cloneObject(obj) {
  return JSON.parse(JSON.stringify(obj))
}

/**
 * 随机生成数字
 *
 * 示例：生成长度为 12 的随机数：randomNumber(12)
 * 示例：生成 3~23 之间的随机数：randomNumber(3, 23)
 *
 * @param1 最小值 | 长度
 * @param2 最大值
 * @return int 生成后的数字
 */
export function randomNumber() {
  // 生成 最小值 到 最大值 区间的随机数
  const random = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1) + min)
  }
  if (arguments.length === 1) {
    let [length] = arguments
  // 生成指定长度的随机数字，首位一定不是 0
    let nums = [...Array(length).keys()].map((i) => (i > 0 ? random(0, 9) : random(1, 9)))
    return parseInt(nums.join(''))
  } else if (arguments.length >= 2) {
    let [min, max] = arguments
    return random(min, max)
  } else {
    return Number.NaN
  }
}

/**
 * 随机生成字符串
 * @param length 字符串的长度
 * @param chats 可选字符串区间（只会生成传入的字符串中的字符）
 * @return string 生成的字符串
 */
export function randomString(length, chats) {
  if (!length) length = 1
  if (!chats) chats = '0123456789qwertyuioplkjhgfdsazxcvbnm'
  let str = ''
  for (let i = 0; i < length; i++) {
    let num = randomNumber(0, chats.length - 1)
    str += chats[num]
  }
  return str
}

/**
 * 随机生成uuid
 * @return string 生成的uuid
 */
export function randomUUID() {
  let chats = '0123456789abcdef'
  return randomString(32, chats)
}

/**
 * 下划线转驼峰
 * @param string
 * @returns {*}
 */
export function underLine2CamelCase(string){
  return string.replace( /_([a-z])/g, function( all, letter ) {
    return letter.toUpperCase();
  });
}

/**
 * 判断是否显示办理按钮
 * @param bpmStatus
 * @returns {*}
 */
export function showDealBtn(bpmStatus){
  if(bpmStatus!="1"&&bpmStatus!="3"&&bpmStatus!="4"){
    return true;
  }
  return false;
}

/**
 * 增强CSS，可以在页面上输出全局css
 * @param css 要增强的css
 * @param id style标签的id，可以用来清除旧样式
 */
export function cssExpand(css, id) {
  let style = document.createElement('style')
  style.type = "text/css"
  style.innerHTML = `@charset "UTF-8"; ${css}`
  // 清除旧样式
  if (id) {
    let $style = document.getElementById(id)
    if ($style != null) $style.outerHTML = ''
    style.id = id
  }
  // 应用新样式
  document.head.appendChild(style)
}


/** 用于js增强事件，运行JS代码，可以传参 */
// options 所需参数：
//    参数名         类型            说明
//    vm             VueComponent    vue实例
//    event          Object          event对象
//    jsCode         String          待执行的js代码
//    errorMessage   String          执行出错后的提示（控制台）
export function jsExpand(options = {}) {

  // 绑定到window上的keyName
  let windowKeyName = 'J_CLICK_EVENT_OPTIONS'
  if (typeof window[windowKeyName] != 'object') {
    window[windowKeyName] = {}
  }

  // 随机生成JS增强的执行id，防止冲突
  let id = randomString(16, 'qwertyuioplkjhgfdsazxcvbnm'.toUpperCase())
  // 封装按钮点击事件
  let code = `
    (function (o_${id}) {
      try {
        (function (globalEvent, vm) {
          ${options.jsCode}
        })(o_${id}.event, o_${id}.vm)
      } catch (e) {
        o_${id}.error(e)
      }
      o_${id}.done()
    })(window['${windowKeyName}']['EVENT_${id}'])
  `
  // 创建script标签
  const script = document.createElement('script')
  // 将需要传递的参数挂载到window对象上
  window[windowKeyName]['EVENT_' + id] = {
    vm: options.vm,
    event: options.event,
    // 当执行完成时，无论如何都会调用的回调事件
    done() {
      // 执行完后删除新增的 script 标签不会撤销执行结果（已产生的结果不会被撤销）
      script.outerHTML = ''
      delete window[windowKeyName]['EVENT_' + id]
    },
    // 当js运行出错的时候调用的事件
    error(e) {
      console.group(`${options.errorMessage || '用户自定义JS增强代码运行出错'}（${new Date()}）`)
      console.error(e)
      console.groupEnd()
    }
  }
  // 将事件挂载到document中
  script.innerHTML = code
  document.body.appendChild(script)
}

/**
 * 如果值不存在就 push 进数组，反之不处理
 * @param array 要操作的数据
 * @param value 要添加的值
 * @param key 可空，如果比较的是对象，可能存在地址不一样但值实际上是一样的情况，可以传此字段判断对象中唯一的字段，例如 id。不传则直接比较实际值
 * @returns {boolean} 成功 push 返回 true，不处理返回 false
 */
export function pushIfNotExist(array, value, key) {
  for (let item of array) {
    if (key && (item[key] === value[key])) {
      return false
    } else if (item === value) {
      return false
    }
  }
  array.push(value)
  return true
}

/**
 * 可用于判断是否成功
 * @type {symbol}
 */
export const succeedSymbol = Symbol()
/**
 * 可用于判断是否失败
 * @type {symbol}
 */
export const failedSymbol = Symbol()

/**
 * 使 promise 无论如何都会 resolve，除非传入的参数不是一个Promise对象或返回Promise对象的方法
 * 一般用在 Promise.all 中
 *
 * @param promise 可传Promise对象或返回Promise对象的方法
 * @returns {Promise<any>}
 */
export function alwaysResolve(promise) {
  return new Promise((resolve, reject) => {
    let p = promise
    if (typeof promise === 'function') {
      p = promise()
    }
    if (p instanceof Promise) {
      p.then(data => {
        resolve({ type: succeedSymbol, data })
      }).catch(error => {
        resolve({ type: failedSymbol, error })
      })
    } else {
      reject('alwaysResolve: 传入的参数不是一个Promise对象或返回Promise对象的方法')
    }
  })
}

/**
 * 简单实现防抖方法
 *
 * 防抖(debounce)函数在第一次触发给定的函数时，不立即执行函数，而是给出一个期限值(delay)，比如100ms。
 * 如果100ms内再次执行函数，就重新开始计时，直到计时结束后再真正执行函数。
 * 这样做的好处是如果短时间内大量触发同一事件，只会执行一次函数。
 *
 * @param fn 要防抖的函数
 * @param delay 防抖的毫秒数
 * @returns {Function}
 */
export function simpleDebounce(fn, delay = 100) {
  let timer = null
  return function () {
    let args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      fn.apply(null, args)
    }, delay)
  }
}

/**
 * 不用正则的方式替换所有值
 * @param text 被替换的字符串
 * @param checker  替换前的内容
 * @param replacer 替换后的内容
 * @returns {String} 替换后的字符串
 */
export function replaceAll(text, checker, replacer) {
  let lastText = text
  text = text.replace(checker, replacer)
  if (lastText !== text) {
    return replaceAll(text, checker, replacer)
  }
  return text
}

/**
 * 转换商品扩展字段的格式
 * @param thisRows
 * @param checker
 * @param replacer
 * @returns {string}
 */
export function getMpListShort(thisRows, checker, replacer) {
  let mPropertyListShort = ''
  let nativeNameStr = ''
  for (let i = 0; i < thisRows.length; i++) {
    if (thisRows[i].enabled) {
      nativeNameStr += thisRows[i].nativeName + ",";
    }
  }
  if (nativeNameStr) {
    mPropertyListShort = nativeNameStr.substring(0, nativeNameStr.length - 1);
  }
  return mPropertyListShort
}

/**
 * js获取当前年份， 格式“yyyy”
 */
export function getNowFormatYear() {
  let date = new Date();
  return date.getFullYear();
}

/**
 * js获取当前月份， 格式“yyyy-MM”
 */
export function getNowFormatMonth() {
  let date = new Date();
  let seperator1 = "-";
  let month = date.getMonth() + 1;
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  return date.getFullYear() + seperator1 + month;
}

/**
 * js获取当前日期， 格式“yyyy-MM-dd”
 */
export function getFormatDate() {
  let date = new Date();
  let seperator1 = "-";
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  return year + seperator1 + month + seperator1 + strDate;
}

/**
 * js获取当前时间， 格式“yyyy-MM-dd HH:MM:SS”
 */
export function getNowFormatDateTime() {
  let date = new Date();
  let seperator1 = "-";
  let seperator2 = ":";
  let month = date.getMonth() + 1;
  let strDate = date.getDate();
  let strHours = date.getHours();
  let strMinutes = date.getMinutes();
  let strSeconds = date.getSeconds();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  if (strHours >= 0 && strHours <= 9) {
    strHours = "0" + strHours;
  }
  if (strMinutes >= 0 && strMinutes <= 9) {
    strMinutes = "0" + strMinutes;
  }
  if (strSeconds >= 0 && strSeconds <= 9) {
    strSeconds = "0" + strSeconds;
  }
  return date.getFullYear() + seperator1 + month + seperator1 + strDate
    + " " + strHours + seperator2 + strMinutes
    + seperator2 + strSeconds;
}

/**
 * js获取当前时间， 格式“MMddHHMMSS”
 */
export function getNowFormatStr() {
  let date = new Date();
  let month = date.getMonth() + 1;
  let strDate = date.getDate();
  let strHours = date.getHours();
  let strMinutes = date.getMinutes();
  let strSeconds = date.getSeconds();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  if (strHours >= 0 && strHours <= 9) {
    strHours = "0" + strHours;
  }
  if (strMinutes >= 0 && strMinutes <= 9) {
    strMinutes = "0" + strMinutes;
  }
  if (strSeconds >= 0 && strSeconds <= 9) {
    strSeconds = "0" + strSeconds;
  }
  return month +''+ strDate +''+ strHours +''+ strMinutes +''+ strSeconds;
}

/**
 * JS中根据指定值删除数组中的元素
 * @param arrylist
 * @param val
 */
export function removeByVal(arrylist, val) {
  for(var i = 0; i < arrylist .length; i++) {
    if(arrylist [i] == val) {
      arrylist .splice(i, 1);
      break;
    }
  }
}

export function getCheckFlag(multiBillType, multiLevelApprovalFlag, prefixNo) {
  if(multiLevelApprovalFlag==='1') {
    //开启
    if(multiBillType) {
      let multiBillTypeArr = multiBillType.split(',')
      return multiBillTypeArr.indexOf(prefixNo) <= -1
    } else {
      return true
    }
  } else {
    //关闭
    return true
  }
}

/**
 * 将字符串中单个金额中的数值转为负数
 * @param str
 * @returns {Array}
 */
export function changeListFmtMinus(str) {
  let newArr = new Array()
  if(str) {
    let arr = []
    if(str.indexOf(',')>-1) {
      arr = str.split(',')
    } else {
      arr = str
    }
    for(let i=0; i<arr.length; i++) {
      if(arr[i] < 0){
        newArr.push((arr[i]-0).toString());
      }
      else {
        newArr.push((0 - arr[i]).toString());
      }
    }
  }
  return newArr;
}

/**
 通用的打开下载对话框方法，没有测试过具体兼容性
 @param url 下载地址，也可以是一个blob对象，必选
 @param saveName 保存文件名，可选
 */
export function openDownloadDialog (url, saveName) {
  if (typeof url === 'object' && url instanceof Blob) {
    url = URL.createObjectURL(url) // 创建blob地址
  }
  let aLink = document.createElement('a')
  aLink.href = url
  saveName = saveName + '_' + getNowFormatStr() + '.xls'
  aLink.download = saveName || '' // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
  let event
  if (window.MouseEvent) event = new MouseEvent('click')
  else {
    event = document.createEvent('MouseEvents')
    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
  }
  aLink.dispatchEvent(event)
}

/**
 * 将一个sheet转成最终的excel文件的blob对象，然后利用URL.createObjectURL下载
 * @param sheet
 * @param sheetName
 * @returns {Blob}
 */
export function sheet2blob (aoa, sheetName) {
  let sheet = XLSX.utils.aoa_to_sheet(aoa)
  sheetName = sheetName || 'sheet1'
  let workbook = {
    SheetNames: [sheetName],
    Sheets: {}
  }
  workbook.Sheets[sheetName] = sheet
  // 生成excel的配置项
  let wopts = {
    bookType: 'xls', // 要生成的文件类型
    bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
    type: 'binary'
  }
  let wbout = XLSX.write(workbook, wopts)
  let blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' })
  // 字符串转ArrayBuffer
  function s2ab (s) {
    let buf = new ArrayBuffer(s.length)
    let view = new Uint8Array(buf)
    for (let i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF
    return buf
  }
  return blob
}

/**
 * 新手引导步骤
 * @param module 这个变量可以用来存取版本号， 系统更新时候改变相应值
 * @param cur_version 这个变量可以用来存取版本号， 系统更新时候改变相应值
 */
export function handleIntroJs(module, cur_version) {
  //每个页面设置不同的缓存变量名称，不可以重复，有新版本时，更新cur_version
  //有新版本更新时才出现一次引导页， 第二次进入进不再出现， 这里有缓存来判断
  let introJsObj = introJs()
  if(module !== 'indexChart') {
    let idElement = '#' + module
    introJsObj = introJs(idElement)
  }
  if (Vue.ls.get('intro_cache_' + module) === cur_version) {
    return;
  }
  introJsObj.setOptions({
    prevLabel: '&larr; 上一步',
    nextLabel: '下一步 &rarr;',
    doneLabel: '知道了',
    exitOnOverlayClick: false //点击空白区域是否关闭提示组件
  }).oncomplete(function(){
    //点击跳过按钮后执行的事件(这里保存对应的版本号到缓存,并且设置有效期为100天）
    Vue.ls.set('intro_cache_' + module, cur_version, 100 * 24 * 60 * 60 * 1000);
  }).onexit(function(){
    //点击结束按钮后， 执行的事件
    Vue.ls.set('intro_cache_' + module, cur_version, 100 * 24 * 60 * 60 * 1000);
  }).start()
}

/**
 * 回车后自动跳到下一个input
 */
export function autoJumpNextInput(domInfo) {
  let domIndex = 0
  let inputs = document.getElementById(domInfo).getElementsByTagName('input')
  inputs[domIndex].focus()
  document.getElementById(domInfo).addEventListener('keydown',function(e){
    if(e.keyCode === 13){
      domIndex++
      if(domIndex === inputs.length) {
        domIndex = 0
      }
      inputs[domIndex].focus()
    }
  })
  for(let i=0; i<inputs.length; i++){
    //这个index就是做个介质，来获取当前的i是第几个
    inputs[i].index = i;
    inputs[i].onclick = function () {
      domIndex = this.index
    }
  }
}