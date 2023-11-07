---
title: react-setState-problem
date: 2019-12-19 17:17:51
tags: 
    - react
    - problem
comments: true
cover: http://pic1.win4000.com/wallpaper/2019-11-20/5dd4dfc5e163b.jpg
description: this.setState is not a function
---


### this.setState is not a function

#### 背景
记录一下今天遇到的一个小问题
下午在公司开发一款b端产品前端时，爆出一个回调问题，
```
this.setState is not a function
```

具体逻辑为在请求完一个删除接口后重新请求当前页面数据接口，重新渲染页面。

#### 解决过程
对应页面逻辑为在删除请求成功后，而请求使用**Promise**包装，通过**then**调用，
因而当时想到的是**this**的指向问题，通过设置
```
bind
```
进行解决

尝试了一下，发现仍报该错误，怀疑是绑定错误，再将过程中涉及到的函数全部绑定
```
this.method = this.method.bind(this)
```

继续尝试，仍抛出该问题，通过查询stackoverflow等网站均提示为仅需bind即可解决问题

遇事不决小黄鸭

根据需求，从头开始梳理页面逻辑，在对应代码处添加**console**及debugger进行检测，
发现this值已存在且正确，但是，点开this对应值，找到其中的setState，意外发现居然不是function
```
setStats: {}
```

居然是个对象，于是恍然大悟，应该是代码某处将setState设置为了对象，覆盖了原有的function
搜索后果不其然，有一处多写了个**=**，修改后problem solved

#### 总结
1. 作为常年写java的同学，在学习动态语言时需要注意，对象变量等类型不定，并可随意赋值，这块相对java等静态语言需要在思想上特别注意
2. 深入了解一下bind函数

    es5中，自动进行绑定
    在es6中，bind方法将创建一个新的函数, 当被调用时，将其this关键字设置为提供的值，在调用新函数时，在任何提供之前提供一个给定的参数序列
    ```
    The bind() method creates a new function that, when called, has its this keyword set to the provided value, with a given sequence of arguments preceding any provided when the new function is called.
    ```
    
    this指向：
    1. 纯粹的函数调用 代表全局对象
    2. 作为对象方法的调用 代表上级对象
    3. 作为构造函数调用 代表生成的新对象
    4. apply调用 代表apply方法的第一个参数
    
    


[1]: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/bind
[2]: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/this
[3]: http://www.ruanyifeng.com/blog/2010/04/using_this_keyword_in_javascript.html