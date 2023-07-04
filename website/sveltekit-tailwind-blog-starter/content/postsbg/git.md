---
title: git
date: 2019-09-29 16:52:02
tags: 
    - git
comments: true
cover: https://i.loli.net/2019/10/23/NtCopZqmeBSKOl1.jpg
description: some git commands
---

#### git相关命令

* 复制仓库
    ```bash
    git clone --bare git://xxx.xx.xx.xx/git_repo/old_project_name.git
    cd old_project_name.git
    git push --mirror new_project.git
    ```


* git命令行出现错误的用户名

    如果是出现在mac上，那么需要在钥匙串中删除github.com的记录即可

    来自[stackoverflow](https://stackoverflow.com/questions/21615431/git-pushes-with-wrong-user-from-terminal)

