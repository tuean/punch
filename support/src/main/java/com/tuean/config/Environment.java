package com.tuean.config;

import com.tuean.annotation.InitMethod;
import com.tuean.entity.HttpHeader;
import com.tuean.helper.context.Ctx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Ctx
public class Environment {

    protected Properties properties;

    public void init(InputStream inputStream) throws IOException {
        properties = new Properties();
        properties.load(inputStream);

        refreshHeaders();
    }

    public static List<HttpHeader> headers = new ArrayList<>();

    public void refreshHeaders() {
        if (properties == null) return;
        if (properties.get("access.control.allow.credentials") != null) {
            headers.add(new HttpHeader("Access-Control-Allow-Credentials", String.valueOf(properties.get("access.control.allow.credentials"))));
        }
        if (properties.get("access.control.allow.origin.default") != null) {
            headers.add(new HttpHeader("Access-Control-Allow-Origin", String.valueOf(properties.get("access.control.allow.origin.default"))));
        }
    }

    public void addProperty(Object k, Object v){
        properties.put(k, v);
    }
    public String getProperty(String key) {
        return String.valueOf(properties.get(key));
    }




}
