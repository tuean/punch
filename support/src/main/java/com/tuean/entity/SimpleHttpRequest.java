package com.tuean.entity;

import java.util.Map;

public class SimpleHttpRequest {

    private String url;

    private Map<String, String> params;

    private byte[] reqBody;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public byte[] getReqBody() {
        return reqBody;
    }

    public void setReqBody(byte[] reqBody) {
        this.reqBody = reqBody;
    }
}
