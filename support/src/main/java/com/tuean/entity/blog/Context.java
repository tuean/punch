package com.tuean.entity.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Context {

    private List<PostItem> recommend;

    private List<PostItem> postItems;

    private List<Tag> tags;


    public Context(List<PostItem> recommend, List<PostItem> postItems, List<Tag> tags) {
        this.recommend = recommend;
        this.postItems = postItems;
        this.tags = tags;
    }


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PostItem> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<PostItem> recommend) {
        this.recommend = recommend;
    }

    public List<PostItem> getPosts() {
        return postItems;
    }

    public void setPosts(List<PostItem> postItems) {
        this.postItems = postItems;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
