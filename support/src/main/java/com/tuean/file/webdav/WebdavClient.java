package com.tuean.file.webdav;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.impl.SardineImpl;
import com.tuean.annotation.Value;
import com.tuean.config.Environment;
import com.tuean.entity.MarkdownFile;
import com.tuean.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebdavClient {

    private static Logger logger = LoggerFactory.getLogger(WebdavClient.class);

    @Value("webdav.account")
    private String account;

    @Value("webdav.password")
    private String password;

    @Value("webdav.url")
    private String webdavUrl;

    private List<MarkdownFile> temp = new ArrayList<>(1024);
    private Sardine sardine = null;

    public WebdavClient() {
        synchronized (sardine) {
            if (sardine == null) {
                init();
            }
        }
    }

    public void init() {
        sardine = new SardineImpl(Environment.getProperty("webdav.account"), Environment.getProperty("webdav.password"));
    }

    public List<DavResource> list() throws IOException {
        if (sardine == null) init();
        List<DavResource> list = sardine.list(Environment.getProperty("webdav.url"));
        logger.info("webdav get list: {}", list);
        return list;
    }

    public List<MarkdownFile> loadFiles() throws IOException {
        return list().stream()
                .parallel()
                .map(this::loadAndParseDavFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public MarkdownFile loadAndParseDavFile(DavResource davResource) {
        try {
            InputStream in = sardine.get(davResource.getPath());
            String content = IOUtils.toString(in, StandardCharsets.UTF_8);
            String fileName = davResource.getName();
            String fileId = Util.transferFileId(fileName);
            Long lastModified = davResource.getModified().getTime();
            return new MarkdownFile(fileId, fileName, 0, content, null, lastModified);
        } catch (IOException e) {
            logger.info("get webdav file error:{}", davResource.getPath());
            logger.error("", e);
        }
        return null;
    }


    public void generateJsonFile() throws IOException {
        if (CollectionUtils.isEmpty(temp)) {
            temp = loadFiles();
        }
        int size = Integer.parseInt(Environment.getProperty("blog.post.recommend.size"));

    }



}
