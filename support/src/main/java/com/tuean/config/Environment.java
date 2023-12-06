package com.tuean.config;

import java.util.Properties;

public class Environment {

    public static Properties properties = new Properties();

    public static void addProperty(Object k, Object v){
        properties.put(k, v);
    }
    public static String getProperty(String key) {
        return String.valueOf(properties.get(key));
    }




}
