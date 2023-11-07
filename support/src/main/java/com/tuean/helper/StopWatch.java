package com.tuean.helper;

public class StopWatch {

    private long start = 0L;

    private long end = 0L;

    public void start() {
        this.start = System.currentTimeMillis();
        this.end = 0L;
    }

    public void stop() {
        this.end = System.currentTimeMillis();
    }

    public long getLastTask() {
        if (this.end == 0L) this.stop();
        return this.end - this.start;
    }



}
