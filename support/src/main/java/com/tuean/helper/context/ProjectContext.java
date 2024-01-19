package com.tuean.helper.context;


import com.tuean.annotation.InitMethod;
import com.tuean.annotation.Value;
import com.tuean.config.Environment;
import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.tuean.util.ReflectionUtil.findAnnotatedClasses;

public class ProjectContext {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContext.class);
    private static final Map<String, Bean> ctxMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Bean> beanMap = new ConcurrentHashMap<>();
    private final Map<String, Bean> beanCache = new ConcurrentHashMap<>();


    public ProjectContext() {

    }

    public void registerBean(Object instance) {
        if (instance == null) throw new RuntimeException("bean is null");
        Class<?> clazz = instance.getClass();
        String beanName = ContextUtil.beanName(clazz);
        boolean exist = beanMap.containsKey(clazz);
        if (exist) throw new RuntimeException("already exist bean");

        Bean bean = new Bean(beanName, clazz, instance);
        beanMap.put(clazz, bean);
        beanCache.put(beanName, bean);
    }

    public void init(String packageName) {
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(packageName, Ctx.class);
        annotatedClasses.stream()
                .parallel()
                .forEach(this::prepareBean);
        createBeanActually();

        refreshValue();

        runInitMethod();
    }


    private void prepareBean(Class<?> clazz) {
        Ctx ctx = clazz.getAnnotation(Ctx.class);
        String beanName = ContextUtil.beanName(ctx, clazz);
        Constructor[] constructors = clazz.getConstructors();
//        Class[] paramsClass = Arrays.stream(constructors).map(Constructor::getDeclaringClass).toArray(Class[]::new);
        try {
            boolean hasRegistered = beanCache.containsKey(beanName);
            if (hasRegistered) {
                logger.info("bean: {} has registered", beanName); return;
            }
            Object o = clazz.getDeclaredConstructor().newInstance();
            Bean bean = new Bean(beanName, clazz, o);
            beanCache.put(beanName, bean);
            logger.info("prepare ctx bean :{}", beanName);
        } catch (Exception var) {
            logger.error("prepare bean error " + beanName, var);
            throw new RuntimeException();
        }
    }

    private void createBeanActually() {
        beanCache.entrySet().stream()
                .parallel()
                .forEach(this::fillBeanField);
    }

    private void fillBeanField(Map.Entry<String, Bean> entry) {
        String beanName = entry.getKey();
        Bean bean = entry.getValue();
        Object beanInstance = bean.getInstance();
        Field[] fields = bean.getClazz().getDeclaredFields();
        for (Field field : fields) {
            Inject inject = field.getDeclaredAnnotation(Inject.class);
            if (inject == null) continue;

            try {
                Class fieldClass = Class.forName(field.getType().getName());
                String fieldBeanName = ContextUtil.beanName(inject, fieldClass);
                Bean injectBean = beanCache.get(fieldBeanName);
                if (injectBean == null) {
                    logger.error("can't find bean: {}", beanName);
                    throw new NullPointerException();
                }
                field.setAccessible(true);
                field.set(beanInstance, injectBean.getInstance());
            } catch (Exception var) {
                logger.error("inject filed error: {} class:{}", field.getName(), bean.getClazz());
                logger.error("inject filed error", var);
                throw new RuntimeException("inject filed error");
            }
        }
        logger.info("register bean finish: {}", bean);
        ctxMap.put(beanName, bean);
        beanMap.put(bean.getClazz(), bean);
    }

    public void runInitMethod() {
        ctxMap.entrySet()
                .stream()
                .parallel()
                .forEach(stringBeanEntry -> {
                    Bean bean = stringBeanEntry.getValue();
                    Class<?> beanClass = bean.getClazz();
                    Method[] methods = beanClass.getMethods();
                    for (Method method : methods) {
                        InitMethod initMethod = method.getAnnotation(InitMethod.class);
                        if (initMethod == null) continue;
                        try {
                            method.invoke(bean.getInstance());
                        } catch (Exception e) {
                            logger.error("bean: {} init method error", bean.getName());
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    public void refreshValue() {
        Environment env = (Environment) getBeanByClass(Environment.class).getInstance();
        ctxMap.entrySet()
                .stream()
                .parallel()
                .forEach(stringBeanEntry -> {
                    Bean bean = stringBeanEntry.getValue();
//                    Class<?> beanClass = bean.getClazz();
                    Field[] fields = bean.getClazz().getDeclaredFields();
                    for (Field field : fields) {
                        Value value = field.getAnnotation(Value.class);
                        if (value == null) continue;
                        String keyName = value.value();
                        if (Util.isBlank(keyName)) continue;

                        String fieldType = field.getType().getName();
                        Object v;
                        switch (fieldType) {
                            case "java.lang.String" -> v = env.getProperty(keyName);
                            case "java.lang.Integer" -> v = Integer.parseInt(env.getProperty(keyName));
                            default -> throw new IllegalStateException("Unexpected value: " + fieldType);
                        }
//                        String envValue = env.getProperty(keyName);
                        field.setAccessible(true);
                        try {
                            field.set(bean.getInstance(), v);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    public static Bean getBeanByName(String name) {
        Bean bean = ctxMap.get(name);
        if (bean == null) throw new NullPointerException();
        return bean;
    }

    public static Bean getBeanByClass(Class clazz) {
        Bean bean = beanMap.get(clazz);
        if (bean == null) throw new NullPointerException();
        return bean;
    }

    public static Object getBeanInstanceByClass(Class clazz) {
        Bean bean = getBeanByClass(clazz);
        return bean.getInstance();
    }

}
