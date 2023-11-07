---
title: webworker-reconnecting-websocket
date: 2022-08-09 19:53:21
tags:
    - web worker
    - reconnecting-websocket
---

### reconnecting-websocket use in webworker

前端心跳保活由于页面切换等操作，定时任务可能非正常执行；

使用web worker替换主线程执行；


### problems

#### window not defined
web worker 内部没有window对象

#### document not defined
web worker 内部没有document对象

### 解决方案
github issue中看到类似的问题，思路是提供一个假的document供使用：
```javascript
var document = self.document = { parentNode: null, nodeType: 9, toString: function () { return "FakeDocument" } };
var window = self.window = self;
var fakeElement = Object.create(document);
fakeElement.nodeType = 1;
fakeElement.toString = function () { return "FakeElement" };
fakeElement.parentNode = fakeElement.firstChild = fakeElement.lastChild = fakeElement;
fakeElement.ownerDocument = document;

document.head = document.body = fakeElement;
document.ownerDocument = document.documentElement = document;
document.getElementById = document.createElement = function () { return fakeElement; };
document.createDocumentFragment = function () { return this; };
document.getElementsByTagName = document.getElementsByClassName = function () { return [fakeElement]; };
document.getAttribute = document.setAttribute = document.removeChild =
    document.addEventListener = document.removeEventListener =
    function () { return null; };
document.cloneNode = document.appendChild = function () { return this; };
document.appendChild = function (child) { return child; };
document.childNodes = [];
document.implementation = {
    createHTMLDocument: function () { return document; }
}
```


### 附录
1. [web worker](https://developer.mozilla.org/zh-CN/docs/Web/API/Web_Workers_API/Using_web_workers#web_workers_api)
2. [setInternal](https://developer.mozilla.org/zh-CN/docs/Web/API/setInterval)
3. [web worker issue](https://github.com/joewalnes/reconnecting-websocket/issues/51)
