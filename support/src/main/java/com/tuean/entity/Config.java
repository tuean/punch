package com.tuean.entity;


public class Config {

    private Integer port;

    private String markdownPath;

    private String author;

    private String sourcePath;

    public Config(Integer port, String markdownPath, String author, String sourcePath) {
        this.port = port;
        this.markdownPath = markdownPath;
        this.author = author;
        this.sourcePath = sourcePath;
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getMarkdownPath() {
        return markdownPath;
    }

    public void setMarkdownPath(String markdownPath) {
        this.markdownPath = markdownPath;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
