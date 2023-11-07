---
title: accessException
date: 2019-11-18 16:52:24
tags:
    - idea
    - problem
cover: https://www.diyimei.net/upload/2018/1523093805792748.jpg
---

### idea出现 cann't access class in the package

今天在公司日常写代码过程选中，发现自己项目结构有问题；
在进行一连串java文件的复制、剪切。黏贴后，重新进行maven的package操作时，发现项目中有报出类找不到的情况；
在这里简单记录一下排查过程

### 1. 尝试自动修复
首先删除报错的import
再尝试通过idea自动化工具修复，在mac环境下为**option + enter**，若有出现import即可自动导入

### 2. 项目package确认
由于是通过idea提供的自助话工具进行包名、类名、参数名的自动替换，首先核对下报错类的package是否正常
特别注意拼写错误

### 3. modules配置确认
打开Project Structure，在modules栏目中查看是否正确定义sources文件夹，注意根目录

### 4. 清理maven
maven clean

### 5. 清楚idea缓存
通过File -> Invalidate Caches / Restart， 选择 Invalidate and start清理缓存并重启idea