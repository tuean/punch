---
title: html2canvas
date: 2022-03-27 22:05:39
tags:
    - html2canvas
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/20220327220842.png
---


### html2canvas

#### 需求背景
家里领导接到一个需求，需要在移动端对某个页面进行截图，并对截图后的图片外层添加外层背景及公司logo，并进行分享

#### 技术方案
将html元素转为HTMLCanvasElement可使用：[html2canvas](https://github.com/niklasvh/html2canvas)

HTMLCanvasElement 元素可通过 [toDataURL](https://developer.mozilla.org/zh-CN/docs/Web/API/HTMLCanvasElement/toDataURL) 方法转为base64

获取到base64后，赋予img元素即可展示或调用后端接口进行分享

#### 问题记录

1. 元素需要缩小，并添加背景

   * transform scale方案
     ```css
     transform: scale(0.95, 0.95)
     ```
     即
   * margin方案
    
  
2. 页面元素过长，无法截图
   
    发现截图后返回的base字符串为 *data:,*；
   
    由于没有在日志中发现报错信息，排查是否为api问题；
   
    阅读 [return value](https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/toDataURL#return_value) 中的
    ```text
    If the height or width of the canvas is 0 or larger than the maximum canvas size, the string "data:," is returned.
    ```
    确定了原因，当页面过长，element过多，转换后的canvas高度或宽度超过当前浏览器限制，从而导致生成结果失效；

    我们可使用 [canvas-size](https://github.com/jhildenbiddle/canvas-size) 进行判断；

3. 结果中出现文字重叠

    当前使用
    ```text
    1.0.0
    ```
   
    升级至最新（1.4.1）
    ```text
    1.4.1
    ```
    即解决
    
