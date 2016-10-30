/*
 * -- grayscale.js --
 * Copyright (C) James Padolsey (http://james.padolsey.com)
 *
 */

var grayscale = (function(){
    
    var config = {
            colorProps: ['color','backgroundColor','borderBottomColor','borderTopColor','borderLeftColor','borderRightColor','backgroundImage'],
            externalImageHandler : {
                /* Grayscaling externally hosted images does not work
                   - Use these functions to handle those images as you so desire */
                /* Out of convenience these functions are also used for browsers
                   like Chrome that do not support CanvasContext.getImageData */
                init : function(el, src) {
                    if (el.nodeName.toLowerCase() === 'img') {
                        // Is IMG element...
                    } else {
                        // Is background-image element:
                        // Default - remove background images
                        data(el).backgroundImageSRC = src;
                        el.style.backgroundImage = '';
                    }
                },
                reset : function(el) {
                    if (el.nodeName.toLowerCase() === 'img') {
                        // Is IMG element...
                    } else {
                        // Is background-image element:
                        el.style.backgroundImage = 'url(' + (data(el).backgroundImageSRC || '') + ')';
                    }
                }
            }
        },
        log = function(){
            try { window.console.log.apply(console, arguments); }
            catch(e) {};
        },
        isExternal = function(url) {
            // Checks whether URL is external: 'CanvasContext.getImageData'
            // only works if the image is on the current domain.
            return (new RegExp('https?://(?!' + window.location.hostname + ')')).test(url);
        },
        data = (function(){
            
            var cache = [0],
            expando = 'data' + (+new Date());
            
            return function(elem) {
                var cacheIndex = elem[expando],
                    nextCacheIndex = cache.length;
                if(!cacheIndex) {
                    cacheIndex = elem[expando] = nextCacheIndex;
                    cache[cacheIndex] = {};
                }
                return cache[cacheIndex];
            };
            
        })(),
        desatIMG = function(img, prepare, realEl) {
            
            // realEl is only set when img is temp (for BG images)
            
            var canvas = document.createElement('canvas'),
                context = canvas.getContext('2d'),
                height = img.naturalHeight || img.offsetHeight || img.height,
                width = img.naturalWidth || img.offsetWidth || img.width,
                imgData;
                
            canvas.height = height;
            canvas.width = width;
            context.drawImage(img, 0, 0);
            try {
                imgData = context.getImageData(0, 0, width, height);
            } catch(e) {}
            
            if (prepare) {
                desatIMG.preparing = true;
                // Slowly recurse through pixels for prep,
                // :: only occurs on grayscale.prepare()
                var y = 0;
                (function(){
                    
                    if (!desatIMG.preparing) { return; }
                    
                    if (y === height) {
                        // Finished!
                        context.putImageData(imgData, 0, 0, 0, 0, width, height);
                        realEl ? (data(realEl).BGdataURL = canvas.toDataURL())
                               : (data(img).dataURL = canvas.toDataURL())
                    }
                    
                    for (var x = 0; x < width; x++) {
                        var i = (y * width + x) * 4;
                        // Apply Monoschrome level across all channels:
                        imgData.data[i] = imgData.data[i+1] = imgData.data[i+2] =
                        RGBtoGRAYSCALE(imgData.data[i], imgData.data[i+1], imgData.data[i+2]);
                    }
                    
                    y++;
                    setTimeout(arguments.callee, 0);
                    
                })();
                return;
            } else {
                // If desatIMG was called without 'prepare' flag
                // then cancel recursion and proceed with force! (below)
                desatIMG.preparing = false;
            }
            
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    var i = (y * width + x) * 4;
                    // Apply Monoschrome level across all channels:
                    imgData.data[i] = imgData.data[i+1] = imgData.data[i+2] =
                    RGBtoGRAYSCALE(imgData.data[i], imgData.data[i+1], imgData.data[i+2]);
                }
            }
            
            context.putImageData(imgData, 0, 0, 0, 0, width, height);
            return canvas;
        
        },
        getStyle = function(el, prop) {
            var style = document.defaultView && document.defaultView.getComputedStyle ? 
                        document.defaultView.getComputedStyle(el, null)[prop]
                        : el.currentStyle[prop];
            // If format is #FFFFFF: (convert to RGB)
            if (style && /^#[A-F0-9]/i.test(style)) {
                    var hex = style.match(/[A-F0-9]{2}/ig);
                    style = 'rgb(' + parseInt(hex[0], 16) + ','
                                   + parseInt(hex[1], 16) + ','
                                   + parseInt(hex[2], 16) + ')';
            }
            return style;
        },
        RGBtoGRAYSCALE = function(r,g,b) {
            // Returns single monochrome figure:
            return parseInt( (0.2125 * r) + (0.7154 * g) + (0.0721 * b), 10 );
        },
        getAllNodes = function(context) {
            var all = Array.prototype.slice.call(context.getElementsByTagName('*'));
            all.unshift(context);
            return all;
        };
        
    var init = function(context) {
        
        // Handle if a DOM collection is passed instead of a single el:
        if (context && context[0] && context.length && context[0].nodeName) {
            // Is a DOM collection:
            var allContexts = Array.prototype.slice.call(context),
                cIndex = -1, cLen = allContexts.length;
            while (++cIndex<cLen) { init.call(this, allContexts[cIndex]); }
            return;
        }
        
        context = context || document.documentElement;
        
        if (!document.createElement('canvas').getContext) {
            context.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)';
            context.style.zoom = 1;
            return;
        }
        
        var all = getAllNodes(context),
            i = -1, len = all.length;
            
        while (++i<len) {
            var cur = all[i];
            
            if (cur.nodeName.toLowerCase() === 'img') {
                var src = cur.getAttribute('src');
                if(!src) { continue; }
                if (isExternal(src)) {
                    config.externalImageHandler.init(cur, src);
                } else {
                    data(cur).realSRC = src;
                    try {
                        // Within try statement just encase there's no support....
                        cur.src = data(cur).dataURL || desatIMG(cur).toDataURL();
                    } catch(e) { config.externalImageHandler.init(cur, src); }
                }
                
            } else {
                for (var pIndex = 0, pLen = config.colorProps.length; pIndex < pLen; pIndex++) {
                    var prop = config.colorProps[pIndex],
                    style = getStyle(cur, prop);
                    if (!style) {continue;}
                    if (cur.style[prop]) {
                        data(cur)[prop] = style;
                    }
                    // RGB color:
                    if (style.substring(0,4) === 'rgb(') {
                        var monoRGB = RGBtoGRAYSCALE.apply(null, style.match(/\d+/g));
                        cur.style[prop] = style = 'rgb(' + monoRGB + ',' + monoRGB + ',' + monoRGB + ')';
                        continue;
                    }
                    // Background Image:
                    if (style.indexOf('url(') > -1) {
                        var urlPatt = /\(['"]?(.+?)['"]?\)/,
                            url = style.match(urlPatt)[1];
                        if (isExternal(url)) {
                            config.externalImageHandler.init(cur, url);
                            data(cur).externalBG = true;
                            continue;
                        }
                        // data(cur).BGdataURL refers to caches URL (from preparation)
                        try {
                            var imgSRC = data(cur).BGdataURL || (function(){
                                    var temp = document.createElement('img');
                                    temp.src = url;
                                    return desatIMG(temp).toDataURL();
                                })();
                            
                            cur.style[prop] = style.replace(urlPatt, function(_, url){
                                return '(' + imgSRC + ')';
                            });
                        } catch(e) { config.externalImageHandler.init(cur, url); }
                    }
                }
            }
        }
        
    };
    
    init.reset = function(context) {
        // Handle if a DOM collection is passed instead of a single el:
        if (context && context[0] && context.length && context[0].nodeName) {
            // Is a DOM collection:
            var allContexts = Array.prototype.slice.call(context),
                cIndex = -1, cLen = allContexts.length;
            while (++cIndex<cLen) { init.reset.call(this, allContexts[cIndex]); }
            return;
        }
        context = context || document.documentElement;
        if (!document.createElement('canvas').getContext) {
            context.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(grayscale=0)';
            return;
        }
        var all = getAllNodes(context),
            i = -1, len = all.length;
        while (++i<len) {
            var cur = all[i];
            if (cur.nodeName.toLowerCase() === 'img') {
                var src = cur.getAttribute('src');
                if (isExternal(src)) {
                    config.externalImageHandler.reset(cur, src);
                }
                cur.src = data(cur).realSRC || src;
            } else {
                for (var pIndex = 0, pLen = config.colorProps.length; pIndex < pLen; pIndex++) {
                    if (data(cur).externalBG) {
                        config.externalImageHandler.reset(cur);
                    }
                    var prop = config.colorProps[pIndex];
                    cur.style[prop] = data(cur)[prop] || '';
                }
            }
        }
    };
    
    init.prepare = function(context) {
        
        // Handle if a DOM collection is passed instead of a single el:
        if (context && context[0] && context.length && context[0].nodeName) {
            // Is a DOM collection:
            var allContexts = Array.prototype.slice.call(context),
                cIndex = -1, cLen = allContexts.length;
            while (++cIndex<cLen) { init.prepare.call(null, allContexts[cIndex]); }
            return;
        }
        
        // Slowly recurses through all elements
        // so as not to lock up on the user.
        
        context = context || document.documentElement;
        
        if (!document.createElement('canvas').getContext) { return; }
        
        var all = getAllNodes(context),
            i = -1, len = all.length;
        
        while (++i<len) {
            var cur = all[i];
            if (data(cur).skip) { return; }
            if (cur.nodeName.toLowerCase() === 'img') {
                if (cur.getAttribute('src') && !isExternal(cur.src)) {
                    desatIMG(cur, true);
                }
                
            } else {
                var style = getStyle(cur, 'backgroundImage');
                if (style.indexOf('url(') > -1) {
                    var urlPatt = /\(['"]?(.+?)['"]?\)/,
                        url = style.match(urlPatt)[1];
                    if (!isExternal(url)) {
                        var temp = document.createElement('img');
                        temp.src = url;
                        desatIMG(temp, true, cur);
                    }
                }
            }
        }
    };
    
    return init;

})();