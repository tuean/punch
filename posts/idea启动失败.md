---
title: idea启动失败
date: 2020-03-17 10:59:33
tags:
    - idea
    - problem
cover: https://i.loli.net/2021/02/18/P4YgnEz2u1sCty5.jpg
---


### idea启动失败

idea打开无反应的处理方式

1. 找到idea根目录下bin文件夹 windows环境下为idea.bat 
2. 编辑该文件 在最后添加
    ```cmd
    pause
    ```
3. 双击执行 本次爆出
    ```text
    Initial heap size set to a larger value than the maximum heap size
    ```
4. 按照搜索引擎查询的，修改bin目录下的.vmoptions文件，调整Xmx、Xms的大小（注意Xmx的大小要比Xms小）
5. 一般来说，调整完成后即可恢复正常打开
6. 若仍无法打开，使用everything搜索以下的文件名
    ```text
    .vmoptions
    ```
7. 可以找到系统用户目录下存在另一份配置文件
    ```text
    C:\Users\{user}\.IntelliJIdea2019.3\config\idea64.exe.vmoptions
    ```
8. 该文件中第一行注释表示其为默认配置
    ```text
    # custom IntelliJ Idea VM options
    ```
9. 修改该文件的Xms、Xmx值即可

#### 资料参考
- [idea support](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360004331580-Afte-2019-2-update-IntelliJ-fails-to-start-in-Windows?flash_digest=9c94f709c65b9fe387a6fd154209a564d7a00f11)