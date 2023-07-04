---
title: amazon-sprider
date: 2019-10-25 10:24:49
tags: 
    - 个人项目 
cover: https://i.loli.net/2019/10/30/5lyRzMAwdLkFS4P.jpg
---

###  [自用的亚马逊商品爬虫](https://github.com/tuean/spider-amazon)
#### 功能说明
给定关键词，爬取对应亚马逊网站对应商品搜索结果

#### 当前版本
v0.0.1

#### 爬取内容
* asin 商品id
* product name 商品名
* price 商品价格
* grade 评分
* number of comment 评论数
* detail page of product 详细页地址

#### 运行方式
1. 主函数中添加搜索词
2. 默认指定为亚马逊美国地址， 需要变更可修改 Constant类
3. 通过db.sql创建数据库实例
4. 执行
5. 结束后即可在db中看到数据

#### todo list
- [ ] 参数可配置
- [ ] excel数据落地
- [ ] 多线程请求
- [ ] 软件页面
