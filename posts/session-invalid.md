---
title: session-invalid
date: 2020-12-24 22:28:40
tags:
    - session
    - chrome
comments: true
cover: https://i.loli.net/2021/02/18/3koWlriFRqBEZcT.jpg
description: 
---


### 问题描述
目前我司使用session作为登录方案，使用了微前端的技术方案整合多个业务组件。
在退出时，使用了session.invalidate() 方法进行session的退出清理工作。
但是在一些偶发情况下，会出现session is already invalidated的报错产生。

### 处理
先说结论，这是由于不同框架对于session的实现类不同，导致其处理逻辑不同导致的。
对与tomcat来说，其中的session实现类，对于session保存方式为存储为文件，对于invalidate的内部逻辑是删除对应的session文件。
而对于springmvc等框架而言，session存与内存中，invalidate方法仅将其标注为validFlag=true，实际对象仍存在，故会显示上述错误。

