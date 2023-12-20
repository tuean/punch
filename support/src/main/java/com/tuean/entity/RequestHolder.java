package com.tuean.entity;

import com.tuean.config.Environment;
import com.tuean.consts.ResourceType;

import java.util.List;

public class RequestHolder {

    private ResourceType resourceType;

    private byte[] response;

    private List<HttpHeader> headers;


    public RequestHolder(ResourceType resourceType, byte[] response) {
        this.resourceType = resourceType;
        this.response = response;
        this.headers = Environment.headers;
    }

    public RequestHolder(ResourceType resourceType, byte[] response, List<HttpHeader> headers) {
        this.resourceType = resourceType;
        this.response = response;
        this.headers = headers;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }
}
