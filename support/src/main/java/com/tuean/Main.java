package com.tuean;

import com.tuean.cache.ResourceCache;
import com.tuean.entity.Config;
import com.tuean.entity.MarkdownFile;
import com.tuean.entity.blog.Post;
import com.tuean.file.PropertiesFileReader;
import com.tuean.file.webdav.WebdavClient;
import com.tuean.helper.StopWatch;
import com.tuean.server.http.HttpServer;
import com.tuean.server.http.Router;
import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * -Dlogback.configurationFile=config/my-logback.xml
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Config config = PropertiesFileReader.readProperties();
        if (config == null) {
            logger.error("read config error");
            return;
        }
        logger.info("punch start");

        Router router = new Router("com.tuean");
        ResourceCache resourceCache = new ResourceCache();

        router.init();
        router.init(resourceCache);

        WebdavClient client = new WebdavClient(resourceCache);


        HttpServer httpServer = new HttpServer(config.getPort(), router);
        httpServer.run();
        logger.info("server started, cost: {} ms", stopWatch.getLastTask());
    }

}
