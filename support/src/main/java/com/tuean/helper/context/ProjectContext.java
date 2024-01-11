package com.tuean.helper.context;


import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.tuean.util.ReflectionUtil.findAnnotatedClasses;

public class ProjectContext {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContext.class);
    private static Map<String, Bean> ctxMap = new ConcurrentHashMap<>();
    private static Map<Class, Bean> beanMap = new ConcurrentHashMap<>();

    private Map<String, Object> beanCache = new ConcurrentHashMap<>();



    public ProjectContext(String packageName) {
        init(packageName);
    }

    public void init(String packageName) {
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(packageName, Ctx.class);
        annotatedClasses.stream()
//                .parallel()
                .forEach(this::createBean);
    }

    private void createBean(Class<?> clazz) {
        Ctx ctx = clazz.getAnnotation(Ctx.class);
        String beanName = ContextUtil.beanName(ctx, clazz);
        Constructor[] constructors = clazz.getConstructors();
        Class[] paramsClass = Arrays.stream(constructors).map(Constructor::getDeclaringClass).toArray(Class[]::new);
        try {
            Object o = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Inject inject = field.getDeclaredAnnotation(Inject.class);
                if (inject == null) continue;
                Class fieldClass = inject.getClass();
                String fieldBeanName = ContextUtil.beanName(inject, fieldClass);
                Bean bean = ctxMap.get(fieldBeanName);
            }
            Bean bean = new Bean(beanName, clazz, o);
            ctxMap.put(beanName, bean);
            beanMap.put(clazz, bean);
            logger.info("registered ctx bean :{}", beanName);
        } catch (Exception var) {
            logger.error("create bean error " + beanName, var);
            throw new RuntimeException();
        }
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

}
