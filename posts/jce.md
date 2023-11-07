---
title: jce
date: 2022-06-23 17:32:49
tags:
    - java
    - aes
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/20220626124753.png
---


### aes解密问题

windows开发环境，aes解密代码正常运行。

发布上服务器后，提示
```text
java.security.InvalidKeyException: Illegal key size
	at javax.crypto.Cipher.checkCryptoPerm(Cipher.java:1039)
	at javax.crypto.Cipher.implInit(Cipher.java:805)
	at javax.crypto.Cipher.chooseProvider(Cipher.java:864)
	at javax.crypto.Cipher.init(Cipher.java:1396)
	at javax.crypto.Cipher.init(Cipher.java:1327)
	......
```


排查后发现服务器当前jdk版本为：
```text
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```


经过检索，发现是低版本jdk中对加解密包进行了限制，具体的版本关系可参考：
https://www.jvmhost.com/articles/jce-unlimited-cipher-policy-different-jdk-versions/


#### 附录
[[1.] oracle jdk8下载](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
