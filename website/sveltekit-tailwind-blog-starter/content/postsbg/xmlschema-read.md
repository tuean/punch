---
title: xmlschema-read
date: 2020-01-07 16:14:10
tags:
    - problem
cover: https://i.loli.net/2021/02/18/alfmWNuB8ZpQPAR.jpg
---

### webService报错

#### 报错内容如下
```text
Exception in thread "main" java.lang.NoSuchMethodError: org.apache.ws.commons.schema.XmlSchemaCollection.read(Lorg/w3c/dom/Document;Ljava/lang/String;)Lorg/apache/ws/commons/schema/XmlSchema;
```

#### 修复
1. 观察是否导入
```xml
<artifactId>xmlchema-core</artifactId>
```
或者
```xml
<artifactId>XmlSchema</artifactId>
```

2. 查找该方法，其位于
```xml
<artifactId>xmlchema-core</artifactId>
<version>2.0.3</version>
```

3. 解决项目中的jar包版本冲突

