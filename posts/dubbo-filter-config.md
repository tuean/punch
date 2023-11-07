---
title: dubbo-filter-config
date: 2020-04-03 15:35:52
tags:
    - dubbo
cover: https://i.loli.net/2021/02/18/QZqbp94dkczXo3M.jpg
---


### dubbo filter 在idea中的配置问题

公司目前使用到dubbo作为rpc调用的框架，但当前对于监控这块暂无对应实现

前几天接到领导指示，需要提供rpc调用日志监控的sdk功能包

查询了一下官网，类同于添加http请求日志的监控，可利用dubbo filter即可实现改功能:

[dubbo filter](http://dubbo.apache.org/zh-cn/docs/dev/impls/filter.html)

但是通过配置改完后，发现代码并不生效，怀疑对应的filter并没有正确加载，通过debug找到对应实现类：

```text
org.apache.dubbo.common.extension.ExtensionLoader#loadDirectory
```

观察到对应的加载语句
```java
urls = classLoader.getResources(fileName)
```

此时urls.hasMoreElements()为false，故而无法触发if下的 loadResource 方法，进而没有加载进入我们的filter

仔细观察，该fileName为 <p>/META-INF/dubbo/xxx.xxx<p>

需要文件对应的层级为
```text
-- META-INF
-----dubbo
--------xxx.xxx
```

而在idea中，创建多层文件夹时，文件分割符为英文句号，即<p>.<p>

并且当仅有一子目录时，自动拼接至上层目录，

而当我们创建目录时，输入
```text
META-INF.dubbo
```

idea默认帮助我们创建名为上述text的文件夹，并没有自动切分并创建子文件夹

所以dubbo没有找到对应的配置文件，也就没有办法加载我们指定的filter了





