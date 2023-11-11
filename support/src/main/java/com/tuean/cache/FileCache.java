package com.tuean.cache;

import com.tuean.util.Util;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileCache {

    private String filePath;

    private File originFile;

    private boolean initialized;

    private byte[] bytes;


    public FileCache(String filePath, File originFile, boolean initialized) {
        this.filePath = filePath;
        this.originFile = originFile;
        this.initialized = initialized;
    }

    public void read() throws IOException {
//        FileInputStream in = new FileInputStream(this.filePath);
//        IOUtils.read(in, bytes);
//        long fileSize = Util.fileSize(filePath);

    }

}
