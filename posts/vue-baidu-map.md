---
title: vue-baidu-map
date: 2020-03-22 22:43:16
tags:
cover: https://i.loli.net/2021/02/18/kM97YQefjbUqnGR.jpg
---


### vue-baidu-map踩坑记录

#### 替换定位图标
 - 使用网络图标： 直接使用url即可
 - 使用本地图标：
    1. 需要下载的图片不能太大
    2. 在项目中可以通过
        ```vue
        import img from '../assets/img/xxx.png'
        ```
        ```vue
        :icon="{url: img, size: {width: 300, height: 157}}"
        ```
        
