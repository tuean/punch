package com.tuean.helper.context;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectContext {

    private Map<String, Bean> ctx;

    public ProjectContext() {
        this.ctx = new ConcurrentHashMap<>();
    }


    public void init() {

    }

}
