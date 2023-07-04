package com.tuean.entity;

import java.time.LocalDateTime;

public class MarkdownFile {

    private String id;

    private String fileName;

    private int sort;

    private String content;

    private String author;

    private Long lastModified;

    public MarkdownFile(String id, String fileName, int sort, String content, String author, Long lastModified) {
        this.id = id;
        this.fileName = fileName;
        this.sort = sort;
        this.content = content;
        this.author = author;
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }
}
