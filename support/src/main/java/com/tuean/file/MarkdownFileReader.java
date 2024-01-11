package com.tuean.file;

import com.tuean.entity.MarkdownFile;
import com.tuean.helper.context.Ctx;
import com.tuean.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Ctx
public class MarkdownFileReader {

    private static final Logger logger = LoggerFactory.getLogger(MarkdownFileReader.class);

    public List<MarkdownFile> allMarkdownFiles(String path) {
        File file = new File(path);
        if (!file.isDirectory()) throw new RuntimeException("path is not a directory: " + path);
        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(this::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public MarkdownFile parse(File file) {
        try {
            String fileName = file.getName();
            FileReader fileReader = new FileReader(file);
            String content = getContent(fileReader);
            Long lastModified = file.lastModified();
            String fileId = Util.transferFileId(fileName);
            return new MarkdownFile(fileId, fileName, 0, content, null, lastModified);
        } catch (Exception var) {
            logger.warn("read markdown file error: {}", file.getName());
        }
        return null;
    }


    private static String getContent(FileReader fileReader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        StringBuilder markdownContent = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            markdownContent.append(line).append("\n");
        }
        return markdownContent.toString();
    }

}
