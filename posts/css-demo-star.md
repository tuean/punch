---
title: css-demo-star
date: 2020-02-25 03:21:39
tags: 
    - css
cover: https://i.loli.net/2021/02/18/eDrMmaJvkguslIV.jpg
---

### 一个星空demo的学习

最近在家上班，生活作息已经混乱了，大半夜刷b站看到了一个自制星空背景的css样式视频

由于个人后端出生，对能写好css的人非常崇拜（就像碰到光速QA的上帝一样）

一时兴起，关注了该[github仓库](https://github.com/LinCyJang/vue-css-demo)，以此学习一波


#### js部分
```javascript 1.8
$(document).ready(function() {
            var stars = 800; /*星星的密集程度，数字越大越多*/
            var $stars = $(".stars");
            var r = 800; /*星星的看起来的距离,值越大越远,可自行调制到自己满意的样子*/
            for (var i = 0; i < stars; i++) {
                var $star = $("<div/>").addClass("star");
                console.log($star)
                $stars.append($star);
            }
            $(".star").each(function() {
                var cur = $(this);
                var s = 0.2 + (Math.random() * 1);
                var curR = r + (Math.random() * 300);
                cur.css({
                    transformOrigin: "0 0 " + curR + "px",
                    transform: " translate3d(0,0,-" + curR + "px) rotateY(" + (Math.random() * 360) + "deg) rotateX(" + (Math.random() * -50) + "deg) scale(" + s + "," + s + ")"

                })
            })
        })
```

js部分负责星星的创建，并添加<p>star<p>的样式，该样式主要用到了两个css属性


- transformOrigin： 修改元素旋转变形的原点
    0 0 curR px分别代表 x y z 轴
- transform： 进行元素的旋转 
    其中
    - translate3d： 在3D空间内移动一个元素的位置
    - rotateY： 元素绕垂直轴旋转
    - rotateX： 元素绕水平轴旋转
    - deg： 旋转的角度
    - scale： 缩放


#### css部分
- radial-gradient: 从原点辐射开的颜色渐变组成的图片
- background-attachment： 确定背景图片在视口内固定或随它的区块的滚动而滚动，此处fix代表背景不旋转
- perspective： 指定了观察者与 z=0 平面的距离，使具有三维位置变换的元素产生透视效果
    这个属性没了解作者的意图
- transform-style: 设置元素的子元素是位于 3D 空间中还是平面中
- perspective-origin： 指定了观察者的位置，并且该属性会导致perspective属性失效
- animation： 定义元素的动画效果 动画需要如下@keyframes定义
- @keyframes： 定义动画的动作
- backface-visibility： 当元素背向观察者时元素是否可见


#### 整体
背景图通过radial-gradient属性进行夜幕的颜色渲染
所有的星星作为一整个元素，通过perspective-origin调整观察者位置，添加animation动画效果使其产生动画效果
每个星星使用随机数定义大小、位置等