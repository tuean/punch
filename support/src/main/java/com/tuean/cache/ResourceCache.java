package com.tuean.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tuean.consts.ResourceType;
import com.tuean.entity.FileContent;
import com.tuean.file.PropertiesFileReader;
import com.tuean.util.Util;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tuean.consts.Const.URL_PATH_STR;

@Slf4j
public class ResourceCache {

    public static final String PREFIX = "";
    public LoadingCache<String, FileContent> getCache() {
        return fileCache;
    }

    private final LoadingCache<String, FileContent> fileCache = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .build(new CacheLoader<>() {
                @Override
                public FileContent load(String s) throws Exception {
                    return new FileContent();
                }
            });


    public ResourceCache() {
        String proxyLocation = PropertiesFileReader.getConfig().getSourcePath();
//        log.info("start to load resource file: {}", proxyLocation);

        File files = new File(proxyLocation);
        if (!files.isDirectory() || !files.exists()) throw new RuntimeException("resource file location with wrong config");
        List<String> prefixList = new ArrayList<>();
//        prefixList.add(PREFIX);
        loadAndRegister(files, prefixList);
    }

    private void loadAndRegister(File file, List<String> prefixList) {
        File[] files = file.listFiles();
        if (files == null || files.length < 1) return;
        Arrays.stream(files)
                .filter(File::isFile)
//                .filter(n -> n.getName().endsWith(".md"))
                .forEach(n -> storeWithPath(prefixList, n));
        Arrays.stream(files)
                .filter(File::isDirectory)
                .forEach(n -> {
                    List<String> nextPrefixList = new ArrayList<>(prefixList);
                    nextPrefixList.add(n.getName());
                    loadAndRegister(n, nextPrefixList);
                });
    }

    private void storeWithPath(List<String> prefixList, File file) {
        prefixList = prefixList.stream().filter(n -> !StringUtil.isNullOrEmpty(n)).toList();
        String path = Util.convert2path(prefixList, file.getName());
//        log.info("file path: {}", path);
        byte[] bytes = loadFileContents(file.getPath());
        String hash = Util.md5(bytes);
        String encoding = Util.getFileContent(file);
        String extension = Util.getExtensionByStringHandling(file.getPath()).orElse("");
        FileContent fileContent = new FileContent(file.getName(), path, file.getAbsolutePath(), hash, encoding, bytes, ResourceType.getByExtension(extension));
        fileCache.put(Util.encodeChinese(path), fileContent);
    }

    public void storeWithPath(List<String> prefixList, byte[] bytes, String encoding, String fileName, ResourceType resourceType) {
        String path = Util.convert2path(prefixList, fileName);
        FileContent fileContent = new FileContent(fileName, path, null, null, encoding, bytes, resourceType);
        fileCache.put(Util.encodeChinese(path), fileContent);
    }

    private byte[] loadFileContents(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (Exception var) {
            log.error("load resource file error", var);
        }
        return null;
    }

    public byte[] getCachedFileContents(String filePath) throws IOException {
        FileContent fileContent = fileCache.getIfPresent(filePath);
        if (fileContent == null) {
            return null;
        }
        return fileContent.getBytes();
    }





}
