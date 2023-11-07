---
title: spring-pathPattern
date: 2022-07-04 12:46:38
tags:
    - java
    - spring
cover: 
---


### spring5中的pathPattern

近期将springboot版本由2.1.1升级至2.7.1时发现出现path校验失败的场景，查看spring升级文档后发现，默认的路径匹配已经调整为PathPattern;

该包完整路径未： *org.springframework.web.util.pattern.PathPattern*

在springboot项目中可通过
```properties
spring.mvc.pathmatch.matching-strategy=
```

可选参数在 *WebMvcProperties.MatchingStrategy*中
```java
public static enum MatchingStrategy {
        ANT_PATH_MATCHER,
        PATH_PATTERN_PARSER;

        private MatchingStrategy() {
        }
    }
```

上述两者配置的实现类分别为
```
ANT_PATH_MATCHER
Use the AntPathMatcher implementation.
PATH_PATTERN_PARSER
Use the PathPatternParser implementation.
```




自 [2d89a8253cf4fc23dee831256a75730fcbba68d9](https://github.com/spring-projects/spring-boot/commit/2d89a8253cf4fc23dee831256a75730fcbba68d9) 后调整为*PATH_PATTERN_PARSER*


### 参考文档
* [1.spring](https://docs.spring.io/spring-framework/docs/5.0.0.RC3_to_5.0.0.RC4/Spring%20Framework%205.0.0.RC4/org/springframework/web/util/pattern/PathPattern.html)
* [2.MatchingStrategy](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/web/servlet/WebMvcProperties.MatchingStrategy.html)
* [3.issue 24805](https://github.com/spring-projects/spring-boot/issues/24805)