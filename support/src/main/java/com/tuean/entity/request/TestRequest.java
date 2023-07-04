package com.tuean.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TestRequest {

    private String test;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public TestRequest() {
    }

    public TestRequest(String test, Date date) {
        this.test = test;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TestRequest{" +
                "test='" + test + '\'' +
                ", date=" + date +
                '}';
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
