package com.tuean;

import com.tuean.cache.ResourceCache;
import com.tuean.entity.Config;
import com.tuean.file.PropertiesFileReader;
import com.tuean.file.webdav.WebdavClient;
import com.tuean.helper.StopWatch;
import com.tuean.job.PostRefresh;
import com.tuean.server.http.HttpServer;
import com.tuean.server.http.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

        router.init(); // register api requests

        HttpServer httpServer = new HttpServer(config.getPort(), router);
        httpServer.run();
        logger.info("server started, cost: {} ms", stopWatch.getLastTask());

        WebdavClient client = new WebdavClient(resourceCache);
        client.refreshPostJson();

        router.init(resourceCache); // register file requests

        new PostRefresh().start(client); // start to refresh webdav files
    }

}
