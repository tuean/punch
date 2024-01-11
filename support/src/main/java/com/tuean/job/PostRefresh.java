package com.tuean.job;

import com.tuean.file.webdav.WebdavClient;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PostRefresh {

    private ScheduledExecutorService executors;

    public PostRefresh() {
        this.executors = Executors.newScheduledThreadPool(1);
    }

    public void start(WebdavClient client) {
        this.executors.schedule(() -> {
            log.info("refresh job start");
            doJob();
            log.info("refresh job finish");
        }, 1, TimeUnit.MINUTES);
    }

    private void doJob() {
        try {

        } catch (Exception var) {

        }
    }


}
