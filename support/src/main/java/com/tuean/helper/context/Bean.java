package com.tuean.helper.context;

public class Bean {

    private String name;

    private Class clazz;

    private Object instance;

    public Bean() {
    }

    public Bean(String name, Class clazz, Object instance) {
        this.name = name;
        this.clazz = clazz;
        this.instance = instance;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz +
                ", instance=" + instance +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
