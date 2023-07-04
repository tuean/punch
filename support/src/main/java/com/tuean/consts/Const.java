package com.tuean.consts;

import com.tuean.entity.JsonResponseWrapper;

public class Const {

    public static final String URL_PATH_STR = "/";
    public static JsonResponseWrapper<?> not_found = new JsonResponseWrapper<>(404, "no mather");
    public static JsonResponseWrapper<?> err = new JsonResponseWrapper<>(500, "something wrong");
    public static JsonResponseWrapper<?> bad_request = new JsonResponseWrapper<>(400, "bad request");



}
