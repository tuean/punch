---
title: cors-repeat
date: 2020-05-22 10:52:00
tags:
    - cors
cover: https://i.loli.net/2019/10/30/bpQnviZ3TAN8Pgm.jpg
---


### 跨域请求头

#### 什么叫跨域
简单来说，当浏览器前端去访问另一个源（协议+域名+端口）下的资源，即为跨域

#### 为什么会出现跨域问题
浏览器根据安全策略，默认禁止跨域行为

#### 如何解决跨域问题
1. jsoup
2. cors 

##### 1. jsoup
利用加载js、html等静态资源的方式进行跨域通信（不够灵活，不在本文讨论范围）

##### 2. cors
返回头信息告知浏览器允许跨域

即添加
```properties
Access-Control-Allow-Origin: ${请求头中的Origin} or *（任一，一般不适用）
Access-Control-Allow-Credentials: true or false 是否允许携带cookie，当设置为true时，allow-Origin不允许设置为*
Access-Control-Allow-Headers： Content-Type,etc 允许的请求头 任意
Access-Control-Allow-Methods: GET,POST  等请求方法
```
至返回头信息中即可完成跨域配置。

>  注意：当请求存在调用链时，有可能下游或上游服务已添加跨域设置，为避免错误，以下参数必须且仅能被设置一次，需要在代码块中进行特殊处理
> * Access-Control-Allow-Origin
> * Access-Control-Allow-Credentials


#### 实现方式-nginx
```conf
server {
    listen     8888;
    location / {

        add_header Access-Control-Allow-Origin "$http_origin" always;
        add_header Access-Control-Allow-Headers 'ycas_token,Content-Type,X-Requested-WIth,Origin,Accept,Authorization,req_id,wx_code' always;
        add_header Access-Control-Allow-Credentials true;
        add_header Access-Control-Allow-Methods 'GET,POST,DELETE,PUT,OPTIONS' always;

        if ($request_method = 'OPTIONS') {
           return 200;
        }


        proxy_http_version     1.1;
        proxy_set_header       Host                      $host:$server_port;
        proxy_set_header       X-Real-IP                 $remote_addr;
        proxy_set_header       X-Forwarded-For           $proxy_add_x_forwarded_for;
        proxy_pass http://yourService/;
    }
}
```


#### 实现方式-代码实现
后端项目通用逻辑：拦截请求，根据请求信息修改返回头；
对于java spring项目而言，可以通过添加Filter或Interceptor拦截请求，修改返回头，匹配请求方式为OPTIONS时直接返回；