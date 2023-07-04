package com.tuean.entity;

public class JsonResponseWrapper<T> {

    private int code;

    private String message;

    private T data;


    public JsonResponseWrapper() {
    }

    public JsonResponseWrapper(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonResponseWrapper(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
