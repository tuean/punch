package com.tuean.annotation;

import com.tuean.config.enums.HttpMethod;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    String path() default "";

}
