package com.tuean;

import com.tuean.cache.ResourceCache;
import com.tuean.config.Environment;
import com.tuean.entity.Config;
import com.tuean.file.PropertiesFileReader;
import com.tuean.file.webdav.WebdavClient;
import com.tuean.helper.StopWatch;
import com.tuean.helper.context.Bean;
import com.tuean.helper.context.ProjectContext;
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
//        Config config = PropertiesFileReader.readProperties();
//        if (config == null) {
//            logger.error("read config error");
//            return;
//        }

        logger.info("punch start");
        Environment env = PropertiesFileReader.initConfig();
        logger.info("load config finish");

        String port = env.getProperty("server.port");

        String packageName = "com.tuean";
        env.addProperty("package.name", packageName);

        ProjectContext projectContext = new ProjectContext();
        projectContext.registerBean(env);
        projectContext.init(packageName);
        logger.info("create ctx finish");

        Router router = (Router) ProjectContext.getBeanInstanceByClass(Router.class);
        HttpServer httpServer = new HttpServer(Integer.parseInt(port), router);
        httpServer.run();
        logger.info("server started, cost: {} ms", stopWatch.getLastTask());
    }

}
