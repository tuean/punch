---
title: springboot-stop
date: 2019-09-29 16:40:35
tags: 
    - springboot
comments: true
cover: https://i.loli.net/2019/10/23/1z5wjHd9bCQO24l.jpg
description: how to stop springboot application
---

### 关停springboot

#### 使用背景
在项目启动过程中，有些时候会需要做一些检查或者初始化工作，当任务失败的情况下为了做到快速响应，
个人推荐直接将发布应用关停，强制ci/cd发布系统报错停止

#### 使用方式
* springboot 1.x
可通过
```
EmbeddedWebApplicationContext
```
的
```
stopAndReleaseEmbeddedServletContainer
```
方法停止启动进程

* springboot 2.x
相关类做了调整，详见
```
ServletWebServerApplicationContext
```


> [参考来源](http://www.ciphermagic.cn/spring-boot-auto-stop.html)