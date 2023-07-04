package com.tuean.entity;

import com.tuean.consts.ResourceType;

public class RequestHolder {

    private ResourceType resourceType;

    private byte[] response;

    public RequestHolder(ResourceType resourceType, byte[] response) {
        this.resourceType = resourceType;
        this.response = response;
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
}
