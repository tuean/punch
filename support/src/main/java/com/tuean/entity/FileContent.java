package com.tuean.entity;

import com.tuean.consts.ResourceType;

import java.util.Arrays;

public class FileContent {

    private String fileName;

    private String filePath;

    private String wholeFilePath;

    private String hash;

    private String encoding;

    private byte[] bytes;

    private ResourceType resourceType;

    public FileContent() {
    }

    public FileContent(String fileName, String filePath, String wholeFilePath, String hash, String encoding, byte[] bytes, ResourceType type) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.wholeFilePath = wholeFilePath;
        this.hash = hash;
        this.encoding = encoding;
        this.bytes = bytes;
        this.resourceType = type;
    }

    @Override
    public String toString() {
        return "FileContent{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", wholeFilePath='" + wholeFilePath + '\'' +
                ", hash='" + hash + '\'' +
                ", encoding='" + encoding + '\'' +
//                ", bytes=" + Arrays.toString(bytes) +
                "resourceType=" + resourceType + '\'' +
                '}';
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getWholeFilePath() {
        return wholeFilePath;
    }

    public void setWholeFilePath(String wholeFilePath) {
        this.wholeFilePath = wholeFilePath;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
