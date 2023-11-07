---
title: chrome
date: 2021-05-17 18:38:15
tags:
    - chrome
cover: https://i.loli.net/2021/05/17/NJQFOBogkwTlvHZ.jpg

---

### chrome使用技巧

#### 调整分辨率
windows下：
```text
control + / + '+ or -'
```

max下
```text
command + / + '+ or -'
```
以上指令还可以调整控制台的分辨率，只要在输入按键前点击下控制台（将焦点focus到控制台），再输入上述按键即可
> edge也支持上述指令

#### 调整配置（实验功能）
```text
chrome://flags
```

#### console输出自定义格式
```js
console.log("%c%s",
            "background: white; color: red; font-size: 10px;",
            "警告！");
```

#### 命令模式
windows下：
```text
control + shift + p
```

max下
```text
command + shift + p
```

### 页面转二维码（原生支持，非插件）
```text
chrome://flags
```
搜索qr，选中enable即可


### 移动端调试
```text
edge://inspect/#devices
```

