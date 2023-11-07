---
title: windowsKnowlodge
date: 2021-08-25 21:35:48
tags:
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/127f2bf2880511ebb6edd017c2d2eca2.jpeg
---


### windows相关的一些内容

#### 添加开机启动项

* win+R   

  ```cmd
  shell:startup
  ```

  将快捷方式贴入该文件夹



#### cp命令

cmd中cp命令为copy



#### windows查找端口

```shell
netstat -aon|findstr "${port}"
```

#### 强制杀进程

```shell
taskkill /T /F /PID ${pid}
```




