package com.tuean.job;

import com.tuean.annotation.InitMethod;
import com.tuean.file.webdav.WebdavClient;
import com.tuean.helper.context.Ctx;
import com.tuean.helper.context.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

@Ctx
public class PostRefresh {

    private static final Logger logger = LoggerFactory.getLogger(PostRefresh.class);

    private ScheduledExecutorService executors;

    @Inject WebdavClient client;

    public PostRefresh() {
        this.executors = Executors.newScheduledThreadPool(1);
    }

    @InitMethod
    public void start() {
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
