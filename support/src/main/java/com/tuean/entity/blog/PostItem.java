package com.tuean.entity.blog;

import java.util.List;

public class PostItem {

    private String name;

    private String author;

    private String publishDate;

    private String title;

    private String description;

    private List<String> tags;

    private List<String> paths;

    public PostItem() {
    }

    public PostItem(String name, String author, String publishDate, String title, String description, List<String> tags, List<String> paths) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.paths = paths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
