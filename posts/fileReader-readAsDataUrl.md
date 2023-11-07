---
title: fileReader-readAsDataUrl
date: 2019-12-31 18:08:36
tags: 
    - 前端
    - problem
cover: https://i.loli.net/2019/10/30/bpQnviZ3TAN8Pgm.jpg
---

### fileReader使用
承接上文 [图片上传回显问题前端修复](./ckeditor.md)
<br />

最近又在另一项目中发现了这一问题，继续使用前端轮训获取感觉体验不佳，询问前端专业小伙伴后，推荐使用**FileReader**直接展示本地文件

> 当然，使用本地图片需要有如下前提：
> 1. 后端端口上传结果可信，成功即文件上传ok
> 2. 本地浏览器版本要求较新


根据官方文档，了解到**FileReader**支持将文件转为url地址，具体可见[https://developer.mozilla.org/en-US/docs/Web/API/FileReader/readAsDataURL](https://developer.mozilla.org/en-US/docs/Web/API/FileReader/readAsDataURL)

根据如上文档，去除了原先onerror处相关方法，只需修改原有ajax代码如下：

```javascript
let reader = new FileReader()
reader.readAsDataURL(response)
reader.onload = (event) => {
    let newUrl = event.target.result;
    // your code
}
```


#### 总结
总结一下
在技术这一层面，该实现方法较为简单，代码量不大，且客户使用体验较好；
<br />
排除技术来说，作为一名开发，在碰到问题时的思路可能就显得比较笨拙，偏向于向一个方向死磕，效率低下且毫无成就感可言，
借此案例，希望时刻提醒自己，要善于动脑，从不同角度发现和解决问题，世界上其实有很多的路。

