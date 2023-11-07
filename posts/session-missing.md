---
title: session-missing
date: 2020-08-12 15:12:48
tags:
    - session
    - chrome
comments: true
cover: https://i.loli.net/2020/08/12/tgGR5QEy1dznb69.jpg
description: 
---

### session消失问题
目前，本公司使用session方式进行登录验证。
某天，有几位同事反馈登录异常情况，经过检查发现，在子iframe页面中发送的请求没有携带相应cookie。
继而向上查询问题，发现chrome控制台中Application栏中Cookies没有对应域下cookie；
检查登录接口，是正常的set-cookie的相关返回；
因此怀疑是浏览器对不同域下的cookie进行了特殊处理，搜索后发现果然如此。

发现网上有相同问题，例如：https://blog.csdn.net/sinat_36521655/article/details/104844667

检查出问题同事的chrome浏览器版本，果然都是最新版84（大于80）。
通过访问
```text
chrome://flags
```

设置sameSite相关属性为disable
1. SameSite by default cookies
2. Cookies without SameSite must be secure

![chrome设置](/images/chrome-sameSite.png)

问题得到临时解决







