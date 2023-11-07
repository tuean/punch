---
title: ckeditor
date: 2019-10-30 22:36:39
tags: 
    - 前端
    - problem
cover: https://i.loli.net/2019/10/30/bpQnviZ3TAN8Pgm.jpg
---

### ckeditor 图片上传修改

#### 背景
公司使用**ckeditor**作为富文本空间，最近发现图片上传时，在链接返回后需要过一段时间才能访问到图片

询问文件服务同事因对接原因无法处理，我这边决定从前端进行校验


#### 源码位置
图片上传失败会显示一个红色图片（noimage.png），此处通过该图片找到对应代码位置为 
```
ckeditor/plugins/image/dialogs/image.js 
```
观察此处为处理图片异常逻辑，替换原有链接为 noimage.png 

#### 修改方案
首先通过js判断图片是否存在
```javascript 1.8
function check_image_exist(src) {
    let image = new Image()
    image = src
   
    if (image.width() > 0 || image.height() > 0) {
        return true
    }
    return false
}
```

其次将原有代码封装为新函数**oldFunction()**

添加定时器，进行一定延时后循环判断文件是否可访问
代码大致思路如下
```javascript 1.8
let gap_time = 300
let max_count = 10
let count = 0
let find = -1
let timer = setInterval(time, gap)
let src = ''
function time() {
  count++;
  if (find != -1 && count > find) {
      let newSrc = src + "?t" + Math.random()
      // 设置新src 加上随机数强制页面重新加载图片
      clearInterval(timer)
      return
  }
  
  if (check_image_exist(src)) {
      find = count + 1
  }
  
  if (count >= max_count) {
      clearInterval(timer)
      oldFunction()
      return
  }
}
```

