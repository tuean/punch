package com.tuean.file.webdav;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.impl.SardineImpl;
import com.tuean.cache.ResourceCache;
import com.tuean.config.Environment;
import com.tuean.consts.ResourceType;
import com.tuean.entity.MarkdownFile;
import com.tuean.entity.blog.Context;
import com.tuean.entity.blog.Post;
import com.tuean.helper.file.FileGenerator;
import com.tuean.util.Util;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.error.Mark;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebdavClient {

    private static Logger logger = LoggerFactory.getLogger(WebdavClient.class);

//    @Value("webdav.account")
//    private String account;
//
//    @Value("webdav.password")
//    private String password;
//
//    @Value("webdav.url")
//    private String webdavUrl;

    private ResourceCache resourceCache;

    private List<MarkdownFile> temp = new ArrayList<>(1024);
    private Sardine sardine;

    public WebdavClient(ResourceCache resourceCache) {
        this.resourceCache = resourceCache;
        if (sardine == null) {
            init();
        }
    }

    public void init() {
        sardine = new SardineImpl(Environment.getProperty("webdav.account"), Environment.getProperty("webdav.password"));
    }

    public void refreshPostJson() throws IOException {
        List<MarkdownFile> mds = loadFiles();

        // union info
        Context context = FileGenerator.generate(mds);
        ObjectMapper mapper = new ObjectMapper();
        byte[] content = mapper.writeValueAsBytes(context);
        resourceCache.storeWithPath(Collections.emptyList(), content, "utf8", "post.json", ResourceType.json);

        // single markdown file
        for (MarkdownFile md : mds) {
            Post post = Util.convertFile2Post(md);
            byte[] content_2 = mapper.writeValueAsBytes(post);
            resourceCache.storeWithPath(Collections.emptyList(), content_2, "utf8", post.getTitle(), ResourceType.json);
        }
    }

    public List<DavResource> list() throws IOException {
        if (sardine == null) init();
        String url = Environment.getProperty("webdav.domain") + Environment.getProperty("webdav.url");
        List<DavResource> list = sardine.list(url);
        logger.info("webdav get list: {}", list);
        return list;
    }



    public List<MarkdownFile> loadFiles() throws IOException {
        return list().stream()
                .parallel()
                .filter(file -> !Objects.equals(file.getPath(), Environment.getProperty("webdav.url")))
                .map(this::loadAndParseDavFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public MarkdownFile loadAndParseDavFile(DavResource davResource) {
        try {
            String domain = Environment.getProperty("webdav.domain");
            String path = domain + davResource.getPath();
            InputStream in = sardine.get(path);
            logger.info("start to load:{}", path);
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






}
