package com.tuean.helper.context;


import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.tuean.util.ReflectionUtil.findAnnotatedClasses;

public class ProjectContext {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContext.class);
    private static Map<String, Bean> ctxMap = new ConcurrentHashMap<>();
    private static Map<Class, Bean> beanMap = new ConcurrentHashMap<>();


    public void init(String packageName) {
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(packageName, Ctx.class);
        annotatedClasses.parallelStream().forEach(this::createBean);
    }

    private void createBean(Class<?> clazz) {
        Ctx ctx = clazz.getAnnotation(Ctx.class);
        String beanName = ctx.name();
        if (Util.isBlank(beanName)) beanName = clazz.getPackageName() + "." + clazz.getName();
        Constructor[] constructors = clazz.getConstructors();
        Class[] paramsClass = Arrays.stream(constructors).map(Constructor::getDeclaringClass).toArray(Class[]::new);
        try {
            Object o = clazz.getDeclaredConstructor().newInstance();
            Bean bean = new Bean(beanName, clazz, o);
            ctxMap.put(beanName, bean);
            beanMap.put(clazz, bean);
        } catch (Exception var) {
            logger.error("create bean error " + beanName, var);
            throw new RuntimeException();
        }
    }


    public static Object getBeanByName(String name) {
        Object bean = ctxMap.get(name);
        if (bean == null) throw new NullPointerException();
        return bean;
    }

    public static Object getBeanByClass(Class clazz) {
        Object bean = beanMap.get(clazz);
        if (bean == null) throw new NullPointerException();
        return bean;
    }

}
