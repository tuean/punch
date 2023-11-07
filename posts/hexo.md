---
title: hexo配合github搭建博客
date: 2019-10-09 16:39:54
tags: 
    - hexo
    - github 
cover: https://i.loli.net/2019/10/30/4KvzoaEV21nQwjM.jpg
comments: true
description: steps of building this blog 
---

## hexo + github pages 搭建博客

## 软件安装
### 1. nodejs (hexo依赖nodejs， 已安装可跳过)
* windows/mac 进入[node官方网址](https://nodejs.org/en/)下载最新版并安装
* mac环境下可通过包管理器安装
    1. 安装homebrew[官网](https://brew.sh/) 或者通过以下命令
          ```bash
           /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
          ```
    2. 通过homebrew安装node
          ```bash
           brew install -g node
          ```
    3. 每次执行homebrew后都会先执行update 目前由于各种原因耗时很大，可通过以下命令关闭
          ```bash
           export HOMEBREW_NO_AUTO_UPDATE=true
          ```
    4. 验证是否安装完成   
          ```bash
           node -v
          ```

#### 2. git (已安装可跳过)
* windows下 [官网](https://git-scm.com/download/win)  
* mac下 
    1. 安装
        ```bash
         homebrew install git
        ```
    2. 校验
        ```bash
         git --version
        ```

#### 3. 安装hexo
1. 通过npm安装
     ```bash
      npm i -g hexo
     ```
2. 校验
     ```bash
      hexo -v
     ```
        
### github项目创建
#### 1. 博客源码仓库 
* 本文中记作 **blog-source** 
* 保存hexo工作环境

#### 2. 博客内容仓库 
* 本文中记作 **blog**
* 输出结果保存
* settings > github pages 勾选

### hexo使用
#### 1. 初始化
1. 选择一 **空** 文件夹 例如 **blog** 
2. 命令行进入上述文件夹 
3. hexo初始化
     ```bash
     hexo init
    ```
4. 完成后可见生成了对应的一系列文件，简单说明
    * source文件夹   文档内容
    * themes文件夹   主题
    * _config.yml    hexo配置文件
    * CNAME          配置域名所需 需要手动创建
    * package.json   webpack配置文件


#### 2. hexo配置文件修改
1. 修改 **# Site**
    ```yaml
     title: title
     descprition: des
     ...
    ```
2. 修改 **# Deployment**
    ```yaml
     deploy: 
       type: git
       branch: master
       repo: blog github地址
    ```


#### 3. hexo使用
* 创建文件
    ```bash
     hexo new fileName
    ```
* 清理文件
    ```bash
     hexo clean
     ```
* 生成文件
    ```bash
     hexo g
    ```
* 本地预览 http://localhost:4000
    ```bash
     hexo s
    ```
* 结果发布
    ```bash
     hexo d
    ```
> 推送成功后即可在 **blog** 仓库发现最新生成文件
    
### 发布流程简述
```bash
hexo clean
hexo g
hexo d
```

### 域名绑定
1. github pages配置页面填写对应域名，例如本站 **tuean.cn**
2. 在hexo工作目录下新建 **CNAME** 文件，不需要任何后缀名，输入域名保存
3. _config.yml中修改 **# Url**
     ```yaml
     url: 域名
     root: 子目录 无子目录输入 /
     ```
     
### 主题
#### 1. 来源
[主题仓库](https://hexo.io/themes/)  
       
#### 2. 安装主题，以[hexo-theme-butterfly](https://github.com/jerryc127/hexo-theme-butterfly)为例
1. 项目根目录下
    ```bash
     git clone -b master https://github.com/jerryc127/hexo-theme-butterfly.git themes/Butterfly
    ```

2. 修改项目 **_config.yml** 中的 **# theme**
    ```bash
     theme: Butterfly
    ```

3. 安装依赖
    ```bash
     npm install hexo-renderer-jade hexo-renderer-stylus --save or yarn add hexo-renderer-jade hexo-renderer-stylus
     npm install hexo-deployer-git --save
    ```
    > 注意 需要在hexo主目录下安装
 
4. 自定义
进入 **theme/Butterfly** 下的 **_config**
自行按照爱好设定


### 生成sitemap
[配置github action](https://knktc.com/2021/06/26/hexo-use-github-actions-to-submit-sitemap/)

google
```shell
npm install hexo-generator-sitemap --save
```

baidu
```shell
npm install hexo-generator-baidu-sitemap --save
```

## 备份
* 源码全部提交到 **blog-source** 仓库

## 问题
 Q: 项目启动后显示白屏
 
 A: 查看 theme 文件夹下所使用的资源文件是否未提交 
 
 Q: git not found 
 
 A: 执行 npm install hexo-deployer-git --save
 
 Q: git auth fail 
 
 A: 修改本地git全局配置 
    git config --global user.name xxx
    git config --global user.email xxx
