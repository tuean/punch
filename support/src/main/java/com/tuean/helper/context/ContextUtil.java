package com.tuean.helper.context;

import com.tuean.util.Util;

public class ContextUtil {

    public static String beanName(Class clazz) {
        return clazz.getPackageName() + "." + clazz.getName();
    }

    public static String beanName(Ctx ctx, Class clazz) {
        String beanName = ctx.name();
        if (Util.isBlank(beanName)) beanName = ContextUtil.beanName(clazz);
        return beanName;
    }

    public static String beanName(Inject inject, Class clazz) {
        String beanName = inject.name();
        if (Util.isBlank(beanName)) beanName = ContextUtil.beanName(clazz);
        return beanName;
    }

}
