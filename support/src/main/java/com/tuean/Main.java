package com.tuean;

import com.tuean.cache.ResourceCache;
import com.tuean.entity.Config;
import com.tuean.file.PropertiesFileReader;
import com.tuean.server.http.HttpServer;
import com.tuean.server.http.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StopWatch;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * -Dlogback.configurationFile=config/my-logback.xml
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Config config = PropertiesFileReader.readProperties();
        if (config == null) {
            logger.error("read config error");
            return;
        }
//        String packageName = Main.class.getPackageName();
        String packageName = "com.tuean";
        logger.info("punch start");
        ApplicationContext context = new AnnotationConfigApplicationContext(packageName);
        ResourceCache resourceCache = new ResourceCache();
        resourceCache.init();
        Router router = new Router(packageName, context);
        router.init();
        router.init(resourceCache);

        HttpServer httpServer = new HttpServer(config.getPort(), router);
        httpServer.run(stopWatch);
    }

}
