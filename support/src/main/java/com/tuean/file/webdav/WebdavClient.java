package com.tuean.file.webdav;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.impl.SardineImpl;
import com.tuean.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WebdavClient {

    private static Logger logger = LoggerFactory.getLogger(WebdavClient.class);

    @Value("webdav.account")
    private String account;

    @Value("webdav.password")
    private String password;

    @Value("webdav.url")
    private String webdavUrl;

    private Sardine sardine;

    public void init() {
        sardine = new SardineImpl("tuean_z@163.com", "abng36e4w594gn8v");
    }

    public List<DavResource> list() throws IOException {
        List<DavResource> list = sardine.list("https://dav.jianguoyun.com/dav/");
        logger.info("webdav get list: {}", list);
        return list;
    }
}
