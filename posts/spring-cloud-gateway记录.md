---
title: spring-cloud-gateway记录
date: 2020-06-17 18:15:59
tags:
    - spring-cloud-gateway
cover: https://i.loli.net/2021/02/18/qVLm52jTMuC6FXk.jpg
---


#### 背景
目前使用spring-cloud-gateway为基构建了公司内部服务网关，遇到了一些问题，记录于此

#### 问题列表

##### cloud 与 boot 对应关系
cloud版本与boot版本需要有相应对应关系，官方有提供[关系数据](https://start.spring.io/actuator/info)

##### 路由数据自定义
数据来源自由定义，主要是将数据转换为RouteDefinition（路由定义类）
1. 继承RouteDefinitionRepository
2. 重写getAllRouteDefinitions方法
由框架将RouteDefinition转换为真实的Route对象

RouteDefinition中
predicates可以使用
```json
[
  {
    "name" : "Path",
    "args" : {
      "Path" : "/**"
    }
  }
]
```

filters可以使用
```json
[
  {
    "name" : "StripPrefix",
    "args" : {
      "_genkey_0" : 0
    } 
  }
]
```

##### 输入输出日志
实现 GlobalFilter、Ordered接口
参考该项目[gateway-request-recorder-starter](https://github.com/giafei/gateway-request-recorder-starter)


##### 转发性能
经由个人测试，当添加完输入输出拦截器，并读取输入输出流后，性能下降严重
在该[github issue](https://github.com/spring-cloud/spring-cloud-gateway/issues/1003)中可见
当前尚未解决方案

##### 超时时间设置
gateway项目默认可设置全局超时时间
```yaml
spring:
    cloud:
      gateway:
        httpclient:
          connect-timeout: 10000
          response-timeout: 10000
```

当我们需要针对Route级别进行超时时间限制时，可参考该[github issue](https://github.com/spring-cloud/spring-cloud-gateway/issues/1112)
当版本大于Hoxton.SR4，合并了上述request merge后，在RouteDefinition中添加了metaData字段，作为额外信息

##### gateway提供项目本身接口
可使用WebFilter、Ordered进行拦截，注意此时所有的请求都会走该拦截器

##### gateway全局异常处理
可参考该[教程](https://www.cnblogs.com/throwable/p/10848879.html)


#### 参考文档
[1]: https://github.com/spring-cloud/spring-cloud-gateway/issues/1003
[2]: https://github.com/spring-cloud/spring-cloud-gateway/pull/1120
[3]: https://github.com/spring-cloud/spring-cloud-gateway/issues/1112
[4]: https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies
[5]: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent
[6]: https://start.spring.io/actuator/info
[7]: https://www.kubernetes.org.cn/7618.html
[8]: https://www.cnblogs.com/throwable/p/10848879.html
