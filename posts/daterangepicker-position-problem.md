---
title: daterangepicker-position-problem
date: 2019-12-20 14:42:52
tags: 
    - daterangepicker
    - problem
cover: http://www.005.tv/uploads/allimg/180508/13-1P50QK509147.jpg
description: daterangepicker position fixed when scrolling
---


### daterangepicker 定位问题

#### 原因
今天同事在一个老项目开发中发现了**daterangerpicker**生成出来的日期选择器固定在了页面元素，并没有随modal框的滚动而跟随其输入框滚动

### 解决过程
首先查看js代码是否有异常出现，无
想到元素生成正常而定位异常，猜测是其动态生成的dom元素的**position**元素的问题，查看新生成元素样式
```
position:absolute
```
无异常

继而查看其父元素无定位元素，给其加上
```
position: relative
```

仍显示异常，查阅官方文档及stackoverflow，发现一重要参数
```
parentEl
```
根据官方定义
> parentEl: (string) jQuery selector of the parent element that the date range picker will be added to, if not provided this will be 'body'

如果不设置，父元素将为body <br />

将其绑定为对应生成元素的父元素（即上述添加 position:relative）

problem solved




> 1. [stackoverflow](https://stackoverflow.com/questions/56964857/dropdown-menu-doesnt-stay-in-place-when-scrolling)
> 2. [github](https://github.com/dangrossman/daterangepicker)
> 3. [官方文档](http://www.daterangepicker.com/#options)
