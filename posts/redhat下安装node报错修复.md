---
title: redhat下安装node报错修复
date: 2020-03-11 17:46:22
tags:
    - linux
    - red hat
    - node
cover: https://i.loli.net/2021/02/18/usOb3vTM5ZSx47U.jpg
---


### node安装异常

#### 当前环境
red hat6.8

#### node版本 
12.16.1

#### 问题原因
解压完node***.tar.gz后，抛出
```text
GLIBCXX.3.4.14 not found
```

搜索后发现是对应系统版本较低导致与新版本node的打包环境相差太大，需要升级gcc相关基础设施

#### 操作手册
搜索后找到了一个[操作文档](https://cloud.tencent.com/developer/article/1456578)

#### 注意事项
注意升级对应的版本，版本变动过大会出现更多关联包的升级需求

#### 后续操作
注意按当前方式操作安装的node及npm，需要手动添加软链
```bash
ln -s /xxx/npm /usr/bin/npm

ln -s /xxx/node /usr/bin/node
```
