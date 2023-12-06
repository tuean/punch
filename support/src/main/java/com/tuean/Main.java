package com.tuean;

import com.tuean.cache.ResourceCache;
import com.tuean.entity.Config;
import com.tuean.file.PropertiesFileReader;
import com.tuean.file.webdav.WebdavClient;
import com.tuean.helper.StopWatch;
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
        String packageName = "com.tuean";
        logger.info("punch start");
        ResourceCache resourceCache = new ResourceCache();
        resourceCache.init();
        Router router = new Router(packageName);
        router.init();
        router.init(resourceCache);
        WebdavClient client = new WebdavClient();
        client.init();
        client.list();

        HttpServer httpServer = new HttpServer(config.getPort(), router);
        httpServer.run();
        logger.info("server started, cost: {} ms", stopWatch.getLastTask());
    }

}
