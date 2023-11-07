---
title: shadowsocks
date: 2019-09-28 18:29:22
tags: 
    - shadowsocks
comments: true
cover: https://i.loli.net/2019/10/23/vUrsJTni9XRkB83.jpg
description: shadowsocks
---

### 服务器环境
```
Ubuntu 14.04 x86_64   
```

### ss安装教程

> 近期临近国庆，vps被封ip的厉害，just my socks注册了连不上，工单也没有回复，暂时没有对应梯子了

> 收到工单回复 重装了下ss客户端，修改了密码、端口、加密方式，本地电脑mac pro禁用ip6即可

1. 下载ss客户端
```bash
pip3 install https://github.com/shadowsocks/shadowsocks/archive/master.zip
```
2. 配置文件
```json
{
    "server":"::",
    "server_port": "port",
    "local_address": "127.0.0.1",
    "local_port": 1080,
    "password": "password",
    "timeout": 300,
    "method":"aes-256-cfb",
    "fast_open": false
}
```
3. ssserver 命令
```bash
sudo ssserver -c /etc/shadowsocks.json -d start
```

> [参考教程](https://novnan.github.io/Shadowsocks/setup-Shadowsocks-on-ubuntu-1604/)


### fail2ban
> 如果日志中有发现恶意扫描，可使用该工具进行拦截屏蔽

> [参考教程](https://www.laobuluo.com/1800.html)

### 相关命令

* mac禁用ip6
```bash
sudo killall -HUP mDNSResponder
```


