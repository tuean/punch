---
title: multi-module-compilar-error
date: 2020-01-09 17:54:34
tags:
    - problem
    - maven
cover: https://i.loli.net/2021/02/18/NsSJFXWM1b2puaK.jpg
---

### 多模块maven项目问题

#### 前提过程
在一次项目搭建过程中，原先创建单个maven项目 
<br />
后续由于需要放出dubbo接口，将api单独拆出
<br />
这样就由原有的单module项目结果转换为一父多子的结构，如下

- root pom.xml
    - module 1 extends root 
    - module 2 extends root
  
  
#### 问题描述    
在开发过程中发现，在子模块中直接run方法会提示
```text
找不到主类
```    

而执行build或rebuild project等会提示
```text
找不到程序包
```

而通过maven的compile方法后，一切正常，从而导致改一次代码需要手动触发compile，非常不方便


#### 问题排查
首先怀疑是idea的配置缓存问题，
```text
file -> Invalidate Caches / Restart
```
清除完缓存后问题仍存在；
<br />

怀疑是maven异常，删去相关plugin和项目reimport后，仍未得到解决；
<br />

接着观察**Project Structure**，观察各module的output path设置，确认设置正确；
<br />

google半天后猜测是项目配置问题，在将父module从**Project Structure**中删去并重新添加，
发现父module和子module都Mark as了同一份资源，换句话说，该资源在两个module都被标记，导致了idea编译路径的误导
<br />

删去父module的Sources后，改为在子module标记，问题得到解决



