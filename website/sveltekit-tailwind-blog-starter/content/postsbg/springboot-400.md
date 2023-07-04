---
title: springboot-400
date: 2020-01-02 15:57:50
tags:
    - springboot
    - problem
comments: true
cover: https://i.loli.net/2021/02/18/zJ6jI9xiYmGXaqF.jpg
---


### springboot接口返回400问题

在其他接口正常访问情况下，一般而言是由于前端请求参数无法与实体类相对应

<br />

在使用lombok的情况下，检查构造器相关注解，例如
```java
@AllArgsConstructor
```
```java
@NoArgsConstructor
```
