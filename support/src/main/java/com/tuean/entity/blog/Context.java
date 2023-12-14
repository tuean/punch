package com.tuean.entity.blog;

import java.util.List;

public class Context {

    private List<Post> recommend;

    private List<Post> posts;

    private List<Tag> tags;


    public Context(List<Post> recommend, List<Post> posts, List<Tag> tags) {
        this.recommend = recommend;
        this.posts = posts;
        this.tags = tags;
    }


    public List<Post> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Post> recommend) {
        this.recommend = recommend;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
