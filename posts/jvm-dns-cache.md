---
title: jvm_dns_cache
date: 2022-06-23 17:41:26
tags:
    - java
    - dns
cover: https://image-1256217908.cos.ap-shanghai.myqcloud.com/20220626124942.png
---


## jvm中的dns缓存使用

> 当前版本 jdk8


### 参数配置
google后发现可通过*networkaddress.cache.ttl*与*sun.net.inetaddr.ttl*参数进行jvm级别的配置。

查寻jdk源码，相关代码都存放于
```text
InetAddressCachePolicy
```

其主要逻辑位于static代码块中
```java
 // Controls the cache policy for successful lookups only
private static final String cachePolicyProp = "networkaddress.cache.ttl";
private static final String cachePolicyPropFallback =
        "sun.net.inetaddr.ttl";

// Controls the cache policy for negative lookups only
private static final String negativeCachePolicyProp =
        "networkaddress.cache.negative.ttl";
private static final String negativeCachePolicyPropFallback =
        "sun.net.inetaddr.negative.ttl";

public static final int DEFAULT_POSITIVE = 30;

static {
        Integer tmp = null;

        try {
            tmp = new Integer(
              java.security.AccessController.doPrivileged (
                new PrivilegedAction<String>() {
                  public String run() {
                      return Security.getProperty(cachePolicyProp);
                  }
              }));
        } catch (NumberFormatException e) {
            // ignore
        }
        if (tmp != null) {
            cachePolicy = tmp.intValue();
            if (cachePolicy < 0) {
                cachePolicy = FOREVER;
            }
            propertySet = true;
        } else {
            tmp = java.security.AccessController.doPrivileged
                (new sun.security.action.GetIntegerAction(cachePolicyPropFallback));
            if (tmp != null) {
                cachePolicy = tmp.intValue();
                if (cachePolicy < 0) {
                    cachePolicy = FOREVER;
                }
                propertySet = true;
            } else {
                /* No properties defined for positive caching. If there is no
                 * security manager then use the default positive cache value.
                 */
                if (System.getSecurityManager() == null) {
                    cachePolicy = DEFAULT_POSITIVE;
                }
            }
        }

        try {
            tmp = new Integer(
              java.security.AccessController.doPrivileged (
                new PrivilegedAction<String>() {
                  public String run() {
                      return Security.getProperty(negativeCachePolicyProp);
                  }
              }));
        } catch (NumberFormatException e) {
            // ignore
        }

        if (tmp != null) {
            negativeCachePolicy = tmp.intValue();
            if (negativeCachePolicy < 0) {
                negativeCachePolicy = FOREVER;
            }
            propertyNegativeSet = true;
        } else {
            tmp = java.security.AccessController.doPrivileged
                (new sun.security.action.GetIntegerAction(negativeCachePolicyPropFallback));
            if (tmp != null) {
                negativeCachePolicy = tmp.intValue();
                if (negativeCachePolicy < 0) {
                    negativeCachePolicy = FOREVER;
                }
                propertyNegativeSet = true;
            }
        }
    }
```


通过上述代码，可以很清楚的看到*networkaddress.cache.ttl*配置优先级最高，取不到的情况下取*sun.net.inetaddr.ttl*配置；
若都无配置且SecurityManager（jdk17过时）为空的情况下，那么就执行配置设置（默认为30s）。


### 更深层调用
在方法*getAddressesFromNameService(String host, InetAddress reqAddr)*中观察到真实的调用方法
```java
addresses = nameService.lookupAllHostAddr(host);
```
此处的nameService为*sun.net.spi.nameservice*
例如官方包中提供的*sun.net.spi.nameservice.dns.DNSNameService*
其中的lookupAllHostAddr方法


### 使用方式
通过名字，很明显的看到这是网络地址缓存策略类，其使用场景位于*InetAddress*类中

```java
private static InetAddress[] getAllByName0 (String host, InetAddress reqAddr, boolean check)
        throws UnknownHostException  {

        /* If it gets here it is presumed to be a hostname */
        /* Cache.get can return: null, unknownAddress, or InetAddress[] */

        /* make sure the connection to the host is allowed, before we
         * give out a hostname
         */
        if (check) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkConnect(host, -1);
            }
        }

        InetAddress[] addresses = getCachedAddresses(host);

        /* If no entry in cache, then do the host lookup */
        if (addresses == null) {
            addresses = getAddressesFromNameService(host, reqAddr);
        }

        if (addresses == unknown_array)
            throw new UnknownHostException(host);

        return addresses.clone();
    }
```

getAllByName0方法中首先尝试从本地缓存中获取参数host对应的ip地址信息；
当缓存为空的情况，执行不同nameSpace的lookupAllHostAddr方法查找，例如ipv4类型由Inet4AddressImpl提供native方法。


### 测试
```java
ExecutorService executor = Executors.newScheduledThreadPool(1);
executor.sheduledAtFixedRate(() -> {
    InetAddress address = null;
    try {
        address = InetAddress.getByName("tuean.cn");
        logger.info(address.getHostAddress);
    } catch (Exception var) {
        logger.error("解析失败");
    }
}, 1, 1, TimeUnit.SECONDS);
```

上述代码启动后即以每秒一次的速度打印*tuean.cn*对应的ip。


#### 附录
[1. cache reference guide](http://javaeesupportpatterns.blogspot.com/2011/03/java-dns-cache-reference-guide.html)
[2. jdk source code](http://hg.openjdk.java.net/jdk8/jdk8/jdk/)
