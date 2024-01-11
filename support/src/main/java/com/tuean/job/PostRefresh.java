package com.tuean.job;

import com.tuean.file.webdav.WebdavClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class PostRefresh {

    private static final Logger logger = LoggerFactory.getLogger(PostRefresh.class);

    private ScheduledExecutorService executors;

    public PostRefresh() {
        this.executors = Executors.newScheduledThreadPool(1);
    }

    public void start(WebdavClient client) {
        ScheduledFuture<?> runnableFuture = this.executors.scheduleAtFixedRate(() -> {
            logger.info("refresh job start");
            doJob(client);
            logger.info("refresh job finish");
        }, 1, 1, TimeUnit.MINUTES);
    }

    private void doJob(WebdavClient client) {
        try {
            client.refreshPostJson();
        } catch (Exception var) {
            logger.info("refresh post error", var);
        }
    }


}
