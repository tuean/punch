---
title: apollo重复key
date: 2023-04-10 22:16:03
tags:
  - apollo
cover: 
---

## apollo重复key

当apollo中出现多个namespace中存在相同的key，官方使用方式为根据namespace排序，取最前面的记录。

当我们希望覆盖该规则时，直接代码中调整指定namespace下的参数。

### 思路



### 项目调整

#### 1.修改DefaultConfig.java

```java
package com.ctrip.framework.apollo.internals;

public class DefaultConfig extends AbstractConfig implements RepositoryChangeListener {
	
	// 添加以下代码
	public static Map<String, String> local_rename_map = new HashMap<>();
	
	static {
		local_rename_map.put("old_key", "new_key");
	}
	
	private boolean needRewriteKey() {
		return this.m_namespace.equals("your_namespace");
	}
	
	private void replaceRropertiesKey(Properties properties) {
		if (properties != null && needRewriteKey()) {
			for (String key : properties.stringPropertyNames()) {
				if (local_rename_map.containsKey(key)) {
					String nextKey = local_rename_map.get(key);
					if (properties.containsKey(nextKey)) continue;
					properties.setProperty(nextKey, properties.getProperty(key));
					properties.remove(key);
					logger.info("use key {} replace {}", nextKey, key);
				}
			}
		}
	} 
	
	
	// 修改以下方法
	private void updateConfig(Properties newConfigProperties, ConfigSourceType sourceType) {
		replaceRropertiesKey(newConfigProperties);
		...
	}
	
	private Map<String, ConfigChange> updateAndCalcConfigChanges(Properties newConfigProperties, ConfigSourceType sourceType) {
		replaceRropertiesKey(newConfigProperties);
		...
	}
}
```

#### 2. 打印出所有当前信息

新建ApolloEnvironmentPostProcessor.java

```java
@Order(Integer.MAX_VALUE)
public class ApolloEnvironmentPostProcessor implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(ApolloEnvironmentPostProcessor.class);
    
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        CompositePropertySource source = (CompositePropertySource) applicationContext.getEnvironment().getPropertySource().get("ApolloBootstrapPropertySources");
        COnfigPropertySource ps = (COnfigPropertySource) source.getPropertySources().stream().filter(n -> n.getName().equals("your_namespace")).findFirst().get();
        for (String key : ps.getPropertyNames()) {
            logger.info("{}, {}", key, ps.getProperty(key));
        } 
    }
}
```

修改main方法

```java
public class MainApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    
    @Value("${new_key}")
	private String v;

	@PostConstruct
	public void init() {
   	 	logger.info("new value:{}", v);
	}
    
    public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MainApplication.class);	
        application.addInitializers(new ApolloEnvironmentPostProcessor());
        appplication.run(args);
	}
}
```

