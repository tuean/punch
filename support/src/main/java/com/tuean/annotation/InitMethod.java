package com.tuean.annotation;

import com.tuean.consts.Empty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitMethod {

    String value() default "";

    int order() default Integer.MIN_VALUE;

    Class[] dependencies() default Empty.class;

}
