---
title: pdf-xss
date: 2021-12-13 10:52:09
tags:
    - xss
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/71e52c67f5094e44b92ccaed93db15c5.jpeg
---

## pdf导致的xss问题
今日做了个需求，需要将h5页面导出为pdf并作为附件分享，当时的设计方案为前端使用html2pdf进行转义，并调用上传接口保存，后续该文件可下载预览。

功能开发一切顺利，但是安全部门发现流程中的一些问题。


### 问题复现
可通过迅捷pdf编辑器，直接修改pdf，在文件中添加jscript相关代码，当浏览器直接打开时，将其识别为js并执行。

具体复现逻辑可参考[过程](https://www.cnblogs.com/xiaozi/p/9951622.html)

### 解决方案
1. 后端识别pdf文件
   文件读取后，识别是否存在 **/Js** 或 **/JavaScript** 等代码
   ```
   return contains(file, "/JavaScript") || contains(file, "/JS")
   ```
   方案源自[github](https://github.com/iandl22/laravel_pdf_xss_validator/blob/main/PdfXssRule)

2. 前端使用其他方式解析pdf
   使用pdf.js仍会触发该攻击，经过调研，可使用 
   * [**永中DCS（付费）**](http://www.yozosoft.com/home.htm) 
   * [**react-pdf(开源)**](https://github.com/diegomura/react-pdf)
   

