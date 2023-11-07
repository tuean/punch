---
title: watermark
date: 2019-11-15 10:01:50
tags: 
    - watermark
comments: true
cover: http://m.imeitou.com/uploads/allimg/2018081608/surthcsugci.jpg
description: watermark
---

## 水印

### 明水印

网上找了个实现

```javascript
function watermark(settings) {
    debugger;
    //默认设置
    var defaultSettings={
        watermark_txt:"text",
        watermark_x:20,//水印起始位置x轴坐标
        watermark_y:20,//水印起始位置Y轴坐标
        watermark_rows:20,//水印行数
        watermark_cols:20,//水印列数
        watermark_x_space:100,//水印x轴间隔
        watermark_y_space:50,//水印y轴间隔
        watermark_color:'#aaa',//水印字体颜色
        watermark_alpha:0.4,//水印透明度
        watermark_fontsize:'15px',//水印字体大小
        watermark_font:'微软雅黑',//水印字体
        watermark_width:210,//水印宽度
        watermark_height:80,//水印长度
        watermark_angle:15//水印倾斜度数
    };
    //采用配置项替换默认值，作用类似jquery.extend
    if(arguments.length===1&&typeof arguments[0] ==="object" )
    {
        var src=arguments[0]||{};
        for(key in src)
        {
            if(src[key]&&defaultSettings[key]&&src[key]===defaultSettings[key])
                continue;
            else if(src[key])
                defaultSettings[key]=src[key];
        }
    }

    var oTemp = document.createDocumentFragment();

    //获取页面最大宽度
    var page_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
    var cutWidth = page_width*0.0150;
    var page_width=page_width-cutWidth;
    //获取页面最大高度
    var page_height = Math.max(document.body.scrollHeight,document.body.clientHeight)+450;
    // var page_height = document.body.scrollHeight+document.body.scrollTop;
    //如果将水印列数设置为0，或水印列数设置过大，超过页面最大宽度，则重新计算水印列数和水印x轴间隔
    if (defaultSettings.watermark_cols == 0 || (parseInt(defaultSettings.watermark_x + defaultSettings.watermark_width *defaultSettings.watermark_cols + defaultSettings.watermark_x_space * (defaultSettings.watermark_cols - 1)) > page_width)) {
        defaultSettings.watermark_cols = parseInt((page_width-defaultSettings.watermark_x+defaultSettings.watermark_x_space) / (defaultSettings.watermark_width + defaultSettings.watermark_x_space));
        defaultSettings.watermark_x_space = parseInt((page_width - defaultSettings.watermark_x - defaultSettings.watermark_width * defaultSettings.watermark_cols) / (defaultSettings.watermark_cols - 1));
    }
    //如果将水印行数设置为0，或水印行数设置过大，超过页面最大长度，则重新计算水印行数和水印y轴间隔
    if (defaultSettings.watermark_rows == 0 || (parseInt(defaultSettings.watermark_y + defaultSettings.watermark_height * defaultSettings.watermark_rows + defaultSettings.watermark_y_space * (defaultSettings.watermark_rows - 1)) > page_height)) {
        defaultSettings.watermark_rows = parseInt((defaultSettings.watermark_y_space + page_height - defaultSettings.watermark_y) / (defaultSettings.watermark_height + defaultSettings.watermark_y_space));
        defaultSettings.watermark_y_space = parseInt(((page_height - defaultSettings.watermark_y) - defaultSettings.watermark_height * defaultSettings.watermark_rows) / (defaultSettings.watermark_rows - 1));
    }
    var x;
    var y;
    for (var i = 0; i < defaultSettings.watermark_rows; i++) {
        y = defaultSettings.watermark_y + (defaultSettings.watermark_y_space + defaultSettings.watermark_height) * i;
        for (var j = 0; j < defaultSettings.watermark_cols; j++) {
            x = defaultSettings.watermark_x + (defaultSettings.watermark_width + defaultSettings.watermark_x_space) * j;

            var mask_div = document.createElement('div');
            mask_div.id = 'mask_div' + i + j;
            mask_div.className = 'mask_div';
            mask_div.appendChild(document.createTextNode(defaultSettings.watermark_txt));
            //设置水印div倾斜显示
            mask_div.style.webkitTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.MozTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.msTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.OTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.transform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.visibility = "";
            mask_div.style.position = "absolute";
            mask_div.style.left = x + 'px';
            mask_div.style.top = y + 'px';
            mask_div.style.overflow = "hidden";
            mask_div.style.zIndex = "9999";
            mask_div.style.pointerEvents='none';//pointer-events:none  让水印不遮挡页面的点击事件
            //mask_div.style.border="solid #eee 1px";
            mask_div.style.opacity = defaultSettings.watermark_alpha;
            mask_div.style.fontSize = defaultSettings.watermark_fontsize;
            mask_div.style.fontFamily = defaultSettings.watermark_font;
            mask_div.style.color = defaultSettings.watermark_color;
            mask_div.style.textAlign = "center";
            mask_div.style.width = defaultSettings.watermark_width + 'px';
            mask_div.style.height = defaultSettings.watermark_height + 'px';
            mask_div.style.display = "block";
            oTemp.appendChild(mask_div);
        };
    };
    document.body.appendChild(oTemp);
}
```

该方法实现了水印的生成，但仍存在几个问题
* 调整页面分辨率后，水印没有进行动态调整适配
* 可通过手动去除页面元素方式进行删除

故做了一些调整
1. 对所有水印元素添加了监控，一旦发现有变化立即重新生成
2. 对水印上层元素添加了监控，一旦发现水印元素有删除，立即重新生成
3. 在页面分辨率有变化的情况下，重新计算水印参数并重新生成页面

调整后代码如下：

```javascript
const defaultSettings={
        watermark_txt:"text",
        watermark_x:20,//水印起始位置x轴坐标
        watermark_y:20,//水印起始位置Y轴坐标
        watermark_rows: 100,//水印行数
        watermark_cols: 100,//水印列数
        watermark_x_space:100,//水印x轴间隔
        watermark_y_space:50,//水印y轴间隔
        watermark_color:'#aaa',//水印字体颜色
        watermark_alpha:0.4,//水印透明度
        watermark_fontsize:'15px',//水印字体大小
        watermark_font:'微软雅黑',//水印字体
        watermark_width:210,//水印宽度
        watermark_height:80,//水印长度
        watermark_angle:15//水印倾斜度数
    };

const mask_name = 'mask'
const mask_class = 'mask_div'
const mask_id_prefix = "mask_div"
const config = {
    attributes: true,
    childList: true,
    subtree: true
}

function removeAllWaterMark() {
    let olds = $('div[name=' + mask_name + ']')
    olds.each( (i, item) => {$(item).remove()})
    
    let olds2 = $('.' + mask_class + '')
    olds2.each( (i, item) => {$(item),remove})
}

function makeWaterMarkId(i, j) {
    return mask_id_prefix + i + "-" + j
}

function createWaterMarkCell(x, y, id, settings) {
    let mask_div = document.createElement('div')
    mask_div.id = idmask_div.setAttribute("name", mask_name)
    mask_div.className = mask_class
    mask_div.appendChild(document.createTextNode(settings.watermark_txt))
    //设置水印div倾斜显示
    mask_div.style.webkitTransform = "rotate(-" + settings.watermark_angle + "deg)";
    mask_div.style.MozTransform = "rotate(-" + settings.watermark_angle + "deg)";
    mask_div.style.msTransform = "rotate(-" + settings.watermark_angle + "deg)";
    mask_div.style.OTransform = "rotate(-" + settings.watermark_angle + "deg)";
    mask_div.style.transform = "rotate(-" + settings.watermark_angle + "deg)";
    mask_div.style.visibility = "";
    mask_div.style.position = "absolute";
    mask_div.style.left = x + 'px';
    mask_div.style.top = y + 'px';
    mask_div.style.overflow = "hidden";
    mask_div.style.zIndex = "9999";
    mask_div.style.pointerEvents='none';//pointer-events:none  让水印不遮挡页面的点击事件
    //mask_div.style.border="solid #eee 1px";
    mask_div.style.opacity = defaultSettings.watermark_alpha;
    mask_div.style.fontSize = defaultSettings.watermark_fontsize;
    mask_div.style.fontFamily = defaultSettings.watermark_font;
    mask_div.style.color = defaultSettings.watermark_color;
    mask_div.style.textAlign = "center";
    mask_div.style.width = defaultSettings.watermark_width + 'px';
    mask_div.style.height = defaultSettings.watermark_height + 'px';
    mask_div.style.display = "block";
    return mask_div
}

// 监听所有的水印元素 是否被修改
function listen_water_mark(targetNode) {
    let observer = new MutationObserver((mutations => {
        mutations.forEach((mutation) => {
            watermark()
        })
    }))
    observer.observe(targetNode, config)
}

// 监听body元素 防止水印元素被删除
let bodyObserver = new MutationObserver(() => {})
function listen_water_mark_body() {
    bodyObserver.disconnect()
    bodyObserver = new MutationObserver( (mutations => {
        let needReDraw = false;
        mutations.forEach((mutation) => {
            let removeNodes = mutation.removedNodes
            if (removeNodes.length < 1) return
            removeNodes.forEach((item) => {
                if (needReDraw) return
                let jitem = $(item)
                let clazz = jitem.attr('class')
                let clazzFlag = clazz != null && clazz.indexOf(mask_class) != -1
                let name = jitem.attr('name')
                let nameFlag = name != null && name.indexOf(mask_name) != -1
                let id = jitem.attr('id')
                let idFlag = id != null && id.startsWith(mask_id_prefix)
                needReDraw = clazzFlag || nameFlag || idFlag
            })
        })
        if (needReDraw) watermark(defaultSettings)
    }))
    bodyObserver.observe(document.querySelector('body'), config)
}

function watermark(settings) {
    removeAllWaterMark()
    
    //采用配置项替换默认值，作用类似jquery.extend
    if(arguments.length===1&&typeof arguments[0] ==="object" )
    {
        var src=arguments[0]||{};
        for(key in src)
        {
            if(src[key]&&defaultSettings[key]&&src[key]===defaultSettings[key])
                continue;
            else if(src[key])
                defaultSettings[key]=src[key];
        }
    }
    
    let instant_setting = JSON.parse(JSON.stringify(defaultSettings))

    let oTemp = document.createDocumentFragment();

    //获取页面最大宽度
    let page_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
    let cutWidth = page_width*0.0150;
    let page_width=page_width-cutWidth;
    //获取页面最大高度
    let page_height = Math.max(document.body.scrollHeight,document.body.clientHeight)+450;
    // var page_height = document.body.scrollHeight+document.body.scrollTop;
    //如果将水印列数设置为0，或水印列数设置过大，超过页面最大宽度，则重新计算水印列数和水印x轴间隔
    if (instant_setting.watermark_cols == 0 || (parseInt(instant_setting.watermark_x + instant_setting.watermark_width *instant_setting.watermark_cols + instant_setting.watermark_x_space * (instant_setting.watermark_cols - 1)) > page_width)) {
        instant_setting.watermark_cols = parseInt((page_width-instant_setting.watermark_x+instant_setting.watermark_x_space) / (instant_setting.watermark_width + instant_setting.watermark_x_space));
        instant_setting.watermark_x_space = parseInt((page_width - instant_setting.watermark_x - instant_setting.watermark_width * instant_setting.watermark_cols) / (instant_setting.watermark_cols - 1));
    }
    //如果将水印行数设置为0，或水印行数设置过大，超过页面最大长度，则重新计算水印行数和水印y轴间隔
    if (instant_setting.watermark_rows == 0 || (parseInt(instant_setting.watermark_y + instant_setting.watermark_height * instant_setting.watermark_rows + instant_setting.watermark_y_space * (instant_setting.watermark_rows - 1)) > page_height)) {
        instant_setting.watermark_rows = parseInt((instant_setting.watermark_y_space + page_height - instant_setting.watermark_y) / (instant_setting.watermark_height + instant_setting.watermark_y_space));
        instant_setting.watermark_y_space = parseInt(((page_height - instant_setting.watermark_y) - instant_setting.watermark_height * instant_setting.watermark_rows) / (instant_setting.watermark_rows - 1));
    }
    
    var x;
    var y;
    for (var i = 0; i < instant_setting.watermark_rows; i++) {
        y = instant_setting.watermark_y + (instant_setting.watermark_y_space + instant_setting.watermark_height) * i;
        for (var j = 0; j < instant_setting.watermark_cols; j++) {
            x = instant_setting.watermark_x + (instant_setting.watermark_width + instant_setting.watermark_x_space) * j;
            let id = makeWaterMarkId(x, y)
            let mask_div = createWaterMarkCell(x, y, id, instant_setting)
            oTemp.appendChild(mask_div)
            listen_water_mark(mask_div)
        }
    }
    
    document.body.appendChild(oTemp)
    
    listen_water_mark_body()
}

window.onresize = function() {
  removeAllWaterMark()
}
```

### 暗水印

类似阿里巴巴内网中水印，见[知乎讨论](https://www.zhihu.com/question/50735753)

具体实现方式待续