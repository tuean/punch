package com.tuean.annotation;

import com.tuean.config.enums.HttpMethod;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ApiJson {

    String path() default "";

    HttpMethod method() default HttpMethod.GET;

}