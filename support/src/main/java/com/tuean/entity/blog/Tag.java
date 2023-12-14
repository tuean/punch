package com.tuean.entity.blog;

public class Tag {

    private String tag;

    private Integer no;

    public Tag(String tag, Integer no) {
        this.tag = tag;
        this.no = no;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
