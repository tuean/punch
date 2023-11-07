---
title: tmp-tomcat
date: 2021-02-18 14:51:57
tags: 
	- tomcat
cover: https://i.loli.net/2021/02/18/NrP3qR5p2eIt4SD.jpg
---



### tomcat临时目录问题

#### 问题简述

tomcat服务出现异常

```java
org.springframework.web.multipart.MultipartException
```

在上传文件接口中可能出现该异常，在其他post方法中也可能抛出该问题。

其原因为tomcat会在接受post请求后默认在/tmp文件夹下创建临时文件，路径例如：

```
/tmp/tomcat.4247994877978548129.7777/work/Tomcat/localhost/api
```

而一些linux发行版会定期删除/tmp文件夹下长期不访问的内容，如centos下

```
/usr/lib/tmpfiles.d/tmp.conf
```



```
#  This file is part of systemd.
#
#  systemd is free software; you can redistribute it and/or modify it
#  under the terms of the GNU Lesser General Public License as published by
#  the Free Software Foundation; either version 2.1 of the License, or
#  (at your option) any later version.

# See tmpfiles.d(5) for details

# Clear tmp directories separately, to make them easier to override
v /tmp 1777 root root 10d
v /var/tmp 1777 root root 30d

# Exclude namespace mountpoints created with PrivateTmp=yes
x /tmp/systemd-private-%b-*
X /tmp/systemd-private-%b-*/tmp
x /var/tmp/systemd-private-%b-*
X /var/tmp/systemd-private-%b-*/tmp
```



这就导致了上述问题的发生。



#### 解决方案

修改tmp.conf文件，添加exclude配置

```
X /tmp/tomcat.*
```



#### 参考文档
[1]: https://blog.csdn.net/leonnew/article/details/79258453

